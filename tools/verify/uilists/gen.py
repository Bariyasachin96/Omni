# Lifts the Voice setup row sort and the Languages search filter out of the
# old commit and the working tree, and wraps each in a function with the same
# locals, so the driver can feed both the same inputs.
import re, sys
old_vr, new_vr, old_la, new_la, driver, out = sys.argv[1:7]
def block(text, start, end_marker):
    i = text.index(start)
    j = text.index(end_marker, i)
    return text[i:j]
def balanced(text, start):
    i = text.index(start); j = text.index("{", i); d = 0
    while True:
        if text[j] == "{": d += 1
        elif text[j] == "}":
            d -= 1
            if d == 0: return text[i:j+1]
        j += 1
abbr = balanced(open(new_vr).read(), "fun abbreviateEngineName(")
o = open(old_vr).read(); n = open(new_vr).read()
old_sort = block(o, "fun tieBreakLabel(", "val stored = try")
new_sort = block(n, "fun tieBreakLabel(", "val stored = try")
lo = open(old_la).read(); ln = open(new_la).read()
old_filter = block(lo, "val visibleIdx = labels.indices.filter", "ResponsiveContent {")
# Since 2026-10-08 the tree lowers with Compose's LocalLocale (lint
# NonObservableLocale) instead of Locale.getDefault(); off a composition the
# harness's stand-in for it is the JVM default locale the old code read.
new_filter = block(ln, "val locale = LocalLocale.current.platformLocale", "ResponsiveContent {")
new_filter = new_filter.replace("LocalLocale.current.platformLocale", "Locale.getDefault()")
with open(out, "w") as f:
    f.write("import java.util.Locale\n" + abbr + "\n")
    f.write("fun <T> remember(k: Any?, calc: () -> T): T = calc()\n")
    f.write("fun <T> remember(k1: Any?, k2: Any?, calc: () -> T): T = calc()\n")
    for tag, srt, flt in (("Old", old_sort, old_filter), ("New", new_sort, new_filter)):
        f.write("fun sort%s(rows: List<ScanVoice?>, selectedIso: String, voiceOrder: (String) -> Int): List<ScanVoice?> {\n%s\nreturn sorted\n}\n" % (tag, srt))
        f.write("fun filter%s(labels: List<String>, query: String, showSelectedOnly: Boolean, checked: List<Boolean>): List<Int> {\n%s\nreturn visibleIdx\n}\n" % (tag, flt))
    f.write(open(driver).read())
