package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
class VoiceSetupActivity : ComponentActivity() {
    private var testTts: android.speech.tts.TextToSpeech? = null
    // State now, not a local, so Previous/Next can move within this one screen.
    private var langIndex by mutableStateOf(-1)
    private var total by mutableStateOf(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = SharedPrefsManager(this)
        testTts = android.speech.tts.TextToSpeech(this, null, "com.tts.easyvoice")
        langIndex = intent.getIntExtra("lang_index", -1)
        val readingMode = prefs.getReadingMode()
        val modeInt = when (readingMode) { "dual" -> 1; "auto" -> 2; "google" -> 3; "mix" -> 4; "multilingual" -> 5; else -> 0 }
        // Rebuilds LangStore.languages, which is what lang_index indexes, and
        // its size is the list Configuration shows -- the same list to walk.
        total = voiceLanguageLabels(this, modeInt).size
        applyTitle()
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
    private fun applyTitle() {
        val entry = LangStore.languages.getOrNull(langIndex)
        if (entry != null) setTitle(entry.displayName + " (" + entry.iso3 + ")")
    }
    override fun onPause() {
        LangStore.persistAll(this)
        super.onPause()
    }
}
