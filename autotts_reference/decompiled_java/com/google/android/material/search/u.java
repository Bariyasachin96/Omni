/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.search;

import android.animation.ValueAnimator;
import com.google.android.material.internal.f;
import com.google.android.material.search.z;

public final class u
implements ValueAnimator.AnimatorUpdateListener {
    public final f a;

    public /* synthetic */ u(f f3) {
        this.a = f3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        z.e(this.a, valueAnimator);
    }
}

