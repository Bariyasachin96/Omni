# Lifts the REAL speaking machinery of EasyVoiceTtsService.kt -- the whole speak
# loop of onSynthesizeTextImpl (from releaseWaitWithoutSpeaking to the first
# speakChunk), the empty-text flush before it, onStop(), onEngineProcessGone,
# the end-synthesis helpers and EngineWrapper -- out of TWO versions of the
# file into two fake services, OldSvc and NewSvc, run by Driver.kt against an
# AOSP-shaped engine on one virtual clock. See run.sh.
import os, re, sys
old_file, new_file, driver, out = sys.argv[1:5]
NEG = os.environ.get("EV_NEG", "")

def block_from(text, start):
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
        elif c == "'" and text[j+2] == "'":
            j += 2
        j += 1

def fun(text, sig):
    i = text.index(sig)
    return block_from(text, i)

def lift(path, cls):
    t = open(path, encoding="utf-8").read()
    # the speak loop
    a = t.index("        fun releaseWaitWithoutSpeaking(why: String) {")
    b = t.index("        speakChunk(true)\n", a) + len("        speakChunk(true)\n")
    region = t[a:b]
    # the empty-text flush (and the per-utterance reset above it)
    p = t.index("        synchronized(syncLock) { isStopped = false; isFlushed = false; syncLock.notifyAll() }")
    # the branch ends with startAndFinish (refs before 2026-10-14) or with a
    # done() that never starts the callback (the tree since)
    ends = [e for e in ("            startAndFinish(callback)\n            return\n        }\n",
                        "            if (callback?.hasFinished() == false) callback.done()\n            return\n        }\n")
            if t.find(e, p) >= 0]
    q = min(t.index(e, p) + len(e) for e in ends)
    prelude = t[p:q]
    funs = [fun(t, "    private fun startCallback("), fun(t, "    private fun startAndFinish("),
            fun(t, "    private fun endSynthesis("), fun(t, "    private fun unlockSynthesis("),
            fun(t, "    private fun onEngineProcessGone("), fun(t, "    override fun onStop() {"),
            fun(t, "    inner class EngineWrapper(")]
    one = re.search(r"    private fun normPkg\(pkg: String\): String = .*\n", t).group(0)
    two = re.search(r"    private fun isSameEnginePkg\(one: String, other: String\): Boolean =\n.*\n", t).group(0)
    members = "\n".join(funs) + "\n" + one + two
    members = members.replace("override fun onStop() {", "fun svcOnStop() {")
    members = re.sub(r"val stopExec: java\.util\.concurrent\.ThreadPoolExecutor =.*?prestartCoreThread\(\) \}\n",
                     "val stopExec = SimExec(this@" + cls + ", pkg)\n", members, flags=re.S)
    if "stopExec: java" in members: sys.exit("stopExec not replaced in " + path)
    body = prelude + "\n        //REST//\n" + region
    for s, r in [("android.os.Bundle", "Bundle"), ("android.speech.tts.Voice", "Voice"),
                 ("android.util.Log.w", "FakeLog.w"), ("android.media.AudioFormat.ENCODING_PCM_16BIT", "2"),
                 ("java.util.Locale", "Locale")]:
        body = body.replace(s, r); members = members.replace(s, r)
    return members, body

om, ob = lift(old_file, "OldSvc")
nm, nb = lift(new_file, "NewSvc")
before = (nm, nb)
if NEG == "noflushgen":
    # the stop rule without the flushed-generation test: always skip a spent order
    nm = nm.replace("if (forGeneration >= 0 && flushedGeneration > forGeneration) {",
                    "if (forGeneration >= 0 && forGeneration != synthesisGeneration) {", 1)
elif NEG == "nocancel":
    # an error of the chunk in front leaves the queued one playing
    nb = nb.replace("""                    if (queued != null && queued.wrapper === wrapper) {
                        try { wrapper.tts?.stop() }""", """                    if (queued != null && queued.wrapper === wrapper && System.nanoTime() < 0) {
                        try { wrapper.tts?.stop() }""", 1)
    nb = nb.replace("""                        if (myGeneration != synthesisGeneration || isStopped || isFlushed) return@post
                        cancelAhead(true)""", """                        if (myGeneration != synthesisGeneration || isStopped || isFlushed) return@post""", 1)
elif NEG == "nodefer":
    # an early failure of the queued chunk handled at once, while the one in front plays
    nb = nb.replace("if (mine != null && mine.deferFailure(code, n)) return", "if (false) return", 1)
elif NEG == "nodeathback":
    # an engine death does not take the queued chunk back
    nb = nb.replace("                    cancelAhead(false)\n                    retryChunkElsewhere(rawPkg", "                    retryChunkElsewhere(rawPkg", 1)
elif NEG == "otherengine":
    # queue ahead even when the next chunk goes to another engine
    nb = nb.replace("            if (wrapper.pkg != speakingPkg) return\n", "", 1)
elif NEG == "norate":
    # the queued chunk keeps the playing chunk's rate
    nb = nb.replace("            tts.setSpeechRate(finalRate); tts.setPitch(finalPitch)\n            val expectedId = ", "            tts.setPitch(finalPitch)\n            val expectedId = ", 1)
elif NEG == "nobackup":
    # the per-language Backup TTS answer ignored: the hand-over never happens
    nb = nb.replace("if (!backupFor(lang) ||", "if (true ||", 1).replace("if (!backupFor(currentPairLang)) return false", "return false", 1)
elif NEG == "stalewins":
    # a stale prefetch is not told it is stale
    nb = nb.replace("loadLanguage(lang, \"\", \"\", true, stale)", "loadLanguage(lang, \"\", \"\", true, null)", 1)
elif NEG:
    sys.exit("unknown EV_NEG " + NEG)
if NEG and (nm, nb) == before: sys.exit("EV_NEG " + NEG + " changed nothing")
REST = """        val bypassed = false
        val workStart = 0L
        val isForceAccessibility = forceAccessibilityFlag
        val isStripAudioAttr = stripAudioAttrFlag
        val chunks = plan.chunks.map { TextChunk(it.text, it.lang, typeCode = it.type) }
        // the first chunk's language, loaded as each mode's branch does
        if (plan.firstLoad.isNotEmpty()) {
            val first = onLoadLanguage(plan.firstLoad, "", "")
            if (first == TextToSpeech.LANG_NOT_SUPPORTED || first == TextToSpeech.LANG_MISSING_DATA) { startAndFinish(callback); return }
        }
        val firstChunkParamLang = plan.firstParamLang
        val sysRate = requestRate
        val sysPitch = requestPitch
        synchronized(chunkQueue) {
            chunkQueue.clear()
            chunkQueue.addAll(chunks.mapIndexed { chunkIdx, queued -> (chunkIdx + 1) to queued })
        }
"""
src = open(driver, encoding="utf-8").read()
a = src.index("// ---- SVC TEMPLATE BEGIN ----"); b = src.index("// ---- SVC TEMPLATE END ----") + len("// ---- SVC TEMPLATE END ----")
tmpl = src[a:b]; src = src[:a] + src[b:]
def svc(name, members, body):
    return tmpl.replace("XSvc", name).replace("//MEMBERS//", members).replace("//BODY//", body.replace("        //REST//\n", REST))
src = src.replace("//SERVICES//", svc("OldSvc", om, ob) + "\n" + svc("NewSvc", nm, nb))
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused", "UNCHECKED_CAST", "DEPRECATION", "UNUSED_VALUE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNREACHABLE_CODE", "SENSELESS_COMPARISON")\n')
    f.write(src)
