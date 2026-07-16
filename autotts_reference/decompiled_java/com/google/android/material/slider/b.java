/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.slider;

import android.animation.ValueAnimator;
import com.google.android.material.slider.BaseSlider;

public final class b
implements ValueAnimator.AnimatorUpdateListener {
    public final BaseSlider a;

    public /* synthetic */ b(BaseSlider baseSlider) {
        this.a = baseSlider;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        BaseSlider.b(this.a, valueAnimator);
    }
}

