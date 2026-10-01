
// ---- hand-written driver; everything above is lifted verbatim ----
object Lists {
    val languages = ArrayList<LangEntry>()
    val listVersion = java.util.concurrent.atomic.AtomicLong()
    fun availableLanguagesFor(pkg: String?, iso3Only: Boolean) = OldLS.availableLanguagesFor(pkg, iso3Only)
}
object EngineFinder { var builtInEngine = "com.google.android.tts" }
object EasyVoiceLogger {
    const val TAG = "t"
    var on = false
    val lines = ArrayList<String>()
    var lambdasRun = 0
    fun isLoggingEnabled() = on
    fun debug(tag: String, msg: String) { if (on) lines.add(msg) }
    fun debug(tag: String, msg: () -> String) { if (on) { lambdasRun++; lines.add(msg()) } }
    fun error(tag: String, msg: String) {}
}
class Voice(val name: String, val locale: java.util.Locale, q: Int, l: Int, n: Boolean, f: Set<String>) {
    companion object { const val QUALITY_HIGH = 400; const val LATENCY_VERY_LOW = 100 }
}
fun localeOf(l: String, c: String = "", v: String = "") = java.util.Locale(l, c, v)
object IsoCodes {
    private val m = mapOf("eng" to "en", "hin" to "hi", "guj" to "gu", "mar" to "mr", "ara" to "ar", "zho" to "zh", "rus" to "ru")
    fun toIso2(t: String?): String? = m[t]
}
fun main() {
    val rnd = java.util.Random(7)
    val isos = listOf("eng", "hin", "guj", "mar", "ara", "zho", "rus", "xyz")
    val engines = listOf("", "disable", "DISABLE", "com.google.android.tts", "com.eloq", "com.voc")
    fun entry(): LangEntry {
        val e = LangEntry("n", isos[rnd.nextInt(isos.size)], 10 + rnd.nextInt(490), 10 + rnd.nextInt(90), 10 + rnd.nextInt(190),
            engines[rnd.nextInt(engines.size)], "l" + rnd.nextInt(5), "v" + rnd.nextInt(5))
        e.disabled = rnd.nextInt(3) == 0
        return e
    }
    var bad = 0; var cases = 0
    // #4 and #5: the one-walk answers against the old separate walks.
    repeat(20000) {
        val list = ArrayList<LangEntry>(); repeat(rnd.nextInt(12)) { list.add(entry()) }
        NewLS.replaceAll(list)
        for (lang in isos + listOf("")) for (mode in listOf(0, 2, 3, 4, 5)) {
            cases++
            val t = NewLS.voiceTargetFor(lang, mode)
            val o = arrayOf(OldLS.localeFor(lang, mode), OldLS.engineFor(lang, mode), OldLS.variantFor(lang, mode))
            if (!t.contentEquals(o)) { bad++; if (bad < 5) println("DIFF target $lang/$mode: ${t.toList()} vs ${o.toList()}") }
            val p = NewLS.voiceParamsFor(lang)
            val q = intArrayOf(OldLS.speedFor(lang), OldLS.pitchFor(lang), OldLS.volumeFor(lang))
            if (!p.contentEquals(q)) { bad++; if (bad < 5) println("DIFF params $lang") }
            // #5: engineFor answers and logs exactly what the old one did, with
            // logging on and off.
            for (on in listOf(false, true)) {
                EasyVoiceLogger.on = on
                EasyVoiceLogger.lines.clear(); val oe = OldLS.engineFor(lang, mode); val ol = ArrayList(EasyVoiceLogger.lines)
                EasyVoiceLogger.lines.clear(); val ne = NewLS.engineFor(lang, mode); val nl = ArrayList(EasyVoiceLogger.lines)
                if (oe != ne || ol != nl) { bad++; if (bad < 5) println("DIFF engineFor $lang/$mode log=$on: $oe/$ne $ol vs $nl") }
            }
            EasyVoiceLogger.on = false
            val a = NewLS.isAvailable(lang); val b = OldLS.availableLanguagesFor(null, true).contains(lang)
            if (a != b) { bad++; if (bad < 5) println("DIFF available $lang") }
        }
        val none: String? = null
        if (NewLS.isAvailable(none) != OldLS.availableLanguagesFor(null, true).contains(none)) bad++
    }
    // #3: after every utterance the core must hold exactly the sets the list
    // gives NOW, whatever changed in between (setters, a new list, a speed
    // change that must NOT cost a rebuild, a push that throws).
    fun truthOk() = Lists.languages.filter { !it.disabled }.map { it.iso3 }.toHashSet()
    fun truthEn() = Lists.languages.filter { !(it.disabled || it.enginePkg.isEmpty() || it.enginePkg.equals("disable", true)) }
        .mapNotNull { IsoCodes.toIso2(it.iso3) }.toHashSet()
    NewLS.replaceAll(ArrayList<LangEntry>().also { l -> repeat(6) { l.add(entry()) } })
    Svc.pushLanguageSets()
    var utterances = 0; var builds = 0
    repeat(200000) {
        when (rnd.nextInt(20)) {
            0 -> { val l = ArrayList<LangEntry>(); repeat(rnd.nextInt(10)) { l.add(entry()) }; NewLS.replaceAll(l) }
            1 -> Lists.languages.randomOrNull()?.let { it.disabled = !it.disabled }
            2 -> Lists.languages.randomOrNull()?.let { it.enginePkg = engines[rnd.nextInt(engines.size)] }
            3 -> Lists.languages.randomOrNull()?.let { it.iso3 = isos[rnd.nextInt(isos.size)] }
            4 -> Lists.languages.randomOrNull()?.let { it.speed = 50 }
            5 -> Svc.failNext = true
            6 -> Svc.pushLanguageSets()
        }
        val before = Lists.listVersion.get()
        val sendsBefore = Svc.sends
        try { Svc.pushDetectSetsOnly() } catch (_: Throwable) {}
        utterances++
        if (Svc.sends != sendsBefore || Svc.pushedListVersion != before) builds++
        cases++
        // #8: the cached voice list names exactly what a fresh build names.
        val names = Svc.onGetVoices().map { it.name }
        if (names != OldLS.availableLanguagesFor(null, true)) { bad++; if (bad < 10) println("DIFF voices after step $it") }
        if (Svc.failNext) return@repeat
        if (Svc.pushedListVersion == -1L) return@repeat   // a push threw: retried next utterance
        if (Svc.coreOk != truthOk() || Svc.coreEnabled != truthEn()) { bad++; if (bad < 10) println("DIFF sets after step $it") }
    }
    println("$cases cases ($utterances utterances, the sets built on $builds of them): " + if (bad == 0) "IDENTICAL" else "$bad DIFFER")
    kotlin.system.exitProcess(if (bad == 0) 0 else 1)
}
