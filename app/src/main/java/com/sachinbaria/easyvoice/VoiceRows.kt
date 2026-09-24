package com.sachinbaria.easyvoice
import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import android.content.Intent
object VoiceRows {
    private fun voiceKey(row: EngineFinder.ScanVoice?, selectedIso: String): String =
        if (row == null) "Disable#" + localeOf(selectedIso).toString() else row.pkg + "#" + row.locale.toString()
    fun label(row: EngineFinder.ScanVoice?): String {
        if (row == null) return "*Disabled"
        val abbreviated = abbreviateEngineName(row.engineName)
        val base = if (row.locale.country.isNotEmpty()) abbreviated + ", " + row.locale.getDisplayCountry() else abbreviated
        return if (row.notFound) base + " (not found in the last scan)" else base
    }
    fun load(context: Context, readingMode: String, selectedIso: String): List<EngineFinder.ScanVoice?> {
        if (selectedIso.isEmpty()) { LangStore.currentVoiceRows = emptyList(); return emptyList() }
        LangStore.currentVoiceIso = selectedIso
        LangStore.currentVoiceChosen = false
        val rawPrefs = LangStore.prefs(context)
        fun voiceOrder(key: String): Int {
            val cached = EngineFinder.voiceWeights[key]
            if (cached != null) return cached
            // toIntOrNull, and a try, because this reads STORAGE rather than a
            // literal: `.toInt()` threw NumberFormatException on anything that is
            // not a number, and `getString` throws ClassCastException outright if
            // the key was ever written with putInt. Both land inside the Voice
            // setup screen's composition, so they are a crash on a screen the
            // owner opens once per language.
            //
            // 1000 is what a MISSING key already
            // answers -- "unranked" -- so a corrupt entry now sorts last instead
            // of taking the screen down, and valid data behaves exactly as before.
            val storedWeight = try {
                (rawPrefs.getString(key, "1000") ?: "1000").toIntOrNull() ?: 1000
            } catch (_: Exception) { 1000 }
            EngineFinder.voiceWeights[key] = storedWeight
            return storedWeight
        }
        val allVoices = EngineFinder.lastScanVoices.filter { EngineFinder.iso3Of(it.locale) == selectedIso }
        val filtered = if (readingMode == "google") allVoices.filter { it.pkg.equals(EngineFinder.builtInEngine, true) } else allVoices
        val rows = ArrayList<EngineFinder.ScanVoice?>(filtered)
        val autoModeC = EasyVoiceTtsService.autoLang
        val mixLatinIso3 = EasyVoiceTtsService.mixLatinLang
        val mixNonLatinIso3 = EasyVoiceTtsService.mixNonLatinLang
        if (readingMode == "auto" && selectedIso != autoModeC) rows.add(null)
        if ((readingMode == "mix" || readingMode == "multilingual") && selectedIso != mixLatinIso3 && selectedIso != mixNonLatinIso3) rows.add(null)
        fun tieBreakLabel(row: EngineFinder.ScanVoice?): String = if (row == null) "*Disabled" else abbreviateEngineName(row.engineName)
        val sorted = rows.sortedWith(Comparator { rowA, rowB ->
            val orderA = voiceOrder(voiceKey(rowA, selectedIso))
            val orderB = voiceOrder(voiceKey(rowB, selectedIso))
            if (orderA != orderB) orderA - orderB else tieBreakLabel(rowA).compareTo(tieBreakLabel(rowB), ignoreCase = true)
        })
        val stored = try { rawPrefs.getString(selectedIso, "") ?: "" } catch (_: Exception) { "" }
        val pinned = pinConfigured(context, sorted, stored, selectedIso, readingMode)
        LangStore.currentVoiceRows = pinned
        var weightIdx = 0
        while (weightIdx < pinned.size) { EngineFinder.voiceWeights[voiceKey(pinned[weightIdx], selectedIso)] = weightIdx; weightIdx++ }
        return pinned
    }
    // THE CONFIGURED VOICE IS ALWAYS THE FIRST ROW (2026-09-23, owner: "Google
    // ... uska voice sab ko chala jata hai ... aisa kuchh karo ki vah jaaye hi
    // na").
    //
    // The first row IS the selection: the screen shows it as the chosen voice,
    // and LangStore.persistVoiceRows writes it back as "<iso3> = <pkg>#<locale>"
    // on every persist -- every onPause, and before EVERY Previous/Next on Voice
    // setup. The rows were built only from what the last scan could read and
    // ordered only by the stored weights, so whenever the configured engine was
    // not read (Google failing a scan, a process restored straight onto this
    // screen with no scan in it) or its weight was missing, some other row came
    // first and was written over the configuration. Walking the languages with
    // Next did that to every one of them in a row -- all of Google's languages
    // reassigned or disabled without the owner touching anything.
    //
    // So the stored configuration decides the first row: the matching row is
    // moved up, and a configured voice the scan did not return is shown anyway,
    // marked as not found. Persisting then writes back exactly what was stored.
    // Only picking a different voice (moveToFront) changes the configuration.
    // With nothing configured the weight order stands, as before.
    private fun pinConfigured(context: Context, rows: List<EngineFinder.ScanVoice?>, stored: String, selectedIso: String,
                              readingMode: String): List<EngineFinder.ScanVoice?> {
        val parts = stored.split("#")
        if (parts.size < 2 || parts[0].isEmpty()) return rows
        val out = ArrayList<EngineFinder.ScanVoice?>(rows)
        if (parts[0].equals("disable", true)) {
            val at = out.indexOf(null)
            if (at > 0) out.add(0, out.removeAt(at))
            return out
        }
        if (readingMode == "google" && !parts[0].equals(EngineFinder.builtInEngine, true)) return rows
        val at = out.indexOfFirst { it != null && it.pkg == parts[0] && it.locale.toString() == parts[1] }
        if (at == 0) return rows
        if (at > 0) { out.add(0, out.removeAt(at)); return out }
        val locale = EngineFinder.parseStoredLocale(parts[1])
        if (EngineFinder.iso3Of(locale) != selectedIso) return rows
        val engineName = EngineFinder.engineLabel(context, parts[0])
        val variants = arrayListOf("*Default")
        val savedVariant = try { LangStore.prefs(context)
            .getString(selectedIso + "_variant", "*Default") ?: "*Default" } catch (_: Exception) { "*Default" }
        if (savedVariant.isNotEmpty() && savedVariant != "*Default") variants.add(savedVariant)
        out.add(0, EngineFinder.ScanVoice(parts[0], engineName, locale, variants, notFound = true))
        return out
    }
    fun moveToFront(rows: List<EngineFinder.ScanVoice?>, position: Int, selectedIso: String, entry: LangEntry): List<EngineFinder.ScanVoice?> {
        val reordered = ArrayList<EngineFinder.ScanVoice?>(rows)
        val picked = reordered.removeAt(position)
        reordered.add(0, picked)
        LangStore.currentVoiceRows = reordered
        LangStore.currentVoiceChosen = true
        var weightIdx = 0
        while (weightIdx < reordered.size) { EngineFinder.voiceWeights[voiceKey(reordered[weightIdx], selectedIso)] = weightIdx; weightIdx++ }
        if (picked != null) { entry.enginePkg = picked.pkg; entry.localeTag = picked.locale.toString() }
        return reordered
    }
    fun variantsFor(rows: List<EngineFinder.ScanVoice?>, entry: LangEntry): List<String> {
        if (rows.isEmpty()) return emptyList()
        val picked = rows[0]
        if (picked == null) return emptyList()
        val base = picked.variants.toList()
        val slotCount = base.size
        if (slotCount == 0) return emptyList()
        val ordered = Array(slotCount) { "" }
        val savedVariant = entry.variant
        if (savedVariant.isNotEmpty()) {
            ordered[0] = savedVariant
            var nextSlot = 1
            for (index in base.indices) {
                if (base[index] != savedVariant && nextSlot < slotCount) { ordered[nextSlot] = base[index]; nextSlot++ }
            }
        } else {
            for (slotIdx in base.indices) ordered[slotIdx] = base[slotIdx]
        }
        if (slotCount > 1) java.util.Arrays.sort(ordered, 1, slotCount - 1)
        return ordered.toList()
    }
    // THE SAMPLE COMES FROM THE ENGINE FIRST NOW (owner, 2026-09-16). See
    // EngineSample for why it is an activity round trip and why the table below
    // still has to exist.
    //
    // `sampleProvider` answers three ways, and the middle one is what makes the
    // window change happen at most once per language:
    //     a non-empty string -> the engine's own sentence, speak it
    //     ""                 -> asked already, the engine had none, use the table
    //     null               -> it has gone to ASK; do not speak, it will call
    //                           `retry` when the answer lands
    // Passing no provider at all -- which every test and every caller that is not
    // VoiceSetupActivity does -- is byte for byte the behaviour of this method
    // before that date.
    //
    // `retry` is this same call with this same arguments, so the second pass
    // finds the cache populated and speaks. It cannot loop: the provider always
    // writes the key before replaying, even when the engine answered nothing.
    fun speakTest(
        rows: List<EngineFinder.ScanVoice?>,
        entry: LangEntry,
        selectedIso: String,
        testClient: TextToSpeech?,
        sampleProvider: ((String, Locale, () -> Unit) -> String?)? = null
    ) {
        val row = rows.firstOrNull()
        if (testClient == null || row == null) return
        val voiceLocale = row.locale
        val qualityTag = entry.variant
        val fromEngine = if (sampleProvider == null) "" else {
            val retry = { speakTest(rows, entry, selectedIso, testClient, sampleProvider) }
            sampleProvider(row.pkg, voiceLocale, retry) ?: return
        }
        val sample = if (fromEngine.isNotEmpty()) fromEngine
                     else SampleTexts.get(EngineFinder.iso3Of(voiceLocale))
        val spoken = if (sample.isEmpty())
            "Sorry. Sample text for language " + (try { voiceLocale.getDisplayName(localeOf("eng")) } catch (_: Exception) { selectedIso }) + " is missing."
        else "[EasyVoice:" + row.pkg + ":" + voiceLocale.toString() + ":" + qualityTag + "]" + sample
        testClient.speak(spoken, TextToSpeech.QUEUE_FLUSH, null, "EasyVoice_Test")
    }
}

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

fun abbreviateEngineName(name: String): String {
    val parts = name.split(" ").dropLastWhile { it.isEmpty() }
    if (parts.size < 2) return name
    val headBuf = StringBuilder(parts[0])
    val tailBuf = StringBuilder(parts[parts.size - 1])
    var headIdx = 1
    var tailIdx = parts.size - 2
    var best = "$headBuf ... $tailBuf"
    while (headIdx < tailIdx) {
        if (best.length > 15) return best
        val candidate = "$headBuf ... $tailBuf"
        if (candidate.length > 15) return if (candidate.length >= 20) best else candidate
        if (parts[headIdx].length < parts[tailIdx].length) { headBuf.append(" ").append(parts[headIdx]); headIdx++ }
        else { tailBuf.insert(0, parts[tailIdx] + " "); tailIdx-- }
        best = candidate
    }
    return if (name.length >= 15) best else name
}
fun voiceLanguageLabels(context: Context, modeInt: Int): List<String> {
    val autoIso3 = EasyVoiceTtsService.autoLang
    val dualIso3 = EasyVoiceTtsService.dualLang
    val mixLatinIso3 = EasyVoiceTtsService.mixLatinLang
    val mixNonLatinIso3 = EasyVoiceTtsService.mixNonLatinLang
    val required = LangStore.requiredLangs(modeInt, autoIso3, dualIso3, mixLatinIso3, mixNonLatinIso3)
    LangStore.persistLanguages(context)
    LangStore.replaceAll(
        if (modeInt == 1) LangStore.dualLangList(context, dualIso3, EngineFinder.lastScanVoices)
        else LangStore.rebuildFromScan(context, true, modeInt, required, EngineFinder.lastScanVoices)
    )
    // c3.k.Y2 rebuilds the list the same way and finishes with s0().
    EasyVoiceTtsService.pushLanguageSets()
    return when (modeInt) {
        2, 4, 5 -> LangStore.availableLanguagesFor(null, false)
        3 -> LangStore.availableLanguagesFor(EngineFinder.builtInEngine, false)
        else -> LangStore.dualLanguageLabels(dualIso3)
    }
}
// Parallel to voiceLanguageLabels: same order, same filters, one entry per
// label -- the chosen engine's package, or "" when none was chosen.
// LangEntry.enginePkg is written ONLY by VoiceRows.moveToFront, i.e. only when
// the user actually picks a voice, so it is exactly the "did I set this
// language up" signal. The two branches mirror availableLanguagesFor and
// dualLanguageLabels line for line, so the lists cannot drift out of step.
fun voiceLanguageEngines(modeInt: Int): List<String> {
    val out = ArrayList<String>()
    val dualIso3 = EasyVoiceTtsService.dualLang
    val pkgFilter = if (modeInt == 3) EngineFinder.builtInEngine else null
    // Under the list's own monitor, like every walk inside LangStore. This one
    // is on the main thread and the writer it races is the SYNTHESIS thread
    // inside reloadLanguagesIfMissing, so an indexed walk here could read a size
    // that a replaceAll had already shrunk. See LangStore.replaceAll.
    synchronized(LangStore.languages) {
        var index = 0
        while (index < LangStore.languages.size) {
            val entry = LangStore.languages[index]
            index++
            if (modeInt == 2 || modeInt == 3 || modeInt == 4 || modeInt == 5) {
                if (pkgFilter != null && !entry.enginePkgs.contains(pkgFilter) || entry.disabled) continue
            } else {
                if (!entry.iso3.equals("eng", true) && !entry.iso3.equals(dualIso3, true)) continue
            }
            val pkg = entry.enginePkg
            out.add(if (pkg.isNotEmpty() && !pkg.equals("disable", true)) pkg else "")
        }
    }
    return out
}
