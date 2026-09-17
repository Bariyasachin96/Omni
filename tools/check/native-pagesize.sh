#!/usr/bin/env bash
# 16 KB PAGE SIZE: does every shipped .so load natively on a 16 KB device?
#
# Android 16 runs a 4 KB-aligned native library in a COMPATIBILITY MODE and
# shows the user a dialog about it. On a blind owner's phone that dialog is an
# unexplained interruption, and the compatibility mode is not something to ship
# when the fix is a build flag.
#
# NDK r28 and later align to 16 KB by default and this project builds with r30,
# so this should always pass -- which is exactly why it is worth checking rather
# than assuming. A future NDK pin, a stray -Wl,-z,max-page-size, or a
# prebuilt .so added to the tree would all break it silently.
#
# It is NOT in check-all.sh, because it needs a built APK and there is no NDK in
# the dev container. Run it against a release asset:
#
#   tools/check/native-pagesize.sh EasyVoice-889-arm64-v8a.apk
#
# What it reads: every PT_LOAD program header's p_align in every lib/*/*.so.
# 0x4000 is 16384 is 16 KB.
#
# ONLY 64-BIT ABIs ARE REQUIRED TO BE ALIGNED, and the first draft of this check
# got that wrong and reported a defect that is not one. Google's own page is
# explicit -- "all apps targeting Android 15 (API level 35) and higher must
# support 16 KB memory page sizes on 64-BIT DEVICES", and its own verification
# step says "if any arm64-v8a or x86_64 shared libraries are UNALIGNED, you'll
# need to update the packaging for those libraries"
# (developer.android.com/guide/practices/page-sizes). 16 KB pages exist only on
# 64-bit devices, so a 32-bit library can never be loaded on one.
#
# This app ships armeabi-v7a beside arm64-v8a, and its armeabi-v7a library IS
# 4 KB aligned. That is correct, not a finding: a 32-bit ELF is reported n/a
# here and never fails the run.
set -euo pipefail

apk="${1:-}"
[ -n "$apk" ] && [ -f "$apk" ] || { echo "usage: $0 <path-to.apk>"; exit 2; }

work="$(mktemp -d)"
trap 'rm -rf "$work"' EXIT
unzip -o -q "$apk" 'lib/*' -d "$work" || true

count=0
bad=0
while IFS= read -r so; do
  count=$((count + 1))
  python3 - "$so" <<'PY' || bad=$((bad + 1))
import struct, sys
path = sys.argv[1]
d = open(path, 'rb').read()
if d[:4] != b'\x7fELF':
    print("  %s: NOT AN ELF" % path); sys.exit(1)
is64 = d[4] == 2
name = path.split('lib/')[-1]
if not is64:
    # 16 KB pages are a 64-bit-only thing, so this library is out of scope.
    # Everything below reads the 64-bit ELF header layout and would be wrong
    # for a 32-bit one, which is why the exit comes first rather than a branch.
    print("  n/a   %-46s 32-bit ABI -- 16 KB pages are 64-bit only" % name)
    sys.exit(0)
phoff = struct.unpack_from('<Q', d, 0x20)[0]
phentsize = struct.unpack_from('<H', d, 0x36)[0]
phnum = struct.unpack_from('<H', d, 0x38)[0]
align_off, unpack = 0x30, '<Q'
aligns = []
for i in range(phnum):
    off = phoff + i * phentsize
    if struct.unpack_from('<I', d, off)[0] == 1:          # PT_LOAD
        aligns.append(struct.unpack_from(unpack, d, off + align_off)[0])
if not aligns:
    print("  %s: no PT_LOAD segment" % path); sys.exit(1)
low = min(aligns)
if low >= 16384:
    print("  ok    %-46s p_align %d (%d KB)" % (name, low, low // 1024))
    sys.exit(0)
print("  FAIL  %-46s p_align %d (%d KB) -- 16 KB devices will run this in "
      "compatibility mode and show the user a dialog" % (name, low, low // 1024))
sys.exit(1)
PY
done < <(find "$work/lib" -name '*.so' 2>/dev/null | sort)

[ "$count" -gt 0 ] || { echo "no .so found in $apk -- is this the right file?"; exit 1; }
if [ "$bad" -eq 0 ]; then
  echo "16 KB page size: all $count native libraries are in scope and aligned, or out of scope"
else
  echo "16 KB page size: $bad of $count 64-bit native libraries are NOT 16 KB aligned"
  exit 1
fi
