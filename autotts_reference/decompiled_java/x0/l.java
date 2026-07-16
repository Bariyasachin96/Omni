/*
 * Decompiled with CFR 0.152.
 */
package x0;

import x0.h;

public final class l {
    public double a = Math.sqrt(1500.0);
    public double b = 0.5;
    public boolean c = false;
    public double d;
    public double e;
    public double f;
    public double g;
    public double h;
    public double i = Double.MAX_VALUE;
    public final h.o j = new h.o();

    public l() {
    }

    public l(float f3) {
        this.i = f3;
    }

    public float a() {
        return (float)this.b;
    }

    public float b() {
        return (float)this.i;
    }

    public float c() {
        double d3 = this.a;
        return (float)(d3 * d3);
    }

    public final void d() {
        if (this.c) {
            return;
        }
        if (this.i != Double.MAX_VALUE) {
            double d3 = this.b;
            if (d3 > 1.0) {
                double d4 = -d3;
                double d5 = this.a;
                this.f = d4 * d5 + d5 * Math.sqrt(d3 * d3 - 1.0);
                d4 = this.b;
                d3 = -d4;
                d5 = this.a;
                this.g = d3 * d5 - d5 * Math.sqrt(d4 * d4 - 1.0);
            } else if (d3 >= 0.0 && d3 < 1.0) {
                this.h = this.a * Math.sqrt(1.0 - d3 * d3);
            }
            this.c = true;
            return;
        }
        throw new IllegalStateException("Error: Final position of the spring must be set before the animation starts");
    }

    public boolean e(float f3, float f4) {
        return (double)Math.abs(f4) < this.e && (double)Math.abs(f3 - this.b()) < this.d;
    }

    public l f(float f3) {
        if (!(f3 < 0.0f)) {
            this.b = f3;
            this.c = false;
            return this;
        }
        throw new IllegalArgumentException("Damping ratio must be non-negative");
    }

    public l g(float f3) {
        this.i = f3;
        return this;
    }

    public l h(float f3) {
        if (!(f3 <= 0.0f)) {
            this.a = Math.sqrt(f3);
            this.c = false;
            return this;
        }
        throw new IllegalArgumentException("Spring stiffness constant must be positive.");
    }

    public void i(double d3) {
        this.d = d3 = Math.abs(d3);
        this.e = d3 * 62.5;
    }

    public h.o j(double d3, double d4, long l3) {
        this.d();
        double d5 = (double)l3 / 1000.0;
        double d6 = d3 - this.i;
        double d7 = this.b;
        if (d7 > 1.0) {
            d3 = this.g;
            double d8 = this.f;
            d7 = d6 - (d3 * d6 - d4) / (d3 - d8);
            d4 = (d6 * d3 - d4) / (d3 - d8);
            d3 = Math.pow(Math.E, d3 * d5) * d7 + Math.pow(Math.E, this.f * d5) * d4;
            double d9 = this.g;
            d6 = Math.pow(Math.E, d9 * d5);
            d8 = this.f;
            d4 = d7 * d9 * d6 + d4 * d8 * Math.pow(Math.E, d8 * d5);
        } else if (d7 == 1.0) {
            d3 = this.a;
            d4 += d3 * d6;
            d3 = Math.pow(Math.E, -d3 * d5) * (d6 += d4 * d5);
            double d10 = Math.pow(Math.E, -this.a * d5);
            d7 = this.a;
            double d11 = -d7;
            d4 = d4 * Math.pow(Math.E, -d7 * d5) + d6 * d10 * d11;
        } else {
            double d12 = 1.0 / this.h;
            d3 = this.a;
            d4 = d12 * (d7 * d3 * d6 + d4);
            d3 = Math.pow(Math.E, -d7 * d3 * d5) * (Math.cos(this.h * d5) * d6 + Math.sin(this.h * d5) * d4);
            double d13 = this.a;
            d12 = -d13;
            d7 = this.b;
            d13 = Math.pow(Math.E, -d7 * d13 * d5);
            double d14 = this.h;
            double d15 = -d14;
            d14 = Math.sin(d14 * d5);
            double d16 = this.h;
            d4 = d12 * d3 * d7 + d13 * (d15 * d6 * d14 + d4 * d16 * Math.cos(d16 * d5));
        }
        h.o o3 = this.j;
        o3.a = (float)(d3 + this.i);
        o3.b = (float)d4;
        return o3;
    }
}

