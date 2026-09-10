#!/usr/bin/env bash
# Every static check, in the order that fails cheapest first.
#
#   tools/check-all.sh [baseline-ref-for-the-kotlin-typecheck]
#
# Run this before every push. It takes about two minutes, almost all of it the
# Kotlin type-check, and it has caught things CI would only have found four
# minutes later -- or, in the case of a wrong trailing lambda, would have found
# only after the owner reported the app misbehaving.
#
# It does NOT build the app. The NDK, CMake, R8 and the APK are CI's job. What
# this proves is that the sources are well-formed, that our own names resolve to
# something with the right signature, that the XML references exist, and that no
# NEW Kotlin type error appeared against a baseline commit.
#
# The behavioural proofs are separate and slower -- tools/verify/*/run.sh.
set -uo pipefail

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)
BASE=${1:-HEAD}
cd "$ROOT"

fail=0
step() {
  echo
  echo "── $1 ────────────────────────────────────────────"
  shift
  "$@" || { echo "   ^^ FAILED"; fail=1; }
}

step "kotlin structure"        python3 tools/check/ktcheck.py
step "kotlin call signatures"  python3 tools/check/ktresolve.py
step "kotlin imports"          python3 tools/check/ktimports.py
step "xml resources"           python3 tools/check/xmlcheck.py
step "invariants"              bash    tools/check/invariants.sh
step "c++ syntax"              bash    tools/check/cpp-syntax.sh
step "minSdk API usage"        bash    tools/check/minsdk-api.sh
step "kotlin type-check vs $BASE" bash tools/check/kotlin-typecheck.sh "$BASE"

echo
if [ "$fail" = 0 ]; then
  echo "ALL CHECKS PASSED"
else
  echo "SOMETHING FAILED -- see above"
fi
exit $fail
