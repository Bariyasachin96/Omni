package com.tts.easyvoice
import java.nio.ByteBuffer
object NativeEngine {
    init { System.loadLibrary("easyvoice_core") }
    external fun processDirect(
        buffer: ByteBuffer, length: Int,
        latinFallback: String, nonLatinFallback: String,
        mode: String,
        numberMode: Int, numberSpecific: String,
        punctuationMode: Int, punctuationSpecific: String,
        emojiMode: Int, emojiSpecific: String, punctuationInFlow: Boolean, smartNumber: Boolean,
        smartNumberGroupSize: Int,
        neutralDefault: String, neutralType: Int,
        disableAdvancedDetection: Boolean
    ): String
    external fun nativeGetLanguages(text: String): Array<String>
    // clsCLD2.b: decorated Latin letters folded back to ASCII.
    external fun normalizeFancy(text: String): String
    external fun setIsoMap(iso2: Array<String>, iso3: Array<String>)
    external fun setLanguageHints(langs: Array<String>)
    external fun setDetectSets(detectOkIso3: Array<String>, enabledLangs: Array<String>)
    external fun detectLanguageFull(text: String, latinFallback: String, nonLatinFallback: String,
        disableAdvancedDetection: Boolean, wantLog: Boolean): String
}
