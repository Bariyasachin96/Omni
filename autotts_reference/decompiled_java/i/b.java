/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package i;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import i.c;
import o.r;

public abstract class b {
    public final Context a;
    public r b;
    public r c;

    public b(Context context) {
        this.a = context;
    }

    public final MenuItem c(MenuItem menuItem) {
        if (menuItem instanceof i0.b) {
            MenuItem menuItem2;
            i0.b b3 = (i0.b)menuItem;
            if (this.b == null) {
                this.b = new r();
            }
            menuItem = menuItem2 = (MenuItem)this.b.get(b3);
            if (menuItem2 == null) {
                menuItem = new c(this.a, b3);
                this.b.put(b3, menuItem);
            }
            return menuItem;
        }
        return menuItem;
    }

    public final SubMenu d(SubMenu subMenu) {
        return subMenu;
    }

    public final void e() {
        r r3 = this.b;
        if (r3 != null) {
            r3.clear();
        }
        if ((r3 = this.c) != null) {
            r3.clear();
        }
    }

    public final void f(int n3) {
        if (this.b != null) {
            int n4 = 0;
            while (n4 < this.b.size()) {
                int n5 = n4;
                if (((i0.b)this.b.f(n4)).getGroupId() == n3) {
                    this.b.h(n4);
                    n5 = n4 - 1;
                }
                n4 = n5 + 1;
            }
        }
    }

    public final void g(int n3) {
        if (this.b != null) {
            for (int i3 = 0; i3 < this.b.size(); ++i3) {
                if (((i0.b)this.b.f(i3)).getItemId() != n3) continue;
                this.b.h(i3);
                return;
            }
        }
    }
}

