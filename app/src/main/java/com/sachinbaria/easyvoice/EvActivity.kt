package com.sachinbaria.easyvoice

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
// THE BARS FOLLOW THE SYSTEM AGAIN (owner, 2026-09-22). This has now flipped
// THREE times, so read why before flipping it a fourth -- the correct answer is
// decided entirely by whether the app follows the system, and by nothing else:
//
//   before 2026-09-08  app always dark    -> dark(...) correct, auto() a bug
//   2026-09-08         app follows system -> auto() correct, dark(...) a bug
//   2026-09-17         app always BLACK   -> dark(...) correct again
//   2026-09-22         app follows system -> auto() correct again
//
// The mechanism, read from EdgeToEdge.kt in the pinned activity 1.13.0 rather
// than recalled. The bare `enableEdgeToEdge()` defaults to
//     statusBarStyle     = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)
//     navigationBarStyle = SystemBarStyle.auto(DefaultLightScrim, DefaultDarkScrim)
// and `auto` decides the ICON colour with
//     (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
// which is the SAME predicate `isSystemInDarkTheme()` reads in EasyVoiceTheme.
// So the bar icons and the page behind them cannot disagree about which mode
// the phone is in -- which is exactly the property that was lost while the app
// was black in both modes, and exactly why the explicit dark(...) had to go:
// it pinned LIGHT bar icons, and on a white page in light mode the clock, the
// battery and the signal meter would have been white on white.
//
// THE SCRIMS ARE androidx's OWN and that matters now that the page can be pale.
// `DefaultLightScrim` exists precisely for light content sitting behind a
// translucent navigation bar; stating TRANSPARENT by hand, as the always-black
// version did, would remove it on the one scheme that needs it. Both scrim
// arguments are used only on API 28 and below in any case.
//
// The bare call is therefore not a shortcut -- it is the only form that states
// "this app follows the system", and every value in it is androidx's.
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
