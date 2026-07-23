/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.util.SparseArray
 *  android.util.TypedValue
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.TextView
 */
package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;
import androidx.appcompat.view.menu.j;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionSet;
import androidx.transition.c;
import com.google.android.material.internal.x;
import com.google.android.material.navigation.NavigationBarDividerView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarPresenter;
import com.google.android.material.navigation.NavigationBarSubheaderView;
import com.google.android.material.navigation.a;
import com.google.android.material.navigation.f;
import com.google.android.material.navigation.g;
import java.util.HashSet;
import n0.e;
import p0.s;
import p2.k;
import v2.i;
import v2.o;
import z1.h;

public abstract class NavigationBarMenuView
extends ViewGroup
implements j {
    public static final int[] b0 = new int[]{0x10100A0};
    public static final int[] c0 = new int[]{-16842910};
    public int A = -1;
    public int B = -1;
    public boolean C;
    public int D;
    public int E;
    public int F;
    public int G;
    public int H;
    public int I;
    public int J = 49;
    public o K;
    public boolean L = false;
    public ColorStateList M;
    public NavigationBarPresenter N;
    public f O;
    public boolean P;
    public boolean Q;
    public int R = 1;
    public int S = 0;
    public boolean T;
    public MenuItem U = null;
    public int V = 7;
    public boolean W = false;
    public final Rect a0;
    public final TransitionSet c;
    public final View.OnClickListener d;
    public e e;
    public final SparseArray f = new SparseArray();
    public int g;
    public int h;
    public g[] i;
    public int j = -1;
    public int k = -1;
    public ColorStateList l;
    public int m;
    public ColorStateList n;
    public final ColorStateList o;
    public int p;
    public int q;
    public int r;
    public int s;
    public boolean t;
    public Drawable u;
    public ColorStateList v;
    public int w;
    public final SparseArray x = new SparseArray();
    public int y = -1;
    public int z = -1;

    public NavigationBarMenuView(Context object) {
        super((Context)object);
        this.a0 = new Rect();
        this.o = this.e(16842808);
        if (this.isInEditMode()) {
            this.c = null;
        } else {
            object = new AutoTransition();
            this.c = object;
            ((TransitionSet)object).y0(0);
            ((TransitionSet)object).s(TextView.class, true);
            ((TransitionSet)object).w0(p2.k.f(this.getContext(), z1.c.motionDurationMedium4, this.getResources().getInteger(z1.h.material_motion_duration_long_1)));
            ((TransitionSet)object).x0(p2.k.g(this.getContext(), z1.c.motionEasingStandard, a2.a.b));
            ((TransitionSet)object).q0(new x());
        }
        this.d = new View.OnClickListener(this){
            public final NavigationBarMenuView c;
            {
                this.c = navigationBarMenuView;
            }

            public void onClick(View object) {
                object = ((NavigationBarItemView)object).getItemData();
                boolean bl = this.c.O.e((MenuItem)object, this.c.N, 0);
                if (object != null && object.isCheckable() && (!bl || object.isChecked())) {
                    this.c.setCheckedItem((MenuItem)object);
                }
            }
        };
        this.setImportantForAccessibility(1);
    }

    private int getCollapsedVisibleItemCount() {
        return Math.min(this.V, this.O.d());
    }

    private NavigationBarItemView getNewItem() {
        Object object = this.e;
        object = object != null ? (NavigationBarItemView)object.b() : null;
        Object object2 = object;
        if (object == null) {
            object2 = this.h(this.getContext());
        }
        return object2;
    }

    private void setBadgeIfNeeded(NavigationBarItemView navigationBarItemView) {
        com.google.android.material.badge.a a4;
        int n3 = navigationBarItemView.getId();
        if (this.k(n3) && (a4 = (com.google.android.material.badge.a)this.x.get(n3)) != null) {
            navigationBarItemView.setBadge(a4);
        }
    }

    @Override
    public void b(androidx.appcompat.view.menu.e e3) {
        this.O = new f(e3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void d() {
        this.removeAllViews();
        this.l();
        this.N.h(true);
        this.O.f();
        this.N.h(false);
        int n3 = this.O.a();
        if (n3 == 0) {
            this.j = 0;
            this.k = 0;
            this.i = null;
            this.e = null;
            return;
        }
        if (this.e == null || this.S != n3) {
            this.S = n3;
            this.e = new n0.g(n3);
        }
        this.m();
        int n4 = this.O.g();
        this.i = new g[n4];
        boolean bl = this.j(this.g, this.getCurrentVisibleContentItemCount());
        int n5 = n3 = 0;
        for (int i3 = 0; i3 < n4; ++i3) {
            Object object;
            MenuItem menuItem = this.O.b(i3);
            boolean bl2 = menuItem instanceof a;
            if (bl2) {
                object = new NavigationBarDividerView(this.getContext());
                object.setOnlyShowWhenExpanded(true);
                ((NavigationBarDividerView)object).setDividersEnabled(this.W);
            } else if (menuItem.hasSubMenu()) {
                if (n3 > 0) throw new IllegalArgumentException("Only one layer of submenu is supported; a submenu inside a submenu is not supported by the Navigation Bar.");
                object = new NavigationBarSubheaderView(this.getContext());
                n3 = this.s;
                if (n3 == 0) {
                    n3 = this.q;
                }
                ((NavigationBarSubheaderView)object).setTextAppearance(n3);
                ((NavigationBarSubheaderView)object).setTextColor(this.n);
                object.setOnlyShowWhenExpanded(true);
                object.d((androidx.appcompat.view.menu.g)menuItem, 0);
                n3 = menuItem.getSubMenu().size();
            } else if (n3 > 0) {
                object = this.g(i3, (androidx.appcompat.view.menu.g)menuItem, bl, true);
                --n3;
            } else {
                object = (androidx.appcompat.view.menu.g)menuItem;
                boolean bl3 = n5 >= this.V;
                object = this.g(i3, (androidx.appcompat.view.menu.g)object, bl, bl3);
                ++n5;
            }
            if (!bl2 && menuItem.isCheckable() && this.k == -1) {
                this.k = i3;
            }
            this.i[i3] = object;
            this.addView((View)object);
        }
        this.k = n3 = Math.min(n4 - 1, this.k);
        this.setCheckedItem(this.i[n3].getItemData());
    }

    public ColorStateList e(int n3) {
        Object object = new TypedValue();
        if (!this.getContext().getTheme().resolveAttribute(n3, object, true)) {
            return null;
        }
        ColorStateList colorStateList = d.a.a(this.getContext(), object.resourceId);
        if (!this.getContext().getTheme().resolveAttribute(c.a.colorPrimary, object, true)) {
            return null;
        }
        int n4 = object.data;
        n3 = colorStateList.getDefaultColor();
        int[] nArray = c0;
        int[] nArray2 = b0;
        object = ViewGroup.EMPTY_STATE_SET;
        int n5 = colorStateList.getColorForState(nArray, n3);
        return new ColorStateList((int[][])new int[][]{nArray, nArray2, (int[])object}, new int[]{n5, n4, n3});
    }

    public final Drawable f() {
        if (this.K != null && this.M != null) {
            i i3 = new i(this.K);
            i3.i0(this.M);
            return i3;
        }
        return null;
    }

    public final NavigationBarItemView g(int n3, androidx.appcompat.view.menu.g g3, boolean bl, boolean bl2) {
        this.N.h(true);
        g3.setCheckable(true);
        this.N.h(false);
        NavigationBarItemView navigationBarItemView = this.getNewItem();
        navigationBarItemView.setShifting(bl);
        navigationBarItemView.setLabelMaxLines(this.R);
        navigationBarItemView.setIconTintList(this.l);
        navigationBarItemView.setIconSize(this.m);
        navigationBarItemView.setTextColor(this.o);
        navigationBarItemView.setTextAppearanceInactive(this.p);
        navigationBarItemView.setTextAppearanceActive(this.q);
        navigationBarItemView.setHorizontalTextAppearanceInactive(this.r);
        navigationBarItemView.setHorizontalTextAppearanceActive(this.s);
        navigationBarItemView.setTextAppearanceActiveBoldEnabled(this.t);
        navigationBarItemView.setTextColor(this.n);
        int n4 = this.y;
        if (n4 != -1) {
            navigationBarItemView.setItemPaddingTop(n4);
        }
        if ((n4 = this.z) != -1) {
            navigationBarItemView.setItemPaddingBottom(n4);
        }
        navigationBarItemView.setMeasureBottomPaddingFromLabelBaseline(this.P);
        navigationBarItemView.setLabelFontScalingEnabled(this.Q);
        n4 = this.A;
        if (n4 != -1) {
            navigationBarItemView.setActiveIndicatorLabelPadding(n4);
        }
        if ((n4 = this.B) != -1) {
            navigationBarItemView.setIconLabelHorizontalSpacing(n4);
        }
        navigationBarItemView.setActiveIndicatorWidth(this.D);
        navigationBarItemView.setActiveIndicatorHeight(this.E);
        navigationBarItemView.setActiveIndicatorExpandedWidth(this.F);
        navigationBarItemView.setActiveIndicatorExpandedHeight(this.G);
        navigationBarItemView.setActiveIndicatorMarginHorizontal(this.H);
        navigationBarItemView.setItemGravity(this.J);
        navigationBarItemView.setActiveIndicatorExpandedPadding(this.a0);
        navigationBarItemView.setActiveIndicatorExpandedMarginHorizontal(this.I);
        navigationBarItemView.setActiveIndicatorDrawable(this.f());
        navigationBarItemView.setActiveIndicatorResizeable(this.L);
        navigationBarItemView.setActiveIndicatorEnabled(this.C);
        Drawable drawable = this.u;
        if (drawable != null) {
            navigationBarItemView.setItemBackground(drawable);
        } else {
            navigationBarItemView.setItemBackground(this.w);
        }
        navigationBarItemView.setItemRippleColor(this.v);
        navigationBarItemView.setLabelVisibilityMode(this.g);
        navigationBarItemView.setItemIconGravity(this.h);
        navigationBarItemView.setOnlyShowWhenExpanded(bl2);
        navigationBarItemView.setExpanded(this.T);
        navigationBarItemView.d(g3, 0);
        navigationBarItemView.setItemPosition(n3);
        n4 = g3.getItemId();
        navigationBarItemView.setOnTouchListener((View.OnTouchListener)this.f.get(n4));
        navigationBarItemView.setOnClickListener(this.d);
        int n5 = this.j;
        if (n5 != 0 && n4 == n5) {
            this.k = n3;
        }
        this.setBadgeIfNeeded(navigationBarItemView);
        return navigationBarItemView;
    }

    public int getActiveIndicatorLabelPadding() {
        return this.A;
    }

    public SparseArray<com.google.android.material.badge.a> getBadgeDrawables() {
        return this.x;
    }

    public int getCurrentVisibleContentItemCount() {
        if (this.T) {
            return this.O.c();
        }
        return this.getCollapsedVisibleItemCount();
    }

    public int getHorizontalItemTextAppearanceActive() {
        return this.s;
    }

    public int getHorizontalItemTextAppearanceInactive() {
        return this.r;
    }

    public int getIconLabelHorizontalSpacing() {
        return this.B;
    }

    public ColorStateList getIconTintList() {
        return this.l;
    }

    public ColorStateList getItemActiveIndicatorColor() {
        return this.M;
    }

    public boolean getItemActiveIndicatorEnabled() {
        return this.C;
    }

    public int getItemActiveIndicatorExpandedHeight() {
        return this.G;
    }

    public int getItemActiveIndicatorExpandedMarginHorizontal() {
        return this.I;
    }

    public int getItemActiveIndicatorExpandedWidth() {
        return this.F;
    }

    public int getItemActiveIndicatorHeight() {
        return this.E;
    }

    public int getItemActiveIndicatorMarginHorizontal() {
        return this.H;
    }

    public o getItemActiveIndicatorShapeAppearance() {
        return this.K;
    }

    public int getItemActiveIndicatorWidth() {
        return this.D;
    }

    public Drawable getItemBackground() {
        g[] gArray = this.i;
        if (gArray != null && gArray.length > 0) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                return ((NavigationBarItemView)g3).getBackground();
            }
        }
        return this.u;
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.w;
    }

    public int getItemGravity() {
        return this.J;
    }

    public int getItemIconGravity() {
        return this.h;
    }

    public int getItemIconSize() {
        return this.m;
    }

    public int getItemPaddingBottom() {
        return this.z;
    }

    public int getItemPaddingTop() {
        return this.y;
    }

    public ColorStateList getItemRippleColor() {
        return this.v;
    }

    public int getItemTextAppearanceActive() {
        return this.q;
    }

    public int getItemTextAppearanceInactive() {
        return this.p;
    }

    public ColorStateList getItemTextColor() {
        return this.n;
    }

    public int getLabelMaxLines() {
        return this.R;
    }

    public int getLabelVisibilityMode() {
        return this.g;
    }

    public f getMenu() {
        return this.O;
    }

    public boolean getScaleLabelTextWithFont() {
        return this.Q;
    }

    public int getSelectedItemId() {
        return this.j;
    }

    public int getSelectedItemPosition() {
        return this.k;
    }

    public int getWindowAnimations() {
        return 0;
    }

    public abstract NavigationBarItemView h(Context var1);

    public final boolean i() {
        f f3;
        if (this.i != null && (f3 = this.O) != null && f3.g() == this.i.length) {
            int n3 = 0;
            while (true) {
                int n4 = this.i.length;
                boolean bl = true;
                if (n3 >= n4) break;
                if (this.O.b(n3) instanceof a && !(this.i[n3] instanceof NavigationBarDividerView)) {
                    return false;
                }
                n4 = this.O.b(n3).hasSubMenu() && !(this.i[n3] instanceof NavigationBarSubheaderView) ? 1 : 0;
                if (this.O.b(n3).hasSubMenu() || this.i[n3] instanceof NavigationBarItemView) {
                    bl = false;
                }
                if (!(this.O.b(n3) instanceof a) && (n4 != 0 || bl)) {
                    return false;
                }
                ++n3;
            }
            return true;
        }
        return false;
    }

    public boolean j(int n3, int n4) {
        if (n3 == -1) {
            return n4 > 3;
        }
        return n3 == 0;
    }

    public final boolean k(int n3) {
        return n3 != -1;
    }

    public final void l() {
        g[] gArray = this.i;
        if (gArray != null && this.e != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                e e3 = this.e;
                g3 = (NavigationBarItemView)g3;
                e3.a(g3);
                ((NavigationBarItemView)g3).g();
            }
        }
    }

    public final void m() {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= this.O.g()) break;
            hashSet.add(this.O.b(n4).getItemId());
            ++n4;
        }
        for (int i3 = n3; i3 < this.x.size(); ++i3) {
            n4 = this.x.keyAt(i3);
            if (hashSet.contains(n4)) continue;
            this.x.delete(n4);
        }
    }

    public void n(SparseArray gArray) {
        int n3;
        int n4;
        int n5 = 0;
        for (n4 = 0; n4 < gArray.size(); ++n4) {
            n3 = gArray.keyAt(n4);
            if (this.x.indexOfKey(n3) >= 0) continue;
            this.x.append(n3, (Object)((com.google.android.material.badge.a)gArray.get(n3)));
        }
        gArray = this.i;
        if (gArray != null) {
            n3 = gArray.length;
            for (n4 = n5; n4 < n3; ++n4) {
                g g3 = gArray[n4];
                if (!(g3 instanceof NavigationBarItemView)) continue;
                Object object = this.x;
                if ((object = (com.google.android.material.badge.a)object.get((g3 = (NavigationBarItemView)g3).getId())) == null) continue;
                ((NavigationBarItemView)g3).setBadge((com.google.android.material.badge.a)object);
            }
        }
    }

    public void o(int n3) {
        int n4 = this.O.g();
        for (int i3 = 0; i3 < n4; ++i3) {
            MenuItem menuItem = this.O.b(i3);
            if (n3 != menuItem.getItemId()) continue;
            this.j = n3;
            this.k = i3;
            this.setCheckedItem(menuItem);
            return;
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        p0.s.L0(accessibilityNodeInfo).j0(s.e.b(1, this.getCurrentVisibleContentItemCount(), false, 1));
    }

    public void p(int n3) {
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).A(n3);
            }
        }
    }

    public void q() {
        if (this.O != null && this.i != null) {
            Object object;
            int n3;
            this.N.h(true);
            this.O.f();
            this.N.h(false);
            if (!this.i()) {
                this.d();
                return;
            }
            int n4 = this.j;
            int n5 = this.O.g();
            for (n3 = 0; n3 < n5; ++n3) {
                object = this.O.b(n3);
                if (!object.isChecked()) continue;
                this.setCheckedItem((MenuItem)object);
                this.j = object.getItemId();
                this.k = n3;
            }
            if (n4 != this.j && (object = this.c) != null) {
                androidx.transition.c.a(this, (Transition)object);
            }
            boolean bl = this.j(this.g, this.getCurrentVisibleContentItemCount());
            for (n3 = 0; n3 < n5; ++n3) {
                this.N.h(true);
                this.i[n3].setExpanded(this.T);
                object = this.i[n3];
                if (object instanceof NavigationBarItemView) {
                    object = (NavigationBarItemView)object;
                    ((NavigationBarItemView)object).setLabelVisibilityMode(this.g);
                    ((NavigationBarItemView)object).setItemIconGravity(this.h);
                    ((NavigationBarItemView)object).setItemGravity(this.J);
                    ((NavigationBarItemView)object).setShifting(bl);
                }
                if (this.O.b(n3) instanceof androidx.appcompat.view.menu.g) {
                    this.i[n3].d((androidx.appcompat.view.menu.g)this.O.b(n3), 0);
                }
                this.N.h(false);
            }
        }
    }

    public void setActiveIndicatorLabelPadding(int n3) {
        this.A = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorLabelPadding(n3);
            }
        }
    }

    public void setCheckedItem(MenuItem menuItem) {
        if (this.U != menuItem && menuItem.isCheckable()) {
            MenuItem menuItem2 = this.U;
            if (menuItem2 != null && menuItem2.isChecked()) {
                this.U.setChecked(false);
            }
            menuItem.setChecked(true);
            this.U = menuItem;
        }
    }

    public void setCollapsedMaxItemCount(int n3) {
        this.V = n3;
    }

    public void setExpanded(boolean bl) {
        this.T = bl;
        g[] gArray = this.i;
        if (gArray != null) {
            int n3 = gArray.length;
            for (int i3 = 0; i3 < n3; ++i3) {
                gArray[i3].setExpanded(bl);
            }
        }
    }

    public void setHorizontalItemTextAppearanceActive(int n3) {
        this.s = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setHorizontalTextAppearanceActive(n3);
            }
        }
    }

    public void setHorizontalItemTextAppearanceInactive(int n3) {
        this.r = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setHorizontalTextAppearanceInactive(n3);
            }
        }
    }

    public void setIconLabelHorizontalSpacing(int n3) {
        this.B = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setIconLabelHorizontalSpacing(n3);
            }
        }
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.l = colorStateList;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setIconTintList(colorStateList);
            }
        }
    }

    public void setItemActiveIndicatorColor(ColorStateList gArray) {
        this.M = gArray;
        gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorDrawable(this.f());
            }
        }
    }

    public void setItemActiveIndicatorEnabled(boolean bl) {
        this.C = bl;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorEnabled(bl);
            }
        }
    }

    public void setItemActiveIndicatorExpandedHeight(int n3) {
        this.G = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorExpandedHeight(n3);
            }
        }
    }

    public void setItemActiveIndicatorExpandedMarginHorizontal(int n3) {
        this.I = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorExpandedMarginHorizontal(n3);
            }
        }
    }

    public void setItemActiveIndicatorExpandedPadding(int n3, int n4, int n5, int n6) {
        g[] gArray = this.a0;
        gArray.left = n3;
        gArray.top = n4;
        gArray.right = n5;
        gArray.bottom = n6;
        gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorExpandedPadding(this.a0);
            }
        }
    }

    public void setItemActiveIndicatorExpandedWidth(int n3) {
        this.F = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorExpandedWidth(n3);
            }
        }
    }

    public void setItemActiveIndicatorHeight(int n3) {
        this.E = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorHeight(n3);
            }
        }
    }

    public void setItemActiveIndicatorMarginHorizontal(int n3) {
        this.H = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorMarginHorizontal(n3);
            }
        }
    }

    public void setItemActiveIndicatorResizeable(boolean bl) {
        this.L = bl;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorResizeable(bl);
            }
        }
    }

    public void setItemActiveIndicatorShapeAppearance(o gArray) {
        this.K = gArray;
        gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorDrawable(this.f());
            }
        }
    }

    public void setItemActiveIndicatorWidth(int n3) {
        this.D = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setActiveIndicatorWidth(n3);
            }
        }
    }

    public void setItemBackground(Drawable drawable) {
        this.u = drawable;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemBackground(drawable);
            }
        }
    }

    public void setItemBackgroundRes(int n3) {
        this.w = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemBackground(n3);
            }
        }
    }

    public void setItemGravity(int n3) {
        this.J = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemGravity(n3);
            }
        }
    }

    public void setItemIconGravity(int n3) {
        this.h = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemIconGravity(n3);
            }
        }
    }

    public void setItemIconSize(int n3) {
        this.m = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setIconSize(n3);
            }
        }
    }

    public void setItemOnTouchListener(int n3, View.OnTouchListener onTouchListener) {
        if (onTouchListener == null) {
            this.f.remove(n3);
        } else {
            this.f.put(n3, (Object)onTouchListener);
        }
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView) || g3.getItemData() == null || g3.getItemData().getItemId() != n3) continue;
                ((NavigationBarItemView)g3).setOnTouchListener(onTouchListener);
            }
        }
    }

    public void setItemPaddingBottom(int n3) {
        this.z = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemPaddingBottom(this.z);
            }
        }
    }

    public void setItemPaddingTop(int n3) {
        this.y = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemPaddingTop(n3);
            }
        }
    }

    public void setItemRippleColor(ColorStateList colorStateList) {
        this.v = colorStateList;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setItemRippleColor(colorStateList);
            }
        }
    }

    public void setItemTextAppearanceActive(int n3) {
        this.q = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setTextAppearanceActive(n3);
            }
        }
    }

    public void setItemTextAppearanceActiveBoldEnabled(boolean bl) {
        this.t = bl;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setTextAppearanceActiveBoldEnabled(bl);
            }
        }
    }

    public void setItemTextAppearanceInactive(int n3) {
        this.p = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setTextAppearanceInactive(n3);
            }
        }
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.n = colorStateList;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setTextColor(colorStateList);
            }
        }
    }

    public void setLabelFontScalingEnabled(boolean bl) {
        this.Q = bl;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setLabelFontScalingEnabled(bl);
            }
        }
    }

    public void setLabelMaxLines(int n3) {
        this.R = n3;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setLabelMaxLines(n3);
            }
        }
    }

    public void setLabelVisibilityMode(int n3) {
        this.g = n3;
    }

    public void setMeasurePaddingFromLabelBaseline(boolean bl) {
        this.P = bl;
        g[] gArray = this.i;
        if (gArray != null) {
            for (g g3 : gArray) {
                if (!(g3 instanceof NavigationBarItemView)) continue;
                ((NavigationBarItemView)g3).setMeasureBottomPaddingFromLabelBaseline(bl);
            }
        }
    }

    public void setPresenter(NavigationBarPresenter navigationBarPresenter) {
        this.N = navigationBarPresenter;
    }

    public void setSubmenuDividersEnabled(boolean bl) {
        if (this.W != bl) {
            this.W = bl;
            g[] gArray = this.i;
            if (gArray != null) {
                for (g g3 : gArray) {
                    if (!(g3 instanceof NavigationBarDividerView)) continue;
                    ((NavigationBarDividerView)g3).setDividersEnabled(bl);
                }
            }
        }
    }
}

