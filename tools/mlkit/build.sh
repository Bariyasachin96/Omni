#!/usr/bin/env bash
# Google's ML Kit language identification on this desktop, for the harnesses
# (host.c): SOURCE this file from a harness's run.sh --
#
#   source "$ROOT/tools/mlkit/build.sh"
#   g++ ... main.cpp "$MLKIT_HOST" -ldl -lm -lpthread
#   LD_LIBRARY_PATH="$MLKIT_LIBS" EV_MLKIT_MODEL="$MLKIT_MODEL" ./harness
#
# and the harness hands evmlkit_identify to the core's evSetLanguageIdentifier
# (see tools/mlkit/mlkit.h). Fetches com.google.mlkit:language-id 17.0.6 from
# Google's Maven repository into tools/.cache (gitignored, sha256-pinned) and
# builds the bionic shim ML Kit's x86_64 library imports. Needs gcc, a JDK's
# jni.h and an x86_64 host.
_ML_ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
_ML_HERE="$_ML_ROOT/tools/mlkit"
_ML_CACHE="$_ML_ROOT/tools/.cache/mlkit-language-id-17.0.6"
MLKIT_LIBS=${EV_WORK:-${TMPDIR:-/tmp}}/ev-mlkit
_ML_AAR_SHA=3610374edfab8537c795195d53645c53923afa1acbe80ef25f5fc8551076a8f7
if [ ! -f "$_ML_CACHE/lib/liblanguage_id_l2c_jni.so" ] || [ ! -f "$_ML_CACHE/tflite_langid.tflite.jpg" ]; then
  mkdir -p "$_ML_CACHE/lib"
  curl -sSfL -o "$_ML_CACHE/language-id.aar" \
    https://dl.google.com/android/maven2/com/google/mlkit/language-id/17.0.6/language-id-17.0.6.aar
  echo "$_ML_AAR_SHA  $_ML_CACHE/language-id.aar" | sha256sum -c --quiet
  unzip -q -o -j "$_ML_CACHE/language-id.aar" jni/x86_64/liblanguage_id_l2c_jni.so -d "$_ML_CACHE/lib"
  unzip -q -o -j "$_ML_CACHE/language-id.aar" assets/tflite_langid.tflite.jpg -d "$_ML_CACHE"
fi
MLKIT_MODEL="$_ML_CACHE/tflite_langid.tflite.jpg"
_ML_JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
mkdir -p "$MLKIT_LIBS"
cp "$_ML_CACHE/lib/liblanguage_id_l2c_jni.so" "$MLKIT_LIBS/"
echo 'LIBC { global: *; };' > "$MLKIT_LIBS/libc.map"
echo 'int bionic_version_anchor;' > "$MLKIT_LIBS/anchor.c"
gcc -O2 -shared -fPIC -Wl,-soname,libc.so -Wl,--version-script="$MLKIT_LIBS/libc.map" -o "$MLKIT_LIBS/libc.so" "$_ML_HERE/bionic.c" -lm
gcc -O2 -shared -fPIC -Wl,-soname,libm.so -Wl,--version-script="$MLKIT_LIBS/libc.map" -o "$MLKIT_LIBS/libm.so" "$MLKIT_LIBS/anchor.c"
gcc -O2 -shared -fPIC -Wl,-soname,libdl.so -Wl,--version-script="$MLKIT_LIBS/libc.map" -o "$MLKIT_LIBS/libdl.so" "$MLKIT_LIBS/anchor.c"
gcc -O2 -shared -fPIC -Wl,-soname,liblog.so -o "$MLKIT_LIBS/liblog.so" "$_ML_HERE/log.c"
gcc -O2 -shared -fPIC -Wl,-soname,libandroid.so -o "$MLKIT_LIBS/libandroid.so" "$MLKIT_LIBS/anchor.c"
python3 -c "
print('// one trap per JNI function slot, naming it (tools/mlkit/build.sh)')
for i in range(240): print('static void trap%d(void) { trapped(%d); }' % (i, i))
print('static void (*const kTraps[240])(void) = {' + ','.join('trap%d' % i for i in range(240)) + '};')
" > "$MLKIT_LIBS/traps.h"
MLKIT_HOST="$MLKIT_LIBS/host.o"
gcc -O2 -Wall -c -I"$MLKIT_LIBS" -I"$_ML_JH/include" -I"$_ML_JH/include/linux" -o "$MLKIT_HOST" "$_ML_HERE/host.c"
export MLKIT_LIBS MLKIT_MODEL MLKIT_HOST
