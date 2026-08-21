/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import com.google.android.material.textfield.p;

public final class i
implements ValueAnimator.AnimatorUpdateListener {
    public final p a;

    public /* synthetic */ i(p p3) {
        this.a = p3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        p.x(this.a, valueAnimator);
    }
}

