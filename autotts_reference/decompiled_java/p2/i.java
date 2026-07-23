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
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Rect
 *  android.os.Build$VERSION
 *  android.util.DisplayMetrics
 *  android.util.Property
 *  android.view.RoundedCorner
 *  android.view.View
 *  android.view.WindowInsets
 */
package p2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Property;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import androidx.activity.b;
import com.google.android.material.internal.ClippableRoundedCornerLayout;
import com.google.android.material.internal.c0;
import p2.a;
import p2.g;
import p2.h;
import z1.e;

public class i
extends a {
    public final float g;
    public final float h;
    public float i;
    public Rect j;
    public Rect k;
    public float[] l;

    public i(View view) {
        super(view);
        view = view.getResources();
        this.g = view.getDimension(z1.e.m3_back_progress_main_container_min_edge_gap);
        this.h = view.getDimension(z1.e.m3_back_progress_main_container_max_translation_y);
    }

    public static /* synthetic */ Object f(float f3, Object object, Object object2) {
        return p2.i.s((float[])object, (float[])object2, f3);
    }

    public static /* synthetic */ void g(ClippableRoundedCornerLayout clippableRoundedCornerLayout, ValueAnimator valueAnimator) {
        clippableRoundedCornerLayout.e((float[])valueAnimator.getAnimatedValue());
    }

    public static float[] r(float[] fArray, float f3, float f4) {
        return new float[]{a2.a.a(fArray[0], f3, f4), a2.a.a(fArray[1], f3, f4), a2.a.a(fArray[2], f3, f4), a2.a.a(fArray[3], f3, f4), a2.a.a(fArray[4], f3, f4), a2.a.a(fArray[5], f3, f4), a2.a.a(fArray[6], f3, f4), a2.a.a(fArray[7], f3, f4)};
    }

    public static float[] s(float[] fArray, float[] fArray2, float f3) {
        return new float[]{a2.a.a(fArray[0], fArray2[0], f3), a2.a.a(fArray[1], fArray2[1], f3), a2.a.a(fArray[2], fArray2[2], f3), a2.a.a(fArray[3], fArray2[3], f3), a2.a.a(fArray[4], fArray2[4], f3), a2.a.a(fArray[5], fArray2[5], f3), a2.a.a(fArray[6], fArray2[6], f3), a2.a.a(fArray[7], fArray2[7], f3)};
    }

    public final float[] h() {
        WindowInsets windowInsets;
        if (Build.VERSION.SDK_INT >= 31 && (windowInsets = this.b.getRootWindowInsets()) != null) {
            Object object = this.b.getResources().getDisplayMetrics();
            int n3 = object.widthPixels;
            int n4 = object.heightPixels;
            object = new int[2];
            this.b.getLocationOnScreen((int[])object);
            DisplayMetrics displayMetrics = object[0];
            Object object2 = object[1];
            int n5 = this.b.getWidth();
            int n6 = this.b.getHeight();
            int n7 = displayMetrics == false && object2 == false ? this.q(windowInsets, 0) : 0;
            int n8 = n5 + displayMetrics;
            n5 = n8 >= n3 && object2 == false ? this.q(windowInsets, 1) : 0;
            n3 = n8 >= n3 && object2 + n6 >= n4 ? this.q(windowInsets, 2) : 0;
            object2 = displayMetrics == false && object2 + n6 >= n4 ? (Object)this.q(windowInsets, 3) : (Object)false;
            float f3 = n7;
            float f4 = n5;
            float f5 = n3;
            float f6 = (float)object2;
            return new float[]{f3, f3, f4, f4, f5, f5, f6, f6};
        }
        return new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    public void i(View view) {
        if (super.b() == null) {
            return;
        }
        view = this.l(view);
        View view2 = this.b;
        if (view2 instanceof ClippableRoundedCornerLayout) {
            view.playTogether(new Animator[]{this.k((ClippableRoundedCornerLayout)view2)});
        }
        view.setDuration((long)this.e);
        view.start();
        this.t();
    }

    public void j() {
        this.l = null;
    }

    public final ValueAnimator k(ClippableRoundedCornerLayout clippableRoundedCornerLayout) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject((TypeEvaluator)new g(), (Object[])new Object[]{clippableRoundedCornerLayout.getCornerRadii(), this.n()});
        valueAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new h(clippableRoundedCornerLayout));
        return valueAnimator;
    }

    public final AnimatorSet l(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat((Object)this.b, (Property)View.SCALE_X, (float[])new float[]{1.0f}), ObjectAnimator.ofFloat((Object)this.b, (Property)View.SCALE_Y, (float[])new float[]{1.0f}), ObjectAnimator.ofFloat((Object)this.b, (Property)View.TRANSLATION_X, (float[])new float[]{0.0f}), ObjectAnimator.ofFloat((Object)this.b, (Property)View.TRANSLATION_Y, (float[])new float[]{0.0f})});
        animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, view){
            public final View a;
            public final i b;
            {
                this.b = i3;
                this.a = view;
            }

            public void onAnimationEnd(Animator animator) {
                animator = this.a;
                if (animator != null) {
                    animator.setVisibility(0);
                }
            }
        });
        return animatorSet;
    }

    public void m(long l3, View view) {
        view = this.l(view);
        view.setDuration(l3);
        view.start();
        this.t();
    }

    public float[] n() {
        if (this.l == null) {
            this.l = this.h();
        }
        return this.l;
    }

    public Rect o() {
        return this.k;
    }

    public Rect p() {
        return this.j;
    }

    public final int q(WindowInsets windowInsets, int n3) {
        if ((windowInsets = c2.a.a(windowInsets, n3)) != null) {
            return c2.b.a((RoundedCorner)windowInsets);
        }
        return 0;
    }

    public final void t() {
        this.i = 0.0f;
        this.j = null;
        this.k = null;
    }

    public void u(float f3, View view) {
        this.j = c0.c(this.b);
        if (view != null) {
            this.k = c0.b(this.b, view);
        }
        this.i = f3;
    }

    public void v(b b3, View view) {
        super.d(b3);
        this.u(b3.c(), view);
    }

    public void w(float f3, boolean bl, float f4, float f5) {
        f3 = this.a(f3);
        float f6 = this.b.getWidth();
        float f7 = this.b.getHeight();
        if (!(f6 <= 0.0f) && !(f7 <= 0.0f)) {
            float f8 = a2.a.a(1.0f, 0.9f, f3);
            f6 = a2.a.a(0.0f, Math.max(0.0f, (f6 - 0.9f * f6) / 2.0f - this.g), f3);
            int n3 = bl ? 1 : -1;
            float f9 = Math.min(Math.max(0.0f, (f7 - f8 * f7) / 2.0f - this.g), this.h);
            f7 = Math.abs(f4 -= this.i) / f7;
            f4 = Math.signum(f4);
            f4 = a2.a.a(0.0f, f9, f7) * f4;
            if (!(Float.isNaN(f8) || Float.isNaN(f6 *= (float)n3) || Float.isNaN(f4))) {
                this.b.setScaleX(f8);
                this.b.setScaleY(f8);
                this.b.setTranslationX(f6);
                this.b.setTranslationY(f4);
                View view = this.b;
                if (view instanceof ClippableRoundedCornerLayout) {
                    ((ClippableRoundedCornerLayout)view).e(p2.i.r(this.n(), f5, f3));
                }
            }
        }
    }

    public void x(b b3, View view, float f3) {
        if (super.e(b3) == null) {
            return;
        }
        if (view != null && view.getVisibility() != 4) {
            view.setVisibility(4);
        }
        boolean bl = b3.b() == 0;
        this.w(b3.a(), bl, b3.c(), f3);
    }
}

