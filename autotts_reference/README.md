# AutoTTS Reference (com.vnspeak.autotts 5.7.7.18)

**Purpose:** Decompiled, human-readable reference of the *AutoTTS* Android app.
EasyVoice (this repo) matches AutoTTS's behaviour, so this is the ground-truth
source we compare against. **These files are committed on purpose — do NOT delete
them.** The build container is ephemeral (scratchpad wiped on reset), so git is the
only durable home for this reference.

Updated from **5.7.7.10 → 5.7.7.18** (versionCode 90000245). The old 5.7.7.10
reference was removed and fully regenerated from the new APK.

> Note: AutoTTS is obfuscated and the obfuscated names change per build. Almost
> every `c3.*` class shifted by one letter between 5.7.7.10 and 5.7.7.18
> (`c3.m` → `c3.n`, `c3.j` → `c3.k`, `c3.y` → `c3.d0`, …). Re-map against this
> decompile when comparing; the 5.7.7.18 map is in `CLAUDE.md`.

## Contents

| Path | What it is |
|------|------------|
| `AutoTTS_5.7.7.18.apk` | Original APK — the ground truth. Everything else regenerates from this. |
| `decompiled_java/` | Full CFR decompile (1656 `.java`). |
| `decompiled_res/` | Decoded resources: `res/values/*` (strings/colors/styles/…), all layouts, `AndroidManifest.xml`. |
| `ANALYSIS_5.7.7.10_mix.md` | Verified findings from the 5.7.7.10 pass. Still valid for behaviour, but **its class/method names are the 5.7.7.10 ones** — translate through the map in `CLAUDE.md`. |

### Key app source files (inside `decompiled_java/`)
- `com/vnspeak/autotts/AutoTtsService.java` — main TTS synthesis service
- `com/vnspeak/autotts/clsCLD2.java` — CLD2 language detection
- `com/vnspeak/autotts/a.java` — script/lang mapping (byte-identical to 5.7.7.10)
- `com/vnspeak/autotts/NewSettingsActivity.java` — settings host activity
- `c3/n.java` — settings store (was `c3/m.java`)
- `c3/k.java` — settings fragment / all tabs (was `c3/j.java`)
- `c3/d0.java` — number / punctuation / emoji segmenter + smart number reading (was `c3/y.java`)
- `c3/e0.java` — one segment: text + type (was `c3/z.java`)

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

In 5.7.7.18 three files carry a `ConfusedCFRException`: `c3/k.java` (the settings
fragment), `c3/p.java` and `w/a.java`. Read those specific methods from baksmali;
everything else in the app package decompiled cleanly.

`javap -c` on the dex2jar output is a good independent cross-check of any constant
read that way.

## How this was regenerated (reproducible)

Tools fetched from Maven Central (GitHub is blocked here):
- **apktool** `org.apktool:apktool-lib/cli:2.9.3` (resources)
- **dex2jar** `de.femtopedia.dex2jar:dex2jar:2.4.37` + ASM 9.10.1
- **CFR** `org.benf:cfr:0.152`

```bash
# resources (skip dex)
java -cp 'apktool_cp/*' brut.apktool.Main d -s -f AutoTTS_5.7.7.18.apk -o decoded
cp -r decoded/res decoded/AndroidManifest.xml decompiled_res/

# dex -> jar -> java
unzip -o AutoTTS_5.7.7.18.apk 'classes*.dex'
java -cp 'd2j_cp/*' com.googlecode.dex2jar.tools.Dex2jarCmd -f -o autotts.jar classes.dex
java -jar cfr-0.152.jar autotts.jar --outputdir decompiled_java \
  --rename true --hideutf false --hidelongstrings false \
  --forcetopsortnopull true --relinkconststring true --comments true
```
