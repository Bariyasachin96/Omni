// ---- driver: iso3() must always answer iso3OfCodeUncached(lang) of the CURRENT lang
fun main() {
    val codes = (java.util.Locale.getISOLanguages().toList() + listOf("eng", "hin", "guj", "cmn", "zh", "iw", "in", "ji", "", "x", "UNKNOWN", "Hi", "EN"))
    val rnd = java.util.Random(11)
    var calls = 0L; var bad = 0
    val chunks = Array(64) { TextChunk("t", codes[rnd.nextInt(codes.size)]) }
    repeat(400000) {
        val c = chunks[rnd.nextInt(chunks.size)]
        if (rnd.nextInt(5) == 0) c.lang = codes[rnd.nextInt(codes.size)]
        repeat(1 + rnd.nextInt(4)) {
            calls++
            if (c.iso3() != iso3OfCodeUncached(c.lang)) { bad++; if (bad < 5) println("DIFF ${c.lang} -> ${c.iso3()}") }
        }
    }
    // four threads reading and reassigning the same chunks
    val errors = java.util.concurrent.atomic.AtomicInteger()
    val threads = (0 until 4).map { t -> Thread {
        val r = java.util.Random(100L + t)
        repeat(200000) {
            val c = chunks[r.nextInt(chunks.size)]
            val l = c.lang
            val got = c.iso3()
            // a reader may race a reassignment; the answer must belong to SOME
            // language the chunk had, i.e. to l or to the current one
            if (got != iso3OfCodeUncached(l) && got != iso3OfCodeUncached(c.lang)) errors.incrementAndGet()
            if (r.nextInt(50) == 0) c.lang = codes[r.nextInt(codes.size)]
        }
    } }
    threads.forEach { it.start() }; threads.forEach { it.join() }
    bad += errors.get()
    println("$calls single-thread calls + 800000 threaded: " + if (bad == 0) "IDENTICAL" else "$bad DIFFER")
    if (bad != 0) kotlin.system.exitProcess(1)
}
