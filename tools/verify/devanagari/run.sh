#!/usr/bin/env bash
# Which language a SHARED-SCRIPT span really gets -- asked of the real detector.
# See main.cpp for why this exists. Needs g++, a JDK, and the CLD2 clone.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-devanagari
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
ls "$JH"/lib/server/libjvm.so >/dev/null 2>&1 || { echo "no libjvm.so under $JH"; exit 2; }

INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
mkdir -p "$WORK/obj"
compile() {
  # TWO `local` lines, not one. `local src=$1 obj="...$src..."` expands $src
  # BEFORE the first assignment lands, so under `set -u` every compile died with
  # "src: unbound variable" -- and the run still linked, because the empty name
  # hashed to one shared object file. Silent-ish, exactly the shape the stale
  # -binary trap of 2026-09-02 had.
  local src=$1
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ]; then
    rm -f "$obj"
    g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src" || {
      echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do objs="$objs $(compile "$f")"; done
g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w $INC -o "$WORK/obj/main.o" "$HERE/main.cpp" || exit 1
g++ -o "$WORK/probe" "$WORK/obj/main.o" $objs -L"$JH/lib/server" -ljvm -lpthread -Wl,-rpath,"$JH/lib/server"
"$WORK/probe"
