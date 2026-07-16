/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.LayerDrawable
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.materialswitch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.m0;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import g0.a;
import j2.d;
import z1.c;
import z1.l;
import z1.m;

public class MaterialSwitch
extends SwitchCompat {
    public static final int m0 = z1.l.Widget_Material3_CompoundButton_MaterialSwitch;
    public static final int[] n0 = new int[]{z1.c.state_with_icon};
    public Drawable W;
    public Drawable a0;
    public int b0;
    public Drawable c0;
    public Drawable d0;
    public ColorStateList e0;
    public ColorStateList f0;
    public PorterDuff.Mode g0;
    public ColorStateList h0;
    public ColorStateList i0;
    public PorterDuff.Mode j0;
    public int[] k0;
    public int[] l0;

    public MaterialSwitch(Context context) {
        this(context, null);
    }

    public MaterialSwitch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialSwitchStyle);
    }

    public MaterialSwitch(Context object, AttributeSet attributeSet, int n3) {
        int n4 = m0;
        super(y2.a.d((Context)object, attributeSet, n3, n4), attributeSet, n3);
        this.b0 = -1;
        object = this.getContext();
        this.W = super.getThumbDrawable();
        this.e0 = super.getThumbTintList();
        super.setThumbTintList(null);
        this.c0 = super.getTrackDrawable();
        this.h0 = super.getTrackTintList();
        super.setTrackTintList(null);
        object = com.google.android.material.internal.z.j((Context)object, attributeSet, z1.m.MaterialSwitch, n3, n4, new int[0]);
        this.a0 = ((m0)object).g(z1.m.MaterialSwitch_thumbIcon);
        this.b0 = ((m0)object).f(z1.m.MaterialSwitch_thumbIconSize, -1);
        this.f0 = ((m0)object).c(z1.m.MaterialSwitch_thumbIconTint);
        n3 = ((m0)object).k(z1.m.MaterialSwitch_thumbIconTintMode, -1);
        attributeSet = PorterDuff.Mode.SRC_IN;
        this.g0 = com.google.android.material.internal.c0.n(n3, (PorterDuff.Mode)attributeSet);
        this.d0 = ((m0)object).g(z1.m.MaterialSwitch_trackDecoration);
        this.i0 = ((m0)object).c(z1.m.MaterialSwitch_trackDecorationTint);
        this.j0 = com.google.android.material.internal.c0.n(((m0)object).k(z1.m.MaterialSwitch_trackDecorationTintMode, -1), (PorterDuff.Mode)attributeSet);
        ((m0)object).x();
        this.setEnforceSwitchWidth(false);
        this.p();
        this.q();
    }

    public static void r(Drawable drawable, ColorStateList colorStateList, int[] nArray, int[] nArray2, float f3) {
        if (drawable != null && colorStateList != null) {
            drawable.setTint(a.c(colorStateList.getColorForState(nArray, 0), colorStateList.getColorForState(nArray2, 0), f3));
        }
    }

    @Override
    public Drawable getThumbDrawable() {
        return this.W;
    }

    public Drawable getThumbIconDrawable() {
        return this.a0;
    }

    public int getThumbIconSize() {
        return this.b0;
    }

    public ColorStateList getThumbIconTintList() {
        return this.f0;
    }

    public PorterDuff.Mode getThumbIconTintMode() {
        return this.g0;
    }

    @Override
    public ColorStateList getThumbTintList() {
        return this.e0;
    }

    public Drawable getTrackDecorationDrawable() {
        return this.d0;
    }

    public ColorStateList getTrackDecorationTintList() {
        return this.i0;
    }

    public PorterDuff.Mode getTrackDecorationTintMode() {
        return this.j0;
    }

    @Override
    public Drawable getTrackDrawable() {
        return this.c0;
    }

    @Override
    public ColorStateList getTrackTintList() {
        return this.h0;
    }

    public void invalidate() {
        this.s();
        super.invalidate();
    }

    @Override
    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 1);
        if (this.a0 != null) {
            View.mergeDrawableStates((int[])nArray, (int[])n0);
        }
        this.k0 = j2.d.j(nArray);
        this.l0 = j2.d.f(nArray);
        return nArray;
    }

    public final void p() {
        this.W = j2.d.c(this.W, this.e0, this.getThumbTintMode());
        this.a0 = j2.d.c(this.a0, this.f0, this.g0);
        this.s();
        Drawable drawable = this.W;
        Drawable drawable2 = this.a0;
        int n3 = this.b0;
        super.setThumbDrawable(j2.d.b(drawable, drawable2, n3, n3));
        this.refreshDrawableState();
    }

    public final void q() {
        this.c0 = j2.d.c(this.c0, this.h0, this.getTrackTintMode());
        this.d0 = j2.d.c(this.d0, this.i0, this.j0);
        this.s();
        Drawable drawable = this.c0;
        if (drawable != null && this.d0 != null) {
            drawable = new LayerDrawable(new Drawable[]{this.c0, this.d0});
        } else if (drawable == null) {
            drawable = this.d0;
        }
        if (drawable != null) {
            this.setSwitchMinWidth(drawable.getIntrinsicWidth());
        }
        super.setTrackDrawable(drawable);
    }

    public final void s() {
        if (this.e0 != null || this.f0 != null || this.h0 != null || this.i0 != null) {
            float f3 = this.getThumbPosition();
            ColorStateList colorStateList = this.e0;
            if (colorStateList != null) {
                MaterialSwitch.r(this.W, colorStateList, this.k0, this.l0, f3);
            }
            if ((colorStateList = this.f0) != null) {
                MaterialSwitch.r(this.a0, colorStateList, this.k0, this.l0, f3);
            }
            if ((colorStateList = this.h0) != null) {
                MaterialSwitch.r(this.c0, colorStateList, this.k0, this.l0, f3);
            }
            if ((colorStateList = this.i0) != null) {
                MaterialSwitch.r(this.d0, colorStateList, this.k0, this.l0, f3);
            }
        }
    }

    @Override
    public void setThumbDrawable(Drawable drawable) {
        this.W = drawable;
        this.p();
    }

    public void setThumbIconDrawable(Drawable drawable) {
        this.a0 = drawable;
        this.p();
    }

    public void setThumbIconResource(int n3) {
        this.setThumbIconDrawable(d.a.b(this.getContext(), n3));
    }

    public void setThumbIconSize(int n3) {
        if (this.b0 != n3) {
            this.b0 = n3;
            this.p();
        }
    }

    public void setThumbIconTintList(ColorStateList colorStateList) {
        this.f0 = colorStateList;
        this.p();
    }

    public void setThumbIconTintMode(PorterDuff.Mode mode) {
        this.g0 = mode;
        this.p();
    }

    @Override
    public void setThumbTintList(ColorStateList colorStateList) {
        this.e0 = colorStateList;
        this.p();
    }

    @Override
    public void setThumbTintMode(PorterDuff.Mode mode) {
        super.setThumbTintMode(mode);
        this.p();
    }

    public void setTrackDecorationDrawable(Drawable drawable) {
        this.d0 = drawable;
        this.q();
    }

    public void setTrackDecorationResource(int n3) {
        this.setTrackDecorationDrawable(d.a.b(this.getContext(), n3));
    }

    public void setTrackDecorationTintList(ColorStateList colorStateList) {
        this.i0 = colorStateList;
        this.q();
    }

    public void setTrackDecorationTintMode(PorterDuff.Mode mode) {
        this.j0 = mode;
        this.q();
    }

    @Override
    public void setTrackDrawable(Drawable drawable) {
        this.c0 = drawable;
        this.q();
    }

    @Override
    public void setTrackTintList(ColorStateList colorStateList) {
        this.h0 = colorStateList;
        this.q();
    }

    @Override
    public void setTrackTintMode(PorterDuff.Mode mode) {
        super.setTrackTintMode(mode);
        this.q();
    }
}

