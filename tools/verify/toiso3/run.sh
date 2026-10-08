#!/usr/bin/env bash
# #6 (owner, 2026-10-01): SharedPrefsManager.toIso3 remembers its answers.
# Lifts the real toIso3 + its uncached body + localeOf and checks the cached
# answer against the uncached one over every ISO code, every two-letter
# string, odd inputs and random strings, from four threads at once.
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-toiso3
rm -rf "$WORK"; mkdir -p "$WORK"
python3 - "$SRC" "$WORK/Check.kt" "$HERE/Driver.kt" <<'PY'
import sys, re
src, out, drv = sys.argv[1:4]
sp = open(src + "/SharedPrefsManager.kt", encoding="utf-8").read()
i = sp.index("    // Called several times per chunk"); j = sp.index("    fun getReadingMode")
iso = open(src + "/IsoCodes.kt", encoding="utf-8").read()
k = iso.index("@Suppress(\"DEPRECATION\")\nfun localeOf")
open(out, "w", encoding="utf-8").write(
    "import java.util.Locale\nclass SharedPrefsManager {\n" + sp[i:j] + "}\n" + iso[k:] + "\n" + open(drv, encoding="utf-8").read())
PY
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
