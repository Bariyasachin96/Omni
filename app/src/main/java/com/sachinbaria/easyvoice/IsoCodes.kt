package com.sachinbaria.easyvoice
import java.util.Locale
object IsoCodes {
    private val bibliographicToTerminological = listOf("alb" to "sqi", "arm" to "hye", "baq" to "eus", "bur" to "mya", "chi" to "zho", "cze" to "ces", "dut" to "nld", "fre" to "fra", "geo" to "kat", "ger" to "deu", "gre" to "ell", "ice" to "isl", "mac" to "mkd", "mao" to "mri", "may" to "msa", "per" to "fas", "rum" to "ron", "slo" to "slk", "tib" to "bod", "wel" to "cym")
    private val iso2ToTerminological = listOf("sq" to "sqi", "hy" to "hye", "eu" to "eus", "my" to "mya", "zh" to "zho", "cs" to "ces", "nl" to "nld", "fr" to "fra", "ka" to "kat", "de" to "deu", "el" to "ell", "is" to "isl", "mk" to "mkd", "mi" to "mri", "ms" to "msa", "fa" to "fas", "ro" to "ron", "sk" to "slk", "bo" to "bod", "cy" to "cym")
    private val chineseVariants = listOf("cmn", "lzh", "gan", "hak", "wuu", "hsn", "cjy", "nan", "mnp")
    private val codesWithoutIso2 = listOf("fil", "ceb", "haw", "hmn", "war", "nso", "syr", "chr", "lus", "sco")
    private val toIso3Map = HashMap<String, String>(256)
    private val toIso2Map = HashMap<String, String>(320)
    init {
        for (iso2 in Locale.getISOLanguages()) {
            val iso3 = try { localeOf(iso2).isO3Language } catch (_: Exception) { "" }
            if (iso3.isEmpty()) continue
            toIso3Map[iso2] = iso3
            if (!toIso2Map.containsKey(iso3)) toIso2Map[iso3] = iso2
        }
        for ((iso2, iso3) in iso2ToTerminological) {
            toIso3Map[iso2] = iso3
            toIso2Map[iso3] = iso2
        }
        for ((bibliographic, terminological) in bibliographicToTerminological) {
            val iso2 = toIso2Map[terminological] ?: continue
            toIso2Map[bibliographic] = iso2
        }
        toIso3Map["iw"] = "heb"
        toIso3Map["he"] = "heb"
        toIso3Map["in"] = "ind"
        toIso3Map["id"] = "ind"
        toIso3Map["ji"] = "yid"
        toIso3Map["yi"] = "yid"
        // CLD2 SAYS "jw" FOR JAVANESE AND WE LEAVE IT AT THAT (owner, 2026-09-09).
        // A fix was written on 2026-09-09 and REVERTED the same day at the
        // owner's word -- *"agar CLD2 jw kehta hai to jw hi rehne do"*. Keep
        // this note so the finding is not re-made and re-fixed:
        //
        //     "iw",    //  6 HEBREW        handled above
        //     "id",    // 38 INDONESIAN    handled above
        //     "jw",    // 48 JAVANESE      deliberately NOT handled
        //     "yi",    // 91 YIDDISH       handled above
        //
        // Those are the four pre-1989 ISO 639-1 spellings. Java's
        // Locale.getISOLanguages() carries jv and not jw, so toIso3("jw")
        // answers null and languageForDetectedRun's `?: byScript` sends the
        // span to the preferred language. **c3.e behaves identically**, which
        // is the whole reason this stays: rule 5 governs detection, and the
        // three pairs around this comment exist only because AutoTTS has
        // exactly those three. A fourth would be our own decision, and the
        // owner has said no.
        toIso2Map["heb"] = "he"
        toIso2Map["ind"] = "id"
        toIso2Map["yid"] = "yi"
        for (variant in chineseVariants) toIso2Map[variant] = "zh"
        toIso2Map["zho"] = "zh"
        toIso2Map["yue"] = "yue"
        for (code in codesWithoutIso2) {
            toIso2Map[code] = code
            toIso3Map[code] = code
        }
    }
    fun normalizeTag(tag: String?): String? {
        if (tag == null) return null
        val trimmed = tag.trim()
        if (trimmed.isEmpty()) return null
        var cut = trimmed.length
        for (index in trimmed.indices) {
            val character = trimmed[index]
            if (character == '-' || character == '_') { cut = index; break }
        }
        return trimmed.substring(0, cut).lowercase(Locale.ROOT)
    }
    fun toIso2(tag: String?): String? {
        val normalized = normalizeTag(tag) ?: return null
        val mapped = toIso2Map[normalized]
        if (mapped != null) return mapped
        if (normalized.length == 2 && toIso3Map.containsKey(normalized)) return normalized
        return null
    }
    fun toIso3(tag: String?): String? {
        val normalized = normalizeTag(tag) ?: return null
        val mapped = toIso3Map[normalized]
        if (mapped != null) return mapped
        if (normalized.length == 3) {
            val iso2 = toIso2Map[normalized] ?: return null
            return toIso3Map[iso2] ?: normalized
        }
        return null
    }
    fun iso2Pairs(): List<Pair<String, String>> = toIso3Map.entries.map { it.key to it.value }
}

// EVERY LOCALE THE APP BUILDS GOES THROUGH HERE, with the CURRENT method
// wherever the phone has it (owner, 2026-10-13: "jo old method hai vah saari
// mat use karo ... jitni bhi new method hai vah saari use karo"). Locale.of is
// the factory that replaced the constructors (Java 19; Android from API 36).
// Below Android 16 the constructor is the only way to build a Locale from these
// three parts (Locale.Builder rejects what engines actually send, such as
// three-letter countries), so a phone on Android 16+ uses the new method and an
// older one the method it has. Both build the same object: BaseLocale.getInstance
// with the same old-ISO-code conversion and the same compatibility extensions
// (checked: tools/verify/toiso3 runs both over every code the app meets).
fun localeOf(language: String, country: String = "", variant: String = ""): Locale =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.BAKLAVA) Locale.of(language, country, variant)
    else @Suppress("DEPRECATION") Locale(language, country, variant)

// A LANGUAGE CODE AS THE SETTINGS AND THE ENGINES KEY IT: three letters. A
// two-letter code becomes its ISO 639-2 code through Locale, anything else is
// kept as it is, and the Chinese macrolanguage codes fold to "zho".
//
// Called several times per chunk with the same few codes. A code's answer never
// changes within a process (Locale's ISO tables are fixed), so it is remembered:
// a two-letter code built a Locale and looked it up every time (owner,
// 2026-10-01). Same answers -- tools/verify/toiso3. Capped: a client could send
// endless odd codes, and past 512 the answer is simply computed.
private val iso3OfCodeCache = java.util.concurrent.ConcurrentHashMap<String, String>()
fun iso3OfCode(lang: String): String =
    iso3OfCodeCache[lang] ?: iso3OfCodeUncached(lang).also { if (iso3OfCodeCache.size < 512) iso3OfCodeCache[lang] = it }
fun iso3OfCodeUncached(lang: String): String {
    val iso3 = if (lang.length != 2) lang else try { localeOf(lang).isO3Language.ifEmpty { lang } } catch (_: Exception) { lang }
    return when (iso3) { "cmn", "lzh", "gan", "hak" -> "zho"; else -> iso3 }
}
