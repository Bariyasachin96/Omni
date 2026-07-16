/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MenuItem
 *  android.view.View
 */
package androidx.fragment.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.l;
import n0.h;

public class j {
    public final l a;

    public j(l l3) {
        this.a = l3;
    }

    public static j b(l l3) {
        return new j((l)h.h(l3, "callbacks == null"));
    }

    public void a(Fragment fragment) {
        l l3 = this.a;
        l3.g.m(l3, l3, fragment);
    }

    public void c() {
        this.a.g.x();
    }

    public boolean d(MenuItem menuItem) {
        return this.a.g.A(menuItem);
    }

    public void e() {
        this.a.g.B();
    }

    public void f() {
        this.a.g.D();
    }

    public void g() {
        this.a.g.M();
    }

    public void h() {
        this.a.g.Q();
    }

    public void i() {
        this.a.g.R();
    }

    public void j() {
        this.a.g.T();
    }

    public boolean k() {
        return this.a.g.a0(true);
    }

    public FragmentManager l() {
        return this.a.g;
    }

    public void m() {
        this.a.g.R0();
    }

    public View n(View view, String string, Context context, AttributeSet attributeSet) {
        return this.a.g.w0().onCreateView(view, string, context, attributeSet);
    }
}

