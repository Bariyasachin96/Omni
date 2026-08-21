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
import e.b;

public final class s
implements ValueAnimator.AnimatorUpdateListener {
    public final b a;

    public /* synthetic */ s(b b3) {
        this.a = b3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        z.h(this.a, valueAnimator);
    }
}

