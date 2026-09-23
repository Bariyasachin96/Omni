#!/usr/bin/env bash
# Say WHY the run went red, in one line, before anyone reads a thousand log lines.
#
#   bash tools/ci/why-failed.sh            (from an `if: failure()` step)
#
# THE PROBLEM THIS SOLVES. Every failure this pipeline has had since run 795 has
# been the accessibility job, and NOT ONE of them was a real accessibility
# regression:
#
#   795-797  the emulator could not make its userdata partition (a FATAL line
#            buried a thousand adb errors above the noise)
#   849-857  a genuine dependency conflict between the app and androidTest graphs
#   866      a transient repository failure -- the same run's build job resolved
#            the identical classpath and published the APK
#   868-869  set -o pipefail in a script the emulator action runs with dash
#
# All four looked identical from the outside: one red X. Twice that cost a wrong
# diagnosis and a reverted-for-nothing change. This reads what actually happened
# and says which KIND it was, so the next person starts from the answer.
#
# It never fails the run -- the step that already failed did that. This only
# explains.
set -u
LOG=${EV_GRADLE_LOG:-/tmp/ev-gradle.log}

say() { echo "::warning::$1"; echo; echo "=============================================================="; echo "  $1"; echo "=============================================================="; }

if [ ! -s "$LOG" ]; then
  say "GRADLE NEVER RAN. The failure is BEFORE it -- a setup step, the shell that
  runs the emulator action's script, the SDK install, or the emulator itself.
  Read the step that is marked failed, not the Gradle output; there is none."
  exit 0
fi

if grep -qE 'Could not (find|resolve|GET|HEAD|download)|Connection reset|Read timed out|connect timed out|502 Bad Gateway|503 Service|504 Gateway' "$LOG"; then
  say "INFRASTRUCTURE: a REPOSITORY failure, not your code.
  Check whether the OTHER job in this same run resolved the same classpath --
  if it did, this is a flake and re-running the failed job is the fix.
  tools/ci/gradle-retry.sh already retried it three times."
  grep -m 5 -E 'Could not (find|resolve|GET|HEAD|download)' "$LOG" || true
  exit 0
fi

if grep -qE 'AccessibilityViewCheckException|AccessibilityCheck|accessibility check|ContrastCheck|SpeakableTextPresentCheck|TouchTargetSizeCheck|DuplicateSpeakableTextCheck' "$LOG"; then
  say "REAL: an ACCESSIBILITY CHECK FAILED. This is a defect in the app, not the
  pipeline. Do NOT add continue-on-error. The uploaded HTML report names the
  exact node and the exact check."
  grep -m 20 -E 'AccessibilityViewCheckException|Check|at com.sachinbaria.easyvoice' "$LOG" || true
  exit 0
fi

if grep -qE '^e: |compileDebugKotlin FAILED|compileReleaseKotlin FAILED|Unresolved reference|error: ' "$LOG"; then
  say "REAL: a COMPILE error. Fix the line it names."
  grep -m 20 -E '^e: |error: ' "$LOG" || true
  exit 0
fi

if grep -qE 'device offline|emulator-5554. not found|Timeout waiting for emulator|INSTALL_FAILED|Not enough space|FATAL' "$LOG"; then
  say "INFRASTRUCTURE: the EMULATOR or the device. Grep the FULL job log for
  'FATAL' before reading a single adb line -- runs 795-797 were a disk-space
  FATAL wearing a KVM costume."
  grep -m 10 -E 'FATAL|Not enough space|Timeout waiting' "$LOG" || true
  exit 0
fi

say "UNCLASSIFIED. tools/ci/why-failed.sh does not recognise this one; the last
  40 lines of the Gradle log are below. If it turns out to be a KIND that can
  happen again, add its signature here."
tail -40 "$LOG"
