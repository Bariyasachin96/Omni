/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.a;
import c1.d;
import c1.l;
import c1.p;
import c1.y;
import o3.k;

public final class b
implements l {
    public final float a;
    public final float b;

    public b(float f3, float f4) {
        this.a = f3;
        this.b = f4;
    }

    public static /* synthetic */ float c(d d3, b b3, float f3, float f4, float f5) {
        return c1.b.d(d3, b3, f3, f4, f5);
    }

    public static final float d(d d3, b b3, float f3, float f4, float f5) {
        k.e(d3, "$c");
        k.e(b3, "this$0");
        long l3 = d3.k(f5);
        return Math.abs(y.j(y.a(p.g(l3) - b3.a, p.h(l3) - b3.b) - f3, y.h()) - f4);
    }

    @Override
    public float a(d d3) {
        float f3;
        k.e(d3, "c");
        float f4 = f3 = y.j(y.a(d3.d() - this.a, d3.e() - this.b) - y.a(d3.b() - this.a, d3.c() - this.b), y.h());
        if (f3 > y.h() - 1.0E-4f) {
            f4 = 0.0f;
        }
        return f4;
    }

    @Override
    public float b(d d3, float f3) {
        k.e(d3, "c");
        return y.f(0.0f, 1.0f, 1.0E-5f, new a(d3, this, y.a(d3.b() - this.a, d3.c() - this.b), f3));
    }
}

