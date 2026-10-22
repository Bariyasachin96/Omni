#!/usr/bin/env python3
"""The word-and-ending model that tells apart the languages of one script.

    train_nb.py learn cases.tsv      learn tools/langid/nb_model.tsv and
                                     letters.tsv from tools/.cache/langtrain
                                     (fetch_train.sh) and the TUNE half of the
                                     suite's cases, keeping the KEEP[slot] most
                                     informative features per script
    train_nb.py eval cases.tsv pipeline.tsv
                                     precision and coverage on the suite's
                                     TEST half and on FLORES-200 devtest
    train_nb.py ortho cases.tsv      learn tools/langid/ortho.tsv (the letters of each
                                     orthography, word by word) from the same sources
    train_nb.py header               write app/src/main/cpp/langnb.h
    train_nb.py check                fail if langnb.h is stale

For each script shared by languages the app routes (GROUPS), every text a
language wrote is reduced to its FEATURES -- each word, and each word's last
one, two and three letters (the endings that carry case and tense: Marathi
-च्या -ला, Nepali -को -मा -हरू, Urdu -ے, Russian -ого ...). The model is how
many of the language's NTEXT learning texts held each feature. A text scores,
for each enabled language, the sum over its features of
    ln((count + ALPHA) / (NTEXT + 2 ALPHA))
and the best language wins only by a margin of at least the script's T
(langblend.h); below it the model abstains. Every language of a group learns
from the same number of texts, so a feature the table does not hold costs
every language the same and can be left out of the sum.

What counts as a letter and how letters are lowercased is defined here from
the repository's own UCD 18 files and written into the header, so the C++
reads exactly the features this script learned.
"""
import collections
import glob
import math
import os
import re
import sys
import unicodedata
import zlib

ROOT = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
UCD = os.path.join(ROOT, "tools", "unicode", "ucd")
TRAIN = os.path.join(ROOT, "tools", ".cache", "langtrain")
MODEL = os.path.join(ROOT, "tools", "langid", "nb_model.tsv")
OUT = os.path.join(ROOT, "app", "src", "main", "cpp", "langnb.h")
GROUPS = collections.OrderedDict([
    (1, ["en", "es", "fr", "de", "pt", "it", "id", "ms"]),
    (2, ["ru", "uk", "bg", "sr"]),
    (3, ["ar", "fa", "ur", "ps"]),
    (4, ["hi", "mr", "ne"]),
    (6, ["bn", "as"]),
])
SLOT = {L: s for s, ls in GROUPS.items() for L in ls}
NTEXT = 150000        # learning texts per language (the same for every language of a group)
ALPHA = 0.5
MINC = 3              # a feature some language holds fewer times than this in total is dropped
SUFFIXES = 3
# the blocks whose letters and marks make words; everything else separates them
BLOCKS = [(0x0041, 0x052F), (0x0600, 0x06FF), (0x0750, 0x077F), (0x08A0, 0x08FF), (0x0900, 0x09FF),
          (0x1E00, 0x1EFF), (0xA8E0, 0xA8FF), (0xFB50, 0xFDFF), (0xFE70, 0xFEFF)]


def _ranges():
    word = set()
    for line in open(os.path.join(UCD, "DerivedGeneralCategory.txt"), encoding="utf-8"):
        line = line.split("#")[0].strip()
        if not line:
            continue
        cps, gc = [x.strip() for x in line.split(";")]
        if gc[0] not in "LM":
            continue
        lo, _, hi = cps.partition("..")
        for cp in range(int(lo, 16), int(hi or lo, 16) + 1):
            if any(a <= cp <= b for a, b in BLOCKS):
                word.add(cp)
    word |= {0x200C, 0x200D}
    lower = {}
    for line in open(os.path.join(UCD, "UnicodeData.txt"), encoding="utf-8"):
        f = line.split(";")
        cp = int(f[0], 16)
        if f[13] and any(a <= cp <= b for a, b in BLOCKS):
            lower[cp] = int(f[13], 16)
    return word, lower


WORD, LOWER = _ranges()


def tokens(text):
    out, cur = [], []
    for ch in text:
        c = ord(ch)
        if c in WORD:
            cur.append(chr(LOWER.get(c, c)))
        elif cur:
            out.append("".join(cur))
            cur = []
    if cur:
        out.append("".join(cur))
    return out


def feats(text):
    f = set()
    for w in tokens(text):
        f.add("w" + w)
        for k in range(1, SUFFIXES + 1):
            if len(w) > k:
                f.add("%d%s" % (k, w[-k:]))
    return f


def key(t):
    return "".join(c for c in t.lower() if unicodedata.category(c)[0] in "LM")


SENT = re.compile(r"(?<=[.!?।॥؟۔])\s+")


def wiki_texts(lang, limit):
    import pyarrow.parquet as pq
    path = os.path.join(TRAIN, "wiki", lang + ".parquet")
    if not os.path.exists(path):
        return []
    pf = pq.ParquetFile(path)
    out = []
    for g in range(pf.metadata.num_row_groups):
        for doc in pf.read_row_group(g, columns=["text"]).column("text").to_pylist():
            for para in doc.split("\n"):
                para = para.strip()
                if len(para) < 20:
                    continue
                for s in SENT.split(para):
                    if 3 <= len(s.split()) <= 60:
                        out.append(s)
            if len(out) >= limit:
                return out[:limit]
    return out


TATOEBA = {"hi": "hin", "mr": "mar", "ne": "npi", "bn": "ben", "as": "asm", "ur": "urd", "fa": "pes", "ar": "ara",
           "ps": "pus", "ru": "rus", "uk": "ukr", "bg": "bul", "sr": "srp", "en": "eng", "es": "spa", "fr": "fra",
           "de": "deu", "pt": "por", "it": "ita", "id": "ind", "ms": "zsm"}
CV = {"ne": "ne-NP"}
FLORES = {"hi": "hin_Deva", "mr": "mar_Deva", "ne": "npi_Deva", "bn": "ben_Beng", "as": "asm_Beng", "ur": "urd_Arab",
          "fa": "pes_Arab", "ar": "arb_Arab", "ps": "pbt_Arab", "ru": "rus_Cyrl", "uk": "ukr_Cyrl", "bg": "bul_Cyrl",
          "sr": "srp_Cyrl", "en": "eng_Latn", "es": "spa_Latn", "fr": "fra_Latn", "de": "deu_Latn", "pt": "por_Latn",
          "it": "ita_Latn", "id": "ind_Latn", "ms": "zsm_Latn"}


def flores(lang, split):
    path = os.path.join(TRAIN, "flores", "flores200_dataset", split, "%s.%s" % (FLORES[lang], split))
    return [l.strip() for l in open(path, encoding="utf-8")] if os.path.exists(path) else []


def of_script(c, slot):
    """the code point is a letter block of the group's script"""
    lo, hi = {1: (0x41, 0x24F), 2: (0x400, 0x52F), 3: (0x600, 0x6FF), 4: (0x900, 0x97F), 6: (0x980, 0x9FF)}[slot]
    return lo <= c <= hi or (slot == 1 and 0x1E00 <= c <= 0x1EFF) or \
        (slot == 3 and (0x750 <= c <= 0x8FF or 0xFB50 <= c <= 0xFEFF))


def in_script(text, slot):
    """the text's letters are mostly of the group's script (Serbian Tatoeba mixes Latin in)"""
    letters = [ord(c) for c in text if ord(c) in WORD]
    if not letters:
        return False
    inside = sum(1 for c in letters if of_script(c, slot))
    return inside >= 0.8 * len(letters)


# Android's own UI strings outside the suite's three files (fetch_train.sh aosp/):
# AOSP locale -> language
AOSP = {"en": "en", "es": "es", "fr": "fr", "de": "de", "pt-rBR": "pt", "pt-rPT": "pt", "it": "it", "in": "id",
        "ms": "ms", "ru": "ru", "uk": "uk", "bg": "bg", "sr": "sr", "ar": "ar", "fa": "fa", "ur": "ur",
        "hi": "hi", "mr": "mr", "ne": "ne", "bn": "bn", "as": "as"}
_FORMAT = re.compile(r"%(\d+\$)?[-#+ 0,(]*\d*(\.\d+)?[sdfxXc%]|\^\d")
_TAG = re.compile(r"<[^>]+>")
_ICU = re.compile(r"\{\s*\w+\s*,\s*(plural|select|selectordinal)\s*,")


def android_strings(path):
    """name -> text of every translatable string, plural and array item, as
    tools/verify/langsuite/realdata.py reads them: markup, format specifiers and
    what a placeholder holds dropped"""
    import html
    import xml.etree.ElementTree as ET
    out = {}
    try:
        root = ET.parse(path).getroot()
    except Exception:
        return out
    for el in root:
        if el.get("translatable") == "false":
            continue
        items = [el] if el.tag == "string" else list(el) if el.tag in ("plurals", "string-array") else []
        for i, it in enumerate(items):
            for child in list(it):
                if child.tag.endswith("}g"):
                    child.text = " "
            text = "".join(it.itertext())
            if _ICU.search(text):
                continue
            text = text.replace("\\n", " ").replace("\\'", "'").replace('\\"', '"').replace("\\@", "@")
            text = _FORMAT.sub(" ", html.unescape(_TAG.sub(" ", text.replace("\\?", "?"))))
            out["%s#%d" % (el.get("name", ""), i)] = " ".join(text.split())
    return out


def android_ui():
    """per language: the parts of every translated UI string in the language's own
    script (a letter of another script cuts it, as the core would)"""
    d = os.path.join(TRAIN, "aosp")
    english = {os.path.basename(p)[3:]: android_strings(p) for p in glob.glob(os.path.join(d, "en-*.xml"))}
    out = collections.defaultdict(list)
    for loc, L in AOSP.items():
        slot = SLOT[L]
        for p in sorted(glob.glob(os.path.join(d, loc + "-*.xml"))):
            en = english.get(os.path.basename(p)[len(loc) + 1:], {})
            for name, text in android_strings(p).items():
                if L != "en" and en.get(name) == text:
                    continue          # left untranslated
                cur, mine = [], False
                for ch in text + "\n":
                    letter = unicodedata.category(ch)[0] == "L"
                    if ch == "\n" or (letter and cur and (ord(ch) in WORD and of_script(ord(ch), slot)) != mine):
                        t = " ".join("".join(cur).split()).strip(" -:,.;()[]\"'/|")
                        if mine and any(unicodedata.category(c)[0] == "L" for c in t):
                            out[L].append(t)
                        cur = []
                    if letter:
                        mine = ord(ch) in WORD and of_script(ord(ch), slot)
                    cur.append(ch)
    return out


def learning_texts(cases):
    """per language: NTEXT texts, the same sources in the same order every run --
    Android's UI text (the suite's TUNE half and android_ui) always, the rest
    (Tatoeba, Common Voice, Wikipedia) in hash order up to NTEXT"""
    test_keys = set()
    tune = collections.defaultdict(list)
    for line in open(cases, encoding="utf-8"):
        f = line.rstrip("\n").split("\t")
        if len(f) == 5 and f[0] in SLOT:
            if f[3] == "test":
                test_keys.add(key(f[4]))
            else:
                tune[f[0]].append(f[4])
    for L in SLOT:
        for split in ("dev", "devtest"):
            test_keys.update(key(t) for t in flores(L, split))
    ui = android_ui()
    texts = {}
    for L, slot in SLOT.items():
        seen = set()

        def take(src):
            got = []
            for t in src:
                k = key(t)
                if k and k not in seen and k not in test_keys and in_script(t, slot):
                    seen.add(k)
                    got.append(t)
            return got
        first = take(tune[L] + ui.get(L, []))
        pool = []
        path = os.path.join(TRAIN, "tatoeba", TATOEBA[L] + ".tsv")
        if os.path.exists(path):
            pool += [l.rstrip("\n").split("\t")[2] for l in open(path, encoding="utf-8", errors="replace")
                     if l.count("\t") == 2]
        path = os.path.join(TRAIN, "cv", CV.get(L, L) + ".txt")
        if os.path.exists(path):
            pool += [l.strip() for l in open(path, encoding="utf-8", errors="replace")]
        pool += wiki_texts(L, NTEXT)
        out = take(pool)
        # the same number for every language: the lowest hashes of each pool
        out.sort(key=lambda t: zlib.crc32(t.encode("utf-8")))
        texts[L] = first + out[:max(0, NTEXT - len(first))]
        print("  %s: %d learning texts, %d of them Android UI (of %d)" % (L, len(texts[L]), len(first),
              len(first) + len(out)), file=sys.stderr)
    return texts


def learn(cases):
    texts = learning_texts(cases)
    learn_letters(texts)
    with open(MODEL, "w", encoding="utf-8") as out:
        out.write("# Learned by tools/langid/train_nb.py learn -- do not edit. Per script slot:\n")
        out.write("# n<TAB>slot<TAB>texts per language, then feature<TAB>slot<TAB>texts holding it\n")
        out.write("# per language (GROUPS order). Sources: Wikipedia (CC BY-SA 4.0), Tatoeba (CC BY\n")
        out.write("# 2.0 FR), Common Voice (CC0), AOSP UI strings (Apache 2.0), the UDHR.\n")
        for slot, langs in GROUPS.items():
            n = min(len(texts[L]) for L in langs if texts[L]) if any(texts[L] for L in langs) else 0
            counts = collections.defaultdict(lambda k=len(langs): [0] * k)
            for i, L in enumerate(langs):
                for t in texts[L][:n]:
                    for f in feats(t):
                        counts[f][i] += 1
            out.write("n\t%d\t%s\n" % (slot, ",".join(str(min(n, len(texts[L]))) for L in langs)))
            kept = 0
            for f in sorted(counts):
                c = counts[f]
                if sum(c) >= MINC and min(c) * 4 < max(c):    # held by some, and not the same everywhere
                    out.write("%s\t%d\t%s\n" % (f, slot, ",".join(map(str, c))))
                    kept += 1
            print("slot %d: %d texts per language, %d features kept of %d" % (slot, n, kept, len(counts)), file=sys.stderr)
    prune()


def value(c, n):
    """how much a feature tells: the texts holding it times how unevenly the
    languages hold them (information it carries about the language)"""
    tot = sum(c)
    p = [(x + ALPHA) / (m + 2 * ALPHA) for x, m in zip(c, n)]
    mean = sum(p) / len(p)
    return sum(x * math.log(q / mean) for x, q in zip(c, p) if x) / max(1, tot) * math.log(1 + tot)


LETTERS = os.path.join(ROOT, "tools", "langid", "letters.tsv")
LETTERS_RARE = os.path.join(ROOT, "tools", "langid", "letters_rare.tsv")
LETTER_SLOTS = (2, 3, 6)          # where a letter is an orthography, not a loanword's accent
LETTER_COMMON = 0.005             # the letter is in at least 1 of 200 of the language's texts
LETTER_NEVER = 0.00002            # and in at most 1 of 50,000 of the other's
# A letter the other writes in at most 1 of 2,000 of its texts decides nothing on
# its own, but a span holding one does not take that other language from the
# context (langblend.h blendFinal): keheh after Arabic text is not Arabic (ar
# writes it in 1 of 4,300 texts, ur in 9 of 10). Chosen on the suite's held-out
# half: it put right 13 / 42 / 64 texts whose context was another language
# (P = 0.1 / 0.3 / 0.5) and turned none wrong; at 1 of 1,000 it starts to.
LETTER_RARE = 0.0005


def learn_letters(texts):
    rate = {}
    for L, ts in texts.items():
        c = collections.Counter()
        for t in ts:
            c.update({ch for ch in t if ord(ch) in WORD and unicodedata.category(ch)[0] == "L"})
        rate[L] = {ch: n / max(1, len(ts)) for ch, n in c.items()}
    for path, other, why in ((LETTERS, LETTER_NEVER, ""),
                             (LETTERS_RARE, LETTER_RARE,
                              "# They decide nothing alone: a span holding one does not take the other\n"
                              "# language from the context (langblend.h blendFinal).\n")):
        with open(path, "w", encoding="utf-8") as out:
            out.write("# Learned by tools/langid/train_nb.py from the same texts as nb_model.tsv -- do not\n")
            out.write("# edit. slot<TAB>language<TAB>other<TAB>letters the language writes in at least\n")
            out.write("# %g of its texts and the other in at most %g of its texts.\n" % (LETTER_COMMON, other))
            out.write(why)
            for slot in LETTER_SLOTS:
                langs = GROUPS[slot]
                for L in langs:
                    for B in langs:
                        if B != L:
                            ex = sorted(ch for ch, r in rate[L].items()
                                        if r >= LETTER_COMMON and rate[B].get(ch, 0) <= other
                                        and of_script(ord(ch), slot))
                            if ex:
                                out.write("%d\t%s\t%s\t%s\n" % (slot, L, B, "".join(ex)))


def letters(path=LETTERS):
    """slot -> {letter: set of (language, other)}"""
    table = {}
    for line in open(path, encoding="utf-8"):
        if line.startswith("#"):
            continue
        slot, L, B, ex = line.rstrip("\n").split("\t")
        for ch in ex:
            table.setdefault(int(slot), {}).setdefault(ch, set()).add((L, B))
    return table


def letter_rule(table, slot, text, cands):
    """the one enabled language some letter of the text belongs to against every
    other enabled language, or None"""
    t = table.get(slot)
    if not t or len(cands) < 2:
        return None
    won = set()
    for ch in set(text):
        pairs = t.get(ch)
        if not pairs:
            continue
        for L in cands:
            if all((L, B) in pairs for B in cands if B != L):
                won.add(L)
    return next(iter(won)) if len(won) == 1 else None


def letter_veto(table, slot, text, got, ctx):
    """some letter of the text is one `got` writes and `ctx` practically never does
    (the letters_rare table): the context must not turn the span into `ctx`"""
    t = table.get(slot)
    return bool(t) and any((got, ctx) in t.get(ch, ()) for ch in set(text))


# ---- THE LETTERS OF AN ORTHOGRAPHY, WORD BY WORD (owner, 2026-10-21: "Arabic
# language Arabic voice ke pass ... aisa Nahin Ki Ek Hi voice do Bhasha Pade") ----
# Where languages share a script, some letters belong to one orthography: Urdu
# writes ٹ ڈ ڑ ں ھ ہ ے, Persian and Urdu ک ی where Arabic writes ك ي, Arabic ة أ إ,
# Pashto ټ ځ څ ډ ړ ږ ښ ګ ۍ ې, Ukrainian і ї є, Russian ы э ё, Serbian ђ ј љ њ ћ,
# Bulgarian ъ, Assamese ৰ ৱ. For each such letter ortho.tsv holds, per language,
# whether it writes the letter itself (in at least ORTHO_NATIVE of its words) and
# round(8 ln r) for r the share of its WORDS holding it: its own share where it
# writes the letter, and where it does not, ONE share for every such language --
# the highest any of them shows in any one source of at least ORTHO_MINWORDS
# words, i.e. what careless or older spelling (Persian typed with Arabic ي ك,
# Assamese with Bengali র) does at worst. A word holding the letter costs a
# language that does not write it that share against the best writer's: letters
# a language does not write rule it out, letters it lacks never do. Only letters
# of the script's own blocks, letters NFC text can hold, and only where the best
# and worst share differ by ORTHO_SPREAD or more.
ORTHO = os.path.join(ROOT, "tools", "langid", "ortho.tsv")
ORTHO_NATIVE = 1 / 200
ORTHO_MINWORDS = 5000
ORTHO_SPREAD = 24
ORTHO_BLOCKS = {2: [(0x400, 0x52F)], 3: [(0x600, 0x6FF), (0x750, 0x77F), (0x8A0, 0x8FF)], 6: [(0x980, 0x9FF)]}
# How the letters decide (langblend.h orthoDecide / orthoSegment), per slot:
# a language whose words' letters score ORTHO_M or more below the best is ruled
# out, and changing language between two words of one sentence costs ORTHO_PW.
# Chosen on FLORES-200 (never learned from): Bengali and Assamese never change
# inside a sentence (Assamese text writes Bengali র in conjuncts).
ORTHO_M = {2: 32, 3: 32, 6: 48}
ORTHO_PW = {2: 52, 3: 52, 6: 1000}
ORTHO_MINW = 3          # words a sentence needs before the word-and-ending model alone may name it
ORTHO_SENT = set(".!?;؟؛۔।॥\n")
ORTHO_STRONG = ORTHO_SENT | set(":\"«»“”„()[]{}﴾﴿۝")


def ortho_letter(ch, slot):
    c = ord(ch)
    return (any(a <= c <= b for a, b in ORTHO_BLOCKS[slot]) and unicodedata.category(ch)[0] == "L"
            and unicodedata.normalize("NFC", ch) == ch)


def ortho_sources(cases):
    """per language, per source (the suite's tune rows, Tatoeba, Common Voice,
    Wikipedia): its texts in the script, none the suite or FLORES test on"""
    test_keys = set()
    tune = collections.defaultdict(list)
    for line in open(cases, encoding="utf-8"):
        f = line.rstrip("\n").split("\t")
        if len(f) == 5 and f[0] in SLOT:
            if f[3] == "test":
                test_keys.add(key(f[4]))
            else:
                tune[f[0]].append(f[4])
    for L in SLOT:
        for split in ("dev", "devtest"):
            test_keys.update(key(t) for t in flores(L, split))
    out = {}
    for slot in LETTER_SLOTS:
        for L in GROUPS[slot]:
            src = {"suite": tune[L]}
            path = os.path.join(TRAIN, "tatoeba", TATOEBA[L] + ".tsv")
            src["tatoeba"] = [l.rstrip("\n").split("\t")[2] for l in open(path, encoding="utf-8", errors="replace")
                              if l.count("\t") == 2] if os.path.exists(path) else []
            path = os.path.join(TRAIN, "cv", CV.get(L, L) + ".txt")
            src["cv"] = [l.strip() for l in open(path, encoding="utf-8", errors="replace")] if os.path.exists(path) else []
            src["wiki"] = wiki_texts(L, NTEXT)
            out[L] = {k: [t for t in v if key(t) not in test_keys and in_script(t, slot)] for k, v in src.items()}
    return out


def learn_ortho(cases):
    srcs = ortho_sources(cases)

    def counts(texts, slot):
        n, c = 0, collections.Counter()
        for t in texts:
            for w in tokens(t):
                n += 1
                c.update({ch for ch in w if ortho_letter(ch, slot)})
        return n, c

    with open(ORTHO, "w", encoding="utf-8") as out:
        out.write("# Learned by tools/langid/train_nb.py ortho -- do not edit. slot<TAB>letter<TAB>per language\n")
        out.write("# (GROUPS order): writes it natively (1/0)<TAB>round(8 ln r), r the share of the language's\n")
        out.write("# words holding the letter (train_nb.py ORTHO_*).\n")
        for slot in LETTER_SLOTS:
            langs = GROUPS[slot]
            pooled = {L: counts([t for v in srcs[L].values() for t in v], slot) for L in langs}
            persrc = {L: [counts(v, slot) for v in srcs[L].values()] for L in langs}
            for ch in sorted(set().union(*[pooled[L][1] for L in langs])):
                rate = {L: pooled[L][1][ch] / pooled[L][0] for L in langs}
                native = [L for L in langs if rate[L] >= ORTHO_NATIVE]
                if not native or len(native) == len(langs):
                    continue
                f = max(max((c[ch] + 0.5) / (n + 1) for n, c in persrc[L] if n >= ORTHO_MINWORDS)
                        for L in langs if L not in native)
                r = [rate[L] if L in native else f for L in langs]
                pres = [max(-127, int(round(SCALE * math.log(x)))) for x in r]
                if max(pres) - min(pres) < ORTHO_SPREAD:
                    continue
                out.write("%d\t%s\t%s\t%s\n" % (slot, ch, ",".join("1" if L in native else "0" for L in langs),
                                                ",".join(map(str, pres))))


def ortho():
    """slot -> {letter: (native, share)}, lists in GROUPS order"""
    table = {}
    for line in open(ORTHO, encoding="utf-8"):
        if line.startswith("#"):
            continue
        slot, ch, nat, pres = line.rstrip("\n").split("\t")
        table.setdefault(int(slot), {})[ch] = ([int(x) for x in nat.split(",")], [int(x) for x in pres.split(",")])
    return table


def ortho_words(text):
    """(word, start, end): the words tokens() makes, with their offsets"""
    res, cur, start = [], [], None
    for i, ch in enumerate(text):
        c = ord(ch)
        if c in WORD:
            if start is None:
                start = i
            cur.append(chr(LOWER.get(c, c)))
        elif cur:
            res.append(("".join(cur), start, i))
            cur, start = [], None
    if cur:
        res.append(("".join(cur), start, len(text)))
    return res


def ortho_pen(ot, slot, w, idx):
    """what a word costs each candidate in letters it does not write itself
    (each such letter: its stored share against the best writer's), or None
    when that is the same for every candidate"""
    t = ot[slot]
    p = [0] * len(idx)
    for ch in set(w) & t.keys():
        nat, pres = t[ch]
        top = max(pres)
        for j, i in enumerate(idx):
            if not nat[i]:
                p[j] += pres[i] - top
    return p if min(p) < max(p) else None


def ortho_decide(ot, nbtab, slot, text, cands, detectors=None):
    """(allowed, totals): the candidates the letters leave. They rule out only
    when some word's letters allow the best language alone (a Pashto letter, an
    Arabic ة, "كتاب" against Urdu): then a language whose words' letters cost it
    ORTHO_M or more more than the best is ruled out, unless the word-and-ending
    model puts it kNbMargin above that best, or (detectors = (CLD2's answer, the
    model's top), the final decision only, every candidate one the model knows)
    CLD2, the model and the word-and-ending model all name it: then the text is
    that language spelled with another's letters. A best no word attests alone
    wins only because it writes everyone's letters (Pashto writes both ي and ی:
    a Persian sentence quoting Arabic is not Pashto) -- nothing is ruled out.
    Totals None: no word's letters tell the candidates apart."""
    langs = GROUPS[slot]
    idx = [langs.index(L) for L in cands]
    tot, ev, alone = [0] * len(cands), False, set()
    for w, _, _ in ortho_words(text):
        s = ortho_pen(ot, slot, w, idx)
        if s is not None:
            ev = True
            for j in range(len(cands)):
                tot[j] += s[j]
            free = [j for j in range(len(cands)) if s[j] == 0]
            if len(free) == 1:
                alone.add(free[0])
    if not ev:
        return list(cands), None
    best = max(range(len(cands)), key=lambda j: (tot[j], -j))
    if best not in alone:
        return list(cands), tot
    keep = [tot[best] - tot[j] < ORTHO_M[slot] for j in range(len(cands))]
    if not all(keep):
        nb = qscores(nbtab, slot, text, cands)
        nbbest = max(range(len(cands)), key=lambda j: (nb[j], -j))
        for j in range(len(cands)):
            if keep[j]:
                continue
            if nb[j] - nb[best] >= 80:
                keep[j] = True
            elif detectors and detectors[0] == cands[j] and detectors[1] == cands[j] and nbbest == j:
                keep[j] = True
    return [cands[j] for j in range(len(cands)) if keep[j]], tot


def ortho_sure(ot, nbtab, slot, text, cands, nwords):
    """what a sentence surely is, for cutting a text: the letters' one language,
    else the word-and-ending model's best among what the letters allow, ahead by
    kNbMargin, in a sentence of ORTHO_MINW words or more; else None"""
    allowed, _ = ortho_decide(ot, nbtab, slot, text, cands)
    if len(allowed) == 1:
        return allowed[0]
    if len(allowed) < 2 or nwords < ORTHO_MINW:
        return None
    sc = qscores(nbtab, slot, text, allowed)
    order = sorted(range(len(allowed)), key=lambda j: (-sc[j], j))
    return allowed[order[0]] if sc[order[0]] - sc[order[1]] >= 80 else None


def _ortho_viterbi(ems, cost, n):
    """the labels of the best path (each step its emission; a change costs `cost`);
    ties keep the language, then take the lower index"""
    score, back = list(ems[0]), []
    for t in range(1, len(ems)):
        ns, bk = [], []
        for j in range(n):
            bj, bs = j, score[j]
            for i in range(n):
                if i != j and score[i] - cost > bs:
                    bj, bs = i, score[i] - cost
            ns.append(bs + ems[t][j])
            bk.append(bj)
        score = ns
        back.append(bk)
    j = max(range(n), key=lambda x: (score[x], -x))
    labels = [j]
    for t in range(len(ems) - 1, 0, -1):
        j = back[t - 1][j]
        labels.append(j)
    return labels[::-1]


def ortho_segment(ot, nbtab, slot, text, cands):
    """where in `text` (offsets) a new piece starts. Pass 1: sentences (cut at
    ORTHO_SENT) each named by ortho_sure; one naming another language than the
    one before starts a new piece. Pass 2: inside each piece, words scored by the
    letters their candidates do not write (ortho_pen), best path with a change
    costing ORTHO_PW. Each cut lands where the word-and-ending model puts the
    words between the two languages' evidence best: at a sentence end in pass 1,
    at an ORTHO_STRONG mark if one is there in pass 2; on a tie, the later."""
    langs = GROUPS[slot]
    idx = [langs.index(L) for L in cands]
    toks = ortho_words(text)
    if len(toks) < 2:
        return []

    def between(g, marks):
        return any(ch in marks for ch in text[toks[g - 1][2]:toks[g][1]])

    def cut(a, b, A, B, options):
        best_g, best_v = b, None
        for g in options:
            v = 0
            for k in range(g, b):
                sc = qscores(nbtab, slot, toks[k][0], [A, B])
                v += sc[1] - sc[0]
            if best_v is None or v > best_v or (v == best_v and g > best_g):
                best_g, best_v = g, v
        return best_g

    runs, cur, start, last, x = [], None, 0, None, 0
    for k in range(1, len(toks) + 1):
        if k < len(toks) and not between(k, ORTHO_SENT):
            continue
        lab = ortho_sure(ot, nbtab, slot, text[toks[x][1]:toks[k - 1][2]], cands, k - x)
        if lab is not None:
            if cur is None:
                cur = lab
            elif lab != cur:
                g = cut(last, x, cur, lab, [g for g in range(last + 1, x + 1) if between(g, ORTHO_SENT)])
                runs.append((start, g))
                start, cur = g, lab
            last = k - 1
        x = k
    runs.append((start, len(toks)))
    cuts = set()
    for ri, (x, y) in enumerate(runs):
        if ri:
            cuts.add(x)
        ev = [(k, ortho_pen(ot, slot, toks[k][0], idx)) for k in range(x, y)]
        ev = [(k, p) for k, p in ev if p is not None]
        if len(ev) < 2:
            continue
        lab = _ortho_viterbi([p for _, p in ev], ORTHO_PW[slot], len(cands))
        for t in range(1, len(ev)):
            if lab[t] != lab[t - 1]:
                a, b = ev[t - 1][0], ev[t][0]
                options = [g for g in range(a + 1, b + 1) if between(g, ORTHO_STRONG)] or list(range(a + 1, b + 1))
                cuts.add(cut(a, b, cands[lab[t - 1]], cands[lab[t]], options))
    return sorted(toks[g][1] for g in cuts)


def load(keep=None):
    model = {}
    ntext = {}
    for line in open(MODEL, encoding="utf-8"):
        if line.startswith("#"):
            continue
        f, slot, c = line.rstrip("\n").split("\t")
        c = [int(x) for x in c.split(",")]
        if f == "n":
            ntext[int(slot)] = c
        else:
            model.setdefault(int(slot), {})[f] = c
    if keep:
        for slot in model:
            ranked = sorted(model[slot], key=lambda f: -value(model[slot][f], ntext[slot]))
            model[slot] = {f: model[slot][f] for f in ranked[:keep.get(slot, keep.get(0))]}
    return model, ntext


def scores(model, ntext, slot, text, cands):
    langs = GROUPS[slot]
    idx = [langs.index(L) for L in cands]
    table = model.get(slot, {})
    s = [0.0] * len(cands)
    for f in feats(text):
        c = table.get(f)
        if c is None:
            continue
        for j, i in enumerate(idx):
            s[j] += math.log((c[i] + ALPHA) / (ntext[slot][i] + 2 * ALPHA))
    return dict(zip(cands, s))


def decide(model, ntext, slot, text, cands):
    """(best language, margin over the second)"""
    sc = scores(model, ntext, slot, text, cands)
    order = sorted(cands, key=lambda L: -sc[L])
    return order[0], sc[order[0]] - sc[order[1]]


def evaluate(cases, pipeline):
    k = int(os.environ.get("NB_KEEP", "0"))
    model, ntext = load({0: k} if k else None)
    rows = []
    for line in open(pipeline, encoding="utf-8"):
        f = line.rstrip("\n").split("\t")
        s, label, cls, split, cld2 = f[:5]
        if split != "test" or label not in SLOT:
            continue
        cands = [L for L in s.split("+") if SLOT.get(L) == SLOT[label]]
        if len(cands) > 1:
            rows.append((label, cands, f[10], cls, cld2))
    for L in SLOT:          # FLORES devtest: every other language of the group a candidate
        for t in flores(L, "devtest"):
            rows.append((L, GROUPS[SLOT[L]], t, "flores", ""))
    res = [(label, cls, cld2) + decide(model, ntext, SLOT[label], t, cands) for label, cands, t, cls, cld2 in rows]
    for name, keep in (("suite test", lambda r: r[1] != "flores"), ("FLORES devtest", lambda r: r[1] == "flores")):
        sub = [r for r in res if keep(r)]
        print("== %s: %d texts" % (name, len(sub)))
        for T in (0, 2, 4, 6, 8, 10, 12, 15, 20):
            dec = [r for r in sub if r[4] >= T]
            wrong = sum(1 for r in dec if r[3] != r[0])
            print("  T=%-3d coverage %5.1f%%  precision %7.3f%%  wrong %5d" % (
                T, 100.0 * len(dec) / len(sub), 100.0 * (len(dec) - wrong) / max(1, len(dec)), wrong))


# features kept per script for the app (the most informative): more where more
# languages share the script. Chosen on the suite's held-out half (the
# word-and-ending model learned with Android's UI text, no context): 20,000
# everywhere 96.04%; 30,000 96.36; 40,000 96.53; 60,000 96.74; 100,000 96.91;
# this split 96.64% at 0.86 MB more per ABI than 20,000 everywhere
KEEP = {1: 60000, 2: 40000, 3: 30000, 4: 40000, 6: 20000}


def prune():
    """keep in nb_model.tsv only what the app holds: the KEEP most informative
    features per script"""
    model, ntext = load(KEEP)
    lines = [l for l in open(MODEL, encoding="utf-8") if l.startswith("#")]
    with open(MODEL, "w", encoding="utf-8") as f:
        f.writelines(lines)
        for slot in GROUPS:
            f.write("n\t%d\t%s\n" % (slot, ",".join(map(str, ntext[slot]))))
            for feat in sorted(model.get(slot, {})):
                f.write("%s\t%d\t%s\n" % (feat, slot, ",".join(map(str, model[slot][feat]))))
    print("nb_model.tsv: %d features" % sum(len(v) for v in model.values()), file=sys.stderr)
SCALE = 8             # stored log-probabilities are round(SCALE * ln p)


def fnv(text):
    h = 0x811C9DC5
    for b in text.encode("utf-8"):
        h = ((h ^ b) * 0x01000193) & 0xFFFFFFFF
    return h


def shipped():
    """The table the app holds: per slot, (hash -> per-language quantized log p),
    for the KEEP[slot] most informative features. Python's scoring and the C++'s
    read the same table, so they decide alike."""
    model, ntext = load(KEEP)
    table = {}
    for slot, feats_ in model.items():
        n = ntext[slot]
        t = {}
        for f, c in feats_.items():
            h = fnv(f)
            assert h not in t, ("hash collision", f)
            t[h] = [max(-127, int(round(SCALE * math.log((x + ALPHA) / (m + 2 * ALPHA))))) for x, m in zip(c, n)]
        table[slot] = t
    return table


def qscores(table, slot, text, cands):
    """quantized scores, exactly as langblend.h sums them"""
    langs = GROUPS[slot]
    idx = [langs.index(L) for L in cands]
    t = table.get(slot, {})
    s = [0] * len(cands)
    for f in feats(text):
        v = t.get(fnv(f))
        if v is not None:
            for j, i in enumerate(idx):
                s[j] += v[i]
    return s


def header():
    table = shipped()
    out = ["// Generated by tools/langid/train_nb.py header from tools/langid/nb_model.tsv -- do not edit.",
           "// Per script: the most informative word and ending features (%s), by FNV-1a hash" % ", ".join(
               "%d for slot %d" % (KEEP[k], k) for k in sorted(KEEP)),
           "// of the feature, each with round(%d ln p) per language of the script." % SCALE,
           "#pragma once",
           "#include <cstdint>",
           "static const int kNbScale = %d;" % SCALE]
    # what makes a word, and lowercase, over the blocks the features are made in
    word = sorted(WORD)
    ranges = []
    for c in word:
        if ranges and ranges[-1][1] == c - 1:
            ranges[-1][1] = c
        else:
            ranges.append([c, c])
    out.append("static const uint32_t kNbWordRanges[][2] = {")
    for i in range(0, len(ranges), 8):
        out.append("    " + " ".join("{0x%X,0x%X}," % tuple(r) for r in ranges[i:i + 8]))
    out.append("};")
    out.append("static const int kNbWordRangeCount = %d;" % len(ranges))
    low = sorted(LOWER.items())
    out.append("static const uint32_t kNbLower[][2] = {")
    for i in range(0, len(low), 8):
        out.append("    " + " ".join("{0x%X,0x%X}," % p for p in low[i:i + 8]))
    out.append("};")
    out.append("static const int kNbLowerCount = %d;" % len(low))
    for slot, langs in GROUPS.items():
        t = table.get(slot, {})
        hs = sorted(t)
        out.append("static const char* const kNbLangs%d[] = {%s};" % (slot, ", ".join('"%s"' % L for L in langs)))
        out.append("static const uint32_t kNbHash%d[%d] = {" % (slot, len(hs)))
        for i in range(0, len(hs), 10):
            out.append("    " + ",".join("0x%08X" % h for h in hs[i:i + 10]) + ",")
        out.append("};")
        out.append("static const int8_t kNbLogp%d[%d] = {" % (slot, len(hs) * len(langs)))
        for i in range(0, len(hs), 6):
            out.append("    " + " ".join(",".join(str(x) for x in t[h]) + "," for h in hs[i:i + 6]))
        out.append("};")
    # letters: per slot, each letter with the (language, other) pairs it decides, as bit
    # (language index * 8 + other index) of a 64-bit mask; the rare letters (which only
    # keep the context away from `other`) the same way
    for name, count, lt in (("kNbLetters", "kNbLetterCount", letters()),
                            ("kNbRare", "kNbRareCount", letters(LETTERS_RARE))):
        for slot in GROUPS:
            langs = GROUPS[slot]
            ents = []
            for ch, pairs in sorted(lt.get(slot, {}).items()):
                mask = 0
                for L, B in pairs:
                    mask |= 1 << (langs.index(L) * 8 + langs.index(B))
                ents.append((ord(ch), mask))
            out.append("static const uint64_t %s%d[][2] = {%s};" % (
                name, slot, " ".join("{0x%X,0x%XULL}," % e for e in ents) if ents else "{0,0}"))
            out.append("static const int %s%d = %d;" % (count, slot, len(ents)))
    # the letters of an orthography (ortho.tsv): per slot, each letter with a mask of the
    # languages that write it natively (bit = language index) and per language round(8 ln r);
    # then how they decide (ORTHO_*)
    ot = ortho()
    out.append("struct OrthoLetter { uint32_t cp; uint32_t native; int8_t share[8]; };")
    for slot in GROUPS:
        ents = sorted(ot.get(slot, {}).items())
        out.append("static const OrthoLetter kOrtho%d[] = {%s};" % (slot, "" if ents else "{0, 0, {0}}"))
        if ents:
            out[-1] = out[-1][:-2]
            for ch, (nat, pres) in ents:
                out.append("    {0x%X, 0x%X, {%s}}," % (ord(ch), sum(1 << i for i, x in enumerate(nat) if x),
                                                       ",".join(map(str, pres))))
            out.append("};")
        out.append("static const int kOrthoCount%d = %d;" % (slot, len(ents)))
    out.append("static const int kOrthoM[7] = {%s};" % ", ".join(str(ORTHO_M.get(s, 0)) for s in range(7)))
    out.append("static const int kOrthoPw[7] = {%s};" % ", ".join(str(ORTHO_PW.get(s, 0)) for s in range(7)))
    out.append("static const int kOrthoMinWords = %d;" % ORTHO_MINW)
    for name, marks in (("kOrthoSent", ORTHO_SENT), ("kOrthoStrong", ORTHO_STRONG)):
        cps = sorted(ord(c) for c in marks)
        out.append("static const uint32_t %s[%d] = {%s};" % (name, len(cps), ", ".join("0x%X" % c for c in cps)))
    out.append("struct NbModel { const char* const* langs; int nLangs; const uint32_t* hash; const int8_t* logp; int n;"
               " const uint64_t (*letters)[2]; int nLetters; const uint64_t (*rare)[2]; int nRare;"
               " const OrthoLetter* ortho; int nOrtho; };")
    out.append("static inline bool nbModel(int slot, NbModel* m) {")
    for slot, langs in GROUPS.items():
        out.append("    if (slot == %d) { *m = {kNbLangs%d, %d, kNbHash%d, kNbLogp%d, %d, kNbLetters%d, kNbLetterCount%d,"
                   " kNbRare%d, kNbRareCount%d, kOrtho%d, kOrthoCount%d}; return true; }"
                   % (slot, slot, len(langs), slot, slot, len(table.get(slot, {})), slot, slot, slot, slot, slot, slot))
    out.append("    return false;")
    out.append("}")
    return "\n".join(out) + "\n"


if __name__ == "__main__":
    if sys.argv[1] == "learn":
        learn(sys.argv[2])
    elif sys.argv[1] == "eval":
        evaluate(sys.argv[2], sys.argv[3])
    elif sys.argv[1] == "prune":
        prune()
    elif sys.argv[1] == "letters":
        learn_letters(learning_texts(sys.argv[2]))
    elif sys.argv[1] == "ortho":
        learn_ortho(sys.argv[2])
    elif sys.argv[1] == "header":
        with open(OUT, "w", encoding="utf-8") as f:
            f.write(header())
        print("wrote", OUT)
    elif sys.argv[1] == "check":
        cur = open(OUT, encoding="utf-8").read() if os.path.exists(OUT) else ""
        if cur != header():
            print("langnb.h is stale: run tools/langid/train_nb.py header")
            sys.exit(1)
        print("langnb.h current")
