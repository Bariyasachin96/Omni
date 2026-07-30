# AutoTTS 5.7.7.10 — Mix/Modes/Advanced analysis (2026-07-23)

Deep read of the freshly-decompiled 5.7.7.10 reference. Implementation deferred —
this is the reference doc for what changed vs 5.7.7.1 and what EasyVoice must match.

## 1. Complete field / obfuscated-name map (5.7.7.1 → 5.7.7.10)

| Concept | pref key | 5.7.7.1 | **5.7.7.10** |
|---|---|---|---|
| Settings store class | — | `c3.k` | **`c3.m`** |
| Radio/mode handler class | — | `c3.i` | **`c3.j`** |
| latRange chunk builder | — | `c3.w` (`w.g`) | **`c3.y` (`y.g`)** |
| LocaleSpan splitter | — | `c3.x` (`x.g`) | **`c3.z` (`z.g`)** |
| engine check | — | `k.o` | **`m.o`** |
| enabled set / scan list | — | `k.f` / `k.c` | **`m.f` / `m.c`** |
| unknown-char | — | `clsCLD2.c` | **`clsCLD2.e`** |
| **mix-chunk list (nativeGetLanguages)** | — | (n/a) | **`clsCLD2.c`** |
| mode int | `auto_mode` | `L` | **`O`** |
| locale_spans | `locale_spans` | `N` | **`Q`** |
| auto pref lang | `auto_mode_language` | `C` | **`F`** |
| mix Latin lang | `mixed_mode_latin_language` | `H` | **`K`** |
| mix non-Latin lang | `mixed_mode_non_latin_language` | `I` | **`L`** |
| dual secondary lang | `dual_mode_language` | `D` | **`G`** |
| number mode (int) | `number_mode_language` | `E` | **`H`** |
| punctuation mode (int) | `punc_mode_language` | `F` | **`I`** |
| emoji mode (int) | `emoji_mode_language` | `G` | **`J`** |
| strip audio attr | `strip_audio_attr` | `P` | **`S`** |
| force accessibility | `force_accessibility_stream` | `Q` | **`T`** |
| **keep-alive (NEW)** | `keep_alive_mode` | — | **`U`** |
| show notification | `show_notification` | `R` | **`V`** |
| disable adv detection | `disable_advanced_detection` | `S` (def **false**) | **`W` (def `true`)** |
| **quick char read (NEW)** | `quick_character_reading` | — | **`X` (def `true`)** |

Pref KEYS are unchanged → SharedPrefsManager still maps. Only defaults + 2 new keys.

## 2. Mode int values (`c3.j`, radio → `AutoTtsService.O`)
`0=None, 1=Dual, 2=Auto, 3=Google, 4=Mixed, 5=Multilingual`.
Loaded in `m.p`: `O = getInt("auto_mode", 3)`; if `O==3` (Google) and Google not
installed → `O=0`.

## 3. Modes tab UI changes (fragment_modes.xml)
- **NEW 6th radio: "Multilingual mode (experimental)"** (`multilingual_mode`).
- **Mixed section now shows number + punctuation spinners** (`number_mode_language`,
  `punc_mode_language`) in addition to Latin/non-Latin lang.
- `support_localespans` checkbox now in **Auto + Mixed + Multilingual** sections.
- All 6 mode descriptions rewritten (longer, with examples).

## 4. Mixed mode flow (O == 4) — onSynthesizeText
```
M.clear()
spans = c3.z.g(text)                       // LocaleSpan split (whole text)
for each span:
    lang = span.b()                        // span's declared locale lang
    if lang not unknown/empty AND m.o(engineFor(lang)) usable (not "Disable"):
        span.e(lang); M.add(span)          // keep span as-is
    else:
        M.addAll( c3.y.g(span.c(), H, I, g0, b0) )   // latRange split
// c3.y.g = latRange+number+punc builder: (text, numberInt H, puncInt I, cld g0, b0)
// then per chunk: clsCLD2.b detect (+ single-char X gate); if lang not in m.h map →
//   type fallback: type1→K (mix Latin lang), type2→L (mix non-Latin lang)
```
So **number/punctuation are handled inside `c3.y.g`** now (H=number selector,
I=punc selector) — same builder dual mode uses.

## 5. Multilingual mode flow (O == 5) — NEW
```
spans = c3.z.g(text)                        // LocaleSpan split
for each span:
    chunks = clsCLD2.c(span.c(), g0, b0, ctx)  // native nativeGetLanguages multi-lang
    // clsCLD2.c: if X && len==1 → single "un" chunk; else CLD2 returns (lang,type,text) triples
    for each chunk:
        lang = m.h.get(chunk.lang)          // map detected lang → routable
        if null → type-based: chunk.type ? K (Latin) : L (non-Latin)
        M.add( new z(chunk.text, lang) )
```
Needs native `nativeGetLanguages` (CLD2 multi-language chunking).
**IMPLEMENTED (commit 011c6ca):** `NativeEngine.nativeGetLanguages` uses CLD2
`ExtDetectLanguageSummary` + `ResultChunkVector`; latin flag via
`Character.UnicodeScript` LATIN/COMMON/INHERITED (exact `clsCLD2.d`); shares
mixed K/L prefs; z.g LocaleSpan split → keep-routable-span or per-span chunk.

## 6. Quick character reading (X, `quick_character_reading`, default true)
- `clsCLD2.b`: `if (text.length()==1 && X) return "UNKNOWN"`.
- `clsCLD2.c`: `if (X && text.length()==1) → single "un" chunk`.
- String: "Reads a single character based on the preferred language in the mode settings."

## 7. disable_advanced_detection (W, default **true** now)
- `clsCLD2.b`: `if (W) return detectedLang` — skips script-family fallback.
- Default flipped false→true, so advanced detection is OFF unless the user turns it on.

## 8. Keep-alive (U, `keep_alive_mode`, default false) — NEW
- In the synth loop: `if (!U) { o.wait() }` — when U is true the post-utterance wait
  is skipped, keeping the engine active so the system is less likely to tear down the
  session mid-speech. Advanced tab: "Keep-alive Mode" section + "Keep alive" checkbox.

## 9. Advanced tab UI changes (fragment_advanced.xml)
- `synthesis_settings` → **"Advanced Synthesis Options"** (+ new description).
- **NEW section "Keep-alive Mode"** + checkbox "Keep alive".
- **NEW checkbox "Quick character read"** under Language Detection Options.
- Reworded: remove_audio_attributes(+desc), disable_advanced desc, import desc, logger desc.

## 10. What EasyVoice must change (for 5.7.7.10 parity)
1. **Modes tab**: add 6th "Multilingual mode (experimental)"; add number+punc spinners
   to Mixed section; locale-spans checkbox in Auto/Mixed/Multilingual; update all
   descriptions.
2. **Advanced tab**: add "Keep-alive Mode" section + "Keep alive" checkbox
   (`keep_alive_mode`); add "Quick character read" checkbox (`quick_character_reading`,
   default true); rename "Synthesis Options" → "Advanced Synthesis Options"; update
   descriptions.
3. **Prefs**: `disable_advanced_detection` default false→**true**; add
   `keep_alive_mode` (default false) + `quick_character_reading` (default true).
4. **Detection**: single-char UNKNOWN gate when quick_character_reading on (in the
   window detector and the mix chunk builder).
5. **Mix mode**: number/punc language applied inside the latRange chunk builder.
6. **Multilingual mode (native)**: ✅ DONE (commit 011c6ca) — `nativeGetLanguages`
   multi-language chunking + type fallback + full 6th-mode UI/wiring.
7. **Keep-alive**: skip the post-utterance wait when `keep_alive_mode` on.

## 11. Mix mode A-to-Z verification (2026-07-28)

Full line-by-line trace of Mixed mode (O==4) vs EasyVoice. Verified MATCH:

| Step | AutoTTS (5.7.7.10) | EasyVoice |
|---|---|---|
| Defaults K/L/G/F | m.q(): "" → m.f(Locale.getDefault()) | same (ifEmpty → device locale) |
| H/I/J | getInt(...,0) | same |
| Span split | c3.z.g (Q-gated); Q off → 1 span "UNKNOWN" | splitByLocaleSpans / whole-text path |
| Span loop guard | `!q.get()` (stop) | `if (isStopped) break` (equivalent) |
| Span lang unknown/"" | → c3.y.g(text,H,I,g0,b0) | → processDirect("mix") |
| Span lang known + engine usable | keep span lang | isLangRoutableRaw → keep |
| Span lang known + engine ""/Disable | lang = F (auto_mode_language) | → autoModeC |
| First chunk only detect | M.get(0) → clsCLD2.b | resolveMixChunk(chunks[0]) |
| det → lang | substring(0,2) → m.h.get (iso2→iso3); null → type K/L | isLangRoutable(det) ? det : mixTypeFallback (equivalent outcome) |
| engine ""/Disable | type fallback 1→K, 2→L, else keep | same via mixTypeFallback |
| onLoadLanguage abort | -1/-2 → K(cb,7) | LANG_NOT_SUPPORTED/MISSING_DATA → done+return |
| onIsLanguageAvailable | m.j(null,true).contains ? 0 : -2 (non-disabled scan list) | getLanguageList().contains && !isLanguageDisabled |
| M(lang) | "" / "Disable" unusable | "NOT_SET" / "Disable" |
| Bypass path | `[AutoTTS:` prefix → loadVoice | `[EasyVoice:` prefix → loadVoice |
| p / q | p=end/unlock/stop, q=stop | isStopped / isFlushed |

Divergences FOUND & FIXED this pass:
1. `zxx` → auto for ALL non-Dual modes (AutoTtsService:1863) — EasyVoice only did it for mix. (46c397f)
2. Speak dispatch: `if (!p && !q)` wraps listenerSet + setAudioAttributes + postDelayed (line 2268); EasyVoice posted unconditionally. Also param order strip→volume. (e16e119)
3. e.onDone posts next chunk with `postDelayed(...,50L)` (line 2605); EasyVoice used post() with no delay. (e16e119)

## 12. Full class-chain sweep (2026-07-28)

Complete call-chain from AutoTtsService and clsCLD2 outward.

Chain: AutoTtsService → {c3.a, c3.b, c3.d, c3.e, c3.f0, c3.g0, c3.m,
c3.o, c3.v, c3.y, c3.z, clsCLD2, c0.k};  clsCLD2 → {c3.m, autotts.a}.

Verified MATCH in this sweep (no change needed):
| AutoTTS | What | EasyVoice |
|---|---|---|
| `c3.b` → `a(int)` | audio-focus change handler — **log only**, no behaviour on loss/gain | focus listener logs only ✓ |
| `N` / `O` / `R` | per-language pitch / speed / volume from `m.c` (default 100) | `<lang>_pitch/_speed/_volume` prefs, default 100 ✓ |
| `P` / `Q` | getVariant4Language / getVoice4Language (O==3 → return arg) | prefs voice/variant lookup ✓ |
| `V(a,b)` | locale compare: iso3lang, then country (empty→true), then variant (empty→true) | `localeMatchesP` — exact ✓ |
| `k0` | findEngineForLocale: 3 passes (lang+country+variant, lang+country, lang), early `return ""` if a voice entry won't parse | `findEngineForLocale` — exact, incl. early bail ✓ |
| `onLoadVoice` | store name, parse locale, onLoadLanguage(iso3, country, variant), 0/1/2 → SUCCESS | same ✓ |
| `onStartCommand` | returns 1 (START_STICKY) | START_STICKY ✓ |
| `J` / `I` / `S` / `W` / `l0` | channel "tts_channel" / "TTS Engine" / IMPORTANCE_LOW(2); notification id **136549**, ongoing; POST_NOTIFICATIONS gate; **SDK 34+ → startForeground with FGS type 2 = MEDIA_PLAYBACK** | identical, incl. FGS type + manifest mediaPlayback ✓ |
| `T` / `X` / `Y` / `a0` / `e0` | engine init + bind, one-time F/K/L/G load, engine list, scan list `m.c`+`m.f`, voice list `Y` | initAllTTS / eagerLoadPrefs / buildEngineList / language list / buildVoiceList ✓ |
| `H` / `U` / `j0` / `c3.g0` | LicenseChecker (LVL) | intentionally absent — not a synthesis behaviour |

No new divergences found in this sweep. The real gaps were the ones
already fixed: detection API (BestEffort/V2), multilingual chunking
(ScriptScanner), 2-char truncation, speak-dispatch guard, onDone 50ms,
zxx routing, spinner sync, and the self-added chunk filters.

Note: `T`/`X`/`Y`/`a0`/`e0` were verified by purpose and interface, not
line-by-line through their (long) loop bodies.

## 13. Mix mode — byte-by-byte verification from DEX (2026-07-28)

Re-verified the whole Mixed-mode chain against **baksmali output** rather
than CFR, because CFR coalesces registers and had produced one
misleading line. Read unfiltered, instruction by instruction.

onSynthesizeText, O == 4 (AutoTtsService.smali, "Mixed mode" at 28327):
```
M.clear()
spans = c3.z.g(text)
for (i = 0; i < spans.size(); i++) {
    if (q.get()) break                       // stop flag
    lang = spans[i].b()
    if (lang.equalsIgnoreCase("unknown") || lang.equals("")) {
        M.addAll(c3.y.g(spans[i].c(), H, I, g0, b0))   // H=number, I=punc
    } else {
        engine = M(lang)
        if (engine.isEmpty() || engine.equals("Disable")) lang = F
        spans[i].e(lang); M.add(spans[i])
    }
}
if (M.isEmpty()) skip
text0 = M.get(0).c();  lang0 = M.get(0).b()
if (lang0.isEmpty() || lang0.equals("unknown"))
    lang0 = clsCLD2.b(text0, g0, b0, ctx)     // result lands in the SAME register
if (lang0.length() > 2) lang0 = lang0.substring(0, 2)
lang = m.h.get(lang0)
if (lang == null) { t = M.get(0).a(); lang = t==1 ? K : t==2 ? L : lang }
engine = M(lang)
if (engine.isEmpty() || engine.equals("Disable")) {
    t = M.get(0).a(); lang = t==1 ? K : t==2 ? L : lang
}
res = onLoadLanguage(lang, "", "");  if (res == -1 || res == -2) K(cb, 7)
```

Resolved ambiguity: CFR rendered the skip-detect path as
`var1_1 = var10_15` (the request language). The DEX shows
`clsCLD2.b(...)`'s result being written into the very register that held
the chunk's language, i.e. the code simply keeps using the chunk's own
language when detection is skipped. EasyVoice already does exactly that
(`onLoadLanguage(prefs.toIso3(chunks[0].lang))`), so NOT "fixing" this
from the CFR artifact was correct.

Everything else confirmed equal to EasyVoice:
- y.g argument order (text, H, I, g0, b0) — number before punctuation,
  matching the jPunc/jNum positions checked in processDirect.
- `m.h.get` membership == isKnownIso2 (both are Locale.getISOLanguages).
- AutoTTS's two separate rejections (m.h null, then engine ""/"Disable")
  collapse to EasyVoice's single
  `isKnownIso2(det) && isLangRoutable(det) ? det : mixTypeFallback`,
  with mixTypeFallback already carrying type1→K / type2→L.
- span-loop guard `q.get()` ≡ EasyVoice's isStopped during chunk build
  (both are only set by onStop at that point).

No divergence left in the Mixed-mode chain.

## 14. Chunk-by-chunk reading — all modes, both decompilers (2026-07-28)

AutoTtsService$e.onDone advances chunks. CFR cannot decompile its inner
Runnable, so it was read from DEX (baksmali) and cross-checked against
the CFR-decompiled first-chunk path.

onDone (e.onDone): `if (M.size() > 1) b.postDelayed(runnable, 50L)`
else `log "No more text to read."; L(cb, 7)`.

The runnable (AutoTtsService$e$a.run) — exactly three mode branches:

| Mode | onDone behaviour | lang used for rate/pitch/volume | EasyVoice |
|---|---|---|---|
| Dual (O==1) | chunk type 1 → onLoadLanguage("eng"); type 2 → onLoadLanguage(G); any other type → no load | "eng" / G | typeCode 1 → "eng", 2 → dual_mode_language ✓ |
| Mixed (O==4) | lang empty/"unknown" → clsCLD2.b → substring(0,2) → m.h.get → null ? type1→K/type2→L → M(lang) ""/"Disable" ? type1→K/type2→L → onLoadLanguage | the routed lang | resolveMixChunk(next) then onLoadLanguage(next.lang) ✓ |
| None / Auto / Google / Multilingual (cond_194) | onLoadLanguage(M.get(0).b()) | chunk.lang | onLoadLanguage(next.lang) ✓ |

Cross-check: the Mixed branch inside the onDone runnable emits the same
call sequence as the CFR-decompiled first-chunk routing —
z.b() → clsCLD2.b() → length()/substring(0,2) → Map.get → z.a()→L/K →
"Disable" → z.a()→L/K → onLoadLanguage. Two independent tools, same
logic, and EasyVoice runs one shared resolveMixChunk/speakChunk for both
the first and subsequent chunks, so the two paths cannot drift.

Also verified: run() starts with N++ then M.remove(0) before reading
M.get(0); the speak block is the same as onSynthesizeText's (7 Bundle
removes, S-gated streamType/audioAttributes, volume, listenerSet,
T && !audioAttrSet audio attributes, QUEUE_FLUSH, utteranceId
"<c0>_<N>"); and there is no AtomicBoolean check anywhere in run() —
stopping works through m0() clearing M, mirrored by EasyVoice clearing
chunkQueue in onStop.

End-of-utterance: AutoTTS uses L(cb, 7) (sets p, and only calls done()
if the callback had already started), then onSynthesizeText's tail runs
K(cb, 13) which does start+done unconditionally. EasyVoice's queue-empty
path does start+done directly and its tail repeats it — same observable
outcome.

---

## 15. `getLanguageSpans` — Multilingual span segmentation (from arm64 disassembly, 2026-07-29)

`clsCLD2.nativeGetLanguages` → `getLanguageSpans` at `0x653114` in
`lib/arm64-v8a/libcld2.so` (size 0x558). **It does NOT use `CLD2::ScriptScanner` /
`GetOneScriptSpan`.** It walks the UTF-8 bytes itself and splits on its OWN
codepoint→script classifier, then calls the emitter at `0x65366c` per span.

### Loop (registers: x21 text, w22 len, w1 spanStart, w3 curScript, w24 i, w27 sc, w28 cpLen)
```
if (len < 1 || !text || !out || cap < 1) return 0;
spanStart = 0; curScript = 0; i = 0;
while (i < len) {
    if ((signed char)text[i] >= 0) {                 // ASCII fast path
        if (((text[i] & 0x5F) - 0x41) > 0x19) { i++; continue; }   // NOT A-Z → skipped
        if (curScript >= 2) { emit(spanStart, i - spanStart); spanStart = i; }
        curScript = 1; i++; continue;                // A-Z always means Latin
    }
    decode UTF-8 → cp, cpLen (2/3/4); malformed → cp = 0xFFFD, cpLen = 1
    sc = classify(cp);
    if (curScript != 0 && sc != curScript) { emit(spanStart, i - spanStart); spanStart = i; }
    i += cpLen; curScript = sc;
}
emit(spanStart, len - spanStart);
```
Note the ASCII branch: digits, spaces and punctuation are **skipped entirely** — they
neither break a span nor change `curScript`, so they are absorbed into whatever span
surrounds them.

### classify(cp) → script id
| id | ranges |
|----|--------|
| keep current | `0x80..0xBF`; `cp>>5 >= 0x7D1 + 0x20000` tail; any cp not listed |
| 1 Latin | `0xC0..0x2AF`, `0x1E00..0x1EFF` (&0x1FFF00), `0x2C60..0x2C7F` (&0x1FFFE0), `0xA720..0xA7FF`, ASCII A–Z |
| 2 Cyrillic | `0x400..0x52F` |
| 3 Arabic | `0x600..0x6FF` (&0x1FFF00), `0x750..0x77F`, `0x8A0..0x8FF`, `0xFB50..0xFDFF`, `0xFE70..0xFEFF` |
| 4 Devanagari | `0x900..0x97F` |
| 5 CJK/Kana | `0x2E80..0x2FDF`, `0x3040..0x30FF`, `0x31F0..0x31FF`, `0x3400..0x9FFF`, `0xF900..0xFAFF`, `0xFF66..0xFF9D`, `0x20000..0x2FA1F` |
| 6 Bengali | `0x980..0x9FF` |
| 7 Greek | `0x370..0x3FF` |
| 8 Armenian | `0x530..0x58F` |
| 9 Hebrew | `0x590..0x5FF` |
| 10 Georgian | `0x10A0..0x10FF` |
| 11 Gurmukhi | `0xA00..0xA7F` |
| 12 Gujarati | `0xA80..0xAFF` |
| 13 Oriya | `0xB00..0xB7F` |
| 14 Tamil | `0xB80..0xBFF` |
| 15 Telugu | `0xC00..0xC7F` |
| 16 Kannada | `0xC80..0xCFF` |
| 17 Malayalam | `0xD00..0xD7F` |
| 18 Sinhala | `0xD80..0xDFF` |
| 19 Thai | `0xE00..0xE7F` |
| 20 Lao | `0xE80..0xEFF` |
| 21 Tibetan | `0xF00..0xFFF` |
| 22 Myanmar | `0x1000..0x109F` |
| 23 Khmer | `0x1780..0x17FF` |
| 24 Ethiopic | `0x1200..0x137F` |
| 25 Hangul | `0x1100..0x11FF`, `0x3130..0x318F`, `0xA960..0xA97F`, `0xAC00..0xD7AF` |

The `0x900..0xEFF` block is computed as `(cp - 0x900) >> 7` and dispatched through a
jump table, which is why every Indic block is exactly 128 codepoints wide.

### emit() at `0x65366c`
- skips spans of length < 1
- **scripts 7..25 never reach CLD2.** `0x6536a0` computes `script - 7`; if that is
  `< 0x13` it indexes a jump table at `0x62f850` for a fixed language code and jumps
  straight to the store, past `ExtDetectLanguageSummary`/`LanguageCode`. Only scripts
  1..6 (Latin, Cyrillic, Arabic, Devanagari, CJK, Bengali) are actually detected.

  | script | | code | | script | | code |
  |---|---|---|---|---|---|---|
  | 7 Greek | | `el` | | 17 Malayalam | | `ml` |
  | 8 Armenian | | `hy` | | 18 Sinhala | | `si` |
  | 9 Hebrew | | `iw` | | 19 Thai | | `th` |
  | 10 Georgian | | `ka` | | 20 Lao | | `lo` |
  | 11 Gurmukhi | | `pa` | | 21 Tibetan | | `bo` |
  | 12 Gujarati | | `gu` | | 22 Myanmar | | `my` |
  | 13 Oriya | | `or` | | 23 Khmer | | `km` |
  | 14 Tamil | | `ta` | | 24 Ethiopic | | `am` |
  | 15 Telugu | | `te` | | 25 Hangul | | `ko` |
  | 16 Kannada | | `kn` | | | | |

  Hebrew is `iw`, CLD2's spelling, not `he` — and `m.h` has no `iw`, so a Hebrew span
  falls to the latin/non-latin fallback rather than routing to Hebrew.
- the merge/cap-stretch described below
- caps the detected slice at **1024 bytes**, backing off over UTF-8 continuation bytes
- `CLD2::ExtDetectLanguageSummary(slice, n, true, hints, 0x4000 /* kCLDFlagBestEffort */, …)`
- `CLD2::LanguageCode(lang)`; a `strcmp` against the unknown code follows
- writes 24-byte records; **the latin flag is `script_id == 1`**, NOT
  `ulscript == ULScript_Latin`

### EasyVoice status — PORTED 2026-07-29
`nativeGetLanguages` now walks the bytes itself with `glsClassify` + `glsEmit`,
exactly as above; `CLD2::ScriptScanner`/`GetOneScriptSpan` are gone and the three
headers they needed were dropped. `latin` is `script_id == 1`.

**Every range comparison in the original is UNSIGNED (`b.lo`).** Writing them as
signed `cp - 0x370 < 0x90` in C++ makes every codepoint below the range match, e.g.
U+02B0 classified as Greek and U+A000 as Latin. The port was checked by
transcribing the branch order at `0x653344..0x653618` a second time, independently,
and diffing the two over the whole codespace: **1,114,112 codepoints, 0
mismatches** (19,366 before the signedness fix).

---

## 16. Voices tab — the per-voice weight (`c3.w.e`) and when it reaches prefs

Read in full: `c3/j.java` `O1` (723-836), `D2` (373-413), `Y2` (1854-1905), `V2`
(1545-1812); `c3/m.java` `a` (112-129), `b` (131-157), `d` (179-229), `h` (275-388),
`i`/`j`/`n` (390-489), `s` (549-551), `u` (568-576), `y`/`z` (622-670), `B`/`C`
(69-83); `c3/w.java` (whole); `c3/e.java` (whole);
`com/vnspeak/autotts/NewSettingsActivity.java` `A0`/`B0` (78-130), `onPause` (410);
`com/vnspeak/autotts/AutoTtsService.java` `M`/`P`/`Q` (314-437), `a0` (736-813).

### The chain
1. `NewSettingsActivity.B0()` clears `m.d` and rescans. Every voice becomes one
   `c3.w` via `m.a(ctx, voice.getLocale(), engine)`, whose weight is seeded
   **once** from `m.s(ctx, w.f())` = `Integer.parseInt(getString(key, "1000"))`.
   `w.f()` is `pkg + "#" + locale.toString()` (`"Disable#" + locale` when
   `w.d == null`). `m.b()` de-dupes by (pkg, iso3-lang, iso3-country) and appends
   the extra `Voice.getName()` to `w.f`, the variant list, which the `w`
   constructor seeds with `"*Default"`.
2. `j.D2(iso3)` rebuilds `m.e` for the selected language, appends the `*Disabled`
   row, `Collections.sort(m.e)` (`w.b`: weight, then `g()` case-insensitively),
   and then **re-numbers every entry `h(0..N-1)`**. This is in memory only.
3. `j.O1` voice branch: `h(0)` on the picked row, `h(n4 + 1)` on every row before
   it, rows after it untouched, `Collections.sort(m.e)`, `Y2()`. **No prefs write.**
   Because step 2 normalised the weights, this is exactly a rotate-to-front.
4. `c3.m.C(ctx)` is the only writer: `m.B(ctx, m.e[i], i)` puts
   `w.f() -> String.valueOf(i)` and, for `i == 0`, `m.f(w.c) -> w.f()` — the
   `<iso3> -> "pkg#locale"` key the service later splits into `e.f`/`e.g`. It runs
   at the **top of `O1`'s language branch** and from `m.u(ctx)` in
   `NewSettingsActivity.onPause`. `commit()`, not `apply()`.

### Consequence that EasyVoice was missing
Merely *visiting* a language in the Voices tab and then switching away writes that
language's `<iso3>` engine key in AutoTTS, because `D2` normalised the weights and
`m.C` persists `m.e[0]`. EasyVoice only ever wrote on an explicit voice pick, so a
language the user had only looked at stayed `NOT_SET` and was not routable.

### Other verified divergences fixed in the same pass
- `D2` adds the `*Disabled` row for `O == 2` (auto, when `iso != F`) **and for
  `O == 4 || O == 5`** (mixed *and multilingual*, when `iso != K && iso != L`).
  EasyVoice had only auto and mixed.
- `m.h(ctx, …)` re-enables any language in `m.n()` that is flagged
  `<iso>_disabled` and persists with `m.z(ctx)`. `m.n()`: `O==1 -> {G, "eng"}`,
  `O==2|3 -> {F}`, `O==4 -> {K, L}`, otherwise empty.
- `Y2()` does nothing at all while `m.e` is empty (the variant spinner keeps its
  old adapter) and writes `m.c[b1].h = ""` when the stored variant is empty.

### Known AutoTTS defect, deliberately NOT reproduced
`Y2()` sizes the variant array at `m.e[0].f.size()`. If `m.c[b1].h` is not in that
list — pick a voice from engine A, then a voice from engine B that lacks A's
variant name — the fill writes one past the end and `O1` throws outside its own
try/catch. EasyVoice's list simply grows by one instead.

---

## 17. Advanced tab — full CFR read (fragment_advanced.xml + c3.j.R2 and its chain)

CFR files read end to end for this pass: `c3/j.java` (whole file, 2031 lines — `J0`, `F0`,
`R2`, `O2`, `P2`, `Q2`, `L2`, `E2`, `G2`, `H2`, `J2`, `M2`, `N2`, `B2`, `C2`, `F1`, `G1`,
`H1`), `c3/t.java`, `c3/u.java`, `c3/b0.java`, `c3/o.java`; resources
`res/layout/fragment_advanced.xml`, `res/values/public.xml`, `res/values/strings.xml`.

### `R2()` binds the two detection checkboxes to ONE view
CFR renders it plainly — no smali needed:

```java
object = (CheckBox)this.i0.findViewById(2131230916);   // disable_advanced_detect
object.setChecked(AutoTtsService.W);
object.setOnCheckedChangeListener(/* j$u0: W = bl */);
CheckBox checkBox = (CheckBox)this.i0.findViewById(2131231161);  // quick_character_reading
object.setChecked(AutoTtsService.X);                   // <- object, not checkBox
object.setOnCheckedChangeListener(/* j$v0: X = bl */); // <- replaces the W listener
```

`checkBox` is overwritten a few lines later by the `enable_logging` box, so
`quick_character_reading` never gets `setChecked` and never gets a listener. Net effect:

- "Disable advanced language detection" shows and toggles **X** (quick character read)
- "Quick character read" is inert and always shows the layout default (unchecked)
- **W (`disable_advanced_detection`) cannot be changed from the UI** and keeps its stored
  value, default `true`

### `c3.o` (logger)
`o.h(level, tag, msg)` failed in CFR (`ConfusedCFRException`) and was transcribed from
`/tmp/dc/smali/c3/o.smali`: DEBUG/INFO produce no logcat at all, WARN → `Log.w`,
ERROR → `Log.e`, and that happens **before** the `d` (enabled) check, so warnings and
errors reach logcat even with logging off. Then `i()` (rotate at 2 MiB, keep `.1/.2/.3`),
`String.format("%s [%s] %s: %s", ts, letter, tag, msg)` through a per-call
`BufferedWriter(FileWriter(file, true))` with `write` + `newLine` + `close`; on
`IOException`, `Log.e("TtsLogger", "Failed to write log", e)`. `o.a()` truncates with
`FileWriter(file, false)` and shows **no** toast. `o.k(ctx)` toasts "No log file to share"
when `o.b()` is null and otherwise puts `FLAG_ACTIVITY_NEW_TASK` on the **chooser**, not on
the ACTION_SEND intent. `o.j(bl)` writes `logging_enabled`.

### `c3.b0.d(ctx)` (export)
Missing `shared_prefs/*.xml` → toast "Settings file not found" and return. A failed copy →
`printStackTrace()` and return, **no toast**. Only after the copy: FileProvider authority
`packageName + ".fileprovider"`, ACTION_SEND `text/xml` with `addFlags(1)`, chooser
"Share Settings" with no extra flags. `res/xml/file_paths.xml` exposes exactly
`files-path logs/` and `cache-path shared/`.

### Import: `L2 → H1 → N2 → E2 → t dialog → G2 → F2 + Q2`
`E2` never writes anything itself. `b0.b(xml)` collects the referenced engine packages; an
empty set toasts `import_settings_error` ("Unable to load configuration. Please verify file
settings.", LENGTH_LONG) and stops. Otherwise the `c3.t` dialog is shown with one row per
package, named by `u.b(pkg)`. Apply → `G2(ctx, xml)` = `try { F2(ctx, xml); Q2(); } catch
{ printStackTrace(); }` — the relaunch is **inside** the try, so a failed import does not
restart the app. `Q2()` = launch intent + `addFlags(0x14000000)` (NEW_TASK|CLEAR_TOP),
`startActivity`, `killProcess(myPid())`, `System.exit(0)`.

`H2(ctx, pkg)` (building the dialog) uses `getPackageInfo(pkg, 0)`; `J2(pkg)` (the resume
re-check in `B2`) uses `getPackageInfo(pkg, 1)`. Both `J0` (onViewCreated) and `F0`
(onResume) end with `if (Z0 != null && a1 != null) B2()`; `R2()` itself runs only from `J0`.

### Layout facts the port now reproduces
Root is a plain vertical `LinearLayout` with **no padding** inside a `fillViewport`
ScrollView. Headers are a `LinearLayout` with background `#ff293842` holding an 18sp
`#ffffffff` TextView, `layout_margin="5dip"`, and `layout_marginTop="16dip"` on every header
except the first. Checkboxes are `TextAppearance.Medium` with `marginTop=10dip` and
`marginStart/End=5dip`; descriptions are `TextAppearance.Small` with `marginStart/End=5dip`
(the first one has `marginTop=10dip` and **no** `marginEnd`). The battery button is
`wrap_content` with `layout_gravity=center_horizontal`. Import/Export are weighted `0dip`
buttons (`marginEnd=4dip` / `marginStart=4dip`); the log buttons are `wrap_content`. Both
rows carry `marginTop=16dip`, `marginBottom=8dip`, `marginStart/End=5dip`.

`j.O2()` has no `Build.VERSION.SDK_INT` gate and adds no intent flags.

---

## 18. Modes and Languages tabs — full CFR read

CFR read for this pass: `c3/j.java` `onRadioButtonClicked` (217-338), `A2`, `I2`, `r2`,
`z2`, `T2` (1046-1218), `U2` (1219-1544), `V2` (1545-1812), `X2`, `Z2`, `P1`, `I1`, `J1`,
`K1`, `L1`, `M1`, `N1`, and the `j.z0` filter adapter (1981-2030); `c3/v.java`; resources
`res/layout/fragment_modes.xml`, `res/layout/fragment_languages.xml`, `res/values/public.xml`,
`res/values/strings.xml`.

### `onRadioButtonClicked`
`AutoTtsService.O` is the only thing written — nothing is persisted here; `m.v(ctx)` writes
`auto_mode`, and it runs from `m.u(ctx)` on pause. Per case:

| id | O | visible section | list rebuilt | adapter | selection |
|---|---|---|---|---|---|
| auto_mode_none | 0 | none | **no** | **none** | **none** |
| auto_mode_dual | 1 | F0 | `m.h(ctx,false)` | `m.m(null)` | `m.g(G)` → s0 |
| auto_mode_auto | 2 | E0 | `m.h(ctx,false)` | `m.m(null)` | `m.g(F)` → r0 |
| auto_mode_google | 3 | E0 | `m.h(ctx,false)` | `m.m("com.google.android.tts")` | `m.g(F)` → r0 |
| auto_mode_mixed | 4 | G0 | `m.h(ctx,false)` | `m.m(null)` | `m.g(K)` → x0,z0; `m.g(L)` → y0,A0 |
| auto_mode_multilingual | 5 | H0 | `m.h(ctx,false)` | `m.m(null)` | same as 4 |

None is the odd one out: it hides the four sections and returns. `m.g(iso3)` indexes into the
unfiltered `m.c`, while the adapter is `m.m(pkg)` — the two only line up when `pkg` is null,
which is every mode except Google.

Modes 4 and 5 set adapters on all four spinners and select in all four, because the
Multilingual pair writes the very same `K`/`L` the Mixed pair writes (`O1` ids 2131231057 →
K, 2131231058 → L, 2131231089 → K, 2131231090 → L). Multilingual has no number and no
punctuation spinner. `emoji_mode_language` is persisted by `m.w` but has no spinner anywhere.

The Google radio and its description are `android:visibility="gone"` in
`fragment_modes.xml`; `U2` only calls `O0.setEnabled(v.a(ctx))` and never makes them visible.

### `U2` tail
The four number/punctuation spinners share one adapter of
`{number_mode_auto_language, number_mode_primary_language, number_mode_secondary_language}`
= "Auto language" / "Primary language" / "Secondary language"; `t0`/`v0` take `H`, `u0`/`w0`
take `I`, and `O1` mirrors each pair. Then the mode radio is dispatched, and only after that
are `R0`/`S0`/`T0` (the three locale-span checkboxes over the single flag `Q`) given their
`setChecked` and then their listeners, so the initial state never fires the mirroring.

### `T2`
`m.c` is rebuilt with `m.h(ctx,false)` and `m.y(ctx)` runs on entry. Label/code/checked lists
come from `m.m/k/l(pkg)` where `pkg` is `com.google.android.tts` only for `O == 3`. `m.l`
force-enables the mode's languages (no case for `O == 5`). The click handler reads the new
state off the ListView, stores it in `o0`, and for a language in `m.n()` only re-checks the
row — `c3.e.i` is not written. `j.I2` returns true on every path, so select-all clears
`c3.e.i` for all of `m.c`. Clear-all disables everything, re-enables `m.n()`'s languages in
both `m.c` and `o0`, re-checks their rows through `z0.b(originalIndex)`, and re-filters only
when `q0` is set.

### Sliders — still to port (`c3.m.y` semantics)
`j.P1` and the six ±5 buttons only mutate the in-memory `c3.e` (`c` speed / `d` volume /
`e` pitch), clamping at 10; `j.Z2` (Default) sets all three to 100 in memory. **Nothing there
touches SharedPreferences.** `c3.m.y(ctx)` is the writer, and it writes `<iso>_speed`,
`<iso>_volume`, `<iso>_pitch` **only when the value differs from 100** and `<iso>_variant`
only when it differs from `"*Default"`, and it never removes a key. So returning a slider to
100 — including with the Default button — leaves the old value in prefs, and the service
keeps using the in-memory 100 until the process restarts. EasyVoice writes every change
straight through, which is a different persisted state.

### Service startup order (`onCreate`, 1555-1590)
`m.c()` → `Y()` → `e0()` → `a0()` → `X()` → `m.p(ctx)` → `m.r(ctx)` → `U()` → `n = true` →
`T()`. `Y()` (the engine list) therefore runs **before** `a0()` fills `m.c` and **before**
`m.p` loads `O`. Its `defaultEngine = M(m.f(Locale.getDefault()))` consults `m.c`, which in a
fresh process is empty, so `M` returns `""`: no engine is promoted to index 0 and none is
skipped, and `P` ends up as `engine_0..N` verbatim (falling back to `com.google.android.tts`
when empty and installed). Only when the settings Activity already populated `m.c` in the
same process, or when `O == 3` short-circuits `M` to Google, does the promotion happen.
EasyVoice's `buildEngineList()` reads the engine straight from prefs and always promotes —
that gap is open.

---

## 19. AutoTtsService — the rest of the file, checked against EasyVoice

Read in this pass (CFR, whole methods): the static block, `H`–`L`, `M`–`R`, `S`, `T`, `U`,
`V`, `W`, `X`, `Y`, `a0`, `c0`, `d0`, `e0`, `f0`, `g0`, `h0`, `j0`, `k0`, `l0`,
`onGetDefaultVoiceNameFor`, `onGetLanguage`, `onGetVoices`, `onIsLanguageAvailable`,
`onIsValidVoiceName`, `onLoadLanguage`, `onLoadVoice`, `onStartCommand`, `onStop`,
`onCreate`, `onDestroy`. `onSynthesizeText`, `Z`, `b0`, `i0`, `m0`–`o0` were verified in the
earlier passes recorded in commits b2f1391, a93a452, 2c23367, f64975c, ef19087, facd5b3 and
cf65f50 and are unchanged.

Confirmed identical, no edit needed:
- `k0(Locale)` — three passes over the `voice_N` list (lang+country+variant, then
  lang+country, then lang), each aborting the whole search with `""` the moment an entry's
  locale fails to parse. EasyVoice's `findEngineForLocale` is this, `?: return ""` included.
- `onGetVoices` = `m.j(null, true)` → one `Voice(iso, Locale(iso), 400, 100, false, {})` per
  non-disabled language.
- `onIsLanguageAvailable` = `m.j(null,true).contains(lang) ? 0 : -2`;
  `onIsValidVoiceName` = the same membership test but `-1` on miss.
- `onLoadVoice` stores the name in the static `e0`, parses it with `f0`, and forwards to
  `onLoadLanguage(iso3, iso3Country, variant)`; anything outside {0,1,2} is `-1`.
- `onGetDefaultVoiceNameFor` returns its `lang` argument; `onStartCommand` returns 1.
- `V(a, b)` — lang must match, then an empty country or a matching one, then an empty
  variant or a matching one.
- `X()` returns immediately when `F != null`, and fills `F/K/L/G` from prefs, each falling
  back to `m.f(Locale.getDefault())` when empty, then `H`, `I`, `J`.
- `h0()` requests audio focus with usage 11 / content type 1; `l0()` needs `S()`
  (POST_NOTIFICATIONS on API 33+) and uses `a.a(this, 136549, I(), 2)` on API 34+,
  `startForeground(136549, I())` below.
- static defaults: `W = true`, `X = false` in the class initialiser, but `m.r` reads
  `quick_character_reading` with default **true**, so a fresh install ends up with X true.

The two gaps this pass found — the slider write rule and the `Y()`-before-`a0()` ordering —
are fixed in the commit that introduces `LangStore`.

---

## 20. Voices tab — fragment_voices.xml read view by view

CFR/res read for this pass: `res/layout/fragment_voices.xml` in full, `c3/k.java`,
`c3/a0.java`, plus the `c3/j.java` methods that drive the tab (`V2`, `O1`'s three spinner
branches, `D2`, `Y2`, `W2`, `Z2`, `P1`, `I1`, `J1`, `K1`, `L1`, `M1`, `N1`) and
`res/values/public.xml` / `strings.xml` for every id and string.

### Tree, with the margins that were missing
`@id/scroller` is `fillViewport`; `@id/VoiceSettings` inside it carries **no padding**.
`@id/VoicesInactive` is a sibling of the ScrollView, and `V2` swaps their visibility —
for `O == 0` the scroller goes and the inactive block appears, and `V2` **returns before**
the reset button and the dedicated-engines checkbox are ever wired.

Both blocks open with the same `#ff293842` `RelativeLayout` bar whose TextView is
`TextAppearance.Medium`, white, `layout_margin="5dip"`. Then, inside `linear_voice`:

| view | width | marginTop | marginStart | style |
|---|---|---|---|---|
| `text11` tip | fill_parent | 10dip | 5dip | Small |
| `text22` "Select language" | fill_parent | 5dip | 5dip | Small |
| `autotts_languages` | fill_parent | 2dip | **5dip** | — |
| `text33` | fill_parent | 5dip | 5dip | Small |
| `autotts_voices` | fill_parent | 2dip | **none** | — |
| `text333` | fill_parent | 5dip | 5dip | Small |
| `autotts_voice_variant` | fill_parent | 2dip | **none** | — |
| `testbutton` | **fill_parent** | — | — | — |

`@id/params` is a vertical `LinearLayout` at `marginStart="5dip"`; each slider label is
`gravity=center`, `fill_parent`, **no style attribute** (default appearance); each row is
`- button (wrap_content, layout_gravity=start, ?android:buttonStyleSmall)`, `SeekBar (0dip,
weight 1, gravity center, layout_gravity center, secondaryProgress 0)`, `+ button
(wrap_content, layout_gravity=end)`. The three SeekBars declare `android:max="19"` in the
XML, but `V2` overrides that with `setMax(500)` speed, `setMax(100)` volume, `setMax(200)`
pitch — the code wins, and `setAccessibilityLiveRegion(1)` is set on all three.

After `params`, still inside `VoiceSettings`: `autotts_reset` (`wrap_content`,
`layout_gravity=center`, buttonStyleSmall, text and contentDescription both "Default"), a
`wrap_content` `TextAppearance.Medium` TextView "Experimental" at `marginStart="5dip"`, the
`dedicatedengines` CheckBox at `fill_parent`/`marginStart="5dip"`, and a Small `fill_parent`
description at `marginStart="5dip"` with **no** marginTop.

### `Z2()` order
`W0 = U0 = V0 = 100`, then `m.c[b1].d = V0` and the **volume** SeekBar, `m.c[b1].c = U0` and
the **speed** SeekBar, `m.c[b1].e = W0` and the **pitch** SeekBar — volume, speed, pitch, in
that order, each write immediately followed by its own `setProgress`. No persistence.

### `W2()` (Test)
Bails when `m.g` is null, `m.e` is empty or `m.e[0].d` is null. `pkg = m.e[0].d.b`,
`loc = m.e[0].c` — the voice's own `Locale`, not a re-parsed tag — `variant = m.c[b1].h`
(the in-memory language entry, so the LangStore entry now), and the sample is
`a0.a(m.f(loc))`, keyed on the iso3 of **that** locale rather than the selected language.
Empty sample → `"Sorry. Sample text for language " + loc.getDisplayName(new Locale("eng")) +
" is missing."`; otherwise `"[AutoTTS:" + pkg + ":" + loc + ":" + variant + "]" + sample`.
`speak(..., QUEUE_FLUSH, null, "AutoTTS_Test")`.

### `c3.a0` sample texts — verified, not eyeballed
`c3.a0.a` holds 184 entries. EasyVoice's `SampleTexts` was diffed against it key by key and
value by value after unescaping both sides: **184/184 keys present on both sides, 0 value
mismatches.** `a0.a(iso3)` returns `""` for a miss, which is the branch that produces the
"Sorry. Sample text …" line.

### `c3.k.a(SeekBar, CharSequence)`
A one-line bridge to `SeekBar.setStateDescription`. `P1` calls it only under
`Build.VERSION.SDK_INT >= 30` and passes the **literal** max (500 / 100 / 200), not
`getMax()`. The six +/-5 buttons never touch the state description — only `setProgress`
reaching `onProgressChanged` does.

---

## 21. Licenses tab — fragment_license.xml + c3.j.S2()

`res/layout/fragment_license.xml` is a `fillViewport` ScrollView over a vertical
`LinearLayout` with **no padding**, holding the `#ff293842` `RelativeLayout` bar (TextView
`TextAppearance.Medium`, white, `layout_margin="5dip"`, `@string/tab_text_5` = "Licenses")
and `@id/licences_text` — `fill_parent`, `layout_margin="5dip"` on all four sides,
`TextAppearance.Small`.

`S2()` is three statements: `Html.fromHtml(Q(@string/autotts_license), 0)` (flag 0 is
`FROM_HTML_MODE_LEGACY`), `setText`, `setMovementMethod(LinkMovementMethod.getInstance())`.

`@string/autotts_license` is, in order: a bold copyright paragraph, one paragraph naming
AOSP and CLD2 under Apache 2.0, then a `<ul>` of two `<li>` blocks (AOSP, then CLD2), each
with a bold-italic title, its copyright line, the two Apache paragraphs and an `<a href>` to
the licence. There is **no version line and no installed-engines line** anywhere in it — the
ones EasyVoice used to print were invented and have been removed. The opening copyright
paragraph is the single line that cannot be copied literally, since it names AutoTTS's own
rights holder; that slot carries this app's name instead.

`tab_text_1..5` are "Modes", "Languages", "Voices", "Advanced", "Licenses".

### Dead helpers removed
Every tab in `TabViews.kt` and `LanguagesVoicesViews.kt` now builds its views straight from
the XML, so the generic `sectionHeader` / `subHeader` / `descriptionText` / `spinnerLabel` /
`addCheckRow` / `headerText` / `bodyText` / `labelText` helpers had no callers left and are
gone, along with `parseLocaleTag` and the `selectedEnginePkg` / `selectedLocaleTag` pair the
Test button no longer reads (it takes the engine and the Locale off `voiceRows[0]`, which is
`m.e[0]`). Leaving them invites a future pass to reach for a style AutoTTS does not have.

---

## 22. The settings shell — new_settings_activity.xml + NewSettingsActivity.onCreate/E0

`activity_tts_settings.xml` is a **leftover** from an older single-screen design (it still has
`android:onClick="onRadioButtonClicked"`, `goToVoiceSettings`, `go_to_mode_settings`). The
live layout is `new_settings_activity.xml`, which `onCreate` inflates:

1. root `LinearLayout` vertical, `fitsSystemWindows="true"`
2. `AppBarLayout` (`android:theme="@style/AppTheme.AppBarOverlay"`) wrapping a horizontal
   `LinearLayout` at `gravity=center_vertical`, `padding=@dimen/appbar_padding` (**16dip**),
   `minHeight="?actionBarSize"` — a 32dip × 32dip `ImageView` of `@drawable/ic_launcher` with
   `contentDescription=@string/app_name` and `marginEnd="8dip"`, then `@id/title` at
   `@style/TextAppearance.Widget.AppCompat.Toolbar.Title`. **The row itself has no
   background** — the AppBarLayout supplies it.
3. `@id/linlaHeaderProgress`, `gravity=center`, vertical, **height 0dip weight 1**: a
   `TextAppearance.Large` TextView (`@string/autotts_progress`), a
   `@android:style/Widget.ProgressBar.Large` ProgressBar at `marginTop="10dip"`, and
   `@id/current_engine` at **`TextAppearance.Medium`** (`@string/autotts_scan_engines` =
   "Scan for tts engines…"). **None of the three carries any padding.**
4. `ViewPager2 @id/view_pager`, `visibility="gone"`, height 0dip weight 1
5. `TabLayout @id/tabs`, `background="?colorPrimary"`, `app:tabGravity="fill"`,
   **`app:tabIndicatorGravity="top"`**, `app:tabMode="fixed"` — last in the column, so the
   tab row sits at the bottom and its indicator is drawn along the top edge.

`onCreate` then: `setOffscreenPageLimit(4)`, `TabLayoutMediator` giving each tab its icon
(`ic_tab_modes`, `ic_tab_languages`, `ic_tab_voices`, `ic_tab_settings`, `ic_tab_licenses`),
its `tab_text_N`, and — at this point — the **plain** title as its content description.
`E0(...)` immediately overwrites every one of those with
`@string/cd_tab_title_param` = `"%1$s, tab %2$d of %3$d"`.

`onPageSelected` does two things: `E0(...)` again for all tabs, then
`findFragmentByTag("f" + position)` and, if it is a `c3.j`, calls **`F0()`** on it — which
re-runs `U2()` for the Modes tab, `T2()` for Languages and `V2()` for Voices, and nothing for
Advanced or Licenses. EasyVoice's `notifyItemChanged(position)` for `position <= 2` is that,
and `onBindViewHolder`'s `if (position >= 3 && childCount > 0) return` is why 3 and 4 stay put.

Then `m.q(ctx)` (F/K/L/G each falling back to `m.f(Locale.getDefault())`, plus H/I/J),
`m.p(ctx)`, `m.r(ctx)`, the license-status line — which EasyVoice has no counterpart for and
must not fabricate — the 180 s watchdog, `B0()` and the self-engine `TextToSpeech` that
becomes `m.g` for the Test button.

### What was off in EasyVoice
No `AppBarLayout` and no launcher-icon `ImageView`; the title used `TextAppearance.Large` +
hardcoded white on a `colorPrimary` row instead of the Toolbar.Title appearance on a plain
row inside an AppBarLayout; no `minHeight=?actionBarSize`; the progress block's three
children carried invented 32dp/10dp paddings and the third line was `Small` rather than
`Medium`, with a default-size ProgressBar rather than `progressBarStyleLarge`; the TabLayout
used `colorBackground` instead of `?colorPrimary` and had no `tabIndicatorGravity=top`.
The `ImageView` resolves the icon through `applicationInfo.loadIcon(packageManager)` because
this app ships no `ic_launcher` of its own — that reads the app's real icon rather than
inventing artwork the original does not have.

---

## 23. The three Voices-tab dropdowns, end to end

Chain re-read for this pass: `c3/w.java` in full (`b`, `c`, `d`, `e`, `f`, `g`, `h`),
`c3/n.java`, `c3/e.java`, `c3/m.java` (`a`, `b`, `d`, `g`, `h`, `i`, `j`, `k`, `l`, `m`, `s`,
`B`, `C`, `y`, `z`), `c3/j.java` (`V2`, `O1`, `D2`, `Y2`, `W2`, `Z2`),
`NewSettingsActivity.A0`/`B0`.

### Wiring order
`V2` attaches the listeners to all three spinners **before** it sets any adapter, then sets the
language adapter last. That first `setAdapter` fires `O1(pos = 0)` → the language branch →
`D2` + `Y2`, and `Y2` sets the voice and variant adapters, which fire `O1(0)` again for each.
The voice branch returns on position 0; **the variant branch does not** — it has no guard, so
it writes `m.c[b1].h` and calls `m.y(ctx)` on that very first pass.

### Where each list comes from
| spinner | list | filter |
|---|---|---|
| `autotts_languages` | `m.i()` for O 1, `m.j("com.google.android.tts", false)` for O 3, `m.j(null, false)` otherwise | `m.i` keeps `eng` + `G`; `m.j` skips `e.i` and, with a pkg, requires `e.j.contains(pkg)` |
| `autotts_voices` | `m.e[n].d()` after `D2` + `Collections.sort` | `D2` keeps voices of the selected iso3, Google-only for O 3, and appends the `*Disabled` row for O 2 (iso != F) and O 4 or 5 (iso != K and != L) |
| `autotts_voice_variant` | `m.e[0].f`, reordered by `Y2` | none |

Both adapters are `simple_spinner_item` (17367048) with
`setDropDownViewResource(simple_spinner_dropdown_item)` (0x1090009). The language spinner is
never `setSelection`-ed in `V2` — it always lands on 0.

### Three things that were still off, now fixed
1. **The sort tie-break.** `w.b` is
   `if (this.e != w3.e) return this.e - w3.e; return this.g().compareToIgnoreCase(w3.g());`
   EasyVoice compared two `.lowercase()` strings, which is not the same relation —
   `compareToIgnoreCase` folds each char through `toUpperCase(toLowerCase(c))`. Now a literal
   `Comparator` with `compareTo(other, ignoreCase = true)`.
2. **`w.g()`'s split.** Java's `name.split(" ")` drops trailing empty strings at the default
   limit; Kotlin's keeps them, so an engine label ending in a space took a different branch.
   Now `split(" ").dropLastWhile { it.isEmpty() }`.
3. **`Y2` reads the variant from memory.** It is `m.c.get(b1).h`, and when that is empty `Y2`
   writes `""` back into the same entry. EasyVoice was reading and writing
   `<iso>_variant` in prefs instead, which is a different value the moment the two drift.
   Now it goes through the LangStore entry, the same one `W2` reads for the Test string.

### And one in the Languages tab, same defect class
`m.m`, `m.k` and `m.l` all read `c3.e.a`, which `m.h` set from `w.c()` =
the scanned voice `Locale`'s `getDisplayLanguage()`. The Languages tab was labelling rows from
a hardcoded name table, which changes both the visible label and the collator order it is
sorted by. The scan locale's display name now wins, and the table is only the fallback for a
language with no scanned voice.

### Verified equal, left alone
`w.d()` uses `getCountry()` (not `getISO3Country()`) for its emptiness test and
`getDisplayCountry()` for the suffix. `w.e()` is `m.f(this.c)`. `w.h(n)` is a plain setter.
`c3.n.a()` marks a package as self when it contains `"autotts"` or `"multilingualtts"`;
`EngineFinder.getEngines` excludes both markers. `m.B`/`m.C` use `commit()`. `m.s` defaults to
`"1000"` and parses with no catch — EasyVoice catches instead of reproducing a crash.

---

## 24. The engine scan — B0 → i.onInit → y0 → j.onInit → D0 → z0

Read in full: `NewSettingsActivity.java` (620 lines — `A0`, `B0`, `C0`, `D0`, `E0`, `y0`,
`z0`, `onCreate`, `onPause`, `onDestroy`, and the two `OnInitListener` inner classes `i` and
`j`), `c3/n.java`, `c3/w.java`, `c3/m.java` (`a`, `b`, `h`, `u`, `x`, `y`, `z`), and the
strings `autotts_scan`, `autotts_engine_failed`, `autotts_progress`, `autotts_scan_engines`.

### The state machine
| field | role |
|---|---|
| `J` | Map pkg → `c3.n` of what `B0` discovered |
| `m.b` | the engine list (static, shared) |
| `m.d` | the voice list (static, shared) |
| `L` | index of the engine being probed |
| `M` | "this engine's onInit fired" |
| `N` | Handler for the **30 s per-engine** watchdog |
| `K` | Handler for the **180 s global** watchdog → `z0()` |
| `O` | indices of engines that failed |
| `F` | the probe `TextToSpeech` |

1. **`B0()`** clears `m.b` and `m.d`, then `queryIntentServices` with flags 131072, 128 and 0,
   skipping any package containing the app's own marker and anything already in `J`.
2. **`F = new TextToSpeech(this, new i(...), OWN_PKG)`** — the own engine is probed for exactly
   one reason: `i.onInit` calls `F.getEngines()` and **merges in every engine the three
   queryIntentServices passes missed**, skipping what `J` already holds and anything carrying
   the own marker. Then `F.shutdown()` and `y0()`. A failed probe instead toasts
   `autotts_engine_failed` at LENGTH_LONG and jumps straight to `z0()` — no per-engine scan.
3. **`y0()`** sets `L = 0`, `M = false`, arms `N` for 30 s, prints
   `getString(autotts_scan) + " <pkg>... (3)"` into `@id/current_engine`, and builds the probe.
4. **`j.onInit(status)`** sets `M = true`, clears `N`, and: non-zero status → `O.add(L)`;
   otherwise reflect `mCurrentEngine` — a null field or a thrown exception both mean "take the
   package at its word" — and if the engine answered as somebody else, `O.add(L)` instead of
   reading voices. `A0` then makes one `c3.w` per (pkg, iso3 lang, iso3 country) and appends
   every `Voice.getName()` to the variant list the `w` constructor seeds with `"*Default"`.
   Finally `F.shutdown()` in a try/catch, then `D0()`.
5. **`D0()`** — `L++`, walk past every engine `c3.n.a()` calls self, then either start it
   (printing `"... (2)"` this time) or `K.removeCallbacksAndMessages(null)` and `z0()`.
6. **`z0()`** dedupes `O`, sorts it descending, removes those engines from `m.b` and their `w`
   from `m.d`, rebuilds `m.c` with `m.h(ctx, false)`, calls `m.u(ctx)` — whose `m.x` writes
   `engine_N` from what is left of `m.b`, skipping only the app's own package — sets
   `m.g` to a fresh own-engine `TextToSpeech` for the Test button, and swaps
   `linlaHeaderProgress` for the pager.

### CFR was wrong about `D0`'s increment — smali settled it
CFR renders the skip loop as `while ((n3 = ++this.L) < size && m.b.get(this.L).a()) {}`, an
empty-bodied loop with a **pre-increment inside the condition** and an unused assignment. Taken
literally that advances twice before the first `.a()` test and silently skips an engine. The
smali at `NewSettingsActivity->D0()` is unambiguous:

```
L = L + 1;  if (L >= size) -> done
:loop  if (L >= size) -> after
       if (!m.b.get(L).a()) -> after
       L = L + 1;  goto :loop
:after if (L >= size) -> done ; else found
```

so it is the ordinary `while (L < size && m.b.get(L).a()) L++;`. Recorded because this is the
rule-7 fallback earning its keep — the CFR shape was unreadable in the exact way the rule
names, and the smali trip was not wasted.

### What was off in EasyVoice
- **No own-engine probe at all**, so `TextToSpeech.getEngines()` was never consulted and any
  engine the three `queryIntentServices` passes miss was invisible. Added, with the
  `autotts_engine_failed` toast and the straight-to-finalize path on a failed probe.
- **No `c3.n.a()` skip while advancing.** `getEngines` filters both markers up front, but the
  `getEngines()` merge only filters the own marker — exactly as `i.onInit` does — so the skip
  is the second line of defence and now exists.
- **`engine_N` came from the engines that succeeded.** `z0` + `m.x` write what is left of
  `m.b` after removing the failures, which is not the same list: an engine `D0` skipped for
  being self was never scanned, never failed, and therefore still gets written. The scan now
  tracks `m.b` and the failed set separately and writes the survivors.
- **`@id/current_engine` was a fixed string.** It is a live per-engine readout in AutoTTS,
  `"Scanning <pkg>... (3)"` for the first and `"... (2)"` for the rest; the scan now drives it
  through a callback.
- **The `onlyPkg` scoped-scan parameter had no AutoTTS counterpart** and both callers passed
  null. Removed.

---

## 25. Internal audit — what was left, and what cannot be ported

Inventory of the reference: `com/vnspeak/autotts/` is 7 classes and `c3/` is 33. Going
through the ones no earlier pass had opened:

### Ported now
- **Theme.** `res/values/styles.xml`:
  `AppTheme` = `Theme.AppCompat.DayNight.DarkActionBar` with `colorAccent #ffd81b60`,
  `colorPrimary #ff008577`, `colorPrimaryDark #ff00574b`;
  `AppTheme.AppBarOverlay` = `ThemeOverlay.AppCompat.Dark.ActionBar`;
  `AppTheme.NoActionBar` = `AppTheme` + `windowActionBar=false` + `windowNoTitle=true`.
  EasyVoice was on `Theme.DeviceDefault` with a `values-night` twin and **no AppBarOverlay at
  all**, so the app bar and the tab row took whatever colours the OEM supplies instead of the
  teal/pink pair, and the bar had no dark overlay. `DayNight` is what handles night, which is
  why AutoTTS ships no `values-night` AppTheme and ours is gone.
  Consequence worth naming: under `Theme.AppCompat`, `colorPrimary` and `colorAccent` are
  **appcompat** attributes — the framework `android:colorPrimary` is simply unset — so the
  TabLayout's colour lookups had to move to `androidx.appcompat.R.attr.*` or they would have
  read 0. `textColorSecondary` stays a framework attr and still resolves.

### Verified equal, no edit
- `CheckVoiceData`: `m.j(null, true)` → `putStringArrayListExtra("availableVoices", …)`,
  `setResult(1)` = `CHECK_VOICE_DATA_PASS`.
- `GetSampleText`: `a0.a(locale.getISO3Language())` — note it uses the raw ISO3, **not**
  `m.f`, so no cmn/lzh/gan/hak → zho folding here; empty sample falls back to the same
  "Sorry. Sample text …" line; `setResult(0)` = `LANG_AVAILABLE`, extra `"sampleText"`.
- `c3.l`: `f() = 5`, `T(n)` = `tab_text_{n+1}`, `B(n)` = `j.K2(n + 1)`.
- `c3.a` / `c3.b` / `c3.c0` / `c3.d0` / `c3.e0` / `c3.f` / `c3.g` / `c3.i` / `c3.p`–`c3.s`:
  one-line bridges to `startForeground`, the audio-focus callback, `f0`'s thread factory and
  runnables, and the activity-result / dialog lambdas. All already inlined on our side.
- `c3.x.a(String)`: whitespace-only over codepoints, used by **`y.c`** —
  `x.a → 0`, `y.e → 4`, `y.d → 3`, else `1`. The C++ classifier is
  `allWs ? 0 : (allPunct ? 4 : (allNumber ? 3 : 1))`, and `y.g` as a whole was already
  differentially verified over ~51,000 strings. Nothing to change; recorded so the type-0
  branch is not mistaken for the invented whitespace pass that was removed earlier.
- `c3.c.a()`: the emoji regex appended into `y`'s pattern `d`; the C++ `isEmoji`/`latRange`
  pair was verified over the whole codespace.

### Deliberately not ported
- **`c3.g0` and the licence path.** `g0.b(ctx)` hashes the APK's signing certificate and
  compares it to a hardcoded Base64 prefix; `g0.a`/`g0.c`/`g0.d` are the obfuscation around
  it. `AutoTtsService.U()` builds a Google LVL `LicenseChecker` with AutoTTS's own public key
  and `ServerManagedPolicy`, `j0(status, text)` stores `license_status`/`license_text`, and
  `NewSettingsActivity.C0()` reads that back to decorate the title with
  `String.format("Auto TTS (%s)", status)`. Porting this would mean embedding another
  developer's signing hash and Play licensing key into this app, where they are meaningless.
  This is the same class as the copyright line in §21: it names AutoTTS itself, not its
  behaviour. Left out, and the title decoration with it.

---

## 26. Every remaining c3 file, read

`c3/` is 33 files and no subdirectories. Going through the ones no pass had opened in full:

### `c3/e.java` — the language entry
`a` display name, `b` iso3, `c/d/e` speed/volume/pitch (all defaulting to 100 in the two-arg
constructor), `f` engine, `g` locale string, `h` variant defaulting to `"*Default"`,
`i` disabled defaulting to false, and **`j` a `LinkedHashSet`** of the engine packages that
provide the language. `LangStore.LangEntry` mirrors every field except `j`, which only feeds
`m.j`/`m.k`/`m.l`'s engine filter — we take that from the scan's voice list instead, which is
the same information from the same source.

### `c3/f0.java` — the engine wrapper
`a` is the package with `-` and `_` stripped. `h()` is the retry gate,
`k == 0 || ((nanoTime() - j) / 1e6 > 3000.0 && k < 10)`; `i()` stamps `j` and increments `k`.
`l()`/`m()` submit shutdown/stop to a per-wrapper
`ThreadPoolExecutor(0, 5, 60s, LinkedBlockingQueue, factory)` whose factory makes one daemon
thread named `"TtsStop"`. `EngineWrapper` matches field for field.

### `c3/d.java` — the keep-alive binder
`c(pkg)` short-circuits when the package is unchanged and still bound, else unbinds, resolves
`android.intent.action.TTS_SERVICE` in that package, sets the explicit component, and binds
with flags **65** = `BIND_AUTO_CREATE | BIND_IMPORTANT`. The connection has four callbacks:
`onServiceConnected` logs, `onServiceDisconnected` calls the service's `i0(pkg)`,
`onBindingDied` unbinds and rebinds the same package, `onNullBinding` unbinds.
`bindEngineKeepAlive` is this, callback for callback.

### `c3/z.java` — the LocaleSpan splitter
Two quirks are load-bearing and were already reproduced: `getSpans(0, length - 1, …)` — a span
starting on the last character is missed — and `prevEnd = spanEnd + 1`, which drops the
character sitting at each span's end. With `Q` off it returns the whole text as one
`"UNKNOWN"` chunk.

### `c3/p`–`c3/s`, `c3/x`
`p`/`q`/`r`/`s` are the four dialog click/callback lambdas for `c3.t`. `x.a(String)` is the
whitespace-only test behind `y.c`'s type 0.

### Two gaps this pass found
- **`T()` submits `m()` and `l()` as two separate `execute()` calls**, wrapped together in one
  `try/catch (Exception)`. `initAllTTS` was submitting a single runnable that did stop and
  shutdown in sequence. Now two, with the try around the pair.
- **`onDestroy` calls plain `f0.l()`** for every wrapper at state 2 — one `execute` of
  shutdown, and it is **not** inside a try. Ours had a `catch` that fell back to calling
  `shutdown()` directly on the caller's thread, which AutoTTS never does.

### Not ported, unchanged from §25
`c3/g0` and the licence path — signing-certificate hash, Google LVL key, `license_status` /
`license_text` and the `"Auto TTS (%s)"` title decoration they drive.

---

## 27. `AutoTtsService.Z()` — what onLoadLanguage actually is

`onLoadLanguage(l, c, v)` is one line: `synchronized(this) { return Z(l, c, v); }`. `Z` reads:

```java
int res = onIsLanguageAvailable(lang, country, variant);
String voiceLoc, enginePkg, variantOut;
if (variant.contains("autotts.") && res == 2) {
    voiceLoc = lang + "_" + country;  enginePkg = variant.substring(8);  variantOut = "";
} else {
    voiceLoc = Q(lang);  enginePkg = M(lang);
    variantOut = variant.isEmpty() ? P(lang) : variant;
}
if (enginePkg.isEmpty()) { voiceLoc = Q(F); enginePkg = M(F); }
switch (res) { case 0: …; case 1: …; case 2: …; default: return res; }
```

Three things fall out of reading it whole:

1. **Google mode has no branch here.** `M(lang)` returns `"com.google.android.tts"` when
   `O == 3`, and **`Q(lang)` and `P(lang)` both `return lang` on `O == 3`** before they touch
   `m.c`. So in Google mode `voiceLoc` is the iso3 string, the engine is Google, and the
   variant — when the request carries none — is **the language code**, not `""`. EasyVoice had
   an `if (modeInt == 3)` early return that passed `v ?: ""` as the variant and skipped the
   `this.c` bookkeeping. Removed; the three lookups now produce it.
2. **Cases 1 and 2 of the switch are dead.** `onIsLanguageAvailable` only ever answers `0`
   (`m.j(null,true).contains(lang)`) or `-2`, and the `"autotts."` variant path additionally
   requires `res == 2`. Only case 0 runs. It is:
   `loc = f0(voiceLoc)`, null → `-2`; if `m.f(loc).equals(lang)` → `b0(enginePkg, loc,
   variantOut, R)` (and `this.c = enginePkg` when it differed); otherwise
   `b0(k0(new Locale(lang)), new Locale(lang), variantOut, R)`.
3. **The empty-engine fallback tests `M`'s result only.** `"Disable"` is not empty, so a
   disabled language is carried into `b0` as the literal engine `"Disable"` rather than being
   replaced from `F`. EasyVoice's `== "NOT_SET"` test is the same relation, since
   `LangStore.engineFor` returns `""` where `M` does.

`Q` and `P` were coming from SharedPreferences (`getLocaleForLangPkg`, `getVoiceName`); they
are `c3.e.g` and `c3.e.h` off the in-memory list, so they now go through
`LangStore.localeFor`/`variantFor`, which also carry the `O == 3` short-circuit.

---

## 28. `b0`, `m0`, `n0`, `o0`, `g0`, `L` — the rest of the service methods

### `b0(pkg, locale, variant, dedicated)` — loadVoice
`pkg.isEmpty() ? pkg = this.c : this.c = pkg`, normalise by stripping `-` and `_`, find the
wrapper at state 2 with that normalised name, else `this.d = -1` and return. Then:
`variant.isEmpty() && !wrapper.e.isEmpty()` → hand off to `c0(norm, locale)`. Otherwise read
the engine's current `Voice` (locale `zxx` and name `""` when there is none) and return early
when language, country — or an empty requested country — and voice name all already match.
Then the variant lookup, then one of two writes, then `wrapper.d = locale; wrapper.e = variant`.

Two things worth stating:
- **The variant lookup goes through `m.c`.** `b0` scans the `voice_N` list for
  `pkg#locale`, and on a hit walks **`m.c`** for the entry whose `e.f` and `e.g` are that
  engine and that locale, taking `e.h`. EasyVoice was re-deriving it from
  `getLanguageList()` + the `<iso>` pref + `getVoiceName`; it now reads `LangStore.c`
  directly, which is that list.
- **Every `i0` call inside `b0`, `c0` and `d0` passes `f.get(d).e()` — the NORMALISED
  package.** EasyVoice was passing `wrapper.realPkg`, the raw one, while `restoreEngine`
  matches against the normalised `enginePool[n].pkg`. For any engine whose package contains
  `-` or `_` the restore silently found nothing. Seven call sites, all now `wrapper.pkg`.
  The keep-alive path is the exception and stays raw: `c3.d.d()` calls `i0(this.c)` with the
  bind package, so AutoTTS compares normalised against raw there too.

### `m0(Boolean)` — stopAllTts
`M.clear()` first. With `false`: if the current wrapper is at state 2, has `g` set and is
speaking, `speak("", QUEUE_FLUSH, null, null)`. With `true` (what `onStop` passes): every
wrapper at state 2 with `g` set and speaking gets `f0.m()` — the async `stop()`. Then
`synchronized (o) { p.set(true); o.notifyAll(); }` and, in a **second** synchronized block,
`q.set(true); o.notifyAll();`.

### `n0(int)`
Only `synchronized (o) { p.set(true); o.notifyAll(); }` — the unlock half of `L` without the
callback bookkeeping.

### `o0(SynthesisCallback)` and `g0(SynthesisCallback)` — keep-alive
`o0` writes the 32-byte `d0` buffer in `getMaxBufferSize()` slices while `!p.get()`, treating
`audioAvailable(...) == 0` as success and anything else as "stop, return false". `g0` is
`cb.start(16000, 2, 1)` then `while (!p.get()) { if (!o0(cb)) return; synchronized (o) {
o.wait(100) } }`, returning on `InterruptedException`. EasyVoice's keep-alive block is this
loop inline, with the same 32 zero bytes, the same 100 ms wait and the same interrupt exit.

### `L(cb, n)` — endSynthesis
`synchronized (o) { p.set(true); o.notifyAll(); }`, then `done()` only when the callback has
started and has not finished.

---

## 29. `onSynthesizeText`'s head and the four inner classes

### The head, in order
1. `if (V && !W()) l0(); else if (!V && W()) stopForeground(1);` — the foreground state is
   reconciled on **every** request, not just at create.
2. `synchronized (o) { p.set(false); o.notifyAll(); }` and then, in a **second** block,
   `q.set(false); o.notifyAll();`.
3. `cs = req.getCharSequenceText()`, `text = cs.toString()`, `lang = req.getLanguage()`,
   `l = getSpeechRate()`, `m = getPitch()`, `j = getParams()`,
   `k = j.getFloat("volume")` with `k = 1.0f` when it is 0, `c0 = j.getString("utteranceId")`.
4. `text = text.trim()`; empty → `m0(FALSE)` then `K(cb, 1)` and return. Note it is
   `m0(FALSE)`, the single-engine `speak("")` path, not the stop-everything one.
5. The licence counter (`a0`, `H()` every 500) — not ported, §25.
6. **Test-utterance parse.** `text.length() >= 9 && text.substring(0, 9).equals("[AutoTTS:")`
   and `text.split("]").length == 2` → `text = parts[1]`, and
   `parts[0].substring(1).split(":")` of length 4 gives engine, locale and variant, with
   `lang = m.f(f0(localeStr))`. That sets the flag which makes the mode gate fall through.
7. **The mode gate:** the span/auto path is taken when
   `((lang.equals("zxx") && O != 1) || O == 2 || O == 3) && !isTest`.

### Per-mode bodies
- **auto / google:** `M = z.g(cs)`; per span, `"unknown"` → `clsCLD2.b(text, g0, b0, h)` and
  truncate to 2 chars; `iso3 = m.h.get(code)` or `F`; `M(iso3)` empty or `"Disable"` → `F`;
  `span.e(iso3)`. Then `onLoadLanguage(M[0].b(), "", "")`, `-2` → `K(cb, 2)`.
- **dual (O == 1):** `M = y.g(text, H, I, g0, b0)`; type 1 → `onLoadLanguage("eng","","")`,
  `-2`/`-1` → `K(cb, 4)`; type 2 → `onLoadLanguage(G,"","")`, `-2`/`-1` → `K(cb, 5)`.
- **mixed (O == 4):** per LocaleSpan, `"unknown"` or empty → `y.g(...)` appended to `M`;
  otherwise `M(b)` empty or `"Disable"` → `F`, then the span itself is appended.
- Speak: `O(lang)` speed, `R(lang)` volume, `N(lang)` pitch;
  `rate = l/100 * speed/100`, `pitch = m/100 * pitch/100`, `vol = k * volume/100` written to
  the bundle only when non-zero, after removing language/country/voiceName/variant/pitch/rate/
  utteranceId and, under `S`, streamType and audioAttributes.

### Inner classes
`b` is the LicenseCheckerCallback and `c`/`d` are the init and restore `OnInitListener`s,
already ported. **`e`, the `UtteranceProgressListener`:**
- `onStart` — `if (!cb.hasStarted()) cb.start(16000, 2, 1)`. Nothing else.
- `onDone` — `if (M.size() > 1) handler.postDelayed(next, 50L); else L(cb, 7);`
- `onError(id)` → `L(cb, 8)`; `onError(id, code)` → `L(cb, 9)`
- `onStop(id, interrupted)` — **empty**

### Two gaps fixed
- **`onDone` continues through `postDelayed(…, 50L)`**, not `post`. Ours posted with no delay,
  so chunk N+1 was prepared a frame earlier than AutoTTS prepares it.
- **`onStart` carried a `synthStartTime` stamp** that nothing read — invented state, removed.
  AutoTTS's `onStart` is the `hasStarted` check and nothing more.
