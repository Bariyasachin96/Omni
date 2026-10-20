"""The texts oracle.py compares, one per line:

    corpus.py udhr2_dir extra.txt out.txt

  - every line of the UDHR in each of udhr2's 388 languages and variants
  - the first 1, 2, 3 and 5 words of its first 30 lines (short text is where
    a detector is least sure, so where rounding could tip a decision)
  - extra.txt: scripts the UDHR set lacks (Telugu, Sinhala, Sindhi...),
    romanized text, and short app-like strings
  - 3000 random strings over many scripts, fixed seed
"""
import os
import random
import sys

udhr, extra, out = sys.argv[1:4]
texts = []
for name in sorted(os.listdir(udhr)):
    if not name.endswith(".txt"):
        continue
    with open(os.path.join(udhr, name), encoding="utf-8", errors="replace") as f:
        lines = [" ".join(x.split()) for x in f.read().split("\n")]
    lines = [x for x in lines if x]
    texts += lines
    for line in lines[:30]:
        words = line.split()
        texts += [" ".join(words[:n]) for n in (1, 2, 3, 5) if len(words) > n]
with open(extra, encoding="utf-8") as f:
    texts += [x for x in (" ".join(y.split()) for y in f.read().split("\n")) if x and not x.startswith("#")]

POOLS = [(0x61, 0x7A), (0x41, 0x5A), (0x30, 0x39), (0x20, 0x2F), (0xC0, 0x24F), (0x370, 0x3FF), (0x400, 0x4FF),
         (0x531, 0x587), (0x5D0, 0x5EA), (0x620, 0x64A), (0x900, 0x97F), (0x980, 0x9FF), (0xA00, 0xDFF),
         (0xE00, 0xEFF), (0x1000, 0x109F), (0x10A0, 0x10FF), (0x1100, 0x11FF), (0x1E00, 0x1FFF), (0x3040, 0x30FF),
         (0x4E00, 0x9FFF), (0xAC00, 0xD7A3), (0x1F300, 0x1FAFF)]
rng = random.Random(20261020)
for i in range(3000):
    pools = rng.sample(POOLS, rng.randint(1, 3))
    s = "".join(chr(rng.randint(*rng.choice(pools))) if rng.random() > 0.15 else " " for _ in range(rng.randint(1, 160)))
    s = " ".join(s.split())
    if s:
        texts.append(s)

seen = set()
with open(out, "w", encoding="utf-8") as f:
    for t in texts:
        if t not in seen:
            seen.add(t)
            f.write(t + "\n")
print("corpus: %d texts" % len(seen))
