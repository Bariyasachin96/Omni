/*
 * Decompiled with CFR 0.152.
 */
package androidx.lifecycle;

import androidx.lifecycle.SavedStateHandleAttacher;
import androidx.lifecycle.c0;
import androidx.lifecycle.f;
import androidx.lifecycle.v;
import androidx.lifecycle.w;
import androidx.lifecycle.z;
import androidx.savedstate.a;
import f1.a;
import f1.c;
import o3.k;
import o3.n;

public abstract class u {
    public static final a.b a = new a.b(){};
    public static final a.b b = new a.b(){};
    public static final a.b c = new a.b(){};

    public static final void a(j1.d d3) {
        k.e(d3, "<this>");
        Object object = d3.t().b();
        if (object != f.b.d && object != f.b.e) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (d3.c().c("androidx.lifecycle.internal.SavedStateHandlesProvider") == null) {
            object = new v(d3.c(), (c0)((Object)d3));
            d3.c().h("androidx.lifecycle.internal.SavedStateHandlesProvider", (a.c)object);
            d3.t().a(new SavedStateHandleAttacher((v)object));
        }
    }

    public static final w b(c0 c02) {
        k.e(c02, "<this>");
        c c3 = new c();
        d d3 = d.d;
        c3.a(n.b(w.class), d3);
        return (w)new z(c02, c3.b()).b("androidx.lifecycle.internal.SavedStateHandlesVM", w.class);
    }
}

