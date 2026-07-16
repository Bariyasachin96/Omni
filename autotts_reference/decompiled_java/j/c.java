/*
 * Decompiled with CFR 0.152.
 */
package j;

import j.a;
import j.b;
import j.d;
import j.e;
import java.util.concurrent.Executor;

public class c
extends e {
    public static volatile c c;
    public static final Executor d;
    public static final Executor e;
    public e a;
    public final e b;

    static {
        d = new a();
        e = new b();
    }

    public c() {
        d d3 = new d();
        this.b = d3;
        this.a = d3;
    }

    public static /* synthetic */ void d(Runnable runnable) {
        j.c.f().c(runnable);
    }

    public static /* synthetic */ void e(Runnable runnable) {
        j.c.f().a(runnable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static c f() {
        if (c != null) {
            return c;
        }
        synchronized (c.class) {
            Throwable throwable2;
            block5: {
                block4: {
                    try {
                        c c3;
                        if (c != null) break block4;
                        c = c3 = new c();
                    }
                    catch (Throwable throwable2) {
                        break block5;
                    }
                }
                return c;
            }
            throw throwable2;
        }
    }

    @Override
    public void a(Runnable runnable) {
        this.a.a(runnable);
    }

    @Override
    public boolean b() {
        return this.a.b();
    }

    @Override
    public void c(Runnable runnable) {
        this.a.c(runnable);
    }
}

