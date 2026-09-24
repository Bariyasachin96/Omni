package com.sachinbaria.easyvoice
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import java.util.Locale
class CheckVoiceData : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // GUARDED 2026-09-11. This activity is EXPORTED and is launched by every
        // screen reader and every TTS settings screen on the device, so a throw
        // here is an "app has stopped" dialog on somebody else's screen -- and for
        // a blind user a dialog they did not ask for is the worst kind of noise.
        // finish() must run whatever happens, or the caller waits on a result that
        // never arrives; setResult must too, or the caller is told nothing.
        //
        // The RESULT stays CHECK_VOICE_DATA_PASS on every path, and that is
        // deliberate rather than lazy: this app answers with an empty list in a
        // cold process already -- AutoTTS's n.i(null, true) loads nothing either,
        // and that is recorded parity -- so an empty list is a state callers
        // already handle. Answering FAIL would be a NEW outcome: it tells the
        // caller to install voice data, and we declare no INSTALL_TTS_DATA
        // activity for it to launch.
        val data = Intent()
        val available = try {
            LangStore.availableLanguagesFor(null, true)
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "CheckVoiceData failed: " + ex.toString())
            ArrayList<String>()
        }
        data.putStringArrayListExtra(TextToSpeech.Engine.EXTRA_AVAILABLE_VOICES, available)
        setResult(TextToSpeech.Engine.CHECK_VOICE_DATA_PASS, data)
        finish()
    }
}

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
        val locale = if (langExtra != null) localeOf(langExtra) else Locale.getDefault()
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
        val text = if (sample.isEmpty()) "Sorry. Sample text for language ${locale.getDisplayName(localeOf("eng"))} is missing." else sample
        val data = Intent()
        data.putExtra(TextToSpeech.Engine.EXTRA_SAMPLE_TEXT, text)
        setResult(TextToSpeech.LANG_AVAILABLE, data)
        finish()
    }
}
