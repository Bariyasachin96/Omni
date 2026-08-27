package com.tts.easyvoice
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
// How far a horizontal drag must travel before it counts as a tab swipe.
// Raw pixels, because a draggable reports pixel deltas; roughly a finger-width
// on a normal-density screen, so a stray horizontal wobble while scrolling
// vertically does not change the tab.
private const val SWIPE_THRESHOLD_PX = 150f
class RequiredEnginesItem(val name: String, val pkg: String, installed: Boolean) {
    var installed by mutableStateOf(installed)
    var installing by mutableStateOf(false)
}
class MainActivity : ComponentActivity() {
    private var testTts: android.speech.tts.TextToSpeech? = null
    private fun newTestClient() {
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
            val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pkg"))
            marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(marketIntent)
        } catch (_: android.content.ActivityNotFoundException) {
            try {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
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
                    appIcon = remember {
                        try {
                            val drawable = applicationInfo.loadIcon(packageManager)
                            val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 96
                            val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 96
                            val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)
                            val canvas = android.graphics.Canvas(bitmap)
                            drawable.setBounds(0, 0, width, height)
                            drawable.draw(canvas)
                            bitmap.asImageBitmap()
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
                        if (android.os.Build.VERSION.SDK_INT >= 33 && checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
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
        title = { Text("Required TTS engines", modifier = Modifier
            .semantics(mergeDescendants = true) { heading(); contentDescription = "Required TTS engines" }) },
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
                            Button(
                                onClick = { onInstall(item) },
                                enabled = !item.installing,
                                modifier = Modifier.semantics {
                                    contentDescription = installLabel + " " + item.name
                                }
                            ) {
                                Icon(painterResource(R.drawable.ic_get_app), contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp))
                                Text(installLabel, modifier = Modifier.clearAndSetSemantics { })
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onApply,
                enabled = allInstalled,
                modifier = Modifier.semantics { contentDescription = "Apply" }
            ) {
                Icon(painterResource(R.drawable.ic_check), contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp))
                Text("Apply", modifier = Modifier.clearAndSetSemantics { })
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.semantics { contentDescription = "Cancel" }
            ) {
                Icon(painterResource(R.drawable.ic_close), contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp))
                Text("Cancel", modifier = Modifier.clearAndSetSemantics { })
            }
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
    val readingMode = remember(modeRefresh) { prefs.getReadingMode() }
    val showAddLanguage = readingMode != "none" && readingMode != "dual"
    // Plain state instead of a PagerState. See the comment on the content Box
    // below for why the pager had to go.
    var currentPage by remember { mutableStateOf(0) }
    var dragTotal by remember { mutableStateOf(0f) }
    Scaffold(
        floatingActionButton = {
            if (!scanning && currentPage == 1 && showAddLanguage) {
                ExtendedFloatingActionButton(
                    onClick = onAddLanguage,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.semantics { contentDescription = "Add language" },
                    icon = { Icon(painterResource(R.drawable.ic_add), contentDescription = null) },
                    text = { Text("Add language", modifier = Modifier.clearAndSetSemantics { }) }
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.app_name)) },
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
                TabRow(selectedTabIndex = currentPage) {
                    for (index in pageTitles.indices) {
                        Tab(
                            selected = currentPage == index,
                            onClick = { currentPage = index },
                            text = { Text(pageTitles[index], modifier = Modifier.clearAndSetSemantics { }) },
                            icon = { Icon(painterResource(pageIcons[index]), contentDescription = null) },
                            modifier = Modifier.semantics {
                                contentDescription = pageTitles[index] + ", " + (index + 1) + " of " + pageTitles.size
                            }
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
                        modifier = Modifier.semantics(mergeDescendants = true) {
                            heading(); contentDescription = "Finding the languages and voices on your device"
                        }
                    )
                    if (animationsEnabled(context)) {
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
                                if (dragTotal <= -SWIPE_THRESHOLD_PX && currentPage < pageTitles.lastIndex) {
                                    currentPage++
                                } else if (dragTotal >= SWIPE_THRESHOLD_PX && currentPage > 0) {
                                    currentPage--
                                }
                                dragTotal = 0f
                            }
                        )
                ) {
                    when (currentPage) {
                        0 -> ModesScreen(prefs, modeRefresh, onOpenModeSettings)
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
