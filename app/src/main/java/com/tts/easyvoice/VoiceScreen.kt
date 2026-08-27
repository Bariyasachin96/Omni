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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import java.util.Locale

// Material's purpose-built "select one value" control. Verified against the
// material3 1.4.0 API surface, which we build against:
//   ExposedDropdownMenuBoxScope is @ExperimentalMaterial3Api  -> the @OptIn below
//   menuAnchor(Modifier) is @Deprecated                        -> use the typed form
//   menuAnchor(Modifier, ExposedDropdownMenuAnchorType, enabled)
// The component sets role = Role.DropdownList on a primary anchor and tracks the
// expanded state itself, so the hand-written semantics block this replaces --
// role, contentDescription and stateDescription -- is deleted rather than kept.
// Material's dropdown menu, with ordinary menu items.
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
        // Same reasoning as the menu items below: the button merges its children,
        // so without this the focused node carries no name and a screen reader
        // that does not walk Compose's fake nodes announces only the role.
        OutlinedButton(
            onClick = { expanded = true },
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    role = Role.DropdownList
                    contentDescription = labelName + ", " + selectedText
                    stateDescription = if (expanded) "Expanded" else "Collapsed"
                }
        ) {
            Text(
                text = selectedText,
                modifier = Modifier.weight(1f).clearAndSetSemantics { }
            )
            Icon(painterResource(R.drawable.ic_arrow_drop_down), contentDescription = null)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            // A LazyColumn would publish these two properties itself, but it
            // cannot live in a menu (see above), so they are declared by hand on
            // a plain Column, which is not a SubcomposeLayout and answers
            // intrinsic measurement fine. Without them TalkBack has no structure
            // for the list and cannot say where you are in it, which is what
            // made a fast swipe through 130 languages unintelligible.
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

@Composable
fun ValueSlider(label: String, value: Int, maxValue: Int, onValue: (Int) -> Unit) {
    val context = LocalContext.current
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
            onClick = {
                val next = if (value - 5 < 10) 10 else value - 5
                onValue(next)
                Toast.makeText(context, next.toString() + " of " + maxValue, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.semantics { contentDescription = "Decrease " + label.lowercase(Locale.getDefault()) }
        ) { Text("-", modifier = Modifier.clearAndSetSemantics { }) }
        Slider(
            value = value.toFloat(),
            onValueChange = { picked -> onValue(if (picked.toInt() < 10) 10 else picked.toInt()) },
            valueRange = 10f..maxValue.toFloat(),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp).semantics {
                contentDescription = label
                stateDescription = value.toString() + " of " + maxValue
            }
        )
        OutlinedButton(
            onClick = {
                val next = if (value + 5 > maxValue) maxValue else value + 5
                onValue(next)
                Toast.makeText(context, next.toString() + " of " + maxValue, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.semantics { contentDescription = "Increase " + label.lowercase(Locale.getDefault()) }
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
                SectionHeader("Voices")
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
            SectionHeader(languageLabel + " voices")
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
                        .semantics { contentDescription = "Test" }
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
                        .semantics { contentDescription = "Default" }
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
                        modifier = Modifier.weight(1f).semantics { contentDescription = "Previous language" }
                    ) {
                        Icon(painterResource(R.drawable.ic_arrow_back), contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp))
                        Text("Previous", modifier = Modifier.clearAndSetSemantics { })
                    }
                    Button(
                        onClick = { onNavigate(langIndex + 1) },
                        enabled = langIndex < total - 1,
                        modifier = Modifier.weight(1f).semantics { contentDescription = "Next language" }
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
