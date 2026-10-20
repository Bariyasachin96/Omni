#!/usr/bin/env bash
# The language identifier (app/src/main/cpp/langid.h) against Google's own code.
#
#   tools/verify/langid/run.sh
#
#  1. ids    MediaPipe's C++ (tools/langid/mediapipe/: rune tables, UTF-8
#            decoding, lowercasing, tokenizing, Murmur) compiled beside
#            langid.h, bit for bit -- see ids.cpp.
#  2. probs  all 111 probabilities against ML Kit's OWN library -- the x86_64
#            build of liblanguage_id_l2c_jni.so from com.google.mlkit:
#            language-id 17.0.6, with the model it bundles, run on glibc
#            through mlkit/ (a shim of the bionic libc it imports, and a fake
#            JNI) -- over the UDHR in 388 languages, short prefixes of it,
#            extra.txt and random text. See compare.py for what must agree.
#
# Fetches into tools/.cache (gitignored), sha256-pinned: NLTK's udhr2.zip and
# the ML Kit AAR. Needs g++, gcc, python3, a JDK's jni.h, and an x86_64 host.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
UP="$ROOT/tools/langid/mediapipe"
CACHE="$ROOT/tools/.cache"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-langid
mkdir -p "$WORK" "$CACHE"

python3 "$ROOT/tools/langid/gen_model.py" --check

UDHR_SHA=0796c314b09a930c989c6f9d93d226af9af13feccd88496e196c743dd266c7f3
if [ ! -d "$CACHE/udhr2" ]; then
  curl -sSfL -o "$CACHE/udhr2.zip" https://raw.githubusercontent.com/nltk/nltk_data/gh-pages/packages/corpora/udhr2.zip
  echo "$UDHR_SHA  $CACHE/udhr2.zip" | sha256sum -c --quiet
  (cd "$CACHE" && unzip -q -o udhr2.zip)
fi

# The four headers upstream's sources include but the op does not ship with.
INC="$WORK/inc"
D="$INC/mediapipe/tasks/cc/text/language_detector/custom_ops/utils"
mkdir -p "$D/utf" "$D/hash" "$INC/absl/base" "$INC/mediapipe/util"
cp "$UP/utf.h" "$UP/runetypebody.h" "$D/utf/"
cat > "$D/ngram_hash_ops_utils.h" <<'H'
#pragma once
#include <string>
#include <utility>
#include <vector>
namespace mediapipe::tasks::text::language_detector::custom_ops {
struct TokenizedOutput { std::string str; std::vector<std::pair<size_t, size_t>> tokens; };
TokenizedOutput Tokenize(const char* input_str, int len, int max_tokens, bool exclude_nonalphaspace_tokens);
void LowercaseUnicodeStr(const char* input_str, int len, std::string* output_str);
}
H
cat > "$D/hash/murmur.h" <<'H'
#pragma once
#include <cstddef>
#include <cstdint>
namespace mediapipe::tasks::text::language_detector::custom_ops::hash {
unsigned long long MurmurHash64WithSeed(const char* buf, const size_t len, const uint64_t seed);
}
H
echo '#define ABSL_ASSUME(cond) ((void)0)' > "$INC/absl/base/optimization.h"
cat > "$INC/mediapipe/util/endian.h" <<'H'
#pragma once
#include <cstdint>
#include <cstring>
namespace mediapipe::little_endian {   // x86-64 and arm64: little-endian already
inline uint64_t Load64(const void* p) { uint64_t v; std::memcpy(&v, p, 8); return v; }
}
H
sed 's|#include "mediapipe/tasks/cc/text/language_detector/custom_ops/utils/utf/runetypebody.h"|#include "runetypebody.h"|' \
  "$UP/runetype.c" > "$WORK/runetype.c"
gcc -O2 -c -I"$INC" -I"$D/utf" -o "$WORK/rune.o" "$UP/rune.c"
gcc -O2 -c -I"$INC" -I"$D/utf" -o "$WORK/runetype.o" "$WORK/runetype.c"
g++ -O2 -std=c++17 -c -I"$INC" -o "$WORK/utils.o" "$UP/ngram_hash_ops_utils.cc"
g++ -O2 -std=c++17 -c -I"$INC" -o "$WORK/murmur.o" "$UP/murmur.cc"
g++ -O2 -std=c++17 -Wall -Wextra -I"$INC" -I"$CPP" -o "$WORK/ids" "$HERE/ids.cpp" \
  "$WORK/rune.o" "$WORK/runetype.o" "$WORK/utils.o" "$WORK/murmur.o"
echo "── 1. ids against MediaPipe's C++"
"$WORK/ids" "$CACHE"/udhr2/*.txt "$HERE/extra.txt"

echo "── 2. probabilities against ML Kit's own library"
python3 "$HERE/corpus.py" "$CACHE/udhr2" "$HERE/extra.txt" "$WORK/corpus.txt"
g++ -O2 -std=c++17 -Wall -Wextra -I"$CPP" -o "$WORK/probs" "$HERE/probs.cpp"
"$WORK/probs" "$WORK/corpus.txt" "$WORK/ours.f32" "$WORK/labels.txt" "$WORK/shrunk.u8"

# com.google.mlkit:language-id 17.0.6 from Google's Maven repository: its x86_64
# library and the model it bundles, run on glibc through mlkit/.
MLKIT="$CACHE/mlkit-language-id-17.0.6"
AAR_SHA=3610374edfab8537c795195d53645c53923afa1acbe80ef25f5fc8551076a8f7
if [ ! -f "$MLKIT/lib/liblanguage_id_l2c_jni.so" ]; then
  mkdir -p "$MLKIT/lib"
  curl -sSfL -o "$MLKIT/language-id.aar" \
    https://dl.google.com/android/maven2/com/google/mlkit/language-id/17.0.6/language-id-17.0.6.aar
  echo "$AAR_SHA  $MLKIT/language-id.aar" | sha256sum -c --quiet
  unzip -q -o -j "$MLKIT/language-id.aar" jni/x86_64/liblanguage_id_l2c_jni.so -d "$MLKIT/lib"
  unzip -q -o -j "$MLKIT/language-id.aar" assets/tflite_langid.tflite.jpg -d "$MLKIT"
fi
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
SHIM="$WORK/mlkit"
mkdir -p "$SHIM"
cp "$MLKIT/lib/liblanguage_id_l2c_jni.so" "$SHIM/"
echo 'LIBC { global: *; };' > "$SHIM/libc.map"
echo 'int bionic_version_anchor;' > "$SHIM/anchor.c"
gcc -O2 -shared -fPIC -Wl,-soname,libc.so -Wl,--version-script="$SHIM/libc.map" -o "$SHIM/libc.so" "$HERE/mlkit/bionic.c" -lm
gcc -O2 -shared -fPIC -Wl,-soname,libm.so -Wl,--version-script="$SHIM/libc.map" -o "$SHIM/libm.so" "$SHIM/anchor.c"
gcc -O2 -shared -fPIC -Wl,-soname,libdl.so -Wl,--version-script="$SHIM/libc.map" -o "$SHIM/libdl.so" "$SHIM/anchor.c"
gcc -O2 -shared -fPIC -Wl,-soname,liblog.so -o "$SHIM/liblog.so" "$HERE/mlkit/log.c"
gcc -O2 -shared -fPIC -Wl,-soname,libandroid.so -o "$SHIM/libandroid.so" "$SHIM/anchor.c"
python3 -c "
print('// one trap per JNI function slot, naming it (run.sh)')
for i in range(240): print('static void trap%d(void) { trapped(%d); }' % (i, i))
print('static void (*const kTraps[240])(void) = {' + ','.join('trap%d' % i for i in range(240)) + '};')
" > "$SHIM/traps.h"
gcc -O2 -Wall -I"$SHIM" -I"$JH/include" -I"$JH/include/linux" -o "$SHIM/runner" "$HERE/mlkit/runner.c" -ldl
LD_LIBRARY_PATH="$SHIM" "$SHIM/runner" "$MLKIT/tflite_langid.tflite.jpg" "$WORK/corpus.txt" "$WORK/mlkit.f32" "$WORK/labels.txt" \
  2> >(grep -v "Initialized TensorFlow Lite runtime" >&2)
python3 "$HERE/compare.py" "$WORK/corpus.txt" "$WORK/ours.f32" "$WORK/mlkit.f32" "$WORK/labels.txt" "$WORK/shrunk.u8"
