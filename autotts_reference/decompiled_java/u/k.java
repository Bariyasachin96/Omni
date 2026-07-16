/*
 * Decompiled with CFR 0.152.
 */
package u;

import u.d;
import u.e;
import u.f;

public abstract class k {
    public static boolean[] a = new boolean[3];

    public static void a(f object, r.d d3, e e3) {
        int n3;
        int n4;
        e3.t = -1;
        e3.u = -1;
        Object object2 = ((e)object).b0[0];
        e.b b3 = e.b.d;
        if (object2 != b3 && e3.b0[0] == e.b.f) {
            n4 = e3.Q.g;
            n3 = ((e)object).Y() - e3.S.g;
            object2 = e3.Q;
            ((d)object2).i = d3.q(object2);
            object2 = e3.S;
            ((d)object2).i = d3.q(object2);
            d3.f(e3.Q.i, n4);
            d3.f(e3.S.i, n3);
            e3.t = 2;
            e3.T0(n4, n3);
        }
        if (((e)object).b0[1] != b3 && e3.b0[1] == e.b.f) {
            n4 = e3.R.g;
            n3 = ((e)object).z() - e3.T.g;
            object = e3.R;
            ((d)object).i = d3.q(object);
            object = e3.T;
            ((d)object).i = d3.q(object);
            d3.f(e3.R.i, n4);
            d3.f(e3.T.i, n3);
            if (e3.n0 > 0 || e3.X() == 8) {
                object = e3.U;
                ((d)object).i = d3.q(object);
                d3.f(e3.U.i, e3.n0 + n4);
            }
            e3.u = 2;
            e3.k1(n4, n3);
        }
    }

    public static final boolean b(int n3, int n4) {
        return (n3 & n4) == n4;
    }
}

