/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package j1;

import android.os.Bundle;
import androidx.lifecycle.f;
import androidx.savedstate.Recreator;
import j1.d;
import o3.g;
import o3.k;

public final class c {
    public static final a d = new a(null);
    public final d a;
    public final androidx.savedstate.a b;
    public boolean c;

    public c(d d3) {
        this.a = d3;
        this.b = new androidx.savedstate.a();
    }

    public /* synthetic */ c(d d3, g g3) {
        this(d3);
    }

    public static final c a(d d3) {
        return d.a(d3);
    }

    public final androidx.savedstate.a b() {
        return this.b;
    }

    public final void c() {
        f f3 = this.a.t();
        if (f3.b() == f.b.d) {
            f3.a(new Recreator(this.a));
            this.b.e(f3);
            this.c = true;
            return;
        }
        throw new IllegalStateException("Restarter must be created only during owner's initialization stage");
    }

    public final void d(Bundle object) {
        f f3;
        if (!this.c) {
            this.c();
        }
        if (!(f3 = this.a.t()).b().b(f.b.f)) {
            this.b.f((Bundle)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("performRestore cannot be called when owner is ");
        ((StringBuilder)object).append((Object)f3.b());
        throw new IllegalStateException(((StringBuilder)object).toString().toString());
    }

    public final void e(Bundle bundle) {
        k.e(bundle, "outBundle");
        this.b.g(bundle);
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(g g3) {
            this();
        }

        public final c a(d d3) {
            k.e(d3, "owner");
            return new c(d3, null);
        }
    }
}

