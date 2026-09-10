package com.tts.easyvoice
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
// How far a horizontal drag must travel before it counts as a tab swipe.
//
// It is stated in DP and converted with LocalDensity at the point of use,
// because a draggable reports RAW PIXELS and a raw-pixel constant is a
// different physical distance on every phone. The old 150f was about 50dp on an
// xhdpi screen, 100dp on hdpi and 37dp on xxhdpi -- so the same flick changed
// the tab on one device and not on another, and the cheapest device needed the
// longest swipe. 48dp is Material's own minimum touch target, comfortably past
// the ~8dp touch slop `draggable` has already absorbed before it reports
// anything, so a wobble while scrolling vertically still cannot reach it.
private val SWIPE_THRESHOLD = 48.dp
class RequiredEnginesItem(val name: String, val pkg: String, installed: Boolean) {
    var installed by mutableStateOf(installed)
    var installing by mutableStateOf(false)
}
class MainActivity : EvActivity() {
    // The Test button's own client. AutoTTS's NewSettingsActivity creates two of
    // these and shuts down NEITHER -- its whole onDestroy is
    // `K.removeCallbacksAndMessages(null); super.onDestroy();` -- and ours was
    // that byte for byte, so this leaked a binding and the Context it was built
    // with on every rotate, fold, resize and theme change.
    //
    // DELIBERATE DEPARTURE, and the owner granted it explicitly on 2026-09-09
    // ("yah jo MainActivity wala hai vah bhi fix kar dena") after it was raised
    // as needing their override. Rule 5's forbidden-justification list names
    // "prevents a leak", which is exactly why it was not done unilaterally.
    // VoiceSetupActivity has carried the same two lines since 2026-09-09.
    private var testTts: android.speech.tts.TextToSpeech? = null
    private fun newTestClient() {
        // Nothing cancels the scan when the Activity goes, so its completion
        // callback can land after onDestroy -- backing out of the app during a
        // scan is the case. A client made then has no Test button to serve and
        // no onDestroy left to shut it down, which is the same leak one step
        // further along. (A rotation does not reach here: the new Activity's
        // scan bumps EngineFinder's generation and the old finalize is skipped.)
        if (isDestroyed) return
        // The scan can finish more than once in one Activity, and each finish
        // used to drop the previous client on the floor.
        try { testTts?.shutdown() } catch (_: Exception) { }
        testTts = android.speech.tts.TextToSpeech(this, null, "com.tts.easyvoice")
    }
    private var scanning by mutableStateOf(true)
    private var scanLine by mutableStateOf("Checking your TTS engines")
    private var modeRefresh by mutableStateOf(0)
    private val dialogItems = mutableStateListOf<RequiredEnginesItem>()
    private var dialogVisible by mutableStateOf(false)
    private var pendingImportXml: String = ""
    private val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            EasyVoiceLogger.debug("TTS", "Notification permission granted")
        } else {
            EasyVoiceLogger.debug("TTS", "Notification permission denied")
        }
    }
    private val importSettingsLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri -> handleImportedSettingsFile(uri) }
    private fun restartAfterImport() {
        try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                android.os.Process.killProcess(android.os.Process.myPid())
                System.exit(0)
            }
        } catch (_: Exception) {}
    }
    private var pendingInstallItem: RequiredEnginesItem? = null
    private fun isRequiredLanguage(prefs: SharedPrefsManager, iso3: String): Boolean =
        LangStore.requiredLangs(modeIntOf(prefs.getReadingMode()),
            EasyVoiceTtsService.autoLang, EasyVoiceTtsService.dualLang,
            EasyVoiceTtsService.mixLatinLang, EasyVoiceTtsService.mixNonLatinLang).contains(iso3)
    private fun isPackageInstalled(pkg: String): Boolean = try { packageManager.getPackageInfo(pkg, 0); true } catch (_: Exception) { false }
    private fun isPackageInstalledWithActivities(pkg: String): Boolean = try { packageManager.getPackageInfo(pkg, 1); true } catch (_: Exception) { false }
    private fun openPlayStoreFor(pkg: String) {
        try {
            val marketIntent = Intent(Intent.ACTION_VIEW, "market://details?id=$pkg".toUri())
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(marketIntent)
        } catch (_: android.content.ActivityNotFoundException) {
            try {
                val webIntent = Intent(Intent.ACTION_VIEW, "https://play.google.com/store/apps/details?id=$pkg".toUri())
                webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(webIntent)
            } catch (_: android.content.ActivityNotFoundException) {
                Toast.makeText(this, "Cannot open Play Store", Toast.LENGTH_SHORT).show()
                pendingInstallItem?.installing = false
                pendingInstallItem = null
            }
        }
    }
    private fun checkPendingInstall() {
        val item = pendingInstallItem
        if (item != null) {
            val installed = isPackageInstalledWithActivities(item.pkg)
            item.installed = installed
            item.installing = false
            if (!installed) Toast.makeText(this, "Package not installed. Please try again.", Toast.LENGTH_SHORT).show()
            pendingInstallItem = null
        }
    }
    private fun handleImportedSettingsFile(uri: android.net.Uri?) {
        if (uri == null) return
        val prefsManager = SharedPrefsManager(this)
        try {
            println("importSettings")
            val xml = contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() } ?: ""
            val pkgs = prefsManager.getReferencedEnginePackagesFromXml(xml)
            if (pkgs.isEmpty()) {
                Toast.makeText(this, "Could not read that settings file.", Toast.LENGTH_LONG).show()
                return
            }
            dialogItems.clear()
            for (pkg in pkgs) {
                val engineLabel = EngineFinder.friendlyName(pkg)
                println("- " + pkg + " : " + engineLabel)
                dialogItems.add(RequiredEnginesItem(engineLabel, pkg, isPackageInstalled(pkg)))
            }
            pendingImportXml = xml
            dialogVisible = true
        } catch (_: Exception) { Toast.makeText(this, "Could not read that settings file.", Toast.LENGTH_LONG).show() }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        // Swaps the splash theme for AppTheme.NoActionBar. It has to run before
        // super.onCreate, which is where the window theme is read. There is no
        // setKeepOnScreenCondition: the splash goes the moment the first frame
        // is drawn, so it never delays a screen reader by a millisecond.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val prefs = SharedPrefsManager(this)
        LangStore.loadModeLangs(this)
        LangStore.loadMode(this)
        LangStore.loadFlags(this)
        EasyVoiceLogger.setLoggingEnabled(prefs.isLoggingEnabled())
        setContent {
            EasyVoiceTheme {
                MainScreen(
                    prefs = prefs,
                    scanning = scanning,
                    scanLine = scanLine,
                    modeRefresh = modeRefresh,
                    // core-ktx's Drawable.toBitmap, not a hand-rolled
                    // Bitmap + Canvas. It does two things the hand-rolled
                    // version did not: it RESTORES the drawable's original
                    // bounds afterwards instead of leaving it resized, and it
                    // returns a BitmapDrawable's own bitmap directly when the
                    // size already matches -- which is the API 24-25 launcher
                    // icon, where our version allocated and redrew for nothing.
                    // The explicit width and height keep our own fallback for a
                    // drawable that reports no intrinsic size; they are also
                    // toBitmap's own defaults, so passing them changes nothing
                    // when the icon is well behaved.
                    appIcon = remember {
                        try {
                            val drawable = applicationInfo.loadIcon(packageManager)
                            val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 96
                            val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 96
                            drawable.toBitmap(width, height).asImageBitmap()
                        } catch (_: Exception) { null }
                    },
                    onOpenModeSettings = { mode ->
                        startActivity(Intent(this, ModeSettingsActivity::class.java).putExtra("mode", mode))
                    },
                    onAddLanguage = { startActivity(Intent(this, LanguagesActivity::class.java)) },
                    onLanguage = { index ->
                        startActivity(Intent(this, VoiceSetupActivity::class.java).putExtra("lang_index", index))
                    },
                    // Wipe what was configured AND drop the language. A language
                    // the current mode requires cannot leave the list -- the same
                    // guard onRowToggled has -- but its settings can still go, so
                    // do that much and say why it stays.
                    onDeleteConfiguration = { index ->
                        val entry = LangStore.languages.getOrNull(index)
                        if (entry != null) {
                            LangStore.clearConfiguration(this, entry)
                            if (isRequiredLanguage(prefs, entry.iso3)) {
                                Toast.makeText(this, "Configuration deleted. " + entry.displayName + " is needed by the current mode, so it stays in the list", Toast.LENGTH_LONG).show()
                            } else {
                                entry.disabled = true
                                LangStore.persistDisabled(this)
                                Toast.makeText(this, entry.displayName + " configuration deleted", Toast.LENGTH_SHORT).show()
                            }
                            modeRefresh++
                        }
                    },
                    // Drop the language but keep everything configured for it, so
                    // adding it back later restores the voice and the sliders.
                    onDisableLanguage = { index ->
                        val entry = LangStore.languages.getOrNull(index)
                        if (entry != null) {
                            if (isRequiredLanguage(prefs, entry.iso3)) {
                                Toast.makeText(this, entry.displayName + " is needed by the current mode, so it cannot be turned off", Toast.LENGTH_LONG).show()
                            } else {
                                entry.disabled = true
                                LangStore.persistDisabled(this)
                                Toast.makeText(this, entry.displayName + " disabled", Toast.LENGTH_SHORT).show()
                                modeRefresh++
                            }
                        }
                    },
                    requestNotificationPermission = {
                        // ContextCompat.checkSelfPermission is the library form
                        // of the same check. The SDK_INT guard stays beside it
                        // and is doing a different job: POST_NOTIFICATIONS only
                        // EXISTS from 33, and below that the permission is not a
                        // runtime one, so there is nothing to ask for.
                        if (android.os.Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                            try { notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS) } catch (_: Exception) {}
                        }
                    },
                    launchImportPicker = {
                        try { importSettingsLauncher.launch(arrayOf("text/xml", "application/xml")) } catch (_: android.content.ActivityNotFoundException) { Toast.makeText(this, "No compatible file manager found on this device", Toast.LENGTH_LONG).show() }
                    }
                )
                if (dialogVisible) {
                    RequiredEnginesDialog(
                        items = dialogItems,
                        onInstall = { item ->
                            item.installing = true
                            pendingInstallItem = item
                            openPlayStoreFor(item.pkg)
                        },
                        onApply = {
                            dialogVisible = false
                            try { prefs.importSettingsXml(pendingImportXml); restartAfterImport() } catch (ex: Exception) { ex.printStackTrace() }
                        },
                        onCancel = { dialogVisible = false }
                    )
                }
            }
        }
        EngineFinder.scanLanguages(this, { line -> runOnUiThread { scanLine = line } }) { newTestClient(); runOnUiThread {
            scanning = false
        } }
    }
    override fun onPause() {
        LangStore.persistAll(this)
        super.onPause()
    }
    override fun onResume() {
        super.onResume()
        modeRefresh++
        if (pendingInstallItem != null) checkPendingInstall()
    }
    override fun onDestroy() {
        EngineFinder.cancelGlobalTimeout()
        // TextToSpeech holds a binding until shutdown() is called; see the field.
        try { testTts?.shutdown() } catch (_: Exception) { }
        testTts = null
        super.onDestroy()
    }
}
@Composable
fun RequiredEnginesDialog(
    items: List<RequiredEnginesItem>,
    onInstall: (RequiredEnginesItem) -> Unit,
    onApply: () -> Unit,
    onCancel: () -> Unit
) {
    var allInstalled = true
    for (item in items) if (!item.installed) allInstalled = false
    AlertDialog(
        onDismissRequest = { },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        // A dialog is a screen too. Material3 does not mark its title slot as a
        // heading, so it was the one screen-sized surface with nothing to jump to.
        title = { Text("Required TTS engines", modifier = Modifier.semantics { heading() }) },
        text = {
            // Material3's AlertDialog does not scroll its text slot -- it only
            // gives it Modifier.weight(weight = 1f, fill = false) -- so with
            // several missing engines at a large font scale this list was
            // clipped with no way to reach the rest. No nested-scroll conflict,
            // precisely because M3 adds none of its own.
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                for (item in items) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = if (item.installed) "Installed" else "Not installed",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (item.installed) LocalContentColor.current
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
                        )
                        if (!item.installed) {
                            Spacer(modifier = Modifier.width(8.dp))
                            val installLabel = if (item.installing) "Installing..." else "Install"
                            // The visible label is "Install"; the accessible
                            // name adds the engine, because a column of
                            // identically named buttons is what
                            // DuplicateSpeakableTextCheck flags. WCAG 2.5.3 is
                            // satisfied because the name still CONTAINS the
                            // visible label.
                            EvButton(
                                label = installLabel + " " + item.name,
                                iconRes = R.drawable.ic_get_app,
                                enabled = !item.installing
                            ) { onInstall(item) }
                        }
                    }
                }
            }
        },
        confirmButton = {
            EvButton("Apply", iconRes = R.drawable.ic_check, enabled = allInstalled, onClick = onApply)
        },
        // Outlined, not filled: Apply is the action this dialog is FOR, and the
        // Material emphasis ladder puts the filled style above the outlined one.
        // It used to be a TextButton, which is a step lower again and carried an
        // icon anyway; outlined keeps the icon looking deliberate.
        dismissButton = {
            EvButton("Cancel", iconRes = R.drawable.ic_close, outlined = true, onClick = onCancel)
        }
    )
}
// Scaffold is Material's screen skeleton: it places the app bar and the bottom
// bar and hands the content the padding they occupy. TopAppBar replaces the
// Surface + Row + Text I had assembled by hand. Colours come from the library's
// own defaults, adjusted through the non-deprecated TopAppBarColors.copy --
// TopAppBarDefaults.topAppBarColors(containerColor = ...) is @Deprecated in 1.4.0.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    prefs: SharedPrefsManager,
    scanning: Boolean,
    scanLine: String,
    modeRefresh: Int,
    appIcon: androidx.compose.ui.graphics.ImageBitmap?,
    onOpenModeSettings: (String) -> Unit,
    onAddLanguage: () -> Unit,
    onLanguage: (Int) -> Unit,
    onDeleteConfiguration: (Int) -> Unit,
    onDisableLanguage: (Int) -> Unit,
    requestNotificationPermission: () -> Unit,
    launchImportPicker: () -> Unit
) {
    val context = LocalContext.current
    val pageTitles = listOf("Main Settings", "Configuration", "Advanced")
    val pageIcons = listOf(R.drawable.ic_tab_modes, R.drawable.ic_tune, R.drawable.ic_tab_advanced)
    // Reading the mode is cheap; the language list is NOT (it rebuilds LangStore
    // and commits), so it is built inside the Configuration page instead, which
    // the pager composes only while that tab is showing -- the same moment
    // ConfigurationActivity used to do it in onResume.
    // A `var`, because the Modes tab changes the mode without bumping
    // modeRefresh -- it writes the store directly -- and BOTH floating buttons
    // depend on the answer: the Main Settings one names the mode whose settings
    // it opens, and the Configuration one is hidden for dual. Re-reading prefs
    // on the refresh key alone left each of them one mode behind.
    var readingMode by remember(modeRefresh) { mutableStateOf(prefs.getReadingMode()) }
    val showAddLanguage = readingMode != "none" && readingMode != "dual"
    // Plain state instead of a PagerState. See the comment on the content Box
    // below for why the pager had to go.
    var currentPage by remember { mutableStateOf(0) }
    var dragTotal by remember { mutableStateOf(0f) }
    // dp -> px once per composition, with the library's own density rather than
    // a hand-multiplied displayMetrics read.
    val swipeThresholdPx = with(LocalDensity.current) { SWIPE_THRESHOLD.toPx() }
    // Window size class, read once here and passed down as ordinary state --
    // the layered approach the adaptive guidance asks for.
    val compactHeight = evIsCompactHeight()
    Scaffold(
        floatingActionButton = {
            // The selected mode's settings, in the corner rather than on the
            // selected radio's own row, where the control moved down the list
            // every time the mode changed (owner, 2026-09-09). See ModesScreen.
            //
            // The VISIBLE label is the short "Settings" while the ACCESSIBLE
            // name is the whole "<Mode> settings" -- the same string the screen
            // it opens uses as its first heading, so what you hear here is what
            // you land on there. WCAG 2.5.3 Label in Name asks the accessible
            // name to CONTAIN the visible label, which it does; the reverse
            // mismatch is the one that had to be fixed on the Add language FAB,
            // where the visible word was absent from the name entirely. The
            // label is kept short on purpose: "Multilingual mode (experimental)
            // settings" is 41 characters and an extended FAB is a single line,
            // so spelling the mode out visibly would stretch the button across
            // a compact screen.
            //
            // A MODE THAT IS NOT A ROW GETS NO BUTTON EITHER (owner,
            // 2026-09-09: "Agar Google hidan hai Google mod to uska button bhi
            // hidan Hona chahie"). ModesScreen draws exactly four rows -- it
            // skips "none" and "google" -- so those two are the modes the list
            // will not show, and offering a corner button that names one of
            // them and opens its settings contradicts the whole point of
            // hiding it. "Google TTS settings" was reachable from here on a
            // fresh install, because LangStore.loadMode defaults auto_mode to
            // 3 when Google TTS is present.
            //
            // The test is derived from the same list ModesScreen walks rather
            // than repeated by hand, so a mode added or unhidden there cannot
            // leave this button behind.
            //
            // What it costs, stated rather than hidden: Google mode's own
            // "Select preferred language" is then unreachable while google is
            // in force. That is the owner's standing decision for this mode --
            // it stays selected and stays invisible -- and it is the same
            // trade they already made when they rejected drawing the google
            // radio to keep that setting reachable.
            if (!scanning && currentPage == 0 && readingMode !in HIDDEN_MODES) {
                val modeTitle = modeRowSpecs.firstOrNull { it.first == readingMode }?.second
                val settingsName =
                    if (modeTitle == null) "Mode settings" else modeTitle + " settings"
                val openSettings = { onOpenModeSettings(readingMode) }
                ExtendedFloatingActionButton(
                    onClick = openSettings,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.evControl(settingsName, Role.Button, action = openSettings),
                    icon = { Icon(painterResource(R.drawable.ic_settings), contentDescription = null) },
                    text = { Text("Settings") }
                )
            }
            if (!scanning && currentPage == 1 && showAddLanguage) {
                ExtendedFloatingActionButton(
                    onClick = onAddLanguage,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.evControl("Add language", Role.Button, action = onAddLanguage),
                    icon = { Icon(painterResource(R.drawable.ic_add), contentDescription = null) },
                    text = { Text("Add language") }
                )
            }
        },
        topBar = {
            // Google's own worked example for compact height: "Decide whether to
            // show the top app bar based on window size class." A phone or an
            // open flippable in LANDSCAPE is medium width but compact height, and
            // there the bar costs a fifth of the usable height to repeat a name
            // the launcher already said. The screen title is not lost -- the
            // pager still carries paneTitle "Easy Voice settings".
            if (!compactHeight) TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    if (appIcon != null) {
                        Image(
                            bitmap = appIcon,
                            contentDescription = null,
                            modifier = Modifier.padding(start = 16.dp, end = 8.dp).size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            if (!scanning) {
                // PrimaryTabRow, not TabRow: in material3 1.4.0 TabRow has ONE
                // overload and it is @Deprecated. These are the app's top-level
                // destinations, which is the PRIMARY tab row in the M3 spec;
                // SecondaryTabRow is for tabs nested inside a destination.
                PrimaryTabRow(selectedTabIndex = currentPage) {
                    for (index in pageTitles.indices) {
                        Tab(
                            selected = currentPage == index,
                            onClick = { currentPage = index },
                            text = { Text(pageTitles[index]) },
                            icon = { Icon(painterResource(pageIcons[index]), contentDescription = null) },
                            // THE TAB ROLE NOW REACHES THE FOCUSED NODE. Material
                            // sets `role = Role.Tab` on a node that merges a text
                            // child, so the delegate's gate sent its
                            // `roleDescription = "Tab"` to a fake child only, and a
                            // reader that does not walk those said nothing at all.
                            // A class name could not rescue it either -- Role.Tab
                            // has no legacy class, and `android.app.ActionBar$Tab`
                            // was tried on device and not recognised. Clearing the
                            // node is what opens the gate, and then the library
                            // writes its own "Tab" onto the node in focus.
                            //
                            // `isSelected` also earns the position back for free:
                            // the delegate turns Selected + Role.Tab into
                            // `info.isSelected`, which is how a reader says which
                            // tab is current.
                            //
                            // THE NAME IS THE TITLE ALONE -- no ", N of M" (owner,
                            // 2026-09-09: *"yah thoda double hai ... jo already
                            // TalkBack announce karti chijen hain vah rakhni hi
                            // nahin hai"*). They were right, and the library says
                            // so: `PrimaryTabRow` applies `Modifier.selectableGroup()`
                            // (TabRow.kt:401) and every `Tab` sets `Selected`
                            // (Tab.kt:177), so `setCollectionInfo` derives a
                            // CollectionInfo from the group and `setCollectionItemInfo`
                            // derives this tab's index from its selected siblings
                            // (CollectionInfo.android.kt) -- the delegate announces
                            // "1 of 2" on its own, onto the FOCUSED node, so a reader
                            // that walks no fake children still gets it.
                            //
                            // The comment that used to sit here justified the suffix
                            // with the owner's device on 2026-08-13. That test was
                            // real and it is not evidence any more: the app was
                            // Views and `TabLayout` then. `PrimaryTabRow`, `evControl`
                            // and this delegate path all arrived afterwards, and the
                            // mechanism changed without the justification following
                            // it. Do not put the suffix back on the strength of that
                            // date; if the position ever goes silent again, check
                            // `selectableGroup` first.
                            modifier = Modifier.evControl(
                                pageTitles[index],
                                Role.Tab,
                                isSelected = currentPage == index,
                                action = { currentPage = index }
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (scanning) {
            ResponsiveContent(padding = innerPadding) {
                // Scrollable so the headline is still reachable at the largest
                // font scales. Arrangement.Center survives it: verticalScroll
                // passes minHeight through, so the column still fills the
                // viewport and only grows when the text is genuinely taller.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Finding the languages and voices on your device",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        // The first screen the app ever shows, and it was the only
                        // one whose title was styled as a headline but never
                        // marked as one.
                        modifier = Modifier.semantics { heading() }
                    )
                    if (animationsEnabled()) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 10.dp).clearAndSetSemantics { }
                        )
                    }
                    Text(
                        text = scanLine,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .semantics { liveRegion = LiveRegionMode.Polite }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .semantics { paneTitle = "Easy Voice settings" }
            ) {
                // A plain Box, NOT a HorizontalPager, and this is a hard rule.
                //
                // A pager is a scroll container, and the Compose delegate goes out
                // of its way to make every scroll container auto-scrollable for
                // TalkBack:
                //     // Talkback defines SCROLLABLE_ROLE_FILTER_FOR_DIRECTION_-
                //     // NAVIGATION, so we need to assign a role for auto scroll to
                //     // work. Node with collectionInfo resolved by Talkback to
                //     // ROLE_LIST and supports autoscroll too
                //     if (!semanticsNode.hasCollectionInfo())
                //         info.className = "android.widget.HorizontalScrollView"
                // Either branch lands the node in TalkBack's FILTER_AUTO_SCROLL
                // (ROLE_LIST / ROLE_GRID / ROLE_SCROLL_VIEW /
                // ROLE_HORIZONTAL_SCROLL_VIEW / ROLE_DROP_DOWN_LIST), the set it
                // scrolls by itself when linear navigation runs off the end -- so
                // reading past the last language row jumped to the next tab, and
                // reading back past the first jumped to the previous one.
                //
                // userScrollEnabled = false does remove that action, but it also
                // removes pageLeft/pageRight and the touch gesture with it, which
                // cost the deliberate two-finger swipe. One switch, both jobs.
                //
                // A Box publishes no scroll semantics at all, so there is nothing
                // for TalkBack to scroll: the page is a boundary. Modifier.draggable
                // adds no semantics either -- unlike Modifier.scrollable -- and
                // still sees the two-finger swipe, because TalkBack forwards it as
                // ordinary touch. The drag is accumulated and acted on once when it
                // ends, so one flick moves exactly one tab.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta -> dragTotal += delta },
                            onDragStarted = { dragTotal = 0f },
                            onDragStopped = {
                                if (dragTotal <= -swipeThresholdPx && currentPage < pageTitles.lastIndex) {
                                    currentPage++
                                } else if (dragTotal >= swipeThresholdPx && currentPage > 0) {
                                    currentPage--
                                }
                                dragTotal = 0f
                            }
                        )
                ) {
                    when (currentPage) {
                        0 -> ModesScreen(
                            prefs,
                            modeRefresh,
                            onModeChanged = { picked -> readingMode = picked }
                        )
                        1 -> {
                            // One remember for both: voiceLanguageLabels rebuilds
                            // LangStore.languages as a side effect, and the flags
                            // are read from it, so they must not be split apart.
                            val page = remember(modeRefresh) {
                                val names = voiceLanguageLabels(context, modeIntOf(readingMode))
                                Pair(names, voiceLanguageEngines(modeIntOf(readingMode)))
                            }
                            ConfigurationScreen(page.first, page.second, onLanguage, onDeleteConfiguration, onDisableLanguage)
                        }
                        else -> AdvancedScreen(prefs, modeRefresh, requestNotificationPermission, launchImportPicker)
                    }
                }
            }
        }
    }
}
