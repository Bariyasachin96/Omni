#!/usr/bin/env bash
# THE RELEASE IS SIGNED WITH THE OWNER'S KEY, IN EVERY SCHEME (owner,
# 2026-10-14: "aage se ... sabhi file ko naya wala keyStore se", and "sirf V2
# signature aata hai ... V1 V2 V3 V4 jitna bhi hai"). Every published APK must
# verify with v1, v2, v3 AND v4, and every signer must be the certificate of
# alias sachin, CN=Sachin Baria. A file signed with any other key would not
# install over the app on the owner's phone.
#
#   tools/check/release-signature.sh <file>...
#
# An .apk is read with apksigner, which verifies each scheme, not only reads
# it. Two of its defaults have to be overridden to see all four:
#   --min-sdk-version 23   apksigner checks v1 only for platforms that use it
#                          (below Android 7, API 24 = our minSdk), so at the
#                          APK's own minSdk it reports v1 "false" although the
#                          JAR signature is there;
#   --v4-signature-file    v4 is not inside the APK but in <apk>.idsig beside
#                          it, which apksigner does not look for by itself.
# An .aab is jar-signed only (a bundle has no v2+; Play signs what it ships),
# so keytool reads its certificate. Run by CI before publishing ("Check the
# release signature").
set -uo pipefail
WANT=1e379aef8fa51a58d60f56b60946b9aed161282fab4323178bb07b7be0b6819b
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
APKSIGNER=$(command -v apksigner || true)
if [ -z "$APKSIGNER" ]; then
  for c in "${ANDROID_HOME:-/nonexistent}"/build-tools/*/apksigner "$HERE"/../.cache/android-sdk/build-tools/*/apksigner; do
    [ -x "$c" ] && APKSIGNER=$c
  done
fi
[ $# -gt 0 ] || { echo "usage: $0 <file.apk|file.aab>..."; exit 2; }
fail=0
for f in "$@"; do
  case "$f" in
    *.apk)
      [ -n "$APKSIGNER" ] || { echo "FAIL apksigner not found (PATH, \$ANDROID_HOME/build-tools, tools/.cache)"; exit 1; }
      [ -f "$f.idsig" ] || { echo "FAIL  $f has no v4 signature file beside it ($f.idsig)"; fail=1; continue; }
      out=$("$APKSIGNER" verify -v --print-certs --min-sdk-version 23 --v4-signature-file "$f.idsig" "$f" 2>&1)
      bad=""
      for v in v1 v2 v3 v4; do
        grep -q "^Verified using $v scheme .*: true$" <<< "$out" || bad="$bad $v"
      done
      # Every signer of every scheme, not only the first.
      got=$(sed -n 's/^.*certificate SHA-256 digest: //p' <<< "$out" | sort -u | tr '\n' ' ')
      [ "$got" = "$WANT " ] || bad="$bad key(${got:-none})"
      # apksigner's own first error says why, e.g. "Missing META-INF/MANIFEST.MF"
      # for an APK with no v1 signature at all.
      why=$(grep -m1 "^ERROR" <<< "$out")
      if [ -z "$bad" ]; then echo "ok    $f  v1 v2 v3 v4, owner's key"
      else echo "FAIL  $f: not verified:$bad${why:+ -- $why}"; fail=1; fi ;;
    *.aab)
      got=$(keytool -printcert -jarfile "$f" 2>/dev/null | sed -n 's/^[[:space:]]*SHA256: //p' | tr -d ':' | tr 'A-F' 'a-f' | sort -u | tr '\n' ' ')
      if [ "$got" = "$WANT " ]; then echo "ok    $f  jar-signed, owner's key"
      else echo "FAIL  $f is signed with ${got:-nothing readable}, not the owner's key $WANT"; fail=1; fi ;;
    *) echo "FAIL  $f is not an .apk or an .aab"; fail=1 ;;
  esac
done
exit $fail
