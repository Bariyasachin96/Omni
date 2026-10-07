// ---- driver: the same core answers through both readers -------------------
fun unE(s: String): String {
    val o = StringBuilder(); var i = 0
    while (i < s.length) {
        if (s[i] == '\\' && i + 5 < s.length && s[i + 1] == 'u') { o.append(s.substring(i + 2, i + 6).toInt(16).toChar()); i += 6 }
        else { o.append(s[i]); i++ }
    }
    return o.toString()
}
// What a mix/multilingual loop adds per record, and what dual adds.
fun oldRead(a: Array<String>, quick: Boolean, sink: (String, String, Boolean, String) -> Unit) {
    for (parts in Old.recordsOf(a)) {
        if (parts.size < 4) continue
        val t = parts[0].toIntOrNull() ?: 0
        if (t == 3 || t == 4 || t == 5) sink("S", parts[2], false, parts[3])
        else for (r in Old.detectedRunsOf(parts, quick)) sink("R", r.lang, r.latin, r.text)
        if (parts.size == 4) sink("D", parts[0] + "/" + parts[2], false, parts[3])
    }
}
fun newRead(a: Array<String>, quick: Boolean, sink: (String, String, Boolean, String) -> Unit) {
    New.forEachRecord(a) { at, n ->
        if (n < 4) return@forEachRecord
        val t = New.fieldInt(a[at]) ?: 0
        if (t == 3 || t == 4 || t == 5) sink("S", a[at + 2], false, a[at + 3])
        else New.forEachDetectedRun(a, at, n, quick) { lang, latin, text -> sink("R", lang, latin, text) }
        if (n == 4) sink("D", a[at] + "/" + a[at + 2], false, a[at + 3])
    }
}
fun main(args: Array<String>) {
    val neg = System.getenv("EV_NEG") ?: ""
    val answers = java.io.File(args[0]).readLines().filter { it.startsWith("P\t") || it.startsWith("Q\t") }.map {
        val h = it.split('\t', limit = 4)
        Pair(if (h[3].isEmpty()) arrayOf<String>() else h[3].split('\t').map { f -> unE(f) }.toTypedArray(), h[2] == "1")
    }
    // A few hand-made answers the core never gives, so the edges are walked too:
    // a count past the end, a non-number count, a record with no spans, "" text.
    val edge = listOf(arrayOf("4", "1", "0", "eng", "abc", "9", "x"), arrayOf("x", "1"), arrayOf("4", "0", "0", "eng", ""),
        arrayOf("12", "1", "0", "eng", "hi", "", "en", "1", "", "un", "0", "z"), arrayOf("5", "2", "0", "hin", "क", ""), arrayOf())
    var bad = 0; var n = 0
    Old.fold = arrayOf("ab", "en", "1", "ab"); New.fold = Old.fold
    for ((a, q0) in answers.map { it } + edge.map { Pair(it, false) } + edge.map { Pair(it, true) }) {
        for (q in listOf(q0, !q0)) {
            Old.quickCharacterFlag = q; New.quickCharacterFlag = q
            val o = ArrayList<String>(); val w = ArrayList<String>()
            oldRead(a, q) { k, l, lat, t -> o.add("$k|$l|$lat|$t") }
            newRead(a, q) { k, l, lat, t -> w.add("$k|$l|${if (neg == "latin") !lat else lat}|$t") }
            n++
            if (o != w) { bad++; if (bad <= 5) println("DIFF quick=$q\n  old $o\n  new $w") }
        }
    }
    // cost per answer, both readers, the same answers, warm
    val mx = java.lang.management.ManagementFactory.getThreadMXBean() as com.sun.management.ThreadMXBean
    val tid = Thread.currentThread().id
    var sinkN = 0
    fun measure(f: (Array<String>, Boolean, (String, String, Boolean, String) -> Unit) -> Unit): Pair<Long, Long> {
        repeat(3) { for ((a, q) in answers) f(a, q) { _, _, _, _ -> sinkN++ } }
        val b0 = mx.getThreadAllocatedBytes(tid); val t0 = System.nanoTime()
        repeat(3) { for ((a, q) in answers) f(a, q) { _, _, _, _ -> sinkN++ } }
        val t1 = System.nanoTime(); val b1 = mx.getThreadAllocatedBytes(tid)
        val k = 3L * answers.size
        return Pair((t1 - t0) / k, (b1 - b0) / k)
    }
    val (ot, ob) = measure(::oldRead); val (nt, nb) = measure(::newRead)
    println("$n readings, $bad differ")
    println("per answer: old $ot ns / $ob bytes, new $nt ns / $nb bytes  ($sinkN)")
    kotlin.system.exitProcess(if (bad > 0) 1 else 0)
}
