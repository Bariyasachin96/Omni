# Lifts the REAL settings read/write code out of LangStore.kt -- readFailed,
# prefs(), the write queue, write(), writeLater(), refusedAfterFailedRead() --
# into a class (one per scenario, so each starts with readFailed false), with
# the Android Context replaced by a holder of a DataStore the driver chooses:
# the real library over a real file, or a fake whose data flow throws.
import re, sys
src, driver, out, neg = sys.argv[1:5]
text = open(src, encoding="utf-8").read()
def block(start_pat):
    m = re.search(start_pat, text, re.M)
    if not m: sys.exit("not found: " + start_pat)
    i = m.start()
    j = text.index("{", m.end() - 1) if text[m.end() - 1] != "{" else m.end() - 1
    # a function: lift to its closing brace; a `val x = Foo(...)` lifts to the
    # end of its balanced parentheses.
    depth = 0; k = text.index("(" if "val writes" in start_pat else "{", i)
    open_c, close_c = ("(", ")") if "val writes" in start_pat else ("{", "}")
    while True:
        c = text[k]
        if c == open_c: depth += 1
        elif c == close_c:
            depth -= 1
            if depth == 0: break
        elif c == '"':
            k += 1
            while text[k] != '"':
                if text[k] == "\\": k += 1
                k += 1
        k += 1
    return text[i:k+1]
parts = [
    re.search(r"^[ \t]*@Volatile private var readFailed = false$", text, re.M).group(0),
    block(r"^[ \t]*fun prefs\(ctx: Context\): Preferences = runBlocking \{"),
    block(r"^[ \t]*private val writes = CoroutineScope\("),
    block(r"^[ \t]*fun write\(ctx: Context, block: \(MutablePreferences\) -> Unit\) \{"),
    block(r"^[ \t]*fun writeLater\(ctx: Context, block: \(MutablePreferences\) -> Unit\) \{"),
    block(r"^[ \t]*private fun refusedAfterFailedRead\(\): Boolean \{"),
]
body = "\n".join(parts)
body = body.replace("ctx.applicationContext.settingsStore", "ctx.store")
body = body.replace("val app = ctx.applicationContext", "val app = ctx").replace("app.settingsStore", "app.store")
body = body.replace("private var readFailed", "var readFailed")
if neg == "retry":
    # the read repeated once more before the catch -- what this code had until
    # the owner asked for it to go
    a = "            .catch { ex ->"; assert a in body; body = body.replace(a, "            .retry(1) { it is IOException }\n" + a)
elif neg == "nocatch":
    i = body.index("            .catch { ex ->"); j = body.index("            .first()")
    body = body[:i] + body[j:]
elif neg == "norefuse":
    a = "        if (!readFailed) return false"; assert a in body; body = body.replace(a, "        return false")
elif neg == "catchall":
    a = "                if (ex !is IOException) throw ex\n"; assert a in body; body = body.replace(a, "")
elif neg: sys.exit("unknown EV_NEG " + neg)
drv = open(driver, encoding="utf-8").read().split("\n")
imports = [l for l in drv if l.startswith("import ")]
rest = "\n".join(l for l in drv if not l.startswith("import "))
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "unused")\n' + "\n".join(imports) + "\n")
    f.write("class LS {\n" + body + "\n}\n" + rest)
