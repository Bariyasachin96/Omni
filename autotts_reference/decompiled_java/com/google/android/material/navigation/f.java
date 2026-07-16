/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.SubMenu
 */
package com.google.android.material.navigation;

import android.view.MenuItem;
import android.view.SubMenu;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.i;
import com.google.android.material.navigation.a;
import java.util.ArrayList;
import java.util.List;

public class f {
    public final e a;
    public final List b;
    public int c = 0;
    public int d = 0;
    public int e = 0;

    public f(e e3) {
        this.a = e3;
        this.b = new ArrayList();
        this.f();
    }

    public int a() {
        return this.c;
    }

    public MenuItem b(int n3) {
        return (MenuItem)this.b.get(n3);
    }

    public int c() {
        return this.d;
    }

    public int d() {
        return this.e;
    }

    public boolean e(MenuItem menuItem, i i3, int n3) {
        return this.a.P(menuItem, i3, n3);
    }

    public void f() {
        Object object;
        this.b.clear();
        this.c = 0;
        this.d = 0;
        this.e = 0;
        for (int i3 = 0; i3 < this.a.size(); ++i3) {
            object = this.a.getItem(i3);
            if (object.hasSubMenu()) {
                List list;
                if (!this.b.isEmpty() && !((list = this.b).get(list.size() - 1) instanceof a) && object.isVisible()) {
                    this.b.add(new a());
                }
                this.b.add(object);
                SubMenu subMenu = object.getSubMenu();
                for (int i4 = 0; i4 < subMenu.size(); ++i4) {
                    list = subMenu.getItem(i4);
                    if (!object.isVisible()) {
                        list.setVisible(false);
                    }
                    this.b.add(list);
                    ++this.c;
                    if (!list.isVisible()) continue;
                    ++this.d;
                }
                this.b.add(new a());
                continue;
            }
            this.b.add(object);
            ++this.c;
            if (!object.isVisible()) continue;
            ++this.d;
            ++this.e;
        }
        if (!this.b.isEmpty() && (object = this.b).get(object.size() - 1) instanceof a) {
            object = this.b;
            object.remove(object.size() - 1);
        }
    }

    public int g() {
        return this.b.size();
    }
}

