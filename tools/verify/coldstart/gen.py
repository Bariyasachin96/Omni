# Lifts the REAL cold-start waiter (awaitEngineStarting, engineSettled) out of
# EasyVoiceTtsService.kt into a fake service the driver drives with real
# threads. EV_NEG=<name> breaks the lifted code on purpose (negative tests).
import os, re, sys
svc, driver, out = sys.argv[1:4]
text = open(svc, encoding="utf-8").read()
def block(start):
    j = text.index("{", start); depth = 0
    while True:
        c = text[j]
        if c == "{": depth += 1
        elif c == "}":
            depth -= 1
            if depth == 0: return text[start:j+1]
        elif c == '"':
            j += 1
            while text[j] != '"':
                if text[j] == "\\": j += 1
                j += 1
        j += 1
def fun(name):
    m = re.search(r"^[ \t]*(?:private )?fun " + name + r"\(", text, re.M)
    if not m: sys.exit("no fun " + name)
    i = m.start(); head = text[i:text.index("\n", i)]
    return head if (head.rstrip().endswith("}") or "{" not in head) else block(i)
body = "\n".join(fun(n) for n in ["engineSettled", "awaitEngineStarting", "normPkg"])
body = body.replace("private fun", "fun").replace("android.os.Looper", "Looper").replace("android.os.SystemClock", "SystemClock")
neg = os.environ.get("EV_NEG", "")
def breaks(old, new):
    global body
    if old not in body: sys.exit("negative " + neg + ": pattern not found")
    body = body.replace(old, new, 1)
if neg == "nonotify": breaks("syncLock.notifyAll()", "Unit")
if neg == "nostop": breaks("&& !isStopped ", "")
if neg == "norestore": breaks("wrapper.state == 0 || wrapper.state == 1", "wrapper.state == 0")
if neg == "nogate": breaks("mayAwaitEngine.get() != true || ", "")
if neg == "nogeneration": breaks("generation == synthesisGeneration && ", "")
with open(out, "w", encoding="utf-8") as f:
    f.write(open(driver, encoding="utf-8").read().replace("//LIFTED//", body))
