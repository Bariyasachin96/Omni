import com.sachinbaria.easyvoice.UnicodeTables
// See run.sh: UnicodeTables.scriptOf (the real file) against the JDK's
// Character.UnicodeScript, grouped the same way (Han, kana, Hangul, Bopomofo
// as one; Common, Inherited, Unknown none). Two code points must agree on
// "same script or not" and on "no script"; a code point the JDK has not
// assigned is counted as newer.
fun group(s: Character.UnicodeScript): String? = when (s) {
    Character.UnicodeScript.COMMON, Character.UnicodeScript.INHERITED, Character.UnicodeScript.UNKNOWN -> null
    Character.UnicodeScript.HIRAGANA, Character.UnicodeScript.KATAKANA, Character.UnicodeScript.HANGUL,
    Character.UnicodeScript.BOPOMOFO -> "HAN"
    else -> s.name
}
fun main(args: Array<String>) {
    val neg = args.getOrNull(0) == "neg"
    val idToJdk = HashMap<Int, String>()
    var bad = 0; var newer = 0; var n = 0
    for (cp in 0 until 0x110000) {
        if (cp in 0xD800..0xDFFF) continue
        n++
        var ours = UnicodeTables.scriptOf(cp)
        if (neg && cp == 0x0A85) ours = UnicodeTables.scriptOf(0x0905)   // Gujarati A read as Devanagari
        val assigned = Character.getType(cp) != Character.UNASSIGNED.toInt()
        val jdk = try { group(Character.UnicodeScript.of(cp)) } catch (e: Exception) { null }
        if (!assigned) { if ((ours < 0) != (jdk == null)) newer++; continue }
        if (jdk == null) { if (ours >= 0) { if (bad < 5) println("script %X ours %d jdk none".format(cp, ours)); bad++ }; continue }
        if (ours < 0) { if (bad < 5) println("script %X ours none jdk %s".format(cp, jdk)); bad++; continue }
        val seen = idToJdk.putIfAbsent(ours, jdk)
        if (seen != null && seen != jdk) { if (bad < 5) println("script %X id %d is %s and %s".format(cp, ours, seen, jdk)); bad++ }
    }
    println("scriptOf: $n code points, ${idToJdk.size} scripts, $bad differ from the JDK ($newer newer than it)")
    if (bad > 0) { println("FAILED"); kotlin.system.exitProcess(1) }
}
