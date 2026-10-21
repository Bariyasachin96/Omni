#!/usr/bin/env bash
# g++ -fsyntax-only over the native source, with the JDK's JNI headers.
#
# This is a SYNTAX check, not a build: the NDK toolchain, CMake and the actual
# link are only exercised in CI. It still catches the great majority of what
# goes wrong when editing tts_engine_core.cpp -- a mistyped identifier, a
# missing brace, a call that does not match its declaration. (Since CLD2 left
# on 2026-10-21 the core needs nothing that is not in this repository.)
set -euo pipefail

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}

status=0
for f in "$CPP"/*.cpp; do
  echo "== $(basename "$f")"
  g++ -fsyntax-only -std=c++17 -Wno-narrowing \
      -I"$JH/include" -I"$JH/include/linux" \
      -I"$CPP" "$f" 2>&1 | grep -E "error|fatal" | head -20 || true
  g++ -fsyntax-only -std=c++17 -Wno-narrowing \
      -I"$JH/include" -I"$JH/include/linux" \
      -I"$CPP" "$f" 2>/dev/null || status=1
done

if [ "$status" = 0 ]; then echo "C++ OK"; else echo "C++ FAILED"; fi
exit $status
