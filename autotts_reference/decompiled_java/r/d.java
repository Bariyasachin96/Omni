/*
 * Decompiled with CFR 0.152.
 */
package r;

import java.util.Arrays;
import java.util.HashMap;
import r.c;
import r.e;
import r.h;
import r.i;
import r.j;
import u.d;

public class d {
    public static boolean s = false;
    public static boolean t = true;
    public static boolean u = true;
    public static boolean v = true;
    public static boolean w = false;
    public static long x;
    public static long y;
    public int a = 1000;
    public boolean b = false;
    public int c = 0;
    public HashMap d = null;
    public a e;
    public int f = 32;
    public int g = 32;
    public r.b[] h;
    public boolean i = false;
    public boolean j = false;
    public boolean[] k = new boolean[32];
    public int l = 1;
    public int m = 0;
    public int n = 32;
    public final c o;
    public i[] p = new i[1000];
    public int q = 0;
    public a r;

    public d() {
        c c3;
        this.h = new r.b[32];
        this.D();
        this.o = c3 = new c();
        this.e = new h(c3);
        if (w) {
            this.r = new b(c3);
            return;
        }
        this.r = new r.b(c3);
    }

    public static r.b s(d d3, i i3, i i4, float f3) {
        return d3.r().j(i3, i4, f3);
    }

    public static e x() {
        return null;
    }

    public void A() {
        if (this.e.isEmpty()) {
            this.n();
            return;
        }
        if (!this.i && !this.j) {
            this.B(this.e);
            return;
        }
        for (int i3 = 0; i3 < this.m; ++i3) {
            if (this.h[i3].f) continue;
            this.B(this.e);
            return;
        }
        this.n();
    }

    public void B(a a4) {
        this.u(a4);
        this.C(a4, false);
        this.n();
    }

    public final int C(a a4, boolean bl) {
        int n3;
        for (n3 = 0; n3 < this.l; ++n3) {
            this.k[n3] = false;
        }
        boolean bl2 = false;
        n3 = 0;
        while (!bl2) {
            Object object;
            i i3;
            int n4;
            block16: {
                block17: {
                    block15: {
                        n4 = n3 + 1;
                        if (n4 >= this.l * 2) break block15;
                        if (a4.getKey() != null) {
                            this.k[a4.getKey().e] = true;
                        }
                        if ((i3 = a4.c(this, this.k)) == null) break block16;
                        object = this.k;
                        n3 = i3.e;
                        if (!object[n3]) break block17;
                    }
                    return n4;
                }
                object[n3] = true;
            }
            if (i3 != null) {
                float f3 = Float.MAX_VALUE;
                int n5 = -1;
                for (n3 = 0; n3 < this.m; ++n3) {
                    int n6;
                    float f4;
                    object = this.h[n3];
                    if (object.a.l == i.a.c) {
                        f4 = f3;
                        n6 = n5;
                    } else if (object.f) {
                        f4 = f3;
                        n6 = n5;
                    } else {
                        f4 = f3;
                        n6 = n5;
                        if (object.t(i3)) {
                            float f5 = object.e.c(i3);
                            f4 = f3;
                            n6 = n5;
                            if (f5 < 0.0f) {
                                f5 = -object.b / f5;
                                f4 = f3;
                                n6 = n5;
                                if (f5 < f3) {
                                    n6 = n3;
                                    f4 = f5;
                                }
                            }
                        }
                    }
                    f3 = f4;
                    n5 = n6;
                }
                n3 = n4;
                if (n5 <= -1) continue;
                object = this.h[n5];
                object.a.f = -1;
                object.x(i3);
                i3 = object.a;
                i3.f = n5;
                i3.h(this, (r.b)object);
                n3 = n4;
                continue;
            }
            bl2 = true;
            n3 = n4;
        }
        return n3;
    }

    public final void D() {
        int n3;
        boolean bl = w;
        int n4 = 0;
        if (bl) {
            for (n3 = n4; n3 < this.m; ++n3) {
                r.b b3 = this.h[n3];
                if (b3 != null) {
                    this.o.a.a(b3);
                }
                this.h[n3] = null;
            }
        } else {
            for (n3 = 0; n3 < this.m; ++n3) {
                r.b b4 = this.h[n3];
                if (b4 != null) {
                    this.o.b.a(b4);
                }
                this.h[n3] = null;
            }
        }
    }

    public void E() {
        Object object;
        int n3 = 0;
        while (true) {
            object = this.o;
            i[] iArray = ((c)object).d;
            if (n3 >= iArray.length) break;
            object = iArray[n3];
            if (object != null) {
                ((i)object).e();
            }
            ++n3;
        }
        ((c)object).c.c(this.p, this.q);
        this.q = 0;
        Arrays.fill(this.o.d, null);
        object = this.d;
        if (object != null) {
            ((HashMap)object).clear();
        }
        this.c = 0;
        this.e.clear();
        this.l = 1;
        for (n3 = 0; n3 < this.m; ++n3) {
            object = this.h[n3];
            if (object == null) continue;
            ((r.b)object).c = false;
        }
        this.D();
        this.m = 0;
        if (w) {
            this.r = new b(this.o);
            return;
        }
        this.r = new r.b(this.o);
    }

    public final i a(i.a object, String iArray) {
        i i3 = (i)this.o.c.b();
        if (i3 == null) {
            i3 = new i((i.a)((Object)object), (String)iArray);
            i3.g((i.a)((Object)object), (String)iArray);
            object = i3;
        } else {
            i3.e();
            i3.g((i.a)((Object)object), (String)iArray);
            object = i3;
        }
        int n3 = this.q;
        int n4 = this.a;
        if (n3 >= n4) {
            this.a = n4 *= 2;
            this.p = Arrays.copyOf(this.p, n4);
        }
        iArray = this.p;
        n4 = this.q;
        this.q = n4 + 1;
        iArray[n4] = object;
        return object;
    }

    public void b(u.e object, u.e object2, float f3, int n3) {
        d.a a4 = d.a.d;
        i i3 = this.q(((u.e)object).q(a4));
        Object object3 = d.a.e;
        i i4 = this.q(((u.e)object).q((d.a)((Object)object3)));
        Object object4 = d.a.f;
        i i5 = this.q(((u.e)object).q((d.a)((Object)object4)));
        Object object5 = d.a.g;
        i i6 = this.q(((u.e)object).q((d.a)((Object)object5)));
        object = this.q(((u.e)object2).q(a4));
        object3 = this.q(((u.e)object2).q((d.a)((Object)object3)));
        object4 = this.q(((u.e)object2).q((d.a)((Object)object4)));
        object5 = this.q(((u.e)object2).q((d.a)((Object)object5)));
        object2 = this.r();
        double d3 = f3;
        double d4 = Math.sin(d3);
        double d5 = n3;
        ((r.b)object2).q(i4, i6, (i)object3, (i)object5, (float)(d4 * d5));
        this.d((r.b)object2);
        object2 = this.r();
        ((r.b)object2).q(i3, i5, (i)object, (i)object4, (float)(Math.cos(d3) * d5));
        this.d((r.b)object2);
    }

    public void c(i i3, i i4, int n3, float f3, i i5, i i6, int n4, int n5) {
        r.b b3 = this.r();
        b3.h(i3, i4, n3, f3, i5, i6, n4);
        if (n5 != 8) {
            b3.d(this, n5);
        }
        this.d(b3);
    }

    /*
     * Unable to fully structure code
     */
    public void d(r.b var1_1) {
        block13: {
            block14: {
                if (var1_1 == null) break block13;
                var2_2 = this.m;
                var3_3 = 1;
                if (var2_2 + 1 >= this.n || this.l + 1 >= this.g) {
                    this.z();
                }
                var4_4 = var1_1.f;
                var2_2 = 0;
                if (var4_4) break block14;
                var1_1.D(this);
                if (var1_1.isEmpty()) break block13;
                var1_1.r();
                if (!var1_1.f(this)) ** GOTO lbl-1000
                var1_1.a = var5_5 = this.p();
                var2_2 = this.m;
                this.l(var1_1);
                if (this.m == var2_2 + 1) {
                    this.r.a(var1_1);
                    this.C(this.r, true);
                    var2_2 = var3_3;
                    if (var5_5.f == -1) {
                        if (var1_1.a == var5_5 && (var5_5 = var1_1.v(var5_5)) != null) {
                            var1_1.x(var5_5);
                        }
                        if (!var1_1.f) {
                            var1_1.a.h(this, var1_1);
                        }
                        if (r.d.w) {
                            this.o.a.a(var1_1);
                        } else {
                            this.o.b.a(var1_1);
                        }
                        --this.m;
                        var2_2 = var3_3;
                    }
                } else lbl-1000:
                // 2 sources

                {
                    var2_2 = 0;
                }
                if (!var1_1.s()) break block13;
            }
            if (var2_2 == 0) {
                this.l(var1_1);
            }
        }
    }

    public r.b e(i i3, i i4, int n3, int n4) {
        if (t && n4 == 8 && i4.i && i3.f == -1) {
            i3.f(this, i4.h + (float)n3);
            return null;
        }
        r.b b3 = this.r();
        b3.n(i3, i4, n3);
        if (n4 != 8) {
            b3.d(this, n4);
        }
        this.d(b3);
        return b3;
    }

    public void f(i i3, int n3) {
        if (t && i3.f == -1) {
            float f3 = n3;
            i3.f(this, f3);
            for (n3 = 0; n3 < this.c + 1; ++n3) {
                i i4 = this.o.d[n3];
                if (i4 == null || !i4.p || i4.q != i3.e) continue;
                i4.f(this, i4.r + f3);
            }
            return;
        }
        int n4 = i3.f;
        if (n4 != -1) {
            r.b b3 = this.h[n4];
            if (b3.f) {
                b3.b = n3;
                return;
            }
            if (b3.e.f() == 0) {
                b3.f = true;
                b3.b = n3;
                return;
            }
            b3 = this.r();
            b3.m(i3, n3);
            this.d(b3);
            return;
        }
        r.b b4 = this.r();
        b4.i(i3, n3);
        this.d(b4);
    }

    public void g(i i3, i i4, int n3, boolean bl) {
        r.b b3 = this.r();
        i i5 = this.t();
        i5.g = 0;
        b3.o(i3, i4, i5, n3);
        this.d(b3);
    }

    public void h(i i3, i i4, int n3, int n4) {
        r.b b3 = this.r();
        i i5 = this.t();
        i5.g = 0;
        b3.o(i3, i4, i5, n3);
        if (n4 != 8) {
            this.m(b3, (int)(b3.e.c(i5) * -1.0f), n4);
        }
        this.d(b3);
    }

    public void i(i i3, i i4, int n3, boolean bl) {
        r.b b3 = this.r();
        i i5 = this.t();
        i5.g = 0;
        b3.p(i3, i4, i5, n3);
        this.d(b3);
    }

    public void j(i i3, i i4, int n3, int n4) {
        r.b b3 = this.r();
        i i5 = this.t();
        i5.g = 0;
        b3.p(i3, i4, i5, n3);
        if (n4 != 8) {
            this.m(b3, (int)(b3.e.c(i5) * -1.0f), n4);
        }
        this.d(b3);
    }

    public void k(i i3, i i4, i i5, i i6, float f3, int n3) {
        r.b b3 = this.r();
        b3.k(i3, i4, i5, i6, f3);
        if (n3 != 8) {
            b3.d(this, n3);
        }
        this.d(b3);
    }

    public final void l(r.b object) {
        int n3;
        Object object2;
        if (u && ((r.b)object).f) {
            ((r.b)object).a.f(this, ((r.b)object).b);
        } else {
            object2 = this.h;
            n3 = this.m;
            object2[n3] = object;
            object2 = ((r.b)object).a;
            ((i)object2).f = n3;
            this.m = n3 + 1;
            ((i)object2).h(this, (r.b)object);
        }
        if (u && this.b) {
            n3 = 0;
            while (n3 < this.m) {
                if (this.h[n3] == null) {
                    System.out.println("WTF");
                }
                object = this.h[n3];
                int n4 = n3;
                if (object != null) {
                    n4 = n3;
                    if (((r.b)object).f) {
                        int n5;
                        ((r.b)object).a.f(this, ((r.b)object).b);
                        if (w) {
                            this.o.a.a(object);
                        } else {
                            this.o.b.a(object);
                        }
                        this.h[n3] = null;
                        int n6 = n4 = n3 + 1;
                        while (n4 < (n5 = this.m)) {
                            object2 = this.h;
                            n6 = n4 - 1;
                            object2[n6] = object = object2[n4];
                            object = ((r.b)object).a;
                            if (((i)object).f == n4) {
                                ((i)object).f = n6;
                            }
                            n6 = n4++;
                        }
                        if (n6 < n5) {
                            this.h[n6] = null;
                        }
                        this.m = n5 - 1;
                        n4 = n3 - 1;
                    }
                }
                n3 = n4 + 1;
            }
            this.b = false;
        }
    }

    public void m(r.b b3, int n3, int n4) {
        b3.e(this.o(n4, null), n3);
    }

    public final void n() {
        for (int i3 = 0; i3 < this.m; ++i3) {
            r.b b3 = this.h[i3];
            b3.a.h = b3.b;
        }
    }

    public i o(int n3, String object) {
        int n4;
        if (this.l + 1 >= this.g) {
            this.z();
        }
        object = this.a(i.a.f, (String)object);
        this.c = n4 = this.c + 1;
        ++this.l;
        ((i)object).e = n4;
        ((i)object).g = n3;
        this.o.d[n4] = object;
        this.e.b((i)object);
        return object;
    }

    public i p() {
        int n3;
        if (this.l + 1 >= this.g) {
            this.z();
        }
        i i3 = this.a(i.a.e, null);
        this.c = n3 = this.c + 1;
        ++this.l;
        i3.e = n3;
        this.o.d[n3] = i3;
        return i3;
    }

    public i q(Object object) {
        Object object2 = null;
        if (object == null) {
            return null;
        }
        if (this.l + 1 >= this.g) {
            this.z();
        }
        if (object instanceof u.d) {
            int n3;
            u.d d3 = (u.d)object;
            object = object2 = d3.i();
            if (object2 == null) {
                d3.s(this.o);
                object = d3.i();
            }
            if ((n3 = ((i)object).e) != -1 && n3 <= this.c && this.o.d[n3] != null) {
                return object;
            }
            if (n3 != -1) {
                ((i)object).e();
            }
            this.c = n3 = this.c + 1;
            ++this.l;
            ((i)object).e = n3;
            ((i)object).l = i.a.c;
            this.o.d[n3] = object;
            object2 = object;
        }
        return object2;
    }

    public r.b r() {
        r.b b3;
        if (w) {
            b3 = (r.b)this.o.a.b();
            if (b3 == null) {
                b3 = new b(this.o);
                ++y;
            } else {
                b3.y();
            }
        } else {
            b3 = (r.b)this.o.b.b();
            if (b3 == null) {
                b3 = new r.b(this.o);
                ++x;
            } else {
                b3.y();
            }
        }
        r.i.c();
        return b3;
    }

    public i t() {
        int n3;
        if (this.l + 1 >= this.g) {
            this.z();
        }
        i i3 = this.a(i.a.e, null);
        this.c = n3 = this.c + 1;
        ++this.l;
        i3.e = n3;
        this.o.d[n3] = i3;
        return i3;
    }

    public final int u(a a4) {
        for (int i3 = 0; i3 < this.m; ++i3) {
            a4 = this.h[i3];
            if (((r.b)a4).a.l == i.a.c) continue;
            float f3 = ((r.b)a4).b;
            float f4 = 0.0f;
            if (!(f3 < 0.0f)) continue;
            boolean bl = false;
            i3 = 0;
            while (!bl) {
                i i4;
                int n3 = i3 + 1;
                f3 = Float.MAX_VALUE;
                int n4 = 0;
                i3 = -1;
                int n5 = -1;
                int n6 = 0;
                while (true) {
                    int n7;
                    int n8;
                    float f5;
                    int n9;
                    block24: {
                        int n10;
                        float f6;
                        int n11;
                        block19: {
                            block17: {
                                block18: {
                                    block16: {
                                        n11 = this.m;
                                        n9 = 1;
                                        if (n4 >= n11) break;
                                        a4 = this.h[n4];
                                        if (((r.b)a4).a.l != i.a.c) break block16;
                                        f6 = f4;
                                        f5 = f3;
                                        n8 = i3;
                                        n7 = n5;
                                        n11 = n6;
                                        break block17;
                                    }
                                    if (!((r.b)a4).f) break block18;
                                    f6 = f4;
                                    f5 = f3;
                                    n8 = i3;
                                    n7 = n5;
                                    n11 = n6;
                                    break block17;
                                }
                                f6 = f4;
                                f5 = f3;
                                n8 = i3;
                                n7 = n5;
                                n11 = n6;
                                if (!(((r.b)a4).b < f4)) break block17;
                                if (!v) break block19;
                                int n12 = ((r.b)a4).e.f();
                                n9 = 0;
                                while (true) {
                                    block21: {
                                        block20: {
                                            f6 = f4;
                                            f5 = f3;
                                            n8 = i3;
                                            n7 = n5;
                                            n11 = n6;
                                            if (n9 >= n12) break;
                                            i4 = ((r.b)a4).e.h(n9);
                                            f6 = ((r.b)a4).e.c(i4);
                                            if (!(f6 <= f4)) break block20;
                                            f5 = f3;
                                            n8 = i3;
                                            n10 = n5;
                                            n7 = n6;
                                            break block21;
                                        }
                                        n7 = 0;
                                        n11 = i3;
                                        i3 = n7;
                                        while (true) {
                                            block23: {
                                                block22: {
                                                    f5 = f3;
                                                    n8 = n11;
                                                    n10 = n5;
                                                    n7 = n6;
                                                    if (i3 >= 9) break;
                                                    f5 = i4.j[i3] / f6;
                                                    if (f5 < f3 && i3 == n6) break block22;
                                                    n7 = n6;
                                                    if (i3 <= n6) break block23;
                                                }
                                                n5 = i4.e;
                                                n7 = i3;
                                                n11 = n4;
                                                f3 = f5;
                                            }
                                            ++i3;
                                            n6 = n7;
                                        }
                                    }
                                    ++n9;
                                    f3 = f5;
                                    i3 = n8;
                                    n5 = n10;
                                    n6 = n7;
                                }
                            }
                            f4 = f6;
                            n9 = n11;
                            break block24;
                        }
                        f6 = f4;
                        n11 = n9;
                        while (true) {
                            block26: {
                                block25: {
                                    f5 = f3;
                                    n8 = i3;
                                    n7 = n5;
                                    n9 = n6;
                                    f4 = f6;
                                    if (n11 >= this.l) break;
                                    i4 = this.o.d[n11];
                                    f5 = ((r.b)a4).e.c(i4);
                                    if (!(f5 <= f6)) break block25;
                                    f4 = f3;
                                    n8 = i3;
                                    n10 = n5;
                                    n9 = n6;
                                    break block26;
                                }
                                n8 = 0;
                                n7 = i3;
                                i3 = n8;
                                while (true) {
                                    block28: {
                                        block27: {
                                            f4 = f3;
                                            n8 = n7;
                                            n10 = n5;
                                            n9 = n6;
                                            if (i3 >= 9) break;
                                            f4 = i4.j[i3] / f5;
                                            if (f4 < f3 && i3 == n6) break block27;
                                            n8 = n6;
                                            if (i3 <= n6) break block28;
                                        }
                                        n8 = i3;
                                        n7 = n4;
                                        n5 = n11;
                                        f3 = f4;
                                    }
                                    ++i3;
                                    n6 = n8;
                                }
                            }
                            ++n11;
                            f3 = f4;
                            i3 = n8;
                            n5 = n10;
                            n6 = n9;
                        }
                    }
                    ++n4;
                    f3 = f5;
                    i3 = n8;
                    n5 = n7;
                    n6 = n9;
                }
                if (i3 != -1) {
                    a4 = this.h[i3];
                    ((r.b)a4).a.f = -1;
                    ((r.b)a4).x(this.o.d[n5]);
                    i4 = ((r.b)a4).a;
                    i4.f = i3;
                    i4.h(this, (r.b)a4);
                } else {
                    bl = true;
                }
                if (n3 > this.l / 2) {
                    bl = true;
                }
                i3 = n3;
            }
            return i3;
        }
        return 0;
    }

    public void v(e e3) {
    }

    public c w() {
        return this.o;
    }

    public int y(Object object) {
        if ((object = ((u.d)object).i()) != null) {
            return (int)(((i)object).h + 0.5f);
        }
        return 0;
    }

    public final void z() {
        int n3;
        this.f = n3 = this.f * 2;
        this.h = Arrays.copyOf(this.h, n3);
        c c3 = this.o;
        c3.d = Arrays.copyOf(c3.d, this.f);
        n3 = this.f;
        this.k = new boolean[n3];
        this.g = n3;
        this.n = n3;
    }

    public static interface a {
        public void a(a var1);

        public void b(i var1);

        public i c(d var1, boolean[] var2);

        public void clear();

        public i getKey();

        public boolean isEmpty();
    }

    public static class b
    extends r.b {
        public b(c c3) {
            this.e = new j(this, c3);
        }
    }
}

