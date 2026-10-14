package com.sachinbaria.easyvoice

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.os.Handler
import android.os.HandlerThread
import android.os.SystemClock
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.PI
import kotlin.math.sin

// The direct player's speaker on a REAL AudioTrack (CI's emulator). The
// simulator (tools/verify/directplay) proves the order of everything on a fake
// track; this proves the parts only the platform can: a track is built for each
// exact format (no resampling), a chunk shorter than the buffer still starts,
// the drain returns once the last real frame is heard -- not before -- and an
// interrupt stops a blocking write at once.
@RunWith(AndroidJUnit4::class)
class SpeechOutTest {
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private val lock = java.lang.Object()
    private lateinit var events: HandlerThread
    private lateinit var out: SpeechOut
    private val speech = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
        .build()

    @Before fun setUp() {
        events = HandlerThread("SpeechOutTest").apply { start() }
        out = SpeechOut(MonitorGate(lock), Handler(events.looper))
        out.begin(speech, AudioManager.AUDIO_SESSION_ID_GENERATE, 0f)
    }

    @After fun tearDown() {
        out.interrupt()
        out.release()
        events.quitSafely()
    }

    /** A quiet 440 Hz tone, [ms] long, in the given format. */
    private fun tone(rate: Int, ms: Int, enc: Int, ch: Int): ByteArray {
        val frames = rate * ms / 1000
        val bytes = when (enc) { AudioFormat.ENCODING_PCM_8BIT -> 1; AudioFormat.ENCODING_PCM_FLOAT -> 4; else -> 2 }
        val buf = ByteBuffer.allocate(frames * ch * bytes).order(ByteOrder.LITTLE_ENDIAN)
        for (i in 0 until frames) {
            val v = 0.1 * sin(2 * PI * 440 * i / rate)
            repeat(ch) {
                when (enc) {
                    AudioFormat.ENCODING_PCM_8BIT -> buf.put((128 + v * 127).toInt().toByte())
                    AudioFormat.ENCODING_PCM_FLOAT -> buf.putFloat(v.toFloat())
                    else -> buf.putShort((v * 32767).toInt().toShort())
                }
            }
        }
        return buf.array()
    }

    /** Runs [block] on its own thread and fails if it is not back within [limitMs]. */
    private fun <T> within(limitMs: Long, what: String, block: () -> T): T {
        val result = AtomicReference<Any?>(NONE)
        val t = Thread { result.set(try { block() } catch (e: Throwable) { e }) }
        t.start()
        t.join(limitMs)
        val r = result.get()
        if (r === NONE) { out.interrupt(); t.join(2000); fail("$what did not return within $limitMs ms") }
        if (r is Throwable) throw r
        @Suppress("UNCHECKED_CAST")
        return r as T
    }

    @Test fun everyFormatPlaysToItsLastFrame() {
        val formats = listOf(
            Triple(16000, AudioFormat.ENCODING_PCM_16BIT, 1),
            Triple(24000, AudioFormat.ENCODING_PCM_16BIT, 1),
            Triple(11025, AudioFormat.ENCODING_PCM_8BIT, 1),
            Triple(22050, AudioFormat.ENCODING_PCM_FLOAT, 2),
            Triple(48000, AudioFormat.ENCODING_PCM_16BIT, 2),
            Triple(16000, AudioFormat.ENCODING_PCM_16BIT, 1),   // a kept track, used again
        )
        for ((rate, enc, ch) in formats) {
            val ms = 300
            val audio = tone(rate, ms, enc, ch)
            val started = SystemClock.elapsedRealtime()
            val heard = within(5000, "$rate Hz") {
                check(out.select(rate, enc, ch)) { "no track for $rate Hz, encoding $enc, $ch ch" }
                out.setVolume(1f)
                // In three pieces, the first two cut mid-frame: a frame the engine
                // splits waits for its other half.
                val a = audio.size / 3 + 1
                val b = 2 * audio.size / 3 + 1
                check(out.write(audio.copyOfRange(0, a)) && out.write(audio.copyOfRange(a, b)) && out.write(audio.copyOfRange(b, audio.size)))
                out.drainAndPark()
            }
            val took = SystemClock.elapsedRealtime() - started
            assertTrue("$rate Hz: drained", heard)
            // Heard to the end: the drain cannot come back before the audio's own length.
            assertTrue("$rate Hz: back after $took ms, the audio is $ms ms", took >= ms * 8 / 10)
        }
    }

    @Test fun aChunkShorterThanTheBufferStillPlays() {
        // 20 ms -- far below any track's start threshold.
        val audio = tone(24000, 20, AudioFormat.ENCODING_PCM_16BIT, 1)
        val heard = within(3000, "a 20 ms chunk") {
            check(out.select(24000, AudioFormat.ENCODING_PCM_16BIT, 1))
            check(out.write(audio))
            out.drainAndPark()
        }
        assertTrue(heard)
    }

    @Test fun anInterruptStopsAWriteAtOnce() {
        val audio = tone(16000, 4000, AudioFormat.ENCODING_PCM_16BIT, 1)
        val wrote = AtomicReference<Boolean?>(null)
        val writer = Thread {
            wrote.set(out.select(16000, AudioFormat.ENCODING_PCM_16BIT, 1) && out.write(audio))
        }
        writer.start()
        Thread.sleep(300)
        val at = SystemClock.elapsedRealtime()
        out.interrupt()
        writer.join(1000)
        assertFalse("the write was still blocked 1 s after the interrupt", writer.isAlive)
        assertTrue("back ${SystemClock.elapsedRealtime() - at} ms after the interrupt", SystemClock.elapsedRealtime() - at < 500)
        assertFalse("the write said it finished a 4 s chunk", wrote.get() ?: true)
        assertTrue(out.interrupted)
        assertFalse(out.drainAndPark())
        out.park()
        // The next utterance plays on the same track again.
        out.begin(speech, AudioManager.AUDIO_SESSION_ID_GENERATE, 0f)
        val again = within(3000, "after an interrupt") {
            check(out.select(16000, AudioFormat.ENCODING_PCM_16BIT, 1))
            check(out.write(tone(16000, 200, AudioFormat.ENCODING_PCM_16BIT, 1)))
            out.drainAndPark()
        }
        assertTrue(again)
    }

    private companion object { val NONE = Any() }
}
