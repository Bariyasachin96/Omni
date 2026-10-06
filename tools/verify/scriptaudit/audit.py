#!/usr/bin/env python3
"""Every script of the current Unicode through the real detection path (owner,
2026-10-06: "sabhi lipi ka detection ... sari matra ... ultra max deeply").

For each Script with a living language behind it: every letter (sampled when a
script has more than LETTER_CAP), EVERY combining mark (matras, viramas, bindus,
nuktas, visargas, tone marks ...), every digit -- alone, and each mark on a base
consonant and on a dotted circle -- in mix mode and auto mode, with quick
character read off and on. The Inherited marks (Latin accents, Vedic signs, ZWJ /
ZWNJ, variation selectors) go on a Latin base and a Devanagari base.

Enabled languages mimic a real phone: English + Hindi + Gujarati + the script's
own language; preferred Latin = English, preferred non-Latin = Gujarati (Hindi
when the script IS Gujarati), auto language = English -- so a character the
detector misses lands on a WRONG voice and is counted.

argv: probe  kotlin-iso3-dump  ucd-dir  report.md
"""
import os, sys, subprocess, collections

probe, isofile, ucd, report = sys.argv[1:5]
LETTER_CAP = 160

def rows(f):
    for line in open(os.path.join(ucd, f), encoding="utf-8"):
        b = line.split("#")[0].strip()
        if not b: continue
        x = [y.strip() for y in b.split(";")]; a = x[0].split("..")
        yield int(a[0], 16), int(a[-1], 16), x[1]
SC = {}; GC = {}; ISC = {}; NAME = {}
for lo, hi, v in rows("Scripts.txt"):
    for c in range(lo, hi + 1): SC[c] = v
for lo, hi, v in rows("DerivedGeneralCategory.txt"):
    for c in range(lo, hi + 1): GC[c] = v
for lo, hi, v in rows("IndicSyllabicCategory.txt"):
    for c in range(lo, hi + 1): ISC[c] = v
for line in open(os.path.join(ucd, "UnicodeData.txt"), encoding="utf-8"):
    f = line.split(";"); NAME[int(f[0], 16)] = f[1]

# Kotlin's own answers
KT = {}
for line in open(isofile, encoding="utf-8"):
    k, _, v = line.strip().partition("=")
    if v: KT[k] = v
def to_iso2(iso3):   # IsoCodes.toIso2: first 2-letter code mapping to it, else a self-mapped 3-letter one
    for k, v in KT.items():
        if v == iso3 and len(k) == 2: return k
    return iso3 if KT.get(iso3) == iso3 else None

LANG = {"Latin": "eng", "Greek": "ell", "Cyrillic": "rus", "Armenian": "hye", "Hebrew": "heb", "Arabic": "ara",
        "Syriac": "syr", "Thaana": "div", "Nko": "nqo", "Devanagari": "hin", "Bengali": "ben", "Gurmukhi": "pan",
        "Gujarati": "guj", "Oriya": "ori", "Tamil": "tam", "Telugu": "tel", "Kannada": "kan", "Malayalam": "mal",
        "Sinhala": "sin", "Thai": "tha", "Lao": "lao", "Tibetan": "bod", "Myanmar": "mya", "Georgian": "kat",
        "Hangul": "kor", "Ethiopic": "amh", "Cherokee": "chr", "Canadian_Aboriginal": "iku", "Khmer": "khm",
        "Mongolian": "mon", "Han": "zho", "Hiragana": "jpn", "Katakana": "jpn", "Bopomofo": "zho", "Yi": "iii",
        "Ol_Chiki": "sat", "Meetei_Mayek": "mni", "Tifinagh": "zgh", "Javanese": "jav", "Balinese": "ban",
        "Sundanese": "sun", "Limbu": "lif", "Chakma": "ccp", "Tai_Le": "tdd", "Vai": "vai", "Adlam": "ful",
        "Lisu": "lis", "Cham": "cjm", "Tai_Tham": "nod", "New_Tai_Lue": "khb", "Buginese": "bug", "Lepcha": "lep",
        "Saurashtra": "saz", "Kayah_Li": "kyu", "Tai_Viet": "blt", "Bamum": "bax", "Hanifi_Rohingya": "rhg",
        "Newa": "new", "Tirhuta": "mai", "Kaithi": "bho", "Syloti_Nagri": "syl", "Masaram_Gondi": "gon",
        "Gunjala_Gondi": "gon", "Dogra": "doi", "Sharada": "kas", "Tagalog": "tgl", "Osage": "osa",
        "Wancho": "nnp", "Nyiakeng_Puachue_Hmong": "hnj", "Pahawh_Hmong": "hmn", "Sora_Sompeng": "srb", "Mro": "mro"}

def base_of(script):
    cons = sorted(c for c, s in SC.items() if s == script and ISC.get(c) == "Consonant")
    if cons: return cons[0]
    lets = sorted(c for c, s in SC.items() if s == script and GC.get(c, "")[:1] == "L")
    return lets[0] if lets else None

cases = []   # (id, group, script, form, cps, quick, target)
def add(script, group, form, cps, target, quickToo=True):
    for q in ((0, 1) if quickToo else (0,)):
        cases.append((len(cases), group, script, form, cps, q, target))

scripts = sorted(set(SC.values()) - {"Common", "Inherited", "Unknown"})
for s in scripts:
    target = LANG.get(s)
    letters = sorted(c for c, v in SC.items() if v == s and GC.get(c, "")[:1] == "L")
    if len(letters) > LETTER_CAP:
        step = len(letters) / LETTER_CAP
        letters = [letters[int(i * step)] for i in range(LETTER_CAP)]
    marks = sorted(c for c, v in SC.items() if v == s and GC.get(c, "")[:1] == "M")
    digits = sorted(c for c, v in SC.items() if v == s and GC.get(c) == "Nd")
    base = base_of(s)
    for c in letters: add(s, "letter", "alone", [c], target)
    for c in digits: add(s, "digit", "alone", [c], target)
    for c in marks:
        add(s, "mark", "alone", [c], target)
        add(s, "mark", "dotted", [0x25CC, c], target, False)
        if base is not None: add(s, "mark", "onbase", [base, c], target, False)
# Inherited marks on a Latin and a Devanagari base, and alone
inh = sorted(c for c, v in SC.items() if v == "Inherited" and GC.get(c, "")[:1] == "M")
for c in inh:
    add("Inherited", "mark", "on-a", [0x61, c], "eng", False)
    add("Inherited", "mark", "on-ka", [0x915, c], "hin", False)
    add("Inherited", "mark", "alone", [c], None)
# ZWJ / ZWNJ inside an Indic word, the way they really occur
for z in (0x200C, 0x200D):
    add("Common", "joiner", "ka-virama-%X-ssa" % z, [0x915, 0x94D, z, 0x937], "hin", False)
    add("Common", "joiner", "ka-virama-%X-ssa-gu" % z, [0xA95, 0xACD, z, 0xAB7], "guj", False)

def phone(target):
    langs = ["eng", "hin", "guj"] + ([target] if target and target not in ("eng", "hin", "guj") else [])
    hints = [h for h in (to_iso2(l) for l in langs) if h]
    nonlat = "hin" if target == "guj" else "guj"
    return ",".join(hints), ",".join(langs), nonlat

lines = []
for (cid, group, s, form, cps, q, target) in cases:
    hints, ok, nonlat = phone(target)
    lines.append("\t".join([str(cid), hints, ok, "eng", nonlat, "eng", str(q), " ".join("%X" % c for c in cps)]))
out = subprocess.run([probe, isofile], input="\n".join(lines) + "\n", capture_output=True, text=True, check=True).stdout
res = {}
for line in out.splitlines():
    f = line.split("\t")
    res[int(f[0])] = (f[1][4:], f[2][5:], int(f[3][7:]))

def judge(case):
    cid, group, s, form, cps, q, target = case
    mix, auto, nchunks = res[cid]
    runs = [r.split(":")[0] for r in mix.split("|") if r]
    autoL = auto.split("(")[0]
    mix_ok = bool(runs) and all(r == target for r in runs) if target else None
    auto_ok = autoL == target if target else None
    split = len(runs) > 1
    return mix_ok, auto_ok, split, runs, autoL, auto

stat = collections.defaultdict(lambda: collections.Counter())
fails = collections.defaultdict(list)
for case in cases:
    cid, group, s, form, cps, q, target = case
    mix_ok, auto_ok, split, runs, autoL, auto = judge(case)
    key = (s, group, form, q)
    st = stat[key]; st["n"] += 1
    if target is None: st["nolang"] += 1; continue
    st["mix_ok"] += bool(mix_ok); st["auto_ok"] += bool(auto_ok); st["split"] += split
    if not (mix_ok and auto_ok) or split:
        fails[key].append((cps, runs, auto))

def cpname(c): return "U+%04X %s%s" % (c, NAME.get(c, "?"), (" [" + ISC[c] + "]") if c in ISC else "")
with open(report, "w", encoding="utf-8") as r:
    r.write("# Script audit -- every script of Unicode %s through the detection path\n\n" %
            open(os.path.join(ucd, "Scripts.txt"), encoding="utf-8").readline().strip("# \n").replace("Scripts-", "").replace(".txt", ""))
    r.write("Generated by tools/verify/scriptaudit/run.sh. Phone: English + Hindi + Gujarati + the script's own language; "
            "preferred Latin English, preferred non-Latin Gujarati (Hindi for Gujarati), auto language English.\n\n")
    r.write("| script | lang | group | form | quick | cases | mix right | auto right | split |\n|---|---|---|---|---|---|---|---|---|\n")
    for key in sorted(stat):
        s, group, form, q = key; st = stat[key]
        tgt = LANG.get(s, "") if s not in ("Inherited", "Common") else "-"
        if st["nolang"] == st["n"]:
            r.write("| %s | (none) | %s | %s | %d | %d | - | - | - |\n" % (s, group, form, q, st["n"])); continue
        r.write("| %s | %s | %s | %s | %d | %d | %d | %d | %d |\n" % (s, tgt, group, form, q, st["n"], st["mix_ok"], st["auto_ok"], st["split"]))
    r.write("\n## Failures (up to 12 per row)\n")
    for key in sorted(fails):
        s, group, form, q = key
        r.write("\n### %s / %s / %s / quick %d -- %d wrong\n" % (s, group, form, q, len(fails[key])))
        for cps, runs, auto in fails[key][:12]:
            r.write("- %s -> mix %s, auto %s\n" % (" + ".join(cpname(c) for c in cps), runs, auto))
tot = collections.Counter()
for key, st in stat.items():
    for k, v in st.items(): tot[k] += v
print("cases %d (no living language: %d)  mix right %d  auto right %d  split %d  -> %s"
      % (tot["n"], tot["nolang"], tot["mix_ok"], tot["auto_ok"], tot["split"], report))
