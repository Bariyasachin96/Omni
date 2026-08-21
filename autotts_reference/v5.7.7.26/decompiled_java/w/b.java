/*
 * Decompiled with CFR 0.152.
 */
package w;

import s.k;
import s.m;
import s.n;

public class b
extends x.n {
    public n a;
    public k b;
    public m c;

    public b() {
        n n3;
        this.a = n3 = new n();
        this.c = n3;
    }

    @Override
    public float a() {
        return this.c.b();
    }

    public void b(float f3, float f4, float f5, float f6, float f7, float f8) {
        n n3 = this.a;
        this.c = n3;
        n3.d(f3, f4, f5, f6, f7, f8);
    }

    public boolean c() {
        return this.c.a();
    }

    public void d(float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n3) {
        if (this.b == null) {
            this.b = new k();
        }
        k k3 = this.b;
        this.c = k3;
        k3.d(f3, f4, f5, f6, f7, f8, f9, n3);
    }

    public float getInterpolation(float f3) {
        return this.c.getInterpolation(f3);
    }
}

