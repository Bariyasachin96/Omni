package com.sachinbaria.easyvoice

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Handler
import java.nio.ByteBuffer

// ==========================================================================
//  EASY VOICE'S OWN SPEAKER (owner, 2026-10-14) -- see SpeechPlayer.kt for
//  why the engines' audio is played here now.
//
//  ONE TRACK PER FORMAT, KEPT. A track is built for the exact sample rate,
//  encoding and channel count the engine announced (onBeginSynthesis), with
//  the screen reader's own audio attributes and session -- what AOSP's
//  PlaybackSynthesisCallback would have built for it (AudioOutputParams). It
//  is paused and emptied at the end of an utterance and used again by the
//  next one in that format, instead of a new AudioTrack per utterance, which
//  is what AOSP's playback does (SynthesisPlaybackQueueItem builds a
//  BlockingAudioTrack every time). At most MAX_TRACKS are kept.
//
//  THE START THRESHOLD, read in AudioTrack.java (play(), and
//  getStartThresholdInFrames): a streaming track does not start until its
//  buffer holds enough data, "also true when resuming audio playback after
//  calling stop() ... after the track underruns". So a chunk shorter than the
//  buffer would never start, and the last piece after an underrun would never
//  play. At the end (drain) the buffer is topped up with SILENCE, written
//  non-blocking -- the track then starts or resumes, plays the real audio,
//  and the marker at the last real frame says it has been heard. The silence
//  after it is dropped with pause()+flush(), so it is never heard.
//
//  HEARD TO THE END: setNotificationMarkerPosition at the last frame written,
//  delivered by the track itself on its own event thread -- an event, not a
//  clock (AOSP's BlockingAudioTrack polls the position with sleeps instead).
//  The head position is reset to zero by flush() (AudioTrack.java,
//  getPlaybackHeadPosition), which is why a track is always flushed before it
//  is used again: its frames and its head count from the same zero.
//
//  INTERRUPTED: pause() from the screen reader's binder thread, inside our
//  onStop(). A blocking write() returns at once ("another thread interrupts
//  the write by calling stop or pause", AudioTrack.write), and pause() stops
//  the sound where it is. The synthesis thread empties the track when it
//  leaves.
// ==========================================================================
class SpeechOut(private val gate: Gate, private val events: Handler) : SpeechSink {
    private class Track(
        val audio: AudioTrack,
        val sampleRate: Int,
        val encoding: Int,
        val channels: Int,
        val attributes: AudioAttributes,
        val session: Int,
        val frameBytes: Int,
        var silence: ByteArray,
    ) {
        /** Frames written since the last flush; the head counts from the same zero. */
        var written = 0L
        /** play() has been called since the last pause. */
        var started = false
        var volume = Float.NaN
        var pan = Float.NaN
        /** Bytes of a frame cut in two by the engine, kept for the next piece. */
        var carry: ByteArray? = null
        var dead = false
        /** The frame the drain waits for; 0 when no drain is waiting. */
        var markerFrame = 0L
        var reached = false
        fun fits(rate: Int, enc: Int, ch: Int, attrs: AudioAttributes, sessionId: Int) =
            !dead && sampleRate == rate && encoding == enc && channels == ch && attributes == attrs && session == sessionId
    }

    private val tracks = ArrayList<Track>()           // the synthesis thread only
    @Volatile private var current: Track? = null
    @Volatile private var draining: Track? = null
    @Volatile private var stopNow = false
    override val interrupted: Boolean get() = stopNow
    private var attributes: AudioAttributes? = null
    private var session = 0
    private var pan = 0f

    // Delivered on [events], the speaker's own thread. A marker event of an
    // EARLIER drain can arrive after a new one was set (one already posted is
    // not taken back), so the head is checked against the marker we want.
    private val positionListener = object : AudioTrack.OnPlaybackPositionUpdateListener {
        override fun onMarkerReached(track: AudioTrack?) {
            val t = draining ?: return
            if (track == null || track !== t.audio) return
            val head = try { track.playbackHeadPosition.toLong() and 0xffffffffL } catch (_: Throwable) { return }
            gate.locked {
                if (t.markerFrame > 0 && head >= t.markerFrame) { t.reached = true; gate.signalAll() }
            }
        }
        override fun onPeriodicNotification(track: AudioTrack?) {}
    }

    /** At the start of every utterance: the screen reader's attributes, session and pan. */
    fun begin(attrs: AudioAttributes, sessionId: Int, panning: Float) {
        attributes = attrs
        session = sessionId
        pan = panning
        stopNow = false
    }

    /** The screen reader interrupted (our onStop, a binder thread): silence now. */
    fun interrupt() {
        stopNow = true
        val t = current
        if (t != null) try { t.audio.pause() } catch (_: Throwable) {}
        gate.locked { gate.signalAll() }
    }

    override fun select(sampleRate: Int, encoding: Int, channels: Int): Boolean {
        val attrs = attributes ?: return false
        val enc = if (encoding == AudioFormat.ENCODING_DEFAULT) AudioFormat.ENCODING_PCM_16BIT else encoding
        val cur = current
        // A new chunk starts on a frame boundary: half a frame left over from the
        // chunk before (one that failed part-way can leave one) is dropped, never
        // joined to this one -- joined, every sample after it would be read a
        // byte off, which is noise.
        if (cur != null) cur.carry = null
        if (cur != null && cur.fits(sampleRate, enc, channels, attrs, session)) return true
        if (cur != null) {
            // Another format: the chunk before is heard to its last frame first.
            val heard = drain(cur)
            current = null
            if (!heard) return false
        }
        val found = tracks.indexOfFirst { it.fits(sampleRate, enc, channels, attrs, session) }
        val t = if (found >= 0) tracks.removeAt(found) else create(sampleRate, enc, channels, attrs) ?: return false
        tracks.add(t)
        while (tracks.size > MAX_TRACKS) release(tracks.removeAt(0))
        current = t
        return !stopNow
    }

    // BlockingAudioTrack.setupVolume, AOSP's own math for the request's volume
    // and pan: the pan takes the far side down, never the near one up.
    override fun setVolume(volume: Float) {
        val t = current ?: return
        if (t.volume == volume && t.pan == pan) return
        t.volume = volume; t.pan = pan
        val vol = volume.coerceIn(0f, 1f)
        val p = pan.coerceIn(-1f, 1f)
        var left = vol
        var right = vol
        if (p > 0f) left *= (1f - p) else if (p < 0f) right *= (1f + p)
        @Suppress("DEPRECATION")
        try { t.audio.setStereoVolume(left, right) } catch (_: Throwable) {}
    }

    override fun write(audio: ByteArray): Boolean {
        val t = current ?: return false
        if (stopNow) return false
        if (!t.started) {
            try { t.audio.play() } catch (_: Throwable) { lose(t); return false }
            t.started = true
            // The pause in interrupt() may have landed between the test above and
            // play(): it is checked again, so a stopped utterance never restarts.
            if (stopNow) { try { t.audio.pause() } catch (_: Throwable) {}; return false }
        }
        // Whole frames only: a frame the engine cut in two waits for the rest.
        var data = audio
        val carry = t.carry
        if (carry != null) { data = carry + audio; t.carry = null }
        var len = data.size
        val rest = len % t.frameBytes
        if (rest != 0) { t.carry = data.copyOfRange(len - rest, len); len -= rest }
        var off = 0
        while (off < len) {
            if (stopNow) return false
            val n = try { t.audio.write(ByteBuffer.wrap(data, off, len - off), len - off, AudioTrack.WRITE_BLOCKING) } catch (_: Throwable) { -1 }
            if (n <= 0) {
                if (stopNow) return false
                lose(t)
                return false
            }
            off += n
            t.written += (n / t.frameBytes).toLong()
        }
        return true
    }

    override fun drainAndPark(): Boolean {
        val t = current ?: return !stopNow
        return drain(t)
    }

    override fun park() {
        val t = current ?: return
        park(t)
    }

    /** onDestroy: every track is released. */
    fun release() {
        current = null
        for (t in tracks) release(t)
        tracks.clear()
    }

    private fun drain(t: Track): Boolean {
        if (!t.started || t.written == 0L || t.dead) { park(t); return !stopNow }
        val target = t.written
        gate.locked { t.reached = false; t.markerFrame = target }
        draining = t
        try {
            val set = try { t.audio.setNotificationMarkerPosition(target.toInt()) } catch (_: Throwable) { AudioTrack.ERROR }
            if (set != AudioTrack.SUCCESS) { lose(t); return !stopNow }
            // Enough data for the track to start (or resume after an underrun):
            // silence after the last real frame, as much as fits, never blocking.
            // Sized by the buffer as it is NOW -- a track moved to another
            // output (Bluetooth) may have been given a larger one.
            val capacity = try { t.audio.bufferCapacityInFrames } catch (_: Throwable) { 0 }
            if (capacity * t.frameBytes > t.silence.size) {
                t.silence = ByteArray(capacity * t.frameBytes)
                if (t.encoding == AudioFormat.ENCODING_PCM_8BIT) t.silence.fill(0x80.toByte())
            }
            try { t.audio.write(ByteBuffer.wrap(t.silence), t.silence.size, AudioTrack.WRITE_NON_BLOCKING) } catch (_: Throwable) {}
            gate.locked { while (!t.reached && !stopNow && !t.dead) gate.await() }
        } finally {
            draining = null
            gate.locked { t.markerFrame = 0L }
        }
        park(t)
        return !stopNow
    }

    private fun park(t: Track) {
        try { t.audio.pause() } catch (_: Throwable) {}
        try { t.audio.flush() } catch (_: Throwable) {}
        t.started = false
        t.written = 0L
        t.carry = null
    }

    // The track can no longer be written (its audio server connection is gone):
    // it is released, and the next chunk in this format builds a new one.
    private fun lose(t: Track) {
        t.dead = true
        if (current === t) current = null
        tracks.remove(t)
        release(t)
        gate.locked { gate.signalAll() }
    }

    private fun release(t: Track) {
        try { t.audio.setPlaybackPositionUpdateListener(null) } catch (_: Throwable) {}
        try { t.audio.release() } catch (_: Throwable) {}
    }

    private fun create(rate: Int, enc: Int, ch: Int, attrs: AudioAttributes): Track? {
        val mask = when (ch) { 1 -> AudioFormat.CHANNEL_OUT_MONO; 2 -> AudioFormat.CHANNEL_OUT_STEREO; else -> return null }
        val sampleBytes = when (enc) {
            AudioFormat.ENCODING_PCM_8BIT -> 1
            AudioFormat.ENCODING_PCM_16BIT -> 2
            AudioFormat.ENCODING_PCM_FLOAT -> 4
            else -> return null
        }
        val min = try { AudioTrack.getMinBufferSize(rate, mask, enc) } catch (_: Throwable) { -1 }
        if (min <= 0) return null
        val audio = try {
            AudioTrack.Builder()
                .setAudioAttributes(attrs)
                .setAudioFormat(AudioFormat.Builder().setSampleRate(rate).setEncoding(enc).setChannelMask(mask).build())
                .setBufferSizeInBytes(min * 2)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .setSessionId(session)
                .build()
        } catch (_: Throwable) { return null }
        if (audio.state != AudioTrack.STATE_INITIALIZED) { try { audio.release() } catch (_: Throwable) {}; return null }
        val frameBytes = sampleBytes * ch
        val capacity = try { audio.bufferCapacityInFrames } catch (_: Throwable) { min * 2 / frameBytes }
        // 8-bit PCM is unsigned: its silence is 0x80, not 0.
        val silence = ByteArray(capacity * frameBytes)
        if (enc == AudioFormat.ENCODING_PCM_8BIT) silence.fill(0x80.toByte())
        try { audio.setPlaybackPositionUpdateListener(positionListener, events) } catch (_: Throwable) {}
        return Track(audio, rate, enc, ch, attrs, session, frameBytes, silence)
    }

    companion object {
        /** Formats kept ready. Three covers the engines one utterance mixes. */
        const val MAX_TRACKS = 3
    }
}
