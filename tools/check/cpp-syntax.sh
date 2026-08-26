#!/usr/bin/env bash
# g++ -fsyntax-only over the native source, with the JDK's JNI headers and the
# CLD2/CLD3 headers CI clones at build time.
#
# This is a SYNTAX check, not a build: the NDK toolchain, CMake and the actual
# link are only exercised in CI. It still catches the great majority of what
# goes wrong when editing tts_engine_core.cpp -- a mistyped identifier, a
# missing brace, a call that does not match its declaration.
#
# The CLD2/CLD3 sources are not in this repository (CI clones them). If they are
# missing, this says so and skips rather than pretending to have checked.
set -euo pipefail

ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
JH=${JAVA_HOME:-$(dirname "$(dirname "$(readlink -f "$(command -v javac)")")")}

missing=0
for d in "$CPP/cld2_src/public" "$CPP/cld3_src/src" "$CPP/cld3_gen"; do
  [ -d "$d" ] || { echo "missing: ${d#$ROOT/}"; missing=1; }
done
if [ "$missing" = 1 ]; then
  cat <<EOF

The CLD2/CLD3 sources are not present. They are cloned by CI, not checked in.
To check locally, from the repository root:

  git clone --depth 1 https://github.com/CLD2Owners/cld2.git      app/src/main/cpp/cld2_src
  git clone --depth 1 https://github.com/google/cld3.git          app/src/main/cpp/cld3_src
  mkdir -p app/src/main/cpp/cld3_gen/cld_3/protos
  protoc -Iapp/src/main/cpp/cld3_src/src \\
         --cpp_out=app/src/main/cpp/cld3_gen/cld_3/protos \\
         app/src/main/cpp/cld3_src/src/*.proto
EOF
  exit 2
fi

status=0
for f in "$CPP"/*.cpp; do
  echo "== $(basename "$f")"
  g++ -fsyntax-only -std=c++17 -Wno-narrowing \
      -I"$JH/include" -I"$JH/include/linux" \
      -I"$CPP/cld2_src/public" -I"$CPP/cld2_src/internal" \
      -I"$CPP/cld3_src/src" -I"$CPP/cld3_src" -I"$CPP/cld3_gen" \
      -I"$CPP" "$f" 2>&1 | grep -E "error|fatal" | head -20 || true
  g++ -fsyntax-only -std=c++17 -Wno-narrowing \
      -I"$JH/include" -I"$JH/include/linux" \
      -I"$CPP/cld2_src/public" -I"$CPP/cld2_src/internal" \
      -I"$CPP/cld3_src/src" -I"$CPP/cld3_src" -I"$CPP/cld3_gen" \
      -I"$CPP" "$f" 2>/dev/null || status=1
done

if [ "$status" = 0 ]; then echo "C++ OK"; else echo "C++ FAILED"; fi
exit $status
