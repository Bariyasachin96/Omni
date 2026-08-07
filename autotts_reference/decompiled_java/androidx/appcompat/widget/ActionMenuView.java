/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.widget.LinearLayout$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.j;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.t0;

public class ActionMenuView
extends LinearLayoutCompat
implements e.b,
j {
    public int A;
    public int B;
    public d C;
    public e r;
    public Context s;
    public int t;
    public boolean u;
    public ActionMenuPresenter v;
    public i.a w;
    public e.a x;
    public boolean y;
    public int z;

    public ActionMenuView(Context context) {
        this(context, null);
    }

    public ActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setBaselineAligned(false);
        float f3 = context.getResources().getDisplayMetrics().density;
        this.A = (int)(56.0f * f3);
        this.B = (int)(f3 * 4.0f);
        this.s = context;
        this.t = 0;
    }

    /*
     * Unable to fully structure code
     */
    public static int J(View var0, int var1_1, int var2_2, int var3_3, int var4_4) {
        var11_5 = (LayoutParams)var0.getLayoutParams();
        var6_6 = View.MeasureSpec.makeMeasureSpec((int)(View.MeasureSpec.getSize((int)var3_3) - var4_4), (int)View.MeasureSpec.getMode((int)var3_3));
        var10_7 = var0 instanceof ActionMenuItemView != false ? (ActionMenuItemView)var0 : null;
        var9_8 = false;
        var3_3 = var10_7 != null && var10_7.s() != false ? 1 : 0;
        if (var2_2 <= 0) ** GOTO lbl-1000
        var4_4 = 2;
        if (var3_3 == 0 || var2_2 >= 2) {
            var0.measure(View.MeasureSpec.makeMeasureSpec((int)(var2_2 * var1_1), (int)-2147483648), var6_6);
            var7_9 = var0.getMeasuredWidth();
            var2_2 = var5_10 = var7_9 / var1_1;
            if (var7_9 % var1_1 != 0) {
                var2_2 = var5_10 + 1;
            }
            if (var3_3 != 0 && var2_2 < 2) {
                var2_2 = var4_4;
            }
        } else lbl-1000:
        // 2 sources

        {
            var2_2 = 0;
        }
        var8_11 = var9_8;
        if (!var11_5.a) {
            var8_11 = var9_8;
            if (var3_3 != 0) {
                var8_11 = true;
            }
        }
        var11_5.d = var8_11;
        var11_5.b = var2_2;
        var0.measure(View.MeasureSpec.makeMeasureSpec((int)(var1_1 * var2_2), (int)0x40000000), var6_6);
        return var2_2;
    }

    public LayoutParams A() {
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.gravity = 16;
        return layoutParams;
    }

    public LayoutParams B(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public LayoutParams C(ViewGroup.LayoutParams object) {
        if (object != null) {
            object = object instanceof LayoutParams ? new LayoutParams((LayoutParams)((Object)object)) : new LayoutParams((ViewGroup.LayoutParams)object);
            if (object.gravity <= 0) {
                object.gravity = 16;
            }
            return object;
        }
        return this.A();
    }

    public LayoutParams D() {
        LayoutParams layoutParams = this.A();
        layoutParams.a = true;
        return layoutParams;
    }

    public boolean E(int n3) {
        boolean bl = false;
        if (n3 == 0) {
            return false;
        }
        View view = this.getChildAt(n3 - 1);
        View view2 = this.getChildAt(n3);
        boolean bl2 = bl;
        if (n3 < this.getChildCount()) {
            bl2 = bl;
            if (view instanceof a) {
                bl2 = ((a)view).a();
            }
        }
        if (n3 > 0 && view2 instanceof a) {
            return ((a)view2).b() | bl2;
        }
        return bl2;
    }

    public boolean F() {
        ActionMenuPresenter actionMenuPresenter = this.v;
        return actionMenuPresenter != null && actionMenuPresenter.E();
    }

    public boolean G() {
        ActionMenuPresenter actionMenuPresenter = this.v;
        return actionMenuPresenter != null && actionMenuPresenter.G();
    }

    public boolean H() {
        ActionMenuPresenter actionMenuPresenter = this.v;
        return actionMenuPresenter != null && actionMenuPresenter.H();
    }

    public boolean I() {
        return this.u;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void K(int var1_1, int var2_2) {
        block35: {
            var20_3 = View.MeasureSpec.getMode((int)var2_2);
            var6_4 = View.MeasureSpec.getSize((int)var1_1);
            var5_5 = View.MeasureSpec.getSize((int)var2_2);
            var7_6 = this.getPaddingLeft();
            var1_1 = this.getPaddingRight();
            var18_7 = this.getPaddingTop() + this.getPaddingBottom();
            var22_8 = ViewGroup.getChildMeasureSpec((int)var2_2, (int)var18_7, (int)-2);
            var21_9 = var6_4 - (var7_6 + var1_1);
            var8_10 = var21_9 / (var1_1 = this.A);
            if (var8_10 == 0) {
                this.setMeasuredDimension(var21_9, 0);
                return;
            }
            var10_11 = var1_1 + var21_9 % var1_1 / var8_10;
            var23_12 = this.getChildCount();
            var13_13 = 0;
            var6_4 = var2_2 = (var1_1 = (var7_6 = (var9_14 = 0)));
            var25_15 = 0L;
            var11_16 = var2_2;
            var12_17 = var1_1;
            var2_2 = var13_13;
            var1_1 = var8_10;
            while (var9_14 < var23_12) {
                var34_26 /* !! */  = this.getChildAt(var9_14);
                if (var34_26 /* !! */ .getVisibility() == 8) {
                    var13_13 = var7_6;
                    var27_24 = var25_15;
                } else {
                    var24_23 = var34_26 /* !! */  instanceof ActionMenuItemView;
                    var14_18 = var12_17 + 1;
                    if (var24_23) {
                        var8_10 = this.B;
                        var34_26 /* !! */ .setPadding(var8_10, 0, var8_10, 0);
                    }
                    var33_25 = (LayoutParams)var34_26 /* !! */ .getLayoutParams();
                    var33_25.f = false;
                    var33_25.c = 0;
                    var33_25.b = 0;
                    var33_25.d = false;
                    var33_25.leftMargin = 0;
                    var33_25.rightMargin = 0;
                    var24_23 = var24_23 != false && ((ActionMenuItemView)var34_26 /* !! */ ).s() != false;
                    var33_25.e = var24_23;
                    var8_10 = var33_25.a != false ? 1 : var1_1;
                    var19_22 = ActionMenuView.J(var34_26 /* !! */ , var10_11, var8_10, var22_8, var18_7);
                    var15_19 = Math.max(var11_16, var19_22);
                    var8_10 = var6_4;
                    if (var33_25.d) {
                        var8_10 = var6_4 + 1;
                    }
                    if (var33_25.a) {
                        var7_6 = 1;
                    }
                    var16_20 = var1_1 - var19_22;
                    var17_21 = Math.max(var2_2, var34_26 /* !! */ .getMeasuredHeight());
                    var1_1 = var16_20;
                    var2_2 = var17_21;
                    var13_13 = var7_6;
                    var12_17 = var14_18;
                    var11_16 = var15_19;
                    var6_4 = var8_10;
                    var27_24 = var25_15;
                    if (var19_22 == 1) {
                        var27_24 = var25_15 | (long)(1 << var9_14);
                        var6_4 = var8_10;
                        var11_16 = var15_19;
                        var12_17 = var14_18;
                        var13_13 = var7_6;
                        var2_2 = var17_21;
                        var1_1 = var16_20;
                    }
                }
                ++var9_14;
                var7_6 = var13_13;
                var25_15 = var27_24;
            }
            var13_13 = 2;
            var8_10 = var7_6 != 0 && var12_17 == 2 ? 1 : 0;
            var9_14 = 0;
            var14_18 = var1_1;
            var1_1 = var9_14;
            while (var6_4 > 0 && var14_18 > 0) {
                var15_19 = 0x7FFFFFFF;
                var29_27 = 0L;
                var18_7 = 0;
                for (var16_20 = 0; var16_20 < var23_12; ++var16_20) {
                    var33_25 = (LayoutParams)this.getChildAt(var16_20).getLayoutParams();
                    if (!var33_25.d) {
                        var9_14 = var18_7;
                        var17_21 = var15_19;
                        var27_24 = var29_27;
                    } else {
                        var19_22 = var33_25.b;
                        if (var19_22 < var15_19) {
                            var27_24 = 1L << var16_20;
                            var17_21 = var19_22;
                            var9_14 = 1;
                        } else {
                            var9_14 = var18_7;
                            var17_21 = var15_19;
                            var27_24 = var29_27;
                            if (var19_22 == var15_19) {
                                var27_24 = var29_27 | 1L << var16_20;
                                var9_14 = var18_7 + 1;
                                var17_21 = var15_19;
                            }
                        }
                    }
                    var18_7 = var9_14;
                    var15_19 = var17_21;
                    var29_27 = var27_24;
                }
                var25_15 |= var29_27;
                if (var18_7 > var14_18) break;
                for (var1_1 = 0; var1_1 < var23_12; ++var1_1) {
                    var34_26 /* !! */  = this.getChildAt(var1_1);
                    var33_25 = (LayoutParams)var34_26 /* !! */ .getLayoutParams();
                    var31_28 = 1 << var1_1;
                    if ((var29_27 & var31_28) == 0L) {
                        var27_24 = var25_15;
                        if (var33_25.b == var15_19 + 1) {
                            var27_24 = var25_15 | var31_28;
                        }
                        var25_15 = var27_24;
                        continue;
                    }
                    if (var8_10 != 0 && var33_25.e && var14_18 == 1) {
                        var9_14 = this.B;
                        var34_26 /* !! */ .setPadding(var9_14 + var10_11, 0, var9_14, 0);
                    }
                    ++var33_25.b;
                    var33_25.f = true;
                    --var14_18;
                }
                var1_1 = 1;
            }
            var6_4 = var7_6 == 0 && var12_17 == 1 ? 1 : 0;
            if (var14_18 <= 0 || var25_15 == 0L || var14_18 >= var12_17 - 1 && var6_4 == 0 && var11_16 <= 1) break block35;
            var4_29 = Long.bitCount(var25_15);
            if (var6_4 == 0) {
                if ((var25_15 & 1L) != 0L) {
                    var3_30 = var4_29;
                    if (!((LayoutParams)this.getChildAt((int)0).getLayoutParams()).e) {
                        var3_30 = var4_29 - 0.5f;
                    }
                } else {
                    var3_30 = var4_29;
                }
                var6_4 = var23_12 - 1;
                var4_29 = var3_30;
                if ((var25_15 & (long)(1 << var6_4)) != 0L) {
                    var4_29 = var3_30;
                    if (!((LayoutParams)this.getChildAt((int)var6_4).getLayoutParams()).e) {
                        var4_29 = var3_30 - 0.5f;
                    }
                }
            }
            var7_6 = var4_29 > 0.0f ? (int)((float)(var14_18 * var10_11) / var4_29) : 0;
            for (var8_10 = 0; var8_10 < var23_12; ++var8_10) {
                if ((var25_15 & (long)(1 << var8_10)) == 0L) ** GOTO lbl152
                var33_25 = this.getChildAt(var8_10);
                var34_26 /* !! */  = (LayoutParams)var33_25.getLayoutParams();
                if (var33_25 instanceof ActionMenuItemView) {
                    var34_26 /* !! */ .c = var7_6;
                    var34_26 /* !! */ .f = true;
                    if (var8_10 == 0 && !var34_26 /* !! */ .e) {
                        var34_26 /* !! */ .leftMargin = -var7_6 / 2;
                    }
                    var1_1 = 1;
lbl152:
                    // 2 sources

                    var6_4 = var1_1;
                } else if (var34_26 /* !! */ .a) {
                    var34_26 /* !! */ .c = var7_6;
                    var34_26 /* !! */ .f = true;
                    var34_26 /* !! */ .rightMargin = -var7_6 / 2;
                    var6_4 = 1;
                } else {
                    if (var8_10 != 0) {
                        var34_26 /* !! */ .leftMargin = var7_6 / 2;
                    }
                    var6_4 = var1_1;
                    if (var8_10 != var23_12 - 1) {
                        var34_26 /* !! */ .rightMargin = var7_6 / 2;
                        var6_4 = var1_1;
                    }
                }
                var1_1 = var6_4;
            }
        }
        if (var1_1 != 0) {
            for (var1_1 = 0; var1_1 < var23_12; ++var1_1) {
                var34_26 /* !! */  = this.getChildAt(var1_1);
                var33_25 = (LayoutParams)var34_26 /* !! */ .getLayoutParams();
                if (!var33_25.f) continue;
                var34_26 /* !! */ .measure(View.MeasureSpec.makeMeasureSpec((int)(var33_25.b * var10_11 + var33_25.c), (int)0x40000000), var22_8);
            }
        }
        var1_1 = var20_3 != 0x40000000 ? var2_2 : var5_5;
        this.setMeasuredDimension(var21_9, var1_1);
    }

    public e L() {
        return this.r;
    }

    public boolean M() {
        ActionMenuPresenter actionMenuPresenter = this.v;
        return actionMenuPresenter != null && actionMenuPresenter.N();
    }

    @Override
    public boolean a(g g3) {
        return this.r.O(g3, 0);
    }

    @Override
    public void b(e e3) {
        this.r = e3;
    }

    @Override
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public Menu getMenu() {
        if (this.r == null) {
            Object object = this.getContext();
            Object object2 = new e((Context)object);
            this.r = object2;
            ((e)object2).W(new c(this));
            object = new ActionMenuPresenter((Context)object);
            this.v = object;
            ((ActionMenuPresenter)object).M(true);
            object2 = this.v;
            object = this.w;
            if (object == null) {
                object = new b();
            }
            ((androidx.appcompat.view.menu.a)object2).m((i.a)object);
            this.r.c(this.v, this.s);
            this.v.K(this);
        }
        return this.r;
    }

    public Drawable getOverflowIcon() {
        this.getMenu();
        return this.v.D();
    }

    public int getPopupTheme() {
        return this.t;
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void onConfigurationChanged(Configuration object) {
        super.onConfigurationChanged((Configuration)object);
        object = this.v;
        if (object != null) {
            ((ActionMenuPresenter)object).g(false);
            if (this.v.H()) {
                this.v.E();
                this.v.N();
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.z();
    }

    @Override
    public void onLayout(boolean bl, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        Object object;
        Object object2;
        if (!this.y) {
            super.onLayout(bl, n3, n4, n5, n6);
            return;
        }
        int n9 = this.getChildCount();
        int n10 = (n6 - n4) / 2;
        int n11 = this.getDividerWidth();
        int n12 = n5 - n3;
        n3 = n12 - this.getPaddingRight() - this.getPaddingLeft();
        bl = t0.b((View)this);
        n6 = 0;
        n5 = 0;
        for (n4 = 0; n4 < n9; ++n4) {
            object2 = this.getChildAt(n4);
            if (object2.getVisibility() == 8) continue;
            object = (LayoutParams)object2.getLayoutParams();
            if (object.a) {
                n6 = n8 = object2.getMeasuredWidth();
                if (this.E(n4)) {
                    n6 = n8 + n11;
                }
                int n13 = object2.getMeasuredHeight();
                if (bl) {
                    n7 = this.getPaddingLeft() + object.leftMargin;
                    n8 = n7 + n6;
                } else {
                    n8 = this.getWidth() - this.getPaddingRight() - object.rightMargin;
                    n7 = n8 - n6;
                }
                int n14 = n10 - n13 / 2;
                object2.layout(n7, n14, n8, n13 + n14);
                n3 -= n6;
                n6 = 1;
                continue;
            }
            n3 -= object2.getMeasuredWidth() + object.leftMargin + object.rightMargin;
            this.E(n4);
            ++n5;
        }
        if (n9 == 1 && n6 == 0) {
            object = this.getChildAt(0);
            n3 = object.getMeasuredWidth();
            n4 = object.getMeasuredHeight();
            n5 = n12 / 2 - n3 / 2;
            n6 = n10 - n4 / 2;
            object.layout(n5, n6, n3 + n5, n4 + n6);
            return;
        }
        n4 = n5 - (n6 ^ 1);
        n3 = n4 > 0 ? (n3 /= n4) : 0;
        n6 = Math.max(0, n3);
        if (bl) {
            n4 = this.getWidth() - this.getPaddingRight();
            for (n3 = 0; n3 < n9; ++n3) {
                object = this.getChildAt(n3);
                object2 = (LayoutParams)object.getLayoutParams();
                n5 = n4;
                if (object.getVisibility() != 8) {
                    if (object2.a) {
                        n5 = n4;
                    } else {
                        n8 = n4 - object2.rightMargin;
                        n7 = object.getMeasuredWidth();
                        n4 = object.getMeasuredHeight();
                        n5 = n10 - n4 / 2;
                        object.layout(n8 - n7, n5, n8, n4 + n5);
                        n5 = n8 - (n7 + object2.leftMargin + n6);
                    }
                }
                n4 = n5;
            }
        } else {
            n5 = this.getPaddingLeft();
            for (n3 = 0; n3 < n9; ++n3) {
                object2 = this.getChildAt(n3);
                object = (LayoutParams)object2.getLayoutParams();
                n4 = n5;
                if (object2.getVisibility() != 8) {
                    if (object.a) {
                        n4 = n5;
                    } else {
                        n7 = object2.getMeasuredWidth();
                        n4 = object2.getMeasuredHeight();
                        n8 = n10 - n4 / 2;
                        object2.layout(n5 += object.leftMargin, n8, n5 + n7, n4 + n8);
                        n4 = n5 + (n7 + object.rightMargin + n6);
                    }
                }
                n5 = n4;
            }
        }
    }

    @Override
    public void onMeasure(int n3, int n4) {
        Object object;
        boolean bl = this.y;
        boolean bl2 = View.MeasureSpec.getMode((int)n3) == 0x40000000;
        this.y = bl2;
        if (bl != bl2) {
            this.z = 0;
        }
        int n5 = View.MeasureSpec.getSize((int)n3);
        if (this.y && (object = this.r) != null && n5 != this.z) {
            this.z = n5;
            object.N(true);
        }
        int n6 = this.getChildCount();
        if (this.y && n6 > 0) {
            this.K(n3, n4);
            return;
        }
        for (n5 = 0; n5 < n6; ++n5) {
            object = (LayoutParams)this.getChildAt(n5).getLayoutParams();
            ((LinearLayout.LayoutParams)object).rightMargin = 0;
            ((LinearLayout.LayoutParams)object).leftMargin = 0;
        }
        super.onMeasure(n3, n4);
    }

    public void setExpandedActionViewsExclusive(boolean bl) {
        this.v.J(bl);
    }

    public void setMenuCallbacks(i.a a4, e.a a5) {
        this.w = a4;
        this.x = a5;
    }

    public void setOnMenuItemClickListener(d d3) {
        this.C = d3;
    }

    public void setOverflowIcon(Drawable drawable) {
        this.getMenu();
        this.v.L(drawable);
    }

    public void setOverflowReserved(boolean bl) {
        this.u = bl;
    }

    public void setPopupTheme(int n3) {
        if (this.t != n3) {
            this.t = n3;
            if (n3 == 0) {
                this.s = this.getContext();
                return;
            }
            this.s = new ContextThemeWrapper(this.getContext(), n3);
        }
    }

    public void setPresenter(ActionMenuPresenter actionMenuPresenter) {
        this.v = actionMenuPresenter;
        actionMenuPresenter.K(this);
    }

    public void z() {
        ActionMenuPresenter actionMenuPresenter = this.v;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.B();
        }
    }

    public static class LayoutParams
    extends LinearLayoutCompat.LayoutParams {
        public boolean a;
        public int b;
        public int c;
        public boolean d;
        public boolean e;
        public boolean f;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
            this.a = false;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams)layoutParams);
            this.a = layoutParams.a;
        }
    }

    public static interface a {
        public boolean a();

        public boolean b();
    }

    public static class b
    implements i.a {
        @Override
        public void a(e e3, boolean bl) {
        }

        @Override
        public boolean b(e e3) {
            return false;
        }
    }

    public class c
    implements e.a {
        public final ActionMenuView c;

        public c(ActionMenuView actionMenuView) {
            this.c = actionMenuView;
        }

        @Override
        public boolean a(e object, MenuItem menuItem) {
            object = this.c.C;
            return object != null && object.onMenuItemClick(menuItem);
        }

        @Override
        public void b(e e3) {
            e.a a4 = this.c.x;
            if (a4 != null) {
                a4.b(e3);
            }
        }
    }

    public static interface d {
        public boolean onMenuItemClick(MenuItem var1);
    }
}

