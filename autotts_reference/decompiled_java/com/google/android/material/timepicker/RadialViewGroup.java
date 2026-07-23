/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.os.Handler
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.b;
import com.google.android.material.timepicker.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import v2.i;
import v2.m;
import z1.g;

class RadialViewGroup
extends ConstraintLayout {
    public final Runnable B;
    public int C;
    public i D;

    public RadialViewGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RadialViewGroup(Context context, AttributeSet attributeSet, int n3) {
        super(context, attributeSet, n3);
        LayoutInflater.from((Context)context).inflate(z1.i.material_radial_view_group, (ViewGroup)this);
        this.setBackground(this.D());
        context = context.obtainStyledAttributes(attributeSet, z1.m.RadialViewGroup, n3, 0);
        this.C = context.getDimensionPixelSize(z1.m.RadialViewGroup_materialCircleRadius, 0);
        this.B = new c(this);
        context.recycle();
    }

    public static boolean H(View view) {
        return "skip".equals(view.getTag());
    }

    public final void C(List list, b b3, int n3) {
        Iterator iterator = list.iterator();
        float f3 = 0.0f;
        while (iterator.hasNext()) {
            b3.r(((View)iterator.next()).getId(), z1.g.circle_center, n3, f3);
            f3 += 360.0f / (float)list.size();
        }
    }

    public final Drawable D() {
        i i3;
        this.D = i3 = new i();
        i3.f0(new m(0.5f));
        this.D.i0(ColorStateList.valueOf((int)-1));
        return this.D;
    }

    public int E(int n3) {
        if (n3 == 2) {
            return Math.round((float)this.C * 0.66f);
        }
        return this.C;
    }

    public int F() {
        return this.C;
    }

    public void G(int n3) {
        this.C = n3;
        this.I();
    }

    public void I() {
        b b3 = new b();
        b3.o(this);
        HashMap hashMap = new HashMap();
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            View view = this.getChildAt(i3);
            if (view.getId() == z1.g.circle_center || RadialViewGroup.H(view)) continue;
            Integer object = (Integer)view.getTag(z1.g.material_clock_level);
            Object object2 = object;
            if (object == null) {
                object2 = 1;
            }
            if (!hashMap.containsKey(object2)) {
                hashMap.put(object2, new ArrayList());
            }
            ((List)hashMap.get(object2)).add(view);
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            this.C((List)entry.getValue(), b3, this.E((Integer)entry.getKey()));
        }
        b3.i(this);
    }

    public final void J() {
        Handler handler = this.getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.B);
            handler.post(this.B);
        }
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n3, layoutParams);
        if (view.getId() == -1) {
            view.setId(View.generateViewId());
        }
        this.J();
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.I();
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.J();
    }

    public void setBackgroundColor(int n3) {
        this.D.i0(ColorStateList.valueOf((int)n3));
    }
}

