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
        val sample = SampleTexts.get(locale.isO3Language)
        val text = if (sample.isEmpty()) "Sorry. Sample text for language ${locale.getDisplayName(Locale("eng"))} is missing." else sample
        val data = Intent()
        data.putExtra("sampleText", text)
        setResult(TextToSpeech.LANG_AVAILABLE, data)
        finish()
    }
}
