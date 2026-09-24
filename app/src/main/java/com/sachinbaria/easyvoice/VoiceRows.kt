package com.sachinbaria.easyvoice
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
object VoiceRows {
    private fun voiceKey(row: EngineFinder.ScanVoice?, selectedIso: String): String =
        if (row == null) "Disable#" + localeOf(selectedIso).toString() else row.pkg + "#" + row.locale.toString()
    fun label(row: EngineFinder.ScanVoice?): String {
        if (row == null) return "*Disabled"
        val abbreviated = abbreviateEngineName(row.engineName)
        val base = if (row.locale.country.isNotEmpty()) abbreviated + ", " + row.locale.getDisplayCountry() else abbreviated
        return if (row.notFound) base + " (not found in the last scan)" else base
    }
    fun load(context: Context, readingMode: String, selectedIso: String): List<EngineFinder.ScanVoice?> {
        if (selectedIso.isEmpty()) { LangStore.currentVoiceRows = emptyList(); return emptyList() }
        LangStore.currentVoiceIso = selectedIso
        LangStore.currentVoiceChosen = false
        val rawPrefs = LangStore.prefs(context)
        fun voiceOrder(key: String): Int {
            val cached = EngineFinder.voiceWeights[key]
            if (cached != null) return cached
            // toIntOrNull, and a try, because this reads STORAGE rather than a
            // literal: `.toInt()` threw NumberFormatException on anything that is
            // not a number, and `getString` throws ClassCastException outright if
            // the key was ever written with putInt. Both land inside the Voice
            // setup screen's composition, so they are a crash on a screen the
            // owner opens once per language.
            //
            // The way a wrong type gets in is Import: importSettingsXml writes
            // whatever TYPE the XML tag says, so one bad entry in a settings file
            // the user picked is enough. 1000 is what a MISSING key already
            // answers -- "unranked" -- so a corrupt entry now sorts last instead
            // of taking the screen down, and valid data behaves exactly as before.
            val storedWeight = try {
                (rawPrefs.getString(key, "1000") ?: "1000").toIntOrNull() ?: 1000
            } catch (_: Exception) { 1000 }
            EngineFinder.voiceWeights[key] = storedWeight
            return storedWeight
        }
        val allVoices = EngineFinder.lastScanVoices.filter { EngineFinder.iso3Of(it.locale) == selectedIso }
        val filtered = if (readingMode == "google") allVoices.filter { it.pkg.equals(LangStore.GOOGLE_TTS, true) } else allVoices
        val rows = ArrayList<EngineFinder.ScanVoice?>(filtered)
        val autoModeC = EasyVoiceTtsService.autoLang
        val mixLatinIso3 = EasyVoiceTtsService.mixLatinLang
        val mixNonLatinIso3 = EasyVoiceTtsService.mixNonLatinLang
        if (readingMode == "auto" && selectedIso != autoModeC) rows.add(null)
        if ((readingMode == "mix" || readingMode == "multilingual") && selectedIso != mixLatinIso3 && selectedIso != mixNonLatinIso3) rows.add(null)
        fun tieBreakLabel(row: EngineFinder.ScanVoice?): String = if (row == null) "*Disabled" else abbreviateEngineName(row.engineName)
        val sorted = rows.sortedWith(Comparator { rowA, rowB ->
            val orderA = voiceOrder(voiceKey(rowA, selectedIso))
            val orderB = voiceOrder(voiceKey(rowB, selectedIso))
            if (orderA != orderB) orderA - orderB else tieBreakLabel(rowA).compareTo(tieBreakLabel(rowB), ignoreCase = true)
        })
        val stored = try { rawPrefs.getString(selectedIso, "") ?: "" } catch (_: Exception) { "" }
        val pinned = pinConfigured(context, sorted, stored, selectedIso, readingMode)
        LangStore.currentVoiceRows = pinned
        var weightIdx = 0
        while (weightIdx < pinned.size) { EngineFinder.voiceWeights[voiceKey(pinned[weightIdx], selectedIso)] = weightIdx; weightIdx++ }
        return pinned
    }
    // THE CONFIGURED VOICE IS ALWAYS THE FIRST ROW (2026-09-23, owner: "Google
    // ... uska voice sab ko chala jata hai ... aisa kuchh karo ki vah jaaye hi
    // na").
    //
    // The first row IS the selection: the screen shows it as the chosen voice,
    // and LangStore.persistVoiceRows writes it back as "<iso3> = <pkg>#<locale>"
    // on every persist -- every onPause, and before EVERY Previous/Next on Voice
    // setup. The rows were built only from what the last scan could read and
    // ordered only by the stored weights, so whenever the configured engine was
    // not read (Google failing a scan, a process restored straight onto this
    // screen with no scan in it) or its weight was missing, some other row came
    // first and was written over the configuration. Walking the languages with
    // Next did that to every one of them in a row -- all of Google's languages
    // reassigned or disabled without the owner touching anything.
    //
    // So the stored configuration decides the first row: the matching row is
    // moved up, and a configured voice the scan did not return is shown anyway,
    // marked as not found. Persisting then writes back exactly what was stored.
    // Only picking a different voice (moveToFront) changes the configuration.
    // With nothing configured the weight order stands, as before.
    private fun pinConfigured(context: Context, rows: List<EngineFinder.ScanVoice?>, stored: String, selectedIso: String,
                              readingMode: String): List<EngineFinder.ScanVoice?> {
        val parts = stored.split("#")
        if (parts.size < 2 || parts[0].isEmpty()) return rows
        val out = ArrayList<EngineFinder.ScanVoice?>(rows)
        if (parts[0].equals("disable", true)) {
            val at = out.indexOf(null)
            if (at > 0) out.add(0, out.removeAt(at))
            return out
        }
        if (readingMode == "google" && !parts[0].equals(LangStore.GOOGLE_TTS, true)) return rows
        val at = out.indexOfFirst { it != null && it.pkg == parts[0] && it.locale.toString() == parts[1] }
        if (at == 0) return rows
        if (at > 0) { out.add(0, out.removeAt(at)); return out }
        val locale = EngineFinder.parseStoredLocale(parts[1])
        if (EngineFinder.iso3Of(locale) != selectedIso) return rows
        val engineName = EngineFinder.engineLabel(context, parts[0])
        val variants = arrayListOf("*Default")
        val savedVariant = try { LangStore.prefs(context)
            .getString(selectedIso + "_variant", "*Default") ?: "*Default" } catch (_: Exception) { "*Default" }
        if (savedVariant.isNotEmpty() && savedVariant != "*Default") variants.add(savedVariant)
        out.add(0, EngineFinder.ScanVoice(parts[0], engineName, locale, variants, notFound = true))
        return out
    }
    fun moveToFront(rows: List<EngineFinder.ScanVoice?>, position: Int, selectedIso: String, entry: LangEntry): List<EngineFinder.ScanVoice?> {
        val reordered = ArrayList<EngineFinder.ScanVoice?>(rows)
        val picked = reordered.removeAt(position)
        reordered.add(0, picked)
        LangStore.currentVoiceRows = reordered
        LangStore.currentVoiceChosen = true
        var weightIdx = 0
        while (weightIdx < reordered.size) { EngineFinder.voiceWeights[voiceKey(reordered[weightIdx], selectedIso)] = weightIdx; weightIdx++ }
        if (picked != null) { entry.enginePkg = picked.pkg; entry.localeTag = picked.locale.toString() }
        return reordered
    }
    fun variantsFor(rows: List<EngineFinder.ScanVoice?>, entry: LangEntry): List<String> {
        if (rows.isEmpty()) return emptyList()
        val picked = rows[0]
        if (picked == null) return emptyList()
        val base = picked.variants.toList()
        val slotCount = base.size
        if (slotCount == 0) return emptyList()
        val ordered = Array(slotCount) { "" }
        val savedVariant = entry.variant
        if (savedVariant.isNotEmpty()) {
            ordered[0] = savedVariant
            var nextSlot = 1
            for (index in base.indices) {
                if (base[index] != savedVariant && nextSlot < slotCount) { ordered[nextSlot] = base[index]; nextSlot++ }
            }
        } else {
            for (slotIdx in base.indices) ordered[slotIdx] = base[slotIdx]
        }
        if (slotCount > 1) java.util.Arrays.sort(ordered, 1, slotCount - 1)
        return ordered.toList()
    }
    // THE SAMPLE COMES FROM THE ENGINE FIRST NOW (owner, 2026-09-16). See
    // EngineSample for why it is an activity round trip and why the table below
    // still has to exist.
    //
    // `sampleProvider` answers three ways, and the middle one is what makes the
    // window change happen at most once per language:
    //     a non-empty string -> the engine's own sentence, speak it
    //     ""                 -> asked already, the engine had none, use the table
    //     null               -> it has gone to ASK; do not speak, it will call
    //                           `retry` when the answer lands
    // Passing no provider at all -- which every test and every caller that is not
    // VoiceSetupActivity does -- is byte for byte the behaviour of this method
    // before that date.
    //
    // `retry` is this same call with this same arguments, so the second pass
    // finds the cache populated and speaks. It cannot loop: the provider always
    // writes the key before replaying, even when the engine answered nothing.
    fun speakTest(
        rows: List<EngineFinder.ScanVoice?>,
        entry: LangEntry,
        selectedIso: String,
        testClient: TextToSpeech?,
        sampleProvider: ((String, Locale, () -> Unit) -> String?)? = null
    ) {
        val row = rows.firstOrNull()
        if (testClient == null || row == null) return
        val voiceLocale = row.locale
        val qualityTag = entry.variant
        val fromEngine = if (sampleProvider == null) "" else {
            val retry = { speakTest(rows, entry, selectedIso, testClient, sampleProvider) }
            sampleProvider(row.pkg, voiceLocale, retry) ?: return
        }
        val sample = if (fromEngine.isNotEmpty()) fromEngine
                     else SampleTexts.get(EngineFinder.iso3Of(voiceLocale))
        val spoken = if (sample.isEmpty())
            "Sorry. Sample text for language " + (try { voiceLocale.getDisplayName(localeOf("eng")) } catch (_: Exception) { selectedIso }) + " is missing."
        else "[EasyVoice:" + row.pkg + ":" + voiceLocale.toString() + ":" + qualityTag + "]" + sample
        testClient.speak(spoken, TextToSpeech.QUEUE_FLUSH, null, "EasyVoice_Test")
    }
}
