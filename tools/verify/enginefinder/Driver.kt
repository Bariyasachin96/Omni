
// ---- hand-written driver; everything above is lifted verbatim ----
object BuildConfig { const val APPLICATION_ID = "com.sachinbaria.easyvoice" }
object TextToSpeech { object Engine { const val INTENT_ACTION_TTS_SERVICE = "android.intent.action.TTS_SERVICE" } }
object EasyVoiceLogger { const val TAG = "t"; fun errorWithStack(t: String, m: String, e: Throwable) {} }
data class ScanVoice(val pkg: String, val engineName: String)
object Shared { var lastScanVoices: List<ScanVoice> = emptyList() }
class Intent(val action: String) { var pkg: String? = null; fun setPackage(p: String?): Intent { pkg = p; return this } }
class ApplicationInfo(val flags: Int) { companion object { const val FLAG_SYSTEM = 1; const val FLAG_UPDATED_SYSTEM_APP = 128 } }
class ServiceInfo(val packageName: String, val applicationInfo: ApplicationInfo?)
class Engine(val pkg: String, val label: String, val flags: Int, val priority: Int, val inMatchAll: Boolean, val inMeta: Boolean, val inPlain: Boolean)
class PackageManager {
    companion object { const val MATCH_ALL = 0x20000; const val GET_META_DATA = 128; const val SIGNATURE_MATCH = 0 }
    var engines: List<Engine> = emptyList()
    var labels = 0; var queries = 0
    inner class RI(val e: Engine) {
        val serviceInfo: ServiceInfo? = ServiceInfo(e.pkg, ApplicationInfo(e.flags))
        val priority = e.priority
        fun loadLabel(pm: PackageManager): CharSequence { labels++; return e.label }
    }
    fun queryIntentServices(i: Intent, flags: Int): List<RI> {
        queries++
        return engines.filter { (i.pkg == null || it.pkg == i.pkg) &&
            when (flags) { MATCH_ALL -> it.inMatchAll; GET_META_DATA -> it.inMeta; else -> it.inPlain } }.map { RI(it) }
    }
    fun checkSignatures(a: String, b: String) = if (b.startsWith("our.")) SIGNATURE_MATCH else -3
}
class Context(val packageManager: PackageManager) { val applicationContext: Context get() = this }
fun main() {
    val rnd = java.util.Random(9)
    val pkgs = listOf("com.google.android.tts", "com.samsung.SMT", "com.eloq", "com.voc", "our.old.easyvoice", "org.rh", "com.x.my_tts")
    var cases = 0; var bad = 0
    var oldLabels = 0; var newLabels = 0; var oldQ = 0; var newQ = 0
    repeat(3000) { round ->
        val pmO = PackageManager(); val pmN = PackageManager()
        val ctxO = Context(pmO); val ctxN = Context(pmN)
        fun randomEngines(): List<Engine> = pkgs.filter { rnd.nextInt(3) != 0 }.map {
            Engine(it, if (rnd.nextInt(5) == 0) "" else it.substringAfterLast('.') + " TTS", if (rnd.nextBoolean()) 1 else 0,
                rnd.nextInt(3) * 50, rnd.nextInt(4) != 0, rnd.nextBoolean(), rnd.nextInt(3) != 0) }.shuffled(rnd)
        val first = randomEngines(); pmO.engines = first; pmN.engines = first
        Shared.lastScanVoices = emptyList()
        repeat(60) {
            when (rnd.nextInt(7)) {
                0, 1 -> { OldEF.attach(ctxO); NewEF.attach(ctxN) }                       // a screen opens
                2 -> {                                                                    // a scan
                    OldEF.attach(ctxO)
                    NewEF.attach(ctxN); if (NewEF.builtInKnown) NewEF.refreshBuiltInEngine()
                    Shared.lastScanVoices = List(rnd.nextInt(8)) { ScanVoice(pkgs[rnd.nextInt(pkgs.size)], if (rnd.nextInt(4) == 0) "" else "Scan " + rnd.nextInt(3)) }
                }
                3 -> {                                                                    // a package changes: the receiver
                    val e = randomEngines(); pmO.engines = e; pmN.engines = e
                    val p = pkgs[rnd.nextInt(pkgs.size)]
                    OldEF.labelCache.remove(p); OldEF.selfCache.remove(p); NewEF.labelCache.remove(p); NewEF.selfCache.remove(p)
                    OldEF.refreshBuiltInEngine(); NewEF.refreshBuiltInEngine()
                }
                else -> {
                    val p = if (rnd.nextInt(8) == 0) "" else pkgs[rnd.nextInt(pkgs.size)]
                    cases++
                    val a = OldEF.engineLabel(ctxO, p); val b = NewEF.engineLabel(ctxN, p)
                    if (a != b) { bad++; if (bad < 5) println("DIFF label $p: $a vs $b") }
                    val c = OldEF.isTtsEngine(ctxO, p); val d = NewEF.isTtsEngine(ctxN, p)
                    if (c != d) { bad++; if (bad < 5) println("DIFF isTtsEngine $p") }
                }
            }
            cases++
            if (OldEF.appContext != null && OldEF.builtInEngine != NewEF.builtInEngine) { bad++; if (bad < 5) println("DIFF builtIn round $round: ${OldEF.builtInEngine} vs ${NewEF.builtInEngine}") }
        }
        oldLabels += pmO.labels; newLabels += pmN.labels; oldQ += pmO.queries; newQ += pmN.queries
        OldEF.appContext = null; NewEF.appContext = null; NewEF.builtInKnown = false
        OldEF.builtInEngine = ""; NewEF.builtInEngine = ""
        OldEF.labelCache.clear(); NewEF.labelCache.clear(); OldEF.selfCache.clear(); NewEF.selfCache.clear(); NewEF.scanLabels = null
    }
    println("cases $cases  differ $bad")
    println("package-manager queries: old $oldQ  new $newQ;  loadLabel calls: old $oldLabels  new $newLabels")
    println(if (bad == 0) "IDENTICAL" else "FAILED")
    if (bad != 0) kotlin.system.exitProcess(1)
}
