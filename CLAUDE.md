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
   - the **selected** mode's `"<Mode> settings"` button sits directly after that mode's own
     description; every other mode's button is `GONE`, and "None" has none;
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

## AutoTTS Reference Files (updated to **5.7.7.10**, 2026-07-23)
**DURABLE (committed, never delete)**: `autotts_reference/` in repo root —
`decompiled_java/` (full CFR decompile, 1651 .java), `decompiled_res/` (full apktool:
strings/colors/styles/dimens/…, all layouts, AndroidManifest.xml), `AutoTTS_5.7.7.10.apk`
(ground truth, versionCode 90000237), `README.md` (exact dex2jar+CFR+apktool regen commands).
The old 5.7.7.1 reference was fully removed. This survives container/scratchpad resets.

### ⚠️ 5.7.7.10 obfuscated-name map (names CHANGED from 5.7.7.1)
The *pref keys* are unchanged (`strip_audio_attr`, `force_accessibility_stream`,
`show_notification`, `disable_advanced_detection`, `locale_spans`, `dedicated_engines`),
so our SharedPrefsManager still matches. Only the obfuscated class/method names shifted:

| Concept | 5.7.7.1 | **5.7.7.10** |
|---|---|---|
| Settings store class | `c3.k` | **`c3.m`** |
| Language check (in m.c AND not-disabled — **no engine test**) | `k.o(lang)` | **`m.o(lang)`** |
| Enabled ISO set / scan list / iso3 helper | `k.f` / `k.c` / `k.f(Locale)` | **`m.f` / `m.c` / `m.f(Locale)`** |
| lang-list builder / pref loaders | `k.h` / `k.p` / `k.r` | **`m.h` / `m.p` / `m.r`** |
| Detection main / first-cp | `clsCLD2.b` / `clsCLD2.a` | same (`clsCLD2.b` / `clsCLD2.a`) |
| unknown-char check | `clsCLD2.c(char)` | **`clsCLD2.e(char)`** |
| codepoint→script / script family / fallback map | `a.b` / `a.e(cp,set)` / `a.w` | same (`a.b` / `a.e(cp, m.f)` / `a.w`) |
| disable-advanced-detection flag | `AutoTtsService.S` | **`AutoTtsService.W`** |
| single-char UNKNOWN gate | (n/a) | **`AutoTtsService.X`** |

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
