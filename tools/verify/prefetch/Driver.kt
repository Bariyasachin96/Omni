// Same-engine prefetch, checked before it is written.
//
// The class body below the fakes is lifted VERBATIM from EasyVoiceTtsService.kt:
// loadVoice and every function it reaches, including the failure handlers that
// can stop or shut a client down. Each scenario is run twice:
//
//   OLD  load chunk i+1 AFTER chunk i finished, except when i+1 is on another
//        engine (that prefetch already ships)
//   NEW  load chunk i+1 WHILE chunk i is still being spoken, whatever engine
//
// and the two must speak every chunk with the same engine, language and voice,
// cut no chunk short, and leave every wrapper, every restore and every
// recover request exactly the same.
//
// The fake engine follows AOSP: setLanguage / setVoice change only the
// client's own params (the engine queues its load at QUEUE_ADD, behind what is
// playing), getVoices / getVoice / isLanguageAvailable are plain binder reads,
// and a dead engine answers runAction's error results (-2, null, -1). Only
// stop() and shutdown() reach the utterance that is playing.
import java.util.Locale

object EasyVoiceLogger {
    const val TAG = "t"
    var on = false
    inline fun debug(tag: String, msg: () -> String) { if (on) msg() }
    fun error(tag: String, msg: String) {}
    fun isLoggingEnabled() = on
}
object FakeLog { fun w(a: String, b: String, c: Throwable) {} }
fun localeOf(l: String, c: String = "", v: String = ""): Locale = Locale(l, c, v)

class Voice(val name: String, val locale: Locale)

val EAGER = System.getenv("EV_NEG") == "eagerengine"

class FakeEngine(val pkg: String) {
    val voices = LinkedHashSet<Voice>()
    val missingData = HashSet<String>()
    var dead = false
    var boundTo: String? = null
    var playing: Int = -1          // chunk on the speaker right now
    val cut = HashSet<Int>()       // chunks stopped before their end
    var calls = 0                  // binder round trips
    var callsWhilePlaying = 0
    fun call() { calls++; if (playing >= 0) callsWhilePlaying++ }
    fun available(loc: Locale): Int {
        val lang = try { loc.isO3Language } catch (_: Exception) { "" }
        if (lang in missingData) return TextToSpeech.LANG_MISSING_DATA
        val same = voices.filter { try { it.locale.isO3Language == lang } catch (_: Exception) { false } }
        if (same.isEmpty()) return TextToSpeech.LANG_NOT_SUPPORTED
        val c = try { loc.isO3Country } catch (_: Exception) { "" }
        return if (c.isNotEmpty() && same.any { it.locale.isO3Country == c }) 1 else 0
    }
}

class TextToSpeech(val engine: FakeEngine) {
    companion object {
        const val SUCCESS = 0; const val ERROR = -1
        const val LANG_AVAILABLE = 0; const val LANG_MISSING_DATA = -1; const val LANG_NOT_SUPPORTED = -2
    }
    var paramLang: Locale? = null
    var paramVoice = ""
    var shut = false
    private fun eagerCut() { if (EAGER && engine.playing >= 0) engine.cut.add(engine.playing) }
    fun isLanguageAvailable(loc: Locale): Int { engine.call(); return if (engine.dead || shut) LANG_NOT_SUPPORTED else engine.available(loc) }
    fun setLanguage(loc: Locale): Int {
        engine.call()
        if (engine.dead || shut) return LANG_NOT_SUPPORTED
        val r = engine.available(loc)
        if (r >= LANG_AVAILABLE) {
            val lang = loc.isO3Language
            val v = engine.voices.first { it.locale.isO3Language == lang }
            paramLang = loc; paramVoice = v.name
            eagerCut()
        }
        return r
    }
    val voices: MutableSet<Voice>? get() { engine.call(); return if (engine.dead || shut) null else LinkedHashSet(engine.voices) }
    val voice: Voice? get() { engine.call(); return if (engine.dead || shut) null else engine.voices.firstOrNull { it.name == paramVoice } }
    fun setVoice(v: Voice): Int {
        engine.call()
        if (engine.dead || shut || engine.voices.none { it.name == v.name }) return ERROR
        paramVoice = v.name; paramLang = v.locale
        eagerCut()
        return SUCCESS
    }
    fun stop(): Int { if (engine.playing >= 0) engine.cut.add(engine.playing); return SUCCESS }
    fun shutdown() { shut = true; if (engine.playing >= 0) engine.cut.add(engine.playing) }
    override fun toString() = "tts:" + engine.pkg
}

object EngineFinder {
    fun boundEngineOf(c: TextToSpeech?, expected: String): String = c?.engine?.boundTo ?: expected
}
class LangEntry(val enginePkg: String, val localeTag: String, val variant: String)
object LangStore {
    var entries = ArrayList<LangEntry>()
    fun entryAt(i: Int): LangEntry? = entries.getOrNull(i)
}

class Svc(val engines: List<FakeEngine>) {
    val enginePool = ArrayList<EngineWrapper>()
    var voiceList = ArrayList<String>()
    var parsedVoices: Pair<ArrayList<String>, List<VoiceEntry>>? = null
    var modeInt = 4
    var lastEnginePkg = ""
    var engineIndex = -1
    var voiceLoadFailed = false
    val insideLoadVoice = ThreadLocal<Boolean>()
    var synthesisGeneration = 0
    val events = ArrayList<String>()
    fun restoreEngine(pkg: String, why: String) { events.add("restore " + pkg + " / " + why) }
    fun recoverEngineNotReady(raw: String) { events.add("recover " + raw) }

//LIFTED//
}

class Target(val pkg: String, val locale: Locale, val variant: String, val dedicated: Boolean)
class Scenario(val seed: Long) {
    val rnd = java.util.Random(seed)
    val pkgs = listOf("com.a.tts", "com.b_x.tts", "com.c-y.tts")
    val locs = listOf(Locale("en", "US"), Locale("en", "GB"), Locale("hi", "IN"), Locale("gu", "IN"),
                      Locale("mr", "IN"), Locale("fr", "FR"), Locale("de", ""))
    class EngineSpec(val pkg: String, val voices: List<Pair<String, Locale>>, val missing: Set<String>,
                     val boundTo: String?, val deathAt: Int)
    val nChunks = 2 + rnd.nextInt(6)
    val specs = pkgs.map { p ->
        val vs = ArrayList<Pair<String, Locale>>()
        for (l in locs) if (rnd.nextInt(3) > 0) {
            val k = 1 + rnd.nextInt(2)
            for (j in 0 until k) vs.add(Pair(p.take(5) + "-" + l + "-" + j, l))
        }
        val miss = HashSet<String>(); if (rnd.nextInt(4) == 0) miss.add(locs[rnd.nextInt(locs.size)].isO3Language)
        val death = if (rnd.nextInt(5) == 0) rnd.nextInt(nChunks) else -1
        EngineSpec(p, vs, miss, if (death >= 0 && rnd.nextInt(2) == 0) "com.sachinbaria.easyvoice" else null, death)
    }
    val mode = if (rnd.nextInt(8) == 0) 3 else 4
    val voiceListRaw = ArrayList<String>().apply {
        for (s in specs) for ((n, l) in s.voices) if (rnd.nextInt(4) > 0) add(s.pkg.replace("-", "").replace("_", "") + "#" + l + "#" + n)
    }
    val entries = ArrayList<LangEntry>().apply {
        for (i in 0 until 6) {
            val s = specs[rnd.nextInt(specs.size)]
            val l = locs[rnd.nextInt(locs.size)]
            val name = s.voices.filter { it.second == l }.map { it.first }
            val v = when (rnd.nextInt(4)) { 0 -> "*Default"; 1 -> name.firstOrNull() ?: "*Default"; 2 -> "nope"; else -> name.lastOrNull() ?: "nope" }
            add(LangEntry(s.pkg.replace("-", "").replace("_", ""), l.toString(), v))
        }
    }
    val targets = (0 until nChunks).map {
        // Runs of the same engine are what the owner's text has (Hindi and
        // Gujarati on one engine), so keep the engine more often than not.
        val s = specs[rnd.nextInt(if (rnd.nextInt(3) == 0) specs.size else 2)]
        val l = locs[rnd.nextInt(locs.size)]
        val name = s.voices.filter { it.second == l }.map { it.first }
        val v = when (rnd.nextInt(5)) { 0, 1 -> "*Default"; 2 -> name.firstOrNull() ?: "x"; 3 -> ""; else -> "nope" }
        Target(if (rnd.nextInt(10) == 0) "" else s.pkg, l, v, rnd.nextInt(8) == 0)
    }
}

class Outcome(val lines: List<String>, val moved: Int)

fun run(sc: Scenario, newOrder: Boolean): Outcome {
    val engines = sc.specs.map { s -> FakeEngine(s.pkg).apply {
        for ((n, l) in s.voices) voices.add(Voice(n, l)); missingData.addAll(s.missing) } }
    val svc = Svc(engines)
    svc.modeInt = sc.mode
    svc.voiceList = ArrayList(sc.voiceListRaw)
    LangStore.entries = ArrayList(sc.entries)
    for (e in engines) { val w = svc.EngineWrapper(e.pkg); w.state = 2; w.tts = TextToSpeech(e); w.listenerSet = true; svc.enginePool.add(w) }
    val out = ArrayList<String>()
    fun load(i: Int, when_: String) {
        val t = sc.targets[i]
        svc.loadVoice(t.pkg, t.locale, t.variant, t.dedicated)
        out.add("load " + i + " idx=" + svc.engineIndex + " failed=" + svc.voiceLoadFailed + " last=" + svc.lastEnginePkg)
    }
    fun pkgOf(i: Int): String { val p = sc.targets[i].pkg; return if (p.isEmpty()) "" else p.replace("-", "").replace("_", "") }
    var speaking: FakeEngine? = null
    fun speak(i: Int) {
        val idx = svc.engineIndex
        val w = svc.enginePool.getOrNull(idx)
        val c = w?.tts
        if (w == null || c == null || w.state != 2) { out.add("chunk " + i + " not spoken"); speaking = null; return }
        out.add("chunk " + i + " on " + c.engine.pkg + " lang=" + c.paramLang + " voice=" + c.paramVoice)
        c.engine.playing = i; speaking = c.engine
    }
    fun midpoint(i: Int) { for ((k, s) in sc.specs.withIndex()) if (s.deathAt == i) { engines[k].dead = true; engines[k].boundTo = s.boundTo } }
    fun finish(i: Int) {
        val e = speaking ?: return
        val lost = e.dead || i in e.cut
        out.add("chunk " + i + (if (lost) " LOST" else " complete"))
        e.playing = -1; speaking = null
    }
    load(0, "first")
    speak(0)
    var moved = 0
    for (i in 0 until sc.nChunks) {
        midpoint(i)
        val hasNext = i + 1 < sc.nChunks
        // prefetchNextLanguage: never without a next engine; OLD only when that
        // engine is not the one speaking.
        var prefetched = false
        if (hasNext) {
            val next = pkgOf(i + 1)
            val speakingPkg = speaking?.pkg?.replace("-", "")?.replace("_", "")
            if (next.isNotEmpty() && (newOrder || next != speakingPkg)) {
                val before = engines.sumOf { it.callsWhilePlaying }
                load(i + 1, "prefetch"); prefetched = true
                moved += engines.sumOf { it.callsWhilePlaying } - before
            }
        }
        finish(i)
        if (!hasNext) break
        if (!prefetched) load(i + 1, "onDone")
        speak(i + 1)
    }
    for (w in svc.enginePool) out.add("wrapper " + w.pkg + " state=" + w.state + " tts=" + (w.tts != null) +
        " locale=" + w.locale + " voice=" + w.voiceName + " localeSet=" + w.localeSet + " refused=" + w.refusedLocale +
        " known=" + w.currentVoiceKnown + " cur=" + w.currentVoice?.name + " cache=" + (w.voicesCache?.size))
    out.addAll(svc.events)
    return Outcome(out, moved)
}

fun main() {
    // The order of loads is the same in both arms; only WHEN they happen
    // differs. So the per-load lines are compared as a sequence, and so are the
    // chunk lines. Interleaving between the two kinds is what moved.
    var differ = 0; var cases = 0; var lost = 0; var moved = 0
    var firstDiff: String? = null
    val n = (System.getenv("EV_CASES") ?: "200000").toInt()
    for (seed in 0 until n) {
        val sc = Scenario(seed.toLong())
        for (logging in listOf(false, true)) {
            EasyVoiceLogger.on = logging
            val a = run(sc, false); val b = run(sc, true)
            cases++
            moved += b.moved
            val ka = a.lines.filter { !it.startsWith("load") } to a.lines.filter { it.startsWith("load") }
            val kb = b.lines.filter { !it.startsWith("load") } to b.lines.filter { it.startsWith("load") }
            if (b.lines.any { it.endsWith(" LOST") }) lost++
            if (ka != kb) {
                differ++
                if (firstDiff == null) firstDiff = "seed " + seed + "\nOLD\n" + a.lines.joinToString("\n") + "\nNEW\n" + b.lines.joinToString("\n")
            }
        }
    }
    println("cases " + cases + ", differ " + differ + ", scenarios with a lost chunk " + lost +
            ", binder calls moved into playing time " + moved)
    if (firstDiff != null) println(firstDiff)
    if (differ != 0) System.exit(1)
}
