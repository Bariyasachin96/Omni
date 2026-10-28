"""choices.tsv as a table: per set and language, the share of texts whose voice
is the text's own language, where two or more languages of the set write the
text's script (elsewhere the script alone decides).

    report.py choices.tsv

"held-out" is the half the suite measured on from 2026-10-21 to 2026-10-27 (the
"test" rows whose crc32 of label + text is odd), so its total compares with the
figures recorded then; "all" is every row.
"""
import collections
import sys
import zlib

rows = [l.rstrip("\n").split("\t") for l in open(sys.argv[1], encoding="utf-8")]
rows = [r for r in rows if len(r) >= 7 and int(r[4]) >= 2]


def held(r):
    return r[3] == "test" and zlib.crc32((r[1] + r[6]).encode("utf-8")) % 2 == 1


stat = collections.defaultdict(collections.Counter)
for r in rows:
    s, label, cls, _, _, got = r[:6]
    for k in ((s, label), (s, "*"), ("*", cls), ("*", "*")):
        stat[k]["n"] += 1
        stat[k]["ok"] += got == label
        if held(r):
            stat[k]["hn"] += 1
            stat[k]["hok"] += got == label
print("%-26s %8s %8s %10s %8s" % ("set  language", "all", "right", "held-out", "right"))
for k in sorted(stat, key=lambda k: (k[0] == "*", k)):
    st = stat[k]
    h = "%10d %7.2f%%" % (st["hn"], 100.0 * st["hok"] / st["hn"]) if st["hn"] else "%10s %8s" % ("-", "-")
    print("%-26s %8d %7.2f%% %s" % ("  ".join(k), st["n"], 100.0 * st["ok"] / st["n"], h))
