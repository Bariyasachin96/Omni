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
// Languages and Mode settings are screens.
//
// NO LICENCE SECTION (owner, 2026-09-23: "licence wala jo dialog hai vah hata
// do, licence wala jo button hai vah bhi hata do ... licence ke related kuchh
// bhi chijen nahin rakhni hai ... jab rakhni hogi to main bata dunga"). The
// "View Licenses" button, its dialog, res/raw/apache_license_2_0.txt and the
// info icon only that button used are all gone. Put them back only when the
// owner asks.
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
        }
    }
}
