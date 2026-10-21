#!/usr/bin/env bash
# The detector's tables are read in at library load (image_warm.h), not on the
# first word (owner, 2026-10-07). This builds the REAL core as a shared
# library, the way the app ships it, and for each round:
#   1. drops the library's file pages from the page cache (posix_fadvise
#      DONTNEED -- what a reboot, an update or memory pressure leaves behind);
#   2. dlopens it and, in the "advised" rounds, calls the real JNI_OnLoad (with
#      no VM: it then does only the advice);
#   3. reports how much of the read-only image is resident, and times the first
#      read of every page -- the cost the first detection pays.
# Waiting for the readahead to land is the HARNESS's doing, so the effect can
# be measured; the app never waits for it.
#   run.sh            EV_NEG=noadvise|nopopulate|norewarm breaks one step on purpose (must FAIL)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-warmtables${EV_NEG:+-$EV_NEG}
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
mkdir -p "$WORK/obj" "$WORK/src"
cp "$CPP"/*.cpp "$CPP"/*.h "$WORK/src/"
case "${EV_NEG:-}" in
  "") ;;
  noadvise)   sed -i 's/MADV_WILLNEED) == 0/MADV_NORMAL) == 0/; s/MADV_POPULATE_READ) != 0/MADV_NORMAL) != 0/' "$WORK/src/image_warm.h" ;;
  nopopulate) sed -i 's/MADV_POPULATE_READ) != 0/MADV_NORMAL) != 0/' "$WORK/src/image_warm.h" ;;
  norewarm)   sed -i 's/    g_rewarmWanted = true;/    g_rewarmWanted = false;/' "$WORK/src/image_warm.h" ;;
  *) echo "unknown EV_NEG"; exit 2 ;;
esac
INC="-I$JH/include -I$JH/include/linux -I$WORK/src"
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  case "$f" in */tts_engine_core.cpp) f="$WORK/src/tts_engine_core.cpp" ;; esac
  o="$WORK/obj/$(echo "$f" | md5sum | cut -c1-16).o"
  if [ ! -f "$o" ] || [ "$f" -nt "$o" ] || [ -n "$(find "$WORK/src" -maxdepth 1 -name "*.h" -newer "$o" -print -quit)" ]; then
    rm -f "$o"; g++ -c -O2 -fPIC -std=c++17 -w $INC -o "$o" "$f" || { echo "compile failed: $f"; exit 1; }
  fi
  objs="$objs $o"
done
g++ -shared -o "$WORK/libeasyvoice.so" $objs -lpthread
g++ -O2 -std=c++17 -o "$WORK/driver" "$HERE/driver.cpp" -ldl -lpthread
for i in 1 2 3 4 5; do
  "$WORK/driver" "$WORK/libeasyvoice.so" cold
  "$WORK/driver" "$WORK/libeasyvoice.so" advised
done > "$WORK/rounds.txt"
awk '{ printf "  %-8s resident before %5d / %d pages, at first read %5d; first read of every page %.2f ms, %d minor faults\n", $1, $2, $4, $3, $5, $6 }' "$WORK/rounds.txt"
awk '
  $1 == "cold"    { cn++; cr += $3; cm += $5; cf += $6; t = $4 }
  $1 == "advised" { an++; ar += $3; am += $5; af += $6 }
  END {
    printf "average: cold %.2f ms, %d minor faults (%d pages resident at the first read); advised %.2f ms, %d minor faults (%d) of %d pages\n", cm/cn, cf/cn, cr/cn, am/an, af/an, ar/an, t
    if (!(ar/an >= t*0.9 && cr/cn < t*0.5)) { print "FAIL: the tables were not read in by the advice"; exit 1 }
    if (!(af/an * 4 <= cf/cn)) { print "FAIL: the page tables were not filled (MADV_POPULATE_READ)"; exit 1 }
    print "PASS: the tables are read in, and mapped, before the first detection"
  }' "$WORK/rounds.txt"
for i in 1 2 3; do "$WORK/driver" "$WORK/libeasyvoice.so" rewarm; done > "$WORK/rewarm.txt"
awk '{ printf "  rewarm: after eviction %d of %d pages resident, after an utterance start %d\n", $2, $4, $3 }
  { n++; ev += $2; back += $3; t = $4 }
  END {
    if (ev/n > t*0.5) { print "FAIL: the harness could not evict the tables"; exit 1 }
    if (back/n < t*0.9) { print "FAIL: an utterance start did not read the dropped tables back"; exit 1 }
    print "PASS: tables the kernel dropped are read back at the next utterance start"
  }' "$WORK/rewarm.txt"
