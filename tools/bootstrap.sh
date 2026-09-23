#!/usr/bin/env bash
# Fetch the things the local checks need, into tools/.cache (gitignored).
#
#   gradle-<v>/    the SAME Gradle CI uses (the version is read out of build.yml)
#   android-sdk/   command-line tools, the compileSdk platform and build-tools,
#                  so that Gradle and AGP can build the app here exactly as CI
#                  does. tools/check/gradle-compile.sh compiles the app and its
#                  instrumented tests with them.
#   kotlinc, compose-plugin.jar, androidx-classpath.txt
#                  what tools/check/minsdk-api.sh needs to compile the app
#                  against minSdk's own android.jar. The classpath is written BY
#                  GRADLE (tools/check/classpath.init.gradle.kts), so it is the
#                  one the app really resolves.
#
# THE HAND-BUILT IMITATION IS GONE (owner, 2026-09-23). This used to fetch an
# API 37 android.jar and resolve androidx with tools/fetch-deps.py, a resolver
# of our own, for tools/check/kotlin-typecheck.sh, a bare kotlinc run. The real
# build is reachable now, so the real build is what checks the code.
#
# dl.google.com and services.gradle.org must be reachable (the environment's
# network access must allow them). A 403 from the proxy is an egress-policy
# denial and is NOT to be routed around with a mirror; the Gradle check then
# says SKIPPED instead of passing.
#
# Everything else the checks use (python3, java 17+, g++) is expected on PATH.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
CACHE="$ROOT/tools/.cache"
KOTLIN_VERSION=2.4.20

# Gradle, the SDK packages and the command-line tools, all read from the files
# that already state them, so this cannot drift from what CI builds with.
GRADLE_VERSION=$(grep -oE "gradle-version: '[0-9.]+'" "$ROOT/.github/workflows/build.yml" | head -1 | grep -oE "[0-9.]+")
COMPILE_SDK=$(grep -oE '^\s*compileSdk\s*=\s*[0-9]+' "$ROOT/app/build.gradle.kts" | grep -oE '[0-9]+$')
BUILD_TOOLS=$(grep -oE 'build-tools;[0-9.]+' "$ROOT/.github/workflows/build.yml" | head -1 | cut -d';' -f2)
KOTLINC_URL="https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip"

# THE NON-EMBEDDABLE PLUGIN, AND THE DIFFERENCE IS NOT cosmetic. The
# -embeddable artifact is shaded against org.jetbrains.kotlin.com.intellij, which
# the kotlinc CLI's preloader classloader does not provide: it dies with
# ClassNotFoundException: org.jetbrains.kotlin.com.intellij.psi.PsiElement before
# compiling anything. The plain artifact is the one the CLI can load. Its version
# tracks KOTLIN_VERSION, exactly as the Gradle plugin does in build.gradle.kts.
COMPOSE_PLUGIN_URL="https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-compose-compiler-plugin/${KOTLIN_VERSION}/kotlin-compose-compiler-plugin-${KOTLIN_VERSION}.jar"

mkdir -p "$CACHE"

# Download with retries. Maven Central answers 429 under load -- that happened on
# the very first run of the Kotlin 2.4.20 bump -- and a one-shot curl turns a
# transient rate limit into a missing compiler plugin, silently, because the
# caller only sees the tail of a pipeline. Retry with a widening wait, and fail
# loudly if it never lands.
#
# A 403 is NOT retried: that is the egress policy answering, and the rule is to
# report the blocked host rather than hammer it.
fetch() {
  local url="$1" out="$2" n=0 code
  while [ $n -lt 5 ]; do
    code=$(curl -sSL --max-time 900 -w '%{http_code}' -o "$out" "$url" 2>/dev/null || echo 000)
    case "$code" in
      200) return 0 ;;
      403) echo "   $url -> 403 (egress policy; not retried)"; return 1 ;;
    esac
    n=$((n + 1))
    echo "   $url -> HTTP $code, retry $n of 5"
    sleep $((n * 5))
  done
  rm -f "$out"
  return 1
}

# --------------------------------------------------------------------- gradle
if [ -x "$CACHE/gradle-$GRADLE_VERSION/bin/gradle" ]; then
  echo "gradle         $GRADLE_VERSION already present"
else
  echo "gradle         downloading $GRADLE_VERSION..."
  fetch "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" "$CACHE/gradle.zip" \
    || { echo "gradle unavailable"; exit 1; }
  unzip -q -o "$CACHE/gradle.zip" -d "$CACHE" && rm -f "$CACHE/gradle.zip"
  echo "gradle         ok"
fi

# ---------------------------------------------------------------- android sdk
SDK="$CACHE/android-sdk"
SDKMANAGER="$SDK/cmdline-tools/latest/bin/sdkmanager"
if [ ! -x "$SDKMANAGER" ]; then
  echo "sdk tools      downloading..."
  fetch https://dl.google.com/android/repository/repository2-3.xml "$CACHE/repo.xml" \
    || { echo "dl.google.com unreachable"; exit 1; }
  TOOLS_ZIP=$(grep -oE 'commandlinetools-linux-[0-9]+_latest\.zip' "$CACHE/repo.xml" | sort -V | tail -1)
  rm -f "$CACHE/repo.xml"
  fetch "https://dl.google.com/android/repository/$TOOLS_ZIP" "$CACHE/tools.zip" \
    || { echo "sdk tools unavailable"; exit 1; }
  mkdir -p "$SDK/cmdline-tools" && rm -rf "$SDK/cmdline-tools/latest" "$SDK/cmdline-tools/cmdline-tools"
  unzip -q -o "$CACHE/tools.zip" -d "$SDK/cmdline-tools" && rm -f "$CACHE/tools.zip"
  mv "$SDK/cmdline-tools/cmdline-tools" "$SDK/cmdline-tools/latest"
fi
if [ -d "$SDK/platforms/android-$COMPILE_SDK.0" ] || [ -d "$SDK/platforms/android-$COMPILE_SDK" ]; then
  echo "sdk packages   already present"
else
  yes 2>/dev/null | "$SDKMANAGER" --sdk_root="$SDK" --licenses >/dev/null 2>&1 || true
  # The platform package is `android-37.0` in the current repository, and older
  # ones used `android-37`; ask for the first and fall back to the second.
  "$SDKMANAGER" --sdk_root="$SDK" "platforms;android-$COMPILE_SDK.0" "build-tools;$BUILD_TOOLS" >/dev/null 2>&1 \
    || "$SDKMANAGER" --sdk_root="$SDK" "platforms;android-$COMPILE_SDK" "build-tools;$BUILD_TOOLS" >/dev/null
  echo "sdk packages   ok (platform $COMPILE_SDK, build-tools $BUILD_TOOLS)"
fi

# ------------------------------------------------------------------- kotlinc
if [ -x "$CACHE/kotlinc/bin/kotlinc" ]; then
  echo "kotlinc        already present"
elif command -v kotlinc >/dev/null 2>&1; then
  echo "kotlinc        found on PATH, not downloading"
else
  echo "kotlinc        downloading ${KOTLIN_VERSION}..."
  fetch "$KOTLINC_URL" "$CACHE/kotlin-compiler.zip" || { echo "kotlinc unavailable"; exit 1; }
  unzip -q -o "$CACHE/kotlin-compiler.zip" -d "$CACHE"
  rm -f "$CACHE/kotlin-compiler.zip"
  echo "kotlinc        ok"
fi

# ------------------------------------------------------ compose compiler plugin
if [ -f "$CACHE/compose-plugin.jar" ]; then
  echo "compose plugin already present"
else
  echo "compose plugin downloading ${KOTLIN_VERSION}..."
  fetch "$COMPOSE_PLUGIN_URL" "$CACHE/compose-plugin.jar" || { rm -f "$CACHE/compose-plugin.jar"; echo "compose plugin unavailable"; exit 1; }
  echo "compose plugin ok ($(wc -c < "$CACHE/compose-plugin.jar") bytes)"
fi

# --------------------------------------------------------- androidx classpath
# Written by Gradle, from the app's real debugCompileClasspath. Always rewritten,
# because it is cheap and a stale one is exactly the defect it replaced.
if (cd "$ROOT" && ANDROID_HOME="$SDK" "$CACHE/gradle-$GRADLE_VERSION/bin/gradle" --no-daemon -q \
      -I tools/check/classpath.init.gradle.kts -PevOut="$CACHE/androidx-classpath.txt" :app:evClasspath) >/dev/null 2>&1; then
  echo "androidx cp    ok ($(wc -l < "$CACHE/androidx-classpath.txt") jars, from Gradle)"
else
  rm -f "$CACHE/androidx-classpath.txt"
  echo "androidx cp    Gradle could not resolve it -- rerun (Maven Central rate-limits)."
fi

echo
echo "Ready. Run tools/check-all.sh"
