/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Parcelable
 *  android.view.Gravity
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.FrameLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.d;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.h;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.MenuPopupWindow;
import c.g;

public final class k
extends i.d
implements PopupWindow.OnDismissListener,
AdapterView.OnItemClickListener,
i,
View.OnKeyListener {
    public static final int x = c.g.abc_popup_menu_item_layout;
    public final Context d;
    public final e e;
    public final d f;
    public final boolean g;
    public final int h;
    public final int i;
    public final int j;
    public final MenuPopupWindow k;
    public final ViewTreeObserver.OnGlobalLayoutListener l = new ViewTreeObserver.OnGlobalLayoutListener(this){
        public final k c;
        {
            this.c = k3;
        }

        public void onGlobalLayout() {
            if (this.c.c() && !this.c.k.B()) {
                View view = this.c.p;
                if (view != null && view.isShown()) {
                    this.c.k.e();
                    return;
                }
                this.c.dismiss();
            }
        }
    };
    public final View.OnAttachStateChangeListener m = new View.OnAttachStateChangeListener(this){
        public final k c;
        {
            this.c = k3;
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            Object object = this.c.r;
            if (object != null) {
                if (!object.isAlive()) {
                    this.c.r = view.getViewTreeObserver();
                }
                object = this.c;
                object.r.removeGlobalOnLayoutListener(object.l);
            }
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }
    };
    public PopupWindow.OnDismissListener n;
    public View o;
    public View p;
    public i.a q;
    public ViewTreeObserver r;
    public boolean s;
    public boolean t;
    public int u;
    public int v = 0;
    public boolean w;

    public k(Context context, e e3, View view, int n3, int n4, boolean bl) {
        this.d = context;
        this.e = e3;
        this.g = bl;
        this.f = new d(e3, LayoutInflater.from((Context)context), bl, x);
        this.i = n3;
        this.j = n4;
        Resources resources = context.getResources();
        this.h = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(c.d.abc_config_prefDialogWidth));
        this.o = view;
        this.k = new MenuPopupWindow(context, null, n3, n4);
        e3.c(this, context);
    }

    public final boolean B() {
        View view;
        if (this.c()) {
            return true;
        }
        if (!this.s && (view = this.o) != null) {
            ViewTreeObserver viewTreeObserver;
            this.p = view;
            this.k.K(this);
            this.k.L(this);
            this.k.J(true);
            view = this.p;
            boolean bl = this.r == null;
            this.r = viewTreeObserver = view.getViewTreeObserver();
            if (bl) {
                viewTreeObserver.addOnGlobalLayoutListener(this.l);
            }
            view.addOnAttachStateChangeListener(this.m);
            this.k.D(view);
            this.k.G(this.v);
            if (!this.t) {
                this.u = i.d.q((ListAdapter)this.f, null, this.d, this.h);
                this.t = true;
            }
            this.k.F(this.u);
            this.k.I(2);
            this.k.H(this.p());
            this.k.e();
            viewTreeObserver = this.k.h();
            viewTreeObserver.setOnKeyListener((View.OnKeyListener)this);
            if (this.w && this.e.z() != null) {
                FrameLayout frameLayout = (FrameLayout)LayoutInflater.from((Context)this.d).inflate(c.g.abc_popup_menu_header_item_layout, (ViewGroup)viewTreeObserver, false);
                view = (TextView)frameLayout.findViewById(16908310);
                if (view != null) {
                    view.setText(this.e.z());
                }
                frameLayout.setEnabled(false);
                viewTreeObserver.addHeaderView((View)frameLayout, null, false);
            }
            this.k.p((ListAdapter)this.f);
            this.k.e();
            return true;
        }
        return false;
    }

    @Override
    public void a(e e3, boolean bl) {
        if (e3 == this.e) {
            this.dismiss();
            i.a a4 = this.q;
            if (a4 != null) {
                a4.a(e3, bl);
            }
        }
    }

    @Override
    public boolean c() {
        return !this.s && this.k.c();
    }

    @Override
    public void d(Parcelable parcelable) {
    }

    @Override
    public void dismiss() {
        if (this.c()) {
            this.k.dismiss();
        }
    }

    @Override
    public void e() {
        if (this.B()) {
            return;
        }
        throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
    }

    @Override
    public boolean f(l l3) {
        if (l3.hasVisibleItems()) {
            Object object = new h(this.d, l3, this.p, this.g, this.i, this.j);
            ((h)object).j(this.q);
            ((h)object).g(i.d.z(l3));
            ((h)object).i(this.n);
            this.n = null;
            this.e.e(false);
            int n3 = this.k.d();
            int n4 = this.k.n();
            int n5 = n3;
            if ((Gravity.getAbsoluteGravity((int)this.v, (int)this.o.getLayoutDirection()) & 7) == 5) {
                n5 = n3 + this.o.getWidth();
            }
            if (((h)object).n(n5, n4)) {
                object = this.q;
                if (object != null) {
                    object.b(l3);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void g(boolean bl) {
        this.t = false;
        d d3 = this.f;
        if (d3 != null) {
            d3.notifyDataSetChanged();
        }
    }

    @Override
    public ListView h() {
        return this.k.h();
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
    public void m(i.a a4) {
        this.q = a4;
    }

    @Override
    public void n(e e3) {
    }

    public void onDismiss() {
        this.s = true;
        this.e.close();
        ViewTreeObserver viewTreeObserver = this.r;
        if (viewTreeObserver != null) {
            if (!viewTreeObserver.isAlive()) {
                this.r = this.p.getViewTreeObserver();
            }
            this.r.removeGlobalOnLayoutListener(this.l);
            this.r = null;
        }
        this.p.removeOnAttachStateChangeListener(this.m);
        viewTreeObserver = this.n;
        if (viewTreeObserver != null) {
            viewTreeObserver.onDismiss();
        }
    }

    public boolean onKey(View view, int n3, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 1 && n3 == 82) {
            this.dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void r(View view) {
        this.o = view;
    }

    @Override
    public void t(boolean bl) {
        this.f.d(bl);
    }

    @Override
    public void u(int n3) {
        this.v = n3;
    }

    @Override
    public void v(int n3) {
        this.k.l(n3);
    }

    @Override
    public void w(PopupWindow.OnDismissListener onDismissListener) {
        this.n = onDismissListener;
    }

    @Override
    public void x(boolean bl) {
        this.w = bl;
    }

    @Override
    public void y(int n3) {
        this.k.j(n3);
    }
}

