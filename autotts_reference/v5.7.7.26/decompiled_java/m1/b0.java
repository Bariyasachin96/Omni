/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.util.Property
 *  android.view.View
 */
package m1;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Property;
import android.view.View;
import m1.e0;
import m1.m0;
import m1.n0;

public abstract class b0 {
    public static final e0 a = Build.VERSION.SDK_INT >= 29 ? new n0() : new m0();
    public static final Property b = new Property(Float.class, "translationAlpha"){

        public Float a(View view) {
            return Float.valueOf(b0.b(view));
        }

        public void b(View view, Float f3) {
            b0.f(view, f3.floatValue());
        }
    };
    public static final Property c = new Property(Rect.class, "clipBounds"){

        public Rect a(View view) {
            return view.getClipBounds();
        }

        public void b(View view, Rect rect) {
            view.setClipBounds(rect);
        }
    };

    public static void a(View view) {
        a.a(view);
    }

    public static float b(View view) {
        return a.b(view);
    }

    public static void c(View view) {
        a.c(view);
    }

    public static void d(View view, Matrix matrix) {
        a.d(view, matrix);
    }

    public static void e(View view, int n3, int n4, int n5, int n6) {
        a.e(view, n3, n4, n5, n6);
    }

    public static void f(View view, float f3) {
        a.f(view, f3);
    }

    public static void g(View view, int n3) {
        a.g(view, n3);
    }

    public static void h(View view, Matrix matrix) {
        a.h(view, matrix);
    }

    public static void i(View view, Matrix matrix) {
        a.i(view, matrix);
    }
}

