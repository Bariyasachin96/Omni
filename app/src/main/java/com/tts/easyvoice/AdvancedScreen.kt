package com.tts.easyvoice
import android.content.Intent
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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

// Material's list row owns the heights, padding and colours.
//
// THE DESCRIPTION STAYS A SEPARATE ELEMENT, and on 2026-09-17 that stopped
// being a style preference and became a hard constraint. The owner asked to
// "integrate descriptions directly with each option", and Material's own answer
// to that is ListItem's `supportingContent` slot -- which would be the library
// doing the work, and is exactly what this project prefers. It cannot be used
// HERE, for a reason that is specific to this row:
//
//   `evControl` below is `clearAndSetSemantics`, and clearing a node drops its
//   WHOLE SUBTREE from the accessibility tree -- that is the documented meaning
//   of an empty `replacedChildren` (SemanticsNode.kt), and it is the same
//   mechanism that once made the Configuration row's three-dot menu unreachable
//   (build 832). A description moved into `supportingContent` would therefore
//   be VISIBLE and completely INAUDIBLE: gone for the one person this app is
//   built for. The View-era finding pointed the same way -- folding the
//   paragraph into the row made TalkBack read it before you could act.
//
// So the description is integrated VISUALLY instead, by spacing: see
// SettingOption below, which renders a row and its description as one unit.
@Composable
fun SettingSwitch(label: String, checked: Boolean, enabled: Boolean = true, onChange: (Boolean) -> Unit) {
    ListItem(
        headlineContent = {
            // "a small, regular-weight light purple font for option labels"
            // (owner, 2026-09-17). bodyMedium is Material's 14sp Normal role --
            // the label was bodyLarge, 16sp -- and the colour is the scheme's
            // own `primary`, which in this theme is PaletteTokens.Primary80
            // #D0BCFF at 12.32:1 on the black page. Set through
            // ListItemDefaults.colors(headlineColor = ...) below rather than on
            // the Text, because that is the slot's own hook and it keeps the
            // disabled colour Material's.
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clearAndSetSemantics { }
            )
        },
        trailingContent = { Switch(checked = checked, enabled = enabled, onCheckedChange = null) },
        colors = ListItemDefaults.colors(
            containerColor = Color.Transparent,
            headlineColor = MaterialTheme.colorScheme.primary
        ),
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

// Unchanged, and deliberately so: this is shared with the About screen, where
// four of these run one under another as Build number / Version / Developer /
// Copyright. Widening the gap here to bind a description to its option would
// have spread those four facts out instead. The binding is done by
// SettingOption below, which is the only place that needs it.
@Composable
fun SettingDescription(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
    )
}

// The gap that ends one option and starts the next. Named rather than repeated,
// because with all eight section headers gone from this screen it is now the
// ONLY thing separating one setting from the next, and it has to be the same
// everywhere or the grouping it creates is a lie. 12dp on top of
// SettingDescription's own 4 makes 16 -- the same measure the section header
// used to reserve above itself.
@Composable
private fun OptionGap() {
    Spacer(modifier = Modifier.height(12.dp))
}

// "INTEGRATE DESCRIPTIONS DIRECTLY WITH EACH OPTION" (owner, 2026-09-17), as
// one composable rather than as spacing repeated at thirteen call sites.
//
// The option and the sentence that explains it are now a single call, so they
// cannot drift apart, and the space that binds them -- 4dp to its own
// description, 16dp to the next option -- is stated once here instead of being
// a number every future row has to remember. With the section headers gone this
// grouping is the only structure the screen has left.
//
// WHY THIS IS SPACING AND NOT ListItem's `supportingContent`, which is the
// library's own answer and would otherwise be the right call: see the note on
// SettingSwitch. In short, `evControl` clears the row's semantics subtree, so a
// description inside the row would be visible and inaudible -- the description
// has to stay a sibling to stay reachable.
@Composable
fun SettingOption(
    label: String,
    description: String,
    checked: Boolean,
    enabled: Boolean = true,
    onChange: (Boolean) -> Unit
) {
    SettingSwitch(label, checked, enabled, onChange)
    SettingDescription(description)
    OptionGap()
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
            // EVERY SECTION HEADER ON THIS SCREEN IS GONE (owner, 2026-09-17:
            // "In the Advanced screen, remove all section headers"). There were
            // eight -- About, Text-to-Speech Settings, Advanced Synthesis
            // Options, Keep-alive Mode, Persistence Options, Language Detection
            // Options, Import/Export Configuration, Logging.
            //
            // WHAT IT COSTS, stated rather than buried: this was the app's only
            // screen with eight heading stops, and heading navigation is how a
            // TalkBack user jumps a long list instead of swiping through every
            // control. That is a real loss and it is the owner's call; ATF has
            // no check for a screen without headings, so the CI gate will not
            // notice it either way. Reverting is putting these eight calls
            // back. The other screens keep theirs -- only this one was asked
            // for.
            //
            // The two descriptions under these first two buttons are gone too,
            // by name ("excluding descriptions from the 'About' and
            // 'Text-to-Speech Settings' buttons"). Both buttons say what they
            // do, so the paragraph under each was restating the label.
            //
            // The DuplicateSpeakableTextCheck note that used to sit here is
            // spent with the header: nothing on this screen reads "About" any
            // more except the one button, so there is no pair to collide. Its
            // label stays "About Easy Voice" regardless -- it is the better
            // name on its own.
            ActionButton("About Easy Voice", R.drawable.ic_info) {
                context.startActivity(Intent(context, AboutActivity::class.java))
            }
            ActionButton("TTS Settings", R.drawable.ic_record_voice) {
                try {
                    context.startActivity(Intent("com.android.settings.TTS_SETTINGS").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                } catch (_: android.content.ActivityNotFoundException) {
                    Toast.makeText(context, "TTS settings are not available on this device.", Toast.LENGTH_SHORT).show()
                }
            }
            OptionGap()
            // THE ONE PARAGRAPH THAT INTRODUCES RATHER THAN DESCRIBES, and it
            // is why it is still a bare SettingDescription instead of going
            // through SettingOption. It belongs to the TWO switches below it,
            // not to one of them -- and with "Advanced Synthesis Options" gone
            // it is now the only thing that says why they exist. The OptionGap
            // above separates it from the TTS Settings button, which the owner
            // asked to leave undescribed, so it cannot be mistaken for that
            // button's description.
            SettingDescription("On some phones, such as Oppo, OnePlus and Realme, only one chosen app is allowed to use the accessibility audio stream. The two settings below get around that.")
            SettingOption(
                "Remove audio attributes from synthesis request.",
                "Sends synthesis requests to other TTS engines without any audio attributes. Easy Voice turns this on by itself if it hits an accessibility stream error.",
                stripAudioAttr
            ) { picked -> stripAudioAttr = picked; EasyVoiceTtsService.stripAudioAttrFlag = picked }
            SettingOption(
                "Force to use audio accessibility stream",
                "Sends the speech out on the accessibility audio stream.",
                forceAccessibility
            ) { picked -> forceAccessibility = picked; EasyVoiceTtsService.forceAccessibilityFlag = picked }
            SettingOption(
                "Keep alive",
                "Feeds a little silence between phrases, so the system does not cut the speech off mid-sentence.",
                keepAlive
            ) { picked -> keepAlive = picked; EasyVoiceTtsService.keepAliveFlag = picked }
            SettingOption(
                "Show persistent notification",
                "Keeps Easy Voice running in the foreground, so it carries on working under battery optimization and when the screen is off.",
                showNotification
            ) { picked ->
                showNotification = picked
                EasyVoiceTtsService.showNotificationFlag = picked
                if (picked) requestNotificationPermission()
            }
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
            OptionGap()
            SettingOption(
                "Disable advanced language detection",
                "Falls back to the simpler detection, which is faster.",
                disableAdvanced
            ) { picked -> disableAdvanced = picked; EasyVoiceTtsService.disableAdvancedFlag = picked }
            SettingOption(
                "Quick character read",
                "When the text is a single character, it is read in your preferred language instead of being detected.",
                quickCharacter
            ) { picked -> quickCharacter = picked; EasyVoiceTtsService.quickCharacterFlag = picked }
            SettingOption(
                "Read punctuation in flow with text",
                "Reads punctuation as part of the sentence. Turn it off and punctuation is read on its own.",
                punctuationInFlow,
                enabled = !punctuationModeIsSpecific
            ) { picked -> punctuationInFlow = picked; EasyVoiceTtsService.punctuationInFlowFlag = picked }
            // NOT a SettingOption: the Group size dropdown belongs to this
            // switch, so the switch, its description, the dropdown and the
            // dropdown's own description are one group and the gap goes after
            // the LAST of them.
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
            OptionGap()
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
            OptionGap()
            SettingOption(
                "Enable logging",
                "Writes a log file that you can send along when you report a problem.",
                loggingEnabled
            ) { picked ->
                loggingEnabled = picked
                prefs.setLoggingEnabled(picked)
                EasyVoiceLogger.setLoggingEnabled(picked)
            }
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
