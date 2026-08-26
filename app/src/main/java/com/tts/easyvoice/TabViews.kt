package com.tts.easyvoice
val modeRowSpecs = listOf(
    Triple("dual", "Dual languages", "Reads Latin words in English and seamlessly switches to your secondary language for non-Latin text. Choose that language in Settings."),
    Triple("auto", "Auto language detect", "Auto-detects the full sentence language and applies the ideal reading voice."),
    Triple("google", "Google TTS", "The same as Auto, except it only uses Google's voices."),
    Triple("mix", "Mixed mode", "Intelligently splits and reads mixed-script text, switching voices for each language segment."),
    Triple("multilingual", "Multilingual mode (experimental)", "Analyzes and reads complex multi-language text using the most accurate voice for each specific language.")
)
