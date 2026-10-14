// android.media as SpeechOut uses it, on the simulator's virtual clock. The
// AudioTrack model follows AudioTrack.java's own contract: a streaming track
// starts only once its buffer holds its start threshold (= capacity) after
// play() or an underrun; pause() stops the sound where it is and returns a
// blocked write at once; flush() (paused or stopped only) drops what is
// buffered and resets the head position to zero; a marker fires once the head
// reaches it, on the listener's handler.
package android.media

import java.nio.ByteBuffer
import sim.Sim

class AudioFormat private constructor(val sampleRate: Int, val encoding: Int, val channelMask: Int) {
    class Builder {
        private var r = 0; private var e = ENCODING_PCM_16BIT; private var m = CHANNEL_OUT_MONO
        fun setSampleRate(v: Int) = apply { r = v }
        fun setEncoding(v: Int) = apply { e = v }
        fun setChannelMask(v: Int) = apply { m = v }
        fun build() = AudioFormat(r, e, m)
    }
    companion object {
        const val ENCODING_DEFAULT = 1; const val ENCODING_PCM_16BIT = 2; const val ENCODING_PCM_8BIT = 3; const val ENCODING_PCM_FLOAT = 4
        const val CHANNEL_OUT_MONO = 4; const val CHANNEL_OUT_STEREO = 12
    }
}

class AudioAttributes private constructor(val usage: Int, val content: Int, val stream: Int) {
    class Builder {
        private var u = 0; private var c = 0; private var s = -1
        fun setUsage(v: Int) = apply { u = v }
        fun setContentType(v: Int) = apply { c = v }
        fun setLegacyStreamType(v: Int) = apply { s = v }
        fun build() = AudioAttributes(u, c, s)
    }
    override fun equals(other: Any?) = other is AudioAttributes && other.usage == usage && other.content == content && other.stream == stream
    override fun hashCode() = usage * 31 + content * 7 + stream
    override fun toString() = "attrs(u=$usage,c=$content,s=$stream)"
    companion object { const val USAGE_ASSISTANCE_ACCESSIBILITY = 11; const val CONTENT_TYPE_SPEECH = 1 }
}

class AudioTrack private constructor(val attributes: AudioAttributes, fmt: AudioFormat, bufferBytes: Int, val session: Int) {
    interface OnPlaybackPositionUpdateListener {
        fun onMarkerReached(track: AudioTrack?)
        fun onPeriodicNotification(track: AudioTrack?)
    }
    class Builder {
        private var a: AudioAttributes? = null; private var f: AudioFormat? = null; private var b = 0; private var s = 0
        fun setAudioAttributes(v: AudioAttributes) = apply { a = v }
        fun setAudioFormat(v: AudioFormat) = apply { f = v }
        fun setBufferSizeInBytes(v: Int) = apply { b = v }
        fun setTransferMode(@Suppress("UNUSED_PARAMETER") v: Int) = apply { }
        fun setSessionId(v: Int) = apply { s = v }
        fun build(): AudioTrack {
            Sim.tracksMade++
            if (Sim.baton.isSimThread()) Sim.baton.block(Sim.clock.now + Sim.trackCreate)
            return AudioTrack(a!!, f!!, b, s)
        }
    }
    companion object {
        const val MODE_STREAM = 1; const val STATE_INITIALIZED = 1; const val STATE_UNINITIALIZED = 0
        const val WRITE_BLOCKING = 0; const val WRITE_NON_BLOCKING = 1
        const val SUCCESS = 0; const val ERROR = -1; const val ERROR_INVALID_OPERATION = -3
        private fun frameBytesOf(mask: Int, enc: Int) = (if (mask == AudioFormat.CHANNEL_OUT_STEREO) 2 else 1) *
            (when (enc) { AudioFormat.ENCODING_PCM_8BIT -> 1; AudioFormat.ENCODING_PCM_FLOAT -> 4; else -> 2 })
        @JvmStatic fun getMinBufferSize(rate: Int, mask: Int, enc: Int): Int =
            maxOf(64, (rate * Sim.minBufferMs / 1000.0).toInt()) * frameBytesOf(mask, enc)
    }

    val rate = fmt.sampleRate
    val channels = if (fmt.channelMask == AudioFormat.CHANNEL_OUT_STEREO) 2 else 1
    private val sampleBytes = when (fmt.encoding) { AudioFormat.ENCODING_PCM_8BIT -> 1; AudioFormat.ENCODING_PCM_FLOAT -> 4; else -> 2 }
    private val frameBytes = sampleBytes * channels
    private val capacity = bufferBytes / frameBytes
    var released = false; private set
    val state: Int get() = if (released) STATE_UNINITIALIZED else STATE_INITIALIZED
    val bufferCapacityInFrames: Int get() = capacity
    var playState = 1; private set              // 1 stopped, 2 paused, 3 playing
    private var running = false                 // consuming frames
    private var startAt = 0.0
    private val queue = ArrayDeque<LongArray>()  // [frame value, frames]
    private var buffered = 0L
    private var head = 0L
    private var lastT = Sim.clock.now
    private var frac = 0.0
    private var volume = 1f
    private var marker = 0L
    private var markerFired = true
    private var listener: OnPlaybackPositionUpdateListener? = null
    private var version = 0L
    private var writerBlocked = false
    var underruns = 0; private set

    private fun advance() {
        val now = Sim.clock.now
        if (running && now > lastT) {
            val from = maxOf(lastT, startAt)
            if (now > from) {
                val budget = (now - from) * rate / 1000.0 + frac
                var whole = budget.toLong(); frac = budget - whole
                var t = from
                while (whole > 0 && queue.isNotEmpty()) {
                    val r = queue.first()
                    val take = minOf(whole, r[1])
                    val dt = take * 1000.0 / rate
                    Sim.heard?.invoke(this, rate, channels, r[0].toInt(), take, t, t + dt, volume)
                    t += dt; r[1] -= take; whole -= take; buffered -= take; head += take
                    if (r[1] == 0L) queue.removeFirst()
                }
                if (buffered == 0L) { running = false; frac = 0.0; underruns++ }
            }
        }
        lastT = now
    }
    private fun maybeStart() {
        if (playState == 3 && !running && buffered >= capacity) {
            running = true; startAt = Sim.clock.now + Sim.startLatency; lastT = Sim.clock.now; frac = 0.0
            tickLater()
        }
    }
    // While it runs, its progress is accounted every Sim.tick (the heard hook
    // sees frames close to when they are heard).
    private var tickChain = 0L
    private fun tickLater() {
        val chain = ++tickChain
        fun next() {
            Sim.clock.at(Sim.clock.now + Sim.tick) {
                if (released || !running || chain != tickChain) return@at
                advance(); rescheduleMarker()
                if (running) next()
            }
        }
        next()
    }
    private fun rescheduleMarker() {
        version++
        if (marker <= 0 || markerFired) return
        if (head >= marker) { fire(); return }
        if (!running) return
        val ahead = marker - head
        val reach = minOf(ahead, buffered)
        val t = maxOf(Sim.clock.now, startAt) + reach * 1000.0 / rate
        val v = version
        Sim.clock.at(t + 1e-9) { if (v == version && !released) { advance(); rescheduleMarker() } }
    }
    private fun fire() {
        markerFired = true
        val l = listener ?: return
        val me = this
        Sim.clock.at(Sim.clock.now + Sim.markerLatency) { if (!me.released) l.onMarkerReached(me) }
    }

    fun play() { check(!released); advance(); playState = 3; maybeStart(); rescheduleMarker() }
    fun pause() {
        check(!released); advance(); playState = 2; running = false; rescheduleMarker()
        if (writerBlocked) Sim.baton.wake()
    }
    fun stop() { check(!released); advance(); playState = 1; rescheduleMarker(); if (writerBlocked) Sim.baton.wake() }
    fun flush() {
        if (released || playState == 3) return
        queue.clear(); buffered = 0; head = 0; frac = 0.0; rescheduleMarker()
    }
    fun release() { released = true; running = false; queue.clear(); buffered = 0; version++; if (writerBlocked) Sim.baton.wake() }
    val playbackHeadPosition: Int get() { advance(); return head.toInt() }
    fun setNotificationMarkerPosition(m: Int): Int {
        if (released) return ERROR_INVALID_OPERATION
        advance(); marker = m.toLong(); markerFired = m <= 0; rescheduleMarker(); return SUCCESS
    }
    fun setPlaybackPositionUpdateListener(l: OnPlaybackPositionUpdateListener?, @Suppress("UNUSED_PARAMETER") h: android.os.Handler? = null) { listener = l }
    @Deprecated("as the platform's") fun setStereoVolume(left: Float, @Suppress("UNUSED_PARAMETER") right: Float): Int { advance(); volume = left; return SUCCESS }

    fun write(data: ByteBuffer, size: Int, mode: Int): Int {
        if (released) return ERROR_INVALID_OPERATION
        val total = size / frameBytes
        val base = data.position()
        var done = 0
        while (true) {
            advance(); maybeStart()
            val space = (capacity - buffered).toInt()
            val n = minOf(space, total - done)
            if (n > 0) {
                for (i in 0 until n) {
                    val off = base + (done + i) * frameBytes
                    val v = (data.get(off).toInt() and 0xff) or (data.get(off + 1).toInt() shl 8)
                    val last = queue.lastOrNull()
                    if (last != null && last[0] == v.toLong()) last[1]++ else queue.addLast(longArrayOf(v.toLong(), 1))
                }
                buffered += n; done += n
                maybeStart(); rescheduleMarker()
            }
            if (done == total || mode == WRITE_NON_BLOCKING) break
            if (playState != 3 || released) break
            val need = minOf((total - done).toLong(), capacity.toLong())
            val until = if (running) maxOf(Sim.clock.now, startAt) + maxOf(0.5, need * 1000.0 / rate) else null
            writerBlocked = true
            Sim.baton.block(until)
            writerBlocked = false
            if (released) break
        }
        data.position(base + done * frameBytes)
        return done * frameBytes
    }
}
