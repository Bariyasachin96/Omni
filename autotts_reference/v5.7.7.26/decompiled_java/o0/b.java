/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 */
package o0;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public abstract class b {
    public final Context a;
    public a b;
    public b c;

    public b(Context context) {
        this.a = context;
    }

    public abstract boolean a();

    public abstract boolean b();

    public abstract View c(MenuItem var1);

    public abstract boolean d();

    public abstract void e(SubMenu var1);

    public abstract boolean f();

    public void g() {
        this.c = null;
        this.b = null;
    }

    public void h(a a4) {
        this.b = a4;
    }

    public abstract void i(b var1);

    public void j(boolean bl) {
        a a4 = this.b;
        if (a4 != null) {
            a4.c(bl);
        }
    }

    public static interface a {
        public void c(boolean var1);
    }

    public static interface b {
        public void onActionProviderVisibilityChanged(boolean var1);
    }
}

