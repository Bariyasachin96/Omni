/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 */
package h;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class b {
    public Object c;
    public boolean d;

    public abstract void c();

    public abstract View d();

    public abstract Menu e();

    public abstract MenuInflater f();

    public abstract CharSequence g();

    public Object h() {
        return this.c;
    }

    public abstract CharSequence i();

    public boolean j() {
        return this.d;
    }

    public abstract void k();

    public abstract boolean l();

    public abstract void m(View var1);

    public abstract void n(int var1);

    public abstract void o(CharSequence var1);

    public void p(Object object) {
        this.c = object;
    }

    public abstract void q(int var1);

    public abstract void r(CharSequence var1);

    public void s(boolean bl) {
        this.d = bl;
    }

    public static interface a {
        public boolean a(b var1, MenuItem var2);

        public boolean b(b var1, Menu var2);

        public boolean c(b var1, Menu var2);

        public void d(b var1);
    }
}

