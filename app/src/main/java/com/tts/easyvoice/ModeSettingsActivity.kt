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
        // NO setTitle HERE ANY MORE. The window already has a title from the
        // manifest ("Mode settings"), and a screen reader speaks a window's
        // title when the window appears; changing it during onCreate produced a
        // SECOND window-state-changed event and therefore a second
        // announcement, which is the doubling the owner reported on 2026-09-04.
        // The mode's own name is not lost -- it is the screen's first heading,
        // "<Mode> settings", which is where the reader lands.
        setContent { EasyVoiceTheme { ModeSettingsScreen(prefs, mode) } }
    }
    override fun onPause() {
        LangStore.persistAll(this)
        super.onPause()
    }
}
