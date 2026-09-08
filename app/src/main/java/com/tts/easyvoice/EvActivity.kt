package com.tts.easyvoice

import android.os.Bundle
import androidx.activity.ComponentActivity
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
// declaring it, so there is one shape, one code path, and the same first frame
// everywhere.
//
// THE TWO EXPLICIT SystemBarStyle.dark(...) ARGUMENTS ARE GONE, AND THE NOTE
// THAT ARGUED FOR THEM IS NOW WRONG (owner, 2026-09-08). It said `auto` would
// be a real bug, because `auto` picks the icon colour from the SYSTEM's
// dark-mode setting while this app was dark in both settings -- so in light
// mode `auto` would have asked for dark icons on our dark bar. That reasoning
// was correct for an app that ignored the system. The app follows the system
// now, so the same sentence says the opposite: `dark(...)` would pin light
// icons over a LIGHT app and the clock would disappear.
//
// The bare `enableEdgeToEdge()` is exactly right, and it is not a shortcut --
// its defaults, read from EdgeToEdge.kt in the pinned activity 1.13.0, are
//     statusBarStyle     = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)
//     navigationBarStyle = SystemBarStyle.auto(DefaultLightScrim, DefaultDarkScrim)
// so the scrims are androidx's own recommended values instead of two numbers of
// ours, and they are used only on API 28 and below in any case.
//
// AND THE TWO DETECTORS CANNOT DISAGREE, which is what makes this safe rather
// than merely tidy. `SystemBarStyle.auto`'s default is
//     (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
// and Compose's `isSystemInDarkTheme()`, which EasyVoiceTheme uses to pick the
// scheme, is
//     (LocalConfiguration.current.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
// -- the same predicate on the same field. The bar icons and the app's colours
// are therefore decided by one signal, so they cannot end up describing
// different themes.
//
// (`SystemBarStyle` and this overload ARE deprecated in androidx-main, pointing
// at WindowCompat.enableEdgeToEdge. They are NOT deprecated in activity 1.13.0,
// which is what this build pins -- checked in that version's api file, which is
// the rule this project already learned the hard way with TabRow and
// ExposedDropdownMenuBox. Do not migrate on the strength of androidx-main.)
//
// The padding that keeps content out of those bars is NOT here -- it is one
// line in EasyVoiceTheme, which every screen also goes through. Read the note
// there for why one place is enough.
open class EvActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
    }
}
