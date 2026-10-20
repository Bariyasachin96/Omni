#!/usr/bin/env python3
"""The word-and-ending model that tells apart the languages of one script.

    train_nb.py learn cases.tsv      learn tools/langid/nb_model.tsv and
                                     letters.tsv from tools/.cache/langtrain
                                     (fetch_train.sh) and the TUNE half of the
                                     suite's cases, keeping the KEEP most
                                     informative features per script
    train_nb.py eval cases.tsv pipeline.tsv
                                     precision and coverage on the suite's
                                     TEST half and on FLORES-200 devtest
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


def in_script(text, slot):
    """the text's letters are mostly of the group's script (Serbian Tatoeba mixes Latin in)"""
    lo, hi = {1: (0x41, 0x24F), 2: (0x400, 0x52F), 3: (0x600, 0x6FF), 4: (0x900, 0x97F), 6: (0x980, 0x9FF)}[slot]
    letters = [ord(c) for c in text if ord(c) in WORD]
    if not letters:
        return False
    inside = sum(1 for c in letters if lo <= c <= hi or (slot == 1 and 0x1E00 <= c <= 0x1EFF) or
                 (slot == 3 and (0x750 <= c <= 0x8FF or 0xFB50 <= c <= 0xFEFF)))
    return inside >= 0.8 * len(letters)


def learning_texts(cases):
    """per language: NTEXT texts, the same sources in the same order every run"""
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
    texts = {}
    for L, slot in SLOT.items():
        pool = list(tune[L])
        path = os.path.join(TRAIN, "tatoeba", TATOEBA[L] + ".tsv")
        if os.path.exists(path):
            pool += [l.rstrip("\n").split("\t")[2] for l in open(path, encoding="utf-8", errors="replace")
                     if l.count("\t") == 2]
        path = os.path.join(TRAIN, "cv", CV.get(L, L) + ".txt")
        if os.path.exists(path):
            pool += [l.strip() for l in open(path, encoding="utf-8", errors="replace")]
        pool += wiki_texts(L, NTEXT)
        seen, out = set(), []
        for t in pool:
            k = key(t)
            if k and k not in seen and k not in test_keys and in_script(t, slot):
                seen.add(k)
                out.append(t)
        # the same number for every language: the lowest hashes of each pool
        out.sort(key=lambda t: zlib.crc32(t.encode("utf-8")))
        texts[L] = out[:NTEXT]
        print("  %s: %d learning texts (of %d)" % (L, len(texts[L]), len(out)), file=sys.stderr)
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
LETTER_SLOTS = (2, 3, 6)          # where a letter is an orthography, not a loanword's accent
LETTER_COMMON = 0.005             # the letter is in at least 1 of 200 of the language's texts
LETTER_NEVER = 0.00002            # and in at most 1 of 50,000 of the other's


def learn_letters(texts):
    rate = {}
    for L, ts in texts.items():
        c = collections.Counter()
        for t in ts:
            c.update({ch for ch in t if ord(ch) in WORD and unicodedata.category(ch)[0] == "L"})
        rate[L] = {ch: n / max(1, len(ts)) for ch, n in c.items()}
    with open(LETTERS, "w", encoding="utf-8") as out:
        out.write("# Learned by tools/langid/train_nb.py from the same texts as nb_model.tsv -- do not\n")
        out.write("# edit. slot<TAB>language<TAB>other<TAB>letters the language writes in at least\n")
        out.write("# %g of its texts and the other in at most %g of its texts.\n" % (LETTER_COMMON, LETTER_NEVER))
        for slot in LETTER_SLOTS:
            langs = GROUPS[slot]
            for L in langs:
                for B in langs:
                    if B != L:
                        ex = sorted(ch for ch, r in rate[L].items()
                                    if r >= LETTER_COMMON and rate[B].get(ch, 0) <= LETTER_NEVER)
                        if ex:
                            out.write("%d\t%s\t%s\t%s\n" % (slot, L, B, "".join(ex)))


def letters():
    """slot -> {letter: set of (language, other)}"""
    table = {}
    for line in open(LETTERS, encoding="utf-8"):
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


KEEP = 20000          # features kept per script for the app (the most informative)


def prune():
    """keep in nb_model.tsv only what the app holds: the KEEP most informative
    features per script"""
    model, ntext = load({0: KEEP})
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
    for the KEEP most informative features. Python's scoring and the C++'s read
    the same table, so they decide alike."""
    model, ntext = load({0: KEEP})
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
           "// Per script: the %d most informative word and ending features, by FNV-1a hash" % KEEP,
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
    # (language index * 8 + other index) of a 64-bit mask
    lt = letters()
    for slot in GROUPS:
        langs = GROUPS[slot]
        ents = []
        for ch, pairs in sorted(lt.get(slot, {}).items()):
            mask = 0
            for L, B in pairs:
                mask |= 1 << (langs.index(L) * 8 + langs.index(B))
            ents.append((ord(ch), mask))
        out.append("static const uint64_t kNbLetters%d[][2] = {%s};" % (
            slot, " ".join("{0x%X,0x%XULL}," % e for e in ents) if ents else "{0,0}"))
        out.append("static const int kNbLetterCount%d = %d;" % (slot, len(ents)))
    out.append("struct NbModel { const char* const* langs; int nLangs; const uint32_t* hash; const int8_t* logp; int n;"
               " const uint64_t (*letters)[2]; int nLetters; };")
    out.append("static inline bool nbModel(int slot, NbModel* m) {")
    for slot, langs in GROUPS.items():
        out.append("    if (slot == %d) { *m = {kNbLangs%d, %d, kNbHash%d, kNbLogp%d, %d, kNbLetters%d, kNbLetterCount%d};"
                   " return true; }" % (slot, slot, len(langs), slot, slot, len(table.get(slot, {})), slot, slot))
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
