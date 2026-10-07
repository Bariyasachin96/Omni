package com.sachinbaria.easyvoice

// ==========================================================================
//  A BACKGROUND SCAN READS AN ENGINE ONLY WHILE NOTHING IS BEING SPOKEN
//  (owner, 2026-10-07: "logically ... performance bahut hi fast").
//
//  The scan the service runs on its own -- once per process start, on a TTS
//  package change, when the app's screens close -- asks every engine for its
//  whole voice set. That is the most expensive call an engine answers, and
//  right after a cold start it landed while the screen reader's first
//  utterances were being synthesised by the same engines.
//
//  The order is decided by what each piece of work is FOR: speech is what the
//  user is waiting for, the scan is bookkeeping nobody is waiting for. So a
//  background scan's walk takes its next engine only when no utterance is in
//  flight; otherwise that step is parked and is run by the event that frees
//  the engines -- an utterance ending (EasyVoiceTtsService, the end of
//  onSynthesizeText). No clock decides anything here.
//
//  The scan's own 180 s watchdog bounds a walk that is STUCK, and a walk that
//  is waiting for speech to end is not stuck, so the watchdog counts walking
//  time only: it is taken down while a step is parked and put back with what
//  was left when the step runs.
//
//  A scan the user started (the main screen) does not yield: its screen is
//  what they are waiting for. The overlap left is at most ONE engine read --
//  one that started in a gap between two utterances; a read in flight cannot
//  be taken back.
//
//  Pure Kotlin on purpose: tools/verify/scanyield/run.sh compiles this very
//  file against a virtual clock.
// ==========================================================================
class ScanYield(
    private val yieldToSpeech: Boolean,
    private val budgetMs: Long,
    private val now: () -> Long,
    private val armWatchdog: (Long) -> Unit,
    private val disarmWatchdog: () -> Unit,
) {
    private var walkedMs = 0L
    private var walkingSince = -1L
    private var done = false

    /** The walk starts: the watchdog gets the whole budget. */
    fun start() {
        walkingSince = now()
        armWatchdog(budgetMs)
    }

    /** The walk is over (finalized, or a newer scan took over). */
    fun finish() {
        done = true
        disarmWatchdog()
        synchronized(parked) { parked.remove(this) }
    }

    /** Runs the walk's next step now, or parks it until speech ends. */
    fun step(run: () -> Unit) {
        if (done) return
        if (yieldToSpeech && speechInFlight()) {
            if (walkingSince >= 0) { walkedMs += now() - walkingSince; walkingSince = -1L }
            disarmWatchdog()
            synchronized(parked) { parked[this] = run }
            return
        }
        if (walkingSince < 0) {
            walkingSince = now()
            armWatchdog(maxOf(0L, budgetMs - walkedMs))
        }
        run()
    }

    companion object {
        /** Set by the service: is an utterance being synthesised right now? */
        @JvmStatic @Volatile var speechInFlight: () -> Boolean = { false }
        // At most one parked step per scan (a walk has one next step).
        private val parked = LinkedHashMap<ScanYield, () -> Unit>()

        /** Is a step waiting? (So an utterance end posts nothing when none is.) */
        @JvmStatic fun hasParked(): Boolean = synchronized(parked) { parked.isNotEmpty() }

        /** An utterance ended: the parked steps run, unless another began. Main thread. */
        @JvmStatic fun speechEnded() {
            if (speechInFlight()) return
            val steps = synchronized(parked) { val list = parked.map { it.key to it.value }; parked.clear(); list }
            for ((scan, run) in steps) {
                try { scan.step(run) } catch (ex: Throwable) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "parked scan step: " + ex.toString())
                }
            }
        }
    }
}
