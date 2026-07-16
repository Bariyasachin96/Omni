/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package p2;

import android.animation.ValueAnimator;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import p2.i;

public final class h
implements ValueAnimator.AnimatorUpdateListener {
    public final ClippableRoundedCornerLayout a;

    public /* synthetic */ h(ClippableRoundedCornerLayout clippableRoundedCornerLayout) {
        this.a = clippableRoundedCornerLayout;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        i.g(this.a, valueAnimator);
    }
}

