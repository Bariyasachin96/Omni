package com.tts.easyvoice

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge

// EVERY Compose screen in this app extends this, and it exists so that the
// window behaves the SAME on every Android the app installs on.
//
// targetSdk is 37, and from Android 15 (API 35) the system draws every app edge
// to edge whether it is asked to or not, and ignores android:statusBarColor and
// android:navigationBarColor. Below API 35 it does not. Without this class the
// app therefore had two different window shapes -- edge to edge on new phones,
// inset by the DecorView on older ones -- and only one of them ever exercised
// the padding in EasyVoiceTheme. `enableEdgeToEdge()` is androidx's own API for
// declaring it, so now there is one shape, one code path, and the same first
// frame everywhere.
//
// **The two styles are explicit, and the default would have been a real bug.**
// EdgeToEdge.kt's default is
//     statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
// and `auto` picks light or dark icons from `detectDarkMode(resources)`, i.e.
// from the SYSTEM's dark-mode setting. This app is dark in both settings by
// design (there is no values-night; see the palette note in ComposeTheme.kt), so
// on a phone in light mode `auto` would ask for DARK icons on our DARK bar and
// the clock and the signal icons would disappear. `SystemBarStyle.dark(...)`
// states what is actually true of this app and is right on every device.
//
// It also closes a gap the XML theme had: styles.xml sets
// android:windowLightStatusBar but never windowLightNavigationBar, so the
// navigation bar's icon appearance was simply unspecified. Both bars are stated
// here, in one place.
//
// TRANSPARENT scrims are correct HERE and would not be in a light app:
// EdgeToEdge.kt defaults the navigation bar to a scrim for exactly the case
// where pale content sits behind the bar. Our content is always #121212, so a
// scrim would only darken what is already dark.
//
// The padding that keeps content out of those bars is NOT here -- it is one
// line in EasyVoiceTheme, which every screen also goes through. Read the note
// there for why one place is enough.
open class EvActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
    }
}
