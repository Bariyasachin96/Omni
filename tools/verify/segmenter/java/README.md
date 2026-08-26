# The AutoTTS side of the segmenter proof

These files are **AutoTTS's own code**, lifted out of
`autotts_reference/v5.7.7.26/decompiled_java/` so that `c3.d0.t` can be run on a
desktop JVM and its output compared with ours. They are here as *evidence*, not
as something to maintain: if the reference tree is ever updated, re-extract.

| file | origin |
|---|---|
| `d0.java` | `c3/d0.java` — the segmenter |
| `clsCLD2.java` | `com/vnspeak/autotts/clsCLD2.java`, **only** `a(int)` and `b(String)`, the Unicode normaliser `t()` calls |
| `e0.java` | `c3/e0.java` — one segment; `g()` removed, it needs `android.text` |
| `c0.java` | `c3/c0.java` — the all-whitespace test |
| `EmojiRe.java` | `c3/c.java` — the emoji regex |
| `n.java` | a stub for `c3.n`: `e(Locale)` and the enabled-language set `f` |
| `AutoTtsService.java` | a stub for the statics `t()` reads: `T`, `I`, `Q`, `d0`, `e0`, `f0` |
| `Main.java` | ours — reads the TSV, calls `d0.t`, prints `type:'text'` records |

## Why they are not verbatim

CFR's output **does not compile**. It reuses one local for two different types,
which javac rejects even though the bytecode was valid. Every departure is
listed here so it can be re-checked:

| where | what CFR emitted | what is here |
|---|---|---|
| `d0` static block | one variable used as both `StringBuilder` and `String[]` | two variables, `emojiSb` and `stringArray` |
| `d0.a()` | `String[] stringArray2 = j;` where `j` is a `HashSet` | `HashSet cached = j;` |
| `d0.e()` | the loop variable reused as `HashSet` and `String` | a separate `String kw` |
| `d0.f()`, `d0.g()` | `Object` reused for `StringBuilder`, `CharSequence` and `String` | separate `sb` and `s` |
| `d0.r()` | one name used as both `String` and `String[]`, and the digit scan rendered as an unreadable rotation of six temporaries | rewritten with named variables — `digits`, `run`, `maxRun`, `hasComma`, `hasDot`, `hasCurrency` — from the same control flow |
| `d0.t()` | parameter typed `String`, later used as `ArrayList` | parameter widened to `Object`; the casts CFR already emits do the rest |
| `clsCLD2.b()` | returns a `CharSequence` where the signature says `String` | `.toString()` |
| `c3.c` | class name `c` collides with `d0`'s field `c` (a `Pattern`) | renamed `EmojiRe` |

`d0.r()` is the only one rewritten rather than merely re-typed. It was checked
against the original branch by branch; the phone-shape cases in `gen_cases.py`
exercise it directly.

## Regenerating

    cd autotts_reference/v5.7.7.26
    # see README.md there for the exact dex2jar + CFR invocation

then re-apply the table above. Nothing else in these files was touched.
