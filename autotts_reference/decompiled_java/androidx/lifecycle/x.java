/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package androidx.lifecycle;

import android.os.Handler;
import androidx.lifecycle.f;
import androidx.lifecycle.k;
import androidx.lifecycle.l;

public class x {
    public final l a;
    public final Handler b;
    public a c;

    public x(k k3) {
        o3.k.e(k3, "provider");
        this.a = new l(k3);
        this.b = new Handler();
    }

    public f a() {
        return this.a;
    }

    public void b() {
        this.f(f.a.ON_START);
    }

    public void c() {
        this.f(f.a.ON_CREATE);
    }

    public void d() {
        this.f(f.a.ON_STOP);
        this.f(f.a.ON_DESTROY);
    }

    public void e() {
        this.f(f.a.ON_START);
    }

    public final void f(f.a a4) {
        a a5 = this.c;
        if (a5 != null) {
            a5.run();
        }
        this.c = a5 = new a(this.a, a4);
        a4 = this.b;
        o3.k.b(a5);
        a4.postAtFrontOfQueue(a5);
    }

    public static final class a
    implements Runnable {
        public final l c;
        public final f.a d;
        public boolean e;

        public a(l l3, f.a a4) {
            o3.k.e(l3, "registry");
            o3.k.e((Object)a4, "event");
            this.c = l3;
            this.d = a4;
        }

        @Override
        public void run() {
            if (!this.e) {
                this.c.h(this.d);
                this.e = true;
            }
        }
    }
}

