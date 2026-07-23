/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.PathInterpolator
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ScrollView
 */
package com.google.android.material.navigationrail;

import a2.a;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import androidx.appcompat.widget.m0;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import androidx.transition.c;
import com.google.android.material.internal.c0;
import com.google.android.material.internal.z;
import com.google.android.material.navigation.NavigationBarDividerView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigationrail.NavigationRailFrameLayout;
import com.google.android.material.navigationrail.NavigationRailMenuView;
import o0.z1;
import q2.b;
import z1.e;
import z1.l;
import z1.m;

public class NavigationRailView
extends NavigationBarView {
    public static final TimeInterpolator A = new PathInterpolator(0.38f, 1.21f, 0.22f, 1.0f);
    public final int g;
    public final int h;
    public final int i;
    public final int j;
    public final boolean k;
    public boolean l;
    public View m;
    public Boolean n = null;
    public Boolean o = null;
    public Boolean p = null;
    public boolean q = false;
    public int r;
    public int s = -1;
    public int t = 0;
    public int u = 49;
    public int v;
    public int w;
    public int x;
    public int y;
    public NavigationRailFrameLayout z;

    public NavigationRailView(Context context) {
        this(context, null);
    }

    public NavigationRailView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.navigationRailStyle);
    }

    public NavigationRailView(Context context, AttributeSet attributeSet, int n3) {
        this(context, attributeSet, n3, z1.l.Widget_MaterialComponents_NavigationRailView);
    }

    public NavigationRailView(Context context, AttributeSet attributeSet, int n3, int n4) {
        super(context, attributeSet, n3, n4);
        context = this.getContext();
        this.y = this.getContext().getResources().getDimensionPixelSize(z1.e.m3_navigation_rail_expanded_item_spacing);
        this.x = 8388627;
        this.w = 1;
        m0 m02 = com.google.android.material.internal.z.j(context, attributeSet, z1.m.NavigationRailView, n3, n4, new int[0]);
        n3 = z1.m.NavigationRailView_contentMarginTop;
        attributeSet = this.getResources();
        n4 = z1.e.mtrl_navigation_rail_margin;
        this.g = m02.f(n3, attributeSet.getDimensionPixelSize(n4));
        this.h = m02.f(z1.m.NavigationRailView_headerMarginBottom, this.getResources().getDimensionPixelSize(n4));
        this.k = m02.a(z1.m.NavigationRailView_scrollingEnabled, false);
        this.setSubmenuDividersEnabled(m02.a(z1.m.NavigationRailView_submenuDividersEnabled, false));
        this.k();
        n3 = m02.n(z1.m.NavigationRailView_headerLayout, 0);
        if (n3 != 0) {
            this.l(n3);
        }
        this.setMenuGravity(m02.k(z1.m.NavigationRailView_menuGravity, 49));
        n4 = z1.m.NavigationRailView_itemMinHeight;
        n3 = m02.f(n4, -1);
        n4 = m02.f(n4, -1);
        int n5 = z1.m.NavigationRailView_collapsedItemMinHeight;
        if (m02.s(n5)) {
            n3 = m02.f(n5, -1);
        }
        if (m02.s(n5 = z1.m.NavigationRailView_expandedItemMinHeight)) {
            n4 = m02.f(n5, -1);
        }
        this.setCollapsedItemMinimumHeight(n3);
        this.setExpandedItemMinimumHeight(n4);
        this.i = m02.f(z1.m.NavigationRailView_expandedMinWidth, context.getResources().getDimensionPixelSize(z1.e.m3_navigation_rail_min_expanded_width));
        this.j = m02.f(z1.m.NavigationRailView_expandedMaxWidth, context.getResources().getDimensionPixelSize(z1.e.m3_navigation_rail_max_expanded_width));
        n3 = z1.m.NavigationRailView_paddingTopSystemWindowInsets;
        if (m02.s(n3)) {
            this.n = m02.a(n3, false);
        }
        if (m02.s(n3 = z1.m.NavigationRailView_paddingBottomSystemWindowInsets)) {
            this.o = m02.a(n3, false);
        }
        if (m02.s(n3 = z1.m.NavigationRailView_paddingStartSystemWindowInsets)) {
            this.p = m02.a(n3, false);
        }
        n4 = this.getResources().getDimensionPixelOffset(z1.e.m3_navigation_rail_item_padding_top_with_large_font);
        n3 = this.getResources().getDimensionPixelOffset(z1.e.m3_navigation_rail_item_padding_bottom_with_large_font);
        float f3 = a.b(0.0f, 1.0f, 0.3f, 1.0f, s2.c.f(context) - 1.0f);
        float f4 = a.c(this.getItemPaddingTop(), n4, f3);
        f3 = a.c(this.getItemPaddingBottom(), n3, f3);
        this.setItemPaddingTop(Math.round(f4));
        this.setItemPaddingBottom(Math.round(f3));
        this.setCollapsedItemSpacing(m02.f(z1.m.NavigationRailView_itemSpacing, 0));
        this.setExpanded(m02.a(z1.m.NavigationRailView_expanded, false));
        m02.x();
        this.n();
    }

    private int getMaxChildWidth() {
        int n3 = this.getNavigationRailMenuView().getChildCount();
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getNavigationRailMenuView().getChildAt(i3);
            int n5 = n4;
            if (view.getVisibility() != 8) {
                n5 = n4;
                if (!(view instanceof NavigationBarDividerView)) {
                    n5 = Math.max(n4, view.getMeasuredWidth());
                }
            }
            n4 = n5;
        }
        return n4;
    }

    private NavigationRailMenuView getNavigationRailMenuView() {
        return (NavigationRailMenuView)this.getMenuView();
    }

    private void n() {
        c0.f((View)this, new c0.d(this){
            public final NavigationRailView a;
            {
                this.a = navigationRailView;
            }

            @Override
            public z1 a(View view, z1 z12, c0.e e3) {
                g0.b b3 = z12.f(z1.m.e());
                g0.b b4 = z12.f(z1.m.a());
                NavigationRailView navigationRailView = this.a;
                if (navigationRailView.s(navigationRailView.n)) {
                    e3.b += b3.b;
                }
                if ((navigationRailView = this.a).s(navigationRailView.o)) {
                    e3.d += b3.d;
                }
                if ((navigationRailView = this.a).s(navigationRailView.p)) {
                    e3.a = c0.m(view) ? (e3.a += Math.max(b3.c, b4.c)) : (e3.a += Math.max(b3.a, b4.a));
                }
                e3.a(view);
                return z12;
            }
        });
    }

    private void setExpanded(boolean bl) {
        if (this.q == bl) {
            return;
        }
        this.t();
        this.q = bl;
        int n3 = this.t;
        int n4 = this.r;
        int n5 = this.s;
        int n6 = this.u;
        if (bl) {
            n3 = this.w;
            n4 = this.y;
            n5 = this.v;
            n6 = this.x;
        }
        this.getNavigationRailMenuView().setItemGravity(n6);
        super.setItemIconGravity(n3);
        this.getNavigationRailMenuView().setItemSpacing(n4);
        this.getNavigationRailMenuView().setItemMinimumHeight(n5);
        this.getNavigationRailMenuView().setExpanded(bl);
    }

    @Override
    public boolean e() {
        return true;
    }

    @Override
    public boolean f() {
        return true;
    }

    public int getCollapsedItemMinimumHeight() {
        return this.s;
    }

    @Override
    public int getCollapsedMaxItemCount() {
        return 7;
    }

    public int getExpandedItemMinimumHeight() {
        return this.v;
    }

    public View getHeaderView() {
        return this.m;
    }

    @Override
    public int getItemGravity() {
        return this.getNavigationRailMenuView().getItemGravity();
    }

    @Override
    public int getItemIconGravity() {
        return this.getNavigationRailMenuView().getItemIconGravity();
    }

    public int getItemMinimumHeight() {
        return this.getNavigationRailMenuView().getItemMinimumHeight();
    }

    public int getItemSpacing() {
        return this.getNavigationRailMenuView().getItemSpacing();
    }

    @Override
    public int getMaxItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getMenuGravity() {
        return this.getNavigationRailMenuView().getMenuGravity();
    }

    public boolean getSubmenuDividersEnabled() {
        return this.l;
    }

    public final void k() {
        NavigationRailFrameLayout navigationRailFrameLayout;
        View view = (View)this.getMenuView();
        this.z = navigationRailFrameLayout = new NavigationRailFrameLayout(this.getContext());
        navigationRailFrameLayout.setPaddingTop(this.g);
        this.z.setScrollingEnabled(this.k);
        this.z.setClipChildren(false);
        this.z.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        view.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-2, -2));
        this.z.addView(view);
        if (!this.k) {
            this.addView((View)this.z);
            return;
        }
        navigationRailFrameLayout = new ScrollView(this.getContext());
        navigationRailFrameLayout.setVerticalScrollBarEnabled(false);
        navigationRailFrameLayout.addView((View)this.z);
        navigationRailFrameLayout.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, -1));
        this.addView((View)navigationRailFrameLayout);
    }

    public void l(int n3) {
        this.m(LayoutInflater.from((Context)this.getContext()).inflate(n3, (ViewGroup)this, false));
    }

    public void m(View view) {
        this.r();
        this.m = view;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 49;
        layoutParams.bottomMargin = this.h;
        this.z.addView(view, 0, (ViewGroup.LayoutParams)layoutParams);
    }

    public NavigationRailMenuView o(Context context) {
        return new NavigationRailMenuView(context);
    }

    public void onMeasure(int n3, int n4) {
        int n5 = this.q(n3);
        if (this.q) {
            this.measureChild((View)this.getNavigationRailMenuView(), n3, n4);
            View view = this.m;
            if (view != null) {
                this.measureChild(view, n3, n4);
            }
            n5 = n3 = this.p(n3, this.getMaxChildWidth());
            if (this.getItemActiveIndicatorExpandedWidth() == -1) {
                this.getNavigationRailMenuView().p(View.MeasureSpec.getSize((int)n3));
                n5 = n3;
            }
        }
        super.onMeasure(n5, n4);
        if (this.z.getMeasuredHeight() < this.getMeasuredHeight()) {
            this.measureChild((View)this.z, n5, View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0x40000000));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public final int p(int n3, int n4) {
        int n5 = Math.min(this.i, View.MeasureSpec.getSize((int)n3));
        int n6 = n3;
        if (View.MeasureSpec.getMode((int)n3) != 0x40000000) {
            n4 = Math.max(n4, n5);
            View view = this.m;
            n3 = n4;
            if (view != null) {
                n3 = Math.max(n4, view.getMeasuredWidth());
            }
            n6 = View.MeasureSpec.makeMeasureSpec((int)Math.max(this.getSuggestedMinimumWidth(), Math.min(n3, this.j)), (int)0x40000000);
        }
        return n6;
    }

    public final int q(int n3) {
        int n4 = this.getSuggestedMinimumWidth();
        int n5 = n3;
        if (View.MeasureSpec.getMode((int)n3) != 0x40000000) {
            n5 = n3;
            if (n4 > 0) {
                int n6 = this.getPaddingLeft();
                n5 = this.getPaddingRight();
                n5 = View.MeasureSpec.makeMeasureSpec((int)Math.min(View.MeasureSpec.getSize((int)n3), n4 + (n6 + n5)), (int)0x40000000);
            }
        }
        return n5;
    }

    public void r() {
        View view = this.m;
        if (view != null) {
            this.z.removeView(view);
            this.m = null;
        }
    }

    public final boolean s(Boolean bl) {
        if (bl != null) {
            return bl;
        }
        return this.getFitsSystemWindows();
    }

    public void setCollapsedItemMinimumHeight(int n3) {
        this.s = n3;
        if (!this.q) {
            ((NavigationRailMenuView)this.getMenuView()).setItemMinimumHeight(n3);
        }
    }

    public void setCollapsedItemSpacing(int n3) {
        this.r = n3;
        if (!this.q) {
            this.getNavigationRailMenuView().setItemSpacing(n3);
        }
    }

    public void setExpandedItemMinimumHeight(int n3) {
        this.v = n3;
        if (this.q) {
            ((NavigationRailMenuView)this.getMenuView()).setItemMinimumHeight(n3);
        }
    }

    @Override
    public void setItemGravity(int n3) {
        this.u = n3;
        this.x = n3;
        super.setItemGravity(n3);
    }

    @Override
    public void setItemIconGravity(int n3) {
        this.t = n3;
        this.w = n3;
        super.setItemIconGravity(n3);
    }

    public void setItemMinimumHeight(int n3) {
        this.s = n3;
        this.v = n3;
        ((NavigationRailMenuView)this.getMenuView()).setItemMinimumHeight(n3);
    }

    public void setItemSpacing(int n3) {
        this.r = n3;
        this.y = n3;
        this.getNavigationRailMenuView().setItemSpacing(n3);
    }

    public void setMenuGravity(int n3) {
        this.getNavigationRailMenuView().setMenuGravity(n3);
    }

    public void setSubmenuDividersEnabled(boolean bl) {
        if (this.l == bl) {
            return;
        }
        this.l = bl;
        this.getNavigationRailMenuView().setSubmenuDividersEnabled(bl);
    }

    public final void t() {
        Object object;
        if (!this.isLaidOut()) {
            return;
        }
        Transition transition = new ChangeBounds().f0(500L).h0(A);
        Transition transition2 = new Fade().f0(100L);
        Transition transition3 = new Fade().f0(100L);
        b b3 = new b();
        Transition transition4 = new Fade().f0(100L);
        int n3 = this.getNavigationRailMenuView().getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getNavigationRailMenuView().getChildAt(i3);
            if (view instanceof NavigationBarItemView) {
                object = (NavigationBarItemView)view;
                transition.r((View)((NavigationBarItemView)object).getLabelGroup(), true);
                transition.r((View)((NavigationBarItemView)object).getExpandedLabelGroup(), true);
                if (this.q) {
                    transition3.b((View)((NavigationBarItemView)object).getExpandedLabelGroup());
                    transition2.b((View)((NavigationBarItemView)object).getLabelGroup());
                } else {
                    transition3.b((View)((NavigationBarItemView)object).getLabelGroup());
                    transition2.b((View)((NavigationBarItemView)object).getExpandedLabelGroup());
                }
                b3.b((View)((NavigationBarItemView)object).getExpandedLabelGroup());
            }
            transition4.b(view);
        }
        object = new TransitionSet();
        ((TransitionSet)object).y0(0);
        ((TransitionSet)object).q0(transition).q0(transition2).q0(b3);
        if (!this.q) {
            ((TransitionSet)object).q0(transition4);
        }
        transition2 = new TransitionSet();
        ((TransitionSet)transition2).y0(0);
        ((TransitionSet)transition2).q0(transition3);
        if (this.q) {
            ((TransitionSet)transition2).q0(transition4);
        }
        transition3 = new TransitionSet();
        ((TransitionSet)transition3).y0(1);
        ((TransitionSet)transition3).q0(transition2).q0((Transition)object);
        androidx.transition.c.a((ViewGroup)this.getParent(), transition3);
    }
}

