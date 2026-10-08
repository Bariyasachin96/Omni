package com.sachinbaria.easyvoice

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.ParcelFileDescriptor
import android.speech.tts.TextToSpeech
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

// EVERY VOICE ENGINE IS SEEN WITHOUT QUERY_ALL_PACKAGES (owner, 2026-10-08:
// "library ke rule ke hisab se"). The Play-restricted permission went; what makes
// the engines visible now is the manifest's <queries> TTS_SERVICE intent, which
// AOSP's AppsFilter matches against each app's own manifest. This proves it on a
// real device rather than from the source: the SHELL, which may see every app,
// lists the TTS services, and the app's own discovery must find exactly the same
// packages -- and then every package-manager question the app asks about an
// engine (isInstalled, resolveService) must be answered for each of them.
@RunWith(AndroidJUnit4::class)
class PackageVisibilityTest {
    @Test
    fun everyEngineIsVisibleThroughQueries() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val ctx: Context = instrumentation.targetContext
        val pm = ctx.packageManager
        // The app really does run without it.
        assertEquals(PackageManager.PERMISSION_DENIED,
            pm.checkPermission("android.permission.QUERY_ALL_PACKAGES", ctx.packageName))

        // The same three queries EngineFinder makes (MATCH_ALL, GET_META_DATA,
        // none), asked as the shell; --components prints one "package/.Service"
        // line per service.
        val shellEngines = sortedSetOf<String>()
        val output = StringBuilder()
        for (flags in intArrayOf(PackageManager.MATCH_ALL, PackageManager.GET_META_DATA, 0)) {
            val command = "pm query-services --components --query-flags " + flags +
                " -a " + TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE
            val text = ParcelFileDescriptor.AutoCloseInputStream(
                instrumentation.uiAutomation.executeShellCommand(command)).bufferedReader().use { it.readText() }
            output.append(text)
            text.lines().map { it.trim() }.filter { it.contains('/') }
                .forEach { shellEngines.add(it.substringBefore('/')) }
        }
        val appEngines = EngineFinder.installedEngines(ctx, withLabels = false).map { it.pkg }.toSortedSet()

        // Easy Voice is an engine itself, so neither list can be empty here.
        assertTrue("the shell lists no TTS service: $output", shellEngines.contains(ctx.packageName))
        assertEquals("the app must see every engine the shell sees", shellEngines, appEngines)
        for (pkg in appEngines) {
            assertTrue("isInstalled($pkg)", EngineFinder.isInstalled(ctx, pkg))
            assertNotNull("resolveService($pkg)", pm.resolveService(
                Intent(TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE).setPackage(pkg), 0))
        }
    }
}
