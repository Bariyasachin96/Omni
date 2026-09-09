package com.tts.easyvoice
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
            val iso3 = try { Locale(iso2).isO3Language } catch (_: Exception) { "" }
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
        // THE FOURTH DEPRECATED CODE, and it is the one c3.e leaves out
        // (found 2026-09-09 while auditing the reading path against the full
        // CLD2 tables). The three lines above exist because Java's Locale
        // still answers with the pre-1989 ISO 639-1 spellings -- iw, in, ji --
        // where the modern codes are he, id, yi. Javanese changed the same way,
        // jw to jv, and CLD2's own kLanguageToCode still says the OLD one:
        //
        //     "iw",    //  6 HEBREW        <- handled above
        //     "id",    // 38 INDONESIAN    <- handled above
        //     "jw",    // 48 JAVANESE      <- was handled NOWHERE
        //     "yi",    // 91 YIDDISH       <- handled above
        //
        // Locale.getISOLanguages() carries jv and NOT jw, so the seeding loop
        // never makes a jw key and toIso3("jw") answered null. Javanese text
        // was therefore detected correctly and then resolved by
        // languageForDetectedRun's `?: byScript` to the preferred Latin
        // language -- the wrong voice, on a language Google TTS really speaks.
        // AutoTTS carries the identical gap; this is a DELIBERATE DEPARTURE of
        // exactly the same shape and size as the three lines above it.
        toIso3Map["jw"] = "jav"
        toIso2Map["jw"] = "jv"
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
