package com.sachinbaria.easyvoice

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.activity.compose.setContent
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
import androidx.core.content.ContextCompat
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
// ==========================================================================

data class TroubleshootFix(val label: String, val intent: Intent)
data class TroubleshootFinding(
    val title: String,
    val ok: Boolean,
    val lines: List<String>,
    val fixes: List<TroubleshootFix> = emptyList()
)
data class TroubleshootResult(
    val summary: String,
    val repaired: List<String>,
    val findings: List<TroubleshootFinding>
)

class TroubleshootActivity : EvActivity() {
    private val handler = HandlerCompat.createAsync(Looper.getMainLooper())
    private var working by mutableStateOf(true)
    private var progress by mutableStateOf("")
    private var result by mutableStateOf<TroubleshootResult?>(null)
    // Bumped by every run and by onDestroy, so a late callback from an earlier
    // run (or from a destroyed screen) touches nothing.
    private var generation = 0
    private val testClients = ArrayList<TextToSpeech>()

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
        runChecks()
    }

    // Coming back from a system screen (battery, TTS settings, app info): every
    // line that reads system state -- the preferred engine, Easy Voice's and each
    // engine's battery optimization, which installer exists -- is read again, so
    // the report says whether the fix took without a whole new run.
    override fun onResume() {
        super.onResume()
        if (working || result == null) return
        val inputs = lastInputs ?: return
        result = buildResult(inputs.reports, inputs.repaired, inputs.speechProblems)
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
        for (client in testClients) { try { client.shutdown() } catch (_: Exception) {} }
        testClients.clear()
    }

    private fun openFix(fix: TroubleshootFix) {
        try {
            startActivity(fix.intent)
        } catch (_: Exception) {
            // No Play Store app: the same page on the web, as MainActivity does.
            val data = fix.intent.data
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
        progress = "Reading every voice engine on this phone"
        EngineFinder.scanLanguages(
            this,
            onProgress = { line ->
                val pkg = line.removePrefix("Scanning ").substringBefore("...")
                runOnUiThread { if (myGeneration == generation) progress = "Reading " + EngineFinder.engineLabel(this, pkg) }
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
            repaired.add("Easy Voice restarted every voice engine that had stopped responding")
        }
        val toTest = reports.filter { it.problem.isEmpty() }
        val speechProblems = HashMap<String, String>()
        if (toTest.isEmpty()) { report(myGeneration, reports, repaired, speechProblems); return }
        progress = "Testing each engine with a short sentence"
        var remaining = toTest.size
        for (report in toTest) {
            testEngine(myGeneration, report.pkg) { problem ->
                if (problem != null) {
                    speechProblems[report.pkg] = problem
                    EasyVoiceTtsService.engineUnresponsive(report.pkg)
                }
                remaining--
                if (remaining == 0) report(myGeneration, reports, repaired, speechProblems)
            }
        }
    }

    // One engine, one fresh client, one silent sentence. Every callback is handed
    // to the main looper: onInit's ERROR and every utterance callback arrive on
    // binder threads.
    private fun testEngine(myGeneration: Int, pkg: String, onDone: (String?) -> Unit) {
        val cell = arrayOfNulls<TextToSpeech>(1)
        val file = java.io.File(cacheDir, "troubleshoot_" + Integer.toHexString(pkg.hashCode()) + ".wav")
        var finished = false
        lateinit var timeout: Runnable
        fun finishTest(problem: String?) {
            if (finished) return
            finished = true
            handler.removeCallbacks(timeout)
            try { cell[0]?.shutdown() } catch (_: Exception) {}
            cell[0]?.let { testClients.remove(it) }
            try { file.delete() } catch (_: Exception) {}
            if (myGeneration == generation) onDone(problem)
        }
        timeout = Runnable { finishTest("it accepted a test sentence and never finished it") }
        fun onInitMain(status: Int) {
            if (finished) return
            val client = cell[0]
            if (status != TextToSpeech.SUCCESS || client == null) { finishTest("it refused to start"); return }
            if (EngineFinder.boundEngineOf(client, pkg) != pkg) { finishTest("Android connected to another engine instead of it"); return }
            val locale = configuredLocaleFor(pkg)
            val iso3 = if (locale != null && client.setLanguage(locale) >= TextToSpeech.LANG_AVAILABLE) EngineFinder.iso3Of(locale)
                       else EngineFinder.iso3Of(try { client.voice?.locale } catch (_: Exception) { null })
            val sentence = SampleTexts.get(iso3).ifEmpty { "Easy Voice test." }
            client.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) { handler.post { finishTest(null) } }
                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) { handler.post { finishTest("it failed on a test sentence") } }
                override fun onError(utteranceId: String?, errorCode: Int) {
                    handler.post { finishTest("it failed on a test sentence (error " + errorCode + ")") }
                }
            })
            val queued = try { client.synthesizeToFile(sentence, Bundle(), file, "ev_troubleshoot") } catch (_: Exception) { TextToSpeech.ERROR }
            if (queued != TextToSpeech.SUCCESS) finishTest("it refused a test sentence")
        }
        try {
            cell[0] = TextToSpeech(applicationContext, { status ->
                if (Looper.myLooper() == Looper.getMainLooper()) onInitMain(status) else handler.post { onInitMain(status) }
            }, pkg)
            cell[0]?.let { testClients.add(it) }
            handler.postDelayed(timeout, 20000L)
        } catch (_: Exception) {
            finishTest("Android could not start it")
        }
    }

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
            if (pkg.isEmpty() || pkg.equals("disable", true) || pkg == packageName) continue
            byEngine.getOrPut(pkg) { ArrayList() }.add(EngineFinder.languageName(key))
        }
        return byEngine
    }

    private fun appInfo(pkg: String) =
        TroubleshootFix("App info", Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, ("package:" + pkg).toUri()))
    private fun batteryFix() =
        TroubleshootFix("Battery optimization settings", Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
    private fun ttsSettingsFix() =
        TroubleshootFix("TTS Settings", Intent(TTS_SETTINGS_ACTION))
    // Asked of PowerManager, which answers for ANY package, not only our own.
    private fun ignoresBatteryOptimization(pkg: String): Boolean {
        val power = ContextCompat.getSystemService(this, PowerManager::class.java) ?: return true
        return try { power.isIgnoringBatteryOptimizations(pkg) } catch (_: Exception) { true }
    }
    // The engine's own "install voice data" screen, but only when the engine
    // really has one: the system is asked to resolve the intent, and an engine
    // that declares no such activity gets no download line and no button,
    // because there would be nothing for the button to open.
    private fun installDataFix(pkg: String): TroubleshootFix? {
        val intent = Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA).setPackage(pkg)
        val found = try { packageManager.resolveActivity(intent, 0) } catch (_: Exception) { null }
        return if (found != null) TroubleshootFix("Download languages", intent) else null
    }
    // The languages an engine speaks, from the voices the scan just read, as
    // names ("Hindi, Gujarati") -- a language counts once however many voices
    // it has.
    private fun languagesOf(pkg: String): List<String> =
        EngineFinder.lastScanVoices.asSequence()
            .filter { it.pkg == pkg && !it.notFound }
            .map { EngineFinder.iso3Of(it.locale) }
            .filter { it != "zxx" }
            .distinct()
            .map { EngineFinder.languageName(it) }
            .distinct()
            .sortedWith(java.text.Collator.getInstance())
            .toList()
    // One line per engine saying where it stands with battery optimization, and
    // the system list as the fix when it is on.
    private fun addBatteryStatus(pkg: String, lines: MutableList<String>, fixes: MutableList<TroubleshootFix>) {
        if (ignoresBatteryOptimization(pkg)) {
            lines.add("Battery optimization: off. Android will not stop it to save battery.")
        } else {
            lines.add("Battery optimization: on. Android can stop it in the background, and speech in its languages can stop with it. Set it to Unrestricted.")
            fixes.add(batteryFix())
        }
    }

    private fun systemFindings(): List<TroubleshootFinding> {
        val out = ArrayList<TroubleshootFinding>()
        val preferred = try { Settings.Secure.getString(contentResolver, Settings.Secure.TTS_DEFAULT_SYNTH) } catch (_: Exception) { null }
        if (preferred != null && preferred != packageName) {
            out.add(TroubleshootFinding(TITLE_PREFERRED, false,
                listOf("The phone's preferred engine is " + EngineFinder.engineLabel(this, preferred) +
                    ", so screen readers are not speaking through Easy Voice. Choose Easy Voice as the preferred engine."),
                listOf(ttsSettingsFix())))
        }
        val restricted = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P && isBackgroundRestricted()
        if (restricted || !ignoresBatteryOptimization(packageName)) {
            val lines = ArrayList<String>()
            if (restricted) lines.add("Background use is restricted for Easy Voice, so Android can stop it while you use the phone.")
            else lines.add("Battery optimization: on for Easy Voice. Android can stop it in the background, and speech stops with it. Set Easy Voice to Unrestricted.")
            out.add(TroubleshootFinding(TITLE_BATTERY, false, lines,
                if (restricted) listOf(appInfo(packageName)) else listOf(batteryFix())))
        }
        return out
    }

    private fun isBackgroundRestricted(): Boolean {
        val manager = ContextCompat.getSystemService(this, ActivityManager::class.java) ?: return false
        return try { manager.isBackgroundRestricted } catch (_: Exception) { false }
    }

    private fun readableProblem(problem: String): String = when {
        problem.startsWith("init status") -> "it refused to start"
        problem.contains("listed 0 voices") -> "it has no languages installed"
        problem.contains("connection lost") -> "it stopped answering while its languages were being read"
        problem.startsWith("bound to") -> "Android connected to another engine instead of it"
        problem == "constructor threw" -> "Android could not start it"
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
            val label = EngineFinder.engineLabel(this, report.pkg)
            val setUp = configured[report.pkg].orEmpty()
            val lines = ArrayList<String>()
            val fixes = ArrayList<TroubleshootFix>()
            val problem = if (report.problem.isNotEmpty()) readableProblem(report.problem) else speechProblems[report.pkg]
            val installer = installDataFix(report.pkg)
            if (problem == null) {
                workingCount++
                val spoken = languagesOf(report.pkg)
                lines.add(when (spoken.size) {
                    0 -> "Working."
                    1 -> "Working. It speaks " + spoken[0] + "."
                    else -> "Working. It speaks " + spoken.size + " languages: " + spoken.joinToString(", ") + "."
                })
                // A language set up on it whose data is not on the phone yet (the
                // engine answered LANG_MISSING_DATA during the scan). Said only
                // when the engine has a download screen to open.
                val missing = EngineFinder.lastMissingData[report.pkg].orEmpty()
                if (missing.isNotEmpty() && installer != null) {
                    lines.add("Not downloaded yet: " + missing.joinToString(", ") { EngineFinder.languageName(it) } + ".")
                    fixes.add(installer)
                }
            } else {
                broken++
                lines.add("Not working: " + problem + ".")
                if (report.voices == 0 && report.problem.isNotEmpty() && installer != null) {
                    lines.add("Download its languages, then run this check again.")
                    fixes.add(installer)
                } else {
                    lines.add("Open its app info and choose Force stop, then run this check again. If it still fails, update the engine.")
                }
                fixes.add(appInfo(report.pkg))
            }
            addBatteryStatus(report.pkg, lines, fixes)
            if (setUp.isNotEmpty()) lines.add("Languages set up on it in Easy Voice: " + setUp.joinToString(", ") + ".")
            engineFindings.add(TroubleshootFinding(label, problem == null, lines, fixes))
        }
        // Engines a language is set up on that the scan did not find at all:
        // uninstalled, turned off, or no longer a TTS engine.
        for ((pkg, setUp) in configured) {
            if (pkg in reported) continue
            broken++
            val label = EngineFinder.engineLabel(this, pkg)
            val appInfo = try { packageManager.getApplicationInfo(pkg, 0) } catch (_: Exception) { null }
            val lines = ArrayList<String>()
            val fixes = ArrayList<TroubleshootFix>()
            when {
                appInfo == null -> {
                    lines.add("Not installed. Install it again, or choose another voice for these languages.")
                    fixes.add(TroubleshootFix("Open in Play Store", Intent(Intent.ACTION_VIEW, ("market://details?id=" + pkg).toUri())))
                }
                !appInfo.enabled -> {
                    lines.add("It is turned off. Turn it on in its app info.")
                    fixes.add(appInfo(pkg))
                }
                else -> {
                    lines.add("It is installed but no longer offers a voice engine. Update it, or choose another voice for these languages.")
                    fixes.add(appInfo(pkg))
                }
            }
            if (appInfo != null) addBatteryStatus(pkg, lines, fixes)
            lines.add("Languages set up on it in Easy Voice: " + setUp.joinToString(", ") + ".")
            engineFindings.add(TroubleshootFinding(label, false, lines, fixes))
        }
        findings.addAll(engineFindings.sortedBy { it.ok })
        val summary = StringBuilder("Check finished. ")
        summary.append(workingCount).append(if (workingCount == 1) " engine is working" else " engines are working")
        if (broken > 0) summary.append(", ").append(broken).append(if (broken == 1) " needs attention" else " need attention")
        summary.append(".")
        if (reports.isEmpty()) summary.append(" No voice engine answered. Install a voice engine, such as Speech Services by Google.")
        return TroubleshootResult(summary.toString(), repaired, findings)
    }

    companion object {
        // Android's own TTS settings screen. There is no public constant for it
        // (checked in API 37's Settings); the menu and this screen share this one.
        const val TTS_SETTINGS_ACTION = "com.android.settings.TTS_SETTINGS"
        const val TITLE_PREFERRED = "Preferred engine"
        const val TITLE_BATTERY = "Easy Voice in the background"
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
                for (line in finding.lines) SettingDescription(line)
                for (fix in finding.fixes) {
                    EvButton(
                        label = fix.label + ", " + finding.title,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        outlined = true,
                        onClick = { onFix(fix) }
                    )
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
