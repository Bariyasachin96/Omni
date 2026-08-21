/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package r2;

import android.animation.ValueAnimator;
import r2.b;
import r2.h;

public final class f
implements ValueAnimator.AnimatorUpdateListener {
    public final h a;
    public final b b;

    public /* synthetic */ f(h h3, b b3) {
        this.a = h3;
        this.b = b3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        h.v(this.a, this.b, valueAnimator);
    }
}

