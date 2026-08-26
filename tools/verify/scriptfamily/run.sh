#!/usr/bin/env bash
# Prove our script-family fallback equals AutoTTS's, over every Unicode code
# point, for many enabled-language sets.
#
#   tools/verify/scriptfamily/run.sh [extra-language-set ...]
#
# WHAT IS COMPARED. AutoTTS's com/vnspeak/autotts/a.java answers "given this code
# point and the languages the user has enabled, which language is this?". It is
# what clsCLD2.d falls back to whenever the detector's answer is not an enabled
# language, so in auto and Google mode it picks the voice more often than the
# detector itself does. Ours is familyLangForCpFiltered / familyForCp in
# app/src/main/cpp/tts_engine_core.cpp.
#
# Two comparisons run, over all 1,114,112 code points each:
#   filtered    the resolved language, for every language set below
#   unfiltered  the whole family -- primary AND members IN ITERATION ORDER
#
# Iteration order is part of the behaviour: a.f and clsCLD2.d walk the member
# set and take the first enabled one, so a different HashSet order is a
# different voice.
#
# THE TRAP THIS SCRIPT AVOIDS. Android's libcore keeps the classic
# HashSet(Collection) constructor,
#     map = new HashMap<>(Math.max((int)(c.size()/.75f) + 1, 16));
# while JDK 19 replaced it with HashMap.newHashMap(c.size()). For a 12-element
# family that is table 32 on Android and 16 on JDK 19+, and the orders differ --
# which is exactly what these two comparisons measure. java/ScriptFam.java
# therefore routes every such construction through its own androidSet() helper.
# ANY future Java harness that compares collection order must do the same.
#
# Needs: javac, g++, python3. No network.
set -euo pipefail

HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-scriptfamily

SETS=(
  "en" "hi" "ar" "ru,uk" "zh,ja,ko"
  "en,es,fr,de,it,pt,nl,sv,no,da,fi,pl,cs,sk,hu,ro,tr,id,ms,vi,tl,sw,hr,sr,sl,et,lv,lt,is,ga,cy,sq,mt"
  "fa,ur,ps,sd,ku,ckb,az,ks,ug,ha,ar"
  "bn,as,mni"
  "ta,kn,te,ml,si,pa,gu,or,tcy"
  "th,lo,bo,my,km,nod,hnx,dz,shn"
  "he,yi,lad,hy,ka,am,ti,xmf"
  "dv" "mn,mnc,xal" "en,hi"
  "en,hi,ar,zh,ru,bn,ta,th,ko,ja,he,el,ka,hy,am,my,km,si,gu,pa,or,te,kn,ml,bo,lo,mn,dv"
)
SETS+=("$@")

rm -rf "$WORK"
mkdir -p "$WORK/java"

echo "1/3  building the AutoTTS reference (javac)"
cp "$HERE"/java/*.java "$WORK/java/"
(cd "$WORK/java" && javac -encoding UTF-8 -nowarn *.java 2>&1 | grep -vE "^(Note:|Picked up)" || true)

echo "2/3  building ours (g++), from the current tree"
python3 "$ROOT/tools/verify/make_core_inc.py" \
        --source "$ROOT/app/src/main/cpp/tts_engine_core.cpp" \
        --until scriptLangForCpFiltered --out "$WORK/core2.inc" >/dev/null
cp "$HERE"/cpp/main.cpp "$HERE"/cpp/main2.cpp "$WORK/"
(cd "$WORK" && g++ -O2 -std=c++17 -I. -o filtered main.cpp && g++ -O2 -std=c++17 -I. -o unfiltered main2.cpp)

echo "3/3  sweeping all 1,114,112 code points"
fail=0

java -cp "$WORK/java" Fam 2>/dev/null | grep -v "^Picked up" > "$WORK/a.out"
"$WORK/unfiltered" > "$WORK/b.out"
if diff -q "$WORK/a.out" "$WORK/b.out" >/dev/null; then
  echo "     unfiltered families (primary + members in order): identical"
else
  echo "     unfiltered families: DIFFER"
  diff "$WORK/a.out" "$WORK/b.out" | head -8
  fail=1
fi

for set in "${SETS[@]}"; do
  java -cp "$WORK/java" Main "$set" 2>/dev/null | grep -v "^Picked up" > "$WORK/a.out"
  "$WORK/filtered" "$set" > "$WORK/b.out"
  if ! diff -q "$WORK/a.out" "$WORK/b.out" >/dev/null; then
    echo "     DIFFER for {$set}"
    diff "$WORK/a.out" "$WORK/b.out" | head -6
    fail=1
  fi
done

echo
if [ "$fail" = 0 ]; then
  echo "IDENTICAL over ${#SETS[@]} language sets x 1,114,112 code points"
else
  echo "work kept in $WORK"
fi
exit $fail
