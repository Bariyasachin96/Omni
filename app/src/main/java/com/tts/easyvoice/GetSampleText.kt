package com.tts.easyvoice
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import java.util.Locale
class GetSampleText : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // getStringExtra UNPARCELS THE WHOLE BUNDLE, and this activity is exported
        // (2026-09-11). Any app on the phone can send an extra carrying a class
        // this process cannot load, and Bundle.unparcel then throws
        // BadParcelableException or a RuntimeException wrapping
        // ClassNotFoundException -- an "app has stopped" dialog triggered by
        // somebody else's intent. Reading it defensively costs one try and the
        // fallback is the device locale, which is what a caller that names no
        // language gets anyway.
        val langExtra = try { intent?.getStringExtra("language") } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "GetSampleText extra unreadable: " + ex.toString()); null
        }
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
