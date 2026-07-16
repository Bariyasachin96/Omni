/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.material.internal.z;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import r2.b;
import z1.c;
import z1.m;

public final class LinearProgressIndicatorSpec
extends b {
    public int o;
    public int p;
    public boolean q;
    public int r;
    public Integer s;
    public int t;
    public float u;
    public boolean v;
    public boolean w;

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, LinearProgressIndicator.s);
    }

    public LinearProgressIndicatorSpec(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(context, attributeSet, n3, n4);
        int[] nArray = z1.m.LinearProgressIndicator;
        n3 = z1.c.linearProgressIndicatorStyle;
        n4 = LinearProgressIndicator.s;
        boolean bl = false;
        attributeSet = z.i(context, attributeSet, nArray, n3, n4, new int[0]);
        this.o = attributeSet.getInt(z1.m.LinearProgressIndicator_indeterminateAnimationType, 1);
        this.p = attributeSet.getInt(z1.m.LinearProgressIndicator_indicatorDirectionLinear, 0);
        this.r = Math.min(attributeSet.getDimensionPixelSize(z1.m.LinearProgressIndicator_trackStopIndicatorSize, 0), this.a);
        n3 = z1.m.LinearProgressIndicator_trackStopIndicatorPadding;
        if (attributeSet.hasValue(n3)) {
            this.s = attributeSet.getDimensionPixelSize(n3, 0);
        }
        if ((context = attributeSet.peekValue(z1.m.LinearProgressIndicator_trackInnerCornerRadius)) != null) {
            n3 = context.type;
            if (n3 == 5) {
                this.t = Math.min(TypedValue.complexToDimensionPixelSize((int)context.data, (DisplayMetrics)attributeSet.getResources().getDisplayMetrics()), this.a / 2);
                this.v = false;
                this.w = true;
            } else if (n3 == 6) {
                this.u = Math.min(context.getFraction(1.0f, 1.0f), 0.5f);
                this.v = true;
                this.w = true;
            }
        }
        attributeSet.recycle();
        this.h();
        if (this.p == 1) {
            bl = true;
        }
        this.q = bl;
    }

    @Override
    public boolean g() {
        return super.g() && this.i() == this.a();
    }

    @Override
    public void h() {
        super.h();
        if (this.r >= 0) {
            if (this.o == 0) {
                if (this.a() <= 0 && (!this.w || this.i() <= 0) || this.i != 0) {
                    if (this.e.length < 3) {
                        throw new IllegalArgumentException("Contiguous indeterminate animation must be used with 3 or more indicator colors.");
                    }
                } else {
                    throw new IllegalArgumentException("Rounded corners without gap are not supported in contiguous indeterminate animation.");
                }
            }
            return;
        }
        throw new IllegalArgumentException("Stop indicator size must be >= 0.");
    }

    public int i() {
        if (!this.w) {
            return this.a();
        }
        if (this.v) {
            return (int)((float)this.a * this.u);
        }
        return this.t;
    }
}

