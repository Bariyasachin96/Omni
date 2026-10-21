#!/usr/bin/env bash
# TWO LANGUAGES OF ONE SCRIPT IN ONE TEXT, each to its own voice (owner,
# 2026-10-21: "Arabic language Arabic voice ke pass ... aisa Nahin Ki Ek Hi
# voice do Bhasha Pade"). See check.py.
#   run.sh               verdicts; exit 1 on any failure
#   EV_NEG=nosegment     a span is never cut                    -> must FAIL
#   EV_NEG=nostep0       the letters never rule a language out  -> must FAIL
#   EV_REF=<commit>      the core read before (default HEAD), for what was so already
# Needs g++, a JDK, the CLD2 clone and FLORES-200 (tools/langid/fetch_train.sh).
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-mixedscript
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
NEG=${EV_NEG:-}
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
mkdir -p "$WORK/obj"
SRC=$CPP
if [ -n "$NEG" ]; then
  rm -rf "$WORK/neg"; mkdir -p "$WORK/neg"; cp "$CPP"/*.h "$CPP/tts_engine_core.cpp" "$WORK/neg/"
  python3 - "$WORK/neg/langblend.h" "$NEG" <<'PY'
import sys
p, neg = sys.argv[1:3]
s = open(p, encoding="utf-8").read()
def swap(a, b):
    global s
    assert a in s, a; s = s.replace(a, b, 1)
if neg == "nosegment": swap("    if (!orthoModel(slot, langs, m, idx)) return cuts;", "    return cuts;")
elif neg == "nostep0": swap("        if (orthoDecide(slot, text, bytes, res.slotLangs, keep, total, best, &cld2Answer)) {", "        if (false) {")
else: sys.exit("unknown EV_NEG " + neg)
open(p, "w", encoding="utf-8").write(s)
PY
  SRC="$WORK/neg"
fi
INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$SRC -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP" | grep cld2_src); do
  o="$WORK/obj/$(basename "$f" .cc).o"
  [ -f "$o" ] && [ "$o" -nt "$f" ] || g++ -c -O2 -std=c++17 -w -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -o "$o" "$f"
  objs="$objs $o"
done
rm -f "$WORK/seg" "$WORK/probe" "$WORK/probe-ref"
g++ -O2 -std=c++17 -w $INC -o "$WORK/seg" "$HERE/seg.cpp" $objs
g++ -O2 -std=c++17 -w -Wno-narrowing $INC -o "$WORK/probe" "$HERE/probe.cpp" $objs -lpthread
# the same probe around the core of a commit before (EV_REF, default HEAD): what
# the text was read as before, so a one-language text the change reads in two
# languages where that core read it in one is told apart from what was so already
REF=${EV_REF:-HEAD}
rm -rf "$WORK/ref"; mkdir -p "$WORK/ref"
git -C "$ROOT" archive "$REF" app/src/main/cpp | tar -x -C "$WORK/ref"
RINC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$WORK/ref/app/src/main/cpp -I$CPP/cld2_src/public -I$CPP/cld2_src/internal"
g++ -O2 -std=c++17 -w -Wno-narrowing $RINC -o "$WORK/probe-ref" "$HERE/probe.cpp" $objs -lpthread
echo "reference core: $REF ($(git -C "$ROOT" rev-parse --short "$REF"))"
python3 "$HERE/check.py" "$WORK"
