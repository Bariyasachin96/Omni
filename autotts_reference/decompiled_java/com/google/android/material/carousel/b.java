/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.google.android.material.carousel;

import android.content.Context;
import com.google.android.material.carousel.a;
import com.google.android.material.carousel.c;
import z1.e;

public abstract class b {
    public static float a(float f3, float f4, int n3) {
        return f3 + (float)Math.max(0, n3 - 1) * f4;
    }

    public static float b(float f3, float f4, int n3) {
        float f5 = f3;
        if (n3 > 0) {
            f5 = f3 + f4 / 2.0f;
        }
        return f5;
    }

    public static c c(Context object, float f3, int n3, f2.a a4) {
        float f4 = Math.min(b.f((Context)object) + f3, a4.f);
        float f5 = f4 / 2.0f;
        float f6 = b.b(0.0f, a4.b, a4.c);
        float f7 = b.j(0.0f, b.a(f6, a4.b, (int)Math.floor((float)a4.c / 2.0f)), a4.b, a4.c);
        float f8 = b.b(f7, a4.e, a4.d);
        float f9 = b.j(f7, b.a(f8, a4.e, (int)Math.floor((float)a4.d / 2.0f)), a4.e, a4.d);
        f7 = b.b(f9, a4.f, a4.g);
        float f10 = b.j(f9, b.a(f7, a4.f, a4.g), a4.f, a4.g);
        f9 = b.b(f10, a4.e, a4.d);
        float f11 = b.b(b.j(f10, b.a(f9, a4.e, (int)Math.ceil((float)a4.d / 2.0f)), a4.e, a4.d), a4.b, a4.c);
        float f12 = n3;
        float f13 = a.b(f4, a4.f, f3);
        f10 = a.b(a4.b, a4.f, f3);
        f3 = a.b(a4.e, a4.f, f3);
        object = new c.b(a4.f, n3).a(0.0f - f5, f13, f4);
        n3 = a4.c;
        if (n3 > 0) {
            ((c.b)object).g(f6, f10, a4.b, (int)Math.floor((float)n3 / 2.0f));
        }
        if ((n3 = a4.d) > 0) {
            ((c.b)object).g(f8, f3, a4.e, (int)Math.floor((float)n3 / 2.0f));
        }
        ((c.b)object).h(f7, 0.0f, a4.f, a4.g, true);
        n3 = a4.d;
        if (n3 > 0) {
            ((c.b)object).g(f9, f3, a4.e, (int)Math.ceil((float)n3 / 2.0f));
        }
        if ((n3 = a4.c) > 0) {
            ((c.b)object).g(f11, f10, a4.b, (int)Math.ceil((float)n3 / 2.0f));
        }
        ((c.b)object).a(f12 + f5, f13, f4);
        return ((c.b)object).i();
    }

    public static c d(Context context, float f3, int n3, f2.a a4, int n4) {
        if (n4 == 1) {
            return b.c(context, f3, n3, a4);
        }
        return b.e(context, f3, n3, a4);
    }

    public static c e(Context object, float f3, int n3, f2.a a4) {
        float f4 = Math.min(b.f((Context)object) + f3, a4.f);
        float f5 = f4 / 2.0f;
        float f6 = b.b(0.0f, a4.f, a4.g);
        float f7 = b.j(0.0f, b.a(f6, a4.f, a4.g), a4.f, a4.g);
        float f8 = b.b(f7, a4.e, a4.d);
        f7 = b.b(b.j(f7, f8, a4.e, a4.d), a4.b, a4.c);
        float f9 = n3;
        float f10 = a.b(f4, a4.f, f3);
        float f11 = a.b(a4.b, a4.f, f3);
        f3 = a.b(a4.e, a4.f, f3);
        object = new c.b(a4.f, n3).a(0.0f - f5, f10, f4).h(f6, 0.0f, a4.f, a4.g, true);
        if (a4.d > 0) {
            ((c.b)object).b(f8, f3, a4.e);
        }
        if ((n3 = a4.c) > 0) {
            ((c.b)object).g(f7, f11, a4.b, n3);
        }
        ((c.b)object).a(f9 + f5, f10, f4);
        return ((c.b)object).i();
    }

    public static float f(Context context) {
        return context.getResources().getDimension(e.m3_carousel_gone_size);
    }

    public static float g(Context context) {
        return context.getResources().getDimension(e.m3_carousel_small_item_size_max);
    }

    public static float h(Context context) {
        return context.getResources().getDimension(e.m3_carousel_small_item_size_min);
    }

    public static int i(int[] nArray) {
        int n3 = nArray.length;
        int n4 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < n3; ++i3) {
            int n5 = nArray[i3];
            int n6 = n4;
            if (n5 > n4) {
                n6 = n5;
            }
            n4 = n6;
        }
        return n4;
    }

    public static float j(float f3, float f4, float f5, int n3) {
        if (n3 > 0) {
            return f4 + f5 / 2.0f;
        }
        return f3;
    }
}

