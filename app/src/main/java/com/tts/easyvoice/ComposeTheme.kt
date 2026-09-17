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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.MotionDurationScale
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass

// THE APP IS PITCH BLACK, AND THE PALETTE IS STILL THE LIBRARY'S (owner, 2026-09-17).
//
//     "Set a pure, pitch-black background across all screens ... use a small,
//      regular-weight light purple font for option labels. For buttons, use a
//      dark purple background with white text."
//
// THIS REVERSES THE 2026-09-08 DECISION recorded here, which took
// `lightColorScheme()` / `darkColorScheme()` unmodified and let
// `isSystemInDarkTheme()` choose between them. It has to reverse it: a light
// purple label is a light purple label on black and nothing at all on white, so
// the request only means one thing if there is ONE scheme. The app is therefore
// black whatever the phone is set to, exactly as it was before 2026-09-08 --
// and the two window themes and the bar-icon style go back with it. Do not
// "restore" the system-following version without the owner asking.
//
// THE OWNER'S OTHER INSTRUCTION WAS TO LOOK FOR THIS IN THE LIBRARY FIRST
// ("pahle to library aur open source se hi hamen pata karna chahie"), so the
// pinned material3-android-1.4.0 jar itself was searched rather than the docs:
//
//     unzip -l material3-android-1.4.0.jar | grep -iE "amoled|contrast|subheader"
//         -> NOTHING. 1,367 classes and not one of them.
//
// So there is no shipped AMOLED scheme, no contrast-level API and no
// list-subheader composable to lean on in this version; `androidx.preference`
// is still View-only at 1.2.1 with no Compose form (checked in Google Maven's
// own group-index.xml), and adopting it would break INVARIANTS #28, which fails
// the build on any `android.view.*`. Nothing to adopt -- but the VALUES below
// are all still the library's, and that is what stops this being a hand-picked
// palette.
//
// EVERY COLOUR IS A MATERIAL TONE, READ OUT OF THE JAR'S OWN BYTECODE.
// `PaletteTokens` builds each tone with `Color(r, g, b)`, so `javap -c` over
// the pinned artifact gives the exact values rather than a recalled hex:
//
//     PaletteTokens.Neutral0    #000000   <- the pitch black. It is a TOKEN.
//     PaletteTokens.Primary80   #D0BCFF   <- the light purple
//     PaletteTokens.Primary40   #6750A4   <- the dark purple
//     PaletteTokens.Primary100  #FFFFFF   <- the white
//
// and two of those already sit where they are needed, which is why the override
// list below is six roles rather than fourteen:
//   * `primary` in the dark scheme IS Primary80. The "light purple" the owner
//     asked for is the colour the library was already handing this app for
//     accents, outlined-button labels, sliders, radios and switches. It is NOT
//     overridden -- the option labels are simply pointed at it.
//   * Primary40 + Primary100 is Material's own `primary`/`onPrimary` pair out
//     of the LIGHT baseline scheme (ColorLightTokens, same javap run). So "dark
//     purple background with white text" is a pairing the library ships and
//     guarantees, not two hexes chosen to look nice together.
//
// MEASURED, against floors of 4.5 for text and 3.0 for a UI component:
//
//     pair                                               ratio   floor
//     body text        onSurface  #E6E0E9 on black       16.20    4.5
//     option label     primary    #D0BCFF on black       12.32    4.5
//     description      onSurfaceVariant #CAC4D0 on black 12.32    4.5
//     outlined label   primary    #D0BCFF on black       12.32    4.5
//     FILLED BUTTON    white on #6750A4                   6.44    4.5
//     app bar title    white on #6750A4                   6.44    4.5
//     button FILL      #6750A4 against the page           3.26    3.0
//     control outline  outline    #938F99 on black        6.63    3.0
//     error text       error      #F2B8B5 on black       12.30    4.5
//     menu text        #E6E0E9 on surfaceContainer       12.57    4.5
//
// Every one clears, and most of them clear by a wide margin -- a pure black
// page is the easiest background in the palette to sit on, which is why this
// change IMPROVES nearly every ratio the table above it used to record.
//
// THE ONE THING THIS RULES OUT, and it is why `primary` is not the dark purple.
// The obvious way to get a dark purple button is to set `primary = #6750A4` and
// let Material's `Button` default to it. That breaks the OUTLINED button, whose
// label is also `primary`: #6750A4 on black is 3.26:1, which is fine for a fill
// and a FAILURE for text. ATF's TextContrastCheck would catch it on the next CI
// run. So `primary` stays the light purple and the filled button states its own
// colours through `ButtonDefaults.buttonColors` -- the library's own hook. See
// EvButton at the bottom of this file.
//
// TWO FILLS STILL READ BELOW 3.0 AND BOTH ARE DECORATION, unchanged in kind
// from the note this replaces: the menu's `surfaceContainer` (1.29 against the
// page) and the divider's `outlineVariant` (2.25). ATF checks TEXT against its
// background and IMAGES against theirs and has NO check for a fill against the
// page, the menu carries 12.57:1 text, and the divider carries nothing. The
// menu is in fact better separated than it was -- on the old #141218 page that
// same fill measured about 1.09.
//
// DYNAMIC COLOUR IS STILL DELIBERATELY NOT USED. `dynamicDarkColorScheme` is in
// 1.4.0 behind @RequiresApi(S) and would derive the palette from the user's
// wallpaper -- which is the opposite of a stated pitch-black theme, and it would
// also mean the accessibility job measured whatever the emulator's wallpaper
// produced instead of what the app ships.
private val EvBlack = Color(0xFF000000)       // PaletteTokens.Neutral0
private val EvDarkPurple = Color(0xFF6750A4)  // PaletteTokens.Primary40
private val EvWhite = Color(0xFFFFFFFF)       // PaletteTokens.Primary100

// Built once, at class-init: darkColorScheme() is an ordinary function, not a
// composable, so there is no reason to rebuild 48 colours on every composition.
private val EvColorScheme = darkColorScheme(
    // The request, literally. Both roles, because `background` is what the
    // Surface below paints and `surface` is what Scaffold, ListItem and every
    // Material container default to -- leaving one of them at Neutral6 would
    // put a #141218 rectangle on a #000000 page.
    background = EvBlack,
    surface = EvBlack,
    // The button pair. `primaryContainer` is already what the top app bar draws
    // (MainActivity), so the bar becomes dark purple with a white title from
    // this one line, and EvButton reads the same pair.
    primaryContainer = EvDarkPurple,
    onPrimaryContainer = EvWhite,
    // The selected filter chip on the Languages screen is the one other filled
    // container the app draws, and Material gives it `secondaryContainer` /
    // `onSecondaryContainer`. Left at the baseline it would be #4A4458 at
    // 2.26:1 against a black page -- and a SELECTED chip's fill is its only
    // boundary, because FilterChip sets selectedBorderColor to Transparent.
    // Pointing it at the same pair makes it 3.26:1 with a 6.44:1 label, and
    // keeps one purple in the app instead of two.
    secondaryContainer = EvDarkPurple,
    onSecondaryContainer = EvWhite,
)

@Composable
fun EasyVoiceTheme(
    // THE PARAMETER IS KEPT THOUGH BOTH VALUES NOW RESOLVE TO THE SAME SCHEME,
    // and that is deliberate rather than a leftover. AccessibilityChecksTest
    // renders every screen twice through it, and the day someone reintroduces a
    // second scheme the sweep starts measuring both again with no test change.
    // Its guard was updated to assert the single scheme rather than a
    // difference -- read the note there before changing either.
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
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
//  evControl: THE ROLE ON THE FOCUSED NODE ITSELF, FOR EVERY SCREEN READER
//
//  Owner, 2026-09-03 and again on 2026-09-04 ("jo asali hal hai vah complete kar
//  hi do"): TalkBack says "button" and "tab", their other reader says only the
//  label. This modifier is the fix, and it is one line at each control instead
//  of a class name bolted on beside a role that is already there.
//
//  WHY THE ROLE WAS NOT REACHING THEM. The delegate writes a role only under
//
//      if (semanticsNode.isFake || semanticsNode.replacedChildren.isEmpty())
//          if (role == Role.Tab)    info.roleDescription = "Tab"
//          else if (role == Switch) info.roleDescription = "Switch"
//          else                     info.className = role.toLegacyClassName()
//
//  and a Material control with a text label always has a semantics child, so it
//  fails that gate. Its role goes instead to a FAKE ROLE CHILD that Compose
//  emits (`SemanticsNode.emitFakeNodes`) and hands to the service as a separate
//  virtual node. TalkBack walks those children. A reader that reads only the
//  focused node finds nothing, which is exactly what the owner reported.
//
//  WHAT THIS DOES, and every step of it is the library's own behaviour:
//    * `clearAndSetSemantics` makes this node's configuration exactly what we
//      declare. `replacedChildren` is then EMPTY for it -- that is the documented
//      contract of the property: "node marked as clearAndSetSemantics will not
//      have children" (SemanticsNode.kt) -- so the gate above PASSES and the role
//      lands on the real, focused node.
//    * `emitFakeNodes` cannot fire either: it is guarded by
//      `unmergedConfig.isMergingSemanticsOfDescendants`, and a cleared config
//      does not merge. So there is exactly ONE node carrying the role, and the
//      "button button" doubling of 2026-09-03 is structurally impossible here.
//    * the label goes on as `contentDescription`, which the delegate assigns
//      whenever the node does not merge descendants -- so the inner Text is no
//      longer read a second time and no longer needs silencing.
//
//  WHY IT IS SAFE, stated because getting it wrong leaves a blind user unable to
//  press anything, and that is the exact failure this file has already caused
//  once:
//    * `clearAndSetSemantics` touches SEMANTICS ONLY. The component's own
//      `clickable` pointer input is untouched, so a finger on the screen still
//      activates the real Material control, with its ripple and its state.
//    * for a screen reader the activation path is `ACTION_CLICK`, and we declare
//      it here: `onClick { action(); true }` puts `SemanticsActions.OnClick` in
//      the cleared configuration and the delegate turns that into
//      `info.addAction(ACTION_CLICK)`. The old bug this file warns about was
//      declaring `onClick(label, action = null)` -- a LABEL with no action, which
//      replaced the real action with nothing. `action` is required here for
//      anything the user can press, so that shape cannot be written by accident.
//    * `enabled = false` adds `disabled()`, which is what the delegate's
//      `semanticsNode.enabled()` test reads before it will add the click action
//      at all. Without it a greyed-out control would still offer activation.
//
//  STATE IS THE LIBRARY'S TOO, not a string we invent:
//    * `toggle` + `Role.Switch` makes the delegate say "On"/"Off" from its own
//      `R.string.state_on` / `state_off`;
//    * `isSelected` on anything that is not a Tab makes it say
//      "Selected"/"Not selected" from `R.string.selected` / `not_selected`;
//    * a selected Tab or RadioButton is deliberately NOT clickable -- the
//      delegate drops ACTION_CLICK for exactly those two roles when selected,
//      because a chosen tab cannot be chosen again. That happens on its own.
//
//  NEVER WRAP A NODE THAT CONTAINS AN INTERACTIVE CHILD. This is the one way to
//  do real damage with it, and it was done once: clearing a node drops its WHOLE
//  SUBTREE from the accessibility tree -- that is what an empty `replacedChildren`
//  means -- so anything focusable inside it simply stops existing for a screen
//  reader. On every control here the subtree is a label and an icon and losing it
//  is the point. The Configuration list row is the exception: its trailingContent
//  is a real three-dot IconButton, and wrapping the row made "More actions for
//  <language>" unreachable, taking Delete configuration and Disable language with
//  it. Build 832's accessibility job is what caught it --
//  `AccessibilityChecksTest.configurationRowMenuOpen` could no longer find that
//  button in the merged tree while the unmerged tree still had it, which is the
//  exact signature of this mistake. That row uses a plain `semantics {}` block
//  instead, which adds without clearing.
//
//  A child passed `onCheckedChange = null` or `onClick = null` is NOT interactive
//  -- that is the whole point of the Material row pattern -- so the Switch inside
//  `SettingSwitch`, the Checkbox inside `LanguageCheckRow` and the RadioButton
//  inside the mode rows are all safe. (The mode row used to carry a "Settings"
//  button too, safe for a different reason -- it was a SIBLING of the cleared
//  Row, not inside it. It moved to the FAB on 2026-09-09, so the mode row now
//  has nothing in it but the RadioButton and its label.)
//
//  WHAT IT IS NOT FOR: anything whose semantics the library already puts on the
//  focused node without help. An `IconButton` is the clearest case -- its
//  `Icon(contentDescription = null)` adds NO semantics modifier, so the button
//  has no semantics children, the gate passes by itself and the class name is
//  already right. A `Slider` is another: `info.className = "android.widget.
//  SeekBar"` is set from ProgressBarRangeInfo with no gate at all. Do not wrap
//  those; there is nothing to gain and a state to lose.
fun Modifier.evControl(
    name: String,
    controlRole: Role? = null,
    enabled: Boolean = true,
    state: String? = null,
    toggle: ToggleableState? = null,
    isSelected: Boolean? = null,
    listItem: CollectionItemInfo? = null,
    action: (() -> Unit)? = null,
): Modifier = this.clearAndSetSemantics {
    contentDescription = name
    // Null for a control the library gives no Role of its own -- a dropdown menu
    // item is the case here. It still gets the name, the position in the list
    // and the click action on the focused node; it simply has no role to state,
    // and inventing one would be a class name bolted on again.
    if (controlRole != null) role = controlRole
    if (!enabled) disabled()
    if (state != null) stateDescription = state
    if (toggle != null) toggleableState = toggle
    if (isSelected != null) selected = isSelected
    if (listItem != null) collectionItemInfo = listItem
    if (action != null) onClick { action(); true }
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

// ==========================================================================
//  EvButton: ONE button type, used everywhere there is a button
//
//  Owner, 2026-09-04: "jo action button diye gaye hain ... vah sahi tarike se
//  jo bhi screen reader agar main use kar raha hun, vah achhe se read kar rahe
//  hain ... yah jo button wale system hai jahan par bhi buttons hai to us type
//  ke buttons laga dene chahie taki har screen reader achhe se read kare."
//
//  So there is now exactly one of them. Every button in the app goes through
//  this function, which means the accessible name, the role, the disabled state
//  and the icon placement are decided in ONE place and cannot drift apart
//  screen by screen -- which is what had happened: eleven call sites each
//  repeating `.semantics { contentDescription = ... }` and each silencing its
//  own label with `clearAndSetSemantics`.
//
//  It is a Material3 `Button` or `OutlinedButton` and nothing else; the only
//  thing added is `evControl`, which is what puts the role on the focused node
//  for a reader that does not walk Compose's fake children. See evControl above
//  for why that is safe and what it does NOT apply to.
//
//  `outlined = true` is the Material emphasis ladder, not decoration: the spec
//  orders the styles elevated > filled > filled tonal > outlined > text, so a
//  screen with two actions uses filled for the one it wants pressed and
//  outlined for the other. `iconRes` is placed LEADING and through the icon
//  slot, which is where the spec puts it ("They should be placed on the leading
//  side of the button, before the label text"), and it carries no description
//  of its own because the label beside it already names the action.
@Composable
fun EvButton(
    label: String,
    modifier: Modifier = Modifier,
    iconRes: Int = 0,
    enabled: Boolean = true,
    outlined: Boolean = false,
    onClick: () -> Unit,
) {
    val described = modifier.evControl(label, Role.Button, enabled = enabled, action = onClick)
    val body: @Composable RowScope.() -> Unit = {
        if (iconRes != 0) {
            Icon(
                painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
        // No `clearAndSetSemantics` here any more, and that is the point: the
        // button's own configuration is cleared, so this Text is already out of
        // the accessibility tree and cannot be read a second time.
        Text(label)
    }
    if (outlined) {
        OutlinedButton(onClick = onClick, modifier = described, enabled = enabled, content = body)
    } else {
        // "For buttons, use a dark purple background with white text" (owner,
        // 2026-09-17), through `ButtonDefaults.buttonColors` -- the library's
        // own hook -- rather than a background drawn by hand.
        //
        // IT READS THE CONTAINER PAIR, NOT `primary`/`onPrimary`, and that is
        // the whole reason the theme overrides `primaryContainer` instead of
        // `primary`. Material's `Button` defaults to primary/onPrimary; making
        // THOSE the dark purple would also repaint the OutlinedButton's label,
        // which is `primary` too, and #6750A4 on a black page is 3.26:1 --
        // fine for a fill, a failure for text, and ATF's TextContrastCheck
        // fails the build on it. Here the pair is #6750A4 with #FFFFFF at
        // 6.44:1, which is Material's own light-scheme primary/onPrimary.
        //
        // Only the two enabled colours are named: the disabled pair is left at
        // ButtonDefaults' own onSurface-at-12%/38%, so a greyed-out button
        // keeps Material's treatment instead of a dark purple that still looks
        // pressable.
        Button(
            onClick = onClick,
            modifier = described,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            content = body
        )
    }
}
