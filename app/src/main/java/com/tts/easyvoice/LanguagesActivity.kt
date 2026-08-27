package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import java.util.Locale
class LanguagesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This screen persists in onPause, so it must not run with unloaded
        // statics: Android can restore it alone into a fresh process. No-op
        // whenever anything is already loaded -- see LangStore.ensureLoaded.
        LangStore.ensureLoaded(this)
        val prefs = SharedPrefsManager(this)
        setContent { EasyVoiceTheme { LanguagesScreen(prefs) } }
    }
    override fun onPause() {
        LangStore.persistAll(this)
        super.onPause()
    }
}
// The documented way to mark a heading, and nothing more:
// developer.android.com/develop/ui/compose/accessibility/semantics shows exactly
// this shape -- a Text with Modifier.semantics { heading() }.
//
// A previous version merged at the Surface and set contentDescription on it, to
// chase a report that headings were spoken but never took focus. That was the
// wrong fix and it broke a different rule. The API-defaults page says
// contentDescription "is mainly meant to be used for graphic elements, such as
// images. Material components, like Button or Text ... come with other
// predefined semantics", and a Text is a LEAF: the delegate sets info.text from
// it unconditionally, so it is already both named and focusable. Merging a leaf
// buys nothing, and contentDescription on it only overrides the text it already
// has.
//
// Contrast with a merged clickable row, where the extra contentDescription IS
// needed and is INVARIANTS #7: there the node merges descendants AND has
// children, so the delegate skips its contentDescription (it goes to a fake
// leaf child) and info.text comes from the unmerged config, which is empty.
// The two cases are opposite; do not apply one rule to the other.
@Composable
fun SectionHeader(title: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp).semantics { heading() }
        )
    }
}
// One row, used by both groups. `position` is the row's index across the WHOLE
// list, not within its group, so the announced position stays continuous.
@Composable
private fun LanguageCheckRow(label: String, checked: Boolean, position: Int, onToggle: (Boolean) -> Unit) {
    ListItem(
        headlineContent = { Text(label, modifier = Modifier.clearAndSetSemantics { }) },
        leadingContent = { Checkbox(checked = checked, onCheckedChange = null) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = onToggle)
            .semantics {
                contentDescription = label
                // Declared by hand because the section headings below are
                // `item {}` entries and a LazyColumn counts everything it holds.
                collectionItemInfo = CollectionItemInfo(position, 1, 0, 1)
            }
    )
}
@Composable
fun LanguagesScreen(prefs: SharedPrefsManager) {
    val context = LocalContext.current
    val readingMode = remember { prefs.getReadingMode() }
    if (readingMode == "none" || readingMode == "dual") {
        ResponsiveContent {
            Column(modifier = Modifier.fillMaxWidth()) {
                SectionHeader("Languages")
                Text(
                    text = "Dual languages mode does not use this list.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        return
    }
    val modeInt = when (readingMode) { "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 0 }
    val loaded = remember {
        val required = LangStore.requiredLangs(modeInt, EasyVoiceTtsService.autoLang,
            EasyVoiceTtsService.dualLang, EasyVoiceTtsService.mixLatinLang, EasyVoiceTtsService.mixNonLatinLang)
        LangStore.persistLanguages(context)
        LangStore.languages.clear()
        LangStore.languages.addAll(LangStore.rebuildFromScan(context, false, modeInt, required, EngineFinder.lastScanVoices))
        // s0(), as c3/k.java:1299 does after rebuilding for this same screen.
        EasyVoiceTtsService.pushLanguageSets()
        val pkgFilter = if (readingMode == "google") "com.google.android.tts" else null
        val loadedLabels = LangStore.languageLabelsFor(pkgFilter)
        val loadedCodes = LangStore.languageCodesFor(pkgFilter)
        val loadedChecked = LangStore.checkedStatesFor(pkgFilter, modeInt, EasyVoiceTtsService.autoLang,
            EasyVoiceTtsService.dualLang, EasyVoiceTtsService.mixLatinLang, EasyVoiceTtsService.mixNonLatinLang)
        LangStore.persistLanguages(context)
        Triple(loadedLabels, loadedCodes, loadedChecked)
    }
    val labels = loaded.first
    val codes = loaded.second
    val checked = remember { mutableStateListOf<Boolean>().also { it.addAll(loaded.third) } }
    var query by remember { mutableStateOf("") }
    var showSelectedOnly by remember { mutableStateOf(false) }
    // The region comes from the device, not from the scan: Locale.getDefault()
    // is whatever the user has set. Which languages belong to it has to come
    // from the voices, because LangEntry has no country -- rebuildFromScan
    // de-duplicates on getDisplayLanguage(), so en_US and en_IN are one row.
    val deviceCountry = remember { Locale.getDefault().country }
    val regionName = remember(deviceCountry) {
        if (deviceCountry.isEmpty()) "" else Locale("", deviceCountry).displayCountry
    }
    val regionLangs = remember(deviceCountry) {
        val out = HashSet<String>()
        if (deviceCountry.isNotEmpty()) {
            for (voice in EngineFinder.lastScanVoices) {
                if (voice.locale.country.equals(deviceCountry, true)) out.add(EngineFinder.iso3Of(voice.locale))
            }
        }
        out
    }
    fun requiredNow(): ArrayList<String> = LangStore.requiredLangs(modeInt,
        EasyVoiceTtsService.autoLang, EasyVoiceTtsService.dualLang,
        EasyVoiceTtsService.mixLatinLang, EasyVoiceTtsService.mixNonLatinLang)
    fun onRowToggled(orig: Int, isChecked: Boolean) {
        val code = if (orig < LangStore.languages.size) LangStore.languages[orig].iso3 else return
        if (requiredNow().contains(code)) {
            if (orig < checked.size) checked[orig] = true
            return
        }
        if (orig < checked.size) checked[orig] = isChecked
        var langIdx = 0
        while (langIdx < LangStore.languages.size) {
            val entry = LangStore.languages[langIdx]
            langIdx++
            if (code.equals(entry.iso3, true)) { entry.disabled = !isChecked; LangStore.persistDisabled(context); break }
        }
    }
    val visibleIdx = labels.indices.filter { index ->
        val matches = query.isEmpty() || labels[index].lowercase(Locale.getDefault())
            .contains(query.lowercase(Locale.getDefault()))
        val selected = if (showSelectedOnly) (index < checked.size && checked[index]) else true
        matches && selected
    }
    ResponsiveContent {
        // The heading, the note, the three list actions and the search field are
        // NOT members of the language collection, so they sit above the list
        // instead of inside it. A LazyColumn publishes collectionInfo covering
        // everything it holds, so as `item {}` entries they were counted as rows
        // and shifted every language's reported position. Keeping them fixed
        // also means search and select-all stay reachable without scrolling back
        // up through 130 languages.
        Column(modifier = Modifier.fillMaxSize()) {
            // Capped and independently scrollable, so a large font scale can
            // never squeeze the list to nothing. Its natural height is about
            // 235dp, so at the default scale the cap is never reached and this
            // behaves exactly like a plain column; past roughly 1.5x the header
            // scrolls within itself and the list keeps the rest of the screen.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 280.dp)
                    .verticalScroll(rememberScrollState())
            ) {
            SectionHeader("Languages")
            Text(
                text = "Pick the languages you use. The list only shows what your installed engines can speak, so install another engine if the one you want is missing.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        for (index in visibleIdx) if (index < checked.size) checked[index] = true
                        var enableIdx = 0
                        while (enableIdx < LangStore.languages.size) { LangStore.languages[enableIdx].disabled = false; enableIdx++ }
                        LangStore.persistDisabled(context)
                    },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Select all" }
                ) { Text("Select all", modifier = Modifier.clearAndSetSemantics { }) }
                Button(
                    onClick = {
                        for (index in visibleIdx) if (index < checked.size) checked[index] = false
                        var disableIdx = 0
                        while (disableIdx < LangStore.languages.size) { LangStore.languages[disableIdx].disabled = true; disableIdx++ }
                        val requiredForClear = requiredNow()
                        var keepIdx = 0
                        while (keepIdx < LangStore.languages.size) {
                            val entry = LangStore.languages[keepIdx]
                            keepIdx++
                            if (requiredForClear.contains(entry.iso3)) entry.disabled = false
                        }
                        for (index in codes.indices) {
                            if (!requiredForClear.contains(codes[index])) continue
                            if (index < checked.size) checked[index] = true
                        }
                        LangStore.persistDisabled(context)
                    },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Clear all" }
                ) { Text("Clear all", modifier = Modifier.clearAndSetSemantics { }) }
                // This one FILTERS the list, it does not perform an action.
                // Material: "Filter chips use tags or descriptive words to
                // filter content... a good alternative to toggle buttons or
                // checkboxes." As a Button whose label flipped between
                // "Show selected" and "Show all", nothing announced whether
                // the filter was ON; a chip carries a real selected state,
                // so TalkBack says "selected" and the label can stay fixed.
                FilterChip(
                    selected = showSelectedOnly,
                    onClick = { showSelectedOnly = !showSelectedOnly },
                    label = { Text("Show selected", modifier = Modifier.clearAndSetSemantics { }) },
                    leadingIcon = {
                        if (showSelectedOnly) {
                            Icon(
                                painterResource(R.drawable.ic_check),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).semantics { contentDescription = "Show selected" }
                )
            }
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search languages") },
                singleLine = true,
                // Only offered once there is something to clear, so it is not a
                // dead stop for a screen reader on an empty field. The name goes
                // on the IconButton and the Icon is left null: the button merges
                // its children, so a description on the icon alone never reaches
                // the node a screen reader focuses. The FIELD itself must stay
                // without a contentDescription -- EditableContentDescCheck.
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = { query = "" },
                            modifier = Modifier.semantics { contentDescription = "Clear search" }
                        ) {
                            Icon(painterResource(R.drawable.ic_close), contentDescription = null)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            }
            // An empty result was silent: a blind user who mistypes in the
            // search field heard nothing at all and could not tell the filter
            // from a frozen screen. This is the only state on the screen with
            // nothing to perceive, so it gets a message.
            if (visibleIdx.isEmpty()) {
                Text(
                    text = if (showSelectedOnly) "No languages are selected yet."
                           else "No languages match your search.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            // The device region's languages first, everything else below.
            // Splitting AFTER the search/filter means both groups follow it.
            val regionIdx = visibleIdx.filter { it < codes.size && regionLangs.contains(codes[it]) }
            val otherIdx = visibleIdx.filter { !(it < codes.size && regionLangs.contains(codes[it])) }
            // Only group when there is something on both sides -- otherwise the
            // headings would just add noise to a single flat list.
            val grouped = regionName.isNotEmpty() && regionIdx.isNotEmpty() && otherIdx.isNotEmpty()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    // Overrides the count LazyColumn publishes for itself, which
                    // would include the two headings.
                    .semantics { collectionInfo = CollectionInfo(rowCount = visibleIdx.size, columnCount = 1) }
            ) {
                if (grouped) {
                    item { SectionHeader(regionName + " languages") }
                    items(regionIdx.size) { position ->
                        val index = regionIdx[position]
                        LanguageCheckRow(labels[index], index < checked.size && checked[index],
                            position) { newValue -> onRowToggled(index, newValue) }
                    }
                    item { SectionHeader("All languages") }
                    items(otherIdx.size) { position ->
                        val index = otherIdx[position]
                        LanguageCheckRow(labels[index], index < checked.size && checked[index],
                            regionIdx.size + position) { newValue -> onRowToggled(index, newValue) }
                    }
                } else {
                    items(visibleIdx.size) { position ->
                        val index = visibleIdx[position]
                        LanguageCheckRow(labels[index], index < checked.size && checked[index],
                            position) { newValue -> onRowToggled(index, newValue) }
                    }
                }
            }
        }
    }
}
