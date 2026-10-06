#!/usr/bin/env python3
"""a.java (AutoTTS) against ours, line by line. Since 2026-10-06 ours also
answers, from the current Unicode's Script property, code points a.java's
Unicode ~6 block ranges never reached; such a line is counted, not failed --
but ONLY where a.java had no answer ("un|un" unfiltered, "" filtered). Any
other difference fails. The answers themselves are checked against the UCD by
tools/verify/unicode."""
import sys
a = open(sys.argv[1], encoding="utf-8").read().split("\n")
b = open(sys.argv[2], encoding="utf-8").read().split("\n")
if len(a) != len(b): print("     line count differs"); sys.exit(1)
added = bad = 0
for x, y in zip(a, b):
    if x == y: continue
    xv = x.split(" ", 1)[1] if " " in x else ""
    if xv in ("un|un", ""): added += 1
    else:
        bad += 1
        if bad <= 5: print("     a.java: %s   ours: %s" % (x, y))
print("ADDED %d BAD %d" % (added, bad))
sys.exit(1 if bad else 0)
