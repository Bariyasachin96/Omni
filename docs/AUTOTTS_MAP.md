# AutoTTS → Easy Voice symbol map

Easy Voice is a re-implementation of **AutoTTS 5.7.7.26** (`com.vnspeak.autotts`),
and the project rule is that its behaviour matches AutoTTS exactly, everywhere
except the user interface. Every bug found in this codebase so far has been
"our X does not do what their Y does", so the first question when hunting one is
always **"what is the AutoTTS counterpart of this code?"**

This file answers that in both directions. The reference tree is
`autotts_reference/v5.7.7.26/decompiled_java/`; regeneration commands are in
`autotts_reference/README.md`.

**Verified** below means checked against the decompiled source or the `.so`, and
recorded in `CLAUDE.md` with the evidence. **Proven** means checked by an
exhaustive comparison harness under `tools/verify/`, not by reading.

---

## The obfuscated names change between releases

`c3.*` class letters and `AutoTtsService`'s static letters shift with almost
every AutoTTS release — they shifted by one from 5.7.7.10 to 5.7.7.18 and again
to 5.7.7.26. **Never carry a letter over from an older note.** The table below is
5.7.7.26 only. `CLAUDE.md` keeps the older maps for reading old commits.

The letters are also **not** guessable from position. Read the return expression:
`T(lang)` returns the *variant*, `U(lang)` returns the *locale tag*, and
`R`/`S`/`V` are *pitch*/*speed*/*volume* — not the alphabetical order you expect.

---

## Service — `com/vnspeak/autotts/AutoTtsService.java`

Ours: `app/src/main/java/com/tts/easyvoice/EasyVoiceTtsService.kt`

| AutoTTS | Easy Voice | Status |
|---|---|---|
| `onSynthesizeText` | `onSynthesizeText` | verified per mode |
| `P()` | `reloadLanguagesIfMissing()` | verified |
| `s0()` | `pushLanguageSets()` (companion) | verified — see INVARIANTS |
| `e0()` | `loadAllSettings()` + `LangStore.loadLanguages` | verified |
| `d0(lang, country, variant)` | `onLoadLanguage` | verified |
| `f0(pkg, locale, variant, dedicated)` | `loadVoice` | verified line for line |
| `W(...)` original-voice path | `loadVoiceOriginal` | verified |
| `X(...)` dedicated-engine path | `loadVoiceDedicated` | verified |
| `o0(Locale)` | `findEngineForLocale` | verified — three passes |
| `j0(String)` | `parseVoiceNameAsLocale` | verified |
| `Q(lang)` | `LangStore.engineFor` | verified |
| **`T(lang)`** | `LangStore.variantFor` — the **variant** | verified |
| **`U(lang)`** | `LangStore.localeFor` — the **locale tag** | verified |
| `R(lang)` | `LangStore.pitchFor` | verified |
| `S(lang)` | `LangStore.speedFor` | verified |
| `V(lang)` | `LangStore.volumeFor` | verified |
| `N(cb, n)` / `O(cb, n)` | `startAndFinish` | verified |
| `k0` / `t0` | the keep-alive silence loop, inline in `onSynthesizeText` | verified |
| `p0()` / `a0()` | `startForegroundIfPossible` / `isForegroundActive` | verified |

### Statics (all on the companion object)

| AutoTTS | Easy Voice |
|---|---|
| `T` | `modeInt` |
| `H` | `autoLang` |
| `I` | `dualLang` |
| `P` / `Q` | `mixLatinLang` / `mixNonLatinLang` |
| `J` / `L` / `N` | `numberModeInt` / `punctuationModeInt` / `emojiModeInt` |
| `K` / `M` / `O` | `numberSpecificLang` / `puncSpecificLang` / `emojiSpecificLang` |
| `V` | `localeSpansFlag` |
| `W` / `X` / `Y` / `Z` | `stripAudioAttrFlag` / `forceAccessibilityFlag` / `keepAliveFlag` / `showNotificationFlag` |
| `a0` | `disableAdvancedFlag` |
| `b0` | `quickCharacterFlag` |
| `d0` | `punctuationInFlowFlag` |
| `e0` | `smartNumberFlag` |
| `f0` | `smartNumberGroupSize` |
| `R` (the chunk list) | `chunkQueue` |
| `k0` (utterance id) | `utteranceIdStr` |

There was no AutoTTS counterpart for the CLD3 switch; it was removed on
2026-09-02 — see "CLD3" below.

---

## Segmenter — `c3/d0.java`

Ours: `app/src/main/cpp/tts_engine_core.cpp`. **PROVEN** as a whole: 163,296
generated cases through AutoTTS's own `t()` and through `buildMixChunks`, chunk
list against chunk list, identical. Harness: `tools/verify/segmenter/`.

| AutoTTS | Easy Voice |
|---|---|
| `t(text, numMode, puncMode, emojiMode, …)` | `buildMixChunks` |
| `b(s, out)` | `splitByPunct` |
| `c(s, out)` | `splitByEmoji` |
| `d(s, out)` | `splitByNumber` |
| `j(s)` | `segmentTypeOf` |
| `h(types)` | `typeAnywhere` |
| `i(types, at)` | `typeBefore` |
| `k(types, at, neutral)` | `surroundingType` |
| `a()` | `activeSmartNumberKeywords` |
| `e(text, keywords)` | `contextHasKeyword` |
| `p(keyword)` | `keywordMatchesAnywhere` |
| `f(list, at, 24)` | `firstUtf16Units` |
| `g(list, at, 48)` | `lastUtf16Units` |
| `l(s)` / `n(s)` / `o(s)` | the emoji / number / punctuation tests inside `segmentTypeOf` |
| `m(s)` | `hasEnoughDigits` |
| `q(s)` | the bidi strip inside `buildMixChunks` |
| `r(s)` | `isPhoneShaped` |
| `s(s)` | `respaceDigits` |
| pattern `b` | `looksLikeClockTime` |

Segment types, used everywhere: **0** whitespace, **1** Latin, **2** non-Latin,
**3** number, **4** punctuation, **5** emoji.

---

## Detection — `com/vnspeak/autotts/clsCLD2.java` and `libcld2.so`

Ours: `tts_engine_core.cpp` plus the Kotlin wrappers in `EasyVoiceTtsService.kt`.

| AutoTTS | Easy Voice | Status |
|---|---|---|
| `clsCLD2.a(int)` | `normalizeFancyCodepoint` | proven — full code-point sweep |
| `clsCLD2.b(String)` | `normalizeFancyText`, exposed as `NativeEngine.normalizeFancy` | proven |
| `clsCLD2.c(String)` | `firstValidCodePointU16` | verified |
| `clsCLD2.d(...)` | `detectLanguageFull` (C++), called by `detectLanguage` (Kotlin) | verified |
| `clsCLD2.e(...)` | `detectLanguageRuns` (Kotlin) over `nativeGetLanguages` | verified |
| `clsCLD2.f(...)` | `detectLanguageAggregate` (Kotlin) | verified |
| `clsCLD2.g(String)` | `isLatinCommonInherited` | verified |
| `clsCLD2.h(char)` | `isLatinPunctuation` | verified |
| `clsCLD2.i(Set)` | `NativeEngine.setLanguageHints` | verified |

### The native half — read from the arm64, addresses are 5.7.7.26's `libcld2.so`

| `libcld2.so` | Easy Voice | Status |
|---|---|---|
| `getLanguageSpans` **0x65392c** | the span loop in `nativeGetLanguages` | verified |
| its code-point classifier **0x653ae0** | `classifyScript` | verified, range for range |
| its span emitter **0x653e90** | `emitScriptSpan` | verified |
| `getLanguage` **0x6523c8** | `detectWindowLang` | verified |
| `setLanguageHints` **0x653588** | `Java_…_setLanguageHints` + `rebuildScriptLanguageTables` | verified |
| the 48 `{script, Language}` pairs **0x62f924** | `kScriptLangPairs` | decoded from the binary |
| `nativeGetLanguages`' span cap | `kMaxSpans = 128` (`mov w3, #0x80`) | verified |

**Script 0 is a case of its own** and the source of a real bug: a run with no
classified character — digits, ASCII punctuation, spaces, emoji — answers `"un"`
with **`latin = true`**, so AutoTTS reads a bare number with the **Latin**
preferred language. See CLAUDE.md, "The DETECTOR lives in libcld2.so".

---

## Script families — `com/vnspeak/autotts/a.java`

Ours: `tts_engine_core.cpp`. **PROVEN**: 45 enabled-language sets × all
1,114,112 code points, identical, including HashSet iteration order.
Harness: `tools/verify/scriptfamily/`.

| AutoTTS | Easy Voice |
|---|---|
| `a.b(int)` / `a.d(int)` | `familyForCp` |
| `a.c(int, Set)` | `familyLangForCpFiltered` |
| `a.e(int, Set)` | `scriptLangForCpFiltered` |
| `a.f(family, Set)` | `pickFamilyLang` + `javaFamilyFallback` |
| `a.g(Set, Set)` | `anyLangEnabled` |
| `a.a()` — the `w` map | the per-script branches inside `familyLangForCpFiltered` / `familyForCp` |
| `a.b … a.k` — the family sets | `FAMILY_LATIN`, `FAMILY_CYRILLIC`, `FAMILY_ARABIC`, `FAMILY_PERSIAN`, `FAMILY_URDU`, `FAMILY_DEVANAGARI`, `FAMILY_ETHIOPIC`, `FAMILY_CJK`, `FAMILY_DIGIT` |

Java's `HashSet` iteration order is part of the behaviour here — it decides which
family member is picked. `javaHashSetOrder` reproduces it, and it must model
**Android's** libcore, not the JDK the harness runs on. See CLAUDE.md.

---

## Settings store — `c3/n.java`

Ours: `app/src/main/java/com/tts/easyvoice/LangStore.kt`

| AutoTTS | Easy Voice |
|---|---|
| `n.c` (the language list) | `LangStore.languages` |
| `n.d` (the scan) | `EngineFinder.lastScanVoices` |
| `n.f` (enabled ISO set) | the set `pushLanguageSets()` builds |
| `n.e(Locale)` | `localeIso3` |
| `n.g(ctx, onlyEnabled)` | `rebuildFromScan` |
| `n.h()` | `dualLangList` |
| `n.i(pkg, onlyEnabled)` | `availableLanguagesFor` |
| `n.j(pkg)` / `n.k(pkg)` / `n.l(pkg)` | `languageCodesFor` / `checkedStatesFor` / `languageLabelsFor` |
| `n.m()` | `requiredLangs` |
| `n.n(lang)` | membership of the detect-ok set |
| `n.o` | `loadMode` |
| `n.q` | the Advanced-tab defaults in `loadAllSettings` |
| `n.x(ctx)` | `persistLanguages` |
| `n.y(ctx)` | `persistDisabled` |
| `n.z(ctx)` | the flag persist inside `persistAll` |
| `c3.f` (one language entry) | `LangStore.LangEntry` |

---

## Everything else

| AutoTTS | Easy Voice | Note |
|---|---|---|
| `c3/e.java` `a`/`b`/`c` | `IsoCodes.normalizeTag` / `toIso2` / `toIso3` | verified statement for statement |
| `c3/e0.java` `g(CharSequence)` | `splitByLocaleSpans` | verified, quirks included |
| `c3/e0.java` the class | `TextChunk` | type −1 vs a real type matters — see CLAUDE.md on dead branches |
| `c3/f0.java` | `SampleTexts.kt` | verified, 184 entries byte for byte |
| `c3/p.java` | `EasyVoiceLogger.kt` | verified |
| `c3/g0.java` | the import/export code in `AdvancedScreen.kt` | verified |
| `c3/u.java` / `c3/v.java` | the required-engines dialog | UI carve-out |
| `c3/k.java` | `ModesScreen`, `AdvancedScreen`, `LanguagesActivity`, `VoiceScreen`, `ConfigurationScreen`, `VoiceRows` | UI carve-out — layout differs on purpose |
| `NewSettingsActivity` | `MainActivity.kt` | UI carve-out |
| `CheckVoiceData` | `CheckVoiceData.kt` | verified, result codes included |
| `GetSampleText` | `GetSampleText.kt` | verified, result codes included |
| `c3/l0.java`, `AutoTtsService.h0`/`m0`/`K`/`Y`/`n0` | **not ported** | licence and signature checks — deliberate carve-out |

---

## CLD3 — REMOVED 2026-09-02

The Advanced tab once carried a "Use CLD3" switch with no AutoTTS counterpart,
and the rule for it was "wherever CLD2 makes a detection, the switch must be
able to put CLD3 there instead". **The owner removed it, A to Z**, after
measuring that CLD3 reads short Devanagari at about 50% while CLD2 reads it at
24 of 25. Nothing about CLD3 remains in the tree, and `invariants.sh` #16 fails
the build if any of it returns. See CLAUDE.md, "CLD3 IS GONE".

## AutoTTS code that must NEVER be ported

Reading these into our code would add behaviour AutoTTS does not actually have,
because the branches are unreachable there:

1. **`d0`'s `n3 == 1` and `n3 == 2` blocks** — `onIsLanguageAvailable` returns
   only `0` or `-2`, so only `n3 == 0` runs.
2. **`c3.k.K2(...)`** — returns `true` on every path, so `if (!K2(x)) continue`
   never skips.
3. **`onDone`'s mix/multilingual re-detect**, and
4. **its type fallback** (`1→P 2→Q 3→K 4→M 5→O`) — both dead because every chunk
   in `R` for those modes is built with `e0(String, String)`, whose type is −1.

`LangStore.engineFor` is still called at that point and its result deliberately
discarded, mirroring `Q(lang)`. The comment there says so; leave it.

---

# Full inventory audit, 2026-08-27

Asked for directly: *"AutoTTS mein kuch rah gaya ho to hamare mein na ho … chahe
native mein ho, chahe sabhi mode ka reading flow mein … koi system level ka kuch
rah gaya ho vah bhi dekh lena."* This is that sweep, done mechanically against
the 5.7.7.26 decompile rather than by re-reading prose.

**Scope.** All 47 classes in AutoTTS's own packages (`com/vnspeak/autotts` and
`c3`) were inventoried. Most of the small ones are compiler artifacts — listener
lambdas (`c3/g h i j q r s t c0 l`), `PackageInfoFlags` bridges for API 33
(`c3/x y z`), thread plumbing (`c3/h0 i0 j0`) — and carry no logic. Every class
that does was checked.

| checked | result |
|---|---|
| `c3/f0` — the 340 sample texts | **340 vs 340, byte-identical**, keys and strings |
| `c3/d` — keep-alive binder | one `c3.d` per engine in `this.g`, unbound in `onDestroy`. Our `engineBinders` map plus `unbindAllEngineKeepAlive` is the same shape |
| `AutoTtsService` — every method | all mapped. Verified this pass: `L()` notification, `M()` channel `tts_channel` at IMPORTANCE_LOW, small icon `17301540`, `W()` POST_NOTIFICATIONS on SDK 33+, `a0()` searching `getActiveNotifications()` for id **136549**, `N()` = `startAndFinish` (`start(16000, 2, 1)` / `done()`), `r0()` = "unlockSynthesis #n" |
| `onStartCommand` | AutoTTS `return 1`; ours `START_STICKY`. Same value |
| `onTaskRemoved` | AutoTTS calls only `super`; so do we |
| `c3/n` — all 31 methods | all mapped, including `n.r`'s voice-order default of the **string** `"1000"` |
| **`n.t()` vs `persistAll()`** | **call-for-call, same order**: `w C B v x u z` = engines, voice list, voice rows, mode languages, languages, mode, flags |
| `clsCLD2` — all 11 methods | all mapped. Verified this pass: `c(String)` = `firstValidCodePointU16` (whitespace, `0-9`, `h(char)`, then `codePointAt` with surrogate handling, else `-1`), `h(char)` = `isLatinPunctuation` — which **includes** backslash, unlike `cpIsAsciiPunct`, and that difference is real and deliberate; `g(String)` = the `UnicodeScript` LATIN/COMMON/INHERITED test |
| native surface | `libcld2.so` exports exactly **three** `Java_` symbols — `nativeGetLanguage`, `nativeGetLanguages`, `nativeSetLanguageHints` — and all three have counterparts. Ours exports seven because the segmenter and the ISO map moved into C++, both proven equal by harness |
| manifest, system level | the five real permissions match exactly; the two we lack (`CHECK_LICENSE`, `DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`) are the licence carve-out. `allowBackup="false"`, `installLocation="auto"`, `extractNativeLibs="true"`, the service's `accessibilityEventTypes` / `accessibilityFlags` / `canRetrieveWindowContent` / `foregroundServiceType` and `tts_engine.xml` all match |
| the reading flow | already proven by measurement, not reading: segmenter 163,296 cases, script family 15 sets x 1,114,112 code points, normaliser 1,114,112 code points |

**One real gap found, and it was ours rather than a missing port.** AutoTTS has
one settings screen that both loads and persists; we split it into four
activities that all persist while only `MainActivity` loads, which lets a
process restored at a sub-activity write default statics over real settings.
Fixed with `LangStore.ensureLoaded`, guarded on `autoLang`; see INVARIANTS #17.

**Nothing else was missing.** Do not redo this sweep; extend it instead.

---

# "Disable advanced language detection" — audited exact, 2026-08-27

Asked because it felt different from AutoTTS. It is not. Every part was checked
against the 5.7.7.26 decompile and **no divergence was found**; this is written
down so it is not re-derived.

| | AutoTTS | ours |
|---|---|---|
| static | `AutoTtsService.b0`, `b0 = true` in the static initialiser | `disableAdvancedFlag`, declared `= true` |
| pref | `getBoolean("disable_advanced_detection", true)` | same key, same default |
| label | `"Disable advanced language detection"` | same string |
| binding | `setChecked(b0)` / `b0 = bl` — **direct, not inverted** | `SettingSwitch(label, disableAdvanced)` / `disableAdvancedFlag = picked` |
| where it is read | **exactly two places, both inside `clsCLD2.d`** | `detectLanguageFull`, which is `clsCLD2.d` |

**What the flag actually does**, from `clsCLD2.d`:

    lang = nativeGetLanguage(window)
    if (lang != null && lang != "UNKNOWN") {
        if (b0) return lang;                 // <- disabled: take the answer raw
        if (n.n(lang)) return lang;          // else: must be an enabled language
        cp = clsCLD2.c(window);              // else: script family on the first
        fam = a.e(cp, n.f);                  //       meaningful code point
        ...
    }
    // no window answered:
    if (b0) return "UNKNOWN";                // <- disabled: give up
    ... whole-text script family ...

so "advanced detection" IS the enabled-language test plus the script-family
fallback, and turning it off means trust the detector. Ours is that, statement
for statement, in the same order.

**It affects auto and Google mode ONLY — in AutoTTS too.** `clsCLD2.e` (the span
detector used by mix and multilingual) and `clsCLD2.f` (the aggregate) never
read `b0`, and neither do our `detectLanguageRuns` and `detectLanguageAggregate`.
In dual mode nothing is detected at all. So in mix, multilingual and dual this
switch does nothing, on either side. That is the likeliest source of "it behaves
differently": it is expected to change something in a mode it has never touched.

**Reach is the same, three call sites versus one, and that is correct.** AutoTTS
calls `clsCLD2.d` three times: the auto/Google span loop (noexc:1630), the mix
first-chunk preflight (:1894) and `onDone`'s re-detect (:2604). The last two are
already recorded here as **dead** — every chunk in those paths carries a language
and a type of −1, so neither `isEmpty()` nor `equals("unknown")` can fire. Ours
has the one live call, in the auto/Google branch.

**The flag sits above the detector, not inside it**: the whole
disable-advanced-detection ladder wraps `detectWindowLang(text)` rather than
living in it, so it applies to the answer however that answer was reached.

**The one place the flag is passed and does nothing**: `processDirect` takes it
and `buildMixChunks` does `(void)disableAdvancedDetection;`. The segmenter never
detects, so it is inert by design — verified at the line, not assumed.

---

# Why "Remove audio attributes" adds a swipe delay — 2026-08-27

Reported from the device: with that switch on, moving element to element with a
screen reader feels slightly slower. Confirmed, and it is **AutoTTS's behaviour
too, byte for byte**.

Both build the downstream `speak()` bundle by copying the incoming
`SynthesisRequest` params and removing seven keys, then two more when the flag
is set (AutoTTS `AutoTtsService.X`, at noexc:2084 for the first chunk and :2677
for the next one; ours at `EasyVoiceTtsService.kt:1431-1434`):

    remove language, country, voiceName, variant, pitch, rate, utteranceId
    if (strip) { remove streamType; remove audioAttributes }

**Why that costs time.** The screen reader asks for speech with
`AudioAttributes` whose usage is `USAGE_ASSISTANCE_ACCESSIBILITY`. Removing them
means the downstream engine falls back to its own default, which is the media
usage. Swiping produces many very short utterances in quick succession; on the
accessibility path that output stays warm between them, while on the media path
the output can be re-routed and re-opened each time. The cost is per utterance,
which is exactly why it is felt while swiping and not on long text.

**It is not a bug to fix — it is what the switch does**, and the switch exists
because some engines misbehave with the attributes the caller supplies. The
setting that undoes the side effect is already there: **"Force to use audio
accessibility stream"** calls `setAudioAttributes(usage 11, contentType 1)` on
the engine itself (`EasyVoiceTtsService.kt:1438-1439`), so the accessibility
routing is restored at the engine even though the per-request attributes were
stripped. Strip on + force on is the combination with neither the engines'
complaint nor the delay.
