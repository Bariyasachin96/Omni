package com.sachinbaria.easyvoice
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

// About -- an Easy Voice screen with no AutoTTS counterpart, asked for on
// 2026-09-03: "hamara alag hi proper, hamara standalone hai". It is the one
// place that says whose app this is and what it is built on, and it took over
// the two facts the Advanced tab's "Information" section used to show at the
// bottom, so a screen reader meets them once rather than twice.
//
// A separate screen rather than an inline section, for the same reason
// Languages and Mode settings are screens.
//
// NO LICENCE SECTION (owner, 2026-09-23: "licence wala jo dialog hai vah hata
// do, licence wala jo button hai vah bhi hata do ... licence ke related kuchh
// bhi chijen nahin rakhni hai ... jab rakhni hogi to main bata dunga"). The
// "View Licenses" button, its dialog, res/raw/apache_license_2_0.txt and the
// info icon only that button used are all gone. Put them back only when the
// owner asks.
//
// THE VISION HACKS PAGE (owner, 2026-10-14/15): the developer and maintainer in
// the centre, highlighted; the people thanked, all in ONE line ("ek hi jagah
// per": a stop and a row per name took too much screen); what the app is; and
// exactly two buttons, side by side: the Telegram channel, then the user
// guide (the help bot, the group and the donation went on the owner's word). Colours are the app's own theme and nothing new ("color aapko
// standard hi rakhna hai"): the highlight is the scheme's `primary`, the
// buttons are EvButton's, and both follow the system's light or dark mode as
// every screen does. The user guide's text is the owner's exactly
// (res/raw/user_guide.txt, byte for byte their file).
class AboutActivity : EvActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EasyVoiceTheme { AboutScreen() } }
    }
}

// The names as the owner spelled them (2026-10-15), one after another, as the
// page first showed them (owner, same day: back to one name per line).
private val THANKS = listOf("Adil Hussain Shaikh", "Naresh Kushmi", "Rini Singh", "Syed Sharib Ali")

private const val ABOUT_THIS_APP =
    "Easy Voice is a TTS engine that works with the other TTS engines on your phone. " +
    "It sends each language to the engine you set up for it, and numbers and punctuation " +
    "can be read in a language of your choice."

private const val CHANNEL_URL = "https://t.me/visionhacks"

// Version comes from the build itself -- BuildConfig.VERSION_NAME is the
// versionName in app/build.gradle.kts, written by AGP at compile time -- so it
// is this build's real value with no PackageManager call and no API-33 branch
// (it used to read its own PackageInfo, which needs PackageInfoFlags on 33+).
@Composable
fun AboutScreen() {
    val context = LocalContext.current
    val versionName = BuildConfig.VERSION_NAME
    // Saveable: a rotation or a theme change keeps the guide open.
    var guideOpen by rememberSaveable { mutableStateOf(false) }
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
            SettingDescription("Easy Voice is a part of Vision Hacks.")
            SettingDescription("Version: " + versionName)
            // In the centre and highlighted, as asked: larger, bold and in the
            // theme's accent, `primary` (12.32:1 on the dark page, 6.68:1 on the
            // light one). One Text, so a screen reader says it in one stop.
            Text(
                text = "Developer and maintainer: Sachin Baria",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp)
            )
            SectionHeader("Huge thanks to")
            for (name in THANKS) SettingDescription(name)
            SectionHeader("About this app")
            SettingDescription(ABOUT_THIS_APP)
            Spacer(Modifier.height(8.dp))
            // SIDE BY SIDE (owner, 2026-10-15: "upar niche nahin, agal-bagal"): the
            // app's own pair shape (Share logs / Clear logs), each half the row.
            // The Telegram label wraps to more lines than "Read user guide", so the
            // row takes its tallest button's height (IntrinsicSize.Min) and both
            // fill it: one even pair, not two buttons of different heights.
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EvButton("Join our Telegram channel: @visionhacks", Modifier.weight(1f).fillMaxHeight()) {
                    openLink(context, CHANNEL_URL)
                }
                EvButton("Read user guide", Modifier.weight(1f).fillMaxHeight()) { guideOpen = true }
            }
            Spacer(Modifier.height(8.dp))
            SettingDescription("Copyright © 2026 Sachin Baria. All rights reserved.")
        }
    }
    if (guideOpen) UserGuideDialog(onDismiss = { guideOpen = false })
}

// The links open in Telegram when it is installed (t.me is its own link
// domain), otherwise in the browser.
private fun openLink(context: Context, url: String) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No app on this phone can open this link", Toast.LENGTH_SHORT).show()
    }
}

// THE USER GUIDE, built into the app (owner, 2026-10-14): the owner's own file,
// res/raw/user_guide.txt, kept byte for byte, so its text is exactly theirs and
// a new guide is a new file with no code change. Every line of it is its own
// Text -- one paragraph, one stop for a screen reader -- and its blank line only
// separates. A short line ending in ':' or '?' ("What is Easy Voice?", "Dual
// Languages Mode:" ...) is a title, so it is a heading: heading navigation then
// jumps from one section to the next. The dialog's text slot does not scroll by
// itself (material3's AlertDialog gives it only a weight), so the Column does.
@Composable
fun UserGuideDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val paragraphs = remember { readUserGuide(context) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("User guide", modifier = Modifier.semantics { heading() }) },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                for (paragraph in paragraphs) {
                    if (isGuideHeading(paragraph)) {
                        Text(
                            text = paragraph,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp).semantics { heading() }
                        )
                    } else {
                        Text(text = paragraph, modifier = Modifier.padding(bottom = 8.dp))
                    }
                }
            }
        },
        confirmButton = { EvButton(label = "Close", onClick = onDismiss) }
    )
}

private fun readUserGuide(context: Context): List<String> =
    context.resources.openRawResource(R.raw.user_guide).bufferedReader().use { it.readLines() }
        .filter { it.isNotBlank() }

internal fun isGuideHeading(line: String): Boolean =
    line.length <= 40 && (line.endsWith(':') || line.endsWith('?'))
