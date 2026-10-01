# Lifts the REAL persist functions out of LangStore.kt (old commit and working
# tree) into Check.kt, with SharedPreferences and the statics they read stubbed.
import re, sys
old_ls, new_ls, driver, out = sys.argv[1:5]
def lift(path, name):
    text = open(path, encoding="utf-8").read()
    m = re.search(r"^[ \t]*(?:@JvmStatic[ \t]*\n?[ \t]*)?(?:private )?fun " + name + r"\(", text, re.M)
    if not m: sys.exit("no %s in %s" % (name, path))
    i = text.index("fun " + name + "(", m.start())
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
    return text[i:j+1]
FUNS = ["persistLanguages", "persistDisabled", "persistEngines", "persistVoiceList", "persistVoiceRows", "voiceRowKey",
        "persistAll", "persistModeLangs", "persistMode", "persistFlags"]
HELPERS = ["putLanguages", "putEngines", "putVoiceList", "putModeLangs", "putMode", "putFlags", "putVoiceRows"]
def body(path):
    text = open(path, encoding="utf-8").read()
    parts = []
    m = re.search(r"^[ \t]*private class Persisted\(.*$", text, re.M)
    if m: parts.append(m.group(0).strip().replace("private ", ""))
    names = FUNS + (["snapshot"] if "fun snapshot(" in text else []) + [h for h in HELPERS if ("fun " + h + "(") in text]
    for n in names:
        if n == "voiceRowKey":
            m = re.search(r"fun voiceRowKey\(.*\n.*\n", text)
            parts.append(m.group(0)); continue
        parts.append(lift(path, n))
    return "\n".join(parts).replace("SharedPreferences.Editor", "Prefs.Ed").replace("SharedPreferences", "Prefs")
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused")\n')
    f.write("object OldP {\n val languages get() = Lists.languages\n" + body(old_ls) + "\n}\n")
    f.write("object NewP {\n val languages get() = Lists.languages\n" + body(new_ls) + "\n}\n")
    f.write(open(driver, encoding="utf-8").read())
