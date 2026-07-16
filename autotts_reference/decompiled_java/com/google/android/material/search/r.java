/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Rect
 */
package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.google.android.material.search.z;

public final class r
implements ValueAnimator.AnimatorUpdateListener {
    public final z a;
    public final float b;
    public final float[] c;
    public final Rect d;

    public /* synthetic */ r(z z3, float f3, float[] fArray, Rect rect) {
        this.a = z3;
        this.b = f3;
        this.c = fArray;
        this.d = rect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        z.g(this.a, this.b, this.c, this.d, valueAnimator);
    }
}

