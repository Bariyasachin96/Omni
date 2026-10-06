# Lifts the REAL restore / startup / package-death functions out of
# EasyVoiceTtsService.kt into a fake service class, with Android replaced by
# fakes the driver controls (binds, binder liveness, TextToSpeech init answers,
# the main-looper clock). See run.sh.
import re, sys
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
    m = re.search(r"^[ \t]*(?:private |override )?fun " + name + r"\(", text, re.M)
    if not m: sys.exit("no fun " + name)
    i = m.start()
    eol = text.index("\n", i)
    head = text[i:eol]
    if "{" not in head and "=" in head:
        # expression body, possibly continued on the next line
        k = eol
        nxt = text[eol+1:text.index("\n", eol+1)]
        if head.rstrip().endswith("=") or nxt.startswith("        "):
            k = text.index("\n", eol+1)
        return text[i:k]
    if "{" in head and head.rstrip().endswith("}"):
        return head
    return block(i)
def cls(name):
    i = text.index("inner class " + name + "(")
    return block(i)
FUNS = ["onEngineProcessGone", "restoreEngine", "wrapperFor", "restoreEngineMain", "restoreFailed",
        "rearmRestore", "onEngineProcessBack", "recoverEngineNotReady", "recoverEngineNotReadyMain",
        "afterStartupFailure", "startNextInitEngine", "firstWaveOf", "startEngineClient", "initFinished", "normPkg", "isSameEnginePkg",
        "engineProcessUp", "onMainThread"]
body = "\n".join(fun(n) for n in FUNS) + "\n" + cls("EngineInitListener") + "\n" + cls("RestoreInitListener")
body = body.replace("private fun", "fun").replace("android.os.Looper", "Looper")
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused", "UNCHECKED_CAST")\n')
    f.write(open(driver, encoding="utf-8").read().replace("//LIFTED//", body))
