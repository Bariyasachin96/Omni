package com.sachinbaria.easyvoice

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

// THE MOVE TO DATASTORE KEEPS EVERY SETTING (owner, 2026-10-07). The app's store
// migrates the old easy_voice_settings.xml with the library's own
// SharedPreferencesMigration on its first read. This runs that same migration on
// the device, over a throwaway file holding every key shape the app writes --
// language list, a language's engine#locale, its sliders and variant, the mode
// int, a flag, the backup list -- and checks each comes back with its value AND
// its type, and that the old file is gone afterwards. The app's own file is not
// touched.
@RunWith(AndroidJUnit4::class)
class SettingsMigrationTest {
    @Test
    fun everyKeyShapeSurvivesTheMigration() {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val name = "ev_migration_check"
        ctx.preferencesDataStoreFile(name).delete()
        @Suppress("ApplySharedPref")
        ctx.getSharedPreferences(name, Context.MODE_PRIVATE).edit()
            .putString("language_0", "hin").putString("language_1", "")
            .putString("hin", "com.google.android.tts#hi_IN")
            .putInt("hin_speed", 130).putInt("hin_pitch", 90).putInt("hin_volume", 100)
            .putString("hin_variant", "*Default")
            .putInt("auto_mode", 4).putBoolean("smart_number_reading", true)
            .putString("backup_languages", "eng,hin")
            .putString("com.google.android.tts#hi_IN", "0")
            .commit()
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        try {
            val store = PreferenceDataStoreFactory.create(
                migrations = listOf(SharedPreferencesMigration(ctx, name)),
                scope = scope,
                produceFile = { ctx.preferencesDataStoreFile(name) },
            )
            val prefs = runBlocking { store.data.first() }
            assertEquals("hin", prefs[stringPreferencesKey("language_0")])
            assertEquals("", prefs[stringPreferencesKey("language_1")])
            assertEquals("com.google.android.tts#hi_IN", prefs[stringPreferencesKey("hin")])
            assertEquals(130, prefs[intPreferencesKey("hin_speed")])
            assertEquals(90, prefs[intPreferencesKey("hin_pitch")])
            assertEquals(100, prefs[intPreferencesKey("hin_volume")])
            assertEquals("*Default", prefs[stringPreferencesKey("hin_variant")])
            assertEquals(4, prefs[intPreferencesKey("auto_mode")])
            assertEquals(true, prefs[booleanPreferencesKey("smart_number_reading")])
            assertEquals("eng,hin", prefs[stringPreferencesKey("backup_languages")])
            assertEquals("0", prefs[stringPreferencesKey("com.google.android.tts#hi_IN")])
            assertEquals(11, prefs.asMap().size)
            // The library deletes the migrated file, so the next start reads
            // DataStore alone.
            assertFalse(java.io.File(ctx.dataDir, "shared_prefs/$name.xml").exists())
        } finally {
            scope.cancel()
            ctx.preferencesDataStoreFile(name).delete()
        }
    }

    // THE MIGRATION IS HANDED OVER ONLY WHILE THE OLD FILE EXISTS (2026-10-08),
    // and "exists" is LangStore.sharedPrefsFile: the path the DataStore library
    // itself reproduces for SharedPreferences. This proves on the device that it
    // is where the platform really writes one -- if it were not, the migration
    // would never run for a phone updating from a SharedPreferences build.
    @Test
    fun legacyFileIsWhereThePlatformWritesIt() {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val name = "ev_path_check"
        @Suppress("ApplySharedPref")
        ctx.getSharedPreferences(name, Context.MODE_PRIVATE).edit().putInt("x", 1).commit()
        assertTrue(LangStore.sharedPrefsFile(ctx, name).exists())
        ctx.deleteSharedPreferences(name)
        assertFalse(LangStore.sharedPrefsFile(ctx, name).exists())
        // And reading the app's own settings leaves no old file behind.
        LangStore.prefs(ctx)
        assertFalse(LangStore.legacyFileExists(ctx))
    }
}
