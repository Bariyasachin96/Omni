/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 */
package m1;

import android.graphics.Matrix;
import android.view.View;
import m1.e0;
import m1.f0;
import m1.g0;
import m1.h0;

public abstract class i0
extends e0 {
    public static boolean d = true;
    public static boolean e = true;
    public static boolean f = true;

    @Override
    public void d(View view, Matrix matrix) {
        if (d) {
            try {
                m1.i0$a.a(view, matrix);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                d = false;
            }
        }
    }

    @Override
    public void h(View view, Matrix matrix) {
        if (e) {
            try {
                m1.i0$a.b(view, matrix);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                e = false;
            }
        }
    }

    @Override
    public void i(View view, Matrix matrix) {
        if (f) {
            try {
                m1.i0$a.c(view, matrix);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                f = false;
            }
        }
    }

    public static abstract class a {
        public static void a(View view, Matrix matrix) {
            f0.a(view, matrix);
        }

        public static void b(View view, Matrix matrix) {
            g0.a(view, matrix);
        }

        public static void c(View view, Matrix matrix) {
            h0.a(view, matrix);
        }
    }
}

