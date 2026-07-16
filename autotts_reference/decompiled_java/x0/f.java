/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator$DurationScaleChangeListener
 */
package x0;

import android.animation.ValueAnimator;
import x0.c;

public final class f
implements ValueAnimator.DurationScaleChangeListener {
    public final c.d a;

    public /* synthetic */ f(c.d d3) {
        this.a = d3;
    }

    public final void onChanged(float f3) {
        c.d.c(this.a, f3);
    }
}

