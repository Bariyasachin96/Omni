/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.view.Choreographer
 *  android.view.Choreographer$FrameCallback
 */
package androidx.profileinstaller;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import androidx.profileinstaller.c;
import h1.g;
import h1.h;
import h1.i;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProfileInstallerInitializer
implements k1.b {
    public static /* synthetic */ void c(Context context) {
        c.h(context);
    }

    public static /* synthetic */ void d(Context context) {
        ProfileInstallerInitializer.h(context);
    }

    public static /* synthetic */ void e(ProfileInstallerInitializer profileInstallerInitializer, Context context, long l3) {
        profileInstallerInitializer.g(context);
    }

    public static void h(Context context) {
        new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()).execute(new i(context));
    }

    @Override
    public List a() {
        return Collections.EMPTY_LIST;
    }

    public b f(Context context) {
        context = context.getApplicationContext();
        Choreographer.getInstance().postFrameCallback((Choreographer.FrameCallback)new g(this, context));
        return new b();
    }

    public void g(Context context) {
        Handler handler = Build.VERSION.SDK_INT >= 28 ? a.a(Looper.getMainLooper()) : new Handler(Looper.getMainLooper());
        int n3 = new Random().nextInt(Math.max(1000, 1));
        handler.postDelayed((Runnable)new h(context), (long)(n3 + 5000));
    }

    public static abstract class a {
        public static Handler a(Looper looper) {
            return androidx.emoji2.text.b.a(looper);
        }
    }

    public static class b {
    }
}

