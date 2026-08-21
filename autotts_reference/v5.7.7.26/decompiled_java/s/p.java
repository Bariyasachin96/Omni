/*
 * Decompiled with CFR 0.152.
 */
package s;

import s.e;
import s.j;

public class p {
    public float a;
    public float b;
    public float c;
    public float d;
    public float e;
    public float f;

    public void a(float f3, float f4, int n3, int n4, float[] fArray) {
        float f5 = fArray[0];
        float f6 = fArray[1];
        f3 = (f3 - 0.5f) * 2.0f;
        float f7 = (f4 - 0.5f) * 2.0f;
        float f8 = this.c;
        float f9 = this.d;
        f4 = this.a;
        float f10 = this.b;
        float f11 = (float)Math.toRadians(this.f);
        float f12 = (float)Math.toRadians(this.e);
        double d3 = (float)(-n3) * f3;
        double d4 = f11;
        double d5 = Math.sin(d4);
        double d6 = (float)n4 * f7;
        float f13 = (float)(d3 * d5 - Math.cos(d4) * d6);
        f11 = (float)((double)((float)n3 * f3) * Math.cos(d4) - d6 * Math.sin(d4));
        fArray[0] = f5 + f8 + f4 * f3 + f13 * f12;
        fArray[1] = f6 + f9 + f10 * f7 + f12 * f11;
    }

    public void b() {
        this.e = 0.0f;
        this.d = 0.0f;
        this.c = 0.0f;
        this.b = 0.0f;
        this.a = 0.0f;
    }

    public void c(e e3, float f3) {
        if (e3 != null) {
            this.e = e3.b(f3);
        }
    }

    public void d(j j3, float f3) {
        if (j3 != null) {
            this.e = j3.b(f3);
            this.f = j3.a(f3);
        }
    }

    public void e(e e3, e e4, float f3) {
        if (e3 != null) {
            this.a = e3.b(f3);
        }
        if (e4 != null) {
            this.b = e4.b(f3);
        }
    }

    public void f(j j3, j j4, float f3) {
        if (j3 != null) {
            this.a = j3.b(f3);
        }
        if (j4 != null) {
            this.b = j4.b(f3);
        }
    }

    public void g(e e3, e e4, float f3) {
        if (e3 != null) {
            this.c = e3.b(f3);
        }
        if (e4 != null) {
            this.d = e4.b(f3);
        }
    }

    public void h(j j3, j j4, float f3) {
        if (j3 != null) {
            this.c = j3.b(f3);
        }
        if (j4 != null) {
            this.d = j4.b(f3);
        }
    }
}

