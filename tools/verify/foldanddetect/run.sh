#!/usr/bin/env bash
# nativeFoldAndDetect (one JNI crossing per chunk, owner 2026-09-30) against the
# two calls it replaced: normalizeFancy() then nativeGetLanguages(folded).
#
#   tools/verify/foldanddetect/run.sh
#
# Builds the REAL core and CLD2 and checks, with ART's real-UTF-8 JNI behaviour,
# that both give the SAME folded text and the SAME spans for every text in
# short.txt and the stress corpus, and for every word of each, with the
# quick-character flag off and on. Needs g++, a JDK and the CLD2 sources.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-foldanddetect
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
FLAGS=${EV_CXXFLAGS:--O2}
mkdir -p "$WORK/obj"
compile() {
  local src=$1
  local obj="$WORK/obj/$(echo "$src $FLAGS" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ]; then
    rm -f "$obj"
    g++ -c -fPIC $FLAGS -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src" || {
      echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do objs="$objs $(compile "$f")"; done
g++ -c $FLAGS -std=c++17 -w $INC -o "$WORK/obj/equal.o" "$HERE/equal.cpp" || exit 1
g++ -o "$WORK/equal" "$WORK/obj/equal.o" $objs -L"$JH/lib/server" -ljvm -lpthread -Wl,-rpath,"$JH/lib/server"
"$WORK/equal" "$HERE/short.txt" "$ROOT/tools/verify/autottslog/stress.txt"
