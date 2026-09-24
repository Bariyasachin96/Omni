package com.sachinbaria.easyvoice
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
class SharedPrefsManager(context: Context) {
    private val prefs: SharedPreferences = LangStore.prefs(context)
    fun toIso3(lang: String): String {
        val iso3 = if (lang.length != 2) lang else try { localeOf(lang).isO3Language.ifEmpty { lang } } catch (_: Exception) { lang }
        return when (iso3) { "cmn", "lzh", "gan", "hak" -> "zho"; else -> iso3 }
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
        prefs.edit {
            var index = 0
            for (langCode in iso3) { putString("language_$index", langCode); index++ }
            putString("language_$index", "")
        }
    }
    fun getScannedLangs(): Set<String> {
        val legacy = prefs.getString("scanned_langs_iso3", "") ?: ""
        if (legacy.isNotEmpty() && getLanguageList().isEmpty()) {
            setScannedLangs(legacy.split(",").filter { it.isNotBlank() }.map { toIso3(it) }.toHashSet())
            prefs.edit { remove("scanned_langs_iso3") }
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
    // Owner override, 2026-09-02: this arrives OFF, where AutoTTS ships it ON.
    // See LangStore.applyAdvancedDetectionDefault for why, and for the one-time
    // migration that existing installs need on top of the default.
    fun isDisableAdvancedDetection(): Boolean = prefs.getBoolean("disable_advanced_detection", false)
    fun isKeepAliveMode(): Boolean = prefs.getBoolean("keep_alive_mode", false)
    fun isEngineFallback(): Boolean = prefs.getBoolean("engine_fallback", false)
    fun isQuickCharacterReading(): Boolean = prefs.getBoolean("quick_character_reading", false)
    fun isPunctuationWithSentence(): Boolean = prefs.getBoolean("punctuation_with_sentence", true)
    fun isSmartNumberReading(): Boolean = prefs.getBoolean("smart_number_reading", false)
    // c3.n: getInt("smart_number_reading_group_size", 1). AutoTTS clamps with
    // Math.max(1, f0) at the point of use, not here, so the raw value is kept.
    fun getSmartNumberGroupSize(): Int = prefs.getInt("smart_number_reading_group_size", 1)
    fun isLoggingEnabled(): Boolean = prefs.getBoolean("logging_enabled", false)

    fun setLoggingEnabled(value: Boolean) = prefs.edit { putBoolean("logging_enabled", value) }
}
