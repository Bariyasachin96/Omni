/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Color
 *  android.view.View
 */
package com.google.android.material.navigation;

import a2.a;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.b;

public abstract class c {
    public static final int a = Color.alpha((int)-1728053248);

    public static /* synthetic */ void a(DrawerLayout drawerLayout, ValueAnimator valueAnimator) {
        drawerLayout.setScrimColor(g0.a.k(-1728053248, a2.a.c(a, 0, valueAnimator.getAnimatedFraction())));
    }

    public static Animator.AnimatorListener b(DrawerLayout drawerLayout, View view) {
        return new AnimatorListenerAdapter(drawerLayout, view){
            public final DrawerLayout a;
            public final View b;
            {
                this.a = drawerLayout;
                this.b = view;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.e(this.b, false);
                this.a.setScrimColor(-1728053248);
            }
        };
    }

    public static ValueAnimator.AnimatorUpdateListener c(DrawerLayout drawerLayout) {
        return new b(drawerLayout);
    }
}

