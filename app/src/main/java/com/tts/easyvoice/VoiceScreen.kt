package com.tts.easyvoice
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.accessibilityClassName
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt

// Material's own exposed dropdown menu, with an OutlinedButton as the anchor.
//
// This WAS a hand-wired OutlinedButton plus a DropdownMenu, with the role, the
// expanded state and the click all written here. The owner asked for the library
// component instead (2026-09-03: "properly library ke through hi karvaiye ...
// apne haath se kuchh bhi nahin"), and they were right -- see the block at the
// call to ExposedDropdownMenuBox for the list of things it now owns.
//
// NO LazyColumn in here, and it must not come back. A DropdownMenu sizes itself
// to its widest item, which means it asks its content for an INTRINSIC width,
// and a lazy list is a SubcomposeLayout that cannot answer that:
//
//   IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout
//   layouts is not supported... such as lazy lists, BoxWithConstraints, TabRow
//
// The exception suggests "adding a size modifier ... to fast return the queried
// intrinsic measurement", and that was tried -- width(280.dp) on the list -- and
// the app still crashed in the same place. The menu's measurement is not
// something a size modifier on the child can satisfy here, so the lazy list is
// out. A crash is worse than a verbose announcement, and this arrangement is
// the one that ran for many builds.
//
// The long-list announcement problem is therefore still open and is being
// solved elsewhere, not by nesting a lazy list in a menu.
// OptIn is REQUIRED and its absence is what broke build 827. androidx-main has
// dropped @ExperimentalMaterial3Api from ExposedDropdownMenuBox, menuAnchor and
// ExposedDropdownMenu, and reading it there is how this was missed -- but the
// version the BOM actually pins is material3 1.4.0, where all three still carry
// it, and the annotation is Level.ERROR so the compile fails outright. This is
// the trap CLAUDE.md already records for TabRow: check the api/*.txt of the
// version the BOM pins, NEVER androidx-main.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledDropdown(
    label: String,
    options: List<String>,
    selectedIndex: Int,
    // AutoTTS disables the group-size spinner whenever smart number reading
    // is off (c3.k.D0.setEnabled(AutoTtsService.e0)). Passing it to the button
    // is what marks the node disabled for a screen reader, and the menu has no
    // other way in, so the whole control goes with it.
    enabled: Boolean = true,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText = if (selectedIndex >= 0 && selectedIndex < options.size) options[selectedIndex] else ""
    // "Select language for reading numbers:" -> "Select language for reading numbers"
    // so the name does not read as "... numbers colon, comma, Auto language".
    val labelName = label.trimEnd(' ', ':')
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)) {
        // The label is announced as part of the button below, not on its own.
        //
        // It used to be a separate leaf node with the button named by its VALUE
        // alone. That is what a user hears three times on the mode settings
        // screen, because "Select language for reading numbers / punctuations /
        // emojis" all default to "Auto language", and twice more when the Latin
        // and non-Latin preferred languages are both English. Google's
        // DuplicateSpeakableTextCheck reports exactly that as a WARNING:
        // two clickable views with the same speakable text.
        //
        // Silencing it with clearAndSetSemantics -- rather than
        // hideFromAccessibility(), whose own KDoc says to use
        // clearAndSetSemantics for content that is redundant with its parent --
        // leaves the text on screen and lets the button own the whole name.
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp, bottom = 2.dp).clearAndSetSemantics { }
        )
        // THE MENU IS MATERIAL'S OWN NOW, not a Button plus a DropdownMenu wired
        // together here. `ExposedDropdownMenuBox` owns everything that used to
        // be hand-written on this control:
        //   * `role = Role.DropdownList` and the accessibility click action,
        //     from Modifier.expandable inside menuAnchor;
        //   * opening on touch, in the Initial pointer pass;
        //   * Enter / space / arrow key handling;
        //   * `BackHandler(enabled = expanded)`, so back closes the menu;
        //   * focus, and `exposedDropdownSize`, which measures the menu against
        //     the anchor instead of leaving it to size itself;
        //   * a scrollState on the menu, which a 137-language list needs.
        // The two expand/collapse ACTIONS added on 2026-09-03 are GONE with it:
        // TalkBack announced the state twice, once from stateDescription and
        // once from the action it was offering, which is what the owner heard.
        //
        // onClick is deliberately EMPTY. menuAnchor consumes the gesture in the
        // Initial pass and toggles the menu itself, so a real onClick here would
        // toggle a second time and the menu would open and shut in one tap.
        //
        // The two semantics that stay are the two the library does NOT set for a
        // PrimaryNotEditable anchor, checked in ExposedDropdownMenu.kt rather
        // than assumed -- `Modifier.expandable` applies stateDescription and
        // contentDescription only on the SecondaryEditable branch:
        //   * contentDescription, because this node merges its children and
        //     therefore carries no name of its own (INVARIANTS #7);
        //   * stateDescription, so expanded/collapsed is still spoken.
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { wanted: Boolean -> if (enabled) expanded = wanted },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = { },
                enabled = enabled,
                modifier = Modifier
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled)
                    .fillMaxWidth()
                    .semantics {
                        contentDescription = labelName + ", " + selectedText
                        stateDescription = if (expanded) "Expanded" else "Collapsed"
                        accessibilityClassName = EvRoleClass.SPINNER
                    }
            ) {
                Text(
                    text = selectedText,
                    modifier = Modifier.weight(1f).clearAndSetSemantics { }
                )
                Icon(painterResource(R.drawable.ic_arrow_drop_down), contentDescription = null)
            }
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            // Declared by hand on a plain Column, which is not a SubcomposeLayout
            // and answers intrinsic measurement fine. Without them TalkBack has
            // no structure for the list and cannot say where you are in it,
            // which is what made a fast swipe through 130 languages
            // unintelligible.
            //
            // BOTH halves are needed, and neither comes free from a lazy list
            // either: LazyLayoutSemantics sets `collectionInfo` on the layout
            // node and nothing else -- there is no collectionItemInfo anywhere
            // in that file -- so per-item positions are always the caller's job.
            // That is also why the section headings inside the Languages screen's
            // LazyColumn do not claim a row of their own.
            //
            // collectionInfo is what makes this a TalkBack CONTAINER as well:
            // its Role.java resolves a node carrying collection info to
            // ROLE_LIST, which is in both FILTER_CONTAINER and the auto-scroll
            // set. Without it a 137-language menu would be a list you can only
            // leave by swiping to the end of.
            Column(
                modifier = Modifier.semantics {
                    collectionInfo = CollectionInfo(rowCount = options.size, columnCount = 1)
                }
            ) {
                for (index in options.indices) {
                    // Why the label is spelled out as a contentDescription here,
                    // and why the children are silenced. Read from the androidx
                    // source, not assumed:
                    //
                    // The a11y delegate walks semanticsOwner.unmergedRootSemanticsNode,
                    // so for a merging node replacedChildren is the real child list
                    // and is never empty. That matters twice:
                    //   info.contentDescription is assigned only under
                    //     !isMergingSemanticsOfDescendants || replacedChildren.isEmpty()
                    //   info.text  = getInfoText(node), which reads the node's OWN
                    //     unmerged config -- a merging container has no Text of its own.
                    // So a menu item, a Button or a clickable row carries NO name of
                    // its own; the label lives on the "fake" child nodes Compose
                    // emits. TalkBack walks those, which is why it reads fine there,
                    // while a reader that only inspects the focused node announces
                    // the bare role -- the "button, button, button" that was
                    // reported with another screen reader.
                    //
                    // Setting contentDescription here makes Compose emit the
                    // "contentDescription clobbering" fake node carrying it, and
                    // Google's Accessibility Scanner reads that back verbatim on
                    // device, so it really is exposed. clearAndSetSemantics on the
                    // Text does NOT remove it from replacedChildren -- any child
                    // with a semantics node counts -- it is there to stop the label
                    // being announced twice. The visible text is untouched.
                    DropdownMenuItem(
                        text = { Text(options[index], modifier = Modifier.clearAndSetSemantics { }) },
                        onClick = { onSelect(index); expanded = false },
                        trailingIcon = {
                            if (index == selectedIndex) {
                                Icon(painterResource(R.drawable.ic_check), contentDescription = null)
                            }
                        },
                        modifier = Modifier.semantics {
                            contentDescription = options[index]
                            accessibilityClassName = EvRoleClass.BUTTON
                            // The state goes in `selected`, never in the name.
                            // Google's Accessibility Scanner flagged the old
                            // description exactly for that: "This item's content
                            // description, \"English (eng), Selected\", contains
                            // the state \"selected\"."
                            selected = index == selectedIndex
                            collectionItemInfo = CollectionItemInfo(index, 1, 0, 1)
                        }
                    )
                }
            }
            }
        }
    }
}

// The three sliders are percentages -- 100 is the engine's own speed, volume or
// pitch -- so one unit IS one percent. The two ways of moving one are therefore
// deliberately different sizes: the buttons are the fine adjustment and a
// screen-reader swipe is the coarse one.
private const val SLIDER_MIN = 10          // AutoTTS's own floor (c3.k.P1())
private const val SLIDER_BUTTON_STEP = 1   // one press of - or +
private const val SLIDER_SWIPE_STEP = 5    // one screen-reader swipe up or down

// A swipe used to move by a fraction of a percent and never came back to where
// it started. Compose's accessibility delegate is what turns the swipe into a
// value, and it does that WITHOUT asking the component:
//
//   var increment = if (rangeInfo.steps > 0) (max - min) / (rangeInfo.steps + 1)
//                   else (max - min) / AccessibilitySliderStepsCount   // = 20
//   ...
//   return setProgressAction.action?.invoke(rangeInfo.current + increment)
//       -- AndroidComposeViewAccessibilityDelegateCompat, ACTION_SCROLL_FORWARD
//
// So a continuous slider moves by a twentieth of its range: 24.5 for Speed
// (10..500), 4.5 for Volume (10..100), 9.5 for Pitch (10..200). Not one of
// those is a whole number, and the value we keep is an Int, so every swipe threw
// the half away and the two directions stopped agreeing:
//
//   100 -> swipe up -> 124 -> swipe down -> 99
//
// and the step the user heard alternated between four and five percent as the
// discarded halves accumulated. Setting `steps` on the Slider would make the
// platform's increment exact, but it also snaps the STATE to the nearest tick,
// which would quietly round away the single percent the buttons just added, and
// it draws a tick mark per step -- 98 of them on Speed.
//
// The fix is to answer the action ourselves. A semantics block passed in through
// `modifier` overrides the component's own: LayoutNode.calculateSemantics-
// Configuration walks `nodes.tailToHead(Nodes.Semantics)` writing every node
// into ONE shared config, so the modifier nearest the head -- the first one in
// the chain, which is ours -- is written last and wins. (The older collapsePeer
// path reaches the same answer from the other end: it keeps the value that is
// already there, and ours is collapsed first.) Slider's own progressBarRangeInfo
// is left alone, because the delegate needs it to offer the actions at all.
//
// Nothing below depends on the platform's increment being any particular size.
// It is compared only to tell a swipe apart from an assistant asking for a
// specific value -- Voice Access's "set slider to 50" -- which is still honoured
// exactly rather than turned into a single step.
private fun sliderStepUp(value: Int) = (value / SLIDER_SWIPE_STEP + 1) * SLIDER_SWIPE_STEP

private fun sliderStepDown(value: Int) =
    ((value + SLIDER_SWIPE_STEP - 1) / SLIDER_SWIPE_STEP - 1) * SLIDER_SWIPE_STEP

@Composable
fun ValueSlider(label: String, value: Int, maxValue: Int, onValue: (Int) -> Unit) {
    val context = LocalContext.current
    val lowered = label.lowercase(Locale.getDefault())
    val clamp = { picked: Int ->
        if (picked < SLIDER_MIN) SLIDER_MIN else if (picked > maxValue) maxValue else picked
    }
    // The buttons are their own focus stop, so nothing re-reads the slider when
    // one is pressed. The toast is what tells you where you landed.
    val step = { picked: Int ->
        val next = clamp(picked)
        onValue(next)
        Toast.makeText(context, next.toString() + " of " + maxValue, Toast.LENGTH_SHORT).show()
    }
    // The Slider below is already named `label`, so this heading was a second
    // node speaking "Speed" right before "Speed, 100 of 500" -- the same
    // duplicate-speakable-text problem as the dropdown label.
    Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp).clearAndSetSemantics { }
    )
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = { step(value - SLIDER_BUTTON_STEP) },
            modifier = Modifier.semantics { contentDescription = "Decrease " + lowered; accessibilityClassName = EvRoleClass.BUTTON }
        ) { Text("-", modifier = Modifier.clearAndSetSemantics { }) }
        Slider(
            value = value.toFloat(),
            // roundToInt, not toInt: a drag lands on fractions and truncating
            // one is what made the two swipe directions disagree.
            onValueChange = { picked -> onValue(clamp(picked.roundToInt())) },
            valueRange = SLIDER_MIN.toFloat()..maxValue.toFloat(),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp).semantics {
                contentDescription = label
                stateDescription = value.toString() + " of " + maxValue
                setProgress { target ->
                    val platformIncrement = (maxValue - SLIDER_MIN) / 20f
                    val delta = target - value.toFloat()
                    val next = clamp(
                        if (abs(abs(delta) - platformIncrement) < 0.01f) {
                            if (delta > 0f) sliderStepUp(value) else sliderStepDown(value)
                        } else {
                            target.roundToInt()
                        }
                    )
                    if (next == value) {
                        false
                    } else {
                        onValue(next)
                        true
                    }
                }
            }
        )
        OutlinedButton(
            onClick = { step(value + SLIDER_BUTTON_STEP) },
            modifier = Modifier.semantics { contentDescription = "Increase " + lowered; accessibilityClassName = EvRoleClass.BUTTON }
        ) { Text("+", modifier = Modifier.clearAndSetSemantics { }) }
    }
}

// `total` and `onNavigate` let the screen walk the same language list the
// Configuration tab shows, so several languages can be set up one after another
// without going back each time. testTtsProvider stays last so the existing
// trailing-lambda call site still reads naturally.
@Composable
fun VoiceScreen(prefs: SharedPrefsManager, langIndex: Int, total: Int, onNavigate: (Int) -> Unit, testTtsProvider: () -> TextToSpeech?) {
    val context = LocalContext.current
    val readingMode = remember { prefs.getReadingMode() }
    val entry = LangStore.languages.getOrNull(langIndex)
    if (entry == null) {
        ResponsiveContent {
            Column(modifier = Modifier.fillMaxWidth()) {
                SectionHeader("Voices", focusOnOpen = true)
                Text(
                    text = "This mode has no voice settings.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        return
    }
    val selectedIso = entry.iso3
    var voiceRows by remember { mutableStateOf(VoiceRows.load(context, readingMode, selectedIso)) }
    var voiceIndex by remember { mutableStateOf(0) }
    var variantOptions by remember { mutableStateOf(VoiceRows.variantsFor(voiceRows, entry)) }
    var variantIndex by remember { mutableStateOf(0) }
    var speed by remember { mutableStateOf(entry.speed) }
    var volume by remember { mutableStateOf(entry.volume) }
    var pitch by remember { mutableStateOf(entry.pitch) }
    var dedicated by remember { mutableStateOf(EasyVoiceTtsService.dedicatedEnginesFlag) }
    val voiceLabels = voiceRows.map { VoiceRows.label(it) }
    // Name the heading after the language you opened. It used to read the
    // generic "Voices", so the only thing saying which language these voices
    // belong to was the window title -- announced once on entry and easy to
    // miss while setting up several languages in a row. Same fix, same reason,
    // as the Mode settings heading.
    val languageLabel = entry.displayName + " (" + entry.iso3 + ")"
    ResponsiveContent {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
            SectionHeader(languageLabel + " voices", focusOnOpen = true)
            Text(
                text = "Pick a voice for this language. Keeping to one engine per language makes switching quickest.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LabeledDropdown("Choose a voice for language", voiceLabels, voiceIndex) { picked ->
                if (picked != 0 && picked < voiceRows.size) {
                    voiceRows = VoiceRows.moveToFront(voiceRows, picked, selectedIso, entry)
                    voiceIndex = 0
                    variantOptions = VoiceRows.variantsFor(voiceRows, entry)
                    variantIndex = 0
                }
            }
            // Nothing below here means anything until a voice is actually
            // chosen. VoiceRows.firstOrNull() is null in exactly that case --
            // either the language has no voices at all, or the "*Disabled" row
            // sits at the front -- and it is the same test speakTest and
            // variantsFor already use. The variant picker was already hidden;
            // Test, the three sliders and Default were not, so they sat on the
            // screen doing nothing.
            if (voiceRows.firstOrNull() != null) {
                // variantsFor returns an empty list when the engine offers no
                // variants, and an empty dropdown is still a stop for a screen
                // reader, so this stays a separate check.
                if (variantOptions.isNotEmpty()) {
                    LabeledDropdown("Choose a variant for voice", variantOptions, variantIndex) { picked ->
                        variantIndex = picked
                        if (picked < variantOptions.size) {
                            entry.variant = variantOptions[picked]
                            LangStore.persistLanguages(context)
                        }
                    }
                }
                Button(
                    // 5.7.7.26 wrapped the Test click in a try/catch that toasts
                    // "Test unknown error". speakTest reaches into a TextToSpeech
                    // client that may already be dead, and before this the whole
                    // settings screen went down with it.
                    onClick = {
                        try {
                            VoiceRows.speakTest(voiceRows, entry, selectedIso, testTtsProvider())
                        } catch (_: Exception) {
                            Toast.makeText(context, "Test unknown error", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .semantics { contentDescription = "Test"; accessibilityClassName = EvRoleClass.BUTTON }
                ) {
                    Icon(painterResource(R.drawable.ic_play_arrow), contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp))
                    Text("Test", modifier = Modifier.clearAndSetSemantics { })
                }
                ValueSlider("Speed", speed, 500) { picked -> speed = picked; entry.speed = picked }
                ValueSlider("Volume", volume, 100) { picked -> volume = picked; entry.volume = picked }
                ValueSlider("Pitch", pitch, 200) { picked -> pitch = picked; entry.pitch = picked }
                OutlinedButton(
                    onClick = {
                        speed = 100
                        volume = 100
                        pitch = 100
                        entry.speed = 100
                        entry.volume = 100
                        entry.pitch = 100
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .semantics { contentDescription = "Default"; accessibilityClassName = EvRoleClass.BUTTON }
                ) {
                    Icon(painterResource(R.drawable.ic_restore), contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp))
                    Text("Default", modifier = Modifier.clearAndSetSemantics { })
                }
            } else {
                // Never leave the screen with nothing to perceive.
                Text(
                    text = "No voice is selected for this language yet. Once you choose one above, you can set its variant, speed, volume and pitch here.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            Text(
                text = "Experimental",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    .semantics { heading() }
            )
            SettingSwitch(
                label = "Dedicated engines",
                checked = dedicated,
                enabled = readingMode != "none" && readingMode != "google"
            ) { picked -> dedicated = picked; EasyVoiceTtsService.dedicatedEnginesFlag = picked }
            Text(
                text = "Gives every voice its own engine. Only turn this on if each voice really does have an engine to itself and no other app is using it.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            // Move to the next or previous language without returning to the
            // Configuration list. Hidden entirely when there is only one
            // language, where both buttons could never do anything. At the ends
            // they are disabled rather than removed, so the layout does not
            // shift and a screen reader announces "disabled" -- which is itself
            // how you know you are at the first or last language.
            if (total > 1) {
                Text(
                    text = "Language " + (langIndex + 1) + " of " + total,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigate(langIndex - 1) },
                        enabled = langIndex > 0,
                        modifier = Modifier.weight(1f).semantics { contentDescription = "Previous language"; accessibilityClassName = EvRoleClass.BUTTON }
                    ) {
                        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp))
                        Text("Previous", modifier = Modifier.clearAndSetSemantics { })
                    }
                    Button(
                        onClick = { onNavigate(langIndex + 1) },
                        enabled = langIndex < total - 1,
                        modifier = Modifier.weight(1f).semantics { contentDescription = "Next language"; accessibilityClassName = EvRoleClass.BUTTON }
                    ) {
                        Icon(painterResource(R.drawable.ic_arrow_forward), contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp))
                        Text("Next", modifier = Modifier.clearAndSetSemantics { })
                    }
                }
            }
        }
    }
}
