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
language hint and CLD3 filters its top-3 by the same list — so they decide which
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
      app/src/main/java/com/tts/easyvoice/EasyVoiceTtsService.kt |
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

One accepted exception: the Languages screen's **"Show selected"** chip, because
WCAG 2.5.3 requires the accessible name to contain the visible label. Renaming
it is a wording decision for the owner.

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

## 16. An unreliable detector answer must never reach a span

**Rule.** Every `cld3DetectRaw` call passes a real `bool*` for `reliableOut` and
treats "not reliable" as unknown. Never `nullptr`.

**Why.** Both arms of `detectWindowLang` already answer `"UNKNOWN"` when the
detector is unsure — the CLD2 arm on `!reliable`, the CLD3 arm on
`!cld3Reliable`. The span site inside `nativeGetLanguages` was the one place
that passed `nullptr` and used the answer regardless.

What that cost, from a device log on 2026-08-27: with CLD3 enabled the Latin
name `"MEET Choudhary "` was spoken by the **Hindi** voice, while CLD2 read it
in English. The chunking was identical under both detectors; only the first
span's language differed. CLD3 answers `hi` for that text with
`is_reliable = 0` and `probability = 0.495`. Its own top-3 loop rejects the
candidate for exactly that reason — and then the fallthrough to `FindLanguage()`
returned the same unreliable `hi` anyway. Because Hindi *is* an enabled
language, `isHinted` accepted it and the per-script fallback never ran.

With the flag honoured, an unreliable answer becomes `"un"`, which is not in the
hint list, so the per-script fallback resolves the span to the one language its
script implies — English for a Latin span when English is the only enabled
Latin language.

**Check.** `tools/check/invariants.sh` #16, and the real proof,
`tools/verify/cld3span/run.sh`, which links the actual native core against CLD2
and CLD3, starts a JVM for a genuine `JNIEnv`, and asserts both detectors agree
on the reported utterance. Negative-tested: with the flag removed it reports the
device's exact failure, `span 0 is "hi", expected "en"`.

### The second half: the answer must belong to the span's script

**Rule.** A CLD3 answer is accepted only if the language is not placed under a
*different* script by `kScriptLangPairs`. `scriptOfLanguageCode` answers the
script or `-1`, and `-1` is **accepted**.

**Why.** CLD2 gets this free: its per-script hint is fed **into** the detector,
so for a Latin span with one enabled Latin language it is told what to expect.
The CLD3 arm has no hints API and can only filter afterwards, against a flat
enabled-language list that knows nothing about script — so a reliable `sr` or
`ja` would win a Latin span merely because the user has Serbian or Japanese
enabled.

Measured over the same 536 Latin strings, with and without the rejection:

| enabled languages | reliability only | + wrong-script rejection |
|---|---|---|
| `eng`, `guj`, `hin` — the reporter's set | 0 | **0** |
| `en`, `ru`, `uk`, `bg` | 1 | **0** |
| `en`, `ja` | 4 | **0** |
| `en`, `zh`, `ja`, `ko` | 4 | **0** |
| `en`, `sr` | 19 | **0** |
| `en`, `ja`, `sr`, `ru`, `zh` | 23 | **0** |

and the other direction checked too — 18 non-Latin lines (Devanagari, Cyrillic,
Arabic, CJK, Bengali, Gujarati, Tamil) across seven enabled sets: **0
disagreements**, so rejecting wrong-script answers did not start rejecting right
ones.

**`-1` must keep meaning "no evidence".** The 48 pairs are not a complete script
classification: Latin lists 24 languages and has no Catalan or Basque, and CJK
has no Korean at all. Treating "absent from the table" as "wrong script" would
reject far more than it fixed. Only a language the table places under a
different script is a provable mismatch.

**Why the reporter's own set was already 0.** CLD3 never *reliably* answers bare
`hi`/`gu` for Latin text — it answers `hi-Latn`, which `isRomanisedTag` drops,
or an unreliable `hi`, which the reliability half drops. The gap was real but
dormant there; it was closed anyway because the enabled-language list is
something the owner changes.

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
been kept. Our comma list, which is what the CLD3 arm filters against, is built
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

**Check.** `tools/verify/cld3span/run.sh`. Negative-tested by building the
harness with each change reverted:

| reverted | what the harness reports |
|---|---|
| script 0 | 6 failures, e.g. `bare number, en enabled: span 0 is un/latin=1, expected en/latin=1` |
| rank 0 | `French text, es+fr enabled: span 0 is "es", expected "fr"`, and three more |

The four rank-0 cases were **found** by building both ways and diffing, not
invented: with `{es, fr}` enabled, a French paragraph with a Dutch tail was
spoken in **Spanish** before the fix and in French after it.

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

**`RedundantDescriptionCheck` on "Show selected".** The chip's visible label
contains the word "selected", and WCAG 2.5.3 Label in Name requires the
accessible name to contain the visible label. Renaming the chip is the only way
to silence it, and that is a wording decision for the owner.

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
