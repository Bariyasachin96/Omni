/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 */
package com.google.android.material.textview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import s2.b;
import s2.c;
import y2.a;
import z1.m;

public class MaterialTextView
extends AppCompatTextView {
    public MaterialTextView(Context context) {
        this(context, null);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public MaterialTextView(Context context, AttributeSet attributeSet, int n3) {
        super(a.d(context, attributeSet, n3, 0), attributeSet, n3);
        this.v(attributeSet, n3, 0);
    }

    public static boolean t(Context context) {
        return b.b(context, z1.c.textAppearanceLineHeightEnabled, true);
    }

    public static int u(Resources.Theme theme, AttributeSet attributeSet, int n3, int n4) {
        theme = theme.obtainStyledAttributes(attributeSet, m.MaterialTextView, n3, n4);
        n3 = theme.getResourceId(m.MaterialTextView_android_textAppearance, -1);
        theme.recycle();
        return n3;
    }

    public static int w(Context context, TypedArray typedArray, int ... nArray) {
        int n3 = -1;
        for (int i3 = 0; i3 < nArray.length && n3 < 0; ++i3) {
            n3 = s2.c.d(context, typedArray, nArray[i3], -1);
        }
        return n3;
    }

    public static boolean x(Context context, Resources.Theme theme, AttributeSet attributeSet, int n3, int n4) {
        theme = theme.obtainStyledAttributes(attributeSet, m.MaterialTextView, n3, n4);
        n3 = MaterialTextView.w(context, (TypedArray)theme, m.MaterialTextView_android_lineHeight, m.MaterialTextView_lineHeight);
        theme.recycle();
        return n3 != -1;
    }

    public final void s(Resources.Theme theme, int n3) {
        theme = theme.obtainStyledAttributes(n3, m.MaterialTextAppearance);
        n3 = MaterialTextView.w(this.getContext(), (TypedArray)theme, m.MaterialTextAppearance_android_lineHeight, m.MaterialTextAppearance_lineHeight);
        theme.recycle();
        if (n3 >= 0) {
            this.setLineHeight(n3);
        }
    }

    @Override
    public void setTextAppearance(Context context, int n3) {
        super.setTextAppearance(context, n3);
        if (MaterialTextView.t(context)) {
            this.s(context.getTheme(), n3);
        }
    }

    public final void v(AttributeSet attributeSet, int n3, int n4) {
        Resources.Theme theme;
        Context context = this.getContext();
        if (MaterialTextView.t(context) && !MaterialTextView.x(context, theme = context.getTheme(), attributeSet, n3, n4) && (n3 = MaterialTextView.u(theme, attributeSet, n3, n4)) != -1) {
            this.s(theme, n3);
        }
    }
}

