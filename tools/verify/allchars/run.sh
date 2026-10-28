#!/usr/bin/env bash
# EVERY CHARACTER THROUGH THE REAL DETECTOR (owner, 2026-10-21: "ek character
# bhi nahin chhutna chahie ... koi bhi Bhasha ka ... Chahe Koi Bhi Lipi Ho").
# Every assigned character of the UCD the app is generated from (letters, marks,
# digits, a script's own punctuation, and the Common/Inherited characters whose
# Script_Extensions name the scripts that use them) goes through the core's real
# JNI entry points (main.cpp, around tools/pgo/fakejni.h): alone under the
# owner's set {hi, en, gu} in mix, auto and dual, and -- for each language that
# writes one of its scripts -- alone and after a letter of that script with that
# language enabled too. The REAL IsoCodes turns every answer into the voice's
# iso3 (Map.kt); check.py judges each character against the enabled languages
# that write its scripts.
#   run.sh                verdicts; exit 1 on any character misrouted, dropped or split
#   EV_NEG=noattach       the segmenter without the attach rule       -> must FAIL
#   EV_NEG=noneutral      an all-neutral text without its scripts     -> must FAIL
#   EV_NEG=noextra        the seven extra scripts without fallback    -> must FAIL
#   EV_NEG=onlyold        the scx slot rule over AutoTTS's 25 slots   -> must FAIL
# Needs g++, a JDK and kotlinc (tools/bootstrap.sh).
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
SRC="$ROOT/app/src/main/java/com/sachinbaria/easyvoice"
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-allchars
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$ROOT/tools/mlkit -I$CPP"
# Google's ML Kit, which the core asks where the text has to name a language
# (2026-10-28), run here by tools/mlkit as on a phone
source "$ROOT/tools/mlkit/build.sh"
mkdir -p "$WORK/obj"
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
NEG=${EV_NEG:-}
if [ -n "$NEG" ]; then
  rm -rf "$WORK/neg"; mkdir -p "$WORK/neg"; cp "$CPP"/*.h "$WORK/neg/"
  python3 - "$core" "$WORK/neg" "$NEG" "$ROOT" <<'PY'
import os, subprocess, sys
core, out, neg, root = sys.argv[1:5]
s = open(core, encoding="utf-8").read()
def swap(a, b):
    global s
    assert a in s, a; s = s.replace(a, b)
if neg == "noattach":   swap("int alone = uniValue(kUniAttach, codePoint, -1);", "int alone = -1;")
elif neg == "noneutral": swap("if (curScript == 0 && neutralSlots != 0) curScript", "if (false) curScript")
elif neg == "noextra":  swap("for(int pair=0; pair<7; pair++){", "for(int pair=0; pair<0; pair++){")
elif neg == "onlyold":
    g = open(os.path.join(root, "tools/unicode/gen_tables.py"), encoding="utf-8").read()
    a = "    return {name_slot(SHORT_NAME[x]) for x in SCX.get(c, ())} - {None}"
    assert a in g
    g = g.replace(a, "    return {name_slot(SHORT_NAME[x]) for x in SCX.get(c, ())} - {None} - set(range(26, 64))")
    g = g.replace("HERE = os.path.dirname(os.path.abspath(__file__))", "HERE = %r" % os.path.join(root, "tools/unicode"))
    g = g.replace('OUT_H = os.path.join(ROOT, "app/src/main/cpp/unicode_tables.h")', 'OUT_H = %r' % os.path.join(out, "unicode_tables.h"))
    g = g.replace('OUT_KT = os.path.join(ROOT, "app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt")', 'OUT_KT = %r' % os.path.join(out, "UnicodeTables.kt"))
    open(os.path.join(out, "gen.py"), "w", encoding="utf-8").write(g)
    subprocess.check_call([sys.executable, os.path.join(out, "gen.py")], cwd=os.path.join(root, "tools/unicode"), env=dict(os.environ, UCD=os.path.join(root, "tools/unicode/ucd")))
else: sys.exit("unknown EV_NEG " + neg)
open(os.path.join(out, "tts_engine_core.cpp"), "w", encoding="utf-8").write(s)
PY
  core="$WORK/neg/tts_engine_core.cpp"
fi
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  [ "$f" = "$CPP/tts_engine_core.cpp" ] && f=$core
  objs="$objs $(compile "$f")"; done
rm -f "$WORK/obj/main.o" "$WORK/probe"
g++ -c -O2 -std=c++17 -w $INC -o "$WORK/obj/main.o" "$HERE/main.cpp" || exit 1
g++ -o "$WORK/probe" "$WORK/obj/main.o" $objs "$MLKIT_HOST" -ldl -lm -lpthread

# the Kotlin half: the real IsoCodes.kt and UnicodeTables.kt, as tools/verify/allscripts lifts them
python3 - "$SRC" "$HERE/Map.kt" "$WORK/Map.kt" <<'PY'
import sys
src, drv, out = sys.argv[1:4]
def body(name):
    t = open(src + "/" + name, encoding="utf-8").read()
    return t.split("\n", 1)[1] if t.startswith("package ") else t
iso = body("IsoCodes.kt")
a = "android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.BAKLAVA"
assert a in iso; iso = iso.replace(a, "true")
text = body("UnicodeTables.kt") + "\n" + iso + "\n" + open(drv, encoding="utf-8").read()
imports = [l for l in text.split("\n") if l.startswith("import ")]
rest = [l for l in text.split("\n") if not l.startswith("import ")]
open(out, "w", encoding="utf-8").write("\n".join(sorted(set(imports)) + rest))
PY
if [ ! -f "$WORK/map.jar" ] || [ "$WORK/Map.kt" -nt "$WORK/map.jar" ]; then
  rm -f "$WORK/map.jar"
  "$KOTLINC" -nowarn "$WORK/Map.kt" -include-runtime -d "$WORK/map.jar" 2>&1 | grep -v '^warning' || true
fi
[ -f "$WORK/map.jar" ] || { echo "Map.kt did not compile"; exit 1; }

python3 -I "$ROOT/tools/verify/allscripts/langs.py" "$ROOT/tools/unicode/ucd" "$ROOT/tools/unicode/cldr" > "$WORK/langs.tsv"
java -jar "$WORK/map.jar" prep "$WORK/langs.tsv" > "$WORK/own.tsv"
python3 -I "$HERE/cases.py" "$ROOT/tools/unicode/ucd" "$WORK/own.tsv" "$WORK"
java -jar "$WORK/map.jar" pairs > "$WORK/isopairs.tsv"
LD_LIBRARY_PATH="$MLKIT_LIBS" EV_MLKIT_MODEL="$MLKIT_MODEL" "$WORK/probe" "$WORK/cases.tsv" "$WORK/isopairs.tsv" > "$WORK/probe.txt" 2> >(grep -v "Initialized TensorFlow Lite runtime" >&2)
python3 -I - "$WORK/probe.txt" > "$WORK/codes.txt" <<'PY'
import sys
codes = set()
for line in open(sys.argv[1], encoding="utf-8"):
    f = line.rstrip("\n").split("\t")
    for sp in f[1].split(","):
        if "/" in sp and not sp.startswith("type"): codes.add(sp.rsplit("/", 1)[0])
    head, _, best = f[3].partition(">")
    if head: codes.add(head)
    if best: codes.add(best.rsplit("/", 1)[0])
print("\n".join(sorted(codes)))
PY
java -jar "$WORK/map.jar" map "$WORK/codes.txt" > "$WORK/codes.tsv"
python3 -I "$HERE/check.py" "$WORK/meta.tsv" "$WORK/probe.txt" "$WORK/codes.tsv"
