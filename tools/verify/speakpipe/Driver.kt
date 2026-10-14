// ---- the speak-path simulator (hand-written; the two services are lifted) ----
//
// OldSvc and NewSvc hold the REAL speak loop of two versions of
// EasyVoiceTtsService.kt (gen.py lifts it). Both run the same scenarios on one
// virtual clock against FakeEngine, which behaves like AOSP's
// TextToSpeechService: one queue per engine, an item's synthesis may run while
// the item before it is still playing (about 500 ms of audio is buffered),
// QUEUE_FLUSH and stop() end what plays and drop what waits, callbacks reach
// the client's CURRENT listener a moment later (binder), a dead engine drops
// everything and answers ERROR. Each step on a thread runs whole, in time order.
//
// Compared, old against new, per utterance: every chunk heard to the end
// (engine, text, rate, pitch, volume, language), the chunk an interrupt cut,
// how the utterance ended (endSynthesis / unlockSynthesis numbers, the
// callback's start/done, the parked thread released). Counted, not required to
// be equal: the silence between chunks (new must not be longer), audio of an
// interrupted utterance still playing after its stop (new must have none), and
// audio of the chunk queued ahead heard after the chunk in front failed.
import java.util.Locale
import java.util.PriorityQueue

// ---------------- framework fakes ----------------
class Bundle(other: Bundle? = null) {
    val m: HashMap<String, Any?> = if (other == null) HashMap() else HashMap(other.m)
    fun remove(k: String) { m.remove(k) }
    fun containsKey(k: String) = m.containsKey(k)
    fun getInt(k: String, d: Int) = (m[k] as? Int) ?: d
    fun putParcelable(k: String, v: Any?) { m[k] = v }
    fun putFloat(k: String, v: Float) { m[k] = v }
    fun getFloat(k: String, d: Float) = (m[k] as? Float) ?: d
    fun getString(k: String) = m[k] as? String
}
class AudioAttributes {
    class Builder {
        fun setUsage(u: Int) = this
        fun setContentType(c: Int) = this
        fun setLegacyStreamType(s: Int) = this
        fun build() = AudioAttributes()
    }
    companion object { const val USAGE_ASSISTANCE_ACCESSIBILITY = 11; const val CONTENT_TYPE_SPEECH = 1 }
}
class Voice(val name: String, val locale: Locale)
object FakeLog { fun w(t: String, m: String, e: Throwable?) {} }
class SynthesisRequest(val charSequenceText: CharSequence, val params: Bundle?) {
    val speechRate = 100; val pitch = 100; val language = ""; val country = ""; val variant = ""
}
class SynthesisCallback {
    var started = false; var finished = false; var starts = 0; var dones = 0
    val maxBufferSize = 4096
    fun start(r: Int, f: Int, c: Int): Int { if (started) return -1; started = true; starts++; return 0 }
    fun done(): Int { if (finished) return -1; finished = true; dones++; return 0 }
    fun hasStarted() = started
    fun hasFinished() = finished
    fun audioAvailable(b: ByteArray, o: Int, l: Int) = 0
}
abstract class UtteranceProgressListener {
    abstract fun onStart(id: String)
    abstract fun onDone(id: String)
    @Deprecated("Deprecated in Java") abstract fun onError(id: String)
    open fun onError(id: String, errorCode: Int) { @Suppress("DEPRECATION") onError(id) }
    open fun onStop(id: String, interrupted: Boolean) {}
}
class TextToSpeech(val engine: FakeEngine) {
    companion object {
        const val SUCCESS = 0; const val ERROR = -1; const val QUEUE_FLUSH = 0; const val QUEUE_ADD = 1
        const val LANG_AVAILABLE = 0; const val LANG_COUNTRY_AVAILABLE = 1; const val LANG_COUNTRY_VAR_AVAILABLE = 2
        const val LANG_MISSING_DATA = -1; const val LANG_NOT_SUPPORTED = -2
        const val ERROR_SYNTHESIS = -3; const val ERROR_OUTPUT = -5; const val ERROR_INVALID_REQUEST = -8
    }
    object Engine {
        const val KEY_PARAM_STREAM = "streamType"; const val KEY_PARAM_UTTERANCE_ID = "utteranceId"
        const val KEY_PARAM_VOLUME = "volume"; const val DEFAULT_STREAM = 3
    }
    @Volatile var listener: UtteranceProgressListener? = null
    var rate = 1f; var pitch = 1f; var lang = ""; val boundTo = engine.raw
    fun setSpeechRate(r: Float): Int { rate = r; return SUCCESS }
    fun setPitch(p: Float): Int { pitch = p; return SUCCESS }
    fun setOnUtteranceProgressListener(l: UtteranceProgressListener?): Int { listener = l; return SUCCESS }
    fun setAudioAttributes(a: AudioAttributes): Int = SUCCESS
    fun speak(text: CharSequence, mode: Int, params: Bundle?, id: String?): Int = engine.speak(this, text.toString(), mode, params, id)
    fun stop(): Int = engine.stop()
    fun shutdown() {}
    val voice: Voice? get() = null
    val language: java.util.Locale? get() = null
}
object EngineFinder { fun boundEngineOf(t: TextToSpeech, raw: String): String = t.boundTo }
object EasyVoiceLogger {
    const val TAG = "EasyVoice"
    var sink: ((String) -> Unit)? = null
    inline fun debug(tag: String, msg: () -> String) { val s = sink ?: return; s(msg()) }
    fun error(tag: String, msg: String) { sink?.invoke(msg) }
    // the NEW code takes its one-walk prefetch path only with logging off;
    // main() runs every other seed that way (the OLD code has no such branch)
    @JvmStatic var loggingOn = true
    fun isLoggingEnabled() = loggingOn
}
data class TextChunk(var text: String, var lang: String, var typeCode: Int = 0, var forcedEngine: String? = null, var forcedLocale: String? = null, var forcedVariant: String? = null) {
    // the real one memoises toIso3(lang); FakePrefs.toIso3 is the identity
    fun iso3(): String = lang
}
class FakePrefs { fun toIso3(s: String) = s }
// the code since 2026-10-08 calls IsoCodes.kt's iso3OfCode; the same identity
fun iso3OfCode(s: String) = s
object LangStore {
    lateinit var run: Run
    fun engineFor(lang: String, mode: Int): String = run.config[lang] ?: ""
    fun voiceParamsFor(lang: String): IntArray { val h = (lang.hashCode() and 0xff); return intArrayOf(60 + h % 90, 70 + h % 60, 50 + h % 50) }
}
fun localeOf(l: String, c: String = "", v: String = "") = Locale(l, c, v)

// ---------------- scenario ----------------
class ChunkSpec(val text: String, val lang: String, val type: Int, val dur: Double)
class UttSpec(val chunks: List<ChunkSpec>, val firstLoad: String, val firstParamLang: String) {
    val empty get() = chunks.isEmpty()
}
class Fault(val immediate: Boolean, val at: Double, val code: Int)
class Scenario(val seed: Int) {
    val r = java.util.Random(seed.toLong())
    val engines = listOf("com.a.tts", "com.b_x.tts", "com.c-y.tts")
    val latency = engines.associateWith { listOf(15.0, 60.0, 150.0, 400.0)[r.nextInt(4)] }
    val langs = listOf("eng", "hin", "guj", "mar")
    val mode = listOf(4, 4, 4, 5, 1, 2)[r.nextInt(6)]
    val fallback = r.nextBoolean()
    val config = HashMap<String, String>()
    val supports = HashMap<String, MutableSet<String>>()
    val faults = HashMap<String, Fault>()      // "text|engine"
    var death: Triple<String, String, Double>? = null   // engine, anchor text, offset after it starts
    var interrupt: Pair<String, Double>? = null          // anchor text of utterance 1, offset after it starts
    val utts = ArrayList<UttSpec>()
    val mainHop = listOf(0.3, 2.0, 15.0, 40.0)[r.nextInt(4)]
    val dStop = listOf(0.2, 1.0, 4.0, 9.0)[r.nextInt(4)]
    val u2delay = listOf(0.1, 0.5, 3.0, 12.0)[r.nextInt(4)]
    var textNo = 0
    fun chunk(lang: String, type: Int) = ChunkSpec("t" + (textNo++) + "_" + lang, lang, type, 80.0 + r.nextInt(1500))
    fun utt(n: Int): UttSpec {
        val list = ArrayList<ChunkSpec>()
        var prev = langs[r.nextInt(4)]
        for (i in 0 until n) {
            val lang = if (r.nextInt(3) == 0) langs[r.nextInt(4)] else prev
            prev = lang
            if (mode == 1) {
                // dual: types 1 (eng), 2 (dual language), 3/4/5 (specific), 0 neutral
                val type = listOf(1, 2, 1, 2, 3, 4)[r.nextInt(6)]
                val l = when (type) { 1 -> "eng"; 2 -> dualLang; 3 -> numberLang; else -> "eng" }   // type 0 is whitespace only: silent, so not generated
                list.add(chunk(l, type))
            } else list.add(chunk(if (r.nextInt(12) == 0) "xxx" else lang, 0))
        }
        val c0 = list[0]
        val firstLoad = if (mode == 1) when (c0.type) { 1 -> "eng"; 2 -> dualLang; 3 -> numberLang; 4, 5 -> "eng"; else -> "" } else c0.lang
        val firstParam = when (mode) { 1 -> "eng"; 2 -> list[list.size - 1].lang; else -> "" }
        return UttSpec(list, firstLoad, firstParam)
    }
    val dualLang = "hin"
    val numberLang = listOf("eng", "hin", "guj")[r.nextInt(3)]
    init {
        for (l in langs) config[l] = engines[r.nextInt(3)]
        if (r.nextInt(4) == 0) config["hin"] = config["guj"]!!      // the owner's Hindi + Gujarati on one engine
        for (e in engines) supports[e] = HashSet()
        for (l in langs) { supports[config[l]!!]!!.add(l); for (e in engines) if (r.nextInt(3) == 0) supports[e]!!.add(l) }
        utts.add(utt(1 + r.nextInt(6)))
        if (r.nextInt(3) == 0) {
            val c = utts[0].chunks[r.nextInt(utts[0].chunks.size)]
            interrupt = Pair(c.text, r.nextDouble() * (c.dur - 2.0))
            if (r.nextInt(3) == 0) { utts.add(UttSpec(emptyList(), "", "")); utts.add(utt(1 + r.nextInt(2))) }
            else utts.add(utt(1 + r.nextInt(3)))
        }
        val all = utts.flatMap { it.chunks }
        if (r.nextInt(3) == 0) for (k in 0 until 1 + r.nextInt(2)) {
            val c = all[r.nextInt(all.size)]
            val e = if (r.nextBoolean()) (config[c.lang] ?: engines[0]) else engines[r.nextInt(3)]
            val code = listOf(-3, -3, -5, -8, -1)[r.nextInt(5)]
            faults[c.text + "|" + e] = Fault(r.nextBoolean(), r.nextDouble() * c.dur, code)
        }
        if (r.nextInt(5) == 0) {
            val c = all[r.nextInt(all.size)]
            death = Triple(config[c.lang] ?: engines[0], c.text, r.nextDouble() * c.dur)
        }
    }
}

// ---------------- the clock ----------------
class Ev(val t: Double, val seq: Long, val run: () -> Unit)
class Play(val gen: Int, val utt: Int, val engine: String, val text: String, val rate: Float, val pitch: Float,
           val volume: Float, val lang: String, val start: Double, var end: Double) {
    var cut = false; var errored = false
    fun key() = "$engine|$text|$rate|$pitch|$volume|$lang"
}
class Run(val sc: Scenario, val negLabel: String) {
    var now = 0.0; var seq = 0L
    val q = PriorityQueue<Ev>(compareBy<Ev>({ it.t }, { it.seq }))
    fun at(t: Double, r: () -> Unit) { q.add(Ev(t, seq++, r)) }
    val config = HashMap(sc.config)
    val plays = ArrayList<Play>()
    val notes = ArrayList<String>()
    val engines = sc.engines.associateWith { FakeEngine(this, it, sc.latency[it]!!) }
    var curUtt = 0
    var genOfUtt = HashMap<Int, Int>()
    val uttEnd = HashMap<Int, Double>()
    val interruptAt = HashMap<Int, Double>()
    val callbacks = HashMap<Int, SynthesisCallback>()
    val attempts = ArrayList<Triple<Int, String, String>>()   // every chunk handed to an engine: utterance, text, language loaded
    var failure: String? = null
    var steps = 0
    fun loop() {
        while (q.isNotEmpty()) {
            val e = q.poll(); now = e.t; e.run()
            if (++steps > 200000) { failure = "runaway"; return }
        }
    }
}
class SerialQueue(val run: Run, val hop: Double) {
    var last = 0.0
    fun post(r: () -> Unit) { val t = maxOf(run.now, last) + hop; last = t; run.at(t, r) }
}
class SimHandler(val sq: SerialQueue) {
    fun post(r: Runnable): Boolean { sq.post { r.run() }; return true }
    fun postDelayed(r: Runnable, d: Long): Boolean = true
    fun removeCallbacks(r: Runnable) {}
    fun removeCallbacksAndMessages(x: Any?) {}
}
class SimExec(val owner: Any, val pkg: String) {
    lateinit var sq: SerialQueue
    fun execute(r: Runnable) { sq.post { r.run() } }
}

class Item(val client: TextToSpeech, val text: String, val id: String?, val rate: Float, val pitch: Float,
           val volume: Float, val lang: String)
class FakeEngine(val run: Run, val raw: String, val latency: Double) {
    var dead = false
    var token = 0
    var synthFree = 0.0
    var lastPlayEnd = 0.0
    var playing: Play? = null
    val binder = 1.0
    fun dispatch(client: TextToSpeech, f: (UtteranceProgressListener) -> Unit) {
        run.at(run.now + binder) { val l = client.listener; if (l != null) f(l) }
    }
    fun flush() {
        token++
        val p = playing
        if (p != null && p.end > run.now) { p.end = run.now; p.cut = true }
        playing = null
        synthFree = run.now; lastPlayEnd = run.now
    }
    fun stop(): Int { if (dead) return TextToSpeech.ERROR; flush(); return TextToSpeech.SUCCESS }
    fun speak(client: TextToSpeech, text: String, mode: Int, params: Bundle?, id: String?): Int {
        if (dead) return TextToSpeech.ERROR
        if (mode == TextToSpeech.QUEUE_FLUSH) flush()
        if (text.isEmpty()) return TextToSpeech.SUCCESS
        val spec = run.sc.utts.flatMap { it.chunks }.first { it.text == text }
        val item = Item(client, text, id, client.rate, client.pitch, params?.getFloat("volume", 0f) ?: 0f, client.lang)
        run.attempts.add(Triple(run.curUtt, text, client.lang))
        val tok = token
        val utt = run.curUtt; val gen = run.genOfUtt[utt] ?: -1
        val t0 = maxOf(run.now, synthFree)
        val fault = run.sc.faults[text + "|" + raw]
        if (fault != null && fault.immediate) {
            val errAt = t0 + latency / 2
            synthFree = errAt
            run.at(errAt) { if (tok == token && !dead) dispatch(client) { it.onError(id ?: "", fault.code) } }
            return TextToSpeech.SUCCESS
        }
        val first = t0 + latency
        val start = maxOf(first, lastPlayEnd)
        val full = spec.dur
        val end = if (fault != null) start + fault.at else start + full
        lastPlayEnd = end
        synthFree = maxOf(first, end - 500.0)
        run.at(start) {
            if (tok != token || dead) return@at
            val p = Play(gen, utt, raw, text, item.rate, item.pitch, item.volume, item.lang, start, end)
            if (fault != null) p.errored = true
            run.plays.add(p); playing = p
            dispatch(client) { it.onStart(id ?: "") }
        }
        run.at(end) {
            if (tok != token || dead) return@at
            if (playing?.end == end) playing = null
            if (fault != null) dispatch(client) { it.onError(id ?: "", fault.code) } else dispatch(client) { it.onDone(id ?: "") }
        }
        // the engine's death, anchored to this chunk starting on it
        val d = run.sc.death
        if (d != null && d.first == raw && d.second == text) run.at(start + d.third) {
            if (tok != token || dead) return@at
            if (playing?.text != text) return@at
            // not a chunk that only started because the one in front just failed
            // (the new code's queued chunk, stopped a moment later): the old code
            // never starts it there, so a death anchored to it has no old twin
            if (run.plays.any { it.errored && it.engine == raw && it.end <= start + 1e-6 && it.end >= start - 2.0 }) return@at
            dead = true; flush()
            run.svcDeath(raw)
        }
        // the interrupt of utterance 1, anchored to this chunk starting
        val i = run.sc.interrupt
        if (i != null && i.first == text && utt == 0) run.at(start + i.second) {
            if (tok != token || dead) return@at
            if (playing?.text != text) return@at
            if (run.interruptAt.containsKey(0)) return@at
            run.interruptAt[0] = run.now
            run.svcInterrupt()
        }
        return TextToSpeech.SUCCESS
    }
}

interface Svc {
    fun startUtt(index: Int, spec: UttSpec)
    fun stopNow()
    fun death(raw: String)
    fun tick()
}
lateinit var svcRef: Svc
fun Run.svcDeath(raw: String) { val sq = mainQ; sq.post { svcRef.death(raw) } }
fun Run.svcInterrupt() {
    svcRef.stopNow()
    svcRef.tick()
    // the next utterance arrives after the synthesis thread is free again
    val next = 1
    if (sc.utts.size > next) startAt(next, now + sc.u2delay)
}
lateinit var mainQ: SerialQueue
var traceOn = false
fun Run.startAt(index: Int, t: Double) {
    at(t) {
        svcRef.startUtt(index, sc.utts[index])
        svcRef.tick()
        // an empty utterance is followed by the next one
        if (sc.utts[index].empty && sc.utts.size > index + 1) startAt(index + 1, now + sc.u2delay)
    }
}

// ---- SVC TEMPLATE BEGIN ----
class XSvc(val run: Run) : Svc {
    val mainHandler = SimHandler(SerialQueue(run, run.sc.mainHop).also { mainQ = it })
    val speakHandler = SimHandler(SerialQueue(run, 0.3))
    val enginePool = ArrayList<EngineWrapper>()
    @Volatile var engineIndex = -1
    val mayAwaitEngine = ThreadLocal<Boolean>()
    val syncLock = Object()
    @Volatile var isStopped = false
    @Volatile var isFlushed = false
    @Volatile var synthesisGeneration = 0
    @Volatile var speakingPkg: String? = null
    @Volatile var retryOnEngineDeath: ((String) -> Boolean)? = null
    val chunkQueue = ArrayList<Pair<Int, TextChunk>>()
    @Volatile var chunkCounter = 0
    var utteranceId = ""
    var requestRate = 1f; var requestPitch = 1f; var requestVolume = 1f
    var requestParams: Bundle? = null
    val modeInt = run.sc.mode
    val dualLang = run.sc.dualLang
    val numberSpecificLang = run.sc.numberLang
    val puncSpecificLang = "eng"
    val emojiSpecificLang = "eng"
    val engineFallbackFlag = run.sc.fallback
    val forceAccessibilityFlag = false
    val stripAudioAttrFlag = false
    val accessibilitySpeech = AudioAttributes()
    val prefs = FakePrefs()
    val stopQ = SerialQueue(run, run.sc.dStop)
    var pendingTail: (() -> Unit)? = null
    var tailUtt = -1
    init {
        for (raw in run.sc.engines) {
            val w = EngineWrapper(raw)
            w.stopExec.sq = stopQ
            w.tts = TextToSpeech(run.engines[raw]!!); w.state = 2
            enginePool.add(w)
        }
    }
    // ---- the fakes of what the speak loop calls outside itself ----
    fun onLoadLanguage(lang: String?, c: String?, v: String?): Int {
        val l = lang ?: ""
        val eng = run.config[l] ?: return TextToSpeech.LANG_NOT_SUPPORTED
        var idx = -1
        for (i in 0 until enginePool.size) if (enginePool[i].rawPkg == eng && enginePool[i].state == 2) { idx = i; break }
        engineIndex = idx
        if (idx >= 0) {
            val w = enginePool[idx]
            val cl = w.tts
            if (cl == null || cl.engine.dead) clientIsDead(w, "load") else cl.lang = l
        }
        return TextToSpeech.LANG_AVAILABLE
    }
    // loadLanguage(needEngine = true), as the prefetch's logging-off path calls
    // it: null when the language has no engine, else the load
    fun loadLanguage(lang: String?, c: String?, v: String?, needEngine: Boolean, stale: (() -> Boolean)? = null): Int? =
        if (stale != null && stale()) null
        else if (needEngine && LangStore.engineFor(lang ?: "", modeInt).isEmpty()) null else onLoadLanguage(lang, c, v)
    // Backup TTS per language (2026-10-06): a scenario with fallback on has it
    // on for every language, which is what the old global switch meant.
    fun backupFor(lang: String): Boolean = engineFallbackFlag && lang.isNotEmpty()
    fun clientIsDead(w: EngineWrapper, why: String) { w.state = -1 }
    fun walkPending(raw: String) = false
    fun loadVoice(p: String, l: Locale, v: String, d: Boolean) {}
    fun parseVoiceNameAsLocale(s: String?): Locale? = null
    fun switchToAlternative(lang: String, excluded: Set<String>, setUp: Boolean, why: String): String? {
        if (!engineFallbackFlag) return null
        for (raw in run.sc.engines) {
            if (excluded.contains(normPkg(raw))) continue
            if (!run.sc.supports[raw]!!.contains(lang)) continue
            for (i in 0 until enginePool.size) {
                val w = enginePool[i]
                if (w.rawPkg != raw || w.state != 2 || w.tts?.engine?.dead != false) continue
                run.config[lang] = raw
                engineIndex = i
                w.tts!!.lang = lang
                return raw
            }
        }
        return null
    }
    // the ADPF report (2026-10-07) has no scheduler to report to here
    fun reportSynthesisWork(nanos: Long) {}
    //MEMBERS//
    fun utter(request: SynthesisRequest?, callback: SynthesisCallback?, plan: UttSpec) {
        //BODY//
        pendingTail = {
            speakingPkg = null
            if (retryOnEngineDeath === retryOnDeath) retryOnEngineDeath = null
            startAndFinish(callback)
        }
    }
    override fun startUtt(index: Int, spec: UttSpec) {
        run.curUtt = index
        run.genOfUtt[index] = synthesisGeneration + 1
        val cb = SynthesisCallback(); run.callbacks[index] = cb
        val params = Bundle(); params.putParcelable("utteranceId", "u$index")
        val text = if (spec.empty) "" else spec.chunks.joinToString(" ") { it.text }
        EasyVoiceLogger.sink = { s ->
            if (s.startsWith("endSynthesis #") || s.startsWith("unlockSynthesis")) run.notes.add("$index:$s")
            if (traceOn) println("    %.1f [%d] %s ei=%d".format(run.now, index, s.replace("\n", " "), engineIndex))
        }
        pendingTail = null
        tailUtt = index
        utter(SynthesisRequest(text, params), cb, spec)
        if (pendingTail == null) run.uttEnd[index] = run.now
    }
    override fun stopNow() { svcOnStop() }
    override fun death(raw: String) { onEngineProcessGone(raw) }
    override fun tick() {
        val t = pendingTail ?: return
        if (!isStopped && !isFlushed) return
        pendingTail = null
        t()
        run.uttEnd[tailUtt] = run.now
    }
}
// ---- SVC TEMPLATE END ----
//SERVICES//

fun runOne(sc: Scenario, newCode: Boolean): Run {
    val run = Run(sc, "")
    LangStore.run = run
    val svc: Svc = if (newCode) NewSvc(run) else OldSvc(run)
    svcRef = svc
    // every step of every thread may end the parked wait; check after each
    run.startAt(0, 0.0)
    while (run.q.isNotEmpty()) {
        val e = run.q.poll(); run.now = e.t; e.run(); svc.tick()
        if (++run.steps > 200000) { run.failure = "runaway"; break }
    }
    return run
}

// audio of utterance 1 still playing after the interrupt and its stop
fun leakOf(run: Run, sc: Scenario): Double {
    val t = run.interruptAt[0] ?: return 0.0
    val lim = t + sc.dStop + 3.0
    return run.plays.filter { it.utt == 0 && it.end > lim }.sumOf { it.end - lim }
}
fun summary(run: Run, utt: Int): String {
    // An interrupted utterance is compared up to its interrupt: what plays after
    // it is the stop's business, counted on its own below.
    val until = run.interruptAt[utt] ?: Double.MAX_VALUE
    val full = run.plays.filter { it.utt == utt && !it.cut && !it.errored && it.end <= until }.sortedBy { it.start }.map { it.key() }
    val notes = run.notes.filter { it.startsWith("$utt:") }
    val cb = run.callbacks[utt]
    val ended = run.uttEnd.containsKey(utt)
    return "full=$full notes=$notes cb=${cb?.starts}/${cb?.dones} ended=$ended"
}

fun main(args: Array<String>) {
    val n = args.getOrNull(0)?.toInt() ?: 100000
    if (args.getOrNull(1) == "dump") {
        val sc = Scenario(n)
        println("mode ${sc.mode} fb ${sc.fallback} lat ${sc.latency} cfg ${sc.config} faults ${sc.faults.mapValues { "${it.value.immediate}/${it.value.at.toInt()}/${it.value.code}" }} death ${sc.death} int ${sc.interrupt} mainHop ${sc.mainHop} dStop ${sc.dStop}")
        for (u in sc.utts) println("  utt " + u.chunks.map { it.text + "(" + it.type + "," + it.dur.toInt() + ")" } + " firstLoad=" + u.firstLoad)
        for (newCode in listOf(false, true)) {
            println(if (newCode) "NEW" else "OLD")
            traceOn = args.getOrNull(2) == "trace"
            val run = runOne(Scenario(n), newCode)
            traceOn = false
            for (p in run.plays) println("  u${p.utt} ${p.engine} ${p.text} %.1f-%.1f cut=${p.cut} err=${p.errored}".format(p.start, p.end))
            println("  notes ${run.notes} ends ${run.uttEnd} int ${run.interruptAt}")
        }
        return
    }
    var differ = 0; var cases = 0; var chunksHeard = 0
    var gapOld = 0.0; var gapNew = 0.0; var gapsCompared = 0; var newLonger = 0
    var leakOld = 0; var leakNew = 0; var leakOldMs = 0.0; var leakNewMs = 0.0
    var glitch = 0; var glitchMs = 0.0; var hangOld = 0; var hangNew = 0; var cutLaterNew = 0; var cutLaterOld = 0
    var wrongOld = 0; var wrongNew = 0; var fixedWrong = 0; var afterFix = 0
    var queuedUsed = 0; var oldLeakHarm = 0; var stopWindow = 0; var stopWindowMs = 0.0; var maxLonger = 0.0; var idleN = 0; var idleOld = 0.0; var idleNew = 0.0
    for (seed in 1..n) {
        val sc = Scenario(seed)
        val o = runOne(Scenario(seed), false)
        EasyVoiceLogger.loggingOn = seed % 2 == 0
        val w = runOne(Scenario(seed), true)
        EasyVoiceLogger.loggingOn = true
        cases++
        if (o.failure != null || w.failure != null) { differ++; if (differ <= 10) println("seed $seed runaway old=${o.failure} new=${w.failure}"); continue }
        var why: String? = null
        // The old code could let an interrupted chunk play on (the stop it
        // queued was skipped); its later onDone / onError then still reached its
        // own listener and could end the NEXT utterance early. Where that
        // happened the later utterances differ because the old code was wrong:
        // counted, not compared.
        val oldLeakedHere = sc.interrupt != null && o.interruptAt.containsKey(0) &&
            (leakOf(o, sc) > 0 || o.plays.any { it.utt == 0 && !it.cut && it.end > o.interruptAt[0]!! + 1e-6 && it.start < o.interruptAt[0]!! })
        // A chunk heard in a language it does not need is a wrong voice: the
        // engine spoke it with whatever its client had loaded last. Counted for
        // both versions; an utterance the old code got wrong and the new one
        // gets right is a fix, not a difference -- and a new wrong voice fails.
        val wantLang = HashMap<String, String>()
        for (c in sc.utts.flatMap { it.chunks }) if (c.lang != "xxx") wantLang[c.text] = c.lang
        // (cut or failed ones too: an attempt in the wrong language is the fault
        // whatever happened to it afterwards)
        // Counted at speak(), so one an engine refused at once counts too.
        fun wrongIn(run: Run, u: Int) = run.attempts.count { it.first == u && wantLang[it.second] != null && wantLang[it.second] != it.third }
        val differentUtts = HashSet<Int>()
        var firstFixed = -1
        for (u in sc.utts.indices) {
            val so = summary(o, u); val sn = summary(w, u)
            val wo = wrongIn(o, u); val wn = wrongIn(w, u)
            wrongOld += wo; wrongNew += wn
            if (wn > 0 && why == null) why = "new: a chunk heard in the wrong language in utterance $u"
            if (so != sn) differentUtts.add(u)
            if (wo > 0 && wn == 0 && firstFixed < 0) firstFixed = u
            if (so != sn && wo > 0 && wn == 0) { fixedWrong++; continue }
            // Once an utterance went differently because the old code spoke a
            // chunk in the wrong language, what follows it in that scenario (the
            // interrupt anchored to a chunk the new code never plays wrongly, a
            // death anchored likewise, the next utterance) follows from the fix.
            if (so != sn && firstFixed in 0 until u) { afterFix++; continue }
            if (!o.uttEnd.containsKey(u) && o.callbacks.containsKey(u)) hangOld++
            if (!w.uttEnd.containsKey(u) && w.callbacks.containsKey(u)) hangNew++
            if (so != sn) {
                if (u > 0 && oldLeakedHere) { oldLeakHarm++; continue }
                if (why == null) why = "utterance $u\n   old $so\n   new $sn"
            }
        }
        // the chunk the interrupt cut
        val ci = sc.interrupt
        if (ci != null && why == null && o.interruptAt.containsKey(0) && w.interruptAt.containsKey(0)) {
            val co = o.plays.filter { it.utt == 0 && it.cut && it.text == ci.first && it.end >= (o.interruptAt[0] ?: 0.0) }.size
            val cn = w.plays.filter { it.utt == 0 && it.cut && it.text == ci.first && it.end >= (w.interruptAt[0] ?: 0.0) }.size
            // where the old code let the interrupted chunk play on (counted as a
            // leak below), the new one cutting it is the fix, not a difference
            val oldLeaked = o.plays.any { it.utt == 0 && it.text == ci.first && !it.cut && (o.interruptAt[0] ?: Double.MAX_VALUE) < it.end }
            if (co != cn && !(oldLeaked && cn == 1) && firstFixed < 0) why = "interrupt cut differs: old $co new $cn"
        }
        chunksHeard += w.plays.count { !it.cut && !it.errored }
        // silence between the chunks of one utterance, heard to the end in both
        for (u in sc.utts.indices) {
            fun gaps(run: Run): List<Double> {
                val p = run.plays.filter { it.utt == u && !it.cut && !it.errored }.sortedBy { it.start }
                return (1 until p.size).map { p[it].start - p[it - 1].end }
            }
            val go = gaps(o); val gn = gaps(w)
            if (u > 0 && oldLeakedHere) continue
            if (u in differentUtts) continue
            if (go.size == gn.size) for (k in go.indices) {
                gapsCompared++; gapOld += go[k]; gapNew += gn[k]
                if (sc.mainHop < 1.0) { idleN++; idleOld += go[k]; idleNew += gn[k] }
                if (gn[k] > go[k] + 1e-6) maxLonger = maxOf(maxLonger, gn[k] - go[k])
                if (gn[k] > go[k] + 1.0) { newLonger++; if (System.getenv("EV_SHOW") == "longer") println("longer seed $seed utt $u gap $k old %.1f new %.1f".format(go[k], gn[k])) }
                if (gn[k] < go[k] - 1e-6) queuedUsed++
            }
        }
        // audio of utterance 1 still playing after the interrupt and its stop
        val lo = leakOf(o, sc); val ln = leakOf(w, sc)
        if (lo > 0) { leakOld++; leakOldMs += lo }
        if (ln > 0) { leakNew++; leakNewMs += ln; if (why == null) why = "new: interrupted audio still playing ${ln} ms" }
        // Every cut the new code makes that the old one did not must be the
        // chunk queued ahead, started the moment the chunk in front of it
        // failed and stopped as soon as that error arrived. Anything else is a
        // stop cutting audio it should not have: a difference.
        fun cuts(run: Run) = run.plays.filter { it.cut }.groupingBy { "${it.utt}|${it.engine}|${it.text}" }.eachCount()
        val co0 = cuts(o); val cn0 = cuts(w)
        val seen = HashMap<String, Int>()
        for (p in w.plays.filter { it.cut }.sortedBy { it.start }) {
            val k = "${p.utt}|${p.engine}|${p.text}"
            val n = (seen[k] ?: 0) + 1; seen[k] = n
            if (n <= (co0[k] ?: 0)) continue
            if (ci != null && p.utt == 0 && p.text == ci.first && w.interruptAt.containsKey(0) && p.end >= w.interruptAt[0]!!) continue
            // started as (or just after) the chunk in front failed, and stopped by
            // that error's handling within a binder hop
            val afterFailure = w.plays.any { it.errored && it.engine == p.engine && p.start >= it.end - 1e-6 && p.start <= it.end + 2.0 }
            // or started in the moment between an interrupt and its stop landing
            // -- the window in which the old code went on playing the chunk in front
            val inStopWindow = p.utt == 0 && w.interruptAt.containsKey(0) && p.end <= w.interruptAt[0]!! + sc.dStop + 3.0 && p.start >= w.interruptAt[0]!!
            if (afterFailure) { glitch++; glitchMs += p.end - p.start }
            else if (inStopWindow) { stopWindow++; stopWindowMs += p.end - p.start }
            else if (p.utt > 0 && oldLeakedHere) oldLeakHarm++
            else if (firstFixed in 0..p.utt) afterFix++
            else { cutLaterNew++; if (why == null) why = "new cut ${p.text} on ${p.engine} at %.1f".format(p.end) }
        }
        for ((k, v) in co0) if (v > (cn0[k] ?: 0)) cutLaterOld += v - (cn0[k] ?: 0)
        if (why != null) { differ++; if (differ <= 12) println("DIFF seed $seed (mode ${sc.mode} fallback ${sc.fallback}): $why") }
    }
    println("$cases scenarios (old vs new, AOSP-shaped engines): $differ differ")
    println("  chunks heard to the end: $chunksHeard; hangs old $hangOld new $hangNew")
    println("  chunks handed to an engine in a language they do not need: old $wrongOld, new $wrongNew; utterances old got wrong and new gets right: $fixedWrong (what followed from them in the same scenario: $afterFix)")
    println("  silence between chunks: old %.0f ms, new %.0f ms over %d boundaries (%d shorter, %d longer by over 1 ms; the most any was longer: %.1f ms)".format(gapOld, gapNew, gapsCompared, queuedUsed, newLonger, maxLonger))
    println("    of them with the main thread idle (no settings screen busy): old %.0f ms, new %.0f ms over %d boundaries".format(idleOld, idleNew, idleN))
    println("  interrupted audio still playing after its stop: old %d cases %.0f ms, new %d cases %.0f ms".format(leakOld, leakOldMs, leakNew, leakNewMs))
    println("  next utterance changed by that old leak (e.g. ended early by its late error): $oldLeakHarm")
    println("  cuts only one version made: old $cutLaterOld, new (not a failed chunk's queued one) $cutLaterNew")
    println("  queued chunk heard after the chunk in front failed: %d cases, %.0f ms".format(glitch, glitchMs))
    println("  queued chunk started between an interrupt and its stop: %d cases, %.0f ms".format(stopWindow, stopWindowMs))
    if (differ > 0 || wrongNew > 0 || hangNew > hangOld || newLonger > 0 || cutLaterNew > 0) kotlin.system.exitProcess(1)
}
