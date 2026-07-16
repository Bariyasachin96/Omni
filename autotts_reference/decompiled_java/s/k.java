/*
 * Decompiled with CFR 0.152.
 */
package s;

import s.m;

public class k
implements m {
    public double a = 0.5;
    public boolean b = false;
    public double c;
    public double d;
    public double e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public int k = 0;

    @Override
    public boolean a() {
        double d3 = this.h;
        double d4 = this.c;
        double d5 = (double)this.g - this.d;
        return Math.sqrt((d3 * d3 * (double)this.i + d4 * d5 * d5) / d4) <= (double)this.j;
    }

    @Override
    public float b() {
        return 0.0f;
    }

    public final void c(double d3) {
        if (!(d3 <= 0.0)) {
            double d4 = this.c;
            double d5 = this.a;
            int n3 = (int)(9.0 / (Math.sqrt(d4 / (double)this.i) * d3 * 4.0) + 1.0);
            double d6 = d3 / (double)n3;
            d3 = d5;
            for (int i3 = 0; i3 < n3; ++i3) {
                float f3 = this.g;
                double d7 = f3;
                d5 = this.d;
                double d8 = -d4;
                float f4 = this.h;
                double d9 = f4;
                float f5 = this.i;
                d9 = (d8 * (d7 - d5) - d9 * d3) / (double)f5;
                d9 = (double)f4 + d9 * d6 / 2.0;
                d8 = (-((double)f3 + d6 * d9 / 2.0 - d5) * d4 - d9 * d3) / (double)f5 * d6;
                d9 = f4;
                d5 = d8 / 2.0;
                this.h = f4 += (float)d8;
                this.g = f3 += (float)((d9 + d5) * d6);
                int n4 = this.k;
                if (n4 <= 0) continue;
                if (f3 < 0.0f && (n4 & 1) == 1) {
                    this.g = -f3;
                    this.h = -f4;
                }
                if (!((f3 = this.g) > 1.0f) || (n4 & 2) != 2) continue;
                this.g = 2.0f - f3;
                this.h = -this.h;
            }
        }
    }

    public void d(float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n3) {
        this.d = f4;
        this.a = f8;
        this.b = false;
        this.g = f3;
        this.e = f5;
        this.c = f7;
        this.i = f6;
        this.j = f9;
        this.k = n3;
        this.f = 0.0f;
    }

    @Override
    public float getInterpolation(float f3) {
        this.c(f3 - this.f);
        this.f = f3;
        if (this.a()) {
            this.g = (float)this.d;
        }
        return this.g;
    }
}

