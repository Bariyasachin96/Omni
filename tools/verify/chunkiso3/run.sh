#!/usr/bin/env bash
# TextChunk.iso3() (owner, 2026-10-06) remembers iso3OfCode(lang) per chunk.
# Lifts the REAL TextChunk and IsoCodes.kt's iso3OfCode machinery and checks
# iso3() against the uncached conversion while chunks are read again and
# again, have their language reassigned, and are shared between threads.
#   EV_NEG=nokey   the memo ignores a reassigned language (must be caught)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-chunkiso3
rm -rf "$WORK"; mkdir -p "$WORK"
python3 - "$SRC" "$WORK/Check.kt" "$HERE/Driver.kt" "${EV_NEG:-}" <<'PY'
import sys
src, out, drv, neg = sys.argv[1:5]
svc = open(src + "/EasyVoiceTtsService.kt", encoding="utf-8").read()
i = svc.index("data class TextChunk("); j = svc.index("\n}\n", i) + 3
chunk = svc[i:j]
if neg == "nokey":
    a = "if (memo != null && memo.first == l) return memo.second"
    assert a in chunk; chunk = chunk.replace(a, "if (memo != null) return memo.second")
elif neg: sys.exit("unknown EV_NEG " + neg)
iso = open(src + "/IsoCodes.kt", encoding="utf-8").read()
# The Android version is a stub here: localeOf takes the Android 16 branch.
a = "android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.BAKLAVA"
assert a in iso; iso = iso.replace(a, "Sdk.INT >= 36")
n = iso.index("fun localeOf(")
open(out, "w", encoding="utf-8").write("import java.util.Locale\nobject Sdk { @Volatile var INT = 36 }\n" + chunk +
    iso[n:] + "\n" + open(drv, encoding="utf-8").read())
PY
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
