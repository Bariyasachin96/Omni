/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 */
package androidx.emoji2.text;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.emoji2.text.b;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class c {
    public static /* synthetic */ Thread a(String object, Runnable runnable) {
        object = new Thread(runnable, (String)object);
        ((Thread)object).setPriority(10);
        return object;
    }

    public static ThreadPoolExecutor b(String object) {
        object = new androidx.emoji2.text.a((String)object);
        object = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), (ThreadFactory)object);
        ((ThreadPoolExecutor)object).allowCoreThreadTimeOut(true);
        return object;
    }

    public static Handler c() {
        if (Build.VERSION.SDK_INT >= 28) {
            return a.a(Looper.getMainLooper());
        }
        return new Handler(Looper.getMainLooper());
    }

    public static abstract class a {
        public static Handler a(Looper looper) {
            return b.a(looper);
        }
    }
}

