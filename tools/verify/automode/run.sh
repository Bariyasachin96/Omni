#!/usr/bin/env bash
# AUTO MODE ON LABELLED TEXT, an earlier core against the working tree's
# (2026-10-21: the window detector was CLD2's getLanguage until CLD2 left the
# app, then the model ML Kit runs; since 2026-10-28 it is Google's ML Kit
# library itself, run here by tools/mlkit). See main.cpp and compare.py.
#   run.sh [ref]      ref defaults to HEAD
# Needs the language suite's cases (tools/verify/langsuite/run.sh), the ISO
# pairs tools/verify/allscripts/run.sh writes, and for a ref from before
# 2026-10-21 CLD2 (tools/verify/clone-cld2.sh).
set -euo pipefail
REF=${1:-HEAD}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
TMP=${EV_WORK:-${TMPDIR:-/tmp}}
WORK=$TMP/ev-verify-automode
CASES=$TMP/ev-verify-langsuite/cases.tsv
PAIRS=$TMP/ev-verify-allscripts/isopairs.tsv
[ -f "$CASES" ] || { echo "run tools/verify/langsuite/run.sh first (cases.tsv)"; exit 2; }
[ -f "$PAIRS" ] || { echo "run tools/verify/allscripts/run.sh first (isopairs.tsv)"; exit 2; }
rm -rf "$WORK/old"; mkdir -p "$WORK/obj" "$WORK/old"
git -C "$ROOT" archive "$REF" app/src/main/cpp | tar -x -C "$WORK/old" --strip-components=4
SRC="$ROOT/tools/verify/latency/sources.py"
OLDINC=$(python3 "$SRC" --includes "$WORK/old") || exit 2
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$ROOT/tools/mlkit -I$CPP"
# Google's ML Kit, which the working tree's core asks (2026-10-28), run here by
# tools/mlkit; a ref from before asks its own model and ignores it.
source "$ROOT/tools/mlkit/build.sh"
compile() {   # compile <source> <extra -I flags>
  local src=$1 extra=$2
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ] || [ -n "$(find "$(dirname "$src")" -maxdepth 1 -name "*.h" -newer "$obj" -print -quit)" ]; then
    rm -f "$obj"
    g++ -c -O2 -std=c++17 -w -Wno-narrowing $INC $extra -o "$obj" "$src" || { echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
rm -f "$WORK/obj/$(echo "$WORK/old/tts_engine_core.cpp" | md5sum | cut -c1-16).o"
oldobjs=""; newobjs=""
for f in $(python3 "$SRC" "$WORK/old"); do oldobjs="$oldobjs $(compile "$f" "$OLDINC")"; done
for f in $(python3 "$SRC" "$CPP"); do newobjs="$newobjs $(compile "$f" "")"; done
g++ -c -O2 -std=c++17 -w $INC -o "$WORK/obj/main.o" "$HERE/main.cpp" || exit 1
g++ -o "$WORK/probe-old" "$WORK/obj/main.o" $oldobjs "$MLKIT_HOST" -ldl -lm -lpthread
g++ -o "$WORK/probe-new" "$WORK/obj/main.o" $newobjs "$MLKIT_HOST" -ldl -lm -lpthread
SETS="$ROOT/tools/verify/langsuite/sets.txt"
export LD_LIBRARY_PATH="$MLKIT_LIBS${LD_LIBRARY_PATH:+:$LD_LIBRARY_PATH}" EV_MLKIT_MODEL="$MLKIT_MODEL"
"$WORK/probe-old" "$CASES" "$SETS" "$PAIRS" > "$WORK/old.tsv" &
"$WORK/probe-new" "$CASES" "$SETS" "$PAIRS" > "$WORK/new.tsv" 2> >(grep -v "Initialized TensorFlow Lite runtime" >&2) &
wait
echo "reference core: $REF ($(git -C "$ROOT" rev-parse --short "$REF"))"
python3 "$HERE/compare.py" "$WORK/old.tsv" "$WORK/new.tsv" "$PAIRS"
