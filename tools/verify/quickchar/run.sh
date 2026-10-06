#!/usr/bin/env bash
# Quick character read stops only on a lone Latin/Common/Inherited character;
# every other lone character (a matra, a halant, one akshar) is detected exactly
# as without quick read. Every BMP code point, the REAL core with CLD2.
#   EV_NEG=always   the old rule (stop on every lone character) -- must be caught
#   EV_NEG=kotlin   the Kotlin table drops a range -- must be caught
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-quickchar
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
mkdir -p "$WORK/obj" "$WORK/src"
INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$WORK/src -I$CPP"
cld=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  case "$f" in */tts_engine_core.cpp) ;; *)
    o="$WORK/obj/$(basename "$f" .cc).o"
    [ -f "$o" ] || g++ -c -O2 -std=c++17 -w $INC -o "$o" "$f" || { echo "compile failed: $f"; exit 1; }
    cld="$cld $o" ;;
  esac
done
cp "$CPP/tts_engine_core.cpp" "$CPP/unicode_tables.h" "$WORK/src/"
cp "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt" "$WORK/src/"
case "${EV_NEG:-}" in
  "") ;;
  always) sed -i 's/bool quickStop = singleStops \&\& units == 1 \&\& uniIn(kUniLatinCommonInherited, firstCodepointOf(folded));/bool quickStop = singleStops \&\& units == 1;/' "$WORK/src/tts_engine_core.cpp" ;;
  kotlin) sed -i 's/^        0x2EC, 0x36F,$/        0x2EC, 0x36E,/' "$WORK/src/UnicodeTables.kt" ;;   # Inherited U+036F lost
  *) echo "unknown EV_NEG"; exit 2 ;;
esac
if [ -n "${EV_NEG:-}" ] && cmp -s "$WORK/src/tts_engine_core.cpp" "$CPP/tts_engine_core.cpp" && cmp -s "$WORK/src/UnicodeTables.kt" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt"; then echo "EV_NEG changed nothing"; exit 2; fi
rm -f "$WORK/probe"
g++ -O1 -std=c++17 -w $INC -o "$WORK/probe" "$HERE/main.cpp" $cld || { echo "probe did not build"; exit 1; }
"$WORK/probe" "$WORK/src/UnicodeTables.kt"
