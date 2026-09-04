package com.tts.easyvoice
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.MotionDurationScale
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

private val EvColorScheme = darkColorScheme(
    primary = Color(0xFF82C7FF),
    onPrimary = Color(0xFF00325A),
    primaryContainer = Color(0xFF1B2A38),
    onPrimaryContainer = Color(0xFFFFFFFF),
    secondary = Color(0xFF4FD8EB),
    onSecondary = Color(0xFF00325A),
    background = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF1E1F22),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF2A2D31),
    onSurfaceVariant = Color(0xFFE3E3E6),
    outline = Color(0xFF4FD8EB),
    // Roles below were left to darkColorScheme()'s M3 baseline until the
    // guideline pass measured them. Two are used and failed WCAG 1.4.11's 3:1
    // floor for non-text UI:
    //   outlineVariant #49454F is what TabRow's own HorizontalDivider draws
    //     with -- 1.76:1 on surface, and that line is the boundary between the
    //     tab bar and the page. Now 3.70:1.
    //   secondaryContainer #4A4458 fills a SELECTED FilterChip, 2.02:1 on the
    //     page, and M3 gives a selected chip no border at all
    //     (selectedBorderWidth = 0), so the fill is its only boundary.
    //     Now 3.44:1, with a white label at 5.45:1.
    // surfaceContainer / surfaceContainerHigh are the menu and dialog fills.
    // Their low ratio against the page is fine -- a popup over a scrim has no
    // contrast requirement against what it covers -- but the baseline values
    // are purple-tinted, so they follow our own surface.
    secondaryContainer = Color(0xFF42707F),
    onSecondaryContainer = Color(0xFFFFFFFF),
    surfaceContainer = Color(0xFF1E1F22),
    surfaceContainerHigh = Color(0xFF1E1F22),
    outlineVariant = Color(0xFF727880)
)

@Composable
fun EasyVoiceTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EvColorScheme) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            // The Surface fills the WHOLE window on purpose, so the background
            // colour paints behind the status and navigation bars and there is
            // no unpainted strip. Only the content inside is inset.
            modifier = Modifier.fillMaxSize()
        ) {
            // WINDOW INSETS, ONE PLACE, EVERY SCREEN.
            //
            // targetSdk is 37, and from Android 15 (API 35) the system draws
            // every app edge to edge and ignores android:statusBarColor and
            // android:navigationBarColor -- which values/styles.xml still sets,
            // and which do nothing there. So content starts at y = 0, under the
            // status bar. The owner's screenshot of "English (eng) voices"
            // sitting on top of the clock was exactly that.
            //
            // This is the ONLY place the app pads for insets, and it is enough
            // for every screen -- present and future -- because of two library
            // guarantees, both read from androidx rather than assumed:
            //
            // 1. Modifier.windowInsetsPadding CONSUMES what it pads.
            //    WindowInsetsPadding.kt: "Any insets consumed by other insets
            //    padding modifiers or [consumeWindowInsets] on a parent layout
            //    will be excluded from [insets]. [insets] will be consumed for
            //    child layouts as well."
            // 2. Material3's Scaffold SUBTRACTS what an ancestor consumed.
            //    Scaffold.kt: `safeInsets.insets =
            //    contentWindowInsets.exclude(consumedWindowInsets)`.
            //    So MainActivity's Scaffold hands out an innerPadding of zero
            //    here instead of padding a second time. Nothing had to be told
            //    about anything.
            //
            // safeDrawing, not systemBars: it is systemBars.union(ime)
            //   .union(displayCutout) (WindowInsets.android.kt:362), so it also
            // clears the punch-hole or notch -- which is what makes one device's
            // usable top edge lower than another's, and why this looked like a
            // Xiaomi-only bug -- and moves content off the keyboard.
            //
            // The window itself is declared edge to edge in EvActivity, with
            // androidx's own enableEdgeToEdge(), so there is ONE window shape on
            // every API level the app installs on and this padding is exercised
            // everywhere rather than only on Android 15 and up. Read the note
            // there for the two SystemBarStyles and why the defaults were wrong
            // for a permanently dark app.
            Box(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
                content()
            }
        }
    }
}

// ==========================================================================
//  WINDOW SIZE CLASSES
//  The one place the app asks how much room it has. Everything else takes the
//  answer as ordinary state, which is what the guidance asks for: "a layered
//  approach confines display size logic to a single location instead of
//  scattering it across your app in many places that need to be kept in sync."
//
//  Read `currentWindowAdaptiveInfoV2()`, NOT `currentWindowAdaptiveInfo(...)`.
//  The doc page still shows the latter with a `supportLargeAndXLargeWidth`
//  flag, but androidx's own api/current.txt marks that one @Deprecated and
//  lists V2 as the replacement; it landed in adaptive 1.3.0-alpha10 and is in
//  1.3.0 stable, which is what we depend on.
//
//  It is the WINDOW, never the device. Split-screen, desktop windowing and a
//  folded inner display all give the app less than the physical screen, and
//  the class changes while the app is running, so this is read per composition
//  rather than cached.
// ==========================================================================
@Composable
fun evWindowSizeClass(): WindowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass

// A short window is the case Google's own worked example calls out: a phone or
// an open flippable in landscape is medium WIDTH but compact HEIGHT, where a
// top app bar costs more than it gives.
@Composable
fun evIsCompactHeight(): Boolean =
    !evWindowSizeClass().isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

// The content measure. Below the expanded breakpoint the single pane fills the
// window, which is right for every phone and for a tablet in portrait. At and
// above it the column is capped and centred, so a 1600dp desktop window does
// not stretch one settings row edge to edge.
//
// The cap stays at the expanded breakpoint rather than growing with the window:
// this is a single-column reading measure, and Material's answer to a wider
// window is a second PANE, not a wider line. Widening the column is the thing
// the large-screen guidance actually warns about.
// Nullable rather than Dp.Unspecified on purpose: Dp.Unspecified is Dp(Float.NaN)
// and Dp is a value class whose equals compares the floats, so `x == Dp.Unspecified`
// is ALWAYS false -- NaN never equals NaN. The idiomatic test is `isUnspecified`,
// and a null is plainer still.
@Composable
private fun evContentMaxWidth(): Dp? =
    if (evWindowSizeClass().isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND))
        WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND.dp
    else null

@Composable
fun ResponsiveContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit
) {
    val maxWidth = evContentMaxWidth()
    Box(
        modifier = modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = if (maxWidth == null) Modifier.fillMaxWidth()
                       else Modifier.fillMaxWidth().widthIn(max = maxWidth)
        ) { content() }
    }
}

// Colour and motion -> "Remove animations", the accessibility setting for
// people with motion sickness, photosensitivity or seizure triggers. The
// startup spinner is the only animation the app owns, and it is hidden outright
// when the setting is on.
//
// THE VALUE COMES FROM COMPOSE, not from a hand-read of Settings.Global, and
// that is a real difference rather than tidying. androidx already reads exactly
// this setting and, unlike a one-shot read, keeps watching it
// (WindowRecomposer.android.kt):
//
//     private fun Context.readAnimationScale() =
//         Settings.Global.getFloat(contentResolver,
//             Settings.Global.ANIMATOR_DURATION_SCALE, 1f)
//     ... Settings.Global.getUriFor(ANIMATOR_DURATION_SCALE) + a ContentObserver
//         -> a StateFlow collected into MotionDurationScaleImpl._scaleFactor
//
// and it puts that MotionDurationScale into the Recomposer's own coroutine
// context (`Recomposer(contextWithClockAndMotionScale)`), which is the context
// rememberCoroutineScope() hands back. `_scaleFactor` is a mutableFloatStateOf,
// so reading it HERE subscribes: switch "Remove animations" on while the scan
// screen is open and the spinner goes at once. The old read could not do that.
//
// The try/catch is the documented contract, not caution: MotionDurationScaleImpl
// throws `error("MotionDurationScale scale factor requested before recomposer
// loop start")` if it is asked before the recomposer's ON_CREATE launch has run.
//
// Compose's own animations already handle a scale of 0 -- InfiniteTransition
// suspends on `if (durationScale == 0f)` and waits for it to come back -- so the
// spinner would FREEZE rather than spin without this. Hiding it is deliberately
// more than the library does: a frozen ring says nothing, and the scan headline
// and the polite live region below it carry the whole message anyway.
@Composable
fun animationsEnabled(): Boolean {
    val scope = rememberCoroutineScope()
    val scale = try {
        scope.coroutineContext[MotionDurationScale]?.scaleFactor ?: 1f
    } catch (_: Exception) { 1f }
    return scale != 0f
}

// ==========================================================================
//  THE ROLE AND THE NON-TALKBACK READER: THE RECORD, NOW THAT THE APP CARRIES
//  NO CLASS NAME OF ITS OWN
//
//  `EvRoleClass` used to live here. It is gone, and so is the last
//  `accessibilityClassName` in the app, because the owner asked for the UI to
//  rest on the library alone ("apne haath se kuchh bhi nahin") and because the
//  one place it survived was describing a list row as a button. That row is a
//  list item now and says so through `collectionItemInfo`, which is the
//  library's own way of naming what it is. See ConfigurationScreen.kt.
//
//  KEEP THE FINDINGS, so this is not rediscovered a third time.
//
//  1. The delegate applies a Role only under
//
//         if (semanticsNode.isFake || semanticsNode.replacedChildren.isEmpty())
//
//     and it does so before `accessibilityClassName`, which is applied last and
//     UNGATED (AndroidComposeViewAccessibilityDelegateCompat, the role block and
//     the final `AccessibilityClassName` let). Every merged control with a text
//     child fails that gate, so its role goes to a FAKE ROLE CHILD
//     (`SemanticsNode.emitFakeNodes`, guarded by `unmergedChildren.isNotEmpty()`)
//     which the service receives as its own virtual node. TalkBack walks those
//     children; a reader that inspects only the focused node does not.
//
//  2. So putting `accessibilityClassName` on a node that ALSO has a Role states
//     the role TWICE. The owner heard exactly that on 2026-09-03: "button
//     button", and the dropdown twice. Never do it again.
//
//  3. An `IconButton` is the case that already works everywhere, and it is worth
//     knowing why: `Icon(contentDescription = null)` adds NO semantics modifier,
//     so the button has no semantics children, `replacedChildren` is empty, the
//     gate PASSES and `info.className = "android.widget.Button"` lands on the
//     real node. One source, every reader. Nothing to fix there.
//
//  4. What is still not reachable, checked rather than assumed:
//       * there is no roleDescription semantics API. In the version the BOM
//         pins, `SemanticsPropertiesAndroid` has exactly three members --
//         AccessibilityClassName, CredentialRequest (@RequiresApi 34) and
//         TestTagsAsResourceId (compose ui api/1.12.0-beta01.txt). A TAB is
//         announced by Compose with `roleDescription = "Tab"` and no class name
//         at all, so on a non-TalkBack reader the tab role cannot be reached
//         from this app. `android.app.ActionBar$Tab` was tried and the owner's
//         second reader does not recognise it.
//       * the fake child cannot be suppressed: Compose has no API to unset a
//         Role a Material component already set.
//       * `clearAndSetSemantics` passed to a component as its `modifier` is NOT
//         the way in: `LayoutNode.calculateSemanticsConfiguration` walks
//         tailToHead and a clearing node RESETS the config, and our modifier is
//         at the head, so it would wipe the component's own onClick, role and
//         disabled state. That is the bug that once made the Configuration rows
//         unopenable.
//
//  5. The one shape that WOULD put a button's role on the focused node for every
//     reader is a wrapper whose semantics are cleared and re-declared by hand
//     (`clearAndSetSemantics { contentDescription; role = Role.Button;
//     onClick {...} }` on a Box AROUND the Button, where the reset is harmless
//     because the config being reset is the Box's own). It is written down here
//     rather than shipped: it re-declares the click action for every button in
//     the app, it cannot be tested in this container, and if it is wrong a blind
//     user cannot press anything. That is the owner's call to make, not a change
//     to slip in.
// ==========================================================================
