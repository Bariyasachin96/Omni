/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.widget.ImageButton
 */
package com.google.android.material.search;

import android.animation.ValueAnimator;
import android.widget.ImageButton;
import com.google.android.material.search.z;

public final class y
implements ValueAnimator.AnimatorUpdateListener {
    public final ImageButton a;

    public /* synthetic */ y(ImageButton imageButton) {
        this.a = imageButton;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        z.f(this.a, valueAnimator);
    }
}

