#!/usr/bin/env bash
# Every libeasyvoice.so in an APK must export ONLY the JNI entry points the VM
# resolves by name (Java_*) and JNI_OnLoad. Anything else is a symbol the
# linker had to keep and --gc-sections could not drop -- build 945 exported 698,
# almost all static libc++ (see app/src/main/cpp/exports.map). Needs a built APK.
#   tools/check/native-exports.sh <app.apk>
set -euo pipefail
apk=${1:?usage: native-exports.sh <apk>}
NM=$(command -v llvm-nm || ls "${ANDROID_HOME:-tools/.cache/android-sdk}"/ndk/*/toolchains/llvm/prebuilt/*/bin/llvm-nm tools/.cache/android-sdk/ndk/*/toolchains/llvm/prebuilt/*/bin/llvm-nm 2>/dev/null | head -1)
work=$(mktemp -d); trap 'rm -rf "$work"' EXIT
unzip -q -o "$apk" 'lib/*/libeasyvoice.so' -d "$work"
fail=0; n=0
for so in "$work"/lib/*/libeasyvoice.so; do
  n=$((n+1))
  # Read once into a variable: `nm | grep -q` under pipefail SIGPIPEs nm and
  # reports a symbol missing that is there.
  syms=$("$NM" -D --defined-only "$so" | awk '{print $NF}')
  extra=$(printf '%s\n' "$syms" | grep -v -E '^(Java_.*|JNI_OnLoad)$' || true)
  abi=$(basename "$(dirname "$so")")
  if [ -n "$extra" ]; then
    echo "FAIL $abi exports $(echo "$extra" | wc -l) symbol(s) beyond Java_*/JNI_OnLoad, e.g.:"; echo "$extra" | head -5
    fail=1
  else echo "ok   $abi exports only Java_* and JNI_OnLoad"; fi
  printf '%s\n' "$syms" | grep -qx 'JNI_OnLoad' || { echo "FAIL $abi lost JNI_OnLoad"; fail=1; }
  [ "$(printf '%s\n' "$syms" | grep -c '^Java_')" -ge 6 ] || { echo "FAIL $abi exports fewer than 6 Java_ entry points"; fail=1; }
done
[ "$n" -gt 0 ] || { echo "FAIL no libeasyvoice.so in $apk"; exit 1; }
exit $fail
