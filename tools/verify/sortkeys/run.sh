#!/usr/bin/env bash
# #2 (owner, 2026-10-01): LangStore.sortedByName folds each name once and sorts
# in exactly the order the old fold-per-comparison comparators gave, under 13
# default locales, with duplicates (stability). run.sh --negative sorts the
# unfolded names and must FAIL.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-sortkeys
rm -rf "$WORK"; mkdir -p "$WORK"
python3 "$HERE/gen.py" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" ${1:+broken} 2>&1 | grep -v JAVA_TOOL
