// Every version comes from gradle/libs.versions.toml (the version catalog).
buildscript {
    repositories { google(); mavenCentral() }
    dependencies {
        // AGP 9 compiles Kotlin itself (built-in Kotlin) on top of the Kotlin
        // Gradle plugin it depends on; putting a newer one on the buildscript
        // classpath is how its own release notes say to use it ("Use a higher
        // KGP or KSP version", AGP 9.0).
        classpath(libs.kotlin.gradle.plugin)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    // Ships with Kotlin, so it is the catalog's Kotlin version exactly.
    alias(libs.plugins.kotlin.compose) apply false
}
