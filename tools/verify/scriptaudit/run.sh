#!/usr/bin/env bash
# Every script of the current Unicode through the real detection path -- see
# audit.py. Writes the report to tools/verify/scriptaudit/REPORT.md (or $1).
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-scriptaudit
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
[ -d "$CPP/cld2_src/internal" ] || { echo "clone CLD2 into app/src/main/cpp/cld2_src first"; exit 2; }
mkdir -p "$WORK/obj"
INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
cld=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  case "$f" in */tts_engine_core.cpp) ;; *)
    o="$WORK/obj/$(basename "$f" .cc).o"
    [ -f "$o" ] || g++ -c -O2 -std=c++17 -w $INC -o "$o" "$f" || { echo "compile failed: $f"; exit 1; }
    cld="$cld $o" ;;
  esac
done
rm -f "$WORK/probe"
g++ -O2 -std=c++17 -w $INC -o "$WORK/probe" "$HERE/probe.cpp" $cld || { echo "probe did not build"; exit 1; }
# Kotlin's own IsoCodes.toIso3 answers for every ISO 639-1 code and every CLD2 code
cat > "$WORK/Dump.kt" <<'KT'
package com.sachinbaria.easyvoice
fun main() {
    val codes = LinkedHashSet<String>()
    for (c in java.util.Locale.getISOLanguages()) codes.add(c)
    generateSequence(::readLine).forEach { val t = it.trim(); if (t.isNotEmpty() && !t.startsWith("#")) codes.add(t.split(Regex("\\s+"))[0]) }
    for (c in codes) { val r = IsoCodes.toIso3(c); if (r != null) println("$c=$r") }
}
KT
rm -f "$WORK/dump.jar"
"$KOTLINC" -nowarn "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/IsoCodes.kt" "$WORK/Dump.kt" -include-runtime -d "$WORK/dump.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/dump.jar" ] || { echo "IsoCodes dump did not compile"; exit 1; }
(grep -v '^#' "$ROOT/tools/verify/langcodes/codes.txt"; printf 'syr\nchr\nsat\nmni\nnqo\n') \
  | java -cp "$WORK/dump.jar" com.sachinbaria.easyvoice.DumpKt 2>/dev/null > "$WORK/iso3.txt"
python3 "$HERE/audit.py" "$WORK/probe" "$WORK/iso3.txt" "$ROOT/tools/unicode/ucd" "${1:-$HERE/REPORT.md}"
