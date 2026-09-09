buildscript {
    repositories { google(); mavenCentral() }
    dependencies {
        // AGP 9 supplies its own Kotlin Gradle plugin; this is the documented
        // hook for pinning it to an exact version (AGP 9.0 release notes).
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.20")
    }
}
plugins {
    // AGP HOLDS AT 9.3.2. 9.4.0 EXISTS AND IT BREAKS THE accessibility JOB.
    //
    // Diagnosed from build 849's log rather than guessed. The `build` job passed
    // there and published the APK -- NDK 30, CMake 4.1.2, Gradle 9.7.1, Kotlin
    // 2.4.20 and build-tools 37 all work -- and only the `accessibility` job
    // failed, on `:app:mergeDebugAndroidTestAssets`:
    //
    //   Could not resolve androidx.concurrent:concurrent-futures:{strictly 1.1.0}
    //     1.1.0 - from lock file
    //     1.2.0 - transitively via androidx.test.ext:junit:1.3.0
    //   Could not resolve com.google.guava:listenablefuture:{strictly 1.0}
    //     ... because of the following reason: version resolved in configuration
    //     ':app:debugRuntimeClasspath' by consistent resolution
    //
    // That `{strictly}` is AGP's own consistent resolution, which pins the
    // androidTest classpath to whatever the app resolved. 9.4.0 changed how
    // hard it applies it, and the app's graph genuinely differs from the test
    // graph on those two: the app resolves concurrent-futures 1.1.0 and
    // listenablefuture 1.0, while the Accessibility Test Framework drags in
    // guava, whose listenablefuture is the empty 9999.0 marker.
    //
    // IT IS NOT core-splashscreen 1.2.0, and that was measured rather than
    // assumed: resolving the app's graph both ways with tools/fetch-deps.py
    // leaves concurrent-futures at 1.1.0 and listenablefuture at 1.0 either
    // way; the upgrade adds only appcompat-resources and two vectordrawable
    // artifacts.
    //
    // The fix is not obvious and cannot be tested in this container -- there is
    // no Gradle or Android SDK here -- so guessing at a resolutionStrategy would
    // be spending CI runs on a hunch. Forcing listenablefuture either way is
    // actively unsafe: 1.0 puts a second copy of ListenableFuture beside guava's,
    // and the 9999.0 marker is an EMPTY jar that would take the class out of the
    // app. So this holds one version back, which is the combination build 848
    // proved green.
    //
    // The owner's standing instruction is "sab kuchh latest", and this is the
    // same carve-out already recorded for lifecycle 2.12.0-alpha: latest means
    // the newest version that actually works, and a version that fails the
    // accessibility gate is not one. Revisit on the next AGP.
    id("com.android.application") version "9.3.2" apply false
    // Ships with Kotlin, so it must match the Kotlin version exactly.
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.20" apply false
}
