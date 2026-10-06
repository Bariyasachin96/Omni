# Lifts the REAL functions out of LangStore.kt / EasyVoiceTtsService.kt (old
# commit and working tree) into Check.kt, with the Android/JNI parts stubbed.
import re, sys
old_ls, new_ls, new_svc, driver, out = sys.argv[1:6]
def lift(path, name, kind="fun"):
    text = open(path, encoding="utf-8").read()
    m = re.search(r"^[ \t]*(?:@JvmStatic[ \t]*)?(?:private |internal |override )?fun " + name + r"\(", text, re.M)
    if not m:
        m2 = re.search(r"^[ \t]*@JvmStatic\s*\n[ \t]*fun " + name + r"\(", text, re.M)
        if not m2: sys.exit("no %s in %s" % (name, path))
        m = m2
    i = m.start()
    # expression body on one line (fun x(...) = ...)
    eol = text.index("\n", text.index("(", i))
    head = text[i:eol]
    if "{" not in head and "=" in head:
        return head.strip().replace("private ", "")
    j = text.index("{", i); depth = 0
    while True:
        c = text[j]
        if c == "{": depth += 1
        elif c == "}":
            depth -= 1
            if depth == 0: break
        elif c == '"':
            j += 1
            while text[j] != '"':
                if text[j] == "\\": j += 1
                j += 1
        j += 1
    return text[i:j+1].strip().replace("private fun", "fun").replace("override fun", "fun").replace("@JvmStatic", "")
def cls(path, name):
    text = open(path, encoding="utf-8").read()
    i = text.index("class " + name + "(")
    j = text.index("\n}\n", i)
    return text[i:j+2]
OLD = ["engineFor", "localeFor", "variantFor", "speedFor", "volumeFor", "pitchFor", "availableLanguagesFor"]
NEW = ["isAvailable", "voiceParamsFor", "voiceTargetFor", "replaceAll", "sameDerivedContent", "engineFor", "loadPlan"]
SVC = ["refillEnabledIso2", "detectOkIso3", "pushDetectSetsOnly", "pushLanguageSets", "sendDetectSets", "onGetVoices"]
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused")\n')
    f.write(cls(new_ls, "LangEntry").replace("LangStore.listVersion", "Lists.listVersion") + "\n")
    f.write("object OldLS {\n val languages get() = Lists.languages\n" +
            "\n".join(lift(old_ls, n) for n in OLD) + "\n}\n")
    f.write("object NewLS {\n val languages get() = Lists.languages\n val listVersion get() = Lists.listVersion\n" +
            re.search(r"    class LoadPlan\(.*?\)\n", open(new_ls, encoding="utf-8").read(), re.S).group(0) +
            "\n".join(lift(new_ls, n) for n in NEW) + "\n}\n")
    svc = "\n".join(lift(new_svc, n) for n in SVC).replace("LangStore.", "Lists.")
    f.write("object Svc {\n" + svc + "\n" + '''
 val enabledIso2: HashSet<String> = HashSet()
 val sentDetectLock = Any()
 var sentDetectOk: Set<String>? = null
 var sentEnabled: Set<String>? = null
 var pushedListVersion = -1L
 var sentHints: Array<String>? = null
 var coreHints: Array<String>? = null
 var hintSends = 0
 var serviceVoices: Pair<Long, List<Voice>>? = null
 fun calledFromOwnClient(w: String) = false
 fun availableLanguagesForNow() = OldLS.availableLanguagesFor(null, true)
 var coreOk: Set<String> = emptySet()
 var coreEnabled: Set<String> = emptySet()
 var sends = 0
 var failNext = false
 fun setDetectSets(ok: Array<String>, en: Array<String>) {
     if (failNext) { failNext = false; throw RuntimeException("injected") }
     sends++; coreOk = ok.toHashSet(); coreEnabled = en.toHashSet()
 }
 fun setLanguageHints(a: Array<String>) { hintSends++; coreHints = a.copyOf() }
}
''')
    f.write(open(driver, encoding="utf-8").read())
