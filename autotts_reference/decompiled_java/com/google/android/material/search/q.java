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

public final class q
implements ValueAnimator.AnimatorUpdateListener {
    public final z a;
    public final Rect b;

    public /* synthetic */ q(z z3, Rect rect) {
        this.a = z3;
        this.b = rect;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        z.c(this.a, this.b, valueAnimator);
    }
}

