// The cold-start waiter (EasyVoiceTtsService.awaitEngineStarting), lifted by
// gen.py and driven with REAL threads: a "synthesis" thread waits for an
// engine that is starting (state 0) or restoring (state 1) while a "main"
// thread settles it, fails it, interrupts, starts the next utterance or
// destroys the service. Every waiter must return, and only once its own reason
// to wait is gone.
@file:Suppress("UNUSED_PARAMETER", "unused")
object Looper { val main = ThreadLocal.withInitial { false }; fun getMainLooper() = this; val isCurrentThread get() = main.get() }
object SystemClock { fun uptimeMillis() = System.nanoTime() / 1_000_000 }
object EasyVoiceLogger { const val TAG = "t"; fun error(t: String, m: String) {} }
object LangStore { val map = HashMap<String, String>(); fun engineFor(lang: String, mode: Int) = map[lang] ?: "" }
class FakePrefs { fun toIso3(s: String) = s }
// the code since 2026-10-08 calls IsoCodes.kt's iso3OfCode; the same identity
fun iso3OfCode(s: String) = s
class EngineWrapper(raw: String) { val pkg = raw.replace("-", "").replace("_", ""); @Volatile var state = 0 }
class Svc {
    val mayAwaitEngine = ThreadLocal<Boolean>()
    val prefs = FakePrefs()
    val modeInt = 4
    val syncLock = Object()
    @Volatile var isStopped = false
    @Volatile var isFlushed = false
    @Volatile var synthesisGeneration = 0
    @Volatile var destroyed = false
    val enginePool = ArrayList<EngineWrapper>()
//LIFTED//
}

var failures = 0
fun check(name: String, ok: Boolean) { if (!ok) { failures++; println("FAIL " + name) } }

// waiter returns true when it came back; `gate` = the thread may wait, `main` = it is the main thread
fun waiter(svc: Svc, lang: String, gate: Boolean = true, main: Boolean = false): Thread {
    val t = Thread { svc.mayAwaitEngine.set(gate); Looper.main.set(main); svc.awaitEngineStarting(lang) }
    t.isDaemon = true; t.start(); return t
}
fun returned(t: Thread, ms: Long): Boolean { t.join(ms); return !t.isAlive }
fun setup(states: Map<String, Int>): Svc {
    val s = Svc()
    LangStore.map.clear(); LangStore.map["hin"] = "com.google.tts"; LangStore.map["eng"] = "com.elo_quence"; LangStore.map["guj"] = "com.google.tts"
    for ((p, st) in states) s.enginePool.add(EngineWrapper(p).also { it.state = st })
    return s
}

fun main() {
    var cases = 0
    fun case() { cases++ }
    // 1. starting engine becomes ready
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); val t = waiter(s, "hin"); check("1 waits while starting", !returned(t, 150)); s.enginePool[0].state = 2; s.engineSettled(); check("1 returns when ready", returned(t, 3000)) }
    // 2. its init fails
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); val t = waiter(s, "hin"); check("2 waits", !returned(t, 100)); s.enginePool[0].state = -1; s.engineSettled(); check("2 returns on failure", returned(t, 3000)) }
    // 3. a failed start goes into a restore (state 1), then the restore lands
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); val t = waiter(s, "hin"); check("3 waits", !returned(t, 100)); s.enginePool[0].state = 1; s.engineSettled(); check("3 keeps waiting through the restore", !returned(t, 150)); s.enginePool[0].state = 2; s.engineSettled(); check("3 returns when restored", returned(t, 3000)) }
    // 4. interrupted
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); val t = waiter(s, "hin"); check("4 waits", !returned(t, 100)); synchronized(s.syncLock) { s.isStopped = true; s.syncLock.notifyAll() }; check("4 returns on interrupt", returned(t, 3000)) }
    // 4b. flushed
    run { case(); val s = setup(mapOf("com.google.tts" to 1)); val t = waiter(s, "hin"); check("4b waits", !returned(t, 100)); synchronized(s.syncLock) { s.isFlushed = true; s.syncLock.notifyAll() }; check("4b returns on flush", returned(t, 3000)) }
    // 5. the next utterance began
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); val t = waiter(s, "hin"); check("5 waits", !returned(t, 100)); s.synthesisGeneration++; s.engineSettled(); check("5 returns on a new utterance", returned(t, 3000)) }
    // 6. service destroyed
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); val t = waiter(s, "hin"); check("6 waits", !returned(t, 100)); s.destroyed = true; s.engineSettled(); check("6 returns on destroy", returned(t, 3000)) }
    // 7. no gate: never waits
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); check("7 no gate, no wait", returned(waiter(s, "hin", gate = false), 1000)) }
    // 8. the main thread never waits
    run { case(); val s = setup(mapOf("com.google.tts" to 0)); check("8 main thread, no wait", returned(waiter(s, "hin", main = true), 1000)) }
    // 9. ready already
    run { case(); val s = setup(mapOf("com.google.tts" to 2)); check("9 ready, no wait", returned(waiter(s, "hin"), 1000)) }
    // 10. a different engine is starting
    run { case(); val s = setup(mapOf("com.google.tts" to 0, "com.elo_quence" to 2)); check("10 other engine, no wait", returned(waiter(s, "eng"), 1000)) }
    // 11. engine not in the pool, empty language, no engine for the language
    run { case(); val s = setup(mapOf("com.other" to 0)); check("11 not in pool", returned(waiter(s, "hin"), 1000)); check("11 empty lang", returned(waiter(s, ""), 1000)); check("11 no engine", returned(waiter(s, "xyz"), 1000)) }
    // 12. dead engine (state -1): no wait -- only a start or a restore is waited for
    run { case(); val s = setup(mapOf("com.google.tts" to -1)); check("12 dead engine, no wait", returned(waiter(s, "hin"), 1000)) }
    // 13. random histories: several engines, waiters on random languages, random settle / fail /
    //     restore / interrupt / new-utterance / destroy events. Every waiter returns, and none
    //     returns while its own engine is still 0 or 1 with nothing else having ended the wait.
    val rnd = java.util.Random(7)
    var early = 0; var hung = 0; var waited = 0
    repeat(400) {
        case()
        val engines = listOf("com.google.tts", "com.elo_quence")
        val s = setup(engines.associateWith { if (rnd.nextBoolean()) 0 else 2 })
        val langs = listOf("hin", "eng", "guj")
        val ts = (0 until 1 + rnd.nextInt(3)).map { val l = langs[rnd.nextInt(3)]; l to waiter(s, l) }
        Thread.sleep(20)
        var ended = false
        // an engine that has once been settled (2 or -1) has released its waiters,
        // even if a later restore puts it back to 1
        val released = HashSet<String>()
        for (w in s.enginePool) if (w.state != 0 && w.state != 1) released.add(w.pkg)
        val steps = 1 + rnd.nextInt(4)
        for (k in 0 until steps) {
            when (rnd.nextInt(6)) {
                0, 1 -> { val w = s.enginePool[rnd.nextInt(s.enginePool.size)]; w.state = listOf(2, -1, 1)[rnd.nextInt(3)]; if (w.state != 1) released.add(w.pkg); s.engineSettled(); Thread.sleep(10) }
                2 -> { synchronized(s.syncLock) { s.isStopped = true; s.syncLock.notifyAll() }; ended = true }
                3 -> { s.synthesisGeneration++; s.engineSettled(); ended = true }
                4 -> { s.destroyed = true; s.engineSettled(); ended = true }
                else -> {}
            }
            Thread.sleep(5)
        }
        Thread.sleep(30)
        for ((l, t) in ts) {
            val pkg = LangStore.map[l]!!.replace("_", "")
            val st = s.enginePool.first { it.pkg == pkg }.state
            val shouldWait = !ended && (st == 0 || st == 1) && !released.contains(pkg)
            if (shouldWait) { waited++; if (!t.isAlive) early++ } else if (!returned(t, 3000)) hung++
        }
        // release whatever still waits, and make sure it does come back
        for (w in s.enginePool) w.state = 2
        s.engineSettled()
        for ((_, t) in ts) if (!returned(t, 3000)) hung++
    }
    check("13 no waiter returned early ($early)", early == 0)
    check("13 no waiter hung ($hung)", hung == 0)
    println("$cases cases, $waited waiters checked while their engine was still starting: " + (if (failures == 0) "ALL PASS" else "$failures FAILED"))
    kotlin.system.exitProcess(if (failures == 0) 0 else 1)
}
