/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Process
 */
package l0;

import android.os.Handler;
import android.os.Process;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class h {
    public static ThreadPoolExecutor a(String object, int n3, int n4) {
        object = new a((String)object, n3);
        object = new ThreadPoolExecutor(0, 1, (long)n4, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), (ThreadFactory)object);
        ((ThreadPoolExecutor)object).allowCoreThreadTimeOut(true);
        return object;
    }

    public static void b(Executor executor, Callable callable, n0.a a4) {
        executor.execute(new b(l0.b.a(), callable, a4));
    }

    public static Object c(ExecutorService object, Callable callable, int n3) {
        object = object.submit(callable);
        long l3 = n3;
        try {
            object = object.get(l3, TimeUnit.MILLISECONDS);
            return object;
        }
        catch (TimeoutException timeoutException) {
            throw new InterruptedException("timeout");
        }
        catch (InterruptedException interruptedException) {
            throw interruptedException;
        }
        catch (ExecutionException executionException) {
            throw new RuntimeException(executionException);
        }
    }

    public static class l0.h$a
    implements ThreadFactory {
        public String a;
        public int b;

        public cfr_renamed_7(String string, int n3) {
            this.a = string;
            this.b = n3;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new a(runnable, this.a, this.b);
        }

        public static class a
        extends Thread {
            public final int c;

            public a(Runnable runnable, String string, int n3) {
                super(runnable, string);
                this.c = n3;
            }

            @Override
            public void run() {
                Process.setThreadPriority((int)this.c);
                super.run();
            }
        }
    }

    public static class b
    implements Runnable {
        public Callable c;
        public n0.a d;
        public Handler e;

        public b(Handler handler, Callable callable, n0.a a4) {
            this.c = callable;
            this.d = a4;
            this.e = handler;
        }

        @Override
        public void run() {
            Object v3;
            try {
                v3 = this.c.call();
            }
            catch (Exception exception) {
                v3 = null;
            }
            n0.a a4 = this.d;
            this.e.post(new Runnable(this, a4, v3){
                public final n0.a c;
                public final Object d;
                public final b e;
                {
                    this.e = b3;
                    this.c = a4;
                    this.d = object;
                }

                @Override
                public void run() {
                    this.c.accept(this.d);
                }
            });
        }
    }
}

