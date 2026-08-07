/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.CompoundButton
 */
package com.google.android.material.radiobutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.c;
import c.a;
import com.google.android.material.internal.z;
import z1.l;
import z1.m;

public class MaterialRadioButton
extends AppCompatRadioButton {
    public static final int i = l.Widget_MaterialComponents_CompoundButton_RadioButton;
    public static final int[][] j;
    public ColorStateList g;
    public boolean h;

    static {
        int[] nArray = new int[]{16842910, 0x10100A0};
        int[] nArray2 = new int[]{-16842910, -16842912};
        j = new int[][]{nArray, {16842910, -16842912}, {-16842910, 0x10100A0}, nArray2};
    }

    public MaterialRadioButton(Context context) {
        this(context, null);
    }

    public MaterialRadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.radioButtonStyle);
    }

    public MaterialRadioButton(Context context, AttributeSet attributeSet, int n3) {
        int n4 = i;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        context = this.getContext();
        attributeSet = z.i(context, attributeSet, m.MaterialRadioButton, n3, n4, new int[0]);
        n3 = m.MaterialRadioButton_buttonTint;
        if (attributeSet.hasValue(n3)) {
            androidx.core.widget.c.d((CompoundButton)this, s2.c.a(context, (TypedArray)attributeSet, n3));
        }
        this.h = attributeSet.getBoolean(m.MaterialRadioButton_useMaterialThemeColors, false);
        attributeSet.recycle();
    }

    private ColorStateList getMaterialThemeColorsTintList() {
        if (this.g == null) {
            int n3 = h2.a.d((View)this, a.colorControlActivated);
            int n4 = h2.a.d((View)this, z1.c.colorOnSurface);
            int n5 = h2.a.d((View)this, z1.c.colorSurface);
            int[][] nArray = j;
            int[] nArray2 = new int[nArray.length];
            nArray2[0] = h2.a.j(n5, n3, 1.0f);
            nArray2[1] = h2.a.j(n5, n4, 0.54f);
            nArray2[2] = h2.a.j(n5, n4, 0.38f);
            nArray2[3] = h2.a.j(n5, n4, 0.38f);
            this.g = new ColorStateList(nArray, nArray2);
        }
        return this.g;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.h && androidx.core.widget.c.b((CompoundButton)this) == null) {
            this.setUseMaterialThemeColors(true);
        }
    }

    public void setUseMaterialThemeColors(boolean bl) {
        this.h = bl;
        if (bl) {
            androidx.core.widget.c.d((CompoundButton)this, this.getMaterialThemeColorsTintList());
            return;
        }
        androidx.core.widget.c.d((CompoundButton)this, null);
    }
}

