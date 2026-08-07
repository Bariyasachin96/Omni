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

### Still to do

- [ ] Read all four mode branches of `onSynthesizeText` old-vs-new line by line — dual,
      mix, multilingual and the auto/google tail — not just locate the `d0.t` call sites.
- [ ] Port the continuation-path type fallback (1231-1264) and the `S == 4 || S == 5`
      gate at 1208.

Do **not** treat §9's "onSynthesizeText: ported" as settled until this section is closed.

