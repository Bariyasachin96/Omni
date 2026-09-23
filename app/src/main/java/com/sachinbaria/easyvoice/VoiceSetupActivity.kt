package com.sachinbaria.easyvoice
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
class VoiceSetupActivity : EvActivity() {
    private var testTts: android.speech.tts.TextToSpeech? = null
    // State now, not a local, so Previous/Next can move within this one screen.
    private var langIndex by mutableStateOf(-1)
    private var total by mutableStateOf(0)
    // ASKING THE ENGINE FOR ITS OWN SAMPLE (owner, 2026-09-16). The whole
    // reasoning is on EngineSample; what lives here is the one thing only an
    // Activity can do, because ACTION_GET_SAMPLE_TEXT is an ACTIVITY action.
    //
    // `registerForActivityResult` has to run before the Activity is STARTED, so
    // it is assigned in onCreate above setContent -- the documented place.
    private var sampleLauncher: ActivityResultLauncher<Intent>? = null
    private var pendingSampleKey: String? = null
    private var pendingSampleRetry: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This screen persists in onPause, so it must not run with unloaded
        // statics: Android can restore it alone into a fresh process. No-op
        // whenever anything is already loaded -- see LangStore.ensureLoaded.
        LangStore.ensureLoaded(this)
        val prefs = SharedPrefsManager(this)
        testTts = android.speech.tts.TextToSpeech(this, null, "com.sachinbaria.easyvoice")
        sampleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val key = pendingSampleKey; pendingSampleKey = null
            val retry = pendingSampleRetry; pendingSampleRetry = null
            // getStringExtra unparcels the whole Bundle, and this one comes from
            // ANOTHER app -- the same reason GetSampleText reads its own extra
            // inside a try. A class this process cannot load is a
            // BadParcelableException, and here it would land on the Test button.
            val text = try { result.data?.getStringExtra(android.speech.tts.TextToSpeech.Engine.EXTRA_SAMPLE_TEXT) } catch (_: Throwable) { null }
            // "" is written on EVERY answered launch, including a null one, so a
            // declining engine is asked once and never again. Only a launch that
            // never happened leaves the key absent.
            if (key != null) EngineSample.put(key, text ?: "")
            try { retry?.invoke() } catch (_: Throwable) { }
        }
        langIndex = intent.getIntExtra("lang_index", -1)
        val readingMode = prefs.getReadingMode()
        val modeInt = when (readingMode) { "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 0 }
        // Rebuilds LangStore.languages, which is what lang_index indexes, and
        // its size is the list Configuration shows -- the same list to walk.
        total = voiceLanguageLabels(this, modeInt).size
        // applyTitle() is deliberately NOT called here. See its comment: on
        // entry the window already announces the manifest title, and setting a
        // second one during startup meant the screen introduced itself twice.
        setContent {
            EasyVoiceTheme {
                // key() throws away the whole subtree when the language changes.
                // VoiceScreen holds the voice rows, the variant and the three
                // slider values in plain remember blocks; without this they
                // would survive the switch and show the previous language's
                // settings.
                key(langIndex) {
                    VoiceScreen(prefs, langIndex, total, { target ->
                        if (target >= 0 && target < total) {
                            // MUST persist before switching. persistVoiceRows is
                            // the only writer of a language's engine assignment
                            // (`if (index == 0) editor.putString(iso3, key)`) and
                            // it reads LangStore.currentVoiceRows, which holds
                            // one language at a time -- VoiceRows.load replaces
                            // it for the language we are about to move to. Skip
                            // this and everything configured before the last
                            // Next is silently thrown away.
                            LangStore.persistAll(this@VoiceSetupActivity)
                            langIndex = target
                            applyTitle()
                        }
                    }, ::engineSample) { testTts }
                }
            }
        }
    }
    // The resolver VoiceRows.speakTest asks. Three answers, and the middle one is
    // why the owner hears the round trip at most once per language:
    //     ""    -> already asked, the engine had none: use the table
    //     text  -> the engine's own sentence
    //     null  -> gone to ask; speakTest returns without speaking and `retry`
    //              runs it again the moment the result lands
    //
    // ActivityNotFoundException is caught because an engine need not declare the
    // activity at all -- AOSP's own Settings catches exactly this -- and the
    // empty string it then caches means that engine is never asked again.
    private fun engineSample(pkg: String, locale: java.util.Locale, retry: () -> Unit): String? {
        val key = EngineSample.key(pkg, locale)
        val hit = EngineSample.cached(key)
        if (hit != null) return hit
        val launcher = sampleLauncher ?: return ""
        // A launch already in flight: answer from the table rather than stacking
        // a second one, which would lose the first key.
        if (pendingSampleKey != null) return ""
        return try {
            pendingSampleKey = key
            pendingSampleRetry = retry
            launcher.launch(EngineSample.intentFor(pkg, locale))
            null
        } catch (ex: Throwable) {
            if (ex is ActivityNotFoundException) EngineSample.put(key, "")
            pendingSampleKey = null; pendingSampleRetry = null
            ""
        }
    }
    // setTitle fires a window-state-changed event, which is what announces the
    // language you just moved to. See the CLAUDE.md note on why this is used
    // rather than setAccessibilityPaneTitle.
    //
    // ONLY FROM Previous/Next, never on entry. There the window is not changing,
    // so nothing else would say which language you have moved to and this is the
    // whole announcement. On entry the window's own title is already spoken, and
    // adding one during onCreate is what made every screen introduce itself
    // twice.
    private fun applyTitle() {
        val entry = LangStore.entryAt(langIndex)
        if (entry != null) setTitle(entry.displayName + " (" + entry.iso3 + ")")
    }
    override fun onPause() {
        LangStore.persistAll(this)
        super.onPause()
    }
    // THE TEST CLIENT IS RELEASED HERE, AND THIS SCREEN IS OURS TO GET RIGHT.
    //
    // TextToSpeech binds to an engine and holds that binding until shutdown()
    // is called; the framework's own documentation is that it "releases the
    // resources used by the TextToSpeech engine", and an Activity destroyed
    // with a live client leaks the connection and the Context it was built
    // with. Nothing declares android:configChanges, so every rotate, fold,
    // resize and theme change destroys and recreates this screen -- and the
    // owner opens it once per language, so the leaks accumulate within a single
    // sitting rather than needing an unusual gesture to reproduce.
    //
    // WHY THIS IS NOT A RULE 5 VIOLATION, which is the only reason it may be
    // written at all. AutoTTS's NewSettingsActivity leaks its two clients the
    // same way -- its onDestroy is `K.removeCallbacksAndMessages(null);
    // super.onDestroy();` and nothing else -- and MainActivity mirrors that
    // exactly and is deliberately LEFT alone. But this Activity has no AutoTTS
    // counterpart at all: AutoTTS has one settings Activity whose single client
    // is created once, while VoiceSetupActivity exists only because the Voices
    // tab became its own screen in the 2026-08-13 Configuration departure. The
    // client is ours, created by our own screen, so releasing it is not a
    // decision about AutoTTS's behaviour.
    //
    // shutdown() on an already-dead client is harmless, and the field is
    // cleared so a late speakTest cannot reach a shut-down engine.
    override fun onDestroy() {
        try { testTts?.shutdown() } catch (_: Exception) { }
        testTts = null
        super.onDestroy()
    }
}
