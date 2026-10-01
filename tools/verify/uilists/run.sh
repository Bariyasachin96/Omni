#!/usr/bin/env bash
# #9 (owner, 2026-10-01): Voice setup's row order (weights + tie-break label
# worked out once) and the Languages search filter (labels lowered once) give
# exactly what they gave at <ref>. run.sh <ref> --negative must FAIL.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-f62feee}
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-uilists
rm -rf "$WORK"; mkdir -p "$WORK"
for f in VoiceRows LanguagesActivity; do git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/$f.kt" > "$WORK/Old$f.kt"; done
python3 "$HERE/gen.py" "$WORK/OldVoiceRows.kt" "$SRC/VoiceRows.kt" "$WORK/OldLanguagesActivity.kt" "$SRC/LanguagesActivity.kt" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" ${2:+broken} 2>&1 | grep -v JAVA_TOOL
