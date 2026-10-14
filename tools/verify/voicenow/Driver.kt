// The current voice's locale read from the client's params, checked against the
// old voice-set read. The lifted bodies below are VERBATIM from the last commit
// (SvcOld) and the working tree (SvcNew). The fake TextToSpeech follows AOSP's
// TextToSpeech.java line for line where it matters:
//   setLanguage(loc)  isLanguageAvailable, getDefaultVoiceNameFor, loadVoice,
//                     then getVoice(service, name) = a getVoices() MARSHAL, then
//                     params = that voice's ISO3 language / ISO3 country / variant
//   setVoice(v)       loadVoice(name); on SUCCESS params from v's locale
//   getVoice()        getVoices() MARSHAL, the voice the params name, or null
//   getLanguage()     NO service call: Locale(params...), null with no connection
// Scenarios: several engines, Hindi/Gujarati/English/... on one engine, *Default
// and named variants, dedicated on/off, missing data, an engine dying, the
// engine's default voice changing, and a voice being uninstalled mid-run.
import java.util.Locale

object EasyVoiceLogger {
    const val TAG = "t"
    @Volatile var loggingOn = false
    inline fun debug(tag: String, msg: () -> String) { if (loggingOn) msg() }
    fun error(tag: String, msg: String) {}
    fun isLoggingEnabled() = loggingOn
}
object FakeLog { fun w(a: String, b: String, c: Throwable) {} }
fun localeOf(l: String, c: String = "", v: String = ""): Locale = Locale(l, c, v)

class Voice(val name: String, val locale: Locale)

class FakeEngine(val pkg: String) {
    val voices = ArrayList<Voice>()
    val missingData = HashSet<String>()
    var preferLast = false          // which voice is the engine's default for a locale
    var dead = false
    var boundTo: String? = null
    var calls = 0; var marshals = 0
    fun call() { calls++ }
    fun iso3(l: Locale) = try { l.isO3Language } catch (_: Exception) { "" }
    fun iso3c(l: Locale) = try { l.isO3Country } catch (_: Exception) { "" }
    fun available(loc: Locale): Int {
        val lang = iso3(loc)
        if (lang in missingData) return TextToSpeech.LANG_MISSING_DATA
        val same = voices.filter { iso3(it.locale) == lang }
        if (same.isEmpty()) return TextToSpeech.LANG_NOT_SUPPORTED
        val c = iso3c(loc)
        return if (c.isNotEmpty() && same.any { iso3c(it.locale) == c }) 1 else 0
    }
    fun defaultName(loc: Locale): String {
        val same = voices.filter { iso3(it.locale) == iso3(loc) }
        val c = iso3c(loc)
        val pool = same.filter { iso3c(it.locale) == c }.ifEmpty { same }
        if (pool.isEmpty()) return ""
        return (if (preferLast) pool.last() else pool.first()).name
    }
}

class TextToSpeech(val engine: FakeEngine) {
    companion object {
        const val SUCCESS = 0; const val ERROR = -1
        const val LANG_AVAILABLE = 0; const val LANG_MISSING_DATA = -1; const val LANG_NOT_SUPPORTED = -2
    }
    var pName = ""; var pLang = ""; var pCountry = ""; var pVariant = ""
    var shut = false
    private fun gone() = engine.dead || shut
    private fun setParams(v: Voice) {
        pName = v.name; pLang = engine.iso3(v.locale); pCountry = engine.iso3c(v.locale); pVariant = v.locale.variant
    }
    fun isLanguageAvailable(loc: Locale): Int { engine.call(); return if (gone()) LANG_NOT_SUPPORTED else engine.available(loc) }
    fun setLanguage(loc: Locale): Int {
        if (gone()) { engine.call(); return LANG_NOT_SUPPORTED }
        try { loc.isO3Language; loc.isO3Country } catch (_: Exception) { return LANG_NOT_SUPPORTED }
        engine.call(); val r = engine.available(loc)
        if (r >= LANG_AVAILABLE) {
            engine.call(); val name = engine.defaultName(loc)
            if (name.isEmpty()) return LANG_NOT_SUPPORTED
            engine.call()                                  // loadVoice
            engine.call(); engine.marshals++               // getVoice(service, name)
            val v = engine.voices.firstOrNull { it.name == name } ?: return LANG_NOT_SUPPORTED
            setParams(v)
        }
        return r
    }
    fun setVoice(v: Voice): Int {
        engine.call()
        if (gone()) return LANG_NOT_SUPPORTED
        if (engine.voices.none { it.name == v.name }) return ERROR
        setParams(v); return SUCCESS
    }
    val voices: MutableSet<Voice>? get() { engine.call(); engine.marshals++; return if (gone()) null else LinkedHashSet(engine.voices) }
    val voice: Voice? get() {
        if (shut) return null
        if (pName.isEmpty()) return null
        engine.call(); engine.marshals++
        return if (engine.dead) null else engine.voices.firstOrNull { it.name == pName }
    }
    val language: Locale? get() = if (gone()) null else Locale(pLang, pCountry, pVariant)
    fun stop(): Int = SUCCESS
    fun shutdown() { shut = true }
    override fun toString() = "tts:" + engine.pkg
}

object EngineFinder {
    fun boundEngineOf(c: TextToSpeech?, expected: String): String = c?.engine?.boundTo ?: expected
}
class LangEntry(val enginePkg: String, val localeTag: String, val variant: String)
object LangStore {
    var entries = ArrayList<LangEntry>()
    fun entryAt(i: Int): LangEntry? = entries.getOrNull(i)
    fun variantForEngineLocale(enginePkg: String, localeTag: String): String? {
        for (e in entries) if (e.enginePkg == enginePkg && e.localeTag == localeTag) return e.variant
        return null
    }
}

interface Arm {
    fun setUp(engines: List<FakeEngine>, mode: Int, voiceList: List<String>)
    fun load(pkg: String, l: Locale, v: String, d: Boolean)
    fun current(): Pair<String, TextToSpeech?>?
    fun summary(): List<String>
}
open class Base {
    var voiceList = ArrayList<String>()
    var modeInt = 4
    var lastEnginePkg = ""
    var engineIndex = -1
    var voiceLoadFailed = false
    val insideLoadVoice = ThreadLocal<Boolean>()
    var synthesisGeneration = 0
    val events = ArrayList<String>()
    fun restoreEngine(pkg: String, why: String) { events.add("restore " + pkg + " / " + why) }
    fun recoverEngineNotReady(raw: String) { events.add("recover " + raw) }
}
class SvcOld : Base(), Arm {
    val enginePool = ArrayList<EngineWrapper>()
    var parsedVoices: Pair<ArrayList<String>, List<VoiceEntry>>? = null
//OLD//
    override fun setUp(engines: List<FakeEngine>, mode: Int, voiceList: List<String>) {
        modeInt = mode; this.voiceList = ArrayList(voiceList)
        for (e in engines) { val w = EngineWrapper(e.pkg); w.state = 2; w.tts = TextToSpeech(e); w.listenerSet = true; enginePool.add(w) }
    }
    override fun load(pkg: String, l: Locale, v: String, d: Boolean) = loadVoice(pkg, l, v, d)
    override fun current(): Pair<String, TextToSpeech?>? { val w = enginePool.getOrNull(engineIndex) ?: return null; return if (w.state != 2) null else Pair(w.pkg, w.tts) }
    override fun summary() = enginePool.map { w -> "wrapper " + w.pkg + " state=" + w.state + " tts=" + (w.tts != null) +
        " locale=" + w.locale + " voice=" + w.voiceName + " localeSet=" + w.localeSet + " refused=" + w.refusedLocale } + events
}
class SvcNew : Base(), Arm {
    val enginePool = ArrayList<EngineWrapper>()
    var parsedVoices: Pair<ArrayList<String>, List<VoiceEntry>>? = null
//NEW//
    override fun setUp(engines: List<FakeEngine>, mode: Int, voiceList: List<String>) {
        modeInt = mode; this.voiceList = ArrayList(voiceList)
        for (e in engines) { val w = EngineWrapper(e.pkg); w.state = 2; w.tts = TextToSpeech(e); w.listenerSet = true; enginePool.add(w) }
    }
    override fun load(pkg: String, l: Locale, v: String, d: Boolean) = loadVoice(pkg, l, v, d)
    override fun current(): Pair<String, TextToSpeech?>? { val w = enginePool.getOrNull(engineIndex) ?: return null; return if (w.state != 2) null else Pair(w.pkg, w.tts) }
    override fun summary() = enginePool.map { w -> "wrapper " + w.pkg + " state=" + w.state + " tts=" + (w.tts != null) +
        " locale=" + w.locale + " voice=" + w.voiceName + " localeSet=" + w.localeSet + " refused=" + w.refusedLocale } + events
}

class Target(val pkg: String, val locale: Locale, val variant: String, val dedicated: Boolean)
class Scenario(seed: Long, val uninstall: Boolean, val deaths: Boolean) {
    val rnd = java.util.Random(seed)
    val pkgs = listOf("com.a.tts", "com.b_x.tts", "com.c-y.tts")
    val locs = listOf(Locale("en", "US"), Locale("en", "GB"), Locale("hi", "IN"), Locale("gu", "IN"),
                      Locale("mr", "IN"), Locale("fr", "FR"), Locale("de", ""), Locale("hi", ""), Locale("en", "IN"))
    class Spec(val pkg: String, val voices: List<Pair<String, Locale>>, val missing: Set<String>, val boundTo: String?, val deathAt: Int)
    val nChunks = 2 + rnd.nextInt(12)
    val specs = pkgs.map { p ->
        val vs = ArrayList<Pair<String, Locale>>()
        for (l in locs.take(7)) if (rnd.nextInt(3) > 0) {
            val k = 1 + rnd.nextInt(3)
            for (j in 0 until k) vs.add(Pair(p.take(5) + "-" + l + "-" + j, if (j == 2 && l.country.isEmpty()) Locale(l.language, "IN") else l))
        }
        val miss = HashSet<String>(); if (rnd.nextInt(5) == 0) miss.add(locs[rnd.nextInt(locs.size)].isO3Language)
        val death = if (deaths && rnd.nextInt(6) == 0) rnd.nextInt(nChunks) else -1
        Spec(p, vs, miss, if (death >= 0 && rnd.nextInt(2) == 0) "com.sachinbaria.easyvoice" else null, death)
    }
    val mode = if (rnd.nextInt(8) == 0) 3 else 4
    val voiceListRaw = ArrayList<String>().apply {
        for (s in specs) for ((n, l) in s.voices) if (rnd.nextInt(4) > 0) add(s.pkg.replace("-", "").replace("_", "") + "#" + l + "#" + n)
    }
    val entries = ArrayList<LangEntry>().apply {
        for (i in 0 until 8) {
            val s = specs[rnd.nextInt(specs.size)]
            val l = locs[rnd.nextInt(locs.size)]
            val name = s.voices.filter { it.second == l }.map { it.first }
            val v = when (rnd.nextInt(5)) { 0, 1 -> "*Default"; 2 -> name.firstOrNull() ?: "*Default"; 3 -> "nope"; else -> name.lastOrNull() ?: "nope" }
            add(LangEntry(s.pkg.replace("-", "").replace("_", ""), l.toString(), v))
        }
    }
    val targets = (0 until nChunks).map {
        val s = specs[rnd.nextInt(if (rnd.nextInt(3) == 0) specs.size else 2)]
        val l = locs[rnd.nextInt(locs.size)]
        val name = s.voices.filter { it.second == l }.map { it.first }
        val v = when (rnd.nextInt(6)) { 0, 1, 2 -> "*Default"; 3 -> name.firstOrNull() ?: "x"; 4 -> ""; else -> "nope" }
        Target(if (rnd.nextInt(12) == 0) "" else s.pkg, l, v, rnd.nextInt(10) == 0)
    }
    // between chunks: the engine's default voice choice flips, or (uninstall
    // runs only) a voice is removed from an engine
    val flips = (0 until nChunks).map { if (rnd.nextInt(6) == 0) rnd.nextInt(specs.size) else -1 }
    val removals = (0 until nChunks).map { if (uninstall && rnd.nextInt(4) == 0) rnd.nextInt(specs.size) else -1 }
    val removalPick = (0 until nChunks).map { rnd.nextInt(1000) }
}

class Outcome(val lines: List<String>, val calls: Int, val marshals: Int)
fun run(sc: Scenario, arm: Arm): Outcome {
    val engines = sc.specs.map { s -> FakeEngine(s.pkg).apply {
        for ((n, l) in s.voices) voices.add(Voice(n, l)); missingData.addAll(s.missing) } }
    LangStore.entries = ArrayList(sc.entries)
    arm.setUp(engines, sc.mode, sc.voiceListRaw)
    val out = ArrayList<String>()
    for (i in 0 until sc.nChunks) {
        for ((k, s) in sc.specs.withIndex()) if (s.deathAt == i) { engines[k].dead = true; engines[k].boundTo = s.boundTo }
        if (sc.flips[i] >= 0) engines[sc.flips[i]].preferLast = !engines[sc.flips[i]].preferLast
        if (sc.removals[i] >= 0) {
            val e = engines[sc.removals[i]]
            if (e.voices.isNotEmpty()) e.voices.removeAt(sc.removalPick[i] % e.voices.size)
        }
        val t = sc.targets[i]
        val before = engines.map { it.calls }
        arm.load(t.pkg, t.locale, t.variant, t.dedicated)
        if (System.getenv("EV_TRACE") != null) out.add("  load $i ${t.pkg} ${t.locale} '${t.variant}' ded=${t.dedicated} dead=${engines.map{it.dead}} calls+=${engines.mapIndexed { k, e -> e.calls - before[k] }}")
        val c = arm.current()
        val tts = c?.second
        out.add(if (tts == null) "chunk $i not spoken" else
            "chunk $i on ${c.first} voice=${tts.pName} params=${tts.pLang}_${tts.pCountry}_${tts.pVariant}")
    }
    out.addAll(arm.summary())
    return Outcome(out, engines.sumOf { it.calls }, engines.sumOf { it.marshals })
}

fun alternation() {
    // The owner's case: Hindi and Gujarati on ONE engine, both *Default, read
    // one after the other -- each load is a language switch.
    for (arm in listOf<Arm>(SvcOld(), SvcNew())) {
        val e = FakeEngine("com.g.tts").apply {
            voices.add(Voice("hi-1", Locale("hi", "IN"))); voices.add(Voice("gu-1", Locale("gu", "IN"))); voices.add(Voice("en-1", Locale("en", "US"))) }
        LangStore.entries = arrayListOf(LangEntry("com.g.tts", "hi_IN", "*Default"), LangEntry("com.g.tts", "gu_IN", "*Default"))
        EasyVoiceLogger.loggingOn = false
        arm.setUp(listOf(e), 4, listOf("com.g.tts#hi_IN#hi-1", "com.g.tts#gu_IN#gu-1"))
        arm.load("com.g.tts", Locale("hi", "IN"), "*Default", false)
        val c0 = e.calls; val m0 = e.marshals
        for (i in 0 until 100) arm.load("com.g.tts", if (i % 2 == 0) Locale("gu", "IN") else Locale("hi", "IN"), "*Default", false)
        println((if (arm is SvcOld) "OLD" else "NEW") + " Hindi<->Gujarati, per switch: binder calls " + (e.calls - c0) / 100.0 + ", voice-set marshals " + (e.marshals - m0) / 100.0)
    }
}

fun main() {
    alternation()
    val n = (System.getenv("EV_CASES") ?: "200000").toInt()
    val s0 = (System.getenv("EV_SEED") ?: "0").toInt()
    var failed = false
    for ((label, uninstall, deaths) in listOf(Triple("engines alive, voices installed", false, false),
                                              Triple("an engine dies unnoticed mid-run", false, true),
                                              Triple("a voice is uninstalled mid-run", true, false))) {
        var cases = 0; var differ = 0; var first: String? = null
        var cOld = 0L; var cNew = 0L; var mOld = 0L; var mNew = 0L
        for (seed in s0 until s0 + n) {
            val sc = Scenario(seed.toLong(), uninstall, deaths)
            for (logging in listOf(false, true)) {
                EasyVoiceLogger.loggingOn = logging
                val a = run(sc, SvcOld()); val b = run(sc, SvcNew())
                cases++
                if (!logging) { cOld += a.calls; cNew += b.calls; mOld += a.marshals; mNew += b.marshals }
                else if (a.calls != b.calls || a.marshals != b.marshals) { differ++; if (first == null) first = "seed $seed logging on: calls ${a.calls}/${b.calls}" }
                if (a.lines != b.lines) {
                    differ++
                    if (first == null) first = "seed $seed logging=$logging\nOLD\n" + a.lines.joinToString("\n") + "\nNEW\n" + b.lines.joinToString("\n")
                }
            }
        }
        println("$label: cases $cases, differ $differ; logging off: binder calls $cOld -> $cNew, voice-set marshals $mOld -> $mNew")
        if (!uninstall && !deaths) { if (differ != 0) { failed = true; println(first) } }
        else if (System.getenv("EV_TRACE") != null && first != null) println(first)
    }
    if (failed) System.exit(1)
}
