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

// ONLY WHAT THE LICENCE REQUIRES (owner, 2026-09-23: "itna bada dialog kyon
// ... jo jaruri hai vahi chij rakho ... sab kuchh legal karo").
//
// Every component the app ships is Apache 2.0, read off each project's own
// licence, and Apache 2.0 section 4 asks a binary redistributor for exactly two
// things that apply here: (a) give recipients a copy of the licence, and
// (c) keep the copyright notices. CLD2 has no NOTICE file (HTTP 404), so (d)
// adds nothing, and (b) is about modified SOURCE files, which we do not ship.
// So the dialog is the four copyright lines and one sentence naming the
// licence. The copy of the licence that 4(a) asks for is still IN the app --
// res/raw/apache_license_2_0.txt, byte for byte from apache.org -- but it is
// folded behind one button rather than printed by default, which is what made
// the dialog eleven kilobytes long. The NDK's libc++ is Apache 2.0 WITH the LLVM
// exception, which waives 4(a), 4(b) and 4(d) for code compiled into a binary,
// so it needs no line here.
private val THIRD_PARTY = listOf(
    "CLD2 (Compact Language Detector 2). Copyright 2013, 2014 Google Inc.",
    "AndroidX and Jetpack Compose. Copyright The Android Open Source Project.",
    "Kotlin standard library and kotlinx.coroutines. Copyright JetBrains s.r.o.",
    "Material Design icons. Copyright Google LLC."
)

@Composable
fun LicensesDialog(onClose: () -> Unit) {
    val context = LocalContext.current
    var showText by remember { mutableStateOf(false) }
    val licenceParagraphs = remember(showText) {
        if (!showText) emptyList() else try {
            context.resources.openRawResource(R.raw.apache_license_2_0).bufferedReader().use { it.readText() }
                .split(Regex("\\n\\s*\\n")).map { it.trim() }.filter { it.isNotEmpty() }
        } catch (_: Exception) { emptyList() }
    }
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Open source licenses", modifier = Modifier.semantics { heading() }) },
        text = {
            // Material3's AlertDialog does not scroll its text slot, so the
            // column scrolls itself -- it only matters once the text is open.
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                for (line in THIRD_PARTY) {
                    Text(line, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 8.dp))
                }
                Text("All are used under the Apache License, Version 2.0.", style = MaterialTheme.typography.bodyMedium)
                EvButton(
                    label = if (showText) "Hide license text" else "Show license text",
                    modifier = Modifier.padding(top = 8.dp),
                    outlined = true
                ) { showText = !showText }
                for (paragraph in licenceParagraphs) {
                    Text(paragraph, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 12.dp))
                }
            }
        },
        confirmButton = { EvButton(label = "Close") { onClose() } }
    )
}
