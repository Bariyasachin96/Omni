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
#
# There is NO exemption any more. "Show selected" used to be carved out here:
# the Languages filter chip's visible label contained the state, and WCAG 2.5.3
# Label in Name meant it could not be fixed by editing the contentDescription
# alone. On 2026-09-02 the owner asked for the accessibility attributes to be
# put right everywhere, so the visible label and the name became "My languages"
# together and the carve-out went with them. Do not add another one: if a label
# genuinely has to carry a state word, that is a UI wording decision and belongs
# to the owner, not to a grep -v.
names=$(grep -rh "contentDescription *=\|stateDescription *=" $KT |
        sed 's://.*::' |
        grep -oE '"[^"]*"' |
        grep -iE '\b(button|checkbox|dropdown|radio|slider|tab)\b|\b(checked|unchecked|selected|unselected)\b' |
        sort -u || true)
[ -z "$names" ] \
  && ok "#6  no accessible name carries its own role word or state" \
  || bad "#6  accessible name carries a role word or state: $names"

# --- 8. no lazy list inside a DropdownMenu ---------------------------------
# Comments are stripped: VoiceScreen carries a banner that says in words why a
# LazyColumn must never come back here, and that is not a violation.
n=$(sed 's://.*::' $KT/*.kt | grep -A 25 "DropdownMenu(" | grep -c "LazyColumn\|LazyRow")
[ "$n" = 0 ] && ok "#8  no lazy list inside a DropdownMenu" \
             || bad "#8  a lazy list inside a DropdownMenu ($n) -- it cannot answer intrinsics and throws"

# --- 18. a heading is a plain Text; never give a Text a contentDescription ---
# The documented shape is exactly `Text(..., Modifier.semantics { heading() })`
# -- it is the example on developer.android.com's semantics page. A Text is a
# LEAF, so the delegate already sets info.text from it and it is already
# focusable; merging it buys nothing.
#
# contentDescription must not go on it. The api-defaults page: it "is mainly
# meant to be used for graphic elements, such as images. Material components,
# like Button or Text ... come with other predefined semantics" -- and in the
# View API contentDescription OVERRIDES the text, so on a Text it can only
# replace a label that was already correct.
#
# The opposite case is INVARIANTS #7 and is not this: a node that merges
# descendants AND has children has its contentDescription moved to a fake leaf
# child and its info.text taken from an empty unmerged config, so there the
# explicit name is required. Both were got wrong here on 2026-08-27, in the same
# change, in opposite directions.
n=$(sed 's://.*::' $KT/*.kt | python3 -c "
import re,sys
text = sys.stdin.read()
bad = 0
for m in re.finditer(r'semantics\s*(\([^)]*\))?\s*\{', text):
    depth, at = 0, m.end() - 1
    while at < len(text):
        if text[at] == '{': depth += 1
        elif text[at] == '}':
            depth -= 1
            if depth == 0: break
        at += 1
    body = text[m.end():at]
    if 'heading()' in body and 'contentDescription' in body:
        bad += 1
print(bad)
")
[ "$n" = 0 ] && ok "#18 headings are plain Texts, with no contentDescription" \
             || bad "#18 a heading also sets contentDescription ($n) -- a Text is a leaf and already named; that only overrides its own text"

# --- 17. an activity that PERSISTS must also LOAD ---------------------------
# AutoTTS has one settings screen and it does both (onCreate loads, onPause ->
# n.t persists). We split it into four activities that all persist, and Android
# restores only the TOP activity of a task after the process is killed -- so any
# of them can come back with statics at their declared defaults and write those
# over the user's real settings. Loading must be guarded, not unconditional:
# reloading over a live edit is its own bug. LangStore.ensureLoaded does both.
# Comments are stripped and matches are COUNTED, not tested by exit code. The
# first version of this check did neither and the selftest caught it asleep: the
# line above each call says "see LangStore.ensureLoaded", so deleting the call
# left the comment matching and the check still reported ok. Same trap as #6.
missing=""
for f in $(grep -rln "LangStore.persistAll" $KT); do
  case "$f" in */LangStore.kt|*/EngineFinder.kt) continue ;;   # not activities
  esac
  n=$(sed 's://.*::' "$f" | grep -c "LangStore\.ensureLoaded\|LangStore\.loadMode(")
  [ "$n" = 0 ] && missing="$missing $(basename $f)"
done
[ -z "$missing" ] && ok "#17 every activity that persists also loads first" \
                  || bad "#17 persists in onPause but never loads:$missing -- a fresh process would write defaults over real settings"

# --- 16. there is ONE detector, and it must stay that way ------------------
# CLD3 was removed on 2026-09-02 at the owner's instruction. Its arm had grown
# three guards of its own -- a reliability flag, a wrong-script rejection and a
# squeeze gate -- and all three are gone with it, so this checks that none of it
# comes back by accident: no CLD3 symbol, no second detector, no useCld3 flag.
# See CLAUDE.md, "CLD3 IS GONE", for why, and for the measurements that decided
# it. Comments are stripped, because that section quotes these very names.
strays=$(sed 's://.*::' $CPP $KT/*.kt |
         grep -oE '\b(cld3[A-Za-z]*|useCld3[A-Za-z]*|NNetLanguageIdentifier|isRomanisedTag)\b' |
         sort -u || true)
[ -z "$strays" ] \
  && ok "#16 CLD2 is the only detector -- no CLD3 symbol anywhere" \
  || bad "#16 CLD3 has come back: $strays"

# --- 14. a log tag is EasyVoiceLogger.TAG, or "TTS" at AutoTTS's six sites --
# AutoTTS logs under exactly two tags: "AutoTTS" everywhere (133 calls) and
# "TTS" at six -- the three audio-focus lines, the focus request, and the two
# notification-permission lines. EasyVoiceLogger.TAG is our "AutoTTS". Any
# OTHER literal is a mistake: `debug("TAG", ...)` shipped in localeFor and
# variantFor, which put the string "TAG" in the log file the owner attaches
# when reporting a problem. Comments are stripped so prose about tags is fine.
badtags=$(sed 's://.*::' $KT/*.kt |
          grep -oE 'EasyVoiceLogger\.[a-z]+\("[^"]*"' |
          grep -oE '"[^"]*"$' | grep -v '^"TTS"$' | sort -u || true)
[ -z "$badtags" ] \
  && ok "#14 every log tag is EasyVoiceLogger.TAG or AutoTTS's \"TTS\"" \
  || bad "#14 log call with a stray tag literal: $badtags"

# --- 12. the workflow file must stay far under 512,000 bytes ---------------
size=$(wc -c < .github/workflows/build.yml)
[ "$size" -lt 400000 ] && ok "#12 build.yml is $size bytes" \
                       || bad "#12 build.yml is $size bytes -- at 512,000 a run is never parsed"

# --- the detector's build inputs stay CLD2-only -----------------------------
# CMakeLists carried nineteen CLD3 sources, three generated protobufs and the
# whole protobuf runtime; build.yml cloned cld3 and protobuf and ran protoc.
# All of it went with the detector. If any of those names reappear the APK has
# grown a dependency nobody asked for.
# Comment lines are stripped first in BOTH files -- CMakeLists and build.yml
# each carry a note saying these names must not come back, and a check that
# fires on its own warning is a check nobody keeps.
buildstray=$(cat app/src/main/cpp/CMakeLists.txt .github/workflows/build.yml |
             sed 's:#.*::' |
             grep -oiE 'cld3|protobuf|protoc' | sort -u || true)
[ -z "$buildstray" ] \
  && ok "no CLD3 or protobuf input in CMakeLists or build.yml" \
  || bad "a CLD3/protobuf build input is back: $buildstray"
echo
echo "not checkable by grep -- read docs/INVARIANTS.md #7, #9, #10, #11, #13"
[ "$fail" = 0 ] && echo "ALL MECHANICAL INVARIANTS HOLD" || echo "SOMETHING IS BROKEN"
exit $fail
