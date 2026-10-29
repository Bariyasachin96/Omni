package com.sachinbaria.easyvoice

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

// ML KIT, ASKED BY THE NATIVE CORE, ON A DEVICE (owner, 2026-10-29: "اخلاق" in a
// Hindi sentence went to the Urdu voice on the phone while the same build's core,
// with ML Kit's own library on a desktop, gives it Arabic). The desktop harnesses
// hand the core ML Kit's library directly; this runs the whole path a phone runs:
// processDirect in mix mode -> the core -> its JNI upcall (installed in
// JNI_OnLoad) -> EasyVoiceTtsService.mlkitIdentify -> ML Kit -> back. The
// owner's languages {en, hi, gu, ur, ar}; "اخلاق" is spelled the same in Urdu
// and Arabic, so only ML Kit's answer (Arabic, 0.635) names it, and "ضروری" is
// Urdu by its letter ی. Run off the main thread, as the synthesis thread is.
@RunWith(AndroidJUnit4::class)
class MlKitCoreTest {
    @Test
    fun guestWordsInAHindiSentenceTakeTheirOwnLanguage() {
        EasyVoiceTtsService.setDetectSets(arrayOf("ara", "eng", "guj", "hin", "urd"), arrayOf("ar", "en", "gu", "hi", "ur"))
        EasyVoiceTtsService.setLanguageHints(arrayOf("ar", "en", "gu", "hi", "ur"))
        val out = EasyVoiceTtsService.processDirect(
            "ज्ञान के साथ अच्छे اخلاق और जिम्मेदारी भी ضروری हैं।",
            "en", "hi", "mix", 0, "", 0, "", 0, "", true, false, 1, "en", 1, false, true, false)
        val spans = spansOf(out)
        assertEquals("spans: $spans", "ar", spans.firstOrNull { it.second == "اخلاق" }?.first)
        assertEquals("spans: $spans", "ur", spans.firstOrNull { it.second == "ضروری" }?.first)
        assertEquals("spans: $spans", "hi", spans.firstOrNull { it.second.startsWith("ज्ञान") }?.first)
    }

    // processDirect's records: a field count, then type, kind, lang, text, the
    // fold ("" = the text) and language / latin / text triples ("" = the fold).
    // Each span's language and trimmed text.
    private fun spansOf(out: Array<String>): List<Pair<String, String>> {
        val spans = mutableListOf<Pair<String, String>>()
        var at = 0
        while (at < out.size) {
            val n = out[at].toIntOrNull() ?: break
            if (n < 0 || at + 1 + n > out.size) break
            val first = at + 1
            if (n >= 5) {
                val fold = out[first + 4].ifEmpty { out[first + 3] }
                var t = first + 5
                while (t + 2 < first + n) {
                    spans.add(out[t] to out[t + 2].ifEmpty { fold }.trim())
                    t += 3
                }
            }
            at += 1 + n
        }
        return spans
    }
}
