/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewOutlineProvider
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 *  android.widget.AbsListView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ScrollView
 */
package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.appcompat.app.s;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.appbar.HeaderBehavior;
import com.google.android.material.appbar.HeaderScrollingViewBehavior;
import com.google.android.material.appbar.a;
import com.google.android.material.internal.z;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import o0.a0;
import o0.f0;
import o0.x0;
import o0.z1;
import p0.s;
import p2.k;
import v2.i;
import v2.j;
import z1.h;
import z1.l;
import z1.m;

public class AppBarLayout
extends LinearLayout
implements CoordinatorLayout.b {
    public static final int D = z1.l.Widget_Design_AppBarLayout;
    public Integer A;
    public final float B;
    public Behavior C;
    public int c;
    public int d;
    public int e;
    public int f;
    public boolean g;
    public int h;
    public z1 i;
    public List j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public ColorStateList o;
    public int p;
    public WeakReference q;
    public ValueAnimator r;
    public ValueAnimator.AnimatorUpdateListener s;
    public final List t;
    public final LinkedHashSet u;
    public final long v;
    public final TimeInterpolator w;
    public int[] x;
    public int y;
    public Drawable z;

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.appBarLayoutStyle);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet, int n3) {
        int n4 = D;
        super(y2.a.d(context, attributeSet, n3, n4), attributeSet, n3);
        this.d = -1;
        this.e = -1;
        this.f = -1;
        this.h = 0;
        this.t = new ArrayList();
        this.u = new LinkedHashSet();
        context = this.getContext();
        this.setOrientation(1);
        if (this.getOutlineProvider() == ViewOutlineProvider.BACKGROUND) {
            com.google.android.material.appbar.d.a((View)this);
        }
        com.google.android.material.appbar.d.c((View)this, attributeSet, n3, n4);
        attributeSet = com.google.android.material.internal.z.i(context, attributeSet, z1.m.AppBarLayout, n3, n4, new int[0]);
        this.o = s2.c.a(context, (TypedArray)attributeSet, z1.m.AppBarLayout_liftOnScrollColor);
        this.v = p2.k.f(context, z1.c.motionDurationMedium2, this.getResources().getInteger(z1.h.app_bar_elevation_anim_duration));
        this.w = p2.k.g(context, z1.c.motionEasingStandardInterpolator, a2.a.a);
        n3 = z1.m.AppBarLayout_expanded;
        if (attributeSet.hasValue(n3)) {
            this.C(attributeSet.getBoolean(n3, false), false, false);
        }
        if (attributeSet.hasValue(n3 = z1.m.AppBarLayout_elevation)) {
            com.google.android.material.appbar.d.b((View)this, attributeSet.getDimensionPixelSize(n3, 0));
        }
        this.setBackground(attributeSet.getDrawable(z1.m.AppBarLayout_android_background));
        n3 = z1.m.AppBarLayout_android_keyboardNavigationCluster;
        if (attributeSet.hasValue(n3)) {
            this.setKeyboardNavigationCluster(attributeSet.getBoolean(n3, false));
        }
        if (attributeSet.hasValue(n3 = z1.m.AppBarLayout_android_touchscreenBlocksFocus)) {
            this.setTouchscreenBlocksFocus(attributeSet.getBoolean(n3, false));
        }
        this.B = this.getResources().getDimension(z1.e.design_appbar_elevation);
        this.n = attributeSet.getBoolean(z1.m.AppBarLayout_liftOnScroll, false);
        this.p = attributeSet.getResourceId(z1.m.AppBarLayout_liftOnScrollTargetViewId, -1);
        this.setStatusBarForeground(attributeSet.getDrawable(z1.m.AppBarLayout_statusBarForeground));
        attributeSet.recycle();
        x0.r0((View)this, new f0(this){
            public final AppBarLayout a;
            {
                this.a = appBarLayout;
            }

            @Override
            public z1 a(View view, z1 z12) {
                return this.a.x(z12);
            }
        });
    }

    public static /* synthetic */ void a(AppBarLayout appBarLayout, i i3, ValueAnimator object) {
        appBarLayout.getClass();
        float f3 = ((Float)object.getAnimatedValue()).floatValue();
        i3.h0(f3);
        object = appBarLayout.z;
        if (object instanceof i) {
            ((i)object).h0(f3);
        }
        if (!(object = appBarLayout.t.iterator()).hasNext()) {
            object = ((AbstractCollection)appBarLayout.u).iterator();
            while (object.hasNext()) {
                ((e)object.next()).a(f3, i3.G(), f3 / appBarLayout.B);
            }
            return;
        }
        androidx.appcompat.app.s.a(object.next());
        i3.G();
        throw null;
    }

    public static /* synthetic */ void b(AppBarLayout object, ColorStateList object2, i i3, Integer n3, ValueAnimator valueAnimator) {
        object.getClass();
        float f3 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
        int n4 = h2.a.j(((AppBarLayout)object).y, object2.getDefaultColor(), f3);
        i3.i0(ColorStateList.valueOf((int)n4));
        if (((AppBarLayout)object).z != null && (object2 = ((AppBarLayout)object).A) != null && ((Integer)object2).equals(n3)) {
            ((AppBarLayout)object).z.setTint(n4);
        }
        if (!((AppBarLayout)object).t.isEmpty()) {
            object2 = ((AppBarLayout)object).t.iterator();
            while (object2.hasNext()) {
                androidx.appcompat.app.s.a(object2.next());
                if (i3.D() == null) continue;
                throw null;
            }
        }
        if (!((AbstractCollection)((AppBarLayout)object).u).isEmpty()) {
            object = ((AbstractCollection)((AppBarLayout)object).u).iterator();
            while (object.hasNext()) {
                ((e)object.next()).a(0.0f, n4, f3);
            }
        }
    }

    public void A(f f3) {
        this.z(f3);
    }

    public void B() {
        this.h = 0;
    }

    public final void C(boolean bl, boolean bl2, boolean bl3) {
        int n3 = bl ? 1 : 2;
        int n4 = 0;
        int n5 = bl2 ? 4 : 0;
        if (bl3) {
            n4 = 8;
        }
        this.h = n3 | n5 | n4;
        this.requestLayout();
    }

    public final boolean D(boolean bl) {
        if (this.l != bl) {
            this.l = bl;
            this.refreshDrawableState();
            return true;
        }
        return false;
    }

    public boolean E(boolean bl) {
        return this.F(bl, this.k ^ true);
    }

    public boolean F(boolean bl, boolean bl2) {
        if (bl2 && this.m != bl) {
            this.m = bl;
            this.refreshDrawableState();
            if (this.s()) {
                ColorStateList colorStateList = this.o;
                float f3 = 0.0f;
                float f4 = 0.0f;
                if (colorStateList != null) {
                    float f5 = bl ? 0.0f : 1.0f;
                    f3 = f4;
                    if (bl) {
                        f3 = 1.0f;
                    }
                    this.J(f5, f3);
                } else if (this.n) {
                    float f6 = bl ? 0.0f : this.B;
                    if (bl) {
                        f3 = this.B;
                    }
                    this.J(f6, f3);
                }
            }
            return true;
        }
        return false;
    }

    public final boolean G() {
        return this.z != null && this.getTopInset() > 0;
    }

    public boolean H(View view) {
        View view2 = this.h(view);
        if (view2 != null) {
            view = view2;
        }
        return view != null && (view.canScrollVertically(-1) || view.getScrollY() > 0);
    }

    public final boolean I() {
        View view;
        return this.getChildCount() > 0 && (view = this.getChildAt(0)).getVisibility() != 8 && !view.getFitsSystemWindows();
    }

    public final void J(float f3, float f4) {
        ValueAnimator valueAnimator = this.r;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.r = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{f3, f4});
        valueAnimator.setDuration(this.v);
        this.r.setInterpolator(this.w);
        valueAnimator = this.s;
        if (valueAnimator != null) {
            this.r.addUpdateListener((ValueAnimator.AnimatorUpdateListener)valueAnimator);
        }
        this.r.start();
    }

    public final void K() {
        this.setWillNotDraw(this.G() ^ true);
    }

    public void c(e e3) {
        ((AbstractCollection)this.u).add(e3);
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void d(b b3) {
        if (this.j == null) {
            this.j = new ArrayList();
        }
        if (b3 != null && !this.j.contains(b3)) {
            this.j.add(b3);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.G()) {
            int n3 = canvas.save();
            canvas.translate(0.0f, (float)(-this.c));
            this.z.draw(canvas);
            canvas.restoreToCount(n3);
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        Drawable drawable = this.z;
        if (drawable != null && drawable.isStateful() && drawable.setState(nArray)) {
            this.invalidateDrawable(drawable);
        }
    }

    public void e(f f3) {
        this.d(f3);
    }

    public final void f() {
        WeakReference weakReference = this.q;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.q = null;
    }

    public final Integer g() {
        Drawable drawable = this.z;
        if (drawable instanceof i) {
            return ((i)drawable).G();
        }
        if ((drawable = j2.d.g(drawable)) != null) {
            return drawable.getDefaultColor();
        }
        return null;
    }

    @Override
    public CoordinatorLayout.Behavior<AppBarLayout> getBehavior() {
        Behavior behavior;
        this.C = behavior = new Behavior();
        return behavior;
    }

    /*
     * Unable to fully structure code
     */
    public int getDownNestedPreScrollRange() {
        var1_1 = this.e;
        if (var1_1 != -1) {
            return var1_1;
        }
        var2_3 = 0;
        for (var3_2 = this.getChildCount() - 1; var3_2 >= 0; --var3_2) {
            var6_6 = this.getChildAt(var3_2);
            if (var6_6.getVisibility() == 8) {
                var1_1 = var2_3;
            } else {
                var7_7 = (LayoutParams)var6_6.getLayoutParams();
                var5_5 = var6_6.getMeasuredHeight();
                var1_1 = var7_7.a;
                if ((var1_1 & 5) == 5) {
                    var4_4 = var7_7.topMargin + var7_7.bottomMargin;
                    if ((var1_1 & 8) != 0) {
                        var1_1 = var6_6.getMinimumHeight();
lbl17:
                        // 2 sources

                        while (true) {
                            var1_1 = var4_4 + var1_1;
                            break;
                        }
                    } else {
                        if ((var1_1 & 2) != 0) {
                            var1_1 = var5_5 - var6_6.getMinimumHeight();
                            ** continue;
                        }
                        var1_1 = var4_4 + var5_5;
                    }
                    var4_4 = var1_1;
                    if (var3_2 == 0) {
                        var4_4 = var1_1;
                        if (var6_6.getFitsSystemWindows()) {
                            var4_4 = Math.min(var1_1, var5_5 - this.getTopInset());
                        }
                    }
                    var1_1 = var2_3 + var4_4;
                } else {
                    var1_1 = var2_3;
                    if (var2_3 > 0) break;
                }
            }
            var2_3 = var1_1;
        }
        this.e = var1_1 = Math.max(0, var2_3);
        return var1_1;
    }

    public int getDownNestedScrollRange() {
        int n3;
        int n4 = this.f;
        if (n4 != -1) {
            return n4;
        }
        int n5 = this.getChildCount();
        int n6 = 0;
        n4 = 0;
        while (true) {
            n3 = n4;
            if (n6 >= n5) break;
            View view = this.getChildAt(n6);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n7 = view.getMeasuredHeight();
                int n8 = layoutParams.topMargin;
                int n9 = layoutParams.bottomMargin;
                int n10 = layoutParams.a;
                n3 = n4;
                if ((n10 & 1) == 0) break;
                n4 = n3 = n4 + (n7 + (n8 + n9));
                if ((n10 & 2) != 0) {
                    n3 -= view.getMinimumHeight();
                    break;
                }
            }
            ++n6;
        }
        this.f = n4 = Math.max(0, n3);
        return n4;
    }

    public int getLiftOnScrollTargetViewId() {
        return this.p;
    }

    public i getMaterialShapeBackground() {
        Drawable drawable = this.getBackground();
        if (drawable instanceof i) {
            return (i)drawable;
        }
        return null;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int n3 = this.getTopInset();
        int n4 = this.getMinimumHeight();
        if (n4 != 0) {
            int n5 = n4 * 2 + n3;
            if (n5 < this.getHeight()) {
                return n5;
            }
            return n4 + n3;
        }
        n4 = this.getChildCount();
        n4 = n4 >= 1 ? this.getChildAt(n4 - 1).getMinimumHeight() : 0;
        if (n4 != 0) {
            int n6 = n4 * 2 + n3;
            if (n6 < this.getHeight()) {
                return n6;
            }
            return n4 + n3;
        }
        return this.getHeight() / 3;
    }

    public int getPendingAction() {
        return this.h;
    }

    public Drawable getStatusBarForeground() {
        return this.z;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    public final int getTopInset() {
        z1 z12 = this.i;
        if (z12 != null) {
            return z12.l();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int n3;
        int n4 = this.d;
        if (n4 != -1) {
            return n4;
        }
        int n5 = this.getChildCount();
        int n6 = 0;
        n4 = 0;
        while (true) {
            n3 = n4;
            if (n6 >= n5) break;
            View view = this.getChildAt(n6);
            if (view.getVisibility() == 8) {
                n3 = n4;
            } else {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n7 = view.getMeasuredHeight();
                int n8 = layoutParams.a;
                n3 = n4;
                if ((n8 & 1) == 0) break;
                n4 = n3 = n4 + (n7 + layoutParams.topMargin + layoutParams.bottomMargin);
                if (n6 == 0) {
                    n4 = n3;
                    if (view.getFitsSystemWindows()) {
                        n4 = n3 - this.getTopInset();
                    }
                }
                n3 = n4;
                if ((n8 & 2) != 0) {
                    n3 = n4 - view.getMinimumHeight();
                    break;
                }
            }
            ++n6;
            n4 = n3;
        }
        this.d = n4 = Math.max(0, n3);
        return n4;
    }

    public int getUpNestedPreScrollRange() {
        return this.getTotalScrollRange();
    }

    public final View h(View object) {
        int n3;
        if (this.q == null && (n3 = this.p) != -1) {
            object = object != null ? object.findViewById(n3) : null;
            Object object2 = object;
            if (object == null) {
                object2 = object;
                if (this.getParent() instanceof ViewGroup) {
                    object2 = ((ViewGroup)this.getParent()).findViewById(this.p);
                }
            }
            if (object2 != null) {
                this.q = new WeakReference<Object>(object2);
            }
        }
        if ((object = this.q) != null) {
            return (View)((Reference)object).get();
        }
        return null;
    }

    public LayoutParams i() {
        return new LayoutParams(-1, -2);
    }

    public LayoutParams j(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public LayoutParams k(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public boolean l() {
        return this.g;
    }

    public final boolean m() {
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!((LayoutParams)this.getChildAt(i3).getLayoutParams()).e()) continue;
            return true;
        }
        return false;
    }

    public boolean n() {
        return this.getTotalScrollRange() != 0;
    }

    public final void o(i i3, ColorStateList colorStateList) {
        this.s = new a(this, colorStateList, i3, h2.a.f(this.getContext(), z1.c.colorSurface));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.e((View)this);
    }

    public int[] onCreateDrawableState(int n3) {
        if (this.x == null) {
            this.x = new int[4];
        }
        int[] nArray = this.x;
        int[] nArray2 = super.onCreateDrawableState(n3 + nArray.length);
        boolean bl = this.l;
        n3 = z1.c.state_liftable;
        if (!bl) {
            n3 = -n3;
        }
        nArray[0] = n3;
        n3 = bl && this.m ? z1.c.state_lifted : -z1.c.state_lifted;
        nArray[1] = n3;
        n3 = z1.c.state_collapsible;
        if (!bl) {
            n3 = -n3;
        }
        nArray[2] = n3;
        n3 = bl && this.m ? z1.c.state_collapsed : -z1.c.state_collapsed;
        nArray[3] = n3;
        return View.mergeDrawableStates((int[])nArray2, (int[])nArray);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f();
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        Drawable drawable;
        super.onLayout(bl, n3, n4, n5, n6);
        bl = this.getFitsSystemWindows();
        boolean bl2 = true;
        if (bl && this.I()) {
            n4 = this.getTopInset();
            for (n3 = this.getChildCount() - 1; n3 >= 0; --n3) {
                x0.S(this.getChildAt(n3), n4);
            }
        }
        this.q();
        this.g = false;
        n4 = this.getChildCount();
        for (n3 = 0; n3 < n4; ++n3) {
            if (((LayoutParams)this.getChildAt(n3).getLayoutParams()).d() == null) continue;
            this.g = true;
            break;
        }
        if ((drawable = this.z) != null) {
            drawable.setBounds(0, 0, this.getWidth(), this.getTopInset());
        }
        if (!this.k) {
            bl = bl2;
            if (!this.n) {
                bl = this.m() ? bl2 : false;
            }
            this.D(bl);
        }
    }

    public void onMeasure(int n3, int n4) {
        super.onMeasure(n3, n4);
        int n5 = View.MeasureSpec.getMode((int)n4);
        if (n5 != 0x40000000 && this.getFitsSystemWindows() && this.I()) {
            n3 = this.getMeasuredHeight();
            if (n5 != Integer.MIN_VALUE) {
                if (n5 == 0) {
                    n3 += this.getTopInset();
                }
            } else {
                n3 = j0.a.b(this.getMeasuredHeight() + this.getTopInset(), 0, View.MeasureSpec.getSize((int)n4));
            }
            this.setMeasuredDimension(this.getMeasuredWidth(), n3);
        }
        this.q();
    }

    public final void p(Context context, i i3) {
        i3.W(context);
        this.s = new com.google.android.material.appbar.b(this, i3);
    }

    public final void q() {
        Object object = this.C;
        object = object != null && this.d != -1 && this.h == 0 ? ((BaseBehavior)object).x0(AbsSavedState.d, this) : null;
        this.d = -1;
        this.e = -1;
        this.f = -1;
        if (object != null) {
            this.C.w0((BaseBehavior.SavedState)object, false);
        }
    }

    public boolean r() {
        return this.n;
    }

    public final boolean s() {
        return this.getBackground() instanceof i;
    }

    public void setBackground(Drawable drawable) {
        super.setBackground(this.v(this.getContext(), drawable));
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        v2.j.d((View)this, f3);
    }

    public void setExpanded(boolean bl) {
        this.setExpanded(bl, this.isLaidOut());
    }

    public void setExpanded(boolean bl, boolean bl2) {
        this.C(bl, bl2, true);
    }

    public void setLiftOnScroll(boolean bl) {
        this.n = bl;
    }

    public void setLiftOnScrollColor(ColorStateList colorStateList) {
        if (this.o != colorStateList) {
            this.o = colorStateList;
            this.setBackground(this.getBackground());
        }
    }

    public void setLiftOnScrollTargetView(View view) {
        this.p = -1;
        if (view == null) {
            this.f();
            return;
        }
        this.q = new WeakReference<View>(view);
    }

    public void setLiftOnScrollTargetViewId(int n3) {
        this.p = n3;
        this.f();
    }

    public void setLiftableOverrideEnabled(boolean bl) {
        this.k = bl;
    }

    public void setOrientation(int n3) {
        if (n3 == 1) {
            super.setOrientation(n3);
            return;
        }
        throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
    }

    public void setPendingAction(int n3) {
        this.h = n3;
    }

    public void setStatusBarForeground(Drawable drawable) {
        Drawable drawable2 = this.z;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.z = drawable3;
            this.A = this.g();
            drawable = this.z;
            if (drawable != null) {
                if (drawable.isStateful()) {
                    this.z.setState(this.getDrawableState());
                }
                h0.a.m(this.z, this.getLayoutDirection());
                drawable = this.z;
                boolean bl = this.getVisibility() == 0;
                drawable.setVisible(bl, false);
                this.z.setCallback((Drawable.Callback)this);
            }
            this.K();
            this.postInvalidateOnAnimation();
        }
    }

    public void setStatusBarForegroundColor(int n3) {
        this.setStatusBarForeground((Drawable)new ColorDrawable(n3));
    }

    public void setStatusBarForegroundResource(int n3) {
        this.setStatusBarForeground(d.a.b(this.getContext(), n3));
    }

    @Deprecated
    public void setTargetElevation(float f3) {
        com.google.android.material.appbar.d.b((View)this, f3);
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        boolean bl = n3 == 0;
        Drawable drawable = this.z;
        if (drawable != null) {
            drawable.setVisible(bl, false);
        }
    }

    public boolean t() {
        return this.m;
    }

    public final i u(Drawable drawable) {
        if (drawable instanceof i) {
            return (i)drawable;
        }
        ColorStateList colorStateList = j2.d.g(drawable);
        if (colorStateList == null) {
            return null;
        }
        drawable = new i();
        drawable.i0(colorStateList);
        return drawable;
    }

    public final Drawable v(Context context, Drawable drawable) {
        i i3 = this.u(drawable);
        if (i3 != null && i3.D() != null) {
            this.y = i3.D().getDefaultColor();
            drawable = this.o;
            if (drawable != null) {
                this.o(i3, (ColorStateList)drawable);
                return i3;
            }
            this.p(context, i3);
            return i3;
        }
        return drawable;
    }

    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.z;
        {
        }
    }

    public void w(int n3) {
        Object object;
        this.c = n3;
        if (!this.willNotDraw()) {
            this.postInvalidateOnAnimation();
        }
        if ((object = this.j) != null) {
            int n4 = object.size();
            for (int i3 = 0; i3 < n4; ++i3) {
                object = (b)this.j.get(i3);
                if (object == null) continue;
                object.a(this, n3);
            }
        }
    }

    public z1 x(z1 z12) {
        z1 z13 = this.getFitsSystemWindows() ? z12 : null;
        if (!n0.c.a(this.i, z13)) {
            this.i = z13;
            this.K();
            this.requestLayout();
        }
        return z12;
    }

    public boolean y(e e3) {
        return ((AbstractCollection)this.u).remove(e3);
    }

    public void z(b b3) {
        List list = this.j;
        if (list != null && b3 != null) {
            list.remove(b3);
        }
    }

    public static class BaseBehavior<T extends AppBarLayout>
    extends HeaderBehavior<T> {
        public int m;
        public int n;
        public ValueAnimator o;
        public SavedState p;
        public WeakReference q;

        public BaseBehavior() {
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public static boolean e0(int n3, int n4) {
            return (n3 & n4) == n4;
        }

        public static View h0(AppBarLayout appBarLayout, int n3) {
            int n4 = Math.abs(n3);
            int n5 = appBarLayout.getChildCount();
            for (n3 = 0; n3 < n5; ++n3) {
                View view = appBarLayout.getChildAt(n3);
                if (n4 < view.getTop() || n4 > view.getBottom()) continue;
                return view;
            }
            return null;
        }

        public final void A0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            View view;
            LayoutParams layoutParams;
            int n3;
            int n4 = appBarLayout.getTopInset() + appBarLayout.getPaddingTop();
            int n5 = this.Q() - n4;
            int n6 = this.i0(appBarLayout, n5);
            if (n6 >= 0 && ((n3 = (layoutParams = (LayoutParams)(view = appBarLayout.getChildAt(n6)).getLayoutParams()).c()) & 0x11) == 17) {
                int n7 = -view.getTop();
                int n8 = -view.getBottom();
                int n9 = n7;
                if (n6 == 0) {
                    n9 = n7;
                    if (appBarLayout.getFitsSystemWindows()) {
                        n9 = n7;
                        if (view.getFitsSystemWindows()) {
                            n9 = n7 - appBarLayout.getTopInset();
                        }
                    }
                }
                if (BaseBehavior.e0(n3, 2)) {
                    n7 = n8 + view.getMinimumHeight();
                    n6 = n9;
                } else {
                    n6 = n9;
                    n7 = n8;
                    if (BaseBehavior.e0(n3, 5)) {
                        n7 = view.getMinimumHeight() + n8;
                        if (n5 < n7) {
                            n6 = n7;
                            n7 = n8;
                        } else {
                            n6 = n9;
                        }
                    }
                }
                n8 = n6;
                n9 = n7;
                if (BaseBehavior.e0(n3, 32)) {
                    n8 = n6 + layoutParams.topMargin;
                    n9 = n7 - layoutParams.bottomMargin;
                }
                this.Z(coordinatorLayout, appBarLayout, j0.a.b(this.b0(n5, n9, n8) + n4, -appBarLayout.getTotalScrollRange(), 0), 0.0f);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public final void B0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n3, int n4, boolean bl) {
            boolean bl2;
            block8: {
                block10: {
                    int n5;
                    int n6;
                    boolean bl3;
                    View view;
                    block9: {
                        view = BaseBehavior.h0(appBarLayout, n3);
                        bl2 = bl3 = false;
                        if (view == null) break block8;
                        n6 = ((LayoutParams)view.getLayoutParams()).c();
                        bl2 = bl3;
                        if ((n6 & 1) == 0) break block8;
                        n5 = view.getMinimumHeight();
                        if (n4 <= 0 || (n6 & 0xC) == 0) break block9;
                        bl2 = bl3;
                        if (-n3 < view.getBottom() - n5 - appBarLayout.getTopInset()) break block8;
                        break block10;
                    }
                    bl2 = bl3;
                    if ((n6 & 2) == 0) break block8;
                    bl2 = bl3;
                    if (-n3 < view.getBottom() - n5 - appBarLayout.getTopInset()) break block8;
                }
                bl2 = true;
            }
            if (appBarLayout.r()) {
                bl2 = appBarLayout.H(this.g0(coordinatorLayout));
            }
            bl2 = appBarLayout.E(bl2);
            if (bl || bl2 && this.z0(coordinatorLayout, appBarLayout)) {
                if (appBarLayout.getBackground() != null) {
                    appBarLayout.getBackground().jumpToCurrentState();
                }
                if (appBarLayout.getForeground() != null) {
                    appBarLayout.getForeground().jumpToCurrentState();
                }
                if (appBarLayout.getStateListAnimator() != null) {
                    appBarLayout.getStateListAnimator().jumpToCurrentState();
                }
            }
        }

        @Override
        public int Q() {
            return this.I() + this.m;
        }

        public final void Y(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            if (!x0.K((View)coordinatorLayout)) {
                x0.h0((View)coordinatorLayout, new o0.a(this, appBarLayout, coordinatorLayout){
                    public final AppBarLayout d;
                    public final CoordinatorLayout e;
                    public final BaseBehavior f;
                    {
                        this.f = baseBehavior;
                        this.d = appBarLayout;
                        this.e = coordinatorLayout;
                    }

                    @Override
                    public void g(View view, p0.s s3) {
                        super.g(view, s3);
                        s3.h0(ScrollView.class.getName());
                        if (this.d.getTotalScrollRange() != 0 && (view = this.f.j0(this.e)) != null && this.f.f0(this.d)) {
                            if (this.f.Q() != -this.d.getTotalScrollRange()) {
                                s3.b(s.a.q);
                                s3.B0(true);
                            }
                            if (this.f.Q() != 0) {
                                if (view.canScrollVertically(-1)) {
                                    if (-this.d.getDownNestedPreScrollRange() != 0) {
                                        s3.b(s.a.r);
                                        s3.B0(true);
                                        return;
                                    }
                                } else {
                                    s3.b(s.a.r);
                                    s3.B0(true);
                                }
                            }
                        }
                    }

                    @Override
                    public boolean j(View view, int n3, Bundle bundle) {
                        if (n3 == 4096) {
                            this.d.setExpanded(false);
                            return true;
                        }
                        if (n3 == 8192) {
                            if (this.f.Q() != 0) {
                                view = this.f.j0(this.e);
                                if (view.canScrollVertically(-1)) {
                                    n3 = -this.d.getDownNestedPreScrollRange();
                                    if (n3 != 0) {
                                        this.f.q0(this.e, this.d, view, 0, n3, new int[]{0, 0}, 1);
                                        return true;
                                    }
                                } else {
                                    this.d.setExpanded(true);
                                    return true;
                                }
                            }
                            return false;
                        }
                        return super.j(view, n3, bundle);
                    }
                });
            }
        }

        public final void Z(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n3, float f3) {
            int n4 = Math.abs(this.Q() - n3);
            n4 = (f3 = Math.abs(f3)) > 0.0f ? Math.round((float)n4 / f3 * 1000.0f) * 3 : (int)(((float)n4 / (float)appBarLayout.getHeight() + 1.0f) * 150.0f);
            this.a0(coordinatorLayout, appBarLayout, n3, n4);
        }

        public final void a0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n3, int n4) {
            int n5 = this.Q();
            if (n5 == n3) {
                coordinatorLayout = this.o;
                if (coordinatorLayout != null && coordinatorLayout.isRunning()) {
                    this.o.cancel();
                }
                return;
            }
            ValueAnimator valueAnimator = this.o;
            if (valueAnimator == null) {
                this.o = valueAnimator = new ValueAnimator();
                valueAnimator.setInterpolator(a2.a.e);
                this.o.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this, coordinatorLayout, appBarLayout){
                    public final CoordinatorLayout a;
                    public final AppBarLayout b;
                    public final BaseBehavior c;
                    {
                        this.c = baseBehavior;
                        this.a = coordinatorLayout;
                        this.b = appBarLayout;
                    }

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.c.T(this.a, (View)this.b, (Integer)valueAnimator.getAnimatedValue());
                    }
                });
            } else {
                valueAnimator.cancel();
            }
            this.o.setDuration((long)Math.min(n4, 600));
            this.o.setIntValues(new int[]{n5, n3});
            this.o.start();
        }

        public final int b0(int n3, int n4, int n5) {
            if (n3 < (n4 + n5) / 2) {
                return n4;
            }
            return n5;
        }

        public boolean c0(AppBarLayout object) {
            object = this.q;
            if (object != null) {
                return (object = (View)((Reference)object).get()) != null && object.isShown() && !object.canScrollVertically(-1);
            }
            return true;
        }

        public final boolean d0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view) {
            return appBarLayout.n() && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight();
        }

        public final boolean f0(AppBarLayout appBarLayout) {
            int n3 = appBarLayout.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                if (((LayoutParams)appBarLayout.getChildAt((int)i3).getLayoutParams()).a == 0) continue;
                return true;
            }
            return false;
        }

        public final View g0(CoordinatorLayout coordinatorLayout) {
            int n3 = coordinatorLayout.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                View view = coordinatorLayout.getChildAt(i3);
                if (!(view instanceof a0 || view instanceof AbsListView || view instanceof ScrollView)) {
                    continue;
                }
                return view;
            }
            return null;
        }

        public final int i0(AppBarLayout appBarLayout, int n3) {
            int n4 = appBarLayout.getChildCount();
            for (int i3 = 0; i3 < n4; ++i3) {
                Object object = appBarLayout.getChildAt(i3);
                int n5 = object.getTop();
                int n6 = object.getBottom();
                object = (LayoutParams)object.getLayoutParams();
                int n7 = n5;
                int n8 = n6;
                if (BaseBehavior.e0(((LayoutParams)((Object)object)).c(), 32)) {
                    n7 = n5 - ((LinearLayout.LayoutParams)object).topMargin;
                    n8 = n6 + ((LinearLayout.LayoutParams)object).bottomMargin;
                }
                if (n7 > (n6 = -n3) || n8 < n6) continue;
                return i3;
            }
            return -1;
        }

        public final View j0(CoordinatorLayout coordinatorLayout) {
            int n3 = coordinatorLayout.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                View view = coordinatorLayout.getChildAt(i3);
                if (!(((CoordinatorLayout.e)view.getLayoutParams()).f() instanceof ScrollingViewBehavior)) continue;
                return view;
            }
            return null;
        }

        public int k0(AppBarLayout appBarLayout) {
            return -appBarLayout.getDownNestedScrollRange() + appBarLayout.getTopInset();
        }

        public int l0(AppBarLayout appBarLayout) {
            return appBarLayout.getTotalScrollRange();
        }

        public final int m0(AppBarLayout appBarLayout, int n3) {
            int n4 = Math.abs(n3);
            int n5 = appBarLayout.getChildCount();
            int n6 = 0;
            for (int i3 = 0; i3 < n5; ++i3) {
                View view = appBarLayout.getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                Interpolator interpolator = layoutParams.d();
                if (n4 < view.getTop() || n4 > view.getBottom()) continue;
                if (interpolator == null) break;
                n5 = layoutParams.c();
                i3 = n6;
                if ((n5 & 1) != 0) {
                    i3 = n6 = view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
                    if ((n5 & 2) != 0) {
                        i3 = n6 - view.getMinimumHeight();
                    }
                }
                n6 = i3;
                if (view.getFitsSystemWindows()) {
                    n6 = i3 - appBarLayout.getTopInset();
                }
                if (n6 <= 0) break;
                i3 = view.getTop();
                float f3 = n6;
                i3 = Math.round(f3 * interpolator.getInterpolation((float)(n4 - i3) / f3));
                return Integer.signum(n3) * (view.getTop() + i3);
            }
            return n3;
        }

        public void n0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            this.A0(coordinatorLayout, appBarLayout);
            if (appBarLayout.r()) {
                appBarLayout.E(appBarLayout.H(this.g0(coordinatorLayout)));
            }
        }

        public boolean o0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n3) {
            boolean bl = super.p(coordinatorLayout, (View)appBarLayout, n3);
            int n4 = appBarLayout.getPendingAction();
            SavedState savedState = this.p;
            if (savedState != null && (n4 & 8) == 0) {
                if (savedState.e) {
                    this.T(coordinatorLayout, (View)appBarLayout, -appBarLayout.getTotalScrollRange());
                } else if (savedState.f) {
                    this.T(coordinatorLayout, (View)appBarLayout, 0);
                } else {
                    savedState = appBarLayout.getChildAt(savedState.g);
                    n4 = -savedState.getBottom();
                    n3 = this.p.i ? savedState.getMinimumHeight() + appBarLayout.getTopInset() : Math.round((float)savedState.getHeight() * this.p.h);
                    this.T(coordinatorLayout, (View)appBarLayout, n4 + n3);
                }
            } else if (n4 != 0) {
                n3 = (n4 & 4) != 0 ? 1 : 0;
                if ((n4 & 2) != 0) {
                    n4 = -appBarLayout.getUpNestedPreScrollRange();
                    if (n3 != 0) {
                        this.Z(coordinatorLayout, appBarLayout, n4, 0.0f);
                    } else {
                        this.T(coordinatorLayout, (View)appBarLayout, n4);
                    }
                } else if ((n4 & 1) != 0) {
                    if (n3 != 0) {
                        this.Z(coordinatorLayout, appBarLayout, 0, 0.0f);
                    } else {
                        this.T(coordinatorLayout, (View)appBarLayout, 0);
                    }
                }
            }
            appBarLayout.B();
            this.p = null;
            this.K(j0.a.b(this.I(), -appBarLayout.getTotalScrollRange(), 0));
            this.B0(coordinatorLayout, appBarLayout, this.I(), 0, true);
            appBarLayout.w(this.I());
            this.Y(coordinatorLayout, appBarLayout);
            return bl;
        }

        public boolean p0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n3, int n4, int n5, int n6) {
            if (((CoordinatorLayout.e)appBarLayout.getLayoutParams()).height == -2) {
                coordinatorLayout.N((View)appBarLayout, n3, n4, View.MeasureSpec.makeMeasureSpec((int)0, (int)0), n6);
                return true;
            }
            return super.q(coordinatorLayout, (View)appBarLayout, n3, n4, n5, n6);
        }

        public void q0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n3, int n4, int[] nArray, int n5) {
            if (n4 != 0) {
                if (n4 < 0) {
                    n5 = -appBarLayout.getTotalScrollRange();
                    n3 = appBarLayout.getDownNestedPreScrollRange() + n5;
                } else {
                    n5 = -appBarLayout.getUpNestedPreScrollRange();
                    n3 = 0;
                }
                if (n5 != n3) {
                    nArray[1] = this.S(coordinatorLayout, (View)appBarLayout, n4, n5, n3);
                }
            }
            if (appBarLayout.r()) {
                appBarLayout.E(appBarLayout.H(view));
            }
        }

        public void r0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n3, int n4, int n5, int n6, int n7, int[] nArray) {
            if (n6 < 0) {
                nArray[1] = this.S(coordinatorLayout, (View)appBarLayout, n6, -appBarLayout.getDownNestedScrollRange(), 0);
            }
            if (n6 == 0) {
                this.Y(coordinatorLayout, appBarLayout);
            }
        }

        public void s0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                this.w0((SavedState)parcelable, true);
                super.B(coordinatorLayout, (View)appBarLayout, this.p.o());
                return;
            }
            super.B(coordinatorLayout, (View)appBarLayout, parcelable);
            this.p = null;
        }

        public Parcelable t0(CoordinatorLayout coordinatorLayout, AppBarLayout object) {
            if ((object = this.x0((Parcelable)(coordinatorLayout = super.C(coordinatorLayout, (View)object)), (AppBarLayout)object)) == null) {
                return coordinatorLayout;
            }
            return object;
        }

        public boolean u0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int n3, int n4) {
            boolean bl = (n3 & 2) != 0 && (appBarLayout.r() || appBarLayout.t() || this.d0(coordinatorLayout, appBarLayout, view));
            if (bl && (coordinatorLayout = this.o) != null) {
                coordinatorLayout.cancel();
            }
            this.q = null;
            this.n = n4;
            return bl;
        }

        public void v0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int n3) {
            if (this.n == 0 || n3 == 1) {
                this.A0(coordinatorLayout, appBarLayout);
                if (appBarLayout.r()) {
                    appBarLayout.E(appBarLayout.H(view));
                }
            }
            this.q = new WeakReference<View>(view);
        }

        public void w0(SavedState savedState, boolean bl) {
            if (this.p != null && !bl) {
                return;
            }
            this.p = savedState;
        }

        public SavedState x0(Parcelable parcelable, AppBarLayout appBarLayout) {
            int n3 = this.I();
            int n4 = appBarLayout.getChildCount();
            boolean bl = false;
            for (int i3 = 0; i3 < n4; ++i3) {
                View view = appBarLayout.getChildAt(i3);
                int n5 = view.getBottom() + n3;
                if (view.getTop() + n3 > 0 || n5 < 0) continue;
                Parcelable parcelable2 = parcelable;
                if (parcelable == null) {
                    parcelable2 = AbsSavedState.d;
                }
                parcelable = new SavedState(parcelable2);
                boolean bl2 = n3 == 0;
                parcelable.f = bl2;
                bl2 = !bl2 && -n3 >= appBarLayout.getTotalScrollRange();
                parcelable.e = bl2;
                parcelable.g = i3;
                bl2 = bl;
                if (n5 == view.getMinimumHeight() + appBarLayout.getTopInset()) {
                    bl2 = true;
                }
                parcelable.i = bl2;
                parcelable.h = (float)n5 / (float)view.getHeight();
                return parcelable;
            }
            return null;
        }

        public int y0(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int n3, int n4, int n5) {
            int n6 = this.Q();
            int n7 = 0;
            int n8 = 0;
            if (n4 != 0 && n6 >= n4 && n6 <= n5) {
                if (n6 != (n4 = j0.a.b(n3, n4, n5))) {
                    n3 = appBarLayout.l() ? this.m0(appBarLayout, n4) : n4;
                    boolean bl = this.K(n3);
                    this.m = n4 - n3;
                    n5 = 1;
                    if (bl) {
                        for (n3 = n8; n3 < appBarLayout.getChildCount(); ++n3) {
                            LayoutParams layoutParams = (LayoutParams)appBarLayout.getChildAt(n3).getLayoutParams();
                            c c3 = layoutParams.b();
                            if (c3 == null || (layoutParams.c() & 1) == 0) continue;
                            c3.a(appBarLayout, appBarLayout.getChildAt(n3), this.I());
                        }
                    }
                    if (!bl && appBarLayout.l()) {
                        coordinatorLayout.p((View)appBarLayout);
                    }
                    appBarLayout.w(this.I());
                    n3 = n5;
                    if (n4 < n6) {
                        n3 = -1;
                    }
                    this.B0(coordinatorLayout, appBarLayout, n4, n3, false);
                    n3 = n6 - n4;
                } else {
                    n3 = n7;
                }
            } else {
                this.m = 0;
                n3 = n7;
            }
            this.Y(coordinatorLayout, appBarLayout);
            return n3;
        }

        public final boolean z0(CoordinatorLayout object, AppBarLayout object2) {
            object2 = ((CoordinatorLayout)object).w((View)object2);
            int n3 = object2.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                object = ((CoordinatorLayout.e)((View)object2.get(i3)).getLayoutParams()).f();
                if (!(object instanceof ScrollingViewBehavior)) continue;
                return ((ScrollingViewBehavior)object).O() != 0;
            }
            return false;
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
            public boolean e;
            public boolean f;
            public int g;
            public float h;
            public boolean i;

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                byte by = parcel.readByte();
                boolean bl = false;
                boolean bl2 = by != 0;
                this.e = bl2;
                bl2 = parcel.readByte() != 0;
                this.f = bl2;
                this.g = parcel.readInt();
                this.h = parcel.readFloat();
                bl2 = bl;
                if (parcel.readByte() != 0) {
                    bl2 = true;
                }
                this.i = bl2;
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }

            @Override
            public void writeToParcel(Parcel parcel, int n3) {
                super.writeToParcel(parcel, n3);
                parcel.writeByte((byte)(this.e ? 1 : 0));
                parcel.writeByte((byte)(this.f ? 1 : 0));
                parcel.writeInt(this.g);
                parcel.writeFloat(this.h);
                parcel.writeByte((byte)(this.i ? 1 : 0));
            }
        }
    }

    public static class Behavior
    extends BaseBehavior<AppBarLayout> {
        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static class LayoutParams
    extends LinearLayout.LayoutParams {
        public int a = 1;
        public c b;
        public Interpolator c;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            attributeSet = context.obtainStyledAttributes(attributeSet, z1.m.AppBarLayout_Layout);
            this.a = attributeSet.getInt(z1.m.AppBarLayout_Layout_layout_scrollFlags, 0);
            this.f(attributeSet.getInt(z1.m.AppBarLayout_Layout_layout_scrollEffect, 0));
            int n3 = z1.m.AppBarLayout_Layout_layout_scrollInterpolator;
            if (attributeSet.hasValue(n3)) {
                this.c = AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(n3, 0));
            }
            attributeSet.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public final c a(int n3) {
            if (n3 != 1) {
                return null;
            }
            return new d();
        }

        public c b() {
            return this.b;
        }

        public int c() {
            return this.a;
        }

        public Interpolator d() {
            return this.c;
        }

        public boolean e() {
            int n3 = this.a;
            return (n3 & 1) == 1 && (n3 & 0xA) != 0;
        }

        public void f(int n3) {
            this.b = this.a(n3);
        }

        public void g(int n3) {
            this.a = n3;
        }
    }

    public static class ScrollingViewBehavior
    extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, z1.m.ScrollingViewBehavior_Layout);
            this.S(context.getDimensionPixelSize(z1.m.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            context.recycle();
        }

        public static int V(AppBarLayout object) {
            if ((object = ((CoordinatorLayout.e)object.getLayoutParams()).f()) instanceof BaseBehavior) {
                return ((BaseBehavior)object).Q();
            }
            return 0;
        }

        @Override
        public boolean A(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean bl) {
            AppBarLayout appBarLayout = this.U(coordinatorLayout.v(view));
            if (appBarLayout != null) {
                rect = new Rect(rect);
                rect.offset(view.getLeft(), view.getTop());
                view = this.f;
                view.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!view.contains(rect)) {
                    appBarLayout.setExpanded(false, bl ^ true);
                    return true;
                }
            }
            return false;
        }

        @Override
        public float N(View object) {
            if (object instanceof AppBarLayout) {
                object = (AppBarLayout)object;
                int n3 = ((AppBarLayout)object).getTotalScrollRange();
                int n4 = ((AppBarLayout)object).getDownNestedPreScrollRange();
                int n5 = ScrollingViewBehavior.V((AppBarLayout)object);
                if (n4 != 0 && n3 + n5 <= n4) {
                    return 0.0f;
                }
                if ((n3 -= n4) != 0) {
                    return (float)n5 / (float)n3 + 1.0f;
                }
            }
            return 0.0f;
        }

        @Override
        public int P(View view) {
            if (view instanceof AppBarLayout) {
                return ((AppBarLayout)view).getTotalScrollRange();
            }
            return super.P(view);
        }

        public AppBarLayout U(List list) {
            int n3 = list.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                View view = (View)list.get(i3);
                if (!(view instanceof AppBarLayout)) continue;
                return (AppBarLayout)view;
            }
            return null;
        }

        public final void W(View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.e)view2.getLayoutParams()).f();
            if (behavior instanceof BaseBehavior) {
                behavior = (BaseBehavior)behavior;
                x0.S(view, view2.getBottom() - view.getTop() + ((BaseBehavior)behavior).m + this.Q() - this.M(view2));
            }
        }

        public final void X(View view, View object) {
            if (object instanceof AppBarLayout && ((AppBarLayout)(object = (AppBarLayout)object)).r()) {
                ((AppBarLayout)object).E(((AppBarLayout)object).H(view));
            }
        }

        @Override
        public boolean i(CoordinatorLayout coordinatorLayout, View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override
        public boolean l(CoordinatorLayout coordinatorLayout, View view, View view2) {
            this.W(view, view2);
            this.X(view, view2);
            return false;
        }

        @Override
        public void m(CoordinatorLayout coordinatorLayout, View view, View view2) {
            if (view2 instanceof AppBarLayout) {
                x0.h0((View)coordinatorLayout, null);
            }
        }
    }

    public static interface b {
        public void a(AppBarLayout var1, int var2);
    }

    public static abstract class c {
        public abstract void a(AppBarLayout var1, View var2, float var3);
    }

    public static class d
    extends c {
        public final Rect a = new Rect();
        public final Rect b = new Rect();

        public static void b(Rect rect, AppBarLayout appBarLayout, View view) {
            view.getDrawingRect(rect);
            appBarLayout.offsetDescendantRectToMyCoords(view, rect);
            rect.offset(0, -appBarLayout.getTopInset());
        }

        @Override
        public void a(AppBarLayout appBarLayout, View view, float f3) {
            com.google.android.material.appbar.AppBarLayout$d.b(this.a, appBarLayout, view);
            float f4 = (float)this.a.top - Math.abs(f3);
            if (f4 <= 0.0f) {
                f3 = j0.a.a(Math.abs(f4 / (float)this.a.height()), 0.0f, 1.0f);
                f4 = -f4;
                f3 = 1.0f - f3;
                f3 = f4 - (float)this.a.height() * 0.3f * (1.0f - f3 * f3);
                view.setTranslationY(f3);
                view.getDrawingRect(this.b);
                this.b.offset(0, (int)(-f3));
                if (f3 >= (float)this.b.height()) {
                    view.setAlpha(0.0f);
                } else {
                    view.setAlpha(1.0f);
                }
                view.setClipBounds(this.b);
                return;
            }
            view.setClipBounds(null);
            view.setTranslationY(0.0f);
            view.setAlpha(1.0f);
        }
    }

    public static abstract class e {
        public abstract void a(float var1, int var2, float var3);
    }

    public static interface f
    extends b {
    }
}

