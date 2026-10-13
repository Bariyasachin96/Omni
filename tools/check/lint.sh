#!/usr/bin/env bash
# Android lint over the app, with the REAL build -- the same rules CI's
# accessibility job runs (owner, 2026-10-08: "library ke rule ke hisab se").
#
# Lint is Android's own rule book plus the checks each AndroidX library ships
# inside its aar (Compose's NonObservableLocale, androidx.test's UseSdkSuppress,
# ...). app/build.gradle.kts sets warningsAsErrors and abortOnError, so a single
# warning fails here; the tree stands at "No issues found", and every exception
# is suppressed at its own line with the reason written beside it.
#
# Maven Central answers 429 under load. That is retried; anything else is not.
set -uo pipefail
ROOT=$(cd "$(dirname "$0")/../.." && pwd)
CACHE="$ROOT/tools/.cache"
GRADLE="$ROOT/gradlew"
GV=$(sed -n 's|^distributionUrl=.*gradle-\([0-9.]*\)-bin\.zip$|\1|p' "$ROOT/gradle/wrapper/gradle-wrapper.properties")
DIST=$(ls -d "${GRADLE_USER_HOME:-$HOME/.gradle}"/wrapper/dists/gradle-"$GV"-bin/*/gradle-"$GV" 2>/dev/null | head -1)
export ANDROID_HOME="$CACHE/android-sdk"
[ -n "$DIST" ] && [ -d "$ANDROID_HOME/platforms" ] || {
  echo "lint  SKIPPED -- Gradle $GV (the wrapper's) or the Android SDK is missing; run tools/bootstrap.sh"
  exit 0; }

LOG=${TMPDIR:-/tmp}/ev-lint.log
REPORT="$ROOT/app/build/reports/lint-results-debug.txt"
n=0
while :; do
  (cd "$ROOT" && "$GRADLE" --no-daemon :app:lintDebug) > "$LOG" 2>&1
  rc=$?
  [ $rc -eq 0 ] && break
  if grep -q " 429 \|status code 429" "$LOG" && [ $n -lt 4 ]; then
    n=$((n + 1)); echo "   Maven Central rate limit (429), retry $n of 4"; sleep $((n * 20)); continue
  fi
  if [ -s "$REPORT" ]; then cat "$REPORT"; else grep -E "What went wrong" -A8 "$LOG" | head -40; fi
  echo "lint  FAILED (full log: $LOG)"
  exit 1
done
# "No issues found." is the only clean answer; anything else is reported even
# if Gradle passed (a check someone downgraded to informational, say).
if ! grep -qx "No issues found." "$REPORT" 2>/dev/null; then
  cat "$REPORT" 2>/dev/null
  echo "lint  FAILED -- the report is not clean"
  exit 1
fi
echo "lint  app/src checked with Gradle $GV: No issues found."
