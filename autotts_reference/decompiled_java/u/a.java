/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.HashMap;
import r.i;
import u.d;
import u.e;
import u.j;

public class a
extends j {
    public int X0 = 0;
    public boolean Y0 = true;
    public int Z0 = 0;
    public boolean a1 = false;

    public int A1() {
        return this.X0;
    }

    public int B1() {
        return this.Z0;
    }

    public int C1() {
        int n3 = this.X0;
        if (n3 != 0 && n3 != 1) {
            if (n3 != 2 && n3 != 3) {
                return -1;
            }
            return 1;
        }
        return 0;
    }

    public void D1() {
        for (int i3 = 0; i3 < this.W0; ++i3) {
            e e3 = this.V0[i3];
            if (!this.Y0 && !e3.h()) continue;
            int n3 = this.X0;
            if (n3 != 0 && n3 != 1) {
                if (n3 != 2 && n3 != 3) continue;
                e3.X0(1, true);
                continue;
            }
            e3.X0(0, true);
        }
    }

    public void E1(boolean bl) {
        this.Y0 = bl;
    }

    public void F1(int n3) {
        this.X0 = n3;
    }

    public void G1(int n3) {
        this.Z0 = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void g(r.d d3, boolean bl) {
        int n3;
        int n4;
        Object object;
        d d4;
        int n5;
        block14: {
            d[] dArray;
            d[] dArray2 = this.Y;
            dArray2[0] = this.Q;
            dArray2[2] = this.R;
            dArray2[1] = this.S;
            dArray2[3] = this.T;
            for (n5 = 0; n5 < (dArray = this.Y).length; ++n5) {
                d d5 = dArray[n5];
                d5.i = d3.q(d5);
            }
            n5 = this.X0;
            if (n5 < 0 || n5 >= 4) return;
            d4 = dArray[n5];
            if (!this.a1) {
                this.y1();
            }
            if (this.a1) {
                this.a1 = false;
                n5 = this.X0;
                if (n5 != 0 && n5 != 1) {
                    if (n5 != 2 && n5 != 3) return;
                    d3.f(this.R.i, this.i0);
                    d3.f(this.T.i, this.i0);
                    return;
                } else {
                    d3.f(this.Q.i, this.h0);
                    d3.f(this.S.i, this.h0);
                    return;
                }
            }
            for (n5 = 0; n5 < this.W0; ++n5) {
                object = this.V0[n5];
                if (!this.Y0 && !((e)object).h() || ((n4 = this.X0) != 0 && n4 != 1 || ((e)object).C() != e.b.e || ((e)object).Q.f == null || ((e)object).S.f == null) && ((n4 = this.X0) != 2 && n4 != 3 || ((e)object).V() != e.b.e || ((e)object).R.f == null || ((e)object).T.f == null)) continue;
                bl = true;
                break block14;
            }
            bl = false;
        }
        n5 = !this.Q.l() && !this.S.l() ? 0 : 1;
        n4 = !this.R.l() && !this.T.l() ? 0 : 1;
        n5 = !bl && ((n3 = this.X0) == 0 && n5 != 0 || n3 == 2 && n4 != 0 || n3 == 1 && n5 != 0 || n3 == 3 && n4 != 0) ? 1 : 0;
        n5 = n5 == 0 ? 4 : 5;
        for (n4 = 0; n4 < this.W0; ++n4) {
            e e3 = this.V0[n4];
            if (!this.Y0 && !e3.h()) continue;
            object = d3.q(e3.Y[this.X0]);
            d[] dArray = e3.Y;
            int n6 = this.X0;
            d d6 = dArray[n6];
            d6.i = object;
            d d7 = d6.f;
            n3 = d7 != null && d7.d == this ? d6.g : 0;
            if (n6 != 0 && n6 != 2) {
                d3.g(d4.i, (i)object, this.Z0 + n3, bl);
            } else {
                d3.i(d4.i, (i)object, this.Z0 - n3, bl);
            }
            d3.e(d4.i, (i)object, this.Z0 + n3, n5);
        }
        n5 = this.X0;
        if (n5 == 0) {
            d3.e(this.S.i, this.Q.i, 0, 8);
            d3.e(this.Q.i, this.c0.S.i, 0, 4);
            d3.e(this.Q.i, this.c0.Q.i, 0, 0);
            return;
        }
        if (n5 == 1) {
            d3.e(this.Q.i, this.S.i, 0, 8);
            d3.e(this.Q.i, this.c0.Q.i, 0, 4);
            d3.e(this.Q.i, this.c0.S.i, 0, 0);
            return;
        }
        if (n5 == 2) {
            d3.e(this.T.i, this.R.i, 0, 8);
            d3.e(this.R.i, this.c0.T.i, 0, 4);
            d3.e(this.R.i, this.c0.R.i, 0, 0);
            return;
        }
        if (n5 != 3) return;
        d3.e(this.R.i, this.T.i, 0, 8);
        d3.e(this.R.i, this.c0.R.i, 0, 4);
        d3.e(this.R.i, this.c0.T.i, 0, 0);
    }

    @Override
    public boolean h() {
        return true;
    }

    @Override
    public void n(e e3, HashMap hashMap) {
        super.n(e3, hashMap);
        e3 = (a)e3;
        this.X0 = ((a)e3).X0;
        this.Y0 = ((a)e3).Y0;
        this.Z0 = ((a)e3).Z0;
    }

    @Override
    public boolean p0() {
        return this.a1;
    }

    @Override
    public boolean q0() {
        return this.a1;
    }

    @Override
    public String toString() {
        CharSequence charSequence;
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("[Barrier] ");
        ((StringBuilder)charSequence2).append(this.v());
        ((StringBuilder)charSequence2).append(" {");
        charSequence2 = ((StringBuilder)charSequence2).toString();
        for (int i3 = 0; i3 < this.W0; ++i3) {
            e e3 = this.V0[i3];
            charSequence = charSequence2;
            if (i3 > 0) {
                charSequence = new StringBuilder();
                charSequence.append((String)charSequence2);
                charSequence.append(", ");
                charSequence = charSequence.toString();
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(e3.v());
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        charSequence.append((String)charSequence2);
        charSequence.append("}");
        return charSequence.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean y1() {
        int n3;
        e e3;
        int n4;
        int n5;
        int n6 = 0;
        int n7 = 1;
        for (n5 = 0; n5 < (n4 = this.W0); ++n5) {
            block26: {
                block27: {
                    block28: {
                        block25: {
                            e3 = this.V0[n5];
                            if (this.Y0 || e3.h()) break block25;
                            n4 = n7;
                            break block26;
                        }
                        n4 = this.X0;
                        if ((n4 == 0 || n4 == 1) && !e3.p0()) break block27;
                        n3 = this.X0;
                        if (n3 == 2) break block28;
                        n4 = n7;
                        if (n3 != 3) break block26;
                    }
                    n4 = n7;
                    if (e3.q0()) break block26;
                }
                n4 = 0;
            }
            n7 = n4;
        }
        if (n7 != 0 && n4 > 0) {
            n5 = 0;
            n4 = 0;
        } else {
            return false;
        }
        while (n6 < this.W0) {
            e3 = this.V0[n6];
            if (this.Y0 || e3.h()) {
                int n8;
                n3 = n5;
                n7 = n4;
                if (n4 == 0) {
                    n7 = this.X0;
                    if (n7 == 0) {
                        n5 = e3.q(d.a.d).e();
                    } else if (n7 == 1) {
                        n5 = e3.q(d.a.f).e();
                    } else if (n7 == 2) {
                        n5 = e3.q(d.a.e).e();
                    } else if (n7 == 3) {
                        n5 = e3.q(d.a.g).e();
                    }
                    n7 = 1;
                    n3 = n5;
                }
                if ((n8 = this.X0) == 0) {
                    n5 = Math.min(n3, e3.q(d.a.d).e());
                    n4 = n7;
                } else if (n8 == 1) {
                    n5 = Math.max(n3, e3.q(d.a.f).e());
                    n4 = n7;
                } else if (n8 == 2) {
                    n5 = Math.min(n3, e3.q(d.a.e).e());
                    n4 = n7;
                } else {
                    n5 = n3;
                    n4 = n7;
                    if (n8 == 3) {
                        n5 = Math.max(n3, e3.q(d.a.g).e());
                        n4 = n7;
                    }
                }
            }
            ++n6;
        }
        n7 = n5 + this.Z0;
        n5 = this.X0;
        if (n5 != 0 && n5 != 1) {
            this.N0(n7, n7);
        } else {
            this.K0(n7, n7);
        }
        this.a1 = true;
        return true;
    }

    public boolean z1() {
        return this.Y0;
    }
}

