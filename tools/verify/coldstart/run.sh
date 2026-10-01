#!/usr/bin/env bash
# The cold-start waiter, lifted from the working tree, under real threads.
#   run.sh            -> ALL PASS expected
#   EV_NEG=<name>     -> breaks the lifted code; must FAIL (nonotify, nostop,
#                        norestore, nogate, nogeneration)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-coldstart
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
mkdir -p "$WORK"
python3 "$HERE/gen.py" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt" "$HERE/Driver.kt" "$WORK/Check.kt"
rm -f "$WORK/check.jar"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "Check.kt did not compile"; exit 1; }
java -jar "$WORK/check.jar"
