
// ---- the driver (hand-written; the two objects above are lifted verbatim) ----
fun unE(s: String): String {
    val o = StringBuilder(); var i = 0
    while (i < s.length) {
        if (s[i] == '\\' && i + 5 < s.length && s[i + 1] == 'u') { o.append(s.substring(i + 2, i + 6).toInt(16).toChar()); i += 6 }
        else { o.append(s[i]); i++ }
    }
    return o.toString()
}
fun fields(s: String): List<String> = s.split('\t').map { unE(it) }
fun oldSpeak(packed: String, quick: Boolean): List<String> {
    val res = ArrayList<String>()
    for (chunkStr in packed.split('\u001E').filter { it.isNotBlank() }) {
        val parts = chunkStr.split('\u001F')
        if (parts.size < 4) continue
        val t = parts[0].toIntOrNull() ?: 0
        if (t == 3 || t == 4 || t == 5) res.add("S|${parts[2]}|${Old.decodeChunkText(parts[3])}")
        else for (r in Old.detectedRunsOf(parts, quick)) res.add("R|${r.lang}|${r.latin}|${r.text}")
    }
    return res
}
fun oldDual(packed: String): List<String> {
    val res = ArrayList<String>()
    for (chunkStr in packed.split('\u001E').filter { it.isNotBlank() }) {
        val parts = chunkStr.split('\u001F', limit = 4)
        if (parts.size == 4) res.add("D|${parts[0]}|${parts[2]}|${Old.decodeChunkText(parts[3])}")
    }
    return res
}
fun newSpeak(out: Array<String>, quick: Boolean): List<String> {
    val res = ArrayList<String>()
    New.forEachRecord(out) { at, n ->
        if (n < 4) return@forEachRecord
        val t = New.fieldInt(out[at]) ?: 0
        if (t == 3 || t == 4 || t == 5) res.add("S|${out[at + 2]}|${out[at + 3]}")
        else New.forEachDetectedRun(out, at, n, quick) { lang, latin, text -> res.add("R|$lang|$latin|$text") }
    }
    return res
}
fun newDual(out: Array<String>): List<String> {
    val res = ArrayList<String>()
    New.forEachRecord(out) { at, n -> if (n == 4) res.add("D|${out[at]}|${out[at + 2]}|${out[at + 3]}") }
    return res
}
fun main(args: Array<String>) {
    val old = java.io.File(args[0]).readLines()
    val new = java.io.File(args[1]).readLines()
    if (old.size != new.size) { println("line count ${old.size} vs ${new.size}"); kotlin.system.exitProcess(1) }
    var text = ""; var cases = 0; var spoken = 0; var bad = 0; var unknowns = 0
    for (i in old.indices) {
        val o = old[i]; val n = new[i]
        if (o.startsWith("T\t")) { if (o != n) { println("text line differs at $i"); bad++ }; text = unE(o.substring(2)); continue }
        val oh = o.split('\t', limit = 4); val nh = n.split('\t', limit = 4)
        val kind = oh[0]; val quick = oh[2] == "1"
        if (kind != nh[0] || oh[1] != nh[1]) { println("case header differs at $i"); bad++; continue }
        cases++
        var why: String? = null
        if (kind == "P" || kind == "Q") {
            val packed = unE(oh[3])
            val arr = if (nh[3].isEmpty()) arrayOf() else fields(nh[3]).toTypedArray()
            Old.quickCharacterFlag = quick; New.quickCharacterFlag = quick
            val oS = if (kind == "P") oldSpeak(packed, quick) else oldDual(packed)
            val nS = if (kind == "P") newSpeak(arr, quick) else newDual(arr)
            spoken += nS.size
            if (oS != nS) why = "spoken chunks differ:\n   old $oS\n   new $nS"
        } else {
            val oParts = o.split('\t').drop(3); val nParts = n.split('\t').drop(3)
            val oSep = oParts.indexOf("\\X"); val nSep = nParts.indexOf("\\X")
            val oFull = unE(oParts[0]); val oFold = oParts.drop(oSep + 1).map { unE(it) }.toTypedArray()
            val nWin = nParts.take(nSep).map { unE(it) }.toTypedArray(); val nFold = nParts.drop(nSep + 1).map { unE(it) }.toTypedArray()
            Old.quickCharacterFlag = quick; New.quickCharacterFlag = quick
            Old.full = oFull; Old.fold = oFold; New.window = nWin; New.fold = nFold
            val ol = ArrayList<String>(); val nl = ArrayList<String>()
            Old.EasyVoiceLogger.sink = ol; New.EasyVoiceLogger.sink = nl
            // the deleted auto/Google lines, verbatim
            var rawDetected = Old.detectLanguage(text, "eng", "hin")
            if (rawDetected.equals("unknown", ignoreCase = true)) {
                unknowns++
                rawDetected = Old.detectLanguageAggregate(text)
            }
            val newDetected = New.detectWindowThenAggregate(text, "eng", "hin")
            if (rawDetected != newDetected) why = "auto answer differs: old '$rawDetected' new '$newDetected'"
            else if (ol != nl) why = "auto log differs:\n   old $ol\n   new $nl"
        }
        if (why != null) { bad++; if (bad <= 10) println("DIFF case ${oh[1]} quick=$quick text '$text': $why") }
    }
    println("\n$cases cases, $spoken spoken chunks, $unknowns auto answers that went to the aggregate: $bad differ")
    kotlin.system.exitProcess(if (bad > 0) 1 else 0)
}
