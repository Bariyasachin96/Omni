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
    // NO PACKAGE NAME IS WRITTEN IN THIS APP (owner, 2026-09-24: "kisi bhi TTS ka
    // package name likhane ki jarurat nahin ... package name change hote rahte
    // hain ... pura system sahi uthao"). The table of forty engine packages that
    // sat here (AutoTTS's c3.v) is gone: an engine is named by Android's own
    // label for its TTS service, and a package that is not installed at all is
    // named from the package itself (friendlyName). "Which engine is ours" and
    // "which engine is the phone's built-in one" are asked of the system too --
    // see isSelfEngine and builtInEngine below.
    //
    // The application context, for the two questions above that need the
    // package manager from places that have no Context (LangStore.engineFor runs
    // on binder threads). The APPLICATION context only -- never an Activity or
    // the Service, which is what the 2026-09-10 appCtx leak held.
    @Volatile private var appContext: Context? = null
    @JvmStatic fun attach(ctx: Context) {
        if (appContext == null) appContext = ctx.applicationContext
        refreshBuiltInEngine()
    }
    // THE ENGINE'S OWN NAME, AS ANDROID GIVES IT (2026-09-24). The scan has always
    // names every engine with resolveInfo.loadLabel -- the label of the engine's
    // TTS service, the same name Android's own TTS settings list it under. So
    // the Voice setup screen named an engine one way and the Configuration list
    // another, and an engine newer than the table got a name built from its
    // package. This answers what Android answers: the last scan's label, else
    // the installed service's own label (cached; a label changes only with an
    // update), and for a package that is not installed at all -- an imported
    // configuration can name one -- a name built from the package itself.
    private val labelCache = java.util.concurrent.ConcurrentHashMap<String, String>()
    @JvmStatic
    fun engineLabel(ctx: Context, pkg: String?): String {
        if (pkg.isNullOrEmpty()) return friendlyName(pkg)
        lastScanVoices.firstOrNull { it.pkg == pkg }?.engineName?.takeIf { it.isNotBlank() }?.let { return it }
        labelCache[pkg]?.let { return it }
        val label = installedEngines(ctx, pkg).firstOrNull()?.label?.takeIf { it.isNotBlank() }
        if (label != null) { labelCache[pkg] = label; return label }
        return friendlyName(pkg)
    }
    // A PACKAGE THAT CHANGED IS ASKED AGAIN (2026-09-24). Both caches above and
    // below were kept for the life of the process: an engine updated to a new
    // label kept its old name, and a package removed and installed again signed
    // by someone else kept its old "is it ours" answer. The service's package
    // receiver calls this on every add, replace, change and removal.
    @JvmStatic fun forget(pkg: String) {
        labelCache.remove(pkg)
        selfCache.remove(pkg)
    }

    // ==========================================================================
    //  THE SYSTEM'S OWN LIST OF VOICE ENGINES, ASKED IN ONE PLACE (2026-09-24)
    //
    //  Five places used to ask the package manager "which apps are voice
    //  engines" each in its own way -- three queryIntentServices with different
    //  flags, and four copies of resolveService(...) != null. They are these two
    //  functions now, shaped after AOSP's own TtsEngines (the class behind
    //  TextToSpeech.getEngines(), which is @hide): an engine is an app with a
    //  service for TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE; its name is
    //  that service's label; it is a system engine when its app is in the system
    //  image; and the intent filter's priority ranks it.
    //
    //  The query is the union AutoTTS's B0() makes -- PackageManager.MATCH_ALL,
    //  GET_META_DATA and no flags, in that order -- so no engine one of them
    //  would return is missed, and the order is the system's own resolution
    //  order (the scan walks engines in it, so it is NOT re-sorted here).
    //  QUERY_ALL_PACKAGES and the manifest's <queries> make every engine visible,
    //  in the background as well as the foreground.
    // ==========================================================================
    data class EngineService(val pkg: String, val label: String, val system: Boolean, val priority: Int)
    @JvmStatic
    fun installedEngines(ctx: Context, onlyPkg: String? = null): List<EngineService> =
        queryEngines(ctx, onlyPkg) ?: emptyList()
    // Null only when the package manager could not be asked at all (every query
    // threw), which callers that would otherwise MOVE a configuration read as
    // "unknown", never as "not installed".
    private fun queryEngines(ctx: Context, onlyPkg: String?): List<EngineService>? {
        val out = ArrayList<EngineService>()
        val seen = HashSet<String>()
        var answered = false
        val pm = ctx.packageManager
        val intent = Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE)
        if (onlyPkg != null) intent.setPackage(onlyPkg)
        for (flags in intArrayOf(android.content.pm.PackageManager.MATCH_ALL, android.content.pm.PackageManager.GET_META_DATA, 0)) {
            try {
                for (resolveInfo in pm.queryIntentServices(intent, flags)) {
                    val service = resolveInfo.serviceInfo ?: continue
                    if (!seen.add(service.packageName)) continue
                    val appFlags = service.applicationInfo?.flags ?: 0
                    out.add(EngineService(
                        service.packageName,
                        try { resolveInfo.loadLabel(pm).toString() } catch (_: Exception) { "" },
                        appFlags and (android.content.pm.ApplicationInfo.FLAG_SYSTEM or android.content.pm.ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0,
                        resolveInfo.priority))
                }
                answered = true
            } catch (ex: Exception) {
                EasyVoiceLogger.errorWithStack(EasyVoiceLogger.TAG, "Error in service discovery", ex)
            }
        }
        return if (answered) out else null
    }
    // Is this package a voice engine the system can bind right now? False for an
    // app that is uninstalled, turned off, or no longer has a TTS service.
    // `whenUnknown` is the answer if the package manager could not be asked.
    @JvmStatic fun isTtsEngine(ctx: Context, pkg: String, whenUnknown: Boolean = false): Boolean {
        if (pkg.isEmpty()) return false
        val found = queryEngines(ctx, pkg) ?: return whenUnknown
        return found.isNotEmpty()
    }

    // EVERY CALL INTO ANOTHER ENGINE THAT CAN BLOCK RUNS HERE, NOT ON THE MAIN
    // THREAD (2026-09-24). TextToSpeech.getVoices, isLanguageAvailable,
    // setLanguage and synthesizeToFile are binder calls into the engine's own
    // process and wait for its answer; an engine that is wedged answers never.
    // On the main thread that froze the screen -- and the 30 s timeout that is
    // meant to give up on such an engine runs on that same thread, so it could
    // not fire: "Easy Voice isn't responding". shutdown() takes the same lock
    // those calls hold (TextToSpeech.mStartLock), so it goes here too.
    // A cached pool rather than one thread: an engine that never answers keeps
    // only its own thread, and every other engine is still read.
    @JvmStatic val engineCalls: java.util.concurrent.ExecutorService =
        java.util.concurrent.Executors.newCachedThreadPool { runnable -> Thread(runnable, "EngineCall").apply { isDaemon = true } }
    // What an engine answered the scan, read on engineCalls: its voices (null is
    // TextToSpeech.getVoices' own error result -- no connection, or the call
    // threw), and the languages set up on it whose data it says is not on the
    // phone (LANG_MISSING_DATA, the engine's own answer).
    private class EngineRead(val voices: Set<android.speech.tts.Voice>?, val missing: List<String>)
    @JvmStatic fun shutdownLater(client: TextToSpeech?) {
        if (client == null) return
        try { engineCalls.execute { try { client.shutdown() } catch (_: Exception) {} } }
        catch (_: Exception) { try { client.shutdown() } catch (_: Exception) {} }
    }
    fun friendlyName(pkg: String?): String {
        if (pkg == null) return "Unknown"
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
    // A scan is walking the engines right now. The service's background scan
    // (EasyVoiceTtsService.scanInBackground) never starts while one is, because a
    // newer scan supersedes the older one and the older one's caller -- the main
    // screen's progress page, the Troubleshoot screen -- would then never hear
    // back. Set when a scan starts, cleared by the newest scan's finalize.
    @Volatile @JvmStatic var scanRunning = false
        private set
    // OURS, ASKED OF THE SYSTEM: our own package, or any package signed with our
    // own certificate -- an older install of this app under its previous package
    // name is one, and a package-name fragment could never say so reliably.
    // PackageManager.checkSignatures compares the signing certificates; the
    // answer is cached per package, since a certificate changes only with a
    // reinstall.
    private val selfCache = java.util.concurrent.ConcurrentHashMap<String, Boolean>()
    @JvmStatic fun isSelfEngine(pkg: String): Boolean {
        if (pkg == BuildConfig.APPLICATION_ID) return true
        if (pkg.isEmpty()) return false
        selfCache[pkg]?.let { return it }
        val ctx = appContext ?: return false
        val same = try {
            ctx.packageManager.checkSignatures(BuildConfig.APPLICATION_ID, pkg) == android.content.pm.PackageManager.SIGNATURE_MATCH
        } catch (_: Exception) { false }
        selfCache[pkg] = same
        return same
    }
    // THE PHONE'S BUILT-IN ENGINE, which is what "Google TTS" mode reads with.
    // It used to be one package name written into the app. It is now what AOSP
    // itself ranks first: TtsEngines.getEngines() sorts installed engines with
    // EngineInfoComparator -- engines in the SYSTEM IMAGE first, then by the
    // intent filter's priority -- and the framework falls back to the head of
    // that list when nothing else is chosen. Ours is never it. Empty when the
    // phone has no built-in engine, which is where "Google installed" used to be
    // false. Recomputed on attach, on every scan and on a package change.
    @Volatile @JvmStatic var builtInEngine: String = ""
        private set
    @JvmStatic fun refreshBuiltInEngine() {
        val ctx = appContext ?: return
        builtInEngine = try {
            installedEngines(ctx)
                .filter { it.system && !isSelfEngine(it.pkg) }
                .sortedByDescending { it.priority }
                .firstOrNull()?.pkg ?: ""
        } catch (_: Exception) { builtInEngine }
    }

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
        val list = mutableListOf<EngineInfo>()
        for (engine in installedEngines(ctx)) {
            if (isSelfEngine(engine.pkg) || seen.containsKey(engine.pkg)) continue
            val info = EngineInfo(engine.pkg, engine.label)
            seen[engine.pkg] = info
            list.add(info)
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
    private val globalTimeoutHandler = androidx.core.os.HandlerCompat.createAsync(android.os.Looper.getMainLooper())
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
                if (isTtsEngine(ctx, parts[0], whenUnknown = true)) continue
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
    // WHICH ENGINES ANSWERED LANG_MISSING_DATA IN THE LAST SCAN, per package (an
    // empty list = the engine listed no voices at all). Recorded only: the scan
    // opens NOTHING on its own (owner, 2026-09-24: "download screen nahin khul
    // jaani chahie"). The Troubleshoot screen shows it with an "Install voice
    // data" button, so the engine's installer opens only when the user asks.
    @JvmStatic val lastMissingData: MutableMap<String, List<String>> = java.util.concurrent.ConcurrentHashMap()
    private fun recordMissingVoiceData(missing: Map<String, List<String>>) {
        lastMissingData.clear()
        lastMissingData.putAll(missing)
        for ((pkg, langs) in missing) EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Voice data missing on " + pkg +
            (if (langs.isEmpty()) " (no voices)" else " for " + langs.joinToString(",")))
    }
    // The engines the last saved scan found, from every place they are kept.
    private fun previousEngines(ctx: Context): LinkedHashSet<String> {
        val out = LinkedHashSet<String>(lastScanEngines)
        out.addAll(EasyVoiceTtsService.engineList)
        try {
            val prefs = LangStore.prefs(ctx)
            var engineIdx = 0
            while (true) {
                val pkg = prefs.getString("engine_$engineIdx", "") ?: ""
                if (pkg.isEmpty() || pkg == "end") break
                out.add(pkg)
                engineIdx++
            }
        } catch (_: Exception) {}
        return out
    }
    private fun isInstalled(ctx: Context, pkg: String): Boolean =
        try { ctx.packageManager.getApplicationInfo(pkg, 0); true } catch (_: Exception) { false }
    @JvmStatic fun languageName(iso3: String): String =
        try { localeOf(iso3).displayLanguage.ifEmpty { iso3 } } catch (_: Exception) { iso3 }
    // `quiet` is the service's own scan, run in the background with no screen:
    // nothing is shown (the probe's failure Toast is the only thing the scan ever
    // shows), and the probe is skipped -- the installed engines come from the
    // package manager's own TTS-service query, which with QUERY_ALL_PACKAGES sees
    // every engine the probe's TextToSpeech.getEngines() would (that method asks
    // the package manager the same question).
    fun scanLanguages(
        ctx: Context,
        onProgress: ((String) -> Unit)? = null,
        repair: Boolean = false,
        quiet: Boolean = false,
        onReport: ((List<EngineReport>, List<String>) -> Unit)? = null,
        onResult: (Set<String>) -> Unit
    ) {
        attach(ctx)
        val engines = ArrayList<EngineInfo>()
        val seen = HashMap<String, EngineInfo>()
        val myGeneration = ++scanGeneration
        scanRunning = true
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
        // Per engine package: the configured languages it answered
        // LANG_MISSING_DATA for, or "" when it listed no voices at all.
        val missingData = LinkedHashMap<String, ArrayList<String>>()
        val problems = HashMap<Int, String>()
        val mainHandler = androidx.core.os.HandlerCompat.createAsync(android.os.Looper.getMainLooper())
        var index = 0
        // ONCE. The 180 s watchdog finalizes a scan whose walk is still going, and
        // the walk used to reach its own end later and finalize a second time --
        // saving again and calling the caller back twice.
        var finalized = false
        fun finalizeScan() {
            // A superseded scan writes NOTHING. Without this the older scan
            // still reached here and did languages.clear() + rebuildFromScan() +
            // persistAll() with its own half-finished engine list, which is
            // exactly why the count differed on every open.
            if (myGeneration != scanGeneration || finalized) return
            finalized = true
            scanRunning = false
            // AN ENGINE THE SYSTEM DID NOT LIST THIS TIME BUT THAT IS STILL
            // INSTALLED KEEPS ITS PLACE (2026-09-24, owner: "TTS mis ho jata hai
            // ... uske voice delete ho jaate hain"). During an update the package
            // is briefly not a TTS service at all, so queryIntentServices leaves it
            // out -- and a scan that ran then (the background scan can, now) saved
            // the lists without it: its voices gone, its languages dropped from
            // the language list, and the service no longer binding it. It is
            // added here as an engine this scan could not read, which keeps the
            // voices it had exactly as a failed read does. Only a package that is
            // really gone -- the package manager has no such app -- is dropped.
            val listed = engines.map { it.pkg }.toHashSet()
            for (pkg in previousEngines(ctx)) {
                if (pkg in listed || isSelfEngine(pkg) || !isInstalled(ctx, pkg)) continue
                listed.add(pkg)
                engines.add(EngineInfo(pkg, engineLabel(ctx, pkg)))
                problems[engines.size - 1] = "Android does not list it as a voice engine right now"
            }
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
            recordMissingVoiceData(missingData)
            // The running service binds any engine this scan found that its pool
            // does not have yet, and runs a scan it had to put off.
            EasyVoiceTtsService.scanFinished()
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
            if (myGeneration != scanGeneration || finalized) return
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

            // Off the main thread: shutdown() waits on the same lock a stuck
            // getVoices() holds (see engineCalls).
            fun release() {
                shutdownLater(cell[0])
                cell[0] = null
            }
            // The walk takes this attempt's result exactly once: the answer, or
            // the timeout, whichever comes first.
            lateinit var timeout: Runnable
            fun takeOver(): Boolean {
                if (handled) return false
                handled = true
                mainHandler.removeCallbacks(timeout)
                return true
            }

            timeout = Runnable {
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
            fun applyRead(read: EngineRead) {
                // The 30 s timeout got there first: it already released the client
                // and moved the walk on.
                if (!takeOver()) return
                val expectedPkg = engines[myIndex].pkg
                var added = 0
                val engineVoices = read.voices
                // null and empty are different failures and the log said "no
                // voices yet" for both.
                val noVoicesWhy = if (engineVoices == null) "no voices yet: getVoices gave null, connection lost"
                                  else "no voices yet: the engine listed 0 voices"
                try {
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
                // VOICE DATA THAT IS NOT DOWNLOADED, RECORDED FOR TROUBLESHOOT; an
                // engine that lists no voices at all needs its data too.
                if (read.missing.isNotEmpty()) missingData.getOrPut(expectedPkg) { ArrayList() }.addAll(read.missing)
                if (added == 0 && retried.contains(myIndex)) missingData.getOrPut(expectedPkg) { ArrayList() }
                if (added == 0) failed.add(myIndex) else { succeeded.add(myIndex); EasyVoiceTtsService.engineAnswered(expectedPkg) }
                release()
                scanNextEngine()
            }
            fun onInitMain(status: Int) {
                if (handled) {
                    // A callback that beat the unbind. It belongs to an engine the
                    // walk has already left, so it must not touch `failed` and must
                    // not advance the walk -- just let its client go.
                    release()
                    return
                }
                if (status != TextToSpeech.SUCCESS) {
                    takeOver()
                    if (retryOrFail("init status " + status)) return
                    release()
                    scanNextEngine()
                    return
                }
                val client = cell[0]
                val expectedPkg = engines[myIndex].pkg
                val actualEngine = boundEngineOf(client, expectedPkg)
                if (actualEngine != expectedPkg || client == null) {
                    takeOver()
                    if (retryOrFail("bound to " + actualEngine)) return
                    release()
                    scanNextEngine()
                    return
                }
                // The locales set up on this engine, read here from the settings;
                // the engine is asked about them on engineCalls.
                val configured = ArrayList<Pair<String, Locale>>()
                try {
                    for ((key, value) in LangStore.prefs(ctx).all) {
                        if (key.length != 3 || value !is String) continue
                        val parts = value.split("#")
                        if (parts.size < 2 || parts[0] != expectedPkg || parts[1].isEmpty()) continue
                        configured.add(key to parseStoredLocale(parts[1]))
                    }
                } catch (_: Exception) {}
                // THE TIMEOUT STAYS ARMED while the engine is read: a wedged engine
                // answers getVoices never, and the walk must still move on.
                try {
                    engineCalls.execute {
                        val voices = try { client.voices } catch (ex: Exception) {
                            EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: ""); null
                        }
                        val missing = ArrayList<String>()
                        for ((key, loc) in configured) {
                            val answer = try { client.isLanguageAvailable(loc) } catch (_: Exception) { TextToSpeech.LANG_NOT_SUPPORTED }
                            if (answer == TextToSpeech.LANG_MISSING_DATA) missing.add(key)
                        }
                        mainHandler.post { applyRead(EngineRead(voices, missing)) }
                    }
                } catch (ex: Exception) {
                    // The pool refused the job (never, for an unbounded cached pool);
                    // treat it like a failed read rather than stall the walk.
                    applyRead(EngineRead(null, emptyList()))
                }
            }
            // ERROR arrives on a BINDER thread: every client here connects through
            // SystemConnection, whose ITextToSpeechSessionCallback.onError calls
            // dispatchOnInit inline. Everything above is main-thread state
            // (`handled`, `failed`, the walk, the next constructor, the progress
            // line), so it is handed to the main looper -- inline when already there.
            val listener = TextToSpeech.OnInitListener { status ->
                if (android.os.Looper.getMainLooper().isCurrentThread) onInitMain(status)
                else mainHandler.post { onInitMain(status) }
            }

            onProgress?.invoke("Scanning $pkg... $suffix")
            // THE CONSTRUCTOR IS GUARDED (2026-09-10): `new TextToSpeech(...)` reads
            // Settings.Secure, resolves the engine and binds it, so it can throw
            // when that engine is mid-update. The recovery is what the timeout
            // does: mark this engine failed and advance the walk.
            //
            // THE APPLICATION CONTEXT, NOT THE SCREEN (2026-09-24). Up to Android
            // 11 a TextToSpeech binds the engine with the context it is given; the
            // main screen scans with ITSELF as that context, so rotating the phone
            // or closing the screen mid-scan destroyed the Activity and unbound
            // every engine still being read ("has leaked ServiceConnection") -- the
            // scan then failed them and saved the lists without their voices. The
            // scan outlives the screen by design, so its clients hang off the
            // application.
            try {
                cell[0] = TextToSpeech(ctx.applicationContext, listener, pkg)
            } catch (ex: Exception) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when initialize " + pkg + "\n" + ex.message)
                if (takeOver()) {
                    if (!retryOrFail("constructor threw")) scanNextEngine()
                }
            }
        }
        // An empty engine list -- a phone with no other TTS engine -- finalizes at
        // once. It used to index engines[0] and throw on the main thread.
        fun scanFirstEngine() {
            index = 0
            while (index < engines.size && isSelfEngine(engines[index].pkg)) index++
            if (index >= engines.size) {
                globalTimeoutHandler.removeCallbacks(globalTimeout)
                finalizeScan()
                return
            }
            startEngine("(3)")
        }
        if (quiet) { scanFirstEngine(); return }
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
            shutdownLater(probe[0])
            probe[0] = null
            try {
                android.widget.Toast.makeText(ctx, "Easy Voice: init new engine failed.", android.widget.Toast.LENGTH_LONG).show()
            } catch (_: Exception) {}
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
                        if (seen.containsKey(name) || isSelfEngine(name)) continue
                        engines.add(EngineInfo(name, engineInfo.label))
                    }
                    shutdownLater(probe[0])
                    probe[0] = null
                    scanFirstEngine()
                } else {
                    probeFailed()
                }
            }
            probe[0] = TextToSpeech(ctx.applicationContext, { status ->
                if (android.os.Looper.getMainLooper().isCurrentThread) onProbeInit(status)
                else mainHandler.post { onProbeInit(status) }
            }, BuildConfig.APPLICATION_ID)
        } catch (ex: Exception) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when initialize probe\n" + ex.message)
            probeFailed()
        }
    }
}
