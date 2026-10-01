#!/usr/bin/env bash
# Background-scan scheduling, old against new (owner, 2026-10-01): the REAL
# startupScan / package-event request / onScanFinished / screens-closed observer
# of the last commit and the working tree, over random histories.
#   run.sh [old-ref] [histories]
set -euo pipefail
REF=${1:-HEAD}
N=${2:-200000}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-scansched
rm -rf "$WORK"; mkdir -p "$WORK"
SVC=app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt
git -C "$ROOT" show "$REF:$SVC" > "$WORK/Old.kt"
python3 "$HERE/gen.py" "$WORK/Old.kt" "$ROOT/$SVC" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" "$N" 2>&1 | grep -v JAVA_TOOL
