/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import com.google.android.material.internal.h;

public class g
implements ValueAnimator.AnimatorUpdateListener {
    public final View a;
    public final View b;
    public final float[] c;

    public g(View view, View view2) {
        this.a = view;
        this.b = view2;
        this.c = new float[2];
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        h.a(((Float)valueAnimator.getAnimatedValue()).floatValue(), this.c);
        valueAnimator = this.a;
        if (valueAnimator != null) {
            valueAnimator.setAlpha(this.c[0]);
        }
        if ((valueAnimator = this.b) != null) {
            valueAnimator.setAlpha(this.c[1]);
        }
    }
}

