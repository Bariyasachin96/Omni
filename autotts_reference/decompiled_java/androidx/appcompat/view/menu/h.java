/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.view.Display
 *  android.view.View
 *  android.view.WindowManager
 *  android.widget.PopupWindow$OnDismissListener
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.b;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.i;
import androidx.appcompat.view.menu.k;
import c.d;
import o0.s;

public class h {
    public final Context a;
    public final e b;
    public final boolean c;
    public final int d;
    public final int e;
    public View f;
    public int g = 0x800003;
    public boolean h;
    public i.a i;
    public i.d j;
    public PopupWindow.OnDismissListener k;
    public final PopupWindow.OnDismissListener l = new PopupWindow.OnDismissListener(this){
        public final h c;
        {
            this.c = h3;
        }

        public void onDismiss() {
            this.c.e();
        }
    };

    public h(Context context, e e3, View view, boolean bl, int n3) {
        this(context, e3, view, bl, n3, 0);
    }

    public h(Context context, e e3, View view, boolean bl, int n3, int n4) {
        this.a = context;
        this.b = e3;
        this.f = view;
        this.c = bl;
        this.d = n3;
        this.e = n4;
    }

    public final i.d a() {
        Display display = ((WindowManager)this.a.getSystemService("window")).getDefaultDisplay();
        Object object = new Point();
        display.getRealSize((Point)object);
        object = Math.min(((Point)object).x, ((Point)object).y) >= this.a.getResources().getDimensionPixelSize(c.d.abc_cascading_menus_min_smallest_width) ? new b(this.a, this.f, this.d, this.e, this.c) : new k(this.a, this.b, this.f, this.d, this.e, this.c);
        ((i.d)object).n(this.b);
        ((i.d)object).w(this.l);
        ((i.d)object).r(this.f);
        object.m(this.i);
        ((i.d)object).t(this.h);
        ((i.d)object).u(this.g);
        return object;
    }

    public void b() {
        if (this.d()) {
            this.j.dismiss();
        }
    }

    public i.d c() {
        if (this.j == null) {
            this.j = this.a();
        }
        return this.j;
    }

    public boolean d() {
        i.d d3 = this.j;
        return d3 != null && d3.c();
    }

    public void e() {
        this.j = null;
        PopupWindow.OnDismissListener onDismissListener = this.k;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    public void f(View view) {
        this.f = view;
    }

    public void g(boolean bl) {
        this.h = bl;
        i.d d3 = this.j;
        if (d3 != null) {
            d3.t(bl);
        }
    }

    public void h(int n3) {
        this.g = n3;
    }

    public void i(PopupWindow.OnDismissListener onDismissListener) {
        this.k = onDismissListener;
    }

    public void j(i.a a4) {
        this.i = a4;
        i.d d3 = this.j;
        if (d3 != null) {
            d3.m(a4);
        }
    }

    public void k() {
        if (this.m()) {
            return;
        }
        throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
    }

    public final void l(int n3, int n4, boolean bl, boolean bl2) {
        i.d d3 = this.c();
        d3.x(bl2);
        if (bl) {
            int n5 = n3;
            if ((s.b(this.g, this.f.getLayoutDirection()) & 7) == 5) {
                n5 = n3 - this.f.getWidth();
            }
            d3.v(n5);
            d3.y(n4);
            n3 = (int)(this.a.getResources().getDisplayMetrics().density * 48.0f / 2.0f);
            d3.s(new Rect(n5 - n3, n4 - n3, n5 + n3, n4 + n3));
        }
        d3.e();
    }

    public boolean m() {
        if (this.d()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        this.l(0, 0, false, false);
        return true;
    }

    public boolean n(int n3, int n4) {
        if (this.d()) {
            return true;
        }
        if (this.f == null) {
            return false;
        }
        this.l(n3, n4, true, true);
        return true;
    }
}

