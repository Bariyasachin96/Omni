package com.tts.easyvoice

import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
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
import org.junit.After
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
    private val deviceLocale: Locale = Locale.getDefault()

    @Before
    fun seed() {
        val context = rule.activity.applicationContext
        // THE DEVICE LOCALE DECIDES WHICH LIST BRANCH RENDERS, so it is
        // pinned here for the same reason the reading mode is. The
        // Languages list groups the device REGION's languages above the
        // rest, and it only groups when both sides are non-empty --
        // `regionLangs` is built from voices whose `locale.country` equals
        // `Locale.getDefault().country`. So on an en_US image the GROUPED
        // branch renders and the flat one never does, and on an image with
        // no country it is the other way round. Neither is a choice we
        // were making. en_US is stated, `languagesListUngrouped` states
        // the other, and each asserts which branch it got.
        Locale.setDefault(Locale("en", "US"))
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
        // THE READING MODE HAS TO BE STATED, and leaving it out was a real hole
        // rather than an omission. `prefs.getReadingMode()` reads
        // `EasyVoiceTtsService.modeInt`, whose default is 0 = "none", and
        // `LangStore.ensureLoaded` may then set it from `auto_mode` -- which is
        // 3 when Google TTS is on the emulator image and 0 when it is not. So
        // the mode was whatever the device and the previous test left behind,
        // and `LanguagesScreen` answers "none" and "dual" with a one-line
        // "not available" message INSTEAD of the list. The 137-row list, its
        // search field, its filter chip and its buttons could therefore have
        // been going unchecked without anything saying so.
        //
        // Mix is the mode to seed: it is the one the owner runs, it has a
        // language list, and it is the mode in which MainScreen shows the
        // "Add language" button. Tests that need another mode set it themselves.
        EasyVoiceTtsService.modeInt = 4
        EasyVoiceTtsService.dedicatedEnginesFlag = false
        EasyVoiceTtsService.stripAudioAttrFlag = false
        EasyVoiceTtsService.forceAccessibilityFlag = false
        EasyVoiceTtsService.keepAliveFlag = false
        EasyVoiceTtsService.showNotificationFlag = false
        EasyVoiceTtsService.disableAdvancedFlag = false
        EasyVoiceTtsService.quickCharacterFlag = false
        EasyVoiceTtsService.punctuationInFlowFlag = true
        EasyVoiceTtsService.smartNumberFlag = false
        EasyVoiceTtsService.smartNumberGroupSize = 1
    }

    @After
    fun restoreLocale() {
        Locale.setDefault(deviceLocale)
    }

    private fun entry(name: String, iso3: String, tag: String) =
        LangEntry(name, iso3, 100, 100, 100, "com.google.android.tts", tag, "*Default")

    // EVERY SCREEN IS RENDERED TWICE THROUGH THE SCHEME PARAMETER, and since
    // 2026-09-22 the two passes are two real schemes again: the app follows the
    // phone, so a colour is only half measured until it has been measured in
    // LIGHT and in DARK.
    //
    // The parameter is why this is possible at all. An emulator image is in
    // exactly one mode, so leaving the scheme to the device would mean one of
    // the two never being rendered under ATF with nothing saying which -- the
    // same hole the reading mode, the Languages list layout and the locale each
    // turned out to have, and each of which is stated in seed() for the same
    // reason. It kept earning its place through the five days the app had one
    // scheme, because the sweep's assertion pinned that scheme and needed both
    // passes to make the point. Read the note in sweepSchemes().
    //
    // setContent can only be called once per test, so the switch is a state
    // object the composition reads: flipping it recomposes the same tree into
    // the other scheme, and every remembered value -- a typed query, an open
    // menu -- survives, so an interaction test keeps whatever it set up.
    private val darkScheme = mutableStateOf(false)

    // What the composition ACTUALLY resolved `background` to on its last pass.
    // Written from inside the theme, which is the only place a ColorScheme can
    // be read, and it is what lets sweepSchemes() prove the flip landed.
    private var composedBackground: ULong = 0uL

    private fun themed(content: @androidx.compose.runtime.Composable () -> Unit) {
        rule.setContent {
            EasyVoiceTheme(darkTheme = darkScheme.value) {
                composedBackground = MaterialTheme.colorScheme.background.value
                content()
            }
        }
    }

    private fun sweepSchemes() {
        darkScheme.value = false
        rule.waitForIdle()
        val light = composedBackground
        rule.onRoot().tryPerformAccessibilityChecks()

        darkScheme.value = true
        rule.waitForIdle()
        val dark = composedBackground
        rule.onRoot().tryPerformAccessibilityChecks()

        // THE SWEEP HAS TO PROVE WHAT IT SWEPT, and what it has to prove has now
        // turned over twice -- read this before editing it a third time.
        //
        // It asserted `light != dark` until 2026-09-17, because the app followed
        // the phone and the two passes really were two schemes. Without that, a
        // flip that stopped reaching the composition would have made every
        // "light" check a second dark one and the run would still have gone
        // green. On 2026-09-17 the app became pitch black in EVERY system mode,
        // one scheme, so that assertion would have failed all 29 tests on the app
        // being CORRECT, and it was replaced by `light == dark && dark is black`.
        //
        // The owner has now asked for the app to follow the phone again
        // (2026-09-22: "jab ham system se light mode karte hain to app light mode
        // mein nahin jaati hai"), so there are two schemes and the original
        // assertion is the right one again -- with the pitch-black check KEPT and
        // pointed at the dark pass, because the 2026-09-17 request was not
        // reversed, it became the dark half.
        //
        // Three things have to hold, and each catches a different regression:
        //   * the two passes DIFFER -- so a flip that stops reaching the
        //     composition, or a collapse back to one scheme, cannot leave half
        //     this run silently measuring the same palette twice;
        //   * the dark pass is pitch black -- so a theme edit that moves the dark
        //     `background` off #000000 is caught even though every contrast ratio
        //     would still pass;
        //   * the light pass is pure white -- the same guard for the other half,
        //     and the one that would have caught the whole of this bug: while the
        //     theme was hardcoded, asking for light simply gave black back.
        val black = androidx.compose.ui.graphics.Color.Black.value
        val white = androidx.compose.ui.graphics.Color.White.value
        if (light == dark) {
            throw AssertionError(
                "EasyVoiceTheme resolved the SAME background (" + light + ") for " +
                    "darkTheme=false and darkTheme=true. The app is meant to follow " +
                    "the system again (owner, 2026-09-22), so the two passes must be " +
                    "two schemes. If one scheme was restored on purpose, restore the " +
                    "light == dark assertion this replaced."
            )
        }
        if (dark != black) {
            throw AssertionError(
                "EasyVoiceTheme resolved dark background " + dark + ", expected pitch " +
                    "black " + black + ". The owner asked for a pure black background " +
                    "(2026-09-17) and that is now the DARK half; see ComposeTheme.kt."
            )
        }
        if (light != white) {
            throw AssertionError(
                "EasyVoiceTheme resolved light background " + light + ", expected pure " +
                    "white " + white + ". See the palette note in ComposeTheme.kt."
            )
        }
    }

    // enableAccessibilityChecks() also runs on every action, so a screen with no
    // interaction still needs the explicit sweep to be checked at all.
    private fun check(content: @androidx.compose.runtime.Composable () -> Unit) {
        themed(content)
        rule.enableAccessibilityChecks()
        sweepSchemes()
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
        check { AdvancedScreen(prefs(), 0, { }) }
    }

    // The About screen, added 2026-09-03. Its two link buttons were removed on
    // 2026-09-10 at the owner's request; the licence block moved to its own
    // screen on 2026-09-17, so what is left is the title, four facts and one
    // button, and the checks that matter are the contrast of all of them and
    // that the button has a name and a 48dp target, in both colour schemes.
    @Test
    fun aboutScreen() {
        check { AboutScreen() }
    }

    // The licence screen the View Licenses button opens (owner, 2026-09-17).
    // Every screen in this app has a test, and this one is five paragraphs of
    // verbatim Apache notice under one heading -- all of it onSurfaceVariant on
    // the page, which is the pair this suite exists to keep honest. Without a
    // test here the text would have moved out of aboutScreen's coverage and out
    // of the run entirely.
    @Test
    fun licensesScreen() {
        check { LicensesScreen() }
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
            requestNotificationPermission = { }
        )
    }

    @Test
    fun mainScreenScanning() {
        check(mainScreen(scanning = true, scanLine = "Checking your TTS engines"))
    }

    // THE SCAN SCREEN OFFERS NOTHING BUT THE SCAN (owner, 2026-09-17: "jab
    // language aur voice scan hota hai ... tab to koi tab vagaira kuchh nahin
    // aata to fir More option ka button kyon aata hai").
    //
    // The tab row and both FABs were already gated on `!scanning`; the overflow
    // menu was not, because the app bar held nothing but a title until the menu
    // was added to it. This asserts the whole set is absent TOGETHER, so the next
    // control added to that bar cannot quietly appear on the scan screen -- which
    // is exactly how this one got there.
    @Test
    fun mainScreenScanningOffersNothingElse() {
        themed(mainScreen(scanning = true, scanLine = "Checking your TTS engines"))
        rule.onNodeWithContentDescription("More options").assertDoesNotExist()
        rule.onNodeWithText("Main Settings").assertDoesNotExist()
    }

    @Test
    fun mainScreenSettled() {
        check(mainScreen(scanning = false, scanLine = ""))
    }

    // THE "MORE OPTIONS" MENU, OPEN (owner, 2026-09-17). mainScreenSettled
    // renders the app bar, so the three-dot button's own contrast and 48dp
    // target are already measured -- but the MENU it opens is a Popup in its
    // own window and nothing else in this suite draws it, exactly as
    // configurationRowMenuOpen exists for the other menu in the app.
    //
    // It is worth its own test for a reason beyond coverage: a DropdownMenu
    // draws on `surfaceContainer`, not on the page, so its two items are the
    // only text in MainScreen measured against a different background. The
    // click also proves the button is reachable BY ITS NAME in the merged tree,
    // which is the exact assertion that caught evControl wrapping an
    // interactive child in build 832.
    @Test
    fun mainScreenMoreOptionsMenuOpen() {
        themed(mainScreen(scanning = false, scanLine = ""))
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("More options").performClick()
        sweepSchemes()
    }

    // THE MODE SETTINGS FAB FOLLOWS THE SELECTED MODE (owner, 2026-09-09).
    // Page 0 draws it and mainScreenSettled starts there, so ATF already
    // measures its contrast and touch target. What that cannot see is the
    // WIRING: the radio writes the mode straight to the store without bumping
    // modeRefresh, so MainScreen only learns about it through onModeChanged.
    // Drop that callback and the button silently keeps opening the settings of
    // the mode you left -- a failure with nothing visible about it. The seed is
    // mix, so the name starts as "Mixed mode settings" and must become
    // "Dual languages settings" after the Dual radio is picked.
    @Test
    fun mainScreenModeSettingsFabFollowsMode() {
        themed { mainScreen(scanning = false, scanLine = "")() }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("Mixed mode settings").assertExists()
        rule.onNodeWithContentDescription("Dual languages").performClick()
        rule.onNodeWithContentDescription("Dual languages settings").assertExists()
        sweepSchemes()
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
        themed { ModeSettingsScreen(prefs(), "dual") }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("Select secondary language, Hindi (hin)").performClick()
        // Acting on an item is what puts the OPEN menu through the checks.
        rule.onNodeWithContentDescription("Gujarati (guj)").performClick()
        sweepSchemes()
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
        themed { ModeSettingsScreen(prefs(), "dual") }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("Specific language for reading numbers, English (eng)")
            .performClick()
        rule.onNodeWithContentDescription("Hindi (hin)").performClick()
        sweepSchemes()
    }

    @Test
    fun configurationRowMenuOpen() {
        themed {
            ConfigurationScreen(
                labels = listOf("English (eng)", "Hindi (hin)"),
                engines = listOf("com.google.android.tts", ""),
                onLanguage = { }, onDeleteConfiguration = { }, onDisable = { }
            )
        }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("More actions for English (eng)").performClick()
        rule.onNodeWithContentDescription("Disable language").performClick()
        sweepSchemes()
    }

    // ======================================================================
    //  THE STATES, not just the screens
    //  Everything above renders each screen ONCE, in whatever state its seeded
    //  statics put it in. That checks the screen but not the app: a control
    //  that only exists after a tap, a switch's other position, an empty list
    //  and a mode's alternative branch are all views a blind user really meets
    //  and none of them was ever rendered here.
    //
    //  Each test below exists because it puts a DIFFERENT view on screen, and
    //  says which one.
    // ======================================================================

    // THE "ADD LANGUAGE" BUTTON HAD NEVER BEEN CHECKED. It is MainScreen's
    // floating action button and it is drawn only when `currentPage == 1`
    // (`!scanning && currentPage == 1 && showAddLanguage`), while
    // mainScreenSettled starts on page 0 -- so no test had ever put it on
    // screen. That matters here more than most: this exact control shipped
    // invisible once, because Material3 fills an ExtendedFloatingActionButton
    // with `primaryContainer`, which is 1.28:1 on this background, and it took
    // a device to notice. Contrast is precisely what this framework measures.
    @Test
    fun mainScreenAddLanguageButton() {
        themed { mainScreen(scanning = false, scanLine = "")() }
        rule.enableAccessibilityChecks()
        // Moving to Configuration is what draws the button; the click itself
        // also puts page 0 through the checks.
        // The tab's name is the title ALONE since 2026-09-09 -- the ", 2 of 3"
        // it used to carry doubled the position the delegate already derives
        // from PrimaryTabRow's selectableGroup.
        rule.onNodeWithContentDescription("Configuration").performClick()
        // Acting on the button is what puts the page that CONTAINS it through
        // them.
        rule.onNodeWithContentDescription("Add language").performClick()
        sweepSchemes()
    }

    // Every switch in its other position, and the Group size dropdown ENABLED.
    // A Material switch draws different colours checked and unchecked -- this
    // palette states four of them by hand (checked thumb #00325A on a #82C7FF
    // track, unchecked #4FD8EB on #2A2D31) and all four ratios in CLAUDE.md
    // were computed with a calculator, never measured on a real screen. The
    // seeded run only ever showed the unchecked half.
    @Test
    fun advancedTabEverythingOn() {
        EasyVoiceTtsService.stripAudioAttrFlag = true
        EasyVoiceTtsService.forceAccessibilityFlag = true
        EasyVoiceTtsService.keepAliveFlag = true
        EasyVoiceTtsService.showNotificationFlag = true
        EasyVoiceTtsService.disableAdvancedFlag = true
        EasyVoiceTtsService.quickCharacterFlag = true
        EasyVoiceTtsService.punctuationInFlowFlag = true
        EasyVoiceTtsService.smartNumberFlag = true
        EasyVoiceTtsService.smartNumberGroupSize = 3
        check { AdvancedScreen(prefs(), 0, { }) }
    }

    // The one DISABLED control on that tab: "Read punctuation in flow with
    // text" is greyed out exactly when the punctuation mode is "Specific
    // language" (mode 3), mirroring c3.k:833-843. A disabled control is exempt
    // from the contrast floor but not from having a name or a 48dp target, and
    // that state had never been rendered.
    @Test
    fun advancedTabPunctuationLocked() {
        EasyVoiceTtsService.punctuationModeInt = 3
        check { AdvancedScreen(prefs(), 0, { }) }
    }

    // The filter ON. A selected FilterChip is drawn with
    // `secondaryContainer` as its whole boundary -- `FlatSelectedOutlineWidth`
    // is 0.dp and `selectedBorderColor` is Transparent -- so the fill is the
    // only thing separating it from the page. That value was changed to
    // #42707F for a hand-computed 3.44:1, which is close enough to the 3.0
    // floor to be worth a real measurement.
    @Test
    fun languagesListFiltered() {
        themed { LanguagesScreen(prefs()) }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("My languages").performClick()
        rule.onNodeWithContentDescription("My languages").performClick()
        sweepSchemes()
    }

    // The CLEAR button inside the search field, which exists only once
    // something has been typed -- "only offered once there is something to
    // clear, so it is not a dead stop for a screen reader on an empty field".
    // Nothing had ever typed, so nothing had ever seen it.
    @Test
    fun languagesListSearching() {
        themed { LanguagesScreen(prefs()) }
        rule.enableAccessibilityChecks()
        rule.onNode(hasSetTextAction()).performTextInput("Hin")
        // Clicking the clear button checks the screen while it is on it.
        rule.onNodeWithContentDescription("Clear search").performClick()
        sweepSchemes()
    }

    // The OTHER half of LanguagesScreen. In "none" and "dual" the whole list is
    // replaced by one explanatory line, and that line is the entire screen --
    // if it is unreadable there is nothing else to fall back on.
    // The Languages list has TWO layouts and only one of them could ever run
    // on a given device, so one of the two has always gone unchecked. Both are
    // stated here, and each asserts the branch it got rather than trusting the
    // emulator image.
    //
    // Grouped is the one with the accessibility machinery in it: two
    // SectionHeaders live INSIDE the LazyColumn, which is the one thing this
    // project's own rule forbids, and it is allowed here only because the list
    // overrides `collectionInfo` with the real row count and each row carries a
    // `collectionItemInfo` index that runs continuously ACROSS both groups. If
    // any of that is wrong, a reader counts the headings as rows and every
    // announced position is off. Nothing had ever rendered it under ATF.
    @Test
    fun languagesListGrouped() {
        check { LanguagesScreen(prefs()) }
        rule.onNodeWithText("United States languages").assertExists()
        rule.onNodeWithText("All languages").assertExists()
    }

    // No country means no region to group by, so the flat list renders. This is
    // a real device state, not a contrivance: a user whose language is set
    // without a region gets it.
    @Test
    fun languagesListUngrouped() {
        Locale.setDefault(Locale("en"))
        check { LanguagesScreen(prefs()) }
        rule.onNodeWithText("All languages").assertDoesNotExist()
    }

    // A search that matches nothing. The list is empty and this one line is the
    // only thing on the screen below the controls -- it exists precisely so a
    // blind user can tell an empty result from a frozen screen, which makes it
    // the last place an unchecked label should be sitting.
    @Test
    fun languagesListNoMatch() {
        themed { LanguagesScreen(prefs()) }
        rule.enableAccessibilityChecks()
        rule.onNode(hasSetTextAction()).performTextInput("zzzz")
        rule.onNodeWithText("No languages match your search.").assertExists()
        sweepSchemes()
    }

    // The SAME empty list under the "My languages" filter says something else,
    // and that second string had no way of being rendered by any test: in mix
    // mode the required languages are always ticked, so the filter alone can
    // never empty the list. Filter plus a search that matches nothing can.
    @Test
    fun languagesListFilteredNoMatch() {
        themed { LanguagesScreen(prefs()) }
        rule.enableAccessibilityChecks()
        rule.onNodeWithContentDescription("My languages").performClick()
        rule.onNode(hasSetTextAction()).performTextInput("zzzz")
        rule.onNodeWithText("No languages are selected yet.").assertExists()
        sweepSchemes()
    }

    @Test
    fun languagesListNotAvailable() {
        EasyVoiceTtsService.modeInt = 1
        check { LanguagesScreen(prefs()) }
    }

    // Configuration with nothing configured yet. This is what a user sees on a
    // first run before picking any language, so it is the first thing the
    // screen ever says to them.
    @Test
    fun configurationTabEmpty() {
        check {
            ConfigurationScreen(
                labels = emptyList(), engines = emptyList(),
                onLanguage = { }, onDeleteConfiguration = { }, onDisable = { }
            )
        }
    }

    // A language the scan found no voice for. VoiceRows.load filters
    // lastScanVoices by iso3, so an entry with no match collapses the screen to
    // the explanatory line and hides Test, the three sliders and Default --
    // "never leave the screen with nothing to perceive" is the rule, and this
    // is the branch that has to honour it.
    @Test
    fun voiceSetupNoVoices() {
        synchronized(LangStore.languages) {
            LangStore.languages.add(entry("Marathi", "mar", "mr_IN"))
        }
        check { VoiceScreen(prefs(), 3, 4, { }) { null } }
    }

    // And the branch above that one: no entry at all at that index, which is
    // what a mode with no voice settings produces.
    @Test
    fun voiceSetupNoLanguage() {
        check { VoiceScreen(prefs(), 99, 1, { }) { null } }
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
