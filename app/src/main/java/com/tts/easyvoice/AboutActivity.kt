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
// The licence text on this screen is read off CLD2's own repository rather than
// copied from anywhere else, and it was checked there rather than remembered:
//
//   github.com/CLD2Owners/cld2 -- what build.yml clones into cld2_src
//     LICENSE          the Apache 2.0 text, byte-identical to our clone's copy
//     README.md        "Compact Language Detector 2", Dick Sites (dsites@google.com),
//                      "These 83 languages are detected"
//     every .cc/.h     "Copyright 2013 Google Inc. All Rights Reserved." plus the
//                      Apache notice; 21 of the 24 files we compile say 2013 and
//                      three say 2014, which is why the notice reads "2013, 2014"
//     NOTICE           DOES NOT EXIST (HTTP 404), so Apache 2.0 section 4(d) asks
//                      us to reproduce nothing extra -- the copyright line and a
//                      pointer to the License is the whole obligation
const val APACHE_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0"
const val CLD2_URL = "https://github.com/CLD2Owners/cld2"

private fun openLink(context: android.content.Context, url: String) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    } catch (_: android.content.ActivityNotFoundException) { }
}

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
            // Everything in this block is taken from CLD2's OWN repository --
            // its README, its LICENSE and the header its source files carry --
            // not from any second-hand summary. See the comment above CLD2_URL.
            SettingDescription("Compact Language Detector 2, written by Dick Sites at Google, is what reads the language of your text. It recognises 83 languages from UTF-8, and it is compiled into Easy Voice from the sources at github.com/CLD2Owners/cld2.")
            SettingDescription("Copyright 2013, 2014 Google Inc. All Rights Reserved.")
            // The two paragraphs below are the Apache 2.0 notice verbatim, as it
            // appears at the top of every CLD2 file Easy Voice compiles and in
            // the appendix of the repository's LICENSE. Do not paraphrase them.
            SettingDescription("Licensed under the Apache License, Version 2.0 (the \"License\"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0")
            SettingDescription("Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.")
            ActionButton("Read the Apache License 2.0", R.drawable.ic_open_in_new) {
                openLink(context, APACHE_LICENSE_URL)
            }
            ActionButton("Compact Language Detector 2 on GitHub", R.drawable.ic_open_in_new) {
                openLink(context, CLD2_URL)
            }
            SettingDescription("Easy Voice is also built with the Android Open Source Project and the Jetpack libraries, Copyright The Android Open Source Project, under that same Apache License, Version 2.0.")
        }
    }
}
