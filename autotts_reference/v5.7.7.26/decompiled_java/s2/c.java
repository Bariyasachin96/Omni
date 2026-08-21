/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package s2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import androidx.appcompat.widget.m0;
import c.j;
import d.a;
import s2.d;
import z1.m;

public abstract class c {
    public static ColorStateList a(Context context, TypedArray typedArray, int n3) {
        int n4;
        if (typedArray.hasValue(n3) && (n4 = typedArray.getResourceId(n3, 0)) != 0 && (context = a.a(context, n4)) != null) {
            return context;
        }
        return typedArray.getColorStateList(n3);
    }

    public static ColorStateList b(Context context, m0 m02, int n3) {
        int n4;
        if (m02.s(n3) && (n4 = m02.n(n3, 0)) != 0 && (context = a.a(context, n4)) != null) {
            return context;
        }
        return m02.c(n3);
    }

    public static int c(TypedValue typedValue) {
        return typedValue.getComplexUnit();
    }

    public static int d(Context context, TypedArray typedArray, int n3, int n4) {
        TypedValue typedValue = new TypedValue();
        if (typedArray.getValue(n3, typedValue) && typedValue.type == 2) {
            context = context.getTheme().obtainStyledAttributes(new int[]{typedValue.data});
            n3 = context.getDimensionPixelSize(0, n4);
            context.recycle();
            return n3;
        }
        return typedArray.getDimensionPixelSize(n3, n4);
    }

    public static Drawable e(Context context, TypedArray typedArray, int n3) {
        int n4;
        if (typedArray.hasValue(n3) && (n4 = typedArray.getResourceId(n3, 0)) != 0 && (context = a.b(context, n4)) != null) {
            return context;
        }
        return typedArray.getDrawable(n3);
    }

    public static float f(Context context) {
        return context.getResources().getConfiguration().fontScale;
    }

    public static int g(TypedArray typedArray, int n3, int n4) {
        if (typedArray.hasValue(n3)) {
            return n3;
        }
        return n4;
    }

    public static d h(Context context, TypedArray typedArray, int n3) {
        if (typedArray.hasValue(n3) && (n3 = typedArray.getResourceId(n3, 0)) != 0) {
            return new d(context, n3);
        }
        return null;
    }

    public static int i(Context context, int n3, int n4) {
        TypedValue typedValue;
        block6: {
            block5: {
                boolean bl;
                if (n3 == 0) break block5;
                TypedArray typedArray = context.obtainStyledAttributes(n3, m.MaterialTextAppearance);
                typedValue = new TypedValue();
                boolean bl2 = bl = typedArray.getValue(m.MaterialTextAppearance_lineHeight, typedValue);
                if (!bl) {
                    bl2 = typedArray.getValue(m.MaterialTextAppearance_android_lineHeight, typedValue);
                }
                typedArray.recycle();
                if (bl2) break block6;
            }
            return n4;
        }
        if (c.c(typedValue) == 2) {
            return Math.round(TypedValue.complexToFloat((int)typedValue.data) * context.getResources().getDisplayMetrics().density);
        }
        return TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)context.getResources().getDisplayMetrics());
    }

    public static int j(Context context, int n3, int n4) {
        TypedValue typedValue;
        block5: {
            block4: {
                if (n3 == 0) break block4;
                TypedArray typedArray = context.obtainStyledAttributes(n3, j.TextAppearance);
                typedValue = new TypedValue();
                boolean bl = typedArray.getValue(j.TextAppearance_android_textSize, typedValue);
                typedArray.recycle();
                if (bl) break block5;
            }
            return n4;
        }
        if (c.c(typedValue) == 2) {
            return Math.round(TypedValue.complexToFloat((int)typedValue.data) * context.getResources().getDisplayMetrics().density);
        }
        return TypedValue.complexToDimensionPixelSize((int)typedValue.data, (DisplayMetrics)context.getResources().getDisplayMetrics());
    }

    public static boolean k(Context context) {
        return context.getResources().getConfiguration().fontScale >= 1.3f;
    }

    public static boolean l(Context context) {
        return context.getResources().getConfiguration().fontScale >= 2.0f;
    }
}

