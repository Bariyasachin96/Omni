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
// declaring it, so there is one shape, one code path, and the same first frame
// everywhere.
//
// BOTH BARS ARE STATED EXPLICITLY AGAIN (owner, 2026-09-17), AND THE NOTE THAT
// ARGUED FOR THE BARE CALL IS NOW WRONG. This has flipped twice, so read why
// before flipping it a third time -- the correct answer is entirely decided by
// whether the app follows the system, and nothing else:
//
//   before 2026-09-08  app always dark   -> dark(...) correct, auto() a bug
//   2026-09-08         app follows system -> auto() correct, dark(...) a bug
//   2026-09-17         app always BLACK  -> dark(...) correct again
//
// The mechanism, read from EdgeToEdge.kt in the pinned activity 1.13.0 rather
// than recalled. The bare `enableEdgeToEdge()` defaults to
//     statusBarStyle     = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)
//     navigationBarStyle = SystemBarStyle.auto(DefaultLightScrim, DefaultDarkScrim)
// and `auto` decides the ICON colour with
//     (resources.configuration.uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
// i.e. from the SYSTEM's dark-mode setting. The app no longer asks the system
// anything -- EasyVoiceTheme has one pitch-black scheme -- so on a phone in
// LIGHT mode `auto` would ask for DARK icons and draw them over our #000000
// bar. The clock, the battery and the signal meter would be black on black.
//
// `SystemBarStyle.dark(Color.TRANSPARENT)` states what is actually true of this
// app -- the window behind the bar is dark, so the icons must be light -- and it
// is right on every device and in every system mode, which is exactly the
// property the bare call loses. It is the runtime half of the two
// `windowLight*` flags in values/styles.xml; those dress the first frame, this
// dresses every frame after it, and they have to agree.
//
// TRANSPARENT SCRIMS ARE CORRECT **HERE** AND WOULD NOT BE IN A LIGHT APP.
// androidx defaults the navigation bar to a scrim precisely for pale content
// sitting behind the bar; ours is #000000 everywhere, so a scrim would darken
// black. Both scrim arguments are used only on API 28 and below in any case.
//
// (`SystemBarStyle` and this overload ARE deprecated in androidx-main, pointing
// at WindowCompat.enableEdgeToEdge. They are NOT deprecated in activity 1.13.0,
// which is what this build pins -- `javap` over the pinned jar lists
// `SystemBarStyle.dark(int)` as ordinary public API. This is the rule this
// project already learned the hard way with TabRow and ExposedDropdownMenuBox:
// do not migrate on the strength of androidx-main.)
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
