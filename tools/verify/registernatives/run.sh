#!/usr/bin/env bash
# Every entry point JNI_OnLoad binds with RegisterNatives (owner, 2026-10-07)
# against the compiled EasyVoiceTtsService: same native methods, same JNI
# signatures, none missing on either side. A drift would only cost that method
# its binding (registerNatives binds one at a time and ART falls back to the
# exported Java_* name), but it should never ship unnoticed.
#   run.sh            needs a compiled class: run tools/check/gradle-compile.sh first
#   EV_NEG=sig        breaks one signature on purpose (must FAIL)
#   EV_NEG=noregister JNI_OnLoad registers nothing (the JVM call must FAIL)
set -euo pipefail
HERE=$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)
ROOT=$(cd "$HERE/../../.." && pwd)
CLS=$(find "$ROOT/app/build/intermediates" -path "*compileDebugKotlin/classes/com/sachinbaria/easyvoice/EasyVoiceTtsService.class" | head -1)
[ -n "$CLS" ] || { echo "no compiled EasyVoiceTtsService.class -- run tools/check/gradle-compile.sh"; exit 2; }
CORE="$ROOT/app/src/main/cpp/tts_engine_core.cpp"
TMP=$(mktemp)
cp "$CORE" "$TMP"
[ "${EV_NEG:-}" = sig ] && sed -i 's|"(Ljava/lang/String;Z)\[Ljava/lang/String;"|"(Ljava/lang/String;I)[Ljava/lang/String;"|' "$TMP"
javap -s -p "$CLS" 2>/dev/null | grep -v JAVA_TOOL > "$TMP.javap"
python3 - "$TMP" "$TMP.javap" <<'PY'
import re, sys
core = open(sys.argv[1]).read()
block = core[core.index("static void registerNatives(JNIEnv* env){"):]
table = dict(re.findall(r'\{const_cast<char\*>\("(\w+)"\),\s*const_cast<char\*>\("([^"]+)"\)', block))
lines = open(sys.argv[2]).read().splitlines()
compiled = {}
for i, l in enumerate(lines):
    m = re.search(r'\bnative\b.*\b(\w+)\(', l)
    if m and i + 1 < len(lines) and "descriptor:" in lines[i + 1]:
        compiled[m.group(1)] = lines[i + 1].split("descriptor:")[1].strip()
exported = set(re.findall(r'Java_com_sachinbaria_easyvoice_EasyVoiceTtsService_(\w+)\(', core))
bad = []
for name, sig in compiled.items():
    if name not in table: bad.append("not registered: %s %s" % (name, sig))
    elif table[name] != sig: bad.append("signature differs: %s registered %s compiled %s" % (name, table[name], sig))
    if name not in exported: bad.append("no exported Java_ fallback: " + name)
for name in table:
    if name not in compiled: bad.append("registered but not declared: " + name)
for b in bad: print(b)
print("%d native methods declared, %d registered, %d problems" % (len(compiled), len(table), len(bad)))
print("IDENTICAL" if not bad else "FAILED")
sys.exit(1 if bad else 0)
PY
[ "${EV_NEG:-}" = sig ] && exit 0
# On a real JVM: the core + CLD2 as a shared library whose ONLY exported symbol
# is JNI_OnLoad, so every call below can succeed only through RegisterNatives.
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}
CPP="$ROOT/app/src/main/cpp"
WORK=${EV_WORK:-${TMPDIR:-/tmp}}/ev-verify-registernatives${EV_NEG:+-$EV_NEG}
mkdir -p "$WORK/obj"
INC="-I$JH/include -I$JH/include/linux -I$CPP/cld2_src/public -I$CPP/cld2_src/internal -I$CPP"
objs=""
for f in $(python3 "$ROOT/tools/verify/latency/sources.py" "$CPP"); do
  src="$f"
  if [ "${EV_NEG:-}" = noregister ] && [[ "$f" == */tts_engine_core.cpp ]]; then
    sed 's/        registerNatives(env);//' "$f" > "$WORK/core-neg.cpp"; src="$WORK/core-neg.cpp"
  fi
  o="$WORK/obj/$(echo "$src" | md5sum | cut -c1-16).o"
  if [ ! -f "$o" ] || [ "$src" -nt "$o" ]; then rm -f "$o"; g++ -c -O2 -fPIC -std=c++17 -w $INC -o "$o" "$src" || { echo "compile failed: $src"; exit 1; }; fi
  objs="$objs $o"
done
printf '{ global: JNI_OnLoad; local: *; };\n' > "$WORK/only-onload.map"
g++ -shared -o "$WORK/libeasyvoice.so" $objs -lpthread -Wl,--version-script="$WORK/only-onload.map"
exported=$(nm -D --defined-only "$WORK/libeasyvoice.so" | awk '{print $3}' | grep -c '^Java_' || true)
echo "Java_ symbols exported by the test library: $exported (must be 0)"
[ "$exported" = 0 ] || exit 1
javac -d "$WORK/cls" "$HERE/jvm/com/sachinbaria/easyvoice/EasyVoiceTtsService.java"
java -cp "$WORK/cls" com.sachinbaria.easyvoice.EasyVoiceTtsService "$WORK/libeasyvoice.so" 2>&1 | grep -v JAVA_TOOL
