// ---- background-scan scheduling, old against new (hand-written driver) ----
//
// OldSvc and NewSvc hold the REAL scheduling functions of two versions of
// EasyVoiceTtsService.kt (gen.py lifts startupScan, the package-event scan
// request, onScanFinished and the screens-closed observer). Random histories of
// TTS-engine package events, the app opening (on the main screen, which scans
// itself, or on another screen, which does not), the app closing, the service's
// startup walk finishing, and scans reading engines one at a time, are run
// through both. Each change that needs the engines read again is tracked:
//   gap     -- a change no finished scan ever covered, once everything settled
//   wasted  -- a scan the SERVICE started when every change was already covered
import java.util.concurrent.atomic.AtomicLong

object EasyVoiceLogger {
    const val TAG = "EasyVoice"
    inline fun debug(tag: String, msg: () -> String) {}
    fun error(tag: String, msg: String) {}
}
interface FakeObserver { fun onStop(owner: FakeOwner) {} }
class FakeOwner

class Change(val time: Int, val pkg: String?, val coveredByScan: Int)
class Scan(val id: Int, val startTime: Int, val startSeq: Long, val byService: Boolean, val pending: ArrayDeque<String>)
class World(val seed: Int) {
    val r = java.util.Random(seed.toLong())
    val pkgs = listOf("a.tts", "b.tts", "c.tts")
    val installed = HashMap(pkgs.associateWith { true })
    var appOpen = false
    var now = 0
    var cur: Scan? = null
    var nextScan = 1
    val changes = ArrayList<Change>()
    val satisfied = HashSet<Change>()
    var wasted = 0
    var serviceScans = 0
    lateinit var svc: Svc
    var intrusive = 0
    fun startScan(byService: Boolean) {
        if (byService) {
            serviceScans++
            if (changes.all { it in satisfied }) wasted++
            // never while the app is open (its main screen scans for itself), and
            // never over a running scan: a newer scan supersedes the older one,
            // whose caller -- the main screen's progress page -- then never hears back
            if (appOpen || cur != null) intrusive++
        }
        val s = Scan(nextScan++, now, EngineFinder.changeSeq.get(), byService, ArrayDeque(pkgs.filter { installed[it] == true }))
        cur = s
        EngineFinder.scanRunning = true
    }
    fun step() {
        val s = cur ?: return
        if (s.pending.isNotEmpty()) { s.pending.removeFirst(); return }
        // finalize: what EngineFinder.finalizeScan does
        cur = null
        EngineFinder.scanRunning = false
        if (s.startSeq > EngineFinder.coveredSeq) EngineFinder.coveredSeq = s.startSeq
        for (c in changes) if (c !in satisfied && (s.startTime >= c.time || c.coveredByScan == s.id)) satisfied.add(c)
        svc.scanFinished()
    }
}
object EngineFinder {
    lateinit var w: World
    var scanRunning = false
    val changeSeq = AtomicLong(1)
    var coveredSeq = 0L
    fun scanOwed(): Boolean = changeSeq.get() > coveredSeq
    fun reset(world: World) { w = world; scanRunning = false; changeSeq.set(1); coveredSeq = 0L }
    fun runningScanWillRead(pkg: String): Boolean = scanRunning && w.cur?.pending?.contains(pkg) == true
    fun isInstalled(ctx: Any?, pkg: String): Boolean = w.installed[pkg] == true
    fun isSelfEngine(pkg: String) = false
    fun scanLanguages(ctx: Any?, yieldToSpeech: Boolean = false, onResult: (Set<String>) -> Unit) { w.startScan(byService = true) }
}
interface Svc {
    fun packageEvent(pkg: String)
    fun walkDone()
    fun appClosed()
    fun scanFinished()
}

// ---- SVC TEMPLATE BEGIN ----
class XSvc(val w: World) : Svc {
    val destroyed = false
    val applicationContext: Any? = null
    val packageName = "us"
    var startupScanDone = false
    var rescanWanted = false
    var initializingIndex = 0
    val walkList = listOf("walk not finished, so onScanFinished adds nothing")
    val engineList = ArrayList<String>()
    val enginePool = ArrayList<EngineWrapper>()
    class EngineWrapper(val pkg: String) { var state = 0 }
    fun wrapperFor(p: String): EngineWrapper? = null
    fun bindEngineKeepAlive(p: String) {}
    fun restoreEngine(p: String, why: String) {}
    fun onMainThread(block: () -> Unit) = block()
    fun appOnScreen(): Boolean = w.appOpen
    //MEMBERS//
    override fun packageEvent(pkg: String) { //PKGCALL//
    }
    override fun walkDone() { startupScan() }
    override fun appClosed() { screensClosedObserver.onStop(FakeOwner()) }
    override fun scanFinished() { onScanFinished() }
}
// ---- SVC TEMPLATE END ----
//SERVICES//

class Result(val gaps: Int, val wasted: Int, val serviceScans: Int, val changes: Int, val intrusive: Int)
fun runOne(seed: Int, newCode: Boolean, neg: String): Result {
    val w = World(seed)
    EngineFinder.reset(w)
    val svc: Svc = if (newCode) NewSvc(w) else OldSvc(w)
    w.svc = svc
    // the process starting is the first change; any scan started after it covers it
    w.changes.add(Change(-1, null, -1))
    var walkDone = false
    val steps = 10 + w.r.nextInt(60)
    for (i in 0 until steps) {
        w.now = i
        when (w.r.nextInt(10)) {
            0, 1 -> {   // a TTS engine installed, updated or removed
                val p = w.pkgs[w.r.nextInt(3)]
                if (w.r.nextInt(4) == 0) w.installed[p] = !(w.installed[p] ?: true)
                val running = w.cur
                val covered = if (running != null && (running.pending.contains(p) || w.installed[p] != true)) running.id else -1
                w.changes.add(Change(i, p, covered))
                svc.packageEvent(p)
            }
            2 -> if (!w.appOpen) {   // the app opens: on the main screen (it scans) or elsewhere
                w.appOpen = true
                if (w.r.nextInt(3) != 0 && neg != "appnoscan") w.startScan(byService = false)
            }
            3 -> if (w.appOpen) { w.appOpen = false; svc.appClosed() }
            4 -> if (!walkDone) { walkDone = true; svc.walkDone() }
            else -> w.step()
        }
    }
    // settle: the walk ends, the app closes, every scan runs to its end
    w.now = steps
    if (!walkDone) svc.walkDone()
    if (w.appOpen) { w.appOpen = false; svc.appClosed() }
    var guard = 0
    while (w.cur != null && guard++ < 10000) w.step()
    val gaps = w.changes.count { it !in w.satisfied }
    if (gaps > 0 && System.getenv("EV_SHOW") == "1" && newCode) {
        println("seed $seed unscanned: " + w.changes.filter { it !in w.satisfied }.map { "t=${it.time} ${it.pkg} cov=${it.coveredByScan}" } + " owed=${EngineFinder.scanOwed()} seq=${EngineFinder.changeSeq.get()} covered=${EngineFinder.coveredSeq}")
    }
    return Result(gaps, w.wasted, w.serviceScans, w.changes.size, w.intrusive)
}

fun main(args: Array<String>) {
    val n = args.getOrNull(0)?.toInt() ?: 200000
    val neg = System.getenv("EV_NEG") ?: ""
    for (newCode in listOf(false, true)) {
        var gaps = 0; var gapRuns = 0; var wasted = 0; var scans = 0; var changes = 0; var intrusive = 0
        for (seed in 1..n) {
            val r = runOne(seed, newCode, neg)
            gaps += r.gaps; if (r.gaps > 0) gapRuns++; wasted += r.wasted; scans += r.serviceScans; changes += r.changes; intrusive += r.intrusive
        }
        println((if (newCode) "new" else "old") + ": $n histories, $changes changes -- left unscanned: $gaps (in $gapRuns histories); service scans $scans, of them needless: $wasted, started while the app was open or over a running scan: $intrusive")
        if (newCode && (gaps > 0 || wasted > 0 || intrusive > 0)) kotlin.system.exitProcess(1)
    }
}
