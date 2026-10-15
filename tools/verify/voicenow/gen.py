# Lifts the REAL voice-load functions out of two copies of EasyVoiceTtsService.kt
# (the last commit's and the working tree's) into SvcOld / SvcNew. See run.sh.
import re, sys
old_svc, new_svc, driver, out = sys.argv[1:5]
def lift(path):
    text = open(path, encoding="utf-8").read()
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
    def fun(name, required=True):
        m = re.search(r"^[ \t]*(?:private |override )?fun " + name + r"\(", text, re.M)
        if not m:
            if required: sys.exit("no fun " + name)
            return ""
        i = m.start(); eol = text.index("\n", i); head = text[i:eol]
        if "{" not in head and "=" in head:
            k = eol; nxt = text[eol+1:text.index("\n", eol+1)]
            if head.rstrip().endswith("=") or nxt.startswith("        "): k = text.index("\n", eol+1)
            return text[i:k]
        if "{" in head and head.rstrip().endswith("}"): return head
        return block(i)
    FUNS = ["loadVoice", "setLanguageFailed", "setVoiceFailed", "clientIsDead", "loadVoiceImpl",
            "loadVoiceOriginal", "loadVoiceDedicated", "voiceEntries", "parseVoiceNameAsLocale",
            "parseVoiceNameAsLocaleUncached", "isSameEnginePkg", "normPkg", "nullableIso3",
            "nullableIso3Country", "localeIso3", "localeMatches"]
    body = "\n".join(fun(n) for n in FUNS) + "\n" + fun("localeMatchesParams", False) + "\n" + fun("languageDiffersFromParams", False)
    for c in ("VoiceEntry", "EngineWrapper"):
        body += "\n" + block(text.index("inner class " + c + "("))
    for decl in ("    private val parsedLocales", "    private val NO_LOCALE"):
        k = text.index(decl); body += "\n" + text[k:text.index("\n", k)]
    body = body.replace("private fun", "fun").replace("private inner class", "inner class")
    body = body.replace("android.speech.tts.Voice", "Voice").replace("android.util.Log.w", "FakeLog.w")
    body = re.sub(r"val stopExec:.*?prestartCoreThread\(\) \}\n", "", body, flags=re.S)
    if "stopExec:" in body: sys.exit("stopExec not removed")
    return body.replace("stopExec.execute {", "run {").replace("return@execute", "return@run")
old_body = lift(old_svc); new_body = lift(new_svc)
import os
neg = os.environ.get("EV_NEG", "")
if neg == "nameowed":   # a named voice wanted after all, but the name never fetched.
    # NOT caught since 2026-10-15, and equal by construction: the named-branch
    # fallback now compares the params locale, so skipping the read costs one
    # setVoice of the voice already current instead of a marshal -- same voice.
    new_body = new_body.replace("nameOwed && effectiveVariant != \"*Default\"", "false", 1)
elif neg == "anyvariant":  # take the params path for every named request, and
    # never read the name, even in the request's own language (2026-10-15)
    new_body = new_body.replace('variant == "*Default" || languageDiffersFromParams(locale, p)', "true", 1)
    new_body = new_body.replace("(effectiveVariant.isEmpty() || !languageDiffersFromParams(locale, paramsLocale!!))", "effectiveVariant.isEmpty()", 1)
elif neg == "nocountry":   # compare the language only
    new_body = new_body.replace("if (matchCountry != p[1]) return false", "", 1)
elif neg == "nostale":    # the remembered locale outlives a setLanguage
    new_body = new_body.replace("set(value) { field = value; if (!value) paramLocale = null }", "", 1)
elif neg == "known":       # take the params path even when the voice is known
    new_body = new_body.replace("!wrapper.currentVoiceKnown && !EasyVoiceLogger.loggingOn", "!EasyVoiceLogger.loggingOn", 1)
if neg and new_body == lift(new_svc): sys.exit("EV_NEG=" + neg + " changed nothing")
d = open(driver, encoding="utf-8").read().replace("//OLD//", old_body).replace("//NEW//", new_body)
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused", "UNCHECKED_CAST", "DEPRECATION")\n' + d)
