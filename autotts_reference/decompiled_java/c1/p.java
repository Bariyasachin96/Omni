/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.q;
import c1.y;
import o.e;
import o3.k;

public abstract class p {
    public static final boolean a(long l3, long l4) {
        return p.g(l3) * p.h(l4) - p.h(l3) * p.g(l4) > 0.0f;
    }

    public static final long b(long l3, float f3) {
        return e.b(p.g(l3) / f3, p.h(l3) / f3);
    }

    public static final float c(long l3, float f3, float f4) {
        return p.g(l3) * f3 + p.h(l3) * f4;
    }

    public static final float d(long l3, long l4) {
        return p.g(l3) * p.g(l4) + p.h(l3) * p.h(l4);
    }

    public static final long e(long l3) {
        float f3 = p.f(l3);
        if (f3 > 0.0f) {
            return p.b(l3, f3);
        }
        throw new IllegalArgumentException("Can't get the direction of a 0-length vector");
    }

    public static final float f(long l3) {
        return (float)Math.sqrt(p.g(l3) * p.g(l3) + p.h(l3) * p.h(l3));
    }

    public static final float g(long l3) {
        return Float.intBitsToFloat((int)(l3 >> 32));
    }

    public static final float h(long l3) {
        return Float.intBitsToFloat((int)(l3 & 0xFFFFFFFFL));
    }

    public static final long i(long l3, long l4, float f3) {
        return e.b(y.i(p.g(l3), p.g(l4), f3), y.i(p.h(l3), p.h(l4), f3));
    }

    public static final long j(long l3, long l4) {
        return e.b(p.g(l3) - p.g(l4), p.h(l3) - p.h(l4));
    }

    public static final long k(long l3, long l4) {
        return e.b(p.g(l3) + p.g(l4), p.h(l3) + p.h(l4));
    }

    public static final long l(long l3, float f3) {
        return e.b(p.g(l3) * f3, p.h(l3) * f3);
    }

    public static final long m(long l3, q q3) {
        k.e(q3, "f");
        l3 = q3.a(p.g(l3), p.h(l3));
        return e.b(Float.intBitsToFloat((int)(l3 >> 32)), Float.intBitsToFloat((int)(l3 & 0xFFFFFFFFL)));
    }
}

