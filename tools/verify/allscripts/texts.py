#!/usr/bin/env python3
"""One sample per Unicode script, from the UCD the app is generated from.

    texts.py <ucd dir>   ->  script<TAB>text   (text = up to 24 of the script's
                                                letters, in words of four)

Letters only (General_Category L*), in code point order, so the sample is the
same every run and needs no hand-typed text in any script.
"""
import os, sys

def rows(path):
    for line in open(path, encoding="utf-8"):
        line = line.split("#", 1)[0].strip()
        if not line: continue
        cps, val = [x.strip() for x in line.split(";")[:2]]
        lo, _, hi = cps.partition("..")
        yield int(lo, 16), int(hi or lo, 16), val

ucd = sys.argv[1]
gc = {}
for lo, hi, v in rows(os.path.join(ucd, "DerivedGeneralCategory.txt")):
    for c in range(lo, hi + 1): gc[c] = v
by = {}
for lo, hi, s in rows(os.path.join(ucd, "Scripts.txt")):
    if s in ("Common", "Inherited", "Unknown"): continue
    for c in range(lo, hi + 1):
        if gc.get(c, "Cn").startswith("L"): by.setdefault(s, []).append(c)
for s in sorted(by):
    cps = by[s][:24]
    words = ["".join(chr(c) for c in cps[i:i + 4]) for i in range(0, len(cps), 4)]
    print(s + "\t" + " ".join(words))
