#!/usr/bin/env bash
# Every static check, in the order that fails cheapest first.
#
#   tools/check-all.sh
#
# Run this before every push (after tools/bootstrap.sh once per container). It
# takes a few minutes, most of it the real Gradle compile, and it has caught
# things CI would only have found thirteen minutes later.
#
# The last step compiles the app AND its instrumented tests with the same
# Gradle, AGP, Kotlin and Compose plugin CI uses, and fails on any error or
# compiler warning. It does not package an APK; the native build and the
# emulator run stay CI's job.
#
# The behavioural proofs are separate and slower -- tools/verify/*/run.sh.
set -uo pipefail

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)
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
step "workflow shell"          bash    tools/check/workflow-shell.sh
step "gradle compile (app + androidTest)" bash tools/check/gradle-compile.sh

echo
if [ "$fail" = 0 ]; then
  echo "ALL CHECKS PASSED"
else
  echo "SOMETHING FAILED -- see above"
fi
exit $fail
