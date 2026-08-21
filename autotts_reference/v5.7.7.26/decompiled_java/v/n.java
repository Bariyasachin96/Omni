/*
 * Decompiled with CFR 0.152.
 */
package v;

import u.d;
import u.e;
import u.i;
import v.d;
import v.f;
import v.g;
import v.l;
import v.p;

public class n
extends p {
    public f k;
    public g l;

    public n(e object) {
        super((e)object);
        this.k = object = new f(this);
        this.l = null;
        this.h.e = f.a.h;
        this.i.e = f.a.i;
        ((f)object).e = f.a.j;
        this.f = 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(d object) {
        int n3;
        float f3;
        int n4;
        block17: {
            block18: {
                block22: {
                    block24: {
                        float f4;
                        block23: {
                            block19: {
                                block20: {
                                    block21: {
                                        n4 = v.n$a.a[this.j.ordinal()];
                                        if (n4 != 1) {
                                            if (n4 != 2) {
                                                if (n4 == 3) {
                                                    e e3 = this.b;
                                                    this.n((d)object, e3.R, e3.T, 1);
                                                    return;
                                                }
                                            } else {
                                                this.o((d)object);
                                            }
                                        } else {
                                            this.p((d)object);
                                        }
                                        object = this.e;
                                        if (!((f)object).c || ((f)object).j || this.d != e.b.e) break block17;
                                        object = this.b;
                                        n4 = ((e)object).x;
                                        if (n4 == 2) break block18;
                                        if (n4 != 3 || !((e)object).e.e.j) break block17;
                                        n4 = ((e)object).y();
                                        if (n4 == -1) break block19;
                                        if (n4 == 0) break block20;
                                        if (n4 == 1) break block21;
                                        n4 = 0;
                                        break block22;
                                    }
                                    object = this.b;
                                    f4 = ((e)object).e.e.g;
                                    f3 = ((e)object).x();
                                    break block23;
                                }
                                object = this.b;
                                f3 = (float)((e)object).e.e.g * ((e)object).x();
                                break block24;
                            }
                            object = this.b;
                            f4 = ((e)object).e.e.g;
                            f3 = ((e)object).x();
                        }
                        f3 = f4 / f3;
                    }
                    n4 = (int)(f3 + 0.5f);
                }
                this.e.d(n4);
                break block17;
            }
            if ((object = ((e)object).M()) != null) {
                object = ((e)object).f.e;
                if (((f)object).j) {
                    f3 = this.b.E;
                    n4 = (int)((float)((f)object).g * f3 + 0.5f);
                    this.e.d(n4);
                }
            }
        }
        object = this.h;
        if (!((f)object).c) return;
        f f5 = this.i;
        if (!f5.c) {
            return;
        }
        if (((f)object).j && f5.j && this.e.j) {
            return;
        }
        if (!this.e.j && this.d == e.b.e) {
            object = this.b;
            if (((e)object).w == 0 && !((e)object).m0()) {
                f5 = (f)this.h.l.get(0);
                object = (f)this.i.l.get(0);
                n4 = f5.g;
                f5 = this.h;
                int n5 = ((f)object).g + this.i.f;
                f5.d(n4 += f5.f);
                this.i.d(n5);
                this.e.d(n5 - n4);
                return;
            }
        }
        if (!this.e.j && this.d == e.b.e && this.a == 1 && this.h.l.size() > 0 && this.i.l.size() > 0) {
            object = (f)this.h.l.get(0);
            f5 = (f)this.i.l.get(0);
            n3 = ((f)object).g;
            n4 = this.h.f;
            n3 = f5.g + this.i.f - (n3 + n4);
            object = this.e;
            n4 = ((g)object).m;
            if (n3 < n4) {
                ((g)object).d(n3);
            } else {
                ((g)object).d(n4);
            }
        }
        if (!this.e.j) {
            return;
        }
        if (this.h.l.size() <= 0) return;
        if (this.i.l.size() <= 0) return;
        object = (f)this.h.l.get(0);
        f5 = (f)this.i.l.get(0);
        n4 = ((f)object).g + this.h.f;
        n3 = f5.g + this.i.f;
        f3 = this.b.T();
        if (object == f5) {
            n4 = ((f)object).g;
            n3 = f5.g;
            f3 = 0.5f;
        }
        int n6 = this.e.g;
        this.h.d((int)((float)n4 + 0.5f + (float)(n3 - n4 - n6) * f3));
        this.i.d(this.h.g + this.e.g);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void d() {
        e e3;
        e e4 = this.b;
        if (e4.a) {
            this.e.d(e4.z());
        }
        if (!this.e.j) {
            e.b b3;
            this.d = this.b.V();
            if (this.b.b0()) {
                this.l = new v.a(this);
            }
            if ((b3 = this.d) != e.b.e) {
                e e5;
                if (b3 == e.b.f && (e5 = this.b.M()) != null && e5.V() == e.b.c) {
                    int n3 = e5.z();
                    int n4 = this.b.R.f();
                    int n5 = this.b.T.f();
                    this.b(this.h, e5.f.h, this.b.R.f());
                    this.b(this.i, e5.f.i, -this.b.T.f());
                    this.e.d(n3 - n4 - n5);
                    return;
                }
                if (this.d == e.b.c) {
                    this.e.d(this.b.z());
                }
            }
        } else if (this.d == e.b.f && (e3 = this.b.M()) != null && e3.V() == e.b.c) {
            this.b(this.h, e3.f.h, this.b.R.f());
            this.b(this.i, e3.f.i, -this.b.T.f());
            return;
        }
        g g3 = this.e;
        boolean bl = g3.j;
        if (bl) {
            e e6 = this.b;
            if (e6.a) {
                u.d[] dArray = e6.Y;
                u.d d3 = dArray[2];
                u.d d4 = d3.f;
                if (d4 != null && dArray[3].f != null) {
                    if (e6.m0()) {
                        this.h.f = this.b.Y[2].f();
                        this.i.f = -this.b.Y[3].f();
                    } else {
                        f f3;
                        f f4 = this.h(this.b.Y[2]);
                        if (f4 != null) {
                            this.b(this.h, f4, this.b.Y[2].f());
                        }
                        if ((f3 = this.h(this.b.Y[3])) != null) {
                            this.b(this.i, f3, -this.b.Y[3].f());
                        }
                        this.h.b = true;
                        this.i.b = true;
                    }
                    if (!this.b.b0()) return;
                    this.b(this.k, this.h, this.b.r());
                    return;
                }
                if (d4 != null) {
                    f f5 = this.h(d3);
                    if (f5 == null) return;
                    this.b(this.h, f5, this.b.Y[2].f());
                    this.b(this.i, this.h, this.e.g);
                    if (!this.b.b0()) return;
                    this.b(this.k, this.h, this.b.r());
                    return;
                }
                d4 = dArray[3];
                if (d4.f != null) {
                    f f6 = this.h(d4);
                    if (f6 != null) {
                        this.b(this.i, f6, -this.b.Y[3].f());
                        this.b(this.h, this.i, -this.e.g);
                    }
                    if (!this.b.b0()) return;
                    this.b(this.k, this.h, this.b.r());
                    return;
                }
                u.d d5 = dArray[4];
                if (d5.f != null) {
                    f f7 = this.h(d5);
                    if (f7 == null) return;
                    this.b(this.k, f7, 0);
                    this.b(this.h, this.k, -this.b.r());
                    this.b(this.i, this.h, this.e.g);
                    return;
                }
                if (e6 instanceof i || e6.M() == null || this.b.q((d.a)d.a.i).f != null) return;
                f f8 = this.b.M().f.h;
                this.b(this.h, f8, this.b.a0());
                this.b(this.i, this.h, this.e.g);
                if (!this.b.b0()) return;
                this.b(this.k, this.h, this.b.r());
                return;
            }
        }
        if (!bl && this.d == e.b.e) {
            e e7 = this.b;
            int n6 = e7.x;
            if (n6 != 2) {
                if (n6 == 3 && !e7.m0()) {
                    e e8 = this.b;
                    if (e8.w != 3) {
                        g g4 = e8.e.e;
                        this.e.l.add(g4);
                        g4.k.add(this.e);
                        g g5 = this.e;
                        g5.b = true;
                        g5.k.add(this.h);
                        this.e.k.add(this.i);
                    }
                }
            } else {
                e e9 = e7.M();
                if (e9 != null) {
                    g g6 = e9.f.e;
                    this.e.l.add(g6);
                    g6.k.add(this.e);
                    g g7 = this.e;
                    g7.b = true;
                    g7.k.add(this.h);
                    this.e.k.add(this.i);
                }
            }
        } else {
            g3.b(this);
        }
        e e10 = this.b;
        u.d[] dArray = e10.Y;
        u.d d6 = dArray[2];
        u.d d7 = d6.f;
        if (d7 != null && dArray[3].f != null) {
            if (e10.m0()) {
                this.h.f = this.b.Y[2].f();
                this.i.f = -this.b.Y[3].f();
            } else {
                f f9 = this.h(this.b.Y[2]);
                f f10 = this.h(this.b.Y[3]);
                if (f9 != null) {
                    f9.b(this);
                }
                if (f10 != null) {
                    f10.b(this);
                }
                this.j = p.b.f;
            }
            if (this.b.b0()) {
                this.c(this.k, this.h, 1, this.l);
            }
        } else if (d7 != null) {
            f f11 = this.h(d6);
            if (f11 != null) {
                e.b b4;
                e.b b5;
                this.b(this.h, f11, this.b.Y[2].f());
                this.c(this.i, this.h, 1, this.e);
                if (this.b.b0()) {
                    this.c(this.k, this.h, 1, this.l);
                }
                if ((b5 = this.d) == (b4 = e.b.e) && this.b.x() > 0.0f) {
                    l l3 = this.b.e;
                    if (l3.d == b4) {
                        l3.e.k.add(this.e);
                        this.e.l.add(this.b.e.e);
                        this.e.a = this;
                    }
                }
            }
        } else {
            d7 = dArray[3];
            if (d7.f != null) {
                f f12 = this.h(d7);
                if (f12 != null) {
                    this.b(this.i, f12, -this.b.Y[3].f());
                    this.c(this.h, this.i, -1, this.e);
                    if (this.b.b0()) {
                        this.c(this.k, this.h, 1, this.l);
                    }
                }
            } else {
                u.d d8 = dArray[4];
                if (d8.f != null) {
                    f f13 = this.h(d8);
                    if (f13 != null) {
                        this.b(this.k, f13, 0);
                        this.c(this.h, this.k, -1, this.l);
                        this.c(this.i, this.h, 1, this.e);
                    }
                } else if (!(e10 instanceof i) && e10.M() != null) {
                    e.b b6;
                    e.b b7;
                    f f14 = this.b.M().f.h;
                    this.b(this.h, f14, this.b.a0());
                    this.c(this.i, this.h, 1, this.e);
                    if (this.b.b0()) {
                        this.c(this.k, this.h, 1, this.l);
                    }
                    if ((b7 = this.d) == (b6 = e.b.e) && this.b.x() > 0.0f) {
                        l l4 = this.b.e;
                        if (l4.d == b6) {
                            l4.e.k.add(this.e);
                            this.e.l.add(this.b.e.e);
                            this.e.a = this;
                        }
                    }
                }
            }
        }
        if (this.e.l.size() != 0) return;
        this.e.c = true;
    }

    @Override
    public void e() {
        f f3 = this.h;
        if (f3.j) {
            this.b.s1(f3.g);
        }
    }

    @Override
    public void f() {
        this.c = null;
        this.h.c();
        this.i.c();
        this.k.c();
        this.e.c();
        this.g = false;
    }

    @Override
    public boolean m() {
        if (this.d == e.b.e) {
            return this.b.x == 0;
        }
        return true;
    }

    public void q() {
        this.g = false;
        this.h.c();
        this.h.j = false;
        this.i.c();
        this.i.j = false;
        this.k.c();
        this.k.j = false;
        this.e.j = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VerticalRun ");
        stringBuilder.append(this.b.v());
        return stringBuilder.toString();
    }
}

