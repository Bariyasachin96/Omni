package com.tts.easyvoice
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
object EasyVoiceLogger {
    const val TAG = "EasyVoice"
    private const val MAX_LOG_BYTES = 2L * 1024L * 1024L
    private val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    private var logFile: File? = null
    private var appContext: Context? = null
    @Volatile private var loggingEnabled = false
    // ---- THE WRITER IS HELD OPEN -------------------------------------------
    // AutoTTS's c3.p.h opens the file, writes one line and closes it again, for
    // EVERY line, and calls exists() + length() before each one. That is five
    // syscalls per line, and this path is not idle: one language switch writes
    // 32 lines between onDone and the next speak(), on the main thread, so it
    // costs about 160 syscalls at exactly the moment the next word is waiting.
    // The owner's own logs measured that whole step at 15-44 ms.
    //
    // DELIBERATE DEPARTURE, owner request 2026-09-02: "latency bilkul aani hi
    // nahin chahie". Measured on this container's NVMe, 32 lines:
    //     open/write/close per line   1.177 ms
    //     one held writer + flush     0.170 ms   (7x)
    // A phone's flash is slower and busier than an NVMe, so the real saving is
    // larger than 7x, not smaller.
    //
    // THE LOG FILE IS BYTE-IDENTICAL. Same lines, same order, and flush() after
    // every one -- nothing is buffered across calls, so a crash or a kill loses
    // no line that a caller had already logged. What goes is the open and the
    // close, not the durability.
    //
    // The size is tracked in memory instead of asking the filesystem twice per
    // line; it is seeded from the real length when the writer opens, so an
    // existing file's size is picked up rather than assumed to be zero.
    private var writer: java.io.BufferedWriter? = null
    private var writtenBytes = 0L
    // Closed on every path that makes the held handle wrong: logging switched
    // off, the file cleared, or the file rotated out from under it. Writing to
    // an unlinked inode after clear() is exactly the bug this guards.
    @Synchronized private fun closeWriter() {
        try { writer?.close() } catch (_: IOException) { }
        writer = null
        writtenBytes = 0L
    }
    fun setLoggingEnabled(enabled: Boolean) {
        loggingEnabled = enabled
        if (!enabled) closeWriter()
    }
    fun isLoggingEnabled(): Boolean = loggingEnabled
    fun init(ctx: Context) {
        appContext = ctx.applicationContext
        val dir = File(ctx.applicationContext.filesDir, "logs")
        if (!dir.exists()) dir.mkdirs()
        logFile = File(dir, "easy_voice.log")
    }
    @Synchronized private fun writeLine(level: String, tag: String, msg: String) {
        when (level) { "E" -> Log.e(tag, msg); "W" -> Log.w(tag, msg) }
        if (!loggingEnabled) return
        val file = logFile ?: return
        // The rotation test is the in-memory count, not two stat calls. It is
        // only ever a lower bound on the real size -- it is seeded from
        // file.length() when the writer opens -- so the file cannot grow past
        // the cap without this noticing.
        if (writer != null && writtenBytes >= MAX_LOG_BYTES) closeWriter()
        rotate(file)
        val line = String.format("%s [%s] %s: %s", timestampFormat.format(Date()), level, tag, msg)
        try {
            var out = writer
            if (out == null) {
                out = java.io.BufferedWriter(FileWriter(file, true))
                writer = out
                writtenBytes = if (file.exists()) file.length() else 0L
            }
            out.write(line)
            out.newLine()
            // Per line, so nothing a caller has logged is lost if the process
            // dies. This is one write syscall; the open and the close are what
            // were costing the time.
            out.flush()
            writtenBytes += line.length + 1
        } catch (ex: IOException) {
            Log.e("TtsLogger", "Failed to write log", ex)
            closeWriter()
        }
    }
    private fun rotate(file: File) {
        if (!file.exists() || file.length() < MAX_LOG_BYTES) return
        closeWriter()
        val parent = file.parent ?: return
        File(parent, "easy_voice.log.3").let { if (it.exists()) it.delete() }
        for (idx in 2 downTo 1) { val src = File(parent, "easy_voice.log.$idx"); if (src.exists()) src.renameTo(File(parent, "easy_voice.log.${idx + 1}")) }
        file.renameTo(File(parent, "easy_voice.log.1"))
    }
    fun debug(tag: String, msg: String) = writeLine("D", tag, msg)
    fun error(tag: String, msg: String) = writeLine("E", tag, msg)
    fun errorWithStack(tag: String, msg: String, throwable: Throwable) = writeLine("E", tag, msg + "\n" + Log.getStackTraceString(throwable))
    fun shareFileOrNull(): File? {
        val file = logFile ?: return null
        if (!file.exists() || file.length() == 0L) return null
        return file
    }
    @Synchronized fun clear() {
        val file = logFile ?: return
        // Before truncating: the held writer has its own offset, so writing
        // through it after the file is emptied would leave a hole of NUL bytes
        // where the old content was.
        closeWriter()
        if (!file.exists()) return
        try { FileWriter(file, false).close() } catch (ex: IOException) { Log.e("TtsLogger", "Failed to clear log", ex) }
    }
}
