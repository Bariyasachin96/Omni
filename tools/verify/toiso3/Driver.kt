
// ---- driver: the cached toIso3 must answer exactly what the uncached one does
fun main() {
    val inputs = ArrayList<String>()
    for (l in java.util.Locale.getISOLanguages()) {
        inputs.add(l); inputs.add(l.uppercase())
        val three = try { java.util.Locale(l).isO3Language } catch (_: Exception) { "" }
        if (three.isNotEmpty()) { inputs.add(three); inputs.add(three.uppercase()); inputs.add(three.replaceFirstChar { it.uppercase() }) }
    }
    for (a in 'a'..'z') for (b in 'a'..'z') inputs.add("$a$b")
    inputs.addAll(listOf("", "e", "eng", "hin", "zho", "cmn", "zh", "iw", "in", "ji", "he", "id", "yi", "en-US", "x1", "🙂a", "ENG", "un", "jw"))
    val rnd = java.util.Random(3)
    repeat(300) { inputs.add(String(CharArray(rnd.nextInt(4)) { ('a' + rnd.nextInt(26)) })) }
    val sp = SharedPrefsManager()
    var bad = 0; var calls = 0
    val threads = (0 until 4).map { t -> Thread {
        val r = java.util.Random(t.toLong())
        repeat(200000) {
            val x = inputs[r.nextInt(inputs.size)]
            val got = sp.toIso3(x); val want = SharedPrefsManager.toIso3Uncached(x)
            synchronized(inputs) { calls++; if (got != want) { bad++; if (bad < 5) println("DIFF '$x': $got vs $want") } }
        }
    } }
    threads.forEach { it.start() }; threads.forEach { it.join() }
    println("$calls calls over ${inputs.toSet().size} codes, 4 threads: " + if (bad == 0) "IDENTICAL" else "$bad DIFFER")
    kotlin.system.exitProcess(if (bad == 0) 0 else 1)
}
