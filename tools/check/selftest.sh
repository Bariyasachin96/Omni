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
    "perl -0pi -e 's/(val readingMode = when \(modeInt\))/val leak = prefs.isUseCld3()\n        \$1/' $K/EasyVoiceTtsService.kt"
run "#5  add an announceForAccessibility" "#5" \
    "echo 'fun x() { v.announceForAccessibility(\"hi\") }' >> $K/ModesScreen.kt"
run "#6  put state in a contentDescription" "#6" \
    "perl -0pi -e 's/contentDescription = /contentDescription = \"Speed, checked\" ?: /' $K/VoiceScreen.kt"
run "#8  put a LazyColumn in a DropdownMenu" "#8" \
    "perl -0pi -e 's/(DropdownMenu\(expanded = expanded)/\$1\n            LazyColumn { }/' $K/VoiceScreen.kt"
run "#16 drop the span site's reliability flag" "#16" \
    "perl -0pi -e 's/cld3DetectRaw\\(std::string\\(text, start, detectBytes\\), &cld3Reliable, true\\)/cld3DetectRaw(std::string(text, start, detectBytes), nullptr, true)/' app/src/main/cpp/tts_engine_core.cpp"
run "#14 log with a stray tag literal" "#14" \
    "perl -0pi -e 's/EasyVoiceLogger\\.debug\\(EasyVoiceLogger\\.TAG/EasyVoiceLogger.debug(\"TAG\"/' $K/LangStore.kt"
run "#12 grow build.yml past the ceiling" "#12" \
    "head -c 500000 /dev/zero | tr '\\0' '#' >> .github/workflows/build.yml"
run "CLD3 parity: delete one CLD3 arm" "CLD3 parity" \
    "perl -0pi -e 's/cld3DetectRaw\(utf8Text, &cld3Reliable, false\)/std::string()/' app/src/main/cpp/tts_engine_core.cpp"
run "CLD3 flag: stop passing it from Kotlin" "CLD3 flag" \
    "sed -i 's/useCld3Flag/false/g' $K/EasyVoiceTtsService.kt"

echo
[ "$fail" = 0 ] && echo "EVERY CHECK FIRES" || echo "AT LEAST ONE CHECK IS ASLEEP"
exit $fail
