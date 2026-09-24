package com.sachinbaria.easyvoice
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import java.util.Locale
object EngineFinder {
    data class EngineInfo(val pkg: String, val name: String) { override fun toString() = name }
    // What one scan learned about one engine, for the Troubleshoot screen.
    // `problem` is empty when the engine answered with voices.
    data class EngineReport(val pkg: String, val label: String, val voices: Int, val problem: String)
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
    // THE ENGINE'S OWN NAME, AS ANDROID GIVES IT (2026-09-24). The table above
    // is AutoTTS's c3.v written out by hand, and the scan never used it: it
    // names every engine with resolveInfo.loadLabel -- the label of the engine's
    // TTS service, the same name Android's own TTS settings list it under. So
    // the Voice setup screen named an engine one way and the Configuration list
    // another, and an engine newer than the table got a name built from its
    // package. This answers what Android answers: the last scan's label, else
    // the installed service's own label (cached; a label changes only with an
    // update), and the table only for a package that is not installed at all --
    // an imported configuration can name one.
    private val labelCache = java.util.concurrent.ConcurrentHashMap<String, String>()
    @JvmStatic
    fun engineLabel(ctx: Context, pkg: String?): String {
        if (pkg.isNullOrEmpty()) return friendlyName(pkg)
        lastScanVoices.firstOrNull { it.pkg == pkg }?.engineName?.takeIf { it.isNotBlank() }?.let { return it }
        labelCache[pkg]?.let { return it }
        val label = try {
            val pm = ctx.packageManager
            pm.queryIntentServices(Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE).setPackage(pkg), 0)
                .firstOrNull()?.loadLabel(pm)?.toString()?.takeIf { it.isNotBlank() }
        } catch (_: Exception) { null }
        if (label != null) { labelCache[pkg] = label; return label }
        return friendlyName(pkg)
    }
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

    // THE 3-ARG TextToSpeech CONSTRUCTOR SILENTLY FALLS BACK TO ANOTHER ENGINE,
    // AND ON THIS APP'S OWN PHONE THAT OTHER ENGINE IS US. Read out of AOSP
    // rather than recalled -- TextToSpeech.java:761 is
    //
    //     public TextToSpeech(Context context, OnInitListener listener, String engine) {
    //         this(context, listener, engine, null, true);   // useFallback = TRUE
    //     }
    //
    // and initTts() then reads, verbatim: step 1 tries the requested engine, and
    // if it "is not installed" OR connectToEngine fails, `mUseFallback` being true
    // means it FALLS THROUGH; step 2 is `connectToEngine(getDefaultEngine())`,
    // which sets `mCurrentEngine = defaultEngine` and returns SUCCESS. AOSP's own
    // comment sits three lines below it: "NOTE: The API currently does not allow
    // the caller to query whether they are actually connected to any engine."
    //
    // Easy Voice IS the default engine on any phone where it is doing its job, so
    // an engine that cannot bind for a moment -- mid-update, or frozen by an OEM
    // battery manager -- hands back SUCCESS and a client bound to EASY VOICE while
    // the caller still believes it holds that engine. Driving such a client calls
    // straight back into our own service.
    //
    // There is no public getter for it, so `mCurrentEngine` is read reflectively.
    // This is the app's ONE reflection, it is a READ and never a write, and it is
    // wrapped: a non-SDK block throws NoSuchFieldException, which is an Exception,
    // so it degrades to "assume we got the engine we asked for" -- exactly what
    // every caller did before this function existed.
    //
    // IT IS NOT BLOCKED TODAY, and that was read rather than assumed: the field is
    // `@UnsupportedAppUsage private volatile String mCurrentEngine` with NO
    // maxTargetSdk, which is the "unsupported" list -- readable by an app at any
    // targetSdk, with a logcat warning. Only a future platform moving it to
    // "blocked" would make this answer expectedPkg.
    //
    // THE Field IS LOOKED UP ONCE (2026-09-23). The speak site now calls this for
    // EVERY chunk (see speakChunk), so the lookup and setAccessible are paid once
    // per process and a chunk costs one Field.get. It also means a platform that
    // does block it logs the stack trace ONCE, not once per chunk into the file
    // the owner shares.
    private val currentEngineField: java.lang.reflect.Field? by lazy {
        try {
            TextToSpeech::class.java.getDeclaredField("mCurrentEngine").also { it.isAccessible = true }
        } catch (ex: Exception) {
            EasyVoiceLogger.errorWithStack(EasyVoiceLogger.TAG, "Reflection failed", ex)
            null
        }
    }
    fun boundEngineOf(client: TextToSpeech?, expectedPkg: String): String {
        if (client == null) return expectedPkg
        val field = currentEngineField ?: return expectedPkg
        return try {
            field.get(client)?.toString() ?: expectedPkg
        } catch (ex: Exception) {
            expectedPkg
        }
    }
    // `seen` is the caller's, so two overlapping scans cannot clear each
    // other's half-built set. It used to be an object-level HashMap that this
    // function cleared on entry, and nothing outside EngineFinder ever read it.
    private fun getEngines(ctx: Context, seen: HashMap<String, EngineInfo>): List<EngineInfo> {
        val pkgManager = ctx.packageManager
        val intent = Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE)
        val list = mutableListOf<EngineInfo>()
        try {
            // AutoTTS's B0() passes these as the numbers 131072, 128 and 0; they
            // are PackageManager's own MATCH_ALL, GET_META_DATA and no flags, the
            // same values by name.
            val lists = listOf(
                pkgManager.queryIntentServices(intent, android.content.pm.PackageManager.MATCH_ALL),
                pkgManager.queryIntentServices(intent, android.content.pm.PackageManager.GET_META_DATA),
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
    // `notFound` marks a voice that is only here because a language is CONFIGURED
    // on it: the engine could not be read this time, but the configuration is
    // the user's and is shown rather than silently replaced (2026-09-23).
    data class ScanVoice(val pkg: String, val engineName: String, val locale: Locale, val variants: ArrayList<String>,
                         val notFound: Boolean = false)
    // "pkg#locale" -> the Locale it names, the inverse of how voice_N and the
    // per-language key are written.
    @JvmStatic fun parseStoredLocale(tag: String): Locale {
        val localeParts = tag.split("_")
        return when (localeParts.size) {
            1 -> localeOf(localeParts[0])
            2 -> localeOf(localeParts[0], localeParts[1])
            else -> localeOf(localeParts[0], localeParts[1], localeParts.drop(2).joinToString("_"))
        }
    }
    // Compiled ONCE rather than per call -- see LangStore.COMBINING_MARKS for
    // the measurement. Both files strip combining marks the same way and both
    // did it inside a comparator.
    private val COMBINING_MARKS = "\\p{M}".toRegex()
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
        if (voice.isNotEmpty()) return localeOf(wantLangIso3, wantCountryIso3, voice)
        if (wantCountryIso3.isNotEmpty()) return localeOf(wantLangIso3, wantCountryIso3)
        return localeOf(wantLangIso3)
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
    // `always` is the Troubleshoot screen: the user asked for broken setups to be
    // repaired, so an engine that is gone moves its languages whether or not
    // Backup TTS is on. Returns one line per language moved.
    private fun repointUninstalled(ctx: Context, voices: List<ScanVoice>, always: Boolean = false): List<String> {
        val moved = ArrayList<String>()
        if (!always && !EasyVoiceTtsService.engineFallbackFlag) return moved
        try {
            val prefs = LangStore.prefs(ctx)
            val editor = prefs.edit()
            var changed = false
            for ((key, value) in prefs.all) {
                if (key.length != 3 || value !is String) continue
                val parts = value.split("#")
                if (parts.size < 2 || parts[0].isEmpty() || parts[0].equals("disable", true) || isSelfEngine(parts[0])) continue
                val installed = try {
                    ctx.packageManager.resolveService(Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE).setPackage(parts[0]), 0) != null
                } catch (_: Exception) { true }
                if (installed) continue
                val replacement = voices.firstOrNull { !isSelfEngine(it.pkg) && it.pkg != parts[0] && iso3Of(it.locale) == key } ?: continue
                editor.putString(key, replacement.pkg + "#" + replacement.locale.toString())
                editor.putString(key + "_variant", "*Default")
                changed = true
                moved.add(languageName(key) + " moved from " + engineLabel(ctx, parts[0]) + " to " + engineLabel(ctx, replacement.pkg))
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, parts[0] + " is not installed any more -- " + key +
                    " is now set up on " + replacement.pkg + " " + replacement.locale)
            }
            if (changed) editor.commit()
        } catch (ex: Exception) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "repointUninstalled: " + ex.toString())
        }
        return moved
    }
    @JvmStatic fun languageName(iso3: String): String =
        try { localeOf(iso3).displayLanguage.ifEmpty { iso3 } } catch (_: Exception) { iso3 }
    fun scanLanguages(
        ctx: Context,
        onProgress: ((String) -> Unit)? = null,
        repair: Boolean = false,
        onReport: ((List<EngineReport>, List<String>) -> Unit)? = null,
        onResult: (Set<String>) -> Unit
    ) {
        val engines = ArrayList<EngineInfo>()
        val seen = HashMap<String, EngineInfo>()
        val myGeneration = ++scanGeneration
        // This scan's own 180 s watchdog, so finalize cancels exactly that one.
        // A cell rather than a val because finalizeScan is declared above it.
        val myTimeout = arrayOfNulls<Runnable>(1)
        voiceWeights.clear()
        val langs = LinkedHashSet<String>()
        val displayNames = HashMap<String, String>()
        val voiceEntries = ArrayList<ScanVoice>()
        val failed = HashSet<Int>()
        val retried = HashSet<Int>()
        val succeeded = HashSet<Int>()
        // Per engine index, for onReport: voices read, and why it was not read.
        val voiceCounts = HashMap<Int, Int>()
        val problems = HashMap<Int, String>()
        val mainHandler = android.os.Handler(android.os.Looper.getMainLooper())
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
            // A FAILED ENGINE IS STILL INSTALLED -- it came out of this scan's
            // own queryIntentServices -- so it stays in the engine list and keeps
            // the voices it had (2026-09-23). Dropping it is what made Google
            // vanish from the list and fall silent after one bad attempt: the
            // persisted engine list lost it, so the service never bound it again.
            // Its voices come from the previous scan in this process, or, in a
            // fresh process, from the voice_N entries the last good scan saved.
            // An engine with nothing on record simply contributes nothing, which
            // is what happened before.
            // Every engine this scan did not read successfully counts, not just
            // the ones in `failed`: the probe failing, or the 180 s watchdog
            // firing, finalizes with engines the walk never reached, and those
            // used to be saved with no voices at all -- the whole list wiped.
            val notRead = engines.indices.filter { !succeeded.contains(it) }
                .map { engines[it].pkg }.filter { !isSelfEngine(it) }.distinct()
            val keptVoices = ArrayList<ScanVoice>()
            for (failedPkg in notRead) {
                val label = engines.firstOrNull { it.pkg == failedPkg }?.name ?: failedPkg
                val previous = lastScanVoices.filter { it.pkg == failedPkg }
                if (previous.isNotEmpty()) { keptVoices.addAll(previous) }
                else {
                    val stored = LangStore.prefs(ctx)
                    var voiceIdx = 0
                    while (true) {
                        val key = stored.getString("voice_$voiceIdx", "") ?: ""
                        if (key.isEmpty()) break
                        voiceIdx++
                        val parts = key.split("#")
                        if (parts.size < 2 || parts[0] != failedPkg) continue
                        keptVoices.add(ScanVoice(failedPkg, label, parseStoredLocale(parts[1]), arrayListOf("*Default")))
                    }
                }
                // THE CONFIGURATION IS THE LAST RECORD AND IT IS KEPT TOO. voice_N
                // is rewritten by every persist, so a build that once dropped an
                // engine from the scan left it with no voice_N history at all --
                // and then this engine contributed nothing, its languages lost the
                // voice, and the owner heard Google "go away" for every language
                // configured on it. Each language's own "<pkg>#<locale>" key names
                // the voice it was set up with, so that voice is kept as well.
                val prefs = LangStore.prefs(ctx)
                for ((key, value) in prefs.all) {
                    if (key.length != 3 || value !is String) continue
                    val parts = value.split("#")
                    if (parts.size < 2 || parts[0] != failedPkg || parts[1].isEmpty()) continue
                    val loc = parseStoredLocale(parts[1])
                    if (keptVoices.any { it.pkg == failedPkg && it.locale.toString() == loc.toString() }) continue
                    keptVoices.add(ScanVoice(failedPkg, label, loc, arrayListOf("*Default")))
                }
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Scan could not read " + failedPkg + "; keeping it with " +
                    keptVoices.count { it.pkg == failedPkg } + " voices from before")
            }
            for (kept in keptVoices) {
                val code = iso3Of(kept.locale)
                if (code.isNotEmpty() && langs.add(code)) displayNames[code] = try { kept.locale.displayLanguage } catch (_: Exception) { code }
            }
            val surviving = engines.map { it.pkg }.filter { !isSelfEngine(it) }
            val collator = java.text.Collator.getInstance()
            // COMBINING_MARKS is compiled once at class init -- see the note on it.
            // This lambda runs inside the Collator comparator, so it was compiling a
            // Pattern per comparison over every scanned language.
            fun sortKey(code: String) = java.text.Normalizer.normalize(displayNames[code] ?: code, java.text.Normalizer.Form.NFD).replace(COMBINING_MARKS, "")
            val sorted = langs.sortedWith { leftCode, rightCode -> collator.compare(sortKey(leftCode), sortKey(rightCode)) }
            val orderedLangs: Set<String> = LinkedHashSet(sorted)
            lastScanVoices = voiceEntries.filter { !notRead.contains(it.pkg) } + keptVoices
            lastScanEngines = surviving
            // A LANGUAGE WHOSE ENGINE IS NO LONGER INSTALLED MOVES TO THE FIRST ONE
            // THIS SCAN FOUND FOR IT (owner, 2026-09-23: "system mein jo TTS
            // available ho usko scan karke ... jo pahle mil jaaye scanning mein vah
            // setup ho jaaye"). Only an engine the package manager no longer
            // resolves as a TTS service -- uninstalled or disabled -- counts: one
            // that is installed but could not be read this time keeps its place
            // (above), because moving languages off an engine that merely hiccuped
            // is how Google's configuration used to vanish. The service does the
            // same at speaking time for an engine that dies while it runs.
            val moved = repointUninstalled(ctx, lastScanVoices, repair)
            val modeInt = EasyVoiceTtsService.modeInt
            val required = LangStore.requiredLangs(modeInt,
                EasyVoiceTtsService.autoLang,
                EasyVoiceTtsService.dualLang,
                EasyVoiceTtsService.mixLatinLang,
                EasyVoiceTtsService.mixNonLatinLang)
            LangStore.replaceAll(LangStore.rebuildFromScan(ctx, false, modeInt, required, lastScanVoices))
            LangStore.persistAll(ctx)
            // s0(). AutoTTS rebuilds the list after its own scan and pushes in
            // the same block (NewSettingsActivity:483); without this the
            // detector keeps hinting at whatever the list held before the scan,
            // which on a first run is nothing at all.
            EasyVoiceTtsService.pushLanguageSets()
            if (onReport != null) {
                val reports = ArrayList<EngineReport>()
                val reported = HashSet<String>()
                for (engineIdx in engines.indices) {
                    val info = engines[engineIdx]
                    if (isSelfEngine(info.pkg) || !reported.add(info.pkg)) continue
                    val count = voiceCounts[engineIdx] ?: 0
                    val problem = if (succeeded.contains(engineIdx)) ""
                                  else problems[engineIdx] ?: "it was not reached before the scan ended"
                    reports.add(EngineReport(info.pkg, info.name, count, problem))
                }
                onReport(reports, moved)
            }
            onResult(orderedLangs)
        }
        val globalTimeout = Runnable { finalizeScan() }
        myTimeout[0] = globalTimeout
        globalTimeoutHandler.postDelayed(globalTimeout, 180000L)
        engines.addAll(getEngines(ctx, seen))
        lateinit var startEngine: (String) -> Unit
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
            startEngine("(2)")
        }
        // ONE ENGINE, ONE LISTENER, ONE TIMEOUT, ONE CLIENT -- all captured
        // together (owner, 2026-09-09, explicitly overriding rule 5 here:
        // "properly sources ke through fix karo ... latency bilkul nahin aani
        // chahie").
        //
        // WHAT WAS WRONG. There was ONE shared listener reading the mutable
        // `index`, and the 30 s timeout advanced the walk WITHOUT shutting the
        // abandoned client down. So a slow engine's onInit arrived after the walk
        // had moved on and was attributed to the NEXT engine: it set the shared
        // `initFired`, masking that engine's own timeout; it read `holder[0]`,
        // which by then held a different engine's client; it could mark the wrong
        // index failed; and it called scanNextEngine() a second time, so the walk
        // skipped an engine. Every abandoned client also stayed bound for the life
        // of the process.
        //
        // THE FIX IS AOSP'S OWN, read from TextToSpeech.java rather than invented:
        //
        //   shutdown()  "Special case, we are asked to shutdown connection that
        //               did finalize its connection" -- while still connecting it
        //               does mConnectingServiceConnection.disconnect(), which is
        //               unbindService(). After that onServiceConnected never
        //               arrives, so dispatchOnInit never runs. Shutting the
        //               abandoned client down at timeout is what PREVENTS the late
        //               callback, and it releases the binding at the same time.
        //
        //   dispatchOnInit  calls the listener and then sets mInitListener = null,
        //               so a client fires onInit at most once.
        //
        //   every INLINE dispatchOnInit is ERROR (lines 871, 877, 907, 2385,
        //               2476). The only one that can carry SUCCESS is inside
        //               SetupConnectionAsyncTask.onPostExecute, which is always
        //               asynchronous -- so on SUCCESS the constructor has long
        //               returned and `cell[0]` is set. On the inline ERROR path
        //               `cell[0]` is still null, and that path needs no client.
        //
        // `handled` is the belt to shutdown()'s braces: unbindService stops future
        // callbacks, but one already queued on the main looper can still land.
        // Both this flag and the callbacks run on the main thread, so the check is
        // a plain read -- no lock, no clock, nothing to tune.
        //
        // NO LATENCY IS ADDED. Nothing new waits. The 30 s per-engine and 180 s
        // overall timeouts are AutoTTS's own numbers and are untouched; this only
        // stops abandoned work from continuing, so it does strictly less than
        // before.
        startEngine = { suffix ->
            val myIndex = index
            val pkg = engines[myIndex].pkg
            val cell = arrayOfNulls<TextToSpeech>(1)
            var handled = false

            fun release() {
                try { cell[0]?.shutdown() } catch (ex: Exception) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "")
                }
                cell[0] = null
            }

            val timeout = Runnable {
                if (handled) return@Runnable
                handled = true
                release()
                failed.add(myIndex)
                problems[myIndex] = "it did not answer within 30 seconds"
                scanNextEngine()
            }
            mainHandler.postDelayed(timeout, 30000L)

            // ONE RETRY, then give up on THIS scan only (2026-09-23, owner:
            // "Google baar-baar skip ho jata hai ... Google fir ismein dikhta hi
            // nahin hai"). A single failed attempt used to drop the engine from
            // the scan, and finalizeScan then PERSISTED the list without it: its
            // languages vanished and, through persistEngines, the service stopped
            // binding it at all, so every language configured on it went silent
            // until some later scan happened to succeed. The causes are all
            // transient -- an engine mid-update or frozen, the system session
            // answering ERROR, or an engine that reports SUCCESS before its voice
            // list is loaded and hands back no voices. One fresh bind clears
            // them. The 30 s timeout does NOT retry: an engine that hung once
            // would cost the scan another 30 s.
            fun retryOrFail(why: String): Boolean {
                if (!retried.add(myIndex)) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Scan of " + pkg + " failed again (" + why + ")")
                    problems[myIndex] = why
                    failed.add(myIndex); return false
                }
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Scan of " + pkg + " failed (" + why + "), retrying once")
                release()
                startEngine("(retry)")
                return true
            }
            fun onInitMain(status: Int) {
                if (handled) {
                    // A callback that beat the unbind. It belongs to an engine the
                    // walk has already left, so it must not touch `failed` and must
                    // not advance the walk -- just let its client go.
                    release()
                    return
                }
                handled = true
                mainHandler.removeCallbacks(timeout)
                if (status != TextToSpeech.SUCCESS) {
                    if (retryOrFail("init status " + status)) return
                } else {
                    val client = cell[0]
                    val expectedPkg = engines[myIndex].pkg
                    val actualEngine = boundEngineOf(client, expectedPkg)
                    if (actualEngine == expectedPkg) {
                        var added = 0
                        // null and empty are different failures and the log said
                        // "no voices yet" for both: null is TextToSpeech.getVoices'
                        // error result (no connection, or the call threw), empty is
                        // the engine itself answering with no voices.
                        var noVoicesWhy = "no voices yet"
                        try {
                            val engineVoices = client?.voices
                            noVoicesWhy = if (engineVoices == null) "no voices yet: getVoices gave null, connection lost"
                                          else "no voices yet: the engine listed 0 voices"
                            if (engineVoices != null) for (voice in engineVoices) {
                                val scannedVoiceName = voice.name ?: ""
                                if (scannedVoiceName.isEmpty()) continue
                                val loc = voice.locale ?: continue
                                val code = iso3Of(loc)
                                if (code.isNotEmpty() && langs.add(code)) displayNames[code] = try { loc.displayLanguage } catch (_: Exception) { code }
                                added++
                                if (addVoiceToMatchingEntry(voiceEntries, loc, expectedPkg, scannedVoiceName)) continue
                                voiceEntries.add(ScanVoice(expectedPkg, engines[myIndex].name, loc, arrayListOf("*Default")))
                                addVoiceToMatchingEntry(voiceEntries, loc, expectedPkg, scannedVoiceName)
                            }
                        } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "") }
                        if (added == 0 && retryOrFail(noVoicesWhy)) return
                        voiceCounts[myIndex] = added
                        if (added == 0) failed.add(myIndex) else { succeeded.add(myIndex); EasyVoiceTtsService.engineAnswered(expectedPkg) }
                    } else {
                        if (retryOrFail("bound to " + actualEngine)) return
                    }
                    release()
                }
                scanNextEngine()
            }
            // ERROR arrives on a BINDER thread: every client here connects through
            // SystemConnection, whose ITextToSpeechSessionCallback.onError calls
            // dispatchOnInit inline. Everything above is main-thread state
            // (`handled`, `failed`, the walk, the next constructor, the progress
            // line), so it is handed to the main looper -- inline when already there.
            val listener = TextToSpeech.OnInitListener { status ->
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) onInitMain(status)
                else mainHandler.post { onInitMain(status) }
            }

            onProgress?.invoke("Scanning $pkg... $suffix")
            // THE FIRST ENGINE'S CONSTRUCTOR IS GUARDED NOW, AND IT WAS NOT
            // (2026-09-10). The "(3)" first-engine step used to call the
            // constructor BARE -- there was a `liveIndex` flag choosing between
            // a guarded and an unguarded call, and it is gone with the last
            // unguarded one -- exactly as
            // AutoTTS's D0() does (NewSettingsActivity:446), while the "(2)"
            // step below has always been wrapped -- AutoTTS wraps that one too
            // (:245). So AutoTTS carries the identical asymmetry and we mirrored
            // it faithfully.
            //
            // It is still a defect, and it is the SAME defect this file already
            // records for restoreEngine: `new TextToSpeech(...)` does real work
            // -- it reads Settings.Secure, resolves the engine and calls
            // bindService -- so it can throw when that engine is mid-update,
            // which is exactly when a Play Store update of a TTS engine leaves
            // the package briefly unresolvable. This runs on the MAIN THREAD
            // from onCreate, so a throw here is not a failed scan, it is the
            // settings screen crashing the moment a blind user opens it, with
            // nothing in the app able to recover.
            //
            // Covered by the owner's standing override for the scan (2026-09-09:
            // "ham log is per depend rahenge na to achha nahin rahega ...
            // properly source ke through fix karo"), and the recovery is not
            // invented -- it is byte for byte what the "(2)" branch already does
            // and what the 30 s timeout already does: mark this engine failed
            // and advance the walk. On the happy path nothing changes at all.
            try {
                cell[0] = TextToSpeech(ctx, listener, pkg)
            } catch (ex: Exception) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when initialize " + pkg + "\n" + ex.message)
                if (!handled) {
                    handled = true
                    mainHandler.removeCallbacks(timeout)
                    if (!retryOrFail("constructor threw")) scanNextEngine()
                }
            }
        }
        fun scanFirstEngine() {
            index = 0
            startEngine("(3)")
        }
        val probe = arrayOfNulls<TextToSpeech>(1)
        // THE PROBE IS GUARDED AND RELEASED ON BOTH PATHS (2026-09-10).
        // AutoTTS's C0() calls this constructor bare too (NewSettingsActivity:402)
        // and shuts its client down on neither path -- the same two defects as
        // the first-engine step above, on the ONE line that runs before anything
        // else in every app open. A throw here crashes MainActivity outright;
        // and on the ERROR path the client stayed bound to our own service for
        // the life of the process. Shutting it down inside its own onInit is
        // AOSP's own mechanism: shutdown() while still connecting calls
        // mConnectingServiceConnection.disconnect(), which is unbindService.
        //
        // The recovery is the one the ERROR branch already had, so nothing on
        // the happy path moves.
        fun probeFailed() {
            try { probe[0]?.shutdown() } catch (ex: Exception) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "")
            }
            probe[0] = null
            android.widget.Toast.makeText(ctx, "Easy Voice: init new engine failed.", android.widget.Toast.LENGTH_LONG).show()
            finalizeScan()
        }
        try {
            // Same binder-thread ERROR as the engine listeners, and here it was a
            // crash rather than a race: probeFailed() shows a Toast, and
            // Toast.makeText throws on a thread with no Looper.
            fun onProbeInit(status: Int) {
                if (status == TextToSpeech.SUCCESS && probe[0] != null) {
                    for (engineInfo in probe[0]!!.engines) {
                        val name = engineInfo.name
                        if (seen.containsKey(name) || name.contains("easyvoice")) continue
                        engines.add(EngineInfo(name, engineInfo.label))
                    }
                    probe[0]!!.shutdown()
                    probe[0] = null
                    scanFirstEngine()
                } else {
                    probeFailed()
                }
            }
            probe[0] = TextToSpeech(ctx, { status ->
                if (android.os.Looper.myLooper() == android.os.Looper.getMainLooper()) onProbeInit(status)
                else mainHandler.post { onProbeInit(status) }
            }, BuildConfig.APPLICATION_ID)
        } catch (ex: Exception) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when initialize probe\n" + ex.message)
            probeFailed()
        }
    }
}
