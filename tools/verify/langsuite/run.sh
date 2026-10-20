#!/usr/bin/env bash
# CLD2 against the model ML Kit runs (langid.h), on labelled text in every
# language the model has, short and long -- the suite the switch from one to
# the other was decided on (owner, 2026-10-20: "pahle test karo internally").
#
#   tools/verify/langsuite/run.sh
#
# Needs g++, python3, the CLD2 clone (tools/ci/clone-cld2.sh) and the UDHR,
# which tools/verify/langid/run.sh fetches into tools/.cache/udhr2; with
# tools/verify/langsuite/fetch_data.sh run once, Android's UI strings and
# Tatoeba's sentences are measured as well.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
CACHE="$ROOT/tools/.cache"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-langsuite
mkdir -p "$WORK/obj"
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 first: tools/ci/clone-cld2.sh"; exit 2; }
[ -d "$CACHE/udhr2" ] || { echo "fetch the UDHR first: tools/verify/langid/run.sh"; exit 2; }

INC="-I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP" | grep cld2_src); do
  o="$WORK/obj/$(basename "$f" .cc).o"
  [ -f "$o" ] && [ "$o" -nt "$f" ] || g++ -c -O2 -std=c++17 -w $INC -o "$o" "$f"
  objs="$objs $o"
done
g++ -O2 -std=c++17 -w $INC -o "$WORK/detect" "$HERE/detect.cpp" $objs
python3 "$HERE/cases.py" "$CACHE/udhr2" "$HERE/phrases.txt" "$WORK/cases.tsv"
# Android's own UI strings and Tatoeba's sentences too, once fetch_data.sh has run
[ -d "$CACHE/langdata/aosp" ] && python3 -I "$HERE/realdata.py" "$CACHE/langdata" "$ROOT/tools/unicode/ucd/Scripts.txt" \
  "$HERE/sets.txt" >> "$WORK/cases.tsv"
"$WORK/detect" "$WORK/cases.tsv" "$HERE/sets.txt" "$WORK/raw.tsv" "$WORK/pipeline.tsv"
python3 "$HERE/report.py" "$WORK/raw.tsv" "$WORK/pipeline.tsv"
python3 "$HERE/combine.py" "$WORK/pipeline.tsv"
