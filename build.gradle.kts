buildscript {
    repositories { google(); mavenCentral() }
    dependencies {
        // AGP 9 supplies its own Kotlin Gradle plugin; this is the documented
        // hook for pinning it to an exact version (AGP 9.0 release notes).
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.4.20")
    }
}
plugins {
    id("com.android.application") version "9.4.0" apply false
    // Ships with Kotlin, so it must match the Kotlin version exactly.
    id("org.jetbrains.kotlin.plugin.compose") version "2.4.20" apply false
}
