/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Color
 *  android.util.TypedValue
 *  android.view.View
 */
package h2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import s2.b;

public abstract class a {
    public static int a(int n3, int n4) {
        return g0.a.k(n3, Color.alpha((int)n3) * n4 / 255);
    }

    public static int b(Context object, int n3, int n4) {
        if ((object = a.f((Context)object, n3)) != null) {
            return (Integer)object;
        }
        return n4;
    }

    public static int c(Context context, int n3, String string) {
        return a.l(context, b.g(context, n3, string));
    }

    public static int d(View view, int n3) {
        return a.l(view.getContext(), b.h(view, n3));
    }

    public static int e(View view, int n3, int n4) {
        return a.b(view.getContext(), n3, n4);
    }

    public static Integer f(Context context, int n3) {
        TypedValue typedValue = b.a(context, n3);
        if (typedValue != null) {
            return a.l(context, typedValue);
        }
        return null;
    }

    public static ColorStateList g(Context context, int n3) {
        TypedValue typedValue = b.a(context, n3);
        if (typedValue == null) {
            return null;
        }
        n3 = typedValue.resourceId;
        if (n3 != 0) {
            return e0.a.c(context, n3);
        }
        n3 = typedValue.data;
        if (n3 != 0) {
            return ColorStateList.valueOf((int)n3);
        }
        return null;
    }

    public static boolean h(int n3) {
        return n3 != 0 && g0.a.d(n3) > 0.5;
    }

    public static int i(int n3, int n4) {
        return g0.a.g(n4, n3);
    }

    public static int j(int n3, int n4, float f3) {
        return a.i(n3, g0.a.k(n4, Math.round((float)Color.alpha((int)n4) * f3)));
    }

    public static int k(View view, int n3, int n4, float f3) {
        return a.j(a.d(view, n3), a.d(view, n4), f3);
    }

    public static int l(Context context, TypedValue typedValue) {
        int n3 = typedValue.resourceId;
        if (n3 != 0) {
            return e0.a.b(context, n3);
        }
        return typedValue.data;
    }
}

