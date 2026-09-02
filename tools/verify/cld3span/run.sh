#!/usr/bin/env bash
# Prove the CLD3 arm of the span site routes a Latin span to a Latin voice.
#
#   tools/verify/cld3span/run.sh
#
# WHAT IS PROVED, and why this harness is not like the other three. The
# segmenter, script-family and normaliser proofs compare our C++ against
# AutoTTS's own Java. There is nothing to compare here: AutoTTS has no CLD3, so
# the standing rule for that switch is not "match AutoTTS" but "wherever CLD2
# makes a detection, the switch must be able to put CLD3 there instead". So this
# asserts the two detectors agree with each other on the same text, which is
# what that rule means at the span site.
#
# It links the REAL app/src/main/cpp/tts_engine_core.cpp -- not a slice --
# together with CLD2, CLD3 and protobuf, starts a JVM for a genuine JNIEnv, and
# calls setLanguageHints / setDetectSets / nativeGetLanguages exactly as the
# Kotlin does. A slice would have missed this bug entirely: it lived in the
# interaction between cld3DetectRaw's reliability flag and emitScriptSpan's
# per-script fallback, and neither is reachable without the detector libraries.
#
# THE BUG IT EXISTS FOR (device log, 2026-08-27). With CLD3 on, "MEET Choudhary"
# was spoken by the Hindi voice; with CLD2 it was read in English. Chunking was
# identical, only the first span's language differed. CLD3 answers "hi" for that
# text with is_reliable = 0 and p = 0.495; its own top-3 loop rejects the
# candidate for exactly that reason, then the fallthrough returned it anyway,
# because the span site passed nullptr for reliableOut. Both arms of
# detectWindowLang already answer UNKNOWN when unsure -- this site did not.
#
# Needs: g++, javac (for libjvm and jni.h), and the CLD2/CLD3 sources CI clones.
# First run compiles about fifty translation units and takes a few minutes; the
# object files are cached in the work directory, so later runs are quick.
set -euo pipefail

HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-cld3span
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
    # Delete first, and fail the whole run on a compile error. Without both,
    # this function ended in a successful `echo` and so returned 0 however g++
    # fared, and the link then picked up the object from the LAST GOOD build --
    # so a broken core printed its errors and the harness still reported every
    # case passing. That happened on 2026-09-02 and cost a full debugging cycle.
    rm -f "$obj"
    g++ -c -O1 -std=c++17 -w -Wno-narrowing $INC -o "$obj" "$src" || {
      echo "compile failed: $src" >&2; exit 1; }
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
g++ -o "$WORK/cld3span" "$WORK/obj/main.o" $objs \
    -L"$JH/lib/server" -ljvm -lprotobuf-lite -lpthread -Wl,-rpath,"$JH/lib/server"

echo "3/3  running"
echo
"$WORK/cld3span"
