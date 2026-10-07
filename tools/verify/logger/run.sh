#!/usr/bin/env bash
# The new EasyVoiceLogger against the one in commit <ref>: the same random
# lines (Hindi, Gujarati, emoji, lone surrogates, 250-char lines), clear() and
# logging switched off/on, a tiny size cap so rotation fires again and again,
# a fake clock so timestamps match. Every log file must be byte-identical.
#   run.sh [ref]     ref defaults to 99393a3
set -euo pipefail
REF=${1:-99393a3}
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-logger
CAP=${EV_LOG_CAP:-1500}
rm -rf "$WORK"; mkdir -p "$WORK/src"
prep() {  # source on stdin, $1 = package
  sed -e "s/^package com.sachinbaria.easyvoice/package $1/" \
      -e "s/MAX_LOG_BYTES = 2L \* 1024L \* 1024L/MAX_LOG_BYTES = ${CAP}L/" \
      -e "s/timestampFormat.format(Date())/timestampFormat.format(FakeClock.now())/" \
      -e "s/timestampFormat.format(LocalDateTime.now())/timestampFormat.format(FakeClock.nowLocal())/" \
      -e "1a import FakeClock"
}
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceLogger.kt" | prep old > "$WORK/src/Old.kt"
prep < "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/EasyVoiceLogger.kt" new > "$WORK/src/New.kt"
grep -q "FakeClock.now" "$WORK/src/Old.kt" && grep -q "FakeClock.now" "$WORK/src/New.kt" || { echo "clock substitution did not apply"; exit 1; }
grep -q "MAX_LOG_BYTES = ${CAP}L" "$WORK/src/Old.kt" && grep -q "MAX_LOG_BYTES = ${CAP}L" "$WORK/src/New.kt" || { echo "cap substitution did not apply"; exit 1; }
cp "$HERE"/Driver.kt "$HERE"/Stubs.kt "$HERE"/Context.kt "$WORK/src/"
"$KOTLINC" -nowarn "$WORK"/src/*.kt -include-runtime -d "$WORK/logger.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/logger.jar" ] || { echo "did not compile"; exit 1; }
fail=0
for seed in 1 2 3 4 5; do
  java -Dfile.encoding=UTF-8 -jar "$WORK/logger.jar" "$WORK/run" 4000 "$seed" 2>&1 | grep -v JAVA_TOOL || fail=1
done
exit $fail
