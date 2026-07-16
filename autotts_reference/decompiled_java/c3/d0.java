/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.speech.tts.TextToSpeech
 *  android.util.Log
 */
package c3;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import c3.a0;
import c3.b0;
import c3.c0;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class d0 {
    public String a;
    public TextToSpeech b;
    public int c;
    public Locale d = null;
    public String e = "";
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    public ExecutorService i;

    public d0(String string) {
        this.a = string.replace("-", "").replace("_", "");
        this.b = null;
        this.c = 0;
        this.d();
    }

    public static /* synthetic */ Thread a(Runnable runnable) {
        runnable = new Thread(runnable, "TtsStop");
        ((Thread)runnable).setDaemon(true);
        return runnable;
    }

    public static /* synthetic */ void b(d0 d02) {
        d02.getClass();
        try {
            d02.b.stop();
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Stop failed for ");
            stringBuilder.append(d02.a);
            Log.w((String)"AutoTTS", (String)stringBuilder.toString(), (Throwable)exception);
            return;
        }
    }

    public static /* synthetic */ void c(d0 d02) {
        d02.getClass();
        try {
            d02.b.shutdown();
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Shutdown failed for ");
            stringBuilder.append(d02.a);
            Log.w((String)"AutoTTS", (String)stringBuilder.toString(), (Throwable)exception);
            return;
        }
    }

    public final void d() {
        this.i = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new a0());
    }

    public String e() {
        return this.a;
    }

    public int f() {
        return this.c;
    }

    public TextToSpeech g() {
        return this.b;
    }

    public void h(int n3) {
        this.c = n3;
    }

    public void i(TextToSpeech textToSpeech) {
        this.b = textToSpeech;
    }

    public void j() {
        this.i.execute(new c0(this));
    }

    public void k() {
        this.i.execute(new b0(this));
    }
}

