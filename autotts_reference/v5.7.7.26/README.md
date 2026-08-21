# AutoTTS 5.7.7.26 reference (com.vnspeak.autotts, versionCode 90000254)

The newest AutoTTS release, kept beside the 5.7.7.18 tree in the parent directory rather
than replacing it. Two reasons: EasyVoice's current behaviour is verified against 5.7.7.18
and every path in `CLAUDE.md` and `ANALYSIS_5.7.7.18_delta.md` points at it, and the
obfuscated names shifted again in 5.7.7.26, so having both side by side is what makes the
letter-for-letter translation checkable.

**Committed on purpose — do not delete.** The container is ephemeral; git is the only
durable home for this.

Start with **`ANALYSIS_5.7.7.26_delta.md`** — it is the full 5.7.7.18 → 5.7.7.26 diff,
including the new obfuscated-name map.

| Path | What it is |
|---|---|
| `AutoTTS_5.7.7.26.apk` | The APK. Everything else regenerates from it. |
| `decompiled_java/` | Full CFR decompile, 1661 `.java`, standard settings. |
| `decompiled_java_noexc/` | The 18 classes the standard settings could not do, decompiled with exception blocks ignored. See the warning below. |
| `decompiled_res/` | `res/` and `AndroidManifest.xml` via apktool. |
| `ANALYSIS_5.7.7.26_delta.md` | What changed and where, with the code quoted. |

## Reading order

1. `decompiled_java/` first, always. Its exception handling is real.
2. If the method you want says `Decompilation failed`, open the same path under
   `decompiled_java_noexc/`.

## The `_noexc` tree — read this before trusting it

`--ignoreexceptionsalways true` makes CFR **discard exception blocks entirely**. Every
method decompiles, but `try` / `catch` / `finally` are gone from the output, and
`synchronized` shows up as `// MONITORENTER :` / `// MONITOREXIT :` comments instead of a
block. Control flow inside a method is faithful; error handling is not there at all.

So: use it to read logic (which is why it exists — `AutoTtsService.onSynthesizeText` is in
it), and never quote it as evidence about what AutoTTS catches or how it recovers. For that,
go back to `decompiled_java/`, or to baksmali.

Files in it: `com/vnspeak/autotts/AutoTtsService.java`, `c3/k.java`, `c3/p.java`,
`w/a.java`, plus fourteen androidx / material / pairip classes that are not ours.

## Why the settings are what they are

In 5.7.7.26 CFR's usual flags fail on `AutoTtsService.onSynthesizeText`:

```
org.benf.cfr.reader.util.ConfusedCFRException: Back jump on a try block
    [egrp 7[TRYBLOCK] [8 : 753->834)] java.lang.Throwable
```

That is the mode-dispatch method — the single most important one in the app — so "leave it
undecompiled" was not an option. Thirteen flag combinations were tried on the isolated
class. Results:

| Flags | Result |
|---|---|
| the standard set (`--forcetopsortnopull true`) | FAIL |
| `--forcetopsortnopull false --aexagg true` (the old fallback in `CLAUDE.md`) | FAIL |
| no `forcetopsort` at all | FAIL |
| `--tryresources false` | FAIL |
| `--decodefinally false` | FAIL |
| `--forceexceptionprune true` | FAIL |
| `--lenient true` | FAIL |
| `--aexagg2 true` | FAIL |
| `--forcetopsort true` / `--forcetopsortaggress true` | FAIL |
| `--recover false` | FAIL |
| `--ignoreexceptions true` | OK — 705 lines, 42 labelled blocks |
| **`--ignoreexceptionsalways true`** | **OK — 656 lines, 27 labelled blocks** |

`--ignoreexceptionsalways` won on readability, so that is what `decompiled_java_noexc/`
uses. Adding `--aexagg` or `--forcetopsortnopull` on top changed nothing (identical output),
so they are not in the command.

Only four classes still fail even then, all library code we never read:
`androidx/appcompat/view/menu/ActionMenuItemView`, `androidx/constraintlayout/widget/b`,
`com/google/android/material/button/MaterialButtonGroup`,
`com/google/android/material/navigation/NavigationBarView`.

## Regenerating (reproducible)

Tools come from **Maven Central** — GitHub and `search.maven.org` are blocked by this
container's proxy, `repo1.maven.org` is not.

```bash
M=https://repo1.maven.org/maven2

# CFR
curl -O $M/org/benf/cfr/0.152/cfr-0.152.jar

# apktool 2.9.3 — the cli jar alone is not enough, it needs all of these on the classpath
mkdir apktool_cp && cd apktool_cp
for a in apktool-lib apktool-cli brut.j.common brut.j.util brut.j.dir; do
  curl -O $M/org/apktool/$a/2.9.3/$a-2.9.3.jar
done
curl -O $M/commons-cli/commons-cli/1.6.0/commons-cli-1.6.0.jar          # brut.apktool.Main
curl -O $M/xpp3/xpp3/1.1.4c/xpp3-1.1.4c.jar                             # XmlSerializer
curl -O $M/org/apache/commons/commons-lang3/3.14.0/commons-lang3-3.14.0.jar
curl -O $M/commons-io/commons-io/2.15.1/commons-io-2.15.1.jar
curl -O $M/com/google/guava/guava/33.0.0-jre/guava-33.0.0-jre.jar       # LittleEndianDataInputStream
cd ..

# dex2jar: use ../tools/d2j-base-cmd-2.4.37.jar plus dex-tools/dex-*/asm-* 2.4.37 / 9.10.1
```

```bash
# resources (-s skips the dex)
java -cp 'apktool_cp/*' brut.apktool.Main d -s -f AutoTTS_5.7.7.26.apk -o decoded
cp -r decoded/res decoded/AndroidManifest.xml decompiled_res/

# dex -> jar
unzip -o AutoTTS_5.7.7.26.apk 'classes*.dex'
java -cp 'd2j/*' com.googlecode.dex2jar.tools.Dex2jarCmd -f -o autotts26.jar classes.dex

# jar -> java, primary tree
java -Xmx3g -jar cfr-0.152.jar autotts26.jar --outputdir decompiled_java \
  --rename true --hideutf false --hidelongstrings false \
  --forcetopsortnopull true --relinkconststring true --comments true

# jar -> java, fallback tree for the classes above
java -Xmx3g -jar cfr-0.152.jar autotts26.jar --outputdir decompiled_java_noexc \
  --rename true --hideutf false --hidelongstrings false \
  --relinkconststring true --comments true --ignoreexceptionsalways true
```

`versionCode` and the SDK levels come from `decoded/apktool.yml`, not the manifest — apktool
moves `<uses-sdk>` there:

```yaml
versionInfo: { versionCode: 90000254, versionName: 5.7.7.26 }
sdkInfo:     { minSdkVersion: 26, targetSdkVersion: 36 }
```

## The native library

`lib/arm64-v8a/libcld2.so` differs from 5.7.7.18, but only just. Same size, same 494 global
`FUNC` symbols, same dynamic section, and **`.rodata` is byte-identical**, so the CLD2 model
tables did not change. The only real difference is three inserted instructions in
`getLanguageSpans`, covered in section 6 of the analysis. Compare with:

```bash
readelf -SW libcld2.so                       # section offsets/sizes
llvm-objdump -d --start-address=0x65392c --stop-address=0x654200 libcld2.so \
  | sed -E 's/^ *[0-9a-f]+: [0-9a-f]{8} +//; s/0x[0-9a-f]+ <[^>]*>/TGT/g'
```

The `sed` strips addresses and branch targets, which is what makes the two builds
diffable — without it every line looks changed because the whole tail shifted 12 bytes.
