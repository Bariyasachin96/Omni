package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.compose.setContent
class ModeSettingsActivity : EvActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This screen persists in onPause, so it must not run with unloaded
        // statics: Android can restore it alone into a fresh process. No-op
        // whenever anything is already loaded -- see LangStore.ensureLoaded.
        LangStore.ensureLoaded(this)
        val prefs = SharedPrefsManager(this)
        val mode = intent.getStringExtra("mode") ?: prefs.getReadingMode()
        val spec = modeRowSpecs.firstOrNull { it.first == mode }
        if (spec != null) setTitle(spec.second)
        setContent { EasyVoiceTheme { ModeSettingsScreen(prefs, mode) } }
    }
    override fun onPause() {
        LangStore.persistAll(this)
        super.onPause()
    }
}
