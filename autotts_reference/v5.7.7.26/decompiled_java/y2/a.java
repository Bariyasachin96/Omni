/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package y2;

import android.content.Context;
import android.util.AttributeSet;
import h.d;
import z1.c;

public abstract class a {
    public static final int[] a = new int[]{0x1010000, c.a.theme};
    public static final int[] b = new int[]{c.materialThemeOverlay};

    public static int a(Context context, AttributeSet attributeSet) {
        context = context.obtainStyledAttributes(attributeSet, a);
        int n3 = context.getResourceId(0, 0);
        int n4 = context.getResourceId(1, 0);
        context.recycle();
        if (n3 != 0) {
            return n3;
        }
        return n4;
    }

    public static int[] b(Context context, AttributeSet attributeSet, int[] nArray, int n3, int n4) {
        int[] nArray2 = new int[nArray.length];
        if (nArray.length > 0) {
            context = context.obtainStyledAttributes(attributeSet, nArray, n3, n4);
            for (n3 = 0; n3 < nArray.length; ++n3) {
                nArray2[n3] = context.getResourceId(n3, 0);
            }
            context.recycle();
        }
        return nArray2;
    }

    public static int c(Context context, AttributeSet attributeSet, int n3, int n4) {
        return y2.a.b(context, attributeSet, b, n3, n4)[0];
    }

    public static Context d(Context context, AttributeSet attributeSet, int n3, int n4) {
        return y2.a.e(context, attributeSet, n3, n4, new int[0]);
    }

    public static Context e(Context context, AttributeSet attributeSet, int n3, int n4, int[] nArray) {
        int n5 = y2.a.c(context, attributeSet, n3, n4);
        boolean bl = context instanceof d;
        int n6 = 0;
        int n7 = bl && ((d)context).c() == n5 ? 1 : 0;
        if (n5 != 0 && n7 == 0) {
            d d3 = new d(context, n5);
            nArray = y2.a.b(context, attributeSet, nArray, n3, n4);
            n4 = nArray.length;
            for (n3 = n6; n3 < n4; ++n3) {
                n7 = nArray[n3];
                if (n7 == 0) continue;
                d3.getTheme().applyStyle(n7, true);
            }
            n3 = y2.a.a(context, attributeSet);
            if (n3 != 0) {
                d3.getTheme().applyStyle(n3, true);
            }
            return d3;
        }
        return context;
    }
}

