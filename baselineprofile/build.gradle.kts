// THE GENERATED BASELINE PROFILE (owner, 2026-10-07: item 3 of the kernel-level
// list). A Macrobenchmark test module whose one test, BaselineProfileGenerator,
// drives the REAL app on a device -- the main screen with its engine scan, then
// the TTS service itself through a TextToSpeech client bound to our engine --
// and records which methods ran. `./gradlew :app:generateBaselineProfile` runs
// it on the connected emulator (CI's baseline-profile job; no KVM in the dev
// container) and writes app/src/main/generated/baselineProfiles/, which the
// release build compiles ahead of time on install and uses for dex layout.
plugins {
    id("com.android.test")
    id("androidx.baselineprofile")
}
android {
    namespace = "com.sachinbaria.easyvoice.baselineprofile"
    compileSdk = 37
    defaultConfig {
        // BaselineProfileRule needs API 28+ at run time; profiles are collected
        // on CI's API 34 emulator. The app itself still installs from 24.
        minSdk = 28
        targetSdk = 37
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    targetProjectPath = ":app"
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
}
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        allWarningsAsErrors.set(true)
    }
}
// The emulator CI starts, not a Gradle managed device: the job already has one
// running, and a managed device would download a second system image.
baselineProfile { useConnectedDevices = true }
dependencies {
    implementation("androidx.test.ext:junit:1.3.0")
    implementation("androidx.test.uiautomator:uiautomator:2.4.0")
    implementation("androidx.benchmark:benchmark-macro-junit4:1.5.0")
}
