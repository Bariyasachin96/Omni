"""raw.tsv and pipeline.tsv as tables.

    report.py raw.tsv pipeline.tsv
"""
import collections
import sys

raw = [l.rstrip("\n").split("\t") for l in open(sys.argv[1], encoding="utf-8")]
# pipeline.tsv: set label class split model fallback cands blend text ctxmap
pipe = [[r[0], r[1], r[2], r[4], r[7]] for r in (l.rstrip("\n").split("\t") for l in open(sys.argv[2], encoding="utf-8"))]
MODEL = {r[2] for r in raw}
CLASSES = ["1w", "2-3w", "4-7w", "8-15w", "16w+", "phrase"]


def raw_score(sub):
    n = len(sub)
    if not n:
        return None
    top = 100.0 * sum(r[2] == r[0] for r in sub) / n
    und = 100.0 * sum(float(r[3]) < 0.5 for r in sub) / n
    return "%5d  model %5.1f%%   (under 0.5: %4.1f%%)" % (n, top, und)


print("== 1. THE MODEL ALONE, its top answer among all its languages ==")
print("every language the model has:")
for c in CLASSES:
    s = raw_score([r for r in raw if r[1] == c and r[0] in MODEL])
    if s:
        print("   %-7s %s" % (c, s))
for lang in ["hi", "mr", "ne", "ur", "ar", "fa", "ps", "bn", "en", "ru", "uk", "bg", "sr", "zh", "ja"]:
    print(lang)
    for c in CLASSES:
        s = raw_score([r for r in raw if r[0] == lang and r[1] == c])
        if s:
            print("   %-7s %s" % (c, s))
print("\nnot in the model, or romanized -- what it answers:")
for lang in ["as", "sa", "bho", "mai", "pnb", "hi-Latn"]:
    sub = [r for r in raw if r[0] == lang]
    if sub:
        print("   %-8s %4d  model %s" % (lang, len(sub), collections.Counter(r[2] for r in sub).most_common(4)))

print("\n== 2. THE VOICE CHOSEN, with a set of languages enabled (the core's real decision) ==")
for name in dict.fromkeys(r[0] for r in pipe):
    sub = [r for r in pipe if r[0] == name]
    print(name)
    for lang in dict.fromkeys(r[1] for r in sub):
        parts = []
        for c in CLASSES:
            s = [r for r in sub if r[1] == lang and r[2] == c]
            if s:
                mod = 100.0 * sum(r[3] == lang for r in s) / len(s)
                now = 100.0 * sum(r[4] == lang for r in s) / len(s)
                parts.append("%s %3.0f/%3.0f" % (c, mod, now))
        print("   %-4s model/core %%: %s" % (lang, "  ".join(parts)))
