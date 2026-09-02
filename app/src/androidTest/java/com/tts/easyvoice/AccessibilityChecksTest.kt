package com.tts.easyvoice

import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.tryPerformAccessibilityChecks
// NOT androidx.compose.ui.test -- enableAccessibilityChecks lives in its own
// package, androidx.compose.ui.test.junit4.accessibility, because it ships in
// the separate ui-test-junit4-accessibility artifact and extends ComposeTestRule
// rather than SemanticsNodeInteraction. Importing it from androidx.compose.ui.test
// alongside tryPerformAccessibilityChecks compiles for the latter and fails for
// this one, which is exactly how run 785 and 786 failed. Verified against the
// declaration in ComposeTestRuleExt.android.kt, not guessed.
import androidx.compose.ui.test.junit4.accessibility.enableAccessibilityChecks
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
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
//
// enableAccessibilityChecks is @RequiresApi(34) and the checks are a no-op below
// that, so this is gated rather than left to run and quietly pass on an older
// device: SdkSuppress reports it as SKIPPED there, which is the truth, while a
// silent pass would be a lie. The workflow's emulator is API 34.
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 34)
@RequiresApi(34)
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
        // These are companion statics, so they survive from one test to the
        // next in the same process -- and the mode ints decide whether the
        // "Specific language for ..." dropdowns are on screen at all. Reset
        // them here or the tests depend on the order JUnit happens to run in.
        EasyVoiceTtsService.numberModeInt = 0
        EasyVoiceTtsService.punctuationModeInt = 0
        EasyVoiceTtsService.emojiModeInt = 0
        EasyVoiceTtsService.numberSpecificLang = "eng"
        EasyVoiceTtsService.puncSpecificLang = "eng"
        EasyVoiceTtsService.emojiSpecificLang = "eng"
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

    // ======================================================================
    //  THE FRONT DOOR
    //  MainScreen was the one screen with no test, and it is the first thing a
    //  blind user meets: the startup scan, then the tab strip and whichever page
    //  is selected. Both of its states are checked, because they share no views
    //  at all -- the scan state is a centred column with a live region, and the
    //  settled state is the app bar plus PrimaryTabRow plus a page.
    //
    //  appIcon is null on purpose. The real one is loaded from PackageManager,
    //  and its Image carries contentDescription = null because the app name is
    //  written beside it; passing null exercises the same branch without needing
    //  a bitmap, and the label under test is the Text, not the icon.
    // ======================================================================

    private fun mainScreen(scanning: Boolean, scanLine: String) = @androidx.compose.runtime.Composable {
        MainScreen(
            prefs = prefs(),
            scanning = scanning,
            scanLine = scanLine,
            modeRefresh = 0,
            appIcon = null,
            onOpenModeSettings = { },
            onAddLanguage = { },
            onLanguage = { },
            onDeleteConfiguration = { },
            onDisableLanguage = { },
            requestNotificationPermission = { },
            launchImportPicker = { }
        )
    }

    @Test
    fun mainScreenScanning() {
        check(mainScreen(scanning = true, scanLine = "Checking your TTS engines"))
    }

    @Test
    fun mainScreenSettled() {
        check(mainScreen(scanning = false, scanLine = ""))
    }

    // ======================================================================
    //  THE POPUPS
    //  Everything above renders one composition and checks what is on screen,
    //  which means a menu that has not been opened is never looked at. That was
    //  the whole of the gap: the language dropdown -- the control a blind user
    //  spends the longest in, because it can hold every language an engine
    //  speaks -- the per-language overflow menu, and the required-engines
    //  dialog were all unchecked.
    //
    //  A popup lives in its OWN view, so onRoot().tryPerformAccessibilityChecks()
    //  would not reach it. What does reach it is that enableAccessibilityChecks
    //  runs the checks before every action performed through the test API: the
    //  click that opens the menu checks the screen behind it, and a click on
    //  something INSIDE the open menu checks the menu's own view.
    // ======================================================================

    // "Select secondary language, Hindi (hin)" -- the label, then the value, as
    // LabeledDropdown names its anchor.
    @Test
    fun languageDropdownOpen() {
        rule.setContent { EasyVoiceTheme { ModeSettingsScreen(prefs(), "dual") } }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("Select secondary language, Hindi (hin)").performClick()
        // Acting on an item is what puts the OPEN menu through the checks.
        rule.onNodeWithContentDescription("Gujarati (guj)").performClick()
    }

    // Mode 3 is a screen STATE nothing else covers: choosing "Specific language"
    // for numbers reveals a second dropdown, and with all three set it reveals
    // three. None of the four mode-settings tests above ever renders them,
    // because the seed leaves every mode int at 0.
    @Test
    fun specificLanguageDropdownOpen() {
        EasyVoiceTtsService.numberModeInt = 3
        EasyVoiceTtsService.punctuationModeInt = 3
        EasyVoiceTtsService.emojiModeInt = 3
        rule.setContent { EasyVoiceTheme { ModeSettingsScreen(prefs(), "dual") } }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("Specific language for reading numbers, English (eng)")
            .performClick()
        rule.onNodeWithContentDescription("Hindi (hin)").performClick()
    }

    @Test
    fun configurationRowMenuOpen() {
        rule.setContent {
            EasyVoiceTheme {
                ConfigurationScreen(
                    labels = listOf("English (eng)", "Hindi (hin)"),
                    engines = listOf("com.google.android.tts", ""),
                    onLanguage = { }, onDeleteConfiguration = { }, onDisable = { }
                )
            }
        }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("More actions for English (eng)").performClick()
        rule.onNodeWithContentDescription("Disable language").performClick()
    }

    // Both rows: one engine installed, one not, so the "Install" button and the
    // green "Installed" label are both on screen. Apply is disabled while
    // anything is missing, and a disabled control still has to be labelled.
    @Test
    fun requiredEnginesDialog() {
        check {
            RequiredEnginesDialog(
                items = listOf(
                    RequiredEnginesItem("Google Speech Services", "com.google.android.tts", true),
                    RequiredEnginesItem("Samsung Text-to-Speech", "com.samsung.SMT", false)
                ),
                onInstall = { }, onApply = { }, onCancel = { }
            )
        }
    }
}
