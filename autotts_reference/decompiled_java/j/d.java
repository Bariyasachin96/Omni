/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Handler$Callback
 *  android.os.Looper
 */
package j;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import j.e;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class d
extends e {
    public final Object a = new Object();
    public final ExecutorService b = Executors.newFixedThreadPool(4, new ThreadFactory(this){
        public final AtomicInteger a;
        public final d b;
        {
            this.b = d3;
            this.a = new AtomicInteger(0);
        }

        @Override
        public Thread newThread(Runnable object) {
            Thread thread = new Thread((Runnable)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("arch_disk_io_");
            ((StringBuilder)object).append(this.a.getAndIncrement());
            thread.setName(((StringBuilder)object).toString());
            return thread;
        }
    });
    public volatile Handler c;

    public static Handler d(Looper looper) {
        if (Build.VERSION.SDK_INT >= 28) {
            return j.d$b.a(looper);
        }
        try {
            Handler handler = (Handler)Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(looper, null, Boolean.TRUE);
            return handler;
        }
        catch (InvocationTargetException invocationTargetException) {
            return new Handler(looper);
        }
        catch (IllegalAccessException | InstantiationException | NoSuchMethodException reflectiveOperationException) {
            return new Handler(looper);
        }
    }

    @Override
    public void a(Runnable runnable) {
        this.b.execute(runnable);
    }

    @Override
    public boolean b() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void c(Runnable runnable) {
        block6: {
            if (this.c == null) {
                Object object = this.a;
                synchronized (object) {
                    Throwable throwable2;
                    block5: {
                        block4: {
                            try {
                                if (this.c != null) break block4;
                                this.c = d.d(Looper.getMainLooper());
                            }
                            catch (Throwable throwable2) {
                                break block5;
                            }
                        }
                        break block6;
                    }
                    throw throwable2;
                }
            }
        }
        this.c.post(runnable);
    }

    public static abstract class b {
        public static Handler a(Looper looper) {
            return Handler.createAsync((Looper)looper);
        }
    }
}

