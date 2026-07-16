/*
 * Decompiled with CFR 0.152.
 */
package f2;

public final class a {
    public final int a;
    public float b;
    public int c;
    public int d;
    public float e;
    public float f;
    public final int g;
    public final float h;

    public a(int n3, float f3, float f4, float f5, int n4, float f6, int n5, float f7, int n6, float f8) {
        this.a = n3;
        this.b = j0.a.a(f3, f4, f5);
        this.c = n4;
        this.e = f6;
        this.d = n5;
        this.f = f7;
        this.g = n6;
        this.d(f8, f4, f5, f7);
        this.h = this.b(f7);
    }

    public static a c(float f3, float f4, float f5, float f6, int[] nArray, float f7, int[] nArray2, float f8, int[] nArray3) {
        int n3 = nArray3.length;
        a a4 = null;
        int n4 = 1;
        for (int i3 = 0; i3 < n3; ++i3) {
            int n5 = nArray3[i3];
            for (int n6 : nArray2) {
                int n7 = nArray.length;
                int n8 = 0;
                while (n8 < n7) {
                    a a5;
                    block8: {
                        a a6;
                        block7: {
                            a6 = new a(n4, f4, f5, f6, nArray[n8], f7, n6, f8, n5, f3);
                            if (a4 == null) break block7;
                            a5 = a4;
                            if (!(a6.h < a4.h)) break block8;
                        }
                        if (a6.h == 0.0f) {
                            return a6;
                        }
                        a5 = a6;
                    }
                    ++n8;
                    ++n4;
                    a4 = a5;
                }
            }
        }
        return a4;
    }

    public final float a(float f3, int n3, float f4, int n4, int n5) {
        if (n3 <= 0) {
            f4 = 0.0f;
        }
        float f5 = n3;
        float f6 = (float)n4 / 2.0f;
        return (f3 - (f5 + f6) * f4) / ((float)n5 + f6);
    }

    public final float b(float f3) {
        if (!this.g()) {
            return Float.MAX_VALUE;
        }
        return Math.abs(f3 - this.f) * (float)this.a;
    }

    public final void d(float f3, float f4, float f5, float f6) {
        float f7 = f3 - this.f();
        int n3 = this.c;
        if (n3 > 0 && f7 > 0.0f) {
            f4 = this.b;
            this.b = f4 + Math.min(f7 / (float)n3, f5 - f4);
        } else if (n3 > 0 && f7 < 0.0f) {
            f5 = this.b;
            this.b = f5 + Math.max(f7 / (float)n3, f4 - f5);
        }
        n3 = this.c;
        f4 = n3 > 0 ? this.b : 0.0f;
        this.b = f4;
        this.f = f4 = this.a(f3, n3, f4, this.d, this.g);
        this.e = f3 = (this.b + f4) / 2.0f;
        n3 = this.d;
        if (n3 > 0 && f4 != f6) {
            f4 = (f6 - f4) * (float)this.g;
            f5 = n3;
            f3 = Math.min(Math.abs(f4), f3 * 0.1f * f5);
            if (f4 > 0.0f) {
                this.e -= f3 / (float)this.d;
                this.f += f3 / (float)this.g;
                return;
            }
            this.e += f3 / (float)this.d;
            this.f -= f3 / (float)this.g;
        }
    }

    public int e() {
        return this.c + this.d + this.g;
    }

    public final float f() {
        return this.f * (float)this.g + this.e * (float)this.d + this.b * (float)this.c;
    }

    public final boolean g() {
        int n3 = this.g;
        if (n3 > 0 && this.c > 0 && this.d > 0) {
            float f3 = this.f;
            float f4 = this.e;
            return f3 > f4 && f4 > this.b;
        }
        if (n3 > 0 && this.c > 0) {
            return this.f > this.b;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Arrangement [priority=");
        stringBuilder.append(this.a);
        stringBuilder.append(", smallCount=");
        stringBuilder.append(this.c);
        stringBuilder.append(", smallSize=");
        stringBuilder.append(this.b);
        stringBuilder.append(", mediumCount=");
        stringBuilder.append(this.d);
        stringBuilder.append(", mediumSize=");
        stringBuilder.append(this.e);
        stringBuilder.append(", largeCount=");
        stringBuilder.append(this.g);
        stringBuilder.append(", largeSize=");
        stringBuilder.append(this.f);
        stringBuilder.append(", cost=");
        stringBuilder.append(this.h);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

