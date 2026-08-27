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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
fun modeIntOf(mode: String): Int =
    when (mode) { "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 0 }
// The mode list offers exactly four: Dual, Auto, Mixed, Multilingual. "None" and
// "Google TTS" are never rows, so a stored value of either has to resolve to one
// that is, or the screen opens with no radio selected and no way to tell what is
// in force -- which for a screen reader user is worse than a wrong selection.
// Both map to "auto", and the caller writes that back, so the stored mode and
// the visible one never disagree.
//
// Google needs this as much as None does, because it is reachable without ever
// being offered: LangStore.loadMode reads `auto_mode` with a default of 3, which
// is AutoTTS's own c3.n.o, so a device with Google TTS installed and no stored
// mode -- a fresh install, or cleared data -- starts in Google mode.
private fun shownMode(stored: String): String =
    if (stored == "none" || stored == "google") "auto" else stored
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
@Composable
fun ModesScreen(
    prefs: SharedPrefsManager,
    refreshKey: Int,
    onOpenModeSettings: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedMode by remember { mutableStateOf(shownMode(prefs.getReadingMode())) }
    LaunchedEffect(refreshKey) {
        val mode = shownMode(prefs.getReadingMode())
        selectedMode = mode
        prefs.setReadingMode(mode)
        rebuildLanguagesFor(context, modeIntOf(mode))
    }
    ResponsiveContent {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).selectableGroup()) {
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
                // rejected that on 2026-08-27 -- the list must show four modes
                // and only four -- and shownMode() is the better answer anyway:
                // a stored google resolves to auto on open, so the setting is
                // not stranded, it simply stops being the mode. Do not put this
                // row back.
                if (mode == "google") continue
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .selectable(
                                selected = mode == selectedMode,
                                role = Role.RadioButton,
                                onClick = {
                                    selectedMode = mode
                                    prefs.setReadingMode(mode)
                                    rebuildLanguagesFor(context, modeIntOf(mode))
                                }
                            )
                            .padding(horizontal = 8.dp, vertical = 12.dp)
                            .semantics { contentDescription = spec.second },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = mode == selectedMode, onClick = null)
                        Text(
                            text = spec.second,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 12.dp).clearAndSetSemantics { }
                        )
                    }
                    if (mode == selectedMode) {
                        Button(
                            onClick = { onOpenModeSettings(mode) },
                            modifier = Modifier.semantics { contentDescription = "Settings" }
                        ) {
                            Icon(painterResource(R.drawable.ic_settings), contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp))
                            Text("Settings", modifier = Modifier.clearAndSetSemantics { })
                        }
                    }
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
@Composable
private fun ReadingSettings(codes: List<String>, labels: List<String>) {
    val modeOptions = listOf("Auto language", "Primary language", "Secondary language", "Specific language")
    // These three belong together and are a different thing from the preferred
    // languages above them, but they ran on as one flat list of dropdowns with
    // nothing naming the group.
    SectionHeader("Numbers, punctuation and emojis")
    var numberMode by remember { mutableStateOf(EasyVoiceTtsService.numberModeInt) }
    var puncMode by remember { mutableStateOf(EasyVoiceTtsService.punctuationModeInt) }
    var emojiMode by remember { mutableStateOf(EasyVoiceTtsService.emojiModeInt) }
    LabeledDropdown("Select language for reading numbers:", modeOptions, numberMode) { picked ->
        numberMode = picked
        EasyVoiceTtsService.numberModeInt = picked
    }
    if (numberMode == 3) {
        LanguageChoice("Specific language for reading numbers", codes, labels,
            EasyVoiceTtsService.numberSpecificLang) { EasyVoiceTtsService.numberSpecificLang = it }
    }
    LabeledDropdown("Select language for reading punctuations:", modeOptions, puncMode) { picked ->
        puncMode = picked
        EasyVoiceTtsService.punctuationModeInt = picked
    }
    if (puncMode == 3) {
        LanguageChoice("Specific language for reading punctuations", codes, labels,
            EasyVoiceTtsService.puncSpecificLang) { EasyVoiceTtsService.puncSpecificLang = it }
    }
    LabeledDropdown("Select language for reading emojis:", modeOptions, emojiMode) { picked ->
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
