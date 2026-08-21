/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewParent
 */
package o0;

import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import o0.c0;
import o0.d0;

public abstract class f1 {
    public static boolean a(ViewParent viewParent, View object, float f3, float f4, boolean bl) {
        try {
            bl = a.a(viewParent, (View)object, f3, f4, bl);
            return bl;
        }
        catch (AbstractMethodError abstractMethodError) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ViewParent ");
            ((StringBuilder)object).append(viewParent);
            ((StringBuilder)object).append(" does not implement interface method onNestedFling");
            Log.e((String)"ViewParentCompat", (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static boolean b(ViewParent viewParent, View view, float f3, float f4) {
        try {
            boolean bl = a.b(viewParent, view, f3, f4);
            return bl;
        }
        catch (AbstractMethodError abstractMethodError) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewParent ");
            stringBuilder.append(viewParent);
            stringBuilder.append(" does not implement interface method onNestedPreFling");
            Log.e((String)"ViewParentCompat", (String)stringBuilder.toString(), (Throwable)abstractMethodError);
            return false;
        }
    }

    public static void c(ViewParent viewParent, View object, int n3, int n4, int[] nArray, int n5) {
        if (viewParent instanceof c0) {
            ((c0)viewParent).h((View)object, n3, n4, nArray, n5);
            return;
        }
        if (n5 == 0) {
            try {
                a.c(viewParent, (View)object, n3, n4, nArray);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ViewParent ");
                ((StringBuilder)object).append(viewParent);
                ((StringBuilder)object).append(" does not implement interface method onNestedPreScroll");
                Log.e((String)"ViewParentCompat", (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            }
        }
    }

    public static void d(ViewParent viewParent, View view, int n3, int n4, int n5, int n6, int n7, int[] object) {
        if (viewParent instanceof d0) {
            ((d0)viewParent).k(view, n3, n4, n5, n6, n7, (int[])object);
            return;
        }
        object[0] = object[0] + n5;
        object[1] = object[1] + n6;
        if (viewParent instanceof c0) {
            ((c0)viewParent).l(view, n3, n4, n5, n6, n7);
            return;
        }
        if (n7 == 0) {
            try {
                a.d(viewParent, view, n3, n4, n5, n6);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ViewParent ");
                ((StringBuilder)object).append(viewParent);
                ((StringBuilder)object).append(" does not implement interface method onNestedScroll");
                Log.e((String)"ViewParentCompat", (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            }
        }
    }

    public static void e(ViewParent viewParent, View view, View object, int n3, int n4) {
        if (viewParent instanceof c0) {
            ((c0)viewParent).b(view, (View)object, n3, n4);
            return;
        }
        if (n4 == 0) {
            try {
                a.e(viewParent, view, (View)object, n3);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ViewParent ");
                ((StringBuilder)object).append(viewParent);
                ((StringBuilder)object).append(" does not implement interface method onNestedScrollAccepted");
                Log.e((String)"ViewParentCompat", (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            }
        }
    }

    public static boolean f(ViewParent viewParent, View object, View view, int n3, int n4) {
        if (viewParent instanceof c0) {
            return ((c0)viewParent).m((View)object, view, n3, n4);
        }
        if (n4 == 0) {
            try {
                boolean bl = a.f(viewParent, (View)object, view, n3);
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ViewParent ");
                ((StringBuilder)object).append(viewParent);
                ((StringBuilder)object).append(" does not implement interface method onStartNestedScroll");
                Log.e((String)"ViewParentCompat", (String)((StringBuilder)object).toString(), (Throwable)abstractMethodError);
            }
        }
        return false;
    }

    public static void g(ViewParent viewParent, View view, int n3) {
        if (viewParent instanceof c0) {
            ((c0)viewParent).g(view, n3);
            return;
        }
        if (n3 == 0) {
            try {
                a.g(viewParent, view);
                return;
            }
            catch (AbstractMethodError abstractMethodError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ViewParent ");
                stringBuilder.append(viewParent);
                stringBuilder.append(" does not implement interface method onStopNestedScroll");
                Log.e((String)"ViewParentCompat", (String)stringBuilder.toString(), (Throwable)abstractMethodError);
            }
        }
    }

    public static abstract class a {
        public static boolean a(ViewParent viewParent, View view, float f3, float f4, boolean bl) {
            return viewParent.onNestedFling(view, f3, f4, bl);
        }

        public static boolean b(ViewParent viewParent, View view, float f3, float f4) {
            return viewParent.onNestedPreFling(view, f3, f4);
        }

        public static void c(ViewParent viewParent, View view, int n3, int n4, int[] nArray) {
            viewParent.onNestedPreScroll(view, n3, n4, nArray);
        }

        public static void d(ViewParent viewParent, View view, int n3, int n4, int n5, int n6) {
            viewParent.onNestedScroll(view, n3, n4, n5, n6);
        }

        public static void e(ViewParent viewParent, View view, View view2, int n3) {
            viewParent.onNestedScrollAccepted(view, view2, n3);
        }

        public static boolean f(ViewParent viewParent, View view, View view2, int n3) {
            return viewParent.onStartNestedScroll(view, view2, n3);
        }

        public static void g(ViewParent viewParent, View view) {
            viewParent.onStopNestedScroll(view);
        }
    }
}

