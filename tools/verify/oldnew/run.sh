#!/usr/bin/env bash
# Old core vs new core, through the real JNI entry points and the real Kotlin
# of each version (owner, 2026-10-01). See main.cpp and gen.py.
#   run.sh [old-ref]     old-ref defaults to ae274b9, the commit before
#                        processDirect took the String and answered an array
#                        and auto/Google mode went to one crossing.
set -euo pipefail
REF=${1:-ae274b9}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-oldnew
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
[ -x "$KOTLINC" ] || { echo "no kotlinc at $KOTLINC (tools/bootstrap.sh)"; exit 2; }
mkdir -p "$WORK/obj" "$WORK/old"
git -C "$ROOT" show "$REF:app/src/main/cpp/tts_engine_core.cpp" > "$WORK/old/tts_engine_core.cpp"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" > "$WORK/old/EasyVoiceTtsService.kt"
git -C "$ROOT" show "$REF:app/src/main/cpp/CMakeLists.txt" > "$WORK/old/CMakeLists.txt"
# the old ref links CLD2 (CLD2 left the app on 2026-10-21): sources.py points
# its files at the clone tools/verify/clone-cld2.sh keeps in tools/.cache/cld2
SRC="$ROOT/tools/verify/latency/sources.py"
OLDINC=$(python3 "$SRC" --includes "$WORK/old") || exit 2
INC="-I$JH/include -I$JH/include/linux -I$CPP"
compile() {   # compile <source> [extra -I flags]
  local src=$1 extra=${2:-}
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ] || [ -n "$(find "$(dirname "$src")" -maxdepth 1 -name "*.h" -newer "$obj" -print -quit)" ]; then
    rm -f "$obj"
    g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w -Wno-narrowing $INC $extra -o "$obj" "$src" || { echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
oldobjs=""; newobjs=""
for f in $(python3 "$SRC" "$WORK/old"); do oldobjs="$oldobjs $(compile "$f" "$OLDINC")"; done
for f in $(python3 "$SRC" "$CPP"); do newobjs="$newobjs $(compile "$f")"; done
rm -f "$WORK/obj/main-old.o" "$WORK/obj/main-new.o"
g++ -c -O2 -std=c++17 -w -DOLD $INC -o "$WORK/obj/main-old.o" "$HERE/main.cpp" || exit 1
g++ -c -O2 -std=c++17 -w $INC -o "$WORK/obj/main-new.o" "$HERE/main.cpp" || exit 1
LINK="-L$JH/lib/server -ljvm -lpthread -Wl,-rpath,$JH/lib/server"
g++ -o "$WORK/probe-old" "$WORK/obj/main-old.o" $oldobjs $LINK
g++ -o "$WORK/probe-new" "$WORK/obj/main-new.o" $newobjs $LINK

CORPUS="$WORK/corpus.txt"
{ grep -v '^# ' "$ROOT/tools/verify/autottslog/stress.txt" | grep -v '^$'
  python3 - "$ROOT/tools/verify/segmenter/gen_cases.py" <<'PY'
import sys
src = open(sys.argv[1], encoding="utf-8").read()
g = {}
exec(src[:src.index("\nout=[]")], g)
for t in g["TEXTS"]:
    sys.stdout.write(t.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n") + "\n")
PY
  grep -v '^# ' "$HERE/extra.txt" | grep -v '^$'; } > "$CORPUS"

"$WORK/probe-old" "$CORPUS" > "$WORK/old.txt"
"$WORK/probe-new" "$CORPUS" > "$WORK/new.txt"
python3 "$HERE/gen.py" "$WORK/old/EasyVoiceTtsService.kt" \
  "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" "$WORK/Check.kt" "$HERE/Driver.kt"
rm -f "$WORK/check.jar"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "Check.kt did not compile"; exit 1; }
java -Dstdout.encoding=UTF-8 -jar "$WORK/check.jar" "$WORK/old.txt" "$WORK/new.txt"
