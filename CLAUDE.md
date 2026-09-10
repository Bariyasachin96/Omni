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

**THE TABLE CHOICE WAS REVERSED BY THE OWNER ON 2026-09-08 -- THE APP NOW BUILDS
THE FULL SET.** This paragraph used to say we build the SMALLEST of CLD2's five
quad tables and must never change it. That is no longer true and the reasoning
it rested on was incomplete, so read the CMakeLists comment for the current
story. In short:

    compile.sh      quadchrome_16 + deltaoctachrome + distinctoctachrome
                    + score_quad_octa_2 + cjk_delta_bi_4        <- was ours
    compile_full.sh quad0122 + deltaocta0122 + distinctocta0122
                    + score_quad_octa_0122 + cjk_delta_bi_32    <- is ours now

They are a matched SET and must be swapped together; the score and CJK-delta
tables are built for their quad table. **Both quad tables define exactly the
same two public symbols** (`CLD2::kQuad_obj`, `kQuad_obj2`), so this is a
file-list change and no C++ moves -- verified with `nm -C --defined-only`, not
assumed.

**What it buys, from CLD2's own evaluation files** (`docs/evaluate_cld2_small_
20140122.txt` and `docs/evaluate_cld2_large_20140122.txt`, same corpus, same
date): **82 languages to 183**. The extra hundred are the ones the QUADGRAM
scorer needs a bigger table to name -- Frisian, Breton, Corsican, Luxembourgish,
Occitan, Maori, Samoan, Xhosa, Shona, Wolof, Kurdish, Pashto, Sindhi, Tajik,
Kazakh, Uzbek, Turkmen, Uighur, Tatar, Bashkir, Mongolian, Sanskrit and the
rest. Accuracy on the languages both sets already had is equal or better
(Bihari 0.7769 -> 0.8541; Arabic and Bengali reach 1.0000).

**It does NOT change the script-defined languages, and that correction matters.**
Gujarati, Tamil, Telugu, Kannada, Malayalam, Punjabi, Oriya, Sinhala, Khmer,
Lao, Georgian, Armenian and the rest are decided by their Unicode SCRIPT, never
by the quad table -- CLD2's README says so and our own `SCRIPT_FIXED_LANG`
ladder is that same rule. They worked before this change and are untouched by
it. They are missing from the small evaluation file only because that run did
not cover them, which is why that file's 82 counts quadgram-scored languages
rather than what the build can detect. **Do not read a language's absence from
that file as "the compact build cannot detect it".**

**What it costs, measured by compiling both sets with the project's own flags
and reading the object sizes:**

| | bytes | per ABI |
|---|---|---|
| compact tables | 951,416 | 0.91 MB |
| full tables | 6,103,648 | 5.82 MB |
| **delta** | **+5,152,232** | **+4.91 MB** |

and the APK ships TWO ABIs, so about **+9.8 MB uncompressed**. This is the one
change in the project that makes the download substantially bigger, and it sits
directly against the earlier "APK size kam kar do" request -- the owner asked
for the full detector knowing the tables are what the size is.

**Latency is unaffected, and that was measured rather than hoped.**
`tools/verify/latency/run.sh` links the REAL core and reads its source list from
`CMakeLists.txt`, so it compiled exactly what the app compiles:

    chars   3,520 (the ceiling)   1.38 ms   (compact was 1.58 ms)
    chars     165                 0.17 ms   (compact was 0.16 ms)

within noise in both directions. A bigger table is a bigger lookup, not more
work. `tools/verify/segmenter/run.sh` is still **IDENTICAL over 163,296 cases**,
which it must be -- it slices the core above `buildMixChunks` and never links a
table.

**To go back**, swap those five names in `CMakeLists.txt` for `compile.sh`'s and
change nothing else.

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
    tools/bootstrap.sh          # once per container: android.jar, kotlinc,
                                #   the Compose plugin and the androidx classpath
    tools/check-all.sh          # ~1 min, everything static
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
call signatures), `ktimports`, `xmlcheck`, `cpp-syntax.sh`, and `kotlin-typecheck.sh`.

**That last one changed completely on 2026-09-09 and the old advice is now wrong.**
It used to report ~1,254 errors, none of them meaningful, and had to be judged by the
NEW error texts alone. With Full network access it resolves androidx, generates `R`,
runs the Compose compiler plugin and reports **0** — so the count is real, and **any new
error fails the run**. It prints which of its two modes it is in on every run; see
"THE LOCAL CHECK NOW RESOLVES androidx" below for both, and for why REDUCED never fails.

Running it with no real `android.jar` at all would be worse than useless: kotlinc then
checks **nothing** at `android.*` call sites, which is how a wrong trailing lambda once
reached CI — and the same was true of androidx, which is how build 827 failed.

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

### The SECOND round: releasing the wait was only half of it (2026-09-03)
Owner, after the first fix: *"beech beech mein chalte chalte speech atak jata hai
... mere khyal se aapne sirf vah Java wali file padhi hai, aur bhi sources ho
sakte hain."* Both halves of that were right. The wait fix above is correct and
stays, but on its own it turns a **hang** into **silence** -- the utterance now
ends promptly instead of parking the thread, and the device still says nothing,
because the reason `engineIndex` was -1 was never addressed. The engine pool had
a state it could not leave.

**`state = -1` was TERMINAL, and nothing in either app could leave it.** All
seven in-app `restoreEngine` call sites live inside `loadVoice` /
`loadVoiceOriginal` / `loadVoiceDedicated`, and all three find their wrapper with
`pkg == normPkg && state == 2`. The eighth is `onServiceDisconnected`, which
fires once per death. So the moment a wrapper lands on -1 -- **one** failed init,
which is exactly what a Play Store update of the engine produces, because the
package is briefly unresolvable and `bindService` fails -- nothing can ever call
`restoreEngine` for it again. `engineIndex` answers -1 for every language on that
engine for the life of the process, and the only cure is force-stopping the app.
That is the report, symptom for symptom, **including why force stop is the fix**.

**The recovery signal was already arriving and nothing was listening.** We hold a
binding to every engine, so Android calls `onServiceConnected` the moment that
process is back; the callback existed and only wrote a log line. It now calls
`onEngineProcessBack(pkg)` -- the mirror of `onEngineProcessGone`, in the other
direction. **No clock, no retry timer, no polling: the event IS "this engine is
alive again"**, which is the shape the owner has now required three times.

Two details in it are load-bearing:
- **the test is `state == -1`, never `!= 2`.** A wrapper that has never been
  initialised is 0, and the first bind fires this callback too, while
  `EngineInitListener` is still walking the pool -- retrying there would fight
  the init. -1 is only reachable from an init or a restore that actually failed.
- **`restoreCount` is zeroed**, for the same reason `onStart` zeroes it: the
  engine demonstrably came back, and that is an event rather than an interval.
  Without it the `k < 10` cap would hold an engine dead through a reconnect that
  would have worked.

**A second process-lifetime wedge, in the same method.** `restoreEngine` sets
`restoringIndex = idx` and then calls `TextToSpeech(...)` **unguarded**, while
`initAllEngines` wraps the identical call in a `try/catch`. That constructor does
real work -- it reads `Settings.Secure`, resolves the engine and calls
`bindService` -- so it can throw, exactly when the engine is mid-update. The
throw left `restoringIndex >= 0` for ever, and the guard at the top of the method
then answers `" -Restoring in progress..."` to **every** restore for the rest of
the process: no engine could be recovered again, by any path. Now guarded, with
the wrapper marked -1 and `restoringIndex` cleared -- and the new reconnect
callback is what picks it up.

**The native side was checked too, since a non-advancing loop there would hang
the same thread with no log at all.** `cld2DetectWindow`'s window loop always
advances `winStart` by 64 and the hint split always breaks on `npos` or advances
`from`; and `buildMixChunks` / `processDirect` are proven terminating by
`tools/verify/segmenter/run.sh`, which could not complete 163,296 cases
otherwise. **No native hang exists. Do not re-sweep it.**

## The screen's first heading was UNDER THE STATUS BAR -- edge-to-edge, not focus (2026-09-03)
Owner: *"jitni bhi screen per aapne heading lagai hai, us per TalkBack ka focus
nahin ja raha hai ... Pixel mein ja raha hai, Xiaomi mein nahin ... Mode settings
kholta hun to focus sirf 'Preferred languages' wali heading per jata hai."*

**The first answer here was incomplete and is corrected below.** It blamed a
per-device initial-focus heuristic and added a focus request. That was reasoning
from the symptom. The owner then sent a **screenshot** of the Voice setup screen
and it settles the question in one look: the heading **"English (eng) voices" is
drawn on top of the clock and the signal icons**. It was never a focus decision.
The heading was behind the status bar.

**The cause is one line in the build file: `targetSdk = 37`.** From Android 15
(API 35) the system draws every app **edge to edge** and **ignores**
`android:statusBarColor` and `android:navigationBarColor`. `values/styles.xml`
still sets both; on API 35+ neither does anything. And the app consumed window
insets **nowhere**.

**Which screens broke, and why exactly those.** `MainActivity` is fine because
its `Scaffold` hands `innerPadding` to `ResponsiveContent`, and Scaffold's
`contentWindowInsets` is `systemBars`. The four screens that are their own
Activity -- About, Languages, Mode settings, Voice setup -- call `setContent`
with nothing between `EasyVoiceTheme` and the screen, so their content starts at
y = 0 and the FIRST element lands under the status bar. **That is exactly the set
the owner reported**, and it is why the first heading was the only one affected:
it is the only element at the top of the window.

**And it explains the device split, which the focus theory never really did.**
The status bar is taller on the owner's Xiaomi than on the Pixel, so there the
whole heading was covered while on the Pixel it cleared the bar. Same APK, same
semantics, different usable top edge.

**THE FIX IS ONE PLACE, `EasyVoiceTheme`, and it covers every screen the app
will ever have.** The first attempt wrapped the four activities by hand in an
`EvScreenInsets` helper. The owner rejected that shape, and was right: *"sabhi
devices aur sabhi user interface ke saath compatible ho jaye ... hamein extra
kuchh karne ki zarurat hi na pade ... library mein aisa kuchh to hoga."* There
is, and it is two guarantees read from androidx rather than assumed:

1. **`Modifier.windowInsetsPadding` CONSUMES what it pads.**
   `WindowInsetsPadding.kt`: *"Any insets consumed by other insets padding
   modifiers or [consumeWindowInsets] on a parent layout will be excluded from
   [insets]. [insets] will be consumed for child layouts as well."*
2. **Material3's `Scaffold` SUBTRACTS what an ancestor consumed.**
   `Scaffold.kt:104`: `safeInsets.insets =
   contentWindowInsets.exclude(consumedWindowInsets)`.

So a single `Modifier.windowInsetsPadding(WindowInsets.safeDrawing)` inside
`EasyVoiceTheme` -- which **every** activity already goes through -- pads every
screen, and MainActivity's `Scaffold` then hands out an `innerPadding` of **zero**
instead of padding a second time. **Nothing had to be told about anything, and a
future screen needs no action at all.** The `Surface` still fills the whole
window so the background colour paints behind the bars; only the `Box` inside it
is inset.

**`safeDrawing`, not `systemBars`.** `WindowInsets.android.kt:362` defines it as
`systemBars.union(ime).union(displayCutout)`, so it also clears the **display
cutout** -- a punch-hole or notch is what makes one device's usable top edge
lower than another's, which is why this read as a Xiaomi-only bug -- and moves
content off the keyboard.

**`enableEdgeToEdge()` IS called, from one place -- `EvActivity` (owner decision,
2026-09-03).** The first version of this fix deliberately skipped it, reasoning
that on API 24-34 the `DecorView` fits the system windows and consumes the bar
insets before they reach the `ComposeView` -- which is where Compose's listener
sits (`WindowInsetsHolder`:
`ViewCompat.setOnApplyWindowInsetsListener(view, insetsListener)`) -- so
`safeDrawing` is zero there and the padding is a harmless no-op. That reasoning
is correct and the app worked. **But it left the app with TWO window shapes**:
edge to edge on API 35+, decor-inset below it, and **only one of them ever
exercised the padding**. The owner asked for androidx to be the thing we depend
on and for one behaviour everywhere -- *"sabhi devices ... Android X library,
Jetpack Compose library ... usi ke saath depend rehna chahta hun"* -- so
`enableEdgeToEdge()`, which is androidx's own API for declaring it, is now
called and there is one shape, one code path and the same first frame on every
Android the app installs on.

**It lives in `EvActivity`, a base `ComponentActivity` all five Compose
activities extend**, so it is one place and a future screen inherits it. (The two
plain `Activity` intent handlers, `CheckVoiceData` and `GetSampleText`, draw no
UI and are untouched.) The order in `MainActivity` still works:
`installSplashScreen()` runs, then `super.onCreate` enters `EvActivity`, which
declares edge to edge before `ComponentActivity.onCreate`.

**Both `SystemBarStyle`s are stated explicitly, and the default would have been a
real bug.** `EdgeToEdge.kt`'s default is
`statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)`, and
`auto` picks light or dark icons from `detectDarkMode(resources)` -- **the
SYSTEM's** dark-mode setting. This app is dark in both settings by design, so on
a phone in light mode `auto` would ask for DARK icons on our DARK bar and the
clock and signal icons would vanish. `SystemBarStyle.dark(Color.TRANSPARENT)`
states what is true of this app and is right on every device. It also closes a
gap the XML theme had: `styles.xml` sets `android:windowLightStatusBar` but never
`windowLightNavigationBar`, so the navigation bar's icons were unspecified. Both
bars are now stated together.

**TRANSPARENT scrims are correct HERE and would not be in a light app** --
`EdgeToEdge.kt` defaults the navigation bar to a scrim precisely for pale content
behind the bar, and our content is always `#121212`.

**A new local-check noise signature came with the base class, and it is not a
defect.** `kotlin-typecheck.sh` now prints `'onPause' overrides nothing` (and
`onResume` / `onDestroy`) for the five activities. Before, each extended
`ComponentActivity` directly, kotlinc could not resolve it, and an error
supertype **suppresses** that diagnostic; now the supertype is `EvActivity`,
which kotlinc resolves fine, so the members are genuinely looked up in a class
whose own parent is unresolvable. It is the same blocked-Google-Maven cascade one
level deeper -- the "our-own-name unresolved refs" counter stayed at 13, and the
total went DOWN by 11. **Do not chase these.**

**Do not move this padding into `ResponsiveContent`** and do not re-add a
per-screen wrapper. The theme is the one place, and the two library guarantees
above are what make it sufficient.

**`focusOnOpen` is KEPT**, and it is now what the owner literally asked for
rather than a theory about why it was needed: the first heading of each of those
four screens takes Compose input focus when the window opens, so the screen's own
name is where the reader lands. The mechanism, read from androidx rather than
assumed: `AndroidComposeViewAccessibilityDelegateCompat` sets `info.isFocusable`
only when a node carries `SemanticsProperties.Focused`, and on that property
turning true it sends `TYPE_VIEW_FOCUSED` for the node -- the standard event a
screen reader follows. `Modifier.focusable()` is what puts `Focused` there;
`focusRequester` is how we ask. Cost, stated rather than hidden: the heading also
joins keyboard and Switch Access focus order, one stop at the top, and that stop
is the screen's own name.

**The lesson worth keeping:** the owner's screenshot found in one frame what two
rounds of reading semantics source did not. **When a report says "on this device
but not that one", ask for a picture before theorising about the screen reader.**

## THE "FOURTH EXIT" WAS SHIPPED AND REVERTED THE SAME DAY (2026-09-03)
**Do not write it again the way it was written.** The utterance listener's
`onStop(id, interrupted)` logs and does nothing else, exactly as AutoTTS's does
(`decompiled_java_noexc/.../AutoTtsService.java:2770` is a bare log). For one
commit it released the wait, and the owner reported the result immediately:

*"thoda sa bhi agar next element per jata hun explore by touch se ... jo pehla
text hai vah bilkul stop ho jata hai ... button read hi nahin karta. Swipe karte
hain to read karta hai."* And: *"stop to already hamare paas likha hua tha, to
vah thoda extra ho gaya."* They were right on both counts.

**WHY IT BROKE, and the mistake is worth keeping.** A screen reader interrupts by
stopping us and immediately sending the next utterance, so the engine's `onStop`
for the OLD utterance lands on a binder thread afterwards. That was guarded with

    if (id != expectedId) return

on the belief that `expectedId` identifies the utterance. **IT DOES NOT.**

    expectedId  = "${utteranceId}_${chunkCounter}"
    utteranceId = (request?.params?.getString("utteranceId")).toString()

`utteranceId` is a **static**, and when the caller sets no `utteranceId` param it
is literally the string **`"null"`**. Consecutive utterances therefore share the
id, the stale callback matched the NEW listener's `expectedId`, and it set
`isStopped` on an utterance that had not spoken yet. **Explore-by-touch is a
continuous stream of interruptions, which is why it failed there every single
time and survived a slower swipe** -- exactly the split the owner described.

**The hang it was written for is real in principle** -- AOSP's
`SynthesisSpeechItem.stopImpl()` dispatches `onStop` and **neither `onDone` nor
`onError`**, so an utterance stopped by something that is not us leaves the wait
unwoken -- **but it was found by reading, never in any log the owner sent**, while
the regression was immediate and total. AutoTTS carries the same shape and rule 5
says mirror it. So it is reverted, and it stays reverted until a real log shows
the hang.

**IF IT EVER SHOWS UP IN A LOG**, the guard must be a per-utterance **generation
counter** bumped at the top of `onSynthesizeText` and captured in the listener
closure -- **never the utterance id, which is not unique**. Write that, not the id
check.

**The other three exits are NOT affected and stay.** `releaseWaitWithoutSpeaking`
fires only where no `speak()` was ever issued, so it cannot cut a speaking
utterance short; and `onEngineProcessBack` / `onEngineProcessGone` fire on real
bind callbacks. Only the `onStop` release could race a live utterance, because it
is the only one on the path a screen reader takes several times a second.

**The lesson, and it is the second time the same shape has bitten:** a fix on the
synthesis path that cannot be tested here must be judged by *what it can do when
it fires at the wrong moment*, not only by the hole it closes. Both regressions
this day -- this one and "button button" -- were changes that were correct in
isolation and wrong in the presence of the next event.

**Still the owner's lever, not ours to flip:** `startForegroundIfPossible` runs
only when "Show persistent notification" is on, and that switch is OFF by
default. On an OEM that kills background services, the foreground notification is
what keeps the service alive at all.

## THE UTTERANCE ID WAS NOT UNIQUE, AND THAT IS THE INTERRUPTION BUG (owner, 2026-09-09)
*"yah problem a raha hai na speech interruption ka ... abhi to sahi tarike se fix
nahin kiya hai, abhi aap check karoge na to pata chal jaega sab."* They were right,
and the thing to check turned out to be one string.

**THE DEFECT, and it is a three-thread race the code had no way to see.** Three
different threads touch the speaking state and nothing said which utterance an
arriving callback belonged to:

    synthesis thread   onSynthesizeText, and speakChunk(true) for the first chunk
    binder threads     the target engine's onStart / onDone / onError / onStop
    main looper        onDone's chunkHandler.post, the next-chunk step

`setOnUtteranceProgressListener` is **one volatile field per `TextToSpeech`**
(`TextToSpeech.java:2156` is a bare assignment) and the framework reads it **at
dispatch time** (`Connection.mCallback.onSuccess`: `listener = mUtteranceProgress-
Listener; listener.onDone(id)`). So when a screen reader interrupts -- stop us,
send the next utterance -- the OLD utterance's callback is delivered to the
listener the NEW utterance installed, **carrying the new utterance's `callback`
and `chunkQueue` in its closure**. A stale `onDone` then popped the new
utterance's chunk and spoke it against the old, already finished callback; a stale
`onError` set `isStopped` on an utterance that had not spoken yet. That is text
being cut off, skipped, or simply not read -- the report, exactly.

**WHY THE 2026-09-03 ATTEMPT FAILED, and it is the whole lesson.** That commit
guarded with `id != expectedId` and broke explore-by-touch outright. The check was
right. **The id was not.**

    expectedId  = "${utteranceId}_${chunkCounter}"
    utteranceId = (request?.params?.getString("utteranceId")).toString()

`utteranceId` is a static and is literally the string `"null"` when the caller
sets no param -- and `chunkCounter` is **reset to 1 for every utterance** by the
`if (currentChunk == 1) chunkCounter = 1` line right above it. So two consecutive
screen-reader utterances, which are one chunk each, both spoke under `"null_1"`,
the stale callback matched, and the guard did the damage it was written to
prevent.

**THE FIX IS THE ID.** A `synthesisGeneration` is bumped once at the top of every
`onSynthesizeText` and goes into the id: `"${utteranceId}_${myGeneration}_${chunk
Counter}"`. Now `id != expectedId` means what it says, and it is used in `onStart`,
`onDone` and both `onError`s. **Eleven lines of code.**

**The framework hands that string back verbatim and an engine cannot alter it** --
read from AOSP, not assumed. `TextToSpeech.speak()` passes it as its **own AIDL
argument**, not inside `params` (`TextToSpeech.java:1238`); `TextToSpeechService`
stores it in `UtteranceSpeechItemWithParams.mUtteranceId`, `protected final` on a
**private** class of the framework; and every `dispatchOn*` sends
`getUtteranceId()`. An engine subclass overrides `onSynthesizeText` and friends,
never those. A live callback therefore always matches, so the guard cannot hang
the wait.

**The id check alone is not enough, and the second half is why.** A callback can be
perfectly live when it arrives and still act too late: `onDone` posts the
next-chunk step to the **main looper**, and an interrupt in between means that
runnable runs after the utterance ended, when `chunkQueue` already belongs to the
next one. The id was true when we posted. So two more guards, on the generation
rather than the id:
- **at the top of `speakChunk`** -- the one place that pops `chunkQueue` and
  speaks, reached from all three threads;
- **inside the posted runnable**, so `chunkCounter++` and `onLoadLanguage` do not
  run for a dead utterance either.

**Why the generation needs no lock:** only `onSynthesizeText` writes it, and AOSP
guarantees exactly one writer -- `SynthHandler` is a single `HandlerThread`,
`SpeechItem.play()` throws on a second call, and `playImpl()` is the only caller.
`@Volatile` is for the readers.

**Why none of this can hang, stated rather than hoped.** The guards only ever drop
a call from a generation whose `onSynthesizeText` has already returned -- and that
method ends with `startAndFinish(callback)`, so that utterance's callback is
already finished and nothing is parked on it. The live utterance's own callbacks
carry its own generation and its own id, so nothing of its is dropped.

**A second, smaller defect fixed in the same pass: two `chunkQueue.clear()` calls
did not hold the monitor.** `onStop` (binder thread) and the empty-text path,
against `speakChunk`'s `removeAt(0)` on the synthesis or main thread -- and the
other four accesses already synchronize on it. A bare `ArrayList.clear()` racing a
`removeAt` does not merely lose an element, it **throws**, and an exception inside
`onStop` would abort it **before** the two lines at the bottom that release the
parked synthesis thread. `chunkQueue` is a leaf lock everywhere (nothing is taken
while holding it), so this cannot invert with the `syncLock` or `LangStore.languages`
orders already recorded.

**`onStop(id, interrupted)` IS STILL LOG-ONLY, and that is deliberate.** AutoTTS's
listener is a bare log (`noexc:2770`) and rule 5 governs. The hang it would guard
against is real in principle -- AOSP's `SynthesisSpeechItem.stopImpl()` dispatches
`onStop` and **neither `onDone` nor `onError`** (verified again this pass:
`PlaybackSynthesisCallback.stop()` -> `item.stop(STOPPED)` ->
`SynthesisPlaybackQueueItem` line 143 `dispatcher.dispatchOnStop()`) -- but no log
the owner has sent contains it, and our own `onStop()` override already releases
the wait on every interrupt we are told about. **If it ever does show up in a log,
`id != expectedId` there is finally a correct guard.** Do not add it on theory.

### THE SAME SHAPE ONE LEVEL DOWN: two engine inits shared one client field
Found while sweeping for more of the above, and it is a **wrong-voice** bug rather
than an interruption one. `initializingTts` was a single field written by the pool
walk in `initAllEngines` **and** by `restoreEngine`, and read back by both
listeners in their `onInit`. Nothing excluded them from each other:
`initAllEngines` and `restoreEngine` each take `this`, and neither `onInit` does.
Both run on the main thread, so they interleave at message boundaries -- which is
exactly where this lands:

    pool walk constructs TextToSpeech(engine3), returns to the looper
    engine5 dies -> onServiceDisconnected -> restoreEngine OVERWRITES the field
    engine3's onInit arrives:  enginePool[3].tts = <engine5's client>

Engine 3's wrapper then holds a client bound to engine 5, so every utterance
routed to engine 3 is spoken **by engine 5** -- wrong voice, wrong language -- and
once the restore lands too, two wrappers share one client and each one's
`setLanguage`/`setVoice` clobbers the other's.

**DELIBERATE DEPARTURE**, on the footing the owner set on 2026-09-09 for the
identical defect in the engine scan: *"ham log is per depend rahenge na to achha
nahin rahega ... properly source ke through fix karo."* AutoTTS carries one static
here as well.

Each construction now owns a one-element holder captured by its listener, the same
shape `EngineFinder.startEngine` uses and for the same AOSP reason: `onInit` can
fire **inline on the constructing thread, but only ever with ERROR** -- the only
dispatch that can carry SUCCESS is inside `SetupConnectionAsyncTask.onPostExecute`,
which is always asynchronous. So on SUCCESS the constructor has long returned and
`cell[0]` is set; on the inline ERROR path `cell[0]` is still null, and storing
null on a wrapper we are marking `state = -1` is strictly better than storing
another engine's client. The field is gone; it had no other reader.

`restoringIndex` itself was checked in the same pass and is **sound** --
`restoreEngine` refuses a second restore while one is in flight and
`RestoreInitListener` always runs to clear it (every failure path in AOSP's
`initTts` ends in `dispatchOnInit(ERROR)`), and `initAllEngines` is called once,
from `onCreate`. Only the client had to move.

**Verified in the same read and NOT changed, so do NOT re-audit:**
- **calling `callback.start()`/`done()` off the synthesis thread is safe.**
  `AbstractSynthesisCallback`'s javadoc says those are synthesis-thread-only, and
  our listener calls them from binder threads -- as AutoTTS's does. The real
  implementation is internally thread-safe: `PlaybackSynthesisCallback` has a
  `private final Object mStateLock`, a `volatile boolean mDone`, and
  `synchronized (mStateLock)` on every one of its entry points.
- **AOSP already covers a callback we start but never finish.**
  `SynthesisSpeechItem.playImpl()` ends with *"Fix for case where client called
  .start() & .error(), but did not called .done()"* and calls `done()` itself. Ours
  ends with `startAndFinish(callback)` anyway.
- **the next utterance cannot begin before ours returns.** `SynthHandler` is one
  `HandlerThread` and `enqueueSpeechItem` posts the new item's runnable behind the
  one that is blocked in `onSynthesizeText`, so the generation can only advance
  after we return.
- **an interrupt reaches us as `onStop()`, not as a queued item.**
  `enqueueSpeechItem(QUEUE_FLUSH, ...)` calls `stopForApp` **synchronously on the
  binder thread first**, and only then posts; `stopForApp` -> `current.stop()` ->
  `stopImpl()` -> `synthesisCallback.stop()` **and** `TextToSpeechService.this.onStop()`.
- **`enginePool`'s bounds-checked `while (index < enginePool.size)` walk is
  AutoTTS's own shape and is left alone** -- adding a lock there would be the
  "defensive" justification rule 5 forbids, and nothing has been reported on it.

## THE READING PATH AUDITED A TO Z AGAINST THE FULL CLD2 (owner, 2026-09-09)
*"pura A to Z reading ... segmentation mein AutoTTS mein hamare paas se kuchh
chhut to nahin raha ... kyunki hamare paas CLD2 pehle full nahin tha ... native
jagah per bhi aur sabhi jagah per."* The instinct was right and it found exactly
one thing.

**THE THREE PROOFS WERE RE-RUN ON THE FULL TABLES AND ARE UNCHANGED**, which is
what rules out a whole class of worry rather than arguing about it:

    segmenter (d0.t)        IDENTICAL over 163,296 cases
    normaliser (clsCLD2.a)  IDENTICAL, 1,062 mappings over 1,114,112 code points
    script family (a.java)  IDENTICAL over 15 sets x 1,114,112 code points

None of them can be affected by the table swap -- the segmenter slices the core
above `buildMixChunks` and never links a table -- but running them is what turns
"cannot be affected" into "was not affected".

**`IsoCodes` IS `c3.e`, and that is now MEASURED rather than trusted.** The four
tables were compared entry by entry (20 bibliographic pairs, 20 iso2 pairs, 9
Chinese variants, 10 no-iso2 codes -- all identical), and `e.a` / `e.b` / `e.c`
were read from the decompile and match `normalizeTag` / `toIso2` / `toIso3`
statement for statement, including `toIso3`'s three-letter branch returning the
ORIGINAL code when the iso2 round-trip finds nothing. (`c3/e.java` does not
compile standalone -- CFR's static initialiser reuses one `Object` local for
`String[]`, the same defect the segmenter harness records, plus one orphan
`catch`. The methods render fine, which is what this needed.)

**THE ONE FIND, AND THE OWNER REVERSED IT THE SAME DAY. DO NOT RE-APPLY IT.**
Every code the full build can return -- 164 of them, taken from CLD2's own
`evaluate_cld2_large_20140122.txt` -- was put through a Java mirror of
`IsoCodes`. Seven failed, and one looked like it mattered:

    "iw",    //  6 HEBREW        toIso3Map["iw"] = "heb"   already there
    "id",    // 38 INDONESIAN    toIso3Map["id"] = "ind"   already there
    "jw",    // 48 JAVANESE      NOTHING
    "yi",    // 91 YIDDISH       toIso3Map["yi"] = "yid"   already there

Those are the four pre-1989 ISO 639-1 spellings, and CLD2's own
`kLanguageToCode` still uses the old one for Javanese. `Locale.getISOLanguages()`
carries **`jv` and not `jw`**, so the seeding loop never makes a `jw` key and
`toIso3("jw")` answers null -- which sends the span through
`languageForDetectedRun`'s `?: byScript` to the **preferred Latin language**.

`toIso3Map["jw"] = "jav"` was added, and the owner reversed it in the next
message: *"Agar CLD2 to JW kehta hai to JW hi rehne do."* They are right and the
reasoning is rule 5. **AutoTTS's `c3.e` behaves identically** -- it has exactly
those three lines and no fourth -- so adding one is not "closing a gap", it is
this project deciding something about DETECTION on its own, which is the one
place the UI carve-out does not reach. The three lines exist only because
AutoTTS has them. `IsoCodes.kt` now carries a comment saying so, so the sweep
does not re-find it and re-fix it.

**The other six are deliberately NOT added** -- `crs` (Seselwa), `kha` (Khasi),
`lif` (Limbu), `mfe` (Mauritian Creole), `tlh` (Klingon), `zzp` (Pig Latin). No
TTS engine speaks any of them, so `languageForDetectedRun`'s engine check would
bounce them to the same fallback they reach today: mapping them would add a map
entry and change no behaviour at all.

**AND `jw` WAS ALREADY REACHABLE BEFORE THE FULL TABLES** (it is in the compact
build's 82 as well), so this was never a consequence of the swap -- it is a
long-standing hole the swap merely gave a reason to look for.

### What is still mismatched, stated rather than quietly fixed
`refillEnabledIso2` builds the hint list from `IsoCodes.toIso2(entry.iso3)`, so
an enabled Javanese arrives as **`jv`** while CLD2's answer is **`jw`** --
`isHinted` therefore cannot match them, and the span falls to
`scriptFallbackCode()`. Closing that needs a second spelling in the enabled set,
which feeds `nativeSetLanguageHints`' 64-code cap -- and this file already
records that the cap's iteration order decides WHICH 64 survive. That is a
change to proven detection state for a language nobody has reported, so it is
**not** made here. Say the word and it is two lines.

### THE SECOND PASS, METHOD BY METHOD (owner, 2026-09-09)
*"aur bhi chijen bahut deeply check kar lijiye ki kya-kya chhut raha hai jo
AutoTTS mein hai."* Every method of every class the reading path touches was
inventoried and matched. **Nothing is missing.** What the pass produced is one
correction to this file, one new proof harness, and a list of things that are
now settled and must not be re-audited.

**THE CORRECTION: `clsCLD2.g` and `clsCLD2.h` are NOT dead code.** A grep for
their callers comes back empty across the whole decompile *if you exclude
`clsCLD2.java` itself*, which is exactly the mistake to avoid -- both are called
from inside their own class:
- **`clsCLD2.h(char)`** is the ASCII-punctuation test `clsCLD2.c` skips with,
  ours is `isLatinPunctuation` (33-47, 58-64, 91-96, 123-126 -- identical);
- **`clsCLD2.g(String)`** is `UnicodeScript.of(cp0) == LATIN || COMMON ||
  INHERITED`, and it supplies the **latin flag of the quick-character
  single-character span** in `clsCLD2.e` AND `clsCLD2.f`. Ours is
  `isLatinCommonInherited`, and `detectLanguageRuns` already calls it at exactly
  that site. Present and correct -- but if it had been missing, a lone character
  under "Quick character read" would have taken the wrong preferred language,
  and no harness covers that path.

**The method inventory, all matched:**

| AutoTTS | count | ours | how it is known |
|---|---|---|---|
| `c3.d0` a-t | 20 | `buildMixChunks` + helpers | segmenter harness, 163,296 cases |
| `clsCLD2` a,b | 2 | `normalizeFancyCodepoint` | normaliser harness, 1,114,112 code points |
| `clsCLD2` c,g,h | 3 | `firstValidCodePointU16`, `isLatinCommonInherited`, `isLatinPunctuation` | read this pass, line for line |
| `clsCLD2` d,e,f,i | 4 | `detectLanguage`, `detectLanguageRuns`, `detectLanguageAggregate`, `pushLanguageSets` | read this pass, branch for branch |
| `a.java` a-g | 7 | `familyForCp` / `familyLangForCp*` | script-family harness, 15 sets x 1,114,112 |
| `c3.e` a,b,c | 3 | `IsoCodes` | entry-by-entry read + the new langcodes harness |
| `c3.e0` ctors, g, a-f | 10 | `TextChunk`, `splitByLocaleSpans` | read this pass, byte for byte |
| `c3.c`, `c3.c0` | 2 | the emoji regex, the all-whitespace test | inside the segmenter harness |

**Four things machine-checked in this pass rather than read:**
- **the smart-number keyword table is identical** -- AutoTTS's `d0.i` and our
  `smartNumberKeywords` were parsed and diffed row by row: **53 rows, same
  order, same contents**, including the four rows CFR hoists into named locals
  (`ar`, `hu`, `ro`, `he`, `bn`, `mr`, `lo`).
- **`CMakeLists.txt` builds exactly `compile_full.sh`'s list**, minus
  `cld2_unittest_full.cc` and `compact_lang_det_test.cc`, which are test drivers
  with their own `main`. Nothing else in the full build needs adding, and the
  two compile scripts differ in the five table files and nothing else.
- **the `Language` enum is shared.** `generated_language.cc` is in BOTH compile
  scripts, so the enum numbering `kScriptLangPairs` and
  `scriptLanguageHint[script]` are written in terms of did not move when the
  tables were swapped. That is the mechanical reason the swap needed no C++ change.
- **`splitByLocaleSpans` is `e0.g` byte for byte** and
  `refillEnabledIso2`/`pushLanguageSets` is `s0` statement for statement,
  including `getSpans(0, len - 1)`, the `end + 1` step, and s0's skip of any
  entry that is disabled or whose engine is empty or `"disable"`.

**THE NEW HARNESS: `tools/verify/langcodes/run.sh`, 283 codes, 0 differences.**
This is the one that actually answers the owner's worry. The full tables can
return **283 distinct codes** -- not the 164 of the evaluation file, because
`kLanguageToCode` also carries `zh-Hant`, `sr-ME` and 101 `xx-Script`
pseudo-languages. Two DIFFERENT rules decide whether such a code is routable:

    AutoTTS   c3.n.n: a THREE-letter code is scanned for VERBATIM, never
              normalised; anything else goes through c3.e.c and a null there
              makes n.n answer FALSE outright
    ours      the native toIso3(), which never returns null -- an unmappable
              code comes back unchanged and simply fails the set lookup

Swept over all 283: **104 codes are ones `c3.e.c` refuses, and every one of them
produces on our side a string (`jw`, `un`, `xx`) that cannot be an iso3 and so
fails the same way. REAL differences: 0.** The harness reads the ISO tables out
of `IsoCodes.kt` at run time and **fingerprints the C++ `toIso3()`**, so neither
side can drift away from it in silence, and it fails outright if a fourth
deprecated pair (`jw`) is ever added back. Negative-tested three ways: the `jw`
pair restored, the C++ changed, and the two-letter mapping broken.

**Settled by this pass, so do NOT re-audit:**
- CLD2 never emits `cmn`, `lzh`, `gan` or `hak`, so the Chinese fold at the end
  of the native `toIso3()` is unreachable from the detector -- harmless, and not
  a divergence;
- for CLD2's own vocabulary the three-letter branch **cannot** differ between
  the two rules: of its 27 three-letter codes only the eight in
  `codesWithoutIso2` are in `toIso3Map` at all, and each maps to itself;
- `AutoTtsService.o0` and `j0` -- the two ints passed to `clsCLD2.d`,
  `clsCLD2.f` and `d0.t` -- are the **licence gate** (`c3.l0.b`/`c`/`d`), not
  detection inputs. Both initialise to -1, which is the branch `d0.t` overwrites,
  so our port taking that branch unconditionally is correct and is the carve-out,
  not a gap;
- both CLD2 call sites still match the disassembly exactly: the span site passes
  `{nullptr, nullptr, UNKNOWN_ENCODING, per-script hint}` with `is_plain_text =
  true`, the window site `{nullptr, "", 0, UNKNOWN_LANGUAGE}` with
  `is_plain_text = false`, both with flags `0x4000`.

**The three proofs were re-run after all of this and are unchanged:** segmenter
IDENTICAL over 163,296 cases, normaliser IDENTICAL with 1,062 mappings over
1,114,112 code points, script family IDENTICAL over 15 sets x 1,114,112.

**Verified clean in the same audit, so do NOT re-check:**
- all **19** `SCRIPT_FIXED_LANG` codes (`el hy iw ka pa gu or ta te kn ml si th
  lo bo my km am ko`) map through `IsoCodes` -- the script path, which is the
  high-traffic one, has no holes;
- the native core holds **no** language-count assumption -- the only cap is
  `kMaxSpans = 128`, AutoTTS's own, and it counts spans;
- `kScriptLangPairs` is still the 48 pairs read off AutoTTS's `.rodata`. Javanese
  is **not** among them and must not be added: those pairs are a transcription of
  AutoTTS's binary, not a list we maintain;
- the CLD2 answer filter still ends by keeping `lang3[0]`'s raw code when nothing
  is hinted and the per-script fallback is empty, so an unmapped code really does
  reach the Kotlin rather than being swallowed.

## CLD2 CANNOT BE COMPRESSED, SO THE DUPLICATE HAD TO GO (owner, 2026-09-09)
*"cld to already clone ho raha hai ... isliye uska optimization hota hi nahin hai
to uska complete karo ... complete full CLD2 rahe aur file size bhi kam ho
jayegi."* Researched at the source and measured, and the answer moved the fix
somewhere the earlier passes never looked.

**The premise is half right and the half that is wrong matters.** CLD2 is not
skipped by the optimiser -- it is compiled with the app's own flags and
`--gc-sections` has always run over it. It is simply not the KIND of thing an
optimiser can shrink:

    .rodata (CLD2's tables)   6,253,216 bytes    95%
    .text   (all our code)      110,533 bytes   1.7%

R8 and `--gc-sections` both work by deleting unreachable CODE. Every table here
is reached from the detector, so there is nothing to collect, and the entire
native flag pass -- eight flags, measured twice -- is worth about 26 KB of
download per ABI.

**AND COMPRESSION CANNOT SAVE IT EITHER. Measured on the real table bytes:**

| | bytes | vs raw |
|---|---|---|
| raw | 6,165,933 | |
| **deflate -9 -- what the APK itself already does** | **4,653,696** | -25% |
| xz -9e | 4,106,444 | -33% |

A quadgram table is a HASH table -- `kQuad0122` is 262,144 buckets of 16 bytes,
79% filled, and only 2.0% of buckets are entirely zero -- so its bytes are close
to random and the APK's own deflate takes most of what is there. Storing the
tables pre-compressed and inflating them at load buys **547,252 bytes per ABI**
and costs 6 MB of dirty private RAM plus decompression on the path to the first
word. **Refused on the measurement, not on taste.**

**Where the size actually is: the APK ships the whole library ONCE PER ABI.**
`armeabi-v7a` and `arm64-v8a` each carry their own 6.4 MB copy of the same
tables, so about 9.5 MB of the 11,410,305-byte APK (build 859, from the API) is
one detector paid for twice.

### The fix is an ABI split, and it removes nothing
`splits { abi }` in `app/build.gradle.kts`, so a phone downloads only its own
CPU's code. `isUniversalApk = true` keeps the every-ABI build, and the workflow
still publishes it as **`EasyVoice-<n>.apk`** -- the name it has always had -- so
nothing the owner already does changes. Two smaller files sit beside it:

    EasyVoice-<n>.apk              universal, every ABI      ~11.4 MB
    EasyVoice-<n>-arm64-v8a.apk    every phone since ~2015    ~6.7 MB
    EasyVoice-<n>-armeabi-v7a.apk  32-bit-only phones         ~6.7 MB

Not one language, one device or one byte of the detector is lost -- the full
table set stays exactly as the owner chose on 2026-09-08.

**GATED ON `-PevAbiSplit`, and that is load-bearing.** Splits apply to every
variant, and the `accessibility` job builds debug + androidTest and drives them
on an emulator. Only the release step passes the property, so the job that took
four builds to get green is building exactly what it built on the green run.

### CLD2's OWN CLD2_DYNAMIC_MODE was researched and NOT taken
It is real and it is supported -- `compact_lang_det_impl.cc` compiles a whole
second path under that define, `loadDataFromFile` / `loadDataFromRawAddress`
mmap the tables from a data file, and `cld2_dynamic_data_tool.cc` writes that
file. One 6.17 MB file would serve both ABIs. It was rejected for three
measured reasons:
- **it solves the SAME duplicate the split solves**, so the two do not add up;
- **it lands on a worse number.** An asset has to be stored UNCOMPRESSED to be
  mmapped from the APK, so the APK would be about 8.4 MB against the split's
  6.7 MB. Compressing it instead means extracting 6.17 MB to internal storage
  on first run, which trades the download for install size and startup time;
- **it adds a failure mode with no floor**: a missing or unreadable asset leaves
  the detector with null tables, and the app then detects nothing in any
  language. For a blind user that is the worst outcome in the project.

It also cannot be built as-is: `cld2_generated_quad0122.cc` does not define
`kQuadChromeIndSize`/`kQuadChrome2IndSize` -- only the COMPACT `quadchrome`
files do -- so the tool would need a shim. **Do not re-propose it without a
reason the split cannot serve.**

### The two levers that remain, both already recorded
An **AAB** (what a Play listing needs anyway) and **dropping `armeabi-v7a`**.
The split gets most of what either would, without a decision that takes a phone
away, which is why it was done and they were not.

## THE AOSP SWEEP CAME BACK EMPTY, AND THAT IS THE RESULT (owner, 2026-09-09)
*"AOSP se sabhi chijen check kar lena aur bhi aapko IDs milenge jo AOSP se aap
fix kar sakte hain."* The current `TextToSpeechService.java` was read again in
full against our overrides. **No new defect.** Recorded so the same ground is
not walked a fourth time:

- **the override surface has not changed.** The five abstract methods are still
  `onIsLanguageAvailable`, `onGetLanguage`, `onLoadLanguage`, `onStop`,
  `onSynthesizeText`, and `onGetFeaturesForLanguage` is still the only
  non-abstract one, overridden by neither app. Nothing new to implement.
- **a dying CLIENT cannot park us.** `CallbackMap.onCallbackDied` calls
  `mSynthHandler.stopForApp(caller)`, which reaches `stopImpl()` ->
  `synthesisCallback.stop()` **and** `TextToSpeechService.this.onStop()`. So a
  screen reader crashing while we are parked on `syncLock` releases the wait.
- **an item flushed before it becomes current never enters `onSynthesizeText`
  at all.** `enqueueSpeechItem`'s runnable is
  `if (setCurrentSpeechItem(item)) { play(); remove(); } else { item.stop(); }`,
  and `setCurrentSpeechItem` re-checks `isFlushed` under `synchronized(this)`
  precisely to close that race. No wait is entered, so none can be orphaned.
- **`stopImpl`'s two branches are both safe for us.** With `mSynthesisCallback`
  null it calls `dispatchOnStop()` and NOT our `onStop` -- but that branch means
  `playImpl` never passed its `synchronized(this)` block, so `onSynthesizeText`
  was never called. With it non-null our `onStop` runs.
- **the params bundle matches.** `AudioOutputParams.createFromParamsBundle`
  reads `KEY_PARAM_AUDIO_ATTRIBUTES`, else `KEY_PARAM_STREAM`, plus
  `KEY_PARAM_SESSION_ID`, `KEY_PARAM_VOLUME` and `KEY_PARAM_PAN`. Our forwarded
  bundle removes exactly what AutoTTS removes and puts `volume` only when
  non-zero; session id and pan are forwarded by both apps.
- **the engine-facing overrides that run on binder threads are safe against the
  UI rebuilding the list.** `onGetVoices`, `onIsValidVoiceName`,
  `onIsLanguageAvailable` all go through `LangStore.availableLanguagesFor`,
  which walks by index under `synchronized(languages)` and returns a fresh list,
  so a rebuild on the main thread cannot throw
  `ConcurrentModificationException` into a binder call.
- **`onStop` takes only the `chunkQueue` leaf lock** before `syncLock`, so the
  release path cannot invert with any order already recorded.

**One thing was looked at hard and deliberately NOT changed.** `chunkCounter` is
the single field in the companion's settings block without `@Volatile`, and it
is written on the synthesis thread and incremented on the main looper. It is not
a defect: the generation guard added the same day returns from the posted
runnable **before** the increment, so the only remaining writer pair is ordered
by the binder transaction and by `Handler`'s own synchronized queue. Adding the
annotation would be the "defensive" justification rule 5 forbids, on a field
that is AutoTTS's plain static `K`. **Do not add it without a log.**

## A MODE THAT IS NOT A ROW GETS NO BUTTON EITHER (owner, 2026-09-09)
*"Google mod already hidden hai to uska button hidden kyon nahin hai ... jo uska
settings wala button hai."* A real defect, and the FAB was the one place the
hiding had not reached.

`modeRowSpecs` carries all five modes and `ModesScreen` skips `"google"` when it
draws the rows, so the list shows four. `MainScreen`'s settings FAB tested only
`readingMode != "none"` -- so with Google as the mode it read **"Google TTS
settings"** and opened the settings of a mode the list refuses to show. That is
not a corner case: `LangStore.loadMode` reads `auto_mode` with a default of
**3**, AutoTTS's own `c3.n.o`, so a fresh install on a device with Google TTS
starts in Google mode and that is the first thing the button says.

Both places now test **`HIDDEN_MODES`**, one `setOf("none", "google")` beside
`modeRowSpecs` in `TabViews.kt`, so the button cannot outlive the row it belongs
to. (`"none"` is not in `modeRowSpecs` at all, so the row loop is unchanged by
this; the FAB is where the set earns its place.)

**What it costs, stated rather than hidden:** Google mode's own "Select
preferred language" is unreachable while google is in force. That is the
owner's standing decision for this mode -- *"vahi Google wala selected rahana
chahie, bus visible nahin hona chahie"* -- and the same trade they already made
when they rejected drawing the google radio to keep that setting reachable.

**`ktimports` had a real gap that this found.** It collects module-wide
capitalised top-level `fun` names but not top-level `val` names, and
SCREAMING_SNAKE is Kotlin's own convention for a top-level constant -- so
`HIDDEN_MODES` looked exactly like an unimported type and failed the check.
`TOP_VAL_RE` is anchored at column 0 on purpose: `CONST_RE` matches every
`val X` including locals and class properties, and adding those module-wide
would suppress real findings rather than one false one. Negative-tested three
ways -- a genuinely unimported name is still reported, an INDENTED capitalised
val is still reported, and the clean tree passes.

## TWO CRASHES ON ANDROID 7, FOUND BY COMPILING AGAINST minSdk's OWN JAR (2026-09-10)
Owner: *"sirf TTS ki baat nahin kar raha hun, baki aur area ki baat kar raha hun
... har jagah check kar lijiye ... A to Z."* The sweep found two defects of the
same shape, and **both were total: the app had no voice at all on those
devices.**

**THE CHECK THAT FOUND THEM, because nothing else in this project could.**
`kotlin-typecheck.sh` compiles against **API 37**, the compileSdk, where every
call below resolves perfectly. Android Lint's `NewApi` is the usual answer and
needs Gradle and the SDK, which this container does not have. So the app was
compiled against **API 24's own `android.jar`** -- the minSdk -- and every
unresolved `android.*` symbol read straight out as "this does not exist on the
oldest phone we claim to support".

| symbol | API | what it was |
|---|---|---|
| **`AudioFocusRequest`** | **26** | **called BARE from `onCreate`, no guard, no try** |
| **`NotificationChannel`** | **26** | unguarded, behind the notification switch |
| `POST_NOTIFICATIONS` x3 | 33 | already guarded |
| `PackageInfoFlags` x2 | 33 | already guarded |
| `longVersionCode` | 28 | already guarded |
| `FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK` | 29 | used at `SDK_INT >= 34` |

**Why a `try/catch` would not have saved either one.** A missing class is a
`NoClassDefFoundError` -- an **Error**, not an Exception -- so
`catch (ex: Exception)` does not hold it. And `requestAudioFocus()` has no catch
at all: it is the fifth line of the service's `onCreate`, so on Android 7.0 and
7.1 **the TTS service died the moment it started**, every time, with nothing in
the app able to recover.

**AutoTTS does NOT have either defect, and the reason is the same both times.**
It makes the identical unguarded calls -- `new NotificationChannel(...)` at
`AutoTtsService:340` and the `AudioFocusRequest` builder in `l0()` -- but **the
lowest `Build.VERSION.SDK_INT` guard anywhere in its code is 28** (the full set
is 28, 30, 33, 34), so its own minSdk is at least 26 and API 24 is not a device
it claims. Ours is 24. That is the same "our refactor's bug, not AutoTTS's"
split as `releaseWaitWithoutSpeaking` and the double scan, so the guards are
ours to add and are **not** a rule 5 divergence -- on 26 and up both paths are
byte for byte what they were.

**The pre-26 audio-focus branch is AOSP's own equivalent, not a guess.**
`AudioAttributes.toVolumeStreamType` maps `USAGE_ASSISTANCE_ACCESSIBILITY` to
`STREAM_ACCESSIBILITY` -- and **that constant is itself API 26**, so below it
there is no accessibility stream in the audio policy at all. The right stream
for speech there is `STREAM_MUSIC`, which is what the TTS framework itself falls
back to: `Engine.DEFAULT_STREAM` is `STREAM_MUSIC`, and
`AudioOutputParams.createFromParamsBundle` builds exactly that pairing when no
attributes are supplied. `androidx.media`'s `AudioManagerCompat` does the same
branch and was **not** adopted -- it is not on the classpath, so it would be a
new dependency and APK bytes for ten lines.

**Each API-26 call now sits in its OWN method** (`requestAudioFocus26`,
`abandonAudioFocus26`, `createNotificationChannel26`). That is the shape
Android's own guidance and `PackageInfoCompat`'s `Api28Impl` both use: the
verifier only has to resolve the new class when that method is entered, and on
API 24 it never is.

### THE CHECK IS PERMANENT NOW: `tools/check/minsdk-api.sh`
In `check-all.sh`, between the C++ syntax check and the type-check. It reads
`minSdk` out of `app/build.gradle.kts`, fetches that platform's `android.jar`
(the package name comes from Google's own `repository2-3.xml` -- `platform-24_r02.zip`
is not derivable from the API level), compiles the app against it, and compares
every unresolved symbol to **`tools/check/minsdk-allowlist.txt`**.

**Why an allowlist rather than a pass/fail.** Every hit is a call above minSdk,
which is correct behind an `SDK_INT` guard and fatal without one -- and no
compiler can tell those apart. So each is reviewed once by a person and written
down with the guard that makes it safe. A hit that is **not** on the list fails
the run, which is exactly the moment to add a guard. A stale entry only warns,
because deleting code should never fail a run.

**Negative-tested, and the first attempt was a dud worth recording.** A
deliberate `areNotificationsEnabled()` was added expecting a failure and the
check stayed green -- **because that method is API 24**, so it resolved. The
real test is `android.os.VibrationEffect.createOneShot` (API 26), which the
check reports as `CheckVoiceData.kt:VibrationEffect` and exits 1; the clean tree
passes. **A checker you have not seen fail is not a checker.**

**One self-inflicted trap in that script, fixed before it shipped.** It first
unzipped the platform straight into `tools/.cache`, where the extracted file is
called `android.jar` -- landing on top of the **API 37** one
`kotlin-typecheck.sh` uses. The next `check-all` answered "no android.jar". It
extracts to a scratch dir and moves to `android-<min>.jar` now.

### Swept in the same pass and CLEAN, so do NOT re-check
- **`FileProvider` resolves both shared files.** Export writes
  `cacheDir/shared/easy_voice_settings.xml` and the log is
  `filesDir/logs/easy_voice.log`; `provider_paths.xml` declares
  `<cache-path name="shared" path="shared/">` and
  `<files-path name="logs" path="logs/">`. A mismatch here is an
  `IllegalArgumentException` on the Export and Share logs buttons.
- **`CheckVoiceData` and `GetSampleText` match AutoTTS and AOSP.** AutoTTS calls
  `setResult(1, ...)` and `setResult(0, ...)`; ours uses
  `CHECK_VOICE_DATA_PASS` and `LANG_AVAILABLE`, whose values **are** 1 and 0.
  The extras are `"availableVoices"`, `"sampleText"` and the incoming
  `"language"` -- the same literals `TextToSpeech.Engine` defines.
- **there is no `PendingIntent` anywhere**, so the API-31 `FLAG_IMMUTABLE`
  requirement cannot bite.
- **the battery button uses the SAFE intent.**
  `Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS` opens the system list
  and needs no permission. It is deliberately **not**
  `ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`, which needs
  `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` and is a restricted Play-policy
  permission -- on the paid listing the owner is preparing, that matters.
- **every `!!` in the app is safe.** There are three: two in `EngineFinder` sit
  inside `if (probe[0] != null)`, and the third is `ex.message!!` in `onCreate`,
  which is **AutoTTS's own** -- its `onCreate` does
  `Objects.requireNonNull(exception2.getMessage())`, which is what `!!`
  compiles to. It is also unreachable: `startForegroundIfPossible` catches
  `Exception` internally, so the outer catch has nothing to receive. Parity plus
  unreachable, so it stays.
- **`onBindingDied` / `onNullBinding` are API 26 and 28 and need no guard.**
  They are `ServiceConnection` OVERRIDES; a class carrying a method the base
  class does not declare is simply a method nobody calls. Only a CALL to a
  missing API can throw.

## THE LANGUAGE LIST HAD NO WRITER LOCK, AND FIVE OTHER CORNERS (owner, 2026-09-10)
*"donon bug aapane Chhod Diye hain ... vah Sahi tarike se research Karke fix kar
dena chahie thi na aapko"*, and then *"Puri App Ka ek-ek Kona chhan maro ...
properly Koi bag Nahin Rahana chahie"*. The owner was right about both left-behind
items, and the sweep that followed found a crash nobody had reported.

**THE BIGGEST FIND: every writer of `LangStore.languages` skipped the monitor that
every reader takes.** The app is ONE process, so that static list is reached from
three kinds of thread -- the settings screens on the main thread, the SYNTHESIS
thread inside `reloadLanguagesIfMissing`, and Android's BINDER threads inside
`onGetVoices` / `onIsValidVoiceName` / `onIsLanguageAvailable`. The engine-facing
three go through `availableLanguagesFor`, which walks by index under
`synchronized(languages)` *precisely so a rebuild cannot tear the walk* -- and yet
`languages.clear()` followed by `addAll(...)` was written bare at **all five**
rebuild sites (`LangStore.loadLanguages`, `EngineFinder.finalizeScan`,
`LanguagesActivity`, `ModesScreen`, `LanguagesVoicesViews`). A `clear()` landing
between a reader's `size` read and its `get()` is not a lost element, it is
**IndexOutOfBoundsException thrown inside a binder call**.

**AutoTTS does not have this hole at the site that matters, and that is what makes
it ours to fix.** Its `P()` is

    synchronized (c3.n.c) { if (!w || c.isEmpty()) { log(...); e0(); } }

i.e. it holds the list monitor **across** the loader, so `e0`'s own clear and
refill happen inside it. Ours deliberately moved the reload out of that monitor --
holding it across `loadLanguages` would take `this` while holding `languages`,
and `onLoadLanguage` takes them the other way round, which is the ABBA this file
already records. So the lock was dropped for a good reason and never put back
where it belonged. Same split as `releaseWaitWithoutSpeaking` and the double scan.

The fix is **`LangStore.replaceAll(fresh)`**: the new list is BUILT outside the
monitor and only the two-statement swap is inside it, so `languages` stays the
**leaf lock** every order recorded in this project depends on -- nothing is ever
taken while holding it. All five sites go through it and there is now exactly one
`languages.clear()` in the tree. The reader side was closed too: the direct
indexed walks in `voiceLanguageEngines`, `ModesScreen` and `LanguagesActivity`
take the monitor, and the four `getOrNull` reads go through **`LangStore.entryAt`**
-- `getOrNull` is `if (index in 0..lastIndex) get(index) else null`, two steps
against a list another thread can replace between them, so it can throw rather
than answer null.

### The two the owner named
**1. `ex.message!!` in the service's `onCreate` -- FIXED, owner override.** I had
answered it with "unreachable" and that was the wrong shape of answer. The
reachability argument is real -- `startForegroundIfPossible()` wraps its whole
body in its own `catch (Exception)`, so only an Error (which `catch (ex: Exception)`
does not hold anyway) or a throw from `EasyVoiceLogger` inside that inner catch
gets here -- but it is not a licence to leave a landmine on the one path that
exists to REPORT a failure. A great many exceptions carry a **null** message (a
bare `SecurityException`, a framework NPE, any `throw Foo()`), and `!!` would then
throw a second exception out of `onCreate` and take the TTS service down: the
phone with no voice at all, which is this project's worst outcome.
It is `?: ex.toString()` now, and that is not even a shape AutoTTS lacks --
**AutoTTS's own `p0()` writes `c3.n.a.d("AutoTTS", exception2.getMessage())` at
the identical site with no `requireNonNull`.** Only its `onCreate` spells it the
crashing way. We took the other spelling.

(Noted while reading it and deliberately NOT copied: on the exception path
AutoTTS's `onCreate` **skips `l0()`**, its audio-focus request -- the CFR block
structure puts `this.l0()` inside the block the catch breaks out of. Ours calls
`requestAudioFocus()` unconditionally, which is right; mirroring that one would
mute the app after a notification failure.)

**2. `chunkCounter` is `@Volatile` now -- FIXED, owner override.** This file used
to say "do not add it without a log" on rule 5's "defensive" prohibition. The read
is genuinely cross-thread: `speakChunk` WRITES it (`if (currentChunk == 1)
chunkCounter = 1`) and READS it into `expectedId`, and `speakChunk` is reached
from all three threads -- the synthesis thread for the first chunk, the MAIN
LOOPER from `onDone`'s post (which also does `chunkCounter++`), and a BINDER
thread when `onDone` finds the queue empty and calls `speakChunk(false)` directly.
The main-looper write and the binder-thread read are ordered only by the two
binder round trips that happen to sit between them, which is a practical barrier
and not a guarantee. A stale read builds the wrong `expectedId` and every callback
for that chunk is then dropped by the id guard -- **silence, not a crash**, which
is the failure this app can least afford to leave to luck.

### Four more found in the same sweep
- **the first engine's `TextToSpeech` constructor was unguarded.** `EngineFinder`
  had a `liveIndex` flag choosing between a guarded and an unguarded call, exactly
  as AutoTTS does -- its `D0()` (`NewSettingsActivity:446`) and `C0()` (`:402`) are
  bare while the "(2)" step at `:245` is wrapped. The constructor does real work
  (reads `Settings.Secure`, resolves the engine, calls `bindService`), so it can
  throw when that engine is mid-update -- and it runs on the MAIN THREAD from
  `onCreate`, so a throw is not a failed scan, it is **the app crashing the moment
  a blind user opens it**. Covered by the owner's standing 2026-09-09 override for
  the scan. The recovery is not invented: it is byte for byte what the "(2)"
  branch and the 30 s timeout already do -- mark the engine failed, advance the
  walk. `liveIndex` is gone with the last unguarded call.
- **the probe client leaked on its own failure path.** `probe[0]!!.shutdown()` ran
  only on SUCCESS, so an ERROR left a binding to our own service open for the life
  of the process. Both paths release it now, and the constructor is guarded too.
  Shutting a client down inside its own `onInit` is AOSP's own mechanism --
  `shutdown()` while still connecting calls `mConnectingServiceConnection.disconnect()`.
- **`GetSampleText` could be crashed by any app on the phone.** `isO3Language`
  **throws** `MissingResourceException` for a language with no three-letter code --
  measured here, not assumed: `Locale("xx").isO3Language` is *"Couldn't find
  3-letter language code for xx"*. That activity is **exported** and its
  `"language"` extra comes from another app. AutoTTS reads it bare as well. The
  fallback is the sentence the screen already shows for a language it has no
  sample for, so a valid code behaves exactly as before. (The three other
  `isO3Language` reads in the app -- `localeIso3`, `localeMatches` and the
  bypass-prefix parse -- were each checked and are each already inside a
  `try`/`catch`.)
- **Export could do nothing at all, silently.** If `exportSettingsFile()` threw an
  `IOException` (no space on the cache partition is the realistic one) the click
  fell out with no Toast. The "file not found" branch beside it already speaks. A
  sighted user would at least see nothing happen; a blind user cannot tell that
  from the app having frozen.

### Checked in the same sweep and CLEAN, so do NOT re-sweep
- **every `chunks[0]` in `onSynthesizeText` is guarded** by an `isEmpty()`/
  `isNotEmpty()` test, and the whole segmentation block sits in a `try`/`catch`
  that ends with `startAndFinish(callback)`, so it cannot park the wait.
- **every `startActivity` that can miss** is guarded -- TTS settings, battery,
  Play Store (which falls back to the web URL and then a Toast), and the import
  picker. The rest target our own activities or a chooser, which always resolve.
- **import/export parsing is guarded to the right depth**: `importSettingsXml`
  catches `Throwable` and never commits a partial edit (the `editor.clear()` is
  discarded with it), `getReferencedEnginePackagesFromXml` catches `Exception` and
  answers an empty set, and `handleImportedSettingsFile` Toasts on both.
- **`onDestroy`'s engine-pool loop has no try and does not need one** --
  `EngineWrapper.shutdown()` submits to `stopExec`, whose queue is unbounded so
  `execute` cannot be rejected, and the real `tts!!.shutdown()` is inside the
  worker's own catch. AutoTTS's loop is bare in the same way.
- **`CheckVoiceData` returning an empty voice list in a cold process is PARITY,
  not our bug.** It is `LangStore.availableLanguagesFor(null, true)` against
  AutoTTS's `n.i(null, true)`, and `c3.n.i` was read: it walks `c3.n.c` and loads
  nothing either. Both apps answer from whatever the process happens to hold. Do
  not "fix" this one -- it is the engine-facing surface, where the UI carve-out
  does not reach.
- **`synthesizeToFile` still cannot be told apart from `speak`, and that is
  measured rather than assumed.** `SynthesisCallback` exposes only
  `getMaxBufferSize`, `hasStarted` and `hasFinished`; nothing in the public API
  names the callback type, and `request.params` carries no marker. So there is no
  non-guesswork way to answer it differently, rule 6 applies, and AutoTTS is
  identical. It cannot hang -- the downstream `onDone` releases the wait exactly
  as it does for `speak()`.

**One thing for the OWNER to decide, not for me:** the manifest declares
**`QUERY_ALL_PACKAGES`**, which is a Play-**restricted** permission needing a
declaration form on a listing. The `<queries>` block already covers engine
discovery; what still needs the permission is `isPackageInstalled(pkg)` on an
arbitrary package named by an imported settings file. It is left exactly as it is
-- removing it could break that check on some devices -- but on the paid listing
being prepared it is worth knowing about before submission.

## THE WHOLE APP, NOT JUST THE TTS PATH (owner, 2026-09-10)
*"Sare API aur sab kuchh puri application ki, TTS ke alava bhi aur bhi services
hai ... kuchh chij missing ho sakti hai ... jo bhi dependency library jo bhi
jarurat hai vah completely research karke sab kuchh implement karo ... bilkul
stable banaa do."* Everything below was found by sweeping the parts of the app
that are NOT the synthesis path.

**A STATIC HELD THE WHOLE SERVICE ALIVE FOR THE LIFE OF THE PROCESS.** The
companion carried `lateinit var appCtx: Context`, assigned `appCtx = this` on the
first line of `onCreate` -- a **static** reference to the Service object, so it
survived `onDestroy` and every restart, and the Service holds the engine pool,
the chunk queue and every `TextToSpeech` client through it.

**It had NO reader.** Swept the whole app: the only other `appCtx` is
`SharedPrefsManager`'s own private instance field. So this is the vestige of
AutoTTS's `this.h = this` -- and the difference is the entire defect. AutoTTS's
`h` is an **instance** field, so it dies with the instance, and it exists because
`c3.l0.b(this.h)` reads it: the licence gate, which is this project's standing
carve-out and was never ported. Ours was moved into the companion during the
port, which turned a field that dies into one that cannot. Our refactor's bug,
nothing reads it, deleted.

**`EasyVoiceLogger` had two smaller versions of the same thing.**
`private var appContext: Context?` was assigned in `init` and read nowhere --
gone. And **`init` was not synchronized** while `writeLine` is: `logFile` is a
plain field written on the MAIN thread and read from the synthesis and binder
threads, so there was no happens-before edge between them. The failure it allows
is the quiet kind -- a reader seeing `null` for ever and the log file silently
never written, on precisely the device whose owner is trying to report a bug.
`@Synchronized` on `init` is the same monitor `writeLine` already takes, so it
costs one uncontended lock once per process.

**The `\p{M}` regex was compiled about two thousand times per app open.**
`sortKey` is called from inside a `Collator` comparator over every scanned
language (~137 of them, so ~970 comparisons x 2 calls), and both copies of it --
`LangStore.sortKey` and `EngineFinder.finalizeScan`'s local one -- wrote
`"\p{M}".toRegex()` **inside** the function, so each call compiled a fresh
`Pattern`. Hoisted to one `COMBINING_MARKS` per file. This is a speed change and
not a behaviour one, and AutoTTS pays the same cost for the same reason
(`String.replaceAll` compiles per call in `c3.n.g`), so the output is identical
either way.

### The manifest was missing two things, and one of them is a real Android 12 gap
- **`android:dataExtractionRules`** (API 31). `allowBackup="false"` is AutoTTS's
  and stays -- it stops CLOUD backup -- but from Android 12 it is **not** the
  whole story: device-to-device transfer copies the app's files anyway unless a
  rules file says otherwise, and without the file the behaviour is whatever the
  platform defaults to rather than a decision. `res/xml/data_extraction_rules.xml`
  excludes **`filesDir/logs`** from both paths, and only that: the log and its
  three rotations are up to 2 MB each, describe the phone they were written on,
  and moving them to a new phone carries stale evidence and nothing else.
  **Everything else is deliberately left transferable** -- the language list, the
  per-language voice, speed, pitch and volume, the mode and the Advanced flags
  are exactly what a blind user would otherwise set up again by hand, and the app
  reconciles them against a fresh engine scan on every open, so a configuration
  naming an engine the new phone lacks is repaired rather than broken. (`cacheDir`
  is never backed up by the platform, so the exported settings copy under
  `cache/shared` needs no rule.)
- **`android:appCategory="accessibility"`**. Verified at the source rather than
  recalled: `CATEGORY_ACCESSIBILITY` is in API 37's own `ApplicationInfo`
  (`javap` over the platform jar), and the manifest documentation lists
  `accessibility` as *"apps that are primarily accessibility apps, such as
  screen-readers"*, which is what this is. An older platform that does not know
  the value parses it to `CATEGORY_UNDEFINED`, so it cannot break anything below.

**An XML comment may not contain two consecutive hyphens, and `xmlcheck.py`
caught it** -- the first draft of the rules file used `--` as punctuation and
failed the check at the exact line and column. Worth knowing before writing the
next commented resource; the file now says so itself.

### The dependency audit: everything current but one, and the BOM absorbed a pin
Every declared coordinate was re-resolved against its own `maven-metadata.xml`.
Current: `core-ktx` 1.19.0, `core-splashscreen` 1.2.0, `lifecycle-runtime-ktx`
2.11.0, `activity-compose` 1.13.0, `material3.adaptive` 1.3.0, `test.ext:junit`
1.3.0, `test:runner` 1.7.0, Kotlin 2.4.20, coroutines 1.11.0.

**`compose-bom` 2026.08.00 -> 2026.09.00**, and it is a patch move rather than a
feature one: `compose.ui` and `compose.foundation` go 1.12.0 -> 1.12.1 and
**material3 stays at 1.4.0**. That last part is what makes it safe here -- every
component this file argues with (`ExposedDropdownMenuBox` and its required
`@OptIn`, `PrimaryTabRow`, `FilterChip`, `Slider`'s `sliderSemantics`) is
material3, and material3 does not move.

**`ui-test-junit4-accessibility` lost its hand-written version**, exactly as the
note beside it said to do "if a future BOM starts managing it". Read out of the
BOM's own pom: 2026.09.00 lists it at **1.12.1** beside `ui` and `ui-android`, so
the whole `androidx.compose.ui` line is managed from one place again. A
hand-pinned artifact in a group the BOM manages is a version skew waiting for the
next BOM bump.

**`concurrent-futures` 1.2.0 is NOT bumped to 1.3.0, and that is deliberate.** It
is declared for one reason only -- to make the app's graph agree with the
androidTest graph, which asks for 1.2.0 through `androidx.test:core:1.7.0` -- and
that alignment is what took four red builds to find. Nothing in the app imports
it. Moving it would re-open the exact `strictly` conflict it exists to close, for
no gain. Leave it until the test graph moves.

### Researched, NOT adopted, and NOT missing
- **No library is missing.** The one candidate with a real case is a **baseline
  profile**: `androidx.profileinstaller` is already on the classpath
  transitively, but without a generated profile it does nothing, and generating
  one needs a Macrobenchmark module and a real device -- neither of which exists
  here. It would help cold start, which the owner cares about. **Owner's call;
  say the word and it is a new module plus a device run.**
- **Direct Boot.** A TTS engine that could speak at the lock screen before the
  first unlock would need `android:directBootAware="true"` AND its settings moved
  to device-protected storage, because `SharedPreferences` is unreadable in that
  window. That is a storage change, which rule 5 governs, and AutoTTS does not do
  it. Flagged, not done.
- **`android:localeConfig`** is for per-app language choice and the app ships one
  language, so declaring it would be declaring nothing.
  **`usesCleartextTraffic`** is already false by default at this targetSdk and the
  app makes no network call at all. **`allowAudioPlaybackCapture`** governs other
  apps capturing OUR audio, and we never produce any.

### Two things for the OWNER, both stated rather than changed
- **`WAKE_LOCK` is declared and never used.** Swept: there is no `WakeLock`
  anywhere in the app. AutoTTS declares it too, so removing it is "remove
  something AutoTTS has", which rule 5 names -- but on the paid listing being
  prepared, a permission with no use is a permission a reviewer and a user can
  both see. One word and it goes.
- **Six GitHub Actions are on deprecated majors**, and this one has a deadline
  rather than a preference behind it. `softprops/action-gh-release`'s own README
  says v2 *"is no longer maintained or supported. It uses the Node 20 runtime
  deprecated by GitHub Actions"* -- and that is the step that publishes the APK.

      actions/checkout                v4  ->  v7
      actions/setup-java              v4  ->  v6
      actions/upload-artifact         v4  ->  v7
      gradle/actions/setup-gradle     v3  ->  v6   (gradle-version input survives)
      android-actions/setup-android   v3  ->  v4
      softprops/action-gh-release     v2  ->  v3   (inputs identical, token defaults)
      reactivecircus/android-emulator-runner  v2   ALREADY CURRENT (2.38.0)

  Checked at each action's own README rather than guessed: `gradle-version` and
  `cache-disabled` survive to v6; `tag_name`/`name`/`body`/`files`/`make_latest`/
  `fail_on_unmatched_files` are unchanged in v3 and `token` defaults to
  `github.token`. **The one that cannot be verified from a README is
  `setup-android` v4** -- our NDK step calls
  `$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager` by absolute path, and v4's
  README documents `cmdline-tools/16.0/bin` on the PATH instead, so the `latest`
  symlink may not be there. If that bump is made, make the call resolve
  `sdkmanager` from PATH first and fall back to the absolute path, so it works
  under both.

  **These are deliberately NOT in the same commit as the code changes**, per the
  lesson this file already records: when a fix cannot be tested locally and the
  only test costs thirteen minutes, do not stack it with anything else.

## WHY IT KEPT GOING RED, ANSWERED WITH THE WHOLE HISTORY (owner, 2026-09-10)
*"Kyon baar-baar fail ho raha hai ... properly dekh lo yaar, sab kuchh fix kar hi
do."* Counted rather than guessed, over every run since 849:

    849 850 851 852 853 854 856 857 866 868 869
    accessibility=failure   build=success      <- ELEVEN times out of eleven

**THE `build` JOB HAS NEVER FAILED. Not once.** Every red run in this project's
recent history is the `accessibility` job, and **the APK was published every
single time**. That is the first thing to say to the owner when a run goes red,
and it is why the classifier below exists.

**And not one of those eleven was a real accessibility regression:**

| runs | what it actually was | fixed by |
|---|---|---|
| 795-797 | the emulator could not create its userdata partition (a `FATAL` line a thousand adb errors below the noise) | a disk-cleanup step |
| 849-857 | a genuine dependency conflict between the app and androidTest graphs | build 858 |
| 866 | a transient repository failure -- the SAME run's build job resolved the identical classpath | `gradle-retry.sh` |
| 868-869 | `set -o pipefail` in a fragment the emulator action runs with **dash** | `gradle-retry.sh` as a FILE |

### Why THAT job and not the other one, structurally
It carries five independent failure sources the build job does not have:

| | build | accessibility |
|---|---|---|
| dependency graph | app only | app + androidTest + ATF + guava -- the one that conflicted |
| network | deps | deps + emulator system image + platform |
| disk | modest | a **7.2 GB** emulator partition |
| shell | bash (`run:`) | **dash, one line at a time** (a third-party action's `script:`) |
| moving parts | none | KVM, emulator boot, adb, ATF |

Each of those five has bitten at least once. **The job is not flaky because the
app is fragile; it is flaky because it has five more things that can go wrong.**

### What was hardened, beyond the two fixes already recorded
**The accessibility job's NDK step used to PASS when the NDK was not there.** It
looped three times and then simply ended -- **no check that the directory
existed, and no non-zero exit if all three attempts failed** -- so a failed or
corrupt download left the step GREEN and the real failure surfaced three minutes
later, inside the emulator, as an opaque CMake or Gradle error with nothing
pointing back. The build job has always verified and errored; this is now that,
byte for byte, cache-clearing between attempts included.

**`tools/ci/why-failed.sh` runs on `if: failure()` in BOTH jobs** and says which
KIND of failure it was in one line at the top: a repository flake, the emulator,
a compile error, a real accessibility check, or "gradle never ran, look before
it". Tested against the exact shapes of all four historical failures. This is the
`FATAL`-line lesson made automatic: twice a red X cost a wrong diagnosis because
infrastructure and a real defect look identical from outside.

### THE NEW CHECKER, AND THE TRAP INSIDE IT
`tools/check/workflow-shell.sh` extracts every shell fragment from `build.yml`
and parses it **with the shell that will actually run it** -- `run:` under bash,
an action's `script:` under **dash**.

**A syntax check alone is USELESS here, and it was written that way first.**
`dash -n` parses all of these happily:

    set -o pipefail          <- the exact line that broke 868 and 869
    if [[ 1 == 1 ]]; then
    f() { local n=0; }

They are syntactically fine and fail at **runtime**. So the first version of
this checker reported **ok** for precisely the bug it was written to prevent.
It carries a **bashism blacklist** for `sh` fragments as well, and the negative
test puts `set -o pipefail` back into the emulator script and requires exit 1.
(`local` is deliberately NOT on the list: dash implements it, and Ubuntu's
`/bin/sh` IS dash.)

**Wired into `check-all.sh`**, so a bashism in a `script:` costs milliseconds
instead of two thirteen-minute runs.

### Considered and NOT done, stated so it is not re-litigated
**`cache-disabled: true` stays**, for now. It is the biggest remaining exposure
-- every run re-downloads the whole buildscript classpath, which is exactly what
flaked in 866 -- and enabling `gradle/actions`' cache would remove the CAUSE
rather than retrying the symptom, and make the job faster. It is not done in the
same breath as the retry because that would stack two untestable CI changes,
which is the mistake this file records three times. **One green run on the retry
first; then it is one line.**

## THE UI IS ALREADY 100% COMPOSE, AND IT IS ENFORCED NOW (owner, 2026-09-10)
*"Full Jetpack Compose user interface chahie, koi XML Android view ya fir kuchh
bhi nahin ... sari library aur sabhi chijon ke saath."* Measured before
answering, and the measurement is the answer.

    res/layout/                        DOES NOT EXIST
    setContentView / findViewById      ZERO
    LayoutInflater                     ZERO
    AndroidView( / ComposeView         ZERO
    android.view.*                     ZERO, app and test sources both
    android.widget.*                   Toast ONLY -- five references

So there is **no Android View anywhere in this app.** The 2026-08-26 migration
finished the job and nothing has crept back.

**GOOGLE'S OWN CURRENT GUIDANCE NOW CONFIRMS THE ICON DECISION, which this file
had previously argued from first principles.** The Compose images page says of
`androidx.compose.material:material-icons`:

> *"this artifact is **no longer maintained or recommended** for use in your
> apps, as it contains an older look and feel and can also increase the build
> time of your apps **significantly**. Instead, we recommend using Google Font
> Icons and download the XML file from the Android Tab"*

That is **exactly what this app does**: 22 vector drawables taken verbatim from
Google's own icon source, drawn with `Icon(painterResource(...))`. Moving them to
`material-icons-extended` would be moving AGAINST current guidance and adding a
large unmaintained dependency. **Do not propose it again**; the earlier note
rejecting it is now backed by Google's own words rather than by our reasoning.

### It is a RULE now, not a fact that happens to hold
`invariants.sh` **#26** fails the build on a `res/layout*/` directory, on any
`android.view.*`, on `android.widget.*` other than `Toast`, and on
`setContentView` / `findViewById` / `LayoutInflater` / `AndroidView(` /
`ComposeView`. Comments are stripped first, because `ComposeTheme.kt` and
`MainActivity.kt` explain in prose what the delegate writes into
`info.className` and the words "android.widget.Button" in a sentence are not a
widget. **Both cases negative-tested** in `selftest.sh`: an added `AndroidView`,
and an added `res/layout/leak.xml`. Written up as `docs/INVARIANTS.md` #28 with
the full table of what XML remains and why each piece cannot be Compose.

`selftest.sh`'s `run` helper also had to learn to `git clean` `res/` -- ADDING a
file is a way to break a rule, and `git checkout` does not remove an untracked
one, so without it every later check ran against a tree that still had a View
layout in it.

### AND THE SELFTEST CAUGHT TWO CHECKS THAT HAD GONE TO SLEEP
This is the part worth keeping. Running it reported **"#1 NOT CAUGHT"** and
**"#8 NOT CAUGHT"** -- two rules that had been reporting `ok` while checking
nothing at all.

- **#1 was blinded BY MY OWN CHANGE EARLIER THE SAME DAY.** It scanned
  `grep -rln "languages.addAll"` for rebuild sites -- and `LangStore.replaceAll`
  had just made every one of those sites call `replaceAll(...)` instead, leaving
  the single remaining `addAll` inside `LangStore.kt`, which the loop explicitly
  skips. So it scanned an empty list and passed. It matches **both spellings**
  now. **A refactor can blind a checker without touching the checker**, and the
  only thing that finds it is running the negative tests.
- **#8's negative test had gone stale**, from the 2026-09-03 dropdown migration:
  it patched `DropdownMenu(expanded = expanded` in `VoiceScreen`, which became
  `ExposedDropdownMenu` and stopped matching, so the test broke nothing and there
  was nothing to catch. The CHECK was fine -- `DropdownMenu(` is a substring of
  `ExposedDropdownMenu(` -- so only the test moved, to `ConfigurationScreen`.

**`selftest.sh` now reports EVERY CHECK FIRES over all 17 negative tests.** Run
it after touching `invariants.sh` OR after any refactor that renames something a
check greps for.

## BUILD 866 WENT RED ON THE REPOSITORIES, NOT ON ANYTHING WE WROTE (2026-09-10)
The `build` job PASSED and **published `EasyVoice-866.apk`**. Only
`accessibility` failed, and the log names the cause plainly enough that it is
worth writing down, because it looks exactly like a real dependency break:

    > Could not find org.ow2.asm:asm-commons:9.9.
    > Could not find org.jdom:jdom2:2.0.6.
    > Could not find org.jetbrains.kotlin:kotlin-stdlib:2.4.0.
    > Could not find com.google.code.gson:gson:2.11.0.
    > Could not find com.google.code.findbugs:jsr305:3.0.2.
      Required by: ... com.android.tools.build:gradle:9.4.0 > jetifier-processor

Those are **AGP 9.4.0's own transitive dependencies**, so the obvious reading is
"the AGP bump is broken, revert it". **Three measurements say otherwise, and this
project has already reverted a correct change once on exactly that kind of
reading:**
1. **all five artifacts answer HTTP 200 from Maven Central** -- checked one by
   one, at the exact URLs the failure listed;
2. **build 864 ran the identical AGP 9.4.0 and Gradle 9.7.1 and both jobs
   passed**, accessibility included;
3. **the SAME RUN's `build` job resolved the same buildscript classpath and
   published the APK.** Two jobs, two runners, one commit, opposite outcomes.

So it is an infrastructure failure wearing a dependency failure's costume --
the same shape as runs 795-797, which looked like KVM and were a `FATAL` disk
line. **Before reverting anything on a "Could not find", check whether the other
job in the same run resolved it.**

### The retry that stops it costing a run, and why it is not a blunt one
`cache-disabled: true` means every run re-downloads the whole buildscript
classpath, so this workflow is maximally exposed to a momentary repository
failure -- and it already retries the NDK install **five** times and the CLD2
clone **three** times for exactly this reason. Gradle was the one step with no
retry at all.

`run_gradle` wraps both invocations and **retries ONLY a transient failure**,
matched against a pattern of resolution and network signatures
(`Could not find|resolve|GET|HEAD|download`, `Connection reset`,
`Read timed out`, `502/503/504`). **A real compile error exits on the FIRST
attempt**, so a genuine break still fails fast and says which line rather than
costing three times the wall clock and three copies of the same error.

**Tested locally against a stubbed `gradle` in all three shapes:**

    transient, then success   retries twice, exits 0
    real compile error        exits 1 IMMEDIATELY, no retry warning
    transient for ever        exits 1 after three attempts

### AND IT BROKE BUILD 868 ANYWAY, BECAUSE THE LOGIC WAS TESTED AND THE SHELL WAS NOT
The first version wrote the function **inline in `build.yml`**. The `build` job
was fine. The `accessibility` job failed instantly:

    /usr/bin/sh -c set -o pipefail
    /usr/bin/sh: 1: set: Illegal option -o pipefail

**`reactivecircus/android-emulator-runner` runs its `script:` input with
`/usr/bin/sh` -- dash, not bash -- ONE LINE AT A TIME.** `set -o pipefail` is not
POSIX and dash rejects it, and a multi-line shell FUNCTION cannot survive being
executed line by line either. The step's previous two lines happened to work
because each was self-contained.

**The fix is `tools/ci/gradle-retry.sh`**, a file with its own `#!/usr/bin/env
bash` shebang. Whatever shell the caller uses, the script runs under bash, and
each caller's line stays self-contained:

    build:          bash tools/ci/gradle-retry.sh assembleRelease -PevAbiSplit
    accessibility:  bash tools/ci/gradle-retry.sh connectedDebugAndroidTest

It is also now testable directly (`EV_GRADLE=<stub> tools/ci/gradle-retry.sh ...`),
which inline YAML never was, and the re-test added a **fourth** case: invoking it
**from dash**, which is the thing that actually failed.

**THE LESSON, and it is a sharper version of one this file already carries:** the
local test proved the ALGORITHM. It did not prove the ENVIRONMENT. When a CI
change runs inside a third-party action, check which shell that action gives it
before believing a local run. `run:` on a GitHub runner is `bash`; an action's
`script:` input is whatever that action chose.

## SIX GITHUB ACTIONS WERE ON A DEPRECATED RUNTIME (2026-09-10)
This is a **deadline**, not a preference. `softprops/action-gh-release`'s own
README says v2 *"is no longer maintained or supported. It uses the Node 20
runtime deprecated by GitHub Actions"* -- and that is the step that **publishes
the APK**. When GitHub switches that runtime off, the build stops producing
anything the owner can install. `checkout`, `setup-java` and `upload-artifact`
were on the same runtime.

    actions/checkout                v4 -> v7
    actions/setup-java              v4 -> v6
    actions/upload-artifact         v4 -> v7
    gradle/actions/setup-gradle     v3 -> v6
    android-actions/setup-android   v3 -> v4
    softprops/action-gh-release     v2 -> v3
    reactivecircus/android-emulator-runner   v2   ALREADY CURRENT (2.38.0)

**Every input this workflow passes was checked against that action's own README
before bumping**, rather than assumed -- this file already records what an
unverified CI change costs:
- `setup-gradle`: `gradle-version` and `cache-disabled` both survive to v6, and
  the docs still say the downloaded version is added to the PATH for later
  `run:` steps, which is exactly how the `gradle assembleRelease` step uses it;
- `action-gh-release`: `tag_name`, `name`, `body`, `files`, `make_latest` and
  `fail_on_unmatched_files` are unchanged in v3, and `token` **defaults to
  `github.token`**, so the existing `GITHUB_TOKEN` env keeps working;
- `checkout`: this workflow passes it no inputs at all;
- `setup-java`: `java-version` and `distribution` are still core inputs;
- `upload-artifact`: `name`, `path`, `retention-days`, `if-no-files-found` all
  unchanged.

**THE ONE REAL RISK WAS `setup-android` v4, AND IT IS CLOSED RATHER THAN
GAMBLED.** Both NDK steps called `$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager`
by absolute path, and v4's README documents **`cmdline-tools/16.0/bin`** on the
PATH instead -- so the `latest` symlink may simply not be there. Both steps now
resolve it the version-independent way:

    SDKMANAGER="$(command -v sdkmanager || true)"
    [ -n "$SDKMANAGER" ] || SDKMANAGER="$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager"

PATH first, the old absolute path as the fallback -- so it works under v3 and v4
alike, and if `sdkmanager` is not on the PATH at all the behaviour is exactly
what it was. The step also prints which one it picked, so the next failure says
so itself.

**Its own commit**, for the reason recorded three times in this file: a change
testable only by a thirteen-minute CI run must not be stacked with another. The
Gradle and AGP move is in the commit before it, so a red run's failing STEP name
separates them -- `Setup Gradle` is one, `Install NDK and CMake` and
`Publish APK to GitHub Release` are the other.

## THE FIRST ENGINE'S CONSTRUCTOR KILLED THE SERVICE, AND A FAILED BIND LEAKED (2026-09-10)
Two more from the engine pool, both in the class the owner has overridden rule 5
for, and the first one is the same defect found in `EngineFinder` earlier the
same day -- in the other of the two places it appears.

**`initAllEngines` constructed the FIRST engine's `TextToSpeech` bare.**
`restoreEngine`'s own comment claimed *"initAllEngines already guards the
identical call for the identical reason"* -- **it did not.** Only
`EngineInitListener`'s walk over engines 2..N was wrapped; engine ONE was
constructed with nothing around it, right in `initAllEngines`.

That constructor does real work -- it reads `Settings.Secure`, resolves the
engine and calls `bindService` -- so it throws when that engine is mid-update,
which is exactly what a Play Store update of a TTS engine produces, because the
package is briefly unresolvable. And `initAllEngines` is the last line of the
**service's `onCreate`**, which does not wrap it either. So the throw killed
`onCreate`: **the TTS service dead at startup, the phone with no voice at all,
and `START_STICKY` restarting it straight back into the same state.** The one
failure this app can least afford, on the one engine it always touches first.

The recovery is **not invented** -- it is byte for byte the loop
`EngineInitListener` already runs for every other engine: log, step
`initializingIndex`, try the next. On the happy path it is what the old code did,
statement for statement. A wrapper left in the pool at state 0 is inert:
`loadVoice*` only ever matches `state == 2`, and `onEngineProcessBack` only acts
on `state == -1` -- which is already true of the listener's own failure path.
The misleading comment in `restoreEngine` is corrected rather than left to
mislead the next reader.

**A FAILED `bindService` STILL LEAVES THE CONNECTION REGISTERED**, and only
`unbindService` takes it back. Read from AOSP rather than from the return
value's wording: `ContextImpl.bindServiceCommon` calls
`mPackageInfo.getServiceDispatcher(conn, ...)` -- which registers it -- **BEFORE**
it asks the ActivityManager, and when the AM answers 0 (the `false` we see) that
registration is **not** undone; `unbindService` is what calls
`forgetServiceDispatcher`.

So every failed bind leaked one dispatcher entry, and **this is not a one-shot
path**: `onBindingDied` does unbind-then-bind, so a flaky engine leaks one per
cycle on a `START_STICKY` service that can run for days, until the context is
destroyed and logcat says *"ServiceConnection ... leaked"*. One
`unbindService(conn)` on the false branch closes it.

### Swept in the same pass and CLEAN, so do NOT re-sweep
- **the Languages screen's collection arithmetic is CORRECT**, and it is worth
  saying because getting it wrong makes every announced position wrong for a
  blind user and nothing on screen looks different. `collectionInfo` publishes
  `visibleIdx.size`, which excludes the two `SectionHeader` `item {}` entries;
  the region group takes positions `0 .. regionIdx.size-1` and the other group
  `regionIdx.size + position`; and the two are a PARTITION of `visibleIdx`, so
  the positions run continuously to `visibleIdx.size - 1` with no gap and no
  overlap.
- **`ConfigurationScreen`'s `items(labels.size)` has no `key`**, so per-item
  state is keyed by index. Looked at hard and **left alone**: the only per-item
  state is `menuOpen`, and every path that changes the list closes the menu first
  (`onDeleteConfiguration` and `onDisable` both set `menuOpen = false` before
  calling back) or leaves the Activity, which dismisses the popup. No reachable
  failure, so by rule 6 it is not a finding -- and adding a `key` would change
  reuse and animation behaviour for nothing.
- **`loadVoice`'s two bare `setLanguage` calls need no try** while `setVoice`
  has one: `TextToSpeech.runAction` catches `RemoteException` internally and
  returns the error value, so `setLanguage` cannot throw at a dead engine, and
  the locale it is handed is never null.
- **`rebuildFromScan`'s dedupe by DISPLAY NAME, and its silent drop of a package
  whose entry was filtered out by `onlyEnabled`**, are `c3.n.g`'s own shape and
  were verified statement for statement in the 2026-09-09 audit. Not a defect.
- **the lock order holds after this session's changes**: `invariants.sh` #3
  ("no lock nested inside the language-list monitor") passes, and the new
  `synchronized(LangStore.languages)` blocks in `LanguagesActivity`,
  `ModesScreen` and `LanguagesVoicesViews` each compute `requiredNow()` and call
  `persistDisabled` OUTSIDE the block for exactly that reason.

## ELEVEN JNI READS COULD SEGFAULT THE PROCESS (2026-09-10)
The native core reads Java strings with `GetStringUTFChars`, and **that function
ALLOCATES**. When it cannot, it answers **`nullptr`** and leaves an
`OutOfMemoryError` **pending** in the JNIEnv. Eleven sites used the result
without looking, and `std::string(chars)` on a null pointer is undefined
behaviour -- on a phone, **SIGSEGV and the whole process gone, taking the voice
with it**.

**It was our own inconsistency rather than a decision.** Several sites in the
SAME file already checked -- `setLanguageHints`, `normalizeFancy`, and
`detectLanguageFull`'s own two fallback strings (`latC?latC:""`). The eleven that
did not:

| function | sites | how hot |
|---|---|---|
| **`processDirect`** | **7** | **once per utterance in mix and multilingual** |
| `jStringArrayToSet` (via `setDetectSets`) | 1, over the whole array | **once per utterance** |
| `setIsoMap` | 2 | once at startup, ~180 pairs |
| `detectLanguageFull` (`GetStringChars` for the text) | 1 | per utterance in auto/google |

`detectLanguageFull` was the worst-shaped of them: unguarded it did
`utf16to8(chars + winStart, ...)` on a null pointer and then
`ReleaseStringChars(jText, nullptr)` at the bottom, which is UB in its own right.
`nativeGetLanguages` guarded the `std::string` but released a possibly-null
pointer the same way.

**AND THE KOTLIN SIDE WAS ALREADY WRITTEN FOR THIS, which is what makes the fix
obviously right rather than defensive.** Every call into the core is already
`try { ... } catch (_: Throwable) {}`, or falls back to `"UNKNOWN"` -- so a
native failure was always *meant* to be recoverable. A segfault is the one
failure that contract cannot survive. Returning `nullptr` with the pending
exception intact turns it back into the `Throwable` those catches are waiting
for, which is the textbook JNI shape: **make no further JNI call while an
exception is pending.**

One helper, `jstringToStd`, is now the only way this file reads a jstring. A
**null jstring is not a failure** -- it is an empty string, which is what every
already-guarded site treated it as.

**Not a rule 5 question.** AutoTTS's native side is a stripped `.so`; this C++ is
ours, and the finding is that our own file guards some sites and not others. It
is also squarely the class the owner has overridden rule 5 for three times: the
outcome is total, and only a reinstall or a reboot clears it.

### The slicer had to learn a rule, and the harness caught the gap itself
`tools/verify/make_core_inc.py` drops `extern "C" JNIEXPORT` entry points so the
behaviour harnesses can compile the core with an ordinary g++ and no `<jni.h>`.
`jstringToStd` is a **`static` helper**, not an entry point, so the slice kept it
and `scriptfamily` stopped compiling with `'out' was not declared in this scope`
-- an error that says nothing at all about the real cause.

It now drops **any function whose signature takes a `JNIEnv*`**, whatever its
linkage. A rule rather than a name, so the next JNI helper cannot repeat this.
Two tests in that rule are load-bearing and were found by writing it wrong first:
it requires a `(` on the line and rejects lines starting with `//` or `*`,
because the WORD `JNIEnv` also appears in the prose above `jstringToStd`, and
consuming from a comment line would have eaten real code until the braces
happened to balance.

**This is the mirror of the note already in this file** about `detectContextText`
having to live above `buildMixChunks`: the slice boundary is real, and anything
on the wrong side of it breaks a proof rather than the app.

### ALL FIVE PROOFS RE-RUN AFTER THE NATIVE CHANGE, and that is the point
    latency        REAL core, REAL JNI symbols, genuine JNIEnv -- links and runs
                   3,520 chars 1.45 ms, 165 chars 0.15 ms (within noise)
    segmenter      IDENTICAL over 163,296 cases
    normaliser     IDENTICAL, 1,062 mappings over 1,114,112 code points
    script family  IDENTICAL over 15 sets x 1,114,112 code points
    langcodes      IDENTICAL over 283 CLD2 codes, 0 real differences

The latency harness is the one that matters most here: it is the only one that
**links the real core and calls the real `Java_com_tts_easyvoice_*` symbols
through a genuine JNIEnv**, so a broken signature or a lost symbol could not
have survived it.

### AGP 9.4.0 AND GRADLE 9.7.1 -- THE HOLD IS LIFTED (2026-09-10)
The pin at 9.3.2 carried a long note blaming AGP 9.4.0 for the accessibility
job's dependency failure. **That diagnosis was refuted by build 854**, which ran
on 9.3.2 and failed with the identical message; two later attempts
(`android.dependency.useConstraints=false`, then reverting Gradle) were refuted
the same way by builds 856 and 857. The cause was never a version: the app's
graph and the androidTest graph genuinely disagreed on `concurrent-futures` and
on guava's empty `listenablefuture` marker, and both conflicts predate every one
of those bumps.

That is fixed in `app/build.gradle.kts` -- the app declares
`concurrent-futures 1.2.0` so the `strictly` pin becomes the version the test
graph asks for, and every `*AndroidTest*` configuration excludes
`com.google.guava:listenablefuture` so the constraint has nothing left to
constrain. **Green three runs running (861, 862, 863)**, which is the condition
the old note itself set: *"AGP can go back to 9.4.0 once a run is green."*

**GRADLE 9.7.1 IS NOT A SEPARATE DECISION.** AGP 9.4's own release notes give its
minimum Gradle as **9.6.0**, and the workflow pinned 9.5.0, so the two move
together or the build fails at configuration. 9.7.1 is not new ground either:
builds 849 to 857 ran on it, and 857 is what proved Gradle innocent.

**Its own commit, with nothing else in it that can touch the build** -- this
file's own lesson is that a change testable only by a thirteen-minute CI run must
never be stacked with another. If it goes red, the step name says which half.

### A `.toInt()` on a stored preference, on the Voice setup screen
`VoiceRows.load`'s `voiceOrder` read the per-voice sort weight with
`(rawPrefs.getString(key, "1000") ?: "1000").toInt()`. That is a literal parser
pointed at STORAGE, and it has two ways to throw: `NumberFormatException` if the
value is not a number, and `ClassCastException` from `getString` itself if that
key was ever written with `putInt`. Both land inside the Voice setup screen's
composition, which is the screen the owner opens once per language.

**The way a wrong type gets in is Import.** `importSettingsXml` writes whatever
TYPE the XML tag names, so a single `<int name="com.x#en_US" .../>` in a settings
file the user picked is enough, for ever, until the app's data is cleared. It is
`toIntOrNull() ?: 1000` inside a `try` now, and **1000 is what a MISSING key
already answers** -- "unranked" -- so a corrupt entry sorts last instead of
taking the screen down, and valid data behaves exactly as before.

**The wider version of that was considered and NOT taken.** Every
`prefs.getInt`/`getBoolean` in `LangStore.loadFlags` and `loadModeLangs` has the
same `ClassCastException` shape, and one of those would kill the SERVICE in
`onCreate` rather than a screen. It is left alone because the import is already
gated: `getReferencedEnginePackagesFromXml` runs first and refuses any file that
does not contain three-letter string keys whose values carry a `#`, so a random
XML never reaches `importSettingsXml` at all, and our own exports always write
the right types. Hardening forty read sites against a file the app already
refuses would be the "defensive" justification rule 5 names. **If a device log
ever shows a ClassCastException out of loadAllSettings, that is the moment.**

### Swept in the same pass and CLEAN, so do NOT re-sweep
- **the engine-facing binder overrides cannot throw.** `onGetLanguage`,
  `onIsLanguageAvailable`, `onGetDefaultVoiceNameFor`, `onGetVoices`,
  `onIsValidVoiceName`, `onLoadVoice` and `onLoadLanguage` were each read to the
  end: every `isO3Language` / `isO3Country` read is inside a `try`, every locale
  parse goes through `parseVoiceNameAsLocale` which answers null rather than
  throwing, and `onLoadLanguage`'s voice load is wrapped. An exception on a binder
  thread is a process death, so this is the surface that matters most.
- **the override surface is complete**: fourteen overrides, the five abstract
  members plus `onStartCommand` (START_STICKY) and `onTaskRemoved`, matching the
  AOSP read already recorded.
- **`SimpleDateFormat` is shared and that is safe** -- it is touched only inside
  `writeLine`, which is `@Synchronized`.
- **`lateinit`**: three in the app, and after `appCtx` went, both remaining ones
  are assigned before any possible read (`prefs` in `onCreate`, `startEngine` at
  the top of the scan).
- **every permission the app declares is used** except `WAKE_LOCK` above, and
  nothing it does needs a permission it lacks.
- **`FileProvider`, `tts_engine.xml` and the four engine intents** were verified
  in the previous pass and are unchanged.

## THE TWO ABOUT BUTTONS ARE GONE, AND THE CLD2 LINE WAS STALE (owner, 2026-09-10)
*"jo donon buttons hai About page mein vah button nahin rakhne hain"*, and
*"About page mein kuchh chijen purani hai ... humne CLD2 full kar diya hai to
usko sahi karna hai."* Both done.

**The buttons.** "Apache License 2.0" and "CLD2 on GitHub" opened a browser.
`APACHE_LICENSE_URL`, `CLD2_URL` and `openLink()` went with them; nothing else
referenced any of the three. **No obligation is lost**: Apache 2.0 section 4(a)
asks that recipients get a copy of the licence and 4(d) that a `NOTICE` file be
reproduced **if one exists** -- CLD2 has none (HTTP 404) -- and the screen still
carries the notice **verbatim** with the licence's own URL written into its
text. A link is a convenience, not a term.

**The count was answering for the wrong build.** The line said *"It recognises
83 languages"*, quoting CLD2's README -- and that sentence describes the
**default** build, whose quadgram table is 256k. Since 2026-09-08 this app
compiles `compile_full.sh`'s set instead. The proof of which evaluation matches
is a number rather than a claim: **`kQuad0122Size` is 262,144 buckets of four
entries = 1,048,576**, and CLD2's own `docs/evaluate_cld2_large_20140122.txt` is
headed *"Evaluate CLD2 20140122 **1024k**"* while the small one says **256k**.
Counted from those two files, the large scores **170 distinct language codes**
against the small one's **78**.

It now reads **"with CLD2's full detection tables, which cover over 170
languages"**. Deliberately "over 170" and not an exact figure: that file counts
languages the QUADGRAM scorer was evaluated on, while the script-defined ones --
Gujarati, Tamil, Telugu, Kannada and the rest -- are decided by their Unicode
script and are detected whichever table is built, so an exact number would be
answering a different question from the one a reader is asking.

**`AccessibilityChecksTest.aboutScreen` needed no change** -- it renders the
screen and asserts nothing about the buttons -- but its comment named them, so
that was corrected rather than left to mislead.

## THE SLIDER MECHANISM, END TO END, AND WHAT THE TWO DIFFERENCES ARE (owner, 2026-09-10)
*"AutoTTS ke slider ka mechanism check kar lijiye ... uski internal prakriya hai
internal process ... aur hamara bhi aisa hi kar dijiye, AOSP se bhi confirm kar
lijiye."* Done at the source. The full mechanism now lives as a comment beside
`SLIDER_MIN` in `VoiceScreen.kt`; this is the summary and the decision.

**AutoTTS's own mechanism, from `c3/k.java`:** three `SeekBar`s at
`setMax(500)` / `100` / `200` with **`setMin` never called**, so each range
starts at 0; the floor of 10 is NOT in the range but enforced inside
`onProgressChanged` -> `P1()` by calling `setProgress(10)` on the bar itself;
`P1()` writes **straight into the live entry** `c3.n.c.get(b1).c/.d/.e` and
**ignores `fromUser`**; `onStartTrackingTouch` and `onStopTrackingTouch` are
**both empty**, so nothing persists there; the announcement is
`setStateDescription("<n> of <max>")` on **API >= 30 only**, read back through
`D2()`/`E2()`/`C2()` so it reports the CLAMPED value; the `-`/`+` handlers
(`I1`/`J1` pitch, `K1`/`L1` speed, `M1`/`N1` volume) read those same getters,
move by **5**, floor 10, cap `getMax()`, store, `setProgress`, then Toast
`"<n> of <max>"`; and `c3()` (Default) sets all three to 100.

**THE TWO DIFFERENCES BELOW WERE CLOSED ON 2026-09-10** -- *"han bilkul kar
dijiye AutoTTS slider"*. The buttons move **5** now and the range starts at
**0**, so AOSP's own `range/20` is a whole **25 / 5 / 10** and our hand-written
`setProgress` override is **gone**: Material3's `sliderSemantics` answers the
action, and its code carries the comment *"This is to keep it consistent with
AbsSeekbar.java: return false if no change from current."* The floor of 10 moved
out of `valueRange` and into the clamp in `onValueChange`, which is exactly
where `P1()` puts it. **The table below is the record of what was there before
and why**; the mechanism description above it is current.

Ours matched every other part of it already -- maxima, floor, the write straight
to the `LangStore` entry with no persist, the Toast, the `"<n> of <max>"` state
description, Default's three 100s -- and differed only in **two numbers, both
the owner's own instruction from 2026-09-02 and both now reversed by them:**

| | AutoTTS / AOSP | ours | the owner's words |
|---|---|---|---|
| one `-`/`+` press | **5** | **1** | *"increase decrease button se to ek-ek percent hi aage badhna chahie"* |
| one screen-reader swipe | **range/20** = 25 / 5 / 10 | **5** on all three | *"5% 5% nahin badh raha hai, aage piche ho jata hai"* |

**The swipe figure is AOSP's, read rather than recalled.**
`AbsSeekBar.performAccessibilityActionInternal` answers `ACTION_SCROLL_FORWARD`
and `ACTION_SCROLL_BACKWARD` with

    int range = getMax() - getMin();
    int increment = Math.max(1, Math.round((float) range / 20));

(`AbsSeekBar.java:1125-1126`), and `setMax()` seeds `mKeyProgressIncrement` the
same way, so a hardware D-pad moves by the same amount. With AutoTTS's 0..500
that is a whole **25**.

**AND THAT ZERO IS WHY AUTOTTS NEVER HAD THE BUG THE OWNER REPORTED**, which is
the one genuinely new thing this audit produced. Our range starts at the floor,
`10..500`, so Compose's own increment is `(max-min)/20 = 24.5` -- a fraction,
and throwing away the half is exactly what made a swipe up and a swipe back down
fail to return to the same number. AutoTTS's range starts at 0, so its increment
is a whole number and the defect cannot arise. **That is the fix that shipped**:
starting at 0 removes the fraction at its source, which is why the override
could be deleted rather than kept. Do not put a step size of ours back here
without the owner asking.

### The service and component surface is COMPLETE -- verified mechanically
*"baki services bhi ... TTS ki aur TTS ke alava bhi."* The two manifests were
diffed rather than read:
- **our `<service>` carries AutoTTS's attributes byte for byte**, including the
  three that are inert on a TTS service and are cargo in AutoTTS too
  (`accessibilityEventTypes`, `accessibilityFlags`, `canRetrieveWindowContent`
  belong to an `<accessibility-service>` resource, not to `<service>`), plus
  `android:label`, `foregroundServiceType="mediaPlayback"`, the priority-100
  intent filter and `<meta-data android:name="android.speech.tts">`;
- **the four engine intents match**: `TTS_SERVICE` as the service,
  `CHECK_TTS_DATA` and `GET_SAMPLE_TEXT` as `Theme.NoDisplay` activities, and
  `INSTALL_TTS_DATA` in `<queries>` only -- **AutoTTS declares no activity for
  it either**, so that is parity, not a gap;
- **`tts-engine`'s `settingsActivity`** points at our main screen, as AutoTTS's
  points at its own;
- **permission diff: AutoTTS has exactly two we do not** --
  `com.android.vending.CHECK_LICENSE` and its androidx-generated
  `DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`. The first is the licence gate,
  which is the standing carve-out. Nothing is missing.

**`synthesizeToFile` was the one TTS entry point never traced, and it is now.**
`SynthesisToFileOutputStreamSpeechItem` extends `SynthesisSpeechItem` and its
`playImpl()` calls `super.playImpl()`, so it **does** reach our
`onSynthesizeText`, with a `FileSynthesisCallback` instead of the playback one.
We never produce audio, so the file gets a WAV header and no samples while the
text is spoken **aloud** by the downstream engine. AutoTTS behaves identically
for the identical reason, and **it cannot hang** -- the downstream `onDone`
releases the wait exactly as it does for `speak()`. Parity; do not "fix" it.
`playEarcon` and `playSilentUtterance` never reach an engine at all
(`AudioSpeechItem` / `SilenceSpeechItem` are handled inside the framework), so
there is nothing there to implement.

### Checked in the same pass and clean, so do NOT re-sweep
- **`String.lowercase()` is locale-independent.** The one call on a language
  code, `prefs.toIso3(langCode.lowercase())`, is Kotlin's no-argument overload,
  which is `Locale.ROOT` by definition -- so the Turkish dotless-i trap does not
  apply. The only `Locale.getDefault()` lowercase is the slider's own
  `"Decrease " + label.lowercase(...)`, where a localised name is what is wanted
  and none of Speed/Volume/Pitch contains an "I".
- **the sliders cannot write to a stale entry.** `VoiceSetupActivity` wraps the
  screen in `key(langIndex)`, so Previous/Next resets every `remember` and the
  three values are re-read from the new language.
- **a swipe at either end is not mis-handled.** The delegate invokes
  `setProgressAction` with `rangeInfo.current + increment` and **no coercion**
  (`AndroidComposeViewAccessibilityDelegateCompat:1603`), so the delta still
  equals the increment at the top of the range and our tolerance test still
  recognises it as a swipe rather than a jump.
- there is **no third slider**: all three go through `ValueSlider`, and no
  `TODO`/`FIXME`/"for now" exists anywhere in our sources.

## THE ROLE/STATE STRINGS IN THE APK ARE androidx's, NOT OURS (asked 2026-09-08)
The owner opened the APK in a resource viewer and saw `tab`, `switch_role`,
`state_on`, `state_off`, `selected`, `not_selected`, `m3c_dropdown_menu_collapsed`
/ `_expanded` / `_toggle`, `default_popup_window_title`, `in_progress`,
`indeterminate`, `m3c_dialog`, `template_percent`,
`androidx_compose_foundation_autofill` -- and reasonably asked why they are there
when everything is supposed to come from the library.

**They ARE the library.** `app/src/main/res/values/strings.xml` contains exactly
**one** string, `app_name`, plus a comment. Every other key is shipped by
`androidx.compose.ui` and `androidx.compose.material3` and merged into the APK
because those are dependencies. They are precisely the words the accessibility
delegate speaks -- "Tab", "Switch", "On", "Off", "Selected", "Not selected",
"Collapsed", "Expanded" -- i.e. the role and state announcements the owner asked
for. **Deleting or overriding them would break exactly the feature they were
added for**, which is what the `default_popup_window_title` experiment already
proved the hard way (see that section).

**Nothing is duplicated either.** The two screenshots were the same list at two
scroll positions, which is why the middle rows appear in both.

## THE MODE'S SETTINGS BUTTON IS THE FAB NOW (owner, 2026-09-09)
*"har ek mode ka jo settings button aata hai ... thoda upar ki taraf hai ...
jahan per FAB button aata hai, bottom right corner per, vahan per hona chahie
... jo bhi mode mein change karunga uske hisab se vah button vahan per change
hoga."*

**The real problem was that the control MOVED.** It sat right-aligned on the
SELECTED radio's own row, so its position changed with the mode: beside Dual at
the top on one visit, beside Multilingual three rows further down on the next.
A control that is somewhere else every time is the hardest kind to find again,
by touch or by eye. The FAB is always the same corner. Material's own definition
is what makes it the right component rather than a preference -- a FAB "lets the
user perform a primary action" and is "typically found anchored to the bottom
right" -- and the Configuration tab already had one, so the app now answers
"where is the main action on this page?" the same way on both.

**The wiring is the part that can silently break, and it has a test.** The radio
writes the mode straight to the store, and `MainScreen` only re-reads prefs when
`modeRefresh` changes -- which a radio tap does not do. So `ModesScreen` takes an
**`onModeChanged`** callback and `readingMode` in `MainScreen` is a `var` fed by
it. Without that the FAB would keep offering the settings of the mode you just
left, and nothing on screen would look wrong. `mainScreenModeSettingsFabFollowsMode`
picks the Dual radio and asserts the button renames from "Mixed mode settings"
to "Dual languages settings".

**The same `var` fixed a second staleness nobody had reported.** `showAddLanguage`
(`readingMode != "none" && != "dual"`) was computed from the same stale read, so
choosing Dual and swiping to Configuration still offered "Add language" -- a
button leading to a screen that answers "Language selection is not available".

**The label is short on purpose, and the split is deliberate.** Visible text is
**"Settings"**; the accessible name is the whole **"<Mode> settings"**, which is
the same string the screen it opens uses as its first heading -- so what you hear
here is what you land on there. WCAG 2.5.3 Label in Name asks the accessible name
to CONTAIN the visible label, and it does. (The reverse mismatch is what had to
be fixed on the Add language FAB, where the visible word "Languages" was absent
from the name entirely -- do not read that fix as forbidding this shape.)
Spelling the mode out visibly was rejected for one measurable reason:
"Multilingual mode (experimental) settings" is **41 characters** and an extended
FAB is a single line, so it would stretch the button across a compact screen.
**Say the word if the visible text should carry the mode too** -- it is one
string, and the cost is that one mode's button gets very wide.

**Two smaller things went with it.** The mode row is ONE `Row` again, not an
outer wrapper holding the inner one at `weight(1f)` beside the button, because
a wrapper with a single child is only a layout node; and the Modes column gained
**88dp of bottom padding**, the same clearance the Configuration list gives its
own FAB, so the last mode's description can be scrolled out from under it.

## THE SCAN RAN TWICE AT ONCE, AND THAT IS THE CHANGING NUMBER (owner, 2026-09-09)
*"hamare mein acche se scan nahin ho raha hai ... AutoTTS ka dekh lijiyega acche
se ho raha hai ... double triple baar jab bhi application open karte hain to
2 3 4 5 6 7 is tarike se."*

The owner was right that AutoTTS does it better, and the reason is structural
rather than a missing step. **Both apps scan on every `onCreate` of their main
screen. The difference is where the scan keeps its state.**

    AutoTTS   NewSettingsActivity INSTANCE fields:
              L (index)  M (init fired)  N (per-engine timeout)
              O (failed) J (dedupe map)  F (the TextToSpeech)
    ours      `object EngineFinder` statics:
              seenEngines, voiceWeights, globalTimeoutHandler

A recreated Activity gives AutoTTS a fresh `J`, a fresh `O` and its own handlers,
so two scans can never tread on each other. Ours lifted the scan into an
`object`, which turned all of it into process-wide state -- and `MainActivity`
starts a scan in **every** `onCreate`. Rotate the phone, or reopen the app while
the previous scan is still walking engines at 30 seconds apiece, and two scans
run at once over the same fields:

- `getEngines()` did `seenEngines.clear()` on entry, so scan 2 wiped the dedupe
  set that scan 1's probe callback was about to read -- which is how the engine
  list came out different every time;
- `finalizeScan()` did `removeCallbacksAndMessages(null)` on the SHARED handler,
  cancelling the other scan's watchdog;
- and both scans reached `finalizeScan()` and each did
  `LangStore.languages.clear()` + `rebuildFromScan()` + `persistAll()` with its
  own half-finished engine list. **That is the 2, 3, 4, 5, 6, 7.**

**This is our refactor's bug, not AutoTTS's**, which is the same distinction that
made `releaseWaitWithoutSpeaking` safe to write: the defect exists because the
code was moved into a shared object, not because AutoTTS does something we left
out.

### The fix is AutoTTS's own shape, restored
- **`seenEngines` is gone.** `getEngines(ctx, seen)` takes the caller's map, so
  the dedupe set is per scan exactly as AutoTTS's `J` is per Activity. Nothing
  outside `EngineFinder` ever read it.
- **A generation counter decides who may publish.** `scanGeneration` is bumped at
  the top of every scan; `finalizeScan` returns immediately if a newer scan has
  started, so a superseded scan writes nothing. `scanNextEngine` checks it too,
  so the old scan stops walking engines instead of fighting the new one for the
  progress line and holding engine bindings open.
- **The watchdog is cancelled by identity**, `removeCallbacks(myTimeout[0])`
  rather than clearing the whole handler.

### THE LATE-INIT RACE IS FIXED, FROM AOSP (owner override, 2026-09-09)
*"properly sources ke through fix karo ... ham log is per depend rahenge na to
achha nahin rahega ... latency bilkul nahin aani chahie."* The section above had
recorded this race as AutoTTS's own and left it under rule 5. The owner has
overridden that explicitly, so it is fixed -- and read out of
`frameworks/base/core/java/android/speech/tts/TextToSpeech.java` rather than
reasoned about.

**What was wrong.** There was ONE shared `OnInitListener` reading the mutable
`index`, and the 30 s timeout advanced the walk **without shutting the abandoned
client down**. A slow engine's `onInit` therefore arrived after the walk had moved
on and was attributed to the NEXT engine: it set the shared `initFired`, masking
that engine's own timeout; it read `holder[0]`, which by then held a different
client; it could mark the wrong index failed; and it called `scanNextEngine()` a
second time, so an engine was skipped. Every abandoned client also stayed bound
for the life of the process.

**Three facts from AOSP decide the fix:**

| line | what it says |
|---|---|
| `shutdown()` 956-964 | *"Special case, we are asked to shutdown connection that did finalize its connection"* -- while still connecting it calls `mConnectingServiceConnection.disconnect()`, which is `unbindService`. **After that `onServiceConnected` never arrives, so `dispatchOnInit` never runs.** |
| `dispatchOnInit` 930-938 | calls the listener then sets `mInitListener = null`, so one client fires `onInit` **at most once** |
| 871, 877, 907, 2385, 2476 vs 2324 | **every INLINE dispatch is ERROR.** The only one that can carry SUCCESS is inside `SetupConnectionAsyncTask.onPostExecute`, which is always asynchronous |

So **shutting the abandoned client down at the timeout is what PREVENTS the late
callback**, and it releases the binding in the same move. That is the fix, and it
is AOSP's own mechanism rather than a guard invented here.

That third row is what makes the code safe to write: on SUCCESS the constructor
has long returned and the client cell is set; on the inline ERROR path the cell is
still null and that path does not need a client.

**Each engine now owns its whole step** -- its index, its client, its timeout and a
one-shot `handled` flag, all captured together. `handled` is the belt to
`shutdown()`'s braces: `unbindService` stops future callbacks, but one already
queued on the main looper can still land, and such a callback now releases its own
client and returns **without touching `failed` and without advancing the walk**.
No lock is needed -- every path here is the main thread, because
`onServiceConnected` and `onPostExecute` are, and the inline ERROR dispatch runs on
the constructor's thread, which is also main.

**NO LATENCY IS ADDED, and this was the owner's condition.** Nothing new waits.
The 30 s per-engine and 180 s overall timeouts are AutoTTS's own numbers and are
untouched. The change only stops abandoned work from continuing, so the scan does
strictly less than before: a timed-out engine's binding is released at once
instead of being held for the life of the process, and a superseded scan stops
walking.

**And there is no faster API to move to, which was checked rather than assumed.**
`TextToSpeech.getEngines()` returns `EngineInfo` only -- name, label, icon -- so
voices genuinely require binding each engine. The `Executor`-based init that would
make dispatch explicit is a **private `@hide` constructor**; the three public ones
take no executor. There is nothing newer to adopt here.

### Checked against AutoTTS and deliberately NOT changed
- **the engine list is built the same way.** `B0()` is our `getEngines`: the same
  three `queryIntentServices` flag passes (131072, 128, 0) with a dedupe map;
  then the probe's `getEngines()` adds what that missed, skipping its own package.
  Identical.
- **the probe-failure path matches.** AutoTTS shows a Toast and calls `z0()`;
  ours shows a Toast and calls `finalizeScan()`, and `z0` **is** our
  `finalizeScan` -- distinct the failed indices, reverse-sort, remove, rebuild
  the language list, persist, then make a new `TextToSpeech`.
- **THE LATE INIT CALLBACK: this bullet is SUPERSEDED -- the owner overrode rule
  5 the same day and it is fixed.** It used to say the race was AutoTTS's own
  (`j.onInit` really does set `M = true`, clear `N` and compare against
  `c3.n.b.get(L)` with no generation of its own) and must not be touched. See
  "THE LATE-INIT RACE IS FIXED, FROM AOSP" below for what replaced it.
- **`D0()`'s index arithmetic was NOT re-derived.** CFR renders it as
  `this.L = n3 + 1` followed by `while ((n3 = ++this.L) < size && get(L).a())`,
  which reads as a double increment and is exactly the ambiguous shape rule 7
  says to take to smali. Ours is `index++` then skip while self-engine, which is
  clear and has been scanning correctly. Chasing an ambiguous decompile to
  "match" it risks breaking a working walk for nothing.

## THERE IS NO `NativeEngine` CLASS ANY MORE (owner, 2026-09-09)
*"native engine wala class ... APK ko unpack karke dekhta hun to yah alag class
padta hai ... vah sari native method aa jaaye services wale ke andar hi, alag se
class na bane."*

The owner decompiled the shipped APK and found `NativeEngine` sitting there under
its own real name. That was not an accident and it was not R8 failing: **a class
that holds native methods cannot be renamed or merged away**, because JNI resolves
by symbol name and the symbol contains the class name. `proguard-rules.pro` had a
`-keep` for exactly that reason, so the class was pinned by design.

The seven `external fun`s now live in **`EasyVoiceTtsService`'s companion, marked
`@JvmStatic`**, and `NativeEngine.kt` is deleted.

### `@JvmStatic` on a companion `external fun` is the ONE form that works
All three candidates were compiled with the project's own kotlinc 2.4.20 and read
back with `javap`, rather than reasoned about:

| Kotlin form | where the native method lands | verdict |
|---|---|---|
| instance member of the class | `EasyVoiceTtsService`, but non-static | needs an instance, and **three** of the twelve call sites are in the companion |
| plain `companion object` member | **`EasyVoiceTtsService$Companion`** | the separate class is still there, and the symbol gains `_00024Companion` |
| **`@JvmStatic` companion member** | **`public static final native` on `EasyVoiceTtsService` itself** | one class, callable unqualified from both scopes -- **taken** |

So the JNI symbols moved from `Java_com_tts_easyvoice_NativeEngine_*` to
`Java_com_tts_easyvoice_EasyVoiceTtsService_*`, in the core and in
`tools/verify/latency/main.cpp`, which declares them by hand.

**`System.loadLibrary` is safe where it is, and that was verified too.** The
companion's `init` block compiles **into `EasyVoiceTtsService.<clinit>`** --
`javap -c` shows the `ldc "easyvoice_core"` and `invokestatic
System.loadLibrary` right there in the outer class's static initialiser. So
touching any of these statics initialises the class and loads the library first;
it does not depend on the companion being touched separately. That was the one
way this change could have failed at runtime with `UnsatisfiedLinkError`.

### Proven, not assumed
**`tools/verify/latency/run.sh` links the REAL core and calls the REAL symbols
through a genuine `JNIEnv`.** It compiled, linked and ran clean after the rename,
which is exactly what a mismatched symbol name could not do. Timings unchanged
(165 chars 0.23 ms, the 3,520-char ceiling 2.62 ms).

### AND THIS WAS THE ONLY CLASS LEFT WITH A REAL NAME
Worth writing down so the question does not come back. After this change
`proguard-rules.pro` contains **no `-keep class` rule at all** -- only
`-keepclassmembers`. So the classes a decompile can still name are exactly the
**eight the manifest forces**, because Android instantiates them by name:

    EasyVoiceTtsService  MainActivity  AboutActivity  LanguagesActivity
    ModeSettingsActivity VoiceSetupActivity  CheckVoiceData  GetSampleText

Nothing can change those. Everything else -- `LangStore`, `EngineFinder`,
`IsoCodes`, `EasyVoiceLogger`, `SampleTexts`, `SharedPrefsManager`, `VoiceRows`,
`EvActivity`, `LangEntry`, `TextChunk`, `RequiredEnginesItem` -- is obfuscated to
a short name in the root package by `-repackageclasses ''`. **`NativeEngine` was
the single exception, and it is gone.**

## THE NATIVE FLAGS WERE REDONE PROPERLY, AND THE OLD PASS WAS WRONG TWICE (owner, 2026-09-09)
*"R8 or baki native type ke jo rules hai ... sare rules lagakar aur bhi optimise
kar dena ... extreamli rules laga do sabhi jagah per full full extremely."* The
section below this one measured these flags once and rejected two of them. **Both
rejections were wrong, and this pass proves it rather than arguing.** Read this
one; the one below is the record of how it was first done.

### What the earlier pass got wrong
- **`-fno-exceptions -fno-rtti` was called "a real behaviour change".** It is not,
  and one grep settles it: there is **not one `try`, `catch`, `throw`,
  `dynamic_cast` or `typeid`** in `tts_engine_core.cpp` or anywhere in CLD2.
  Nothing can be caught that was not already going to `terminate`.
- **`-Oz` was called "+12,648 WORSE".** True at the time -- with exceptions on and
  no LTO. With `-fno-exceptions` and ThinLTO it is **16,632 bytes BETTER**.
- **The biggest apparent win is already banked, and that had to be checked.**
  `--pack-dyn-relocs=android` measured -71,696 on a host link -- and **clang's own
  driver already adds it below API 28** (`clang/lib/Driver/ToolChains/Linux.cpp`
  line 288, read at the source), which is exactly our `minSdk 24`. Adding the flag
  would change nothing. The NDK's own cmake files were checked too, over HTTP range
  requests into the 738 MB zip rather than downloading it: they set `ANDROID_RELRO`
  and say nothing about relocation packing.

### The measurement, against a base that already carries what the NDK adds
So every number here is what a real Android build actually gains:

| | raw .so | gzipped | x2 ABI download |
|---|---|---|---|
| base (today + what the NDK already adds) | 6,464,816 | 4,765,627 | -- |
| no exceptions/rtti/unwind + inlines-hidden | **-42,864** | -17,419 | **-34,838** |
| + `--icf=safe` | -45,760 | -17,492 | -34,984 |
| + `-flto=thin` | -58,896 | -19,832 | -39,664 |
| + `-Oz` instead of `-Os` | **-75,528** | **-26,463** | **-52,926** |

**Measured zero or noise, and NOT added:** `--exclude-libs,ALL`, `--hash-style=gnu`,
`--as-needed`, `-z nostart-stop-gc`, `-fmerge-all-constants` (+528),
`-fno-semantic-interposition` (0). `--icf=all` beats `--icf=safe` by 448 bytes and
can fold two functions to one address, so `safe` is taken.

### The codegen flags were PROVEN, which closes a limitation this file recorded
`-Oz` and `-flto=thin` change codegen, and the older note said plainly that the
harnesses could not certify such a flag because they compile at their own `-O1`/
`-O2`. **That is fixed: all four harnesses now honour `EV_CXXFLAGS`**, and were
re-run under the exact flags that ship:

    EV_CXXFLAGS="-Oz -flto -fno-exceptions -fno-rtti -fno-unwind-tables \
                 -fno-asynchronous-unwind-tables -fvisibility-inlines-hidden"

    segmenter        IDENTICAL over 163,296 cases
    normaliser       IDENTICAL, 1,062 mappings over 1,114,112 code points
    script family    IDENTICAL over 15 sets x 1,114,112 code points
    latency          UNCHANGED within noise -- the 3,520-char ceiling reads
                     1.11 ms against 1.16 ms, and 165 chars is 0.15 vs 0.16

**Latency mattered more than the bytes here** and was checked for exactly that
reason: `-Oz` can cost speed, and this app's first rule is that it must not. It
does not, because the hot path is table lookups rather than code.

**And the one way this could break at runtime was checked:** `nm -D --defined-only`
finds **7 of 7** `Java_com_tts_easyvoice_*` entry points on the most aggressive
build. A missing symbol would be `UnsatisfiedLinkError` on the first utterance.

### The R8 side is already at its limit, and the two remaining levers are stated
`isMinifyEnabled`, `isShrinkResources`, R8 full mode, `-repackageclasses ''`,
`-allowaccessmodification` and the `Intrinsics` strip are all on. Two things are
deliberately NOT done:
- **`-keepattributes SourceFile,LineNumberTable` STAYS.** Dropping it shrinks the
  dex, and it also deletes the line numbers from the stack trace
  `EasyVoiceLogger` writes into `easy_voice.log` -- the file the owner sends when
  reporting a bug. Same reasoning as not stripping `android.util.Log`.
- **`androidResources.localeFilters` (locale filtering) is the OWNER'S CALL.**
  androidx ships **7,646 string entries across 86 locales** (measured from the
  three aars: compose.ui 1,719, material3 5,670, foundation 257). Filtering to
  English would drop about 85/86 of that from `resources.arsc` -- the only
  legitimate way to reduce those strings. The cost: on a phone set to Hindi,
  TalkBack would announce "Tab", "Switch", "On", "Off" in English instead of
  Hindi. Say the word and it is one line.

### THE STRINGS ARE androidx's AND DELETING THEM BREAKS TALKBACK -- asked again 2026-09-09
The owner listed `tab`, `switch_role`, `state_on`, `state_off`, `selected`,
`not_selected`, `template_percent`, `m3c_dropdown_menu_*`, `m3c_dialog`,
`default_popup_window_title`, `default_error_message`, `in_progress`,
`indeterminate`, `state_empty`, `androidx_compose_foundation_autofill` and said
*"TalkBack already announce karta hai to ye sab faltu add kar rakhi hai ... hata
dena"*. **The premise is backwards, and it is now proven at the source rather
than asserted.** Each aar was downloaded and grepped:

    tab, switch_role, state_on/off, selected, not_selected,
    template_percent, in_progress, indeterminate, state_empty,
    default_popup_window_title, default_error_message   <- androidx.compose.ui
    m3c_dialog, m3c_dropdown_menu_collapsed/expanded/toggle <- material3
    androidx_compose_foundation_autofill                 <- compose.foundation

**Our `strings.xml` contains exactly one string, `app_name`.** Not one of those
names is declared or referenced anywhere in our code -- the only greps that hit
are COMMENTS in `ComposeTheme.kt` and `LanguagesActivity.kt` explaining where the
words come from.

**And they are not "extra beside" what TalkBack says -- they ARE what TalkBack
says.** The accessibility delegate reads "Tab" out of `R.string.tab`, "Switch"
out of `switch_role`, "On"/"Off" out of `state_on`/`state_off`,
"Selected"/"Not selected" out of `selected`/`not_selected`. Delete them and the
role and state announcements this app spent three sessions getting right go
silent.

**This was already proven the hard way once.** Overriding one of them --
`default_popup_window_title`, with an empty string -- shipped on 2026-09-04 and
was reverted the same day, because an untitled window makes a reader fall back to
"sub panel", the activity and the package name. The owner heard it and accepted
the revert. `strings.xml` carries a comment saying so.

## THERE IS NO NATIVE R8, AND THAT IS MEASURED (owner asked 2026-09-09)
*"R8 mein to aapne shrink kar diya hai ... to native size bhi file size kam ho
jaani chahie na ... native side mein jaisa kuchh compiler hoga na jiske through
bilkul file size kam ho jayegi."*

A fair question with a disappointing answer, so here is the evidence rather than
the opinion. **R8 shrinks by deleting unreachable CODE. This library is not code.**

    .rodata  (CLD2's lookup tables)   6,253,216 bytes    95%
    .text    (every line we wrote)      110,533 bytes   1.7%

Every one of those tables is reached from the detector, so there is nothing for a
garbage collector to collect -- and **`-Wl,--gc-sections` was already enabled**,
paired with the `-ffunction-sections -fdata-sections` in `build.gradle.kts`, so
the one flag that IS R8-shaped has been doing its job all along.

**Every remaining candidate was built and weighed**, against the real source list,
with the project's own flags (host clang 18 + lld; absolute bytes are x86-64, the
deltas are what transfer):

| flag | delta | verdict |
|---|---|---|
| `-Oz` instead of `-Os` | **+12,648** | **WORSE.** Measured, not guessed |
| `-Wl,--icf=all` | -3,696 | noise, and it can fold two functions to one address, so a function-pointer compare could change meaning |
| `-Wl,--exclude-libs,ALL` | **0** | exactly nothing |
| `-fno-exceptions -fno-rtti` | -28,256 | a real behaviour change; the owner said "bina kuchh badle" |
| **`-Wl,--strip-all`** | **-47,184** | **free and safe -- TAKEN** |
| all the safe ones together | -52,648 | 0.8% of the library |

**`--strip-all` keeps the app working, and that was checked rather than hoped:**
it removes `.symtab` and `.strtab` only. All **seven** `Java_...` JNI entry points
live in `.dynsym`, which a shared library must keep to be loadable, and `nm -D
--defined-only` finds all seven after stripping.

**And the honest number is smaller still.** The APK stores `.so` **compressed**
(`useLegacyPackaging = true`, which is already the smaller of the two packaging
choices for download size). Gzipped, the library is 4.78 MB, and stripping saves
**10,621 bytes of actual download**. That is 0.2%.

**THE REAL APK, FROM THE RELEASES API, IS WHERE THIS LANDS.** Build 849 is
**11,428,505 bytes (10.90 MB)**, and the compact-table build 818 was 3,837,769 --
so the full table set the owner chose on 2026-09-08 is about **+7 MB**, exactly as
that section predicted. Against that:

    native .so, two ABIs, compressed      ~9.6 MB      ~88% of the APK
    everything else (dex, resources)      ~1.3 MB
    what every flag above can save          10 KB      0.09%

**So the flags are finished. Do not chase them again.** The two levers that would
actually move this are both already recorded above and both are the owner's call:
the **full CLD2 table set** (+4.91 MB per ABI over the compact one, chosen on
2026-09-08 for 183 languages instead of 82) and the fact that the APK ships **two
ABIs**, which doubles all of it. An AAB, ABI splits, or dropping `armeabi-v7a`
are the only changes with a megabyte in them.

**A limitation worth stating.** The three proof harnesses compile the core with
their own `-O1`/`-O2` under g++, not with the app's flag set, so they cannot
certify that a codegen flag is behaviour-neutral. That did not matter here --
`--strip-all` is a link-time symbol-table removal and cannot change codegen -- but
if a future flag DOES touch codegen, teach the harnesses a `CXXFLAGS` override
first and re-run them under the app's real flags.

## EVERYTHING IS ON THE LATEST STABLE NOW (owner, 2026-09-09)
*"sab kuchh latest hi use karna. Aisa nahin ki AutoTTS mein purane component use
kiye hain to ham bhi pura nahin use karenge ... sab kuchh latest version hi hona
chahie."*

**This is a standing instruction and it OVERRIDES one decision recorded the same
day.** The note beside `core-splashscreen` argued for staying on 1.0.1 because
1.2.0 adds an AppCompat artifact; the owner has taken that cost knowingly. Do not
restore that reasoning, and do not decline a version bump again on the grounds
that the old one still works.

**AutoTTS's own component ages are NOT a reason to stay behind.** That is the
sentence the owner led with, and it is the general rule this section establishes:
rule 5 governs **behaviour** -- what the code does -- never the **version** of the
library or toolchain it is built with.

### What moved, each checked against its own metadata rather than recalled
| | was | now | source of truth |
|---|---|---|---|
| Android Gradle Plugin | 9.3.2 | **9.3.2 -- HELD, see below** | Google Maven `maven-metadata.xml` |
| Kotlin (+ the Compose plugin, which must match exactly) | 2.4.10 | **2.4.20** | Maven Central |
| Gradle | 9.5.0 | **9.7.1** | `services.gradle.org/versions/current` |
| NDK | 29.0.14206865 | **30.0.16248370** | the SDK's own `repository2-3.xml` |
| CMake | 3.22.1 | **4.1.2** | same |
| build-tools | 36.0.0 | **37.0.0** | same |
| `core-splashscreen` | 1.0.1 | **1.2.0** | Google Maven |

**build-tools was a real inconsistency, not just an old number**: `compileSdk` is
37 and build-tools was still 36.0.0.

**Everything else was already current** and that is measured, not assumed --
`activity-compose` 1.13.0, `core-ktx` 1.19.0, `lifecycle-runtime-ktx` 2.11.0,
`compose-bom` 2026.08.00, `material3.adaptive` 1.3.0, `kotlinx-coroutines` 1.11.0,
`test:runner` 1.7.0, `test.ext:junit` 1.3.0, `ui-test-junit4-accessibility` 1.12.0.

### AGP 9.4.0 WAS BLAMED FOR THE accessibility FAILURE AND IT WAS INNOCENT
**READ THE CORRECTION AT THE END OF THIS SECTION BEFORE ACTING ON ANY OF IT.**
Build 849 is where it started, and the first diagnosis below was wrong.

**The `build` job PASSED and published the APK.** Every step green: Install NDK
and CMake, Build APK, Publish. So **NDK r30, CMake 4.1.2, Gradle 9.7.1, Kotlin
2.4.20 and build-tools 37 are all proven to work** -- the four bumps that could
not be tested in the container are now tested by CI, and none of them was the
problem.

**Only `accessibility` failed**, on `:app:mergeDebugAndroidTestAssets`:

    Could not resolve androidx.concurrent:concurrent-futures:{strictly 1.1.0}
      1.1.0 - from lock file
      1.2.0 - transitively via androidx.test.ext:junit:1.3.0
    Could not resolve com.google.guava:listenablefuture:{strictly 1.0}
      ... version resolved in configuration ':app:debugRuntimeClasspath'
          by consistent resolution

That `{strictly}` is **AGP's own consistent resolution**, which pins the
androidTest classpath to whatever the app resolved. 9.4.0 applies it harder, and
the two graphs genuinely disagree: the app resolves `concurrent-futures` 1.1.0
and `listenablefuture` 1.0, while the Accessibility Test Framework drags in guava,
whose `listenablefuture` is the empty `9999.0-empty-to-avoid-conflict-with-guava`
marker.

**It is NOT `core-splashscreen` 1.2.0, and that was measured rather than assumed.**
`tools/fetch-deps.py` resolved the app's graph both ways: `concurrent-futures`
stays 1.1.0 and `listenablefuture` stays 1.0 either way, and the upgrade adds only
`appcompat-resources` plus two `vectordrawable` artifacts.

**Why it is held rather than worked around.** Neither force is safe: pinning
`listenablefuture` to 1.0 puts a second copy of `ListenableFuture` beside guava's,
and the 9999.0 marker is an EMPTY jar that would take the class out of the app.
And none of it can be tested here -- there is no Gradle or Android SDK in this
container -- so a `resolutionStrategy` would be a hunch spending thirteen-minute
CI runs. AGP holds at **9.3.2**, the combination build 848 proved green.

**This is the same carve-out as lifecycle 2.12.0-alpha**: "latest" means the
newest version that actually works, and one that fails the accessibility gate is
not one. Revisit on the next AGP.

**THE CORRECTION (2026-09-09, build 854).** AGP was already back at **9.3.2**
and build 854 failed with the **identical** error, so the diagnosis above is
refuted: AGP 9.4.0 is not the cause and holding at 9.3.2 buys nothing.

What it actually is, measured rather than eliminated by hand-waving:
- **the two graphs genuinely disagree, and always did.** `tools/fetch-deps.py`
  resolved both -- the app resolves `concurrent-futures` **1.1.0** and has no
  guava at all, while adding the androidTest roots brings `concurrent-futures`
  **1.2.0** (via `androidx.test:core:1.7.0`) and **guava 31.0.1-android** (via
  ATF 4.1.1), whose `listenablefuture` is the empty
  `9999.0-empty-to-avoid-conflict-with-guava` marker that `strictly 1.0` can
  never accept;
- **`core-splashscreen` 1.2.0 is innocent too** -- resolved at 1.0.1 and 1.2.0,
  and neither artifact moves; it only adds `appcompat-resources`;
- **by elimination the version that changed the behaviour is Gradle 9.5.0 ->
  9.7.1.** Nothing else in that batch can touch dependency resolution: Kotlin,
  NDK, CMake and build-tools cannot. Both conflicts are older than the batch and
  were simply tolerated before.

**The fix is `android.dependency.useConstraints=false`** in `gradle.properties`,
with the whole reasoning written beside it there. The option was read out of the
**AGP 9.3.2 jar** rather than recalled -- `BooleanOption.USE_DEPENDENCY_CONSTRAINTS`,
`ApiStage.Stable`, and it is exactly what `VariantDependenciesBuilder.
maybeAddDependencyConstraints()` gates on. It affects the debug/androidTest
classpath only; the release APK the owner installs comes from the `build` job,
which passed on every one of these runs.

**THE SECOND CORRECTION (build 856): `android.dependency.useConstraints=false`
DID NOT WORK EITHER.** The option is real -- read out of the AGP 9.3.2 jar,
`BooleanOption.USE_DEPENDENCY_CONSTRAINTS`, `ApiStage.Stable`, gating
`VariantDependenciesBuilder.maybeAddDependencyConstraints()` -- and build 856 set
it and failed with the **same message**, still reasoned *"by consistent
resolution"*. So it does not gate what that message comes from. The line was
removed rather than left in as dead config.

**GRADLE IS BACK AT 9.5.0.** By elimination it is the only variable left, and
9.5.0 is the version build 848 proved green with this exact dependency graph.
Same carve-out as lifecycle 2.12.0-alpha, and the owner's own rule: "latest"
means the newest version that actually works.

**The real repair, for a green baseline rather than mid-firefight:** make the two
graphs agree -- add `androidx.concurrent:concurrent-futures:1.2.0` to the app so
both classpaths resolve 1.2.0, and keep guava's empty `listenablefuture` marker
off the androidTest classpath so the app's `1.0` is not contradicted. Neither can
be tested in this container, so each is one CI run; do them one at a time on top
of a green run, not stacked.

**AGP can go back to 9.4.0 once a run is green** -- it was never the cause. Say
the word.

**THE THIRD CORRECTION (build 857): GRADLE WAS INNOCENT TOO.** 9.5.0 ran (the
error's own doc URL says `docs.gradle.org/9.5.0/`) and failed with the identical
message. So AGP, `useConstraints` and Gradle have each been reverted or set and
each still fails, and `core-splashscreen` was measured twice -- resolving the
app graph at 1.0.1 and 1.2.0 moves neither artifact, it only adds
`appcompat-resources`. **848 really was green and 849 really is the first red**
(checked against the run list, not recalled), so the break is in that commit --
but every candidate in it that can touch dependency resolution is now excluded.

**SO THE FIX ATTACKS THE ERROR, NOT ITS ORIGIN**, and both halves are in
`app/build.gradle.kts` with the reasoning beside them:
- **`implementation("androidx.concurrent:concurrent-futures:1.2.0")`** -- the app
  resolved 1.1.0 and the test graph needs 1.2.0, so the `strictly` pin was
  unsatisfiable. Declaring 1.2.0 makes the pin *become* 1.2.0.
- **`exclude(group = "com.google.guava", module = "listenablefuture")` on every
  `*AndroidTest*` configuration** -- the app has it at 1.0 while ATF's guava needs
  the empty `9999.0-empty-to-avoid-conflict-with-guava` marker. With no dependency
  edge left in that configuration the constraint has nothing to constrain, and
  guava itself supplies `ListenableFuture` there. The app's own classpath keeps
  1.0 and is untouched.

**Gradle stays at 9.5.0 for this run** even though it is now proven innocent --
restoring 9.7.1 in the same commit would add a variable to a run that is already
testing two changes. It is one line once a run is green.

**THE METHOD LESSON, and it is the third time this shape has cost a run.** Both
wrong diagnoses were *mechanically* well-sourced -- the AGP release behaviour and
the AGP jar's own option table -- and both were wrong about the OUTCOME. When a
fix cannot be tested locally and the only test costs thirteen minutes, prefer the
change that is **known** to have been green over the one that is **reasoned** to
work, and keep the reasoned one for a run that can afford to fail.


### LATEST STABLE, not latest published -- and the difference is deliberate
`lifecycle` publishes **2.12.0-alpha02** and `lifecycle-runtime-compose` shows it
as its `<release>`. It stays at **2.11.0**. An alpha is not a version you ship in
the app a blind person depends on every minute, and "latest" in the owner's
instruction means the newest one its authors call finished. Every check in this
project filters `alpha|beta|rc|dev|eap` for that reason.

### What is verified here and what only CI can test -- stated rather than blurred
- **Kotlin 2.4.20 IS verified locally.** `tools/bootstrap.sh` downloads that exact
  compiler and its matching Compose plugin, and `check-all` type-checks the whole
  app with them. A Kotlin regression would have shown up before the push.
- **The dependency graph IS verified**: `tools/fetch-deps.py` re-resolved every
  artifact at the new versions off Google Maven and Maven Central.
- **NDK r30 IS verified safe for `minSdk 24`, and that mattered.** Every NDK
  release raises its floor eventually, and this project has always refused to
  raise `minSdk`, so "the NDK dropped API 24" would have been a silent way to
  break the app for old phones. Read from the NDK's OWN
  `meta/platforms.json` rather than from release notes -- **without downloading
  the 0.74 GB archive**: `dl.google.com` answers range requests (HTTP 206), so
  the zip's central directory was fetched from the tail, walked for that entry,
  and just that member inflated. It says **min 21, max 37** -- 24 clears the
  floor, and the ceiling is exactly our `compileSdk`. **Reuse this trick** for
  any large archive whose metadata is the only part that matters.
- **AGP, Gradle and CMake CANNOT be tested here.** There is no NDK in this
  container and the local `cmake` is 3.28, so CMake 4's semantics cannot be
  exercised. The one thing that could be checked was checked: our
  `cmake_minimum_required(VERSION 3.18.1)` is **above** CMake 4's floor of 3.5, so
  the headline CMake-4 breaking change does not apply to us.
- **If a build goes red, read WHICH STEP failed before backing anything out.** The
  four unverifiable bumps fail in four distinguishable places -- the sdkmanager
  install, Gradle's own startup, the CMake configure, or the native compile -- so
  one run names the culprit. Do not revert the whole set on a single red.

### The Actions versions could NOT be checked, and were therefore NOT touched
`actions/checkout@v4`, `setup-java@v4`, `upload-artifact@v4`,
`android-actions/setup-android@v3`, `gradle/actions/setup-gradle@v3`,
`softprops/action-gh-release@v2` and `reactivecircus/android-emulator-runner@v2`
are all still on their old pins. **This session's GitHub API is scoped to
`bariyasachin96/omni` only**, so every other repository answers *"GitHub access to
this repository is not ..."* -- that is a policy restriction, not something to
route around, and guessing a tag number would be exactly the guesswork rule 6
forbids. Ask for them and they can be bumped from a session that can read those
tags, or the owner can name the versions.

### Navigation Compose is STILL not adopted, and the reason is unchanged
The owner's "integrate whatever library is needed" does not resolve the objection
written up above it, because that objection is not about age: `navigation-compose`
2.10.0 has **zero** accessibility handling (grepped, not assumed), and this app's
per-screen window title is what announces the screen to a blind user. A NavHost
removes the window change and therefore the announcement. It remains the owner's
call and it needs one word; nothing about "use the latest" makes a silent screen
the right outcome.

## FOUR LIBRARIES RESEARCHED AT THE SOURCE, THREE NOT ADOPTED (owner, 2026-09-09)
*"Jetpack navigation components sahi tarike se use kariye ... ab to Google Maven
open ho gaya hai to aap research bhi kar sakte hain proper. Agar mujhe to nahin
pata hai aapko hi pata hoga is app ko jarurat padati hai ya nahin ... lifecycle
... hilt library ... AndroidX startup library."*

The owner delegated the judgement, so each one was read from its OWN artifact off
Google Maven rather than from memory or a blog. **Three are wrong for this app and
one was already present.** The reasons are below so nobody re-opens them on a hunch.

### 1. Navigation Compose -- NOT adopted, and the reason is the owner's ears
`navigation-compose:2.10.0` sources were downloaded and grepped. The result is one
line long and it decides the question:

    grep -niE "semantics|paneTitle|announce|accessib|liveRegion|contentDescription"
      -> ZERO hits across the whole library

NavHost is `AnimatedContent` swapping composables **inside one window**. It has no
accessibility handling of any kind.

**That is exactly what this app currently relies on.** Every screen is its own
Activity with `android:label` in the manifest -- "About", "Languages", "Mode
settings", "Voice setup" -- and a screen reader speaks a window's title when the
window appears. **That announcement IS how a blind user knows which screen they
landed on**, and this file already records two rounds of bugs getting it to fire
exactly once (the `focusOnOpen` removal and the two `setTitle` calls in
`onCreate`). Migrate to a NavHost and there is no new window, so **there is no
announcement at all** -- the owner would open Languages and be told nothing.

It could be rebuilt with `Modifier.semantics { paneTitle = ... }` per destination
(the app already does this once, on the main pager). But that is unproven for this
app, cannot be tested in this container, and lands on the path the owner walks
every few seconds. Twice this year a change that was correct about the mechanism
was wrong about the outcome on exactly that path.

**And it buys this app nothing.** No deep links (there are none and none are
wanted), no shared-element transitions (the owner is blind and the app
deliberately has no decorative motion), no nested graphs, no ViewModel scoping --
there are no ViewModels. Activities already survive configuration change and
already give predictive back for free via `enableOnBackInvokedCallback`.

**If the owner wants it anyway it is doable** -- one `paneTitle` per destination
plus an `AccessibilityChecksTest` case for each, so the announcement is proven on
the emulator rather than hoped for. Say the word; it is not being done on my own
judgement in either direction.

### 2. Lifecycle -- already correct, and it found the one real bug of this pass
`lifecycle-runtime-ktx:2.11.0` is declared and **is the current stable** (2.12.0
is alpha). Nothing to change there.

What the lifecycle read DID find is a genuine resource leak, in two places:

    MainActivity.testTts     = TextToSpeech(this, null, "com.tts.easyvoice")
    VoiceSetupActivity.testTts = TextToSpeech(this, null, "com.tts.easyvoice")

`TextToSpeech` holds a binding until `shutdown()` is called. **Neither was ever
shut down**, and nothing declares `android:configChanges`, so every rotate, fold,
resize and theme change destroys the Activity and leaks the connection plus the
Context it was built with.

**Only ONE of them was fixed, and the split is rule 5.**
- **`MainActivity` WAS left leaking on purpose, and the owner overrode that on
  2026-09-09** -- *"han yah jo MainActivity wala hai vah bhi fix kar dena"*. It is
  fixed now: `onDestroy` shuts the client down, and `newTestClient()` shuts down
  the previous one before replacing it, because the scan can finish more than
  once in one Activity. **DELIBERATE DEPARTURE**, on the owner's word.
  The reason it needed that word: AutoTTS's `NewSettingsActivity` creates two
  clients (`this.F` at :397 and `c3.n.g` at :473) and its whole `onDestroy` is
  `K.removeCallbacksAndMessages(null); super.onDestroy();` -- it shuts down
  neither, ours was that byte for byte, and rule 5's forbidden-justification list
  names "prevents a leak" explicitly.
- **`VoiceSetupActivity` IS fixed**, because it has no AutoTTS counterpart at all.
  AutoTTS has one settings Activity with one client; this screen exists only
  because the Voices tab became its own Activity in the 2026-08-13 Configuration
  departure, and the owner opens it **once per language**, so ours leaks several
  times per sitting where AutoTTS's leaks once. The client is ours, so releasing
  it is not a decision about AutoTTS's behaviour. It had no `onDestroy` at all.

**State across configuration change was checked too and is deliberately fine.**
There is no `rememberSaveable` anywhere and that is correct rather than an
oversight: almost every `remember { mutableStateOf(...) }` is seeded from the
**statics** (`EasyVoiceTtsService.*`), which are process-scoped and survive the
Activity, so a rotation re-reads the right value. The genuinely UI-only state that
does reset is the selected tab and the Languages search box -- both AutoTTS resets
too, and neither has been reported.

### 3. Hilt -- NOT adopted, and it would fight an enforced invariant
`hilt-android:2.60.1`. There is nothing here for it to inject:
- **there is no `Application` class at all** (checked: zero matches, and the
  manifest declares none), so `@HiltAndroidApp` would mean adding one purely to
  host a framework;
- **every piece of state is deliberately static.** That is the owner's own
  instruction -- *"sab kuchh static rakho ... preferences wala sahi nahin rahta"*
  -- and it is not a style preference: **`invariants.sh` #4b FAILS THE BUILD** if a
  screen reads a setting from preferences instead of the static. `LangStore`,
  `EasyVoiceLogger`, `EngineFinder` are `object`s; `SharedPrefsManager` is a thin
  per-screen wrapper. Hilt's whole value is replacing exactly that with injected
  scopes;
- it needs KSP or kapt, so every build gets an annotation-processing round;
- it adds to the APK, against a recorded "APK size kam kar do";
- and its usual payoff, swapping fakes in unit tests, does not apply -- the tests
  here are instrumented ATF checks driving the real screens on an emulator.

So it costs build time, size and an argument with an invariant, and returns
nothing this app can use.

### 4. AndroidX Startup -- ALREADY IN THE APK, and nothing of ours belongs in it
`androidx.startup:startup-runtime:1.1.1` is **already on the classpath**, pulled in
transitively by `emoji2`, `lifecycle-process` and `profileinstaller`. Its
`InitializationProvider` ContentProvider therefore already runs at every process
start. Adding the dependency would change nothing.

The question is only whether to write an `Initializer`, and there is nothing to
put in one:
- there is no app-wide initialisation. Every entry point loads lazily and on
  purpose -- `LangStore.ensureLoaded(this)` per Activity, `loadAllSettings()` in
  the service -- and **INVARIANTS #17 enforces** that any Activity which persists
  also loads first;
- the one thing that looks like a candidate, seeding `EasyVoiceLogger` in both
  `MainActivity.onCreate` and the service's `onCreate`, is a port of `c3.p`, which
  AutoTTS seeds lazily through `p.f(context)`. Moving it into a process-start
  initializer changes WHEN it is seeded, which is rule 5 territory and not a UI
  question;
- and the app's first priority is that the TTS service starts fast --
  *"latency bilkul aani hi nahin chahie"* -- so adding work to every process
  start, including the one a screen reader triggers, is the wrong direction.

### The dependency audit that came with it: everything current but one, and that one stays
Every declared coordinate was checked against its `maven-metadata.xml`:

    activity-compose 1.13.0        current      core-ktx 1.19.0            current
    material3.adaptive 1.3.0       current      lifecycle-runtime-ktx 2.11.0 current
    compose-bom 2026.08.00         current      kotlinx-coroutines 1.11.0   current
    ui-test-junit4-accessibility 1.12.0 current test:runner 1.7.0          current
    test.ext:junit 1.3.0           current
    core-splashscreen 1.0.1        <-- 1.2.0 exists, and 1.0.1 STAYS

**`core-splashscreen` WAS the only one behind, and the owner then reversed the
decision below in the very next message -- it is on 1.2.0 now. Read the
"EVERYTHING IS ON THE LATEST STABLE" section above; what follows is the
measurement, which is still accurate, and NOT the conclusion.** The public API
of 1.0.1 and 1.2.0 is **identical** (`javap` over both aars gives the same
members), the class lists match but for one inner lambda, and 1.2.0 adds a
runtime dependency on **`androidx.appcompat:appcompat-resources:1.7.0`**, a
library this project did not otherwise have. That is the price of being
current and the owner has taken it.

## THE LOCAL CHECK NOW RESOLVES androidx, AND IT WENT FROM 1,254 ERRORS TO 0 (owner, 2026-09-09)
*"ab Maine network access full de diya hai dekh lijiye ab."* They did, and this
is what it bought. **Measured from this session, not recalled:**

    dl.google.com        302   <- was "CONNECT tunnel refused"
    maven.google.com     301
    repo1.maven.org      200

and a real fetch, because a 302 on the root proves nothing: `material3-1.4.0.pom`
200/3,667 B, `compose-bom-2026.08.00.pom` 200/41,126 B, `gradle-9.3.2.pom`
200/9,034 B. The docs say the change takes effect on a NEW session and the
running one keeps its policy; **this container picked it up anyway**, so it was
used rather than deferred.

**THE HOLE THIS CLOSES IS THE ONE THIS FILE HAS COMPLAINED ABOUT FOR WEEKS.**
`kotlin-typecheck.sh` reported ~1,254 errors and the whole apparatus around it --
the baseline diff, the "our-own-name" counter, "do not chase these" written into
six separate sections -- existed to work around the fact that it could not
resolve a single androidx symbol. It now reports **0**, and the count is real.

    before   ~1,254 errors, none of them meaningful, Compose unchecked
    after         0 errors, and a Compose mistake fails locally

**And it got FASTER, which was not the point but is worth knowing.**
`tools/check-all.sh` now runs in **55 seconds** where the note in this file said
two minutes -- kotlinc no longer has to produce and format 1,254 error messages.
So the better check is also the cheaper one; there is no reason to skip it.

### Four pieces, each measured
| piece | what it fixed |
|---|---|
| **android.jar is API 37** -- the app's own `compileSdk`, unpacked from `platform-37.0_r02.zip` off `dl.google.com` | `Voice`, `AudioAttributes`, `Settings$Global`, `PackageInfoFlags`, `getLongVersionCode`, `generateViewId` all resolve. Every "known noise signature" in this file is gone with them. |
| **`tools/fetch-deps.py`** -- 110 artifacts resolved from `app/build.gradle.kts` | every androidx / Compose / Material3 call site |
| **`-jvm-target 17`** matching `compileOptions` | **218 of the first 245 errors**: kotlinc defaults to 1.8 and answers every androidx inline function with "cannot inline bytecode built with JVM target 11" |
| **`tools/check/genr.py`** -- generates R from `res/` the way AGP does | the last 26, all `unresolved reference 'R'`, plus one inference cascade |

### The Compose compiler plugin is on, and it earns its place in one test
`kotlin-compose-compiler-plugin:2.4.10`, the same version `build.gradle.kts`
applies. Without it this is legal to kotlinc and illegal to Compose:

    fun NotComposable() { Text("hi") }
    without the plugin   0 errors
    with the plugin      functions which invoke @Composable functions must be
                         marked with the @Composable annotation

**USE THE PLAIN ARTIFACT, NEVER `-embeddable`.** The embeddable one is shaded
against `org.jetbrains.kotlin.com.intellij`, which the kotlinc CLI's preloader
classloader does not provide -- it dies with `ClassNotFoundException:
org.jetbrains.kotlin.com.intellij.psi.PsiElement` before compiling anything.

### PROVEN TO FIRE, four negative tests, because a quiet checker is the worst outcome
    drop @OptIn(ExperimentalMaterial3Api::class)   3 errors  <- IS build 827
    R.drawable.ic_settings -> ic_settingz          1 error
    Text("Settings") -> Text(42)                   1 error
    @Composable called from an ordinary function   2 errors
    unmodified tree                                0 errors

The first is the exact failure that broke build 827, reproduced locally with the
same three `e:` lines. The second is new coverage: `xmlcheck.py` resolves
references made from XML and **nothing checked the ones made from Kotlin**, so a
misspelled `R.drawable.*` used to be caught only by AAPT in CI.

### THE `own()` HEURISTIC IS GONE -- IT FIRED A FALSE FAILURE, AND THAT IS THE LESSON
It counted unresolved references to our own names, on the reasoning that a file
which fails to resolve cascades into them. **That reasoning stopped being true
the moment android.jar became real and R was generated**: our own names now
resolve even without androidx, the count reads a legitimate 0, and the guard
built on it announced *"the compiler's wording has changed again"* and exited 1.
A tripwire whose premise has been repaired underneath it does not go quiet -- it
lies. Replaced by two things that are not heuristics:
- **kotlinc really compiled the package** -- assert the class-file count, so an
  empty error list cannot mean "it never saw the sources";
- **an error really would be reported and really would be caught by the grep** --
  compile a deliberate `fun deliberatelyWrong(): Int = "not an Int"` and require
  it to fail. This is what the K1/K2 wording break of 2026-08-27 needed and never
  had.

### Two modes, and REDUCED never fails
**FULL** is the above. **REDUCED** is what a container on the *Trusted* network
level gets: `dl.google.com` refused, so API 15 from Maven Central and no androidx
at all, ~1,112 errors. It prints the diff and **deliberately never exits 1**,
because one added androidx import produces a handful of errors that say nothing
about whether the code is right, and a check people learn to ignore is worse than
no check. `bootstrap.sh` degrades to it silently and says which mode it is in on
every run. **A proxy denial is still not to be routed around with a mirror.**

### Two traps inside `fetch-deps.py`, both found by the jars they silently dropped
- **`[1.12.0]` is a version, not a list.** androidx poms pin with Maven's
  hard-requirement syntax. Read literally it resolves nothing, and that took
  `compose.runtime`, `ui-graphics`, `ui-text` and `ui-unit` -- **the core of
  Compose** -- straight off the classpath while the run still reported success.
- **`androidx.compose.ui:ui` has no jar and that is correct.** It is
  `packaging=pom`, a shim that exists to depend on `ui-android`, where the classes
  are. The resolver reads `<packaging>` and skips those silently.

It parses the roots and the BOM **out of `app/build.gradle.kts` itself**, so it
cannot drift from what the app declares -- a hand-kept jar list would go stale the
first time a dependency is added and say nothing. Version conflicts are settled by
highest-wins; Maven uses nearest-wins, so this can differ, and for a type-check
classpath the newer API surface is the safe direction.

### `set -o pipefail` broke the new self-checks TWICE, in two different ways
Both were silent or misleading failures of a checker that was working, which is
the exact failure mode this whole section exists to prevent. Recorded because a
third one is likely:
- **`grep -q` SIGPIPEs the producer.** The self-test ran
  `kotlinc ... 2>&1 | grep -q ": error: "`. `grep -q` exits on the first match,
  kotlinc takes SIGPIPE, and under `pipefail` the pipeline reports 141 -- so the
  self-test reported "kotlinc is not reporting errors" while kotlinc was printing
  exactly the error asked for. Write to a file and test `-s`, which is what
  `errs()` already did and why `errs()` never had this bug.
- **`find` on a missing directory kills the script with no message.**
  `classes=$(find "$WORK/out" ... | wc -l)` -- in REDUCED mode compilation
  produces no output directory at all, `find` exits non-zero, `pipefail` carries
  it out of the command substitution and `set -e` ends the run between two
  `echo`s. The mode "failed" with a blank line. Wrap it: `$( { find ... || true; } | wc -l )`.

**And the assertion itself was wrong for one mode.** "kotlinc really compiled the
package" cannot hold in REDUCED, where androidx is unresolvable and producing no
classes is correct behaviour. It is FULL-only now. The error-reporting self-test
runs in both, because it is about the compiler and the grep rather than about
this app's dependencies.

### A process trap worth more than any of them
The both-modes verification run failed with `line 126: es: command not found`.
Nothing was wrong with the script. **bash reads a script incrementally, and I
edited it while it was running**, so execution resumed at a byte offset that now
landed mid-token. If a shell script fails with a nonsense fragment as a command
name, check whether it was edited mid-run before debugging a single line of it.

**The Gradle side needed no change and never did.** `google()` IS Google Maven and
was already declared in all three places it can be -- `pluginManagement`,
`dependencyResolutionManagement` (with `repositoriesMode.set(FAIL_ON_PROJECT_REPOS)`)
and `buildscript` -- which is why CI has always resolved AGP, Kotlin, the Compose
BOM and the rest and published an APK. What was blocked was this container's
egress, and only that. **Nothing about what the app builds, ships or does has
changed by any of this**; the change is entirely in what can be caught before a
push instead of thirteen minutes into a CI run.

## "1 of 3" WAS BEING SAID TWICE, AND THE SECOND ONE WAS OURS (owner, 2026-09-09)
*"bahut sari jagah per one of three, two of three ... mere khyal se yah thoda
double hai ... jo already TalkBack announce karti chijen hain vah chijen aap
rakhni hi nahin hai."* Right, and the doubled one was the tab strip.

**THE RULE THE OWNER STATED, and it now has a mechanical test:** before adding
any name, state or position, check whether the delegate already writes it onto
the FOCUSED node. If it does, ours is a second voice saying the same thing.

**THE DOUBLE: `MainActivity`'s tab name carried `", N of M"`.** The app has three
tabs, which is exactly the "one of three, two of three" the owner heard. Proven
from androidx's own source rather than argued:
- `PrimaryTabRow` applies **`Modifier.selectableGroup()`** (`TabRow.kt:401`);
- every `Tab` sets **`Selected`** with `role = Role.Tab` (`Tab.kt:177`);
- `setCollectionInfo` derives a `CollectionInfo` from a `SelectableGroup` whose
  children carry `Selected`, and `setCollectionItemInfo` derives THIS tab's index
  by counting its selected siblings (`CollectionInfo.android.kt`).

So the delegate announces "1 of 3" by itself, and it lands on the real node
rather than a fake child, so a reader that walks no fake children gets it too.
Our suffix was a second copy. The name is now the title alone.

**THE STALE JUSTIFICATION IS THE LESSON HERE.** The comment at that line defended
the suffix with the owner's own device test of 2026-08-13 -- and that test was
real. It is simply not evidence any more: the app was **Views and `TabLayout`**
then. `PrimaryTabRow`, the Compose migration and `evControl` all arrived
afterwards, and the mechanism changed without anyone revisiting the note that
rested on it. **A device result is evidence about the code that was running when
it was taken.** Re-date it before quoting it.

**A SECOND FINDING, smaller: a half-declared collection.** The Configuration
three-dot menu's two items carried `CollectionItemInfo(0/1)` while **nothing
published the matching `CollectionInfo`** -- unlike the language dropdown in
`VoiceScreen`, which declares both. `setCollectionItemInfo` writes the developer
property with no cross-check, so a reader was handed a position out of a
collection whose size it was never told, on a two-line action menu. Both
`listItem` arguments are gone. Do not "complete" it with a `collectionInfo`
Column instead -- the ask was less position noise, not more.

### Every other "N of M" in the app was checked and NONE of them is ours to remove
- **the mode radios and the three reading groups** -- `LabeledRadioGroup` and
  `ModesScreen` wrap their rows in `selectableGroup()`, so the "1 of 4" is
  derived by the delegate from the group. **We add nothing there.** That is the
  library announcing itself, which is what the owner asked for.
- **the Languages rows and the Configuration rows** -- `collectionItemInfo` per
  row is ours and must stay: **Compose sets it on no lazy item by itself**, and
  it is what makes a row announce as a list item instead of a button (owner,
  2026-09-04). The `LazyColumn` supplies the container half.
- **the language dropdown in `VoiceScreen`** -- `collectionInfo` on the Column
  plus `collectionItemInfo` per item, a matched pair, because a menu's content
  is not a lazy list and Compose publishes neither.
- **"Language 1 of 3" on Voice setup** is a VISIBLE line, not a duplicate.
  Nothing else tells you which language of how many the Previous/Next pair is
  moving through -- there is no collection on that screen at all. It stays.
- **the slider's `stateDescription = "100 of 500"`** is a REPLACEMENT, not a
  double: `getInfoStateDescriptionOrNull` reads ours first and only falls back to
  `template_percent` **when it is null**. Keeping it matters -- Speed runs 10..500
  where 100 is the engine's own rate, so "20 per cent" would be actively wrong.
  (`template_percent` is the string the owner spotted in the APK; it is
  androidx's fallback for exactly this, and it is why it ships.)
- **the slider's `-`/`+` Toast** is not a position announcement. Those buttons
  are their own focus stop, so nothing re-reads the slider when one is pressed.

### Re-verified in the same pass, so do NOT re-open
- **the dropdown anchor's `stateDescription` / `contentDescription` are NOT a
  double.** `ExposedDropdownMenu.kt` applies both **only** under
  `if (anchorType == SecondaryEditable)`; ours is `PrimaryNotEditable`, so the
  library sets neither and our two lines are the only source. Checked in
  androidx-main as well as the pinned api file -- it has not changed.
- **`Selected` does not double with "Selected".** For a non-Tab node the delegate
  sets `info.isChecked` AND a `stateDescription` of "Selected"/"Not selected",
  and androidx's own comment says why: TalkBack would otherwise say "checked".
  The stateDescription is what TalkBack speaks, once.
- **a Switch's "On"/"Off"** comes from `ToggleableState` + `Role.Switch` in the
  same function, guarded by `stateDescription == null`. We pass `toggle` and no
  `state`, so the library's own words are used.
- **A LATENT androidx BUG, worth knowing but not ours to trip.**
  `setCollectionItemInfo` has **no `return`** after the developer branch, while
  `setCollectionInfo` does. So a node carrying its own `collectionItemInfo`
  *inside a `selectableGroup`* gets the info written twice and the group-derived
  index wins. Nothing in this app does that -- our radio rows pass no `listItem`
  and our list rows have no selectable-group parent -- and it must stay that way.

**Sizes, switches, checkboxes and radio buttons: the emulator job is the answer,
not a reading.** `AccessibilityChecksTest` runs Google's Accessibility Test
Framework over **29 screen states in BOTH colour schemes** on every CI run, and
its preset includes the touch-target and contrast checks. That job **fails the
build**, so the 48dp minimum and every contrast pair are measured on the real
app every time rather than argued here.

## THE THEME IS MATERIAL 3's OWN AND IT FOLLOWS THE SYSTEM (owner, 2026-09-08)
*"sare buttons ke colour ... sab kuchh accessibility ke hisab se sahi hai na ...
material accessibility guideline mein kya kahta hai, kaisi theme rakhni chahie
... library se hi import karna hai, extra khud likhne ki jarurat nahin ... system
dark hai to app mein bhi dark, system light hai to light."*

**Two things changed and both are removals.** The app had ONE hand-built dark
scheme with fourteen colours picked by hand, and it stayed dark whatever the
phone was set to. It now takes `lightColorScheme()` and `darkColorScheme()`
**unmodified** and picks between them with `isSystemInDarkTheme()`.

**WHY UNMODIFIED IS THE ACCESSIBLE ANSWER, MEASURED RATHER THAN ASSUMED.**
Material 3 builds every scheme from tonal palettes, and the accessibility of a
colour PAIR is a property of the TONE GAP, not the hue: a role and its `on` role
are placed far enough apart that the pair passes by construction. Both baseline
schemes were resolved from androidx's own `ColorLightTokens`, `ColorDarkTokens`
and `PaletteTokens` and every pair this app draws was put through the WCAG
formula:

| pair | LIGHT | DARK |
|---|---|---|
| body text on the page | 16.23 | 14.35 |
| text on a surface | 16.23 | 14.35 |
| secondary text | 8.88 | 10.91 |
| filled button label | 6.44 | 7.71 |
| accent / outlined button label on the page | 6.12 | 10.91 |
| section header text | 13.32 | 7.23 |
| app bar title on its bar | 13.32 | 7.23 |
| control outline | 4.33 | 5.87 |
| error text | 6.21 | 10.89 |
| text on a menu | 14.85 | 12.57 |

against floors of 4.5 for text and 3.0 for a UI component. **Nothing is close.**
Hand-picking a replacement could only make one of those worse, and it would now
have to be done TWICE.

**FOUR PAIRS READ LOW AND ALL FOUR ARE CONTAINER FILLS, NOT TEXT** -- the section
header bar (1.23 light / 1.99 dark against the page), the divider (1.62 / 1.99),
and the selected chip and switch track (1.23 / 2.00). **They are low in Google's
own baseline, in both schemes**, so "fixing" them means overriding the library
with numbers of ours -- the move this project has had to undo before. None of
them carries information by colour alone: the selected filter chip draws a
**check icon**, a Switch is read by thumb position and its unchecked track is
outlined with `outline` (4.33 / 5.87), and the divider and the header bar are
decoration with 13.32 / 7.23 text on them. And Google's own **Accessibility Test
Framework -- the engine the CI job runs -- has `TextContrastCheck` and
`ImageContrastCheck` and no check for a fill against the page at all** (read from
`AccessibilityCheckPreset.java`, which lists all fourteen).

**The two component overrides that remain are role PAIRINGS, not colours, and
both were re-measured.** The FAB is `primary` / `onPrimary` rather than
Material's default `primaryContainer` / `onPrimaryContainer`, which gives its
shape 6.12 / 10.91 against the page where the default gives 1.23 / 1.99 -- right
for the screen's primary action, and it is why the "invisible FAB" of the
Compose migration cannot come back. The top app bar is `primaryContainer` with
`titleContentColor = onPrimaryContainer`, i.e. the pair, at 13.32 / 7.23.

**DYNAMIC COLOUR WAS RESEARCHED AND DELIBERATELY NOT ADOPTED.**
`dynamicLightColorScheme` / `dynamicDarkColorScheme` **are** in the pinned
material3 1.4.0 (`api/1.4.0-beta01.txt`, both `@RequiresApi(S)`), and the tone
gaps survive a wallpaper-derived palette, so it would not be unsafe. It would
make the app's colours different on every device, which means the accessibility
job would be measuring whatever the emulator's wallpaper produced instead of what
the app ships. A fixed baseline is what makes that job's result mean anything.
Turn it on if the owner asks; do not slip it in.

**There is NO contrast-level API to adopt.** Android 14's system contrast setting
and M3's high-contrast schemes are **not** in material3 1.4.0 -- `grep -i contrast`
over that api file returns nothing. Do not invent one.

### `enableEdgeToEdge()` now takes NO arguments, and the note that argued against that is REVERSED
The section below ("Both `SystemBarStyle`s are stated explicitly, and the default
would have been a real bug") was correct **for an app that ignored the system**
and is now exactly wrong. `SystemBarStyle.dark(...)` pins LIGHT bar icons; over a
light window the clock and the signal icons disappear. The bare
`enableEdgeToEdge()` is right, and it is not a shortcut -- its defaults, read
from `EdgeToEdge.kt`, are `auto(TRANSPARENT, TRANSPARENT)` for the status bar and
`auto(DefaultLightScrim, DefaultDarkScrim)` for the navigation bar, so the scrims
are androidx's own recommended values instead of two of ours, and they are used
only on API 28 and below.

**The two detectors cannot disagree, and that is what makes it safe:**

    SystemBarStyle.auto  (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
    isSystemInDarkTheme  (LocalConfiguration.current.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES

the same predicate on the same field, so the bar icons and the app's colours are
decided by one signal.

**`SystemBarStyle` IS deprecated in androidx-main and is NOT deprecated in
activity 1.13.0**, which this build pins -- checked in that version's api file.
That is the same trap `TabRow` and `ExposedDropdownMenuBox` already sprang here.
**Do not migrate to `WindowCompat.enableEdgeToEdge` on the strength of
androidx-main.**

### The window before Compose draws is split by the night qualifier
`values/styles.xml` is now the LIGHT theme (parent
`@android:style/Theme.Material.Light.NoActionBar`, `windowLightStatusBar` and
`windowLightNavigationBar` **true**) and `values-night/styles.xml` the dark one
(parent `Theme.Material.NoActionBar`, both false). Both framework style names
were confirmed in AOSP's own `core/res/res/values/themes_material.xml` rather
than recalled. `windowLightNavigationBar` also closes a gap the theme always had
-- it was never stated, so that bar's icons were unspecified.

Only `AppTheme` is repeated. `AppTheme.NoActionBar` declares
`parent="@style/AppTheme"`, a resource reference that resolves per
configuration, and `Theme.EasyVoice.Splash` reads `@color/ev_background`, which
does the same -- so the two styles defined once serve both modes and cannot
drift. **`ev_background` is the library's value, not a choice**: `#FEF7FF` is
`ColorLightTokens.Background` = `PaletteTokens.Neutral98` and `#141218` is
`ColorDarkTokens.Background` = `Neutral6`. Keeping them equal to the scheme is
what makes splash-to-Compose a change of content rather than a flash of colour.

### Every screen is now checked in BOTH schemes
`EasyVoiceTheme(darkTheme: Boolean = isSystemInDarkTheme(), content)` -- the
signature Compose's own templates use -- exists so a test can state the scheme
instead of inheriting the emulator's. An emulator image is in exactly one mode,
so leaving it to the device would have meant one of the two schemes never being
rendered under ATF with nothing saying which: the same hole the reading mode and
the Languages list layout each turned out to have.

`setContent` can only be called once per test, so the switch is a state object
the composition reads (`darkScheme`); flipping it recomposes the same tree into
the other scheme and every remembered value -- a typed query, an open menu --
survives, so the interaction tests keep what they set up. `sweepSchemes()` runs
the ATF checks in light and then in dark, and every test goes through it.

**One earlier section is superseded by this one.** "Colour roles we never
declared" argued for replacing `outlineVariant` with `#727880` and
`secondaryContainer` with `#42707F`. Those numbers are gone with the hand-built
palette; the reasoning above is what replaces it. The *contrast tables* in the
older sections describe the OLD palette and are a record, not current fact.

**Blocked while doing this:** `www.w3.org` answers 403 through the egress proxy,
so WCAG's own Understanding pages could not be read here. The floors used are the
ones already recorded in this file, and ATF's source -- which is reachable and is
what CI actually runs -- was read instead.

## THE ACCESSIBILITY JOB NOW COVERS STATES, NOT JUST SCREENS (owner request, 2026-09-04)
*"Sabhi screen per accessibility job apply karo ... to aapko acche se pata
chalega aur jahan per bhi problem aaye vahan per ekadam properly source ke
through fix karna."* Every screen already had a test; what none of them had was
its **other states**. 16 tests to **25**, and the first finding was in the test
harness itself.

**THE SEED WAS NON-DETERMINISTIC, AND IT MAY HAVE BEEN CHECKING THE WRONG
SCREEN.** `prefs.getReadingMode()` reads `EasyVoiceTtsService.modeInt`, whose
default is **0 = "none"**, and `LangStore.ensureLoaded` may then set it from
`auto_mode` -- which is 3 when Google TTS is on the emulator image and 0 when it
is not. So the mode was whatever the device and the previous test left behind.
That matters because **`LanguagesScreen` answers "none" and "dual" with a
one-line "not available" message instead of the list**: the 137-row list, its
search field, its filter chip and its three buttons could have been going
unchecked with nothing saying so. The seed now states `modeInt = 4` (mix -- the
mode the owner runs, which has a language list and shows the Add language
button) and every Advanced flag explicitly.

**The nine states that had never been rendered**, each because a view only exists
after an action or in another branch:

| test | the view nothing had drawn |
|---|---|
| `mainScreenAddLanguageButton` | **the FAB.** It is drawn only at `currentPage == 1` and `mainScreenSettled` starts on page 0, so the one control that once shipped INVISIBLE (Material3 fills an ExtendedFAB with `primaryContainer`, 1.28:1 here) had never been measured |
| `advancedTabEverythingOn` | every switch CHECKED, and the Group size dropdown ENABLED. The four checked/unchecked thumb-and-track ratios in this file were calculator work, never measured |
| `advancedTabPunctuationLocked` | the one DISABLED control (`punctuationModeInt == 3`) |
| `languagesListFiltered` | the chip SELECTED -- its fill is its only boundary (`FlatSelectedOutlineWidth = 0.dp`, transparent border) at a hand-computed 3.44:1 |
| `languagesListSearching` | the **Clear search** button, which exists only once text is typed |
| `languagesListNotAvailable` | the "none"/"dual" branch, where that one line IS the whole screen |
| `configurationTabEmpty` | the first-run empty state |
| `voiceSetupNoVoices` | a language the scan found no voice for |
| `voiceSetupNoLanguage` | no entry at that index at all |

**`ktcheck` now reads `app/src/androidTest` too**, and that was a gap of its own:
the instrumented tests compile ONLY in CI's emulator job, so an unbalanced brace
or a missing import there costs a thirteen-minute run to discover. It is a
structure check and needs no classpath, so it is free. `evpaths.android_test_dir()`
returns `None` when the directory is absent, so a tree without tests skips it
rather than failing. Negative-tested both ways: a deliberate `fun broken( {`
appended to the test file is reported at its real line and the clean file
passes.

**THE SECOND PASS FOUND THE STATES THE FIRST ONE COULD NOT SEE (25 to 29).**
Reading every screen's branches against the test list turned up four views no
test had ever drawn, and one of them was invisible for the same reason the
reading mode was:

**THE LANGUAGES LIST HAS TWO LAYOUTS AND THE DEVICE PICKED WHICH ONE RAN.**
`LanguagesScreen` groups the device REGION's languages above the rest, and
`grouped` is true only when `regionIdx` and `otherIdx` are both non-empty --
where `regionLangs` is built from voices whose `locale.country` equals
`Locale.getDefault().country`. On the emulator's en_US image the GROUPED branch
renders and the flat one never does; on an image with no country it is the
other way round. So one of the two has always gone unchecked, and nothing said
which. `Locale.setDefault(Locale("en", "US"))` is now stated in `seed()` beside
`modeInt`, `languagesListUngrouped` states the other, and each test ASSERTS the
branch it got rather than trusting the image. An `@After` restores the process
locale.

**Grouped is also the branch with the accessibility machinery in it**, which is
what makes leaving it unrendered expensive: two `SectionHeader`s live INSIDE the
`LazyColumn` -- the one thing this document's own rule forbids -- and it is
allowed there only because the list overrides `collectionInfo` with the real row
count and each row's `collectionItemInfo` index runs continuously ACROSS both
groups. If any of that is wrong a reader counts the headings as rows and every
announced position is off by two.

The other two are the empty-result messages, which exist precisely so a blind
user can tell an empty filter from a frozen screen: `languagesListNoMatch` (a
search matching nothing) and `languagesListFilteredNoMatch`. The second string
had no way of being rendered at all -- in mix mode the required languages are
always ticked, so the "My languages" filter alone can never empty the list;
filter PLUS a search that matches nothing can.

**Checked and already covered, so do not add them again:** `voiceSetup` passes
`total = 3` at index 0 and `voiceSetupNoVoices` index 3 of 4, so the Previous/Next
row is rendered with EACH button disabled in turn; every one of the nine screen
composables, `RequiredEnginesDialog` included, has a test.

**A trap this pass hit, worth keeping.** `assertExists` and `assertDoesNotExist`
are MEMBER functions of `SemanticsNodeInteraction`, not top-level extensions like
`onNodeWithText` -- importing them is an unresolved reference and would have
failed the compile in CI. `ktimports` cannot see that, because it does not know
androidx. **Before importing a Compose test symbol, check whether it is a member
or an extension.**

**The rule this establishes for new UI:** a screen's test is not done when the
screen renders. Ask what the screen looks like after a tap, with the switch the
other way, with the list empty, and in the mode the branch above it takes -- and
write one test per view, not per screen.

## I DIAGNOSED THE CONFIGURATION ROW WRONG, AND THE CORRECTION IS THE LESSON (2026-09-04)
The owner reported on build 834 that the three-dot action button beside each
language was unreachable. I reverted the whole screen to `c04fc01` and wrote up
`collectionItemInfo` as the cause. **Both halves of that were wrong**, and the
owner corrected it themselves: *"meri galti thi ki maine action button wale ke
liye ulta sidha bol diya, vah sahi kaam kar raha hai."* The screen is restored to
build 834's version (`bd797b4`).

**Two independent proofs that `collectionItemInfo` is innocent, so this is never
re-litigated:**

1. **Build 834's `accessibility` job was GREEN**, both jobs, all sixteen tests.
   `AccessibilityChecksTest.configurationRowMenuOpen` finds and clicks
   "More actions for English (eng)" **in the merged tree** -- which is exactly
   the thing I claimed had become unreachable.
2. **The delegate's own focus rule, read rather than assumed:**

       private fun isScreenReaderFocusable(node, resources, isInMergingHiddenSubtree) {
           if (node.isHidden || isInMergingHiddenSubtree) return false
           // If the node explicitly merges its descendants, we map it directly to
           // the merging algorithm on the accessibility side.
           if (node.unmergedConfig.isMergingSemanticsOfDescendants) return true
           ...

   An `IconButton` merges its own descendants, so it is its own focus stop **no
   matter what its parent row carries**, and `collectionItemInfo` is not
   consulted anywhere in that function. `setCollectionItemInfo` likewise writes
   only `info.setCollectionItemInfo(...)` on that one node and touches nothing
   about children.

**What DID break it is still true and still the rule.** `evControl`
(= `clearAndSetSemantics`) on that row dropped its whole subtree from the
accessibility tree, which is what hid the button; builds 832 and 833 failed that
same test on exactly it. **Never put `evControl` on a node that contains an
interactive child.** A plain `semantics {}` block adds without clearing, and that
is what the row uses.

**THE REAL MISTAKE, and it is a method mistake rather than a code one.** I had a
device report and a plausible mechanism, and I shipped the mechanism as the
diagnosis without checking the one piece of evidence that could refute it -- the
`accessibility` job for the very build being reported on, which was still running
and which came back green. **When a device report and a CI result disagree, get
the CI result before writing the fix**, and when a report contradicts a test that
exercises the exact same interaction, say so and ask rather than reverting good
work. Reverting cost a correct change and a build.

## evControl MUST NOT WRAP A NODE WITH AN INTERACTIVE CHILD (caught by CI, 2026-09-04)
Build 832's `build` job passed and published the APK; its **`accessibility` job
failed**, on exactly one of sixteen tests:

    configurationRowMenuOpen FAILED
    could not find any node that satisfies:
        ContentDescription = 'More actions for English (eng)'
    However, the unmerged tree contains '1' node that matches.

**That is a real defect, not a test artefact, and the wording of the failure is
its whole diagnosis.** `evControl` is `clearAndSetSemantics`, and clearing a node
drops its **entire subtree** from the accessibility tree -- an empty
`replacedChildren` is precisely what that means. The Configuration row's
`trailingContent` is a real three-dot `IconButton`, so wrapping the row made
**"More actions for &lt;language&gt;" unreachable for a screen reader, and Delete
configuration and Disable language with it**. The node still existed in the
UNMERGED tree, which is why the test could see it there and not in the merged one.

**THE RULE: never put `evControl` on a node that CONTAINS an interactive child.**
On every other control the subtree is a label and an icon, and losing it is the
point. Where the subtree holds something the user must reach, use a plain
`semantics {}` block, which adds without clearing -- that is what the
Configuration row does now, and it is the one row in the app that must not use
`evControl`.

**A child passed `onCheckedChange = null` / `onClick = null` is NOT interactive**,
which is the whole point of the Material row pattern, so `SettingSwitch`'s Switch,
`LanguageCheckRow`'s Checkbox and the mode rows' RadioButton are all safe. The
mode row's "Settings" button is safe for a different reason: it is a **sibling**
of the cleared Row, not inside it. Every `evControl` site was re-audited against
that test and only the Configuration row failed it.

**Two things worth keeping about how this was found.** The `accessibility` job is
the only check in this project that runs the real app, and it is the third time it
has earned its place -- **read its failure before assuming the APK is fine**,
because the `build` job passing means only that the code compiled. And the owner
would not have noticed quickly: the three-dot menu is not on the path they use
every minute, so this would have sat broken.

## THE ROLE COVERAGE TABLE: every control type, and who announces it (2026-09-04)
Owner: *"user jab button per ungali rakhe ya tab per ya radio button ya checkbox
ya switch ya kahin per bhi ungali rakhe ... ya swiping kare ... to use pata
chalna chahie ki yah radio button hai, yah tab hai, yah button hai, yah dropdown
list hai ... jo library mein available hai bas vahi fix karni hai."*

**Nothing here is a role string of ours.** Every name below is written by
androidx's own delegate, from the library's own `Role` enum and its own string
resources. `evControl` supplies no wording at all -- it only clears the merged
children that were stopping the delegate from putting the role on the node the
reader is actually focused on. Explore-by-touch and swipe both land on that same
node, which is why one fix answers both.

| control | role | what the reader is given | who writes it |
|---|---|---|---|
| every button, the FAB, slider - / + | `Role.Button` | `android.widget.Button` | delegate, `Role.toLegacyClassName()` |
| the two tabs | `Role.Tab` | `roleDescription` "Tab" + `isSelected` | delegate, `R.string.tab` |
| mode radios, the three reading groups | `Role.RadioButton` | `android.widget.RadioButton` + "Selected"/"Not selected" | delegate, `R.string.selected` |
| language rows, the My-languages chip | `Role.Checkbox` | `android.widget.CheckBox` + checked state | delegate |
| the nine Advanced switches | `Role.Switch` | `roleDescription` "Switch" + "On"/"Off" | delegate, `R.string.switch_role` / `state_on` / `state_off` |
| every dropdown anchor | `Role.DropdownList` | `android.widget.Spinner` + Expanded/Collapsed | delegate |
| Configuration rows, dropdown menu items | none, by design | `collectionItemInfo` -> "item N of M" | delegate, from CollectionItemInfo |
| Speed / Volume / Pitch | none needed | `android.widget.SeekBar` | Material3 Slider, **ungated** |
| the three-dot and clear-search buttons | `IconButton` | `android.widget.Button` | delegate, gate passes on its own |

**Two rows are deliberately NOT wrapped and must stay that way.** A `Slider` sets
its class name from `ProgressBarRangeInfo` with **no gate**, so it already reaches
every reader, and wrapping it would cost `ValueSlider`'s `setProgress` -- the
thing that makes one screen-reader swipe move exactly 5. An `IconButton`'s
`Icon(contentDescription = null)` adds no semantics modifier at all, so the button
has no semantics children, the gate passes by itself and the class name is already
on the focused node.

**There is no `Role.ListItem` in Compose**, so a list row is named the way the
platform names one: the container publishes `CollectionInfo` (a `LazyColumn` does
this itself) and the row publishes `CollectionItemInfo`. That is the library's own
answer, and it is why the Configuration rows carry no role -- a role there would
make them buttons again.

## THE REAL FIX FOR ROLES IS SHIPPED: evControl (owner, 2026-09-04)
*"Jo asali hal hai vah complete kar hi do."* Done. The shape written up the day
before as "the owner's call" is now the app's only way of describing a control,
and the two things that had been bolted on instead -- `accessibilityClassName`
and a hand-repeated `contentDescription` at every call site -- are gone.

**`Modifier.evControl(name, role, enabled, state, toggle, isSelected, listItem,
action)`** in `ComposeTheme.kt`. It is `clearAndSetSemantics` with the control's
whole accessible identity declared inside it, and that single step closes the
gap the owner has reported twice:

    if (semanticsNode.isFake || semanticsNode.replacedChildren.isEmpty()) {
        if (role == Role.Tab)         info.roleDescription = "Tab"
        else if (role == Role.Switch) info.roleDescription = "Switch"
        else                          info.className = role.toLegacyClassName()
    }

A cleared node has **no** `replacedChildren` -- that is the documented contract
of the property ("node marked as clearAndSetSemantics will not have children",
`SemanticsNode.kt`) -- so the gate PASSES and the role lands on the real,
focused node. `emitFakeNodes` cannot fire either: it is guarded by
`unmergedConfig.isMergingSemanticsOfDescendants`, and a cleared config does not
merge. **One node carries the role, so the "button button" doubling of
2026-09-03 is now structurally impossible**, not merely avoided by discipline.

**ORDER IS LOAD-BEARING, and it is the one way to get this wrong.**
`LayoutNode.calculateSemanticsConfiguration` walks `nodes.tailToHead` and a
clearing node does `config = SemanticsConfiguration()` -- a RESET of everything
collected so far. So the clearing modifier wins only if it is visited LAST,
which means it must be the **head-most** semantics node, i.e. **first in the
chain we write**. Two shapes follow from that and both are in the code:
- passed as a component's `modifier` (`EvButton`, `Tab`, `FilterChip`,
  `ExtendedFloatingActionButton`, `DropdownMenuItem`) it is already head-ward of
  everything the component appends, so `modifier.evControl(...)` is correct;
- chained with one of OUR own semantics modifiers (`toggleable`, `selectable`,
  `menuAnchor`) it must come FIRST: `Modifier.evControl(...).toggleable(...)`.
  Written the other way round the component's own semantics are applied on top
  of the reset and the fake child is back.

**Why it is safe, and this is the part that had to be right.**
`clearAndSetSemantics` touches SEMANTICS ONLY: the component's `clickable` /
`toggleable` pointer input is untouched, so a finger still activates the real
Material control with its ripple and its state. For a screen reader the
activation path is `ACTION_CLICK`, and `action` is what declares it --
`onClick { action(); true }` puts `SemanticsActions.OnClick` in the cleared
config and the delegate turns that into `info.addAction(ACTION_CLICK)`. **The
old bug this file warned about was `onClick(label, action = null)`** -- a label
with no action, which replaced the real one with nothing and once made the
Configuration rows unopenable. `action` is a parameter here, so that shape
cannot be written by accident.

**State stays the library's**, never a string we invent: `toggle` +
`Role.Switch` makes the delegate say "On"/"Off" from its own
`R.string.state_on`/`state_off`; `isSelected` on anything that is not a Tab
makes it say "Selected"/"Not selected"; and a **selected** Tab or RadioButton is
deliberately left un-clickable, because the delegate drops ACTION_CLICK for
exactly those two roles when selected. `enabled = false` adds `disabled()`,
which is what `semanticsNode.enabled()` reads before it will add the click
action at all.

**`EvButton` is now the only button in the app.** One Material3
`Button`/`OutlinedButton`, one place that decides the accessible name, the role,
the disabled state and the leading icon -- which is what the owner asked for
("us type ke buttons laga dene chahie taki har screen reader achhe se read
kare"). Eleven call sites that each repeated `.semantics { contentDescription =
... }` and silenced their own label with `clearAndSetSemantics` are gone.
`outlined = true` is the Material emphasis ladder, so a two-action row states
which action it is for: Apply filled / Cancel outlined, Next filled / Previous
outlined, Test filled / Default outlined.

**THE TAB ROLE IS FIXED TOO, which the class-name approach could never do.**
`Role.Tab` has no legacy class name at all -- the delegate answers it with
`roleDescription` -- so there was nothing for `accessibilityClassName` to carry,
and `android.app.ActionBar$Tab` was tried on device and not recognised. Clearing
the node is what opens the gate, and then the LIBRARY writes its own "Tab" onto
the node in focus. `isSelected` comes with it, so a reader also says which tab
is current.

**What is deliberately NOT wrapped, because the library already puts it on the
focused node:**
- **`IconButton`** -- `Icon(contentDescription = null)` adds no semantics
  modifier at all, so the button has no semantics children, the gate passes by
  itself and `info.className = "android.widget.Button"` is already right;
- **`Slider`** -- `info.className = "android.widget.SeekBar"` is set from
  `ProgressBarRangeInfo` with **no gate**, and `ValueSlider`'s `setProgress` is
  what makes a screen-reader swipe move exactly 5. Wrapping it would cost that.

## The dropdown opens at the language you already chose (owner, 2026-09-04)
*"AutoTTS mein preferred languages ka jo dropdown list hai ... jab dropdown list
open karte hain to jo language select kari hui hai vahan se hi shuru hota hai.
Hamare mein aisa nahin hai, pahle language se hi aa jata hai."*

Correct, and on a 137-language menu it means scrolling past everything to find
out what is set, every single time. `ExposedDropdownMenu` takes a `scrollState`
(checked in the pinned material3 `api/1.4.0-beta01.txt`), so `LabeledDropdown`
holds one and scrolls it to the selected row when the menu opens.

**The offset is MEASURED, not computed.** A language label can wrap to two
lines, so counting a fixed row height would drift further wrong the further down
the list the answer is. `Modifier.onGloballyPositioned` on the selected row
reports where it really is, and `positionInParent()` is taken inside the content
`Column`, so it does not move when the menu scrolls and there is no feedback
loop.

**No delay and no frame-counting**, which matters because the owner has banned
timing constants twice. Layout has not happened when the menu first composes, so
the effect is keyed on the measurement as well as on `expanded`: the position
arrives a frame later, the key changes, and the scroll runs then.

## The screen no longer introduces itself twice (owner, 2026-09-04)
*"Jo bhi activity ham open karte hain ... pahle announce karta hai ... Mode
settings double double announce kyon ho raha hai."* Two separate causes, both
ours, both removed.

**1. `focusOnOpen` was reading the heading a second time.** It gave the first
heading of a stand-alone screen `Modifier.focusRequester().focusable()` and
requested Compose input focus in a `LaunchedEffect`. That works -- the delegate
sends `TYPE_VIEW_FOCUSED` when `SemanticsProperties.Focused` turns true, which
is the event every reader follows -- and **that is exactly the problem**: a
screen reader has already placed its initial focus on the first element of a new
window, which is that same heading. So it was read once by the reader's own
initial focus and again by ours, a frame later.

It was added on 2026-09-03 for a symptom that turned out to be something else
entirely: the heading was drawn UNDERNEATH the status bar, because the app went
edge-to-edge at targetSdk 35 and consumed no insets. That is fixed in
`EasyVoiceTheme` with `windowInsetsPadding(WindowInsets.safeDrawing)`, so there
is nothing left for a focus request to rescue. Removing it also takes the
heading back out of keyboard and Switch Access focus order, where it never
belonged. **Do not put it back without a report that the heading is genuinely
unreachable again, and check the insets first.**

**2. A SECOND window title was being set during startup.**
`ModeSettingsActivity` called `setTitle(spec.second)` in `onCreate` and
`VoiceSetupActivity` called `applyTitle()` there too. The window already has a
title from the manifest, and a screen reader speaks a window's title when the
window appears -- so changing it during startup fired another
window-state-changed event and the screen introduced itself twice, with two
different names. Both entry calls are gone. Nothing is lost: the specific name
is the screen's first heading, "<Mode> settings" and "<language> voices", which
is where the reader lands.

**`VoiceSetupActivity.applyTitle()` STAYS for Previous/Next**, and only there.
On that path the window is not changing, so nothing else would say which
language you have moved to and this is the whole announcement.

**One thing is left and it is the owner's wording call, not a defect I can
settle alone:** on the Languages and About screens the manifest label and the
first heading are the same word ("Languages", "About"), so the window
announcement and the heading still say it twice. Making them differ means
changing either a visible heading or the name the screen shows in the recents
list, and both are wording the owner has chosen before.

## THE POPUP-TITLE OVERRIDE WAS SHIPPED AND REVERTED THE SAME DAY (2026-09-04)
**Do not write it again.** Every Compose `Popup` titles its own Android window
from `androidx.compose.ui`'s `R.string.default_popup_window_title`
(`AndroidPopup.android.kt`, `createLayoutParams`: *"accessibilityTitle is not
exposed as a public API therefore we set popup window title which is used as a
fallback by a11y services"*), and the library's value is the words **"Pop-Up
Window"**. A screen reader speaks a window's title when the window appears, so
opening a dropdown said our "Expanded" and then "Pop-Up Window".

The owner asked for that second announcement to go, and I answered it by
overriding the resource in `app/src/main/res/values/strings.xml` with an **empty**
string. AAPT2 does merge the application's value over the library's, so the
override worked exactly as designed -- **and that is the bug.** An empty title
does not remove the announcement, it removes the NAME, and the reader then falls
back to describing the window itself. The owner heard it immediately:

*"sab panel, uske bad kuchh activity, uske bad kuchh package name ka kuchh aisa
kuchh bolna shuru ho jata hai."*

"Sub panel" is the window type (`TYPE_APPLICATION_SUB_PANEL`) followed by the
activity and the package -- the platform's own debug name for an untitled window.
**A window with no title is worse than a window with a dull one**, which is
precisely why androidx put a string there in the first place.

Reverted. The library's own wording ships, and the owner accepted it in the same
message: *"compose mein to already hota hai na ... bhale vah to bolta hi rahata
hai, to aapko yah extra fix karne ki jarurat hi nahin hai."* `strings.xml` now
carries only `app_name` plus a comment saying not to try this again.

**The lesson, and it is the same one as the `onStop` release and "button
button".** A change that is correct about the mechanism can still be wrong about
the outcome, and the way to tell is to ask what the system does with the state you
are creating -- here, "what does a reader say about a window with no name?" --
rather than only whether the mechanism does what the docs promise. Three of my
regressions this session have that identical shape.

**The standing rule the owner stated with it:** *"Jo library mein available hai
bas vahi fix karni hai."* Do not override a library resource, and do not add a
semantics property the library already supplies, to remove something the library
deliberately says.

## The Configuration language rows are LIST ITEMS now, not buttons (owner, 2026-09-04)
*"language list items ... announced specifically as 'list items', not buttons."*

They were buttons because **I made them buttons the day before**. The row carried
`accessibilityClassName = "android.widget.Button"`, added so that a reader other
than TalkBack would say *something* about the control; it did, and the something
was wrong. A row in a list of languages is a list item, and the only reason it
was a button is that a class name was the tool I had in my hand.

**What names a list row is its place in a collection, not a widget class.** The
`LazyColumn` already publishes its own half -- `LazyLayoutSemanticState` sets
`CollectionInfo(rowCount = totalItemsCount, columnCount = 1)` -- and what was
missing is the per-row half, because **Compose sets `collectionItemInfo` on no
lazy item by itself**. The row now declares
`collectionItemInfo = CollectionItemInfo(index, 1, 0, 1)`, so the platform
reports a list and the row's index in it, and that lands on the **focused** node
rather than depending on a service walking Compose's fake children. The
Languages screen's checkbox rows (`LanguageCheckRow`) were already written this
way, so both language lists in the app now describe themselves identically.

`Modifier.clickable(onClickLabel = ...)` still deliberately leaves `role` null. A
Role here would make Compose emit a fake role child and the row would be called a
button again by the back door.

**`EvRoleClass` is GONE, and with it the last `accessibilityClassName` in the
app.** That row was its only use. `ComposeTheme.kt` keeps the findings as a
comment; the section below is the record and is still accurate except that
nothing sets a class name any more.

**What is still not solved, stated plainly rather than papered over.** A control
that Material gives a Role AND that has a text child -- every `Button`,
`OutlinedButton`, `Tab`, `FilterChip`, `DropdownMenuItem` and our
`toggleable`/`selectable` rows -- still announces its role only through Compose's
fake child node, which TalkBack walks and a reader that inspects only the focused
node does not. Three things were checked again and none of them opens:
- **an `IconButton` already works everywhere**, and the reason is worth keeping:
  `Icon(contentDescription = null)` adds no semantics modifier at all, so the
  button has **no semantics children**, `replacedChildren` is empty, the
  delegate's gate passes and `info.className = "android.widget.Button"` lands on
  the real node. One source, every reader.
- **a text label always creates a semantics node**, `clearAndSetSemantics {}`
  included, so a labelled button can never pass that gate. Proven on the owner's
  own device rather than argued: adding a class name beside the fake node is what
  produced "button button".
- **`Role.Tab` sets `roleDescription` and no class name at all**, so a tab has
  nothing to reach a non-TalkBack reader with, and there is no `roleDescription`
  semantics API to set by hand (`SemanticsPropertiesAndroid` has exactly three
  members in the pinned api file).

**The one shape that would fix it is written down and NOT shipped.** A `Box`
around the control carrying `clearAndSetSemantics { contentDescription; role;
onClick { ... } }` empties `replacedChildren`, so the role lands on the focused
node once -- and the config being reset is the Box's own, so the objection
recorded below (a clearing modifier passed to a component wipes the component's
semantics) does not apply. It also re-declares the click action for **every
button in the app**, cannot be tested in this container, and if it is wrong a
blind user cannot press anything. That is the owner's call, not a change to slip
in -- and it is the third time this session that an untested change on a path the
owner uses every second has been the thing that broke.

## The ROLE and the non-TalkBack reader: what works, what cannot (2026-09-03)
Owner: *"TalkBack mein to button aur tab bol raha hai, but aur screen reader mein
vah button aur tab bol hi nahin raha ... sirf bolta hai jo likha hai."* The NAME
arrives, the ROLE does not. **The first fix for this was wrong and the owner
caught it the same day** -- *"button button do baar TalkBack announce kar raha
hai, drop down bhi do baar"*. This section is the corrected record.

### Why the role does not reach the focused node
The delegate applies the role only under

    if (semanticsNode.isFake || semanticsNode.replacedChildren.isEmpty()) {
        if (role == Role.Tab)         info.roleDescription = "Tab"
        else if (role == Role.Switch) info.roleDescription = "Switch"
        else info.className = role.toLegacyClassName()
    }

Every interactive control here is a merging node WITH children and the delegate
walks the UNMERGED tree, so `replacedChildren` is never empty. The role goes to a
**fake child node** (`SemanticsNode.emitFakeNodes`), and that fake node is handed
to the service as **its own virtual node** -- `SemanticsOwner`'s
`addFakeNode(...)` writes it straight into the map the delegate serves. TalkBack
walks those children; a reader that inspects only the focused node does not.

### The fix that was WRONG: className on a node that already has a Role
`accessibilityClassName` is androidx's one ungated hook and it DOES reach the
focused node -- confirmed on device, the owner's second reader started announcing
buttons and the dropdown. **But on a node that also has a Role it puts the role in
TWO places**, the real node and the fake child, and TalkBack read both. That is
"button button", and the dropdown twice. My regression, from the commit before.

### THE RULE
- **A node WITH a Role already announces it, through the fake child. Do NOT add
  `accessibilityClassName` there -- it will double.**
- **A node WITHOUT a Role emits no fake child**, so `accessibilityClassName` is
  the single source: safe, and the only way the role reaches the second reader.

Today that is **exactly one control**: the Configuration list row, whose
`Modifier.clickable(onClickLabel = ...)` leaves `role` null. `EvRoleClass` has one
entry and one use for that reason. Everything else -- Button, IconButton, Tab,
FilterChip, FAB, DropdownMenuItem, and our own `toggleable`/`selectable` rows --
carries a Role and must not get a class name.

### What CANNOT be done today, checked rather than assumed
- **There is no `roleDescription` semantics API.** In the version the BOM pins,
  `SemanticsPropertiesAndroid` has exactly three members -- `AccessibilityClassName`,
  `CredentialRequest` (`@RequiresApi(34)`) and `TestTagsAsResourceId`
  (`compose/ui/ui/api/1.12.0-beta01.txt`). So a **TAB**, which Compose announces
  with `roleDescription = "Tab"` and no class name at all, has **no supported way
  to reach the focused node**. `android.app.ActionBar$Tab` was tried and the
  owner's second reader does not recognise it -- it is not one of the classes a
  generic reader maps. **The tab role on a non-TalkBack reader is an open gap in
  Compose, not something this app can close.**
- **The fake child cannot be suppressed.** `emitFakeNodes` fires whenever the node
  has a Role, merges descendants and has children, and Compose has no API to unset
  a Role a Material component already set. Overwriting it with an inert Role would
  work mechanically and is a hack -- it also breaks `isSelected` for Tab and the
  localised "on"/"off" the delegate derives from `Role.Switch`.
- **`clearAndSetSemantics` is not the way in.** It really does make
  `replacedChildren` empty and open the gate, but
  `LayoutNode.calculateSemanticsConfiguration` walks `tailToHead` and a clearing
  node does `config = SemanticsConfiguration()` -- a RESET. Our modifier is at the
  head and applied last, so it would wipe the component's own `onClick`, `role`
  and disabled state. That is the bug that once made the Configuration rows
  unopenable.
- **The Slider needed nothing** and is the proof the model is right:
  `info.className = "android.widget.SeekBar"` is set from `ProgressBarRangeInfo` +
  `SetProgress` with **no gate**, so it already reaches every reader.

**If this is ever to be solved properly, the shape is: build the control from
`Surface(onClick = ...)`, which sets NO Role (checked -- zero `Role.` in
Material3's `Surface.kt`), and give it `accessibilityClassName` as the single
source.** That is a real option and it rewrites every button in the app, so it is
the owner's call, not a change to make quietly.

## The dropdown is Material's own component now (owner request, 2026-09-03)
*"aapne dropdown list wala apne jaanbujhkar nahin liya hai, usko le lijiye ...
kyunki double-double baar TalkBack expand-collapse do baar announce kar raha hai
... properly library ke through hi karvaiye ... apne haath se kuchh bhi nahin."*

**The double announcement was mine, from the previous commit.** The
`expand {}` / `collapse {}` actions added that morning sat on top of the existing
`stateDescription = "Expanded"/"Collapsed"`, so TalkBack read the state and then
offered the action for it. Both are gone with the migration.

`LabeledDropdown` is `ExposedDropdownMenuBox` + `Modifier.menuAnchor(
ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled)` +
`ExposedDropdownMenu`, under `@OptIn(ExperimentalMaterial3Api::class)`.

**THAT OptIn IS REQUIRED, AND LEAVING IT OUT BROKE BUILD 827.** The first version
of this section claimed "neither is `@ExperimentalMaterial3Api` any more", because
androidx-main really has dropped the annotation from all three. **That was the
wrong file to read**, and it is the trap this document already records for
`TabRow`: the BOM pins **material3 1.4.0**, and `api/1.4.0-beta01.txt` says

    @ExperimentalMaterial3Api public abstract sealed class ExposedDropdownMenuBoxScope
    @ExperimentalMaterial3Api @Composable public static void ExposedDropdownMenuBox(...)

so `ExposedDropdownMenuBox`, and `menuAnchor` and `ExposedDropdownMenu` through
that scope, all still carry it. `ExperimentalMaterial3Api` is
`RequiresOptIn.Level.ERROR`, so the compile fails outright rather than warning --
three `e:` lines and `compileReleaseKotlin FAILED`, in both the build and the
accessibility job. (`ExposedDropdownMenuAnchorType` is NOT annotated; it is a
plain value class.) **ALWAYS check the api/*.txt of the version the BOM pins,
never androidx-main.**

What the library owns, instead of this file: `role = Role.DropdownList` and the
accessibility click action, opening on touch in the Initial pointer pass,
Enter/space/arrow keys, `BackHandler(enabled = expanded)`, focus,
`exposedDropdownSize`, and a `scrollState` on the menu.

**`onClick = { }` on the anchor is deliberate.** `Modifier.expandable` consumes
the gesture in the Initial pass and toggles the menu itself, so a real `onClick`
would toggle a second time and the menu would open and shut in one tap.

**Two semantics stay, and they are the two the library does NOT set for this
anchor type** -- checked in `ExposedDropdownMenu.kt`, where `Modifier.expandable`
applies `stateDescription` and `contentDescription` only on the
`SecondaryEditable` branch: `contentDescription`, because this node merges its
children and so carries no name of its own, and `stateDescription`, so
expanded/collapsed is still spoken. Dropping those would be a regression, not
purity.

## The two headings on Mode settings are gone (owner request, 2026-09-03)
*"Preferred languages / Numbers, punctuation and emojis -- yah dono heading ko
hata do."* Both were added on 2026-08-27 at the owner's request and both are now
removed at the owner's request; the earlier entry in this file argues for them
and is superseded. The three reading groups keep their own headings
(`LabeledRadioGroup`'s `title`), which is what made the filled bar above them a
second heading level for the same content, and the two preferred-language
dropdowns are already named in full so the bar only repeated them.

## The androidx / Compose library sweep (owner request, 2026-09-03)
*"Jetpack compose aur Android X ki library jahan use ho sakti hai vahan per usko
use karo ... jahan se aapne jo chijen hath se likhi hui hai aur yah library se ho
sakti hai to usko research karke properly fully library integration karo ...
Android accessibility ke liye bhi properly Androidx library chhan maro."*

Every androidx API below was read from androidx's own source or `api/current.txt`
before it was used, and the two things that were NOT adopted were measured
against the same sources. Both halves are recorded, because "we looked and the
library adds nothing here" is an answer that has to survive being asked again.

### What moved to the library

| was hand-written | now | why it is better, not just shorter |
|---|---|---|
| `Bitmap.createBitmap` + `Canvas` + `setBounds` + `draw`, for the app-bar icon | **`Drawable.toBitmap()`** (core-ktx) | it RESTORES the drawable's bounds afterwards, and returns a `BitmapDrawable`'s own bitmap when the size already matches -- which is the API 24-25 launcher icon, where ours allocated and redrew for nothing |
| two hand-built `ACTION_SEND` intents + `createChooser` (Export, Share logs) | **`ShareCompat.IntentBuilder`** | it is what builds the `ClipData` the URI grant is really derived from (`migrateExtraStreamToClipData`: `setClipData`, then `addFlags(FLAG_GRANT_READ_URI_PERMISSION)`) |
| `getSystemService(POWER_SERVICE) as PowerManager` | **`ContextCompat.getSystemService(ctx, PowerManager::class.java)`** | typed, so a device that answers null is a null rather than a `ClassCastException` |
| `checkSelfPermission(POST_NOTIFICATIONS)` | **`ContextCompat.checkSelfPermission`** | the library form; the `SDK_INT >= 33` guard beside it stays, doing a different job |
| `if (SDK_INT >= 28) longVersionCode else versionCode` on the About screen | **`PackageInfoCompat.getLongVersionCode`** | identical body, with the API-28 call isolated in a nested class |
| `Uri.parse(...)` x3 | **`String.toUri()`** (core-ktx) | |
| `context.getString(R.string.app_name)` inside a composable | **`stringResource(...)`** | the composable-correct read |
| `animationsEnabled(context)` reading `Settings.Global.ANIMATOR_DURATION_SCALE` | **`MotionDurationScale` from the composition's coroutine context** | see below -- this one is a behaviour improvement |
| `SWIPE_THRESHOLD_PX = 150f` | **`48.dp` + `LocalDensity`** | see below -- this one was a real bug |
| the Languages search field had no leading icon | **Material `search` glyph**, fetched verbatim from `google/material-design-icons` | the one place in the app where an icon is load-bearing rather than decoration |
| the dropdown button advertised no expandable action | **`expand {}` / `collapse {}`** semantics | |

**The animation-scale read is the one that actually changes behaviour.**
`WindowRecomposer.android.kt` already reads exactly the setting we were reading
by hand -- and unlike a one-shot read it keeps WATCHING it:
`Settings.Global.getUriFor(ANIMATOR_DURATION_SCALE)` plus a `ContentObserver`,
collected into `MotionDurationScaleImpl._scaleFactor`, which is a
`mutableFloatStateOf`. That `MotionDurationScale` goes into the Recomposer's own
context (`Recomposer(contextWithClockAndMotionScale)`), which is what
`rememberCoroutineScope()` hands back, so reading it in composition SUBSCRIBES:
turn "Remove animations" on while the scan screen is open and the spinner goes at
once. The try/catch around it is the documented contract, not caution -- the impl
throws `error("MotionDurationScale scale factor requested before recomposer loop
start")` if asked too early.
**Hiding the spinner is still deliberately more than the library does**, and that
was checked rather than assumed: `InfiniteTransition` handles a scale of 0 by
suspending (`if (durationScale == 0f)`) and waiting for it to come back, so
`CircularProgressIndicator` would FREEZE rather than spin. A frozen ring says
nothing to anyone, and the headline plus the polite live region carry the whole
message. Do not "restore" the spinner on the grounds that Compose handles it.

**The swipe threshold was a genuine device-dependent bug.** `Modifier.draggable`
reports RAW PIXELS, and `150f` is a different physical distance on every phone:
about 50dp on xhdpi, **100dp on hdpi**, 37dp on xxhdpi. The same flick changed the
tab on one device and not on another, and the cheapest screens needed the longest
swipe. It is `48.dp` now -- Material's own minimum touch target, comfortably past
the ~8dp slop `draggable` has already absorbed -- converted with `LocalDensity` at
the point of use. **Never write a raw-pixel distance constant again; state it in
dp and convert.**

### What was researched and deliberately NOT adopted

**1. `androidx.compose.material:material-icons-core` / `-extended`. Keep the
vector drawables.** The 22 icons in `res/drawable/` are Google's own paths, copied
verbatim from `google/material-design-icons`, and eleven of them have an
`Icons.Filled` counterpart. Adopting them would still be a step backwards:
material3 does **not** depend on the icons artifact in production -- its
`build.gradle` names `androidx.compose.material:material-icons-core:1.7.5` only
under `androidDeviceTest`, while every real dependency is 1.12.0 or
`project(":...")` -- so it is a NEW dependency, not one already on the classpath,
and it is generated Kotlin `ImageVector` builders rather than XML. The one thing
it would give free, RTL mirroring on the two arrows, our XML already declares with
`android:autoMirrored="true"`. Nothing is gained and a dependency is added.
(`Icons.kt` and the module's `api/current.txt` are no longer at any path under
`compose/material/` in `androidx-main`, so the icon list could not be read at the
source; the material3 build file above is what the decision rests on.)

**2. `ExposedDropdownMenuBox` + `Modifier.menuAnchor`. Keep `LabeledDropdown`.**
This looks like the library component for our dropdown and it is not, and the
source says so plainly. For a `PrimaryNotEditable` anchor, `Modifier.expandable`
sets only `role = Role.DropdownList` and an `onClick`; the `stateDescription =
expandedDescription / collapsedDescription` pair is applied **only** to
`SecondaryEditable`. So migrating would DELETE the expanded/collapsed state our
button announces today. Its `pointerInput` in the Initial pass also fights a
`Button`'s own `clickable`, and the box is built around a text field we do not
have. What was taken from that source instead is the pair of semantics actions
below.

**3. `Modifier.semantics { expand/collapse }` -- taken, though the library
component does not use it.** The API is androidx's, and the delegate turns it into
`AccessibilityNodeInfoCompat.ACTION_EXPAND` / `ACTION_COLLAPSE`
(`AndroidComposeViewAccessibilityDelegateCompat` lines 1117-1125 and 1772-1777).
A tap already opens the menu, so this changes nothing for a TalkBack double-tap;
what it adds is a NAMED action, so Voice Access can be told "expand" and Switch
Access and TalkBack's Actions menu list it. Only the action that can run is
offered, and neither is offered while the control is disabled -- Material3's own
`menuAnchor(enabled = false)` skips its whole expandable modifier for the same
reason.

**4. The service's own `versionCode()` keeps its inline SDK branch.** `About` is a
screen with no AutoTTS counterpart, so the library form is free there; the
service's `onCreate` version log is AutoTTS-mirrored and rule 5 governs it. Do not
"finish the job" by changing the service one too.

**5. Storage, detection, the logger and `IsoCodes` were not touched at all.**
`SharedPrefsManager`'s `commit()`, `LangStore`, `EasyVoiceLogger` and the ISO
tables are proven-equal AutoTTS ports; DataStore and `LocaleListCompat` are the
androidx answers and both would break a proof. Out of scope by rule 5, not by
oversight.

**New local-check noise, all of it the blocked-Google-Maven cascade.**
`kotlin-typecheck.sh` now also reports `unresolved reference` for `toUri`,
`ContextCompat`, `ShareCompat`, `PackageInfoCompat`, `MotionDurationScale`,
`rememberCoroutineScope`, `scaleFactor`, `LocalDensity`, `stringResource`,
`expand` and `collapse` -- every one an androidx symbol behind `dl.google.com`.
One is a cascade rather than a symbol: `swipeThresholdPx` is derived from
`LocalDensity`, so it has an error type and kotlinc reports
`'operator' modifier is required on 'fun String.compareTo(...)'` for the two
comparisons that use it. **Do not chase these.** The counter that matters,
"our-own-name unresolved refs", stayed at 13.

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
**SUPERSEDED 2026-09-09 -- the owner set network access to Full and it is not
blocked any more.** Kept as the record of how it was diagnosed and of what the
fix was, because the same reasoning applies to any host the policy refuses. The
measurements below describe the *Trusted* level and are still accurate for it.

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
