# Lifts languageForDetectedRun from the old commit and detectedLangOf +
# languageForDetectedRun from the working tree, with the real IsoCodes.
import sys
old, new, iso, driver, out = sys.argv[1:6]
def block(text, start):
    i = text.index(start); j = text.index("{", i); d = 0
    while True:
        c = text[j]
        if c == "{": d += 1
        elif c == "}":
            d -= 1
            if d == 0: return text[i:j+1]
        j += 1
o = open(old).read(); n = open(new).read()
isotext = open(iso).read().replace("package com.sachinbaria.easyvoice\n", "")
with open(out, "w") as f:
    f.write(isotext + "\n")
    f.write("class OldX(val prefs: P, val modeInt: Int) {\n" + block(o, "private fun languageForDetectedRun(") + "\nfun lang(r: DetectedRun, a: String, b: String) = prefs.toIso3(languageForDetectedRun(r, a, b))\n}\n")
    f.write("class NewX(val prefs: P, val modeInt: Int) {\n" + block(n, "private fun detectedLangOf(") + "\n" + block(n, "private fun languageForDetectedRun(") +
            "\nfun lang(r: DetectedRun, a: String, b: String, m: HashMap<String, String>?) = detectedLangOf(r, a, b, m)\n}\n")
    f.write(open(driver).read())
