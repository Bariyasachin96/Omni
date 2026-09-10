buildscript {
    repositories { google(); mavenCentral() }
    dependencies {
        // AGP 9 supplies its own Kotlin Gradle plugin; this is the documented
        // hook for pinning it to an exact version (AGP 9.0 release notes).
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.20")
    }
}
plugins {
    // AGP 9.4.0, AND GRADLE MOVES WITH IT -- THE HOLD IS LIFTED (2026-09-10).
    //
    // This used to pin 9.3.2 with a long note blaming 9.4.0 for the
    // accessibility job's dependency failure. THAT DIAGNOSIS WAS REFUTED BY
    // BUILD 854, which ran on 9.3.2 and failed with the identical message, and
    // two later attempts (android.dependency.useConstraints=false, and reverting
    // Gradle) were refuted the same way. The real cause was never a version at
    // all: the app's graph and the androidTest graph genuinely disagreed on
    // concurrent-futures and on guava's empty listenablefuture marker, and both
    // conflicts were older than any of those bumps.
    //
    // That is FIXED, in app/build.gradle.kts, and the fix has now been green
    // three times running (861, 862, 863): the app declares
    // concurrent-futures 1.2.0 so the `strictly` pin becomes the version the
    // test graph asks for, and every *AndroidTest* configuration excludes
    // com.google.guava:listenablefuture so the constraint has nothing left to
    // constrain. With that in place the note this replaces said in its own
    // words: "AGP can go back to 9.4.0 once a run is green -- it was never the
    // cause."
    //
    // GRADLE 9.7.1 IS NOT A SEPARATE DECISION, it is a REQUIREMENT of this one.
    // AGP 9.4's own release notes give its minimum Gradle as 9.6.0, and the
    // workflow pinned 9.5.0, so the two have to move together or the build fails
    // at configuration. 9.7.1 is the version builds 849-857 already ran on, so
    // it is not new ground either -- it was reverted to 9.5.0 during the
    // firefight and proven innocent by build 857.
    //
    // This is deliberately its OWN commit, with nothing else in it that can
    // touch the build, because this file's own recorded lesson is that a change
    // which can only be tested by a thirteen-minute CI run must never be stacked
    // with another.
    id("com.android.application") version "9.4.0" apply false
    // Ships with Kotlin, so it must match the Kotlin version exactly.
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.20" apply false
}
