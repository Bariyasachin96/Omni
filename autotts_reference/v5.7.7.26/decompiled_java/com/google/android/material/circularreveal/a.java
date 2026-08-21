/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.TypeEvaluator
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewAnimationUtils
 */
package com.google.android.material.circularreveal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.util.Property;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.google.android.material.circularreveal.c;

public abstract class a {
    public static Animator a(c c3, float f3, float f4, float f5) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject((Object)c3, (Property)c.c.a, (TypeEvaluator)c.b.b, (Object[])new c.e[]{new c.e(f3, f4, f5)});
        c.e e3 = c3.getRevealInfo();
        if (e3 != null) {
            float f6 = e3.c;
            c3 = ViewAnimationUtils.createCircularReveal((View)((View)c3), (int)((int)f3), (int)((int)f4), (float)f6, (float)f5);
            e3 = new AnimatorSet();
            e3.playTogether(new Animator[]{objectAnimator, c3});
            return e3;
        }
        throw new IllegalStateException("Caller must set a non-null RevealInfo before calling this.");
    }

    public static Animator.AnimatorListener b(c c3) {
        return new AnimatorListenerAdapter(c3){
            public final c a;
            {
                this.a = c3;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.a();
            }

            public void onAnimationStart(Animator animator) {
                this.a.d();
            }
        };
    }
}

