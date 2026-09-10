package com.tts.easyvoice
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import java.util.Locale
class GetSampleText : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val langExtra = intent?.getStringExtra("language")
        val locale = if (langExtra != null) Locale(langExtra) else Locale.getDefault()
        // isO3Language THROWS MissingResourceException for a language with no
        // three-letter code -- measured, not assumed: Locale("xx").isO3Language
        // is "Couldn't find 3-letter language code for xx". This activity is
        // exported and its "language" extra comes from ANOTHER app, so an
        // unguarded read is a crash any caller can trigger. AutoTTS's
        // GetSampleText reads it bare as well; this is the same "our refactor is
        // not the point, the phone crashing is" split as the API-26 guards, and
        // the fallback is the sentence the screen already shows for a language
        // it has no sample for. A valid code behaves exactly as before.
        val sample = try { SampleTexts.get(locale.isO3Language) } catch (_: Exception) { "" }
        val text = if (sample.isEmpty()) "Sorry. Sample text for language ${locale.getDisplayName(Locale("eng"))} is missing." else sample
        val data = Intent()
        data.putExtra("sampleText", text)
        setResult(TextToSpeech.LANG_AVAILABLE, data)
        finish()
    }
}
