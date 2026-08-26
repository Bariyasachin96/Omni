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
    fun setLoggingEnabled(enabled: Boolean) { loggingEnabled = enabled }
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
        rotate(file)
        val line = String.format("%s [%s] %s: %s", timestampFormat.format(Date()), level, tag, msg)
        try {
            java.io.BufferedWriter(FileWriter(file, true)).use { writer -> writer.write(line); writer.newLine() }
        } catch (ex: IOException) { Log.e("TtsLogger", "Failed to write log", ex) }
    }
    private fun rotate(file: File) {
        if (!file.exists() || file.length() < MAX_LOG_BYTES) return
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
        if (!file.exists()) return
        try { FileWriter(file, false).close() } catch (ex: IOException) { Log.e("TtsLogger", "Failed to clear log", ex) }
    }
}
