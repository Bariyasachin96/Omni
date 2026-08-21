/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.AnimatorSet
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Paint$Style
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$MarginLayoutParams
 */
package com.google.android.material.bottomappbar;

import a2.k;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.s;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomappbar.a;
import com.google.android.material.bottomappbar.b;
import com.google.android.material.bottomappbar.c;
import com.google.android.material.bottomappbar.d;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import o0.z1;
import v2.g;
import v2.i;
import v2.j;
import v2.o;
import z1.l;
import z1.m;

public class BottomAppBar
extends Toolbar
implements CoordinatorLayout.b {
    public static final int A0;
    public static final int y0;
    public static final int z0;
    public Integer V;
    public final i W;
    public Animator a0;
    public Animator b0;
    public int c0;
    public int d0;
    public int e0;
    public final int f0;
    public int g0;
    public int h0;
    public final boolean i0;
    public boolean j0;
    public final boolean k0;
    public final boolean l0;
    public final boolean m0;
    public int n0;
    public ArrayList o0;
    public int p0;
    public boolean q0;
    public boolean r0;
    public Behavior s0;
    public int t0;
    public int u0;
    public int v0;
    public AnimatorListenerAdapter w0;
    public k x0;

    static {
        y0 = z1.l.Widget_MaterialComponents_BottomAppBar;
        z0 = z1.c.motionDurationLong2;
        A0 = z1.c.motionEasingEmphasizedInterpolator;
    }

    public BottomAppBar(Context context) {
        this(context, null);
    }

    public BottomAppBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.bottomAppBarStyle);
    }

    public BottomAppBar(Context context, AttributeSet attributeSet, int n3) {
        i i3;
        int n4 = y0;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        this.W = i3 = new i();
        this.n0 = 0;
        this.p0 = 0;
        this.q0 = false;
        this.r0 = true;
        this.w0 = new AnimatorListenerAdapter(this){
            public final BottomAppBar a;
            {
                this.a = bottomAppBar;
            }

            public void onAnimationStart(Animator object) {
                if (!this.a.q0) {
                    object = this.a;
                    ((BottomAppBar)object).M0(((BottomAppBar)object).c0, this.a.r0);
                }
            }
        };
        this.x0 = new k(this){
            public final BottomAppBar a;
            {
                this.a = bottomAppBar;
            }

            public void c(FloatingActionButton floatingActionButton) {
                i i3 = this.a.W;
                float f3 = floatingActionButton.getVisibility() == 0 && this.a.e0 == 1 ? floatingActionButton.getScaleY() : 0.0f;
                i3.j0(f3);
            }

            public void d(FloatingActionButton floatingActionButton) {
                if (this.a.e0 != 1) {
                    return;
                }
                float f3 = floatingActionButton.getTranslationX();
                if (this.a.getTopEdgeTreatment().h() != f3) {
                    this.a.getTopEdgeTreatment().n(f3);
                    this.a.W.invalidateSelf();
                }
                float f4 = -floatingActionButton.getTranslationY();
                f3 = 0.0f;
                f4 = Math.max(0.0f, f4);
                if (this.a.getTopEdgeTreatment().c() != f4) {
                    this.a.getTopEdgeTreatment().i(f4);
                    this.a.W.invalidateSelf();
                }
                i i3 = this.a.W;
                if (floatingActionButton.getVisibility() == 0) {
                    f3 = floatingActionButton.getScaleY();
                }
                i3.j0(f3);
            }
        };
        Context context2 = this.getContext();
        Object object = com.google.android.material.internal.z.i(context2, attributeSet, z1.m.BottomAppBar, n3, n4, new int[0]);
        context = s2.c.a(context2, object, z1.m.BottomAppBar_backgroundTint);
        int n5 = z1.m.BottomAppBar_navigationIconTint;
        if (object.hasValue(n5)) {
            this.setNavigationIconTint(object.getColor(n5, -1));
        }
        n5 = object.getDimensionPixelSize(z1.m.BottomAppBar_elevation, 0);
        float f3 = object.getDimensionPixelOffset(z1.m.BottomAppBar_fabCradleMargin, 0);
        float f4 = object.getDimensionPixelOffset(z1.m.BottomAppBar_fabCradleRoundedCornerRadius, 0);
        float f5 = object.getDimensionPixelOffset(z1.m.BottomAppBar_fabCradleVerticalOffset, 0);
        this.c0 = object.getInt(z1.m.BottomAppBar_fabAlignmentMode, 0);
        this.d0 = object.getInt(z1.m.BottomAppBar_fabAnimationMode, 0);
        this.e0 = object.getInt(z1.m.BottomAppBar_fabAnchorMode, 1);
        this.i0 = object.getBoolean(z1.m.BottomAppBar_removeEmbeddedFabElevation, true);
        this.h0 = object.getInt(z1.m.BottomAppBar_menuAlignmentMode, 0);
        this.j0 = object.getBoolean(z1.m.BottomAppBar_hideOnScroll, false);
        this.k0 = object.getBoolean(z1.m.BottomAppBar_paddingBottomSystemWindowInsets, false);
        this.l0 = object.getBoolean(z1.m.BottomAppBar_paddingLeftSystemWindowInsets, false);
        this.m0 = object.getBoolean(z1.m.BottomAppBar_paddingRightSystemWindowInsets, false);
        this.g0 = object.getDimensionPixelOffset(z1.m.BottomAppBar_fabAlignmentModeEndMargin, -1);
        boolean bl = object.getBoolean(z1.m.BottomAppBar_addElevationShadow, true);
        object.recycle();
        this.f0 = this.getResources().getDimensionPixelOffset(z1.e.mtrl_bottomappbar_fabOffsetEndMode);
        object = new d(f3, f4, f5);
        i3.setShapeAppearanceModel(v2.o.a().B((g)object).m());
        if (bl) {
            i3.q0(2);
        } else {
            i3.q0(1);
            if (Build.VERSION.SDK_INT >= 28) {
                a.a(this, 0);
                b.a(this, 0);
            }
        }
        i3.m0(Paint.Style.FILL);
        i3.W(context2);
        i3.setTintList((ColorStateList)context);
        this.setElevation(n5);
        this.setBackground(i3);
        com.google.android.material.internal.c0.e((View)this, attributeSet, n3, n4, new c0.d(this){
            public final BottomAppBar a;
            {
                this.a = bottomAppBar;
            }

            @Override
            public z1 a(View view, z1 z12, c0.e e3) {
                boolean bl;
                boolean bl2;
                if (this.a.k0) {
                    BottomAppBar.y0(this.a, z12.i());
                }
                boolean bl3 = this.a.l0;
                boolean bl4 = true;
                boolean bl5 = false;
                if (bl3) {
                    bl2 = this.a.v0 != z12.j();
                    BottomAppBar.U(this.a, z12.j());
                    bl = bl2;
                } else {
                    bl = false;
                }
                bl2 = bl5;
                if (this.a.m0) {
                    bl2 = this.a.u0 != z12.k() ? bl4 : false;
                    BottomAppBar.X(this.a, z12.k());
                }
                if (!bl && !bl2) {
                    return z12;
                }
                this.a.B0();
                this.a.R0();
                this.a.Q0();
                return z12;
            }
        });
    }

    public static /* synthetic */ void P(View view) {
        view.requestLayout();
    }

    public static /* synthetic */ boolean R(BottomAppBar bottomAppBar, boolean bl) {
        bottomAppBar.q0 = bl;
        return bl;
    }

    public static /* synthetic */ int U(BottomAppBar bottomAppBar, int n3) {
        bottomAppBar.v0 = n3;
        return n3;
    }

    public static void V0(BottomAppBar bottomAppBar, View object) {
        object = (CoordinatorLayout.e)object.getLayoutParams();
        object.d = 17;
        int n3 = bottomAppBar.e0;
        if (n3 == 1) {
            object.d = 0x11 | 0x30;
        }
        if (n3 == 0) {
            object.d |= 0x50;
        }
    }

    public static /* synthetic */ int X(BottomAppBar bottomAppBar, int n3) {
        bottomAppBar.u0 = n3;
        return n3;
    }

    public static /* synthetic */ Animator d0(BottomAppBar bottomAppBar, Animator animator) {
        bottomAppBar.a0 = animator;
        return animator;
    }

    public static /* synthetic */ Animator g0(BottomAppBar bottomAppBar, Animator animator) {
        bottomAppBar.b0 = animator;
        return animator;
    }

    private ActionMenuView getActionMenuView() {
        for (int i3 = 0; i3 < this.getChildCount(); ++i3) {
            View view = this.getChildAt(i3);
            if (!(view instanceof ActionMenuView)) continue;
            return (ActionMenuView)view;
        }
        return null;
    }

    private int getBottomInset() {
        return this.t0;
    }

    private int getFabAlignmentAnimationDuration() {
        return p2.k.f(this.getContext(), z0, 300);
    }

    private float getFabTranslationX() {
        return this.K0(this.c0);
    }

    private float getFabTranslationY() {
        if (this.e0 == 1) {
            return -this.getTopEdgeTreatment().c();
        }
        View view = this.I0();
        int n3 = view != null ? -(this.getMeasuredHeight() + this.getBottomInset() - view.getMeasuredHeight()) / 2 : 0;
        return n3;
    }

    private int getLeftInset() {
        return this.v0;
    }

    private int getRightInset() {
        return this.u0;
    }

    private d getTopEdgeTreatment() {
        return (d)this.W.K().p();
    }

    public static /* synthetic */ int y0(BottomAppBar bottomAppBar, int n3) {
        bottomAppBar.t0 = n3;
        return n3;
    }

    public final void A0(FloatingActionButton floatingActionButton) {
        floatingActionButton.e((Animator.AnimatorListener)this.w0);
        floatingActionButton.f((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final BottomAppBar a;
            {
                this.a = bottomAppBar;
            }

            public void onAnimationStart(Animator object) {
                this.a.w0.onAnimationStart((Animator)object);
                object = this.a.H0();
                if (object != null) {
                    ((FloatingActionButton)object).setTranslationX(this.a.getFabTranslationX());
                }
            }
        });
        floatingActionButton.g(this.x0);
    }

    public final void B0() {
        Animator animator = this.b0;
        if (animator != null) {
            animator.cancel();
        }
        if ((animator = this.a0) != null) {
            animator.cancel();
        }
    }

    public void C0(int n3, List object) {
        object = this.H0();
        if (object != null && !((FloatingActionButton)object).m()) {
            this.G0();
            ((FloatingActionButton)object).k(new FloatingActionButton.b(this, n3){
                public final int a;
                public final BottomAppBar b;
                {
                    this.b = bottomAppBar;
                    this.a = n3;
                }

                @Override
                public void a(FloatingActionButton floatingActionButton) {
                    floatingActionButton.setTranslationX(this.b.K0(this.a));
                    floatingActionButton.q(new FloatingActionButton.b(this){
                        public final e a;
                        {
                            this.a = e3;
                        }

                        @Override
                        public void b(FloatingActionButton floatingActionButton) {
                            this.a.b.F0();
                        }
                    });
                }
            });
        }
    }

    public final void D0(int n3, List list) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this.H0(), (String)"translationX", (float[])new float[]{this.K0(n3)});
        objectAnimator.setDuration((long)this.getFabAlignmentAnimationDuration());
        list.add(objectAnimator);
    }

    public final void E0(int n3, boolean bl, List list) {
        ActionMenuView actionMenuView = this.getActionMenuView();
        if (actionMenuView != null) {
            float f3 = this.getFabAlignmentAnimationDuration();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)actionMenuView, (String)"alpha", (float[])new float[]{1.0f});
            objectAnimator.setDuration((long)(0.8f * f3));
            if (Math.abs(actionMenuView.getTranslationX() - (float)this.J0(actionMenuView, n3, bl)) > 1.0f) {
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat((Object)actionMenuView, (String)"alpha", (float[])new float[]{0.0f});
                objectAnimator2.setDuration((long)(f3 * 0.2f));
                objectAnimator2.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this, actionMenuView, n3, bl){
                    public boolean a;
                    public final ActionMenuView b;
                    public final int c;
                    public final boolean d;
                    public final BottomAppBar e;
                    {
                        this.e = bottomAppBar;
                        this.b = actionMenuView;
                        this.c = n3;
                        this.d = bl;
                    }

                    public void onAnimationCancel(Animator animator) {
                        this.a = true;
                    }

                    public void onAnimationEnd(Animator object) {
                        if (!this.a) {
                            boolean bl = this.e.p0 != 0;
                            object = this.e;
                            ((BottomAppBar)object).P0(((BottomAppBar)object).p0);
                            this.e.U0(this.b, this.c, this.d, bl);
                        }
                    }
                });
                actionMenuView = new AnimatorSet();
                actionMenuView.playSequentially(new Animator[]{objectAnimator2, objectAnimator});
                list.add(actionMenuView);
                return;
            }
            if (actionMenuView.getAlpha() < 1.0f) {
                list.add(objectAnimator);
            }
        }
    }

    public final void F0() {
        Object object;
        int n3;
        this.n0 = n3 = this.n0 - 1;
        if (n3 == 0 && (object = this.o0) != null && (object = ((ArrayList)object).iterator()).hasNext()) {
            androidx.appcompat.app.s.a(object.next());
            throw null;
        }
    }

    public final void G0() {
        Object object;
        int n3 = this.n0;
        this.n0 = n3 + 1;
        if (n3 == 0 && (object = this.o0) != null && (object = ((ArrayList)object).iterator()).hasNext()) {
            androidx.appcompat.app.s.a(object.next());
            throw null;
        }
    }

    public final FloatingActionButton H0() {
        View view = this.I0();
        if (view instanceof FloatingActionButton) {
            return (FloatingActionButton)view;
        }
        return null;
    }

    public final View I0() {
        if (!(this.getParent() instanceof CoordinatorLayout)) {
            return null;
        }
        for (View view : ((CoordinatorLayout)this.getParent()).w((View)this)) {
            if (!(view instanceof FloatingActionButton) && !(view instanceof ExtendedFloatingActionButton)) continue;
            return view;
        }
        return null;
    }

    public int J0(ActionMenuView actionMenuView, int n3, boolean bl) {
        int n4;
        int n5 = this.h0;
        int n6 = 0;
        if (!(n5 == 1 || n3 == 1 && bl)) {
            return 0;
        }
        bl = com.google.android.material.internal.c0.m((View)this);
        n3 = bl ? this.getMeasuredWidth() : 0;
        n5 = n3;
        for (n4 = 0; n4 < this.getChildCount(); ++n4) {
            View view = this.getChildAt(n4);
            n3 = n5;
            if (view.getLayoutParams() instanceof Toolbar.LayoutParams) {
                n3 = n5;
                if ((((Toolbar.LayoutParams)view.getLayoutParams()).a & 0x800007) == 0x800003) {
                    n3 = bl ? Math.min(n5, view.getLeft()) : Math.max(n5, view.getRight());
                }
            }
            n5 = n3;
        }
        n4 = bl ? actionMenuView.getRight() : actionMenuView.getLeft();
        int n7 = bl ? this.u0 : -this.v0;
        n3 = n6;
        if (this.getNavigationIcon() == null) {
            n3 = this.getResources().getDimensionPixelOffset(z1.e.m3_bottomappbar_horizontal_padding);
            if (!bl) {
                n3 = -n3;
            }
        }
        return n5 - (n4 + n7 + n3);
    }

    public final float K0(int n3) {
        boolean bl = com.google.android.material.internal.c0.m((View)this);
        int n4 = 1;
        if (n3 == 1) {
            View view = this.I0();
            n3 = bl ? this.v0 : this.u0;
            int n5 = this.g0 != -1 && view != null ? view.getMeasuredWidth() / 2 + this.g0 : this.f0;
            int n6 = this.getMeasuredWidth() / 2;
            if (bl) {
                n4 = -1;
            }
            return (n6 - (n3 + n5)) * n4;
        }
        return 0.0f;
    }

    public final boolean L0() {
        FloatingActionButton floatingActionButton = this.H0();
        return floatingActionButton != null && floatingActionButton.n();
    }

    public final void M0(int n3, boolean bl) {
        if (!this.isLaidOut()) {
            this.q0 = false;
            this.P0(this.p0);
            return;
        }
        Animator animator = this.b0;
        if (animator != null) {
            animator.cancel();
        }
        ArrayList arrayList = new ArrayList();
        if (!this.L0()) {
            n3 = 0;
            bl = false;
        }
        this.E0(n3, bl, arrayList);
        animator = new AnimatorSet();
        animator.playTogether(arrayList);
        this.b0 = animator;
        animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final BottomAppBar a;
            {
                this.a = bottomAppBar;
            }

            public void onAnimationEnd(Animator animator) {
                this.a.F0();
                BottomAppBar.R(this.a, false);
                BottomAppBar.g0(this.a, null);
            }

            public void onAnimationStart(Animator animator) {
                this.a.G0();
            }
        });
        this.b0.start();
    }

    public final void N0(int n3) {
        if (this.c0 != n3 && this.isLaidOut()) {
            Object object = this.a0;
            if (object != null) {
                object.cancel();
            }
            object = new ArrayList();
            if (this.d0 == 1) {
                this.D0(n3, (List)object);
            } else {
                this.C0(n3, (List)object);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether((Collection)object);
            animatorSet.setInterpolator(p2.k.g(this.getContext(), A0, a2.a.a));
            this.a0 = animatorSet;
            animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final BottomAppBar a;
                {
                    this.a = bottomAppBar;
                }

                public void onAnimationEnd(Animator animator) {
                    this.a.F0();
                    BottomAppBar.d0(this.a, null);
                }

                public void onAnimationStart(Animator animator) {
                    this.a.G0();
                }
            });
            this.a0.start();
        }
    }

    public final Drawable O0(Drawable drawable) {
        Drawable drawable2 = drawable;
        if (drawable != null) {
            drawable2 = drawable;
            if (this.V != null) {
                drawable2 = h0.a.r(drawable.mutate());
                drawable2.setTint(this.V.intValue());
            }
        }
        return drawable2;
    }

    public void P0(int n3) {
        if (n3 != 0) {
            this.p0 = 0;
            this.getMenu().clear();
            this.z(n3);
        }
    }

    public final void Q0() {
        ActionMenuView actionMenuView = this.getActionMenuView();
        if (actionMenuView != null && this.b0 == null) {
            actionMenuView.setAlpha(1.0f);
            if (!this.L0()) {
                this.T0(actionMenuView, 0, false);
                return;
            }
            this.T0(actionMenuView, this.c0, this.r0);
        }
    }

    public final void R0() {
        this.getTopEdgeTreatment().n(this.getFabTranslationX());
        i i3 = this.W;
        float f3 = this.r0 && this.L0() && this.e0 == 1 ? 1.0f : 0.0f;
        i3.j0(f3);
        i3 = this.I0();
        if (i3 != null) {
            i3.setTranslationY(this.getFabTranslationY());
            i3.setTranslationX(this.getFabTranslationX());
        }
    }

    public boolean S0(int n3) {
        float f3 = n3;
        if (f3 != this.getTopEdgeTreatment().g()) {
            this.getTopEdgeTreatment().m(f3);
            this.W.invalidateSelf();
            return true;
        }
        return false;
    }

    public final void T0(ActionMenuView actionMenuView, int n3, boolean bl) {
        this.U0(actionMenuView, n3, bl, false);
    }

    public final void U0(ActionMenuView actionMenuView, int n3, boolean bl, boolean bl2) {
        Runnable runnable = new Runnable(this, actionMenuView, n3, bl){
            public final ActionMenuView c;
            public final int d;
            public final boolean e;
            public final BottomAppBar f;
            {
                this.f = bottomAppBar;
                this.c = actionMenuView;
                this.d = n3;
                this.e = bl;
            }

            @Override
            public void run() {
                ActionMenuView actionMenuView = this.c;
                actionMenuView.setTranslationX(this.f.J0(actionMenuView, this.d, this.e));
            }
        };
        if (bl2) {
            actionMenuView.post(runnable);
            return;
        }
        runnable.run();
    }

    public ColorStateList getBackgroundTint() {
        return this.W.O();
    }

    @Override
    public Behavior getBehavior() {
        if (this.s0 == null) {
            this.s0 = new Behavior();
        }
        return this.s0;
    }

    public float getCradleVerticalOffset() {
        return this.getTopEdgeTreatment().c();
    }

    public int getFabAlignmentMode() {
        return this.c0;
    }

    public int getFabAlignmentModeEndMargin() {
        return this.g0;
    }

    public int getFabAnchorMode() {
        return this.e0;
    }

    public int getFabAnimationMode() {
        return this.d0;
    }

    public float getFabCradleMargin() {
        return this.getTopEdgeTreatment().e();
    }

    public float getFabCradleRoundedCornerRadius() {
        return this.getTopEdgeTreatment().f();
    }

    public boolean getHideOnScroll() {
        return this.j0;
    }

    public int getMenuAlignmentMode() {
        return this.h0;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.f((View)this, this.W);
        if (this.getParent() instanceof ViewGroup) {
            ((ViewGroup)this.getParent()).setClipChildren(false);
        }
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        super.onLayout(bl, n3, n4, n5, n6);
        if (bl) {
            this.B0();
            this.R0();
            View view = this.I0();
            if (view != null && view.isLaidOut()) {
                view.post((Runnable)new c(view));
            }
        }
        this.Q0();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.c0 = parcelable.e;
        this.r0 = parcelable.f;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = this.c0;
        savedState.f = this.r0;
        return savedState;
    }

    public void setBackgroundTint(ColorStateList colorStateList) {
        this.W.setTintList(colorStateList);
    }

    public void setCradleVerticalOffset(float f3) {
        if (f3 != this.getCradleVerticalOffset()) {
            this.getTopEdgeTreatment().i(f3);
            this.W.invalidateSelf();
            this.R0();
        }
    }

    public void setElevation(float f3) {
        this.W.h0(f3);
        int n3 = this.W.J();
        int n4 = this.W.I();
        ((HideBottomViewOnScrollBehavior)this.getBehavior()).R((View)this, n3 - n4);
    }

    public void setFabAlignmentMode(int n3) {
        this.setFabAlignmentModeAndReplaceMenu(n3, 0);
    }

    public void setFabAlignmentModeAndReplaceMenu(int n3, int n4) {
        this.p0 = n4;
        this.q0 = true;
        this.M0(n3, this.r0);
        this.N0(n3);
        this.c0 = n3;
    }

    public void setFabAlignmentModeEndMargin(int n3) {
        if (this.g0 != n3) {
            this.g0 = n3;
            this.R0();
        }
    }

    public void setFabAnchorMode(int n3) {
        this.e0 = n3;
        this.R0();
        View view = this.I0();
        if (view != null) {
            BottomAppBar.V0(this, view);
            view.requestLayout();
            this.W.invalidateSelf();
        }
    }

    public void setFabAnimationMode(int n3) {
        this.d0 = n3;
    }

    public void setFabCornerSize(float f3) {
        if (f3 != this.getTopEdgeTreatment().d()) {
            this.getTopEdgeTreatment().j(f3);
            this.W.invalidateSelf();
        }
    }

    public void setFabCradleMargin(float f3) {
        if (f3 != this.getFabCradleMargin()) {
            this.getTopEdgeTreatment().k(f3);
            this.W.invalidateSelf();
        }
    }

    public void setFabCradleRoundedCornerRadius(float f3) {
        if (f3 != this.getFabCradleRoundedCornerRadius()) {
            this.getTopEdgeTreatment().l(f3);
            this.W.invalidateSelf();
        }
    }

    public void setHideOnScroll(boolean bl) {
        this.j0 = bl;
    }

    public void setMenuAlignmentMode(int n3) {
        if (this.h0 != n3) {
            this.h0 = n3;
            ActionMenuView actionMenuView = this.getActionMenuView();
            if (actionMenuView != null) {
                this.T0(actionMenuView, this.c0, this.L0());
            }
        }
    }

    @Override
    public void setNavigationIcon(Drawable drawable) {
        super.setNavigationIcon(this.O0(drawable));
    }

    public void setNavigationIconTint(int n3) {
        this.V = n3;
        Drawable drawable = this.getNavigationIcon();
        if (drawable != null) {
            this.setNavigationIcon(drawable);
        }
    }

    @Override
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override
    public void setTitle(CharSequence charSequence) {
    }

    public static class Behavior
    extends HideBottomViewOnScrollBehavior<BottomAppBar> {
        public final Rect r;
        public WeakReference s;
        public int t;
        public final View.OnLayoutChangeListener u = new View.OnLayoutChangeListener(this){
            public final Behavior a;
            {
                this.a = behavior;
            }

            public void onLayoutChange(View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
                BottomAppBar bottomAppBar = (BottomAppBar)this.a.s.get();
                if (bottomAppBar != null && (view instanceof FloatingActionButton || view instanceof ExtendedFloatingActionButton)) {
                    Object object;
                    n3 = view.getHeight();
                    if (view instanceof FloatingActionButton) {
                        object = (FloatingActionButton)view;
                        ((FloatingActionButton)object).h(this.a.r);
                        n3 = this.a.r.height();
                        bottomAppBar.S0(n3);
                        bottomAppBar.setFabCornerSize(((FloatingActionButton)object).getShapeAppearanceModel().r().a(new RectF(this.a.r)));
                    }
                    object = (CoordinatorLayout.e)view.getLayoutParams();
                    if (this.a.t == 0) {
                        if (bottomAppBar.e0 == 1) {
                            n3 = (view.getMeasuredHeight() - n3) / 2;
                            n4 = bottomAppBar.getResources().getDimensionPixelOffset(z1.e.mtrl_bottomappbar_fab_bottom_margin);
                            ((ViewGroup.MarginLayoutParams)object).bottomMargin = bottomAppBar.getBottomInset() + (n4 - n3);
                        }
                        ((ViewGroup.MarginLayoutParams)object).leftMargin = bottomAppBar.getLeftInset();
                        ((ViewGroup.MarginLayoutParams)object).rightMargin = bottomAppBar.getRightInset();
                        if (com.google.android.material.internal.c0.m(view)) {
                            ((ViewGroup.MarginLayoutParams)object).leftMargin += bottomAppBar.f0;
                        } else {
                            ((ViewGroup.MarginLayoutParams)object).rightMargin += bottomAppBar.f0;
                        }
                    }
                    bottomAppBar.R0();
                    return;
                }
                view.removeOnLayoutChangeListener((View.OnLayoutChangeListener)this);
            }
        };

        public Behavior() {
            this.r = new Rect();
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.r = new Rect();
        }

        public boolean a0(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, int n3) {
            this.s = new WeakReference<BottomAppBar>(bottomAppBar);
            View view = bottomAppBar.I0();
            if (view != null && !view.isLaidOut()) {
                BottomAppBar.V0(bottomAppBar, view);
                this.t = ((CoordinatorLayout.e)view.getLayoutParams()).bottomMargin;
                if (view instanceof FloatingActionButton) {
                    FloatingActionButton floatingActionButton = (FloatingActionButton)view;
                    if (bottomAppBar.e0 == 0 && bottomAppBar.i0) {
                        floatingActionButton.setElevation(0.0f);
                        floatingActionButton.setCompatElevation(0.0f);
                    }
                    if (floatingActionButton.getShowMotionSpec() == null) {
                        floatingActionButton.setShowMotionSpecResource(z1.b.mtrl_fab_show_motion_spec);
                    }
                    if (floatingActionButton.getHideMotionSpec() == null) {
                        floatingActionButton.setHideMotionSpecResource(z1.b.mtrl_fab_hide_motion_spec);
                    }
                    bottomAppBar.A0(floatingActionButton);
                }
                view.addOnLayoutChangeListener(this.u);
                bottomAppBar.R0();
            }
            coordinatorLayout.M((View)bottomAppBar, n3);
            return super.p(coordinatorLayout, (View)bottomAppBar, n3);
        }

        public boolean b0(CoordinatorLayout coordinatorLayout, BottomAppBar bottomAppBar, View view, View view2, int n3, int n4) {
            return bottomAppBar.getHideOnScroll() && super.E(coordinatorLayout, (View)bottomAppBar, view, view2, n3, n4);
        }
    }

    public static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

            public SavedState a(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState b(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] c(int n3) {
                return new SavedState[n3];
            }
        };
        public int e;
        public boolean f;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readInt();
            boolean bl = parcel.readInt() != 0;
            this.f = bl;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e);
            parcel.writeInt(this.f ? 1 : 0);
        }
    }
}

