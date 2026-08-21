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
 *  android.view.Gravity
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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.app.s;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import b2.b;
import b2.c;
import b2.d;
import b2.e;
import b2.f;
import e0.a;
import java.util.AbstractCollection;
import java.util.LinkedHashSet;
import p2.k;

public class HideViewOnScrollBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    public static final int q = z1.c.motionDurationLong2;
    public static final int r = z1.c.motionDurationMedium4;
    public static final int s = z1.c.motionEasingEmphasizedInterpolator;
    public f c;
    public AccessibilityManager d;
    public AccessibilityManager.TouchExplorationStateChangeListener e;
    public boolean f = true;
    public final LinkedHashSet g = new LinkedHashSet();
    public int h;
    public int i;
    public TimeInterpolator j;
    public TimeInterpolator k;
    public int l = 0;
    public int m = 2;
    public int n = 0;
    public ViewPropertyAnimator o;
    public boolean p = false;

    public HideViewOnScrollBehavior() {
    }

    public HideViewOnScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public static /* synthetic */ void I(HideViewOnScrollBehavior hideViewOnScrollBehavior, View view, boolean bl) {
        if (hideViewOnScrollBehavior.f && bl && hideViewOnScrollBehavior.S()) {
            hideViewOnScrollBehavior.V(view);
        }
    }

    public static /* synthetic */ AccessibilityManager.TouchExplorationStateChangeListener K(HideViewOnScrollBehavior hideViewOnScrollBehavior, AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener) {
        hideViewOnScrollBehavior.e = touchExplorationStateChangeListener;
        return touchExplorationStateChangeListener;
    }

    public static /* synthetic */ ViewPropertyAnimator M(HideViewOnScrollBehavior hideViewOnScrollBehavior, ViewPropertyAnimator viewPropertyAnimator) {
        hideViewOnScrollBehavior.o = viewPropertyAnimator;
        return viewPropertyAnimator;
    }

    private void N(View view, int n3, long l3, TimeInterpolator timeInterpolator) {
        this.o = this.c.d(view, n3).setInterpolator(timeInterpolator).setDuration(l3).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final HideViewOnScrollBehavior a;
            {
                this.a = hideViewOnScrollBehavior;
            }

            public void onAnimationEnd(Animator animator) {
                HideViewOnScrollBehavior.M(this.a, null);
            }
        });
    }

    private void O(View view) {
        if (this.d == null) {
            this.d = (AccessibilityManager)a.g(view.getContext(), AccessibilityManager.class);
        }
        if (this.d != null && this.e == null) {
            e e3 = new e(this, view);
            this.e = e3;
            this.d.addTouchExplorationStateChangeListener((AccessibilityManager.TouchExplorationStateChangeListener)e3);
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this){
                public final HideViewOnScrollBehavior c;
                {
                    this.c = hideViewOnScrollBehavior;
                }

                public void onViewAttachedToWindow(View view) {
                }

                public void onViewDetachedFromWindow(View view) {
                    if (this.c.e != null && this.c.d != null) {
                        this.c.d.removeTouchExplorationStateChangeListener(this.c.e);
                        HideViewOnScrollBehavior.K(this.c, null);
                    }
                }
            });
        }
    }

    private void Z(View object, int n3) {
        this.m = n3;
        object = ((AbstractCollection)this.g).iterator();
        if (!object.hasNext()) {
            return;
        }
        androidx.appcompat.app.s.a(object.next());
        throw null;
    }

    @Override
    public boolean E(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int n3, int n4) {
        return n3 == 2;
    }

    public final boolean P(int n3) {
        return n3 == 80 || n3 == 81;
        {
        }
    }

    public final boolean Q(int n3) {
        return n3 == 3 || n3 == 19;
        {
        }
    }

    public boolean R() {
        return this.m == 2;
    }

    public boolean S() {
        return this.m == 1;
    }

    public final void T(View view, int n3) {
        if (this.p) {
            return;
        }
        int n4 = ((CoordinatorLayout.e)view.getLayoutParams()).c;
        if (this.P(n4)) {
            this.U(1);
            return;
        }
        n3 = this.Q(Gravity.getAbsoluteGravity((int)n4, (int)n3)) ? 2 : 0;
        this.U(n3);
    }

    public final void U(int n3) {
        Object object = this.c;
        if (object != null && ((f)object).c() == n3) {
            return;
        }
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 2) {
                    this.c = new c();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid view edge position value: ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(". Must be ");
                ((StringBuilder)object).append(0);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(1);
                ((StringBuilder)object).append(" or ");
                ((StringBuilder)object).append(2);
                ((StringBuilder)object).append(".");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.c = new b();
            return;
        }
        this.c = new d();
    }

    public void V(View view) {
        this.W(view, true);
    }

    public void W(View view, boolean bl) {
        if (this.R()) {
            return;
        }
        ViewPropertyAnimator viewPropertyAnimator = this.o;
        if (viewPropertyAnimator != null) {
            viewPropertyAnimator.cancel();
            view.clearAnimation();
        }
        this.Z(view, 2);
        int n3 = this.c.b();
        if (bl) {
            this.N(view, n3, this.h, this.j);
            return;
        }
        this.c.e(view, n3);
    }

    public void X(View view) {
        this.Y(view, true);
    }

    public void Y(View view, boolean bl) {
        AccessibilityManager accessibilityManager;
        if (this.S() || this.f && (accessibilityManager = this.d) != null && accessibilityManager.isTouchExplorationEnabled()) {
            return;
        }
        accessibilityManager = this.o;
        if (accessibilityManager != null) {
            accessibilityManager.cancel();
            view.clearAnimation();
        }
        this.Z(view, 1);
        int n3 = this.l + this.n;
        if (bl) {
            this.N(view, n3, this.i, this.k);
            return;
        }
        this.c.e(view, n3);
    }

    @Override
    public boolean p(CoordinatorLayout coordinatorLayout, View view, int n3) {
        this.O(view);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        this.T(view, n3);
        this.l = this.c.a(view, marginLayoutParams);
        this.h = p2.k.f(view.getContext(), q, 225);
        this.i = p2.k.f(view.getContext(), r, 175);
        marginLayoutParams = view.getContext();
        int n4 = s;
        this.j = p2.k.g((Context)marginLayoutParams, n4, a2.a.d);
        this.k = p2.k.g(view.getContext(), n4, a2.a.c);
        return super.p(coordinatorLayout, view, n3);
    }

    @Override
    public void x(CoordinatorLayout coordinatorLayout, View view, View view2, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
        if (n4 > 0) {
            this.X(view);
            return;
        }
        if (n4 < 0) {
            this.V(view);
        }
    }
}

