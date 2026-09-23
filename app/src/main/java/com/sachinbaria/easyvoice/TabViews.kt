package com.sachinbaria.easyvoice
// The two modes that are NEVER drawn as a row, and therefore never get a
// settings button either (owner, 2026-09-09). "none" because the owner had it
// removed outright, "google" because AutoTTS's own radio is
// android:visibility="gone" in fragment_modes.xml and setVisibility is never
// called on it anywhere -- so that mode is not offered there either. Both still
// EXIST in the store and in the service; only the UI refuses to show them.
//
// One list, read by ModesScreen's row loop and by MainScreen's settings FAB, so
// the button cannot outlive the row it belongs to.
val HIDDEN_MODES = setOf("none", "google")
val modeRowSpecs = listOf(
    Triple("dual", "Dual languages", "Reads Latin words in English and seamlessly switches to your secondary language for non-Latin text. Choose that language in Settings."),
    Triple("auto", "Auto language detect", "Auto-detects the full sentence language and applies the ideal reading voice."),
    Triple("google", "Google TTS", "The same as Auto, except it only uses Google's voices."),
    Triple("mix", "Mixed mode", "Intelligently splits and reads mixed-script text, switching voices for each language segment."),
    Triple("multilingual", "Multilingual mode (experimental)", "Analyzes and reads complex multi-language text using the most accurate voice for each specific language.")
)
