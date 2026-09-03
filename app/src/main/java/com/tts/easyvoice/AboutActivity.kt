package com.tts.easyvoice
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// About -- an Easy Voice screen with no AutoTTS counterpart, asked for on
// 2026-09-03: "hamara alag hi proper, hamara standalone hai". It is the one
// place that says whose app this is and what it is built on, and it took over
// the two facts the Advanced tab's "Information" section used to show at the
// bottom, so a screen reader meets them once rather than twice.
//
// A separate screen rather than an inline section, for the same reason
// Languages and Mode settings are screens: the license notice is long, and the
// Advanced tab is already the longest thing in the app to swipe through.
const val APACHE_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0"

class AboutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EasyVoiceTheme { AboutScreen() } }
    }
}

// Build number and version come from PackageManager, so they are this build's
// real numbers rather than literals that go stale. versionCode is set from
// github.run_number on the Gradle step, which is what makes "Build number"
// change from build to build.
@Composable
fun AboutScreen() {
    val context = LocalContext.current
    val versionInfo = remember {
        try {
            // c3.a0.a switches overload at API 33. The explicit type matters for
            // the local kotlinc check: without it, PackageInfoFlags being absent
            // from the API-15 jar makes the whole expression an error type and
            // every member read below it cascades into the noise.
            val info: android.content.pm.PackageInfo = if (android.os.Build.VERSION.SDK_INT >= 33)
                context.packageManager.getPackageInfo(context.packageName,
                    android.content.pm.PackageManager.PackageInfoFlags.of(0L))
            else { @Suppress("DEPRECATION") context.packageManager.getPackageInfo(context.packageName, 0) }
            val build = if (android.os.Build.VERSION.SDK_INT >= 28) {
                info.longVersionCode.toString()
            } else {
                @Suppress("DEPRECATION")
                info.versionCode.toString()
            }
            Pair(build, info.versionName ?: "")
        } catch (_: android.content.pm.PackageManager.NameNotFoundException) {
            Pair("", "")
        }
    }
    ResponsiveContent {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(bottom = 16.dp)) {
            // The app's name is the screen's first heading, so heading
            // navigation goes straight from it to "License".
            SectionHeader("Easy Voice")
            // Label and value are ONE Text each, so a screen reader reads
            // "Build number, 41" as a single stop rather than two.
            SettingDescription("Build number: " + versionInfo.first)
            SettingDescription("Version: " + versionInfo.second)
            SettingDescription("Developer: Sachin Baria")
            SettingDescription("Copyright © 2026 Sachin Baria. All rights reserved.")

            SectionHeader("License")
            SettingDescription("Easy Voice is built on open source software. The parts below are used under the Apache License, Version 2.0.")
            SettingDescription("Compact Language Detector 2 (CLD2), which is what reads the language of the text. Copyright © 2013 Google Inc. All rights reserved.")
            SettingDescription("The Android Open Source Project and the Jetpack libraries. Copyright © The Android Open Source Project.")
            ActionButton("Apache License 2.0", R.drawable.ic_open_in_new) {
                try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(APACHE_LICENSE_URL))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                } catch (_: android.content.ActivityNotFoundException) { }
            }
            SettingDescription("Opens the full license text at apache.org.")
        }
    }
}
