"""langid.h's probabilities against ML Kit's own library's, text by text.

    compare.py corpus.txt ours.f32 mlkit.f32 labels.txt shrunk.u8

All 111 probabilities of every text must agree within TOL, and so must every
decision: the top label, and whether it clears ML Kit's default 0.5 (a tie
closer than TOL may fall either way). What is left between the two is the
last bits of the softmax: Eigen's vectorized exp there, std::exp here.

The one input they cannot agree on is text whose lowercase is shorter than it
(Turkish U+0130...) and too long for libc++'s 22-byte short string: past the
lowercased copy's end ML Kit's tokenizer reads heap memory, whatever it holds
(langid.h takes the zeros a short string has there). Those are reported, not
held to TOL.
"""
import sys
from array import array

corpus, ours_path, mlkit_path, labels_path, shrunk_path = sys.argv[1:6]
with open(corpus, encoding="utf-8") as f:
    lines = f.read().split("\n")
if lines and not lines[-1]:
    lines.pop()   # the newline that ends the file
with open(labels_path, encoding="utf-8") as f:
    labels = f.read().split()
L = len(labels)
ours, theirs = array("f"), array("f")
with open(ours_path, "rb") as f:
    ours.frombytes(f.read())
with open(mlkit_path, "rb") as f:
    theirs.frombytes(f.read())
with open(shrunk_path, "rb") as f:
    shrunk = f.read()
assert len(ours) == len(theirs) == len(lines) * L and len(shrunk) == len(lines)

TOL = 1e-5
texts = worst_all = ties = 0
fails, heap = [], []
for i, text in enumerate(lines):
    if not text:
        continue
    texts += 1
    o, t = ours[i * L:(i + 1) * L], theirs[i * L:(i + 1) * L]
    worst = max(abs(a - b) for a, b in zip(o, t))
    if shrunk[i] and len(text.encode()) > 22:
        heap.append((worst, text))
        continue
    worst_all = max(worst_all, worst)
    top_o, top_t = max(range(L), key=o.__getitem__), max(range(L), key=t.__getitem__)
    first, second = sorted(t)[-1], sorted(t)[-2]
    differs = top_o != top_t or (o[top_o] >= 0.5) != (t[top_t] >= 0.5)
    tie = first - second <= TOL or abs(first - 0.5) <= TOL
    if worst > TOL or (differs and not tie):
        fails.append((worst, labels[top_o], o[top_o], labels[top_t], t[top_t], text))
    elif differs:
        ties += 1

print("ML Kit: %d texts; every probability within %.0e of ML Kit's own library (max |difference| %.1e),"
      % (texts - len(heap), TOL, worst_all))
print("        so the top label and ML Kit's decision (top >= 0.5, else und) are the same, %d exact ties apart" % ties)
print("        %d texts have a shorter lowercase too long for a short string, not held: %d differ beyond %.0e (max %.1e)"
      % (len(heap), sum(1 for w, _ in heap if w > TOL), TOL, max([w for w, _ in heap] + [0])))
for f in sorted(fails, reverse=True)[:15]:
    print("   %.2e  ours %s %.5f  ML Kit %s %.5f  %r" % (f[0], f[1], f[2], f[3], f[4], f[5][:70]))
if fails:
    print("FAILED: %d texts differ from ML Kit beyond %.0e" % (len(fails), TOL))
    sys.exit(1)
print("probs: the same as ML Kit's own library")
