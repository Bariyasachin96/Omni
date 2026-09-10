package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
class VoiceSetupActivity : EvActivity() {
    private var testTts: android.speech.tts.TextToSpeech? = null
    // State now, not a local, so Previous/Next can move within this one screen.
    private var langIndex by mutableStateOf(-1)
    private var total by mutableStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This screen persists in onPause, so it must not run with unloaded
        // statics: Android can restore it alone into a fresh process. No-op
        // whenever anything is already loaded -- see LangStore.ensureLoaded.
        LangStore.ensureLoaded(this)
        val prefs = SharedPrefsManager(this)
        testTts = android.speech.tts.TextToSpeech(this, null, "com.tts.easyvoice")
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
                    }) { testTts }
                }
            }
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
