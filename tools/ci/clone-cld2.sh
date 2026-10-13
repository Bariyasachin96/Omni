#!/usr/bin/env bash
# Clone CLD2, the language detector the native core links, into
# app/src/main/cpp/cld2_src (gitignored; the build never checks it in). One
# script for all three CI jobs.
#
# Run 738 sat on this for 33 minutes -- it normally takes 45 seconds -- because
# a clone hung with no timeout to kill it. Each try gets its own timeout, and
# the step fails fast instead of burning the job's budget.
set -u
ROOT=$(cd "$(dirname "$0")/../.." && pwd)
DEST="$ROOT/app/src/main/cpp/cld2_src"
mkdir -p "$ROOT/app/src/main/cpp"
n=0
until [ "$n" -ge 3 ]; do
  rm -rf "$DEST"
  if timeout 240 git clone --depth 1 https://github.com/CLD2Owners/cld2.git "$DEST"; then exit 0; fi
  n=$((n + 1)); echo "clone attempt $n failed, trying again"; sleep $((n * 5))
done
echo "::error::could not clone CLD2"
exit 1
