/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window$Callback
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.ScrollingTabContainerView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.m0;
import androidx.appcompat.widget.u;
import c.a;
import c.f;
import c.h;
import c.j;
import o0.h1;
import o0.j1;
import o0.x0;

public class q0
implements u {
    public Toolbar a;
    public int b;
    public View c;
    public View d;
    public Drawable e;
    public Drawable f;
    public Drawable g;
    public boolean h;
    public CharSequence i;
    public CharSequence j;
    public CharSequence k;
    public Window.Callback l;
    public boolean m;
    public ActionMenuPresenter n;
    public int o = 0;
    public int p = 0;
    public Drawable q;

    public q0(Toolbar toolbar, boolean bl) {
        this(toolbar, bl, c.h.abc_action_bar_up_description, c.e.abc_ic_ab_back_material);
    }

    public q0(Toolbar object, boolean bl, int n3, int n4) {
        this.a = object;
        this.i = ((Toolbar)object).getTitle();
        this.j = ((Toolbar)object).getSubtitle();
        boolean bl2 = this.i != null;
        this.h = bl2;
        this.g = ((Toolbar)object).getNavigationIcon();
        object = m0.v(object.getContext(), null, c.j.ActionBar, c.a.actionBarStyle, 0);
        this.q = ((m0)object).g(c.j.ActionBar_homeAsUpIndicator);
        if (bl) {
            Object object2 = ((m0)object).p(c.j.ActionBar_title);
            if (!TextUtils.isEmpty((CharSequence)object2)) {
                this.A((CharSequence)object2);
            }
            if (!TextUtils.isEmpty((CharSequence)(object2 = ((m0)object).p(c.j.ActionBar_subtitle)))) {
                this.z((CharSequence)object2);
            }
            if ((object2 = ((m0)object).g(c.j.ActionBar_logo)) != null) {
                this.v((Drawable)object2);
            }
            if ((object2 = ((m0)object).g(c.j.ActionBar_icon)) != null) {
                this.setIcon((Drawable)object2);
            }
            if (this.g == null && (object2 = this.q) != null) {
                this.y((Drawable)object2);
            }
            this.k(((m0)object).k(c.j.ActionBar_displayOptions, 0));
            n4 = ((m0)object).n(c.j.ActionBar_customNavigationLayout, 0);
            if (n4 != 0) {
                this.t(LayoutInflater.from((Context)this.a.getContext()).inflate(n4, (ViewGroup)this.a, false));
                this.k(this.b | 0x10);
            }
            if ((n4 = ((m0)object).m(c.j.ActionBar_height, 0)) > 0) {
                object2 = this.a.getLayoutParams();
                ((ViewGroup.LayoutParams)object2).height = n4;
                this.a.setLayoutParams((ViewGroup.LayoutParams)object2);
            }
            int n5 = ((m0)object).e(c.j.ActionBar_contentInsetStart, -1);
            n4 = ((m0)object).e(c.j.ActionBar_contentInsetEnd, -1);
            if (n5 >= 0 || n4 >= 0) {
                this.a.setContentInsetsRelative(Math.max(n5, 0), Math.max(n4, 0));
            }
            if ((n4 = ((m0)object).n(c.j.ActionBar_titleTextStyle, 0)) != 0) {
                object2 = this.a;
                ((Toolbar)object2).setTitleTextAppearance(object2.getContext(), n4);
            }
            if ((n4 = ((m0)object).n(c.j.ActionBar_subtitleTextStyle, 0)) != 0) {
                object2 = this.a;
                ((Toolbar)object2).setSubtitleTextAppearance(object2.getContext(), n4);
            }
            if ((n4 = ((m0)object).n(c.j.ActionBar_popupTheme, 0)) != 0) {
                this.a.setPopupTheme(n4);
            }
        } else {
            this.b = this.s();
        }
        ((m0)object).x();
        this.u(n3);
        this.k = this.a.getNavigationContentDescription();
        this.a.setNavigationOnClickListener(new View.OnClickListener(this){
            public final i.a c;
            public final q0 d;
            {
                this.d = q02;
                this.c = new i.a(q02.a.getContext(), 0, 16908332, 0, 0, q02.i);
            }

            public void onClick(View view) {
                q0 q02 = this.d;
                view = q02.l;
                if (view != null && q02.m) {
                    view.onMenuItemSelected(0, (MenuItem)this.c);
                }
            }
        });
    }

    public void A(CharSequence charSequence) {
        this.h = true;
        this.B(charSequence);
    }

    public final void B(CharSequence charSequence) {
        this.i = charSequence;
        if ((this.b & 8) != 0) {
            this.a.setTitle(charSequence);
            if (this.h) {
                x0.j0(this.a.getRootView(), charSequence);
            }
        }
    }

    public final void C() {
        if ((this.b & 4) != 0) {
            if (TextUtils.isEmpty((CharSequence)this.k)) {
                this.a.setNavigationContentDescription(this.p);
                return;
            }
            this.a.setNavigationContentDescription(this.k);
        }
    }

    public final void D() {
        if ((this.b & 4) != 0) {
            Toolbar toolbar = this.a;
            Drawable drawable = this.g;
            if (drawable == null) {
                drawable = this.q;
            }
            toolbar.setNavigationIcon(drawable);
            return;
        }
        this.a.setNavigationIcon(null);
    }

    public final void E() {
        Drawable drawable;
        int n3 = this.b;
        if ((n3 & 2) != 0) {
            if ((n3 & 1) != 0) {
                drawable = this.f;
                if (drawable == null) {
                    drawable = this.e;
                }
            } else {
                drawable = this.e;
            }
        } else {
            drawable = null;
        }
        this.a.setLogo(drawable);
    }

    @Override
    public boolean a() {
        return this.a.D();
    }

    @Override
    public Context b() {
        return this.a.getContext();
    }

    @Override
    public boolean c() {
        return this.a.C();
    }

    @Override
    public void collapseActionView() {
        this.a.e();
    }

    @Override
    public boolean d() {
        return this.a.y();
    }

    @Override
    public boolean e() {
        return this.a.N();
    }

    @Override
    public boolean f() {
        return this.a.d();
    }

    @Override
    public void g() {
        this.a.f();
    }

    @Override
    public CharSequence getTitle() {
        return this.a.getTitle();
    }

    @Override
    public void h(ScrollingTabContainerView scrollingTabContainerView) {
        Toolbar toolbar;
        Object object = this.c;
        if (object != null && (object = object.getParent()) == (toolbar = this.a)) {
            toolbar.removeView(this.c);
        }
        this.c = scrollingTabContainerView;
        if (scrollingTabContainerView != null && this.o == 2) {
            this.a.addView((View)scrollingTabContainerView, 0);
            object = (Toolbar.LayoutParams)this.c.getLayoutParams();
            object.width = -2;
            object.height = -2;
            object.a = 8388691;
            scrollingTabContainerView.setAllowCollapse(true);
        }
    }

    @Override
    public void i(boolean bl) {
    }

    @Override
    public boolean j() {
        return this.a.x();
    }

    @Override
    public void k(int n3) {
        int n4 = this.b ^ n3;
        this.b = n3;
        if (n4 != 0) {
            View view;
            if ((n4 & 4) != 0) {
                if ((n3 & 4) != 0) {
                    this.C();
                }
                this.D();
            }
            if ((n4 & 3) != 0) {
                this.E();
            }
            if ((n4 & 8) != 0) {
                if ((n3 & 8) != 0) {
                    this.a.setTitle(this.i);
                    this.a.setSubtitle(this.j);
                } else {
                    this.a.setTitle(null);
                    this.a.setSubtitle(null);
                }
            }
            if ((n4 & 0x10) != 0 && (view = this.d) != null) {
                if ((n3 & 0x10) != 0) {
                    this.a.addView(view);
                    return;
                }
                this.a.removeView(view);
            }
        }
    }

    @Override
    public int l() {
        return this.b;
    }

    @Override
    public void m(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.b(), n3) : null;
        this.v(drawable);
    }

    @Override
    public int n() {
        return this.o;
    }

    @Override
    public h1 o(int n3, long l3) {
        h1 h12 = x0.e((View)this.a);
        float f3 = n3 == 0 ? 1.0f : 0.0f;
        return h12.b(f3).e(l3).g(new j1(this, n3){
            public boolean a;
            public final int b;
            public final q0 c;
            {
                this.c = q02;
                this.b = n3;
                this.a = false;
            }

            @Override
            public void a(View view) {
                this.a = true;
            }

            @Override
            public void b(View view) {
                if (!this.a) {
                    this.c.a.setVisibility(this.b);
                }
            }

            @Override
            public void c(View view) {
                this.c.a.setVisibility(0);
            }
        });
    }

    @Override
    public void p() {
    }

    @Override
    public void q() {
    }

    @Override
    public void r(boolean bl) {
        this.a.setCollapsible(bl);
    }

    public final int s() {
        if (this.a.getNavigationIcon() != null) {
            this.q = this.a.getNavigationIcon();
            return 15;
        }
        return 11;
    }

    @Override
    public void setIcon(int n3) {
        Drawable drawable = n3 != 0 ? d.a.b(this.b(), n3) : null;
        this.setIcon(drawable);
    }

    @Override
    public void setIcon(Drawable drawable) {
        this.e = drawable;
        this.E();
    }

    @Override
    public void setMenu(Menu menu, i.a a4) {
        if (this.n == null) {
            ActionMenuPresenter actionMenuPresenter;
            this.n = actionMenuPresenter = new ActionMenuPresenter(this.a.getContext());
            actionMenuPresenter.s(c.f.action_menu_presenter);
        }
        this.n.m(a4);
        this.a.setMenu((e)menu, this.n);
    }

    @Override
    public void setMenuPrepared() {
        this.m = true;
    }

    @Override
    public void setVisibility(int n3) {
        this.a.setVisibility(n3);
    }

    @Override
    public void setWindowCallback(Window.Callback callback) {
        this.l = callback;
    }

    @Override
    public void setWindowTitle(CharSequence charSequence) {
        if (!this.h) {
            this.B(charSequence);
        }
    }

    public void t(View view) {
        View view2 = this.d;
        if (view2 != null && (this.b & 0x10) != 0) {
            this.a.removeView(view2);
        }
        this.d = view;
        if (view != null && (this.b & 0x10) != 0) {
            this.a.addView(view);
        }
    }

    public void u(int n3) {
        if (n3 != this.p) {
            this.p = n3;
            if (TextUtils.isEmpty((CharSequence)this.a.getNavigationContentDescription())) {
                this.w(this.p);
            }
        }
    }

    public void v(Drawable drawable) {
        this.f = drawable;
        this.E();
    }

    public void w(int n3) {
        String string = n3 == 0 ? null : this.b().getString(n3);
        this.x(string);
    }

    public void x(CharSequence charSequence) {
        this.k = charSequence;
        this.C();
    }

    public void y(Drawable drawable) {
        this.g = drawable;
        this.D();
    }

    public void z(CharSequence charSequence) {
        this.j = charSequence;
        if ((this.b & 8) != 0) {
            this.a.setSubtitle(charSequence);
        }
    }
}

