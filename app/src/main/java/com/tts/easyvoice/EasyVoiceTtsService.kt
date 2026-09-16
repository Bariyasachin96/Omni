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
    // The restore's bind timeout. Main looper, because that is where every other
    // engine callback in this file is delivered and where EngineFinder posts its
    // own. Only one restore is ever in flight (restoringIndex enforces that), so
    // one handler with one pending message is the whole requirement.
    private val restoreTimeoutHandler by lazy { android.os.Handler(android.os.Looper.getMainLooper()) }
    private val engineBinders = java.util.concurrent.ConcurrentHashMap<String, android.content.ServiceConnection>()
    private val keepAliveLocks = java.util.concurrent.ConcurrentHashMap<String, Any>()
    @Volatile var engineIndex = -1
    private lateinit var prefs: SharedPrefsManager
    private val syncLock = Object()
    @Volatile private var isStopped = false
    @Volatile private var isFlushed = false
    // ---- WHICH UTTERANCE A CALLBACK BELONGS TO ------------------------------
    // Bumped once at the top of every onSynthesizeText and captured by that
    // utterance's listener closures. Three different threads touch the speaking
    // state -- the screen reader's synthesis thread runs onSynthesizeText, the
    // target engine's callbacks arrive on OUR binder threads, and onDone posts
    // the next-chunk step to the main looper -- and until this existed nothing
    // said which utterance an arriving callback belonged to.
    //
    // Only onSynthesizeText writes it, and AOSP guarantees exactly one writer:
    // SynthHandler is a single HandlerThread, SpeechItem.play() throws on a
    // second call, and playImpl() is the only caller of onSynthesizeText. So the
    // read-modify-write needs no lock; @Volatile is for the readers.
    @Volatile private var synthesisGeneration = 0
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
        // NORMALISED, because the two sides were not (2026-09-11). speakingPkg is
        // assigned `wrapper.pkg`, and EngineWrapper's constructor strips "-" and
        // "_" from the package name; this callback is handed the RAW name out of
        // engineList. For any engine package containing an underscore the compare
        // could never match, so the one mechanism that unparks the synthesis
        // thread when an engine dies was silently disabled for exactly those
        // engines -- and a missed match here is the total failure this whole
        // field exists to prevent.
        if (speakingPkg != pkg.replace("-","").replace("_","")) return
        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Engine holding this utterance died: " + pkg)
        speakingPkg = null
        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
    }
    // `appCtx` USED TO SIT HERE AND IT LEAKED THE WHOLE SERVICE (2026-09-10).
    // It was `lateinit var appCtx: Context`, assigned `appCtx = this` on the
    // first line of onCreate, and read by NOTHING -- swept for readers across
    // the whole app and there are none (SharedPrefsManager's `appCtx` is a
    // different, private, instance field).
    //
    // It is the vestige of AutoTTS's `this.h = this`, and the difference is the
    // whole defect: AutoTTS's `h` is an INSTANCE field, so it dies with the
    // service, and it exists because `c3.l0.b(this.h)` -- the licence gate,
    // which is this project's standing carve-out -- reads it. Ours was moved
    // into the COMPANION during the port, which makes it a static holding a
    // strong reference to the Service object for the life of the process,
    // surviving onDestroy and every restart of the service. Our refactor's bug,
    // not AutoTTS's, and with no reader there is nothing to keep.
    @Volatile var googleEngineIndex = -1
    var requestVolume = 1.0f
    var requestRate = 1.0f
    var requestPitch = 1.0f
    @Volatile var requestParams: android.os.Bundle? = null
    var initializingIndex = 0
    // TRUE only once setIsoMap has actually reached the native side. It used to
    // be the emptiness of a scratch HashMap, and that was wrong twice -- see
    // initIsoMaps.
    @Volatile private var isoMapPushed = false
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
    // NotificationChannel is API 26 and minSdk is 24, so this is the SECOND
    // NoClassDefFoundError the API-24 compile found. It is less severe than the
    // audio-focus one only because "Show persistent notification" is off by
    // default -- with it on, an Android 7 phone crashed here instead.
    //
    // Below 26 there is no channel to create and none is needed:
    // NotificationCompat.Builder takes the channel id on every level and simply
    // ignores it under 26, and startForeground has never required one there.
    // AutoTTS makes the same unguarded call and does not need the guard; its
    // own minSdk is at least 26. See requestAudioFocus for the same split.
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) return
        createNotificationChannel26()
    }
    private fun createNotificationChannel26() {
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
    // THE ONE LISTENER BOTH PATHS USE. Below API 26 the focus is abandoned by
    // handing this same object back, so it has to outlive the request.
    private val audioFocusListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            -2 -> EasyVoiceLogger.debug("TTS", "Audio focus lost temporarily")
            -1 -> EasyVoiceLogger.debug("TTS", "Audio focus lost")
            1 -> EasyVoiceLogger.debug("TTS", "Audio focus gained")
        }
    }

    // AudioFocusRequest IS API 26, AND minSdk IS 24 -- THIS CRASHED EVERY
    // ANDROID 7 PHONE (found 2026-09-10 by compiling the whole app against API
    // 24's own android.jar, which is the only thing that can see this).
    //
    // requestAudioFocus() is called BARE from onCreate, with no try/catch, and
    // AudioFocusRequest.Builder resolves its class the moment that line runs.
    // On API 24 or 25 that is a NoClassDefFoundError -- an Error, NOT an
    // Exception, so even a catch would not have held it -- thrown out of the
    // service's onCreate. The TTS service died on startup and the phone had no
    // voice at all. Nothing in the app could recover from it.
    //
    // AutoTTS does NOT have this defect: it makes the same unguarded call, but
    // the lowest SDK_INT guard anywhere in its code is 28, so its own minSdk is
    // at least 26 and API 24 is not a device it claims. Ours is 24 -- the same
    // "our refactor's bug, not AutoTTS's" split as releaseWaitWithoutSpeaking
    // and the double scan -- so the guard is ours to add and is not a rule 5
    // divergence: on 26 and up this is byte for byte l0() as before.
    //
    // The pre-26 branch is AOSP's own equivalent rather than a guess.
    // AudioAttributes.toVolumeStreamType maps USAGE_ASSISTANCE_ACCESSIBILITY to
    // STREAM_ACCESSIBILITY -- and that constant is ITSELF API 26, so below it
    // there is no accessibility stream in the audio policy at all. The right
    // stream for speech there is STREAM_MUSIC, which is what the TTS framework
    // itself falls back to: Engine.DEFAULT_STREAM is STREAM_MUSIC, and
    // AudioOutputParams.createFromParamsBundle builds exactly that pairing when
    // no attributes are supplied.
    private fun requestAudioFocus() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val result = if (Build.VERSION.SDK_INT >= 26) requestAudioFocus26()
        else {
            @Suppress("DEPRECATION")
            audioManager!!.requestAudioFocus(audioFocusListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
        EasyVoiceLogger.debug("TTS", "Audio focus request: " + (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED))
    }
    // Its own method on purpose, which is the shape Android's own guidance and
    // PackageInfoCompat's Api28Impl both use: the verifier only ever has to
    // resolve AudioFocusRequest when this method is entered, and on API 24 it
    // never is.
    private fun requestAudioFocus26(): Int {
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(attrs)
            .setOnAudioFocusChangeListener(audioFocusListener)
            .build()
        audioFocusRequest = focusRequest
        return audioManager!!.requestAudioFocus(focusRequest)
    }
    private fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= 26) abandonAudioFocus26()
        else { @Suppress("DEPRECATION") audioManager!!.abandonAudioFocus(audioFocusListener) }
    }
    private fun abandonAudioFocus26() {
        audioManager!!.abandonAudioFocusRequest(audioFocusRequest!!)
    }

    // ==========================================================================
    //  LIFECYCLE
    //  onCreate is ordering-critical: context, logger, prefs and the logging flag
    //  come BEFORE super.onCreate(), and the language sets are pushed only after
    //  loadAllSettings has filled them.
    // ==========================================================================
    override fun onCreate() {
        EasyVoiceLogger.init(this)
        prefs = SharedPrefsManager(this)
        EasyVoiceLogger.setLoggingEnabled(prefs.isLoggingEnabled())
        // AutoTtsService.onCreate opens with c3.a0.c/c3.a0.b, so every log a
        // user shares names the build it came from -- "Unknown" and -1 are the
        // values a0 answers with when PackageManager cannot find the package.
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onCreate Version Name: " + versionName() + " Version Code: " + versionCode())
        super.onCreate()
        // THE `!!` HERE WAS A CRASH, AND THE OWNER OVERRODE THE PARITY DEFENCE
        // (2026-09-10). It was `ex.message!!`, which is AutoTTS's own
        // `Objects.requireNonNull(exception2.getMessage())` byte for byte -- and
        // that is exactly what makes it a defect rather than a style point: a
        // great many exceptions carry a NULL message (a bare SecurityException,
        // a framework NullPointerException, any `throw Foo()`), so on the one
        // path that exists to REPORT a failure it would throw a second
        // exception, out of onCreate, and take the whole TTS service down with
        // it. The phone would then have no voice at all.
        //
        // I had answered this with "unreachable", and the reachability argument
        // is real but it is not a licence to leave a landmine on the reporting
        // path: startForegroundIfPossible() wraps its whole body in its own
        // `catch (Exception)`, so the only things that reach here are an Error
        // (which `catch (ex: Exception)` does not hold anyway) or a throw from
        // EasyVoiceLogger inside that inner catch. The owner asked for it fixed
        // and they are right -- the cost of being wrong is total.
        //
        // `?: ""` is what AutoTTS's OWN p0() does at the identical site
        // (`c3.n.a.d("AutoTTS", exception2.getMessage())`, no requireNonNull),
        // so this is not even a shape AutoTTS lacks; it is the one of its two
        // spellings that does not crash.
        try { if (showNotificationFlag) startForegroundIfPossible() } catch (ex: Exception) { android.util.Log.e("EasyVoice", ex.message ?: ex.toString()) }
        // EVERY STEP IS GUARDED SEPARATELY (2026-09-11), and this is the most
        // catastrophic failure in the whole app: a throw anywhere in onCreate
        // leaves the TTS service dead, the phone with no voice at all, and
        // START_STICKY restarting it straight back into the same state. Two real
        // crashes of exactly this shape were found on 2026-09-10 (AudioFocusRequest
        // and NotificationChannel, both API 26 against minSdk 24), and both were
        // total for every Android 7 device.
        //
        // Guarding each step SEPARATELY rather than the block as a whole is the
        // point: these five are independent, so a device on which one of them
        // fails should still get the other four. An app that has loaded its
        // settings and its engines but could not take audio focus still speaks; an
        // app that skipped initAllEngines because requestAudioFocus threw does not.
        //
        // getSystemService(AUDIO_SERVICE) as AudioManager is the concrete new risk
        // here -- `as` on a null answer is an NPE, and a heavily modified OEM build
        // is exactly where a system service comes back null.
        try { requestAudioFocus() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "requestAudioFocus failed: " + ex.toString()) }
        try { initIsoMaps() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "initIsoMaps failed: " + ex.toString()) }
        try { loadAllSettings() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "loadAllSettings failed: " + ex.toString()) }
        // e0() ends with s0(): the list has just been loaded, so hints go too.
        try { pushLanguageSets() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "pushLanguageSets failed: " + ex.toString()) }
        initDone = true
        try { initAllEngines() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "initAllEngines failed: " + ex.toString()) }
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
        // TRUE means "the TextToSpeech client currently in `tts` has had our
        // USAGE_ASSISTANCE_ACCESSIBILITY attributes installed on it". AutoTTS's
        // k0.h is the same flag, but it is only ever set to true (AutoTtsService
        // noexc:2052, :2130, :2438) and never cleared, which is wrong for a
        // field that describes a client object AutoTTS itself replaces on
        // restore. Cleared wherever `tts` is replaced -- see RestoreInitListener.
        var audioAttrSet: Boolean = false
        var restoreCount: Int = 0
        // The engine's own voice set, cached. See loadVoice for why. Cleared
        // wherever `tts` is replaced, because a new TextToSpeech is a new
        // connection to the engine and the old objects belong to the old one.
        @Volatile var voicesCache: MutableSet<android.speech.tts.Voice>? = null
        // WHAT `TextToSpeech.getVoice()` WOULD ANSWER, WITHOUT ASKING (2026-09-11).
        //
        // getVoice() is NOT a local read. AOSP:
        //     public Voice getVoice() { ... return getVoice(service, voiceName); }
        //     private Voice getVoice(service, name) { List<Voice> voices = service.getVoices(); ... }
        // -- so every call marshals the engine's ENTIRE voice set across a binder
        // and then picks one entry out of it by name, client-side. For Google TTS
        // that is hundreds of Voice objects, each with a name, a Locale, two ints,
        // a boolean and a feature Set. It is the same expensive call the 2026-09-02
        // pass already cached for the VARIANT SCAN, and it was left uncached here.
        //
        // The name it looks up is `mParams[KEY_PARAM_VOICE_NAME]`, which AOSP writes
        // in exactly two places, so this pair can mirror it EXACTLY rather than
        // approximately:
        //   setVoice(v)      -> on SUCCESS only, param = v.name  => currentVoice = v
        //   setLanguage(loc) -> on success, param = the engine's own
        //                       getDefaultVoiceNameFor(...), a name we are never
        //                       told  => currentVoiceKnown = false, ask next time
        //   a failed call writes nothing                         => leave both alone
        //   a replaced `tts` is a new client with no params       => null, known
        //
        // So a binder call happens only where we genuinely do not know the answer,
        // and never on the repeat path.
        @Volatile var currentVoice: android.speech.tts.Voice? = null
        @Volatile var currentVoiceKnown: Boolean = true
        // Called wherever `tts` is replaced. A new TextToSpeech is a new connection
        // with empty mParams, so getVoice() would answer null and the old engine's
        // Voice objects belong to the old connection.
        //
        // localeSet JOINED THIS SET ON 2026-09-16, AND IT IS THE "USE DEDICATED
        // ENGINES" SILENCE (owner: "jaise hi maine on kiya tha to vah sabhi TTS
        // bolna band ho gaya tha").
        //
        // localeSet means "setLanguage or setVoice has succeeded on the client
        // currently in `tts`". Replace `tts` and that is simply no longer true --
        // the new client has had neither call made on it. RestoreInitListener
        // already clears voiceName, locale and audioAttrSet for exactly this
        // reason; localeSet is the one field of that set the 2026-09-03 pass
        // missed. Putting it HERE rather than at the listener is what stops it
        // being missed again: this function's whole contract is "the client was
        // replaced", and all four replacement sites go through it.
        //
        // WHY A STALE `true` IS FATAL RATHER THAN UNTIDY. loadVoiceDedicated's
        // first act is `if (dedicated && localeSet) return` -- so with the switch
        // ON, a wrapper carrying a stale true is never handed a language or a
        // voice again, and, far worse, NEVER CALLS setLanguage OR setVoice AT
        // ALL. Those two calls are the only things that can notice the client is
        // dead, and their failure branch is the only thing that calls
        // restoreEngine. So the ordinary loadVoice path heals itself after a bad
        // restore -- it tries, fails, and restores again -- and the dedicated
        // path cannot: it returns before it can discover anything. The engine
        // stays at state 2 holding a client that can never speak, nothing in the
        // app looks at it again, and only force-stopping the process clears it.
        //
        // AutoTTS leaves its k0.f stale here too, and its restore listener
        // (noexc:2404-2406) clears only `e` and `d`. This is the same DELIBERATE
        // DEPARTURE already taken for audioAttrSet on 2026-09-03, on the same
        // reasoning and at the same site: a flag that describes a client must not
        // outlive the client it describes.
        fun forgetClientState() {
            voicesCache = null
            currentVoice = null
            currentVoiceKnown = true
            localeSet = false
        }
        fun voiceNow(): android.speech.tts.Voice? {
            if (currentVoiceKnown) return currentVoice
            val asked = try { tts?.voice } catch (_: Exception) { null }
            currentVoice = asked
            currentVoiceKnown = true
            return asked
        }
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
        // THE CLIENT IS CAPTURED HERE, NOT READ INSIDE THE RUNNABLE (2026-09-16).
        //
        // `tts!!` inside the lambda is a read of this wrapper's FIELD at the
        // moment the worker runs it, which can be long after the call: stopExec
        // is ThreadPoolExecutor(core 1, max 5, UNBOUNDED LinkedBlockingQueue),
        // and an unbounded queue's offer() never fails, so execute() never
        // reaches addWorker and the pool can never grow past one. maximumPoolSize
        // = 5 is dead config -- every stop and shutdown for this engine runs
        // strictly FIFO on ONE thread, behind whatever is already queued.
        //
        // restoreEngine is the site where that matters, because it is the only
        // one that REUSES the wrapper: it queues stop() and shutdown() and then
        // immediately builds a replacement client, and RestoreInitListener
        // assigns `tts = <the new client>` on the main looper. If the queued
        // shutdown has not run by then -- one stop already ahead of it in the
        // queue is enough -- it reads the field and shuts down THE BRAND NEW
        // CLIENT. The wrapper is then at state 2 holding an unbound client, which
        // is the state the localeSet fix above describes.
        //
        // Capturing removes the window outright: these methods are asked to stop
        // or shut down the client the wrapper holds NOW, and that is what they
        // do. A null client is nothing to shut down, so there is no work to
        // queue -- which is also what the old `tts!!` achieved, by throwing an
        // NPE into its own catch and logging a failure that had not happened.
        //
        // initAllEngines and onDestroy were never exposed to this: neither
        // reassigns the wrapper's client afterwards.
        fun stop() {
            val client = tts ?: return
            stopExec.execute {
                try { client.stop() }
                catch (ex: Exception) { android.util.Log.w("EasyVoice", "Stop failed for " + pkg, ex) }
            }
        }
        fun shutdown() {
            val client = tts ?: return
            stopExec.execute {
                try { client.shutdown() }
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
                // voiceNow(), not `wrapper.tts!!.voice` -- see the field for why that
                // read is a full voice-set marshal and how this mirrors it exactly.
                // (It also fixes a second defect: `!!` on a client that state == 2
                // does NOT guarantee is non-null, because restoreEngine shuts the old
                // one down and constructs a replacement without clearing state, and
                // both init listeners assign cell[0], which is null on AOSP's
                // inline-ERROR dispatch.)
                val curVoice = wrapper.voiceNow()
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
                    // THE LAST TWO UNSYNCHRONIZED WALKS OF LangStore.languages WERE
                    // HERE AND IN loadVoiceDedicated (2026-09-11), and they were the
                    // two on the SPEAKING path -- the one place this hole costs a
                    // blind user speech rather than a settings screen.
                    //
                    // The 2026-09-10 pass closed every other reader and made
                    // LangStore.replaceAll the only writer, precisely so a rebuild on
                    // the main thread cannot tear a walk on another one. It missed
                    // these two. A bare `size` read followed by `get(index)` against a
                    // clear()+addAll is not a stale element, it is
                    // IndexOutOfBoundsException -- thrown out of loadVoice, which is
                    // reached from onLoadLanguage on binder threads, from the main
                    // looper inside onDone's post, and from speakChunk's bypass branch
                    // on the synthesis thread itself.
                    //
                    // entryAt is the accessor the same pass introduced: it takes the
                    // list monitor for ONE element and answers null past the end, so
                    // no lock is held across the body and the logging inside the
                    // sibling loop cannot nest the logger's monitor inside this one
                    // (INVARIANTS #3).
                    var langIdx = 0
                    while (true) {
                        val entry = LangStore.entryAt(langIdx) ?: break
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
                    if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 1") } else restoreEngine(wrapper.pkg)
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
                // THE RE-QUERY BELOW USED TO RUN BACK TO BACK WITH THIS ONE
                // (2026-09-11). On the first switch the cache is null, so we fetch
                // the whole set here, the scan fails to find the variant, and the
                // `!matchedAVoice` branch immediately fetched THE SAME SET AGAIN --
                // two full voice-set marshals, one behind the other, for identical
                // data. The re-query only makes sense against a cache that is OLD
                // enough to have gone stale, which is exactly what it was written
                // for ("a voice installed while the service is alive").
                val cacheWasFresh = voices == null
                if (voices == null) {
                    voices = try { wrapper.tts?.voices } catch (_: Exception) { null }
                    wrapper.voicesCache = voices
                }
                fun scanFor(list: MutableSet<android.speech.tts.Voice>?): Boolean {
                    if (list == null) return false
                    for (voiceObj in list) {
                        if (voiceObj.name.equals(effectiveVariant, ignoreCase = true)) {
                            val setVoiceResult = try { wrapper.tts?.setVoice(voiceObj) } catch (_: Exception) { null }
                            if (setVoiceResult != null && setVoiceResult >= 0) { wrapper.localeSet = true; wrapper.currentVoice = voiceObj; wrapper.currentVoiceKnown = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 2: " + voiceObj.name + " res=" + setVoiceResult) } else restoreEngine(wrapper.pkg)
                            return true
                        }
                    }
                    return false
                }
                matchedAVoice = scanFor(voices)
                if (!matchedAVoice && voices != null && !cacheWasFresh) {
                    val fresh = try { wrapper.tts?.voices } catch (_: Exception) { null }
                    if (fresh != null) { wrapper.voicesCache = fresh; matchedAVoice = scanFor(fresh) }
                }
                if (!matchedAVoice && !localeMatches(locale, curLocale)) {
                    val setLangResult = wrapper.tts?.setLanguage(locale)
                    if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 3: " + locale + " res = " + setLangResult + " " + wrapper.tts.toString()) } else restoreEngine(wrapper.pkg)
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
            if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; wrapper.locale = locale; wrapper.voiceName = "" } else restoreEngine(wrapper.pkg)
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
        // THIS GUARD PINS ONE VOICE PER **ENGINE**, NOT PER LANGUAGE, and until
        // 2026-09-11 it was the only path in the app that could keep the wrong
        // voice while writing NOTHING to the log.
        //
        // It is AutoTTS's, byte for byte -- h0 (noexc:1023) wraps its whole body
        // in `if (!bl || !((k0)f.get(d)).f)`, which is `if (dedicated &&
        // localeSet) skip` written the other way round, and `localeSet` is
        // assigned at the SAME seven sites in both apps (three in loadVoice, one
        // in loadVoiceOriginal, three here). So the guard itself is parity and
        // stays.
        //
        // WHAT WAS A DEFECT WAS THE FLAG IT READS, and that is fixed upstream in
        // forgetClientState() (2026-09-16) -- read the comment there before
        // touching anything here. In short: restoring an engine replaces `tts`
        // without clearing localeSet, so this guard would return for a client
        // that had never had a language loaded into it, and -- because the guard
        // returns BEFORE any setLanguage or setVoice -- nothing could ever
        // discover that the client was dead, so the restoreEngine those failures
        // trigger never ran either. The plain loadVoice path heals itself from a
        // bad restore precisely because it does make those calls and does see
        // them fail; this path could not, and that is why "Use dedicated
        // engines" went permanently silent where the normal path only stuttered.
        //
        // What it MEANS is worth stating, because it is surprising and it
        // matches a real report: "Use dedicated engines" assumes one engine per
        // language. Put Hindi, Gujarati and Marathi all on Google TTS and the
        // FIRST of them to load pins that engine's voice; every other language
        // on it is skipped here and speaks in the first one's voice, for the
        // life of the process, whatever the detector said. The owner reported
        // exactly that on 2026-09-11 (Marathi read in the Hindi voice, either
        // order), and detection was measured clean -- see
        // tools/verify/devanagari/run.sh.
        //
        // The line below is a LOG, not a behaviour change. Without it the one
        // path that can silently keep the wrong voice left no trace at all, so
        // no log the owner sends could ever name it.
        if (dedicated && wrapper.localeSet) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG,
                " *dedicated: keeping " + wrapper.voiceName + " on " + wrapper.pkg +
                ", not loading " + locale.toString() + " " + variant)
            return
        }
        // voiceNow(), for the reason written on the field: `tts.voice` is a full
        // voice-set marshal, and this path runs on every language change when
        // "Use dedicated engines" is on.
        val engineVoice = wrapper.voiceNow()
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
                // Same fix as loadVoice's walk above, and the same reason.
                var langIdx = 0
                while (true) {
                    val entry = LangStore.entryAt(langIdx) ?: break
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
            if (setLangResult != null && setLangResult >= 0) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; wrapper.locale = locale; wrapper.voiceName = locale.variant } else restoreEngine(wrapper.pkg)
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
        // CACHED, as loadVoice's scan already is (2026-09-11). This read was left
        // uncached when the 2026-09-02 pass cached the other one, so with "Use
        // dedicated engines" on, EVERY language change marshalled the engine's
        // whole voice set -- the same cost that pass was written to remove, on the
        // path it was written for.
        var voices = wrapper.voicesCache
        if (voices == null) {
            voices = try { wrapper.tts?.voices } catch (_: Exception) { null }
            wrapper.voicesCache = voices
        }
        if (voices != null) {
            for (voiceObj in voices) {
                if (!voiceObj.name.equals(effectiveVariant, ignoreCase = true)) continue
                val setVoiceResult = try { wrapper.tts?.setVoice(voiceObj) } catch (_: Exception) { null }
                if (setVoiceResult != null && setVoiceResult >= 0) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 2: " + voiceObj.name)
                    wrapper.currentVoice = voiceObj; wrapper.currentVoiceKnown = true
                    wrapper.locale = voiceObj.locale; wrapper.voiceName = effectiveVariant; wrapper.localeSet = true
                    return
                }
                restoreEngine(wrapper.pkg)
                break
            }
        }
        if (!localeMatches(locale, engineLocale)) {
            val setLangResult = wrapper.tts?.setLanguage(locale)
            if (setLangResult != null && setLangResult >= 0) { wrapper.currentVoiceKnown = false; wrapper.locale = locale; wrapper.voiceName = locale.variant; wrapper.localeSet = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Set voice 3") } else restoreEngine(wrapper.pkg)
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
            // THE FIRST ENGINE'S CONSTRUCTOR IS GUARDED NOW (2026-09-10), and it
            // was the last bare one in the app. restoreEngine's own comment even
            // claimed "initAllEngines already guards the identical call" -- it
            // did not: only EngineInitListener's walk over engines 2..N was
            // wrapped, and engine ONE was constructed bare, right here.
            //
            // A TextToSpeech constructor does real work: it reads
            // Settings.Secure, resolves the engine and calls bindService, so it
            // throws when that engine is mid-update -- exactly what a Play Store
            // update of a TTS engine produces, because the package is briefly
            // unresolvable. And this runs from the SERVICE's onCreate, which
            // does not wrap it either, so the throw killed onCreate: the TTS
            // service dead at startup, the phone with no voice at all, and
            // START_STICKY restarting it straight back into the same state.
            //
            // The recovery is not invented -- it is byte for byte the loop
            // EngineInitListener already uses for every OTHER engine: log, step
            // initializingIndex, try the next one. On the happy path this is
            // what the old code did, statement for statement. A wrapper left in
            // the pool at state 0 is inert: loadVoice* only ever matches
            // state == 2 and onEngineProcessBack only acts on state == -1, which
            // is already true of the listener's failure path.
            while (initializingIndex < engineList.size) {
                enginePool.add(EngineWrapper(engineList[initializingIndex]))
                bindEngineKeepAlive(engineList[initializingIndex])
                val cell = arrayOfNulls<TextToSpeech>(1)
                try {
                    cell[0] = TextToSpeech(applicationContext, EngineInitListener(cell), engineList[initializingIndex])
                    break
                } catch (_: Exception) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Error when initializing " + engineList[initializingIndex])
                    // state = -1, NOT the 0 it was left at (2026-09-16). See the
                    // note above: a state-0 wrapper is inert, but it is also
                    // INVISIBLE TO EVERY RECOVERY PATH -- onEngineProcessBack acts
                    // only on -1, and initAllEngines runs once from onCreate. So a
                    // constructor that threw because the engine was mid-update left
                    // that engine dead for the life of the process even after it
                    // came back. -1 is what "this wrapper has no usable client"
                    // already means, and it is what the listener's own failure path
                    // writes; 0 now means only "the walk has not reached it yet",
                    // which is what keeps onEngineProcessBack's -1 test correct.
                    if (initializingIndex < enginePool.size) enginePool[initializingIndex].state = -1
                    initializingIndex++
                }
            }
        }
    }
    // EACH INIT OWNS ITS CLIENT, and the shared field it replaces was a real
    // cross-engine bug (2026-09-09). `initializingTts` was ONE field written by
    // the pool walk here AND by restoreEngine, and read back by both listeners
    // in their onInit -- with no mutual exclusion between them, because
    // initAllEngines and restoreEngine each take `this` while the listeners do
    // not. Both run on the main thread, so they interleave at message
    // boundaries, which is exactly where this lands:
    //
    //   pool walk constructs TextToSpeech(engine3) and returns to the looper
    //   engine5 dies -> onServiceDisconnected -> restoreEngine overwrites the field
    //   engine3's onInit arrives and does enginePool[3].tts = <engine5's client>
    //
    // Engine 3's wrapper then holds a client bound to engine 5, so every
    // utterance routed to engine 3 is spoken by engine 5 -- wrong voice, wrong
    // language -- and once the restore lands too, two wrappers share one client
    // and each one's setLanguage/setVoice clobbers the other's.
    //
    // The holder array is the same shape the engine scan uses, and for the same
    // AOSP reason: onInit can fire INLINE on the constructing thread, but only
    // ever with ERROR (TextToSpeech.java 871, 877, 907, 2385, 2476 -- the only
    // dispatch that can carry SUCCESS is inside SetupConnectionAsyncTask.
    // onPostExecute, which is always asynchronous). So on SUCCESS the
    // constructor has long returned and cell[0] is set; on the inline ERROR path
    // cell[0] is still null, and storing null there is strictly better than
    // storing some other engine's client on a wrapper we are marking dead.
    inner class EngineInitListener(private val cell: Array<TextToSpeech?>) : TextToSpeech.OnInitListener {
        // Guarded 2026-09-11 for the same two reasons as RestoreInitListener: this
        // is the main looper, so an escape is a process death, and it is also the
        // only thing that advances the pool walk -- every engine after the one that
        // threw would never be initialised, and every language on those engines
        // would answer "TTS is not ready" for the life of the process.
        override fun onInit(status: Int) {
            try {
            val initializingTts = cell[0]
            if (initializingIndex >= engineList.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "All tts engines have been initialized. (1)"); return }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Init " + (if (initializingIndex < enginePool.size) enginePool[initializingIndex].pkg else ""))
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "res " + status)
            if (status == TextToSpeech.SUCCESS) {
                if (forceAccessibilityFlag) { try { val audioAttributes = android.media.AudioAttributes.Builder().setUsage(11).setContentType(1).build(); initializingTts?.setAudioAttributes(audioAttributes); if (initializingIndex < enginePool.size) enginePool[initializingIndex].audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) } }
                if (initializingIndex < enginePool.size) { enginePool[initializingIndex].tts = initializingTts; enginePool[initializingIndex].forgetClientState(); enginePool[initializingIndex].state = 2 }
                if (engineList[initializingIndex] == "com.google.android.tts") googleEngineIndex = initializingIndex
            } else {
                if (initializingIndex < enginePool.size) { enginePool[initializingIndex].tts = initializingTts; enginePool[initializingIndex].forgetClientState(); enginePool[initializingIndex].state = -1 }
            }
            initializingIndex++
            while (initializingIndex < engineList.size) {
                enginePool.add(EngineWrapper(engineList[initializingIndex]))
                bindEngineKeepAlive(engineList[initializingIndex])
                val nextCell = arrayOfNulls<TextToSpeech>(1)
                try { nextCell[0] = TextToSpeech(applicationContext, EngineInitListener(nextCell), engineList[initializingIndex]); break } catch (_: Exception) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Error when initializing " + engineList[initializingIndex]); if (initializingIndex < enginePool.size) enginePool[initializingIndex].state = -1; initializingIndex++ }
            }
            if (initializingIndex >= engineList.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "All tts engines have been initialized. (2)") }
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Engine init failed: " + ex.toString())
            }
        }
    }
    private fun restoreEngine(pkg: String) {
        synchronized(this) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "restoreTts " + pkg)
            if (restoringIndex != -1) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -Restoring in progress..."); return }
            // NORMALISED, like every other pool lookup in this file (2026-09-16).
            // EngineWrapper.pkg strips "-" and "_", and onServiceDisconnected hands
            // this the RAW name out of engineList -- so for any engine package
            // carrying either character the restore answered " -restore package
            // name is not found" and the engine was never recovered. That is the
            // SAME defect already fixed in onEngineProcessGone on 2026-09-11, one
            // line below it in the same callback, and missed here.
            val normPkg = pkg.replace("-","").replace("_","")
            var idx = -1
            for (index in 0 until enginePool.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, " -" + enginePool[index].pkg); if (enginePool[index].pkg.equals(normPkg, ignoreCase = true)) { idx = index; break } }
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
            // The try/catch is NOT decoration, and its absence was a
            // process-lifetime wedge. restoringIndex was already set above, and
            // a TextToSpeech constructor does real work -- it reads
            // Settings.Secure, resolves the engine and calls bindService -- so
            // it can throw, exactly when the engine is mid-update. The throw
            // would then leave restoringIndex >= 0 for ever, and the guard at
            // the top of this method answers " -Restoring in progress..." to
            // every restore attempt for the rest of the process: no engine can
            // ever be recovered again. (This used to say "initAllEngines
            // already guards the identical call" -- that was WRONG until
            // 2026-09-10: only the listener's walk over engines 2..N was
            // guarded, and the FIRST engine was constructed bare. Both sites
            // are guarded now.)
            //
            // AND THE COMMENT ABOVE WAS ONLY HALF THE WEDGE (2026-09-16). The note
            // twelve lines up says restoringIndex needs no clock because
            // "RestoreInitListener ALWAYS runs -- every failure path in AOSP's
            // initTts ends in dispatchOnInit(ERROR)". **THAT IS FALSE**, read from
            // AOSP rather than recalled:
            //
            //     private boolean connectToEngine(String engine) {
            //         boolean bound = connection.connect(engine);
            //         if (!bound) { ...; return false; }
            //         mConnectingServiceConnection = connection; return true;   // <-- no dispatch
            //     }
            //     ... if (connectToEngine(defaultEngine)) { mCurrentEngine = ...; return SUCCESS; }
            //
            // initTts returns SUCCESS the moment bindService returns true and
            // dispatches NOTHING; the dispatch happens later, from
            // Connection.onServiceConnected -> SetupConnectionAsyncTask.onPostExecute.
            // Its dispatchOnInit(ERROR) is only the fall-through for "no engine
            // could be bound at all". So when the bind SUCCEEDS but the engine
            // process never actually comes up -- which is precisely a Play Store
            // update -- onInit NEVER FIRES, and TextToSpeech has no timeout of its
            // own anywhere.
            //
            // restoringIndex is written here and cleared in exactly two places, both
            // of which need that callback. So it stayed >= 0 FOR EVER, and the guard
            // at the top of this method then answered " -Restoring in progress..."
            // to every restore of EVERY engine for the rest of the process. Total,
            // permanent, and force-stopping the app is the only cure -- the owner's
            // report, symptom for symptom.
            //
            // This is not a new mechanism: EngineFinder.startEngine was repaired
            // away from this exact shape on 2026-09-09 (owner override, "properly
            // sources ke through fix karo") and its 30 s per-engine timeout is the
            // number reused here. shutdown() on the abandoned client is AOSP's own
            // way to PREVENT the late callback -- while still connecting it calls
            // mConnectingServiceConnection.disconnect(), after which
            // onServiceConnected never arrives -- and the one-shot flag is the belt
            // to that brace, for a callback already queued on the looper.
            //
            // NO LATENCY IS ADDED: nothing new waits. The timeout only stops
            // abandoned work, and it fires on a path where speech is already
            // impossible.
            val cell = arrayOfNulls<TextToSpeech>(1)
            val done = java.util.concurrent.atomic.AtomicBoolean(false)
            try {
                cell[0] = TextToSpeech(applicationContext, RestoreInitListener(cell, idx, done), wrapper.pkg)
                restoreTimeoutHandler.postDelayed({
                    if (!done.compareAndSet(false, true)) return@postDelayed
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                        "restore init never arrived for " + wrapper.pkg + " -- releasing the restore slot")
                    try { cell[0]?.shutdown() } catch (_: Throwable) {}
                    wrapper.state = -1
                    wrapper.forgetClientState()
                    restoringIndex = -1
                }, 30000L)
            } catch (ex: Exception) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when restoring " + wrapper.pkg + ": " + ex.message)
                done.set(true)
                wrapper.state = -1
                restoringIndex = -1
            }
        }
    }
    // DELIBERATE DEPARTURE, and the second half of the "chalte chalte ruk jata
    // hai" fix (owner, 2026-09-03). state = -1 was a TERMINAL state and nothing
    // in either app could leave it.
    //
    // Every one of the seven in-app restoreEngine call sites lives inside
    // loadVoice / loadVoiceOriginal / loadVoiceDedicated, and all three find
    // their wrapper with `pkg == normPkg && state == 2`. The eighth is
    // onServiceDisconnected, which fires once per death. So the moment a
    // wrapper lands on -1 -- one failed init, which is exactly what a Play
    // Store update of the engine produces, because the package is briefly
    // unresolvable and bindService fails -- NOTHING can ever call restoreEngine
    // for it again. engineIndex then answers -1 for every language on that
    // engine, for the life of the process, and the only cure is force-stopping
    // the app. That is the owner's report, symptom for symptom, including why
    // force stop is what fixes it.
    //
    // The recovery signal was already arriving and nothing was listening --
    // the same shape as onEngineProcessGone, in the other direction. We hold a
    // binding to every engine, so Android calls onServiceConnected the moment
    // that process is back up. No clock, no retry timer, no polling: the event
    // IS "this engine is alive again".
    //
    // state == -1 is the exact test, never `!= 2`: a wrapper that has never
    // been initialised is 0, and the first bind fires this callback too, while
    // EngineInitListener is still walking the pool. -1 can only be reached by
    // an init or a restore that actually failed.
    //
    // restoreCount is zeroed for the same reason onStart zeroes it: the engine
    // demonstrably came back, and that is an event, not an interval. Without
    // it, ten failed restores would leave the cap holding the engine dead
    // through a reconnect that would have worked.
    private fun onEngineProcessBack(pkg: String) {
        val normPkg = pkg.replace("-","").replace("_","")
        for (index in 0 until enginePool.size) {
            val wrapper = enginePool[index]
            if (wrapper.pkg != normPkg) continue
            // THE STREAK ENDS ON THE RECONNECT, WHATEVER THE STATE (2026-09-16).
            // This reset used to sit inside the `state == -1` branch, so a wrapper
            // that reached restoreCount == 10 while sitting at state 2 -- ten
            // restores that each SUCCEEDED but never got to speak, which is what a
            // repeatedly-bouncing engine produces -- could never be restored again:
            // the cap refuses, onStart cannot fire because the client is dead, and
            // nothing else zeroes the count. The engine's process demonstrably
            // coming back is an EVENT, and it is the right one to end a failure
            // streak on; it is the same reasoning onStart's reset already rests on.
            wrapper.restoreCount = 0
            if (wrapper.state == -1) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Engine is back, retrying: " + wrapper.pkg)
                restoreEngine(wrapper.pkg)
            }
            return
        }
    }
    // Its own client, for the reason written over EngineInitListener: the
    // shared field was also written by the pool walk.
    //
    // THIS COMMENT USED TO END "restoringIndex itself is sound -- restoreEngine
    // refuses a second restore while one is in flight and this listener always
    // runs to clear it". The second half was WRONG and it was the process-lifetime
    // wedge: AOSP's initTts returns SUCCESS as soon as bindService does, without
    // dispatching, so when the bind succeeds and the engine process never starts
    // this listener never runs at all. The timeout in restoreEngine is what makes
    // the sentence true now; read the note there before changing either.
    inner class RestoreInitListener(
        private val cell: Array<TextToSpeech?>,
        private val idx: Int,
        private val done: java.util.concurrent.atomic.AtomicBoolean
    ) : TextToSpeech.OnInitListener {
        // try/finally ADDED 2026-09-11. `restoringIndex = -1` at the bottom is the
        // only thing that lets ANY engine be restored again -- restoreEngine's own
        // first guard answers " -Restoring in progress..." while it is set -- so an
        // escape from this method wedges recovery for the life of the process,
        // which is the same process-lifetime wedge the unguarded TextToSpeech
        // constructor produced on 2026-09-03, reached a different way. This runs on
        // the main looper, where an escape is also a process death and therefore a
        // silent phone. The finally runs the same statement at the same point when
        // nothing throws.
        override fun onInit(status: Int) {
            // ONE SHOT, AND THE INDEX IS CAPTURED (2026-09-16). Two reasons, both
            // from the timeout above. A callback that arrives AFTER the timeout has
            // already released the slot must touch nothing -- it would otherwise
            // clear a restoringIndex belonging to a NEWER restore and hand a dead
            // client to whatever wrapper that index now names -- so it releases its
            // own client and returns without reaching the finally. And the index is
            // the one this restore was started for rather than whatever
            // restoringIndex happens to hold when the callback lands, which is the
            // same fix EngineInitListener's shared-field defect needed on
            // 2026-09-09.
            if (!done.compareAndSet(false, true)) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Restore init arrived after the slot was released; discarding that client")
                try { cell[0]?.shutdown() } catch (_: Throwable) {}
                return
            }
            restoreTimeoutHandler.removeCallbacksAndMessages(null)
            try {
            val initializingTts = cell[0]
            val restoreIdx = idx
            if (restoreIdx >= 0 && restoreIdx < enginePool.size) {
                val wrapper = enginePool[restoreIdx]
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Restore " + wrapper.pkg)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "res " + status)
                // DELIBERATE DEPARTURE (owner request, 2026-09-03). Both branches
                // below replace `tts` with a brand new TextToSpeech, and audio
                // attributes live in THAT object's mParams, so whatever the old
                // client carried is gone. AutoTTS never clears k0.h here, which
                // leaves the flag claiming attributes are installed on a client
                // that has none: if the force switch happened to be OFF at the
                // moment of a restore, turning it back ON afterwards did nothing
                // for the rest of the process -- the speak path's
                // `!wrapper.audioAttrSet` guard skipped the install, and the
                // forwarded bundle had its own attributes removed, so the engine
                // fell back to STREAM_MUSIC. The switch was dead on that engine.
                wrapper.audioAttrSet = false
                if (status == TextToSpeech.SUCCESS) {
                    if (forceAccessibilityFlag) { try { val audioAttributes = android.media.AudioAttributes.Builder().setUsage(11).setContentType(1).build(); initializingTts?.setAudioAttributes(audioAttributes); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) } }
                    wrapper.tts = initializingTts; wrapper.forgetClientState()
                    wrapper.state = 2
                    wrapper.voiceName = ""
                    wrapper.locale = null
                } else {
                    wrapper.tts = initializingTts; wrapper.forgetClientState()
                    wrapper.state = -1
                }
            } else { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "ttsInitListener_restore invalid index") }
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Restore init failed: " + ex.toString())
            } finally {
                restoringIndex = -1
            }
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
            // ALL FOUR ARE GUARDED (2026-09-11). Android delivers every
            // ServiceConnection callback on the MAIN LOOPER, so an escape from any
            // of them is an uncaught exception on the main thread and therefore the
            // death of this process -- and a dead TTS engine process is a screen
            // reader with no voice. onServiceDisconnected is the worst of the four:
            // it is the path that both unparks a waiting synthesis thread
            // (onEngineProcessGone) and recovers the engine (restoreEngine), so a
            // throw in the first would silently skip the second.
            val conn = object : android.content.ServiceConnection {
                override fun onServiceConnected(name: android.content.ComponentName?, service: android.os.IBinder?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Keep-alive bound to " + name?.flattenToShortString()); onEngineProcessBack(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onServiceConnected: " + ex.toString()) } }
                override fun onServiceDisconnected(name: android.content.ComponentName?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Engine process died: " + name?.flattenToShortString()); onEngineProcessGone(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onServiceDisconnected: " + ex.toString()) }; try { restoreEngine(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore after disconnect: " + ex.toString()) } }
                // restoreEngine ADDED 2026-09-16. This callback did everything
                // onServiceDisconnected does EXCEPT the restore, so a binding death
                // left the wrapper at state 2 holding a client bound to a process
                // that is gone -- and state 2 is the one state onEngineProcessBack
                // cannot recover. A binding death is exactly what a Play Store
                // update of a TTS engine produces, which is the case the owner
                // keeps hitting.
                override fun onBindingDied(name: android.content.ComponentName?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Binding died: " + name?.flattenToShortString()); onEngineProcessGone(pkg); unbindEngineKeepAlive(pkg); bindEngineKeepAlive(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onBindingDied: " + ex.toString()) }; try { restoreEngine(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore after binding died: " + ex.toString()) } }
                override fun onNullBinding(name: android.content.ComponentName?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "Service returned null binding"); unbindEngineKeepAlive(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onNullBinding: " + ex.toString()) } }
            }
            val bound = try { bindService(intent, conn, android.content.Context.BIND_AUTO_CREATE or android.content.Context.BIND_IMPORTANT) } catch (_: Exception) { false }
            if (bound) {
                engineBinders[pkg] = conn
            } else {
                // A FAILED bindService STILL LEAVES THE CONNECTION REGISTERED,
                // and only unbindService takes it back. Read from AOSP rather
                // than from the return value's wording: ContextImpl.
                // bindServiceCommon calls mPackageInfo.getServiceDispatcher(conn,
                // ...) -- which registers it -- BEFORE it asks the
                // ActivityManager, and when the AM answers 0 (this false) that
                // registration is NOT undone. unbindService is what calls
                // forgetServiceDispatcher.
                //
                // So every failed bind leaked one dispatcher entry, and this is
                // not a one-shot path: onBindingDied does unbind-then-bind, so a
                // flaky engine leaks one per cycle on a START_STICKY service that
                // can run for days, until the context is destroyed and logcat
                // says "ServiceConnection ... leaked".
                try { unbindService(conn) } catch (_: Exception) {}
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "bindService failed for " + pkg)
            }
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
            // There used to be a `&& ::prefs.isInitialized` here. It was ours, not
            // AutoTTS's, and it could only ever do harm: if it had been false this
            // method would have reported the language available and loaded no voice
            // at all, silently. It also cannot be false. AOSP's own
            // TextToSpeechService.onCreate ENDS with
            //     onLoadLanguage(defaultLocale[0], defaultLocale[1], defaultLocale[2]);
            // so the earliest possible call is our own `super.onCreate()`, six lines
            // after `prefs` is assigned, and no binder call can arrive before
            // onCreate returns. AutoTTS's onCreate has the same shape -- logger,
            // version log, super.onCreate(), and only then e0()/n.o()/n.q().
            if ((result == TextToSpeech.LANG_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_AVAILABLE || result == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) && !langCode.isNullOrEmpty()) {
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
                } catch (ex: Throwable) {
                    // Silent until 2026-09-11, and this is the one place a "wrong
                    // voice" or "no voice" bug can start: a throw here means the
                    // voice was never loaded, engineIndex may already have been
                    // written, and the utterance goes on to speak with whatever the
                    // engine happened to be set to. Catching is right -- this runs
                    // on binder threads, where an escape is returned to the CALLER,
                    // i.e. it would surface as a crash inside the screen reader --
                    // but swallowing it without a line meant the log the owner
                    // shares said nothing about it. Throwable for the same reason
                    // as everywhere else on this path.
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "loadVoice failed for " + lang + ": " + ex.toString())
                }
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
        // Under the monitor the other four accesses already use. onStop runs on
        // a binder thread while speakChunk may be popping on the synthesis or
        // main thread, and a bare ArrayList.clear() against a concurrent
        // removeAt is not merely a lost element -- it throws, and an exception
        // here would abort onStop before the two lines at the bottom that
        // release the parked synthesis thread.
        // EVERYTHING BEFORE THE RELEASE IS IN A try/finally (2026-09-11).
        //
        // These last two lines are what unparks the screen reader's ONE synthesis
        // thread when it interrupts us, and until now anything above them could
        // stop them running. That is not hypothetical: enginePool is a plain
        // ArrayList with no lock, walked here on a BINDER thread while
        // EngineInitListener.onInit appends to it on the main thread, so a
        // `get(index)` can land on the old backing array with the new size and
        // throw ArrayIndexOutOfBounds. wrapper.stop() and the two logger calls can
        // throw as well. Any of them aborted onStop before the release, and the
        // parked thread then had nothing left that could wake it -- the whole
        // device silent until our process is killed, which is the exact report.
        //
        // The finally cannot change behaviour when nothing throws: it runs the
        // same two statements in the same order at the same point.
        try {
        synchronized(chunkQueue) { chunkQueue.clear() }
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
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onStop failed before releasing: " + ex.toString())
        } finally {
            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
            synchronized(syncLock) { isFlushed = true; syncLock.notifyAll() }
        }
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
        try {
            initIsoMaps()
            val out = detectLanguageFull(text, latinFallback, nonLatinFallback, disableAdvancedFlag, EasyVoiceLogger.isLoggingEnabled())
            val markerIdx = out.indexOf('\u0001')
            if (markerIdx >= 0) {
                if (markerIdx + 1 < out.length) for (line in out.substring(markerIdx + 1).split('\n')) if (line.isNotEmpty()) EasyVoiceLogger.debug(EasyVoiceLogger.TAG, line)
                return out.substring(0, markerIdx)
            }
            return out
        } catch (_: Throwable) {  }
        return "UNKNOWN"
    }
    // THE "ALREADY DONE" FLAG USED TO BE SET BEFORE THE WORK, AND THE MAP IT
    // TESTED WAS A PLAIN HashMap WRITTEN FROM TWO THREADS (2026-09-11).
    //
    //     if (isoToIso3.isNotEmpty()) return                 <- the flag
    //     for (...) isoToIso3[iso2] = iso3                   <- sets the flag
    //     try { setIsoMap(...) } catch (_: Throwable) {}     <- the actual work
    //
    // Two defects, and the first one is permanent. If setIsoMap threw -- an
    // OutOfMemoryError out of the JNI reads guarded on 2026-09-10, or an
    // UnsatisfiedLinkError -- the Kotlin map was already full, so the guard was
    // satisfied and THE NATIVE SIDE KEPT AN EMPTY ISO MAP FOR THE LIFE OF THE
    // PROCESS. Every toIso3() in the core then answers the code unchanged, so no
    // detected language resolves to anything the engine list knows and the app
    // reads everything in the fallback language, or nothing at all, until it is
    // force-stopped. One transient failure, permanent damage.
    //
    // Second: initIsoMaps is called from onCreate on the main thread AND from
    // every onSynthesizeText on the synthesis thread, and java.util.HashMap is
    // not safe for concurrent writes -- two threads resizing one together is the
    // classic livelock, which on this path is a spinning synthesis thread and a
    // silent phone.
    //
    // Both go away by making the flag mean what it says and dropping the map,
    // which had no other reader: it existed only to build the two arrays and to
    // be its own "done" marker. The retry is free -- onSynthesizeText calls this
    // once per utterance, so a transient failure is repaired by the next thing
    // the user reads instead of lasting until a restart.
    private fun initIsoMaps() {
        if (isoMapPushed) return
        try {
            val pairs = IsoCodes.iso2Pairs().toList()
            setIsoMap(pairs.map { it.first }.toTypedArray(), pairs.map { it.second }.toTypedArray())
            isoMapPushed = true
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "setIsoMap failed, will retry: " + ex.toString())
        }
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
        val folded = try { normalizeFancy(text) } catch (_: Throwable) { text }
        if (quickCharacterFlag && folded.length == 1) {
            runs.add(DetectedRun("un", isLatinCommonInherited(folded.codePointAt(0)), folded))
            return runs
        }
        val flat = try { nativeGetLanguages(folded) } catch (_: Throwable) { return runs }
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
        val folded = try { normalizeFancy(text) } catch (_: Throwable) { text }
        if (quickCharacterFlag && folded.length == 1) return "un"
        val flat = try { nativeGetLanguages(folded) } catch (_: Throwable) { return "un" }
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
    // AN EXCEPTION THROWN OUT OF HERE KILLED THE PROCESS, AND A DEAD PROCESS IS A
    // SILENT PHONE (2026-09-11, owner: "achanak se bolna band ho jata hai").
    //
    // AOSP does not catch anything on this path. SynthesisSpeechItem.playImpl()
    // calls onSynthesizeText directly, SpeechItem.play() calls playImpl, and
    // play() is invoked from a Runnable on SynthHandler -- a plain HandlerThread.
    // An uncaught Throwable there goes to the default handler and takes the whole
    // engine process down. The screen reader's TextToSpeech then loses its
    // binding and stays mute until it is re-initialised, which for the owner is
    // "it suddenly stopped and only a restart fixes it".
    //
    // The body is 570 lines and most of it is NOT inside the one try it already
    // has. That try covers segmentation only; everything before it
    // (reloadLanguagesIfMissing, the foreground check, the params bundle read,
    // which unparcels another app's Bundle) and everything after it
    // (onLoadLanguage, the queue fill, the whole of speakChunk with its
    // loadVoice call, its Bundle copy and its parcelable put) is bare.
    //
    // So the whole method moves into onSynthesizeTextImpl and this wrapper is the
    // net. It converts "the process dies and the phone goes silent" into "this
    // one utterance is dropped", which is the same trade every other fix in this
    // file makes, and it is judged by the same test the owner set for
    // releaseWaitWithoutSpeaking: it can never fire while speech is healthy, so
    // it costs nothing on the happy path and cannot cut an utterance short.
    //
    // Throwable, not Exception, on purpose. The failures that actually reach here
    // are Errors: NoClassDefFoundError from an API above minSdk (two of those
    // were found on 2026-09-10), OutOfMemoryError raised by the JNI guards added
    // the same day, and ArrayIndexOutOfBounds/NPE from the races this session
    // closed. Catching Exception alone would have held none of the first two.
    //
    // The catch does exactly what every other "this utterance cannot speak" exit
    // does: release the parked thread, then finish the callback. Both are
    // idempotent -- startAndFinish tests hasStarted/hasFinished, and the wait
    // below rechecks isStopped -- so the normal ending is unaffected.
    override fun onSynthesizeText(request: SynthesisRequest?, callback: SynthesisCallback?) {
        try {
            onSynthesizeTextImpl(request, callback)
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onSynthesizeText failed: " + ex.toString() + "\n" + android.util.Log.getStackTraceString(ex))
            speakingPkg = null
            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
            try { startAndFinish(callback) } catch (_: Throwable) {}
        }
    }
    private fun onSynthesizeTextImpl(request: SynthesisRequest?, callback: SynthesisCallback?) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "\n-------------------------------\nonSynthesizeText")
        reloadLanguagesIfMissing()
        if (showNotificationFlag && !isForegroundActive()) {
            startForegroundIfPossible()
        } else if (!showNotificationFlag && isForegroundActive()) {
            // ServiceCompat, not the deprecated int overload (2026-09-11). The
            // owner's standing instruction is that anything androidx already does
            // comes from androidx; ServiceCompat.stopForeground is that API, and
            // STOP_FOREGROUND_REMOVE is the same value 1 this passed by hand, so
            // the behaviour is identical on every level. ServiceCompat is already
            // imported for startForeground.
            ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        }
        synchronized(syncLock) { isStopped = false; syncLock.notifyAll() }
        synchronized(syncLock) { isFlushed = false; syncLock.notifyAll() }
        speakingPkg = null
        // THIS utterance's identity, for the rest of this method and for every
        // closure it creates. See the field for why the ++ needs no lock.
        val myGeneration = ++synthesisGeneration
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
            synchronized(chunkQueue) { chunkQueue.clear() }
            if (engineIndex >= 0 && engineIndex < enginePool.size) {
                val wrapper = enginePool[engineIndex]
                // DELIBERATE DEPARTURE FROM AutoTTS -- the SAME one onStop carries,
                // finished here on 2026-09-02 because it was left half-done.
                //
                // q0(FALSE) at AutoTtsService:1886 gates this flush on
                //     ... && f.get(d).g().isSpeaking()
                // and that is the identical race described at onStop: isSpeaking()
                // is a binder query into another app's engine and answers FALSE in
                // the window between our speak() and that engine really starting.
                // This is the path a screen reader takes when it interrupts with an
                // EMPTY utterance, which is how TalkBack flushes -- so landing in
                // the window meant the flush never happened and the previous phrase
                // carried on. Same defect, same evidence, same fix.
                //
                // speak("", QUEUE_FLUSH, null, null) on an idle engine is harmless:
                // it flushes an empty queue, and the null utterance id means AOSP
                // dispatches no callback for it (dispatchOnSuccess only fires when
                // the id is non-null). So asking unconditionally costs one binder
                // call and closes the race.
                if (wrapper.state == 2 && wrapper.listenerSet) {
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
                        val chunkOutput = processDirect(
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
                        val chunkOutput = processDirect(
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
                    val chunkOutput = processDirect(
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
        // DELIBERATE DEPARTURE, and the fix for "beech mein TTS ruk jata hai,
        // force stop karne ke baad phir chalta hai" (owner, 2026-09-03).
        //
        // onSynthesizeText ends by parking THE SCREEN READER'S ONE synthesis
        // thread on syncLock until a callback sets isStopped or isFlushed. Both
        // are set to false at the top of every utterance, so the wait is only
        // ever released by an engine that has actually been handed the text.
        // Every exit from speakChunk that does NOT reach speak() therefore has
        // to release it by hand. The `isStopped || isFlushed` returns are safe
        // because whoever set the flag already notified; these three were not:
        //
        //   engineIndex = -1 ("TTS is not ready") is set whenever no wrapper
        //   matches the package in state 2 -- which is exactly what restoreEngine
        //   leaves behind while an engine re-initialises after a failed
        //   setLanguage. The NEXT utterance then hit "mTTSIndex out of range",
        //   logged it, called startAndFinish and returned... into the wait, with
        //   both flags false, no speak() issued, no callback coming, and
        //   speakingPkg null so onEngineProcessGone could not match either. The
        //   thread parked for ever, and since a screen reader has ONE synthesis
        //   thread the whole device went silent until our process was killed.
        //
        // AutoTTS does not hang here, and not because it releases anything: its
        // two guards (noexc:2074 and :2176) are inline in onSynthesizeText and
        // `return` from the METHOD, so the wait is never entered. Ours is a
        // shared local fun used for the first chunk AND for the next-chunk step,
        // where `return` only leaves speakChunk. The hang is our refactor's, not
        // AutoTTS's, which is why fixing it is not a parity break.
        fun releaseWaitWithoutSpeaking(why: String) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "unlockSynthesis: " + why)
            startAndFinish(callback)
            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
        }
        fun speakChunk(first: Boolean) {
            // The one place that pops chunkQueue and speaks, and it is reached
            // from three threads: the synthesis thread for the first chunk, a
            // binder thread when onDone finds no next chunk, and the main looper
            // for every chunk after the first. Once this utterance has ended the
            // queue belongs to the next one, so a late caller must not pop it.
            if (myGeneration != synthesisGeneration) return
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
                releaseWaitWithoutSpeaking("engine index out of range")
                return
            }
            val wrapper = enginePool[engineIndex]
            val tts = wrapper.tts ?: run {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "mTTSIndex refers null tts.")
                releaseWaitWithoutSpeaking("engine has no TextToSpeech")
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
            // THE UTTERANCE ID IS NOW UNIQUE, AND IT WAS NOT (owner, 2026-09-09:
            // "speech interruption ka problem").
            //
            // It used to be "${utteranceId}_${chunkCounter}", and neither half
            // separates one utterance from the next: `utteranceId` is a static
            // read from the caller's params and is literally the string "null"
            // whenever the caller sets none, and `chunkCounter` is reset to 1 for
            // every utterance by the line above -- so two consecutive
            // screen-reader utterances, which are one chunk each, both spoke
            // under the id "null_1".
            //
            // That is why the 2026-09-03 attempt to drop stale callbacks with
            // `id != expectedId` broke explore-by-touch instead of fixing it: the
            // check was right and the id was not. The generation makes the id
            // genuinely unique, and the guards below then mean what they say.
            //
            // The framework hands this string back verbatim and cannot alter it:
            // TextToSpeech.speak() passes it as its own AIDL argument, not inside
            // params; TextToSpeechService stores it in UtteranceSpeechItemWith-
            // Params.mUtteranceId, which is protected final on a PRIVATE class of
            // the framework; and every dispatchOn* sends getUtteranceId(). An
            // engine implementation overrides onSynthesizeText, never those.
            val expectedId = "${utteranceId}_${myGeneration}_${chunkCounter}"
            val chunkHandler = android.os.Handler(android.os.Looper.getMainLooper())
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(id: String) {
                    // The engine really spoke, so its failure streak is over.
                    // This is what replaces a "healthy for N seconds" clock in
                    // restoreEngine: an event that means exactly "it worked".
                    // True whichever utterance it belongs to, so it is not gated.
                    try {
                        wrapper.restoreCount = 0
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onStart " + id)
                        if (id != expectedId) return
                        if (callback?.hasStarted() == false) { callback?.start(16000, android.media.AudioFormat.ENCODING_PCM_16BIT, 1) }
                    } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onStart failed: " + ex.toString()) }
                }
                override fun onDone(id: String) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "onDone " + id)
                    // STALE CALLBACK. setOnUtteranceProgressListener stores ONE
                    // volatile listener per TextToSpeech and the framework reads
                    // it at dispatch time, so a callback for the utterance we
                    // just interrupted is delivered to the listener the NEXT
                    // utterance installed -- carrying the next utterance's
                    // `callback` and `chunkQueue` in its closure. Acting on it
                    // spoke the new utterance's chunk against the old, already
                    // finished callback, which is the text that gets cut off.
                    // The id is unique now, so this tells the two apart.
                    if (id != expectedId) return
                    // THE BINDER-THREAD HALF IS GUARDED TOO (2026-09-11). This is
                    // the engine's own oneway callback, so an escape is logged by
                    // Binder.execTransact and does NOT crash -- it just stops. And
                    // stopping here is the hang: speakChunk(false) below is what
                    // ends the utterance when the queue is empty, and
                    // chunkHandler.post is what starts the next chunk, so losing
                    // either leaves the synthesis thread parked with no callback
                    // left to come.
                    try {
                    val next = synchronized(chunkQueue) { chunkQueue.firstOrNull()?.second }
                    if (next == null) { speakChunk(false); return }
                    chunkHandler.post {
                        try {
                        // Posted while THIS utterance was live; it runs on the
                        // main looper, which is neither the synthesis thread nor
                        // the binder thread, so an interrupt in between means it
                        // arrives after the utterance ended and the next one has
                        // already refilled chunkQueue. The id check above cannot
                        // see that -- it was true when we posted.
                        if (myGeneration != synthesisGeneration) return@post
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
                                // Same hole as the two guards above: this ends the
                                // utterance without speaking, so the parked thread
                                // has to be released here too. Its sibling branch
                                // (the auto/Google one just below) always did.
                                releaseWaitWithoutSpeaking("next chunk language not supported")
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
                        } catch (ex: Throwable) {
                            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onDone Error: " + ex.toString())
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #6")
                            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                            try { startAndFinish(callback) } catch (_: Throwable) {}
                        }
                    }
                    } catch (ex: Throwable) {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onDone failed: " + ex.toString())
                        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                        try { startAndFinish(callback) } catch (_: Throwable) {}
                    }
                }
                override fun onError(id: String) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onError " + id)
                    if (id != expectedId) return
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #8")
                    synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                    if (callback?.hasStarted() == true && callback?.hasFinished() == false) { callback?.done() }
                }
                override fun onError(id: String, errorCode: Int) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onError " + id + " code " + errorCode)
                    if (id != expectedId) return
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG, "endSynthesis #9")
                    synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
                    if (callback?.hasStarted() == true && callback?.hasFinished() == false) { callback?.done() }
                }
                // LOG ONLY, exactly as AutoTTS's listener does
                // (decompiled_java_noexc/.../AutoTtsService.java:2770 is a bare
                // log). This DID release the wait for one commit on 2026-09-03
                // and it was REVERTED the same day, because it broke
                // explore-by-touch on the owner's device: "thoda sa bhi agar
                // next element per jata hun ... to jo pehla text hai vah bilkul
                // stop ho jata hai ... button read hi nahin karta".
                //
                // WHY IT BROKE, so nobody writes it again the same way. A screen
                // reader interrupts by stopping us and immediately sending the
                // next utterance, and the engine's onStop for the OLD utterance
                // lands on a binder thread afterwards. The released version
                // guarded that with `if (id != expectedId) return`, believing
                // expectedId identified the utterance. **It does not.**
                // `expectedId` is "${utteranceId}_${chunkCounter}", and
                // `utteranceId` is
                //     (request?.params?.getString("utteranceId")).toString()
                // -- a STATIC, and literally the string "null" whenever the
                // caller sets no utteranceId param. So consecutive utterances
                // share the id, the stale callback matched the NEW listener's
                // expectedId, and it set isStopped on an utterance that had not
                // spoken yet. Explore-by-touch is a continuous stream of
                // interruptions, which is why it failed there every time and
                // survived a slower swipe.
                //
                // The hang it was written for is real in principle -- AOSP's
                // SynthesisSpeechItem.stopImpl() dispatches onStop and NEITHER
                // onDone NOR onError, so an utterance stopped by something that
                // is not us leaves the wait unwoken -- but it was found by
                // reading, never in any log the owner sent, while the regression
                // was immediate and total. AutoTTS carries the same shape, and
                // rule 5 says mirror it.
                //
                // THE ID IS UNIQUE NOW (see expectedId above), so if that hang
                // ever does show up in a real log, `id != expectedId` here is
                // finally a correct guard and this can release the wait. It is
                // still NOT done, because AutoTTS's listener is a bare log and
                // no log the owner has sent contains the hang -- only the
                // interruption the id fixes. Do not add it on theory.
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
            // ...and turning the switch back OFF has to undo it, which is the
            // other half of the same AOSP fact and is NOT free. DELIBERATE
            // DEPARTURE (owner request, 2026-09-03).
            //
            // setAudioAttributes writes into the client's mParams and there is
            // no way to take it back out: TextToSpeech.java:1522 answers ERROR
            // for a null argument and leaves mParams untouched, and mParams
            // lives as long as the TextToSpeech object. So an engine that was
            // ever spoken to with the force switch on keeps
            // USAGE_ASSISTANCE_ACCESSIBILITY in its mParams for the life of the
            // process, and getParams() hands it downstream whenever the merged
            // bundle carries no attributes of its own -- either because the
            // strip switch just removed the caller's, or because the caller
            // never set any. The user turns the switch off and still hears the
            // accessibility routing.
            //
            // Overwriting the key is the only way to beat mParams in that
            // merge, so we put back exactly what AOSP itself would have built
            // had the key been absent: TextToSpeechService's
            // AudioOutputParams.createFromParamsBundle falls back to
            // `setLegacyStreamType(KEY_PARAM_STREAM, default Engine.DEFAULT_STREAM
            // = STREAM_MUSIC = 3).setContentType(CONTENT_TYPE_SPEECH = 1)`.
            // Inert unless this wrapper really is carrying our attributes.
            if (!isForceAccessibility && wrapper.audioAttrSet && !params.containsKey("audioAttributes")) {
                try { params.putParcelable("audioAttributes", android.media.AudioAttributes.Builder().setLegacyStreamType(params.getInt("streamType", 3)).setContentType(1).build()) } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) }
            }
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
        // Each step on its own, for the same reason onCreate's five are: they are
        // independent, and a failure in one must not skip the others. Shutting the
        // engine clients down and releasing the bindings is what stops this
        // process leaking a connection into every TTS engine on the phone, and
        // until 2026-09-11 a throw from stopForeground or abandonAudioFocus --
        // abandonAudioFocus dereferences audioManager!! and audioFocusRequest!!,
        // both null if requestAudioFocus failed at startup -- skipped the pool
        // walk that follows.
        try { ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "stopForeground: " + ex.toString()) }
        try { abandonAudioFocus() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "abandonAudioFocus: " + ex.toString()) }
        try { for (index in 0 until enginePool.size) { if (enginePool[index].state == 2) enginePool[index].shutdown() } } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "engine shutdown: " + ex.toString()) }
        try { unbindAllEngineKeepAlive() } catch (_: Throwable) {}
        super.onDestroy()
    }

    // ==========================================================================
    //  STATICS                     AutoTtsService's fields
    //  pushLanguageSets is s0(): detect sets AND hints, and it may only be called
    //  where the language list was just rebuilt. pushDetectSetsOnly is the
    //  per-utterance one. INVARIANTS #1 and #2.
    // ==========================================================================
    companion object {
        // THE NATIVE METHODS LIVE HERE, NOT IN A CLASS OF THEIR OWN (owner,
        // 2026-09-09: "vah sari native method aa jaaye, alag se class na bane").
        // They used to sit in a `NativeEngine` object, which survived R8 as its
        // own class in the decompiled APK -- a class carrying native methods
        // cannot be renamed or merged away, because JNI resolves by symbol name.
        //
        // @JvmStatic ON A COMPANION `external fun` IS THE ONE FORM THAT WORKS,
        // and the other two were compiled and rejected rather than reasoned
        // about (kotlinc 2.4.20, read back with javap):
        //
        //   instance member          -> native on the class, but needs an
        //                               instance, and nine call sites are
        //                               instance methods while three are here
        //   plain companion member   -> native lands on EasyVoiceTtsService$Companion,
        //                               so the separate class is still there and
        //                               the symbol gains _00024Companion
        //   @JvmStatic companion     -> `public static final native` on
        //                               EasyVoiceTtsService ITSELF. One class,
        //                               callable unqualified from both scopes.
        //
        // System.loadLibrary is safe where it is, and that was verified too: the
        // companion's init block compiles INTO EasyVoiceTtsService.<clinit>
        // (javap shows the ldc "easyvoice_core" + invokestatic System.loadLibrary
        // right there), so touching any of these statics initialises the class
        // and loads the library first. It is not left to the companion being
        // touched separately.
        init { System.loadLibrary("easyvoice_core") }

        @JvmStatic external fun processDirect(
            buffer: java.nio.ByteBuffer, length: Int,
            latinFallback: String, nonLatinFallback: String,
            mode: String,
            numberMode: Int, numberSpecific: String,
            punctuationMode: Int, punctuationSpecific: String,
            emojiMode: Int, emojiSpecific: String, punctuationInFlow: Boolean, smartNumber: Boolean,
            smartNumberGroupSize: Int,
            neutralDefault: String, neutralType: Int,
            disableAdvancedDetection: Boolean
        ): String
        @JvmStatic external fun nativeGetLanguages(text: String): Array<String>
        // clsCLD2.b: decorated Latin letters folded back to ASCII.
        @JvmStatic external fun normalizeFancy(text: String): String
        @JvmStatic external fun setIsoMap(iso2: Array<String>, iso3: Array<String>)
        @JvmStatic external fun setLanguageHints(langs: Array<String>)
        @JvmStatic external fun setDetectSets(detectOkIso3: Array<String>, enabledLangs: Array<String>)
        @JvmStatic external fun detectLanguageFull(text: String, latinFallback: String, nonLatinFallback: String,
            disableAdvancedDetection: Boolean, wantLog: Boolean): String

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
            try { setDetectSets(detectOkIso3().toTypedArray(), enabledSet.toTypedArray()) } catch (_: Throwable) {}
            try { setLanguageHints(enabledSet.toTypedArray()) } catch (_: Throwable) {}
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
            try { setDetectSets(detectOkIso3().toTypedArray(), enabledSet.toTypedArray()) } catch (_: Throwable) {}
            return enabledSet
        }
        @JvmField val chunkQueue: ArrayList<Pair<Int, TextChunk>> = ArrayList()
        @Volatile @JvmField var engineList: ArrayList<String> = ArrayList()
        @Volatile @JvmField var voiceList: ArrayList<String> = ArrayList()
        @JvmField var dedicatedEnginesFlag = false
        // AutoTtsService's static `m0` (declared at :114, zeroed at :169), which
        // onLoadVoice assigns and NOTHING in the app ever reads -- do not confuse
        // it with the method `m0(String)` at :1503, which is a different member
        // with the same letter. Written here for the same reason: it is dead in
        // AutoTTS, so it stays dead here rather than being deleted or given a
        // reader we invented.
        @JvmField var lastLoadedVoiceName = ""
        // @Volatile ADDED ON THE OWNER'S OVERRIDE (2026-09-10). This file used
        // to record it as "left alone deliberately" because AutoTTS's K is a
        // plain static and rule 5 forbids a "defensive" change. The owner has
        // reversed that, and the read is genuinely cross-thread: speakChunk
        // WRITES it (`if (currentChunk == 1) chunkCounter = 1`) and READS it
        // into expectedId, and speakChunk is reached from three threads -- the
        // synthesis thread for the first chunk, the MAIN LOOPER from onDone's
        // post (which also does chunkCounter++), and a BINDER thread when
        // onDone finds the queue empty and calls speakChunk(false) directly.
        // The main-looper write and the binder-thread read are ordered only by
        // the two binder round trips that happen to sit between them, which is
        // a practical barrier and not a guarantee. A stale read builds the
        // wrong expectedId, and every callback for that chunk is then dropped
        // by the id guard -- silence, not a crash, which is the failure this
        // app can least afford to leave to luck.
        @Volatile @JvmField var chunkCounter = 0
        @Volatile @JvmField var utteranceId = ""
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
