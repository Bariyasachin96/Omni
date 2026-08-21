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
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import m1.c0;
import m1.d0;

public abstract class e0 {
    public static boolean a = true;
    public static Field b;
    public static boolean c;

    public void a(View view) {
    }

    public float b(View view) {
        if (a) {
            try {
                float f3 = m1.e0$a.a(view);
                return f3;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                a = false;
            }
        }
        return view.getAlpha();
    }

    public void c(View view) {
    }

    public abstract void d(View var1, Matrix var2);

    public abstract void e(View var1, int var2, int var3, int var4, int var5);

    public void f(View view, float f3) {
        if (a) {
            try {
                m1.e0$a.b(view, f3);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                a = false;
            }
        }
        view.setAlpha(f3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void g(View view, int n3) {
        Field field;
        if (!c) {
            try {
                b = field = View.class.getDeclaredField("mViewFlags");
                ((AccessibleObject)field).setAccessible(true);
            }
            catch (NoSuchFieldException noSuchFieldException) {}
            c = true;
        }
        if ((field = b) == null) return;
        try {
            int n4 = field.getInt(view);
            b.setInt(view, n3 | n4 & 0xFFFFFFF3);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    public abstract void h(View var1, Matrix var2);

    public abstract void i(View var1, Matrix var2);

    public static abstract class a {
        public static float a(View view) {
            return d0.a(view);
        }

        public static void b(View view, float f3) {
            c0.a(view, f3);
        }
    }
}

