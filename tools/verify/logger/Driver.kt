// Old logger vs new logger: the same random operations, then every log file
// compared byte for byte. See run.sh.
import java.io.File

object FakeClock {
    var t = 1_700_000_000_000L
    fun now(): java.util.Date { t += 7; return java.util.Date(t) }
    // The same tick for a java.time logger (2026-10-07).
    fun nowLocal(): java.time.LocalDateTime { t += 7; return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(t), java.time.ZoneId.systemDefault()) }
}

fun main(args: Array<String>) {
    val root = File(args[0])
    val ops = args[1].toInt()
    val pool = listOf(
        "onLoadLanguage: hin IND", "speak 1: नमस्ते आप कैसे हैं", "કેમ છો મજામાં",
        "emoji 😀👍🏽 🇮🇳 family 👨‍👩‍👧", "lone high \uD800 end", "lone low \uDC00 x",
        "trailing high \uD83D", "", "a".repeat(250), "मराठी ".repeat(40), "tab\tnl\nmid")
    val rnd = java.util.Random(args[2].toLong())
    val plan = ArrayList<Int>()
    repeat(ops) { plan.add(rnd.nextInt(1000)) }
    fun run(dir: File, which: String) {
        dir.deleteRecursively(); dir.mkdirs()
        FakeClock.t = 1_700_000_000_000L
        val ctx = android.content.Context(dir)
        val r = java.util.Random(args[2].toLong() + 1)
        if (which == "old") { old.EasyVoiceLogger.init(ctx); old.EasyVoiceLogger.setLoggingEnabled(true) }
        else { new.EasyVoiceLogger.init(ctx); new.EasyVoiceLogger.setLoggingEnabled(true) }
        for (p in plan) {
            val msg = pool[r.nextInt(pool.size)] + " " + p
            when {
                p < 3 -> if (which == "old") old.EasyVoiceLogger.clear() else new.EasyVoiceLogger.clear()
                p < 8 -> { val on = p % 2 == 0; if (which == "old") old.EasyVoiceLogger.setLoggingEnabled(on) else new.EasyVoiceLogger.setLoggingEnabled(on) }
                p < 500 -> if (which == "old") old.EasyVoiceLogger.debug("EasyVoice", msg) else new.EasyVoiceLogger.debug("EasyVoice") { msg }
                else -> if (which == "old") old.EasyVoiceLogger.error("EasyVoice", msg) else new.EasyVoiceLogger.error("EasyVoice", msg)
            }
        }
        if (which == "old") old.EasyVoiceLogger.setLoggingEnabled(false) else new.EasyVoiceLogger.setLoggingEnabled(false)
    }
    val a = File(root, "old"); val b = File(root, "new")
    run(a, "old"); run(b, "new")
    var bad = 0; var bytes = 0L; var files = 0
    for (name in listOf("easy_voice.log", "easy_voice.log.1", "easy_voice.log.2", "easy_voice.log.3")) {
        val fa = File(a, "logs/$name"); val fb = File(b, "logs/$name")
        if (fa.exists() != fb.exists()) { println("DIFF: $name exists in only one"); bad++; continue }
        if (!fa.exists()) continue
        files++; bytes += fa.length()
        if (!fa.readBytes().contentEquals(fb.readBytes())) { println("DIFF: $name (${fa.length()} vs ${fb.length()} bytes)"); bad++ }
    }
    // #2: with logging off the message must not even be built.
    var built = 0
    new.EasyVoiceLogger.setLoggingEnabled(false)
    new.EasyVoiceLogger.debug("EasyVoice") { built++; "x" }
    new.EasyVoiceLogger.setLoggingEnabled(true)
    new.EasyVoiceLogger.debug("EasyVoice") { built++; "x" }
    new.EasyVoiceLogger.setLoggingEnabled(false)
    if (built != 1) { println("DIFF: message built $built times, want 1 (only while logging is on)"); bad++ }
    println("$ops operations, $files files, $bytes bytes: " + (if (bad == 0) "IDENTICAL" else "$bad DIFFER"))
    kotlin.system.exitProcess(if (bad == 0) 0 else 1)
}
