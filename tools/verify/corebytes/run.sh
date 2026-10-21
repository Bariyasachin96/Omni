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
# the ref's core WITH its own headers (langblend.h, langnb.h, unicode_tables.h
# ...): the old core includes them from its own directory first, so a change
# made in a header alone is measured too (2026-10-21; before, the old core was
# built against the working tree's headers and such a change showed 0 differ).
# A ref from before 2026-10-21 links CLD2: sources.py points it at the clone
# tools/verify/clone-cld2.sh keeps in tools/.cache/cld2; the working tree links
# nothing but its own core.
rm -rf "$WORK/old"; mkdir -p "$WORK/obj" "$WORK/old"
git -C "$ROOT" archive "$REF" app/src/main/cpp | tar -x -C "$WORK/old" --strip-components=4
SRC="$ROOT/tools/verify/latency/sources.py"
OLDINC=$(python3 "$SRC" --includes "$WORK/old") || exit 2
NEWINC=$(python3 "$SRC" --includes "$CPP") || exit 2
INC="-I$JH/include -I$JH/include/linux -I$CPP"
compile() {   # compile <source> <extra -I flags>
  local src=$1 extra=$2
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ] || [ -n "$(find "$(dirname "$src")" -maxdepth 1 -name "*.h" -newer "$obj" -print -quit)" ]; then
    rm -f "$obj"
    g++ -c ${EV_CXXFLAGS:--O2} -std=c++17 -w -Wno-narrowing $INC $extra -o "$obj" "$src" || { echo "compile failed: $src" >&2; exit 1; }
  fi
  echo "$obj"
}
# the old core is always rebuilt: the same path holds whatever ref was asked for
rm -f "$WORK/obj/$(echo "$WORK/old/tts_engine_core.cpp" | md5sum | cut -c1-16).o"
oldobjs=""; newobjs=""
for f in $(python3 "$SRC" "$WORK/old"); do oldobjs="$oldobjs $(compile "$f" "$OLDINC")"; done
for f in $(python3 "$SRC" "$CPP"); do newobjs="$newobjs $(compile "$f" "$NEWINC")"; done
g++ -c -O2 -std=c++17 -w $INC -o "$WORK/obj/probe.o" "$HERE/main.cpp" || exit 1
LINK="-L$JH/lib/server -ljvm -lpthread -Wl,-rpath,$JH/lib/server"
g++ -o "$WORK/probe-old" "$WORK/obj/probe.o" $oldobjs $LINK
g++ -o "$WORK/probe-new" "$WORK/obj/probe.o" $newobjs $LINK

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
