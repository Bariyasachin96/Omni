#!/usr/bin/env bash
# Which language a SHARED-SCRIPT span really gets -- asked of the real detector.
# See main.cpp for why this exists. Needs g++ and a JDK.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-devanagari
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
ls "$JH"/lib/server/libjvm.so >/dev/null 2>&1 || { echo "no libjvm.so under $JH"; exit 2; }

INC="-I$JH/include -I$JH/include/linux -I$ROOT/tools/mlkit -I$CPP"
source "$ROOT/tools/mlkit/build.sh"
mkdir -p "$WORK/obj"
compile() {
  # TWO `local` lines, not one. `local src=$1 obj="...$src..."` expands $src
  # BEFORE the first assignment lands, so under `set -u` every compile died with
  # "src: unbound variable" -- and the run still linked, because the empty name
  # hashed to one shared object file. Silent-ish, exactly the shape the stale
  # -binary trap of 2026-09-02 had.
  local src=$1
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ] || [ -n "$(find "$(dirname "$src")" -maxdepth 1 -name "*.h" -newer "$obj" -print -quit)" ]; then
    rm -f "$obj"
    g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src" || {
      echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do objs="$objs $(compile "$f")"; done
g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w $INC -o "$WORK/obj/main.o" "$HERE/main.cpp" || exit 1
g++ -o "$WORK/probe" "$WORK/obj/main.o" $objs "$MLKIT_HOST" -ldl -lm -L"$JH/lib/server" -ljvm -lpthread -Wl,-rpath,"$JH/lib/server"
LD_LIBRARY_PATH="$MLKIT_LIBS" EV_MLKIT_MODEL="$MLKIT_MODEL" "$WORK/probe" 2> >(grep -v "Initialized TensorFlow Lite runtime" >&2)
