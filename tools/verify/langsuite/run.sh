#!/usr/bin/env bash
# The voice the core chooses for labelled text in every language, short and
# long, where two or more enabled languages share a script -- with Google's ML
# Kit as the language identifier (owner, 2026-10-28: "ML Kit use karenge har
# jagah"), run on this desktop by tools/mlkit, as on a phone.
#
#   tools/verify/langsuite/run.sh
#
# Needs g++, python3, a JDK's jni.h and the UDHR (fetched below); with
# tools/verify/langsuite/fetch_data.sh run once, Android's UI strings and
# Tatoeba's sentences are measured as well. The ISO pairs come from
# tools/verify/allscripts/run.sh.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
CACHE="$ROOT/tools/.cache"
TMP=${EV_WORK:-${TMPDIR:-/tmp}}
WORK=$TMP/ev-verify-langsuite
PAIRS=$TMP/ev-verify-allscripts/isopairs.tsv
[ -f "$PAIRS" ] || { echo "run tools/verify/allscripts/run.sh first (isopairs.tsv)"; exit 2; }
mkdir -p "$WORK"
UDHR_SHA=0796c314b09a930c989c6f9d93d226af9af13feccd88496e196c743dd266c7f3
if [ ! -d "$CACHE/udhr2" ]; then
  curl -sSfL -o "$CACHE/udhr2.zip" https://raw.githubusercontent.com/nltk/nltk_data/gh-pages/packages/corpora/udhr2.zip
  echo "$UDHR_SHA  $CACHE/udhr2.zip" | sha256sum -c --quiet
  (cd "$CACHE" && unzip -q -o udhr2.zip)
fi
source "$ROOT/tools/mlkit/build.sh"
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/pgo -I$ROOT/tools/mlkit -I$CPP"
rm -f "$WORK/main"
g++ -O2 -std=c++17 -w -Wno-narrowing $INC -o "$WORK/main" "$HERE/main.cpp" "$CPP/tts_engine_core.cpp" "$MLKIT_HOST" -ldl -lm -lpthread
python3 "$HERE/cases.py" "$CACHE/udhr2" "$HERE/phrases.txt" "$WORK/cases.tsv"
# Android's own UI strings and Tatoeba's sentences too, once fetch_data.sh has run
[ -d "$CACHE/langdata/aosp" ] && python3 -I "$HERE/realdata.py" "$CACHE/langdata" "$ROOT/tools/unicode/ucd/Scripts.txt" \
  "$HERE/sets.txt" >> "$WORK/cases.tsv"
LD_LIBRARY_PATH="$MLKIT_LIBS" EV_MLKIT_MODEL="$MLKIT_MODEL" "$WORK/main" "$WORK/cases.tsv" "$HERE/sets.txt" "$PAIRS" \
  > "$WORK/choices.tsv" 2> >(grep -v "Initialized TensorFlow Lite runtime" >&2)
python3 "$HERE/report.py" "$WORK/choices.tsv"
