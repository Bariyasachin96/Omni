package com.tts.easyvoice
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import java.util.Locale
object EngineFinder {
    data class EngineInfo(val pkg: String, val name: String) { override fun toString() = name }
    private val engineNames = mapOf(
        "com.google.android.tts" to "Google Text-to-Speech", "com.google.android.tts.speechpack.eng" to "Google TTS - English",
        "com.samsung.SMT" to "Samsung Text-to-Speech", "com.samsung.android.ttssmt" to "Samsung TTS", "com.samsung.smt" to "Samsung TTS Engine",
        "com.huawei.hiai.speech.tts" to "Huawei Text-to-Speech", "com.huawei.tts" to "Huawei TTS",
        "com.xiaomi.mibrain.speech" to "Xiaomi Text-to-Speech", "com.miui.voiceassist" to "Mi Voice Assistant TTS",
        "com.acapelagroup.android.tts" to "Acapela TTS Voices", "com.cereproc.android.tts" to "CereProc Text-to-Speech", "com.cereproc.CerePlay" to "CerePlay Text-to-Speech",
        "com.ivona.tts" to "IVONA Text-to-Speech", "com.ivona.tts.hq" to "IVONA Text-to-Speech HQ", "com.ivona.tts.oem" to "IVONA TTS OEM",
        "es.codefactory.vocalizertts" to "Vocalizer TTS", "com.nuance.tts" to "Nuance Vocalizer", "com.nuance.vocalizer" to "Nuance Vocalizer TTS",
        "com.reecedunn.espeak" to "eSpeak TTS", "com.googlecode.eyesfree.espeak" to "eSpeak TTS (Eyes-Free)", "rhzmr.espeak" to "eSpeak TTS",
        "com.github.olga_yakovleva.rhvoice.android" to "RHVoice", "com.svox.classic" to "SVOX Classic TTS", "com.svox.pico" to "Pico TTS",
        "edu.cmu.cs.speech.tts.flite" to "Flite TTS",
        "com.hear2read.tts.kannada" to "Hear2Read Kannada", "com.hear2read.tts.telugu" to "Hear2Read Telugu", "com.hear2read.tts.punjabi" to "Hear2Read Punjabi",
        "com.hear2read.tts.tamil" to "Hear2Read Tamil", "com.hear2read.tts.gujarati" to "Hear2Read Gujarati", "com.hear2read.tts.marathi" to "Hear2Read Marathi",
        "com.hear2read.tts.malayalam" to "Hear2Read Malayalam", "com.hear2read.tts.sanskrit" to "Hear2Read Sanskrit", "com.hear2read.tts.assamese" to "Hear2Read Assamese", "com.hear2read.tts.hindi" to "Hear2Read Hindi",
        "ru.yandex.speechkit.tts" to "Yandex SpeechKit TTS", "bg.bultreebank.speechlab" to "SpeechLab TTS", "io.github.aholab.ahotts" to "AhoTTS",
        "com.k2fsa.sherpa.onnx.tts.engine" to "Sherpa TTS", "com.amazon.tts" to "Amazon Text-to-Speech", "com.lge.tts" to "LG Text-to-Speech", "com.htc.tts" to "HTC Text-to-Speech",
        "com.voiceforge.tts" to "VoiceForge TTS", "jp.kddilabs.n2tts" to "N2 TTS", "com.speech.tts.engine" to "Speech TTS Engine", "com.nirenr.talkman" to "Jieshuo+"
    )
    fun friendlyName(pkg: String?): String {
        if (pkg == null) return "Unknown"
        engineNames[pkg]?.let { return it }
        if (pkg.isEmpty()) return "Unknown"
        val parts = pkg.split(".")
        val start = if (parts.size > 1 && parts[0].lowercase(Locale.getDefault()) in setOf("com", "org", "net", "io", "ru", "jp")) 1 else 0
        val pretty = StringBuilder()
        for (wordIdx in start until parts.size) { val word = parts[wordIdx]; if (word.isEmpty()) continue; pretty.append(word[0].uppercaseChar()); if (word.length > 1) pretty.append(word.substring(1)); if (wordIdx < parts.size - 1) pretty.append(" ") }
        return pretty.toString().trim()
    }
    // THE SCAN'S STATE IS PER SCAN NOW, NOT SHARED (owner, 2026-09-09:
    // "double triple baar jab bhi application open karte hain to 2 3 4 5 6 7").
    //
    // Every scan supersedes the one before it, and only the newest may write a
    // result. AutoTTS gets this for free because its whole scan lives in
    // NewSettingsActivity's INSTANCE fields -- L (index), M (init fired),
    // N (per-engine timeout), O (failed list), J and F -- so a recreated
    // Activity simply starts with fresh ones. Ours was lifted into an `object`,
    // which turned all of that into process-wide state, and MainActivity starts
    // a scan in EVERY onCreate: rotate the phone, or reopen the app while the
    // previous scan is still walking engines at 30 s a time, and two scans run
    // at once over the same fields. That is our refactor's bug, not AutoTTS's,
    // which is what makes fixing it right rather than a rule 5 departure.
    @Volatile private var scanGeneration = 0
    private fun isSelfEngine(pkg: String): Boolean =
        pkg.contains("easyvoice") || pkg.contains("multilingualtts")
    // `seen` is the caller's, so two overlapping scans cannot clear each
    // other's half-built set. It used to be an object-level HashMap that this
    // function cleared on entry, and nothing outside EngineFinder ever read it.
    private fun getEngines(ctx: Context, seen: HashMap<String, EngineInfo>): List<EngineInfo> {
        val pkgManager = ctx.packageManager
        val intent = Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE)
        val list = mutableListOf<EngineInfo>()
        try {
            val lists = listOf(
                pkgManager.queryIntentServices(intent, 131072),
                pkgManager.queryIntentServices(intent, 128),
                pkgManager.queryIntentServices(intent, 0)
            )
            for (resolveList in lists) for (resolveInfo in resolveList) {
                val serviceInfo = resolveInfo.serviceInfo ?: continue
                val pkg = serviceInfo.packageName
                if (pkg.contains("easyvoice") || seen.containsKey(pkg)) continue
                val info = EngineInfo(pkg, resolveInfo.loadLabel(pkgManager).toString())
                seen[pkg] = info
                list.add(info)
            }
        } catch (ex: Exception) {
            EasyVoiceLogger.errorWithStack(EasyVoiceLogger.TAG, "Error in service discovery", ex)
        }
        return list
    }
    data class ScanVoice(val pkg: String, val engineName: String, val locale: Locale, val variants: ArrayList<String>)
    @Volatile @JvmStatic var lastScanVoices: List<ScanVoice> = emptyList()
    @Volatile @JvmStatic var lastScanEngines: List<String> = emptyList()
    @JvmStatic val voiceWeights: HashMap<String, Int> = HashMap()
    private val globalTimeoutHandler = android.os.Handler(android.os.Looper.getMainLooper())
    // MainActivity.onDestroy calls this. It clears the handler wholesale, which
    // on a rotation is harmless -- onDestroy runs BEFORE the next onCreate, so
    // there is no newer scan to hit -- and in the rare order where a live scan
    // does lose its 180 s watchdog, the scan still finishes: the per-engine
    // 30 s timeout is what actually advances scanNextEngine, and the generation
    // guard is what decides who may publish.
    @JvmStatic fun cancelGlobalTimeout() { globalTimeoutHandler.removeCallbacksAndMessages(null) }
    @JvmStatic fun iso3Of(loc: Locale?): String = if (loc == null) "zxx" else try {
        val code = loc.isO3Language
        if (code == "cmn" || code == "lzh" || code == "gan" || code == "hak") "zho" else code
    } catch (_: Exception) { "zxx" }
    @JvmStatic fun iso3CountryOf(loc: Locale?): String = if (loc == null) "" else try { loc.isO3Country } catch (_: Exception) { "" }
    @JvmStatic fun normalizeLocale(loc: Locale?): Locale? {
        if (loc == null) return null
        val wantLangIso3 = iso3Of(loc); val wantCountryIso3 = iso3CountryOf(loc); val voice = loc.variant
        if (voice.isNotEmpty()) return Locale(wantLangIso3, wantCountryIso3, voice)
        if (wantCountryIso3.isNotEmpty()) return Locale(wantLangIso3, wantCountryIso3)
        return Locale(wantLangIso3)
    }
    @JvmStatic fun addVoiceToMatchingEntry(list: ArrayList<ScanVoice>, loc: Locale, pkg: String, voiceName: String): Boolean {
        val wantLang = iso3Of(loc)
        val wantCountry = iso3CountryOf(loc)
        for (stored in list) {
            if (stored.pkg != pkg) continue
            val norm = normalizeLocale(stored.locale)
            if (!iso3Of(norm).equals(wantLang, true)) continue
            if (!iso3CountryOf(norm).equals(wantCountry, true)) continue
            if (!stored.variants.contains(voiceName)) stored.variants.add(voiceName)
            return true
        }
        return false
    }
    fun scanLanguages(ctx: Context, onProgress: ((String) -> Unit)? = null, onResult: (Set<String>) -> Unit) {
        val engines = ArrayList<EngineInfo>()
        val seen = HashMap<String, EngineInfo>()
        val myGeneration = ++scanGeneration
        // This scan's own 180 s watchdog, so finalize cancels exactly that one.
        // A cell rather than a val because finalizeScan is declared above it.
        val myTimeout = arrayOfNulls<Runnable>(1)
        voiceWeights.clear()
        var initFired = false
        val langs = LinkedHashSet<String>()
        val displayNames = HashMap<String, String>()
        val voiceEntries = ArrayList<ScanVoice>()
        val failed = HashSet<Int>()
        val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())
        val holder = arrayOfNulls<TextToSpeech>(1)
        var index = 0
        fun finalizeScan() {
            // A superseded scan writes NOTHING. Without this the older scan
            // still reached here and did languages.clear() + rebuildFromScan() +
            // persistAll() with its own half-finished engine list, which is
            // exactly why the count differed on every open.
            if (myGeneration != scanGeneration) return
            // removeCallbacks, NOT removeCallbacksAndMessages(null): the handler
            // is shared, and clearing it wholesale cancelled the OTHER scan's
            // watchdog too.
            myTimeout[0]?.let { globalTimeoutHandler.removeCallbacks(it) }
            val remaining = ArrayList(engines)
            val removedPkgs = ArrayList<String>()
            for (failedIdx in failed.distinct().sortedDescending()) {
                if (failedIdx >= remaining.size) continue
                removedPkgs.add(remaining[failedIdx].pkg)
                remaining.removeAt(failedIdx)
            }
            val surviving = remaining.map { it.pkg }
            val collator = java.text.Collator.getInstance()
            fun sortKey(code: String) = java.text.Normalizer.normalize(displayNames[code] ?: code, java.text.Normalizer.Form.NFD).replace("\\p{M}".toRegex(), "")
            val sorted = langs.sortedWith { leftCode, rightCode -> collator.compare(sortKey(leftCode), sortKey(rightCode)) }
            val orderedLangs: Set<String> = LinkedHashSet(sorted)
            lastScanVoices = voiceEntries.filter { !removedPkgs.contains(it.pkg) }
            lastScanEngines = surviving
            val modeInt = EasyVoiceTtsService.modeInt
            val required = LangStore.requiredLangs(modeInt,
                EasyVoiceTtsService.autoLang,
                EasyVoiceTtsService.dualLang,
                EasyVoiceTtsService.mixLatinLang,
                EasyVoiceTtsService.mixNonLatinLang)
            LangStore.languages.clear()
            LangStore.languages.addAll(LangStore.rebuildFromScan(ctx, false, modeInt, required, lastScanVoices))
            LangStore.persistAll(ctx)
            // s0(). AutoTTS rebuilds the list after its own scan and pushes in
            // the same block (NewSettingsActivity:483); without this the
            // detector keeps hinting at whatever the list held before the scan,
            // which on a first run is nothing at all.
            EasyVoiceTtsService.pushLanguageSets()
            onResult(orderedLangs)
        }
        val globalTimeout = Runnable { finalizeScan() }
        myTimeout[0] = globalTimeout
        globalTimeoutHandler.postDelayed(globalTimeout, 180000L)
        engines.addAll(getEngines(ctx, seen))
        lateinit var startEngine: (String, Boolean) -> Unit
        fun scanNextEngine() {
            // Stop dead once a newer scan has started: this one can no longer
            // publish anything, and carrying on only fights the new scan for the
            // progress line and keeps engines binding for nothing.
            if (myGeneration != scanGeneration) return
            index++
            while (index < engines.size && isSelfEngine(engines[index].pkg)) index++
            if (index >= engines.size) {
                globalTimeoutHandler.removeCallbacks(globalTimeout)
                finalizeScan()
                return
            }
            startEngine("(2)", false)
        }
        val engineInitListener = TextToSpeech.OnInitListener { status ->
            initFired = true
            mainHandler.removeCallbacksAndMessages(null)
            if (status != TextToSpeech.SUCCESS) {
                failed.add(index)
            } else {
                val expectedPkg = if (index < engines.size) engines[index].pkg else ""
                val actualEngine = try {
                    val currentEngineField = holder[0]!!.javaClass.getDeclaredField("mCurrentEngine")
                    currentEngineField.isAccessible = true
                    currentEngineField.get(holder[0])?.toString() ?: engines[index].pkg
                } catch (ex: Exception) {
                    EasyVoiceLogger.errorWithStack(EasyVoiceLogger.TAG, "Reflection failed", ex)
                    expectedPkg
                }
                if (index < engines.size) {
                    if (actualEngine == engines[index].pkg) {
                        try {
                            val engineVoices = holder[0]?.voices
                            if (engineVoices != null) for (voice in engineVoices) {
                                val scannedVoiceName = voice.name ?: ""
                                if (scannedVoiceName.isEmpty()) continue
                                val loc = voice.locale ?: continue
                                val pkg = engines[index].pkg
                                val code = iso3Of(loc)
                                if (code.isNotEmpty() && langs.add(code)) displayNames[code] = try { loc.displayLanguage } catch (_: Exception) { code }
                                if (addVoiceToMatchingEntry(voiceEntries, loc, pkg, scannedVoiceName)) continue
                                voiceEntries.add(ScanVoice(pkg, engines[index].name, loc, arrayListOf("*Default")))
                                addVoiceToMatchingEntry(voiceEntries, loc, pkg, scannedVoiceName)
                            }
                        } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "") }
                    } else {
                        failed.add(index)
                    }
                }
                try { holder[0]?.shutdown() } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "") }
            }
            scanNextEngine()
        }
        startEngine = { suffix, liveIndex ->
            val pkg = engines[index].pkg
            val myIndex = index
            initFired = false
            mainHandler.postDelayed({
                if (!initFired) failed.add(if (liveIndex) index else myIndex)
                scanNextEngine()
            }, 30000L)
            onProgress?.invoke("Scanning $pkg... $suffix")
            if (liveIndex) {
                holder[0] = TextToSpeech(ctx, engineInitListener, pkg)
            } else {
                try {
                    holder[0] = TextToSpeech(ctx, engineInitListener, pkg)
                } catch (ex: Exception) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when initialize " + pkg + "\n" + ex.message)
                    scanNextEngine()
                }
            }
        }
        fun scanFirstEngine() {
            index = 0
            startEngine("(3)", true)
        }
        val probe = arrayOfNulls<TextToSpeech>(1)
        probe[0] = TextToSpeech(ctx, { status ->
            if (status == TextToSpeech.SUCCESS && probe[0] != null) {
                for (engineInfo in probe[0]!!.engines) {
                    val name = engineInfo.name
                    if (seen.containsKey(name) || name.contains("easyvoice")) continue
                    engines.add(EngineInfo(name, engineInfo.label))
                }
                probe[0]!!.shutdown()
                scanFirstEngine()
            } else {
                android.widget.Toast.makeText(ctx, "Easy Voice: init new engine failed.", android.widget.Toast.LENGTH_LONG).show()
                finalizeScan()
            }
        }, "com.tts.easyvoice")
    }
}
