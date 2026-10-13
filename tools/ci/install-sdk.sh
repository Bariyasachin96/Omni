#!/usr/bin/env bash
# Install the NDK, CMake, build-tools and compileSdk platform this build uses --
# the versions the version catalog (gradle/libs.versions.toml) and the app's
# build file state, so CI cannot drift from the build. One script for all three
# CI jobs; each used to carry its own copy of these steps with the versions
# typed in.
#
# WITH THE ANDROID CLI (`android sdk install`), NOT sdkmanager. Since command-line
# tools 23.0 sdkmanager says of itself "The SDK Manager CLI tool (sdkmanager) is
# deprecated. Android CLI will be used instead ... 'android sdk' is the
# replacement for 'sdkmanager'" (developer.android.com/tools/agents/android-cli).
# The workflow asks setup-android for those command-line tools, which put
# `android` on the PATH. It accepts the SDK licence itself, non-interactively.
#
# `android sdk install` answers 0 even for a package it could not find
# ("Package ... not found."), so success is the package's directory, checked
# after each attempt. A corrupt download is the usual reason a CI install fails,
# so a failed attempt clears what it left and tries again (EV_SDK_ATTEMPTS,
# default 3), and the step FAILS HERE if a package never lands -- not minutes
# later inside the native build with a message that points nowhere.
set -u
ROOT=$(cd "$(dirname "$0")/../.." && pwd)
catalog() { sed -n "s/^$1 *= *\"\([^\"]*\)\".*/\1/p" "$ROOT/gradle/libs.versions.toml" | head -1; }
NDK=$(catalog ndk); CMAKE=$(catalog cmake); BUILD_TOOLS=$(catalog buildTools)
COMPILE_SDK=$(grep -oE '^\s*compileSdk\s*=\s*[0-9]+' "$ROOT/app/build.gradle.kts" | grep -oE '[0-9]+$')
[ -n "$NDK" ] && [ -n "$CMAKE" ] && [ -n "$BUILD_TOOLS" ] && [ -n "$COMPILE_SDK" ] \
  || { echo "::error::could not read the SDK versions from the version catalog"; exit 1; }
: "${ANDROID_HOME:?ANDROID_HOME is not set}"
ANDROID=$(command -v android || true)
[ -n "$ANDROID" ] || { echo "::error::the Android CLI ('android') is not on the PATH -- setup-android must install command-line tools 23.0 or newer"; exit 1; }
echo "using $ANDROID ($("$ANDROID" --version 2>/dev/null | tail -1))"
echo "installing ndk $NDK, cmake $CMAKE, build-tools $BUILD_TOOLS, platform $COMPILE_SDK"
# A package is installed when its directory carries the manifest the installer
# writes last (package.xml, or source.properties); a directory without one is
# what a cut-off download leaves.
complete() { [ -f "$1/package.xml" ] || [ -f "$1/source.properties" ]; }
PKGS="ndk/$NDK cmake/$CMAKE build-tools/$BUILD_TOOLS"
have_all() { for p in $PKGS; do complete "$ANDROID_HOME/$p" || return 1; done; }
ATTEMPTS=${EV_SDK_ATTEMPTS:-3}
n=0
until [ "$n" -ge "$ATTEMPTS" ]; do
  # shellcheck disable=SC2086
  "$ANDROID" --sdk="$ANDROID_HOME" sdk install $PKGS < /dev/null
  if have_all; then echo "NDK, CMake and build-tools installed."; break; fi
  n=$((n + 1))
  echo "::warning::SDK install attempt $n left a package missing; clearing what is half there and trying again"
  # Only a HALF-installed package is removed; a complete one is never touched.
  for p in $PKGS; do
    if [ -d "$ANDROID_HOME/$p" ] && ! complete "$ANDROID_HOME/$p"; then rm -rf "${ANDROID_HOME:?}/$p"; fi
  done
  sleep $((n * 10))
done
have_all || { echo "::error::the NDK, CMake or build-tools did not install after $n attempts. Failing here rather than in the native build."; exit 1; }
# The platform: best effort. AGP fetches a missing platform itself. The current
# repository names it android-37.0; older ones android-37.
"$ANDROID" --sdk="$ANDROID_HOME" sdk install "platforms/android-$COMPILE_SDK.0" < /dev/null
[ -d "$ANDROID_HOME/platforms/android-$COMPILE_SDK.0" ] || [ -d "$ANDROID_HOME/platforms/android-$COMPILE_SDK" ] \
  || "$ANDROID" --sdk="$ANDROID_HOME" sdk install "platforms/android-$COMPILE_SDK" < /dev/null
[ -d "$ANDROID_HOME/platforms/android-$COMPILE_SDK.0" ] || [ -d "$ANDROID_HOME/platforms/android-$COMPILE_SDK" ] \
  || echo "::warning::platform $COMPILE_SDK did not install; AGP will fetch it"
exit 0
