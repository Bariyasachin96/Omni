package com.sachinbaria.easyvoice
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
    //
    // TWO-LETTER CODES, as Settings sends (2026-09-24, owner: "Vocalizer ... Hindi
    // ke liye configure ... test button ... English ke liye bol raha hai"). Settings
    // passes the voice's Locale.getLanguage()/getCountry(), which for the locales
    // Android builds are "hi" / "IN". Some engines name their voices with
    // three-letter locales ("hin" / "IND"), and passing those back unchanged asked
    // the engine for a language it does not recognise under that spelling -- and
    // an engine that does not recognise the request answers with its default
    // sentence, which is English. So the codes are turned into the two-letter
    // form first; a code with no two-letter form is sent as it is.
    @JvmStatic fun intentFor(pkg: String, locale: Locale): Intent {
        val intent = Intent(android.speech.tts.TextToSpeech.Engine.ACTION_GET_SAMPLE_TEXT)
        intent.putExtra("language", IsoCodes.toIso2(EngineFinder.iso3Of(locale)) ?: locale.language)
        intent.putExtra("country", countryIso2(locale.country))
        intent.putExtra("variant", locale.variant)
        intent.setPackage(pkg)
        return intent
    }
    private fun countryIso2(country: String): String {
        if (country.length != 3) return country
        return try {
            Locale.getISOCountries().firstOrNull { localeOf("", it).isO3Country.equals(country, true) } ?: country
        } catch (_: Exception) { country }
    }
    // THE ENGINE'S SENTENCE IS USED ONLY WHEN IT IS WRITTEN IN THE LANGUAGE'S OWN
    // SCRIPT. An engine that does not know the language it was asked for can still
    // answer LANG_AVAILABLE with its default sentence, and the owner then heard
    // the Hindi voice read English. The table's own sample for the language says
    // which script to expect (Devanagari for Hindi, Latin for French); the
    // engine's text must be mostly in that script, counted with the platform's
    // Character.UnicodeScript. The Han, kana and Hangul scripts count as one, since
    // Japanese mixes them and either can lead. With no table sample there is
    // nothing to compare against, and the engine's sentence is taken as it is.
    // Not caught: an English sentence for another Latin-script language -- the
    // script cannot tell those apart.
    @JvmStatic fun fitsLanguage(text: String, locale: Locale): Boolean {
        val expected = dominantScript(SampleTexts.get(EngineFinder.iso3Of(locale))) ?: return true
        return dominantScript(text) == expected
    }
    private fun dominantScript(text: String): Character.UnicodeScript? {
        val counts = HashMap<Character.UnicodeScript, Int>()
        var offset = 0
        while (offset < text.length) {
            val cp = text.codePointAt(offset)
            offset += Character.charCount(cp)
            var script = try { Character.UnicodeScript.of(cp) } catch (_: Exception) { continue }
            if (script == Character.UnicodeScript.COMMON || script == Character.UnicodeScript.INHERITED ||
                script == Character.UnicodeScript.UNKNOWN) continue
            if (script == Character.UnicodeScript.HIRAGANA || script == Character.UnicodeScript.KATAKANA ||
                script == Character.UnicodeScript.HANGUL || script == Character.UnicodeScript.BOPOMOFO) script = Character.UnicodeScript.HAN
            counts[script] = (counts[script] ?: 0) + 1
        }
        return counts.maxByOrNull { it.value }?.key
    }
}
