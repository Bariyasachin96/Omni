"""Auto mode, an earlier core against the working tree's: per enabled set and
language, the share of texts whose answer names the text's own language (its
voice), and how many each core alone got right.

    compare.py old.tsv new.tsv isopairs.tsv
"""
import collections
import sys

iso3 = {}
for line in open(sys.argv[3], encoding="utf-8"):
    a, _, b = line.rstrip("\n").partition("\t")
    iso3[a] = b


def key(code):
    code = code.split("-")[0].split("_")[0].lower()
    return iso3.get(code, code)


old = [l.rstrip("\n").split("\t") for l in open(sys.argv[1], encoding="utf-8")]
new = [l.rstrip("\n").split("\t") for l in open(sys.argv[2], encoding="utf-8")]
assert len(old) == len(new), "the two runs answered different numbers of texts"
stat = collections.defaultdict(collections.Counter)
for o, n in zip(old, new):
    assert o[:4] == n[:4]
    s, label, cls, split = o[:4]
    ro, rn = key(o[4]) == key(label), key(n[4]) == key(label)
    for k in ((s, label), (s, "*"), ("*", cls)):
        st = stat[k]
        st["n"] += 1
        st["old"] += ro
        st["new"] += rn
        st["broke"] += ro and not rn
        st["fixed"] += rn and not ro
print("%-34s %8s %8s %8s   broke/fixed" % ("", "texts", "before", "now"))
for k in sorted(stat, key=lambda k: (k[0] == "*", k)):
    st = stat[k]
    print("%-34s %8d %7.2f%% %7.2f%%   %d/%d" % ("  ".join(k), st["n"], 100 * st["old"] / st["n"], 100 * st["new"] / st["n"],
                                            st["broke"], st["fixed"]))
