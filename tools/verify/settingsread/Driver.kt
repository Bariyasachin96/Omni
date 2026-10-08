import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.IOException

// The Android side of the lifted code, as small as it can be: a Context that
// only carries the store, and the logger.
class Context(val store: DataStore<Preferences>)
object EasyVoiceLogger {
    const val TAG = "EasyVoice"
    val errors = ArrayList<String>()
    fun error(tag: String, msg: String) { synchronized(errors) { errors.add(msg) } }
}
// A store whose data flow throws `failures` times before it answers -- what the
// DataStore contract says a failed read looks like ("IOExceptions are thrown
// when an error occurs while reading data").
class FakeStore(var failures: Int, val failure: () -> Throwable, val value: Preferences) : DataStore<Preferences> {
    @Volatile var reads = 0
    @Volatile var updates = 0
    override val data: Flow<Preferences> = flow { reads++; if (failures > 0) { failures--; throw failure() }; emit(value) }
    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences { updates++; return transform(value) }
}
val KEY = stringPreferencesKey("auto_mode_language")
var failed = 0
fun check(name: String, ok: Boolean) { println((if (ok) "  ok    " else "  FAIL  ") + name); if (!ok) failed++ }

fun main(args: Array<String>) {
    val dir = File(args[0]); dir.mkdirs()
    val stored = preferencesOf(KEY to "hin")

    // 1. A healthy file on the real library: the value comes back, nothing is
    //    flagged, and a write lands.
    run {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        val file = File(dir, "healthy.preferences_pb")
        val store = PreferenceDataStoreFactory.create(scope = scope, produceFile = { file })
        runBlocking { store.edit { it[KEY] = "hin" } }
        val ls = LS(); val ctx = Context(store)
        check("healthy file: value read", ls.prefs(ctx)[KEY] == "hin")
        check("healthy file: not flagged", !ls.readFailed)
        ls.write(ctx) { it[KEY] = "guj" }
        check("healthy file: write lands", runBlocking { store.data.first() }[KEY] == "guj")
        scope.cancel()
    }
    // 2. One passing I/O failure: the retry reads the real value.
    run {
        val store = FakeStore(1, { IOException("transient") }, stored)
        val ls = LS()
        check("one I/O failure: retried, value read", ls.prefs(Context(store))[KEY] == "hin" && store.reads == 2)
        check("one I/O failure: not flagged", !ls.readFailed)
    }
    // 3. A lasting I/O failure: defaults, flagged, and NO write reaches the
    //    store -- the defaults never go over the real file.
    run {
        val store = FakeStore(99, { IOException("disk") }, stored)
        val ls = LS(); val ctx = Context(store)
        val got = runCatching { ls.prefs(ctx) }
        check("lasting I/O failure: defaults, no throw", got.getOrNull() == emptyPreferences())
        check("lasting I/O failure: flagged", ls.readFailed)
        ls.write(ctx) { it[KEY] = "eng" }
        ls.writeLater(ctx) { it[KEY] = "eng" }
        Thread.sleep(300)
        check("lasting I/O failure: no write reached the store", store.updates == 0)
    }
    // 4. Not an I/O failure: thrown, as the DataStore guide says, and not flagged.
    run {
        val store = FakeStore(1, { IllegalStateException("bug") }, stored)
        val ls = LS()
        val got = runCatching { ls.prefs(Context(store)) }
        check("other exception: thrown, not swallowed", got.exceptionOrNull() is IllegalStateException)
        check("other exception: not flagged", !ls.readFailed)
    }
    // 5. The real library over a path it cannot read (a directory where the file
    //    should be): defaults, flagged, write refused, the path untouched.
    run {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        val file = File(dir, "blocked.preferences_pb"); file.mkdirs()
        val store = PreferenceDataStoreFactory.create(scope = scope, produceFile = { file })
        val ls = LS(); val ctx = Context(store)
        val got = runCatching { ls.prefs(ctx) }
        check("unreadable file: defaults, no throw", got.getOrNull() == emptyPreferences())
        check("unreadable file: flagged", ls.readFailed)
        ls.write(ctx) { it[KEY] = "eng" }
        check("unreadable file: left as it was", file.isDirectory)
        scope.cancel()
    }
    println(if (failed == 0) "ALL PASS" else "$failed FAILED")
    if (failed != 0) kotlin.system.exitProcess(1)
}
