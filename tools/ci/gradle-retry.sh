#!/usr/bin/env bash
# Run Gradle, and retry ONLY a transient repository failure.
#
#   tools/ci/gradle-retry.sh assembleRelease -PevAbiSplit
#   tools/ci/gradle-retry.sh connectedDebugAndroidTest
#
# WHY IT IS A FILE AND NOT INLINE YAML, which is the whole reason this exists in
# this shape. The first version put the function straight into build.yml. It
# worked in the build job and broke the accessibility job with
#
#     /usr/bin/sh -c set -o pipefail
#     /usr/bin/sh: 1: set: Illegal option -o pipefail
#
# because reactivecircus/android-emulator-runner runs its `script:` input with
# /usr/bin/sh -- dash, not bash -- and one line at a time. `set -o pipefail` is
# not POSIX, and a multi-line shell FUNCTION cannot survive being executed line
# by line either. A file with its own shebang sidesteps both: whatever shell the
# caller uses, this runs under bash, and the caller's line is self-contained.
#
# The lesson, recorded because it is the same one this project keeps relearning:
# the logic was tested locally against a stubbed gradle and all three cases
# passed. What was NOT tested was the ENVIRONMENT it would run in. A local test
# proves the algorithm, not the shell.
#
# WHY RETRY AT ALL. Build 866's accessibility job died on
#   Could not find org.ow2.asm:asm-commons:9.9      ... and four more
# -- AGP's OWN transitive dependencies, none of it real: all five answer 200
# from Maven Central, build 864 ran the identical AGP and Gradle green, and the
# SAME RUN's build job resolved the same classpath and published the APK. One
# runner could not reach the repositories for a moment. `cache-disabled: true`
# means every run re-downloads the whole buildscript classpath, so this workflow
# is maximally exposed, and it already retries the NDK install five times and
# the CLD2 clone three times for the same reason. Gradle was the one step with
# no retry at all.
#
# IT RETRIES ONLY A TRANSIENT FAILURE. A real compile error exits on the FIRST
# attempt, so a genuine break still fails fast and names its line rather than
# costing three times the wall clock and printing the same error three times.
set -u
set -o pipefail

TRANSIENT='Could not (find|resolve|GET|HEAD|download)|Connection reset|Read timed out|connect timed out|Premature end of Content-Length|502 Bad Gateway|503 Service|504 Gateway'
LOG=${EV_GRADLE_LOG:-/tmp/ev-gradle.log}
GRADLE=${EV_GRADLE:-gradle}

n=0
until [ "$n" -ge 3 ]; do
  if "$GRADLE" "$@" 2>&1 | tee "$LOG"; then exit 0; fi
  if grep -qE "$TRANSIENT" "$LOG"; then
    n=$((n + 1))
    echo "::warning::Gradle hit a transient repository failure (attempt $n of 3); retrying"
    [ "$n" -lt 3 ] && sleep $((n * 20))
  else
    echo "::error::Gradle failed for a real reason -- not retrying."
    exit 1
  fi
done
echo "::error::Gradle still failing after 3 attempts; the last log is above."
exit 1
