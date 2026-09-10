#!/usr/bin/env bash
# Compile the whole app against minSdk's OWN android.jar and report every
# android.* symbol that does not exist there.
#
# WHY THIS EXISTS, and it is not theoretical. On 2026-09-10 this check found two
# NoClassDefFoundErrors that nothing else in the project could see:
#
#   AudioFocusRequest    API 26, called BARE from the service's onCreate --
#                        every Android 7 phone lost its voice on startup
#   NotificationChannel  API 26, called whenever "Show persistent notification"
#                        was on
#
# Neither is a compile error normally, because kotlin-typecheck.sh uses API 37's
# android.jar -- the compileSdk -- where both resolve fine. Neither is caught by
# a try/catch either: a missing class is an Error, not an Exception. And Android
# Lint's NewApi, which is the usual answer, needs Gradle and the SDK, and this
# container has neither.
#
# HOW IT IS JUDGED. Every hit here is a call ABOVE minSdk, which is fine when it
# sits behind a Build.VERSION.SDK_INT guard and fatal when it does not -- and no
# compiler can tell those apart. So each one is reviewed once by a person and
# written into minsdk-allowlist.txt with the guard that makes it safe. A hit
# that is NOT on that list fails the run, which is exactly the moment to add a
# guard. A stale entry only warns, because deleting code should not fail a run.
set -u
ROOT=$(cd "$(dirname "$0")/../.." && pwd)
CACHE="$ROOT/tools/.cache"
SRC="$ROOT/app/src/main/java/com/tts/easyvoice"
ALLOW="$ROOT/tools/check/minsdk-allowlist.txt"
WORK=${TMPDIR:-/tmp}/ev-minsdk

MIN=$(grep -oE '^\s*minSdk\s*=\s*[0-9]+' "$ROOT/app/build.gradle.kts" | grep -oE '[0-9]+$')
[ -n "$MIN" ] || { echo "could not read minSdk from app/build.gradle.kts"; exit 2; }
JAR="$CACHE/android-$MIN.jar"

if [ ! -f "$JAR" ]; then
  # The package name is read off Google's own repository index, never guessed --
  # platform-24_r02.zip is not derivable from the API level alone.
  mkdir -p "$WORK"
  curl -sSf -m 180 -o "$WORK/repo.xml" \
    https://dl.google.com/android/repository/repository2-3.xml 2>/dev/null || {
      echo "minsdk-api  SKIPPED -- dl.google.com unreachable (egress policy, not an error)"; exit 0; }
  ZIP=$(python3 - "$WORK/repo.xml" "$MIN" <<'PY'
import re,sys
s=open(sys.argv[1],encoding='utf-8',errors='replace').read()
m=re.search(r'<remotePackage path="platforms;android-%s".*?</remotePackage>'%sys.argv[2],s,re.S)
print(re.search(r'<url>([^<]+)</url>',m.group(0)).group(1) if m else '')
PY
)
  [ -n "$ZIP" ] || { echo "minsdk-api  SKIPPED -- no platform package for android-$MIN"; exit 0; }
  curl -sSf -m 600 -o "$WORK/p.zip" "https://dl.google.com/android/repository/$ZIP" || {
      echo "minsdk-api  SKIPPED -- could not fetch $ZIP"; exit 0; }
  # Into a scratch dir, NEVER straight into $CACHE: the extracted file is called
  # android.jar and would land on top of the API 37 one kotlin-typecheck.sh uses.
  # (It did, once, and the next check-all reported "no android.jar".)
  rm -rf "$WORK/x" && mkdir -p "$WORK/x"
  unzip -q -o -j "$WORK/p.zip" "*/android.jar" -d "$WORK/x" && mv "$WORK/x/android.jar" "$JAR"
fi
[ -f "$JAR" ] || { echo "minsdk-api  SKIPPED -- no android-$MIN.jar"; exit 0; }

KOTLINC="$CACHE/kotlinc/bin/kotlinc"
[ -x "$KOTLINC" ] || { echo "minsdk-api  SKIPPED -- run tools/bootstrap.sh"; exit 0; }
CP="$JAR"
[ -s "$CACHE/androidx-classpath.txt" ] &&
  CP="$JAR:$(tr '\n' ':' < "$CACHE/androidx-classpath.txt" | sed 's/:*$//')"
PLUGIN=""; [ -f "$CACHE/compose-plugin.jar" ] && PLUGIN="-Xplugin=$CACHE/compose-plugin.jar"

rm -rf "$WORK/src" "$WORK/out"; mkdir -p "$WORK/src"
cp "$SRC"/*.kt "$WORK/src/"
python3 "$ROOT/tools/check/genr.py" "$WORK/src" >/dev/null 2>&1
"$KOTLINC" -cp "$CP" -jvm-target 17 $PLUGIN "$WORK/src"/*.kt -nowarn -d "$WORK/out" 2>&1 \
  | grep ": error:" > "$WORK/err.txt"

# One line per (file, symbol). R is generated locally and never an android API.
grep -oE "[A-Za-z]+\.kt:[0-9]+:[0-9]+: error: unresolved reference '[A-Za-z_]+'" "$WORK/err.txt" \
  | sed -E "s|.*/||; s|:[0-9]+:[0-9]+: error: unresolved reference .|:|; s|'$||" \
  | grep -v ":R$" | sort -u > "$WORK/hits.txt"

echo "minsdk-api  compiled against API $MIN, $(wc -l < "$WORK/hits.txt") symbol(s) above it"
sed -E 's/[[:space:]]*#.*//; /^[[:space:]]*$/d' "$ALLOW" | sort -u > "$WORK/allow.txt"
NEW=$(comm -23 "$WORK/hits.txt" "$WORK/allow.txt")
STALE=$(comm -13 "$WORK/hits.txt" "$WORK/allow.txt")
[ -n "$STALE" ] && { echo "  stale allowlist entries (harmless, tidy when convenient):"; echo "$STALE" | sed 's/^/    /'; }
if [ -n "$NEW" ]; then
  echo "  NOT ALLOWLISTED -- these run above API $MIN with no reviewed guard:"
  echo "$NEW" | sed 's/^/    /'
  echo "  Each one is a NoClassDefFoundError or NoSuchMethodError on a real"
  echo "  device unless a Build.VERSION.SDK_INT guard keeps it from executing."
  echo "  Add the guard, then add the line to tools/check/minsdk-allowlist.txt."
  exit 1
fi
echo "  every one sits behind a reviewed SDK_INT guard"
