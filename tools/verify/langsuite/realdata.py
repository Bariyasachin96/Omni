"""More labelled text for the suite, from tools/.cache/langdata (fetch_data.sh):

    realdata.py langdata_dir Scripts.txt sets.txt >> cases.tsv

  class "ui"       Android's own UI strings (Settings, SystemUI, framework) in
                   each locale, skipping any left untranslated -- what a screen
                   reader actually reads all day: labels, menu items, short
                   sentences
  class "tatoeba"  everyday sentences from Tatoeba

Each text is cut into spans the way the core cuts it -- a letter of another
script starts a new span, digits, spaces and punctuation stay with the span
they are in -- and only the spans in the label's own script are kept, so a
Hindi string that names "Wi-Fi" gives its Devanagari part, as the core would
hand it to the detector. Kept once per language by its letters; tune or test
by a hash of the text, so the split is stable across runs. Only the languages
of sets.txt, and at most CAP texts of each language and class (the same ones
every run: the lowest hashes).
"""
import glob
import html
import os
import re
import sys
import unicodedata
import xml.etree.ElementTree as ET
import zlib

langdata, scripts_txt, sets_txt = sys.argv[1:4]
WANTED = set()
for line in open(sets_txt, encoding="utf-8"):
    if not line.startswith("#"):
        WANTED.update(line.split())
CAP = {"ui": 2500, "tatoeba": 1500}

SCRIPT_SLOT = {"Latin": 1, "Cyrillic": 2, "Arabic": 3, "Devanagari": 4, "Han": 5, "Hiragana": 5, "Katakana": 5,
               "Bengali": 6}
ranges = []
for line in open(scripts_txt, encoding="utf-8"):
    line = line.split("#")[0].strip()
    if not line:
        continue
    cps, script = [x.strip() for x in line.split(";")]
    lo, _, hi = cps.partition("..")
    ranges.append((int(lo, 16), int(hi or lo, 16), script))
ranges.sort()
starts = [r[0] for r in ranges]


def script_of(ch):
    import bisect
    cp = ord(ch)
    i = bisect.bisect_right(starts, cp) - 1
    if i >= 0 and ranges[i][0] <= cp <= ranges[i][1]:
        return ranges[i][2]
    return "Unknown"


def slot_of(ch):
    """1..6 a detector slot, 0 another script, None neutral."""
    if ch == "ー":            # the long-vowel mark: kana by its extensions
        return 5
    s = script_of(ch)
    if s in ("Common", "Inherited", "Unknown"):
        return None
    return SCRIPT_SLOT.get(s, 0)


def spans(text, want):
    """The parts of text the core would hand the detector as spans of slot want."""
    out, cur, cur_slot = [], [], None
    for ch in text:
        sl = slot_of(ch)
        if sl is not None and sl != cur_slot and cur_slot is not None:
            out.append((cur_slot, "".join(cur)))
            cur = []
        if sl is not None:
            cur_slot = sl
        cur.append(ch)
    if cur:
        out.append((cur_slot, "".join(cur)))
    res = []
    for sl, t in out:
        t = " ".join(t.split()).strip(" -:,.;()[]\"'/|")
        if sl == want and any(unicodedata.category(c)[0] == "L" for c in t):
            res.append(t)
    return res


SLOT = {}
for lang in "be bg kk ky mk mn ru sr tg uk".split():
    SLOT[lang] = 2
for lang in "ar fa ps sd ur".split():
    SLOT[lang] = 3
for lang in "hi mr ne".split():
    SLOT[lang] = 4
for lang in "zh ja".split():
    SLOT[lang] = 5
for lang in "bn as".split():
    SLOT[lang] = 6

AOSP = {"en": "en", "ja": "ja", "zh-rCN": "zh", "zh-rTW": "zh", "zh-rHK": "zh", "hi": "hi", "mr": "mr", "ne": "ne",
        "bn": "bn", "as": "as", "ar": "ar", "fa": "fa", "ur": "ur", "ru": "ru", "uk": "uk", "bg": "bg", "sr": "sr",
        "mk": "mk", "be": "be", "kk": "kk", "ky": "ky", "mn": "mn", "es": "es", "fr": "fr", "it": "it",
        "pt-rBR": "pt", "pt-rPT": "pt", "de": "de", "in": "id", "ms": "ms", "nl": "nl", "ca": "ca", "ro": "ro",
        "pl": "pl", "cs": "cs", "sk": "sk", "tr": "tr", "vi": "vi", "tl": "fil", "sv": "sv", "da": "da", "nb": "no",
        "fi": "fi", "hu": "hu", "hr": "hr", "sl": "sl", "bs": "bs", "sq": "sq", "et": "et", "lv": "lv", "lt": "lt",
        "is": "is", "gl": "gl", "eu": "eu", "af": "af", "sw": "sw", "zu": "zu", "uz": "uz", "az": "az"}
TATOEBA = {"jpn": "ja", "cmn": "zh", "yue": "zh", "hin": "hi", "mar": "mr", "npi": "ne", "ben": "bn", "asm": "as",
           "urd": "ur", "pes": "fa", "ara": "ar", "pus": "ps", "rus": "ru", "ukr": "uk", "bul": "bg", "srp": "sr",
           "mkd": "mk", "bel": "be", "kaz": "kk", "spa": "es", "fra": "fr", "ita": "it", "por": "pt", "deu": "de",
           "ind": "id", "zsm": "ms", "eng": "en"}
FORMAT = re.compile(r"%(\d+\$)?[-#+ 0,(]*\d*(\.\d+)?[sdfxXc%]|\^\d")
TAG = re.compile(r"<[^>]+>")


ICU = re.compile(r"\{\s*\w+\s*,\s*(plural|select|selectordinal)\s*,")


def clean(text):
    if ICU.search(text):
        return ""          # an ICU message template, never read out as written
    text = text.replace("\\n", " ").replace("\\'", "'").replace('\\"', '"').replace("\\@", "@").replace("\\?", "?")
    text = TAG.sub(" ", text)
    text = html.unescape(text)
    text = FORMAT.sub(" ", text)
    return " ".join(text.split())


def strings(path):
    """name -> text for every translatable string, plural and array item."""
    out = {}
    try:
        root = ET.parse(path).getroot()
    except Exception:
        return out
    for el in root:
        if el.get("translatable") == "false":
            continue
        name = el.get("name", "")
        items = [el] if el.tag == "string" else list(el) if el.tag in ("plurals", "string-array") else []
        for i, it in enumerate(items):
            # placeholders (xliff:g) carry no language: drop what they hold
            for child in list(it):
                if child.tag.endswith("}g"):
                    child.text = " "
            out["%s#%d" % (name, i)] = clean("".join(it.itertext()))
    return out


rows, seen = [], set()


def add(label, cls, text):
    if label not in WANTED:
        return
    want = SLOT.get(label, 1)
    for t in spans(text, want):
        k = (label, "".join(c for c in t if unicodedata.category(c)[0] in "LM"))
        if k in seen:
            continue
        seen.add(k)
        split = "tune" if zlib.crc32(("%s|%s" % k).encode("utf-8")) % 2 == 0 else "test"
        rows.append((label, want, cls, split, t))


english = {}
for f in ("settings", "systemui", "core"):
    english.update(strings(os.path.join(langdata, "aosp", "en-%s.xml" % f)))
for loc, label in AOSP.items():
    for f in ("settings", "systemui", "core"):
        for name, text in strings(os.path.join(langdata, "aosp", "%s-%s.xml" % (loc, f))).items():
            if loc != "en" and english.get(name) == text:
                continue          # left untranslated
            add(label, "ui", text)
for iso, label in TATOEBA.items():
    path = os.path.join(langdata, "tatoeba", iso + ".tsv")
    if not os.path.exists(path):
        continue
    n = 0
    for line in open(path, encoding="utf-8", errors="replace"):
        parts = line.rstrip("\n").split("\t")
        if len(parts) == 3 and n < 3000:
            before = len(rows)
            add(label, "tatoeba", parts[2])
            n += len(rows) > before
groups = {}
for r in rows:
    groups.setdefault((r[0], r[2]), []).append(r)
rows = []
for k, g in groups.items():
    g.sort(key=lambda r: zlib.crc32(r[4].encode("utf-8")))
    rows.extend(g[:CAP[k[1]]])
for r in rows:
    sys.stdout.write("%s\t%d\t%s\t%s\t%s\n" % r)
counts = {}
for r in rows:
    counts[(r[0], r[2])] = counts.get((r[0], r[2]), 0) + 1
sys.stderr.write("real data: %d cases (%s)\n" % (len(rows), ", ".join("%s/%s %d" % (k[0], k[1], v) for k, v in sorted(counts.items()) if SLOT.get(k[0]))))
