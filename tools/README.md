# tools

Everything here exists for one reason: **this app has to match AutoTTS exactly,
and reading the two side by side is not reliable enough.** Every bug found so
far was either caught by one of these, or was missed for weeks because the
equivalent check did not exist yet.

Until 2026-08-26 all of this lived in a scratch directory that was wiped between
sessions, so every investigation rebuilt the same harnesses from scratch before
it could start. That is why it is here now.

## First run

    tools/bootstrap.sh          # downloads android.jar and kotlinc into tools/.cache

Everything else needs only `python3`, `javac` and `g++`, which are expected to
be on `PATH`.

## Before every push

    tools/check-all.sh          # ~2 minutes

Runs, cheapest first:

| check | what it catches |
|---|---|
| `check/ktcheck.py` | unbalanced braces, quotes and parens per Kotlin file |
| `check/ktresolve.py` | a call to one of **our own** functions whose arity or parameter names do not match the declaration |
| `check/ktimports.py` | a capitalised name used but never imported, and the reverse — an import of something that is a scope member and cannot be imported |
| `check/xmlcheck.py` | an `@color/`, `@drawable/`, `@string/` or `?attr/` reference with nothing behind it |
| `check/invariants.sh` | the rules in `docs/INVARIANTS.md` that a grep can decide, plus CLD2/CLD3 parity |
| `check/cpp-syntax.sh` | `g++ -fsyntax-only` over the native source |
| `check/kotlin-typecheck.sh` | **NEW** Kotlin type errors against a baseline commit |

`check/invariants.sh` is the one to notice. Each rule in `docs/INVARIANTS.md` is
there because breaking it caused a bug that took days to find, and most of them
are decidable by grep — so "did I break one" is a question with an answer rather
than a memory test. **`check/selftest.sh` proves that checker actually fires**,
by copying the repo, breaking each rule on purpose and asserting the check
reports it. Run it after touching `invariants.sh`; writing that script produced
two silent-failure bugs in a row, both recorded in its header.

### Why the Kotlin type-check works by diff

It compiles with an **API 15** `android.jar` from Maven Central, because Google
Maven is unreachable from some environments. androidx, Material and Compose are
therefore all unresolvable and every run reports over a thousand errors. The
count is meaningless; a *new error text* is not.

Running it **without** a real `android.jar` would be worse than useless: kotlinc
then treats every `android.*` type as unresolved and checks **nothing** at those
call sites. That is exactly how `buildModesTabView(ctx, prefs) { testTts }` once
bound its trailing lambda to a newly added last parameter instead of
`testTtsProvider`, and reached CI.

## Proving behaviour, not just compilation

    tools/verify/segmenter/run.sh        # ~1 min,  163,296 cases
    tools/verify/scriptfamily/run.sh     # ~3 min,  15 language sets x 1,114,112 code points
    tools/verify/normalizer/run.sh       # ~20 s,   1,114,112 code points, 1,062 mappings
    tools/verify/isocodes/run.sh         # ~5 s,    all 109 CLD3 tags

Each builds **AutoTTS's own code** from `autotts_reference/` alongside ours,
runs both over the same inputs, and diffs. A failure prints the exact case.

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
- **the ISO tags** — every language CLD3 can name has to survive
  `IsoCodes.toIso3`. One did not, and that was a real bug.

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
