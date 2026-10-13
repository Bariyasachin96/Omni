#!/usr/bin/env bash
# #1 (owner, 2026-10-01): the persist functions write exactly what they wrote
# at <ref>, and the new ones never hold the language-list monitor while they
# commit. Lifted from the real files by gen.py.
#   run.sh [ref]    ref defaults to 7d8649e (the last commit before the old
#                   storage's leftovers went, 2026-10-08). Both sides then run
#                   against the REAL Jetpack DataStore library. An older ref
#                   from before DataStore (3038d16, the last one) still works:
#                   its side runs against a faithful stand-in of the old store.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-7d8649e}
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-persist
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/LangStore.kt" > "$WORK/OldLangStore.kt"
python3 "$HERE/gen.py" "$WORK/OldLangStore.kt" "${EV_NEW_LS:-$SRC/LangStore.kt}" "$HERE/Driver.kt" "$WORK/Check.kt"
# The new side runs the REAL Jetpack DataStore (2026-10-07): its JVM jars, at the
# version the version catalog (gradle/libs.versions.toml) declares, cached
# beside the other tools.
DSV=$(sed -n 's/^datastore *= *"\([^"]*\)".*/\1/p' "$ROOT/gradle/libs.versions.toml")
JARS=$ROOT/tools/.cache/datastore-$DSV; mkdir -p "$JARS"
G=https://dl.google.com/android/maven2; M=https://repo1.maven.org/maven2
for spec in "$G/androidx/datastore/datastore-preferences-core-jvm/$DSV" "$G/androidx/datastore/datastore-core-jvm/$DSV" \
            "$G/androidx/datastore/datastore-core-okio-jvm/$DSV" "$G/androidx/datastore/datastore-preferences-proto/$DSV" \
            "$G/androidx/datastore/datastore-preferences-external-protobuf/$DSV" "$G/androidx/annotation/annotation-jvm/1.9.1" \
            "$M/com/squareup/okio/okio-jvm/3.9.1" "$M/org/jetbrains/kotlinx/kotlinx-coroutines-core-jvm/1.11.0"; do
  a=$(basename "$(dirname "$spec")"); v=$(basename "$spec"); f="$JARS/$a-$v.jar"
  [ -s "$f" ] || curl -sfL -o "$f" "$spec/$a-$v.jar" || { echo "could not fetch $a $v"; exit 2; }
done
CP=$(ls "$JARS"/*.jar | tr '\n' ':')
"$KOTLINC" -nowarn -cp "$CP" "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v -e '^warning' -e '^Picked up' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -cp "$WORK/check.jar:$CP" CheckKt 2>&1 | grep -v JAVA_TOOL
