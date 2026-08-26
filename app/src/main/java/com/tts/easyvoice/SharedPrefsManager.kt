package com.tts.easyvoice
import android.content.Context
import android.content.SharedPreferences
class SharedPrefsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("easy_voice_settings", Context.MODE_PRIVATE)
    private val appCtx: Context = context.applicationContext
    private fun isGoogleTtsInstalled(): Boolean = try {
        appCtx.packageManager.getPackageInfo("com.google.android.tts", 0); true
    } catch (_: Exception) { false }
    fun toIso3(lang: String): String {
        val iso3 = if (lang.length != 2) lang else try { java.util.Locale(lang).isO3Language.ifEmpty { lang } } catch (_: Exception) { lang }
        return when (iso3) { "cmn", "lzh", "gan", "hak" -> "zho"; else -> iso3 }
    }
    fun getLocaleForLangPkg(lang: String, pkg: String): String {
        val combined = prefs.getString(toIso3(lang), "") ?: ""
        return combined.split("#", limit = 2).getOrElse(1) { "" }
    }
    fun getLanguageList(): List<String> {
        val out = mutableListOf<String>()
        var index = 0
        while (true) {
            val lang = prefs.getString("language_$index", "") ?: ""
            if (lang.isEmpty()) break
            out.add(lang); index++
            if (index > 1000) break
        }
        return out
    }
    fun setScannedLangs(iso3: Set<String>) {
        val editor = prefs.edit()
        var index = 0
        for (langCode in iso3) { editor.putString("language_$index", langCode); index++ }
        editor.putString("language_$index", "")
        editor.apply()
    }
    fun getScannedLangs(): Set<String> {
        val legacy = prefs.getString("scanned_langs_iso3", "") ?: ""
        if (legacy.isNotEmpty() && getLanguageList().isEmpty()) {
            setScannedLangs(legacy.split(",").filter { it.isNotBlank() }.map { toIso3(it) }.toHashSet())
            prefs.edit().remove("scanned_langs_iso3").apply()
        }
        return getLanguageList().map { toIso3(it) }.toHashSet()
    }
    fun getString(key: String, defaultValue: String): String = prefs.getString(key, defaultValue) ?: defaultValue
    fun getInt(key: String, defaultValue: Int): Int = prefs.getInt(key, defaultValue)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean = prefs.getBoolean(key, defaultValue)
    fun getFloat(key: String, defaultValue: Float): Float = prefs.getFloat(key, defaultValue)
    fun getReadingMode(): String {
        val autoMode = EasyVoiceTtsService.modeInt
        return when (autoMode) { 0 -> "none"; 1 -> "dual"; 2 -> "auto"; 3 -> "google"; 4 -> "mix"; 5 -> "multilingual"; else -> "auto" }
    }
    fun setReadingMode(mode: String) {
        val autoMode = when (mode) { "none" -> 0; "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 2 }
        EasyVoiceTtsService.modeInt = autoMode
    }
    fun isStripAudioAttr(): Boolean = prefs.getBoolean("strip_audio_attr", false)
    fun isForceAccessibilityStream(): Boolean = prefs.getBoolean("force_accessibility_stream", false)
    fun getPuncModeLang(): Int = prefs.getInt("punc_mode_language", 0)
    fun getEmojiModeLang(): Int = prefs.getInt("emoji_mode_language", 0)
    fun getNumberModeLang(): Int = prefs.getInt("number_mode_language", 0)
    fun getPuncSpecificLang(): String = prefs.getString("punc_specific_language", "") ?: ""
    fun getEmojiSpecificLang(): String = prefs.getString("emoji_specific_language", "") ?: ""
    fun getNumberSpecificLang(): String = prefs.getString("number_specific_language", "") ?: ""
    fun isShowNotification(): Boolean = prefs.getBoolean("show_notification", false)
    fun isLocaleSpansEnabled(): Boolean = prefs.getBoolean("locale_spans", false)
    fun isDisableAdvancedDetection(): Boolean = prefs.getBoolean("disable_advanced_detection", true)
    fun isKeepAliveMode(): Boolean = prefs.getBoolean("keep_alive_mode", false)
    fun isQuickCharacterReading(): Boolean = prefs.getBoolean("quick_character_reading", false)
    fun isPunctuationWithSentence(): Boolean = prefs.getBoolean("punctuation_with_sentence", true)
    fun isSmartNumberReading(): Boolean = prefs.getBoolean("smart_number_reading", false)
    // c3.n: getInt("smart_number_reading_group_size", 1). AutoTTS clamps with
    // Math.max(1, f0) at the point of use, not here, so the raw value is kept.
    fun getSmartNumberGroupSize(): Int = prefs.getInt("smart_number_reading_group_size", 1)
    fun isLoggingEnabled(): Boolean = prefs.getBoolean("logging_enabled", false)
    fun isUseCld3(): Boolean = prefs.getBoolean("use_cld3", false)

    fun setLoggingEnabled(value: Boolean) = prefs.edit().putBoolean("logging_enabled", value).apply()
    fun settingsXmlFile(): java.io.File = java.io.File(appCtx.applicationInfo.dataDir, "shared_prefs/easy_voice_settings.xml")
    fun exportSettingsFile(): java.io.File {
        val src = settingsXmlFile()
        val dir = java.io.File(appCtx.cacheDir, "shared"); dir.mkdirs()
        val file = java.io.File(dir, "easy_voice_settings.xml")
        src.inputStream().use { input -> file.outputStream().use { out -> input.copyTo(out, 1024) } }
        return file
    }
    fun importSettingsXml(xml: String): Boolean {
        try {
            val editor = prefs.edit()
            editor.clear()
            val parser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser()
            parser.setInput(java.io.StringReader(xml))
            var event = parser.eventType
            while (event != org.xmlpull.v1.XmlPullParser.END_DOCUMENT) {
                if (event == org.xmlpull.v1.XmlPullParser.START_TAG) {
                    val tag = parser.name
                    val name = parser.getAttributeValue(null, "name")
                    if (name != null) {
                        when (tag) {
                            "float" -> editor.putFloat(name, parser.getAttributeValue(null, "value").toFloat())
                            "boolean" -> editor.putBoolean(name, "true" == parser.getAttributeValue(null, "value"))
                            "long" -> editor.putLong(name, parser.getAttributeValue(null, "value").toLong())
                            "int" -> editor.putInt(name, parser.getAttributeValue(null, "value").toInt())
                            "string" -> editor.putString(name, parser.nextText())
                        }
                    }
                }
                event = parser.next()
            }
            editor.commit()
            return true
        } catch (ex: Throwable) {
            ex.printStackTrace()
            return false
        }
    }
    fun getReferencedEnginePackagesFromXml(xml: String): Set<String> {
        println("getActiveEnginePackages")
        val disabled = HashSet<String>()
        val engines = HashMap<String, String>()
        try {
            val parser = org.xmlpull.v1.XmlPullParserFactory.newInstance().newPullParser()
            parser.setInput(java.io.StringReader(xml))
            var event = parser.eventType
            while (event != org.xmlpull.v1.XmlPullParser.END_DOCUMENT) {
                if (event == org.xmlpull.v1.XmlPullParser.START_TAG) {
                    val tag = parser.name
                    val name = parser.getAttributeValue(null, "name")
                    if (name != null) {
                        if (tag == "boolean" && name.endsWith("_disabled")) {
                            if ("true" == parser.getAttributeValue(null, "value")) disabled.add(name.substring(0, name.length - 9))
                        } else if (tag == "string" && name.length == 3 && name.all { it.isLetter() }) {
                            val value = parser.nextText()
                            if (value != null && value.contains("#")) engines[name] = value
                        }
                    }
                }
                event = parser.next()
            }
        } catch (ex: Exception) { ex.printStackTrace() }
        val out = HashSet<String>()
        for ((lang, value) in engines) {
            println("- " + lang + " " + value)
            if (lang in disabled) continue
            val enginePkg = value.split("#")[0]
            if (enginePkg.isEmpty() || enginePkg.equals("disable", ignoreCase = true)) continue
            println(" -> " + enginePkg)
            out.add(enginePkg)
        }
        return out
    }
}
