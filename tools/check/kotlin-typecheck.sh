#!/usr/bin/env bash
# Type-check the Kotlin against the real classpath and diff the errors against a
# baseline commit, so only NEW errors show up.
#
#   tools/check/kotlin-typecheck.sh [baseline-ref]     (default: HEAD)
#
# WHY THE CLASSPATH AT ALL. kotlinc can only check a call site whose types it can
# resolve. Give it nothing and it treats every android.* and androidx.* type as
# unresolved and therefore checks NOTHING there. Both halves have already cost a
# CI run: a wrong trailing lambda -- buildModesTabView(ctx, prefs) { testTts }
# binding to a newly added last parameter instead of testTtsProvider -- reached CI
# because android.jar was absent, and a missing @ExperimentalMaterial3Api opt-in
# failed build 827 because androidx was.
#
# IT RUNS IN ONE OF TWO MODES AND SAYS WHICH.
#   FULL     android.jar is API 37 (the app's own compileSdk) and the androidx /
#            Compose / Material3 jars are on the classpath. Errors are real; the
#            count means something and should be at or near zero.
#   REDUCED  dl.google.com was unreachable when tools/bootstrap.sh ran, so
#            android.jar is API 15 from Maven Central and there is no androidx at
#            all. Over a thousand errors are reported and the number is
#            meaningless -- what matters is whether an error TEXT appears now that
#            did not appear at the baseline.
# The diff is printed in both modes, because it is the thing that is correct in
# both. Run tools/bootstrap.sh to move from REDUCED to FULL; if it stays REDUCED,
# that is the environment's egress policy and is not to be routed around.
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

# The Compose compiler plugin. It is what applies Compose's own rules -- a
# @Composable invoked from an ordinary function is legal Kotlin and illegal
# Compose, and without this the local check says nothing about it. It comes from
# Maven Central, so it is available in REDUCED mode too; it needs the androidx
# classpath to be USEFUL, but it is harmless without it.
PLUGIN=""
[ -f "$CACHE/compose-plugin.jar" ] && PLUGIN="-Xplugin=$CACHE/compose-plugin.jar"

# The androidx half. Absent means REDUCED mode, which is a supported way to run
# rather than a failure -- see the header.
CP="$JAR"
MODE=REDUCED
if [ -s "$CACHE/androidx-classpath.txt" ]; then
  CP="$JAR:$(tr '\n' ':' < "$CACHE/androidx-classpath.txt" | sed 's/:*$//')"
  MODE=FULL
fi
echo "compose plugin: $([ -n "$PLUGIN" ] && echo on || echo "OFF -- run tools/bootstrap.sh")"
echo "classpath: $MODE  ($(cat "$CACHE/android.jar.source" 2>/dev/null || echo 'android.jar source unknown')$([ "$MODE" = FULL ] && echo ", $(wc -l < "$CACHE/androidx-classpath.txt") androidx jars"))"

PKG=app/src/main/java/com/sachinbaria/easyvoice
OLD_PKG=app/src/main/java/com/tts/easyvoice
RES=app/src/main/res

# grab <ref|WORKTREE> <destdir> -> populates <destdir>/app/src/...
grab() {
  local ref="$1" dest="$2"
  mkdir -p "$dest"
  if [ "$ref" = WORKTREE ]; then
    mkdir -p "$dest/$PKG"
    cp "$ROOT/$PKG"/*.kt "$dest/$PKG/"
    mkdir -p "$dest/app/src/main"
    cp -r "$ROOT/$RES" "$dest/app/src/main/"
    return
  fi
  if (cd "$ROOT" && git cat-file -e "$ref:$PKG/EasyVoiceTtsService.kt" 2>/dev/null); then
    (cd "$ROOT" && git archive "$ref" "$PKG" "$RES") | tar -x -C "$dest"
    return
  fi
  # Before 2026-09-23 the package was com.tts.easyvoice. A baseline from then
  # is extracted from the old directory and moved to where $PKG says, so the
  # compile below finds it; its own `package` lines are left as they were.
  if (cd "$ROOT" && git cat-file -e "$ref:$OLD_PKG/EasyVoiceTtsService.kt" 2>/dev/null); then
    (cd "$ROOT" && git archive "$ref" "$OLD_PKG" "$RES") | tar -x -C "$dest"
    mkdir -p "$dest/$(dirname "$PKG")"
    mv "$dest/$OLD_PKG" "$dest/$PKG"
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
  # -jvm-target 17 matches compileOptions in app/build.gradle.kts. Without it
  # kotlinc defaults to 1.8 and answers every inline function in androidx with
  # "cannot inline bytecode built with JVM target 11" -- 218 errors that say
  # nothing about this code and drown the ones that do.
  (cd "$1" && "$KOTLINC" -cp "$CP" -jvm-target 17 $PLUGIN "$PKG"/*.kt -nowarn -d "$WORK/out" 2>&1 \
      | grep ": error: " > "$2") || true
  echo "$(wc -l < "$2") errors"
}

rm -rf "$WORK"
mkdir -p "$WORK/base" "$WORK/cur"
grab "$BASE" "$WORK/base"
grab WORKTREE "$WORK/cur"

# R is generated by AGP from res/ at build time, so it lives in no jar. Generate
# it here too, from EACH TREE'S OWN res/ -- taking it from the worktree for both
# would put a resource deleted since the baseline into the baseline's R, and an
# inflated baseline count is exactly what would hide a real new error.
genr() {
  if [ -d "$1/$RES" ]; then
    EV_ROOT="$1" python3 "$ROOT/tools/check/genr.py" "$1/$PKG/R.kt" >/dev/null
  else
    echo "note: no $RES at this ref -- R references will read as unresolved"
  fi
}
genr "$WORK/base"
genr "$WORK/cur"

echo -n "baseline ($BASE): "; errs "$WORK/base" "$WORK/base.txt"
echo -n "current:          "; errs "$WORK/cur"  "$WORK/cur.txt"

echo
echo "=== NEW error texts (current minus baseline) ==="
comm -13 <(sed 's/:[0-9]*:[0-9]*: error: /: /' "$WORK/base.txt" | sort -u) \
         <(sed 's/:[0-9]*:[0-9]*: error: /: /' "$WORK/cur.txt"  | sort -u) \
         | tee "$WORK/new.txt"
echo "=== end ==="
echo

# ---------------------------------------------------------------------------
# IS THE CHECKER ACTUALLY LOOKING? Two things are asserted rather than assumed,
# because a checker that goes quiet while looking healthier is the worst outcome
# this project has had -- see the K1/K2 note in the header.
#
# The metric that used to sit here counted unresolved references to OUR OWN
# names, on the reasoning that a file which failed to resolve cascades into
# them. That reasoning stopped being true the moment android.jar became real and
# R was generated: our own names now resolve even in REDUCED mode, the count
# reads a legitimate 0, and the guard built on it fired a FALSE failure saying
# the compiler's wording had changed. Replaced with two direct tests.

# 1. an error really is reported and really is caught by the grep in errs().
#    A deliberate type error, compiled against the same classpath. This one
#    applies in BOTH modes -- it is about the compiler and the grep, not about
#    whether this app's dependencies are present.
mkdir -p "$WORK/selftest"
cat > "$WORK/selftest/SelfTest.kt" <<'KT'
package evselftest
fun deliberatelyWrong(): Int = "this is not an Int"
KT
# NOT `grep -q`. Under `set -o pipefail` a -q grep exits on the first match,
# kotlinc takes SIGPIPE, and the pipeline reports 141 -- so the self-test failed
# while the compiler was doing exactly the right thing. Write and count, which is
# what errs() above already does and why it never had this bug.
("$KOTLINC" -cp "$CP" -jvm-target 17 $PLUGIN "$WORK/selftest/SelfTest.kt" \
    -nowarn -d "$WORK/selftest/out" 2>&1 | grep ": error: " > "$WORK/selftest.txt") || true
if [ ! -s "$WORK/selftest.txt" ]; then
  echo "the self-test file did NOT produce an error -- kotlinc is not reporting,"
  echo "or errs()'s grep no longer matches the compiler's wording. Fix this"
  echo "before trusting any run above."
  exit 1
fi
echo "self-test: kotlinc reports errors and errs() catches them."

# 2. kotlinc really compiled our sources, so an empty error list cannot mean "it
#    never saw them". FULL ONLY: in REDUCED androidx is unresolvable, compilation
#    legitimately produces nothing, and asserting otherwise would fail a mode that
#    is working exactly as designed.
#
#    The braces matter. `find` on a missing directory exits non-zero, and under
#    `set -o pipefail` that propagates out of the command substitution and `set -e`
#    kills the script -- silently, with no message, which is precisely how this
#    check first went wrong.
classes=$( { find "$WORK/out" -name '*.class' 2>/dev/null || true; } | wc -l )
if [ "$MODE" = FULL ] && [ "$classes" -lt 20 ]; then
  echo "kotlinc produced only $classes class files -- it did not compile the"
  echo "package. The error list above is empty for the wrong reason."
  exit 1
fi

# ---------------------------------------------------------------------------
tot_b=$(wc -l < "$WORK/base.txt"); tot_c=$(wc -l < "$WORK/cur.txt")
new=$(grep -c . "$WORK/new.txt" || true)
echo "total errors: baseline $tot_b, current $tot_c"

if [ "$MODE" = FULL ]; then
  # Everything the app calls is on the classpath and R is generated, so every
  # error here is real and the expected count is ZERO. Anything new fails.
  if [ "$new" -gt 0 ] || [ "$tot_c" -gt "$tot_b" ]; then
    echo "FAILED: $new new error text(s), and the count went $tot_b -> $tot_c."
    exit 1
  fi
  echo "PASSED: no new errors ($classes classes compiled)."
  exit 0
fi

# REDUCED is ADVISORY and deliberately never fails.
#
# androidx is unresolvable in this mode, so one added import produces a handful
# of errors that say nothing about whether the code is right. A rule that failed
# on that would cry wolf on every legitimate change -- and a check people learn
# to ignore is worse than no check. Read the NEW texts above with judgement, and
# run tools/bootstrap.sh to get FULL, which is the real check.
if [ "$new" -gt 0 ]; then
  echo "REDUCED mode: $new new error text(s) above. Most androidx-shaped ones are"
  echo "noise; read them rather than counting them. This mode does not fail."
else
  echo "REDUCED mode: no new error texts."
fi
exit 0
