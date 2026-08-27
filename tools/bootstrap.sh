#!/usr/bin/env bash
# Fetch the two large things the local checks need, into tools/.cache (ignored).
#
#   android.jar   a real Android API jar, so kotlinc actually type-checks the
#                 android.* call sites instead of silently treating every one of
#                 them as an unresolved symbol. Without it kotlinc checks
#                 NOTHING at those call sites -- that is how a wrong trailing
#                 lambda once reached CI.
#   kotlinc       the Kotlin compiler, pinned to the version the app is built
#                 with (see app/build.gradle.kts).
#
# Both are downloads, so this needs network. Everything else the checks use
# (python3, javac, g++) is expected to be on PATH already.
#
# Note on android.jar: it comes from Maven Central and is API 15, because Google
# Maven is unreachable from some environments. It is old on purpose and the
# resulting noise is constant -- see tools/check/kotlin-typecheck.sh, which
# judges by the DIFF against a baseline commit rather than by the error count.
set -euo pipefail

CACHE="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/.cache"
KOTLIN_VERSION=2.4.10
ANDROID_JAR_URL=https://repo1.maven.org/maven2/com/google/android/android/4.1.1.4/android-4.1.1.4.jar
KOTLINC_URL="https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip"

mkdir -p "$CACHE"

if [ -f "$CACHE/android.jar" ]; then
  echo "android.jar    already present"
else
  echo "android.jar    downloading..."
  curl -fsSL -o "$CACHE/android.jar" "$ANDROID_JAR_URL"
  echo "android.jar    ok ($(wc -c < "$CACHE/android.jar") bytes)"
fi

if [ -x "$CACHE/kotlinc/bin/kotlinc" ]; then
  echo "kotlinc        already present"
elif command -v kotlinc >/dev/null 2>&1; then
  echo "kotlinc        found on PATH, not downloading"
else
  echo "kotlinc        downloading ${KOTLIN_VERSION}..."
  curl -fsSL -o "$CACHE/kotlin-compiler.zip" "$KOTLINC_URL"
  unzip -q -o "$CACHE/kotlin-compiler.zip" -d "$CACHE"
  rm -f "$CACHE/kotlin-compiler.zip"
  echo "kotlinc        ok"
fi

echo
echo "Ready. Run tools/check-all.sh"
