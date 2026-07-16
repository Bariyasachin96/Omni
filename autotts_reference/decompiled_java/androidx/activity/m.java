/*
 * Decompiled with CFR 0.152.
 */
package androidx.activity;

import androidx.activity.l;
import d3.j;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import n3.a;
import o3.k;

public final class m {
    public final Executor a;
    public final a b;
    public final Object c;
    public int d;
    public boolean e;
    public boolean f;
    public final List g;
    public final Runnable h;

    public m(Executor executor, a a4) {
        k.e(executor, "executor");
        k.e(a4, "reportFullyDrawn");
        this.a = executor;
        this.b = a4;
        this.c = new Object();
        this.g = new ArrayList();
        this.h = new l(this);
    }

    public static /* synthetic */ void a(m m3) {
        m.d(m3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final void d(m object) {
        k.e(object, "this$0");
        Object object2 = ((m)object).c;
        synchronized (object2) {
            Throwable throwable2;
            block4: {
                block3: {
                    try {
                        ((m)object).e = false;
                        if (((m)object).d != 0 || ((m)object).f) break block3;
                        ((m)object).b.a();
                        ((m)object).b();
                    }
                    catch (Throwable throwable2) {
                        break block4;
                    }
                }
                object = j.a;
                return;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void b() {
        Object object = this.c;
        synchronized (object) {
            try {
                this.f = true;
                Object object2 = this.g.iterator();
                while (true) {
                    if (!object2.hasNext()) {
                        this.g.clear();
                        object2 = j.a;
                        return;
                    }
                    ((a)object2.next()).a();
                }
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public final boolean c() {
        Object object = this.c;
        synchronized (object) {
            boolean bl = this.f;
            return bl;
        }
    }
}

