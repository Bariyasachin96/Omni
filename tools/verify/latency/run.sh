#!/usr/bin/env bash
# How long the app spends BEFORE the first word can be spoken.
#
#   tools/verify/latency/run.sh
#
# WHY IT EXISTS. The owner reported two things a device log could not answer:
# language switching that feels slow with Gujarati/Hindi/Marathi in one message,
# and a long wait before a 25-to-30 paragraph text starts. Every log sent so far
# tops out at a 404-character utterance, where start-to-first-speak is 8-12 ms
# (median over 122 utterances across three logs), so the big-text case was pure
# speculation until this measured it.
#
# WHAT IT MEASURES. The work that must finish before speakChunk(true) runs:
# buildMixChunks through processDirect, then one detection per chunk, which is
# what the mix and multilingual branches do. It links the REAL
# tts_engine_core.cpp with CLD2 and CLD3 and starts a JVM for a genuine JNIEnv,
# so the numbers are the device's code, not a model of it.
#
# WHAT IT FOUND (2026-09-01, x86-64 container):
#
#   paragraphs  chars  chunks  segment ms  detect ms  TOTAL ms
#   10           1758      29         0.2        1.0       1.2
#   30           5310      81         0.5        1.5       2.0
#   60          10620     161         0.6        2.6       3.3
#
# Totals move by a few tenths between runs. That is the finding, not a caveat:
# the whole measurement is smaller than the noise in anything a person can hear.
#
# So segmentation and detection are NOT the delay, even allowing an order of
# magnitude for a phone. Anything that looks like a chunking cost should be
# measured here first.
#
# Needs: g++, javac (for libjvm and jni.h), and the CLD2/CLD3 sources CI clones.
set -euo pipefail

HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-latency
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}

missing=0
for d in "$CPP/cld2_src/internal" "$CPP/cld3_src/src" "$CPP/cld3_gen"; do
  [ -d "$d" ] || { echo "missing: ${d#$ROOT/}"; missing=1; }
done
if [ "$missing" = 1 ]; then
  cat <<EOF

The CLD2/CLD3 sources are not present. They are cloned by CI, not checked in.
From the repository root:

  git clone --depth 1 https://github.com/CLD2Owners/cld2.git app/src/main/cpp/cld2_src
  git clone --depth 1 https://github.com/google/cld3.git     app/src/main/cpp/cld3_src
  mkdir -p app/src/main/cpp/cld3_gen/cld_3/protos
  protoc -Iapp/src/main/cpp/cld3_src/src \\
         --cpp_out=app/src/main/cpp/cld3_gen/cld_3/protos \\
         app/src/main/cpp/cld3_src/src/*.proto
EOF
  exit 2
fi
if ! ls "$JH"/lib/server/libjvm.so >/dev/null 2>&1; then
  echo "no libjvm.so under $JH -- a JDK is needed for the JNIEnv"; exit 2
fi

INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal
     -I$CPP/cld3_src/src -I$CPP/cld3_src -I$CPP/cld3_gen -I$CPP"

mkdir -p "$WORK/obj"
compile() {  # compile <source> -- cached on mtime
  local src=$1
  local obj="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$obj" ] || [ "$src" -nt "$obj" ]; then
    g++ -c -O1 -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src"
  fi
  echo "$obj"
}

echo "1/3  compiling CLD2, CLD3 and the native core (cached in $WORK)"
# The source list comes from CMakeLists.txt, not a glob -- see sources.py for
# why a glob cannot work here.
sources=$(python3 "$HERE/sources.py" "$CPP")
objs=""
for f in $sources; do
  [ -f "$f" ] || { echo "CMakeLists names a file that is not here: $f"; exit 2; }
  objs="$objs $(compile "$f")"
done

echo "2/3  linking the harness"
g++ -c -O1 -std=c++17 -w $INC -o "$WORK/obj/main.o" "$HERE/main.cpp"
g++ -o "$WORK/latency" "$WORK/obj/main.o" $objs \
    -L"$JH/lib/server" -ljvm -lprotobuf-lite -lpthread -Wl,-rpath,"$JH/lib/server"

echo "3/3  running"
echo
"$WORK/latency"
