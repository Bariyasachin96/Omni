/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.text.TextUtils$TruncateAt
 *  android.util.AttributeSet
 *  android.view.ContextThemeWrapper
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.g0;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.n0;
import androidx.appcompat.widget.o0;
import androidx.appcompat.widget.p0;
import androidx.appcompat.widget.q0;
import androidx.appcompat.widget.r0;
import androidx.appcompat.widget.t0;
import androidx.appcompat.widget.u;
import androidx.customview.view.AbsSavedState;
import c.a;
import c.j;
import h.c;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import o0.s;
import o0.v;
import o0.w;
import o0.x0;
import o0.y;

public class Toolbar
extends ViewGroup
implements v {
    public CharSequence A;
    public ColorStateList B;
    public ColorStateList C;
    public boolean D;
    public boolean E;
    public final ArrayList F = new ArrayList();
    public final ArrayList G = new ArrayList();
    public final int[] H = new int[2];
    public final w I = new w(new o0(this));
    public ArrayList J = new ArrayList();
    public final ActionMenuView.d K = new ActionMenuView.d(this){
        public final Toolbar a;
        {
            this.a = toolbar;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (this.a.I.d(menuItem)) {
                return true;
            }
            this.a.getClass();
            return false;
        }
    };
    public q0 L;
    public ActionMenuPresenter M;
    public f N;
    public i.a O;
    public e.a P;
    public boolean Q;
    public OnBackInvokedCallback R;
    public OnBackInvokedDispatcher S;
    public boolean T;
    public final Runnable U = new Runnable(this){
        public final Toolbar c;
        {
            this.c = toolbar;
        }

        @Override
        public void run() {
            this.c.N();
        }
    };
    public ActionMenuView c;
    public TextView d;
    public TextView e;
    public ImageButton f;
    public ImageView g;
    public Drawable h;
    public CharSequence i;
    public ImageButton j;
    public View k;
    public Context l;
    public int m;
    public int n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public g0 v;
    public int w;
    public int x;
    public int y = 8388627;
    public CharSequence z;

    public Toolbar(Context context) {
        this(context, null);
    }

    public Toolbar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, a.toolbarStyle);
    }

    public Toolbar(Context object, AttributeSet attributeSet, int n3) {
        super(object, attributeSet, n3);
        Object object2 = this.getContext();
        int[] nArray = c.j.Toolbar;
        object2 = m0.v((Context)object2, attributeSet, nArray, n3, 0);
        x0.f0((View)this, object, nArray, attributeSet, ((m0)object2).r(), n3, 0);
        this.n = ((m0)object2).n(c.j.Toolbar_titleTextAppearance, 0);
        this.o = ((m0)object2).n(c.j.Toolbar_subtitleTextAppearance, 0);
        this.y = ((m0)object2).l(c.j.Toolbar_android_gravity, this.y);
        this.p = ((m0)object2).l(c.j.Toolbar_buttonGravity, 48);
        int n4 = ((m0)object2).e(c.j.Toolbar_titleMargin, 0);
        int n5 = c.j.Toolbar_titleMargins;
        n3 = n4;
        if (((m0)object2).s(n5)) {
            n3 = ((m0)object2).e(n5, n4);
        }
        this.u = n3;
        this.t = n3;
        this.s = n3;
        this.r = n3;
        n3 = ((m0)object2).e(c.j.Toolbar_titleMarginStart, -1);
        if (n3 >= 0) {
            this.r = n3;
        }
        if ((n3 = ((m0)object2).e(c.j.Toolbar_titleMarginEnd, -1)) >= 0) {
            this.s = n3;
        }
        if ((n3 = ((m0)object2).e(c.j.Toolbar_titleMarginTop, -1)) >= 0) {
            this.t = n3;
        }
        if ((n3 = ((m0)object2).e(c.j.Toolbar_titleMarginBottom, -1)) >= 0) {
            this.u = n3;
        }
        this.q = ((m0)object2).f(c.j.Toolbar_maxButtonHeight, -1);
        n4 = ((m0)object2).e(c.j.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        n3 = ((m0)object2).e(c.j.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int n6 = ((m0)object2).f(c.j.Toolbar_contentInsetLeft, 0);
        n5 = ((m0)object2).f(c.j.Toolbar_contentInsetRight, 0);
        this.i();
        this.v.e(n6, n5);
        if (n4 != Integer.MIN_VALUE || n3 != Integer.MIN_VALUE) {
            this.v.g(n4, n3);
        }
        this.w = ((m0)object2).e(c.j.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.x = ((m0)object2).e(c.j.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.h = ((m0)object2).g(c.j.Toolbar_collapseIcon);
        this.i = ((m0)object2).p(c.j.Toolbar_collapseContentDescription);
        object = ((m0)object2).p(c.j.Toolbar_title);
        if (!TextUtils.isEmpty((CharSequence)object)) {
            this.setTitle((CharSequence)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = ((m0)object2).p(c.j.Toolbar_subtitle)))) {
            this.setSubtitle((CharSequence)object);
        }
        this.l = this.getContext();
        this.setPopupTheme(((m0)object2).n(c.j.Toolbar_popupTheme, 0));
        object = ((m0)object2).g(c.j.Toolbar_navigationIcon);
        if (object != null) {
            this.setNavigationIcon((Drawable)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = ((m0)object2).p(c.j.Toolbar_navigationContentDescription)))) {
            this.setNavigationContentDescription((CharSequence)object);
        }
        if ((object = ((m0)object2).g(c.j.Toolbar_logo)) != null) {
            this.setLogo((Drawable)object);
        }
        if (!TextUtils.isEmpty((CharSequence)(object = ((m0)object2).p(c.j.Toolbar_logoDescription)))) {
            this.setLogoDescription((CharSequence)object);
        }
        if (((m0)object2).s(n3 = c.j.Toolbar_titleTextColor)) {
            this.setTitleTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = c.j.Toolbar_subtitleTextColor)) {
            this.setSubtitleTextColor(((m0)object2).c(n3));
        }
        if (((m0)object2).s(n3 = c.j.Toolbar_menu)) {
            this.z(((m0)object2).n(n3, 0));
        }
        ((m0)object2).x();
    }

    private ArrayList<MenuItem> getCurrentMenuItems() {
        ArrayList<MenuItem> arrayList = new ArrayList<MenuItem>();
        Menu menu = this.getMenu();
        for (int i3 = 0; i3 < menu.size(); ++i3) {
            arrayList.add(menu.getItem(i3));
        }
        return arrayList;
    }

    private MenuInflater getMenuInflater() {
        return new h.g(this.getContext());
    }

    public void A() {
        ArrayList arrayList = this.J;
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = arrayList.get(i3);
            object = (MenuItem)object;
            this.getMenu().removeItem(object.getItemId());
        }
        this.I();
    }

    public final boolean B(View view) {
        return view.getParent() == this || this.G.contains(view);
        {
        }
    }

    public boolean C() {
        ActionMenuView actionMenuView = this.c;
        return actionMenuView != null && actionMenuView.G();
    }

    public boolean D() {
        ActionMenuView actionMenuView = this.c;
        return actionMenuView != null && actionMenuView.H();
    }

    public final int E(View view, int n3, int[] nArray, int n4) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n5 = layoutParams.leftMargin - nArray[0];
        n3 += Math.max(0, n5);
        nArray[0] = Math.max(0, -n5);
        n4 = this.s(view, n4);
        n5 = view.getMeasuredWidth();
        view.layout(n3, n4, n3 + n5, view.getMeasuredHeight() + n4);
        return n3 + (n5 + layoutParams.rightMargin);
    }

    public final int F(View view, int n3, int[] nArray, int n4) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n5 = layoutParams.rightMargin - nArray[1];
        n3 -= Math.max(0, n5);
        nArray[1] = Math.max(0, -n5);
        n5 = this.s(view, n4);
        n4 = view.getMeasuredWidth();
        view.layout(n3 - n4, n5, n3, view.getMeasuredHeight() + n5);
        return n3 - (n4 + layoutParams.leftMargin);
    }

    public final int G(View view, int n3, int n4, int n5, int n6, int[] nArray) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n7 = marginLayoutParams.leftMargin - nArray[0];
        int n8 = marginLayoutParams.rightMargin - nArray[1];
        int n9 = Math.max(0, n7) + Math.max(0, n8);
        nArray[0] = Math.max(0, -n7);
        nArray[1] = Math.max(0, -n8);
        view.measure(ViewGroup.getChildMeasureSpec((int)n3, (int)(this.getPaddingLeft() + this.getPaddingRight() + n9 + n4), (int)marginLayoutParams.width), ViewGroup.getChildMeasureSpec((int)n5, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n6), (int)marginLayoutParams.height));
        return view.getMeasuredWidth() + n9;
    }

    public final void H(View view, int n3, int n4, int n5, int n6, int n7) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        int n8 = ViewGroup.getChildMeasureSpec((int)n3, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n4), (int)marginLayoutParams.width);
        n4 = ViewGroup.getChildMeasureSpec((int)n5, (int)(this.getPaddingTop() + this.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + n6), (int)marginLayoutParams.height);
        n5 = View.MeasureSpec.getMode((int)n4);
        n3 = n4;
        if (n5 != 0x40000000) {
            n3 = n4;
            if (n7 >= 0) {
                n3 = n7;
                if (n5 != 0) {
                    n3 = Math.min(View.MeasureSpec.getSize((int)n4), n7);
                }
                n3 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)0x40000000);
            }
        }
        view.measure(n8, n3);
    }

    public final void I() {
        Object object = this.getMenu();
        ArrayList<MenuItem> arrayList = this.getCurrentMenuItems();
        this.I.b((Menu)object, this.getMenuInflater());
        object = this.getCurrentMenuItems();
        ((ArrayList)object).removeAll(arrayList);
        this.J = object;
    }

    public final void J() {
        this.removeCallbacks(this.U);
        this.post(this.U);
    }

    public void K() {
        for (int i3 = this.getChildCount() - 1; i3 >= 0; --i3) {
            View view = this.getChildAt(i3);
            if (((LayoutParams)view.getLayoutParams()).b == 2 || view == this.c) continue;
            this.removeViewAt(i3);
            this.G.add(view);
        }
    }

    public final boolean L() {
        if (!this.Q) {
            return false;
        }
        int n3 = this.getChildCount();
        for (int i3 = 0; i3 < n3; ++i3) {
            View view = this.getChildAt(i3);
            if (!this.M(view) || view.getMeasuredWidth() <= 0 || view.getMeasuredHeight() <= 0) continue;
            return false;
        }
        return true;
    }

    public final boolean M(View view) {
        return view != null && view.getParent() == this && view.getVisibility() != 8;
    }

    public boolean N() {
        ActionMenuView actionMenuView = this.c;
        return actionMenuView != null && actionMenuView.M();
    }

    public void O() {
        if (Build.VERSION.SDK_INT >= 33) {
            OnBackInvokedDispatcher onBackInvokedDispatcher = androidx.appcompat.widget.Toolbar$e.a((View)this);
            boolean bl = this.x() && onBackInvokedDispatcher != null && this.isAttachedToWindow() && this.T;
            if (bl && this.S == null) {
                if (this.R == null) {
                    this.R = androidx.appcompat.widget.Toolbar$e.b(new n0(this));
                }
                androidx.appcompat.widget.Toolbar$e.c(onBackInvokedDispatcher, this.R);
                this.S = onBackInvokedDispatcher;
                return;
            }
            if (!bl && (onBackInvokedDispatcher = this.S) != null) {
                androidx.appcompat.widget.Toolbar$e.d(onBackInvokedDispatcher, this.R);
                this.S = null;
            }
        }
    }

    public void a() {
        for (int i3 = this.G.size() - 1; i3 >= 0; --i3) {
            this.addView((View)this.G.get(i3));
        }
        this.G.clear();
    }

    public final void b(List list, int n3) {
        int n4 = this.getLayoutDirection();
        int n5 = 0;
        n4 = n4 == 1 ? 1 : 0;
        int n6 = this.getChildCount();
        int n7 = o0.s.b(n3, this.getLayoutDirection());
        list.clear();
        if (n4 != 0) {
            for (n3 = n6 - 1; n3 >= 0; --n3) {
                View view = this.getChildAt(n3);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.b != 0 || !this.M(view) || this.r(layoutParams.a) != n7) continue;
                list.add(view);
            }
        } else {
            for (n3 = n5; n3 < n6; ++n3) {
                View view = this.getChildAt(n3);
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.b != 0 || !this.M(view) || this.r(layoutParams.a) != n7) continue;
                list.add(view);
            }
        }
    }

    public final void c(View view, boolean bl) {
        Object object = view.getLayoutParams();
        object = object == null ? this.o() : (!this.checkLayoutParams((ViewGroup.LayoutParams)object) ? this.q((ViewGroup.LayoutParams)object) : (LayoutParams)((Object)object));
        object.b = 1;
        if (bl && this.k != null) {
            view.setLayoutParams(object);
            this.G.add(view);
            return;
        }
        this.addView(view, (ViewGroup.LayoutParams)object);
    }

    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && layoutParams instanceof LayoutParams;
    }

    public boolean d() {
        ActionMenuView actionMenuView;
        return this.getVisibility() == 0 && (actionMenuView = this.c) != null && actionMenuView.I();
    }

    public void e() {
        Object object = this.N;
        object = object == null ? null : ((f)object).d;
        if (object != null) {
            ((androidx.appcompat.view.menu.g)object).collapseActionView();
        }
    }

    public void f() {
        ActionMenuView actionMenuView = this.c;
        if (actionMenuView != null) {
            actionMenuView.z();
        }
    }

    @Override
    public void g(y y3) {
        this.I.a(y3);
    }

    public CharSequence getCollapseContentDescription() {
        ImageButton imageButton = this.j;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getCollapseIcon() {
        ImageButton imageButton = this.j;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public int getContentInsetEnd() {
        g0 g02 = this.v;
        if (g02 != null) {
            return g02.a();
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        int n3 = this.x;
        if (n3 != Integer.MIN_VALUE) {
            return n3;
        }
        return this.getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        g0 g02 = this.v;
        if (g02 != null) {
            return g02.b();
        }
        return 0;
    }

    public int getContentInsetRight() {
        g0 g02 = this.v;
        if (g02 != null) {
            return g02.c();
        }
        return 0;
    }

    public int getContentInsetStart() {
        g0 g02 = this.v;
        if (g02 != null) {
            return g02.d();
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int n3 = this.w;
        if (n3 != Integer.MIN_VALUE) {
            return n3;
        }
        return this.getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        Object object = this.c;
        if (object != null && (object = ((ActionMenuView)object).L()) != null && ((androidx.appcompat.view.menu.e)object).hasVisibleItems()) {
            return Math.max(this.getContentInsetEnd(), Math.max(this.x, 0));
        }
        return this.getContentInsetEnd();
    }

    public int getCurrentContentInsetLeft() {
        if (this.getLayoutDirection() == 1) {
            return this.getCurrentContentInsetEnd();
        }
        return this.getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        if (this.getLayoutDirection() == 1) {
            return this.getCurrentContentInsetStart();
        }
        return this.getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        if (this.getNavigationIcon() != null) {
            return Math.max(this.getContentInsetStart(), Math.max(this.w, 0));
        }
        return this.getContentInsetStart();
    }

    public Drawable getLogo() {
        ImageView imageView = this.g;
        if (imageView != null) {
            return imageView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        ImageView imageView = this.g;
        if (imageView != null) {
            return imageView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        this.k();
        return this.c.getMenu();
    }

    public View getNavButtonView() {
        return this.f;
    }

    public CharSequence getNavigationContentDescription() {
        ImageButton imageButton = this.f;
        if (imageButton != null) {
            return imageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getNavigationIcon() {
        ImageButton imageButton = this.f;
        if (imageButton != null) {
            return imageButton.getDrawable();
        }
        return null;
    }

    public ActionMenuPresenter getOuterActionMenuPresenter() {
        return this.M;
    }

    public Drawable getOverflowIcon() {
        this.k();
        return this.c.getOverflowIcon();
    }

    public Context getPopupContext() {
        return this.l;
    }

    public int getPopupTheme() {
        return this.m;
    }

    public CharSequence getSubtitle() {
        return this.A;
    }

    public final TextView getSubtitleTextView() {
        return this.e;
    }

    public CharSequence getTitle() {
        return this.z;
    }

    public int getTitleMarginBottom() {
        return this.u;
    }

    public int getTitleMarginEnd() {
        return this.s;
    }

    public int getTitleMarginStart() {
        return this.r;
    }

    public int getTitleMarginTop() {
        return this.t;
    }

    public final TextView getTitleTextView() {
        return this.d;
    }

    public u getWrapper() {
        if (this.L == null) {
            this.L = new q0(this, true);
        }
        return this.L;
    }

    public void h() {
        if (this.j == null) {
            Object object = new AppCompatImageButton(this.getContext(), null, a.toolbarNavigationButtonStyle);
            this.j = object;
            object.setImageDrawable(this.h);
            this.j.setContentDescription(this.i);
            object = this.o();
            ((ActionBar.LayoutParams)((Object)object)).a = this.p & 0x70 | 0x800003;
            ((LayoutParams)((Object)object)).b = 2;
            this.j.setLayoutParams((ViewGroup.LayoutParams)object);
            this.j.setOnClickListener(new View.OnClickListener(this){
                public final Toolbar c;
                {
                    this.c = toolbar;
                }

                public void onClick(View view) {
                    this.c.e();
                }
            });
        }
    }

    public final void i() {
        if (this.v == null) {
            this.v = new g0();
        }
    }

    public final void j() {
        if (this.g == null) {
            this.g = new AppCompatImageView(this.getContext());
        }
    }

    public final void k() {
        this.l();
        if (this.c.L() == null) {
            androidx.appcompat.view.menu.e e3 = (androidx.appcompat.view.menu.e)this.c.getMenu();
            if (this.N == null) {
                this.N = new f(this);
            }
            this.c.setExpandedActionViewsExclusive(true);
            e3.c(this.N, this.l);
            this.O();
        }
    }

    public final void l() {
        if (this.c == null) {
            Object object;
            this.c = object = new ActionMenuView(this.getContext());
            object.setPopupTheme(this.m);
            this.c.setOnMenuItemClickListener(this.K);
            this.c.setMenuCallbacks(this.O, new e.a(this){
                public final Toolbar c;
                {
                    this.c = toolbar;
                }

                @Override
                public boolean a(androidx.appcompat.view.menu.e e3, MenuItem menuItem) {
                    e.a a4 = this.c.P;
                    return a4 != null && a4.a(e3, menuItem);
                }

                @Override
                public void b(androidx.appcompat.view.menu.e e3) {
                    e.a a4;
                    if (!this.c.c.H()) {
                        this.c.I.e(e3);
                    }
                    if ((a4 = this.c.P) != null) {
                        a4.b(e3);
                    }
                }
            });
            object = this.o();
            ((ActionBar.LayoutParams)((Object)object)).a = this.p & 0x70 | 0x800005;
            this.c.setLayoutParams((ViewGroup.LayoutParams)object);
            this.c((View)this.c, false);
        }
    }

    public final void m() {
        if (this.f == null) {
            this.f = new AppCompatImageButton(this.getContext(), null, a.toolbarNavigationButtonStyle);
            LayoutParams layoutParams = this.o();
            layoutParams.a = this.p & 0x70 | 0x800003;
            this.f.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
    }

    @Override
    public void n(y y3) {
        this.I.f(y3);
    }

    public LayoutParams o() {
        return new LayoutParams(-2, -2);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.O();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.U);
        this.O();
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionMasked();
        if (n3 == 9) {
            this.E = false;
        }
        if (!this.E) {
            boolean bl = super.onHoverEvent(motionEvent);
            if (n3 == 9 && !bl) {
                this.E = true;
            }
        }
        if (n3 == 10 || n3 == 3) {
            this.E = false;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void onLayout(boolean var1_1, int var2_2, int var3_3, int var4_4, int var5_5) {
        block44: {
            var8_6 = this.getLayoutDirection() == 1 ? 1 : 0;
            var12_7 = this.getWidth();
            var14_8 = this.getHeight();
            var7_9 = this.getPaddingLeft();
            var11_10 = this.getPaddingRight();
            var13_11 = this.getPaddingTop();
            var15_12 = this.getPaddingBottom();
            var9_13 = var12_7 - var11_10;
            var20_14 = this.H;
            var20_14[1] = 0;
            var20_14[0] = 0;
            var2_2 = x0.z((View)this);
            var6_15 = var2_2 >= 0 ? Math.min(var2_2, var5_5 - var3_3) : 0;
            if (!this.M((View)this.f)) ** GOTO lbl23
            if (var8_6 != 0) {
                var5_5 = this.F((View)this.f, var9_13, var20_14, var6_15);
                var4_4 = var7_9;
            } else {
                var4_4 = this.E((View)this.f, var7_9, var20_14, var6_15);
lbl20:
                // 2 sources

                while (true) {
                    var5_5 = var9_13;
                    break block44;
                    break;
                }
lbl23:
                // 1 sources

                var4_4 = var7_9;
                ** continue;
            }
        }
        var3_3 = var4_4;
        var2_2 = var5_5;
        if (this.M((View)this.j)) {
            if (var8_6 != 0) {
                var2_2 = this.F((View)this.j, var5_5, var20_14, var6_15);
                var3_3 = var4_4;
            } else {
                var3_3 = this.E((View)this.j, var4_4, var20_14, var6_15);
                var2_2 = var5_5;
            }
        }
        var5_5 = var3_3;
        var4_4 = var2_2;
        if (this.M((View)this.c)) {
            if (var8_6 != 0) {
                var5_5 = this.E((View)this.c, var3_3, var20_14, var6_15);
                var4_4 = var2_2;
            } else {
                var4_4 = this.F((View)this.c, var2_2, var20_14, var6_15);
                var5_5 = var3_3;
            }
        }
        var3_3 = this.getCurrentContentInsetLeft();
        var2_2 = this.getCurrentContentInsetRight();
        var20_14[0] = Math.max(0, var3_3 - var5_5);
        var20_14[1] = Math.max(0, var2_2 - (var9_13 - var4_4));
        var3_3 = Math.max(var5_5, var3_3);
        var4_4 = Math.min(var4_4, var9_13 - var2_2);
        var2_2 = var3_3;
        var5_5 = var4_4;
        if (this.M(this.k)) {
            if (var8_6 != 0) {
                var5_5 = this.F(this.k, var4_4, var20_14, var6_15);
                var2_2 = var3_3;
            } else {
                var2_2 = this.E(this.k, var3_3, var20_14, var6_15);
                var5_5 = var4_4;
            }
        }
        var4_4 = var2_2;
        var3_3 = var5_5;
        if (this.M((View)this.g)) {
            if (var8_6 != 0) {
                var3_3 = this.F((View)this.g, var5_5, var20_14, var6_15);
                var4_4 = var2_2;
            } else {
                var4_4 = this.E((View)this.g, var2_2, var20_14, var6_15);
                var3_3 = var5_5;
            }
        }
        var1_1 = this.M((View)this.d);
        var17_16 = this.M((View)this.e);
        if (var1_1) {
            var18_17 = (LayoutParams)this.d.getLayoutParams();
            var5_5 = var18_17.topMargin;
            var2_2 = this.d.getMeasuredHeight();
            var2_2 = var18_17.bottomMargin + (var5_5 + var2_2);
        } else {
            var2_2 = 0;
        }
        if (var17_16) {
            var18_17 = (LayoutParams)this.e.getLayoutParams();
            var9_13 = var2_2 + (var18_17.topMargin + this.e.getMeasuredHeight() + var18_17.bottomMargin);
        } else {
            var9_13 = var2_2;
        }
        if (var1_1) ** GOTO lbl-1000
        var2_2 = var4_4;
        var5_5 = var3_3;
        if (!var17_16) lbl-1000:
        // 2 sources

        {
            while (true) {
                var4_4 = var5_5;
                break;
            }
        } else lbl-1000:
        // 2 sources

        {
            var18_17 = var1_1 != false ? this.d : this.e;
            var19_20 /* !! */  = var17_16 != false ? this.e : this.d;
            var18_17 = (LayoutParams)var18_17.getLayoutParams();
            var19_20 /* !! */  = (LayoutParams)var19_20 /* !! */ .getLayoutParams();
            var5_5 = var1_1 != false && this.d.getMeasuredWidth() > 0 || var17_16 != false && this.e.getMeasuredWidth() > 0 ? 1 : 0;
            var2_2 = this.y & 112;
            if (var2_2 != 48) {
                if (var2_2 != 80) {
                    var10_18 = (var14_8 - var13_11 - var15_12 - var9_13) / 2;
                    var2_2 = var18_17.topMargin;
                    var16_19 = this.t;
                    if (var10_18 < var2_2 + var16_19) {
                        var2_2 += var16_19;
                    } else {
                        var9_13 = var14_8 - var15_12 - var9_13 - var10_18 - var13_11;
                        var14_8 = var18_17.bottomMargin;
                        var15_12 = this.u;
                        var2_2 = var10_18;
                        if (var9_13 < var14_8 + var15_12) {
                            var2_2 = Math.max(0, var10_18 - (var19_20 /* !! */ .bottomMargin + var15_12 - var9_13));
                        }
                    }
                    var2_2 = var13_11 + var2_2;
                } else {
                    var2_2 = var14_8 - var15_12 - var19_20 /* !! */ .bottomMargin - this.u - var9_13;
                }
            } else {
                var2_2 = this.getPaddingTop() + var18_17.topMargin + this.t;
            }
            if (var8_6 != 0) {
                var8_6 = var5_5 != 0 ? this.r : 0;
                var3_3 -= Math.max(0, var8_6 -= var20_14[1]);
                var20_14[1] = Math.max(0, -var8_6);
                if (var1_1) {
                    var18_17 = (LayoutParams)this.d.getLayoutParams();
                    var9_13 = var3_3 - this.d.getMeasuredWidth();
                    var8_6 = this.d.getMeasuredHeight() + var2_2;
                    this.d.layout(var9_13, var2_2, var3_3, var8_6);
                    var2_2 = var9_13 - this.s;
                    var8_6 += var18_17.bottomMargin;
                } else {
                    var9_13 = var3_3;
                    var8_6 = var2_2;
                    var2_2 = var9_13;
                }
                if (var17_16) {
                    var9_13 = var8_6 + ((LayoutParams)this.e.getLayoutParams()).topMargin;
                    var8_6 = this.e.getMeasuredWidth();
                    var10_18 = this.e.getMeasuredHeight();
                    this.e.layout(var3_3 - var8_6, var9_13, var3_3, var10_18 + var9_13);
                    var8_6 = var3_3 - this.s;
                } else {
                    var8_6 = var3_3;
                }
                if (var5_5 != 0) {
                    var3_3 = Math.min(var2_2, var8_6);
                }
                var2_2 = var4_4;
                var5_5 = var3_3;
                ** continue;
            }
            var8_6 = var5_5 != 0 ? this.r : 0;
            var4_4 += Math.max(0, var8_6 -= var20_14[0]);
            var20_14[0] = Math.max(0, -var8_6);
            if (var1_1) {
                var18_17 = (LayoutParams)this.d.getLayoutParams();
                var8_6 = this.d.getMeasuredWidth() + var4_4;
                var9_13 = this.d.getMeasuredHeight() + var2_2;
                this.d.layout(var4_4, var2_2, var8_6, var9_13);
                var8_6 += this.s;
                var2_2 = var9_13 + var18_17.bottomMargin;
            } else {
                var8_6 = var4_4;
            }
            if (var17_16) {
                var10_18 = this.e.getMeasuredWidth() + var4_4;
                var9_13 = this.e.getMeasuredHeight();
                this.e.layout(var4_4, var2_2 += ((LayoutParams)this.e.getLayoutParams()).topMargin, var10_18, var9_13 + var2_2);
                var9_13 = var10_18 + this.s;
            } else {
                var9_13 = var4_4;
            }
            var2_2 = var4_4;
            var4_4 = var3_3;
            if (var5_5 != 0) {
                var2_2 = Math.max(var8_6, var9_13);
                var4_4 = var3_3;
            }
        }
        var5_5 = 0;
        this.b(this.F, 3);
        var8_6 = this.F.size();
        for (var3_3 = 0; var3_3 < var8_6; ++var3_3) {
            var2_2 = this.E((View)this.F.get(var3_3), var2_2, var20_14, var6_15);
        }
        this.b(this.F, 5);
        var8_6 = this.F.size();
        for (var3_3 = 0; var3_3 < var8_6; ++var3_3) {
            var4_4 = this.F((View)this.F.get(var3_3), var4_4, var20_14, var6_15);
        }
        this.b(this.F, 1);
        var8_6 = this.w(this.F, var20_14);
        var3_3 = var7_9 + (var12_7 - var7_9 - var11_10) / 2 - var8_6 / 2;
        var7_9 = var8_6 + var3_3;
        if (var3_3 >= var2_2) {
            var2_2 = var7_9 > var4_4 ? var3_3 - (var7_9 - var4_4) : var3_3;
        }
        var4_4 = this.F.size();
        var3_3 = var2_2;
        for (var2_2 = var5_5; var2_2 < var4_4; ++var2_2) {
            var3_3 = this.E((View)this.F.get(var2_2), var3_3, var20_14, var6_15);
        }
        this.F.clear();
    }

    public void onMeasure(int n3, int n4) {
        int n5;
        int n6;
        int n7;
        int n8;
        int[] nArray = this.H;
        int n82 = t0.b((View)this);
        boolean bl = this.M((View)this.f);
        int n9 = 0;
        if (bl) {
            this.H((View)this.f, n3, 0, n4, 0, this.q);
            n8 = this.f.getMeasuredWidth() + this.u((View)this.f);
            n7 = Math.max(0, this.f.getMeasuredHeight() + this.v((View)this.f));
            n6 = View.combineMeasuredStates((int)0, (int)this.f.getMeasuredState());
        } else {
            n8 = 0;
            n6 = n7 = 0;
        }
        int n10 = n8;
        int n11 = n7;
        n8 = n6;
        if (this.M((View)this.j)) {
            this.H((View)this.j, n3, 0, n4, 0, this.q);
            n10 = this.j.getMeasuredWidth() + this.u((View)this.j);
            n11 = Math.max(n7, this.j.getMeasuredHeight() + this.v((View)this.j));
            n8 = View.combineMeasuredStates((int)n6, (int)this.j.getMeasuredState());
        }
        n6 = this.getCurrentContentInsetStart();
        n7 = Math.max(n6, n10);
        nArray[n82] = Math.max(0, n6 - n10);
        if (this.M((View)this.c)) {
            this.H((View)this.c, n3, n7, n4, 0, this.q);
            n6 = this.c.getMeasuredWidth() + this.u((View)this.c);
            n11 = Math.max(n11, this.c.getMeasuredHeight() + this.v((View)this.c));
            n8 = View.combineMeasuredStates((int)n8, (int)this.c.getMeasuredState());
        } else {
            n6 = 0;
        }
        n10 = this.getCurrentContentInsetEnd();
        int n12 = n7 + Math.max(n10, n6);
        nArray[n82 ^ 1] = Math.max(0, n10 - n6);
        if (this.M(this.k)) {
            n12 += this.G(this.k, n3, n12, n4, 0, nArray);
            n10 = Math.max(n11, this.k.getMeasuredHeight() + this.v(this.k));
            n7 = View.combineMeasuredStates((int)n8, (int)this.k.getMeasuredState());
        } else {
            n7 = n8;
            n10 = n11;
        }
        n8 = n12;
        n6 = n10;
        n11 = n7;
        if (this.M((View)this.g)) {
            n8 = n12 + this.G((View)this.g, n3, n12, n4, 0, nArray);
            n6 = Math.max(n10, this.g.getMeasuredHeight() + this.v((View)this.g));
            n11 = View.combineMeasuredStates((int)n7, (int)this.g.getMeasuredState());
        }
        n12 = this.getChildCount();
        n7 = 0;
        n10 = n6;
        for (n6 = n7; n6 < n12; ++n6) {
            View view = this.getChildAt(n6);
            if (((LayoutParams)view.getLayoutParams()).b != 0 || !this.M(view)) continue;
            n8 += this.G(view, n3, n8, n4, 0, nArray);
            n10 = Math.max(n10, view.getMeasuredHeight() + this.v(view));
            n11 = View.combineMeasuredStates((int)n11, (int)view.getMeasuredState());
        }
        int n13 = this.t + this.u;
        int n14 = this.r + this.s;
        if (this.M((View)this.d)) {
            this.G((View)this.d, n3, n8 + n14, n4, n13, nArray);
            n7 = this.d.getMeasuredWidth();
            int n15 = this.u((View)this.d);
            n6 = this.d.getMeasuredHeight();
            n12 = this.v((View)this.d);
            n11 = View.combineMeasuredStates((int)n11, (int)this.d.getMeasuredState());
            n7 += n15;
            n6 += n12;
        } else {
            n7 = 0;
            n6 = 0;
        }
        int n16 = n7;
        int n17 = n6;
        n12 = n11;
        if (this.M((View)this.e)) {
            n5 = Math.max(n7, this.G((View)this.e, n3, n8 + n14, n4, n13 + n6, nArray));
            n17 = n6 + (this.e.getMeasuredHeight() + this.v((View)this.e));
            n12 = View.combineMeasuredStates((int)n11, (int)this.e.getMeasuredState());
        }
        n6 = Math.max(n10, n17);
        n10 = this.getPaddingLeft();
        n17 = this.getPaddingRight();
        n7 = this.getPaddingTop();
        n11 = this.getPaddingBottom();
        n8 = View.resolveSizeAndState((int)Math.max(n8 + n5 + (n10 + n17), this.getSuggestedMinimumWidth()), (int)n3, (int)(0xFF000000 & n12));
        n3 = View.resolveSizeAndState((int)Math.max(n6 + (n7 + n11), this.getSuggestedMinimumHeight()), (int)n4, (int)(n12 << 16));
        if (this.L()) {
            n3 = n9;
        }
        this.setMeasuredDimension(n8, n3);
    }

    public void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState((Parcelable)object);
            return;
        }
        SavedState savedState = (SavedState)object;
        super.onRestoreInstanceState(savedState.o());
        object = this.c;
        object = object != null ? ((ActionMenuView)object).L() : null;
        int n3 = savedState.e;
        if (n3 != 0 && this.N != null && object != null && (object = object.findItem(n3)) != null) {
            object.expandActionView();
        }
        if (savedState.f) {
            this.J();
        }
    }

    public void onRtlPropertiesChanged(int n3) {
        super.onRtlPropertiesChanged(n3);
        this.i();
        g0 g02 = this.v;
        boolean bl = true;
        if (n3 != 1) {
            bl = false;
        }
        g02.f(bl);
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Object object = this.N;
        if (object != null && (object = ((f)object).d) != null) {
            savedState.e = ((androidx.appcompat.view.menu.g)object).getItemId();
        }
        savedState.f = this.D();
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int n3 = motionEvent.getActionMasked();
        if (n3 == 0) {
            this.D = false;
        }
        if (!this.D) {
            boolean bl = super.onTouchEvent(motionEvent);
            if (n3 == 0 && !bl) {
                this.D = true;
            }
        }
        if (n3 == 1 || n3 == 3) {
            this.D = false;
        }
        return true;
    }

    public LayoutParams p(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    public LayoutParams q(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ActionBar.LayoutParams) {
            return new LayoutParams((ActionBar.LayoutParams)layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final int r(int n3) {
        int n4 = this.getLayoutDirection();
        if ((n3 = o0.s.b(n3, n4) & 7) != 1 && n3 != 3 && n3 != 5) {
            if (n4 == 1) {
                return 5;
            }
            return 3;
        }
        return n3;
    }

    public final int s(View view, int n3) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        int n4 = view.getMeasuredHeight();
        n3 = n3 > 0 ? (n4 - n3) / 2 : 0;
        int n5 = this.t(layoutParams.a);
        if (n5 != 48) {
            if (n5 != 80) {
                int n6 = this.getPaddingTop();
                int n7 = this.getPaddingBottom();
                int n8 = this.getHeight();
                n5 = (n8 - n6 - n7 - n4) / 2;
                if (n5 >= (n3 = layoutParams.topMargin)) {
                    n4 = n8 - n7 - n4 - n5 - n6;
                    n8 = layoutParams.bottomMargin;
                    n3 = n5;
                    if (n4 < n8) {
                        n3 = Math.max(0, n5 - (n8 - n4));
                    }
                }
                return n6 + n3;
            }
            return this.getHeight() - this.getPaddingBottom() - n4 - layoutParams.bottomMargin - n3;
        }
        return this.getPaddingTop() - n3;
    }

    public void setBackInvokedCallbackEnabled(boolean bl) {
        if (this.T != bl) {
            this.T = bl;
            this.O();
        }
    }

    public void setCollapseContentDescription(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getContext().getText(n3) : null;
        this.setCollapseContentDescription(charSequence);
    }

    public void setCollapseContentDescription(CharSequence charSequence) {
        ImageButton imageButton;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.h();
        }
        if ((imageButton = this.j) != null) {
            imageButton.setContentDescription(charSequence);
        }
    }

    public void setCollapseIcon(int n3) {
        this.setCollapseIcon(d.a.b(this.getContext(), n3));
    }

    public void setCollapseIcon(Drawable drawable) {
        if (drawable != null) {
            this.h();
            this.j.setImageDrawable(drawable);
            return;
        }
        drawable = this.j;
        if (drawable != null) {
            drawable.setImageDrawable(this.h);
        }
    }

    public void setCollapsible(boolean bl) {
        this.Q = bl;
        this.requestLayout();
    }

    public void setContentInsetEndWithActions(int n3) {
        int n4 = n3;
        if (n3 < 0) {
            n4 = Integer.MIN_VALUE;
        }
        if (n4 != this.x) {
            this.x = n4;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int n3) {
        int n4 = n3;
        if (n3 < 0) {
            n4 = Integer.MIN_VALUE;
        }
        if (n4 != this.w) {
            this.w = n4;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }

    public void setContentInsetsAbsolute(int n3, int n4) {
        this.i();
        this.v.e(n3, n4);
    }

    public void setContentInsetsRelative(int n3, int n4) {
        this.i();
        this.v.g(n3, n4);
    }

    public void setLogo(int n3) {
        this.setLogo(d.a.b(this.getContext(), n3));
    }

    public void setLogo(Drawable drawable) {
        ImageView imageView;
        if (drawable != null) {
            this.j();
            if (!this.B((View)this.g)) {
                this.c((View)this.g, true);
            }
        } else {
            imageView = this.g;
            if (imageView != null && this.B((View)imageView)) {
                this.removeView((View)this.g);
                this.G.remove(this.g);
            }
        }
        if ((imageView = this.g) != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(int n3) {
        this.setLogoDescription(this.getContext().getText(n3));
    }

    public void setLogoDescription(CharSequence charSequence) {
        ImageView imageView;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.j();
        }
        if ((imageView = this.g) != null) {
            imageView.setContentDescription(charSequence);
        }
    }

    public void setMenu(androidx.appcompat.view.menu.e e3, ActionMenuPresenter actionMenuPresenter) {
        androidx.appcompat.view.menu.e e4;
        block8: {
            block7: {
                if (e3 == null && this.c == null) break block7;
                this.l();
                e4 = this.c.L();
                if (e4 != e3) break block8;
            }
            return;
        }
        if (e4 != null) {
            e4.R(this.M);
            e4.R(this.N);
        }
        if (this.N == null) {
            this.N = new f(this);
        }
        actionMenuPresenter.J(true);
        if (e3 != null) {
            e3.c(actionMenuPresenter, this.l);
            e3.c(this.N, this.l);
        } else {
            actionMenuPresenter.b(this.l, null);
            this.N.b(this.l, null);
            actionMenuPresenter.g(true);
            this.N.g(true);
        }
        this.c.setPopupTheme(this.m);
        this.c.setPresenter(actionMenuPresenter);
        this.M = actionMenuPresenter;
        this.O();
    }

    public void setMenuCallbacks(i.a a4, e.a a5) {
        this.O = a4;
        this.P = a5;
        ActionMenuView actionMenuView = this.c;
        if (actionMenuView != null) {
            actionMenuView.setMenuCallbacks(a4, a5);
        }
    }

    public void setNavigationContentDescription(int n3) {
        CharSequence charSequence = n3 != 0 ? this.getContext().getText(n3) : null;
        this.setNavigationContentDescription(charSequence);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        ImageButton imageButton;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            this.m();
        }
        if ((imageButton = this.f) != null) {
            imageButton.setContentDescription(charSequence);
            r0.a((View)this.f, charSequence);
        }
    }

    public void setNavigationIcon(int n3) {
        this.setNavigationIcon(d.a.b(this.getContext(), n3));
    }

    public void setNavigationIcon(Drawable drawable) {
        ImageButton imageButton;
        if (drawable != null) {
            this.m();
            if (!this.B((View)this.f)) {
                this.c((View)this.f, true);
            }
        } else {
            imageButton = this.f;
            if (imageButton != null && this.B((View)imageButton)) {
                this.removeView((View)this.f);
                this.G.remove(this.f);
            }
        }
        if ((imageButton = this.f) != null) {
            imageButton.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        this.m();
        this.f.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(g g3) {
    }

    public void setOverflowIcon(Drawable drawable) {
        this.k();
        this.c.setOverflowIcon(drawable);
    }

    public void setPopupTheme(int n3) {
        if (this.m != n3) {
            this.m = n3;
            if (n3 == 0) {
                this.l = this.getContext();
                return;
            }
            this.l = new ContextThemeWrapper(this.getContext(), n3);
        }
    }

    public void setSubtitle(int n3) {
        this.setSubtitle(this.getContext().getText(n3));
    }

    public void setSubtitle(CharSequence charSequence) {
        TextView textView;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.e == null) {
                Context context = this.getContext();
                this.e = textView = new AppCompatTextView(context);
                textView.setSingleLine();
                this.e.setEllipsize(TextUtils.TruncateAt.END);
                int n3 = this.o;
                if (n3 != 0) {
                    this.e.setTextAppearance(context, n3);
                }
                if ((textView = this.C) != null) {
                    this.e.setTextColor((ColorStateList)textView);
                }
            }
            if (!this.B((View)this.e)) {
                this.c((View)this.e, true);
            }
        } else {
            textView = this.e;
            if (textView != null && this.B((View)textView)) {
                this.removeView((View)this.e);
                this.G.remove(this.e);
            }
        }
        if ((textView = this.e) != null) {
            textView.setText(charSequence);
        }
        this.A = charSequence;
    }

    public void setSubtitleTextAppearance(Context context, int n3) {
        this.o = n3;
        TextView textView = this.e;
        if (textView != null) {
            textView.setTextAppearance(context, n3);
        }
    }

    public void setSubtitleTextColor(int n3) {
        this.setSubtitleTextColor(ColorStateList.valueOf((int)n3));
    }

    public void setSubtitleTextColor(ColorStateList colorStateList) {
        this.C = colorStateList;
        TextView textView = this.e;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void setTitle(int n3) {
        this.setTitle(this.getContext().getText(n3));
    }

    public void setTitle(CharSequence charSequence) {
        TextView textView;
        if (!TextUtils.isEmpty((CharSequence)charSequence)) {
            if (this.d == null) {
                Context context = this.getContext();
                this.d = textView = new AppCompatTextView(context);
                textView.setSingleLine();
                this.d.setEllipsize(TextUtils.TruncateAt.END);
                int n3 = this.n;
                if (n3 != 0) {
                    this.d.setTextAppearance(context, n3);
                }
                if ((textView = this.B) != null) {
                    this.d.setTextColor((ColorStateList)textView);
                }
            }
            if (!this.B((View)this.d)) {
                this.c((View)this.d, true);
            }
        } else {
            textView = this.d;
            if (textView != null && this.B((View)textView)) {
                this.removeView((View)this.d);
                this.G.remove(this.d);
            }
        }
        if ((textView = this.d) != null) {
            textView.setText(charSequence);
        }
        this.z = charSequence;
    }

    public void setTitleMargin(int n3, int n4, int n5, int n6) {
        this.r = n3;
        this.t = n4;
        this.s = n5;
        this.u = n6;
        this.requestLayout();
    }

    public void setTitleMarginBottom(int n3) {
        this.u = n3;
        this.requestLayout();
    }

    public void setTitleMarginEnd(int n3) {
        this.s = n3;
        this.requestLayout();
    }

    public void setTitleMarginStart(int n3) {
        this.r = n3;
        this.requestLayout();
    }

    public void setTitleMarginTop(int n3) {
        this.t = n3;
        this.requestLayout();
    }

    public void setTitleTextAppearance(Context context, int n3) {
        this.n = n3;
        TextView textView = this.d;
        if (textView != null) {
            textView.setTextAppearance(context, n3);
        }
    }

    public void setTitleTextColor(int n3) {
        this.setTitleTextColor(ColorStateList.valueOf((int)n3));
    }

    public void setTitleTextColor(ColorStateList colorStateList) {
        this.B = colorStateList;
        TextView textView = this.d;
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public final int t(int n3) {
        int n4;
        n3 = n4 = n3 & 0x70;
        if (n4 != 16) {
            n3 = n4;
            if (n4 != 48) {
                n3 = n4;
                if (n4 != 80) {
                    n3 = this.y & 0x70;
                }
            }
        }
        return n3;
    }

    public final int u(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return view.getMarginStart() + view.getMarginEnd();
    }

    public final int v(View view) {
        view = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        return view.topMargin + view.bottomMargin;
    }

    public final int w(List list, int[] object) {
        int n3 = object[0];
        int n4 = object[1];
        int n5 = list.size();
        int n6 = 0;
        for (int i3 = 0; i3 < n5; ++i3) {
            object = (View)list.get(i3);
            LayoutParams layoutParams = (LayoutParams)object.getLayoutParams();
            n3 = layoutParams.leftMargin - n3;
            n4 = layoutParams.rightMargin - n4;
            int n7 = Math.max(0, n3);
            int n8 = Math.max(0, n4);
            n3 = Math.max(0, -n3);
            n4 = Math.max(0, -n4);
            n6 += n7 + object.getMeasuredWidth() + n8;
        }
        return n6;
    }

    public boolean x() {
        f f3 = this.N;
        return f3 != null && f3.d != null;
    }

    public boolean y() {
        ActionMenuView actionMenuView = this.c;
        return actionMenuView != null && actionMenuView.F();
    }

    public void z(int n3) {
        this.getMenuInflater().inflate(n3, this.getMenu());
    }

    public static class LayoutParams
    extends ActionBar.LayoutParams {
        public int b = 0;

        public LayoutParams(int n3, int n4) {
            super(n3, n4);
            this.a = 8388627;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super((ViewGroup.LayoutParams)marginLayoutParams);
            this.a(marginLayoutParams);
        }

        public LayoutParams(ActionBar.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.b = layoutParams.b;
        }

        public void a(ViewGroup.MarginLayoutParams marginLayoutParams) {
            this.leftMargin = marginLayoutParams.leftMargin;
            this.topMargin = marginLayoutParams.topMargin;
            this.rightMargin = marginLayoutParams.rightMargin;
            this.bottomMargin = marginLayoutParams.bottomMargin;
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

    public static abstract class e {
        public static OnBackInvokedDispatcher a(View view) {
            return view.findOnBackInvokedDispatcher();
        }

        public static OnBackInvokedCallback b(Runnable runnable) {
            Objects.requireNonNull(runnable);
            return new p0(runnable);
        }

        public static void c(Object object, Object object2) {
            ((OnBackInvokedDispatcher)object).registerOnBackInvokedCallback(1000000, (OnBackInvokedCallback)object2);
        }

        public static void d(Object object, Object object2) {
            ((OnBackInvokedDispatcher)object).unregisterOnBackInvokedCallback((OnBackInvokedCallback)object2);
        }
    }

    public class f
    implements i {
        public androidx.appcompat.view.menu.e c;
        public androidx.appcompat.view.menu.g d;
        public final Toolbar e;

        public f(Toolbar toolbar) {
            this.e = toolbar;
        }

        @Override
        public void a(androidx.appcompat.view.menu.e e3, boolean bl) {
        }

        @Override
        public void b(Context object, androidx.appcompat.view.menu.e e3) {
            androidx.appcompat.view.menu.e e4 = this.c;
            if (e4 != null && (object = this.d) != null) {
                e4.f((androidx.appcompat.view.menu.g)object);
            }
            this.c = e3;
        }

        @Override
        public void d(Parcelable parcelable) {
        }

        @Override
        public boolean f(l l3) {
            return false;
        }

        @Override
        public void g(boolean bl) {
            if (this.d != null) {
                androidx.appcompat.view.menu.e e3 = this.c;
                if (e3 != null) {
                    int n3 = e3.size();
                    for (int i3 = 0; i3 < n3; ++i3) {
                        if (this.c.getItem(i3) != this.d) continue;
                        return;
                    }
                }
                this.k(this.c, this.d);
            }
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public boolean i() {
            return false;
        }

        @Override
        public Parcelable j() {
            return null;
        }

        @Override
        public boolean k(androidx.appcompat.view.menu.e object, androidx.appcompat.view.menu.g g3) {
            object = this.e.k;
            if (object instanceof c) {
                ((c)object).onActionViewCollapsed();
            }
            object = this.e;
            object.removeView(((Toolbar)object).k);
            object = this.e;
            object.removeView((View)((Toolbar)object).j);
            object = this.e;
            ((Toolbar)object).k = null;
            ((Toolbar)object).a();
            this.d = null;
            this.e.requestLayout();
            g3.r(false);
            this.e.O();
            return true;
        }

        @Override
        public boolean l(androidx.appcompat.view.menu.e object, androidx.appcompat.view.menu.g g3) {
            this.e.h();
            Object object2 = this.e.j.getParent();
            object = this.e;
            if (object2 != object) {
                if (object2 instanceof ViewGroup) {
                    ((ViewGroup)object2).removeView((View)((Toolbar)object).j);
                }
                object = this.e;
                object.addView((View)((Toolbar)object).j);
            }
            this.e.k = g3.getActionView();
            this.d = g3;
            object = this.e.k.getParent();
            if (object != (object2 = this.e)) {
                if (object instanceof ViewGroup) {
                    ((ViewGroup)object).removeView(object2.k);
                }
                object2 = this.e.o();
                object = this.e;
                object2.a = ((Toolbar)object).p & 0x70 | 0x800003;
                object2.b = 2;
                ((Toolbar)object).k.setLayoutParams((ViewGroup.LayoutParams)object2);
                object = this.e;
                object.addView(((Toolbar)object).k);
            }
            this.e.K();
            this.e.requestLayout();
            g3.r(true);
            object = this.e.k;
            if (object instanceof c) {
                ((c)object).onActionViewExpanded();
            }
            this.e.O();
            return true;
        }
    }

    public static interface g {
    }
}

