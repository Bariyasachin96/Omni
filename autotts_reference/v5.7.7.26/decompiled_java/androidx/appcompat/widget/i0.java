/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Color
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.View
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import androidx.appcompat.widget.m0;
import c.j;
import g0.a;

public abstract class i0 {
    public static final ThreadLocal a = new ThreadLocal();
    public static final int[] b = new int[]{-16842910};
    public static final int[] c = new int[]{16842908};
    public static final int[] d = new int[]{16843518};
    public static final int[] e = new int[]{16842919};
    public static final int[] f = new int[]{0x10100A0};
    public static final int[] g = new int[]{0x10100A1};
    public static final int[] h = new int[]{-16842919, -16842908};
    public static final int[] i = new int[0];
    public static final int[] j = new int[1];

    public static void a(View view, Context context) {
        Throwable throwable2;
        block3: {
            block2: {
                context = context.obtainStyledAttributes(c.j.AppCompatTheme);
                try {
                    if (context.hasValue(c.j.AppCompatTheme_windowActionBar)) break block2;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("View ");
                    stringBuilder.append(view.getClass());
                    stringBuilder.append(" is an AppCompat widget that can only be used with a Theme.AppCompat theme (or descendant).");
                    Log.e((String)"ThemeUtils", (String)stringBuilder.toString());
                }
                catch (Throwable throwable2) {
                    break block3;
                }
            }
            context.recycle();
            return;
        }
        context.recycle();
        throw throwable2;
    }

    public static int b(Context context, int n3) {
        ColorStateList colorStateList = i0.e(context, n3);
        if (colorStateList != null && colorStateList.isStateful()) {
            return colorStateList.getColorForState(b, colorStateList.getDefaultColor());
        }
        colorStateList = i0.f();
        context.getTheme().resolveAttribute(0x1010033, (TypedValue)colorStateList, true);
        return i0.d(context, n3, colorStateList.getFloat());
    }

    public static int c(Context object, int n3) {
        int[] nArray = j;
        nArray[0] = n3;
        object = m0.u((Context)object, null, nArray);
        try {
            n3 = ((m0)object).b(0, 0);
            return n3;
        }
        finally {
            ((m0)object).x();
        }
    }

    public static int d(Context context, int n3, float f3) {
        n3 = i0.c(context, n3);
        return g0.a.k(n3, Math.round((float)Color.alpha((int)n3) * f3));
    }

    public static ColorStateList e(Context object, int n3) {
        Object object2 = j;
        object2[0] = n3;
        object = m0.u((Context)object, null, object2);
        try {
            object2 = ((m0)object).c(0);
            return object2;
        }
        finally {
            ((m0)object).x();
        }
    }

    public static TypedValue f() {
        TypedValue typedValue;
        ThreadLocal threadLocal = a;
        TypedValue typedValue2 = typedValue = (TypedValue)threadLocal.get();
        if (typedValue == null) {
            typedValue2 = new TypedValue();
            threadLocal.set(typedValue2);
        }
        return typedValue2;
    }
}

