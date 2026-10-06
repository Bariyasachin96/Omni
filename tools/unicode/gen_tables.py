#!/usr/bin/env python3
"""Every Unicode table the app uses, generated from the Unicode Character
Database (owner, 2026-10-06: "Unicode ... bilkul purana hai ... latest likho").

Reads tools/unicode/ucd/ (UCD 18.0.0, unicode.org/Public/18.0.0/ucd, emoji
data from unicode.org/Public/emoji/18.0) and writes

    app/src/main/cpp/unicode_tables.h
    app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt

To move to a newer Unicode: put the new files in tools/unicode/ucd/, run this,
then tools/verify/unicode/run.sh (every code point against this file's own
reading of the data) and the detection harnesses. Nothing here is typed by
hand except which scripts the app has a slot for.

    gen_tables.py [--check]     --check: fail if the generated files differ
"""
import os, sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(HERE))
UCD = os.environ.get("UCD", os.path.join(HERE, "ucd"))
OUT_H = os.path.join(ROOT, "app/src/main/cpp/unicode_tables.h")
OUT_KT = os.path.join(ROOT, "app/src/main/java/com/sachinbaria/easyvoice/UnicodeTables.kt")

def rows(fname):
    for line in open(os.path.join(UCD, fname), encoding="utf-8"):
        body = line.split("#")[0].strip()
        if not body: continue
        f = [x.strip() for x in body.split(";")]
        a = f[0].split("..")
        yield int(a[0], 16), int(a[-1], 16), f[1]

def version():
    first = open(os.path.join(UCD, "Scripts.txt"), encoding="utf-8").readline()
    return first.strip("# \n").replace("Scripts-", "").replace(".txt", "")

def prop(fname, value):
    s = set()
    for lo, hi, v in rows(fname):
        if v == value: s.update(range(lo, hi + 1))
    return s

SCRIPT = {}
for lo, hi, v in rows("Scripts.txt"):
    for c in range(lo, hi + 1): SCRIPT[c] = v
SCX = {}
for lo, hi, v in rows("ScriptExtensions.txt"):
    for c in range(lo, hi + 1): SCX[c] = v.split()
GC = {}
for lo, hi, v in rows("DerivedGeneralCategory.txt"):
    for c in range(lo, hi + 1): GC[c] = v

# ---- the span detector's script slots (libcld2 getLanguageSpans' numbering:
# 1 Latin, 2 Cyrillic, 3 Arabic, 4 Devanagari, 5 CJK, 6 Bengali, then 7..25 the
# scripts SCRIPT_FIXED_LANG names, in its order). A code point gets the slot of
# its Script; a Common/Inherited one gets the slot its Script_Extensions name
# when they name exactly ONE slot (the Arabic comma is Arabic), and none when
# they name several (the danda serves a dozen Indic scripts -- AutoTTS made it
# neutral by hand in 5.7.7.26, the data says the same) or none.
SLOT_LONG = {"Latin": 1, "Cyrillic": 2, "Arabic": 3, "Devanagari": 4, "Han": 5, "Hiragana": 5,
             "Katakana": 5, "Bopomofo": 5, "Bengali": 6, "Greek": 7, "Armenian": 8, "Hebrew": 9,
             "Georgian": 10, "Gurmukhi": 11, "Gujarati": 12, "Oriya": 13, "Tamil": 14, "Telugu": 15,
             "Kannada": 16, "Malayalam": 17, "Sinhala": 18, "Thai": 19, "Lao": 20, "Tibetan": 21,
             "Myanmar": 22, "Khmer": 23, "Ethiopic": 24, "Hangul": 25}
SLOT_SHORT = {"Latn": 1, "Cyrl": 2, "Arab": 3, "Deva": 4, "Hani": 5, "Hira": 5, "Kana": 5, "Bopo": 5,
              "Beng": 6, "Grek": 7, "Armn": 8, "Hebr": 9, "Geor": 10, "Guru": 11, "Gujr": 12,
              "Orya": 13, "Taml": 14, "Telu": 15, "Knda": 16, "Mlym": 17, "Sinh": 18, "Thai": 19,
              "Laoo": 20, "Tibt": 21, "Mymr": 22, "Khmr": 23, "Ethi": 24, "Hang": 25}
def span_slot(c):
    s = SCRIPT.get(c, "Unknown")
    if s in SLOT_LONG: return SLOT_LONG[s]
    if c in SCX:
        v = {SLOT_SHORT[x] for x in SCX[c] if x in SLOT_SHORT}
        if len(v) == 1: return v.pop()
    return -1

# ---- the script-family fallback (a.java's families), by Script alone: a
# letter of a script the app knows that none of a.java's hand-made block ranges
# reached. The numbers are FamilyScript in tts_engine_core.cpp.
FAMILY = {"Latin": 1, "Greek": 2, "Cyrillic": 3, "Armenian": 4, "Hebrew": 5, "Arabic": 6, "Thaana": 7,
          "Devanagari": 8, "Bengali": 9, "Gurmukhi": 10, "Gujarati": 11, "Oriya": 12, "Tamil": 13,
          "Telugu": 14, "Kannada": 15, "Malayalam": 16, "Sinhala": 17, "Thai": 18, "Lao": 19,
          "Tibetan": 20, "Myanmar": 21, "Georgian": 22, "Hangul": 23, "Ethiopic": 24, "Khmer": 25,
          "Mongolian": 26, "Hiragana": 27, "Katakana": 28, "Han": 29, "Bopomofo": 29}

# ---- emoji: Extended_Pictographic plus Emoji_Component, without the ASCII
# keycap bases (# * 0-9, ordinary text) and ZWJ (the segmenter joins on it
# separately). Tag characters E0020-E007F come with the components, so a tag
# flag (England, Scotland) is one emoji run now.
EMOJI = (prop("emoji-data.txt", "Extended_Pictographic") | prop("emoji-data.txt", "Emoji_Component")) \
        - {0x23, 0x2A, 0x200D} - set(range(0x30, 0x3A))
BIDI = prop("PropList.txt", "Bidi_Control")
LETTER = {c for c, v in GC.items() if v in ("Lu", "Ll", "Lt", "Lm", "Lo")}
LATIN = {c for c, s in SCRIPT.items() if s == "Latin"}
LCI = {c for c, s in SCRIPT.items() if s in ("Latin", "Common", "Inherited")}

# ---- the fancy-letter fold, extended: every compatibility form whose
# decomposition is ONE ASCII letter or digit and whose tag is a styling one
# (font, circle, wide, super, sub, square, small, narrow). Not Latin-1 (the
# ordinal indicators ª º are words in Spanish and Portuguese, not styling) and
# not an emoji (ℹ). clsCLD2.a's own mappings are asked FIRST and win.
STYLE = ("font", "circle", "wide", "super", "sub", "square", "small", "narrow")
FOLD = {}
for line in open(os.path.join(UCD, "UnicodeData.txt"), encoding="utf-8"):
    f = line.split(";")
    d = f[5]
    if not d.startswith("<"): continue
    tag, rest = d[1:].split(">", 1)
    parts = rest.split()
    if tag not in STYLE or len(parts) != 1: continue
    t = int(parts[0], 16); c = int(f[0], 16)
    if not (0x30 <= t <= 0x39 or 0x41 <= t <= 0x5A or 0x61 <= t <= 0x7A): continue
    if c < 0x100 or c in EMOJI: continue
    FOLD[c] = t

def merge(cps):
    out = []
    for c in sorted(cps):
        if out and out[-1][1] == c - 1: out[-1][1] = c
        else: out.append([c, c])
    return out

def merge_val(fn, lo=0x80, hi=0x10FFFF):
    out = []
    for c in range(lo, hi + 1):
        if 0xD800 <= c <= 0xDFFF: continue
        v = fn(c)
        if v is None: continue
        if out and out[-1][1] == c - 1 and out[-1][2] == v: out[-1][1] = c
        else: out.append([c, c, v])
    return out

def c_ranges(name, rs):
    body = ",\n".join("    {0x%X, 0x%X}" % (a, b) for a, b in rs)
    return "static const UniRange %s[] = {\n%s\n};\n" % (name, body)

def c_vranges(name, rs):
    body = ",\n".join("    {0x%X, 0x%X, %d}" % (a, b, v) for a, b, v in rs)
    return "static const UniRangeV %s[] = {\n%s\n};\n" % (name, body)

ver = version()
span = merge_val(lambda c: (lambda s: s if s > 0 else None)(span_slot(c)))
fam = merge_val(lambda c: FAMILY.get(SCRIPT.get(c)) if GC.get(c, "Cn")[0] in "LMN" else None, 0)
fold = [[c, c, t] for c, t in sorted(FOLD.items())]

h = []
h.append("// GENERATED by tools/unicode/gen_tables.py from the Unicode Character Database\n"
         "// %s. Do not edit; regenerate. Every range is inclusive and the arrays are sorted.\n" % ver)
h.append("#pragma once\n#include <cstddef>\n#include <cstdint>\n\n")
h.append("#define EV_UNICODE_VERSION \"%s\"\n\n" % ver)
h.append("struct UniRange { uint32_t lo, hi; };\nstruct UniRangeV { uint32_t lo, hi; int32_t v; };\n\n")
h.append("template <size_t N> static inline bool uniIn(const UniRange (&t)[N], int cp) {\n"
         "    if (cp < 0) return false;\n    size_t a = 0, b = N;\n"
         "    while (a < b) { size_t m = (a + b) / 2; if ((uint32_t)cp < t[m].lo) b = m; else if ((uint32_t)cp > t[m].hi) a = m + 1; else return true; }\n"
         "    return false;\n}\n")
h.append("template <size_t N> static inline int32_t uniValue(const UniRangeV (&t)[N], int cp, int32_t none) {\n"
         "    if (cp < 0) return none;\n    size_t a = 0, b = N;\n"
         "    while (a < b) { size_t m = (a + b) / 2; if ((uint32_t)cp < t[m].lo) b = m; else if ((uint32_t)cp > t[m].hi) a = m + 1; else return t[m].v; }\n"
         "    return none;\n}\n\n")
h.append("// Extended_Pictographic + Emoji_Component, minus # * 0-9 and ZWJ.\n" + c_ranges("kUniEmoji", merge(EMOJI)) + "\n")
h.append("// Bidi_Control (PropList).\n" + c_ranges("kUniBidiControl", merge(BIDI)) + "\n")
h.append("// General_Category L* -- Java's Character.isLetter.\n" + c_ranges("kUniLetter", merge(LETTER)) + "\n")
h.append("// Script=Latin.\n" + c_ranges("kUniLatin", merge(LATIN)) + "\n")
h.append("// The span detector's script slot of each code point from U+0080 (see the generator).\n" + c_vranges("kUniSpanSlot", span) + "\n")
h.append("// The script-family fallback: FamilyScript of each letter, mark or digit.\n" + c_vranges("kUniFamilyScript", fam) + "\n")
h.append("// Styling compatibility forms that decompose to one ASCII letter or digit.\n" + c_vranges("kUniFold", fold) + "\n")
h = "".join(h)

lci = merge(LCI)
kt = ("package com.sachinbaria.easyvoice\n\n"
      "// GENERATED by tools/unicode/gen_tables.py from the Unicode Character Database\n"
      "// %s. Do not edit; regenerate.\n\n"
      "// Script=Latin, Common or Inherited, as pairs of inclusive bounds -- the\n"
      "// app's own copy, so the answer does not depend on the phone's Unicode.\n"
      "internal object UnicodeTables {\n"
      "    const val VERSION = \"%s\"\n"
      "    private val LATIN_COMMON_INHERITED = intArrayOf(\n%s\n    )\n"
      "    fun isLatinCommonInherited(codePoint: Int): Boolean {\n"
      "        var a = 0\n        var b = LATIN_COMMON_INHERITED.size / 2\n"
      "        while (a < b) {\n            val m = (a + b) ushr 1\n"
      "            if (codePoint < LATIN_COMMON_INHERITED[2 * m]) b = m\n"
      "            else if (codePoint > LATIN_COMMON_INHERITED[2 * m + 1]) a = m + 1\n"
      "            else return true\n        }\n        return false\n    }\n}\n") % (
      ver, ver, ",\n".join("        0x%X, 0x%X" % (a, b) for a, b in lci))

check = "--check" in sys.argv
for path, text in ((OUT_H, h), (OUT_KT, kt)):
    old = open(path, encoding="utf-8").read() if os.path.exists(path) else None
    if check:
        if old != text: sys.exit("out of date: " + path)
    elif old != text:
        open(path, "w", encoding="utf-8").write(text)
print("Unicode %s: emoji %d, bidi %d, letters %d, latin %d, span slots %d ranges, family %d ranges, fold %d, kotlin LCI %d ranges"
      % (ver, len(EMOJI), len(BIDI), len(LETTER), len(LATIN), len(span), len(fam), len(fold), len(lci)))
