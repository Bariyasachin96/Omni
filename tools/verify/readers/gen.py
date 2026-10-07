# Builds Check.kt: the answer readers of the LAST COMMIT (recordsOf,
# detectedRunsOf, runsFromFlat, detectLanguageRuns) and of the WORKING TREE
# (forEachRecord, forEachDetectedRun, forEachRun, fieldInt), lifted verbatim
# out of each version's EasyVoiceTtsService.kt by oldnew/gen.py's lift().
import sys, os, importlib.util
here = os.path.dirname(os.path.abspath(__file__))
old_src, new_src, out, driver = sys.argv[1:5]
src = open(os.path.join(here, "..", "oldnew", "gen.py")).read()
ns = {"re": __import__("re"), "sys": sys}
exec(src[src.index("def lift("):src.index("STUBS =")], ns)
lift = ns["lift"]
STUBS = '''
    var quickCharacterFlag = false
    var fold: Array<String> = arrayOf("")
    class DetectedRun(val lang: String, val latin: Boolean, val text: String)
    object EasyVoiceLogger { const val TAG = "t"; fun error(tag: String, m: String) {} }
    fun nativeFoldAndDetect(text: String, q: Boolean): Array<String> = fold
'''
OLD = ["recordsOf", "detectLanguageRuns", "runsFromFlat", "detectedRunsOf", "foldAndDetect", "isLatinCommonInherited"]
NEW = ["forEachRecord", "fieldInt", "forEachRun", "forEachDetectedRun", "foldAndDetect", "isLatinCommonInherited"]
def obj(name, path, fns):
    return "object %s {\n%s\n%s\n}\n" % (name, STUBS, "\n".join("    " + lift(path, f).strip() for f in fns))
with open(out, "w", encoding="utf-8") as f:
    f.write("@file:Suppress(\"UNUSED_PARAMETER\", \"NOTHING_TO_INLINE\")\n")
    f.write(obj("Old", old_src, OLD)); f.write(obj("New", new_src, NEW))
    ut = os.path.join(os.path.dirname(new_src), "UnicodeTables.kt")
    f.write(open(ut, encoding="utf-8").read().split("\n", 1)[1].replace("internal object", "object") + "\n")
    f.write(open(driver, encoding="utf-8").read())
