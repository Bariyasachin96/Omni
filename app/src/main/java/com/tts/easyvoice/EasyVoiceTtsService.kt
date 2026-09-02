package com.tts.easyvoice
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.speech.tts.SynthesisCallback
import android.speech.tts.SynthesisRequest
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeechService
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.text.SpannableString
import android.text.style.LocaleSpan
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import java.nio.ByteBuffer
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
data class TextChunk(var text: String, var lang: String, var typeCode: Int = 0, var forcedEngine: String? = null, var forcedLocale: String? = null, var forcedVariant: String? = null)
class EasyVoiceTtsService : TextToSpeechService() {
    private val enginePool = ArrayList<EngineWrapper>()
    @Volatile private var restoringIndex = -1
    private val engineBinders = java.util.concurrent.ConcurrentHashMap<String, android.content.ServiceConnection>()
    private val keepAliveLocks = java.util.concurrent.ConcurrentHashMap<String, Any>()
    @Volatile var engineIndex = -1
    private lateinit var prefs: SharedPrefsManager
    private val syncLock = Object()
    @Volatile private var isStopped = false
    @Volatile private var isFlushed = false
    // ---- WHO IS SPEAKING, so engine death can unblock the wait ---------------
    // onSynthesizeText ends by parking the SCREEN READER'S synthesis thread on
    // syncLock until the utterance listener sets isStopped or isFlushed. If the
    // target engine accepts a speak() and then never calls back, that thread
    // parks forever -- the screen reader has one synthesis thread, so from that
    // moment the whole device stops speaking. AutoTtsService:2165 has the same
    // bare o.wait() and the same defect.
    //
    // THERE IS NO TIMEOUT HERE, AND THERE MUST NEVER BE ONE. A clock cannot tell
    // "this engine is dead" from "this paragraph is long", so any number picked
    // is a guess that either cuts off real speech or leaves the hang in place.
    // Android already reports the real event.
    //
    // WHY AN EVENT IS ENOUGH -- read from AOSP, not assumed:
    //   * TextToSpeechService dispatches onDone, onError or onStop for every
    //     speech item it processes, and dispatchOnError(ERROR_SERVICE) even for
    //     items it refuses. So a live engine ALWAYS calls back.
    //   * The one path with no callback is the engine's process going away.
    //     TextToSpeech's own Connection.onServiceDisconnected just sets
    //     mService = null and tells the app nothing (dispatchOnInit(ERROR) fires
    //     only if a connect was still in flight), which is exactly why this hung.
    //   * But we hold our OWN binding to every engine -- bindEngineKeepAlive is
    //     called for each one as the pool is built, not only in keep-alive mode
    //     -- and Android calls onServiceDisconnected / onBindingDied on it the
    //     moment that process dies. That is the missing signal, it is free, and
    //     it arrives sooner than any timeout could have fired.
    //
    // speakingPkg names the engine currently holding the utterance, so the
    // connection callback can tell "the engine I am waiting on died" from "some
    // other engine in the pool died". Cleared when the utterance ends.
    @Volatile private var speakingPkg: String? = null
    // Called from a ServiceConnection callback on the main thread. Ends the
    // utterance the dead engine was holding; does nothing for any other engine.
    private fun onEngineProcessGone(pkg: String) {
        if (speakingPkg != pkg) return
        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Engine holding this utterance died: " + pkg)
        speakingPkg = null
        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
    }
    lateinit var appCtx: Context
    @Volatile var googleEngineIndex = -1
    var requestVolume = 1.0f
    var requestRate = 1.0f
    var requestPitch = 1.0f
    @Volatile var requestParams: android.os.Bundle? = null
    var initializingIndex = 0
    private val isoToIso3 = HashMap<String, String>()
    var initDone = false
    // AutoTtsService.w: set once the language list has actually been read.
    @Volatile private var languagesLoaded = false
    var audioManager: AudioManager? = null
    var audioFocusRequest: AudioFocusRequest? = null
    private val FOREGROUND_NOTIFICATION_ID = 136549
    private val FOREGROUND_CHANNEL_ID = "tts_channel"
    @Volatile private var lastEnginePkg = ""

    // ==========================================================================
    //  FOREGROUND NOTIFICATION AND AUDIO FOCUS
    // ==========================================================================
    private fun createNotificationChannel() {
        val channel = NotificationChannel(FOREGROUND_CHANNEL_ID, "TTS Engine", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }
    private fun startAndFinish(callback: SynthesisCallback?) {
        if (callback?.hasStarted() == false) callback.start(16000, android.media.AudioFormat.ENCODING_PCM_16BIT, 1)
        if (callback?.hasFinished() == false) callback.done()
    }
    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setContentTitle("Easy Voice active")
            .setSmallIcon(17301540)
            .setOngoing(true)
            .build()
    }
    private fun hasNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= 33) {
            return checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
        }
        return true
    }
    private fun isForegroundActive(): Boolean {
        return getSystemService(NotificationManager::class.java).activeNotifications.any { it.id == FOREGROUND_NOTIFICATION_ID }
    }
    private fun startForegroundIfPossible() {
        try {
            if (!hasNotificationPermission()) return
            createNotificationChannel()
            if (Build.VERSION.SDK_INT >= 34) {
                ServiceCompat.startForeground(this, FOREGROUND_NOTIFICATION_ID, buildNotification(),
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
                return
            }
            startForeground(FOREGROUND_NOTIFICATION_ID, buildNotification())
        } catch (ex: Exception) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "")
        }
    }
    private fun requestAudioFocus() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(attrs)
            .setOnAudioFocusChangeListener { focusChange ->
                when (focusChange) {
                    -2 -> EasyVoiceLogger.debug("TTS", "Audio focus lost temporarily")
                    -1 -> EasyVoiceLogger.debug("TTS", "Audio focus lost")
                    1 -> EasyVoiceLogger.debug("TTS", "Audio focus gained")
                }
            }
            .build()
        audioFocusRequest = focusRequest
        val result = audioManager!!.requestAudioFocus(focusRequest)
        EasyVoiceLogger.debug("TTS", "Audio focus request: " + (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED))
    }

    // ==========================================================================
    //  LIFECYCLE
    //  onCreate is ordering-critical: context, logger, prefs and the logging flag
    //  come BEFORE super.onCreate(), and the language sets are pushed only after
    //  loadAllSettings has filled them.
    // ==========================================================================
    override fun onCreate() {
        appCtx = this
        EasyVoiceLogger.init(this)
        prefs = SharedPrefsManager(this)
        EasyVoiceLogger.setLoggingEnabled(prefs.isLoggingEnabled())
        // AutoTtsService.onCreate opens with c3.a0.c/c3.a0.b, so every log a
        // user shares names the build it came from -- "Unknown" and -1 are the
        // values a0 answers with when PackageManager cannot find the package.
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onCreate Version Name: " + versionName() + " Version Code: " + versionCode())
        super.onCreate()
        try { if (showNotificationFlag) startForegroundIfPossible() } catch (ex: Exception) { android.util.Log.e("EasyVoice", ex.message!!) }
        requestAudioFocus()
        initIsoMaps()
        loadAllSettings()
        // e0() ends with s0(): the list has just been loaded, so hints go too.
        pushLanguageSets()
        initDone = true
        initAllEngines()
    }
    // c3.a0.a / c3.a0.c / c3.a0.b, down to the logcat tag they report a miss
    // under and the API level at which a0.a switches overloads.
    private fun ownPackageInfo(): android.content.pm.PackageInfo =
        if (Build.VERSION.SDK_INT >= 33)
            packageManager.getPackageInfo(packageName, android.content.pm.PackageManager.PackageInfoFlags.of(0L))
        else { @Suppress("DEPRECATION") packageManager.getPackageInfo(packageName, 0) }
    private fun versionName(): String = try {
        ownPackageInfo().versionName ?: "Unknown"
    } catch (ex: android.content.pm.PackageManager.NameNotFoundException) {
        android.util.Log.e("VersionUtils", "Error getting version name", ex); "Unknown"
    }
    private fun versionCode(): Long = try {
        val info = ownPackageInfo()
        if (Build.VERSION.SDK_INT >= 28) info.longVersionCode
        else { @Suppress("DEPRECATION") info.versionCode.toLong() }
    } catch (ex: android.content.pm.PackageManager.NameNotFoundException) {
        android.util.Log.e("VersionUtils", "Error getting version code", ex); -1L
    }
    override fun onStartCommand(intent: android.content.Intent?, flags: Int, startId: Int): Int = START_STICKY
    override fun onTaskRemoved(rootIntent: android.content.Intent?) { super.onTaskRemoved(rootIntent) }

    // ==========================================================================
    //  THE ENGINE POOL
    //  One TextToSpeech client per installed engine. state == 2 means ready.
    // ==========================================================================
    inner class EngineWrapper(pkgName: String) {
        val pkg: String = pkgName.replace("-","").replace("_","")
        var tts: TextToSpeech? = null
        var state: Int = 0
        var locale: java.util.Locale? = null
        var voiceName: String = ""
        var localeSet: Boolean = false
        var listenerSet: Boolean = false
        var audioAttrSet: Boolean = false
        var restoreCount: Int = 0
        // The engine's own voice set, cached. See loadVoice for why. Cleared
        // wherever `tts` is replaced, because a new TextToSpeech is a new
        // connection to the engine and the old objects belong to the old one.
        @Volatile var voicesCache: MutableSet<android.speech.tts.Voice>? = null
        // AutoTTS's c3.k0.java:78 is ThreadPoolExecutor(0, 5, 60s, LinkedBlockingQueue,
        // h0) with daemon "TtsStop" threads, and k0.m() is just i.execute(new i0(this)).
        // Queuing is right -- onStop() must return promptly, so the binder call into the
        // other engine cannot happen on the caller's thread.
        //
        // DELIBERATE DEPARTURE (owner request, 2026-09-02): the core size is 1, not 0,
        // and the thread is prestarted. With a core of 0 the FIRST stop after an idle
        // spell has to construct a thread before it can even issue the stop, which is
        // pure added delay on the one call that must not be slow. One idle daemon
        // thread per engine wrapper is the price, and it is blocked on the queue.
        val stopExec: java.util.concurrent.ThreadPoolExecutor =
            java.util.concurrent.ThreadPoolExecutor(1, 5, 60L, java.util.concurrent.TimeUnit.SECONDS,
                java.util.concurrent.LinkedBlockingQueue<Runnable>(),
                java.util.concurrent.ThreadFactory { runnable -> Thread(runnable, "TtsStop").apply { isDaemon = true } })
                .apply { prestartCoreThread() }
        fun stop() {
            stopExec.execute {
                try { tts!!.stop() }
                catch (ex: Exception) { android.util.Log.w("EasyVoice", "Stop failed for " + pkg, ex) }
            }
        }
        fun shutdown() {
            stopExec.execute {
                try { tts!!.shutdown() }
                catch (ex: Exception) { android.util.Log.w("EasyVoice", "Shutdown failed for " + pkg, ex) }
            }
        }
    }

    // ==========================================================================
    //  VOICE LOADING               AutoTTS f0, and its two helpers
    //  An empty package means "keep the last one". The "do nothing" short-circuit
    //  fires when the current voice already matches language, country and name.
    // ==========================================================================
    private fun loadVoice(pkg: String, locale: java.util.Locale, variant: String, dedicated: Boolean) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "LoadVoice " + pkg + " " + locale + " " + variant)
        if (dedicated && modeInt != 3) { loadVoiceDedicated(pkg, locale, variant, dedicated); return }
        val normPkg = (if (pkg.isEmpty()) lastEnginePkg else { lastEnginePkg = pkg; pkg }).replace("-","").replace("_","")
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " current engine: " + normPkg)
        var idx = -1
        for (index in 0 until enginePool.size) { if (enginePool[index].pkg == normPkg && enginePool[index].state == 2) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " found!"); idx = index; break } }
        if (idx != -1) {
            engineIndex = idx
            val wrapper = enginePool[idx]
            if (variant.isEmpty() && wrapper.voiceName.isNotEmpty()) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Load voice original"); loadVoiceOriginal(normPkg, locale); return }
            var curLocale = java.util.Locale("zxx")
            var curVoiceName = ""
            if (wrapper.voiceName.isNotEmpty()) {
                val curVoice = wrapper.tts!!.voice
                if (curVoice != null) {
                    curLocale = curVoice.locale
                    curVoiceName = curVoice.name
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " last " + curLocale + " " + curVoiceName)
                    val curCountry = try { curLocale.isO3Country } catch (_: Exception) { "" }
                    val reqCountry = try { locale.isO3Country } catch (_: Exception) { "" }
                    if (localeIso3(curLocale) == localeIso3(locale) &&
                        (curCountry == reqCountry || reqCountry == "") &&
                        curVoiceName == variant) {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Do nothing!"); return
                    }
                }
            }
            var effectiveVariant = variant
            run {
                for (index in 0 until voiceList.size) {
                    val parts = voiceList[index].split("#")
                    if (parts.size < 2 || parts[0] != normPkg || parts[1] != locale.toString()) continue
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "voice: " + voiceList[index])
                    var langIdx = 0
                    while (langIdx < LangStore.languages.size) {
                        val entry = LangStore.languages[langIdx]
                        langIdx++
                        if (entry.enginePkg != normPkg || entry.localeTag != locale.toString()) continue
                        effectiveVariant = entry.variant; break
                    }
                    break
                }
            }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " variant " + effectiveVariant)
            if (effectiveVariant == "*Default") {
                if (!localeMatches(locale, curLocale)) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, locale.toString() + " vs " + curLocale.toString())
                    val setLangResult = wrapper.tts?.setLanguage(locale)
                    if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 1") } else restoreEngine(wrapper.pkg)
                }
            } else if (effectiveVariant != curVoiceName) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Check voice 1")
                // f0 leaves the voice loop with `break block32` only when a voice
                // MATCHED the variant -- success or failure. A list that holds no
                // voice by that name falls straight through to the setLanguage
                // below, and so does a null list. Making the fallback the `else`
                // of `voices != null` cut off the first of those two: with a
                // stored variant naming a voice the engine no longer has --
                // renamed or dropped by an engine update -- AutoTTS still moves
                // the engine to the right LANGUAGE, and we left it wherever it
                // happened to be.
                var matchedAVoice = false
                // TextToSpeech.getVoices() is a BINDER CALL that marshals the
                // engine's ENTIRE voice set -- for Google TTS that is hundreds of
                // Voice objects, each with a name, a Locale, quality, latency and a
                // feature Set. AutoTTS's f0 asks for it on every voice change and we
                // copied that, so switching Hindi -> Gujarati on one engine paid the
                // whole marshal, ON THE MAIN THREAD: onDone posts to the main looper,
                // and onLoadLanguage -> loadVoice runs inside that post. That is the
                // "halka sa delay" the owner hears between languages even when both
                // sit on the same engine (owner request, 2026-09-02).
                //
                // DELIBERATE DEPARTURE FROM AutoTTS: the list is cached per wrapper.
                // It is safe because the set only changes when the engine itself
                // changes -- an update, or newly downloaded voice data -- and both
                // replace `tts`, which clears the cache. A variant that is NOT in the
                // cached list re-queries once and rescans, so a voice installed while
                // the service is alive is still found; that path costs exactly what
                // every call used to cost, and it is the rare one.
                var voices = wrapper.voicesCache
                if (voices == null) {
                    voices = try { wrapper.tts?.voices } catch (_: Exception) { null }
                    wrapper.voicesCache = voices
                }
                fun scanFor(list: MutableSet<android.speech.tts.Voice>?): Boolean {
                    if (list == null) return false
                    for (voiceObj in list) {
                        if (voiceObj.name.equals(effectiveVariant, ignoreCase = true)) {
                            val setVoiceResult = try { wrapper.tts?.setVoice(voiceObj) } catch (_: Exception) { null }
                            if (setVoiceResult != null && setVoiceResult >= 0) { wrapper.localeSet = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 2: " + voiceObj.name + " res=" + setVoiceResult) } else restoreEngine(wrapper.pkg)
                            return true
                        }
                    }
                    return false
                }
                matchedAVoice = scanFor(voices)
                if (!matchedAVoice && voices != null) {
                    val fresh = try { wrapper.tts?.voices } catch (_: Exception) { null }
                    if (fresh != null) { wrapper.voicesCache = fresh; matchedAVoice = scanFor(fresh) }
                }
                if (!matchedAVoice && !localeMatches(locale, curLocale)) {
                    val setLangResult = wrapper.tts?.setLanguage(locale)
                    if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 3: " + locale + " res = " + setLangResult + " " + wrapper.tts.toString()) } else restoreEngine(wrapper.pkg)
                }
            }
            wrapper.locale = locale; wrapper.voiceName = effectiveVariant
        } else { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "TTS is not ready"); engineIndex = -1 }
    }
    private fun loadVoiceOriginal(pkg: String, locale: java.util.Locale) {
        val normPkg = (if (pkg.isEmpty()) lastEnginePkg else { lastEnginePkg = pkg; pkg }).replace("-","").replace("_","")
        var idx = -1
        for (index in 0 until enginePool.size) { if (enginePool[index].pkg == normPkg && enginePool[index].state == 2) { idx = index; break } }
        if (idx != -1) {
            engineIndex = idx; val wrapper = enginePool[idx]
            // g0 runs the comparison unconditionally: the wrapper's locale can
            // be null, and n.e(null) is "zxx" while n.d(null) is "". Skipping
            // the whole test on null is not the same thing -- a request whose
            // own iso3 also resolves to "zxx" would match and AutoTTS would do
            // nothing, where a null check falls through to setLanguage.
            val wrapperLocale = wrapper.locale
            val reqCountry = nullableIso3Country(locale)
            if (nullableIso3(wrapperLocale) == nullableIso3(locale) &&
                (nullableIso3Country(wrapperLocale) == reqCountry || reqCountry.isEmpty())) return
            val setLangResult = wrapper.tts?.setLanguage(locale)
            if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; wrapper.locale = locale; wrapper.voiceName = "" } else restoreEngine(wrapper.pkg)
        } else { engineIndex = -1 }
    }
    private fun loadVoiceDedicated(pkg: String, locale: java.util.Locale, variant: String, dedicated: Boolean) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "loadVoice_Secondary " + pkg + " " + locale + " " + variant)
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Current Engine " + lastEnginePkg)
        val normPkg = (if (pkg.isEmpty()) lastEnginePkg else { lastEnginePkg = pkg; pkg }).replace("-","").replace("_","")
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " current engine: " + normPkg)
        var idx = -1
        for (index in 0 until enginePool.size) { if (enginePool[index].pkg == normPkg && enginePool[index].state == 2) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " found!"); idx = index; break } }
        if (idx == -1) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "TTS is not ready"); engineIndex = -1; return }
        val previousEngineIndex = engineIndex
        engineIndex = idx
        val wrapper = enginePool[idx]
        if (dedicated && wrapper.localeSet) return
        val engineVoice = try { wrapper.tts?.voice } catch (_: Exception) { null }
        val engineLocale = engineVoice?.locale ?: java.util.Locale("zxx")
        val engineName = engineVoice?.name ?: ""
        if (previousEngineIndex == engineIndex && engineVoice != null) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Engine Variant " + engineName)
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Engine Locale " + engineLocale)
            if (localeMatches(locale, engineLocale) &&
                (engineName == variant || variant == "*Default" || variant.isEmpty())) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " *0 Do nothing"); return
            }
        }
        var effectiveVariant = variant
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Searching " + normPkg + " " + locale)
        run {
            for (index in 0 until voiceList.size) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " *" + voiceList[index])
                val parts = voiceList[index].split("#")
                if (parts.size < 2 || parts[0] != normPkg) continue
                var langIdx = 0
                while (langIdx < LangStore.languages.size) {
                    val entry = LangStore.languages[langIdx]
                    langIdx++
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "  -" + entry.enginePkg + " " + entry.localeTag + " " + entry.variant)
                    if (entry.enginePkg != normPkg || !localeMatches(locale, parseVoiceNameAsLocale(entry.localeTag)) || entry.variant.isEmpty()) continue
                    effectiveVariant = entry.variant
                    break
                }
                break
            }
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Variant " + effectiveVariant)
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Locale " + locale)
        if ((effectiveVariant == "*Default" || effectiveVariant.isEmpty()) && !localeMatches(locale, engineLocale)) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " *1")
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, locale.toString() + " vs " + engineLocale)
            val setLangResult = wrapper.tts?.setLanguage(locale)
            if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; wrapper.locale = locale; wrapper.voiceName = locale.variant } else restoreEngine(wrapper.pkg)
            return
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " *2")
        if (engineVoice != null) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Engine Variant " + engineName)
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " Engine Locale " + engineLocale)
            if (localeMatches(locale, engineLocale) &&
                (engineName == effectiveVariant || effectiveVariant == "*Default" || effectiveVariant.isEmpty())) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " *2.1 Do nothing"); return
            }
        }
        val voices = try { wrapper.tts?.voices } catch (_: Exception) { null }
        if (voices != null) {
            for (voiceObj in voices) {
                if (!voiceObj.name.equals(effectiveVariant, ignoreCase = true)) continue
                val setVoiceResult = try { wrapper.tts?.setVoice(voiceObj) } catch (_: Exception) { null }
                if (setVoiceResult != null && setVoiceResult >= 0) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 2: " + voiceObj.name)
                    wrapper.locale = voiceObj.locale; wrapper.voiceName = effectiveVariant; wrapper.localeSet = true
                    return
                }
                restoreEngine(wrapper.pkg)
                break
            }
        }
        if (!localeMatches(locale, engineLocale)) {
            val setLangResult = wrapper.tts?.setLanguage(locale)
            if (setLangResult != null && setLangResult >= 0) { wrapper.locale = locale; wrapper.voiceName = locale.variant; wrapper.localeSet = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 3") } else restoreEngine(wrapper.pkg)
        }
    }

    // ==========================================================================
    //  ENGINE INIT, RESTORE AND KEEP-ALIVE
    // ==========================================================================
    private fun initAllEngines() {
        synchronized(this) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "initAllTTS")
            var index = 0
            while (index < enginePool.size) {
                try {
                    enginePool[index].stop()
                    enginePool[index].shutdown()
                } catch (_: Exception) {}
                index++
            }
            enginePool.clear(); initializingIndex = 0
            if (!engineList.isEmpty()) {
                enginePool.add(EngineWrapper(engineList[initializingIndex]))
                bindEngineKeepAlive(engineList[initializingIndex])
                initializingTts = TextToSpeech(applicationContext, EngineInitListener(), engineList[initializingIndex])
            }
        }
    }
    inner class EngineInitListener : TextToSpeech.OnInitListener {
        override fun onInit(status: Int) {
            if (initializingIndex >= engineList.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "All tts engines have been initialized. (1)"); return }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Init " + (if (initializingIndex < enginePool.size) enginePool[initializingIndex].pkg else ""))
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "res " + status)
            if (status == TextToSpeech.SUCCESS) {
                if (forceAccessibilityFlag) { try { val audioAttributes = android.media.AudioAttributes.Builder().setUsage(11).setContentType(1).build(); initializingTts?.setAudioAttributes(audioAttributes); if (initializingIndex < enginePool.size) enginePool[initializingIndex].audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) } }
                if (initializingIndex < enginePool.size) { enginePool[initializingIndex].tts = initializingTts; enginePool[initializingIndex].voicesCache = null; enginePool[initializingIndex].state = 2 }
                if (engineList[initializingIndex] == "com.google.android.tts") googleEngineIndex = initializingIndex
            } else {
                if (initializingIndex < enginePool.size) { enginePool[initializingIndex].tts = initializingTts; enginePool[initializingIndex].voicesCache = null; enginePool[initializingIndex].state = -1 }
            }
            initializingIndex++
            while (initializingIndex < engineList.size) {
                enginePool.add(EngineWrapper(engineList[initializingIndex]))
                bindEngineKeepAlive(engineList[initializingIndex])
                try { initializingTts = TextToSpeech(applicationContext, EngineInitListener(), engineList[initializingIndex]); break } catch (_: Exception) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Error when initializing " + engineList[initializingIndex]); initializingIndex++ }
            }
            if (initializingIndex >= engineList.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "All tts engines have been initialized. (2)") }
        }
    }
    private fun restoreEngine(pkg: String) {
        synchronized(this) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "restoreTts " + pkg)
            if (restoringIndex != -1) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -Restoring in progress..."); return }
            var idx = -1
            for (index in 0 until enginePool.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + enginePool[index].pkg); if (enginePool[index].pkg.equals(pkg, ignoreCase = true)) { idx = index; break } }
            if (idx == -1) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -restore package name is not found"); return }
            val wrapper = enginePool[idx]
            // AutoTTS's k0.h() is `k == 0 || (elapsed > 3000ms && k < 10)` and
            // k0.i() only ever increments k. BOTH halves are wrong for us and
            // both are gone; what is left is entirely event-driven.
            //
            // THE CLOCK. The 3-second window is a rate limit on RECOVERY, and it
            // is the one place in this file where a delay was actually felt: an
            // engine that dies within 3 seconds of being restored is refused,
            // and nothing schedules a retry -- it simply stays dead until some
            // later failure happens to trigger another attempt. Removing it
            // makes recovery immediate. What it guarded is already guarded
            // without a clock: `restoringIndex != -1` above rejects a second
            // restore while one is in flight, and that flag is cleared by
            // RestoreInitListener, which ALWAYS runs -- every failure path in
            // AOSP's TextToSpeech.initTts ends in dispatchOnInit(ERROR), so the
            // listener cannot be skipped. A restore therefore costs a bind plus
            // an init before another can start; the loop can never be tight.
            //
            // THE CAP. `k < 10` stays, because a genuinely broken engine must
            // stop being retried -- but the streak is now ended by an EVENT
            // rather than being left to run out forever: onStart in the
            // utterance listener zeroes restoreCount the moment the engine
            // actually speaks. "It worked" is what ends a failure streak.
            // Without it, ten deaths over a multi-day process life killed the
            // engine for good, and this service is START_STICKY.
            if (wrapper.restoreCount >= 10) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -restore is not applicable!"); return }
            wrapper.restoreCount++
            restoringIndex = idx
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "restoreTts " + wrapper.pkg)
            try {
                wrapper.stop()
                wrapper.shutdown()
            } catch (_: Exception) {}
            initializingTts = TextToSpeech(applicationContext, RestoreInitListener(), wrapper.pkg)
        }
    }
    inner class RestoreInitListener : TextToSpeech.OnInitListener {
        override fun onInit(status: Int) {
            val restoreIdx = restoringIndex
            if (restoreIdx >= 0 && restoreIdx < enginePool.size) {
                val wrapper = enginePool[restoreIdx]
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Restore " + wrapper.pkg)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "res " + status)
                if (status == TextToSpeech.SUCCESS) {
                    if (forceAccessibilityFlag) { try { val audioAttributes = android.media.AudioAttributes.Builder().setUsage(11).setContentType(1).build(); initializingTts?.setAudioAttributes(audioAttributes); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) } }
                    wrapper.tts = initializingTts; wrapper.voicesCache = null
                    wrapper.state = 2
                    wrapper.voiceName = ""
                    wrapper.locale = null
                } else {
                    wrapper.tts = initializingTts; wrapper.voicesCache = null
                    wrapper.state = -1
                }
            } else { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "ttsInitListener_restore invalid index") }
            restoringIndex = -1
        }
    }
    private fun keepAliveLockFor(pkg: String): Any {
        val fresh = Any()
        return keepAliveLocks.putIfAbsent(pkg, fresh) ?: fresh
    }
    private fun bindEngineKeepAlive(pkg: String) {
        synchronized(keepAliveLockFor(pkg)) {
        if (engineBinders.containsKey(pkg)) return
        try {
            val intent = android.content.Intent("android.intent.action.TTS_SERVICE").setPackage(pkg)
            val resolveInfo = packageManager.resolveService(intent, 0)
            if (resolveInfo == null || resolveInfo.serviceInfo == null) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "No TTS service found in " + pkg); return }
            intent.component = android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name)
            val conn = object : android.content.ServiceConnection {
                override fun onServiceConnected(name: android.content.ComponentName?, service: android.os.IBinder?) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Keep-alive bound to " + name?.flattenToShortString()) }
                override fun onServiceDisconnected(name: android.content.ComponentName?) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Engine process died: " + name?.flattenToShortString()); onEngineProcessGone(pkg); restoreEngine(pkg) }
                override fun onBindingDied(name: android.content.ComponentName?) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Binding died: " + name?.flattenToShortString()); onEngineProcessGone(pkg); unbindEngineKeepAlive(pkg); bindEngineKeepAlive(pkg) }
                override fun onNullBinding(name: android.content.ComponentName?) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Service returned null binding"); unbindEngineKeepAlive(pkg) }
            }
            val bound = try { bindService(intent, conn, android.content.Context.BIND_AUTO_CREATE or android.content.Context.BIND_IMPORTANT) } catch (_: Exception) { false }
            if (bound) engineBinders[pkg] = conn else EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "bindService failed for " + pkg)
        } catch (_: Exception) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "bindService failed for " + pkg) }
        }
    }
    private fun unbindEngineKeepAlive(pkg: String) {
        synchronized(keepAliveLockFor(pkg)) {
            val conn = engineBinders.remove(pkg) ?: return
            try { unbindService(conn) } catch (_: Exception) {}
        }
    }
    private fun unbindAllEngineKeepAlive() { for (boundPkg in engineBinders.keys.toList()) unbindEngineKeepAlive(boundPkg) }

    // ==========================================================================
    //  THE TextToSpeechService API SURFACE
    //  onIsLanguageAvailable answers only 0 or -2, which is why two whole branches
    //  of AutoTTS's d0 are unreachable and must not be ported. See AUTOTTS_MAP.
    // ==========================================================================
    override fun onGetLanguage(): Array<String> {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onGetLanguage")
        val iso3 = localeIso3()
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + iso3)
        return arrayOf(iso3)
    }
    override fun onIsLanguageAvailable(langCode: String?, countryCode: String?, variantName: String?): Int {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onIsLanguageAvailable: " + langCode + " " + countryCode + " " + variantName)
        val raw = langCode ?: ""
        val result = if (LangStore.availableLanguagesFor(null, true).contains(raw)) TextToSpeech.LANG_AVAILABLE else TextToSpeech.LANG_NOT_SUPPORTED
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -res: " + result)
        return result
    }
    override fun onLoadLanguage(langCode: String?, countryCode: String?, variantName: String?): Int {
        return synchronized(this) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onLoadLanguage: " + langCode + " " + countryCode + " " + variantName)
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "loadLanguage " + langCode + " " + countryCode + " " + variantName)
            val result = onIsLanguageAvailable(langCode, countryCode, variantName)
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " isLanguageAvailable = " + result)
            if ((result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) && !langCode.isNullOrEmpty() && ::prefs.isInitialized) {
                val lang = prefs.toIso3(langCode.lowercase())
                val variantOrEmpty = variantName ?: ""
                val autoIso3 = prefs.toIso3(autoLang)
                var voiceLoc = LangStore.localeFor(lang, modeInt)
                var enginePkg = LangStore.engineFor(lang, modeInt)
                val variantOut = if (variantOrEmpty.isEmpty()) LangStore.variantFor(lang, modeInt) else variantOrEmpty
                if (enginePkg.isEmpty()) {
                    voiceLoc = LangStore.localeFor(autoIso3, modeInt)
                    enginePkg = LangStore.engineFor(autoIso3, modeInt)
                }
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "engine: " + enginePkg + " voice " + voiceLoc + " variant " + variantOut)
                try {
                    val loc = parseVoiceNameAsLocale(voiceLoc) ?: return@synchronized TextToSpeech.LANG_NOT_SUPPORTED
                    if (localeIso3(loc) == lang) {
                        loadVoice(enginePkg, loc, variantOut, dedicatedEnginesFlag)
                        if (lastEnginePkg != enginePkg) lastEnginePkg = enginePkg
                    } else {
                        val langOnly = Locale(lang)
                        loadVoice(findEngineForLocale(langOnly), langOnly, variantOut, dedicatedEnginesFlag)
                    }
                } catch (_: Exception) {}
            }
            result
        }
    }
    override fun onGetDefaultVoiceNameFor(langCode: String?, countryCode: String?, variantName: String?): String {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onGetDefaultVoiceNameFor " + langCode + " " + countryCode + " " + variantName)
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + langCode)
        return langCode ?: ""
    }
    override fun onGetVoices(): MutableList<Voice> {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onGetVoices")
        val names = LangStore.availableLanguagesFor(null, true)
        val list = mutableListOf<Voice>()
        for (nameIdx in names.indices) {
            list.add(Voice(names[nameIdx], Locale(names[nameIdx]), 400, 100, false, HashSet<String>()))
        }
        return list
    }
    override fun onIsValidVoiceName(name: String?): Int {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onIsValidVoiceName " + name)
        val result = if (LangStore.availableLanguagesFor(null, true).contains(name)) TextToSpeech.SUCCESS else TextToSpeech.ERROR
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -res " + result)
        return result
    }
    private fun parseVoiceNameAsLocale(voiceName: String?): java.util.Locale? {
        if (voiceName == null) return null
        return try {
            val parts = voiceName.split("_")
            when (parts.size) {
                1 -> java.util.Locale(parts[0])
                2 -> java.util.Locale(parts[0], parts[1])
                3 -> if (parts[2].isEmpty()) java.util.Locale(parts[0], parts[1]) else java.util.Locale(parts[0], parts[1], parts[2])
                else -> null
            }
        } catch (_: Exception) { null }
    }
    private fun findEngineForLocale(locale: java.util.Locale): String {
        val requestedIso3 = localeIso3(locale)
        val requestedCountry = try { locale.isO3Country } catch (_: Exception) { "" }
        val requestedVariant = locale.variant
        for (index in 0 until voiceList.size) {
            val parts = voiceList[index].split("#")
            if (parts.size != 2 && parts.size != 3) continue
            val storedLocale = parseVoiceNameAsLocale(parts[1]) ?: return ""
            val storedCountryIso3 = try { storedLocale.isO3Country } catch (_: Exception) { "" }
            if (requestedIso3 == localeIso3(storedLocale) && requestedCountry == storedCountryIso3 && requestedVariant == storedLocale.variant) return parts[0]
        }
        for (index in 0 until voiceList.size) {
            val parts = voiceList[index].split("#")
            if (parts.size != 2 && parts.size != 3) continue
            val storedLocale = parseVoiceNameAsLocale(parts[1]) ?: return ""
            val storedCountryIso3 = try { storedLocale.isO3Country } catch (_: Exception) { "" }
            if (requestedIso3 == localeIso3(storedLocale) && requestedCountry == storedCountryIso3) return parts[0]
        }
        for (index in 0 until voiceList.size) {
            val parts = voiceList[index].split("#")
            if (parts.size != 2 && parts.size != 3) continue
            val storedLocale = parseVoiceNameAsLocale(parts[1]) ?: return ""
            if (requestedIso3 == localeIso3(storedLocale)) return parts[0]
        }
        return ""
    }
    override fun onLoadVoice(name: String?): Int {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onLoadVoice " + name)
        lastLoadedVoiceName = name ?: ""
        val locale = parseVoiceNameAsLocale(name)
        if (locale == null) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -error"); return TextToSpeech.ERROR }
        val iso3Lang = localeIso3(locale)
        val iso3Country = try { locale.isO3Country } catch (_: Exception) { "" }
        val loadRes = onLoadLanguage(iso3Lang, iso3Country, locale.variant)
        if (loadRes != TextToSpeech.LANG_AVAILABLE &&
            loadRes != TextToSpeech.LANG_COUNTRY_AVAILABLE &&
            loadRes != TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -error"); return TextToSpeech.ERROR
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -success")
        return TextToSpeech.SUCCESS
    }
    override fun onStop() {
        // onStop logs, then calls q0(TRUE), which logs and only then clears R.
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onStop calling!!!")
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "stopAllTts " + true)
        chunkQueue.clear()
        var index = 0
        while (index < enginePool.size) {
            val wrapper = enginePool[index]
            // DELIBERATE DEPARTURE FROM AutoTTS (owner request, 2026-09-02):
            // "kabhi kabhar to vah stop nahi hota hai".
            //
            // AutoTtsService.java:1918 gates the stop on THREE conditions:
            //     if (f.get(i3).f() != 2 || !f.get(i3).g || !f.get(i3).g().isSpeaking()) continue;
            // and we carried all three. The third one is the bug.
            //
            // isSpeaking() is a binder query into ANOTHER app's TTS service, and it
            // answers true only while that engine is actually producing audio. Between
            // our speak() and the engine really starting there is a window in which it
            // answers FALSE. A screen-reader user swiping quickly lands onStop() inside
            // exactly that window: the guard fails, stop() is never called, and the
            // engine then starts speaking with nothing left to cancel it. That is the
            // reported "sometimes it does not stop", and it is AutoTTS's own defect --
            // verified in the decompile, not guessed.
            //
            // eSpeak NG's Android service is the counter-example the owner pointed at:
            //     protected void onStop() { Log.i(TAG, "Received stop request."); mEngine.stop(); }
            // no state query at all. TextToSpeech.stop() on an idle engine is a
            // documented no-op that returns SUCCESS, so asking unconditionally costs
            // one harmless binder call and closes the race.
            if (wrapper.state == 2 && wrapper.listenerSet) {
                try {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " - calling stop for " + wrapper.pkg)
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "stop " + utteranceId)
                    wrapper.stop()
                } catch (ex: Exception) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Stop failed for " + wrapper.pkg + "\n  " + ex.message)
                }
            }
            index++
        }
        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
        synchronized(syncLock) { isFlushed = true; syncLock.notifyAll() }
    }

    // ==========================================================================
    //  LOCALE SPANS                AutoTTS e0.g
    //  Two quirks copied on purpose: getSpans(0, length - 1), and end + 1.
    // ==========================================================================
    private fun splitByLocaleSpans(input: CharSequence): MutableList<TextChunk> {
        val result = mutableListOf<TextChunk>()
        if (localeSpansFlag) {
            val span = SpannableString(input)
            val spans = span.getSpans(0, span.length - 1, LocaleSpan::class.java)
            var prevEnd = 0
            for (localeSpan in spans) {
                val start = span.getSpanStart(localeSpan)
                val end   = span.getSpanEnd(localeSpan)
                if (start > prevEnd) {
                    result.add(TextChunk(span.subSequence(prevEnd, start).toString(), "UNKNOWN"))
                }
                result.add(TextChunk(span.subSequence(start, end).toString(), localeSpan.locale!!.language))
                prevEnd = end + 1
            }
            if (prevEnd < span.length) {
                result.add(TextChunk(span.subSequence(prevEnd, span.length).toString(), "UNKNOWN"))
            }
            return result
        }
        result.add(TextChunk(input.toString(), "UNKNOWN"))
        return result
    }

    // ==========================================================================
    //  DETECTION                   AutoTTS clsCLD2.d / clsCLD2.e / clsCLD2.f
    //  detectLanguage is the windowed detect, detectLanguageRuns the per-span one,
    //  detectLanguageAggregate the most-text-wins fallback. They are hinted
    //  DIFFERENTLY in the native half -- see docs/AUTOTTS_MAP.md.
    // ==========================================================================
    private fun detectLanguage(text: String, latinFallback: String, nonLatinFallback: String): String {
        if (text.isEmpty()) return "UNKNOWN"
        if (text.length == 1 && quickCharacterFlag) return "UNKNOWN"
        run {
            try {
                initIsoMaps()
                val out = NativeEngine.detectLanguageFull(text, latinFallback, nonLatinFallback, disableAdvancedFlag, EasyVoiceLogger.isLoggingEnabled())
                val markerIdx = out.indexOf('\u0001')
                if (markerIdx >= 0) {
                    if (markerIdx + 1 < out.length) for (line in out.substring(markerIdx + 1).split('\n')) if (line.isNotEmpty()) EasyVoiceLogger.debug(EasyVoiceLogger.TAG, line)
                    return out.substring(0, markerIdx)
                }
                return out
            } catch (_: Throwable) {  }
        }
        return "UNKNOWN"
    }
    private fun initIsoMaps() {
        if (isoToIso3.isNotEmpty()) return
        for ((iso2, iso3) in IsoCodes.iso2Pairs()) isoToIso3[iso2] = iso3
        try {
            val entries = isoToIso3.entries.toList()
            NativeEngine.setIsoMap(entries.map { it.key }.toTypedArray(), entries.map { it.value }.toTypedArray())
        } catch (_: Throwable) {}
    }
    private fun hasEngineForLang(lang: String): Boolean {
        val engine = LangStore.engineFor(lang, modeInt)
        return engine.isNotEmpty() && engine != "Disable"
    }
    private fun buildEngineList() {
        synchronized(this) {
            val rebuiltEngineList = ArrayList<String>()
            val isGoogleMode = modeInt == 3
            val defaultEngine = LangStore.engineFor(localeIso3(), if (isGoogleMode) 3 else 0)
            var index = 0
            while (true) {
                val pkg = prefs.getString("engine_$index", "")
                if (pkg.isEmpty() || pkg == "end") break
                if (pkg != defaultEngine) rebuiltEngineList.add(pkg)
                index++
            }
            if (defaultEngine.isNotEmpty() && defaultEngine != "Disable") {
                rebuiltEngineList.add(0, defaultEngine)
            }
            if (rebuiltEngineList.isEmpty() && (try { packageManager.getPackageInfo("com.google.android.tts", 0); true } catch (_: Exception) { false })) {
                rebuiltEngineList.add("com.google.android.tts")
            }
            engineList = rebuiltEngineList
        }
    }
    private fun loadVoiceList() {
        synchronized(this) {
            if (!voiceList.isEmpty()) return
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "LoadVoices")
            val sharedPrefs = getApplicationContext().getSharedPreferences("easy_voice_settings", 0)
            var index = 0
            while (true) {
                val voice = sharedPrefs.getString("voice_$index", "") ?: ""
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + voice)
                if (voice.isEmpty()) {
                    dedicatedEnginesFlag = sharedPrefs.getBoolean("dedicated_engines", false)
                    return
                }
                voiceList.add(voice)
                index++
            }
        }
    }
    // Per utterance. Detect sets only -- pushing the hints from here was a
    // regression: LangStore.languages is a shared static that the settings
    // screens rebuild, and not always whole (dualLangList is two entries,
    // voiceLanguageLabels uses onlyEnabled = true), so re-deriving the hints on
    // every utterance let a visit to the Configuration tab leave CLD2 hinting at
    // two languages for the rest of the process. AutoTTS rebuilds its hint set
    // (c3.n.f) only inside s0(), which it never calls from onSynthesizeText.
    private fun refreshEnabledLangs() {
        pushDetectSetsOnly()
    }
    // The other half of processDirect's packField: U+001D, U+001E and U+001F
    // are escaped there because they double as the field and record separators
    // and can legitimately occur in the text.
    private fun decodeChunkText(field: String): String {
        if (field.indexOf('\u001D') < 0) return field
        val out = StringBuilder(field.length)
        var at = 0
        while (at < field.length) {
            val ch = field[at]
            if (ch == '\u001D' && at + 1 < field.length) {
                val marker = field[at + 1]
                if (marker == '0' || marker == '1' || marker == '2') {
                    out.append(if (marker == '0') '\u001D' else if (marker == '1') '\u001E' else '\u001F')
                    at += 2
                    continue
                }
            }
            out.append(ch)
            at++
        }
        return out.toString()
    }
    private class DetectedRun(val lang: String, val latin: Boolean, val text: String)
    private fun detectLanguageRuns(text: String): List<DetectedRun> {
        val runs = ArrayList<DetectedRun>()
        if (text.isEmpty()) { runs.add(DetectedRun("un", false, "")); return runs }
        // clsCLD2.e folds first and only then measures the length. A lone
        // maths-bold letter is two UTF-16 units, so testing the raw text would
        // miss the single-character case that folding creates.
        val folded = try { NativeEngine.normalizeFancy(text) } catch (_: Throwable) { text }
        if (quickCharacterFlag && folded.length == 1) {
            runs.add(DetectedRun("un", isLatinCommonInherited(folded.codePointAt(0)), folded))
            return runs
        }
        val flat = try { NativeEngine.nativeGetLanguages(folded) } catch (_: Throwable) { return runs }
        var index = 0
        while (index + 2 < flat.size) {
            runs.add(DetectedRun(flat[index], flat[index + 1] == "1", flat[index + 2]))
            index += 3
        }
        return runs
    }
    // AutoTTS 5.7.7.26 clsCLD2.f. Ask for every span the detector can see, add up
    // how much text each language covers, and take the biggest -- except that a
    // real language beats "un" even when "un" covers more, which is what the two
    // separate best-so-far candidates below are for.
    private fun detectLanguageAggregate(text: String): String {
        if (text.isEmpty()) return "un"
        val folded = try { NativeEngine.normalizeFancy(text) } catch (_: Throwable) { text }
        if (quickCharacterFlag && folded.length == 1) return "un"
        val flat = try { NativeEngine.nativeGetLanguages(folded) } catch (_: Throwable) { return "un" }
        if (flat.size < 3) return "un"
        // One triple means one span, so there is nothing to weigh up.
        if (flat.size == 3) return flat[0]
        // A plain HashMap, as clsCLD2.f uses: both best-so-far scans below
        // replace only on a strict >, so an exact tie is settled by whichever
        // entry entrySet() yields first, and that is HashMap's bucket order --
        // not insertion order. Kotlin's HashMap is java.util.HashMap, so the
        // same keys inserted in the same order iterate in the same sequence.
        val totals = HashMap<String, Int>()
        var index = 0
        while (index + 2 < flat.size) {
            val key = flat[index] + "|" + flat[index + 1]
            totals[key] = (totals[key] ?: 0) + flat[index + 2].length
            index += 3
        }
        var bestAny: String? = null
        var bestAnyTotal = -1
        var bestReal: String? = null
        var bestRealTotal = -1
        for (entry in totals.entries) {
            if (entry.value > bestAnyTotal) { bestAny = entry.key; bestAnyTotal = entry.value }
            if (entry.key.startsWith("un|")) continue
            if (entry.value > bestRealTotal) { bestReal = entry.key; bestRealTotal = entry.value }
        }
        val winner = bestReal ?: bestAny ?: return "un"
        return winner.substring(0, winner.indexOf('|'))
    }
    private fun isLatinCommonInherited(codePoint: Int): Boolean {
        val script = Character.UnicodeScript.of(codePoint)
        return script == Character.UnicodeScript.LATIN ||
            script == Character.UnicodeScript.COMMON ||
            script == Character.UnicodeScript.INHERITED
    }
    private fun languageForDetectedRun(run: DetectedRun, latinFallback: String, nonLatinFallback: String): String {
        val byScript = if (run.latin) latinFallback else nonLatinFallback
        var resolved = IsoCodes.toIso3(run.lang) ?: byScript
        val enginePkg = LangStore.engineFor(prefs.toIso3(resolved), modeInt)
        if (enginePkg.isEmpty() || enginePkg == "Disable") resolved = byScript
        return resolved
    }
    // languageForSegmentKind is gone: it applied the number/punctuation/emoji
    // mode ints a second time, after buildMixChunks had already applied them
    // exactly as c3.d0.t does. The resolved type and language now come straight
    // from the segmenter, which is what AutoTTS reads.
    private fun preflightLanguage(lang: String): Boolean {
        val loaded = onLoadLanguage(lang, "", "")
        return loaded != TextToSpeech.LANG_MISSING_DATA && loaded != TextToSpeech.LANG_NOT_SUPPORTED
    }

    // ==========================================================================
    //  SETTINGS
    //  Loaded once into the companion statics and never re-read from prefs on the
    //  synthesis path. INVARIANTS #4 -- prefs hold what was last persisted, the
    //  statics hold what the user has just chosen.
    // ==========================================================================
    private fun loadAllSettings() {
        prefs.getScannedLangs()
        buildEngineList()
        loadVoiceList()
        synchronized(this) { LangStore.loadLanguages(applicationContext) }
        languagesLoaded = true
        loadModeLangsOnce()
        LangStore.loadMode(applicationContext)
        // The service reads the flags through prefs rather than LangStore.loadFlags,
        // so the one-time default flip has to be applied on this path as well. It
        // is guarded by its own marker key, so running it from both is a no-op.
        LangStore.applyAdvancedDetectionDefault(applicationContext)
        localeSpansFlag = prefs.isLocaleSpansEnabled()
        stripAudioAttrFlag = prefs.isStripAudioAttr()
        forceAccessibilityFlag = prefs.isForceAccessibilityStream()
        keepAliveFlag = prefs.isKeepAliveMode()
        showNotificationFlag = prefs.isShowNotification()
        disableAdvancedFlag = prefs.isDisableAdvancedDetection()
        quickCharacterFlag = prefs.isQuickCharacterReading()
        punctuationInFlowFlag = prefs.isPunctuationWithSentence()
        smartNumberFlag = prefs.isSmartNumberReading()
        smartNumberGroupSize = prefs.getSmartNumberGroupSize()
    }
    private fun loadModeLangsOnce() {
        if (autoLang.isNotEmpty()) return
        autoLang = prefs.getString("auto_mode_language", "").ifEmpty { localeIso3() }
        mixLatinLang = prefs.getString("mixed_mode_latin_language", "").ifEmpty { localeIso3() }
        mixNonLatinLang = prefs.getString("mixed_mode_non_latin_language", "").ifEmpty { localeIso3() }
        dualLang = prefs.getString("dual_mode_language", "").ifEmpty { localeIso3() }
        numberModeInt = prefs.getNumberModeLang()
        punctuationModeInt = prefs.getPuncModeLang()
        emojiModeInt = prefs.getEmojiModeLang()
        numberSpecificLang = prefs.getNumberSpecificLang().ifEmpty { localeIso3() }
        puncSpecificLang = prefs.getPuncSpecificLang().ifEmpty { localeIso3() }
        emojiSpecificLang = prefs.getEmojiSpecificLang().ifEmpty { localeIso3() }
    }
    // c3.n.e and c3.n.d, which both accept null: "zxx" and "" respectively.
    private fun nullableIso3(locale: java.util.Locale?): String =
        if (locale == null) "zxx" else localeIso3(locale)
    private fun nullableIso3Country(locale: java.util.Locale?): String =
        if (locale == null) "" else try { locale.isO3Country } catch (_: Exception) { "" }
    private fun localeIso3(locale: java.util.Locale = java.util.Locale.getDefault()): String {
        return try {
            val iso3 = locale.isO3Language
            if (iso3 in listOf("cmn", "lzh", "gan", "hak")) "zho" else iso3
        } catch (_: Exception) { "zxx" }
    }
    private fun localeMatches(requested: java.util.Locale?, candidate: java.util.Locale?): Boolean {
        if (requested == null || candidate == null) return false
        return try {
            if (requested.isO3Language != candidate.isO3Language) return false
            val matchCountry = requested.isO3Country
            if (matchCountry.isEmpty()) return true
            if (matchCountry != candidate.isO3Country) return false
            val matchVariant = requested.variant
            if (matchVariant.isEmpty()) return true
            matchVariant == candidate.variant
        } catch (_: Exception) { false }
    }
    private fun normalizeLangCode(code: String): String = IsoCodes.toIso2(code) ?: code
    // AutoTtsService.P(), new in 5.7.7.26 and called from exactly here -- after
    // the opening log line and before anything else:
    //
    //     synchronized (c3.n.c) {
    //         if (!this.w || c.isEmpty()) { log(...); this.e0(); }
    //     }
    //
    // w is set at the end of e0(), the loader. So the list is reloaded at the
    // point of use if it was never loaded or has come back empty, rather than
    // synthesising with nothing to route by. Ours loaded once in onCreate and
    // never looked again, so a service that started before the scan had written
    // anything kept an empty list for the rest of its life.
    //
    // The guard is read under the list's monitor and the reload then runs
    // OUTSIDE it, which is deliberate. Holding the list monitor across
    // loadLanguages would take `this` while holding `languages`, and
    // onLoadLanguage takes them the other way round -- `this` first, then
    // `languages` inside LangStore.localeFor / engineFor / variantFor. Android
    // calls onLoadLanguage on binder threads, so the two orders can meet and
    // deadlock, and speech would stop with no error at all. AutoTTS has no such
    // hazard: its P() holds only the list monitor and e0() re-enters the same
    // one.
    private fun reloadLanguagesIfMissing() {
        val missing = synchronized(LangStore.languages) {
            !languagesLoaded || LangStore.languages.isEmpty()
        }
        if (!missing) return
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "languages missing at point of use - reloading")
        synchronized(this) { LangStore.loadLanguages(applicationContext) }
        // e0() ends with s0(); this is that.
        pushLanguageSets()
        languagesLoaded = true
    }

    // ==========================================================================
    //  SYNTHESIS                   AutoTTS onSynthesizeText
    //  The one entry point. Order: reload the list if missing, read the request,
    //  pick the mode, build the chunk list in one of five branches below, then
    //  speakChunk walks the queue.
    // ==========================================================================
    override fun onSynthesizeText(request: SynthesisRequest?, callback: SynthesisCallback?) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "\n-------------------------------\nonSynthesizeText")
        reloadLanguagesIfMissing()
        if (showNotificationFlag && !isForegroundActive()) {
            startForegroundIfPossible()
        } else if (!showNotificationFlag && isForegroundActive()) {
            @Suppress("DEPRECATION")
            stopForeground(1)
        }
        synchronized(syncLock) { isStopped = false; syncLock.notifyAll() }
        synchronized(syncLock) { isFlushed = false; syncLock.notifyAll() }
        speakingPkg = null
        val rawCharSeq = request?.charSequenceText ?: ""
        val rawText = rawCharSeq.toString()
        requestRate = (request?.speechRate ?: 100) / 100.0f
        requestPitch = (request?.pitch ?: 100) / 100.0f
        requestParams = request?.params
        requestVolume = requestParams?.getFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 0f) ?: 0f; if (requestVolume == 0f) requestVolume = 1.0f
        utteranceId = (request?.params?.getString("utteranceId")).toString()
        if (rawText.trim().isEmpty()) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Speak text is empty")
            // q0 logs before it touches anything: "stopAllTts <flag>", then
            // removeCallbacks(u) -- which has no counterpart here, because the
            // speak runnable is no longer posted -- then R.clear().
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "stopAllTts " + false)
            chunkQueue.clear()
            if (engineIndex >= 0 && engineIndex < enginePool.size) {
                val wrapper = enginePool[engineIndex]
                if (wrapper.state == 2 && wrapper.listenerSet && wrapper.tts?.isSpeaking == true) {
                    try {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " - calling speak empty for " + wrapper.pkg)
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onSynthesizeText: " + utteranceId + " ''")
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "speak " + utteranceId)
                        wrapper.tts?.speak("", TextToSpeech.QUEUE_FLUSH, null, null)
                    } catch (ex: Exception) {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Speaking failed for " + wrapper.pkg + "\n " + ex.message)
                    }
                }
            }
            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
            synchronized(syncLock) { isFlushed = true; syncLock.notifyAll() }
            startAndFinish(callback)
            return
        }
        val readingMode = when (modeInt) { 1 -> "dual"; 2 -> "auto"; 3 -> "google"; 4 -> "mix"; 5 -> "multilingual"; else -> "none" }
        val isForceAccessibility  = forceAccessibilityFlag
        val isStripAudioAttr      = stripAudioAttrFlag
        val latinFallback = when (readingMode) {
            "mix", "multilingual"  -> normalizeLangCode(mixLatinLang)
            "dual" -> "en"
            else   -> autoLang
        }
        val nonLatinFallback = when (readingMode) {
            "mix", "multilingual"  -> normalizeLangCode(mixNonLatinLang)
            "dual" -> dualLang
            else   -> autoLang
        }
        initIsoMaps()
        refreshEnabledLangs()
        val chunks = mutableListOf<TextChunk>()
        val trimmedText = rawText.trim()
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Speak: " + trimmedText + " id: " + utteranceId)
        var requestedLang = request?.language ?: ""
        val bypassPrefix = trimmedText.length >= 11 && trimmedText.substring(0, 11) == "[EasyVoice:"
        var bypassed = false
        if (bypassPrefix) {
            val splitParts = trimmedText.split("]").dropLastWhile { it.isEmpty() }
            if (splitParts.size == 2) {
                var forcedEng = ""
                var forcedLoc = ""
                var forcedVariant = ""
                val innerParts = splitParts[0].substring(1).split(":").dropLastWhile { it.isEmpty() }
                if (innerParts.size == 4) {
                    forcedEng     = innerParts[1]
                    forcedLoc     = innerParts[2]
                    forcedVariant = innerParts[3]
                    requestedLang      = localeIso3(parseVoiceNameAsLocale(forcedLoc) ?: java.util.Locale(""))
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "FIXED: " + forcedEng + " " + forcedLoc + " " + forcedVariant)
                }
                val stripped = splitParts[1]
                val baseLang: String = run {
                    val loc = forcedLoc
                    if (loc.isBlank()) normalizeLangCode((request?.language ?: "").ifBlank { latinFallback })
                    else try {
                        val localeParts = loc.split("_")
                        val locale: Locale = when (localeParts.size) {
                            1 -> Locale(localeParts[0])
                            2 -> Locale(localeParts[0], localeParts[1])
                            else -> if (localeParts[2].isNotEmpty()) Locale(localeParts[0], localeParts[1], localeParts[2]) else Locale(localeParts[0], localeParts[1])
                        }
                        val iso3 = locale.getISO3Language()
                        when {
                            iso3 == "cmn" || iso3 == "lzh" || iso3 == "gan" || iso3 == "hak" -> "zho"
                            iso3.isEmpty() -> latinFallback
                            else -> iso3
                        }
                    } catch (_: Exception) { latinFallback }
                }
                if (stripped.isNotEmpty()) {
                    chunks.add(TextChunk(stripped, baseLang,
                        forcedEngine = forcedEng.ifEmpty { null },
                        forcedLocale = forcedLoc.ifEmpty { null },
                        forcedVariant = forcedVariant.ifEmpty { null }))
                }
                bypassed = true
            }
        }
        val forceGoogle = readingMode == "google"
        val effectiveMode = when {
            (requestedLang == "zxx" && readingMode != "dual") || readingMode == "auto" || readingMode == "google" -> "auto"
            readingMode == "dual" -> "dual"
            readingMode == "mix" -> "mix"
            readingMode == "multilingual" -> "multilingual"
            else -> "none"
        }
        try {
        if (!bypassed) when (effectiveMode) {
            "none" -> {
                val noneLang = normalizeLangCode(request?.language ?: latinFallback)
                chunks.add(TextChunk(trimmedText, noneLang))
                    }

            // ==========================================================================
            //  branch: MIXED               spans, then segment, then detect each run
            // ==========================================================================
            "mix" -> {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Mixed mode")
                val localeChunks = splitByLocaleSpans(rawCharSeq)
                val neutralType = if (localeIso3() == prefs.toIso3(nonLatinFallback)) 2 else 1
                val neutralDefault = requestedLang.ifEmpty { latinFallback }
                val disableAdvancedDetection = disableAdvancedFlag
                val autoModeF = prefs.toIso3(autoLang)
                for (spanChunk in localeChunks) {
                    if (isFlushed) break
                    if (!spanChunk.lang.equals("unknown", ignoreCase = true) && spanChunk.lang != "") {
                        val resolved = if (hasEngineForLang(spanChunk.lang)) spanChunk.lang else autoModeF
                        chunks.add(TextChunk(spanChunk.text, resolved))
                    } else {
                        val textBytes = spanChunk.text.toByteArray(Charsets.UTF_8)
                        val textBuffer = ByteBuffer.allocateDirect(textBytes.size)
                        textBuffer.put(textBytes); textBuffer.position(0)
                        val chunkOutput = NativeEngine.processDirect(
                            textBuffer, textBytes.size,
                            latinFallback, nonLatinFallback, "mix",
                            numberModeInt, normalizeLangCode(numberSpecificLang),
                            punctuationModeInt, normalizeLangCode(puncSpecificLang),
                            emojiModeInt, normalizeLangCode(emojiSpecificLang), punctuationInFlowFlag, smartNumberFlag,
                            smartNumberGroupSize,
                            neutralDefault, neutralType, disableAdvancedDetection
                        )
                        for (chunkStr in chunkOutput.split('\u001E').filter { it.isNotBlank() }) {
                            val parts = chunkStr.split('\u001F', limit = 4)
                            if (parts.size == 4) {
                                // AutoTTS branches on the type d0.t RESOLVED, never on what
                                // the text looks like. A type of 3, 4 or 5 survives only when
                                // that mode int is 3 ("Specific language"), and the caller
                                // then maps 3 -> K, 4 -> M, 5 -> O -- which is already the
                                // language the segmenter put in this chunk. Types 0, 1 and 2
                                // all go to clsCLD2.e, and modes 1 and 2 rely on that: the
                                // segment was re-typed so it MERGES with the Latin or the
                                // non-Latin run and is detected together with it.
                                val spanMixType = parts[0].toIntOrNull() ?: 0
                                if (spanMixType == 3 || spanMixType == 4 || spanMixType == 5) {
                                    chunks.add(TextChunk(decodeChunkText(parts[3]), prefs.toIso3(normalizeLangCode(parts[2]))))
                                } else {
                                    for (run in detectLanguageRuns(decodeChunkText(parts[3]))) {
                                        chunks.add(TextChunk(run.text, prefs.toIso3(languageForDetectedRun(run, latinFallback, nonLatinFallback))))
                                    }
                                }
                            }
                        }
                    }
                }
                if (chunks.isNotEmpty()) {
                    val firstLang = prefs.toIso3(chunks[0].lang)
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "language: " + firstLang)
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "engine: " + LangStore.engineFor(firstLang, modeInt))
                    if (!preflightLanguage(firstLang)) {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                            // 5.7.7.26 fixed AutoTTS's "Languge" typo at seven of its
                            // nine sites and left it at two: the mix preflight (line
                            // 1962 of the noexc decompile) and the auto/Google one
                            // (1677). Those two are these two.
                            "Languge is not supported: " + firstLang + ", text: " + chunks[0].text)
                        startAndFinish(callback)
                        return
                    }
                }
            }

            // ==========================================================================
            //  branch: MULTILINGUAL        as mixed, but a locale span with an engine wins
            // ==========================================================================
            "multilingual" -> {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Multilingual mode")
                val localeChunks = splitByLocaleSpans(rawCharSeq)
                val neutralType = if (localeIso3() == prefs.toIso3(nonLatinFallback)) 2 else 1
                val neutralDefault = requestedLang.ifEmpty { latinFallback }
                val disableAdvancedDetection = disableAdvancedFlag
                for (spanChunk in localeChunks) {
                    if (isFlushed) break
                    if (!spanChunk.lang.equals("unknown", ignoreCase = true) && spanChunk.lang.isNotEmpty() && hasEngineForLang(spanChunk.lang)) {
                        chunks.add(TextChunk(spanChunk.text, spanChunk.lang))
                    } else {
                        val textBytes = spanChunk.text.toByteArray(Charsets.UTF_8)
                        val textBuffer = ByteBuffer.allocateDirect(textBytes.size)
                        textBuffer.put(textBytes); textBuffer.position(0)
                        val chunkOutput = NativeEngine.processDirect(
                            textBuffer, textBytes.size,
                            latinFallback, nonLatinFallback, "mix",
                            numberModeInt, normalizeLangCode(numberSpecificLang),
                            punctuationModeInt, normalizeLangCode(puncSpecificLang),
                            emojiModeInt, normalizeLangCode(emojiSpecificLang), punctuationInFlowFlag, smartNumberFlag,
                            smartNumberGroupSize,
                            neutralDefault, neutralType, disableAdvancedDetection
                        )
                        for (chunkStr in chunkOutput.split('\u001E').filter { it.isNotBlank() }) {
                            val parts = chunkStr.split('\u001F', limit = 4)
                            if (parts.size != 4) continue
                            // Same branch as mixed above, and for the same reason.
                            val spanMixType = parts[0].toIntOrNull() ?: 0
                            if (spanMixType == 3 || spanMixType == 4 || spanMixType == 5) {
                                chunks.add(TextChunk(decodeChunkText(parts[3]), prefs.toIso3(normalizeLangCode(parts[2]))))
                            } else {
                                for (run in detectLanguageRuns(decodeChunkText(parts[3]))) {
                                    chunks.add(TextChunk(run.text, prefs.toIso3(languageForDetectedRun(run, latinFallback, nonLatinFallback))))
                                }
                            }
                        }
                    }
                }
                if (chunks.isEmpty()) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "lstLanString is empty!")
                    startAndFinish(callback)
                    return
                }
                if (!preflightLanguage(chunks[0].lang)) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                        "Language is not supported: " + chunks[0].lang + ", text: " + chunks[0].text)
                    startAndFinish(callback)
                    return
                }
            }

            // ==========================================================================
            //  branch: DUAL                no detection at all -- the segment TYPE picks it
            // ==========================================================================
            "dual" -> {
                run {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Dual mode")
                    val textBytes = trimmedText.toByteArray(Charsets.UTF_8)
                    val textBuffer = ByteBuffer.allocateDirect(textBytes.size)
                    textBuffer.put(textBytes); textBuffer.position(0)
                    val neutralType = if (localeIso3() == prefs.toIso3(dualLang)) 2 else 1
                    val neutralDefault = requestedLang.ifEmpty { latinFallback }
                    val disableAdvancedDetection = disableAdvancedFlag
                    val chunkOutput = NativeEngine.processDirect(
                        textBuffer, textBytes.size,
                        latinFallback, nonLatinFallback, "dual",
                        numberModeInt, normalizeLangCode(numberSpecificLang),
                        punctuationModeInt, normalizeLangCode(puncSpecificLang),
                        emojiModeInt, normalizeLangCode(emojiSpecificLang), punctuationInFlowFlag, smartNumberFlag,
                        smartNumberGroupSize,
                        neutralDefault, neutralType, disableAdvancedDetection
                    )
                    for (chunkStr in chunkOutput.split('\u001E').filter { it.isNotBlank() }) {
                        val parts = chunkStr.split('\u001F', limit = 4)
                        if (parts.size == 4) {
                            val resolved = normalizeLangCode(parts[2])
                            chunks.add(TextChunk(decodeChunkText(parts[3]), resolved, typeCode = parts[0].toIntOrNull() ?: 0))
                        }
                    }
                    val segmenterEmpty = chunks.isEmpty()
                    if (segmenterEmpty) {
                        chunks.add(TextChunk(trimmedText, requestedLang.ifEmpty { latinFallback }))
                    }
                    if (!segmenterEmpty) {
                        val preflight = when (chunks[0].typeCode) {
                            1 -> "eng"
                            2 -> dualLang
                            3 -> numberSpecificLang
                            4 -> puncSpecificLang
                            5 -> emojiSpecificLang
                            else -> ""
                        }
                        if (preflight.isNotEmpty()) {
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "language: " + preflight)
                            if (!preflightLanguage(preflight)) {
                                EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                                    "Language is not supported: " + preflight + ", text: " + chunks[0].text)
                                startAndFinish(callback)
                                return
                            }
                        }
                    }
                }
            }
            else -> {
                val autoModeF = prefs.toIso3(autoLang)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Auto mode || Google mode")
                val localeChunks = splitByLocaleSpans(rawCharSeq)
                for (spanChunk in localeChunks) {
                    if (isFlushed) break
                    val detected = if (spanChunk.lang.equals("unknown", ignoreCase = true)) {
                        var rawDetected = detectLanguage(spanChunk.text, latinFallback, nonLatinFallback)
                        // 5.7.7.26 adds a third step here: when the span carries no
                        // language of its own AND the main detector still says
                        // unknown, the aggregate detector gets a turn. It costs
                        // nothing on ordinary text, because it only runs once both
                        // earlier answers have failed.
                        if (rawDetected.equals("unknown", ignoreCase = true)) {
                            rawDetected = detectLanguageAggregate(spanChunk.text)
                        }
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Cld2: " + rawDetected + " '" + spanChunk.text + "'")
                        if (rawDetected.length > 2) rawDetected.substring(0, 2) else rawDetected
                    } else spanChunk.lang
                    var resolved = IsoCodes.toIso3(detected) ?: autoModeF
                    val engAuto = LangStore.engineFor(resolved, modeInt)
                    if (engAuto.isEmpty() || engAuto == "Disable") resolved = autoModeF
                    chunks.add(TextChunk(spanChunk.text, resolved))
                }
                if (chunks.isNotEmpty()) {
                    val firstLoad = onLoadLanguage(chunks[0].lang, "", "")
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "load " + firstLoad)
                    if (firstLoad == TextToSpeech.LANG_MISSING_DATA || firstLoad == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // The second surviving "Languge" -- see the mix branch above.
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                            "Languge is not supported: " + chunks[0].lang + ", text: " + chunks[0].text)
                        startAndFinish(callback)
                        return
                    }
                }
            }
        }
        } catch (ex: Exception) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Synthesis ended with error: " + ex.message)
            startAndFinish(callback)
            return
        }
        if (forceGoogle && !bypassed) { chunks.forEach { it.forcedEngine = "com.google.android.tts" } }
        val firstChunkParamLang = when (readingMode) {
            "dual" -> prefs.toIso3(requestedLang.ifEmpty { latinFallback })
            "auto", "google" -> if (chunks.isNotEmpty()) prefs.toIso3(chunks[chunks.size - 1].lang) else ""
            else -> ""
        }
        var firstChunkForced = false
        if (chunks.isNotEmpty()) {
            firstChunkForced = bypassed && (chunks[0].forcedEngine != null || chunks[0].forcedLocale != null)
            if (!firstChunkForced && (bypassed || effectiveMode == "none")) {
                val requestLang = request?.language ?: ""
                val firstLangLoad = onLoadLanguage(requestLang, request?.country ?: "", request?.variant ?: "")
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "load " + firstLangLoad)
                if (firstLangLoad == TextToSpeech.LANG_NOT_SUPPORTED || firstLangLoad == TextToSpeech.LANG_MISSING_DATA) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Language is not supported: " + requestLang + ", text: " + chunks[0].text)
                    startAndFinish(callback)
                    return
                }
            }
        }
        val sysRate  = requestRate
        val sysPitch = requestPitch
        synchronized(chunkQueue) {
            chunkQueue.clear()
            chunkQueue.addAll(chunks.mapIndexed { chunkIdx, queued -> (chunkIdx + 1) to queued })
        }

        // ==========================================================================
        //  THE SPEAK LOOP
        //  Rate, pitch and volume are each the system request times the per-language
        //  setting. onDone arrives on a BINDER thread and posts the next chunk to the
        //  main thread, because the next step may create or shut down a TextToSpeech.
        // ==========================================================================
        fun speakChunk(first: Boolean) {
            if (isStopped || isFlushed) return
            val pair = synchronized(chunkQueue) { if (chunkQueue.isEmpty()) null else chunkQueue.removeAt(0) }
            if (pair == null) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "No more text to read.")
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #7")
                synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                if (callback?.hasStarted() == false) return
                if (callback?.hasFinished() == true) return
                callback?.done()
                return
            }
            val (currentChunk, chunk) = pair
            val effectiveLang = if (modeInt == 1) when (chunk.typeCode) {
                2 -> prefs.toIso3(dualLang)
                3, 4, 5 -> prefs.toIso3(chunk.lang)
                else -> "eng"
            } else prefs.toIso3(chunk.lang)
            val chunkText = chunk.text
            // AutoTTS's bypass branch runs f0 only when the prefix actually
            // carried an engine or a locale, and hands the parsed fields
            // straight through:
            //     if (!engine.isEmpty() || !locale.isEmpty())
            //         f0(engine, j0(locale), variant, false);
            // An empty engine means "keep the last one" inside loadVoice, and
            // j0("") is Locale(""). Ours used to substitute the resolved engine
            // and, with no locale in the prefix, read one back out of
            // SharedPreferences -- a runtime pref read, which is exactly what
            // the settings-live-in-statics rule forbids, and on the Test button
            // of all places: the user changes a voice and presses Test before
            // anything has been persisted.
            if (bypassed && (chunk.forcedEngine != null || chunk.forcedLocale != null)) {
                val forcedLocale = parseVoiceNameAsLocale(chunk.forcedLocale ?: "") ?: Locale("")
                loadVoice(chunk.forcedEngine ?: "", forcedLocale, chunk.forcedVariant ?: "", false)
            }
            if (engineIndex < 0 || engineIndex >= enginePool.size) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "mTTSIndex out of range.")
                startAndFinish(callback)
                return
            }
            val wrapper = enginePool[engineIndex]
            val tts = wrapper.tts ?: run {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "mTTSIndex refers null tts.")
                startAndFinish(callback)
                return
            }
            // "Current engine: " logs the wrapper that is about to speak --
            // AutoTTS reads f.get(d).e() here, the engine actually selected, not
            // the one the language asked for. Resolving a "preferred" package
            // instead meant a whole getEngine4Language block (the header, one
            // line per enabled language, and the result) in the log for every
            // chunk, which AutoTTS never emits at this point.
            val pkg = wrapper.pkg
            val paramLang = if (first && firstChunkParamLang.isNotEmpty()) firstChunkParamLang else effectiveLang
            val appRate   = LangStore.speedFor(paramLang)  / 100.0f
            val appPitch  = LangStore.pitchFor(paramLang)  / 100.0f
            val appVolume = LangStore.volumeFor(paramLang) / 100.0f
            val finalRate   = sysRate  * appRate
            val finalPitch  = sysPitch * appPitch
            val finalVolume = requestVolume * appVolume
            tts.setSpeechRate(finalRate); tts.setPitch(finalPitch)
            if (currentChunk == 1) chunkCounter = 1
            val expectedId = "${utteranceId}_${chunkCounter}"
            val chunkHandler = android.os.Handler(android.os.Looper.getMainLooper())
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String) {
                    // The engine really spoke, so its failure streak is over.
                    // This is what replaces a "healthy for N seconds" clock in
                    // restoreEngine: an event that means exactly "it worked".
                    wrapper.restoreCount = 0
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onStart " + id)
                    if (callback?.hasStarted() == false) { callback?.start(16000, android.media.AudioFormat.ENCODING_PCM_16BIT, 1) }
                }
                override fun onDone(id: String) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onDone " + id)
                    val next = synchronized(chunkQueue) { chunkQueue.firstOrNull()?.second }
                    if (next == null) { speakChunk(false); return }
                    chunkHandler.post {
                        try {
                        chunkCounter++
                        var advance = true
                        if (modeInt == 1) {
                            val nextLang = when (next.typeCode) {
                                1 -> "eng"
                                2 -> dualLang
                                3 -> numberSpecificLang
                                4 -> puncSpecificLang
                                5 -> emojiSpecificLang
                                else -> ""
                            }
                            if (nextLang.isNotEmpty()) {
                                val loadRes = onLoadLanguage(nextLang, "", "")
                                if (loadRes == TextToSpeech.LANG_NOT_SUPPORTED || loadRes == TextToSpeech.LANG_MISSING_DATA) {
                                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Language " + nextLang + " is not supported.\n Text: " + next.text)
                                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #" + (if (next.typeCode == 1) 2 else 3))
                                    synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                                    if (callback?.hasStarted() == true && callback?.hasFinished() == false) callback?.done()
                                    advance = false
                                }
                            }
                        } else if (modeInt == 4 || modeInt == 5) {
                            val nextLang = prefs.toIso3(next.lang)
                            // Mirrors AutoTTS's Q(lang) at this exact point. Its
                            // result is deliberately discarded, and that is NOT an
                            // oversight: AutoTTS only uses it to fall back by
                            // segment type, and that fallback cannot fire.
                            //
                            // c3.e0 has two constructors and only one sets a type:
                            //     e0(String s, int n)    { b = n;  c = ""; }
                            //     e0(String s, String t) { b = -1; c = t;  }
                            // every chunk AutoTTS puts in R for mix and
                            // multilingual uses the SECOND -- the locale-span
                            // objects from e0.g, new e0(text, K/M/O), and
                            // new e0(run.c, lang) alike -- so R.get(0).a() is
                            // always -1 there, none of its cases 1..5 match, and
                            // the language is used unchanged. The same -1 kills the
                            // re-detect above it, because .b() is never empty and
                            // never "unknown" for those chunks.
                            LangStore.engineFor(nextLang, modeInt)
                            val loadRes = onLoadLanguage(nextLang, "", "")
                            if (loadRes == TextToSpeech.LANG_NOT_SUPPORTED || loadRes == TextToSpeech.LANG_MISSING_DATA) {
                                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Language " + nextLang + " is not supported.\n Text: " + next.text)
                                startAndFinish(callback)
                                advance = false
                            }
                        } else {
                            val nextLang = prefs.toIso3(next.lang)
                            val loadRes = onLoadLanguage(nextLang, "", "")
                            if (loadRes == TextToSpeech.LANG_NOT_SUPPORTED || loadRes == TextToSpeech.LANG_MISSING_DATA) {
                                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Language " + nextLang + " is not supported.\n Text: " + next.text)
                                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #4")
                                synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                                if (callback?.hasStarted() == true && callback?.hasFinished() == false) callback?.done()
                                advance = false
                            }
                        }
                        if (advance) speakChunk(false)
                        } catch (ex: Exception) {
                            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onDone Error: " + ex.message)
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #6")
                            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                            if (callback?.hasStarted() == true && callback?.hasFinished() == false) callback?.done()
                        }
                    }
                }
                override fun onError(id: String) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onError " + id)
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #8")
                    synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                    if (callback?.hasStarted() == true && callback?.hasFinished() == false) { callback?.done() }
                }
                override fun onError(id: String, errorCode: Int) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onError " + id + " code " + errorCode)
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #9")
                    synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                    if (callback?.hasStarted() == true && callback?.hasFinished() == false) { callback?.done() }
                }
                override fun onStop(id: String, interrupted: Boolean) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onStop " + id)
                }
            })
            val params = android.os.Bundle(requestParams ?: android.os.Bundle())
            params.remove("pitch"); params.remove("rate")
            params.remove("language"); params.remove("country")
            params.remove("variant"); params.remove("voiceName"); params.remove("utteranceId")
            // "Force to use audio accessibility stream" DID NOT WORK whenever
            // the screen reader supplied its own audio attributes, and that is
            // most of the time. Traced through AOSP rather than guessed:
            //
            //   TextToSpeech.setAudioAttributes() stores the attributes in the
            //   client's own mParams (TextToSpeech.java:1522), and speak() then
            //   merges them with the bundle we hand it:
            //       Bundle bundle = new Bundle(mParams);
            //       bundle.putAll(params);          // TextToSpeech.getParams()
            //   mParams is the BASE and our bundle OVERWRITES it. The bundle we
            //   forward is a copy of the caller's request params, so TalkBack's
            //   own KEY_PARAM_AUDIO_ATTRIBUTES landed on top of the accessibility
            //   attributes this switch had just set, and the downstream engine's
            //   TextToSpeechService.AudioOutputParams.createFromParamsBundle read
            //   the caller's, never ours. The switch was silently a no-op.
            //
            // So forcing the accessibility stream means clearing the caller's
            // attributes out of the forwarded bundle too -- exactly what the
            // strip option already does -- and letting mParams through. The two
            // switches share this line because they need the same removal for
            // opposite reasons: strip so the engine falls back to
            // Engine.DEFAULT_STREAM (STREAM_MUSIC) + CONTENT_TYPE_SPEECH, force
            // so our USAGE_ASSISTANCE_ACCESSIBILITY survives.
            //
            // The two constants below are checked against AOSP AudioAttributes:
            // USAGE_ASSISTANCE_ACCESSIBILITY = 11 (line 186), CONTENT_TYPE_SPEECH
            // = 1 (line 92). They are written as numbers only because the API-15
            // check jar has no AudioAttributes at all; the values are right.
            if (isStripAudioAttr || isForceAccessibility) { params.remove("streamType"); params.remove("audioAttributes") }
            if (finalVolume != 0f) { params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, finalVolume) }
            if (isStopped || isFlushed) return
            wrapper.listenerSet = true
            if (isForceAccessibility && !wrapper.audioAttrSet) {
                try { tts.setAudioAttributes(android.media.AudioAttributes.Builder().setUsage(11).setContentType(1).build()); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) }
            }
            if (first) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Current engine: " + pkg)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, expectedId)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "speak 1: " + chunkText)
            } else {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "speak 2: " + chunkText)
            }
            val speakRunnable = Runnable {
                try {
                    // Whose death would strand this utterance. Set before the
                    // call, so a process that dies during it is still matched.
                    speakingPkg = pkg
                    val speakResult = tts.speak(chunkText, TextToSpeech.QUEUE_FLUSH, params, expectedId)
                    if (speakResult != 0) {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Speaking failed!!!")
                        if (first) {
                            startAndFinish(callback)
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "unlockSynthesis #12")
                            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                        } else {
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #5")
                            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                            if (callback?.hasStarted() == true && callback?.hasFinished() == false) callback?.done()
                        }
                    }
                } catch (ex: Exception) {
                    if (first) {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onSynthesis Error: " + ex.message)
                        startAndFinish(callback)
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "unlockSynthesis #14")
                        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                    } else {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onDone Error: " + ex.message)
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #6")
                        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                        if (callback?.hasStarted() == true && callback?.hasFinished() == false) callback?.done()
                    }
                }
            }
            speakRunnable.run()
        }
        speakChunk(true)
        if (keepAliveFlag) {
            // AutoTTS logs this immediately before k0(callback), and spells it
            // "Keep-live", not "Keep-alive".
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Keep-live activated")
            callback?.start(16000, android.media.AudioFormat.ENCODING_PCM_16BIT, 1)
            val silenceBuf = ByteArray(32)
            val maxBuf = callback?.maxBufferSize ?: silenceBuf.size
            keepAlive@ while (!isStopped) {
                var offset = 0
                while (offset < silenceBuf.size && !isStopped) {
                    val chunkLen = kotlin.math.min(maxBuf, silenceBuf.size - offset)
                    if (callback?.audioAvailable(silenceBuf, offset, chunkLen) != TextToSpeech.SUCCESS) break@keepAlive
                    offset += chunkLen
                }
                var keepAliveInterrupted = false
                synchronized(syncLock) { try { syncLock.wait(100) } catch (ex: InterruptedException) { keepAliveInterrupted = true } }
                if (keepAliveInterrupted) break@keepAlive
            }
        } else {
            // Byte for byte AutoTtsService:2165. It is woken by the utterance
            // listener, by onStop, or by onEngineProcessGone -- never by a clock.
            synchronized(syncLock) {
                while (!isStopped && !isFlushed) {
                    try { syncLock.wait() } catch (ex: InterruptedException) { break }
                }
            }
        }
        speakingPkg = null
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onSynthesizeText ended")
        startAndFinish(callback)
    }

    // ==========================================================================
    //  SHUTDOWN
    // ==========================================================================
    override fun onDestroy() {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onDestroy")
        try {
            @Suppress("DEPRECATION")
            stopForeground(1)
            audioManager!!.abandonAudioFocusRequest(audioFocusRequest!!)
        } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: "") }
        for (index in 0 until enginePool.size) { if (enginePool[index].state == 2) enginePool[index].shutdown() }
        try { unbindAllEngineKeepAlive() } catch (_: Exception) {}
        super.onDestroy()
    }

    // ==========================================================================
    //  STATICS                     AutoTtsService's fields
    //  pushLanguageSets is s0(): detect sets AND hints, and it may only be called
    //  where the language list was just rebuilt. pushDetectSetsOnly is the
    //  per-utterance one. INVARIANTS #1 and #2.
    // ==========================================================================
    companion object {
        // AutoTtsService.s0, "updateLanguage2LetterCodes". It rebuilds the enabled
        // two-letter set from the language list and pushes it to the detector:
        //
        //     n.f.clear(); n.f.addAll(codes);
        //     clsCLD2.i(c3.n.f);        // -> nativeSetLanguageHints
        //
        // AutoTTS runs it from eleven places -- every radio in
        // onRadioButtonClicked, the Modes and Voices tab builds, and the
        // service's own list loader -- so it is a companion function here rather
        // than a private method: the settings screens rebuild the list too, and
        // they have no service instance to call.
        //
        // Ours used to push the hints only in onCreate, so a mode switch left the
        // native detector running on the hints it was given at process start.
        @JvmStatic
        fun pushLanguageSets(): HashSet<String> {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "updateLanguage2LetterCodes")
            val enabledSet = refillEnabledIso2(true)
            try { NativeEngine.setDetectSets(detectOkIso3().toTypedArray(), enabledSet.toTypedArray()) } catch (_: Throwable) {}
            try { NativeEngine.setLanguageHints(enabledSet.toTypedArray()) } catch (_: Throwable) {}
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " " + enabledSet.toString())
            return enabledSet
        }
        // c3.n.f: ONE HashSet for the life of the process, cleared and refilled
        // by s0() rather than replaced. That is not a detail -- a HashMap's
        // table never shrinks on clear(), so the iteration order of a later
        // refill depends on how large the set has ever been, and the order is
        // what decides WHICH 64 codes survive nativeSetLanguageHints' cap when
        // more than 64 languages are enabled. A fresh HashSet each time would
        // always iterate at the small table size and keep a different 64.
        @JvmField val enabledIso2: HashSet<String> = HashSet()
        private fun refillEnabledIso2(logUnmapped: Boolean): HashSet<String> {
            val ordered = ArrayList<String>()
            synchronized(LangStore.languages) {
                var enabledIdx = 0
                while (enabledIdx < LangStore.languages.size) {
                    val entry = LangStore.languages[enabledIdx]
                    enabledIdx++
                    if (entry.disabled || entry.enginePkg.isEmpty() || entry.enginePkg.equals("disable", true)) continue
                    val iso2 = IsoCodes.toIso2(entry.iso3)
                    if (iso2 != null) { if (!ordered.contains(iso2)) ordered.add(iso2) }
                    else if (logUnmapped) EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "unmapped language code: " + entry.iso3)
                }
                enabledIso2.clear()
                enabledIso2.addAll(ordered)
            }
            return enabledIso2
        }
        private fun detectOkIso3(): ArrayList<String> {
            val detectOkList = ArrayList<String>()
            synchronized(LangStore.languages) {
                var detectIdx = 0
                while (detectIdx < LangStore.languages.size) {
                    val entry = LangStore.languages[detectIdx]
                    detectIdx++
                    if (!entry.disabled) detectOkList.add(entry.iso3)
                }
            }
            return detectOkList
        }
        // The detect sets ALONE. This has no AutoTTS counterpart: their detector
        // reads c3.n.c and c3.n.f live from Java, ours lives in C++ and has to be
        // told, so the sets are refreshed per utterance. The HINTS are not
        // refreshed here, and that distinction matters -- see pushLanguageSets.
        @JvmStatic
        fun pushDetectSetsOnly(): HashSet<String> {
            val enabledSet = refillEnabledIso2(false)
            try { NativeEngine.setDetectSets(detectOkIso3().toTypedArray(), enabledSet.toTypedArray()) } catch (_: Throwable) {}
            return enabledSet
        }
        @JvmField val chunkQueue: ArrayList<Pair<Int, TextChunk>> = ArrayList()
        @Volatile @JvmField var engineList: ArrayList<String> = ArrayList()
        @Volatile @JvmField var voiceList: ArrayList<String> = ArrayList()
        @JvmField var dedicatedEnginesFlag = false
        @JvmField var lastLoadedVoiceName = ""
        @JvmField var chunkCounter = 0
        @Volatile @JvmField var utteranceId = ""
        @JvmField var initializingTts: TextToSpeech? = null
        @Volatile @JvmField var showNotificationFlag = false
        @Volatile @JvmField var localeSpansFlag = false
        @Volatile @JvmField var stripAudioAttrFlag = false
        @Volatile @JvmField var forceAccessibilityFlag = false
        @JvmField var numberModeInt = 0
        @JvmField var punctuationModeInt = 0
        @JvmField var emojiModeInt = 0
        @Volatile @JvmField var numberSpecificLang = ""
        @Volatile @JvmField var puncSpecificLang = ""
        @Volatile @JvmField var emojiSpecificLang = ""
        @Volatile @JvmField var punctuationInFlowFlag = true
        @Volatile @JvmField var smartNumberFlag = false
        // AutoTtsService.f0, new in 5.7.7.26. Spaces go in every N digits
        // instead of after every one; 1 reproduces the old behaviour exactly.
        @Volatile @JvmField var smartNumberGroupSize = 1
        @Volatile @JvmField var modeInt = 0
        @Volatile @JvmField var autoLang = ""
        @Volatile @JvmField var dualLang = ""
        @Volatile @JvmField var mixLatinLang = ""
        @Volatile @JvmField var mixNonLatinLang = ""
        @Volatile @JvmField var keepAliveFlag = false
        @Volatile @JvmField var disableAdvancedFlag = true
        @Volatile @JvmField var quickCharacterFlag = false
    }
}
