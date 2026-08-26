# The AutoTTS side of the script-family proof

`ScriptFam.java` is **AutoTTS's `com/vnspeak/autotts/a.java`**, lifted out of
`autotts_reference/v5.7.7.26/decompiled_java/` so its answers can be compared
with ours over every Unicode code point. `Main.java` and `Fam.java` are ours.

| file | what it prints, one line per code point |
|---|---|
| `Main.java <langs>` | the resolved language — what `clsCLD2.d`'s fallback would use |
| `Fam.java` | `primary\|member,member,…` for the unfiltered family, **in iteration order** |

## Departures from the decompile

| where | what CFR emitted | what is here |
|---|---|---|
| class name | `a`, which collides with its own inner class `a` | outer renamed `ScriptFam`; the inner `a` is untouched |
| `a.f()` | one `Object` used as `Set`, `Iterator` and `String` | named `members`, `scan`, `kept`; same control flow |
| `a.g()` | the `Set` parameter reassigned to its own `Iterator` | a separate `Iterator it` |
| every `new HashSet<>(Arrays.asList(…))` | verbatim | routed through `androidSet(…)` — **see below** |

## `androidSet` is not a cosmetic change, and removing it invalidates the proof

Android's libcore keeps the classic constructor:

    public HashSet(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

**JDK 19 replaced it** with `HashMap.newHashMap(c.size())`, i.e. capacity
`ceil(size/0.75)`. For a **12-element** family that is table **32** on Android
and **16** on JDK 19+, and the iteration orders differ:

    Devanagari, table 32 (Android):  hi,new,mr,kok,bh,bho,awa,sa,mai,ne,raj,hne
    Devanagari, table 16 (JDK 21):   hi,new,mai,mr,kok,bh,ne,bho,awa,raj,sa,hne

Only the two 12-member families — Devanagari and the digit family — are
affected, and the order is exactly what `a.f` and `clsCLD2.d` walk to pick a
language. The first run of this proof reported a difference for those two
families; **our C++ was right and the harness was wrong**. `androidSet` restores
the Android form on whatever JVM the harness happens to run on.

Any future Java harness that compares collection order must do the same.
