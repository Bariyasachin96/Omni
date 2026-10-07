package com.sachinbaria.easyvoice
import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
object EasyVoiceLogger {
    const val TAG = "EasyVoice"
    private const val MAX_LOG_BYTES = 2L * 1024L * 1024L
    // java.time (owner, 2026-10-07: the current API, not the old one): an
    // immutable, thread-safe DateTimeFormatter instead of SimpleDateFormat,
    // which is neither. Below API 26 the app's core-library desugaring supplies
    // java.time. Same pattern, same text (tools/verify/logger compares files).
    private val timestampFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    private var logFile: File? = null
    @PublishedApi @Volatile internal var loggingOn = false
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
        loggingOn = enabled
        if (!enabled) closeWriter()
    }
    fun isLoggingEnabled(): Boolean = loggingOn
    // @Synchronized, and it is the same monitor writeLine takes. `logFile` is a
    // plain field written here on the MAIN thread and read by writeLine on the
    // synthesis and binder threads, so without a common lock there is no
    // happens-before edge and a reader could see null for ever -- the log file
    // silently never written, on exactly the device whose owner is trying to
    // report a bug. It costs one uncontended lock, once per process.
    //
    // `appContext` was assigned beside logFile and read by nothing; gone.
    //
    // GUARDED 2026-09-11. This is the FIRST line of the TTS service's onCreate,
    // before super.onCreate(), so a throw out of it leaves the service dead and
    // the phone with no voice at all -- and it does real filesystem work:
    // getFilesDir() can fail on a device whose storage is not ready (direct boot,
    // an encrypted profile, a full or read-only data partition) and mkdirs() can
    // throw SecurityException. Logging is a diagnostic; it must never be the
    // reason the app cannot speak.
    @Synchronized fun init(ctx: Context) {
        try {
            val dir = File(ctx.applicationContext.filesDir, "logs")
            if (!dir.exists()) dir.mkdirs()
            logFile = File(dir, "easy_voice.log")
        } catch (ex: Throwable) {
            logFile = null
            Log.e("TtsLogger", "Failed to open the log directory", ex)
        }
    }
    // EVERY LINE USED TO ASK THE FILESYSTEM TWICE (owner, 2026-10-01: "extra
    // process"). rotate() ran before each line and called exists() + length(),
    // two stat calls, although the size is already counted here -- and the
    // count was in CHARACTERS, so for Hindi or Gujarati it was too small and
    // only the stat could see the file pass the cap. It counts BYTES now,
    // exactly as UTF-8 writes them, so rotate() is asked only when the writer
    // is opened or the count says the cap is reached: the same moments the old
    // stat answered yes. The line is joined with + instead of String.format,
    // which built a Formatter per line for the same four %s. The file is
    // byte-identical (tools/verify/logger, old vs new logger, random lines
    // with Hindi, emoji and lone surrogates, rotation and clear()).
    @PublishedApi @Synchronized internal fun writeLine(level: String, tag: String, msg: String) {
        when (level) { "E" -> Log.e(tag, msg); "W" -> Log.w(tag, msg) }
        if (!loggingOn) return
        val file = logFile ?: return
        if (writer != null && writtenBytes >= MAX_LOG_BYTES) closeWriter()
        val line = try {
            if (writer == null) rotate(file)
            timestampFormat.format(LocalDateTime.now()) + " [" + level + "] " + tag + ": " + msg
        } catch (ex: Throwable) {
            Log.e("TtsLogger", "Failed to prepare a log line", ex)
            closeWriter()
            return
        }
        try {
            var out = writer
            if (out == null) {
                out = java.io.BufferedWriter(FileWriter(file, true))
                writer = out
                writtenBytes = if (file.exists()) file.length() else 0L
            }
            out.write(line)
            out.newLine()
            out.flush()
            writtenBytes += utf8Length(line) + LINE_SEPARATOR_BYTES
        } catch (ex: Throwable) {
            Log.e("TtsLogger", "Failed to write log", ex)
            try { closeWriter() } catch (_: Throwable) {}
        }
    }
    private val LINE_SEPARATOR_BYTES = utf8Length(System.lineSeparator())
    // Bytes UTF-8 writes for `text`: a surrogate pair is four, a surrogate with
    // no partner is the one '?' the encoder puts in its place.
    private fun utf8Length(text: String): Long {
        var bytes = 0L
        var index = 0
        while (index < text.length) {
            val ch = text[index]
            if (Character.isHighSurrogate(ch) && index + 1 < text.length && Character.isLowSurrogate(text[index + 1])) {
                bytes += 4; index += 2; continue
            }
            bytes += when {
                ch.code < 0x80 -> 1
                ch.code < 0x800 -> 2
                Character.isSurrogate(ch) -> 1
                else -> 3
            }
            index++
        }
        return bytes
    }
    private fun rotate(file: File) {
        if (!file.exists() || file.length() < MAX_LOG_BYTES) return
        closeWriter()
        val parent = file.parent ?: return
        File(parent, "easy_voice.log.3").let { if (it.exists()) it.delete() }
        for (idx in 2 downTo 1) { val src = File(parent, "easy_voice.log.$idx"); if (src.exists()) src.renameTo(File(parent, "easy_voice.log.${idx + 1}")) }
        file.renameTo(File(parent, "easy_voice.log.1"))
    }
    // THE MESSAGE IS BUILT ONLY WHEN IT WILL BE WRITTEN (owner, 2026-10-01).
    // debug(tag, "x " + y) built its string at every call site -- about 150 of
    // them on the speaking path -- and only then found logging switched off.
    // Inline with a lambda, the text is not even joined when logging is off and
    // no lambda object is allocated either. Every message expression was
    // checked when the call sites moved: none has a side effect (the one call
    // inside, LangStore.engineFor in the "engine: " line, only logs).
    inline fun debug(tag: String, msg: () -> String) { if (loggingOn) writeLine("D", tag, msg()) }
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
