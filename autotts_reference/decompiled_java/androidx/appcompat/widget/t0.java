/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Insets
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.WindowInsets$Builder
 */
package androidx.appcompat.widget;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class t0 {
    public static boolean a;
    public static Method b;
    public static final boolean c;

    static {
        boolean bl = Build.VERSION.SDK_INT >= 27;
        c = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void a(View view, Rect rect, Rect rect2) {
        Method method;
        if (Build.VERSION.SDK_INT >= 29) {
            androidx.appcompat.widget.t0$a.a(view, rect, rect2);
            return;
        }
        if (!a) {
            a = true;
            try {
                b = method = View.class.getDeclaredMethod("computeFitSystemWindows", Rect.class, Rect.class);
                if (!method.isAccessible()) {
                    ((AccessibleObject)b).setAccessible(true);
                }
            }
            catch (NoSuchMethodException noSuchMethodException) {}
        }
        if ((method = b) == null) return;
        try {
            method.invoke((Object)view, rect, rect2);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static boolean b(View view) {
        return view.getLayoutDirection() == 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void c(View view) {
        try {
            Method method = view.getClass().getMethod("makeOptionalFitsSystemWindows", null);
            if (!method.isAccessible()) {
                ((AccessibleObject)method).setAccessible(true);
            }
            method.invoke((Object)view, null);
            return;
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException reflectiveOperationException) {
            return;
        }
    }

    public static abstract class a {
        public static void a(View view, Rect rect, Rect rect2) {
            view = view.computeSystemWindowInsets(new WindowInsets.Builder().setSystemWindowInsets(Insets.of((Rect)rect)).build(), rect2).getSystemWindowInsets();
            rect.set(view.left, view.top, view.right, view.bottom);
        }
    }
}

