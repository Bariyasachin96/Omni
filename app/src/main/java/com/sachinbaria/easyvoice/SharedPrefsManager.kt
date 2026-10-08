package com.sachinbaria.easyvoice
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
// What is left of the old preferences helper: the ISO-code cache, the reading
// mode (which lives in EasyVoiceTtsService.modeInt), and the logging flag.
// Since 2026-10-07 the settings are Jetpack DataStore (LangStore.prefs /
// write / writeLater); since 2026-10-08 the sixteen per-setting readers are gone
// too -- the service loads every flag through LangStore.loadFlags and friends,
// one snapshot each, instead of one blocking DataStore read per flag. The class
// keeps its name so its callers and harnesses did not have to move.
class SharedPrefsManager(context: Context) {
    private val appContext = context.applicationContext
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
    fun getReadingMode(): String {
        val autoMode = EasyVoiceTtsService.modeInt
        return when (autoMode) { 0 -> "none"; 1 -> "dual"; 2 -> "auto"; 3 -> "google"; 4 -> "mix"; 5 -> "multilingual"; else -> "auto" }
    }
    fun setReadingMode(mode: String) {
        val autoMode = when (mode) { "none" -> 0; "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 2 }
        EasyVoiceTtsService.modeInt = autoMode
    }
    // The one setting read through this class, because it is the one AutoTTS
    // persists eagerly (c3.p) and seeds before anything else (the service's
    // onCreate and MainActivity's). Every other setting is loaded into the
    // statics by LangStore's loaders, from one snapshot each.
    fun isLoggingEnabled(): Boolean = (LangStore.prefs(appContext)[booleanPreferencesKey("logging_enabled")] ?: false)

    fun setLoggingEnabled(value: Boolean) = LangStore.writeLater(appContext) { it[booleanPreferencesKey("logging_enabled")] = value }
}
