
// ---- hand-written driver; everything above is lifted verbatim ----
// The old shape, as LangStore.rebuildFromScan / dualLangList and
// EngineFinder.finalizeScan wrote it before 2026-10-01.
fun oldSortEntries(out: MutableList<String>) {
    val collator = java.text.Collator.getInstance()
    java.util.Collections.sort(out) { left, right -> collator.compare(sortKey(left), sortKey(right)) }
}
fun oldSortCodes(langs: Collection<String>, names: Map<String, String>): List<String> {
    val collator = java.text.Collator.getInstance()
    fun key(code: String) = java.text.Normalizer.normalize(names[code] ?: code, java.text.Normalizer.Form.NFD).replace(COMBINING_MARKS, "")
    return langs.sortedWith { l, r -> collator.compare(key(l), key(r)) }
}
fun main(args: Array<String>) {
    val broken = args.isNotEmpty()
    val rnd = java.util.Random(5)
    val locales = java.util.Locale.getAvailableLocales()
    val defaults = listOf("en-US", "hi-IN", "gu-IN", "ar", "de", "sv", "tr", "zh-CN", "ja", "fr", "es", "th", "vi").map { java.util.Locale.forLanguageTag(it) }
    var cases = 0; var bad = 0
    for (def in defaults) {
        java.util.Locale.setDefault(def)
        // every language name Java knows, in this display locale, plus odd ones
        val all = locales.map { it.getDisplayLanguage(def) }.filter { it.isNotEmpty() }.distinct() +
            listOf("", "Ångström", "angstrom", "Éwé", "ewe", "Ewe", "Œ", "oe", "ß", "ss", "İ", "i", "ı")
        repeat(400) {
            val n = 1 + rnd.nextInt(200)
            val names = MutableList(n) { all[rnd.nextInt(all.size)] }   // duplicates allowed: stability matters
            val a = ArrayList(names); oldSortEntries(a)
            val b = if (broken) names.sortedWith { l, r -> java.text.Collator.getInstance().compare(l, r) } else sortedByName(names) { it }
            cases++; if (a != b) { bad++; if (bad < 3) println("DIFF $def: $a\n vs $b") }
            // the EngineFinder shape: codes in a LinkedHashSet, names in a map, some missing
            val codes = LinkedHashSet<String>(); val map = HashMap<String, String>()
            repeat(n) { val c = "c" + rnd.nextInt(300); codes.add(c); if (rnd.nextInt(6) != 0) map[c] = all[rnd.nextInt(all.size)] }
            val c1 = oldSortCodes(codes, map)
            val c2 = if (broken) codes.sorted() else sortedByName(codes) { map[it] ?: it }
            cases++; if (c1 != c2) { bad++; if (bad < 3) println("DIFF codes $def") }
        }
    }
    println("cases $cases  differ $bad")
    println(if (bad == 0) "IDENTICAL" else "FAILED")
    if (bad != 0) kotlin.system.exitProcess(1)
}
