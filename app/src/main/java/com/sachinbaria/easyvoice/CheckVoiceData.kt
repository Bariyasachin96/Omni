package com.sachinbaria.easyvoice
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
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
