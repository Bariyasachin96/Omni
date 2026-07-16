/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.graphics.Color
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.util.StateSet
 */
package t2;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.StateSet;

public abstract class a {
    public static final int[] a = new int[]{16842919};
    public static final int[] b = new int[]{16842908};
    public static final int[] c = new int[]{0x10100A1, 16842919};
    public static final int[] d = new int[]{0x10100A1};
    public static final int[] e = new int[]{16842910, 16842919};
    public static final String f = a.class.getSimpleName();

    public static ColorStateList a(ColorStateList colorStateList) {
        int[] nArray = d;
        int n3 = t2.a.c(colorStateList, c);
        int[] nArray2 = b;
        int n4 = t2.a.c(colorStateList, nArray2);
        int[] nArray3 = StateSet.NOTHING;
        int n5 = t2.a.c(colorStateList, a);
        return new ColorStateList((int[][])new int[][]{nArray, nArray2, nArray3}, new int[]{n3, n4, n5});
    }

    public static int b(int n3) {
        return g0.a.k(n3, Math.min(Color.alpha((int)n3) * 2, 255));
    }

    public static int c(ColorStateList colorStateList, int[] nArray) {
        int n3 = colorStateList != null ? colorStateList.getColorForState(nArray, colorStateList.getDefaultColor()) : 0;
        return t2.a.b(n3);
    }

    public static ColorStateList d(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (Build.VERSION.SDK_INT <= 27 && Color.alpha((int)colorStateList.getDefaultColor()) == 0 && Color.alpha((int)colorStateList.getColorForState(e, 0)) != 0) {
                Log.w((String)f, (String)"Use a non-transparent color for the default color as it will be used to finish ripple animations.");
            }
            return colorStateList;
        }
        return ColorStateList.valueOf((int)0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean e(int[] nArray) {
        boolean bl;
        int n3 = nArray.length;
        boolean bl2 = bl = false;
        for (int i3 = 0; i3 < n3; ++i3) {
            boolean bl3;
            block4: {
                block5: {
                    int n4;
                    block3: {
                        n4 = nArray[i3];
                        if (n4 != 16842910) break block3;
                        bl3 = true;
                        break block4;
                    }
                    if (n4 == 16842908 || n4 == 16842919) break block5;
                    bl3 = bl;
                    if (n4 != 16843623) break block4;
                }
                bl2 = true;
                bl3 = bl;
            }
            bl = bl3;
        }
        return bl && bl2;
    }
}

