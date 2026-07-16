/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.view.View
 *  android.view.animation.Interpolator
 */
package o0;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;
import o0.g1;
import o0.i1;
import o0.k1;

public final class h1 {
    public final WeakReference a;

    public h1(View view) {
        this.a = new WeakReference<View>(view);
    }

    public static /* synthetic */ void a(k1 k12, View view, ValueAnimator valueAnimator) {
        k12.a(view);
    }

    public h1 b(float f3) {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().alpha(f3);
        }
        return this;
    }

    public void c() {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().cancel();
        }
    }

    public long d() {
        View view = (View)this.a.get();
        if (view != null) {
            return view.animate().getDuration();
        }
        return 0L;
    }

    public h1 e(long l3) {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().setDuration(l3);
        }
        return this;
    }

    public h1 f(Interpolator interpolator) {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().setInterpolator((TimeInterpolator)interpolator);
        }
        return this;
    }

    public h1 g(i1 i12) {
        View view = (View)this.a.get();
        if (view != null) {
            this.h(view, i12);
        }
        return this;
    }

    public final void h(View view, i1 i12) {
        if (i12 != null) {
            view.animate().setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, i12, view){
                public final i1 a;
                public final View b;
                public final h1 c;
                {
                    this.c = h12;
                    this.a = i12;
                    this.b = view;
                }

                public void onAnimationCancel(Animator animator) {
                    this.a.a(this.b);
                }

                public void onAnimationEnd(Animator animator) {
                    this.a.b(this.b);
                }

                public void onAnimationStart(Animator animator) {
                    this.a.c(this.b);
                }
            });
            return;
        }
        view.animate().setListener(null);
    }

    public h1 i(long l3) {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().setStartDelay(l3);
        }
        return this;
    }

    public h1 j(k1 object) {
        View view = (View)this.a.get();
        if (view != null) {
            object = object != null ? new g1((k1)object, view) : null;
            view.animate().setUpdateListener((ValueAnimator.AnimatorUpdateListener)object);
        }
        return this;
    }

    public void k() {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().start();
        }
    }

    public h1 l(float f3) {
        View view = (View)this.a.get();
        if (view != null) {
            view.animate().translationY(f3);
        }
        return this;
    }
}

