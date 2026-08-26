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
    var selectedMode by remember {
        mutableStateOf(prefs.getReadingMode().let { if (it == "none") "auto" else it })
    }
    LaunchedEffect(refreshKey) {
        val stored = prefs.getReadingMode()
        val mode = if (stored == "none") "auto" else stored
        selectedMode = mode
        prefs.setReadingMode(mode)
        rebuildLanguagesFor(context, modeIntOf(mode))
    }
    ResponsiveContent {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).selectableGroup()) {
            SectionHeader("Modes")
            for (spec in modeRowSpecs) {
                val mode = spec.first
                // AutoTTS's Google radio is android:visibility="gone" in
                // fragment_modes.xml and setVisibility is never called on it, so it
                // is never offered there either. But its AutoModeSettings block IS
                // visible for mode 3, so the preferred language stays editable.
                // Skipping the row outright cost us that: a stored or imported
                // auto_mode = 3 left the setting with no way in. So the row appears
                // only while google is the mode actually in force.
                if (mode == "google" && selectedMode != "google") continue
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
