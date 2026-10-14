package com.sachinbaria.easyvoice

// ==========================================================================
//  THE DIRECT PLAYER: EASY VOICE PLAYS THE ENGINES' AUDIO ITSELF
//  (owner, 2026-10-14: "screen reader per hi chhod deni chahie ... smart voice
//  ... processing usse bhi fast honi chahie ... resampling nahin karna hai
//  kisi bhi halat mein ... directly hi jo process hai vah ho jaaye").
//
//  BEFORE: each chunk was handed to its engine with speak(), so the audio
//  played inside the ENGINE'S process. The screen reader's stop reached only
//  Easy Voice, which then had to relay it to whichever engine was talking --
//  a queued stop per engine, generations to keep a late stop off the next
//  utterance, a flush per interrupt, the chunk queued ahead taken back on an
//  error. That relay was the "processing on every swipe" the owner asked about.
//
//  NOW: each chunk goes to its engine with synthesizeToFile() into /dev/null,
//  and the engine's own framework streams the PCM back to us as it is made
//  (UtteranceProgressListener.onBeginSynthesis / onAudioAvailable, API 24).
//  Easy Voice plays it on its own AudioTrack (SpeechOut) -- the path every
//  ordinary TTS engine has. When the screen reader interrupts, Android calls
//  our onStop() in that same instant and the track is paused there: the audio
//  stops in OUR process, nothing has to reach another app first.
//
//  NO RESAMPLING. SmartVoice puts every engine through one output at the
//  phone's native rate and resamples in Java (libresample4j). Here every chunk
//  plays at its engine's own sample rate, encoding and channel count, on a
//  track of exactly that format; a change of format between two chunks
//  (Eloquence at 11 kHz, then Google at 24 kHz) is a change of track. What
//  the speaker needs is done by Android's own mixer, exactly as it was when
//  the engine played the audio itself.
//
//  FASTER THAN SMARTVOICE, AND THAN THE OLD PATH, for three reasons:
//    * every chunk of an utterance is handed to its engine at once (the first
//      on the synthesis thread, the rest on the speak thread), so the next
//      language is synthesised while the current one plays -- SmartVoice waits
//      for each chunk to finish before it asks for the next;
//    * a track is kept and reused for its format, where AOSP's playback (and
//      so SmartVoice, and the engines before) builds a new AudioTrack for
//      every utterance (SynthesisPlaybackQueueItem -> new BlockingAudioTrack);
//    * the track's buffer is twice the platform minimum, so it starts as soon
//      as a few tens of milliseconds of audio are in -- AOSP's TTS buffer is
//      at least 8192 bytes (170 ms at 24 kHz), and a streaming track does not
//      start until its buffer is full.
//
//  THIS FILE IS PURE KOTLIN ON PURPOSE: tools/verify/directplay/run.sh
//  compiles this very file on a virtual clock. The service (EasyVoiceTtsService)
//  hands chunks to engines and fills the slots; this plays them in order.
// ==========================================================================

/**
 * The lock the player waits on. In the service it is the one monitor every
 * waiter already uses (syncLock), so an interrupt that notifies it wakes the
 * player too. The simulator gives a cooperative one.
 */
interface Gate {
    fun <T> locked(block: () -> T): T
    /** Waits for [signalAll]; only inside [locked]. */
    fun await()
    /** Wakes every waiter; only inside [locked]. */
    fun signalAll()
}

/** A [Gate] on a Java monitor: synchronized / wait / notifyAll. */
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class MonitorGate(private val monitor: java.lang.Object) : Gate {
    override fun <T> locked(block: () -> T): T = synchronized(monitor) { block() }
    override fun await() { try { monitor.wait() } catch (_: InterruptedException) {} }
    override fun signalAll() { monitor.notifyAll() }
}

/** TextToSpeech.SUCCESS, the code a stream ends with when its engine said onDone. */
const val STREAM_OK = 0

/**
 * One chunk's audio, as its engine streams it in. Filled by the engine's
 * callbacks (binder threads) and read by the player; every field is read and
 * written under the [Gate].
 */
class PcmStream(
    /** The utterance id the engine was given; its callbacks carry it back. */
    val id: String,
    /** The chunk's place in the utterance. */
    val index: Int,
    /** The engine it was handed to, as Android names it. */
    val rawPkg: String,
    /** The language it is spoken in (ISO 639-2). */
    val lang: String,
    /** The request's volume times the language's own setting, 0..1. */
    val volume: Float,
) {
    var sampleRate = 0
    var encoding = 0
    var channels = 0
    /** onBeginSynthesis arrived: the format is known. */
    var begun = false
    /** onDone, onError or onStop arrived (or the engine's process died). */
    var ended = false
    /** How it ended: [STREAM_OK], or an engine error / TextToSpeech.STOPPED / a death. */
    var code = STREAM_OK
    /** Audio not yet given to the output, oldest first. */
    val pieces = ArrayDeque<ByteArray>()
    /** Bytes of audio the engine has sent, for the log. */
    var bytes = 0L

    fun begin(rate: Int, audioFormat: Int, channelCount: Int) {
        if (begun || ended) return
        sampleRate = rate; encoding = audioFormat; channels = channelCount
        begun = true
    }
    fun append(audio: ByteArray) {
        if (ended || audio.isEmpty()) return
        pieces.addLast(audio); bytes += audio.size
    }
    fun end(how: Int) {
        if (ended) return
        ended = true; code = how
    }
}

/** What the player writes to: SpeechOut on a phone, a fake in the simulator. */
interface SpeechSink {
    /** The screen reader interrupted: everything returns at once. */
    val interrupted: Boolean
    /**
     * Makes a track of this exact format the current one. Audio still playing on
     * a track of another format is heard to its end first. False: this format
     * cannot be played, or the wait was interrupted.
     */
    fun select(sampleRate: Int, encoding: Int, channels: Int): Boolean
    fun setVolume(volume: Float)
    /** Queues the audio, blocking while the track is full. False: interrupted, or the track is gone. */
    fun write(audio: ByteArray): Boolean
    /** Waits until every frame written has been heard, then pauses and empties the track. */
    fun drainAndPark(): Boolean
    /** Pauses and empties the current track now. */
    fun park()
}

/**
 * One utterance's chunks, played in order on the synthesis thread while the
 * service hands them to their engines. [slots] and [endBefore] are written by
 * the service (under the gate) and read here.
 */
class UtterancePlayback(private val gate: Gate, val count: Int, private val host: Host) {
    interface Host {
        /** False once the utterance is over: interrupted, or a newer one began. */
        fun live(): Boolean
        /** The first audio of the utterance is about to play (the callback's start). */
        fun audioStarting()
        /**
         * The engine failed chunk [index]. True when the service has sent it to
         * another engine (the slot is empty again and will be filled); false
         * when it cannot be said, and the utterance ends there.
         */
        fun retry(index: Int, failed: PcmStream): Boolean
        /** After every piece written (the keep-alive's trickle). */
        fun afterWrite()
        fun log(message: () -> String)
    }

    /** How the utterance ended. */
    enum class Ending {
        /** Every chunk heard. */
        DONE,
        /** Interrupted, or a newer utterance began. */
        STOPPED,
        /** A chunk could not be said (no engine, not supported, failed and not handed over). */
        ENDED_EARLY,
        /** The audio output itself failed (a format no track can play, or a dead track). */
        OUTPUT_LOST,
    }

    /** A chunk's stream, once its engine has it; null while it is being handed over. */
    val slots = arrayOfNulls<PcmStream>(count)
    /** The first chunk that cannot be said: the utterance ends before it. */
    var endBefore = count
    /** The chunk the utterance ended at, when it ended early. */
    var endedAt = -1
        private set
    /** The last failure's code, for the log. */
    var failureCode = STREAM_OK
        private set

    /** The service has handed chunk [index] to an engine. */
    fun fill(index: Int, stream: PcmStream) {
        gate.locked { slots[index] = stream; gate.signalAll() }
    }
    /** Chunk [index] cannot be said: nothing from it on is played. */
    fun stopBefore(index: Int) {
        gate.locked { if (index < endBefore) endBefore = index; gate.signalAll() }
    }
    /** Chunk [index] is being handed over again (a retry): its old stream is let go. */
    fun clear(index: Int) {
        gate.locked { slots[index] = null }
    }

    /** Plays every chunk in order. Runs on the synthesis thread and blocks. */
    fun play(sink: SpeechSink): Ending {
        var k = 0
        while (true) {
            // 1. Its stream, once the service has handed the chunk to an engine.
            var stream: PcmStream? = null
            val proceed = gate.locked {
                while (host.live() && !sink.interrupted && k < endBefore && slots[k] == null) gate.await()
                if (!host.live() || sink.interrupted || k >= endBefore) false
                else { stream = slots[k]; true }
            }
            if (!proceed) return if (!host.live() || sink.interrupted) Ending.STOPPED
                else if (k >= count) Ending.DONE else { endedAt = k; Ending.ENDED_EARLY }
            val s = stream!!
            // 2. Its format, or its end without a sound.
            gate.locked {
                while (host.live() && !sink.interrupted && !s.begun && !s.ended) gate.await()
            }
            if (!host.live() || sink.interrupted) return Ending.STOPPED
            if (!s.begun) {
                // Ended before any audio. An engine that answers synthesizeToFile by
                // speaking for itself (another proxy, like AutoTTS) ends this way
                // AFTER it has spoken, which is why the next chunk waited for it.
                if (s.code == STREAM_OK) { k++; continue }
                failureCode = s.code
                if (host.retry(k, s)) continue
                endedAt = k
                return Ending.ENDED_EARLY
            }
            // 3. Its audio, piece by piece, as the engine sends it.
            if (!sink.select(s.sampleRate, s.encoding, s.channels)) {
                if (!host.live() || sink.interrupted) return Ending.STOPPED
                host.log { "no audio track for " + s.sampleRate + " Hz, encoding " + s.encoding + ", " + s.channels + " channel(s)" }
                endedAt = k
                return Ending.OUTPUT_LOST
            }
            sink.setVolume(s.volume)
            host.audioStarting()
            while (true) {
                val piece = gate.locked {
                    while (host.live() && !sink.interrupted && s.pieces.isEmpty() && !s.ended) gate.await()
                    if (!host.live() || sink.interrupted) null else s.pieces.removeFirstOrNull()
                } ?: break
                if (!sink.write(piece)) {
                    if (!host.live() || sink.interrupted) return Ending.STOPPED
                    endedAt = k
                    return Ending.OUTPUT_LOST
                }
                host.afterWrite()
            }
            if (!host.live() || sink.interrupted) return Ending.STOPPED
            // 4. How it ended. A failure part-way is handed over whole, as a chunk
            // failed by its engine always was.
            if (s.code != STREAM_OK) {
                failureCode = s.code
                if (host.retry(k, s)) continue
                endedAt = k
                return Ending.ENDED_EARLY
            }
            k++
        }
    }
}
