#!/usr/bin/env bash
# #1 #4 (owner, 2026-10-01): EngineFinder asks the package manager for the
# built-in engine once (and without loading labels), and engineLabel looks the
# scan's label up in a map -- with the same answers as <ref>'s code, over random
# engine sets, screens opening, scans and package changes.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-9800ce1}
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-enginefinder
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/EngineFinder.kt" > "$WORK/Old.kt"
python3 "$HERE/gen.py" "$WORK/Old.kt" "${EV_NEW:-$ROOT/app/src/main/java/com/sachinbaria/easyvoice/EngineFinder.kt}" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
