/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 */
package com.google.android.material.navigation;

import android.animation.ValueAnimator;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.c;

public final class b
implements ValueAnimator.AnimatorUpdateListener {
    public final DrawerLayout a;

    public /* synthetic */ b(DrawerLayout drawerLayout) {
        this.a = drawerLayout;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        c.a(this.a, valueAnimator);
    }
}

