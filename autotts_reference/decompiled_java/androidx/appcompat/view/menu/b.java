/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.FrameLayout
 *  android.widget.HeaderViewListAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.l;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.appcompat.widget.d0;
import c.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import o0.s;

public final class b
extends i.d
implements i,
View.OnKeyListener,
PopupWindow.OnDismissListener {
    public static final int D = c.g.abc_cascading_menu_item_layout;
    public ViewTreeObserver A;
    public PopupWindow.OnDismissListener B;
    public boolean C;
    public final Context d;
    public final int e;
    public final int f;
    public final int g;
    public final boolean h;
    public final Handler i;
    public final List j = new ArrayList();
    public final List k = new ArrayList();
    public final ViewTreeObserver.OnGlobalLayoutListener l = new ViewTreeObserver.OnGlobalLayoutListener(this){
        public final b c;
        {
            this.c = b3;
        }

        public void onGlobalLayout() {
            if (this.c.c() && this.c.k.size() > 0 && !((d)this.c.k.get((int)0)).a.B()) {
                Object object = this.c.r;
                if (object != null && object.isShown()) {
                    object = this.c.k.iterator();
                    while (object.hasNext()) {
                        ((d)object.next()).a.e();
                    }
                } else {
                    this.c.dismiss();
                }
            }
        }
    };
    public final View.OnAttachStateChangeListener m = new View.OnAttachStateChangeListener(this){
        public final b c;
        {
            this.c = b3;
        }

        public void onViewAttachedToWindow(View view) {
        }

        public void onViewDetachedFromWindow(View view) {
            Object object = this.c.A;
            if (object != null) {
                if (!object.isAlive()) {
                    this.c.A = view.getViewTreeObserver();
                }
                object = this.c;
                object.A.removeGlobalOnLayoutListener(object.l);
            }
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
        }
    };
    public final d0 n = new d0(this){
        public final b c;
        {
            this.c = b3;
        }

        @Override
        public void a(e e3, MenuItem object) {
            int n3;
            d d3;
            block4: {
                Handler handler = this.c.i;
                d3 = null;
                handler.removeCallbacksAndMessages(null);
                int n4 = this.c.k.size();
                for (n3 = 0; n3 < n4; ++n3) {
                    if (e3 != ((d)this.c.k.get((int)n3)).b) {
                        continue;
                    }
                    break block4;
                }
                n3 = -1;
            }
            if (n3 == -1) {
                return;
            }
            if (++n3 < this.c.k.size()) {
                d3 = (d)this.c.k.get(n3);
            }
            object = new Runnable(this, d3, (MenuItem)object, e3){
                public final d c;
                public final MenuItem d;
                public final e e;
                public final c f;
                {
                    this.f = c3;
                    this.c = d3;
                    this.d = menuItem;
                    this.e = e3;
                }

                @Override
                public void run() {
                    d d3 = this.c;
                    if (d3 != null) {
                        this.f.c.C = true;
                        d3.b.e(false);
                        this.f.c.C = false;
                    }
                    if (this.d.isEnabled() && this.d.hasSubMenu()) {
                        this.e.O(this.d, 4);
                    }
                }
            };
            long l3 = SystemClock.uptimeMillis();
            this.c.i.postAtTime((Runnable)object, (Object)e3, l3 + 200L);
        }

        @Override
        public void f(e e3, MenuItem menuItem) {
            this.c.i.removeCallbacksAndMessages((Object)e3);
        }
    };
    public int o = 0;
    public int p = 0;
    public View q;
    public View r;
    public int s;
    public boolean t;
    public boolean u;
    public int v;
    public int w;
    public boolean x;
    public boolean y;
    public i.a z;

    public b(Context context, View view, int n3, int n4, boolean bl) {
        this.d = context;
        this.q = view;
        this.f = n3;
        this.g = n4;
        this.h = bl;
        this.x = false;
        this.s = this.F();
        context = context.getResources();
        this.e = Math.max(context.getDisplayMetrics().widthPixels / 2, context.getDimensionPixelSize(c.d.abc_config_prefDialogWidth));
        this.i = new Handler();
    }

    public final MenuPopupWindow B() {
        MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.d, null, this.f, this.g);
        menuPopupWindow.U(this.n);
        menuPopupWindow.L(this);
        menuPopupWindow.K(this);
        menuPopupWindow.D(this.q);
        menuPopupWindow.G(this.p);
        menuPopupWindow.J(true);
        menuPopupWindow.I(2);
        return menuPopupWindow;
    }

    public final int C(e e3) {
        int n3 = this.k.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (e3 != ((d)this.k.get((int)i3)).b) continue;
            return i3;
        }
        return -1;
    }

    public final MenuItem D(e e3, e e4) {
        int n3 = e3.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            MenuItem menuItem = e3.getItem(i3);
            if (!menuItem.hasSubMenu() || e4 != menuItem.getSubMenu()) continue;
            return menuItem;
        }
        return null;
    }

    public final View E(d object, e e3) {
        int n3;
        int n4;
        ListView listView;
        block7: {
            if ((e3 = this.D(((d)object).b, e3)) == null) {
                return null;
            }
            listView = ((d)object).a();
            object = listView.getAdapter();
            boolean bl = object instanceof HeaderViewListAdapter;
            n4 = 0;
            if (bl) {
                object = (HeaderViewListAdapter)object;
                n3 = object.getHeadersCount();
                object = (androidx.appcompat.view.menu.d)object.getWrappedAdapter();
            } else {
                object = (androidx.appcompat.view.menu.d)((Object)object);
                n3 = 0;
            }
            int n5 = ((androidx.appcompat.view.menu.d)((Object)object)).getCount();
            while (n4 < n5) {
                if (e3 != ((androidx.appcompat.view.menu.d)((Object)object)).c(n4)) {
                    ++n4;
                    continue;
                }
                break block7;
            }
            n4 = -1;
        }
        if (n4 == -1) {
            return null;
        }
        if ((n4 = n4 + n3 - listView.getFirstVisiblePosition()) >= 0 && n4 < listView.getChildCount()) {
            return listView.getChildAt(n4);
        }
        return null;
    }

    public final int F() {
        if (this.q.getLayoutDirection() == 1) {
            return 0;
        }
        return 1;
    }

    public final int G(int n3) {
        Object object = this.k;
        ListView listView = ((d)object.get(object.size() - 1)).a();
        object = new int[2];
        listView.getLocationOnScreen((int[])object);
        Rect rect = new Rect();
        this.r.getWindowVisibleDisplayFrame(rect);
        if (this.s == 1) {
            if (object[0] + listView.getWidth() + n3 > rect.right) {
                return 0;
            }
            return 1;
        }
        if (object[0] - n3 < 0) {
            return 1;
        }
        return 0;
    }

    public final void H(e e3) {
        Object object;
        LayoutInflater layoutInflater = LayoutInflater.from((Context)this.d);
        Object object2 = new androidx.appcompat.view.menu.d(e3, layoutInflater, this.h, D);
        if (!this.c() && this.x) {
            ((androidx.appcompat.view.menu.d)((Object)object2)).d(true);
        } else if (this.c()) {
            ((androidx.appcompat.view.menu.d)((Object)object2)).d(i.d.z(e3));
        }
        int n3 = i.d.q((ListAdapter)object2, null, this.d, this.e);
        MenuPopupWindow menuPopupWindow = this.B();
        menuPopupWindow.p((ListAdapter)object2);
        menuPopupWindow.F(n3);
        menuPopupWindow.G(this.p);
        if (this.k.size() > 0) {
            object2 = this.k;
            object2 = (d)object2.get(object2.size() - 1);
            object = this.E((d)object2, e3);
        } else {
            object2 = null;
            object = null;
        }
        if (object != null) {
            menuPopupWindow.V(false);
            menuPopupWindow.S(null);
            int n4 = this.G(n3);
            int n5 = n4 == 1 ? 1 : 0;
            this.s = n4;
            menuPopupWindow.D((View)object);
            n5 = (this.p & 5) == 5 ? (n5 != 0 ? n3 : 0 - object.getWidth()) : (n5 != 0 ? object.getWidth() : 0 - n3);
            menuPopupWindow.l(n5);
            menuPopupWindow.N(true);
            menuPopupWindow.j(0);
        } else {
            if (this.t) {
                menuPopupWindow.l(this.v);
            }
            if (this.u) {
                menuPopupWindow.j(this.w);
            }
            menuPopupWindow.H(this.p());
        }
        object = new d(menuPopupWindow, e3, this.s);
        this.k.add(object);
        menuPopupWindow.e();
        object = menuPopupWindow.h();
        object.setOnKeyListener((View.OnKeyListener)this);
        if (object2 == null && this.y && e3.z() != null) {
            layoutInflater = (FrameLayout)layoutInflater.inflate(c.g.abc_popup_menu_header_item_layout, (ViewGroup)object, false);
            object2 = (TextView)layoutInflater.findViewById(16908310);
            layoutInflater.setEnabled(false);
            object2.setText(e3.z());
            object.addHeaderView((View)layoutInflater, null, false);
            menuPopupWindow.e();
        }
    }

    @Override
    public void a(e e3, boolean bl) {
        int n3 = this.C(e3);
        if (n3 >= 0) {
            int n4 = n3 + 1;
            if (n4 < this.k.size()) {
                ((d)this.k.get((int)n4)).b.e(false);
            }
            Object object = (d)this.k.remove(n3);
            ((d)object).b.R(this);
            if (this.C) {
                ((d)object).a.T(null);
                ((d)object).a.E(0);
            }
            ((d)object).a.dismiss();
            n3 = this.k.size();
            this.s = n3 > 0 ? ((d)this.k.get((int)(n3 - 1))).c : this.F();
            if (n3 == 0) {
                this.dismiss();
                object = this.z;
                if (object != null) {
                    object.a(e3, true);
                }
                if ((e3 = this.A) != null) {
                    if (e3.isAlive()) {
                        this.A.removeGlobalOnLayoutListener(this.l);
                    }
                    this.A = null;
                }
                this.r.removeOnAttachStateChangeListener(this.m);
                this.B.onDismiss();
                return;
            }
            if (bl) {
                ((d)this.k.get((int)0)).b.e(false);
            }
        }
    }

    @Override
    public boolean c() {
        return this.k.size() > 0 && ((d)this.k.get((int)0)).a.c();
    }

    @Override
    public void d(Parcelable parcelable) {
    }

    @Override
    public void dismiss() {
        int n3 = this.k.size();
        if (n3 > 0) {
            d[] dArray = this.k.toArray(new d[n3]);
            --n3;
            while (n3 >= 0) {
                d d3 = dArray[n3];
                if (d3.a.c()) {
                    d3.a.dismiss();
                }
                --n3;
            }
        }
    }

    @Override
    public void e() {
        if (!this.c()) {
            View view = this.j.iterator();
            while (view.hasNext()) {
                this.H((e)view.next());
            }
            this.j.clear();
            this.r = view = this.q;
            if (view != null) {
                boolean bl = this.A == null;
                view = view.getViewTreeObserver();
                this.A = view;
                if (bl) {
                    view.addOnGlobalLayoutListener(this.l);
                }
                this.r.addOnAttachStateChangeListener(this.m);
            }
        }
    }

    @Override
    public boolean f(l l3) {
        for (d d3 : this.k) {
            if (l3 != d3.b) continue;
            d3.a().requestFocus();
            return true;
        }
        if (l3.hasVisibleItems()) {
            this.n(l3);
            i.a a4 = this.z;
            if (a4 != null) {
                a4.b(l3);
            }
            return true;
        }
        return false;
    }

    @Override
    public void g(boolean bl) {
        Iterator iterator = this.k.iterator();
        while (iterator.hasNext()) {
            i.d.A(((d)iterator.next()).a().getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public ListView h() {
        if (this.k.isEmpty()) {
            return null;
        }
        List list = this.k;
        return ((d)list.get(list.size() - 1)).a();
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
        this.z = a4;
    }

    @Override
    public void n(e e3) {
        e3.c(this, this.d);
        if (this.c()) {
            this.H(e3);
            return;
        }
        this.j.add(e3);
    }

    @Override
    public boolean o() {
        return false;
    }

    public void onDismiss() {
        d d3;
        block3: {
            int n3 = this.k.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                d3 = (d)this.k.get(i3);
                if (d3.a.c()) {
                    continue;
                }
                break block3;
            }
            d3 = null;
        }
        if (d3 != null) {
            d3.b.e(false);
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
        if (this.q != view) {
            this.q = view;
            this.p = o0.s.b(this.o, view.getLayoutDirection());
        }
    }

    @Override
    public void t(boolean bl) {
        this.x = bl;
    }

    @Override
    public void u(int n3) {
        if (this.o != n3) {
            this.o = n3;
            this.p = o0.s.b(n3, this.q.getLayoutDirection());
        }
    }

    @Override
    public void v(int n3) {
        this.t = true;
        this.v = n3;
    }

    @Override
    public void w(PopupWindow.OnDismissListener onDismissListener) {
        this.B = onDismissListener;
    }

    @Override
    public void x(boolean bl) {
        this.y = bl;
    }

    @Override
    public void y(int n3) {
        this.u = true;
        this.w = n3;
    }

    public static class d {
        public final MenuPopupWindow a;
        public final e b;
        public final int c;

        public d(MenuPopupWindow menuPopupWindow, e e3, int n3) {
            this.a = menuPopupWindow;
            this.b = e3;
            this.c = n3;
        }

        public ListView a() {
            return this.a.h();
        }
    }
}

