"""Ways of combining CLD2 and the model, tuned on one half of the text and
measured on the other.

    combine.py pipeline.tsv

Every row of pipeline.tsv is one decision the core makes: a text in a language
the user enabled, the enabled set, what CLD2's path answered, and the model's
probability for each enabled language of that script. A way of combining them
is tuned only on one half of the "test" rows and scored only on the other half,
text it never saw (the "tune" rows are what the word-and-ending model learned from).

The rules are not tuned: each is a letter one language of a pair writes and the
other never does, or (Chinese and Japanese) which national character sets the
text's Han characters fit. The blend's weights are tuned per script with a
right answer turned wrong counted LAM times a wrong one put right -- the owner's
"no risk".
"""
import collections
import math
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(os.path.dirname(HERE)))
SLOT = {}
for l in "be bg kk ky mk mn ru sr tg uk".split():
    SLOT[l] = 2
for l in "ar fa ps sd ur".split():
    SLOT[l] = 3
for l in "hi mr ne".split():
    SLOT[l] = 4
for l in "zh ja".split():
    SLOT[l] = 5
for l in "bn as".split():
    SLOT[l] = 6
SCRIPT = {1: "Latin", 2: "Cyrillic", 3: "Arabic", 4: "Devanagari", 5: "Han", 6: "Bengali"}
ALIAS = {"tl": "fil", "jw": "jv", "zh-Hant": "zh", "nn": "no", "sr-ME": "sr"}
MODEL_LABELS = {"af", "am", "ar", "az", "be", "bg", "bn", "bs", "ca", "ceb", "co", "cs", "cy", "da", "de", "el", "en",
                "eo", "es", "et", "eu", "fa", "fi", "fil", "fr", "fy", "ga", "gd", "gl", "gu", "ha", "haw", "hi", "hmn",
                "hr", "ht", "hu", "hy", "id", "ig", "is", "it", "iw", "ja", "jv", "ka", "kk", "km", "kn", "ko", "ku",
                "ky", "la", "lb", "lo", "lt", "lv", "mg", "mi", "mk", "ml", "mn", "mr", "ms", "mt", "my", "ne", "nl",
                "no", "ny", "pa", "pl", "ps", "pt", "ro", "ru", "sd", "si", "sk", "sl", "sm", "sn", "so", "sq", "sr",
                "st", "su", "sv", "sw", "ta", "te", "tg", "th", "tr", "uk", "ur", "uz", "vi", "xh", "yi", "yo", "zh", "zu"}

# Han character sets (tools/langid/han_sets.txt): S simplified, T traditional, J Japanese
HAN = {}
for line in open(os.path.join(ROOT, "tools", "langid", "han_sets.txt"), encoding="utf-8"):
    if not line.startswith("#"):
        cp, tags = line.split()
        HAN[int(cp[2:], 16)] = tags


def han_rule(t, e):
    """langblend.h's Han rules: kana -> ja; a Han character outside JIS X 0208
    -> zh; Han fitting neither GB 2312 alone nor CNS 11643 1-2 alone -> ja;
    seven or more Han characters (no kana) -> zh."""
    han = 0
    kana = notj = False
    alls = allt = True
    for ch in t:
        c = ord(ch)
        if (0x3041 <= c <= 0x309F) or (0x30A0 <= c <= 0x30FF and c != 0x30FB) or (0x31F0 <= c <= 0x31FF) \
                or (0xFF66 <= c <= 0xFF9F) or c == 0x3005:
            kana = True
        elif (0x4E00 <= c <= 0x9FFF) or (0x3400 <= c <= 0x4DBF) or (0xF900 <= c <= 0xFAFF) or (0x20000 <= c <= 0x3FFFF):
            han += 1
            tags = HAN.get(c, "") if 0x4E00 <= c <= 0x9FFF else ""
            notj |= "J" not in tags
            alls &= "S" in tags
            allt &= "T" in tags
    if "ja" in e and kana:
        return "ja"
    if "zh" in e and han and notj:
        return "zh"
    if "zh" in e and han >= 7:
        return "zh"
    if "ja" in e and han and not alls and not allt:
        return "ja"
    return None


# The word-and-ending model (tools/langid/train_nb.py; langnb.h): langblend.h's
# step 2, believed when the best enabled language leads by NB_MARGIN.
sys.path.insert(0, os.path.join(ROOT, "tools", "langid"))
import train_nb  # noqa: E402
NB = train_nb.shipped()
NB_MARGIN = {1: 80, 2: 80, 3: 80, 4: 80, 6: 80}


def nb_rule(t, order, slot):
    langs = train_nb.GROUPS.get(slot)
    cands = [L for L in dict.fromkeys(order) if SLOT.get(L, 1) == slot]
    if not langs or len(cands) < 2 or any(L not in langs for L in cands) or not NB_MARGIN.get(slot):
        return None
    sc = train_nb.qscores(NB, slot, t, cands)
    best = max(range(len(cands)), key=lambda i: (sc[i], -i))
    second = max(sc[i] for i in range(len(cands)) if i != best)
    return cands[best] if sc[best] - second >= NB_MARGIN[slot] else None


LT = train_nb.letters()
LR = train_nb.letters(train_nb.LETTERS_RARE)


OT = train_nb.ortho()


def slot_langs(r):
    """the enabled languages of the text's script, as blendDetectors holds them
    once its step 0 has run: those the orthographies' letters leave"""
    if r.get("left") is not None:
        return r["left"]
    return [L for L in dict.fromkeys(r["set"].split("+")) if SLOT.get(L, 1) == r["slot"]]


def ortho_step(r):
    """langblend.h's step 0 (orthoDecide): (the one language the letters leave,
    or None; what they leave when they rule something out, else None; their
    best, which stands for CLD2's answer if they ruled that out)"""
    slot = r["slot"]
    if slot not in (2, 3, 6):
        return None, None, None
    langs = train_nb.GROUPS[slot]
    cands = [L for L in dict.fromkeys(r["set"].split("+")) if SLOT.get(L, 1) == slot]
    if len(cands) < 2 or any(L not in langs for L in cands):
        return None, None, None
    det = (r["cld2"], r["mtop"]) if all(L in MODEL_LABELS for L in cands) else None
    left, tot = train_nb.ortho_decide(OT, NB, slot, r["text"], cands, det)
    if tot is None or len(left) == len(cands):
        return None, None, None
    best = cands[max(range(len(cands)), key=lambda j: (tot[j], -j))]
    return (left[0] if len(left) == 1 else None), left, best


def rule(r):
    """langblend.h's step 1: the learned letters (Cyrillic, Arabic, Bengali), the
    Han rules (Chinese and Japanese)."""
    t, e, slot = r["text"], r["enabled"], r["slot"]
    if slot in (2, 3, 6):
        langs = train_nb.GROUPS[slot]
        cands = slot_langs(r)
        if any(L not in langs for L in cands):
            return None
        return train_nb.letter_rule(LT, slot, t, cands)
    if slot == 5:
        return han_rule(t, e)
    return None


rows = []
for line in open(sys.argv[1], encoding="utf-8"):
    f = line.rstrip("\n").split("\t")
    s, label, cls, split, cld2, model, fallback, detail, cands, blend_cpp, text = f[:11]
    ctxmap = dict(x.split(":", 1) for x in f[11].split(",") if ":" in x) if len(f) > 11 else {}
    probs = {}
    for c in cands.split(","):
        if "=" in c:
            k, v = c.split("=")
            probs[k] = float(v)
    d = detail.split(":")
    rank = [(ALIAS.get(x.split("/")[0], x.split("/")[0]), int(x.split("/")[1])) for x in d[2:]]
    enabled = set(s.split("+"))
    slot = SLOT.get(label, 1)
    r = dict(set=s, label=label, cls=cls, split=split, cld2=cld2, model=model, text=text, slot=slot,
             enabled=enabled, blend=blend_cpp, probs=probs, rank=rank, ctxmap=ctxmap)
    r["multi"] = sum(1 for x in enabled if SLOT.get(x, 1) == slot) > 1
    r["mtop"] = max(sorted(probs), key=lambda L: probs[L]) if probs else None
    r["left"], r["ruled_out"] = None, None
    sure0 = None
    if r["multi"]:
        sure0, r["left"], best0 = ortho_step(r)
        if r["left"] is not None and cld2 not in r["left"]:
            r["ruled_out"] = best0
    r["rule"] = (sure0 or rule(r) or nb_rule(text, slot_langs(r), slot)) if r["multi"] else None
    # what the blend reads, once: per candidate ln p, CLD2's percent, CLD2's answer
    r["cands"] = sorted(L for L in probs if r["left"] is None or L in r["left"])
    r["lp"] = [math.log(probs[L] + 1e-6) for L in r["cands"]]
    pct = {L: 0.0 for L in r["cands"]}
    named = False
    for code, p in rank:
        if code in pct and p > 0:
            pct[code] += p / 100.0
            named = True
    r["pct"] = [pct[L] for L in r["cands"]]
    r["ans"] = [1.0 if L == cld2 else 0.0 for L in r["cands"]]
    r["named"] = named
    nbl = train_nb.GROUPS.get(slot)
    if r["multi"] and nbl and len(r["cands"]) > 1 and all(L in nbl for L in r["cands"]):
        q8 = train_nb.qscores(NB, slot, text, r["cands"])
        r["nb"] = [x / 8.0 for x in q8]
    else:
        r["nb"] = [0.0] * len(r["cands"])
    r["usable"] = bool(probs) and cld2 in MODEL_LABELS
    rows.append(r)


def soft(r, a, k, b, c):
    """langblend.h's step 4: a ln p_model + (1 - a) ln q_cld2 + c nb, q = CLD2's
    percents + k + b for its answer, nb = the word-and-ending model's score (nats)."""
    if not r["usable"] or not r["cands"]:
        return r["ruled_out"] or r["cld2"]
    q = [k + p + b * a_ for p, a_ in zip(r["pct"], r["ans"])]
    z = sum(q)
    best_l, bs = None, -1e300
    for L, lp, qq, nb in zip(r["cands"], r["lp"], q, r["nb"]):
        sc = a * lp + (1 - a) * math.log(qq / z + 1e-6) + c * nb
        if sc > bs:
            best_l, bs = L, sc
    return best_l


def blend(r, w):
    return r["rule"] or soft(r, *w)


LAM = float(os.environ.get("EV_LAM", "3"))


def gain(sub, w):
    g = 0
    for r in sub:
        old = r["cld2"] == r["label"]
        new = blend(r, w) == r["label"]
        if new != old:
            g += 1 if new else -LAM
    return g


# The word-and-ending model learned from the cases' "tune" half, so those rows
# say nothing fair about weights: the blend is tuned on one half of the "test"
# rows and measured on the other (by a hash of the text, the same every run).
import zlib  # noqa: E402
tune = [r for r in rows if r["split"] == "test" and zlib.crc32((r["label"] + r["text"]).encode("utf-8")) % 2 == 0]
test = [r for r in rows if r["split"] == "test" and zlib.crc32((r["label"] + r["text"]).encode("utf-8")) % 2 == 1]
GRID = [(a, k, b, c) for a in (0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9) for k in (0.01, 0.05, 0.2)
        for b in (0, 0.5, 1, 2) for c in (0, 0.05, 0.1, 0.2, 0.3, 0.5, 1.0)]
# The weights langblend.h ships (kBlend), per slot: a retune replaces them only
# when it gains more than one break's worth (LAM) on the tune half -- a change
# of the rows a rule decides moves the argmax by a point or two either way, and
# chasing that is noise, not a better blend (2026-10-21: the letters' step 0
# changed which Cyrillic texts the blend decides; the argmax then gained 1 on
# the tune half and lost 4 on the held-out one).
import re  # noqa: E402
SHIPPED = {}
_kb = open(os.path.join(ROOT, "app", "src", "main", "cpp", "langblend.h"), encoding="utf-8").read()
_kb = _kb[_kb.index("static const BlendWeights kBlend[7] = {"):]
for _slot, _row in enumerate(re.findall(r"\{([0-9.]+)f, ([0-9.]+)f, ([0-9.]+)f, ([0-9.]+)f\}", _kb[:_kb.index("};")])):
    SHIPPED[_slot] = tuple(float(x) for x in _row)
best = {}
for slot in SCRIPT:
    # only the decisions the weights can change: the blend runs (two or more
    # candidates), no rule has decided, CLD2's answer is one the model knows
    live = [r for r in tune if r["slot"] == slot and r["multi"] and not r["rule"] and r["usable"]
            and len(r["cands"]) > 1]
    if not live:
        continue
    best[slot] = max(GRID, key=lambda w: (gain(live, w), -w[3], -w[0]))
    if slot in SHIPPED and gain(live, SHIPPED[slot]) >= gain(live, best[slot]) - LAM:
        best[slot] = SHIPPED[slot]
    print("%-10s tuned on %6d: a=%.1f k=%.2f b=%.1f c=%.2f" % ((SCRIPT[slot], len(live)) + best[slot]))

WAYS = collections.OrderedDict([
    ("CLD2 (today)", lambda r: r["cld2"]),
    ("model", lambda r: r["model"]),
    ("rules + CLD2", lambda r: r["rule"] or r["cld2"]),
    ("rules + blend", lambda r: blend(r, best[r["slot"]]) if r["slot"] in best else (r["rule"] or r["cld2"])),
    ("C++ blend", lambda r: r["blend"]),
    ("either right", lambda r: r["label"] if r["label"] in (r["cld2"], r["model"]) else r["cld2"]),
])


def acc(sub, fn):
    return sum(fn(r) == r["label"] for r in sub) / len(sub) if sub else 0.0


def breaks(sub, fn):
    return sum(1 for r in sub if r["cld2"] == r["label"] and fn(r) != r["label"])


def fixes(sub, fn):
    return sum(1 for r in sub if r["cld2"] != r["label"] and fn(r) == r["label"])


print("\n== TEST rows only (never used for tuning): accuracy %, and for rules + blend the texts")
print("== CLD2 got right that it gets wrong / CLD2 got wrong that it gets right ==")
print("%-34s" % "" + "".join("%15s" % w for w in WAYS) + "   broke/fixed")
for name in dict.fromkeys(r["set"] for r in test):
    sub = [r for r in test if r["set"] == name and r["multi"]]
    for lang in dict.fromkeys(r["label"] for r in sub):
        ls = [r for r in sub if r["label"] == lang]
        fn = WAYS["rules + blend"]
        print("%-34s" % ("%s  %s (%d)" % (name, lang, len(ls))) + "".join("%14.1f " % (100 * acc(ls, f)) for f in WAYS.values())
              + "   %d/%d" % (breaks(ls, fn), fixes(ls, fn)))
print()
for title, keep in (("ALL test", lambda r: True),
                    ("short (1-3 words, phrases)", lambda r: r["cls"] in ("1w", "2-3w", "phrase")),
                    ("PHRASES", lambda r: r["cls"] == "phrase"),
                    ("UDHR", lambda r: r["cls"] not in ("phrase", "ui", "tatoeba")),
                    ("Android UI", lambda r: r["cls"] == "ui"),
                    ("Tatoeba", lambda r: r["cls"] == "tatoeba")):
    sub = [r for r in test if keep(r) and r["multi"]]
    if sub:
        fn = WAYS["rules + blend"]
        print("%-34s" % ("%s (%d)" % (title, len(sub))) + "".join("%14.1f " % (100 * acc(sub, f)) for f in WAYS.values())
              + "   %d/%d" % (breaks(sub, fn), fixes(sub, fn)))

# CONTEXT (langblend.h's blendFinal): a decision that is not sure (no rule, the
# word-and-ending model below its margin) takes the language of the last sure
# decision in the same script, unless the word-and-ending model puts its own
# pick at least CTX nats above that language, or the text holds a letter its own
# answer writes and that language practically never does (letters_rare.tsv). Simulated: the last sure
# decision was the text's own language, or with probability P another one.
def nb_scores_all(r):
    langs = train_nb.GROUPS.get(r["slot"])
    cands = slot_langs(r)
    if not langs or len(cands) < 2 or any(L not in langs for L in cands):
        return cands, None
    return cands, train_nb.qscores(NB, r["slot"], r["text"], cands)


def with_context(r, ctx, ctx_margin):
    got = WAYS["rules + blend"](r)
    if r["rule"] or ctx is None or ctx == got:
        return got
    cands, sc = nb_scores_all(r)
    if ctx not in cands:
        return got
    if train_nb.letter_veto(LR, r["slot"], r["text"], got, ctx):
        return got
    if sc is None or got not in cands:
        return ctx
    return got if sc[cands.index(got)] - sc[cands.index(ctx)] >= ctx_margin * 8 else ctx


def sim_ctx(r, p):
    cands = [L for L in dict.fromkeys(r["set"].split("+")) if SLOT.get(L, 1) == r["slot"]]
    h = zlib.crc32(("ctx" + r["text"]).encode("utf-8")) % 1000
    if h >= p * 1000 or len(cands) < 2:
        return r["label"]
    others = [L for L in cands if L != r["label"]]
    return others[h % len(others)]


for r in rows:
    r["nbs"] = None
ctx_best = 6          # langblend.h's kCtxMargin (48 = 6 nats): context wins unless overruled by 6 nats
print("\n== CONTEXT (held-out half), margin to overrule it: %d nats ==" % ctx_best)
for P in (0.0, 0.1, 0.3, 0.5):
    sub = [r for r in test if r["multi"]]
    a = sum(with_context(r, sim_ctx(r, P), ctx_best) == r["label"] for r in sub) / len(sub)
    print("  last sure language differs with probability %.1f: %.2f%%  (no context %.2f%%)" % (
        P, 100 * a, 100 * acc(sub, WAYS["rules + blend"])))

# blendFinal (C++) after every possible context must answer what with_context does
cdiff = 0
ctotal = 0
for r in rows:
    if r["multi"] and r["slot"] in best and r["ctxmap"]:
        for c, cpp in r["ctxmap"].items():
            ctotal += 1
            if with_context(r, c, ctx_best) != cpp:
                cdiff += 1
                if cdiff <= 5:
                    print("   context %s, %s %s: python %s  C++ %s  %r" % (c, r["set"], r["label"], with_context(r, c, ctx_best), cpp, r["text"][:50]))
print("C++ context vs python: %d of %d differ" % (cdiff, ctotal))
if cdiff > ctotal // 1000:
    print("FAILED: blendFinal does not apply the context as simulated")
    sys.exit(1)

# Every test text the blend turns wrong, for reading: breaks.tsv beside the input.
with open(os.path.join(os.path.dirname(os.path.abspath(sys.argv[1])), "breaks.tsv"), "w", encoding="utf-8") as out:
    fn = WAYS["rules + blend"]
    for r in test:
        if r["multi"] and r["cld2"] == r["label"] and fn(r) != r["label"]:
            out.write("%s\t%s\t%s\tcld2=%s\tmodel=%s\tblend=%s\t%s\t%s\n" % (
                r["set"], r["label"], r["cls"], r["cld2"], r["model"], fn(r),
                ",".join("%s/%d" % x for x in r["rank"]), r["text"]))

# The C++ (langblend.h, what the core runs) must make the decisions the tuned
# arithmetic above makes. Single-language scripts are left to CLD2's path by
# design, so they are not compared.
multi = [r for r in rows if r["multi"] and r["slot"] in best]
differ = [r for r in multi if blend(r, best[r["slot"]]) != r["blend"]]
print("\nC++ blend vs the tuned arithmetic: %d of %d decisions differ" % (len(differ), len(multi)))
for r in differ[:8]:
    print("   %s %s: python %s  C++ %s  %r" % (r["set"], r["label"], blend(r, best[r["slot"]]), r["blend"], r["text"][:50]))
print("kBlend: " + ", ".join("{%.1ff, %.2ff, %.1ff, %.2ff}" % best[s] for s in sorted(best)))
if len(differ) > len(multi) // 1000:
    print("FAILED: langblend.h does not decide what was tuned")
    sys.exit(1)
