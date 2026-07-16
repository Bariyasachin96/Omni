/*
 * Decompiled with CFR 0.152.
 */
package r2;

import j0.a;
import java.util.ArrayList;
import java.util.List;
import n1.b;
import r2.j;
import r2.l;

public abstract class k {
    public l a;
    public final List b = new ArrayList();

    public k(int n3) {
        for (int i3 = 0; i3 < n3; ++i3) {
            this.b.add(new j.a());
        }
    }

    public abstract void a();

    public float b(int n3, int n4, int n5) {
        return j0.a.a((float)(n3 - n4) / (float)n5, 0.0f, 1.0f);
    }

    public abstract void c();

    public abstract void d(b var1);

    public void e(l l3) {
        this.a = l3;
    }

    public abstract void f();

    public abstract void g();

    public abstract void h();
}

