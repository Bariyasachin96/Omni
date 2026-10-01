# Lifts the REAL background-scan scheduling of EasyVoiceTtsService.kt -- from
# TWO versions of the file -- into OldSvc / NewSvc, driven by Driver.kt. See run.sh.
import re, sys
old_file, new_file, driver, out = sys.argv[1:5]
def block_from(t, start):
    j = t.index("{", start); depth = 0
    while True:
        c = t[j]
        if c == "{": depth += 1
        elif c == "}":
            depth -= 1
            if depth == 0: return t[start:j+1]
        elif c == '"':
            j += 1
            while t[j] != '"':
                if t[j] == "\\": j += 1
                j += 1
        j += 1
def lift(path):
    t = open(path, encoding="utf-8").read()
    out = []
    for sig in ["    private fun startupScan()", "    private fun scanInBackground(", "    private fun requestScan(",
                "    private fun tryOwedScan(", "    private fun onScanFinished()", "    private val screensClosedObserver ="]:
        if sig in t: out.append(block_from(t, t.index(sig)))
    body = "\n".join(out).replace("private fun", "fun").replace("private val", "val")
    body = body.replace("androidx.lifecycle.DefaultLifecycleObserver", "FakeObserver").replace("androidx.lifecycle.LifecycleOwner", "FakeOwner")
    pr = re.search(r"if \(isEngine\) (scanInBackground|requestScan)\((.*)\)\n", t)
    call = pr.group(1)
    return body, call
ob, oc = lift(old_file); nb, nc = lift(new_file)
import os
NEG = os.environ.get("EV_NEG", "")
muts = {
  "noobserver": ('tryOwedScan("a change arrived while the app was open")', ''),
  "nobump": ('EngineFinder.changeSeq.incrementAndGet()', ''),
  "nofinished": ('tryOwedScan("a change arrived during the last scan")', ''),
  "noappcheck": ('if (appOnScreen()) {', 'if (false && appOnScreen()) {'),
  "norunningcheck": ('if (EngineFinder.scanRunning) {\n', 'if (false && EngineFinder.scanRunning) {\n'),
}
if NEG:
    a, b = muts[NEG]
    if a not in nb: sys.exit("EV_NEG " + NEG + " changed nothing")
    nb = nb.replace(a, b, 1)
src = open(driver, encoding="utf-8").read()
a = src.index("// ---- SVC TEMPLATE BEGIN ----"); b = src.index("// ---- SVC TEMPLATE END ----") + len("// ---- SVC TEMPLATE END ----")
tmpl = src[a:b]; src = src[:a] + src[b:]
def pkgcall(c):
    return 'scanInBackground("package " + pkg, queueIfBusy = true, pkg = pkg)' if c == "scanInBackground" else 'requestScan("package " + pkg, pkg)'
def svc(name, body, c):
    return tmpl.replace("XSvc", name).replace("//MEMBERS//", body).replace("//PKGCALL//", pkgcall(c))
src = src.replace("//SERVICES//", svc("OldSvc", ob, oc) + "\n" + svc("NewSvc", nb, nc))
open(out, "w", encoding="utf-8").write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "unused", "NAME_SHADOWING")\n' + src)
