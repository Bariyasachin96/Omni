package com.tts.easyvoice
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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

@Composable
fun ResponsiveContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(modifier = Modifier.widthIn(max = 840.dp).fillMaxWidth()) { content() }
    }
}

// Colour and motion -> "Remove animations". Moved here from Theming.kt when the
// last View code went; the startup spinner is the only animation the app owns.
fun animationsEnabled(context: Context): Boolean = try {
    android.provider.Settings.Global.getFloat(context.contentResolver,
        android.provider.Settings.Global.ANIMATOR_DURATION_SCALE, 1f) != 0f
} catch (_: Exception) { true }
