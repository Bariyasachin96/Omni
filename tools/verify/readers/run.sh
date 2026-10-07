#!/usr/bin/env bash
# The processDirect answer readers of a ref (default HEAD) against the working
# tree's, over the real core's answers (oldnew's probe output) plus edge cases;
# also prints the time and allocation of each per answer. EV_NEG=latin breaks
# the new reader's latin flag (must FAIL).
set -euo pipefail
REF=${1:-HEAD}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-readers
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
ANS=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-oldnew/new.txt
[ -f "$ANS" ] || bash "$ROOT/tools/verify/oldnew/run.sh" >/dev/null 2>&1 || true
[ -f "$ANS" ] || { echo "no core answers at $ANS"; exit 2; }
mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" > "$WORK/old.kt"
python3 "$HERE/gen.py" "$WORK/old.kt" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" "$WORK/Check.kt" "$HERE/Driver.kt"
rm -f "$WORK/check.jar"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "Check.kt did not compile"; exit 1; }
java -cp "$WORK/check.jar" CheckKt "$ANS"
