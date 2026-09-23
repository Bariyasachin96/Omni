package com.sachinbaria.easyvoice
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.AlertDialog
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
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

// Version comes from PackageManager, so it is this build's real number rather
// than a literal that goes stale.
@Composable
fun AboutScreen() {
    val context = LocalContext.current
    var showLicenses by remember { mutableStateOf(false) }
    val versionName = remember {
        try {
            val info: android.content.pm.PackageInfo = if (android.os.Build.VERSION.SDK_INT >= 33)
                context.packageManager.getPackageInfo(context.packageName,
                    android.content.pm.PackageManager.PackageInfoFlags.of(0L))
            else { @Suppress("DEPRECATION") context.packageManager.getPackageInfo(context.packageName, 0) }
            info.versionName ?: ""
        } catch (_: android.content.pm.PackageManager.NameNotFoundException) {
            ""
        }
    }
    ResponsiveContent {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            // The heading names the SCREEN, as every other screen's first heading
            // does; the app's name sits under it as the page's title.
            SectionHeader("About")
            Text(
                text = "Easy Voice",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
            )
            // Owner, 2026-09-23 (second message): the owner's name belongs on
            // the Developer line and in the copyright -- "main uska owner hun".
            // The build number stays off, as asked the first time.
            SettingDescription("Version: " + versionName)
            SettingDescription("Developer: Sachin Baria")
            SettingDescription("Copyright \u00a9 2026 Sachin Baria. All rights reserved.")
            // A DIALOG, not a screen (owner, 2026-09-23: "licence ki activity
            // hatao ... ek dialog rakho"). A dialog is its own window, so a
            // screen reader still speaks its title when it opens, and back or the
            // Close button dismisses it.
            ActionButton("View Licenses", R.drawable.ic_info) { showLicenses = true }
        }
    }
    if (showLicenses) LicensesDialog { showLicenses = false }
}

// ONLY WHAT THE LICENCE REQUIRES, AND NOTHING ELSE (owner, 2026-09-23: "jo
// Apache 2.0 usmein aata hai vahi dalo, baki extra nahin").
//
// Everything third-party in the APK is Apache 2.0: CLD2, AndroidX / Compose,
// the Kotlin standard library and kotlinx.coroutines, the Material icons. For a
// BINARY, section 4 asks for exactly one thing -- 4(a), a copy of the License.
// 4(b) is about modified source files and 4(c) says "in the Source form", so
// neither applies to an APK; 4(d) applies only to a work that ships a NOTICE
// file, and none of these does. Checked, not assumed: no NOTICE inside any of
// the jars and aars the build resolves, and HTTP 404 for a NOTICE in the CLD2,
// androidx, kotlinx.coroutines and material-design-icons repositories. The
// Kotlin repo's license/NOTICE.txt is headed "in this case for the Kotlin
// Compiler distribution", which the app does not ship. The NDK's libc++ is
// Apache 2.0 WITH the LLVM exception, which waives 4(a), 4(b) and 4(d) for code
// compiled into a binary.
//
// So the dialog is the licence itself: res/raw/apache_license_2_0.txt, taken
// from apache.org and cut after "END OF TERMS AND CONDITIONS". What follows
// that line in the original is the appendix telling authors how to apply the
// licence to their own files -- instructions, not terms.
@Composable
fun LicensesDialog(onClose: () -> Unit) {
    val context = LocalContext.current
    // Paragraphs split on blank lines, so a screen reader moves through the
    // licence a paragraph at a time instead of meeting one 10 KB block.
    val licenceParagraphs = remember {
        try {
            context.resources.openRawResource(R.raw.apache_license_2_0).bufferedReader().use { it.readText() }
                .split(Regex("\\n\\s*\\n")).map { it.trim() }.filter { it.isNotEmpty() }
        } catch (_: Exception) { emptyList() }
    }
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Open source licenses", modifier = Modifier.semantics { heading() }) },
        text = {
            // Material3's AlertDialog does not scroll its text slot, so the
            // column scrolls itself.
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                for (paragraph in licenceParagraphs) {
                    Text(paragraph, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 12.dp))
                }
            }
        },
        confirmButton = { EvButton(label = "Close") { onClose() } }
    )
}
