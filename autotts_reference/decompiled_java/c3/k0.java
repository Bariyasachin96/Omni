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
import c3.h0;
import c3.i0;
import c3.j0;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class k0 {
    public final String a;
    public TextToSpeech b;
    public int c;
    public Locale d = null;
    public String e = "";
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    public ExecutorService i;
    public long j;
    public int k = 0;

    public k0(String string) {
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

    public static /* synthetic */ void b(k0 k02) {
        k02.getClass();
        try {
            k02.b.stop();
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Stop failed for ");
            stringBuilder.append(k02.a);
            Log.w((String)"AutoTTS", (String)stringBuilder.toString(), (Throwable)exception);
            return;
        }
    }

    public static /* synthetic */ void c(k0 k02) {
        k02.getClass();
        try {
            k02.b.shutdown();
            return;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Shutdown failed for ");
            stringBuilder.append(k02.a);
            Log.w((String)"AutoTTS", (String)stringBuilder.toString(), (Throwable)exception);
            return;
        }
    }

    public final void d() {
        this.i = new ThreadPoolExecutor(0, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new h0());
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

    public boolean h() {
        if (this.k == 0) {
            return true;
        }
        return (double)(System.nanoTime() - this.j) / 1000000.0 > 3000.0 && this.k < 10;
    }

    public void i() {
        this.j = System.nanoTime();
        ++this.k;
    }

    public void j(int n3) {
        this.c = n3;
    }

    public void k(TextToSpeech textToSpeech) {
        this.b = textToSpeech;
    }

    public void l() {
        this.i.execute(new j0(this));
    }

    public void m() {
        this.i.execute(new i0(this));
    }
}

