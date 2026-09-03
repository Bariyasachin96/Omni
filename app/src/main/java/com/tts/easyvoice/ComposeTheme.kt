package com.tts.easyvoice
import android.content.Context
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
import androidx.compose.ui.Alignment
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
            modifier = Modifier.fillMaxSize()
        ) { content() }
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

// EVERY screen that is its own Activity must be wrapped in this, and the reason
// is a platform change we had already opted into without handling: **targetSdk
// is 37**, and from Android 15 (API 35) the system draws every app edge to edge
// and IGNORES android:statusBarColor / android:navigationBarColor. Our theme
// still sets both, and on API 35+ neither does anything.
//
// So the content of a screen starts at y = 0, UNDER the status bar. MainActivity
// never showed it because its Scaffold hands `innerPadding` to
// ResponsiveContent, and Scaffold's contentWindowInsets is systemBars -- but
// About, Languages, Mode settings and Voice setup call setContent with nothing
// between the theme and the screen, so their FIRST element sits behind the
// clock and the signal icons. The owner's screenshot of "English (eng) voices"
// overlapping the status bar is exactly that, and it is why the screen's own
// heading could not be reached on their Xiaomi while a Pixel was fine: the
// status bar is taller there, so the whole heading was covered rather than
// peeking out below it.
//
// safeDrawing rather than systemBars, because it also covers the display cutout
// -- a punch-hole or notch is what makes one device's usable top edge lower
// than another's, which is the whole shape of this bug.
@Composable
fun EvScreenInsets(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)) {
        content()
    }
}

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

// Colour and motion -> "Remove animations". Moved here from Theming.kt when the
// last View code went; the startup spinner is the only animation the app owns.
fun animationsEnabled(context: Context): Boolean = try {
    android.provider.Settings.Global.getFloat(context.contentResolver,
        android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f
} catch (_: Exception) { true }
