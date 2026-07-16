/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import r.i;
import u.a;
import u.b;
import u.c;
import u.d;
import u.e;
import u.h;
import u.k;
import u.m;
import u.n;
import v.b;

public class f
extends n {
    public v.b W0 = new v.b(this);
    public v.e X0 = new v.e(this);
    public int Y0;
    public b.b Z0 = null;
    public boolean a1 = false;
    public r.d b1 = new r.d();
    public int c1;
    public int d1;
    public int e1;
    public int f1;
    public int g1 = 0;
    public int h1 = 0;
    public c[] i1 = new c[4];
    public c[] j1 = new c[4];
    public boolean k1 = false;
    public boolean l1 = false;
    public boolean m1 = false;
    public int n1 = 0;
    public int o1 = 0;
    public int p1 = 257;
    public boolean q1 = false;
    public boolean r1 = false;
    public boolean s1 = false;
    public int t1 = 0;
    public WeakReference u1 = null;
    public WeakReference v1 = null;
    public WeakReference w1 = null;
    public WeakReference x1 = null;
    public HashSet y1 = new HashSet();
    public b.a z1 = new b.a();

    public static boolean Y1(int n3, e e3, b.b b3, b.a a4, int n4) {
        if (b3 == null) {
            return false;
        }
        if (e3.X() != 8 && !(e3 instanceof h) && !(e3 instanceof a)) {
            a4.a = e3.C();
            a4.b = e3.V();
            a4.c = e3.Y();
            a4.d = e3.z();
            a4.i = false;
            a4.j = n4;
            e.b b4 = a4.a;
            e.b b5 = e.b.e;
            n4 = b4 == b5 ? 1 : 0;
            n3 = a4.b == b5 ? 1 : 0;
            boolean bl = n4 != 0 && e3.f0 > 0.0f;
            boolean bl2 = n3 != 0 && e3.f0 > 0.0f;
            int n5 = n4;
            if (n4 != 0) {
                n5 = n4;
                if (e3.c0(0)) {
                    n5 = n4;
                    if (e3.w == 0) {
                        n5 = n4;
                        if (!bl) {
                            a4.a = e.b.d;
                            if (n3 != 0 && e3.x == 0) {
                                a4.a = e.b.c;
                            }
                            n5 = 0;
                        }
                    }
                }
            }
            n4 = n3;
            if (n3 != 0) {
                n4 = n3;
                if (e3.c0(1)) {
                    n4 = n3;
                    if (e3.x == 0) {
                        n4 = n3;
                        if (!bl2) {
                            a4.b = e.b.d;
                            if (n5 != 0 && e3.w == 0) {
                                a4.b = e.b.c;
                            }
                            n4 = 0;
                        }
                    }
                }
            }
            if (e3.p0()) {
                a4.a = e.b.c;
                n5 = 0;
            }
            if (e3.q0()) {
                a4.b = e.b.c;
                n4 = 0;
            }
            if (bl) {
                if (e3.y[0] == 4) {
                    a4.a = e.b.c;
                } else if (n4 == 0) {
                    b4 = a4.b;
                    b5 = e.b.c;
                    if (b4 == b5) {
                        n3 = a4.d;
                    } else {
                        a4.a = e.b.d;
                        b3.b(e3, a4);
                        n3 = a4.f;
                    }
                    a4.a = b5;
                    a4.c = (int)(e3.x() * (float)n3);
                }
            }
            if (bl2) {
                if (e3.y[1] == 4) {
                    a4.b = e.b.c;
                } else if (n5 == 0) {
                    b5 = a4.a;
                    b4 = e.b.c;
                    if (b5 == b4) {
                        n3 = a4.c;
                    } else {
                        a4.b = e.b.d;
                        b3.b(e3, a4);
                        n3 = a4.e;
                    }
                    a4.b = b4;
                    a4.d = e3.y() == -1 ? (int)((float)n3 / e3.x()) : (int)(e3.x() * (float)n3);
                }
            }
            b3.b(e3, a4);
            e3.p1(a4.e);
            e3.Q0(a4.f);
            e3.P0(a4.h);
            e3.F0(a4.g);
            a4.j = b.a.k;
            return a4.i;
        }
        a4.e = 0;
        a4.f = 0;
        return false;
    }

    public void A1(e e3, int n3) {
        if (n3 == 0) {
            this.C1(e3);
            return;
        }
        if (n3 == 1) {
            this.H1(e3);
        }
    }

    public boolean B1(r.d d3) {
        Object object;
        Object object2;
        int n3;
        boolean bl = this.Z1(64);
        this.g(d3, bl);
        int n4 = this.V0.size();
        boolean bl2 = false;
        for (n3 = 0; n3 < n4; ++n3) {
            object2 = (e)this.V0.get(n3);
            ((e)object2).X0(0, false);
            ((e)object2).X0(1, false);
            if (!(object2 instanceof a)) continue;
            bl2 = true;
        }
        if (bl2) {
            for (n3 = 0; n3 < n4; ++n3) {
                object2 = (e)this.V0.get(n3);
                if (!(object2 instanceof a)) continue;
                ((a)object2).D1();
            }
        }
        this.y1.clear();
        for (n3 = 0; n3 < n4; ++n3) {
            object2 = (e)this.V0.get(n3);
            if (!((e)object2).f()) continue;
            if (object2 instanceof m) {
                this.y1.add(object2);
                continue;
            }
            ((e)object2).g(d3, bl);
        }
        while (this.y1.size() > 0) {
            n3 = this.y1.size();
            object = this.y1.iterator();
            while (object.hasNext()) {
                object2 = (m)((e)object.next());
                if (!((m)object2).A1(this.y1)) continue;
                ((e)object2).g(d3, bl);
                this.y1.remove(object2);
                break;
            }
            if (n3 != this.y1.size()) continue;
            object2 = this.y1.iterator();
            while (object2.hasNext()) {
                ((e)object2.next()).g(d3, bl);
            }
            this.y1.clear();
        }
        if (r.d.s) {
            object2 = new HashSet();
            for (n3 = 0; n3 < n4; ++n3) {
                object = (e)this.V0.get(n3);
                if (((e)object).f()) continue;
                ((HashSet)object2).add((Object)object);
            }
            n3 = this.C() == e.b.d ? 0 : 1;
            this.e(this, d3, (HashSet)object2, n3, false);
            object = ((HashSet)object2).iterator();
            while (object.hasNext()) {
                object2 = (e)object.next();
                u.k.a(this, d3, (e)object2);
                ((e)object2).g(d3, bl);
            }
        } else {
            for (n3 = 0; n3 < n4; ++n3) {
                object2 = (e)this.V0.get(n3);
                if (object2 instanceof f) {
                    Object object3 = ((e)object2).b0;
                    object = object3[0];
                    e.b b3 = object3[1];
                    object3 = e.b.d;
                    if (object == object3) {
                        ((e)object2).U0(e.b.c);
                    }
                    if (b3 == object3) {
                        ((e)object2).l1(e.b.c);
                    }
                    ((e)object2).g(d3, bl);
                    if (object == object3) {
                        ((e)object2).U0((e.b)((Object)object));
                    }
                    if (b3 != object3) continue;
                    ((e)object2).l1(b3);
                    continue;
                }
                u.k.a(this, d3, object2);
                if (((e)object2).f()) continue;
                ((e)object2).g(d3, bl);
            }
        }
        if (this.g1 > 0) {
            u.b.b(this, d3, null, 0);
        }
        if (this.h1 > 0) {
            u.b.b(this, d3, null, 1);
        }
        return true;
    }

    public final void C1(e e3) {
        int n3 = this.g1;
        c[] cArray = this.j1;
        if (n3 + 1 >= cArray.length) {
            this.j1 = Arrays.copyOf(cArray, cArray.length * 2);
        }
        this.j1[this.g1] = new c(e3, 0, this.V1());
        ++this.g1;
    }

    public void D1(d d3) {
        WeakReference weakReference = this.x1;
        if (weakReference != null && weakReference.get() != null && d3.e() <= ((d)this.x1.get()).e()) {
            return;
        }
        this.x1 = new WeakReference<d>(d3);
    }

    public void E1(d d3) {
        WeakReference weakReference = this.v1;
        if (weakReference != null && weakReference.get() != null && d3.e() <= ((d)this.v1.get()).e()) {
            return;
        }
        this.v1 = new WeakReference<d>(d3);
    }

    public final void F1(d object, i i3) {
        object = this.b1.q(object);
        this.b1.h(i3, (i)object, 0, 5);
    }

    public final void G1(d object, i i3) {
        object = this.b1.q(object);
        this.b1.h((i)object, i3, 0, 5);
    }

    public final void H1(e e3) {
        int n3 = this.h1;
        c[] cArray = this.i1;
        if (n3 + 1 >= cArray.length) {
            this.i1 = Arrays.copyOf(cArray, cArray.length * 2);
        }
        this.i1[this.h1] = new c(e3, 1, this.V1());
        ++this.h1;
    }

    public void I1(d d3) {
        WeakReference weakReference = this.w1;
        if (weakReference != null && weakReference.get() != null && d3.e() <= ((d)this.w1.get()).e()) {
            return;
        }
        this.w1 = new WeakReference<d>(d3);
    }

    public void J1(d d3) {
        WeakReference weakReference = this.u1;
        if (weakReference != null && weakReference.get() != null && d3.e() <= ((d)this.u1.get()).e()) {
            return;
        }
        this.u1 = new WeakReference<d>(d3);
    }

    public boolean K1(boolean bl) {
        return this.X0.f(bl);
    }

    public boolean L1(boolean bl) {
        return this.X0.g(bl);
    }

    public boolean M1(boolean bl, int n3) {
        return this.X0.h(bl, n3);
    }

    public void N1(r.e e3) {
        this.b1.v(e3);
    }

    public b.b O1() {
        return this.Z0;
    }

    public int P1() {
        return this.p1;
    }

    @Override
    public void Q(StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(this.o);
        stringBuilder2.append(":{\n");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("  actualWidth:");
        stringBuilder2.append(this.d0);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("  actualHeight:");
        stringBuilder2.append(this.e0);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        ArrayList arrayList = this.w1();
        int n3 = arrayList.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            stringBuilder2 = arrayList.get(i3);
            ((e)((Object)stringBuilder2)).Q(stringBuilder);
            stringBuilder.append(",\n");
        }
        stringBuilder.append("}");
    }

    public r.d Q1() {
        return this.b1;
    }

    public boolean R1() {
        return false;
    }

    public void S1() {
        this.X0.j();
    }

    public void T1() {
        this.X0.k();
    }

    public boolean U1() {
        return this.s1;
    }

    public boolean V1() {
        return this.a1;
    }

    public boolean W1() {
        return this.r1;
    }

    public long X1(int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11) {
        this.c1 = n10;
        this.d1 = n11;
        return this.W0.d(this, n3, n10, n11, n4, n5, n6, n7, n8, n9);
    }

    public boolean Z1(int n3) {
        return (this.p1 & n3) == n3;
    }

    public final void a2() {
        this.g1 = 0;
        this.h1 = 0;
    }

    public void b2(b.b b3) {
        this.Z0 = b3;
        this.X0.n(b3);
    }

    public void c2(int n3) {
        this.p1 = n3;
        r.d.s = this.Z1(512);
    }

    public void d2(int n3) {
        this.Y0 = n3;
    }

    public void e2(boolean bl) {
        this.a1 = bl;
    }

    public boolean f2(r.d d3, boolean[] object) {
        object[2] = false;
        boolean bl = this.Z1(64);
        this.v1(d3, bl);
        int n3 = this.V0.size();
        boolean bl2 = false;
        for (int i3 = 0; i3 < n3; ++i3) {
            object = (e)this.V0.get(i3);
            ((e)object).v1(d3, bl);
            if (!((e)object).e0()) continue;
            bl2 = true;
        }
        return bl2;
    }

    public void g2() {
        this.W0.e(this);
    }

    @Override
    public void u1(boolean bl, boolean bl2) {
        super.u1(bl, bl2);
        int n3 = this.V0.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((e)this.V0.get(i3)).u1(bl, bl2);
        }
    }

    @Override
    public void v0() {
        this.b1.E();
        this.c1 = 0;
        this.e1 = 0;
        this.d1 = 0;
        this.f1 = 0;
        this.q1 = false;
        super.v0();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void x1() {
        var5_1 = 0;
        this.h0 = 0;
        this.i0 = 0;
        this.r1 = false;
        this.s1 = false;
        var13_2 = this.V0.size();
        var2_3 = Math.max(0, this.Y());
        var3_4 = Math.max(0, this.z());
        var15_5 /* !! */  = this.b0;
        var16_10 = var15_5 /* !! */ [1];
        var17_11 = var15_5 /* !! */ [0];
        if (this.Y0 == 0 && u.k.b(this.p1, 1)) {
            v.h.h(this, this.O1());
            for (var1_12 = 0; var1_12 < var13_2; ++var1_12) {
                var15_5 /* !! */  = (e)this.V0.get(var1_12);
                if (!var15_5 /* !! */ .o0() || var15_5 /* !! */  instanceof h || var15_5 /* !! */  instanceof a || var15_5 /* !! */  instanceof m || var15_5 /* !! */ .n0()) continue;
                var19_14 /* !! */  = var15_5 /* !! */ .w(0);
                var20_15 = var15_5 /* !! */ .w(1);
                var18_13 /* !! */  = e.b.e;
                if (var19_14 /* !! */  == var18_13 /* !! */  && var15_5 /* !! */ .w != 1 && var20_15 == var18_13 /* !! */  && var15_5 /* !! */ .x != 1) continue;
                var18_13 /* !! */  = new b.a();
                u.f.Y1(0, (e)var15_5 /* !! */ , this.Z0, (b.a)var18_13 /* !! */ , b.a.k);
            }
        }
        var4_16 = 2;
        if (var13_2 > 2 && (var17_11 == (var15_5 /* !! */  = e.b.d) || var16_10 == var15_5 /* !! */ ) && u.k.b(this.p1, 1024) && v.i.c(this, this.O1())) {
            var1_12 = var2_3;
            if (var17_11 == var15_5 /* !! */ ) {
                if (var2_3 < this.Y() && var2_3 > 0) {
                    this.p1(var2_3);
                    this.r1 = true;
                    var1_12 = var2_3;
                } else {
                    var1_12 = this.Y();
                }
            }
            var2_3 = var3_4;
            if (var16_10 == var15_5 /* !! */ ) {
                if (var3_4 < this.z() && var3_4 > 0) {
                    this.Q0(var3_4);
                    this.s1 = true;
                    var2_3 = var3_4;
                } else {
                    var2_3 = this.z();
                }
            }
            var10_17 = var1_12;
            var1_12 = 1;
            var9_18 = var2_3;
        } else {
            var1_12 = 0;
            var9_18 = var3_4;
            var10_17 = var2_3;
        }
        var2_3 = !this.Z1(64) && !this.Z1(128) ? 0 : 1;
        var15_5 /* !! */  = this.b1;
        var15_5 /* !! */ .i = false;
        var15_5 /* !! */ .j = false;
        if (this.p1 != 0 && var2_3 != 0) {
            var15_5 /* !! */ .j = true;
        }
        var18_13 /* !! */  = this.V0;
        var15_5 /* !! */  = this.C();
        var11_19 = var15_5 /* !! */  == (var19_14 /* !! */  = e.b.d) || this.V() == var19_14 /* !! */ ;
        this.a2();
        for (var2_3 = 0; var2_3 < var13_2; ++var2_3) {
            var15_5 /* !! */  = (e)this.V0.get(var2_3);
            if (!(var15_5 /* !! */  instanceof n)) continue;
            ((n)var15_5 /* !! */ ).x1();
        }
        var14_20 = this.Z1(64);
        var3_4 = var1_12;
        var6_21 = 0;
        var1_12 = 1;
        var2_3 = var5_1;
        var5_1 = var6_21;
        while (true) {
            block47: {
                block45: {
                    block46: {
                        block44: {
                            if (var1_12 != 0) {
                                var12_24 = var5_1 + 1;
                                try {
                                    this.b1.E();
                                    this.a2();
                                    this.o(this.b1);
                                    break block44;
                                }
                                catch (Exception var15_9) {
                                    var5_1 = var4_16;
                                    var4_16 = var2_3;
                                    var2_3 = var1_12;
                                    break block45;
                                }
                            }
                            this.V0 = var18_13 /* !! */ ;
                            if (var3_4 != 0) {
                                var15_5 /* !! */  = this.b0;
                                var15_5 /* !! */ [var2_3] = var17_11;
                                var15_5 /* !! */ [1] = var16_10;
                            }
                            this.z0(this.b1.w());
                            return;
                        }
                        for (var6_21 = var2_3; var6_21 < var13_2; ++var6_21) {
                            try {
                                var15_5 /* !! */  = (e)this.V0.get(var6_21);
                                var5_1 = var1_12;
                            }
                            catch (Exception var15_7) {
                                ** GOTO lbl118
                            }
                            var15_5 /* !! */ .o(this.b1);
                            continue;
                        }
                        var7_22 = var2_3;
                        var6_21 = var4_16;
                        var5_1 = var1_12;
                        try {
                            var8_23 = this.B1(this.b1);
                            var5_1 = var8_23;
                            var15_5 /* !! */  = this.u1;
                            if (var15_5 /* !! */  == null) break block46;
                            var5_1 = var8_23;
                            if (var15_5 /* !! */ .get() == null) break block46;
                            var5_1 = var8_23;
                            var19_14 /* !! */  = (d)this.u1.get();
                            var5_1 = var8_23;
                            var15_5 /* !! */  = this.b1;
                        }
                        catch (Exception var15_6) {
                            var1_12 = var5_1;
lbl118:
                            // 2 sources

                            var5_1 = var4_16;
                            var4_16 = var2_3;
                            var2_3 = var1_12;
                            break block45;
                        }
                        try {
                            this.G1((d)var19_14 /* !! */ , var15_5 /* !! */ .q(this.R));
                            this.u1 = null;
                        }
                        catch (Exception var15_8) {
                            var2_3 = var8_23;
                            var4_16 = var7_22;
                            var5_1 = var6_21;
                            break block45;
                        }
                    }
                    if ((var15_5 /* !! */  = this.w1) != null && var15_5 /* !! */ .get() != null) {
                        this.F1((d)this.w1.get(), this.b1.q(this.T));
                        this.w1 = null;
                    }
                    if ((var15_5 /* !! */  = this.v1) != null && var15_5 /* !! */ .get() != null) {
                        this.G1((d)this.v1.get(), this.b1.q(this.Q));
                        this.v1 = null;
                    }
                    if ((var15_5 /* !! */  = this.x1) != null && var15_5 /* !! */ .get() != null) {
                        this.F1((d)this.x1.get(), this.b1.q(this.S));
                        this.x1 = null;
                    }
                    var2_3 = var8_23;
                    var1_12 = var7_22;
                    var5_1 = var6_21;
                    if (var8_23 != 0) {
                        this.b1.A();
                        var2_3 = var8_23;
                        var1_12 = var7_22;
                        var5_1 = var6_21;
                    }
                    break block47;
                }
                var15_5 /* !! */ .printStackTrace();
                var20_15 = System.out;
                var19_14 /* !! */  = new StringBuilder();
                var19_14 /* !! */ .append("EXCEPTION : ");
                var19_14 /* !! */ .append(var15_5 /* !! */ );
                var20_15.println(var19_14 /* !! */ .toString());
                var1_12 = var4_16;
            }
            if (var2_3 != 0) {
                var2_3 = (int)this.f2(this.b1, u.k.a);
            } else {
                this.v1(this.b1, var14_20);
                for (var2_3 = var1_12; var2_3 < var13_2; ++var2_3) {
                    ((e)this.V0.get(var2_3)).v1(this.b1, var14_20);
                }
                var2_3 = var1_12;
            }
            if (var11_19 && var12_24 < 8 && u.k.a[var5_1]) {
                var7_22 = var6_21 = (var4_16 = var1_12);
                while (var4_16 < var13_2) {
                    var15_5 /* !! */  = (e)this.V0.get(var4_16);
                    var6_21 = Math.max(var6_21, var15_5 /* !! */ .h0 + var15_5 /* !! */ .Y());
                    var7_22 = Math.max(var7_22, var15_5 /* !! */ .i0 + var15_5 /* !! */ .z());
                    ++var4_16;
                }
                var8_23 = Math.max(this.o0, var6_21);
                var7_22 = Math.max(this.p0, var7_22);
                var15_5 /* !! */  = e.b.d;
                var6_21 = var3_4;
                var4_16 = var2_3;
                if (var17_11 == var15_5 /* !! */ ) {
                    var6_21 = var3_4;
                    var4_16 = var2_3;
                    if (this.Y() < var8_23) {
                        this.p1(var8_23);
                        this.b0[var1_12] = var15_5 /* !! */ ;
                        var4_16 = var6_21 = 1;
                    }
                }
                var3_4 = var6_21;
                var2_3 = var4_16;
                if (var16_10 == var15_5 /* !! */ ) {
                    var3_4 = var6_21;
                    var2_3 = var4_16;
                    if (this.z() < var7_22) {
                        this.Q0(var7_22);
                        this.b0[1] = var15_5 /* !! */ ;
                        var2_3 = var3_4 = 1;
                    }
                }
            }
            var6_21 = Math.max(this.o0, this.Y());
            var4_16 = var3_4;
            var3_4 = var2_3;
            if (var6_21 > this.Y()) {
                this.p1(var6_21);
                this.b0[var1_12] = e.b.c;
                var3_4 = var4_16 = 1;
            }
            var6_21 = Math.max(this.p0, this.z());
            var2_3 = var4_16;
            if (var6_21 > this.z()) {
                this.Q0(var6_21);
                this.b0[1] = e.b.c;
                var3_4 = var2_3 = 1;
            }
            var4_16 = var2_3;
            var6_21 = var3_4;
            if (var2_3 != 0) ** GOTO lbl-1000
            var19_14 /* !! */  = this.b0[var1_12];
            var15_5 /* !! */  = e.b.d;
            if (var19_14 /* !! */  == var15_5 /* !! */  && var10_17 > 0 && this.Y() > var10_17) {
                this.r1 = true;
                this.b0[var1_12] = e.b.c;
                this.p1(var10_17);
                var3_4 = var2_3 = 1;
            }
            var4_16 = var2_3;
            var6_21 = var3_4;
            if (this.b0[1] != var15_5 /* !! */ ) ** GOTO lbl-1000
            var4_16 = var2_3;
            var6_21 = var3_4;
            if (var9_18 <= 0) ** GOTO lbl-1000
            var4_16 = var2_3;
            var6_21 = var3_4;
            if (this.z() > var9_18) {
                this.s1 = true;
                this.b0[1] = e.b.c;
                this.Q0(var9_18);
                var3_4 = 1;
                var6_21 = 1;
            } else lbl-1000:
            // 4 sources

            {
                var3_4 = var4_16;
            }
            if (var12_24 > 8) {
                var6_21 = var1_12;
            }
            var2_3 = var12_24;
            var4_16 = var5_1;
            var5_1 = var2_3;
            var2_3 = var1_12;
            var1_12 = var6_21;
        }
    }
}

