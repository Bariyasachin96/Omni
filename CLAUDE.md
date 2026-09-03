# EasyVoice — Claude Session Context

## Project Overview
- **App name**: Easy Voice — Android TTS screen reader for 100% blind users
- **Goal**: Exactly match AutoTTS (com.vnspeak.autotts) behavior in all modes
- **Source**: real files under `app/`, checked in. Edit them directly.
- **Working branch**: `claude/yaml-file-nk3czh`
- **Build**: Manual `workflow_dispatch` trigger on GitHub Actions — must trigger manually after each push

## CLD3 IS GONE — CLD2 IS THE ONLY DETECTOR (owner decision, 2026-09-02)
*"cld3 library hai na, ham hata hi dete hain properly … jitna bhi cld3 ke saath juda
hua hai sab kuchh, A to Z … only CLD2 hi rakhna hai."* Done, and it is not a pause:
**do not reintroduce CLD3, its switch, its flag or its build inputs** without the
owner reversing this in writing. `tools/check/invariants.sh` #16 fails the build if
any `cld3*` / `useCld3*` / `NNetLanguageIdentifier` symbol reappears in the core or
the service, and a second check fails if `cld3`, `protobuf` or `protoc` reappears in
`CMakeLists.txt` or `build.yml`. Both are negative-tested in
`tools/check/selftest.sh`.

**What went, in one list.** The native detector arm (`cld3DetectRaw`,
`cld3FindLanguageGated`, `cld3TopNGated`, `isRomanisedTag`, `isCld3Unknown`,
`scriptOfLanguageCode`, `baseLanguageTag`, the squeeze gate, the `#define private
public` include wrapper); the **detect-context store and the span widening** that
existed only to feed it; the `useCld3` parameter on all three JNI entry points and
their Kotlin declarations; `useCld3Flag` and the `use_cld3` preference with its load
and persist; the Advanced-tab switch and its description; **nineteen CLD3 sources,
three generated protobufs and the whole protobuf runtime** from `CMakeLists.txt`;
the cld3 and protobuf clones and the protoc step from **both** CI jobs; and two
harnesses that existed only for it, `tools/verify/cld3span/` and
`tools/verify/isocodes/`.

**Why, in one line the owner earned by testing it for days:** CLD3 reads short
Devanagari at about 50%, a coin toss, and is wrong in both directions; CLD2 scores
24 of 25 on the same corpus at every length. The measurements that killed every
attempted fix are kept below under the three CLD3 report sections — read them before
ever proposing to bring it back.

**Two things got better on the way out, both measured, neither of them the point:**
- the detection path is now **12x faster on long text** — the latency harness reads
  3.9 ms for 60 paragraphs where CLD3 plus the widening read 47.3 ms;
- the APK loses the protobuf runtime, nineteen CLD3 translation units and a protoc
  download from every CI run.

**The CLD2 path is byte-for-byte what it was.** That is the one thing this change had
to guarantee and it is proven, not asserted: `tools/verify/segmenter/run.sh` is still
**identical over 163,296 cases** after the removal, and `check-all` reports no new
type errors.

## LATENCY: where it actually is, measured after CLD3 went (owner request, 2026-09-02)
*"latency bilkul aani hi nahin chahie."* Measured rather than guessed, and the
answer moved the work somewhere unexpected.

**The detector was never the problem, and the number that made it look like one was
the wrong number.** The latency harness repeats three fixed paragraphs, and the row
being quoted was 60 paragraphs = 10,620 characters. **That utterance cannot exist**:
`TextToSpeechService.SynthesisSpeechItem.isValid()` rejects anything over 4000
characters before `onSynthesizeText` is ever called. Measured against the sizes a
screen reader really sends, CLD2 alone, with `normalizeFancy` timed too because
`detectLanguageRuns` calls it per chunk:

| chars | chunks | segment | detect | fold+detect | TOTAL |
|---|---|---|---|---|---|
| **165** | 5 | 0.07 | 0.17 | 0.09 | **0.16 ms** |
| **334** | 7 | 0.04 | 0.18 | 0.22 | **0.26 ms** |
| **531** | 9 | 0.12 | 0.38 | 0.28 | **0.41 ms** |
| 1,758 | 29 | 0.29 | 0.91 | 0.86 | **1.15 ms** |
| 3,520 (the ceiling) | 55 | 0.33 | 1.27 | 1.25 | **1.58 ms** |

The owner's own logged utterances run 20 to 400 characters, so the real figure is
**a tenth to three tenths of a millisecond**. `normalizeFancy` costs nothing
measurable: its fast path returns immediately when the text carries no decorated
letter, which is almost always.

**THE REAL LATENCY WAS THE LOGGER, and it is about a hundred times the detector.**
`EasyVoiceLogger.writeLine` is a byte-for-byte port of AutoTTS's `c3.p.h`, which
opens the file, writes ONE line and closes it again, after calling `exists()` and
`length()` first. That is **five syscalls per line** — and one language switch
writes **32 lines** between `onDone` and the next `speak()`, on the main thread, so
roughly **160 syscalls with the next word already waiting**. The owner's own logs
measured that whole step at **15 to 44 ms**.

**The writer is held open now.** Measured on this container's NVMe, 32 lines:

    open/write/close per line   1.177 ms
    one held writer + flush     0.170 ms      7x

A phone's flash is slower and busier than an NVMe, so the real saving is larger
than 7x rather than smaller. **DELIBERATE DEPARTURE** from AutoTTS, on the same
footing as the two 50 ms `postDelayed` removals.

**The log file is byte-identical, and that is proven rather than claimed.** A
harness ran the old and the new implementation over the same 120 operations --
including random `clear()` calls and repeated rotations at a deliberately tiny cap
so rotation really fires -- and compared all four files (`easy_voice.log` and `.1`
`.2` `.3`) byte for byte. Identical on both trials. What changed is the open and
the close, not the durability: `flush()` still runs after **every** line, so a crash
or a kill loses no line a caller had already logged.

**Three places close the handle, and each is a real bug if it is missed:**
- **logging switched off** — otherwise the file stays open for the life of the
  process;
- **`clear()`** — the held writer carries its own offset, so writing through it
  after the file is truncated would leave a hole of NUL bytes where the old content
  was. It closes BEFORE truncating;
- **rotation** — the handle would otherwise keep writing to the renamed inode, and
  the new `easy_voice.log` would stay empty.

The rotation test is the in-memory byte count rather than two `stat` calls, seeded
from the real `file.length()` when the writer opens, so it is never an
underestimate and the file cannot grow past the cap unnoticed.

## APK size: it is CLD2's tables x two ABIs, not the code (measured 2026-09-03)
Owner: *"unused file unused code hata do jahan per bhi ho aur APK size kam kar do
aur koi tarkeeb laga R8 ki."* Measured rather than guessed, and the measurement
moves the answer away from R8 entirely.

**Baseline: build 818 is 3,837,769 bytes** (the release asset, from the API).
**The release APK could not be opened here** -- the GitHub download answers 403
through the egress proxy, and that is a policy denial, not to be routed around --
so the native side was measured by compiling the exact source list
`CMakeLists.txt` names, with the same `-Os -g0 -fvisibility=hidden
-ffunction-sections -fdata-sections`, and summing `.text + .rodata`:

| object | bytes |
|---|---|
| **`cld2_generated_quadchrome_16`** | **814,971** |
| `cld_generated_cjk_uni_prop_80` | 75,077 |
| `cld2_generated_deltaoctachrome` | 70,227 |
| `getonescriptspan` | 61,011 |
| `generated_language` | 38,990 |
| the other 19 objects together | ~100,000 |
| **CLD2 total, per ABI** | **1,160,930** |

So **one quadgram table is 70% of CLD2, CLD2 is ~1.11 MB per ABI, and the APK
ships TWO ABIs** (`armeabi-v7a` + `arm64-v8a`). The native libraries are most of
the download; the Kotlin is not where the size is.

**The smaller-table idea is already taken, and this is why it must not be
re-opened.** CLD2 ships five quad tables and we already build the SMALLEST:

    cld2_generated_quad0122.cc      27,817,652    <- not used
    cld2_generated_quad0720.cc      27,428,641    <- not used
    cld2_generated_quadchrome_2.cc   7,661,476    <- not used
    cld2_generated_quadchrome_16.cc  4,874,655    <- OURS

and it is also the one `libcld2.so` uses, so swapping it would change detection
answers and break the segmenter's proven parity. Do not.

**What was actually changed, and what was deliberately NOT.**
- **R8 full mode is now stated** in `gradle.properties` instead of inherited from
  the AGP default. It changes nothing today (AGP 9 defaults it true) -- it is
  there because `proguard-rules.pro` is WRITTEN for full mode
  (`-allowaccessmodification`, `-repackageclasses ''`) and would quietly stop
  paying for itself if a default ever flipped. The comment in that file used to
  claim gradle.properties said so; now it does.
- **Kotlin's `Intrinsics` null checks are stripped.** Every public Kotlin function
  with a non-null parameter carries a `checkNotNullParameter` call plus its
  parameter-name string, and R8 does not remove them on its own even in full mode.
  Safe here for a reason rather than by hope: the framework entry points all
  declare their parameters **nullable**, so no check is generated for them, and
  the JNI boundary is the only other source of a null -- every entry point in
  `tts_engine_core.cpp` returns `NewStringUTF`/`NewObjectArray` and cannot return
  null, with a `try/catch` around every Kotlin call into it besides.
- **`android.util.Log` is NOT stripped, and that was a measurement, not a
  preference.** It is the obvious next `-assumenosideeffects` line and it is
  wrong here: `EasyVoiceLogger` itself calls `Log.e`/`Log.w` **and
  `Log.getStackTraceString`**, which is what puts a stack trace INTO
  `easy_voice.log` -- the file the owner shares when reporting a bug. Stripping it
  would delete that trace and save nothing worth having, because the message
  strings are built by our own code and handed to `EasyVoiceLogger`, so they stay
  in the APK either way. There is a comment in `proguard-rules.pro` saying so.

**THE ONE BIG LEVER IS THE DOUBLE ABI, AND IT IS THE OWNER'S CALL** because it
changes what gets published, so it was not done unilaterally. Three shapes:
an **AAB**, which is what a Play Store listing needs anyway and lets Play deliver
one ABI per device; **ABI splits**, which publish two APKs and mean picking the
right file; or **dropping `armeabi-v7a`**, which is the smallest change and takes
32-bit-only phones away -- the same kind of cost as raising `minSdk`, which this
project has always refused.

**The unused sweep came back nearly empty, which is the real answer to "unused
hata do".** Every `res/` file and every `values/` entry is referenced; no Kotlin
declaration is unused (the one apparent orphan, `marketIntent`, is a local read
on the next line); no `static` in the native core is uncalled. Two real findings:
`abbreviateEngineNameFor` was a pure pass-through to a `private` function in the
same file, so the private modifier went and the wrapper with it; and **five tool
files nothing runs** were deleted -- `tools/check/ktown.py` (superseded by the
metric `kotlin-typecheck.sh` computes inline), `tools/autotts/extract_method.py`,
`tools/autotts/showskel.py` + `skelmod.py` (showskel imports skelmod and nothing
imports showskel; `cmp_versions.py` imports neither), and
`tools/verify/normalizer/cpp/e2e.cpp` (`run.sh` builds only `sweep.cpp`).
**`Norm.java` in that same harness LOOKS orphaned to a grep and is not** -- the
script copies `java/*.java` and runs `java Norm`. None of this touches the APK;
tools are not shipped.

## Easy Voice is STANDALONE now, and About is the first thing that shows it (owner, 2026-09-03)
*"kya donon mein jo nahin hai uski jarurat padegi? Agar uski jarurat hamare mein
pad sakti hai to hamare mein dalne mein koi harj nahin hai. Hamara alag hi
proper, hamara standalone hai."*

**What this changes, and what it does NOT.** It is a standing permission to ADD
things AutoTTS has no counterpart for, when the app needs them. It is **not** a
repeal of rule 5: anything AutoTTS *does* have is still mirrored exactly, and
detection, the service and storage are untouched. The test is "AutoTTS lacks it
and we need it", never "AutoTTS has it and I would do it differently".

### The About screen
Asked for in the same message: an **About section at the very top of the
Advanced tab**, and tapping it shows the license and everything else. The
developer is **Sachin Baria**.

`AboutActivity` / `AboutScreen`, a separate screen for the same reason Languages
and Mode settings are screens: the license notice is long and the Advanced tab
is already the longest thing in the app to swipe through. Its content is in the
order the owner gave:

    About                          (SectionHeader -- names the SCREEN)
    Easy Voice                     (headlineSmall -- the app's name)
    Build number: <n>
    Version: <name>
    Developer: Sachin Baria
    Copyright (c) 2026 Sachin Baria. All rights reserved.
    Open source licenses           (SectionHeader)
    the Apache 2.0 line, CLD2, AOSP/Jetpack, the verbatim notice
    [Apache License 2.0]  [CLD2 on GitHub]      side by side, last

**Three things the owner sent it back for on 2026-09-03, all fixed. Do not undo
them:**
1. **The first heading names the SCREEN, not the app.** It said "Easy Voice",
   so heading navigation opened on the app's name, which reads as content --
   *"vah vahan per developer ke upar aa hi nahin raha hai, jaise aur screen mein
   heading aati hai"*. Every other screen's first heading names the screen
   ("Languages", "<Mode> settings"), and About now does too. The app's name sits
   under it as a `headlineSmall` title, which is what it actually is.
2. **The two link buttons are SIDE BY SIDE, never stacked** -- *"button ko upar
   niche nahin, baju mein karna hai"*. It is the `Row` + `weight(1f)` +
   `spacedBy(8.dp)` shape Import/Export and Share logs/Clear logs already use.
   **They carry NO icons**, which is the app's own recorded rule for a row of
   buttons: at a compact width half the row leaves about 112dp for the label,
   and 24dp of icon plus 8dp of padding overflows it. That is also why the
   labels are short -- "Apache License 2.0" and "CLD2 on GitHub" -- and
   `ic_open_in_new.xml` was deleted with them, since nothing referenced it any
   more.
3. **The AOSP/Jetpack line moved UP.** It was one run-on sentence stranded
   *below* the buttons, so the page ended on a footnote instead of on its
   actions. It is a licence entry like CLD2's, so it now reads like one and sits
   beside it, and the buttons are last.

**The licence text comes from CLD2's OWN repository, not from the old Licenses
tab.** The first version of this screen reused the wording recovered from the
generator in commit `a4dc250`; the owner rejected that the same day -- *"licence
info ... repository se dekhna chahie ... jo CLD2 ki repository hai vahan se
uthana chahie ... purana wala ko aapko nahin dekhna chahie"* -- so it was written
again from `github.com/CLD2Owners/cld2`, which is what `build.yml` clones. What
was read there, and what each thing settled:

| in the repo | what it gave the screen |
|---|---|
| `LICENSE` | the Apache 2.0 text, **byte-identical** to our clone's copy (`diff` clean) |
| `README.md` | the name, the author line **Dick Sites (dsites@google.com)**, and "These **83** languages are detected" |
| every `.cc` / `.h` header | `Copyright <year> Google Inc. All Rights Reserved.` plus the Apache notice |
| **`NOTICE`** | **does not exist -- HTTP 404** |

Two things that only reading the repo could have told us:
- the copyright line is **"2013, 2014"**, not 2013 alone. Of the 24 CLD2 files
  `CMakeLists.txt` compiles, 21 headers say 2013 and **three say 2014**
  (`cld2_generated_quadchrome_16`, `cld2_generated_deltaoctachrome`,
  `cld2_generated_distinctoctachrome`). The old wording said 2013 only.
- there is **no `NOTICE` file**, so Apache 2.0 section 4(d) asks us to reproduce
  nothing extra; the copyright line plus a pointer to the License is the whole
  obligation.

The two Apache paragraphs on the screen are the notice **verbatim**, exactly as
it sits at the top of every CLD2 file we compile and in the LICENSE's own
appendix. **Do not paraphrase them** -- being verbatim is the point. The screen
also carries a button to the repository itself, because that is where the code
comes from, and a line for AOSP/Jetpack, whose headers were checked the same way
(`Copyright <year> The Android Open Source Project` plus the same notice, read
from `androidx/androidx`; note androidx has no root `LICENSE` -- that URL is a
404, the headers are the source of truth).

**The licence section is COMMERCIALLY load-bearing -- its heading AND its
first sentence (owner, 2026-09-03).** The heading is **"Open source licenses"**,
not "License": on a paid listing a section called "License" sitting above an
Apache 2.0 notice invites the reader to think the APP carries that licence, while
this name says plainly that the licences below belong to the open source PARTS.
It is also what a store listing's own section is normally called. The owner is preparing a **PAID** Play Store listing and asked
about this line before anything was implemented -- *"main isko purchase rakhna
chahta hun ... yah text mere khyal se thoda sahi nahin hai"*. They were right. It
read:

    Easy Voice is built on open source work, and all of it is used under
    the Apache License, Version 2.0.

Three things wrong with it, all of which matter only once money is involved:
- **"all of it" reads back to "Easy Voice"**, so the sentence could be understood
  as putting the WHOLE APP under Apache 2.0. That is false, and on a paid app it
  is a statement working directly against the thing being sold.
- **"built on open source work"** invites the reader to assume the app itself is
  open source, and therefore obtainable free somewhere else.
- **nothing in the section said who owns Easy Voice**, so there was no line drawn
  between our code and the components we merely include.

It now reads *"Easy Voice itself is proprietary software. The third-party
components below are open source, and each one is used under the Apache License,
Version 2.0."* **Do not shorten it back to something friendlier.**

**Selling it is NOT a licence problem, and that was checked at the source rather
than assumed.** Apache 2.0 section 2 grants a *"perpetual, worldwide,
non-exclusive, no-charge, royalty-free, irrevocable copyright license to
reproduce, prepare Derivative Works of, publicly display, publicly perform,
**sublicense, and distribute** the Work"*, and section 4 imposes exactly four
conditions -- (a) give recipients the licence, (b) mark modified files, (c) keep
the copyright and attribution notices, (d) reproduce `NOTICE` **if the work has
one**, and CLD2 has none. Nothing anywhere forbids charging. The About screen
already satisfies all four.

**The AutoTTS question was raised once and the owner has decided.** Told plainly
that this app's code was written from AutoTTS's decompiled source and that a paid
listing raises a copyright and Play-policy question worth a lawyer's hour, the
owner answered: *"mujhe usse koi fark nahin padta hai kyunki humne khud likha hai
to hamara hi ho gaya ... uske liye hamen kuch likhne ki jarurat nahin hai."*
**That is their call and it is settled -- do not add an AutoTTS credit, notice or
attribution anywhere, and do not raise it again unless the owner does.**
(`autotts_reference/` is repo-only and has never been part of the APK.)

**Two details that are load-bearing, not style:**
- **The Advanced tab's "Information" section is GONE, folded into About.** It
  showed Build number and Version at the bottom; About now shows both, and
  leaving the old section would make a screen reader meet the same two facts
  twice on one tab. Nothing was lost -- it is the same `PackageManager` read,
  moved. (It was AutoTTS's `c3.k:1187-1188`; this is a UI departure under the
  carve-out. Say so if it should come back.)
- **The header says "About" and the button says "About Easy Voice", on purpose.**
  ATF's `DuplicateSpeakableTextCheck` warns when two elements share a speakable
  name and either is clickable, which a header reading "About" directly above a
  button reading "About" is exactly. Do not "tidy" them to the same string.

Two new icons, both fetched verbatim from `google/material-design-icons`
(`src/action/info` and `src/action/open_in_new`); `ic_open_in_new` is
`autoMirrored` because it is directional. `AboutScreen` is covered by
`AccessibilityChecksTest.aboutScreen`, so the emulator job checks its contrast,
label and touch target on every run.

## The half-done sweep, and the AOSP TTS class read end to end (owner request, 2026-09-02)
*"jo bhi function mein jo bhi jagah per aadha adhura lagta hai … completely fix
karo"*, *"sab kuchh static rakho … preferences wala sahi nahin rahta"*, and
*"TTS wala jo class hai … properly uske bare mein sab kuchh nikalo"*. Three
findings, all fixed; the rest of the sweep is recorded so it is not redone.

### 1. The empty-text flush still had the `isSpeaking()` race — FINISHED
The `onStop` fix of earlier the same day dropped
`f.get(i3).g().isSpeaking()` from the stop path, and the note here said the
**other** `stopAllTts` site was *"left alone deliberately"* because it was a
different trigger. That was the half-done thing.

`q0(FALSE)` at `AutoTtsService:1886` carries the identical guard on the identical
race: `isSpeaking()` is a binder query into another app's engine and answers
FALSE in the window between our `speak()` and that engine really starting. And
this is the path a screen reader takes when it interrupts with an **empty
utterance**, which is how TalkBack flushes — so landing in the window meant the
flush never happened and the previous phrase carried on. Same defect, same
evidence, same fix, and it is a **DELIBERATE DEPARTURE** on the same footing.
`speak("", QUEUE_FLUSH, null, null)` on an idle engine is harmless: it flushes an
empty queue, and the null utterance id means AOSP dispatches no callback for it.

### 2. A guard of ours that could only ever do harm — REMOVED
`onLoadLanguage` tested `&& ::prefs.isInitialized`. AutoTTS's `d0` has nothing of
the sort, and had it ever been false this method would have reported the language
**available** and loaded **no voice at all**, silently. It also cannot be false:
AOSP's own `TextToSpeechService.onCreate` **ends** with

    onLoadLanguage(defaultLocale[0], defaultLocale[1], defaultLocale[2]);

so the earliest possible call is our own `super.onCreate()`, six lines after
`prefs` is assigned, and no binder call can arrive before `onCreate` returns.
AutoTTS's `onCreate` has the same shape — logger, version log, `super.onCreate()`,
and only then `e0()` / `n.o()` / `n.q()` — so at that first call BOTH apps see an
empty language list and answer LANG_NOT_SUPPORTED. Parity, not luck.

### 3. Statics: already complete, and now ENFORCED rather than asserted
Every settings flag is read from preferences exactly **once**, in the service's
`loadAllSettings`, and every Advanced/Modes/Voices control reads and writes
`EasyVoiceTtsService.<flag>` directly. `getReadingMode`/`setReadingMode` look
like preference accessors and are not — they read and write
`EasyVoiceTtsService.modeInt`. So the answer to *"sab kuchh static rakho"* is
that it already is; what was missing was anything stopping it from drifting back.
**`invariants.sh` #4b** now fails the build if any screen calls one of the
sixteen settings accessors, negative-tested in `selftest.sh`. Written up as
`docs/INVARIANTS.md` #4b.

### What the AOSP read produced, so it is not redone
`TextToSpeechService.java` and `TextToSpeech.java` were read against our service
method by method. **The override surface matches AutoTTS exactly**: fourteen
overrides including `onTaskRemoved` (both just call super) and all five
`UtteranceProgressListener` members including the API-21 `onError(id, code)` and
`onStop(id, interrupted)`; `onGetFeaturesForLanguage` is overridden by neither.
`onStartCommand` returns 1 = START_STICKY in both.

Four contracts checked and clean, so **do not re-audit**:
- *"the engine must NOT hold on to the callback or call any methods on it after
  the method returns"* — `callback` is a **parameter** captured by the listener
  closure, never a field, and `onSynthesizeText` is still parked on `syncLock`
  while those callbacks run. A late callback after the wait is released is
  AutoTTS's shape too, and AOSP's `PlaybackSynthesisCallback` answers `ERROR` and
  does nothing once stopped.
- *"return values HAVE to be consistent with onLoadLanguage"* —
  `onLoadLanguage` literally returns `onIsLanguageAvailable`'s result.
- **the wait cannot be orphaned by service death**: `SynthHandler.quit()` calls
  `current.stop()` → `stopImpl()` → `synthesisCallback.stop()` **and**
  `TextToSpeechService.this.onStop()`, so `onDestroy` releases it. Our
  `super.onDestroy()` is last, as AutoTTS's is.
- **`d0`'s `variant.contains("autotts.") && n3 == 2` branch is dead**, like the
  `n3 == 1` / `n3 == 2` blocks already recorded: `onIsLanguageAvailable` answers
  only 0 or −2.

Two more pieces of AutoTTS dead code confirmed and left dead on purpose:
`AutoTtsService`'s static field **`m0`** (assigned by `onLoadVoice`, read
nowhere — not to be confused with the *method* `m0(String)` at :1503) is our
`lastLoadedVoiceName`, now carrying that explanation; and `k0.h` is
`wrapper.audioAttrSet`, one-shot per engine under `if (!Y || h) break`, which is
byte for byte our `if (isForceAccessibility && !wrapper.audioAttrSet)`.

**Mechanical sweeps that came back empty**, so there is nothing else of this kind
to find: no `TODO`/`FIXME`/"for now"/"not implemented" anywhere in the app
sources; every Kotlin declaration is referenced (the only apparent orphans are
framework overrides); every `static` in the native core is called; and the single
`(void)` cast is `disableAdvancedDetection` in `buildMixChunks`, inert by design
and verified at the line.

## The sliders: a swipe moves 5, the buttons move 1 (owner request, 2026-09-02)
*"TalkBack se slider ko badhate hain ghatate hain to 5% 5% nahin badh raha hai,
aage piche ho jata hai"*, and *"increase decrease button se to ek-ek percent hi
aage badhna chahie"*. Both fixed in `ValueSlider` (`VoiceScreen.kt`). Speed,
Volume and Pitch are percentages -- 100 is the engine's own rate -- so one unit
IS one percent, which is what makes "5%" and "one percent" the same vocabulary.

**Why it drifted, read from the delegate rather than guessed.** Compose turns a
screen-reader swipe into a value ITSELF, without asking the component:

    var increment = if (rangeInfo.steps > 0) (max - min) / (rangeInfo.steps + 1)
                    else (max - min) / AccessibilitySliderStepsCount   // = 20
    return setProgressAction.action?.invoke(rangeInfo.current + increment)
        -- AndroidComposeViewAccessibilityDelegateCompat, ACTION_SCROLL_FORWARD

A continuous slider therefore moves by a **twentieth of its range**: 24.5 for
Speed (10..500), 4.5 for Volume (10..100), 9.5 for Pitch (10..200). Not one is
a whole number, and the value we keep is an `Int`, so `onValueChange`'s
`toInt()` threw the half away every single time:

    100 -> swipe up -> 124 -> swipe down -> 99

That is the "aage piche" exactly -- up and back down does not return -- and it
is why the step the owner heard alternated between four and five.

**The fix is to answer the action ourselves**, with `setProgress` in the
semantics block we already pass through `modifier`. **That block overrides the
component's own, and here is why it does**: `LayoutNode.calculateSemantics-
Configuration` walks `nodes.tailToHead(Nodes.Semantics)` writing every node into
ONE shared config, so the modifier nearest the head -- the FIRST in the chain,
which is ours, since Slider does `modifier.<...>.sliderSemantics(state, enabled)` --
is written last and wins. (The older `collapsePeer` path agrees from the other
end: it keeps the value already present, and ours is collapsed first.) Slider's
own `progressBarRangeInfo` is deliberately left alone; the delegate needs it to
offer the scroll actions at all.

A swipe now steps to the next multiple of 5, so it is exactly 5 in both
directions and always lands on the same grid whatever the buttons did in
between. Verified over every value of all three sliders: every step is 5, and
up-then-down returns. The buttons step by 1.

**Setting `steps` on the Slider is the obvious alternative and is wrong twice**:
`SliderState.value`'s setter snaps to the nearest tick, so it would quietly
round away the single percent a button had just added, and the default track
draws a tick mark per step -- 98 of them on Speed. Do not "simplify" to it.

Voice Access asking for a specific value is still honoured exactly; the
platform's increment is compared only to tell the two kinds of call apart, and
nothing depends on it being any particular size.

## READ THESE BEFORE ANYTHING ELSE
Four documents were written on 2026-08-26 so that a session does not have to
reconstruct the same knowledge every time. They are short and they are the fastest
route into the code:

| file | what it answers |
|---|---|
| `README.md` | what the repo is, where things are, how to change one |
| `docs/ARCHITECTURE.md` | one utterance from the system to the speaker, stage by stage — **start here to find the code that owns a symptom** |
| `docs/AUTOTTS_MAP.md` | every AutoTTS class and method and its counterpart here, with what is verified and how |
| `docs/INVARIANTS.md` | thirteen rules that must hold, each one there because breaking it caused a real bug |

`tools/README.md` covers the checks and the four proof harnesses.

## THE SOURCE IS CHECKED IN NOW (2026-08-26) — there is no generator
**Do NOT reintroduce one.** Every Kotlin, C++, XML and Gradle file used to be embedded
as Python string literals inside `ci/generate.py`, which CI ran to materialise the tree.
That is gone. `app/`, `build.gradle.kts`, `settings.gradle.kts` and `gradle.properties`
are ordinary files; **edit them with `Edit`**, and `git diff` shows the real change.

What went with it, and must not be resurrected:
- **`blocks.py`** — the string-splicing tool that was the only safe way to edit a
  `write_source` block. It had already swallowed a whole file once.
- **`gentree.py`** — materialised the tree into a scratch dir.
- the **"Generate Complete Project"** step in `build.yml`.

Proved before deleting, not assumed: `ci/generate.py` parsed to 67 top-level statements
with no conditional, no environment read and no computation; two runs under different
environments produced byte-identical trees; and the 62 checked-in files were each
compared byte for byte against the generator's output.

**The 500 KB ceiling that forced the generator out of `build.yml` no longer applies to
anything** — the workflow is 11 KB and has nothing in it that can grow. The rule is
recorded in `docs/INVARIANTS.md` #12 for the day someone is tempted to inline something
again. In short: GitHub's limit is **512,000 bytes per workflow file**, and over it a run
is created and numbered but **never parsed** — queued forever, zero jobs, no
`startup_failure`, and cancel answers HTTP 500. It cost two debugging sessions.

**One thing genuinely lost.** Three launcher drawables were spliced from a shared
`launcher_mark` literal, so editing one changed all three. They are independent copies
now; change them together by hand.

## Navigating the two big files (2026-08-26)
`tts_engine_core.cpp` and `EasyVoiceTtsService.kt` each carry a table of contents:

    grep -n "^//  [A-Z]"   app/src/main/cpp/tts_engine_core.cpp
    grep -n "^ *//  [A-Z]" app/src/main/java/com/tts/easyvoice/EasyVoiceTtsService.kt

Every banner names the AutoTTS counterpart of the section under it, and the ones over
proven code say which harness proves it. Inside `onSynthesizeText` the five mode
branches are marked too — that method is 571 lines and was the hardest thing in the
project to navigate.

**Three dead things were deleted from the native core at the same time**, all verified
by grep to have no caller and all already dropped by the compiler at `-Os`:
`scriptLangForCp` (65 lines — a *third* copy of the code-point ladder, sitting right
after `processDirect` and looking exactly like live code; the live port of `a.b`/`a.d`
is `familyForCp`), `isEmojiJoiner`, and four forward declarations that declared nothing.
The one forward declaration that IS load-bearing — `currentLanguageHints`, called 1,288
lines before it is defined — stays and now says so.

**Neither file was split.** The Kotlin cannot be: Kotlin has no partial classes, and
almost every member of `EasyVoiceTtsService` touches instance state, so a split would
mean rewriting call sites rather than moving text. The C++ could be, and there is a
worked plan for it — but it needs headers, internal-linkage changes and a CMake edit,
and none of that can be verified without an NDK build. Do it when a build is available
to check it, not before.

## Local validation before every push
    tools/bootstrap.sh          # once per container: android.jar + kotlinc
    tools/check-all.sh          # ~2 min, everything static
    tools/verify/*/run.sh       # the behaviour proofs, when you touch what they cover

**Accessibility is checked in CI, not here.** `app/src/androidTest/.../AccessibilityChecksTest.kt`
runs Google's Accessibility Test Framework — the engine behind Accessibility Scanner — over
every screen, and catches the four mechanical things this document has been computing by
hand: missing labels, colour contrast, touch target size and traversal order.
`enableAccessibilityChecks()` needs **API 34** and is a no-op under Robolectric, so it runs on
an emulator in the workflow's **`accessibility` job**. **That job failing fails the run.** It is
a separate job only so it runs in *parallel* with the APK build; an accessibility regression is
a real defect and is to be fixed, never tolerated because the APK happened to build. Do not add
`continue-on-error`. It still cannot judge whether a label is the *right* label — INVARIANTS
#6, #7, #9 and #18 are a careful read and the owner's ear.

`tools/check-all.sh` runs, cheapest first: `ktcheck` (structure), `ktresolve` (our own
call signatures), `ktimports`, `xmlcheck`, `cpp-syntax.sh`, and `kotlin-typecheck.sh`,
which diffs kotlinc's errors against a baseline commit. **Judge that last one by the NEW
error texts it prints, never by the total** — the jar is API 15 from Maven Central
because Google Maven is blocked, so androidx, Material and Compose are all unresolvable
and every run reports over a thousand errors. Running it *without* a real `android.jar`
is worse than useless: kotlinc then checks **nothing** at `android.*` call sites, which
is how a wrong trailing lambda once reached CI.

`kotlin-typecheck.sh` still understands the old layouts — it reads a baseline from real
files, else `ci/generate.py`, else the generator inlined in `build.yml` — so a baseline
from before 2026-08-26 still works.

**The local `kotlinc` now MATCHES the project (2026-08-27).** `tools/bootstrap.sh` pins
**2.4.10**, the same Kotlin the build uses, so the local check finally sees what CI sees;
it used to be 1.9.22, which could not diagnose anything Kotlin 2.x specific. The
Compose-migration note far below still says 1.9.22 / Compose Compiler 1.5.10 /
BOM 2024.02.00 — that is a record of the migration, not current fact.

**Moving to K2 broke a checker silently, and it is worth knowing why.** Kotlin changed the
wording of its most common diagnostic:

    K1 (1.9.22):  unresolved reference: androidx
    K2 (2.4.10):  unresolved reference 'androidx'.

`kotlin-typecheck.sh`'s "our-own-name unresolved refs" metric grepped for the K1 form, so
after the bump it matched **nothing** and read 0 where it had read 9 — a checker going
blind, dressed up as an improvement. It matches both forms now, and it carries a guard: if
the compiler reports unresolved references at all while the pattern matches none of ours,
the script fails and says the wording has changed. Negative-tested both ways.

## HARD RULES (NEVER violate)
1. **NEVER build/push without explicit user request**
2. **Fix #16 (Disable engine handling) — IMPLEMENTED 2026-07-10** (user override: "exactly AutoTTS"). `m.o()` (in `m.c` and not disabled, **no engine test**) is what `clsCLD2.b` accepts a detection with and what `a.e(cp, m.f)` filters by; on our side that is the `detectOk`/`enabledOk` arrays handed to the native detector. The auto and mix span resolution use a *different* test — `M(lang)` empty or `"Disable"` — which is `isLangRoutableRaw`. Do not merge the two. Dual mode untouched (type-based, no k.o). Previously set-aside; no longer.
3. **Always develop on branch `claude/yaml-file-nk3czh`**
4. **After every push, manually trigger GitHub Actions** (workflow_dispatch, workflow ID: 262884892)
5. **ZERO OWN DECISIONS — fix EXACTLY like AutoTTS, always. This is the #1 rule, blink on it every single edit.** I have NO independent decision, EVER. The user has said this many times; never make them say it again. Concretely:
   - NEVER add anything AutoTTS does not have, and NEVER remove/change anything AutoTTS has — no matter how "small", "big", "cosmetic", or "internal".
   - FORBIDDEN justifications for diverging: "minimal risk", "behaviorally same", "cosmetic/inaudible", "already equivalent", "defensive", "robustness", "hardening", "prevents a hang/leak/stray", "efficiency", "cleaner", "safer", "structural necessity". If AutoTTS does X — even if X looks like a bug — do EXACTLY X. If AutoTTS does NOT do Y, do NOT do Y.
   - Before ANY behavioral edit: find the exact AutoTTS source, and mirror it byte-for-byte / field-for-field / order-for-order (UI strings, control flow, defaults, method calls, side effects, everything).
   - Saying "already fine" / "inaudible" / "I'll leave it (my decision)" without a full AutoTTS source match IS a violation.
   - Past self-inflicted violations already reverted (commit 3b02624): multilingual whitespace-chunk skip, multilingual empty→emptyList, speakRunnable stop-guard, initAllTTS restoringIndex reset, RestoreInitListener captured-idx guard, restoreEngine field pre-clearing, bindEngineKeepAlive in restore-listener. Do not reintroduce this class of "improvement".
   The user's decision IS AutoTTS's actual behavior; there is no other source of truth.
6. **NO GUESSWORK, ANYWHERE (user rule, 2026-07-29). Every line must trace to AutoTTS source.**
   - I may NEVER "infer", "assume", "reason that it is equivalent", or fill a gap from what seems
     sensible. If I have not SEEN the exact AutoTTS code for a behaviour, I do not write that
     behaviour — I go and find it (CFR → smali → arm64 disassembly of the `.so`).
   - "Behaviourally equivalent", "same outcome either way", "unreachable in practice",
     "redundant but harmless" are NOT reasons to keep my own version. Write what AutoTTS writes.
   - This covers the WHOLE app, every mode (none/dual/auto/google/mixed/multilingual), the
     service, the native code, the UI, the manifest and the resources — not just the parts under
     discussion.
   - Where I previously guessed and later verified, the verification must be recorded (commit
     message or `autotts_reference/ANALYSIS_5.7.7.10_mix.md`) so it is never re-guessed.
   - Known past guesses, all since replaced by verified ports: span cap (now confirmed
     `mov w3, #0x80` = 128), the multilingual latin flag (now `script == 1`), the emitter's
     merge/cap-stretch, `y.g`'s defType, the mix two-stage fallback, `a.c` vs `a.b` selection.
7. **CFR FIRST, SMALI ONLY AS FALLBACK (user rule, 2026-07-29).** Reading order is fixed:
   - **Always start with the CFR `.java` files** in `autotts_reference/decompiled_java/`. Read the
     WHOLE relevant file(s) — all methods, big ones included, unfiltered, byte-by-byte.
   - **Fix everything that CFR alone makes clear.** CFR's accuracy is very high; do not second-guess
     it or go hunting in smali "just to be sure". That wastes the pass and is what the user objected to.
   - **Open smali ONLY when CFR is actually broken or unreadable** for that specific spot — e.g.
     `** GOTO lblNNN`, `// 2 sources`, `** continue`, `ConfusedCFRException`, an obviously wrong
     `varX = varY` alias, or a control-flow shape that cannot be understood as written.
   - When CFR *is* clear, smali is not needed and must not be the basis of the fix.
   - Practical note: CFR often renders as ONE expression what smali scatters across many `cond_*`
     labels (e.g. the onSynthesizeText mode gate) — that is precisely why CFR comes first.
   - Regenerate CFR with the commands in `autotts_reference/README.md`; for stubborn methods add
     `--forcetopsortnopull false --aexagg true` before falling back to baksmali.

8. **HOW RULE 7 IS ACTUALLY EXECUTED (added 2026-07-29 after the user caught me skipping it).**
   The failure mode is not disagreeing with the rule — it is starting to EDIT before the CFR
   read is finished. So the order is mandatory and has no shortcut:
   1. **List the chain first.** Name every CFR file and every method the feature touches
      (the fragment method, the layout XML, the strings, every helper class it calls, and the
      service fields it writes). Write that list down before touching an editor.
   2. **Read all of it in CFR.** Whole methods, no filtering, including the ones that look
      boring. Aliases like `CheckBox cb = findViewById(...)` followed by `other.setChecked(...)`
      are exactly the bugs worth finding — CFR shows them plainly.
   3. **Only then edit.** No file is modified until step 2 is complete for the whole chain.
   4. **Smali is a last resort, and the reason gets recorded.** Allowed only when CFR itself
      says it failed (`ConfusedCFRException` / "Decompilation failed") or renders something
      unreadable. If a smali trip only confirms what CFR already showed, that is a wasted pass
      — say so in the commit so it is not repeated.
   Violations to date: went to smali for `c3.j.R2()` although CFR had already rendered the
   `object` / `checkBox` alias correctly; began editing the Advanced tab before finishing the
   CFR read of `j.E2/G2/H2/J2/M2/N2/B2/P2/Q2`, `c3.t`, `c3.u`, `c3.b0`.

9. **NEVER call `AskUserQuestion` (user rule, 2026-08-06).** The option cards it renders got
   stuck in the user's chat, kept reappearing with a submit button, and blocked them from
   typing. It is denied in `.claude/settings.json` and `~/.claude/settings.json`, but those
   are gitignored/ephemeral, so this rule is the durable record. If something genuinely needs
   the user's decision, ask it as one plain sentence in the reply — no tool, no cards. Same
   for `ShowOnboardingRolePicker` and anything else that renders an interactive prompt.

## UI departures from AutoTTS (user decision, 2026-08-06)
The user has taken the **user interface** out of the AutoTTS-parity rule: *"ab mere hisab se
… sirf user interface change karna hai"*. Logic, service, detection and storage stay exactly
AutoTTS. Only the UI may differ, and only where the user asks.

Recorded so far:
1. **Modes tab — per-mode collapse/expand settings.** AutoTTS shows the active mode's settings
   inline at the bottom, below all five radios and their descriptions, so a TalkBack user
   swipes past everything to reach a spinner. Ours keeps the list exactly as AutoTTS has it —
   **each radio followed by its own description paragraph, as separate `TextView`s** — and adds
   a **collapse/expand disclosure** for the settings:
   - the **selected** mode's settings button sits **on the same row as its radio button**,
     right-aligned, labelled `"Settings"` with `contentDescription = "<Mode> settings"`;
     every other mode's button is `GONE`, and "None" has none. (It used to be a full-width
     button under the description - the user rejected that as too heavy, 2026-08-12.)
   - pressing it expands that mode's settings **in place**, directly beneath the button. No
     dialog, no second screen, no navigation.
   - state is exposed with `ViewCompat.setStateDescription(toggle, "Expanded"/"Collapsed")` —
     the approach Android's accessibility docs prescribe for disclosure controls — plus an
     `announceForAccessibility` on each toggle. Name comes from the button text, role from
     Button, state from stateDescription: WCAG 4.1.2 satisfied.
   - switching modes collapses the mode you left.
   Three things were tried and **rejected by the user after testing — do not reintroduce**:
   descriptions folded into the radio's `contentDescription` (TalkBack then reads a whole
   paragraph before you can move on), the settings in an `AlertDialog` (the list showed
   through above and below it), and the settings as a separate full page (navigating away is
   unnecessary for this).
   The four sections, their spinners, checkboxes and every handler are unchanged; only where
   they are shown moved. Do NOT "restore" this to AutoTTS's bottom-of-page layout.
2. **Voices tab replaced by "Configuration settings" — two screens (user request,
   2026-08-13).** AutoTTS has five tabs; we now have two. The Voices tab first became a
   collapse/expand section on the Modes tab, and **on 2026-08-13 the user replaced that
   toggle with a "Configuration settings" button** that opens a real screen, because the
   inline section felt heavy: *"voice wala jo collapse button hai use jagah per configuration
   settings ka button add kar do … us per click karne se … language ki list … kisi bhi ek
   language per click karunga to vah wala screen khulega jismein voice variant … slider"*.
   - **`ConfigurationActivity`** — a **"Languages" `ExtendedFloatingActionButton`, anchored
     bottom-end**, labelled **"Add language"** (user, 2026-08-13: *"languages ka button
     bottom right corner per hona chahie … primary action"*, and *"languages button ka
     content description change karna hai add language"*). The **visible text** was changed
     rather than only the `contentDescription`: WCAG 2.5.3 *Label in Name* wants the
     accessible name to contain the visible label, so a button reading "Languages" but
     announcing "Add language" would be a defect. Setting the text makes both the same
     string. Google's own wording: a FAB "lets the user perform a primary
     action" and is "typically found anchored to the bottom right"; the **extended** variant
     carries a text label, and that label is the accessibility affordance, so it stays a
     labelled "Languages" control rather than a bare icon. The list scrolls under it with
     `clipToPadding = false` + 88dp bottom padding so the last row is never covered. It is
     **`GONE` unless the mode has a language list**, i.e. never for `dual`/`none` (user:
     *"vah button rakhne ki jarurat kya hai"*), because `buildLanguagesTabView` answers those
     two with "Language selection is not available…" and the button would only ever lead to
     that sentence. Below it are the languages configured for the current
     mode, one button per language, from `voiceLanguageLabels(this, modeInt)`: dual gives
     the two `dualLangList` entries, every other mode gives its selected languages. It
     rebuilds in `onResume`, so returning from the Languages screen or a voice screen, or
     changing the mode, is picked up. The Main Settings tab therefore shows only
     "Configuration settings"; Languages is one level in, not duplicated in both places.
   - **`VoiceSetupActivity`** — the per-language voice screen, `buildVoicesTabView(this,
     prefs, { testTts }, langIndex)` with the same `singleLangIndex` pin the wizard uses, so
     engine/voice, variant, Test, speed, volume, pitch, Default and dedicated engines are
     the untouched Voices code. Its `title` (what TalkBack announces) is read from
     `LangStore.languages` **after** the view is built, in `dualLanguageLabels`'s exact
     `displayName + " (" + iso3 + ")"` format, so no second rebuild is needed.
   - The index passed as `lang_index` indexes `LangStore.languages`, matching the assumption
     the Voices language spinner already makes (`onLanguageSelected(position)` indexes the
     same list the labels came from). Do not "fix" that mapping here alone.
   - Voice, variant, speed, volume and pitch stay **per-language** settings that every mode
     reads (`speedFor`/`pitchFor`/`volumeFor`/`variantFor`), which is why this lives beside
     the modes rather than inside one of them.
   - `buildVoicesTabView` still returns its content root instead of a `ScrollView`; each
     screen wraps it. Every entry point (`buildLanguagesTabView`, `buildVoicesTabView`,
     `voiceLanguageLabels`) does its **own** `LangStore` rebuild — `onlyEnabled = false` for
     the Modes tab and the Languages screen, `onlyEnabled = true` for the voice list — so
     whichever one runs last leaves `LangStore.languages` correct for itself.
     Expanding a mode's settings calls `refreshModeLanguages(mode)` for the same reason.
   - `pageTitles`/`pageIcons` dropped and `ic_tab_voices.xml` is gone with them.
3. **Languages tab folded into the Modes tab, per mode.** Now two tabs: Modes and Advanced
   - the **Licenses tab was removed on 2026-08-12**. `buildLicensesTabView` was kept unused
   in `TabViews.kt` for a while afterwards, in case the user wanted it placed elsewhere; it
   **went with the Compose migration and no longer exists anywhere**. Unlike Voices, the language list **is** per-mode (`buildLanguagesTabView` reads
   `prefs.getReadingMode()` and builds `modeInt`/`required` from it), so it sits **inside each
   mode**, in the order the user asked for: radio → its description → **"Languages"
   collapse/expand** → **"<Mode> settings" collapse/expand**. Only modes that support a
   language list get the button — auto, mix, multilingual (google is `GONE` and shares auto's
   holder via `holderMode`); **dual has none**. Since 2026-08-12 the button lives **inside**
   that mode's expanded settings section, under the mode settings, not directly beneath the
   radio, which is the inline equivalent of the old tab's
   "not available for None and Dual" message. Rebuilt on each expand, same `LangStore.languages`
   reason as Voices. `ic_tab_languages.xml` is gone.
   The list itself changed from `ListView` to a `LinearLayout` of `CheckBox` rows — **required**,
   because a `ListView` cannot measure inside the Modes tab's `ScrollView`. All the underlying
   logic is unchanged (`checkedFlags`, `visibleIdx`, `requiredNow()`, `entry.disabled`,
   `persistDisabled`, search, select-all, clear-all, show-selected). `setRowChecked` sets
   `suppressRowEvents` so a programmatic check does **not** re-enter the handler — that
   reproduces `ListView.setItemChecked`, which never fired `onItemClick`.
4. **"None" AND "Google TTS" are never rows — the list shows exactly four modes**
   (None: *"None ki koi jarurat hi nahi … hata hi dena hai"*; Google, 2026-08-27: *"vah
   already bhale hi rahe but use vahan per nahin dikhna chahie … sirf aur sirf vahi charon hi
   dikhna chahie"*). Dual, Auto, Mixed, Multilingual, and nothing else. Only the UI option is
   gone — modes 0 and 3 still exist in the store and the service, and every
   `readingMode == "none"` / `== "google"` guard elsewhere stays.
   **Google is AutoTTS-faithful, not a departure:** its radio is
   `android:visibility="gone"` in `fragment_modes.xml` and `setVisibility` is never called on
   it anywhere, so AutoTTS never offers it either.
   `ModesScreen.shownMode()` maps a stored `"none"` to `"auto"` and writes it back, because
   None is not a row. **Google is deliberately NOT mapped** (owner, 2026-08-27: *"vahi Google
   wala selected rahana chahie, bus visible nahin hona chahie"*). `LangStore.loadMode` reads
   `auto_mode` with a default of **3** — AutoTTS's own `c3.n.o` — so a fresh install on a
   device with Google TTS starts in Google mode, and AutoTTS starts there too with nothing
   checked in its own list, because its radio is `gone`. Ours now behaves identically: the
   mode stays Google and the list simply shows no checked radio. An earlier version mapped
   google to auto and wrote it back; that stopped the app ever sitting in Google mode, which
   is a behaviour change rather than a UI one, and it was reverted.

7. **Tab title, list buttons and switches (user request, 2026-08-12).**
   - The first tab is titled **"Main Settings"**; the section header inside it still says
     "Modes", because that header labels the radio group, not the tab.
     It was "Main Settings Tab" until 2026-08-13, when the user heard TalkBack say "tab"
     twice: the accessibility role is appended by the service, so a title ending in "Tab" is
     announced as "Main Settings Tab, Tab 1 of 2". **A label must never contain its own role
     word.** Swept the rest of the app for the same mistake — no other user-facing string
     contains button/tab/switch/checkbox/slider/dropdown/menu/radio.
   - **Languages and Voices sit in one row** at the bottom of that tab, Languages first, each
     at half width. Languages **opens its own screen** — `LanguagesActivity`, a plain
     `ComponentActivity` that puts `buildLanguagesTabView` in a `ScrollView`, declared with
     `android:label="Languages"` so TalkBack announces it on entry, and closed by the system
     back gesture. This replaced an inline disclosure the user rejected on 2026-08-12: they
     want the whole separate screen AutoTTS's Languages tab gave. The per-mode Languages
     buttons, holders and `applyLanguagesExpandState` are gone. The second button in that
     row was "Voices" (collapse/expand) until 2026-08-13; it is now **"Configuration
     settings"**, which opens `ConfigurationActivity` — see item 2.
   - **Every checkbox is a `MaterialSwitch`** (`EvSwitch` typealias, `evSwitch(context)`
     factory in `Theming.kt`): the nine Advanced rows, "Use locale spans", "Use dedicated
     engines" and the language-list rows. They are still `CompoundButton`s, so
     `setRowChecked`, `suppressRowEvents` and every existing handler are unchanged.
     `applyAccessibleTheme` gained an `EvSwitch` branch that tints thumb, track and track
     decoration instead of the button drawable — checked thumb `#00325A` on a `#82C7FF`
     track (7.2:1), unchecked `#4FD8EB` thumb and outline on `#2A2D31` (8.1:1 thumb,
     11.0:1 outline on the page) so an off switch is still clearly visible.
   - The CLD3 row's description now leads on performance.

5. **Material 3 accessible dark theme (user request, 2026-08-12).** `AppTheme` now extends
   `Theme.Material3.Dark.NoActionBar` with a fixed dark palette — there is no `values-night`
   override any more, because the theme is dark in both modes by design. The palette lives in
   `res/values/colors.xml` and, for code-built views, in `AppPalette` (`Theming.kt`):
   background `#121212`, surface `#1E1F22`, section headers `#1B2A38`, text `#FFFFFF`,
   primary `#82C7FF`, on-primary `#00325A`, bright control outline `#4FD8EB`.
   Measured contrast: white on background **18.7:1**, light blue on background **10.3:1**,
   button label on button fill **7.2:1**, checkbox border on background **11.0:1** — all WCAG
   AAA; the disabled grey is 3.9:1, above the 3:1 UI-component floor.
   Because every view is built in code, `applyAccessibleTheme(view)` walks the finished tree
   and styles by type — compound buttons get a `buttonTintList` that is bright cyan unchecked
   and light blue checked, buttons get a light-blue fill with dark label text, seek bars get
   a light-blue track and thumb, spinners get a dark popup, and the `SearchView`'s internal
   text, hint and icons are recoloured. `applyPageTheme` adds the background and is what each
   `build*TabView` returns. It is re-run wherever rows are rebuilt (the Languages list filter,
   the required-engines dialog) so late views are covered too. Spinner rows use
   `res/layout/ev_spinner_item.xml` and `ev_spinner_dropdown_item.xml` — white on dark, 48dp
   minimum height, and the dropdown wraps instead of truncating.
   Every control is forced to a 48dp minimum touch target. No `contentDescription`,
   `stateDescription`, `announceForAccessibility` or live region was touched — this change is
   colour, size and background only.

6. **Instant speech start — the two 50 ms posts removed (user request, 2026-08-12).**
   AutoTTS posts the first `speak()` with `t.postDelayed(a, 50L)` and the next-chunk step
   with `b.postDelayed(e$a, 50L)`. The user swipes with TalkBack and wants speech to start
   with no added delay, so **both delays are gone** — this is a deliberate departure and
   must not be "restored" to AutoTTS.
   - First chunk: `speak()` is now called **inline on the synthesis thread**, no handler.
     `onSynthesizeText` already runs off the main thread and is documented to block there,
     and `TextToSpeech` is internally synchronised (`runAction` takes `mStartLock`;
     `mUtteranceProgressListener` is `volatile`, "written from an unspecified application
     thread, read from a binder thread"), so `speak`/`setSpeechRate`/`setPitch`/
     `setOnUtteranceProgressListener` carry no main-thread requirement. Program order on one
     thread gives the listener-before-speak ordering the hop used to provide.
   - Later chunks: the main-thread hop **stays**, at zero delay (`post`, not `postDelayed`).
     It is not cosmetic — `onDone` arrives on a **binder thread** and the next step can create
     or shut down a `TextToSpeech`, which must not happen inside an engine callback.
   - Side benefit: the old 50 ms window let a `stop()` be followed by a `speak()`, because the
     runnable has no stop guard (and must not get one — that was reverted in `3b02624`).
     Inline, the existing `if (isStopped || isFlushed) return` sits immediately before
     `speak()`, so the stray-utterance window closes on its own.

8. **Setup wizard — REMOVED ENTIRELY (user request, 2026-08-18: *"setup wizard hamen
   kahin per bhi rakhna nahin hai, setup wizard pura hata do"*). Do NOT reintroduce it,
   and do not treat anything below as current.** Gone with it: `SetupWizardActivity.kt`
   (including `ModeChoiceStep`), its manifest entry, the launch from
   `MainActivity`'s scan callback, the Advanced tab's whole "Setup" section and its
   `ic_auto_fix` icon, and `isSetupDone()`/`setSetupDone()` — the `setup_done` pref now
   has no reader. The description below is kept only as a record of what once existed.

   ~~**Setup wizard (user request, 2026-08-12).**~~ `SetupWizardActivity` — a plain
   `ComponentActivity` with a **dynamic step list**, one heading + a scrolling body + a
   Back/Next row. The steps are
   **mode → settings → languages → one voice step per language**:
   - `baseIds()` is `["mode","settings","languages"]`, but **the languages step is dropped
     for Dual** (user request, 2026-08-12: *"dual board select karega to … vah language wala
     step nahin aaega"*), mirroring `buildLanguagesTabView`, which answers "Language
     selection is not available for \"None\" and \"Dual languages\" modes." for exactly
     `dual`/`none`.
   - `stepIds()` = `baseIds()` + **one `"voice"` step per entry of `voiceLangLabels`**
     (user request, 2026-08-12: *"jo bhi selected language hogi uske TTS setup aaega …
     Charon language ke liye अलग-अलग next karna padega"*). Dual therefore ends with exactly
     two voice steps — English and the dual language — because `LangStore.dualLangList`
     returns only those two.
   - `voiceLangLabels` comes from **`voiceLanguageLabels(context, modeInt)`**, which was
     lifted out of `buildVoicesTabView`'s local `applyLanguageList()` so the wizard and the
     Voices section share one list; `applyLanguageList()` now just calls it and sets the
     adapter. It is re-read at the top of `showStep()` and again before Next decides
     finish-vs-advance, so toggling languages immediately changes the step count, the
     "step N of M" heading and the Next/Finish label.
   - A voice step is `buildVoicesTabView(this, prefs, { testTts }, voiceIndex)` — the new
     **`singleLangIndex`** parameter hides the "Select language" label and its spinner and
     pins the view to that one language (`setSelection` + `onLanguageSelected`). Everything
     else is the untouched Voices code: engine/voice spinner, variant spinner, Test, speed,
     volume, pitch, Default, dedicated engines. `addSmallText` now returns its `TextView`
     so the label can be hidden.
   - The wizard owns a `TextToSpeech(this, null, "com.tts.easyvoice")` exactly like
     `MainActivity.newTestClient()`, so **Test speaks** on the settings and voice steps.
   - Steps 2 and 3 embed `buildModesTabView(..., settingsOnlyForMode)` and
     `buildLanguagesTabView(this, prefs)` — the same functions the Main Settings tab and
     `LanguagesActivity` use, nothing duplicated. Both read `prefs.getReadingMode()`, which
     is why `setReadingMode(chosenMode)` is written when **leaving step 1**.
   - Each of `buildLanguagesTabView`, `buildVoicesTabView` and `voiceLanguageLabels` does
     its **own** `LangStore` rebuild (`onlyEnabled = false` / `true`), so the order in which
     the wizard visits them does not matter — the same reason the tabs rebuild on each open.
   - `stepIndex` is clamped to `ids.lastIndex` in `showStep()`, for the case where the step
     list shrinks (a language disappearing while the wizard is open).
   - **Swipe navigation (user request, 2026-08-13; reworked the same day for TalkBack).**
     A horizontal swipe on the step body moves between steps, and the Back/Next buttons stay.
     Both paths call the same `goBack()`/`goNext()`, so there is one implementation.
     `GestureDetector` was avoided on purpose: `onFling`'s first parameter became `@Nullable`
     in a later API than the API-15 check jar exposes, so the override signature could not be
     verified locally.
     **What the first attempt got wrong.** Google's TalkBack help states that with TalkBack on
     *"most one-finger gestures become two-finger gestures … put two fingers on the screen and
     drag"*. The first version read `event.action` and `event.x`, which describe **pointer 0
     only** — under a two-finger drag the tracked finger can lift as `ACTION_POINTER_UP` while
     `event.x` reports whichever pointer currently sits at index 0, so the measurement was
     wrong exactly in the TalkBack case the user tested. It now uses `actionMasked`, records
     the pointer id from `ACTION_DOWN`, settles on whichever of `ACTION_UP`/`ACTION_POINTER_UP`
     lifts **that** id, reads `getX(actionIndex)`, and resets on `ACTION_CANCEL`.
     **The documented fix, which the gesture alone can never be.** For gesture-only flows the
     Views guidance is `ViewCompat.addAccessibilityAction(view, label, action)` — *"your app
     can expose the actions in a way that is accessible to users of accessibility services"*.
     `stepHeading` therefore carries **"Next step"** and **"Previous step"** actions, so
     TalkBack, Voice Access and Switch Access users reach them from the Actions menu on the
     heading they already land on at every step. The actions are added **once** in `onCreate`,
     not per step — `addAccessibilityAction` allocates a new action id per call, so repeating
     it would stack duplicates.
     Source: `support.google.com/accessibility/android/answer/6151827`,
     `developer.android.com/guide/topics/ui/accessibility/views/principles-views`.
   - **Next must stay snappy (user request, 2026-08-13: *"next karta hun to thoda bhari
     bhari sa lagta hai"*).** `refreshVoiceLangs()` runs **once per Next** — in the Next
     handler, before it decides finish-vs-advance — plus once before the first `showStep()`.
     It used to also run at the top of `showStep()`, so every press rebuilt the language list
     twice. Each rebuild goes through `LangStore.persistLanguages`, whose `editor.commit()`
     is a synchronous main-thread disk write, so the duplicate was felt. `commit()` itself
     must NOT be changed to `apply()` — that is AutoTTS-mirrored storage code. Back does not
     refresh at all, since it cannot change the language selection.
   - **2026-08-13, second pass on the same complaint.** Two more sources of the stall:
     - `refreshVoiceLangs()` now runs **only while on a base step**
       (`if (stepIndex < baseIds().size)`). Moving between two voice steps cannot change the
       language list, so the rebuild — and its `commit()` — was pure waste on exactly the
       presses the user makes most.
     - the Voices view is **built once and re-pinned**, not rebuilt per language. The new
       `VoicePin` holder is handed to `buildVoicesTabView`, which fills `pin.select` with
       the same `setSelection` + `onLanguageSelected` pair the language spinner uses. So the
       2nd..Nth voice step costs one language switch instead of a whole view construction
       plus two `commit()`s. `refreshVoiceLangs()` clears `voicesView`/`voicePin.select`, so
       changing the language selection still forces a fresh build.
   - Step 1 lists **every mode** as a radio (Google TTS skipped when
     `com.google.android.tts` is absent), each followed by its description — the same
     strings the Main Settings tab uses, because `modeRowSpecs` was lifted to a **top-level
     `val` in `TabViews.kt`** and both read it. Exclusivity is manual (a `suppress` flag +
     a loop clearing the others), as the rows are not in a `RadioGroup`.
   - Next → step 2 calls `prefs.setReadingMode(chosenMode)` and shows **that mode's own
     settings**, by reusing `buildModesTabView` through a new
     `settingsOnlyForMode: String? = null` parameter. When it is non-null the function skips
     the "Modes" header, the radio rows, the descriptions and the Languages/Voices row, then
     does `onModeSelected(settingsOnlyForMode)`, hides that mode's toggle button and force-
     expands its section. Nothing is duplicated — the wizard runs the already-verified
     settings code.
   - Next reads "Finish" on step 2; it writes the mode, `prefs.setSetupDone(true)`,
     `LangStore.persistAll` and finishes. Back on step 2 returns to step 1; on step 1 it
     closes the wizard.
   - Accessibility: the heading is a real heading (`ViewCompat.setAccessibilityHeading`) and
     each step change calls `announceForAccessibility` on it, so TalkBack states which of the
     two steps you are on.
   - First run only: `MainActivity` launches it from the `EngineFinder.scanLanguages`
     completion callback when `!prefs.isSetupDone()` — after the scan, so the mode settings
     have languages to show. The flag is `setup_done` in `SharedPrefsManager`.
   - Re-runnable: the Advanced tab's **first** section is now "Setup", with a
     "Setup wizard" button that starts the activity again.

## Responsive layout + touch targets (user request, 2026-08-13, guidelines read first)
Sources read for this, not recalled: `developer.android.com/develop/ui/views/layout/window-size-classes`,
`.../guide/topics/ui/accessibility/apps`, `.../design/ui/mobile/guides/layout-and-content/grids-and-units`,
`.../docs/quality-guidelines/large-screen-app-quality`.

What they actually say, with the numbers:
- **Width window size classes**: compact `< 600dp`, medium `600–839dp`, expanded `840–1199dp`,
  large `1200–1599dp`, extra-large `≥ 1600dp`. Google's advice is to "optimize your layout for
  the expanded width size class" first.
- **Touch targets**: "at least 48dp×48dp. Larger is even better."
- **Baseline grid**: 8dp, with 4dp for finer steps.
- **Text**: `sp`, so it follows the user's font-size setting.
- **Large-screen tiers**: Tier 3 is running full screen without letterboxing; **Tier 2 is
  "layout optimizations implemented for all screen sizes"**. Recommended test sizes:
  841×701, 1024×640, 1280×800, 1600×900 dp.

What was already satisfied, verified rather than assumed:
- the manifest sets no `screenOrientation` and no `resizeableActivity`, and `targetSdk` is 34,
  so activities are resizeable and rotate freely — Tier 3;
- `applyAccessibleTheme` already forces `minimumHeight = 48dp` on `EvSwitch`, `CompoundButton`,
  `Button` (plus `minimumWidth`), `SeekBar` and `Spinner`, and it walks the whole tree from
  `applyPageTheme`, so every screen is covered — including the small `-`/`+` slider buttons,
  which are `Button`s and so are lifted from `buttonStyleSmall` back to 48dp;
- every text size comes from `setTextAppearance(android.R.style.TextAppearance_*)`, i.e. `sp`.

What was missing and is now fixed:
- **Tier 2.** Every page was a full-width `ScrollView`, so on a 1280dp tablet or a 1600dp
  Chromebook each row stretched edge to edge. `applyResponsiveWidth(root)` now sets symmetric
  horizontal padding of `(screenWidthDp - 840) / 2` whenever the window is wider than the
  **expanded breakpoint**, so the content column is capped at 840dp and centred; below 840dp
  nothing changes and the single pane fills the window. `840` is the documented expanded
  breakpoint, not an invented number. It reads `resources.configuration.screenWidthDp`, which
  is the *window* width (correct in split-screen), and the activities are recreated on
  configuration change because no `android:configChanges` is declared, so it recomputes on
  rotate/fold/resize. Applied at every full-screen `setContentView`: `MainActivity`,
  `SetupWizardActivity`, `ConfigurationActivity`, `VoiceSetupActivity`, `ModeSettingsActivity`,
  `LanguagesActivity`. The required-engines **dialog is deliberately excluded** — dialogs are
  already width-constrained by the platform.
- Setting rather than adding the padding keeps `applyResponsiveWidth` idempotent; no page root
  carries horizontal padding of its own, checked before relying on that.
- The `SearchView`'s clickable icons (`search_close_btn`, `search_button`, `search_go_btn`,
  `search_voice_btn`) now get the 48dp minimum too; `search_mag_icon` is decorative when the
  view is permanently expanded, so it is only recoloured.

## Latency: what was MEASURED, and what is still unexplained (2026-09-01)
The owner reported slow language switching with Gujarati/Hindi/Marathi in one message, and a
long wait before a 25-to-30 paragraph text starts. Researched and measured before touching
anything, per rule 6. **Nothing was changed, because nothing measured was ours.**

**Our own code is fast, and this is now measured, not argued.** New harness
`tools/verify/latency/run.sh` links the REAL native core with CLD2/CLD3 and a genuine JNIEnv
and times the work that must finish before the first word:

| paragraphs | chars | chunks | segment | detect | TOTAL |
|---|---|---|---|---|---|
| 10 | 1,758 | 29 | 0.2 ms | 1.0 ms | **1.2 ms** |
| 30 | 5,310 | 81 | 0.5 ms | 1.5 ms | **2.0 ms** |
| 60 | 10,620 | 161 | 0.6 ms | 2.6 ms | **3.3 ms** |

Run to run the totals move by a few tenths of a millisecond, which is the point: the whole
measurement is smaller than the noise in anything a person can hear. Allow an order of
magnitude for a phone and it is still tens of milliseconds.
**Segmentation and detection are not the delay.** Measure here before blaming chunking again.

**From the owner's own logs** (122 utterances across three): start-to-first-speak is a median
of **8-12 ms**, worst 54 ms. One language switch — `onDone` to the next `speak 2:` — is
**15-44 ms**, and that path writes **32 log lines**, each a separate open/write/close in
`EasyVoiceLogger.writeLine` plus `rotate()`'s `exists()` + `length()`. That is `c3.p.h`
byte for byte, so it is parity, not a defect — but it is most of the 15-44 ms when logging is
on. **No log the owner has sent contains the slow case**; the longest utterance in any of them
is 404 characters.

**The 4000-character limit is NOT our bug — verified, so do not "fix" it.**
`TextToSpeech.speak`'s contract says *"No longer than getMaxSpeechInputLength() characters"*
(4000), and AOSP `TextToSpeechService.SynthesisSpeechItem.isValid()` rejects `> 4000` with
`ERROR_INVALID_REQUEST`. But **that same check runs on OUR service for incoming text**, so
`onSynthesizeText` can never receive more than 4000 characters and a merged chunk cannot
exceed it either. A screen reader splits a long document into requests before we ever see it.
Read from the AOSP mirror, not from memory.

**Foreground service, checked against the current rules.** `mediaPlayback` is right for us:
its description is *"Continue audio or video playback from the background"* and its runtime
prerequisites are **None**. We declare the type and `FOREGROUND_SERVICE_MEDIA_PLAYBACK`, and
pass `FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK` on API 34+. A TTS engine is **not** in the
Android 12+ exemption list for starting a foreground service from the background, so
`startForeground` can throw `ForegroundServiceStartNotAllowedException` — `startForegroundIfPossible`
already catches and logs it, so it degrades to no notification rather than a crash.
`isForegroundActive()` doing a `getActiveNotifications()` binder call **per utterance** is
`AutoTtsService.a0()` called from exactly where AutoTTS calls it, including the `&&`
short-circuit that still calls it when the setting is off.

**What is left, and it needs a log rather than a guess.** Everything on the hot path measured
so far is either fast or AutoTTS-faithful, so the audible gap is most likely the target
engine's own time-to-first-audio: we wait for `onDone` of chunk N before asking engine N+1 to
start, and nothing overlaps. That is AutoTTS's architecture too. **Do not "optimise" it on
theory** — ask for a log of the actual slow message first, with logging on, and measure
`onDone` to `speak 2:` against `speak 2:` to the next `onStart`.

## Detection speed: CLD3 is 5x CLD2, and that is the whole story (measured 2026-09-01)
The owner asked for language detection to be made as fast as possible, and pointed at eSpeak
NG's source as a place to learn from. Both were done properly: an instruction-level profile
of our own pipeline, and a read of eSpeak's synthesis loop. **Read the numbers before
changing anything here again.**

**`tools/verify/latency/run.sh` now times BOTH detectors.** It had only ever measured CLD2,
which hid the arm the owner may actually be running:

| paragraphs | chars | chunks | segment | **CLD2** | **CLD3** |
|---|---|---|---|---|---|
| 10 | 1,758 | 29 | 0.1 ms | 0.1 ms | **2.5 ms** |
| 30 | 5,310 | 81 | 0.3 ms | 1.1 ms | **6.6 ms** |
| 60 | 10,620 | 161 | 0.6 ms | 2.6 ms | **8.6 ms** |

**So the Advanced tab's "Use CLD3" switch costs about 5x**, and on a phone (an order of
magnitude slower than this container) that is tens of milliseconds before the first word of a
long text. With CLD2 it is single-digit milliseconds. That switch is the one real lever on
detection speed, and it belongs to the owner.

**Where the time goes, from `valgrind --tool=callgrind` over the whole harness — not from
reading the code and guessing:**
- `SparseReluProductPlusBias` is **40.67% of every instruction the program executes**. That
  is CLD3's hidden layer. Nothing else is within an order of magnitude of it.
- **294 spans produce 464 net evaluations.** `FindTopNMostFreqLangs` runs 294 times (once per
  span) and `FindLanguageOfValidUTF8` 464 times, so **58% of spans pay the net twice**: the
  hinted top-3 loop finds no candidate the user has enabled, and `cld3DetectRaw` falls through
  to `FindLanguage`. `SparseReluProductPlusBias` runs 928 times = two layers per evaluation.
- CLD2 for the same 294 spans: `ExtDetectLanguageSummary` 294 calls, and its whole cost
  (`DocTote::Sort`, `GetOneScriptSpan`, `GetOctaHits`, `GetQuadHits`, the UTF8 scanners) is
  about 2.5M instructions — **beaten by our own segmenter**, where `buildMixChunks` alone is
  1.58M.

**The obvious halving was investigated and REJECTED, and the reasoning is recorded so it is
not re-attempted casually.** For a text containing exactly one script span,
`FindTopNMostFreqLangs(t, 3)[0]` and `FindLanguage(t)` evaluate the net on byte-identical
text: `FindLanguage` concatenates the lowered script spans and squeezes the result, TopN
squeezes each span, and with one span those are the same bytes through the same
`CheapSqueezeInplace(ptr, len, 0)` into the same `SelectTextGivenBeginAndSize` (TopN's
`SelectTextGivenScriptSpan` is a one-line delegate to it). So the second run looks redundant.
**It was not taken, for two reasons.** The single-span condition cannot be tested from
outside CLD3's public API, and TopN reports `probability = prob_sum / byte_sum`, i.e.
`(p * n) / n` in float, which is not guaranteed to be bit-identical to `p` -- and
`is_reliable` is `probability >= 0.7f`, so a span sitting on the threshold could flip.
Trading a proven detector for a 37% saving on a switch the owner can turn off for a 5%
saving is the wrong trade, and "behaviourally equivalent" is a forbidden justification here.
CLD3 is vendored by a CI `git clone`, so it cannot be patched either.

**One change was made, and it is provably dead work, not an optimisation of behaviour.**
`cld3DetectRaw` built its `hinted` set -- a comma split plus an `unordered_set` of up to 64
`std::string`s -- on **every** call, including the window site where `useHints` is false and
the set is unreachable behind `useHints && !hinted.empty()`. The window site runs once per
64-character window in auto mode, so a long utterance rebuilt and discarded that list hundreds
of times. It is now built only when `useHints` is true. Proven by
`tools/verify/cld3span/run.sh` (all cases pass) and `tools/verify/segmenter/run.sh`
(identical over 163,296 cases).

**eSpeak NG, read rather than recalled** (`raw.githubusercontent.com/espeak-ng/espeak-ng/master`;
`codeload.github.com` is 403 from this proxy, so files were fetched one at a time):
- `src/libespeak-ng/speech.c` `Synthesize()` is **clause at a time**. `SpeakNextClause(0)`
  reads ONE clause, `WavegenFill()` fills `outbuf`, and `synth_callback(outbuf, length, ...)`
  hands that buffer out **immediately**; the next clause is only read once the current one has
  finished generating. `outbuf_size` is derived from a millisecond buffer length, so audio
  starts flowing after tens of milliseconds rather than after the whole text.
- `src/libespeak-ng/fifo.c` is a command queue with a dedicated `say_thread`, so in async mode
  `espeak_Synth` enqueues and returns and the caller never blocks.
- **What that teaches, and why it does not transfer.** eSpeak never precomputes the whole
  text; we build every chunk and detect every language before `speakChunk(true)`. But that
  entire precompute is the "segment + CLD2" column above -- **1.4 ms for 30 paragraphs** --
  so making it lazy would save 1.4 ms and diverge from AutoTTS, which builds its list up
  front too. eSpeak's other lesson, streaming the first buffer early, cannot apply at all:
  eSpeak IS a synthesiser and owns its samples, while we are a proxy that calls
  `TextToSpeech.speak()` on another engine and never touches audio. There is no `outbuf` here
  to hand out sooner.

**So what is left is the engine switch, not detection.** We wait for `onDone` of chunk N
before asking engine N+1 to start, and `onLoadLanguage` on that path does binder calls into
another app's TTS service. That is AutoTTS's architecture as well. Measure it from a device
log -- `onDone` to `speak 2:`, and `speak 2:` to the next `onStart` -- before touching it.

## Runs 795-797 went red on DISK, not on anything we wrote (fixed 2026-09-01)
The owner reported failing builds. **The APK was fine every time** -- `build-795`, `build-796`
and `build-797` are all on the Releases page, published by the `build` job, which passed. What
went red was the second job, `accessibility`, and only that.

**The cause, and it is one line buried a thousand lines above the noise.** The job log ends in
hundreds of `adb: device 'emulator-5554' not found` and `Timeout waiting for emulator to boot`,
which look like KVM or a missing system image and are neither. The real line is at emulator
start:

    FATAL | Not enough space to create userdata partition.
            Available: 6881.56 MB at /home/runner/.android/avd/test.avd, need 7372.80 MB.

**Short by 491 MB.** The system image installed, `avdmanager create avd` succeeded, the KVM udev
rule was already in place -- the emulator simply refused to make its partition and exited, and
everything after that is adb talking to a process that was never there. **When this job fails,
grep the log for `FATAL` before reading a single `adb` line.**

**Nothing in this repository changed.** Run 794 on 28 August passed with `build.yml` byte for
byte identical. What moved is the hosted runner image, which keeps growing while the `pixel_6`
userdata partition stays 7.2 GB; by the time the emulator starts, this job has already spent its
budget on NDK 29, the protobuf clone, Gradle and the system image.

**The fix** is a `Free disk space for the emulator` step before the NDK install, removing
preinstalled toolchains a TTS build provably never touches -- .NET, Haskell, Swift, PowerShell,
node_modules, CodeQL, and the Docker image cache -- each with `|| true` so a path vanishing from
a future image cannot fail the run, and `df -h /` printed on both sides so the next failure
states its own numbers. **NEVER add `/opt/hostedtoolcache/Java_*` (JAVA_HOME) or
`/usr/local/lib/android` (ANDROID_HOME) to that list**; they are what the job runs on.

`disk-size` on `reactivecircus/android-emulator-runner` is a real input and would also work by
shrinking the partition, but freeing space leaves the emulator exactly as it was and gives
headroom for the next image bump, so that is what was done rather than capping the AVD.

**Do not answer this by adding `continue-on-error`.** The rule at the top of this file stands:
an accessibility regression is a real defect. This was an infrastructure failure wearing its
costume, and the way to tell them apart is the `FATAL` line.

## Stop, and the language-switch stall (owner request, 2026-09-02)
Three changes, all DELIBERATE DEPARTURES from AutoTTS, all asked for directly after the
owner compared us with eSpeak NG's Android service. Do not "restore" any of them.

**The report:** *"kabhi kabhar to vah stop nahi hota hai"*, and a *"halka sa delay"* when the
voice changes between Hindi and Gujarati -- **both on Google TTS**, i.e. one engine.

### 1. onStop no longer asks isSpeaking() first
`AutoTtsService.java:1918` gates its stop on three conditions and we carried all three:

    if (f.get(i3).f() != 2 || !f.get(i3).g || !f.get(i3).g().isSpeaking()) continue;

**The third one is the defect.** `isSpeaking()` is a binder query into ANOTHER app's TTS
service and answers true only while that engine is actually producing audio. Between our
`speak()` and the engine really starting it answers **false**. A screen-reader user swiping
quickly lands `onStop()` inside exactly that window: the guard fails, `stop()` is never
called, and the engine then starts speaking with nothing left to cancel it.

eSpeak NG's whole `onStop` is the counter-example, and it is three lines:

    protected void onStop() { Log.i(TAG, "Received stop request."); mEngine.stop(); }

no state query at all. It also carries a SECOND, independent net in `onSynthDataReady` --
if `mCallback.audioAvailable(...)` returns non-SUCCESS it calls `mEngine.stop()` too,
commented *"A stop normally reaches the engine through onStop(); stopping here as well
covers a failure that arrives without one."* **We cannot copy that half** -- we never
produce audio and have no `audioAvailable` -- so the guard removal is the whole fix.
`TextToSpeech.stop()` on an idle engine is a documented no-op returning SUCCESS, so asking
unconditionally costs one harmless binder call.

**The empty-text path at the other `stopAllTts` site still has the guard.** It is a
different trigger (`q0(FALSE)`, which flushes with `speak("", QUEUE_FLUSH)`) and was not
part of the request. Left alone deliberately.

### 2. the stop pool starts with a live thread
`c3/k0.java:78` is `ThreadPoolExecutor(0, 5, 60s, LinkedBlockingQueue, h0)` with daemon
"TtsStop" threads and `k0.m()` is `i.execute(new i0(this))`. Queuing is RIGHT and stays --
`onStop()` must return promptly, so the binder call cannot run on the caller's thread. But a
core of **0** means the first stop after an idle spell must construct a thread before it can
issue the stop. Core is now **1** and prestarted.

### 3. the engine's voice list is cached -- this is the language-switch stall
`loadVoice` (= `f0`) calls **`TextToSpeech.getVoices()`** whenever the wanted variant differs
from the current one. That is a binder call marshalling the engine's ENTIRE voice set --
hundreds of `Voice` objects for Google TTS, each with a name, Locale, quality, latency and a
feature `Set`. And it runs **ON THE MAIN THREAD**: `onDone` posts to the main looper, and
`onLoadLanguage` -> `loadVoice` runs inside that post.

So Hindi -> Gujarati on ONE engine paid a full voice-set marshal on the main thread, every
switch. **This only bites when the language has a named variant**; at `"*Default"` the branch
is just `setLanguage`, which is why it is a *slight* delay rather than a stall.

The list is now cached per `EngineWrapper` and cleared wherever `tts` is replaced (the two
init sites and `restoreEngine`), because a new `TextToSpeech` is a new connection and the old
`Voice` objects belong to the old one. A variant **not** found in the cache re-queries once
and rescans, so a voice installed while the service is alive is still found -- that path
costs exactly what every call used to cost, and it is the rare one.

**What was NOT changed, and why it must not be:** the proxy hop itself. eSpeak has zero
latency because it IS the synthesiser -- text into its own wavegen, audio straight out, one
process. We call `TextToSpeech.speak()` on another app and wait for its audio. That hop is
what this app IS, and it is AutoTTS's architecture too. The two 50 ms `postDelayed` calls
were already removed on 2026-08-12; the owner's own logs show start-to-first-speak at a
median of 8-12 ms on our side. Everything beyond that belongs to the target engine.

## CLD3 calls Hindi "Marathi", and it is the MODEL, not our plumbing (2026-09-02)
The owner reported it precisely: *"CLD2 bahut acche se read kar raha hai, CLD3 mein gadbadi
hai"*, and named the sentence. Their log (`id: 614`, mixed mode, enabled = eng on Eloquence +
guj/hin/**mar** on Google) reads:

    जहाँ 𝙌𝙪𝙖𝙡𝙞𝙩𝙮 और 𝙌𝙪𝙖𝙣𝙩𝙞𝙩𝙮 दोनों मिलें  en dash  वही असली चैनल होता है!”

and the chunks it produced were

    1 जहाँ            hin  ok        5 दोनों मिलें        hin  ok
    2 Quality         eng  ok        6 en dash           eng  ok
    3 और              hin  ok        7 वही असली चैनल...   mar  WRONG
    4 Quantity        eng  ok

so the tail switched to `mr-in-x-mrc-local` mid-sentence. (Note chunks 2 and 4: the
maths-bold-italic 𝙌𝙪𝙖𝙡𝙞𝙩𝙮 normalised to "Quality" correctly, so `clsCLD2.a` is fine.)

**Reproduced against the real detectors, not argued.** A probe through
`tools/verify/cld3span/` with the owner's exact enabled set `{en, gu, hi, mr}`:

    TAIL  CLD2 -> hi
    TAIL  CLD3 -> mr

and CLD3's own numbers for that chunk:

    0  mr   p=0.712  reliable=1  proportion=1.000
    1  und  p=0.000  reliable=0
    2  und  p=0.000  reliable=0
    FindLanguage: mr p=0.712 reliable=1

**Hindi is not in CLD3's top-3 at all.** There is no better candidate to prefer, so this
cannot be fixed by reordering, by the reliability gate (0.712 clears CLD3's own 0.7
threshold, if only just) or by the script check (Marathi IS Devanagari, script 4, same as the
span). Our CLD3 arm is doing exactly what the design table above prescribes.

**Why CLD2 gets it right, and why that is luck rather than skill.** `setLanguageHints` keeps
a per-script hint only where **exactly one** enabled language uses that script. Hindi and
Marathi are both Devanagari, so `matched[4] == 2` and `scriptLanguageHint[4]` goes back to
UNKNOWN -- CLD2 runs unguided and its own model happens to answer `hi`.
`scriptLanguageFallback[4]` is HINDI purely because `kScriptLangPairs` lists
`{4, HINDI}` before `{4, MARATHI}`.

**Three fixes were considered and all REJECTED. Do not implement them later.**
- raise the reliability bar above 0.712 -- arbitrary, and rejects many correct answers;
- override CLD3 with the per-script fallback whenever two enabled languages share a script --
  that is every Hindi+Marathi, every Russian+Ukrainian, every Chinese+Japanese user, and it
  would make a genuine Marathi sentence read as Hindi. It destroys CLD3 for exactly the
  people who need it;
- prefer the table-order language among close candidates -- there are no close candidates.

**The owner then asked, reasonably, whether CLD3 could simply be made to behave like CLD2.
That was researched properly and the answer is NO. Here is the evidence, so it is never
re-opened on a hunch.**

`FindLanguageOfValidUTF8` computes the FULL 109-language score vector
(`network_.ComputeFinalScores(features, &scores)`), takes the argmax and discards the rest;
both it and `GetLanguageName` are **private**. A diagnostic build of
`tools/verify/cld3span/` with `#define private public` peeked at that distribution for the
failing chunk:

    0  mr   p=0.99991
    1  hi   p=0.00006      <- sixteen thousand times less likely
    2  is   p=0.00003

**CLD3 is 99.99% certain it is Marathi.** There is no close second, so there is nothing to
tie-break, no threshold to tune and no candidate to re-rank. Any rule that produced Hindi
here would have to ignore the model outright -- and would then read genuine, unambiguous
Marathi as Hindi too. At that point CLD3 is not being used at all; it is the per-script
fallback table with extra steps and five times the cost.

(The 0.712 that `FindTopNMostFreqLangs` reports is the AGGREGATED figure,
`prob_sum / byte_sum` across script spans; the raw softmax is 0.99991. Do not confuse them.)

**And the speed premise is backwards, which matters because it is why the owner wanted to
keep CLD3.** Measured 2026-09-01, 30 paragraphs: **CLD2 1.1 ms, CLD3 6.6 ms**. CLD3 is
about **5x SLOWER**, not faster -- `SparseReluProductPlusBias` is 40.67% of all instructions.

**What DOES fix it, with no code change, and it is the owner's lever:** untick **Marathi**
in the Languages screen. Then `mr` is not in the hint list, `isHinted` rejects it, and the
per-script fallback resolves Devanagari to Hindi, because `kScriptLangPairs` lists
`{4, HINDI}` before `{4, MARATHI}`. Verified both ways and now asserted permanently in
`tools/verify/cld3span/run.sh`:

    Hindi tail, CLD2, mr also enabled   -> hi
    Hindi tail, CLD3, mr also enabled   -> mr
    Hindi tail, CLD3, mr NOT enabled    -> hi
    Hindi tail, CLD2, mr NOT enabled    -> hi

**The owner's next idea was Unicode**: CLD2 does better, AutoTTS uses Unicode well, so
something Unicode-shaped must be missing from the CLD3 path. Checked, and there is no gap:
- the fancy-letter normaliser (`clsCLD2.a`) is **proven** identical over all 1,114,112 code
  points and runs before both detectors, in `buildMixChunks` and `detectLanguageRuns` alike;
- at the span site **both arms get the same bytes** -- `text[start .. start+detectBytes]`;
- AutoTTS's genuinely Unicode-driven fallback, `a.e(cp, n.f)`, belongs to `clsCLD2.d`, the
  auto/Google WINDOW path, and ours is proven equal there over 1,114,112 code points x 45
  enabled sets. Mixed mode uses the per-script fallback instead, which we also have.
The failing text is plain Devanagari that is valid in both languages -- no character in it
distinguishes Hindi from Marathi, so no Unicode rule could.

**One idea from that line of thinking WAS promising and was measured, then rejected.** We
build CLD3 with `NNetLanguageIdentifier(0, 1024)` while its own default minimum is
`kMinNumBytesToConsider = 140`; below the minimum it returns `Result()` = "und", which our
span site folds to `"un"` and resolves through the per-script fallback. Raising it looked
like a clean fix. Measured across thresholds:

    text                  bytes  min=0  min=40  min=60  min=80
    hindi tail (the bug)     56  mr     mr      und     und
    real marathi            112  mr     mr      mr      mr
    short marathi            41  mr     mr      und     und

At `min = 60` the reported sentence is fixed **and 41-byte Marathi breaks**, becoming "und"
and then Hindi. **Both detectors get short Marathi right today**, so that trade is one
sentence gained for a whole language's short phrases lost. **Do not raise `min_num_bytes`.**

**Measured at the span site with the owner's exact set `{en, gu, hi, mr}`, the two detectors
AGREE on five of six realistic cases** -- real Marathi, short Marathi, short Hindi, short
English and short Gujarati all match; only the reported Hindi tail differs. Those agreements
are now asserted in `tools/verify/cld3span/run.sh` so a future change cannot quietly break
Marathi while chasing this sentence.

### THE FIX (owner request, reaffirmed twice: it must be fixed INSIDE CLD3)
The owner rejected two of my proposals outright and was right both times. First, telling
them to untick Marathi: *"kisi ko char language detect karni hai to vah to enable rakhega
na, to vah to galat tarika hai"* -- enabling the languages you read is correct usage, not a
misconfiguration. Second, and this one I had already started writing: routing shared-script
spans to CLD2 under the CLD3 switch. *"Agar user ne CLD3 enable kara hai to CLD3 hi chalna
chahiye."* Reverted before it went anywhere. **There is no CLD2 anywhere in the CLD3 path.**

**What the measurement finally showed, and it was in an earlier probe I had not chased:**

    the 56-byte tail alone                             -> mr  p=0.712
    the same tail WITH the utterance's Devanagari       -> hi  p=1.0000
    the same, doubled                                   -> hi  p=1.0000

Same model, same enabled set, same span. **Only the amount of text changed.** CLD3 is not
broken; it is being asked with too little text -- and the app is what makes it too little.
`buildMixChunks` cuts an utterance into per-script chunks BEFORE detection, so one Devanagari
sentence reaches the detector as four fragments of 3 to 56 bytes. CLD2's n-gram tables
tolerate that; a neural net does not.

**So the span is widened before it is detected, and only in the CLD3 arm.**
- `buildMixChunks` stores the whole normalised utterance in `detectContextText` -- the same
  bytes the chunks are substrings of, which is why the staleness guard is a plain
  `context.find(span)`. It is refused when it does not match, which is what keeps the
  auto/Google aggregate detector (which never runs the chunk builder) from picking up a
  context left over from an earlier utterance.
- At the span site, a span **shorter than CLD3's own `kMinNumBytesToConsider` (140)** is
  detected against the utterance's text **in that span's script**. 140 is CLD3's number, not
  one of ours; we construct the identifier with 0 precisely so short spans still get an
  answer instead of "und".
- The extractor matches ASCII letters directly (the scanner classifies those in its own
  `& 0x5F` fast path and never asks `classifyScript`) and everything else through
  `classifyScript`, the same ladder the span boundaries were drawn with. Spaces are kept so
  n-grams do not run together; digits and punctuation are dropped.
- **The span still gets the answer. Only the evidence is wider.** CLD2's arm is untouched.

**Where the store lives is load-bearing:** immediately above `buildMixChunks`, not beside the
language-hint tables where the rest of the detector state sits, because
`tools/verify/make_core_inc.py` slices the core `--until buildMixChunks` for the segmenter
harness. Anything the chunk builder calls must be defined above it or that harness stops
linking. It did, once.

**Proven, not asserted.** `tools/verify/cld3span/run.sh` now runs the real sequence --
`processDirect` first, then per-chunk detection, exactly as the app does:

    THE BUG: Hindi tail after processDirect, CLD3   -> hi     (was mr)
    Hindi tail after processDirect, CLD2            -> hi
    Marathi still Marathi after widening, CLD3      -> mr
    Marathi still Marathi after widening, CLD2      -> mr

and the direct-call cases above them are kept, so the raw detector behaviour without context
stays documented. Every other harness re-run clean: segmenter **identical over 163,296
cases**, normaliser identical over 1,114,112 code points, script family identical over 15
sets x 1,114,112 code points.

**CLD2 is still the faster switch** (1.1 ms vs 6.6 ms per 30 paragraphs) and is untouched by
all of this.

## THE MID-USE SILENCE: three exits that ended an utterance without waking the wait (2026-09-03)
Owner: *"beech-beech mein kabhi kabhar chalte chalte bilkul TTS ruk jata hai ...
APK ko full stop karne ke bad phir chalata hun tab completely chalta hai."* That
last clause is the whole diagnosis: **only killing the process fixes it**, which
means nothing is broken on disk and nothing retries -- a thread is parked.

**The thread that parks is not ours.** `onSynthesizeText` ends by blocking **the
screen reader's synthesis thread** on `syncLock` until the utterance listener sets
`isStopped` or `isFlushed`. A screen reader has exactly ONE of those threads, so
the moment it is parked with nobody left to wake it, the whole device goes silent
and stays silent -- which is exactly the symptom.

**The chain, end to end.** `setLanguage` fails on an engine -> `restoreEngine(pkg)`
-> that wrapper leaves state 2 while it re-initialises -> the next utterance's
`loadVoice*` finds no wrapper in state 2 -> `engineIndex = -1` -> `speakChunk`
hits `engineIndex < 0`, logs **"mTTSIndex out of range."**, calls
`startAndFinish(callback)` and returns -> `onSynthesizeText` walks straight into
`syncLock.wait()` with both flags false, **no `speak()` ever issued**, so no
`onDone`, no `onError`, no `onStop` is coming. `speakingPkg` is null too, so even
`onEngineProcessGone` cannot match. Parked for ever.

**This is OUR refactor's bug, not AutoTTS's, and that distinction is the reason
the fix is safe.** AutoTTS's two equivalent guards (`decompiled_java_noexc/.../
AutoTtsService.java` :2074 and :2176) are written INLINE in `onSynthesizeText`, so
their `return` leaves the METHOD and the wait below is never reached. Ours live in
a shared local `fun speakChunk(first: Boolean)`, so `return` only leaves
`speakChunk` and execution falls into the wait. Same source, different scope, and
the scope is what leaks.

The fix is one local helper defined above `speakChunk` --
`releaseWaitWithoutSpeaking(why)`: log, `startAndFinish(callback)`, then
`synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }`. It is called
at all three exits that end an utterance without speaking:

    "mTTSIndex out of range."          engineIndex < 0
    "mTTSIndex refers null tts."       the wrapper has no TextToSpeech
    "Language <x> is not supported."   the mix/multilingual NEXT-chunk branch

The third one is the same hole one level down and had a sibling that always got it
right -- the auto/Google next-chunk branch beside it already released the wait.

**No clock, no timeout, nothing to tune** -- this is the shape the owner demanded
on 2026-09-02 (*"koi second nahin, koi millisecond bhi nahin"*). The release
happens on the exact event that made speech impossible, in the same statement that
decides it, so it can never fire during healthy speech and can never be late.

## The two audio switches, traced through AOSP (owner request, 2026-09-02)
*"accessibility stream … aur ek audio attributes … dono properly research karke
complete karo … modern phone ke hisab se."* Traced end to end through AOSP rather
than reasoned about, and it found a switch that had never worked.

**How audio attributes actually reach the speaking engine.** Four hops, each read
from source:
1. The screen reader calls `TextToSpeech.setAudioAttributes()`, which puts an
   `AudioAttributes` parcelable into ITS params as `KEY_PARAM_AUDIO_ATTRIBUTES`
   (`TextToSpeech.java:1522`).
2. That bundle arrives here as `request.params`, and `onSynthesizeText` forwards a
   copy of it to the downstream engine's `speak()`.
3. `TextToSpeech.getParams()` merges it with the downstream client's own params:
   `Bundle bundle = new Bundle(mParams); bundle.putAll(params);`
4. The downstream `TextToSpeechService.AudioOutputParams.createFromParamsBundle`
   reads `KEY_PARAM_AUDIO_ATTRIBUTES`, or -- only if it is absent -- builds
   attributes from `KEY_PARAM_STREAM` defaulting to `Engine.DEFAULT_STREAM`
   (`STREAM_MUSIC`) with `CONTENT_TYPE_SPEECH`.

**THE BUG, at step 3.** `mParams` is the BASE and the forwarded bundle
**overwrites** it. `setAudioAttributes` writes into `mParams`. So
**"Force to use audio accessibility stream" was defeated by the caller's own
attributes** -- and a screen reader sets them, so that is the normal case. The
switch set `USAGE_ASSISTANCE_ACCESSIBILITY`, TalkBack's attributes landed on top,
and the downstream engine never saw ours. It was silently a no-op for anyone whose
client supplies attributes, which is why turning it on appeared to change nothing.

**The fix** is one condition: forcing the accessibility stream now also clears
`audioAttributes` / `streamType` out of the forwarded bundle, which is exactly
what the strip switch already did. The two share the line for opposite reasons --
**strip** so the engine falls back to `STREAM_MUSIC` + `CONTENT_TYPE_SPEECH`,
**force** so our `USAGE_ASSISTANCE_ACCESSIBILITY` survives the merge.

**Verified, so do not re-check:** the magic numbers are right --
`AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY = 11` (line 186) and
`CONTENT_TYPE_SPEECH = 1` (line 92) in AOSP. They are numbers only because the
API-15 check jar has no `AudioAttributes` class at all, so the named constants
cannot be resolved locally; the values are correct and `minSdk` is 24.

**Considered and NOT added:** `setSpatializationBehavior(SPATIALIZATION_BEHAVIOR_NEVER)`
(API 32). Spatialization defaults to `AUTO`, and the spatializer will not process
a 16 kHz mono speech stream anyway, so it would be a speculative attribute on a
path that cannot be tested here. Do not add it without a device showing a problem.

### The switch was re-read on 2026-09-03 and it was still broken in TWO more places
Owner: *"jo uses accessibility services use karta hai na TTS mein ... uses 11 ...
uske bare mein bhi ek bar padh kar sahi karo ... ho sakta hai hamare mein kuchh
galat ho."* They were right twice. AOSP was read again rather than recalled --
`TextToSpeech.java` 1522 (`setAudioAttributes`) and 2024 (`getParams`), and
`TextToSpeechService.java` 738 (`AudioOutputParams.createFromParamsBundle`). The
merge fix above is confirmed correct; both new defects are the same root cause,
which the merge write-up never states: **`mParams` is STICKY, and
`wrapper.audioAttrSet` describes a `TextToSpeech` OBJECT, not a setting.**

**1. A restore left the flag lying.** `RestoreInitListener` replaces `tts` with a
brand new `TextToSpeech`, and attributes live in that object's `mParams`, so the
old client's are gone -- but AutoTTS never clears `k0.h` (noexc:2130 only ever
sets it true, like :2052 and :2438), and neither did we. If the force switch was
OFF at the moment of a restore, turning it back ON afterwards did **nothing for
the life of the process**: the speak path's `!wrapper.audioAttrSet` guard skipped
the install, the forwarded bundle had its own attributes removed by the very same
switch, and the engine fell through to `STREAM_MUSIC`. `wrapper.audioAttrSet =
false` now runs before the status check in the restore listener, so it always
describes the client actually in `tts`. (`initAllEngines` is clean -- it
`clear()`s the pool and builds fresh wrappers, so that path always started false.)

**2. Turning the switch OFF could not undo it.** There is no way to take
attributes back out of a client: `setAudioAttributes(null)` answers `ERROR` and
leaves `mParams` untouched, and `mParams` lives as long as the `TextToSpeech`
object. So an engine ever spoken to with force ON keeps
`USAGE_ASSISTANCE_ACCESSIBILITY` in `mParams`, and `getParams()` hands it
downstream whenever the merged bundle carries none of its own -- either because
the **strip** switch just removed the caller's, or because the caller never set
any. The user switches it off and still hears accessibility routing.

Overwriting the key is the only way to beat `mParams` in that merge, so the speak
path now puts back **exactly what AOSP would have built had the key been absent**:
`setLegacyStreamType(KEY_PARAM_STREAM, default Engine.DEFAULT_STREAM =
STREAM_MUSIC = 3).setContentType(CONTENT_TYPE_SPEECH = 1)`, which is
`createFromParamsBundle`'s own fallback, transcribed from lines 746-755. Guarded
by `!isForceAccessibility && wrapper.audioAttrSet && !params.containsKey(...)`,
so it is inert unless that wrapper really is carrying our attributes and nothing
else is going to overwrite them anyway.

Both are **DELIBERATE DEPARTURES** on the same footing as the merge fix: AutoTTS
carries both defects. Verified in the same read and NOT changed: `requestAudioFocus`
is `l0()` byte for byte (`AUDIOFOCUS_GAIN` = 1, usage 11, content type 1, one
request in `onCreate`, abandoned in `onDestroy`), and `k0.f`/`k0.g` (`localeSet` /
`listenerSet`) are left un-cleared on restore in both apps -- they gate logging and
re-entry, not audio routing.

## The last two clocks are gone -- what the research actually said (2026-09-02)
The owner rejected every timing constant: *"koi timing wala system mat rakho yaar,
yah to ek latency ka karan ban jata hai."* Both remaining ones were researched
against AOSP; **one had to go and one had to stay**, and the difference matters.

### The 3-second restore rate limit: REMOVED
`k0.h()`'s `elapsed > 3000ms` is a rate limit on **recovery**, and it was the one
place in the file where a delay was genuinely felt: an engine that dies within
three seconds of being restored is refused, and **nothing schedules a retry** --
it simply stays dead until some later failure happens to trigger another attempt.

What it guarded is already guarded without a clock. `restoringIndex != -1` rejects
a second restore while one is in flight, and that flag is cleared by
`RestoreInitListener`, which **always** runs: every failure path in AOSP's
`TextToSpeech.initTts` ends in `dispatchOnInit(ERROR)` (the requested engine is not
installed, the bind fails, no engine can be reached), so the listener cannot be
skipped. A restore therefore costs a bind plus an init before another can begin,
and the loop can never be tight. The `k < 10` cap stays as the runaway guard, and
`onStart` zeroes the streak -- an event, not an interval.

### The keep-alive `wait(100)`: KEPT, and here is why it must be
It looks like the same kind of constant and it is not. `k0`/`t0` feeds 32 bytes of
silence per round, and the AOSP call it feeds **already blocks by itself**:

    // Might block on mItem.this, if there are too many buffers waiting
    // to be consumed.
    item.put(bufferCopy);            // PlaybackSynthesisCallback.audioAvailable

and `SynthesisPlaybackQueueItem.put` waits on `mNotFull` whenever more than
**`MAX_UNCONSUMED_AUDIO_MS = 500`** of audio is unconsumed, waking on stop as well.
So the backpressure is real -- but it only bites once 500 ms is buffered. **32
bytes at 16 kHz 16-bit mono is 1 ms of audio.** Removing the `wait(100)` would not
make the loop event-paced; it would make it spin about **a thousand times a second**
to keep half a second of silence queued, burning CPU and battery for nothing. With
the wait it pokes ten times a second and never fills the queue at all.

**And it adds no latency**, which is the actual question: the loop is inside
`synchronized(syncLock)`, and every stop path does
`synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }`, so it is woken
immediately rather than after the remainder of the 100 ms. It also runs **only when
keep-alive is switched on**, which is off by default, and never on the speaking
path. Leaving AutoTTS's own pacing alone here is the correct answer; replacing it
would be the change that costs performance.

## Accessibility swept again against the CURRENT APIs (owner request, 2026-09-02)
*"accessibility attributes … usko bhi sahi karna hai sab jagah se properly … jo
latest devices aur latest technology hai uske hisab se research karke."* Done by
reading the current sources, not from memory, and it found two real gaps rather
than a list of things to admire.

**What was actually checked, and where the list came from.** Android 15 and 16's
behaviour-changes pages carry exactly one accessibility item between them -- the
`announceForAccessibility` deprecation, which this app closed on 2026-08-13 -- so
the platform side is current. For Compose the authority is androidx's own
`SemanticsProperties.kt`, fetched rather than recalled: **101 semantics keys and
83 `SemanticsPropertyReceiver` setters**. Every one was compared against what the
app uses (`contentDescription`, `stateDescription`, `heading`, `paneTitle`,
`liveRegion`, `collectionInfo`, `collectionItemInfo`, `selectableGroup`, `role`,
`selected`, `toggleableState`, `clearAndSetSemantics`, `onClickLabel`,
`hideFromAccessibility`). The rest are either set for us by the Material
component (`disabled`, `dialog`, `popup`, `isEditable`, `progressBarRangeInfo`),
belong to text input we do not have (`imeAction`, `insertTextAtCursor`,
`textSubstitution`, `maxTextLength`), or are `isTraversalGroup` /
`traversalIndex`, which are deliberately unused -- see below.

**Gap 1: `MainScreen` had no accessibility test.** Six of the seven screens were
covered by `AccessibilityChecksTest`; the one that was not is the app's front
door -- the startup scan, the app bar, the tab strip and the selected page. It is
covered now in **both** states, which share no views at all: `mainScreenScanning`
and `mainScreenSettled`. `appIcon` is passed as null on purpose -- the real one
comes from `PackageManager` and its `Image` carries
`contentDescription = null` because the name is written beside it, so null
exercises the same branch without needing a bitmap. That job runs Google's
Accessibility Test Framework on a real emulator and **fails the build**, so this
is the part that keeps working without anyone re-reading the screen.

**Gap 2: the last `RedundantDescriptionCheck` warning is closed.** The Languages
filter chip said **"Show selected"** while a `FilterChip` also publishes
`selected`, so TalkBack announced the state twice. It had been carried as an
accepted warning because WCAG 2.5.3 Label in Name forbids fixing it by editing
the accessible name alone -- the visible text has to change with it. Both changed
together to **"My languages"**, which also says what the filter leaves on screen
where "Show selected" never did. **The `grep -v "Show selected"` carve-out is out
of `tools/check/invariants.sh`**, negative-tested both ways, so the rule now has
no exception and a future regression cannot slip through it.

**Verified clean in the same sweep, so do NOT re-audit:** every
`clickable`/`toggleable`/`selectable`/`IconButton` in the app has an accessible
name within its own block; no two accessible names in one file collide
(`DuplicateSpeakableTextCheck`); no name contains a role word or a state word any
more; disabled controls really are disabled rather than only greyed --
`SettingSwitch` passes `enabled` to `toggleable` and `LabeledDropdown` passes it
to the `OutlinedButton`, which is what puts the `Disabled` semantics on the node.

**Deliberately NOT done, and it needs the owner's decision.** The tab strip is in
`Scaffold`'s `bottomBar`, so it is the LAST thing in reading order and a user
must swipe past a whole page -- up to 137 language rows -- to reach it.
`isTraversalGroup` + `traversalIndex` could move it to the front. That changes how
the owner traverses the app every day, and both `LazyColumn`s publish
`collectionInfo`, so container navigation already offers a way out of a long list.
Ask before changing it.

## Three owner overrides on 2026-09-02, all DELIBERATE DEPARTURES from AutoTTS
Asked for directly. Do not "restore" any of them to AutoTTS's behaviour.

### 1. "Disable advanced language detection" now arrives OFF
Owner: *"already by default off hona chahie, hamare mein on rahata hai."*
AutoTTS ships it **ON** -- `c3/n.java:636` is
`getBoolean("disable_advanced_detection", true)` -- so this is an override, not a
parity bug being corrected.

**What the flag gates, read from the native window detector rather than the name.**
With it ON, `detectLanguageFull` takes the detector's first reliable answer and
stops: no check that the language is one the user enabled, and no script-family
fallback. With it OFF, the answer must be in the enabled set, and if it is not,
`a.e(cp, n.f)` resolves the window by its script instead. **ON is the cruder
path** and can route a span to a language the user never ticked.

**Changing the default alone does nothing on an existing install**, and that is
the part worth remembering: `persistAll` writes this key on every `onPause`, so
any device that has opened the app already has `true` stored and never consults a
default again. Hence `LangStore.applyAdvancedDetectionDefault`, a one-time
migration behind its own marker key
(`disable_advanced_detection_default_off_applied`). It flips the stored value
once and never touches it again, so the owner can still switch it back on and it
stays on. It is called from **both** load paths -- `LangStore.loadFlags` and the
service's `loadAllSettings`, which reads through `SharedPrefsManager` and does not
go through `loadFlags` -- and the marker makes the second call a no-op.

### 2. Engine death unblocks the wait -- BY EVENT, never by a clock
Owner: *"kabhi kabhar beech mein vah band ho jata hai … vah stop na ho jaye."*

`onSynthesizeText` ends by parking **the screen reader's synthesis thread** on
`syncLock` until the utterance listener sets `isStopped` or `isFlushed`.
`AutoTtsService:2165` is a bare `while (!p.get() && !q.get()) o.wait();` and ours
was the same. If a target engine accepts a `speak()` and then never calls back,
that thread parks forever -- the screen reader has one synthesis thread, so from
that moment the whole device stops speaking and only killing the engine brings it
back.

**THERE IS NO TIMEOUT, AND THERE MUST NEVER BE ONE.** A first attempt used a
10-second quiet watchdog and the owner rejected it outright, correctly:
*"koi timeout kuchh nahin rakhna hai … koi second nahin, koi millisecond bhi
nahin … yah to jugaad kar diya."* A clock cannot tell "this engine is dead" from
"this paragraph is long", so any number is a guess that either cuts off real
speech or leaves the hang in place. **If a future session is tempted to add a
timeout here, that is the wrong shape; read the next paragraph instead.**

**Why an event is sufficient -- read from AOSP, not assumed:**
- `TextToSpeechService` dispatches `onDone`, `onError` or `onStop` for **every**
  speech item it processes, and `dispatchOnError(ERROR_SERVICE)` even for items it
  refuses. A live engine always calls back, so silence is never "still working".
- The one path with no callback is the engine's **process going away**.
  `TextToSpeech.Connection.onServiceDisconnected` merely sets `mService = null`
  and tells the app nothing -- its `dispatchOnInit(ERROR)` fires only when a
  connect was still in flight -- which is exactly why this hung.
- But **we hold our own binding to every engine already**: `bindEngineKeepAlive`
  is called for each one as the pool is built, not only in keep-alive mode. Android
  calls `onServiceDisconnected` / `onBindingDied` on it the moment that process
  dies. The signal was already arriving; nothing was listening.

So `speakingPkg` names the engine currently holding the utterance, set at the
`speak()` site, and `onEngineProcessGone(pkg)` -- called from both connection
callbacks -- ends the wait only when the dead engine is that one. It costs a field
write and a string compare, adds **no latency of any kind**, and fires sooner than
any timeout could have.

### 3. The restore streak is ended by an EVENT too
`k0.h()` is `k == 0 || (elapsed > 3000ms && k < 10)` and `k0.i()` only ever
increments `k`; **nothing in AutoTTS resets it.** After ten restores an engine is
unrecoverable for the life of the process -- and this service is `START_STICKY`
and can run for days. It dies an eleventh time and is never brought back.

The first attempt reset the counter after a minute of health. That is the same
jugaad in a smaller costume, so it is gone. **`onStart` zeroes
`wrapper.restoreCount`** instead: the engine actually spoke, which is what ends a
failure streak. How long ago it last failed says nothing on its own. AutoTTS's cap
and its 3-second rate limit are untouched.

**The only two time values left in the service are AutoTTS's own** -- that 3000 ms
rate limit, and the `wait(100)` inside the keep-alive loop (`k0`/`t0`), which runs
only when keep-alive is switched on. There is no constant of ours anywhere on the
speaking path.

**None of the owner's logs contains any of these three failures** -- zero
`restoreTts`, zero `Engine process died`, zero `onDestroy` across all seven logs
sent so far. They were found by reading the wait and the restore guard, not from a
log, which is why the watchdog is written to be impossible to trigger during
healthy speech rather than tuned against a trace.

## The SECOND CLD3 report: it was never the model, it was the squeeze (2026-09-02)
The owner sent a fresh log and said the problem was still there — *"kuchh chijon ke
liye to fix hua hai but kis chij ke liye abhi bhi fix nahin hua hai … Usi direction
ko"*. Correct on both counts. The log toggles the CLD3 switch mid-session (ids 924
Unchecked → 926 Checked), so the same utterance is routed twice, and the engine
column hides it because Hindi, Gujarati and Marathi all sit on Google TTS. Read the
`loadLanguage` line, not the engine:

    किसी दूसरी भाषा में हो रही किसी दूसरी भाषा बातचीत Text box Compose message
      ids 910-916  (CLD2)  chunk 1 -> hin      chunk 2 -> eng
      ids 951-959  (CLD3)  chunk 1 -> mar      chunk 2 -> eng

**The widening from the previous fix cannot touch this one.** That Devanagari chunk
IS all the Devanagari in the utterance — 130 bytes, under CLD3's own 140 — so
`sameScriptContextText` returns the same bytes and the widening is a no-op. Right
direction, different cause.

**And the model is not wrong.** Feeding the raw bytes straight to the network:

    hi p=0.992561      ne p=0.006406      mr p=0.000844

What the network is handed is not those bytes. `FindLanguage` lowers the text with
CLD2's ScriptScanner and then runs `CLD2::CheapSqueezeInplace` on it:

    cleaned  (131 B)  किसी दूसरी भाषा में हो रही किसी दूसरी भाषा बातचीत
    squeezed  (75 B)  किसी दूसरी भाषा भाषा बातचीत          ->  mr p=0.913

`"किसी दूसरी भाषा"` occurs twice, the squeezer drops the 48-byte chunk it can
predict, and a third of the sentence is deleted before detection. **CLD2 never does
this to a short text** — `compact_lang_det_impl.cc:1867` squeezes a span only when
`2048 < scriptspan.text_bytes`, and even then only if `CheapSqueezeTriggerTest`
agrees. CLD3 calls it unconditionally, in **both** entry points. So the switch did
not only change detector, it silently changed the text. That is the whole defect,
and it is inside CLD3 exactly as the owner insisted.

**The fix** is `cld3FindLanguageGated` / `cld3TopNGated` in `tts_engine_core.cpp`:
CLD3's own two functions mirrored statement for statement, with the squeeze gated
by CLD2's own 2048-byte threshold. Reaching the pipeline without the squeeze needs
two private members, so the include is wrapped in `#define private public` — CLD3
is a CI `git clone`, so patching it is not an option. **Namespace trap:** CLD3
carries its own copy of CLD2's span code, so it is `chrome_lang_id::CLD2::…`, not
the top-level `CLD2::…`, and `CheapSqueezeInplace` needs
`#include "script_span/text_processing.h"` on top of what the CLD3 header pulls in.

**Measured over 608 distinct strings** (every phrase spoken in both device logs plus
the app's own literals): the stock squeeze removes text from **9** and changes the
answer on **4** — `mr 0.913 → hi 0.989`, `mr 0.526 → hi 0.814`,
`"Text box Compose message"` doubled `ja 0.784 → en unreliable`, and a Samoan
sentence `hu 0.861 → sm 1.000`. Nothing gets worse. The Japanese one is the same
bug on Latin text and was found while measuring the fix.

**Speed, measured rather than assumed.** `tools/verify/latency` repeats three fixed
paragraphs, so at 60 paragraphs each appears twenty times — the pathological case
the squeeze exists for, and nothing like real text (9 of 608). There:

    gate only, no widening        6.6 ms      (stock was 8.6 ms)
    gate + the widening fix      47.3 ms

so the cost is the **widening**, and it grew only because the squeeze used to
collapse the widened evidence into nothing. `onSynthesizeText` can never receive
more than 4000 characters, so the real ceiling is the 20-paragraph row at
**8.7 ms**; a typical screen-reader utterance is under a millisecond. CLD2 is
untouched and still the faster switch. (The write-up that lived in
`docs/INVARIANTS.md` #25 went with CLD3; this section is what is left of it.)

**Do not raise `min_num_bytes`, do not route to CLD2, do not touch the enabled
list** — all three were measured and rejected earlier and none of them was ever the
cause.

## THE THIRD CLD3 REPORT: this one is the MODEL, and there is no fix to write (2026-09-02)
Owner, with Marathi enabled this time so the case is genuinely exercised:
*"cld3 ab natak karne laga hai kahin kahin per … bina title wali batchit aur nai
chat wala jo button hai."* Two Gemini labels, spoken by the **Marathi** voice under
CLD3 and by the **Hindi** voice under CLD2:

    "नई चैट"                   17 bytes    ids 4590, 4592, 4594, 4618, 4620
    "बिना टाइटल वाली बातचीत"    61 bytes    ids 4603, 4605, 4607

**Unlike the previous two reports there is NO defect on our side.** All three
suspects were checked and cleared, and that is the point of this section -- do not
re-investigate them:
- **the hint list is right.** Marathi really is enabled (`getEngine4Language mar`
  lists eng/guj/hin/mar), so `isHinted` accepts `mr` and the per-script fallback
  correctly does not fire. Nothing to fix.
- **the squeeze gate is irrelevant.** 17 bytes is a single 48-byte chunk;
  `CheapSqueeze` removes nothing.
- **the widening has nothing to widen to.** Neither utterance carries any other
  Devanagari, so `sameScriptContextText` returns the same bytes. Verified through
  the real `processDirect` sequence in the harness, not argued.

**It is the model, and its own softmax says so:**

    "नई चैट"                  mr 0.9640   vi 0.0343   hi 0.0015
    "बिना टाइटल वाली बातचीत"    mr 0.9992   hi 0.0006   ne 0.0001

Hindi is 600 to 1600 times less likely. There is no close second to prefer, no
threshold that separates these from the correct answers, and nothing to re-rank.

**AND IT IS NOT A BIAS TOWARDS MARATHI -- that framing is wrong and it matters.**
Measured over 16 real UI labels, nine Hindi from the owner's own log and seven
Marathi of the same kinds, at the current `min_num_bytes = 0`: **four are wrong,
two in each direction**, and one Marathi label (`मायक्रोफोन`) is answered **Nepali**.
CLD3 is simply unusable on Devanagari below its own `kMinNumBytesToConsider`.

**Raising that minimum was measured again, with the widening now in place, and it
still fails.** It only trades the errors over:

| `min_num_bytes` | 0 | 40 | 60 | 80 | 140 |
|---|---|---|---|---|---|
| Hindi labels wrong | 2 | 1 | 1 | **0** | **0** |
| Marathi labels wrong | 2 | 3 | 5 | 5 | 6 |

The total never improves, because below the threshold every Devanagari span falls
to the per-script fallback, and `kScriptLangPairs` lists `{4, HINDI}` first -- so
"raise the minimum" means "read all short Devanagari as Hindi", which is perfect
for this owner and broken for a Marathi one. **Do not raise it.**

**Everything else was already measured and rejected in the earlier rounds** -- a
higher reliability bar, a shared-script override, table-order re-ranking, and
routing to CLD2 under the CLD3 switch (which the owner refused outright). Each one
makes some other user worse. **There is no code change that fixes this**, and
writing one anyway would be the jugaad the owner has twice told us not to write.

**What is true and worth telling the owner plainly:** CLD2 reads short Devanagari
correctly, CLD3 does not, and choosing between them is the switch they already
have. CLD2 is also five times faster. CLD3 earns its place on longer text, which is
where a neural detector beats n-grams.

The whole measurement is now permanent in `tools/verify/cld3span/run.sh` -- the two
failing labels in both detectors, the same pair through the real `processDirect`
sequence, **and three neighbours from the same log that both detectors get right**
(`साइडबार बंद करें`, `चैट खोजें`, `मोबाइल पर कैनवा का उपयोग`). Those three are the guard: they
are what stops a future "fix" from routing every short Devanagari span to Hindi and
declaring the problem solved.

## The two shipped CLD3 fixes were RE-TESTED under suspicion, and three more ideas died (2026-09-02)
The owner suspected the fix itself: *"jo fix kiya hai vah valid nahin hai … shayad
aapne galat fix kar diya hai … jahan se fix kiya hai vahan se aap hata dena."* That
is a fair challenge and it was answered by measurement, not by argument. **A 25-item
corpus** — the owner's own Hindi labels from the device logs plus Marathi labels of
the same kinds — was run at the REAL span site with the core in three states:

| core state | CLD3 wrong |
|---|---|
| both fixes removed | **6** of 24 |
| widening + squeeze gate (shipped) | **5** of 24 |

**So the shipped fixes are not wrong.** They fix one case (the 130-byte repeated
sentence, which the squeeze was mutilating) and break nothing. They stay. What they
are is **insufficient**, which is a different thing and is what the owner is feeling.

**Three further ideas were then implemented and measured, and ALL THREE MADE IT
WORSE. Do not try them again; the numbers are here so nobody has to.**

**1. Decline below CLD3's own minimum and let the user's preference decide.**
The most promising idea by far, because it needs no invented number (CLD3's own
`kMinNumBytesToConsider = 140`) and no invented fallback: `IsoCodes.toIso3("un")` is
null, so `languageForDetectedRun` already falls to `run.latin ? P : Q`, the user's
own **"Preferred language for Latin / non-Latin text"**. A Hindi reader has set that
to Hindi and a Marathi reader to Marathi, so each would be answered in their own
language exactly where the model cannot tell them apart. Measured what each reader
actually HEARS, sweeping the threshold:

| decline below | Hindi reader | Marathi reader | both |
|---|---|---|---|
| 0 (today) | **76%** | **76%** | **76%** |
| 20 | 80% | 72% | 76% |
| 40 | 72% | 64% | 68% |
| 80 | 64% | 48% | 56% |
| 140 | 64% | 44% | 54% |

**Today's behaviour is the best row.** The user's stated preference is a weaker prior
than even a coin-toss detector, because it is right only for the language they read
most and wrong for every other one they enabled. Reverted.

**2. Pad short text by repeating it until it reaches CLD3's minimum.** It looked
right — one earlier probe showed a 56-byte tail answered correctly when doubled, and
with the squeeze gate in place the repetition now survives to the network. Measured
over the same corpus:

| pad to | Hindi | Marathi | combined |
|---|---|---|---|
| no padding | 67% | 80% | **72%** |
| 140 bytes | 53% | 60% | 56% |
| 300 bytes | 53% | 60% | 56% |
| 700 bytes | 47% | 60% | 52% |

That one doubling was luck, not an effect. Reverted.

**3. Constrain the answer to the enabled languages OF THAT SCRIPT**, using the full
109-language softmax we already reach through `#define private public`. Not even
implemented, because the distribution settles it: for `नई चैट` the enabled Devanagari
languages are {hi, mr} and the model gives **mr 0.9640, hi 0.0015**; for the real
Marathi `नवीन चॅट` it answers **hi**. The ordering itself is wrong, so constraining
the choice cannot help.

**WHY NONE OF THIS CAN WORK, in one measurement.** Twelve real Hindi and Marathi
sentences were cut to rising byte prefixes and put through `FindLanguage`:

| bytes | Hindi right | Marathi right | combined |
|---|---|---|---|
| ≤20 | 33% | 67% | **50%** |
| ≤40 | 50% | 50% | **50%** |
| ≤60 | 50% | 83% | **67%** |
| ≤80 | 83% | 100% | **92%** |
| ≥100 | 100% | 100% | **100%** |

**Below about 80 bytes CLD3 is a coin toss between the two, in both directions.**
Above 100 it is perfect. There is no threshold, no re-rank, no fallback and no
padding that turns a coin toss into an answer — every one of those only decides
*which* way to be wrong. **CLD2 scores 24 of 25 on the same corpus at every length**,
because n-gram tables degrade gracefully on short text and a neural net does not.

**So the honest position, and it should be stated plainly rather than worked around:
CLD3 cannot read short Devanagari, and no code in this app can make it.** What the
app can do is what it already does — let the owner choose the detector. For UI labels
and button names, which is what a screen reader mostly speaks, **CLD2 is the correct
switch, and it is also five times faster**. CLD3 earns its place on long text, where
it is 100% and where a neural detector genuinely beats n-grams.

## The verify harnesses could pass on a STALE binary (found and fixed 2026-09-02)
`tools/verify/cld3span/run.sh` and `tools/verify/latency/run.sh` compile through a
`compile()` helper that ends in `echo "$obj"`, so the function returned **0 however
g++ fared**, and the link then picked up the object file from the last good build.
A broken core printed a wall of compile errors and the harness still reported every
case passing. It cost a full debugging cycle on the day it was found: the CLD3 fix
looked like it had no effect. Both scripts now `rm -f` the object first and `exit 1`
on a compile failure, negative-tested by appending a syntax error to the core and
confirming the run goes red. **Any new harness must do the same.**

## The launcher icon is the owner's artwork (2026-09-02)
Supplied as a square JPG with a white margin around a rounded-square badge.
`tools/icon/make_icons.py` regenerates the whole set from `tools/icon/source.jpg`; three
decisions are baked into it rather than done by hand:

1. **The white margin is cropped** -- it is part of the picture, not the icon.
2. **The badge is INSET to 68%, not full bleed.** An adaptive icon is 108dp and the smallest
   real launcher mask shows only the centre 72dp. This artwork's side bubbles ("Hello",
   "नमस्ते") and their sound-wave arcs sit close to the badge edge, so full bleed would cut
   them off on any circular launcher.
3. **The background is a bilinear gradient sampled from inside the badge's own rounded
   corners**, so the inset badge does not float on a colour that is not in the art.

Layout afterwards: `drawable-nodpi/ic_launcher_art_{bg,fg}.png` feed
`mipmap-anydpi-v26/ic_launcher{,_round}.xml`; `mipmap-{m,h,xh,xxh,xxx}dpi/ic_launcher{,_round}.png`
serve API 24-25, which cannot parse `<adaptive-icon>`, and are FULL BLEED because legacy icons
are not masked. The old `drawable/ic_launcher_{background,foreground}.xml` and the two
`mipmap-anydpi/` vectors are gone. **`drawable/ic_launcher_monochrome.xml` is KEPT** -- it is
the themed-icon silhouette (Android 13+), a speech bubble, which still suits the app; a
bitmap monochrome would be worse.

**`xmlcheck.py` had to be widened for this, and it was a real gap.** It read only
`res/drawable/`, so `@drawable/ic_launcher_art_bg` in `res/drawable-nodpi/` was reported
MISSING when it was there. A drawable resolves across every `drawable*/` and `mipmap*/`
folder and any extension. Negative-tested both ways: it still reports a genuinely absent
drawable, and passes the real one.

## The app-opening animation is the launcher icon, and it holds nothing up (2026-09-02)
Asked for with the artwork: *"include the proper app opening animation using this
icon"*. `androidx.core:core-splashscreen` + one theme, `Theme.EasyVoice.Splash` in
`values/styles.xml`, named by **MainActivity only** — the sub-screens keep
`AppTheme.NoActionBar`, so there is no splash when navigating inside the app.

Three decisions worth keeping:
1. **The icon is `@mipmap/ic_launcher`, the ADAPTIVE icon, not a new asset.** The
   platform gives an adaptive splash icon a 240dp canvas and shows the inner 160dp
   after masking — the same two-thirds rule the launcher mask uses, which is what
   `tools/icon/make_icons.py`'s 68% inset was computed for. 68% of 240dp is 163dp,
   so the badge lands on the visible circle instead of being cropped. On API 24-25,
   which cannot parse `mipmap-anydpi-v26`, the same reference resolves to the
   full-bleed legacy PNG, which is right there too. **Do not add a splash-specific
   drawable** — the splash IS the launcher icon, and one asset cannot drift from
   the other.
2. **`windowSplashScreenBackground` is `@color/ev_background`**, the same `#121212`
   as `windowBackground` and the Compose surface, so the handover is a change of
   content rather than a flash of colour.
3. **Nothing holds it open.** There is no `setKeepOnScreenCondition` and no
   `setOnExitAnimationListener`. The owner is blind: parking the startup scan
   behind a picture would delay TalkBack for seconds and show them nothing. The
   splash lasts exactly as long as the first frame takes to draw.
   `installSplashScreen()` runs **before** `super.onCreate`, which is where the
   window theme is read.

From API 31 the platform shows a splash for every app whether we ask or not, built
from the launcher icon and `windowBackground`; the library is what gives API 24-30
the same thing and lets one theme describe it everywhere.

**The version could not be verified here.** `dl.google.com` answers 403 through the
egress proxy, so `core-splashscreen`'s version list is unreadable from this
container and `kotlin-typecheck.sh` reports `unresolved reference
'installSplashScreen'` as ordinary androidx noise. `1.0.1` is the long-standing
stable; CI is what checks it resolves. See the section below for how the owner can
unblock that host.

## "Google Maven is blocked" is ONE HOST, and the owner can unblock it (diagnosed 2026-09-01)
Repeated everywhere in this file as a flat fact. It is narrower than that, and it is fixable
from the environment settings — measured, not assumed:

| host | result |
|---|---|
| `maven.google.com` | reachable, **HTTP 301** — it is only a redirector |
| `dl.google.com` | **CONNECT tunnel failed, 403** — where the artifacts actually live |
| `repo.maven.apache.org`, `repo1.maven.org` | reachable |
| `plugins.gradle.org`, `services.gradle.org`, `gradle.org` | reachable |

So **one host** is the whole blockage. The cloud environment's *Trusted* network level has a
"JVM package managers" group — `maven.org`, `repo1.maven.org`, `repo.maven.apache.org`,
`gradle.org`, `services.gradle.org`, `plugins.gradle.org` — and **no Google Maven host at
all**. That is why `android.jar` comes from Maven Central and every androidx symbol is
unresolvable.

**The fix, which is the owner's to make** (`code.claude.com/docs/en/cloud-environments`):
on `claude.ai/code`, the cloud icon above the message box opens the environment selector —
there is no settings URL — then the settings icon on the environment, **Network access →
Custom**, and in **Allowed domains**:

    dl.google.com
    maven.google.com

with **"Also include default list of common package managers" TICKED**, or Maven Central,
GitHub and the rest are lost. It takes effect on a **new** session; the running one keeps the
policy it started with.

**Do not route around it.** `/root/.ccr/README.md` is explicit: a 403 from the proxy is an
egress-policy denial, *"Do not retry or route around it — report the blocked host."* A
third-party mirror of Google Maven would defeat the owner's own setting; ask instead.

**What it would buy.** `kotlin-typecheck.sh` would resolve androidx, Compose and Material3,
so the ~1,240-error baseline mostly disappears and a real Compose mistake is caught locally
instead of in CI — which is the single biggest hole in the local checks. It would also let a
coordinate like `androidx.compose.material3.adaptive:adaptive:1.3.0` be verified before it is
pushed, rather than trusting the release notes.

## Adaptive layout brought to the current standard (user request, 2026-09-01)
*"Puri application sabhi screen per chalani hai … use screen ke according adjust ho jana
chahie … bilkul naya standard."* Read for this, and the reading is the point:
`develop/ui/compose/layouts/adaptive/use-window-size-classes`,
`.../support-different-display-sizes`, `.../canonical-layouts`, the five Compose
accessibility pages, **and androidx's own API files** — which is where two of the three
findings came from, because the doc pages are behind the libraries.

**The library was missing.** `androidx.compose.material3.adaptive:adaptive:1.3.0` (latest
stable, 12 Aug 2026) is not in the Compose BOM and carries its own version. Only the
`adaptive` artifact is declared; `adaptive-layout` and `adaptive-navigation` are the pane
scaffolds and we have no two-pane layout.

**Three things were behind, all now current — details and the traps in INVARIANTS #23/#24:**
1. **`currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)` is DEPRECATED.** The doc
   page still shows it; `api/current.txt` says V2 replaced it in 1.3.0-alpha10. We use
   `currentWindowAdaptiveInfoV2()`.
2. **`TabRow` is deprecated in material3 1.4.0** — the version BOM `2026.08.00` pins, checked
   against `api/1.4.0-beta01.txt`, not androidx-main. The app's tab strip is `PrimaryTabRow`
   now. Every other component we call is current in 1.4.0; the other "deprecated" overloads
   in that file are binary-compat shims hidden from Kotlin source. **Do not migrate those.**
3. **Two hard-coded dp numbers did not adapt.** The 840dp content cap is now
   `WIDTH_DP_EXPANDED_LOWER_BOUND` and applies only at/above that breakpoint, so a compact or
   medium window fills. The Languages header's 280dp cap is **most of a landscape phone** —
   compact height is under 480dp — so on a short window it is 140dp.

**The top app bar is hidden when the window is short**, which is Google's own worked example
for compact height (*"Decide whether to show the top app bar based on window size class"*).
It held only the icon and the app name, no actions, and a landscape phone lost a fifth of its
height to it. The screen is still announced: `paneTitle` is on the pager, not the bar.

**NOT done, and it needs the owner's decision: the canonical large-screen layouts.** A
navigation rail at medium+ instead of tabs, or a two-pane list-detail for
Configuration → Voice setup, is what the guidance recommends for an expanded window. Both
change how the screen reader traverses the app, which is the thing the owner uses every day,
so neither was done unilaterally.

## Full UI audit against Google's guidelines (user request, 2026-08-13)
*"pura user interface mein kahin per bhi Google ke khilaf kuch ho to usko sahi kar dijiyega"*.

**Contrast — computed, not eyeballed.** Every palette pair was run through the WCAG relative
luminance formula. All active pairs pass: body text on background **18.7:1**, on surface
16.5:1, secondary text 14.6:1, white on the section header 14.6:1, button label on fill
7.2:1, accent on background 10.3:1, bright outline 11.0:1, checked thumb on track 7.2:1,
unchecked thumb on track 8.1:1 — against floors of 4.5 (text) and 3.0 (UI components).
Four pairs land below their floor and **all four are exempt**: the disabled button label
(2.9), disabled text (3.9) and the disabled switch thumb (2.9) are inactive components, and
WCAG 1.4.3 states *"Text … that are part of an inactive user interface component … have no
contrast requirement"*; `surface` vs `background` (1.1) is the spinner popup's own fill,
which no criterion requires to be distinguishable — the text on it is 16.5:1 and the popup
carries elevation. The hardcoded green `#4CAF50` on "Installed" is 6.7:1, and the state is
carried by the words "Installed"/"Not installed", not by colour alone.

**Fixed in this pass:**
- **`android:supportsRtl="true"` was missing.** It defaults to false, so the layout never
  mirrored for Arabic, Urdu or Hebrew users — languages this very app reads. Now declared.
- **Two RTL-unsafe spots** in the required-engines dialog: `rightMargin`/`leftMargin` on the
  Apply/Cancel buttons became `marginEnd`/`marginStart`, and
  `notInstalledLabel.setPadding(0, 0, 16, 0)` became `setPaddingRelative`.
- **Raw pixels instead of dp** throughout that dialog (`48/32/16`). AutoTTS's `c3.u` uses raw
  px too (lines 99/104/141/157), so our port was faithful — but px is density-dependent and
  the units guidance is dp, so under the UI carve-out it is now `dialogDp(…)`. `dialogDp` is
  a **class member**, not a local of `buildContent()`, because `renderRows()` needs it as
  well — the kotlinc+android.jar check caught that as a genuine `unresolved reference`.
- **The app-bar icon duplicated the app name.** It carried
  `contentDescription = app_name` while the `TextView` beside it shows the same string, so
  TalkBack said "Easy Voice" twice. It is decorative, so it is now `contentDescription = null`
  plus `IMPORTANT_FOR_ACCESSIBILITY_NO`.

**Second, deeper pass (same day, user: *"aur bhi bahut sari aisi jagah hogi … completely"*):**
- **No section header was an accessibility heading.** Google: *"Indicate headings to allow
  users to navigate between them."* The app has 13 header instances — "Modes", "Mode
  Settings" (×4), "Voices" (×2), "Languages", "Licenses" and the eight Advanced sections —
  built by six header factories, and only the wizard's step heading was marked. Without this
  a TalkBack user has to swipe through every control instead of jumping section to section.
  All six factories now call `ViewCompat.setAccessibilityHeading(this, true)` on the header
  `TextView` (the leaf TalkBack focuses, not the coloured bar). The white-on-header
  `setTextColor(0xffffffff)` + `CENTER_VERTICAL` pair is what identifies a header uniquely —
  a blanket replace on `CENTER_VERTICAL` alone would have wrongly caught the locale-spans
  switch.
- **Tab position was announced twice.** Material's `TabLayout` already publishes collection
  info, which TalkBack renders as "Tab 1 of 2", and we appended `", tab N of M"` on top in
  two places. The suffix is gone; the tab title alone is the label.
- **The three sliders had no name.** `SeekBar` carried only `stateDescription`, so landing on
  one announced "100 of 500, seek control" with no clue whether it was Speed, Volume or
  Pitch. Each now has `contentDescription = labelString`. (The `-`/`+` buttons already said
  "Decrease speed" / "Increase speed".)

**Verified clean in that pass, so do not re-audit blindly:** every `Spinner` has an adjacent
label `TextView`; every `setOnClickListener` sits on a `Button`; no code sets `ellipsize`,
`singleLine` or `maxLines` (only `ev_spinner_item.xml` does, which is the conventional
closed-spinner behaviour and does not hide anything from TalkBack, since the node keeps the
full text); every activity has a title, either `android:label` or a runtime `title`; the
language-list rows are uniquely labelled by language name.

**Checked and deliberately left alone:**
- the `SeekBar`'s `ACCESSIBILITY_LIVE_REGION_POLITE`. The Android 16 page names
  `setAccessibilityLiveRegion` as *the* API for a critical UI change and only warns to use it
  sparingly; a slider's value is exactly that case, and `stateDescription` (API 30+) alone
  would leave API 24-29 without the announcement. Do not remove it.
- `"Requried TTS Engines"` — the typo is **AutoTTS's own string** (`c3.u:102`), not ours, and
  is not a guideline breach. Left per rule 5; change it only if the user asks.
- text sizes are all `setTextAppearance(android.R.style.TextAppearance_*)` or
  `setTextSize(float)`, both `sp`, so they follow the user's font-size setting; no view uses
  a fixed height that could clip when the font scale grows.

## Material components: icons and button emphasis (user request, 2026-08-13)
*"material icon se hote hain vah bhi humne nahi diye hain … jahan per jaruri hote hain"*.
Sources read: `raw.githubusercontent.com/material-components/material-components-android/
master/docs/components/CommonButton.md`, and the extended-FAB description on
`developer.android.com`.

- **Button emphasis order is a real spec**, quoted: *"There are five button styles, in order
  of emphasis: 1. Elevated button 2. Filled button 3. Filled tonal button 4. Outlined button
  5. Text button."* Every `Button` in the app was filled, so a two-action screen expressed no
  hierarchy. `AppPalette.SECONDARY_BUTTON` is the tag for the **outlined** variant —
  transparent fill, 1dp `outlineBright` stroke, `primary` label — handled in
  `applyAccessibleTheme`'s `Button` branch. Contrast holds: label 10.3:1, stroke 11.0:1,
  both above their floors. Used on the wizard's **Back**, so **Next/Finish** is the only
  filled (higher-emphasis) action on that screen.
- **Icon placement is specified**, quoted: *"Icons visually communicate the button's action
  and help draw attention. They should be placed on the leading side of the button, before
  the label text."* So the wizard's icons use
  `setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)` — the **relative** form,
  which mirrors in RTL — never the left/right form.
- **The extended FAB is *defined* as icon + text** ("Extended floating action buttons are
  distinguished by an icon and a text"), and ours was text only. It now carries `ic_add`.
- Three new vector drawables with standard Material paths: `ic_add`, `ic_arrow_back`,
  `ic_arrow_forward`. **Both arrows are `android:autoMirrored="true"`** — directional icons
  must flip in RTL, which matters now that `supportsRtl` is on.
- Icon tint is set per button with `TextViewCompat.setCompoundDrawableTintList`, because the
  filled and outlined buttons have different label colours; a single static `android:tint` in
  the vector could only match one of them.
- **Icons are now on every action button that has a standard Material icon** (user,
  2026-08-13: *"jo cheez ke liye hota hai already icon bana hota hai vah"*). All path data is
  fetched from **Google's own `google/material-design-icons` repo**
  (`src/<category>/<name>/materialicons/24px.svg`) and pasted verbatim — no hand-drawn paths.
  17 buttons: Setup wizard `auto_fix_high`, TTS Settings `record_voice_over`, Disable battery
  optimization `battery_alert`, Import `file_download`, Export `file_upload`, Share logs
  `share`, Clear logs `delete`, Configuration settings `tune`, per-mode Settings `settings`,
  Test `play_arrow`, Default `restore`, dialog Install `get_app` / Apply `check` /
  Cancel `close`, the FAB `add`, and the wizard's Back/Next arrows.
- One helper does all of it: **`setLeadingIcon(button, iconRes)`** in `Theming.kt` — leading
  placement, the **relative** compound-drawable form so it mirrors in RTL, 8dp padding, and a
  tint chosen from the button's own emphasis variant (`SECONDARY_BUTTON` → `primary`,
  otherwise `onPrimary`). `fullWidthButton` gained an `iconRes` parameter defaulting to 0.
- **Deliberately left without icons:** the Languages screen's "Select all" / "Clear all" /
  "Show selected" — three `WRAP_CONTENT` buttons with two-line labels sharing one row; adding
  24dp + 8dp to each would overflow a compact (<600dp) width. The slider `-`/`+` buttons keep
  their glyph labels, which already act as the icon and carry
  "Decrease …"/"Increase …" content descriptions.
- Icons never touch the accessible name: every one of these buttons keeps its visible text as
  the label, and compound drawables are not announced.
- `GradientDrawable.setCornerRadius(...)` is called as a method, not via the `cornerRadius`
  property: `javap` shows the API-15 check jar has only the setter, and the getter that the
  Kotlin property needs arrived in API 24 — exactly our `minSdk`, so the method form removes
  the edge case entirely.

## Colour and navigation, checked against the rules (user request, 2026-08-13)

**Colour — every pair computed, and the "not by colour alone" rule walked.**
Beyond the earlier table, the components added since were measured too: tab text selected
**9.1:1** and unselected **16.5:1** on the app-bar surface, the tab indicator **9.1:1** (floor
3.0 as a UI component), the outlined button's label **10.3:1** and its stroke **11.0:1**.
Every state is carried by something other than colour as well: the selected tab has
TabLayout's indicator underline, a switch has thumb position, a radio has its dot, a disabled
button is announced as disabled by TalkBack, and "Installed"/"Not installed" is words. The
palette is blue/cyan on dark grey, so no red-green pair carries meaning anywhere.

**A regression of my own, found and fixed.** Giving the outlined button
`view.background = GradientDrawable` replaced the default background and therefore **removed
its ripple**, leaving no pressed or focused feedback — WCAG 2.4.7 wants the focus state
visible. `outlinedButtonBackground` now returns a `RippleDrawable(colorControlHighlight,
shape, mask)`, so press and focus are visible again. **Never assign a bare `Drawable` to a
button background here; wrap it.**

**Navigation.** Quoted from the navigation principles: *"Within your app's task, the Up and
Back buttons behave identically"*, and *"If a user is at the app's start destination, then
the Up button does not appear, because the Up button never exits the app."* Every sub-screen
(Languages, Configuration settings, Voice setup, Mode settings, the wizard) is reachable only
from inside the app, never by deep link, so **system Back already is Up** and a separate Up
affordance is not required — that is why the `NoActionBar` screens have none.
Verified alongside it: `MainActivity` is the launcher and the fixed start destination; the
manifest declares **no** `launchMode`, `noHistory`, `taskAffinity` or `clearTaskOnLaunch`, so
the back stack is an ordinary stack; every internal `startActivity` passes no flags and simply
pushes; the only `FLAG_ACTIVITY_NEW_TASK` uses target **external** apps (Play Store, system
TTS settings, share chooser) plus the deliberate post-import restart, which is correct there.
Tab swiping is lateral navigation, so Back not traversing it is correct.
**Predictive back**: we intercept back nowhere, and the docs say apps using default back
navigation need no code, so the manifest now simply declares
`android:enableOnBackInvokedCallback="true"` to opt into the system animations.
Source: `developer.android.com/guide/navigation/principles`,
`.../guide/navigation/custom-back/predictive-back-gesture`.

## TalkBack pass over the whole UI (user request, 2026-08-13)
Google's pre-launch report groups accessibility findings into four buckets — **touch target
size, low contrast, content labelling, implementation** (e.g. "traversal order that doesn't
match logical arrangement"). Touch targets and contrast were closed earlier; this pass was
labelling and implementation, screen by screen.

**Fixed — and the first two are the very first thing a blind user meets:**
- **The startup scan said nothing.** `currentEngineText` is rewritten as each engine is found,
  but it was not a live region, so TalkBack read "Please wait while Easy Voice scans …" once
  and then went silent for the whole scan. Android 16 names `setAccessibilityLiveRegion` as
  the API for a **critical UI change**; it is now `ACCESSIBILITY_LIVE_REGION_POLITE`, which
  queues rather than interrupts.
- **Finishing the scan announced nothing** either — `progressBox` went `GONE` and the tabs
  `VISIBLE` in silence. That is the documented "significant UI change" case, so `tabPager`
  now carries `ViewCompat.setAccessibilityPaneTitle(…, "Easy Voice settings")` and TalkBack
  announces it as the pane appears.
- **The indeterminate `ProgressBar` was a focus stop that said nothing.** It is decorative —
  the text above and below it carries the meaning — so it is now
  `IMPORTANT_FOR_ACCESSIBILITY_NO`.

**Considered and deliberately rejected, so it is not "fixed" later by mistake:**
- **`labelFor` / `contentDescription` on the spinners.** A `contentDescription` on a `Spinner`
  *replaces* the node text, so TalkBack would announce the label and **not the selected
  value** — strictly worse. `ViewCompat.setLabelFor` avoids that but makes the label be read
  twice (once as its own focus stop, once with the spinner). Every spinner already has an
  adjacent label `TextView`, which is the conventional Android pattern and reads correctly.
  Leave it alone.
- **Reordering the radio / Settings button / description triple.** Traversal is radio →
  "<Mode> settings" button → description, because the button shares the radio's row. That
  matches the *visual* arrangement, which is what the implementation check actually asks for,
  so `accessibilityTraversalAfter` would be solving a non-problem.

**Re-verified in this pass:** no label contains its own role word (a `contentDescription` of
"… settings" never says "button", so TalkBack does not say "button button"); `"Select\nall"`
and friends read as normal words; no focusable view is unlabelled; header containers are not
focusable, only the header `TextView` inside them is.

## Which control belongs where (user request, 2026-08-13)
Sources: Material Components Android `docs/components/Switch.md` and `Checkbox.md`.
- **Switch** — *"Toggle a single item on or off"*, *"Immediately activate or deactivate
  something"*, *"The effects of a switch should start immediately, without needing to save"*,
  *"best used to adjust settings and other standalone options"*.
- **Checkbox** — *"Checkboxes let users select one or more items from a list, or turn an item
  on or off."*

Audited every selection control against that:

| Control | Where | Verdict |
|---|---|---|
| 9 Advanced switches | standalone settings, effect immediate | **Switch — correct** |
| "Use locale spans" | standalone setting | **Switch — correct** |
| "Dedicated engines" | standalone setting | **Switch — correct** |
| Mode radios | pick one of five | **RadioButton in a `RadioGroup` — correct** |
| Language / voice / variant / mode-int spinners | pick one of many | **Dropdown — correct** |
| Speed / Volume / Pitch | continuous value | **SeekBar — correct** |
| "Add language" | screen's primary action | **Extended FAB — correct** |
| Language list rows | **select one or more items from a list** | **Checkbox — corrected 2026-08-13** |

**The language list is the one place the 2026-08-12 "all checkboxes → switches" sweep went too
far.** By the quotes above a list multi-select is the checkbox case, while switches are for
*standalone* options. Raised with the user, who agreed (*"han kar do checkboxes"*), so
`rowBoxes` is `ArrayList<android.widget.CheckBox>` again and the rows are
`android.widget.CheckBox`. Everything else stays a `MaterialSwitch` — those really are
standalone settings. `applyAccessibleTheme`'s `CompoundButton` branch already styles
checkboxes, and `setRowChecked`/`suppressRowEvents` are type-agnostic, so nothing else moved.

**Real defect found in the same pass:** `addLocaleSpanRow` added its switch with
`LayoutParams(MATCH_PARENT, MATCH_PARENT)` — a `MATCH_PARENT` *height* inside a vertical
`LinearLayout`, where every other switch row in the app uses `WRAP_CONTENT`. Fixed.

## Animation (user request, 2026-08-13) — the answer was "respect the setting", not "add motion"
Audited first: **the app writes no animation code at all.** No `Animator`, no
`overridePendingTransition`, no `TransitionManager`, no interpolators, and `styles.xml`
overrides no window or activity transition. So app open/close, the `ViewPager2` page scroll,
the `TabLayout` indicator, `MaterialSwitch` thumb travel and every ripple are **framework
animations**, which the platform already scales by the user's window/transition/animator
scales. Predictive back is opted in, and from Android 15 those system animations show for
apps that did so — which is us.

That leaves exactly one animation we own: the **indeterminate `ProgressBar`** on the startup
scan. Android's accessibility settings carry **Colour and motion → "Remove animations"**, for
users with motion sickness, photosensitivity or seizure triggers, and it works *"on supported
apps"*. So `animationsEnabled(context)` reads
`Settings.Global.ANIMATOR_DURATION_SCALE != 0f` (wrapped in try/catch, defaulting to true)
and the spinner is `GONE` when the user has removed animations. Nothing is lost: the
"Please wait …" text and the per-engine progress line are still there, and that line is a
polite live region, so a TalkBack user is better informed than the spinner ever made them.

**Do not "improve" this by adding decorative transitions.** Adding motion would work directly
against the setting this section is about, and it buys a blind user nothing.
`Settings.Global` is API 17, so the local kotlinc check reports
`unresolved reference: Global` — verified with `javap` that the API-15 jar has only
`Settings$System`, `Settings$Secure` and `Settings$NameValueTable`; `minSdk` is 24.

## Three TalkBack regressions the user caught (2026-08-13) — two were mine
- **Tabs stopped announcing their position.** I had removed the `", tab N of M"` suffix on the
  belief that Material's `TabLayout` supplies collection info that TalkBack renders itself. On
  device it does not — the user heard nothing at all afterwards. The position is back, but as
  `"<title>, N of M"` **without the word "tab"**, because the service appends the role: the
  original string said "tab" twice precisely because it contained the word.
  **Lesson: do not delete an announcement on the theory that the framework supplies it —
  the user's device is the authority.**
- **The wizard never announced its step.** `ViewCompat.setAccessibilityPaneTitle` only fires
  when the pane's **visibility changes** — the very note in the research that set it up — and
  `stepBody` stays visible while only its children are swapped, so no event was ever sent.
  Replaced with `setTitle(...)`, the other API the Android 16 page lists for a significant UI
  change, which does fire a window-state-changed event. `tabPager`'s pane title is **kept**,
  because that one really does go `GONE` → `VISIBLE` and therefore does fire.
  Use `setTitle(text.toString())`, not `title = …`: the property form failed the kotlinc
  check with "val cannot be reassigned" / an impossible smart cast.
- **Coming back from a sub-screen dumped focus at the top.** `MainActivity.onResume` rebuilds
  the Modes tab and `ConfigurationActivity.onResume` rebuilds its list, so the control that
  opened the screen no longer exists and TalkBack falls back to the first element. Both now
  remember what launched the screen — `pendingFocusMode` in `TabViews.kt`, `pendingFocusRow`
  in `ConfigurationActivity` — and hand accessibility focus back to the rebuilt control with
  `ViewCompat.performAccessibilityAction(view, ACTION_ACCESSIBILITY_FOCUS, null)`, posted so
  it runs after layout. The flag is cleared as it is consumed, so focus is only restored on
  the return trip and never steals focus later.

## Accessibility rule: `announceForAccessibility` is BANNED (researched 2026-08-13)
The user asked for the guidelines to be read properly rather than recalled. Google's
**Android 16 behaviour-changes page deprecates accessibility announcements** — both
`View.announceForAccessibility()` and dispatching `TYPE_ANNOUNCEMENT` events — because they
"create inconsistent user experiences for TalkBack and Android's screen reader" users. The
documented replacements, by case:
- **significant UI / window change** → `Activity.setTitle()` and
  `View.setAccessibilityPaneTitle()` (`ViewCompat.setAccessibilityPaneTitle`, which is
  backwards compatible and spoken by TalkBack from API 19 — our `minSdk` is 24);
- **critical UI change** → `setAccessibilityLiveRegion()`, and the docs say use it
  *sparingly*, since it fires on every update;
- **errors** → `CONTENT_CHANGE_TYPE_ERROR` / `setError()`.

Applied: the wizard's per-step announce became
`ViewCompat.setAccessibilityPaneTitle(stepBody, <step heading>)` — a content swap inside one
window is exactly the pane case — and the mode-settings toggle's announce was deleted
outright, because `applyExpandState` already sets `ViewCompat.setStateDescription`, which is
the documented carrier for expanded/collapsed and avoids double-speaking.
**There is now no `announceForAccessibility` anywhere in the app; do not reintroduce one.**
Sources: `developer.android.com/about/versions/16/behavior-changes-all`,
`developer.android.com/guide/topics/ui/accessibility/principles`.

## Settings live in STATICS, never re-read from prefs at runtime (audited 2026-08-13)
AutoTTS loads every setting into a static once (`c3.n.p`/`n.r` fill `AutoTtsService.G/H/O/P/
S/…`), the settings UI writes those statics directly, and the synthesis path reads **only**
the statics — `Z()` resolves its fallback with `this.Q(G)`/`this.M(G)`, `M()` and `Q()` walk
the in-memory `c3.n.c`, and `onSynthesizeText` re-reads nothing from `SharedPreferences`.
Prefs are a persistence layer, not a runtime source.

We had drifted from that in three places, all fixed:
- `onLoadLanguage` read `auto_mode_language` from prefs → now the `autoLang` static;
- the dual branch of the chunk loop read `dual_mode_language` from prefs → now `dualLang`;
- `onSynthesizeText` did `scannedLangsIso3 = prefs.getScannedLangs()` **per utterance**, and
  `scannedLangsIso3` was never read anywhere — dead. The call's only remaining effect was the
  one-time legacy-key migration, which now runs once in `loadAllSettings()`.

**Why it mattered:** prefs hold what was last *persisted*; the statics hold what the user has
just *chosen*. They only converge when `persistAll` runs (onPause / the wizard's Finish). So
choosing Hindi as the dual language inside the wizard updated the static but not the stored
key, and the service kept routing with the previously stored language — the user heard
Gujarati. Any future "the UI says X but it speaks Y" bug should be checked against this rule
first.

`loadModeLangsOnce()` is guarded by `if (autoLang.isNotEmpty()) return` and `loadAllSettings()`
runs only from `onCreate`, so the statics are never silently reloaded over a live edit. Keep it
that way.

**A fourth offender, found and removed 2026-08-25:** the `[EasyVoice:...]` bypass path called
`prefs.getLocaleForLangPkg(...)` when the prefix carried no locale. AutoTTS's branch is
`if (!engine.isEmpty() || !locale.isEmpty()) f0(engine, j0(locale), variant, false);` — the
parsed fields and nothing else, an empty engine meaning "keep the last one" inside `loadVoice`
and `j0("")` being `Locale("")`. Ours also ran the load for **every** bypassed utterance rather
than only a forced one. Unreachable (`speakTest` always sends a real package and locale), but
it was a pref read on the **Test button**, where prefs and statics differ by definition.
**Swept afterwards: the only `prefs.` calls left on the synthesis path are `prefs.toIso3`,
which is a pure conversion and reads nothing.** The accessor itself outlived that fix as dead
code and was deleted on 2026-08-26, along with `SharedPrefsManager.isGoogleTtsInstalled`,
which had been dead since `getReadingMode()` moved to the statics — the Google-TTS presence
test AutoTTS's `c3.n.o` actually performs lives in `LangStore.loadMode`.

**The two non-`EasyVoiceTtsService` flags, checked 2026-08-13 when the user asked whether they
could be statics too:**
- **Logging** already is one. `EasyVoiceLogger.loggingEnabled` is a `@Volatile` in-memory flag,
  the Advanced switch reads it (not prefs) and writes both it and the pref with `apply()`. That
  is byte-for-byte AutoTTS's `c3.p`: field `d`, seeded in the constructor from
  `getBoolean("logging_enabled", false)`, read by `g()`, written by `j()` which also `apply()`s.
  The immediate `apply()` here is correct and must NOT be moved into `persistAll` — AutoTTS
  persists this one eagerly, unlike the settings that wait for `n.v()`.
  **Gap found and fixed:** AutoTTS seeds it from a *lazily created* singleton (`p.f(context)`),
  so whichever of the UI or the service touches it first seeds it. Ours seeded only in the
  service's `onCreate`, so opening the app before the TTS service ever started showed the
  "Enable logging" switch OFF while the pref said ON. `MainActivity.onCreate` now seeds it too,
  next to the `LangStore.load*` calls.
- **`setup_done` — GONE with the setup wizard (2026-08-18).** Its only reader and writer were
  the wizard and the first-run launch in `MainActivity`, so `isSetupDone()`/`setSetupDone()`
  were removed with it. Nothing reads the key any more. (It used to be argued for as a
  persistent one-shot marker that must survive process death, which a static cannot do — that
  reasoning stands, but there is no longer anything to mark.)

## Jetpack Compose migration (user decision, 2026-08-13) — IN PROGRESS, screen by screen
The user chose to move the **whole UI** to Compose, strictly per Google's accessibility rules.
I advised against it once; they decided, so this is the plan of record.

**Why Compose is genuinely better here, from the docs (not opinion):** Material components
apply the **48dp** minimum touch target themselves (only when the component is interactive —
`onCheckedChange` non-null); `clickable`/`toggleable` **merge child semantics** so an icon +
text button is one node, which our View code does not do at all today; and `heading()`,
`paneTitle`, `liveRegion`, `customActions`, `stateDescription` are first-class modifiers.

**The one real cost, and it is environmental, not Compose's fault:** Google Maven is blocked
by the proxy, so the Compose compiler plugin and every `androidx.compose` artifact are
unresolvable locally. `android.jar` comes from Maven Central, which is why the View code can
still be type-checked; **there is no equivalent for Compose.** CI builds fine (it has network).
So every Compose mistake reaches a real build before anything catches it. That is exactly why
this migration is **incremental, one screen per build**, never a big-bang rewrite.

**Toolchain, verified against Google's compose-kotlin compatibility table, not guessed:**
Kotlin `1.9.22` → Compose Compiler **`1.5.10`**; BOM `2024.02.00`; `activity-compose:1.8.2`;
`compose.ui`, `compose.ui:ui-graphics`, `material3`. **`material-icons-extended` is
deliberately NOT added** — it is large, and we already ship the authentic Google icon paths as
vector drawables, which Compose reads with `painterResource(R.drawable.…)`.

**Order:** `ConfigurationActivity` first (smallest real screen), then the other activities,
with `MainActivity`/`TabViews` last because they are the most intertwined.

**THE MIGRATION IS FINISHED (verified 2026-08-26).** Every screen is Compose: there is no
`ComposeView`, no `AndroidView`, no `setContentView` and no View-era theming helper left in
the tree — `applyAccessibleTheme`, `applyPageTheme`, `applyResponsiveWidth`, `evSwitch`,
`fullWidthButton`, `setLeadingIcon` and `AppPalette` are all gone, and `Theming.kt` is now
`ComposeTheme.kt`. Every activity calls `setContent`. Sections below that describe those
helpers are a record of the View era; read them for the *decision*, not for the file name.

**Interop pattern for a tab (historical).** While the migration was in flight,
`buildAdvancedTabView` still returned a `View` that was a `ComposeView` hosting
`AdvancedScreen`, so a tab could be ported without touching the pager. Use the same shape for
the Modes tab.

**`punctuationInFlowBox` is gone with the Advanced View code.** It was a file-level `var` the
Modes tab poked to disable that one switch when the punctuation mode is "Specific language".
`AdvancedScreen` derives `enabled` from `EasyVoiceTtsService.punctuationModeInt != 3` instead,
so the behaviour survives without the cross-screen global. `applySpecificVisibility` used to
carry a leftover `punctuationInFlowBox?.isEnabled` no-op; **both are gone** — the Modes tab
was ported and the View code went with it. Neither name exists in the tree any more.

**~~`blocks.py` is now the ONLY safe way to edit a `write_source` block.~~ OBSOLETE
since 2026-08-26 — the source is checked in and you edit it directly.** Kept as a
record of why: a generated block ended either `"}\n")` or `"}\n"` + a lone `)` line, and
slicing with a hard-coded terminator silently swallowed whole files when the style did
not match. That is exactly how `LanguagesActivity.kt` once disappeared, caught only
because `ktimports` then reported its class as unimported. That entire class of accident
is gone with the generator.

**The wizard's settings step still hosts a View** through `AndroidView { buildModesTabView(…) }`,
because `TabViews` is not ported yet. That interop stays until it is.

**Bug the first device test caught — read this before writing any Compose surface.**
Material3's `ExtendedFloatingActionButton` fills with **`primaryContainer`**, not `primary`.
In this palette `primaryContainer` is the dark header blue, which is **1.28:1** on the
background — the FAB was effectively invisible. The View version tinted it with `primary`
(10.3:1) and laboured the label `onPrimary` (7.2:1). **Always state `containerColor` /
`contentColor` explicitly on Material3 components rather than trusting a default to land on
the colour role you meant.** The user also reported the FAB announcing no label, so it now
carries an explicit `Modifier.semantics { contentDescription = … }` alongside its `text` slot.

**Accessible checkbox row pattern** (used in `LanguagesScreen`, keep it for every future row):
`Row(Modifier.toggleable(value, role = Role.Checkbox, onValueChange = …))` with
`Checkbox(onCheckedChange = null)` inside. That merges the row into one node, so TalkBack
reads "<language>, checkbox, checked" once, and Material supplies the 48dp target.

**Checker patches for Compose, each negative-tested:**
- `ktresolve.py` skipped identifiers followed by `(`, so a **generic call** like
  `mutableStateListOf<Boolean>()` looked like a bare unresolved name. It now skips `name<T>(`.
- `ktimports.py` knows **receiver-scope composables** (`ExposedDropdownMenu`) that are called
  on a scope and never imported.
- `ktimports.py` also flags **SCOPE-ONLY imports**: `weight`, `align`, `menuAnchor` and
  friends are `RowScope`/`ColumnScope` members, so `import …layout.weight` is an unresolved
  reference that no *missing*-import check could ever see. I wrote exactly that bug in the
  wizard; the new guard catches it.

**Checker patches this needed** (both negative-tested afterwards):
- `ktimports.py` now adds **module-wide capitalised top-level `fun` names** to the known set —
  every `@Composable` is a capitalised function, so without this each one is a false positive.
- `ktresolve.py`'s parameter patterns now allow an **annotated type** (`content: @Composable
  () -> Unit`); the leading `@` made it miss the parameter and report it as unresolved.

**~~`build.gradle.kts` lives in a raw YAML block, not an escaped Python string.~~
OBSOLETE since 2026-08-26** — it is `app/build.gradle.kts`, an ordinary file.

## A merged node carries NO name for non-TalkBack screen readers (2026-08-19)
The user tried a screen reader other than TalkBack: swiping the dropdown list announced
"button, button, button" with no item labels. TalkBack was fine. The cause is in Compose's
own `AndroidComposeViewAccessibilityDelegateCompat`:

```kotlin
if (!node.unmergedConfig.isMergingSemanticsOfDescendants || node.replacedChildren.isEmpty()) {
    info.contentDescription = node.unmergedConfig.getOrNull(ContentDescription)?.firstOrNull()
}
info.text = getInfoText(node)      // reads node.UNMERGED config
```

A `DropdownMenuItem` / `Button` / `clickable` row **merges** its children and **has** children,
so BOTH branches skip it: the focused node gets neither `text` nor `contentDescription`. The
label lives on the **fake child nodes** Compose emits. TalkBack walks those; a reader that only
inspects the focused node finds nothing and announces the bare role.

**Why `replacedChildren` is never empty here — read from the source, do not re-derive.**
`SemanticsOwner.getAllUncoveredSemanticsNodesToIntObjectMap` starts from
`unmergedRootSemanticsNode`, so the delegate walks the **unmerged** tree, i.e.
`mergingEnabled = false`. `SemanticsNode.getChildren` only short-circuits to
`findOneLayerOfMergingSemanticsNodes()` when its *gated* `isMergingSemanticsOfDescendants`
(`mergingEnabled && config.isMergingSemanticsOfDescendants`) is true — which it is not in that
tree — so it falls through to `unmergedChildren()`, and
`LayoutNode.fillOneLayerOfSemanticsWrappers` adds **every** child layout node that
`has(Nodes.Semantics)`. A `Text` marked `clearAndSetSemantics { }` still has a semantics node,
so it still counts. **`clearAndSetSemantics` on the child does NOT make the parent's
`contentDescription` land on the parent node**; the earlier note here claimed it did and was
wrong.

**What the fix actually does, and the on-device proof.** Setting `contentDescription` on the
merging node makes Compose emit the "contentDescription clobbering" **fake first child**
carrying it (`SemanticsNode.emitFakeNodes`). Google's **Accessibility Scanner**, run by the user
on 2026-08-19, read that description back verbatim from a dropdown item
(*"This item's content description, \"English (eng), Selected\", contains the state
\"selected\""*), which proves the label really is exposed to a plain
`AccessibilityNodeInfo` consumer. So the pattern is:
- `Modifier.semantics { contentDescription = <label> }` on the clickable node — this is what
  puts the name in the tree;
- `Text(..., modifier = Modifier.clearAndSetSemantics { })` on the visible label — this is
  **only** to stop the name being announced twice, not to empty `replacedChildren`;
- `contentDescription = null` on any decorative icon.

**Never put state in the name.** That was the scanner's one reproducible finding. State goes in
real semantics — `selected`, the `ToggleableState` that `toggleable`/`selectable` supply, or
`stateDescription`. The dropdown item's `", Selected"` suffix is gone; it now sets
`selected = index == selectedIndex`. (`SemanticsProperties.Selected` maps to `info.isChecked`
unless the role is `Tab`, and it suppresses `ACTION_CLICK` **only** for `Role.RadioButton` /
`Role.Tab` — our menu items declare no role, so activation is untouched.)

**The label belongs to the control, not beside it (corrected 2026-08-19).** The rule here used to
say "put only the VALUE on a control whose label is a separate Text beside it, never
'label, value'", because the label was its own leaf node and would be announced twice. That
produced a worse bug, which Google's `DuplicateSpeakableTextCheck` calls out as a WARNING —
*two clickable views with the same speakable text*: on mode settings, "Select language for
reading numbers / punctuations / emojis" all default to `"Auto language"`, so three controls
announced identically, and the Latin / non-Latin preferred languages are both `"English (eng)"`
by default, making five. The same held for `ValueSlider`, whose Slider is already named "Speed"
while a Text above it said "Speed" again.

So `LabeledDropdown` now names the button `"<label>, <value>"` and silences the visible label
`Text` with `clearAndSetSemantics { }`; `ValueSlider` does the same with its label. The text
stays on screen — only its semantics go. Use `clearAndSetSemantics`, **not**
`hideFromAccessibility()`: that API's own KDoc says *"If looking for a way to clear semantics of
small items from the UI tree completely because they are redundant with semantics of their
parent, consider [clearAndSetSemantics] instead"* — `hideFromAccessibility()` is for **occluded**
content. (The Compose accessibility doc page's summary table reads the other way round; the KDoc
is the authority.)

**Applied to every merged clickable in the app (2026-08-19)** — 25 controls: the 9 Advanced
switches and `ActionButton`, Import/Export/Share logs/Clear logs, the Configuration language
rows, the Languages checkbox rows + Select all/Clear all/"Show selected" chip, the dialog's
Install/Apply/Cancel, the FAB, the 3 tabs, the mode radio rows and their Settings button, the
dropdown trigger and items, the slider −/+, Test and Default. Keep the pattern on anything new.

**"Unexposed Text" from the scanner is flaky — do not chase it.** Three scans of the *same* open
dropdown produced "No suggestions", "No suggestions", and 17 × *"Unexposed Text … Ensure this
item's accessibility label includes its visible text"*. The check is OCR-timing based; judge it
by whether the label is set, not by one report.

## NEVER put a lazy list inside a DropdownMenu (crash, 2026-08-18)
`DropdownMenu` sizes itself to its widest item, i.e. it asks its content for an **intrinsic
width**. A `LazyColumn` is a `SubcomposeLayout` and cannot answer that:

    IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout layouts is
    not supported... such as lazy lists, BoxWithConstraints, TabRow

The exception suggests "adding a size modifier ... to fast return the queried intrinsic
measurement". **That was tried — `width(280.dp)` on the list — and the app crashed again in
the same place.** So the mitigation the log prints does not save this combination; the lazy
list simply cannot live in a menu. Do not reintroduce it, and do not trust that hint here.

The menu therefore holds ordinary `DropdownMenuItem`s. Because a `LazyColumn` would have
supplied them automatically, `collectionInfo` / `collectionItemInfo` are instead declared by
hand on a plain `Column` inside the menu (a `Column` is not a `SubcomposeLayout`, so it
answers intrinsics fine) — that is what lets TalkBack say "item 5 of 137" while swiping a
130-language list.

The app's other lazy lists are fine where they are: `ConfigurationScreen` and
`LanguagesScreen` sit inside plain `Box`/`widthIn` parents, and `TabRow` sits in `Scaffold`'s
`bottomBar`, none of which query intrinsics.

## Why the checks are what they are (2026-08-12; the procedure moved to `tools/` on 2026-08-26)
**Run `tools/check-all.sh`** — see the top of this file. What follows is the reasoning
behind it, which is still exactly right and is the reason none of these steps may be
skipped. The old procedure ("extract the generator, `ast.parse` it, generate into a
tree…") is gone with the generator; everything below about *why* is not.
- kotlinc **with a real `android.jar` on the classpath**, and the errors **diffed against the
  same run on the last commit that built green**. Script: `tools/check/kotlin-typecheck.sh <good-ref>`.
- Without `android.jar` every `android.*` type is unresolved, so kotlinc silently type-checks
  **nothing** at call sites. That is exactly how `MainActivity`'s
  `buildModesTabView(container.context, prefs) { testTts }` reached CI: adding a trailing
  `settingsOnlyForMode: String?` parameter made Kotlin bind that **trailing lambda** to the new
  last parameter instead of `testTtsProvider`. Negative-tested both ways — invisible without the
  jar, caught immediately with it.
- `android.jar` comes from **Maven Central** (`com/google/android/android/4.1.1.4`); it is API 15.
  **Google Maven is blocked by the proxy**, so androidx/material are unavailable. The resulting
  noise is constant and cancels out in the baseline diff. Two known-noise signatures, both
  already present for known-good files, so do not chase them:
  - a `ComponentActivity` subclass → `unresolved reference: androidx/ComponentActivity`, then
    `finish`/`resources`/`packageManager`/`setContentView`/`onCreate`/`onPause` unresolved and
    `type mismatch: inferred type is <Activity> but Context was expected`;
  - `setTextAppearance(int)` is API 23 → `no value passed for parameter 'p1'` +
    `type mismatch: inferred type is Int but Context! was expected`;
  - `View.generateViewId()` is API 17 → `unresolved reference: generateViewId` (verified
    absent from the jar with `javap`);
  - `PackageManager.PackageInfoFlags` is **API 33** → `unresolved reference 'PackageInfoFlags'`,
    at the two sites that mirror `c3.a0.a`'s overload switch. Give the result an
    **explicit `PackageInfo` type** or the error type cascades into every member read
    below it — that is four extra noise lines instead of one;
  - `PackageInfo.getLongVersionCode()` is **API 28** → `unresolved reference 'longVersionCode'`.
    Verified with `javap` over the check jar: it declares only `public int versionCode`.
    Guarded by `Build.VERSION.SDK_INT >= 28` at both call sites;
  - **`android.speech.tts.Voice` and `TextToSpeech.getVoices()` are API 21** →
    `unresolved reference 'Voice'` / `'voices'`. Verified by unzipping the check jar:
    **zero** `android/speech/tts/Voice` entries and **zero** voice methods on
    `TextToSpeech`. `minSdk` is 24, so this is fine. It inflates the
    "our-own-name unresolved refs" COUNT, which is a guard meant to make you look --
    look, confirm the NEW error *texts* list is empty, then commit, and the count
    self-resolves because the baseline is HEAD;
  - `clipToPadding = false` needs the **getter** `getClipToPadding()`, which is API 21 —
    `javap` shows the API-15 jar has only `setClipToPadding(boolean)`, so Kotlin cannot form
    the property → `unresolved reference: clipToPadding`. Fine at `compileSdk 34`/`minSdk 24`;
  - anything from **`com.google.android.material`** (`MaterialSwitch`,
    `ExtendedFloatingActionButton`) → `unresolved reference: google` plus a cascade on its
    members (`text`, `variable expected`), because Google Maven is blocked. The baseline
    already carries nine of these for `MainActivity`/`Theming`;
  - a SAM lambda passed to an **androidx** method (e.g. `ViewCompat.addAccessibilityAction`)
    → `cannot infer a type for this parameter`, because the functional interface itself is
    unresolved. The baseline already carries this for `EasyVoiceTtsService`,
    `LanguagesVoicesViews`, `MainActivity` and `TabViews`.
- Judge the run by the **NEW error texts** the diff prints, not by the total count.

## Number / punctuation / emoji "Specific language" — VERIFIED EXACT (2026-08-18)
Checked against CFR, not assumed, after the user asked whether the specific option matches.
Do NOT re-derive this; it is done.

**Where AutoTTS decides.** `onSynthesizeText` dispatches on the reading mode `AutoTtsService.S`:
line 2032 guards `S != 1` (dual), 2178 guards `S != 4` (mix), 2392 guards `S != 5`
(multilingual). Anything between those lines belongs to that mode.

**Mix branch, lines 2207-2262.** For each segment the type is tested with `d0.n` (number),
`d0.o` (punctuation), `d0.l` (emoji), then the matching mode int decides the language:
```java
var7_25 = AutoTtsService.I;              // K for punctuation, M for emoji
if (mode != 0 && mode != 1) {
    if (mode != 2) {
        if (mode == 3) Q.add(new e0(text, AutoTtsService.J));   // L / N
        break;                                                   // >3: nothing is added
    } else Q.add(new e0(text, AutoTtsService.P));
    break;
}
Q.add(new e0(text, AutoTtsService.O));
```
So **0 or 1 -> O (mix latin), 2 -> P (mix non-latin), 3 -> the specific language, anything
else -> the segment is dropped.** Ours is `languageForSegmentKind`, which returns
latinFallback / nonLatinFallback / specific / null, and the caller adds the chunk only when
non-null — the same four outcomes.

**Dual branch, lines 2060-2157.** Types dispatch to: 1 -> `"eng"`, 2 -> `H` (dual language),
3 -> `J`, 4 -> `L`, 5 -> `N`. Ours matches exactly.

**A false alarm worth recording so it is not raised again.** Line 2356 maps the FIRST segment
of `AutoTtsService.Q` to a preflight language with `1 -> O, 2 -> P, 3 -> J, 4 -> L, 5 -> N,
else -> the already-resolved language`. That looks like it contradicts our
`1 -> "eng", 2 -> dualLang`, but 2356 sits between 2178 and 2392, so it is the **mix** branch,
while ours is inside `if (modeInt == 1)` — the **dual** branch. Different branches, no bug.

**CFR is genuinely broken in this region** (`lbl432:`, `// 3 sources`, `break block140..172`),
so the structure was recovered from the mode-dispatch line numbers and the `break blockNNN`
targets rather than from indentation. Smali was not needed.

**Advanced tab defaults, all verified against `c3/n.java` q() and the persist:** strip_audio_attr
false, force_accessibility_stream false, keep_alive_mode false, show_notification false,
disable_advanced_detection **true**, quick_character_reading false, punctuation_with_sentence
**true**, smart_number_reading false; and `number/punc/emoji_specific_language` each fall back to
`n.e(Locale.getDefault())` when empty. Ours matches all of it.

## ~~CLD3 (user decision, 2026-07-29)~~ — SUPERSEDED, see "CLD3 IS GONE" at the top
This section used to say the Advanced-tab row **"Use CLD3 (neural language
detection)"** was an EasyVoice-only feature that **must NOT be removed**, and that
wherever CLD2 detects, the switch must be able to put CLD3 there instead. **The
owner reversed that on 2026-09-02 and CLD3 is gone, A to Z.** The paragraph is kept
only so a future session that remembers the old rule finds the reversal instead of
the rule. Do not act on it.

## How to Trigger Build
```
mcp__github__actions_run_trigger → run_workflow
owner: bariyasachin96, repo: omni
workflow_id: 262884892, ref: claude/yaml-file-nk3czh
```

## Full Diff List (117 differences — fresh deep analysis)
- `.claude/autotts_vs_easyvoice_full_diff.txt` — complete numbered list (DIFF-1 to DIFF-117)
  - HIGH: 21 items, MEDIUM: 41 items, LOW: 53 items
  - Generated by 10-parallel-agent workflow on 2026-06-24

## TWO REGRESSIONS I INTRODUCED, and the rules that stop them recurring (2026-08-21)
The user reported that the reading flow had changed in **every** mode. Both causes were
mine, from the 5.7.7.26 work. Neither was caught by any checker, because both are runtime
behaviour, not compilation.

### 1. Language hints must NOT be pushed per utterance
Chasing `s0()` I made `refreshEnabledLangs()` — which `onSynthesizeText` runs for **every
utterance** — push `setLanguageHints` as well as `setDetectSets`. **AutoTTS never does
that.** Every one of its `s0()` calls sits immediately after the language list is loaded or
rebuilt: `AutoTtsService.e0()`, and the eleven settings-screen sites in `c3/k.java`. There
is no `s0()` anywhere in `onSynthesizeText`.

Why it was audible: the hints steer CLD2 (`CLDHints`) and CLD3 (`cld3DetectRaw` filters its
top-3 by the same list), so they decide which languages the detector may name.
`LangStore.languages` is a shared static that the **settings screens rebuild, and not always
whole** — `voiceLanguageLabels(ctx, 1)` builds `dualLangList`, which is exactly two entries,
and the other modes use `rebuildFromScan(onlyEnabled = true)`. Re-deriving hints from it on
every utterance meant one visit to the Configuration tab left the detector hinting at two
languages for the rest of the process.

**The rule: `pushLanguageSets()` IS `s0()` — call it only where the list was just loaded or
rebuilt. The per-utterance call is `pushDetectSetsOnly()`.** That per-utterance call has no
AutoTTS counterpart at all; it exists because their detector reads `c3.n.c`/`c3.n.f` live
from Java while ours lives in C++ and must be told. The detect sets are the live list, so
refreshing them per utterance is right; the hints are not.

### 2. Lock-order inversion — `languages` → `this` vs `this` → `languages`
Built in two innocent-looking steps: `cbce1a5` made the `LangStore` accessors
`synchronized(languages)` (mirroring `c3.n`), then `59d30cb` wrapped
`reloadLanguagesIfMissing` in `synchronized(languages)` **around** a `synchronized(this)`.

    reloadLanguagesIfMissing()   languages -> this      (loadLanguages)
    onLoadLanguage()             this -> languages      (localeFor/engineFor/variantFor)

`onSynthesizeText` runs on the synthesis thread; `onLoadLanguage` is a
`TextToSpeechService` override Android also calls on **binder threads**. Classic ABBA: when
it hits, speech stops with no error and nothing in the log.

AutoTTS cannot have this — its `P()` holds only the list monitor and `e0()` re-enters the
**same** one. Our `synchronized(this)` is the extra lock. Fixed by reading the guard under
the list monitor and running the reload outside it, which is the shape `loadAllSettings`
already uses.

**The rule: never hold `LangStore.languages` while taking any other lock.** Verified by a
scan of every `synchronized(LangStore.languages)` block for a nested `synchronized(this)` —
none remain.

## The speak loop read UNFILTERED (2026-08-21) — and AutoTTS's four dead branches
`onDone`'s next-chunk step had only ever been skimmed. Read in full this time, along with
`f0` (loadVoice) and `R`/`S`/`V`. **Everything matched; no behaviour was missing.** What the
read did produce is the reason our code looks thinner than AutoTTS's in two places, which is
worth keeping so nobody "restores" the difference.

**`R`/`S`/`V` — again, the names mislead. Read the fields:**
`R(lang)` → `f.e` = **pitch**, `S(lang)` → `f.c` = **speed**, `V(lang)` → `f.d` = **volume**.
The speak step then does `rate = requestRate/100 * S(lang)/100`,
`pitch = requestPitch/100 * R(lang)/100`, `volume = requestVolume * V(lang)/100`. Ours
matches, and so does the bundle handling: copy `requestParams`, remove
`pitch`/`rate`/`language`/`country`/`variant`/`voiceName`/`utteranceId`, additionally remove
`streamType`/`audioAttributes` when strip-audio-attributes is on, and put `volume` only when
it is non-zero. `setUsage(11)` + `setContentType(1)` is the force-accessibility path, applied
once per engine.

**The dual next-chunk error codes are exactly ours:** type 1 → `endSynthesis #2`, types
2/3/4/5 → `#3`, and the auto/Google path → `#4`. Checked against the decompile, not assumed.

### AutoTTS dead code — FOUR pieces now. Do not port any of them.
1. **`d0`'s `n3 == 1` and `n3 == 2` branches.** `onIsLanguageAvailable` returns only `0` or
   `-2`, so only `n3 == 0` runs.
2. **`c3.k.K2(...)`** returns `true` on every path, so `if (!K2(x)) continue` never skips.
3. **`onDone`'s mix/multilingual re-detect**, and
4. **its type fallback** (`1→P 2→Q 3→K 4→M 5→O`), both after the `Q(lang)` engine check.

3 and 4 are dead for the same reason, and it is worth stating precisely. `c3.e0` has two
constructors and only one sets a type:

    e0(String s, int n)    { a = s; b = n;  c = "";  }
    e0(String s, String t) { a = s; b = -1; c = t;   }

**Every** chunk AutoTTS puts in `R` for mix and multilingual uses the **second** — the
locale-span objects out of `e0.g`, `new e0(text, K/M/O)`, and `new e0(run.c, lang)` alike. So
`R.get(0).a()` is **always −1** there: none of the cases 1..5 can match and the language is
used unchanged, which is what ours does directly. The same −1 kills the re-detect, because
`.b()` is a non-empty language string for every one of those chunks, so neither `isEmpty()`
nor `equals("unknown")` can fire.

That is why `LangStore.engineFor(nextLang, modeInt)` in our mix/multilingual next-chunk step
**discards its result on purpose** — it mirrors `Q(lang)` whose only consumer is the dead
fallback. The call now carries that explanation inline.

**`loadVoice` = `f0`, verified line for line:** dedicated-and-not-google delegates to
`loadVoiceDedicated`; an empty package means "keep the last one", otherwise it becomes the
last one; the package is matched with `-` and `_` stripped and the engine must be in state 2;
an empty variant with a stored voice name goes to `loadVoiceOriginal`; and the
"Do nothing!" short-circuit fires when the current voice's iso3 language matches **and**
(its iso3 country matches **or** the requested country is empty) **and** the voice name
equals the variant.

## The whole service algorithm audited against 5.7.7.26 (2026-08-21)
Not a version diff this time — **AutoTTS's algorithm as it stands, method by method,
against ours.** All **66** methods of `AutoTtsService` were inventoried and mapped. Every
one has a counterpart except the three licence methods (`K`, `Y`, `n0`), which are the
deliberate carve-out. **No missing algorithm was found.**

**The 5.7.7.26 helper letters, derived by reading the return expressions — the obvious
guess is WRONG, so use this table:**

| method | returns | ours |
|---|---|---|
| `Q(lang)` | engine package, `"com.google.android.tts"` when mode 3 | `LangStore.engineFor` |
| **`T(lang)`** | **`f.h` = the VARIANT** | `LangStore.variantFor` |
| **`U(lang)`** | **`f.g` = the LOCALE TAG** | `LangStore.localeFor` |
| `R`/`S`/`V(lang)` | speed / volume / pitch | `speedFor` / `volumeFor` / `pitchFor` |
| `o0(Locale)` | engine package for a locale, **three passes** | `findEngineForLocale` |
| `d0(l,c,v)` | the body of `onLoadLanguage` | `onLoadLanguage` |
| `k0`/`t0` | the keep-alive silence loop | inline in `onSynthesizeText` |
| `O(cb,n)` | "endSynthesis #n" + `done()` | `startAndFinish` |
| `P()` | reload guard | `reloadLanguagesIfMissing` |
| `s0()` | push language sets | `pushLanguageSets` |

`T` and `U` both skip an entry whose **`f.f`** (engine) is empty or `"disable"`, or that is
disabled — note the gate is `f.f`, not the `f.j` package list.

**Verified equal, so do NOT re-derive:**
- **`onLoadLanguage`**: locale = `U(lang)`, engine = `Q(lang)`, variant = the passed one or
  `T(lang)`; if the engine is empty both fall back to the **auto-mode language**. Then parse
  the locale tag, and if its iso3 matches, load that voice and update the last engine;
  otherwise fall back to `Locale(lang)` with `o0(...)`. Ours is line-for-line this.
- **`d0`'s `n3 == 1` and `n3 == 2` blocks are DEAD.** `onIsLanguageAvailable` returns only
  `0` or `-2` (`contains ? 0 : -2`), so only the `n3 == 0` path can ever run. Do not port
  the country/variant comparisons in the other two — they are unreachable.
- **`o0` has three passes**: lang+country+variant, then lang+country, then lang alone; each
  guarded by `parts.size != 2 && != 3` and returning `""` if the locale will not parse.
  `findEngineForLocale` is a byte-for-byte port.
- **Keep-alive**: `l0 = new byte[32]`, `callback.start(16000, 2, 1)`, feed in
  `getMaxBufferSize()` chunks, stop when `audioAvailable != SUCCESS`, `wait(100)` between
  rounds. Ours matches including the buffer size.
- **The TTS API surface is identical**: `onGetLanguage` returns one element, the device
  iso3; `onGetVoices` builds `Voice(name, Locale(name), 400, 100, false, HashSet())` from
  `n.i(null, true)`; `onIsValidVoiceName` is `contains ? 0 : -1`; `onIsLanguageAvailable` is
  `contains ? 0 : -2`; `onGetDefaultVoiceNameFor` just returns the language code;
  `onLoadVoice` stores the name, parses it and defers to `onLoadLanguage`.

## Voices and Languages tabs audited (2026-08-21) — NO gap, do not re-audit
`fragment_voices.xml` and `fragment_languages.xml` are **byte-identical** between 5.7.7.18
and 5.7.7.26. In fact the whole resource tree differs in exactly four places, and all four
are already ported:

    layout/fragment_advanced.xml    the Group size row and the Information section
    values/ids.xml                  +app_build_number  +app_version  +smart_number_reading_group
    values/strings.xml              +app_build +app_information +app_version
                                    +smart_number_reading_group_size, and
                                    smart_number_reading_description reworded
    values/public.xml               the id/string numbering that follows from those

**So there is no 5.7.7.26 UI feature we do not have.** That closes the question.

The two tab builders were then extracted from `c3/k.java` in both versions and
structurally diffed. **The Voices builder differs in exactly three ways** — the
`synchronized (c3.n.c)` wrapper, an early-return restructure of the mode-0 branch (same
behaviour), and the Test `try`/`catch`. **The Languages builder in two** — the same
`synchronized` wrapper, and `q0 = false` at the end (the "Show selected" filter resets on
rebuild; ours starts fresh every time the Activity is created, so it already matches).

**Verified equal to AutoTTS, so do NOT re-derive:**
- **Slider maxima**: Speed `setMax(500)`, Volume `setMax(100)`, Pitch `setMax(200)` — ours
  are 500 / 100 / 200, and the floor of 10 matches `P1()`.
- **Default button** = `c3()`: sets speed, volume and pitch all to 100.
- **Dedicated engines** is enabled exactly when `T != 0 && T != 3`, i.e. disabled in none
  and google — ours is `readingMode != "none" && readingMode != "google"`.
- **The Voices language list**: dual/none → `n.h()`; auto/mix/multilingual → `n.i(null,
  false)`; google → `n.i("com.google.android.tts", false)`. `n.i` skips disabled. Ours is
  `voiceLanguageLabels`, same three branches.
- **The variant list** (`b3()`) puts the saved variant first and then
  `Arrays.sort(arr, 1, n4 - 1)` — which sorts `[1, n4-1)` and therefore **leaves the last
  entry unsorted**. Ours carries the identical off-by-one on purpose.
- **`Z2()`** builds `[AutoTTS:pkg:locale:variant]` + sample, or the "Sorry. Sample text for
  language X is missing." line — ours is `speakTest`, same shape.
- **Languages list source**: `n.l(pkg)` labels, `n.j(pkg)` codes, `n.k(pkg)` checked, with
  `pkg = "com.google.android.tts"` unless the mode is 2/4/5. Ours matches, including the
  google filter.
- **Select all**: ticks the FILTERED view but clears `disabled` on the WHOLE list, then
  `n.y()`. **Clear all**: unticks the filtered view, disables everything, re-enables and
  re-ticks the required languages, then `n.y()`. Ours matches both, quirk included.
- **A required language cannot be unticked** — the row is forced back on. Ours does the
  same in `onRowToggled`.
- `c3.k.K2(...)` **always returns true** on every path, so `if (!K2(x)) continue` never
  skips anything. Do not try to reproduce it as a real test.

## CLD3 audited A to Z against CLD2 (2026-08-21) — one real gap, fixed
The standing rule for the CLD3 row is **"wherever CLD2 makes a detection, the switch must
be able to put CLD3 there instead"**. This is that audit, with the 5.7.7.26 additions in
place. **Checked and clean — do not re-derive:**

- **Two** detection sites exist in the native code and **both** have a CLD3 arm:
  `detectWindowLang` (behind `detectLanguageFull`) and `emitScriptSpan` inside
  `nativeGetLanguages`.
- Every Kotlin caller passes `useCld3Flag`: `detectLanguageFull`, `detectLanguageRuns` and
  the new `detectLanguageAggregate`. **`processDirect` voids the flag on purpose** —
  `buildMixChunks` segments and merges, it never detects; its chunks are detected back in
  Kotlin.
- Reliability is symmetric: CLD2 returns `"UNKNOWN"` when `!reliable`, the CLD3 arm returns
  `"UNKNOWN"` when `!is_reliable`.
- **Hints are mirrored.** CLD2 gets `CLDHints`; CLD3 has no hints API, so `cld3DetectRaw`
  asks `FindTopNMostFreqLangs(text, 3)` and keeps the first reliable candidate whose base
  tag is in the same hint list.
- **The romanised filter is complete.** CLD3's model emits exactly six `-Latn` tags —
  `zh ru bg hi el ja` — and `isRomanisedTag` lists exactly those six. Counted from
  `task_context_params.cc`'s `kLanguageNames`, 109 entries.
- **The whole CLD3 vocabulary maps.** All 109 tags were run through a Java copy of
  `IsoCodes.toIso3`, built the same way from `Locale.getISOLanguages()` plus the
  terminological and no-iso2 tables. **108 map; only `"und"` does not.**
- All five 5.7.7.26 additions are detector-agnostic (normaliser, danda, digit grouping,
  clock-time guard, `':'`) or pass the flag (the aggregate detector).

**THE GAP.** CLD2 says `"un"` for undetermined, CLD3 says **`"und"`**
(`NNetLanguageIdentifier::kUnknown[] = "und"`). Nothing normalised between them, so under
CLD3 `emitScriptSpan` wrote `"und"` into the span — and the new aggregate detector filters
unknown with `key.startsWith("un|")`, which **`"und|1"` does not match** (third character is
`d`). An undetermined span therefore counted as a real language and **could win the
aggregate** — exactly what `clsCLD2.f`'s two-candidate scan exists to prevent, and only on
the CLD3 side.

**Fixed at the boundary, one place.** `cld3DetectRaw` already returns `""` for a romanised
tag and both callers already treat `""` as CLD2's own unknown (`"UNKNOWN"` for
`detectWindowLang`, `"un"` for `emitScriptSpan`), so `"und"` returns `""` too. Keep any
future CLD3 tag folding there rather than at the call sites.

## The 5.7.7.18 → 5.7.7.26 sweep is COMPLETE (2026-08-21) — do not redo it
5.7.7.18 was re-decompiled with the **same** CFR flags as 5.7.7.26 (standard for the tree,
`--ignoreexceptionsalways` for the four classes the standard flags cannot do) and **every**
`.java` in `com/vnspeak/autotts` and `c3` was compared with identifiers, literals, block
labels and casts normalised away, so only real structure and real string literals survive.
Script: `tools/autotts/cmp_versions.py`.

**Result: 14 files differ at all, and only these five carry a behaviour change.**

| file | change | state |
|---|---|---|
| `c3/d0.java` | digit grouping, clock-time guard, `:` | ported |
| `c3/n.java` | `synchronized` accessors, group-size pref | ported |
| `c3/k.java` | Group size spinner, Information section, **Test try/catch** | ported |
| `clsCLD2.java` | normaliser `a()`/`b()`, aggregate `f()` | ported |
| `AutoTtsService` | **new `P()`**, `"Languge"` → `"Language"` at 7 of 9 sites | ported |

**Everything else is CFR noise and must not be re-investigated:** `c3/d`, `c3/e`, `c3/e0`,
`c3/g0`, `c3/l0`, `c3/p`, `a.java`, `CheckVoiceData`, `NewSettingsActivity` differ only in
how CFR named locals, declared them, or placed casts. `c3/e.java` was checked harder because
it holds the ISO tables — both versions carry the **same 40** `{"xx","yyy"}` pairs and the
same `put()` pairs, compared as sorted sets.

**The last two, ported here:**
1. **`AutoTtsService.P()`** — first thing in `onSynthesizeText`, after the opening log:
   `synchronized (c) { if (!w || c.isEmpty()) { log("languages missing at point of use -
   reloading"); e0(); } }`, where `w` is set at the end of `e0()`. Ours is
   `reloadLanguagesIfMissing()` with a `languagesLoaded` flag; it calls `pushLanguageSets()`
   because `e0()` ends with `s0()`. Before this we loaded once in `onCreate` and never looked
   again, so a service that started before the scan had written anything kept an empty list
   for its whole life.
2. **The Test button gained a try/catch.** 5.7.7.18 was `onClick { Y2(); }`; 5.7.7.26 is
   `try { Z2(); } catch (Exception) { Toast "Test unknown error" }`. `speakTest` reaches into
   a `TextToSpeech` that may already be dead, and the settings screen used to go down with it.

## The native detector was read AGAIN, instruction by instruction (2026-08-28)
The owner asked for a complete sweep of anything AutoTTS has that we do not, detection
first and in the finest detail. `getLanguageSpans` (**0x65392c**), its span emitter
(**0x653e90**), `setLanguageHints` (**0x653588**), `getLanguage` (**0x6523c8**) and both JNI
wrappers were disassembled and compared line by line. Most of it matched — the script
ladder, the 26-entry jump table and all nineteen fixed languages, the 1024-byte cap and its
continuation back-off, `kMaxSpans` = 128, the merge test, the cap-stretch, the clamp in the
wrapper, the CLDHints layout at both sites, and the 48 `{script, language}` pairs, which were
decoded from **0x62f924** and matched entry for entry against `kScriptLangPairs`.

**Five things did not, and all five are now fixed. They are written up in
`docs/INVARIANTS.md` #20 with the instruction listings; do NOT re-derive them.**

1. **Script 0 falls back to the LATIN language, not to `"un"`.** A run of digits,
   punctuation, spaces or emoji is named after the first enabled Latin language whenever any
   hint is set. This is the one that decides the voice of every bare number.
2. **The CLD2 answer filter tests FOUR candidates** — the summary, then `language3[0]`,
   `[1]`, `[2]`. Rank 0 is not a repeat of the summary: `CalcSummaryLang` returns a different
   language on its English and FIGS boilerplate rules and `UNKNOWN_LANGUAGE` when the top
   language covers under 26%.
3. **`setLanguageHints` skips a code outside 1..7 characters** rather than truncating it, and
   stops at 64. The CLD3 hint list is now built from the same accepted codes.
4. **An ASCII letter means Latin; anything else means "keep the current script."** Ours had
   the ternary inverted. Reachable through overlong UTF-8, which JNI produces for U+0000.
5. **`c3.n.f` is ONE `HashSet` for the life of the process.** `clear()` never shrinks the
   table, and that order decides which 64 codes survive the cap in 3.

**`N` and `O` are two DIFFERENT end-synthesis helpers, and the map only ever named `O`.**
`N(cb, n)` starts the callback if it has not started and finishes it if it has not finished —
no log, no stop flag — and is what `onSynthesizeText` calls (numbers 1, 2, 4, 5, 7, 9, 10, 12,
13). `O(cb, n)` logs `"endSynthesis #n"`, sets the stopped flag and notifies, and calls
`done()` **only if the callback has already started**; it is what the utterance listener
calls. `startAndFinish` is `N`; the listener sites inline `O`. Checked site by site.

**A literal-by-literal diff of every AutoTTS class against ours** (`AutoTtsService`,
`clsCLD2`, `c3.n`, `c3.d0`, `c3.k`, `c3.u`, `c3.v`, `c3.g0`, `c3.p`, `c3.d`, `c3.b0`,
`c3.f0`, `c3.a0`, `c3.w`, `c3.f`, `c3.m`) found the remaining gaps, all log-only, all now
matched: `s0`'s three lines, `onCreate`'s version name and code, `"Load voice original"`,
the `"<want> vs <have>"` locale line, `"Check voice 1"`, `"Keep-live activated"`, and the
`stopAllTts` / `onStop` orderings. **`"Current engine: "` names the wrapper about to speak**
(`f.get(d).e()`), not a resolved preferred package — resolving one meant a whole
`getEngine4Language` block in the log for every chunk that AutoTTS never emits.
`c3.f0`'s sample-text table was compared key by key: **184 iso3 entries, identical**. Its
other 156 entries are keyed by **iso2** and are unreachable — both callers pass an iso3 —
so they are deliberately not carried.

## The DETECTOR lives in `libcld2.so`, and it was read there (2026-08-25)
The user reported it from the device: **mix mode, non-Latin preferred language Hindi, and
numbers were being spoken in Hindi.** Correct report. The cause was not in any `.java` file —
`clsCLD2`'s three natives are the whole story, so `lib/arm64-v8a/libcld2.so` was disassembled.
**Three findings, all now ported. Do NOT re-derive them; re-read this section instead.**

**Extract and disassemble like this:**

    unzip -o AutoTTS_5.7.7.26.apk 'lib/arm64-v8a/*'
    llvm-objdump -T lib/arm64-v8a/libcld2.so | grep -i "getLanguage\|Java_"
    llvm-objdump -d --start-address=0x65392c --stop-address=0x653e90 lib/arm64-v8a/libcld2.so

The four functions that matter: `getLanguageSpans` **0x65392c**, its span emitter **0x653e90**,
`setLanguageHints` **0x653588**, `getLanguage` **0x6523c8**.

### 1. Script 0 is its own case: `"un"` and `latin = TRUE` — THE number-in-Hindi bug
`getLanguageSpans` dispatches on the script through a **26-entry jump table at 0x62f8f0**
(`cmp w3, #0x19 / b.hi` sends anything above 25 straight to CLD2). Decoding all 26 entries:

| script | target |
|---|---|
| 0 | 0x653ef0 — **its own case** |
| 1–6 | 0x653fbc — the CLD2 detect path |
| 7 | `"el"` |
| 8–25 | the eighteen fixed languages, **in our exact `SCRIPT_FIXED_LANG` order** |

and script 0's case is

    653ef0: mov  w20, #0x1        ; latin = TRUE
    653ef8: adrp x19, <"un">      ; language = "un"
    653f08: b.lt 0x6544c0         ; ONE PAST the cset below
    6544b8: cmp  w3, #0x1
    6544bc: cset w20, eq          ; latin = (script == 1)

so it never runs CLD2 **and never runs that cset**. `w20` really is the latin field — the
append compares it with `ldur w8, [x23, #-0x8]`, the fourth member of the 24-byte span struct
whose other three are offset, bytes and the language pointer.

A run made only of **digits, ASCII punctuation, spaces or emoji** classifies as script 0,
because the ASCII branch only sets a script for A–Z/a–z and the code-point classifier answers
"keep the current script" for everything else. The caller resolves such a span with
`run.b ? P : Q`, so **AutoTTS speaks a bare number, a bare punctuation run and a bare emoji
with the preferred LATIN language.** Ours detected it, got "un" anyway, and left latin false —
sending every one of them to the preferred NON-Latin language. That is every standalone number
a screen reader announces: a battery level, a list position, a time, a percentage.

Script 0 can only be the **single final span** of a text in which nothing was classified: the
two mid-text emits fire on `curScript >= 2` and on `curScript != 0 && stringClass != curScript`,
and neither can pass 0. Dual mode is unaffected (`d0.t`'s type picks the language, no detection).

### 2. `setLanguageHints` builds TWO per-script tables, and CLD2 is steered with them
`clsCLD2.i(n.f)` → `nativeSetLanguageHints` is all the Java side does with the list. The native
side stores the codes lowercased, at most 64, **and then derives**:

    653758: adr x25, 0x6984c0        ; scriptLanguageHint[40]
    653760: adr x26, 0x698560        ; scriptLanguageFallback[40]
    653778: stp q1, q1, [x25]        ; both filled with 26 = UNKNOWN_LANGUAGE
    6537f0: adr x8, 0x62f924         ; 48 {script, Language} pairs
    65386c: hint[script] = lang ; count[script]++ ;
            if (fallback[script] == 26) fallback[script] = lang
    653898: any slot whose count != 1 -> hint back to 26

So the per-script **language hint** survives only where **exactly one** enabled language uses
that script; the **fallback** is the first enabled language that does, in table order. The 48
pairs cover the six scripts CLD2 is asked about — Latin, Cyrillic, Arabic, Devanagari, CJK,
Bengali — and are transcribed verbatim into `kScriptLangPairs`.

`getLanguageSpans` then calls CLD2 with **no content-language hint at all**:

    65400c: stp xzr, xzr, [sp,#0x30]      ; content_language_hint = NULL, tld = NULL
    653fec: mov w10, #0x17                ; encoding_hint = 23 = UNKNOWN_ENCODING
    654030: ldr w9, [0x6984c0+script*4]   ; language_hint = the per-script one
    654070: bl ExtDetectLanguageSummary(..., true, &hints, 0x4000, ...)

and **filters** the answer: `lang3[0]`'s code if it is one of the hints (0x654098); else
`lang3[1]` when it is a real language and `percent3[1] >= 1` (0x6542ac); else `lang3[2]` on the
same terms (0x654380); else `scriptLanguageFallback[script]` when that is not UNKNOWN
(0x654430); else `lang3[0]`'s code after all (0x654498).

Ours had passed the comma list as `content_language_hint` and kept the top answer — a
different selection, and the two disagree exactly on short or mixed runs.

### 3. `getLanguage` (the auto/Google window detect) differs in three smaller ways

    6523f0: ldr d0, [x11, #0xf20]     ; the 8 bytes are {0, 26}
    6523f8: add x10, x10, #0x80a      ; tld_hint = "", not NULL
    652404: ldrb w20, [x8]            ; is_plain_text = CLD2::FLAGS_plain
    652408: stp xzr, x10, [sp,#0x28]  ; content_language_hint = NULL

no content hint, an **empty** tld hint, **encoding_hint 0** (`ISO_8859_1`, not
`UNKNOWN_ENCODING`), and `is_plain_text` from **`CLD2::FLAGS_plain`** — a flag defined only in
CLD2's own test file, that nothing in the app ever sets, so **false**. It keeps `lang3[0]` when
the result is reliable and answers `"UNKNOWN"` otherwise, which we already did.

### 4. CLD3 must be level with CLD2 SITE BY SITE, not globally (2026-08-25)
The rule for the CLD3 row is "wherever CLD2 makes a detection, the switch must be able to put
CLD3 there instead". Once CLD2's real hinting was in, that had to be rebalanced **in both
directions**, because the two native sites are not the same:

| site | CLD2 | CLD3 |
|---|---|---|
| `getLanguageSpans` | per-script language hint, then filter the answer against the enabled list, then the per-script fallback | top-3 filtered by the same list, **then the same per-script fallback** |
| `getLanguage` | **no hints and no filter** — `lang3[0]` when reliable, else UNKNOWN; `clsCLD2.d` does the `n.n()` test and the script-family fallback | `FindLanguage()`, kept when reliable, else UNKNOWN — **no filter** |

So `cld3DetectRaw` takes a **`useHints`** flag: `true` at the span site, `false` at the window
site. Filtering at the window site took the decision away from `clsCLD2.d` and made the two
detectors answer differently for the same text; missing the per-script fallback at the span
site made CLD3 return a language the user has not enabled, which the engine check then sent to
`P`/`Q`. Both are voice changes, not cosmetics. **Do not "simplify" the flag away.**

**The third thing that table implies, and which was MISSING until 2026-08-27: reliability.**
Both arms of `detectWindowLang` answer `"UNKNOWN"` when the detector is unsure. The span site
passed **`nullptr`** for `cld3DetectRaw`'s `reliableOut` and used the answer regardless. Found
from a device log: with CLD3 on, the Latin name `"MEET Choudhary "` was spoken by the **Hindi**
voice, while CLD2 read the same utterance in English — identical chunking, only span 0's
language differed. CLD3 answers `hi` there with `is_reliable = 0`, `p = 0.495`; its own top-3
loop rejects that candidate for exactly that reason, and then the fallthrough to
`FindLanguage()` returned it anyway. Hindi being enabled, `isHinted` accepted it and the
per-script fallback never ran. The span site now asks for the flag and treats unreliable as
`"un"`, so the fallback resolves the span to the language its script implies. Proof and
negative test: **`tools/verify/cld3span/run.sh`**, which links the real core against CLD2 and
CLD3 and starts a JVM for a genuine `JNIEnv`. Recorded as INVARIANTS #16.

**The fourth thing, and it is now DONE too (2026-08-27): the answer must belong to the span's
own script.** CLD2 gets that free — its per-script hint goes **into** the detector — while the
CLD3 arm can only reject afterwards, against a flat enabled list that knows nothing about
script. So a reliable `sr` or `ja` won a Latin span merely because Serbian or Japanese was
enabled. `scriptOfLanguageCode` looks the answer up in the same 48 `kScriptLangPairs` and the
span site rejects it only when the table places it under a **different** script; **`-1` means
"not listed" and is ACCEPTED**, because those 48 pairs are not a full classification (Latin
lists 24 languages and no Catalan; CJK has no Korean at all), so "absent" must never mean
"wrong".

Measured over 536 Latin strings from the reported log plus the app's own literals, first with
the reliability fix alone and then with both: `{eng,guj,hin}` 0 → **0**, `{en,ru,uk,bg}` 1 → **0**,
`{en,ja}` 4 → **0**, `{en,zh,ja,ko}` 4 → **0**, `{en,sr}` 19 → **0**, `{en,ja,sr,ru,zh}` 23 → **0**.
The other direction was checked as well — 18 non-Latin lines across seven enabled sets, 0
disagreements — so rejecting wrong-script answers did not start rejecting right ones. The
reporter's own set was already 0 because CLD3 never reliably answers bare `hi`/`gu` for Latin
text (it answers `hi-Latn`, which `isRomanisedTag` drops); it was closed anyway because the
enabled-language list is something the user changes. Both halves are covered by
`tools/verify/cld3span/run.sh` and INVARIANTS #16, each negative-tested.

**Verified equal in the same read, so do NOT re-audit:** the ASCII branch
(`and w8, w9, #0x5f`, `sub #0x41`, `cmp #0x19`, `b.hi` — non-letters change nothing);
`latin = (script == 1)` for every script other than 0; the 1024-byte detect cap and its
continuation-byte back-off; `kMaxSpans` = **128** (`mov w3, #0x80` in `nativeGetLanguages`);
the merge-with-previous test (contiguous **and** same latin **and** same language) and the
cap-stretch; and `SCRIPT_FIXED_LANG`, which matches the jump table's scripts 7–25 exactly.
The `"XXKNOWN"` early return in `nativeGetLanguages` is the licence gate — carve-out, not ported.

## EVERY language-list rebuild must push the sets (2026-08-25)
AutoTTS keeps one invariant with no exception: wherever the list is rebuilt, `s0()` runs in
the **same** block.

    synchronized (c3.n.c) { c.clear(); c.addAll(c3.n.g(...)); AutoTtsService.s0(); }

All eleven sites: `c3/k.java` **252, 300, 346, 370, 410, 1299, 1482, 1844, 1853**,
`AutoTtsService.e0()` at **898**, `NewSettingsActivity` at **483**. `s0()` recomputes `c3.n.f`
from the **live** list — every entry that is not disabled and whose engine is neither empty nor
`"disable"`, mapped through `c3.e.b` — and hands it to `clsCLD2.i` → `nativeSetLanguageHints`,
which is what builds the two per-script tables the native detector steers by. **A rebuild that
does not push leaves the detector hinting at the previous list.**

Two of ours did not, and both are now fixed:
- **`EngineFinder.finalizeScan`** — the startup scan, whose counterpart is
  `NewSettingsActivity:483`. Before this, the detector kept whatever the list held *before* the
  scan; on a first run, nothing at all.
- **`LanguagesActivity`** — rebuilds with `onlyEnabled = false` on open, exactly what
  `c3/k.java:1299` does before its `s0()`.

The other two (`ModesScreen.rebuildLanguagesFor`, `LanguagesVoicesViews.refreshModeLanguages`)
already did. **Any new rebuild site must call `EasyVoiceTtsService.pushLanguageSets()`** — the
full `s0`, not `pushDetectSetsOnly`, which stays the per-utterance call.

A mere **toggle** does not push: AutoTTS's select-all / clear-all / row tap change `f.i` and
call `n.y()` (persist) only, with no `s0()`. Ours matches; do not "fix" that either.

**`c3.n.g` itself was read against `rebuildFromScan` in the same pass and matches statement for
statement** — the mode-3 Google package filter, dedup by **display name**, the `_volume`/
`_pitch`/`_speed` defaults of 100 and `_variant` of `"*Default"`, the `iso3` → `engine#locale`
split on `#` needing at least 2 parts, the required-language re-enable and its conditional
`n.y()`, the `onlyEnabled` gate, the repeat-name branch adding the package to the entry with
the matching **iso3**, and the sort by `Collator` over the NFD-normalised, `\p{M}`-stripped
display name. The one deliberate difference is the `_disabled` default (ours `true`, theirs
`false`), which is the 2026-08-19 user request.

## The script-family fallback is PROVEN equal to `a.java` (2026-08-25)
`a.e(cp, n.f)` is what `clsCLD2.d` falls back to when the detector's answer is not an enabled
language, so it decides the voice for auto and Google mode more often than the detector does.
It was checked by **measurement**, like the segmenter: `com/vnspeak/autotts/a.java` was lifted
into `tools/verify/scriptfamily/java/ScriptFam.java` and swept against our `familyLangForCpFiltered` /
`familyForCp` over **all 1,114,112 code points**, for **45 enabled-language sets** — one per
script family, the full Latin set, fifteen hand-picked mixtures and thirty random realistic
ones. Both the resolved language and the **unfiltered family, primary plus members in
iteration order**, come out identical everywhere.

    tools/verify/scriptfamily/run.sh          # both sweeps, one command

**THE HARNESS TRAP, and it invalidated the first run: `HashSet` iterates differently on
Android than on the JDK the harness runs.** Android's libcore keeps the classic
OpenJDK 8–17 constructor

    public HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int)(c.size()/.75f) + 1, 16));
        addAll(c); }

while **JDK 19 replaced it** with `HashMap.newHashMap(c.size())`, i.e. `ceil(size/0.75)`. For a
**12-element** family that is capacity 17 → table **32** on Android but 16 on JDK 19+, and the
two orders differ:

    Devanagari, table 32 (Android):  hi,new,mr,kok,bh,bho,awa,sa,mai,ne,raj,hne
    Devanagari, table 16 (JDK 21):   hi,new,mai,mr,kok,bh,ne,bho,awa,raj,sa,hne

Only the two 12-member families — Devanagari and the digit family — are affected, and the
order is exactly what `a.f` and `clsCLD2.d` walk to pick a language, so it is not cosmetic.
**Our C++ was right and the harness was wrong**: `ScriptFam.java` now routes every
`new HashSet<>(Arrays.asList(...))` through an `androidSet()` helper that restores the Android
form. **Any future Java harness that compares collection ORDER must do the same.**

**Verified equal in the same sweep, so do NOT re-derive:** `a.b`/`a.d`'s code-point ranges and
their order; every family in `a.a()` — primary, members and script name; `a.c`'s eighteen
`bl…` script-presence flags and the order it tests them in; `a.f`'s "return unchanged when the
primary and every member are enabled, otherwise keep what is enabled, and take the original
primary if it survived or else the first kept member in HashSet order"; and `a.e`'s
`set == null || set.isEmpty() ? d(cp) : c(cp, set)` gate, which our
`enabled.empty() ? familyLangForCp(...) : familyLangForCpFiltered(...)` mirrors.

**One difference exists and is unreachable.** For the punctuation, symbol, maths and emoji
ranges AutoTTS returns the `"zz"` family (`t`, `w.get("emoji")`, `w.get("math")`) and lets the
caller filter it, while ours answers `""` directly. They differ only if `"zz"` is itself an
enabled language — and `n.f` is built from `IsoCodes.toIso2(entry.iso3)` over scanned
languages, so it can never contain `"zz"`; `n.n("zz")` is false as well. Every one of the 45
realistic sets agrees; only a hand-made `{"xx","zz"}` set separates them.

**`c3.e` (the ISO map) was read line for line against `IsoCodes.kt` in the same pass** — same
two tables, same `Locale.getISOLanguages()` seeding, same order of the five fix-up passes
(iso2→terminological, bibliographic→iso2, the heb/ind/yid puts, the Chinese variants, the
no-iso2 codes), and `e.a`/`e.b`/`e.c` match `normalizeTag`/`toIso2`/`toIso3` statement for
statement.

## The segmenter is PROVEN equal to `d0.t` — 163,296 cases (2026-08-25)
The reading flow was checked by **measurement**, not by reading. `c3/d0.java`'s `t()` was
lifted out of the 5.7.7.26 decompile into a standalone Java program in
`tools/verify/segmenter/java/` — the whole class, with only `n.e`, the `AutoTtsService` statics and
`clsCLD2.a/b` stubbed — and the same inputs were run through it and through our
`buildMixChunks` (`tools/verify/segmenter/cpp/`), chunk list against chunk list.

**Rebuild it like this** (both harnesses read the same TSV on stdin and print
`type:'text' | type:'text'`, so `diff` is the whole test):

    tools/verify/segmenter/run.sh             # generates the cases, builds both, diffs
    TSV columns: mode numMode puncMode emojiMode inFlow smart group dualLang mixNonLat deviceIso3 hints text

CFR's output does **not** compile; five methods had to be re-transcribed by hand because CFR
reuses one local for two types (`a`, `e`, `f`, `g`, `r`) and `t()`/`clsCLD2.b` need their
declarations widened to `Object`. Every departure is listed in `tools/verify/segmenter/java/README.md`.

**The battery**: 189 texts × 16 number/punctuation/emoji mode triples × 6 flag sets
(punctuation in flow on and off, smart numbers off and on at group 1/2/3) × dual, mixed and
multilingual × 3 device/dual/non-Latin configurations × 7 hint lists. Texts cover Latin,
Devanagari, Arabic, Chinese and Cyrillic; ASCII, Devanagari, Arabic-Indic and full-width
digits; phone shapes, clock times, decimals, thousands separators, currency; emoji including
ZWJ sequences, skin tones, keycaps and flag pairs; the danda; bidi controls; maths-bold,
script, circled, superscript and full-width letters; every Java whitespace class from U+001C
to U+3000 in leading, medial and trailing position; and smart-number keywords in English,
Hindi, Urdu, Persian, Russian, Chinese, Japanese, Korean and Thai.

**So the whole of `d0` is now verified, not argued**: `b`/`c`/`d` (the punctuation, emoji and
number splits), `e` + `p` (keyword matching and the CJK/Thai boundary exemption), `f`/`g` (the
24- and 48-character context windows), `h`/`i`/`k` (neighbour typing), `j` (segment typing),
`l`/`m`/`n`/`o` (the four predicates), `q` (bidi strip), `r` (phone shape), `s` (digit
grouping) and `t` itself — plus `clsCLD2.b`'s normaliser and both merge passes.

**ONE input disagreed, and it was a real bug.** `d0.k`'s two scans return the first type that
is not 3, 4 or 5 — and **type 0, all whitespace, IS such a type**. `k()` reads that 0 as
"nothing found", falls through to the forward scan, meets the same 0 and answers 0 again, so
the segment takes the **neutral** type. Ours treated 0 as "keep looking" and walked past it.

    " 123 Hello", dual mode, device language == the dual language
        AutoTTS   0:' ' | 2:'123 ' | 1:'Hello'
        ours      0:' ' | 1:'123 Hello'

Type 0 survives the first merge **only at index 0**, because every later whitespace segment is
absorbed by the run before it. Index 0 can be whitespace because `trim()` strips nothing above
U+0020: a leading **U+00A0, U+2007, U+202F or U+FEFF** is still there when the collapse turns
it into a space. `surroundingType` is now a one-for-one port of `d0.i` / `d0.h` / `d0.k`.

**`n7` is NOT the licence argument — it is the neutral type.** `t(text, n3, n4, n5, n6, n7)`
tests `n6 == -1 || n7 == -1` and then **overwrites both**: `n6 = AutoTtsService.T` and
`n7 = 2`, dropped to 1 unless (mode 1 and device iso3 == `I`, the dual language) or (mode 4/5
and device iso3 == `Q`, the mix non-Latin language). Anywhere below that line, `n7` means
1 or 2. An earlier note here read it as `j0` = 0; that was wrong.

**Three more differences came out of reading the callers beside it, all fixed:**
- `detectLanguageAggregate` totals into a plain `HashMap`, as `clsCLD2.f` does. Both
  best-so-far scans replace only on a strict `>`, so an exact tie is settled by whichever
  entry `entrySet()` yields first — HashMap's bucket order, not insertion order. Kotlin's
  `HashMap` **is** `java.util.HashMap`, so the same keys inserted in the same order now
  iterate in AutoTTS's sequence. Do not "improve" this to a `LinkedHashMap`.
- the mix and multilingual branches no longer skip a detected run with empty text; AutoTTS
  adds every element `clsCLD2.e` returns, and the skip was unreachable anyway.
- **a `U+001E` in the text silently lost the rest of the utterance.** `processDirect` packs
  the chunks as `type \x1F kind \x1F lang \x1F text` with records joined by `\x1E`.
  U+001C–U+001F are `Character.isWhitespace`, but AutoTTS's collapse is `replaceAll("\\s+",
  " ")` and Java's `\s` is only `[ \t\n\x0B\f\r]` — so a U+001E survives the collapse there
  and here alike, and then split one record in two, leaving a half with three fields that the
  Kotlin dropped. `packField` / `decodeChunkText` escape the three characters now.

**Verified equal in the same pass, so do NOT re-audit:** the mix and multilingual span gate;
the type 3/4/5 → specific-language mapping; `languageForDetectedRun` against `c3.e.c` →
`run.b ? P : Q` → engine check → `P`/`Q`; the auto/Google chain `clsCLD2.d` →
`clsCLD2.f` → `c3.e.c` → `H`; dual's type table (1→`eng`, 2→`I`, 3→`K`, 4→`M`, 5→`O`, and
**type 0 → "eng" with no language load at all**, first chunk and next chunk alike);
`splitByLocaleSpans` against `e0.g` down to `getSpans(0, len - 1)` and the `end + 1` step;
and the mix first-chunk preflight, whose re-detect and two type fallbacks are dead because
every chunk there carries a language and a type of −1.

**`parts[1]` (the `kind` field) is dead payload** since `languageForSegmentKind` was removed.
The Kotlin branches on `parts[0]`, the type `d0.t` resolved. Left in the wire format on
purpose; do not start reading it again.

**The smart-number keyword set is cached FOR THE LIFE OF THE PROCESS, deliberately.**
`d0.j` is a `static volatile HashSet` assigned in exactly two places — `null` in the static
initialiser and the built set in `a()` — and **nothing in the whole app ever clears it**. So
AutoTTS builds the keyword list from whatever `c3.n.f` held at the first utterance with smart
numbers on, and keeps it. Our `smartNumberCacheValid` does the same. Changing the enabled
languages afterwards does not change the keyword list, in AutoTTS or here; that is not a bug
to fix. (There used to be a `smartNumberCachedHints` beside it, written once and never read —
a leftover from a design where a hint change invalidated the cache. Neither AutoTTS nor we
invalidate, so it was doing nothing; deleted 2026-08-26.)

## Per-mode DETECTION audited against 5.7.7.26 (2026-08-21)
`onSynthesizeText` was decompiled from **both** 5.7.7.18 and 5.7.7.26 with identical CFR
flags (`--ignoreexceptionsalways`) and compared after normalising the static letter shift.
The call profile is the same in both:

    c3.e.c(  4→4     clsCLD2.d(  2→2     d0.t(  3→3     e0.g(  3→3     onLoadLanguage(  9→9
    clsCLD2.f(  0→1   ← the only added call, already ported

so **the per-mode detection shape did not change in 5.7.7.26.** Only the log typo did:
`"Languge is not supported"` → `"Language is not supported"` — but only at **seven of
its nine sites**. The **auto/Google** branch (noexc line 1677) and the **mix** branch (1962)
still carry the typo in 5.7.7.26; the five dual sites, multilingual and the final path are
spelled correctly. Ours matches site by site. An earlier note here said the typo was simply
fixed, and a pass that "corrects" both remaining sites is a regression.

**How `d0.t` really works — this is the part that was misunderstood.** It takes the three
mode ints and **resolves them itself**, re-typing each number/punctuation/emoji segment:

| mode int | what `d0.t` does to the segment type |
|---|---|
| 3 "Specific language" | leaves it 3 / 4 / 5 |
| 0 "Auto language" | `d0.k(types, i, 0)` — nearest non-3/4/5 neighbour, **backwards first, then forwards**, else 0 |
| 1 "Primary" / 2 "Secondary" | sets the type to 1 or 2 |

Segments are then **merged by type**, and that merge is the whole point of modes 1 and 2:
the number joins the Latin run or the non-Latin run and is detected *together with it*.
The caller branches on the resolved type alone — `3 → K`, `4 → M`, `5 → O`, and **0, 1 and
2 all go to `clsCLD2.e`**.

**The gap that was fixed.** `buildMixChunks` already mirrors all of that, `surroundingType`
already is `d0.k`, and the chunk already carries the resolved language. But the Kotlin
branched on `kind` — which the C++ **re-derives** from the merged text with
`wholeSegmentKind` — and then applied the mode ints a *second* time in
`languageForSegmentKind`. `parts[0]` (resolved type) and `parts[2]` (resolved language)
were parsed and never read.

Applying the mode int twice is not a no-op. A lone number with `numberMode == 1`: AutoTTS
re-types it to 1 and detects it, and a pure-digit run has no cased letters so
`getLanguageSpans` leaves `curScript` at 0 and the span returns **`latin = false`**, so
`run.b ? P : Q` picks **Q**. Ours skipped detection and mapped mode 1 to the **Latin**
fallback **P**. Different voice, same text.

Mix and multilingual now branch on `parts[0]` and use `parts[2]`, exactly as AutoTTS does.
`languageForSegmentKind` is gone — it had no other callers.

**Verified already correct, do NOT re-audit:**
- **dual** already read `parts[0]`/`parts[2]` and never detects — which matches: AutoTTS's
  dual branch contains no `clsCLD2.e` at all, only a first-segment preflight
  (`1 → "eng"`, `2 → I`, `3 → K`, `4 → M`, `5 → O`).
- **mix vs multilingual differ on the locale-span gate, and ours matches both.** Mix: if the
  span's own language has no engine it falls back to **`H` (auto language)** and is still
  spoken as one span. Multilingual: an unknown/empty/engine-less language sends the span to
  **detection instead**, and the span's language is never overwritten.
- the detected-run fallback is `c3.e.c(run.a)`, then `run.b ? P : Q` when that is null, then
  `run.b ? P : Q` **again** if the engine is empty or `"Disable"` — ours is
  `languageForDetectedRun`, same three steps.

## The Modes tab, audited A to Z against 5.7.7.26 (2026-08-21)
`fragment_modes.xml` is **byte-identical** between 5.7.7.18 and 5.7.7.26, so the tab
itself did not change in the new release; this was a full parity audit, not a port.
Chain read: the layout, `c3/k.java`'s `onRadioButtonClicked`, `X2()`, `O1()`, `c3/w.a`,
`AutoTtsService.s0`/`e0`, and every id resolved through `public.xml`.

**THE VISIBILITY TABLE — write it down, it is not symmetric:**

| mode | Auto | Dual | Mixed | Multi | Reading | Common (locale spans) |
|---|---|---|---|---|---|---|
| none (0) | gone | gone | gone | gone | gone | gone |
| dual (1) | gone | **VIS** | gone | gone | **VIS** | **VIS** |
| auto (2) | **VIS** | gone | gone | gone | **GONE** | **VIS** |
| google (3) | **VIS** | gone | gone | gone | *never assigned* | *never assigned* |
| mix (4) | gone | gone | **VIS** | gone | **VIS** | **VIS** |
| multilingual (5) | gone | gone | gone | **VIS** | **VIS** | **VIS** |

**Auto hides the number/punctuation/emoji section** — that is deliberate in AutoTTS and
we already had it right. **Google assigns neither**, so on a freshly built tab both keep
the layout's `gone`; a per-mode screen like ours is always a fresh open, so that is the
behaviour to match.

**Verified identical, do NOT re-audit:** every spinner writes the right static
(`r0`→`H`, `s0`→`I`, `w0`/`y0`→**`P`**, `x0`/`z0`→**`Q`** — mix and multilingual really do
share one pair of statics, `t0`/`u0`/`v0`→`J`/`L`/`N`, `A0`/`B0`/`C0`→`K`/`M`/`O`); the
specific-language spinner shows only at mode 3; the four mode-int options are exactly
"Auto language" / "Primary language" / "Secondary language" / "Specific language"; the
language adapter is `n.l(pkg)`, which filters by engine package and **not** by disabled,
which is what `languageLabelsFor` does; `loadMode`'s `auto_mode` default of 3 falling
back to 0 when Google TTS is absent is byte-for-byte `c3.n.o`; and `setReadingMode`
writes the **static**, as `AutoTtsService.T = n` does.

**The Google radio is `android:visibility="gone"` in the layout and `setVisibility` is
never called on it anywhere** — AutoTTS never shows it either, only `setEnabled`. So our
skipping that row was already correct.

**Three gaps found and fixed:**
1. **`s0()` was not running on a UI rebuild.** AutoTTS calls `AutoTtsService.s0()` from
   **eleven** places — every radio, `X2`, `Y2`, and the service's `e0` — and `s0` pushes
   **both** the enabled set and `clsCLD2.i(n.f)` = `setLanguageHints`. Ours pushed hints
   only in `onCreate`; `refreshEnabledLangs` recomputed per utterance but called
   `setDetectSets` alone, so after a mode switch the native detector still held the hints
   from process start. Now `EasyVoiceTtsService.pushLanguageSets()` is the single
   companion implementation of `s0`, sends both, and is called from every rebuild site.
2. **Google mode showed the locale-spans switch.** `CommonSettings` is gone for mode 3.
3. **Google mode's settings were unreachable.** We skip the Google row, so no Settings
   button was drawn beside it — and an imported `auto_mode = 3` left the preferred-language
   spinner with no way in, while AutoTTS still shows it (its `AutoModeSettings` IS visible
   for mode 3). This was first answered by drawing the row while google was the mode in
   force. **That is REVERTED (2026-08-27, owner request): the row is never drawn.** The
   setting is not stranded, because `shownMode()` resolves a stored `"google"` to `"auto"` on
   open and writes it back, so google simply stops being the mode instead of becoming an
   unreachable one. See the UI-departures list, item 4.

**One AutoTTS bug copied on purpose:** in google mode the adapter is
`n.l("com.google.android.tts")` (filtered) while the selection index is `n.f(H)` — an
index into the **unfiltered** list — and `O1` writes back `c.get(position).b` the same
way. Ours has the identical off-by-filter. Do not "fix" it alone.

## 5.7.7.26 detection changes — ALL FOUR PORTED (2026-08-21)
The remaining items from `v5.7.7.26/ANALYSIS_5.7.7.26_delta.md`. Chain read in CFR
first: `clsCLD2.a/b/e/f`, `AutoTtsService.onSynthesizeText`'s auto branch (noexc
tree), `c3/d0.t`, `c3/n`'s accessors, plus the arm64 of `getLanguageSpans`.

1. **The Unicode normaliser is EXACT, and that was proved, not eyeballed.**
   `clsCLD2.a(int)` was extracted from the CFR source, compiled with `javac`, swept
   over all **1,114,112** code points, and the **1,062** mappings it produces were
   diffed against the same sweep of our `normalizeFancyCodepoint`. Identical — and
   re-checked a second time after the function was spliced into the real file.
   The transcription is mechanical; the only edit was adding `break;` to nine
   trailing `case N:` labels, which Java allows to be empty and C++ does not.
   **If that function ever needs touching, redo that sweep — do not hand-check it.**
   `isFancyTrigger` is `clsCLD2.b`'s scan moved from UTF-16 chars to code points:
   `n3 == 55349` and `n3 == 55356` are the lead surrogates `0xD835`/`0xD83C`, so
   they become U+1D400–U+1D7FF and U+1F000–U+1F3FF. Trail surrogates
   (0xDC00–0xDFFF) match none of the other ranges, so the two forms agree.
   Applied in the three places AutoTTS applies it: `buildMixChunks` between the
   whitespace collapse and the bidi strip (= `d0.q(clsCLD2.b(...))`), and in
   `detectLanguageRuns` / `detectLanguageAggregate` on the Kotlin side.
   **Order matters in `clsCLD2.e`: fold FIRST, then the quick-character length
   test** — a lone maths-bold letter is two UTF-16 units, so the raw text would
   never satisfy `length == 1`. Ours used to test the raw text; fixed.

2. **`detectLanguageAggregate` = `clsCLD2.f`**, in Kotlin because it is Java in
   AutoTTS too — a plain aggregation over the existing `nativeGetLanguages`. Sums
   UTF-16 text length per `lang|isLatin` key and takes the biggest, tracking **two**
   candidates so a real language beats `un` even when `un` covers more text.
   Short-circuits, in order: empty, quick-character on the folded string, fewer
   than 3 entries, exactly one triple.
   **Used in auto/Google mode only**, as the third step: span language →
   `detectLanguage` → `detectLanguageAggregate`. The `"Cld2: "` log prints the
   final answer, as AutoTTS's does.

3. **The Devanagari danda is neutral.** `classifyScript` gained
   `if ((codePoint & 0x1FFFFE) == 0x964) return -1;` after the Arabic tests and
   before the Indic block lookup. Read off the arm64, not guessed: the inserted
   `and w10, w8, #0x1ffffe / cmp w10, #0x964 / b.eq 0x653b88` branches to
   **`mov w27, w3`** — the same target `cmp w8, #0xc0 / b.lo` uses, i.e. "take the
   current script". The mask makes one compare cover U+0964 and U+0965 (verified:
   those are the only two code points that match).

4. **`LangStore`'s accessors are `synchronized(languages)`** — `c3.n` guards eleven
   methods (`f g h i j k l n w x y`), all reads and persists. AutoTTS does **not**
   guard the writes, which live outside `c3.n`; that asymmetry is copied, not
   corrected. `engineFor` already had it. Monitors are reentrant, so nesting is free.

Verified end to end: 𝐇𝐞𝐥𝐥𝐨→Hello, 𝓗𝓲→Hi, Ｔｅｓｔ→Test, ℂℕℤ→CNZ, ①②ⓐ→12a, ¹²³→123,
plain ASCII and Hindi returned untouched by the fast path.

## The Advanced tab is now AutoTTS 5.7.7.26, complete (2026-08-21)
Asked for directly: the whole Advanced tab behaviour of the new AutoTTS, exactly.
Chain read in CFR before any edit, per rule 8: `fragment_advanced.xml`, `c3/k.java`'s
`U2()` (the whole tab build), `O1` (spinner select), `P2`/`R2`/`S2`/`N2`/`G2`,
`c3/n.java` load+persist, `c3/g0.java` (export), `c3/p.java` (logger), `c3/d0.java`
(`m`/`r`/`s`), and every id resolved through `public.xml` rather than guessed.

**What was missing and is now in:**
- **Group size.** `smart_number_reading_group_size`, int, default 1, static
  `EasyVoiceTtsService.smartNumberGroupSize` (= `AutoTtsService.f0`). Dropdown with
  exactly `1`/`2`/`3`, selection is `f0 - 1`, pick writes `position + 1`, and it is
  **disabled while Smart number reading is off** — `c3.k` does that twice, at build
  (`D0.setEnabled(e0)`) and from the switch's own listener. `LabeledDropdown` gained
  an `enabled` parameter for it. Persisted in `persistAll`, not eagerly, because
  `O1` only writes the static.
- **`respaceDigits` groups.** Was a space between every pair of adjacent digits;
  now `if (run > 0 && run % size == 0) space; run++` with `else run = 0`, mirroring
  `d0.s()`. **At size 1 the output is byte-identical to before**, which is why the
  default is 1. Verified with a standalone harness, not by eye.
- **A clock time is never spelled out.** `looksLikeClockTime` is `d0.b` used with
  `matches()`: `[0-9]{1,2}:[0-9]{2}(:[0-9]{2})?`, checked immediately after the empty
  test at the top of `isPhoneShaped` (= `d0.r`). Written as a scan; the leading run is
  **counted, not capped at 2**, because the regex backtracks — `123:45` must be
  rejected, and capping would accept it.
- **`:` rejects in `hasEnoughDigits`** (= `d0.m`), joining `,` `.` `%` `$`.
- **Information section** — "Build number:" and "Version:" at the bottom of the tab,
  the same two facts `c3.k:1187-1188` shows. AutoTTS hard-codes its own literals; ours
  come from `PackageManager` (`longVersionCode` on API 28+, else the deprecated
  `versionCode`). **`versionCode` is now `EV_BUILD_NUMBER`**, set from
  `github.run_number` on the Gradle step, because a frozen `versionCode = 1` would make
  "Build number" say the same thing forever.

**Verified already correct, so do not re-audit:** every one of the eight switches maps
to the right static; `show_notification` requests POST_NOTIFICATIONS when switched on
(`S2()`); punctuation-in-flow is disabled exactly when `punctuationModeInt == 3`
(`c3.k:833-843` sets `E0.setEnabled` from the punctuation spinner); TTS Settings,
battery, import, export, logging, share and clear logs all match; and every visible
label is byte-for-byte AutoTTS's string — "Text-to-Speech Settings", "TTS Settings",
"Advanced Synthesis Options", "Keep-alive Mode", "Keep alive", "Persistence Options",
"Language Detection Options", "Import/Export Configuration", "Import", "Export",
"Logging", "Enable logging", "Share logs", "Clear logs", "Smart number reading",
"Group size", "Information", "Version:", "Build number:".

**One AutoTTS bug deliberately NOT copied:** `fragment_advanced.xml` puts
`quick_character_reading_description` under the `punctuation_with_sentence` checkbox,
in 5.7.7.18 and 5.7.7.26 alike. Ours shows the punctuation description. Our prose is
our own anyway under the 2026-08-20 rewrite.

**Still NOT ported, and out of scope for this task** (they are detection, not the
Advanced tab): `clsCLD2.a/b`'s fancy-Unicode normaliser, the new `clsCLD2.f`
most-text-wins fallback detector, `c3.n`'s `synchronized` list accessors, and the
native Devanagari-danda span break. All four are written up in
`autotts_reference/v5.7.7.26/ANALYSIS_5.7.7.26_delta.md`.

**~~`blocks.py` gained `replace_raw_block`.~~ Obsolete with the generator.** The C++ lives in a `r"""..."""` block, which
`find_block` cannot see — it only understands the escaped one-line-per-source-line
style. The new helper re-adds the 14-space YAML block indent on write and was
round-trip tested (replace the block with the file generated from it, assert the YAML
is byte-identical) before being used.

## AutoTTS 5.7.7.26 also decompiled and kept (2026-08-21)
A newer AutoTTS release was supplied and lives in **`autotts_reference/v5.7.7.26/`**,
beside the 5.7.7.18 tree rather than replacing it, because our code is verified against
5.7.7.18 and every path below points at it. Read
**`autotts_reference/v5.7.7.26/ANALYSIS_5.7.7.26_delta.md`** — it is the full 5.7.7.18 →
5.7.7.26 diff with the code quoted, and it carries the new name map.

Three things from it that matter before touching anything:
- **The `AutoTtsService` static letters shifted by one AGAIN** (`G`→`H` … `d0`→`e0`,
  `e0`→`g0`), and there is a genuinely new one, **`f0` = smart number group size**. The
  `c3.*` class names did NOT move this time (`c3.n`, `c3.k`, `c3.d0` are still those).
- **`clsCLD2`'s methods were renamed and two were inserted at the front** — `a(int)` and
  `b(String)` are the new Unicode normaliser, and the old `a`/`b`/`c`/`d`/`e`/`f` are now
  `c`/`d`/`e`/`g`/`h`/`i`. A new `f(...)` is the aggregate detector.
- **Rule 7's CFR fallback does not work on 5.7.7.26.** `--forcetopsortnopull false --aexagg
  true` still fails on `onSynthesizeText` there; the only flag that decompiles it is
  **`--ignoreexceptionsalways true`**, which is why `v5.7.7.26/decompiled_java_noexc/`
  exists. That tree **discards exception blocks** — read logic from it, never quote it as
  evidence about what AutoTTS catches. Full flag matrix in `v5.7.7.26/README.md`.

The five real behaviour changes are listed in the analysis: digit grouping with a new
`smart_number_reading_group_size` pref, a clock-time guard in the phone-number test, `:`
added to the numeric character set, Unicode fancy-letter normalisation before detection,
and a new most-text-wins fallback detector in auto/Google mode. The native `.so` changed in
exactly one place (Devanagari danda ends a script span); `.rodata` is byte-identical.

## AutoTTS Reference Files (updated to **5.7.7.18**, 2026-08-07)
**DURABLE (committed, never delete)**: `autotts_reference/` in repo root —
`decompiled_java/` (full CFR decompile, 1656 .java), `decompiled_res/` (full apktool:
strings/colors/styles/dimens/…, all layouts, AndroidManifest.xml), `AutoTTS_5.7.7.18.apk`
(ground truth, versionCode 90000245), `README.md` (exact dex2jar+CFR+apktool regen commands).
The old 5.7.7.10 reference was fully removed. This survives container/scratchpad resets.

### ⚠️ 5.7.7.18 obfuscated-name map (names CHANGED from 5.7.7.10)
Almost every `c3.*` class shifted by **one letter**. The *pref keys* are unchanged, and
**five new ones** were added (`number_specific_language`, `punc_specific_language`,
`emoji_specific_language`, `punctuation_with_sentence`, `smart_number_reading`).
`com/vnspeak/autotts/a.java` is byte-identical to 5.7.7.10.

| Concept | 5.7.7.10 | **5.7.7.18** |
|---|---|---|
| Settings store class | `c3.m` | **`c3.n`** |
| Settings fragment (all tabs) | `c3.j` | **`c3.k`** |
| Number/punct/emoji segmenter | `c3.y` (`y.g`, 227 ln) | **`c3.d0` (`d0.t`, 649 ln)** |
| One segment (text + type) | `c3.z` | **`c3.e0`** |
| Required-engines dialog / engine-name map | `c3.t` / `c3.u` | **`c3.u` / `c3.v`** |
| Language check (in store list AND not-disabled) | `m.o(lang)` | **`n.n(lang)`** |
| Enabled ISO set / scan list / iso3 helper | `m.f` / `m.c` / `m.f(Locale)` | **`n.f` / `n.c` / `n.e(Locale)`** |
| Detection main / first-cp | `clsCLD2.b` / `clsCLD2.a` | same |
| codepoint→script / script family / fallback map | `a.b` / `a.e(cp, m.f)` / `a.w` | `a.b` / **`a.e(cp, n.f)`** / `a.w` |
| **CLD2 language hints (NEW)** | (n/a) | **`clsCLD2.f(Set)` → `nativeSetLanguageHints(String[])`** |
| disable-advanced-detection flag | `AutoTtsService.W` | **`AutoTtsService.a0`** |
| single-char UNKNOWN gate (quick char read) | `AutoTtsService.X` | **`AutoTtsService.b0`** |
| strip-audio-attr / force-accessibility / keep-alive / notification | `S`-era letters | **`W` / `X` / `Y` / `Z`** |
| **punctuation-in-flow (NEW, default true)** | (n/a) | **`AutoTtsService.c0`** |
| **smart number reading (NEW, default false)** | (n/a) | **`AutoTtsService.d0`** |
| number / punct / emoji mode ints | `H` / `I` / `g0` | **`I` / `K` / `M`** |
| **number / punct / emoji specific language (NEW)** | (n/a) | **`J` / `L` / `N`** |
| dual / mixed-latin / mixed-non-latin language | `G` / `K` / `L` | **`H` / `O` / `P`** |
| reading mode int | `AutoTtsService.O` | **`AutoTtsService.S`** |
| licence/signature gate (**do NOT port** — carve-out) | `y.g` args 4-5 | **`AutoTtsService.h0` / `m0`, `c3.l0`** |

### Java Source (decompiled_java/)
- `com/vnspeak/autotts/AutoTtsService.java` — main TTS service (synthesis logic, static flags)
- `com/vnspeak/autotts/clsCLD2.java` — CLD2 detection (`b()` detect, `a()` first-cp, `e()` unknown-char, `c()` mix-chunk list)
- `com/vnspeak/autotts/a.java` — script/lang mapping (`a.b(int)`, `a.e(cp,set)`, `a.w` map)
- `com/vnspeak/autotts/NewSettingsActivity.java` — settings UI (tabs/spinners/sliders)
- `c3/m.java` — settings store + engine check (`m.o()`, `m.f` Set, `m.p`/`m.r` loaders)

### Resources (autotts_res/)
- `autotts_res/res/xml/tts_engine.xml` — TTS engine declaration (settingsActivity)
- `autotts_res/res/layout/` — UI layouts
- `autotts_res/res/xml/` — XML configs
- **Note**: `values/strings.xml` ab poori decode ho gayi hai — `autotts_reference/decompiled_res/res/values/strings.xml` (app_name, auto_mode_*, autotts_* sab)

## Completed Fixes (all in branch, build verified)
| Commit | Fix |
|--------|-----|
| `d55a46b` | MISSING-C: type-based disabled-engine fallback |
| `c6fc8e8` | `isUnknownChar()` exact AutoTTS clsCLD2.c() match |
| `d195364` | Fix #1/#2/#4: emoji latRange, non-Latin type, CLD2 windowing |
| `0b91941` | Fix #3: smartLangFallback → type-based in Mix mode |
| `431b694` | Remove stripMarkdownHtml, MISSING-B, per-char emoji routing |
| `80ac1ce` | Emoji routing: segment-level emojiFall |
| `32a8486` | Phase 4 whitespace detection — full Java isWhitespace set |
| `f80216c` | getScriptLang() SCRIPT_BLOCKS mirror |
| `83b7a80` | getScriptLang() exact Blocks.txt match |
| `d6cb130` | getScriptLang() exact port of AutoTTS `a.b(int)` |
| `10794af` | Script fallback: first codepoint only + script family fallbacks (a.w map) |

## Pending / Known Remaining Differences
1. **~~emojiFall~~ — RESOLVED (verified 2026-07-17)**: emoji handling ab AutoTTS ke
   barabar hai. AutoTTS pattern `d` (c3/w.java:25-32) khud `c3.c.a()` (emoji regex)
   append karta hai → emoji `latRange` pattern `d` mein MATCH hote hain → Latin segment
   → `w.c()` type **1** (Latin) deta hai (v.a/w.e/w.d nahi matchte). Hamara C++
   (buildMixChunks: emoji Latin buffer mein, type 1; `emojiFall` dead-code `(void)`)
   isse exact match karta hai. Purana note (emoji type-2) galat tha.

2. **Mix mode flow — VERIFIED A-to-Z (2026-07-17)**: N gating (x.g khud N check),
   N=true LocaleSpan split → H()/w.g, N=false whole-text w.g, per-chunk clsCLD2.b +
   type fallback, emoji type-1 — sab AutoTTS ke barabar. Koi divergence nahi.

## Key Architecture
- **Naming (2026-07-12)**: service ke obfuscated AutoTTS mirror-names descriptive kiye gaye — modeInt(L), dualLang(D), localeSpansFlag(N), stripAudioAttrFlag(P), forceAccessibilityFlag(Q), numberModeInt(E), puncModeInt(F), emojiModeInt(G), dedicatedEnginesFlag(O), voiceList(T), engineList(M), chunkQueue(J), chunkCounter(K), utteranceIdStr(X), initializingTts(U), enginePool(f), engineIndex(d_), lastEnginePkg(c), reqParams/reqVolume/reqRate/reqPitch(i/j/k/l), loadVoice(V), loadVoiceOriginal(W), loadVoiceDedicated(X_eng), findEngineForLocale(c0), isLangRoutable(ko), isLangRoutableRaw(koRaw), localeIso3(kfLocale), isKnownIso2(khMappable). Har declaration par AutoTTS mapping comment hai.
- 3 modes: "auto", "mix", "dual"
- CLD2 native library for language detection
- `getScriptLang(cp)` — port of AutoTTS `a.b(int)` — codepoint → language
- `getScriptLangFallbacks(primary)` — port of AutoTTS `a.w` map — fallback list
- `isLangRoutable(lang)`/`isEngineAvailable(lang)` — mirrors AutoTTS `k.o(lang)`

## Key AutoTTS Concepts (5.7.7.10 names; 5.7.7.1 in parens)
- `clsCLD2.b()` — main detection, calls `a.e(cp, m.f)` (was `k.f`) for script fallback
- `a.e(cp, m.f)` — returns script family object (filtered by user's enabled langs)
- `a_result.b()` — primary lang of script family; `a_result.a()` — fallback lang set
- `m.f` (was `k.f`) — Set of user-enabled ISO lang codes
- `m.o(lang)` (was `k.o(lang)`) — **iso2/iso3 is present in `m.c` AND `!e.i`. It does NOT look
  at the engine at all.** `m.c` comes from `language_N`, i.e. every scanned language. The
  engine test is a different method, `AutoTtsService.M(lang)`, and the two are used in
  different places — see `ANALYSIS_5.7.7.10_mix.md` §30.
- `AutoTtsService.W` (was `S`) — disable-advanced-detection flag; `AutoTtsService.X` — single-char UNKNOWN gate

## Google's Accessibility Test Framework is the spec (read 2026-08-19)
`support.google.com` is blocked by this container's egress proxy, so the "Learn more" links in a
Scanner report (e.g. `?p=unexposed_text`) cannot be fetched here. The **implementation** behind
every one of those pages is open source and reachable:
`raw.githubusercontent.com/google/Accessibility-Test-Framework-for-Android/master/src/main/java/
com/google/android/apps/common/testing/accessibility/framework/…`. Read the check class, not a
summary. `AccessibilityCheckPreset.java` lists all 14.

What matters for this app:
- **`SpeakableTextPresentCheck`** is the only ERROR-level one (topic 7158690). It has never fired
  on us — every focusable element already has a name.
- **`RedundantDescriptionCheck`** (topic 6378990) matches, case-insensitively and on word
  boundaries, `button`/`checkbox` (type), `checked`/`unchecked`/`selected`/`unselected` (state),
  `click`/`swipe`/`tap` (action) **inside a contentDescription**. Keep state out of names.
  *Known accepted warning:* the Languages screen's **"Show selected"** chip contains "selected" as
  part of its visible label, and WCAG 2.5.3 Label in Name requires the accessible name to contain
  the visible label. Renaming the chip is the only way to silence it — a UI wording decision, so
  ask the user first.
- **`UnexposedTextCheck` is `@Beta`, javadoc "This check is under development"**, and entirely
  OCR-driven (`CONFIDENCE_FILTER_THRESHOLD = 0.5f`, Levenshtein ≤ 2, best-match view by
  intersection-over-union). Three scans of the *same* open dropdown gave "No suggestions",
  "No suggestions", and 17 flagged rows. **Do not chase its results**; judge by whether the label
  is set. Its real rule: an element's speakable text must contain its visible text, in the same
  order, and a list row's contentDescription must cover all the text visible in that row.
- **`DuplicateSpeakableTextCheck`** (topic 7102513) — two views with the same speakable text is a
  WARNING when either is clickable. This is what the label-in-the-control change above fixes.
- **`EditableContentDescCheck`** (topic 6378120) — an editable text field must **not** have a
  contentDescription. The Languages search field therefore stays as it is; never add one.
- `ViewHierarchyElementUtils.getSpeakableTextForElement` is ATF's model of what a screen reader
  says, and it states plainly: *"Content descriptions override everything else — including
  children."*

**Compose's own docs confirm the merged-node diagnosis**: *"Unmerged Tree: keeps all nodes intact
— used by accessibility services — allows services to apply their own merging."* Compose assumes
the service merges. TalkBack does; the reader the user tried does not.

**A doc snippet that is a trap.** `developer.android.com/develop/ui/compose/accessibility/
api-defaults` shows `Modifier.semantics { onClick(label = "…", action = { true }) }` for
relabelling a nested clickable. That **replaces** the accessibility click action with a no-op —
it is exactly what once made the Configuration language rows unopenable under TalkBack. Relabel
with `Modifier.clickable(onClickLabel = …)` instead.

**Automated option, not yet set up:** Compose 1.8+ ships `ui-test-junit4-accessibility` with
`enableAccessibilityChecks()`, which runs these same ATF checks from an instrumented test. It
needs an emulator in CI.

## The APK must always land on the Releases page (fixed 2026-08-19)
`workflow_dispatch` declared an input `publish_release: {type: boolean, default: true}` and the
release step was gated on it. **A dispatch through the API does not reliably apply that default**
— run 730 skipped "Publish APK to GitHub Release" and run 731 did not, from identical calls, so
builds 697-730 published no release at all and the only thing left to download on the run page
was `r8-mapping-<n>`, which is what the user ended up with instead of an APK.

The input is gone and both the release and the backup artifact are unconditional. The asset is
copied to **`EasyVoice-<run_number>.apk`** first, so it is distinguishable by name from the
mapping and does not collide in the Downloads folder. `mapping.txt` stays a private artifact —
it undoes the obfuscation and must never be attached to a release.

## Full Compose accessibility guideline pass (2026-08-19)
All seven pages under `developer.android.com/develop/ui/compose/accessibility/` were read
(index, api-defaults, semantics, merging-clearing, traversal, scalable-content, inspect-debug,
testing), plus `guide/topics/ui/accessibility/{principles,apps}`. What that changed, each item
verified against androidx source rather than a doc summary:

1. **Colour roles we never declared.** `darkColorScheme()` fills the rest from the M3 baseline,
   and two of those are actually drawn and failed WCAG 1.4.11's 3:1 floor for non-text UI:
   - `outlineVariant` `#49454F` — `TabRow`'s default `divider = { HorizontalDivider() }`
     (TabRow.kt) and `DividerDefaults.color = DividerTokens.Color = OutlineVariant`, i.e. the
     line between the tab bar and the page, at **1.76:1** on surface. It is also
     `FilterChipTokens.FlatUnselectedOutlineColor`, so an unselected chip's whole boundary.
     Now `#727880` = 3.70:1.
   - `secondaryContainer` `#4A4458` — `FilterChipTokens.FlatSelectedContainerColor`, the selected
     chip's fill, **2.02:1** on the page, and `FlatSelectedOutlineWidth = 0.0.dp` with
     `selectedBorderColor = Color.Transparent`, so the fill is the only boundary a selected chip
     has. Now `#42707F` = 3.44:1 with a white label at 5.45:1.
   `surfaceContainer` / `surfaceContainerHigh` (menu and dialog fills) follow our own surface —
   their low ratio against the page needs no fixing, a popup over a scrim has no contrast
   requirement against what it covers, but the baseline values are purple-tinted.
   Every pair in the palette is computed in `scratchpad`, not eyeballed; the four that read as
   failures are the two above plus the unchecked switch track and the menu fill, and those two
   are covered by `uncheckedBorderColor = outline` (11.0:1) and the scrim respectively.

2. **Text has to survive the font scale** ("test across all scales, 0.75x-3.5x").
   - **Material3's `AlertDialog` does not scroll its text slot** — `AlertDialog.kt` gives it
     `Modifier.weight(weight = 1f, fill = false)` and nothing else — so the required-engines list
     clipped. It now scrolls. There is no nested-scroll conflict *because* M3 adds none.
   - The startup scan screen had no scroll at all. `fillMaxSize().verticalScroll()` keeps
     `Arrangement.Center` working: `verticalScroll` only relaxes `maxHeight`, so `minHeight` still
     forces the column to fill the viewport and it grows only when the text is genuinely taller.
   - The Languages header is capped at `heightIn(max = 280.dp)` and scrolls inside itself, so it
     can never starve the list. Natural height is ~235dp, so at the default scale nothing changes.

3. **A LazyColumn's `collectionInfo` counts everything it holds.**
   `LazyLayoutSemanticState.kt`: `CollectionInfo(rowCount = totalItemsCount, columnCount = 1)`.
   The Languages list therefore reported **141 items** (137 languages + header + note + button row
   + search field) and every row's index was off by four; Configuration was off by one. The
   non-row content moved out of both lists into a fixed header above them — which also keeps
   search and Select all reachable without scrolling back through 130 rows.
   **Never put a header, a paragraph or a control inside a LazyColumn that represents a list.**

4. Configuration gained a `SectionHeader`, so every tab now has at least one heading to jump to.

Checked and already correct, so do not re-audit: traversal order (every screen is a single
vertical column, so the default reading order matches the visual one — `isTraversalGroup` /
`traversalIndex` are not needed); `liveRegion` used twice only, as the docs ask; the Languages
search field carries **no** `contentDescription`, which `EditableContentDescCheck` requires; no
duplicate literal accessible name remains anywhere (swept automatically).

## Languages start CLEARED, and the voice screen hides what needs a voice (user, 2026-08-19)
Two deliberate departures, both asked for directly.

1. **`rebuildFromScan` reads `iso3 + "_disabled"` with a default of `true`.** AutoTTS defaults it
   to `false`, so every scanned language arrived enabled and the Languages screen opened with all
   ~137 boxes ticked. The user does not want that: *"default language bhale hi selected rahe,
   baaki language clear rehni chahiye"*. Nothing else was needed, because the very next line
   already existed:
   `if (entry.disabled && required.contains(iso3)) { entry.disabled = false; changed = true }`
   and `requiredLangs` returns the mode's own languages — `autoLang` for auto/google,
   `dualLang` + `"eng"` for dual, `mixLatin` + `mixNonLatin` for mix/multilingual — so the default
   language stays ticked and nothing else does.
   **Deliberately NOT flipped:** `dualLangList` (it builds only `"eng"` and the dual language,
   both always required, and it has no required-language fix-up, so a `true` default there would
   leave dual mode with nothing routable) and `load()` (it parses the persisted `language_N` list,
   whose entries have been written at least once).
   The default only applies where the key has never been written, so an install that already has
   choices keeps them; **"Clear all" produces the same state on demand** and already re-ticks the
   required languages.

2. **`VoiceScreen` shows Test, the three sliders and Default only when a voice is selected.**
   `voiceRows.firstOrNull() == null` is exactly "no TTS selected" — either the language has no
   voices at all, or the `"*Disabled"` row sits at the front — and it is the same test
   `speakTest` and `variantsFor` already use. The variant picker was already gated on it; the rest
   were not, so they sat on the screen doing nothing. An explanatory line replaces them, so the
   screen is never left with nothing to perceive.

## The tab strip is a Box + draggable, NOT a pager — do not "restore" the pager (2026-08-19)
Two rounds of getting this wrong; the record matters.

**What the user reported, exactly.** From a language row in Configuration, swiping backward with a
screen reader landed on **Main Settings**; from the last language row, swiping forward landed on
**Advanced**. Separately, the deliberate **two-finger swipe** must keep changing tabs — TalkBack
forwards a two-finger gesture through as ordinary touch, so it is a real gesture, not an accident.

**Why `HorizontalPager` cannot do both.** Its scroll semantics come from `lazyLayoutSemantics`, and
the Compose a11y delegate deliberately makes every scroll container auto-scrollable for TalkBack:

```kotlin
// Talkback defines SCROLLABLE_ROLE_FILTER_FOR_DIRECTION_NAVIGATION, so we need to
// assign a role for auto scroll to work. Node with collectionInfo resolved by
// Talkback to ROLE_LIST and supports autoscroll too
if (!semanticsNode.hasCollectionInfo()) { info.className = "android.widget.HorizontalScrollView" }
```

Either branch puts the node in TalkBack's `FILTER_AUTO_SCROLL`
(`ROLE_DROP_DOWN_LIST`/`ROLE_LIST`/`ROLE_GRID`/`ROLE_SCROLL_VIEW`/`ROLE_HORIZONTAL_SCROLL_VIEW`),
the set TalkBack scrolls by itself when linear navigation runs off the end. The only switch that
removes that action, **`userScrollEnabled`, removes `pageLeft`/`pageRight` and the touch gesture
with it** — `Pager.kt`'s `pagerSemantics` is gated on it and its KDoc says the flag covers
"scrolling via the user gestures **or** accessibility actions". That was the first attempt and it
cost the two-finger swipe.

**What it is now.** A plain `Box` rendering only the selected page, with `currentPage` as ordinary
state that the `TabRow` drives. A `Box` publishes no scroll semantics, so there is nothing for
TalkBack to scroll and each page is a hard boundary. `Modifier.draggable` supplies the swipe: it
adds **no** semantics of its own (unlike `Modifier.scrollable`), it is stable API, and it still
sees the two-finger gesture. The delta is accumulated and acted on once in `onDragStopped` against
`SWIPE_THRESHOLD_PX`, so one flick moves exactly one tab — the bug the old hand-written
`detectHorizontalDragGestures` had.

**Accepted costs, stated so they are not "fixed" later:** the tab strip is no longer a TalkBack
`ROLE_PAGER` and therefore no longer one of its containers, and there is no slide animation between
tabs. Container navigation still works where it matters — the Languages and Configuration
`LazyColumn`s publish `collectionInfo`, so they are `ROLE_LIST` containers, which is what lets a
user escape 137 language rows.

**For reference, TalkBack's own rules** (`google/talkback`, `AccessibilityNodeInfoUtils.java` and
`Role.java`): `FILTER_CONTAINER` accepts `CONTAINER_ROLES` (`LIST`, `GRID`, `PAGER`, `SCROLL_VIEW`,
`HORIZONTAL_SCROLL_VIEW`, `WEB_VIEW`) **or** a non-empty `getContainerTitle()`; and `ROLE_PAGER` is
derived from the node supporting `ACTION_PAGE_UP/DOWN/LEFT/RIGHT`, not from any class name.
Compose has no semantics property for `containerTitle` at all.

2. **The Mode settings heading names the mode** — "Dual languages settings" rather than the
   generic "Mode Settings". The window title already carried the mode name, but that is announced
   once on entry and easily missed, so nothing on the screen said which mode you were editing.

## The UI prose is OURS now — do not restore AutoTTS's wording (user request, 2026-08-20)
The user asked for every string that could carry copyright to be pulled out and rewritten from
what the code actually does — short, ours, no risk. Done, and measured rather than asserted:
**483 words of verbatim AutoTTS text down to 101.**

**What was rewritten (26 strings):** all five mode descriptions, the Languages and Voices screen
intros, the two "not available" lines, the scan message, the import error (both call sites), and
every Advanced-tab description — TTS settings, the accessibility-stream pair, keep-alive,
notification, battery, advanced detection, quick character read, punctuation in flow, smart
numbers, import/export and logging. Also **`"Requried TTS Engines"` → `"Required TTS engines"`**:
AutoTTS's own typo was the single most identifying string in the app, and an identical spelling
mistake is the classic fingerprint of copying. Rule 5's "keep AutoTTS's typo" no longer applies —
the user asked for exactly this.

Each replacement was written from **our** behaviour, not by paraphrasing AutoTTS's sentence. The
keep-alive line says "feeds silence between phrases" because that is literally what the 32-byte
`ByteArray(32)` loop does.

**What was deliberately kept (23 strings, all ≤6 words, none a sentence):** the plain functional
control labels — "Select preferred language:", "Preferred language for Latin text", "Select
language for reading numbers:", "Choose a voice for language", "Disable battery optimization",
"Show persistent notification", "Advanced Synthesis Options", "Language Detection Options",
"Import/Export Configuration", "Read punctuation in flow with text", "Force to use audio
accessibility stream", "Supports multilingual text with locale spans", "Remove audio attributes
from synthesis request.", "Multilingual mode (experimental)" and the rest of that family. Two
reasons, both deliberate: they are the plainest way to name the function, so any app writing them
independently lands on the same words and a claim over them is weak; and the user navigates by
them every day, so changing them costs real muscle memory for nothing. **Do not "finish the job"
by rewriting these** without the user asking.

Mode NAMES are also unchanged — "Dual languages", "Auto language detect", "Google TTS", "Mixed
mode", "Multilingual mode (experimental)".

This is a UI-only change under the carve-out: no logic, service, detection or storage behaviour
moved, and `EasyVoiceLogger` messages and log tags were not touched.

### Second pass: the wording had to sound like a person (user request, 2026-08-20)
*"bahut Sare paragraph Aise Hain Jo AI generated lag rahe hain — Jaise symbols bhi a rahe hain …
meaning aap Sahi rakhiye … bilkul Human Development ki tarah"*. The shortening pass got the
meaning right but the prose read machine-made. 32 strings reworded, **meaning unchanged** — this
pass touched wording only, and every replacement was checked against the meaning audit from the
previous turn.

What was actually wrong, and is now banned:
- **Em dashes and ellipsis characters.** `—` appeared in the Oppo/OnePlus/Realme line and in
  the Languages intro; `…` in `"Search languages…"`, `"Scan for tts engines…"` and
  `"Finding the voices installed on your device…"`. **No `—`, `…` or `…` anywhere in a
  user-facing string** — they read as a symbol to a screen reader and they are the giveaway the
  user spotted.
- **Clipped parallel fragments.** `"On, punctuation is read inside the sentence. Off, on its own."`
  → `"Reads punctuation as part of the sentence. Turn it off and punctuation is read on its own."`
  `"Check the voices themselves yourself."` →
  `"You still have to make sure the individual voices are there."`
- **`Off = CLD2 (default).`** — an equals sign is not prose.
- **Two lines starting with the same word on the same screen.** The scan headline and the scan
  progress line both began "Finding…"; the progress line is now "Checking your TTS engines".

One AutoTTS line was rewritten here that the copyright pass had missed: the battery toast
`"Find Auto TTS & all third-party tts engines and select 'Unrestricted'"` (`c3/k.java:1025`) was
still carried over with only the app name swapped. It is now
`"Set Easy Voice and each of your TTS engines to Unrestricted"`.

The 23 short AutoTTS labels and toasts stay as they are — they were written by a person, so this
pass had nothing to fix in them.
