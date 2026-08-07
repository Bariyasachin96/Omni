/*
 * Decompiled with CFR 0.152.
 */
package f0;

import f0.b;
import f0.l;

public class a {
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final float f;
    public final float g;
    public final float h;
    public final float i;

    public a(float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11) {
        this.a = f3;
        this.b = f4;
        this.c = f5;
        this.d = f6;
        this.e = f7;
        this.f = f8;
        this.g = f9;
        this.h = f10;
        this.i = f11;
    }

    public static a b(float f3, float f4, float f5) {
        float f6 = 100.0f;
        float f7 = 1000.0f;
        float f8 = 0.0f;
        a a4 = null;
        float f9 = 1000.0f;
        while (Math.abs(f8 - f6) > 0.01f) {
            float f10 = (f6 - f8) / 2.0f + f8;
            int n3 = f0.a.e(f10, f4, f3).p();
            float f11 = f0.b.b(n3);
            float f12 = Math.abs(f5 - f11);
            float f13 = f7;
            float f14 = f9;
            a a5 = a4;
            if (f12 < 0.2f) {
                a a6 = f0.a.c(n3);
                float f15 = a6.a(f0.a.e(a6.k(), a6.i(), f3));
                f13 = f7;
                f14 = f9;
                a5 = a4;
                if (f15 <= 1.0f) {
                    a5 = a6;
                    f13 = f12;
                    f14 = f15;
                }
            }
            if (f13 == 0.0f && f14 == 0.0f) {
                return a5;
            }
            if (f11 < f5) {
                f8 = f10;
                f7 = f13;
                f9 = f14;
                a4 = a5;
                continue;
            }
            f6 = f10;
            f7 = f13;
            f9 = f14;
            a4 = a5;
        }
        return a4;
    }

    public static a c(int n3) {
        float[] fArray = new float[7];
        float[] fArray2 = new float[3];
        f0.a.d(n3, l.k, fArray, fArray2);
        return new a(fArray2[0], fArray2[1], fArray[0], fArray[1], fArray[2], fArray[3], fArray[4], fArray[5], fArray[6]);
    }

    public static void d(int n3, l l3, float[] fArray, float[] fArray2) {
        f0.b.f(n3, fArray2);
        Object object = f0.b.a;
        float f3 = fArray2[0];
        float[] fArray3 = object[0];
        float f4 = fArray3[0];
        float f5 = fArray2[1];
        float f6 = fArray3[1];
        float f7 = fArray2[2];
        float f8 = fArray3[2];
        fArray3 = object[1];
        float f9 = fArray3[0];
        float f10 = fArray3[1];
        float f11 = fArray3[2];
        object = object[2];
        Object object2 = object[0];
        Object object3 = object[1];
        Object object4 = object[2];
        f8 = l3.i()[0] * (f4 * f3 + f6 * f5 + f8 * f7);
        f10 = l3.i()[1] * (f9 * f3 + f10 * f5 + f11 * f7);
        f3 = l3.i()[2] * (f3 * object2 + f5 * object3 + f7 * object4);
        object4 = (float)Math.pow((double)(l3.c() * Math.abs(f8)) / 100.0, 0.42);
        object3 = (float)Math.pow((double)(l3.c() * Math.abs(f10)) / 100.0, 0.42);
        f5 = (float)Math.pow((double)(l3.c() * Math.abs(f3)) / 100.0, 0.42);
        f8 = Math.signum(f8) * 400.0f * object4 / (object4 + 27.13f);
        object3 = Math.signum(f10) * 400.0f * object3 / (object3 + 27.13f);
        f5 = Math.signum(f3) * 400.0f * f5 / (f5 + 27.13f);
        double d3 = f8;
        double d4 = (double)object3;
        double d5 = f5;
        f9 = (float)(d3 * 11.0 + d4 * -12.0 + d5) / 11.0f;
        f10 = (float)((double)(f8 + object3) - d5 * 2.0) / 9.0f;
        f3 = (float)(object3 * 20.0f);
        f6 = (f8 * 20.0f + f3 + 21.0f * f5) / 20.0f;
        f3 = (f8 * 40.0f + f3 + f5) / 20.0f;
        f5 = (float)Math.atan2(f10, f9) * 180.0f / (float)Math.PI;
        if (f5 < 0.0f) {
            f8 = f5 + 360.0f;
        } else {
            f8 = f5;
            if (f5 >= 360.0f) {
                f8 = f5 - 360.0f;
            }
        }
        f11 = (float)Math.PI * f8 / 180.0f;
        f7 = (float)Math.pow(f3 * l3.f() / l3.a(), l3.b() * l3.j()) * 100.0f;
        object3 = 4.0f / l3.b();
        object4 = (float)Math.sqrt(f7 / 100.0f);
        object2 = l3.a();
        f3 = l3.d();
        f5 = (double)f8 < 20.14 ? 360.0f + f8 : f8;
        f5 = (float)(Math.cos((double)f5 * Math.PI / 180.0 + 2.0) + 3.8) * 0.25f * 3846.1538f * l3.g() * l3.h() * (float)Math.sqrt(f9 * f9 + f10 * f10) / (f6 + 0.305f);
        f9 = (float)Math.pow(1.64 - Math.pow(0.29, l3.e()), 0.73) * (float)Math.pow(f5, 0.9);
        f10 = (float)Math.sqrt((double)f7 / 100.0) * f9;
        f5 = l3.d() * f10;
        f4 = (float)Math.sqrt(f9 * l3.b() / (l3.a() + 4.0f));
        f6 = 1.7f * f7 / (0.007f * f7 + 1.0f);
        f9 = (float)Math.log(0.0228f * f5 + 1.0f) * 43.85965f;
        d4 = f11;
        float f12 = (float)Math.cos(d4);
        f11 = (float)Math.sin(d4);
        fArray2[0] = f8;
        fArray2[1] = f10;
        if (fArray != null) {
            fArray[0] = f7;
            fArray[1] = (float)(object3 * object4 * (object2 + 4.0f) * f3);
            fArray[2] = f5;
            fArray[3] = f4 * 50.0f;
            fArray[4] = f6;
            fArray[5] = f12 * f9;
            fArray[6] = f9 * f11;
        }
    }

    public static a e(float f3, float f4, float f5) {
        return f0.a.f(f3, f4, f5, l.k);
    }

    public static a f(float f3, float f4, float f5, l l3) {
        float f6 = 4.0f / l3.b();
        double d3 = (double)f3 / 100.0;
        float f7 = (float)Math.sqrt(d3);
        float f8 = l3.a();
        float f9 = l3.d();
        float f10 = l3.d() * f4;
        float f11 = (float)Math.sqrt(f4 / (float)Math.sqrt(d3) * l3.b() / (l3.a() + 4.0f));
        float f12 = (float)Math.PI * f5 / 180.0f;
        float f13 = 1.7f * f3 / (0.007f * f3 + 1.0f);
        float f14 = (float)Math.log((double)f10 * 0.0228 + 1.0) * 43.85965f;
        d3 = f12;
        return new a(f5, f4, f3, f6 * f7 * (f8 + 4.0f) * f9, f10, f11 * 50.0f, f13, (float)Math.cos(d3) * f14, f14 * (float)Math.sin(d3));
    }

    public static int m(float f3, float f4, float f5) {
        return f0.a.n(f3, f4, f5, l.k);
    }

    public static int n(float f3, float f4, float f5, l l3) {
        if (!((double)f4 < 1.0 || (double)Math.round(f5) <= 0.0 || (double)Math.round(f5) >= 100.0)) {
            float f6 = f3 < 0.0f ? 0.0f : Math.min(360.0f, f3);
            a a4 = null;
            boolean bl = true;
            float f7 = 0.0f;
            f3 = f4;
            float f8 = f4;
            f4 = f7;
            while (Math.abs(f4 - f8) >= 0.4f) {
                a a5 = f0.a.b(f6, f3, f5);
                if (bl) {
                    if (a5 != null) {
                        return a5.o(l3);
                    }
                    f3 = (f8 - f4) / 2.0f + f4;
                    bl = false;
                    continue;
                }
                if (a5 == null) {
                    f8 = f3;
                } else {
                    a4 = a5;
                    f4 = f3;
                }
                f3 = (f8 - f4) / 2.0f + f4;
            }
            if (a4 == null) {
                return f0.b.a(f5);
            }
            return a4.o(l3);
        }
        return f0.b.a(f5);
    }

    public float a(a a4) {
        float f3 = this.l() - a4.l();
        float f4 = this.g() - a4.g();
        float f5 = this.h() - a4.h();
        return (float)(Math.pow(Math.sqrt(f3 * f3 + f4 * f4 + f5 * f5), 0.63) * 1.41);
    }

    public float g() {
        return this.h;
    }

    public float h() {
        return this.i;
    }

    public float i() {
        return this.b;
    }

    public float j() {
        return this.a;
    }

    public float k() {
        return this.c;
    }

    public float l() {
        return this.g;
    }

    public int o(l object) {
        float f3 = (double)this.i() != 0.0 && (double)this.k() != 0.0 ? this.i() / (float)Math.sqrt((double)this.k() / 100.0) : 0.0f;
        Object object2 = (float)Math.pow((double)f3 / Math.pow(1.64 - Math.pow(0.29, ((l)object).e()), 0.73), 1.1111111111111112);
        double d3 = this.j() * (float)Math.PI / 180.0f;
        Object object3 = (float)(Math.cos(2.0 + d3) + 3.8);
        Object object4 = ((l)object).a();
        f3 = (float)Math.pow((double)this.k() / 100.0, 1.0 / (double)((l)object).b() / (double)((l)object).j());
        float f4 = ((l)object).g();
        Object object5 = ((l)object).h();
        f3 = object4 * f3 / ((l)object).f();
        object4 = (float)Math.sin(d3);
        Object object6 = (float)Math.cos(d3);
        object2 = (0.305f + f3) * 23.0f * object2 / (object3 * 0.25f * 3846.1538f * f4 * object5 * 23.0f + 11.0f * object2 * object6 + object2 * 108.0f * object4);
        f4 = object6 * object2;
        object5 = f3 * 460.0f;
        f3 = (451.0f * f4 + object5 + 288.0f * (object2 *= object4)) / 1403.0f;
        object4 = (object5 - 891.0f * f4 - 261.0f * object2) / 1403.0f;
        object6 = (object5 - f4 * 220.0f - object2 * 6300.0f) / 1403.0f;
        object2 = (float)Math.max(0.0, (double)Math.abs(f3) * 27.13 / (400.0 - (double)Math.abs(f3)));
        f3 = Math.signum(f3);
        f4 = 100.0f / ((l)object).c();
        object2 = (float)Math.pow(object2, 2.380952380952381);
        object3 = (float)Math.max(0.0, (double)Math.abs(object4) * 27.13 / (400.0 - (double)Math.abs(object4)));
        object4 = Math.signum(object4);
        object5 = 100.0f / ((l)object).c();
        object3 = (float)Math.pow(object3, 2.380952380952381);
        Object object7 = (float)Math.max(0.0, (double)Math.abs(object6) * 27.13 / (400.0 - (double)Math.abs(object6)));
        Object object8 = Math.signum(object6);
        object6 = 100.0f / ((l)object).c();
        object7 = (float)Math.pow(object7, 2.380952380952381);
        f3 = f3 * f4 * object2 / ((l)object).i()[0];
        f4 = object4 * object5 * object3 / ((l)object).i()[1];
        float f5 = object8 * object6 * object7 / ((l)object).i()[2];
        object = f0.b.b;
        Object object9 = object[0];
        object3 = object9[0];
        object7 = object9[1];
        object8 = object9[2];
        object9 = object[1];
        object4 = object9[0];
        object5 = object9[1];
        Object object10 = object9[2];
        object = object[2];
        object2 = object[0];
        object6 = object[1];
        Object object11 = object[2];
        return g0.a.b(object3 * f3 + object7 * f4 + object8 * f5, object4 * f3 + object5 * f4 + object10 * f5, f3 * object2 + f4 * object6 + f5 * object11);
    }

    public int p() {
        return this.o(l.k);
    }
}

