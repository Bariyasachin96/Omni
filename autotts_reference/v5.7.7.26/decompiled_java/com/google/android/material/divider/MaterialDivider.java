/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.google.android.material.divider;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.material.internal.z;
import e0.a;
import s2.c;
import v2.i;
import z1.e;
import z1.l;
import z1.m;

public class MaterialDivider
extends View {
    public static final int h = l.Widget_MaterialComponents_MaterialDivider;
    public final i c;
    public int d;
    public int e;
    public int f;
    public int g;

    public MaterialDivider(Context context) {
        this(context, null);
    }

    public MaterialDivider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.materialDividerStyle);
    }

    public MaterialDivider(Context context, AttributeSet attributeSet, int n3) {
        int n4 = h;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        context = this.getContext();
        this.c = new i();
        attributeSet = z.i(context, attributeSet, m.MaterialDivider, n3, n4, new int[0]);
        this.d = attributeSet.getDimensionPixelSize(m.MaterialDivider_dividerThickness, this.getResources().getDimensionPixelSize(z1.e.material_divider_thickness));
        this.f = attributeSet.getDimensionPixelOffset(m.MaterialDivider_dividerInsetStart, 0);
        this.g = attributeSet.getDimensionPixelOffset(m.MaterialDivider_dividerInsetEnd, 0);
        this.setDividerColor(s2.c.a(context, (TypedArray)attributeSet, m.MaterialDivider_dividerColor).getDefaultColor());
        attributeSet.recycle();
    }

    public int getDividerColor() {
        return this.e;
    }

    public int getDividerInsetEnd() {
        return this.g;
    }

    public int getDividerInsetStart() {
        return this.f;
    }

    public int getDividerThickness() {
        return this.d;
    }

    public void onDraw(Canvas canvas) {
        int n3;
        super.onDraw(canvas);
        int n4 = this.getLayoutDirection();
        int n5 = 1;
        if (n4 != 1) {
            n5 = 0;
        }
        n4 = n5 != 0 ? this.g : this.f;
        if (n5 != 0) {
            n5 = this.getWidth();
            n3 = this.f;
        } else {
            n5 = this.getWidth();
            n3 = this.g;
        }
        this.c.setBounds(n4, 0, n5 - n3, this.getBottom() - this.getTop());
        this.c.draw(canvas);
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        n3 = View.MeasureSpec.getMode((int)n4);
        n4 = this.getMeasuredHeight();
        if (n3 != Integer.MIN_VALUE && n3 != 0) {
            return;
        }
        int n5 = this.d;
        n3 = n4;
        if (n5 > 0) {
            n3 = n4;
            if (n4 != n5) {
                n3 = n5;
            }
        }
        this.setMeasuredDimension(this.getMeasuredWidth(), n3);
    }

    public void setDividerColor(int n3) {
        if (this.e != n3) {
            this.e = n3;
            this.c.i0(ColorStateList.valueOf((int)n3));
            this.invalidate();
        }
    }

    public void setDividerColorResource(int n3) {
        this.setDividerColor(a.b(this.getContext(), n3));
    }

    public void setDividerInsetEnd(int n3) {
        this.g = n3;
    }

    public void setDividerInsetEndResource(int n3) {
        this.setDividerInsetEnd(this.getContext().getResources().getDimensionPixelOffset(n3));
    }

    public void setDividerInsetStart(int n3) {
        this.f = n3;
    }

    public void setDividerInsetStartResource(int n3) {
        this.setDividerInsetStart(this.getContext().getResources().getDimensionPixelOffset(n3));
    }

    public void setDividerThickness(int n3) {
        if (this.d != n3) {
            this.d = n3;
            this.requestLayout();
        }
    }

    public void setDividerThicknessResource(int n3) {
        this.setDividerThickness(this.getContext().getResources().getDimensionPixelSize(n3));
    }
}

