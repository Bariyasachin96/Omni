"""The core's choice between the enabled languages of one script, mirrored in
Python, tuned on one half of the text and measured on the other.

    combine.py pipeline.tsv [baseline.tsv]

Every row of pipeline.tsv is one decision the core makes: a text in a language
the user enabled, the enabled set, the model's probability for each enabled
language of that script, and what langblend.h decided (blendDetectors) and
would decide after each possible context (blendFinal). This mirrors those
steps -- the letters of each orthography (step 0), the learned letters and the
Han rules (1), the word-and-ending model when it is sure (2), the model ML Kit
runs and the word-and-ending model weighed (3), then the context -- and FAILS
if the C++ decides otherwise. The weight of step 3 (kBlendC, one per script) is
tuned only on one half of the "test" rows and scored only on the other half;
the shipped weight is kept unless a retune gains more than LAM there, a right
answer turned wrong counted LAM times a wrong one put right (the owner's "no
risk"). The "tune" rows are what the word-and-ending model learned from.

baseline.tsv: a pipeline.tsv of an earlier core (its own format), for a
"before" column -- e.g. the last run with CLD2 (until 2026-10-21), whose rows
are set, label, class, split, cld2, model, fallback, detail, cands, blend, text.
"""
import collections
import math
import os
import re
import sys
import zlib

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(os.path.dirname(HERE)))
sys.path.insert(0, os.path.join(ROOT, "tools", "langid"))
import train_nb  # noqa: E402

SLOT = {}
for l in "be bg kk ky mk mn ru sr tg uk".split():
    SLOT[l] = 2
for l in "ar fa ps sd ur".split():
    SLOT[l] = 3
for l in "hi mr ne sa".split():
    SLOT[l] = 4
for l in "zh ja".split():
    SLOT[l] = 5
for l in "bn as".split():
    SLOT[l] = 6
SCRIPT = {1: "Latin", 2: "Cyrillic", 3: "Arabic", 4: "Devanagari", 5: "Han", 6: "Bengali"}
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
    -> zh; seven or more Han characters (no kana) -> zh; Han fitting neither
    GB 2312 alone nor CNS 11643 1-2 alone -> ja."""
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


NB = train_nb.shipped()
LT = train_nb.letters()
LR = train_nb.letters(train_nb.LETTERS_RARE)
OT = train_nb.ortho()
NB_MARGIN = 80     # langblend.h kNbMargin: e^10
CTX_MARGIN = 48    # langblend.h kCtxMargin: 6 nats

rows = []
for line in open(sys.argv[1], encoding="utf-8"):
    f = line.rstrip("\n").split("\t")
    s, label, cls, split, model, fallback, cands, blend_cpp, text = f[:9]
    ctxmap = dict(x.split(":", 1) for x in f[9].split(",") if ":" in x) if len(f) > 9 else {}
    probs = {}
    for c in cands.split(","):
        if "=" in c:
            k, v = c.split("=")
            probs[k] = float(v)
    enabled = list(dict.fromkeys(s.split("+")))
    slot = SLOT.get(label, 1)
    r = dict(set=s, label=label, cls=cls, split=split, model=model, text=text, slot=slot, enabled=set(enabled),
             blend=blend_cpp, probs=probs, ctxmap=ctxmap)
    r["slotl"] = [L for L in enabled if SLOT.get(L, 1) == slot]
    r["multi"] = len(r["slotl"]) > 1
    r["mtop"] = max(sorted(probs), key=lambda L: probs[L]) if probs else None
    rows.append(r)


def prepare(r):
    """steps 0-2 of blendDetectors: (sure answer or None, the languages left,
    the word-and-ending model's scores over them or None)"""
    slot, text = r["slot"], r["text"]
    left = list(r["slotl"])
    if slot in (2, 3, 6) and all(L in train_nb.GROUPS[slot] for L in left):
        det = (r["mtop"], r["mtop"]) if all(L in MODEL_LABELS for L in left) else None
        kept, tot = train_nb.ortho_decide(OT, NB, slot, text, left, det)
        if tot is not None and len(kept) < len(left):
            left = kept
            if len(kept) == 1:
                return kept[0], left, None
    if slot in (2, 3, 6):
        if all(L in train_nb.GROUPS[slot] for L in left):
            won = train_nb.letter_rule(LT, slot, text, left)
            if won:
                return won, left, None
    elif slot == 5:
        won = han_rule(text, r["enabled"])
        if won:
            return won, left, None
    nb = None
    g = train_nb.GROUPS.get(slot)
    if g and len(left) > 1 and all(L in g for L in left):
        nb = train_nb.qscores(NB, slot, text, left)
        best = max(range(len(left)), key=lambda i: (nb[i], -i))
        second = max(nb[i] for i in range(len(left)) if i != best)
        if nb[best] - second >= NB_MARGIN:
            return left[best], left, nb
    return None, left, nb


for r in rows:
    if r["multi"]:
        r["sure"], r["left"], r["nb"] = prepare(r)


def soft(r, c):
    """step 3: ln p(model) + c * nb / 8 when the model knows every candidate;
    else the word-and-ending model alone; else the model among those it knows"""
    left, nb = r["left"], r["nb"]
    lab = [L for L in left if L in MODEL_LABELS]
    if len(lab) == len(left) and lab:
        best, bs = None, -1e300
        for i, L in enumerate(left):
            sc = math.log(r["probs"].get(L, 0.0) + 1e-6) + (c * nb[i] / 8.0 if nb else 0.0)
            if sc > bs:
                best, bs = L, sc
        return best
    if nb:
        return left[max(range(len(left)), key=lambda i: (nb[i], -i))]
    if lab:
        return max(lab, key=lambda L: r["probs"].get(L, 0.0))
    return left[0]


def decide(r, c):
    if not r["multi"]:
        return r["model"]
    return r["sure"] or soft(r, c)


SHIPPED = {}
_kb = open(os.path.join(ROOT, "app", "src", "main", "cpp", "langblend.h"), encoding="utf-8").read()
_kb = _kb[_kb.index("static const float kBlendC[7] = {"):]
for _slot, _v in enumerate(re.findall(r"([0-9.]+)f", _kb[:_kb.index("};")])):
    SHIPPED[_slot] = float(_v)

tune = [r for r in rows if r["split"] == "test" and zlib.crc32((r["label"] + r["text"]).encode("utf-8")) % 2 == 0]
test = [r for r in rows if r["split"] == "test" and zlib.crc32((r["label"] + r["text"]).encode("utf-8")) % 2 == 1]
LAM = float(os.environ.get("EV_LAM", "3"))
GRIDC = [0, 0.05, 0.1, 0.2, 0.3, 0.5, 0.75, 1.0, 1.5, 2.0, 3.0, 5.0, 8.0]
best = {}
for slot in SCRIPT:
    live = [r for r in tune if r["slot"] == slot and r["multi"] and not r["sure"] and len(r["left"]) > 1]
    if not live:
        best[slot] = SHIPPED.get(slot, 0.0)
        continue
    shipped = SHIPPED.get(slot, 0.0)

    def gain(c):
        g = 0
        for r in live:
            old = soft(r, shipped) == r["label"]
            new = soft(r, c) == r["label"]
            if new != old:
                g += 1 if new else -LAM
        return g
    cand = max(GRIDC, key=lambda c: (gain(c), -abs(c - shipped)))
    best[slot] = cand if gain(cand) > LAM else shipped
    print("%-10s tuned on %6d undecided texts: c = %.2f (shipped %.2f, best of the grid %.2f gains %+.0f)"
          % (SCRIPT[slot], len(live), best[slot], shipped, cand, gain(cand)))

base = {}
if len(sys.argv) > 2:
    for line in open(sys.argv[2], encoding="utf-8"):
        f = line.rstrip("\n").split("\t")
        if len(f) >= 11:
            base.setdefault((f[0], f[1], f[10]), f[9])
before = (lambda r: base.get((r["set"], r["label"], r["text"]))) if base else None

WAYS = collections.OrderedDict()
if before:
    WAYS["before"] = before
WAYS["model alone"] = lambda r: r["model"]
WAYS["now (python)"] = lambda r: decide(r, best[r["slot"]])
WAYS["now (C++)"] = lambda r: r["blend"]
NOW = WAYS["now (python)"]
REF = before or WAYS["model alone"]


def acc(sub, fn):
    return sum(fn(r) == r["label"] for r in sub) / len(sub) if sub else 0.0


def broke(sub):
    return sum(1 for r in sub if REF(r) == r["label"] and NOW(r) != r["label"])


def fixed(sub):
    return sum(1 for r in sub if REF(r) != r["label"] and NOW(r) == r["label"])


print("\n== TEST rows only (never used for tuning), texts where 2+ enabled languages share the script:")
print("== accuracy %%, and the texts %s got right that now are wrong / wrong that now are right =="
      % ("before" if before else "the model alone"))
print("%-34s" % "" + "".join("%14s" % w for w in WAYS) + "   broke/fixed")
for name in dict.fromkeys(r["set"] for r in test):
    sub = [r for r in test if r["set"] == name and r["multi"]]
    for lang in dict.fromkeys(r["label"] for r in sub):
        ls = [r for r in sub if r["label"] == lang]
        print("%-34s" % ("%s  %s (%d)" % (name, lang, len(ls))) + "".join("%13.1f " % (100 * acc(ls, f)) for f in WAYS.values())
              + "   %d/%d" % (broke(ls), fixed(ls)))
print()
for title, keep in (("ALL test", lambda r: True),
                    ("short (1-3 words, phrases)", lambda r: r["cls"] in ("1w", "2-3w", "phrase")),
                    ("PHRASES", lambda r: r["cls"] == "phrase"),
                    ("UDHR", lambda r: r["cls"] not in ("phrase", "ui", "tatoeba")),
                    ("Android UI", lambda r: r["cls"] == "ui"),
                    ("Tatoeba", lambda r: r["cls"] == "tatoeba")):
    sub = [r for r in test if keep(r) and r["multi"]]
    if sub:
        print("%-34s" % ("%s (%d)" % (title, len(sub))) + "".join("%13.1f " % (100 * acc(sub, f)) for f in WAYS.values())
              + "   %d/%d" % (broke(sub), fixed(sub)))


# CONTEXT (langblend.h's blendFinal): a decision that is not sure takes the
# language of the last sure decision in the same script, unless the
# word-and-ending model puts its own pick CTX_MARGIN above that language, or the
# text holds a letter its own answer writes and that language practically never
# does (letters_rare.tsv). Simulated: the last sure decision was the text's own
# language, or with probability P another one.
def with_context(r, ctx):
    got = decide(r, best[r["slot"]])
    if not r["multi"] or r["sure"] or ctx is None or ctx == got:
        return got
    cands = r["left"]
    if ctx not in cands:
        return got
    if got in cands and train_nb.letter_veto(LR, r["slot"], r["text"], got, ctx):
        return got
    sc = r["nb"]
    if sc is None or got not in cands:
        return ctx
    return got if sc[cands.index(got)] - sc[cands.index(ctx)] >= CTX_MARGIN else ctx


def sim_ctx(r, p):
    cands = r["slotl"]
    h = zlib.crc32(("ctx" + r["text"]).encode("utf-8")) % 1000
    if h >= p * 1000 or len(cands) < 2:
        return r["label"]
    others = [L for L in cands if L != r["label"]]
    return others[h % len(others)]


print("\n== CONTEXT (held-out half), margin to overrule it: %d nats ==" % (CTX_MARGIN // 8))
sub = [r for r in test if r["multi"]]
for P in (0.0, 0.1, 0.3, 0.5):
    a = sum(with_context(r, sim_ctx(r, P)) == r["label"] for r in sub) / len(sub)
    print("  last sure language differs with probability %.1f: %.2f%%  (no context %.2f%%)" % (P, 100 * a, 100 * acc(sub, NOW)))

cdiff = ctotal = 0
for r in rows:
    if r["multi"] and r["ctxmap"]:
        for c, cpp in r["ctxmap"].items():
            ctotal += 1
            if with_context(r, c) != cpp:
                cdiff += 1
                if cdiff <= 5:
                    print("   context %s, %s %s: python %s  C++ %s  %r" % (c, r["set"], r["label"], with_context(r, c), cpp, r["text"][:50]))
print("C++ context vs python: %d of %d differ" % (cdiff, ctotal))

# Every test text now wrong that the reference got right, for reading: breaks.tsv beside the input.
with open(os.path.join(os.path.dirname(os.path.abspath(sys.argv[1])), "breaks.tsv"), "w", encoding="utf-8") as out:
    for r in test:
        if r["multi"] and REF(r) == r["label"] and NOW(r) != r["label"]:
            out.write("%s\t%s\t%s\tref=%s\tmodel=%s\tnow=%s\t%s\n" % (
                r["set"], r["label"], r["cls"], REF(r), r["model"], NOW(r), r["text"]))

multi = [r for r in rows if r["multi"]]
differ = [r for r in multi if decide(r, best[r["slot"]]) != r["blend"]]
print("\nC++ blend vs python: %d of %d decisions differ" % (len(differ), len(multi)))
for r in differ[:8]:
    print("   %s %s: python %s  C++ %s  %r" % (r["set"], r["label"], decide(r, best[r["slot"]]), r["blend"], r["text"][:50]))
print("kBlendC: {0.0f, " + ", ".join("%.2ff" % best[s] for s in sorted(best)) + "}")
failed = False
if any(abs(best[s] - SHIPPED.get(s, 0.0)) > 1e-9 for s in best):
    print("NOTE: a retune beats the shipped kBlendC by more than LAM -- put the line above into langblend.h")
if differ:
    print("FAILED: langblend.h does not decide what is mirrored here")
    failed = True
if cdiff:
    print("FAILED: blendFinal does not apply the context as mirrored here")
    failed = True
sys.exit(1 if failed else 0)
