/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package r2;

import android.animation.ValueAnimator;
import r2.h;

public final class g
implements ValueAnimator.AnimatorUpdateListener {
    public final h a;

    public /* synthetic */ g(h h3) {
        this.a = h3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        h.w(this.a, valueAnimator);
    }
}

