#!/usr/bin/env bash
# Prove that tools/check/invariants.sh actually fires.
#
#   tools/check/selftest.sh
#
# A checker that always says "ok" is worse than no checker, because it is
# believed. This copies the repository to a scratch directory, breaks each rule
# on purpose, and asserts that the corresponding check reports FAIL. Nothing in
# the real tree is touched.
#
# It exists because writing invariants.sh produced exactly that failure twice:
#
#   * `set -o pipefail` plus `grep -q`. grep -q exits the moment it finds a
#     match, the upstream command gets SIGPIPE and exits 141, and pipefail makes
#     THAT the pipeline's status -- so four checks reported "ok" precisely when
#     they had found a violation. Every check counts matches now.
#   * a flat `grep -A 12` for #3 could not tell a lock INSIDE the language-list
#     monitor from one three lines after it -- and the correct code is exactly
#     the latter, so it reported a violation against working code. It is
#     brace-aware now (nested_locks.py).
#
# Run this after changing invariants.sh. Needs git and python3.
set -u

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-invariant-selftest
K=app/src/main/java/com/tts/easyvoice

rm -rf "$WORK"
cp -r "$ROOT" "$WORK"
cd "$WORK"
git add -A >/dev/null 2>&1
git -c user.name=selftest -c user.email=selftest@local commit -q -m "selftest base" >/dev/null 2>&1

fail=0
run() {  # run <label> <expected FAIL tag> <command that breaks the rule>
  printf '  %-52s ' "$1"
  bash -c "$3" >/dev/null 2>&1
  if bash tools/check/invariants.sh 2>&1 | grep -q "FAIL  $2"; then
    echo "caught"
  else
    echo "NOT CAUGHT -- the check for $2 does not fire"
    fail=1
  fi
  git checkout -q -- .
  # ADDING a file is a way to break a rule too -- #26's second case creates
  # res/layout/leak.xml, and `git checkout` does not remove an UNTRACKED file,
  # so without this every later check would run against a tree that still has a
  # View layout in it. Scoped to res/ so nothing else in the scratch copy is
  # touched.
  git clean -qfd app/src/main/res 2>/dev/null || true
}

echo "first, the unbroken tree must be clean:"
if bash tools/check/invariants.sh >/dev/null 2>&1; then
  echo "  ok"
else
  echo "  the tree ALREADY fails a check -- fix that before trusting this"
  bash tools/check/invariants.sh | grep FAIL
  exit 1
fi

echo
echo "then each rule, broken on purpose:"
run "#1  drop a push from a rebuild site" "#1" \
    "sed -i '/EasyVoiceTtsService.pushLanguageSets()/d' $K/ModesScreen.kt"
run "#2  push hints from the synthesis path" "#2" \
    "perl -0pi -e 's/\n(\s+)refreshEnabledLangs\(\)\n/\n\$1pushLanguageSets()\n/' $K/EasyVoiceTtsService.kt"
run "#3  nest a lock in the list monitor" "#3" \
    "perl -0pi -e 's/(val missing = synchronized\(LangStore\.languages\) \{)/\$1\n            synchronized(this) { }/' $K/EasyVoiceTtsService.kt"
run "#4  read a preference while synthesising" "#4" \
    "perl -0pi -e 's/(val readingMode = when \(modeInt\))/val leak = prefs.isKeepAliveMode()\n        \$1/' $K/EasyVoiceTtsService.kt"
run "#4b read a setting from preferences in a screen" "#4b" \
    "perl -0pi -e 's/(var keepAlive by remember)/val leak = prefs.isKeepAliveMode()\n    \$1/' $K/AdvancedScreen.kt"
run "#5  add an announceForAccessibility" "#5" \
    "echo 'fun x() { v.announceForAccessibility(\"hi\") }' >> $K/ModesScreen.kt"
run "#6  put state in a contentDescription" "#6" \
    "perl -0pi -e 's/contentDescription = /contentDescription = \"Speed, checked\" ?: /' $K/VoiceScreen.kt"
run "#8  put a LazyColumn in a DropdownMenu" "#8" \
    "perl -0pi -e 's/(DropdownMenu\(expanded = menuOpen[^\n]*\{)/\$1\n                                    LazyColumn { }/' $K/ConfigurationScreen.kt"
run "#26 add an AndroidView to a screen" "#26" \
    "perl -0pi -e 's/(fun ConfigurationScreen)/fun Leak() { AndroidView(factory = { c -> android.widget.TextView(c) }) }\\n\$1/' $K/ConfigurationScreen.kt"
run "#26 add a res/layout XML" "#26" \
    "mkdir -p app/src/main/res/layout && printf '<FrameLayout/>' > app/src/main/res/layout/leak.xml"
run "#27 unwrap onSynthesizeText's guard" "#27" \
    "perl -0pi -e 's/onSynthesizeTextImpl\\(request, callback\\)/EasyVoiceLogger.debug(EasyVoiceLogger.TAG, \\"x\\")/' $K/EasyVoiceTtsService.kt"
run "#27 take the finally off onStop's release" "#27" \
    "perl -0pi -e 's/\\} finally \\{\\n            synchronized\\(syncLock\\) \\{ isStopped/} catch (_: Error) {\\n            synchronized(syncLock) { isStopped/' $K/EasyVoiceTtsService.kt"
run "#27 drop a ServiceConnection guard" "#27" \
    "perl -0pi -e 's/override fun onNullBinding\\(name: android.content.ComponentName\\?\\) \\{ try \\{/override fun onNullBinding(name: android.content.ComponentName?) { run {/' $K/EasyVoiceTtsService.kt"
run "#18 contentDescription on a heading" "#18" \
    "perl -0pi -e 's/\\.semantics \\{ heading\\(\\) \\}/.semantics { heading(); contentDescription = title }/' $K/ModesScreen.kt"
run "#17 persist without loading" "#17" \
    "sed -i '/LangStore.ensureLoaded(this)/d' $K/LanguagesActivity.kt"
run "#16 bring a CLD3 symbol back into the core" "#16" \
    "perl -0pi -e 's/static std::string detectWindowLang/static std::string cld3DetectRaw(const std::string\&, bool*, bool);\\nstatic std::string detectWindowLang/' app/src/main/cpp/tts_engine_core.cpp"
run "#16 bring a useCld3 flag back into the service" "#16" \
    "sed -i 's/var utteranceId = \"\"/var useCld3Flag = false/' $K/EasyVoiceTtsService.kt"
run "#14 log with a stray tag literal" "#14" \
    "perl -0pi -e 's/EasyVoiceLogger\\.debug\\(EasyVoiceLogger\\.TAG/EasyVoiceLogger.debug(\"TAG\"/' $K/LangStore.kt"
run "#28 stop clearing localeSet on a client swap" "#28" \
    "perl -0pi -e 's/            currentVoiceKnown = true\\n            localeSet = false\\n/            currentVoiceKnown = true\\n/' $K/EasyVoiceTtsService.kt"
run "#28 replace a client without forgetting its state" "#28" \
    "perl -0pi -e 's/wrapper\\.tts = initializingTts; wrapper\\.forgetClientState\\(\\)/wrapper.tts = initializingTts/' $K/EasyVoiceTtsService.kt"
run "#28 stop clearing the voice cache" "#28" \
    "perl -0pi -e 's/            voicesCache = null\\n            currentVoice = null/            currentVoice = null/' $K/EasyVoiceTtsService.kt"
run "#29 leave a failed construction at state 0" "#29" \
    "perl -0pi -e 's/if \\(myIndex < enginePool\\.size\\) enginePool\\[myIndex\\]\\.state = -1\\n                initializingIndex\\+\\+/initializingIndex++/' $K/EasyVoiceTtsService.kt"
run "#29 remove the init walk timeout" "#29" \
    "perl -0pi -e 's/initWalkHandler\\.postDelayed\\(\\{/run({/' $K/EasyVoiceTtsService.kt"
run "#29 drop the speak-time recovery" "#29" \
    "perl -0pi -e 's/; recoverEngineNotReady\\(if \\(pkg\\.isEmpty\\(\\)\\) lastEnginePkg else pkg\\)//g' $K/EasyVoiceTtsService.kt"
run "#29 remove the restore bind timeout" "#29" \
    "perl -0pi -e 's/restoreTimeoutHandler\\.postDelayed\\(\\{/run({/' $K/EasyVoiceTtsService.kt"
run "#29 gate the reconnect reset on state again" "#29" \
    "perl -0pi -e 's/            wrapper\\.restoreCount = 0\\n            if \\(wrapper\\.state == -1\\) \\{/            if (wrapper.state == -1) {\\n                wrapper.restoreCount = 0/' $K/EasyVoiceTtsService.kt"
run "#12 grow build.yml past the ceiling" "#12" \
    "head -c 500000 /dev/zero | tr '\\0' '#' >> .github/workflows/build.yml"
run "put a CLD3 source back in CMakeLists" "a CLD3/protobuf build input" \
    "sed -i 's|    \${CLD2_DIR}/cldutil.cc|    \${CLD3_DIR}/nnet_language_identifier.cc\\n    \${CLD2_DIR}/cldutil.cc|' app/src/main/cpp/CMakeLists.txt"
echo
[ "$fail" = 0 ] && echo "EVERY CHECK FIRES" || echo "AT LEAST ONE CHECK IS ASLEEP"
exit $fail
