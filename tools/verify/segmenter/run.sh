#!/usr/bin/env bash
# Prove our segmenter equals AutoTTS's, by running both over the same cases.
#
#   tools/verify/segmenter/run.sh
#
# WHAT IS COMPARED. AutoTTS's c3/d0.java `t()` is the function that turns an
# utterance into typed chunks: it splits Latin from non-Latin, then splits those
# by number, punctuation and emoji, resolves each of those three against its
# mode setting, and merges. Ours is buildMixChunks in
# app/src/main/cpp/tts_engine_core.cpp. Reading them side by side is how the
# earlier bugs were MISSED; running them side by side is how the last one was
# found.
#
# Both harnesses read the same TSV on stdin and print one line per case:
#
#     type:'text' | type:'text' | ...
#
# so `diff` is the entire test. The TSV columns are
#
#     mode numMode puncMode emojiMode inFlow smart group dualLang mixNonLat
#     deviceIso3 hints text
#
# java/ holds c3/d0.java and its dependencies lifted out of
# autotts_reference/v5.7.7.26/decompiled_java/. See java/README for the list of
# methods that had to be re-transcribed because CFR's output does not compile.
#
# Needs: javac, g++, python3. No network.
set -euo pipefail

HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-segmenter

rm -rf "$WORK"
mkdir -p "$WORK/java"

echo "1/4  generating cases"
python3 "$HERE/gen_cases.py" > "$WORK/cases.tsv"
echo "     $(wc -l < "$WORK/cases.tsv") cases"

echo "2/4  building the AutoTTS reference (javac)"
cp "$HERE"/java/*.java "$WORK/java/"
(cd "$WORK/java" && javac -encoding UTF-8 -nowarn *.java 2>&1 | grep -vE "^(Note:|Picked up)" || true)

echo "3/4  building our segmenter (g++), from the current tree"
python3 "$ROOT/tools/verify/make_core_inc.py" \
        --source "$ROOT/app/src/main/cpp/tts_engine_core.cpp" \
        --until buildMixChunks --out "$WORK/core.inc" >/dev/null
cp "$HERE/cpp/diffmain.cpp" "$WORK/"
# EV_CXXFLAGS lets this harness be re-run under the APP's real optimisation
# flags. CLAUDE.md records the limitation this closes: the harnesses normally
# compile at -O1/-O2 under g++, so they cannot certify that a CODEGEN flag is
# behaviour-neutral. Before shipping -flto / -Oz the segmenter was re-run with
#   EV_CXXFLAGS="-Oz -flto -fno-exceptions -fno-rtti" tools/verify/segmenter/run.sh
# and stayed IDENTICAL over all 163,296 cases.
(cd "$WORK" && g++ ${EV_CXXFLAGS:--O1} -std=c++17 -I. -o diffharness diffmain.cpp)

echo "4/4  running both"
java -cp "$WORK/java" Main < "$WORK/cases.tsv" 2>/dev/null | grep -v "^Picked up" > "$WORK/autotts.out"
"$WORK/diffharness" < "$WORK/cases.tsv" > "$WORK/ours.out"

if diff -q "$WORK/autotts.out" "$WORK/ours.out" >/dev/null; then
  echo
  echo "IDENTICAL over $(wc -l < "$WORK/cases.tsv") cases"
  exit 0
fi

echo
echo "DIFFERENCES: $(diff "$WORK/autotts.out" "$WORK/ours.out" | grep -c '^<') lines"
echo "first few, with the case that produced each:"
python3 - "$WORK/cases.tsv" "$WORK/autotts.out" "$WORK/ours.out" <<'EOF'
import sys
cases = open(sys.argv[1], encoding='utf-8').read().split('\n')
a = open(sys.argv[2], encoding='utf-8').read().split('\n')
b = open(sys.argv[3], encoding='utf-8').read().split('\n')
shown = 0
for case, autotts, ours in zip(cases, a, b):
    if autotts == ours:
        continue
    f = case.split('\t')
    print('\n  mode=%s num=%s punc=%s emoji=%s inFlow=%s smart=%s group=%s '
          'dual=%s mixNonLat=%s device=%s hints=%s' % tuple(f[:11]))
    print('  text    %r' % f[11])
    print('  AutoTTS %s' % autotts)
    print('  ours    %s' % ours)
    shown += 1
    if shown == 5:
        break
EOF
echo
echo "work kept in $WORK"
exit 1
