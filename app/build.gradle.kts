plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace = "com.sachinbaria.easyvoice"
    compileSdk = 37
    ndkVersion = "30.0.16248370"
    defaultConfig {
        applicationId = "com.sachinbaria.easyvoice"
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
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                // EVERY NATIVE SIZE FLAG, MEASURED AND PROVEN (owner, 2026-09-09:
                // "extremely rules laga do ... full full extremely"). This REPLACES the
                // earlier pass, which rejected -Oz and -fno-exceptions on reasoning that
                // measurement has now refuted -- read the CLAUDE.md section before
                // reverting any of them.
                //
                // Measured by compiling the exact source list CMakeLists names, with a
                // base that already carries `--pack-dyn-relocs=android`, because clang's
                // own driver adds that below API 28 (Linux.cpp:288) and minSdk is 24 --
                // so the numbers below are what a real Android build actually gains:
                //
                //   no exceptions/rtti/unwind + inlines-hidden   -42,864   gz -17,419
                //   + --icf=safe                                 -45,760   gz -17,492
                //   + -flto=thin                                 -58,896   gz -19,832
                //   + -Oz instead of -Os                         -75,528   gz -26,463
                //
                // and the APK ships two ABIs compressed, so about 53 KB of download.
                //
                // -fno-exceptions -fno-rtti IS BEHAVIOUR-NEUTRAL HERE, not a trade:
                // there is not one try, catch, throw, dynamic_cast or typeid in our core
                // OR in CLD2. Nothing can be caught that was not already going to
                // terminate. -fno-unwind-tables follows from that; it drops 18 KB of
                // .eh_frame metadata that nothing unwinds.
                //
                // -Oz and -flto=thin DO change codegen, so they were proven rather than
                // argued -- all three behaviour harnesses were re-run under these exact
                // flags (EV_CXXFLAGS, added for this) and are IDENTICAL: segmenter over
                // 163,296 cases, normaliser over 1,114,112 code points, script family
                // over 15 sets x 1,114,112. Latency is unchanged within noise (the
                // 3,520-char ceiling reads 1.11 ms against 1.16 ms), because the hot
                // path is table lookups rather than code. All seven JNI entry points
                // still resolve -- `nm -D --defined-only` finds 7 of 7 on the most
                // aggressive build, which is the one way this could fail at runtime.
                cppFlags += listOf(
                    "-std=c++17", "-Wno-narrowing", "-Oz", "-g0",
                    "-fvisibility=hidden", "-fvisibility-inlines-hidden",
                    "-ffunction-sections", "-fdata-sections",
                    "-fno-exceptions", "-fno-rtti",
                    "-fno-unwind-tables", "-fno-asynchronous-unwind-tables",
                    "-flto=thin",
                )
                // THE FLAGS ABOVE WERE NEVER IN EFFECT UNTIL 2026-09-24. AGP builds
                // the release library as CMake's RelWithDebInfo, and CMake puts
                // CMAKE_CXX_FLAGS_RELWITHDEBINFO ("-O2 -g -DNDEBUG") AFTER
                // CMAKE_CXX_FLAGS on every compile line, so the last -O and -g won:
                // the shipped library was -O2 with debug info, not -Oz -g0. Read
                // off the real compile_commands.json, not assumed. The per-config
                // flags are now only -DNDEBUG, which keeps what was there (asserts
                // off) and lets the flags above decide the optimisation.
                arguments += listOf(
                    "-DANDROID_STL=c++_static",
                    "-DCMAKE_CXX_FLAGS_RELWITHDEBINFO=-DNDEBUG",
                    "-DCMAKE_C_FLAGS_RELWITHDEBINFO=-DNDEBUG",
                    "-DCMAKE_CXX_FLAGS_RELEASE=-DNDEBUG",
                    "-DCMAKE_C_FLAGS_RELEASE=-DNDEBUG",
                )
            }
        }
        ndk { abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a")) }
    }
    // ONE ABI PER DOWNLOAD (owner, 2026-09-09: "complete full CLD2 rahe aur file
    // size bhi kam ho jayegi"). This is the change that answers that, and it is
    // the only one that does -- the research is written up in CLAUDE.md and in
    // CMakeLists.txt, and it comes down to one measured fact: the library is 95%
    // CLD2 lookup TABLES, the tables are near-random so they barely compress
    // (the APK's own deflate gets 6,165,933 -> 4,653,696 and xz -9e manages only
    // 4,106,444), and the APK ships the whole thing TWICE, once per ABI.
    //
    // So the duplicate copy is the size, and splitting removes it without
    // dropping one language, one device or one byte of the detector. A phone
    // installs the APK for its own ABI:
    //
    //     universal (today)   11,410,305 bytes   build 859, from the API
    //     arm64-v8a only      about 6.7 MB       what a modern phone needs
    //     armeabi-v7a only    about 6.7 MB       32-bit phones
    //
    // CLD2's OWN CLD2_DYNAMIC_MODE was researched as the alternative -- it mmaps
    // the tables from a data file instead of linking them in, so one 6.17 MB
    // asset would serve both ABIs. It solves the SAME duplicate, so the two do
    // not add up, and it lands on a WORSE number (an uncompressed asset has to
    // be stored uncompressed to be mmapped, giving about 8.4 MB) while adding a
    // failure mode where a missing asset leaves the app detecting nothing at
    // all. Not taken. Do not re-propose it without a reason the split cannot
    // serve.
    //
    // isUniversalApk keeps the every-ABI build, and the workflow still publishes
    // it under the name it has always had, so nothing the owner already
    // downloads changes; the two smaller files are added beside it.
    //
    // GATED ON A PROPERTY, and that is deliberate: the accessibility job builds
    // the debug and androidTest variants and drives them on an emulator, and
    // splits apply to every variant. Only the release step passes -PevAbiSplit,
    // so the job that is hardest to debug builds exactly what it built on the
    // last green run.
    if (project.hasProperty("evAbiSplit")) {
        splits {
            abi {
                isEnable = true
                reset()
                include("armeabi-v7a", "arm64-v8a")
                isUniversalApk = true
            }
        }
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
    // buildConfig: the About screen reads BuildConfig.VERSION_NAME instead of
    // asking PackageManager for the app's own version at run time.
    buildFeatures { compose = true; buildConfig = true }
    packaging {
        resources { excludes.add("**/libc++_shared.so") }
        jniLibs { useLegacyPackaging = true }
    }
}
// THE KOTLIN COMPILER, STATED (2026-09-24). Nothing set it before: the JVM
// target was whatever AGP derived from compileOptions (17, read off the class
// files), and a warning passed the CI build and was caught only by
// tools/check/gradle-compile.sh on a machine that ran it. The target is written
// here beside compileOptions so the two cannot drift, and any compiler warning
// in app or androidTest code now fails the build itself -- the code stands at
// zero warnings, so this changes nothing today and stops the next one.
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        allWarningsAsErrors.set(true)
    }
}
// THE OTHER HALF OF THE SAME FAILURE: com.google.guava:listenablefuture.
//
// The app has it at 1.0 (androidx.core and profileinstaller both depend on it),
// so AGP's consistent resolution pins the androidTest classpath to
// `strictly 1.0`. But the Accessibility Test Framework 4.1.1 drags in
// guava 31.0.1-android, whose listenablefuture is the EMPTY marker
// 9999.0-empty-to-avoid-conflict-with-guava -- it exists precisely so that the
// standalone jar is dropped when the real guava is present. `strictly 1.0` can
// never accept 9999.0, so the two requirements are unsatisfiable together.
//
// Excluding the module from the androidTest configurations removes every
// dependency edge to it there, and a Gradle constraint only constrains a module
// that is otherwise in the graph -- so the pin becomes inert. Nothing is lost at
// runtime: guava itself carries com.google.common.util.concurrent.ListenableFuture,
// which is the whole reason the empty marker exists.
//
// The APP's own classpath is untouched -- it keeps listenablefuture 1.0, which is
// where the class comes from there. This is androidTest only, so the release APK
// the owner installs cannot be affected by it.
configurations.configureEach {
    if (name.contains("AndroidTest")) {
        exclude(group = "com.google.guava", module = "listenablefuture")
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
    // ProcessLifecycleOwner: "is any of the app's screens showing?" for the whole
    // process. The service's background engine scan waits for the screens to
    // close (the Languages screen's rows must not move under it). It was already
    // in the APK -- emoji2 pulls it in at runtime and androidx.startup's
    // InitializationProvider starts it -- and is declared now that the app calls it.
    implementation("androidx.lifecycle:lifecycle-process:2.11.0")
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
    // Installs the ART profile (app/src/main/baseline-prof.txt plus the libraries'
    // own) on sideloaded installs too, where Play's cloud profiles never arrive --
    // which is how this app is installed. It was already in the graph through
    // Compose; declared so the profile does not depend on that.
    implementation("androidx.profileinstaller:profileinstaller:1.4.1")
    // ALIGNS THE APP WITH THE androidTest CLASSPATH -- one of the two halves of
    // the accessibility job's old dependency failure. The app used to resolve
    // concurrent-futures 1.1.0 (via profileinstaller) while androidx.test:core
    // 1.7.0 pulls concurrent-futures-ktx 1.2.0 -> concurrent-futures 1.2.0, and
    // AGP's consistent resolution pins the test classpath to whatever the app
    // resolved, as `strictly`. Declaring it here makes that pin a version the
    // test graph can accept.
    //
    // 1.3.0 NOW, the latest stable (owner, 2026-09-23: "baki aur bhi chijen jo
    // hogi vah bhi update kar do"). The test graph asks for 1.2.0 as a plain
    // `requires`, which 1.3.0 satisfies; 1.3.0's own module constrains
    // concurrent-futures-ktx to 1.3.0, so the test side moves with it. Its
    // runtime needs are annotation 1.8.1, listenablefuture 1.0 and jspecify --
    // nothing the app does not already carry.
    implementation("androidx.concurrent:concurrent-futures:1.3.0")
    implementation(platform("androidx.compose:compose-bom:2026.09.00"))
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

    // THE LIBRARIES NOTHING HERE DECLARES, RAISED TO THEIR LATEST STABLE (owner,
    // 2026-09-23: "baki aur bhi chijen jo hogi vah bhi update kar do ... properly
    // research karke update"). Every direct dependency above was already current;
    // these are the ones Compose, activity, core and splashscreen pull in, and
    // Gradle had settled them on whatever the oldest requester asked for.
    //
    // The list is what build 903's own APK carries -- read from its META-INF
    // *.version files, not guessed from the graph -- compared with each
    // artifact's maven-metadata.xml. For every one of them the new release's
    // own Gradle module and AAR manifest were read before it went in:
    //   * minSdk is 23 or lower for all of them (ours is 24);
    //   * nothing they require is newer than what the app already resolves
    //     (Compose runtime 1.11.x, lifecycle 2.9.x, coroutines 1.9.0, Kotlin
    //     stdlib 2.3.20 at most -- we are on 1.12.1 / 2.11.0 / 1.11.0 / 2.4.20);
    //   * graphics-path is the one with a native library, and 1.1.0's
    //     libandroidx.graphics.path.so is 16 KB aligned on every ABI.
    //
    // CONSTRAINTS, NOT DEPENDENCIES: a constraint only raises a module that is
    // already in the graph and never adds one, so nothing new reaches the APK.
    // And since AGP pins the androidTest classpath to what the app resolves,
    // raising the app to the newest stable is also what keeps a test library
    // from ever asking for more than the app has.
    //
    // androidx.tracing IS ON 2.0.2, A MAJOR VERSION, AND THAT WAS CHECKED BEFORE
    // IT WENT IN (owner, 2026-09-23: "jo nahin kiya hai vah bhi update kar hi
    // lo"). A major version may break the 1.x callers inside startup-runtime and
    // androidx.test, so the two jars were compared with javap: androidx.tracing.
    // Trace and TraceKt in 2.0.2 carry every 1.3.0 member unchanged (plus a
    // setCounter(String, long) and a TAG), so those callers still link. Its
    // aar says minSdk 23; it needs annotation 1.10.0, collection 1.6.0 and
    // coroutines 1.9.0, all at or below what the app resolves.
    // Where one member of a family is named (savedstate, navigationevent,
    // window, vectordrawable), the library's own constraints bring the rest of
    // the family to the same version.
    constraints {
        implementation("androidx.annotation:annotation-experimental:1.6.0")
        implementation("androidx.appcompat:appcompat-resources:1.8.0")
        implementation("androidx.autofill:autofill:1.3.0")
        implementation("androidx.collection:collection:1.6.0")
        implementation("androidx.customview:customview-poolingcontainer:1.1.0")
        implementation("androidx.emoji2:emoji2:1.6.0")
        implementation("androidx.graphics:graphics-path:1.1.0")
        implementation("androidx.navigationevent:navigationevent:1.1.2")
        implementation("androidx.navigationevent:navigationevent-compose:1.1.2")
        implementation("androidx.profileinstaller:profileinstaller:1.4.1")
        implementation("androidx.savedstate:savedstate:1.5.0")
        implementation("androidx.savedstate:savedstate-compose:1.5.0")
        implementation("androidx.savedstate:savedstate-ktx:1.5.0")
        implementation("androidx.startup:startup-runtime:1.2.0")
        implementation("androidx.tracing:tracing:2.0.2")
        implementation("androidx.vectordrawable:vectordrawable:1.2.0")
        implementation("androidx.vectordrawable:vectordrawable-animated:1.2.0")
        implementation("androidx.versionedparcelable:versionedparcelable:1.2.1")
        implementation("androidx.window:window:1.5.1")
        implementation("androidx.window:window-core:1.5.1")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.11.0")
        implementation("org.jspecify:jspecify:1.0.1")
    }

    // Accessibility checks, run against the real screens on a real emulator.
    // enableAccessibilityChecks() drives Google's Accessibility Test Framework
    // -- the same engine behind Accessibility Scanner -- so the four things
    // this project has been checking BY HAND for months become a test: missing
    // labels, colour contrast, touch target size, and traversal order.
    //
    // It needs API 34 and is a no-op under Robolectric, which is why the
    // workflow runs it on an emulator rather than as a unit test.
    //
    // ui-test-junit4-accessibility USED TO CARRY ITS OWN VERSION and no longer
    // needs to. The note here said "if a future BOM starts managing it, drop the
    // version", and BOM 2026.09.00 does -- read out of the BOM's own pom, which
    // lists ui-test-junit4-accessibility at 1.12.1 beside ui and ui-android.
    // So the whole androidx.compose.ui line is managed from one place again,
    // which is the point of a BOM and the thing a hand-pinned artifact quietly
    // breaks the first time the BOM moves.
    //
    // THE BOM ITSELF WENT 2026.08.00 -> 2026.09.00, and it is a patch move
    // rather than a feature one: compose.ui and compose.foundation go 1.12.0 ->
    // 1.12.1 and material3 STAYS at 1.4.0. That last part is what makes it safe
    // here -- every component this app argues with in CLAUDE.md
    // (ExposedDropdownMenuBox and its OptIn, PrimaryTabRow, FilterChip, Slider's
    // sliderSemantics) is material3, and material3 does not move.
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4-accessibility")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    // Supplies the empty activity createAndroidComposeRule<ComponentActivity>()
    // launches; debug-only, so it never reaches the release APK.
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
