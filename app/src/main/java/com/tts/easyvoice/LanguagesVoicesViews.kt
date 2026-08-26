package com.tts.easyvoice
import android.content.Context
fun abbreviateEngineNameFor(name: String): String = abbreviateEngineName(name)
private fun abbreviateEngineName(name: String): String {
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
    LangStore.languages.clear()
    if (modeInt == 1) LangStore.languages.addAll(LangStore.dualLangList(context, dualIso3, EngineFinder.lastScanVoices))
    else LangStore.languages.addAll(LangStore.rebuildFromScan(context, true, modeInt, required, EngineFinder.lastScanVoices))
    // c3.k.Y2 rebuilds the list the same way and finishes with s0().
    EasyVoiceTtsService.pushLanguageSets()
    return when (modeInt) {
        2, 4, 5 -> LangStore.availableLanguagesFor(null, false)
        3 -> LangStore.availableLanguagesFor("com.google.android.tts", false)
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
    val pkgFilter = if (modeInt == 3) "com.google.android.tts" else null
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
    return out
}
