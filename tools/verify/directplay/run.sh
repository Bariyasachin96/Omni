#!/usr/bin/env bash
# The direct player (owner, 2026-10-14), old against new: the speak() loop of
# the last commit before it (OLD_REF) and the working tree's direct player,
# both lifted by gen.py and run by Driver.kt on one virtual clock, with
# SpeechPlayer.kt and SpeechOut.kt compiled as they are against an AudioTrack
# model (fake/).
#   run.sh [old-ref] [scenarios]        EV_NEG=<name> breaks the new code on purpose
set -euo pipefail
REF=${1:-236c56c}
N=${2:-50000}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-directplay${EV_NEG:+-$EV_NEG}
rm -rf "$WORK"; mkdir -p "$WORK"
SVC=app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
git -C "$ROOT" show "$REF:$SVC" > "$WORK/Old.kt"
python3 "$HERE/gen.py" "$WORK/Old.kt" "$ROOT/$SVC" "$HERE/Driver.kt" "$WORK/Check.kt"
cp "$SRC/SpeechPlayer.kt" "$SRC/SpeechOut.kt" "$WORK/"
if [ "${EV_NEG:-}" = nocarry ]; then
  perl -0pi -e 's/        if \(cur != null\) cur\.carry = null\n//' "$WORK/SpeechOut.kt"
  ! grep -q "cur.carry = null" "$WORK/SpeechOut.kt" || { echo "EV_NEG nocarry: pattern not found"; exit 1; }
fi
"$KOTLINC" -nowarn "$WORK/Check.kt" "$WORK/SpeechPlayer.kt" "$WORK/SpeechOut.kt" "$HERE"/fake/*.kt \
  -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -Xss8m -jar "$WORK/check.jar" "$N" 2>&1 | grep -v JAVA_TOOL
