# Lifts findEngineForLocale from the old commit, and findEngineForLocale +
# VoiceEntry + voiceEntries from the working tree (plus the shared helpers
# parseVoiceNameAsLocale / localeIso3), into two classes that each own a
# voiceList, so the driver can feed both the same lists.
import re, sys
old, new, driver, out = sys.argv[1:5]
def block(text, start):
    i = text.index(start); j = text.index("{", i); d = 0
    while True:
        c = text[j]
        if c == "{": d += 1
        elif c == "}":
            d -= 1
            if d == 0: return text[i:j+1]
        j += 1
def line(text, start):
    i = text.index(start); return text[i:text.index("\n", i)]
o = open(old).read(); n = open(new).read()
helpers = block(n, "private fun parseVoiceNameAsLocale(") + "\n" + block(n, "private fun localeIso3(")
with open(out, "w") as f:
    f.write("fun localeOf(l: String, c: String = \"\", v: String = \"\") = java.util.Locale(l, c, v)\n")
    f.write("class OldS(val voiceList: ArrayList<String>) {\n" + helpers + "\n" + block(o, "private fun findEngineForLocale(") + "\nfun find(l: java.util.Locale) = findEngineForLocale(l)\n}\n")
    f.write("class NewS(var voiceList: ArrayList<String>) {\n" + helpers + "\n" + block(n, "private inner class VoiceEntry(").replace("private inner", "inner") + "\n" +
            line(n, "@Volatile private var parsedVoices") + "\n" + block(n, "private fun voiceEntries(").replace("private fun", "fun") + "\n" +
            block(n, "private fun findEngineForLocale(") + "\nfun find(l: java.util.Locale) = findEngineForLocale(l)\nfun entries() = voiceEntries()\nfun parse(t: String?) = parseVoiceNameAsLocale(t)\n}\n")
    f.write(open(driver).read())
