package com.sachinbaria.easyvoice
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
// THE VISION HACKS PAGE (owner, 2026-10-14): the developer and maintainer in
// the centre, highlighted; the people thanked; what the app is; the Telegram
// help bot, channel and group; the user guide; and a donation through any UPI
// app. Colours are the app's own theme and nothing new ("color aapko standard
// hi rakhna hai"): the highlight is the scheme's `primary`, the buttons are
// EvButton's, and both follow the system's light or dark mode as every screen
// does. The spelling in the owner's prompt was corrected on their word
// ("rooting" -> routing, "swich" -> switch, "Joine" -> Join); the user guide's
// text is theirs exactly (res/raw/user_guide.txt, byte for byte their file).
class AboutActivity : EvActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { EasyVoiceTheme { AboutScreen() } }
    }
}

private val THANKS = listOf("Adil Hussain Shekh", "Naresh Khushmi", "Rini Singh", "Syed Sharib Ali")

private const val ABOUT_THIS_APP =
    "This TTS engine acts as a free TTS routing solution that allows the user to seamlessly " +
    "switch between various TTS engines in accordance with user-specified languages and the " +
    "nature of the text, such as punctuation and digits."

private const val HELP_BOT_URL = "https://t.me/visionHacksHelpBot"
private const val CHANNEL_URL = "https://t.me/visionhacks"
private const val GROUP_URL = "https://t.me/+ggOHU9LRRR04MTk1"
private const val UPI_ID = "9727654365@pthdfc"

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
            ActionButton("Get help on Telegram: @visionHacksHelpBot", 0) { openLink(context, HELP_BOT_URL) }
            ActionButton("Join our Telegram channel: @visionhacks", 0) { openLink(context, CHANNEL_URL) }
            ActionButton("Join our Telegram group", 0) { openLink(context, GROUP_URL) }
            ActionButton("Read user guide", 0) { guideOpen = true }
            ActionButton("Make a donation", 0) { donate(context) }
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

// A UPI payment link (NPCI's upi://pay: payee address, payee name, currency)
// offered to every UPI app on the phone through the system chooser, so the
// person picks the app they pay with each time. No amount is set: the app asks
// for one. Asking the package manager first needs the upi <queries> entry in
// the manifest; it is there so "no UPI app" is said in words instead of an
// empty chooser.
private fun donate(context: Context) {
    val pay = Intent(
        Intent.ACTION_VIEW,
        Uri.Builder().scheme("upi").authority("pay")
            .appendQueryParameter("pa", UPI_ID)
            .appendQueryParameter("pn", "Sachin Baria")
            .appendQueryParameter("cu", "INR")
            .build()
    )
    if (pay.resolveActivity(context.packageManager) == null) {
        Toast.makeText(context, "No UPI payment app found on this phone", Toast.LENGTH_LONG).show()
        return
    }
    try {
        context.startActivity(Intent.createChooser(pay, "Pay with"))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No UPI payment app found on this phone", Toast.LENGTH_LONG).show()
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
