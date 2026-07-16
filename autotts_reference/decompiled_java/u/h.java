/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.HashMap;
import r.i;
import u.d;
import u.e;
import u.f;

public class h
extends e {
    public float V0 = -1.0f;
    public int W0 = -1;
    public int X0 = -1;
    public boolean Y0 = true;
    public d Z0 = this.R;
    public int a1 = 0;
    public int b1 = 0;
    public boolean c1;

    public h() {
        this.Z.clear();
        this.Z.add(this.Z0);
        int n3 = this.Y.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            this.Y[i3] = this.Z0;
        }
    }

    public float A1() {
        return this.V0;
    }

    public void B1(int n3) {
        this.Z0.t(n3);
        this.c1 = true;
    }

    public void C1(int n3) {
        if (n3 > -1) {
            this.V0 = -1.0f;
            this.W0 = n3;
            this.X0 = -1;
        }
    }

    public void D1(int n3) {
        if (n3 > -1) {
            this.V0 = -1.0f;
            this.W0 = -1;
            this.X0 = n3;
        }
    }

    public void E1(float f3) {
        if (f3 > -1.0f) {
            this.V0 = f3;
            this.W0 = -1;
            this.X0 = -1;
        }
    }

    public void F1(int n3) {
        if (this.a1 != n3) {
            this.a1 = n3;
            this.Z.clear();
            this.Z0 = this.a1 == 1 ? this.Q : this.R;
            this.Z.add(this.Z0);
            int n4 = this.Y.length;
            for (n3 = 0; n3 < n4; ++n3) {
                this.Y[n3] = this.Z0;
            }
        }
    }

    @Override
    public void g(r.d d3, boolean bl) {
        Object object = (f)this.M();
        if (object != null) {
            d d4 = ((e)object).q(d.a.d);
            Object object2 = ((e)object).q(d.a.f);
            e e3 = this.c0;
            boolean bl2 = true;
            boolean bl3 = e3 != null && e3.b0[0] == e.b.d;
            if (this.a1 == 0) {
                d4 = ((e)object).q(d.a.e);
                object2 = ((e)object).q(d.a.g);
                object = this.c0;
                bl3 = object != null && ((e)object).b0[1] == e.b.d ? bl2 : false;
            }
            if (this.c1 && this.Z0.n()) {
                object = d3.q(this.Z0);
                d3.f((i)object, this.Z0.e());
                if (this.W0 != -1) {
                    if (bl3) {
                        d3.h(d3.q(object2), (i)object, 0, 5);
                    }
                } else if (this.X0 != -1 && bl3) {
                    object2 = d3.q(object2);
                    d3.h((i)object, d3.q(d4), 0, 5);
                    d3.h((i)object2, (i)object, 0, 5);
                }
                this.c1 = false;
                return;
            }
            if (this.W0 != -1) {
                object = d3.q(this.Z0);
                d3.e((i)object, d3.q(d4), this.W0, 8);
                if (bl3) {
                    d3.h(d3.q(object2), (i)object, 0, 5);
                    return;
                }
            } else if (this.X0 != -1) {
                object = d3.q(this.Z0);
                object2 = d3.q(object2);
                d3.e((i)object, (i)object2, -this.X0, 8);
                if (bl3) {
                    d3.h((i)object, d3.q(d4), 0, 5);
                    d3.h((i)object2, (i)object, 0, 5);
                    return;
                }
            } else if (this.V0 != -1.0f) {
                d3.d(r.d.s(d3, d3.q(this.Z0), d3.q(object2), this.V0));
            }
        }
    }

    @Override
    public boolean h() {
        return true;
    }

    @Override
    public void n(e e3, HashMap hashMap) {
        super.n(e3, hashMap);
        e3 = (h)e3;
        this.V0 = ((h)e3).V0;
        this.W0 = ((h)e3).W0;
        this.X0 = ((h)e3).X0;
        this.Y0 = ((h)e3).Y0;
        this.F1(((h)e3).a1);
    }

    @Override
    public boolean p0() {
        return this.c1;
    }

    @Override
    public d q(d.a a4) {
        int n3 = u.h$a.a[a4.ordinal()];
        if (n3 != 1 && n3 != 2 ? (n3 == 3 || n3 == 4) && this.a1 == 0 : this.a1 == 1) {
            return this.Z0;
        }
        return null;
    }

    @Override
    public boolean q0() {
        return this.c1;
    }

    @Override
    public void v1(r.d d3, boolean bl) {
        if (this.M() == null) {
            return;
        }
        int n3 = d3.y(this.Z0);
        if (this.a1 == 1) {
            this.r1(n3);
            this.s1(0);
            this.Q0(this.M().z());
            this.p1(0);
            return;
        }
        this.r1(0);
        this.s1(n3);
        this.p1(this.M().Y());
        this.Q0(0);
    }

    public d w1() {
        return this.Z0;
    }

    public int x1() {
        return this.a1;
    }

    public int y1() {
        return this.W0;
    }

    public int z1() {
        return this.X0;
    }
}

