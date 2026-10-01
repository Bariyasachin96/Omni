
// ---- hand-written driver; everything above is lifted verbatim ----
object EngineFinder { class ScanVoice(val pkg: String, val engineName: String, val locale: Locale) }
typealias ScanVoice = EngineFinder.ScanVoice
fun localeOf(l: String) = Locale(l)
fun voiceKey(row: ScanVoice?, selectedIso: String): String =
    if (row == null) "Disable#" + localeOf(selectedIso).toString() else row.pkg + "#" + row.locale.toString()
fun main(args: Array<String>) {
    val rnd = java.util.Random(3)
    val engines = listOf("Speech Services by Google", "Vocalizer TTS Voice", "eloquence", "Eloquence", "RHVoice", "eSpeak NG", "Samsung text-to-speech engine", "A B C D E F G H")
    val pkgs = listOf("com.google.android.tts", "com.voc", "com.eloq", "org.rh", "com.samsung")
    val locs = listOf(Locale("hi", "IN"), Locale("hi"), Locale("en", "US"), Locale("en", "IN"), Locale("gu", "IN"))
    var cases = 0; var bad = 0
    repeat(30000) {
        val rows = MutableList<ScanVoice?>(rnd.nextInt(12)) { if (rnd.nextInt(8) == 0) null else ScanVoice(pkgs[rnd.nextInt(pkgs.size)], engines[rnd.nextInt(engines.size)], locs[rnd.nextInt(locs.size)]) }
        val weights = HashMap<String, Int>()
        fun order(cache: HashMap<String, Int>, stored: Map<String, Int>) = { key: String -> cache.getOrPut(key) { stored[key] ?: 1000 } }
        val stored = HashMap<String, Int>(); for (p in pkgs) for (l in locs) if (rnd.nextBoolean()) stored["$p#$l"] = rnd.nextInt(6)
        val c1 = HashMap<String, Int>(); val c2 = HashMap<String, Int>()
        val a = sortOld(rows, "hin", order(c1, stored))
        val b = if (args.isNotEmpty()) rows.sortedBy { it?.engineName ?: "" } else sortNew(rows, "hin", order(c2, stored))
        cases++; if (a != b) { bad++; if (bad < 3) println("DIFF sort") }
        // the cache may only differ where the old sort compared nothing (one row)
        if (rows.size >= 2 && c1 != c2) { bad++; if (bad < 3) println("DIFF weight cache") }
    }
    val words = Locale.getAvailableLocales().map { it.displayName }.filter { it.isNotEmpty() }.distinct() + listOf("İstanbul", "ISTANBUL", "ǅ", "Σίσυφος")
    for (def in listOf("en", "tr", "hi", "el", "de")) {
        Locale.setDefault(Locale.forLanguageTag(def))
        repeat(3000) {
            val labels = List(1 + rnd.nextInt(150)) { words[rnd.nextInt(words.size)] }
            val w = words[rnd.nextInt(words.size)]
            val q = when (rnd.nextInt(4)) { 0 -> ""; 1 -> w.take(1 + rnd.nextInt(3)); 2 -> w.uppercase(); else -> w.substring(rnd.nextInt(w.length)) }
            val only = rnd.nextBoolean(); val checked = List(labels.size + rnd.nextInt(3) - 1) { rnd.nextBoolean() }
            val a = filterOld(labels, q, only, checked)
            val b = if (args.isNotEmpty()) labels.indices.filter { labels[it].contains(q) } else filterNew(labels, q, only, checked)
            cases++; if (a != b) { bad++; if (bad < 3) println("DIFF filter '$q' in $def") }
        }
    }
    println("cases $cases  differ $bad")
    println(if (bad == 0) "IDENTICAL" else "FAILED")
    if (bad != 0) kotlin.system.exitProcess(1)
}
