#!/usr/bin/env bash
# Type-check the Kotlin with a real android.jar and diff the errors against a
# baseline commit, so only NEW errors show up.
#
#   tools/check/kotlin-typecheck.sh [baseline-ref]     (default: HEAD)
#
# WHY THE DIFF. The jar is API 15 from Maven Central, because Google Maven is
# unreachable from some environments, so androidx, Material and Compose are all
# unresolvable and every run reports over a thousand errors. That number is
# meaningless on its own. What matters is whether an error text appears now that
# did not appear at the baseline.
#
# WHY THE JAR AT ALL. Without it kotlinc treats every android.* type as
# unresolved and therefore checks NOTHING at those call sites. That is exactly
# how a wrong trailing lambda -- buildModesTabView(ctx, prefs) { testTts } binding
# to a newly added last parameter instead of testTtsProvider -- once reached CI.
# Negative-tested both ways: invisible without the jar, caught immediately with it.
#
# The baseline may be any commit, including one from before the source was
# checked in (2026-08-26): grab() falls back to ci/generate.py, and then to the
# generator inlined in build.yml, so old commits still work.
set -euo pipefail

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
CACHE="$ROOT/tools/.cache"
WORK=${EV_WORK:-${TMPDIR:-/tmp}/ev-typecheck}
BASE=${1:-HEAD}
JAR="$CACHE/android.jar"
KOTLINC=${KOTLINC:-$CACHE/kotlinc/bin/kotlinc}
command -v "$KOTLINC" >/dev/null 2>&1 || KOTLINC=$(command -v kotlinc || true)

[ -f "$JAR" ] || { echo "no $JAR -- run tools/bootstrap.sh"; exit 2; }
[ -n "$KOTLINC" ] && [ -x "$KOTLINC" ] || { echo "no kotlinc -- run tools/bootstrap.sh"; exit 2; }

PKG=app/src/main/java/com/tts/easyvoice

# grab <ref|WORKTREE> <destdir> -> populates <destdir>/app/src/...
grab() {
  local ref="$1" dest="$2"
  mkdir -p "$dest"
  if [ "$ref" = WORKTREE ]; then
    mkdir -p "$dest/$PKG"
    cp "$ROOT/$PKG"/*.kt "$dest/$PKG/"
    return
  fi
  if (cd "$ROOT" && git cat-file -e "$ref:$PKG/EasyVoiceTtsService.kt" 2>/dev/null); then
    (cd "$ROOT" && git archive "$ref" "$PKG") | tar -x -C "$dest"
    return
  fi
  # Before 2026-08-26 there were no source files: the tree was written out by a
  # generator, which itself moved out of build.yml on 2026-08-21.
  if (cd "$ROOT" && git cat-file -e "$ref:ci/generate.py" 2>/dev/null); then
    (cd "$ROOT" && git show "$ref:ci/generate.py") > "$dest/gen.py"
  else
    (cd "$ROOT" && git show "$ref:.github/workflows/build.yml") > "$dest/build.yml"
    python3 - "$dest/build.yml" "$dest/gen.py" <<'EOF'
import sys
lines = open(sys.argv[1], encoding='utf-8').read().split('\n')
a = b = None
for i, l in enumerate(lines):
    if l.strip() == "python3 << 'PYEOF'":
        a = i + 1
    if l.strip() == 'PYEOF':
        b = i
        break
assert a is not None and b is not None, 'no inline generator at this ref'
body = [l[10:] if l.startswith(' ' * 10) else l for l in lines[a:b]]
open(sys.argv[2], 'w', encoding='utf-8').write('\n'.join(body) + '\n')
EOF
  fi
  (cd "$dest" && python3 gen.py >/dev/null)
}

errs() {
  (cd "$1" && "$KOTLINC" -cp "$JAR" "$PKG"/*.kt -nowarn -d "$WORK/out" 2>&1 \
      | grep ": error: " > "$2") || true
  echo "$(wc -l < "$2") errors"
}

rm -rf "$WORK"
mkdir -p "$WORK/base" "$WORK/cur"
grab "$BASE" "$WORK/base"
grab WORKTREE "$WORK/cur"

echo -n "baseline ($BASE): "; errs "$WORK/base" "$WORK/base.txt"
echo -n "current:          "; errs "$WORK/cur"  "$WORK/cur.txt"

echo
echo "=== NEW error texts (current minus baseline) ==="
comm -13 <(sed 's/:[0-9]*:[0-9]*: error: /: /' "$WORK/base.txt" | sort -u) \
         <(sed 's/:[0-9]*:[0-9]*: error: /: /' "$WORK/cur.txt"  | sort -u)
echo "=== end ==="
echo

# Absolute counts are meaningless; a MOVE in the count of unresolved references
# to OUR OWN names is not.
#
# BOTH compiler formats are matched on purpose. K1 wrote
#     unresolved reference: androidx
# and K2 writes
#     unresolved reference 'androidx'.
# so the K1-only pattern silently matched NOTHING once the compiler moved to
# 2.4.10, and this metric read 0 where it had read 9 -- a checker going blind
# dressed up as an improvement. Caught on 2026-08-27 by asking why the number
# had improved rather than being pleased that it had.
own() { grep -cE "unresolved reference:? '?(build|apply|voice|lang|Easy|Lang|Voice|Mode|Setting|Advanced|Configuration|Section|Labeled|Responsive)" "$1" || true; }
b=$(own "$WORK/base.txt"); c=$(own "$WORK/cur.txt")

# The metric must be able to see SOMETHING. androidx is unresolvable in this
# container -- Google Maven is blocked -- so a run that reports unresolved
# references at all but matches none of ours means the pattern has stopped
# matching the compiler's wording, not that the code got better. That is
# precisely how this went blind when kotlinc moved from 1.9.22 to 2.4.10.
if grep -q "unresolved reference" "$WORK/base.txt" && [ "$b" = 0 ]; then
  echo "the our-own-name pattern matched NOTHING while the compiler reported"
  echo "unresolved references -- its wording has changed again. Fix own()."
  grep -m3 "unresolved reference" "$WORK/base.txt"
  exit 1
fi
if [ "$b" = "$c" ]; then
  echo "our-own-name unresolved refs: baseline $b, current $c (unchanged)"
else
  echo "our-own-name unresolved refs: baseline $b, current $c  <-- CHANGED, look at these"
  exit 1
fi
