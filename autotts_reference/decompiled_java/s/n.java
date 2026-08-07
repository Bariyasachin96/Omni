/*
 * Decompiled with CFR 0.152.
 */
package s;

import s.m;

public class n
implements m {
    public float a;
    public float b;
    public float c;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public int j;
    public String k;
    public boolean l = false;
    public float m;
    public float n;
    public float o;
    public boolean p = false;

    @Override
    public boolean a() {
        return this.b() < 1.0E-5f && Math.abs(this.i - this.n) < 1.0E-5f;
    }

    @Override
    public float b() {
        if (this.l) {
            return -this.e(this.o);
        }
        return this.e(this.o);
    }

    public final float c(float f3) {
        this.p = false;
        float f4 = this.d;
        if (f3 <= f4) {
            float f5 = this.a;
            return f5 * f3 + (this.b - f5) * f3 * f3 / (f4 * 2.0f);
        }
        int n3 = this.j;
        if (n3 == 1) {
            return this.g;
        }
        float f6 = f3 - f4;
        float f7 = this.e;
        if (f6 < f7) {
            f3 = this.g;
            f4 = this.b;
            return f3 + f4 * f6 + (this.c - f4) * f6 * f6 / (f7 * 2.0f);
        }
        if (n3 == 2) {
            return this.h;
        }
        if ((f7 = f6 - f7) <= (f6 = this.f)) {
            f4 = this.h;
            f3 = this.c;
            return f4 + f3 * f7 - f3 * f7 * f7 / (f6 * 2.0f);
        }
        this.p = true;
        return this.i;
    }

    public void d(float f3, float f4, float f5, float f6, float f7, float f8) {
        boolean bl = false;
        this.p = false;
        this.m = f3;
        if (f3 > f4) {
            bl = true;
        }
        this.l = bl;
        if (bl) {
            this.f(-f5, f3 - f4, f7, f8, f6);
            return;
        }
        this.f(f5, f4 - f3, f7, f8, f6);
    }

    public float e(float f3) {
        float f4 = this.d;
        if (f3 <= f4) {
            float f5 = this.a;
            return f5 + (this.b - f5) * f3 / f4;
        }
        int n3 = this.j;
        if (n3 == 1) {
            return 0.0f;
        }
        float f6 = this.e;
        if ((f4 = f3 - f4) < f6) {
            f3 = this.b;
            return f3 + (this.c - f3) * f4 / f6;
        }
        if (n3 == 2) {
            return 0.0f;
        }
        f3 = this.f;
        if ((f6 = f4 - f6) < f3) {
            f4 = this.c;
            return f4 - f6 * f4 / f3;
        }
        return 0.0f;
    }

    public final void f(float f3, float f4, float f5, float f6, float f7) {
        this.p = false;
        this.i = f4;
        float f8 = f3;
        if (f3 == 0.0f) {
            f8 = 1.0E-4f;
        }
        f3 = f8 / f5;
        float f9 = f3 * f8 / 2.0f;
        if (f8 < 0.0f) {
            f3 = (float)Math.sqrt((f4 - -f8 / f5 * f8 / 2.0f) * f5);
            if (f3 < f6) {
                this.k = "backward accelerate, decelerate";
                this.j = 2;
                this.a = f8;
                this.b = f3;
                this.c = 0.0f;
                this.d = f6 = (f3 - f8) / f5;
                this.e = f3 / f5;
                this.g = (f8 + f3) * f6 / 2.0f;
                this.h = f4;
                this.i = f4;
                return;
            }
            this.k = "backward accelerate cruse decelerate";
            this.j = 3;
            this.a = f8;
            this.b = f6;
            this.c = f6;
            this.d = f3 = (f6 - f8) / f5;
            this.f = f5 = f6 / f5;
            f3 = (f8 + f6) * f3 / 2.0f;
            f5 = f5 * f6 / 2.0f;
            this.e = (f4 - f3 - f5) / f6;
            this.g = f3;
            this.h = f4 - f5;
            this.i = f4;
            return;
        }
        if (f9 >= f4) {
            this.k = "hard stop";
            f3 = 2.0f * f4 / f8;
            this.j = 1;
            this.a = f8;
            this.b = 0.0f;
            this.g = f4;
            this.d = f3;
            return;
        }
        float f10 = (f9 = f4 - f9) / f8;
        if (f10 + f3 < f7) {
            this.k = "cruse decelerate";
            this.j = 2;
            this.a = f8;
            this.b = f8;
            this.c = 0.0f;
            this.g = f9;
            this.h = f4;
            this.d = f10;
            this.e = f3;
            return;
        }
        f3 = (float)Math.sqrt(f5 * f4 + f8 * f8 / 2.0f);
        this.d = f7 = (f3 - f8) / f5;
        this.e = f9 = f3 / f5;
        if (f3 < f6) {
            this.k = "accelerate decelerate";
            this.j = 2;
            this.a = f8;
            this.b = f3;
            this.c = 0.0f;
            this.d = f7;
            this.e = f9;
            this.g = (f8 + f3) * f7 / 2.0f;
            this.h = f4;
            return;
        }
        this.k = "accelerate cruse decelerate";
        this.j = 3;
        this.a = f8;
        this.b = f6;
        this.c = f6;
        this.d = f7 = (f6 - f8) / f5;
        this.f = f3 = f6 / f5;
        f5 = (f8 + f6) * f7 / 2.0f;
        f3 = f3 * f6 / 2.0f;
        this.e = (f4 - f5 - f3) / f6;
        this.g = f5;
        this.h = f4 - f3;
        this.i = f4;
    }

    @Override
    public float getInterpolation(float f3) {
        float f4;
        this.n = f4 = this.c(f3);
        this.o = f3;
        if (this.l) {
            return this.m - f4;
        }
        return this.m + f4;
    }
}

