/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.bottomappbar;

import v2.g;
import v2.q;

public class d
extends g
implements Cloneable {
    public float c;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h = -1.0f;

    public d(float f3, float f4, float f5) {
        this.d = f3;
        this.c = f4;
        this.i(f5);
        this.g = 0.0f;
    }

    @Override
    public void b(float f3, float f4, float f5, q q3) {
        float f6 = this.e;
        if (f6 == 0.0f) {
            q3.m(f3, 0.0f);
            return;
        }
        float f7 = (this.d * 2.0f + f6) / 2.0f;
        float f8 = f5 * this.c;
        float f9 = f4 + this.g;
        f4 = this.f * f5 + (1.0f - f5) * f7;
        if (f4 / f7 >= 1.0f) {
            q3.m(f3, 0.0f);
            return;
        }
        float f10 = this.h;
        float f11 = f10 * f5;
        boolean bl = f10 == -1.0f || Math.abs(f10 * 2.0f - f6) < 0.1f;
        if (!bl) {
            f5 = 1.75f;
            f4 = 0.0f;
        } else {
            f5 = 0.0f;
        }
        f6 = f7 + f8;
        f10 = f4 + f8;
        float f12 = (float)Math.sqrt(f6 * f6 - f10 * f10);
        float f13 = f9 - f12;
        f6 = f9 + f12;
        f10 = (float)Math.toDegrees(Math.atan(f12 / f10));
        f5 = 90.0f - f10 + f5;
        q3.m(f13, 0.0f);
        f12 = f8 * 2.0f;
        q3.a(f13 - f8, 0.0f, f13 + f8, f12, 270.0f, f10);
        if (bl) {
            q3.a(f9 - f7, -f7 - f4, f9 + f7, f7 - f4, 180.0f - f5, f5 * 2.0f - 180.0f);
        } else {
            f13 = this.d;
            f4 = f11 * 2.0f;
            float f14 = f9 - f7;
            q3.a(f14, -(f11 + f13), f13 + f4 + f14, f13 + f11, 180.0f - f5, (f5 * 2.0f - 180.0f) / 2.0f);
            f7 = f9 + f7;
            f9 = this.d;
            q3.m(f7 - (f9 / 2.0f + f11), f9 + f11);
            f9 = this.d;
            q3.a(f7 - (f4 + f9), -(f11 + f9), f7, f9 + f11, 90.0f, f5 - 90.0f);
        }
        q3.a(f6 - f8, 0.0f, f6 + f8, f12, 270.0f - f10, f10);
        q3.m(f3, 0.0f);
    }

    public float c() {
        return this.f;
    }

    public float d() {
        return this.h;
    }

    public float e() {
        return this.d;
    }

    public float f() {
        return this.c;
    }

    public float g() {
        return this.e;
    }

    public float h() {
        return this.g;
    }

    public void i(float f3) {
        if (!(f3 < 0.0f)) {
            this.f = f3;
            return;
        }
        throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
    }

    public void j(float f3) {
        this.h = f3;
    }

    public void k(float f3) {
        this.d = f3;
    }

    public void l(float f3) {
        this.c = f3;
    }

    public void m(float f3) {
        this.e = f3;
    }

    public void n(float f3) {
        this.g = f3;
    }
}

