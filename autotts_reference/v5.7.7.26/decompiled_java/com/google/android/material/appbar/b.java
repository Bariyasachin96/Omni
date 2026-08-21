/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import com.google.android.material.appbar.AppBarLayout;
import v2.i;

public final class b
implements ValueAnimator.AnimatorUpdateListener {
    public final AppBarLayout a;
    public final i b;

    public /* synthetic */ b(AppBarLayout appBarLayout, i i3) {
        this.a = appBarLayout;
        this.b = i3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        AppBarLayout.a(this.a, this.b, valueAnimator);
    }
}

