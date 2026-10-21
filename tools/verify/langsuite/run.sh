#!/usr/bin/env bash
# The detector (the model ML Kit runs, langid.h, and langblend.h's choice
# between the enabled languages of one script) on labelled text in every
# language the model has, short and long (owner, 2026-10-20: "pahle test karo
# internally"; CLD2, measured beside it until 2026-10-21, is gone).
#
#   tools/verify/langsuite/run.sh
#   EV_BASELINE=<pipeline.tsv of an earlier run>   adds a "before" column
#
# Needs g++, python3 and the UDHR, which tools/verify/langid/run.sh fetches
# into tools/.cache/udhr2; with tools/verify/langsuite/fetch_data.sh run once,
# Android's UI strings and Tatoeba's sentences are measured as well.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
CACHE="$ROOT/tools/.cache"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-langsuite
mkdir -p "$WORK/obj"
[ -d "$CACHE/udhr2" ] || { echo "fetch the UDHR first: tools/verify/langid/run.sh"; exit 2; }

rm -f "$WORK/detect"
g++ -O2 -std=c++17 -w -I"$CPP" -o "$WORK/detect" "$HERE/detect.cpp" -lpthread
python3 "$HERE/cases.py" "$CACHE/udhr2" "$HERE/phrases.txt" "$WORK/cases.tsv"
# Android's own UI strings and Tatoeba's sentences too, once fetch_data.sh has run
[ -d "$CACHE/langdata/aosp" ] && python3 -I "$HERE/realdata.py" "$CACHE/langdata" "$ROOT/tools/unicode/ucd/Scripts.txt" \
  "$HERE/sets.txt" >> "$WORK/cases.tsv"
"$WORK/detect" "$WORK/cases.tsv" "$HERE/sets.txt" "$WORK/raw.tsv" "$WORK/pipeline.tsv"
python3 "$HERE/report.py" "$WORK/raw.tsv" "$WORK/pipeline.tsv"
python3 "$HERE/combine.py" "$WORK/pipeline.tsv" ${EV_BASELINE:-}
