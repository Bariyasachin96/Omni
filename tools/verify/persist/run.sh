#!/usr/bin/env bash
# #1 (owner, 2026-10-01): the persist functions write exactly what they wrote
# at <ref>, and the new ones never hold the language-list monitor while they
# commit. Lifted from the real files by gen.py.
#   run.sh [ref]    ref defaults to f62feee (before the change)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-f62feee}
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-persist
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt" > "$WORK/OldLangStore.kt"
python3 "$HERE/gen.py" "$WORK/OldLangStore.kt" "${EV_NEW_LS:-$SRC/LangStore.kt}" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
