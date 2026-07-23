/*
 * Decompiled with CFR 0.152.
 */
package u;

import r.d;
import u.d;
import u.e;
import u.m;

public class l
extends m {
    @Override
    public void H1(int n3, int n4, int n5, int n6) {
        int n7 = this.E1();
        int n8 = this.F1();
        int n9 = this.G1();
        int n10 = this.D1();
        n8 = n7 + n8;
        n7 = n9 + n10;
        int n11 = this.W0;
        boolean bl = false;
        n10 = n8;
        n9 = n7;
        if (n11 > 0) {
            n10 = n8 + this.V0[0].Y();
            n9 = n7 + this.V0[0].z();
        }
        n10 = Math.max(this.K(), n10);
        n9 = Math.max(this.J(), n9);
        if (n3 != 0x40000000) {
            n4 = n3 == Integer.MIN_VALUE ? Math.min(n10, n4) : (n3 == 0 ? n10 : 0);
        }
        if (n5 != 0x40000000) {
            n6 = n5 == Integer.MIN_VALUE ? Math.min(n9, n6) : (n5 == 0 ? n9 : 0);
        }
        this.M1(n4, n6);
        this.p1(n4);
        this.Q0(n6);
        if (this.W0 > 0) {
            bl = true;
        }
        this.L1(bl);
    }

    @Override
    public void g(d object, boolean bl) {
        super.g((d)object, bl);
        if (this.W0 > 0) {
            object = this.V0[0];
            ((e)object).w0();
            d.a a4 = d.a.d;
            ((e)object).j(a4, this, a4);
            a4 = d.a.f;
            ((e)object).j(a4, this, a4);
            a4 = d.a.e;
            ((e)object).j(a4, this, a4);
            a4 = d.a.g;
            ((e)object).j(a4, this, a4);
        }
    }
}

