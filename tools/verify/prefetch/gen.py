# Lifts the REAL voice-load functions (loadVoice and everything it calls on
# a failure) out of EasyVoiceTtsService.kt into a fake service, with the
# downstream engine replaced by an AOSP-shaped fake. See run.sh.
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
FUNS = ["loadVoice", "setLanguageFailed", "setVoiceFailed", "clientIsDead", "loadVoiceImpl",
        "loadVoiceOriginal", "loadVoiceDedicated", "voiceEntries", "parseVoiceNameAsLocale", "parseVoiceNameAsLocaleUncached",
        "isSameEnginePkg", "normPkg", "nullableIso3", "nullableIso3Country", "localeIso3", "localeMatches", "localeMatchesParams"]
body = "\n".join(fun(n) for n in FUNS) + "\n" + cls("VoiceEntry") + "\n" + cls("EngineWrapper")
# the parser's cache (2026-10-06): its two fields, as they are in the file
for decl in ("    private val parsedLocales", "    private val NO_LOCALE"):
    k = text.index(decl); body += "\n" + text[k:text.index("\n", k)]
body = body.replace("private fun", "fun").replace("private inner class", "inner class")
body = body.replace("val router: StreamRouter by lazy { StreamRouter(this) }", "")  # the direct player's callback router: not in this harness
body = body.replace("android.speech.tts.Voice", "Voice").replace("android.util.Log.w", "FakeLog.w")
body = re.sub(r"val stopExec:.*?prestartCoreThread\(\) \}\n", "", body, flags=re.S)
if "stopExec:" in body: sys.exit("stopExec not removed")
body = body.replace("stopExec.execute {", "run {").replace("return@execute", "return@run")
import os
neg = os.environ.get("EV_NEG", "")
if neg == "alwaysdead":
    body = body.replace('clientIsDead(wrapper, "setLanguage failed and the engine does not answer")',
                        'clientIsDead(wrapper, "NEG")', 1)
    body = body.replace("isSameEnginePkg(EngineFinder.boundEngineOf(client, wrapper.rawPkg), wrapper.rawPkg)) {\n                EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + \" is connected and answering -- kept, not restored\")\n                wrapper.refusedLocale",
                        "false) {\n                EasyVoiceLogger.error(EasyVoiceLogger.TAG, wrapper.rawPkg + \" is connected and answering -- kept, not restored\")\n                wrapper.refusedLocale", 1)
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused", "UNCHECKED_CAST")\n')
    f.write(open(driver, encoding="utf-8").read().replace("//LIFTED//", body))
