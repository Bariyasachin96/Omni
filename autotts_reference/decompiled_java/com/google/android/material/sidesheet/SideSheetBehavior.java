/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.TypedValue
 *  android.view.Gravity
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 */
package com.google.android.material.sidesheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.activity.b;
import androidx.appcompat.app.s;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import j0.a;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;
import o0.x0;
import p0.s;
import p0.v;
import p2.j;
import v0.c;
import v2.i;
import v2.o;
import w2.d;
import w2.e;
import w2.f;
import w2.g;
import w2.h;
import z1.k;
import z1.l;
import z1.m;

public class SideSheetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V>
implements p2.b {
    public static final int B = z1.k.side_sheet_accessibility_pane_title;
    public static final int C = z1.l.Widget_Material3_SideSheet;
    public final c.c A;
    public w2.c c;
    public float d;
    public i e;
    public ColorStateList f;
    public o g;
    public final c h = new c(this);
    public float i;
    public boolean j = true;
    public int k = 5;
    public int l = 5;
    public v0.c m;
    public boolean n;
    public float o = 0.1f;
    public int p;
    public int q;
    public int r;
    public int s;
    public WeakReference t;
    public WeakReference u;
    public int v = -1;
    public VelocityTracker w;
    public j x;
    public int y;
    public final Set z = new LinkedHashSet();

    public SideSheetBehavior() {
        this.A = new c.c(this){
            public final SideSheetBehavior a;
            {
                this.a = sideSheetBehavior;
            }

            @Override
            public int a(View view, int n3, int n4) {
                return a.b(n3, this.a.c.g(), this.a.c.f());
            }

            @Override
            public int b(View view, int n3, int n4) {
                return view.getTop();
            }

            @Override
            public int d(View view) {
                return this.a.p + this.a.k0();
            }

            @Override
            public void j(int n3) {
                if (n3 == 1 && this.a.j) {
                    this.a.G0(1);
                }
            }

            @Override
            public void k(View view, int n3, int n4, int n5, int n6) {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                View view2 = this.a.f0();
                if (view2 != null && (marginLayoutParams = (ViewGroup.MarginLayoutParams)view2.getLayoutParams()) != null) {
                    this.a.c.p(marginLayoutParams, view.getLeft(), view.getRight());
                    view2.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
                }
                this.a.a0(view, n3);
            }

            @Override
            public void l(View view, float f3, float f4) {
                int n3 = this.a.W(view, f3, f4);
                SideSheetBehavior sideSheetBehavior = this.a;
                sideSheetBehavior.L0(view, n3, sideSheetBehavior.K0());
            }

            @Override
            public boolean m(View view, int n3) {
                if (this.a.k == 1) {
                    return false;
                }
                return this.a.t != null && this.a.t.get() == view;
            }
        };
    }

    public SideSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.A = new /* invalid duplicate definition of identical inner class */;
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, z1.m.SideSheetBehavior_Layout);
        int n3 = z1.m.SideSheetBehavior_Layout_backgroundTint;
        if (typedArray.hasValue(n3)) {
            this.f = s2.c.a(context, typedArray, n3);
        }
        if (typedArray.hasValue(z1.m.SideSheetBehavior_Layout_shapeAppearance)) {
            this.g = v2.o.e(context, attributeSet, 0, C).m();
        }
        if (typedArray.hasValue(n3 = z1.m.SideSheetBehavior_Layout_coplanarSiblingViewId)) {
            this.B0(typedArray.getResourceId(n3, -1));
        }
        this.Z(context);
        this.i = typedArray.getDimension(z1.m.SideSheetBehavior_Layout_android_elevation, -1.0f);
        this.C0(typedArray.getBoolean(z1.m.SideSheetBehavior_Layout_behavior_draggable, true));
        typedArray.recycle();
        this.d = ViewConfiguration.get((Context)context).getScaledMaximumFlingVelocity();
    }

    private void A0(View view, Runnable runnable) {
        if (this.v0(view)) {
            view.post(runnable);
            return;
        }
        runnable.run();
    }

    private boolean H0() {
        return this.m != null && (this.j || this.k == 1);
    }

    public static /* synthetic */ boolean I(SideSheetBehavior sideSheetBehavior, int n3, View view, v.a a4) {
        sideSheetBehavior.F0(n3);
        return true;
    }

    public static /* synthetic */ void J(SideSheetBehavior sideSheetBehavior, int n3) {
        View view = (View)sideSheetBehavior.t.get();
        if (view != null) {
            sideSheetBehavior.L0(view, n3, false);
        }
    }

    public static /* synthetic */ void K(SideSheetBehavior sideSheetBehavior, ViewGroup.MarginLayoutParams marginLayoutParams, int n3, View view, ValueAnimator valueAnimator) {
        sideSheetBehavior.c.o(marginLayoutParams, a2.a.c(n3, 0, valueAnimator.getAnimatedFraction()));
        view.requestLayout();
    }

    private void L0(View view, int n3, boolean bl) {
        if (this.w0(view, n3, bl)) {
            this.G0(2);
            this.h.b(n3);
            return;
        }
        this.G0(n3);
    }

    private void M0() {
        WeakReference weakReference = this.t;
        if (weakReference != null && (weakReference = (View)weakReference.get()) != null) {
            x0.b0((View)weakReference, 262144);
            x0.b0((View)weakReference, 0x100000);
            if (this.k != 5) {
                this.y0((View)weakReference, s.a.y, 5);
            }
            if (this.k != 3) {
                this.y0((View)weakReference, s.a.w, 3);
            }
        }
    }

    private v Y(int n3) {
        return new e(this, n3);
    }

    private void Z(Context context) {
        i i3;
        if (this.g == null) {
            return;
        }
        this.e = i3 = new i(this.g);
        i3.W(context);
        i3 = this.f;
        if (i3 != null) {
            this.e.i0((ColorStateList)i3);
            return;
        }
        i3 = new TypedValue();
        context.getTheme().resolveAttribute(0x1010031, (TypedValue)i3, true);
        this.e.setTint(((TypedValue)i3).data);
    }

    private int c0(int n3, int n4, int n5, int n6) {
        n4 = ViewGroup.getChildMeasureSpec((int)n3, (int)n4, (int)n6);
        if (n5 == -1) {
            return n4;
        }
        n3 = View.MeasureSpec.getMode((int)n4);
        n4 = View.MeasureSpec.getSize((int)n4);
        if (n3 != 0x40000000) {
            if (n4 != 0) {
                n5 = Math.min(n4, n5);
            }
            return View.MeasureSpec.makeMeasureSpec((int)n5, (int)Integer.MIN_VALUE);
        }
        return View.MeasureSpec.makeMeasureSpec((int)Math.min(n4, n5), (int)0x40000000);
    }

    private void y0(View view, s.a a4, int n3) {
        x0.d0(view, a4, null, this.Y(n3));
    }

    @Override
    public void B(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
        int n3;
        block5: {
            block4: {
                int n4;
                if ((parcelable = (SavedState)parcelable).o() != null) {
                    super.B(coordinatorLayout, view, parcelable.o());
                }
                if ((n4 = parcelable.e) == 1) break block4;
                n3 = n4;
                if (n4 != 2) break block5;
            }
            n3 = 5;
        }
        this.k = n3;
        this.l = n3;
    }

    public void B0(int n3) {
        this.v = n3;
        this.X();
        WeakReference weakReference = this.t;
        if (weakReference != null) {
            weakReference = (View)weakReference.get();
            if (n3 != -1 && weakReference.isLaidOut()) {
                weakReference.requestLayout();
            }
        }
    }

    @Override
    public Parcelable C(CoordinatorLayout coordinatorLayout, View view) {
        return new SavedState(super.C(coordinatorLayout, view), this);
    }

    public void C0(boolean bl) {
        this.j = bl;
    }

    public final void D0(int n3) {
        Object object;
        block8: {
            block6: {
                block7: {
                    object = this.c;
                    if (object != null && ((w2.c)object).j() == n3) break block6;
                    if (n3 != 0) break block7;
                    this.c = new w2.b(this);
                    if (this.g != null && !this.s0()) {
                        object = this.g.w();
                        ((o.b)object).I(0.0f).z(0.0f);
                        this.O0(((o.b)object).m());
                        return;
                    }
                    break block6;
                }
                if (n3 != 1) break block8;
                this.c = new w2.a(this);
                if (this.g != null && !this.r0()) {
                    object = this.g.w();
                    ((o.b)object).E(0.0f).v(0.0f);
                    this.O0(((o.b)object).m());
                }
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid sheet edge position value: ");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(". Must be ");
        ((StringBuilder)object).append(0);
        ((StringBuilder)object).append(" or ");
        ((StringBuilder)object).append(1);
        ((StringBuilder)object).append(".");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public final void E0(View view, int n3) {
        n3 = Gravity.getAbsoluteGravity((int)((CoordinatorLayout.e)view.getLayoutParams()).c, (int)n3) == 3 ? 1 : 0;
        this.D0(n3);
    }

    public void F0(int n3) {
        if (n3 != 1 && n3 != 2) {
            WeakReference weakReference = this.t;
            if (weakReference != null && weakReference.get() != null) {
                this.A0((View)this.t.get(), new f(this, n3));
                return;
            }
            this.G0(n3);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("STATE_");
        String string = n3 == 1 ? "DRAGGING" : "SETTLING";
        stringBuilder.append(string);
        stringBuilder.append(" should not be set externally.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void G0(int n3) {
        Object object;
        block6: {
            block5: {
                if (this.k == n3) break block5;
                this.k = n3;
                if (n3 == 3 || n3 == 5) {
                    this.l = n3;
                }
                if ((object = this.t) != null && (object = (View)((Reference)object).get()) != null) break block6;
            }
            return;
        }
        this.P0((View)object);
        object = this.z.iterator();
        if (!object.hasNext()) {
            this.M0();
            return;
        }
        androidx.appcompat.app.s.a(object.next());
        throw null;
    }

    @Override
    public boolean H(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int n3 = motionEvent.getActionMasked();
        if (this.k == 1 && n3 == 0) {
            return true;
        }
        if (this.H0()) {
            this.m.F(motionEvent);
        }
        if (n3 == 0) {
            this.z0();
        }
        if (this.w == null) {
            this.w = VelocityTracker.obtain();
        }
        this.w.addMovement(motionEvent);
        if (this.H0() && n3 == 2 && !this.n && this.t0(motionEvent)) {
            this.m.b(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return this.n ^ true;
    }

    public boolean I0(View view, float f3) {
        return this.c.n(view, f3);
    }

    public final boolean J0(View view) {
        return (view.isShown() || x0.n(view) != null) && this.j;
    }

    public boolean K0() {
        return true;
    }

    public final void N0() {
        WeakReference weakReference = this.t;
        if (weakReference != null && weakReference.get() != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams;
            View view = (View)this.t.get();
            weakReference = this.f0();
            if (weakReference != null && (marginLayoutParams = (ViewGroup.MarginLayoutParams)weakReference.getLayoutParams()) != null) {
                int n3 = (int)((float)this.p * view.getScaleX() + (float)this.s);
                this.c.o(marginLayoutParams, n3);
                weakReference.requestLayout();
            }
        }
    }

    public final void O0(o o3) {
        i i3 = this.e;
        if (i3 != null) {
            i3.setShapeAppearanceModel(o3);
        }
    }

    public final void P0(View view) {
        int n3 = this.k == 5 ? 4 : 0;
        if (view.getVisibility() != n3) {
            view.setVisibility(n3);
        }
    }

    public final int U(int n3, View object) {
        int n4 = this.k;
        if (n4 != 1 && n4 != 2) {
            if (n4 != 3) {
                if (n4 == 5) {
                    return this.c.e();
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected value: ");
                ((StringBuilder)object).append(this.k);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            return 0;
        }
        return n3 - this.c.h((View)object);
    }

    public final float V(float f3, float f4) {
        return Math.abs(f3 - f4);
    }

    public final int W(View view, float f3, float f4) {
        if (this.u0(f3)) {
            return 3;
        }
        if (this.I0(view, f3)) {
            if (!this.c.m(f3, f4) && !this.c.l(view)) {
                return 3;
            }
            return 5;
        }
        if (f3 != 0.0f && w2.d.a(f3, f4)) {
            return 5;
        }
        int n3 = view.getLeft();
        if (Math.abs(n3 - this.g0()) < Math.abs(n3 - this.c.e())) {
            return 3;
        }
        return 5;
    }

    public final void X() {
        WeakReference weakReference = this.u;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.u = null;
    }

    @Override
    public void a() {
        Object object = this.x;
        if (object == null) {
            return;
        }
        if ((object = ((p2.a)object).c()) != null && Build.VERSION.SDK_INT >= 34) {
            this.x.h((b)object, this.h0(), (Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final SideSheetBehavior a;
                {
                    this.a = sideSheetBehavior;
                }

                public void onAnimationEnd(Animator animator) {
                    this.a.G0(5);
                    if (this.a.t != null && this.a.t.get() != null) {
                        ((View)this.a.t.get()).requestLayout();
                    }
                }
            }, this.e0());
            return;
        }
        this.F0(5);
    }

    public final void a0(View object, int n3) {
        if (!this.z.isEmpty()) {
            this.c.b(n3);
            object = this.z.iterator();
            if (object.hasNext()) {
                androidx.appcompat.app.s.a(object.next());
                throw null;
            }
        }
    }

    @Override
    public void b(b b3) {
        j j3 = this.x;
        if (j3 == null) {
            return;
        }
        j3.j(b3);
    }

    public final void b0(View view) {
        if (x0.n(view) == null) {
            x0.j0(view, view.getResources().getString(B));
        }
    }

    @Override
    public void c(b b3) {
        j j3 = this.x;
        if (j3 == null) {
            return;
        }
        j3.l(b3, this.h0());
        this.N0();
    }

    @Override
    public void d() {
        j j3 = this.x;
        if (j3 == null) {
            return;
        }
        j3.f();
    }

    public int d0() {
        return this.p;
    }

    public final ValueAnimator.AnimatorUpdateListener e0() {
        View view = this.f0();
        if (view == null) {
            return null;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        if (marginLayoutParams == null) {
            return null;
        }
        return new g(this, marginLayoutParams, this.c.c(marginLayoutParams), view);
    }

    public View f0() {
        WeakReference weakReference = this.u;
        if (weakReference != null) {
            return (View)weakReference.get();
        }
        return null;
    }

    public int g0() {
        return this.c.d();
    }

    public final int h0() {
        w2.c c3 = this.c;
        if (c3 != null) {
            if (c3.j() == 0) {
                return 5;
            }
            return 3;
        }
        return 5;
    }

    public float i0() {
        return this.o;
    }

    public float j0() {
        return 0.5f;
    }

    @Override
    public void k(CoordinatorLayout.e e3) {
        super.k(e3);
        this.t = null;
        this.m = null;
        this.x = null;
    }

    public int k0() {
        return this.s;
    }

    public int l0(int n3) {
        if (n3 != 3) {
            if (n3 == 5) {
                return this.c.e();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid state to get outer edge offset: ");
            stringBuilder.append(n3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return this.g0();
    }

    public int m0() {
        return this.r;
    }

    @Override
    public void n() {
        super.n();
        this.t = null;
        this.m = null;
        this.x = null;
    }

    public int n0() {
        return this.q;
    }

    @Override
    public boolean o(CoordinatorLayout object, View view, MotionEvent motionEvent) {
        if (!this.J0(view)) {
            this.n = true;
            return false;
        }
        int n3 = motionEvent.getActionMasked();
        if (n3 == 0) {
            this.z0();
        }
        if (this.w == null) {
            this.w = VelocityTracker.obtain();
        }
        this.w.addMovement(motionEvent);
        if (n3 != 0) {
            if ((n3 == 1 || n3 == 3) && this.n) {
                this.n = false;
                return false;
            }
        } else {
            this.y = (int)motionEvent.getX();
        }
        return !this.n && (object = this.m) != null && ((v0.c)object).P(motionEvent);
    }

    public int o0() {
        return 500;
    }

    @Override
    public boolean p(CoordinatorLayout object, View view, int n3) {
        i i3;
        if (object.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.t == null) {
            this.t = new WeakReference<View>(view);
            this.x = new j(view);
            i3 = this.e;
            if (i3 != null) {
                float f3;
                view.setBackground((Drawable)i3);
                i3 = this.e;
                float f4 = f3 = this.i;
                if (f3 == -1.0f) {
                    f4 = view.getElevation();
                }
                i3.h0(f4);
            } else {
                i3 = this.f;
                if (i3 != null) {
                    x0.l0(view, (ColorStateList)i3);
                }
            }
            this.P0(view);
            this.M0();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
            this.b0(view);
        }
        this.E0(view, n3);
        if (this.m == null) {
            this.m = v0.c.o((ViewGroup)object, this.A);
        }
        int n4 = this.c.h(view);
        ((CoordinatorLayout)object).M(view, n3);
        this.q = object.getWidth();
        this.r = this.c.i((CoordinatorLayout)object);
        this.p = view.getWidth();
        i3 = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        n3 = i3 != null ? this.c.a((ViewGroup.MarginLayoutParams)i3) : 0;
        this.s = n3;
        x0.R(view, this.U(n4, view));
        this.x0((CoordinatorLayout)object);
        object = this.z.iterator();
        while (object.hasNext()) {
            androidx.appcompat.app.s.a(object.next());
        }
        return true;
    }

    public v0.c p0() {
        return this.m;
    }

    @Override
    public boolean q(CoordinatorLayout coordinatorLayout, View view, int n3, int n4, int n5, int n6) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(this.c0(n3, coordinatorLayout.getPaddingLeft() + coordinatorLayout.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n4, -1, marginLayoutParams.width), this.c0(n5, coordinatorLayout.getPaddingTop() + coordinatorLayout.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n6, -1, marginLayoutParams.height));
        return true;
    }

    public final CoordinatorLayout.e q0() {
        WeakReference weakReference = this.t;
        if (weakReference != null && (weakReference = (View)weakReference.get()) != null && weakReference.getLayoutParams() instanceof CoordinatorLayout.e) {
            return (CoordinatorLayout.e)weakReference.getLayoutParams();
        }
        return null;
    }

    public final boolean r0() {
        CoordinatorLayout.e e3 = this.q0();
        return e3 != null && e3.leftMargin > 0;
    }

    public final boolean s0() {
        CoordinatorLayout.e e3 = this.q0();
        return e3 != null && e3.rightMargin > 0;
    }

    public final boolean t0(MotionEvent motionEvent) {
        if (!this.H0()) {
            return false;
        }
        return this.V(this.y, motionEvent.getX()) > (float)this.m.z();
    }

    public final boolean u0(float f3) {
        return this.c.k(f3);
    }

    public final boolean v0(View view) {
        ViewParent viewParent = view.getParent();
        return viewParent != null && viewParent.isLayoutRequested() && view.isAttachedToWindow();
    }

    public final boolean w0(View view, int n3, boolean bl) {
        n3 = this.l0(n3);
        v0.c c3 = this.p0();
        return c3 != null && (bl ? c3.O(n3, view.getTop()) : c3.Q(view, n3, view.getTop()));
    }

    public final void x0(CoordinatorLayout coordinatorLayout) {
        int n3;
        if (this.u == null && (n3 = this.v) != -1 && (coordinatorLayout = coordinatorLayout.findViewById(n3)) != null) {
            this.u = new WeakReference<CoordinatorLayout>(coordinatorLayout);
        }
    }

    public final void z0() {
        VelocityTracker velocityTracker = this.w;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.w = null;
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
        public final int e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, SideSheetBehavior sideSheetBehavior) {
            super(parcelable);
            this.e = sideSheetBehavior.k;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeInt(this.e);
        }
    }

    public class c {
        public int a;
        public boolean b;
        public final Runnable c;
        public final SideSheetBehavior d;

        public c(SideSheetBehavior sideSheetBehavior) {
            this.d = sideSheetBehavior;
            this.c = new h(this);
        }

        public static /* synthetic */ void a(c c3) {
            c3.b = false;
            if (c3.d.m != null && c3.d.m.m(true)) {
                c3.b(c3.a);
                return;
            }
            if (c3.d.k == 2) {
                c3.d.G0(c3.a);
            }
        }

        public void b(int n3) {
            if (this.d.t != null && this.d.t.get() != null) {
                this.a = n3;
                if (!this.b) {
                    ((View)this.d.t.get()).postOnAnimation(this.c);
                    this.b = true;
                }
            }
        }
    }
}

