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
// Languages and Mode settings are screens. The licences are a dialog opened from
// it; what that dialog says, and where each line of it comes from, is written
// above LicensesDialog below.
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

// THE LICENCE, AND ABOVE IT ONLY WHAT IS USED UNDER IT (owner, 2026-09-23:
// "sirf licence ki copy rakhni hai ... aur copy se upar jo bhi us licence ke
// andar aata hai bus sirf vahi").
//
// Everything third-party in the APK is under the Apache License 2.0 -- measured
// on build 902's own APK, not read off the dependency graph: androidx's META-INF
// version files, kotlinx_coroutines_*.version, the kotlin/* builtins, CLD2
// inside the native library, and the Material icons copied into res/drawable.
// kotlinx.serialization, atomicfu, JSpecify and the JetBrains annotations are on
// the classpath and leave nothing in the APK, so they are not named. The NDK's
// libc++ is Apache 2.0 WITH the LLVM exception, which waives the licence-copy
// requirement for compiled code, so it is not named either.
//
// For a binary, Apache 2.0 asks for one thing: 4(a), a copy of the licence.
// 4(b) and 4(c) are about source; 4(d) needs a NOTICE file and none of these
// ships one. So no copyright lines are shown -- they are not required here.
//
// res/raw/apache_license_2_0.txt is apache.org's file byte for byte up to "END
// OF TERMS AND CONDITIONS"; the appendix after it is instructions for authors.
private val APACHE_COMPONENTS = listOf(
    "Compact Language Detector 2 (CLD2)",
    "Android Jetpack (AndroidX)",
    "Jetpack Compose",
    "Kotlin Standard Library",
    "kotlinx.coroutines",
    "Material Design Icons"
)

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
                // The names, one per line, as one focus stop.
                Text(
                    APACHE_COMPONENTS.joinToString("\n"),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                for (paragraph in licenceParagraphs) {
                    Text(paragraph, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 12.dp))
                }
            }
        },
        confirmButton = { EvButton(label = "Close") { onClose() } }
    )
}
