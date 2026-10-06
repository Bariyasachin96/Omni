
// ---- hand-written driver; everything above is lifted verbatim ----
fun main() {
    val rnd = java.util.Random(4)
    val pkgs = listOf("com.google.android.tts", "com.eloq", "com.voc", "")
    val tags = listOf("en_US", "en_IN", "en", "hi_IN", "hi", "gu_IN", "mr", "zh_CN", "zh_TW", "cmn", "yue_HK", "sr_RS_#Latn",
        "de_DE_POSIX", "", "xx", "en__var", "a_b_c_d", "iw_IL", "fil_PH", "zz_ZZZZ")
    fun entry(): String = when (rnd.nextInt(12)) {
        0 -> pkgs[rnd.nextInt(pkgs.size)]                                              // no '#'
        1 -> pkgs[rnd.nextInt(pkgs.size)] + "#" + tags[rnd.nextInt(tags.size)] + "#x"  // three parts
        2 -> "a#b#c#d"
        else -> pkgs[rnd.nextInt(pkgs.size)] + "#" + tags[rnd.nextInt(tags.size)]
    }
    val wants = tags.map { t -> val p = t.split("_"); localeOf(p[0], p.getOrElse(1) { "" }, p.getOrElse(2) { "" }) } + listOf(localeOf("eng"), localeOf("hin", "IND"))
    var cases = 0; var bad = 0
    // 2026-10-06: the cached parser answers what the old one did -- the same
    // tags again and again (hits), then more distinct ones than the cache holds.
    run {
        val o = OldS(ArrayList()); val w = NewS(ArrayList())
        val many = tags + listOf<String?>(null) + (0 until 400).map { "q" + it + "_" + (if (it % 3 == 0) "" else "R" + it) + (if (it % 7 == 0) "_v" else "") }
        repeat(200000) {
            val t = many[rnd.nextInt(many.size)]
            cases++
            if (o.parse(t) != w.parse(t)) { bad++; if (bad < 5) println("DIFF parse $t") }
        }
    }
    repeat(20000) {
        val list = ArrayList<String>(); repeat(rnd.nextInt(30)) { list.add(entry()) }
        val oldS = OldS(list); val newS = NewS(list)
        repeat(5) { step ->
            if (step > 0) when (rnd.nextInt(3)) {
                0 -> { val e = entry(); newS.voiceList.add(e); list.add(e) }   // loadVoiceList appends in place
                1 -> { val fresh = ArrayList(list); fresh.add(0, entry()); newS.voiceList = fresh; list.clear(); list.addAll(fresh) } // persistVoiceList assigns a new list
                else -> {}
            }
            for (w in wants) {
                cases++
                val a = oldS.find(w); val b = newS.find(w)
                if (a != b) { bad++; if (bad < 5) println("DIFF find $w in $list: '$a' vs '$b'") }
            }
            val es = newS.entries()
            cases++
            if (es.map { it.raw } != list || es.any { it.parts != it.raw.split("#") }) { bad++; if (bad < 5) println("DIFF entries") }
            for (e in es) if (e.parts.size >= 2) {
                cases++
                if (e.storedLocale != newS.parse(e.parts[1])) { bad++; if (bad < 5) println("DIFF locale ${e.raw}") }
            }
        }
    }
    println("cases $cases  differ $bad")
    println(if (bad == 0) "IDENTICAL" else "FAILED")
    if (bad != 0) kotlin.system.exitProcess(1)
}
