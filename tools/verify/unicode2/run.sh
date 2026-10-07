#!/usr/bin/env bash
# The core's whitespace, digits, currency, no-space letters and lowercase,
# every code point, against Java itself (owner, 2026-10-07: "jo old hai vahan
# per sab kuchh update kar do"). dump.cpp asks the REAL functions sliced out of
# tts_engine_core.cpp; Check.java asks the JDK (Character.isWhitespace,
# Character.isDigit/digit, CURRENCY_SYMBOL, String.toLowerCase(Locale.ROOT) --
# the call AutoTTS's d0 makes). A code point the JDK's older Unicode has not
# assigned is counted as "newer", not as a difference. strings.txt carries
# Final_Sigma and U+0130 cases.
#   run.sh            EV_NEG=nosigma|ascii-digits|ws|script breaks one on purpose
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-unicode2${EV_NEG:+-$EV_NEG}
rm -rf "$WORK"; mkdir -p "$WORK"
cp "$ROOT/app/src/main/cpp/unicode_tables.h" "$WORK/"
cp "$ROOT/app/src/main/cpp/tts_engine_core.cpp" "$WORK/core.cpp"
case "${EV_NEG:-}" in
  "") ;;
  nosigma)      sed -i 's/(before \&\& !after) ? 0x3C2 : 0x3C3/0x3C3/' "$WORK/core.cpp" ;;
  ascii-digits) sed -i 's/    if(cp < 0x80) return -1;\n    size_t a = 0, b = sizeof(kUniDigit)/X/; s/^    if(cp < 0x80) return -1;$/    return -1;/' "$WORK/core.cpp" ;;
  script)       ;;
  ws)           sed -i '0,/{0x3000, 0x3000}/s//{0x3001, 0x3001}/' "$WORK/unicode_tables.h" ;;
  *) echo "unknown EV_NEG"; exit 2 ;;
esac
python3 "$ROOT/tools/verify/make_core_inc.py" --source "$WORK/core.cpp" --until scriptLangForCpFiltered --out "$WORK/core.inc" >/dev/null
g++ -O1 -std=c++17 -w -I"$WORK" -o "$WORK/dump" "$HERE/dump.cpp"
javac -d "$WORK" "$HERE/Check.java"
"$WORK/dump" < "$HERE/strings.txt" | java -cp "$WORK" Check 2>&1 | grep -v JAVA_TOOL
# The Kotlin side: UnicodeTables.scriptOf against Character.UnicodeScript.
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
"$KOTLINC" -nowarn "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt" "$HERE/ScriptCheck.kt" -include-runtime -d "$WORK/script.jar" 2>&1 | grep -v '^warning' || true
java -cp "$WORK/script.jar" ScriptCheckKt ${EV_NEG:+$([ "$EV_NEG" = script ] && echo neg)} 2>&1 | grep -v JAVA_TOOL
