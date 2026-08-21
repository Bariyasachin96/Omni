/*
 * Decompiled with CFR 0.152.
 */
package i3;

import i3.a;
import i3.b;
import o3.k;

public abstract class c
extends a {
    public final g3.c d;
    public transient g3.a e;

    public c(g3.a a4, g3.c c3) {
        super(a4);
        this.d = c3;
    }

    @Override
    public g3.c b() {
        g3.c c3 = this.d;
        k.b(c3);
        return c3;
    }

    @Override
    public void k() {
        g3.a a4 = this.e;
        if (a4 != null && a4 != this) {
            this.b().a(g3.b.a);
            k.b(null);
            throw null;
        }
        this.e = b.c;
    }
}

