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

// WHAT THE APP USES, UNDER WHICH LICENCE, AND THEN THE LICENCE ITSELF (owner,
// 2026-09-23: "pahle yah likho kya-kya use kiya hai aur uske kis licence ke
// andar aata hai aur phir licence ki copy ... jo officially documented hai vahi
// likhna hai, khud se nahin likhna hai").
//
// THE LIST IS WHAT THE SHIPPED APK CONTAINS, measured on build 902's own APK
// rather than read off the dependency graph: androidx's META-INF version files
// and its bundled LICENSE.txt, kotlinx_coroutines_*.version, the kotlin/*
// builtins, CLD2 inside libeasyvoice_core.so, and the Material icons copied into
// res/drawable. kotlinx.serialization, atomicfu, JSpecify and the JetBrains
// annotations are on the resolved classpath and leave nothing in the APK, so
// they are not listed. Every POM involved declares Apache 2.0, so one licence
// text covers the whole list.
//
// EVERY COPYRIGHT LINE IS COPIED, NOT WRITTEN:
//   CLD2        the headers of the 24 files CMakeLists compiles -- 21 say 2013,
//               the three full quadgram tables say 2014; both lines as written
//   AndroidX    androidx-main .idea/copyright/AndroidCopyright.xml, whose year
//               is $today.year, so the line is given without one
//   Kotlin      JetBrains/kotlin license/COPYRIGHT_HEADER.txt
//   coroutines  the copyright line in kotlinx.coroutines' own LICENSE.txt
//   icons       google/material-design-icons states no copyright line; its
//               README gives the licence and asks for attribution on About
//
// THE LICENCE TEXT is res/raw/apache_license_2_0.txt, apache.org's file byte for
// byte up to "END OF TERMS AND CONDITIONS". What follows that line in the
// original is the appendix telling authors how to apply the licence to their
// own files -- instructions, not terms. For a binary only section 4(a) applies
// (give the recipient a copy of the licence); none of these projects ships a
// NOTICE file, so 4(d) asks for nothing more. The NDK's libc++, linked
// statically, is Apache 2.0 WITH the LLVM exception, which waives 4(a), 4(b)
// and 4(d) for compiled code, which is why it is not listed.
private val THIRD_PARTY = listOf(
    "Compact Language Detector 2 (CLD2)" to listOf(
        "Copyright 2013 Google Inc. All Rights Reserved.",
        "Copyright 2014 Google Inc. All Rights Reserved."
    ),
    "Android Jetpack (AndroidX) and Jetpack Compose" to listOf(
        "Copyright The Android Open Source Project"
    ),
    "Kotlin Standard Library" to listOf(
        "Copyright 2010-2024 JetBrains s.r.o. and Kotlin Programming Language contributors."
    ),
    "kotlinx.coroutines" to listOf(
        "Copyright 2000-2020 JetBrains s.r.o. and Kotlin Programming Language contributors."
    ),
    "Material Design Icons" to emptyList()
)
private const val LICENSE_NAME = "Apache License, Version 2.0"

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
                // One focus stop per component: its name, its copyright lines
                // and its licence are read together.
                for ((name, copyrights) in THIRD_PARTY) {
                    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp).semantics(mergeDescendants = true) { }) {
                        Text(name, style = MaterialTheme.typography.titleSmall)
                        for (line in copyrights) Text(line, style = MaterialTheme.typography.bodySmall)
                        Text(LICENSE_NAME, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text(
                    LICENSE_NAME,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp).semantics { heading() }
                )
                for (paragraph in licenceParagraphs) {
                    Text(paragraph, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 12.dp))
                }
            }
        },
        confirmButton = { EvButton(label = "Close") { onClose() } }
    )
}
