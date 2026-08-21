/*
 * Decompiled with CFR 0.152.
 */
package f0;

import f0.b;

public final class l {
    public static final l k = l.k(f0.b.c, (float)((double)f0.b.h(50.0f) * 63.66197723675813 / 100.0), 50.0f, 2.0f, false);
    public final float a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final float f;
    public final float[] g;
    public final float h;
    public final float i;
    public final float j;

    public l(float f3, float f4, float f5, float f6, float f7, float f8, float[] fArray, float f9, float f10, float f11) {
        this.f = f3;
        this.a = f4;
        this.b = f5;
        this.c = f6;
        this.d = f7;
        this.e = f8;
        this.g = fArray;
        this.h = f9;
        this.i = f10;
        this.j = f11;
    }

    public static l k(float[] fArray, float f3, float f4, float f5, boolean bl) {
        Object object = f0.b.a;
        float f6 = fArray[0];
        float[] fArray2 = object[0];
        float f7 = fArray2[0];
        float f8 = fArray[1];
        float f9 = fArray2[1];
        float f10 = fArray[2];
        f9 = f7 * f6 + f9 * f8 + fArray2[2] * f10;
        fArray2 = object[1];
        f7 = fArray2[0] * f6 + fArray2[1] * f8 + fArray2[2] * f10;
        object = object[2];
        f10 = f6 * object[0] + f8 * object[1] + f10 * object[2];
        f8 = f5 / 10.0f + 0.8f;
        f6 = (double)f8 >= 0.9 ? f0.b.d(0.59f, 0.69f, (f8 - 0.9f) * 10.0f) : f0.b.d(0.525f, 0.59f, (f8 - 0.8f) * 10.0f);
        f5 = bl ? 1.0f : (1.0f - (float)Math.exp((-f3 - 42.0f) / 92.0f) * 0.2777778f) * f8;
        double d3 = f5;
        if (d3 > 1.0) {
            f5 = 1.0f;
        } else if (d3 < 0.0) {
            f5 = 0.0f;
        }
        float f11 = 100.0f / f9;
        float f12 = 100.0f / f7;
        float f13 = 100.0f / f10;
        object = new float[3];
        object[0] = (float[])(f11 * f5 + 1.0f - f5);
        object[1] = (float[])(f12 * f5 + 1.0f - f5);
        object[2] = (float[])(f13 * f5 + 1.0f - f5);
        f5 = 1.0f / (5.0f * f3 + 1.0f);
        f5 = f5 * f5 * f5 * f5;
        f11 = 1.0f - f5;
        f3 = f5 * f3 + 0.1f * f11 * f11 * (float)Math.cbrt((double)f3 * 5.0);
        f4 = f0.b.h(f4) / fArray[1];
        d3 = f4;
        f11 = (float)Math.sqrt(d3);
        f5 = 0.725f / (float)Math.pow(d3, 0.2);
        f9 = (float)Math.pow((double)(object[0] * f3 * f9) / 100.0, 0.42);
        f7 = (float)Math.pow((double)(object[1] * f3 * f7) / 100.0, 0.42);
        f10 = (float)Math.pow((double)(object[2] * f3 * f10) / 100.0, 0.42);
        fArray = new float[]{f9, f7, f10};
        f7 = fArray[0];
        f7 = f7 * 400.0f / (f7 + 27.13f);
        f9 = fArray[1];
        f9 = f9 * 400.0f / (f9 + 27.13f);
        f10 = fArray[2];
        f10 = 400.0f * f10 / (f10 + 27.13f);
        fArray = new float[]{f7, f9, f10};
        return new l(f4, (fArray[0] * 2.0f + fArray[1] + fArray[2] * 0.05f) * f5, f5, f5, f6, f8, (float[])object, f3, (float)Math.pow(f3, 0.25), f11 + 1.48f);
    }

    public float a() {
        return this.a;
    }

    public float b() {
        return this.d;
    }

    public float c() {
        return this.h;
    }

    public float d() {
        return this.i;
    }

    public float e() {
        return this.f;
    }

    public float f() {
        return this.b;
    }

    public float g() {
        return this.e;
    }

    public float h() {
        return this.c;
    }

    public float[] i() {
        return this.g;
    }

    public float j() {
        return this.j;
    }
}

