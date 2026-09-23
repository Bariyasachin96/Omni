# Invariants

Rules that must hold across the whole app. Each one is here because **breaking
it produced a real bug that was hard to find** — the point of writing them down
is that the next change can be checked against the list in a minute instead of
being discovered by ear weeks later.

Each entry says what the rule is, why, how to check it, and what it looked like
when it was broken.

**Most of these are checked automatically now:**

    tools/check/invariants.sh     # the ones a grep can decide
    tools/check/selftest.sh       # proves that checker actually fires

`invariants.sh` also runs as part of `tools/check-all.sh`. The rules it cannot
decide — #7, #9, #10, #11 and #13 — need judgement, and it says so rather than
pretending.

---

## 1. Every language-list rebuild must push the language sets

**Rule.** Any code that does `LangStore.languages.clear()` followed by
`addAll(...)` must call `EasyVoiceTtsService.pushLanguageSets()` immediately
afterwards.

**Why.** `pushLanguageSets` is AutoTTS's `s0()`. It recomputes the enabled-ISO
set from the *live* list and hands it to `NativeEngine.setLanguageHints`, which
rebuilds the two per-script tables the native detector steers CLD2 with. AutoTTS
does this at **all eleven** of its rebuild sites, inside the same
`synchronized` block, without exception. A rebuild that does not push leaves the
detector hinting at the previous list.

**Check.**

    grep -rn "languages.addAll\|languages.clear()" app/src/main/java

Every hit must have a `pushLanguageSets()` next to it. The six current sites are
`EngineFinder`'s scan completion, `LanguagesActivity`'s `loaded` block,
`ModesScreen.rebuildLanguagesFor`, `LanguagesVoicesViews.voiceLanguageLabels`,
and the service's `loadAllSettings` and `reloadLanguagesIfMissing`.
`LangStore.loadLanguages` is exempt: it is not a rebuild site of its own, and
both of its callers push.

**When it was broken.** The startup scan rebuilt the whole list and never
pushed, so on a first run the detector had no hints at all.

**The other half of the rule.** A mere *toggle* does **not** push. AutoTTS's
select-all / clear-all / row tap change the disabled flag and call `n.y()`
(persist) only. Do not add a push there.

---

## 2. Hints move only on a rebuild; detect sets move every utterance

**Rule.** `pushLanguageSets()` (= `s0()`) sends **both** the detect sets and the
hints, and may only be called where the list was just loaded or rebuilt.
`pushDetectSetsOnly()` is the per-utterance call and must never send hints.

**Why.** The hints steer the detector — CLD2 takes them as its per-script
language hint is derived from that list — so it decides which
languages may be named at all. `LangStore.languages` is a shared static that the
settings screens rebuild, and **not always whole**: `dualLangList` is two
entries, and `voiceLanguageLabels` uses `onlyEnabled = true`. Re-deriving the
hints per utterance therefore lets one visit to a settings screen leave the
detector on a two-language hint set for the rest of the process.

There is no `s0()` anywhere in AutoTTS's `onSynthesizeText`.

**Check.** `pushLanguageSets` must not appear anywhere in `onSynthesizeText` or
anything it calls per utterance.

**When it was broken.** Reading changed in every mode after one visit to the
Configuration screen.

---

## 3. Never hold `LangStore.languages` while taking another lock

**Rule.** Inside `synchronized(LangStore.languages) { … }`, do not take the
service monitor or any other lock.

**Why.** `onSynthesizeText` runs on the synthesis thread and reaches
`reloadLanguagesIfMissing`; `onLoadLanguage` is a `TextToSpeechService` override
that Android also calls on **binder** threads and which takes the service
monitor and then `languages` (inside `localeFor` / `engineFor` / `variantFor`).
Two threads taking the same pair in opposite orders is a textbook ABBA deadlock,
and when it lands **speech simply stops with no error anywhere**.

AutoTTS cannot have this: its `P()` holds only the list monitor and `e0()`
re-enters the same one.

**Check.** `tools/check/nested_locks.py`, which is brace-aware. A flat
`grep -A 12` cannot do this one: the correct shape and the deadlocking shape are
three lines apart and look almost identical —

    val missing = synchronized(LangStore.languages) { … }   // correct: read the
    if (!missing) return                                    // guard inside,
    synchronized(this) { LangStore.loadLanguages(ctx) }     // reload OUTSIDE

    synchronized(LangStore.languages) {                     // deadlock
        synchronized(this) { LangStore.loadLanguages(ctx) }
    }

— so a window-based grep reports the working code as a violation. It did.

**When it was broken.** Speech stopped intermittently with nothing in the log.

---

## 4. Settings live in statics, never re-read from prefs at runtime

**Rule.** The synthesis path reads only the companion statics. No
`SharedPreferences` read may happen per utterance, per chunk, or on any control
the user can press right after changing a setting.

**Why.** Prefs hold what was last *persisted*; the statics hold what the user
has just *chosen*. They converge only when `persistAll` runs. AutoTTS loads
every setting into a static once and the synthesis path re-reads nothing.

**Check.** Look only at the synthesis path — `onSynthesizeText` and the
`speakChunk` loop inside it, which is everything between the `SYNTHESIS` and
`SHUTDOWN` banners:

    awk '/\/\/  SYNTHESIS /,/\/\/  SHUTDOWN/' \
      app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt |
      grep -o "prefs\.[a-zA-Z]*" | sort -u

That must print **`prefs.toIso3` and nothing else** — it is a pure string
conversion and reads no preference. Everywhere else in the file, `prefs.` calls
are fine and expected: they are `loadAllSettings` and `loadModeLangsOnce`
filling the statics once.

**When it was broken.** Four times: `onLoadLanguage` reading `auto_mode_language`,
the dual branch reading `dual_mode_language`, `onSynthesizeText` re-reading the
scanned languages per utterance, and the Test path reading a locale back with
`getLocaleForLangPkg`.

---

## 4b. The settings UI reads and writes the statics, never the preferences

**Rule.** No screen may call a settings accessor on `SharedPrefsManager`
(`isKeepAliveMode`, `isDisableAdvancedDetection`, `getSmartNumberGroupSize`, and
the thirteen others). It reads `EasyVoiceTtsService.<flag>` and writes it back.
`getReadingMode` / `setReadingMode` are exempt because, despite living on
`SharedPrefsManager`, they read and write `EasyVoiceTtsService.modeInt` and
touch no preference at all. `isLoggingEnabled` is exempt because it is the one
flag AutoTTS persists eagerly (`c3.p`), and `MainActivity` seeds the logger from
it on purpose.

**Why.** This is #4 seen from the other end, and it is AutoTTS's own design:
`c3.k` assigns `AutoTtsService`'s fields directly and `c3.n.v()` persists them
later, at `onPause`. Preferences hold what was last **persisted**; the statics
hold what the user has just **chosen**; the two converge only when `persistAll`
runs. A screen that reads a flag back out of preferences therefore shows the
value from before the current edit while the service is already speaking with
the new one — the "the UI says X but it speaks Y" bug class recorded in #4.

The owner asked for it in these words on 2026-09-02: *"sab kuchh static rakho …
static wala bahut andar hi andar apply ho jata hai"*.

**Check.** `tools/check/invariants.sh` #4b, negative-tested in `selftest.sh` by
making `AdvancedScreen` read `prefs.isKeepAliveMode()`.

## 5. `announceForAccessibility` is banned

**Rule.** Never call `View.announceForAccessibility` or dispatch a
`TYPE_ANNOUNCEMENT` event.

**Why.** Android 16 deprecates both. The documented replacements are
`Activity.setTitle()` and `ViewCompat.setAccessibilityPaneTitle()` for a
significant UI change, `setAccessibilityLiveRegion()` for a critical one (used
sparingly), and `setError()` for errors.

**Check.** `grep -rn announceForAccessibility app/src/main/java` must be empty.

---

## 6. A label must never contain its own role word

**Rule.** No user-facing label or `contentDescription` may contain "button",
"tab", "switch", "checkbox", "slider", "dropdown", "menu" or "radio", and none
may contain state ("checked", "selected") either.

**Why.** The accessibility service appends the role itself, so "Main Settings
Tab" is announced as "Main Settings Tab, Tab 1 of 2". State belongs in real
semantics — `selected`, a `ToggleableState`, or `stateDescription` — which is
also what Google's `RedundantDescriptionCheck` enforces.

**Check.** `tools/check/invariants.sh`. It looks only at
`contentDescription =` and `stateDescription =` assignments, with comments
stripped — ordinary prose on screen may of course say "no voice is selected",
and several comments quote the very strings the rule forbids, because that is
where the rule is written down. A broader grep flags all of those.

**There is no exception left.** The Languages screen's filter chip used to be
carved out of this check: its visible label was "Show selected", and WCAG 2.5.3
Label in Name meant the state word could not be removed from the accessible name
without also changing the text on screen. On 2026-09-02 the owner asked for the
accessibility attributes to be put right everywhere, so both became
**"My languages"** together and the `grep -v` came out of `invariants.sh`.
A chip already publishes `selected`, so the state is announced once now instead
of twice. Do not add another carve-out: if a label genuinely has to carry a
state word, that is a wording decision and belongs to the owner.

---

## 7. A merged Compose node needs the name on the node itself

**Rule.** Any `clickable` / `toggleable` / `Button` / `DropdownMenuItem` that
merges children must carry
`Modifier.semantics { contentDescription = <label> }`, and the visible `Text`
inside it must carry `Modifier.clearAndSetSemantics { }`.

**Why.** Compose's accessibility delegate skips both the `text` and the
`contentDescription` of a node that merges its descendants *and* has children.
TalkBack walks the fake child nodes and copes; a screen reader that only
inspects the focused node announces the bare role. The owner hit exactly that:
a dropdown that read "button, button, button".

Use `clearAndSetSemantics`, **not** `hideFromAccessibility()` — that one is for
occluded content, and its own KDoc says so.

---

## 8. Never put a lazy list inside a `DropdownMenu`

**Rule.** A `DropdownMenu` may contain only a plain `Column` of
`DropdownMenuItem`s.

**Why.** `DropdownMenu` sizes itself to its widest item, i.e. it asks for an
intrinsic width, and a `LazyColumn` is a `SubcomposeLayout` that cannot answer —
it throws. The exception message suggests adding a size modifier; that was tried
and it crashed again. Because a plain `Column` supplies no collection info,
`collectionInfo` / `collectionItemInfo` are declared by hand so TalkBack can
still say "item 5 of 137".

---

## 9. A list's `collectionInfo` counts everything in it

**Rule.** A `LazyColumn` that represents a list must contain **only** the rows.
Headers, paragraphs, search fields and button rows go above it.

**Why.** `LazyLayoutSemanticState` reports `rowCount = totalItemsCount`, so a
header inside the list makes it announce the wrong count and puts every row's
index out by one. The Languages screen once reported 141 items for 137 languages.

---

## 10. Wrap, never replace, a Compose or View background

**Rule.** Do not assign a bare `GradientDrawable` to a button's background; wrap
it in a `RippleDrawable`.

**Why.** Replacing the background removes the ripple, and with it the visible
pressed and focused state that WCAG 2.4.7 asks for.

---

## 11. Do not delete an announcement because the framework "should" supply it

**Rule.** Do not remove an accessibility announcement on the theory that a
component publishes it — verify on the device first.

**Why.** The tab position was removed on the belief that Material's `TabLayout`
supplies collection info; on the owner's device nothing was announced at all.
It is back, as `"<title>, N of M"` without the word "tab".

---

## 12. The workflow file must stay far under 500 KB

**Rule.** `.github/workflows/build.yml` must remain small.

**Why.** GitHub's limit is **512,000 bytes per workflow file**, and over it a run
is **created and numbered but never parsed**: it sits `queued` forever with zero
jobs, never reports a `startup_failure`, and the cancel endpoint answers HTTP
500. There is no error message anywhere. This cost two separate debugging
sessions.

**Check.** `wc -c .github/workflows/build.yml` — currently about 11 KB, and now
that the sources are checked in as real files rather than embedded in a
generator there is nothing that can grow it.

---

## 17. An activity that persists must load first

**Rule.** Any activity whose `onPause` calls `LangStore.persistAll` must call
`LangStore.ensureLoaded(this)` in `onCreate`.

**Why.** AutoTTS has one settings screen, `NewSettingsActivity`, and it does
both: `onCreate` loads, `onPause` calls `c3.n.t`, which is `persistAll`
call-for-call and in the same order (`w`, `C`, `B`, `v`, `x`, `u`, `z`). We
split that screen into four activities, **all four persist and only
`MainActivity` loads**. That asymmetry is ours, not AutoTTS's.

Android restores the **top** activity of a task after the process is killed, not
the whole stack, so `ModeSettingsActivity`, `LanguagesActivity` or
`VoiceSetupActivity` can each come back in a process where nothing has loaded —
`modeInt` 0, every mode language `""`, every Advanced flag `false`, all at their
declared defaults. Their `onPause` would then write **all of that** over the
user's real settings. A silent wipe, on the owner's only TTS engine.

It has not bitten constantly only because the TTS service usually lives in the
same process and its `onCreate` has already run `loadAllSettings`. That is luck,
not design.

**The load must be guarded, not unconditional.** Reloading over a *live* edit is
its own bug — the one where the service kept routing to the previously stored
language while the UI showed the new one (see #4). `ensureLoaded` returns
immediately when `EasyVoiceTtsService.autoLang` is non-empty, which is exactly
the marker `loadModeLangsOnce` already uses: it is `""` only before anything has
loaded, and both loaders end with `ifEmpty { deviceIso3 }`, so it can never be
empty afterwards.

**Check.** `tools/check/invariants.sh` #17.

**A checker asleep, caught by the selftest.** The first version of this check
grepped the file without stripping comments, and the line above each call reads
"see `LangStore.ensureLoaded`" — so deleting the call left the comment matching
and the check still said ok. It strips comments and counts matches now, like #6.
That is the second time this exact trap has been hit; `selftest.sh` found both.

---

## 16. CLD2 is the only detector — CLD3 must not come back

**Rule.** No `cld3*`, `useCld3*`, `NNetLanguageIdentifier` or `isRomanisedTag`
symbol in `tts_engine_core.cpp` or the service, and no `cld3` / `protobuf` /
`protoc` in `CMakeLists.txt` or `build.yml`. Both halves are checked by
`invariants.sh` and both are negative-tested in `selftest.sh`.

**Why.** CLD3 was removed on 2026-09-02 at the owner's instruction, after they had
tested it on their own device for days. This invariant used to say the opposite
thing — that an unreliable CLD3 answer must never reach a span — and it grew three
guards of its own (a reliability flag, a wrong-script rejection, a squeeze gate).
All three are gone with the arm they protected.

**The measurement that ended it**, twelve real Hindi and Marathi sentences cut to
rising byte prefixes through `FindLanguage`:

| bytes | Hindi right | Marathi right | combined |
|---|---|---|---|
| ≤20 | 33% | 67% | **50%** |
| ≤40 | 50% | 50% | **50%** |
| ≤60 | 50% | 83% | **67%** |
| ≤80 | 83% | 100% | **92%** |
| ≥100 | 100% | 100% | **100%** |

Below about 80 bytes CLD3 is a coin toss between the two, **in both directions** —
a real Marathi label is answered Nepali as readily as a Hindi one is answered
Marathi. UI labels and button names, which is most of what a screen reader speaks,
live entirely in that range. CLD2 scores 24 of 25 on the same corpus at every
length. Seven attempted fixes were measured and every one made some user worse;
they are written up in CLAUDE.md under the three CLD3 report sections.

**Do not reintroduce it to "give the user the choice".** The choice was measured
and it was a bad one, and the switch cost the APK the protobuf runtime, nineteen
translation units, a protoc download per CI run, and 12x on the detection path.

---


## 20. The span emitter has three parts nobody would guess from the Java

**Rule.** `emitScriptSpan` must keep all three of these. They are not visible in
any `.java` file — `clsCLD2.nativeGetLanguages` is a `native` declaration, and
everything below lives in `getLanguageSpans` at **0x65392c** in
`lib/arm64-v8a/libcld2.so`.

**1. Script 0 falls back to the LATIN language, not to `"un"`.** Case 0 of the
26-entry jump table starts at `"un"` with `latin = TRUE`, and then:

```
653f00: ldr  w8, [x8, #0x2b4]   ; how many hint codes are set
653f04: cmp  w8, #0x1
653f08: b.lt 0x6544c0           ; none -> keep "un"
653f10: ldr  w0, [x8, #0x564]   ; scriptLanguageFallback[1], the LATIN slot
653f14: cmp  w0, #0x1a          ; UNKNOWN_LANGUAGE
653f18: b.eq 0x6544c0           ; no Latin language enabled -> keep "un"
653f34: bl   CLD2::LanguageCode
653f50: mov  x19, x0            ; the span's language is that code
```

Script 0 is a run in which nothing was classified: digits, ASCII punctuation,
spaces, emoji. So a bare number, a bare punctuation run and a bare emoji are
**named after the first enabled Latin language**, and `latin` stays true either
way. Answering `"un"` there is not equivalent: an unnamed span is resolved by
the caller with the *preferred* Latin language, a named one goes through
`c3.e.c` and the engine check first.

**2. The CLD2 answer filter tests FOUR candidates, not three.** In order: the
value `ExtDetectLanguageSummary` returns, then `language3[0]`, `[1]` and `[2]`,
each of the last three only when it is a real language covering at least one
percent. The compiler unrolled the loop into three identical blocks at
`0x654190`, `0x6542ac` and `0x654380`, reading `language3[0..2]` from
`x29-0x14/-0x10/-0xc` and `percent3[0..2]` from `x29-0x20/-0x1c/-0x18`.

Rank 0 is **not** a repeat of the summary above it. `CalcSummaryLang` in
`compact_lang_det_impl.cc` returns `language3[active_slot[1]]` when it decides
the top answer is English or FIGS boilerplate, and `UNKNOWN_LANGUAGE` when the
top language covers less than `kGoodFirstMinPercent` (26) of the text. In both
cases the summary can be a language the user has not enabled while
`language3[0]` is one they have, and skipping rank 0 sent the span to the
per-script fallback instead.

**3. `setLanguageHints` skips a code it cannot store, and stops at 64.**
`0x6535f4`: `sub x8, x0, #0x8 / cmn x8, #0x7 / b.lo <skip>` keeps a code only
when `1 <= strlen <= 7` — it has to fit an 8-byte slot with its NUL — and a
longer one is **skipped, not truncated**. `0x65373c` stops the loop once 64 have
been kept. Our comma list, which the per-script hint tables are derived from, is built
from the same accepted codes so the two detectors are steered by one list.

**4. In the script ladder, an ASCII letter means Latin and anything else means
"no information".** `0x653ae8`: `and w8, w8, #0x5f / sub w8, w8, #0x5b /
cmn w8, #0x1a / b.lo 0x653b88`, where `b.lo` is taken when the folded byte is
**outside** `[0x41, 0x5A]` and `0x653b88` is `mov w27, w3` — keep the current
script. A letter falls through to `mov w27, #1`. Ours had the ternary the wrong
way round. The branch is reachable only through an **overlong** UTF-8 sequence,
which is not hypothetical: `GetStringUTFChars` hands out modified UTF-8, where
U+0000 is `C0 80`. `utf16to8` emits that form too, for the same reason.

**5. The enabled ISO-2 set is ONE `HashSet` for the life of the process.**
`c3.n.f` is created once and `clear()`ed and refilled by `s0()`, never replaced.
A `HashMap`'s table never shrinks on `clear()`, so the iteration order of a
later refill depends on how large the set has ever been — and that order is what
decides **which 64** codes survive the cap in part 3 when more than 64 languages
are enabled. A fresh `HashSet` per call would always iterate at the small table
size and keep a different 64. `EasyVoiceTtsService.enabledIso2` is that set.

**Check.** was `tools/verify/cld3span/run.sh`, which went with CLD3 on 2026-09-02.
It was negative-tested by building the
harness with each change reverted:

| reverted | what the harness reports |
|---|---|
| script 0 | 6 failures, e.g. `bare number, en enabled: span 0 is un/latin=1, expected en/latin=1` |
| rank 0 | `French text, es+fr enabled: span 0 is "es", expected "fr"`, and three more |

The four rank-0 cases were **found** by building both ways and diffing, not
invented: with `{es, fr}` enabled, a French paragraph with a Dutch tail was
spoken in **Spanish** before the fix and in French after it.

---

## 21. `e0` reads until the first empty key, and `f0` always reaches setLanguage

**Rule.** `LangStore.loadLanguages` stops at the first empty `language_N` and
always replaces the list. `loadVoice`'s `setLanguage` fallback runs whenever no
voice matched the variant, not only when the engine reports no voices at all.

**Why, for the loader.** `AutoTtsService.e0()` is nine lines: read
`language_0` upward, break on the first empty string, then
`synchronized (c) { c.clear(); c.addAll(list); s0(); }`. Three things had been
added to ours that it does not have, and two of them were doing damage:

| added | what it cost |
|---|---|
| `while (index < 64)` | a device with more than 64 scanned languages lost every one past the 64th. The **service** reads this list, so `engineFor`, `n.n()` and the detect sets all missed them — and the next `persistLanguages` wrote the truncated list back over the stored one. |
| `emptyRun < 8` | reading past the terminator to tolerate a gap `persistLanguages` never leaves. When the list SHRANK, the stale keys of the longer one were still in prefs, and this read them back in. |
| two "keep the previous state" returns | `e0` clears and replaces even when it parsed nothing. |

None had a comment or a note anywhere; all three came in with the original
import.

**Why, for `f0`.** Its voice loop leaves with `break block32` only when a voice
**matched** the variant — success or failure alike. A list that contains no
voice by that name falls straight through to

```java
if (!this.Z(locale, object4)) { ... setLanguage(locale) ... }
```

and so does a null list. Ours had made that fallback the `else` of
`voices != null`, which cut off the first of the two cases: with a stored
`_variant` naming a voice the engine no longer has — renamed or dropped by an
engine update — AutoTTS still moves the engine to the right **language**, and we
left it wherever it happened to be.

**And `g0`.** It compares the wrapper's locale unconditionally; the wrapper's
locale can be null, and `n.e(null)` is `"zxx"` while `n.d(null)` is `""`.
Skipping the comparison on null is not the same test.

**Check.** Not grep-able — read this entry. The loader is nine lines; keep it
that way.

---

## 22. A popup is not checked unless a test opens it

**Rule.** Every menu, dialog and conditional block of the UI needs its own
`AccessibilityChecksTest` case. Rendering a screen checks only what is on it.

**Why.** `enableAccessibilityChecks()` runs Google's Accessibility Test
Framework, but only over the view that is actually there. A `DropdownMenu` is a
separate window that does not exist until something opens it, so
`onRoot().tryPerformAccessibilityChecks()` cannot see it — and the language
dropdown is the control a blind user spends the longest in, because it can hold
every language the installed engines speak.

The way in is that the checks also run **before every action performed through
the test API**. So a click on the anchor checks the screen behind the menu, and
a click on something *inside* the open menu checks the menu's own view:

```kotlin
rule.onNodeWithContentDescription("Select secondary language, Hindi (hin)").performClick()
rule.onNodeWithContentDescription("Gujarati (guj)").performClick()   // checks the OPEN menu
```

Four cases cover what was invisible: the language dropdown, the specific-language
dropdown (a screen **state** — it only exists when the mode int is 3, and none of
the four mode-settings cases render it), the per-language overflow menu, and the
required-engines dialog.

**The seed must reset the mode ints.** They are companion statics and survive
from one test to the next in the same process, so without a reset the tests
depend on the order JUnit happens to pick.

### What was checked by hand in the same pass, and is correct

- **`collectionItemInfo` is always the caller's job.** `LazyLayoutSemantics`
  sets `collectionInfo` on the lazy layout node and nothing else — the file
  contains no `collectionItemInfo` at all. So the hand-written per-row info in
  both the Languages list and the dropdown is required, and the section headings
  inside that `LazyColumn` do **not** claim a row of their own.
- **`collectionInfo` is also what makes a list escapable.** TalkBack's
  `Role.java` resolves a node carrying collection info to `ROLE_LIST`, which is
  in `FILTER_CONTAINER` and in the auto-scroll set, so container navigation can
  jump out of 137 rows.
- **Touch targets**: the only explicit sizes under 48dp in the whole UI are
  decorative icons inside larger controls — the 18dp chip check mark and the
  32dp app-bar image, neither of which is clickable.
- **`isTraversalGroup`** is true by default on scroll containers and Material
  surfaces, so the single-column screens need nothing.

---

## 23. Window size classes come from ONE place, and never from the device

**Rule.** Ask `evWindowSizeClass()` in `ComposeTheme.kt` and pass the answer down
as ordinary state. Never branch on `screenWidthDp`, a device type, an
orientation, or a hard-coded dp number.

**Why.** The adaptive guidance is explicit: *"Avoid using physical hardware
values for making layout decisions… the physical screen size isn't relevant"* —
split-screen, desktop windowing and a folded inner display all give the app less
than the screen — and *"a layered approach confines display size logic to a
single location instead of scattering it across your app in many places that
need to be kept in sync."*

**Use `currentWindowAdaptiveInfoV2()`, not `currentWindowAdaptiveInfo(...)`.**
The developer.android.com page still shows the older one with a
`supportLargeAndXLargeWidth` flag; androidx's own `api/current.txt` marks that
overload `@Deprecated` and lists V2 as the replacement. It landed in adaptive
`1.3.0-alpha10` and is in `1.3.0` stable, which is what is declared. **Read the
API file, not the doc page** — that is how this one was caught.

The breakpoints are named constants, never literals:

| constant | dp |
|---|---|
| `WIDTH_DP_MEDIUM_LOWER_BOUND` | 600 |
| `WIDTH_DP_EXPANDED_LOWER_BOUND` | 840 |
| `WIDTH_DP_LARGE_LOWER_BOUND` | 1200 |
| `WIDTH_DP_EXTRA_LARGE_LOWER_BOUND` | 1600 |
| `HEIGHT_DP_MEDIUM_LOWER_BOUND` | 480 |
| `HEIGHT_DP_EXPANDED_LOWER_BOUND` | 900 |

**`Dp.Unspecified` cannot be compared with `==`.** It is `Dp(Float.NaN)` and
`Dp` is a value class whose `equals` compares the floats, so `x == Dp.Unspecified`
is **always false** — NaN never equals NaN. The idiomatic test is `isUnspecified`;
`evContentMaxWidth()` returns a nullable `Dp` instead, which needs no trap
knowledge at all. This was written the wrong way once and caught before it shipped.

**Why the content column does NOT grow past 840dp.** It is a single-column
reading measure. Material's answer to a wider window is a second **pane**, not a
longer line, and stretching one settings row across a 1600dp desktop window is
the thing the large-screen guidance warns about. If a two-pane layout is ever
wanted, that is a navigation change and needs the owner's decision first,
because it changes how the screen reader traverses the app.

---

## 24. `TabRow` is deprecated — the app's tab strip is `PrimaryTabRow`

**Rule.** Use `PrimaryTabRow` for the app's top-level destinations, and
`SecondaryTabRow` only for tabs nested inside one of them.

**Why.** In material3 **1.4.0** — the version Compose BOM `2026.08.00` pins —
`TabRow` has exactly **one** overload and it is `@Deprecated`, while
`PrimaryTabRow` and `SecondaryTabRow` are current. Checked against
`compose/material3/material3/api/1.4.0-beta01.txt`, not against androidx-main,
which is a later version and deprecates things ours has not reached yet.

**Everything else we call is current, and the rest of the "deprecated" counts in
that API file are a trap.** `Text`, `IconButton`, `TopAppBar`, `DropdownMenu` and
`CircularProgressIndicator` each list deprecated overloads, but those are
**binary-compatibility shims** — the previous parameter lists, kept so old
bytecode still links, hidden from Kotlin source. A call using named arguments
binds to the current overload on its own. Do not "migrate" them.

Checked and current in 1.4.0: `Button`, `OutlinedButton`, `TextButton`,
`IconButton`, `Icon`, `Text`, `Surface`, `Scaffold`, `TopAppBar`, `Tab`,
`ListItem`, `Checkbox`, `RadioButton`, `Switch`, `Slider`, `FilterChip`,
`AlertDialog`, `DropdownMenu`, `DropdownMenuItem`, `OutlinedTextField`,
`ExtendedFloatingActionButton`, `CircularProgressIndicator`.

---

## 15. Two things look dead to a text scan and are not

**Rule.** Never delete these on the strength of a grep.

**`import androidx.compose.runtime.getValue` / `setValue`.** Fourteen of these
across seven files, and no occurrence of either name anywhere in the bodies.
They are the **operator imports Kotlin needs for property delegation** — every
`var x by remember { mutableStateOf(…) }` compiles to `getValue`/`setValue`
calls the compiler resolves through the import, not through anything written in
the file. Removing them does not tidy the file; it fails the build. The seven
files use `by remember` between 1 and 12 times each.

**A parameter used only inside a string template.** `openPlayStoreFor(pkg)`
reads `pkg` only in `"market://details?id=$pkg"` and the https fallback, so any
scan that blanks string literals before counting identifiers reports the
parameter as unused. It is not.

**Why this is written down.** A dead-code sweep on 2026-08-26 produced exactly
these two as its only "findings" outside real dead code. Both were false
positives from the same cause — the scanner strips comments and strings so that
braces and prose cannot skew the counts, which is right for finding dead
functions and wrong for anything the compiler resolves implicitly or that lives
inside a `"…"`. The genuinely unused import that same sweep found,
`android.text.Spanned`, was removed only after `tools/check/kotlin-typecheck.sh`
showed 1135 errors before and 1135 after, with no new error text.

---

## 14. A log tag is `EasyVoiceLogger.TAG`, or `"TTS"` at six sites

**Rule.** Every `EasyVoiceLogger` call passes `EasyVoiceLogger.TAG`, except the
six that pass the literal `"TTS"`.

**Why.** AutoTTS logs under exactly two tags, counted across the whole 5.7.7.26
decompile: `"AutoTTS"` at 133 call sites, and `"TTS"` at six — the three
audio-focus lines (`AutoTtsService:211,214,217`), the focus request
(`:1495`), and the two notification-permission lines (`c3/k.java:160,163`).
Ours mirrors all six message-for-message. `EasyVoiceLogger.TAG` is `"EasyVoice"`,
our counterpart of `"AutoTTS"`, so any *other* literal is a mistake.

**Check.** `tools/check/invariants.sh` #14, comments stripped.

**When it was broken.** `LangStore.localeFor` and `variantFor` shipped
`debug("TAG", …)` — the name of the constant, written as a string. AutoTTS
passes `"AutoTTS"` at both (`AutoTtsService.T` and `.U`), so this was a plain
typo, and it wrote the word `TAG` into the log file the owner attaches when
reporting a problem.

---

## 13. Do not "improve" a copied AutoTTS quirk

Several oddities are deliberate. Removing them is a behaviour change:

- the variant list sorts `[1, n-1)`, leaving the **last** entry unsorted;
- Google mode's language spinner uses a filtered adapter with an unfiltered
  index, so the selection is off by the filter;
- "Select all" ticks the filtered view but clears `disabled` on the whole list;
- the smart-number keyword set is built once per process and **never** rebuilt,
  because AutoTTS's `d0.j` is assigned in exactly two places and nothing clears
  it;
- `detectLanguageAggregate` uses a plain `HashMap`, because a tie is settled by
  bucket order and AutoTTS gets `HashMap`'s, not insertion order;
- `commit()` rather than `apply()` in the storage code.

The two deliberate **departures** from AutoTTS are the whole user interface
(the owner's decision) and the `_disabled` default, which starts languages
cleared instead of ticked.

---

## 18. A heading is a plain `Text`; never give a `Text` a `contentDescription`

**Rule.** A heading is exactly
`Text(…, modifier = Modifier.semantics { heading() })` — the shape
developer.android.com's semantics page gives as its own example. Do not merge
it, and do not add a `contentDescription`.

**Why.** A `Text` is a **leaf**. Compose's delegate calls `setText(node, info)`
unconditionally, so the node already carries its name and is already focusable;
merging a leaf buys nothing. And on `contentDescription` the api-defaults page
is explicit: it *"is mainly meant to be used for graphic elements, such as
images. Material components, like `Button` or `Text`, and actionable behaviors,
like `clickable` or `toggleable`, come with other predefined semantics"*. In the
View API a `contentDescription` **overrides** the text, so on a `Text` it can
only replace a label that was already correct.

**The opposite case is #7, and it is not this one.** A node that merges
descendants *and* has children has its `contentDescription` moved into a fake
leaf child, and `info.text` read from an unmerged config that is empty — so
there the explicit name is required, and that one was verified on a real device
and by Google's Accessibility Scanner. Leaf and merged-parent are opposite
cases. Do not apply either rule to the other.

**When it was broken.** On 2026-08-27 the owner reported that headings were
spoken but never took focus. I diagnosed it as a merging problem, merged every
heading at the `Surface` and gave each a `contentDescription` — a fix aimed at
a cause I had not verified, which broke this rule everywhere at once. Reverted
the same day after reading the guidance, which prescribes the plain form the
code already had. **The focus report itself is still unexplained and still
open**: it must be reproduced on the device before anything is changed again,
which is #11's rule and was exactly what I skipped.

**Check.** `tools/check/invariants.sh` #18 parses each `semantics { … }` block
and reports any that sets both `heading()` and `contentDescription`.
Negative-tested in `selftest.sh`.

---

## 19. The two accessibility warnings that are accepted, and why

`enableAccessibilityChecks()` throws on **ERROR** results only — that is the
framework's own default, not a setting of ours. Two of the thirteen ATF checks
report **WARNING** on this app, deliberately, and both were audited rather than
waved through.

**`DuplicateSpeakableTextCheck` on the mode-int radios.** Three radio groups on
a mode's settings screen — numbers, punctuation, emojis — each offer the same
four options, so twelve clickable rows carry four speakable names between them.
The check's own source says it warns when "two Views with the same text, and at
least one of them is clickable"; it has no way to see that each group sits under
its own heading. Naming the rows "Numbers, Auto language" and so on **does**
silence it, and that is exactly what the owner used and rejected on 2026-08-27:
with a heading already above each group, repeating the group name on all four
rows is noise. A radio group whose options repeat is the canonical case this
check cannot judge. The heading stays, the prefix does not.

**`RedundantDescriptionCheck` on "Show selected" -- CLOSED 2026-09-02.** The
chip's visible label contained "selected" while the chip also published the
`selected` state, so TalkBack said it twice; WCAG 2.5.3 Label in Name meant it
could not be fixed by editing the accessible name alone. The owner asked for the
accessibility attributes to be put right everywhere, so the visible label and the
name changed together to **"My languages"** -- which also describes what the
filter leaves on screen, where "Show selected" never did. This was the last
outstanding accessibility warning in the app.

**Everything else was measured, not assumed** (2026-08-27):

| check | result |
|---|---|
| `SpeakableTextPresentCheck` (the only ERROR-level one that could plausibly fire) | **36 of 36** clickable / selectable / toggleable nodes carry a name |
| `TouchTargetSizeCheck` | passes. M3 `Button` is 40dp tall but every one is built on the clickable `Surface`, whose modifier chain **begins** with `minimumInteractiveComponentSize()`, so the a11y bounds are 48dp. Our own rows are larger still: the radio rows are a `RadioButton` plus 24dp of vertical padding, and the list rows are `ListItem` at 56dp |
| `TextContrastCheck` | every text-on-background pair computed: 18.73, 16.48, 14.63, 14.64, 7.21, 10.31, 5.45, 16.48 — all above 4.5 |
| non-text contrast (WCAG 1.4.11) | control outline 11.02, tab divider 3.70, selected chip fill 3.44, switch thumb 7.21 — all above 3.0 |
| `EditableContentDescCheck` | the Languages search field carries no `contentDescription`; never add one |
| `TraversalOrderCheck` | no `traversalIndex` or `traversalBefore/After` anywhere, so there is no order to contradict |
| `ClickableSpanCheck`, `LinkPurposeUnclearCheck` | no links or spans in the app |

**One measurement that is a design question, not a violation.** The
`SectionHeader` bar (`primaryContainer` `#1B2A38`) is **1.28:1** against the page
(`#121212`). ATF does not flag it — `TextContrastCheck` compares text against
its own background, and the heading text on that bar is 14.64:1. The bar is a
grouping fill whose meaning is carried by the text on it, so it is the same
exemption already recorded for `surface` against `background`. Raising it would
make the header bars visibly lighter across the whole app, which is the owner's
call, not a correctness fix.

## 26. The utterance id handed to an engine must be unique per utterance

`speakChunk` builds it as

    val expectedId = "${utteranceId}_${myGeneration}_${chunkCounter}"

and the middle field is the whole point. Without it the id was
`"${utteranceId}_${chunkCounter}"` and **could not tell two utterances apart**:
`utteranceId` is a static read from the caller's params and is literally the
string `"null"` when the caller sets none, and `chunkCounter` is reset to 1 for
every utterance by the `if (currentChunk == 1) chunkCounter = 1` line directly
above. Two consecutive screen-reader utterances, which are one chunk each, both
spoke under `"null_1"`.

**Why it matters.** `setOnUtteranceProgressListener` is ONE volatile field per
`TextToSpeech` and the framework reads it **at dispatch time**, so when a screen
reader interrupts, the OLD utterance's callback is delivered to the listener the
NEW utterance installed -- carrying the new utterance's `callback` and
`chunkQueue` in its closure. `onStart`, `onDone` and both `onError`s therefore
begin with `if (id != expectedId) return`, and that guard is only correct
because the id is unique.

This was shipped once WITHOUT the generation, on 2026-09-03, and it broke
explore-by-touch outright: the stale callback matched and killed the utterance
that had not spoken yet. The check was right; the id was not.

**The id survives the round trip and an engine cannot change it** --
`TextToSpeech.speak()` passes it as its own AIDL argument rather than inside
`params`, and `TextToSpeechService` keeps it in
`UtteranceSpeechItemWithParams.mUtteranceId`, `protected final` on a private
framework class that every `dispatchOn*` reads. So a live callback always
matches and the guard can never hang the wait.

**The id is not enough on its own.** `onDone` posts the next-chunk step to the
main looper, and an interrupt in between means that runnable runs after the
utterance ended -- the id was true when we posted. So `synthesisGeneration` is
also compared at the top of `speakChunk`, the one place that pops `chunkQueue`
and speaks, and inside the posted runnable. **Any new callback or posted step on
the speech path needs one of the two guards**: the id where the engine hands it
back, the generation where it does not.

## 27. Every engine init owns its client

`TextToSpeech(ctx, listener, pkg)` is constructed at three sites -- twice in the
pool walk, once in `restoreEngine` -- and each passes a fresh
`arrayOfNulls<TextToSpeech>(1)` that its listener captures. **Never put the
in-flight client back into a shared field.** It used to be one
`initializingTts`, written by the pool walk AND by `restoreEngine` with no
mutual exclusion between them (both methods take `this`; neither `onInit`
does), so an engine dying mid-walk made the next `onInit` store the wrong
client and route one engine's utterances to another's voice.

The holder is a one-element array rather than a `val` because `onInit` can fire
**inline on the constructing thread** -- but only ever with ERROR, since the only
dispatch that can carry SUCCESS is inside
`SetupConnectionAsyncTask.onPostExecute`, which is always asynchronous. On the
inline ERROR path `cell[0]` is still null, and that is correct: the wrapper is
being marked `state = -1` and must not be handed anybody's client.

`EngineFinder.startEngine` uses the identical shape for the identical reason.

## 28. The user interface is PURE Jetpack Compose

Owner, 2026-09-10: *"full Jetpack Compose user interface chahie, koi XML Android
view ya fir kuchh bhi nahin."* It already is, and this rule exists so it stays
that way: the way it would come back is **one `AndroidView()` in one screen**,
and nothing on the screen would look wrong.

`invariants.sh` #26 fails the build on any of:

    res/layout*/                 an inflatable View layout -- no such directory
    android.view.*               ANY of it -- there is currently not one
                                 reference in the app or the test sources
    android.widget.*             except Toast (see below)
    setContentView( findViewById LayoutInflater AndroidView( ComposeView
                                 the four ways a View gets into a Compose tree
                                 or a Compose tree into a View one

Comments are stripped first, and that is **not** optional: `ComposeTheme.kt` and
`MainActivity.kt` explain in prose what the accessibility delegate writes into
`info.className`, and the words `android.widget.Button` in a sentence are not a
widget. Both cases are negative-tested in `selftest.sh` -- an added
`AndroidView`, and an added `res/layout/leak.xml`.

**`Toast` is the one exception, and it is deliberate.** Since API 30 a custom
toast *view* is deprecated and the system renders text toasts in its own
process, so `Toast.makeText(...).show()` is a system call like posting a
notification rather than a View this app inflates. There are five, and every one
of them is AutoTTS-mirrored feedback (the slider's `"<n> of <max>"`, the battery
hint, the scan failure, the Play Store fallback, the import and export errors).
**Moving them to a Compose `Snackbar` is possible under the UI carve-out and is
NOT free**: a `Snackbar` needs a `SnackbarHost` in scope, so `EngineFinder`'s --
which fires from an object with no composition around it -- would have to be
re-routed, and it changes what a screen reader announces on the feedback path
this project has already broken twice. Owner's call, not a tidy-up.

### What is XML and CANNOT be Compose, each verified rather than assumed

| file | what it is | why it stays |
|---|---|---|
| `res/drawable/*.xml` (22 icons) | icon **resources**, drawn by `Icon(painterResource(...))` | **Google's own current guidance.** developer.android.com's Compose images page says `material-icons` is *"no longer maintained or recommended ... contains an older look and feel and can also increase the build time of your apps significantly"* and recommends downloading the XML from fonts.google.com instead. Ours already are Google's own paths. |
| `mipmap-anydpi-v26/ic_launcher*.xml`, `drawable/ic_launcher_monochrome.xml` | adaptive and themed launcher icons | the **launcher** reads them, in another process |
| `values/styles.xml`, `values-night/styles.xml` | the **window** theme and the splash | the framework reads it at `setTheme` time, **before any composition exists** |
| `values/colors.xml` | feeds those themes | same |
| `values/strings.xml` | one string, `app_name` | read by the manifest and the launcher |
| `res/xml/tts_engine.xml` | `<meta-data android:name="android.speech.tts">` | **required** by `TextToSpeechService` |
| `res/xml/provider_paths.xml` | | **required** by `FileProvider` |
| `res/xml/data_extraction_rules.xml` | | **required** by the backup manager |
| `AndroidManifest.xml` | | required by Android |

None of those is a **View**. Every one is read by a platform component outside
this app's composition, and there is no Compose API that can replace any of
them.

## 29. Nothing may escape a framework entry point on the speaking path

Checked by `tools/check/invariants.sh` **#27**, negative-tested three ways in
`selftest.sh`.

**Written after the owner reported it for the fourth time** (2026-09-11:
*"achanak se bolna band ho jata hai"*). The first three rounds each found a
specific hole and closed it — the three exits that ended an utterance without
waking the wait, the terminal `state = -1`, and the utterance id that was not
unique. This one is not a hole, it is the class: **on the speaking path an
escaped `Throwable` is fatal rather than annoying**, and the three places the
framework calls into us are where it escapes from.

| entry point | what an escape does |
|---|---|
| **`onSynthesizeText`** | AOSP calls it from `SynthesisSpeechItem.playImpl()`, which `SpeechItem.play()` calls from a `Runnable` on **`SynthHandler` — a plain `HandlerThread` with no catch anywhere above it**. An uncaught `Throwable` there goes to the default handler and **kills the engine process**. The screen reader's `TextToSpeech` then loses its binding and stays mute until it re-initialises. |
| **`onStop`** | its last two lines are what unpark the screen reader's **one** synthesis thread. An escape above them leaves that thread with nothing left that can wake it — the device silent until our process is killed. |
| **the four `ServiceConnection` callbacks** | Android delivers every one on the **main looper**, so an escape is an uncaught exception on the main thread and therefore a process death. `onServiceDisconnected` is the worst: it both unparks a waiting synthesis thread (`onEngineProcessGone`) and recovers the engine (`restoreEngine`). |

So the shapes are fixed and the check enforces each one:

    onSynthesizeText   the override is NOTHING but a wrapper --
                       onSynthesizeTextImpl(...) inside try/catch (ex: Throwable)
    onStop             the two release lines are in a `finally`
    the four callbacks each carries its own `{ try { ... } catch (ex: Throwable)`

**`Throwable`, not `Exception`, and that is not fussiness.** The failures that
actually reach these points are **Errors**: `NoClassDefFoundError` from an API
above `minSdk` (two of those were real crashes, found 2026-09-10),
`OutOfMemoryError` raised by the JNI guards added the same day, and
`ArrayIndexOutOfBoundsException`/NPE from the races closed on 2026-09-10 and
2026-09-11. `catch (Exception)` would have held **neither of the first two**.

**Why this cannot be the "defensive" justification rule 5 forbids.** The test the
owner set for `releaseWaitWithoutSpeaking` applies unchanged: a guard that can
only fire on a path where speech has *already* become impossible costs nothing on
the happy path and cannot cut a live utterance short. Every one of these catches
does exactly what the neighbouring "this utterance cannot speak" exits already
do — release the wait, finish the callback — and none of them can run while
speech is healthy.

**The same rule extends to `EasyVoiceLogger`, and that one is not grepped.**
`writeLine` is reached from every thread in the app **including from inside these
catches**, so a throw escaping the logger would defeat the very guard that called
it. Its catch is `Throwable`, `rotate()` is inside the guard rather than beside
it, and `init()` — the first line of the service's `onCreate`, before
`super.onCreate()` — is guarded too. A diagnostic that can break the thing it is
diagnosing is worse than no diagnostic.

---

## 30. A flag that describes the `TextToSpeech` CLIENT must not outlive it
*(`invariants.sh` #28, negative-tested three ways in `selftest.sh`)*

`EngineWrapper` has two kinds of field. Some describe the **engine package** —
`pkg`, `state`, `restoreCount` — and survive anything. Five describe **the
`TextToSpeech` object currently in `tts`**:

    voicesCache        the voice set marshalled out of THAT connection
    currentVoice       what mParams[KEY_PARAM_VOICE_NAME] resolves to on it
    currentVoiceKnown  whether we know the above without asking
    audioAttrSet       our accessibility attributes are in ITS mParams
    localeSet          setLanguage or setVoice has succeeded ON IT

Replace `tts` and every one of those becomes a statement about an object that no
longer exists. **A new `TextToSpeech` is a new binder connection with empty
`mParams`**, so none of them can be carried over.

**The rule, and it is one grep:** `tts` is assigned nowhere without
`forgetClientState()` beside it, and `forgetClientState()` clears the four that
belong to it. There are four assignment sites — both init listeners, both
restore branches.

**`audioAttrSet` is the deliberate exception, and the ORDER is why.** Both
listeners set it to `true` — inside the `forceAccessibilityFlag` branch, on the
new client — immediately *before* they assign `tts` and call
`forgetClientState()`. Clearing it in there would therefore undo the value just
written and make the force switch dead on that engine, which is the exact bug
that clearing it was added to fix. It is reset at both listeners instead, and
`RestoreInitListener` sets it `false` before the status branch so the failure
path is covered too.

### Each field that was missed cost a real bug, and they got worse
- **`voicesCache`** (2026-09-02) — a stale list meant scanning the *old*
  connection's `Voice` objects. Wrong voice.
- **`audioAttrSet`** (2026-09-03) — stale `true` made the speak path skip
  `setAudioAttributes` on a client that had none, so "Force accessibility
  stream" was **dead on that engine for the life of the process**. AutoTTS
  leaves its `k0.h` stale here as well; clearing it was a recorded DELIBERATE
  DEPARTURE.
- **`localeSet`** (2026-09-16) — the worst, and the reason this is a rule rather
  than three separate fixes.

### Why `localeSet` was permanent silence rather than a wrong voice
`loadVoiceDedicated` opens with

    if (dedicated && wrapper.localeSet) return

which is AutoTTS's `h0` inverted (`if (!bl || !((k0)f.get(d)).f)`, noexc:1023)
and **stays** — the guard is parity. What was wrong is that the restore listener
cleared `voiceName`, `locale` and `audioAttrSet` and **not** `localeSet`, so a
freshly restored client arrived claiming a language had already been loaded into
it.

The damage is not that the language is skipped. It is that the guard returns
**before any `setLanguage` or `setVoice`** — and those two calls are the only
things in the app that can notice a client is dead, because their failure branch
is the only caller of `restoreEngine`. So:

| path | after a bad restore |
|---|---|
| `loadVoice` (dedicated OFF) | calls `setLanguage`, it fails, `restoreEngine` runs again — **self-healing** |
| `loadVoiceDedicated` (ON) | returns at the guard, calls nothing, notices nothing — **wedged** |

The wrapper stays at `state == 2` holding a client that can never speak,
`loadVoice*` keeps selecting it (they all match `state == 2`), and
`onEngineProcessBack` cannot help because it only acts on `state == -1`. Only
force-stopping the process clears it — which is exactly what the owner reported,
symptom for symptom, on 2026-09-11 and again on 2026-09-15.

**It also explains "as soon as I turned it ON".** Before the switch, every
wrapper has `localeSet == true` from the ordinary `loadVoice` path. The moment
dedicated engines goes on, that guard fires for *every* engine, so the
self-healing check above is disabled app-wide from that instant.

### The related lifetime bug fixed with it
`EngineWrapper.stop()` and `shutdown()` used to read `tts` **inside** the queued
runnable, i.e. when the worker ran it. `stopExec` is
`ThreadPoolExecutor(1, 5, 60s, unbounded LinkedBlockingQueue)`, and an unbounded
queue's `offer()` never fails, so `execute()` never reaches `addWorker` and the
pool can never grow past one — `maximumPoolSize = 5` is dead config and every
task runs strictly FIFO on **one** thread.

`restoreEngine` is the only site that reuses a wrapper: it queues `stop()` and
`shutdown()`, then builds a replacement, and the listener assigns the new client
on the main looper. With one stop already ahead in that queue, the pending
shutdown reads the field and **shuts down the brand-new client**. Both methods
capture the client at call time now, so they act on the client they were asked
about.

---

## 31. An engine wrapper must never reach a state no recovery path can see
*(`invariants.sh` #29, negative-tested three ways in `selftest.sh`)*

The app has **exactly one** recovery path for a broken engine: `onEngineProcessBack`,
which fires when our keep-alive binding reconnects and acts on `state == -1`. So
`-1` must be what *"this wrapper has no usable client"* MEANS, at every site where
that becomes true. Anywhere else, the wrapper is invisible to recovery and that
engine is silent until the process is force-stopped.

Three ways in were found on 2026-09-16, **all permanent**:

| state reached | why nothing recovered it |
|---|---|
| **0** — a `TextToSpeech` constructor threw | `onEngineProcessBack` tests `-1`; `initAllEngines` runs once, from `onCreate` |
| **2** with a dead client, `restoreCount == 10` | the cap refuses; `onStart` cannot reset it because a dead client never speaks |
| any — `restoringIndex` stuck | `restoreEngine`'s first guard then refuses **every** engine, for ever |

### The third one is the worst, and a comment was hiding it
`restoreEngine`'s own note claimed no clock was needed because *"RestoreInitListener
ALWAYS runs — every failure path in AOSP's `initTts` ends in `dispatchOnInit(ERROR)`"*.
**That is false**, read from AOSP rather than recalled:

    private boolean connectToEngine(String engine) {
        boolean bound = connection.connect(engine);
        if (!bound) { ...; return false; }
        mConnectingServiceConnection = connection; return true;      // no dispatch
    }
    ... if (connectToEngine(defaultEngine)) { mCurrentEngine = ...; return SUCCESS; }

`initTts` returns SUCCESS the moment `bindService` returns true and **dispatches
nothing**; the dispatch happens later, from `Connection.onServiceConnected` →
`SetupConnectionAsyncTask.onPostExecute`. Its `dispatchOnInit(ERROR)` is only the
fall-through for *"no engine could be bound at all"*. So when the bind **succeeds**
and the engine process never comes up — a Play Store update, exactly — `onInit`
never fires, and `TextToSpeech` has no timeout of its own.

`restoringIndex` is written in one place and cleared in two, both needing that
callback. It therefore stayed set for ever, and every later restore of **every**
engine answered `" -Restoring in progress..."`.

### The fix is EngineFinder's own shape, in the second place it was needed
`EngineFinder.startEngine` was repaired away from this exact shape on 2026-09-09
(owner override). `restoreEngine` now uses the same three pieces and the same 30 s:
a captured index, a one-shot `AtomicBoolean`, and a main-looper timeout that
`shutdown()`s the abandoned client — **AOSP's own way to prevent the late callback**,
since `shutdown()` while still connecting calls
`mConnectingServiceConnection.disconnect()` — marks the wrapper `-1` and releases the
slot. A callback that lands after the timeout releases its own client and returns
**without** touching `restoringIndex`, which would otherwise belong to a newer restore.

**No latency is added.** Nothing new waits; the timeout only stops abandoned work,
and it can only fire on a path where speech is already impossible.

### Two smaller holes closed with it
- **`onBindingDied` never restored.** It did everything `onServiceDisconnected` does
  except the restore, so a binding death — which is what an engine *update* produces —
  left the wrapper at state 2 holding a client bound to a process that was gone.
- **`restoreEngine` compared a NORMALISED `wrapper.pkg` against the RAW package**
  `onServiceDisconnected` hands it. That is the identical defect fixed in
  `onEngineProcessGone` on 2026-09-11, one line below it in the same callback.
