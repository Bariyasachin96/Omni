/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import java.util.Iterator;
import u.a;
import u.d;
import u.e;
import u.f;
import u.n;
import v.b;

public abstract class h {
    public static b.a a = new b.a();
    public static int b = 0;
    public static int c = 0;

    public static boolean a(int n3, e e3) {
        e.b b3;
        e.b b4 = e3.C();
        e.b b5 = e3.V();
        Object object = e3.M() != null ? (f)e3.M() : null;
        if (object != null) {
            object.C();
            b3 = e.b.c;
        }
        if (object != null) {
            object.V();
            object = e.b.c;
        }
        n3 = !(b4 == (b3 = e.b.c) || e3.p0() || b4 == e.b.d || b4 == (object = e.b.e) && e3.w == 0 && e3.f0 == 0.0f && e3.c0(0) || b4 == object && e3.w == 1 && e3.f0(0, e3.Y())) ? 0 : 1;
        boolean bl = b5 == b3 || e3.q0() || b5 == e.b.d || b5 == (object = e.b.e) && e3.x == 0 && e3.f0 == 0.0f && e3.c0(1) || b5 == object && e3.x == 1 && e3.f0(1, e3.z());
        if (e3.f0 > 0.0f && (n3 != 0 || bl)) {
            return true;
        }
        return n3 != 0 && bl;
    }

    public static void b(int n3, e e3, b.b b3, boolean bl) {
        Object object;
        boolean bl2;
        Object object2;
        if (e3.i0()) {
            return;
        }
        int n4 = b;
        int n5 = 1;
        int n6 = 1;
        b = n4 + 1;
        if (!(e3 instanceof f) && e3.o0() && h.a(n4 = n3 + 1, e3)) {
            f.Y1(n4, e3, b3, new b.a(), b.a.k);
        }
        Object object3 = e3.q(d.a.d);
        d d3 = e3.q(d.a.f);
        int n7 = ((d)object3).e();
        int n8 = d3.e();
        n4 = n5;
        if (((d)object3).d() != null) {
            n4 = n5;
            if (((d)object3).n()) {
                Object object4 = ((d)object3).d().iterator();
                n5 = n6;
                while (true) {
                    Object object5;
                    n4 = n5;
                    if (!object4.hasNext()) break;
                    object3 = (d)object4.next();
                    object2 = ((d)object3).d;
                    n6 = n3 + 1;
                    bl2 = h.a(n6, (e)object2);
                    if (((e)object2).o0() && bl2) {
                        f.Y1(n6, (e)object2, b3, new b.a(), b.a.k);
                    }
                    n4 = object3 == ((e)object2).Q && (object = ((e)object2).S.f) != null && object.n() || object3 == ((e)object2).S && (object = ((e)object2).Q.f) != null && object.n() ? n5 : 0;
                    object = ((e)object2).C();
                    if (object == (object5 = e.b.e) && !bl2) {
                        if (((e)object2).C() != object5 || ((e)object2).A < 0 || ((e)object2).z < 0 || ((e)object2).X() != 8 && (((e)object2).w != 0 || ((e)object2).x() != 0.0f) || ((e)object2).k0() || ((e)object2).n0() || n4 == 0 || ((e)object2).k0()) continue;
                        h.e(n6, e3, b3, (e)object2, bl);
                        continue;
                    }
                    if (((e)object2).o0()) continue;
                    object5 = ((e)object2).Q;
                    if (object3 == object5 && ((e)object2).S.f == null) {
                        n4 = ((d)object5).f() + n7;
                        ((e)object2).K0(n4, ((e)object2).Y() + n4);
                        h.b(n6, (e)object2, b3, bl);
                        continue;
                    }
                    object = ((e)object2).S;
                    if (object3 == object && ((d)object5).f == null) {
                        n4 = n7 - object.f();
                        ((e)object2).K0(n4 - ((e)object2).Y(), n4);
                        h.b(n6, (e)object2, b3, bl);
                        continue;
                    }
                    if (n4 == 0 || ((e)object2).k0()) continue;
                    h.d(n6, b3, (e)object2, bl);
                }
            }
        }
        if (e3 instanceof u.h) {
            return;
        }
        if (d3.d() != null && d3.n()) {
            for (Object object4 : d3.d()) {
                object3 = ((d)object4).d;
                n6 = n3 + 1;
                bl2 = h.a(n6, (e)object3);
                if (((e)object3).o0() && bl2) {
                    f.Y1(n6, (e)object3, b3, new b.a(), b.a.k);
                }
                n5 = object4 == ((e)object3).Q && (object2 = ((e)object3).S.f) != null && ((d)object2).n() || object4 == ((e)object3).S && (object2 = ((e)object3).Q.f) != null && ((d)object2).n() ? n4 : 0;
                object = ((e)object3).C();
                if (object == (object2 = e.b.e) && !bl2) {
                    if (((e)object3).C() != object2 || ((e)object3).A < 0 || ((e)object3).z < 0 || ((e)object3).X() != 8 && (((e)object3).w != 0 || ((e)object3).x() != 0.0f) || ((e)object3).k0() || ((e)object3).n0() || n5 == 0 || ((e)object3).k0()) continue;
                    h.e(n6, e3, b3, (e)object3, bl);
                    continue;
                }
                if (((e)object3).o0()) continue;
                object2 = ((e)object3).Q;
                if (object4 == object2 && ((e)object3).S.f == null) {
                    n5 = ((d)object2).f() + n8;
                    ((e)object3).K0(n5, ((e)object3).Y() + n5);
                    h.b(n6, (e)object3, b3, bl);
                    continue;
                }
                object = ((e)object3).S;
                if (object4 == object && ((d)object2).f == null) {
                    n5 = n8 - object.f();
                    ((e)object3).K0(n5 - ((e)object3).Y(), n5);
                    h.b(n6, (e)object3, b3, bl);
                    continue;
                }
                if (n5 == 0 || ((e)object3).k0()) continue;
                h.d(n6, b3, (e)object3, bl);
            }
        }
        e3.s0();
    }

    public static void c(int n3, a a4, b.b b3, int n4, boolean bl) {
        if (a4.y1()) {
            if (n4 == 0) {
                h.b(n3 + 1, a4, b3, bl);
                return;
            }
            h.i(n3 + 1, a4, b3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void d(int n3, b.b b3, e e3, boolean bl) {
        float f3 = e3.A();
        int n4 = e3.Q.f.e();
        int n5 = e3.S.f.e();
        int n6 = e3.Q.f();
        int n7 = e3.S.f();
        if (n4 == n5) {
            f3 = 0.5f;
        } else {
            n4 = n6 + n4;
            n5 -= n7;
        }
        n6 = e3.Y();
        n7 = n5 - n4 - n6;
        if (n4 > n5) {
            n7 = n4 - n5 - n6;
        }
        f3 = n7 > 0 ? f3 * (float)n7 + 0.5f : (f3 *= (float)n7);
        n7 = (int)f3;
        int n8 = n7 + n4;
        n7 = n8 + n6;
        if (n4 > n5) {
            n7 = n8 - n6;
        }
        e3.K0(n8, n7);
        h.b(n3 + 1, e3, b3, bl);
    }

    public static void e(int n3, e e3, b.b b3, e e4, boolean bl) {
        float f3 = e4.A();
        int n4 = e4.Q.f.e() + e4.Q.f();
        int n5 = e4.S.f.e() - e4.S.f();
        if (n5 >= n4) {
            int n6;
            int n7 = n6 = e4.Y();
            if (e4.X() != 8) {
                int n8 = e4.w;
                if (n8 == 2) {
                    n7 = e3 instanceof f ? e3.Y() : e3.M().Y();
                    n7 = (int)(e4.A() * 0.5f * (float)n7);
                } else {
                    n7 = n6;
                    if (n8 == 0) {
                        n7 = n5 - n4;
                    }
                }
                n6 = Math.max(e4.z, n7);
                n8 = e4.A;
                n7 = n6;
                if (n8 > 0) {
                    n7 = Math.min(n8, n6);
                }
            }
            n6 = n4 + (int)(f3 * (float)(n5 - n4 - n7) + 0.5f);
            e4.K0(n6, n7 + n6);
            h.b(n3 + 1, e4, b3, bl);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void f(int n3, b.b b3, e e3) {
        float f3 = e3.T();
        int n4 = e3.R.f.e();
        int n5 = e3.T.f.e();
        int n6 = e3.R.f();
        int n7 = e3.T.f();
        if (n4 == n5) {
            f3 = 0.5f;
        } else {
            n4 = n6 + n4;
            n5 -= n7;
        }
        int n8 = e3.z();
        n7 = n5 - n4 - n8;
        if (n4 > n5) {
            n7 = n4 - n5 - n8;
        }
        f3 = n7 > 0 ? f3 * (float)n7 + 0.5f : (f3 *= (float)n7);
        int n9 = (int)f3;
        n7 = n4 + n9;
        n6 = n7 + n8;
        if (n4 > n5) {
            n7 = n4 - n9;
            n6 = n7 - n8;
        }
        e3.N0(n7, n6);
        h.i(n3 + 1, e3, b3);
    }

    public static void g(int n3, e e3, b.b b3, e e4) {
        float f3 = e4.T();
        int n4 = e4.R.f.e() + e4.R.f();
        int n5 = e4.T.f.e() - e4.T.f();
        if (n5 >= n4) {
            int n6;
            int n7 = n6 = e4.z();
            if (e4.X() != 8) {
                int n8 = e4.x;
                if (n8 == 2) {
                    n7 = e3 instanceof f ? e3.z() : e3.M().z();
                    n7 = (int)(f3 * 0.5f * (float)n7);
                } else {
                    n7 = n6;
                    if (n8 == 0) {
                        n7 = n5 - n4;
                    }
                }
                n6 = Math.max(e4.C, n7);
                n8 = e4.D;
                n7 = n6;
                if (n8 > 0) {
                    n7 = Math.min(n8, n6);
                }
            }
            n6 = n4 + (int)(f3 * (float)(n5 - n4 - n7) + 0.5f);
            e4.N0(n6, n7 + n6);
            h.i(n3 + 1, e4, b3);
        }
    }

    public static void h(f e3, b.b b3) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        Object object = e3.C();
        Object object2 = e3.V();
        b = 0;
        c = 0;
        e3.y0();
        ArrayList arrayList = ((n)e3).w1();
        int n8 = arrayList.size();
        for (n7 = 0; n7 < n8; ++n7) {
            ((e)arrayList.get(n7)).y0();
        }
        boolean bl = ((f)e3).V1();
        if (object == e.b.c) {
            e3.K0(0, e3.Y());
        } else {
            e3.L0(0);
        }
        n7 = n6 = 0;
        for (n5 = 0; n5 < n8; ++n5) {
            object = (e)arrayList.get(n5);
            if (object instanceof u.h) {
                object = (u.h)object;
                n4 = n6;
                n3 = n7;
                if (((u.h)object).x1() == 1) {
                    if (((u.h)object).y1() != -1) {
                        ((u.h)object).B1(((u.h)object).y1());
                    } else if (((u.h)object).z1() != -1 && e3.p0()) {
                        ((u.h)object).B1(e3.Y() - ((u.h)object).z1());
                    } else if (e3.p0()) {
                        ((u.h)object).B1((int)(((u.h)object).A1() * (float)e3.Y() + 0.5f));
                    }
                    n4 = 1;
                    n3 = n7;
                }
            } else {
                n4 = n6;
                n3 = n7;
                if (object instanceof a) {
                    n4 = n6;
                    n3 = n7;
                    if (((a)object).C1() == 0) {
                        n3 = 1;
                        n4 = n6;
                    }
                }
            }
            n6 = n4;
            n7 = n3;
        }
        if (n6 != 0) {
            for (n6 = 0; n6 < n8; ++n6) {
                object = (e)arrayList.get(n6);
                if (!(object instanceof u.h) || ((u.h)(object = (u.h)object)).x1() != 1) continue;
                h.b(0, (e)object, b3, bl);
            }
        }
        h.b(0, e3, b3, bl);
        if (n7 != 0) {
            for (n7 = 0; n7 < n8; ++n7) {
                object = (e)arrayList.get(n7);
                if (!(object instanceof a) || ((a)(object = (a)object)).C1() != 0) continue;
                h.c(0, (a)object, b3, 0, bl);
            }
        }
        if (object2 == e.b.c) {
            e3.N0(0, e3.z());
        } else {
            e3.M0(0);
        }
        n7 = n6 = 0;
        for (n5 = 0; n5 < n8; ++n5) {
            object2 = (e)arrayList.get(n5);
            if (object2 instanceof u.h) {
                object2 = (u.h)object2;
                n4 = n6;
                n3 = n7;
                if (((u.h)object2).x1() == 0) {
                    if (((u.h)object2).y1() != -1) {
                        ((u.h)object2).B1(((u.h)object2).y1());
                    } else if (((u.h)object2).z1() != -1 && e3.q0()) {
                        ((u.h)object2).B1(e3.z() - ((u.h)object2).z1());
                    } else if (e3.q0()) {
                        ((u.h)object2).B1((int)(((u.h)object2).A1() * (float)e3.z() + 0.5f));
                    }
                    n4 = 1;
                    n3 = n7;
                }
            } else {
                n4 = n6;
                n3 = n7;
                if (object2 instanceof a) {
                    n4 = n6;
                    n3 = n7;
                    if (((a)object2).C1() == 1) {
                        n3 = 1;
                        n4 = n6;
                    }
                }
            }
            n6 = n4;
            n7 = n3;
        }
        if (n6 != 0) {
            for (n6 = 0; n6 < n8; ++n6) {
                object2 = (e)arrayList.get(n6);
                if (!(object2 instanceof u.h) || ((u.h)(object2 = (u.h)object2)).x1() != 0) continue;
                h.i(1, (e)object2, b3);
            }
        }
        h.i(0, e3, b3);
        if (n7 != 0) {
            for (n7 = 0; n7 < n8; ++n7) {
                e3 = (e)arrayList.get(n7);
                if (!(e3 instanceof a) || ((a)(e3 = (a)e3)).C1() != 1) continue;
                h.c(0, (a)e3, b3, 1, bl);
            }
        }
        for (n7 = 0; n7 < n8; ++n7) {
            e3 = (e)arrayList.get(n7);
            if (!e3.o0() || !h.a(0, e3)) continue;
            f.Y1(0, e3, b3, a, b.a.k);
            if (e3 instanceof u.h) {
                if (((u.h)e3).x1() == 0) {
                    h.i(0, e3, b3);
                    continue;
                }
                h.b(0, e3, b3, bl);
                continue;
            }
            h.b(0, e3, b3, bl);
            h.i(0, e3, b3);
        }
    }

    public static void i(int n3, e e3, b.b b3) {
        Object object;
        boolean bl;
        Object object2;
        if (e3.r0()) {
            return;
        }
        int n4 = c;
        int n5 = 1;
        int n6 = 1;
        c = n4 + 1;
        if (!(e3 instanceof f) && e3.o0() && h.a(n4 = n3 + 1, e3)) {
            f.Y1(n4, e3, b3, new b.a(), b.a.k);
        }
        Iterator iterator = e3.q(d.a.e);
        Object object32 = e3.q(d.a.g);
        int n7 = ((d)((Object)iterator)).e();
        int n8 = ((d)object32).e();
        n4 = n5;
        if (((d)((Object)iterator)).d() != null) {
            n4 = n5;
            if (((d)((Object)iterator)).n()) {
                object2 = ((d)((Object)iterator)).d().iterator();
                n5 = n6;
                while (true) {
                    n4 = n5;
                    if (!object2.hasNext()) break;
                    Object object4 = (d)object2.next();
                    iterator = ((d)object4).d;
                    n6 = n3 + 1;
                    bl = h.a(n6, iterator);
                    if (((e)((Object)iterator)).o0() && bl) {
                        f.Y1(n6, iterator, b3, new b.a(), b.a.k);
                    }
                    n4 = object4 == ((e)((Object)iterator)).R && (object = ((e)((Object)iterator)).T.f) != null && object.n() || object4 == ((e)((Object)iterator)).T && (object = ((e)((Object)iterator)).R.f) != null && object.n() ? n5 : 0;
                    Object object5 = ((e)((Object)iterator)).V();
                    if (object5 == (object = e.b.e) && !bl) {
                        if (((e)((Object)iterator)).V() != object || ((e)((Object)iterator)).D < 0 || ((e)((Object)iterator)).C < 0 || ((e)((Object)iterator)).X() != 8 && (((e)((Object)iterator)).x != 0 || ((e)((Object)iterator)).x() != 0.0f) || ((e)((Object)iterator)).m0() || ((e)((Object)iterator)).n0() || n4 == 0 || ((e)((Object)iterator)).m0()) continue;
                        h.g(n6, e3, b3, iterator);
                        continue;
                    }
                    if (((e)((Object)iterator)).o0()) continue;
                    object5 = ((e)((Object)iterator)).R;
                    if (object4 == object5 && ((e)((Object)iterator)).T.f == null) {
                        n4 = ((d)object5).f() + n7;
                        ((e)((Object)iterator)).N0(n4, ((e)((Object)iterator)).z() + n4);
                        h.i(n6, iterator, b3);
                        continue;
                    }
                    object = ((e)((Object)iterator)).T;
                    if (object4 == object && ((d)object5).f == null) {
                        n4 = n7 - object.f();
                        ((e)((Object)iterator)).N0(n4 - ((e)((Object)iterator)).z(), n4);
                        h.i(n6, iterator, b3);
                        continue;
                    }
                    if (n4 == 0 || ((e)((Object)iterator)).m0()) continue;
                    h.f(n6, b3, iterator);
                }
            }
        }
        if (e3 instanceof u.h) {
            return;
        }
        if (((d)object32).d() != null && ((d)object32).n()) {
            for (Object object32 : ((d)object32).d()) {
                iterator = ((d)object32).d;
                n6 = n3 + 1;
                bl = h.a(n6, iterator);
                if (((e)((Object)iterator)).o0() && bl) {
                    f.Y1(n6, iterator, b3, new b.a(), b.a.k);
                }
                n5 = object32 == ((e)((Object)iterator)).R && (object2 = ((e)((Object)iterator)).T.f) != null && ((d)object2).n() || object32 == ((e)((Object)iterator)).T && (object2 = ((e)((Object)iterator)).R.f) != null && ((d)object2).n() ? n4 : 0;
                object = ((e)((Object)iterator)).V();
                if (object == (object2 = e.b.e) && !bl) {
                    if (((e)((Object)iterator)).V() != object2 || ((e)((Object)iterator)).D < 0 || ((e)((Object)iterator)).C < 0 || ((e)((Object)iterator)).X() != 8 && (((e)((Object)iterator)).x != 0 || ((e)((Object)iterator)).x() != 0.0f) || ((e)((Object)iterator)).m0() || ((e)((Object)iterator)).n0() || n5 == 0 || ((e)((Object)iterator)).m0()) continue;
                    h.g(n6, e3, b3, iterator);
                    continue;
                }
                if (((e)((Object)iterator)).o0()) continue;
                object = ((e)((Object)iterator)).R;
                if (object32 == object && ((e)((Object)iterator)).T.f == null) {
                    n5 = object.f() + n8;
                    ((e)((Object)iterator)).N0(n5, ((e)((Object)iterator)).z() + n5);
                    h.i(n6, iterator, b3);
                    continue;
                }
                object2 = ((e)((Object)iterator)).T;
                if (object32 == object2 && object.f == null) {
                    n5 = n8 - ((d)object2).f();
                    ((e)((Object)iterator)).N0(n5 - ((e)((Object)iterator)).z(), n5);
                    h.i(n6, iterator, b3);
                    continue;
                }
                if (n5 == 0 || ((e)((Object)iterator)).m0()) continue;
                h.f(n6, b3, iterator);
            }
        }
        if (((d)(object32 = e3.q(d.a.h))).d() != null && ((d)object32).n()) {
            n4 = ((d)object32).e();
            for (Object object4 : ((d)object32).d()) {
                object32 = ((d)object4).d;
                n5 = n3 + 1;
                bl = h.a(n5, (e)object32);
                if (((e)object32).o0() && bl) {
                    f.Y1(n5, (e)object32, b3, new b.a(), b.a.k);
                }
                if (((e)object32).V() == e.b.e && !bl || ((e)object32).o0() || object4 != ((e)object32).U) continue;
                ((e)object32).J0(((d)object4).f() + n4);
                h.i(n5, (e)object32, b3);
            }
        }
        e3.t0();
    }
}

