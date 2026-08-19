# EasyVoice — Claude Session Context

## Project Overview
- **App name**: Easy Voice — Android TTS screen reader for 100% blind users
- **Goal**: Exactly match AutoTTS (com.vnspeak.autotts) behavior in all modes
- **All source code** is embedded as Python string literals in `.github/workflows/build.yml`
- **Working branch**: `claude/yaml-file-nk3czh`
- **Build**: Manual `workflow_dispatch` trigger on GitHub Actions — must trigger manually after each push

## HARD RULES (NEVER violate)
1. **NEVER build/push without explicit user request**
2. **Fix #16 (Disable engine handling) — IMPLEMENTED 2026-07-10** (user override: "exactly AutoTTS"). `m.o()` (in `m.c` and not disabled, **no engine test**) is what `clsCLD2.b` accepts a detection with and what `a.e(cp, m.f)` filters by; on our side that is the `detectOk`/`enabledOk` arrays handed to the native detector. The auto and mix span resolution use a *different* test — `M(lang)` empty or `"Disable"` — which is `isLangRoutableRaw`. Do not merge the two. Dual mode untouched (type-based, no k.o). Previously set-aside; no longer.
3. **Always develop on branch `claude/yaml-file-nk3czh`**
4. **After every push, manually trigger GitHub Actions** (workflow_dispatch, workflow ID: 262884892)
5. **ZERO OWN DECISIONS — fix EXACTLY like AutoTTS, always. This is the #1 rule, blink on it every single edit.** I have NO independent decision, EVER. The user has said this many times; never make them say it again. Concretely:
   - NEVER add anything AutoTTS does not have, and NEVER remove/change anything AutoTTS has — no matter how "small", "big", "cosmetic", or "internal".
   - FORBIDDEN justifications for diverging: "minimal risk", "behaviorally same", "cosmetic/inaudible", "already equivalent", "defensive", "robustness", "hardening", "prevents a hang/leak/stray", "efficiency", "cleaner", "safer", "structural necessity". If AutoTTS does X — even if X looks like a bug — do EXACTLY X. If AutoTTS does NOT do Y, do NOT do Y.
   - Before ANY behavioral edit: find the exact AutoTTS source, and mirror it byte-for-byte / field-for-field / order-for-order (UI strings, control flow, defaults, method calls, side effects, everything).
   - Saying "already fine" / "inaudible" / "I'll leave it (my decision)" without a full AutoTTS source match IS a violation.
   - Past self-inflicted violations already reverted (commit 3b02624): multilingual whitespace-chunk skip, multilingual empty→emptyList, speakRunnable stop-guard, initAllTTS restoringIndex reset, RestoreInitListener captured-idx guard, restoreEngine field pre-clearing, bindEngineKeepAlive in restore-listener. Do not reintroduce this class of "improvement".
   The user's decision IS AutoTTS's actual behavior; there is no other source of truth.
6. **NO GUESSWORK, ANYWHERE (user rule, 2026-07-29). Every line must trace to AutoTTS source.**
   - I may NEVER "infer", "assume", "reason that it is equivalent", or fill a gap from what seems
     sensible. If I have not SEEN the exact AutoTTS code for a behaviour, I do not write that
     behaviour — I go and find it (CFR → smali → arm64 disassembly of the `.so`).
   - "Behaviourally equivalent", "same outcome either way", "unreachable in practice",
     "redundant but harmless" are NOT reasons to keep my own version. Write what AutoTTS writes.
   - This covers the WHOLE app, every mode (none/dual/auto/google/mixed/multilingual), the
     service, the native code, the UI, the manifest and the resources — not just the parts under
     discussion.
   - Where I previously guessed and later verified, the verification must be recorded (commit
     message or `autotts_reference/ANALYSIS_5.7.7.10_mix.md`) so it is never re-guessed.
   - Known past guesses, all since replaced by verified ports: span cap (now confirmed
     `mov w3, #0x80` = 128), the multilingual latin flag (now `script == 1`), the emitter's
     merge/cap-stretch, `y.g`'s defType, the mix two-stage fallback, `a.c` vs `a.b` selection.
7. **CFR FIRST, SMALI ONLY AS FALLBACK (user rule, 2026-07-29).** Reading order is fixed:
   - **Always start with the CFR `.java` files** in `autotts_reference/decompiled_java/`. Read the
     WHOLE relevant file(s) — all methods, big ones included, unfiltered, byte-by-byte.
   - **Fix everything that CFR alone makes clear.** CFR's accuracy is very high; do not second-guess
     it or go hunting in smali "just to be sure". That wastes the pass and is what the user objected to.
   - **Open smali ONLY when CFR is actually broken or unreadable** for that specific spot — e.g.
     `** GOTO lblNNN`, `// 2 sources`, `** continue`, `ConfusedCFRException`, an obviously wrong
     `varX = varY` alias, or a control-flow shape that cannot be understood as written.
   - When CFR *is* clear, smali is not needed and must not be the basis of the fix.
   - Practical note: CFR often renders as ONE expression what smali scatters across many `cond_*`
     labels (e.g. the onSynthesizeText mode gate) — that is precisely why CFR comes first.
   - Regenerate CFR with the commands in `autotts_reference/README.md`; for stubborn methods add
     `--forcetopsortnopull false --aexagg true` before falling back to baksmali.

8. **HOW RULE 7 IS ACTUALLY EXECUTED (added 2026-07-29 after the user caught me skipping it).**
   The failure mode is not disagreeing with the rule — it is starting to EDIT before the CFR
   read is finished. So the order is mandatory and has no shortcut:
   1. **List the chain first.** Name every CFR file and every method the feature touches
      (the fragment method, the layout XML, the strings, every helper class it calls, and the
      service fields it writes). Write that list down before touching an editor.
   2. **Read all of it in CFR.** Whole methods, no filtering, including the ones that look
      boring. Aliases like `CheckBox cb = findViewById(...)` followed by `other.setChecked(...)`
      are exactly the bugs worth finding — CFR shows them plainly.
   3. **Only then edit.** No file is modified until step 2 is complete for the whole chain.
   4. **Smali is a last resort, and the reason gets recorded.** Allowed only when CFR itself
      says it failed (`ConfusedCFRException` / "Decompilation failed") or renders something
      unreadable. If a smali trip only confirms what CFR already showed, that is a wasted pass
      — say so in the commit so it is not repeated.
   Violations to date: went to smali for `c3.j.R2()` although CFR had already rendered the
   `object` / `checkBox` alias correctly; began editing the Advanced tab before finishing the
   CFR read of `j.E2/G2/H2/J2/M2/N2/B2/P2/Q2`, `c3.t`, `c3.u`, `c3.b0`.

9. **NEVER call `AskUserQuestion` (user rule, 2026-08-06).** The option cards it renders got
   stuck in the user's chat, kept reappearing with a submit button, and blocked them from
   typing. It is denied in `.claude/settings.json` and `~/.claude/settings.json`, but those
   are gitignored/ephemeral, so this rule is the durable record. If something genuinely needs
   the user's decision, ask it as one plain sentence in the reply — no tool, no cards. Same
   for `ShowOnboardingRolePicker` and anything else that renders an interactive prompt.

## UI departures from AutoTTS (user decision, 2026-08-06)
The user has taken the **user interface** out of the AutoTTS-parity rule: *"ab mere hisab se
… sirf user interface change karna hai"*. Logic, service, detection and storage stay exactly
AutoTTS. Only the UI may differ, and only where the user asks.

Recorded so far:
1. **Modes tab — per-mode collapse/expand settings.** AutoTTS shows the active mode's settings
   inline at the bottom, below all five radios and their descriptions, so a TalkBack user
   swipes past everything to reach a spinner. Ours keeps the list exactly as AutoTTS has it —
   **each radio followed by its own description paragraph, as separate `TextView`s** — and adds
   a **collapse/expand disclosure** for the settings:
   - the **selected** mode's settings button sits **on the same row as its radio button**,
     right-aligned, labelled `"Settings"` with `contentDescription = "<Mode> settings"`;
     every other mode's button is `GONE`, and "None" has none. (It used to be a full-width
     button under the description - the user rejected that as too heavy, 2026-08-12.)
   - pressing it expands that mode's settings **in place**, directly beneath the button. No
     dialog, no second screen, no navigation.
   - state is exposed with `ViewCompat.setStateDescription(toggle, "Expanded"/"Collapsed")` —
     the approach Android's accessibility docs prescribe for disclosure controls — plus an
     `announceForAccessibility` on each toggle. Name comes from the button text, role from
     Button, state from stateDescription: WCAG 4.1.2 satisfied.
   - switching modes collapses the mode you left.
   Three things were tried and **rejected by the user after testing — do not reintroduce**:
   descriptions folded into the radio's `contentDescription` (TalkBack then reads a whole
   paragraph before you can move on), the settings in an `AlertDialog` (the list showed
   through above and below it), and the settings as a separate full page (navigating away is
   unnecessary for this).
   The four sections, their spinners, checkboxes and every handler are unchanged; only where
   they are shown moved. Do NOT "restore" this to AutoTTS's bottom-of-page layout.
2. **Voices tab replaced by "Configuration settings" — two screens (user request,
   2026-08-13).** AutoTTS has five tabs; we now have two. The Voices tab first became a
   collapse/expand section on the Modes tab, and **on 2026-08-13 the user replaced that
   toggle with a "Configuration settings" button** that opens a real screen, because the
   inline section felt heavy: *"voice wala jo collapse button hai use jagah per configuration
   settings ka button add kar do … us per click karne se … language ki list … kisi bhi ek
   language per click karunga to vah wala screen khulega jismein voice variant … slider"*.
   - **`ConfigurationActivity`** — a **"Languages" `ExtendedFloatingActionButton`, anchored
     bottom-end**, labelled **"Add language"** (user, 2026-08-13: *"languages ka button
     bottom right corner per hona chahie … primary action"*, and *"languages button ka
     content description change karna hai add language"*). The **visible text** was changed
     rather than only the `contentDescription`: WCAG 2.5.3 *Label in Name* wants the
     accessible name to contain the visible label, so a button reading "Languages" but
     announcing "Add language" would be a defect. Setting the text makes both the same
     string. Google's own wording: a FAB "lets the user perform a primary
     action" and is "typically found anchored to the bottom right"; the **extended** variant
     carries a text label, and that label is the accessibility affordance, so it stays a
     labelled "Languages" control rather than a bare icon. The list scrolls under it with
     `clipToPadding = false` + 88dp bottom padding so the last row is never covered. It is
     **`GONE` unless the mode has a language list**, i.e. never for `dual`/`none` (user:
     *"vah button rakhne ki jarurat kya hai"*), because `buildLanguagesTabView` answers those
     two with "Language selection is not available…" and the button would only ever lead to
     that sentence. Below it are the languages configured for the current
     mode, one button per language, from `voiceLanguageLabels(this, modeInt)`: dual gives
     the two `dualLangList` entries, every other mode gives its selected languages. It
     rebuilds in `onResume`, so returning from the Languages screen or a voice screen, or
     changing the mode, is picked up. The Main Settings tab therefore shows only
     "Configuration settings"; Languages is one level in, not duplicated in both places.
   - **`VoiceSetupActivity`** — the per-language voice screen, `buildVoicesTabView(this,
     prefs, { testTts }, langIndex)` with the same `singleLangIndex` pin the wizard uses, so
     engine/voice, variant, Test, speed, volume, pitch, Default and dedicated engines are
     the untouched Voices code. Its `title` (what TalkBack announces) is read from
     `LangStore.languages` **after** the view is built, in `dualLanguageLabels`'s exact
     `displayName + " (" + iso3 + ")"` format, so no second rebuild is needed.
   - The index passed as `lang_index` indexes `LangStore.languages`, matching the assumption
     the Voices language spinner already makes (`onLanguageSelected(position)` indexes the
     same list the labels came from). Do not "fix" that mapping here alone.
   - Voice, variant, speed, volume and pitch stay **per-language** settings that every mode
     reads (`speedFor`/`pitchFor`/`volumeFor`/`variantFor`), which is why this lives beside
     the modes rather than inside one of them.
   - `buildVoicesTabView` still returns its content root instead of a `ScrollView`; each
     screen wraps it. Every entry point (`buildLanguagesTabView`, `buildVoicesTabView`,
     `voiceLanguageLabels`) does its **own** `LangStore` rebuild — `onlyEnabled = false` for
     the Modes tab and the Languages screen, `onlyEnabled = true` for the voice list — so
     whichever one runs last leaves `LangStore.languages` correct for itself.
     Expanding a mode's settings calls `refreshModeLanguages(mode)` for the same reason.
   - `pageTitles`/`pageIcons` dropped and `ic_tab_voices.xml` is gone with them.
3. **Languages tab folded into the Modes tab, per mode.** Now two tabs: Modes and Advanced
   - the **Licenses tab was removed on 2026-08-12**; `buildLicensesTabView` is kept in
   `TabViews.kt` unused, because the user intends to place it somewhere else. Unlike Voices, the language list **is** per-mode (`buildLanguagesTabView` reads
   `prefs.getReadingMode()` and builds `modeInt`/`required` from it), so it sits **inside each
   mode**, in the order the user asked for: radio → its description → **"Languages"
   collapse/expand** → **"<Mode> settings" collapse/expand**. Only modes that support a
   language list get the button — auto, mix, multilingual (google is `GONE` and shares auto's
   holder via `holderMode`); **dual has none**. Since 2026-08-12 the button lives **inside**
   that mode's expanded settings section, under the mode settings, not directly beneath the
   radio, which is the inline equivalent of the old tab's
   "not available for None and Dual" message. Rebuilt on each expand, same `LangStore.languages`
   reason as Voices. `ic_tab_languages.xml` is gone.
   The list itself changed from `ListView` to a `LinearLayout` of `CheckBox` rows — **required**,
   because a `ListView` cannot measure inside the Modes tab's `ScrollView`. All the underlying
   logic is unchanged (`checkedFlags`, `visibleIdx`, `requiredNow()`, `entry.disabled`,
   `persistDisabled`, search, select-all, clear-all, show-selected). `setRowChecked` sets
   `suppressRowEvents` so a programmatic check does **not** re-enter the handler — that
   reproduces `ListView.setItemChecked`, which never fired `onItemClick`.
4. **"None" mode removed from the radio list** (user: *"None ki koi jarurat hi nahi … hata hi
   dena hai"*). Only the UI option is gone — mode 0 still exists in the store and the service,
   and `buildVoicesTabView`'s `readingMode == "none"` guards stay. Because
   `getReadingMode()` returns `"none"` by default when Google TTS is absent (`auto_mode` 3 → 0),
   `buildModesTabView` maps a stored `"none"` to `"auto"` — the same fallback
   `getReadingMode()`/`setReadingMode()` already use for an unrecognised value — otherwise the
   screen would open with nothing selected and no settings reachable.

7. **Tab title, list buttons and switches (user request, 2026-08-12).**
   - The first tab is titled **"Main Settings"**; the section header inside it still says
     "Modes", because that header labels the radio group, not the tab.
     It was "Main Settings Tab" until 2026-08-13, when the user heard TalkBack say "tab"
     twice: the accessibility role is appended by the service, so a title ending in "Tab" is
     announced as "Main Settings Tab, Tab 1 of 2". **A label must never contain its own role
     word.** Swept the rest of the app for the same mistake — no other user-facing string
     contains button/tab/switch/checkbox/slider/dropdown/menu/radio.
   - **Languages and Voices sit in one row** at the bottom of that tab, Languages first, each
     at half width. Languages **opens its own screen** — `LanguagesActivity`, a plain
     `ComponentActivity` that puts `buildLanguagesTabView` in a `ScrollView`, declared with
     `android:label="Languages"` so TalkBack announces it on entry, and closed by the system
     back gesture. This replaced an inline disclosure the user rejected on 2026-08-12: they
     want the whole separate screen AutoTTS's Languages tab gave. The per-mode Languages
     buttons, holders and `applyLanguagesExpandState` are gone. The second button in that
     row was "Voices" (collapse/expand) until 2026-08-13; it is now **"Configuration
     settings"**, which opens `ConfigurationActivity` — see item 2.
   - **Every checkbox is a `MaterialSwitch`** (`EvSwitch` typealias, `evSwitch(context)`
     factory in `Theming.kt`): the nine Advanced rows, "Use locale spans", "Use dedicated
     engines" and the language-list rows. They are still `CompoundButton`s, so
     `setRowChecked`, `suppressRowEvents` and every existing handler are unchanged.
     `applyAccessibleTheme` gained an `EvSwitch` branch that tints thumb, track and track
     decoration instead of the button drawable — checked thumb `#00325A` on a `#82C7FF`
     track (7.2:1), unchecked `#4FD8EB` thumb and outline on `#2A2D31` (8.1:1 thumb,
     11.0:1 outline on the page) so an off switch is still clearly visible.
   - The CLD3 row's description now leads on performance.

5. **Material 3 accessible dark theme (user request, 2026-08-12).** `AppTheme` now extends
   `Theme.Material3.Dark.NoActionBar` with a fixed dark palette — there is no `values-night`
   override any more, because the theme is dark in both modes by design. The palette lives in
   `res/values/colors.xml` and, for code-built views, in `AppPalette` (`Theming.kt`):
   background `#121212`, surface `#1E1F22`, section headers `#1B2A38`, text `#FFFFFF`,
   primary `#82C7FF`, on-primary `#00325A`, bright control outline `#4FD8EB`.
   Measured contrast: white on background **18.7:1**, light blue on background **10.3:1**,
   button label on button fill **7.2:1**, checkbox border on background **11.0:1** — all WCAG
   AAA; the disabled grey is 3.9:1, above the 3:1 UI-component floor.
   Because every view is built in code, `applyAccessibleTheme(view)` walks the finished tree
   and styles by type — compound buttons get a `buttonTintList` that is bright cyan unchecked
   and light blue checked, buttons get a light-blue fill with dark label text, seek bars get
   a light-blue track and thumb, spinners get a dark popup, and the `SearchView`'s internal
   text, hint and icons are recoloured. `applyPageTheme` adds the background and is what each
   `build*TabView` returns. It is re-run wherever rows are rebuilt (the Languages list filter,
   the required-engines dialog) so late views are covered too. Spinner rows use
   `res/layout/ev_spinner_item.xml` and `ev_spinner_dropdown_item.xml` — white on dark, 48dp
   minimum height, and the dropdown wraps instead of truncating.
   Every control is forced to a 48dp minimum touch target. No `contentDescription`,
   `stateDescription`, `announceForAccessibility` or live region was touched — this change is
   colour, size and background only.

6. **Instant speech start — the two 50 ms posts removed (user request, 2026-08-12).**
   AutoTTS posts the first `speak()` with `t.postDelayed(a, 50L)` and the next-chunk step
   with `b.postDelayed(e$a, 50L)`. The user swipes with TalkBack and wants speech to start
   with no added delay, so **both delays are gone** — this is a deliberate departure and
   must not be "restored" to AutoTTS.
   - First chunk: `speak()` is now called **inline on the synthesis thread**, no handler.
     `onSynthesizeText` already runs off the main thread and is documented to block there,
     and `TextToSpeech` is internally synchronised (`runAction` takes `mStartLock`;
     `mUtteranceProgressListener` is `volatile`, "written from an unspecified application
     thread, read from a binder thread"), so `speak`/`setSpeechRate`/`setPitch`/
     `setOnUtteranceProgressListener` carry no main-thread requirement. Program order on one
     thread gives the listener-before-speak ordering the hop used to provide.
   - Later chunks: the main-thread hop **stays**, at zero delay (`post`, not `postDelayed`).
     It is not cosmetic — `onDone` arrives on a **binder thread** and the next step can create
     or shut down a `TextToSpeech`, which must not happen inside an engine callback.
   - Side benefit: the old 50 ms window let a `stop()` be followed by a `speak()`, because the
     runnable has no stop guard (and must not get one — that was reverted in `3b02624`).
     Inline, the existing `if (isStopped || isFlushed) return` sits immediately before
     `speak()`, so the stray-utterance window closes on its own.

8. **Setup wizard — REMOVED ENTIRELY (user request, 2026-08-18: *"setup wizard hamen
   kahin per bhi rakhna nahin hai, setup wizard pura hata do"*). Do NOT reintroduce it,
   and do not treat anything below as current.** Gone with it: `SetupWizardActivity.kt`
   (including `ModeChoiceStep`), its manifest entry, the launch from
   `MainActivity`'s scan callback, the Advanced tab's whole "Setup" section and its
   `ic_auto_fix` icon, and `isSetupDone()`/`setSetupDone()` — the `setup_done` pref now
   has no reader. The description below is kept only as a record of what once existed.

   ~~**Setup wizard (user request, 2026-08-12).**~~ `SetupWizardActivity` — a plain
   `ComponentActivity` with a **dynamic step list**, one heading + a scrolling body + a
   Back/Next row. The steps are
   **mode → settings → languages → one voice step per language**:
   - `baseIds()` is `["mode","settings","languages"]`, but **the languages step is dropped
     for Dual** (user request, 2026-08-12: *"dual board select karega to … vah language wala
     step nahin aaega"*), mirroring `buildLanguagesTabView`, which answers "Language
     selection is not available for \"None\" and \"Dual languages\" modes." for exactly
     `dual`/`none`.
   - `stepIds()` = `baseIds()` + **one `"voice"` step per entry of `voiceLangLabels`**
     (user request, 2026-08-12: *"jo bhi selected language hogi uske TTS setup aaega …
     Charon language ke liye अलग-अलग next karna padega"*). Dual therefore ends with exactly
     two voice steps — English and the dual language — because `LangStore.dualLangList`
     returns only those two.
   - `voiceLangLabels` comes from **`voiceLanguageLabels(context, modeInt)`**, which was
     lifted out of `buildVoicesTabView`'s local `applyLanguageList()` so the wizard and the
     Voices section share one list; `applyLanguageList()` now just calls it and sets the
     adapter. It is re-read at the top of `showStep()` and again before Next decides
     finish-vs-advance, so toggling languages immediately changes the step count, the
     "step N of M" heading and the Next/Finish label.
   - A voice step is `buildVoicesTabView(this, prefs, { testTts }, voiceIndex)` — the new
     **`singleLangIndex`** parameter hides the "Select language" label and its spinner and
     pins the view to that one language (`setSelection` + `onLanguageSelected`). Everything
     else is the untouched Voices code: engine/voice spinner, variant spinner, Test, speed,
     volume, pitch, Default, dedicated engines. `addSmallText` now returns its `TextView`
     so the label can be hidden.
   - The wizard owns a `TextToSpeech(this, null, "com.tts.easyvoice")` exactly like
     `MainActivity.newTestClient()`, so **Test speaks** on the settings and voice steps.
   - Steps 2 and 3 embed `buildModesTabView(..., settingsOnlyForMode)` and
     `buildLanguagesTabView(this, prefs)` — the same functions the Main Settings tab and
     `LanguagesActivity` use, nothing duplicated. Both read `prefs.getReadingMode()`, which
     is why `setReadingMode(chosenMode)` is written when **leaving step 1**.
   - Each of `buildLanguagesTabView`, `buildVoicesTabView` and `voiceLanguageLabels` does
     its **own** `LangStore` rebuild (`onlyEnabled = false` / `true`), so the order in which
     the wizard visits them does not matter — the same reason the tabs rebuild on each open.
   - `stepIndex` is clamped to `ids.lastIndex` in `showStep()`, for the case where the step
     list shrinks (a language disappearing while the wizard is open).
   - **Swipe navigation (user request, 2026-08-13; reworked the same day for TalkBack).**
     A horizontal swipe on the step body moves between steps, and the Back/Next buttons stay.
     Both paths call the same `goBack()`/`goNext()`, so there is one implementation.
     `GestureDetector` was avoided on purpose: `onFling`'s first parameter became `@Nullable`
     in a later API than the API-15 check jar exposes, so the override signature could not be
     verified locally.
     **What the first attempt got wrong.** Google's TalkBack help states that with TalkBack on
     *"most one-finger gestures become two-finger gestures … put two fingers on the screen and
     drag"*. The first version read `event.action` and `event.x`, which describe **pointer 0
     only** — under a two-finger drag the tracked finger can lift as `ACTION_POINTER_UP` while
     `event.x` reports whichever pointer currently sits at index 0, so the measurement was
     wrong exactly in the TalkBack case the user tested. It now uses `actionMasked`, records
     the pointer id from `ACTION_DOWN`, settles on whichever of `ACTION_UP`/`ACTION_POINTER_UP`
     lifts **that** id, reads `getX(actionIndex)`, and resets on `ACTION_CANCEL`.
     **The documented fix, which the gesture alone can never be.** For gesture-only flows the
     Views guidance is `ViewCompat.addAccessibilityAction(view, label, action)` — *"your app
     can expose the actions in a way that is accessible to users of accessibility services"*.
     `stepHeading` therefore carries **"Next step"** and **"Previous step"** actions, so
     TalkBack, Voice Access and Switch Access users reach them from the Actions menu on the
     heading they already land on at every step. The actions are added **once** in `onCreate`,
     not per step — `addAccessibilityAction` allocates a new action id per call, so repeating
     it would stack duplicates.
     Source: `support.google.com/accessibility/android/answer/6151827`,
     `developer.android.com/guide/topics/ui/accessibility/views/principles-views`.
   - **Next must stay snappy (user request, 2026-08-13: *"next karta hun to thoda bhari
     bhari sa lagta hai"*).** `refreshVoiceLangs()` runs **once per Next** — in the Next
     handler, before it decides finish-vs-advance — plus once before the first `showStep()`.
     It used to also run at the top of `showStep()`, so every press rebuilt the language list
     twice. Each rebuild goes through `LangStore.persistLanguages`, whose `editor.commit()`
     is a synchronous main-thread disk write, so the duplicate was felt. `commit()` itself
     must NOT be changed to `apply()` — that is AutoTTS-mirrored storage code. Back does not
     refresh at all, since it cannot change the language selection.
   - **2026-08-13, second pass on the same complaint.** Two more sources of the stall:
     - `refreshVoiceLangs()` now runs **only while on a base step**
       (`if (stepIndex < baseIds().size)`). Moving between two voice steps cannot change the
       language list, so the rebuild — and its `commit()` — was pure waste on exactly the
       presses the user makes most.
     - the Voices view is **built once and re-pinned**, not rebuilt per language. The new
       `VoicePin` holder is handed to `buildVoicesTabView`, which fills `pin.select` with
       the same `setSelection` + `onLanguageSelected` pair the language spinner uses. So the
       2nd..Nth voice step costs one language switch instead of a whole view construction
       plus two `commit()`s. `refreshVoiceLangs()` clears `voicesView`/`voicePin.select`, so
       changing the language selection still forces a fresh build.
   - Step 1 lists **every mode** as a radio (Google TTS skipped when
     `com.google.android.tts` is absent), each followed by its description — the same
     strings the Main Settings tab uses, because `modeRowSpecs` was lifted to a **top-level
     `val` in `TabViews.kt`** and both read it. Exclusivity is manual (a `suppress` flag +
     a loop clearing the others), as the rows are not in a `RadioGroup`.
   - Next → step 2 calls `prefs.setReadingMode(chosenMode)` and shows **that mode's own
     settings**, by reusing `buildModesTabView` through a new
     `settingsOnlyForMode: String? = null` parameter. When it is non-null the function skips
     the "Modes" header, the radio rows, the descriptions and the Languages/Voices row, then
     does `onModeSelected(settingsOnlyForMode)`, hides that mode's toggle button and force-
     expands its section. Nothing is duplicated — the wizard runs the already-verified
     settings code.
   - Next reads "Finish" on step 2; it writes the mode, `prefs.setSetupDone(true)`,
     `LangStore.persistAll` and finishes. Back on step 2 returns to step 1; on step 1 it
     closes the wizard.
   - Accessibility: the heading is a real heading (`ViewCompat.setAccessibilityHeading`) and
     each step change calls `announceForAccessibility` on it, so TalkBack states which of the
     two steps you are on.
   - First run only: `MainActivity` launches it from the `EngineFinder.scanLanguages`
     completion callback when `!prefs.isSetupDone()` — after the scan, so the mode settings
     have languages to show. The flag is `setup_done` in `SharedPrefsManager`.
   - Re-runnable: the Advanced tab's **first** section is now "Setup", with a
     "Setup wizard" button that starts the activity again.

## Responsive layout + touch targets (user request, 2026-08-13, guidelines read first)
Sources read for this, not recalled: `developer.android.com/develop/ui/views/layout/window-size-classes`,
`.../guide/topics/ui/accessibility/apps`, `.../design/ui/mobile/guides/layout-and-content/grids-and-units`,
`.../docs/quality-guidelines/large-screen-app-quality`.

What they actually say, with the numbers:
- **Width window size classes**: compact `< 600dp`, medium `600–839dp`, expanded `840–1199dp`,
  large `1200–1599dp`, extra-large `≥ 1600dp`. Google's advice is to "optimize your layout for
  the expanded width size class" first.
- **Touch targets**: "at least 48dp×48dp. Larger is even better."
- **Baseline grid**: 8dp, with 4dp for finer steps.
- **Text**: `sp`, so it follows the user's font-size setting.
- **Large-screen tiers**: Tier 3 is running full screen without letterboxing; **Tier 2 is
  "layout optimizations implemented for all screen sizes"**. Recommended test sizes:
  841×701, 1024×640, 1280×800, 1600×900 dp.

What was already satisfied, verified rather than assumed:
- the manifest sets no `screenOrientation` and no `resizeableActivity`, and `targetSdk` is 34,
  so activities are resizeable and rotate freely — Tier 3;
- `applyAccessibleTheme` already forces `minimumHeight = 48dp` on `EvSwitch`, `CompoundButton`,
  `Button` (plus `minimumWidth`), `SeekBar` and `Spinner`, and it walks the whole tree from
  `applyPageTheme`, so every screen is covered — including the small `-`/`+` slider buttons,
  which are `Button`s and so are lifted from `buttonStyleSmall` back to 48dp;
- every text size comes from `setTextAppearance(android.R.style.TextAppearance_*)`, i.e. `sp`.

What was missing and is now fixed:
- **Tier 2.** Every page was a full-width `ScrollView`, so on a 1280dp tablet or a 1600dp
  Chromebook each row stretched edge to edge. `applyResponsiveWidth(root)` now sets symmetric
  horizontal padding of `(screenWidthDp - 840) / 2` whenever the window is wider than the
  **expanded breakpoint**, so the content column is capped at 840dp and centred; below 840dp
  nothing changes and the single pane fills the window. `840` is the documented expanded
  breakpoint, not an invented number. It reads `resources.configuration.screenWidthDp`, which
  is the *window* width (correct in split-screen), and the activities are recreated on
  configuration change because no `android:configChanges` is declared, so it recomputes on
  rotate/fold/resize. Applied at every full-screen `setContentView`: `MainActivity`,
  `SetupWizardActivity`, `ConfigurationActivity`, `VoiceSetupActivity`, `ModeSettingsActivity`,
  `LanguagesActivity`. The required-engines **dialog is deliberately excluded** — dialogs are
  already width-constrained by the platform.
- Setting rather than adding the padding keeps `applyResponsiveWidth` idempotent; no page root
  carries horizontal padding of its own, checked before relying on that.
- The `SearchView`'s clickable icons (`search_close_btn`, `search_button`, `search_go_btn`,
  `search_voice_btn`) now get the 48dp minimum too; `search_mag_icon` is decorative when the
  view is permanently expanded, so it is only recoloured.

## Full UI audit against Google's guidelines (user request, 2026-08-13)
*"pura user interface mein kahin per bhi Google ke khilaf kuch ho to usko sahi kar dijiyega"*.

**Contrast — computed, not eyeballed.** Every palette pair was run through the WCAG relative
luminance formula. All active pairs pass: body text on background **18.7:1**, on surface
16.5:1, secondary text 14.6:1, white on the section header 14.6:1, button label on fill
7.2:1, accent on background 10.3:1, bright outline 11.0:1, checked thumb on track 7.2:1,
unchecked thumb on track 8.1:1 — against floors of 4.5 (text) and 3.0 (UI components).
Four pairs land below their floor and **all four are exempt**: the disabled button label
(2.9), disabled text (3.9) and the disabled switch thumb (2.9) are inactive components, and
WCAG 1.4.3 states *"Text … that are part of an inactive user interface component … have no
contrast requirement"*; `surface` vs `background` (1.1) is the spinner popup's own fill,
which no criterion requires to be distinguishable — the text on it is 16.5:1 and the popup
carries elevation. The hardcoded green `#4CAF50` on "Installed" is 6.7:1, and the state is
carried by the words "Installed"/"Not installed", not by colour alone.

**Fixed in this pass:**
- **`android:supportsRtl="true"` was missing.** It defaults to false, so the layout never
  mirrored for Arabic, Urdu or Hebrew users — languages this very app reads. Now declared.
- **Two RTL-unsafe spots** in the required-engines dialog: `rightMargin`/`leftMargin` on the
  Apply/Cancel buttons became `marginEnd`/`marginStart`, and
  `notInstalledLabel.setPadding(0, 0, 16, 0)` became `setPaddingRelative`.
- **Raw pixels instead of dp** throughout that dialog (`48/32/16`). AutoTTS's `c3.u` uses raw
  px too (lines 99/104/141/157), so our port was faithful — but px is density-dependent and
  the units guidance is dp, so under the UI carve-out it is now `dialogDp(…)`. `dialogDp` is
  a **class member**, not a local of `buildContent()`, because `renderRows()` needs it as
  well — the kotlinc+android.jar check caught that as a genuine `unresolved reference`.
- **The app-bar icon duplicated the app name.** It carried
  `contentDescription = app_name` while the `TextView` beside it shows the same string, so
  TalkBack said "Easy Voice" twice. It is decorative, so it is now `contentDescription = null`
  plus `IMPORTANT_FOR_ACCESSIBILITY_NO`.

**Second, deeper pass (same day, user: *"aur bhi bahut sari aisi jagah hogi … completely"*):**
- **No section header was an accessibility heading.** Google: *"Indicate headings to allow
  users to navigate between them."* The app has 13 header instances — "Modes", "Mode
  Settings" (×4), "Voices" (×2), "Languages", "Licenses" and the eight Advanced sections —
  built by six header factories, and only the wizard's step heading was marked. Without this
  a TalkBack user has to swipe through every control instead of jumping section to section.
  All six factories now call `ViewCompat.setAccessibilityHeading(this, true)` on the header
  `TextView` (the leaf TalkBack focuses, not the coloured bar). The white-on-header
  `setTextColor(0xffffffff)` + `CENTER_VERTICAL` pair is what identifies a header uniquely —
  a blanket replace on `CENTER_VERTICAL` alone would have wrongly caught the locale-spans
  switch.
- **Tab position was announced twice.** Material's `TabLayout` already publishes collection
  info, which TalkBack renders as "Tab 1 of 2", and we appended `", tab N of M"` on top in
  two places. The suffix is gone; the tab title alone is the label.
- **The three sliders had no name.** `SeekBar` carried only `stateDescription`, so landing on
  one announced "100 of 500, seek control" with no clue whether it was Speed, Volume or
  Pitch. Each now has `contentDescription = labelString`. (The `-`/`+` buttons already said
  "Decrease speed" / "Increase speed".)

**Verified clean in that pass, so do not re-audit blindly:** every `Spinner` has an adjacent
label `TextView`; every `setOnClickListener` sits on a `Button`; no code sets `ellipsize`,
`singleLine` or `maxLines` (only `ev_spinner_item.xml` does, which is the conventional
closed-spinner behaviour and does not hide anything from TalkBack, since the node keeps the
full text); every activity has a title, either `android:label` or a runtime `title`; the
language-list rows are uniquely labelled by language name.

**Checked and deliberately left alone:**
- the `SeekBar`'s `ACCESSIBILITY_LIVE_REGION_POLITE`. The Android 16 page names
  `setAccessibilityLiveRegion` as *the* API for a critical UI change and only warns to use it
  sparingly; a slider's value is exactly that case, and `stateDescription` (API 30+) alone
  would leave API 24-29 without the announcement. Do not remove it.
- `"Requried TTS Engines"` — the typo is **AutoTTS's own string** (`c3.u:102`), not ours, and
  is not a guideline breach. Left per rule 5; change it only if the user asks.
- text sizes are all `setTextAppearance(android.R.style.TextAppearance_*)` or
  `setTextSize(float)`, both `sp`, so they follow the user's font-size setting; no view uses
  a fixed height that could clip when the font scale grows.

## Material components: icons and button emphasis (user request, 2026-08-13)
*"material icon se hote hain vah bhi humne nahi diye hain … jahan per jaruri hote hain"*.
Sources read: `raw.githubusercontent.com/material-components/material-components-android/
master/docs/components/CommonButton.md`, and the extended-FAB description on
`developer.android.com`.

- **Button emphasis order is a real spec**, quoted: *"There are five button styles, in order
  of emphasis: 1. Elevated button 2. Filled button 3. Filled tonal button 4. Outlined button
  5. Text button."* Every `Button` in the app was filled, so a two-action screen expressed no
  hierarchy. `AppPalette.SECONDARY_BUTTON` is the tag for the **outlined** variant —
  transparent fill, 1dp `outlineBright` stroke, `primary` label — handled in
  `applyAccessibleTheme`'s `Button` branch. Contrast holds: label 10.3:1, stroke 11.0:1,
  both above their floors. Used on the wizard's **Back**, so **Next/Finish** is the only
  filled (higher-emphasis) action on that screen.
- **Icon placement is specified**, quoted: *"Icons visually communicate the button's action
  and help draw attention. They should be placed on the leading side of the button, before
  the label text."* So the wizard's icons use
  `setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)` — the **relative** form,
  which mirrors in RTL — never the left/right form.
- **The extended FAB is *defined* as icon + text** ("Extended floating action buttons are
  distinguished by an icon and a text"), and ours was text only. It now carries `ic_add`.
- Three new vector drawables with standard Material paths: `ic_add`, `ic_arrow_back`,
  `ic_arrow_forward`. **Both arrows are `android:autoMirrored="true"`** — directional icons
  must flip in RTL, which matters now that `supportsRtl` is on.
- Icon tint is set per button with `TextViewCompat.setCompoundDrawableTintList`, because the
  filled and outlined buttons have different label colours; a single static `android:tint` in
  the vector could only match one of them.
- **Icons are now on every action button that has a standard Material icon** (user,
  2026-08-13: *"jo cheez ke liye hota hai already icon bana hota hai vah"*). All path data is
  fetched from **Google's own `google/material-design-icons` repo**
  (`src/<category>/<name>/materialicons/24px.svg`) and pasted verbatim — no hand-drawn paths.
  17 buttons: Setup wizard `auto_fix_high`, TTS Settings `record_voice_over`, Disable battery
  optimization `battery_alert`, Import `file_download`, Export `file_upload`, Share logs
  `share`, Clear logs `delete`, Configuration settings `tune`, per-mode Settings `settings`,
  Test `play_arrow`, Default `restore`, dialog Install `get_app` / Apply `check` /
  Cancel `close`, the FAB `add`, and the wizard's Back/Next arrows.
- One helper does all of it: **`setLeadingIcon(button, iconRes)`** in `Theming.kt` — leading
  placement, the **relative** compound-drawable form so it mirrors in RTL, 8dp padding, and a
  tint chosen from the button's own emphasis variant (`SECONDARY_BUTTON` → `primary`,
  otherwise `onPrimary`). `fullWidthButton` gained an `iconRes` parameter defaulting to 0.
- **Deliberately left without icons:** the Languages screen's "Select all" / "Clear all" /
  "Show selected" — three `WRAP_CONTENT` buttons with two-line labels sharing one row; adding
  24dp + 8dp to each would overflow a compact (<600dp) width. The slider `-`/`+` buttons keep
  their glyph labels, which already act as the icon and carry
  "Decrease …"/"Increase …" content descriptions.
- Icons never touch the accessible name: every one of these buttons keeps its visible text as
  the label, and compound drawables are not announced.
- `GradientDrawable.setCornerRadius(...)` is called as a method, not via the `cornerRadius`
  property: `javap` shows the API-15 check jar has only the setter, and the getter that the
  Kotlin property needs arrived in API 24 — exactly our `minSdk`, so the method form removes
  the edge case entirely.

## Colour and navigation, checked against the rules (user request, 2026-08-13)

**Colour — every pair computed, and the "not by colour alone" rule walked.**
Beyond the earlier table, the components added since were measured too: tab text selected
**9.1:1** and unselected **16.5:1** on the app-bar surface, the tab indicator **9.1:1** (floor
3.0 as a UI component), the outlined button's label **10.3:1** and its stroke **11.0:1**.
Every state is carried by something other than colour as well: the selected tab has
TabLayout's indicator underline, a switch has thumb position, a radio has its dot, a disabled
button is announced as disabled by TalkBack, and "Installed"/"Not installed" is words. The
palette is blue/cyan on dark grey, so no red-green pair carries meaning anywhere.

**A regression of my own, found and fixed.** Giving the outlined button
`view.background = GradientDrawable` replaced the default background and therefore **removed
its ripple**, leaving no pressed or focused feedback — WCAG 2.4.7 wants the focus state
visible. `outlinedButtonBackground` now returns a `RippleDrawable(colorControlHighlight,
shape, mask)`, so press and focus are visible again. **Never assign a bare `Drawable` to a
button background here; wrap it.**

**Navigation.** Quoted from the navigation principles: *"Within your app's task, the Up and
Back buttons behave identically"*, and *"If a user is at the app's start destination, then
the Up button does not appear, because the Up button never exits the app."* Every sub-screen
(Languages, Configuration settings, Voice setup, Mode settings, the wizard) is reachable only
from inside the app, never by deep link, so **system Back already is Up** and a separate Up
affordance is not required — that is why the `NoActionBar` screens have none.
Verified alongside it: `MainActivity` is the launcher and the fixed start destination; the
manifest declares **no** `launchMode`, `noHistory`, `taskAffinity` or `clearTaskOnLaunch`, so
the back stack is an ordinary stack; every internal `startActivity` passes no flags and simply
pushes; the only `FLAG_ACTIVITY_NEW_TASK` uses target **external** apps (Play Store, system
TTS settings, share chooser) plus the deliberate post-import restart, which is correct there.
Tab swiping is lateral navigation, so Back not traversing it is correct.
**Predictive back**: we intercept back nowhere, and the docs say apps using default back
navigation need no code, so the manifest now simply declares
`android:enableOnBackInvokedCallback="true"` to opt into the system animations.
Source: `developer.android.com/guide/navigation/principles`,
`.../guide/navigation/custom-back/predictive-back-gesture`.

## TalkBack pass over the whole UI (user request, 2026-08-13)
Google's pre-launch report groups accessibility findings into four buckets — **touch target
size, low contrast, content labelling, implementation** (e.g. "traversal order that doesn't
match logical arrangement"). Touch targets and contrast were closed earlier; this pass was
labelling and implementation, screen by screen.

**Fixed — and the first two are the very first thing a blind user meets:**
- **The startup scan said nothing.** `currentEngineText` is rewritten as each engine is found,
  but it was not a live region, so TalkBack read "Please wait while Easy Voice scans …" once
  and then went silent for the whole scan. Android 16 names `setAccessibilityLiveRegion` as
  the API for a **critical UI change**; it is now `ACCESSIBILITY_LIVE_REGION_POLITE`, which
  queues rather than interrupts.
- **Finishing the scan announced nothing** either — `progressBox` went `GONE` and the tabs
  `VISIBLE` in silence. That is the documented "significant UI change" case, so `tabPager`
  now carries `ViewCompat.setAccessibilityPaneTitle(…, "Easy Voice settings")` and TalkBack
  announces it as the pane appears.
- **The indeterminate `ProgressBar` was a focus stop that said nothing.** It is decorative —
  the text above and below it carries the meaning — so it is now
  `IMPORTANT_FOR_ACCESSIBILITY_NO`.

**Considered and deliberately rejected, so it is not "fixed" later by mistake:**
- **`labelFor` / `contentDescription` on the spinners.** A `contentDescription` on a `Spinner`
  *replaces* the node text, so TalkBack would announce the label and **not the selected
  value** — strictly worse. `ViewCompat.setLabelFor` avoids that but makes the label be read
  twice (once as its own focus stop, once with the spinner). Every spinner already has an
  adjacent label `TextView`, which is the conventional Android pattern and reads correctly.
  Leave it alone.
- **Reordering the radio / Settings button / description triple.** Traversal is radio →
  "<Mode> settings" button → description, because the button shares the radio's row. That
  matches the *visual* arrangement, which is what the implementation check actually asks for,
  so `accessibilityTraversalAfter` would be solving a non-problem.

**Re-verified in this pass:** no label contains its own role word (a `contentDescription` of
"… settings" never says "button", so TalkBack does not say "button button"); `"Select\nall"`
and friends read as normal words; no focusable view is unlabelled; header containers are not
focusable, only the header `TextView` inside them is.

## Which control belongs where (user request, 2026-08-13)
Sources: Material Components Android `docs/components/Switch.md` and `Checkbox.md`.
- **Switch** — *"Toggle a single item on or off"*, *"Immediately activate or deactivate
  something"*, *"The effects of a switch should start immediately, without needing to save"*,
  *"best used to adjust settings and other standalone options"*.
- **Checkbox** — *"Checkboxes let users select one or more items from a list, or turn an item
  on or off."*

Audited every selection control against that:

| Control | Where | Verdict |
|---|---|---|
| 9 Advanced switches | standalone settings, effect immediate | **Switch — correct** |
| "Use locale spans" | standalone setting | **Switch — correct** |
| "Dedicated engines" | standalone setting | **Switch — correct** |
| Mode radios | pick one of five | **RadioButton in a `RadioGroup` — correct** |
| Language / voice / variant / mode-int spinners | pick one of many | **Dropdown — correct** |
| Speed / Volume / Pitch | continuous value | **SeekBar — correct** |
| "Add language" | screen's primary action | **Extended FAB — correct** |
| Language list rows | **select one or more items from a list** | **Checkbox — corrected 2026-08-13** |

**The language list is the one place the 2026-08-12 "all checkboxes → switches" sweep went too
far.** By the quotes above a list multi-select is the checkbox case, while switches are for
*standalone* options. Raised with the user, who agreed (*"han kar do checkboxes"*), so
`rowBoxes` is `ArrayList<android.widget.CheckBox>` again and the rows are
`android.widget.CheckBox`. Everything else stays a `MaterialSwitch` — those really are
standalone settings. `applyAccessibleTheme`'s `CompoundButton` branch already styles
checkboxes, and `setRowChecked`/`suppressRowEvents` are type-agnostic, so nothing else moved.

**Real defect found in the same pass:** `addLocaleSpanRow` added its switch with
`LayoutParams(MATCH_PARENT, MATCH_PARENT)` — a `MATCH_PARENT` *height* inside a vertical
`LinearLayout`, where every other switch row in the app uses `WRAP_CONTENT`. Fixed.

## Animation (user request, 2026-08-13) — the answer was "respect the setting", not "add motion"
Audited first: **the app writes no animation code at all.** No `Animator`, no
`overridePendingTransition`, no `TransitionManager`, no interpolators, and `styles.xml`
overrides no window or activity transition. So app open/close, the `ViewPager2` page scroll,
the `TabLayout` indicator, `MaterialSwitch` thumb travel and every ripple are **framework
animations**, which the platform already scales by the user's window/transition/animator
scales. Predictive back is opted in, and from Android 15 those system animations show for
apps that did so — which is us.

That leaves exactly one animation we own: the **indeterminate `ProgressBar`** on the startup
scan. Android's accessibility settings carry **Colour and motion → "Remove animations"**, for
users with motion sickness, photosensitivity or seizure triggers, and it works *"on supported
apps"*. So `animationsEnabled(context)` reads
`Settings.Global.ANIMATOR_DURATION_SCALE != 0f` (wrapped in try/catch, defaulting to true)
and the spinner is `GONE` when the user has removed animations. Nothing is lost: the
"Please wait …" text and the per-engine progress line are still there, and that line is a
polite live region, so a TalkBack user is better informed than the spinner ever made them.

**Do not "improve" this by adding decorative transitions.** Adding motion would work directly
against the setting this section is about, and it buys a blind user nothing.
`Settings.Global` is API 17, so the local kotlinc check reports
`unresolved reference: Global` — verified with `javap` that the API-15 jar has only
`Settings$System`, `Settings$Secure` and `Settings$NameValueTable`; `minSdk` is 24.

## Three TalkBack regressions the user caught (2026-08-13) — two were mine
- **Tabs stopped announcing their position.** I had removed the `", tab N of M"` suffix on the
  belief that Material's `TabLayout` supplies collection info that TalkBack renders itself. On
  device it does not — the user heard nothing at all afterwards. The position is back, but as
  `"<title>, N of M"` **without the word "tab"**, because the service appends the role: the
  original string said "tab" twice precisely because it contained the word.
  **Lesson: do not delete an announcement on the theory that the framework supplies it —
  the user's device is the authority.**
- **The wizard never announced its step.** `ViewCompat.setAccessibilityPaneTitle` only fires
  when the pane's **visibility changes** — the very note in the research that set it up — and
  `stepBody` stays visible while only its children are swapped, so no event was ever sent.
  Replaced with `setTitle(...)`, the other API the Android 16 page lists for a significant UI
  change, which does fire a window-state-changed event. `tabPager`'s pane title is **kept**,
  because that one really does go `GONE` → `VISIBLE` and therefore does fire.
  Use `setTitle(text.toString())`, not `title = …`: the property form failed the kotlinc
  check with "val cannot be reassigned" / an impossible smart cast.
- **Coming back from a sub-screen dumped focus at the top.** `MainActivity.onResume` rebuilds
  the Modes tab and `ConfigurationActivity.onResume` rebuilds its list, so the control that
  opened the screen no longer exists and TalkBack falls back to the first element. Both now
  remember what launched the screen — `pendingFocusMode` in `TabViews.kt`, `pendingFocusRow`
  in `ConfigurationActivity` — and hand accessibility focus back to the rebuilt control with
  `ViewCompat.performAccessibilityAction(view, ACTION_ACCESSIBILITY_FOCUS, null)`, posted so
  it runs after layout. The flag is cleared as it is consumed, so focus is only restored on
  the return trip and never steals focus later.

## Accessibility rule: `announceForAccessibility` is BANNED (researched 2026-08-13)
The user asked for the guidelines to be read properly rather than recalled. Google's
**Android 16 behaviour-changes page deprecates accessibility announcements** — both
`View.announceForAccessibility()` and dispatching `TYPE_ANNOUNCEMENT` events — because they
"create inconsistent user experiences for TalkBack and Android's screen reader" users. The
documented replacements, by case:
- **significant UI / window change** → `Activity.setTitle()` and
  `View.setAccessibilityPaneTitle()` (`ViewCompat.setAccessibilityPaneTitle`, which is
  backwards compatible and spoken by TalkBack from API 19 — our `minSdk` is 24);
- **critical UI change** → `setAccessibilityLiveRegion()`, and the docs say use it
  *sparingly*, since it fires on every update;
- **errors** → `CONTENT_CHANGE_TYPE_ERROR` / `setError()`.

Applied: the wizard's per-step announce became
`ViewCompat.setAccessibilityPaneTitle(stepBody, <step heading>)` — a content swap inside one
window is exactly the pane case — and the mode-settings toggle's announce was deleted
outright, because `applyExpandState` already sets `ViewCompat.setStateDescription`, which is
the documented carrier for expanded/collapsed and avoids double-speaking.
**There is now no `announceForAccessibility` anywhere in the app; do not reintroduce one.**
Sources: `developer.android.com/about/versions/16/behavior-changes-all`,
`developer.android.com/guide/topics/ui/accessibility/principles`.

## Settings live in STATICS, never re-read from prefs at runtime (audited 2026-08-13)
AutoTTS loads every setting into a static once (`c3.n.p`/`n.r` fill `AutoTtsService.G/H/O/P/
S/…`), the settings UI writes those statics directly, and the synthesis path reads **only**
the statics — `Z()` resolves its fallback with `this.Q(G)`/`this.M(G)`, `M()` and `Q()` walk
the in-memory `c3.n.c`, and `onSynthesizeText` re-reads nothing from `SharedPreferences`.
Prefs are a persistence layer, not a runtime source.

We had drifted from that in three places, all fixed:
- `onLoadLanguage` read `auto_mode_language` from prefs → now the `autoLang` static;
- the dual branch of the chunk loop read `dual_mode_language` from prefs → now `dualLang`;
- `onSynthesizeText` did `scannedLangsIso3 = prefs.getScannedLangs()` **per utterance**, and
  `scannedLangsIso3` was never read anywhere — dead. The call's only remaining effect was the
  one-time legacy-key migration, which now runs once in `loadAllSettings()`.

**Why it mattered:** prefs hold what was last *persisted*; the statics hold what the user has
just *chosen*. They only converge when `persistAll` runs (onPause / the wizard's Finish). So
choosing Hindi as the dual language inside the wizard updated the static but not the stored
key, and the service kept routing with the previously stored language — the user heard
Gujarati. Any future "the UI says X but it speaks Y" bug should be checked against this rule
first.

`loadModeLangsOnce()` is guarded by `if (autoLang.isNotEmpty()) return` and `loadAllSettings()`
runs only from `onCreate`, so the statics are never silently reloaded over a live edit. Keep it
that way.

**The two non-`EasyVoiceTtsService` flags, checked 2026-08-13 when the user asked whether they
could be statics too:**
- **Logging** already is one. `EasyVoiceLogger.loggingEnabled` is a `@Volatile` in-memory flag,
  the Advanced switch reads it (not prefs) and writes both it and the pref with `apply()`. That
  is byte-for-byte AutoTTS's `c3.p`: field `d`, seeded in the constructor from
  `getBoolean("logging_enabled", false)`, read by `g()`, written by `j()` which also `apply()`s.
  The immediate `apply()` here is correct and must NOT be moved into `persistAll` — AutoTTS
  persists this one eagerly, unlike the settings that wait for `n.v()`.
  **Gap found and fixed:** AutoTTS seeds it from a *lazily created* singleton (`p.f(context)`),
  so whichever of the UI or the service touches it first seeds it. Ours seeded only in the
  service's `onCreate`, so opening the app before the TTS service ever started showed the
  "Enable logging" switch OFF while the pref said ON. `MainActivity.onCreate` now seeds it too,
  next to the `LangStore.load*` calls.
- **`setup_done` — GONE with the setup wizard (2026-08-18).** Its only reader and writer were
  the wizard and the first-run launch in `MainActivity`, so `isSetupDone()`/`setSetupDone()`
  were removed with it. Nothing reads the key any more. (It used to be argued for as a
  persistent one-shot marker that must survive process death, which a static cannot do — that
  reasoning stands, but there is no longer anything to mark.)

## Jetpack Compose migration (user decision, 2026-08-13) — IN PROGRESS, screen by screen
The user chose to move the **whole UI** to Compose, strictly per Google's accessibility rules.
I advised against it once; they decided, so this is the plan of record.

**Why Compose is genuinely better here, from the docs (not opinion):** Material components
apply the **48dp** minimum touch target themselves (only when the component is interactive —
`onCheckedChange` non-null); `clickable`/`toggleable` **merge child semantics** so an icon +
text button is one node, which our View code does not do at all today; and `heading()`,
`paneTitle`, `liveRegion`, `customActions`, `stateDescription` are first-class modifiers.

**The one real cost, and it is environmental, not Compose's fault:** Google Maven is blocked
by the proxy, so the Compose compiler plugin and every `androidx.compose` artifact are
unresolvable locally. `android.jar` comes from Maven Central, which is why the View code can
still be type-checked; **there is no equivalent for Compose.** CI builds fine (it has network).
So every Compose mistake reaches a real build before anything catches it. That is exactly why
this migration is **incremental, one screen per build**, never a big-bang rewrite.

**Toolchain, verified against Google's compose-kotlin compatibility table, not guessed:**
Kotlin `1.9.22` → Compose Compiler **`1.5.10`**; BOM `2024.02.00`; `activity-compose:1.8.2`;
`compose.ui`, `compose.ui:ui-graphics`, `material3`. **`material-icons-extended` is
deliberately NOT added** — it is large, and we already ship the authentic Google icon paths as
vector drawables, which Compose reads with `painterResource(R.drawable.…)`.

**Order:** `ConfigurationActivity` first (smallest real screen), then the other activities,
with `MainActivity`/`TabViews` last because they are the most intertwined.

**Done so far:** `ConfigurationActivity` (verified on device), `LanguagesActivity`,
`VoiceSetupActivity` + `VoiceScreen`/`VoiceRows`, `SetupWizardActivity`, and the **Advanced
tab** (`AdvancedScreen.kt`). Remaining: the **Modes tab** in `TabViews` and `MainActivity`.

**Interop pattern for a tab:** `buildAdvancedTabView` still exists and still returns a `View`,
but that view is now a `ComposeView` hosting `AdvancedScreen`. `MainActivity`'s pager is
untouched, so a tab can be ported without touching the pager at all. Use the same shape for
the Modes tab.

**`punctuationInFlowBox` is gone with the Advanced View code.** It was a file-level `var` the
Modes tab poked to disable that one switch when the punctuation mode is "Specific language".
`AdvancedScreen` derives `enabled` from `EasyVoiceTtsService.punctuationModeInt != 3` instead,
so the behaviour survives without the cross-screen global. `applySpecificVisibility` still has
its `punctuationInFlowBox?.isEnabled` line, which is now a safe no-op — remove it when the
Modes tab is ported.

**`blocks.py` is now the ONLY safe way to edit a `write_source` block.** A generated block
ends either `"}\n")` (paren on the last string line) or `"}\n"` + a `)` line. Slicing with a
hard-coded terminator silently swallows whole files when the style does not match — that is
exactly how `LanguagesActivity.kt` disappeared, caught only because `ktimports` then reported
its class as unimported. `blocks.py` consumes the string lines and accepts either ending, and
every edit asserts `block_paths()` lost nothing.

**The wizard's settings step still hosts a View** through `AndroidView { buildModesTabView(…) }`,
because `TabViews` is not ported yet. That interop stays until it is.

**Bug the first device test caught — read this before writing any Compose surface.**
Material3's `ExtendedFloatingActionButton` fills with **`primaryContainer`**, not `primary`.
In this palette `primaryContainer` is the dark header blue, which is **1.28:1** on the
background — the FAB was effectively invisible. The View version tinted it with `primary`
(10.3:1) and laboured the label `onPrimary` (7.2:1). **Always state `containerColor` /
`contentColor` explicitly on Material3 components rather than trusting a default to land on
the colour role you meant.** The user also reported the FAB announcing no label, so it now
carries an explicit `Modifier.semantics { contentDescription = … }` alongside its `text` slot.

**Accessible checkbox row pattern** (used in `LanguagesScreen`, keep it for every future row):
`Row(Modifier.toggleable(value, role = Role.Checkbox, onValueChange = …))` with
`Checkbox(onCheckedChange = null)` inside. That merges the row into one node, so TalkBack
reads "<language>, checkbox, checked" once, and Material supplies the 48dp target.

**Checker patches for Compose, each negative-tested:**
- `ktresolve.py` skipped identifiers followed by `(`, so a **generic call** like
  `mutableStateListOf<Boolean>()` looked like a bare unresolved name. It now skips `name<T>(`.
- `ktimports.py` knows **receiver-scope composables** (`ExposedDropdownMenu`) that are called
  on a scope and never imported.
- `ktimports.py` also flags **SCOPE-ONLY imports**: `weight`, `align`, `menuAnchor` and
  friends are `RowScope`/`ColumnScope` members, so `import …layout.weight` is an unresolved
  reference that no *missing*-import check could ever see. I wrote exactly that bug in the
  wizard; the new guard catches it.

**Checker patches this needed** (both negative-tested afterwards):
- `ktimports.py` now adds **module-wide capitalised top-level `fun` names** to the known set —
  every `@Composable` is a capitalised function, so without this each one is a false positive.
- `ktresolve.py`'s parameter patterns now allow an **annotated type** (`content: @Composable
  () -> Unit`); the leading `@` made it miss the parameter and report it as unresolved.

**`build.gradle.kts` lives in a raw YAML block, not an escaped Python string.** Inserted lines
must carry the block's 14-space indent — getting that wrong breaks `yaml.safe_load` outright,
which is how it was caught here.

## A merged node carries NO name for non-TalkBack screen readers (2026-08-19)
The user tried a screen reader other than TalkBack: swiping the dropdown list announced
"button, button, button" with no item labels. TalkBack was fine. The cause is in Compose's
own `AndroidComposeViewAccessibilityDelegateCompat`:

```kotlin
if (!node.unmergedConfig.isMergingSemanticsOfDescendants || node.replacedChildren.isEmpty()) {
    info.contentDescription = node.unmergedConfig.getOrNull(ContentDescription)?.firstOrNull()
}
info.text = getInfoText(node)      // reads node.UNMERGED config
```

A `DropdownMenuItem` / `Button` / `clickable` row **merges** its children and **has** children,
so BOTH branches skip it: the focused node gets neither `text` nor `contentDescription`. The
label lives on the **fake child nodes** Compose emits. TalkBack walks those; a reader that only
inspects the focused node finds nothing and announces the bare role.

**The fix, where it matters:** leave the item with no semantic children, so the condition above
holds and the description is really written onto the node —
- `Text(..., modifier = Modifier.clearAndSetSemantics { })` on the visible label,
- `contentDescription = null` on any decorative icon,
- `Modifier.semantics { contentDescription = <the full label> }` on the item itself.

Put only the VALUE on a control whose label is a separate Text beside it, never "label, value" —
the label is its own leaf node and would otherwise be announced twice.

This applies to every merged clickable, not just menus. It is currently applied to the dropdown
trigger and the dropdown items, which is where it was reported; if that reader is also silent on
other controls, the same three steps are the remedy.

## NEVER put a lazy list inside a DropdownMenu (crash, 2026-08-18)
`DropdownMenu` sizes itself to its widest item, i.e. it asks its content for an **intrinsic
width**. A `LazyColumn` is a `SubcomposeLayout` and cannot answer that:

    IllegalStateException: Asking for intrinsic measurements of SubcomposeLayout layouts is
    not supported... such as lazy lists, BoxWithConstraints, TabRow

The exception suggests "adding a size modifier ... to fast return the queried intrinsic
measurement". **That was tried — `width(280.dp)` on the list — and the app crashed again in
the same place.** So the mitigation the log prints does not save this combination; the lazy
list simply cannot live in a menu. Do not reintroduce it, and do not trust that hint here.

The menu therefore holds ordinary `DropdownMenuItem`s. Because a `LazyColumn` would have
supplied them automatically, `collectionInfo` / `collectionItemInfo` are instead declared by
hand on a plain `Column` inside the menu (a `Column` is not a `SubcomposeLayout`, so it
answers intrinsics fine) — that is what lets TalkBack say "item 5 of 137" while swiping a
130-language list.

The app's other lazy lists are fine where they are: `ConfigurationScreen` and
`LanguagesScreen` sit inside plain `Box`/`widthIn` parents, and `TabRow` sits in `Scaffold`'s
`bottomBar`, none of which query intrinsics.

## Local validation before every push (upgraded 2026-08-12 after a CI compile failure)
Run, in order: `yaml.safe_load` → extract the generator → `ast.parse` → generate into a tree →
`ktcheck.py` / `ktresolve.py` / `ktimports.py` → `g++ -fsyntax-only` for the C++.
**Then the Kotlin type-check, which is the step that was toothless and must not be skipped:**
- kotlinc **with a real `android.jar` on the classpath**, and the errors **diffed against the
  same run on the last commit that built green**. Script: `scratchpad/ktcompile.sh <good-ref>`.
- Without `android.jar` every `android.*` type is unresolved, so kotlinc silently type-checks
  **nothing** at call sites. That is exactly how `MainActivity`'s
  `buildModesTabView(container.context, prefs) { testTts }` reached CI: adding a trailing
  `settingsOnlyForMode: String?` parameter made Kotlin bind that **trailing lambda** to the new
  last parameter instead of `testTtsProvider`. Negative-tested both ways — invisible without the
  jar, caught immediately with it.
- `android.jar` comes from **Maven Central** (`com/google/android/android/4.1.1.4`); it is API 15.
  **Google Maven is blocked by the proxy**, so androidx/material are unavailable. The resulting
  noise is constant and cancels out in the baseline diff. Two known-noise signatures, both
  already present for known-good files, so do not chase them:
  - a `ComponentActivity` subclass → `unresolved reference: androidx/ComponentActivity`, then
    `finish`/`resources`/`packageManager`/`setContentView`/`onCreate`/`onPause` unresolved and
    `type mismatch: inferred type is <Activity> but Context was expected`;
  - `setTextAppearance(int)` is API 23 → `no value passed for parameter 'p1'` +
    `type mismatch: inferred type is Int but Context! was expected`;
  - `View.generateViewId()` is API 17 → `unresolved reference: generateViewId` (verified
    absent from the jar with `javap`);
  - `clipToPadding = false` needs the **getter** `getClipToPadding()`, which is API 21 —
    `javap` shows the API-15 jar has only `setClipToPadding(boolean)`, so Kotlin cannot form
    the property → `unresolved reference: clipToPadding`. Fine at `compileSdk 34`/`minSdk 24`;
  - anything from **`com.google.android.material`** (`MaterialSwitch`,
    `ExtendedFloatingActionButton`) → `unresolved reference: google` plus a cascade on its
    members (`text`, `variable expected`), because Google Maven is blocked. The baseline
    already carries nine of these for `MainActivity`/`Theming`;
  - a SAM lambda passed to an **androidx** method (e.g. `ViewCompat.addAccessibilityAction`)
    → `cannot infer a type for this parameter`, because the functional interface itself is
    unresolved. The baseline already carries this for `EasyVoiceTtsService`,
    `LanguagesVoicesViews`, `MainActivity` and `TabViews`.
- Judge the run by the **NEW error texts** the diff prints, not by the total count.

## Number / punctuation / emoji "Specific language" — VERIFIED EXACT (2026-08-18)
Checked against CFR, not assumed, after the user asked whether the specific option matches.
Do NOT re-derive this; it is done.

**Where AutoTTS decides.** `onSynthesizeText` dispatches on the reading mode `AutoTtsService.S`:
line 2032 guards `S != 1` (dual), 2178 guards `S != 4` (mix), 2392 guards `S != 5`
(multilingual). Anything between those lines belongs to that mode.

**Mix branch, lines 2207-2262.** For each segment the type is tested with `d0.n` (number),
`d0.o` (punctuation), `d0.l` (emoji), then the matching mode int decides the language:
```java
var7_25 = AutoTtsService.I;              // K for punctuation, M for emoji
if (mode != 0 && mode != 1) {
    if (mode != 2) {
        if (mode == 3) Q.add(new e0(text, AutoTtsService.J));   // L / N
        break;                                                   // >3: nothing is added
    } else Q.add(new e0(text, AutoTtsService.P));
    break;
}
Q.add(new e0(text, AutoTtsService.O));
```
So **0 or 1 -> O (mix latin), 2 -> P (mix non-latin), 3 -> the specific language, anything
else -> the segment is dropped.** Ours is `languageForSegmentKind`, which returns
latinFallback / nonLatinFallback / specific / null, and the caller adds the chunk only when
non-null — the same four outcomes.

**Dual branch, lines 2060-2157.** Types dispatch to: 1 -> `"eng"`, 2 -> `H` (dual language),
3 -> `J`, 4 -> `L`, 5 -> `N`. Ours matches exactly.

**A false alarm worth recording so it is not raised again.** Line 2356 maps the FIRST segment
of `AutoTtsService.Q` to a preflight language with `1 -> O, 2 -> P, 3 -> J, 4 -> L, 5 -> N,
else -> the already-resolved language`. That looks like it contradicts our
`1 -> "eng", 2 -> dualLang`, but 2356 sits between 2178 and 2392, so it is the **mix** branch,
while ours is inside `if (modeInt == 1)` — the **dual** branch. Different branches, no bug.

**CFR is genuinely broken in this region** (`lbl432:`, `// 3 sources`, `break block140..172`),
so the structure was recovered from the mode-dispatch line numbers and the `break blockNNN`
targets rather than from indentation. Smali was not needed.

**Advanced tab defaults, all verified against `c3/n.java` q() and the persist:** strip_audio_attr
false, force_accessibility_stream false, keep_alive_mode false, show_notification false,
disable_advanced_detection **true**, quick_character_reading false, punctuation_with_sentence
**true**, smart_number_reading false; and `number/punc/emoji_specific_language` each fall back to
`n.e(Locale.getDefault())` when empty. Ours matches all of it.

## CLD3 (user decision, 2026-07-29 — DONE 2026-08-06)
The Advanced-tab row **"Use CLD3 (neural language detection)"** is an EasyVoice-only
feature and **must NOT be removed**. Both steps the user asked for are finished:
1. CLD2 path taken to exact AutoTTS parity (coverage audit, `ANALYSIS` §42-§47).
2. CLD3 brought level with CLD2 (`ANALYSIS` §37 and §48) — both detector call sites have
   symmetric arms, and the switch now lives on the same static/persist/load path as the
   other five Advanced flags.
The rule for this row is not "match AutoTTS" (AutoTTS has no such switch) but **"wherever
CLD2 makes a detection, the switch must be able to put CLD3 there instead"**. Keep that
invariant on any future change to the detection path.

## How to Trigger Build
```
mcp__github__actions_run_trigger → run_workflow
owner: bariyasachin96, repo: omni
workflow_id: 262884892, ref: claude/yaml-file-nk3czh
```

## Full Diff List (117 differences — fresh deep analysis)
- `.claude/autotts_vs_easyvoice_full_diff.txt` — complete numbered list (DIFF-1 to DIFF-117)
  - HIGH: 21 items, MEDIUM: 41 items, LOW: 53 items
  - Generated by 10-parallel-agent workflow on 2026-06-24

## AutoTTS Reference Files (updated to **5.7.7.18**, 2026-08-07)
**DURABLE (committed, never delete)**: `autotts_reference/` in repo root —
`decompiled_java/` (full CFR decompile, 1656 .java), `decompiled_res/` (full apktool:
strings/colors/styles/dimens/…, all layouts, AndroidManifest.xml), `AutoTTS_5.7.7.18.apk`
(ground truth, versionCode 90000245), `README.md` (exact dex2jar+CFR+apktool regen commands).
The old 5.7.7.10 reference was fully removed. This survives container/scratchpad resets.

### ⚠️ 5.7.7.18 obfuscated-name map (names CHANGED from 5.7.7.10)
Almost every `c3.*` class shifted by **one letter**. The *pref keys* are unchanged, and
**five new ones** were added (`number_specific_language`, `punc_specific_language`,
`emoji_specific_language`, `punctuation_with_sentence`, `smart_number_reading`).
`com/vnspeak/autotts/a.java` is byte-identical to 5.7.7.10.

| Concept | 5.7.7.10 | **5.7.7.18** |
|---|---|---|
| Settings store class | `c3.m` | **`c3.n`** |
| Settings fragment (all tabs) | `c3.j` | **`c3.k`** |
| Number/punct/emoji segmenter | `c3.y` (`y.g`, 227 ln) | **`c3.d0` (`d0.t`, 649 ln)** |
| One segment (text + type) | `c3.z` | **`c3.e0`** |
| Required-engines dialog / engine-name map | `c3.t` / `c3.u` | **`c3.u` / `c3.v`** |
| Language check (in store list AND not-disabled) | `m.o(lang)` | **`n.n(lang)`** |
| Enabled ISO set / scan list / iso3 helper | `m.f` / `m.c` / `m.f(Locale)` | **`n.f` / `n.c` / `n.e(Locale)`** |
| Detection main / first-cp | `clsCLD2.b` / `clsCLD2.a` | same |
| codepoint→script / script family / fallback map | `a.b` / `a.e(cp, m.f)` / `a.w` | `a.b` / **`a.e(cp, n.f)`** / `a.w` |
| **CLD2 language hints (NEW)** | (n/a) | **`clsCLD2.f(Set)` → `nativeSetLanguageHints(String[])`** |
| disable-advanced-detection flag | `AutoTtsService.W` | **`AutoTtsService.a0`** |
| single-char UNKNOWN gate (quick char read) | `AutoTtsService.X` | **`AutoTtsService.b0`** |
| strip-audio-attr / force-accessibility / keep-alive / notification | `S`-era letters | **`W` / `X` / `Y` / `Z`** |
| **punctuation-in-flow (NEW, default true)** | (n/a) | **`AutoTtsService.c0`** |
| **smart number reading (NEW, default false)** | (n/a) | **`AutoTtsService.d0`** |
| number / punct / emoji mode ints | `H` / `I` / `g0` | **`I` / `K` / `M`** |
| **number / punct / emoji specific language (NEW)** | (n/a) | **`J` / `L` / `N`** |
| dual / mixed-latin / mixed-non-latin language | `G` / `K` / `L` | **`H` / `O` / `P`** |
| reading mode int | `AutoTtsService.O` | **`AutoTtsService.S`** |
| licence/signature gate (**do NOT port** — carve-out) | `y.g` args 4-5 | **`AutoTtsService.h0` / `m0`, `c3.l0`** |

### Java Source (decompiled_java/)
- `com/vnspeak/autotts/AutoTtsService.java` — main TTS service (synthesis logic, static flags)
- `com/vnspeak/autotts/clsCLD2.java` — CLD2 detection (`b()` detect, `a()` first-cp, `e()` unknown-char, `c()` mix-chunk list)
- `com/vnspeak/autotts/a.java` — script/lang mapping (`a.b(int)`, `a.e(cp,set)`, `a.w` map)
- `com/vnspeak/autotts/NewSettingsActivity.java` — settings UI (tabs/spinners/sliders)
- `c3/m.java` — settings store + engine check (`m.o()`, `m.f` Set, `m.p`/`m.r` loaders)

### Resources (autotts_res/)
- `autotts_res/res/xml/tts_engine.xml` — TTS engine declaration (settingsActivity)
- `autotts_res/res/layout/` — UI layouts
- `autotts_res/res/xml/` — XML configs
- **Note**: `values/strings.xml` ab poori decode ho gayi hai — `autotts_reference/decompiled_res/res/values/strings.xml` (app_name, auto_mode_*, autotts_* sab)

## Completed Fixes (all in branch, build verified)
| Commit | Fix |
|--------|-----|
| `d55a46b` | MISSING-C: type-based disabled-engine fallback |
| `c6fc8e8` | `isUnknownChar()` exact AutoTTS clsCLD2.c() match |
| `d195364` | Fix #1/#2/#4: emoji latRange, non-Latin type, CLD2 windowing |
| `0b91941` | Fix #3: smartLangFallback → type-based in Mix mode |
| `431b694` | Remove stripMarkdownHtml, MISSING-B, per-char emoji routing |
| `80ac1ce` | Emoji routing: segment-level emojiFall |
| `32a8486` | Phase 4 whitespace detection — full Java isWhitespace set |
| `f80216c` | getScriptLang() SCRIPT_BLOCKS mirror |
| `83b7a80` | getScriptLang() exact Blocks.txt match |
| `d6cb130` | getScriptLang() exact port of AutoTTS `a.b(int)` |
| `10794af` | Script fallback: first codepoint only + script family fallbacks (a.w map) |

## Pending / Known Remaining Differences
1. **~~emojiFall~~ — RESOLVED (verified 2026-07-17)**: emoji handling ab AutoTTS ke
   barabar hai. AutoTTS pattern `d` (c3/w.java:25-32) khud `c3.c.a()` (emoji regex)
   append karta hai → emoji `latRange` pattern `d` mein MATCH hote hain → Latin segment
   → `w.c()` type **1** (Latin) deta hai (v.a/w.e/w.d nahi matchte). Hamara C++
   (buildMixChunks: emoji Latin buffer mein, type 1; `emojiFall` dead-code `(void)`)
   isse exact match karta hai. Purana note (emoji type-2) galat tha.

2. **Mix mode flow — VERIFIED A-to-Z (2026-07-17)**: N gating (x.g khud N check),
   N=true LocaleSpan split → H()/w.g, N=false whole-text w.g, per-chunk clsCLD2.b +
   type fallback, emoji type-1 — sab AutoTTS ke barabar. Koi divergence nahi.

## Key Architecture
- **Naming (2026-07-12)**: service ke obfuscated AutoTTS mirror-names descriptive kiye gaye — modeInt(L), dualLang(D), localeSpansFlag(N), stripAudioAttrFlag(P), forceAccessibilityFlag(Q), numberModeInt(E), puncModeInt(F), emojiModeInt(G), dedicatedEnginesFlag(O), voiceList(T), engineList(M), chunkQueue(J), chunkCounter(K), utteranceIdStr(X), initializingTts(U), enginePool(f), engineIndex(d_), lastEnginePkg(c), reqParams/reqVolume/reqRate/reqPitch(i/j/k/l), loadVoice(V), loadVoiceOriginal(W), loadVoiceDedicated(X_eng), findEngineForLocale(c0), isLangRoutable(ko), isLangRoutableRaw(koRaw), localeIso3(kfLocale), isKnownIso2(khMappable). Har declaration par AutoTTS mapping comment hai.
- 3 modes: "auto", "mix", "dual"
- CLD2 native library for language detection
- `getScriptLang(cp)` — port of AutoTTS `a.b(int)` — codepoint → language
- `getScriptLangFallbacks(primary)` — port of AutoTTS `a.w` map — fallback list
- `isLangRoutable(lang)`/`isEngineAvailable(lang)` — mirrors AutoTTS `k.o(lang)`

## Key AutoTTS Concepts (5.7.7.10 names; 5.7.7.1 in parens)
- `clsCLD2.b()` — main detection, calls `a.e(cp, m.f)` (was `k.f`) for script fallback
- `a.e(cp, m.f)` — returns script family object (filtered by user's enabled langs)
- `a_result.b()` — primary lang of script family; `a_result.a()` — fallback lang set
- `m.f` (was `k.f`) — Set of user-enabled ISO lang codes
- `m.o(lang)` (was `k.o(lang)`) — **iso2/iso3 is present in `m.c` AND `!e.i`. It does NOT look
  at the engine at all.** `m.c` comes from `language_N`, i.e. every scanned language. The
  engine test is a different method, `AutoTtsService.M(lang)`, and the two are used in
  different places — see `ANALYSIS_5.7.7.10_mix.md` §30.
- `AutoTtsService.W` (was `S`) — disable-advanced-detection flag; `AutoTtsService.X` — single-char UNKNOWN gate
