/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 */
package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.material.internal.z;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import r2.b;
import s2.c;
import z1.e;
import z1.m;

public final class CircularProgressIndicatorSpec
extends b {
    public int o;
    public int p;
    public int q;
    public int r;
    public boolean s;

    public CircularProgressIndicatorSpec(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.circularProgressIndicatorStyle);
    }

    public CircularProgressIndicatorSpec(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, CircularProgressIndicator.s);
    }

    public CircularProgressIndicatorSpec(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(context, attributeSet, n3, n4);
        int n5 = context.getResources().getDimensionPixelSize(z1.e.mtrl_progress_circular_size_medium);
        int n6 = context.getResources().getDimensionPixelSize(z1.e.mtrl_progress_circular_inset_medium);
        attributeSet = z.i(context, attributeSet, z1.m.CircularProgressIndicator, n3, n4, new int[0]);
        this.o = attributeSet.getInt(z1.m.CircularProgressIndicator_indeterminateAnimationTypeCircular, 0);
        this.p = Math.max(s2.c.d(context, (TypedArray)attributeSet, z1.m.CircularProgressIndicator_indicatorSize, n5), this.a * 2);
        this.q = s2.c.d(context, (TypedArray)attributeSet, z1.m.CircularProgressIndicator_indicatorInset, n6);
        this.r = attributeSet.getInt(z1.m.CircularProgressIndicator_indicatorDirectionCircular, 0);
        this.s = attributeSet.getBoolean(z1.m.CircularProgressIndicator_indeterminateTrackVisible, true);
        attributeSet.recycle();
        this.h();
    }
}

