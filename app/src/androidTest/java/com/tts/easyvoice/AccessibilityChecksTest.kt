package com.tts.easyvoice

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.tryPerformAccessibilityChecks
import androidx.compose.ui.test.enableAccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

// Every screen, through Google's Accessibility Test Framework.
//
// This is the same engine that powers Accessibility Scanner, and it checks the
// four things this project has been checking BY HAND for months, one screen at
// a time, in CLAUDE.md: missing labels, colour contrast, touch target size and
// traversal order. Doing it by hand is how the contrast ratios in that document
// got computed and how every contentDescription got audited. A test does it on
// every push instead, and does not get tired.
//
// It needs a real device or emulator at API 34 or above -- the checks are a
// no-op under Robolectric -- which is why the workflow runs this in its own job
// with an emulator. That job failing FAILS THE RUN; it is separate only so it
// runs in parallel with the APK build.
//
// WHAT THIS DOES NOT REPLACE. The framework cannot tell whether a label is the
// RIGHT label, whether a heading is in the right place, or whether the reading
// order makes sense to someone who cannot see the screen. Everything in
// docs/INVARIANTS.md #6, #7, #9 and #18 is still the owner's ear and a careful
// read. This catches the mechanical half so the judgement half gets the
// attention.
@RunWith(AndroidJUnit4::class)
class AccessibilityChecksTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    // The screens read shared statics and the scan results, so an unseeded run
    // would render empty lists and check almost nothing. This puts a small but
    // realistic configuration in place -- three languages across two engines,
    // which is the shape the owner actually runs (English on a dedicated
    // engine, Gujarati and Hindi on Google).
    @Before
    fun seed() {
        val context = rule.activity.applicationContext
        LangStore.ensureLoaded(context)

        EngineFinder.lastScanVoices = listOf(
            EngineFinder.ScanVoice(
                "com.google.android.tts", "Google Speech Services",
                Locale("en", "US"), arrayListOf("*Default")
            ),
            EngineFinder.ScanVoice(
                "com.google.android.tts", "Google Speech Services",
                Locale("hi", "IN"), arrayListOf("*Default")
            ),
            EngineFinder.ScanVoice(
                "com.google.android.tts", "Google Speech Services",
                Locale("gu", "IN"), arrayListOf("*Default")
            )
        )
        EngineFinder.lastScanEngines = listOf("com.google.android.tts")

        synchronized(LangStore.languages) {
            LangStore.languages.clear()
            LangStore.languages.add(entry("English", "eng", "en_US"))
            LangStore.languages.add(entry("Hindi", "hin", "hi_IN"))
            LangStore.languages.add(entry("Gujarati", "guj", "gu_IN"))
        }

        EasyVoiceTtsService.autoLang = "eng"
        EasyVoiceTtsService.dualLang = "hin"
        EasyVoiceTtsService.mixLatinLang = "eng"
        EasyVoiceTtsService.mixNonLatinLang = "hin"
    }

    private fun entry(name: String, iso3: String, tag: String) =
        LangEntry(name, iso3, 100, 100, 100, "com.google.android.tts", tag, "*Default")

    // enableAccessibilityChecks() also runs on every action, so a screen with no
    // interaction still needs the explicit call to be checked at all.
    private fun check(content: @androidx.compose.runtime.Composable () -> Unit) {
        rule.setContent { EasyVoiceTheme { content() } }
        rule.enableAccessibilityChecks()
        rule.onRoot().tryPerformAccessibilityChecks()
    }

    private fun prefs() = SharedPrefsManager(rule.activity)

    @Test
    fun modesList() {
        check { ModesScreen(prefs(), 0) { } }
    }

    // One per mode, because the settings screen shows a different set of
    // controls for each and they are built by different branches: dual has one
    // dropdown, mix and multilingual have the Latin/non-Latin pair, and auto
    // hides the numbers-punctuation-emojis block entirely.
    @Test
    fun dualModeSettings() {
        check { ModeSettingsScreen(prefs(), "dual") }
    }

    @Test
    fun autoModeSettings() {
        check { ModeSettingsScreen(prefs(), "auto") }
    }

    @Test
    fun mixedModeSettings() {
        check { ModeSettingsScreen(prefs(), "mix") }
    }

    @Test
    fun multilingualModeSettings() {
        check { ModeSettingsScreen(prefs(), "multilingual") }
    }

    @Test
    fun advancedTab() {
        check { AdvancedScreen(prefs(), 0, { }, { }) }
    }

    @Test
    fun configurationTab() {
        check {
            ConfigurationScreen(
                labels = listOf("English (eng)", "Hindi (hin)", "Gujarati (guj)"),
                engines = listOf("com.google.android.tts", "com.google.android.tts", ""),
                onLanguage = { }, onDeleteConfiguration = { }, onDisable = { }
            )
        }
    }

    // The one screen with an empty state worth checking as well: with no engine
    // chosen the voice list collapses to an explanatory line, and that line has
    // to be reachable and readable too.
    @Test
    fun voiceSetup() {
        check { VoiceScreen(prefs(), 0, 3, { }) { null } }
    }

    @Test
    fun languagesList() {
        check { LanguagesScreen(prefs()) }
    }
}
