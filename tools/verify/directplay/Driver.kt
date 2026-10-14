// ---- the direct-player simulator (hand-written; the two services are lifted) ----
//
// OldSvc holds the speak loop of the last version that handed each chunk to its
// engine with speak() (the engine played the audio itself); NewSvc holds the
// direct player of the working tree (each chunk to its engine with
// synthesizeToFile, the PCM streamed back and played on Easy Voice's own
// AudioTrack). gen.py lifts both. SpeechPlayer.kt and SpeechOut.kt are compiled
// as they are, SpeechOut against an AudioTrack model of AudioTrack.java's
// contract (fake/android_media.kt). Both run the same scenarios on one virtual
// clock; the new one's synthesis thread is a real thread that runs only where
// the real one would (fake/sim.kt).
//
// Compared, old against new, per utterance: every chunk heard to the end
// (engine, text, rate, pitch, volume, language), and the callback's start/done.
// Required of the new one only: nothing of an interrupted utterance heard after
// its interrupt; nothing heard after the screen reader was told the utterance
// is done; every chunk played at its engine's own sample rate and channel count
// (no resampling); no request of the utterance in progress stopped; no hang.
// Counted: the silence between chunks and the time to the first sound.
import java.util.Locale
import android.media.AudioAttributes
import com.sachinbaria.easyvoice.Gate
import com.sachinbaria.easyvoice.PcmStream
import com.sachinbaria.easyvoice.SpeechOut
import com.sachinbaria.easyvoice.UtterancePlayback
import com.sachinbaria.easyvoice.STREAM_OK
import sim.Baton
import sim.Clock
import sim.Sim

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
    fun isEmpty() = m.isEmpty()
}
object BundleCompat { fun <T> getParcelable(b: Bundle, k: String, c: Class<T>): T? = c.cast(b.m[k]) }
object AudioManager { const val AUDIO_SESSION_ID_GENERATE = 0 }
class Voice(val name: String, val locale: Locale)
object FakeLog { fun w(t: String, m: String, e: Throwable?) {} }
class SynthesisRequest(val charSequenceText: CharSequence, val params: Bundle?) {
    val speechRate = 100; val pitch = 100; val language = ""; val country = ""; val variant = ""
}
class SynthesisCallback(val run: Run, val utt: Int) {
    var started = false; var finished = false; var starts = 0; var dones = 0; var trickles = 0
    val maxBufferSize = 8192
    fun start(r: Int, f: Int, c: Int): Int { if (started) return -1; started = true; starts++; return 0 }
    fun done(): Int { if (finished) return -1; finished = true; dones++; run.doneAt[utt] = run.clock.now; return 0 }
    fun hasStarted() = started
    fun hasFinished() = finished
    fun audioAvailable(b: ByteArray, o: Int, l: Int): Int { trickles++; return 0 }
}
abstract class UtteranceProgressListener {
    abstract fun onStart(id: String)
    abstract fun onDone(id: String)
    @Deprecated("Deprecated in Java") abstract fun onError(id: String)
    open fun onError(id: String, errorCode: Int) { @Suppress("DEPRECATION") onError(id) }
    open fun onStop(id: String, interrupted: Boolean) {}
    open fun onBeginSynthesis(id: String, sampleRateInHz: Int, audioFormat: Int, channelCount: Int) {}
    open fun onAudioAvailable(id: String, audio: ByteArray) {}
}
class TextToSpeech(val engine: FakeEngine) {
    companion object {
        const val SUCCESS = 0; const val ERROR = -1; const val STOPPED = -2; const val QUEUE_FLUSH = 0; const val QUEUE_ADD = 1
        const val LANG_AVAILABLE = 0; const val LANG_COUNTRY_AVAILABLE = 1; const val LANG_COUNTRY_VAR_AVAILABLE = 2
        const val LANG_MISSING_DATA = -1; const val LANG_NOT_SUPPORTED = -2
        const val ERROR_SYNTHESIS = -3; const val ERROR_OUTPUT = -5; const val ERROR_INVALID_REQUEST = -8
    }
    object Engine {
        const val KEY_PARAM_STREAM = "streamType"; const val KEY_PARAM_UTTERANCE_ID = "utteranceId"
        const val KEY_PARAM_VOLUME = "volume"; const val KEY_PARAM_PAN = "pan"; const val KEY_PARAM_SESSION_ID = "sessionId"
        const val DEFAULT_STREAM = 3
    }
    @Volatile var listener: UtteranceProgressListener? = null
    var rate = 1f; var pitch = 1f; var lang = ""; val boundTo = engine.raw
    fun setSpeechRate(r: Float): Int { rate = r; return SUCCESS }
    fun setPitch(p: Float): Int { pitch = p; return SUCCESS }
    fun setOnUtteranceProgressListener(l: UtteranceProgressListener?): Int { listener = l; return SUCCESS }
    fun setAudioAttributes(a: AudioAttributes): Int = SUCCESS
    fun speak(text: CharSequence, mode: Int, params: Bundle?, id: String?): Int = engine.speak(this, text.toString(), mode, params, id)
    fun synthesizeToFile(text: CharSequence, params: Bundle, file: java.io.File, id: String): Int = engine.synthesizeToFile(this, text.toString(), params, file, id)
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
    fun errorWithStack(tag: String, msg: String, ex: Throwable) { sink?.invoke(msg) }
    @JvmStatic var loggingOn = true
    fun isLoggingEnabled() = loggingOn
}
data class TextChunk(var text: String, var lang: String, var typeCode: Int = 0, var forcedEngine: String? = null, var forcedLocale: String? = null, var forcedVariant: String? = null) {
    fun iso3(): String = lang
}
class FakePrefs { fun toIso3(s: String) = s }
fun iso3OfCode(s: String) = s
object LangStore {
    lateinit var run: Run
    fun engineFor(lang: String, mode: Int): String = run.config[lang] ?: ""
    fun voiceParamsFor(lang: String): IntArray { val h = (lang.hashCode() and 0xff); return intArrayOf(60 + h % 90, 70 + h % 60, 50 + h % 50) }
}
fun localeOf(l: String, c: String = "", v: String = "") = Locale(l, c, v)
// The player's lock on the simulator: one thread runs at a time, so locking is
// nothing; waiting hands the baton back; a signal makes the waiter runnable.
class SimGate(private val baton: Baton) : Gate {
    override fun <T> locked(block: () -> T): T = block()
    override fun await() { baton.block() }
    override fun signalAll() { baton.wake() }
}

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
                val type = listOf(1, 2, 1, 2, 3, 4)[r.nextInt(6)]
                val l = when (type) { 1 -> "eng"; 2 -> dualLang; 3 -> numberLang; else -> "eng" }
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
    // The new engines' audio: each engine's own format and synthesis speed.
    val sameRate = r.nextInt(4) == 0
    val sampleRate = engines.associateWith { if (sameRate) 24000 else listOf(11025, 16000, 22050, 24000)[r.nextInt(4)] }
    val channels = engines.associateWith { if (r.nextInt(5) == 0) 2 else 1 }
    val speed = engines.associateWith { listOf(1.5, 4.0, 12.0, 40.0)[r.nextInt(4)] }
    val pieceBytes = engines.associateWith { listOf(512, 1999, 4096, 8191)[r.nextInt(4)] }
    val keepAlive = r.nextInt(6) == 0
    init {
        for (l in langs) config[l] = engines[r.nextInt(3)]
        if (r.nextInt(4) == 0) config["hin"] = config["guj"]!!
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

// ---------------- the run ----------------
class Play(val gen: Int, val utt: Int, val engine: String, val text: String, val rate: Float, val pitch: Float,
           val volume: Float, val lang: String, val start: Double, var end: Double) {
    var cut = false; var errored = false
    fun key() = "$engine|$text|$rate|$pitch|$volume|$lang"
}
// One request a new engine was given (synthesizeToFile).
class FileItem(val client: TextToSpeech, val engine: String, val text: String, val id: String, val serial: Int,
               val utt: Int, val gen: Int, val rate: Float, val pitch: Float, val lang: String,
               val sampleRate: Int, val channels: Int, val frames: Long) {
    var produced = 0L; var failed = false; var dropped = false
}
class Heard { var frames = 0L; var first = 0.0; var last = 0.0; var vol = 0f; var offVol = 0L }
class Run(val sc: Scenario, val newCode: Boolean) {
    val clock = Clock()
    val baton = Baton(clock)
    val now: Double get() = clock.now
    fun at(t: Double, r: () -> Unit) = clock.at(t, r)
    val config = HashMap(sc.config)
    val plays = ArrayList<Play>()
    val notes = ArrayList<String>()
    val engines = sc.engines.associateWith { FakeEngine(this, it, sc.latency[it]!!) }
    var curUtt = 0
    val genOfUtt = HashMap<Int, Int>()
    val uttStart = HashMap<Int, Double>()
    val uttEnd = HashMap<Int, Double>()
    val doneAt = HashMap<Int, Double>()
    val interruptAt = HashMap<Int, Double>()
    val callbacks = HashMap<Int, SynthesisCallback>()
    val attempts = ArrayList<Triple<Int, String, String>>()
    /** "utterance|text|engine" of every request made. */
    val asked = HashSet<String>()
    var failure: String? = null
    var steps = 0
    // the new code's audio
    var nextSerial = 1
    val items = HashMap<Int, FileItem>()
    val heard = HashMap<Int, Heard>()
    var leakFrames = 0L; var afterDoneFrames = 0L; var rateMismatch = 0L; var unknownFrames = 0L; var silenceFrames = 0L
    var liveKilled = 0
    var liveCheck: ((Int) -> Boolean)? = null
    val waiting = ArrayDeque<Int>()
    var running = -1
    fun onHeard(track: Any, rate: Int, ch: Int, value: Int, frames: Long, t0: Double, t1: Double, vol: Float) {
        if (value == 0) { silenceFrames += frames; return }
        val item = items[value] ?: run { unknownFrames += frames; return }
        val h = heard.getOrPut(value) { Heard() }
        if (h.frames == 0L) { h.first = t0; h.vol = vol; firstHeard(item, t0) }
        h.frames += frames; h.last = t1
        if (vol != h.vol) h.offVol += frames
        if (rate != item.sampleRate || ch != item.channels) rateMismatch += frames
        // (a run is accounted whole, so its end may pass the instant by less
        // than a frame: only frames wholly after it count)
        val ti = interruptAt[item.utt]
        if (ti != null) { val after = ((t1 - ti) * rate / 1000.0).toLong(); if (after > 1) { leakFrames += after; if (traceOn) println("    LEAK %d frames of %s %.3f-%.3f (interrupt %.3f, now %.3f)".format(after, item.text, t0, t1, ti, now)) } }
        val td = doneAt[item.utt]
        if (td != null) { val after = ((t1 - td) * rate / 1000.0).toLong(); if (after > 1) afterDoneFrames += after }
    }
    private fun firstHeard(item: FileItem, t0: Double) {
        val i = sc.interrupt ?: return
        if (item.utt != 0 || i.first != item.text || interruptAt.containsKey(0)) return
        at(t0 + i.second) {
            if (interruptAt.containsKey(0) || uttEnd.containsKey(0)) return@at
            interruptAt[0] = now
            svcInterrupt()
        }
    }
}
class SerialQueue(val run: Run, val hop: Double) {
    var last = 0.0
    fun post(r: () -> Unit) { val t = maxOf(run.now, last) + hop; last = t; run.at(t, r) }
}
class SimHandler(val sq: SerialQueue) {
    fun post(r: Runnable): Boolean { sq.post { r.run() }; return true }
    fun post(r: () -> Unit): Boolean { sq.post(r); return true }
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
    // callbacks to one client arrive in order, a binder hop later
    private val lastDispatch = HashMap<TextToSpeech, Double>()
    fun dispatch(client: TextToSpeech, f: (UtteranceProgressListener) -> Unit) {
        val t = maxOf(run.now + binder, (lastDispatch[client] ?: 0.0) + 1e-6)
        lastDispatch[client] = t
        run.at(t) { val l = client.listener; if (l != null) f(l) }
    }
    // ---- the old path: the engine plays the audio itself ----
    fun flush() {
        token++
        val p = playing
        if (p != null && p.end > run.now) { p.end = run.now; p.cut = true }
        playing = null
        synthFree = run.now; lastPlayEnd = run.now
    }
    fun stop(): Int {
        if (dead) return TextToSpeech.ERROR
        if (run.newCode) { dropAll(); return TextToSpeech.SUCCESS }
        flush(); return TextToSpeech.SUCCESS
    }
    fun speak(client: TextToSpeech, text: String, mode: Int, params: Bundle?, id: String?): Int {
        if (dead) return TextToSpeech.ERROR
        if (mode == TextToSpeech.QUEUE_FLUSH) flush()
        if (text.isEmpty()) return TextToSpeech.SUCCESS
        val spec = run.sc.utts.flatMap { it.chunks }.first { it.text == text }
        val item = Item(client, text, id, client.rate, client.pitch, params?.getFloat("volume", 0f) ?: 0f, client.lang)
        run.attempts.add(Triple(run.curUtt, text, client.lang))
        run.asked.add("${run.curUtt}|$text|$raw")
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
        // AOSP's own playback inside the engine, timed as the new path is: its
        // AudioTrack (a NEW one per request, BlockingAudioTrack) starts only once
        // it holds max(8192 bytes, the platform minimum) or the whole chunk, the
        // engine hands its audio over in the same pieces, and a request queued
        // behind another gets its track only when that one has finished playing.
        val sr = run.sc.sampleRate[raw]!!; val ch = run.sc.channels[raw]!!
        val bytesPerMs = sr * 2.0 * ch / 1000.0
        val piece = run.sc.pieceBytes[raw]!!
        val threshold = maxOf(8192.0, Sim.minBufferMs * bytesPerMs)
        val fillMs = minOf(spec.dur, Math.ceil(threshold / piece) * piece / bytesPerMs)
        val first = maxOf(t0 + latency + fillMs / run.sc.speed[raw]!!, t0 + TRACK_CREATE) + Sim.startLatency
        val start = maxOf(first, lastPlayEnd + TRACK_CREATE + Sim.startLatency)
        val full = spec.dur
        val end = if (fault != null) start + fault.at else start + full
        lastPlayEnd = end
        // The engine's next request starts when this one's synthesis returns: no
        // sooner than its own speed allows, and held back while more than 500 ms
        // of its audio is still unplayed (AOSP's MAX_UNCONSUMED_AUDIO_MS).
        synthFree = maxOf(first, end - 500.0, t0 + latency + (end - start) / run.sc.speed[raw]!!)
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
        val d = run.sc.death
        if (d != null && d.first == raw && d.second == text) run.at(start + d.third) {
            if (tok != token || dead) return@at
            if (playing?.text != text) return@at
            if (run.plays.any { it.errored && it.engine == raw && it.end <= start + 1e-6 && it.end >= start - 2.0 }) return@at
            dead = true; flush()
            run.svcDeath(raw)
        }
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
    // ---- the new path: the engine synthesises into a file and streams the PCM
    // back (FileSynthesisCallback: onBeginSynthesis, onStart, onAudioAvailable...,
    // onDone), one request at a time in arrival order (QUEUE_ADD) ----
    private val fileQueue = ArrayDeque<FileItem>()
    private var current: FileItem? = null
    fun synthesizeToFile(client: TextToSpeech, text: String, params: Bundle, file: java.io.File, id: String): Int {
        if (dead) return TextToSpeech.ERROR
        val spec = run.sc.utts.flatMap { it.chunks }.first { it.text == text }
        val serial = run.nextSerial++
        val sr = run.sc.sampleRate[raw]!!; val ch = run.sc.channels[raw]!!
        val item = FileItem(client, raw, text, id, serial, run.curUtt, run.genOfUtt[run.curUtt] ?: -1, client.rate, client.pitch,
            client.lang, sr, ch, (spec.dur * sr / 1000.0).toLong())
        run.items[serial] = item
        run.attempts.add(Triple(run.curUtt, text, client.lang))
        run.asked.add("${run.curUtt}|$text|$raw")
        fileQueue.addLast(item)
        if (current == null) startNext()
        return TextToSpeech.SUCCESS
    }
    private fun dropAll() {
        token++
        val dropped = ArrayList<FileItem>()
        current?.let { dropped.add(it) }; dropped.addAll(fileQueue)
        current = null; fileQueue.clear()
        for (it in dropped) {
            it.dropped = true
            if (run.liveCheck?.invoke(it.gen) == true) run.liveKilled++
            dispatch(it.client) { l -> l.onStop(it.id, true) }
        }
    }
    private fun startNext() {
        val item = fileQueue.removeFirstOrNull() ?: run { current = null; return }
        current = item
        val tok = ++token
        val fault = run.sc.faults[item.text + "|" + raw]
        if (fault != null && fault.immediate) {
            run.at(run.now + latency / 2) {
                if (tok != token || dead) return@at
                item.failed = true
                dispatch(item.client) { it.onError(item.id, fault.code) }
                current = null; startNext()
            }
            return
        }
        val speed = run.sc.speed[raw]!!
        val frameBytes = 2 * item.channels
        val limit = if (fault != null) minOf(item.frames, (fault.at * item.sampleRate / 1000.0).toLong()) else item.frames
        val totalBytes = limit * frameBytes
        val piece = run.sc.pieceBytes[raw]!!
        val t0 = run.now + latency
        val synthDur = item.frames * 1000.0 / item.sampleRate / speed
        run.at(t0) {
            if (tok != token || dead) return@at
            dispatch(item.client) { it.onBeginSynthesis(item.id, item.sampleRate, 2, item.channels) }
            dispatch(item.client) { it.onStart(item.id) }
        }
        var off = 0L
        var t = t0
        while (off < totalBytes) {
            val n = minOf(piece.toLong(), totalBytes - off).toInt()
            val bytes = ByteArray(n)
            for (b in 0 until n) {
                val pos = off + b
                val inFrame = (pos % frameBytes).toInt()
                bytes[b] = if (inFrame % 2 == 0) (item.serial and 0xff).toByte() else ((item.serial shr 8) and 0xff).toByte()
            }
            off += n
            t += (n.toDouble() / frameBytes) * 1000.0 / item.sampleRate / speed
            val at = t
            run.at(at) {
                if (tok != token || dead) return@at
                item.produced += n / frameBytes
                dispatch(item.client) { it.onAudioAvailable(item.id, bytes) }
            }
        }
        run.at(t) {
            if (tok != token || dead) return@at
            if (fault != null) { item.failed = true; dispatch(item.client) { it.onError(item.id, fault.code) } }
            else dispatch(item.client) { it.onDone(item.id) }
            current = null; startNext()
        }
        val d = run.sc.death
        if (d != null && d.first == raw && d.second == item.text) {
            val spec = run.sc.utts.flatMap { it.chunks }.first { it.text == item.text }
            run.at(t0 + synthDur * (d.third / spec.dur)) {
                if (tok != token || dead) return@at
                dead = true; token++; current = null; fileQueue.clear()
                run.svcDeath(raw)
            }
        }
    }
}

interface Svc {
    fun startUtt(index: Int, spec: UttSpec)
    fun stopNow()
    fun death(raw: String)
    fun tick()
}
lateinit var svcRef: Svc
lateinit var runRef: Run
fun Run.svcDeath(raw: String) { val sq = mainQ; sq.post { svcRef.death(raw) } }
fun Run.svcInterrupt() {
    svcRef.stopNow()
    svcRef.tick()
    if (sc.utts.size > 1) startAt(1, now + sc.u2delay)
}
lateinit var mainQ: SerialQueue
var traceOn = false
// The time a new AudioTrack takes to build (AudioFlinger's createTrack), on
// both paths.
const val TRACK_CREATE = 8.0
fun Run.startAt(index: Int, t: Double) {
    at(t) {
        if (newCode) { arrive(index); return@at }
        uttStart[index] = now
        svcRef.startUtt(index, sc.utts[index])
        svcRef.tick()
        if (sc.utts[index].empty && sc.utts.size > index + 1) startAt(index + 1, now + sc.u2delay)
    }
}
// The new code: an utterance runs on the synthesis thread; the next one waits
// until that thread is free, as AOSP's SynthHandler runs one item at a time.
fun Run.arrive(index: Int) {
    if (baton.busy) { waiting.addLast(index); return }
    begin(index)
}
fun Run.begin(index: Int) {
    running = index
    uttStart[index] = now
    svcRef.startUtt(index, sc.utts[index])
}
fun Run.finished() {
    val i = running
    if (i >= 0) { uttEnd[i] = now; running = -1 }
    if (i >= 0 && sc.utts[i].empty && sc.utts.size > i + 1) { startAt(i + 1, now + sc.u2delay) }
    val next = waiting.removeFirstOrNull() ?: return
    at(now) { if (baton.busy) waiting.addFirst(next) else begin(next) }
}

// ---- OLD SVC TEMPLATE BEGIN ----
class OldSvc(val run: Run) : Svc {
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
    val accessibilitySpeech = AudioAttributes.Builder().build()
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
    fun loadLanguage(lang: String?, c: String?, v: String?, needEngine: Boolean, stale: (() -> Boolean)? = null): Int? =
        if (stale != null && stale()) null
        else if (needEngine && LangStore.engineFor(lang ?: "", modeInt).isEmpty()) null else onLoadLanguage(lang, c, v)
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
    fun reportSynthesisWork(nanos: Long) {}
    //OLD MEMBERS//
    fun utter(request: SynthesisRequest?, callback: SynthesisCallback?, plan: UttSpec) {
        //OLD BODY//
        pendingTail = {
            speakingPkg = null
            if (retryOnEngineDeath === retryOnDeath) retryOnEngineDeath = null
            startAndFinish(callback)
        }
    }
    override fun startUtt(index: Int, spec: UttSpec) {
        run.curUtt = index
        run.genOfUtt[index] = synthesisGeneration + 1
        val cb = SynthesisCallback(run, index); run.callbacks[index] = cb
        val params = Bundle(); params.putParcelable("utteranceId", "u$index")
        val text = if (spec.empty) "" else spec.chunks.joinToString(" ") { it.text }
        EasyVoiceLogger.sink = { s -> if (traceOn) println("    %.1f [%d] %s".format(run.now, index, s.replace("\n", " "))) }
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
// ---- OLD SVC TEMPLATE END ----

// ---- NEW SVC TEMPLATE BEGIN ----
class NewSvc(val run: Run) : Svc {
    val mainHandler = SimHandler(SerialQueue(run, run.sc.mainHop).also { mainQ = it })
    val speakHandler = SimHandler(SerialQueue(run, 0.3))
    val enginePool = ArrayList<EngineWrapper>()
    @Volatile var engineIndex = -1
    val mayAwaitEngine = ThreadLocal<Boolean>()
    val syncLock = Object()
    @Volatile var isStopped = false
    @Volatile var isFlushed = false
    @Volatile var synthesisGeneration = 0
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
    val keepAliveFlag = run.sc.keepAlive
    val accessibilitySpeech = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
    val gate: Gate = SimGate(run.baton)
    val speechOut = SpeechOut(gate, android.os.Handler())
    val streams = java.util.concurrent.ConcurrentHashMap<String, PcmStream>()
    val devNull = java.io.File("/dev/null")
    val stopQ = SerialQueue(run, run.sc.dStop)
    init {
        for (raw in run.sc.engines) {
            val w = EngineWrapper(raw)
            w.stopExec.sq = stopQ
            w.tts = TextToSpeech(run.engines[raw]!!); w.state = 2
            enginePool.add(w)
        }
        run.liveCheck = { g -> g == synthesisGeneration && !isStopped && !isFlushed && run.running >= 0 }
    }
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
    fun loadLanguage(lang: String?, c: String?, v: String?, needEngine: Boolean, stale: (() -> Boolean)? = null): Int? =
        if (stale != null && stale()) null
        else if (needEngine && LangStore.engineFor(lang ?: "", modeInt).isEmpty()) null else onLoadLanguage(lang, c, v)
    fun awaitEngineStarting(lang: String?) {}
    fun backupFor(lang: String): Boolean = engineFallbackFlag && lang.isNotEmpty()
    // as the real one: the wrapper leaves use at once and what it owed us ends
    fun clientIsDead(w: EngineWrapper, why: String) { w.state = -1; failPending(w, "lost its client (" + why + ")") }
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
    fun reportSynthesisWork(nanos: Long) {}
    //NEW MEMBERS//
    fun utter(request: SynthesisRequest?, callback: SynthesisCallback?, plan: UttSpec) {
        //NEW BODY//
    }
    override fun startUtt(index: Int, spec: UttSpec) {
        run.curUtt = index
        run.genOfUtt[index] = synthesisGeneration + 1
        val cb = SynthesisCallback(run, index); run.callbacks[index] = cb
        val params = Bundle(); params.putParcelable("utteranceId", "u$index")
        val text = if (spec.empty) "" else spec.chunks.joinToString(" ") { it.text }
        EasyVoiceLogger.sink = { s -> if (traceOn) println("    %.1f [%d] %s".format(run.now, index, s.replace("\n", " "))) }
        run.baton.start { utter(SynthesisRequest(text, params), cb, spec) }
    }
    override fun stopNow() { svcOnStop() }
    override fun death(raw: String) { onEngineProcessGone(raw) }
    override fun tick() {}
}
// ---- NEW SVC TEMPLATE END ----

fun runOne(sc: Scenario, newCode: Boolean): Run {
    val run = Run(sc, newCode)
    runRef = run
    Sim.clock = run.clock; Sim.baton = run.baton; Sim.tracksMade = 0
    Sim.heard = if (newCode) run::onHeard else null
    run.baton.onFinish = { run.finished() }
    LangStore.run = run
    val svc: Svc = if (newCode) NewSvc(run) else OldSvc(run)
    svcRef = svc
    run.startAt(0, 0.0)
    while (run.clock.q.isNotEmpty()) {
        val e = run.clock.q.poll(); run.clock.now = e.t; e.run()
        if (!newCode) svc.tick()
        if (run.baton.failure != null) { run.failure = "exception " + run.baton.failure; break }
        if (++run.steps > 2_000_000) { run.failure = "runaway"; break }
    }
    if (newCode) {
        if (run.failure == null && run.baton.busy) run.failure = "hang (the synthesis thread never returned)"
        // what was heard, as the old run records what the engines played
        for ((serial, item) in run.items) {
            val h = run.heard[serial] ?: continue
            val p = Play(item.gen, item.utt, item.engine, item.text, item.rate, item.pitch, h.vol, item.lang, h.first, h.last)
            p.cut = h.frames < item.frames
            p.errored = item.failed
            run.plays.add(p)
        }
    }
    return run
}

fun leakOldOf(run: Run, sc: Scenario): Double {
    val t = run.interruptAt[0] ?: return 0.0
    val lim = t + sc.dStop + 3.0
    return run.plays.filter { it.utt == 0 && it.end > lim }.sumOf { it.end - lim }
}
fun summary(run: Run, utt: Int): String {
    val until = run.interruptAt[utt] ?: Double.MAX_VALUE
    val full = run.plays.filter { it.utt == utt && !it.cut && !it.errored && it.end <= until + 1e-6 }.sortedBy { it.start }.map { it.key() }
    val cb = run.callbacks[utt]
    val ended = run.uttEnd.containsKey(utt)
    return "full=$full cb=${cb?.starts}/${cb?.dones} ended=$ended"
}

fun main(args: Array<String>) {
    val n = args.getOrNull(0)?.toInt() ?: 50000
    if (args.getOrNull(1) == "dump") {
        val sc = Scenario(n)
        println("mode ${sc.mode} fb ${sc.fallback} lat ${sc.latency} rates ${sc.sampleRate} ch ${sc.channels} speed ${sc.speed} cfg ${sc.config} faults ${sc.faults.mapValues { "${it.value.immediate}/${it.value.at.toInt()}/${it.value.code}" }} death ${sc.death} int ${sc.interrupt}")
        for (u in sc.utts) println("  utt " + u.chunks.map { it.text + "(" + it.type + "," + it.dur.toInt() + ")" } + " firstLoad=" + u.firstLoad)
        for (newCode in listOf(false, true)) {
            println(if (newCode) "NEW" else "OLD")
            traceOn = args.getOrNull(2) == "trace"
            val run = runOne(Scenario(n), newCode)
            traceOn = false
            for (p in run.plays.sortedBy { it.start }) println("  u${p.utt} ${p.engine} ${p.text} %.1f-%.1f cut=${p.cut} err=${p.errored} vol=${p.volume}".format(p.start, p.end))
            println("  ends ${run.uttEnd} done ${run.doneAt} int ${run.interruptAt} failure ${run.failure} leak ${run.leakFrames} afterDone ${run.afterDoneFrames} rate ${run.rateMismatch} killed ${run.liveKilled}")
            for (u in Scenario(n).utts.indices) println("  " + summary(run, u))
        }
        return
    }
    var differ = 0; var cases = 0; var chunksHeard = 0; var failures = 0
    var deathDiff = 0; var hangs = 0; var leakN = 0; var afterDoneN = 0; var rateN = 0; var killedN = 0; var unknownN = 0
    var wrongNew = 0; var wrongOld = 0
    var gapOld = 0.0; var gapNew = 0.0; var gapsCompared = 0; var newLonger = 0; var maxLonger = 0.0
    var sameFmtGaps = 0; var sameFmtNew = 0.0; var switchGaps = 0; var switchNew = 0.0
    var firstOld = 0.0; var firstNew = 0.0; var firstN = 0
    var leakOldCases = 0; var offVolFrames = 0L; var tracks = 0L; var trickles = 0L; var lookaheadEngine = 0; var lookaheadFault = 0; var minus5 = 0; var follows = 0
    fun engineless(summary: String) = summary.replace(Regex("com\\.[a-z_-]+\\.tts\\|"), "")
    for (seed in 1..n) {
        val sc = Scenario(seed)
        val o = runOne(Scenario(seed), false)
        EasyVoiceLogger.loggingOn = seed % 2 == 0
        val w = runOne(Scenario(seed), true)
        EasyVoiceLogger.loggingOn = true
        tracks += Sim.tracksMade
        cases++
        var why: String? = null
        if (w.failure != null) { failures++; why = "new: " + w.failure }
        if (o.failure != null && why == null) { why = "old: " + o.failure }
        if (w.leakFrames > 0) { leakN++; if (why == null) why = "new: ${w.leakFrames} frames heard after the interrupt" }
        if (w.afterDoneFrames > 0) { afterDoneN++; if (why == null) why = "new: ${w.afterDoneFrames} frames heard after done()" }
        if (w.rateMismatch > 0) { rateN++; if (why == null) why = "new: ${w.rateMismatch} frames played at another rate/channel count" }
        if (w.liveKilled > 0) { killedN++; if (why == null) why = "new: ${w.liveKilled} requests of the utterance in progress stopped" }
        if (w.unknownFrames > 0) { unknownN++; if (why == null) why = "new: ${w.unknownFrames} frames of no request heard" }
        if (leakOldOf(o, sc) > 0) leakOldCases++
        for (h in w.heard.values) offVolFrames += h.offVol
        for (cb in w.callbacks.values) trickles += cb.trickles
        val wantLang = HashMap<String, String>()
        for (c in sc.utts.flatMap { it.chunks }) if (c.lang != "xxx") wantLang[c.text] = c.lang
        fun wrongIn(run: Run, u: Int) = run.attempts.count { it.first == u && wantLang[it.second] != null && wantLang[it.second] != it.third }
        var byDesign = false
        for (u in sc.utts.indices) {
            val so = summary(o, u); val sn = summary(w, u)
            wrongOld += wrongIn(o, u)
            val wn = wrongIn(w, u); wrongNew += wn
            if (wn > 0 && why == null) why = "new: a chunk given to an engine in the wrong language, utterance $u"
            if (w.callbacks.containsKey(u) && !w.uttEnd.containsKey(u)) { hangs++; if (why == null) why = "new: utterance $u never ended" }
            if (so != sn) {
                // The next request waits for the interrupt, which is anchored in the
                // audio of the utterance before: once one differs by design, the
                // ones after it follow from it.
                if (byDesign) { follows++; continue }
                byDesign = true
                // An engine's death is where the two are not alike by design: the
                // old engine was PLAYING the chunk when it died; the new one only
                // synthesises it, so audio it had already sent is still heard.
                if (sc.death != null) { deathDiff++; continue }
                // A backup switch moves the LANGUAGE to another engine; the new
                // path had already handed the chunks after the failed one to their
                // engine (it hands them all over at once), and an engine that says
                // them is not taken off them. Same chunks, same voice settings and
                // language, another engine: counted, not a failure.
                if (sc.fallback && engineless(so) == engineless(sn)) { lookaheadEngine++; continue }
                // The same, where the chunk the new path had already handed to the
                // failed engine then failed there for a reason no other engine can
                // help (-8, the request itself): the old path never asked that
                // engine, it had moved the language to the backup first.
                if (sc.fallback && w.items.values.any { it.utt == u && it.failed && sc.faults[it.text + "|" + it.engine]?.code == -8 &&
                        o.plays.none { p -> p.utt == u && p.text == it.text && p.engine == it.engine } }) { lookaheadFault++; continue }
                // The mirror: the old path moved the next chunk to the backup with the
                // language, and it failed THERE -- an engine the new path never asked,
                // because its chunk was already with the first engine, which said it.
                if (sc.fallback && sc.faults.keys.any { k -> o.asked.contains("$u|$k") && !w.asked.contains("$u|$k") }) { lookaheadFault++; continue }
                // An engine's ERROR_OUTPUT (-5) is handed to the backup now (it is the
                // engine's own file, not our speaker); the old path ended there.
                // An utterance AFTER that one differs too: the next request waits for the
                // interrupt, anchored in audio the old path never reached.
                if (sc.fallback && sc.faults.any { f -> f.value.code == -5 && (0..u).any { v -> sc.utts[v].chunks.any { c -> f.key.startsWith(c.text + "|") } } }) { minus5++; continue }
                byDesign = false
                if (why == null) why = "utterance $u\n   old $so\n   new $sn"
            }
        }
        // timing: the silence between two chunks heard whole in both, and the first sound
        for (u in sc.utts.indices) {
            fun fulls(run: Run) = run.plays.filter { it.utt == u && !it.cut && !it.errored }.sortedBy { it.start }
            val po = fulls(o); val pn = fulls(w)
            if (po.map { it.key() } == pn.map { it.key() }) {
                for (k in 1 until po.size) {
                    val go = po[k].start - po[k - 1].end; val gn = pn[k].start - pn[k - 1].end
                    gapsCompared++; gapOld += go; gapNew += gn
                    if (gn > go + 1e-6) maxLonger = maxOf(maxLonger, gn - go)
                    if (gn > go + 1.0) { newLonger++; if (System.getenv("EV_SHOW") == "longer") println("longer seed $seed utt $u gap $k old %.1f new %.1f".format(go, gn)) }
                    fun whole(t: String) = w.items.values.first { it.text == t && it.utt == u && (w.heard[it.serial]?.frames ?: -1L) >= it.frames && !it.failed }
                    val a = whole(pn[k - 1].text)
                    val b = whole(pn[k].text)
                    if (a.sampleRate == b.sampleRate && a.channels == b.channels) { sameFmtGaps++; sameFmtNew += gn } else { switchGaps++; switchNew += gn }
                }
                if (po.isNotEmpty() && o.uttStart.containsKey(u) && w.uttStart.containsKey(u)) {
                    firstN++; firstOld += po[0].start - o.uttStart[u]!!; firstNew += pn[0].start - w.uttStart[u]!!
                }
            }
        }
        chunksHeard += w.plays.count { !it.cut && !it.errored }
        if (why != null) { differ++; if (differ <= 12) println("DIFF seed $seed (mode ${sc.mode} fallback ${sc.fallback}): $why") }
    }
    println("$cases scenarios (old speak() path vs the direct player, AOSP-shaped engines): $differ differ")
    println("  chunks heard to the end (new): $chunksHeard; hangs $hangs; failures $failures")
    println("  heard after an interrupt: $leakN; after done(): $afterDoneN; at another rate: $rateN; of no request: $unknownN")
    println("  requests of the utterance in progress stopped: $killedN; given in the wrong language: old $wrongOld, new $wrongNew")
    println("  utterances that differ where an engine died (by design, not compared): $deathDiff")
    println("  utterances where a chunk already handed over stayed with its engine after a backup switch: $lookaheadEngine")
    println("    of them where one path met a fault on an engine the other never asked: $lookaheadFault")
    println("  utterances that went on where an engine said ERROR_OUTPUT (-5): handed to the backup now: $minus5")
    println("  utterances after one of those, whose request waited for audio that differs: $follows")
    println("  interrupted audio still playing after the old path's stop: $leakOldCases scenarios (the new one: $leakN)")
    println("  silence between chunks: old %.0f ms, new %.0f ms over %d boundaries (%d longer by over 1 ms, the most %.1f ms)".format(gapOld, gapNew, gapsCompared, newLonger, maxLonger))
    println("    new, same format: %d boundaries, avg %.2f ms; format switch: %d, avg %.2f ms".format(sameFmtGaps, if (sameFmtGaps > 0) sameFmtNew / sameFmtGaps else 0.0, switchGaps, if (switchGaps > 0) switchNew / switchGaps else 0.0))
    println("  first sound after the request: old avg %.1f ms, new avg %.1f ms over %d utterances".format(if (firstN > 0) firstOld / firstN else 0.0, if (firstN > 0) firstNew / firstN else 0.0, firstN))
    println("  tracks built: $tracks; frames of a chunk's tail heard at the next chunk's volume: $offVolFrames; keep-alive trickles: $trickles")
    if (differ > 0) kotlin.system.exitProcess(1)
}
