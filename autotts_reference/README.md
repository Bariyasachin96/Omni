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
