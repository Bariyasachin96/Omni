#!/usr/bin/env bash
# The settings read and write path (owner, 2026-10-08: "data store ke rule ke
# hisab se"): LangStore.prefs() follows the DataStore guide -- one retry, then
# catch the IOException and go on with emptyPreferences() -- and while a process
# runs on those defaults write()/writeLater() refuse, so defaults never overwrite
# the real file. The REAL functions, lifted by gen.py, against the REAL DataStore
# JVM library (a healthy file, and a path it cannot read) and a fake store whose
# data flow throws.
#   run.sh                 all scenarios must pass
#   EV_NEG=noretry|nocatch|norefuse|catchall run.sh   each must FAIL
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-settingsread
rm -rf "$WORK"; mkdir -p "$WORK"
python3 "$HERE/gen.py" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt" "$HERE/Driver.kt" "$WORK/Check.kt" "${EV_NEG:-}"
DSV=$(grep -oE 'datastore-preferences:[0-9.]+' "$ROOT/app/build.gradle.kts" | cut -d: -f2)
JARS=$ROOT/tools/.cache/datastore-$DSV
[ -d "$JARS" ] || { echo "run tools/verify/persist/run.sh once first: it fetches the DataStore $DSV JVM jars"; exit 2; }
CP=$(ls "$JARS"/*.jar | tr '\n' ':')
"$KOTLINC" -nowarn -cp "$CP" "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -cp "$WORK/check.jar:$CP" CheckKt "$WORK/files" 2>&1 | grep -v JAVA_TOOL
