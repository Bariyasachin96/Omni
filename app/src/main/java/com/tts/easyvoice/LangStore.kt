package com.tts.easyvoice
import android.content.Context
class LangEntry(
    @JvmField var displayName: String,
    @JvmField var iso3: String,
    @JvmField var speed: Int,
    @JvmField var volume: Int,
    @JvmField var pitch: Int,
    @JvmField var enginePkg: String,
    @JvmField var localeTag: String,
    @JvmField var variant: String
) {
    @JvmField var disabled: Boolean = false
    @JvmField val enginePkgs: java.util.LinkedHashSet<String> = java.util.LinkedHashSet()
}
object LangStore {
    @JvmStatic val languages: java.util.ArrayList<LangEntry> = java.util.ArrayList()
    @JvmStatic var currentVoiceIso: String = ""
    @JvmStatic var currentVoiceRows: List<EngineFinder.ScanVoice?> = emptyList()
    @JvmStatic
    fun loadLanguages(ctx: Context) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "loadLanguages")
        val parsed = ArrayList<LangEntry>()
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        val storedKeyCount = sharedPrefs.all.size
        var index = 0
        var emptyRun = 0
        while (index < 64) {
            val iso = sharedPrefs.getString("language_$index", "") ?: ""
            if (iso.isEmpty()) {
                emptyRun++
                index++
                if (emptyRun < 8) continue
                if (parsed.isEmpty()) continue
                break
            }
            val speed = sharedPrefs.getInt(iso + "_speed", 100)
            val pitch = sharedPrefs.getInt(iso + "_pitch", 100)
            val volume = sharedPrefs.getInt(iso + "_volume", 100)
            val variant = sharedPrefs.getString(iso + "_variant", "*Default") ?: "*Default"
            val stored = sharedPrefs.getString(iso, "") ?: ""
            var engine = ""
            var locale = ""
            if (stored.isNotEmpty()) {
                val parts = stored.split("#")
                if (parts.size >= 2) { engine = parts[0]; locale = parts[1] }
            }
            val entry = LangEntry("", iso, speed, volume, pitch, engine, locale, variant)
            entry.disabled = sharedPrefs.getBoolean(iso + "_disabled", false)
            parsed.add(entry)
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " - " + iso + " " + engine + " " + locale + " " + variant)
            index++
        }
        if (parsed.isEmpty() && storedKeyCount > 0) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "prefs readable (" + storedKeyCount + " keys) but no languages parsed")
            return
        }
        if (parsed.isEmpty()) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "no languages loaded - keeping previous state")
            return
        }
        languages.clear()
        languages.addAll(parsed)
    }
    @JvmStatic
    fun persistLanguages(ctx: Context) {
        synchronized(languages) {
            val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
            for (index in languages.indices) {
                val entry = languages[index]
                editor.putString("language_$index", entry.iso3)
                editor.putInt(entry.iso3 + "_speed", entry.speed)
                editor.putInt(entry.iso3 + "_volume", entry.volume)
                editor.putInt(entry.iso3 + "_pitch", entry.pitch)
                editor.putString(entry.iso3 + "_variant", entry.variant)
            }
            editor.putString("language_${languages.size}", "")
            editor.commit()
        }
    }
    // Everything "set up" means for one language, undone: the chosen voice, the
    // variant and the three sliders, in memory AND in prefs. The stored keys
    // have to be removed rather than just blanked, because rebuildFromScan
    // reads them straight back in.
    @JvmStatic
    fun clearConfiguration(ctx: Context, entry: LangEntry) {
        entry.enginePkg = ""
        entry.localeTag = ""
        entry.variant = "*Default"
        entry.speed = 100
        entry.volume = 100
        entry.pitch = 100
        val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
        editor.remove(entry.iso3)
        editor.remove(entry.iso3 + "_speed")
        editor.remove(entry.iso3 + "_volume")
        editor.remove(entry.iso3 + "_pitch")
        editor.remove(entry.iso3 + "_variant")
        editor.commit()
    }
    @JvmStatic
    fun persistDisabled(ctx: Context) {
        synchronized(languages) {
            val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
            var index = 0
            while (index < languages.size) { val entry = languages[index]; index++; editor.putBoolean(entry.iso3 + "_disabled", entry.disabled) }
            editor.commit()
        }
    }
    @JvmStatic
    fun requiredLangs(modeInt: Int, autoLang: String, dualLang: String, mixLatin: String, mixNonLatin: String): ArrayList<String> {
        val out = ArrayList<String>()
        when (modeInt) {
            1 -> { out.add(dualLang); out.add("eng") }
            2, 3 -> { out.add(autoLang); return out }
            4, 5 -> { out.add(mixLatin); out.add(mixNonLatin) }
            else -> return out
        }
        if (EasyVoiceTtsService.numberModeInt == 3) out.add(EasyVoiceTtsService.numberSpecificLang)
        if (EasyVoiceTtsService.punctuationModeInt == 3) out.add(EasyVoiceTtsService.puncSpecificLang)
        if (EasyVoiceTtsService.emojiModeInt == 3) out.add(EasyVoiceTtsService.emojiSpecificLang)
        return out
    }
    private fun sortKey(text: String): String =
        java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD).replace("\\p{M}".toRegex(), "")
    @JvmStatic
    @Synchronized
    fun rebuildFromScan(ctx: Context, onlyEnabled: Boolean, modeInt: Int, required: List<String>,
                        voices: List<EngineFinder.ScanVoice>): ArrayList<LangEntry> {
        val seenNames = ArrayList<String>()
        val out = ArrayList<LangEntry>()
        var changed = false
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        for (scanVoice in voices) {
            val name = scanVoice.locale.getDisplayLanguage()
            val iso3 = EngineFinder.iso3Of(scanVoice.locale)
            val pkg = scanVoice.pkg
            if (modeInt == 3 && !pkg.equals("com.google.android.tts", true)) continue
            if (!seenNames.contains(name)) {
                seenNames.add(name)
                val entry = LangEntry(name, iso3, 100, 100, 100, "", "", "*Default")
                entry.volume = sharedPrefs.getInt(iso3 + "_volume", 100)
                entry.pitch = sharedPrefs.getInt(iso3 + "_pitch", 100)
                entry.speed = sharedPrefs.getInt(iso3 + "_speed", 100)
                entry.variant = sharedPrefs.getString(iso3 + "_variant", "*Default") ?: "*Default"
                entry.enginePkg = ""
                entry.localeTag = ""
                // Default TRUE = start cleared. AutoTTS defaults this to false, so
                // every scanned language arrived ticked and the Languages screen
                // opened with all ~137 boxes checked; the user asked for the
                // opposite. The line below already re-enables whatever the mode
                // actually requires -- autoLang for auto/google, dualLang + "eng"
                // for dual, mixLatin + mixNonLatin for mix/multilingual -- so the
                // default language stays ticked and nothing else does.
                // Only applies where the key has never been written: an install
                // that already has choices saved keeps them. "Clear all" produces
                // this same state on demand.
                entry.disabled = sharedPrefs.getBoolean(iso3 + "_disabled", true)
                if (entry.disabled && required.contains(iso3)) { entry.disabled = false; changed = true }
                entry.enginePkgs.add(pkg)
                val stored = sharedPrefs.getString(iso3, "") ?: ""
                if (stored != "") {
                    val parts = stored.split("#")
                    if (parts.size >= 2) { entry.enginePkg = parts[0]; entry.localeTag = parts[1] }
                }
                if (onlyEnabled) { if (!entry.disabled) out.add(entry) } else out.add(entry)
            } else {
                for (existing in out) if (existing.iso3.equals(iso3, true)) { existing.enginePkgs.add(pkg); break }
            }
        }
        val collator = java.text.Collator.getInstance()
        java.util.Collections.sort(out) { left, right -> collator.compare(sortKey(left.displayName), sortKey(right.displayName)) }
        if (changed) persistDisabled(ctx)
        return out
    }
    @JvmStatic
    fun dualLangList(ctx: Context, dualLang: String, voices: List<EngineFinder.ScanVoice>): ArrayList<LangEntry> {
        val seenNames = ArrayList<String>()
        val out = ArrayList<LangEntry>()
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        for (scanVoice in voices) {
            val name = scanVoice.locale.getDisplayLanguage()
            val iso3 = EngineFinder.iso3Of(scanVoice.locale)
            if (!iso3.equals("eng", true) && !iso3.equals(dualLang, true) || seenNames.contains(name)) continue
            seenNames.add(name)
            val entry = LangEntry(name, iso3, 100, 100, 100, "", "", "*Default")
            entry.volume = sharedPrefs.getInt(iso3 + "_volume", 100)
            entry.pitch = sharedPrefs.getInt(iso3 + "_pitch", 100)
            entry.speed = sharedPrefs.getInt(iso3 + "_speed", 100)
            entry.variant = sharedPrefs.getString(iso3 + "_variant", "*Default") ?: "*Default"
            entry.enginePkg = ""
            entry.localeTag = ""
            entry.disabled = sharedPrefs.getBoolean(iso3 + "_disabled", false)
            val stored = sharedPrefs.getString(iso3, "") ?: ""
            if (stored != "") {
                val parts = stored.split("#")
                if (parts.size >= 2) { entry.enginePkg = parts[0]; entry.localeTag = parts[1] }
            }
            out.add(entry)
        }
        val collator = java.text.Collator.getInstance()
        java.util.Collections.sort(out) { left, right -> collator.compare(sortKey(left.displayName), sortKey(right.displayName)) }
        return out
    }
    @JvmStatic
    // 5.7.7.26 wraps every c3.n accessor that walks the language list in
    // synchronized (c) -- eleven methods, all reads and persists. The writes
    // live outside c3.n and stay unguarded there, so they stay unguarded here
    // too. Kotlin monitors are reentrant, so nesting these costs nothing.
    fun dualLanguageLabels(dualLang: String): ArrayList<String> {
        synchronized(languages) {
            val out = ArrayList<String>()
            var index = 0
            while (index < languages.size) {
                val entry = languages[index]
                index++
                if (!entry.iso3.equals("eng", true) && !entry.iso3.equals(dualLang, true)) continue
                out.add(entry.displayName + " (" + entry.iso3 + ")")
            }
            return out
        }
    }
    @JvmStatic
    fun languageLabelsFor(pkg: String?): ArrayList<String> {
        synchronized(languages) {
            val out = ArrayList<String>()
            var index = 0
            while (index < languages.size) { val entry = languages[index]; index++; if (pkg != null && !entry.enginePkgs.contains(pkg)) continue; out.add(entry.displayName + " (" + entry.iso3 + ")") }
            return out
        }
    }
    @JvmStatic
    fun languageCodesFor(pkg: String?): ArrayList<String> {
        synchronized(languages) {
            val out = ArrayList<String>()
            var index = 0
            while (index < languages.size) { val entry = languages[index]; index++; if (pkg != null && !entry.enginePkgs.contains(pkg)) continue; out.add(entry.iso3) }
            return out
        }
    }
    @JvmStatic
    fun checkedStatesFor(pkg: String?, modeInt: Int, autoLang: String, dualLang: String, mixLatin: String, mixNonLatin: String): ArrayList<Boolean> {
        synchronized(languages) {
            val out = ArrayList<Boolean>()
            var index = 0
            while (index < languages.size) {
                val entry = languages[index]
                index++
                if (pkg != null && !entry.enginePkgs.contains(pkg)) continue
                when (modeInt) {
                    1 -> if (entry.iso3.equals(dualLang, true)) entry.disabled = false
                    2, 3 -> if (entry.iso3.equals(autoLang, true)) entry.disabled = false
                    4, 5 -> if (entry.iso3.equals(mixLatin, true) || entry.iso3.equals(mixNonLatin, true)) entry.disabled = false
                }
                out.add(!entry.disabled)
            }
            return out
        }
    }
    @JvmStatic
    fun availableLanguagesFor(pkg: String?, iso3Only: Boolean): ArrayList<String> {
        synchronized(languages) {
            val out = ArrayList<String>()
            var index = 0
            while (index < languages.size) {
                val entry = languages[index]
                index++
                if (pkg != null && !entry.enginePkgs.contains(pkg) || entry.disabled) continue
                if (!iso3Only) out.add(entry.displayName + " (" + entry.iso3 + ")") else out.add(entry.iso3)
            }
            return out
        }
    }
    @JvmStatic
    fun loadModeLangs(ctx: Context) {
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        val deviceIso3 = try {
            val index = java.util.Locale.getDefault().isO3Language
            if (index == "cmn" || index == "lzh" || index == "gan" || index == "hak") "zho" else index
        } catch (_: Exception) { "zxx" }
        EasyVoiceTtsService.autoLang = (sharedPrefs.getString("auto_mode_language", "") ?: "").ifEmpty { deviceIso3 }
        EasyVoiceTtsService.mixLatinLang = (sharedPrefs.getString("mixed_mode_latin_language", "") ?: "").ifEmpty { deviceIso3 }
        EasyVoiceTtsService.mixNonLatinLang = (sharedPrefs.getString("mixed_mode_non_latin_language", "") ?: "").ifEmpty { deviceIso3 }
        EasyVoiceTtsService.dualLang = (sharedPrefs.getString("dual_mode_language", "") ?: "").ifEmpty { deviceIso3 }
        EasyVoiceTtsService.numberModeInt = sharedPrefs.getInt("number_mode_language", 0)
        EasyVoiceTtsService.punctuationModeInt = sharedPrefs.getInt("punc_mode_language", 0)
        EasyVoiceTtsService.emojiModeInt = sharedPrefs.getInt("emoji_mode_language", 0)
        EasyVoiceTtsService.numberSpecificLang = (sharedPrefs.getString("number_specific_language", "") ?: "").ifEmpty { deviceIso3 }
        EasyVoiceTtsService.puncSpecificLang = (sharedPrefs.getString("punc_specific_language", "") ?: "").ifEmpty { deviceIso3 }
        EasyVoiceTtsService.emojiSpecificLang = (sharedPrefs.getString("emoji_specific_language", "") ?: "").ifEmpty { deviceIso3 }
    }
    @JvmStatic
    fun loadMode(ctx: Context) {
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        var index = sharedPrefs.getInt("auto_mode", 3)
        if (index == 3) {
            val googleInstalled = try { ctx.packageManager.getPackageInfo("com.google.android.tts", 0); true } catch (_: Exception) { false }
            if (!googleInstalled) index = 0
        }
        EasyVoiceTtsService.modeInt = index
        EasyVoiceTtsService.localeSpansFlag = sharedPrefs.getBoolean("locale_spans", false)
    }
    @JvmStatic
    fun loadFlags(ctx: Context) {
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        EasyVoiceTtsService.stripAudioAttrFlag = sharedPrefs.getBoolean("strip_audio_attr", false)
        EasyVoiceTtsService.forceAccessibilityFlag = sharedPrefs.getBoolean("force_accessibility_stream", false)
        EasyVoiceTtsService.keepAliveFlag = sharedPrefs.getBoolean("keep_alive_mode", false)
        EasyVoiceTtsService.showNotificationFlag = sharedPrefs.getBoolean("show_notification", false)
        EasyVoiceTtsService.disableAdvancedFlag = sharedPrefs.getBoolean("disable_advanced_detection", true)
        EasyVoiceTtsService.quickCharacterFlag = sharedPrefs.getBoolean("quick_character_reading", false)
        EasyVoiceTtsService.useCld3Flag = sharedPrefs.getBoolean("use_cld3", false)
        EasyVoiceTtsService.punctuationInFlowFlag = sharedPrefs.getBoolean("punctuation_with_sentence", true)
        EasyVoiceTtsService.smartNumberFlag = sharedPrefs.getBoolean("smart_number_reading", false)
        EasyVoiceTtsService.smartNumberGroupSize = sharedPrefs.getInt("smart_number_reading_group_size", 1)
        EasyVoiceTtsService.useCld3Flag = sharedPrefs.getBoolean("use_cld3", false)
    }
    @JvmStatic
    fun persistEngines(ctx: Context) {
        synchronized(languages) {
            val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
            val newEngineList = ArrayList<String>()
            var index = 0
            for (pkg in EngineFinder.lastScanEngines) {
                if (pkg == "com.tts.easyvoice") continue
                editor.putString("engine_$index", pkg)
                newEngineList.add(pkg)
                index++
            }
            editor.putString("engine_$index", "end")
            editor.commit()
            EasyVoiceTtsService.engineList = newEngineList
        }
    }
    @JvmStatic
    fun persistVoiceList(ctx: Context) {
        synchronized(languages) {
            val voices = EngineFinder.lastScanVoices
            if (voices.isEmpty()) return
            val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
            val newVoiceList = ArrayList<String>()
            for (index in voices.indices) {
                val key = voices[index].pkg + "#" + voices[index].locale.toString()
                editor.putString("voice_$index", key)
                newVoiceList.add(key)
            }
            editor.putString("voice_" + voices.size, "")
            editor.putBoolean("dedicated_engines", EasyVoiceTtsService.dedicatedEnginesFlag)
            editor.commit()
            EasyVoiceTtsService.voiceList = newVoiceList
        }
    }
    @JvmStatic
    fun persistModeLangs(ctx: Context) {
        val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
        editor.putString("auto_mode_language", EasyVoiceTtsService.autoLang)
        editor.putString("dual_mode_language", EasyVoiceTtsService.dualLang)
        editor.putString("mixed_mode_latin_language", EasyVoiceTtsService.mixLatinLang)
        editor.putString("mixed_mode_non_latin_language", EasyVoiceTtsService.mixNonLatinLang)
        editor.putInt("number_mode_language", EasyVoiceTtsService.numberModeInt)
        editor.putInt("punc_mode_language", EasyVoiceTtsService.punctuationModeInt)
        editor.putInt("emoji_mode_language", EasyVoiceTtsService.emojiModeInt)
        editor.putString("number_specific_language", EasyVoiceTtsService.numberSpecificLang)
        editor.putString("punc_specific_language", EasyVoiceTtsService.puncSpecificLang)
        editor.putString("emoji_specific_language", EasyVoiceTtsService.emojiSpecificLang)
        editor.commit()
    }
    @JvmStatic
    fun persistMode(ctx: Context) {
        val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
        editor.putInt("auto_mode", EasyVoiceTtsService.modeInt)
        editor.putBoolean("locale_spans", EasyVoiceTtsService.localeSpansFlag)
        editor.commit()
    }
    @JvmStatic
    fun persistFlags(ctx: Context) {
        val editor = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0).edit()
        editor.putBoolean("strip_audio_attr", EasyVoiceTtsService.stripAudioAttrFlag)
        editor.putBoolean("force_accessibility_stream", EasyVoiceTtsService.forceAccessibilityFlag)
        editor.putBoolean("keep_alive_mode", EasyVoiceTtsService.keepAliveFlag)
        editor.putBoolean("show_notification", EasyVoiceTtsService.showNotificationFlag)
        editor.putBoolean("disable_advanced_detection", EasyVoiceTtsService.disableAdvancedFlag)
        editor.putBoolean("quick_character_reading", EasyVoiceTtsService.quickCharacterFlag)
        editor.putBoolean("punctuation_with_sentence", EasyVoiceTtsService.punctuationInFlowFlag)
        editor.putBoolean("smart_number_reading", EasyVoiceTtsService.smartNumberFlag)
        editor.putInt("smart_number_reading_group_size", EasyVoiceTtsService.smartNumberGroupSize)
        editor.putBoolean("use_cld3", EasyVoiceTtsService.useCld3Flag)
        editor.commit()
    }
    @JvmStatic
    fun voiceRowKey(row: EngineFinder.ScanVoice?): String =
        if (row == null) "Disable#" + java.util.Locale(currentVoiceIso).toString() else row.pkg + "#" + row.locale.toString()
    @JvmStatic
    fun persistVoiceRows(ctx: Context) {
        val sharedPrefs = ctx.applicationContext.getSharedPreferences("easy_voice_settings", 0)
        var index = 0
        while (index < currentVoiceRows.size) {
            val row = currentVoiceRows[index]
            val editor = sharedPrefs.edit()
            val key = voiceRowKey(row)
            editor.putString(key, index.toString())
            if (index == 0) editor.putString(if (row == null) currentVoiceIso else EngineFinder.iso3Of(row.locale), key)
            editor.commit()
            index++
        }
    }
    @JvmStatic
    fun persistAll(ctx: Context) {
        persistEngines(ctx)
        persistVoiceList(ctx)
        persistVoiceRows(ctx)
        persistModeLangs(ctx)
        persistLanguages(ctx)
        persistMode(ctx)
        persistFlags(ctx)
    }
    @JvmStatic
    fun indexOf(iso3: String): Int {
        synchronized(languages) {
            for (index in languages.indices) if (iso3 == languages[index].iso3) return index
            return -1
        }
    }
    @JvmStatic
    fun engineFor(lang: String, modeInt: Int): String {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "getEngine4Language " + lang)
        if (modeInt == 3) return "com.google.android.tts"
        synchronized(languages) {
            var index = 0
            while (index < languages.size) {
                val entry = languages[index]
                index++
                if (entry.enginePkg.isNotEmpty() && !entry.enginePkg.equals("disable", true) && !entry.disabled) EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "- " + entry.iso3 + " " + entry.enginePkg)
                if (lang == entry.iso3) {
                    if (entry.disabled) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " res1 Disable"); return "Disable" }
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " res " + entry.enginePkg)
                    return entry.enginePkg
                }
            }
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " res ''")
        return ""
    }
    @JvmStatic
    fun localeFor(lang: String, modeInt: Int): String {
        synchronized(languages) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "getVoice4Language " + lang)
            if (modeInt == 3) return lang
            var index = 0
            while (index < languages.size) {
                val entry = languages[index]
                index++
                if (entry.enginePkg.isEmpty() || entry.enginePkg.equals("disable", true) || entry.disabled) continue
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + entry.iso3 + " -> " + entry.localeTag)
                if (lang != entry.iso3) continue
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Found " + entry.localeTag)
                return entry.localeTag
            }
            return ""
        }
    }
    @JvmStatic
    fun variantFor(lang: String, modeInt: Int): String {
        synchronized(languages) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "getVariant4Language " + lang)
            if (modeInt == 3) return lang
            var index = 0
            while (index < languages.size) {
                val entry = languages[index]
                index++
                if (entry.enginePkg.isEmpty() || entry.enginePkg.equals("disable", true) || entry.disabled) continue
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + entry.iso3 + " -> " + entry.variant)
                if (lang != entry.iso3) continue
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Found " + entry.variant)
                return entry.variant
            }
            return ""
        }
    }
    @JvmStatic fun speedFor(lang: String): Int = synchronized(languages) { var index = 0; while (index < languages.size) { val entry = languages[index]; index++; if (lang == entry.iso3) return@synchronized entry.speed }; return@synchronized 100 }
    @JvmStatic fun volumeFor(lang: String): Int = synchronized(languages) { var index = 0; while (index < languages.size) { val entry = languages[index]; index++; if (lang == entry.iso3) return@synchronized entry.volume }; return@synchronized 100 }
    @JvmStatic fun pitchFor(lang: String): Int = synchronized(languages) { var index = 0; while (index < languages.size) { val entry = languages[index]; index++; if (lang == entry.iso3) return@synchronized entry.pitch }; return@synchronized 100 }
}
