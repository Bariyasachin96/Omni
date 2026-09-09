#!/usr/bin/env bash
# Fetch the things the local checks need, into tools/.cache (gitignored).
#
#   android.jar   a real Android API jar, so kotlinc actually type-checks the
#                 android.* call sites instead of silently treating every one of
#                 them as an unresolved symbol. Without it kotlinc checks
#                 NOTHING at those call sites -- that is how a wrong trailing
#                 lambda once reached CI.
#   kotlinc       the Kotlin compiler, pinned to the version the app is built
#                 with (see app/build.gradle.kts).
#   compose-plugin.jar
#                 the Compose compiler plugin, so kotlinc applies Compose's OWN
#                 rules. Without it "a @Composable called from an ordinary
#                 function" compiles clean locally -- and every screen in this
#                 app is Compose.
#   m2/lib/*.jar  the androidx / Compose / Material3 classpath, resolved from
#                 app/build.gradle.kts by tools/fetch-deps.py. Without it every
#                 Compose call site is unresolved and therefore unchecked --
#                 which is how the missing @ExperimentalMaterial3Api opt-in
#                 reached CI and failed build 827.
#
# ANDROID.JAR COMES FROM ONE OF TWO PLACES, AND THE DIFFERENCE MATTERS.
# The real SDK platform lives on dl.google.com, which is Google Maven's host and
# is NOT in a cloud environment's "Trusted" allow-list. If it is reachable this
# fetches API 37 -- the app's own compileSdk, so android.* resolves completely.
# If it is not, it falls back to API 15 from Maven Central, which is old on
# purpose: the resulting noise is constant and cancels out in the baseline diff
# that tools/check/kotlin-typecheck.sh judges by.
#
# A 403 or a refused tunnel from the proxy is an egress-policy denial and is NOT
# to be routed around with a mirror. The fallback exists so the checks still run;
# to get the full jar, raise the environment's network access (see CLAUDE.md).
#
# Everything else the checks use (python3, javac, g++) is expected on PATH.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
CACHE="$ROOT/tools/.cache"
KOTLIN_VERSION=2.4.20

# The SDK platform whose android.jar we want, and the release of it. Both are
# read off dl.google.com's own repository2-3.xml, not guessed: the package is
# `platforms;android-37.0` and its one archive is this zip.
SDK_PLATFORM_ZIP=platform-37.0_r02.zip
SDK_PLATFORM_DIR=android-37.0
SDK_URL="https://dl.google.com/android/repository/$SDK_PLATFORM_ZIP"
FALLBACK_JAR_URL=https://repo1.maven.org/maven2/com/google/android/android/4.1.1.4/android-4.1.1.4.jar
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

# ---------------------------------------------------------------- android.jar
if [ -f "$CACHE/android.jar" ]; then
  echo "android.jar    already present ($(cat "$CACHE/android.jar.source" 2>/dev/null || echo 'source unknown'))"
else
  if fetch "$SDK_URL" "$CACHE/platform.zip"; then
    unzip -q -o -j "$CACHE/platform.zip" "$SDK_PLATFORM_DIR/android.jar" -d "$CACHE"
    rm -f "$CACHE/platform.zip"
    echo "API 37 (dl.google.com, $SDK_PLATFORM_ZIP)" > "$CACHE/android.jar.source"
    echo "android.jar    ok, API 37 from dl.google.com ($(wc -c < "$CACHE/android.jar") bytes)"
  else
    echo "android.jar    dl.google.com unreachable -- egress policy, not an error."
    echo "               falling back to API 15 from Maven Central."
    fetch "$FALLBACK_JAR_URL" "$CACHE/android.jar" || { echo "android.jar unavailable"; exit 1; }
    echo "API 15 (Maven Central fallback -- dl.google.com was unreachable)" > "$CACHE/android.jar.source"
    echo "android.jar    ok, API 15 ($(wc -c < "$CACHE/android.jar") bytes)"
  fi
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
if [ -s "$CACHE/androidx-classpath.txt" ]; then
  echo "androidx cp    already present ($(wc -l < "$CACHE/androidx-classpath.txt") jars)"
elif python3 "$ROOT/tools/fetch-deps.py" "$CACHE/androidx-classpath.txt"; then
  echo "androidx cp    ok ($(wc -l < "$CACHE/androidx-classpath.txt") jars)"
else
  rm -f "$CACHE/androidx-classpath.txt"
  echo "androidx cp    Google Maven unreachable -- egress policy, not an error."
  echo "               kotlin-typecheck.sh will run WITHOUT androidx and say so."
fi

echo
echo "Ready. Run tools/check-all.sh"
