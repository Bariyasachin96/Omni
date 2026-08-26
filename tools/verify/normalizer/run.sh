#!/usr/bin/env bash
# Prove our Unicode normaliser equals AutoTTS's, over every code point.
#
#   tools/verify/normalizer/run.sh
#
# WHAT IS COMPARED. AutoTTS's clsCLD2.a(int) folds decorated Latin letters --
# maths-bold, script, circled, full-width, superscript -- back to plain ASCII
# before any language detection runs. Without it a whole sentence written in
# maths-bold, which is ordinary on social media, comes back UNKNOWN and falls
# through to the preferred language. Ours is normalizeFancyCodepoint in
# app/src/main/cpp/tts_engine_core.cpp.
#
# It is a 300-line ladder of nested switches transcribed by hand from CFR's
# output. CLAUDE.md says outright: if it is ever touched, redo this sweep --
# do NOT hand-check it. This is that sweep, and it is one command.
#
# Both sides print "<from hex> <to hex>" for every code point that changes, so
# diff is the whole test. There are 1,062 such code points.
#
# Needs: javac, g++, python3. No network.
set -euo pipefail

HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-normalizer

rm -rf "$WORK"
mkdir -p "$WORK/java"

echo "1/3  building the AutoTTS reference (javac)"
cp "$HERE"/java/*.java "$WORK/java/"
(cd "$WORK/java" && javac -encoding UTF-8 -nowarn *.java 2>&1 | grep -vE "^(Note:|Picked up)" || true)

echo "2/3  building ours (g++), from the current tree"
python3 "$ROOT/tools/verify/make_core_inc.py" \
        --source "$ROOT/app/src/main/cpp/tts_engine_core.cpp" \
        --until normalizeFancyText --out "$WORK/core.inc" >/dev/null
cp "$HERE"/cpp/sweep.cpp "$WORK/"
(cd "$WORK" && g++ -O2 -std=c++17 -I. -o sweep sweep.cpp)

echo "3/3  sweeping all 1,114,112 code points"
java -cp "$WORK/java" Norm 2>/dev/null | grep -v "^Picked up" > "$WORK/autotts.out"
"$WORK/sweep" > "$WORK/ours.out"

if diff -q "$WORK/autotts.out" "$WORK/ours.out" >/dev/null; then
  echo
  echo "IDENTICAL -- $(wc -l < "$WORK/ours.out") mappings over 1,114,112 code points"
  exit 0
fi

echo
echo "DIFFERENCES:"
diff "$WORK/autotts.out" "$WORK/ours.out" | head -20
echo "work kept in $WORK"
exit 1
