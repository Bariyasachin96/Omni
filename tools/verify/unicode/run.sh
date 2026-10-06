#!/usr/bin/env bash
# The core's Unicode against the Unicode Character Database itself, every code
# point (owner, 2026-10-06). dump.cpp asks the real functions (sliced out of
# tts_engine_core.cpp); reference.py reads tools/unicode/ucd on its own and
# says what each must be. With a ref commit (default HEAD) the old core is
# dumped too and the changes are counted; the family fallback may only change
# answers that were "un".
#   run.sh [ref]          EV_NEG=<name> breaks one table on purpose (see below)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
REF=${1:-HEAD}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-unicode
rm -rf "$WORK"; mkdir -p "$WORK/new" "$WORK/old"
python3 "$ROOT/tools/unicode/gen_tables.py" --check
cp "$ROOT/app/src/main/cpp/unicode_tables.h" "$WORK/new/"
case "${EV_NEG:-}" in
  "") ;;
  emoji)  sed -i '0,/{0xE0020, 0xE007F}/s//{0xE0020, 0xE0020}/' "$WORK/new/unicode_tables.h" ;;   # tag chars lost
  letter) sed -i '0,/{0xAA, 0xAA}/s//{0xAB, 0xAB}/' "$WORK/new/unicode_tables.h" ;;
  slot)   sed -i '0,/{0x966, 0x97F, 4}/s//{0x964, 0x97F, 4}/' "$WORK/new/unicode_tables.h" ;;   # danda as Devanagari
  bidi)   sed -i '0,/{0x2066, 0x2069}/s//{0x2066, 0x2068}/' "$WORK/new/unicode_tables.h" ;;
  *) echo "unknown EV_NEG"; exit 2 ;;
esac
[ -z "${EV_NEG:-}" ] || ! cmp -s "$WORK/new/unicode_tables.h" "$ROOT/app/src/main/cpp/unicode_tables.h" || { echo "EV_NEG changed nothing"; exit 2; }
python3 "$ROOT/tools/verify/make_core_inc.py" --until scriptLangForCpFiltered --out "$WORK/new/core.inc" >/dev/null
g++ -O1 -std=c++17 -w -I"$WORK/new" -o "$WORK/dump-new" "$HERE/dump.cpp"
OLD=-
if git -C "$ROOT" cat-file -e "$REF:app/src/main/cpp/tts_engine_core.cpp" 2>/dev/null; then
  git -C "$ROOT" show "$REF:app/src/main/cpp/tts_engine_core.cpp" > "$WORK/old/tts_engine_core.cpp"
  git -C "$ROOT" show "$REF:app/src/main/cpp/unicode_tables.h" > "$WORK/old/unicode_tables.h" 2>/dev/null || true
  python3 "$ROOT/tools/verify/make_core_inc.py" --source "$WORK/old/tts_engine_core.cpp" --until scriptLangForCpFiltered --out "$WORK/old/core.inc" >/dev/null 2>&1 || true
  if [ -s "$WORK/old/core.inc" ] && cat > "$WORK/old/dump.cpp" <<'CPP' && g++ -O1 -std=c++17 -w -I"$WORK/old" -o "$WORK/dump-old" "$WORK/old/dump.cpp" 2>/dev/null; then
#include <string>
#include <vector>
#include <unordered_set>
#include <unordered_map>
#include <cstdio>
#include <cstdint>
#include <mutex>
#include <memory>
#include <algorithm>
#include "core.inc"
int main(){ for(int cp=0; cp<0x110000; cp++){ if(cp>=0xD800&&cp<=0xDFFF) continue;
  printf("%X\t%X\t%d\t%d\t%s\n", cp, normalizeFancyCodepoint(cp), isEmoji(cp)?1:0, isLetterCodepoint(cp)?1:0, familyForCp(cp).primary); } }
CPP
    "$WORK/dump-old" > "$WORK/old.tsv"; OLD="$WORK/old.tsv"
  fi
fi
"$WORK/dump-new" | python3 "$HERE/reference.py" "$ROOT/tools/unicode/ucd" "$ROOT/app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt" "$OLD"
