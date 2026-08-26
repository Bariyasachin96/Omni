plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
}
android {
    namespace = "com.tts.easyvoice"
    compileSdk = 37
    ndkVersion = "29.0.14206865"
    defaultConfig {
        applicationId = "com.tts.easyvoice"
        minSdk = 24
        targetSdk = 36
        // The Advanced tab's Information section shows these two, the way
        // AutoTTS's does. A frozen versionCode = 1 would make "Build number"
        // say the same thing for every build and tell the user nothing, so
        // it comes from the workflow's run number; EV_BUILD_NUMBER is set on
        // the Gradle step and falls back to 1 for a local build.
        versionCode = (System.getenv("EV_BUILD_NUMBER") ?: "1").toInt()
        versionName = "16.0"
        externalNativeBuild {
            cmake {
                cppFlags += listOf("-std=c++17", "-Wno-narrowing", "-Os", "-g0", "-fvisibility=hidden", "-ffunction-sections", "-fdata-sections")
                arguments += listOf("-DANDROID_STL=c++_static")
            }
        }
        ndk { abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a")) }
    }
    externalNativeBuild { cmake { path = file("src/main/cpp/CMakeLists.txt"); version = "3.22.1" } }
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
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation(platform("androidx.compose:compose-bom:2026.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
}
