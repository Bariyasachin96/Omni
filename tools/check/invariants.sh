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
KT=app/src/main/java/com/sachinbaria/easyvoice
SERVICE=$KT/EasyVoiceTtsService.kt
CPP=app/src/main/cpp/tts_engine_core.cpp

fail=0
ok()   { printf '  ok    %s\n' "$1"; }
bad()  { printf '  FAIL  %s\n' "$1"; fail=1; }

echo "invariants (docs/INVARIANTS.md)"

# --- 1. every language-list rebuild pushes the language sets ---------------
# BOTH SPELLINGS. Every rebuild site used to write `languages.addAll(...)`
# directly; since 2026-09-10 they all go through LangStore.replaceAll(), which
# is what puts the swap under the list's monitor. Matching only the old spelling
# left this check scanning NOTHING -- the one remaining addAll is inside
# replaceAll itself, in LangStore.kt, which the loop below skips -- and
# selftest.sh caught it as "#1 NOT CAUGHT". A refactor can blind a checker
# without touching it; that is what the selftest is for.
rebuilds=$(grep -rln "languages\.addAll\|LangStore\.replaceAll(" $KT | sort)
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

# --- 4b. the SETTINGS UI reads and writes the statics, never the preferences --
# The other half of #4, and the owner asked for it in so many words on
# 2026-09-02: "sab kuchh static rakho ... static wala bahut andar hi andar apply
# ho jata hai". It is AutoTTS's own design: c3.k writes AutoTtsService's fields
# directly and c3.n.v() persists them later, at onPause.
#
# Why it matters, from the bug that produced the rule: preferences hold what was
# last PERSISTED, the statics hold what the user has just CHOSEN, and the two
# only converge when persistAll runs. A screen that reads a flag back out of
# preferences therefore shows the value from before the current edit, and the
# service keeps speaking with the one the user has already changed.
#
# getReadingMode/setReadingMode are NOT preference accessors despite living on
# SharedPrefsManager -- they read and write EasyVoiceTtsService.modeInt -- so
# they are not listed. isLoggingEnabled is the one eager-persist flag (AutoTTS's
# c3.p does the same) and is seeded from MainActivity on purpose.
settings_readers='isStripAudioAttr|isForceAccessibilityStream|getPuncModeLang|getEmojiModeLang|getNumberModeLang|getPuncSpecificLang|getEmojiSpecificLang|getNumberSpecificLang|isShowNotification|isLocaleSpansEnabled|isDisableAdvancedDetection|isKeepAliveMode|isQuickCharacterReading|isPunctuationWithSentence|isSmartNumberReading|getSmartNumberGroupSize|isEngineFallback'
uiread=$(for f in $KT/*.kt; do
           case "$f" in
             */EasyVoiceTtsService.kt|*/SharedPrefsManager.kt) continue ;;
           esac
           sed 's://.*::' "$f" | grep -qE "prefs\.($settings_readers)\(" && echo "$f"
         done)
[ -z "$uiread" ] && ok "#4b settings live in statics -- no screen reads them from preferences" \
                 || bad "#4b a screen reads a setting from preferences instead of the static:$uiread"

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
#
# `DropdownMenu(` is a SUBSTRING of `ExposedDropdownMenu(`, so this covers both
# the plain menu in ConfigurationScreen and the Material one VoiceScreen moved
# to on 2026-09-03. The NEGATIVE TEST used to patch VoiceScreen's plain
# DropdownMenu and stopped matching anything when that migration happened, which
# is why selftest reported "#8 NOT CAUGHT" -- the check was fine, the test had
# gone stale. It points at ConfigurationScreen now.
n=$(sed 's://.*::' $KT/*.kt | grep -A 25 "DropdownMenu(" | grep -c "LazyColumn\|LazyRow")
[ "$n" = 0 ] && ok "#8  no lazy list inside a DropdownMenu" \
             || bad "#8  a lazy list inside a DropdownMenu ($n) -- it cannot answer intrinsics and throws"

# --- 26. the user interface is PURE JETPACK COMPOSE ------------------------
# Owner, 2026-09-10: "full Jetpack Compose user interface chahie, koi XML
# Android view ya fir kuchh bhi nahin". It already IS -- this makes it a rule
# instead of a fact that happens to hold today, because the way it would come
# back is one AndroidView() in one screen, and nothing would look wrong.
#
# THREE THINGS ARE CHECKED, and each is a different way in:
#   * res/layout*/ -- an inflatable View layout. There is no such directory.
#   * android.view.*  -- ANY of it. There is currently not one reference in the
#     whole app, test sources included.
#   * android.widget.* -- except Toast, which since API 30 renders in the system
#     process (custom toast VIEWS are deprecated there), so it is a system call
#     like a notification rather than a View this app inflates. Five references,
#     all of them AutoTTS-mirrored feedback.
#   * setContentView / findViewById / LayoutInflater / AndroidView / ComposeView
#     -- the four ways a View gets into a Compose tree or a Compose tree into a
#     View one.
#
# Comments are stripped first, and that is not optional: ComposeTheme.kt and
# MainActivity.kt explain in prose what the accessibility delegate writes into
# info.className, and the words "android.widget.Button" in a sentence are not a
# widget.
#
# WHAT IS NOT CHECKED, because it is not UI and cannot be Compose: the vector
# drawables under res/drawable (Google's OWN current guidance is to download
# icon XML from fonts.google.com and use it with Icon + painterResource --
# material-icons is "no longer maintained or recommended"), the adaptive
# launcher icon (read by the launcher, another process), values/styles.xml (the
# WINDOW theme, read by the framework before any composition exists), and
# res/xml/ (tts_engine, provider_paths and data_extraction_rules are each
# required by a platform component that reads them, not by us).
layoutdirs=$(ls -d app/src/main/res/layout* 2>/dev/null | wc -l)
uisrc="$KT/*.kt app/src/androidTest/java/com/sachinbaria/easyvoice/*.kt"
viewrefs=$(sed 's://.*::' $KT/*.kt app/src/androidTest/java/com/sachinbaria/easyvoice/*.kt 2>/dev/null \
  | grep -oE "setContentView\(|findViewById|LayoutInflater|AndroidView\(|ComposeView|android\.view\.[A-Za-z]+|android\.widget\.[A-Za-z]+" \
  | grep -v "android\.widget\.Toast" | wc -l)
if [ "$layoutdirs" = 0 ] && [ "$viewrefs" = 0 ]; then
  ok "#26 the UI is pure Compose -- no layout XML, no android.view, no AndroidView"
else
  bad "#26 an Android View is back (res/layout dirs: $layoutdirs, view refs: $viewrefs)"
fi

# --- 27. nothing may escape a framework entry point on the speaking path ---
# Added 2026-09-11, after the owner reported "achanak se bolna band ho jata hai".
#
# The three places the framework calls into us on the synthesis path are the ONE
# place an escaped Throwable is fatal rather than annoying:
#   onSynthesizeText   AOSP calls it from SynthesisSpeechItem.playImpl() on
#                      SynthHandler, a plain HandlerThread with no catch above
#                      it -- an escape kills the process, and a dead TTS engine
#                      process is a screen reader with no voice
#   onStop             its last two lines are what unpark the screen reader's
#                      ONE synthesis thread; an escape above them leaves that
#                      thread with nothing that can wake it
#   the ServiceConnection callbacks  delivered on the main looper, where an
#                      escape is also a process death
#
# Each is checked for its own guard shape, so a refactor that removes one is a
# red build rather than a device report weeks later.
guarded=1
# onSynthesizeText must be nothing but the wrapper: delegate + catch Throwable.
sig=$(grep -n "override fun onSynthesizeText(" "$KT/EasyVoiceTtsService.kt" | head -1 | cut -d: -f1)
if [ -z "$sig" ]; then guarded=0
else
  body=$(sed -n "${sig},$((sig+9))p" "$KT/EasyVoiceTtsService.kt")
  echo "$body" | grep -q "onSynthesizeTextImpl" || guarded=0
  echo "$body" | grep -q "catch (ex: Throwable)" || guarded=0
fi
# onStop must release in a finally.
grep -A 200 "override fun onStop() {" "$KT/EasyVoiceTtsService.kt" \
  | sed -n '1,200p' | grep -q "} finally {" || guarded=0
# all four ServiceConnection callbacks carry their own catch.
conncatch=$(grep -cE "override fun on(ServiceConnected|ServiceDisconnected|BindingDied|NullBinding)\(.*\{ try \{.*catch \(ex: Throwable\)" "$KT/EasyVoiceTtsService.kt")
[ "$conncatch" = 4 ] || guarded=0
if [ "$guarded" = 1 ]; then
  ok "#27 the framework entry points on the speaking path cannot let a Throwable escape"
else
  bad "#27 a framework entry point on the speaking path lost its guard (a throw there silences the phone)"
fi

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

# --- 28. a client-describing flag must not outlive the client --------------
# EngineWrapper carries five fields that describe THE TextToSpeech OBJECT in
# `tts` rather than the engine package: voicesCache, currentVoice,
# currentVoiceKnown, audioAttrSet and localeSet. Replace `tts` and every one of
# them becomes a statement about a client that no longer exists.
#
# Each of the three that were missed cost a real bug. audioAttrSet stale-true
# made "Force accessibility stream" dead on that engine for the life of the
# process (2026-09-03). voicesCache stale meant the wrong voice list
# (2026-09-02). localeSet stale-true was the worst: loadVoiceDedicated returns
# on it BEFORE calling setLanguage or setVoice, so with "Use dedicated engines"
# on, a restored engine was never given a language AND could never discover
# that its client was dead -- the failure branch that calls restoreEngine was
# unreachable. Permanent silence, clearable only by force-stopping (2026-09-16).
#
# The rule that prevents a fourth: `tts` is assigned NOWHERE without
# forgetClientState() beside it, and forgetClientState() clears all of them.
# Comments are stripped, since the prose above those sites names the fields.
body=$(sed 's://.*::' $KT/EasyVoiceTtsService.kt)
assigns=$(printf '%s\n' "$body" | grep -cE '\.tts = ' || true)
paired=$(printf '%s\n' "$body" | grep -E '\.tts = ' | grep -c 'forgetClientState()' || true)
cleared=$(printf '%s\n' "$body" |
          sed -n '/fun forgetClientState()/,/^        }/p' |
          grep -cE 'voicesCache = null|currentVoice = null|currentVoiceKnown = true|localeSet = false' || true)
# A PROCESS DEATH IS ALSO THE CLIENT GOING AWAY, even though `tts` still holds the
# same (now dead) object. Without this the cached currentVoice survived the death and
# loadVoice's "Do nothing!" short-circuit answered from it and never touched the
# engine -- which the 2026-09-11 caching change introduced, because that test used to
# be a LIVE tts.voice read that answers null on a dead connection.
gone=$(printf '%s\n' "$body" | sed -n '/fun onEngineProcessGone/,/^    }/p' | grep -c 'forgetClientState()' || true)
[ "$gone" -ge 1 ] || paired="$paired(onEngineProcessGone-does-not-forget)"
if [ "$assigns" -gt 0 ] && [ "$assigns" = "$paired" ] && [ "$cleared" = 4 ]; then
  ok "#28 every replacement of a wrapper's TextToSpeech forgets the old client's state"
else
  bad "#28 a wrapper's TextToSpeech is replaced without forgetClientState(), or that function stopped clearing all four fields ($paired/$assigns paired, $cleared/4 cleared)"
fi

# --- 29. every unusable engine wrapper must be REACHABLE BY RECOVERY -------
# The app has exactly ONE recovery path for a broken engine -- onEngineProcessBack,
# which fires on the keep-alive reconnect and acts on `state == -1`. So -1 must be
# what "this wrapper has no usable client" MEANS, everywhere, or the wrapper is
# invisible to it and that engine is silent until the app is force-stopped. Three
# real ways in were found on 2026-09-16, all of them permanent:
#
#   a failed TextToSpeech construction left the wrapper at state 0
#   restoreCount hit its cap while the wrapper sat at state 2
#   restoringIndex was never cleared, so NO engine could be restored again
#
# The last one is the worst and was hidden behind a false claim in a comment: AOSP's
# initTts returns SUCCESS as soon as bindService does, WITHOUT dispatching, so a
# bind that succeeds against a process that never starts means onInit never fires.
# The restore therefore needs the same bind timeout EngineFinder already has.
fail29=""
# Comments stripped AND the blank lines they leave behind removed -- otherwise a
# long explanatory comment inside a catch pushes the assignment out of grep's -A
# window and the check fails on a clean tree.
svc=$(sed 's://.*::' $SERVICE | grep -v '^[[:space:]]*$')
# every engine-construction catch marks the wrapper dead rather than leaving it at 0
# -A2 because one of the two catches is a multi-line block, so the assignment is
# not on the log line itself; grouping markers are stripped so the count is clean.
inits=$(printf '%s\n' "$svc" | grep -c 'Error when initializing' || true)
initsdead=$(printf '%s\n' "$svc" | grep -A2 'Error when initializing' | grep -c 'state = -1' || true)
# (One site since 2026-09-23: startNextInitEngine, which now starts every engine at once.)
[ "$inits" -ge 1 ] && [ "$initsdead" = "$inits" ] || fail29="$fail29 construction-catch($initsdead/$inits marks state=-1);"
# every startup init is bounded too: an onInit that never arrives would leave
# that engine at state 0, invisible to every recovery path
printf '%s\n' "$svc" | grep -q 'initWalkHandler.postDelayed' \
  || fail29="$fail29 no-init-walk-timeout;"
# a configured engine that is not ready asks for itself back
printf '%s\n' "$svc" | grep -q 'recoverEngineNotReady(if' \
  || fail29="$fail29 no-speak-time-recovery;"
# the restore slot is taken once and has more than one way to be given back
takes=$(printf '%s\n' "$svc" | grep -c 'restoringIndex = idx' || true)
frees=$(printf '%s\n' "$svc" | grep -c 'restoringIndex = -1' || true)
[ "$takes" = 1 ] || fail29="$fail29 restoringIndex-taken-$takes-times;"
[ "$frees" -ge 3 ] || fail29="$fail29 only-$frees-ways-to-release-the-restore-slot;"
# and the bind that can never answer is bounded
printf '%s\n' "$svc" | grep -q 'restoreTimeoutHandler.postDelayed' \
  || fail29="$fail29 no-restore-bind-timeout;"
# the reconnect ends the failure streak whatever state the wrapper is in
printf '%s\n' "$svc" | sed -n '/fun onEngineProcessBack/,/^    }/p' |
  grep -B2 'restoreCount = 0' | grep -q 'state == -1' \
  && fail29="$fail29 restoreCount-reset-gated-on-state;"
[ -z "$fail29" ] \
  && ok "#29 every unusable engine wrapper can still be recovered" \
  || bad "#29 an engine can reach a state no recovery path sees:$fail29"

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
