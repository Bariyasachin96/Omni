#!/usr/bin/env bash
# The native core of a commit vs the working tree, byte for byte.
#   run.sh [ref] [--mask-kind]
# ref defaults to HEAD. Every answer the Kotlin side receives -- processDirect
# for mix (with detection) and dual over 8 enabled sets x 8 mode triples x
# quick x smart numbers off/1/2/3 x punctuation in flow x neutral type, plus
# detectWindowOrSpans and nativeFoldAndDetect -- must be the same string array.
# --mask-kind ignores field 1 of each processDirect record (the "kind" the
# Kotlin never reads; it stopped being computed on 2026-10-01) and counts how
# often it differed instead.
set -euo pipefail
REF=HEAD; MASK=0
for a in "$@"; do case "$a" in --mask-kind) MASK=1 ;; *) REF=$a ;; esac; done
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-corebytes
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
mkdir -p "$WORK/obj" "$WORK/old"
git -C "$ROOT" show "$REF:app/src/main/cpp/tts_engine_core.cpp" > "$WORK/old/tts_engine_core.cpp"
INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
compile() {
  local src=$1
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ]; then
    rm -f "$obj"
    g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src" || { echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
cld=""; core=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  case "$f" in */tts_engine_core.cpp) core="$f" ;; *) cld="$cld $(compile "$f")" ;; esac
done
# the old core is always rebuilt: the same path holds whatever ref was asked for
rm -f "$WORK/obj/$(echo "$WORK/old/tts_engine_core.cpp" | md5sum | cut -c1-16).o"
newcore=$(compile "$core"); oldcore=$(compile "$WORK/old/tts_engine_core.cpp")
g++ -c -O2 -std=c++17 -w $INC -o "$WORK/obj/probe.o" "$HERE/main.cpp" || exit 1
LINK="-L$JH/lib/server -ljvm -lpthread -Wl,-rpath,$JH/lib/server"
g++ -o "$WORK/probe-old" "$WORK/obj/probe.o" "$oldcore" $cld $LINK
g++ -o "$WORK/probe-new" "$WORK/obj/probe.o" "$newcore" $cld $LINK

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
  grep -v '^# ' "$ROOT/tools/verify/oldnew/extra.txt" | grep -v '^$'
  grep -v '^# ' "$HERE/extra.txt" | grep -v '^$'; } > "$CORPUS"

"$WORK/probe-old" "$CORPUS" > "$WORK/old.txt"
"$WORK/probe-new" "$CORPUS" > "$WORK/new.txt"
python3 "$HERE/compare.py" "$WORK/old.txt" "$WORK/new.txt" "$MASK"
