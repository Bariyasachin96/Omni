#!/usr/bin/env bash
# EVERY UNICODE SCRIPT THROUGH THE REAL DETECTOR, IN EVERY MODE (owner,
# 2026-10-16: "Unicode script mein kuchh chijen aisi hogi jo detect nahin hoti").
# One sample per script of the UCD the app is generated from goes through the
# core's real JNI entry points (main.cpp, around tools/pgo/fakejni.h) under the
# owner's set {hi, en, gu} and again with the script's own language enabled; the
# REAL IsoCodes then turns each answer into the language a voice is looked up by
# (Check.kt). Every script in use today (CLDR: UAX #31 Recommended or Limited_Use)
# must reach its CLDR language in mix and auto mode, and be non-Latin in dual.
#   run.sh             verdicts; exit 1 on any wrong script
#   EV_NEG=nofixed     the core without its fixed-slot languages  -> must FAIL
#   EV_NEG=noiso       IsoCodes without the script languages      -> must FAIL
#   EV_NEG=truncate    auto mode cutting the code to two letters  -> must FAIL
# Needs g++, a JDK and kotlinc (tools/bootstrap.sh).
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
SRC="$ROOT/app/src/main/java/com/sachinbaria/easyvoice"
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-allscripts
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
NEG=${EV_NEG:-}
# The auto branch hands toIso3 the whole code (it drops a subtag itself).
if grep -q 'rawDetected.substring(0, 2)' "$SRC/EasyVoiceTtsService.kt"; then
  echo "FAILED: the auto branch cuts the detected code to two letters again"; exit 1; fi
INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$CPP"
mkdir -p "$WORK/obj" "$WORK/neg"
compile() {
  local src=$1
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ] || [ -n "$(find "$(dirname "$src")" -maxdepth 1 -name "*.h" -newer "$obj" -print -quit)" ]; then
    rm -f "$obj"
    g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src" || { echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
core="$CPP/tts_engine_core.cpp"
if [ "$NEG" = nofixed ]; then
  python3 - "$core" "$WORK/neg/tts_engine_core.cpp" <<'PY'
import sys
s = open(sys.argv[1], encoding="utf-8").read()
a = "lang = kUniFixedSlotLang[script - EV_FIXED_SLOT_FIRST];"
assert a in s; open(sys.argv[2], "w", encoding="utf-8").write(s.replace(a, 'lang = "un";'))
PY
  cp "$CPP"/*.h "$WORK/neg/"; core="$WORK/neg/tts_engine_core.cpp"
fi
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  [ "$f" = "$CPP/tts_engine_core.cpp" ] && f=$core
  objs="$objs $(compile "$f")"; done
rm -f "$WORK/obj/main.o" "$WORK/probe"
g++ -c -O2 -std=c++17 -w $INC -o "$WORK/obj/main.o" "$HERE/main.cpp" || exit 1
g++ -o "$WORK/probe" "$WORK/obj/main.o" $objs -lpthread

# the Kotlin half: the real IsoCodes.kt and UnicodeTables.kt, as tools/verify/toiso3 lifts them
python3 - "$SRC" "$HERE/Check.kt" "$WORK/Check.kt" "$NEG" <<'PY'
import sys
src, drv, out, neg = sys.argv[1:5]
def body(name):
    t = open(src + "/" + name, encoding="utf-8").read()
    return t.split("\n", 1)[1] if t.startswith("package ") else t
iso = body("IsoCodes.kt")
a = "android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.BAKLAVA"
assert a in iso; iso = iso.replace(a, "true")
if neg == "noiso":
    a = "for (code in UnicodeTables.SCRIPT_LANGUAGES) {"
    assert a in iso; iso = iso.replace(a, "for (code in emptyArray<String>()) {")
text = body("UnicodeTables.kt") + "\n" + iso + "\n" + open(drv, encoding="utf-8").read()
imports = [l for l in text.split("\n") if l.startswith("import ")]
rest = [l for l in text.split("\n") if not l.startswith("import ")]
open(out, "w", encoding="utf-8").write("\n".join(sorted(set(imports)) + rest))
PY
rm -f "$WORK/check.jar"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "Check.kt did not compile"; exit 1; }

python3 -I "$HERE/texts.py" "$ROOT/tools/unicode/ucd" > "$WORK/scripts.tsv"
python3 -I "$HERE/langs.py" "$ROOT/tools/unicode/ucd" "$ROOT/tools/unicode/cldr" > "$WORK/langs.tsv"
java -jar "$WORK/check.jar" prep "$WORK/langs.tsv" > "$WORK/own.tsv"
java -jar "$WORK/check.jar" pairs > "$WORK/isopairs.tsv"
"$WORK/probe" "$WORK/scripts.tsv" "$WORK/own.tsv" "$WORK/isopairs.tsv" > "$WORK/probe.txt"
[ -n "${EV_VERBOSE:-}" ] && cat "$WORK/probe.txt"
EV_NEG="$NEG" java -jar "$WORK/check.jar" check "$WORK/langs.tsv" "$WORK/probe.txt"
