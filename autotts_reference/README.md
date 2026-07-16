# AutoTTS Reference (com.vnspeak.autotts 5.7.7.1)

**Purpose:** Decompiled, human-readable reference of the *AutoTTS* Android app.
EasyVoice (this repo) must match AutoTTS's behavior byte-for-byte, so this is the
ground-truth source we compare against. **These files are committed on purpose —
do NOT delete them.** The build container is ephemeral (the scratchpad is wiped on
every reset), so git is the only durable home for this reference. It was lost once
to a container reset; keeping it here prevents that from happening again.

## Contents

| Path | What it is |
|------|------------|
| `AutoTTS_5.7.7.1.apk` | Original APK — the ground truth. Everything below regenerates from this. |
| `decompiled_java/` | Full CFR decompile of the whole APK (all 3254 classes → 1648 `.java`, 216 packages). |
| `decompiled_res/` | Decoded resources: `res/values/strings.xml`, `res/xml/tts_engine.xml`, all layouts (467 XML). |

### Key app source files (inside `decompiled_java/`)
- `com/vnspeak/autotts/AutoTtsService.java` — main TTS synthesis service (mode dispatch, CLD2, speak)
- `com/vnspeak/autotts/clsCLD2.java` — CLD2 language detection (`b()` detect, `c()` unknown-char, `nativeGetLanguage`)
- `com/vnspeak/autotts/a.java` — script/lang mapping (`a.b(int)` codepoint→script, `a.w` fallback map)
- `com/vnspeak/autotts/NewSettingsActivity.java` — settings UI (tabs, spinners, sliders)
- `c3/k.java` — settings store, engine check (`k.o(lang)`, `k.f` enabled-set, `k.h()` language-list builder, `k.m()`)
- `c3/w.java` — latRange segmentation (`w.g()`)
- `c3/x.java` — LocaleSpan detection (`x.g()`)

## How this was generated (exact commands, reproducible)

Tools (fetched from Maven Central — GitHub is blocked in this environment):
- **dex2jar**: `de.femtopedia.dex2jar` fork, v2.4.37 (maintained; original pxb1988 is unmaintained)
- **CFR**: `org.benf:cfr:0.152`

```bash
# 1. Unzip APK, grab classes.dex
unzip -o AutoTTS_5.7.7.1.apk -d apk_unzipped

# 2. dex -> jar  (classpath = all femtopedia dex2jar modules + ASM 9.10.1)
java -cp 'd2jlib/*' com.googlecode.dex2jar.tools.Dex2jarCmd -f -o autotts.jar apk_unzipped/classes.dex

# 3. jar -> readable Java with CFR
#    Settings chosen for MAX readability while staying CORRECTNESS-SAFE:
#      --rename true            fix illegal/duplicate identifiers; keep legal obfuscated
#                               names (k, o, a, b, w, x) so cross-refs stay stable
#      --hideutf false          show CLD2 unicode/language strings literally
#      --hidelongstrings false  never truncate long string constants
#      --forcetopsortnopull true resolves some try/catch structure failures, correctness-safe
#      --relinkconststring true relink inlined string constants back to symbolic form
#      --comments true          keep CFR's WARNING notes so behaviour-risk spots are visible
#    Deliberately NOT used:
#      --ignoreexceptions       -> would DROP exception handling = WRONG logic. Never use.
#      --antiobf / --aggressiveduff / --obfcontrol -> aggressive control-flow deobf risks
#                               wrong logic; tested to give identical structure results anyway.
java -jar cfr-0.152.jar autotts.jar \
  --outputdir decompiled_java \
  --rename true \
  --hideutf false \
  --hidelongstrings false \
  --forcetopsortnopull true \
  --relinkconststring true \
  --comments true \
  --usenametable true \
  --caseinsensitivefs false \
  --clobber true \
  --silent true
```

## Known decompiler limits (2 methods only)

CFR cannot fully structure **2 methods** because R8 emits a *try→catch back-jump*
that no decompiler (CFR or jadx) folds cleanly. The code is **fully present and
correct** — just rendered as labeled blocks / gotos with a `WARNING - Removed back
jump...` comment. Both are understood; their logic is documented here so nothing is
ambiguous:

1. **`c3/k.java` → `k.h(Context, boolean onlyEnabled)`** — builds the language list.
   Loops `k.d` engine entries; per new locale creates a `d` and loads prefs
   (`<code>_volume/_pitch/_speed` def 100, `_variant` def `*Default`, `_disabled`);
   force-enables any locale present in `k.n()` (flips `_disabled`, marks changed →
   `k.z()` re-save); merges duplicate locales' engine packages into `d.j`; in Google
   mode (`L==3`) only counts `com.google.android.tts`; optional only-enabled filter;
   final accent-insensitive (NFD + strip-diacritics) Collator sort.

2. **`com/vnspeak/autotts/AutoTtsService.java` → the `postDelayed(…, 50L)` `run()`** —
   per-chunk speak dispatch. Resolves language by mode (`L`): Dual(1) span-type→eng/`D`;
   Mix(4)/Auto(2) CLD2 `clsCLD2.b()` → 2-char ISO map → `H`/`I` fallback → `h()`
   availability (`"Disable"` re-resolve); sets language via `B()`; applies
   volume/pitch/speed prefs; builds/strips the params `Bundle` (extra strip when `P`);
   attaches `UtteranceProgressListener`; sets accessibility `AudioAttributes` when `Q`;
   `speak(QUEUE_FLUSH)`. Error codes: 2/3 Dual-unsupported, 4 auto-unsupported,
   5 speak-failed, 6 onDone-error, 14 mix-unsupported.
