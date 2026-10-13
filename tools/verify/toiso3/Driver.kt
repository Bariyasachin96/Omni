
// ---- driver: the cached iso3OfCode must answer exactly what the uncached one
// does, and what the function it replaced (Old = REF's SharedPrefsManager) did
fun main() {
    val inputs = ArrayList<String>()
    for (l in java.util.Locale.getISOLanguages()) {
        inputs.add(l); inputs.add(l.uppercase())
        val three = try { localeOf(l).isO3Language } catch (_: Exception) { "" }
        if (three.isNotEmpty()) { inputs.add(three); inputs.add(three.uppercase()); inputs.add(three.replaceFirstChar { it.uppercase() }) }
    }
    for (a in 'a'..'z') for (b in 'a'..'z') inputs.add("$a$b")
    inputs.addAll(listOf("", "e", "eng", "hin", "zho", "cmn", "lzh", "gan", "hak", "zh", "iw", "in", "ji", "he", "id", "yi", "en-US", "x1", "🙂a", "ENG", "un", "jw"))
    val rnd = java.util.Random(3)
    repeat(300) { inputs.add(String(CharArray(rnd.nextInt(4)) { ('a' + rnd.nextInt(26)) })) }
    var bad = 0; var calls = 0
    val threads = (0 until 4).map { t -> Thread {
        val r = java.util.Random(t.toLong())
        repeat(200000) {
            val x = inputs[r.nextInt(inputs.size)]
            val got = iso3OfCode(x); val want = iso3OfCodeUncached(x)
            val old = Old.toIso3Cached(x); val oldUncached = Old.toIso3Uncached(x)
            synchronized(inputs) {
                calls++
                if (got != want || got != old || got != oldUncached) { bad++; if (bad < 5) println("DIFF '$x': $got vs $want / old $old $oldUncached") }
            }
        }
    } }
    threads.forEach { it.start() }; threads.forEach { it.join() }
    println("$calls calls over ${inputs.toSet().size} codes, 4 threads, cached vs uncached vs the old class: " + if (bad == 0) "IDENTICAL" else "$bad DIFFER")
    // localeOf's two methods: Locale.of (Android 16+) and the constructor below
    // it, over every language above with every country and variant shape the
    // app builds Locales from (engine voice names, stored tags, ISO lookups).
    val countries = listOf("", "US", "IN", "us", "USA", "IND", "GB", "419", "x", "ZZZ", "001")
    val variants = listOf("", "*Default", "POSIX", "JP", "TH", "NY", "default", "x-none", "f1")
    fun shape(l: java.util.Locale): String =
        l.toString() + "|" + l.toLanguageTag() + "|" + (try { l.isO3Language } catch (e: Exception) { "!" }) +
            "|" + (try { l.isO3Country } catch (e: Exception) { "!" }) + "|" + l.hashCode()
    var lcases = 0; var lbad = 0
    for (l in inputs.toSet() + listOf("x-none", "zxx", "", "ja", "th", "no", "iw", "in", "ji")) for (c in countries) for (v in variants) {
        Sdk.INT = 36; val a = try { localeOf(l, c, v) } catch (e: Exception) { null }
        Sdk.INT = 35; val b = try { localeOf(l, c, v) } catch (e: Exception) { null }
        lcases++
        if ((a == null) != (b == null) || (a != null && b != null && (a != b || shape(a) != shape(b)))) {
            lbad++; if (lbad < 5) println("DIFF localeOf('$l','$c','$v'): ${a?.let { shape(it) }} vs ${b?.let { shape(it) }}")
        }
    }
    Sdk.INT = 36
    println("$lcases localeOf inputs, Locale.of (Android 16+) vs the constructor: " + if (lbad == 0) "IDENTICAL" else "$lbad DIFFER")
    kotlin.system.exitProcess(if (bad == 0 && lbad == 0) 0 else 1)
}
