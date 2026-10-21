#!/usr/bin/env bash
# Clone CLD2 into tools/.cache/cld2 -- ONLY to build the core of a commit from
# before 2026-10-21, when CLD2 left the app (owner: "CLD2 hata do"). The app
# itself no longer needs it; the harnesses that compare the working tree with
# an older ref (corebytes, langsuite's "today with CLD2" column ...) do, and
# tools/verify/latency/sources.py points ${CLD2_DIR} of such a ref here.
#
# Run 738 once sat on a hung clone for 33 minutes, so each try has its own
# timeout and the script gives up after three.
set -u
ROOT=$(cd "$(dirname "$0")/../.." && pwd)
DEST="$ROOT/tools/.cache/cld2"
[ -d "$DEST/internal" ] && exit 0
mkdir -p "$ROOT/tools/.cache"
n=0
until [ "$n" -ge 3 ]; do
  rm -rf "$DEST"
  if timeout 240 git clone --depth 1 https://github.com/CLD2Owners/cld2.git "$DEST"; then exit 0; fi
  n=$((n + 1)); echo "clone attempt $n failed, trying again"; sleep $((n * 5))
done
echo "could not clone CLD2" >&2
exit 1
