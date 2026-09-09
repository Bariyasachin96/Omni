package com.tts.easyvoice
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
fun modeIntOf(mode: String): Int =
    when (mode) { "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 0 }
// The mode list offers exactly four: Dual, Auto, Mixed, Multilingual. "None" is
// never a row and a stored "none" resolves to "auto", which the caller writes
// back, so the stored mode and the visible one never disagree.
//
// "Google TTS" is NOT mapped, and that is deliberate (owner, 2026-08-27).
// LangStore.loadMode reads `auto_mode` with a default of 3 -- AutoTTS's own
// c3.n.o -- so a fresh install on a device with Google TTS starts in Google
// mode. AutoTTS starts there too, and its Google radio is gone from the layout,
// so nothing is checked in its list either. The owner wants exactly that:
// "vahi Google wala selected rahana chahie, bus visible nahin hona chahie".
//
// An earlier version mapped google to auto here and wrote it back. It stopped
// the app ever sitting in Google mode, which is a real behaviour change and not
// ours to make. Do not reintroduce it: the mode is hidden, not converted.
private fun shownMode(stored: String): String =
    if (stored == "none") "auto" else stored
private fun rebuildLanguagesFor(context: android.content.Context, modeInt: Int) {
    val required = LangStore.requiredLangs(modeInt,
        EasyVoiceTtsService.autoLang,
        EasyVoiceTtsService.dualLang,
        EasyVoiceTtsService.mixLatinLang,
        EasyVoiceTtsService.mixNonLatinLang)
    LangStore.persistLanguages(context)
    LangStore.languages.clear()
    LangStore.languages.addAll(LangStore.rebuildFromScan(context, false, modeInt, required, EngineFinder.lastScanVoices))
    // Every AutoTTS radio ends with n.x(ctx); c.clear(); c.addAll(n.g(a,false));
    // AutoTtsService.s0(). The first three are the lines above; this is s0.
    EasyVoiceTtsService.pushLanguageSets()
}
// THE MODE'S SETTINGS ARE REACHED FROM THE FAB, NOT FROM A BUTTON IN THE ROW
// (owner, 2026-09-09): *"har ek mode ka jo settings button aata hai ... thoda
// upar ki taraf hai ... jahan per FAB button aata hai, bottom right corner per,
// vahan per hona chahie ... jo bhi mode mein change karunga uske hisab se vah
// button vahan per change hoga."*
//
// It used to sit right-aligned on the SELECTED radio's own row, so its position
// moved every time the mode changed -- it was beside Dual on one visit and
// beside Multilingual on the next, three rows further down. A control that
// moves is the hardest kind to find again, by touch or by eye. The FAB is
// always in the same corner, and Material's own definition is what makes it the
// right component here: a FAB "lets the user perform a primary action" and is
// "typically found anchored to the bottom right".
//
// `onModeChanged` is what keeps the FAB honest. The radio writes the mode
// straight to the store, so MainScreen cannot learn about it by re-reading
// prefs on a refresh key -- it has to be told, or the FAB would go on offering
// the settings of the mode you just left.
@Composable
fun ModesScreen(
    prefs: SharedPrefsManager,
    refreshKey: Int,
    onModeChanged: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedMode by remember { mutableStateOf(shownMode(prefs.getReadingMode())) }
    LaunchedEffect(refreshKey) {
        val mode = shownMode(prefs.getReadingMode())
        selectedMode = mode
        prefs.setReadingMode(mode)
        rebuildLanguagesFor(context, modeIntOf(mode))
        onModeChanged(mode)
    }
    ResponsiveContent {
        // The bottom padding is the FAB's clearance, the same 88dp the
        // Configuration list gives its own floating button, so the last mode's
        // description can always be scrolled out from under it.
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
                .selectableGroup().padding(bottom = 88.dp)
        ) {
            SectionHeader("Modes")
            for (spec in modeRowSpecs) {
                val mode = spec.first
                // Google TTS is NEVER a row, exactly like None. AutoTTS does the
                // same: its Google radio is android:visibility="gone" in
                // fragment_modes.xml and setVisibility is never called on it
                // anywhere, so that mode is not offered there either.
                //
                // It was briefly drawn here while google was the mode in force,
                // to keep its preferred-language setting reachable. The owner
                // rejected that -- the list must show four modes and only four.
                // So when Google IS the mode, which is the default on a fresh
                // install, the list shows no checked radio at all. AutoTTS does
                // the same, for the same reason, and that is the intent.
                if (mode == "google") continue
                val pick = {
                    selectedMode = mode
                    prefs.setReadingMode(mode)
                    rebuildLanguagesFor(context, modeIntOf(mode))
                    onModeChanged(mode)
                }
                // ONE Row, not two. There used to be an outer Row holding this
                // one at weight(1f) beside the selected mode's Settings button;
                // with that button gone to the FAB there is nothing to share the
                // line with, and a wrapper with a single child would only add a
                // layout node.
                //
                // `selectable` keeps the touch and the exclusive group;
                // evControl states the row on the focused node so a reader that
                // does not walk Compose's fake children still hears "radio
                // button, selected". It is FIRST because the configuration is
                // built tailToHead and a clearing node resets it, so only the
                // head-most one survives.
                Row(
                    modifier = Modifier
                        .evControl(
                            spec.second,
                            Role.RadioButton,
                            isSelected = mode == selectedMode,
                            action = pick
                        )
                        .fillMaxWidth()
                        .selectable(
                            selected = mode == selectedMode,
                            role = Role.RadioButton,
                            onClick = pick
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = mode == selectedMode, onClick = null)
                    Text(
                        text = spec.second,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 12.dp).clearAndSetSemantics { }
                    )
                }
                Text(
                    text = spec.third,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
@Composable
private fun LanguageChoice(
    label: String,
    codes: List<String>,
    labels: List<String>,
    initialIso3: String,
    onPick: (String) -> Unit
) {
    var index by remember { mutableStateOf(LangStore.indexOf(initialIso3)) }
    LabeledDropdown(label, labels, index) { picked ->
        index = picked
        if (picked >= 0 && picked < codes.size) onPick(codes[picked])
    }
}
// One of the three choices under "Numbers, punctuation and emojis", as a radio
// group rather than a dropdown (owner request, 2026-08-27). Four options is
// small enough that a list is faster than opening a menu, and every option is
// then reachable by one swipe instead of a menu round trip.
//
// `title` is a real heading, so a screen reader can jump between the three
// groups instead of swiping through twelve rows to find out which is which --
// that was the other half of the request. It is a plain Text with heading()
// rather than a SectionHeader, because SectionHeader draws a filled bar and
// there is already one of those above these three.
//
// A row is named by its OPTION ALONE -- "Auto language", not "Numbers, Auto
// language". The first version carried the group name on every row, for
// Google's DuplicateSpeakableTextCheck: all three groups offer the identical
// four options, so twelve rows share four names between them. The owner used it
// and rejected it (2026-08-27) -- with a heading already above each group,
// hearing "Numbers, Numbers, Numbers" down the list is noise, and they are
// right. The heading plus the selectableGroup's own position announcement carry
// the context, which is the conventional Android radio-group pattern and is how
// the mode radios on the previous screen already read. Do not put the prefix
// back; if the duplicate-name warning ever has to be answered, answer it with
// the headings, not by lengthening twelve names.
//
// The name still has to be set explicitly rather than left to the child Text:
// a merged node with children carries neither text nor contentDescription for a
// screen reader that only inspects the focused node. See INVARIANTS #7.
@Composable
private fun LabeledRadioGroup(
    title: String,
    options: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().selectableGroup()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).semantics { heading() }
        )
        for (index in options.indices) {
            Row(
                modifier = Modifier
                    .evControl(
                        options[index],
                        Role.RadioButton,
                        isSelected = index == selectedIndex,
                        action = { onSelect(index) }
                    )
                    .fillMaxWidth()
                    .selectable(
                        selected = index == selectedIndex,
                        role = Role.RadioButton,
                        onClick = { onSelect(index) }
                    )
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = index == selectedIndex, onClick = null)
                Text(
                    text = options[index],
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 12.dp).clearAndSetSemantics { }
                )
            }
        }
    }
}
@Composable
private fun ReadingSettings(codes: List<String>, labels: List<String>) {
    // The four options and their order are AutoTTS's number_mode_* strings, and
    // the index written to each static is what d0.t re-types a segment with, so
    // neither may be reordered: 0 auto, 1 primary, 2 secondary, 3 specific.
    val modeOptions = listOf("Auto language", "Primary language", "Secondary language", "Specific language")
    // NO SectionHeader here (owner, 2026-09-03: "yah dono heading ko hata do").
    // It used to read "Numbers, punctuation and emojis". The three groups below
    // each carry their own heading already -- "Select language for reading
    // numbers / punctuations / emojis" are real headings via LabeledRadioGroup --
    // so a filled bar on top of them was a second level of heading for the same
    // content, and heading navigation stopped on it before every group.
    var numberMode by remember { mutableStateOf(EasyVoiceTtsService.numberModeInt) }
    var puncMode by remember { mutableStateOf(EasyVoiceTtsService.punctuationModeInt) }
    var emojiMode by remember { mutableStateOf(EasyVoiceTtsService.emojiModeInt) }
    LabeledRadioGroup("Select language for reading numbers", modeOptions, numberMode) { picked ->
        numberMode = picked
        EasyVoiceTtsService.numberModeInt = picked
    }
    if (numberMode == 3) {
        LanguageChoice("Specific language for reading numbers", codes, labels,
            EasyVoiceTtsService.numberSpecificLang) { EasyVoiceTtsService.numberSpecificLang = it }
    }
    LabeledRadioGroup("Select language for reading punctuations", modeOptions, puncMode) { picked ->
        puncMode = picked
        EasyVoiceTtsService.punctuationModeInt = picked
    }
    if (puncMode == 3) {
        LanguageChoice("Specific language for reading punctuations", codes, labels,
            EasyVoiceTtsService.puncSpecificLang) { EasyVoiceTtsService.puncSpecificLang = it }
    }
    LabeledRadioGroup("Select language for reading emojis", modeOptions, emojiMode) { picked ->
        emojiMode = picked
        EasyVoiceTtsService.emojiModeInt = picked
    }
    if (emojiMode == 3) {
        LanguageChoice("Specific language for reading emojis", codes, labels,
            EasyVoiceTtsService.emojiSpecificLang) { EasyVoiceTtsService.emojiSpecificLang = it }
    }
}
@Composable
private fun LocaleSpanRow() {
    var localeSpans by remember { mutableStateOf(EasyVoiceTtsService.localeSpansFlag) }
    SettingSwitch("Supports multilingual text with locale spans", localeSpans) { picked ->
        localeSpans = picked
        EasyVoiceTtsService.localeSpansFlag = picked
    }
}
@Composable
fun ModeSettingsScreen(prefs: SharedPrefsManager, mode: String) {
    val context = LocalContext.current
    val modeInt = modeIntOf(mode)
    val loaded = remember(mode) {
        // Exactly what onModeSelected(settingsOnlyForMode) did in the View version:
        // write the reading mode, then rebuild the language list for it.
        prefs.setReadingMode(mode)
        rebuildLanguagesFor(context, modeInt)
        val loadedCodes = ArrayList<String>()
        var codeIdx = 0
        while (codeIdx < LangStore.languages.size) { loadedCodes.add(LangStore.languages[codeIdx].iso3); codeIdx++ }
        val loadedLabels = LangStore.languageLabelsFor(if (mode == "google") "com.google.android.tts" else null)
        Pair(loadedCodes, loadedLabels)
    }
    val codes = loaded.first
    val labels = loaded.second
    // Name the heading after the mode you opened. It used to read the generic
    // "Mode Settings", so the only thing identifying which mode's settings
    // these are was the window title, announced once on entry and easy to miss.
    val modeTitle = modeRowSpecs.firstOrNull { it.first == mode }?.second
    ResponsiveContent {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            SectionHeader(if (modeTitle == null) "Mode settings" else modeTitle + " settings")
            if (mode == "auto" || mode == "google") {
                LanguageChoice("Select preferred language:", codes, labels,
                    EasyVoiceTtsService.autoLang) { EasyVoiceTtsService.autoLang = it }
            }
            if (mode == "dual") {
                LanguageChoice("Select secondary language:", codes, labels,
                    EasyVoiceTtsService.dualLang) { EasyVoiceTtsService.dualLang = it }
                ReadingSettings(codes, labels)
            }
            if (mode == "mix" || mode == "multilingual") {
                // NO SectionHeader here either (owner, 2026-09-03). It read
                // "Preferred languages" and was added on 2026-08-27 so that
                // heading navigation had a stop before these two dropdowns.
                // The owner has used it and does not want it: both dropdowns
                // are already named in full ("Preferred language for Latin
                // text"), so the heading only repeated them.
                LanguageChoice("Preferred language for Latin text", codes, labels,
                    EasyVoiceTtsService.mixLatinLang) { EasyVoiceTtsService.mixLatinLang = it }
                LanguageChoice("Preferred language for non-Latin text", codes, labels,
                    EasyVoiceTtsService.mixNonLatinLang) { EasyVoiceTtsService.mixNonLatinLang = it }
                ReadingSettings(codes, labels)
            }
            // onRadioButtonClicked assigns CommonSettings for every mode except
            // google, which leaves it at the layout's gone. Same for ReadingSettings
            // above, which auto hides outright.
            if (mode != "google") {
                Text(
                    text = "Other options",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp).semantics { heading() }
                )
                LocaleSpanRow()
            }
        }
    }
}
