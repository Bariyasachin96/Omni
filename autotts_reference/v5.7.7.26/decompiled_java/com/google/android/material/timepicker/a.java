/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.timepicker;

import android.animation.ValueAnimator;
import com.google.android.material.timepicker.ClockHandView;

public final class a
implements ValueAnimator.AnimatorUpdateListener {
    public final ClockHandView a;

    public /* synthetic */ a(ClockHandView clockHandView) {
        this.a = clockHandView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ClockHandView.a(this.a, valueAnimator);
    }
}

