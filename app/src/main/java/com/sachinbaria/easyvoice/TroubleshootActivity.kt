package com.sachinbaria.easyvoice

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.core.content.PackageManagerCompat
import androidx.core.content.UnusedAppRestrictionsConstants
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.net.toUri
import androidx.core.os.HandlerCompat

// ==========================================================================
//  TROUBLESHOOT VOICE ENGINES (owner, 2026-09-24)
//
//  "jo bhi TTS work nahin karte honge ... scan karke properly sahi tarike sab
//  kuchh sahi ho jaega aur automatically kaam karne lag jaega."
//
//  What it does, in order, and what repairs itself without the user:
//   1. The engine scan, with `repair = true`. It builds a FRESH client for every
//      installed engine and reads its voices (one retry each), which is the only
//      proof an engine can be reached right now. Every engine that answers hands
//      the service back its restore (engineAnswered), languages set up on an
//      engine that is no longer installed move to one that speaks them -- even
//      with Backup TTS off, because the user asked for repair -- and the voice
//      list, engine list and language list are saved again.
//   2. The running service is told to troubleshoot its own pool: an engine it
//      never had is added, every stopped engine is restarted once, and a client
//      that lost its connection or was re-bound elsewhere is replaced. A live
//      engine is never torn down.
//   3. Every engine that answered is asked to SYNTHESISE a test sentence into a
//      file -- TextToSpeech.synthesizeToFile, silent, so TalkBack is not talked
//      over. That runs the engine's real synthesis path and must end in onDone
//      or onError. An engine that accepts the text and never finishes is the one
//      failure nothing on the speaking path can see (it raises no event), and
//      this is the only place a clock is allowed to name it: 20 s, on a screen
//      the user opened to diagnose, never on the speaking path. The service then
//      replaces its client for that engine once.
//   4. What cannot be repaired by an app is reported with the system's own
//      screen for it: a language not downloaded -> the engine's
//      INSTALL_TTS_DATA activity, only when the engine has one (the system is
//      asked to resolve it); each engine's battery optimization, on or off,
//      asked of PowerManager; turned off or restricted -> its app
//      info; not installed -> the Play Store; battery optimisation -> the
//      system list; Easy Voice not the preferred engine -> the TTS settings.
//   5. EVERYTHING EASY VOICE NEEDS TO KEEP RUNNING, in one place (owner,
//      2026-09-24: "auto start wala bhi button ... jo permission ki need rahti
//      hai ... taki background mein acche se work karen"): battery
//      optimization, background restriction, "Pause app activity if unused"
//      (androidx PackageManagerCompat), the persistent notification that makes
//      it a foreground service, and the phone maker's own Auto-start list when
//      this phone has one.
//   6. (2026-09-24, owner: "jis TTS ke pass voice hai sirf vahi aane chahie ...
//      jo bhi permission jaruri hoti hai vah permissions ke buttons aane
//      chahie") ONLY ENGINES WITH A VOICE OF THEIR OWN ARE LISTED. An engine
//      that has never listed a voice, or whose test sentence came back as a
//      file with no sound in it -- an engine that hands text to another
//      engine, as Easy Voice and AutoTTS do -- is left out, unless one of the
//      user's languages is set up on it. Every permission sits on its own line
//      with its own button right under it, and a line that is already fine has
//      no button.
// ==========================================================================

// A fix opens a system screen (`intent`; `forResult` when the screen must be
// started for a result), or does something in the app (`action`).
data class TroubleshootFix(
    val label: String,
    val intent: Intent? = null,
    val forResult: Boolean = false,
    val action: (() -> Unit)? = null
)
// One line of the report and the buttons that fix what it says, right under it.
data class TroubleshootItem(val text: String, val fixes: List<TroubleshootFix> = emptyList())
data class TroubleshootFinding(
    val title: String,
    val ok: Boolean,
    val items: List<TroubleshootItem>
)
data class TroubleshootResult(
    val summary: String,
    val repaired: List<String>,
    val findings: List<TroubleshootFinding>
)

// THE SYSTEM'S OWN SCREENS, IN ONE PLACE, each as direct as Android allows.
object SystemScreens {
    fun appInfo(pkg: String) =
        TroubleshootFix("App info", Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, ("package:" + pkg).toUri()))
    fun batteryList() =
        TroubleshootFix("Battery optimization settings", Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    // Asked of PowerManager, which answers for ANY package, not only our own.
    fun ignoresBatteryOptimization(ctx: Context, pkg: String): Boolean {
        val power = ContextCompat.getSystemService(ctx, PowerManager::class.java) ?: return true
        return try { power.isIgnoringBatteryOptimizations(pkg) } catch (_: Exception) { true }
    }
    // The app's own notification page from Android 8, its app info before that.
    fun notificationSettings(pkg: String): TroubleshootFix =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            TroubleshootFix("Notification settings", Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(Settings.EXTRA_APP_PACKAGE, pkg))
        else appInfo(pkg)
}

class TroubleshootActivity : EvActivity() {
    private val handler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var working by mutableStateOf(true)
    private var progress by mutableStateOf("")
    private var result by mutableStateOf<TroubleshootResult?>(null)
    // Bumped by every run and by onDestroy, so a late callback from an earlier
    // run (or from a destroyed screen) touches nothing.
    private var generation = 0
    private val testClients = ArrayList<TextToSpeech>()
    // The unused-app screen has to be started for a result (IntentCompat's own
    // documentation: the Play Store backport answers only startActivityForResult).
    private val fixLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    // After the notification dialog: said no, and Android will not ask again
    // (ActivityCompat: no rationale to show) -> the app's notification page,
    // where the switch is. Otherwise the report is simply read again.
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (!granted && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
            !ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
            openFix(SystemScreens.notificationSettings(packageName))
        }
        refreshReport()
    }
    // "Pause app activity if unused", read asynchronously: the answer is a
    // ListenableFuture, and on Android 6 to 10 it comes from the Play Store.
    private var unusedRestrictions = UnusedAppRestrictionsConstants.FEATURE_NOT_AVAILABLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LangStore.ensureLoaded(this)
        setContent {
            EasyVoiceTheme {
                TroubleshootScreen(
                    working = working,
                    progress = progress,
                    result = result,
                    onRunAgain = { runChecks() },
                    onFix = { fix -> openFix(fix) }
                )
            }
        }
        readUnusedRestrictions()
        runChecks()
    }

    // Coming back from a system screen: every line that reads system state is
    // read again, so the report says whether the fix took without a new run.
    override fun onResume() {
        super.onResume()
        readUnusedRestrictions()
        refreshReport()
    }

    private fun refreshReport() {
        if (working || result == null) return
        val inputs = lastInputs ?: return
        result = buildResult(inputs.reports, inputs.repaired, inputs.speechProblems)
    }

    private fun readUnusedRestrictions() {
        try {
            val future = PackageManagerCompat.getUnusedAppRestrictionsStatus(this)
            future.addListener({
                val status = try { future.get() } catch (_: Exception) { UnusedAppRestrictionsConstants.ERROR }
                if (status != unusedRestrictions) { unusedRestrictions = status; refreshReport() }
            }, ContextCompat.getMainExecutor(this))
        } catch (_: Exception) {}
    }

    private class ReportInputs(
        val reports: List<EngineFinder.EngineReport>,
        val repaired: List<String>,
        val speechProblems: Map<String, String>
    )
    private var lastInputs: ReportInputs? = null

    override fun onDestroy() {
        generation++
        handler.removeCallbacksAndMessages(null)
        releaseTestClients()
        super.onDestroy()
    }

    private fun releaseTestClients() {
        for (client in testClients) EngineFinder.shutdownLater(client)
        testClients.clear()
    }

    private fun openFix(fix: TroubleshootFix) {
        val action = fix.action
        if (action != null) {
            try { action() } catch (_: Exception) {}
            refreshReport()
            return
        }
        val intent = fix.intent ?: return
        try {
            if (fix.forResult) fixLauncher.launch(intent) else startActivity(intent)
        } catch (_: Exception) {
            // No Play Store app: the same page on the web, as MainActivity does.
            val data = intent.data
            if (data != null && data.scheme == "market") {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, ("https://play.google.com/store/apps/details?id=" + data.getQueryParameter("id")).toUri()))
                    return
                } catch (_: Exception) {}
            }
            // ActivityNotFoundException, or a SecurityException from an OEM
            // settings screen that is not exported.
            Toast.makeText(this, "That screen is not available on this device.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun runChecks() {
        val myGeneration = ++generation
        handler.removeCallbacksAndMessages(null)
        releaseTestClients()
        working = true
        result = null
        lastInputs = null
        progress = "Checking voice engines"
        EngineFinder.scanLanguages(
            this,
            onProgress = { line ->
                val pkg = line.removePrefix("Scanning ").substringBefore("...")
                runOnUiThread { if (myGeneration == generation) progress = "Checking " + EngineFinder.engineLabel(this, pkg) }
            },
            repair = true,
            onReport = { reports, moved ->
                if (myGeneration == generation) afterScan(myGeneration, reports, moved)
            }
        ) { }
    }

    private fun afterScan(myGeneration: Int, reports: List<EngineFinder.EngineReport>, moved: List<String>) {
        val repaired = ArrayList<String>(moved)
        if (EasyVoiceTtsService.troubleshootEngines()) {
            repaired.add("Restarted engines that had stopped")
        }
        val speechProblems = HashMap<String, String>()
        // An engine already found silent at this very version is not asked
        // again: a proxy engine SPEAKS the test sentence aloud through another
        // engine, over the screen reader.
        val toTest = ArrayList<EngineFinder.EngineReport>()
        for (report in reports) {
            if (report.problem.isNotEmpty()) continue
            if (knownSilent(report.pkg)) speechProblems[report.pkg] = NO_OWN_VOICE else toTest.add(report)
        }
        if (toTest.isEmpty()) { report(myGeneration, reports, repaired, speechProblems); return }
        progress = "Testing engines"
        var remaining = toTest.size
        for (report in toTest) {
            testEngine(myGeneration, report.pkg) { problem ->
                if (problem == NO_OWN_VOICE) {
                    rememberSilent(report.pkg)
                    speechProblems[report.pkg] = problem
                } else if (problem != null) {
                    speechProblems[report.pkg] = problem
                    EasyVoiceTtsService.engineUnresponsive(report.pkg)
                }
                remaining--
                if (remaining == 0) report(myGeneration, reports, repaired, speechProblems)
            }
        }
    }

    private fun versionOf(pkg: String): Long = try {
        PackageInfoCompat.getLongVersionCode(packageManager.getPackageInfo(pkg, 0))
    } catch (_: Exception) { -1L }
    private fun knownSilent(pkg: String): Boolean {
        val version = silentEngines[pkg] ?: return false
        return version == versionOf(pkg)
    }
    private fun rememberSilent(pkg: String) { silentEngines[pkg] = versionOf(pkg) }

    // One engine, one fresh client, one silent sentence. The engine is asked on
    // EngineFinder.engineCalls, never on the main thread: setLanguage and
    // synthesizeToFile wait for the engine, and a wedged one would freeze this
    // screen -- and with it the 20 s clock that is here to name it.
    private fun testEngine(myGeneration: Int, pkg: String, onDone: (String?) -> Unit) {
        val cell = arrayOfNulls<TextToSpeech>(1)
        val file = java.io.File(cacheDir, "troubleshoot_" + Integer.toHexString(pkg.hashCode()) + ".wav")
        var finished = false
        lateinit var timeout: Runnable
        fun finishTest(problem: String?) {
            if (finished) return
            finished = true
            handler.removeCallbacks(timeout)
            val client = cell[0]
            cell[0] = null
            client?.let { testClients.remove(it) }
            try {
                EngineFinder.engineCalls.execute {
                    try { client?.shutdown() } catch (_: Exception) {}
                    try { file.delete() } catch (_: Exception) {}
                }
            } catch (_: Exception) {}
            if (myGeneration == generation) onDone(problem)
        }
        timeout = Runnable { finishTest("no answer to a test") }
        fun onInitMain(status: Int) {
            if (finished) return
            val client = cell[0]
            if (status != TextToSpeech.SUCCESS || client == null) { finishTest("did not start"); return }
            if (EngineFinder.boundEngineOf(client, pkg) != pkg) { finishTest("connected to another engine"); return }
            val locale = configuredLocaleFor(pkg)
            client.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                // Read here, on the binder thread, before the file is deleted.
                override fun onDone(utteranceId: String?) {
                    val sound = wroteSound(file)
                    handler.post { finishTest(if (sound) null else NO_OWN_VOICE) }
                }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) { handler.post { finishTest("test failed") } }
                override fun onError(utteranceId: String?, errorCode: Int) {
                    handler.post { finishTest("test failed (error " + errorCode + ")") }
                }
            })
            try {
                EngineFinder.engineCalls.execute {
                    val queued = try {
                        val iso3 = if (locale != null && client.setLanguage(locale) >= TextToSpeech.LANG_AVAILABLE) EngineFinder.iso3Of(locale)
                                   else EngineFinder.iso3Of(try { client.voice?.locale } catch (_: Exception) { null })
                        val sentence = SampleTexts.get(iso3).ifEmpty { "Easy Voice test." }
                        client.synthesizeToFile(sentence, Bundle(), file, "ev_troubleshoot")
                    } catch (_: Exception) { TextToSpeech.ERROR }
                    if (queued != TextToSpeech.SUCCESS) handler.post { finishTest("test refused") }
                }
            } catch (_: Exception) { finishTest("test refused") }
        }
        try {
            cell[0] = TextToSpeech(applicationContext, { status ->
                if (Looper.getMainLooper().isCurrentThread) onInitMain(status) else handler.post { onInitMain(status) }
            }, pkg)
            cell[0]?.let { testClients.add(it) }
            handler.postDelayed(timeout, 20000L)
        } catch (_: Exception) {
            finishTest("could not start")
        }
    }

    // THE ENGINE'S OWN VOICE, MEASURED. synthesizeToFile's file is written by
    // the framework (FileSynthesisCallback): a 44-byte WAV header, then the PCM
    // the engine produced. An engine with no voice of its own -- one that hands
    // the text to another engine, as Easy Voice does -- produces none, so the
    // file is the header alone, or silence (a proxy that feeds zero samples to
    // stay alive). Any non-zero byte after the header is sound.
    private fun wroteSound(file: java.io.File): Boolean = try {
        if (!file.exists() || file.length() <= WAV_HEADER) false
        else {
            val bytes = file.readBytes()
            var sound = false
            for (i in WAV_HEADER until bytes.size) { if (bytes[i] != 0.toByte()) { sound = true; break } }
            sound
        }
    } catch (_: Exception) { true }

    // The locale a language is set up with on this engine, so the test uses a
    // voice that is really in use.
    private fun configuredLocaleFor(pkg: String): java.util.Locale? {
        for ((key, value) in LangStore.prefs(this).all) {
            if (key.length != 3 || value !is String) continue
            val parts = value.split("#")
            if (parts.size >= 2 && parts[0] == pkg && parts[1].isNotEmpty()) return EngineFinder.parseStoredLocale(parts[1])
        }
        return null
    }

    // Every engine a language is set up on, with those languages' names.
    private fun configuredEngines(): Map<String, List<String>> {
        val byEngine = LinkedHashMap<String, MutableList<String>>()
        for ((key, value) in LangStore.prefs(this).all) {
            if (key.length != 3 || value !is String) continue
            val pkg = value.substringBefore("#")
            if (pkg.isEmpty() || pkg.equals("disable", true) || EngineFinder.isSelfEngine(pkg)) continue
            byEngine.getOrPut(pkg) { ArrayList() }.add(EngineFinder.languageName(key))
        }
        return byEngine
    }

    private fun ttsSettingsFix() =
        TroubleshootFix("TTS Settings", Intent(TTS_SETTINGS_ACTION))
    // The engine's own "install voice data" screen, but only when the engine
    // really has one: the system is asked to resolve the intent.
    private fun installDataFix(pkg: String): TroubleshootFix? {
        val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA).setPackage(pkg)
        val found = try { packageManager.resolveActivity(intent, 0) } catch (_: Exception) { null }
        return if (found != null) TroubleshootFix("Download voice data", intent) else null
    }
    // How many languages the engine has voices for: the scan just made, or the
    // voices kept from before for an engine it could not read.
    private fun languageCount(pkg: String): Int =
        EngineFinder.lastScanVoices.asSequence()
            .filter { it.pkg == pkg && !it.notFound }
            .map { EngineFinder.iso3Of(it.locale) }
            .filter { it != "zxx" }
            .distinct()
            .count()
    // An engine's battery line: off, or on with the system list and the
    // engine's App info (Android 12+ keeps Unrestricted there). Not the one-tap
    // ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS dialog: it needs a permission
    // Google Play restricts (owner, 2026-09-24).
    private fun batteryItem(pkg: String): TroubleshootItem {
        if (SystemScreens.ignoresBatteryOptimization(this, pkg)) return TroubleshootItem("Battery optimization: off.")
        return TroubleshootItem("Battery optimization: on. Set it to Unrestricted.",
            listOf(SystemScreens.batteryList(), SystemScreens.appInfo(pkg)))
    }

    private fun systemFindings(): List<TroubleshootFinding> {
        val out = ArrayList<TroubleshootFinding>()
        val preferred = try { Settings.Secure.getString(contentResolver, Settings.Secure.TTS_DEFAULT_SYNTH) } catch (_: Exception) { null }
        if (preferred != null && preferred != packageName) {
            out.add(TroubleshootFinding(TITLE_PREFERRED, false, listOf(TroubleshootItem(
                "Preferred engine: " + EngineFinder.engineLabel(this, preferred) + ". Choose Easy Voice.",
                listOf(ttsSettingsFix())))))
        }
        out.add(permissionsFinding())
        return out
    }

    // EVERY PERMISSION EASY VOICE NEEDS TO KEEP RUNNING, one line each, and the
    // button that turns it on right under its line. A line that is already fine
    // says so and has no button.
    private fun permissionsFinding(): TroubleshootFinding {
        val items = ArrayList<TroubleshootItem>()
        var ok = true
        // Background use (Android 9+): "Restricted" stops the app in the
        // background whatever else is set. Changed on the app's own info page.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P && isBackgroundRestricted()) {
            ok = false
            items.add(TroubleshootItem("Background use: restricted. Allow it.", listOf(SystemScreens.appInfo(packageName))))
        }
        val battery = batteryItem(packageName)
        if (battery.fixes.isNotEmpty()) ok = false
        items.add(battery)
        // "Pause app activity if unused" (Android 11 and later, and the Play
        // Store's backport on 6 to 10): a voice engine is used without ever being
        // opened, so it looks unused.
        when (unusedRestrictions) {
            UnusedAppRestrictionsConstants.API_30_BACKPORT, UnusedAppRestrictionsConstants.API_30,
            UnusedAppRestrictionsConstants.API_31 -> {
                ok = false
                items.add(TroubleshootItem("Pause app activity if unused: on. Turn it off.", listOf(TroubleshootFix("Unused app settings",
                    IntentCompat.createManageUnusedAppRestrictionsIntent(this, packageName), forResult = true))))
            }
            UnusedAppRestrictionsConstants.DISABLED -> items.add(TroubleshootItem("Pause app activity if unused: off."))
        }
        // The persistent notification is what runs Easy Voice as a foreground
        // service, the one kind of app Android does not stop to free memory, and
        // what keeps it through Clear all. It runs even with notifications off;
        // they only decide whether the notification is seen.
        if (!EasyVoiceTtsService.showNotificationFlag) {
            ok = false
            items.add(TroubleshootItem("Persistent notification: off. Turn it on to keep Easy Voice running.",
                listOf(TroubleshootFix("Turn on persistent notification", action = { turnOnNotification() }))))
        } else {
            items.add(TroubleshootItem("Persistent notification: on."))
        }
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            items.add(TroubleshootItem("Notifications: off. Allow them to see the persistent notification.",
                listOf(TroubleshootFix("Allow notifications", action = { askForNotifications() }))))
        } else {
            items.add(TroubleshootItem("Notifications: allowed."))
        }
        // THE PHONE MAKER'S OWN AUTO-START LIST. Android has no API for it, and
        // whether Easy Voice is on it cannot be read by any app, so the screen is
        // offered whenever this phone has one. On those phones an engine off the
        // list cannot be started by another app either.
        val autoStart = autoStartFix()
        if (autoStart != null) {
            items.add(TroubleshootItem("Auto-start: allow Easy Voice and each voice engine, so they start again after a restart or Clear all.",
                listOf(autoStart)))
        }
        return TroubleshootFinding(TITLE_PERMISSIONS, ok, items)
    }

    private fun turnOnNotification() {
        EasyVoiceTtsService.showNotificationFlag = true
        LangStore.persistFlags(this)
        // Started now, while this screen shows, which Android allows.
        EasyVoiceTtsService.applyForegroundSetting(this)
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) askForNotifications()
        Toast.makeText(this, "Persistent notification is on.", Toast.LENGTH_LONG).show()
    }

    // Android 13+: the permission dialog (and the app's notification page if
    // Android will not show it again). Below 13 there is no permission, only the
    // user's own switch, on the notification page.
    private fun askForNotifications() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            try { notificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS); return } catch (_: Exception) {}
        }
        openFix(SystemScreens.notificationSettings(packageName))
    }

    // The first Auto-start screen this phone really has. The component list is the
    // one the AutoStarter library keeps (judemanutd/AutoStarter 1.1.0,
    // AutoStartPermissionHelper) -- read from its source, not guessed. The library
    // itself is not used: it picks the screen by Build.BRAND, which misses the
    // same screens on sister brands (Realme and OnePlus run Oppo's ColorOS,
    // iQOO is Vivo), and it has not been updated since 2021. Here every screen is
    // asked of the package manager instead, and only one that exists and may be
    // opened by another app (exported) gets a button.
    private fun autoStartFix(): TroubleshootFix? {
        for ((pkg, cls) in AUTO_START_SCREENS) {
            val intent = Intent().setComponent(android.content.ComponentName(pkg, cls))
            val found = try { packageManager.resolveActivity(intent, 0) } catch (_: Exception) { null }
            if (found?.activityInfo?.exported == true) return TroubleshootFix("Auto-start settings", intent)
        }
        return null
    }

    private fun isBackgroundRestricted(): Boolean {
        val manager = ContextCompat.getSystemService(this, ActivityManager::class.java) ?: return false
        return try { manager.isBackgroundRestricted } catch (_: Exception) { false }
    }

    private fun readableProblem(problem: String): String = when {
        problem.startsWith("init status") -> "did not start"
        problem.contains("listed 0 voices") -> "no languages installed"
        problem.contains("connection lost") -> "stopped answering"
        problem.startsWith("bound to") -> "connected to another engine"
        problem == "constructor threw" -> "could not start"
        else -> problem
    }

    private fun report(
        myGeneration: Int,
        reports: List<EngineFinder.EngineReport>,
        repaired: List<String>,
        speechProblems: Map<String, String>
    ) {
        if (myGeneration != generation) return
        lastInputs = ReportInputs(reports, repaired, speechProblems)
        result = buildResult(reports, repaired, speechProblems)
        working = false
        releaseTestClients()
    }

    private fun buildResult(
        reports: List<EngineFinder.EngineReport>,
        repaired: List<String>,
        speechProblems: Map<String, String>
    ): TroubleshootResult {
        val configured = configuredEngines()
        val findings = ArrayList<TroubleshootFinding>(systemFindings())
        val engineFindings = ArrayList<TroubleshootFinding>()
        var workingCount = 0
        var broken = 0
        val reported = HashSet<String>()
        for (report in reports) {
            reported.add(report.pkg)
            val setUp = configured[report.pkg].orEmpty()
            val count = languageCount(report.pkg)
            val silent = speechProblems[report.pkg] == NO_OWN_VOICE
            // ONLY ENGINES WITH A VOICE OF THEIR OWN (owner, 2026-09-24). Left
            // out: one that has never listed a voice, and one whose test came back
            // without sound -- unless a language of the user's is set up on it,
            // because then it is the reason that language is silent.
            if (setUp.isEmpty() && (count == 0 || silent)) continue
            val label = EngineFinder.engineLabel(this, report.pkg)
            val items = ArrayList<TroubleshootItem>()
            val problem = if (report.problem.isNotEmpty()) readableProblem(report.problem) else speechProblems[report.pkg]
            val installer = installDataFix(report.pkg)
            if (problem == null) {
                workingCount++
                items.add(TroubleshootItem(when (count) {
                    0 -> "Working."
                    1 -> "Working. 1 language."
                    else -> "Working. $count languages."
                }))
                // A language set up on it whose data is not on the phone yet (the
                // engine answered LANG_MISSING_DATA during the scan).
                val missing = EngineFinder.lastMissingData[report.pkg].orEmpty()
                if (missing.isNotEmpty() && installer != null) {
                    items.add(TroubleshootItem("Not downloaded: " + missing.joinToString(", ") { EngineFinder.languageName(it) } + ".", listOf(installer)))
                } else if (installer != null) {
                    items.add(TroubleshootItem("More languages can be downloaded.", listOf(installer)))
                }
            } else if (silent) {
                broken++
                items.add(TroubleshootItem("Not working: it has no voice of its own. Set these languages up on another engine."))
            } else {
                broken++
                if (report.voices == 0 && report.problem.isNotEmpty() && installer != null) {
                    items.add(TroubleshootItem("Not working: " + problem + ". Download its voice data, then run again.", listOf(installer)))
                } else {
                    items.add(TroubleshootItem("Not working: " + problem + ". Force stop it in App info, then run again.",
                        listOf(SystemScreens.appInfo(report.pkg))))
                }
            }
            items.add(batteryItem(report.pkg))
            if (setUp.isNotEmpty()) items.add(TroubleshootItem("Set up for: " + setUp.joinToString(", ") + "."))
            engineFindings.add(TroubleshootFinding(label, problem == null, dedupe(items)))
        }
        // Engines a language is set up on that the scan did not find at all:
        // uninstalled, turned off, or no longer a TTS engine.
        for ((pkg, setUp) in configured) {
            if (pkg in reported) continue
            broken++
            val label = EngineFinder.engineLabel(this, pkg)
            val appInfo = try { packageManager.getApplicationInfo(pkg, 0) } catch (_: Exception) { null }
            val items = ArrayList<TroubleshootItem>()
            when {
                appInfo == null -> items.add(TroubleshootItem("Not installed.",
                    listOf(TroubleshootFix("Open in Play Store", Intent(Intent.ACTION_VIEW, ("market://details?id=" + pkg).toUri())))))
                !appInfo.enabled -> items.add(TroubleshootItem("Turned off. Turn it on in App info.", listOf(SystemScreens.appInfo(pkg))))
                else -> items.add(TroubleshootItem("No longer a voice engine. Update it.", listOf(SystemScreens.appInfo(pkg))))
            }
            if (appInfo != null) items.add(batteryItem(pkg))
            items.add(TroubleshootItem("Set up for: " + setUp.joinToString(", ") + "."))
            engineFindings.add(TroubleshootFinding(label, false, dedupe(items)))
        }
        findings.addAll(engineFindings.sortedBy { it.ok })
        val summary = StringBuilder("Done. ")
        summary.append(workingCount).append(" working")
        if (broken > 0) summary.append(", ").append(broken).append(" need attention")
        summary.append(".")
        if (engineFindings.isEmpty()) summary.append(" No voice engine found.")
        return TroubleshootResult(summary.toString(), repaired, findings)
    }

    // One button per label in a finding: two lines may offer the same screen
    // (App info), and two buttons with one name are one too many for a reader.
    private fun dedupe(items: List<TroubleshootItem>): List<TroubleshootItem> {
        val labels = HashSet<String>()
        return items.map { item -> item.copy(fixes = item.fixes.filter { labels.add(it.label) }) }
    }

    companion object {
        // Android's own TTS settings screen. There is no public constant for it
        // (checked in API 37's Settings); the menu and this screen share this one.
        const val TTS_SETTINGS_ACTION = "com.android.settings.TTS_SETTINGS"
        const val TITLE_PREFERRED = "Preferred engine"
        const val TITLE_PERMISSIONS = "Easy Voice permissions"
        private const val NO_OWN_VOICE = "no voice of its own"
        private const val WAV_HEADER = 44
        // Engines whose test came back without sound, with the version that did,
        // for the life of the process: tested again only after an update.
        private val silentEngines = java.util.concurrent.ConcurrentHashMap<String, Long>()
        // OEM Auto-start screens, package to activity, in AutoStarter 1.1.0's
        // order: Xiaomi, Letv, Asus (two), Huawei and Honor (two), Oppo (three),
        // Vivo (three), Nokia, Samsung (three), OnePlus.
        private val AUTO_START_SCREENS = listOf(
            "com.miui.securitycenter" to "com.miui.permcenter.autostart.AutoStartManagementActivity",
            "com.letv.android.letvsafe" to "com.letv.android.letvsafe.AutobootManageActivity",
            "com.asus.mobilemanager" to "com.asus.mobilemanager.powersaver.PowerSaverSettings",
            "com.asus.mobilemanager" to "com.asus.mobilemanager.autostart.AutoStartActivity",
            "com.huawei.systemmanager" to "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity",
            "com.huawei.systemmanager" to "com.huawei.systemmanager.optimize.process.ProtectActivity",
            "com.coloros.safecenter" to "com.coloros.safecenter.permission.startup.StartupAppListActivity",
            "com.oppo.safe" to "com.oppo.safe.permission.startup.StartupAppListActivity",
            "com.coloros.safecenter" to "com.coloros.safecenter.startupapp.StartupAppListActivity",
            "com.iqoo.secure" to "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity",
            "com.vivo.permissionmanager" to "com.vivo.permissionmanager.activity.BgStartUpManagerActivity",
            "com.iqoo.secure" to "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager",
            "com.evenwell.powersaving.g3" to "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity",
            "com.samsung.android.lool" to "com.samsung.android.sm.ui.battery.BatteryActivity",
            "com.samsung.android.lool" to "com.samsung.android.sm.battery.ui.usage.CheckableAppListActivity",
            "com.samsung.android.lool" to "com.samsung.android.sm.battery.ui.BatteryActivity",
            "com.oneplus.security" to "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
        )
    }
}

@Composable
fun TroubleshootScreen(
    working: Boolean,
    progress: String,
    result: TroubleshootResult?,
    onRunAgain: () -> Unit,
    onFix: (TroubleshootFix) -> Unit
) {
    ResponsiveContent {
        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            SectionHeader("Troubleshoot voice engines")
            if (working || result == null) {
                if (animationsEnabled()) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).clearAndSetSemantics { }
                    )
                }
                // Polite: the reader hears each engine as it is read, and is not
                // interrupted.
                Text(
                    text = progress,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                        .semantics { liveRegion = LiveRegionMode.Polite }
                )
                return@Column
            }
            Text(
                text = result.summary,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                    .semantics { liveRegion = LiveRegionMode.Polite }
            )
            if (result.repaired.isNotEmpty()) {
                SectionHeader("Fixed automatically")
                for (line in result.repaired) SettingDescription(line)
            }
            for (finding in result.findings) {
                SectionHeader(finding.title)
                // Each line, then the buttons that fix it, so a button is met
                // right after the line it answers.
                for (item in finding.items) {
                    SettingDescription(item.text)
                    for (fix in item.fixes) {
                        EvButton(
                            label = fix.label + ", " + finding.title,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            outlined = true,
                            onClick = { onFix(fix) }
                        )
                    }
                }
            }
            OptionGap()
            EvButton(
                label = "Run again",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                iconRes = R.drawable.ic_restore,
                onClick = onRunAgain
            )
        }
    }
}
