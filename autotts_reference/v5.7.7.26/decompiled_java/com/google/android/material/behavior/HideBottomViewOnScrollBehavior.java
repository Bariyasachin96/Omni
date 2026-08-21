/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewPropertyAnimator
 *  android.view.accessibility.AccessibilityManager
 *  android.view.accessibility.AccessibilityManager$TouchExplorationStateChangeListener
 */
package com.google.android.material.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.app.s;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import b2.a;
import java.util.AbstractCollection;
import java.util.LinkedHashSet;
import p2.k;
import z1.c;

@Deprecated
public class HideBottomViewOnScrollBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    public static final int o = z1.c.motionDurationLong2;
    public static final int p = z1.c.motionDurationMedium4;
    public static final int q = z1.c.motionEasingEmphasizedInterpolator;
    public final LinkedHashSet c = new LinkedHashSet();
    public int d;
    public int e;
    public TimeInterpolator f;
    public TimeInterpolator g;
    public int h = 0;
    public AccessibilityManager i;
    public AccessibilityManager.TouchExplorationStateChangeListener j;
    public boolean k = true;
    public int l = 2;
    public int m = 0;
    public ViewPropertyAnimator n;

    public HideBottomViewOnScrollBehavior() {
    }

    public HideBottomViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static /* synthetic */ void I(HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior, View view, boolean bl) {
        if (bl) {
            if (hideBottomViewOnScrollBehavior.P()) {
                hideBottomViewOnScrollBehavior.U(view);
            }
            return;
        }
        hideBottomViewOnScrollBehavior.getClass();
    }

    public static /* synthetic */ AccessibilityManager.TouchExplorationStateChangeListener K(HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior, AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        hideBottomViewOnScrollBehavior.j = touchExplorationStateChangeListener;
        return touchExplorationStateChangeListener;
    }

    public static /* synthetic */ ViewPropertyAnimator M(HideBottomViewOnScrollBehavior hideBottomViewOnScrollBehavior, ViewPropertyAnimator viewPropertyAnimator) {
        hideBottomViewOnScrollBehavior.n = viewPropertyAnimator;
        return viewPropertyAnimator;
    }

    @Override
    public boolean E(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3, int n4) {
        return n3 == 2;
    }

    public final void N(View view, int n3, long l3, TimeInterpolator timeInterpolator) {
        this.n = view.animate().translationY((float)n3).setInterpolator(timeInterpolator).setDuration(l3).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final HideBottomViewOnScrollBehavior a;
            {
                this.a = hideBottomViewOnScrollBehavior;
            }

            public void onAnimationEnd(Animator animator) {
                HideBottomViewOnScrollBehavior.M(this.a, null);
            }
        });
    }

    public final void O(View view) {
        if (this.i == null) {
            this.i = (AccessibilityManager)e0.a.g(view.getContext(), AccessibilityManager.class);
        }
        if (this.i != null && this.j == null) {
            a a4 = new a(this, view);
            this.j = a4;
            this.i.addTouchExplorationStateChangeListener((AccessibilityManager.TouchExplorationStateChangeListener)a4);
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this){
                public final HideBottomViewOnScrollBehavior c;
                {
                    this.c = hideBottomViewOnScrollBehavior;
                }

                public void onViewAttachedToWindow(View view) {
                }

                public void onViewDetachedFromWindow(View view) {
                    if (this.c.j != null && this.c.i != null) {
                        this.c.i.removeTouchExplorationStateChangeListener(this.c.j);
                        HideBottomViewOnScrollBehavior.K(this.c, null);
                    }
                }
            });
        }
    }

    public boolean P() {
        return this.l == 1;
    }

    public boolean Q() {
        return this.l == 2;
    }

    public void R(View view, int n3) {
        this.m = n3;
        if (this.l == 1) {
            view.setTranslationY((float)(this.h + n3));
        }
    }

    public void S(View view) {
        this.T(view, true);
    }

    public void T(View view, boolean bl) {
        AccessibilityManager accessibilityManager;
        if (this.P() || this.k && (accessibilityManager = this.i) != null && accessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        accessibilityManager = this.n;
        if (accessibilityManager != null) {
            accessibilityManager.cancel();
            view.clearAnimation();
        }
        this.W(view, 1);
        int n3 = this.h + this.m;
        if (bl) {
            this.N(view, n3, this.e, this.g);
            return;
        }
        view.setTranslationY((float)n3);
    }

    public void U(View view) {
        this.V(view, true);
    }

    public void V(View view, boolean bl) {
        if (this.Q()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.n;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        this.W(view, 2);
        if (bl) {
            this.N(view, 0, this.d, this.f);
            return;
        }
        view.setTranslationY(0.0f);
    }

    public final void W(View object, int n3) {
        this.l = n3;
        object = ((AbstractCollection)this.c).iterator();
        if (!object.hasNext()) {
            return;
        }
        s.a(object.next());
        throw null;
    }

    @Override
    public boolean p(CoordinatorLayout coordinatorLayout, View view, int n3) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        this.h = view.getMeasuredHeight() + marginLayoutParams.bottomMargin;
        this.d = p2.k.f(view.getContext(), o, 225);
        this.e = p2.k.f(view.getContext(), p, 175);
        marginLayoutParams = view.getContext();
        int n4 = q;
        this.f = p2.k.g((Context)marginLayoutParams, n4, a2.a.d);
        this.g = p2.k.g(view.getContext(), n4, a2.a.c);
        this.O(view);
        return super.p(coordinatorLayout, view, n3);
    }

    @Override
    public void x(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        if (n4 > 0) {
            this.S(view);
            return;
        }
        if (n4 < 0) {
            this.U(view);
        }
    }
}

