#!/usr/bin/env bash
# The current voice's locale from the client's params instead of a voice-set
# marshal: the REAL loadVoice family of a ref (default HEAD) and of the working
# tree, against an AOSP-shaped engine whose setLanguage / setVoice / getVoice /
# getLanguage follow TextToSpeech.java line for line. Usage: run.sh [ref]
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-HEAD}
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-voicenow
rm -rf "$WORK"; mkdir -p "$WORK"
SVC=app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceTtsService.kt
git -C "$ROOT" show "$REF:$SVC" > "$WORK/old.kt"
python3 "$HERE/gen.py" "$WORK/old.kt" "$ROOT/$SVC" "$HERE/Driver.kt" "$WORK/Check.kt"
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
