/*
 * Decompiled with CFR 0.152.
 */
package androidx.activity;

import androidx.activity.b;
import androidx.activity.c;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import n3.a;
import o3.k;

public abstract class o {
    public boolean a;
    public final CopyOnWriteArrayList b;
    public a c;

    public o(boolean bl) {
        this.a = bl;
        this.b = new CopyOnWriteArrayList();
    }

    public final void a(c c3) {
        k.e(c3, "cancellable");
        this.b.add(c3);
    }

    public final a b() {
        return this.c;
    }

    public void c() {
    }

    public abstract void d();

    public void e(b b3) {
        k.e(b3, "backEvent");
    }

    public void f(b b3) {
        k.e(b3, "backEvent");
    }

    public final boolean g() {
        return this.a;
    }

    public final void h() {
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            ((c)iterator.next()).cancel();
        }
    }

    public final void i(c c3) {
        k.e(c3, "cancellable");
        this.b.remove(c3);
    }

    public final void j(boolean bl) {
        this.a = bl;
        a a4 = this.c;
        if (a4 != null) {
            a4.a();
        }
    }

    public final void k(a a4) {
        this.c = a4;
    }
}

