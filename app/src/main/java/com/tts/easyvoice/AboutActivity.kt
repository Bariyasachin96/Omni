package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.PackageInfoCompat

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
// THE TWO LINK BUTTONS ARE GONE (owner, 2026-09-10: "jo donon buttons hai
// about page mein vah button nahin rakhne hain"). They opened the Apache
// licence and the CLD2 repository in a browser. Nothing is lost from the
// NOTICE either way: Apache 2.0 section 4(a) asks for a copy of the licence and
// 4(d) for the NOTICE file if one exists -- CLD2 has none (HTTP 404) -- and the
// two paragraphs below reproduce the notice verbatim while the licence's own
// URL is written into the text of the first one. A link is a convenience, not
// an obligation, and this screen still states every term.
//
// APACHE_LICENSE_URL, CLD2_URL and openLink() went with them; nothing else
// referenced any of the three.
class AboutActivity : EvActivity() {
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
    // Still needed after the two link buttons went: the version block below
    // reads packageManager and packageName through it.
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
            // PackageInfoCompat, not a hand-written SDK_INT >= 28 branch. The
            // library's body is the same two lines --
            //     if (Build.VERSION.SDK_INT >= 28) return Api28Impl
            //         .getLongVersionCode(info); return info.versionCode;
            // -- with the API-28 call isolated in a nested class, which is the
            // documented shape for a version-gated call and the one that cannot
            // be tripped up by an eager verifier on an old device.
            //
            // The SERVICE keeps its own inline branch on purpose: onCreate's
            // version log is AutoTTS-mirrored code and rule 5 governs it. This
            // screen has no AutoTTS counterpart at all, so the library form is
            // free to be used here.
            Pair(PackageInfoCompat.getLongVersionCode(info).toString(), info.versionName ?: "")
        } catch (_: android.content.pm.PackageManager.NameNotFoundException) {
            Pair("", "")
        }
    }
    ResponsiveContent {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            // The heading names the SCREEN, exactly as every other screen in the
            // app does -- "Languages" on LanguagesActivity, "<Mode> settings" on
            // ModeSettingsActivity. The first version put the app's name here
            // instead, so heading navigation opened on "Easy Voice", which reads
            // as content rather than as the heading of the page you just entered.
            SectionHeader("About")
            // The app's name is the page's subject, so it is a real title rather
            // than another line of body text.
            Text(
                text = "Easy Voice",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
            )
            // THE ORDER IS THE OWNER'S, stated on 2026-09-17: "vertically stack
            // the app name, version, build number, developer name, and
            // copyright". Version now comes BEFORE build number -- it was the
            // other way round -- so the block reads from what the app IS down to
            // who owns it. Nothing else about these four lines changed.
            //
            // Label and value are ONE Text each, so a screen reader reads
            // "Build number, 41" as a single stop rather than two.
            SettingDescription("Version: " + versionInfo.second)
            SettingDescription("Build number: " + versionInfo.first)
            SettingDescription("Developer: Sachin Baria")
            SettingDescription("Copyright \u00a9 2026 Sachin Baria. All rights reserved.")

            // THE LICENCES MOVED TO THEIR OWN SCREEN (owner, 2026-09-17:
            // "followed by a dedicated 'View Licenses' button that displays all
            // open-source license information"). They were the longest thing on
            // this page by a wide margin -- one heading and five paragraphs of
            // verbatim notice -- and a blind user reaching About for the build
            // number had to swipe through every one of them.
            //
            // A SCREEN rather than an expanding section, for the reason every
            // other screen in this app is one: a new window makes a reader
            // announce its title, so "Open source licenses" is spoken on entry.
            // An in-place disclosure announces nothing at all.
            //
            // Nothing is lost from the NOTICE by moving it. Apache 2.0 section
            // 4(a) asks that recipients receive the licence and 4(d) that a
            // NOTICE file be reproduced if one exists -- CLD2 has none (HTTP
            // 404) -- and every word of the notice is still in the app, one tap
            // away, with the licence's own URL written into its text.
            ActionButton("View Licenses", R.drawable.ic_info) {
                context.startActivity(android.content.Intent(context, LicensesActivity::class.java))
            }
        }
    }
}
