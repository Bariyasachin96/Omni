#!/usr/bin/env python3
"""Every character of the Unicode the app is generated from, as the detector's
input (tools/verify/allchars).

    cases.py <ucd dir> <own.tsv> <out dir>

own.tsv is script<TAB>iso2<TAB>iso3 of every living script's language (the
real IsoCodes, Check.kt prep). Writes
  cases.tsv  "@set" lines (a set of enabled languages) each followed by the
             texts to send under it: id<TAB>modes<TAB>text
  meta.tsv   id<TAB>cp<TAB>gc<TAB>variant<TAB>set<TAB>owning scripts<TAB>acceptable iso3s

A character's OWNING scripts are its Script, or for a Common/Inherited one the
scripts its Script_Extensions name; none for the truly neutral ones (ASCII
digits, most punctuation and symbols, emoji), which follow the reading modes.
Every character is sent alone under the owner's set {hi, en, gu}; and for each
language that writes one of its owning scripts, alone and after a letter of its
script, with that language enabled too. The acceptable answers are the enabled
languages that write one of the character's owning scripts.
"""
import os, sys

ucd, own_tsv, out = sys.argv[1:4]


def rows(path):
    for line in open(path, encoding="utf-8"):
        line = line.split("#", 1)[0].strip()
        if not line:
            continue
        f = [x.strip() for x in line.split(";")]
        lo, _, hi = f[0].partition("..")
        yield int(lo, 16), int(hi or lo, 16), f[1:]


short2long = {}
for line in open(os.path.join(ucd, "PropertyValueAliases.txt"), encoding="utf-8"):
    f = [x.strip() for x in line.split("#")[0].split(";")]
    if f[0] == "sc" and len(f) > 2:
        short2long[f[1]] = f[2]
gc, sc, scx = {}, {}, {}
for lo, hi, v in rows(os.path.join(ucd, "DerivedGeneralCategory.txt")):
    for c in range(lo, hi + 1):
        gc[c] = v[0]
for lo, hi, v in rows(os.path.join(ucd, "Scripts.txt")):
    for c in range(lo, hi + 1):
        sc[c] = v[0]
for lo, hi, v in rows(os.path.join(ucd, "ScriptExtensions.txt")):
    names = [short2long[s] for s in v[0].split()]
    for c in range(lo, hi + 1):
        scx[c] = names

own = {}        # script -> (iso2, iso3)
for line in open(own_tsv, encoding="utf-8"):
    f = line.rstrip("\n").split("\t")
    if len(f) >= 3 and f[1]:
        own[f[0]] = (f[1], f[2])
OWNER = [("hi", "hin"), ("en", "eng"), ("gu", "guj")]
writes = {}     # iso3 -> scripts that language is written in (as far as this test goes)
for s, (i2, i3) in own.items():
    writes.setdefault(i3, set()).add(s)
writes.setdefault("eng", set()).add("Latin")
writes.setdefault("jpn", set()).add("Han")          # kanji
iso2of = {i3: i2 for (i2, i3) in OWNER}
for s, (i2, i3) in own.items():
    iso2of.setdefault(i3, i2)

# a base letter of each script, for the "after a letter" variant
first_letter = {}
for c in sorted(sc):
    s = sc[c]
    if s not in first_letter and gc.get(c, "Cn") in ("Lo", "Lu", "Ll", "Lt") and s not in ("Common", "Inherited"):
        first_letter[s] = c

SKIP_GC = {"Cc", "Cf", "Cs", "Co", "Cn", "Zs", "Zl", "Zp"}
sets = {"owner": [i3 for _, i3 in OWNER]}
cases = {"owner": []}
meta = []
n = 0
for c in sorted(gc):
    g = gc[c]
    if g in SKIP_GC:
        continue
    s = sc.get(c, "Unknown")
    owning = [s] if s not in ("Common", "Inherited", "Unknown") else scx.get(c, [])
    ch = chr(c)

    def add(setname, variant, text, modes):
        global n
        n += 1
        enabled = sets[setname]
        acc = sorted(l for l in enabled if writes.get(l, set()) & set(owning))
        cases.setdefault(setname, []).append("%d\t%s\t%s" % (n, modes, text))
        meta.append("%d\t%04X\t%s\t%s\t%s\t%s\t%s" % (n, c, g, variant, setname, ",".join(owning) or "-", ",".join(acc) or "-"))

    add("owner", "alone", ch, "mad")
    langs = sorted({own[o][1] for o in owning if o in own} | {l for l, w in writes.items() if w & set(owning)})
    for l in langs:
        if l in sets["owner"]:
            name = "owner"
        else:
            name = "+" + l
            sets.setdefault(name, sets["owner"] + [l])
            add(name, "alone", ch, "ma")
        base = next((first_letter[o] for o in owning if o in first_letter and o in writes.get(l, set())), None)
        if base is not None and base != c:
            add(name, "after", chr(base) + ch, "md")

with open(os.path.join(out, "cases.tsv"), "w", encoding="utf-8") as f:
    for name, lines in cases.items():
        en = sets[name]
        f.write("@set\t%s\t%s\teng\tguj\n" % (name, ",".join("%s:%s" % (iso2of.get(l, l), l) for l in en)))
        f.write("\n".join(lines) + "\n")
with open(os.path.join(out, "meta.tsv"), "w", encoding="utf-8") as f:
    f.write("\n".join(meta) + "\n")
sys.stderr.write("allchars: %d texts, %d sets\n" % (n, len(sets)))
