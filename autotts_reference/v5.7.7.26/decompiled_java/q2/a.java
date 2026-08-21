/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package q2;

import android.animation.ValueAnimator;
import android.view.View;
import q2.b;

public final class a
implements ValueAnimator.AnimatorUpdateListener {
    public final View a;

    public /* synthetic */ a(View view) {
        this.a = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        b.o0(this.a, valueAnimator);
    }
}

