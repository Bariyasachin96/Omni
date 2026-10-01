# Lifts sortKey + sortedByName out of the working tree's LangStore.kt; the old
# comparator (sortKey on both sides of every comparison) is written beside it
# exactly as LangStore/EngineFinder had it.
import re, sys
ls, driver, out = sys.argv[1:4]
t = open(ls, encoding="utf-8").read()
cm = re.search(r"private val COMBINING_MARKS = .*\n", t).group(0).replace("private ", "")
sk = re.search(r"private fun sortKey\(text: String\): String =\n.*\n", t).group(0).replace("private ", "")
i = t.index("fun <T> sortedByName(")
j = t.index("\n    }\n", i)
open(out, "w", encoding="utf-8").write(cm + sk + t[i:j+6] + open(driver, encoding="utf-8").read())
