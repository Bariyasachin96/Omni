/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.e;
import c1.o;
import c1.p;
import c1.q;
import c1.y;
import d3.h;
import java.util.Arrays;
import o3.g;
import o3.k;

public class d {
    public static final a b = new a(null);
    public final float[] a;

    public d(long l3, long l4, long l5, long l6) {
        this(new float[]{p.g(l3), p.h(l3), p.g(l4), p.h(l4), p.g(l5), p.h(l5), p.g(l6), p.h(l6)});
    }

    public /* synthetic */ d(long l3, long l4, long l5, long l6, g g3) {
        this(l3, l4, l5, l6);
    }

    public d(float[] fArray) {
        k.e(fArray, "points");
        this.a = fArray;
        if (fArray.length == 8) {
            return;
        }
        throw new IllegalArgumentException("Points array size should be 8");
    }

    public /* synthetic */ d(float[] fArray, int n3, g g3) {
        if ((n3 & 1) != 0) {
            fArray = new float[8];
        }
        this(fArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(float[] fArray, boolean bl) {
        float f3;
        float f4;
        float f5;
        float f6;
        block26: {
            float f7;
            float f8;
            block25: {
                float f9;
                block27: {
                    double d3;
                    float f10;
                    float f11;
                    float f12;
                    float f13;
                    block23: {
                        block24: {
                            block22: {
                                block20: {
                                    block21: {
                                        k.e(fArray, "bounds");
                                        if (this.p()) {
                                            fArray[0] = this.b();
                                            fArray[1] = this.c();
                                            fArray[2] = this.b();
                                            fArray[3] = this.c();
                                            return;
                                        }
                                        f6 = Math.min(this.b(), this.d());
                                        f8 = Math.min(this.c(), this.e());
                                        f7 = Math.max(this.b(), this.d());
                                        f13 = Math.max(this.c(), this.e());
                                        if (bl) {
                                            fArray[0] = Math.min(f6, Math.min(this.f(), this.h()));
                                            fArray[1] = Math.min(f8, Math.min(this.g(), this.i()));
                                            fArray[2] = Math.max(f7, Math.max(this.f(), this.h()));
                                            fArray[3] = Math.max(f13, Math.max(this.g(), this.i()));
                                            return;
                                        }
                                        f9 = -this.b();
                                        f12 = 3;
                                        f9 = f9 + this.f() * f12 - this.h() * f12 + this.d();
                                        f11 = 2;
                                        f5 = this.b();
                                        f10 = 4;
                                        f5 = f5 * f11 - this.f() * f10 + this.h() * f11;
                                        f4 = -this.b() + this.f();
                                        if (!this.o(f9)) break block20;
                                        if (f5 != 0.0f) break block21;
                                        f4 = f6;
                                        f3 = f7;
                                        break block22;
                                    }
                                    f9 = f4 * f11 / ((float)-2 * f5);
                                    f4 = f6;
                                    f3 = f7;
                                    if (!(0.0f <= f9)) break block22;
                                    f4 = f6;
                                    f3 = f7;
                                    if (f9 <= 1.0f) {
                                        f5 = p.g(this.k(f9));
                                        f9 = f6;
                                        if (f5 < f6) {
                                            f9 = f5;
                                        }
                                        f4 = f9;
                                        f3 = f7;
                                        if (f5 > f7) {
                                            f3 = f5;
                                            f4 = f9;
                                        }
                                    }
                                    break block22;
                                }
                                float f14 = f5 * f5 - f10 * f9 * f4;
                                f4 = f6;
                                f3 = f7;
                                if (f14 >= 0.0f) {
                                    float f15 = -f5;
                                    d3 = f14;
                                    f5 = (float)Math.sqrt(d3);
                                    f14 = f9 * f11;
                                    f4 = (f5 + f15) / f14;
                                    f9 = f6;
                                    f5 = f7;
                                    if (0.0f <= f4) {
                                        f9 = f6;
                                        f5 = f7;
                                        if (f4 <= 1.0f) {
                                            f3 = p.g(this.k(f4));
                                            f4 = f6;
                                            if (f3 < f6) {
                                                f4 = f3;
                                            }
                                            f9 = f4;
                                            f5 = f7;
                                            if (f3 > f7) {
                                                f5 = f3;
                                                f9 = f4;
                                            }
                                        }
                                    }
                                    f6 = (f15 - (float)Math.sqrt(d3)) / f14;
                                    f4 = f9;
                                    f3 = f5;
                                    if (0.0f <= f6) {
                                        f4 = f9;
                                        f3 = f5;
                                        if (f6 <= 1.0f) {
                                            f7 = p.g(this.k(f6));
                                            f6 = f9;
                                            if (f7 < f9) {
                                                f6 = f7;
                                            }
                                            f4 = f6;
                                            f3 = f5;
                                            if (f7 > f5) {
                                                f3 = f7;
                                                f4 = f6;
                                            }
                                        }
                                    }
                                }
                            }
                            f6 = f8;
                            f9 = -this.c() + this.g() * f12 - f12 * this.i() + this.e();
                            f5 = this.c() * f11 - this.g() * f10 + this.i() * f11;
                            f7 = -this.c() + this.g();
                            if (!this.o(f9)) break block23;
                            if (f5 != 0.0f) break block24;
                            f7 = f13;
                            f8 = f6;
                            break block25;
                        }
                        f9 = f11 * f7 / ((float)-2 * f5);
                        f7 = f13;
                        f8 = f6;
                        if (!(0.0f <= f9)) break block25;
                        f7 = f13;
                        f8 = f6;
                        if (!(f9 <= 1.0f)) break block25;
                        f7 = p.h(this.k(f9));
                        f9 = f7 < f6 ? f7 : f6;
                        f5 = f9;
                        f6 = f13;
                        if (!(f7 > f13)) break block26;
                        f6 = f7;
                        break block27;
                    }
                    f12 = f5 * f5 - f10 * f9 * f7;
                    f7 = f13;
                    f8 = f6;
                    if (!(f12 >= 0.0f)) break block25;
                    f10 = -f5;
                    d3 = f12;
                    f5 = (float)Math.sqrt(d3);
                    f7 = (f5 + f10) / (f11 *= f9);
                    f9 = f13;
                    f5 = f6;
                    if (0.0f <= f7) {
                        f9 = f13;
                        f5 = f6;
                        if (f7 <= 1.0f) {
                            f8 = p.h(this.k(f7));
                            f7 = f6;
                            if (f8 < f6) {
                                f7 = f8;
                            }
                            f9 = f13;
                            f5 = f7;
                            if (f8 > f13) {
                                f9 = f8;
                                f5 = f7;
                            }
                        }
                    }
                    f6 = (f10 - (float)Math.sqrt(d3)) / f11;
                    f7 = f9;
                    f8 = f5;
                    if (!(0.0f <= f6)) break block25;
                    f7 = f9;
                    f8 = f5;
                    if (!(f6 <= 1.0f)) break block25;
                    f7 = p.h(this.k(f6));
                    f13 = f7 < f5 ? f7 : f5;
                    f5 = f13;
                    f6 = f9;
                    if (!(f7 > f9)) break block26;
                    f6 = f7;
                    f9 = f13;
                }
                f5 = f9;
                break block26;
            }
            f5 = f8;
            f6 = f7;
        }
        fArray[0] = f4;
        fArray[1] = f5;
        fArray[2] = f3;
        fArray[3] = f6;
    }

    public final float b() {
        return this.a[0];
    }

    public final float c() {
        return this.a[1];
    }

    public final float d() {
        return this.a[6];
    }

    public final float e() {
        return this.a[7];
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof d)) {
            return false;
        }
        return Arrays.equals(this.a, ((d)object).a);
    }

    public final float f() {
        return this.a[2];
    }

    public final float g() {
        return this.a[3];
    }

    public final float h() {
        return this.a[4];
    }

    public int hashCode() {
        return Arrays.hashCode(this.a);
    }

    public final float i() {
        return this.a[5];
    }

    public final float[] j() {
        return this.a;
    }

    public final long k(float f3) {
        float f4 = 1.0f - f3;
        float f5 = this.b();
        float f6 = f4 * f4 * f4;
        float f7 = this.f();
        float f8 = (float)3 * f3;
        float f9 = f8 * f4 * f4;
        float f10 = this.h();
        f4 = f8 * f3 * f4;
        f8 = this.d();
        f3 = f3 * f3 * f3;
        return o.e.b(f5 * f6 + f7 * f9 + f10 * f4 + f8 * f3, this.c() * f6 + this.g() * f9 + this.i() * f4 + this.e() * f3);
    }

    public final d l() {
        return e.a(this.d(), this.e(), this.h(), this.i(), this.f(), this.g(), this.b(), this.c());
    }

    public final d3.d m(float f3) {
        float f4 = 1.0f - f3;
        long l3 = this.k(f3);
        float f5 = this.b();
        float f6 = this.c();
        float f7 = this.b();
        float f8 = this.f();
        float f9 = this.c();
        float f10 = this.g();
        float f11 = this.b();
        float f12 = f4 * f4;
        float f13 = this.f();
        float f14 = (float)2 * f4 * f3;
        float f15 = this.h();
        float f16 = f3 * f3;
        return h.a(e.a(f5, f6, f7 * f4 + f8 * f3, f9 * f4 + f10 * f3, f11 * f12 + f13 * f14 + f15 * f16, this.c() * f12 + this.g() * f14 + this.i() * f16, p.g(l3), p.h(l3)), e.a(p.g(l3), p.h(l3), this.f() * f12 + this.h() * f14 + this.d() * f16, this.g() * f12 + this.i() * f14 + this.e() * f16, this.h() * f4 + this.d() * f3, this.i() * f4 + this.e() * f3, this.d(), this.e()));
    }

    public final d n(q q3) {
        k.e(q3, "f");
        o o3 = new o();
        e3.h.h(this.a, o3.j(), 0, 0, 0, 14, null);
        o3.q(q3);
        return o3;
    }

    public final boolean o(float f3) {
        return Math.abs(f3) < 1.0E-4f;
    }

    public final boolean p() {
        return Math.abs(this.b() - this.d()) < 1.0E-4f && Math.abs(this.c() - this.e()) < 1.0E-4f;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("anchor0: (");
        stringBuilder.append(this.b());
        stringBuilder.append(", ");
        stringBuilder.append(this.c());
        stringBuilder.append(") control0: (");
        stringBuilder.append(this.f());
        stringBuilder.append(", ");
        stringBuilder.append(this.g());
        stringBuilder.append("), control1: (");
        stringBuilder.append(this.h());
        stringBuilder.append(", ");
        stringBuilder.append(this.i());
        stringBuilder.append("), anchor1: (");
        stringBuilder.append(this.d());
        stringBuilder.append(", ");
        stringBuilder.append(this.e());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final d a(float f3, float f4, float f5, float f6, float f7, float f8) {
            float f9 = f5 - f3;
            float f10 = f6 - f4;
            long l3 = y.c(f9, f10);
            f3 = f7 - f3;
            f4 = f8 - f4;
            long l4 = y.c(f3, f4);
            long l5 = y.m(l3);
            long l6 = y.m(l4);
            boolean bl = p.c(l5, f3, f4) >= 0.0f;
            f3 = p.d(l3, l4);
            if (f3 > 0.999f) {
                return this.b(f5, f6, f7, f8);
            }
            f10 = y.d(f9, f10) * 4.0f / 3.0f;
            f4 = 2;
            float f11 = 1.0f;
            f9 = f11 - f3;
            f4 = f10 * ((float)Math.sqrt(f4 * f9) - (float)Math.sqrt(f11 - f3 * f3)) / f9;
            f3 = bl ? 1.0f : -1.0f;
            f3 = f4 * f3;
            return e.a(f5, f6, f5 + p.g(l5) * f3, f6 + p.h(l5) * f3, f7 - p.g(l6) * f3, f8 - p.h(l6) * f3, f7, f8);
        }

        public final d b(float f3, float f4, float f5, float f6) {
            return e.a(f3, f4, y.i(f3, f5, 0.33333334f), y.i(f4, f6, 0.33333334f), y.i(f3, f5, 0.6666667f), y.i(f4, f6, 0.6666667f), f5, f6);
        }
    }
}

