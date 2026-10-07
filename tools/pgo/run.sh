#!/usr/bin/env bash
# PROFILE-GUIDED OPTIMIZATION OF THE NATIVE CORE (owner, 2026-10-07).
#
#   tools/pgo/run.sh            retrain, prove, measure; fails if the committed
#                               profile is not what training produces now
#   tools/pgo/run.sh --update   the same, and write the new profile into
#                               app/src/main/cpp/pgo/easyvoice.profdata
#
# Everything is built with the NDK's OWN clang -- the compiler that ships the
# app -- as static x86_64-android executables, which run on this Linux x86_64
# kernel as they are. The driver calls the core's real JNI entry points through
# a fake JNIEnv (fakejni.h).
#   1. instrumented build + `driver train`  ->  the profile
#   2. the profile must equal the committed one (training is deterministic)
#   3. plain build vs PGO build, both with the shipped flags: `driver dump`
#      must be byte-identical (the corebytes cross product)
#   4. `driver time`: microseconds per pass, plain vs PGO
#   5. arm64-v8a and armeabi-v7a compiled with the profile and
#      -Werror=profile-instr-out-of-date: the profile still fits the source
#      on both ABIs the app ships
#   6. the driver's training run under AddressSanitizer + UBSan (host g++,
#      host JDK's jni.h): no out-of-bounds read anywhere on that whole
#      workload. This is the step that found CLD2 reading 4 MB past a table
#      for a U+0000 (cld2Bytes in the core); `EV_CORE=<file>` runs it against
#      another copy of the core, e.g. the one from before that fix.
set -euo pipefail
UPDATE=0; [ "${1:-}" = "--update" ] && UPDATE=1
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
PROF="$CPP/pgo/easyvoice.profdata"
NDK=$(ls -d "$ROOT"/tools/.cache/android-sdk/ndk/* 2>/dev/null | sort | tail -1)
[ -n "$NDK" ] || { echo "no NDK under tools/.cache (run tools/bootstrap.sh and one gradle native build)"; exit 2; }
BIN="$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin"
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-pgo
rm -rf "$WORK"; mkdir -p "$WORK"

# The shipped cppFlags (app/build.gradle.kts) and the -DNDEBUG CMake adds.
FLAGS="-std=c++17 -Wno-narrowing -Oz -g0 -fvisibility=hidden -fvisibility-inlines-hidden
 -ffunction-sections -fdata-sections -fno-exceptions -fno-rtti -fno-unwind-tables
 -fno-asynchronous-unwind-tables -flto=thin -DNDEBUG"
QUIET="$FLAGS"; FLAGS="$FLAGS -w"   # -w would also silence the -Werror of step 5
INC="-I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
SRCS=$(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP")

# build <name> <target> <extra flags...>: objects of every library source
build() {
  local name=$1 target=$2; shift 2
  mkdir -p "$WORK/$name"
  printf '%s\n' $SRCS | xargs -P "$(nproc)" -I{} sh -c \
    '"$0" --target="$1" -c $2 "$3" -o "$4/$(basename {}).o" {} || exit 255' \
    "$BIN/clang++" "$target" "$FLAGS $INC $*" "" "$WORK/$name"
}
link() {   # link <name> <arch> <extra flags...>: the driver against that build
  local name=$1 arch=$2; shift 2
  "$BIN/clang++" --target=$arch-linux-android24 $FLAGS -I"$HERE" $* -c "$HERE/driver.cpp" -o "$WORK/$name/driver.o"
  "$BIN/clang++" --target=$arch-linux-android24 -static -static-libstdc++ -flto=thin -fuse-ld=lld $* \
    "$WORK/$name"/*.o -o "$WORK/$name/driver"
}

# The corpus: what corebytes reads (stress, the segmenter's texts, both extras).
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
  grep -v '^# ' "$ROOT/tools/verify/corebytes/extra.txt" | grep -v '^$'; } > "$CORPUS"
echo "corpus: $(wc -l < "$CORPUS") texts"

echo "== 1. train, 64-bit AND 32-bit"
# Both widths, because a function whose signature holds size_t or long has a
# different mangled name on a 32-bit ABI (j/i vs m/l), and the profile is keyed
# by name: trained on x86_64 alone, 31 of the core's functions had no record
# for armeabi-v7a. i686-android runs here as x86_64-android does.
GEN="-fprofile-instr-generate"
for t in x86_64 i686; do
  build "gen-$t" "$t-linux-android24" "$GEN"
  link "gen-$t" "$t" "$GEN"
  LLVM_PROFILE_FILE="$WORK/train-$t.profraw" "$WORK/gen-$t/driver" train "$CORPUS"
done
"$BIN/llvm-profdata" merge -o "$WORK/easyvoice.profdata" "$WORK/train-x86_64.profraw" "$WORK/train-i686.profraw"
"$BIN/llvm-profdata" show "$WORK/easyvoice.profdata" | sed -n '1,4p'

echo "== 2. committed profile"
if [ $UPDATE = 1 ]; then
  mkdir -p "$(dirname "$PROF")"; cp "$WORK/easyvoice.profdata" "$PROF"; echo "written: $PROF"
elif ! cmp -s "$WORK/easyvoice.profdata" "$PROF"; then
  echo "FAIL: the committed profile is not what training produces now -- run tools/pgo/run.sh --update"; exit 1
else echo "identical"; fi

USE="-fprofile-instr-use=$PROF"
echo "== 3. plain vs PGO, answers"
build plain x86_64-linux-android24 ""
link plain x86_64 ""
build pgo x86_64-linux-android24 "$USE"
link pgo x86_64 "$USE"
"$WORK/plain/driver" dump "$CORPUS" > "$WORK/plain.txt"
"$WORK/pgo/driver" dump "$CORPUS" > "$WORK/pgo.txt"
if cmp -s "$WORK/plain.txt" "$WORK/pgo.txt"; then echo "IDENTICAL: $(wc -l < "$WORK/plain.txt") answers"
else echo "FAIL: answers differ"; diff "$WORK/plain.txt" "$WORK/pgo.txt" | head; exit 1; fi

echo "== 4. time (us per pass over the corpus, median of 25; cold = CLD2 cache emptied, warm = repeated)"
for r in 1 2 3; do
  p=$("$WORK/plain/driver" time "$CORPUS"); g=$("$WORK/pgo/driver" time "$CORPUS")
  echo "  plain cold/warm $p    pgo cold/warm $g"
done

echo "== 5. the shipped ABIs accept the profile"
FLAGS="$QUIET"
for t in aarch64-linux-android24 armv7a-linux-androideabi24; do
  build "abi-$t" "$t" "$USE -Wprofile-instr-out-of-date -Werror=profile-instr-out-of-date -Wprofile-instr-missing -Werror=profile-instr-missing"
  echo "  $t: compiles with no out-of-date function"
done
echo "== 6. AddressSanitizer + UBSan over the training run"
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
mkdir -p "$WORK/asan"
CORE=${EV_CORE:-$CPP/tts_engine_core.cpp}
for f in $SRCS; do
  case "$f" in */tts_engine_core.cpp) f=$CORE ;; esac
  echo "$f"
done | xargs -P "$(nproc)" -I{} sh -c 'g++ -c -O1 -g -fsanitize=address,undefined -fno-sanitize=shift-base -fno-sanitize-recover=all -std=c++17 -w -Wno-narrowing -I"$0/include" -I"$0/include/linux" $1 {} -o "$2/$(basename {}).o" || exit 255' "$JH" "$INC" "$WORK/asan"
g++ -c -O1 -g -fsanitize=address,undefined -std=c++17 -w -I"$JH/include" -I"$JH/include/linux" -I"$HERE" "$HERE/driver.cpp" -o "$WORK/asan/driver.o"
g++ -fsanitize=address,undefined "$WORK/asan"/*.o -o "$WORK/asan/driver"
if ASAN_OPTIONS=detect_leaks=0 "$WORK/asan/driver" train "$CORPUS" 2> "$WORK/asan.log"; then echo "  clean"
else echo "FAIL: sanitizer report:"; grep -E "ERROR|SUMMARY|#[0-4] " "$WORK/asan.log" | head -12; exit 1; fi
echo "PGO: ALL PASS"
