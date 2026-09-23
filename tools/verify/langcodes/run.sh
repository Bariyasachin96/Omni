#!/usr/bin/env bash
# Prove that every language code CLD2's FULL build can return resolves the same
# way here as it does in AutoTTS.
#
#     tools/verify/langcodes/run.sh
#
# WHY THIS EXISTS. On 2026-09-08 the app swapped CLD2's compact quadgram tables
# for the full set, which took the detector from 82 quadgram-scored languages to
# 183. That is a hundred codes the app had never been handed before, and the
# question the owner asked was the right one: is there anything in the reading
# path that quietly cannot deal with them? This answers it mechanically instead
# of by reading.
#
# WHAT IS COMPARED. Two routes decide whether a detected code is routable.
#
#   AutoTTS   c3.n.n(lang). A THREE-letter code is NOT normalised -- it is
#             scanned for verbatim in the language list. Anything else goes
#             through c3.e.c, and a null there makes n.n answer FALSE outright.
#
#   ours      the native toIso3() in app/src/main/cpp/tts_engine_core.cpp,
#             whose answer is looked up in detectOkIso3Set. It never returns
#             null; a code it cannot map comes back unchanged.
#
# They agree exactly when every code c3.e.c refuses also produces, on our side,
# a string that is not a real iso3. That is what is asserted, code by code --
# not argued.
#
# The ISO tables are read out of IsoCodes.kt at run time, so this cannot pass
# against a stale copy of them. The native toIso3() is mirrored in Java, and the
# fingerprint check below fails if that C++ ever changes, so the mirror cannot
# drift silently either.
set -uo pipefail
ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../../.." && pwd)
HERE="$ROOT/tools/verify/langcodes"
CORE="$ROOT/app/src/main/cpp/tts_engine_core.cpp"
ISO="$ROOT/app/src/main/java/com/sachinbaria/easyvoice/IsoCodes.kt"

echo "1/3  fingerprinting the native toIso3()"
body=$(awk '/^static std::string toIso3\(const std::string& lang\)\{/,/^\}/' "$CORE" | tr -d ' \t\n')
if [ -z "$body" ]; then
  echo "     FAILED: toIso3 not found in $CORE"; exit 1
fi
sum=$(printf '%s' "$body" | sha256sum | cut -d' ' -f1)
expected=$(cat "$HERE/toiso3.sha256" 2>/dev/null || echo "")
if [ "$sum" != "$expected" ]; then
  echo "     FAILED: the native toIso3() changed."
  echo "     Re-read it, update java/LangCodes.java's ourKey() to match, then:"
  echo "         echo $sum > $HERE/toiso3.sha256"
  exit 1
fi
echo "     unchanged"

echo "2/3  building (javac)"
out=$(mktemp -d)
trap 'rm -rf "$out"' EXIT
rm -f "$out"/*.class
if ! javac -d "$out" "$HERE/java/LangCodes.java" 2>&1 | grep -v '^Picked up JAVA_TOOL_OPTIONS' ; then :; fi
if [ ! -f "$out/LangCodes.class" ]; then
  echo "     FAILED: javac produced no class file"; exit 1
fi

echo "3/3  sweeping every CLD2 language code"
java -cp "$out" LangCodes "$ISO" "$HERE/codes.txt" 2>&1 | grep -v '^Picked up JAVA_TOOL_OPTIONS'
exit "${PIPESTATUS[0]}"
