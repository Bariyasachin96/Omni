
// ---- hand-written driver; everything above is lifted verbatim ----
class DetectedRun(val lang: String, val latin: Boolean, val text: String)
// The old code converted through its prefs object, the new through IsoCodes.kt's
// iso3OfCode (2026-10-08); both sides get the REAL function.
class P { fun toIso3(s: String): String = iso3OfCode(s) }
object LangStore {
    var engines: Map<String, String> = emptyMap()
    val calls = ArrayList<String>()
    fun engineFor(lang: String, mode: Int): String { calls.add(lang); return engines[lang] ?: "" }
}
fun main() {
    val rnd = java.util.Random(21)
    val langs = listOf("en", "hi", "gu", "mr", "un", "xx-Latn", "jw", "zh-Hant", "iw", "sr", "eng", "hin", "")
    val iso3s = listOf("eng", "hin", "guj", "mar", "zho", "heb", "srp")
    var cases = 0; var bad = 0
    repeat(50000) {
        LangStore.engines = iso3s.filter { rnd.nextBoolean() }.associateWith { listOf("com.g", "Disable", "")[rnd.nextInt(3)] }
        val mode = listOf(4, 5)[rnd.nextInt(2)]
        val lat = listOf("en", "eng", "hi")[rnd.nextInt(3)]; val non = listOf("hi", "gu", "hin", "")[rnd.nextInt(4)]
        val runs = List(1 + rnd.nextInt(12)) { DetectedRun(langs[rnd.nextInt(langs.size)], rnd.nextBoolean(), "t") }
        val o = OldX(P(), mode); val n = NewX(P(), mode)
        LangStore.calls.clear(); val a = runs.map { o.lang(it, lat, non) }; val oldCalls = ArrayList(LangStore.calls)
        LangStore.calls.clear(); val memo = RunLangMemo(); val b = runs.map { n.lang(it, lat, non, memo) }
        cases++; if (a != b) { bad++; if (bad < 5) println("DIFF memo $a vs $b") }
        LangStore.calls.clear(); val c = runs.map { n.lang(it, lat, non, null) }
        cases++; if (a != c || LangStore.calls != oldCalls) { bad++; if (bad < 5) println("DIFF no-memo (logging on)") }
    }
    println("cases $cases  differ $bad")
    println(if (bad == 0) "IDENTICAL" else "FAILED")
    if (bad != 0) kotlin.system.exitProcess(1)
}
