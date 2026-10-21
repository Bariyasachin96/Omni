#!/usr/bin/env python3
"""Verdicts for tools/verify/allchars.

    check.py <meta.tsv> <probe.txt> <codes.tsv>

A text's voice is worked out as the service works it out: mix -- each span's
code through IsoCodes (codes.tsv), kept if that language is enabled, else the
preferred Latin (eng) or non-Latin (guj) voice by the span's flag; auto -- the
window answer, else the longest span's, kept if enabled, else the auto
language (eng here); dual -- type 1 is the Latin voice, 2 the other (judged for
letters and marks; punctuation, symbols and digits follow the reading modes).
A character MISSES when it has acceptable voices (enabled languages that write
one of its owning scripts) and a span holding it goes to another one, when it
is dropped (no record text in mix), or when a letter or mark is SPLIT from the
letter of its script before it (two records in mix, two chunk types in dual). Characters with no owning script follow
the reading modes and are not judged; characters whose script no enabled
language writes are counted ("no voice"), not judged.
"""
import os, sys, collections
meta_tsv, probe_txt, codes_tsv = sys.argv[1:4]
iso = {}
for line in open(codes_tsv, encoding="utf-8"):
    c, i = line.rstrip("\n").split("\t")
    iso[c] = None if i == "-" else i
SETS = {}
meta = {}
for line in open(meta_tsv, encoding="utf-8"):
    f = line.rstrip("\n").split("\t")
    meta[f[0]] = f[1:]
OWNER = ["hin", "eng", "guj"]
def enabled(setname):
    return OWNER + ([setname[1:]] if setname.startswith("+") else [])
def voice_mix(span, en):
    lang, flag = span.rsplit("/", 1)
    i = iso.get(lang)
    if i in en:
        return i
    return "eng" if flag == "L" else "guj"
def voice_auto(ans, en):
    head, _, best = ans.partition(">")
    code = head
    if head.lower() in ("unknown", "un", "") and best:
        code = best.rsplit("/", 1)[0]
    i = iso.get(code)
    return i if i in en else "eng"
miss = collections.Counter(); ok = collections.Counter(); novoice = collections.Counter(); dropped = []; split = []
examples = collections.defaultdict(list)
for line in open(probe_txt, encoding="utf-8"):
    f = line.rstrip("\n").split("\t")
    if len(f) < 5:
        continue
    tid, spans, recs, auto, dual = f[:5]
    cp, gc, variant, setname, owning, acc = meta[tid]
    en = enabled(setname)
    accset = set(acc.split(",")) if acc != "-" else set()
    key = (variant, setname == "owner")
    if spans != "" and recs.replace("\x1f", "") == "" and gc[0] != "M":
        dropped.append((cp, gc, setname))
    # a letter or mark after a letter of its own script is one word: one record, one dual chunk
    if variant == "after" and gc[0] in "LM" and ("\x1f" in recs or len(set(dual) - {"-"}) > 1):
        split.append((cp, gc, setname, dual))
    if owning == "-":
        continue
    if not accset:
        novoice[variant] += 1
        continue
    problems = []
    if spans not in ("", "-"):
        for sp in spans.split(","):
            if sp.startswith("type"):
                continue
            v = voice_mix(sp, en)
            if v not in accset:
                problems.append("mix " + sp + "->" + v)
    if auto:
        v = voice_auto(auto, en)
        if v not in accset:
            problems.append("auto " + auto + "->" + v)
    if dual and dual != "-" and gc[0] in "LM":    # punctuation, symbols and digits follow the reading modes in dual
        want = "1" if accset == {"eng"} else "2" if "eng" not in accset else None
        if want and any(t != want for t in dual if t in "12"):
            problems.append("dual " + dual)
    cat = "%s/%s" % (variant, "owner" if setname == "owner" else "own")
    if problems:
        miss[cat] += 1
        sc = owning.split(",")[0]
        if len(examples[sc]) < (10**9 if os.environ.get("EV_ALL") else 6):
            examples[sc].append("U+%s %s %s [%s] want %s: %s" % (cp, gc, setname, owning, acc, "; ".join(problems)))
    else:
        ok[cat] += 1
for cat in sorted(set(ok) | set(miss)):
    print("%-12s %7d right  %6d wrong" % (cat, ok[cat], miss[cat]))
print("no enabled voice for the script (not judged): " + ", ".join("%s %d" % kv for kv in sorted(novoice.items())))
print("dropped (no text left, not a mark): %d" % len(dropped))
for d in dropped[:20]:
    print("  DROPPED U+%s %s %s" % d)
print("split from the letter before it: %d" % len(split))
for d in split[:20]:
    print("  SPLIT U+%s %s %s dual=%s" % d)
bad = sorted(examples.items(), key=lambda kv: -len(kv[1]))
for sc, ex in bad:
    for e in ex:
        print("MISS " + e)
total = sum(miss.values())
print("characters judged: %d right, %d wrong" % (sum(ok.values()), total))
sys.exit(1 if total or dropped or split else 0)
