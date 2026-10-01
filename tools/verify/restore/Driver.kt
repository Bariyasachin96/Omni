// Restore state machine, driven through the situations an update produces.
// The class body below the fakes is lifted verbatim from EasyVoiceTtsService.kt.
object EasyVoiceLogger {
    const val TAG = "t"
    val lines = ArrayList<String>()
    inline fun debug(tag: String, msg: () -> String) { lines.add(msg()) }
    fun error(tag: String, msg: String) { lines.add(msg) }
}
class TextToSpeech(ctx: Any?, val listener: OnInitListener, val pkg: String) {
    fun interface OnInitListener { fun onInit(status: Int) }
    companion object { const val SUCCESS = 0; const val ERROR = -1; val built = ArrayList<TextToSpeech>() }
    var shut = false
    init { built.add(this) }
    fun shutdown() { shut = true }
    fun setAudioAttributes(a: Any?): Int = 0
}
object EngineFinder {
    var builtInEngine = "com.google.android.tts"
    var boundTo: String? = null
    fun boundEngineOf(c: TextToSpeech?, expected: String): String = boundTo ?: expected
    fun isTtsEngine(ctx: Any, pkg: String) = true
    fun isSelfEngine(pkg: String) = false
}
class FakeBinder { var isBinderAlive = true }
class FakeHandler {
    val delayed = ArrayList<Runnable>()
    fun post(r: Runnable): Boolean { r.run(); return true }
    fun postDelayed(r: Runnable, ms: Long): Boolean { delayed.add(r); return true }
    fun removeCallbacks(r: Runnable) { delayed.remove(r) }
    fun fireAll() { val l = ArrayList(delayed); delayed.clear(); for (r in l) r.run() }
}
object Looper { object Main { val isCurrentThread = true }; fun getMainLooper() = Main }

class Svc {
    val enginePool = ArrayList<EngineWrapper>()
    var walkList = ArrayList<String>()
    var initializingIndex = 0
    val engineServiceBinders = HashMap<String, FakeBinder>()
    val mainHandler = FakeHandler()
    val applicationContext = Any()
    val packageName = "com.sachinbaria.easyvoice"
    var forceAccessibilityFlag = false
    val accessibilitySpeech: Any? = null
    var googleEngineIndex = -1
    var speakingPkg: String? = null
    var retryOnEngineDeath: ((String) -> Boolean)? = null
    val syncLock = Object()
    var isStopped = false
    var destroyed = false
    val binds = ArrayList<String>()
    fun bindEngineKeepAlive(pkg: String) { binds.add(pkg) }
    var scanned = 0
    fun startupScan() { scanned++ }
    inner class EngineWrapper(pkgName: String) {
        val pkg: String = normPkg(pkgName)
        val rawPkg: String = pkgName
        var tts: TextToSpeech? = null
        var state = 0
        var locale: java.util.Locale? = null
        var voiceName = ""
        var audioAttrSet = false
        var restoring = false
        var restoreSpent = false
        var processGone = false
        var restoreWanted = false
        var needRetryUsed = false
        fun stop() {}
        fun shutdown() { tts?.shutdown() }
        fun forgetClientState() {}
    }
//LIFTED//
}

var bad = 0
fun check(name: String, ok: Boolean, extra: String = "") { println((if (ok) "ok   " else "FAIL ") + name + (if (extra.isEmpty()) "" else "  " + extra)); if (!ok) bad++ }
fun fresh(): Svc { TextToSpeech.built.clear(); EngineFinder.boundTo = null; return Svc() }
const val G = "com.google.android.tts"

fun main() {
    // S1: after an update the keep-alive answers first, then the first init fails.
    run {
        val s = fresh(); s.walkList = arrayListOf(G); s.startNextInitEngine()
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)          // process up, init pending
        TextToSpeech.built[0].listener.onInit(TextToSpeech.ERROR)                    // first start fails
        val w = s.enginePool[0]
        check("S1 first start failed -> restored at once", TextToSpeech.built.size == 2, "clients=${TextToSpeech.built.size} state=${w.state}")
        TextToSpeech.built[1].listener.onInit(TextToSpeech.SUCCESS)
        check("S1 engine ready after its restore", w.state == 2)
        check("S1 the walk finished (startup scan ran)", s.scanned == 1)
    }
    // S2: a restore fails with the process up; a language asks: ONE more, never per utterance.
    run {
        val s = fresh(); s.walkList = arrayListOf(G); s.startNextInitEngine()
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)
        TextToSpeech.built[0].listener.onInit(TextToSpeech.ERROR)                    // -> restore #1
        TextToSpeech.built[1].listener.onInit(TextToSpeech.ERROR)                    // restore fails, process up
        val w = s.enginePool[0]
        check("S2 failed restore leaves it waiting", w.state == -1 && TextToSpeech.built.size == 2)
        s.recoverEngineNotReadyMain(G)                                               // a language needs it
        check("S2 a language gets one more restore", TextToSpeech.built.size == 3)
        TextToSpeech.built[2].listener.onInit(TextToSpeech.ERROR)
        repeat(5) { s.recoverEngineNotReadyMain(G) }
        check("S2 no restore per utterance after that", TextToSpeech.built.size == 3, "clients=${TextToSpeech.built.size}")
        s.onEngineProcessBack(G)                                                     // an event
        check("S2 an event gives the restore back", TextToSpeech.built.size == 4)
        TextToSpeech.built[3].listener.onInit(TextToSpeech.ERROR)                    // fails again
        s.recoverEngineNotReadyMain(G)
        check("S2 the event also gave back the one extra restore", TextToSpeech.built.size == 5)
        TextToSpeech.built[4].listener.onInit(TextToSpeech.SUCCESS)
        check("S2 engine ready", w.state == 2)
    }
    // S3: the engine is updated while we run: death, then its process comes back.
    run {
        val s = fresh(); s.walkList = arrayListOf(G); s.startNextInitEngine()
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)
        TextToSpeech.built[0].listener.onInit(TextToSpeech.SUCCESS)
        val w = s.enginePool[0]
        s.engineServiceBinders.remove(G); s.onEngineProcessGone(G)                    // killed by the update
        check("S3 death marks it unusable", w.state == -1 && w.processGone)
        s.recoverEngineNotReadyMain(G)                                               // utterance while it is down
        check("S3 no restore while the process is down", TextToSpeech.built.size == 1)
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)          // new version up
        check("S3 restored when it is back", TextToSpeech.built.size == 2)
        TextToSpeech.built[1].listener.onInit(TextToSpeech.SUCCESS)
        check("S3 engine ready", w.state == 2)
    }
    // S4: the first init never answers (30 s watchdog).
    run {
        val s = fresh(); s.walkList = arrayListOf(G); s.startNextInitEngine()
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)
        s.mainHandler.fireAll()
        check("S4 a silent first start is restored", TextToSpeech.built.size == 2 && TextToSpeech.built[0].shut)
        check("S4 the walk finished", s.scanned == 1)
    }
    // S5: the first start fell back to another engine (mid-update) and was refused.
    run {
        val s = fresh(); s.walkList = arrayListOf(G); s.startNextInitEngine()
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)
        EngineFinder.boundTo = "com.sachinbaria.easyvoice"
        TextToSpeech.built[0].listener.onInit(TextToSpeech.SUCCESS)
        EngineFinder.boundTo = null
        check("S5 refused fallback is restored", TextToSpeech.built.size == 2)
        TextToSpeech.built[1].listener.onInit(TextToSpeech.SUCCESS)
        check("S5 engine ready", s.enginePool[0].state == 2)
    }
    // S6: first start fails while the process is NOT up yet: wait, then restore once on its connect.
    run {
        val s = fresh(); s.walkList = arrayListOf(G); s.startNextInitEngine()
        TextToSpeech.built[0].listener.onInit(TextToSpeech.ERROR)
        check("S6 waits for the process", TextToSpeech.built.size == 1 && s.enginePool[0].processGone)
        s.engineServiceBinders[G] = FakeBinder(); s.onEngineProcessBack(G)
        check("S6 restored when it connects", TextToSpeech.built.size == 2)
    }
    println(if (bad == 0) "ALL RESTORE SCENARIOS PASS" else "$bad FAILED")
    kotlin.system.exitProcess(if (bad == 0) 0 else 1)
}
