# Lifts EngineFinder's engine questions (attach, refreshBuiltInEngine,
# queryEngines, installedEngines, isTtsEngine, engineLabel, isSelfEngine,
# friendlyName, scanLabelOf) from the old commit and the working tree into two
# objects with their own state, against a fake package manager.
import re, sys
old, new, driver, out = sys.argv[1:5]
NAMES = ["attach", "refreshBuiltInEngine", "installedEngines", "queryEngines", "isTtsEngine",
         "engineLabel", "isSelfEngine", "friendlyName", "scanLabelOf"]
def lift(text, name):
    m = re.search(r"(?:@JvmStatic\s+)?(?:private\s+)?fun " + name + r"\(", text)
    if not m: return None
    i = m.start()
    # expression body: "fun x(...): T =\n  body" -- up to the next blank-ish declaration
    j = text.index("(", i); d = 0
    while True:
        if text[j] == "(": d += 1
        elif text[j] == ")":
            d -= 1
            if d == 0: break
        j += 1
    rest = text[j:j+200]
    brace = text.index("{", j); eq = text.find("=", j)
    if eq != -1 and eq < brace and "\n" in text[j:brace] and text[j:eq].strip().startswith(")"):
        end = text.index("\n    ", text.index("\n", eq) + 1)   # next member at 4 spaces
        body = text[i:end]
    else:
        k = brace; d = 0
        while True:
            c = text[k]
            if c == "{": d += 1
            elif c == "}":
                d -= 1
                if d == 0: break
            elif c == '"':
                k += 1
                while text[k] != '"':
                    if text[k] == "\\": k += 1
                    k += 1
            k += 1
        body = text[i:k+1]
    return body.replace("android.content.pm.PackageManager.", "PackageManager.") \
               .replace("android.content.pm.ApplicationInfo.", "ApplicationInfo.")
def obj(path, tag):
    text = open(path).read()
    parts = [x for x in (lift(text, n) for n in NAMES) if x]
    return ("object %s {\n" % tag + STATE + "\n".join(parts) + "\n}\n")
STATE = """
 @Volatile var appContext: Context? = null
 @Volatile var builtInEngine: String = ""
 @Volatile var builtInKnown = false
 val labelCache = java.util.concurrent.ConcurrentHashMap<String, String>()
 val selfCache = java.util.concurrent.ConcurrentHashMap<String, Boolean>()
 @Volatile var scanLabels: Pair<List<ScanVoice>, Map<String, String>>? = null
 val lastScanVoices get() = Shared.lastScanVoices
 data class EngineService(val pkg: String, val label: String, val system: Boolean, val priority: Int)
"""
with open(out, "w") as f:
    f.write("import java.util.Locale\n")
    f.write(obj(old, "OldEF")); f.write(obj(new, "NewEF"))
    f.write(open(driver).read())
