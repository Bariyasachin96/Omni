/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.LinearInterpolator
 */
package a2;

import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import d1.b;
import d1.c;

public abstract class a {
    public static final TimeInterpolator a = new LinearInterpolator();
    public static final TimeInterpolator b = new b();
    public static final TimeInterpolator c = new d1.a();
    public static final TimeInterpolator d = new c();
    public static final TimeInterpolator e = new DecelerateInterpolator();

    public static float a(float f3, float f4, float f5) {
        return f3 + f5 * (f4 - f3);
    }

    public static float b(float f3, float f4, float f5, float f6, float f7) {
        if (f7 <= f5) {
            return f3;
        }
        if (f7 >= f6) {
            return f4;
        }
        return a2.a.a(f3, f4, (f7 - f5) / (f6 - f5));
    }

    public static int c(int n3, int n4, float f3) {
        return n3 + Math.round(f3 * (float)(n4 - n3));
    }
}

