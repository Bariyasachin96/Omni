# AutoTTS 5.7.7.18 → 5.7.7.26 — what actually changed

versionCode `90000245` → `90000254`. `minSdkVersion` 26 and `targetSdkVersion` 36 in
**both**, so no SDK move despite the version jump.

Method: every file that `diff -rq` flagged was read. Most of those diffs are decompiler
variable-naming noise (`stringArray2` becoming `object3` and so on) — those are listed at
the bottom under "no behavioural change" so nobody re-reads them. What follows is only what
the app actually does differently.

---

## 1. Smart number reading now groups digits — new pref `smart_number_reading_group_size`

`c3/d0.java`, method `s(String)`. This is the function that inserts spaces into a number so
the engine spells it out instead of reading "four hundred and twelve".

**5.7.7.18** — a space between every pair of adjacent digits:

```java
public static String s(String string) {
    StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
    int n3 = 0;                                       // previous char
    for (int i3 = 0; i3 < string.length(); ++i3) {
        char c3 = string.charAt(i3);
        if (c3 >= '0' && c3 <= '9' && n3 >= 48 && n3 <= 57) {   // prev was a digit too
            stringBuilder.append(' ');
        }
        stringBuilder.append(c3);
        n3 = c3;
    }
    return stringBuilder.toString();
}
```

**5.7.7.26** — a space every N digits, N from a new setting:

```java
public static String s(String string) {
    int n3 = Math.max(1, AutoTtsService.f0);           // group size, clamped to >= 1
    StringBuilder stringBuilder = new StringBuilder(string.length() * 2);
    int n4 = 0;                                       // digits emitted in the current run
    for (int i3 = 0; i3 < string.length(); ++i3) {
        char c3 = string.charAt(i3);
        if (c3 >= '0' && c3 <= '9') {
            if (n4 > 0 && n4 % n3 == 0) {
                stringBuilder.append(' ');
            }
            ++n4;
        } else {
            n4 = 0;                                   // any non-digit restarts the run
        }
        stringBuilder.append(c3);
    }
    return stringBuilder.toString();
}
```

With group size 1 this is byte-for-byte the old behaviour, which is why the default is 1.

**The setting.** `c3/n.java`:

```java
AutoTtsService.f0 = context.getInt("smart_number_reading_group_size", 1);   // load
context.putInt("smart_number_reading_group_size", AutoTtsService.f0);       // persist
```

**The control.** `c3/k.java:1101-1105` — a `Spinner` with exactly three entries, and it is
greyed out unless Smart number reading itself is on:

```java
this.D0 = (Spinner)this.i0.findViewById(2131231222);
checkBox = new ArrayAdapter(this.n1(), 17367048, (Object[])new String[]{"1", "2", "3"});
this.D0.setAdapter((SpinnerAdapter)checkBox);
this.D0.setEnabled(AutoTtsService.e0);          // e0 == smart_number_reading
this.D0.setSelection(AutoTtsService.f0 - 1);
```

and on selection `AutoTtsService.f0 = position + 1` (`k.java:897`). So the stored value is
1, 2 or 3 — never 0 — and `Math.max(1, ...)` in `s()` is belt-and-braces for an older
stored 0.

**The description string changed with it:**

```
5.7.7.18  Reads phone numbers and codes digit by digit.
5.7.7.26  Reads phone numbers and codes by group of digits.
```

Plus a new label `smart_number_reading_group_size` = `Group size`.

---

## 2. A clock time is no longer treated as a phone number

`c3/d0.java`. New field, and every other pattern shifted one letter to make room
(`b`→`c`, `c`→`d`, `d`→`e`, `e`→`f`, `f`→`g`, `g`→`h`, `h`→`i`, `i`→`j`):

```java
b = Pattern.compile("[0-9]{1,2}:[0-9]{2}(:[0-9]{2})?");
```

and at the very top of `r(String)` — the "is this a phone number / code?" test that gates
the digit spacing:

```java
public static boolean r(String string) {
    ...
    stringArray = string.trim();
    if (stringArray.isEmpty()) {
        return false;
    }
    if (b.matcher((CharSequence)stringArray).matches()) {     // NEW
        return false;
    }
```

So `10:30` and `10:30:45` are read as a time, not spelled out digit by digit. Note the
pattern is deliberately unanchored-looking but used with `matches()`, so it must be the
**whole** trimmed string.

---

## 3. `:` counts as a number character

`c3/d0.java`, method `m(String)` — the "does this look numeric enough" helper (it returns
true when at least 4 digits are present and every other character is one of a small set):

```java
5.7.7.18   if (c3 == ',' || c3 == '.' || c3 == '%' || c3 == '$') break block5;
5.7.7.26   if (c3 == ',' || c3 == '.' || c3 == ':' || c3 == '%' || c3 == '$') break block5;
```

`m()` is one of the two ways a segment can still be spelled out when `r()` said no — the
other being a nearby keyword from the `i` table (`otp`, `pin`, `phone`, …).

---

## 4. Fancy Unicode letters are normalised to ASCII before detection — the big one

Two brand-new methods in `com/vnspeak/autotts/clsCLD2.java`.

**`clsCLD2.a(int codepoint)`** maps a decorated Latin codepoint back to plain ASCII:

| Range | What it is | Maps to |
|---|---|---|
| `0x1D400`–`0x1D6A3` | Mathematical Alphanumeric Symbols (𝐀 𝑨 𝔸 𝓐 …) | `A`–`Z` / `a`–`z`, via `(cp - 0x1D400) % 52` |
| `0x1D7CE`–`0x1D7FF` | Mathematical digits | `0`–`9`, via `% 10` |
| `0xFF10`–`0xFF5A` | Fullwidth forms (０ Ａ ａ) | `cp - 0xFF00 + 0x20` |
| `0x2460`–`0x24EA` | Circled digits and letters (① Ⓐ ⓐ) | digit / letter |
| `0x1F130`–`0x1F169` | Squared and negative-circled letters (🄰 🅐) | `A`–`Z` |
| `0x1D00`–`0x1DBB`, `0x2C7x` | Small-capital and modifier letters (ᴀ ᴃ ꟁ) | `A`–`Z` |
| `0x2070`–`0x209C`, `¹ ² ³` | Super/subscripts | digits and `a e o x h k l m n p s t` |
| `0x2102`–`0x2134` | Blackboard bold / script letters (ℂ ℍ ℕ ℤ ℒ ℛ) | `C H N Z L R` … |

Anything else is returned unchanged.

**`clsCLD2.b(String)`** applies it, with a fast path that returns the original string
untouched when no such character is present:

```java
public static String b(String string) {
    ...
    while (true) {
        charSequence = string;
        if (n5 >= string.length()) break block2;          // nothing fancy -> return as-is
        n3 = string.charAt(n5);
        if (n3 == 55349 || n3 == 55356 || n3 >= 8450 && n3 <= 8500 || ... ) break;
        ++n5;
    }
    charSequence = new StringBuilder(string.length());
    n3 = string.length();
    for (n5 = n4; n5 < n3; n5 += Character.charCount(n4)) {
        n4 = string.codePointAt(n5);
        ((StringBuilder)charSequence).appendCodePoint(clsCLD2.a(n4));
    }
    charSequence = ((StringBuilder)charSequence).toString();
    ...
}
```

(`55349` = `0xD835` and `55356` = `0xD83C` are the surrogate leads for the two
supplementary-plane blocks, so the scan works on UTF-16 chars but the conversion loop works
on code points.)

**Where it is applied — three places:**

- `clsCLD2.e(...)` (the mix-chunk list, `c` in 5.7.7.18): first line of the body is now
  `stringArray = clsCLD2.b(stringArray);`
- `clsCLD2.f(...)` — the new method, below.
- `c3/d0.java:459`, inside the segmenter `t(...)`:
  `object = d0.q(clsCLD2.b(text.replaceAll("[   ﻿]", " ").replaceAll("\\s+", " ")))`

Why it matters for a screen reader: bold/italic/script Unicode is everywhere on social
media, and CLD2 sees those code points as symbols, not letters, so a whole 𝓯𝓪𝓷𝓬𝔂 sentence
used to come back `UNKNOWN` and fall through to the preferred language. Normalising first
means it detects as ordinary text.

**Note it does NOT touch `clsCLD2.d(...)`** (the main per-segment detect, `b` in 5.7.7.18).
Only the mix path, the new aggregate path, and the segmenter.

---

## 5. New second-chance detection: `clsCLD2.f(...)`, "the language with the most text wins"

A completely new entry point built on the **existing** native `nativeGetLanguages(String,
int, int, Object)` — the signature is unchanged from 5.7.7.18, so nothing new is needed from
the `.so` for this.

The native call returns a flat array of triples `[lang, isLatinFlag, textChunk, lang,
isLatinFlag, textChunk, …]`. `f` aggregates:

```java
// key = "<lang>|<isLatinFlag>", value = total length of text attributed to that key
while ((n6 = n3 + 2) < stringArray.length) {
    object2 = stringArray[n3] + "|" + stringArray[n3 + 1];
    n4 = (existing count for object2, or 0);
    object.put(object2, n4 + stringArray[n6].length());
    if (!hashMap.containsKey(object2)) {
        hashMap.put(object2, new boolean[]{"1".equals(stringArray[n3 + 1])});
    }
    n3 += 3;
}
```

then picks a winner in one pass, tracking two candidates:

- `stringArray` / `n4` — the highest total **including** `un|…`;
- `object` / `n5` — the highest total **excluding** keys that start with `"un|"`.

`object` wins if it is non-null, otherwise it falls back to `stringArray`; if both are null
the result is `new a("un", clsCLD2.g(string), string)`. So **a real language beats
`unknown` even when `unknown` covers more of the text**, and only among real languages does
sheer volume decide.

Short-circuits, in order:
1. null/empty input → `new a("un", false, "")`;
2. after normalisation, a single character with quick-character-read on → `un`;
3. native returned `null` or fewer than 3 entries → `un`;
4. exactly 3 entries (one triple) → that language directly, no aggregation.

**Where it is used — auto and Google mode only**, `AutoTtsService.onSynthesizeText`:

```java
object3 = object = ((e0)((ArrayList)object6).get(n5)).b();
if (((String)object).equalsIgnoreCase("unknown")) {
    object = object3 = clsCLD2.d(segmentText, o0, j0, this.h);          // the old detect
    if (((String)object3).equalsIgnoreCase("unknown")) {
        object = clsCLD2.f(segmentText, o0, j0, this.h).a;              // NEW fallback
    }
    ...
}
```

So the chain is now **segment's own language → `clsCLD2.d` → `clsCLD2.f`**, where 5.7.7.18
stopped after `d`. It only runs when both earlier steps say `unknown`, so it costs nothing
on ordinary text.

---

## 6. Native: the Devanagari danda is handled specially in `getLanguageSpans`

`lib/arm64-v8a/libcld2.so` changed. Established rather than assumed:

- same file size (6884088), same 494 global `FUNC` symbols, same dynamic section, same
  string table;
- **`.rodata` is byte-identical** (sha256 `932592d8…`) — the CLD2 model tables did not move;
- only `.text` differs, and only inside `getLanguageSpans` (the function behind
  `nativeGetLanguages`). Everything after `0x653e50` shifts by exactly 12 bytes.

Disassembling that function in both and diffing with addresses stripped gives one real
insertion:

```asm
and  w10, w8, #0x1ffffe
cmp  w10, #0x964
b.eq <span-break path>
```

`w8` is the code point. `cp & 0x1FFFFE == 0x964` is true for exactly **U+0964 DEVANAGARI
DANDA `।`** and **U+0965 DEVANAGARI DOUBLE DANDA `॥`** (the mask clears the low bit, so the
pair collapses to one comparison). They are now taken out of the Devanagari script run
instead of extending it — i.e. a danda ends a span the way a full stop does.

The rest of the `.text` diff is the compiler reordering three identical script-range tests
(`0x370`/`0x530`/`0x590` → ids 7/8/9) and hoisting one `adrp`+`add` pair. Same instructions,
different order.

---

## 7. `c3/n.java`'s language list is now synchronised

Nine accessors that walk the static list `c3.n.c` gained `synchronized (c) { … }`:
`n(String)`, the index lookup, `i(...)`, `j(...)`, `k(...)`, `l(...)`, `m(...)`, `w(Context)`
and the two persist helpers. Behaviour is unchanged single-threaded; this is AutoTTS
guarding the list against the synthesis thread reading it while the settings UI rebuilds it.

---

## 8. Advanced tab gained an "Information" section

New strings `app_information` = `Information`, `app_version` = `Version:`,
`app_build` = `Build number:`; new layout rows at the bottom of `fragment_advanced.xml`;
and `c3/k.java:1187-1188` fills them with literals:

```java
((TextView)this.i0.findViewById(2131230812)).setText(String.valueOf(90000254));
((TextView)this.i0.findViewById(2131230813)).setText("5.7.7.26");
```

---

## 9. Manifest

`android:extractNativeLibs` `true` → `false`, and the
`com.android.dynamic.apk.fused.modules` meta-data moved to the end of `<application>`.
Nothing else.

---

## Files that differ but do NOT change behaviour

Checked and dismissed, so they are not re-read next time. In every one of these the string
literals are identical between versions (`diff <(grep -o '"[^"]*"' old | sort) <(… new …)`
is empty) and the diff is CFR naming a local differently:

`c3/d.java`, `c3/e.java`, `c3/e0.java`, `c3/g0.java`, `c3/l0.java`, `c3/v.java`,
`com/vnspeak/autotts/a.java`, `com/vnspeak/autotts/CheckVoiceData.java`,
`com/vnspeak/autotts/LicensesDialogFragment.java`.

Two exceptions inside that group, both mechanical:
- `c3/e0.java` — `AutoTtsService.U` became `V`, part of the static rename below.
- `c3/m.java` — the five resource ids shifted by 4 (new strings were added ahead of them)
  and `c3.k.L2` became `M2`.

---

## The obfuscated-name map moved again

Every `AutoTtsService` static letter from `G` onward shifted by one, and a genuinely new one
appeared at the end.

| Concept | 5.7.7.18 | **5.7.7.26** |
|---|---|---|
| auto-mode language | `G` | **`H`** |
| dual-mode language | `H` | **`I`** |
| number mode int | `I` | **`J`** |
| number specific language | `J` | **`K`** |
| punctuation mode int | `K` | **`L`** |
| punctuation specific language | `L` | **`M`** |
| emoji mode int | `M` | **`N`** |
| emoji specific language | `N` | **`O`** |
| mixed latin language | `O` | **`P`** |
| mixed non-latin language | `P` | **`Q`** |
| reading mode int | `S` | **`T`** |
| engine package list | `T` | **`U`** |
| locale spans | `U` | **`V`** |
| dedicated engines | `V` | **`W`** |
| strip audio attributes | `W` | **`X`** |
| force accessibility stream | `X` | **`Y`** |
| keep-alive | `Y` | **`Z`** |
| show notification | `Z` | **`a0`** |
| disable advanced detection | `a0` | **`b0`** |
| quick character read | `b0` | **`c0`** |
| punctuation in flow | `c0` | **`d0`** |
| smart number reading | `d0` | **`e0`** |
| **smart number group size (NEW, int, default 1)** | (n/a) | **`f0`** |
| voice list | `e0` | **`g0`** |

`clsCLD2` methods were renamed too, and two were inserted at the front:

| 5.7.7.18 | **5.7.7.26** | What it does |
|---|---|---|
| (n/a) | **`a(int)`** | decorated code point → ASCII |
| (n/a) | **`b(String)`** | apply `a` across a string, fast path if nothing to do |
| `a(String)` | **`c(String)`** | first-code-point helper |
| `b(String,int,int,Context)` | **`d(...)`** | main per-segment detect |
| `c(String,int,int,Object)` | **`e(...)`** | mix-chunk list (now normalises first) |
| (n/a) | **`f(String,int,int,Object)`** | NEW aggregate detect, most-text-wins |
| `d(String)` | **`g(String)`** | is-latin test |
| `e(char)` | **`h(char)`** | unknown-char test |
| `f(Set)` | **`i(Set)`** | push language hints to the native side |

`c3/d0.java`'s fields all shifted one letter as well (`b`→`c` … `i`→`j`) to make room for
the new time pattern at `b`.

`c3` class names themselves did **not** move this time — `c3.n` is still the settings store,
`c3.k` still the settings fragment, `c3.d0` still the segmenter.
