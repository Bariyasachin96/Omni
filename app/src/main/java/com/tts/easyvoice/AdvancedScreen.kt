package com.tts.easyvoice
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider

// Material's list row owns the heights, padding and colours. The description
// stays a SEPARATE element rather than ListItem's supportingContent: folding a
// paragraph into the row was tried in the View era and rejected on device,
// because TalkBack then reads the whole paragraph before you can act.
@Composable
fun SettingSwitch(label: String, checked: Boolean, enabled: Boolean = true, onChange: (Boolean) -> Unit) {
    ListItem(
        headlineContent = { Text(label, modifier = Modifier.clearAndSetSemantics { }) },
        trailingContent = { Switch(checked = checked, enabled = enabled, onCheckedChange = null) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onChange
            )
            .semantics { contentDescription = label }
    )
}

@Composable
fun SettingDescription(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
    )
}

@Composable
fun ActionButton(label: String, iconRes: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .semantics { contentDescription = label }
    ) {
        Icon(painterResource(iconRes), contentDescription = null, modifier = Modifier.padding(end = 8.dp))
        Text(label, modifier = Modifier.clearAndSetSemantics { })
    }
}

@Composable
fun AdvancedScreen(
    prefs: SharedPrefsManager,
    refreshKey: Int,
    requestNotificationPermission: () -> Unit,
    launchImportPicker: () -> Unit
) {
    val context = LocalContext.current
    // AutoTTS holds this checkbox (c3.k.D0) and re-runs setEnabled(K != 3) when
    // the punctuation mode changes; mirror that instead of deciding once at
    // composition, which left the switch stale after a trip to Mode settings.
    var punctuationModeIsSpecific by remember { mutableStateOf(EasyVoiceTtsService.punctuationModeInt == 3) }
    LaunchedEffect(refreshKey) {
        punctuationModeIsSpecific = EasyVoiceTtsService.punctuationModeInt == 3
    }
    var stripAudioAttr by remember { mutableStateOf(EasyVoiceTtsService.stripAudioAttrFlag) }
    var forceAccessibility by remember { mutableStateOf(EasyVoiceTtsService.forceAccessibilityFlag) }
    var keepAlive by remember { mutableStateOf(EasyVoiceTtsService.keepAliveFlag) }
    var showNotification by remember { mutableStateOf(EasyVoiceTtsService.showNotificationFlag) }
    var disableAdvanced by remember { mutableStateOf(EasyVoiceTtsService.disableAdvancedFlag) }
    var quickCharacter by remember { mutableStateOf(EasyVoiceTtsService.quickCharacterFlag) }
    var punctuationInFlow by remember { mutableStateOf(EasyVoiceTtsService.punctuationInFlowFlag) }
    var smartNumber by remember { mutableStateOf(EasyVoiceTtsService.smartNumberFlag) }
    var groupSize by remember { mutableStateOf(EasyVoiceTtsService.smartNumberGroupSize) }
    var useCld3 by remember { mutableStateOf(EasyVoiceTtsService.useCld3Flag) }
    var loggingEnabled by remember { mutableStateOf(EasyVoiceLogger.isLoggingEnabled()) }
    ResponsiveContent {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            SectionHeader("Text-to-Speech Settings")
            ActionButton("TTS Settings", R.drawable.ic_record_voice) {
                try {
                    context.startActivity(Intent("com.android.settings.TTS_SETTINGS").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                } catch (_: android.content.ActivityNotFoundException) {
                    Toast.makeText(context, "TTS settings are not available on this device.", Toast.LENGTH_SHORT).show()
                }
            }
            SettingDescription("Takes you to Android's own text-to-speech settings.")

            SectionHeader("Advanced Synthesis Options")
            SettingDescription("On some phones, such as Oppo, OnePlus and Realme, only one chosen app is allowed to use the accessibility audio stream. The two settings below get around that.")
            SettingSwitch("Remove audio attributes from synthesis request.", stripAudioAttr) { picked ->
                stripAudioAttr = picked; EasyVoiceTtsService.stripAudioAttrFlag = picked
            }
            SettingDescription("Sends synthesis requests to other TTS engines without any audio attributes. Easy Voice turns this on by itself if it hits an accessibility stream error.")
            SettingSwitch("Force to use audio accessibility stream", forceAccessibility) { picked ->
                forceAccessibility = picked; EasyVoiceTtsService.forceAccessibilityFlag = picked
            }
            SettingDescription("Sends the speech out on the accessibility audio stream.")

            SectionHeader("Keep-alive Mode")
            SettingSwitch("Keep alive", keepAlive) { picked ->
                keepAlive = picked; EasyVoiceTtsService.keepAliveFlag = picked
            }
            SettingDescription("Feeds a little silence between phrases, so the system does not cut the speech off mid-sentence.")

            SectionHeader("Persistence Options")
            SettingSwitch("Show persistent notification", showNotification) { picked ->
                showNotification = picked
                EasyVoiceTtsService.showNotificationFlag = picked
                if (picked) requestNotificationPermission()
            }
            SettingDescription("Keeps Easy Voice running in the foreground, so it carries on working under battery optimization and when the screen is off.")
            ActionButton("Disable battery optimization", R.drawable.ic_battery_alert) {
                val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                if (!powerManager.isIgnoringBatteryOptimizations(context.packageName)) {
                    try {
                        context.startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
                        Toast.makeText(context, "Set Easy Voice and each of your TTS engines to Unrestricted", Toast.LENGTH_LONG).show()
                    } catch (_: Exception) {
                        Toast.makeText(context, "Failed to open battery settings", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Without this the button was completely silent whenever the
                    // exemption was already granted -- nothing opened, nothing was
                    // said -- which is indistinguishable from a broken button.
                    Toast.makeText(context, "Battery optimization is already off for Easy Voice", Toast.LENGTH_LONG).show()
                }
            }
            SettingDescription("Opens the system battery screen, where you can set Easy Voice and each of your TTS engines to Unrestricted.")

            SectionHeader("Language Detection Options")
            SettingSwitch("Disable advanced language detection", disableAdvanced) { picked ->
                disableAdvanced = picked; EasyVoiceTtsService.disableAdvancedFlag = picked
            }
            SettingDescription("Falls back to the simpler detection, which is faster.")
            SettingSwitch("Quick character read", quickCharacter) { picked ->
                quickCharacter = picked; EasyVoiceTtsService.quickCharacterFlag = picked
            }
            SettingDescription("When the text is a single character, it is read in your preferred language instead of being detected.")
            SettingSwitch("Read punctuation in flow with text", punctuationInFlow,
                enabled = !punctuationModeIsSpecific) { picked ->
                punctuationInFlow = picked; EasyVoiceTtsService.punctuationInFlowFlag = picked
            }
            SettingDescription("Reads punctuation as part of the sentence. Turn it off and punctuation is read on its own.")
            SettingSwitch("Smart number reading", smartNumber) { picked ->
                smartNumber = picked; EasyVoiceTtsService.smartNumberFlag = picked
            }
            SettingDescription("Reads long numbers, like phone numbers and codes, in small groups of digits.")
            // c3.k:1101-1105 -- three entries, selection is f0 - 1, and the whole
            // control is greyed out while smart number reading is off.
            LabeledDropdown(
                label = "Group size",
                options = listOf("1", "2", "3"),
                selectedIndex = (if (groupSize in 1..3) groupSize else 1) - 1,
                enabled = smartNumber
            ) { picked ->
                // AutoTtsService.f0 = getSelectedItemPosition() + 1
                groupSize = picked + 1
                EasyVoiceTtsService.smartNumberGroupSize = groupSize
            }
            SettingDescription("One reads every digit on its own. Two or three read them in pairs or threes, which is easier to follow for a long number.")
            SettingSwitch("Use CLD3 (neural language detection)", useCld3) { picked ->
                useCld3 = picked; EasyVoiceTtsService.useCld3Flag = picked
            }
            SettingDescription("Experimental. Detects languages more accurately. Leave it off and Easy Voice uses CLD2, which is the default.")

            SectionHeader("Import/Export Configuration")
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { launchImportPicker() },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Import" }
                ) {
                    Icon(painterResource(R.drawable.ic_file_download), contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Import", modifier = Modifier.clearAndSetSemantics { })
                }
                Button(
                    onClick = {
                        if (!prefs.settingsXmlFile().exists()) {
                            Toast.makeText(context, "Settings file not found", Toast.LENGTH_SHORT).show()
                        } else {
                            val exportFile = try { prefs.exportSettingsFile() } catch (ex: java.io.IOException) { ex.printStackTrace(); null }
                            if (exportFile != null) {
                                val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", exportFile)
                                context.startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                                    type = "text/xml"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }, "Share Settings"))
                            }
                        }
                    },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Export" }
                ) {
                    Icon(painterResource(R.drawable.ic_file_upload), contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Export", modifier = Modifier.clearAndSetSemantics { })
                }
            }
            SettingDescription("Importing only checks that the engines you need are installed. You still have to make sure the individual voices are there.")

            SectionHeader("Logging")
            SettingSwitch("Enable logging", loggingEnabled) { picked ->
                loggingEnabled = picked
                prefs.setLoggingEnabled(picked)
                EasyVoiceLogger.setLoggingEnabled(picked)
            }
            SettingDescription("Writes a log file that you can send along when you report a problem.")
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val logFile = EasyVoiceLogger.shareFileOrNull()
                        if (logFile == null) {
                            Toast.makeText(context, "No log file to share", Toast.LENGTH_SHORT).show()
                        } else {
                            val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", logFile)
                            val send = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_STREAM, uri)
                                putExtra(Intent.EXTRA_SUBJECT, "Easy Voice Log")
                                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            val chooser = Intent.createChooser(send, "Share Easy Voice Log")
                            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(chooser)
                        }
                    },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Share logs" }
                ) {
                    Icon(painterResource(R.drawable.ic_share), contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Share logs", modifier = Modifier.clearAndSetSemantics { })
                }
                Button(
                    onClick = { EasyVoiceLogger.clear() },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Clear logs" }
                ) {
                    Icon(painterResource(R.drawable.ic_delete), contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                    Text("Clear logs", modifier = Modifier.clearAndSetSemantics { })
                }
            }

            SectionHeader("Information")
            // AutoTTS writes its own literals here (c3.k:1187-1188); ours come
            // from PackageManager, so they are this build's real numbers.
            // Label and value are ONE Text, so a screen reader reads
            // "Build number, 41" as a single stop rather than two.
            val versionInfo = remember {
                try {
                    val info = context.packageManager.getPackageInfo(context.packageName, 0)
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
            SettingDescription("Build number: " + versionInfo.first)
            SettingDescription("Version: " + versionInfo.second)
        }
    }
}
