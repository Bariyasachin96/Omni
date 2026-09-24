# Architecture

Easy Voice is an Android **TTS engine**, not a TTS voice. It does not synthesise
audio itself: it decides, for each piece of text the system hands it, *which
language that text is in* and *which installed engine and voice should speak
it*, then forwards the text to that engine. The user is blind and navigates
entirely with a screen reader, so a wrong decision is not cosmetic — it is a
sentence read in the wrong voice, or silence.

Everything below follows one utterance from the system to the speaker. When
hunting a bug, find the stage it belongs to and go straight there.

---

## The one entry point

`EasyVoiceTtsService.onSynthesizeText(request, callback)` —
`app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt`

Android calls this on a synthesis thread with the text and a callback that wants
PCM. Everything else in this document happens inside it or is called from it.
Its AutoTTS counterpart is `AutoTtsService.onSynthesizeText`, and the two are
structured the same way, branch for branch.

---

## Stage 0 — settings

All settings live in **statics on the service's companion object**, loaded once
in `onCreate` by `loadAllSettings()`. The synthesis path never reads
`SharedPreferences`. See INVARIANTS #4 for why.

The language list — which languages exist, which are enabled, which engine and
voice each uses — is `LangStore.languages`, a shared static rebuilt by the
settings screens and by the startup scan. Anything that rebuilds it must also
push the language sets to the native detector: INVARIANTS #1.

---

## Stage 1 — which mode

`modeInt` selects one of five readings of the text:

| mode | int | what it does |
|---|---|---|
| none | 0 | speak everything with the requested language |
| dual | 1 | Latin runs in English, everything else in one chosen language |
| auto | 2 | detect the language of the whole span and use one voice |
| google | 3 | as auto, but force Google's engine |
| mixed | 4 | split into runs and detect each one |
| multilingual | 5 | as mixed, but locale spans in the text win outright |

The branches are separate blocks inside `onSynthesizeText`, in the same order
AutoTTS has them. They differ in real ways — mixed and multilingual do not treat
a locale span the same, and dual never runs the detector at all — so a change to
one is rarely a change to all five.

---

## Stage 2 — locale spans

`splitByLocaleSpans(text)` (= AutoTTS `c3.e0.g`) looks for `LocaleSpan`s the
calling app attached to the text. It is on only when the "Use locale spans"
setting is on. Everything not covered by a span comes back tagged `"UNKNOWN"`.

Two deliberate quirks are copied: it asks for spans over `0 .. length - 1`, and
it advances past a span with `end + 1`.

---

## Stage 3 — segmentation (mixed, multilingual and dual)

`NativeEngine.processDirect` → `buildMixChunks` in
`app/src/main/cpp/tts_engine_core.cpp`. This is AutoTTS's `c3.d0.t`, and it is
the single most intricate piece of the app. It:

1. trims, collapses whitespace, folds decorated Unicode letters to plain ASCII
   (`normalizeFancyText`), and strips bidi controls;
2. splits the text into Latin and non-Latin runs, then splits those by number,
   punctuation and emoji, giving each piece a **type** 0..5;
3. merges adjacent pieces of the same type;
4. optionally re-spaces long digit runs ("smart number reading");
5. **resolves** the number, punctuation and emoji types according to their mode
   settings — "Specific language" keeps the type, "Auto language" takes the
   nearest neighbour's type, "Primary"/"Secondary" force type 1 or 2;
6. merges again by resolved type, optionally keeping punctuation separate.

The result crosses back into Kotlin as records of `type ␟ kind ␟ lang ␟ text`
joined by `␞`, with those three separators escaped inside the text.

**This whole stage is proven, not argued**: 163,296 generated cases were run
through AutoTTS's own `t()` and through `buildMixChunks` and the chunk lists
compared. `tools/verify/segmenter/`.

---

## Stage 4 — detection

Two different detectors, at two different call sites, and they are **not**
hinted the same way. This distinction is the source of more than one bug.

**Per span** — `nativeGetLanguages` → the span loop → `emitScriptSpan`.
The text is walked code point by code point, each classified into a script by
`classifyScript`; a run of one script becomes a span. Then:

- script 0 (nothing classified — digits, punctuation, spaces, emoji) →
  `"un"` with **`latin = true`**, and no detector runs at all;
- scripts 7..25 → a fixed language (Greek, Armenian, Hebrew, …);
- scripts 1..6 → CLD2, hinted with the per-script language hint and
  then **filtered** against the enabled languages, falling back to the script's
  own language.

**Per 64-character window** — `detectLanguageFull` → `detectWindowLang`, used by
auto and Google mode. Here the detector gets **no hints and no filter**; the
caller decides what to do with an answer the user has not enabled, falling back
to the script family.

The **script family** fallback is `scriptLangForCpFiltered` (AutoTTS `a.e`): given
a code point and the enabled languages, it answers the most likely language of
that script. It is **proven** over all 1,114,112 code points for 45 language
sets: `tools/verify/scriptfamily/`.

CLD3 used to sit beside CLD2 here as an Easy Voice extra. It was removed on
2026-09-02 at the owner's instruction; see CLAUDE.md, "CLD3 IS GONE".

---

## Stage 5 — language to engine and voice

For each chunk, in order:

    detected code → IsoCodes.toIso3 → LangStore.engineFor(lang, mode)
                 → if empty or "Disable", fall back to the mode's default
                 → onLoadLanguage(lang, "", "")
                 → loadVoice(engine, locale, variant, dedicated)

`onLoadLanguage` (AutoTTS `d0`) resolves the locale, engine and variant from the
language list and loads them into the chosen `TextToSpeech` client.
`loadVoice` (AutoTTS `f0`) picks the client, honours the dedicated-engine
setting, and short-circuits when the current voice already matches.

---

## Stage 6 — speaking

`speakChunk` sets the rate, pitch and volume — each the product of the system
request and the per-language setting — copies the request's parameter bundle
minus the keys the platform must not see, and calls `speak`.

`onDone` on a binder thread posts the next chunk to the main thread, loads that
chunk's language, and repeats. The queue is `chunkQueue`.

Two deliberate departures from AutoTTS, both requested by the owner: the first
chunk is spoken inline on the synthesis thread rather than after a 50 ms post,
and the next-chunk hop is `post` rather than `postDelayed(50)`.

**Three threads share this stage, and every callback has to say which utterance
it belongs to.** `onSynthesizeText` runs on the screen reader's synthesis thread
and blocks there; the target engine's `onStart` / `onDone` / `onError` / `onStop`
arrive on OUR binder threads; and the next-chunk step runs on the main looper.
`setOnUtteranceProgressListener` holds ONE listener per `TextToSpeech` and the
framework reads it at dispatch time, so when the reader interrupts, the old
utterance's callback lands on the listener the NEW utterance installed.

Two guards keep them apart, and INVARIANTS #26 has the whole story:
- the utterance id we hand the engine is
  `"${utteranceId}_${synthesisGeneration}_${chunkCounter}"`, unique per utterance
  and per chunk, and the framework echoes it verbatim. `onStart`, `onDone` and
  both `onError`s drop anything that does not match;
- `synthesisGeneration`, bumped once per `onSynthesizeText`, is checked at the
  top of `speakChunk` and inside the posted next-chunk step -- for the callback
  that was live when it arrived and acts only after the utterance has ended.

---

## The native library

`app/src/main/cpp/tts_engine_core.cpp` is one translation unit built with CMake
against **CLD2**, which CI clones at build time (it is not in
this repository). It holds, roughly in file order:

| area | what |
|---|---|
| Unicode normaliser | `normalizeFancyCodepoint`, `normalizeFancyText` |
| emoji | `isEmoji`, `emojiRunLengthAt` |
| smart numbers | the keyword table, `isPhoneShaped`, `respaceDigits` |
| segmenter | `segmentTypeOf`, `splitBy*`, `buildMixChunks` |
| script classification | `classifyScript`, `SCRIPT_FIXED_LANG` |
| script families | `FAMILY_*`, `familyForCp`, `familyLangForCpFiltered` |
| Java emulation | `javaStringHash`, `javaHashSetOrder` — Android's `HashSet` order is part of the behaviour |
| detectors | `detectWindowLang`, `emitScriptSpan` |
| hints | `setLanguageHints`, `rebuildScriptLanguageTables`, `kScriptLangPairs` |
| JNI | the `Java_com_sachinbaria_easyvoice_NativeEngine_*` entry points |

---

## The user interface

The UI is the **one part deliberately not matched to AutoTTS** — the owner asked
for a layout that suits a screen reader instead. It is Jetpack Compose:

| file | screen |
|---|---|
| `MainActivity.kt` | the startup scan and the two-tab shell |
| `ModesScreen.kt` | Main Settings — the mode radios and each mode's settings |
| `AdvancedScreen.kt` | Advanced |
| `LanguagesActivity.kt` | the language list |
| `ConfigurationScreen.kt` | Configuration settings — the per-language list |
| `VoiceScreen.kt`, `VoiceRows.kt`, `VoiceSetupActivity.kt` | the per-language voice screen |
| `ModesScreen.kt` (ModeSettingsActivity) | one mode's settings on its own screen |
| `ComposeTheme.kt` | the accessible dark palette |

The accessibility rules these screens must keep are INVARIANTS #5 to #11.

---

## Where the bugs have actually been

Ranked by how long each took to find, because it says where to look first:

1. **Inside `libcld2.so`** — a bare number was read in the wrong language
   because script 0 answers `latin = true`. No amount of reading the Java would
   have found it; it took disassembling the library.
2. **A missing call after a list rebuild** — INVARIANTS #1. Found by grepping
   for the rebuild sites and comparing the count with AutoTTS's.
3. **A neighbour-type scan that walked past a whitespace segment** — found by a
   harness, not by reading.
4. **A runtime `SharedPreferences` read on the Test path** — INVARIANTS #4.
   Found by grepping `prefs.` in the synthesis path.

The lesson each time: **compare against the real AutoTTS artefact**, and where a
function is small enough to isolate, **prove it with a harness** rather than
argue about it.
