package com.sachinbaria.easyvoice.baselineprofile

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

// What a person's phone actually runs, recorded so ART compiles it ahead of time
// on install instead of interpreting it on the first utterances (owner,
// 2026-10-07). Two halves, the same two a blind user meets:
//   1. the app opened: MainActivity, its engine scan, the mode list;
//   2. the TTS service speaking, reached the way a screen reader reaches it --
//      a TextToSpeech client bound to our engine -- in the mixed, auto and dual
//      modes, over short labels and longer mixed-script text.
// Every step tolerates a device that lacks something (no voice engine, a label
// not found): the profile then simply covers less, and the run still passes.
@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {
    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(
        packageName = TARGET,
        includeInStartupProfile = true,
        // Bounded: one pass speaks for a minute or two, and the rule's default
        // (up to 15 passes until 3 agree) could outlast the CI job.
        maxIterations = 5,
        stableIterations = 2,
    ) {
        pressHome()
        startActivityAndWait()
        // The first open scans the installed engines; the mode list appears when
        // it is done.
        device.wait(Until.hasObject(By.desc(MIXED)), SCAN_WAIT_MS)
        for (mode in listOf(MIXED, AUTO, DUAL)) {
            tap(device, mode)
            device.waitForIdle()
            // onPause writes the mode the service reads at its next start.
            pressHome()
            speakAll(TEXTS)
            startActivityAndWait()
            device.wait(Until.hasObject(By.desc(mode)), SCAN_WAIT_MS)
        }
        pressHome()
    }

    private fun tap(device: UiDevice, label: String) {
        (device.findObject(By.desc(label)) ?: device.findObject(By.text(label)))?.click()
    }

    private fun speakAll(texts: List<String>) {
        val context = InstrumentationRegistry.getInstrumentation().context
        val ready = CountDownLatch(1)
        var status = TextToSpeech.ERROR
        val tts = TextToSpeech(context, { s -> status = s; ready.countDown() }, TARGET)
        try {
            if (!ready.await(INIT_WAIT_S, TimeUnit.SECONDS) || status != TextToSpeech.SUCCESS) return
            val finished = CountDownLatch(texts.size)
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) { finished.countDown() }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) { finished.countDown() }
                override fun onError(utteranceId: String?, errorCode: Int) { finished.countDown() }
                override fun onStop(utteranceId: String?, interrupted: Boolean) { finished.countDown() }
            })
            texts.forEachIndexed { i, text -> tts.speak(text, TextToSpeech.QUEUE_ADD, null, "bp$i") }
            finished.await(PER_TEXT_WAIT_S * texts.size, TimeUnit.SECONDS)
        } finally {
            tts.shutdown()
        }
    }

    private companion object {
        const val TARGET = "com.sachinbaria.easyvoice"
        const val MIXED = "Mixed mode"
        const val AUTO = "Auto language detect"
        const val DUAL = "Dual languages"
        const val SCAN_WAIT_MS = 90_000L
        const val INIT_WAIT_S = 30L
        const val PER_TEXT_WAIT_S = 10L
        // Screen-reader-shaped: labels, a time, a number, an emoji, then
        // paragraphs mixing Latin, Devanagari and Gujarati.
        val TEXTS = listOf(
            "Settings", "Button", "12:45", "Battery 87 percent", "नमस्ते", "કેમ છો",
            "New message 😀 from Ravi",
            "आज की मीटिंग 3:30 बजे है, please join on time.",
            "Hello दुनिया, આ એક પરીક્ષણ છે. Call 98765 43210 for details!",
            "This is a longer English sentence with numbers like 1,250 and 3.5, " +
                "followed by हिंदी में एक पूरा वाक्य और ગુજરાતીમાં પણ એક વાક્ય.",
        )
    }
}
