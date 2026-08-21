/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Build$VERSION
 *  android.view.ViewGroup
 */
package m1;

import android.os.Build;
import android.view.ViewGroup;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class a0 {
    public static boolean a = true;
    public static Method b;
    public static boolean c;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int a(ViewGroup viewGroup, int n3) {
        GenericDeclaration genericDeclaration;
        if (Build.VERSION.SDK_INT >= 29) {
            return m1.a0$a.a(viewGroup, n3);
        }
        if (!c) {
            try {
                genericDeclaration = Integer.TYPE;
                genericDeclaration = ViewGroup.class.getDeclaredMethod("getChildDrawingOrder", new Class[]{genericDeclaration, genericDeclaration});
                b = genericDeclaration;
                ((AccessibleObject)((Object)genericDeclaration)).setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            c = true;
        }
        if ((genericDeclaration = b) == null) return n3;
        try {
            return (Integer)((Method)genericDeclaration).invoke(viewGroup, viewGroup.getChildCount(), n3);
        }
        catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
            return n3;
        }
    }

    public static void b(ViewGroup viewGroup, boolean bl) {
        if (a) {
            try {
                m1.a0$a.b(viewGroup, bl);
                return;
            }
            catch (NoSuchMethodError noSuchMethodError) {
                a = false;
            }
        }
    }

    public static void c(ViewGroup viewGroup, boolean bl) {
        if (Build.VERSION.SDK_INT >= 29) {
            m1.a0$a.b(viewGroup, bl);
            return;
        }
        a0.b(viewGroup, bl);
    }

    public static abstract class a {
        public static int a(ViewGroup viewGroup, int n3) {
            return viewGroup.getChildDrawingOrder(n3);
        }

        public static void b(ViewGroup viewGroup, boolean bl) {
            viewGroup.suppressLayout(bl);
        }
    }
}

