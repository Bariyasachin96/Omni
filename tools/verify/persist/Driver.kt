
// ---- hand-written driver; everything above is lifted verbatim ----
class LangEntry(var iso3: String, var speed: Int, var volume: Int, var pitch: Int, var variant: String, var disabled: Boolean)
object Lists { val languages = ArrayList<LangEntry>() }
class Context
object BuildConfig { const val APPLICATION_ID = "com.sachinbaria.easyvoice" }
object EasyVoiceTtsService {
    var engineList: List<String> = emptyList(); var voiceList: List<String> = emptyList(); var dedicatedEnginesFlag = false
    var autoLang = "eng"; var dualLang = "hin"; var mixLatinLang = "eng"; var mixNonLatinLang = "guj"
    var numberModeInt = 0; var punctuationModeInt = 1; var emojiModeInt = 2
    var numberSpecificLang = "eng"; var puncSpecificLang = "hin"; var emojiSpecificLang = "guj"
    var modeInt = 4; var localeSpansFlag = false
    var stripAudioAttrFlag = false; var forceAccessibilityFlag = true; var keepAliveFlag = false; var engineFallbackFlag = true
    var showNotificationFlag = false; var disableAdvancedFlag = false; var quickCharacterFlag = true; var punctuationInFlowFlag = true
    var smartNumberFlag = false; var smartNumberGroupSize = 2
}
object EngineFinder {
    class ScanVoice(val pkg: String, val locale: java.util.Locale)
    var lastScanEngines: List<String> = emptyList()
    var lastScanVoices: List<ScanVoice> = emptyList()
    fun iso3Of(l: java.util.Locale): String = l.isO3Language
}
fun localeOf(l: String) = java.util.Locale(l)
var currentVoiceRows: List<EngineFinder.ScanVoice?> = emptyList()
var currentVoiceIso = ""
var currentVoiceChosen = false
// A SharedPreferences stand-in: puts are buffered per editor, commit applies
// them in order and records whether the list monitor was held at that moment.
class Prefs {
    val map = LinkedHashMap<String, Any>()
    var commits = 0; var commitsUnderLock = 0
    fun getString(k: String, d: String?): String? = map[k] as String? ?: d
    inner class Ed {
        val ops = ArrayList<Pair<String, Any>>()
        fun putString(k: String, v: String) = apply { ops.add(k to v) }
        fun putInt(k: String, v: Int) = apply { ops.add(k to v) }
        fun putBoolean(k: String, v: Boolean) = apply { ops.add(k to v) }
        fun commit(): Boolean {
            commits++; if (Thread.holdsLock(Lists.languages)) commitsUnderLock++
            for ((k, v) in ops) map[k] = v; return true
        }
    }
    fun edit() = Ed()
}
inline fun Prefs.edit(commit: Boolean = false, action: Prefs.Ed.() -> Unit) { val e = edit(); e.action(); e.commit() }
var current = Prefs()
fun prefs(ctx: Context) = current
fun main() {
    val rnd = java.util.Random(11)
    val isos = listOf("eng", "hin", "guj", "mar", "ara", "zho", "rus")
    val pkgs = listOf("com.google.android.tts", "com.eloq", "com.voc", BuildConfig.APPLICATION_ID)
    val locs = listOf(java.util.Locale("en", "US"), java.util.Locale("hi", "IN"), java.util.Locale("gu"), java.util.Locale("ar"))
    var bad = 0; var cases = 0; var oldUnder = 0; var newUnder = 0; var oldCommits = 0; var newCommits = 0; var replacedSame = 0
    val ctx = Context()
    repeat(20000) { step ->
        Lists.languages.clear()
        repeat(rnd.nextInt(10)) {
            Lists.languages.add(LangEntry(isos[rnd.nextInt(isos.size)], rnd.nextInt(500), rnd.nextInt(100), rnd.nextInt(200), "v" + rnd.nextInt(4), rnd.nextBoolean()))
        }
        EngineFinder.lastScanEngines = List(rnd.nextInt(5)) { pkgs[rnd.nextInt(pkgs.size)] }
        EngineFinder.lastScanVoices = List(rnd.nextInt(6)) { EngineFinder.ScanVoice(pkgs[rnd.nextInt(pkgs.size)], locs[rnd.nextInt(locs.size)]) }
        EasyVoiceTtsService.dedicatedEnginesFlag = rnd.nextBoolean()
        EasyVoiceTtsService.modeInt = rnd.nextInt(6); EasyVoiceTtsService.autoLang = isos[rnd.nextInt(isos.size)]
        EasyVoiceTtsService.quickCharacterFlag = rnd.nextBoolean(); EasyVoiceTtsService.smartNumberGroupSize = 1 + rnd.nextInt(3)
        currentVoiceRows = List(rnd.nextInt(8)) { if (rnd.nextInt(5) == 0) null else EngineFinder.ScanVoice(pkgs[rnd.nextInt(pkgs.size)], locs[rnd.nextInt(locs.size)]) }
        currentVoiceIso = isos[rnd.nextInt(isos.size)]
        currentVoiceChosen = rnd.nextBoolean()
        // the same starting file for both, sometimes holding the language's own key already
        val seed = LinkedHashMap<String, Any>()
        if (rnd.nextBoolean()) for (iso in isos) if (rnd.nextBoolean()) seed[iso] = "x#y"
        val runs = listOf<(Any) -> Unit>(
            { o -> if (o === OldP) OldP.persistLanguages(ctx) else NewP.persistLanguages(ctx) },
            { o -> if (o === OldP) OldP.persistDisabled(ctx) else NewP.persistDisabled(ctx) },
            { o -> if (o === OldP) OldP.persistEngines(ctx) else NewP.persistEngines(ctx) },
            { o -> if (o === OldP) OldP.persistVoiceList(ctx) else NewP.persistVoiceList(ctx) },
            { o -> if (o === OldP) OldP.persistVoiceRows(ctx) else NewP.persistVoiceRows(ctx) },
            { o -> if (o === OldP) OldP.persistAll(ctx) else NewP.persistAll(ctx) })
        for ((i, r) in runs.withIndex()) {
            cases++
            EasyVoiceTtsService.engineList = emptyList(); EasyVoiceTtsService.voiceList = emptyList()
            current = Prefs().also { it.map.putAll(seed) }; r(OldP)
            val oldMap = LinkedHashMap(current.map); val oldE = EasyVoiceTtsService.engineList; val oldV = EasyVoiceTtsService.voiceList
            oldUnder += current.commitsUnderLock; oldCommits += current.commits
            EasyVoiceTtsService.engineList = emptyList(); EasyVoiceTtsService.voiceList = emptyList()
            current = Prefs().also { it.map.putAll(seed) }; r(NewP)
            newUnder += current.commitsUnderLock; newCommits += current.commits
            // #6: the same persist again must keep the service's list OBJECTS
            // (the service caches parsed voices by identity) when content-equal.
            val e1 = EasyVoiceTtsService.engineList; val v1 = EasyVoiceTtsService.voiceList
            val saved = current
            current = Prefs().also { it.map.putAll(seed) }; r(NewP); current = saved
            if (EasyVoiceTtsService.engineList == e1 && EasyVoiceTtsService.engineList !== e1) replacedSame++
            if (EasyVoiceTtsService.voiceList == v1 && EasyVoiceTtsService.voiceList !== v1) replacedSame++
            if (EasyVoiceTtsService.engineList != e1 || EasyVoiceTtsService.voiceList != v1) { bad++; println("DIFF repeat persist changed the lists") }
            EasyVoiceTtsService.engineList = e1; EasyVoiceTtsService.voiceList = v1
            // compared as maps: what a SharedPreferences file holds
            if (HashMap(oldMap) != HashMap(current.map) || oldE != EasyVoiceTtsService.engineList || oldV != EasyVoiceTtsService.voiceList) {
                bad++; if (bad < 5) println("DIFF fn#$i step $step\n old $oldMap\n new ${current.map}")
            }
        }
    }
    println("cases $cases  differ $bad  content-equal lists replaced on a repeat persist: $replacedSame")
    if (replacedSame != 0) bad++
    println("commits: old $oldCommits ($oldUnder under the list lock)  new $newCommits ($newUnder under the list lock)")
    println(if (bad == 0 && newUnder == 0) "IDENTICAL, never under the lock" else "FAILED")
    if (bad != 0 || newUnder != 0) kotlin.system.exitProcess(1)
}
