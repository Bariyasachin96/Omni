package com.tts.easyvoice
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
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
        // `toggleable` still owns the touch: it is what makes a finger
        // anywhere on the row flip the switch, and it keeps Material's own
        // minimum target. `evControl` then states the row's whole accessible
        // identity on the focused node -- name, Switch role, on/off, and the
        // activation action a screen reader uses -- so the role no longer lives
        // only on a fake child. Passing the SAME `onChange(!checked)` means both
        // routes do exactly one thing.
        // ORDER IS LOAD-BEARING: evControl comes FIRST. Compose builds the node
        // configuration with `nodes.tailToHead`, and a clearing node RESETS what
        // has been collected so far, so the clearing modifier only wins if it is
        // the LAST one visited -- which is the one nearest the HEAD, i.e. first
        // in the chain we write. Written the other way round, `toggleable` would
        // be applied on top of the reset and the fake role child would be back.
        modifier = Modifier
            .evControl(
                label,
                Role.Switch,
                enabled = enabled,
                toggle = if (checked) ToggleableState.On else ToggleableState.Off,
                action = { onChange(!checked) }
            )
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onChange
            )
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

// A full-width EvButton. Kept as its own name because the Advanced tab's
// sections read as a column of full-width actions and the width is part of that
// layout, not of the button.
@Composable
fun ActionButton(label: String, iconRes: Int, onClick: () -> Unit) {
    EvButton(
        label = label,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        iconRes = iconRes,
        onClick = onClick
    )
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
    var loggingEnabled by remember { mutableStateOf(EasyVoiceLogger.isLoggingEnabled()) }
    ResponsiveContent {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            // About sits FIRST, asked for on 2026-09-03. The header and the
            // button deliberately carry DIFFERENT text: ATF's
            // DuplicateSpeakableTextCheck warns when two elements share a
            // speakable name and either one is clickable, which a header
            // reading "About" above a button reading "About" is exactly.
            SectionHeader("About")
            ActionButton("About Easy Voice", R.drawable.ic_info) {
                context.startActivity(Intent(context, AboutActivity::class.java))
            }
            SettingDescription("The app and developer details, and the open source licenses Easy Voice is built on.")

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
                // ContextCompat.getSystemService, not getSystemService(String)
                // plus an unchecked cast: the library overload is typed, so a
                // device that answers null for POWER_SERVICE is a null here
                // rather than a ClassCastException on the next line.
                val powerManager = ContextCompat.getSystemService(context, PowerManager::class.java)
                if (powerManager == null || !powerManager.isIgnoringBatteryOptimizations(context.packageName)) {
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
            SectionHeader("Import/Export Configuration")
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EvButton("Import", Modifier.weight(1f), R.drawable.ic_file_download) { launchImportPicker() }
                EvButton(
                    "Export",
                    Modifier.weight(1f),
                    R.drawable.ic_file_upload,
                    onClick = {
                        if (!prefs.settingsXmlFile().exists()) {
                            Toast.makeText(context, "Settings file not found", Toast.LENGTH_SHORT).show()
                        } else {
                            val exportFile = try { prefs.exportSettingsFile() } catch (ex: java.io.IOException) { ex.printStackTrace(); null }
                            if (exportFile != null) {
                                val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", exportFile)
                                // ShareCompat.IntentBuilder is androidx's own API
                                // for an ACTION_SEND, and it is what builds the
                                // ClipData the URI grant is actually derived from
                                // (migrateExtraStreamToClipData: setClipData, then
                                // addFlags(FLAG_GRANT_READ_URI_PERMISSION)). The
                                // framework does the same migration on its way out
                                // of startActivity, and it walks into an
                                // ACTION_CHOOSER's EXTRA_INTENT to do it -- so the
                                // hand-built version was not broken -- but it
                                // refuses once the extras have been parcelled, and
                                // there is no reason to depend on that when the
                                // library does it eagerly and in one line.
                                context.startActivity(
                                    ShareCompat.IntentBuilder(context)
                                        .setType("text/xml")
                                        .setStream(uri)
                                        .setChooserTitle("Share Settings")
                                        .createChooserIntent()
                                )
                            } else {
                                // A BUTTON THAT DOES NOTHING IS THE WORST
                                // OUTCOME HERE (2026-09-10). The copy can fail
                                // for reasons that have nothing to do with the
                                // settings file existing -- no space on the
                                // cache partition is the realistic one -- and
                                // the null branch used to fall out of the click
                                // silently. The "file not found" branch above
                                // already speaks; a sighted user would at least
                                // see nothing happen, and a blind user cannot
                                // tell that from the app having frozen.
                                Toast.makeText(context, "Could not export the settings file", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                )
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
                EvButton(
                    "Share logs",
                    Modifier.weight(1f),
                    R.drawable.ic_share,
                    onClick = {
                        val logFile = EasyVoiceLogger.shareFileOrNull()
                        if (logFile == null) {
                            Toast.makeText(context, "No log file to share", Toast.LENGTH_SHORT).show()
                        } else {
                            val uri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", logFile)
                            // Same library builder as Export above.
                            // createChooserIntent() rather than startChooser(),
                            // only so the FLAG_ACTIVITY_NEW_TASK this path has
                            // always carried survives -- startChooser() would
                            // call startActivity itself with no flags.
                            val chooser = ShareCompat.IntentBuilder(context)
                                .setType("text/plain")
                                .setStream(uri)
                                .setSubject("Easy Voice Log")
                                .setChooserTitle("Share Easy Voice Log")
                                .createChooserIntent()
                            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(chooser)
                        }
                    }
                )
                EvButton("Clear logs", Modifier.weight(1f), R.drawable.ic_delete) { EasyVoiceLogger.clear() }
            }
        }
    }
}
