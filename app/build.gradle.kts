plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace = "com.tts.easyvoice"
    compileSdk = 37
    ndkVersion = "30.0.16248370"
    defaultConfig {
        applicationId = "com.tts.easyvoice"
        // minSdk STAYS 24. It is the oldest Android this app will install on,
        // and raising it is the one number here that takes phones away -- at 37
        // it would install on almost nothing. compileSdk and targetSdk are what
        // "being on the latest API" actually means; minSdk is the opposite end.
        minSdk = 24
        // 37 = Android 17. Checked against its behaviour changes before moving,
        // because targetSdk is an opt-in to all of them, and three could have
        // bitten this app:
        //   * native libraries loaded with System.load() must now be read-only
        //     or throw UnsatisfiedLinkError. We use System.loadLibrary(), which
        //     loads from the APK's own read-only lib directory. Unaffected.
        //   * "static final" fields can no longer be modified by reflection.
        //     Our single reflection is TextToSpeech's mCurrentEngine, an
        //     INSTANCE field, read not written, inside a try/catch that falls
        //     back to the expected package. Unaffected, and it degrades if the
        //     non-SDK restrictions ever block it.
        //   * the opt-out from orientation and resizability restrictions on
        //     large screens is gone. We never declared screenOrientation or
        //     resizeableActivity, so there was no opt-out to lose.
        targetSdk = 37
        // The Advanced tab's Information section shows these two, the way
        // AutoTTS's does. A frozen versionCode = 1 would make "Build number"
        // say the same thing for every build and tell the user nothing, so
        // it comes from the workflow's run number; EV_BUILD_NUMBER is set on
        // the Gradle step and falls back to 1 for a local build.
        versionCode = (System.getenv("EV_BUILD_NUMBER") ?: "1").toInt()
        versionName = "16.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags += listOf("-std=c++17", "-Wno-narrowing", "-Os", "-g0", "-fvisibility=hidden", "-ffunction-sections", "-fdata-sections")
                arguments += listOf("-DANDROID_STL=c++_static")
            }
        }
        ndk { abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a")) }
    }
    externalNativeBuild { cmake { path = file("src/main/cpp/CMakeLists.txt"); version = "4.1.2" } }
    signingConfigs {
        create("release") {
            storeFile = rootProject.file("easyvoice-release.keystore")
            storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "sachin1211"
            keyAlias = System.getenv("KEY_ALIAS") ?: "sachin"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "sachin1211"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
        debug { isMinifyEnabled = false }
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    buildFeatures { compose = true }
    packaging {
        resources { excludes.add("**/libc++_shared.so") }
        jniLibs { useLegacyPackaging = true }
    }
}
dependencies {
    // Latest stable, checked against the androidx release notes on 2026-08-27.
    // core-ktx is what NotificationCompat, ServiceCompat and FileProvider come
    // from, so it is the one that actually earns its place.
    implementation("androidx.core:core-ktx:1.19.0")
    // lifecycle and coroutines are declared but not imported directly anywhere:
    // Compose and activity-compose pull both in, and Gradle resolves to the
    // highest, so the old 2.7.0 / 1.7.3 pins were already inert. Declared at
    // current stable rather than removed, so the resolved versions are stated
    // in one place instead of being whatever the transitive graph settles on.
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    // The app-opening splash. From API 31 the platform shows one for every app
    // whether we ask or not, built from the launcher icon and windowBackground;
    // this library backports the same thing to API 23 and gives one theme that
    // describes it on every level the app installs on (minSdk 24).
    //
    // 1.2.0, the latest stable (owner, 2026-09-09: "sab kuchh latest version
    // hi hona chahie"). This REVERSES the note that stood here, which argued for
    // staying on 1.0.1 -- do not restore that reasoning.
    //
    // What the upgrade costs, measured rather than guessed, so the decision is
    // recorded honestly: the public API is IDENTICAL (`javap` over both aars
    // lists the same members on SplashScreen), the class lists match but for one
    // inner lambda, and 1.2.0 adds a RUNTIME dependency on
    // androidx.appcompat:appcompat-resources:1.7.0 -- a 64 KB aar, and the first
    // AppCompat artifact in this project. That is the whole price, and the owner
    // has taken it knowingly in exchange for being current.
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation(platform("androidx.compose:compose-bom:2026.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    // Window size classes. This is the library the adaptive guidance is written
    // against -- currentWindowAdaptiveInfoV2() and the WindowSizeClass
    // breakpoints -- and it is NOT in the Compose BOM, which is why it carries
    // its own version. 1.3.0 is the latest stable (12 August 2026), from the
    // compose-material3-adaptive release notes.
    //
    // Only the `adaptive` artifact: adaptive-layout and adaptive-navigation are
    // the pane scaffolds, and we deliberately have no two-pane layout.
    implementation("androidx.compose.material3.adaptive:adaptive:1.3.0")

    // Accessibility checks, run against the real screens on a real emulator.
    // enableAccessibilityChecks() drives Google's Accessibility Test Framework
    // -- the same engine behind Accessibility Scanner -- so the four things
    // this project has been checking BY HAND for months become a test: missing
    // labels, colour contrast, touch target size, and traversal order.
    //
    // It needs API 34 and is a no-op under Robolectric, which is why the
    // workflow runs it on an emulator rather than as a unit test.
    //
    // ui-test-junit4-accessibility is pinned rather than left to the BOM: the
    // BOM's published mapping table lists ui-test, ui-test-junit4 and
    // ui-test-manifest but not this one, and every artifact in the
    // androidx.compose.ui group shares one version line, which this BOM puts at
    // 1.12.0. If a future BOM starts managing it, drop the version.
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4-accessibility:1.12.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    // Supplies the empty activity createAndroidComposeRule<ComponentActivity>()
    // launches; debug-only, so it never reaches the release APK.
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
