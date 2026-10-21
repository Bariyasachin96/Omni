"""TWO LANGUAGES OF ONE SCRIPT IN ONE TEXT (owner, 2026-10-21: "Arabic language
Arabic voice ke pass ... jo bhasha ke liye Jo voice Chuna Hai vah bhasha ke liye
Vahi voice ... aisa Nahin Ki Ek Hi voice do Bhasha Pade").

    check.py <work dir>      (run.sh builds seg and probe there first)

1. langblend.h's orthoSegment (seg) against tools/langid/train_nb.py's
   ortho_segment, the reference it mirrors, on every text below: the same cuts,
   byte for byte, or FAIL.
2. The real core (probe: mix mode's processDirect, the context cleared before
   each text): FLORES-200 sentences (never learned from) in one language must
   never be read in another language in part (a text cut into two of them), and
   two sentences of two languages in one text -- after a sentence end, after a
   space, three words or one word inside the other's sentence -- are measured
   word by word.
3. What the owner hears, exactly: "كتاب" with Arabic kaf after an Urdu
   sentence is Arabic, "کتاب" with keheh after an Arabic one is Urdu, "تمام"
   (the same in both) is whichever language came before, an Urdu sentence and an
   Arabic one in one text reach their own voices -- and the same for Persian and
   Pashto, Russian and Ukrainian, Bengali and Assamese.
"""
import collections
import os
import subprocess
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(os.path.dirname(os.path.dirname(HERE)))
sys.path.insert(0, os.path.join(ROOT, "tools", "langid"))
import train_nb as T  # noqa: E402

WORK = sys.argv[1]
OT, NB = T.ortho(), T.shipped()
ISO3 = {"en": "eng", "ru": "rus", "uk": "ukr", "bg": "bul", "sr": "srp", "ur": "urd", "ar": "ara", "fa": "fas",
        "ps": "pus", "bn": "ben", "as": "asm"}
SETS = {2: [["ru", "uk"], ["ru", "uk", "bg", "sr"]], 3: [["ur", "ar"], ["ur", "fa"], ["fa", "ps"], ["ar", "fa", "ur", "ps"]],
        6: [["bn", "as"]]}
STOP = {"ur": "۔ ", "bn": "। ", "as": "। "}


def fl(L):
    return T.flores(L, "dev") + T.flores(L, "devtest")


def script_runs(slot, text):
    """(run, start): each stretch of the script's own letters inside a word --
    "JASگریپین" holds the Latin JAS and the Urdu گریپین, which the core reads
    as two spans"""
    out = []
    for w, ws, _ in T.ortho_words(text):
        at = None
        for k, c in enumerate(w + " "):
            if c != " " and T.of_script(ord(c), slot):
                if at is None:
                    at = k
            elif at is not None:
                out.append((w[at:k], ws + at))
                at = None
    return out


def script_words(slot, text):
    return [w for w, _ in script_runs(slot, text)]


# ---- the texts: (slot, set, text, truth) with truth [(start, end, language)] ----
cases = []
for slot, sets in SETS.items():
    for cands in sets:
        for L in cands:
            for t in fl(L):
                cases.append((slot, cands, "mono", t, [(0, len(t), L)]))
        for A in cands:
            for B in cands:
                if A == B:
                    continue
                fa, fb = fl(A), fl(B)
                for k in range(150):
                    s1, s2 = fa[k], fb[(k * 7 + 3) % len(fb)]
                    a1 = s1.rstrip(".।۔؟?!")
                    for kind in ("dot", "nopunct", "embed3", "embed1"):
                        if kind.startswith("embed"):
                            w1, w2 = s1.split(), s2.split()
                            at = 1 + (k % max(1, len(w1) - 1))
                            ph = " ".join(w2[:3] if kind == "embed3" else w2[:1])
                            pre = " ".join(w1[:at]) + " "
                            text = pre + ph + " " + " ".join(w1[at:])
                            truth = [(0, len(pre), A), (len(pre), len(pre) + len(ph), B), (len(pre) + len(ph), len(text), A)]
                        else:
                            sep = STOP.get(A, ". ") if kind == "dot" else " "
                            text = a1 + sep + s2
                            truth = [(0, len(a1) + len(sep), A), (len(a1) + len(sep), len(text), B)]
                        cases.append((slot, cands, kind, text, truth))

# ---- 1. the C++ segmentation equals the reference ----
seg_in = "".join("%d\t%s\t%s\n" % (slot, ",".join(["en"] + cands), text.replace("\n", "\\n"))
                 for slot, cands, _, text, _ in cases)
out = subprocess.run([os.path.join(WORK, "seg")], input=seg_in.encode("utf-8"), stdout=subprocess.PIPE, check=True)
cpp = out.stdout.decode("utf-8").split("\n")
differ = 0
for (slot, cands, kind, text, _), got in zip(cases, cpp):
    py = T.ortho_segment(OT, NB, slot, text, cands)
    want = ",".join(str(len(text[:o].encode("utf-8"))) for o in py) or "-"
    if want != got:
        differ += 1
        if differ <= 5:
            print("   segmentation differs (%s %s): python %s  C++ %s  %r" % ("+".join(cands), kind, want, got, text[:60]))
print("segmentation: C++ (langblend.h orthoSegment) vs python (train_nb.ortho_segment): %d of %d texts differ"
      % (differ, len(cases)))
failed = differ > 0


# ---- 2. the real core ----
def run_probe(lines, probe="probe"):
    p = subprocess.run([os.path.join(WORK, probe)], input="".join(lines).encode("utf-8"),
                       stdout=subprocess.PIPE, check=True)
    res = {}
    for line in p.stdout.decode("utf-8").split("\n"):
        if not line:
            continue
        f = line.split("\t")
        res[f[0]] = [tuple(x.split("\x1f", 1)) for x in f[1:]]
    return res


lines, last = [], None
for i, (slot, cands, kind, text, truth) in enumerate(cases):
    s = ["en"] + cands
    if s != last:
        lines.append("SET\t%s\t%s\n" % (",".join(s), ",".join(ISO3[x] for x in s)))
        last = s
    lines.append("RESET\n")
    lines.append("TEXT\t%d\t%s\n" % (i, text.replace("\n", "\\n")))

def measure(spans):
    """per (set, kind): words right; per one-language text, whether it was read in two"""
    stats = collections.defaultdict(collections.Counter)
    cut, unaligned = set(), []
    for i, (slot, cands, kind, text, truth) in enumerate(cases):
        want = [(w, next(L for a, b, L in truth if a <= ws < b)) for w, ws in script_runs(slot, text)]
        got = []
        for lang, t in spans.get(str(i), []):
            got += [(w, lang) for w in script_words(slot, t)]
        if [w for w, _ in got] != [w for w, _ in want]:
            unaligned.append(i)
            continue
        st = stats[("+".join(cands), kind)]
        st["texts"] += 1
        st["words"] += len(want)
        st["right"] += sum(1 for (w, g), (_, L) in zip(got, want) if g == L)
        if kind == "mono" and len({g for _, g in got}) > 1:
            cut.add(i)
            st["cut"] += 1
    return stats, cut, unaligned


now, now_cut, now_unaligned = measure(run_probe(lines))
was, was_cut, was_unaligned = measure(run_probe(lines, "probe-ref"))
print("real core: %d texts; words not given back in order: %d (before: %d)"
      % (len(cases), len(now_unaligned), len(was_unaligned)))
failed |= len(now_unaligned) > len(was_unaligned)
print("%-18s %16s %14s %14s %14s %14s %14s" % ("set (now / before)", "one-language", "read in two",
                                               "dot", "nopunct", "embed3", "embed1"))
for slot, sets in SETS.items():
    for cands in sets:
        name = "+".join(cands)
        row = "%-18s" % name
        m, mw = now[(name, "mono")], was[(name, "mono")]
        row += " %7.2f/%6.2f%% %6d/%-6d" % (100 * m["right"] / max(1, m["words"]), 100 * mw["right"] / max(1, mw["words"]),
                                         m["cut"], mw["cut"])
        for kind in ("dot", "nopunct", "embed3", "embed1"):
            st, sw = now[(name, kind)], was[(name, kind)]
            row += " %6.1f/%5.1f%%" % (100 * st["right"] / max(1, st["words"]), 100 * sw["right"] / max(1, sw["words"]))
        print(row)
        if now[(name, "dot")]["right"] < 0.99 * now[(name, "dot")]["words"]:
            print("FAILED: %s, two sentences in one text: below 99%% of words right" % name)
            failed = True
# a text in one language the core read in one before is never read in two now
new_cut = sorted(now_cut - was_cut)
print("one-language texts read in two languages: %d now, %d before, %d of them new"
      % (len(now_cut), len(was_cut), len(new_cut)))
for i in new_cut[:8]:
    slot, cands, kind, text, truth = cases[i]
    print("   new: %s %s %r" % ("+".join(cands), truth[0][2], text[:80]))
failed |= len(new_cut) > 0

# ---- 3. what the owner hears ----
UR, AR = "یہ کتاب میری ہے۔", "هذا الكتاب لي."
scen = [
    ("كتاب (Arabic kaf) after Urdu is Arabic", ["ur", "ar"], [UR, "كتاب"], ["ur", "ar"]),
    ("کتاب (keheh) after Arabic is Urdu", ["ur", "ar"], [AR, "کتاب"], ["ar", "ur"]),
    ("تمام after Urdu stays Urdu", ["ur", "ar"], [UR, "تمام"], ["ur", "ur"]),
    ("تمام after Arabic stays Arabic", ["ur", "ar"], [AR, "تمام"], ["ar", "ar"]),
    ("an Urdu and an Arabic sentence in one text", ["ur", "ar"], [UR + " " + AR], ["ur ar"]),
    ("an Arabic and an Urdu sentence in one text", ["ur", "ar"], [AR + " " + UR], ["ar ur"]),
    ("Urdu, Arabic, Persian and Pashto sentences", ["ar", "fa", "ur", "ps"],
     [UR + " " + AR + " " + fl("fa")[0] + " " + fl("ps")[0]], ["ur ar fa ps"]),
    ("Russian then Ukrainian", ["ru", "uk"], [fl("ru")[0] + " " + fl("uk")[0]], ["ru uk"]),
    ("Bengali then Assamese", ["bn", "as"], [fl("bn")[0] + " " + fl("as")[0]], ["bn as"]),
]
lines, keys = [], []
for n, (title, cands, texts, want) in enumerate(scen):
    s = ["en"] + cands
    lines.append("SET\t%s\t%s\n" % (",".join(s), ",".join(ISO3[x] for x in s)))
    lines.append("RESET\n")
    for m, t in enumerate(texts):
        lines.append("TEXT\ts%d_%d\t%s\n" % (n, m, t))
res = run_probe(lines)
for n, (title, cands, texts, want) in enumerate(scen):
    got = []
    for m in range(len(texts)):
        langs = []
        for lang, t in res.get("s%d_%d" % (n, m), []):
            if script_words(T.SLOT[cands[0]], t) and (not langs or langs[-1] != lang):
                langs.append(lang)
        got.append(" ".join(langs))
    ok = got == want
    failed |= not ok
    print("  %-4s %-48s %s" % ("ok" if ok else "FAIL", title, " | ".join(got)))

print("ALL PASS" if not failed else "FAILED")
sys.exit(1 if failed else 0)
