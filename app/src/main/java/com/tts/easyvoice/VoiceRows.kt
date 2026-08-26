package com.tts.easyvoice
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
object VoiceRows {
    private fun voiceKey(row: EngineFinder.ScanVoice?, selectedIso: String): String =
        if (row == null) "Disable#" + Locale(selectedIso).toString() else row.pkg + "#" + row.locale.toString()
    fun label(row: EngineFinder.ScanVoice?): String {
        if (row == null) return "*Disabled"
        val abbreviated = abbreviateEngineNameFor(row.engineName)
        return if (row.locale.country.isNotEmpty()) abbreviated + ", " + row.locale.getDisplayCountry() else abbreviated
    }
    fun load(context: Context, readingMode: String, selectedIso: String): List<EngineFinder.ScanVoice?> {
        if (selectedIso.isEmpty()) { LangStore.currentVoiceRows = emptyList(); return emptyList() }
        LangStore.currentVoiceIso = selectedIso
        val rawPrefs = context.getSharedPreferences("easy_voice_settings", Context.MODE_PRIVATE)
        fun voiceOrder(key: String): Int {
            val cached = EngineFinder.voiceWeights[key]
            if (cached != null) return cached
            val storedWeight = (rawPrefs.getString(key, "1000") ?: "1000").toInt()
            EngineFinder.voiceWeights[key] = storedWeight
            return storedWeight
        }
        val allVoices = EngineFinder.lastScanVoices.filter { EngineFinder.iso3Of(it.locale) == selectedIso }
        val filtered = if (readingMode == "google") allVoices.filter { it.pkg.equals("com.google.android.tts", true) } else allVoices
        val rows = ArrayList<EngineFinder.ScanVoice?>(filtered)
        val autoModeC = EasyVoiceTtsService.autoLang
        val mixLatinIso3 = EasyVoiceTtsService.mixLatinLang
        val mixNonLatinIso3 = EasyVoiceTtsService.mixNonLatinLang
        if (readingMode == "auto" && selectedIso != autoModeC) rows.add(null)
        if ((readingMode == "mix" || readingMode == "multilingual") && selectedIso != mixLatinIso3 && selectedIso != mixNonLatinIso3) rows.add(null)
        fun tieBreakLabel(row: EngineFinder.ScanVoice?): String = if (row == null) "*Disabled" else abbreviateEngineNameFor(row.engineName)
        val sorted = rows.sortedWith(Comparator { rowA, rowB ->
            val orderA = voiceOrder(voiceKey(rowA, selectedIso))
            val orderB = voiceOrder(voiceKey(rowB, selectedIso))
            if (orderA != orderB) orderA - orderB else tieBreakLabel(rowA).compareTo(tieBreakLabel(rowB), ignoreCase = true)
        })
        LangStore.currentVoiceRows = sorted
        var weightIdx = 0
        while (weightIdx < sorted.size) { EngineFinder.voiceWeights[voiceKey(sorted[weightIdx], selectedIso)] = weightIdx; weightIdx++ }
        return sorted
    }
    fun moveToFront(rows: List<EngineFinder.ScanVoice?>, position: Int, selectedIso: String, entry: LangEntry): List<EngineFinder.ScanVoice?> {
        val reordered = ArrayList<EngineFinder.ScanVoice?>(rows)
        val picked = reordered.removeAt(position)
        reordered.add(0, picked)
        LangStore.currentVoiceRows = reordered
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
    fun speakTest(rows: List<EngineFinder.ScanVoice?>, entry: LangEntry, selectedIso: String, testClient: TextToSpeech?) {
        val row = rows.firstOrNull()
        if (testClient == null || row == null) return
        val voiceLocale = row.locale
        val qualityTag = entry.variant
        val sample = SampleTexts.get(EngineFinder.iso3Of(voiceLocale))
        val spoken = if (sample.isEmpty())
            "Sorry. Sample text for language " + (try { voiceLocale.getDisplayName(Locale("eng")) } catch (_: Exception) { selectedIso }) + " is missing."
        else "[EasyVoice:" + row.pkg + ":" + voiceLocale.toString() + ":" + qualityTag + "]" + sample
        testClient.speak(spoken, TextToSpeech.QUEUE_FLUSH, null, "EasyVoice_Test")
    }
}
