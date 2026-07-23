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
        Object object2 = fArray3[0];
        float f4 = fArray2[1];
        float f5 = fArray3[1];
        float f6 = fArray2[2];
        float f7 = fArray3[2];
        fArray3 = object[1];
        float f8 = fArray3[0];
        float f9 = fArray3[1];
        float f10 = fArray3[2];
        object = object[2];
        Object object3 = object[0];
        Object object4 = object[1];
        Object object5 = object[2];
        object2 = l3.i()[0] * (object2 * f3 + f5 * f4 + f7 * f6);
        f9 = l3.i()[1] * (f8 * f3 + f9 * f4 + f10 * f6);
        object5 = l3.i()[2] * (f3 * object3 + f4 * object4 + f6 * object5);
        object3 = (float)Math.pow((double)(l3.c() * Math.abs(object2)) / 100.0, 0.42);
        f3 = (float)Math.pow((double)(l3.c() * Math.abs(f9)) / 100.0, 0.42);
        object4 = (float)Math.pow((double)(l3.c() * Math.abs((float)object5)) / 100.0, 0.42);
        object2 = Math.signum(object2) * 400.0f * object3 / (object3 + 27.13f);
        f3 = Math.signum(f9) * 400.0f * f3 / (f3 + 27.13f);
        object4 = Math.signum((float)object5) * 400.0f * object4 / (object4 + 27.13f);
        double d3 = object2;
        double d4 = f3;
        double d5 = (double)object4;
        f8 = (float)(d3 * 11.0 + d4 * -12.0 + d5) / 11.0f;
        f10 = (float)((double)(object2 + f3) - d5 * 2.0) / 9.0f;
        object5 = f3 * 20.0f;
        f5 = (object2 * 20.0f + object5 + 21.0f * object4) / 20.0f;
        object5 = (object2 * 40.0f + object5 + object4) / 20.0f;
        object4 = (float)Math.atan2(f10, f8) * 180.0f / (float)Math.PI;
        if (object4 < 0.0f) {
            object2 = object4 + 360.0f;
        } else {
            object2 = object4;
            if (object4 >= 360.0f) {
                object2 = object4 - 360.0f;
            }
        }
        f9 = (float)Math.PI * object2 / 180.0f;
        object5 = (float)Math.pow((double)(object5 * l3.f() / l3.a()), l3.b() * l3.j()) * 100.0f;
        f3 = 4.0f / l3.b();
        f6 = (float)Math.sqrt((double)(object5 / 100.0f));
        object3 = l3.a();
        f4 = l3.d();
        object4 = (double)object2 < 20.14 ? (Object)(360.0f + object2) : (Object)object2;
        object4 = (float)(Math.cos((double)object4 * Math.PI / 180.0 + 2.0) + 3.8) * 0.25f * 3846.1538f * l3.g() * l3.h() * (float)Math.sqrt(f8 * f8 + f10 * f10) / (f5 + 0.305f);
        f8 = (float)Math.pow(1.64 - Math.pow(0.29, l3.e()), 0.73) * (float)Math.pow((double)object4, 0.9);
        f10 = (float)Math.sqrt((double)object5 / 100.0) * f8;
        object4 = l3.d() * f10;
        f8 = (float)Math.sqrt(f8 * l3.b() / (l3.a() + 4.0f));
        f5 = 1.7f * object5 / (0.007f * object5 + 1.0f);
        f7 = (float)Math.log(0.0228f * object4 + 1.0f) * 43.85965f;
        d5 = f9;
        float f11 = (float)Math.cos(d5);
        f9 = (float)Math.sin(d5);
        fArray2[0] = object2;
        fArray2[1] = f10;
        if (fArray != null) {
            fArray[0] = (float)object5;
            fArray[1] = f3 * f6 * (object3 + 4.0f) * f4;
            fArray[2] = (float)object4;
            fArray[3] = f8 * 50.0f;
            fArray[4] = f5;
            fArray[5] = f11 * f7;
            fArray[6] = f7 * f9;
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
        float f4 = ((l)object).a();
        f3 = (float)Math.pow((double)this.k() / 100.0, 1.0 / (double)((l)object).b() / (double)((l)object).j());
        Object object4 = ((l)object).g();
        Object object5 = ((l)object).h();
        f3 = f4 * f3 / ((l)object).f();
        f4 = (float)Math.sin(d3);
        Object object6 = (float)Math.cos(d3);
        object3 = (0.305f + f3) * 23.0f * object2 / (object3 * 0.25f * 3846.1538f * object4 * object5 * 23.0f + 11.0f * object2 * object6 + object2 * 108.0f * f4);
        object4 = object6 * object3;
        f4 = object3 * f4;
        object5 = f3 * 460.0f;
        f3 = (451.0f * object4 + object5 + 288.0f * f4) / 1403.0f;
        object3 = (object5 - 891.0f * object4 - 261.0f * f4) / 1403.0f;
        object2 = (object5 - object4 * 220.0f - f4 * 6300.0f) / 1403.0f;
        object4 = (float)Math.max(0.0, (double)Math.abs(f3) * 27.13 / (400.0 - (double)Math.abs(f3)));
        f4 = Math.signum(f3);
        f3 = 100.0f / ((l)object).c();
        object4 = (float)Math.pow(object4, 2.380952380952381);
        object6 = (float)Math.max(0.0, (double)Math.abs(object3) * 27.13 / (400.0 - (double)Math.abs(object3)));
        object3 = Math.signum(object3);
        object5 = 100.0f / ((l)object).c();
        float f5 = (float)Math.pow(object6, 2.380952380952381);
        Object object7 = (float)Math.max(0.0, (double)Math.abs(object2) * 27.13 / (400.0 - (double)Math.abs(object2)));
        object6 = Math.signum(object2);
        object2 = 100.0f / ((l)object).c();
        object7 = (float)Math.pow(object7, 2.380952380952381);
        f3 = f4 * f3 * object4 / ((l)object).i()[0];
        f4 = object3 * object5 * f5 / ((l)object).i()[1];
        f5 = object6 * object2 * object7 / ((l)object).i()[2];
        object = f0.b.b;
        Object object8 = object[0];
        object5 = object8[0];
        Object object9 = object8[1];
        Object object10 = object8[2];
        object8 = object[1];
        object6 = object8[0];
        Object object11 = object8[1];
        object4 = object8[2];
        object = object[2];
        object7 = object[0];
        object3 = object[1];
        object2 = object[2];
        return g0.a.b(object5 * f3 + object9 * f4 + object10 * f5, object6 * f3 + object11 * f4 + object4 * f5, f3 * object7 + f4 * object3 + f5 * object2);
    }

    public int p() {
        return this.o(l.k);
    }
}

