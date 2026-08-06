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

> **Correction (re-read 2026-07-29).** The keep-as-is test above is written as
> `m.o(engineFor(lang))`. The real test is **`M(lang)`** — empty or `"Disable"` → fall back
> to `F`. `m.o` is a different predicate (membership in `m.c` plus the not-disabled flag, no
> engine test at all) and is used by `clsCLD2.b`, not here. See §30 and §34. The code was
> always right; this line was not.

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
> **Correction (re-read 2026-07-29).** The loop above is wrong: it sends **every** span
> through `clsCLD2.c`. `onSynthesizeText`'s O == 5 branch keeps a span **as-is** when its
> LocaleSpan language is known *and* `M(lang)` is a real engine, and only splits the others.
> The per-part resolution is also **two stages**, both falling back on `part.b ? K : L` —
> once when `m.h.get` misses, and again when `M(iso3)` comes back empty or `"Disable"`.
> §34 has the branch written out. The code matches §34, not the sketch above.

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
- Advanced tab: "Keep-alive Mode" section + "Keep alive" checkbox.

> **Correction (re-read 2026-07-29).** "the post-utterance wait is skipped" is not what
> happens. With `U` set the tail of `onSynthesizeText` runs **`g0(cb)`**, which starts the
> callback and then loops `while (!p.get()) { o0(cb); synchronized (o) { o.wait(100) } }`,
> writing the 32 zero bytes of `d0` in `getMaxBufferSize()` slices every 100 ms. With `U`
> clear it is the plain `while (!p && !q) o.wait()`. So keep-alive **feeds silence**; it does
> not skip a wait. §28 has `g0` and `o0` in full, and the port matches them.

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

> **Correction (re-read 2026-07-30).** Item 3 was tagged fixed but **was not in the code**.
> The re-read on 2026-07-30 found `chunkHandler.post` with no delay still there and fixed it
> in `47e6a71`. Items 1 and 2 were checked in the same pass and *are* applied: the mode gate
> carries the `zxx` rule for every non-Dual mode, and `if (isStopped || isFlushed) return`
> sits above `listenerSet`, `setAudioAttributes` and the `postDelayed`, with the bundle order
> strip-then-volume. A "fixed" tag in this file is not evidence; the code is.

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
- span-loop guard `q.get()` ≡ EasyVoice's **isFlushed** during chunk build.
  (Written as `isStopped` here originally. `q` is set only by `m0`, i.e. only by `onStop`,
  and `isFlushed` is the field that mirrors it — `isStopped` is `p`. The three span loops
  guard on `isFlushed`, which is right; see §34.)

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

### Inventory of every method CFR cannot be trusted on
Found by grepping the whole decompile for CFR's own markers, so this list is complete for
`c3.*` and `com.vnspeak.autotts.*`:

**Hard failures** (`throw new IllegalStateException("Decompilation failed")`):
`c3.o.h`, `c3.j.F2`, `AutoTtsService$e$a.run` — all three transcribed from baksmali.

**Soft failures** (code emitted, but with `** GOTO` / `** continue` / `// 2 sources` /
"Removed back jump from a try to a catch block"):
`c3.m.b`, `c3.m.h`, `clsCLD2.b`, `AutoTtsService.T`, `AutoTtsService.Y`,
`AutoTtsService.e0`, `AutoTtsService.onSynthesizeText`.

Status: **all of them are done.** `clsCLD2.b`, `m.h` and `m.n` were already exact; `T`, `Y`,
`e0`, `m.b` and `onSynthesizeText` were corrected.

**`onSynthesizeText`, what the walk changed.** The banner is the first statement, ahead of
the foreground handling. The request is read in the dex's order. The empty-text branch is
`log("Speak text is empty"); m0(FALSE); K(cb, 1)` and m0 emits five log lines we had none
of. The five mode branches do NOT share one pair of lines: Auto/Google and the fall-through
log `"load <r>"`, Dual/Mixed/Multilingual do not; Auto/Google, Dual and Mixed write
`"Languge is not supported"` (typo), Multilingual and the fall-through write
`"Language is not supported"`. Every branch logs its banner BEFORE its `synchronized(M)`
block. The none path speaks the TRIMMED text. `O == 5` alone reacts to an empty `M`
("lstLanString is empty!", K, return). In the tail, the wait loop's InterruptedException
LEAVES the loop, and the two `mTTSIndex …` failures do a bare `K(cb, 10); return` without
touching the stop flag. `K` never reads its int argument, so 1..13 carry nothing.

**`clsCLD2.b`, confirmed line by line.** Null and empty answer "UNKNOWN"; a single char
answers "UNKNOWN" when `X` is set. Then non-overlapping 64-char windows, stepping by a full
64 even at the tail. Per window: `nativeGetLanguage`; a null or "UNKNOWN" result skips; `W`
set returns the raw code without consulting `m.o`; otherwise `m.o(det)` returns it, and
failing that `a.e(clsCLD2.a(WINDOW), m.f)` supplies the primary then each fallback, each
gated on `m.o`. The whole per-window body is one try whose catch just advances to the next
window. After the loop `W` answers "UNKNOWN"; otherwise the same script lookup runs once
more on the FIRST codepoint of the WHOLE string, and `clsCLD2.a` skips whitespace, ASCII
digits and `clsCLD2.e`'s four punctuation ranges (33–47, 58–64, 91–96, 123–126).

**`c3.m.b`'s asymmetry.** It compares `m.f/m.e` of `m.t(stored)` against `m.f/m.e` of the
RAW incoming locale. `m.t` rebuilds from the ISO3 pair, and `Locale.getISO3Country()` only
resolves two-letter regions — a three-letter one throws and `m.e` answers `""`. Verified by
running it: `en_US` gives stored `("eng","")` vs incoming `("eng","USA")` → no match, while
a country-less locale gives `("eng","")` on both sides → match. So whether scanned voices
merge depends entirely on what `Voice.getLocale()` hands back on the device. Ported as the
comparison itself rather than as a key, so either case lands where AutoTTS lands.

### Which methods actually hold a monitor
Checked against the dex, not guessed. In `c3.m` only **`a`, `b` and `h`** are
`synchronized (m.class)`; `d/g/i/j/k/l/m/n/p/q/r/u/v/w/x/y/z/A/C/D` are plain statics. In
`AutoTtsService` only **`T` (initAllTTS), `Y` (engine list), `a0` (loadLanguages),
`e0` (loadVoices), `i0` (restoreTts) and `onLoadLanguage`** are `synchronized (this)` —
`M/N/O/P/Q/R` are not. In `c3.o` the dex marks **`f`, `a` and `h`** `declared-synchronized`;
`b(ctx)` is not. `onLoadLanguage` holds the monitor across **both** of its log lines
(its own, then `Z`'s "loadLanguage …"), because `Z` is called from inside it.

### `c3.j.F2(ctx, xml)` (import) — third CFR-failing method
CFR gives up with `ConfusedCFRException: Back jump on a try block`, so it was transcribed
from `/tmp/dc/smali/c3/j.smali`. The whole body is inside **one** try whose handler catches
`Throwable`, calls `printStackTrace()` and returns `false`; the success path returns `true`
(`G2` discards the value either way). Order: `getSharedPreferences("auto_tts_settings", 0)
.edit()` → `clear()` → `XmlPullParserFactory.newInstance().newPullParser()` →
`setInput(new StringReader(xml))`. The loop only looks at START_TAG, requires the `name`
attribute to be non-null, and switches on the tag name over exactly five cases —
`float`/`boolean`/`long`/`int` read the `value` attribute, `string` uses `nextText()`.
There is **no** `set`/`putStringSet` case, **no** per-value null guard (a missing `value`
NPEs straight into the outer catch) and **no** inner try around `nextText()`. Ends with
`commit()`.

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

---

## 30. The CLD2 chain, every part of it

### `clsCLD2` — five methods and one inner class, all of them
- **`a(String)`** — the first codepoint that is not whitespace, not an ASCII digit and not
  `e(char)` punctuation, else `-1`. It walks **`charAt`** (UTF-16 units) but returns
  `codePointAt(i)`, so a surrogate pair answers with the whole codepoint and a lone surrogate
  answers with itself.
- **`e(char)`** — the four ASCII punctuation runs: `!`–`/`, `:`–`@`, `[`–`` ` ``, `{`–`~`.
- **`b(text, g0, b0, ctx)`** — `null`/empty → `"UNKNOWN"`; `length == 1 && X` → `"UNKNOWN"`;
  then 64-**char** windows: `nativeGetLanguage` per window, a non-`"UNKNOWN"` answer returns
  immediately when `W`, else when `m.o(answer)`; failing that, `a(window)` → `a.e(cp, m.f)` →
  the family's primary if `m.o`, else the first fallback that passes `m.o`. An exception in a
  window just advances to the next. After the loop, `W` → `"UNKNOWN"`, otherwise the same
  script-fallback over the **whole** text.
- **`c(text, g0, b0, obj)`** — `null`/empty → one `a("un", false, "")`; `X && length == 1` →
  one `a("un", d(text), text)`; else `nativeGetLanguages` read three at a time
  (`lang`, `"1"`-means-latin, `text`), and a null array yields an **empty** list, not the
  `"un"` fallback.
- **`d(String)`** — `UnicodeScript.of(codePointAt(0))` ∈ {LATIN, COMMON, INHERITED}.
- **inner `clsCLD2.a`** — the immutable (lang, latin, text) triple plus a `toString` of the
  form `[xx/latin] text`. `MLChunk` is this.

### `com.vnspeak.autotts.a` — every member
Statics `a`, `b`–`k`, `l`–`v`, `w`; methods `a()`, `b(int)`, `c(int, Set)`, `d(int)`,
`e(int, Set)`, `f(a.a, Set)`, `g(Set, Set)`; inner `a.a` (primary, possible, script).
`e` is `set != null && !set.isEmpty() ? c(n, set) : d(n)`. **`f` and `g` are called only from
`c`** — `g` is a set-intersection test and `f` narrows a family to the enabled languages,
returning the family untouched when everything is enabled and the primary is in, `u` when the
intersection is empty, and otherwise a new family whose primary is the old one if enabled or
else the first survivor. Both are therefore covered by the `a.c` differential run:
**208 sets × 205,232 codepoints = 42,688,256 comparisons, 0 mismatches**, with `a.b`/`a.d`
separately at 136 × 205,232 = 24,627,840, 0 mismatches.

### `m.o(lang)` — the documentation was wrong
```java
if (lang.length() != 3) { lang = h.get(lang); if (lang == null) return FALSE; }
for (e entry : c) if (entry.b.compareTo(lang) == 0) return !entry.i;
return FALSE;
```
It is **membership in `m.c` plus the not-disabled flag, and nothing else — there is no engine
test in it.** `CLAUDE.md` described it as "engine available and not disabled", which would
have led a later pass to add an engine check to the detection path.

The engine test is a different method, `AutoTtsService.M(lang)` — `""` when the language is
absent, `"Disable"` when `e.i`, else `e.f` — and the two are used in different places:

| where | test |
|---|---|
| `clsCLD2.b` accepting a detection | `m.o` |
| `a.e(cp, m.f)`'s primary and fallbacks | `m.o` |
| auto path per span, `onSynthesizeText` | `M(iso3)` empty or `"Disable"` → `F` |
| mixed path per span | `M(b)` empty or `"Disable"` → `F` |
| `resolveMixChunk` / `resolveMultilingualChunk` | `M(lang)` |

Checked on our side: `detectOk` is `scannedLangsIso3.filter { !isLanguageDisabled(it) }` —
`m.c` membership plus the flag, exactly `m.o` — and `enabledOk` is `m.f`. Both go to the
native detector, so the CLD2 path never consults an engine. `isLangRoutableRaw` is the
`M()`-shaped test and is what the auto and mixed span loops call. The three call sites line
up with AutoTTS's three. No change needed in the code; the note was the only thing wrong.

---

## 31. The entry point — Text-to-speech output → the engine's settings screen

The path is: Settings → Accessibility → Text-to-speech output → the gear next to the engine.
Android resolves that through the service's `<meta-data android:name="android.speech.tts">`
→ `res/xml/tts_engine.xml` → `android:settingsActivity`. The same screen also fires
`CHECK_TTS_DATA` and `GET_SAMPLE_TEXT` at the two no-display activities, and calls
`onGetLanguage`, `onIsLanguageAvailable`, `onGetVoices`, `onLoadLanguage` and `onLoadVoice`
on the service for its language list and its "listen to an example" button.

`tts_engine.xml` is a single self-closing `<tts-engine android:settingsActivity="…"/>` with
the fully-qualified activity name — matched.

### Manifest, attribute by attribute
Six differences, all now closed:

| | AutoTTS | was |
|---|---|---|
| service | `accessibilityEventTypes="typeWindowsChanged"`, `accessibilityFlags="flagRetrieveInteractiveWindows"`, `canRetrieveWindowContent="true"` | all three absent |
| `CheckVoiceData` | one filter, `CHECK_TTS_DATA` | had a second `INSTALL_TTS_DATA` filter of its own invention |
| settings activity | `android:theme="@style/AppTheme.NoActionBar"` on the **activity** | theme sat on `<application>` |
| settings activity | no `windowSoftInputMode` | `adjustResize` |
| `<application>` | `extractNativeLibs="false"` | absent, and it ships a `.so` |
| `<application>` | no `supportsRtl` | `supportsRtl="true"` |

Element order now matches too: service, `CheckVoiceData`, `GetSampleText`, the settings
activity, provider.

`SYSTEM_ALERT_WINDOW` is declared by AutoTTS and is now declared here. **Grepping the whole
decompile for `TYPE_APPLICATION_OVERLAY`, `canDrawOverlays`, `ACTION_MANAGE_OVERLAY_PERMISSION`
and `WindowManager.LayoutParams` returns nothing — AutoTTS never uses it.** It is mirrored
because the rule is to mirror, but it is a user-visible "Display over other apps" grant on an
app that does not draw overlays, so it is flagged here rather than buried.

Not carried over, consistent with §25: `com.android.vending.CHECK_LICENSE`, the
`com.pairip.application.Application` wrapper and `@drawable/ic_launcher` (no such asset here —
the app bar reads the real icon through `applicationInfo.loadIcon`). The
`DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION` pair and `appComponentFactory` are generated by
AGP/androidx, not hand-written.

---

## 32. Running as the system TTS engine — the runtime path

When TalkBack or any client binds `android.intent.action.TTS_SERVICE`, the framework drives
`TextToSpeechService` in this order: `onCreate` → `onGetLanguage` →
`onIsLanguageAvailable` → `onLoadLanguage` (on `setLanguage`) / `onLoadVoice` (on `setVoice`)
→ `onIsValidVoiceName` / `onGetVoices` / `onGetDefaultVoiceNameFor` as the client asks →
`onSynthesizeText` per utterance → `onStop` → `onDestroy`. Every one of those is covered in
§27, §28, §29 and here.

### `onCreate`, exactly as CFR renders it
```java
block4: { block3: { block2: {
    m.a = o.f(this); log("onCreate"); super.onCreate(); this.h = this;
    try { if (!V) break block2; this.l0(); }
    catch (Exception e) { break block3; }
} this.h0(); break block4; }
    Log.e("AutoTTS", e.getMessage());      // l0 threw: h0 is SKIPPED
}
b0 = …licence…; m.c(); Y(); e0(); a0(); X(); m.p(ctx); m.r(ctx); U(); n = true; T();
```
Two orderings fall out of that shape, and both are load-bearing:
- **`h0()` — the audio-focus request — sits on the fall-through out of the try.** If `l0()`
  throws, it is skipped and only the message is logged; `this.r` stays null, which `onDestroy`
  survives purely because its cleanup is inside a try. EasyVoice was calling
  `requestAudioFocusIfNeeded()` unconditionally after the catch. Fixed, and `audioMgr` is a
  nullable field now rather than `lateinit`, so it can stay unset the way `this.r` does.
- **`V` is still the class default `false` when `l0()` is tested**, because `m.r(ctx)` — which
  loads `show_notification` — runs six statements later. So on a cold process the notification
  is never started here; it appears on the first `onSynthesizeText`, whose head reconciles
  `if (V && !W()) l0(); else if (!V && W()) stopForeground(1);`. Already matched, and now
  commented in place.
- `Y()` before `a0()` is the third of these, covered in §18/§24.

### The rest of the runtime path
`onGetLanguage` → `m.f(Locale.getDefault())`. `onIsLanguageAvailable` /
`onIsValidVoiceName` / `onGetVoices` → `m.j(null, true)`. `onLoadLanguage` → `Z` (§27).
`onLoadVoice` → stores `e0` then forwards to `onLoadLanguage` (§19). `onSynthesizeText` and
the `UtteranceProgressListener` (§29). `onStop` → `m0(TRUE)` (§28). `onStartCommand` returns
`START_STICKY`; `onTaskRemoved` just calls through to super. `onDestroy` (§26).

---

## 33. Every Modes-tab dropdown and checkbox, against `O1` branch by branch

| id | field | `O1` body |
|---|---|---|
| `auto_mode_language` r0 | `F` | `F = m.c.get(r0.getSelectedItemPosition()).b` |
| `dual_mode_language` s0 | `G` | `G = m.c.get(s0.getSelectedItemPosition()).b` |
| `mixed_mode_latin_language` x0 | `K` | `K = m.c.get(x0.getSelectedItemPosition()).b` |
| `mixed_mode_non_latin_language` y0 | `L` | `L = m.c.get(y0.getSelectedItemPosition()).b` |
| `multilingual_mode_latin_language` z0 | **`K`** | `K = m.c.get(z0.getSelectedItemPosition()).b` |
| `multilingual_mode_non_latin_language` A0 | **`L`** | `L = m.c.get(A0.getSelectedItemPosition()).b` |
| `number_mode_language` t0 | `H` | `H = t0.getSelectedItemPosition(); v0.setSelection(H)` |
| `number_mode_language_mixed` v0 | `H` | `H = v0.getSelectedItemPosition(); t0.setSelection(H)` |
| `punc_mode_language` u0 | `I` | `I = u0.getSelectedItemPosition(); w0.setSelection(I)` |
| `punc_mode_language_mixed` w0 | `I` | `I = w0.getSelectedItemPosition(); u0.setSelection(I)` |

Multilingual's pair writes the **same `K`/`L`** the mixed pair writes — there is no separate
multilingual language setting. Number and punctuation are one `H` and one `I` each, mirrored
across the dual and mixed copies of the spinner. None of these branches persists; `m.w(ctx)`
writes `F/G/K/L/H/I/J` and runs from `m.u(ctx)` on pause.

### The index the language spinners write
Every language branch reads its own `getSelectedItemPosition()` and indexes **`m.c`**, the
unfiltered list, while the adapter is `m.m(pkg)`. **`m.m` filters only on
`e.j.contains(pkg)` — never on the disabled flag** — so with `pkg == null`, which is every
mode but Google, the adapter is `m.c` one for one and the index is right. In Google mode
`m.m("com.google.android.tts")` is shorter, and the position then names a *different* entry
of `m.c`. That is AutoTTS's behaviour; we were writing the filtered list's entry, which is
"correct" and therefore wrong. Now `allCodes[position]`.

(The Voices tab is the other way round — `V2` builds `m.c` with `m.h(ctx, true)` and its
adapter with `m.j(...)`, both of which drop disabled entries, so those line up.)

### The three locale-span checkboxes
`R0`, `S0`, `T0` are three views over the single flag `Q`. Each sets `Q = bl` and then calls
`setChecked(Q)` on the other two **unconditionally** — `CompoundButton.setChecked` no-ops when
the value is unchanged, so it never recurses. Ours had an `isChecked != checked` guard in
front; removed, so the call shape matches. `U2` sets all three from `Q` *before* attaching any
listener, which is why the initial state never fires the mirroring — our construction order
does the same.

### Dedicated engines
`V2`: `setEnabled(O != 0 && O != 3)`, then `setChecked(R)`, then the listener `R = bl`.
Matched.

---

## 34. Auto (O==2/3) and Multilingual (O==5) — settings through to segmentation

### What the dropdowns write
Auto shows section `E0`: `auto_mode_language` → `F`, and the `localespans` checkbox → `Q`.
Multilingual shows `H0`: `multilingual_mode_latin_language` → **`K`** and
`multilingual_mode_non_latin_language` → **`L`** — the very fields the Mixed pair writes —
plus `localespans_multilingual` → `Q`. Multilingual has no number and no punctuation spinner,
so `H` and `I` are untouched by it. All of this is §33; none of the branches persists, `m.w`
does that from `m.u` on pause.

### `onSynthesizeText`, O == 2 / O == 3
```java
M.clear(); M.addAll(z.g(cs));
for (i = 0; i < M.size() && !q.get(); i++) {
    b = M.get(i).b();
    if (b.equalsIgnoreCase("unknown")) { b = clsCLD2.b(M.get(i).c(), g0, b0, h);
                                         if (b.length() > 2) b = b.substring(0, 2); }
    iso3 = m.h.get(b);                       if (iso3 == null)                 iso3 = F;
    if (M(iso3).isEmpty() || M(iso3).equals("Disable"))                        iso3 = F;
    M.get(i).e(iso3);
}
if (!M.isEmpty()) { text = M.get(0).c(); if (onLoadLanguage(M.get(0).b(), "", "") == -2) K(cb, 2); }
```
Ours: `splitByLocaleSpans` → per span, `"unknown"` → `detectLanguage` truncated to two chars →
`isKnownIso2(detected) && (forceGoogle || isLangRoutable(detected)) ? detected : autoModeC`.
`isKnownIso2` is `m.h.get(b) != null` (both read `Locale.getISOLanguages()`),
`isLangRoutable` is the `M()` test, `forceGoogle` covers `M` answering Google unconditionally
on O == 3, and the iso2 → iso3 step happens later in `speakChunk`'s `toIso3`. Matches.

### `onSynthesizeText`, O == 5 — the branch that had not been written down
```java
M.clear(); spans = z.g(cs);
for (i = 0; i < spans.size() && !q.get(); i++) {
    b = spans.get(i).b();
    if (b.equalsIgnoreCase("unknown") || b.isEmpty()
        || (eng = M(b)).isEmpty() || eng.equals("Disable")) {
        parts = clsCLD2.c(spans.get(i).c(), g0, b0, h);      // the native per-language split
        for (k = 0; k < parts.size(); k++) {
            iso3 = m.h.get(parts.get(k).a);
            if (iso3 == null)                       iso3 = parts.get(k).b ? K : L;
            e2 = M(iso3);
            if (e2.isEmpty() || e2.equals("Disable")) iso3 = parts.get(k).b ? K : L;
            M.add(new z(parts.get(k).c, iso3));
        }
    } else {
        M.add(spans.get(i));
    }
}
if (M.isEmpty()) { K(cb, 7); return; }
text = M.get(0).c(); onLoadLanguage(M.get(0).b(), "", "");
```
Two things to note. The keep-as-is test is a **single `||` chain**, so a span that *has* a
locale but whose language has no engine is still re-detected, not kept. And the per-part
resolution is **two stages**, both falling back on `parts.b ? K : L` — the latin flag decides
which of the mixed pair is used, and the second stage re-checks the engine after the first
mapping succeeded.

Ours is this span for span: `splitByLocaleSpans`, then
`!lang.equals("unknown") && lang.isNotEmpty() && isLangRoutableRaw(lang)` → keep, else
`multilingualChunks(lc.text)` (which is `clsCLD2.c`, `X`-gate and all, §30) with
`resolveMultilingualChunk` doing exactly the two stages. Matches.

### The loop guard
Both loops run `while (… && !q.get())`. `q` is set only by `m0`, i.e. only by `onStop`; `p` is
the general end-of-synthesis flag set by `L`, `n0` and `m0`. On our side that is `isFlushed`
and `isStopped`, both cleared in two separate synchronized blocks at the top of
`onSynthesizeText` and both set by the `m0` port and by the empty-text path — which calls
`m0(FALSE)`, and `m0` sets both regardless of its argument. The three span loops guard on
`isFlushed`. Matches.

---

## 35. `y.g` read whole — the tail nobody had opened, and a dual-mode bug

Earlier passes leaned on the differential run over `y.g` (~51,000 strings) rather than
reading it. Lines 120–227 — the merge passes and the type-fixing — had never been opened.
They are:

```java
if (n5 == -1) return new ArrayList();     // g0  — licence gate
if (n6 == -1) return new ArrayList();     // b0  — licence gate
text = text.trim(); if (text.equals("")) return new ArrayList();
s = y.f(text.replaceAll("\\s+", " "));    // collapse whitespace, then strip bidi controls
dev = m.f(Locale.getDefault());
neutralType = ((O != 1) ? (O == 4 && dev.equals(L)) : dev.equals(G)) ? 2 : 1;
// pattern d over s: gaps are type 2, matches take y.c(seg)
// merge pass 1: adjacent equal types, ABSORBING type 0
// size 0 -> return; size 1 -> a lone 3 takes H or neutralType, a lone 4 takes I or neutralType
// size > 1 -> element 0's 3/4 takes H/I, or y.a(list) (first type that is not 3 or 4), or neutralType
// from index 1 on -> each 3 takes H or y.b(list, i), each 4 takes I or y.b(list, i)
// merge pass 2: adjacent equal types, NO type-0 absorption
```

The two merge passes differing on type-0 absorption is the load-bearing detail, and the C++
already has it that way — pass one carries `|| segs[runEnd].type == 0`, pass two does not.
The whitespace collapse (`\s` = `[ \t\n\x0B\f\r]`, matching the C++'s `isUniWs`), the bidi
strip set, and their order after `trim()` all line up as well.

### The bug
`neutralType` is `dev.equals(G)` for **O == 1** and `dev.equals(L)` for **O == 4**. `G` is
`dual_mode_language`; `L` is `mixed_mode_non_latin_language`. **Our dual arm was comparing
against `nonLatFall`, i.e. `L`** — the mixed setting — so in Dual mode the neutral type came
out wrong whenever the device language matched one setting but not the other. It decides what
a lone punctuation- or number-only chunk becomes, and what `y.b` hands back to later chunks.
Now `prefs.toIso3(dualLang)`. The mix arm was already right.

`neutralDefault` is left alone: it is a C++ parameter name with no `y.g` counterpart — it only
supplies a language when a resolved type ends up neither 1 nor 2 — so changing it would be
guesswork, and dual's type→language mapping is done by `onSynthesizeText`'s own branch
(`type 1 → "eng"`, `type 2 → G`) regardless.

### Not ported, deliberately
The two lines at the top of `y.g` — `if (g0 == -1) return empty; if (b0 == -1) return empty;`
— are the licence gate. `g0` defaults to `-1` and `b0` is `g0.b(ctx)`'s verdict, so an
unlicensed build gets **no chunks at all** in dual and mixed mode. Same family as §25;
recorded here so a later pass reading `y.g` does not try to port them.

---

## 36. Honest ledger — what is deliberately not AutoTTS, and why

Everything below is a place where the code does **not** mirror AutoTTS byte for byte. Nothing
else is outstanding; if it is not on this list, it is a literal port.

### Closed in this pass — they were my judgement, and they should not have been
| what | AutoTTS | was | now |
|---|---|---|---|
| `c3.z.g` span locale | `localeSpan.getLocale().getLanguage()`, no guard | `ls.locale?.language ?: "UNKNOWN"` | `ls.locale.language` — throws where AutoTTS throws |
| `e.onStop(id, interrupted)` | **logs `"onStop <id>"`** and nothing else | logged the id | ~~empty body~~ — this row was WRONG; the log is back |
| `c3.m.s` | `Integer.parseInt(getString(key, "1000"))`, no catch | caught and fell back to 1000 | no catch |

The same ruling as the `Y2` variant-array overflow: a defect in AutoTTS is still AutoTTS.

### Standing carve-outs, agreed with the user
- **Licence and integrity, everywhere it reaches.** `c3.g0` (signing-certificate hash),
  `AutoTtsService.U`/`H`/`j0` and the inner `b` (Google LVL with AutoTTS's own public key),
  `NewSettingsActivity.C0`'s `"Auto TTS (%s)"` title decoration, the `a0` counter and its
  `H()` every 500 in `onSynthesizeText`, the `g0.c(...) % 100 == 1` text-replacement easter
  egg, the `g0`/`b0` arguments threaded into `clsCLD2.b`/`clsCLD2.c` and the native calls,
  **`y.g`'s two opening lines** (`g0 == -1` or `b0 == -1` → empty list, so an unlicensed build
  gets no chunks in dual and mixed), `com.android.vending.CHECK_LICENSE`, and the
  `com.pairip.application.Application` wrapper.
- **The Licenses copyright paragraph.** AutoTTS opens with its own rights holder; that slot
  carries this app's name. Copying it would be false attribution.
- **`@drawable/ic_launcher`.** No such asset here, so the app bar reads the real icon through
  `applicationInfo.loadIcon(packageManager)` rather than inventing artwork.
- **The CLD3 row** in the Advanced tab — EasyVoice-only, kept by explicit instruction, and to
  be brought up to CLD2's level after CLD2 is declared finished.

### Closed on 2026-07-30, after the user asked for every remaining divergence
| what | AutoTTS | was | now |
|---|---|---|---|
| logger tag | `c3.o.c/d/e(tag, msg)` — per call | one fixed `EASYVOICE-NATIVE` | per call; `EasyVoice` where AutoTTS passes `AutoTTS`, `TTS` passed through |
| every service log line | 141 exact strings | `[SERVICE]`/`[ENGINE]`/`[LANG]`/`[CHUNK-n]` of our own | AutoTTS's text, in AutoTTS's places; the ones AutoTTS does not log are gone |
| `c3.o(Context)` | writes nothing | wrote a device banner | writes nothing |
| `f0.m()` / `f0.l()` | `Log.w("Stop failed for …", e)` | swallowed | ported, incl. reading `b` at run time |
| `h0()` | no guard, no catch, takes the AudioManager itself | had both guards | literal |
| `W()` / `J()` / `S()` | no version gate, no catch, no catch | all three guarded | literal |
| `onDestroy` | stopForeground+abandon, then shutdowns, then unbinds | unbinds inside the first try, before the shutdowns | literal order |
| `M.clear()` | unsynchronized | `synchronized(chunkQueue)` | unsynchronized |
| `AndroidManifest` | `android:installLocation="auto"` | absent | present |
| `getReadingMode` | `getInt("auto_mode", 3)` + the Google fallback | plus a `reading_mode` string migration of our own | literal |
| `neutralDefault` | the mixed branch's `t != 1 ? (t != 2 ? var10_15 : L) : K` — `var10_15` is the request's own language | `if (neutralType == 2) nonLatFall else latFall`, guessed | the request language |
| `LangEntry.j` | `c3.e.j` `LinkedHashSet` of providing engines | omitted | present, and `m.j`/`k`/`l`/`m` filter on it |

### Still standing, and why they cannot be closed
- **Licence and integrity.** Porting `c3.g0` means checking AutoTTS's *signing certificate*
  and shipping AutoTTS's own Google LVL public key. This app is signed differently, so the
  check would fail — and `y.g`'s first two lines return an empty chunk list when it does, i.e.
  dual and mixed would stop speaking altogether. It is not parity, it is a self-disabling
  build, and the key is not ours to ship. `com.pairip.application.Application` is Play's
  anti-tamper wrapper and is applied by the store, not declared by an app.
- **`NewSettingsActivity.C0`'s `"Auto TTS (%s)"` title and the Licenses copyright line.**
  Both name another company as the rights holder.
- **`@drawable/ic_launcher`.** The artwork is AutoTTS's. The manifest attribute is absent
  rather than pointing at a copy of their asset.
- **The CLD3 row** in the Advanced tab — EasyVoice-only, kept by explicit instruction.

### Accepted, with the user's agreement
- **`SYSTEM_ALERT_WINDOW`** is declared because AutoTTS declares it, even though grepping the
  whole decompile for `TYPE_APPLICATION_OVERLAY`, `canDrawOverlays`,
  `ACTION_MANAGE_OVERLAY_PERMISSION` and `WindowManager.LayoutParams` finds nothing.
- **`setReadingMode`'s `else` branch** is unreachable — Kotlin requires a `when` expression
  over `String` to be exhaustive. AutoTTS switches on radio-button ids and needs no default.

---

## 37. CLD3 brought level with CLD2

The Advanced-tab "Use CLD3 (neural language detection)" row is EasyVoice-only — AutoTTS has
no such switch. The rule for it is therefore not "match AutoTTS" but "wherever CLD2 makes a
detection, the switch must be able to put CLD3 there instead". CLD3 itself is real: the
workflow clones `google/cld3`, runs `protoc` over its `.proto` files, and CMake compiles the
whole `${CLD3_DIR}` source list plus `script_span` into the single `easyvoice_core` `.so`.

### Where CLD3 already reached
`cld2WindowDetectC(u8, useCld3)` is the one detector entry point, and it has exactly two
callers — `detectLanguageFull` (the `clsCLD2.b` port, which serves auto, mix and multilingual
first-chunk detection through Kotlin's `detectLanguage`) and `buildAutoChunks`. Both pass the
flag through. `cld3DetectRaw` holds one lazily built `NNetLanguageIdentifier(0, 1000)` behind
a mutex, drops the region suffix off `zh-Hant`-style answers, runs the result through
`normalizeLangCode`, and then through `cld3ScriptConsistent`, which rejects an answer whose
latin-vs-non-latin side disagrees with the first real codepoint of the text.

### Where it did not
**`glsEmit`** — the multilingual span emitter inside `nativeGetLanguages`. It called
`CLD2::ExtDetectLanguageSummary` unconditionally, and the Kotlin `external fun` did not even
take the flag, so **Multilingual mode ignored the switch completely**. That is the whole gap.

`buildMixChunks` is not a gap: it segments by script and emits no language of its own, leaving
detection to Kotlin's `resolveMixChunk` → `detectLanguage` → `detectLanguageFull`, which
already honours the flag.

### The change
`nativeGetLanguages` now takes `useCld3`, and `glsEmit`'s detection branch honours it:

- **The scripts 7..25 fixed table is untouched.** Those 19 entries are *segmentation* — the
  native code jumps straight past detection for them — not a detector result, so no switch
  should reach them.
- Only the `else` branch, the one that actually detects for scripts 1..6, chooses between
  `cld3DetectRaw` and `ExtDetectLanguageSummary`.
- The 1024-byte cap with its UTF-8 back-off runs first for both, so the two detectors see the
  same slice.
- **A CLD3 decline is not quietly handed to CLD2.** It becomes `"un"`, exactly as
  `cld2WindowDetectC`'s CLD3 branch returns `"UNKNOWN"` rather than falling through. The
  Kotlin side then does what it does for any unknown code — `resolveMultilingualChunk` falls
  back on the latin flag to `K` or `L`.

### 2026-08-04: the last asymmetry, closed
CLD2's arm is four lines — call, `if (!reliable) return "UNKNOWN"`, `LanguageCode(lang3[0])`,
return. **No post-processing whatsoever.** CLD3's arm had three extra stages the CLD2 arm has
no counterpart for:

| stage | what it did | why it is gone |
|---|---|---|
| strip after `-` | `hi-Latn` -> `hi` | CLD2 returns `zh-Hant` unstripped and the CALLER truncates to two chars. `und` truncates to `un`, which is CLD2's own unknown code, so even that case lines up |
| `normalizeLangCode` | iso3 -> iso2 rewrite | CLD2's code is never rewritten |
| `cld3ScriptConsistent` | rejected an answer whose latin/non-latin side disagreed with the text's first real codepoint | CLD2 may answer whatever it is confident about; nothing second-guesses it |

Both helpers had no other caller and were deleted with the stages. What changes in practice:
CLD3's six script-tagged codes (`bg-Latn`, `el-Latn`, `hi-Latn`, `ja-Latn`, `ru-Latn`,
`zh-Latn`) now reach the callers intact, so romanised Hindi truncates to `hi` in the auto path
instead of being declined, and in the multilingual path it is not an iso2 code so it falls back
on the latin flag. CLD3 can also now answer with a latin language for non-latin text when it is
confident, which is a freedom CLD2 always had.

---

## 38. Why AutoTTS needs no restart — and the two places we still did

AutoTTS's settings screen and its TTS service are one process, and the screen writes the
**shared statics** the service reads: `AutoTtsService.O/F/G/K/L/H/I/J/Q/R/S/T/U/V/W/X` and the
`c3.m.c` list. Nothing round-trips through SharedPreferences to take effect; the next
`onSynthesizeText` already sees it.

### What was already live on our side
`onSynthesizeText` re-reads, per request: the foreground reconcile, `modeInt`, `dualLang`,
`localeSpansFlag`, `stripAudioAttrFlag`, `forceAccessibilityFlag`, `numberModeInt`,
`puncModeInt`, `emojiModeInt`, `dedicatedEnginesFlag`, `readingMode`, `latFall`, `nonLatFall`,
`scannedLangsIso3`, `enabledLangs`, `mixNumLang`, `mixPuncLang`. `isUseCld3`,
`isDisableAdvancedDetection`, `isQuickCharacterReading`, `isKeepAliveMode` and
`isLoggingEnabled` are read at each use. `setShowNotification` writes the service field
directly, which is the static-write pattern itself.

`engineList`, `voiceList` and `enginePool` are built once at create — but so are AutoTTS's
`P`, `Y` and `f` (`Y()`, `e0()`, `T()` all run only from `onCreate`), so a restart is required
on both sides equally. Not a gap.

### The two that were not live
Moving the service onto `LangStore` (§16, §27) made `c3.m.c` the service's source for the
engine, the locale, the variant and the disabled flag. Two UI paths were still writing only
SharedPreferences, so the service kept the stale in-memory value until something reloaded the
list — the same shape as the original "picked a voice for English, nothing applied" report.

- **Voices tab, voice pick.** `j.O1`'s voice branch writes `m.c[b1].f = m.e[n4].d.b` and
  `m.c[b1].g = m.e[n4].c.toString()` straight onto the shared list, and **skips the write for
  the Disabled row** (`if (m.e[n4].d == null) continue;`). Ours only did `putString`. Both
  writes are now mirrored, Disabled-row skip included, and `persistVoiceRows` — the `m.C`
  port — keeps the entry in step whenever index 0 changes.
- **Languages tab, enable/disable.** `T2`'s click handler does `m.c[j].i = !bl` *then*
  `m.z(ctx)`; select-all and clear-all do the same across the list, and `m.l`'s force-enable
  pass writes `e.i = false`. Ours called `prefs.setLanguageDisabled` alone. Every one of those
  now sets `.i` on the entry as well, in the same order AutoTTS uses: memory first, then
  persist.

The Voices tab's sliders, the variant spinner and the Test button were already going through
the entry (§16, §20, §23).

## 39. `m.o` vs `M(lang)` — read again, and the alias that came out of it

Re-read because the identifier audit found two of our functions doing the identical engine
test under two different names, which reads like two different tests.

`c3/m.java:491`, whole method:

```java
public static Boolean o(String object) {
    Object object2 = object;
    if (((String)object).length() != 3) {
        object2 = object = (String)h.get(object);
        if (object == null) return Boolean.FALSE;
    }
    for (int i3 = 0; i3 < (object = c).size(); ++i3) {
        if (((e)object.get((int)i3)).b.compareTo((String)object2) != 0) continue;
        return ((e)object.get((int)i3)).i ^ true;
    }
    return Boolean.FALSE;
}
```

Iso2 folds to iso3 through `m.h`, then the iso3 is looked up in `m.c` and the answer is
`!e.i` — **not disabled**. The engine field `e.f` is never touched. Every caller of `m.o`
in the whole decompile lives in `clsCLD2.java` (lines 59, 65, 69, 83, 89), i.e. the detection
path and nothing else. On our side that is precisely the `detectOk` array
(`LangStore.languages.filter { !it.disabled }.map { it.iso3 }`) and the `enabledOk` array
handed to `detectLanguageFull`. Correct as it stands, and it is not a Kotlin function.

`AutoTtsService.java:314`, `M(String)`: logs `getEngine4Language`, returns
`"com.google.android.tts"` when `O == 3`, otherwise walks `c3.m.c` under a monitor and
returns `e.f` for the matching entry, or `""`. It returns a **String**, never a boolean.
Callers — 583, 652, 665, 1907, 2040, 2091, 2166, 2178 — each write the usability test
inline at the call site:

```java
if ((var1_1 = this.M(var10_15)).isEmpty() || var1_1.equals("Disable")) { ... }        // 1907, 2178
if (var1_1.equalsIgnoreCase("unknown") || var1_1.isEmpty()
        || (var1_1 = this.M(var1_1)).isEmpty() || var1_1.equals("Disable")) break;    // 2166
```

So AutoTTS has exactly one method here, `M` = our `LangStore.engineFor(lang, modeInt)`, and
**no boolean helper at all**. We had two: `hasEngineForLang` (the real test) and
`hasUsableEngine`, a one-line delegate to it. The delegate is something AutoTTS does not
have, it had two call sites (the mix locale-span branch mirroring 2178, and the multilingual
locale-span branch mirroring 2166), and both now call `hasEngineForLang` directly. Nothing
else changed — same predicate, same short-circuit order, same `"Disable"` case sensitivity.

Recorded so this is not re-derived: `m.o` is the detect arrays, `M(lang)` is
`LangStore.engineFor`, and there is one helper over it, not two.

## 40. Every monitor in the app, swept — and the one `engineFor` was missing

Swept `MONITORENTER` across every class we mirror. `clsCLD2`, `a`, `NewSettingsActivity`,
`c3.m`, `c3.e`, `c3.o`, `c3.j`, `c3.w`, `c3.v`, `c3.z` have **none** — in `c3.m` the three
synchronized methods (`a`, `b`, `h`) are method-level `synchronized (m.class)`, which CFR
prints in the signature, not as a comment. Only `AutoTtsService` has synchronized *blocks*,
ten of them, in four methods:

* **`L`** (endSynthesis) — `synchronized (o) { p.set(true); o.notifyAll(); }` then the
  hasStarted / hasFinished / done checks outside. We mirror it.
* **`g0`** (keep-alive silence writer) — `synchronized (o) { o.wait(100L); }` with
  `InterruptedException` → return. We mirror it.
* **`onSynthesizeText`** — two `synchronized (o)` pairs at the head (`p.set(false)`,
  `q.set(false)`, each with `notifyAll`), the `synchronized (o) { while (!p && !q) o.wait(); }`
  in the tail, and **one `synchronized (AutoTtsService.M)` around each of the four mode
  branches** (Auto/Google 1866, Dual 1959, Mixed 2019, Multilingual 2146), each inside a
  `try { } catch (Exception)` that logs. See the note below.
* **`M(lang)`** — the one we did not have.

### `M(lang)`'s monitor, from smali

CFR is wrong here, so this came from `AutoTtsService.smali`: CFR drops the `monitor-exit`
on the not-found path and prints the `" res ''"` log *inside* the loop. The bytecode:

```
    if-ne v0, v1, :cond_0        # O == 3
    return-object "com.google.android.tts"     <- before the monitor
:cond_0
    sget-object v0, Lc3/m;->c:Ljava/util/List;
    monitor-enter v0
    :goto_0  ... loop ...
        " res1 Disable" logged, monitor-exit v0, return-object "Disable"
        " res "         logged, monitor-exit v0, return-object e.f
:cond_4
    monitor-exit v0                            <- released BEFORE the last log
    " res ''" logged
    return-object ""
```

So: the `getEngine4Language` log and the `O == 3` early return are outside the monitor, the
whole walk is inside it, the two hit paths log inside and release on the way out, and the
miss path releases first and logs after. `LangStore.engineFor` now has exactly that shape —
`synchronized(languages) { … }` around the loop only. Kotlin's `synchronized` is inline, so
a `return` from inside it compiles to the same monitor-exit-then-return.

The lock is the **list**, not the store. That matters: `m.h` (our `rebuildFromScan`) is
`synchronized (m.class)` — a *different* monitor — so in AutoTTS a rebuild does not exclude
a concurrent `M(lang)`. Ours mirrors that exactly (`@Synchronized` on the `LangStore` object
vs `synchronized(languages)` in `engineFor`). Do not "fix" it into one lock.

`N` (pitch), `O` (speed), `P` (voice locale), `Q` (variant) and `R` (volume) hold **no**
monitor — plain linear walks over `c3.m.c` returning 100 / "" — which is what
`pitchFor` / `speedFor` / `localeFor` / `variantFor` / `volumeFor` already do.

### Still open: the chunk-list monitor in `onSynthesizeText`

AutoTTS builds chunks **directly into the static list `AutoTtsService.M`** while holding
`synchronized (M)`, once per mode branch, and `m0` (stopAllTts) calls `M.clear()` at 1442
*without* the lock. We build into a local `chunks` list and publish it in one
`synchronized(chunkQueue) { clear(); addAll() }` at the end. Same result when nothing races;
different when a stop lands mid-build (AutoTTS loses the partial list, we do not). Not
changed here — it is a restructure of the four mode branches in the largest method in the
app, and CFR is already proven unreliable around these monitors, so it needs its own
smali-based pass.
