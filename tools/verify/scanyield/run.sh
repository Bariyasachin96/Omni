#!/usr/bin/env bash
# A background scan reads an engine only while nothing is being spoken
# (owner, 2026-10-07). Compiles the REAL ScanYield.kt with a virtual clock:
# random utterance histories (back to back, short gaps, two-minute ones) against
# a scan walk of 1-5 engines (some stuck for the 30 s per-engine timeout), a
# background scan or the user's. Checks: no background read starts during an
# utterance; every scan finishes; the user's scan never waits; the 180 s
# watchdog counts walking time only (never fires inside it, always past it).
#   run.sh [scans]     EV_NEG=nogate|noresume|nopause breaks it on purpose (must FAIL)
set -euo pipefail
N=${1:-100000}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-scanyield${EV_NEG:+-$EV_NEG}
rm -rf "$WORK"; mkdir -p "$WORK"
grep -v '^package ' "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/ScanYield.kt" > "$WORK/Y.kt"
case "${EV_NEG:-}" in
  nogate)   sed -i 's/if (yieldToSpeech \&\& speechInFlight())/if (false)/' "$WORK/Y.kt" ;;
  noresume) sed -i 's/if (speechInFlight()) return$/return/' "$WORK/Y.kt" ;;
  nopause)  sed -i '0,/            disarmWatchdog()/s//            \/\/ disarm removed/' "$WORK/Y.kt" ;;
esac
cat "$WORK/Y.kt" "$HERE/Driver.kt" > "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" "$N" 2>&1 | grep -v JAVA_TOOL
