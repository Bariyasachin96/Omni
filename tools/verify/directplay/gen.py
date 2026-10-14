# Lifts the speaking code of TWO versions of EasyVoiceTtsService.kt into the
# simulator (Driver.kt): OLD -- the speak() loop of the last commit before the
# direct player (from releaseWaitWithoutSpeaking to the first speakChunk, the
# empty-text flush, onStop, onEngineProcessGone, the end helpers, EngineWrapper)
# -- and NEW -- the direct player's hand-over (Utterance .. engineParams), the
# end of onSynthesizeTextImpl that runs it, onStop, onEngineProcessGone,
# failPending, the end helpers and EngineWrapper. SpeechPlayer.kt and
# SpeechOut.kt are compiled as they are. See run.sh.
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

SUBST = [("android.os.Bundle", "Bundle"), ("android.speech.tts.Voice", "Voice"),
         ("android.util.Log.w", "FakeLog.w"), ("android.media.AudioFormat.ENCODING_PCM_16BIT", "2"),
         ("java.util.Locale", "Locale"), ("androidx.core.os.BundleCompat", "BundleCompat")]

def common(t, cls):
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
    if "stopExec: java" in members: sys.exit("stopExec not replaced in " + cls)
    return members

def lift_old(path):
    t = open(path, encoding="utf-8").read()
    members = common(t, "OldSvc")
    a = t.index("        fun releaseWaitWithoutSpeaking(why: String) {")
    b = t.index("        speakChunk(true)\n", a) + len("        speakChunk(true)\n")
    region = t[a:b]
    p = t.index("        synchronized(syncLock) { isStopped = false; isFlushed = false; syncLock.notifyAll() }")
    q = t.index("            startAndFinish(callback)\n            return\n        }\n", p) + len("            startAndFinish(callback)\n            return\n        }\n")
    rest = """        val bypassed = false
        val workStart = 0L
        val isForceAccessibility = forceAccessibilityFlag
        val isStripAudioAttr = stripAudioAttrFlag
        val chunks = plan.chunks.map { TextChunk(it.text, it.lang, typeCode = it.type) }
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
    body = t[p:q] + rest + region
    for s_, r_ in SUBST:
        body = body.replace(s_, r_); members = members.replace(s_, r_)
    return members, body

def lift_new(path):
    t = open(path, encoding="utf-8").read()
    members = common(t, "NewSvc")
    members += "\n" + fun(t, "    private fun failPending(")
    a = t.index("    // ONE UTTERANCE ON ITS WAY THROUGH THE PLAYER.\n")
    b = t.index("    // ==========================================================================\n    //  SHUTDOWN")
    members += "\n" + t[a:b]
    for name in ["ENGINE_LOST", "KEY_AUDIO_ATTRIBUTES"]:
        m = re.search(r"        const val " + name + r" = .*\n", t)
        members += m.group(0).replace("const val", "val")
    members += re.search(r"        val KEEP_ALIVE_SILENCE = .*\n", t).group(0)
    m = re.search(r"        val NOT_FOR_THE_ENGINE = arrayOf\(.*?\)\n", t, re.S)
    members += m.group(0)
    p = t.index("        synchronized(syncLock) { isStopped = false; isFlushed = false; syncLock.notifyAll() }")
    q = t.index("            startAndFinish(callback)\n            return\n        }\n", p) + len("            startAndFinish(callback)\n            return\n        }\n")
    r = t.index("        val sysRate  = requestRate\n")
    e = t.index("        startAndFinish(callback)\n    }\n\n    // ==========================================================================\n    //  THE HAND-OVER")
    tail = t[r:e + len("        startAndFinish(callback)\n")]
    rest = """        val bypassed = false
        val workStart = 0L
        val chunks = plan.chunks.map { TextChunk(it.text, it.lang, typeCode = it.type) }
        if (plan.firstLoad.isNotEmpty()) {
            val first = onLoadLanguage(plan.firstLoad, "", "")
            if (first == TextToSpeech.LANG_NOT_SUPPORTED || first == TextToSpeech.LANG_MISSING_DATA) { startAndFinish(callback); return }
        }
        val firstChunkParamLang = plan.firstParamLang
"""
    body = t[p:q] + rest + tail
    for s_, r_ in SUBST:
        body = body.replace(s_, r_); members = members.replace(s_, r_)
    return members, body

om, ob = lift_old(old_file)
nm, nb = lift_new(new_file)
before = (nm, nb)

# Negative tests: the new code broken on purpose; each must be caught.
def breaks(where, old, new):
    global nm, nb
    if where == "m":
        if old not in nm: sys.exit("EV_NEG " + NEG + ": pattern not found")
        nm = nm.replace(old, new, 1)
    else:
        if old not in nb: sys.exit("EV_NEG " + NEG + ": pattern not found")
        nb = nb.replace(old, new, 1)
if NEG == "nodrain":       # the screen reader is told "done" before the last frame is heard
    breaks("b", "speechOut.drainAndPark()", "true")
elif NEG == "nopause":     # an interrupt does not stop our track
    breaks("m", "            speechOut.interrupt()\n", "            run.at(run.now + 30.0) { speechOut.interrupt() }\n")
elif NEG == "nodrop":      # stale work is never stopped: it stays in front of the next utterance
    breaks("m", "        if (wrapper.pending.values.none { it < generation }) return\n", "        return\n")
elif NEG == "dropall":     # the stop does not spare the utterance in progress
    breaks("m", "        if (wrapper.pending.values.none { it < generation }) return\n", "        if (wrapper.pending.isEmpty()) return\n")
    breaks("m", "        dropStaleWork(wrapper, u.generation)\n        tts.setSpeechRate", "        dropStaleWork(wrapper, u.generation + 1)\n        tts.setSpeechRate")
elif NEG == "noretry":     # a failed chunk is never handed to another engine
    breaks("m", "        if (!backupFor(stream.lang) || code == TextToSpeech.ERROR_INVALID_REQUEST) return false", "        if (true) return false")
elif NEG == "volume":      # each chunk keeps the first chunk's volume
    breaks("m", "        val finalVolume = requestVolume * (voiceParams[2] / 100.0f)\n", "        val finalVolume = requestVolume * (LangStore.voiceParamsFor(u.chunks[0].lang)[2] / 100.0f)\n")
elif NEG == "nodeath":     # an engine's death does not end its streams
    breaks("m", "            failPending(dead, \"its process died\")\n", "")
elif NEG == "noload":      # a later chunk is not given its own language
    breaks("m", "        val loadLang = if (first) \"\" else loadLanguageOf(chunk)\n", "        val loadLang = \"\"\n")
elif NEG == "nocarry":     # half a frame left by a failed chunk is joined to the next one
    pass                    # applied to SpeechOut.kt by run.sh
elif NEG:
    sys.exit("unknown EV_NEG " + NEG)
if NEG and NEG != "nocarry" and (nm, nb) == before: sys.exit("EV_NEG " + NEG + " changed nothing")

src = open(driver, encoding="utf-8").read()
src = src.replace("        //OLD BODY//\n", ob).replace("    //OLD MEMBERS//\n", om + "\n")
src = src.replace("        //NEW BODY//\n", nb).replace("    //NEW MEMBERS//\n", nm + "\n")
with open(out, "w", encoding="utf-8") as f:
    f.write('@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE", "NAME_SHADOWING", "unused", "UNCHECKED_CAST", "DEPRECATION", "UNUSED_VALUE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNREACHABLE_CODE", "SENSELESS_COMPARISON")\n')
    f.write(src)
