package com.sachinbaria.easyvoice

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.datatransport.Encoding
import com.google.android.datatransport.Event
import com.google.android.datatransport.Transformer
import com.google.android.datatransport.cct.CCTDestination
import com.google.android.datatransport.runtime.TransportRuntime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference

// NOTHING LEAVES THE PHONE (owner, 2026-10-28: "internet ki jarurat Nahin Hai
// ... Har Jagah se internet Hata dena"). ML Kit's language identification is a
// model inside the APK; the network came only with Google's datatransport, the
// library ML Kit uploads its usage logs with. The manifest removes both network
// permissions and the upload path (AndroidManifest.xml says why each part is
// safe, from the library's bytecode); this proves it on a device:
//   1. the installed app holds neither permission and declares none of
//      datatransport's components;
//   2. ML Kit still names Hindi and English, with no network at all;
//   3. an event sent the way ML Kit sends its logs (TransportRuntime,
//      CCTDestination) is refused before it is stored -- the scheduler answers
//      "Transport backend 'cct' is not registered", the branch that returns
//      before eventStore.persist. With the backend still registered the callback
//      would get null instead, so this fails if a future dependency brings the
//      upload back.
@RunWith(AndroidJUnit4::class)
class OfflineTest {
    private val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private fun packageInfo(flags: Int): PackageInfo =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ctx.packageManager.getPackageInfo(ctx.packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
        else @Suppress("DEPRECATION") ctx.packageManager.getPackageInfo(ctx.packageName, flags)

    @Test
    fun noNetworkPermissionAndNoUploader() {
        val info = packageInfo(PackageManager.GET_PERMISSIONS or PackageManager.GET_SERVICES or
            PackageManager.GET_RECEIVERS)
        val requested = info.requestedPermissions.orEmpty().toSet()
        for (permission in listOf("android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE")) {
            assertFalse("$permission is requested", permission in requested)
            assertEquals(permission, PackageManager.PERMISSION_DENIED,
                ctx.packageManager.checkPermission(permission, ctx.packageName))
        }
        val components = info.services.orEmpty().map { it.name } + info.receivers.orEmpty().map { it.name }
        assertTrue("datatransport components: $components",
            components.none { it.startsWith("com.google.android.datatransport.") })
    }

    @Test
    fun mlKitIdentifiesOffline() {
        assertEquals("hi", topTag(EasyVoiceTtsService.mlkitIdentify("आज मौसम बहुत अच्छा है और हम बाहर घूमने जा रहे हैं।")))
        assertEquals("en", topTag(EasyVoiceTtsService.mlkitIdentify("The weather is very nice today and we are going out.")))
    }

    @Test
    fun mlKitLogIsDroppedBeforeItIsStored() {
        TransportRuntime.initialize(ctx)
        val transport = TransportRuntime.getInstance().newFactory(CCTDestination.INSTANCE)
            .getTransport("EV_OFFLINE_TEST", ByteArray::class.java, Encoding.of("proto"), Transformer<ByteArray, ByteArray> { it })
        val done = CountDownLatch(1)
        val answer = AtomicReference<Exception?>()
        transport.schedule(Event.ofData(byteArrayOf(1, 2, 3))) { e -> answer.set(e); done.countDown() }
        assertTrue("the scheduler never answered", done.await(30, TimeUnit.SECONDS))
        val refused = answer.get()
        assertNotNull("the event was accepted for upload", refused)
        assertTrue("unexpected answer: $refused", refused?.message.orEmpty().contains("not registered"))
    }

    // mlkitIdentify answers "tag millionths" lines; the language with the most.
    private fun topTag(lines: String): String? =
        lines.lines().mapNotNull { line ->
            val parts = line.trim().split(' ')
            if (parts.size == 2) parts[1].toIntOrNull()?.let { parts[0] to it } else null
        }.maxByOrNull { it.second }?.first
}
