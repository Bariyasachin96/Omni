/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.TextView
 */
package com.google.android.material.navigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.r0;
import androidx.core.widget.j;
import com.google.android.material.badge.b;
import com.google.android.material.internal.BaselineLayout;
import com.google.android.material.navigation.g;
import p0.s;
import z1.e;
import z1.f;
import z1.h;
import z1.k;

public abstract class NavigationBarItemView
extends FrameLayout
implements g {
    public static final int[] l0 = new int[]{0x10100A0};
    public static final c m0 = new c(null);
    public static final c n0 = new d(null);
    public TextView A;
    public TextView B;
    public BaselineLayout C;
    public int D = -1;
    public int E = 0;
    public int F = 0;
    public int G = 0;
    public int H = 0;
    public ColorStateList I;
    public boolean J = false;
    public androidx.appcompat.view.menu.g K;
    public ColorStateList L;
    public Drawable M;
    public Drawable N;
    public ValueAnimator O;
    public c P = m0;
    public float Q = 0.0f;
    public boolean R = false;
    public int S = 0;
    public int T = 0;
    public int U = -2;
    public int V = 0;
    public boolean W = false;
    public int a0 = 0;
    public int b0 = 0;
    public boolean c = false;
    public com.google.android.material.badge.a c0;
    public ColorStateList d;
    public int d0;
    public Drawable e;
    public int e0 = 0;
    public int f;
    public int f0 = 49;
    public int g;
    public boolean g0 = false;
    public int h;
    public boolean h0 = false;
    public int i;
    public boolean i0 = false;
    public float j;
    public boolean j0 = false;
    public float k;
    public Rect k0 = new Rect();
    public float l;
    public float m;
    public float n;
    public float o;
    public int p;
    public boolean q;
    public final LinearLayout r;
    public final LinearLayout s;
    public final View t;
    public final FrameLayout u;
    public final ImageView v;
    public final BaselineLayout w;
    public final TextView x;
    public final TextView y;
    public BaselineLayout z;

    public NavigationBarItemView(Context context) {
        super(context);
        TextView textView;
        TextView textView2;
        BaselineLayout baselineLayout;
        LayoutInflater.from((Context)context).inflate(this.getItemLayoutResId(), (ViewGroup)this, true);
        this.r = (LinearLayout)this.findViewById(z1.g.navigation_bar_item_content_container);
        context = (LinearLayout)this.findViewById(z1.g.navigation_bar_item_inner_content_container);
        this.s = context;
        this.t = this.findViewById(z1.g.navigation_bar_item_active_indicator_view);
        this.u = (FrameLayout)this.findViewById(z1.g.navigation_bar_item_icon_container);
        this.v = (ImageView)this.findViewById(z1.g.navigation_bar_item_icon_view);
        this.w = baselineLayout = (BaselineLayout)this.findViewById(z1.g.navigation_bar_item_labels_group);
        this.x = textView2 = (TextView)this.findViewById(z1.g.navigation_bar_item_small_label_view);
        this.y = textView = (TextView)this.findViewById(z1.g.navigation_bar_item_large_label_view);
        this.j();
        this.C = baselineLayout;
        this.setBackgroundResource(this.getItemBackgroundResId());
        this.f = this.getResources().getDimensionPixelSize(this.getItemDefaultMarginResId());
        this.g = baselineLayout.getPaddingBottom();
        this.h = 0;
        this.i = 0;
        textView2.setImportantForAccessibility(2);
        textView.setImportantForAccessibility(2);
        this.A.setImportantForAccessibility(2);
        this.B.setImportantForAccessibility(2);
        this.setFocusable(true);
        this.f();
        this.V = this.getResources().getDimensionPixelSize(z1.e.m3_navigation_item_expanded_active_indicator_height_default);
        context.addOnLayoutChangeListener((View.OnLayoutChangeListener)new com.google.android.material.navigation.d(this));
    }

    public static void G(View view, int n3) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), n3);
    }

    private void H() {
        androidx.appcompat.view.menu.g g3 = this.K;
        if (g3 != null) {
            int n3 = g3.isVisible() && (this.g0 || !this.h0) ? 0 : 8;
            this.setVisibility(n3);
        }
    }

    public static /* synthetic */ void a(NavigationBarItemView navigationBarItemView, View view, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        if (navigationBarItemView.v.getVisibility() == 0) {
            navigationBarItemView.z((View)navigationBarItemView.v);
        }
        view = (FrameLayout.LayoutParams)navigationBarItemView.s.getLayoutParams();
        n3 = n5 - n3 + view.rightMargin + view.leftMargin;
        n5 = n6 - n4 + view.topMargin + view.bottomMargin;
        n6 = navigationBarItemView.d0;
        n4 = 1;
        if (n6 == 1 && navigationBarItemView.U == -2) {
            view = (FrameLayout.LayoutParams)navigationBarItemView.t.getLayoutParams();
            if (navigationBarItemView.U == -2 && navigationBarItemView.t.getMeasuredWidth() != n3) {
                view.width = Math.max(n3, Math.min(navigationBarItemView.S, navigationBarItemView.getMeasuredWidth() - navigationBarItemView.a0 * 2));
                n3 = 1;
            } else {
                n3 = 0;
            }
            if (navigationBarItemView.t.getMeasuredHeight() < n5) {
                view.height = n5;
                n3 = n4;
            }
            if (n3 != 0) {
                navigationBarItemView.t.setLayoutParams((ViewGroup.LayoutParams)view);
            }
        }
    }

    private int getItemVisiblePosition() {
        ViewGroup viewGroup = (ViewGroup)this.getParent();
        int n3 = viewGroup.indexOfChild((View)this);
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = viewGroup.getChildAt(i3);
            int n5 = n4;
            if (view instanceof NavigationBarItemView) {
                n5 = n4;
                if (view.getVisibility() == 0) {
                    n5 = n4 + 1;
                }
            }
            n4 = n5;
        }
        return n4;
    }

    private int getSuggestedIconWidth() {
        com.google.android.material.badge.a a4 = this.c0;
        int n3 = a4 == null ? 0 : a4.getMinimumWidth() - this.c0.m();
        a4 = (LinearLayout.LayoutParams)this.u.getLayoutParams();
        return Math.max(n3, ((LinearLayout.LayoutParams)a4).leftMargin) + this.v.getMeasuredWidth() + Math.max(n3, ((LinearLayout.LayoutParams)a4).rightMargin);
    }

    public static Drawable h(ColorStateList colorStateList) {
        return new RippleDrawable(t2.a.a(colorStateList), null, null);
    }

    private void setLabelPivots(TextView textView) {
        textView.setPivotX((float)(textView.getWidth() / 2));
        textView.setPivotY((float)textView.getBaseline());
    }

    public static void u(TextView textView, int n3) {
        androidx.core.widget.j.m(textView, n3);
        n3 = s2.c.j(textView.getContext(), n3, 0);
        if (n3 != 0) {
            textView.setTextSize(0, (float)n3);
        }
    }

    public static void v(View view, int n3, int n4, int n5) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        layoutParams.topMargin = n3;
        layoutParams.bottomMargin = n4;
        layoutParams.gravity = n5;
        view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public static void w(View view, float f3, float f4, int n3) {
        view.setScaleX(f3);
        view.setScaleY(f4);
        view.setVisibility(n3);
    }

    public void A(int n3) {
        if (n3 <= 0 && this.getVisibility() == 0) {
            return;
        }
        int n4 = Math.min(this.S, n3 - this.a0 * 2);
        int n5 = this.T;
        if (this.d0 == 1) {
            n3 -= this.b0 * 2;
            n4 = this.U;
            if (n4 != -1) {
                n3 = n4 == -2 ? this.r.getMeasuredWidth() : Math.min(n4, n3);
            }
            n5 = Math.max(this.V, this.s.getMeasuredHeight());
            n4 = n3;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.t.getLayoutParams();
        if (this.k()) {
            n5 = n4;
        }
        layoutParams.height = n5;
        layoutParams.width = Math.max(0, n4);
        this.t.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    public final void B() {
        if (this.k()) {
            this.P = n0;
            return;
        }
        this.P = m0;
    }

    public final void C() {
        TextView textView = this.y;
        textView.setTypeface(textView.getTypeface(), this.J ? 1 : 0);
        textView = this.B;
        textView.setTypeface(textView.getTypeface(), this.J ? 1 : 0);
    }

    public final void D(TextView textView, int n3) {
        if (textView == null) {
            return;
        }
        this.t(textView, n3);
        this.f();
        textView.setMinimumHeight(s2.c.i(textView.getContext(), n3, 0));
        ColorStateList colorStateList = this.I;
        if (colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
        this.C();
    }

    public final void E(TextView textView, int n3) {
        if (textView != null) {
            this.t(textView, n3);
            this.f();
            textView.setMinimumHeight(s2.c.i(textView.getContext(), n3, 0));
            ColorStateList colorStateList = this.I;
            if (colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
        }
    }

    public final void F() {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        Rect rect;
        this.e0 = 0;
        this.C = this.w;
        int n9 = this.d0;
        int n10 = 8;
        if (n9 == 1) {
            if (this.z.getParent() == null) {
                this.e();
            }
            rect = this.k0;
            n8 = rect.left;
            n7 = rect.right;
            n9 = rect.top;
            n6 = rect.bottom;
            this.e0 = 1;
            n5 = this.b0;
            this.C = this.z;
            n4 = 0;
            n3 = n9;
        } else {
            int n11 = 0;
            n5 = n7 = (n9 = (n6 = 0));
            n4 = 8;
            n10 = n5;
            n3 = n5;
            n8 = n9;
            n5 = n6;
            n6 = n11;
        }
        this.w.setVisibility(n10);
        this.z.setVisibility(n4);
        ((FrameLayout.LayoutParams)this.r.getLayoutParams()).gravity = this.f0;
        rect = (FrameLayout.LayoutParams)this.s.getLayoutParams();
        rect.leftMargin = n8;
        rect.rightMargin = n7;
        rect.topMargin = n3;
        rect.bottomMargin = n6;
        this.setPadding(n5, 0, n5, 0);
        this.A(this.getWidth());
    }

    @Override
    public boolean c() {
        return false;
    }

    @Override
    public void d(androidx.appcompat.view.menu.g object, int n3) {
        this.K = object;
        this.setCheckable(((androidx.appcompat.view.menu.g)object).isCheckable());
        this.setChecked(((androidx.appcompat.view.menu.g)object).isChecked());
        this.setEnabled(((androidx.appcompat.view.menu.g)object).isEnabled());
        this.setIcon(((androidx.appcompat.view.menu.g)object).getIcon());
        this.setTitle(((androidx.appcompat.view.menu.g)object).getTitle());
        this.setId(((androidx.appcompat.view.menu.g)object).getItemId());
        if (!TextUtils.isEmpty((CharSequence)((androidx.appcompat.view.menu.g)object).getContentDescription())) {
            this.setContentDescription(((androidx.appcompat.view.menu.g)object).getContentDescription());
        }
        object = !TextUtils.isEmpty((CharSequence)((androidx.appcompat.view.menu.g)object).getTooltipText()) ? ((androidx.appcompat.view.menu.g)object).getTooltipText() : ((androidx.appcompat.view.menu.g)object).getTitle();
        r0.a((View)this, (CharSequence)object);
        this.H();
        this.c = true;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.R) {
            this.u.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void e() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        this.s.addView((View)this.z, (ViewGroup.LayoutParams)layoutParams);
        this.q();
    }

    public final void f() {
        float f3 = this.x.getTextSize();
        float f4 = this.y.getTextSize();
        this.j = f3 - f4;
        this.k = f4 * 1.0f / f3;
        this.l = f3 * 1.0f / f4;
        f4 = this.A.getTextSize();
        f3 = this.B.getTextSize();
        this.m = f4 - f3;
        this.n = f3 * 1.0f / f4;
        this.o = f4 * 1.0f / f3;
    }

    public void g() {
        this.o();
        this.K = null;
        this.Q = 0.0f;
        this.c = false;
    }

    public Drawable getActiveIndicatorDrawable() {
        return this.t.getBackground();
    }

    public com.google.android.material.badge.a getBadge() {
        return this.c0;
    }

    public BaselineLayout getExpandedLabelGroup() {
        return this.z;
    }

    public int getItemBackgroundResId() {
        return z1.f.mtrl_navigation_bar_item_background;
    }

    @Override
    public androidx.appcompat.view.menu.g getItemData() {
        return this.K;
    }

    public int getItemDefaultMarginResId() {
        return z1.e.mtrl_navigation_bar_item_default_margin;
    }

    public abstract int getItemLayoutResId();

    public int getItemPosition() {
        return this.D;
    }

    public BaselineLayout getLabelGroup() {
        return this.w;
    }

    public int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.r.getLayoutParams();
        return this.r.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    public int getSuggestedMinimumWidth() {
        if (this.d0 == 1) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)this.s.getLayoutParams();
            return this.s.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.w.getLayoutParams();
        int n3 = layoutParams.leftMargin;
        int n4 = this.w.getMeasuredWidth();
        int n5 = layoutParams.rightMargin;
        return Math.max(this.getSuggestedIconWidth(), n3 + n4 + n5);
    }

    public final boolean i() {
        return this.c0 != null;
    }

    public final void j() {
        BaselineLayout baselineLayout;
        float f3 = this.getResources().getDimension(z1.e.default_navigation_text_size);
        float f4 = this.getResources().getDimension(z1.e.default_navigation_active_text_size);
        this.z = baselineLayout = new BaselineLayout(this.getContext());
        baselineLayout.setVisibility(8);
        this.z.setDuplicateParentStateEnabled(true);
        this.z.setMeasurePaddingFromBaseline(this.i0);
        baselineLayout = new TextView(this.getContext());
        this.A = baselineLayout;
        baselineLayout.setMaxLines(1);
        TextView textView = this.A;
        baselineLayout = TextUtils.TruncateAt.END;
        textView.setEllipsize((TextUtils.TruncateAt)baselineLayout);
        this.A.setDuplicateParentStateEnabled(true);
        this.A.setIncludeFontPadding(false);
        this.A.setGravity(16);
        this.A.setTextSize(f3);
        this.B = textView = new TextView(this.getContext());
        textView.setMaxLines(1);
        this.B.setEllipsize((TextUtils.TruncateAt)baselineLayout);
        this.B.setDuplicateParentStateEnabled(true);
        this.B.setVisibility(4);
        this.B.setIncludeFontPadding(false);
        this.B.setGravity(16);
        this.B.setTextSize(f4);
        this.z.addView((View)this.A);
        this.z.addView((View)this.B);
    }

    public final boolean k() {
        return this.W && this.p == 2;
    }

    public final void l(float f3) {
        if (this.R && this.c && this.isAttachedToWindow()) {
            ValueAnimator valueAnimator = this.O;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.O = null;
            }
            this.O = valueAnimator = ValueAnimator.ofFloat((float[])new float[]{this.Q, f3});
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this, f3){
                public final float a;
                public final NavigationBarItemView b;
                {
                    this.b = navigationBarItemView;
                    this.a = f3;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float f3 = ((Float)valueAnimator.getAnimatedValue()).floatValue();
                    this.b.p(f3, this.a);
                }
            });
            this.O.setInterpolator(p2.k.g(this.getContext(), z1.c.motionEasingEmphasizedInterpolator, a2.a.b));
            this.O.setDuration((long)p2.k.f(this.getContext(), z1.c.motionDurationLong2, this.getResources().getInteger(z1.h.material_motion_duration_long_1)));
            this.O.start();
            return;
        }
        this.p(f3, f3);
    }

    public final void m() {
        androidx.appcompat.view.menu.g g3 = this.K;
        if (g3 != null) {
            this.setChecked(g3.isChecked());
        }
    }

    public final void n() {
        Drawable drawable = this.e;
        ColorStateList colorStateList = this.d;
        Drawable drawable2 = null;
        boolean bl = true;
        Drawable drawable3 = drawable;
        Drawable drawable4 = drawable2;
        boolean bl2 = bl;
        if (colorStateList != null) {
            drawable4 = this.getActiveIndicatorDrawable();
            if (this.R && this.getActiveIndicatorDrawable() != null && drawable4 != null) {
                drawable4 = new RippleDrawable(t2.a.d(this.d), null, drawable4);
                bl2 = false;
                drawable3 = drawable;
            } else {
                drawable3 = drawable;
                drawable4 = drawable2;
                bl2 = bl;
                if (drawable == null) {
                    drawable3 = NavigationBarItemView.h(this.d);
                    bl2 = bl;
                    drawable4 = drawable2;
                }
            }
        }
        this.u.setPadding(0, 0, 0, 0);
        this.u.setForeground(drawable4);
        this.setBackground(drawable3);
        this.setDefaultFocusHighlightEnabled(bl2);
    }

    public void o() {
        this.y((View)this.v);
    }

    public int[] onCreateDrawableState(int n3) {
        int[] nArray = super.onCreateDrawableState(n3 + 1);
        androidx.appcompat.view.menu.g g3 = this.K;
        if (g3 != null && g3.isCheckable() && this.K.isChecked()) {
            View.mergeDrawableStates((int[])nArray, (int[])l0);
        }
        return nArray;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo object) {
        super.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        Object object2 = this.c0;
        if (object2 != null && object2.isVisible()) {
            object2 = this.K.getTitle();
            if (!TextUtils.isEmpty((CharSequence)this.K.getContentDescription())) {
                object2 = this.K.getContentDescription();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(object2);
            stringBuilder.append(", ");
            stringBuilder.append((Object)this.c0.j());
            object.setContentDescription((CharSequence)stringBuilder.toString());
        }
        object = p0.s.L0((AccessibilityNodeInfo)object);
        ((s)object).k0(s.f.a(0, 1, this.getItemVisiblePosition(), 1, false, this.isSelected()));
        if (this.isSelected()) {
            ((s)object).i0(false);
            ((s)object).Z(s.a.i);
        }
        ((s)object).z0(this.getResources().getString(z1.k.item_view_role_description));
    }

    public void onSizeChanged(int n3, int n4, int n5, int n6) {
        super.onSizeChanged(n3, n4, n5, n6);
        this.post(new Runnable(this, n3){
            public final int c;
            public final NavigationBarItemView d;
            {
                this.d = navigationBarItemView;
                this.c = n3;
            }

            @Override
            public void run() {
                this.d.A(this.c);
            }
        });
    }

    public final void p(float f3, float f4) {
        this.P.d(f3, f4, this.t);
        this.Q = f3;
    }

    public final void q() {
        int n3 = this.v.getLayoutParams().width;
        int n4 = 0;
        n3 = n3 > 0 ? this.i : 0;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.z.getLayoutParams();
        if (layoutParams != null) {
            int n5 = this.getLayoutDirection() == 1 ? n3 : 0;
            layoutParams.rightMargin = n5;
            if (this.getLayoutDirection() == 1) {
                n3 = n4;
            }
            layoutParams.leftMargin = n3;
        }
    }

    public final void r(View view, View view2, float f3, float f4) {
        LinearLayout linearLayout = this.r;
        int n3 = this.d0 == 0 ? (int)((float)this.f + f4) : 0;
        NavigationBarItemView.v((View)linearLayout, n3, 0, this.f0);
        linearLayout = this.s;
        int n4 = this.d0;
        n3 = n4 == 0 ? 0 : this.k0.top;
        int n5 = n4 == 0 ? 0 : this.k0.bottom;
        n4 = n4 == 0 ? 17 : 8388627;
        NavigationBarItemView.v((View)linearLayout, n3, n5, n4);
        NavigationBarItemView.G((View)this.w, this.g);
        this.C.setVisibility(0);
        NavigationBarItemView.w(view, 1.0f, 1.0f, 0);
        NavigationBarItemView.w(view2, f3, f3, 4);
    }

    public final void s() {
        LinearLayout linearLayout = this.r;
        int n3 = this.f;
        int n4 = this.d0 == 0 ? 17 : this.f0;
        NavigationBarItemView.v((View)linearLayout, n3, n3, n4);
        NavigationBarItemView.v((View)this.s, 0, 0, 17);
        NavigationBarItemView.G((View)this.w, 0);
        this.C.setVisibility(8);
    }

    public void setActiveIndicatorDrawable(Drawable drawable) {
        this.t.setBackground(drawable);
        this.n();
    }

    public void setActiveIndicatorEnabled(boolean bl) {
        this.R = bl;
        this.n();
        View view = this.t;
        int n3 = bl ? 0 : 8;
        view.setVisibility(n3);
        this.requestLayout();
    }

    public void setActiveIndicatorExpandedHeight(int n3) {
        this.V = n3;
        this.A(this.getWidth());
    }

    public void setActiveIndicatorExpandedMarginHorizontal(int n3) {
        this.b0 = n3;
        if (this.d0 == 1) {
            this.setPadding(n3, 0, n3, 0);
        }
        this.A(this.getWidth());
    }

    public void setActiveIndicatorExpandedPadding(Rect rect) {
        this.k0 = rect;
    }

    public void setActiveIndicatorExpandedWidth(int n3) {
        this.U = n3;
        this.A(this.getWidth());
    }

    public void setActiveIndicatorHeight(int n3) {
        this.T = n3;
        this.A(this.getWidth());
    }

    public void setActiveIndicatorLabelPadding(int n3) {
        if (this.h != n3) {
            this.h = n3;
            ((LinearLayout.LayoutParams)this.w.getLayoutParams()).topMargin = n3;
            if (this.z.getLayoutParams() != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.z.getLayoutParams();
                int n4 = this.getLayoutDirection() == 1 ? n3 : 0;
                layoutParams.rightMargin = n4;
                if (this.getLayoutDirection() == 1) {
                    n3 = 0;
                }
                layoutParams.leftMargin = n3;
                this.requestLayout();
            }
        }
    }

    public void setActiveIndicatorMarginHorizontal(int n3) {
        this.a0 = n3;
        this.A(this.getWidth());
    }

    public void setActiveIndicatorResizeable(boolean bl) {
        this.W = bl;
    }

    public void setActiveIndicatorWidth(int n3) {
        this.S = n3;
        this.A(this.getWidth());
    }

    public void setBadge(com.google.android.material.badge.a a4) {
        if (this.c0 != a4) {
            if (this.i() && this.v != null) {
                Log.w((String)"NavigationBar", (String)"Multiple badges shouldn't be attached to one item.");
                this.y((View)this.v);
            }
            this.c0 = a4;
            a4.O(this.e0);
            a4 = this.v;
            if (a4 != null) {
                this.x((View)a4);
            }
        }
    }

    public void setCheckable(boolean bl) {
        this.refreshDrawableState();
    }

    public void setChecked(boolean bl) {
        int n3;
        this.setLabelPivots(this.y);
        this.setLabelPivots(this.x);
        this.setLabelPivots(this.B);
        this.setLabelPivots(this.A);
        float f3 = bl ? 1.0f : 0.0f;
        this.l(f3);
        TextView textView = this.y;
        TextView textView2 = this.x;
        float f4 = this.j;
        float f5 = this.k;
        f3 = this.l;
        if (this.d0 == 1) {
            textView = this.B;
            textView2 = this.A;
            f4 = this.m;
            f5 = this.n;
            f3 = this.o;
        }
        if ((n3 = this.p) != -1) {
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 == 2) {
                        this.s();
                    }
                } else if (bl) {
                    this.r((View)textView, (View)textView2, f5, f4);
                } else {
                    this.r((View)textView2, (View)textView, f3, 0.0f);
                }
            } else if (bl) {
                this.r((View)textView, (View)textView2, f5, 0.0f);
            } else {
                this.s();
            }
        } else if (this.q) {
            if (bl) {
                this.r((View)textView, (View)textView2, f5, 0.0f);
            } else {
                this.s();
            }
        } else if (bl) {
            this.r((View)textView, (View)textView2, f5, f4);
        } else {
            this.r((View)textView2, (View)textView, f3, 0.0f);
        }
        this.refreshDrawableState();
        this.setSelected(bl);
    }

    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.x.setEnabled(bl);
        this.y.setEnabled(bl);
        this.A.setEnabled(bl);
        this.B.setEnabled(bl);
        this.v.setEnabled(bl);
    }

    @Override
    public void setExpanded(boolean bl) {
        this.g0 = bl;
        this.H();
    }

    public void setHorizontalTextAppearanceActive(int n3) {
        this.G = n3;
        TextView textView = this.B;
        if (n3 == 0) {
            n3 = this.E;
        }
        this.D(textView, n3);
    }

    public void setHorizontalTextAppearanceInactive(int n3) {
        this.H = n3;
        TextView textView = this.A;
        if (n3 == 0) {
            n3 = this.F;
        }
        this.E(textView, n3);
    }

    public void setIcon(Drawable drawable) {
        if (drawable == this.M) {
            return;
        }
        this.M = drawable;
        Drawable drawable2 = drawable;
        if (drawable != null) {
            drawable2 = drawable.getConstantState();
            if (drawable2 != null) {
                drawable = drawable2.newDrawable();
            }
            this.N = drawable = h0.a.r(drawable).mutate();
            ColorStateList colorStateList = this.L;
            drawable2 = drawable;
            if (colorStateList != null) {
                drawable.setTintList(colorStateList);
                drawable2 = drawable;
            }
        }
        this.v.setImageDrawable(drawable2);
    }

    public void setIconLabelHorizontalSpacing(int n3) {
        if (this.i != n3) {
            this.i = n3;
            this.q();
            this.requestLayout();
        }
    }

    public void setIconSize(int n3) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.v.getLayoutParams();
        layoutParams.width = n3;
        layoutParams.height = n3;
        this.v.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.q();
    }

    public void setIconTintList(ColorStateList colorStateList) {
        Drawable drawable;
        this.L = colorStateList;
        if (this.K != null && (drawable = this.N) != null) {
            drawable.setTintList(colorStateList);
            this.N.invalidateSelf();
        }
    }

    public void setItemBackground(int n3) {
        Drawable drawable = n3 == 0 ? null : this.getContext().getDrawable(n3);
        this.setItemBackground(drawable);
    }

    public void setItemBackground(Drawable drawable) {
        Drawable drawable2 = drawable;
        if (drawable != null) {
            drawable2 = drawable;
            if (drawable.getConstantState() != null) {
                drawable2 = drawable.getConstantState().newDrawable().mutate();
            }
        }
        this.e = drawable2;
        this.n();
    }

    public void setItemGravity(int n3) {
        this.f0 = n3;
        this.requestLayout();
    }

    public void setItemIconGravity(int n3) {
        if (this.d0 != n3) {
            this.d0 = n3;
            this.F();
            this.n();
        }
    }

    public void setItemPaddingBottom(int n3) {
        if (this.g != n3) {
            this.g = n3;
            this.m();
        }
    }

    public void setItemPaddingTop(int n3) {
        if (this.f != n3) {
            this.f = n3;
            this.m();
        }
    }

    public void setItemPosition(int n3) {
        this.D = n3;
    }

    public void setItemRippleColor(ColorStateList colorStateList) {
        this.d = colorStateList;
        this.n();
    }

    public void setLabelFontScalingEnabled(boolean bl) {
        this.j0 = bl;
        this.setTextAppearanceActive(this.E);
        this.setTextAppearanceInactive(this.F);
        this.setHorizontalTextAppearanceActive(this.G);
        this.setHorizontalTextAppearanceInactive(this.H);
    }

    public void setLabelMaxLines(int n3) {
        this.x.setMaxLines(n3);
        this.y.setMaxLines(n3);
        this.A.setMaxLines(n3);
        this.B.setMaxLines(n3);
        if (Build.VERSION.SDK_INT > 34) {
            this.x.setGravity(17);
            this.y.setGravity(17);
        } else if (n3 > 1) {
            this.x.setEllipsize(null);
            this.y.setEllipsize(null);
            this.x.setGravity(17);
            this.y.setGravity(17);
        } else {
            this.x.setGravity(16);
            this.y.setGravity(16);
        }
        this.requestLayout();
    }

    public void setLabelVisibilityMode(int n3) {
        if (this.p != n3) {
            this.p = n3;
            this.B();
            this.A(this.getWidth());
            this.m();
        }
    }

    public void setMeasureBottomPaddingFromLabelBaseline(boolean bl) {
        this.i0 = bl;
        this.w.setMeasurePaddingFromBaseline(bl);
        this.x.setIncludeFontPadding(bl);
        this.y.setIncludeFontPadding(bl);
        this.z.setMeasurePaddingFromBaseline(bl);
        this.A.setIncludeFontPadding(bl);
        this.B.setIncludeFontPadding(bl);
        this.requestLayout();
    }

    @Override
    public void setOnlyShowWhenExpanded(boolean bl) {
        this.h0 = bl;
        this.H();
    }

    public void setShifting(boolean bl) {
        if (this.q != bl) {
            this.q = bl;
            this.m();
        }
    }

    public void setShortcut(boolean bl, char c3) {
    }

    public void setTextAppearanceActive(int n3) {
        this.E = n3;
        this.D(this.y, n3);
    }

    public void setTextAppearanceActiveBoldEnabled(boolean bl) {
        this.J = bl;
        this.setTextAppearanceActive(this.E);
        this.setHorizontalTextAppearanceActive(this.G);
        this.C();
    }

    public void setTextAppearanceInactive(int n3) {
        this.F = n3;
        this.E(this.x, n3);
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.I = colorStateList;
        if (colorStateList != null) {
            this.x.setTextColor(colorStateList);
            this.y.setTextColor(colorStateList);
            this.A.setTextColor(colorStateList);
            this.B.setTextColor(colorStateList);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.x.setText(charSequence);
        this.y.setText(charSequence);
        this.A.setText(charSequence);
        this.B.setText(charSequence);
        Object object = this.K;
        if (object == null || TextUtils.isEmpty((CharSequence)((androidx.appcompat.view.menu.g)object).getContentDescription())) {
            this.setContentDescription(charSequence);
        }
        androidx.appcompat.view.menu.g g3 = this.K;
        object = charSequence;
        if (g3 != null) {
            object = TextUtils.isEmpty((CharSequence)g3.getTooltipText()) ? charSequence : this.K.getTooltipText();
        }
        r0.a((View)this, (CharSequence)object);
    }

    public final void t(TextView textView, int n3) {
        if (this.j0) {
            androidx.core.widget.j.m(textView, n3);
            return;
        }
        NavigationBarItemView.u(textView, n3);
    }

    public final void x(View view) {
        if (this.i() && view != null) {
            this.setClipChildren(false);
            this.setClipToPadding(false);
            b.a(this.c0, view);
        }
    }

    public final void y(View view) {
        if (!this.i()) {
            return;
        }
        if (view != null) {
            this.setClipChildren(true);
            this.setClipToPadding(true);
            b.e(this.c0, view);
        }
        this.c0 = null;
    }

    public final void z(View view) {
        if (!this.i()) {
            return;
        }
        b.f(this.c0, view, null);
    }

    public static class c {
        public c() {
        }

        public /* synthetic */ c(a a4) {
            this();
        }

        public float a(float f3, float f4) {
            float f5 = f4 - 0.0f;
            float f6 = f5 == 0.0f ? 0 : (f5 > 0.0f ? 1 : -1);
            f4 = f6 == false ? 0.8f : 0.0f;
            float f7 = f6 == false ? 1.0f : 0.2f;
            return a2.a.b(0.0f, 1.0f, f4, f7, f3);
        }

        public float b(float f3) {
            return a2.a.a(0.4f, 1.0f, f3);
        }

        public float c(float f3) {
            return 1.0f;
        }

        public void d(float f3, float f4, View view) {
            view.setScaleX(this.b(f3));
            view.setScaleY(this.c(f3));
            view.setAlpha(this.a(f3, f4));
        }
    }

    public static class d
    extends c {
        public d() {
            super(null);
        }

        public /* synthetic */ d(a a4) {
            this();
        }

        @Override
        public float c(float f3) {
            return this.b(f3);
        }
    }
}

