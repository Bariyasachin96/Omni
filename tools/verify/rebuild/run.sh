#!/usr/bin/env bash
# #5 (owner, 2026-10-01): LangStore.rebuildFromScan with a HashSet for seen
# names, a first-entry-per-iso3 map and one name/ISO lookup per language gives
# the list <ref>'s gave: same entries, fields, engine lists, order, and the same
# persistDisabled call -- over random scans under four default locales.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-9800ce1}
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-rebuild
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt" > "$WORK/Old.kt"
python3 "$HERE/gen.py" "$WORK/Old.kt" "${EV_NEW:-$ROOT/app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt}" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
