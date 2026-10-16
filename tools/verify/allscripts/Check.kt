// The Kotlin half of tools/verify/allscripts: the REAL IsoCodes (and the
// generated UnicodeTables) turn the detector's code into the language a voice is
// looked up by, as languageForDetectedRun (mix, multilingual) and the auto
// branch (auto, Google) do. Appended to a copy of those two files by run.sh.
//   prep  <langs.tsv>             -> script, iso2 (hint), iso3 (detect set) of each living script
//   check <langs.tsv> <probe.txt> -> one verdict per living script; exit 1 on any miss
fun autoCode(raw: String): String = if (System.getenv("EV_NEG") == "truncate" && raw.length > 2) raw.substring(0, 2) else raw
fun main(args: Array<String>) {
    val langs = java.io.File(args[1]).readLines().map { it.split('\t') }.filter { it.size >= 3 }
    val living = langs.filter { it[1].isNotEmpty() && (it[2] == "RECOMMENDED" || it[2] == "LIMITED_USE") }.associate { it[0] to it[1] }
    if (args[0] == "prep") {
        for ((script, lang) in living) { val iso3 = iso3OfCode(lang); println(script + "\t" + (IsoCodes.toIso2(iso3) ?: lang) + "\t" + iso3) }
        return
    }
    var miss = 0; var ok = 0
    for (line in java.io.File(args[2]).readLines()) {
        val f = line.split('\t')
        val script = f[0]; val want = living[script] ?: continue
        fun first(col: String) = col.substringAfter('=').split(',')[0].substringBefore('/')
        fun resolved(code: String) = IsoCodes.toIso3(code)?.let { iso3OfCode(it) } ?: "-"
        val expected = iso3OfCode(IsoCodes.toIso3(want) ?: want)
        val mixOwner = first(f[2]); val mixOwn = first(f[5])
        val autoOwn = f[6].substringAfter("auto=").let { if (" -> " in it) it.substringAfter(" -> ").substringBefore('/') else it }
        val dual = f[4].substringAfter("dual=")
        val problems = ArrayList<String>()
        if (resolved(mixOwn) != expected) problems.add("mix " + mixOwn + "->" + resolved(mixOwn))
        if (resolved(autoCode(autoOwn)) != expected) problems.add("auto " + autoOwn + "->" + resolved(autoCode(autoOwn)))
        if (dual.any { it != '2' }) problems.add("dual " + dual)
        // a script that names its language says so whatever is enabled; only the
        // scripts CLD2 shares out among several languages answer by the hints
        if (script !in setOf("Arabic", "Bengali", "Cyrillic", "Devanagari", "Han", "Hiragana", "Katakana", "Bopomofo")
            && resolved(mixOwner) != expected) problems.add("owner-set mix " + mixOwner)
        if (problems.isEmpty()) ok++ else { miss++; println("MISS  %-24s want %-4s %s".format(script, expected, problems.joinToString("; "))) }
    }
    println("living scripts: $ok right, $miss wrong")
    if (miss > 0) kotlin.system.exitProcess(1)
}
