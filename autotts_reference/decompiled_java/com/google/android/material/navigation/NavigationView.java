/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.InsetDrawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Pair
 *  android.util.TypedValue
 *  android.view.Gravity
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.FrameLayout
 */
package com.google.android.material.navigation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import androidx.activity.b;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.widget.m0;
import androidx.customview.view.AbsSavedState;
import androidx.drawerlayout.widget.DrawerLayout;
import c.a;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.d0;
import com.google.android.material.internal.p;
import com.google.android.material.internal.q;
import com.google.android.material.internal.z;
import com.google.android.material.navigation.c;
import com.google.android.material.navigation.i;
import com.google.android.material.navigation.j;
import h.g;
import java.util.Objects;
import o0.z1;
import v2.o;
import v2.s;
import z1.l;
import z1.m;

public class NavigationView
extends ScrimInsetsFrameLayout
implements p2.b {
    public static final int[] A = new int[]{0x10100A0};
    public static final int[] B = new int[]{-16842910};
    public static final int C = z1.l.Widget_Design_NavigationView;
    public final p j;
    public final q k;
    public final int l;
    public final int[] m;
    public MenuInflater n;
    public ViewTreeObserver.OnGlobalLayoutListener o;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean s;
    public int t;
    public final boolean u;
    public final int v;
    public final s w;
    public final p2.j x;
    public final p2.c y;
    public final DrawerLayout.e z;

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.navigationViewStyle);
    }

    public NavigationView(Context object, AttributeSet object2, int n3) {
        p p3;
        q q3;
        int n4 = C;
        super(y2.a.d(object, (AttributeSet)object2, n3, n4), (AttributeSet)object2, n3);
        this.k = q3 = new q();
        this.m = new int[2];
        this.p = true;
        this.q = true;
        this.r = true;
        this.s = true;
        this.t = 0;
        this.w = v2.s.a((View)this);
        this.x = new p2.j((View)this);
        this.y = new p2.c((View)this);
        this.z = new DrawerLayout.f(this){
            public final NavigationView a;
            {
                this.a = navigationView;
            }

            @Override
            public void c(View view) {
                Object object = this.a;
                if (view == object) {
                    object = ((NavigationView)object).y;
                    Objects.requireNonNull(object);
                    view.post((Runnable)new j((p2.c)object));
                }
            }

            @Override
            public void d(View view) {
                NavigationView navigationView = this.a;
                if (view == navigationView) {
                    navigationView.y.f();
                    this.a.u();
                }
            }
        };
        Context context = this.getContext();
        this.j = p3 = new p(context);
        m0 m02 = com.google.android.material.internal.z.j(context, (AttributeSet)object2, z1.m.NavigationView, n3, n4, new int[0]);
        int n5 = z1.m.NavigationView_android_background;
        if (m02.s(n5)) {
            this.setBackground(m02.g(n5));
        }
        this.t = n5 = m02.f(z1.m.NavigationView_drawerLayoutCornerSize, 0);
        boolean bl = n5 == 0;
        this.u = bl;
        this.v = this.getResources().getDimensionPixelSize(z1.e.m3_navigation_drawer_layout_corner_size);
        Drawable drawable = this.getBackground();
        object = j2.d.g(drawable);
        if (drawable == null || object != null) {
            object2 = new v2.i(v2.o.e(context, (AttributeSet)object2, n3, n4).m());
            if (object != null) {
                ((v2.i)object2).i0((ColorStateList)object);
            }
            ((v2.i)object2).W(context);
            this.setBackground((Drawable)object2);
        }
        if (m02.s(n3 = z1.m.NavigationView_elevation)) {
            this.setElevation(m02.f(n3, 0));
        }
        this.setFitsSystemWindows(m02.a(z1.m.NavigationView_android_fitsSystemWindows, false));
        this.l = m02.f(z1.m.NavigationView_android_maxWidth, 0);
        n3 = z1.m.NavigationView_subheaderColor;
        object = m02.s(n3) ? m02.c(n3) : null;
        n3 = z1.m.NavigationView_subheaderTextAppearance;
        n3 = m02.s(n3) ? m02.n(n3, 0) : 0;
        object2 = object;
        if (n3 == 0) {
            object2 = object;
            if (object == null) {
                object2 = this.k(16842808);
            }
        }
        drawable = m02.s(n4 = z1.m.NavigationView_itemIconTint) ? m02.c(n4) : this.k(16842808);
        n4 = z1.m.NavigationView_itemTextAppearance;
        n4 = m02.s(n4) ? m02.n(n4, 0) : 0;
        bl = m02.a(z1.m.NavigationView_itemTextAppearanceActiveBoldEnabled, true);
        n5 = z1.m.NavigationView_itemIconSize;
        if (m02.s(n5)) {
            this.setItemIconSize(m02.f(n5, 0));
        }
        object = m02.s(n5 = z1.m.NavigationView_itemTextColor) ? m02.c(n5) : null;
        Context context2 = object;
        if (n4 == 0) {
            context2 = object;
            if (object == null) {
                context2 = this.k(16842806);
            }
        }
        Drawable drawable2 = m02.g(z1.m.NavigationView_itemBackground);
        object = drawable2;
        if (drawable2 == null) {
            object = drawable2;
            if (this.n(m02)) {
                drawable2 = this.l(m02);
                ColorStateList colorStateList = s2.c.b(context, m02, z1.m.NavigationView_itemRippleColor);
                object = drawable2;
                if (colorStateList != null) {
                    object = this.m(m02, null);
                    q3.J(new RippleDrawable(t2.a.d(colorStateList), null, (Drawable)object));
                    object = drawable2;
                }
            }
        }
        if (m02.s(n5 = z1.m.NavigationView_itemHorizontalPadding)) {
            this.setItemHorizontalPadding(m02.f(n5, 0));
        }
        if (m02.s(n5 = z1.m.NavigationView_itemVerticalPadding)) {
            this.setItemVerticalPadding(m02.f(n5, 0));
        }
        this.setDividerInsetStart(m02.f(z1.m.NavigationView_dividerInsetStart, 0));
        this.setDividerInsetEnd(m02.f(z1.m.NavigationView_dividerInsetEnd, 0));
        this.setSubheaderInsetStart(m02.f(z1.m.NavigationView_subheaderInsetStart, 0));
        this.setSubheaderInsetEnd(m02.f(z1.m.NavigationView_subheaderInsetEnd, 0));
        this.setTopInsetScrimEnabled(m02.a(z1.m.NavigationView_topInsetScrimEnabled, this.p));
        this.setBottomInsetScrimEnabled(m02.a(z1.m.NavigationView_bottomInsetScrimEnabled, this.q));
        this.setStartInsetScrimEnabled(m02.a(z1.m.NavigationView_startInsetScrimEnabled, this.r));
        this.setEndInsetScrimEnabled(m02.a(z1.m.NavigationView_endInsetScrimEnabled, this.s));
        n5 = m02.f(z1.m.NavigationView_itemIconPadding, 0);
        this.setItemMaxLines(m02.k(z1.m.NavigationView_itemMaxLines, 1));
        p3.W(new e.a(this){
            public final NavigationView c;
            {
                this.c = navigationView;
            }

            @Override
            public boolean a(e e3, MenuItem menuItem) {
                this.c.getClass();
                return false;
            }

            @Override
            public void b(e e3) {
            }
        });
        q3.H(1);
        q3.b(context, p3);
        if (n3 != 0) {
            q3.X(n3);
        }
        q3.U((ColorStateList)object2);
        q3.N((ColorStateList)drawable);
        q3.T(this.getOverScrollMode());
        if (n4 != 0) {
            q3.P(n4);
        }
        q3.Q(bl);
        q3.R((ColorStateList)context2);
        q3.I((Drawable)object);
        q3.L(n5);
        p3.b(q3);
        this.addView((View)q3.y((ViewGroup)this));
        n3 = z1.m.NavigationView_menu;
        if (m02.s(n3)) {
            this.p(m02.n(n3, 0));
        }
        if (m02.s(n3 = z1.m.NavigationView_headerLayout)) {
            this.o(m02.n(n3, 0));
        }
        m02.x();
        this.x();
    }

    public static /* synthetic */ void f(NavigationView navigationView, Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    private MenuInflater getMenuInflater() {
        if (this.n == null) {
            this.n = new g(this.getContext());
        }
        return this.n;
    }

    public static /* synthetic */ int[] i(NavigationView navigationView) {
        return navigationView.m;
    }

    public static /* synthetic */ q j(NavigationView navigationView) {
        return navigationView.k;
    }

    private ColorStateList k(int n3) {
        Object object = new TypedValue();
        if (!this.getContext().getTheme().resolveAttribute(n3, object, true)) {
            return null;
        }
        ColorStateList colorStateList = d.a.a(this.getContext(), object.resourceId);
        if (!this.getContext().getTheme().resolveAttribute(a.colorPrimary, object, true)) {
            return null;
        }
        int n4 = object.data;
        int n5 = colorStateList.getDefaultColor();
        int[] nArray = B;
        int[] nArray2 = A;
        object = FrameLayout.EMPTY_STATE_SET;
        n3 = colorStateList.getColorForState(nArray, n5);
        return new ColorStateList((int[][])new int[][]{nArray, nArray2, (int[])object}, new int[]{n3, n4, n5});
    }

    @Override
    public void a() {
        Pair pair = this.w();
        DrawerLayout drawerLayout = (DrawerLayout)((Object)pair.first);
        b b3 = this.x.c();
        if (b3 != null && Build.VERSION.SDK_INT >= 34) {
            int n3 = ((DrawerLayout.LayoutParams)((Object)pair.second)).a;
            pair = com.google.android.material.navigation.c.b(drawerLayout, (View)this);
            drawerLayout = com.google.android.material.navigation.c.c(drawerLayout);
            this.x.h(b3, n3, (Animator.AnimatorListener)pair, (ValueAnimator.AnimatorUpdateListener)drawerLayout);
            return;
        }
        drawerLayout.d((View)this);
    }

    @Override
    public void b(b b3) {
        this.w();
        this.x.j(b3);
    }

    @Override
    public void c(b b3) {
        Pair pair = this.w();
        this.x.l(b3, ((DrawerLayout.LayoutParams)((Object)pair.second)).a);
        if (this.u) {
            float f3 = this.x.a(b3.a());
            this.t = a2.a.c(0, this.v, f3);
            this.v(this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void d() {
        this.w();
        this.x.f();
        this.u();
    }

    public void dispatchDraw(Canvas canvas) {
        this.w.e(canvas, new i(this));
    }

    @Override
    public void e(z1 z12) {
        this.k.h(z12);
    }

    public p2.j getBackHelper() {
        return this.x;
    }

    public MenuItem getCheckedItem() {
        return this.k.n();
    }

    public int getDividerInsetEnd() {
        return this.k.o();
    }

    public int getDividerInsetStart() {
        return this.k.p();
    }

    public int getHeaderCount() {
        return this.k.q();
    }

    public Drawable getItemBackground() {
        return this.k.r();
    }

    public int getItemHorizontalPadding() {
        return this.k.s();
    }

    public int getItemIconPadding() {
        return this.k.t();
    }

    public ColorStateList getItemIconTintList() {
        return this.k.w();
    }

    public int getItemMaxLines() {
        return this.k.u();
    }

    public ColorStateList getItemTextColor() {
        return this.k.v();
    }

    public int getItemVerticalPadding() {
        return this.k.x();
    }

    public Menu getMenu() {
        return this.j;
    }

    public int getSubheaderInsetEnd() {
        return this.k.z();
    }

    public int getSubheaderInsetStart() {
        return this.k.A();
    }

    public final Drawable l(m0 m02) {
        return this.m(m02, s2.c.b(this.getContext(), m02, z1.m.NavigationView_itemShapeFillColor));
    }

    public final Drawable m(m0 m02, ColorStateList colorStateList) {
        int n3 = m02.n(z1.m.NavigationView_itemShapeAppearance, 0);
        int n4 = m02.n(z1.m.NavigationView_itemShapeAppearanceOverlay, 0);
        v2.i i3 = new v2.i(v2.o.b(this.getContext(), n3, n4).m());
        i3.i0(colorStateList);
        return new InsetDrawable((Drawable)i3, m02.f(z1.m.NavigationView_itemShapeInsetStart, 0), m02.f(z1.m.NavigationView_itemShapeInsetTop, 0), m02.f(z1.m.NavigationView_itemShapeInsetEnd, 0), m02.f(z1.m.NavigationView_itemShapeInsetBottom, 0));
    }

    public final boolean n(m0 m02) {
        return m02.s(z1.m.NavigationView_itemShapeAppearance) || m02.s(z1.m.NavigationView_itemShapeAppearanceOverlay);
        {
        }
    }

    public View o(int n3) {
        return this.k.C(n3);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        v2.j.e((View)this);
        Object object = this.getParent();
        if (object instanceof DrawerLayout && this.y.b()) {
            object = (DrawerLayout)((Object)object);
            ((DrawerLayout)((Object)object)).I(this.z);
            ((DrawerLayout)((Object)object)).a(this.z);
            if (((DrawerLayout)((Object)object)).A((View)this)) {
                this.y.e();
            }
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getViewTreeObserver().removeOnGlobalLayoutListener(this.o);
        ViewParent viewParent = this.getParent();
        if (viewParent instanceof DrawerLayout) {
            ((DrawerLayout)viewParent).I(this.z);
        }
        this.y.f();
    }

    public void onMeasure(int n3, int n4) {
        int n5 = View.MeasureSpec.getMode((int)n3);
        if (n5 != Integer.MIN_VALUE) {
            if (n5 == 0) {
                n3 = View.MeasureSpec.makeMeasureSpec((int)this.l, (int)0x40000000);
            }
        } else {
            n3 = View.MeasureSpec.makeMeasureSpec((int)Math.min(View.MeasureSpec.getSize((int)n3), this.l), (int)0x40000000);
        }
        super.onMeasure(n3, n4);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.o());
        this.j.T(parcelable.e);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.e = bundle = new Bundle();
        this.j.V(bundle);
        return savedState;
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        this.v(n3, n4);
    }

    public void p(int n3) {
        this.k.Y(true);
        this.getMenuInflater().inflate(n3, (Menu)this.j);
        this.k.Y(false);
        this.k.g(false);
    }

    public boolean q() {
        return this.q;
    }

    public boolean r() {
        return this.s;
    }

    public boolean s() {
        return this.r;
    }

    public void setBottomInsetScrimEnabled(boolean bl) {
        this.q = bl;
    }

    public void setCheckedItem(int n3) {
        MenuItem menuItem = this.j.findItem(n3);
        if (menuItem != null) {
            this.k.E((androidx.appcompat.view.menu.g)menuItem);
        }
    }

    public void setCheckedItem(MenuItem menuItem) {
        if ((menuItem = this.j.findItem(menuItem.getItemId())) != null) {
            this.k.E((androidx.appcompat.view.menu.g)menuItem);
            return;
        }
        throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
    }

    public void setDividerInsetEnd(int n3) {
        this.k.F(n3);
    }

    public void setDividerInsetStart(int n3) {
        this.k.G(n3);
    }

    public void setElevation(float f3) {
        super.setElevation(f3);
        v2.j.d((View)this, f3);
    }

    public void setEndInsetScrimEnabled(boolean bl) {
        this.s = bl;
    }

    public void setForceCompatClippingEnabled(boolean bl) {
        this.w.h((View)this, bl);
    }

    public void setItemBackground(Drawable drawable) {
        this.k.I(drawable);
    }

    public void setItemBackgroundResource(int n3) {
        this.setItemBackground(this.getContext().getDrawable(n3));
    }

    public void setItemHorizontalPadding(int n3) {
        this.k.K(n3);
    }

    public void setItemHorizontalPaddingResource(int n3) {
        this.k.K(this.getResources().getDimensionPixelSize(n3));
    }

    public void setItemIconPadding(int n3) {
        this.k.L(n3);
    }

    public void setItemIconPaddingResource(int n3) {
        this.k.L(this.getResources().getDimensionPixelSize(n3));
    }

    public void setItemIconSize(int n3) {
        this.k.M(n3);
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        this.k.N(colorStateList);
    }

    public void setItemMaxLines(int n3) {
        this.k.O(n3);
    }

    public void setItemTextAppearance(int n3) {
        this.k.P(n3);
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean bl) {
        this.k.Q(bl);
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.k.R(colorStateList);
    }

    public void setItemVerticalPadding(int n3) {
        this.k.S(n3);
    }

    public void setItemVerticalPaddingResource(int n3) {
        this.k.S(this.getResources().getDimensionPixelSize(n3));
    }

    public void setNavigationItemSelectedListener(d d3) {
    }

    public void setOverScrollMode(int n3) {
        super.setOverScrollMode(n3);
        q q3 = this.k;
        if (q3 != null) {
            q3.T(n3);
        }
    }

    public void setStartInsetScrimEnabled(boolean bl) {
        this.r = bl;
    }

    public void setSubheaderInsetEnd(int n3) {
        this.k.V(n3);
    }

    public void setSubheaderInsetStart(int n3) {
        this.k.W(n3);
    }

    public void setTopInsetScrimEnabled(boolean bl) {
        this.p = bl;
    }

    public boolean t() {
        return this.p;
    }

    public final void u() {
        if (this.u && this.t != 0) {
            this.t = 0;
            this.v(this.getWidth(), this.getHeight());
        }
    }

    public final void v(int n3, int n4) {
        if (this.getParent() instanceof DrawerLayout && this.getLayoutParams() instanceof DrawerLayout.LayoutParams && (this.t > 0 || this.u) && this.getBackground() instanceof v2.i) {
            boolean bl = Gravity.getAbsoluteGravity((int)((DrawerLayout.LayoutParams)this.getLayoutParams()).a, (int)this.getLayoutDirection()) == 3;
            v2.i i3 = (v2.i)this.getBackground();
            Object object = i3.K().w().o(this.t);
            if (bl) {
                ((o.b)object).E(0.0f);
                ((o.b)object).v(0.0f);
            } else {
                ((o.b)object).I(0.0f);
                ((o.b)object).z(0.0f);
            }
            object = ((o.b)object).m();
            i3.setShapeAppearanceModel((o)object);
            this.w.g((View)this, (o)object);
            this.w.f((View)this, new RectF(0.0f, 0.0f, (float)n3, (float)n4));
            this.w.i((View)this, true);
        }
    }

    public final Pair w() {
        ViewParent viewParent = this.getParent();
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        if (viewParent instanceof DrawerLayout && layoutParams instanceof DrawerLayout.LayoutParams) {
            return new Pair((Object)((DrawerLayout)viewParent), (Object)((DrawerLayout.LayoutParams)layoutParams));
        }
        throw new IllegalStateException("NavigationView back progress requires the direct parent view to be a DrawerLayout.");
    }

    public final void x() {
        this.o = new ViewTreeObserver.OnGlobalLayoutListener(this){
            public final NavigationView c;
            {
                this.c = navigationView;
            }

            /*
             * Unable to fully structure code
             */
            public void onGlobalLayout() {
                block2: {
                    block4: {
                        block3: {
                            var6_1 = this.c;
                            var6_1.getLocationOnScreen(NavigationView.i((NavigationView)var6_1));
                            var6_1 = NavigationView.i(this.c);
                            var5_2 = true;
                            var4_3 = var6_1[1] == false;
                            NavigationView.j(this.c).D(var4_3);
                            var6_1 = this.c;
                            var4_3 = var4_3 != false && var6_1.t() != false;
                            var6_1.setDrawTopInsetForeground(var4_3);
                            var1_4 = this.c.getLayoutDirection() == 1;
                            var2_5 = NavigationView.i(this.c)[0] == 0 || NavigationView.i(this.c)[0] + this.c.getWidth() == 0;
                            var6_1 = this.c;
                            var4_3 = var2_5 != false && (var1_4 != false ? var6_1.r() != false : var6_1.s() != false);
                            var6_1.setDrawLeftInsetForeground(var4_3);
                            var7_6 = com.google.android.material.internal.c.a(this.c.getContext());
                            if (var7_6 == null) break block2;
                            var6_1 = d0.a((Context)var7_6);
                            var2_5 = var6_1.height() - this.c.getHeight() == NavigationView.i(this.c)[1];
                            var3_7 = Color.alpha((int)var7_6.getWindow().getNavigationBarColor()) != 0;
                            var7_6 = this.c;
                            var4_3 = var2_5 != false && var3_7 != false && var7_6.q() != false;
                            var7_6.setDrawBottomInsetForeground(var4_3);
                            var2_5 = var6_1.width() == NavigationView.i(this.c)[0] || var6_1.width() - this.c.getWidth() == NavigationView.i(this.c)[0];
                            var6_1 = this.c;
                            if (!var2_5) ** GOTO lbl-1000
                            if (!var1_4) break block3;
                            if (!var6_1.s()) ** GOTO lbl-1000
                            var4_3 = var5_2;
                            break block4;
                        }
                        if (var6_1.r()) {
                            var4_3 = var5_2;
                        } else lbl-1000:
                        // 3 sources

                        {
                            var4_3 = false;
                        }
                    }
                    var6_1.setDrawRightInsetForeground(var4_3);
                }
            }
        };
        this.getViewTreeObserver().addOnGlobalLayoutListener(this.o);
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
        public Bundle e;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.e = parcel.readBundle(classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n3) {
            super.writeToParcel(parcel, n3);
            parcel.writeBundle(this.e);
        }
    }

    public static interface d {
    }
}

