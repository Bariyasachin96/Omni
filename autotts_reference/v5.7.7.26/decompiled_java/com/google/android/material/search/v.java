/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.search;

import android.animation.ValueAnimator;
import com.google.android.material.search.z;

public final class v
implements ValueAnimator.AnimatorUpdateListener {
    public final z a;

    public /* synthetic */ v(z z3) {
        this.a = z3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        z.b(this.a, valueAnimator);
    }
}

