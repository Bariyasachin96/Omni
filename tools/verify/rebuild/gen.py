# Lifts LangStore.rebuildFromScan from the old commit and the working tree,
# with the shared pieces (LangEntry, sortedByName) from the working tree.
import re, sys
old, new, driver, out = sys.argv[1:5]
def block(text, start):
    i = text.index(start); j = text.index("{", text.index(")", i)); d = 0
    # the signature spans two lines; find the body brace after "ArrayList<LangEntry>"
    j = text.index("{", text.index("ArrayList<LangEntry>", i)) if "rebuildFromScan" in start else j
    while True:
        c = text[j]
        if c == "{": d += 1
        elif c == "}":
            d -= 1
            if d == 0: return text[i:j+1]
        elif c == '"':
            j += 1
            while text[j] != '"':
                if text[j] == "\\": j += 1
                j += 1
        j += 1
o = open(old).read(); n = open(new).read()
def cls(text):
    i = text.index("class LangEntry("); j = text.index("\n}\n", i); return text[i:j+2]
shared = re.search(r"private val COMBINING_MARKS = .*\n", n).group(0) + re.search(r"private fun sortKey\(text: String\): String =\n.*\n", n).group(0)
i = n.index("fun <T> sortedByName("); shared += n[i:n.index("\n    }\n", i)+6]
with open(out, "w") as f:
    f.write(cls(n).replace("LangStore.listVersion", "Ver.v") + "\nobject Ver { val v = java.util.concurrent.atomic.AtomicLong() }\n")
    f.write("object Shared {\n" + shared + "\n}\n")
    for tag, text in (("OldR", o), ("NewR", n)):
        body = block(text, "fun rebuildFromScan(").replace("sortedByName(", "Shared.sortedByName(")
        f.write("object %s {\n var persisted = 0\n fun persistDisabled(c: Context) { persisted++ }\n fun prefs(c: Context) = c.prefs\n%s\n}\n" % (tag, body))
    f.write(open(driver).read())
