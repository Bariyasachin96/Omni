/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 */
package p2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import d1.b;
import p2.a;
import z1.e;

public class f
extends a {
    public final float g;
    public final float h;

    public f(View view) {
        super(view);
        view = view.getResources();
        this.g = view.getDimension(z1.e.m3_back_progress_bottom_container_max_scale_x_distance);
        this.h = view.getDimension(z1.e.m3_back_progress_bottom_container_max_scale_y_distance);
    }

    public void f() {
        if (super.b() == null) {
            return;
        }
        Animator animator = this.g();
        animator.setDuration((long)this.e);
        animator.start();
    }

    public final Animator g() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat((Object)this.b, (Property)View.SCALE_X, (float[])new float[]{1.0f}), ObjectAnimator.ofFloat((Object)this.b, (Property)View.SCALE_Y, (float[])new float[]{1.0f})});
        View view = this.b;
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            for (int i3 = 0; i3 < view.getChildCount(); ++i3) {
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat((Object)view.getChildAt(i3), (Property)View.SCALE_Y, (float[])new float[]{1.0f})});
            }
        }
        animatorSet.setInterpolator((TimeInterpolator)new b());
        return animatorSet;
    }

    public void h(androidx.activity.b b3, Animator.AnimatorListener animatorListener) {
        float f3 = this.b.getHeight();
        float f4 = this.b.getScaleY();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this.b, (Property)View.TRANSLATION_Y, (float[])new float[]{f3 * f4});
        objectAnimator.setInterpolator((TimeInterpolator)new b());
        objectAnimator.setDuration((long)a2.a.c(this.c, this.d, b3.a()));
        objectAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final f a;
            {
                this.a = f3;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.b.setTranslationY(0.0f);
                this.a.k(0.0f);
            }
        });
        if (animatorListener != null) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
    }

    public void i(androidx.activity.b b3, Animator.AnimatorListener animatorListener) {
        Animator animator = this.g();
        animator.setDuration((long)a2.a.c(this.c, this.d, b3.a()));
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.start();
    }

    public void j(androidx.activity.b b3) {
        super.d(b3);
    }

    public void k(float f3) {
        float f4 = this.a(f3);
        float f5 = this.b.getWidth();
        f3 = this.b.getHeight();
        if (!(f5 <= 0.0f) && !(f3 <= 0.0f)) {
            float f6 = this.g / f5;
            f5 = this.h / f3;
            f6 = a2.a.a(0.0f, f6, f4);
            f5 = a2.a.a(0.0f, f5, f4);
            f4 = 1.0f - f6;
            f5 = 1.0f - f5;
            if (!Float.isNaN(f4) && !Float.isNaN(f5)) {
                this.b.setScaleX(f4);
                this.b.setPivotY(f3);
                this.b.setScaleY(f5);
                View view = this.b;
                if (view instanceof ViewGroup) {
                    view = (ViewGroup)view;
                    for (int i3 = 0; i3 < view.getChildCount(); ++i3) {
                        View view2 = view.getChildAt(i3);
                        view2.setPivotY((float)(-view2.getTop()));
                        f3 = f5 != 0.0f ? f4 / f5 : 1.0f;
                        view2.setScaleY(f3);
                    }
                }
            }
        }
    }

    public void l(androidx.activity.b b3) {
        if (super.e(b3) == null) {
            return;
        }
        this.k(b3.a());
    }
}

