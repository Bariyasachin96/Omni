#!/usr/bin/env bash
# NOTHING LEAVES THE PHONE (owner, 2026-10-28: "internet ki jarurat Nahin Hai
# Kyunki yah to offline kam Karti Hai ml kit ... Har Jagah se internet Hata
# dena"). Fails when an APK -- or a merged AndroidManifest.xml -- asks for a
# network permission or carries Google datatransport's upload parts (the path ML
# Kit's usage logs left by; app/src/main/AndroidManifest.xml removes it and says
# why that is safe). A library update that brings either back is caught here,
# before anything is published.
#   tools/check/no-network.sh <app.apk | AndroidManifest.xml> ...
set -euo pipefail
[ $# -ge 1 ] || { echo "usage: no-network.sh <apk|manifest.xml> ..."; exit 2; }
PERMS='android\.permission\.(INTERNET|ACCESS_NETWORK_STATE|ACCESS_WIFI_STATE|CHANGE_NETWORK_STATE|CHANGE_WIFI_STATE)'
PARTS='com\.google\.android\.datatransport\.[A-Za-z0-9_.$]+'
AAPT2=""
for c in "${ANDROID_HOME:-/nonexistent}"/build-tools/*/aapt2 tools/.cache/android-sdk/build-tools/*/aapt2; do
  [ -x "$c" ] && AAPT2=$c
done
fail=0
for f in "$@"; do
  case "$f" in
    *.apk)
      [ -n "$AAPT2" ] || { echo "FAIL aapt2 not found (\$ANDROID_HOME/build-tools, tools/.cache)"; exit 1; }
      # Read into a variable first: `aapt2 | grep -q` under pipefail SIGPIPEs aapt2.
      text=$("$AAPT2" dump xmltree --file AndroidManifest.xml "$f") ;;
    *) text=$(cat "$f") ;;
  esac
  found=$( { printf '%s\n' "$text" | grep -oE "$PERMS|$PARTS" || true; } | sort -u)
  if [ -n "$found" ]; then
    echo "FAIL $f:"; printf '  %s\n' $found; fail=1
  else
    echo "ok   $f: no network permission, no datatransport component"
  fi
done
exit $fail
