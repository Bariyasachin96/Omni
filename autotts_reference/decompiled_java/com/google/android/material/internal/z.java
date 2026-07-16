/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.TypedValue
 */
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.m0;
import c.a;
import s2.b;
import z1.c;
import z1.m;

public abstract class z {
    public static final int[] a = new int[]{c.a.colorPrimary};
    public static final int[] b = new int[]{c.colorPrimaryVariant};

    public static void a(Context context) {
        z.e(context, a, "Theme.AppCompat");
    }

    public static void b(Context context, AttributeSet attributeSet, int n3, int n4) {
        attributeSet = context.obtainStyledAttributes(attributeSet, m.ThemeEnforcement, n3, n4);
        boolean bl = attributeSet.getBoolean(m.ThemeEnforcement_enforceMaterialTheme, false);
        attributeSet.recycle();
        if (bl) {
            attributeSet = new TypedValue();
            if (!context.getTheme().resolveAttribute(c.isMaterialTheme, (TypedValue)attributeSet, true) || attributeSet.type == 18 && attributeSet.data == 0) {
                z.c(context);
            }
        }
        z.a(context);
    }

    public static void c(Context context) {
        z.e(context, b, "Theme.MaterialComponents");
    }

    public static void d(Context context, AttributeSet attributeSet, int[] nArray, int n3, int n4, int ... nArray2) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, m.ThemeEnforcement, n3, n4);
        int n5 = m.ThemeEnforcement_enforceTextAppearance;
        boolean bl = false;
        if (!typedArray.getBoolean(n5, false)) {
            typedArray.recycle();
            return;
        }
        if (nArray2 != null && nArray2.length != 0) {
            bl = z.f(context, attributeSet, nArray, n3, n4, nArray2);
        } else if (typedArray.getResourceId(m.ThemeEnforcement_android_textAppearance, -1) != -1) {
            bl = true;
        }
        typedArray.recycle();
        if (bl) {
            return;
        }
        throw new IllegalArgumentException("This component requires that you specify a valid TextAppearance attribute. Update your app theme to inherit from Theme.MaterialComponents (or a descendant).");
    }

    public static void e(Context object, int[] nArray, String string) {
        if (z.h((Context)object, nArray)) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("The style on this component requires your app theme to be ");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" (or a descendant).");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static boolean f(Context context, AttributeSet attributeSet, int[] nArray, int n3, int n4, int ... nArray2) {
        context = context.obtainStyledAttributes(attributeSet, nArray, n3, n4);
        n4 = nArray2.length;
        for (n3 = 0; n3 < n4; ++n3) {
            if (context.getResourceId(nArray2[n3], -1) != -1) continue;
            context.recycle();
            return false;
        }
        context.recycle();
        return true;
    }

    public static boolean g(Context context) {
        return s2.b.b(context, c.isMaterial3Theme, false);
    }

    public static boolean h(Context context, int[] nArray) {
        context = context.obtainStyledAttributes(nArray);
        for (int i3 = 0; i3 < nArray.length; ++i3) {
            if (context.hasValue(i3)) continue;
            context.recycle();
            return false;
        }
        context.recycle();
        return true;
    }

    public static TypedArray i(Context context, AttributeSet attributeSet, int[] nArray, int n3, int n4, int ... nArray2) {
        z.b(context, attributeSet, n3, n4);
        z.d(context, attributeSet, nArray, n3, n4, nArray2);
        return context.obtainStyledAttributes(attributeSet, nArray, n3, n4);
    }

    public static m0 j(Context context, AttributeSet attributeSet, int[] nArray, int n3, int n4, int ... nArray2) {
        z.b(context, attributeSet, n3, n4);
        z.d(context, attributeSet, nArray, n3, n4, nArray2);
        return m0.v(context, attributeSet, nArray, n3, n4);
    }
}

