# EasyVoice — Claude Session Context

## Project Overview
- **App name**: Easy Voice — Android TTS screen reader for 100% blind users
- **Goal**: Exactly match AutoTTS (com.vnspeak.autotts) behavior in all modes
- **All source code** is embedded as Python string literals in `.github/workflows/build.yml`
- **Working branch**: `claude/yaml-file-nk3czh`
- **Build**: Manual `workflow_dispatch` trigger on GitHub Actions — must trigger manually after each push

## HARD RULES (NEVER violate)
1. **NEVER build/push without explicit user request**
2. **Fix #16 (Disable engine handling) — IMPLEMENTED 2026-07-10** (user override: "exactly AutoTTS"). `isLangRoutable(lang)` helper (formerly `ko`) = `k.o()` parity (configured AND not-disabled), applied at detection-acceptance (clsCLD2.b:60), script-fallback primary+fallbacks (a.e+k.o), and auto/mix span resolution (H()=="Disable"→C). Dual mode untouched (type-based, no k.o). Previously set-aside; no longer.
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

## CLD3 (user decision, 2026-07-29)
The Advanced-tab row **"Use CLD3 (neural language detection)"** is an EasyVoice-only
feature and **must NOT be removed**. The user wants CLD3 brought up to the same level of
implementation as CLD2 — but **later**. Order of work:
1. **NOW**: finish everything on the CLD2 path to exact AutoTTS parity.
2. **THEN**: improve CLD3 the same way CLD2 is done.
Do not start CLD3 work until the CLD2 side is declared complete.

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
| Engine check (configured AND not-disabled) | `k.o(lang)` | **`m.o(lang)`** |
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
- `m.o(lang)` (was `k.o(lang)`) — checks if engine available and not disabled
- `AutoTtsService.W` (was `S`) — disable-advanced-detection flag; `AutoTtsService.X` — single-char UNKNOWN gate
