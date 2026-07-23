/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.graphics.Region$Op
 *  android.graphics.Typeface
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$Callback
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.animation.AnimationUtils
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.Toolbar
 */
package com.google.android.material.appbar;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import androidx.appcompat.widget.Toolbar;
import c.i;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.internal.b;
import com.google.android.material.internal.v;
import com.google.android.material.internal.z;
import o0.f0;
import o0.x0;
import o0.z1;
import p2.k;
import y2.a;
import z1.e;
import z1.g;
import z1.l;
import z1.m;

public class CollapsingToolbarLayout
extends FrameLayout {
    public static final int N = z1.l.Widget_Design_CollapsingToolbar;
    public final TimeInterpolator A;
    public int B;
    public AppBarLayout.f C;
    public int D;
    public int E;
    public int F;
    public z1 G;
    public int H;
    public boolean I;
    public int J;
    public int K;
    public boolean L;
    public int M;
    public boolean c;
    public int d;
    public ViewGroup e;
    public View f;
    public View g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public final Rect m;
    public final b n;
    public final b o;
    public final k2.a p;
    public boolean q;
    public boolean r;
    public final int s;
    public Drawable t;
    public Drawable u;
    public int v;
    public boolean w;
    public ValueAnimator x;
    public long y;
    public final TimeInterpolator z;

    public CollapsingToolbarLayout(Context context) {
        this(context, null);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, z1.c.collapsingToolbarLayoutStyle);
    }

    public CollapsingToolbarLayout(Context context, AttributeSet attributeSet, int n3) {
        int n4;
        b b3;
        int n5 = N;
        super(a.d(context, attributeSet, n3, n5), attributeSet, n3);
        this.c = true;
        this.m = new Rect();
        this.B = -1;
        this.H = 0;
        this.J = 0;
        this.K = 0;
        this.M = 0;
        context = this.getContext();
        this.E = this.getResources().getConfiguration().orientation;
        this.n = b3 = new b((View)this);
        TimeInterpolator timeInterpolator = a2.a.e;
        b3.P0(timeInterpolator);
        b3.L0(false);
        this.p = new k2.a(context);
        attributeSet = com.google.android.material.internal.z.i(context, attributeSet, z1.m.CollapsingToolbarLayout, n3, n5, new int[0]);
        n5 = attributeSet.getInt(z1.m.CollapsingToolbarLayout_expandedTitleGravity, 8388691);
        n3 = attributeSet.getInt(z1.m.CollapsingToolbarLayout_collapsedTitleGravity, 8388627);
        this.s = attributeSet.getInt(z1.m.CollapsingToolbarLayout_collapsedTitleGravityMode, 1);
        b3.z0(n5);
        b3.m0(n3);
        this.k = n4 = attributeSet.getDimensionPixelSize(z1.m.CollapsingToolbarLayout_expandedTitleMargin, 0);
        this.j = n4;
        this.i = n4;
        this.h = n4;
        n4 = z1.m.CollapsingToolbarLayout_expandedTitleMarginStart;
        if (attributeSet.hasValue(n4)) {
            this.h = attributeSet.getDimensionPixelSize(n4, 0);
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_expandedTitleMarginEnd)) {
            this.j = attributeSet.getDimensionPixelSize(n4, 0);
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_expandedTitleMarginTop)) {
            this.i = attributeSet.getDimensionPixelSize(n4, 0);
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_expandedTitleMarginBottom)) {
            this.k = attributeSet.getDimensionPixelSize(n4, 0);
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_expandedTitleSpacing)) {
            this.l = attributeSet.getDimensionPixelSize(n4, 0);
        }
        this.q = attributeSet.getBoolean(z1.m.CollapsingToolbarLayout_titleEnabled, true);
        this.setTitle(attributeSet.getText(z1.m.CollapsingToolbarLayout_title));
        b3.w0(z1.l.TextAppearance_Design_CollapsingToolbar_Expanded);
        b3.j0(c.i.TextAppearance_AppCompat_Widget_ActionBar_Title);
        n4 = z1.m.CollapsingToolbarLayout_expandedTitleTextAppearance;
        if (attributeSet.hasValue(n4)) {
            b3.w0(attributeSet.getResourceId(n4, 0));
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_collapsedTitleTextAppearance)) {
            b3.j0(attributeSet.getResourceId(n4, 0));
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_titleTextEllipsize)) {
            this.setTitleEllipsize(this.b(attributeSet.getInt(n4, -1)));
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_expandedTitleTextColor)) {
            b3.y0(s2.c.a(context, (TypedArray)attributeSet, n4));
        }
        if (attributeSet.hasValue(n4 = z1.m.CollapsingToolbarLayout_collapsedTitleTextColor)) {
            b3.l0(s2.c.a(context, (TypedArray)attributeSet, n4));
        }
        this.B = attributeSet.getDimensionPixelSize(z1.m.CollapsingToolbarLayout_scrimVisibleHeightTrigger, -1);
        n4 = z1.m.CollapsingToolbarLayout_titleMaxLines;
        if (attributeSet.hasValue(n4)) {
            b3.v0(attributeSet.getInt(n4, 1));
        } else {
            n4 = z1.m.CollapsingToolbarLayout_maxLines;
            if (attributeSet.hasValue(n4)) {
                b3.v0(attributeSet.getInt(n4, 1));
            }
        }
        n4 = z1.m.CollapsingToolbarLayout_titlePositionInterpolator;
        if (attributeSet.hasValue(n4)) {
            b3.K0((TimeInterpolator)AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(n4, 0)));
        }
        this.o = b3 = new b((View)this);
        b3.P0(timeInterpolator);
        b3.L0(false);
        int n6 = z1.m.CollapsingToolbarLayout_subtitle;
        if (attributeSet.hasValue(n6)) {
            this.setSubtitle(attributeSet.getText(n6));
        }
        b3.z0(n5);
        b3.m0(n3);
        b3.w0(c.i.TextAppearance_AppCompat_Headline);
        b3.j0(c.i.TextAppearance_AppCompat_Widget_ActionBar_Subtitle);
        n3 = z1.m.CollapsingToolbarLayout_expandedSubtitleTextAppearance;
        if (attributeSet.hasValue(n3)) {
            b3.w0(attributeSet.getResourceId(n3, 0));
        }
        if (attributeSet.hasValue(n3 = z1.m.CollapsingToolbarLayout_collapsedSubtitleTextAppearance)) {
            b3.j0(attributeSet.getResourceId(n3, 0));
        }
        if (attributeSet.hasValue(n3 = z1.m.CollapsingToolbarLayout_expandedSubtitleTextColor)) {
            b3.y0(s2.c.a(context, (TypedArray)attributeSet, n3));
        }
        if (attributeSet.hasValue(n3 = z1.m.CollapsingToolbarLayout_collapsedSubtitleTextColor)) {
            b3.l0(s2.c.a(context, (TypedArray)attributeSet, n3));
        }
        if (attributeSet.hasValue(n3 = z1.m.CollapsingToolbarLayout_subtitleMaxLines)) {
            b3.v0(attributeSet.getInt(n3, 1));
        }
        if (attributeSet.hasValue(n4)) {
            b3.K0((TimeInterpolator)AnimationUtils.loadInterpolator((Context)context, (int)attributeSet.getResourceId(n4, 0)));
        }
        this.y = attributeSet.getInt(z1.m.CollapsingToolbarLayout_scrimAnimationDuration, 600);
        n3 = z1.c.motionEasingStandardInterpolator;
        this.z = p2.k.g(context, n3, a2.a.c);
        this.A = p2.k.g(context, n3, a2.a.d);
        this.setContentScrim(attributeSet.getDrawable(z1.m.CollapsingToolbarLayout_contentScrim));
        this.setStatusBarScrim(attributeSet.getDrawable(z1.m.CollapsingToolbarLayout_statusBarScrim));
        this.setTitleCollapseMode(attributeSet.getInt(z1.m.CollapsingToolbarLayout_titleCollapseMode, 0));
        this.d = attributeSet.getResourceId(z1.m.CollapsingToolbarLayout_toolbarId, -1);
        this.I = attributeSet.getBoolean(z1.m.CollapsingToolbarLayout_forceApplySystemWindowInsetTop, false);
        this.L = attributeSet.getBoolean(z1.m.CollapsingToolbarLayout_extraMultilineHeightEnabled, false);
        attributeSet.recycle();
        this.setWillNotDraw(false);
        x0.r0((View)this, new f0(this){
            public final CollapsingToolbarLayout a;
            {
                this.a = collapsingToolbarLayout;
            }

            @Override
            public z1 a(View view, z1 z12) {
                return this.a.p(z12);
            }
        });
    }

    private int getDefaultContentScrimColorForTitleCollapseFadeMode() {
        ColorStateList colorStateList = h2.a.g(this.getContext(), z1.c.colorSurfaceContainer);
        if (colorStateList != null) {
            return colorStateList.getDefaultColor();
        }
        float f3 = this.getResources().getDimension(z1.e.design_appbar_elevation);
        return this.p.d(f3);
    }

    public static int h(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
        }
        return view.getMeasuredHeight();
    }

    public static CharSequence j(View view) {
        if (view instanceof Toolbar) {
            return ((Toolbar)view).getSubtitle();
        }
        if (view instanceof android.widget.Toolbar) {
            return ((android.widget.Toolbar)view).getSubtitle();
        }
        return null;
    }

    public static CharSequence k(View view) {
        if (view instanceof Toolbar) {
            return ((Toolbar)view).getTitle();
        }
        if (view instanceof android.widget.Toolbar) {
            return ((android.widget.Toolbar)view).getTitle();
        }
        return null;
    }

    public static com.google.android.material.appbar.c l(View view) {
        com.google.android.material.appbar.c c3;
        int n3 = z1.g.view_offset_helper;
        com.google.android.material.appbar.c c4 = c3 = (com.google.android.material.appbar.c)view.getTag(n3);
        if (c3 == null) {
            c4 = new com.google.android.material.appbar.c(view);
            view.setTag(n3, (Object)c4);
        }
        return c4;
    }

    public static boolean n(View view) {
        return view instanceof Toolbar || view instanceof android.widget.Toolbar;
        {
        }
    }

    public final void a(int n3) {
        this.d();
        ValueAnimator valueAnimator = this.x;
        if (valueAnimator == null) {
            ValueAnimator valueAnimator2;
            this.x = valueAnimator2 = new ValueAnimator();
            valueAnimator = n3 > this.v ? this.z : this.A;
            valueAnimator2.setInterpolator((TimeInterpolator)valueAnimator);
            this.x.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this){
                public final CollapsingToolbarLayout a;
                {
                    this.a = collapsingToolbarLayout;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.a.setScrimAlpha((Integer)valueAnimator.getAnimatedValue());
                }
            });
        } else if (valueAnimator.isRunning()) {
            this.x.cancel();
        }
        this.x.setDuration(this.y);
        this.x.setIntValues(new int[]{this.v, n3});
        this.x.start();
    }

    public final TextUtils.TruncateAt b(int n3) {
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 3) {
                    return TextUtils.TruncateAt.END;
                }
                return TextUtils.TruncateAt.MARQUEE;
            }
            return TextUtils.TruncateAt.MIDDLE;
        }
        return TextUtils.TruncateAt.START;
    }

    public final void c(AppBarLayout appBarLayout) {
        if (this.m()) {
            appBarLayout.setLiftOnScroll(false);
        }
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void d() {
        ViewGroup viewGroup;
        if (!this.c) {
            return;
        }
        Object var4_1 = null;
        this.e = null;
        this.f = null;
        int n3 = this.d;
        if (n3 != -1) {
            this.e = viewGroup = (ViewGroup)this.findViewById(n3);
            if (viewGroup != null) {
                this.f = this.e((View)viewGroup);
            }
        }
        if (this.e == null) {
            int n4 = this.getChildCount();
            n3 = 0;
            while (true) {
                viewGroup = var4_1;
                if (n3 >= n4 || CollapsingToolbarLayout.n((View)(viewGroup = this.getChildAt(n3)))) break;
                ++n3;
            }
            this.e = viewGroup;
        }
        this.u();
        this.c = false;
    }

    public void draw(Canvas canvas) {
        int n3;
        Object object;
        super.draw(canvas);
        this.d();
        if (this.e == null && (object = this.t) != null && this.v > 0) {
            object.mutate().setAlpha(this.v);
            this.t.draw(canvas);
        }
        if (this.q && this.r) {
            if (this.e != null && this.t != null && this.v > 0 && this.m() && this.n.H() < this.n.I()) {
                n3 = canvas.save();
                canvas.clipRect(this.t.getBounds(), Region.Op.DIFFERENCE);
                this.n.k(canvas);
                this.o.k(canvas);
                canvas.restoreToCount(n3);
            } else {
                this.n.k(canvas);
                this.o.k(canvas);
            }
        }
        if (this.u != null && this.v > 0 && (n3 = (object = this.G) != null ? ((z1)object).l() : 0) > 0) {
            this.u.setBounds(0, -this.D, this.getWidth(), n3 - this.D);
            this.u.mutate().setAlpha(this.v);
            this.u.draw(canvas);
        }
    }

    public boolean drawChild(Canvas canvas, View view, long l3) {
        boolean bl;
        if (this.t != null && this.v > 0 && this.o(view)) {
            this.t(this.t, view, this.getWidth(), this.getHeight());
            this.t.mutate().setAlpha(this.v);
            this.t.draw(canvas);
            bl = true;
        } else {
            bl = false;
        }
        return super.drawChild(canvas, view, l3) || bl;
        {
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        int[] nArray = this.getDrawableState();
        Object object = this.u;
        boolean bl = object != null && object.isStateful() ? object.setState(nArray) : false;
        object = this.t;
        boolean bl2 = bl;
        if (object != null) {
            bl2 = bl;
            if (object.isStateful()) {
                bl2 = bl | object.setState(nArray);
            }
        }
        object = this.n;
        bl = bl2;
        if (object != null) {
            bl = bl2 | ((b)object).M0(nArray);
        }
        if (bl) {
            this.invalidate();
        }
    }

    public final View e(View view) {
        ViewParent viewParent = view.getParent();
        View view2 = view;
        for (view = viewParent; view != this && view != null; view = view.getParent()) {
            if (!(view instanceof View)) continue;
            view2 = view;
        }
        return view2;
    }

    public LayoutParams f() {
        return new LayoutParams(-1, -1);
    }

    public FrameLayout.LayoutParams g(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public float getCollapsedSubtitleTextSize() {
        return this.o.u();
    }

    public Typeface getCollapsedSubtitleTypeface() {
        return this.o.v();
    }

    public int getCollapsedTitleGravity() {
        return this.n.q();
    }

    public float getCollapsedTitleTextSize() {
        return this.n.u();
    }

    public Typeface getCollapsedTitleTypeface() {
        return this.n.v();
    }

    public Drawable getContentScrim() {
        return this.t;
    }

    public float getExpandedSubtitleTextSize() {
        return this.o.F();
    }

    public Typeface getExpandedSubtitleTypeface() {
        return this.o.G();
    }

    public int getExpandedTitleGravity() {
        return this.n.C();
    }

    public int getExpandedTitleMarginBottom() {
        return this.k;
    }

    public int getExpandedTitleMarginEnd() {
        return this.j;
    }

    public int getExpandedTitleMarginStart() {
        return this.h;
    }

    public int getExpandedTitleMarginTop() {
        return this.i;
    }

    public int getExpandedTitleSpacing() {
        return this.l;
    }

    public float getExpandedTitleTextSize() {
        return this.n.F();
    }

    public Typeface getExpandedTitleTypeface() {
        return this.n.G();
    }

    public int getHyphenationFrequency() {
        return this.n.J();
    }

    public int getLineCount() {
        return this.n.K();
    }

    public float getLineSpacingAdd() {
        return this.n.L();
    }

    public float getLineSpacingMultiplier() {
        return this.n.M();
    }

    public int getMaxLines() {
        return this.n.A();
    }

    public int getScrimAlpha() {
        return this.v;
    }

    public long getScrimAnimationDuration() {
        return this.y;
    }

    public int getScrimVisibleHeightTrigger() {
        int n3 = this.B;
        if (n3 >= 0) {
            return n3 + this.H + this.J + this.K + this.M;
        }
        z1 z12 = this.G;
        n3 = z12 != null ? z12.l() : 0;
        int n4 = this.getMinimumHeight();
        if (n4 > 0) {
            return Math.min(n4 * 2 + n3, this.getHeight());
        }
        return this.getHeight() / 3;
    }

    public Drawable getStatusBarScrim() {
        return this.u;
    }

    public CharSequence getSubtitle() {
        if (this.q) {
            return this.o.P();
        }
        return null;
    }

    public CharSequence getTitle() {
        if (this.q) {
            return this.n.P();
        }
        return null;
    }

    public int getTitleCollapseMode() {
        return this.F;
    }

    public TimeInterpolator getTitlePositionInterpolator() {
        return this.n.O();
    }

    public TextUtils.TruncateAt getTitleTextEllipsize() {
        return this.n.S();
    }

    public final int i(View view) {
        com.google.android.material.appbar.c c3 = CollapsingToolbarLayout.l(view);
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        return this.getHeight() - c3.b() - view.getHeight() - layoutParams.bottomMargin;
    }

    public final boolean m() {
        return this.F == 1;
    }

    public final boolean o(View view) {
        View view2 = this.f;
        if (view2 != null && view2 != this) {
            return view == view2;
        }
        return view == this.e;
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.getParent();
        if (object instanceof AppBarLayout) {
            object = (AppBarLayout)object;
            this.c((AppBarLayout)object);
            this.setFitsSystemWindows(object.getFitsSystemWindows());
            if (this.C == null) {
                this.C = new c(this);
            }
            ((AppBarLayout)object).e(this.C);
            this.requestApplyInsets();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        Object object;
        super.onConfigurationChanged(configuration);
        this.n.Z(configuration);
        if (this.E != configuration.orientation && this.L && this.n.H() == 1.0f && (object = this.getParent()) instanceof AppBarLayout && ((AppBarLayout)(object = (AppBarLayout)object)).getPendingAction() == 0) {
            ((AppBarLayout)object).setPendingAction(2);
        }
        this.E = configuration.orientation;
    }

    public void onDetachedFromWindow() {
        ViewParent viewParent = this.getParent();
        AppBarLayout.f f3 = this.C;
        if (f3 != null && viewParent instanceof AppBarLayout) {
            ((AppBarLayout)viewParent).A(f3);
        }
        super.onDetachedFromWindow();
    }

    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        super.onLayout(bl, n3, n4, n5, n6);
        z1 z12 = this.G;
        int n9 = 0;
        if (z12 != null) {
            n8 = z12.l();
            int n10 = this.getChildCount();
            for (n7 = 0; n7 < n10; ++n7) {
                z12 = this.getChildAt(n7);
                if (z12.getFitsSystemWindows() || z12.getTop() >= n8) continue;
                x0.S((View)z12, n8);
            }
        }
        n8 = this.getChildCount();
        for (n7 = 0; n7 < n8; ++n7) {
            CollapsingToolbarLayout.l(this.getChildAt(n7)).d();
        }
        this.w(n3, n4, n5, n6, false);
        this.x();
        this.v();
        n4 = this.getChildCount();
        for (n3 = n9; n3 < n4; ++n3) {
            CollapsingToolbarLayout.l(this.getChildAt(n3)).a();
        }
    }

    public void onMeasure(int n3, int n4) {
        ViewGroup viewGroup;
        this.d();
        super.onMeasure(n3, n4);
        int n5 = View.MeasureSpec.getMode((int)n4);
        z1 z12 = this.G;
        n4 = z12 != null ? z12.l() : 0;
        if ((n5 == 0 || this.I) && n4 > 0) {
            this.H = n4;
            super.onMeasure(n3, View.MeasureSpec.makeMeasureSpec((int)(this.getMeasuredHeight() + n4), (int)0x40000000));
        }
        this.x();
        if (this.q && !TextUtils.isEmpty((CharSequence)this.n.P())) {
            int n6;
            int n7;
            n4 = this.getMeasuredHeight();
            n5 = this.getMeasuredWidth();
            this.w(0, 0, n5, n4, true);
            float f3 = this.H + this.i;
            float f4 = this.n.B();
            float f5 = TextUtils.isEmpty((CharSequence)this.o.P()) ? 0.0f : (float)this.l + this.o.B();
            n5 = (int)(f3 + f4 + f5 + (float)this.k);
            this.M = n5 > n4 ? n5 - n4 : 0;
            if (this.L) {
                if (this.n.A() > 1) {
                    n5 = this.n.z();
                    this.J = n5 > 1 ? Math.round(this.n.B()) * (n5 - 1) : 0;
                }
                if (this.o.A() > 1) {
                    n5 = this.o.z();
                    this.K = n5 > 1 ? Math.round(this.o.B()) * (n5 - 1) : 0;
                }
            }
            if ((n7 = this.M) + (n5 = this.J) + (n6 = this.K) > 0) {
                super.onMeasure(n3, View.MeasureSpec.makeMeasureSpec((int)(n4 + n7 + n5 + n6), (int)0x40000000));
            }
        }
        if ((viewGroup = this.e) != null) {
            z12 = this.f;
            if (z12 != null && z12 != this) {
                this.setMinimumHeight(CollapsingToolbarLayout.h((View)z12));
                return;
            }
            this.setMinimumHeight(CollapsingToolbarLayout.h((View)viewGroup));
        }
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        Drawable drawable = this.t;
        if (drawable != null) {
            this.s(drawable, n3, n4);
        }
    }

    public z1 p(z1 z12) {
        z1 z13 = this.getFitsSystemWindows() ? z12 : null;
        if (!n0.c.a(this.G, z13)) {
            this.G = z13;
            this.requestLayout();
        }
        return z12.c();
    }

    public final void q(boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        Object object = this.f;
        if (object == null) {
            object = this.e;
        }
        int n7 = this.i((View)object);
        com.google.android.material.internal.d.a((ViewGroup)this, this.g, this.m);
        object = this.e;
        if (object instanceof Toolbar) {
            object = (Toolbar)object;
            n6 = ((Toolbar)object).getTitleMarginStart();
            n5 = ((Toolbar)object).getTitleMarginEnd();
            n4 = ((Toolbar)object).getTitleMarginTop();
            n3 = ((Toolbar)object).getTitleMarginBottom();
        } else if (object instanceof android.widget.Toolbar) {
            object = (android.widget.Toolbar)object;
            n6 = object.getTitleMarginStart();
            n5 = object.getTitleMarginEnd();
            n4 = object.getTitleMarginTop();
            n3 = object.getTitleMarginBottom();
        } else {
            n6 = 0;
            n3 = 0;
            n4 = n5 = 0;
        }
        object = this.m;
        int n8 = ((Rect)object).left;
        int n9 = bl ? n5 : n6;
        int n10 = n8 + n9;
        n8 = ((Rect)object).right;
        n9 = bl ? n6 : n5;
        int n11 = n8 - n9;
        n4 = ((Rect)object).top + n7 + n4;
        n9 = ((Rect)object).bottom + n7 - n3;
        n7 = (int)((float)n9 - this.o.m());
        n8 = (int)((float)n4 + this.n.m());
        if (TextUtils.isEmpty((CharSequence)this.o.P())) {
            this.n.f0(n10, n4, n11, n9);
        } else {
            this.n.f0(n10, n4, n11, n7);
            this.o.f0(n10, n8, n11, n9);
        }
        if (this.s == 0) {
            com.google.android.material.internal.d.a((ViewGroup)this, (View)this, this.m);
            object = this.m;
            n10 = ((Rect)object).left;
            n3 = bl ? n5 : n6;
            n3 = n10 + n3;
            n10 = ((Rect)object).right;
            if (!bl) {
                n6 = n5;
            }
            n6 = n10 - n6;
            if (TextUtils.isEmpty((CharSequence)this.o.P())) {
                this.n.h0(n3, n4, n6, n9);
                return;
            }
            this.n.h0(n3, n4, n6, n7);
            this.o.h0(n3, n8, n6, n9);
        }
    }

    public final void r() {
        this.setContentDescription(this.getTitle());
    }

    public final void s(Drawable drawable, int n3, int n4) {
        this.t(drawable, (View)this.e, n3, n4);
    }

    public void setCollapsedSubtitleTextAppearance(int n3) {
        this.o.j0(n3);
    }

    public void setCollapsedSubtitleTextColor(int n3) {
        this.setCollapsedSubtitleTextColor(ColorStateList.valueOf((int)n3));
    }

    public void setCollapsedSubtitleTextColor(ColorStateList colorStateList) {
        this.o.l0(colorStateList);
    }

    public void setCollapsedSubtitleTextSize(float f3) {
        this.o.n0(f3);
    }

    public void setCollapsedSubtitleTypeface(Typeface typeface) {
        this.o.o0(typeface);
    }

    public void setCollapsedTitleGravity(int n3) {
        this.n.m0(n3);
        this.o.m0(n3);
    }

    public void setCollapsedTitleTextAppearance(int n3) {
        this.n.j0(n3);
    }

    public void setCollapsedTitleTextColor(int n3) {
        this.setCollapsedTitleTextColor(ColorStateList.valueOf((int)n3));
    }

    public void setCollapsedTitleTextColor(ColorStateList colorStateList) {
        this.n.l0(colorStateList);
    }

    public void setCollapsedTitleTextSize(float f3) {
        this.n.n0(f3);
    }

    public void setCollapsedTitleTypeface(Typeface typeface) {
        this.n.o0(typeface);
    }

    public void setContentScrim(Drawable drawable) {
        Drawable drawable2 = this.t;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.t = drawable3;
            if (drawable3 != null) {
                this.s(drawable3, this.getWidth(), this.getHeight());
                this.t.setCallback((Drawable.Callback)this);
                this.t.setAlpha(this.v);
            }
            this.postInvalidateOnAnimation();
        }
    }

    public void setContentScrimColor(int n3) {
        this.setContentScrim((Drawable)new ColorDrawable(n3));
    }

    public void setContentScrimResource(int n3) {
        this.setContentScrim(this.getContext().getDrawable(n3));
    }

    public void setExpandedSubtitleColor(int n3) {
        this.setExpandedSubtitleTextColor(ColorStateList.valueOf((int)n3));
    }

    public void setExpandedSubtitleTextAppearance(int n3) {
        this.o.w0(n3);
    }

    public void setExpandedSubtitleTextColor(ColorStateList colorStateList) {
        this.o.y0(colorStateList);
    }

    public void setExpandedSubtitleTextSize(float f3) {
        this.o.A0(f3);
    }

    public void setExpandedSubtitleTypeface(Typeface typeface) {
        this.o.B0(typeface);
    }

    public void setExpandedTitleColor(int n3) {
        this.setExpandedTitleTextColor(ColorStateList.valueOf((int)n3));
    }

    public void setExpandedTitleGravity(int n3) {
        this.n.z0(n3);
        this.o.z0(n3);
    }

    public void setExpandedTitleMargin(int n3, int n4, int n5, int n6) {
        this.h = n3;
        this.i = n4;
        this.j = n5;
        this.k = n6;
        this.requestLayout();
    }

    public void setExpandedTitleMarginBottom(int n3) {
        this.k = n3;
        this.requestLayout();
    }

    public void setExpandedTitleMarginEnd(int n3) {
        this.j = n3;
        this.requestLayout();
    }

    public void setExpandedTitleMarginStart(int n3) {
        this.h = n3;
        this.requestLayout();
    }

    public void setExpandedTitleMarginTop(int n3) {
        this.i = n3;
        this.requestLayout();
    }

    public void setExpandedTitleSpacing(int n3) {
        this.l = n3;
        this.requestLayout();
    }

    public void setExpandedTitleTextAppearance(int n3) {
        this.n.w0(n3);
    }

    public void setExpandedTitleTextColor(ColorStateList colorStateList) {
        this.n.y0(colorStateList);
    }

    public void setExpandedTitleTextSize(float f3) {
        this.n.A0(f3);
    }

    public void setExpandedTitleTypeface(Typeface typeface) {
        this.n.B0(typeface);
    }

    public void setExtraMultilineHeightEnabled(boolean bl) {
        this.L = bl;
    }

    public void setForceApplySystemWindowInsetTop(boolean bl) {
        this.I = bl;
    }

    public void setHyphenationFrequency(int n3) {
        this.n.G0(n3);
    }

    public void setLineSpacingAdd(float f3) {
        this.n.I0(f3);
    }

    public void setLineSpacingMultiplier(float f3) {
        this.n.J0(f3);
    }

    public void setMaxLines(int n3) {
        this.n.v0(n3);
        this.o.v0(n3);
    }

    public void setRtlTextDirectionHeuristicsEnabled(boolean bl) {
        this.n.L0(bl);
    }

    public void setScrimAlpha(int n3) {
        if (n3 != this.v) {
            ViewGroup viewGroup;
            if (this.t != null && (viewGroup = this.e) != null) {
                viewGroup.postInvalidateOnAnimation();
            }
            this.v = n3;
            this.postInvalidateOnAnimation();
        }
    }

    public void setScrimAnimationDuration(long l3) {
        this.y = l3;
    }

    public void setScrimVisibleHeightTrigger(int n3) {
        if (this.B != n3) {
            this.B = n3;
            this.v();
        }
    }

    public void setScrimsShown(boolean bl) {
        boolean bl2 = this.isLaidOut() && !this.isInEditMode();
        this.setScrimsShown(bl, bl2);
    }

    public void setScrimsShown(boolean bl, boolean bl2) {
        if (this.w != bl) {
            int n3 = 0;
            int n4 = 0;
            if (bl2) {
                if (bl) {
                    n4 = 255;
                }
                this.a(n4);
            } else {
                n4 = n3;
                if (bl) {
                    n4 = 255;
                }
                this.setScrimAlpha(n4);
            }
            this.w = bl;
        }
    }

    public void setStaticLayoutBuilderConfigurer(d d3) {
        this.n.N0(d3);
    }

    public void setStatusBarScrim(Drawable drawable) {
        Drawable drawable2 = this.u;
        if (drawable2 != drawable) {
            Drawable drawable3 = null;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable3 = drawable.mutate();
            }
            this.u = drawable3;
            if (drawable3 != null) {
                if (drawable3.isStateful()) {
                    this.u.setState(this.getDrawableState());
                }
                h0.a.m(this.u, this.getLayoutDirection());
                drawable = this.u;
                boolean bl = this.getVisibility() == 0;
                drawable.setVisible(bl, false);
                this.u.setCallback((Drawable.Callback)this);
                this.u.setAlpha(this.v);
            }
            this.postInvalidateOnAnimation();
        }
    }

    public void setStatusBarScrimColor(int n3) {
        this.setStatusBarScrim((Drawable)new ColorDrawable(n3));
    }

    public void setStatusBarScrimResource(int n3) {
        this.setStatusBarScrim(this.getContext().getDrawable(n3));
    }

    public void setSubtitle(CharSequence charSequence) {
        this.o.O0(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        this.n.O0(charSequence);
        this.r();
    }

    public void setTitleCollapseMode(int n3) {
        this.F = n3;
        boolean bl = this.m();
        this.n.E0(bl);
        this.o.E0(bl);
        ViewParent viewParent = this.getParent();
        if (viewParent instanceof AppBarLayout) {
            this.c((AppBarLayout)viewParent);
        }
        if (bl && this.t == null) {
            this.setContentScrimColor(this.getDefaultContentScrimColorForTitleCollapseFadeMode());
        }
    }

    public void setTitleEllipsize(TextUtils.TruncateAt truncateAt) {
        this.n.Q0(truncateAt);
    }

    public void setTitleEnabled(boolean bl) {
        if (bl != this.q) {
            this.q = bl;
            this.r();
            this.u();
            this.requestLayout();
        }
    }

    public void setTitlePositionInterpolator(TimeInterpolator timeInterpolator) {
        this.n.K0(timeInterpolator);
    }

    public void setVisibility(int n3) {
        super.setVisibility(n3);
        boolean bl = n3 == 0;
        Drawable drawable = this.u;
        if (drawable != null && drawable.isVisible() != bl) {
            this.u.setVisible(bl, false);
        }
        if ((drawable = this.t) != null && drawable.isVisible() != bl) {
            this.t.setVisible(bl, false);
        }
    }

    public final void t(Drawable drawable, View view, int n3, int n4) {
        int n5 = n4;
        if (this.m()) {
            n5 = n4;
            if (view != null) {
                n5 = n4;
                if (this.q) {
                    n5 = view.getBottom();
                }
            }
        }
        drawable.setBounds(0, 0, n3, n5);
    }

    public final void u() {
        View view;
        if (!this.q && (view = this.g) != null && (view = view.getParent()) instanceof ViewGroup) {
            ((ViewGroup)view).removeView(this.g);
        }
        if (this.q && this.e != null) {
            if (this.g == null) {
                this.g = new View(this.getContext());
            }
            if (this.g.getParent() == null) {
                this.e.addView(this.g, -1, -1);
            }
        }
    }

    public final void v() {
        if (this.t == null && this.u == null) {
            return;
        }
        boolean bl = this.getHeight() + this.D < this.getScrimVisibleHeightTrigger();
        this.setScrimsShown(bl);
    }

    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.t || drawable == this.u;
        {
        }
    }

    public final void w(int n3, int n4, int n5, int n6, boolean bl) {
        View view;
        if (this.q && (view = this.g) != null) {
            boolean bl2 = view.isAttachedToWindow();
            boolean bl3 = false;
            bl2 = bl2 && this.g.getVisibility() == 0;
            this.r = bl2;
            if (bl2 || bl) {
                bl2 = bl3;
                if (this.getLayoutDirection() == 1) {
                    bl2 = true;
                }
                this.q(bl2);
                int n7 = bl2 ? this.j : this.h;
                int n8 = this.m.top + this.i;
                int n9 = bl2 ? this.h : this.j;
                n3 = n5 - n3 - n9;
                n4 = n6 - n4 - this.k;
                if (TextUtils.isEmpty((CharSequence)this.o.P())) {
                    this.n.r0(n7, n8, n3, n4);
                    this.n.c0(bl);
                    return;
                }
                this.n.s0(n7, n8, n3, (int)((float)n4 - (this.o.B() + (float)this.K) - (float)this.l), false);
                this.o.s0(n7, (int)((float)n8 + (this.n.B() + (float)this.J) + (float)this.l), n3, n4, false);
                this.n.c0(bl);
                this.o.c0(bl);
            }
        }
    }

    public final void x() {
        Object object = this.e;
        if (object != null && this.q) {
            object = CollapsingToolbarLayout.k((View)object);
            if (TextUtils.isEmpty((CharSequence)this.n.P()) && !TextUtils.isEmpty((CharSequence)object)) {
                this.setTitle((CharSequence)object);
            }
            object = CollapsingToolbarLayout.j((View)this.e);
            if (TextUtils.isEmpty((CharSequence)this.o.P()) && !TextUtils.isEmpty((CharSequence)object)) {
                this.setSubtitle((CharSequence)object);
            }
        }
    }

    public static class LayoutParams
    extends FrameLayout.LayoutParams {
        public int a = 0;
        public float b = 0.5f;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, z1.m.CollapsingToolbarLayout_Layout);
            this.a = context.getInt(z1.m.CollapsingToolbarLayout_Layout_layout_collapseMode, 0);
            this.a(context.getFloat(z1.m.CollapsingToolbarLayout_Layout_layout_collapseParallaxMultiplier, 0.5f));
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public void a(float f3) {
            this.b = f3;
        }
    }

    public class c
    implements AppBarLayout.f {
        public final CollapsingToolbarLayout a;

        public c(CollapsingToolbarLayout collapsingToolbarLayout) {
            this.a = collapsingToolbarLayout;
        }

        @Override
        public void a(AppBarLayout object, int n3) {
            int n4;
            int n5;
            object = this.a;
            ((CollapsingToolbarLayout)((Object)object)).D = n3;
            object = ((CollapsingToolbarLayout)((Object)object)).G;
            int n6 = object != null ? ((z1)object).l() : 0;
            int n7 = this.a.getChildCount();
            for (n5 = 0; n5 < n7; ++n5) {
                View view = this.a.getChildAt(n5);
                object = (LayoutParams)view.getLayoutParams();
                com.google.android.material.appbar.c c3 = CollapsingToolbarLayout.l(view);
                n4 = ((LayoutParams)((Object)object)).a;
                if (n4 != 1) {
                    if (n4 != 2) continue;
                    c3.f(Math.round((float)(-n3) * ((LayoutParams)((Object)object)).b));
                    continue;
                }
                c3.f(j0.a.b(-n3, 0, this.a.i(view)));
            }
            this.a.v();
            object = this.a;
            if (((CollapsingToolbarLayout)((Object)object)).u != null && n6 > 0) {
                object.postInvalidateOnAnimation();
            }
            n5 = this.a.getHeight();
            n6 = n5 - this.a.getMinimumHeight() - n6;
            n7 = this.a.getScrimVisibleHeightTrigger();
            n4 = this.a.D + n6;
            float f3 = Math.abs(n3);
            float f4 = n6;
            f3 /= f4;
            object = this.a.n;
            f4 = (float)(n5 - n7) / f4;
            ((b)object).F0(Math.min(1.0f, f4));
            this.a.n.q0(n4);
            this.a.n.D0(f3);
            this.a.o.F0(Math.min(1.0f, f4));
            this.a.o.q0(n4);
            this.a.o.D0(f3);
        }
    }

    public static interface d
    extends v {
    }
}

