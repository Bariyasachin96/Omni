#!/usr/bin/env bash
# iso3OfCode (IsoCodes.kt) remembers its answers (owner, 2026-10-01). Lifts the
# real function, its uncached body and localeOf, and checks, over every ISO
# code, every two-letter string, odd inputs and random strings, from four
# threads at once:
#   * the cached answer against the uncached one, and
#   * both against the function it replaced -- SharedPrefsManager.toIso3 of
#     REF (default 7d8649e, the last commit that had that class), so moving it
#     out of the old class changed no answer, and
#   * localeOf's two methods against each other -- Locale.of, which a phone on
#     Android 16+ uses, and the constructor older phones use -- over every
#     language, country and variant shape the app meets: the same Locale.
# The Android version is a stub here (Sdk.INT), so both branches of the REAL
# localeOf run on this JVM.
#   EV_NEG=fold     the new function forgets to fold "hak" to "zho" (must be caught)
#   EV_NEG=locale   the Android 16 branch upper-cases the variant (must be caught)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
SRC=$ROOT/app/src/main/java/com/sachinbaria/easyvoice
REF=${1:-7d8649e}
KOTLINC=${KOTLINC:-$ROOT/tools/.cache/kotlinc/bin/kotlinc}
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-toiso3
rm -rf "$WORK"; mkdir -p "$WORK"
git -C "$ROOT" show "$REF:app/src/main/java/com/sachinbaria/easyvoice/SharedPrefsManager.kt" > "$WORK/old.kt"
python3 - "$SRC" "$WORK/old.kt" "$WORK/Check.kt" "$HERE/Driver.kt" "${EV_NEG:-}" <<'PY'
import sys
src, old, out, drv, neg = sys.argv[1:6]
iso = open(src + "/IsoCodes.kt", encoding="utf-8").read()
if neg == "fold":
    a = '"cmn", "lzh", "gan", "hak" -> "zho"'; assert a in iso; iso = iso.replace(a, '"cmn", "lzh", "gan" -> "zho"')
elif neg == "locale":
    a = 'Locale.of(language, country, variant)'; assert a in iso; iso = iso.replace(a, 'Locale.of(language, country, variant.uppercase())')
elif neg: sys.exit("unknown EV_NEG " + neg)
a = "android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.BAKLAVA"
assert a in iso; iso = iso.replace(a, "Sdk.INT >= 36")
k = iso.index("fun localeOf(")
sp = open(old, encoding="utf-8").read()
i = sp.index("    companion object {"); j = sp.index("\n    }\n", i) + 7
open(out, "w", encoding="utf-8").write(
    "import java.util.Locale\nobject Sdk { @Volatile var INT = 36 }\n" + iso[k:] + "\nclass Old {\n" + sp[i:j] + "}\n" + open(drv, encoding="utf-8").read())
PY
"$KOTLINC" -nowarn "$WORK/Check.kt" -include-runtime -d "$WORK/check.jar" 2>&1 | grep -v '^warning' || true
[ -f "$WORK/check.jar" ] || { echo "did not compile"; exit 1; }
java -jar "$WORK/check.jar" 2>&1 | grep -v JAVA_TOOL
