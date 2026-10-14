// The simulator's clock and the synthesis thread's baton (directplay harness).
package sim

import java.util.PriorityQueue
import java.util.concurrent.Semaphore

class Ev(val t: Double, val seq: Long, val run: () -> Unit)

class Clock {
    @JvmField var now = 0.0
    private var seq = 0L
    @JvmField val q = PriorityQueue<Ev>(compareBy<Ev>({ it.t }, { it.seq }))
    fun at(t: Double, r: () -> Unit) { q.add(Ev(if (t < now) now else t, seq++, r)) }
}

// The screen reader's ONE synthesis thread, as a real thread that runs only
// while it holds the baton: it gives it back exactly where the real one would
// block (a wait on the gate, a blocking write into a full AudioTrack), and the
// scheduler gives it back when that wait would end. So the real code runs, in
// a deterministic order, on virtual time.
class Baton(val clock: Clock) {
    private val toSim = Semaphore(0)
    private val toSched = Semaphore(0)
    private var thread: Thread? = null
    @Volatile private var task: (() -> Unit)? = null
    @Volatile var blocked = false; private set
    @Volatile var busy = false; private set
    @Volatile private var episode = 0L
    @Volatile var failure: Throwable? = null
    var onFinish: (() -> Unit)? = null

    private fun loop() {
        while (true) {
            toSim.acquire()
            val t = task ?: continue
            task = null
            try { t() } catch (ex: Throwable) { if (failure == null) failure = ex }
            busy = false; blocked = false
            toSched.release()
        }
    }
    fun isSimThread() = Thread.currentThread() === thread
    fun start(t: () -> Unit) {
        check(!busy) { "the synthesis thread is busy" }
        task = t; busy = true; blocked = false
        if (thread == null) thread = Thread { loop() }.apply { isDaemon = true; start() }
        toSim.release(); toSched.acquire()
        if (!busy) onFinish?.invoke()
    }
    fun block(until: Double? = null) {
        check(isSimThread()) { "only the synthesis thread blocks" }
        val ep = ++episode
        blocked = true
        if (until != null) clock.at(until) { resume(ep) }
        toSched.release(); toSim.acquire()
        blocked = false
    }
    fun wake() { if (blocked && !isSimThread()) { val ep = episode; clock.at(clock.now) { resume(ep) } } }
    private fun resume(ep: Long) {
        if (!blocked || ep != episode) return
        toSim.release(); toSched.acquire()
        if (!busy) onFinish?.invoke()
    }
}

// What the fakes need from the run in progress.
object Sim {
    lateinit var clock: Clock
    lateinit var baton: Baton
    // a run of frames heard: track, rate, channels, frame value, count, from, to, volume
    var heard: ((Any, Int, Int, Int, Long, Double, Double, Float) -> Unit)? = null
    var tracksMade = 0
    var minBufferMs = 20.0
    var startLatency = 4.0      // threshold met -> first frame out
    var markerLatency = 1.0     // marker reached -> its event delivered
    var tick = 10.0             // a running track's progress is accounted this often
    var trackCreate = 8.0       // building an AudioTrack (AudioFlinger's createTrack)
}
