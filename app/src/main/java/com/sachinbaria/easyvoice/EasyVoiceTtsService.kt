package com.sachinbaria.easyvoice
import android.app.Notification
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
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
data class TextChunk(var text: String, var lang: String, var typeCode: Int = 0, var forcedEngine: String? = null, var forcedLocale: String? = null, var forcedVariant: String? = null) {
    // toIso3(lang) worked out once per chunk (owner, 2026-10-06): the prefetch,
    // the queue-ahead, speakChunk and onDone each converted the same chunk's
    // language again. Keyed by `lang` itself, so a chunk whose language is
    // reassigned converts the new one; one immutable pair, so a reader on
    // another thread sees a key and its value together or neither.
    @Volatile private var iso3Memo: Pair<String, String>? = null
    fun iso3(): String {
        val l = lang
        val memo = iso3Memo
        if (memo != null && memo.first == l) return memo.second
        return SharedPrefsManager.toIso3Cached(l).also { iso3Memo = Pair(l, it) }
    }
}
class EasyVoiceTtsService : TextToSpeechService() {
    private val enginePool = ArrayList<EngineWrapper>()
    // THE SERVICE'S ONE MAIN-LOOPER HANDLER (2026-09-23). There were three --
    // the init walk's, onMainThread's and the restores' -- plus a new Handler
    // built for every chunk spoken, all on the same looper. One does all of it;
    // every post is removed by identity (removeCallbacks(runnable)), never by
    // clearing the queue, so they cannot disturb each other.
    //
    // ASYNCHRONOUS, from androidx (HandlerCompat.createAsync: the public
    // Handler.createAsync on API 28+, the hidden async constructor below it).
    // The settings screens run in this same process, and while one is drawing,
    // the view system puts a SYNC BARRIER on the main queue that holds every
    // ordinary message until the frame is done. The next chunk's hop, the
    // engine init callbacks and the restores go past that barrier instead of
    // waiting behind a frame. Same thread and the same order among our own
    // posts; the only thing that changes is that UI drawing can no longer delay
    // speech.
    private val mainHandler: android.os.Handler = androidx.core.os.HandlerCompat.createAsync(android.os.Looper.getMainLooper())
    // THE SPEAK THREAD (owner, 2026-10-01). Every step of an utterance after its
    // first chunk -- the next chunk, the prefetch, the chunk queued ahead, the
    // hand-over after an error or an engine's death -- runs here, one at a time,
    // as it used to on the main looper. Those steps share currentPair, the queue
    // and the queued chunk without locks, so they must stay on ONE thread; this
    // one does nothing else, so the settings screens, a restore or a scan on the
    // main thread can no longer hold the next chunk up.
    private val speakThread = android.os.HandlerThread("EasyVoiceSpeak", android.os.Process.THREAD_PRIORITY_AUDIO).also { it.start() }
    private val speakHandler: android.os.Handler = androidx.core.os.HandlerCompat.createAsync(speakThread.looper)
    private val engineBinders = java.util.concurrent.ConcurrentHashMap<String, android.content.ServiceConnection>()
    // The engine's own IBinder, as the keep-alive binding received it in
    // onServiceConnected; removed when that binding reports a death or is
    // unbound. IBinder.isBinderAlive() answers "is the engine's process up right
    // now" locally, with no IPC -- the framework's own answer, used by
    // restoreEngine so a restore is never made into a process that is not there.
    private val engineServiceBinders = java.util.concurrent.ConcurrentHashMap<String, android.os.IBinder>()
    private fun engineProcessUp(rawPkg: String): Boolean =
        try { engineServiceBinders[rawPkg]?.isBinderAlive == true } catch (_: Throwable) { false }
    // ==========================================================================
    //  A CHUNK WAITS FOR ITS ENGINE TO FINISH STARTING (owner, 2026-10-01:
    //  "package se jo scan ... restore ... kuchh to problem ho rahi hai ...
    //  start aur close ... koi gap"). After every cold start of this process --
    //  boot, Clear all, a memory kill, an update -- the screen reader binds us
    //  and speaks AT ONCE, while every engine's client is still being built
    //  (state 0) or restored (state 1). loadVoice only takes state 2, so those
    //  first utterances found "TTS is not ready" and were dropped in silence,
    //  which is exactly the moment a blind user needs to hear where they are.
    //  Now the synthesis thread (the first chunk) and the speak thread (a next
    //  chunk) WAIT for that one engine to settle instead.
    //
    //  NO NEW CLOCK. The wait ends only on events that already exist: the
    //  engine's init or restore answering (initFinished / the restore listener
    //  call engineSettled), the 30 s init and restore watchdogs that are already
    //  there (they settle the state too), an interrupt (onStop sets isStopped and
    //  notifies syncLock), the next utterance (a new generation), and onDestroy.
    //  It waits on syncLock, which every one of those already notifies.
    //
    //  ONLY where a wait is safe: a thread that set mayAwaitEngine (the
    //  synthesis thread inside onSynthesizeText, the speak thread inside its
    //  next-chunk load), never the main thread (the init callbacks run there),
    //  and with no other lock held -- it is called BEFORE onLoadLanguage takes
    //  `this`. AOSP's queued LoadLanguageItems run on the synthesis thread
    //  outside onSynthesizeText, where an interrupt does not reach our onStop,
    //  so they do not wait.
    // ==========================================================================
    private val mayAwaitEngine = java.lang.ThreadLocal<Boolean>()
    private fun engineSettled() { synchronized(syncLock) { syncLock.notifyAll() } }
    private fun awaitEngineStarting(langCode: String?) {
        if (mayAwaitEngine.get() != true || langCode.isNullOrEmpty()) return
        if (android.os.Looper.getMainLooper().isCurrentThread) return
        // FAST PATH (owner, 2026-10-01): once every engine has answered there is
        // nothing to wait for, and asking which engine the language uses walks
        // the whole language list on every load. A wrapper only ever ENTERS
        // state 0 or 1 on the main looper (pool fill, restore), and the loop
        // below re-reads the state under syncLock, so skipping here can only
        // miss a start that also began after this utterance asked -- which the
        // old code would equally have found settled.
        var anyStarting = false
        try {
            for (index in 0 until enginePool.size) {
                val state = enginePool[index].state
                if (state == 0 || state == 1) { anyStarting = true; break }
            }
        } catch (_: Throwable) { anyStarting = true }
        if (!anyStarting) return
        val rawPkg = try { LangStore.engineFor(prefs.toIso3(langCode.lowercase()), modeInt) } catch (_: Throwable) { "" }
        if (rawPkg.isEmpty() || rawPkg.equals("disable", true)) return
        val norm = normPkg(rawPkg)
        val generation = synthesisGeneration
        var waitedSince = 0L
        synchronized(syncLock) {
            while (!destroyed && generation == synthesisGeneration && !isStopped && !isFlushed) {
                var settling = false
                try {
                    for (index in 0 until enginePool.size) {
                        val wrapper = enginePool[index]
                        if (wrapper.pkg == norm) { settling = wrapper.state == 0 || wrapper.state == 1; break }
                    }
                } catch (_: Throwable) { settling = false }
                if (!settling) break
                if (waitedSince == 0L) {
                    waitedSince = android.os.SystemClock.uptimeMillis()
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, rawPkg + " is still starting -- waiting for it")
                }
                try { syncLock.wait() } catch (_: InterruptedException) { break }
            }
        }
        if (waitedSince != 0L) EasyVoiceLogger.error(EasyVoiceLogger.TAG, rawPkg + " waited " + (android.os.SystemClock.uptimeMillis() - waitedSince) + " ms")
    }
    private val keepAliveLocks = java.util.concurrent.ConcurrentHashMap<String, Any>()
    @Volatile var engineIndex = -1
    private lateinit var prefs: SharedPrefsManager
    // java.lang.Object on purpose: wait() and notifyAll() are ITS methods and
    // kotlin.Any does not expose them, so this is the one place the compiler's
    // "use kotlin.Any" advice does not apply.
    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
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
    // The live utterance's "say this chunk on another engine", so the death of
    // the engine holding it hands the chunk over instead of only ending it.
    @Volatile private var retryOnEngineDeath: ((String) -> Boolean)? = null
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
        val normPkg = normPkg(pkg)
        // THE CACHED VOICE DESCRIBES A CONNECTION, AND THE CONNECTION IS GONE
        // (2026-09-16). forgetClientState() ran only where `tts` was REPLACED, and
        // a process death does not replace it -- the same object is still there,
        // now dead. So currentVoice/currentVoiceKnown survived it, and loadVoice's
        // "Do nothing!" short-circuit twenty lines into the method answered from
        // that cache and RETURNED WITHOUT TOUCHING THE ENGINE. The utterance then
        // spoke into a dead client.
        //
        // The 2026-09-11 caching change is what opened this. Before it, that test
        // read `tts.voice` LIVE, and AOSP answers null on a client whose service
        // connection is gone, so the short-circuit could not fire and the method
        // fell through to setLanguage -- which failed, which called restoreEngine.
        // Caching removed the app's only way to notice on the ordinary path.
        //
        // Clearing it HERE keeps the whole caching win: this runs on a process
        // death, never on the speaking path. It also clears localeSet, so
        // loadVoiceDedicated stops skipping the load for an engine that just died.
        // Both callbacks that reach here are on the main looper, which is where
        // enginePool is appended to, so this walk cannot tear.
        //
        // AND THE CLIENT ITSELF IS DEAD, so the wrapper stops being selectable
        // (2026-09-23). Every client connects through the system's TTS manager,
        // whose session answers a process death with onDisconnected and then
        // drops the callback (`mCallback = null`), so nothing reconnects it. The
        // wrapper goes to -1 and processGone is set: the restore is made ONCE,
        // from onEngineProcessBack, when the process is back -- not now, while a
        // new bind could only wait for the system's own restart.
        for (index in 0 until enginePool.size) {
            val dead = enginePool[index]
            if (dead.pkg != normPkg) continue
            dead.forgetClientState()
            dead.processGone = true
            if (dead.state == 2) dead.state = -1
            break
        }
        if (speakingPkg != normPkg) return
        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Engine holding this utterance died: " + pkg)
        speakingPkg = null
        val retry = retryOnEngineDeath
        if (retry != null && retry(pkg)) return
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
    // THE WALK OWNS ITS LIST (2026-09-23). The walk below indexes a list by
    // initializingIndex across many main-looper messages, and it used to index
    // the companion's engineList -- which LangStore.persistEngines REPLACES from
    // the settings screens in this same process. A scan finishing mid-walk
    // swapped the list under it, so the next step constructed the wrong engine
    // at the wrong index, or ran off the end and left every later engine at
    // state 0 for the life of the process. The walk takes a copy and reads
    // only that.
    private var walkList: ArrayList<String> = ArrayList()
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
    // THE CHANNEL COMES FROM androidx.core NOW (owner, 2026-09-23: a library
    // rather than code written by hand). NotificationChannel is API 26 against
    // minSdk 24 -- the second of the two Android 7 crashes found on 2026-09-10 --
    // and NotificationChannelCompat carries that check itself: below 26
    // createNotificationChannel does nothing, which is what the hand-written
    // guard and its separate API-26 method did. Its defaults were read in the
    // core 1.19.0 source and are the platform's own (the default notification
    // sound, no lights, no vibration, badge on), so the channel is unchanged.
    private fun createNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(FOREGROUND_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW)
            .setName(getString(R.string.app_name))
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
    // THE ONE PLACE the callback is started. Easy Voice produces no audio of its
    // own, but AOSP needs start() before anything else: it is what dispatches
    // onBeginSynthesis and queues the item whose playback sends the screen
    // reader its onStart, and without it done() logs "done() was called before
    // start() call" and returns ERROR (PlaybackSynthesisCallback, read at the
    // line), and the keep-alive's audioAvailable() refuses every buffer. The
    // format is only a declaration for audio we never write (or, for the
    // keep-alive, for silence), so it is the plainest one the API accepts:
    // 16 kHz, 16-bit PCM, mono. The framework has no named constant for a
    // sample rate or a channel count.
    private fun startCallback(callback: SynthesisCallback?) {
        if (callback?.hasStarted() == false) callback.start(16000, android.media.AudioFormat.ENCODING_PCM_16BIT, 1)
    }
    private fun startAndFinish(callback: SynthesisCallback?) {
        startCallback(callback)
        if (callback?.hasFinished() == false) callback.done()
    }
    // ==========================================================================
    //  THE TWO WAYS AN UTTERANCE ENDS          AutoTTS O(cb,int) and r0(int)
    //
    //  Owner, 2026-09-17: "on stop wala bahut sari jagahon per ... on done on
    //  event is sab chijen lagi hui hai ... call back yah thoda ek bar research
    //  karke complete karo properly TTS ka jo properly jo system hota hai na
    //  exactly A to Z."
    //
    //  THEY ARE RIGHT, AND THE COUNT IS THE PROOF. This file had NINETEEN places
    //  that wrote `synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }`
    //  by hand. AutoTTS has FOUR, and three of them are these two named functions
    //  plus the empty-text flush:
    //
    //      O(cb, n)  noexc:300  "endSynthesis #n", set the flag, notify, and then
    //                           done() ONLY if the callback has already started
    //                           and has not finished
    //      r0(n)     noexc:2250 "unlockSynthesis #n", set the flag, notify, and
    //                           touch the callback NOT AT ALL
    //
    //  So AutoTTS funnels; we inlined. That is the whole of the owner's
    //  complaint, and the repair is a REFACTOR WITH NO BEHAVIOUR CHANGE that
    //  moves us TOWARDS AutoTTS rather than away: our sites already wrote the
    //  identical log lines, in the identical order, with the identical guard --
    //  they simply spelled the body out instead of calling the function.
    //
    //  Eight sites are now endSynthesis(): #2/#3, #4, #5, #6, #7, #8, #9, #14.
    //  Two are unlockSynthesis(): #12 and #14.
    //
    //  WHAT DELIBERATELY DID NOT MOVE, because the body genuinely differs:
    //    * our own onStop() override and the empty-text flush set BOTH flags --
    //      that is AutoTTS's q0, a third shape;
    //    * releaseWaitWithoutSpeaking and the onSynthesizeText catch-all call
    //      startAndFinish (AutoTTS's N), which STARTS the callback if it never
    //      started. O never starts one. They are not interchangeable;
    //    * onEngineProcessGone releases on an event rather than at an exit.
    //
    //  `n` is a String, not an Int, because one site computes its own number
    //  (`if (next.typeCode == 1) 2 else 3`), exactly as AutoTTS's caller does.
    private fun endSynthesis(callback: SynthesisCallback?, n: String) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "endSynthesis #" + n }
        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
        if (callback?.hasStarted() == true && !callback.hasFinished()) callback.done()
    }
    private fun unlockSynthesis(n: String) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "unlockSynthesis #" + n }
        synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
    }
    // USAGE_ASSISTANCE_ACCESSIBILITY + CONTENT_TYPE_SPEECH, built ONCE (2026-09-24).
    // The same attributes were built four times over -- the focus request and the
    // three places that install them on an engine client -- and three of those
    // spelled the constants as 11 and 1 (a leftover from an API-15 check jar that
    // had no AudioAttributes; the values were right). AudioAttributes is
    // immutable, so one instance serves every caller.
    private val accessibilitySpeech: AudioAttributes by lazy {
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
    }
    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID)
            .setContentTitle("Easy Voice active")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()
    }
    // Only for the log: from Android 13 notifications need POST_NOTIFICATIONS to
    // SHOW. The foreground service does not need it (below).
    private fun notificationsShown(): Boolean = NotificationManagerCompat.from(this).areNotificationsEnabled()
    // True while THIS instance runs in the foreground. Only this service starts
    // and stops its foreground state, so the flag is exact; the notification
    // check beside it (AutoTTS's a0()) cannot see a notification the user has
    // blocked, and would ask for startForeground again on every utterance.
    @Volatile private var foregroundStarted = false
    // The flag alone (owner, 2026-10-01). Only this service starts its foreground
    // state, a new instance starts without it, and stopping the service removes
    // it, so foregroundStarted is exact -- the reasoning the OFF branch of
    // onSynthesizeText already uses. The activeNotifications() binder call it
    // replaces ran on EVERY utterance while the switch was on and the start had
    // been refused.
    private fun isForegroundActive(): Boolean = foregroundStarted
    // A refused start is not retried from an utterance: from the background
    // Android 12+ refuses it every time, so each retry was an exception and a
    // log line per utterance. Cleared by onStartCommand (an app screen asked,
    // which Android allows); a new instance starts with it clear.
    @Volatile private var foregroundRefused = false
    private fun stopForegroundNow() {
        ServiceCompat.stopForeground(this, ServiceCompat.STOP_FOREGROUND_REMOVE)
        foregroundStarted = false
    }
    // THE FOREGROUND SERVICE NO LONGER WAITS FOR THE NOTIFICATION PERMISSION
    // (2026-09-24). It used to return at once when POST_NOTIFICATIONS was not
    // granted, so on Android 13+ a user who switched "Show persistent
    // notification" on and then said "Don't allow" got nothing at all: no
    // foreground service, and Android free to stop Easy Voice again. Android's
    // own page says otherwise, verbatim: "Apps don't need to request the
    // POST_NOTIFICATIONS permission in order to launch a foreground service.
    // However, apps must include a notification" -- which is still passed. With
    // the permission off the notification is kept out of the drawer (the app is
    // listed in the Task Manager instead) and the service is foreground all the
    // same. developer.android.com/develop/ui/views/notifications/notification-permission
    //
    // A refusal is still logged by class and message: a TTS engine is NOT on
    // Android 12's exemption list for starting a foreground service from the
    // background, so ForegroundServiceStartNotAllowedException is a real outcome
    // when this runs from an utterance. The app's own screens start it while
    // they are showing instead (onStartCommand), which Android allows.
    private fun startForegroundIfPossible() {
        try {
            if (!notificationsShown()) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) {
                    "notifications are off: the foreground service runs, its notification is not shown" }
            }
            createNotificationChannel()
            // ONE CALL FOR EVERY API LEVEL: ServiceCompat picks the platform
            // overload itself -- the typed one on 29+, the plain one below.
            ServiceCompat.startForeground(this, FOREGROUND_NOTIFICATION_ID, buildNotification(),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
            foregroundStarted = true
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "foreground service started (mediaPlayback)" }
        } catch (ex: Exception) {
            // `ex.toString()` carries the class and the message and is never
            // empty, which `ex.message` can be.
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "foreground service refused: " + ex.toString())
            foregroundRefused = true
        }
    }
    // THE ONE LISTENER BOTH PATHS USE. Below API 26 the focus is abandoned by
    // handing this same object back, so it has to outlive the request.
    private val audioFocusListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            -2 -> EasyVoiceLogger.debug("TTS") { "Audio focus lost temporarily" }
            -1 -> EasyVoiceLogger.debug("TTS") { "Audio focus lost" }
            1 -> EasyVoiceLogger.debug("TTS") { "Audio focus gained" }
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
    // NO LIBRARY HERE, and that was checked rather than assumed (owner,
    // 2026-09-23: a library over code written by hand wherever one exists).
    // androidx.media's AudioManagerCompat does this exact split, and in 1.8.0
    // -- the latest -- every one of its audio classes is @Deprecated
    // ("androidx.media is deprecated. Please migrate to androidx.media3"); it
    // also added 23,868 bytes of dex, measured with R8, and below API 26 asks
    // on STREAM_ACCESSIBILITY, a stream Android 7 does not have. Media3's
    // replacement lives in media3-common, which is @UnstableApi and pulls the
    // whole of Guava in. The platform AudioFocusRequest below IS the current
    // API, so it stays.
    private fun requestAudioFocus() {
        // ContextCompat.getSystemService is androidx's typed lookup: a device that
        // has no audio service answers null here instead of the NPE that
        // `getSystemService(AUDIO_SERVICE) as AudioManager` threw on the cast.
        val manager = ContextCompat.getSystemService(this, AudioManager::class.java)
        if (manager == null) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Audio focus request: no AudioManager on this device"); return }
        audioManager = manager
        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) requestAudioFocus26()
        else {
            @Suppress("DEPRECATION")
            audioManager!!.requestAudioFocus(audioFocusListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        }
        // THE RAW CODE AS WELL AS THE BOOLEAN, AND IT STARTS AT ANDROID 15 --
        // NOT 17, WHICH IS WHAT THIS COMMENT SAID FIRST AND GOT WRONG.
        //
        // Android 15 (API 35), "Audio focus request restrictions": an app "must be
        // top app or running a foreground service to request audio focus", and
        // otherwise the call returns AUDIOFOCUS_REQUEST_FAILED
        // (developer.android.com/about/versions/15/behavior-changes-15, Camera and
        // Media). Android 17 then HARDENS the same rule for every app whatever it
        // targets -- "calls fail silently or return AUDIOFOCUS_REQUEST_FAILED when
        // the app is not in valid lifecycle"
        // (about/versions/17/behavior-changes-all, Media).
        //
        // The correction matters rather than being pedantry: this app targets 37
        // but RUNS on everything from 24 up, and the restriction has been in force
        // on the owner's phone since Android 15 rather than arriving with 17.
        //
        // This request is made from onCreate, which runs when the system BINDS the
        // engine -- in the background by definition -- so from Android 15 onward a
        // denial here is expected rather than exceptional.
        //
        // It is harmless, and that is worth stating because it looks alarming in a
        // log: Easy Voice never produces a sample. It hands text to another
        // engine, and THAT engine holds the focus for the audio it plays. The
        // request is AutoTTS's `l0()` carried over, not something the speech
        // depends on.
        //
        // GRANTED is 1, FAILED is 0 and DELAYED is 2, and the boolean cannot tell
        // the last two apart -- which is exactly the distinction that says whether
        // Android 17 refused it or the system deferred it.
        EasyVoiceLogger.debug("TTS") { "Audio focus request: " +
            (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) + " (code " + result + ")" }
    }
    // Its own method on purpose, which is the shape Android's own guidance and
    // PackageInfoCompat's Api28Impl both use: the verifier only ever has to
    // resolve AudioFocusRequest when this method is entered, and on API 24 it
    // never is.
    private fun requestAudioFocus26(): Int {
        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(accessibilitySpeech)
            .setOnAudioFocusChangeListener(audioFocusListener)
            .build()
        audioFocusRequest = focusRequest
        return audioManager!!.requestAudioFocus(focusRequest)
    }
    private fun abandonAudioFocus() {
        if (audioManager == null) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) abandonAudioFocus26()
        else { @Suppress("DEPRECATION") audioManager!!.abandonAudioFocus(audioFocusListener) }
    }
    private fun abandonAudioFocus26() {
        val request = audioFocusRequest ?: return
        audioManager?.abandonAudioFocusRequest(request)
    }

    // ==========================================================================
    //  LIFECYCLE
    //  onCreate is ordering-critical: context, logger, prefs and the logging flag
    //  come BEFORE super.onCreate(), and the language sets are pushed only after
    //  loadAllSettings has filled them.
    // ==========================================================================
    override fun onCreate() {
        EasyVoiceLogger.init(this)
        // Which installed engine is the phone's built-in one, and which are ours,
        // are asked of the package manager -- see EngineFinder.attach.
        try { EngineFinder.attach(this) } catch (_: Throwable) {}
        prefs = SharedPrefsManager(this)
        ScanYield.speechInFlight = { synthesizingNow }
        EasyVoiceLogger.setLoggingEnabled(prefs.isLoggingEnabled())
        // AutoTtsService.onCreate opens with c3.a0.c/c3.a0.b, so every log a
        // user shares names the build it came from -- "Unknown" and -1 are the
        // values a0 answers with when PackageManager cannot find the package.
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onCreate Version Name: " + BuildConfig.VERSION_NAME + " Version Code: " + BuildConfig.VERSION_CODE }
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
        // Through the app's one logger, as AutoTTS's p0() writes it through its
        // own (c3.n.a.d): logcat as before, and the shared log file as well.
        // (The foreground start moved below loadAllSettings -- see there.)
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
        // AFTER loadAllSettings, not before it (owner, 2026-10-01). The flag is a
        // static that loadAllSettings fills, so in a fresh process -- boot, Clear
        // all, a memory kill: exactly when it matters -- it was still false
        // above and the foreground service never started at service start, only
        // at the first utterance. AutoTTS has the same order (onCreate tests a0
        // before n.q() loads it); a DELIBERATE DEPARTURE.
        try { if (showNotificationFlag) startForegroundIfPossible() } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.message ?: ex.toString()) }
        // e0() ends with s0(): the list has just been loaded, so hints go too.
        try { pushLanguageSets() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "pushLanguageSets failed: " + ex.toString()) }
        initDone = true
        try { initAllEngines() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "initAllEngines failed: " + ex.toString()) }
        // The two event sources restoreEngine waits on besides the keep-alive
        // bindings: an engine's package being installed or updated, and the
        // settings scan reaching an engine. Its own step, like the five above.
        try { registerPackageReceiver() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "registerPackageReceiver failed: " + ex.toString()) }
        // Tells the background scan when the app's screens close. onCreate runs on
        // the main thread, which is where a Lifecycle may be observed.
        try {
            androidx.lifecycle.ProcessLifecycleOwner.get().lifecycle.addObserver(screensClosedObserver)
            screensObserved = true
        } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "screen observer failed: " + ex.toString()) }
        running = java.lang.ref.WeakReference(this)
    }
    // THE VERSION IN THAT LOG LINE COMES FROM THE BUILD (2026-09-24). It used to be
    // c3.a0.a/b/c ported: ask PackageManager for our OWN PackageInfo -- a binder
    // call, and an API-33 branch for PackageInfoFlags -- with "Unknown" / -1 for
    // a lookup that cannot fail for the package asking about itself.
    // BuildConfig.VERSION_NAME / VERSION_CODE are written by AGP from the same
    // versionName / versionCode in app/build.gradle.kts, so the line prints the
    // same two values, the way the About screen already reads them.
    // "SHOW PERSISTENT NOTIFICATION" TAKES EFFECT THE MOMENT IT IS SWITCHED ON
    // (2026-09-24). It used to wait for the next utterance, which is in the
    // background, where Android 12+ may refuse a foreground start -- so the one
    // setting that keeps Easy Voice alive through Clear all and battery
    // optimization could stay off without a word. The app's screens now send a
    // start (applyForegroundSetting) while they are showing, and Android lets an
    // app on screen start its foreground service. Switched off, the foreground
    // is dropped and the start undone: the service lives on only as long as the
    // system keeps it bound, as it always did.
    override fun onStartCommand(intent: android.content.Intent?, flags: Int, startId: Int): Int {
        try {
            if (showNotificationFlag) {
                foregroundRefused = false
                if (!isForegroundActive()) startForegroundIfPossible()
            } else {
                if (isForegroundActive()) stopForegroundNow()
                stopSelf(startId)
            }
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onStartCommand: " + ex.toString())
        }
        return START_STICKY
    }
    override fun onTaskRemoved(rootIntent: android.content.Intent?) { super.onTaskRemoved(rootIntent) }

    // ==========================================================================
    //  THE ENGINE POOL
    //  One TextToSpeech client per installed engine. state == 2 means ready.
    // ==========================================================================
    inner class EngineWrapper(pkgName: String) {
        val pkg: String = normPkg(pkgName)
        // THE NAME AS ANDROID KNOWS IT, KEPT BESIDE THE NORMALISED ONE.
        //
        // `pkg` above is AutoTTS's own loose comparison key and every lookup in
        // this file matches on it, so it must stay stripped. But it is NOT a
        // package name any more: "_" is legal in an Android package and "com.x
        // .my_tts" normalises to "com.x.mytts", which is not installed.
        //
        // restoreEngine used to hand `pkg` straight to the TextToSpeech
        // constructor, and that is the SECOND way into the defect the init
        // guards now catch: an engine name the system cannot resolve makes
        // AOSP's initTts() fall through step 1 and bind the USER'S DEFAULT
        // engine instead -- Easy Voice -- and report SUCCESS. So for any engine
        // with an underscore in its package, every restore produced a client
        // pointed back at us. Construct with this one; compare with the other.
        val rawPkg: String = pkgName
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
        // ONE RESTORE PER EPISODE (owner, 2026-09-23) -- read restoreEngine.
        //   restoring     a restore of THIS engine is in flight
        //   restoreSpent  this episode's one restore has been used; only an
        //                 event clears it (the engine spoke, its process came
        //                 back, its package was updated, the scan reached it)
        //   processGone   our keep-alive binding saw the process die and has
        //                 not seen it come back; a restore now would only wait
        //                 for the same restart, so it waits for the event
        @Volatile var restoring: Boolean = false
        @Volatile var restoreSpent: Boolean = false
        @Volatile var processGone: Boolean = false
        //   restoreWanted an event that hands the restore back arrived WHILE a
        //                 restore was in flight; if that restore fails, it is
        //                 made once more instead of the event being lost
        @Volatile var restoreWanted: Boolean = false
        //   needRetryUsed  the one extra restore a language may ask for after a
        //                  restore FAILED with the engine's process up has been
        //                  used. Given back with the restore by every event
        //                  (rearmRestore), so it is at most two per episode.
        @Volatile var needRetryUsed: Boolean = false
        // The last locale this client refused while provably live (see
        // setLanguageFailed). Describes the client, so forgetClientState clears it.
        @Volatile var refusedLocale: String? = null
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
            set(value) { field = value; if (!value) paramLocale = null }
        // The current voice's locale as loadVoice last read it from the client's
        // params (paramsLocaleNow) while the voice NAME was unknown. Kept exactly
        // as long as voiceNow() would keep the voice it reads: dropped whenever a
        // setLanguage makes the voice unknown again, and with the client.
        @Volatile var paramLocale: Array<String>? = null
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
            paramLocale = null
            refusedLocale = null
        }
        // The locale of the voice the client's params name, as AOSP wrote it there
        // (ISO3 language, ISO3 country, variant of that voice), read with
        // TextToSpeech.getLanguage(), whose body is "No service call, but we're
        // accessing mParams". Null when there is no client or no connection.
        fun paramsLocaleNow(): Array<String>? {
            @Suppress("DEPRECATION")
            val l = try { tts?.language } catch (_: Exception) { null } ?: return null
            return arrayOf(l.language, l.country, l.variant)
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
        // A STOP ORDERED FOR ONE UTTERANCE MUST NOT LAND ON THE NEXT ONE
        // (owner, 2026-09-17: "explore by touch karte hain vahan per ... stop ho
        // jata hai ... jahan per on stop do bar do teen bar ho raha hoga na usko
        // sahi karo").
        //
        // stopExec is ONE thread, and our onStop() override queues here and then
        // releases the wait in its finally. AOSP calls stopForApp SYNCHRONOUSLY on
        // the binder thread before the next item can play, so the screen reader's
        // NEXT utterance can already be speaking by the time this runnable finally
        // runs -- and then it stops THAT one. The engine dispatches onStop for it,
        // and that callback is not stale: it carries the LIVE utterance's id and
        // matches its expectedId exactly.
        //
        // Explore-by-touch is a continuous stream of interrupts, so this fires
        // constantly there, which is precisely where the owner sees it.
        //
        // THE LATE STOP IS REDUNDANT AS WELL AS HARMFUL, which is what makes
        // skipping it correct rather than merely convenient: every speakChunk
        // issues speak(..., QUEUE_FLUSH, ...), so the new utterance has ALREADY
        // flushed whatever the old one left behind. There is nothing for the late
        // stop to cancel except the wrong thing.
        //
        // NO CLOCK. The generation advancing IS the event -- if it has moved, a
        // newer utterance owns the engine and this order is spent. `forGeneration
        // < 0` means "unconditional", which is what the two shutdown callers
        // (initAllEngines and restoreEngine) need, and they are unchanged.
        //
        // THE CORNER THIS LEAVES, stated rather than hidden: if the interrupt is
        // followed by an utterance that resolves NO engine (engineIndex < 0), that
        // generation never issues a speak, so nothing flushes the old audio and
        // the skipped order is not replaced by anything. It needs the pool to be
        // in the state where nothing can speak at all -- already the broken case
        // -- and it is bounded by the reader's next interrupt.
        // The newest utterance that flushed this engine (speak QUEUE_FLUSH, or the
        // empty-text flush), written under speakLock together with that call.
        @Volatile var flushedGeneration: Int = -1
        val speakLock = Any()
        fun stop(forGeneration: Int = -1) {
            val client = tts ?: return
            stopExec.execute {
                // A stop ordered for an older utterance runs only if no newer
                // utterance has flushed this engine since (owner, 2026-10-01).
                // If one has, that flush already ended the old audio, and the stop
                // would cut the NEW utterance -- the explore-by-touch regression of
                // 2026-09-17. If none has -- the new utterance has not reached this
                // engine yet, or speaks on another one -- the old audio is still
                // playing, and skipping the stop (as the old test
                // `forGeneration != synthesisGeneration` did) left it playing over
                // the new utterance. Under speakLock, so a flush cannot slip in
                // between the test and the stop.
                synchronized(speakLock) {
                    if (forGeneration >= 0 && flushedGeneration > forGeneration) {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) {
                            "stop for generation " + forGeneration + " is spent (flushed by " +
                            flushedGeneration + ") -- skipping " + pkg }
                        return@execute
                    }
                    try { client.stop() }
                    catch (ex: Exception) { android.util.Log.w("EasyVoice", "Stop failed for " + pkg, ex) }
                }
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
    // EngineWrapper.pkg strips "-" and "_", so every comparison against it must
    // strip them too. Comparing a RAW package name against a wrapper's is the
    // exact defect that made onEngineProcessGone unable to match its own engine.
    // Equal strings normalise equally, so the common case -- the client bound
    // to exactly the package it was built for -- answers without the four
    // String.replace allocations normPkg makes (owner, 2026-10-01).
    private fun isSameEnginePkg(one: String, other: String): Boolean =
        one == other || normPkg(one) == normPkg(other)

    // THE BACKSTOP FOR THE SAME DEFECT, AND IT NEEDS NO REFLECTION.
    //
    // The cause is fixed where the client is accepted -- see the two init
    // listeners and EngineFinder.boundEngineOf -- but that test is a read of a
    // NON-SDK field, and a platform that blocks it makes boundEngineOf answer
    // "the engine we asked for" and the guard pass. This one cannot be blocked.
    //
    // WHAT THE LOOP ACTUALLY IS -- CORRECTED 2026-09-23. The first version of this
    // backstop refused a loadVoice that re-entered itself on one stack, on the
    // belief that the 707 cycles in the owner's log (2026-09-21, 1.644 seconds,
    // not one onSynthesizeText) were one call nesting 707 deep. THE LOG SAYS
    // OTHERWISE, and it says so in one line:
    //
    //     Set voice 3: eng_USA res = 0 ...
    //      -success                       <- onLoadVoice RETURNS here
    //     onLoadVoice eng                 <- and only THEN does the next one start
    //
    // Nested calls would print every "onLoadVoice" first and every "-success" at
    // the unwind. So each cycle finishes before the next begins, and the reason is
    // in AOSP: the Stub's loadVoice ENQUEUES a LoadVoiceItem on our own
    // SynthHandler and returns (TextToSpeechService.java, `loadVoice` ->
    // `enqueueSpeechItem(QUEUE_ADD, new LoadVoiceItem(...))`). The self-bound
    // client's setLanguage therefore queued one more onLoadVoice for our own
    // synthesis thread, every time, for ever. The old refusal could not fire on
    // that -- its flag was cleared by the finally before the queued item ran.
    //
    // WHAT DOES RUN INLINE, and is the detector now. An in-process binder call is
    // a direct Java call (Stub.asInterface answers queryLocalInterface, i.e. our
    // own Stub object), so the part of setLanguage/setVoice that is NOT queued
    // runs on the calling thread: isLanguageAvailable, getDefaultVoiceNameFor,
    // the loadVoice binder method's onIsValidVoiceName, and getVoices. The log
    // shows exactly those -- "onIsLanguageAvailable: eng USA",
    // "onGetDefaultVoiceNameFor", "onGetVoices" -- inside one loadVoice, at one
    // timestamp. For a REAL engine those four are answered in ANOTHER process and
    // can never reach our overrides on this thread. So:
    //
    //     one of our engine-facing overrides runs while THIS thread is inside
    //     loadVoiceImpl  <=>  the client loadVoice is driving is bound to us.
    //
    // Nothing in loadVoiceImpl, loadVoiceOriginal or loadVoiceDedicated calls
    // those overrides directly -- onLoadLanguage asks onIsLanguageAvailable
    // BEFORE it calls loadVoice, outside the flag -- which is what makes the
    // equivalence exact rather than likely.
    //
    // THE ANSWER IS "NOT SUPPORTED", and that is what ends it at the source. A
    // refused isLanguageAvailable makes setLanguage return LANG_NOT_SUPPORTED
    // BEFORE it reaches the binder loadVoice, so no LoadVoiceItem is queued; and
    // a negative result is exactly what loadVoice's own failure branch turns into
    // restoreEngine(), which rebuilds the client from rawPkg. setVoice fails the
    // same way through onIsValidVoiceName. The restore is ONE per episode (see
    // restoreEngine), so even an engine that stays missing costs one rebuild,
    // and not a core.
    private val insideLoadVoice = java.lang.ThreadLocal<Boolean>()

    private fun calledFromOwnClient(what: String): Boolean {
        if (insideLoadVoice.get() != true) return false
        EasyVoiceLogger.error(EasyVoiceLogger.TAG,
            what + " was called from inside loadVoice on the same thread -- an engine " +
            "client is bound back to Easy Voice; refusing it")
        return true
    }

    // Set when the load just made could not put the language on its engine: the
    // engine had no live client ("TTS is not ready"), or setLanguage failed. Read
    // by onLoadLanguage straight after its own loadVoice, on the same thread and
    // under the same monitor, to decide whether to use another engine.
    @Volatile private var voiceLoadFailed = false

    private fun loadVoice(pkg: String, locale: java.util.Locale, variant: String, dedicated: Boolean) {
        val outer = insideLoadVoice.get() == true
        if (!outer) voiceLoadFailed = false
        insideLoadVoice.set(true)
        try { loadVoiceImpl(pkg, locale, variant, dedicated) } finally { if (!outer) insideLoadVoice.set(false) }
    }

    // A NEGATIVE setLanguage IS NOT ALWAYS A DEAD CLIENT (2026-09-23, read from
    // AOSP's TextToSpeech.setLanguage). AutoTTS's f0 answers every failure with a
    // restore (m0 -> k0.h), and we copied that. But the number says which failure
    // it was, and exactly one of them is something a restore can fix:
    //
    //     -2  LANG_NOT_SUPPORTED  runAction's errorResult for setLanguage, i.e. the
    //                             client is not connected -- or the engine refused
    //     -1  LANG_MISSING_DATA   ONLY ever the engine's own isLanguageAvailable
    //                             answer: setLanguage returns `result` unchanged
    //                             when it is below LANG_AVAILABLE, and every other
    //                             exit in the method is LANG_NOT_SUPPORTED
    //
    // So -1 means a LIVE engine, answering on a healthy connection, that has not
    // downloaded that language's voice. Restoring it cannot bring the data back;
    // what it does do is shut down the one client that engine has and bind a new
    // one -- cutting off whatever else that engine is saying (Hindi, while this
    // was Gujarati on the same Google TTS) and paying a bind plus an init, for
    // every attempt at that language, for as long as the process lived.
    //
    // DELIBERATE DEPARTURE. Since 2026-09-23 a -2 from a client that is still
    // connected to its own engine is kept too (see inside); only a dead or
    // re-bound client is restored.
    private fun setLanguageFailed(wrapper: EngineWrapper, locale: java.util.Locale, result: Int?) {
        voiceLoadFailed = true
        // ASKED ONCE, NOT ON EVERY CHUNK (2026-09-23). The test below reads the
        // engine's WHOLE voice set across a binder -- hundreds of Voice objects on
        // Google TTS -- and a language the engine refuses fails here on every
        // chunk of it. -1 needs no test at all (it is only ever the engine's own
        // answer, above); and a refusal already found to come from a live client
        // is the same answer from the same client until that client is replaced
        // or its process dies, both of which clear refusedLocale.
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " setLanguage(" + locale + ") = -1: the engine has no data for it -- kept, not restored")
            return
        }
        if (wrapper.tts != null && wrapper.refusedLocale == locale.toString()) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " setLanguage(" + locale + ") = " + result + " again -- kept")
            return
        }
        // A LIVE ENGINE THAT SAYS NO IS NOT RESTORED (2026-09-23, owner: "force stop
        // karta hun to Google mar jata hai ... aapko find karna chahie ki TTS mar
        // kyon jaate hain"). The owner's log: Google failed setLanguage on every
        // Hindi chunk, the restore answered "not applicable" because its ten were
        // already spent, yet speak() on the SAME client returned SUCCESS -- a dead
        // client cannot do that. And the scan's fresh clients got SUCCESS from
        // Google with ZERO voices. AOSP's setLanguage returns LANG_NOT_SUPPORTED
        // (-2) not only for a dead client but also when the engine's
        // getDefaultVoiceNameFor is empty or its getVoices() lacks that name --
        // i.e. an engine that is up but has not loaded its voices yet, which is a
        // cold Google after a reboot or after this app was force-stopped.
        //
        // Restoring that engine shut its client down and built a new one, over
        // and over, while it was still starting: each one a new session through
        // the system's TTS manager, and after ten the engine was given up on for
        // the life of the process. It never helped -- the engine was alive -- and
        // it kept the engine from ever settling.
        //
        // The test is AOSP's own: getVoices()'s runAction error result is NULL (no
        // connection, or the call threw), while a connected engine answers a Set,
        // empty or not. So: connected AND bound to the engine we asked for ->
        // keep the client; the next utterance calls setLanguage again (curLocale
        // stays unknown), and speaks the moment the engine has its voices. Dead,
        // or re-bound elsewhere (the AOSP fallback to the default engine, i.e. us)
        // -> restore exactly as before.
        try {
            val client = wrapper.tts
            val available = try { client?.isLanguageAvailable(locale) } catch (_: Exception) { null }
            val voices = try { client?.voices } catch (_: Exception) { null }
            val forLang = voices?.count { v -> try { v.locale.isO3Language == locale.isO3Language } catch (_: Exception) { false } }
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " setLanguage(" + locale + ") = " + result +
                "; engine says isLanguageAvailable = " + available +
                ", voices = " + (voices?.size?.toString() ?: "null (no connection)") +
                (if (forLang != null) ", for this language = " + forLang else ""))
            if (client != null && voices != null &&
                isSameEnginePkg(EngineFinder.boundEngineOf(client, wrapper.rawPkg), wrapper.rawPkg)) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " is connected and answering -- kept, not restored")
                wrapper.refusedLocale = locale.toString()
                return
            }
        } catch (_: Throwable) {}
        clientIsDead(wrapper, "setLanguage failed and the engine does not answer")
    }
    // setVoice FAILED. Its -1 is ERROR, which is both runAction's dead-client
    // answer and a live engine refusing that voice (not downloaded, or gone
    // since the scan). The same test as setLanguageFailed tells them apart:
    // a client that still answers getVoices() through its own engine is kept,
    // because a restore cannot give an engine a voice it does not have.
    private fun setVoiceFailed(wrapper: EngineWrapper, voice: android.speech.tts.Voice, result: Int?) {
        try {
            val client = wrapper.tts
            val voices = try { client?.voices } catch (_: Exception) { null }
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " setVoice(" + voice.name + ") = " + result +
                "; voices = " + (voices?.size?.toString() ?: "null (no connection)"))
            if (client != null && voices != null &&
                isSameEnginePkg(EngineFinder.boundEngineOf(client, wrapper.rawPkg), wrapper.rawPkg)) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " is connected and answering -- kept, not restored")
                return
            }
        } catch (_: Throwable) {}
        clientIsDead(wrapper, "setVoice failed and the engine does not answer")
    }
    // THE CLIENT IN THIS WRAPPER CAN NO LONGER BE USED. It stops being selectable
    // at once -- state -1, so no loader hands it another chunk and nothing speaks
    // into a dead connection -- and the engine's ONE restore is asked for.
    // Whether that runs now or when the engine's process is back is
    // restoreEngine's decision, not the caller's.
    //
    // The client is shut down HERE rather than when the restore runs, which may
    // be much later: a client that reconnected behind our back (runAction's
    // reconnect) holds a live session -- to Easy Voice itself, or through the
    // system's TTS manager, which keeps its own binding to the engine until the
    // client asks to disconnect. Nothing waits for that to be released.
    private fun clientIsDead(wrapper: EngineWrapper, why: String) {
        if (wrapper.restoring || (wrapper.state == -1 && wrapper.tts == null)) return
        wrapper.state = -1
        try { wrapper.stop(); wrapper.shutdown() } catch (_: Exception) {}
        wrapper.tts = null; wrapper.forgetClientState()
        restoreEngine(wrapper.pkg, why)
    }

    private fun loadVoiceImpl(pkg: String, locale: java.util.Locale, variant: String, dedicated: Boolean) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "LoadVoice " + pkg + " " + locale + " " + variant }
        if (dedicated && modeInt != 3) { loadVoiceDedicated(pkg, locale, variant, dedicated); return }
        val normPkg = normPkg(if (pkg.isEmpty()) lastEnginePkg else { lastEnginePkg = pkg; pkg })
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " current engine: " + normPkg }
        var idx = -1
        for (index in 0 until enginePool.size) { if (enginePool[index].pkg == normPkg && enginePool[index].state == 2) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " found!" }; idx = index; break } }
        if (idx != -1) {
            engineIndex = idx
            val wrapper = enginePool[idx]
            if (variant.isEmpty() && wrapper.voiceName.isNotEmpty()) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Load voice original" }; loadVoiceOriginal(normPkg, locale); return }
            var curLocale = localeOf("zxx")
            var curVoiceName = ""
            // THE CURRENT VOICE'S LOCALE WITHOUT A VOICE-SET MARSHAL (owner,
            // 2026-10-06: "performance top notch ... koi jugaad nahin").
            // After a successful setLanguage the voice name in the client's
            // params is one we were never told, so voiceNow() below asks
            // getVoice() -- AOSP's getVoices() marshal of the engine's whole voice
            // set -- and on a *Default language the only thing used from that
            // answer is its LOCALE. AOSP's setLanguage writes that locale into the
            // same params (KEY_PARAM_LANGUAGE/COUNTRY/VARIANT = the chosen
            // voice's getISO3Language / getISO3Country / getVariant), and
            // getLanguage() reads them back with "No service call". So when the
            // name cannot matter -- the request is *Default, which no engine
            // voice is called, so the "Do nothing!" test below cannot fire --
            // the locale comes from there. The name is fetched later, exactly as
            // before, if the variant this engine stores for the locale turns out
            // to be a named voice. Logging on keeps the old read and its line.
            var nameOwed = false
            var paramsLocale: Array<String>? = null
            if (wrapper.voiceName.isNotEmpty() && !wrapper.currentVoiceKnown && variant == "*Default" && !EasyVoiceLogger.loggingOn) {
                paramsLocale = wrapper.paramLocale ?: wrapper.paramsLocaleNow()
                if (paramsLocale != null) { nameOwed = true; wrapper.paramLocale = paramsLocale }
            }
            if (nameOwed) {
                // compared through localeMatchesParams; curLocale is not read
            } else if (wrapper.voiceName.isNotEmpty()) {
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
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " last " + curLocale + " " + curVoiceName }
                    val curCountry = try { curLocale.isO3Country } catch (_: Exception) { "" }
                    val reqCountry = try { locale.isO3Country } catch (_: Exception) { "" }
                    if (localeIso3(curLocale) == localeIso3(locale) &&
                        (curCountry == reqCountry || reqCountry == "") &&
                        curVoiceName == variant) {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Do nothing!" }; return
                    }
                }
            }
            var effectiveVariant = variant
            run {
                val voices = voiceEntries()
                val localeTag = locale.toString()
                for (index in voices.indices) {
                    val parts = voices[index].parts
                    if (parts.size < 2 || parts[0] != normPkg || parts[1] != localeTag) continue
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "voice: " + voices[index].raw }
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
                    // One walk under one lock, the locale written once (owner,
                    // 2026-10-01): this was a lock per entry and a toString()
                    // per entry on every language switch.
                    val found = LangStore.variantForEngineLocale(normPkg, localeTag)
                    if (found != null) effectiveVariant = found
                    break
                }
            }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " variant " + effectiveVariant }
            if (nameOwed && effectiveVariant != "*Default") {
                // A named voice is wanted after all: the comparison needs the name,
                // so read the voice exactly as the old path did (the "Do nothing!"
                // test it carried cannot fire for a *Default request).
                nameOwed = false
                val curVoice = wrapper.voiceNow()
                if (curVoice != null) { curLocale = curVoice.locale; curVoiceName = curVoice.name }
            }
            if (effectiveVariant == "*Default") {
                if (if (nameOwed) !localeMatchesParams(locale, paramsLocale!!) else !localeMatches(locale, curLocale)) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { locale.toString() + " vs " + curLocale.toString() }
                    val setLangResult = wrapper.tts?.setLanguage(locale)
                    if (setLangResult != null && setLangResult >= TextToSpeech.LANG_AVAILABLE) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Set voice 1" } } else setLanguageFailed(wrapper, locale, setLangResult)
                }
            } else if (effectiveVariant != curVoiceName) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Check voice 1" }
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
                            if (setVoiceResult != null && setVoiceResult >= TextToSpeech.SUCCESS) { wrapper.localeSet = true; wrapper.currentVoice = voiceObj; wrapper.currentVoiceKnown = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Set voice 2: " + voiceObj.name + " res=" + setVoiceResult } } else setVoiceFailed(wrapper, voiceObj, setVoiceResult)
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
                    if (setLangResult != null && setLangResult >= TextToSpeech.LANG_AVAILABLE) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Set voice 3: " + locale + " res = " + setLangResult + " " + wrapper.tts.toString() } } else setLanguageFailed(wrapper, locale, setLangResult)
                }
            }
            wrapper.locale = locale; wrapper.voiceName = effectiveVariant
        } else { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "TTS is not ready"); engineIndex = -1; voiceLoadFailed = true; recoverEngineNotReady(if (pkg.isEmpty()) lastEnginePkg else pkg) }
    }
    // THE CONFIGURED ENGINE IS NOT READY: ASK FOR IT BACK (2026-09-23, owner:
    // "Google ... uska voice sab ko chala jata hai ... aisa kuchh karo ki vah
    // jaaye hi na"). "TTS is not ready" used to be the end of it: the utterance
    // was released unspoken and nothing tried again. A wrapper that reached -1
    // could only come back through onEngineProcessBack, which needs the
    // keep-alive binding to connect -- and when that bindService itself failed
    // (engine mid-update, the one case the owner keeps hitting) no callback is
    // ever delivered. An engine the scan found AFTER this service started was
    // never in the pool at all. Either way every language configured on it
    // stayed silent until the process died.
    //
    // A language asking for its engine IS the event. So:
    //   state -1      -> the keep-alive binding is made if it is missing (a bind
    //                    that works delivers onServiceConnected, which is an
    //                    event of its own), and restoreEngine is ASKED -- it
    //                    runs only if this episode's one restore is unused and
    //                    the process is not known to be down;
    //   not in pool   -> added (only once the init walk has finished, so the
    //                    walk's indices cannot move) and handled the same way;
    //   state 0, 1, 2 -> nothing: not reached yet, restoring, or fine.
    // SINCE 2026-09-23 THIS NO LONGER RESTORES ON EVERY UTTERANCE (owner:
    // "restore wala baar-baar try mat karaya karo, is vajah se hi problem ho
    // rahi hai"). A language on a dead engine asks here once per chunk; the
    // first ask spends the restore and every later one is a log line.
    // This utterance is still released unspoken, as before; the next one finds
    // the engine back. No clock, and nothing on the healthy path runs.
    // It runs on the main looper, which is where the pool is grown and the walk
    // runs; the callers are the synthesis and binder threads.
    private fun recoverEngineNotReady(rawPkg: String) { onMainThread { recoverEngineNotReadyMain(rawPkg) } }
    private fun recoverEngineNotReadyMain(rawPkg: String) {
        try {
            if (rawPkg.isEmpty() || rawPkg.equals("disable", true) || rawPkg == packageName) return
            val normPkg = normPkg(rawPkg)
            var wrapper: EngineWrapper? = null
            for (index in 0 until enginePool.size) { if (enginePool[index].pkg.equals(normPkg, true)) { wrapper = enginePool[index]; break } }
            if (wrapper == null) {
                if (initializingIndex < walkList.size) return
                if (!EngineFinder.isTtsEngine(this, rawPkg)) return
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, rawPkg + " is configured but was not in the engine pool -- adding it")
                val added = EngineWrapper(rawPkg)
                added.state = -1
                enginePool.add(added)
                wrapper = added
            }
            if (wrapper.state != -1) return
            bindEngineKeepAlive(wrapper.rawPkg)
            // A RESTORE THAT FAILED WITH THE PROCESS UP WAITED FOR AN EVENT THAT
            // MAY NEVER COME (owner, 2026-10-01: "update karta hun ... tab kaam
            // nahin karta"). Nothing else hands the restore back when the engine
            // simply answered badly once -- no death, no package event, no scan
            // while the app stays closed -- so the language stayed silent until
            // the app was opened. A language asking for the engine now gets ONE
            // more restore per episode (needRetryUsed), never one per utterance.
            if (wrapper.restoreSpent && !wrapper.restoring && !wrapper.needRetryUsed) {
                wrapper.needRetryUsed = true
                wrapper.restoreSpent = false
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restoreTts " + wrapper.pkg + ": its last restore failed and a language needs it -- one more")
            }
            restoreEngine(wrapper.pkg, "not ready when a language asked for it")
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "recoverEngineNotReady: " + ex.toString())
        }
    }
    private fun loadVoiceOriginal(pkg: String, locale: java.util.Locale) {
        val normPkg = normPkg(if (pkg.isEmpty()) lastEnginePkg else { lastEnginePkg = pkg; pkg })
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
            if (setLangResult != null && setLangResult >= TextToSpeech.LANG_AVAILABLE) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; wrapper.locale = locale; wrapper.voiceName = "" } else setLanguageFailed(wrapper, locale, setLangResult)
        } else { engineIndex = -1; voiceLoadFailed = true; recoverEngineNotReady(if (pkg.isEmpty()) lastEnginePkg else pkg) }
    }
    private fun loadVoiceDedicated(pkg: String, locale: java.util.Locale, variant: String, dedicated: Boolean) {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "loadVoice_Secondary " + pkg + " " + locale + " " + variant }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Current Engine " + lastEnginePkg }
        val normPkg = normPkg(if (pkg.isEmpty()) lastEnginePkg else { lastEnginePkg = pkg; pkg })
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " current engine: " + normPkg }
        var idx = -1
        for (index in 0 until enginePool.size) { if (enginePool[index].pkg == normPkg && enginePool[index].state == 2) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " found!" }; idx = index; break } }
        if (idx == -1) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "TTS is not ready"); engineIndex = -1; voiceLoadFailed = true; recoverEngineNotReady(if (pkg.isEmpty()) lastEnginePkg else pkg); return }
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
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) {
                " *dedicated: keeping " + wrapper.voiceName + " on " + wrapper.pkg +
                ", not loading " + locale.toString() + " " + variant }
            return
        }
        // voiceNow(), for the reason written on the field: `tts.voice` is a full
        // voice-set marshal, and this path runs on every language change when
        // "Use dedicated engines" is on.
        val engineVoice = wrapper.voiceNow()
        val engineLocale = engineVoice?.locale ?: localeOf("zxx")
        val engineName = engineVoice?.name ?: ""
        if (previousEngineIndex == engineIndex && engineVoice != null) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Engine Variant " + engineName }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Engine Locale " + engineLocale }
            if (localeMatches(locale, engineLocale) &&
                (engineName == variant || variant == "*Default" || variant.isEmpty())) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " *0 Do nothing" }; return
            }
        }
        var effectiveVariant = variant
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Searching " + normPkg + " " + locale }
        run {
            val voices = voiceEntries()
            for (index in voices.indices) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " *" + voices[index].raw }
                val parts = voices[index].parts
                if (parts.size < 2 || parts[0] != normPkg) continue
                // Same fix as loadVoice's walk above, and the same reason.
                var langIdx = 0
                while (true) {
                    val entry = LangStore.entryAt(langIdx) ?: break
                    langIdx++
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "  -" + entry.enginePkg + " " + entry.localeTag + " " + entry.variant }
                    if (entry.enginePkg != normPkg || !localeMatches(locale, parseVoiceNameAsLocale(entry.localeTag)) || entry.variant.isEmpty()) continue
                    effectiveVariant = entry.variant
                    break
                }
                break
            }
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Variant " + effectiveVariant }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Locale " + locale }
        if ((effectiveVariant == "*Default" || effectiveVariant.isEmpty()) && !localeMatches(locale, engineLocale)) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " *1" }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { locale.toString() + " vs " + engineLocale }
            val setLangResult = wrapper.tts?.setLanguage(locale)
            if (setLangResult != null && setLangResult >= TextToSpeech.LANG_AVAILABLE) { wrapper.localeSet = true; wrapper.currentVoiceKnown = false; wrapper.locale = locale; wrapper.voiceName = locale.variant } else setLanguageFailed(wrapper, locale, setLangResult)
            return
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " *2" }
        if (engineVoice != null) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Engine Variant " + engineName }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " Engine Locale " + engineLocale }
            if (localeMatches(locale, engineLocale) &&
                (engineName == effectiveVariant || effectiveVariant == "*Default" || effectiveVariant.isEmpty())) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " *2.1 Do nothing" }; return
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
                if (setVoiceResult != null && setVoiceResult >= TextToSpeech.SUCCESS) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Set voice 2: " + voiceObj.name }
                    wrapper.currentVoice = voiceObj; wrapper.currentVoiceKnown = true
                    wrapper.locale = voiceObj.locale; wrapper.voiceName = effectiveVariant; wrapper.localeSet = true
                    return
                }
                setVoiceFailed(wrapper, voiceObj, setVoiceResult)
                break
            }
        }
        if (!localeMatches(locale, engineLocale)) {
            val setLangResult = wrapper.tts?.setLanguage(locale)
            if (setLangResult != null && setLangResult >= TextToSpeech.LANG_AVAILABLE) { wrapper.currentVoiceKnown = false; wrapper.locale = locale; wrapper.voiceName = locale.variant; wrapper.localeSet = true; EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Set voice 3" } } else setLanguageFailed(wrapper, locale, setLangResult)
        }
    }

    // ==========================================================================
    //  ENGINE INIT, RESTORE AND KEEP-ALIVE
    // ==========================================================================
    private fun initAllEngines() {
        synchronized(this) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "initAllTTS" }
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
            walkList = ArrayList(engineList)
            startNextInitEngine()
        }
    }
    // One step of the walk: construct the client for walkList[initializingIndex],
    // skipping (at state -1) any engine whose constructor throws.
    //
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
    //
    // AND EVERY STEP HAS A 30 s WATCHDOG NOW (2026-09-23, owner: "aur bhi TTS
    // honge, aise to nahin jaane chahie"). Engine N+1 is constructed ONLY inside
    // engine N's onInit, and AOSP's initTts dispatches nothing when the bind
    // succeeds but the engine process never comes up -- the case already written
    // up in restoreEngine. So one engine that never answered left EVERY engine
    // after it at state 0 for the life of the process: all their languages
    // silent, and nothing able to recover them, because onEngineProcessBack acts
    // only on -1. The watchdog is restoreEngine's own, same number, same
    // mechanism: shutdown() on the abandoned client prevents the late callback,
    // and the one-shot flag discards one already queued. That engine goes to -1,
    // where the reconnect and the speak-time recovery can still bring it back,
    // and the walk moves on. Nothing new waits on the healthy path.
    // EVERY ENGINE STARTS AT ONCE NOW (2026-09-23, owner: "phone restart ke bad
    // ... jaldi bolata nahin hai ... aur bhi TTS ko default rakhta hun to lock
    // kholte hi turant bolane lag jata hai"). The walk used to construct engine
    // N+1 only inside engine N's onInit, so the engine a language needs waited
    // for every engine ahead of it in engine_N order to finish its own cold
    // start -- Google TTS alone can take seconds after a reboot. Each client
    // already owns its cell, its index, its one-shot flag and its 30 s watchdog
    // (see EngineInitListener), so nothing they share needs the order; the pool
    // is filled up front, so no index moves while inits land. initializingIndex
    // now COUNTS finished inits, which keeps its one meaning for its readers
    // (walkPending, recoverEngineNotReady): "the startup walk is still running"
    // while it is below walkList.size. All of it runs on the main looper.
    private fun startNextInitEngine() {
        val count = walkList.size
        for (myIndex in 0 until count) enginePool.add(EngineWrapper(walkList[myIndex]))
        // TWO WAVES (owner, 2026-10-01: "process over kar raha ho ... latency").
        // Every installed engine used to be bound and built at once at a cold
        // start, the ones no language uses included, so the first utterance
        // waited behind that crowd. The engines a language is set up on start
        // now; the rest start the moment the last of those has answered (its
        // init, its failure or its 30 s watchdog -- initFinished counts them).
        // An EVENT, not a clock. No language maps to a second-wave engine, so
        // nothing waits for one; until it starts it is state 0, which only
        // awaitEngineStarting reads, and only for a language's own engine.
        val first = firstWaveOf(walkList, usedEnginePkgs())
        secondWave = (0 until count).filter { it !in first }
        firstWaveSize = first.size
        for (myIndex in first) startEngineClient(myIndex)
        if (count == 0) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "All tts engines have been initialized. (2)" }; startupScan() }
    }
    // Indices of walk to start first: every engine a language is set up on,
    // in walk order. None of them installed -> all of them, the old behaviour.
    private fun firstWaveOf(walk: List<String>, used: Set<String>): List<Int> {
        val first = ArrayList<Int>()
        for (index in walk.indices) if (normPkg(walk[index]) in used) first.add(index)
        if (first.isEmpty()) for (index in walk.indices) first.add(index)
        return first
    }
    // Normalised packages of the engines the language list routes to, plus the
    // built-in engine in Google mode, plus the device language's engine (walk
    // position 0, which buildEngineList puts first).
    private fun usedEnginePkgs(): Set<String> {
        val used = HashSet<String>()
        try {
            synchronized(LangStore.languages) {
                for (index in 0 until LangStore.languages.size) {
                    val entry = LangStore.languages[index]
                    if (entry.disabled || entry.enginePkg.isEmpty() || entry.enginePkg.equals("disable", true)) continue
                    used.add(normPkg(entry.enginePkg))
                }
            }
            if (modeInt == 3 && EngineFinder.builtInEngine.isNotEmpty()) used.add(normPkg(EngineFinder.builtInEngine))
            if (walkList.isNotEmpty()) used.add(normPkg(walkList[0]))
        } catch (_: Throwable) { used.clear() }
        return used
    }
    @Volatile private var secondWave: List<Int> = emptyList()
    @Volatile private var firstWaveSize = 0
    private fun startEngineClient(myIndex: Int) {
        val enginePkg = walkList[myIndex]
        bindEngineKeepAlive(enginePkg)
        val cell = arrayOfNulls<TextToSpeech>(1)
        val done = java.util.concurrent.atomic.AtomicBoolean(false)
        try {
            cell[0] = TextToSpeech(applicationContext, EngineInitListener(cell, myIndex, done), enginePkg)
            mainHandler.postDelayed({
                if (!done.compareAndSet(false, true)) return@postDelayed
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "init never arrived for " + enginePkg + " -- marking it not ready")
                try { cell[0]?.shutdown() } catch (_: Throwable) {}
                if (myIndex < enginePool.size) { enginePool[myIndex].tts = null; enginePool[myIndex].forgetClientState(); enginePool[myIndex].state = -1; afterStartupFailure(enginePool[myIndex], "its first start never answered") }
                initFinished()
            }, 30000L)
        } catch (_: Exception) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Error when initializing " + enginePkg }
            if (done.compareAndSet(false, true)) {
                if (myIndex < enginePool.size) enginePool[myIndex].state = -1
                if (myIndex < enginePool.size) afterStartupFailure(enginePool[myIndex], "its client could not be built")
                initFinished()
            }
        }
    }
    private fun liveClient(rawPkg: String): TextToSpeech? {
        if (destroyed) return null
        val norm = normPkg(rawPkg)
        for (index in 0 until enginePool.size) {
            val wrapper = enginePool[index]
            if (wrapper.pkg != norm) continue
            val client = wrapper.tts ?: return null
            if (wrapper.state != 2 || wrapper.restoring) return null
            if (!isSameEnginePkg(EngineFinder.boundEngineOf(client, wrapper.rawPkg), wrapper.rawPkg)) return null
            return client
        }
        return null
    }
    // THE SCAN'S READ OF A LIVE CLIENT FILLS THAT CLIENT'S VOICE CACHE (owner,
    // 2026-10-06: "pahla aur dusra karna hai"). The scan asks the service's own
    // client for getVoices() -- the full voice-set marshal -- and used to keep
    // the answer only for its lists, so the first switch to a language with a
    // NAMED voice asked the same client for the same set again. Handed over
    // here it is the object loadVoiceImpl would have fetched, from the same
    // connection, only earlier; a voice installed since is found by the
    // re-query on a miss, exactly as for any cached set. Runs on the main
    // looper, where every replacement of `tts` (and its forgetClientState)
    // also runs, so a client replaced meanwhile is seen and nothing is kept.
    private fun offerVoices(client: TextToSpeech, voices: MutableSet<android.speech.tts.Voice>) {
        if (destroyed) return
        for (index in 0 until enginePool.size) {
            val wrapper = enginePool[index]
            if (wrapper.tts !== client) continue
            if (wrapper.voicesCache == null) wrapper.voicesCache = voices
            return
        }
    }
    private fun afterStartupFailure(wrapper: EngineWrapper, why: String) {
        try {
            if (destroyed) return
            if (engineProcessUp(wrapper.rawPkg)) restoreEngine(wrapper.pkg, why)
            else { wrapper.processGone = true; bindEngineKeepAlive(wrapper.rawPkg); EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + wrapper.pkg + " (" + why + "): restoring it when its process is up" } }
        } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "afterStartupFailure: " + ex.toString()) }
    }
    private fun initFinished() {
        engineSettled()
        initializingIndex++
        if (initializingIndex == firstWaveSize && secondWave.isNotEmpty()) {
            val rest = secondWave
            secondWave = emptyList()
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "first engines answered -- starting the other " + rest.size }
            for (myIndex in rest) startEngineClient(myIndex)
        }
        if (initializingIndex == walkList.size) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "All tts engines have been initialized. (2)" }; startupScan() }
    }

    // ==========================================================================
    //  THE BACKGROUND SCAN (owner, 2026-09-24: "background mein scanning nahin ho
    //  rahi hai, vah tabhi scan ho rahi hai jab app open hoti hai ... jaise hi RAM
    //  clear ho jaaye to automatically scanning shuru ho jaani chahie")
    //
    //  The engine scan used to run only when the settings app was opened. The
    //  service is what the system starts on its own -- a screen reader binds the
    //  preferred engine at boot, and binds it again after its process was killed
    //  (a RAM clear) -- so the service's own start is the event that means "the
    //  phone came back", and the scan now runs from it:
    //    * once per process, when the startup walk has finished (every engine
    //      then has a warm client of ours, so the scan's fresh clients bind fast);
    //    * when a TTS engine is installed, updated, turned on or off, or removed
    //      (packageReceiver);
    //  quietly (no screen, nothing shown). A finished
    //  scan saves the engine, voice and language lists, hands every engine that
    //  answered its restore back (engineAnswered), and onScanFinished binds any
    //  engine the pool did not have.
    //
    //  NEVER while the app's own screens are showing (ProcessLifecycleOwner): the
    //  main screen scans on every open anyway, and a background scan that rebuilt
    //  the language list under the Languages screen would move its rows. Nor
    //  while another scan runs -- a newer scan supersedes the older one, whose
    //  screen would then never finish. A scan put off for either reason is run
    //  when the screens close (onStop below) or when that scan finishes. No clock:
    //  every trigger is an event.
    //
    //  WHAT IT CANNOT DO, stated: if the phone's maker does not let Easy Voice start
    //  by itself (Auto-start on Xiaomi, Oppo, Realme, Vivo, Huawei and others),
    //  the system never starts the service after a restart or a RAM clear, and
    //  nothing inside the app can run.
    // ==========================================================================
    // The startup scan. The process starting is the first change EngineFinder
    // counts, so it is owed until the first scan of this process finishes -- the
    // main screen's, if the app was opened first, or this one.
    private fun startupScan() {
        if (startupScanDone) return
        startupScanDone = true
        tryOwedScan("the service started")
    }
    private fun appOnScreen(): Boolean = try {
        androidx.lifecycle.ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(androidx.lifecycle.Lifecycle.State.STARTED)
    } catch (_: Throwable) { false }
    // A package event that needs the engines read again. One the running scan
    // will read anyway (it has not reached that engine yet, or the package is
    // gone and its finalize drops it) owes nothing; any other is numbered, and
    // the first scan to START after it covers it -- the main screen's or ours.
    private fun requestScan(why: String, pkg: String) {
        onMainThread {
            if (destroyed) return@onMainThread
            try {
                if (EngineFinder.scanRunning &&
                    (EngineFinder.runningScanWillRead(pkg) || !EngineFinder.isInstalled(applicationContext, pkg))) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Background scan (" + why + "): the running scan covers it" }
                    return@onMainThread
                }
                EngineFinder.changeSeq.incrementAndGet()
                tryOwedScan(why)
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "requestScan: " + ex.toString())
            }
        }
    }
    // Starts a scan only when one is owed, none is running and none of the app's
    // screens is open (the main screen scans for itself). A running scan ending
    // and the app's screens closing both come back here, so an owed scan is never
    // dropped -- it waits -- and one already covered is never run twice.
    private fun tryOwedScan(why: String) {
        onMainThread {
            if (destroyed) return@onMainThread
            try {
                if (!EngineFinder.scanOwed()) return@onMainThread
                if (EngineFinder.scanRunning) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Background scan (" + why + "): a scan is already running -- scanning again after it if it does not cover this" }
                    return@onMainThread
                }
                if (appOnScreen()) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Background scan (" + why + "): the app is open -- scanning when it closes unless it scans first" }
                    return@onMainThread
                }
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Background scan: " + why)
                EngineFinder.scanLanguages(applicationContext, yieldToSpeech = true) { langs ->
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Background scan finished: " + langs.size + " languages" }
                }
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "tryOwedScan: " + ex.toString())
            }
        }
    }
    private fun onScanFinished() {
        onMainThread {
            if (destroyed) return@onMainThread
            try {
                if (initializingIndex >= walkList.size) {
                    for (rawPkg in ArrayList(engineList)) {
                        if (rawPkg == packageName || EngineFinder.isSelfEngine(rawPkg) || wrapperFor(rawPkg) != null) continue
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Scan found " + rawPkg + ", which the engine pool did not have -- adding it")
                        val added = EngineWrapper(rawPkg)
                        added.state = -1
                        enginePool.add(added)
                        bindEngineKeepAlive(rawPkg)
                        restoreEngine(added.pkg, "the scan found it")
                    }
                }
                tryOwedScan("a change arrived during the last scan")
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onScanFinished: " + ex.toString())
            }
        }
    }
    private val screensClosedObserver = object : androidx.lifecycle.DefaultLifecycleObserver {
        override fun onStop(owner: androidx.lifecycle.LifecycleOwner) {
            tryOwedScan("a change arrived while the app was open")
        }
    }
    @Volatile private var screensObserved = false
    // onInit IS NOT ALWAYS ON THE MAIN THREAD, and both listeners below assume
    // it is (2026-09-23, read from AOSP main). The public
    // TextToSpeech(Context, OnInitListener, String) constructor passes
    // isSystem = TRUE, so every client connects through SystemConnection: the
    // system's TextToSpeechManagerService binds the engine for us and answers
    // over ITextToSpeechSessionCallback. A SUCCESS still arrives on the main
    // thread (SetupConnectionAsyncTask.onPostExecute), but
    //
    //     public void onError(String errorInfo) { ... dispatchOnInit(ERROR); }
    //
    // runs on the BINDER thread that callback lands on, and dispatchOnInit calls
    // the listener inline when there is no executor. So an engine that could not
    // be bound -- mid-update, frozen, missing -- ran these listeners on a binder
    // thread, where they grow enginePool (a plain ArrayList the main thread also
    // walks), step initializingIndex, and construct the NEXT engine's client
    // while the main thread may be doing the same. Everything they touch is
    // main-thread state, so they are moved onto it. On the main thread already,
    // nothing changes: the body runs inline exactly as before.
    private fun onMainThread(block: () -> Unit) {
        if (android.os.Looper.getMainLooper().isCurrentThread) block() else mainHandler.post { block() }
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
    inner class EngineInitListener(
        private val cell: Array<TextToSpeech?>,
        private val myIndex: Int,
        private val done: java.util.concurrent.atomic.AtomicBoolean
    ) : TextToSpeech.OnInitListener {
        // Guarded 2026-09-11 for the same two reasons as RestoreInitListener: this
        // is the main looper, so an escape is a process death, and it is also the
        // only thing that advances the pool walk -- every engine after the one that
        // threw would never be initialised, and every language on those engines
        // would answer "TTS is not ready" for the life of the process.
        override fun onInit(status: Int) { onMainThread { handleInit(status) } }
        private fun handleInit(status: Int) {
            // One shot: an onInit that lands after the watchdog gave up on this
            // engine belongs to a step the walk has already left.
            if (!done.compareAndSet(false, true)) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Init arrived after the walk moved on; discarding that client" }
                try { cell[0]?.shutdown() } catch (_: Throwable) {}
                return
            }
            var finished = false
            try {
            val initializingTts = cell[0]
            if (myIndex >= walkList.size || myIndex >= enginePool.size) { initFinished(); return }
            val wrapper = enginePool[myIndex]
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Init " + wrapper.pkg }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "res " + status }
            val wantedPkg = walkList[myIndex]
            val boundPkg = if (status == TextToSpeech.SUCCESS) EngineFinder.boundEngineOf(initializingTts, wantedPkg) else wantedPkg
            if (status == TextToSpeech.SUCCESS && !isSameEnginePkg(boundPkg, wantedPkg)) {
                // AOSP HANDED US SOMEBODY ELSE'S ENGINE AND CALLED IT SUCCESS.
                // See EngineFinder.boundEngineOf for the two AOSP lines that make
                // this reachable; what it costs is in the note above onInit.
                EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                    "asked for " + wantedPkg + " and got a client bound to " + boundPkg + " -- refusing it")
                try { initializingTts?.shutdown() } catch (_: Throwable) {}
                wrapper.tts = null; wrapper.forgetClientState(); wrapper.state = -1
                afterStartupFailure(wrapper, "its first start came back bound to " + boundPkg)
            } else if (status == TextToSpeech.SUCCESS) {
                if (forceAccessibilityFlag) { try { initializingTts?.setAudioAttributes(accessibilitySpeech); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) } }
                wrapper.tts = initializingTts; wrapper.forgetClientState(); wrapper.state = 2
                if (wantedPkg == EngineFinder.builtInEngine) googleEngineIndex = myIndex
            } else {
                wrapper.tts = initializingTts; wrapper.forgetClientState(); wrapper.state = -1
                afterStartupFailure(wrapper, "its first start answered " + status)
            }
            finished = true
            initFinished()
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Engine init failed: " + ex.toString())
                // The walk must still end, or walkPending stays true for the life
                // of the process and the startup scan never runs.
                if (!finished) { finished = true; try { initFinished() } catch (_: Throwable) {} }
            }
        }
    }
    // ==========================================================================
    //  RESTORE: ONE PER EPISODE, EVERY ENGINE ON ITS OWN, AND ONLY ON AN EVENT
    //  (owner, 2026-09-23: "restore wala baar-baar try mat karaya karo, is vajah
    //  se hi problem ho rahi hai ... ek bar mein sab restore ho jaaye").
    // ==========================================================================
    //
    // WHAT A RESTORE IS FOR, read from AOSP rather than assumed. Every client
    // connects through the system's TextToSpeechManagerService, whose session
    // connection does, when the engine's process dies:
    //
    //     if (!connected) { runSessionCallbackMethod(mCallback::onDisconnected);
    //                       ...; mCallback = null; }
    //
    // so the client's Connection loses mService AND mServiceConnection, and
    // nothing ever reconnects it -- the manager has dropped the callback. A
    // client whose engine died is dead for good, and building a new one is the
    // only cure. That is what a restore is. It cures NOTHING else: a live engine
    // that says no (no voice data, still loading its voices) is kept, never
    // restored -- setLanguageFailed / setVoiceFailed decide that first.
    //
    // WHAT WAS WRONG WITH THE OLD ONE. AutoTTS's shape, carried over: one global
    // slot (restoringIndex), so a second engine could not be restored while the
    // first was in flight; ten tries per engine, refilled every time the engine
    // said anything (onStart) or reconnected; and called from every failure
    // path, so a language on a broken engine tore that engine down again on
    // every utterance until the ten ran out -- each teardown a new session
    // through the system's TTS manager, each cutting off whatever else the
    // engine was saying. And on a process death it restored TWICE: once from
    // onServiceDisconnected, while the process was still down and the new bind
    // could only wait for the system's own restart (a bind to a service pending
    // restart does nothing, ActiveServices.bringUpServiceLocked), and again from
    // onServiceConnected when it came back.
    //
    // WHAT IT IS NOW:
    //   * per engine: `restoring` instead of the global slot, so every engine
    //     that needs one restores at the same time, and one engine's slow bind
    //     never holds another's back;
    //   * ONE per episode: `restoreSpent`. The first failure spends it; every
    //     later failure in the same episode is a log line. It is given back
    //     only by an EVENT that means the engine can be reached again:
    //         the engine spoke (onStart)
    //         its process came back (keep-alive onServiceConnected)
    //         its package was installed, updated or changed (packageReceiver)
    //         the settings scan reached it with its voices (engineAnswered)
    //   * a known-dead process is waited for: `processGone` is set by the
    //     keep-alive binding's death callbacks, and the restore runs from
    //     onServiceConnected instead of racing the system's restart;
    //   * state 1 while in flight, so no loader picks the wrapper (they all
    //     match state 2) and recovery does not start a second one (it acts on
    //     -1 only).
    // No cap, no counter and no clock on the speaking path: nothing can loop,
    // because nothing but an event can hand the restore back.
    //
    // It runs on the main looper, like every callback that ends it: the flags
    // are then only ever touched on one thread, and a caller on the synthesis
    // or binder thread never builds a TextToSpeech inside onLoadLanguage.
    private fun restoreEngine(pkg: String, why: String) { onMainThread { restoreEngineMain(pkg, why) } }
    private fun wrapperFor(rawPkg: String): EngineWrapper? {
        val norm = normPkg(rawPkg)
        for (index in 0 until enginePool.size) { if (enginePool[index].pkg.equals(norm, ignoreCase = true)) return enginePool[index] }
        return null
    }
    // Set in onDestroy: no restore starts after it, and one still in flight
    // releases its client instead of handing it to a dead service.
    @Volatile private var destroyed = false
    private fun restoreEngineMain(pkg: String, why: String) {
        if (destroyed) return
        try {
            // NORMALISED, like every other pool lookup in this file (2026-09-16):
            // the keep-alive callbacks hand over the RAW name out of engineList.
            val wrapper = wrapperFor(pkg)
            if (wrapper == null) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + pkg + ": not in the engine pool" }; return }
            if (wrapper.restoring) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + wrapper.pkg + " (" + why + "): already being restored" }; return }
            // NOT STARTED YET (owner, 2026-10-01). State 0 with no client is an
            // engine whose first start is still pending -- in flight, or in the
            // second wave of startNextInitEngine. A restore here built a second
            // client beside the one being built; the first start's own answer
            // (initFinished, afterStartupFailure) is what follows instead.
            if (wrapper.state == 0 && wrapper.tts == null) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + wrapper.pkg + " (" + why + "): its first start is still pending" }; return }
            if (wrapper.restoreSpent) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + wrapper.pkg + " (" + why + "): restored once already -- waiting for it to come back" }; return }
            if (wrapper.processGone) {
                // Waiting needs a binding that can report the return. One that
                // is missing (its bind failed mid-update) is made again here;
                // if that fails too, the package receiver reports the install.
                bindEngineKeepAlive(wrapper.rawPkg)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + wrapper.pkg + " (" + why + "): its process is down -- restoring it when it is back" }
                return
            }
            // ONLY INTO A PROCESS THAT IS UP (2026-09-24). The keep-alive binding
            // holds the engine's IBinder, and isBinderAlive() says whether that
            // process is running now. When it is not -- the binding is still
            // connecting, or its bind failed while the engine was being updated
            // -- a client built now can only wait for the same start, and when
            // its init fails the episode's one restore is gone with no event
            // left to give it back. So it waits instead: processGone is set, the
            // binding is made if missing, and its onServiceConnected (the
            // process is up) makes this restore. The restore is not spent.
            if (!engineProcessUp(wrapper.rawPkg)) {
                wrapper.processGone = true
                bindEngineKeepAlive(wrapper.rawPkg)
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "restoreTts " + wrapper.pkg + " (" + why + "): its process is not up yet -- restoring it when it is" }
                return
            }
            wrapper.restoring = true
            wrapper.restoreSpent = true
            wrapper.state = 1
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restoreTts " + wrapper.pkg + " (" + why + ")")
            // stop() and shutdown() capture the client at call time (see
            // EngineWrapper.stop), so dropping it from the wrapper right after is
            // safe, and nothing can speak into it while the new one binds.
            try { wrapper.stop(); wrapper.shutdown() } catch (_: Exception) {}
            wrapper.tts = null; wrapper.forgetClientState()
            wrapper.voiceName = ""; wrapper.locale = null
            // THE 30 s WATCHDOG STAYS, and it is not a clock on the speaking path.
            // AOSP's initTts returns SUCCESS the moment bindService does and
            // dispatches NOTHING; the init callback comes later, from the
            // connection. A bind that succeeds against a process that never
            // starts therefore never calls back, and TextToSpeech has no timeout
            // of its own. EngineFinder's scan uses the same 30 s. shutdown() on
            // the abandoned client is AOSP's own way to prevent the late callback
            // (it disconnects the pending connection), and the one-shot flag is
            // the belt to that brace for a callback already queued.
            val cell = arrayOfNulls<TextToSpeech>(1)
            val done = java.util.concurrent.atomic.AtomicBoolean(false)
            val watchdog = Runnable {
                if (!done.compareAndSet(false, true)) return@Runnable
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore init never arrived for " + wrapper.pkg + " -- giving that client up")
                try { cell[0]?.shutdown() } catch (_: Throwable) {}
                wrapper.tts = null; wrapper.forgetClientState()
                wrapper.state = -1
                wrapper.restoring = false
                engineSettled()
                restoreFailed(wrapper, "no answer in 30 s")
            }
            try {
                cell[0] = TextToSpeech(applicationContext, RestoreInitListener(cell, wrapper, done, watchdog), wrapper.rawPkg)
                mainHandler.postDelayed(watchdog, 30000L)
            } catch (ex: Throwable) {
                // The constructor reads Settings.Secure, resolves the engine and
                // calls bindService, so it can throw exactly when the engine is
                // mid-update. The wrapper goes to -1, where an event finds it.
                // Throwable, not Exception: an Error escaping here would leave
                // `restoring` set, and this engine could never be restored again.
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Error when restoring " + wrapper.pkg + ": " + ex.message)
                done.set(true)
                wrapper.state = -1
                wrapper.restoring = false
                engineSettled()
                restoreFailed(wrapper, "the client could not be built")
            }
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restoreEngine: " + ex.toString())
        }
    }
    // A RESTORE THAT FAILED keeps the episode's one restore spent -- that is the
    // 2026-09-23 rule, and it is what stops a broken engine being torn down and
    // rebuilt on every utterance. Two things decide what happens next, both
    // events rather than clocks:
    //   * an event that gives the restore back arrived while this one was in
    //     flight (restoreWanted): it was not answered, so it is answered now,
    //     once -- before 2026-09-24 it was lost, and with it the engine;
    //   * the engine's process is not up (its keep-alive IBinder is dead or
    //     missing): the failure is the process, not the engine, so it waits for
    //     the binding to report the process back (processGone), which restores.
    // Otherwise the process is up and refused a client: the next event -- the
    // engine speaks, its package changes, a scan reads it -- gives
    // the restore back.
    private fun restoreFailed(wrapper: EngineWrapper, why: String) {
        if (destroyed) return
        if (wrapper.restoreWanted) {
            wrapper.restoreWanted = false
            wrapper.restoreSpent = false
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore of " + wrapper.pkg + " failed (" + why + "); an event came in meanwhile -- restoring it again")
            restoreEngine(wrapper.pkg, "an event arrived during the last restore")
            return
        }
        if (!engineProcessUp(wrapper.rawPkg)) {
            wrapper.processGone = true
            bindEngineKeepAlive(wrapper.rawPkg)
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore of " + wrapper.pkg + " failed (" + why + ") and its process is not up -- restoring it when it is")
            return
        }
        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore of " + wrapper.pkg + " failed (" + why + ") with its process up -- waiting for the next event")
    }
    // THE ONE WAY AN EVENT GIVES THE RESTORE BACK. While a restore is in flight
    // the event is remembered (restoreFailed answers it) instead of dropped.
    private fun rearmRestore(wrapper: EngineWrapper, why: String) {
        wrapper.restoreSpent = false
        wrapper.needRetryUsed = false
        if (wrapper.restoring) { wrapper.restoreWanted = true; return }
        if (wrapper.state == -1) restoreEngine(wrapper.pkg, why)
    }
    // THE ENGINE'S PROCESS IS BACK (keep-alive onServiceConnected). The event
    // that ends a death episode: the restore is handed back, and a wrapper left
    // without a client (-1) is restored now, once. It is also the first connect
    // of every startup bind, while the init is still running -- that wrapper is
    // at 0 or 2, and -1 is the exact test, so nothing is done for it.
    //
    // This is also what made -1 recoverable in the first place (2026-09-03):
    // before it, a wrapper that reached -1 was terminal, because every other
    // restore site found its wrapper with `state == 2`.
    private fun onEngineProcessBack(pkg: String) {
        val wrapper = wrapperFor(pkg) ?: return
        wrapper.processGone = false
        rearmRestore(wrapper, "its process is back")
    }
    // An EVENT that proves the engine can be reached right now, from outside the
    // service: the settings scan just built a fresh client for it and read its
    // voices (EngineFinder, through the companion's engineAnswered).
    private fun onEngineAnswered(rawPkg: String) {
        onMainThread {
            val wrapper = wrapperFor(rawPkg) ?: return@onMainThread
            rearmRestore(wrapper, "the scan reached it")
        }
    }
    // THE ENGINE'S PACKAGE WAS INSTALLED, UPDATED OR CHANGED. An update kills
    // the engine's process and the keep-alive's re-bind in onBindingDied can
    // fail while the package is being replaced -- after which no binding exists
    // to report the return, and the engine used to stay dead until something
    // restored it on speculation. The system's own package broadcast IS that
    // report. Registered at runtime (a manifest receiver cannot get these since
    // API 26), not exported: AOSP delivers a system broadcast to a non-exported
    // receiver anyway -- ActivityManager.checkComponentPermission, "Root, system
    // server get to do everything".
    private val packageReceiver = object : android.content.BroadcastReceiver() {
        override fun onReceive(context: android.content.Context?, intent: android.content.Intent?) {
            try {
                val changed = intent?.data?.schemeSpecificPart ?: return
                // Its name and whether it is ours are asked again, not remembered.
                EngineFinder.forget(changed)
                // An engine installed, updated or turned on can change which one
                // is the phone's built-in engine; ask again.
                EngineFinder.refreshBuiltInEngine()
                // A TTS ENGINE CHANGED, SO THE VOICE LISTS ARE READ AGAIN. The
                // halves of an update (REMOVED and ADDED while replacing) are
                // skipped; REPLACED follows them. A removed package is known only
                // by our own list, since the system no longer resolves it.
                val replacing = intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false)
                val isEngine = !EngineFinder.isSelfEngine(changed) && when (intent.action) {
                    android.content.Intent.ACTION_PACKAGE_REMOVED -> !replacing && engineList.contains(changed)
                    android.content.Intent.ACTION_PACKAGE_ADDED -> !replacing && isTtsEngine(changed)
                    else -> isTtsEngine(changed)
                }
                if (isEngine) requestScan("package " + changed + ": " + intent.action?.substringAfterLast('_')?.lowercase(), changed)
                if (intent.action == android.content.Intent.ACTION_PACKAGE_REMOVED) return
                // ONE REBIND PER UPDATE (2026-09-30, "koi chijen double double
                // nahin honi chahie"). An update sends ADDED(replacing) and then
                // REPLACED; both used to unbind and bind the keep-alive and ask for
                // the restore, the second while the first bind was still
                // connecting. The ADDED half is skipped here as the scan above
                // already skips it; REPLACED does the work.
                if (intent.action == android.content.Intent.ACTION_PACKAGE_ADDED && replacing) return
                val wrapper = wrapperFor(changed) ?: return
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Package " + changed + ": " + intent.action)
                // A live binding reports the process itself; only a missing or
                // dead one is made again, and its connect event does the rest.
                if (wrapper.processGone || !engineBinders.containsKey(wrapper.rawPkg)) {
                    unbindEngineKeepAlive(wrapper.rawPkg)
                    bindEngineKeepAlive(wrapper.rawPkg)
                }
                rearmRestore(wrapper, "its package was " + (intent.action?.substringAfterLast('_')?.lowercase() ?: "changed"))
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "packageReceiver: " + ex.toString())
            }
        }
    }
    @Volatile private var packageReceiverRegistered = false
    private fun isTtsEngine(pkg: String): Boolean = EngineFinder.isTtsEngine(this, pkg)
    private fun registerPackageReceiver() {
        val filter = android.content.IntentFilter()
        filter.addAction(android.content.Intent.ACTION_PACKAGE_ADDED)
        filter.addAction(android.content.Intent.ACTION_PACKAGE_REPLACED)
        filter.addAction(android.content.Intent.ACTION_PACKAGE_CHANGED)
        filter.addAction(android.content.Intent.ACTION_PACKAGE_REMOVED)
        filter.addDataScheme("package")
        ContextCompat.registerReceiver(this, packageReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
        packageReceiverRegistered = true
    }
    // Its own client and its own wrapper, captured: several restores can be in
    // flight at once, so nothing here may read a shared field to learn which
    // restore it is finishing.
    inner class RestoreInitListener(
        private val cell: Array<TextToSpeech?>,
        private val wrapper: EngineWrapper,
        private val done: java.util.concurrent.atomic.AtomicBoolean,
        private val watchdog: Runnable
    ) : TextToSpeech.OnInitListener {
        // ERROR arrives on a BINDER thread (SystemConnection's onError dispatches
        // inline); everything below is main-thread state.
        override fun onInit(status: Int) { onMainThread { handleInit(status) } }
        private fun handleInit(status: Int) {
            // ONE SHOT. A callback that arrives after the watchdog gave this
            // client up must touch nothing -- it releases its own client.
            if (!done.compareAndSet(false, true)) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Restore init arrived after it was given up; discarding that client" }
                try { cell[0]?.shutdown() } catch (_: Throwable) {}
                return
            }
            mainHandler.removeCallbacks(watchdog)
            if (destroyed) { try { cell[0]?.shutdown() } catch (_: Throwable) {}; wrapper.restoring = false; return }
            var failure: String? = null
            try {
                val initializingTts = cell[0]
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Restore " + wrapper.pkg }
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "res " + status }
                // DELIBERATE DEPARTURE (owner request, 2026-09-03). Audio
                // attributes live in the client's mParams, and this is a brand new
                // client, so whatever the old one carried is gone. AutoTTS never
                // clears k0.h here, which left the force-accessibility switch dead
                // on this engine after a restore made while it was OFF.
                wrapper.audioAttrSet = false
                val restoredPkg = if (status == TextToSpeech.SUCCESS) EngineFinder.boundEngineOf(initializingTts, wrapper.rawPkg) else wrapper.rawPkg
                if (status == TextToSpeech.SUCCESS && !isSameEnginePkg(restoredPkg, wrapper.rawPkg)) {
                    // THE AOSP FALLBACK: the public constructor passes
                    // useFallback = true, so an engine missing at this instant --
                    // exactly when a restore runs -- binds the default engine,
                    // i.e. Easy Voice, and reports SUCCESS. Refused; -1 leaves it
                    // for the next event.
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                        "restore of " + wrapper.pkg + " came back bound to " + restoredPkg + " -- refusing it")
                    try { initializingTts?.shutdown() } catch (_: Throwable) {}
                    wrapper.tts = null; wrapper.forgetClientState()
                    wrapper.state = -1
                    failure = "it came back bound to " + restoredPkg
                } else if (status == TextToSpeech.SUCCESS) {
                    if (forceAccessibilityFlag) { try { initializingTts?.setAudioAttributes(accessibilitySpeech); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) } }
                    wrapper.tts = initializingTts; wrapper.forgetClientState()
                    wrapper.state = 2
                    wrapper.voiceName = ""
                    wrapper.locale = null
                    wrapper.restoreWanted = false
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.pkg + " restored")
                } else {
                    try { initializingTts?.shutdown() } catch (_: Throwable) {}
                    wrapper.tts = null; wrapper.forgetClientState()
                    wrapper.state = -1
                    failure = "init answered " + status
                }
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Restore init failed: " + ex.toString())
                if (wrapper.state == 1) wrapper.state = -1
                failure = "init threw " + ex.javaClass.simpleName
            } finally {
                wrapper.restoring = false
                engineSettled()
            }
            if (failure != null) restoreFailed(wrapper, failure)
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
            val intent = android.content.Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE).setPackage(pkg)
            val resolveInfo = packageManager.resolveService(intent, 0)
            if (resolveInfo == null || resolveInfo.serviceInfo == null) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "No TTS service found in " + pkg }; return }
            intent.component = android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name)
            // ALL FOUR ARE GUARDED (2026-09-11). Android delivers every
            // ServiceConnection callback on the MAIN LOOPER, so an escape from any
            // of them is an uncaught exception on the main thread and therefore the
            // death of this process -- and a dead TTS engine process is a screen
            // reader with no voice. onServiceDisconnected is the worst of the four:
            // it is the path that unparks a waiting synthesis thread
            // (onEngineProcessGone).
            //
            // NONE OF THEM RESTORES ON A DEATH ANY MORE (2026-09-23). A death marks
            // the wrapper dead and waits; onServiceConnected -- the process is back
            // -- is where the one restore of that episode is made. See restoreEngine.
            val conn = object : android.content.ServiceConnection {
                override fun onServiceConnected(name: android.content.ComponentName?, service: android.os.IBinder?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Keep-alive bound to " + name?.flattenToShortString() }; if (service != null) engineServiceBinders[pkg] = service; onEngineProcessBack(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onServiceConnected: " + ex.toString()) } }
                override fun onServiceDisconnected(name: android.content.ComponentName?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Engine process died: " + name?.flattenToShortString() }; engineServiceBinders.remove(pkg); onEngineProcessGone(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onServiceDisconnected: " + ex.toString()) } }
                // A binding death is what a Play Store update or a force stop of
                // the engine produces. onEngineProcessGone takes the wrapper to -1
                // (state 2 over a dead process was the one state recovery could not
                // see, 2026-09-16), and the fresh binding reports the return. If
                // the fresh bind fails mid-update, packageReceiver reports it.
                override fun onBindingDied(name: android.content.ComponentName?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Binding died: " + name?.flattenToShortString() }; engineServiceBinders.remove(pkg); onEngineProcessGone(pkg); unbindEngineKeepAlive(pkg); bindEngineKeepAlive(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onBindingDied: " + ex.toString()) } }
                override fun onNullBinding(name: android.content.ComponentName?) { try { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Service returned null binding" }; unbindEngineKeepAlive(pkg) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onNullBinding: " + ex.toString()) } }
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
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "bindService failed for " + pkg }
            }
        } catch (_: Exception) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "bindService failed for " + pkg } }
        }
    }
    private fun unbindEngineKeepAlive(pkg: String) {
        synchronized(keepAliveLockFor(pkg)) {
            engineServiceBinders.remove(pkg)
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
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onGetLanguage" }
        val iso3 = localeIso3()
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -" + iso3 }
        return arrayOf(iso3)
    }
    override fun onIsLanguageAvailable(langCode: String?, countryCode: String?, variantName: String?): Int {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onIsLanguageAvailable: " + langCode + " " + countryCode + " " + variantName }
        // Our own engine client, driven by loadVoice, is bound back to us -- see
        // calledFromOwnClient. Refusing here is what stops setLanguage before it
        // queues another onLoadVoice, and what hands loadVoice its restore.
        if (calledFromOwnClient("onIsLanguageAvailable")) return TextToSpeech.LANG_NOT_SUPPORTED
        val raw = langCode ?: ""
        val result = if (LangStore.isAvailable(raw)) TextToSpeech.LANG_AVAILABLE else TextToSpeech.LANG_NOT_SUPPORTED
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -res: " + result }
        return result
    }
    override fun onLoadLanguage(langCode: String?, countryCode: String?, variantName: String?): Int =
        loadLanguage(langCode, countryCode, variantName, false) ?: TextToSpeech.LANG_NOT_SUPPORTED
    // onLoadLanguage's body. With logging OFF the list is walked ONCE for all it
    // needs (LangStore.loadPlan, owner 2026-10-06): availability, the target
    // voice, and -- for the prefetch (`needEngine`) -- whether the raw code has
    // an engine; null then means "no engine", decided before anything is loaded.
    // With logging on it is the old code, call for call, so the log lines and
    // their order stay what they were. tools/verify/langstore proves loadPlan.
    //
    // `stale` (owner, 2026-10-06): the prefetch passes "my utterance has ended".
    // It is asked AFTER the monitor is taken, because that is where a stale
    // prefetch used to lose the time: it waited for the lock behind some other
    // load, a swipe ended its utterance meanwhile, and Java monitors are not
    // fair -- it could win the lock over the NEW utterance's own load and then
    // make an engine's whole setLanguage (four binder calls) for a chunk that
    // will never be spoken, while the new utterance waited. Its result was
    // never used either way (loadOrPrefetched belongs to the utterance that
    // started it), so skipping it changes nothing that is heard.
    private fun loadLanguage(langCode: String?, countryCode: String?, variantName: String?, needEngine: Boolean,
                             stale: (() -> Boolean)? = null): Int? {
        awaitEngineStarting(langCode)
        return synchronized(this) {
            if (stale != null && stale()) return@synchronized null
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onLoadLanguage: " + langCode + " " + countryCode + " " + variantName }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "loadLanguage " + langCode + " " + countryCode + " " + variantName }
            val logging = EasyVoiceLogger.isLoggingEnabled()
            val plan = if (logging) null else LangStore.loadPlan(langCode ?: "",
                if (langCode.isNullOrEmpty()) "" else prefs.toIso3(langCode.lowercase()), modeInt)
            if (needEngine && plan != null && plan.rawEngine.isEmpty()) return@synchronized null
            val result = if (plan == null) onIsLanguageAvailable(langCode, countryCode, variantName)
                else if (calledFromOwnClient("onIsLanguageAvailable")) TextToSpeech.LANG_NOT_SUPPORTED
                else if (plan.available) TextToSpeech.LANG_AVAILABLE else TextToSpeech.LANG_NOT_SUPPORTED
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " isLanguageAvailable = " + result }
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
                var voiceLoc: String
                var enginePkg: String
                val variantOut: String
                if (plan == null) {
                    // The three walks that write AutoTTS's log lines, in their order.
                    voiceLoc = LangStore.localeFor(lang, modeInt)
                    enginePkg = LangStore.engineFor(lang, modeInt)
                    variantOut = if (variantOrEmpty.isEmpty()) LangStore.variantFor(lang, modeInt) else variantOrEmpty
                    if (enginePkg.isEmpty()) {
                        voiceLoc = LangStore.localeFor(autoIso3, modeInt)
                        enginePkg = LangStore.engineFor(autoIso3, modeInt)
                    }
                } else {
                    // Logging off: the same three answers, already in the plan
                    // (owner, 2026-10-01 / 2026-10-06). tools/verify/langstore proves it.
                    voiceLoc = plan.locale
                    enginePkg = plan.engine
                    variantOut = if (variantOrEmpty.isEmpty()) plan.variant else variantOrEmpty
                    if (enginePkg.isEmpty()) {
                        val auto = LangStore.voiceTargetFor(autoIso3, modeInt)
                        voiceLoc = auto[0]
                        enginePkg = auto[1]
                    }
                }
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "engine: " + enginePkg + " voice " + voiceLoc + " variant " + variantOut }
                try {
                    val loc = parseVoiceNameAsLocale(voiceLoc) ?: return@synchronized TextToSpeech.LANG_NOT_SUPPORTED
                    if (localeIso3(loc) == lang) {
                        loadVoice(enginePkg, loc, variantOut, dedicatedEnginesFlag)
                        if (lastEnginePkg != enginePkg) lastEnginePkg = enginePkg
                        if (voiceLoadFailed) switchToAlternative(lang, setOf(normPkg(enginePkg)), !walkPending(enginePkg), enginePkg + " could not load " + lang)
                    } else {
                        val langOnly = localeOf(lang)
                        val foundPkg = findEngineForLocale(langOnly)
                        loadVoice(foundPkg, langOnly, variantOut, dedicatedEnginesFlag)
                        if (voiceLoadFailed) switchToAlternative(lang, setOf(normPkg(foundPkg)), !walkPending(foundPkg), foundPkg + " could not load " + lang)
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
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onGetDefaultVoiceNameFor " + langCode + " " + countryCode + " " + variantName }
        if (calledFromOwnClient("onGetDefaultVoiceNameFor")) return ""
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -" + langCode }
        return langCode ?: ""
    }
    override fun onGetVoices(): MutableList<Voice> {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onGetVoices" }
        if (calledFromOwnClient("onGetVoices")) return mutableListOf()
        // Built once per language-list version (owner, 2026-10-01): a screen
        // reader's every setLanguage asks for the whole list, and it was rebuilt
        // -- one Voice and one Locale per language -- each time. The names come
        // only from iso3 and disabled, and LangStore.listVersion moves on every
        // write to either. A Voice is immutable; each caller gets its own list.
        val version = LangStore.listVersion.get()
        val cached = serviceVoices
        if (cached != null && cached.first == version) return ArrayList(cached.second)
        val names = LangStore.availableLanguagesFor(null, true)
        val list = mutableListOf<Voice>()
        for (nameIdx in names.indices) {
            list.add(Voice(names[nameIdx], localeOf(names[nameIdx]), Voice.QUALITY_HIGH, Voice.LATENCY_VERY_LOW, false, HashSet<String>()))
        }
        serviceVoices = Pair(version, ArrayList(list))
        return list
    }
    @Volatile private var serviceVoices: Pair<Long, List<Voice>>? = null
    override fun onIsValidVoiceName(name: String?): Int {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onIsValidVoiceName " + name }
        if (calledFromOwnClient("onIsValidVoiceName")) return TextToSpeech.ERROR
        val result = if (LangStore.isAvailable(name)) TextToSpeech.SUCCESS else TextToSpeech.ERROR
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -res " + result }
        return result
    }
    // THE VOICE LIST, SPLIT ONCE (owner, 2026-10-01). loadVoice, loadVoiceDedicated,
    // findEngineForLocale and switchToAlternative each walked voiceList -- every
    // voice of every engine, hundreds -- calling split("#") on every entry, and
    // findEngineForLocale walked it THREE times, building a Locale and its ISO
    // codes for every entry each time. That ran on the main looper between two
    // chunks. Each entry is now split once, and its Locale and ISO codes are
    // worked out the first time something asks, for as long as the list lasts.
    // A new list (persistVoiceList assigns one) or a grown one (loadVoiceList
    // appends to the empty list it starts from) is parsed again. The answers are
    // the ones split / parseVoiceNameAsLocale / localeIso3 / isO3Country give:
    // tools/verify/voicelist/run.sh.
    private inner class VoiceEntry(val raw: String) {
        val parts: List<String> = raw.split("#")
        val storedLocale: java.util.Locale? by lazy { parseVoiceNameAsLocale(parts[1]) }
        val storedIso3: String by lazy { storedLocale?.let { localeIso3(it) } ?: "" }
        val storedCountry: String by lazy { storedLocale?.let { try { it.isO3Country } catch (_: Exception) { "" } } ?: "" }
    }
    @Volatile private var parsedVoices: Pair<ArrayList<String>, List<VoiceEntry>>? = null
    private fun voiceEntries(): List<VoiceEntry> {
        val list = voiceList
        val cached = parsedVoices
        if (cached != null && cached.first === list && cached.second.size == list.size) return cached.second
        val parsed = ArrayList<VoiceEntry>(list.size)
        for (index in 0 until list.size) parsed.add(VoiceEntry(list[index]))
        parsedVoices = Pair(list, parsed)
        return parsed
    }
    // Answers cached by the string (owner, 2026-10-06): every language switch
    // parsed the same few stored locale tags again -- a split and a new Locale
    // each -- and loadVoiceDedicated parsed one per list entry. A Locale is
    // immutable and compared by value, so handing back the same object is the
    // same answer; NO_LOCALE stands in for null. Capped like toIso3's cache.
    private fun parseVoiceNameAsLocale(voiceName: String?): java.util.Locale? {
        if (voiceName == null) return null
        val cached = parsedLocales[voiceName]
        if (cached != null) return if (cached === NO_LOCALE) null else cached
        val parsed = parseVoiceNameAsLocaleUncached(voiceName)
        if (parsedLocales.size < 256) parsedLocales[voiceName] = parsed ?: NO_LOCALE
        return parsed
    }
    private val parsedLocales = java.util.concurrent.ConcurrentHashMap<String, java.util.Locale>()
    private val NO_LOCALE: java.util.Locale = localeOf("x-none")
    private fun parseVoiceNameAsLocaleUncached(voiceName: String): java.util.Locale? {
        return try {
            val parts = voiceName.split("_")
            when (parts.size) {
                1 -> localeOf(parts[0])
                2 -> localeOf(parts[0], parts[1])
                3 -> if (parts[2].isEmpty()) localeOf(parts[0], parts[1]) else localeOf(parts[0], parts[1], parts[2])
                else -> null
            }
        } catch (_: Exception) { null }
    }
    // A TTS THAT STOPS WORKING HANDS ITS LANGUAGE TO ANOTHER ONE, AT THAT MOMENT
    // (owner, 2026-09-23: "achanak se vahan per kuchh TTS ki awaaz nahin a rahi
    // hai ... Google TTS nahin chal raha hai to automatically scan karen aur use
    // language ke liye jo bhi TTS available ho pahle ... vah setup ho jana
    // chahie usi time per ... aur settings bhi update ho jaani chahie"). DELIBERATE
    // DEPARTURE -- AutoTTS stays on the configured engine and says nothing.
    //
    // Called from two places, both at the moment of failure: onLoadLanguage, when
    // its load could not put the language on its engine (no live client, or
    // setLanguage failed -- including "no voice data"), and the speak path, when
    // the engine fails the chunk it was given (onError, speak() failing, its
    // process dying, its client re-bound elsewhere). The speak path then says the
    // same chunk again on the engine chosen here.
    //
    // THE SCAN, DONE THEN AND THERE: first the voices the last full scan saved
    // (voice_N, in scan order -- "jo pahle mil jaaye scanning mein"), then every
    // engine that is live right now, asked directly with isLanguageAvailable. An
    // engine that is not live is asked to come back and skipped for this chunk.
    // Easy Voice itself and every engine already failed for this chunk are
    // skipped, so the search always ends.
    //
    // THE SETTING IS UPDATED AT ONCE (setUpAlternative) -- except in Google mode,
    // whose engine is fixed, and while the startup walk has simply not reached
    // the configured engine yet, which is not a failure.
    private fun normPkg(pkg: String): String = pkg.replace("-", "").replace("_", "")
    private fun walkPending(rawPkg: String): Boolean {
        if (initializingIndex >= walkList.size) return false
        val norm = normPkg(rawPkg)
        for (index in 0 until enginePool.size) {
            val wrapper = enginePool[index]
            if (wrapper.pkg == norm) return wrapper.state == 0
        }
        return true
    }
    private fun switchToAlternative(lang: String, excluded: Set<String>, setUp: Boolean, why: String): String? {
        if (!backupFor(lang)) return null
        try {
            val tried = HashSet<String>(excluded)
            val target = findBackupEngine(lang, tried) ?: run {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, why + " -- no other working engine speaks " + lang)
                return null
            }
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, why + " -- speaking " + lang + " with " + target.first + " " + target.second)
            if (setUp && modeInt != 3) setUpAlternative(lang, target.first, target.second)
            return target.first
        } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "switchToAlternative: " + ex.toString())
        }
        return null
    }
    // THE BACKUP SEARCH, REWRITTEN (owner, 2026-10-06: "purana delete karke
    // bilkul nai tarike se ... acche se likhna"). One question per engine, in a
    // fixed order, and the language LOADED on the first engine that can take it
    // -- loadVoice is the only thing that proves an engine can speak it now.
    // `tried` grows with every engine asked, so nothing is asked twice and the
    // search always ends. Answers the engine and the locale it was loaded with.
    //
    //   1. the voices the last full scan saved, in scan order -- the user's own
    //      engines first, with the locale the scan found for the language;
    //   2. every engine that is live right now, asked isLanguageAvailable;
    //   3. installed TTS services that are not live are brought up (bound and
    //      restored) for the NEXT chunk: a bind is asynchronous, so this chunk
    //      cannot wait for one, and the next finds it live in step 2.
    //
    // It is loaded at "*Default", the variant setUpAlternative stores, so the
    // backup speaks this chunk with the voice the next utterance will get too.
    // (It used to pass "", which loadVoice treats as "keep the voice this engine
    // already has" -- another language's named voice could be kept.)
    private fun findBackupEngine(lang: String, tried: HashSet<String>): Pair<String, Locale>? {
        fun usable(rawPkg: String): Boolean {
            if (rawPkg.isEmpty() || tried.contains(normPkg(rawPkg))) return false
            return rawPkg != packageName && !EngineFinder.isSelfEngine(rawPkg)
        }
        fun live(rawPkg: String): EngineWrapper? {
            val norm = normPkg(rawPkg)
            for (index in 0 until enginePool.size) {
                val wrapper = enginePool[index]
                if (wrapper.pkg != norm) continue
                val client = wrapper.tts ?: return null
                if (wrapper.state != 2) return null
                if (!isSameEnginePkg(EngineFinder.boundEngineOf(client, wrapper.rawPkg), wrapper.rawPkg)) return null
                return wrapper
            }
            return null
        }
        fun load(rawPkg: String, loc: Locale): Pair<String, Locale>? {
            tried.add(normPkg(rawPkg))
            loadVoice(rawPkg, loc, "*Default", false)
            return if (voiceLoadFailed || engineIndex < 0) null else Pair(rawPkg, loc)
        }
        // 1. the last full scan
        for (voice in voiceEntries()) {
            val rawPkg = voice.parts[0]
            if (voice.parts.size < 2 || !usable(rawPkg)) continue
            val loc = voice.storedLocale ?: continue
            if (localeIso3(loc) != lang) continue
            if (live(rawPkg) == null) { tried.add(normPkg(rawPkg)); recoverEngineNotReady(rawPkg); continue }
            load(rawPkg, loc)?.let { return it }
        }
        // 2. every live engine, asked now
        val wanted = localeOf(lang)
        for (wrapper in ArrayList(enginePool)) {
            if (!usable(wrapper.rawPkg)) continue
            val client = live(wrapper.rawPkg)?.tts ?: continue
            val answer = try { client.isLanguageAvailable(wanted) } catch (_: Exception) { TextToSpeech.LANG_NOT_SUPPORTED }
            if (answer < TextToSpeech.LANG_AVAILABLE) { tried.add(wrapper.pkg); continue }
            load(wrapper.rawPkg, wanted)?.let { return it }
        }
        // 3. engines on the phone that are not up, for the next chunk
        val started = ArrayList<String>()
        for (info in EngineFinder.installedEngines(this, withLabels = false)) {
            if (!usable(info.pkg) || live(info.pkg) != null) continue
            recoverEngineNotReady(info.pkg)
            started.add(info.pkg)
        }
        if (started.isNotEmpty()) EasyVoiceLogger.error(EasyVoiceLogger.TAG,
            "bringing up " + started.joinToString(", ") + " to look for " + lang + " on the next chunk")
        return null
    }
    // The same write the Voice setup screen makes when a voice is picked there:
    // "<iso3>" = "<pkg>#<locale>", and the variant back to the engine's default,
    // because a variant name belongs to the engine it came from. The in-memory
    // entry is updated under the list monitor (this -> languages, the order
    // onLoadLanguage already uses), so the very next utterance routes to it.
    private fun setUpAlternative(lang: String, pkg: String, loc: Locale) {
        synchronized(LangStore.languages) {
            for (entry in LangStore.languages) {
                if (entry.iso3 != lang) continue
                entry.enginePkg = pkg
                entry.localeTag = loc.toString()
                entry.variant = "*Default"
            }
        }
        val stored = pkg + "#" + loc.toString()
        LangStore.writeLater(this) {
            it[stringPreferencesKey(lang)] = stored
            it[stringPreferencesKey(lang + "_variant")] = "*Default"
        }
        EasyVoiceLogger.error(EasyVoiceLogger.TAG, lang + " is now set up on " + pkg + " " + loc)
    }
    // THE ENGINE-GONE FALLBACK WAS HERE AND IS REMOVED (owner, 2026-09-17:
    // "vah sahi tarike se hua nahin hai ... to usko code hata dena, uski jarurat
    // nahin hai").
    //
    // It was `engineIsLive(pkg)` plus `findLiveEngineForLocale(locale)`, called
    // from onLoadLanguage: when the configured engine was not in the pool at
    // state 2, it searched voiceList for another LIVE engine speaking the same
    // language and silently used that instead. It was a DELIBERATE DEPARTURE --
    // AutoTTS's `d0` falls back only when the engine string is EMPTY, never when
    // it names something absent -- and it did not do what the owner asked for on
    // their device, so the departure is withdrawn and this path is AutoTTS's
    // again.
    //
    // DO NOT REINSTATE IT WITHOUT THE OWNER ASKING. The gap it aimed at is real
    // and is written down so the next session does not rediscover it as a bug: a
    // LangStore entry keeps the package it was configured with, so if that engine
    // is uninstalled or mid-update, `engineFor` still answers it, `loadVoice`
    // finds no wrapper at state 2, `engineIndex` goes to -1 and the utterance
    // ends through `releaseWaitWithoutSpeaking`. That language stays silent until
    // the engine is back or the configuration changes. THAT IS AutoTTS'S
    // BEHAVIOUR TOO. Repairing it properly is a STORAGE change -- the stored
    // entry re-pointed and persisted, which is what a fresh engine scan already
    // does -- not a substitution made silently on the speaking path, which is
    // what this was and is why it did not hold up.
    private fun findEngineForLocale(locale: java.util.Locale): String {
        val requestedIso3 = localeIso3(locale)
        val requestedCountry = try { locale.isO3Country } catch (_: Exception) { "" }
        val requestedVariant = locale.variant
        val voices = voiceEntries()
        for (voice in voices) {
            val parts = voice.parts
            if (parts.size != 2 && parts.size != 3) continue
            val storedLocale = voice.storedLocale ?: return ""
            if (requestedIso3 == voice.storedIso3 && requestedCountry == voice.storedCountry && requestedVariant == storedLocale.variant) return parts[0]
        }
        for (voice in voices) {
            val parts = voice.parts
            if (parts.size != 2 && parts.size != 3) continue
            voice.storedLocale ?: return ""
            if (requestedIso3 == voice.storedIso3 && requestedCountry == voice.storedCountry) return parts[0]
        }
        for (voice in voices) {
            val parts = voice.parts
            if (parts.size != 2 && parts.size != 3) continue
            voice.storedLocale ?: return ""
            if (requestedIso3 == voice.storedIso3) return parts[0]
        }
        return ""
    }
    override fun onLoadVoice(name: String?): Int {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onLoadVoice " + name }
        lastLoadedVoiceName = name ?: ""
        val locale = parseVoiceNameAsLocale(name)
        if (locale == null) { EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -error" }; return TextToSpeech.ERROR }
        val iso3Lang = localeIso3(locale)
        val iso3Country = try { locale.isO3Country } catch (_: Exception) { "" }
        val loadRes = onLoadLanguage(iso3Lang, iso3Country, locale.variant)
        if (loadRes != TextToSpeech.LANG_AVAILABLE &&
            loadRes != TextToSpeech.LANG_COUNTRY_AVAILABLE &&
            loadRes != TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -error" }; return TextToSpeech.ERROR
        }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -success" }
        return TextToSpeech.SUCCESS
    }
    override fun onStop() {
        // onStop logs, then calls q0(TRUE), which logs and only then clears R.
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onStop calling!!!" }
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "stopAllTts " + true }
        // Read ONCE, before the walk below, so every engine in the pool is stamped
        // with the same answer even if a new utterance starts part-way through it.
        val stoppingGeneration = synthesisGeneration
        // ONLY THE ENGINE THAT IS SPEAKING. Read at the top, once, so the walk
        // below cannot see it change part-way through.
        //
        // Safe to read HERE specifically: AOSP runs stopForApp -> current.stop() ->
        // stopImpl() -> this override SYNCHRONOUSLY on the binder thread, before the
        // next item's runnable is posted, so the utterance that was speaking is
        // still the current one and speakingPkg still names its engine.
        val speakingNow = speakingPkg
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
            // See the identical note on the empty-text flush: this walked EVERY
            // engine and stopped all of them, on every interrupt, when exactly one
            // was producing audio. speakingPkg is assigned immediately BEFORE
            // tts.speak() so there is no window where an engine is speaking and
            // this is empty -- which is what made isSpeaking() wrong and makes this
            // right. When nothing recorded an engine the walk is exactly what it
            // was, so this can only do LESS work, never miss a stop the old code
            // would have made.
            if (speakingNow != null && wrapper.pkg != speakingNow) { index++; continue }
            if (wrapper.state == 2 && wrapper.listenerSet) {
                try {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " - calling stop for " + wrapper.pkg }
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "stop " + utteranceId }
                    // STAMPED WITH THE GENERATION THIS STOP IS FOR. Read the note on
                    // EngineWrapper.stop: the queue is one thread, so without this a
                    // stop ordered here can run after the reader's NEXT utterance has
                    // started, and cut that one off instead of the one we meant.
                    wrapper.stop(stoppingGeneration)
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
    // plainText is input.toString(), which the caller already made: with locale
    // spans off the chunk is that string, not a second copy (owner, 2026-10-01).
    private fun splitByLocaleSpans(input: CharSequence, plainText: String): MutableList<TextChunk> {
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
        result.add(TextChunk(plainText, "UNKNOWN"))
        return result
    }

    // ==========================================================================
    //  DETECTION                   AutoTTS clsCLD2.d / clsCLD2.e / clsCLD2.f
    //  detectLanguage is the windowed detect, forEachRun the per-span reader,
    //  detectLanguageAggregate the most-text-wins fallback. They are hinted
    //  DIFFERENTLY in the native half -- see docs/AUTOTTS_MAP.md.
    // ==========================================================================
    // Auto and Google mode: clsCLD2.d's windowed answer and, when that is
    // unknown, clsCLD2.f's (5.7.7.26's third step). ONE crossing does both
    // (owner, 2026-10-01): detectWindowOrSpans answers the windowed result and,
    // only when it is unknown, the fold and spans clsCLD2.f weighs up -- the
    // same thing nativeFoldAndDetect would have answered for this text. The
    // two early answers (empty text, a lone character under quick read) are
    // clsCLD2.d's own, made here without the core, exactly as before.
    private fun detectWindowThenAggregate(text: String, latinFallback: String, nonLatinFallback: String): String {
        if (text.isEmpty()) return detectLanguageAggregate(text)
        val quick = quickCharacterFlag
        if (text.length == 1 && quick) return detectLanguageAggregate(text)
        try {
            initIsoMaps()
            val out = detectWindowOrSpans(text, latinFallback, nonLatinFallback, disableAdvancedFlag,
                EasyVoiceLogger.isLoggingEnabled(), quick)
            val head = out[0]
            val markerIdx = head.indexOf('\u0001')
            var lang = head
            if (markerIdx >= 0) {
                if (markerIdx + 1 < head.length) for (line in head.substring(markerIdx + 1).split('\n')) if (line.isNotEmpty()) EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { line }
                lang = head.substring(0, markerIdx)
            }
            if (!lang.equals("unknown", ignoreCase = true)) return lang
            if (out.size >= 2) return aggregateOf(out.asList(), 1, quick)
        } catch (_: Throwable) {  }
        return detectLanguageAggregate(text)
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
            if (rebuiltEngineList.isEmpty() && EngineFinder.builtInEngine.isNotEmpty()) {
                rebuiltEngineList.add(EngineFinder.builtInEngine)
            }
            engineList = rebuiltEngineList
        }
    }
    private fun loadVoiceList() {
        synchronized(this) {
            if (!voiceList.isEmpty()) return
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "LoadVoices" }
            val sharedPrefs = LangStore.prefs(this)
            var index = 0
            while (true) {
                val voice = sharedPrefs[stringPreferencesKey("voice_$index")] ?: ""
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " -" + voice }
                if (voice.isEmpty()) {
                    dedicatedEnginesFlag = sharedPrefs[booleanPreferencesKey("dedicated_engines")] ?: false
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
    // processDirect's answer, one record at a time: each record is the number
    // of fields that follow, then the fields (type, kind, lang, text and, for a
    // detected chunk, its fold and spans). A count that runs past the end ends
    // the walk rather than reading what is not there.
    // READ IN PLACE (owner, 2026-10-07, item 5): `block` gets the record's
    // first field and its field count, so no list, view or run object is made
    // per record or per span -- the answer is walked by index, once.
    private inline fun forEachRecord(flat: Array<String>, block: (at: Int, n: Int) -> Unit) {
        var at = 0
        while (at < flat.size) {
            val n = fieldInt(flat[at]) ?: break
            if (n < 0 || at + 1 + n > flat.size) break
            block(at + 1, n)
            at += 1 + n
        }
    }
    // toIntOrNull, with the one-digit case (every type, most counts) answered
    // without a parse. Same answer for every string.
    private fun fieldInt(s: String): Int? =
        if (s.length == 1 && s[0] in '0'..'9') s[0] - '0' else s.toIntOrNull()
    // The runs of a fold-and-detect answer: flat[first] is the folded text,
    // lang / latin / text triples follow up to `end`. One reader for both
    // carriers -- nativeFoldAndDetect's array and a processDirect record -- so
    // the two cannot read the same answer two ways.
    // `sameAs` (processDirect records only, owner 2026-10-01): the core sends
    // the fold, and a span covering the whole fold, as "" there, because both
    // are the record's own text; a real fold or span text is never empty.
    private inline fun forEachRun(flat: Array<String>, first: Int, end: Int, quick: Boolean, sameAs: String?,
                                  block: (lang: String, latin: Boolean, text: String) -> Unit) {
        val folded = if (sameAs != null && flat[first].isEmpty()) sameAs else flat[first]
        // clsCLD2.e folds first and only then measures the length. A lone
        // maths-bold letter is two UTF-16 units, so testing the raw text would
        // miss the single-character case that folding creates.
        if (quick && folded.length == 1) {
            block("un", isLatinCommonInherited(folded.codePointAt(0)), folded)
            return
        }
        var index = first + 1
        while (index + 2 < end) {
            val spanText = flat[index + 2]
            block(flat[index], flat[index + 1] == "1", if (sameAs != null && spanText.isEmpty()) folded else spanText)
            index += 3
        }
    }
    // A processDirect record of type 0, 1 or 2 at flat[at], n fields: type /
    // kind / lang / text, then (owner, 2026-10-01, one crossing per span) the
    // chunk's fold and spans, exactly what nativeFoldAndDetect would answer for
    // that text. A record without them -- the empty chunk, which the core
    // skips -- is detected here as before: an empty text is one "un" span
    // without the core, anything else one trip into it (fold and spans together).
    private inline fun forEachDetectedRun(flat: Array<String>, at: Int, n: Int, quick: Boolean,
                                          block: (lang: String, latin: Boolean, text: String) -> Unit) {
        val text = flat[at + 3]
        if (n < 5 || text.isEmpty()) {
            if (text.isEmpty()) { block("un", false, ""); return }
            val answer = foldAndDetect(text)
            forEachRun(answer, 0, answer.size, quickCharacterFlag, null, block)
            return
        }
        forEachRun(flat, at + 4, at + n, quick, text, block)
    }
    // AutoTTS 5.7.7.26 clsCLD2.f. Ask for every span the detector can see, add up
    // how much text each language covers, and take the biggest -- except that a
    // real language beats "un" even when "un" covers more, which is what the two
    // separate best-so-far candidates below are for.
    private fun detectLanguageAggregate(text: String): String {
        if (text.isEmpty()) return "un"
        return aggregateOf(foldAndDetect(text).asList(), 0, quickCharacterFlag)
    }
    // clsCLD2.f's weighing, over a fold-and-detect answer whose element `first`
    // is the folded text -- nativeFoldAndDetect's array, or the tail of
    // detectWindowOrSpans's.
    private fun aggregateOf(flat: List<String>, first: Int, quick: Boolean): String {
        if (quick && flat[first].length == 1) return "un"
        val size = flat.size - first
        if (size < 4) return "un"
        // One triple means one span, so there is nothing to weigh up.
        if (size == 4) return flat[first + 1]
        // A plain HashMap, as clsCLD2.f uses: both best-so-far scans below
        // replace only on a strict >, so an exact tie is settled by whichever
        // entry entrySet() yields first, and that is HashMap's bucket order --
        // not insertion order. Kotlin's HashMap is java.util.HashMap, so the
        // same keys inserted in the same order iterate in the same sequence.
        val totals = HashMap<String, Int>()
        var index = first + 1
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
    // nativeFoldAndDetect: element 0 is the folded text, the triples follow.
    // It is the only route to the span detector (owner, 2026-09-30: no second
    // path). If it throws, the text goes on unfolded as ONE "un" span, so the
    // caller reads it in the preferred language (Latin when its first code
    // point is Latin/Common/Inherited, as the detector's script-0 rule does).
    // Answering the folded text alone -- the first version -- gave the mix and
    // multilingual loops no span at all, and that chunk was never spoken.
    private fun foldAndDetect(text: String): Array<String> =
        try { nativeFoldAndDetect(text, quickCharacterFlag) } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "nativeFoldAndDetect failed: $ex")
            if (text.isEmpty()) arrayOf(text)
            else arrayOf(text, "un", if (isLatinCommonInherited(text.codePointAt(0))) "1" else "0", text)
        }
    // clsCLD2.g: Script is Latin, Common or Inherited. From the app's own
    // generated table (UnicodeTables, owner 2026-10-06) rather than
    // Character.UnicodeScript, whose answer is whatever Unicode the phone's
    // Android carries -- a character newer than that was "Unknown" there.
    private fun isLatinCommonInherited(codePoint: Int): Boolean = UnicodeTables.isLatinCommonInherited(codePoint)

    // A detected run's final iso3, as the mix and multilingual loops add it:
    // prefs.toIso3(languageForDetectedRun(...)). The answer depends only on the
    // run's language and latin flag within one utterance, so with a `memo` it is
    // worked out once per (language, latin) instead of once per run -- each
    // time an ISO conversion and a walk of the language list (owner,
    // 2026-10-01). While logging is on the callers pass no memo, so the log
    // keeps every getEngine4Language block it had. One map per latin flag
    // (owner, 2026-10-07): the key is the language itself, no string is joined.
    private class RunLangMemo { val latin = HashMap<String, String>(); val nonLatin = HashMap<String, String>() }
    private fun detectedLangOf(lang: String, latin: Boolean, latinFallback: String, nonLatinFallback: String, memo: RunLangMemo?): String {
        if (memo == null) return prefs.toIso3(languageForDetectedRun(lang, latin, latinFallback, nonLatinFallback))
        return (if (latin) memo.latin else memo.nonLatin).getOrPut(lang) {
            prefs.toIso3(languageForDetectedRun(lang, latin, latinFallback, nonLatinFallback))
        }
    }
    private fun languageForDetectedRun(lang: String, latin: Boolean, latinFallback: String, nonLatinFallback: String): String {
        val byScript = if (latin) latinFallback else nonLatinFallback
        var resolved = IsoCodes.toIso3(lang) ?: byScript
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
        backupLangs = LangStore.loadBackupLanguages(applicationContext)
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
            when (iso3) { "cmn", "lzh", "gan", "hak" -> "zho"; else -> iso3 }
        } catch (_: Exception) { "zxx" }
    }
    // localeMatches against the client's params: p = the current voice's ISO3
    // language, ISO3 country and variant, which AOSP computed from that voice's
    // locale with the same getISO3Language / getISO3Country localeMatches calls
    // ("" where those threw -- and a candidate that throws answers false there,
    // as "" never equals a requested code here).
    private fun localeMatchesParams(requested: java.util.Locale, p: Array<String>): Boolean {
        return try {
            if (requested.isO3Language != p[0]) return false
            val matchCountry = requested.isO3Country
            if (matchCountry.isEmpty()) return true
            if (matchCountry != p[1]) return false
            val matchVariant = requested.variant
            if (matchVariant.isEmpty()) return true
            matchVariant == p[2]
        } catch (_: Exception) { false }
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
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "languages missing at point of use - reloading" }
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
        mayAwaitEngine.set(true)
        // An utterance is in flight: a background scan does not read an engine
        // now (ScanYield). Its end, below, is the event that lets it go on.
        synthesizingNow = true
        boostSynthesisThread()
        try { nativeRewarmTables() } catch (_: Throwable) {}
        try {
            onSynthesizeTextImpl(request, callback)
        } catch (ex: Throwable) {
            EasyVoiceLogger.errorWithStack(EasyVoiceLogger.TAG, "onSynthesizeText failed: " + ex.toString(), ex)
            speakingPkg = null
            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
            try { startAndFinish(callback) } catch (_: Throwable) {}
        } finally {
            mayAwaitEngine.set(false)
            synthesizingNow = false
            if (ScanYield.hasParked()) onMainThread { ScanYield.speechEnded() }
        }
    }
    // ==========================================================================
    //  THE SCHEDULER IS TOLD WHICH THREADS THE SCREEN READER IS WAITING ON
    //  (owner, 2026-10-07: "Karnal level per ... bahut jyada hi fast").
    //
    //  1. AOSP builds the synthesis thread at THREAD_PRIORITY_DEFAULT
    //     (TextToSpeechService.SynthThread: super(SYNTH_THREAD_NAME,
    //     Process.THREAD_PRIORITY_DEFAULT)), and onSynthesizeText runs on it --
    //     segmentation, detection, the language load and the first speak(). It is
    //     raised to THREAD_PRIORITY_AUDIO, the priority the speak thread already
    //     has, once, the first time this thread enters (the thread lives as long
    //     as the service). A phone busy with something else no longer runs that
    //     work after its own.
    //
    //  2. Android 12+: an ADPF hint session (PerformanceHintManager) for the
    //     synthesis and speak threads. Each utterance reports how long its work
    //     took, from entering onSynthesizeText to the first speak() accepted; the
    //     kernel's scheduler raises those threads' CPU floor (uclamp) when that
    //     is over the target and lowers it again when it is under. The target is
    //     the session's required parameter, not a wait: nothing here waits.
    //     A device that does not support it answers null and nothing changes.
    // ==========================================================================
    private val synthesisThreadBoosted = ThreadLocal<Boolean>()
    @Volatile private var hintSession: Any? = null          // PerformanceHintManager.Session, API 31+
    private var hintSessionTried = false
    private fun boostSynthesisThread() {
        if (synthesisThreadBoosted.get() == true) return
        synthesisThreadBoosted.set(true)
        try { android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO) } catch (ex: Throwable) {
            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "synthesis thread priority: " + ex.toString())
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S && !hintSessionTried) {
            hintSessionTried = true
            try { hintSession = createHintSession31(intArrayOf(android.os.Process.myTid(), speakThread.threadId)) } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "hint session: " + ex.toString())
            }
        }
    }
    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.S)
    private fun createHintSession31(threads: IntArray): Any? {
        val manager = getSystemService(android.os.PerformanceHintManager::class.java) ?: return null
        return manager.createHintSession(threads, HINT_TARGET_NANOS)
    }
    // How long this utterance's work took, up to the first speak() accepted.
    private fun reportSynthesisWork(nanos: Long) {
        val session = hintSession ?: return
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S) return
        try { reportWork31(session, nanos) } catch (_: Throwable) {}
    }
    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.S)
    private fun reportWork31(session: Any, nanos: Long) {
        if (nanos > 0) (session as android.os.PerformanceHintManager.Session).reportActualWorkDuration(nanos)
    }
    private fun onSynthesizeTextImpl(request: SynthesisRequest?, callback: SynthesisCallback?) {
        val workStart = System.nanoTime()
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "\n-------------------------------\nonSynthesizeText" }
        reloadLanguagesIfMissing()
        if (showNotificationFlag && !isForegroundActive()) {
            if (!foregroundRefused) startForegroundIfPossible()
        } else if (!showNotificationFlag && foregroundStarted) {
            // The flag, not isForegroundActive(): with the switch OFF (the
            // default) that asked the notification service over binder on EVERY
            // utterance (owner, 2026-09-30: "bulane mein der"). Only this service
            // starts its foreground state and a new instance starts without it,
            // so the flag alone is exact.
            // ServiceCompat.stopForeground with STOP_FOREGROUND_REMOVE, the value
            // 1 this once passed by hand.
            stopForegroundNow()
        }
        synchronized(syncLock) { isStopped = false; isFlushed = false; syncLock.notifyAll() }
        // SNAPSHOT BEFORE THE CLEAR, because the empty-text flush below needs to
        // know which engine was speaking and this line is what destroys that.
        // TalkBack interrupts with an EMPTY utterance, so that branch runs with
        // speakingPkg already null and had no way to tell one engine from another
        // -- which is why it walked the whole pool.
        val wasSpeakingPkg = speakingPkg
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
        utteranceId = (request?.params?.getString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID)).toString()
        val trimmedText = rawText.trim()
        if (trimmedText.isEmpty()) {
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Speak text is empty" }
            // q0 logs before it touches anything: "stopAllTts <flag>", then
            // removeCallbacks(u) -- which has no counterpart here, because the
            // speak runnable is no longer posted -- then R.clear().
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "stopAllTts " + false }
            synchronized(chunkQueue) { chunkQueue.clear() }
            // THE POOL IS WALKED HERE NOW, NOT enginePool[engineIndex] (2026-09-16).
            //
            // Two reasons, and the second is what made it necessary.
            //
            // It is what our own onStop() has always done -- `while (index <
            // enginePool.size)` over every wrapper in state 2 with listenerSet --
            // and the two exist for the same job: end whatever is speaking. Reading
            // one index was the weaker of the two and could already miss.
            //
            // And `engineIndex` now moves BEFORE the chunk it belongs to speaks,
            // because the next chunk's language is loaded while the current one is
            // still being read (see prefetchNextLanguage). Left as it was, a
            // TalkBack flush landing in that window would have flushed the engine
            // that has not started instead of the one speaking, and the previous
            // phrase would have carried on -- the exact defect the 2026-09-02 note
            // below describes, re-introduced by the other end.
            var flushIdx = 0
            while (flushIdx < enginePool.size) {
                val wrapper = enginePool[flushIdx]
                flushIdx++
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
                // ONLY THE ENGINE THAT WAS SPEAKING (owner, 2026-09-17: "screen
                // ko touch karne se stop hota hai vah to khud TalkBack ka function
                // rehta hai na, to aap kyon is tarike ka kam karte ho ... do teen
                // jagah pe aapne stop wala on stop wala laga rakha hai").
                //
                // This walked EVERY engine in the pool and flushed all of them, and
                // our onStop() override did the same a moment earlier, so one
                // explore-by-touch step cost roughly 2xN binder calls when exactly
                // ONE engine was producing audio. Explore-by-touch is a continuous
                // stream of those, and every extra queued stop is another chance
                // for one to run late and land on an utterance it does not own.
                //
                // wasSpeakingPkg is the right signal and `isSpeaking()` was not:
                // speakingPkg is OUR OWN state, assigned immediately BEFORE
                // tts.speak() at the speak site, so unlike a binder query into the
                // other app there is NO window where the engine is speaking and
                // this is empty. That window is the whole reason the 2026-09-02
                // departure dropped isSpeaking(), and this keeps that fix.
                //
                // It is also not `engineIndex`, which prefetchNextLanguage moves to
                // the NEXT chunk's engine while the current one still speaks --
                // that is the defect the pool walk was introduced to avoid.
                //
                // THE FALLBACK IS THE SAFETY NET: when nothing recorded an engine,
                // the walk is exactly what it was. So this can only ever do LESS
                // work, never miss something the old code would have caught.
                if (wasSpeakingPkg != null && wrapper.pkg != wasSpeakingPkg) { continue }
                if (wrapper.state == 2 && wrapper.listenerSet) {
                    // A client re-bound to Easy Voice (see speakChunk) must not be
                    // flushed: speak("") on it queues an EMPTY utterance for our
                    // own synthesis thread, which lands right back in this branch
                    // and flushes it again -- a loop with no audio in it at all.
                    // Skipped rather than restored here; the next speak or
                    // loadVoice on that engine restores it.
                    val flushClient = wrapper.tts
                    if (flushClient != null) {
                        val flushBound = EngineFinder.boundEngineOf(flushClient, wrapper.rawPkg)
                        if (!isSameEnginePkg(flushBound, wrapper.rawPkg)) {
                            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "not flushing " + wrapper.rawPkg + ": its client is bound to " + flushBound)
                            continue
                        }
                    }
                    try {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " - calling speak empty for " + wrapper.pkg }
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onSynthesizeText: " + utteranceId + " ''" }
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "speak " + utteranceId }
                        synchronized(wrapper.speakLock) {
                            wrapper.flushedGeneration = myGeneration
                            wrapper.tts?.speak("", TextToSpeech.QUEUE_FLUSH, null, null)
                        }
                    } catch (ex: Exception) {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Speaking failed for " + wrapper.pkg + "\n " + ex.message }
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
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Speak: " + trimmedText + " id: " + utteranceId }
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
                    requestedLang      = localeIso3(parseVoiceNameAsLocale(forcedLoc) ?: localeOf(""))
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "FIXED: " + forcedEng + " " + forcedLoc + " " + forcedVariant }
                }
                val stripped = splitParts[1]
                val baseLang: String = run {
                    val loc = forcedLoc
                    if (loc.isBlank()) normalizeLangCode((request?.language ?: "").ifBlank { latinFallback })
                    else try {
                        val localeParts = loc.split("_")
                        val locale: Locale = when (localeParts.size) {
                            1 -> localeOf(localeParts[0])
                            2 -> localeOf(localeParts[0], localeParts[1])
                            else -> if (localeParts[2].isNotEmpty()) localeOf(localeParts[0], localeParts[1], localeParts[2]) else localeOf(localeParts[0], localeParts[1])
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
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Mixed mode" }
                val localeChunks = splitByLocaleSpans(rawCharSeq, rawText)
                val neutralType = if (localeIso3() == prefs.toIso3(nonLatinFallback)) 2 else 1
                val neutralDefault = requestedLang.ifEmpty { latinFallback }
                val disableAdvancedDetection = disableAdvancedFlag
                val quick = quickCharacterFlag
                val autoModeF = prefs.toIso3(autoLang)
                val runLangMemo = if (EasyVoiceLogger.isLoggingEnabled()) null else RunLangMemo()
                // The three specific languages are the same for every span of
                // this utterance; converted once, not once per span.
                val numberSpecific = normalizeLangCode(numberSpecificLang)
                val puncSpecific = normalizeLangCode(puncSpecificLang)
                val emojiSpecific = normalizeLangCode(emojiSpecificLang)
                for (spanChunk in localeChunks) {
                    if (isFlushed) break
                    if (!spanChunk.lang.equals("unknown", ignoreCase = true) && spanChunk.lang != "") {
                        val resolved = if (hasEngineForLang(spanChunk.lang)) spanChunk.lang else autoModeF
                        chunks.add(TextChunk(spanChunk.text, resolved))
                    } else {
                        val chunkOutput = processDirect(
                            spanChunk.text,
                            latinFallback, nonLatinFallback, "mix",
                            numberModeInt, numberSpecific,
                            punctuationModeInt, puncSpecific,
                            emojiModeInt, emojiSpecific, punctuationInFlowFlag, smartNumberFlag,
                            smartNumberGroupSize,
                            neutralDefault, neutralType, disableAdvancedDetection, true, quick
                        )
                        forEachRecord(chunkOutput) { at, n ->
                            if (n >= 4) {
                                // AutoTTS branches on the type d0.t RESOLVED, never on what
                                // the text looks like. A type of 3, 4 or 5 survives only when
                                // that mode int is 3 ("Specific language"), and the caller
                                // then maps 3 -> K, 4 -> M, 5 -> O -- which is already the
                                // language the segmenter put in this chunk. Types 0, 1 and 2
                                // all go to clsCLD2.e, and modes 1 and 2 rely on that: the
                                // segment was re-typed so it MERGES with the Latin or the
                                // non-Latin run and is detected together with it.
                                val spanMixType = fieldInt(chunkOutput[at]) ?: 0
                                if (spanMixType == 3 || spanMixType == 4 || spanMixType == 5) {
                                    chunks.add(TextChunk(chunkOutput[at + 3], prefs.toIso3(normalizeLangCode(chunkOutput[at + 2]))))
                                } else {
                                    forEachDetectedRun(chunkOutput, at, n, quick) { lang, latin, text ->
                                        chunks.add(TextChunk(text, detectedLangOf(lang, latin, latinFallback, nonLatinFallback, runLangMemo)))
                                    }
                                }
                            }
                        }
                    }
                }
                if (chunks.isNotEmpty()) {
                    val firstLang = prefs.toIso3(chunks[0].lang)
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "language: " + firstLang }
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "engine: " + LangStore.engineFor(firstLang, modeInt) }
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
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Multilingual mode" }
                val localeChunks = splitByLocaleSpans(rawCharSeq, rawText)
                val neutralType = if (localeIso3() == prefs.toIso3(nonLatinFallback)) 2 else 1
                val neutralDefault = requestedLang.ifEmpty { latinFallback }
                val disableAdvancedDetection = disableAdvancedFlag
                val quick = quickCharacterFlag
                val runLangMemo = if (EasyVoiceLogger.isLoggingEnabled()) null else RunLangMemo()
                val numberSpecific = normalizeLangCode(numberSpecificLang)
                val puncSpecific = normalizeLangCode(puncSpecificLang)
                val emojiSpecific = normalizeLangCode(emojiSpecificLang)
                for (spanChunk in localeChunks) {
                    if (isFlushed) break
                    if (!spanChunk.lang.equals("unknown", ignoreCase = true) && spanChunk.lang.isNotEmpty() && hasEngineForLang(spanChunk.lang)) {
                        chunks.add(TextChunk(spanChunk.text, spanChunk.lang))
                    } else {
                        val chunkOutput = processDirect(
                            spanChunk.text,
                            latinFallback, nonLatinFallback, "mix",
                            numberModeInt, numberSpecific,
                            punctuationModeInt, puncSpecific,
                            emojiModeInt, emojiSpecific, punctuationInFlowFlag, smartNumberFlag,
                            smartNumberGroupSize,
                            neutralDefault, neutralType, disableAdvancedDetection, true, quick
                        )
                        forEachRecord(chunkOutput) { at, n ->
                            if (n < 4) return@forEachRecord
                            // Same branch as mixed above, and for the same reason.
                            val spanMixType = fieldInt(chunkOutput[at]) ?: 0
                            if (spanMixType == 3 || spanMixType == 4 || spanMixType == 5) {
                                chunks.add(TextChunk(chunkOutput[at + 3], prefs.toIso3(normalizeLangCode(chunkOutput[at + 2]))))
                            } else {
                                forEachDetectedRun(chunkOutput, at, n, quick) { lang, latin, text ->
                                    chunks.add(TextChunk(text, detectedLangOf(lang, latin, latinFallback, nonLatinFallback, runLangMemo)))
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
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Dual mode" }
                    val neutralType = if (localeIso3() == prefs.toIso3(dualLang)) 2 else 1
                    val neutralDefault = requestedLang.ifEmpty { latinFallback }
                    val disableAdvancedDetection = disableAdvancedFlag
                    val chunkOutput = processDirect(
                        trimmedText,
                        latinFallback, nonLatinFallback, "dual",
                        numberModeInt, normalizeLangCode(numberSpecificLang),
                        punctuationModeInt, normalizeLangCode(puncSpecificLang),
                        emojiModeInt, normalizeLangCode(emojiSpecificLang), punctuationInFlowFlag, smartNumberFlag,
                        smartNumberGroupSize,
                        neutralDefault, neutralType, disableAdvancedDetection, false, false
                    )
                    forEachRecord(chunkOutput) { at, n ->
                        if (n == 4) {
                            val resolved = normalizeLangCode(chunkOutput[at + 2])
                            chunks.add(TextChunk(chunkOutput[at + 3], resolved, typeCode = fieldInt(chunkOutput[at]) ?: 0))
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
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "language: " + preflight }
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
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Auto mode || Google mode" }
                val localeChunks = splitByLocaleSpans(rawCharSeq, rawText)
                for (spanChunk in localeChunks) {
                    if (isFlushed) break
                    val detected = if (spanChunk.lang.equals("unknown", ignoreCase = true)) {
                        // 5.7.7.26 adds a third step here: when the span carries no
                        // language of its own AND the main detector still says
                        // unknown, the aggregate detector gets a turn. Both are in
                        // detectWindowThenAggregate, in one crossing.
                        val rawDetected = detectWindowThenAggregate(spanChunk.text, latinFallback, nonLatinFallback)
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Cld2: " + rawDetected + " '" + spanChunk.text + "'" }
                        if (rawDetected.length > 2) rawDetected.substring(0, 2) else rawDetected
                    } else spanChunk.lang
                    var resolved = IsoCodes.toIso3(detected) ?: autoModeF
                    val engAuto = LangStore.engineFor(resolved, modeInt)
                    if (engAuto.isEmpty() || engAuto == "Disable") resolved = autoModeF
                    chunks.add(TextChunk(spanChunk.text, resolved))
                }
                if (chunks.isNotEmpty()) {
                    val firstLoad = onLoadLanguage(chunks[0].lang, "", "")
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "load " + firstLoad }
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
        if (forceGoogle && !bypassed) { chunks.forEach { it.forcedEngine = EngineFinder.builtInEngine } }
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
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "load " + firstLangLoad }
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
        //  speak thread, because the next step may create or shut down a TextToSpeech.
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
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "unlockSynthesis: " + why }
            startAndFinish(callback)
            synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
        }
        // THE NEXT CHUNK'S LANGUAGE IS LOADED WHILE THIS ONE IS STILL SPEAKING
        // (owner, 2026-09-16: "alag-alag bhashaen read karne ke liye jo TTS change
        // hote hain to vah time thoda lagata hai ... switching bahut fast honi
        // chahie").
        //
        // WHERE THE TIME WENT, read out of AOSP rather than guessed. The whole
        // chain after chunk N's audio stops was serial:
        //     onDone (binder) -> post to the main looper -> onLoadLanguage(N+1)
        //     -> loadVoice -> setLanguage/setVoice -> speak()
        // and `TextToSpeech.setLanguage` is FOUR blocking binder round trips:
        //     service.isLanguageAvailable(...)
        //     service.getDefaultVoiceNameFor(...)
        //     service.loadVoice(...)
        //     getVoice(service, voiceName)   <-- a FULL service.getVoices() marshal
        // The last one is the engine's entire voice set -- hundreds of Voice
        // objects for Google TTS -- and it exists only so getLanguage() reports the
        // right locale afterwards, which nothing in this app reads. It cannot be
        // removed from inside the framework, so the only lever is WHEN it runs.
        //
        // IT IS SAFE TO RUN IT EARLY, and that is AOSP's own contract rather than a
        // hope. TextToSpeechService.Stub.loadVoice and loadLanguage both enqueue at
        // **QUEUE_ADD**; only QUEUE_FLUSH calls stopForApp. And isLanguageAvailable
        // and getDefaultVoiceNameFor are plain binder methods answered off the
        // binder thread pool, never touching SynthHandler. So nothing on this path
        // can flush, stop or delay an utterance already playing -- on this engine or
        // any other.
        //
        // IT RUNS ON THE ENGINE THAT IS SPEAKING TOO (owner, 2026-10-01: Hindi and
        // Gujarati both on one engine paid the four calls after every switch). It
        // used to stop at a different engine, because loadVoice restored the
        // engine whenever setLanguage failed and a restore shuts the client down,
        // which would have cut the chunk it was playing. That is no longer so:
        // setLanguageFailed / setVoiceFailed KEEP a client that is connected and
        // bound to its own engine, and only clientIsDead shuts one down -- when
        // getVoices answers null (no connection) or the client was re-bound, i.e.
        // the engine is already gone and so is the chunk it was playing. And
        // speakChunk never speaks through a re-bound client, so "playing and
        // re-bound" cannot happen. Proof: tools/verify/prefetch/run.sh lifts the
        // whole loadVoice family and runs it in the old order and this one against
        // an AOSP-shaped engine: 200,000 scenarios x logging off/on, every chunk
        // spoken with the same engine, language and voice, none cut, every
        // wrapper / restore / recover the same. Negative tests: an engine that
        // applied a load to the playing utterance, and setLanguageFailed shutting
        // a live client down, are both caught.
        //
        // The result is REMEMBERED, not just the fact of loading, so onDone's three
        // branches keep every check they had -- including LANG_NOT_SUPPORTED and its
        // release of the parked thread. They ask `loadOrPrefetched`, which answers
        // from the prefetch only when it was for the same language.
        var prefetchedLang = ""
        var prefetchedResult = 0
        // A prefetch that ran while the engine was still starting found no
        // client (engineIndex -1). Its answer is not reused: the chunk loads
        // again, now able to wait for that engine, instead of being dropped.
        var prefetchedReady = false
        fun loadOrPrefetched(lang: String): Int {
            if (prefetchedLang == lang && prefetchedReady) { val cached = prefetchedResult; prefetchedLang = ""; return cached }
            prefetchedLang = ""
            mayAwaitEngine.set(true)
            try { return onLoadLanguage(lang, "", "") } finally { mayAwaitEngine.set(false) }
        }
        // Runs on the SPEAK THREAD, posted from onStart -- never on the binder thread
        // the callback arrives on, for the same reason onDone posts: this can
        // construct or shut down a TextToSpeech and that must not happen inside an
        // engine callback.
        fun prefetchNextLanguage() {
            if (myGeneration != synthesisGeneration) return
            if (isStopped || isFlushed) return
            if (prefetchedLang.isNotEmpty()) return
            val next = synchronized(chunkQueue) { chunkQueue.firstOrNull()?.second } ?: return
            // The same three branches onDone resolves the next language with, and
            // they must stay the same three: this only decides WHEN the load runs.
            val lang = if (modeInt == 1) when (next.typeCode) {
                1 -> "eng"
                2 -> dualLang
                3 -> numberSpecificLang
                4 -> puncSpecificLang
                5 -> emojiSpecificLang
                else -> ""
            } else next.iso3()
            if (lang.isEmpty()) return
            // Logging on: engineFor, then onLoadLanguage -- the old pair, log
            // lines included. Logging off: loadLanguage(needEngine = true) finds
            // "no engine" in the same walk the load uses and answers null
            // (owner, 2026-10-06: one walk, not two).
            val nextPkg = if (EasyVoiceLogger.isLoggingEnabled()) LangStore.engineFor(lang, modeInt) else null
            if (nextPkg != null && nextPkg.isEmpty()) return
            try {
                val stale = { myGeneration != synthesisGeneration || isStopped || isFlushed }
                prefetchedResult = (if (nextPkg != null) loadLanguage(lang, "", "", false, stale) else loadLanguage(lang, "", "", true, stale)) ?: return
                prefetchedReady = engineIndex >= 0
                prefetchedLang = lang
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "prefetch " + lang + " on " + nextPkg + " = " + prefetchedResult }
            } catch (ex: Throwable) {
                prefetchedLang = ""
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "prefetch failed for " + lang + ": " + ex.toString())
            }
        }
        // THE CHUNK BEING SPOKEN, KEPT SO A FAILING ENGINE CAN HAND IT OVER (owner,
        // 2026-09-23: "usi dauran ... turant usi kshan per"). When the engine the
        // chunk went to fails it -- onError, speak() failing, its process dying,
        // its client re-bound elsewhere, or no client at all -- the same chunk is
        // put back at the head of the queue and spoken again on the engine
        // switchToAlternative chooses, which also updates the setting. Every
        // engine that failed this utterance is excluded from the search, so it
        // always ends; the id gets a retry suffix, so a late callback from the
        // failed engine cannot match the new attempt.
        var currentPair: Pair<Int, TextChunk>? = null
        var currentPairFirst = false
        var currentPairLang = ""
        var retryCount = 0
        val failedThisUtterance = HashSet<String>()
        lateinit var speakChunkRef: (Boolean) -> Unit
        fun retryChunkElsewhere(failedRawPkg: String, why: String): Boolean {
            if (!backupFor(currentPairLang)) return false
            if (myGeneration != synthesisGeneration || isStopped || isFlushed) return false
            if (bypassed) return false
            val pair = currentPair ?: return false
            if (currentPairLang.isEmpty() || failedRawPkg.isEmpty()) return false
            failedThisUtterance.add(normPkg(failedRawPkg))
            synchronized(this) {
                switchToAlternative(currentPairLang, failedThisUtterance, !walkPending(failedRawPkg), why)
            } ?: return false
            // A prefetch is "the last load", and it is only reusable while it still
            // is: switchToAlternative has just loaded this chunk's language on
            // another engine and moved engineIndex there. Kept, the next chunk would
            // be spoken (or queued ahead) on the engine this retry chose, with the
            // language loaded on a different one. Dropped, the next chunk loads
            // again -- and the retry's own speak() starts that load at once.
            prefetchedLang = ""
            retryCount++
            synchronized(chunkQueue) { chunkQueue.add(0, pair) }
            speakChunkRef(currentPairFirst)
            return true
        }
        // ==========================================================================
        //  THE NEXT CHUNK, ALREADY IN THE ENGINE'S QUEUE (owner, 2026-10-01: "court
        //  mein jo donon chijen ... properli test ... simulator bhi aap banaa sakte
        //  hain ... complete karke kariye").
        //
        //  When the next chunk goes to the SAME engine that is speaking now, it is
        //  handed to that engine with QUEUE_ADD while this one plays, instead of
        //  after onDone. AOSP's TextToSpeechService runs a queued item's synthesis
        //  while the one before it is still playing (its playback queue holds about
        //  half a second of audio), so the engine's own time to first audio -- and
        //  our onDone -> post -> speak round trip -- is no longer a gap between the
        //  chunks. The request is built exactly as speakChunk would build it then:
        //  the client's rate and pitch live in its mParams, which speak() copies,
        //  so setting them for the next chunk does not touch the one playing.
        //
        //  It is done only when onDone would have spoken it with nothing in
        //  between: the language it needs is already loaded by the prefetch (with
        //  an answer that is not "not supported"), that load put engineIndex on the
        //  engine now speaking, and the client is live and bound to it. Anything
        //  else is left to onDone, as before.
        //
        //  EVERY WAY BACK:
        //  - onDone of the chunk in front: the queued one becomes the current one
        //    (promote) -- its listener alone on the client, as speakChunk leaves it.
        //  - the chunk in front FAILS (onError): the queued one is stopped at once
        //    and taken back (cancelAhead) before the error is handled, so the error
        //    path sees exactly the queue it saw before.
        //  - the engine DIES: the queued one died with it; taken back, then the
        //    retry as before.
        //  - the queued one fails before its turn (an engine can report an error
        //    without starting): held, and handled when the chunk in front is done.
        //  - an interrupt flushes the engine, which takes both.
        //  tools/verify/speakpipe/run.sh drives the real code against an
        //  AOSP-shaped engine and compares it with the code before this.
        // ==========================================================================
        class AheadChunk(val pair: Pair<Int, TextChunk>, val wrapper: EngineWrapper, val lang: String,
                         val savedCounter: Int, val savedPrefetchLang: String, val savedPrefetchResult: Int) {
            lateinit var listener: UtteranceProgressListener
            var handOver: (Int, String) -> Unit = { _, _ -> }
            @Volatile var takenBack = false
            private var promoted = false
            private var failure: Pair<Int, String>? = null
            @Synchronized fun deferFailure(code: Int, n: String): Boolean {
                if (promoted) return false
                failure = Pair(code, n); return true
            }
            @Synchronized fun takeTurn(): Pair<Int, String>? { promoted = true; return failure }
        }
        val ahead = java.util.concurrent.atomic.AtomicReference<AheadChunk?>(null)
        var currentListener: UtteranceProgressListener? = null
        lateinit var queueNextAheadRef: () -> Unit
        // Puts the queued chunk back where it was, as if it had never been queued:
        // at the head of chunkQueue, with the counter and the prefetch it used.
        fun cancelAhead(stopEngine: Boolean) {
            val queued = ahead.getAndSet(null) ?: return
            queued.takenBack = true
            if (stopEngine) {
                try { queued.wrapper.tts?.stop() } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "stop of the queued chunk: " + ex.toString()) }
            }
            try { queued.wrapper.tts?.setOnUtteranceProgressListener(currentListener) } catch (_: Exception) {}
            synchronized(chunkQueue) { chunkQueue.add(0, queued.pair) }
            chunkCounter = queued.savedCounter
            prefetchedLang = queued.savedPrefetchLang
            prefetchedResult = queued.savedPrefetchResult
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "queued chunk taken back: " + queued.pair.second.text }
        }
        fun promote(queued: AheadChunk) {
            if (!ahead.compareAndSet(queued, null)) return
            val failure = queued.takeTurn()
            try { queued.wrapper.tts?.setOnUtteranceProgressListener(queued.listener) } catch (_: Exception) {}
            currentPair = queued.pair
            currentPairFirst = false
            currentPairLang = queued.lang
            currentListener = queued.listener
            if (failure != null) queued.handOver(failure.first, failure.second)
        }
        // The engine's process died while this utterance held it. Run on the speak
        // thread with every other step of this utterance (they share currentPair,
        // the queue and the chunk queued ahead, none of which is locked); the
        // answer is therefore always "handled here", and when no other engine can
        // take the chunk the wait is released here, as onEngineProcessGone did.
        val retryOnDeath: (String) -> Boolean = { rawPkg ->
            speakHandler.post {
                if (myGeneration != synthesisGeneration) return@post
                val moved = try {
                    cancelAhead(false)
                    retryChunkElsewhere(rawPkg, rawPkg + " died while speaking")
                } catch (ex: Throwable) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "retry after engine death: " + ex.toString()); false
                }
                if (!moved) synchronized(syncLock) { isStopped = true; syncLock.notifyAll() }
            }
            true
        }
        retryOnEngineDeath = retryOnDeath
        // The bundle handed to speak(), the same for the chunk queued ahead.
        fun buildParams(wrapper: EngineWrapper, finalVolume: Float): android.os.Bundle {
            val params = android.os.Bundle(requestParams ?: android.os.Bundle())
            params.remove("pitch"); params.remove("rate")
            params.remove("language"); params.remove("country")
            params.remove("variant"); params.remove("voiceName"); params.remove(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID)
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
            // The keys: KEY_PARAM_STREAM, KEY_PARAM_UTTERANCE_ID and
            // KEY_PARAM_VOLUME are public in TextToSpeech.Engine and are used by
            // name. "pitch", "rate", "language", "country", "variant",
            // "voiceName" and "audioAttributes" are the SAME class's @hide keys --
            // not in the SDK (read from API 37's android.jar with javap) -- so
            // they stay as the literal strings AOSP itself defines them as.
            if (isStripAudioAttr || isForceAccessibility) { params.remove(TextToSpeech.Engine.KEY_PARAM_STREAM); params.remove("audioAttributes") }
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
                try { params.putParcelable("audioAttributes", AudioAttributes.Builder().setLegacyStreamType(params.getInt(TextToSpeech.Engine.KEY_PARAM_STREAM, TextToSpeech.Engine.DEFAULT_STREAM)).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()) } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) }
            }
            if (finalVolume != 0f) { params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, finalVolume) }
            return params
        }
        // ONE CHUNK'S LISTENER. It used to be built inline in speakChunk; it is a
        // function now because the chunk queued AHEAD (below) needs the very same
        // listener, built the very same way. `mine` is that ahead chunk, or null.
        fun makeListener(wrapper: EngineWrapper, expectedId: String, mine: AheadChunk?, lang: String): UtteranceProgressListener =
            object : UtteranceProgressListener() {
                init { mine?.handOver = { code, n -> handOver(code, n) } }
                override fun onStart(id: String) {
                    // The engine really spoke, so its episode is over and its
                    // one restore is handed back: an event that means exactly
                    // "it worked", instead of a "healthy for N seconds" clock.
                    // True whichever utterance it belongs to, so it is not gated.
                    try {
                        wrapper.restoreSpent = false
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onStart " + id }
                        if (id != expectedId) return
                        startCallback(callback)
                        // The engine has begun audio, so the whole of this chunk is
                        // free time to load the next one, on this engine or any
                        // other. Posted rather than run here: this is a binder callback.
                        // Then the next chunk, if it goes to this same engine, is handed
                        // to it now with QUEUE_ADD, so it starts the moment this one ends.
                        // Not for a queued chunk that was taken back: it started only
                        // because the chunk in front failed, and it is spoken again later.
                        //
                        // ONLY FOR A CHUNK QUEUED AHEAD now (owner, 2026-10-07). A chunk
                        // spoken the ordinary way starts this work the moment its
                        // speak() is accepted (speakChunk, below): from that instant the
                        // engine owns it, and waiting for its first audio only spent
                        // the engine's own start-up time doing nothing. A queued chunk
                        // cannot start it earlier -- until it is promoted, the chunk in
                        // front still holds the one prefetch slot.
                        if (mine != null) speakHandler.post {
                            if (mine.takenBack) return@post
                            prefetchNextLanguage(); queueNextAheadRef()
                        }
                    } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onStart failed: " + ex.toString()) }
                }
                override fun onDone(id: String) {
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onDone " + id }
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
                    // stopping here is the hang: the post below is what ends the
                    // utterance when the queue is empty and what starts the next
                    // chunk, so losing
                    // either leaves the synthesis thread parked with no callback
                    // left to come.
                    try {
                    // THE LAST CHUNK ENDS HERE, ON THE BINDER THREAD (owner,
                    // 2026-10-01). With nothing queued and nothing queued ahead,
                    // the posted step below would only reach speakChunk(false),
                    // find the queue empty and call endSynthesis #7 -- one thread
                    // hop later, while the screen reader's synthesis thread (and
                    // its next utterance behind it) waits. Same checks, same
                    // ending, no hop. Nothing can refill the queue after this
                    // point: a pending queueNextAhead finds it empty, and a
                    // retry only follows an error, not this onDone.
                    if (myGeneration == synthesisGeneration && !isStopped && !isFlushed && ahead.get() == null &&
                        synchronized(chunkQueue) { chunkQueue.isEmpty() }) {
                        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "No more text to read." }
                        endSynthesis(callback, "7")
                        return
                    }
                    speakHandler.post {
                        try {
                        // Posted while THIS utterance was live; it runs on the
                        // speak thread, which is neither the synthesis thread nor
                        // the binder thread, so an interrupt in between means it
                        // arrives after the utterance ended and the next one has
                        // already refilled chunkQueue. The id check above cannot
                        // see that -- it was true when we posted.
                        if (myGeneration != synthesisGeneration) return@post
                        // The next chunk is already in this engine's queue (and
                        // starting now): it only becomes the current one.
                        val queued = ahead.get()
                        if (queued != null) { promote(queued); return@post }
                        val next = synchronized(chunkQueue) { chunkQueue.firstOrNull()?.second }
                        if (next == null) { speakChunkRef(false); return@post }
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
                                val loadRes = loadOrPrefetched(nextLang)
                                if (loadRes == TextToSpeech.LANG_NOT_SUPPORTED || loadRes == TextToSpeech.LANG_MISSING_DATA) {
                                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Language " + nextLang + " is not supported.\n Text: " + next.text)
                                    endSynthesis(callback, if (next.typeCode == 1) "2" else "3")
                                    advance = false
                                }
                            }
                        } else if (modeInt == 4 || modeInt == 5) {
                            val nextLang = next.iso3()
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
                            // Its one effect is its log lines (the answer feeds only the
                            // dead fallback above), so the walk runs only while logging
                            // is on (owner, 2026-10-01).
                            if (EasyVoiceLogger.isLoggingEnabled()) LangStore.engineFor(nextLang, modeInt)
                            val loadRes = loadOrPrefetched(nextLang)
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
                            val nextLang = next.iso3()
                            val loadRes = loadOrPrefetched(nextLang)
                            if (loadRes == TextToSpeech.LANG_NOT_SUPPORTED || loadRes == TextToSpeech.LANG_MISSING_DATA) {
                                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Language " + nextLang + " is not supported.\n Text: " + next.text)
                                endSynthesis(callback, "4")
                                advance = false
                            }
                        }
                        if (advance) speakChunkRef(false)
                        } catch (ex: Throwable) {
                            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onDone Error: " + ex.toString())
                            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "endSynthesis #6" }
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
                // The engine could not say this chunk. Unless the failure is not
                // the engine's -- ERROR_OUTPUT (-5) is the audio device, and
                // ERROR_INVALID_REQUEST (-8) the request itself, and another engine
                // would fail both the same way -- the chunk goes to another engine,
                // posted off this binder callback because that may build a client.
                fun handOver(code: Int, n: String) {
                    // This chunk is the one queued AHEAD and failed before its
                    // turn: it is acted on when the chunk in front of it is done,
                    // which is when it would have been spoken and failed before.
                    if (mine != null && mine.deferFailure(code, n)) return
                    // A chunk queued ahead behind this failed one must not play:
                    // stop it now, from this thread, before it can start.
                    val queued = ahead.get()
                    if (queued != null && queued.wrapper === wrapper) {
                        try { wrapper.tts?.stop() } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "stop of the queued chunk: " + ex.toString()) }
                    }
                    // Switch OFF: straight to endSynthesis on this thread, which
                    // is exactly what onError did before the hand-over existed.
                    if (!backupFor(lang) || code == TextToSpeech.ERROR_OUTPUT || code == TextToSpeech.ERROR_INVALID_REQUEST) {
                        if (queued == null) { endSynthesis(callback, n); return }
                        speakHandler.post {
                            if (myGeneration != synthesisGeneration) return@post
                            cancelAhead(true)
                            endSynthesis(callback, n)
                        }
                        return
                    }
                    speakHandler.post {
                        if (myGeneration != synthesisGeneration || isStopped || isFlushed) return@post
                        cancelAhead(true)
                        // Already handed over (its process died first, and that
                        // path moved the chunk): the chunk is someone else's now.
                        if (speakingPkg != wrapper.pkg) return@post
                        val moved = try { retryChunkElsewhere(wrapper.rawPkg, wrapper.rawPkg + " failed with error " + code) } catch (ex: Throwable) {
                            EasyVoiceLogger.error(EasyVoiceLogger.TAG, "retry after error: " + ex.toString()); false
                        }
                        if (!moved) endSynthesis(callback, n)
                    }
                }
                @Deprecated("Deprecated in Java")
                override fun onError(id: String) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onError " + id)
                    if (id != expectedId) return
                    handOver(TextToSpeech.ERROR, "8")
                }
                override fun onError(id: String, errorCode: Int) {
                    EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onError " + id + " code " + errorCode)
                    if (id != expectedId) return
                    handOver(errorCode, "9")
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
                //
                // THE LINE BELOW IS A DIAGNOSTIC, NOT A FIX (2026-09-16), and it is
                // here because the hole above is REAL and reachable and the fix for
                // it is the one change that has already broken this app once.
                //
                // How it is reached, without inventing anything: our own onStop()
                // override QUEUES wrapper.stop() on the one stopExec thread and then
                // releases the wait in its finally. AOSP calls stopForApp
                // SYNCHRONOUSLY on the binder thread before posting the next item, so
                // the screen reader's NEXT utterance can be speaking by the time that
                // queued stop finally runs -- and it then stops THAT utterance, for
                // which stopImpl dispatches onStop and NEITHER onDone NOR onError.
                // Nothing else is coming, so that utterance's synthesis thread parks.
                // It recovers on the reader's next interrupt, so it costs an utterance
                // rather than the process, which is why it reads as "text skipped"
                // rather than "dead".
                //
                // WHAT IS LOGGED IS THE ONE THING THAT TELLS THE TWO APART: whether
                // the wait was still parked when this arrived. `parked=true` on a
                // matching id IS the hang; anything else is a stale callback doing no
                // harm. One line in the log the owner already shares settles it.
                //
                // THE OWNER GAVE THAT WORD ON 2026-09-17, so this releases the wait
                // now. The note above said the fix waits "for that log, or for the
                // owner's word", and the report is this symptom exactly: "bolte bolte
                // atak jata hai ... bilkul freeze ho jata hai ... force stop karta hun
                // fir tab shuru ho jata hai."
                //
                // FORCE STOP BEING THE CURE IS THE WHOLE DIAGNOSIS. Nothing is broken
                // on disk and nothing retries -- a thread is parked, and it is the
                // screen reader's ONE synthesis thread, so the device goes silent and
                // stays silent until the process dies.
                //
                // THE PRECONDITION IS MET AND THAT IS WHAT MAKES THIS SAFE NOW. The
                // 2026-09-03 attempt at this killed explore-by-touch outright, and the
                // reason was NOT the guard -- it was the id. `expectedId` was
                // "${utteranceId}_${chunkCounter}" where utteranceId is a static that
                // is literally "null" when the caller sets no param, and chunkCounter
                // resets to 1 per utterance, so two consecutive screen-reader
                // utterances both spoke under "null_1": a stale callback matched the
                // NEW listener and set isStopped on an utterance that had not spoken.
                // Since 2026-09-09 the id carries synthesisGeneration, which is bumped
                // once per onSynthesizeText and can never repeat, so `id == expectedId`
                // finally means what it says.
                //
                // IT MIRRORS onError ABOVE, LINE FOR LINE -- same id guard, same
                // release under syncLock, same conditional done() -- because both are
                // AutoTTS's `O(cb, n)` shape: log, set the stopped flag, notify, and
                // call done() only if the callback has already started.
                //
                // DELIBERATE DEPARTURE: AutoTTS's own listener onStop is a bare log
                // (noexc:2770). This is the class the owner has overridden rule 5 for
                // repeatedly -- the outcome is the whole device mute and only a force
                // stop clears it.
                //
                // TO REVERT, delete from the `if (id != expectedId) return` down, and
                // the diagnostic line above it goes back to doing the whole job.
                // LOG ONLY, AND IT IS BACK TO LOG ONLY (owner, 2026-09-17:
                // "explore by touch karte hain vahan per ... stop ho jata hai ...
                // vah problem fir se a gai hai").
                //
                // On 2026-09-17 this released the wait under `id == expectedId`,
                // reasoning that the generation in the id made the guard finally
                // mean what it says. The guard IS correct now. The change was
                // still wrong, and the reason is worth keeping because the note
                // that used to sit here described the mechanism and I shipped it
                // anyway.
                //
                // The callback this fires on is NOT stale. Our own onStop()
                // override QUEUES wrapper.stop() on the one stopExec thread and
                // releases the wait immediately; the reader's NEXT utterance can
                // already be speaking when that queued stop runs, so it stops the
                // NEW one -- and the engine then dispatches onStop carrying the
                // NEW utterance's id, which matches its expectedId exactly. The
                // release therefore cut off a live utterance. Explore-by-touch is
                // a continuous stream of interrupts, so it fired there constantly:
                // the 2026-09-03 regression, reproduced from the other end.
                //
                // THE ROOT CAUSE IS FIXED WHERE IT BELONGS instead -- see
                // EngineWrapper.stop, which now skips an order whose generation is
                // spent. With the late stop gone, the spurious onStop is gone with
                // it, and the utterance that used to park because nothing followed
                // that stop is never stopped in the first place.
                //
                // So this is AutoTTS's shape again (its listener's onStop is a
                // bare log, noexc:2770), and `parked=` still settles from the
                // owner's own log whether anything is left to fix here at all.
                override fun onStop(id: String, interrupted: Boolean) {
                    val parked = !isStopped && !isFlushed
                    EasyVoiceLogger.debug(EasyVoiceLogger.TAG) {
                        "onStop " + id + " interrupted=" + interrupted +
                        " mine=" + (id == expectedId) + " parked=" + parked }
                }
            }
        fun queueNextAhead() {
            if (myGeneration != synthesisGeneration || isStopped || isFlushed) return
            if (bypassed || ahead.get() != null) return
            val current = currentListener ?: return
            val pair = synchronized(chunkQueue) { chunkQueue.firstOrNull() } ?: return
            val next = pair.second
            // The language onDone would load for it, by the same three branches.
            val loadLang = if (modeInt == 1) when (next.typeCode) {
                1 -> "eng"
                2 -> dualLang
                3 -> numberSpecificLang
                4 -> puncSpecificLang
                5 -> emojiSpecificLang
                else -> ""
            } else next.iso3()
            if (loadLang.isNotEmpty()) {
                if (prefetchedLang != loadLang) return
                if (prefetchedResult == TextToSpeech.LANG_NOT_SUPPORTED || prefetchedResult == TextToSpeech.LANG_MISSING_DATA) return
            }
            val index = engineIndex
            if (index < 0 || index >= enginePool.size) return
            val wrapper = enginePool[index]
            if (wrapper.pkg != speakingPkg) return
            val tts = wrapper.tts ?: return
            if (wrapper.state != 2) return
            if (!isSameEnginePkg(EngineFinder.boundEngineOf(tts, wrapper.rawPkg), wrapper.rawPkg)) return
            // From here on, what onDone's step and speakChunk(false) do, in order.
            val savedCounter = chunkCounter
            val savedLang = prefetchedLang
            val savedResult = prefetchedResult
            synchronized(chunkQueue) {
                if (chunkQueue.firstOrNull() !== pair) return
                chunkQueue.removeAt(0)
            }
            if (loadLang.isNotEmpty()) prefetchedLang = ""
            chunkCounter++
            val chunk = pair.second
            val effectiveLang = if (modeInt == 1) when (chunk.typeCode) {
                2 -> prefs.toIso3(dualLang)
                3, 4, 5 -> chunk.iso3()
                else -> "eng"
            } else chunk.iso3()
            val voiceParams = LangStore.voiceParamsFor(effectiveLang)
            val finalRate   = sysRate  * (voiceParams[0] / 100.0f)
            val finalPitch  = sysPitch * (voiceParams[1] / 100.0f)
            val finalVolume = requestVolume * (voiceParams[2] / 100.0f)
            tts.setSpeechRate(finalRate); tts.setPitch(finalPitch)
            val expectedId = "${utteranceId}_${myGeneration}_${chunkCounter}" + (if (retryCount > 0) "_r$retryCount" else "")
            val queued = AheadChunk(pair, wrapper, effectiveLang, savedCounter, savedLang, savedResult)
            val listener = makeListener(wrapper, expectedId, queued, effectiveLang)
            queued.listener = listener
            // Until the chunk in front is done the client carries both listeners:
            // the queued chunk's id goes to its own, everything else to the one
            // that was there -- exactly what that one received before.
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                fun route(id: String?): UtteranceProgressListener = if (id == expectedId) listener else current
                override fun onStart(id: String) { route(id).onStart(id) }
                override fun onDone(id: String) { route(id).onDone(id) }
                @Deprecated("Deprecated in Java")
                override fun onError(id: String) { @Suppress("DEPRECATION") route(id).onError(id) }
                override fun onError(id: String, errorCode: Int) { route(id).onError(id, errorCode) }
                override fun onStop(id: String, interrupted: Boolean) { route(id).onStop(id, interrupted) }
            })
            val params = buildParams(wrapper, finalVolume)
            wrapper.listenerSet = true
            if (isForceAccessibility && !wrapper.audioAttrSet) {
                try { tts.setAudioAttributes(accessibilitySpeech); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) }
            }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "speak 2: " + chunk.text }
            ahead.set(queued)
            val accepted = try { tts.speak(chunk.text, TextToSpeech.QUEUE_ADD, params, expectedId) == TextToSpeech.SUCCESS } catch (ex: Exception) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "queueing the next chunk: " + ex.toString()); false
            }
            if (!accepted) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "the engine did not take the next chunk ahead -- it waits its turn"); cancelAhead(false) }
        }
        queueNextAheadRef = { queueNextAhead() }
        fun speakChunk(first: Boolean) {
            // The one place that pops chunkQueue and speaks, and it is reached
            // from two threads: the synthesis thread for the first chunk, and the
            // speak thread for every chunk after it (and for the end). Once this utterance has ended the
            // queue belongs to the next one, so a late caller must not pop it.
            if (myGeneration != synthesisGeneration) return
            if (isStopped || isFlushed) return
            // Nothing may still be queued ahead when a chunk is spoken normally;
            // every path that leads here has already taken it back.
            if (!first) cancelAhead(true)
            val pair = synchronized(chunkQueue) { if (chunkQueue.isEmpty()) null else chunkQueue.removeAt(0) }
            if (pair == null) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "No more text to read." }
                // Was four lines with two early returns. Identical in all four
                // cases -- null callback, not started, already finished, and
                // started-and-unfinished -- because the block ends in `return`
                // either way, so the guards' only effect was the done() call.
                endSynthesis(callback, "7")
                return
            }
            val (currentChunk, chunk) = pair
            val effectiveLang = if (modeInt == 1) when (chunk.typeCode) {
                2 -> prefs.toIso3(dualLang)
                3, 4, 5 -> chunk.iso3()
                else -> "eng"
            } else chunk.iso3()
            val chunkText = chunk.text
            currentPair = pair
            currentPairFirst = first
            currentPairLang = effectiveLang
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
                val forcedLocale = parseVoiceNameAsLocale(chunk.forcedLocale ?: "") ?: localeOf("")
                loadVoice(chunk.forcedEngine ?: "", forcedLocale, chunk.forcedVariant ?: "", false)
            }
            if (engineIndex < 0 || engineIndex >= enginePool.size) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "mTTSIndex out of range.")
                if (retryChunkElsewhere(LangStore.engineFor(effectiveLang, modeInt), "no engine could take " + effectiveLang)) return
                releaseWaitWithoutSpeaking("engine index out of range")
                return
            }
            val wrapper = enginePool[engineIndex]
            val tts = wrapper.tts ?: run {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "mTTSIndex refers null tts.")
                if (retryChunkElsewhere(wrapper.rawPkg, wrapper.rawPkg + " has no client")) return
                releaseWaitWithoutSpeaking("engine has no TextToSpeech")
                return
            }
            // A client this utterance's own load just found dead (clientIsDead
            // sets -1 at once), or one being restored (1), is not spoken into:
            // speak() on it can only fail, one binder call later.
            if (wrapper.state != 2) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + " is not ready (state " + wrapper.state + ")")
                if (retryChunkElsewhere(wrapper.rawPkg, wrapper.rawPkg + " is not ready")) return
                releaseWaitWithoutSpeaking("engine is not ready")
                return
            }
            // A CLIENT CAN BE RE-BOUND BEHIND OUR BACK, AND THIS IS THE ONLY PLACE
            // THAT CAN SEE IT (2026-09-23, from AOSP's TextToSpeech.Connection).
            //
            //     } catch (RemoteException ex) {
            //         Log.e(TAG, method + " failed", ex);
            //         if (reconnect) { disconnect(); initTts(); }
            //         return errorResult;
            //     }
            //
            // Every ordinary client call -- speak, stop, setLanguage, setVoice,
            // getVoices -- goes through runAction with reconnect = true. So a call
            // that lands on an engine as its process dies (DeadObjectException, a
            // RemoteException) makes the framework run initTts() AGAIN, with the
            // same useFallback = true that the init listeners guard against -- and
            // this time mInitListener is already null (dispatchOnInit clears it
            // after the first init), so NO listener of ours hears about it. If the
            // engine is not installed at that instant, which is what an update
            // looks like, the client comes back bound to the default engine: Easy
            // Voice. The wrapper still says state 2 and still names the engine.
            //
            // Speaking through that client is not a wrong voice, it is a hang: our
            // own Stub enqueues the chunk on OUR SynthHandler, behind the very
            // onSynthesizeText that is parked waiting for it. loadVoice's inline
            // detector cannot help when the voice cache says "Do nothing!" and no
            // setLanguage is made, so the check is here, on the client that is
            // about to speak. One Field.get per chunk (the Field is cached in
            // EngineFinder).
            //
            // The cure is the one every other failure on this path already uses:
            // clientIsDead takes the wrapper out of use and asks for the engine's
            // one restore, and this chunk ends through releaseWaitWithoutSpeaking
            // (or goes to another engine) so nothing is left parked.
            val boundNow = EngineFinder.boundEngineOf(tts, wrapper.rawPkg)
            if (!isSameEnginePkg(boundNow, wrapper.rawPkg)) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG,
                    "client for " + wrapper.rawPkg + " is bound to " + boundNow +
                    " -- not speaking through it")
                try { clientIsDead(wrapper, "its client was re-bound to " + boundNow) } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "restore after re-bind: " + ex.toString()) }
                if (retryChunkElsewhere(wrapper.rawPkg, wrapper.rawPkg + " is not reachable")) return
                releaseWaitWithoutSpeaking("engine client re-bound to another engine")
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
            // One walk for all three (owner, 2026-10-01): speed, pitch, volume.
            val voiceParams = LangStore.voiceParamsFor(paramLang)
            val appRate   = voiceParams[0] / 100.0f
            val appPitch  = voiceParams[1] / 100.0f
            val appVolume = voiceParams[2] / 100.0f
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
            val expectedId = "${utteranceId}_${myGeneration}_${chunkCounter}" + (if (retryCount > 0) "_r$retryCount" else "")
            val listener = makeListener(wrapper, expectedId, null, effectiveLang)
            currentListener = listener
            tts.setOnUtteranceProgressListener(listener)
            val params = buildParams(wrapper, finalVolume)
            if (isStopped || isFlushed) return
            wrapper.listenerSet = true
            if (isForceAccessibility && !wrapper.audioAttrSet) {
                try { tts.setAudioAttributes(accessibilitySpeech); wrapper.audioAttrSet = true } catch (ex: Exception) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, ex.toString()) }
            }
            if (first) {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Current engine: " + pkg }
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { expectedId }
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "speak 1: " + chunkText }
            } else {
                EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "speak 2: " + chunkText }
            }
            val speakRunnable = Runnable {
                try {
                    // Whose death would strand this utterance. Set before the
                    // call, so a process that dies during it is still matched.
                    speakingPkg = pkg
                    // Under the wrapper's speak lock, with the generation that
                    // flushed it: a late stop for an older utterance sees it and
                    // leaves this one alone (EngineWrapper.stop).
                    val speakResult = synchronized(wrapper.speakLock) {
                        wrapper.flushedGeneration = myGeneration
                        tts.speak(chunkText, TextToSpeech.QUEUE_FLUSH, params, expectedId)
                    }
                    if (speakResult == 0) {
                        if (first) reportSynthesisWork(System.nanoTime() - workStart)
                        // The engine has the chunk. Everything until its last word is
                        // free time to load the next chunk's language and, on this same
                        // engine, to hand it over with QUEUE_ADD -- so it starts now,
                        // not at onStart. Posted: the speak thread is the one thread
                        // that owns the prefetch slot and the ahead chunk.
                        speakHandler.post { prefetchNextLanguage(); queueNextAheadRef() }
                    } else {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "Speaking failed!!!")
                        if (retryChunkElsewhere(wrapper.rawPkg, wrapper.rawPkg + " refused to speak")) {
                            // the chunk is being spoken elsewhere
                        } else if (first) {
                            startAndFinish(callback)
                            unlockSynthesis("12")
                        } else {
                            endSynthesis(callback, "5")
                        }
                    }
                } catch (ex: Exception) {
                    if (retryChunkElsewhere(wrapper.rawPkg, wrapper.rawPkg + " threw on speak")) {
                        // the chunk is being spoken elsewhere
                    } else if (first) {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onSynthesis Error: " + ex.message)
                        startAndFinish(callback)
                        unlockSynthesis("14")
                    } else {
                        EasyVoiceLogger.error(EasyVoiceLogger.TAG, "onDone Error: " + ex.message)
                        endSynthesis(callback, "6")
                    }
                }
            }
            speakRunnable.run()
        }
        speakChunkRef = { isFirst -> speakChunk(isFirst) }
        speakChunk(true)
        if (keepAliveFlag) {
            // AutoTTS logs this immediately before k0(callback), and spells it
            // "Keep-live", not "Keep-alive".
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "Keep-live activated" }
            startCallback(callback)
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
        if (retryOnEngineDeath === retryOnDeath) retryOnEngineDeath = null
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onSynthesizeText ended" }
        startAndFinish(callback)
    }

    // ==========================================================================
    //  SHUTDOWN
    // ==========================================================================
    override fun onDestroy() {
        EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "onDestroy" }
        // Each step on its own, for the same reason onCreate's five are: they are
        // independent, and a failure in one must not skip the others. Shutting the
        // engine clients down and releasing the bindings is what stops this
        // process leaking a connection into every TTS engine on the phone, and
        // until 2026-09-11 a throw from stopForeground or abandonAudioFocus --
        // abandonAudioFocus dereferences audioManager!! and audioFocusRequest!!,
        // both null if requestAudioFocus failed at startup -- skipped the pool
        // walk that follows.
        try { stopForegroundNow() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "stopForeground: " + ex.toString()) }
        try { abandonAudioFocus() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "abandonAudioFocus: " + ex.toString()) }
        // Every client the pool still holds, not only the ready ones: a wrapper
        // at -1 can still hold the client that failed, and shutdown() is what
        // releases its session with the system's TTS manager.
        destroyed = true
        try { engineSettled() } catch (_: Throwable) {}
        // Nothing will be spoken by this instance any more: a parked background
        // scan step goes on now instead of waiting for an utterance end.
        try { synthesizingNow = false; ScanYield.speechInFlight = { false }; ScanYield.speechEnded() } catch (_: Throwable) {}
        try { (hintSession as? java.io.Closeable)?.close() } catch (_: Throwable) {}
        hintSession = null
        try { for (index in 0 until enginePool.size) enginePool[index].shutdown() } catch (ex: Throwable) { EasyVoiceLogger.error(EasyVoiceLogger.TAG, "engine shutdown: " + ex.toString()) }
        try { unbindAllEngineKeepAlive() } catch (_: Throwable) {}
        try { if (packageReceiverRegistered) { packageReceiverRegistered = false; unregisterReceiver(packageReceiver) } } catch (_: Throwable) {}
        try { if (screensObserved) { screensObserved = false; androidx.lifecycle.ProcessLifecycleOwner.get().lifecycle.removeObserver(screensClosedObserver) } } catch (_: Throwable) {}
        try { mainHandler.removeCallbacksAndMessages(null) } catch (_: Throwable) {}
        try { speakHandler.removeCallbacksAndMessages(null); speakThread.quitSafely() } catch (_: Throwable) {}
        if (running?.get() === this) running = null
        super.onDestroy()
    }

    // ==========================================================================
    //  STATICS                     AutoTtsService's fields
    //  pushLanguageSets is s0(): detect sets AND hints, and it may only be called
    //  where the language list was just rebuilt. pushDetectSetsOnly is the
    //  per-utterance one. INVARIANTS #1 and #2.
    // ==========================================================================
    companion object {
        // THE RUNNING SERVICE, WEAKLY, for one purpose: the settings scan
        // (EngineFinder, same process) reports an engine that just answered a
        // fresh client with its voices -- proof that it can be reached now, one
        // of the events restoreEngine waits on. Weak and cleared in onDestroy, so
        // it never keeps the service alive (the 2026-09-10 appCtx leak was a
        // STRONG static reference to it).
        @Volatile private var running: java.lang.ref.WeakReference<EasyVoiceTtsService>? = null
        // ADPF's required target for one utterance's work (see boostSynthesisThread):
        // under it the scheduler eases off, over it it raises the CPU floor.
        const val HINT_TARGET_NANOS = 1_000_000L
        // True while onSynthesizeText runs (there is one synthesis thread).
        @Volatile @JvmStatic var synthesizingNow = false
        // THE SCAN READS A LIVE ENGINE THROUGH THE CLIENT WE ALREADY HOLD
        // (owner, 2026-10-01). EngineFinder built a NEW TextToSpeech per engine
        // for every scan -- the background scan right after a cold start
        // included, so every engine was bound twice exactly while the first
        // utterances waited. A client that is ready (state 2), not mid-restore
        // and still bound to its own engine answers getVoices the same way; the
        // scan reads it off the main thread and never shuts it down. Anything
        // less than that and the scan builds its own client, as before.
        @JvmStatic fun liveClientFor(rawPkg: String): TextToSpeech? {
            val svc = running?.get() ?: return null
            return try { svc.liveClient(rawPkg) } catch (_: Throwable) { null }
        }
        @JvmStatic fun offerScannedVoices(client: TextToSpeech, voices: MutableSet<android.speech.tts.Voice>) {
            try { running?.get()?.offerVoices(client, voices) } catch (_: Throwable) {}
        }
        @JvmStatic fun engineAnswered(rawPkg: String) {
            try { running?.get()?.onEngineAnswered(rawPkg) } catch (_: Throwable) {}
        }
        // "Show persistent notification" was switched, on one of the app's own
        // screen (the Advanced tab). ON: a start is sent while the
        // screen shows, so the foreground service begins now rather than on the
        // next utterance in the background (see onStartCommand). OFF: only a
        // running service is told, so switching it off never creates one just to
        // stop it again.
        @JvmStatic fun applyForegroundSetting(ctx: android.content.Context) {
            try {
                if (showNotificationFlag) ctx.startService(android.content.Intent(ctx, EasyVoiceTtsService::class.java))
                else running?.get()?.let { service -> service.onMainThread { if (service.isForegroundActive()) service.stopForegroundNow() } }
            } catch (ex: Throwable) {
                EasyVoiceLogger.error(EasyVoiceLogger.TAG, "applyForegroundSetting: " + ex.toString())
            }
        }
        // Every scan ends here, whichever screen or service started it.
        @JvmStatic fun scanFinished() {
            try { running?.get()?.onScanFinished() } catch (_: Throwable) {}
        }
        // Once per PROCESS, not per service instance: the system may destroy and
        // recreate the service while the process lives, and the lists the scan
        // saved are still in it.
        @Volatile private var startupScanDone = false
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
        // (javap shows the ldc "easyvoice" + invokestatic System.loadLibrary
        // right there), so touching any of these statics initialises the class
        // and loads the library first. It is not left to the companion being
        // touched separately.
        init { System.loadLibrary("easyvoice") }

        @JvmStatic external fun processDirect(
            text: String,
            latinFallback: String, nonLatinFallback: String,
            mode: String,
            numberMode: Int, numberSpecific: String,
            punctuationMode: Int, punctuationSpecific: String,
            emojiMode: Int, emojiSpecific: String, punctuationInFlow: Boolean, smartNumber: Boolean,
            smartNumberGroupSize: Int,
            neutralDefault: String, neutralType: Int,
            disableAdvancedDetection: Boolean, detect: Boolean, singleStops: Boolean
        ): Array<String>
        // clsCLD2.b's fold, then the span detector, in one call.
        @JvmStatic external fun nativeFoldAndDetect(text: String, singleStops: Boolean): Array<String>
        @JvmStatic external fun setIsoMap(iso2: Array<String>, iso3: Array<String>)
        @JvmStatic external fun setLanguageHints(langs: Array<String>)
        @JvmStatic external fun setDetectSets(detectOkIso3: Array<String>, enabledLangs: Array<String>)
        // clsCLD2.d's windowed detect and, only when it answers unknown, the fold
        // and spans clsCLD2.f weighs up -- one crossing (owner, 2026-10-01).
        @JvmStatic external fun detectWindowOrSpans(text: String, latinFallback: String, nonLatinFallback: String,
            disableAdvancedDetection: Boolean, wantLog: Boolean, singleStops: Boolean): Array<String>
        // An utterance is starting: the core's own worker checks that the
        // detector's tables are still in the page cache and reads back what the
        // kernel dropped (image_warm.h). One mutex and a signal here.
        @JvmStatic external fun nativeRewarmTables()

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
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "updateLanguage2LetterCodes" }
            val version = LangStore.listVersion.get()
            val enabledSet = refillEnabledIso2(true)
            if (sendDetectSets(detectOkIso3(), enabledSet, always = true)) {
                synchronized(sentDetectLock) { pushedListVersion = version }
            }
            // The SAME hints in the SAME order are not sent again (owner,
            // 2026-10-01). setLanguageHints builds a new HintState, and a new
            // HintState drops the native CLD2 answer cache's span table -- so
            // every screen resume, which rebuilds an identical list, emptied the
            // cache the next utterance would have hit. Order is compared, not
            // just membership: it decides which 64 survive the native cap.
            val hints = enabledSet.toTypedArray()
            val sameHints = synchronized(sentDetectLock) { sentHints?.contentEquals(hints) == true }
            if (!sameHints) {
                try {
                    setLanguageHints(hints)
                    synchronized(sentDetectLock) { sentHints = hints }
                } catch (_: Throwable) { synchronized(sentDetectLock) { sentHints = null } }
            }
            EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { " " + enabledSet.toString() }
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
                    else if (logUnmapped) EasyVoiceLogger.debug(EasyVoiceLogger.TAG) { "unmapped language code: " + entry.iso3 }
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
        // NOT EVEN BUILT when the list has not changed (owner, 2026-10-01):
        // the sets come only from entries' iso3 / engine / disabled and from
        // which entries are in the list, and LangStore.listVersion moves on
        // every one of those writes. Read BEFORE the walk, so a change during
        // it leaves the recorded version behind and the next utterance builds
        // again. Recorded only when the core holds the sets (sendDetectSets
        // true), so a push that threw is retried.
        @JvmStatic
        fun pushDetectSetsOnly() {
            val version = LangStore.listVersion.get()
            synchronized(sentDetectLock) { if (version == pushedListVersion) return }
            val enabledSet = refillEnabledIso2(false)
            if (sendDetectSets(detectOkIso3(), enabledSet, always = false)) {
                synchronized(sentDetectLock) { pushedListVersion = version }
            }
        }
        // WHAT THE CORE HOLDS NOW (owner, 2026-10-01: "jab badle tabhi bhejo").
        // The per-utterance push marshalled both arrays across JNI and rebuilt
        // two unordered_sets in C++ every time, though the language list changes
        // only when a settings screen rebuilds it. Compared as SETS: the core
        // stores them as unordered_sets, so order cannot change what it holds,
        // and enabledIso2 is one HashSet refilled in place whose iteration order
        // may move. A push that throws is not remembered, so the next utterance
        // tries again. pushLanguageSets (= s0) always sends, as AutoTTS's s0 does,
        // and records what it sent.
        private val sentDetectLock = Any()
        private var sentDetectOk: Set<String>? = null
        private var sentEnabled: Set<String>? = null
        private var sentHints: Array<String>? = null
        private var pushedListVersion = -1L
        // True when the core now holds these sets (already did, or just sent).
        private fun sendDetectSets(detectOk: List<String>, enabled: Set<String>, always: Boolean): Boolean {
            val okNow = HashSet(detectOk)
            val enabledNow = HashSet(enabled)
            synchronized(sentDetectLock) {
                if (!always && okNow == sentDetectOk && enabledNow == sentEnabled) return true
                try {
                    setDetectSets(okNow.toTypedArray(), enabledNow.toTypedArray())
                    sentDetectOk = okNow
                    sentEnabled = enabledNow
                    return true
                } catch (_: Throwable) {
                    sentDetectOk = null
                    sentEnabled = null
                    pushedListVersion = -1L
                    return false
                }
            }
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
        // synthesis thread for the first chunk and the SPEAK THREAD from onDone's
        // post (which also does chunkCounter++; until 2026-10-01 that was the main
        // looper, and an empty queue was ended on a BINDER thread directly).
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
        // "Backup TTS", PER LANGUAGE (owner, 2026-10-06; it was one global
        // switch on the Advanced tab from 2026-09-23). The iso3 codes it is on
        // for, set on the Voice setup screen of each language; an immutable set
        // replaced whole, so a reader on any thread sees one consistent answer.
        // Gates every automatic engine substitution for THAT language:
        // switchToAlternative, retryChunkElsewhere, the onError hand-over and the
        // scan's repointUninstalled. Off (the default, empty) is the behaviour
        // from before the feature -- the configured engine is kept.
        @Volatile @JvmField var backupLangs: Set<String> = emptySet()
        @JvmStatic fun backupFor(lang: String): Boolean = lang.isNotEmpty() && backupLangs.contains(lang)
        @Volatile @JvmField var disableAdvancedFlag = true
        @Volatile @JvmField var quickCharacterFlag = false
    }
}
