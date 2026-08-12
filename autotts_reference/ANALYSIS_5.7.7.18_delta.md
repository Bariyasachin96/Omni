# AutoTTS 5.7.7.10 → 5.7.7.18 delta (verified from the decompile, 2026-08-07)

Everything here was read out of `decompiled_java/` and `decompiled_res/`. Nothing is
inferred. Line numbers are 5.7.7.18 unless stated.

The store's "What's new" is **cumulative**. Verified already present in 5.7.7.10 and
already implemented in EasyVoice: keep-alive, quick character read, multilingual mode,
auto recovery. Only the items below are new since 5.7.7.10.

---

## 1. Name map

Almost every `c3.*` class shifted one letter. Full table is in `CLAUDE.md`. The ones
that matter here:

| 5.7.7.10 | 5.7.7.18 | role |
|---|---|---|
| `c3.m` | `c3.n` | settings store |
| `c3.j` | `c3.k` | settings fragment (all tabs) |
| `c3.y` (`y.g`, 227 ln) | `c3.d0` (`d0.t`, 649 ln) | number/punct/emoji segmenter |
| `c3.z` | `c3.e0` | one segment (text + type); only `toString()` changed |
| `c3.x` | `c3.c0` | all-whitespace test |
| `c3.c` | `c3.c` | emoji regex — **byte-identical** |
| `com/vnspeak/autotts/a.java` | same | script→lang map — **byte-identical** |

`AutoTtsService.h0` / `m0` and `c3.l0` are the licence/signature gate. `d0.t` and
`clsCLD2.c` take them as the last two args and return empty/UNKNOWN when either is
`-1`. Out of scope under the existing carve-out — do not port.

## 2. Settings (c3/n.java)

Five new keys. `n.o` loads the three strings (empty → `n.e(Locale.getDefault())`),
`n.q` loads the two booleans; `n.v` and `n.z` persist them.

```
number_specific_language   String   ""    -> device iso3     AutoTtsService.J
punc_specific_language     String   ""    -> device iso3     AutoTtsService.L
emoji_specific_language    String   ""    -> device iso3     AutoTtsService.N
punctuation_with_sentence  Boolean  true                     AutoTtsService.c0
smart_number_reading       Boolean  false                    AutoTtsService.d0
```

**One default changed:** `quick_character_reading` was `true` in `c3/m.java:546`,
is `false` in `c3/n.java:546`. A sweep of every `getBoolean`/`getInt`/`getString`
default in the store confirms this is the only changed default; the rest are additions.

`n.t` persist-all order is `w, C, B, v, x, u, z` — the same seven as 5.7.7.10's
`m.u` (`x, D, C, w, y, v, A`), renamed only. Order unchanged.

## 3. Segment types and routing

`d0.j(s)`: `c0.a(s)` all-whitespace → **0**; `d0.o(s)` punct → **4**;
`d0.n(s)` number → **3**; `d0.l(s)` emoji → **5**; else → **1**.
Emoji (5) is new; in 5.7.7.10 emoji fell inside the Latin class.

Type → language, `AutoTtsService.java:2356`:

```
1 -> O (mixed latin)   3 -> J (number specific)   5 -> N (emoji specific)
2 -> P (mixed nonlatin) 4 -> L (punct specific)
```

Mode int semantics for each of number `I` / punct `K` / emoji `M`:

```
0 Auto language      -> type = d0.k(types, i, default)  (backward scan, then forward
                        from 0, then the locale default n6)
1 Primary language   -> type 1
2 Secondary language -> type 2
3 Specific language  -> type left at 3/4/5, so the map above routes it to J/L/N
```

In the mix/multilingual per-chunk path (`AutoTtsService.java:2207-2272`) the same
modes are read directly: `0 or 1 -> O`, `2 -> P`, `3 -> J/L/N`. Note **0 and 1 both
go to O** there.

`d0.t` is called from **four** sites (2056, 2199, 2410, and the multilingual one);
5.7.7.10's `y.g` had two. That is the "applied to Dual, Mixed and Multilingual" line.

## 4. Patterns (d0 static block)

| | 5.7.7.10 `y` | 5.7.7.18 `d0` |
|---|---|---|
| latin block | `d` — included `c.a()` (emoji) | `d` — **same minus the emoji group** |
| emoji | (none) | `e` = `a` = `(?:c.a()|[U+200D U+FE0F U+20E3])+` |
| number (whole-segment test) | `b` | `b` rewritten: `[×÷°₠-⃏ -⁯\p{Punct}\p{Space}]*[0-9][…]*` |
| number (run finder) | (none) | `f`, with `(?<![\p{L}0-9])` / `(?![\p{L}0-9])` guards |
| punct | `c` | unchanged |
| bidi strip | `e` | `g` |

Pipeline in `d0.t`: split by `d` → between latin runs `d0.c` splits emoji (5) from the
rest (2); inside latin runs `d0.d` splits number runs (3) and the remainder goes to
`d0.b`, typed by `d0.j` → merge adjacent same-type-or-0 → smart number pass → type
assignment from the modes → final merge.

**Punctuation flow**, `d0.t:624`: `if (n4 != 0 && !AutoTtsService.c0)` — when the
punct mode is not 0 *and* punctuation-in-flow is off, type-4 segments are kept out of
the final merge, using the **original** type array, not the reassigned one.

## 5. Smart number reading (AutoTtsService.d0 flag)

`d0.h` is a keyword table: 52 language rows plus a `*` row that always applies
(`otp pin sms imei cvv tel fax hotline sim whatsapp zalo viber telegram`).
`d0.a()` builds the active set, keeping a row only if its tag is `*` or is in `n.f`
(the user's enabled languages), and caches it in `d0.i`.

For each type-3 segment (`d0.t:514-533`), respace with `d0.s` (a space between
adjacent digits) when either:
- `d0.r(text)` — phone-number shape: `+`/`00` prefix with ≥8 digits; leading `0` with
  ≥5 digits; ≥7 consecutive digits; or ≥3 groups of 2-4 digits with ≥7 digits total.
  Rejects anything containing `%`, `$` or U+20A0-U+20CF, and anything with `,`/`.`
  unless the run is long enough.
- or the set is non-empty **and** `d0.m(text)` (≥4 digits, only digits/`,`/`.`/`%`/`$`/
  currency) **and** a keyword occurs in the 48 chars before (`d0.g`) or the 24 chars
  after (`d0.f`). `d0.e` requires letter boundaries on both sides unless `d0.p` says
  the keyword's first char is in a script without word spacing (Thai, Myanmar, Khmer,
  CJK, Hangul, CJK-compat) — those match anywhere.

## 6. CLD2 language hints

`clsCLD2` is otherwise rename-only. New:

```java
public static void f(Set s) { nativeSetLanguageHints(s == null ? null : s.toArray(new String[0])); }
private static native void nativeSetLanguageHints(String[] v);
```

Called once, `AutoTtsService.java:1659`: `clsCLD2.f(c3.n.f)` — the enabled-language
set, right after `a0()` and before `X()`. This is the "improved language detection
accuracy" line.

## 7. UI

`fragment_modes.xml` — the per-mode duplicates (`number_mode_language_mixed`,
`localespans_mixed`, `localespans_multilingual`, …) are gone, replaced by two shared
blocks: `@id/ReadingSettings` (6 spinners) and `@id/CommonSettings` (localespans).
Radio order is now **None, Auto, Google, Dual, Mixed, Multilingual** (Dual moved).

Visibility matrix from `k.onRadioButtonClicked` (H0 auto, I0 dual, J0 mixed,
K0 multilingual, L0 reading, M0 common):

| mode | H0 | I0 | J0 | K0 | L0 | M0 |
|---|---|---|---|---|---|---|
| 0 none | gone | gone | gone | gone | gone | gone |
| 1 dual | gone | **vis** | gone | gone | **vis** | **vis** |
| 2 auto | **vis** | gone | gone | gone | gone | **vis** |
| 3 google | **vis** | gone | gone | gone | *(untouched)* | *(untouched)* |
| 4 mixed | gone | gone | **vis** | gone | **vis** | **vis** |
| 5 multilingual | gone | gone | gone | **vis** | **vis** | **vis** |

Google not touching L0/M0 is AutoTTS's own quirk — the previous mode's visibility
survives. Reproduce it.

Spinner dispatch, `k.O1`: `t0`→`I`, `u0`→`K`, `v0`→`M`; each shows its specific
spinner (`A0`/`B0`/`C0` → `J`/`L`/`N`) only when the mode == 3. Selecting punct
mode 3 also **disables** `D0`, the punctuation-in-flow checkbox on the Advanced tab
(`k.O1:844,848`), and `k.T2:1125` sets its initial enabled state to `K != 3`.

`fragment_advanced.xml` — new **Text-to-Speech Settings** header + button +
description at the very top (`k.O2` fires `com.android.settings.TTS_SETTINGS`, and
Toasts "TTS settings are not available on this device." on
`ActivityNotFoundException`), and two new rows at the end of Language Detection
Options: `punctuation_with_sentence` and `smart_number_reading`.

**AutoTTS bug, reproduce as-is:** the description TextView under
`punctuation_with_sentence` uses `@string/quick_character_reading_description`.
`punctuation_with_sentence_description` exists in `strings.xml` but is never used.

`NewSettingsActivity` is rename-only. `AutoTtsService` keeps the same 63 methods.

---

## Porting status in EasyVoice

- [x] §2 — the five new settings, both loaders and both persisters, plus the
      `quick_character_reading` default flip. `181ba9f`
- [x] §3/§4 — emoji as segment type 5, the mode ints threaded to C++ the way `d0.t`
      takes them, `d0.k`'s type assignment, and the punctuation-in-flow split.
      `39bb512`
- [x] §6 — CLD2 language hints. `04a51f3`
- [x] §7 — UI: the TTS Settings button, the two new Advanced rows, the shared reading
      settings with the fourth "Specific language" option, and the cross-tab
      enable/disable. `0df94d0`
- [x] §5 — smart number reading. `27a9c68`

Two spots where a Java library call had to be written out instead of transcribed,
recorded so they are not mistaken for verified ports: `isLetterCodepoint` stands in
for `Character.isLetter` (covers the letter blocks of every script in the keyword
table) and `lowerCaseRoot` for `toLowerCase(Locale.ROOT)` (cases Latin incl. Latin-1,
Greek and Cyrillic — every other script in the table is caseless).

---

## 8. Full sweep of everything else (audit, 2026-08-07)

Every `c3.*` class was paired old→new by normalised content and diffed. Real findings:

**`c3.e` — a brand-new class, and the one real gap the first pass missed.**
In 5.7.7.10 the iso2↔iso3 maps were `c3.m.h` / `c3.m.i`, built purely from
`Locale.getISOLanguages()` + `getISO3Language()` with a `cmn/lzh/gan/hak → zho` fix
(`m.c`, old line 159-175). 5.7.7.18 replaces that with `c3.e`, which starts from the
same platform data and then **overrides** it:

- `e.d` — 20 iso2 → *terminological* iso3 pairs (`sq→sqi hy→hye eu→eus my→mya zh→zho
  cs→ces nl→nld fr→fra ka→kat de→deu el→ell is→isl mk→mkd mi→mri ms→msa fa→fas
  ro→ron sk→slk bo→bod cy→cym`). Android returns the *bibliographic* codes here
  (alb, arm, baq, bur, chi, cze, dut, fre, geo, ger, gre, ice, mac, mao, may, per,
  rum, slo, tib, wel), so this is a behavioural fix, not a reshuffle.
- `e.c` — the reverse, bibliographic iso3 → iso2.
- legacy tags: `iw`/`he`→`heb`, `in`/`id`→`ind`, `ji`/`yi`→`yid`; `heb`→`he`,
  `ind`→`id`, `yid`→`yi`.
- Chinese: `cmn lzh gan hak wuu hsn cjy nan mnp` → `zh` (5.7.7.10 handled only the
  first four), plus `zho`→`zh` and `yue`→`yue`.
- `e.f` — 10 codes with no iso2 (`fil ceb haw hmn war nso syr chr lus sco`) map to
  themselves in both directions.
- `e.a(tag)` strips at `-`/`_` and lowercases ROOT, so `en-US` / `zh_CN` resolve.
- `e.b` → iso2, `e.c` → iso3; both return null when unknown and every caller falls
  back.

It is called from `c3/n.java:483` and from **five** places in `AutoTtsService`
(830, 1972, 2306, 2479, 2685, 3084) — that is one per mode, on the detector's output,
where 5.7.7.10 did `c3.m.h.get(...)`. So it changes routing in **every** mode.

**Manifest:** `SYSTEM_ALERT_WINDOW` is gone in 5.7.7.18. It was declared and never
used in 5.7.7.10 — the long-standing open question is now answered by AutoTTS itself.
(`extractNativeLibs` false→true and the fused-modules meta-data moving are packaging,
and `com.pairip.application.Application` / `@drawable/ic_launcher` stay carve-outs.)

**Everything else in the sweep is noise**, confirmed by reading each diff:
`c3.d` (keep-alive), `c3.b0→g0` (export/import, lost some `System.out.println`
debug lines), `c3.o→p` (logger), `c3.t→u` (required-engines dialog), `c3.l→m`
(resource ids renumbered, `K2`→`L2`), `c3.w→b0`, `c3.f0→k0`, `c3.d0→i0`,
`c3.e0→j0`, `c3.z→e0` — all variable-slot shuffles, renames or dropped debug
output. `c3.x`, `c3.y`, `c3.z`, `c3.a0` are new but are only API-33 `PackageInfo`
shims and a version-code helper. `NewSettingsActivity` and the rest of `clsCLD2`
are rename-only. `res/xml/` and every `values/` file except `ids.xml`, `public.xml`
and `strings.xml` are unchanged.

### Still to port

- [x] `c3.e` ported in full as `IsoCodes.kt` (tables machine-extracted). It now backs
      `normalizeLangCode` (= `e.b`), the three detection-result sites (= `e.c`) and the
      enabled-language set (= `e.b`, `AutoTtsService:830`). `n.e(Locale)` is unchanged
      in 5.7.7.18, so `localeIso3` / `SharedPrefsManager.toIso3` stay as they are.
- [x] `SYSTEM_ALERT_WINDOW` dropped from the manifest.

---

## 9. Class-by-class sweep of the app code (audit round 2, 2026-08-07)

Round 1 (§8) paired whole `c3.*` files. This round goes method by method, matching each
old method to its most similar new one by normalised content rather than by name — the
obfuscated method letters shifted too, so name-keyed diffs are worthless here.

### AutoTtsService — 85 old "methods" vs 65 new

The count gap is not code going missing: CFR inlined the anonymous listener classes in
5.7.7.10 and split them into their own files in 5.7.7.18 (`c3.i0`, `c3.j0`, …), so the
old file carried extra tiny `a`/`b`/`c`/`d`/`e`/`onDone`/`onError`/`onInit`/`onStart`/
`onStop`/`onTaskRemoved` bodies that are now elsewhere.

Real differences, everything else being CFR rendering:

| method | what changed | ours |
|---|---|---|
| `onSynthesizeText` | the whole `d0.t` / type-5 / specific-language rework | ported |
| `onCreate` | adds `clsCLD2.f(n.f)`, and logs version name + code via the new `c3.a0` | hints ported; the version log is a log line |
| `M` (engine lookup) | the log line is now also skipped for disabled entries — `!(f.isEmpty() \|\| f.equalsIgnoreCase("disable") \|\| i)` instead of `!f.isEmpty() && !f.equalsIgnoreCase("disable")`. **Return values are identical.** | no action |
| `g0` (silence loop) | `return` became `break` on `InterruptedException`, and the `o0` check moved into the loop condition. Nothing follows the loop, so behaviour is identical | no action |

Read and confirmed as rendering-only: `f0`, `P`, `L`, `T`, `Y`, `e0`, `h0`, `i0`, `d0`,
`onLoadLanguage`, `b0`, `c0`, `k0`, `m0`, `n0`, `o0`, `l0`, `q`, `o`, `B`, `H`, `U`, `X`,
`onDestroy`, `onGetLanguage`, `onIsValidVoiceName`, `onLoadVoice`, `onIsLanguageAvailable`,
`onGetDefaultVoiceNameFor`.

### Settings fragment `c3.j` → `c3.k` (49 → 53 methods)

Changed: `onRadioButtonClicked`, `O1` (spinner dispatch) and `R2`→`T2` (Advanced tab) —
all three read in full in §7 and ported.

New: `O2` (TTS settings intent, ported) and **`B2`/`C2`/`D2`**, guarded accessors for the
selected language's volume/speed/pitch that return **100** when the selected index is out
of range. Ours read `LangStore.languages[selectedLangIndex]` unguarded, so this is now
mirrored as `storedSliderValue`.

`Z2`→`b3` and `F0` gained a null-guard and constant resets; `I1`–`N1` are a family of
near-identical small methods the matcher paired badly, all rendering-only on inspection.

### The rest of the app package

`CheckVoiceData`, `GetSampleText`, `LicensesDialogFragment` — obfuscated-import renames
and one resource-id renumber. `a.java` byte-identical. `clsCLD2` — only the hint hook
(§6). `NewSettingsActivity` — rename-only.

**Nothing else in the app code changed between 5.7.7.10 and 5.7.7.18.**

---

## 10. onSynthesizeText, per-mode — OPEN, work in progress

§9 treated `onSynthesizeText` as "the d0.t rework, ported". That was too coarse. Read
properly it is **607 lines → 1390**, and every mode branch roughly doubled:

| branch | 5.7.7.10 | 5.7.7.18 |
|---|---|---|
| gate `S == 1` (dual) | line 188, ~67 lines | line 197, ~146 lines |
| gate `S == 4` (mix) | line 255, ~127 lines | line 343, ~214 lines |
| gate `S == 5` (multilingual) | line 382, ~225 lines | line 557, ~... |
| gate `S == 4 \|\| S == 5` | **absent** | **line 1208 — new** |

(line numbers are relative to the start of the method)

### What is confirmed missing on our side

The **continuation / later-chunk path** in the tail of the method now carries the same
type→language routing that §3 documented for the segmenter, and we have only ported it
in the mix path. At method-relative 1231-1264:

```
lang = clsCLD2.b(text, ...)                  // detect
if (lang.length() > 2) lang = lang[0..2]
lang = c3.e.c(lang)                          // NEW in 5.7.7.18
if (lang == null) {                          // fall back BY SEGMENT TYPE
    switch (AutoTtsService.k().get(0).a()) {
        case 1 -> O        case 4 -> L
        case 2 -> P        case 5 -> N
        case 3 -> J
    }
}
```

In 5.7.7.10 this fallback existed only for types 1 and 2. Types **3, 4 and 5 routing to
J / L / N here is new**, and so is the `c3.e.c` conversion in this path, and so is the
`S == 4 || S == 5` gate just above it at 1208.

### 10a. Dual branch — read in full, specified

New: method-relative 197-342. Old: 188-254.

After `d0.t`, AutoTTS looks at the **first** segment only, and preflights the language
that segment's type routes to. `onLoadLanguage` returning -1 or -2 aborts the whole
synthesis with an error code:

| first segment type | language preflighted | error code on failure |
|---|---|---|
| 1 | `"eng"` (literal) | **4** |
| 2 | `H` (dual language) | 5 |
| 3 | `J` (number specific) | 5 |
| 4 | `L` (punct specific) | 5 |
| 5 | `N` (emoji specific) | 5 |

Each logs `"language: <code>"` before, and `"Languge is not supported: <code>, text: <t>"`
(AutoTTS's own spelling) on failure. Empty chunk list falls through to the plain path.

5.7.7.10 had **only rows 1 and 2** here (old 215-239, using the then-name `G` for the
dual language). Rows 3, 4 and 5 are new.

**Ours does none of this preflight** — the dual path takes the C++ chunks and appends
them, with no `onLoadLanguage` check and no error return. So rows 1 and 2 were already
missing from the 5.7.7.10 port, and 3-5 are new on top.

### 10b. Mix branch — read in full, and it contradicts how we resolve the language

New: method-relative 343-556.

Per LocaleSpan chunk:

1. If the span carries a language that is neither empty nor `"unknown"`: `M(lang)`; if
   that is empty or `"Disable"`, use `G` (the auto-mode language). Set it on the segment,
   append, next chunk.
2. Otherwise `d0.t(text, I, K, M, …)`, and then **for each sub-segment the service
   re-tests the text itself** and picks the language straight from the mode:

   ```
   if (d0.n(text))       // number
       I == 0 || I == 1 -> O      I == 2 -> P      I == 3 -> J
   else if (d0.o(text))  // punctuation
       K == 0 || K == 1 -> O      K == 2 -> P      K == 3 -> L
   else if (d0.l(text))  // emoji
       M == 0 || M == 1 -> O      M == 2 -> P      M == 3 -> N
   else
       clsCLD2.c(text, …)         // the mix-chunk detection list
   ```

**This is the gap.** `d0.t` has already assigned a type to every segment, and for mode 0
that assignment is `d0.k`'s neighbour scan (back, then forward from 0, then the locale
default). The mix path **ignores that for language purposes** and maps mode 0 straight to
`O`. `d0.t`'s types still matter — they decide where segments merge — but they are not
what selects the voice here.

Our C++ returns one `(type, lang, text)` triple and the Kotlin mix path uses `lang`, so
we currently speak a mode-0 number segment in whatever `d0.k` derived from its
neighbours, where AutoTTS speaks it in `O`. Same for punctuation and emoji.

The fix is a split, not a tweak: keep `d0.t`'s type for merging, and derive the language
in the service by re-testing the segment with `d0.n` / `d0.o` / `d0.l` and applying the
table above.

Note `d0.n`, `d0.o` and `d0.l` are whole-string matches against patterns `b`, `c` and `e`
respectively, so the re-test has to run on the merged segment text, not per character.

### 10c. Multilingual branch — read in full

New: method-relative 557-698. Same mode table as mix, three differences:

1. The span-with-a-language test is one combined condition —
   `!(lang.equalsIgnoreCase("unknown") || lang.isEmpty() || (lang = M(lang)).isEmpty()
   || lang.equals("Disable"))` — and when it fails it **falls through to `d0.t`**.
   Mix instead substitutes `G` and keeps the span. Multilingual has no `G` fallback.
2. In the `clsCLD2.c` arm every returned item goes through **two** fallbacks:
   ```
   lang = c3.e.c(item.a)
   if (lang == null)                       lang = item.b ? O : P     // b = latin flag
   if (M(lang).isEmpty() || M(lang) == "Disable")  lang = item.b ? O : P
   ```
   The second one — no engine for the detected language — is the one we do not have.
3. After the loop: empty `Q` logs `"lstLanString is empty!"` and aborts with
   `K(callback, 7)`; otherwise the **first** segment's language is preflighted with
   `onLoadLanguage`, and -1/-2 aborts with **7** (dual uses 4/5).

A mode value that is not 0, 1, 2 or 3 makes the segment `continue` — dropped, not added.
Mix reaches the same outcome by a different branch.

### 10d. Auto / Google tail — read in full

The chunk-speak tail carries **two** type-based fallbacks, both mapping
`1 -> O, 2 -> P, 3 -> J, 4 -> L, 5 -> N` off `k().get(0).a()`:

- **1244-1264** — after `clsCLD2.b`, `substring(0, 2)` and `c3.e.c`, when `c3.e.c`
  returns null.
- **1270-1290** — after that, when `AutoTtsService.r(ctx, lang)` (the engine lookup) is
  empty or `"Disable"`.

Then `AutoTtsService.l(ctx, lang, "", "")` preflights, -1/-2 aborting. In 5.7.7.10 both
fallbacks covered only types 1 and 2, and neither went through `c3.e.c`.

The gate at **1208**, `if (S == 4 || S == 5) break block30`, skips the first preflight
for mix and multilingual — they already did their own inside their branches — and lets
dual, auto and google run it.

### The single port pass — everything it must contain

1. **dual** (§10a): first-segment preflight, `1 -> "eng"` err 4, `2 -> H`, `3 -> J`,
   `4 -> L`, `5 -> N` err 5. We have none of it.
2. **mix** (§10b): keep `d0.t`'s type for merging, but derive the language by re-testing
   the merged segment with `d0.n` / `d0.o` / `d0.l` and mapping the mode
   `0|1 -> O, 2 -> P, 3 -> J|L|N`; span with a language uses `M(lang)` with a `G`
   fallback.
3. **multilingual** (§10c): as mix but no `G` fallback, the extra `M(lang)` engine check
   in the `clsCLD2.c` arm, and the empty/preflight aborts with code 7.
4. **tail** (§10d): both type fallbacks, the `c3.e.c` conversion, and the `S == 4 || S == 5`
   gate.

These four share one shape — resolve a language for a segment, check the engine, fall
back by type — so they went in together as one change, not four patches.

**Done:** `0f4b165` (dual preflight, mix/multilingual mode table, multilingual's second
fallback) and `e06fcd7` (multilingual onto the shared chunker, tail fallbacks extended
to types 3/4/5). The mix tail turned out to carry the same double fallback and the same
error-7 preflight as multilingual, so both now run one shared `resolveMixChunk`.
- [ ] Port the dual first-segment preflight table above.
- [ ] Port the continuation-path type fallback (1231-1264) and the `S == 4 || S == 5`
      gate at 1208.

**§10 is now closed** — all four paths ported in `0f4b165` and `e06fcd7`.

---

## 11. Deep re-read of mix, dual and auto (2026-08-07)

Two things the mode-by-mode pass had still not caught.

**`clsCLD2.c` splits; we did not.** It returns a list of (language, latin flag, text)
triples, not one language for the chunk, and both mix and multilingual add every run as
its own segment. Our C++ has always had the equivalent, `nativeGetLanguages`, returning
the same flat triples — Kotlin simply never called it, so a chunk mixing two scripts got
one voice for all of it. Fixed in `0a70de0`; found by asking why that native function had
no callers.

**The first-chunk preflight was only in dual.** AutoTTS runs `onLoadLanguage` on the first
segment's language in mix (524-538) and multilingual (668-672) too, aborting with error 7.
Added to both.

### §11 correction — the auto tail is NOT a divergence

An independent read (a separate agent, resolving every CFR labelled-block target by
brace-matching rather than by eye) settled this, and it corrects what §11 first recorded.

The gate at chunk-runnable line 3048 is reached only after `if (var3_2 != 1) break
block27`, so:

- **before** the gate (block27) is **dual only** — it switches on `k().get(0).a()`,
  the segment TYPE: 1→"eng", 2→H, 3→J, 4→L, 5→N, each with its own preflight
- **after** the gate is auto, google and none — `l(ctx, k().get(0).b(), "", "")` and
  nothing else: no CLD2, no type switch, no engine test
- mix and multilingual jump over both

So auto/google never reach the two type fallbacks at all, and in every mode the
segments there were rebuilt with the `e0(String,String)` constructor, whose type is
**-1** — those two fallbacks are dead code in AutoTTS itself.

Auto/google handle a missing or "Disable" engine **once, up front**, in
`onSynthesizeText` 1972-1979: `e.c(detected)`, null → **G**; then `M(lang)` empty or
"Disable" → **G**. Not the detected language, not O/P/J/L/N — **G**, the auto-mode
language. `EasyVoiceTtsService.kt:1049-1052` does exactly that. **Match, no divergence.**
§11's "deliberately left as it is" entry was based on my own misreading of which modes
reach that region; there was nothing to port.

## 12. Components, system-TTS wiring and libraries (2026-08-07)

Checked because a file-level diff says nothing about whether our *components* are
declared the way AutoTTS declares them.

**`res/xml/tts_engine.xml`** — byte-identical between 5.7.7.10 and 5.7.7.18, and ours is
the same declaration pointing at our own settings activity. Nothing to do.

**Service declaration** — ours carries every attribute AutoTTS's does:
`accessibilityEventTypes="typeWindowsChanged"`,
`accessibilityFlags="flagRetrieveInteractiveWindows"`,
`canRetrieveWindowContent="true"`, `exported="true"`,
`foregroundServiceType="mediaPlayback"`, `label="@string/app_name"`, the
`<intent-filter android:priority="100">` with `android.intent.action.TTS_SERVICE` and
`category.DEFAULT`, and the `android.speech.tts` meta-data pointing at `@xml/tts_engine`.
Same for `CheckVoiceData` and `GetSampleText` (`exported="true"`, `Theme.NoDisplay`) and
the `FileProvider`.

The one component AutoTTS has that we do not is `androidx.startup.InitializationProvider`,
which androidx.startup contributes via profileinstaller — a library artefact, not a
feature, and not something to mirror by hand.

**Manifest deltas between the two AutoTTS builds** were only the three already recorded in
§8: `SYSTEM_ALERT_WINDOW` dropped (done), `extractNativeLibs` false→true and the
fused-modules meta-data moving, both packaging.

**Libraries** — `androidx` is 379 files in both builds with an identical subpackage list;
`com` went 345→346. The single added file is
`com/google/android/gms/common/stats/DE/…/pbbjamvqysbuyaauvpaybwulpmruu.java`, an
obfuscated Play-services stats class. No new library, no new feature surface.

So the only classes that changed for real between 5.7.7.10 and 5.7.7.18 are the app's own:
`c3` went 33→38, and those five are `c3.d0`, `c3.e`, plus the API-33 shims `c3.x`, `c3.y`,
`c3.z` and the version helper `c3.a0` — all covered in §8 and §9.

---

## 13. The segmenter never actually split (2026-08-07) — the big one

Everything in §3-§5 was wired end to end: the flags reached the C++, the JNI parameter
order matched, the mode tables were right. And all three new features still did nothing,
because the thing they operate on was never produced.

Found by compiling `buildMixChunks` standalone against the header stubs and running it on
sample text. Before the fix, every input came back as **one type-1 segment**:

```
"Call me at 9876543210 today"  ->  [t1]'Call me at 9876543210 today'
"hello, world!"                ->  [t1]'hello, world!'
"abc 123 !!! ok"               ->  [t1]'abc 123 !!! ok'
```

`d0.t`'s pipeline is: pattern `d` finds the Latin runs; between them `d0.c` splits emoji
out; **inside** them `d0.d` splits standalone number runs (pattern `f`) and hands the rest
to `d0.b`, which splits on punctuation runs (pattern `c`) and types every piece with
`d0.j`. We had only the outer Latin/non-Latin split, with one whole-buffer type test —
the 5.7.7.10 shape. **`d0.b` and `d0.d` were never written.**

So there were no type-3 segments for smart number reading to respace, no type-4 segments
for punctuation-in-flow to hold apart, and no type-3/4/5 segments for the specific
languages to route. Three of the nine What's New lines were dead on arrival.

Now implemented as `splitByNumber` (`d0.d`), `splitByPunct` (`d0.b`) and `splitByEmoji`
(`d0.c`), with `segmentTypeOf` for `d0.j`. `matchNumberRun` implements pattern `f`
including its possessive optional group — the group takes the longest span ending in a
numeric character, and if the trailing `(?![\p{L}0-9])` then fails there is no
backtracking to a shorter span, the match simply fails at that position.

Verified empirically after the fix:

```
smart number ON   "Call me at 9876543210 today" -> '9 8 7 6 5 4 3 2 1 0'   (phone shape)
smart number ON   "your otp is 4821 ok"         -> '4 8 2 1'               (keyword in window)
smart number ON   "I have 1234 rupees"          -> unchanged               (no shape, no keyword)
smart number ON   "it costs $1234 now"          -> unchanged               (d0.m rejects $)
punct mode1 flow off  "hello, world!"  -> 'hello' | ', ' | 'world' | '!'
punct mode0 flow off  "hello, world!"  -> unchanged  (AutoTTS gates on mode != 0)
emoji mode3       "hello :) world"     -> latin | emoji(specific) | latin
mode3 all         "abc 123 !!! ok"     -> 'abc ' | '123'(number lang) | ' !!! '(punct lang) | 'ok'
```

**Lesson for the next pass:** wiring audits confirm that a flag reaches its call site.
They cannot tell you the call site never fires. Test the behaviour, do not just trace it.

---

## 14. What is left, stated precisely (2026-08-07)

All 19 audit findings are fixed. Two questions must not be confused:

**(a) Did we miss anything 5.7.7.18 changed?** No, and this is now evidenced rather
than asserted:

- §9 matched every method of every app class old-to-new by normalised content and found
  exactly four real differences in `AutoTtsService` — `onSynthesizeText`, `onCreate`,
  `M`'s log condition and `g0`'s return-vs-break — plus the new `c3.d0` and `c3.e`.
- §12 confirmed the manifest components, `res/xml/tts_engine.xml`, every resource
  directory and the library set are unchanged.
- The voice-loading path, which is what actually produces sound, was re-checked here
  line by line: `b0()` (156 lines, the main loader) and `c0()` (34 lines) have **zero**
  structural differences between the two versions, and `d0()` (182 lines) has 30 diff
  lines that are all CFR rendering logger StringBuilder chains as casts.

**(b) Is our original 5.7.7.10 port of the unchanged areas faithful?** Unknown for
several of them. These were ported in earlier sessions and have never had the depth of
audit that modes, the segmenter and settings just received:

- engine and voice loading (`b0`/`c0`/`d0`, `c3.k0`, `c3.d` keep-alive) vs `loadVoice`,
  `loadVoiceOriginal`, `loadVoiceDedicated`, `initAllEngines`, `restoreEngine`
- the engine scan (`NewSettingsActivity.B0/D0/y0/z0`) vs `EngineFinder`
- export/import (`c3.g0`) vs `SharedPrefsManager`
- the Voices tab internals (`k.E2/N2/a3/b3`) and the Languages tab internals
  (`k.W2/X2`)
- the logger, `CheckVoiceData`, `GetSampleText`

Every deep audit so far has found real defects, including three that were ours rather
than version drift, so the prior on these is not "clean". They are simply a different
piece of work from the 5.7.7.18 update, and should be labelled as such rather than
folded into it.

## 15. Voice loading, read line by line (2026-08-07)

`b0()` (AutoTtsService 886-1041) against our `loadVoice`. Verified equal, point by point:

- dedicated bypass when the flag is set and the mode is not 3
- empty package falls back to the remembered one, otherwise it is remembered; `-` and
  `_` stripped
- engine search over the pool for package match **and state 2**, `-1` when not found
- empty variant with a voice already set hands off to `c0` / `loadVoiceOriginal`
- current voice read, and the "Do nothing!" early return when iso3 language matches,
  country matches or the requested country is empty, and the name equals the variant
- the voice list is searched for `pkg#locale`, then the store for the same pair, to
  override the variant
- `*Default` → `setLanguage` only when the locales do not already match
- otherwise the engine's voice list is searched by name and `setVoice` is called;
  when the list is null it falls back to `setLanguage` under the same locale check
- **every** `setLanguage`/`setVoice` return is tested `>= 0`, setting the wrapper flag,
  and calls `i0` / `restoreEngine` otherwise
- the wrapper's locale and voice name are written from `n3`, not from the current index
- not-found path logs "TTS is not ready" and sets the index to -1

No divergence. Recorded so this method is not re-read from scratch.

**Method note.** Three suspected gaps were raised during this read and all three were
artefacts of filtering logger lines out of the listing — the engine search loop, the
"Do nothing!" return and the `setVoice` result check were all present, hidden inside or
next to lines containing a log call. Read the raw text before believing a gap in this
file.

## 16. c0 and d0, read line by line (2026-08-07)

**`c0()` (1043-1076) vs `loadVoiceOriginal`.** Equal throughout: the remembered-package
fallback and the `-`/`_` strip, the engine search on package and state 2, the early
return when the wrapper's stored locale already matches on iso3 language and on country
or the request has none — two conditions, not the three `b0` uses — then `setLanguage`
with `>= 0` setting the flag, the locale and an empty voice name, `i0` on failure, and
index `-1` when no engine is found. Ours guards the stored locale for null where AutoTTS
relies on `n.e(null)` returning "zxx"; same outcome.

**`d0()` (1078-1259) vs `loadVoiceDedicated`.** Equal throughout:

- the previous index is saved before the new one is assigned
- `!bl || !wrapper.f` gates the body — ours writes the negation, `dedicated &&
  localeSet` returns early
- the "*0 Do nothing" check runs only when the index did not change and a voice exists,
  and requires the locale to match **and** the name to equal the variant, or the variant
  to be `*Default` or empty
- the voice-list scan matches on package **only** — no locale field check, unlike `b0` —
  and the inner store scan skips unless the package matches, the locale matches through
  `f0`, and the stored variant is non-empty
- `(*Default or empty) && locale differs` → `setLanguage`, and on success the voice name
  is set to **the requested locale's variant**, not to the variant argument
- then a second do-nothing check, then the voices list with `setVoice`, which on success
  stores **the voice's own locale**
- then a final `setLanguage` when the locale still differs
- every `setLanguage`/`setVoice` is tested `>= 0` with `i0`/`restoreEngine` on failure
- not found → "TTS is not ready" and index `-1`

No divergence in either. With `b0` in §15, all three voice loaders are verified.

## 17. initAllEngines and restoreEngine (2026-08-07)

**`T()` (505-538) vs `initAllEngines`.** Equal: the `synchronized(this)`, the
"initAllTTS" log, the loop calling `m()` then `l()` on every wrapper inside a
per-iteration try/catch, `clear()` and the index reset, then - only when the engine list
is non-empty - one wrapper for entry `i`, its `c3.d` keep-alive binder added to the
binder list, and one `TextToSpeech` stored in the static initialising reference. Only
the first engine is created here; the rest are chained from the init listener.

**`i0()` (1371-1429) vs `restoreEngine`.** Equal: `synchronized(this)`, the early return
when a restore is already in progress, the pool search by package with
`equalsIgnoreCase`, "restore package name is not found", the applicability gate, the
counter bump, the index assignment, `m()`/`l()` inside try/catch, and the new
`TextToSpeech` with the restore listener.

The gate is `k0.h()`: true when the restore count is 0, otherwise
`(nanoTime - lastRestore) / 1e6 > 3000 && count < 10`. `k0.i()` stamps the time and
increments. Ours reproduces both exactly.

One divergence found and removed: `restoreEngine` began with
`if (pkg == null || pkg.isEmpty()) return`, which `i0` does not have. Unreachable in
practice - every call site passes a wrapper's package - but it also skipped the
"restoreTts" log and the in-progress check that AutoTTS performs first. The parameter is
now non-null and the guard is gone.

## 18. Keep-alive binder c3.d (2026-08-07)

`c3/d.java` (184 lines) against `bindEngineKeepAlive` / `unbindEngineKeepAlive` /
`unbindAllEngineKeepAlive`. Equal on every point:

- `c(pkg)` is `synchronized(this)`; already bound to that package with a live
  connection returns true without rebinding
- `android.intent.action.TTS_SERVICE` intent with the package set, `resolveService`,
  and a log-and-false when there is no `serviceInfo`
- the component is set from the resolved `serviceInfo`
- `bindService(intent, conn, 65)` — 65 is `BIND_AUTO_CREATE | BIND_IMPORTANT`, which is
  exactly the pair we pass
- success records the package; failure and exception both log and drop the connection
- `e()` is `synchronized`, unbinds inside a catch, then clears both fields
- `d()` calls `i0(pkg)`, the engine restore

Connection callbacks: `onServiceConnected` logs only; `onServiceDisconnected` calls
`d()`, i.e. restore; `onBindingDied` saves the package, unbinds, then rebinds it;
`onNullBinding` unbinds. Ours matches all four.

**Structural note.** `c3.d` holds **one** connection and **one** package per instance,
and `T()` creates a fresh instance per engine and appends it to the binder list. Ours
keeps a single map of package to connection instead. Same result — one live binding per
engine — because each `c3.d` is only ever handed one package, so its "different package,
unbind the old one first" branch is unreachable in AutoTTS's own usage.

No divergence.

## 19. Engine scan — discovery verified, driver PARTIAL (2026-08-07)

**`B0()` (87-129) vs `EngineFinder`'s discovery — verified equal.** Both clear the two
store lists, then run **three** `queryIntentServices` calls over an
`android.intent.action.TTS_SERVICE` intent with flags **131072, 128 and 0** in that
order, iterate all three result lists, skip a null `serviceInfo`, skip the app's own
package (`contains("autotts")` there, `contains("easyvoice")` here) and anything already
seen, and record label plus package into both the map and the list. The whole thing sits
in one try/catch logging "Error in service discovery". No divergence.

**`D0()` (141-184) vs `scanNextEngine` — shape verified, one predicate OPEN.** Both
increment the index, run a skip loop, and when the index passes the end they cancel the
pending callbacks and call the finalise step; otherwise they take the package at that
index and post the engine init. That much matches.

**Resolved.** `c3/o.java` is eleven lines: two string fields and
`a() { return b.contains("autotts") || b.contains("multilingualtts"); }`, where `b` is the
package. So `D0` skips the app's own two engines. Ours is
`isSelfEngine(pkg) = pkg.contains("easyvoice") || pkg.contains("multilingualtts")` — the
same test with our package substituted and the sibling engine kept. **Equivalent, no
divergence.** Note discovery filters only `"autotts"`, so a `multilingualtts` package does
enter the list and is skipped here instead — a genuine two-stage filter, and ours has both
stages.

**`z0()` (444-479) vs `finalizeScan` — verified equal.** Cancel the pending callbacks;
the failed-index list is deduplicated and sorted **descending** before anything is removed,
which is what keeps the indices valid; each failed index below the list size has its
package collected and its entry removed from the engine list; voices whose engine is in
that removed set are dropped; the language list is rebuilt with `onlyEnabled = false`;
everything is persisted; and a `TextToSpeech` on the app's own package with a null
listener is created — ours does that as `newTestClient()` from the scan callback, at the
same point, after the rebuild and persist and before the UI update.

**`onInit` (553-614) vs our per-engine init — verified equal, including the part that
matters most.** Mark init fired, cancel the watchdog, and on a non-success status add the
index to the failed list. On success it reads the private `mCurrentEngine` field out of
the `TextToSpeech` by reflection to find which engine actually bound — Android can quietly
fall back to the default one — using the expected package when the field is null and when
the reflection throws. **If the actual engine differs from the expected one the index is
marked failed and its voices are not collected.** Ours does exactly this, same fallbacks
included. Then `getVoices()`, collection, `shutdown()` in a try/catch, and on to the next
engine.

**`A0()` (76-86) vs `addVoiceToMatchingEntry`.** Per voice: skip an empty name, then try
to attach to an existing entry and skip if that succeeded; otherwise create the entry and
attach. Same shape both sides.

The engine scan area is verified.

## 20. Voices tab logic (2026-08-07)

Logic only — the layout is ours under the UI carve-out and was not touched.

**`E2()` (438-478) vs `loadVoiceRows`.** Rows are collected from the scanned voice list
for the selected language, filtered by mode: 1, 2, 4 and 5 take everything, 3 takes only
`com.google.android.tts`, and mode 0 takes nothing. Then the "*Disabled" row is appended
— for mode 2 when the language is not `G`, and for modes 4 and 5 when it is neither `O`
nor `P` — with its weight read from prefs, default 1000. Finally the list is sorted and
every row's weight is rewritten to its new position. Ours matches all of it.

Mode 0 is the one case ours does not reproduce, and it is unreachable: our Voices view
returns early for `"none"`, and the None radio is gone.

**`b0.b()` (compareTo).** `if (this.e != other.e) return this.e - other.e;` then
`this.g().compareToIgnoreCase(other.g())` — weight first, then the abbreviated engine
name, case-insensitive. Our comparator is the same, with `tieBreakLabel` returning
`"*Disabled"` for the null row, which is what `g()` yields there.

**`b0.d()`** is `g() + ", " + locale.getDisplayCountry()` when the country is non-empty,
otherwise `g()` — our `rowLabel`. **`b0.f()`** is `pkg + "#" + locale` or
`"Disable#" + locale`, and the Disable row is built with `new Locale(iso, "", "")` whose
`toString()` is just the iso — our `voiceKey` produces the same string.

**`a3()` (2034-2085) vs `rebuildVoiceSpinner` + `refreshVariantSpinner`.** The voice
spinner takes `d()` for every row. The variant spinner is built only when the first row
is not "*Disabled" — ours tests the row for null, which is the same row — and otherwise
its adapter is set to null. The saved variant goes to slot 0 and the rest follow in
order, and when there is no saved variant the store entry is cleared to `""` first.
Ours reproduces this **including two quirks**: the scan loop whose result is never used,
and `Arrays.sort(array, 1, n - 1)`, which leaves the last element unsorted.

**`b3()` (2087-2104) vs the Default button.** Volume, then speed, then pitch, each set to
100 on the selected entry behind a bounds check, each followed by its slider. Same order,
same guard.

`_variant` reads with default `"*Default"` on both sides (`n.java:168`, `n.java:290`).

**`N2()` (738-768)** is the Play Store opener from the required-engines flow, not the
Voices tab: `market://` first, `https://play.google.com/...` on `ActivityNotFoundException`,
and on a second failure a "Cannot open Play Store" toast plus the install callback with
false. Our `MainActivity.openPlayStoreFor` matches.

No divergence.

## 21. Languages tab logic (2026-08-07)

Logic only; the list is a `LinearLayout` of `CheckBox` rows here rather than a `ListView`
(UI departure 3) and that was not revisited.

**Row click (1281-1316).** Read the new checked state, map the visible position to the
original index with `m0.c(pos)` — our `visibleIdx[position]` — write `o0[orig]`, then for
each code of that row: if it is in `n.m()` (the required list) force the row back on and
move to the next code; otherwise find the store entry by case-insensitive code, set
`entry.i = !checked` and persist. Ours matches, including persisting inside the loop.

`y2(i)` returns a **one-element** list holding `n.c.get(i).b`, so the "for each code"
loop is over a single code — ours uses that code directly. Equivalent.

**Select all (1326-1339)** checks every visible row, writes `o0[orig] = true`, then walks
the whole store clearing `disabled`, then persists.

**Clear all (1347-1381)** unchecks every visible row, writes false, sets `disabled` on the
whole store, then re-clears `disabled` for everything in `n.m()`, then walks all codes and
for each required one sets its flag and re-checks its row through `m0.b(orig)`, persists,
and refilters when "show selected" is on. Ours matches step for step.

**`J2()` — read it before "fixing" anything here.** Both branches return `true`:

```java
public final boolean J2(String s) {
    if (m0 == null) return true;
    for (i...) { if (item == null || !item.toLowerCase().contains(s.toLowerCase())) continue; return true; }
    return true;   // no match — still true
}
```

So `if (!J2(entry.b)) continue;` in select-all and clear-all **never skips**, and both
buttons apply to every language rather than only the filtered ones. Ours has no filter
there, which is the same behaviour. Do not add one.

**Search** — `onQueryTextChange` calls the filter and returns true, `onQueryTextSubmit`
returns false. **`x2()`** rebuilds the visible list from the query, the show-selected flag
and the checked states, then restores each visible row's checked state from `o0[orig]`.
**`Z2()`** swaps the toggle button's label on `q0`. All three match ours.

No divergence.

## 22. Export/import and the logger (2026-08-07) — sweep complete

**`c3/g0.java` vs `SharedPrefsManager` + the Advanced tab's Export button.**

- `a(src, dst)` copies with a **1024-byte** buffer; ours is `copyTo(out, 1024)`.
- `b(xml)` pulls two things out of the settings XML: `<boolean name="X_disabled"
  value="true">` into a disabled set with the 9-character suffix stripped, and
  `<string name="XXX">` where the name is exactly three characters and all letters
  (`c()`) into a code-to-value map. Then per entry: skip when the code is disabled, when
  `value.split("#")[0]` is empty, or when it equals "disable" case-insensitively;
  otherwise the engine package joins the result. Ours matches, including the
  `println` tracing.
  Ours additionally requires the value to contain `"#"` before it enters the map. That
  cannot change the outcome: every three-letter key is a per-language engine entry whose
  value is `pkg#locale`, and an empty value is dropped by the empty check on both sides.
- `c(s)` is "every character is a letter"; ours is `name.all { it.isLetter() }`.
- `d(ctx)` exports: the prefs file must exist or a **"Settings file not found"** toast,
  then copy into `cacheDir/shared/`, a `FileProvider` URI, `ACTION_SEND` with type
  **`text/xml`**, `EXTRA_STREAM`, flag 1 (`FLAG_GRANT_READ_URI_PERMISSION`), and a
  chooser titled **"Share Settings"**. Ours does all of it, split across
  `settingsXmlFile`, `exportSettingsFile` and the button.

**`c3/p.java` vs `EasyVoiceLogger`.** `i()` rotates when the file exists and its length is
at least **0x200000** — 2 MiB, which is our `MAX_LOG_BYTES` — by deleting `.3`, then
renaming `.2` to `.3` and `.1` to `.2` in a `for (i = 2; i >= 1; i--)` loop, then the
current file to `.1`. Ours is the same, `for (idx in 2 downTo 1)`. `b(ctx)` only offers
the share when the file exists **and** its length is non-zero — our `shareFileOrNull`.
`a()` clears, `c`/`d`/`e` are debug, error and error-with-throwable, `g()` is the enabled
flag. All match.

No divergence in either.

---

**Sweep complete.** Voice loaders (§15, §16), engine init and restore (§17), the
keep-alive binder (§18), the engine scan (§19), the Voices tab (§20), the Languages tab
(§21) and export/import plus the logger (§22) have now each been read against the
5.7.7.18 decompile. Across all of it exactly one divergence was found and fixed — the
null-or-empty package guard in `restoreEngine` that `i0` does not have (§17).

## 23. Correction — §19-§22 were overclaimed (2026-08-07)

Sections 19 to 22 each ended with "no divergence" or "the area is verified". That was
too strong. Important methods were read and the **area** was then declared done, which is
the same mistake §9 made with `onSynthesizeText` — and that one hid the largest defect in
the project.

What those sections actually cover, and what they do not:

**§19 engine scan.** Read: `B0`, `D0`, `z0`, the voice-collecting `onInit` (553-614),
`A0`, `c3.o`. **Not read:** `y0()` (412-420), the other `onInit` (505-538), `C0()`
(131-140), `E0()` (275-289), and the six `run` bodies at 185-208, 209-274, 392-400,
422-435, 436-443 and 481-491.

**§20 Voices tab.** Read: `E2`, `a3`, `b3`, `b0`'s `compareTo`/`d()`/`f()`, `N2`.
**Not read at the time:** the slider handlers. `P1` has since been read and matches —
clamp to 10, push the bar to 10 as well, then write behind the index bounds check, with
ids mapping to pitch, speed and volume. **Still not read:** the `+`/`-` button handlers
`I1`-`N1`, `M2`, `Q2`.

**§21 Languages tab.** Read: the row click, select-all, clear-all, `J2`, the search
listener, `x2`, `Z2`, `y2`, `c1.c()`. **Not read:** `c1.a()`, the adapter's actual filter
implementation — which is where the visible list is built — and `z2()`.

**§22 logger.** Read: `i()` rotation and the structure. **Not read:** `h()`, the write
path itself, which `autotts_reference/README.md` specifically flags as a method CFR
struggles with, plus `b(ctx)` and `f(ctx)`.

**§18 keep-alive.** Read: `c()`, `d()`, `e()` and the four callbacks. **Not checked:**
where `e()` is driven from on service destroy.

None of this means those areas are wrong. It means they are **sampled, not swept**, and
the sections must be read that way until the listed methods are actually opened.

## 24. Closing §23 — the unread methods, opened (2026-08-12)

Every method §23 listed as unread has now been opened. Six divergences came out of it.

### §22 logger — `h()`, `b()`, `f()`

`h()` is the one method `README.md` flags: CFR throws `ConfusedCFRException: Back jump on
a try block` on it. Read from `smali18/smali/c3/p.smali` (apktool 2.9.3 on the 5.7.7.18
APK). **This is the smali fallback rule being used for the reason it exists** — CFR
genuinely failed, not "just to be sure".

```
synchronized h(level, tag, msg):
    switch (level.ordinal())          // DEBUG=0, INFO=1, WARN=2, ERROR=3
        0, 1 -> nothing               // debug and info never reach logcat
        2    -> Log.w(tag, msg)
        3    -> Log.e(tag, msg)
    if (!enabled) return
    i()                               // rotate
    line = String.format("%s [%s] %s: %s", fmt.format(new Date()), level.c, tag, msg)
    try (BufferedWriter w = new BufferedWriter(new FileWriter(file, true)))
        w.write(line); w.newLine();
    catch (IOException e) Log.e("TtsLogger", "Failed to write log", e)
```

`EasyVoiceLogger.writeLine` is this, line for line, including the `"TtsLogger"` tag on the
failure path and the fact that a debug line is written to the file but never to logcat.

`f(ctx)` is a `synchronized (p.class)` singleton over `getApplicationContext()`; `b(ctx)`
builds the share intent — file must exist and be non-empty, `FileProvider` authority
`packageName + ".fileprovider"`, `ACTION_SEND`, `text/plain`, `EXTRA_STREAM`,
`EXTRA_SUBJECT`, `FLAG_GRANT_READ_URI_PERMISSION`, else null; `k(ctx)` toasts
"No log file to share" on null, else a chooser with `FLAG_ACTIVITY_NEW_TASK`. Our
"Share logs" button is all three inline and matches. **No divergence.**

### §21 Languages tab — `c1.a()` and `z2()`

`c1.a(query, selectedOnly, checked)` clears the three lists, lowercases a null query to
`""`, and for every original index keeps the row when the label contains the query **and**
— only when `selectedOnly` — `checked[i]` is true and `i` is in range. It records the
surviving labels in `d` and the surviving **original** indices in `e`; `b(orig)` is
`e.indexOf(orig)` and `c(pos)` is `e.get(pos)`. `x2()` re-runs it and then re-checks each
visible row from `o0[orig]`.

Our `applyFilter` is the same function with rows rebuilt instead of an adapter refreshed,
and `visibleIdx` is `e`. The search listener also matches: `SearchView.m.a` is the
**text-changed** callback (`SearchView.W` calls `M.a`, the submit path at line 676 calls
`M.b`), AutoTTS returns `true` from `a` after `x2()` and `false` from `b` — ours is
`onQueryTextChange { applyFilter(); true }` / `onQueryTextSubmit = false`.

`z2()` is the required-engine install return path, not a list method: if a pending
`u.d`/`u.a` pair exists it re-tests `getPackageInfo(pkg, 1)`, hands the result to the
callback, toasts "Package not installed. Please try again." on false, then clears the
pair. `N2(pkg)` opens `market://details?id=` and falls back to the Play web URL, both with
`FLAG_ACTIVITY_NEW_TASK`, and only on the second `ActivityNotFoundException` toasts
"Cannot open Play Store" and reports failure. `MainActivity.checkPendingInstall` /
`openPlayStoreFor` match. **No divergence.**

### §20 Voices tab — `I1`-`N1`, `M2`, `Q2`

All six `+`/`-` handlers are `value ±5`, floor 10 on minus, `seekBar.getMax()` cap on
plus, then a **`Toast` of `"<value> of <max>"`** with the max as a hardcoded literal:
**500 for speed, 100 for volume, 200 for pitch**. The store write is bounds-checked in
`L1`, `M1` and `N1` and **not** bounds-checked in `I1`, `J1` and `K1`, and `L1` alone sets
the bar before writing. Ours already reproduced the checked/unchecked split and the
ordering — **but had no Toast at all**. Fixed: all six now toast, last, exactly as
AutoTTS does. (`seekBar.stateDescription` stays; that is ours under the UI carve-out and
is additional, not a replacement.)

`Q2` (battery optimisation) matches. **`M2` did not**: AutoTTS catches
`ActivityNotFoundException` from the document picker and toasts **"No compatible file
manager found on this device"** with `LENGTH_LONG`; ours caught `Exception` and toasted
`"Import failed: <message>"` with `LENGTH_SHORT`. Fixed.

### §19 engine scan — the driver, read in full

`D0()`'s skip loop is where **CFR is wrong**. It renders

```java
while ((n3 = ++this.L) < object.size() && ((o)object.get(this.L)).a()) {}
```

which reads as an unconditional extra increment — every second engine skipped. The smali
is a plain `while (L < size && o(L).a()) L++;` with the increment inside the body. Our
`while (index < engines.size && isSelfEngine(engines[index].pkg)) index++` was already
right; the CFR text was the artifact. Recording it so nobody "fixes" it to match CFR.

Everything else in the driver was read from CFR plus smali for the exception ordering, and
five divergences came out — all of them things **we had added** that AutoTTS does not do:

1. **`y0()` has no try/catch and no empty-list guard.** It is `L = 0; M = false;` watchdog,
   progress text `"%s %s... (3)"`, then `new TextToSpeech(this, j, n.b.get(L).b)` bare. Ours
   wrapped the first engine in the same try/catch as the rest and returned early on an empty
   engine list. Both removed. **This means an empty engine list now throws, exactly as
   AutoTTS does** — the app has to have found at least one third-party engine by then.
2. **The 30 s watchdog does not shut the engine down.** `$e.run()` is only
   `if (!M) { synchronized(O) { O.add(capturedIndex) } } D0();`. Ours also called
   `shutdown()`. Removed.
3. **There is no late-arrival guard between the watchdog and `onInit`.** `j.onInit` opens
   with `M = true; N.removeCallbacksAndMessages(null);` and then runs unconditionally — if
   the watchdog already fired, AutoTTS advances twice. Our `watchdogHolder` identity check
   suppressed the second advance. Removed, and the single-runnable `removeCallbacks` became
   `removeCallbacksAndMessages(null)`.
4. **`D0()`'s ctor `catch` does not cancel the watchdog.** It logs
   `"Error when initialize <pkg>\n<message>"` at ERROR and calls `D0()`, leaving the 30 s
   timer armed to fire and advance again. Ours cancelled it. Removed.
5. **Three logging calls were missing.** Reflection failure logs
   `e("AutoTTS", "Reflection failed", ex)` (with stack); a `getVoices()` failure and a
   `shutdown()` failure each log `d("AutoTTS", ex.getMessage())`. Ours swallowed all three.
   Added.

Also aligned: `z0()` has no re-entry flag, does not shut the engine down and has no
try/catch around the rebuild — the `AtomicBoolean`, the `holder[0]?.shutdown()` and the
`try { … } catch (_: Exception) {}` are gone. `i.onInit`'s failure branch is a bare
`Toast` + `z0()` with no `runOnUiThread` and no try/catch, because a `TextToSpeech`
`OnInitListener` is delivered on the main thread — which is also why the `mainHandler.post`
wrapper around both listener bodies is gone, and why the only place AutoTTS uses
`runOnUiThread` is the progress text, which is where our caller already puts it. The
180 s `z0` watchdog is now armed **before** discovery runs, as in `onCreate`.

`C0()` reads `license_text` and is under the licence carve-out. `E0()` sets each tab's
content description from `cd_tab_title_param` = `"%1$s, tab %2$d of %3$d"`; ours builds the
same string inline.

### §18 keep-alive — the destroy driver

`AutoTtsService.onDestroy` is: log, `stopForeground(1)` + `abandonAudioFocusRequest` in a
try/catch that logs `ex.getMessage()`, `shutdown()` on every pooled engine whose state is
2, then **one try/catch around a loop calling `e()` on every `c3.d` in `g`**, then the
licence checker (carve-out), then `super`. Ours is the same, with
`unbindAllEngineKeepAlive()` as the loop. **No divergence.**

### What this leaves

§19-§22 are now swept, not sampled. The remaining open item is unchanged: the launcher
icon artwork.

## 25. The four modes, re-read end to end from smali (2026-08-12)

`onSynthesizeText` is 890 CFR lines wrapped in 41 nested labelled blocks, and the mix and
multilingual branches share loop tails that CFR renders as `break block145` / `break
block146` jumping **into the other branch's loop increment**. That is unreadable as
written, so this pass was done from `smali18/smali/com/vnspeak/autotts/AutoTtsService.smali`
(apktool 2.9.3 on the 5.7.7.18 APK), 1926 instructions, plus `AutoTtsService$a`,
`AutoTtsService$e` and `AutoTtsService$e$a`. Reason recorded per rule 7.

### The mode gate

```
lang = request.getLanguage()                      // reassigned to iso3(forcedLocale) by the [AutoTTS:] prefix
if (((lang.equals("zxx") && S != 1) || S == 2 || S == 3) && !fixed)  -> auto / google
else if (S == 1 && !fixed)                                          -> dual
else if (S == 4 && !fixed)                                          -> mix
else if (S == 5 && !fixed)                                          -> multilingual
else                                                                -> none / fixed
```

Ours computes exactly this as `effectiveMode`, in the same order.

### Auto / Google

Per `e0.g` span: if `.b()` is `"unknown"` → `clsCLD2.b`, log `Cld2: <lang> '<text>'`, cut to
2 chars. Then **`c3.e.c(lang)` is applied whether or not the span was detected** — a raw
LocaleSpan language goes through it too — null → `G`. Then `M(lang)`; empty or `"Disable"`
→ `G`. `.e(lang)` writes it back. After the loop, `onLoadLanguage(Q[0].b())`, log
`load <r>`, and on -1/-2 the **misspelled** `"Languge is not supported: "` + `K(cb,2)`.

### Dual

`Q.addAll(d0.t(text, I, K, M))`, then a single switch on **`Q[0].a()`** — the segment type,
which is real here because `d0.t` builds `e0(text,int)`:
`1 -> "eng"` (log `language: eng`, `K(cb,4)` on failure), `2 -> H`, `3 -> J`, `4 -> L`,
`5 -> N` (all `K(cb,5)`), and **type 0 loads nothing at all**. Q empty → the whole text is
spoken unchanged.

### Mix

`Q.clear()`, then per span:

- `.b()` known and non-empty → `M(lang)`; empty or `"Disable"` → **`lang = G`**; `.e(lang)`;
  `Q.add(span)`.
- otherwise → `d0.t(spanText, I, K, M)` and per segment, **on the segment text, in this
  order**: `d0.n` (number) → `I`, `d0.o` (punctuation) → `K`, `d0.l` (emoji) → `M`, with
  `0` and `1` both giving `O`, `2` giving `P`, `3` giving the specific language, and
  **anything else dropping the segment**; else `clsCLD2.c(segText)` and per run
  `c3.e.c(run.a)` ?: `run.b ? O : P`, then `M(...)` empty/Disable → `run.b ? O : P`.

Tail: `Q[0].b()` is re-detected only when empty or `"unknown"`, then `M(lang)` is logged,
then a type fallback — **both are dead**, because every `e0` in mix is built with the
`(String,String)` constructor and so carries type **-1**. Then
`onLoadLanguage`, misspelled message, `K(cb,7)`. Q empty → no check at all.

### Multilingual

Same span loop with two deliberate differences: a span whose `M(lang)` is empty or
`"Disable"` **falls through to the segmenter** instead of being rewritten to `G`, and the
span's language is never written back. Number segments additionally
`System.out.print("- " + text)`. Tail: Q empty → `"lstLanString is empty!"` + `K(cb,7)`;
otherwise `onLoadLanguage(Q[0].b())` with the **correctly spelled** `"Language is not
supported: "`, and no re-detection, no `M()` re-check and no type fallback.

### The per-chunk advance — `AutoTtsService$e` and `$e$a`

`onDone` posts `$e$a` after **50 ms** only while `Q.size() > 1`; otherwise
`"No more text to read."` + `L(cb,7)`. `$e$a` increments the chunk counter, removes
`Q[0]`, and resolves the next language **by mode**:

| mode | language | failure |
|---|---|---|
| dual | `Q[0].a()`: 1→`"eng"`, 2→`H`, 3→`J`, 4→`L`, 5→`N`, **other → no load, language stays `"eng"`** | `L(cb,2)` for type 1, `L(cb,3)` for 2-5 |
| mix / multilingual | `Q[0].b()` (re-detect and type fallback are dead), after a logged `M(lang)` | **`K(cb,14)`** — start+done, **no unlock and no `endSynthesis` line** |
| none / auto / google | `Q[0].b()` | `L(cb,4)` |

then `O`/`R`/`N` for speed/volume/pitch on that language, the same bundle strip, `speak 2: `
and `speak(text, QUEUE_FLUSH, bundle, id)`; a non-zero result is `"Speaking failed!!!"` +
`L(cb,5)`, and the whole body is wrapped in one catch → `"onDone Error: "` + `L(cb,6)`.
The first chunk (`$a`) is the same but sets the counter to 1, logs `Current engine: `, the
id and `speak 1: `, and on failure uses `K(cb,12)` **then** `n0(12)`
(`"unlockSynthesis #12"` + unlock) — and its catch is `"onSynthesis Error: "` + `K(cb,14)`
+ `n0(14)`. `K` ignores its int entirely; only `L` and `n0` log one.

### What was wrong on our side, and is now fixed

1. **The first-chunk preflight ran twice** in dual, mix and multilingual — once inside the
   mode branch and again in a shared block afterwards. `onLoadLanguage` switches the engine,
   so this was a duplicated engine switch and a duplicated log on every utterance. There is
   now exactly one call per mode, inside the branch, as in AutoTTS; the shared block only
   covers the none / fixed-prefix path.
2. **Multilingual reported the failure with the misspelled message.** AutoTTS spells it
   correctly there and misspells it in auto, dual and mix. Both now match.
3. **Mix did not log `language: ` and `engine: `** before the load. Added.
4. **Dual preflighted type 0** (a whitespace-only first segment) and mapped it to the
   neutral default; AutoTTS loads nothing and keeps `"eng"`. Fixed in the preflight and in
   `effectiveLang`.
5. **The per-chunk advance was mode-blind**: one `onLoadLanguage(next.lang)` with
   `endSynthesis #4` for every mode. It is now the table above, including the fact that a
   mix/multilingual failure calls `K` and therefore **does not release
   `onSynthesizeText`** — AutoTTS's actual behaviour, kept deliberately under rule 5.
6. **A speak failure on a later chunk used `K` + unlock instead of `L`**, and the catch
   used the first-chunk message and code. Both now branch on `first`, and the per-chunk
   advance is wrapped in the single catch AutoTTS has.
7. **`M`'s trace line dropped the disabled test.** AutoTTS logs `- <iso> <engine>` only when
   the engine is non-empty, not `"disable"` **and the entry is not disabled**.

### Checked and equal, no change needed

`e0.g` including `prevEnd = end + 1` and `getSpans(0, len-1)`; the `d0` patterns read as raw
constants from smali (` -⁯`, the ASCII set without a backslash in pattern `c`,
with one in `b`); `wholeSegmentKind` vs `d0.n`/`d0.o`/`d0.l` and their order, and
`segmentTypeOf` vs `d0.j`'s different order (punctuation before number); `clsCLD2.c` and its
empty-text, single-character and null-array paths; `M` vs `engineFor` including the `S == 3`
short-circuit; `N`/`O`/`R` as pitch/speed/volume; the `q.get()` break placement in all three
span loops; `K` vs `L` vs `n0`; the common tail's bundle strip, `X`/`W` flags, 50 ms post
and `wait()` loop. Mix mode never keeps a two-letter LocaleSpan language (`M` cannot match
one, so it becomes `G`), and multilingual's "span has an engine" arm is unreachable for the
same reason — on both sides.

## 26. CLD2 / CLD3 parity audit (2026-08-12)

The rule for this row is not AutoTTS parity — AutoTTS has no CLD3 — but the invariant in
`CLAUDE.md`: **wherever CLD2 makes a detection, the switch must be able to put CLD3 there
instead, working the same way.** This is the audit of that.

### Every detection site

`grep -n 'CLD2::\|cld3DetectRaw\|detectWindowLang'` over `tts_engine_core.cpp` gives
exactly **two** places a detector is invoked, and both have both arms:

| # | C++ | AutoTTS equivalent | Reached from | CLD2 arm | CLD3 arm |
|---|---|---|---|---|---|
| 1 | `detectWindowLang()` in `detectLanguageFull` | `clsCLD2.b` → `nativeGetLanguage` | auto / google, per 64-UTF-16-unit window | `DetectLanguageSummaryV2` | `cld3DetectRaw` |
| 2 | `emitScriptSpan()` in `nativeGetLanguages` | `clsCLD2.c` → `nativeGetLanguages` | mix and multilingual, per script span | `ExtDetectLanguageSummary` | `cld3DetectRaw` |

Nothing else detects. `processDirect` / `buildMixChunks` only segments — its `useCld3` and
`disableAdvancedDetection` parameters are inert, kept so the JNI signature carries the
intent; `segmentKind` is pure text classification; and the two post-detection stages,
`n.n()` (`detectOkIso3Set`) and `a.e(cp, m.f)` (`scriptLangForCpFiltered`), are
detector-independent and shared by both arms.

On the Kotlin side there are likewise only two entry points — `detectLanguage()` into site 1
and `detectLanguageRuns()` into site 2 — and both pass `useCld3Flag`. The empty-text guard
and the `quickCharacterFlag` single-character guard sit in Kotlin, ahead of either arm, so
they apply to both. `use_cld3` loads, persists and reloads on exactly the same path as the
other five Advanced flags.

### Already symmetric, verified line by line

Site 1 gates on reliability in both arms (unreliable → `"UNKNOWN"`, which is what
`clsCLD2.b` tests for); site 2 gates on reliability in neither, so both return a best guess;
the 1024-byte cap with the UTF-8 boundary back-up in site 2 and the 64-unit windowing with
`winStart = winStart + 64` in site 1 are shared code ahead of the branch;
`disableAdvancedFlag`'s short-circuit and the `n.n` → `a.e` fallback chain run after the
branch, identically; the log lines differ only in their `[CLD2]` / `[CLD3]` prefix; and the
two unknown sentinels — CLD2's `"un"` and CLD3's `"und"` — both fail `IsoCodes.toIso3` and
`toIso3` in C++, so both fall through to the script fallback rather than being spoken.

### Two asymmetries found and fixed

1. **The hint snapshot was not the same one.** AutoTTS calls `clsCLD2.f(c3.n.f)` **once**,
   in `AutoTtsService.onCreate` (`AutoTtsService.java:1659`), so CLD2's hint list is frozen
   at service start — and our `setLanguageHints` call sits in `onCreate` too, matching it.
   CLD3's bias, however, read `enabledLangSet`, which `refreshEnabledLangs()` rewrites on
   **every** `onSynthesizeText`. Flipping the switch therefore changed more than the
   detector: CLD3 saw a live language set where CLD2 saw a frozen one. `cld3DetectRaw` now
   parses the same frozen `languageHintList` the CLD2 arm hands to `CLD2::CLDHints`, so both
   detectors read one snapshot taken at the same moment. `enabledLangSet` stays live where
   it belongs — the `a.e(cp, m.f)` script-family filter, which AutoTTS also evaluates at
   detection time.

2. **The hint match was tag-sensitive.** CLD3 returns script-tagged codes (`zh-Hant`,
   `zh-Hans`) while the hint set holds bare ISO 639-1 (`zh`), so a hinted Chinese candidate
   never matched and the bias silently did nothing for exactly the languages that need it.
   The lookup now compares `baseLanguageTag(candidate.language)` — cut at the first `-`/`_`,
   lowercased, the same normalisation `IsoCodes.normalizeTag` applies downstream. The value
   returned is still the full tag, which is what CLD2 would have handed back.

## 27. The rest of the app — manifest, TTS-framework entry points, small classes (2026-08-12)

Everything outside `onSynthesizeText` that the system, the TTS framework or another app can
reach, read against 5.7.7.18.

### Manifest and the engine declaration

`res/xml/tts_engine.xml` is a bare `<tts-engine android:settingsActivity="…">`; ours points
at `MainActivity` where theirs points at `NewSettingsActivity`. The `<queries>` block (four
TTS intents), all five `uses-permission` entries we share, the service block down to
`accessibilityEventTypes`, `accessibilityFlags`, `canRetrieveWindowContent`,
`foregroundServiceType`, the `priority="100"` intent filter and the `android.speech.tts`
meta-data, both `Theme.NoDisplay` activities, the launcher activity with
`AppTheme.NoActionBar`, and the `FileProvider` with the same two paths (`files-path
logs/`, `cache-path shared/`) all match.

What AutoTTS has and we do not is licence and Play packaging only — `CHECK_LICENSE`,
`com.pairip.application.Application`, `LicenseActivity`,
`PlayCoreDialogWrapperActivity`, the `com.google.android.gms.version` meta-data and the
stamp/splits/derived-apk meta-data — plus entries the AndroidX manifest merger adds by
itself (`CoreComponentFactory`, the `DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION` pair,
`InitializationProvider`, `ProfileInstallReceiver`). Those are carve-outs or generated.

**One genuine difference, now matched:** AutoTTS ships
`android:extractNativeLibs="true"`; we had `false`. Set to `true`, with
`packaging { jniLibs { useLegacyPackaging = true } }` in `build.gradle.kts` so AGP 8
actually honours the attribute rather than overwriting it.

### The TTS framework entry points

`CheckVoiceData` — `n.i(null, true)` into `availableVoices`, `setResult(1)`, `finish()`;
ours is `LangStore.availableLanguagesFor(null, true)` with `CHECK_VOICE_DATA_PASS`, and
`availableLanguagesFor` matches `n.i` including the `pkg != null && !enginePkgs.contains(pkg)
|| disabled` skip and the `"Name (iso3)"` shape of the non-iso3 branch.

`GetSampleText` — locale from the `"language"` extra else the default, `f0.a(getISO3Language())`,
and on a miss `"Sorry. Sample text for language " + locale.getDisplayName(new Locale("eng"))
+ " is missing."`. **Ours read `Locale("enginePkg")`** — a stale global rename had eaten the
`"eng"` literal, so the display name came back in whatever locale that garbage resolved to.
Fixed. The Voices-tab Test button (`c3.k.Y2`) builds the same string and already had it
right.

`f0` itself: 340 entries, key for key and value for value identical to `SampleTexts` — the
iso3 keys plus the iso2 and locale-qualified ones (`en_US`, `eng_USA`, `fra_CAN`, `zho_TWN`,
`ru_LV`, …). Only `getISO3Language()` ever reaches it from either call site, so the
non-iso3 keys are unreachable in AutoTTS too; they are present on both sides regardless.

The service overrides all match: `onGetLanguage`, `onGetDefaultVoiceNameFor` (returns the
language it was handed), `onGetVoices` (`Voice(name, Locale(name), 400, 100, false,
HashSet())`), `onIsLanguageAvailable` (`0` / `-2`), `onIsValidVoiceName` (`0` / `-1`),
`onLoadVoice` (records the name, parses the locale, delegates to `onLoadLanguage`, accepts
0/1/2), `onStartCommand` returning `START_STICKY`, and `onStop` → `m0(TRUE)`. The
write-only `k0` static is mirrored by `lastLoadedVoiceName`.

### The small classes

`c3.f` (language row) is field for field our `LangEntry`. `c3.k0` (engine wrapper) matches
`EngineWrapper` down to the `ThreadPoolExecutor(0, 5, 60s, LinkedBlockingQueue)` with a
daemon `"TtsStop"` thread factory, so `stop()` and `shutdown()` are asynchronous on both
sides, and the retry gate `k == 0 || ((nanoTime - j)/1e6 > 3000 && k < 10)` is reproduced
exactly in `restoreEngine`. `c3.b0.g()`'s ellipsis abbreviation, including the `> 15`,
`>= 20` and trailing `>= 15` quirks, is our `abbreviateEngineName`. `c3.v`'s package →
friendly-name map is 46 entries on both sides, identical. `c3.u`, the required-engines
dialog, matches down to the misspelled `"Requried TTS Engines"` title, the 48/48/48/24 and
0/16/0/16 paddings, the `-11751600` / `-6381922` colours, the 0.5f disabled alpha,
`setCancelable(false)` and the Install → "Installing…" → Installed state machine.
`c3.w` (is a package installed), `c3.a0` (version name and code), `c3.c` (the emoji
alternation), `c3.c0` (all-whitespace), `c3.o` (engine info) and the `b`/`g`/`h`/`i`/`j`/
`h0`/`i0`/`j0`/`l`/`q`/`r`/`s`/`t` lambda shims carry no behaviour of their own beyond what
is already ported. `c3.m` is the five-tab pager adapter — ours has three tabs under the UI
carve-out.
