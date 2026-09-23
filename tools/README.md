# tools

Everything here exists for one reason: **this app has to match AutoTTS exactly,
and reading the two side by side is not reliable enough.** Every bug found so
far was either caught by one of these, or was missed for weeks because the
equivalent check did not exist yet.

Until 2026-08-26 all of this lived in a scratch directory that was wiped between
sessions, so every investigation rebuilt the same harnesses from scratch before
it could start. That is why it is here now.

## First run

    tools/bootstrap.sh          # android.jar, kotlinc, the Compose plugin
                                #   and the androidx classpath

Every download retries five times with a widening wait. Maven Central answers
**429** under load -- it did on the first run of the Kotlin 2.4.20 bump, and a
one-shot `curl` turned that into a missing Compose plugin. A **403** is not
retried: that is the egress policy answering, and the rule is to report the
blocked host rather than hammer it.

Everything else needs only `python3`, `javac` and `g++`, which are expected to
be on `PATH`.

## Before every push

    tools/check-all.sh          # ~1 minute

Runs, cheapest first:

| check | what it catches |
|---|---|
| `check/ktcheck.py` | unbalanced braces, quotes and parens per Kotlin file |
| `check/ktresolve.py` | a call to one of **our own** functions whose arity or parameter names do not match the declaration |
| `check/ktimports.py` | a capitalised name used but never imported, and the reverse — an import of something that is a scope member and cannot be imported |
| `check/xmlcheck.py` | an `@color/`, `@drawable/`, `@string/` or `?attr/` reference with nothing behind it |
| `check/invariants.sh` | the rules in `docs/INVARIANTS.md` that a grep can decide |
| `check/cpp-syntax.sh` | `g++ -fsyntax-only` over the native source |
| `check/minsdk-api.sh` | a call above `minSdk` that is not behind a reviewed `SDK_INT` guard |
| `check/workflow-shell.sh` | a `build.yml` shell fragment that the shell running it cannot run |
| `check/gradle-compile.sh` | **the real build**: app + androidTest compiled by Gradle, AGP, Kotlin and the Compose plugin CI uses; any error or compiler warning fails it |

`check/invariants.sh` is the one to notice. Each rule in `docs/INVARIANTS.md` is
there because breaking it caused a bug that took days to find, and most of them
are decidable by grep — so "did I break one" is a question with an answer rather
than a memory test. **`check/selftest.sh` proves that checker actually fires**,
by copying the repo, breaking each rule on purpose and asserting the check
reports it. Run it after touching `invariants.sh`; writing that script produced
two silent-failure bugs in a row, both recorded in its header.

### The type-check IS the real build now (2026-09-23)

`check/gradle-compile.sh` runs `:app:compileDebugKotlin` and
`:app:compileDebugAndroidTestKotlin` with the Gradle version `build.yml` names,
against the Android SDK `bootstrap.sh` installs into `tools/.cache/android-sdk`.
Zero errors and zero compiler warnings from our sources is the bar; it retries
Maven Central's 429s and nothing else. It was negative-tested both ways: a type
error and a new warning each fail it.

It replaced `check/kotlin-typecheck.sh` and `tools/fetch-deps.py`, a bare kotlinc
run on a classpath from a resolver of our own (owner: *"dependency humne khud
likhi hai ... usko hatakar jo already hai vah kar do"*). That resolver walked
every version it met rather than the ones Gradle selects, so it put
`transition`, `dynamicanimation` and `legacy-support` on the classpath although
the app has none of them, and nothing compiled the instrumented tests until CI
did. Compiling androidTest here also resolves the androidTest classpath, which is
where AGP pins every test library to the app's versions -- the conflict behind
builds 849-857 is now caught before a push.

`check/minsdk-api.sh` still compiles with kotlinc, because it has to compile
against **API 24's** `android.jar`. Its classpath is written by Gradle
(`check/classpath.init.gradle.kts`, `:app:evClasspath`), so it is the app's real
`debugCompileClasspath`, not an imitation.

## Proving behaviour, not just compilation

    tools/verify/segmenter/run.sh        # ~1 min,  163,296 cases
    tools/verify/scriptfamily/run.sh     # ~3 min,  15 language sets x 1,114,112 code points
    tools/verify/normalizer/run.sh       # ~20 s,   1,114,112 code points, 1,062 mappings
    tools/verify/langcodes/run.sh        # ~5 s,    283 CLD2 language codes

The first three build **AutoTTS's own code** from `autotts_reference/` alongside
ours, run both over the same inputs, and diff. A failure prints the exact case.

CLD3 and its two harnesses (`cld3span`, `isocodes`) were removed on
2026-09-02 at the owner's instruction; see CLAUDE.md, "CLD3 IS GONE".

These are the pieces of the app small enough to isolate and important enough to
be worth proving:

- **the segmenter** — `c3.d0.t` versus `buildMixChunks`. It decides how an
  utterance is cut up and what type each piece gets, and the type decides the
  voice. One difference found here: `d0.k` stops at a whitespace segment and
  ours walked past it.
- **the script family** — `a.e(cp, enabled)` versus `familyLangForCpFiltered`.
  It is what the auto-mode detector falls back to whenever its answer is not an
  enabled language. Its Java side has a trap in it worth reading before you
  touch any Java harness: `verify/scriptfamily/java/README.md`.
- **the Unicode normaliser** — `clsCLD2.a` versus `normalizeFancyCodepoint`, a
  300-line hand transcription. CLAUDE.md says outright: if it is ever touched,
  redo this sweep rather than hand-checking it. This is that sweep.
- **the language codes** — `c3.n.n`'s normalisation versus the native
  `toIso3()`, over **every** code CLD2's full build can return. It is the
  youngest of the four and it exists because of a specific worry: the 2026-09-08
  swap to CLD2's full tables handed the app a hundred languages it had never
  seen, and "does anything downstream choke on them?" deserved a measurement
  rather than a read. `n.n` does NOT normalise a three-letter code, our native
  `toIso3` never returns null, and the sweep asserts those two rules still agree
  code by code. It also pins the three deprecated ISO 639-1 pairs `c3.e` states
  by hand and fails if a fourth (`jw`) comes back -- see CLAUDE.md, 2026-09-09.
  It reads the ISO tables out of `IsoCodes.kt` at run time and fingerprints the
  C++ `toIso3()`, so neither side can drift away from it silently.

## Measuring, not proving

    tools/verify/latency/run.sh          # ~1 min first run, then seconds

The odd one out: it asserts nothing. It links the real `tts_engine_core.cpp`
against CLD2, starts a JVM for a genuine `JNIEnv`, and
**times** the work that has to finish before the first word is spoken -- the
segmenter and then one `nativeGetLanguages` per chunk -- for a 30- and a
60-paragraph text.

It times the one detector there is. For 30 paragraphs: segmenting 0.3 ms,
CLD2 1.6 ms. CLD3 used to add a second column at 6.6 ms and is gone.

It exists because the owner reported a long wait before a long text starts
reading, and the honest answer to "is our own pipeline the delay?" is a number,
not an argument. Run it before blaming chunking or detection for anything the
owner reports as slow. The callgrind profile behind these numbers is in
CLAUDE.md; it was taken while CLD3 was still here, and CLD3's hidden layer was
40.67% of every instruction the program executed, which is most of what removing
it bought back.

`verify/segmenter/cpp/explore.cpp` is the same segmenter with a readable
main: 25 named cases printing type, kind, language and text. Use it to look at
one input rather than to prove anything.

Both `run.sh` scripts rebuild the C++ side **from the current tree**, so they
test what you have just edited. See each one's header and its `java/README.md`
for what was changed to make CFR's output compile, and why.

## Comparing AutoTTS releases

    tools/autotts/cmp_versions.py <old-decompile> <new-decompile>

Structural comparison with identifiers, literals, block labels and casts
normalised away, so only real structure and real string literals survive. This
is what showed that 5.7.7.18 → 5.7.7.26 changed only five files behaviourally
and that everything else was CFR noise.

## What is deliberately not here

- **A build.** The NDK, CMake, R8 and signing are CI's, and the owner triggers
  that manually.
- **`blocks.py` and `gentree.py`.** They existed to edit and materialise
  `ci/generate.py`, which embedded every source file as a Python string literal.
  The sources are real files now, so both are gone; edit the file directly.

## `check/native-pagesize.sh` -- 16 KB page size, against a built APK

    tools/check/native-pagesize.sh EasyVoice-<n>-arm64-v8a.apk

Reads every PT_LOAD `p_align` out of each `lib/*/*.so` and requires 16 KB on the
**64-bit** ABIs. Android 16 runs a 4 KB-aligned 64-bit library in a compatibility
mode and shows the user a dialog; on a blind owner's phone that is an
unexplained interruption.

It is deliberately NOT part of `check-all.sh`: it needs a built APK, and there is
no NDK in the dev container. Run it when an APK is at hand -- the release assets
are downloadable now the repository is public.

32-bit ABIs are reported `n/a` and never fail: 16 KB pages exist only on 64-bit
devices, so `armeabi-v7a` being 4 KB aligned is correct rather than a defect.
