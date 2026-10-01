#!/usr/bin/env python3
"""Compare two probe outputs line by line. A processDirect line (P = mix with
detection, Q = dual) is split into records -- each record starts with its field
count -- and, with mask on, field 1 of every record (the kind) is set aside and
only counted. Everything else must be identical."""
import sys
import itertools
# Read line by line: the two outputs are hundreds of MB, and a broken core
# can make one far longer than the other.
oldf = open(sys.argv[1], encoding="utf-8", errors="surrogateescape")
newf = open(sys.argv[2], encoding="utf-8", errors="surrogateescape")
mask = sys.argv[3] == "1"
def records(fields):
    out = []; i = 0
    while i < len(fields):
        n = int(fields[i]); out.append(fields[i + 1:i + 1 + n]); i += 1 + n
    return out
cases = recs = chunks_detected = kind_diff = bad = 0
for i, (o, n) in enumerate(itertools.zip_longest(oldf, newf, fillvalue=None)):
    if o is None or n is None:
        print(f"line count differs from line {i}"); bad += 1; break
    o = o.rstrip("\n"); n = n.rstrip("\n")
    if not o.startswith(("P\t", "Q\t")):
        if o.startswith("A\t"): cases += 1
        if o != n:
            bad += 1
            if bad <= 10: print(f"line {i} differs:\n  old {o[:300]}\n  new {n[:300]}")
        continue
    cases += 1
    of = o.split("\t")[2:]; nf = n.split("\t")[2:]
    if of == [""]: of = []
    if nf == [""]: nf = []
    try:
        orr = records(of); nr = records(nf)
    except Exception as e:
        bad += 1; print(f"line {i}: cannot parse records ({e})"); continue
    if mask:
        for a, b in zip(orr, nr):
            if len(a) >= 2 and len(b) >= 2 and a[1] != b[1]: kind_diff += 1
        orr = [r[:1] + r[2:] for r in orr]; nr = [r[:1] + r[2:] for r in nr]
    recs += len(nr); chunks_detected += sum(1 for r in nr if len(r) > (3 if mask else 4))
    if orr != nr:
        bad += 1
        if bad <= 10: print(f"line {i} differs:\n  old {orr}\n  new {nr}")
print(f"{cases} cases, {recs} records, {chunks_detected} detected records"
      + (f", kind field differed in {kind_diff} records (masked)" if mask else "")
      + f": {bad} differ")
sys.exit(1 if bad else 0)
