package com.tts.easyvoice
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
class ModeSettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
