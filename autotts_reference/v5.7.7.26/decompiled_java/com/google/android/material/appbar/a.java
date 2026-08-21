/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.res.ColorStateList
 */
package com.google.android.material.appbar;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import com.google.android.material.appbar.AppBarLayout;
import v2.i;

public final class a
implements ValueAnimator.AnimatorUpdateListener {
    public final AppBarLayout a;
    public final ColorStateList b;
    public final i c;
    public final Integer d;

    public /* synthetic */ a(AppBarLayout appBarLayout, ColorStateList colorStateList, i i3, Integer n3) {
        this.a = appBarLayout;
        this.b = colorStateList;
        this.c = i3;
        this.d = n3;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        AppBarLayout.b(this.a, this.b, this.c, this.d, valueAnimator);
    }
}

