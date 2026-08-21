/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package o0;

import android.animation.ValueAnimator;
import android.view.View;
import o0.h1;
import o0.k1;

public final class g1
implements ValueAnimator.AnimatorUpdateListener {
    public final k1 a;
    public final View b;

    public /* synthetic */ g1(k1 k12, View view) {
        this.a = k12;
        this.b = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        h1.a(this.a, this.b, valueAnimator);
    }
}

