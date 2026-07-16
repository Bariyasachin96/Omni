/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.view.SubMenu
 *  android.view.View
 */
package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.view.menu.g;

public class l
extends e
implements SubMenu {
    public e B;
    public g C;

    public l(Context context, e e3, g g3) {
        super(context);
        this.B = e3;
        this.C = g3;
    }

    @Override
    public e F() {
        return this.B.F();
    }

    @Override
    public boolean I() {
        return this.B.I();
    }

    @Override
    public boolean J() {
        return this.B.J();
    }

    @Override
    public boolean K() {
        return this.B.K();
    }

    @Override
    public void W(e.a a4) {
        this.B.W(a4);
    }

    @Override
    public boolean f(g g3) {
        return this.B.f(g3);
    }

    public MenuItem getItem() {
        return this.C;
    }

    @Override
    public boolean h(e e3, MenuItem menuItem) {
        return super.h(e3, menuItem) || this.B.h(e3, menuItem);
        {
        }
    }

    public Menu j0() {
        return this.B;
    }

    @Override
    public boolean m(g g3) {
        return this.B.m(g3);
    }

    @Override
    public void setGroupDividerEnabled(boolean bl) {
        this.B.setGroupDividerEnabled(bl);
    }

    public SubMenu setHeaderIcon(int n3) {
        return (SubMenu)super.Z(n3);
    }

    public SubMenu setHeaderIcon(Drawable drawable) {
        return (SubMenu)super.a0(drawable);
    }

    public SubMenu setHeaderTitle(int n3) {
        return (SubMenu)super.c0(n3);
    }

    public SubMenu setHeaderTitle(CharSequence charSequence) {
        return (SubMenu)super.d0(charSequence);
    }

    public SubMenu setHeaderView(View view) {
        return (SubMenu)super.e0(view);
    }

    public SubMenu setIcon(int n3) {
        this.C.setIcon(n3);
        return this;
    }

    public SubMenu setIcon(Drawable drawable) {
        this.C.setIcon(drawable);
        return this;
    }

    @Override
    public void setQwertyMode(boolean bl) {
        this.B.setQwertyMode(bl);
    }

    @Override
    public String v() {
        Object object = this.C;
        int n3 = object != null ? ((g)object).getItemId() : 0;
        if (n3 == 0) {
            return null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(super.v());
        ((StringBuilder)object).append(":");
        ((StringBuilder)object).append(n3);
        return ((StringBuilder)object).toString();
    }
}

