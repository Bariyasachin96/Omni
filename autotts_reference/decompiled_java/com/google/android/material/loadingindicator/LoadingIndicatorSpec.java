/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 */
package com.google.android.material.loadingindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import c.a;
import com.google.android.material.internal.z;
import com.google.android.material.loadingindicator.LoadingIndicator;
import z1.c;
import z1.e;
import z1.m;

public final class LoadingIndicatorSpec {
    public boolean a = false;
    public int b;
    public int c;
    public int d;
    public int[] e = new int[0];
    public int f;

    public LoadingIndicatorSpec(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.loadingIndicatorStyle);
    }

    public LoadingIndicatorSpec(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, LoadingIndicator.e);
    }

    public LoadingIndicatorSpec(Context context, AttributeSet attributeSet, int n3, int n4) {
        int n5 = context.getResources().getDimensionPixelSize(z1.e.m3_loading_indicator_shape_size);
        int n6 = context.getResources().getDimensionPixelSize(z1.e.m3_loading_indicator_container_size);
        attributeSet = z.i(context, attributeSet, m.LoadingIndicator, n3, n4, new int[0]);
        this.b = attributeSet.getDimensionPixelSize(m.LoadingIndicator_indicatorSize, n5);
        this.c = attributeSet.getDimensionPixelSize(m.LoadingIndicator_containerWidth, n6);
        this.d = attributeSet.getDimensionPixelSize(m.LoadingIndicator_containerHeight, n6);
        this.a(context, (TypedArray)attributeSet);
        this.f = attributeSet.getColor(m.LoadingIndicator_containerColor, 0);
        attributeSet.recycle();
    }

    public final void a(Context object, TypedArray typedArray) {
        int n3 = m.LoadingIndicator_indicatorColor;
        if (!typedArray.hasValue(n3)) {
            this.e = new int[]{h2.a.b(object, c.a.colorPrimary, -1)};
            return;
        }
        if (typedArray.peekValue((int)n3).type != 1) {
            this.e = new int[]{typedArray.getColor(n3, -1)};
            return;
        }
        object = object.getResources().getIntArray(typedArray.getResourceId(n3, -1));
        this.e = (int[])object;
        if (((Context)object).length != 0) {
            return;
        }
        throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
    }
}

