#!/usr/bin/env bash
# Check the mechanically-checkable rules in docs/INVARIANTS.md.
#
#   tools/check/invariants.sh
#
# Every rule in that document is there because breaking it produced a bug that
# took days to find. Most of them are one grep. This runs those, so "did I break
# one of the rules" is a question with an answer instead of a memory test.
#
# Rules that need judgement rather than a grep (#7 merged Compose nodes, #10 the
# ripple, #11 not deleting an announcement, #13 the copied quirks) are listed at
# the end as a reminder, not checked.
set -u
# NOT pipefail. `something | grep -q x` exits as soon as grep finds a match,
# the upstream gets SIGPIPE and exits 141, and with pipefail THAT becomes the
# pipeline's status -- so a check would report "ok" precisely when it had found
# a violation. Every check here therefore counts matches instead of testing an
# exit code, and the negative tests below the script prove each one fires.

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
cd "$ROOT"
KT=app/src/main/java/com/tts/easyvoice
SERVICE=$KT/EasyVoiceTtsService.kt
CPP=app/src/main/cpp/tts_engine_core.cpp

fail=0
ok()   { printf '  ok    %s\n' "$1"; }
bad()  { printf '  FAIL  %s\n' "$1"; fail=1; }

echo "invariants (docs/INVARIANTS.md)"

# --- 1. every language-list rebuild pushes the language sets ---------------
rebuilds=$(grep -rln "languages.addAll" $KT | sort)
missing=""
for f in $rebuilds; do
  case "$f" in
    */LangStore.kt) continue ;;   # loadLanguages; both its callers push
  esac
  grep -q "pushLanguageSets()" "$f" || missing="$missing $f"
done
grep -q "pushLanguageSets()" $SERVICE || missing="$missing $SERVICE"
[ -z "$missing" ] && ok "#1  every rebuild site pushes the language sets" \
                  || bad "#1  rebuild without pushLanguageSets():$missing"

# --- 2. hints only on a rebuild, never per utterance -----------------------
n=$(awk '/\/\/  SYNTHESIS /,/\/\/  SHUTDOWN/' $SERVICE | grep -c "pushLanguageSets")
[ "$n" = 0 ] && ok "#2  the synthesis path pushes detect sets only" \
             || bad "#2  pushLanguageSets called from the synthesis path ($n times) -- must be pushDetectSetsOnly"

# --- 3. never hold LangStore.languages while taking another lock -----------
# Brace-aware, because a flat window cannot tell "inside the block" from "three
# lines after it" -- and the correct shape is exactly the latter.
if python3 tools/check/nested_locks.py; then
  ok "#3  no lock nested inside the language-list monitor"
else
  bad "#3  a lock nested inside the language-list monitor -- ABBA deadlock"
fi

# --- 4. no runtime preference read on the synthesis path -------------------
stray=$(awk '/\/\/  SYNTHESIS /,/\/\/  SHUTDOWN/' $SERVICE |
        grep -o "prefs\.[a-zA-Z]*" | sort -u | grep -v "^prefs\.toIso" || true)
[ -z "$stray" ] && ok "#4  synthesis path reads statics only" \
                || bad "#4  runtime preference read on the synthesis path: $stray"

# --- 5. announceForAccessibility is banned --------------------------------
n=$(grep -rc "announceForAccessibility" $KT | awk -F: '{s+=$2} END{print s+0}')
[ "$n" = 0 ] && ok "#5  no announceForAccessibility" \
             || bad "#5  announceForAccessibility is deprecated and banned ($n)"

# --- 6. an ACCESSIBLE NAME must not contain its own role word or its state --
# Only accessibility names count. Ordinary prose on screen may of course say
# "no voice is selected"; what must not happen is a contentDescription that
# names the role the service already appends, or that puts state in the name.
# Comments are stripped first -- several of them quote the very strings this
# looks for, because that is where the rule was written down.
names=$(grep -rh "contentDescription *=\|stateDescription *=" $KT |
        sed 's://.*::' |
        grep -oE '"[^"]*"' |
        grep -iE '\b(button|checkbox|dropdown|radio|slider|tab)\b|\b(checked|unchecked|selected|unselected)\b' |
        grep -v "Show selected" | sort -u || true)
[ -z "$names" ] \
  && ok "#6  no accessible name carries its own role word or state" \
  || bad "#6  accessible name carries a role word or state: $names"

# --- 8. no lazy list inside a DropdownMenu ---------------------------------
# Comments are stripped: VoiceScreen carries a banner that says in words why a
# LazyColumn must never come back here, and that is not a violation.
n=$(sed 's://.*::' $KT/*.kt | grep -A 25 "DropdownMenu(" | grep -c "LazyColumn\|LazyRow")
[ "$n" = 0 ] && ok "#8  no lazy list inside a DropdownMenu" \
             || bad "#8  a lazy list inside a DropdownMenu ($n) -- it cannot answer intrinsics and throws"

# --- 12. the workflow file must stay far under 512,000 bytes ---------------
size=$(wc -c < .github/workflows/build.yml)
[ "$size" -lt 400000 ] && ok "#12 build.yml is $size bytes" \
                       || bad "#12 build.yml is $size bytes -- at 512,000 a run is never parsed"

# --- CLD3 parity: wherever CLD2 detects, CLD3 must be able to ---------------
cld2=$(grep -c "CLD2::DetectLanguageSummaryV2\|CLD2::ExtDetectLanguageSummary" $CPP)
cld3=$(grep -c "cld3DetectRaw(.*, *\(true\|false\))" $CPP)
[ "$cld2" = "$cld3" ] && ok "CLD3 parity: $cld2 CLD2 detection sites, $cld3 CLD3 arms" \
                      || bad "CLD3 parity: $cld2 CLD2 detection sites but $cld3 CLD3 arms"

flagged=$(grep -c "useCld3Flag" $SERVICE)
[ "$flagged" -ge 6 ] && ok "CLD3 flag reaches all $flagged native call sites" \
                     || bad "CLD3 flag reaches only $flagged call sites"

echo
echo "not checkable by grep -- read docs/INVARIANTS.md #7, #9, #10, #11, #13"
[ "$fail" = 0 ] && echo "ALL MECHANICAL INVARIANTS HOLD" || echo "SOMETHING IS BROKEN"
exit $fail
