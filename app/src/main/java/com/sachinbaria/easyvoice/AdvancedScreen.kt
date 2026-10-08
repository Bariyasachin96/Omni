package com.sachinbaria.easyvoice
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

// Material's list row owns the heights, padding and colours.
//
// THE SWITCH ROLE, ON/OFF AND THE CLICK ARE `toggleable`'s OWN (owner,
// 2026-09-29: "jis chij ke liye components available hai vah components use
// karo"). They used to be declared a second time by hand in `evControl`; that
// is gone. See the note in ComposeTheme.kt.
//
// THE DESCRIPTION IS ITS OWN FOCUS STOP AGAIN (owner, 2026-09-30: "vah
// alag-alag chahie jo pahle tha"). On 2026-09-17 it was folded into the
// switch's name so one swipe said both; the owner has asked for the earlier
// shape back: the switch says its label, the paragraph under it is read on the
// next swipe. Do not fold them together again without the owner asking.
@Composable
fun SettingSwitch(
    label: String,
    checked: Boolean,
    enabled: Boolean = true,
    onChange: (Boolean) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    // WHY THE STATE IS NAMED HERE (owner, 2026-10-07: "switch on/off karta hun to TalkBack
    // announce nahin karta ... Samsung"). Read in the libraries, not guessed:
    //  - Compose UI 1.11+ (1.12.1 here, 1.13 alphas too) announces a toggle by sending
    //    CONTENT_CHANGE_TYPE_CHECKED -- an Android 16 (API 36) constant -- where 1.8..1.10
    //    sent CONTENT_CHANGE_TYPE_STATE_DESCRIPTION. Android 15 and older, and every
    //    TalkBack build that does not know the new flag (Samsung's), drop it.
    //  - TalkBack (google/talkback EventTypeViewClickedFeedbackRule) says nothing on the
    //    click for a node that HAS a state description, because it expects the
    //    STATE_DESCRIPTION change to say it -- and Compose gives every Switch one ("On").
    //  So on those phones the click says nothing and the state change says nothing.
    //  Setting the state description ourselves makes Compose send STATE_DESCRIPTION when
    //  it changes (the delegate's StateDescription branch), which every TalkBack speaks.
    //  The words are androidx's OWN strings -- exactly what Compose would have said, in
    //  the phone's language -- so nothing is written by hand; only the event changes.
    //  lint calls them PrivateResource (the aar publishes no public.txt), and its advice
    //  is to copy them; that would mean hand-typing "On"/"Off" in every language, the
    //  thing this avoids. If a future Compose drops them, the build fails to compile
    //  here -- it cannot fail quietly on a phone -- so the suppression is the safe side.
    @android.annotation.SuppressLint("PrivateResource")
    val stateWord = stringResource(
        if (checked) androidx.compose.ui.R.string.state_on else androidx.compose.ui.R.string.state_off
    )
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
        // `toggleable` is the whole control: the touch anywhere on the row,
        // Material's minimum target, the Switch role, the on/off state, the
        // disabled state and the click a screen reader sends. The one thing
        // added is the NAME (INVARIANTS #7).
        //
        // THE NAME GOES LAST in the chain, as `clearAndSetSemantics` (owner,
        // 2026-09-30: every reader, and fast swipes read in full). Semantics
        // are collected tail to head, so a clearing node at the TAIL resets
        // only what is tail-ward of it -- nothing of ours -- and `toggleable`,
        // head-ward, still writes its own role, state and click on top. What
        // the clear removes is the row's CHILDREN (label, switch), and with no
        // children Compose emits no fake role/name nodes and puts the role and
        // the name on the one node a reader focuses.
        //
        // The system's own toggle tick, from the same onValueChange a finger
        // and a double tap both reach -- see HapticFeedback.toggled.
        modifier = Modifier
            .toggleable(
                value = checked,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = { picked -> haptic.toggled(picked); onChange(picked) }
            )
            .clearAndSetSemantics {
                contentDescription = label
                stateDescription = stateWord
            }
    )
}

// A description is read on its own swipe, after the control it explains.
@Composable
fun SettingDescription(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    )
}

// The gap that ends one option and starts the next. Named rather than repeated,
// because with all eight section headers gone from this screen it is now the
// ONLY thing separating one setting from the next, and it has to be the same
// everywhere or the grouping it creates is a lie. 12dp on top of
// SettingDescription's own 4 makes 16 -- the same measure the section header
// used to reserve above itself.
// One string, drawn once under the switch. It covers the Group size dropdown as well, which is why
// it is not passed through SettingOption.
private const val SMART_NUMBER_DESCRIPTION =
    "Reads long numbers, like phone numbers and codes, in small groups of digits. " +
    "A group size of one reads every digit on its own; two or three read them in " +
    "pairs or threes, which is easier to follow for a long number."

@Composable
fun OptionGap() {
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
// The description is drawn as a sibling under the row and read on its own swipe.
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

// STANDARD SIZE, NOT FULL WIDTH (owner, 2026-09-17: "jo bhi bade-bade buttons
// hain ... vah buttons bilkul standard kar do ... standard size rakh do").
//
// It was `fillMaxWidth()`, so every action on the Advanced tab stretched edge to
// edge and read as a slab rather than a button. Material sizes a Button from its
// own content plus its own padding, which is what "standard" means here, and a
// wrap-content button is also the shape the touch-target minimum is specified
// against. The 16dp horizontal padding stays -- it is the app's own measure and
// what aligns this with every row above it.
//
// NOTHING ACCESSIBLE CHANGED. The name, the role, the click action and the 48dp
// minimum all come from EvButton and are untouched; only the width is.
//
// Still its own name rather than a bare EvButton, because the Advanced tab's
// actions share one padding and stating it in one place is what stops the next
// one drifting.
@Composable
fun ActionButton(label: String, iconRes: Int, onClick: () -> Unit) {
    EvButton(
        label = label,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        iconRes = iconRes,
        onClick = onClick
    )
}

@Composable
fun AdvancedScreen(
    prefs: SharedPrefsManager,
    refreshKey: Int,
    requestNotificationPermission: () -> Unit
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
    var groupSize by remember { mutableIntStateOf(EasyVoiceTtsService.smartNumberGroupSize) }
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
            // THE TWO BUTTONS THAT USED TO OPEN THIS SCREEN'S ONLY OTHER
            // SCREENS ARE GONE FROM IT (owner, 2026-09-17): "About Easy Voice"
            // and "TTS Settings" are in the app bar's "More options" menu now.
            // Both NAVIGATE rather than change a setting, which is what an
            // overflow menu is for and what everything left in this column is
            // not. Their two descriptions went with them, by the owner's own
            // instruction, and the headers above them were already gone.
            //
            // THE FIRST PARAGRAPH INTRODUCES RATHER THAN DESCRIBES, and it is
            // why it is a bare SettingDescription instead of going through
            // SettingOption. It belongs to the TWO switches below it, not to
            // one of them -- and with "Advanced Synthesis Options" gone it is
            // the only thing that says why they exist. Nothing sits above it
            // now, so nothing can be mistaken for what it describes.
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
            // switch, so the switch, its description and the dropdown are one
            // group and the gap goes after the LAST of them.
            SettingSwitch(
                "Smart number reading",
                smartNumber
            ) { picked ->
                smartNumber = picked; EasyVoiceTtsService.smartNumberFlag = picked
            }
            // ONE DESCRIPTION FOR BOTH CONTROLS (owner, 2026-09-17: "provide a
            // unified description for 'Smart Number Reading'"). There were two
            // paragraphs, one under the switch and one under the dropdown --
            // and with the dropdown now drawn only while the switch is ON, the
            // second would appear and disappear with it, so what the sizes mean
            // would only ever be readable AFTER you had already turned the
            // feature on. Said once, here, it is there either way.
            SettingDescription(SMART_NUMBER_DESCRIPTION)
            // c3.k:1101-1105 -- three entries, selection is f0 - 1.
            //
            // DRAWN ONLY WHILE THE SWITCH IS ON (owner, 2026-09-17:
            // "conditionally display the 'Group Size' combo box only when
            // toggled ON"). It used to be drawn always and merely greyed out,
            // which is AutoTTS's own shape -- c3.k sets D0.setEnabled(e0) at
            // build time and again from the switch's own listener -- so this is
            // a UI departure under the carve-out, not a parity break. The int it
            // writes and when it is persisted are untouched.
            //
            // WHAT IT COSTS A SCREEN READER, stated rather than skipped: a
            // control that is not drawn cannot be reached to be told it is
            // unavailable, where `disabled` at least announced itself as a
            // disabled control. That is the trade the owner asked for, and the
            // switch that brings it back is the row immediately above it.
            // `smartNumber` is ordinary state, so flipping it recomposes and
            // the row simply appears -- nothing is announced by hand, and
            // nothing may be: INVARIANTS #5 bans the deprecated announcement
            // API outright, and the check enforcing it greps for the name, so
            // it cannot even be written in a comment.
            if (smartNumber) {
                LabeledDropdown(
                    label = "Group size",
                    options = listOf("1", "2", "3"),
                    selectedIndex = (if (groupSize in 1..3) groupSize else 1) - 1
                ) { picked ->
                    // AutoTtsService.f0 = getSelectedItemPosition() + 1
                    groupSize = picked + 1
                    EasyVoiceTtsService.smartNumberGroupSize = groupSize
                }
            }
            OptionGap()
            // THE LAST THREE ARE IN THE OWNER'S ORDER (2026-09-23: "persistent
            // notification aur battery optimization wala ... last mein ...
            // sabse last mein log wala, log ke upar battery wala, uske upar
            // persistent notification").
            SettingOption(
                "Show persistent notification",
                "Keeps Easy Voice running in the foreground, so it carries on working under battery optimization and when the screen is off.",
                showNotification
            ) { picked ->
                showNotification = picked
                EasyVoiceTtsService.showNotificationFlag = picked
                // Now, while this screen shows, not at the next utterance.
                EasyVoiceTtsService.applyForegroundSetting(context)
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
            SettingDescription("Turns battery optimization off for Easy Voice. Set each voice engine to Unrestricted as well.")
            OptionGap()
            // Import and Export are gone (owner, 2026-09-17), and on 2026-09-24
            // the unreachable code behind them went too.
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
                            // ShareCompat.IntentBuilder sets the ClipData and the URI grant.
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
