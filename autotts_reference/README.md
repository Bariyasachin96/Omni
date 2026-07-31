# AutoTTS Reference (com.vnspeak.autotts 5.7.7.10)

**Purpose:** Decompiled, human-readable reference of the *AutoTTS* Android app.
EasyVoice (this repo) matches AutoTTS's behaviour, so this is the ground-truth
source we compare against. **These files are committed on purpose — do NOT delete
them.** The build container is ephemeral (scratchpad wiped on reset), so git is the
only durable home for this reference.

Updated from **5.7.7.1 → 5.7.7.10** (versionCode 90000237). The old 5.7.7.1
reference was removed and fully regenerated from the new APK.

> Note: AutoTTS is obfuscated and the obfuscated names change per build. The
> 5.7.7.1 mappings (e.g. `c3.k`, `a.e`, `k.o`) may live under different obfuscated
> classes in 5.7.7.10 — re-map against this decompile when comparing.

## Contents

| Path | What it is |
|------|------------|
| `AutoTTS_5.7.7.10.apk` | Original APK — the ground truth. Everything else regenerates from this. |
| `decompiled_java/` | Full CFR decompile (1651 `.java`). |
| `decompiled_res/` | Decoded resources: `res/values/*` (strings/colors/styles/…), all layouts, `AndroidManifest.xml`. |

### Key app source files (inside `decompiled_java/`)
- `com/vnspeak/autotts/AutoTtsService.java` — main TTS synthesis service
- `com/vnspeak/autotts/clsCLD2.java` — CLD2 language detection
- `com/vnspeak/autotts/a.java` — script/lang mapping
- `com/vnspeak/autotts/NewSettingsActivity.java` — settings UI (tabs, spinners, sliders)
- `com/vnspeak/autotts/CheckVoiceData.java`, `GetSampleText.java` — engine intents

## Tools kept here (do not delete)

`tools/d2j-base-cmd-2.4.37.jar` — dex2jar's `com.googlecode.dex2jar.tools.BaseCmd`
lives in its own Maven artifact, `de.femtopedia.dex2jar:d2j-base-cmd`, which is NOT
pulled in by `dex2jar`/`dex-tools`. Without it every `Dex2jarCmd` run dies with
`NoClassDefFoundError: com/googlecode/dex2jar/tools/BaseCmd`. It is 14 KB, it is
needed every time the APK is re-converted, and the scratchpad is wiped on container
reset — so it is committed here.

```bash
# if it ever needs re-fetching (search.maven.org is blocked; repo1 is not):
curl -O https://repo1.maven.org/maven2/de/femtopedia/dex2jar/d2j-base-cmd/2.4.37/d2j-base-cmd-2.4.37.jar

# converting the APK's dex, with that jar on the classpath:
java -cp 'd2j/*' com.googlecode.dex2jar.tools.Dex2jarCmd -f -o app.jar classes.dex
```

### Methods CFR cannot decompile

Two methods fail with `ConfusedCFRException` and stay failing even with
`--forcetopsortnopull false --aexagg true`, so they must be read from baksmali:

| method | why it matters |
|---|---|
| `AutoTtsService$e$a.run()` | the later-chunk speak path — its logs, its endSynthesis numbers and its inline (not posted) speak all differ from the first chunk's |
| `c3.o.h(level, tag, msg)` | the logger's write path |

For `e$a.run` the readable form is baksmali output with R8's `.line` directives
stripped (3384 lines -> 527 real instructions). `javap -c` on the dex2jar output is a
good independent cross-check of any constant read that way.

## How this was regenerated (reproducible)

Tools fetched from Maven Central (GitHub is blocked here):
- **apktool** `org.apktool:apktool-lib/cli:2.9.3` (resources)
- **dex2jar** `de.femtopedia.dex2jar:dex2jar:2.4.37` + ASM 9.10.1
- **CFR** `org.benf:cfr:0.152`

```bash
# resources (skip dex)
java -cp 'apktool_cp/*' brut.apktool.Main d -s -f AutoTTS_5.7.7.10.apk -o decoded
cp -r decoded/res decoded/AndroidManifest.xml decompiled_res/

# dex -> jar -> java
unzip -o AutoTTS_5.7.7.10.apk 'classes*.dex'
java -cp 'd2j_cp/*' com.googlecode.dex2jar.tools.Dex2jarCmd -f -o autotts.jar classes.dex
java -jar cfr-0.152.jar autotts.jar --outputdir decompiled_java \
  --rename true --hideutf false --hidelongstrings false \
  --forcetopsortnopull true --relinkconststring true --comments true
```
