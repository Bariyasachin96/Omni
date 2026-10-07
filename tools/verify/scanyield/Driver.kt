// See run.sh. Appended to the REAL ScanYield.kt (package line stripped).
object EasyVoiceLogger { const val TAG = "t"; fun error(t: String, m: String) {} }

class Ev(val t: Long, val seq: Long, val run: () -> Unit)
class Clock {
    var now = 0L; var seq = 0L
    val q = java.util.PriorityQueue<Ev>(compareBy<Ev>({ it.t }, { it.seq }))
    fun at(t: Long, r: () -> Unit) { q.add(Ev(t, seq++, r)) }
    fun loop(limit: Int = 100000) { var n = 0; while (q.isNotEmpty() && n++ < limit) { val e = q.poll(); now = e.t; e.run() } }
}

fun main(args: Array<String>) {
    val n = args.getOrNull(0)?.toInt() ?: 100000
    var readsDuringSpeech = 0; var notFinished = 0; var watchdogWrong = 0; var watchdogMissed = 0
    var parkedSteps = 0; var userScanParked = 0; var scans = 0
    var speechMs = 0L; var pastBudget = 0
    for (seed in 1..n) {
        val r = java.util.Random(seed.toLong())
        val c = Clock()
        // speech: utterances, back to back or with gaps
        var speaking = false
        ScanYield.speechInFlight = { speaking }
        val nUtt = r.nextInt(12)
        var t = r.nextInt(500).toLong()
        val utts = ArrayList<Pair<Long, Long>>()
        repeat(nUtt) {
            val dur = 50L + r.nextInt(if (r.nextInt(5) == 0) 120000 else 4000)
            utts.add(Pair(t, t + dur)); t += dur + listOf(0L, 1L, 30L, 400L, 5000L)[r.nextInt(5)]
        }
        for ((s, e) in utts) {
            c.at(s) { speaking = true }
            c.at(e) { speaking = false; if (ScanYield.hasParked()) c.at(c.now + 1) { ScanYield.speechEnded() } }
        }
        // one scan: a background one (yields) or the user's (does not)
        val yieldy = r.nextInt(4) != 0
        val engines = 1 + r.nextInt(5)
        // each engine read takes this long; a stuck one takes forever (30 s per-engine timeout cuts it)
        val reads = (0 until engines).map { when (r.nextInt(15)) { 0 -> 30000L; 1 -> 100000L; else -> 5L + r.nextInt(400) } }
        val budget = 180000L
        var watchdogAt = -1L; var watchdogToken = 0
        var finished = false; var firedWatchdog = false
        var walkTime = 0L
        lateinit var walk: ScanYield
        walk = ScanYield(yieldy, budget, { c.now },
            { ms -> val tok = ++watchdogToken; watchdogAt = c.now + ms; c.at(c.now + ms) { if (tok == watchdogToken && !finished) { firedWatchdog = true; finished = true; walk.finish() } } },
            { watchdogToken++; watchdogAt = -1L })
        scans++
        var index = 0
        lateinit var readEngine: () -> Unit
        readEngine = {
            if (yieldy && speaking) readsDuringSpeech++
            val i = index; val d = reads[i]
            val start = c.now
            walkTime += d
            c.at(start + d) {
                if (!finished) {
                    index++
                    if (index >= engines) { finished = true; walk.finish() }
                    else walk.step { if (!finished) readEngine() }
                }
            }
        }
        c.at(r.nextInt(300).toLong()) {
            walk.start()
            val before = ScanYield.hasParked()
            walk.step { if (!finished) readEngine() }
            if (!before && ScanYield.hasParked()) { parkedSteps++; if (!yieldy) userScanParked++ }
        }
        c.loop()
        speechMs += utts.sumOf { it.second - it.first }
        if (!finished) notFinished++
        if (firedWatchdog && walkTime <= budget) watchdogWrong++
        if (walkTime > budget + 1000) pastBudget++
        if (!firedWatchdog && walkTime > budget + 1000) watchdogMissed++
        ScanYield.speechInFlight = { false }; ScanYield.speechEnded()
    }
    println("$scans scans, $speechMs ms of speech")
    println("  engine reads started while an utterance was in flight (background scans): $readsDuringSpeech")
    println("  scans that never finished: $notFinished; user scans that waited for speech: $userScanParked")
    println("  watchdog fired with walking time inside the budget: $watchdogWrong; not fired past it: $watchdogMissed (of $pastBudget walks past it)")
    val ok = readsDuringSpeech == 0 && notFinished == 0 && userScanParked == 0 && watchdogWrong == 0 && watchdogMissed == 0
    println(if (ok) "PASS" else "FAIL")
    if (!ok) kotlin.system.exitProcess(1)
}
