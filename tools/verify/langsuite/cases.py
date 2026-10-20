"""The labelled texts the suite asks both detectors about, one per line:

    cases.py udhr2_dir phrases.txt out.tsv  -> label TAB slot TAB class TAB split TAB text

From the UDHR (NLTK's udhr2) in every language the model has, and in the
ones it lacks that the app routes through a shared script (Sanskrit, Bhojpuri,
Maithili, Shahmukhi Punjabi): the first 1, 3, 6 and 15 words of each line
(characters 2, 4, 8, 16 where a script is written without spaces) and the
whole line. A text is classed by its OWN length -- 1w, 2-3w, 4-7w, 8-15w,
16w+ -- and kept once per language by its letters alone, so "Article 10."
... "Article 30." are one case, not twenty. phrases.txt, the everyday short
text, is class "phrase". The split is "tune" for the UDHR's even lines and
each language's even phrases, "test" for the odd ones: whatever is decided on
"tune" is measured on "test", text it never saw. The slot is the core's script slot for the label's
script (1 Latin, 2 Cyrillic, 3 Arabic, 4 Devanagari, 5 Han/Kana, 6 Bengali;
0 for a script the core decides without a detector).
"""
import os
import sys
import unicodedata

udhr, phrases, out = sys.argv[1:4]
UDHR = {
    "afr": "af", "amh": "am", "arb": "ar", "azj_latn": "az", "bel": "be", "bul": "bg", "ben": "bn",
    "bos_latn": "bs", "cat": "ca", "ceb": "ceb", "cos": "co", "ces": "cs", "cym": "cy", "dan": "da",
    "deu": "de", "ell_monotonic": "el", "eng": "en", "epo": "eo", "spa": "es", "est": "et", "eus": "eu",
    "pes_1": "fa", "fin": "fi", "tgl": "fil", "fra": "fr", "fri": "fy", "gle": "ga", "gla": "gd",
    "glg": "gl", "guj": "gu", "hau_NG": "ha", "haw": "haw", "hin": "hi", "hrv": "hr", "hat_kreyol": "ht",
    "hun": "hu", "hye": "hy", "ind": "id", "ibo": "ig", "isl": "is", "ita": "it", "heb": "iw", "jpn": "ja",
    "jav": "jv", "kat": "ka", "kaz": "kk", "khm": "km", "kan": "kn", "kor": "ko", "ckb": "ku", "kir": "ky",
    "lat": "la", "ltz": "lb", "lao": "lo", "lit": "lt", "lav": "lv", "plt": "mg", "mri": "mi", "mkd": "mk",
    "mal": "ml", "khk": "mn", "mar": "mr", "mly_latn": "ms", "mlt": "mt", "mya": "my", "nep": "ne",
    "nld": "nl", "nob": "no", "nya_chechewa": "ny", "pan": "pa", "pol": "pl", "pbu": "ps", "por_PT": "pt",
    "ron": "ro", "rus": "ru", "slk": "sk", "slv": "sl", "smo": "sm", "sna": "sn", "som": "so", "als": "sq",
    "srp_cyrl": "sr", "sot": "st", "sun": "su", "swe": "sv", "swh": "sw", "tam": "ta", "tgk": "tg",
    "tha": "th", "tur": "tr", "ukr": "uk", "urd": "ur", "uzn_latn": "uz", "vie": "vi", "xho": "xh",
    "ydd": "yi", "yor": "yo", "cmn_hans": "zh", "zul": "zu",
    "san": "sa", "bho": "bho", "mai": "mai", "pnb": "pnb",
}
SLOT = {}
for lang in "be bg kk ky mk mn ru sr tg uk".split():
    SLOT[lang] = 2
for lang in "ar fa ps sd ur pnb".split():
    SLOT[lang] = 3
for lang in "hi mr ne sa bho mai".split():
    SLOT[lang] = 4
for lang in "zh ja".split():
    SLOT[lang] = 5
for lang in "bn as".split():
    SLOT[lang] = 6
for lang in "am el gu hy iw ka km kn ko lo ml my pa si ta te th yi".split():
    SLOT[lang] = 0
NO_SPACES = {"zh", "ja", "th", "lo", "km", "my"}


def key(text):
    return "".join(c for c in text if unicodedata.category(c)[0] in "LM")


def bucket(label, text):
    n = len(text) if label in NO_SPACES else len(text.split())
    edges = (2, 4, 8, 16) if label in NO_SPACES else (1, 3, 7, 15)
    names = ("1w", "2-3w", "4-7w", "8-15w", "16w+")
    for edge, name in zip(edges, names):
        if n <= edge:
            return name
    return names[-1]


rows, seen = [], set()
for name, label in sorted(UDHR.items(), key=lambda x: x[1]):
    with open(os.path.join(udhr, name + ".txt"), encoding="utf-8", errors="replace") as f:
        lines = [" ".join(x.split()) for x in f.read().split("\n")]
    for number, line in enumerate([x for x in lines if x]):
        split = "tune" if number % 2 == 0 else "test"
        if label in NO_SPACES:
            texts = [line[:n] for n in (2, 4, 8, 16)] + [line]
        else:
            words = line.split()
            texts = [" ".join(words[:n]) for n in (1, 3, 6, 15)] + [line]
        for t in texts:
            k = (label, key(t))
            if k[1] and k not in seen:
                seen.add(k)
                rows.append((label, SLOT.get(label, 1), bucket(label, t), split, t))
count = {}
with open(phrases, encoding="utf-8") as f:
    for line in f:
        line = line.rstrip("\n")
        if line and not line.startswith("#"):
            label, text = line.split("\t", 1)
            count[label] = count.get(label, 0) + 1
            split = "tune" if count[label] % 2 == 0 else "test"
            rows.append((label, SLOT.get(label.split("-")[0], 1) if label != "hi-Latn" else 1, "phrase", split, text))
with open(out, "w", encoding="utf-8") as f:
    for label, slot, cls, split, text in rows:
        f.write("%s\t%d\t%s\t%s\t%s\n" % (label, slot, cls, split, text))
print("cases: %d" % len(rows))
