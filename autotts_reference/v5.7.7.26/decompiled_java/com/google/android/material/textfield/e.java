/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import com.google.android.material.textfield.f;

public final class e
implements ValueAnimator.AnimatorUpdateListener {
    public final f a;

    public /* synthetic */ e(f f3) {
        this.a = f3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        f.y(this.a, valueAnimator);
    }
}

