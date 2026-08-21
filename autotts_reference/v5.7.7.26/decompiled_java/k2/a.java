/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Color
 */
package k2;

import android.content.Context;
import android.graphics.Color;
import s2.b;
import z1.c;

public class a {
    public static final int f = (int)Math.round(5.1000000000000005);
    public final boolean a;
    public final int b;
    public final int c;
    public final int d;
    public final float e;

    public a(Context context) {
        this(s2.b.b(context, z1.c.elevationOverlayEnabled, false), h2.a.b(context, z1.c.elevationOverlayColor, 0), h2.a.b(context, z1.c.elevationOverlayAccentColor, 0), h2.a.b(context, z1.c.colorSurface, 0), context.getResources().getDisplayMetrics().density);
    }

    public a(boolean bl, int n3, int n4, int n5, float f3) {
        this.a = bl;
        this.b = n3;
        this.c = n4;
        this.d = n5;
        this.e = f3;
    }

    public float a(float f3) {
        float f4 = this.e;
        if (!(f4 <= 0.0f) && !(f3 <= 0.0f)) {
            return Math.min(((float)Math.log1p(f3 / f4) * 4.5f + 2.0f) / 100.0f, 1.0f);
        }
        return 0.0f;
    }

    public int b(int n3, float f3) {
        int n4;
        f3 = this.a(f3);
        int n5 = Color.alpha((int)n3);
        n3 = n4 = h2.a.j(g0.a.k(n3, 255), this.b, f3);
        if (f3 > 0.0f) {
            int n6 = this.c;
            n3 = n4;
            if (n6 != 0) {
                n3 = h2.a.i(n4, g0.a.k(n6, f));
            }
        }
        return g0.a.k(n3, n5);
    }

    public int c(int n3, float f3) {
        int n4 = n3;
        if (this.a) {
            n4 = n3;
            if (this.f(n3)) {
                n4 = this.b(n3, f3);
            }
        }
        return n4;
    }

    public int d(float f3) {
        return this.c(this.d, f3);
    }

    public boolean e() {
        return this.a;
    }

    public final boolean f(int n3) {
        return g0.a.k(n3, 255) == this.d;
    }
}

