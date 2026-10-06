#!/usr/bin/env python3
"""An independent reading of the UCD files: what every predicate in dump.cpp
must answer, written from the Unicode definitions, not from gen_tables.py.
Compares the dump on stdin, line by line, and counts every difference.
argv: ucd dir, Kotlin UnicodeTables.kt, old-dump (or "-")"""
import sys, os, re
ucd, kt, olddump = sys.argv[1:4]
def rows(f):
    for line in open(os.path.join(ucd, f), encoding="utf-8"):
        b = line.split("#")[0].strip()
        if not b: continue
        x = [y.strip() for y in b.split(";")]; a = x[0].split("..")
        yield int(a[0], 16), int(a[-1], 16), x[1]
def setof(f, val):
    s = set()
    for lo, hi, v in rows(f):
        if v == val: s.update(range(lo, hi + 1))
    return s
sc = {}; scx = {}; gc = {}
for lo, hi, v in rows("Scripts.txt"):
    for c in range(lo, hi + 1): sc[c] = v
for lo, hi, v in rows("ScriptExtensions.txt"):
    for c in range(lo, hi + 1): scx[c] = set(v.split())
for lo, hi, v in rows("DerivedGeneralCategory.txt"):
    for c in range(lo, hi + 1): gc[c] = v
emoji = setof("emoji-data.txt", "Extended_Pictographic") | setof("emoji-data.txt", "Emoji_Component")
bidi = setof("PropList.txt", "Bidi_Control")
# the span slots, by name -- deliberately a different spelling of the same rule
GROUPS = [("Latin","Latn"),("Cyrillic","Cyrl"),("Arabic","Arab"),("Devanagari","Deva"),
          (("Han","Hiragana","Katakana","Bopomofo"),("Hani","Hira","Kana","Bopo")),("Bengali","Beng"),
          ("Greek","Grek"),("Armenian","Armn"),("Hebrew","Hebr"),("Georgian","Geor"),("Gurmukhi","Guru"),
          ("Gujarati","Gujr"),("Oriya","Orya"),("Tamil","Taml"),("Telugu","Telu"),("Kannada","Knda"),
          ("Malayalam","Mlym"),("Sinhala","Sinh"),("Thai","Thai"),("Lao","Laoo"),("Tibetan","Tibt"),
          ("Myanmar","Mymr"),("Khmer","Khmr"),("Ethiopic","Ethi"),("Hangul","Hang")]
longslot = {}; shortslot = {}
for i, (l, s) in enumerate(GROUPS, 1):
    for x in (l if isinstance(l, tuple) else (l,)): longslot[x] = i
    for x in (s if isinstance(s, tuple) else (s,)): shortslot[x] = i
def slot(c):
    if c < 0x80: return 1 if chr(c).isascii() and chr(c).isalpha() else -1
    if sc.get(c) in longslot: return longslot[sc[c]]
    hits = {shortslot[x] for x in scx.get(c, ()) if x in shortslot}
    return hits.pop() if len(hits) == 1 else -1
fold = {}
for line in open(os.path.join(ucd, "UnicodeData.txt"), encoding="utf-8"):
    f = line.split(";")
    m = re.match(r"<(font|circle|wide|super|sub|square|small|narrow)> ([0-9A-F]+)$", f[5])
    if m:
        t = int(m.group(2), 16)
        if chr(t).isascii() and chr(t).isalnum(): fold[int(f[0], 16)] = t
lci = []
nums = [int(x, 16) for x in re.findall(r"0x([0-9A-F]+)", open(kt).read().split("intArrayOf(")[1].split(")")[0])]
ktset = set()
for a, b in zip(nums[::2], nums[1::2]): ktset.update(range(a, b + 1))
old = {}
if olddump != "-":
    for line in open(olddump):
        f = line.rstrip("\n").split("\t"); old[int(f[0], 16)] = f
bad = {k: 0 for k in ("fold", "emoji", "letter", "slot", "latinbuf", "bidi", "family", "kotlin")}
changed = {k: 0 for k in ("fold", "emoji", "letter", "family")}
# what a.java's own branches give each script's generic letter (primary)
FAMPRIMARY = {"Latin":"en","Greek":"el","Cyrillic":"ru","Armenian":"hy","Hebrew":"he","Arabic":"ar","Thaana":"dv",
  "Devanagari":"hi","Bengali":"bn","Gurmukhi":"pa","Gujarati":"gu","Oriya":"or","Tamil":"ta","Telugu":"te","Kannada":"kn",
  "Malayalam":"ml","Sinhala":"si","Thai":"th","Lao":"lo","Tibetan":"bo","Myanmar":"my","Georgian":"ka","Hangul":"ko",
  "Ethiopic":"am","Khmer":"km","Mongolian":"mn","Hiragana":"ja","Katakana":"ja","Han":"zh","Bopomofo":"zh"}
famwrong = 0; lines = 0
for line in sys.stdin:
    f = line.rstrip("\n").split("\t"); c = int(f[0], 16); lines += 1
    o = old.get(c)
    oldfold = int(o[1], 16) if o else c
    # fold: the old (clsCLD2.a) answer wins; else a styling form -> its letter,
    # not Latin-1, not an emoji
    want = oldfold if oldfold != c else (fold[c] if c in fold and c >= 0x100 and c not in emoji else c)
    if int(f[1], 16) != want: bad["fold"] += 1
    e = c in emoji and not (c in (0x23, 0x2A, 0x200D) or 0x30 <= c <= 0x39)
    if (f[2] == "1") != e: bad["emoji"] += 1
    if (f[3] == "1") != (gc.get(c, "Cn") in ("Lu", "Ll", "Lt", "Lm", "Lo") or (c < 0x80 and chr(c).isalpha())): bad["letter"] += 1
    if int(f[4]) != slot(c): bad["slot"] += 1
    lat = (c <= 0x24F or 0x1E00 <= c <= 0x1EFF or 0x2000 <= c <= 0x206F or 0x20A0 <= c <= 0x20CF
           or 0x2C60 <= c <= 0x2C7F or 0xA720 <= c <= 0xA7FF or sc.get(c) == "Latin")
    if (f[5] == "1") != lat: bad["latinbuf"] += 1
    if (f[6] == "1") != (c in bidi): bad["bidi"] += 1
    if (c in ktset) != (sc.get(c) in ("Latin", "Common", "Inherited")): bad["kotlin"] += 1
    if o:
        if o[1] != f[1]: changed["fold"] += 1
        if o[2] != f[2]: changed["emoji"] += 1
        if o[3] != f[3]: changed["letter"] += 1
        if o[4] != f[7]:
            changed["family"] += 1
            if o[4] != "un": famwrong += 1   # the fallback may only fill what was "un"
            elif f[7] != FAMPRIMARY.get(sc.get(c), "un") or gc.get(c, "Cn")[0] not in "LMN": famwrong += 1
bad["family"] = famwrong
print("Unicode predicates over %d code points:" % lines)
for k, v in bad.items(): print("  %-9s %s" % (k, "IDENTICAL to the UCD" if v == 0 else "%d DIFFER" % v))
if old: print("  changed from the old tables: " + ", ".join("%s %d" % kv for kv in changed.items()))
sys.exit(1 if any(bad.values()) else 0)
