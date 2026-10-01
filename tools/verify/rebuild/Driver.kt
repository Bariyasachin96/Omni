
// ---- hand-written driver; everything above is lifted verbatim ----
object EngineFinder {
    var builtInEngine = "com.google.android.tts"
    class ScanVoice(val pkg: String, val locale: java.util.Locale)
    fun iso3Of(loc: java.util.Locale?): String = if (loc == null) "zxx" else try {
        val code = loc.isO3Language
        if (code == "cmn" || code == "lzh" || code == "gan" || code == "hak") "zho" else code
    } catch (_: Exception) { "zxx" }
}
class Prefs(val map: Map<String, Any>) {
    fun getInt(k: String, d: Int) = map[k] as Int? ?: d
    fun getString(k: String, d: String?) = map[k] as String? ?: d
    fun getBoolean(k: String, d: Boolean) = map[k] as Boolean? ?: d
}
class Context(val prefs: Prefs)
fun describe(list: List<LangEntry>) = list.joinToString("\n") {
    "${it.displayName}|${it.iso3}|${it.speed}|${it.volume}|${it.pitch}|${it.variant}|${it.enginePkg}|${it.localeTag}|${it.disabled}|${it.enginePkgs}"
}
fun main() {
    val rnd = java.util.Random(13)
    val locales = (java.util.Locale.getAvailableLocales().toList() + listOf(java.util.Locale("cmn"), java.util.Locale("xx"), java.util.Locale(""), java.util.Locale("iw", "IL"), java.util.Locale("he")))
        .filter { true }
    val pkgs = listOf("com.google.android.tts", "com.eloq", "com.voc", "com.x.my_tts")
    var cases = 0; var bad = 0
    for (def in listOf("en", "hi", "tr", "de")) {
        java.util.Locale.setDefault(java.util.Locale.forLanguageTag(def))
        repeat(3000) {
            val voices = List(rnd.nextInt(300)) { EngineFinder.ScanVoice(pkgs[rnd.nextInt(pkgs.size)], locales[rnd.nextInt(locales.size)]) }
            val map = HashMap<String, Any>()
            for (v in voices) {
                val iso = EngineFinder.iso3Of(v.locale)
                if (rnd.nextInt(3) == 0) map[iso + "_disabled"] = rnd.nextBoolean()
                if (rnd.nextInt(4) == 0) map[iso + "_speed"] = rnd.nextInt(500)
                if (rnd.nextInt(5) == 0) map[iso] = pkgs[rnd.nextInt(pkgs.size)] + "#" + v.locale
                if (rnd.nextInt(9) == 0) map[iso] = "broken"
            }
            val ctx = Context(Prefs(map))
            val only = rnd.nextBoolean(); val mode = listOf(0, 1, 2, 3, 4, 5)[rnd.nextInt(6)]
            val required = List(rnd.nextInt(4)) { EngineFinder.iso3Of(locales[rnd.nextInt(locales.size)]) }
            OldR.persisted = 0; NewR.persisted = 0
            val a = describe(OldR.rebuildFromScan(ctx, only, mode, required, voices))
            val b = describe(NewR.rebuildFromScan(ctx, only, mode, required, voices))
            cases++
            if (a != b || OldR.persisted != NewR.persisted) { bad++; if (bad < 3) println("DIFF\n$a\n--\n$b") }
        }
    }
    // Two display names with ONE iso3 ("cmn" and Chinese both resolve to zho).
    // Both read the same zho_disabled and the same `required`, so one cannot be
    // filtered out while the other is kept -- which is why registering a
    // filtered entry in the map would change nothing either. Checked anyway.
    for (firstDisabled in listOf(true, false)) for (only in listOf(true, false)) {
        val voices = listOf(EngineFinder.ScanVoice("com.a", java.util.Locale("cmn")), EngineFinder.ScanVoice("com.b", java.util.Locale("zh", "CN")),
            EngineFinder.ScanVoice("com.c", java.util.Locale("zh", "TW")), EngineFinder.ScanVoice("com.d", java.util.Locale("cmn", "CN")))
        val ctx = Context(Prefs(mapOf("zho_disabled" to firstDisabled)))
        val a = describe(OldR.rebuildFromScan(ctx, only, 4, if (firstDisabled) emptyList() else listOf("zho"), voices))
        val b = describe(NewR.rebuildFromScan(ctx, only, 4, if (firstDisabled) emptyList() else listOf("zho"), voices))
        cases++
        if (a != b) { bad++; if (bad < 3) println("DIFF fixed\n$a\n--\n$b") }
    }
    println("cases $cases  differ $bad")
    println(if (bad == 0) "IDENTICAL" else "FAILED")
    if (bad != 0) kotlin.system.exitProcess(1)
}
