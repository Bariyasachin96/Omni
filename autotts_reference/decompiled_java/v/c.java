/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import u.e;
import v.d;
import v.f;
import v.g;
import v.p;

public class c
extends p {
    public ArrayList k = new ArrayList();
    public int l;

    public c(e e3, int n3) {
        super(e3);
        this.f = n3;
        this.q();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void a(d object) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        boolean bl;
        block89: {
            int n10;
            int n11;
            int n12;
            block99: {
                block97: {
                    float f3;
                    int n13;
                    float f4;
                    float f5;
                    int n14;
                    block98: {
                        int n15;
                        block96: {
                            int n16;
                            block88: {
                                block87: {
                                    block86: {
                                        if (!this.h.j) return;
                                        if (!this.i.j) {
                                            return;
                                        }
                                        object = this.b.M();
                                        bl = object instanceof u.f ? ((u.f)object).V1() : false;
                                        n14 = this.i.g - this.h.g;
                                        n9 = this.k.size();
                                        n8 = 0;
                                        while (true) {
                                            n7 = -1;
                                            if (n8 >= n9) break;
                                            n6 = n8;
                                            if (((p)this.k.get((int)n8)).b.X() == 8) {
                                                ++n8;
                                                continue;
                                            }
                                            break block86;
                                            break;
                                        }
                                        n6 = -1;
                                    }
                                    n8 = n5 = n9 - 1;
                                    while (true) {
                                        n4 = n7;
                                        if (n8 < 0) break block87;
                                        if (((p)this.k.get((int)n8)).b.X() != 8) break;
                                        --n8;
                                    }
                                    n4 = n8;
                                }
                                n12 = 0;
                                while (true) {
                                    if (n12 >= 2) {
                                        f5 = 0.0f;
                                        n15 = 0;
                                        n3 = 0;
                                        n7 = 0;
                                        break block88;
                                    }
                                    n3 = 0;
                                    n8 = 0;
                                    n7 = 0;
                                    f4 = 0.0f;
                                    for (n11 = 0; n11 < n9; ++n11) {
                                        block91: {
                                            p p3;
                                            block93: {
                                                block95: {
                                                    block94: {
                                                        block92: {
                                                            block90: {
                                                                p3 = (p)this.k.get(n11);
                                                                if (p3.b.X() != 8) break block90;
                                                                n13 = n8;
                                                                break block91;
                                                            }
                                                            n16 = n7 + 1;
                                                            n7 = n3;
                                                            if (n11 > 0) {
                                                                n7 = n3;
                                                                if (n11 >= n6) {
                                                                    n7 = n3 + p3.h.f;
                                                                }
                                                            }
                                                            object = p3.e;
                                                            n10 = ((f)object).g;
                                                            n3 = p3.d != e.b.e ? 1 : 0;
                                                            if (n3 == 0) break block92;
                                                            n13 = this.f;
                                                            if (n13 == 0 && !p3.b.e.e.j) {
                                                                return;
                                                            }
                                                            if (n13 == 1 && !p3.b.f.e.j) {
                                                                return;
                                                            }
                                                            n13 = n3;
                                                            n15 = n10;
                                                            n3 = n8;
                                                            break block93;
                                                        }
                                                        if (p3.a != 1 || n12 != 0) break block94;
                                                        n3 = ((g)object).m;
                                                        ++n8;
                                                        break block95;
                                                    }
                                                    n13 = n3;
                                                    n15 = n10;
                                                    n3 = n8;
                                                    if (!((f)object).j) break block93;
                                                    n3 = n10;
                                                }
                                                n13 = 1;
                                                n15 = n3;
                                                n3 = n8;
                                            }
                                            if (n13 == 0) {
                                                f3 = p3.b.N0[this.f];
                                                n10 = n7;
                                                n8 = ++n3;
                                                f5 = f4;
                                                if (f3 >= 0.0f) {
                                                    f5 = f4 + f3;
                                                    n10 = n7;
                                                    n8 = n3;
                                                }
                                            } else {
                                                n10 = n7 + n15;
                                                f5 = f4;
                                                n8 = n3;
                                            }
                                            n3 = n10;
                                            n13 = n8;
                                            n7 = n16;
                                            f4 = f5;
                                            if (n11 < n5) {
                                                n3 = n10;
                                                n13 = n8;
                                                n7 = n16;
                                                f4 = f5;
                                                if (n11 < n4) {
                                                    n3 = n10 + -p3.i.f;
                                                    f4 = f5;
                                                    n7 = n16;
                                                    n13 = n8;
                                                }
                                            }
                                        }
                                        n8 = n13;
                                    }
                                    if (n3 < n14 || n8 == 0) break;
                                    ++n12;
                                }
                                n15 = n7;
                                n7 = n8;
                                f5 = f4;
                            }
                            n10 = this.h.g;
                            if (bl) {
                                n10 = this.i.g;
                            }
                            f4 = 0.5f;
                            n8 = n10;
                            if (n3 > n14) {
                                n8 = bl ? n10 + (int)((float)(n3 - n14) / 2.0f + 0.5f) : n10 - (int)((float)(n3 - n14) / 2.0f + 0.5f);
                            }
                            if (n7 <= 0) {
                                f5 = 0.5f;
                                n12 = n7;
                                n7 = n3;
                            } else {
                                f3 = n14 - n3;
                                n12 = (int)(f3 / (float)n7 + 0.5f);
                                n10 = 0;
                                n11 = n8;
                                for (n16 = 0; n16 < n9; ++n16) {
                                    int n17;
                                    e e3;
                                    object = (p)this.k.get(n16);
                                    if (((p)object).b.X() == 8 || ((p)object).d != e.b.e) continue;
                                    g g3 = ((p)object).e;
                                    if (g3.j) continue;
                                    n8 = f5 > 0.0f ? (int)(((p)object).b.N0[this.f] * f3 / f5 + f4) : n12;
                                    if (this.f == 0) {
                                        e3 = ((p)object).b;
                                        n17 = e3.A;
                                        n13 = e3.z;
                                    } else {
                                        e3 = ((p)object).b;
                                        n17 = e3.D;
                                        n13 = e3.C;
                                    }
                                    int n18 = ((p)object).a == 1 ? Math.min(n8, g3.m) : n8;
                                    n13 = n18 = Math.max(n13, n18);
                                    if (n17 > 0) {
                                        n13 = Math.min(n17, n18);
                                    }
                                    if (n13 != n8) {
                                        ++n10;
                                        n8 = n13;
                                    }
                                    ((p)object).e.d(n8);
                                }
                                n12 = n3;
                                n3 = n7;
                                if (n10 > 0) {
                                    n13 = n7 - n10;
                                    n7 = 0;
                                    n8 = 0;
                                    while (true) {
                                        n12 = n8;
                                        n3 = n13;
                                        if (n7 >= n9) break;
                                        object = (p)this.k.get(n7);
                                        if (((p)object).b.X() != 8) {
                                            n3 = n8;
                                            if (n7 > 0) {
                                                n3 = n8;
                                                if (n7 >= n6) {
                                                    n3 = n8 + ((p)object).h.f;
                                                }
                                            }
                                            n8 = n3 += ((p)object).e.g;
                                            if (n7 < n5) {
                                                n8 = n3;
                                                if (n7 < n4) {
                                                    n8 = n3 + -((p)object).i.f;
                                                }
                                            }
                                        }
                                        ++n7;
                                    }
                                }
                                if (this.l == 2 && n10 == 0) {
                                    this.l = 0;
                                    n7 = n12;
                                    n12 = n3;
                                    f5 = f4;
                                    n8 = n11;
                                } else {
                                    n7 = n12;
                                    n12 = n3;
                                    f5 = f4;
                                    n8 = n11;
                                }
                            }
                            n11 = 0;
                            n10 = 0;
                            n3 = 0;
                            if (n7 > n14) {
                                this.l = 2;
                            }
                            if (n15 > 0 && n12 == 0 && n6 == n4) {
                                this.l = 2;
                            }
                            if ((n13 = this.l) != 1) break block96;
                            n7 = n15 > 1 ? (n14 - n7) / (n15 - 1) : (n15 == 1 ? (n14 - n7) / 2 : 0);
                            n10 = n7;
                            if (n12 > 0) {
                                n10 = 0;
                            }
                            break block97;
                        }
                        if (n13 != 0) break block98;
                        n10 = (n14 - n7) / (n15 + 1);
                        if (n12 > 0) {
                            n10 = 0;
                        }
                        break block99;
                    }
                    if (n13 != 2) return;
                    f4 = this.f == 0 ? this.b.A() : this.b.T();
                    f3 = f4;
                    if (bl) {
                        f3 = 1.0f - f4;
                    }
                    if ((n7 = (int)((float)(n14 - n7) * f3 + f5)) < 0 || n12 > 0) {
                        n7 = 0;
                    }
                    if (bl) {
                        n8 -= n7;
                        n7 = n10;
                        break block89;
                    } else {
                        n8 += n7;
                        n7 = n10;
                    }
                    break block89;
                }
                n7 = n3;
                n3 = n8;
                while (n7 < n9) {
                    n8 = bl ? n9 - (n7 + 1) : n7;
                    p p4 = (p)this.k.get(n8);
                    if (p4.b.X() == 8) {
                        p4.h.d(n3);
                        p4.i.d(n3);
                        n8 = n3;
                    } else {
                        n8 = n3;
                        if (n7 > 0) {
                            n8 = bl ? n3 - n10 : n3 + n10;
                        }
                        n3 = n8;
                        if (n7 > 0) {
                            n3 = n8;
                            if (n7 >= n6) {
                                n3 = bl ? n8 - p4.h.f : n8 + p4.h.f;
                            }
                        }
                        if (bl) {
                            p4.i.d(n3);
                        } else {
                            p4.h.d(n3);
                        }
                        object = p4.e;
                        n8 = n12 = ((f)object).g;
                        if (p4.d == e.b.e) {
                            n8 = n12;
                            if (p4.a == 1) {
                                n8 = ((g)object).m;
                            }
                        }
                        n3 = bl ? (n3 -= n8) : (n3 += n8);
                        if (bl) {
                            p4.h.d(n3);
                        } else {
                            p4.i.d(n3);
                        }
                        p4.g = true;
                        n8 = n3;
                        if (n7 < n5) {
                            n8 = n3;
                            if (n7 < n4) {
                                n8 = bl ? n3 - -p4.i.f : n3 + -p4.i.f;
                            }
                        }
                    }
                    ++n7;
                    n3 = n8;
                }
                return;
            }
            n7 = n11;
            while (n7 < n9) {
                n3 = bl ? n9 - (n7 + 1) : n7;
                object = (p)this.k.get(n3);
                if (((p)object).b.X() == 8) {
                    ((p)object).h.d(n8);
                    ((p)object).i.d(n8);
                } else {
                    n3 = bl ? n8 - n10 : n8 + n10;
                    n8 = n3;
                    if (n7 > 0) {
                        n8 = n3;
                        if (n7 >= n6) {
                            n8 = bl ? n3 - ((p)object).h.f : n3 + ((p)object).h.f;
                        }
                    }
                    if (bl) {
                        ((p)object).i.d(n8);
                    } else {
                        ((p)object).h.d(n8);
                    }
                    g g4 = ((p)object).e;
                    n3 = n12 = g4.g;
                    if (((p)object).d == e.b.e) {
                        n3 = n12;
                        if (((p)object).a == 1) {
                            n3 = Math.min(n12, g4.m);
                        }
                    }
                    n3 = bl ? n8 - n3 : n8 + n3;
                    if (bl) {
                        ((p)object).h.d(n3);
                    } else {
                        ((p)object).i.d(n3);
                    }
                    n8 = n3;
                    if (n7 < n5) {
                        n8 = n3;
                        if (n7 < n4) {
                            n8 = bl ? n3 - -((p)object).i.f : n3 + -((p)object).i.f;
                        }
                    }
                }
                ++n7;
            }
            return;
        }
        while (n7 < n9) {
            n3 = bl ? n9 - (n7 + 1) : n7;
            p p5 = (p)this.k.get(n3);
            if (p5.b.X() == 8) {
                p5.h.d(n8);
                p5.i.d(n8);
            } else {
                n3 = n8;
                if (n7 > 0) {
                    n3 = n8;
                    if (n7 >= n6) {
                        n3 = bl ? n8 - p5.h.f : n8 + p5.h.f;
                    }
                }
                if (bl) {
                    p5.i.d(n3);
                } else {
                    p5.h.d(n3);
                }
                object = p5.e;
                n8 = ((f)object).g;
                if (p5.d == e.b.e && p5.a == 1) {
                    n8 = ((g)object).m;
                }
                n3 = bl ? (n3 -= n8) : (n3 += n8);
                if (bl) {
                    p5.h.d(n3);
                } else {
                    p5.i.d(n3);
                }
                n8 = n3;
                if (n7 < n5) {
                    n8 = n3;
                    if (n7 < n4) {
                        n8 = bl ? n3 - -p5.i.f : n3 + -p5.i.f;
                    }
                }
            }
            ++n7;
        }
    }

    @Override
    public void d() {
        Object object;
        int n3;
        Object object2 = this.k;
        int n4 = ((ArrayList)object2).size();
        for (n3 = 0; n3 < n4; ++n3) {
            object = ((ArrayList)object2).get(n3);
            ((p)object).d();
        }
        n3 = this.k.size();
        if (n3 < 1) {
            return;
        }
        object = ((p)this.k.get((int)0)).b;
        object2 = ((p)this.k.get((int)(n3 - 1))).b;
        if (this.f == 0) {
            Object object3 = ((e)object).Q;
            object2 = ((e)object2).S;
            object = this.i((u.d)object3, 0);
            n3 = ((u.d)object3).f();
            object3 = this.r();
            if (object3 != null) {
                n3 = ((e)object3).Q.f();
            }
            if (object != null) {
                this.b(this.h, (f)object, n3);
            }
            object = this.i((u.d)object2, 0);
            n3 = ((u.d)object2).f();
            object2 = this.s();
            if (object2 != null) {
                n3 = ((e)object2).S.f();
            }
            if (object != null) {
                this.b(this.i, (f)object, -n3);
            }
        } else {
            object = ((e)object).R;
            object2 = ((e)object2).T;
            f f3 = this.i((u.d)object, 1);
            n3 = ((u.d)object).f();
            object = this.r();
            if (object != null) {
                n3 = ((e)object).R.f();
            }
            if (f3 != null) {
                this.b(this.h, f3, n3);
            }
            object = this.i((u.d)object2, 1);
            n3 = ((u.d)object2).f();
            object2 = this.s();
            if (object2 != null) {
                n3 = ((e)object2).T.f();
            }
            if (object != null) {
                this.b(this.i, (f)object, -n3);
            }
        }
        this.h.a = this;
        this.i.a = this;
    }

    @Override
    public void e() {
        for (int i3 = 0; i3 < this.k.size(); ++i3) {
            ((p)this.k.get(i3)).e();
        }
    }

    @Override
    public void f() {
        this.c = null;
        ArrayList arrayList = this.k;
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object e3 = arrayList.get(i3);
            ((p)e3).f();
        }
    }

    @Override
    public long j() {
        int n3 = this.k.size();
        long l3 = 0L;
        for (int i3 = 0; i3 < n3; ++i3) {
            p p3 = (p)this.k.get(i3);
            l3 = l3 + (long)p3.h.f + p3.j() + (long)p3.i.f;
        }
        return l3;
    }

    @Override
    public boolean m() {
        int n3 = this.k.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (((p)this.k.get(i3)).m()) continue;
            return false;
        }
        return true;
    }

    public final void q() {
        Object object = this.b;
        Object object2 = ((e)object).N(this.f);
        while (object2 != null) {
            e e3 = ((e)object2).N(this.f);
            object = object2;
            object2 = e3;
        }
        this.b = object;
        this.k.add(((e)object).P(this.f));
        for (object2 = ((e)object).L(this.f); object2 != null; object2 = ((e)object2).L(this.f)) {
            this.k.add(((e)object2).P(this.f));
        }
        object2 = this.k;
        int n3 = ((ArrayList)object2).size();
        int n4 = 0;
        while (n4 < n3) {
            object = ((ArrayList)object2).get(n4);
            int n5 = n4 + 1;
            object = (p)object;
            int n6 = this.f;
            if (n6 == 0) {
                ((p)object).b.c = this;
                n4 = n5;
                continue;
            }
            n4 = n5;
            if (n6 != 1) continue;
            ((p)object).b.d = this;
            n4 = n5;
        }
        if (this.f == 0 && ((u.f)this.b.M()).V1() && this.k.size() > 1) {
            object2 = this.k;
            this.b = ((p)((ArrayList)object2).get((int)(((ArrayList)object2).size() - 1))).b;
        }
        n4 = this.f == 0 ? this.b.B() : this.b.U();
        this.l = n4;
    }

    public final e r() {
        for (int i3 = 0; i3 < this.k.size(); ++i3) {
            p p3 = (p)this.k.get(i3);
            if (p3.b.X() == 8) continue;
            return p3.b;
        }
        return null;
    }

    public final e s() {
        for (int i3 = this.k.size() - 1; i3 >= 0; --i3) {
            p p3 = (p)this.k.get(i3);
            if (p3.b.X() == 8) continue;
            return p3.b;
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ChainRun ");
        Object object = this.f == 0 ? "horizontal : " : "vertical : ";
        stringBuilder.append((String)object);
        object = this.k;
        int n3 = ((ArrayList)object).size();
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object2 = ((ArrayList)object).get(i3);
            object2 = (p)object2;
            stringBuilder.append("<");
            stringBuilder.append(object2);
            stringBuilder.append("> ");
        }
        return stringBuilder.toString();
    }
}

