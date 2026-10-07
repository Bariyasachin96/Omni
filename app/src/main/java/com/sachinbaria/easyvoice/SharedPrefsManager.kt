package com.sachinbaria.easyvoice
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
// The settings readers the service and screens use. Since 2026-10-07 every read
// is the Jetpack DataStore snapshot (LangStore.prefs) and every write goes
// through LangStore.write / writeLater; the class keeps its old name only so
// its many callers and harnesses did not have to move.
class SharedPrefsManager(context: Context) {
    private val appContext = context.applicationContext
    private val prefs get() = LangStore.prefs(appContext)
    // Called several times per chunk with the same few codes. A code's answer
    // never changes within a process (Locale's ISO tables are fixed), so it is
    // remembered: a two-letter code built a Locale and looked it up every time
    // (owner, 2026-10-01). Same function, same answers -- tools/verify/toiso3.
    // Capped: a client could send endless odd codes, and past 512 the answer
    // is simply computed as before.
    fun toIso3(lang: String): String = toIso3Cached(lang)
    companion object {
        private val toIso3Cache = java.util.concurrent.ConcurrentHashMap<String, String>()
        // The cache lives here so code without a SharedPrefsManager (TextChunk's
        // memo) reaches the very same answers.
        @JvmStatic
        fun toIso3Cached(lang: String): String =
            toIso3Cache[lang] ?: toIso3Uncached(lang).also { if (toIso3Cache.size < 512) toIso3Cache[lang] = it }
        fun toIso3Uncached(lang: String): String {
            val iso3 = if (lang.length != 2) lang else try { localeOf(lang).isO3Language.ifEmpty { lang } } catch (_: Exception) { lang }
            return when (iso3) { "cmn", "lzh", "gan", "hak" -> "zho"; else -> iso3 }
        }
    }
    fun getLanguageList(): List<String> {
        val out = mutableListOf<String>()
        var index = 0
        while (true) {
            val lang = prefs[stringPreferencesKey("language_$index")] ?: ""
            if (lang.isEmpty()) break
            out.add(lang); index++
            if (index > 1000) break
        }
        return out
    }
    fun setScannedLangs(iso3: Set<String>) {
        val codes = iso3.toList()
        LangStore.writeLater(appContext) {
            for (index in codes.indices) it[stringPreferencesKey("language_$index")] = codes[index]
            it[stringPreferencesKey("language_${codes.size}")] = ""
        }
    }
    fun getScannedLangs(): Set<String> {
        val legacy = prefs[stringPreferencesKey("scanned_langs_iso3")] ?: ""
        if (legacy.isNotEmpty() && getLanguageList().isEmpty()) {
            setScannedLangs(legacy.split(",").filter { it.isNotBlank() }.map { toIso3(it) }.toHashSet())
            LangStore.writeLater(appContext) { it.remove(stringPreferencesKey("scanned_langs_iso3")) }
        }
        return getLanguageList().map { toIso3(it) }.toHashSet()
    }
    fun getString(key: String, defaultValue: String): String = prefs[stringPreferencesKey(key)] ?: defaultValue
    fun getInt(key: String, defaultValue: Int): Int = prefs[intPreferencesKey(key)] ?: defaultValue
    fun getBoolean(key: String, defaultValue: Boolean): Boolean = prefs[booleanPreferencesKey(key)] ?: defaultValue
    fun getFloat(key: String, defaultValue: Float): Float = prefs[floatPreferencesKey(key)] ?: defaultValue
    fun getReadingMode(): String {
        val autoMode = EasyVoiceTtsService.modeInt
        return when (autoMode) { 0 -> "none"; 1 -> "dual"; 2 -> "auto"; 3 -> "google"; 4 -> "mix"; 5 -> "multilingual"; else -> "auto" }
    }
    fun setReadingMode(mode: String) {
        val autoMode = when (mode) { "none" -> 0; "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 2 }
        EasyVoiceTtsService.modeInt = autoMode
    }
    fun isStripAudioAttr(): Boolean = (prefs[booleanPreferencesKey("strip_audio_attr")] ?: false)
    fun isForceAccessibilityStream(): Boolean = (prefs[booleanPreferencesKey("force_accessibility_stream")] ?: false)
    fun getPuncModeLang(): Int = (prefs[intPreferencesKey("punc_mode_language")] ?: 0)
    fun getEmojiModeLang(): Int = (prefs[intPreferencesKey("emoji_mode_language")] ?: 0)
    fun getNumberModeLang(): Int = (prefs[intPreferencesKey("number_mode_language")] ?: 0)
    fun getPuncSpecificLang(): String = (prefs[stringPreferencesKey("punc_specific_language")] ?: "")
    fun getEmojiSpecificLang(): String = (prefs[stringPreferencesKey("emoji_specific_language")] ?: "")
    fun getNumberSpecificLang(): String = (prefs[stringPreferencesKey("number_specific_language")] ?: "")
    fun isShowNotification(): Boolean = (prefs[booleanPreferencesKey("show_notification")] ?: false)
    fun isLocaleSpansEnabled(): Boolean = (prefs[booleanPreferencesKey("locale_spans")] ?: false)
    // Owner override, 2026-09-02: this arrives OFF, where AutoTTS ships it ON.
    // See LangStore.applyAdvancedDetectionDefault for why, and for the one-time
    // migration that existing installs need on top of the default.
    fun isDisableAdvancedDetection(): Boolean = (prefs[booleanPreferencesKey("disable_advanced_detection")] ?: false)
    fun isKeepAliveMode(): Boolean = (prefs[booleanPreferencesKey("keep_alive_mode")] ?: false)
    fun isQuickCharacterReading(): Boolean = (prefs[booleanPreferencesKey("quick_character_reading")] ?: false)
    fun isPunctuationWithSentence(): Boolean = (prefs[booleanPreferencesKey("punctuation_with_sentence")] ?: true)
    fun isSmartNumberReading(): Boolean = (prefs[booleanPreferencesKey("smart_number_reading")] ?: false)
    // c3.n: getInt("smart_number_reading_group_size", 1). AutoTTS clamps with
    // Math.max(1, f0) at the point of use, not here, so the raw value is kept.
    fun getSmartNumberGroupSize(): Int = (prefs[intPreferencesKey("smart_number_reading_group_size")] ?: 1)
    fun isLoggingEnabled(): Boolean = (prefs[booleanPreferencesKey("logging_enabled")] ?: false)

    fun setLoggingEnabled(value: Boolean) = LangStore.writeLater(appContext) { it[booleanPreferencesKey("logging_enabled")] = value }
}
