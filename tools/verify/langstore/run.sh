#!/usr/bin/env bash
# #3 #4 #5 (owner, 2026-10-01): the one-walk LangStore answers against the old
# separate walks of commit <ref>, and the "push the detect sets only when the
# list changed" logic against the truth after every change. Lifted from the
# real files by gen.py.
#   run.sh [ref]    ref defaults to 99393a3
set -euo pipefail
REF=${1:-99393a3}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-langstore
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt" > "$WORK/OldLangStore.kt"
python3 "$HERE/gen.py" "$WORK/OldLangStore.kt" "$SRC/LangStore.kt" "$SRC/EasyVoiceTtsService.kt" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
