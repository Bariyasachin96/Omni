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
import v.n;
import v.p;

public class l
extends p {
    public static int[] k = new int[2];

    public l(e e3) {
        super(e3);
        this.h.e = f.a.f;
        this.i.e = f.a.g;
        this.f = 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(d object) {
        int n3;
        int n4;
        d d3;
        float f3;
        int n5;
        block31: {
            Object object2;
            block32: {
                block42: {
                    block43: {
                        int n6;
                        int n7;
                        block41: {
                            block40: {
                                int n8;
                                int n9;
                                int n10;
                                int n11;
                                block33: {
                                    block37: {
                                        block39: {
                                            float f4;
                                            block38: {
                                                block34: {
                                                    block35: {
                                                        block36: {
                                                            n5 = v.l$a.a[this.j.ordinal()];
                                                            if (n5 != 1) {
                                                                if (n5 != 2) {
                                                                    if (n5 == 3) {
                                                                        e e3 = this.b;
                                                                        this.n((d)object, e3.Q, e3.S, 0);
                                                                        return;
                                                                    }
                                                                } else {
                                                                    this.o((d)object);
                                                                }
                                                            } else {
                                                                this.p((d)object);
                                                            }
                                                            if (this.e.j || this.d != e.b.e) break block31;
                                                            object2 = this.b;
                                                            n5 = ((e)object2).w;
                                                            if (n5 == 2) break block32;
                                                            if (n5 != 3) break block31;
                                                            n5 = ((e)object2).x;
                                                            if (n5 == 0 || n5 == 3) break block33;
                                                            n5 = ((e)object2).y();
                                                            if (n5 == -1) break block34;
                                                            if (n5 == 0) break block35;
                                                            if (n5 == 1) break block36;
                                                            n5 = 0;
                                                            break block37;
                                                        }
                                                        object = this.b;
                                                        f4 = ((e)object).f.e.g;
                                                        f3 = ((e)object).x();
                                                        break block38;
                                                    }
                                                    object = this.b;
                                                    f3 = (float)((e)object).f.e.g / ((e)object).x();
                                                    break block39;
                                                }
                                                object = this.b;
                                                f4 = ((e)object).f.e.g;
                                                f3 = ((e)object).x();
                                            }
                                            f3 = f4 * f3;
                                        }
                                        n5 = (int)(f3 + 0.5f);
                                    }
                                    this.e.d(n5);
                                    break block31;
                                }
                                d3 = ((e)object2).f;
                                object = ((p)d3).h;
                                d3 = ((p)d3).i;
                                n5 = ((e)object2).Q.f != null ? 1 : 0;
                                n4 = ((e)object2).R.f != null ? 1 : 0;
                                n3 = ((e)object2).S.f != null ? 1 : 0;
                                n7 = ((e)object2).T.f != null ? 1 : 0;
                                n6 = ((e)object2).y();
                                if (n5 == 0 || n4 == 0 || n3 == 0 || n7 == 0) break block40;
                                f3 = this.b.x();
                                if (((f)object).j && ((f)d3).j) {
                                    object2 = this.h;
                                    if (!((f)object2).c) return;
                                    if (!this.i.c) {
                                        return;
                                    }
                                    int n12 = ((f)((f)object2).l.get((int)0)).g;
                                    int n13 = this.h.f;
                                    int n14 = ((f)this.i.l.get((int)0)).g;
                                    n3 = this.i.f;
                                    n7 = ((f)object).g;
                                    int n15 = ((f)object).f;
                                    n4 = ((f)d3).g;
                                    n5 = ((f)d3).f;
                                    this.q(k, n13 + n12, n14 - n3, n15 + n7, n4 - n5, f3, n6);
                                    this.e.d(k[0]);
                                    this.b.f.e.d(k[1]);
                                    return;
                                }
                                object2 = this.h;
                                if (((f)object2).j) {
                                    f f5 = this.i;
                                    if (f5.j) {
                                        if (!((f)object).c) return;
                                        if (!((f)d3).c) {
                                            return;
                                        }
                                        n11 = ((f)object2).g;
                                        n10 = ((f)object2).f;
                                        n7 = f5.g;
                                        n5 = f5.f;
                                        n3 = ((f)((f)object).l.get((int)0)).g;
                                        n4 = ((f)object).f;
                                        n9 = ((f)((f)d3).l.get((int)0)).g;
                                        n8 = ((f)d3).f;
                                        this.q(k, n11 + n10, n7 - n5, n4 + n3, n9 - n8, f3, n6);
                                        this.e.d(k[0]);
                                        this.b.f.e.d(k[1]);
                                    }
                                }
                                object2 = this.h;
                                if (!((f)object2).c) return;
                                if (!this.i.c) return;
                                if (!((f)object).c) return;
                                if (!((f)d3).c) {
                                    return;
                                }
                                n3 = ((f)((f)object2).l.get((int)0)).g;
                                n11 = this.h.f;
                                n5 = ((f)this.i.l.get((int)0)).g;
                                n4 = this.i.f;
                                n8 = ((f)((f)object).l.get((int)0)).g;
                                n7 = ((f)object).f;
                                n9 = ((f)((f)d3).l.get((int)0)).g;
                                n10 = ((f)d3).f;
                                this.q(k, n11 + n3, n5 - n4, n7 + n8, n9 - n10, f3, n6);
                                this.e.d(k[0]);
                                this.b.f.e.d(k[1]);
                                break block31;
                            }
                            if (n5 == 0 || n3 == 0) break block41;
                            if (!this.h.c) return;
                            if (!this.i.c) {
                                return;
                            }
                            f3 = this.b.x();
                            n4 = ((f)this.h.l.get((int)0)).g + this.h.f;
                            n5 = ((f)this.i.l.get((int)0)).g - this.i.f;
                            if (n6 != -1 && n6 != 0) {
                                if (n6 == 1) {
                                    n3 = (int)((float)(n5 = this.g(n5 - n4, 0)) / f3 + 0.5f);
                                    if (n3 != (n4 = this.g(n3, 1))) {
                                        n5 = (int)((float)n4 * f3 + 0.5f);
                                    }
                                    this.e.d(n5);
                                    this.b.f.e.d(n4);
                                }
                                break block31;
                            } else {
                                n3 = (int)((float)(n5 = this.g(n5 - n4, 0)) * f3 + 0.5f);
                                if (n3 != (n4 = this.g(n3, 1))) {
                                    n5 = (int)((float)n4 / f3 + 0.5f);
                                }
                                this.e.d(n5);
                                this.b.f.e.d(n4);
                            }
                            break block31;
                        }
                        if (n4 == 0 || n7 == 0) break block31;
                        if (!((f)object).c) return;
                        if (!((f)d3).c) {
                            return;
                        }
                        f3 = this.b.x();
                        n4 = ((f)((f)object).l.get((int)0)).g + ((f)object).f;
                        n5 = ((f)((f)d3).l.get((int)0)).g - ((f)d3).f;
                        if (n6 == -1) break block42;
                        if (n6 == 0) break block43;
                        if (n6 == 1) break block42;
                        break block31;
                    }
                    n3 = (int)((float)(n5 = this.g(n5 - n4, 1)) * f3 + 0.5f);
                    if (n3 != (n4 = this.g(n3, 0))) {
                        n5 = (int)((float)n4 / f3 + 0.5f);
                    }
                    this.e.d(n4);
                    this.b.f.e.d(n5);
                    break block31;
                }
                if ((n3 = (int)((float)(n5 = this.g(n5 - n4, 1)) / f3 + 0.5f)) != (n4 = this.g(n3, 0))) {
                    n5 = (int)((float)n4 * f3 + 0.5f);
                }
                this.e.d(n4);
                this.b.f.e.d(n5);
                break block31;
            }
            object = ((e)object2).M();
            if (object != null) {
                object = ((e)object).e.e;
                if (((f)object).j) {
                    f3 = this.b.B;
                    n5 = (int)((float)((f)object).g * f3 + 0.5f);
                    this.e.d(n5);
                }
            }
        }
        object = this.h;
        if (!((f)object).c) return;
        d3 = this.i;
        if (!((f)d3).c) {
            return;
        }
        if (((f)object).j && ((f)d3).j && this.e.j) {
            return;
        }
        if (!this.e.j && this.d == e.b.e) {
            object = this.b;
            if (((e)object).w == 0 && !((e)object).k0()) {
                d3 = (f)this.h.l.get(0);
                object = (f)this.i.l.get(0);
                n5 = ((f)d3).g;
                d3 = this.h;
                n4 = n5 + ((f)d3).f;
                n5 = ((f)object).g + this.i.f;
                ((f)d3).d(n4);
                this.i.d(n5);
                this.e.d(n5 - n4);
                return;
            }
        }
        if (!this.e.j && this.d == e.b.e && this.a == 1 && this.h.l.size() > 0 && this.i.l.size() > 0) {
            object = (f)this.h.l.get(0);
            d3 = (f)this.i.l.get(0);
            n4 = ((f)object).g;
            n5 = this.h.f;
            n5 = Math.min(((f)d3).g + this.i.f - (n4 + n5), this.e.m);
            object = this.b;
            n3 = ((e)object).A;
            n5 = n4 = Math.max(((e)object).z, n5);
            if (n3 > 0) {
                n5 = Math.min(n3, n4);
            }
            this.e.d(n5);
        }
        if (!this.e.j) {
            return;
        }
        d3 = (f)this.h.l.get(0);
        object = (f)this.i.l.get(0);
        n4 = ((f)d3).g + this.h.f;
        n5 = ((f)object).g + this.i.f;
        f3 = this.b.A();
        if (d3 == object) {
            n4 = ((f)d3).g;
            n5 = ((f)object).g;
            f3 = 0.5f;
        }
        n3 = this.e.g;
        this.h.d((int)((float)n4 + 0.5f + (float)(n5 - n4 - n3) * f3));
        this.i.d(this.h.g + this.e.g);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void d() {
        e e3 = this.b;
        if (e3.a) {
            this.e.d(e3.Y());
        }
        if (!this.e.j) {
            e.b b3;
            this.d = b3 = this.b.C();
            if (b3 != e.b.e) {
                e e4;
                e.b b4 = e.b.f;
                if (b3 == b4 && (e4 = this.b.M()) != null && (e4.C() == e.b.c || e4.C() == b4)) {
                    int n3 = e4.Y();
                    int n4 = this.b.Q.f();
                    int n5 = this.b.S.f();
                    this.b(this.h, e4.e.h, this.b.Q.f());
                    this.b(this.i, e4.e.i, -this.b.S.f());
                    this.e.d(n3 - n4 - n5);
                    return;
                }
                if (this.d == e.b.c) {
                    this.e.d(this.b.Y());
                }
            }
        } else {
            e e5;
            e.b b5 = this.d;
            e.b b6 = e.b.f;
            if (b5 == b6 && (e5 = this.b.M()) != null && (e5.C() == e.b.c || e5.C() == b6)) {
                this.b(this.h, e5.e.h, this.b.Q.f());
                this.b(this.i, e5.e.i, -this.b.S.f());
                return;
            }
        }
        g g3 = this.e;
        if (g3.j) {
            e e6 = this.b;
            if (e6.a) {
                u.d[] dArray = e6.Y;
                u.d d3 = dArray[0];
                u.d d4 = d3.f;
                if (d4 != null && dArray[1].f != null) {
                    f f3;
                    if (e6.k0()) {
                        this.h.f = this.b.Y[0].f();
                        this.i.f = -this.b.Y[1].f();
                        return;
                    }
                    f f4 = this.h(this.b.Y[0]);
                    if (f4 != null) {
                        this.b(this.h, f4, this.b.Y[0].f());
                    }
                    if ((f3 = this.h(this.b.Y[1])) != null) {
                        this.b(this.i, f3, -this.b.Y[1].f());
                    }
                    this.h.b = true;
                    this.i.b = true;
                    return;
                }
                if (d4 != null) {
                    f f5 = this.h(d3);
                    if (f5 == null) return;
                    this.b(this.h, f5, this.b.Y[0].f());
                    this.b(this.i, this.h, this.e.g);
                    return;
                }
                u.d d5 = dArray[1];
                if (d5.f != null) {
                    f f6 = this.h(d5);
                    if (f6 == null) return;
                    this.b(this.i, f6, -this.b.Y[1].f());
                    this.b(this.h, this.i, -this.e.g);
                    return;
                }
                if (e6 instanceof i || e6.M() == null || this.b.q((d.a)d.a.i).f != null) return;
                f f7 = this.b.M().e.h;
                this.b(this.h, f7, this.b.Z());
                this.b(this.i, this.h, this.e.g);
                return;
            }
        }
        if (this.d == e.b.e) {
            e e7 = this.b;
            int n6 = e7.w;
            if (n6 != 2) {
                if (n6 == 3) {
                    if (e7.x == 3) {
                        this.h.a = this;
                        this.i.a = this;
                        n n7 = e7.f;
                        n7.h.a = this;
                        n7.i.a = this;
                        g3.a = this;
                        if (e7.m0()) {
                            this.e.l.add(this.b.f.e);
                            this.b.f.e.k.add(this.e);
                            n n8 = this.b.f;
                            n8.e.a = this;
                            this.e.l.add(n8.h);
                            this.e.l.add(this.b.f.i);
                            this.b.f.h.k.add(this.e);
                            this.b.f.i.k.add(this.e);
                        } else if (this.b.k0()) {
                            this.b.f.e.l.add(this.e);
                            this.e.k.add(this.b.f.e);
                        } else {
                            this.b.f.e.l.add(this.e);
                        }
                    } else {
                        g g4 = e7.f.e;
                        g3.l.add(g4);
                        g4.k.add(this.e);
                        this.b.f.h.k.add(this.e);
                        this.b.f.i.k.add(this.e);
                        g g5 = this.e;
                        g5.b = true;
                        g5.k.add(this.h);
                        this.e.k.add(this.i);
                        this.h.l.add(this.e);
                        this.i.l.add(this.e);
                    }
                }
            } else {
                e e8 = e7.M();
                if (e8 != null) {
                    g g6 = e8.f.e;
                    this.e.l.add(g6);
                    g6.k.add(this.e);
                    g g7 = this.e;
                    g7.b = true;
                    g7.k.add(this.h);
                    this.e.k.add(this.i);
                }
            }
        }
        e e9 = this.b;
        u.d[] dArray = e9.Y;
        u.d d6 = dArray[0];
        u.d d7 = d6.f;
        if (d7 != null && dArray[1].f != null) {
            if (e9.k0()) {
                this.h.f = this.b.Y[0].f();
                this.i.f = -this.b.Y[1].f();
                return;
            }
            f f8 = this.h(this.b.Y[0]);
            f f9 = this.h(this.b.Y[1]);
            if (f8 != null) {
                f8.b(this);
            }
            if (f9 != null) {
                f9.b(this);
            }
            this.j = p.b.f;
            return;
        }
        if (d7 != null) {
            f f10 = this.h(d6);
            if (f10 == null) return;
            this.b(this.h, f10, this.b.Y[0].f());
            this.c(this.i, this.h, 1, this.e);
            return;
        }
        u.d d8 = dArray[1];
        if (d8.f != null) {
            f f11 = this.h(d8);
            if (f11 == null) return;
            this.b(this.i, f11, -this.b.Y[1].f());
            this.c(this.h, this.i, -1, this.e);
            return;
        }
        if (e9 instanceof i || e9.M() == null) return;
        f f12 = this.b.M().e.h;
        this.b(this.h, f12, this.b.Z());
        this.c(this.i, this.h, 1, this.e);
    }

    @Override
    public void e() {
        f f3 = this.h;
        if (f3.j) {
            this.b.r1(f3.g);
        }
    }

    @Override
    public void f() {
        this.c = null;
        this.h.c();
        this.i.c();
        this.e.c();
        this.g = false;
    }

    @Override
    public boolean m() {
        if (this.d == e.b.e) {
            return this.b.w == 0;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void q(int[] nArray, int n3, int n4, int n5, int n6, float f3, int n7) {
        n3 = n4 - n3;
        n4 = n6 - n5;
        if (n7 != -1) {
            if (n7 != 0) {
                if (n7 != 1) return;
                n4 = (int)((float)n3 * f3 + 0.5f);
                nArray[0] = n3;
                nArray[1] = n4;
                return;
            }
            nArray[0] = (int)((float)n4 * f3 + 0.5f);
            nArray[1] = n4;
            return;
        }
        n6 = (int)((float)n4 * f3 + 0.5f);
        n5 = (int)((float)n3 / f3 + 0.5f);
        if (n6 <= n3) {
            nArray[0] = n6;
            nArray[1] = n4;
            return;
        }
        if (n5 > n4) return;
        nArray[0] = n3;
        nArray[1] = n5;
    }

    public void r() {
        this.g = false;
        this.h.c();
        this.h.j = false;
        this.i.c();
        this.i.j = false;
        this.e.j = false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HorizontalRun ");
        stringBuilder.append(this.b.v());
        return stringBuilder.toString();
    }
}

