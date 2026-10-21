#!/usr/bin/env bash
# TWO LANGUAGES OF ONE SCRIPT IN ONE TEXT, each to its own voice (owner,
# 2026-10-21: "Arabic language Arabic voice ke pass ... aisa Nahin Ki Ek Hi
# voice do Bhasha Pade"). See check.py.
#   run.sh               verdicts; exit 1 on any failure
#   EV_NEG=nosegment     a span is never cut                    -> must FAIL
#   EV_NEG=nostep0       the letters never rule a language out  -> must FAIL
#   EV_REF=<commit>      the core read before (default HEAD), for what was so already
# Needs g++, a JDK and FLORES-200 (tools/langid/fetch_train.sh); a reference
# core from before 2026-10-21 also needs CLD2 (tools/verify/clone-cld2.sh).
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-mixedscript
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
NEG=${EV_NEG:-}
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
elif neg == "nostep0": swap("        if (orthoDecide(slot, text, bytes, res.slotLangs, keep, total, best, true)) {", "        if (false) {")
else: sys.exit("unknown EV_NEG " + neg)
open(p, "w", encoding="utf-8").write(s)
PY
  SRC="$WORK/neg"
fi
INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$SRC -I$CPP"
rm -f "$WORK/seg" "$WORK/probe" "$WORK/probe-ref"
g++ -O2 -std=c++17 -w $INC -o "$WORK/seg" "$HERE/seg.cpp"
g++ -O2 -std=c++17 -w -Wno-narrowing $INC -o "$WORK/probe" "$HERE/probe.cpp" -lpthread
# the same probe around the core of a commit before (EV_REF, default HEAD): what
# the text was read as before, so a one-language text the change reads in two
# languages where that core read it in one is told apart from what was so already
# (a core from before 2026-10-21 links CLD2, compiled here from the clone)
REF=${EV_REF:-HEAD}
rm -rf "$WORK/ref"; mkdir -p "$WORK/ref"
git -C "$ROOT" archive "$REF" app/src/main/cpp | tar -x -C "$WORK/ref"
RDIR="$WORK/ref/app/src/main/cpp"
SOURCES="$ROOT/tools/verify/latency/sources.py"
RX=$(python3 "$SOURCES" --includes "$RDIR") || exit 2
robjs=""
for f in $(python3 "$SOURCES" "$RDIR" | grep -v '/tts_engine_core.cpp$'); do
  o="$WORK/obj/$(basename "$f" .cc).o"
  [ -f "$o" ] && [ "$o" -nt "$f" ] || g++ -c -O2 -std=c++17 -w $RX -o "$o" "$f"
  robjs="$robjs $o"
done
RINC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$RDIR $RX"
g++ -O2 -std=c++17 -w -Wno-narrowing $RINC -o "$WORK/probe-ref" "$HERE/probe.cpp" $robjs -lpthread
echo "reference core: $REF ($(git -C "$ROOT" rev-parse --short "$REF"))"
python3 "$HERE/check.py" "$WORK"
