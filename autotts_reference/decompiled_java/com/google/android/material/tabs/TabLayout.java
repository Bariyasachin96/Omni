/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator$AnimatorListener
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.text.Layout
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.r0;
import androidx.viewpager.widget.ViewPager;
import c.j;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.a;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import o0.j0;
import o0.x0;
import p0.s;
import z1.i;
import z1.k;
import z1.l;
import z1.m;

@ViewPager.e
public class TabLayout
extends HorizontalScrollView {
    public static final int a0 = z1.l.Widget_Design_TabLayout;
    public static final n0.e b0 = new n0.g(16);
    public final int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public boolean G;
    public boolean H;
    public int I;
    public int J;
    public boolean K;
    public a L;
    public final TimeInterpolator M;
    public c N;
    public final ArrayList O;
    public c P;
    public ValueAnimator Q;
    public ViewPager R;
    public g S;
    public b T;
    public boolean U;
    public int V;
    public final n0.e W;
    public int c;
    public final ArrayList d;
    public f e;
    public final e f;
    public int g;
    public int h;
    public int i;
    public int j;
    public final int k;
    public final int l;
    public int m;
    public ColorStateList n;
    public ColorStateList o;
    public ColorStateList p;
    public Drawable q;
    public int r;
    public PorterDuff.Mode s;
    public float t;
    public float u;
    public float v;
    public final int w;
    public int x;
    public final int y;
    public final int z;

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.tabStyle);
    }

    public TabLayout(Context context, AttributeSet attributeSet, int n3) {
        e e3;
        block13: {
            int n4;
            int n5 = a0;
            super(y2.a.d(context, attributeSet, n3, n5), attributeSet, n3);
            this.c = -1;
            this.d = new ArrayList();
            this.m = -1;
            this.r = 0;
            this.x = Integer.MAX_VALUE;
            this.I = -1;
            this.O = new ArrayList();
            this.W = new n0.f(12);
            context = this.getContext();
            this.setHorizontalScrollBarEnabled(false);
            this.f = e3 = new e(this, context);
            super.addView((View)e3, 0, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -1));
            Object object = z1.m.TabLayout;
            int n6 = z1.m.TabLayout_tabTextAppearance;
            attributeSet = com.google.android.material.internal.z.i(context, attributeSet, object, n3, n5, new int[]{n6});
            ColorStateList colorStateList = j2.d.g(this.getBackground());
            if (colorStateList != null) {
                object = new v2.i();
                object.i0(colorStateList);
                object.W(context);
                object.h0(this.getElevation());
                this.setBackground((Drawable)object);
            }
            this.setSelectedTabIndicator(s2.c.e(context, (TypedArray)attributeSet, z1.m.TabLayout_tabIndicator));
            this.setSelectedTabIndicatorColor(attributeSet.getColor(z1.m.TabLayout_tabIndicatorColor, 0));
            e3.i(attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabIndicatorHeight, -1));
            this.setSelectedTabIndicatorGravity(attributeSet.getInt(z1.m.TabLayout_tabIndicatorGravity, 0));
            this.setTabIndicatorAnimationMode(attributeSet.getInt(z1.m.TabLayout_tabIndicatorAnimationMode, 0));
            this.setTabIndicatorFullWidth(attributeSet.getBoolean(z1.m.TabLayout_tabIndicatorFullWidth, true));
            this.j = n3 = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabPadding, 0);
            this.i = n3;
            this.h = n3;
            this.g = n3;
            this.g = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabPaddingStart, n3);
            this.h = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabPaddingTop, this.h);
            this.i = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabPaddingEnd, this.i);
            this.j = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabPaddingBottom, this.j);
            this.k = com.google.android.material.internal.z.g(context) ? z1.c.textAppearanceTitleSmall : z1.c.textAppearanceButton;
            this.l = n4 = attributeSet.getResourceId(n6, z1.l.TextAppearance_Design_Tab);
            object = c.j.TextAppearance;
            e3 = context.obtainStyledAttributes(n4, object);
            n3 = c.j.TextAppearance_android_textSize;
            this.t = e3.getDimensionPixelSize(n3, 0);
            n6 = c.j.TextAppearance_android_textColor;
            this.n = s2.c.a(context, (TypedArray)e3, n6);
            n5 = z1.m.TabLayout_tabSelectedTextAppearance;
            if (attributeSet.hasValue(n5)) {
                this.m = attributeSet.getResourceId(n5, n4);
            }
            if ((n5 = this.m) != -1) {
                Throwable throwable2;
                block12: {
                    block11: {
                        e3 = context.obtainStyledAttributes(n5, object);
                        try {
                            this.u = e3.getDimensionPixelSize(n3, (int)this.t);
                            object = s2.c.a(context, (TypedArray)e3, n6);
                            if (object == null) break block11;
                        }
                        catch (Throwable throwable2) {
                            break block12;
                        }
                        n3 = this.n.getDefaultColor();
                        n6 = object.getDefaultColor();
                        this.n = TabLayout.t(n3, object.getColorForState(new int[]{0x10100A1}, n6));
                    }
                    e3.recycle();
                    break block13;
                }
                e3.recycle();
                throw throwable2;
            }
        }
        if (attributeSet.hasValue(n3 = z1.m.TabLayout_tabTextColor)) {
            this.n = s2.c.a(context, (TypedArray)attributeSet, n3);
        }
        if (attributeSet.hasValue(n3 = z1.m.TabLayout_tabSelectedTextColor)) {
            n3 = attributeSet.getColor(n3, 0);
            this.n = TabLayout.t(this.n.getDefaultColor(), n3);
        }
        this.o = s2.c.a(context, (TypedArray)attributeSet, z1.m.TabLayout_tabIconTint);
        this.s = c0.n(attributeSet.getInt(z1.m.TabLayout_tabIconTintMode, -1), null);
        this.p = s2.c.a(context, (TypedArray)attributeSet, z1.m.TabLayout_tabRippleColor);
        this.D = attributeSet.getInt(z1.m.TabLayout_tabIndicatorAnimationDuration, 300);
        this.M = p2.k.g(context, z1.c.motionEasingEmphasizedInterpolator, a2.a.b);
        this.y = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabMinWidth, -1);
        this.z = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabMaxWidth, -1);
        this.w = attributeSet.getResourceId(z1.m.TabLayout_tabBackground, 0);
        this.B = attributeSet.getDimensionPixelSize(z1.m.TabLayout_tabContentStart, 0);
        this.F = attributeSet.getInt(z1.m.TabLayout_tabMode, 1);
        this.C = attributeSet.getInt(z1.m.TabLayout_tabGravity, 0);
        this.G = attributeSet.getBoolean(z1.m.TabLayout_tabInlineLabel, false);
        this.K = attributeSet.getBoolean(z1.m.TabLayout_tabUnboundedRipple, false);
        attributeSet.recycle();
        context = this.getResources();
        this.v = context.getDimensionPixelSize(z1.e.design_tab_text_size_2line);
        this.A = context.getDimensionPixelSize(z1.e.design_tab_scrollable_min_width);
        this.q();
        return;
        finally {
            e3.recycle();
        }
    }

    private int getDefaultHeight() {
        int n3 = this.d.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            f f3 = (f)this.d.get(i3);
            if (f3 == null || f3.f() == null || TextUtils.isEmpty((CharSequence)f3.i())) continue;
            if (this.G) break;
            return 72;
        }
        return 48;
    }

    private int getTabMinWidth() {
        int n3 = this.y;
        if (n3 != -1) {
            return n3;
        }
        n3 = this.F;
        if (n3 != 0 && n3 != 2) {
            return 0;
        }
        return this.A;
    }

    private int getTabScrollRange() {
        return Math.max(0, this.f.getWidth() - this.getWidth() - this.getPaddingLeft() - this.getPaddingRight());
    }

    private void setSelectedTabView(int n3) {
        int n4 = this.f.getChildCount();
        if (n3 < n4) {
            for (int i3 = 0; i3 < n4; ++i3) {
                boolean bl;
                View view = this.f.getChildAt(i3);
                boolean bl2 = true;
                boolean bl3 = true;
                if (i3 == n3 && !view.isSelected() || i3 != n3 && view.isSelected()) {
                    bl = i3 == n3;
                    view.setSelected(bl);
                    bl = i3 == n3 ? bl3 : false;
                    view.setActivated(bl);
                    if (!(view instanceof TabView)) continue;
                    ((TabView)view).s();
                    continue;
                }
                bl = i3 == n3;
                view.setSelected(bl);
                bl = i3 == n3 ? bl2 : false;
                view.setActivated(bl);
            }
        }
    }

    public static ColorStateList t(int n3, int n4) {
        return new ColorStateList((int[][])new int[][]{HorizontalScrollView.SELECTED_STATE_SET, HorizontalScrollView.EMPTY_STATE_SET}, new int[]{n4, n3});
    }

    public final void A() {
        if (this.Q == null) {
            ValueAnimator valueAnimator;
            this.Q = valueAnimator = new ValueAnimator();
            valueAnimator.setInterpolator(this.M);
            this.Q.setDuration((long)this.D);
            this.Q.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this){
                public final TabLayout a;
                {
                    this.a = tabLayout;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.a.scrollTo((Integer)valueAnimator.getAnimatedValue(), 0);
                }
            });
        }
    }

    public f B(int n3) {
        if (n3 >= 0 && n3 < this.getTabCount()) {
            return (f)this.d.get(n3);
        }
        return null;
    }

    public final boolean C() {
        return this.getTabMode() == 0 || this.getTabMode() == 2;
        {
        }
    }

    public boolean D() {
        return this.H;
    }

    public f E() {
        f f3 = this.v();
        f3.h = this;
        f3.i = this.w(f3);
        if (f3.j != -1) {
            f3.i.setId(f3.j);
        }
        return f3;
    }

    public void F() {
        this.H();
    }

    public boolean G(f f3) {
        return b0.a(f3);
    }

    public void H() {
        for (int i3 = this.f.getChildCount() - 1; i3 >= 0; --i3) {
            this.J(i3);
        }
        Iterator iterator = this.d.iterator();
        while (iterator.hasNext()) {
            f f3 = (f)iterator.next();
            iterator.remove();
            f3.k();
            this.G(f3);
        }
        this.e = null;
    }

    public void I(c c3) {
        this.O.remove(c3);
    }

    public final void J(int n3) {
        TabView tabView = (TabView)this.f.getChildAt(n3);
        this.f.removeViewAt(n3);
        if (tabView != null) {
            tabView.k();
            this.W.a((Object)tabView);
        }
        this.requestLayout();
    }

    public void K(f f3) {
        this.L(f3, true);
    }

    public void L(f f3, boolean bl) {
        f f4 = this.e;
        if (f4 == f3) {
            if (f4 != null) {
                this.x(f3);
                this.o(f3.g());
                return;
            }
        } else {
            int n3 = f3 != null ? f3.g() : -1;
            if (bl) {
                if ((f4 == null || f4.g() == -1) && n3 != -1) {
                    this.setScrollPosition(n3, 0.0f, true);
                } else {
                    this.o(n3);
                }
                if (n3 != -1) {
                    this.setSelectedTabView(n3);
                }
            }
            this.e = f3;
            if (f4 != null && f4.h != null) {
                this.z(f4);
            }
            if (f3 != null) {
                this.y(f3);
            }
        }
    }

    public void M(p1.a a4, boolean bl) {
        this.F();
    }

    public void N(int n3, float f3, boolean bl, boolean bl2, boolean bl3) {
        int n4 = Math.round((float)n3 + f3);
        if (n4 >= 0 && n4 < this.f.getChildCount()) {
            ValueAnimator valueAnimator;
            if (bl2) {
                this.f.h(n3, f3);
            }
            if ((valueAnimator = this.Q) != null && valueAnimator.isRunning()) {
                this.Q.cancel();
            }
            int n5 = this.r(n3, f3);
            int n6 = this.getScrollX();
            int n7 = n3 < this.getSelectedTabPosition() && n5 >= n6 || n3 > this.getSelectedTabPosition() && n5 <= n6 || n3 == this.getSelectedTabPosition() ? 1 : 0;
            if (this.getLayoutDirection() == 1) {
                n7 = n3 < this.getSelectedTabPosition() && n5 <= n6 || n3 > this.getSelectedTabPosition() && n5 >= n6 || n3 == this.getSelectedTabPosition() ? 1 : 0;
            }
            if (n7 != 0 || this.V == 1 || bl3) {
                n7 = n5;
                if (n3 < 0) {
                    n7 = 0;
                }
                this.scrollTo(n7, 0);
            }
            if (bl) {
                this.setSelectedTabView(n4);
            }
        }
    }

    public final void O(ViewPager viewPager, boolean bl, boolean bl2) {
        Object object = this.R;
        if (object != null) {
            g g3 = this.S;
            if (g3 != null) {
                object.C(g3);
            }
            if ((object = this.T) != null) {
                this.R.B((ViewPager.h)object);
            }
        }
        if ((object = this.P) != null) {
            this.I((c)object);
            this.P = null;
        }
        if (viewPager != null) {
            this.R = viewPager;
            if (this.S == null) {
                this.S = new g(this);
            }
            this.S.d();
            viewPager.b(this.S);
            this.P = object = new h(viewPager);
            this.g((c)object);
            viewPager.getAdapter();
            if (this.T == null) {
                this.T = new b(this);
            }
            this.T.a(bl);
            viewPager.a(this.T);
            this.setScrollPosition(viewPager.getCurrentItem(), 0.0f, true);
        } else {
            this.R = null;
            this.M(null, false);
        }
        this.U = bl2;
    }

    public final void P() {
        int n3 = this.d.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((f)this.d.get(i3)).u();
        }
    }

    public final void Q(LinearLayout.LayoutParams layoutParams) {
        if (this.F == 1 && this.C == 0) {
            layoutParams.width = 0;
            layoutParams.weight = 1.0f;
            return;
        }
        layoutParams.width = -2;
        layoutParams.weight = 0.0f;
    }

    public void R(boolean bl) {
        for (int i3 = 0; i3 < this.f.getChildCount(); ++i3) {
            View view = this.f.getChildAt(i3);
            view.setMinimumWidth(this.getTabMinWidth());
            this.Q((LinearLayout.LayoutParams)view.getLayoutParams());
            if (!bl) continue;
            view.requestLayout();
        }
    }

    public void S(int n3) {
        this.V = n3;
    }

    public void addView(View view) {
        this.n(view);
    }

    public void addView(View view, int n3) {
        this.n(view);
    }

    public void addView(View view, int n3, ViewGroup.LayoutParams layoutParams) {
        this.n(view);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.n(view);
    }

    public void g(c c3) {
        if (!this.O.contains(c3)) {
            this.O.add(c3);
        }
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return this.generateDefaultLayoutParams();
    }

    public int getSelectedTabPosition() {
        f f3 = this.e;
        if (f3 != null) {
            return f3.g();
        }
        return -1;
    }

    public int getTabCount() {
        return this.d.size();
    }

    public int getTabGravity() {
        return this.C;
    }

    public ColorStateList getTabIconTint() {
        return this.o;
    }

    public int getTabIndicatorAnimationMode() {
        return this.J;
    }

    public int getTabIndicatorGravity() {
        return this.E;
    }

    public int getTabMaxWidth() {
        return this.x;
    }

    public int getTabMode() {
        return this.F;
    }

    public ColorStateList getTabRippleColor() {
        return this.p;
    }

    public Drawable getTabSelectedIndicator() {
        return this.q;
    }

    public ColorStateList getTabTextColors() {
        return this.n;
    }

    public void h(d d3) {
        this.g(d3);
    }

    public void i(f f3) {
        this.k(f3, this.d.isEmpty());
    }

    public void j(f f3, int n3, boolean bl) {
        if (f3.h == this) {
            this.s(f3, n3);
            this.m(f3);
            if (bl) {
                f3.l();
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    public void k(f f3, boolean bl) {
        this.j(f3, this.d.size(), bl);
    }

    public final void l(TabItem tabItem) {
        int n3;
        f f3 = this.E();
        CharSequence charSequence = tabItem.c;
        if (charSequence != null) {
            f3.t(charSequence);
        }
        if ((charSequence = tabItem.d) != null) {
            f3.q((Drawable)charSequence);
        }
        if ((n3 = tabItem.e) != 0) {
            f3.n(n3);
        }
        if (!TextUtils.isEmpty((CharSequence)tabItem.getContentDescription())) {
            f3.m(tabItem.getContentDescription());
        }
        this.i(f3);
    }

    public final void m(f f3) {
        TabView tabView = f3.i;
        tabView.setSelected(false);
        tabView.setActivated(false);
        this.f.addView((View)tabView, f3.g(), (ViewGroup.LayoutParams)this.u());
    }

    public final void n(View view) {
        if (view instanceof TabItem) {
            this.l((TabItem)view);
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    public final void o(int n3) {
        if (n3 == -1) {
            return;
        }
        if (this.getWindowToken() != null && this.isLaidOut() && !this.f.d()) {
            int n4;
            int n5 = this.getScrollX();
            if (n5 != (n4 = this.r(n3, 0.0f))) {
                this.A();
                this.Q.setIntValues(new int[]{n5, n4});
                this.Q.start();
            }
            this.f.c(n3, this.D);
            return;
        }
        this.setScrollPosition(n3, 0.0f, true);
    }

    public void onAttachedToWindow() {
        ViewParent viewParent;
        super.onAttachedToWindow();
        v2.j.e((View)this);
        if (this.R == null && (viewParent = this.getParent()) instanceof ViewPager) {
            this.O((ViewPager)viewParent, true, true);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.U) {
            this.setupWithViewPager(null);
            this.U = false;
        }
    }

    public void onDraw(Canvas canvas) {
        for (int i3 = 0; i3 < this.f.getChildCount(); ++i3) {
            View view = this.f.getChildAt(i3);
            if (!(view instanceof TabView)) continue;
            ((TabView)view).g(canvas);
        }
        super.onDraw(canvas);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        p0.s.L0(accessibilityNodeInfo).j0(s.e.b(1, this.getTabCount(), false, 1));
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.C() && super.onInterceptTouchEvent(motionEvent);
    }

    public void onMeasure(int n3, int n4) {
        block11: {
            View view;
            int n5;
            block14: {
                block12: {
                    block13: {
                        int n6 = Math.round(c0.g(this.getContext(), this.getDefaultHeight()));
                        n5 = View.MeasureSpec.getMode((int)n4);
                        if (n5 != Integer.MIN_VALUE) {
                            n5 = n5 != 0 ? n4 : View.MeasureSpec.makeMeasureSpec((int)(n6 + this.getPaddingTop() + this.getPaddingBottom()), (int)0x40000000);
                        } else {
                            n5 = n4;
                            if (this.getChildCount() == 1) {
                                n5 = n4;
                                if (View.MeasureSpec.getSize((int)n4) >= n6) {
                                    this.getChildAt(0).setMinimumHeight(n6);
                                    n5 = n4;
                                }
                            }
                        }
                        n6 = View.MeasureSpec.getSize((int)n3);
                        if (View.MeasureSpec.getMode((int)n3) != 0) {
                            n4 = this.z;
                            if (n4 <= 0) {
                                n4 = (int)((float)n6 - c0.g(this.getContext(), 56));
                            }
                            this.x = n4;
                        }
                        super.onMeasure(n3, n5);
                        if (this.getChildCount() != 1) break block11;
                        view = this.getChildAt(0);
                        n3 = this.F;
                        if (n3 == 0) break block12;
                        if (n3 == 1) break block13;
                        if (n3 == 2) break block12;
                        break block11;
                    }
                    if (view.getMeasuredWidth() == this.getMeasuredWidth()) {
                        return;
                    }
                    break block14;
                }
                if (view.getMeasuredWidth() >= this.getMeasuredWidth()) break block11;
            }
            n3 = ViewGroup.getChildMeasureSpec((int)n5, (int)(this.getPaddingTop() + this.getPaddingBottom()), (int)view.getLayoutParams().height);
            view.measure(View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0x40000000), n3);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 8 && !this.C()) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void p(int n3) {
        if (n3 != 0) {
            if (n3 == 1) {
                this.f.setGravity(1);
                return;
            }
            if (n3 != 2) {
                return;
            }
        } else {
            Log.w((String)"TabLayout", (String)"MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead");
        }
        this.f.setGravity(0x800003);
    }

    public final void q() {
        int n3 = this.F;
        n3 = n3 != 0 && n3 != 2 ? 0 : Math.max(0, this.B - this.g);
        this.f.setPaddingRelative(n3, 0, 0, 0);
        n3 = this.F;
        if (n3 != 0) {
            if (n3 == 1 || n3 == 2) {
                if (this.C == 2) {
                    Log.w((String)"TabLayout", (String)"GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead");
                }
                this.f.setGravity(1);
            }
        } else {
            this.p(this.C);
        }
        this.R(true);
    }

    public final int r(int n3, float f3) {
        int n4 = this.F;
        int n5 = 0;
        if (n4 != 0 && n4 != 2) {
            return 0;
        }
        View view = this.f.getChildAt(n3);
        if (view == null) {
            return 0;
        }
        View view2 = ++n3 < this.f.getChildCount() ? this.f.getChildAt(n3) : null;
        n4 = view.getWidth();
        n3 = n5;
        if (view2 != null) {
            n3 = view2.getWidth();
        }
        n5 = view.getLeft() + n4 / 2 - this.getWidth() / 2;
        n3 = (int)((float)(n4 + n3) * 0.5f * f3);
        if (this.getLayoutDirection() == 0) {
            return n5 + n3;
        }
        return n5 - n3;
    }

    public final void s(f f3, int n3) {
        f3.r(n3);
        this.d.add(n3, f3);
        int n4 = this.d.size();
        ++n3;
        int n5 = -1;
        while (n3 < n4) {
            if (((f)this.d.get(n3)).g() == this.c) {
                n5 = n3;
            }
            ((f)this.d.get(n3)).r(n3);
            ++n3;
        }
        this.c = n5;
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        v2.j.d((View)this, f3);
    }

    public void setInlineLabel(boolean bl) {
        if (this.G != bl) {
            this.G = bl;
            for (int i3 = 0; i3 < this.f.getChildCount(); ++i3) {
                View view = this.f.getChildAt(i3);
                if (!(view instanceof TabView)) continue;
                ((TabView)view).r();
            }
            this.q();
        }
    }

    public void setInlineLabelResource(int n3) {
        this.setInlineLabel(this.getResources().getBoolean(n3));
    }

    @Deprecated
    public void setOnTabSelectedListener(c c3) {
        c c4 = this.N;
        if (c4 != null) {
            this.I(c4);
        }
        this.N = c3;
        if (c3 != null) {
            this.g(c3);
        }
    }

    @Deprecated
    public void setOnTabSelectedListener(d d3) {
        this.setOnTabSelectedListener((c)d3);
    }

    public void setScrollAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.A();
        this.Q.addListener(animatorListener);
    }

    public void setScrollPosition(int n3, float f3, boolean bl) {
        this.setScrollPosition(n3, f3, bl, true);
    }

    public void setScrollPosition(int n3, float f3, boolean bl, boolean bl2) {
        this.N(n3, f3, bl, bl2, true);
    }

    public void setSelectedTabIndicator(int n3) {
        if (n3 != 0) {
            this.setSelectedTabIndicator(d.a.b(this.getContext(), n3));
            return;
        }
        this.setSelectedTabIndicator(null);
    }

    public void setSelectedTabIndicator(Drawable drawable) {
        int n3;
        Drawable drawable2 = drawable;
        if (drawable == null) {
            drawable2 = new GradientDrawable();
        }
        this.q = drawable = h0.a.r(drawable2).mutate();
        j2.d.n(drawable, this.r);
        int n4 = n3 = this.I;
        if (n3 == -1) {
            n4 = this.q.getIntrinsicHeight();
        }
        this.f.i(n4);
    }

    public void setSelectedTabIndicatorColor(int n3) {
        this.r = n3;
        j2.d.n(this.q, n3);
        this.R(false);
    }

    public void setSelectedTabIndicatorGravity(int n3) {
        if (this.E != n3) {
            this.E = n3;
            this.f.postInvalidateOnAnimation();
        }
    }

    @Deprecated
    public void setSelectedTabIndicatorHeight(int n3) {
        this.I = n3;
        this.f.i(n3);
    }

    public void setTabGravity(int n3) {
        if (this.C != n3) {
            this.C = n3;
            this.q();
        }
    }

    public void setTabIconTint(ColorStateList colorStateList) {
        if (this.o != colorStateList) {
            this.o = colorStateList;
            this.P();
        }
    }

    public void setTabIconTintResource(int n3) {
        this.setTabIconTint(d.a.a(this.getContext(), n3));
    }

    public void setTabIndicatorAnimationMode(int n3) {
        this.J = n3;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 == 2) {
                    this.L = new x2.b();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(n3);
                stringBuilder.append(" is not a valid TabIndicatorAnimationMode");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.L = new x2.a();
            return;
        }
        this.L = new a();
    }

    public void setTabIndicatorFullWidth(boolean bl) {
        this.H = bl;
        this.f.g();
        this.f.postInvalidateOnAnimation();
    }

    public void setTabMode(int n3) {
        if (n3 != this.F) {
            this.F = n3;
            this.q();
        }
    }

    public void setTabRippleColor(ColorStateList colorStateList) {
        if (this.p != colorStateList) {
            this.p = colorStateList;
            for (int i3 = 0; i3 < this.f.getChildCount(); ++i3) {
                colorStateList = this.f.getChildAt(i3);
                if (!(colorStateList instanceof TabView)) continue;
                ((TabView)colorStateList).q(this.getContext());
            }
        }
    }

    public void setTabRippleColorResource(int n3) {
        this.setTabRippleColor(d.a.a(this.getContext(), n3));
    }

    public void setTabTextColors(int n3, int n4) {
        this.setTabTextColors(TabLayout.t(n3, n4));
    }

    public void setTabTextColors(ColorStateList colorStateList) {
        if (this.n != colorStateList) {
            this.n = colorStateList;
            this.P();
        }
    }

    @Deprecated
    public void setTabsFromPagerAdapter(p1.a a4) {
        this.M(a4, false);
    }

    public void setUnboundedRipple(boolean bl) {
        if (this.K != bl) {
            this.K = bl;
            for (int i3 = 0; i3 < this.f.getChildCount(); ++i3) {
                View view = this.f.getChildAt(i3);
                if (!(view instanceof TabView)) continue;
                ((TabView)view).q(this.getContext());
            }
        }
    }

    public void setUnboundedRippleResource(int n3) {
        this.setUnboundedRipple(this.getResources().getBoolean(n3));
    }

    public void setupWithViewPager(ViewPager viewPager) {
        this.setupWithViewPager(viewPager, true);
    }

    public void setupWithViewPager(ViewPager viewPager, boolean bl) {
        this.O(viewPager, bl, false);
    }

    public boolean shouldDelayChildPressedState() {
        return this.getTabScrollRange() > 0;
    }

    public final LinearLayout.LayoutParams u() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
        this.Q(layoutParams);
        return layoutParams;
    }

    public f v() {
        f f3;
        f f4 = f3 = (f)b0.b();
        if (f3 == null) {
            f4 = new f();
        }
        return f4;
    }

    public final TabView w(f f3) {
        Object object = this.W;
        object = object != null ? (TabView)((Object)object.b()) : null;
        Object object2 = object;
        if (object == null) {
            object2 = new TabView(this, this.getContext());
        }
        ((TabView)((Object)object2)).setTab(f3);
        object2.setFocusable(true);
        object2.setMinimumWidth(this.getTabMinWidth());
        if (TextUtils.isEmpty((CharSequence)f3.d)) {
            object2.setContentDescription(f3.c);
            return object2;
        }
        object2.setContentDescription(f3.d);
        return object2;
    }

    public final void x(f f3) {
        for (int i3 = this.O.size() - 1; i3 >= 0; --i3) {
            ((c)this.O.get(i3)).a(f3);
        }
    }

    public final void y(f f3) {
        for (int i3 = this.O.size() - 1; i3 >= 0; --i3) {
            ((c)this.O.get(i3)).c(f3);
        }
    }

    public final void z(f f3) {
        for (int i3 = this.O.size() - 1; i3 >= 0; --i3) {
            ((c)this.O.get(i3)).b(f3);
        }
    }

    public final class TabView
    extends LinearLayout {
        public f c;
        public TextView d;
        public ImageView e;
        public View f;
        public com.google.android.material.badge.a g;
        public View h;
        public TextView i;
        public ImageView j;
        public Drawable k;
        public int l;
        public final TabLayout m;

        public TabView(TabLayout tabLayout, Context context) {
            this.m = tabLayout;
            super(context);
            this.l = 2;
            this.q(context);
            this.setPaddingRelative(tabLayout.g, tabLayout.h, tabLayout.i, tabLayout.j);
            this.setGravity(17);
            this.setOrientation(tabLayout.G ^ 1);
            this.setClickable(true);
            x0.s0((View)this, j0.b(this.getContext(), 1002));
        }

        private com.google.android.material.badge.a getBadge() {
            return this.g;
        }

        private com.google.android.material.badge.a getOrCreateBadge() {
            if (this.g == null) {
                this.g = com.google.android.material.badge.a.e(this.getContext());
            }
            this.n();
            com.google.android.material.badge.a a4 = this.g;
            if (a4 != null) {
                return a4;
            }
            throw new IllegalStateException("Unable to create badge");
        }

        public final void d(View view) {
            if (view == null) {
                return;
            }
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this, view){
                public final View a;
                public final TabView b;
                {
                    this.b = tabView;
                    this.a = view;
                }

                public void onLayoutChange(View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
                    if (this.a.getVisibility() == 0) {
                        this.b.o(this.a);
                    }
                }
            });
        }

        public void drawableStateChanged() {
            super.drawableStateChanged();
            int[] nArray = this.getDrawableState();
            Drawable drawable = this.k;
            boolean bl = drawable != null && drawable.isStateful() ? this.k.setState(nArray) : false;
            if (bl) {
                this.invalidate();
                this.m.invalidate();
            }
        }

        public final float e(Layout layout, int n3, float f3) {
            return layout.getLineWidth(n3) * (f3 / layout.getPaint().getTextSize());
        }

        public final void f(boolean bl) {
            this.setClipChildren(bl);
            this.setClipToPadding(bl);
            ViewGroup viewGroup = (ViewGroup)this.getParent();
            if (viewGroup != null) {
                viewGroup.setClipChildren(bl);
                viewGroup.setClipToPadding(bl);
            }
        }

        public final void g(Canvas canvas) {
            Drawable drawable = this.k;
            if (drawable != null) {
                drawable.setBounds(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
                this.k.draw(canvas);
            }
        }

        public int getContentHeight() {
            int n3;
            TextView textView = this.d;
            ImageView imageView = this.e;
            View view = this.h;
            int n4 = 0;
            int n5 = n3 = 0;
            for (int i3 = 0; i3 < 3; ++i3) {
                View view2 = (new View[]{textView, imageView, view})[i3];
                int n6 = n4;
                int n7 = n3;
                int n8 = n5;
                if (view2 != null) {
                    n6 = n4;
                    n7 = n3;
                    n8 = n5;
                    if (view2.getVisibility() == 0) {
                        n3 = n5 != 0 ? Math.min(n3, view2.getTop()) : view2.getTop();
                        n5 = n5 != 0 ? Math.max(n4, view2.getBottom()) : view2.getBottom();
                        n8 = 1;
                        n7 = n3;
                        n6 = n5;
                    }
                }
                n4 = n6;
                n3 = n7;
                n5 = n8;
            }
            return n4 - n3;
        }

        public int getContentWidth() {
            int n3;
            TextView textView = this.d;
            ImageView imageView = this.e;
            View view = this.h;
            int n4 = 0;
            int n5 = n3 = 0;
            for (int i3 = 0; i3 < 3; ++i3) {
                View view2 = (new View[]{textView, imageView, view})[i3];
                int n6 = n4;
                int n7 = n3;
                int n8 = n5;
                if (view2 != null) {
                    n6 = n4;
                    n7 = n3;
                    n8 = n5;
                    if (view2.getVisibility() == 0) {
                        n7 = n5 != 0 ? Math.min(n3, view2.getLeft()) : view2.getLeft();
                        n5 = n5 != 0 ? Math.max(n4, view2.getRight()) : view2.getRight();
                        n8 = 1;
                        n6 = n5;
                    }
                }
                n4 = n6;
                n3 = n7;
                n5 = n8;
            }
            return n4 - n3;
        }

        public f getTab() {
            return this.c;
        }

        public final boolean h() {
            return this.g != null;
        }

        public final void i() {
            ImageView imageView;
            this.e = imageView = (ImageView)LayoutInflater.from((Context)this.getContext()).inflate(z1.i.design_layout_tab_icon, (ViewGroup)this, false);
            this.addView((View)imageView, 0);
        }

        public final void j() {
            TextView textView;
            this.d = textView = (TextView)LayoutInflater.from((Context)this.getContext()).inflate(z1.i.design_layout_tab_text, (ViewGroup)this, false);
            this.addView((View)textView);
        }

        public void k() {
            this.setTab(null);
            this.setSelected(false);
        }

        public final void l(View view) {
            if (this.h() && view != null) {
                this.f(false);
                com.google.android.material.badge.b.b(this.g, view, null);
                this.f = view;
            }
        }

        public final void m() {
            if (this.h()) {
                this.f(true);
                View view = this.f;
                if (view != null) {
                    com.google.android.material.badge.b.e(this.g, view);
                    this.f = null;
                }
            }
        }

        public final void n() {
            f f3;
            if (!this.h()) {
                return;
            }
            if (this.h != null) {
                this.m();
                return;
            }
            if (this.e != null && (f3 = this.c) != null && f3.f() != null) {
                View view = this.f;
                f3 = this.e;
                if (view != f3) {
                    this.m();
                    this.l((View)this.e);
                    return;
                }
                this.o((View)f3);
                return;
            }
            if (this.d != null && (f3 = this.c) != null && f3.h() == 1) {
                View view = this.f;
                f3 = this.d;
                if (view != f3) {
                    this.m();
                    this.l((View)this.d);
                    return;
                }
                this.o((View)f3);
                return;
            }
            this.m();
        }

        public final void o(View view) {
            if (this.h() && view == this.f) {
                com.google.android.material.badge.b.f(this.g, view, null);
            }
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
            super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
            object = p0.s.L0((AccessibilityNodeInfo)object);
            com.google.android.material.badge.a a4 = this.g;
            if (a4 != null && a4.isVisible()) {
                ((s)object).l0(this.g.j());
            }
            ((s)object).k0(s.f.a(0, 1, this.c.g(), 1, false, this.isSelected()));
            if (this.isSelected()) {
                ((s)object).i0(false);
                ((s)object).Z(s.a.i);
            }
            ((s)object).z0(this.getResources().getString(z1.k.item_view_role_description));
        }

        public void onMeasure(int n3, int n4) {
            int n5;
            int n6;
            int n7;
            int n8;
            block12: {
                block13: {
                    n8 = View.MeasureSpec.getSize((int)n3);
                    n7 = View.MeasureSpec.getMode((int)n3);
                    n6 = this.m.getTabMaxWidth();
                    n5 = n3;
                    if (n6 <= 0) break block12;
                    if (n7 == 0) break block13;
                    n5 = n3;
                    if (n8 <= n6) break block12;
                }
                n5 = View.MeasureSpec.makeMeasureSpec((int)this.m.x, (int)Integer.MIN_VALUE);
            }
            super.onMeasure(n5, n4);
            if (this.d != null) {
                float f3;
                float f4 = f3 = this.m.t;
                if (this.isSelected()) {
                    f4 = f3;
                    if (this.m.m != -1) {
                        f4 = this.m.u;
                    }
                }
                n7 = this.l;
                ImageView imageView = this.e;
                if (imageView != null && imageView.getVisibility() == 0) {
                    n3 = 1;
                    f3 = f4;
                } else {
                    imageView = this.d;
                    f3 = f4;
                    n3 = n7;
                    if (imageView != null) {
                        f3 = f4;
                        n3 = n7;
                        if (imageView.getLineCount() > 1) {
                            f3 = this.m.v;
                            n3 = n7;
                        }
                    }
                }
                f4 = this.d.getTextSize();
                n6 = this.d.getLineCount();
                n8 = this.d.getMaxLines();
                n7 = f3 == f4 ? 0 : (f3 > f4 ? 1 : -1);
                if (n7 != 0 || n8 >= 0 && n3 != n8) {
                    if (this.m.F == 1 && n7 > 0 && n6 == 1 && ((imageView = this.d.getLayout()) == null || this.e((Layout)imageView, 0, f3) > (float)(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight()))) {
                        return;
                    }
                    this.d.setTextSize(0, f3);
                    this.d.setMaxLines(n3);
                    super.onMeasure(n5, n4);
                }
            }
        }

        public final void p() {
            this.s();
            f f3 = this.c;
            boolean bl = f3 != null && f3.j();
            this.setSelected(bl);
        }

        public boolean performClick() {
            boolean bl;
            boolean bl2 = bl = super.performClick();
            if (this.c != null) {
                if (!bl) {
                    this.playSoundEffect(0);
                }
                this.c.l();
                bl2 = true;
            }
            return bl2;
        }

        public final void q(Context context) {
            int n3 = this.m.w;
            Object var5_3 = null;
            if (n3 != 0) {
                context = d.a.b(context, n3);
                this.k = context;
                if (context != null && context.isStateful()) {
                    this.k.setState(this.getDrawableState());
                }
            } else {
                this.k = null;
            }
            context = new GradientDrawable();
            context.setColor(0);
            Context context2 = context;
            if (this.m.p != null) {
                context2 = new GradientDrawable();
                context2.setCornerRadius(1.0E-5f);
                context2.setColor(-1);
                ColorStateList colorStateList = t2.a.a(this.m.p);
                boolean bl = this.m.K;
                if (bl) {
                    context = null;
                }
                if (bl) {
                    context2 = var5_3;
                }
                context2 = new RippleDrawable(colorStateList, (Drawable)context, (Drawable)context2);
            }
            this.setBackground((Drawable)context2);
            this.m.invalidate();
        }

        public final void r() {
            this.setOrientation(this.m.G ^ 1);
            TextView textView = this.i;
            if (textView == null && this.j == null) {
                this.t(this.d, this.e, true);
                return;
            }
            this.t(textView, this.j, false);
        }

        public final void s() {
            f f3 = this.c;
            View view = f3 != null ? f3.e() : null;
            if (view != null) {
                ViewParent viewParent = view.getParent();
                if (viewParent != this) {
                    if (viewParent != null) {
                        ((ViewGroup)viewParent).removeView(view);
                    }
                    if ((viewParent = this.h) != null && (viewParent = viewParent.getParent()) != null) {
                        ((ViewGroup)viewParent).removeView(this.h);
                    }
                    this.addView(view);
                }
                this.h = view;
                viewParent = this.d;
                if (viewParent != null) {
                    viewParent.setVisibility(8);
                }
                if ((viewParent = this.e) != null) {
                    viewParent.setVisibility(8);
                    this.e.setImageDrawable(null);
                }
                viewParent = (TextView)view.findViewById(16908308);
                this.i = viewParent;
                if (viewParent != null) {
                    this.l = viewParent.getMaxLines();
                }
                this.j = (ImageView)view.findViewById(16908294);
            } else {
                view = this.h;
                if (view != null) {
                    this.removeView(view);
                    this.h = null;
                }
                this.i = null;
                this.j = null;
            }
            if (this.h == null) {
                if (this.e == null) {
                    this.i();
                }
                if (this.d == null) {
                    this.j();
                    this.l = this.d.getMaxLines();
                }
                androidx.core.widget.j.m(this.d, this.m.k);
                if (this.isSelected() && this.m.m != -1) {
                    androidx.core.widget.j.m(this.d, this.m.m);
                } else {
                    androidx.core.widget.j.m(this.d, this.m.l);
                }
                view = this.m.n;
                if (view != null) {
                    this.d.setTextColor((ColorStateList)view);
                }
                this.t(this.d, this.e, true);
                this.n();
                this.d((View)this.e);
                this.d((View)this.d);
            } else {
                view = this.i;
                if (view != null || this.j != null) {
                    this.t((TextView)view, this.j, false);
                }
            }
            if (f3 != null && !TextUtils.isEmpty((CharSequence)f3.d)) {
                this.setContentDescription(f3.d);
            }
        }

        public void setSelected(boolean bl) {
            this.isSelected();
            super.setSelected(bl);
            TextView textView = this.d;
            if (textView != null) {
                textView.setSelected(bl);
            }
            if ((textView = this.e) != null) {
                textView.setSelected(bl);
            }
            if ((textView = this.h) != null) {
                textView.setSelected(bl);
            }
        }

        public void setTab(f f3) {
            if (f3 != this.c) {
                this.c = f3;
                this.p();
            }
        }

        public final void t(TextView object, ImageView object2, boolean bl) {
            int n3;
            int n4;
            boolean bl2;
            Object var9_5;
            Object object3;
            block20: {
                block17: {
                    Object object4;
                    block19: {
                        block18: {
                            object3 = this.c;
                            var9_5 = null;
                            object4 = object3 != null && ((f)object3).f() != null ? h0.a.r(this.c.f()).mutate() : null;
                            if (object4 != null) {
                                object4.setTintList(this.m.o);
                                object3 = this.m.s;
                                if (object3 != null) {
                                    object4.setTintMode((PorterDuff.Mode)object3);
                                }
                            }
                            object3 = (object3 = this.c) != null ? ((f)object3).i() : null;
                            if (object2 != null) {
                                if (object4 != null) {
                                    object2.setImageDrawable(object4);
                                    object2.setVisibility(0);
                                    this.setVisibility(0);
                                } else {
                                    object2.setVisibility(8);
                                    object2.setImageDrawable(null);
                                }
                            }
                            bl2 = TextUtils.isEmpty((CharSequence)object3);
                            if (object == null) break block17;
                            if (bl2) break block18;
                            n4 = this.c.g;
                            n3 = 1;
                            if (n4 == 1) break block19;
                        }
                        n3 = 0;
                    }
                    object4 = !bl2 ? object3 : null;
                    object.setText((CharSequence)object4);
                    n4 = n3 != 0 ? 0 : 8;
                    object.setVisibility(n4);
                    n4 = n3;
                    if (!bl2) {
                        this.setVisibility(0);
                        n4 = n3;
                    }
                    break block20;
                }
                n4 = 0;
            }
            if (bl && object2 != null) {
                object = (ViewGroup.MarginLayoutParams)object2.getLayoutParams();
                n3 = n4 != 0 && object2.getVisibility() == 0 ? (int)c0.g(this.getContext(), 8) : 0;
                if (this.m.G) {
                    if (n3 != object.getMarginEnd()) {
                        object.setMarginEnd(n3);
                        object.bottomMargin = 0;
                        object2.setLayoutParams((ViewGroup.LayoutParams)object);
                        object2.requestLayout();
                    }
                } else if (n3 != object.bottomMargin) {
                    object.bottomMargin = n3;
                    object.setMarginEnd(0);
                    object2.setLayoutParams((ViewGroup.LayoutParams)object);
                    object2.requestLayout();
                }
            }
            object2 = this.c;
            object = var9_5;
            if (object2 != null) {
                object = ((f)object2).d;
            }
            if (bl2) {
                object3 = object;
            }
            r0.a((View)this, (CharSequence)object3);
        }
    }

    public class b
    implements ViewPager.h {
        public boolean a;
        public final TabLayout b;

        public b(TabLayout tabLayout) {
            this.b = tabLayout;
        }

        public void a(boolean bl) {
            this.a = bl;
        }

        @Override
        public void d(ViewPager viewPager, p1.a object, p1.a a4) {
            object = this.b;
            if (((TabLayout)((Object)object)).R == viewPager) {
                ((TabLayout)((Object)object)).M(a4, this.a);
            }
        }
    }

    public static interface c {
        public void a(f var1);

        public void b(f var1);

        public void c(f var1);
    }

    public static interface d
    extends c {
    }

    public class e
    extends LinearLayout {
        public ValueAnimator c;
        public int d;
        public final TabLayout e;

        public e(TabLayout tabLayout, Context context) {
            this.e = tabLayout;
            super(context);
            this.d = -1;
            this.setWillNotDraw(false);
        }

        public void c(int n3, int n4) {
            ValueAnimator valueAnimator = this.c;
            if (valueAnimator != null && valueAnimator.isRunning() && this.e.c != n3) {
                this.c.cancel();
            }
            this.k(true, n3, n4);
        }

        public boolean d() {
            int n3 = this.getChildCount();
            for (int i3 = 0; i3 < n3; ++i3) {
                if (this.getChildAt(i3).getWidth() > 0) continue;
                return true;
            }
            return false;
        }

        public void draw(Canvas canvas) {
            int n3;
            int n4;
            int n5 = n4 = this.e.q.getBounds().height();
            if (n4 < 0) {
                n5 = this.e.q.getIntrinsicHeight();
            }
            if ((n3 = this.e.E) != 0) {
                if (n3 != 1) {
                    int n6;
                    n4 = n6 = 0;
                    if (n3 != 2) {
                        if (n3 != 3) {
                            n5 = 0;
                            n4 = n6;
                        } else {
                            n5 = this.getHeight();
                            n4 = n6;
                        }
                    }
                } else {
                    n4 = (this.getHeight() - n5) / 2;
                    n5 = (this.getHeight() + n5) / 2;
                }
            } else {
                n4 = this.getHeight() - n5;
                n5 = this.getHeight();
            }
            if (this.e.q.getBounds().width() > 0) {
                Rect rect = this.e.q.getBounds();
                this.e.q.setBounds(rect.left, n4, rect.right, n5);
                this.e.q.draw(canvas);
            }
            super.draw(canvas);
        }

        public final void e() {
            TabLayout tabLayout = this.e;
            if (tabLayout.c == -1) {
                tabLayout.c = tabLayout.getSelectedTabPosition();
            }
            this.f(this.e.c);
        }

        public final void f(int n3) {
            if (this.e.V != 0 && (this.e.getTabSelectedIndicator().getBounds().left != -1 || this.e.getTabSelectedIndicator().getBounds().right != -1)) {
                return;
            }
            View view = this.getChildAt(n3);
            a a4 = this.e.L;
            TabLayout tabLayout = this.e;
            a4.c(tabLayout, view, tabLayout.q);
            this.e.c = n3;
        }

        public final void g() {
            this.f(this.e.getSelectedTabPosition());
        }

        public void h(int n3, float f3) {
            this.e.c = Math.round((float)n3 + f3);
            ValueAnimator valueAnimator = this.c;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.c.cancel();
            }
            this.j(this.getChildAt(n3), this.getChildAt(n3 + 1), f3);
        }

        public void i(int n3) {
            Rect rect = this.e.q.getBounds();
            this.e.q.setBounds(rect.left, 0, rect.right, n3);
            this.requestLayout();
        }

        public final void j(View view, View view2, float f3) {
            if (view != null && view.getWidth() > 0) {
                a a4 = this.e.L;
                TabLayout tabLayout = this.e;
                a4.d(tabLayout, view, view2, f3, tabLayout.q);
            } else {
                view = this.e.q;
                view.setBounds(-1, view.getBounds().top, -1, this.e.q.getBounds().bottom);
            }
            this.postInvalidateOnAnimation();
        }

        public final void k(boolean bl, int n3, int n4) {
            TabLayout tabLayout = this.e;
            if (tabLayout.c == n3) {
                return;
            }
            tabLayout = this.getChildAt(tabLayout.getSelectedTabPosition());
            Object object = this.getChildAt(n3);
            if (object == null) {
                this.g();
                return;
            }
            this.e.c = n3;
            object = new ValueAnimator.AnimatorUpdateListener(this, (View)tabLayout, (View)object){
                public final View a;
                public final View b;
                public final e c;
                {
                    this.c = e3;
                    this.a = view;
                    this.b = view2;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.c.j(this.a, this.b, valueAnimator.getAnimatedFraction());
                }
            };
            if (bl) {
                tabLayout = new ValueAnimator();
                this.c = tabLayout;
                tabLayout.setInterpolator(this.e.M);
                tabLayout.setDuration(n4);
                tabLayout.setFloatValues(new float[]{0.0f, 1.0f});
                tabLayout.addUpdateListener((ValueAnimator.AnimatorUpdateListener)object);
                tabLayout.start();
                return;
            }
            this.c.removeAllUpdateListeners();
            this.c.addUpdateListener((ValueAnimator.AnimatorUpdateListener)object);
        }

        public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
            super.onLayout(bl, n3, n4, n5, n6);
            ValueAnimator valueAnimator = this.c;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                this.k(false, this.e.getSelectedTabPosition(), -1);
                return;
            }
            this.e();
        }

        public void onMeasure(int n3, int n4) {
            super.onMeasure(n3, n4);
            if (View.MeasureSpec.getMode((int)n3) == 0x40000000) {
                TabLayout tabLayout = this.e;
                int n5 = tabLayout.C;
                int n6 = 1;
                if (n5 == 1 || tabLayout.F == 2) {
                    int n7;
                    int n8 = this.getChildCount();
                    int n9 = 0;
                    int n10 = 0;
                    for (n7 = 0; n7 < n8; ++n7) {
                        tabLayout = this.getChildAt(n7);
                        n5 = n10;
                        if (tabLayout.getVisibility() == 0) {
                            n5 = Math.max(n10, tabLayout.getMeasuredWidth());
                        }
                        n10 = n5;
                    }
                    if (n10 > 0) {
                        n5 = (int)c0.g(this.getContext(), 16);
                        if (n10 * n8 <= this.getMeasuredWidth() - n5 * 2) {
                            n5 = 0;
                            for (n7 = n9; n7 < n8; ++n7) {
                                tabLayout = (LinearLayout.LayoutParams)this.getChildAt(n7).getLayoutParams();
                                if (((LinearLayout.LayoutParams)tabLayout).width == n10 && ((LinearLayout.LayoutParams)tabLayout).weight == 0.0f) continue;
                                ((LinearLayout.LayoutParams)tabLayout).width = n10;
                                ((LinearLayout.LayoutParams)tabLayout).weight = 0.0f;
                                n5 = 1;
                            }
                        } else {
                            tabLayout = this.e;
                            tabLayout.C = 0;
                            tabLayout.R(false);
                            n5 = n6;
                        }
                        if (n5 != 0) {
                            super.onMeasure(n3, n4);
                        }
                    }
                }
            }
        }

        public void onRtlPropertiesChanged(int n3) {
            super.onRtlPropertiesChanged(n3);
        }
    }

    public static class f {
        public Object a;
        public Drawable b;
        public CharSequence c;
        public CharSequence d;
        public int e = -1;
        public View f;
        public int g = 1;
        public TabLayout h;
        public TabView i;
        public int j = -1;

        public View e() {
            return this.f;
        }

        public Drawable f() {
            return this.b;
        }

        public int g() {
            return this.e;
        }

        public int h() {
            return this.g;
        }

        public CharSequence i() {
            return this.c;
        }

        public boolean j() {
            TabLayout tabLayout = this.h;
            if (tabLayout != null) {
                int n3 = tabLayout.getSelectedTabPosition();
                return n3 != -1 && n3 == this.e;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public void k() {
            this.h = null;
            this.i = null;
            this.a = null;
            this.b = null;
            this.j = -1;
            this.c = null;
            this.d = null;
            this.e = -1;
            this.f = null;
        }

        public void l() {
            TabLayout tabLayout = this.h;
            if (tabLayout != null) {
                tabLayout.K(this);
                return;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public f m(CharSequence charSequence) {
            this.d = charSequence;
            this.u();
            return this;
        }

        public f n(int n3) {
            return this.o(LayoutInflater.from((Context)this.i.getContext()).inflate(n3, (ViewGroup)this.i, false));
        }

        public f o(View view) {
            this.f = view;
            this.u();
            return this;
        }

        public f p(int n3) {
            TabLayout tabLayout = this.h;
            if (tabLayout != null) {
                return this.q(d.a.b(tabLayout.getContext(), n3));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public f q(Drawable object) {
            this.b = object;
            object = this.h;
            if (((TabLayout)((Object)object)).C == 1 || ((TabLayout)((Object)object)).F == 2) {
                ((TabLayout)((Object)object)).R(true);
            }
            this.u();
            return this;
        }

        public void r(int n3) {
            this.e = n3;
        }

        public f s(int n3) {
            TabLayout tabLayout = this.h;
            if (tabLayout != null) {
                return this.t(tabLayout.getResources().getText(n3));
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public f t(CharSequence charSequence) {
            if (TextUtils.isEmpty((CharSequence)this.d) && !TextUtils.isEmpty((CharSequence)charSequence)) {
                this.i.setContentDescription(charSequence);
            }
            this.c = charSequence;
            this.u();
            return this;
        }

        public void u() {
            TabView tabView = this.i;
            if (tabView != null) {
                tabView.p();
            }
        }
    }

    public static class g
    implements ViewPager.i {
        public final WeakReference a;
        public int b;
        public int c;

        public g(TabLayout tabLayout) {
            this.a = new WeakReference<TabLayout>(tabLayout);
        }

        @Override
        public void a(int n3, float f3, int n4) {
            TabLayout tabLayout = (TabLayout)((Object)this.a.get());
            if (tabLayout != null) {
                boolean bl;
                n4 = this.c;
                boolean bl2 = true;
                if (n4 == 2 && this.b != 1) {
                    bl2 = false;
                }
                boolean bl3 = bl = true;
                if (n4 == 2) {
                    bl3 = this.b != 0 ? bl : false;
                }
                tabLayout.N(n3, f3, bl2, bl3, false);
            }
        }

        @Override
        public void b(int n3) {
            this.b = this.c;
            this.c = n3;
            TabLayout tabLayout = (TabLayout)((Object)this.a.get());
            if (tabLayout != null) {
                tabLayout.S(this.c);
            }
        }

        @Override
        public void c(int n3) {
            TabLayout tabLayout = (TabLayout)((Object)this.a.get());
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != n3 && n3 < tabLayout.getTabCount()) {
                int n4 = this.c;
                boolean bl = n4 == 0 || n4 == 2 && this.b == 0;
                tabLayout.L(tabLayout.B(n3), bl);
            }
        }

        public void d() {
            this.c = 0;
            this.b = 0;
        }
    }

    public static class h
    implements d {
        public final ViewPager a;

        public h(ViewPager viewPager) {
            this.a = viewPager;
        }

        @Override
        public void a(f f3) {
        }

        @Override
        public void b(f f3) {
        }

        @Override
        public void c(f f3) {
            this.a.setCurrentItem(f3.g());
        }
    }
}

