/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import r.d;
import u.d;
import u.e;
import u.f;
import u.h;
import u.i;
import u.k;
import u.m;

public class b {
    public final ArrayList a = new ArrayList();
    public a b = new a();
    public f c;

    public b(f f3) {
        this.c = f3;
    }

    public final boolean a(b object, e e3, int n3) {
        this.b.a = e3.C();
        this.b.b = e3.V();
        this.b.c = e3.Y();
        this.b.d = e3.z();
        a a4 = this.b;
        a4.i = false;
        a4.j = n3;
        e.b b3 = a4.a;
        e.b b4 = e.b.e;
        boolean bl = b3 == b4;
        n3 = a4.b == b4 ? 1 : 0;
        bl = bl && e3.f0 > 0.0f;
        n3 = n3 != 0 && e3.f0 > 0.0f ? 1 : 0;
        if (bl && e3.y[0] == 4) {
            a4.a = e.b.c;
        }
        if (n3 != 0 && e3.y[1] == 4) {
            a4.b = e.b.c;
        }
        object.b(e3, a4);
        e3.p1(this.b.e);
        e3.Q0(this.b.f);
        e3.P0(this.b.h);
        e3.F0(this.b.g);
        object = this.b;
        ((a)object).j = v.b$a.k;
        return ((a)object).i;
    }

    public final void b(f f3) {
        int n3 = f3.V0.size();
        boolean bl = f3.Z1(64);
        b b3 = f3.O1();
        for (int i3 = 0; i3 < n3; ++i3) {
            boolean bl2;
            e e3;
            block13: {
                boolean bl3;
                boolean bl4;
                block14: {
                    Object object;
                    Object object2;
                    e3 = (e)f3.V0.get(i3);
                    if (e3 instanceof h || e3 instanceof u.a || e3.n0() || bl && (object2 = e3.e) != null && (object = e3.f) != null && object2.e.j && object.e.j) continue;
                    e.b b4 = e3.w(0);
                    bl4 = true;
                    object = e3.w(1);
                    object2 = e.b.e;
                    bl3 = b4 == object2 && e3.w != 1 && object == object2 && e3.x != 1;
                    bl2 = bl3;
                    if (bl3) break block13;
                    bl2 = bl3;
                    if (!f3.Z1(1)) break block13;
                    bl2 = bl3;
                    if (e3 instanceof m) break block13;
                    bl2 = bl3;
                    if (b4 == object2) {
                        bl2 = bl3;
                        if (e3.w == 0) {
                            bl2 = bl3;
                            if (object != object2) {
                                bl2 = bl3;
                                if (!e3.k0()) {
                                    bl2 = true;
                                }
                            }
                        }
                    }
                    bl3 = bl2;
                    if (object == object2) {
                        bl3 = bl2;
                        if (e3.x == 0) {
                            bl3 = bl2;
                            if (b4 != object2) {
                                bl3 = bl2;
                                if (!e3.k0()) {
                                    bl3 = true;
                                }
                            }
                        }
                    }
                    if (b4 == object2) break block14;
                    bl2 = bl3;
                    if (object != object2) break block13;
                }
                bl2 = bl3;
                if (e3.f0 > 0.0f) {
                    bl2 = bl4;
                }
            }
            if (bl2) continue;
            this.a(b3, e3, v.b$a.k);
        }
        b3.a();
    }

    public final void c(f f3, String string, int n3, int n4, int n5) {
        f3.getClass();
        int n6 = f3.K();
        int n7 = f3.J();
        f3.f1(0);
        f3.e1(0);
        f3.p1(n4);
        f3.Q0(n5);
        f3.f1(n6);
        f3.e1(n7);
        this.c.d2(n3);
        this.c.x1();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public long d(f f3, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        void var21_21;
        int n12;
        e.b b3;
        b b4 = f3.O1();
        int n13 = f3.V0.size();
        n11 = f3.Y();
        int n14 = f3.z();
        boolean bl = k.b(n3, 128);
        n3 = !bl && !k.b(n3, 64) ? 0 : 1;
        n5 = n3;
        if (n3 != 0) {
            n4 = 0;
            while (true) {
                e.b b5;
                n5 = n3;
                if (n4 >= n13) break;
                e e3 = (e)f3.V0.get(n4);
                b3 = e3.C();
                n5 = b3 == (b5 = e.b.e) ? 1 : 0;
                n10 = e3.V() == b5 ? 1 : 0;
                n5 = n5 != 0 && n10 != 0 && e3.x() > 0.0f ? 1 : 0;
                if (e3.k0() && n5 != 0 || e3.m0() && n5 != 0 || e3 instanceof m || e3.k0() || e3.m0()) {
                    n5 = 0;
                    break;
                }
                ++n4;
            }
        }
        if (n5 != 0) {
            boolean bl2 = d.s;
        }
        if ((n12 = n5 & (n3 = n6 == 0x40000000 && n8 == 0x40000000 || bl ? 1 : 0)) != 0) {
            void var20_35;
            n3 = Math.min(f3.I(), n7);
            n4 = Math.min(f3.H(), n9);
            if (n6 == 0x40000000 && f3.Y() != n3) {
                f3.p1(n3);
                f3.S1();
            }
            if (n8 == 0x40000000 && f3.z() != n4) {
                f3.Q0(n4);
                f3.S1();
            }
            if (n6 == 0x40000000 && n8 == 0x40000000) {
                boolean bl3 = f3.K1(bl);
                n3 = 2;
            } else {
                boolean bl4;
                boolean bl5 = f3.L1(bl);
                if (n6 == 0x40000000) {
                    bl4 = bl5 & f3.M1(bl, 0);
                    n3 = 1;
                } else {
                    n3 = 0;
                }
                if (n8 == 0x40000000) {
                    boolean bl6 = f3.M1(bl, 1) & bl4;
                    ++n3;
                }
            }
            void var21_15 = var20_35;
            n4 = n3;
            if (var20_35 != false) {
                void var21_18;
                if (n6 == 0x40000000) {
                    boolean bl7 = true;
                } else {
                    boolean bl8 = false;
                }
                boolean bl9 = n8 == 0x40000000;
                f3.u1((boolean)var21_18, bl9);
                void var21_19 = var20_35;
                n4 = n3;
            }
        } else {
            boolean bl10 = false;
            n4 = 0;
        }
        if (var21_21 == false || n4 != 2) {
            int n15 = f3.P1();
            if (n13 > 0) {
                this.b(f3);
            }
            this.e(f3);
            n8 = this.a.size();
            if (n13 > 0) {
                this.c(f3, "First pass", 0, n11, n14);
            }
            if (n8 > 0) {
                int n16;
                int n17;
                e.b b6 = f3.C();
                n9 = b6 == (b3 = e.b.d) ? 1 : 0;
                n10 = f3.V() == b3 ? 1 : 0;
                n4 = Math.max(f3.Y(), this.c.K());
                n3 = Math.max(f3.z(), this.c.J());
                n5 = 0;
                for (n7 = 0; n7 < n8; ++n7) {
                    e e4 = (e)this.a.get(n7);
                    if (!(e4 instanceof m)) {
                        n6 = n5;
                    } else {
                        n6 = e4.Y();
                        n17 = e4.z();
                        int n18 = this.a(b4, e4, v.b$a.l);
                        n16 = e4.Y();
                        n13 = e4.z();
                        if (n16 != n6) {
                            e4.p1(n16);
                            n5 = n4;
                            if (n9 != 0) {
                                n5 = n4;
                                if (e4.O() > n4) {
                                    n5 = Math.max(n4, e4.O() + e4.q(d.a.f).f());
                                }
                            }
                            n6 = 1;
                            n4 = n5;
                        } else {
                            n6 = n18 | n5;
                        }
                        n5 = n3;
                        if (n13 != n17) {
                            e4.Q0(n13);
                            n5 = n3;
                            if (n10 != 0) {
                                n5 = n3;
                                if (e4.t() > n3) {
                                    n5 = Math.max(n3, e4.t() + e4.q(d.a.g).f());
                                }
                            }
                            n6 = 1;
                        }
                        n6 |= ((m)e4).K1();
                        n3 = n5;
                    }
                    n5 = n6;
                }
                n13 = 0;
                n7 = n5;
                n6 = n8;
                n5 = n12;
                n12 = n13;
                while (n12 < 2) {
                    for (n13 = 0; n13 < n6; ++n13) {
                        e e5 = (e)this.a.get(n13);
                        if (e5 instanceof i && !(e5 instanceof m) || e5 instanceof h || e5.X() == 8 || n5 != 0 && e5.e.e.j && e5.f.e.j || e5 instanceof m) {
                            n17 = n4;
                            n8 = n7;
                        } else {
                            int n19 = e5.Y();
                            n16 = e5.z();
                            n17 = e5.r();
                            n8 = v.b$a.l;
                            if (n12 == 1) {
                                n8 = v.b$a.m;
                            }
                            n8 = this.a(b4, e5, n8) | n7;
                            int n20 = e5.Y();
                            int n21 = e5.z();
                            n7 = n4;
                            if (n20 != n19) {
                                e5.p1(n20);
                                n7 = n4;
                                if (n9 != 0) {
                                    n7 = n4;
                                    if (e5.O() > n4) {
                                        n7 = Math.max(n4, e5.O() + e5.q(d.a.f).f());
                                    }
                                }
                                n8 = 1;
                            }
                            n4 = n3;
                            if (n21 != n16) {
                                e5.Q0(n21);
                                n4 = n3;
                                if (n10 != 0) {
                                    n4 = n3;
                                    if (e5.t() > n3) {
                                        n4 = Math.max(n3, e5.t() + e5.q(d.a.g).f());
                                    }
                                }
                                n8 = 1;
                            }
                            if (e5.b0() && n17 != e5.r()) {
                                n8 = 1;
                                n17 = n7;
                                n3 = n4;
                            } else {
                                n3 = n4;
                                n17 = n7;
                            }
                        }
                        n4 = n17;
                        n7 = n8;
                    }
                    if (n7 == 0) break;
                    this.c(f3, "intermediate pass", ++n12, n11, n14);
                    n7 = 0;
                }
            }
            f3.c2(n15);
        }
        return 0L;
    }

    public void e(f f3) {
        this.a.clear();
        int n3 = f3.V0.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            e.b b3;
            e e3 = (e)f3.V0.get(i3);
            e.b b4 = e3.C();
            if (b4 != (b3 = e.b.e) && e3.V() != b3) continue;
            this.a.add(e3);
        }
        f3.S1();
    }

    public static class a {
        public static int k = 0;
        public static int l = 1;
        public static int m = 2;
        public e.b a;
        public e.b b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public boolean h;
        public boolean i;
        public int j;
    }

    public static interface b {
        public void a();

        public void b(e var1, a var2);
    }
}

