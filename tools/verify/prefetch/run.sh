#!/usr/bin/env bash
# Same-engine prefetch: the REAL loadVoice family, lifted from
# EasyVoiceTtsService.kt, run in the old order (load after the chunk ends)
# and the new one (load while it speaks) against an AOSP-shaped engine.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-prefetch
rm -rf "$WORK"; mkdir -p "$WORK"
python3 "$HERE/gen.py" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
