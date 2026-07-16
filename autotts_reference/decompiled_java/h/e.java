/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 */
package h;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.menu.e;
import androidx.appcompat.widget.ActionBarContextView;
import h.b;
import h.g;
import java.lang.ref.WeakReference;

public class e
extends b
implements e.a {
    public Context e;
    public ActionBarContextView f;
    public b.a g;
    public WeakReference h;
    public boolean i;
    public boolean j;
    public androidx.appcompat.view.menu.e k;

    public e(Context object, ActionBarContextView actionBarContextView, b.a a4, boolean bl) {
        this.e = object;
        this.f = actionBarContextView;
        this.g = a4;
        object = new androidx.appcompat.view.menu.e(actionBarContextView.getContext()).X(1);
        this.k = object;
        ((androidx.appcompat.view.menu.e)object).W(this);
        this.j = bl;
    }

    @Override
    public boolean a(androidx.appcompat.view.menu.e e3, MenuItem menuItem) {
        return this.g.a(this, menuItem);
    }

    @Override
    public void b(androidx.appcompat.view.menu.e e3) {
        this.k();
        this.f.l();
    }

    @Override
    public void c() {
        if (this.i) {
            return;
        }
        this.i = true;
        this.g.d(this);
    }

    @Override
    public View d() {
        WeakReference weakReference = this.h;
        if (weakReference != null) {
            return (View)weakReference.get();
        }
        return null;
    }

    @Override
    public Menu e() {
        return this.k;
    }

    @Override
    public MenuInflater f() {
        return new g(this.f.getContext());
    }

    @Override
    public CharSequence g() {
        return this.f.getSubtitle();
    }

    @Override
    public CharSequence i() {
        return this.f.getTitle();
    }

    @Override
    public void k() {
        this.g.c(this, this.k);
    }

    @Override
    public boolean l() {
        return this.f.j();
    }

    @Override
    public void m(View object) {
        this.f.setCustomView((View)object);
        object = object != null ? new WeakReference<View>((View)object) : null;
        this.h = object;
    }

    @Override
    public void n(int n3) {
        this.o(this.e.getString(n3));
    }

    @Override
    public void o(CharSequence charSequence) {
        this.f.setSubtitle(charSequence);
    }

    @Override
    public void q(int n3) {
        this.r(this.e.getString(n3));
    }

    @Override
    public void r(CharSequence charSequence) {
        this.f.setTitle(charSequence);
    }

    @Override
    public void s(boolean bl) {
        super.s(bl);
        this.f.setTitleOptional(bl);
    }
}

