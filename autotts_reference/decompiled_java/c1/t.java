/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.c;
import c1.d;
import c1.p;
import c1.y;
import e3.k;
import e3.l;
import java.util.List;
import o.e;
import o3.g;

public final class t {
    public final long a;
    public final long b;
    public final long c;
    public final c d;
    public final long e;
    public final long f;
    public final float g;
    public final float h;
    public final float i;
    public final float j;
    public final float k;
    public long l;

    public t(long l3, long l4, long l5, c c3) {
        float f3;
        this.a = l3;
        this.b = l4;
        this.c = l5;
        this.d = c3;
        this.e = l3 = p.e(p.j(l3, l4));
        this.f = l4 = p.e(p.j(l5, l4));
        float f4 = c3 != null ? c3.a() : 0.0f;
        this.g = f4;
        float f5 = c3 != null ? c3.b() : 0.0f;
        this.h = f5;
        this.i = f5 = p.d(l3, l4);
        float f6 = 1.0f;
        this.j = f3 = (float)Math.sqrt(f6 - y.n(f5));
        f4 = (double)f3 > 0.001 ? f4 * (f5 + f6) / f3 : 0.0f;
        this.k = f4;
        this.l = o.e.b(0.0f, 0.0f);
    }

    public /* synthetic */ t(long l3, long l4, long l5, c c3, g g3) {
        this(l3, l4, l5, c3);
    }

    public final float a(float f3) {
        if (f3 > this.e()) {
            return this.h;
        }
        float f4 = this.k;
        if (f3 > f4) {
            return this.h * (f3 - f4) / (this.e() - this.k);
        }
        return 0.0f;
    }

    public final d b(float f3, float f4, long l3, long l4, long l5, long l6, long l7, float f5) {
        long l8 = p.e(p.j(l4, l3));
        l3 = p.k(l3, p.l(p.l(l8, f3), 1.0f + f4));
        l6 = p.b(p.k(l5, l6), 2.0f);
        l6 = p.i(l5, l6, f4);
        e e3 = this.g(l4, l8, l6 = p.k(l7, p.l(y.c(p.g(l6) - p.g(l7), p.h(l6) - p.h(l7)), f5)), y.m(p.j(l6, l7)));
        if (e3 != null) {
            l5 = e3.g();
        }
        return new d(l3, p.b(p.k(l3, p.l(l5, 2.0f)), 3.0f), l5, l6, null);
    }

    public final long c() {
        return this.l;
    }

    public final List d(float f3, float f4) {
        long l3;
        float f5 = Math.min(f3, f4);
        float f6 = this.k;
        if (!(f6 < 1.0E-4f || f5 < 1.0E-4f || this.g < 1.0E-4f)) {
            f5 = Math.min(f5, f6);
            f3 = this.a(f3);
            float f7 = this.a(f4);
            f6 = this.g * f5 / this.k;
            f4 = (float)Math.sqrt(y.n(f6) + y.n(f5));
            this.l = p.k(this.b, p.l(p.e(p.b(p.k(this.e, this.f), 2.0f)), f4));
            long l4 = p.k(this.b, p.l(this.e, f5));
            long l5 = p.k(this.b, p.l(this.f, f5));
            d d3 = this.b(f5, f3, this.b, this.a, l4, l5, this.l, f6);
            d d4 = this.b(f5, f7, this.b, this.c, l5, l4, this.l, f6).l();
            return e3.l.h(d3, c1.d.b.a(p.g(this.l), p.h(this.l), d3.d(), d3.e(), d4.b(), d4.c()), d4);
        }
        this.l = l3 = this.b;
        return e3.k.d(c1.d.b.b(p.g(l3), p.h(this.b), p.g(this.b), p.h(this.b)));
    }

    public final float e() {
        return (1.0f + this.h) * this.k;
    }

    public final float f() {
        return this.k;
    }

    public final e g(long l3, long l4, long l5, long l6) {
        float f3 = p.d(l4, l6 = y.m(l6));
        if (Math.abs(f3) < 1.0E-4f) {
            return null;
        }
        float f4 = p.d(p.j(l5, l3), l6);
        if (Math.abs(f3) < Math.abs(f4) * 1.0E-4f) {
            return null;
        }
        return o.e.a(p.k(l3, p.l(l4, f4 / f3)));
    }
}

