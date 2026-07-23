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
