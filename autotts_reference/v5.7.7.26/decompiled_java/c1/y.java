/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.i;
import c1.p;
import o.e;
import o3.k;

public abstract class y {
    public static final long a = e.b(0.0f, 0.0f);
    public static final float b = (float)Math.PI;
    public static final float c = (float)Math.PI * 2;

    public static final float a(float f3, float f4) {
        f4 = (float)Math.atan2(f4, f3);
        f3 = c;
        return (f4 + f3) % f3;
    }

    public static final long b(float f3) {
        double d3 = f3;
        return e.b((float)Math.cos(d3), (float)Math.sin(d3));
    }

    public static final long c(float f3, float f4) {
        float f5 = y.d(f3, f4);
        if (f5 > 0.0f) {
            return e.b(f3 / f5, f4 / f5);
        }
        throw new IllegalArgumentException("Required distance greater than zero");
    }

    public static final float d(float f3, float f4) {
        return (float)Math.sqrt(f3 * f3 + f4 * f4);
    }

    public static final float e(float f3, float f4) {
        return f3 * f3 + f4 * f4;
    }

    public static final float f(float f3, float f4, float f5, i i3) {
        k.e(i3, "f");
        while (f4 - f3 > f5) {
            float f6 = 2;
            float f7 = 3;
            float f8 = (f6 * f3 + f4) / f7;
            f7 = (f6 * f4 + f3) / f7;
            if (i3.a(f8) < i3.a(f7)) {
                f4 = f7;
                continue;
            }
            f3 = f8;
        }
        return (f3 + f4) / (float)2;
    }

    public static final float g() {
        return b;
    }

    public static final float h() {
        return c;
    }

    public static final float i(float f3, float f4, float f5) {
        return (1.0f - f5) * f3 + f5 * f4;
    }

    public static final float j(float f3, float f4) {
        return (f3 % f4 + f4) % f4;
    }

    public static final long k(float f3, float f4, long l3) {
        return p.k(p.l(y.b(f4), f3), l3);
    }

    public static /* synthetic */ long l(float f3, float f4, long l3, int n3, Object object) {
        if ((n3 & 4) != 0) {
            l3 = a;
        }
        return y.k(f3, f4, l3);
    }

    public static final long m(long l3) {
        return e.b(-p.h(l3), p.g(l3));
    }

    public static final float n(float f3) {
        return f3 * f3;
    }
}

