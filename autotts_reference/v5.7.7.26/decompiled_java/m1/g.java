/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.view.View
 *  android.view.ViewGroup
 */
package m1;

import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import m1.e;

public class g
implements e {
    public static Class d;
    public static boolean e;
    public static Method f;
    public static boolean g;
    public static Method h;
    public static boolean i;
    public final View c;

    public g(View view) {
        this.c = view;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static e b(View object, ViewGroup viewGroup, Matrix matrix) {
        m1.g.c();
        Method method = f;
        if (method == null) return null;
        try {
            void var2_5;
            void var1_4;
            return new g((View)method.invoke(null, object, var1_4, var2_5));
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void c() {
        if (!g) {
            try {
                Method method;
                m1.g.d();
                f = method = d.getDeclaredMethod("addGhost", View.class, ViewGroup.class, Matrix.class);
                ((AccessibleObject)method).setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            g = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void d() {
        if (!e) {
            try {
                d = Class.forName("android.view.GhostView");
            }
            catch (ClassNotFoundException classNotFoundException) {}
            e = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void e() {
        if (!i) {
            try {
                Method method;
                m1.g.d();
                h = method = d.getDeclaredMethod("removeGhost", View.class);
                ((AccessibleObject)method).setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            i = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void f(View view) {
        m1.g.e();
        Method method = h;
        if (method == null) return;
        try {
            method.invoke(null, view);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new RuntimeException(invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
    }

    @Override
    public void a(ViewGroup viewGroup, View view) {
    }

    @Override
    public void setVisibility(int n3) {
        this.c.setVisibility(n3);
    }
}

