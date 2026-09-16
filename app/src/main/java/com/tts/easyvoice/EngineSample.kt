package com.tts.easyvoice
import android.content.Intent
import java.util.Locale
// THE SAMPLE SENTENCE COMES FROM THE ENGINE NOW (owner, 2026-09-16):
// "har TTS ke paas vah sample text rahata hi hai ... hamen alag se likhne ki
// jarurat nahin hai."
//
// They are right, and it is the PLATFORM'S OWN PATTERN: Android Settings does
// exactly this in `TextToSpeechSettings.getSampleText()` -- fire
// ACTION_GET_SAMPLE_TEXT at the engine, read EXTRA_SAMPLE_TEXT off the result.
//
// THREE THINGS FROM THE AOSP SOURCE DECIDE THE SHAPE, and each is why this file
// is bigger than "ask the engine":
//
//  1. IT IS AN ACTIVITY, NOT A BINDER CALL. `ACTION_GET_SAMPLE_TEXT` carries
//     `@SdkConstant(SdkConstantType.ACTIVITY_INTENT_ACTION)`, so the only way to
//     read it is startActivityForResult and a result callback. There is no
//     background API and no method on TextToSpeech -- checked, not assumed.
//  2. THE ANSWER IS OPTIONAL. The Javadoc says the result *may* contain
//     EXTRA_SAMPLE_TEXT, and Settings catches ActivityNotFoundException, because
//     an engine need not declare the activity at all. So `SampleTexts` STAYS as
//     the fallback -- asking the engine cannot delete it, and Settings keeps its
//     own canned table for the same reason.
//  3. AOSP hedges on the request side: "This is currently a hidden private API.
//     The intent extras and the intent action should be made public if we intend
//     to make this a public API. We fall back to using a canned set of strings if
//     this doesn't work." The extras below are the ones its own Javadoc documents
//     and the ones Settings sends.
//
// WHY IT IS CACHED, and this is the accessibility decision rather than a
// performance one. An activity round trip is a WINDOW CHANGE, and a screen
// reader speaks a window's title when a window appears -- this app has spent
// three sessions making each screen introduce itself exactly once. Caching per
// engine+locale means the round trip happens on the FIRST Test for a language
// and never again for the life of the process: every later press is byte for
// byte the instant, silent path it is today.
//
// An empty string is cached deliberately and means "asked, the engine had
// nothing" -- so a language whose engine declines is not re-asked on every
// press. Only a genuine failure to launch leaves the key absent.
object EngineSample {
    private val cache = HashMap<String, String>()
    @JvmStatic fun key(pkg: String, locale: Locale): String = pkg + "|" + locale.toString()
    // null  = never asked, ask now
    // ""    = asked, engine gave nothing -> use SampleTexts
    // other = the engine's own sentence
    @JvmStatic @Synchronized fun cached(key: String): String? = cache[key]
    @JvmStatic @Synchronized fun put(key: String, text: String) { cache[key] = text }
    // The three extras are what TextToSpeech.Engine.ACTION_GET_SAMPLE_TEXT's own
    // Javadoc lists -- language, country, variant -- and `setPackage` is what
    // aims it at the engine that will actually speak, rather than at whichever
    // engine the system would resolve. Both are copied from Settings.
    @JvmStatic fun intentFor(pkg: String, locale: Locale): Intent {
        val intent = Intent(android.speech.tts.TextToSpeech.Engine.ACTION_GET_SAMPLE_TEXT)
        intent.putExtra("language", locale.language)
        intent.putExtra("country", locale.country)
        intent.putExtra("variant", locale.variant)
        intent.setPackage(pkg)
        return intent
    }
}
