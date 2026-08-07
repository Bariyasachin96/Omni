/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.switchmaterial;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import c.a;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import z1.c;
import z1.e;
import z1.l;
import z1.m;

public class SwitchMaterial
extends SwitchCompat {
    public static final int d0 = z1.l.Widget_MaterialComponents_CompoundButton_Switch;
    public static final int[][] e0;
    public final k2.a W;
    public ColorStateList a0;
    public ColorStateList b0;
    public boolean c0;

    static {
        int[] nArray = new int[]{16842910, -16842912};
        int[] nArray2 = new int[]{-16842910, 0x10100A0};
        int[] nArray3 = new int[]{-16842910, -16842912};
        e0 = new int[][]{{16842910, 0x10100A0}, nArray, nArray2, nArray3};
    }

    public SwitchMaterial(Context context) {
        this(context, null);
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.switchStyle);
    }

    public SwitchMaterial(Context context, AttributeSet attributeSet, int n3) {
        int n4 = d0;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        context = this.getContext();
        this.W = new k2.a(context);
        context = com.google.android.material.internal.z.i(context, attributeSet, z1.m.SwitchMaterial, n3, n4, new int[0]);
        this.c0 = context.getBoolean(z1.m.SwitchMaterial_useMaterialThemeColors, false);
        context.recycle();
    }

    private ColorStateList getMaterialThemeColorsThumbTintList() {
        if (this.a0 == null) {
            float f3;
            int n3 = h2.a.d((View)this, z1.c.colorSurface);
            int n4 = h2.a.d((View)this, a.colorControlActivated);
            float f4 = f3 = this.getResources().getDimension(z1.e.mtrl_switch_thumb_elevation);
            if (this.W.e()) {
                f4 = f3 + com.google.android.material.internal.c0.k((View)this);
            }
            int n5 = this.W.c(n3, f4);
            int[][] nArray = e0;
            int[] nArray2 = new int[nArray.length];
            nArray2[0] = h2.a.j(n3, n4, 1.0f);
            nArray2[1] = n5;
            nArray2[2] = h2.a.j(n3, n4, 0.38f);
            nArray2[3] = n5;
            this.a0 = new ColorStateList(nArray, nArray2);
        }
        return this.a0;
    }

    private ColorStateList getMaterialThemeColorsTrackTintList() {
        if (this.b0 == null) {
            int[][] nArray = e0;
            int[] nArray2 = new int[nArray.length];
            int n3 = h2.a.d((View)this, z1.c.colorSurface);
            int n4 = h2.a.d((View)this, a.colorControlActivated);
            int n5 = h2.a.d((View)this, z1.c.colorOnSurface);
            nArray2[0] = h2.a.j(n3, n4, 0.54f);
            nArray2[1] = h2.a.j(n3, n5, 0.32f);
            nArray2[2] = h2.a.j(n3, n4, 0.12f);
            nArray2[3] = h2.a.j(n3, n5, 0.12f);
            this.b0 = new ColorStateList(nArray, nArray2);
        }
        return this.b0;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.c0 && this.getThumbTintList() == null) {
            this.setThumbTintList(this.getMaterialThemeColorsThumbTintList());
        }
        if (this.c0 && this.getTrackTintList() == null) {
            this.setTrackTintList(this.getMaterialThemeColorsTrackTintList());
        }
    }

    public void setUseMaterialThemeColors(boolean bl) {
        this.c0 = bl;
        if (bl) {
            this.setThumbTintList(this.getMaterialThemeColorsThumbTintList());
            this.setTrackTintList(this.getMaterialThemeColorsTrackTintList());
            return;
        }
        this.setThumbTintList(null);
        this.setTrackTintList(null);
    }
}

