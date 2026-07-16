/*
 * Decompiled with CFR 0.152.
 */
package o;

import e3.h;
import o.p;
import o.q;

public final class o
extends p {
    public int f;

    public o(int n3) {
        super(null);
        if (n3 >= 0) {
            this.h(q.c(n3));
            return;
        }
        throw new IllegalArgumentException("Capacity must be a positive value.");
    }

    public final void f() {
        this.f = q.a(this.c()) - this.e;
    }

    public final void g(int n3) {
        long[] lArray;
        if (n3 == 0) {
            lArray = q.a;
        } else {
            lArray = new long[(n3 + 15 & 0xFFFFFFF8) >> 3];
            h.n(lArray, -9187201950435737472L, 0, 0, 6, null);
        }
        this.a = lArray;
        int n4 = n3 >> 3;
        long l3 = lArray[n4];
        long l4 = 255L << ((n3 & 7) << 3);
        lArray[n4] = l3 & (l4 ^ 0xFFFFFFFFFFFFFFFFL) | l4;
        this.f();
    }

    public final void h(int n3) {
        n3 = n3 > 0 ? Math.max(7, q.b(n3)) : 0;
        this.d = n3;
        this.g(n3);
        this.b = new Object[n3];
        this.c = new Object[n3];
    }
}

