#!/usr/bin/env bash
# The native PGO profile still fits the core (2026-10-07).
#
#   tools/check/pgo-fresh.sh [core.cpp]
#
# app/src/main/cpp/pgo/easyvoice.profdata is keyed by each function's name and
# control-flow hash. Change a function's branches without retraining and that
# function silently loses its profile in the shipped build. This compiles the
# core alone for arm64-v8a with the profile, turning an out-of-date or missing
# function record into an error. The fix is always the same:
#     tools/pgo/run.sh --update
# Needs the NDK under tools/.cache (tools/bootstrap.sh + one native build);
# without it the check says so and passes, like the other NDK-only steps.
set -euo pipefail
ROOT=$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)
CPP="$ROOT/app/src/main/cpp"
CORE=${1:-$CPP/tts_engine_core.cpp}
NDK=$(ls -d "$ROOT"/tools/.cache/android-sdk/ndk/* 2>/dev/null | sort | tail -1 || true)
if [ -z "$NDK" ]; then echo "   skipped: no NDK here"; exit 0; fi
OUT=$(mktemp -d); trap 'rm -rf "$OUT"' EXIT
if "$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/clang++" --target=aarch64-linux-android24 \
     -std=c++17 -Wno-narrowing -Oz -fno-exceptions -fno-rtti -DNDEBUG \
     -fprofile-instr-use="$CPP/pgo/easyvoice.profdata" \
     -Wprofile-instr-out-of-date -Werror=profile-instr-out-of-date \
     -Wprofile-instr-missing -Werror=profile-instr-missing \
     -I"$CPP" \
     -c "$CORE" -o "$OUT/core.o" 2> "$OUT/err"; then
  echo "   profile fits tts_engine_core.cpp"
else
  grep -E "error" "$OUT/err" | head -5
  echo "   STALE: run tools/pgo/run.sh --update"; exit 1
fi
