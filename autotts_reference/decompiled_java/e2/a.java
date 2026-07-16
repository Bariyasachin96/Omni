/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package e2;

import android.animation.ValueAnimator;
import e2.b;

public final class a
implements ValueAnimator.AnimatorUpdateListener {
    public final b a;

    public /* synthetic */ a(b b3) {
        this.a = b3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        b.a(this.a, valueAnimator);
    }
}

