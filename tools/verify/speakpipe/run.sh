#!/usr/bin/env bash
# The speak loop, old against new (owner, 2026-10-01): the REAL speaking code
# of two versions of EasyVoiceTtsService.kt -- by default the last commit and
# the working tree -- lifted by gen.py and driven by Driver.kt against
# AOSP-shaped engines on one virtual clock.
#   run.sh [old-ref] [scenarios]        EV_NEG=<name> breaks the new code on purpose
set -euo pipefail
REF=${1:-HEAD}
N=${2:-200000}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-speakpipe${EV_NEG:+-$EV_NEG}
rm -rf "$WORK"; mkdir -p "$WORK"
SVC=app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt
git -C "$ROOT" show "$REF:$SVC" > "$WORK/Old.kt"
python3 "$HERE/gen.py" "$WORK/Old.kt" "$ROOT/$SVC" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" "$N" 2>&1 | grep -v JAVA_TOOL
