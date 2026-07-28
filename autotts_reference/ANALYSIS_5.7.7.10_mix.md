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
