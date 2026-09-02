# Easy Voice

An Android **text-to-speech engine** for a blind user who navigates entirely by
screen reader. It does not synthesise audio: it decides, for each piece of text
the system hands it, **which language that is** and **which installed engine and
voice should speak it**, then forwards the text to that engine.

It is a re-implementation of **AutoTTS 5.7.7.26** (`com.vnspeak.autotts`), and
its behaviour must match AutoTTS exactly — everywhere except the user interface,
which is deliberately different.

## Where things are

| path | what |
|---|---|
| `app/src/main/java/com/tts/easyvoice/` | the service, the settings screens, the storage |
| `app/src/main/cpp/tts_engine_core.cpp` | the segmenter, the script classifier and the CLD2 wrapper |
| `docs/` | how it works, what it maps to, and the rules that must hold |
| `tools/` | the checks and the two proof harnesses |
| `autotts_reference/` | the decompiled AutoTTS releases this is checked against |
| `.github/workflows/build.yml` | the only build |

## Read these first

1. **`docs/ARCHITECTURE.md`** — one utterance, from the system to the speaker,
   stage by stage. Start here to find the code that owns a symptom.
2. **`docs/AUTOTTS_MAP.md`** — every AutoTTS class and method and its
   counterpart here, with what has been verified and how. The obfuscated names
   shift between AutoTTS releases and are not guessable, so do not carry one
   over from an old note.
3. **`docs/INVARIANTS.md`** — the rules that must hold across the whole app,
   each one there because breaking it produced a bug that was hard to find.
4. **`CLAUDE.md`** — the working rules, and the written-up record of every
   investigation. Long, and worth it.

## Building

CI does it, and only on a manual dispatch:

    Actions → "Build Easy Voice Native" → Run workflow

The APK is published to a GitHub release named after the run number. There is no
local build — the NDK, CMake and R8 are CI's job, and CLD2
are cloned at build time rather than checked in.

## Changing something

    tools/bootstrap.sh          # once, downloads android.jar and kotlinc
    …edit…
    tools/check-all.sh          # ~2 min: structure, signatures, imports, XML, C++, Kotlin types
    tools/verify/segmenter/run.sh
    tools/verify/scriptfamily/run.sh

The two `verify` scripts build **AutoTTS's own code** next to ours and diff the
answers over hundreds of thousands of cases. Run them whenever you touch the
segmenter or the script tables; they are the difference between believing the
two agree and knowing it.

## The one rule that outranks the rest

**Match AutoTTS exactly.** Not "behaviourally equivalent", not "cleaner", not
"surely this is a bug in theirs" — exactly. Several oddities in this codebase
are copied on purpose and are listed in `docs/INVARIANTS.md`; removing one is a
behaviour change.

The two deliberate departures are the **user interface**, which the owner asked
to be laid out for a screen reader instead, and the **`_disabled` default**,
which starts languages cleared rather than ticked.
