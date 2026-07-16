/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.os.Bundle
 */
package androidx.fragment.app;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.b0;
import androidx.lifecycle.e;
import androidx.lifecycle.f;
import androidx.lifecycle.l;
import androidx.lifecycle.u;
import androidx.lifecycle.z;
import androidx.savedstate.a;
import j1.c;
import j1.d;

public class c0
implements e,
d,
androidx.lifecycle.c0 {
    public final Fragment c;
    public final b0 d;
    public l e = null;
    public c f = null;

    public c0(Fragment fragment, b0 b02) {
        this.c = fragment;
        this.d = b02;
    }

    public void a(f.a a4) {
        this.e.h(a4);
    }

    @Override
    public a c() {
        this.d();
        return this.f.b();
    }

    public void d() {
        if (this.e == null) {
            c c3;
            this.e = new l(this);
            this.f = c3 = j1.c.a(this);
            c3.c();
            u.a(this);
        }
    }

    public boolean e() {
        return this.e != null;
    }

    public void f(Bundle bundle) {
        this.f.d(bundle);
    }

    public void g(Bundle bundle) {
        this.f.e(bundle);
    }

    public void h(f.b b3) {
        this.e.m(b3);
    }

    @Override
    public f1.a j() {
        Context context;
        block4: {
            context = this.c.n1().getApplicationContext();
            while (context instanceof ContextWrapper) {
                if (context instanceof Application) {
                    context = (Application)context;
                    break block4;
                }
                context = ((ContextWrapper)context).getBaseContext();
            }
            context = null;
        }
        f1.d d3 = new f1.d();
        if (context != null) {
            d3.b(z.a.e, context);
        }
        d3.b(u.a, this);
        d3.b(u.b, this);
        if (this.c.n() != null) {
            d3.b(u.c, this.c.n());
        }
        return d3;
    }

    @Override
    public b0 r() {
        this.d();
        return this.d;
    }

    @Override
    public f t() {
        this.d();
        return this.e;
    }
}

