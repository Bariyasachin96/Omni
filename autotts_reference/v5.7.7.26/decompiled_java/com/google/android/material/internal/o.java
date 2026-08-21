/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 */
package com.google.android.material.internal;

import android.animation.ValueAnimator;
import android.view.View;
import com.google.android.material.internal.k;
import com.google.android.material.internal.l;
import com.google.android.material.internal.m;
import com.google.android.material.internal.n;

public class o
implements ValueAnimator.AnimatorUpdateListener {
    public final a a;
    public final View[] b;

    public o(a a4, View ... viewArray) {
        this.a = a4;
        this.b = viewArray;
    }

    public static /* synthetic */ void a(ValueAnimator valueAnimator, View view) {
        o.h(valueAnimator, view);
    }

    public static /* synthetic */ void b(ValueAnimator valueAnimator, View view) {
        o.j(valueAnimator, view);
    }

    public static /* synthetic */ void c(ValueAnimator valueAnimator, View view) {
        o.g(valueAnimator, view);
    }

    public static /* synthetic */ void d(ValueAnimator valueAnimator, View view) {
        o.i(valueAnimator, view);
    }

    public static o e(View ... viewArray) {
        return new o(new n(), viewArray);
    }

    public static o f(View ... viewArray) {
        return new o(new m(), viewArray);
    }

    public static void g(ValueAnimator valueAnimator, View view) {
        view.setAlpha(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public static void h(ValueAnimator object, View view) {
        object = (Float)object.getAnimatedValue();
        view.setScaleX(((Float)object).floatValue());
        view.setScaleY(((Float)object).floatValue());
    }

    public static void i(ValueAnimator valueAnimator, View view) {
        view.setTranslationX(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public static void j(ValueAnimator valueAnimator, View view) {
        view.setTranslationY(((Float)valueAnimator.getAnimatedValue()).floatValue());
    }

    public static o k(View ... viewArray) {
        return new o(new k(), viewArray);
    }

    public static o l(View ... viewArray) {
        return new o(new l(), viewArray);
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        for (View view : this.b) {
            this.a.a(valueAnimator, view);
        }
    }

    public static interface a {
        public void a(ValueAnimator var1, View var2);
    }
}

