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
hand except which scripts the app has a slot for. Which language a living
script is read in comes from CLDR (tools/unicode/cldr/, release 48.2): its
likely subtags and its script metadata.

    gen_tables.py [--check]     --check: fail if the generated files differ
"""
import os, re, sys

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
# EVERY OTHER SCRIPT gets a slot of its own too (owner, 2026-10-07: "sabhi
# lipiyon ka sahi detection"). libcld2's 25 slots left Thaana, Syriac,
# Cherokee, Mongolian, Ol Chiki ... with no script at all, so a Dhivehi
# sentence joined the run before it and was read by the LATIN voice although
# CLD2 itself names it (dv 100%). Slots 26..39 are the rest of the 40 the hint
# tables hold, and a slot above 25 goes to CLD2 (getLanguageSpans' own
# `cmp w3, #0x19 / b.hi`): the seven scripts CLD2 has a language for get one
# each, every other script shares 33 -- CLD2 answers "un" for those, a
# non-Latin span, so the non-Latin preferred voice reads it.
SLOT_EXTRA = {"Syriac": 26, "Thaana": 27, "Cherokee": 28, "Canadian_Aboriginal": 29,
              "Mongolian": 30, "Tagalog": 31, "Limbu": 32}
SLOT_OTHER = 33
# A LIVING SCRIPT NOBODY ELSE NAMES gets a slot of its own from 34 up, and its
# language is CLDR's (owner, 2026-10-16: "Unicode script mein kuchh chijen aisi
# hogi jo detect nahin hoti ... latest Unicode ... jo available na ho vah
# provide"). Measured first (tools/verify/allscripts): 30 scripts in use today
# -- Ol Chiki, Meetei Mayek, Javanese, Tifinagh, N'Ko, Adlam ... -- reached
# CLD2 on slot 33 and came back "un" or its xx-Script pseudo-code, so even with
# that language's voice set up the span went to the non-Latin preferred voice.
# CLDR's likely subtags name each script's most likely language (und_Olck ->
# sat_Olck_IN) and its script metadata says which scripts are in use today (UAX
# #31 Recommended or Limited_Use). Such a script that getLanguageSpans' 25
# slots and SLOT_EXTRA leave out is decided by the script alone, like the 19
# SCRIPT_FIXED_LANG ones. Historic scripts stay on SLOT_OTHER: no voice speaks
# them. The slots follow the scripts' names, so the numbering is stable.
CLDR = os.path.join(HERE, "cldr")
SCRIPT_CODE = {}
for line in open(os.path.join(UCD, "PropertyValueAliases.txt"), encoding="utf-8"):
    p = [x.strip() for x in line.split("#", 1)[0].split(";")]
    if len(p) >= 3 and p[0] == "sc": SCRIPT_CODE[p[2]] = p[1]
USAGE = {}
for line in open(os.path.join(CLDR, "scriptMetadata.txt"), encoding="utf-8"):
    p = [x.strip() for x in line.split("#", 1)[0].split(";")]
    if len(p) > 5: USAGE[p[0]] = p[5]
LIKELY = dict(re.findall(r'from="und_([A-Z][a-z]{3})" to="([a-z]{2,3})_',
                         open(os.path.join(CLDR, "likelySubtags-scripts.txt"), encoding="utf-8").read()))
LIVING = {n: LIKELY[c] for n, c in SCRIPT_CODE.items()
          if USAGE.get(c) in ("RECOMMENDED", "LIMITED_USE") and c in LIKELY and n in set(SCRIPT.values())}
SLOT_FIXED_FIRST = 34
SLOT_FIXED = {n: SLOT_FIXED_FIRST + i for i, n in
              enumerate(sorted(n for n in LIVING if n not in SLOT_LONG and n not in SLOT_EXTRA))}
SHORT_NAME = {short: name for name, short in SCRIPT_CODE.items()}
def name_slot(name):
    """The slot of a script, by its long name; None for Common/Inherited/Unknown."""
    if name in SLOT_LONG: return SLOT_LONG[name]
    if name in SLOT_EXTRA: return SLOT_EXTRA[name]
    if name in SLOT_FIXED: return SLOT_FIXED[name]
    if name in ("Common", "Inherited", "Unknown"): return None
    return SLOT_OTHER
def scx_slots(c):
    """Every slot a Common/Inherited code point's Script_Extensions name."""
    return {name_slot(SHORT_NAME[x]) for x in SCX.get(c, ())} - {None}
# EVERY SLOT COUNTS, not only libcld2's 25 (owner, 2026-10-21: "ek character
# bhi nahin chhutna chahie ... Chahe Koi Bhi Lipi Ho"). Counting only those 25
# made the Arabic comma, question mark and tatweel and the harakat ARABIC even
# though Syriac, Thaana, N'Ko, Adlam and Hanifi Rohingya use them too (their
# slots are SLOT_EXTRA / SLOT_FIXED): after a Syriac or N'Ko letter they broke
# off into an Arabic span that CLD2 answered "un" or "xx-Qaai" for.
def span_slot(c):
    s = SCRIPT.get(c, "Unknown")
    if s in ("Common", "Inherited", "Unknown"):
        v = scx_slots(c)
        return v.pop() if len(v) == 1 else -1
    return name_slot(s)
# A RUN OF NOTHING BUT NEUTRAL CHARACTERS (a lone danda, a lone CJK full stop,
# a Vedic sign, an Arabic comma) took the LATIN language -- AutoTTS's rule for
# digits, ASCII punctuation and emoji, which name no script at all. These name
# the scripts that use them: kUniScxMask holds, for each neutral code point
# whose Script_Extensions name any, the bit (1 << slot) of every such slot, and
# such a run is read in the lowest slot an enabled language writes.
def scx_mask(c):
    if c in SCX and span_slot(c) == -1:
        m = 0
        for slot in scx_slots(c): m |= 1 << slot
        return m or None
    return None
# A CHARACTER THAT BELONGS TO THE ONE BEFORE IT stays in that character's
# buffer (the segmenter's Latin / non-Latin split): Script=Inherited (UAX #24:
# "inherit the script of the preceding character" -- combining marks), and the
# Common modifier letters, modifier symbols and marks. The split cut "café"
# typed with a combining acute into "cafe" and a lone mark, Uzbek "Oʻzbekiston"
# (U+02BB) into three pieces and every decomposed Vietnamese word apart. Emoji
# components keep their own handling. v = the buffer when nothing comes before
# it: 0 Latin (its Script_Extensions name Latin, or none), 1 otherwise.
def attach_kind(c):
    if c in EMOJI: return None
    s = SCRIPT.get(c, "Unknown")
    g = GC.get(c, "Cn")
    if s == "Inherited" or (s == "Common" and g in ("Lm", "Sk", "Mn", "Mc", "Me")):
        x = SCX.get(c)
        return 0 if (x is None or "Latn" in x) else 1
    return None

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

# ---- Java's Character.isWhitespace, from the data: a space, line or paragraph
# separator (Zs Zl Zp) that is not a no-break one (U+00A0 U+2007 U+202F), plus
# the controls Java names (U+0009-000D, U+001C-001F). The core had it typed out
# twice.
JAVA_SPACE = {c for c, v in GC.items() if v in ("Zs", "Zl", "Zp")} - {0xA0, 0x2007, 0x202F}
JAVA_SPACE |= set(range(0x09, 0x0E)) | set(range(0x1C, 0x20))

# ---- decimal digits (General_Category Nd, which is Java's Character.isDigit),
# with each range's value at its low end: every Nd range is a run of 0..9.
DIGIT_VALUE = {}
for line in open(os.path.join(UCD, "UnicodeData.txt"), encoding="utf-8"):
    f = line.split(";")
    if f[2] == "Nd": DIGIT_VALUE[int(f[0], 16)] = int(f[6])
NUMERIC_DIGIT = set(DIGIT_VALUE)

# ---- currency symbols (General_Category Sc), outside ASCII: the core already
# names '$' on its own wherever it means it.
CURRENCY = {c for c, v in GC.items() if v == "Sc" and c >= 0x80}

# ---- letters of the scripts written without spaces between words: Line_Break
# SA (Thai, Lao, Myanmar, Khmer, Tai Tham, New Tai Lue, Tai Viet...), ID/CJ
# (ideographs, kana) and the Hangul classes. A smart-number keyword that starts
# with one may match inside a word.
NOSPACE_LB = {"SA", "ID", "CJ", "H2", "H3", "JL", "JV", "JT"}
NOSPACE = set()
for lo, hi, v in rows("LineBreak.txt"):
    if v in NOSPACE_LB:
        for c in range(lo, hi + 1):
            if GC.get(c, "Cn")[0] in "LM": NOSPACE.add(c)

# ---- lowercase: the simple Lowercase_Mapping of UnicodeData (field 13), as a
# delta per code point; SpecialCasing's one unconditional lowercase (U+0130 ->
# i + U+0307) and Final_Sigma are applied in the core, as Java's
# String.toLowerCase(Locale.ROOT) applies them.
LOWER_DELTA = {}
for line in open(os.path.join(UCD, "UnicodeData.txt"), encoding="utf-8"):
    f = line.split(";")
    if f[13]: LOWER_DELTA[int(f[0], 16)] = int(f[13], 16) - int(f[0], 16)
SPECIAL_LOWER = {}
for line in open(os.path.join(UCD, "SpecialCasing.txt"), encoding="utf-8"):
    body = line.split("#")[0].strip()
    if not body: continue
    f = [x.strip() for x in body.split(";")]
    if len(f) >= 5 and f[4]: continue          # conditional (Final_Sigma, language)
    c = int(f[0], 16); low = [int(x, 16) for x in f[1].split()]
    if low != [c] and (len(low) != 1 or LOWER_DELTA.get(c, 0) + c != low[0]): SPECIAL_LOWER[c] = low
CASED = prop("DerivedCoreProperties.txt", "Cased")
CASE_IGNORABLE = prop("DerivedCoreProperties.txt", "Case_Ignorable")

# ---- THE LANGUAGES OF A SCRIPT TWO OR MORE LANGUAGES SHARE, AND THE LETTERS
# EACH ONE WRITES (owner, 2026-10-28: "cldr ka properli jo data hai Unicode ka
# ... sab Kuchh complete karo aur ekadam accurate result chahie"). The
# languages of slots 1..6 are ML Kit's own list of the 111 it identifies
# (developers.google.com/ml-kit/language/identification/langid-support), plus
# Sanskrit and Assamese, which it does not name; the core's languageSlot reads
# them from here. The letters are CLDR's exemplar characters
# (tools/unicode/cldr/exemplars.txt, release 48.2): main, auxiliary and index
# together -- the auxiliary set holds the letters of loanwords and names, so a
# letter a language does write is not taken for a foreign one (main alone left
# Urdu without ں and آ). Each letter is expanded the way text can spell it: its
# simple upper, lower and title case and its canonical decomposition, and a
# {sequence} gives its letters and its composed form. Kept: letters and marks
# of the slot's own script only (span_slot) -- a mark several scripts share
# (Arabic harakat, the tatweel, a Latin combining accent) says nothing -- and on
# slot 5 no Han ideograph, which Chinese and Japanese share (CLDR lists the
# common ones of each, not all), so there it is the kana. A language with no
# CLDR locale (Hmong, Samoan) has no letters here, and one CLDR gives no
# auxiliary letters (Malay, Kazakh, Latin) does not say which letters it never
# writes: neither is ever ruled out ("open"; a Malay name with é is still
# Malay), though Kazakh's own letters still say Kazakh.
# Measured over the language suite's held-out texts (16,233, 9 sets): a word
# whose letters only one enabled language writes is that language -- Urdu
# beside Arabic 42.6% of words, Arabic 20.0%, Ukrainian beside Russian 33.8%,
# Serbian 28.2%, Japanese beside Chinese 91% (kana) -- and wrong once in 81,000
# words (a Nepali word with eyelash ra, which CLDR gives Marathi alone).
SHARED_LANGS = {
    1: ["af", "az", "bs", "ca", "ceb", "co", "cs", "cy", "da", "de", "en", "eo", "es", "et", "eu", "fi",
        "fil", "fr", "fy", "ga", "gd", "gl", "ha", "haw", "hmn", "hr", "ht", "hu", "id", "ig", "is", "it",
        "jv", "ku", "la", "lb", "lt", "lv", "mg", "mi", "ms", "mt", "nl", "no", "ny", "pl", "pt", "ro",
        "sk", "sl", "sm", "sn", "so", "sq", "st", "su", "sv", "sw", "tr", "uz", "vi", "xh", "yo", "zu"],
    2: ["be", "bg", "kk", "ky", "mk", "mn", "ru", "sr", "tg", "uk"],
    3: ["ar", "fa", "ps", "sd", "ur"],
    4: ["hi", "mr", "ne", "sa"],
    5: ["zh", "ja"],
    6: ["bn", "as"]}
UD = {}
for line in open(os.path.join(UCD, "UnicodeData.txt"), encoding="utf-8"):
    f = line.rstrip("\n").split(";")
    UD[int(f[0], 16)] = f
def full_decomposition(c):
    d = UD.get(c, [""] * 15)[5]
    if not d or d.startswith("<"): return [c]
    out = []
    for x in d.split(): out += full_decomposition(int(x, 16))
    return out
COMPOSED = {}
for c in UD:
    d = full_decomposition(c)
    if len(d) > 1: COMPOSED.setdefault(tuple(d), c)
def parse_uset(s):
    """A CLDR exemplar UnicodeSet: its single characters and its {sequences}."""
    s = s.strip()
    assert s[0] == "[" and s[-1] == "]", s
    s = s[1:-1]
    singles, seqs = set(), []
    def take(i):
        if s[i] == "\\":
            if s[i + 1] == "u": return int(s[i + 2:i + 6], 16), i + 6
            if s[i + 1] == "U": return int(s[i + 2:i + 10], 16), i + 10
            return ord(s[i + 1]), i + 2
        return ord(s[i]), i + 1
    i, prev = 0, None
    while i < len(s):
        if s[i] == " ": i += 1; continue
        if s[i] == "{":
            j = s.index("}", i)
            seqs.append([ord(x) for x in s[i + 1:j]])
            i, prev = j + 1, None
            continue
        if s[i] == "-" and prev is not None and i + 1 < len(s):
            hi, i = take(i + 1)
            singles.update(range(prev, hi + 1))
            prev = None
            continue
        c, i = take(i)
        singles.add(c)
        prev = c
    return singles, seqs
def spellings(c):
    """c, its simple upper, lower and title case, and what they decompose to."""
    out = set()
    for x in [c] + [int(UD[c][k], 16) for k in (12, 13, 14) if c in UD and UD[c][k]]:
        out.add(x)
        out.update(full_decomposition(x))
    return out
EXEMPLARS = {}
HAS_AUXILIARY = set()
for line in open(os.path.join(CLDR, "exemplars.txt"), encoding="utf-8"):
    if line.startswith("#") or not line.strip(): continue
    lang, kind, uset = line.rstrip("\n").split("\t")
    if kind == "auxiliary": HAS_AUXILIARY.add(lang)
    singles, seqs = parse_uset(uset)
    letters = EXEMPLARS.setdefault(lang, set())
    for c in singles: letters |= spellings(c)
    for seq in seqs:
        for c in seq: letters |= spellings(c)
        nfd = []
        for c in seq: nfd += full_decomposition(c)
        if tuple(nfd) in COMPOSED: letters |= spellings(COMPOSED[tuple(nfd)])
# Letters a language's own words write that its CLDR exemplars leave out --
# measured, not assumed (2026-10-28: over the language suite's texts, every
# letter a language's texts write that CLDR gives another language of its
# script and not it). Nepali writes the eyelash ra "ऱ" (U+0931) as Marathi
# does -- 8 of 4,266 Nepali texts, Android's own strings among them
# ("नपुऱ्याउनुहोस्", "गऱ्यो"), against Marathi's 23 of 4,305 -- and CLDR gives it
# Marathi alone, so a Nepali sentence with it was read as Marathi. The other
# letters that check finds are one text each (a Uyghur line labelled Urdu, the
# Esperanto alphabet inside an Indonesian text) or French words in one Haitian
# document ("Volonté", "inaliénables"): not letters of those languages.
CLDR_MISSES = {"ne": [0x931]}
for lang, cps in CLDR_MISSES.items():
    for c in cps: EXEMPLARS[lang] |= spellings(c)
NO_CLDR_LOCALE = {"hmn", "sm"}
NOT_MLKIT = {"sa", "as"}
SLOT_LETTERS = {}          # slot -> {code point: bits of the languages that write it}
for slot, langs in SHARED_LANGS.items():
    assert len(langs) <= 64, slot
    table = SLOT_LETTERS.setdefault(slot, {})
    for bit, lang in enumerate(langs):
        if lang not in EXEMPLARS:
            assert lang in NO_CLDR_LOCALE, "no CLDR exemplars for " + lang
            continue
        for c in EXEMPLARS[lang]:
            if GC.get(c, "Cn")[0] not in "LM" or span_slot(c) != slot: continue
            if slot == 5 and SCRIPT.get(c) == "Han": continue
            table[c] = table.get(c, 0) | (1 << bit)

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

def c_mranges(name, rs):
    body = ",\n".join("    {0x%X, 0x%X, 0x%XULL}" % (a, b, v) for a, b, v in rs)
    return "static const UniRangeM %s[] = {\n%s\n};\n" % (name, body)

def c_vranges(name, rs):
    body = ",\n".join("    {0x%X, 0x%X, %d}" % (a, b, v) for a, b, v in rs)
    return "static const UniRangeV %s[] = {\n%s\n};\n" % (name, body)

ver = version()
span = merge_val(lambda c: (lambda s: s if s > 0 else None)(span_slot(c)))
scxm = merge_val(scx_mask)
attach = merge_val(attach_kind)
fam = merge_val(lambda c: FAMILY.get(SCRIPT.get(c)) if GC.get(c, "Cn")[0] in "LMN" else None, 0)
fold = [[c, c, t] for c, t in sorted(FOLD.items())]

h = []
h.append("// GENERATED by tools/unicode/gen_tables.py from the Unicode Character Database\n"
         "// %s. Do not edit; regenerate. Every range is inclusive and the arrays are sorted.\n" % ver)
h.append("#pragma once\n#include <cstddef>\n#include <cstdint>\n\n")
h.append("#define EV_UNICODE_VERSION \"%s\"\n\n" % ver)
h.append("struct UniRange { uint32_t lo, hi; };\nstruct UniRangeV { uint32_t lo, hi; int32_t v; };\n"
         "struct UniRangeM { uint32_t lo, hi; uint64_t m; };\n\n")
h.append("template <size_t N> static inline bool uniIn(const UniRange (&t)[N], int cp) {\n"
         "    if (cp < 0) return false;\n    size_t a = 0, b = N;\n"
         "    while (a < b) { size_t m = (a + b) / 2; if ((uint32_t)cp < t[m].lo) b = m; else if ((uint32_t)cp > t[m].hi) a = m + 1; else return true; }\n"
         "    return false;\n}\n")
h.append("template <size_t N> static inline int32_t uniValue(const UniRangeV (&t)[N], int cp, int32_t none) {\n"
         "    if (cp < 0) return none;\n    size_t a = 0, b = N;\n"
         "    while (a < b) { size_t m = (a + b) / 2; if ((uint32_t)cp < t[m].lo) b = m; else if ((uint32_t)cp > t[m].hi) a = m + 1; else return t[m].v; }\n"
         "    return none;\n}\n")
h.append("template <size_t N> static inline uint64_t uniMask(const UniRangeM (&t)[N], int cp) {\n"
         "    if (cp < 0) return 0;\n    size_t a = 0, b = N;\n"
         "    while (a < b) { size_t m = (a + b) / 2; if ((uint32_t)cp < t[m].lo) b = m; else if ((uint32_t)cp > t[m].hi) a = m + 1; else return t[m].m; }\n"
         "    return 0;\n}\n\n")
h.append("// Extended_Pictographic + Emoji_Component, minus # * 0-9 and ZWJ.\n" + c_ranges("kUniEmoji", merge(EMOJI)) + "\n")
h.append("// Bidi_Control (PropList).\n" + c_ranges("kUniBidiControl", merge(BIDI)) + "\n")
h.append("// General_Category L* -- Java's Character.isLetter.\n" + c_ranges("kUniLetter", merge(LETTER)) + "\n")
h.append("// Script=Latin.\n" + c_ranges("kUniLatin", merge(LATIN)) + "\n")
h.append("// The span detector's script slot of each code point from U+0080 (see the generator).\n" + c_vranges("kUniSpanSlot", span) + "\n")
h.append("// The slots a neutral code point's Script_Extensions name, as bits (1 << slot).\n" + c_mranges("kUniScxMask", scxm) + "\n")
h.append("// Characters that stay in the segmenter buffer of the one before them; v = the\n// buffer alone (0 Latin, 1 not).\n" + c_vranges("kUniAttach", attach) + "\n")
fixed = sorted(SLOT_FIXED, key=SLOT_FIXED.get)
h.append("// A living script no other slot names (see the generator): slot EV_FIXED_SLOT_FIRST + i\n"
         "// is read in kUniFixedSlotLang[i], CLDR's likely language of that script.\n"
         "#define EV_FIXED_SLOT_FIRST %d\n#define EV_FIXED_SLOT_COUNT %d\n"
         "static const char* const kUniFixedSlotLang[EV_FIXED_SLOT_COUNT] = {\n%s\n};\n\n"
         % (SLOT_FIXED_FIRST, len(fixed), ",\n".join('    "%s"  /* %s */' % (LIVING[n], n) for n in fixed)))
h.append("// The script-family fallback: FamilyScript of each letter, mark or digit.\n" + c_vranges("kUniFamilyScript", fam) + "\n")
h.append("// Styling compatibility forms that decompose to one ASCII letter or digit.\n" + c_vranges("kUniFold", fold) + "\n")
h.append("// Java's Character.isWhitespace: Zs Zl Zp minus the no-break spaces, plus U+0009-000D, U+001C-001F.\n" + c_ranges("kUniJavaSpace", merge(JAVA_SPACE)) + "\n")
digit_ranges = []
for c in sorted(NUMERIC_DIGIT):
    if digit_ranges and digit_ranges[-1][1] == c - 1 and DIGIT_VALUE[c] == DIGIT_VALUE[digit_ranges[-1][1]] + 1: digit_ranges[-1][1] = c
    else: digit_ranges.append([c, c, DIGIT_VALUE[c]])
h.append("// Decimal digits (General_Category Nd); v is the digit value at lo.\n" + c_vranges("kUniDigit", digit_ranges) + "\n")
h.append("// Currency symbols (General_Category Sc) from U+0080.\n" + c_ranges("kUniCurrency", merge(CURRENCY)) + "\n")
h.append("// Letters and marks of scripts written without spaces (Line_Break SA ID CJ H2 H3 JL JV JT).\n" + c_ranges("kUniNoSpace", merge(NOSPACE)) + "\n")
lower_ranges = []
for c in sorted(LOWER_DELTA):
    d = LOWER_DELTA[c]
    if lower_ranges and lower_ranges[-1][1] == c - 1 and lower_ranges[-1][2] == d: lower_ranges[-1][1] = c
    else: lower_ranges.append([c, c, d])
h.append("// Simple Lowercase_Mapping (UnicodeData field 13) as a delta.\n" + c_vranges("kUniLowerDelta", lower_ranges) + "\n")
h.append("// Cased and Case_Ignorable (DerivedCoreProperties), for Final_Sigma.\n" + c_ranges("kUniCased", merge(CASED)) + "\n" + c_ranges("kUniCaseIgnorable", merge(CASE_IGNORABLE)) + "\n")
h.append("// SpecialCasing's unconditional lowercase mappings that are not one code point.\n#define EV_SPECIAL_LOWER_COUNT %d\n" % len(SPECIAL_LOWER))
shared = []
for slot in sorted(SHARED_LANGS):
    for bit, lang in enumerate(SHARED_LANGS[slot]):
        shared.append('    {%d, %d, %s, %s, "%s"}' % (slot, bit if lang in EXEMPLARS else -1,
                                                   "false" if lang in HAS_AUXILIARY else "true",
                                                   "false" if lang in NOT_MLKIT else "true", lang))
h.append("\n// The languages of the scripts two or more languages share (slots 1..6; see the\n"
         "// generator) -- languageSlot reads them here -- and, per slot, the letters each one\n"
         "// writes (CLDR 48.2 exemplar characters): bit `bit` of kUniLetterLangs<slot>'s mask is\n"
         "// that language, -1 when CLDR has no locale for it; `open`: no letter rules it out (no\n"
         "// locale, or no auxiliary letters); `mlkit` false: ML Kit does not name it.\n"
         "struct UniSharedLang { int slot; int bit; bool open; bool mlkit; const char* code; };\n"
         "#define EV_SHARED_LANG_COUNT %d\n"
         "static const UniSharedLang kUniSharedLangs[EV_SHARED_LANG_COUNT] = {\n%s\n};\n" % (len(shared), ",\n".join(shared)))
for slot in sorted(SLOT_LETTERS):
    rs = []
    for c in sorted(SLOT_LETTERS[slot]):
        v = SLOT_LETTERS[slot][c]
        if rs and rs[-1][1] == c - 1 and rs[-1][2] == v: rs[-1][1] = c
        else: rs.append([c, c, v])
    h.append(c_mranges("kUniLetterLangs%d" % slot, rs))
h.append("// The languages of slot `slot` (bits as above) that write code point `cp`; 0 when\n"
         "// none of them does, or `cp` is not a letter of that script.\n"
         "static inline uint64_t uniLetterLangs(int slot, int cp) {\n"
         "    switch (slot) {\n%s\n    }\n    return 0;\n}\n"
         % "\n".join("    case %d: return uniMask(kUniLetterLangs%d, cp);" % (s, s) for s in sorted(SLOT_LETTERS)))
h = "".join(h)

lci = merge(LCI)
# ---- the Script of each code point for the Kotlin side (Voice setup's check
# that an engine's sample is in the language's script). Common, Inherited and
# Unknown carry no script; Hiragana, Katakana, Hangul and Bopomofo count as Han,
# because a Japanese or Korean sample mixes them with it.
SCRIPT_GROUP = {"Hiragana": "Han", "Katakana": "Han", "Hangul": "Han", "Bopomofo": "Han"}
script_names = sorted({SCRIPT_GROUP.get(v, v) for v in SCRIPT.values()} - {"Common", "Inherited", "Unknown"})
script_id = {n: i for i, n in enumerate(script_names)}
def kt_script(c):
    v = SCRIPT.get(c)
    if v is None or v in ("Common", "Inherited", "Unknown"): return None
    return script_id[SCRIPT_GROUP.get(v, v)]
script_ranges = merge_val(kt_script, 0)
kt_scripts = (
    "\n    // Script of each code point (Han, kana, Hangul and Bopomofo as one), as\n"
    "    // \"lo,hi,id;\" in hex; -1 for Common, Inherited and Unknown. Read once, when\n"
    "    // first asked (only the Voice setup screen asks).\n"
    "    private const val SCRIPT_RANGES = \"%s\"\n"
    "    private val scriptTable: IntArray by lazy {\n"
    "        val parts = SCRIPT_RANGES.split(';')\n"
    "        val out = IntArray(parts.size * 3)\n"
    "        var n = 0\n"
    "        for (part in parts) { val f = part.split(','); out[n++] = f[0].toInt(16); out[n++] = f[1].toInt(16); out[n++] = f[2].toInt(16) }\n"
    "        out\n    }\n"
    "    const val SCRIPT_COUNT = %d\n"
    "    fun scriptOf(codePoint: Int): Int {\n"
    "        val t = scriptTable\n        var a = 0\n        var b = t.size / 3\n"
    "        while (a < b) {\n            val m = (a + b) ushr 1\n"
    "            if (codePoint < t[3 * m]) b = m\n"
    "            else if (codePoint > t[3 * m + 1]) a = m + 1\n"
    "            else return t[3 * m + 2]\n        }\n        return -1\n    }\n") % (
    ";".join("%X,%X,%X" % (a, b, v) for a, b, v in script_ranges), len(script_names))

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
      "            else return true\n        }\n        return false\n    }\n%s%s}\n") % (
      ver, ver, ",\n".join("        0x%X, 0x%X" % (a, b) for a, b in lci), kt_scripts,
      "\n    // The language of every script in use today (CLDR's likely subtags of the\n"
      "    // UAX #31 Recommended and Limited_Use scripts): the codes the detector can\n"
      "    // name a span after its script. IsoCodes knows a three-letter one by itself.\n"
      "    val SCRIPT_LANGUAGES = arrayOf(%s)\n" % ", ".join('"%s"' % l for l in sorted(set(LIVING.values()))))

check = "--check" in sys.argv
for path, text in ((OUT_H, h), (OUT_KT, kt)):
    old = open(path, encoding="utf-8").read() if os.path.exists(path) else None
    if check:
        if old != text: sys.exit("out of date: " + path)
    elif old != text:
        open(path, "w", encoding="utf-8").write(text)
print("Unicode %s: emoji %d, bidi %d, letters %d, latin %d, span slots %d ranges, family %d ranges, fold %d, kotlin LCI %d ranges, living scripts %d (%d on fixed slots %d-%d)"
      % (ver, len(EMOJI), len(BIDI), len(LETTER), len(LATIN), len(span), len(fam), len(fold), len(lci),
         len(LIVING), len(SLOT_FIXED), SLOT_FIXED_FIRST, SLOT_FIXED_FIRST + len(SLOT_FIXED) - 1))
