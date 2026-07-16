/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.TypedValue
 *  android.view.View
 */
package s2;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import z1.c;
import z1.e;

public abstract class b {
    public static TypedValue a(Context context, int n3) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(n3, typedValue, true)) {
            return typedValue;
        }
        return null;
    }

    public static boolean b(Context context, int n3, boolean bl) {
        if ((context = b.a(context, n3)) != null && context.type == 18) {
            return context.data != 0;
        }
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int c(Context context, int n3, int n4) {
        float f3;
        TypedValue typedValue = b.a(context, n3);
        if (typedValue != null && typedValue.type == 5) {
            f3 = typedValue.getDimension(context.getResources().getDisplayMetrics());
            return (int)f3;
        }
        f3 = context.getResources().getDimension(n4);
        return (int)f3;
    }

    public static int d(Context context, int n3, int n4) {
        if ((context = b.a(context, n3)) != null && context.type == 16) {
            return context.data;
        }
        return n4;
    }

    public static int e(Context context) {
        return b.c(context, c.minTouchTargetSize, e.mtrl_min_touch_target_size);
    }

    public static int f(Context context, int n3, String string) {
        return b.g((Context)context, (int)n3, (String)string).data;
    }

    public static TypedValue g(Context context, int n3, String string) {
        TypedValue typedValue = b.a(context, n3);
        if (typedValue != null) {
            return typedValue;
        }
        throw new IllegalArgumentException(String.format("%1$s requires a value for the %2$s attribute to be set in your app theme. You can either set the attribute in your theme or update your theme to inherit from Theme.MaterialComponents (or a descendant).", string, context.getResources().getResourceName(n3)));
    }

    public static TypedValue h(View view, int n3) {
        return b.g(view.getContext(), n3, view.getClass().getCanonicalName());
    }
}

