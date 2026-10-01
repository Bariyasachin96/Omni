#!/usr/bin/env bash
# #7 (owner, 2026-10-01): a detected run's iso3 is worked out once per
# (language, latin) per utterance; with logging on, every run is worked out as
# before so the log is unchanged. Compared with <ref>'s per-run conversion.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-9800ce1}
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-runlang
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" > "$WORK/Old.kt"
python3 "$HERE/gen.py" "$WORK/Old.kt" "${EV_NEW:-$SRC/EasyVoiceTtsService.kt}" "$SRC/IsoCodes.kt" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
