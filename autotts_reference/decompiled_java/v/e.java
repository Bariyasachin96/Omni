/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import java.util.HashSet;
import u.d;
import u.e;
import u.h;
import v.b;
import v.c;
import v.f;
import v.g;
import v.j;
import v.k;
import v.l;
import v.m;
import v.n;
import v.p;

public class e {
    public u.f a;
    public boolean b = true;
    public boolean c = true;
    public u.f d;
    public ArrayList e = new ArrayList();
    public ArrayList f = new ArrayList();
    public b.b g = null;
    public b.a h = new b.a();
    public ArrayList i = new ArrayList();

    public e(u.f f3) {
        this.a = f3;
        this.d = f3;
    }

    public final void a(f object, int n3, int n4, f f3, ArrayList arrayList, m object22) {
        p p3 = ((f)object).d;
        if (p3.c == null) {
            object = this.a;
            if (p3 != ((u.e)object).e && p3 != ((u.e)object).f) {
                object = object22;
                if (object22 == null) {
                    object = new m(p3, n4);
                    arrayList.add(object);
                }
                p3.c = object;
                ((m)object).a(p3);
                for (Object object3 : p3.h.k) {
                    if (!(object3 instanceof f)) continue;
                    this.a((f)object3, n3, 0, f3, arrayList, (m)object);
                }
                for (Object object22 : p3.i.k) {
                    if (!(object22 instanceof f)) continue;
                    this.a((f)object22, n3, 1, f3, arrayList, (m)object);
                }
                if (n3 == 1 && p3 instanceof n) {
                    for (Object object22 : ((n)p3).k.k) {
                        if (!(object22 instanceof f)) continue;
                        this.a((f)object22, n3, 2, f3, arrayList, (m)object);
                    }
                }
                for (Object object22 : p3.h.l) {
                    if (object22 == f3) {
                        ((m)object).b = true;
                    }
                    this.a((f)object22, n3, 0, f3, arrayList, (m)object);
                }
                for (Object object22 : p3.i.l) {
                    if (object22 == f3) {
                        ((m)object).b = true;
                    }
                    this.a((f)object22, n3, 1, f3, arrayList, (m)object);
                }
                if (n3 == 1 && p3 instanceof n) {
                    object22 = ((n)p3).k.l.iterator();
                    while (object22.hasNext()) {
                        this.a((f)object22.next(), n3, 2, f3, arrayList, (m)object);
                    }
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final boolean b(u.f f3) {
        ArrayList arrayList = f3.V0;
        int n3 = arrayList.size();
        int n4 = 0;
        int n5 = 0;
        while (true) {
            int n6;
            block39: {
                void var11_52;
                void var10_38;
                d[] dArray;
                void var9_18;
                int n7;
                u.e e3;
                block35: {
                    block36: {
                        float f4;
                        float f5;
                        void var10_33;
                        e.b b3;
                        block42: {
                            void var11_48;
                            e.b b4;
                            block45: {
                                block44: {
                                    block43: {
                                        block37: {
                                            block41: {
                                                block40: {
                                                    block38: {
                                                        block34: {
                                                            block32: {
                                                                block33: {
                                                                    e.b b5;
                                                                    block30: {
                                                                        block31: {
                                                                            if (n5 >= n3) {
                                                                                return n4 != 0;
                                                                            }
                                                                            Object e4 = arrayList.get(n5);
                                                                            n6 = n5 + 1;
                                                                            e3 = (u.e)e4;
                                                                            e.b[] bArray = e3.b0;
                                                                            e.b b6 = bArray[n4];
                                                                            b5 = bArray[1];
                                                                            if (e3.X() == 8) {
                                                                                e3.a = true;
                                                                                n5 = n6;
                                                                                continue;
                                                                            }
                                                                            if (e3.B < 1.0f && b6 == e.b.e) {
                                                                                e3.w = 2;
                                                                            }
                                                                            if (e3.E < 1.0f && b5 == e.b.e) {
                                                                                e3.x = 2;
                                                                            }
                                                                            if (e3.x() > 0.0f) {
                                                                                e.b b7 = e.b.e;
                                                                                if (b6 == b7 && (b5 == e.b.d || b5 == e.b.c)) {
                                                                                    e3.w = 3;
                                                                                } else if (b5 == b7 && (b6 == e.b.d || b6 == e.b.c)) {
                                                                                    e3.x = 3;
                                                                                } else if (b6 == b7 && b5 == b7) {
                                                                                    if (e3.w == 0) {
                                                                                        e3.w = 3;
                                                                                    }
                                                                                    if (e3.x == 0) {
                                                                                        e3.x = 3;
                                                                                    }
                                                                                }
                                                                            }
                                                                            b3 = e.b.e;
                                                                            e.b b8 = b6;
                                                                            if (b6 != b3) break block30;
                                                                            e.b b9 = b6;
                                                                            if (e3.w != 1) break block30;
                                                                            if (e3.Q.f == null) break block31;
                                                                            e.b b10 = b6;
                                                                            if (e3.S.f != null) break block30;
                                                                        }
                                                                        e.b b11 = e.b.d;
                                                                    }
                                                                    e.b b12 = b5;
                                                                    if (b5 != b3) break block32;
                                                                    e.b b13 = b5;
                                                                    if (e3.x != 1) break block32;
                                                                    if (e3.R.f == null) break block33;
                                                                    e.b b14 = b5;
                                                                    if (e3.T.f != null) break block32;
                                                                }
                                                                e.b b15 = e.b.d;
                                                            }
                                                            l l3 = e3.e;
                                                            l3.d = var10_33;
                                                            l3.a = n7 = e3.w;
                                                            n n8 = e3.f;
                                                            n8.d = var9_18;
                                                            n8.a = n5 = e3.x;
                                                            dArray = e.b.f;
                                                            if (var10_33 != dArray && var10_33 != e.b.c && var10_33 != e.b.d || var9_18 != dArray && var9_18 != e.b.c && var9_18 != e.b.d) break block34;
                                                            void var11_43 = var10_33;
                                                            n5 = e3.Y();
                                                            void var10_36 = var11_43;
                                                            if (var11_43 != dArray) break block35;
                                                            break block36;
                                                        }
                                                        if (var10_33 != b3 || var9_18 != (b4 = e.b.d) && var9_18 != e.b.c) break block37;
                                                        if (n7 != 3) break block38;
                                                        if (var9_18 == b4) {
                                                            this.l(e3, b4, 0, b4, 0);
                                                        }
                                                        n5 = e3.z();
                                                        n7 = (int)((float)n5 * e3.f0 + 0.5f);
                                                        e.b b16 = e.b.c;
                                                        this.l(e3, b16, n7, b16, n5);
                                                        e3.e.e.d(e3.Y());
                                                        e3.f.e.d(e3.z());
                                                        e3.a = true;
                                                        break block39;
                                                    }
                                                    if (n7 != 1) break block40;
                                                    this.l(e3, b4, 0, (e.b)var9_18, 0);
                                                    e3.e.e.m = e3.Y();
                                                    break block39;
                                                }
                                                if (n7 != 2) break block41;
                                                b4 = f3.b0[n4];
                                                e.b b17 = e.b.c;
                                                if (b4 != b17 && b4 != dArray) break block37;
                                                this.l(e3, b17, (int)(e3.B * (float)f3.Y() + 0.5f), (e.b)var9_18, e3.z());
                                                e3.e.e.d(e3.Y());
                                                e3.f.e.d(e3.z());
                                                e3.a = true;
                                                break block39;
                                            }
                                            d[] dArray2 = e3.Y;
                                            if (dArray2[n4].f != null && dArray2[1].f != null) break block37;
                                            this.l(e3, b4, 0, (e.b)var9_18, 0);
                                            e3.e.e.d(e3.Y());
                                            e3.f.e.d(e3.z());
                                            e3.a = true;
                                            break block39;
                                        }
                                        if ((var11_48 = var9_18) != b3 || var10_33 != (b4 = e.b.d) && var10_33 != e.b.c) break block42;
                                        if (n5 != 3) break block43;
                                        if (var10_33 == b4) {
                                            this.l(e3, b4, 0, b4, 0);
                                        }
                                        n7 = e3.Y();
                                        f4 = f5 = e3.f0;
                                        if (e3.y() == -1) {
                                            f4 = 1.0f / f5;
                                        }
                                        n5 = (int)((float)n7 * f4 + 0.5f);
                                        e.b b18 = e.b.c;
                                        this.l(e3, b18, n7, b18, n5);
                                        e3.e.e.d(e3.Y());
                                        e3.f.e.d(e3.z());
                                        e3.a = true;
                                        break block39;
                                    }
                                    if (n5 != 1) break block44;
                                    this.l(e3, (e.b)var10_33, 0, b4, 0);
                                    e3.f.e.m = e3.z();
                                    break block39;
                                }
                                if (n5 != 2) break block45;
                                b4 = f3.b0[1];
                                e.b b19 = e.b.c;
                                if (b4 != b19 && b4 != dArray) break block42;
                                f4 = e3.E;
                                this.l(e3, (e.b)var10_33, e3.Y(), b19, (int)(f4 * (float)f3.z() + 0.5f));
                                e3.e.e.d(e3.Y());
                                e3.f.e.d(e3.z());
                                e3.a = true;
                                break block39;
                            }
                            dArray = e3.Y;
                            if (dArray[2].f != null && dArray[3].f != null) break block42;
                            this.l(e3, b4, 0, (e.b)var11_48, 0);
                            e3.e.e.d(e3.Y());
                            e3.f.e.d(e3.z());
                            e3.a = true;
                            break block39;
                        }
                        if (var10_33 != b3 || var9_18 != b3) break block39;
                        if (n7 != 1 && n5 != 1) {
                            e.b b20;
                            e.b[] bArray;
                            e.b b21;
                            if (n5 == 2 && n7 == 2 && (b21 = (bArray = f3.b0)[n4]) == (b20 = e.b.c) && bArray[1] == b20) {
                                f5 = e3.B;
                                f4 = e3.E;
                                this.l(e3, b20, (int)(f5 * (float)f3.Y() + 0.5f), b20, (int)(f4 * (float)f3.z() + 0.5f));
                                e3.e.e.d(e3.Y());
                                e3.f.e.d(e3.z());
                                e3.a = true;
                            }
                            break block39;
                        } else {
                            e.b b22 = e.b.d;
                            this.l(e3, b22, 0, b22, 0);
                            e3.e.e.m = e3.Y();
                            e3.f.e.m = e3.z();
                        }
                        break block39;
                    }
                    n5 = f3.Y() - e3.Q.g - e3.S.g;
                    e.b b23 = e.b.c;
                }
                n7 = e3.z();
                void var11_50 = var9_18;
                if (var9_18 == dArray) {
                    n7 = f3.z() - e3.R.g - e3.T.g;
                    e.b b24 = e.b.c;
                }
                this.l(e3, (e.b)var10_38, n5, (e.b)var11_52, n7);
                e3.e.e.d(e3.Y());
                e3.f.e.d(e3.z());
                e3.a = true;
            }
            n5 = n6;
        }
    }

    public void c() {
        this.d(this.e);
        this.i.clear();
        m.h = 0;
        this.i(this.a.e, 0, this.i);
        this.i(this.a.f, 1, this.i);
        this.b = false;
    }

    public void d(ArrayList arrayList) {
        int n3;
        arrayList.clear();
        this.d.e.f();
        this.d.f.f();
        arrayList.add(this.d.e);
        arrayList.add(this.d.f);
        ArrayList arrayList2 = this.d.V0;
        int n4 = arrayList2.size();
        Object object = null;
        int n5 = 0;
        int n6 = 0;
        while (n6 < n4) {
            Object object2 = arrayList2.get(n6);
            n3 = n6 + 1;
            u.e e3 = (u.e)object2;
            if (e3 instanceof h) {
                arrayList.add(new j(e3));
                n6 = n3;
                continue;
            }
            if (e3.k0()) {
                if (e3.c == null) {
                    e3.c = new c(e3, 0);
                }
                object2 = object;
                if (object == null) {
                    object2 = new HashSet();
                }
                ((HashSet)object2).add((c)e3.c);
                object = object2;
            } else {
                arrayList.add(e3.e);
            }
            if (e3.m0()) {
                if (e3.d == null) {
                    e3.d = new c(e3, 1);
                }
                object2 = object;
                if (object == null) {
                    object2 = new HashSet();
                }
                ((HashSet)object2).add((c)e3.d);
            } else {
                arrayList.add(e3.f);
                object2 = object;
            }
            object = object2;
            n6 = n3;
            if (!(e3 instanceof u.j)) continue;
            arrayList.add(new k(e3));
            object = object2;
            n6 = n3;
        }
        if (object != null) {
            arrayList.addAll(object);
        }
        n3 = arrayList.size();
        for (n6 = 0; n6 < n3; ++n6) {
            object = arrayList.get(n6);
            ((p)object).f();
        }
        n3 = arrayList.size();
        for (n6 = n5; n6 < n3; ++n6) {
            object = arrayList.get(n6);
            object = (p)object;
            if (((p)object).b == this.d) continue;
            ((p)object).d();
        }
    }

    public final int e(u.f f3, int n3) {
        int n4 = this.i.size();
        long l3 = 0L;
        for (int i3 = 0; i3 < n4; ++i3) {
            l3 = Math.max(l3, ((m)this.i.get(i3)).b(f3, n3));
        }
        return (int)l3;
    }

    public boolean f(boolean bl) {
        Object object;
        Object object2;
        block20: {
            Object object3;
            int n3;
            int n4;
            int n5;
            boolean bl2 = this.b;
            boolean bl3 = false;
            if (bl2 || this.c) {
                object2 = this.a.V0;
                n5 = ((ArrayList)object2).size();
                for (n4 = 0; n4 < n5; ++n4) {
                    object = ((ArrayList)object2).get(n4);
                    object = (u.e)object;
                    ((u.e)object).p();
                    ((u.e)object).a = false;
                    ((u.e)object).e.r();
                    ((u.e)object).f.q();
                }
                this.a.p();
                object2 = this.a;
                ((u.e)object2).a = false;
                ((u.e)object2).e.r();
                this.a.f.q();
                this.c = false;
            }
            if (this.b(this.d)) {
                return false;
            }
            this.a.r1(0);
            this.a.s1(0);
            object2 = this.a.w(0);
            object = this.a.w(1);
            if (this.b) {
                this.c();
            }
            int n6 = this.a.Z();
            n5 = this.a.a0();
            this.a.e.h.d(n6);
            this.a.f.h.d(n5);
            this.m();
            Object object4 = e.b.d;
            if (object2 == object4 || object == object4) {
                block19: {
                    bl2 = bl;
                    if (bl) {
                        object4 = this.e;
                        n3 = ((ArrayList)object4).size();
                        n4 = 0;
                        do {
                            bl2 = bl;
                            if (n4 >= n3) break block19;
                            object3 = ((ArrayList)object4).get(n4);
                            ++n4;
                        } while (((p)object3).m());
                        bl2 = false;
                    }
                }
                if (bl2 && object2 == e.b.d) {
                    this.a.U0(e.b.c);
                    object4 = this.a;
                    ((u.e)object4).p1(this.e((u.f)object4, 0));
                    object4 = this.a;
                    ((u.e)object4).e.e.d(((u.e)object4).Y());
                }
                if (bl2 && object == e.b.d) {
                    this.a.l1(e.b.c);
                    object4 = this.a;
                    ((u.e)object4).Q0(this.e((u.f)object4, 1));
                    object4 = this.a;
                    ((u.e)object4).f.e.d(((u.e)object4).z());
                }
            }
            Object object5 = this.a;
            object3 = object5.b0[0];
            object4 = e.b.c;
            if (object3 != object4 && object3 != e.b.f) {
                n4 = 0;
            } else {
                n4 = object5.Y() + n6;
                this.a.e.i.d(n4);
                this.a.e.e.d(n4 - n6);
                this.m();
                object3 = this.a;
                object5 = ((u.e)object3).b0[1];
                if (object5 == object4 || object5 == e.b.f) {
                    n4 = ((u.e)object3).z() + n5;
                    this.a.f.i.d(n4);
                    this.a.f.e.d(n4 - n5);
                }
                this.m();
                n4 = 1;
            }
            object4 = this.e;
            n6 = ((ArrayList)object4).size();
            for (n5 = 0; n5 < n6; ++n5) {
                object3 = ((ArrayList)object4).get(n5);
                object3 = (p)object3;
                if (((p)object3).b == this.a && !((p)object3).g) continue;
                ((p)object3).e();
            }
            object4 = this.e;
            n3 = ((ArrayList)object4).size();
            n5 = 0;
            while (n5 < n3) {
                object3 = ((ArrayList)object4).get(n5);
                n6 = n5 + 1;
                object3 = (p)object3;
                if (n4 == 0 && ((p)object3).b == this.a) {
                    n5 = n6;
                    continue;
                }
                if (!((p)object3).h.j) {
                    bl = bl3;
                } else if (!((p)object3).i.j && !(object3 instanceof j)) {
                    bl = bl3;
                } else {
                    n5 = n6;
                    if (((p)object3).e.j) continue;
                    n5 = n6;
                    if (object3 instanceof c) continue;
                    n5 = n6;
                    if (object3 instanceof j) continue;
                    bl = bl3;
                }
                break block20;
            }
            bl = true;
        }
        this.a.U0((e.b)((Object)object2));
        this.a.l1((e.b)((Object)object));
        return bl;
    }

    public boolean g(boolean bl) {
        if (this.b) {
            Object object = this.a.V0;
            int n3 = ((ArrayList)object).size();
            for (int i3 = 0; i3 < n3; ++i3) {
                Object object2 = ((ArrayList)object).get(i3);
                object2 = (u.e)object2;
                ((u.e)object2).p();
                ((u.e)object2).a = false;
                l l3 = ((u.e)object2).e;
                l3.e.j = false;
                l3.g = false;
                l3.r();
                object2 = ((u.e)object2).f;
                ((p)object2).e.j = false;
                ((p)object2).g = false;
                ((n)object2).q();
            }
            this.a.p();
            object = this.a;
            ((u.e)object).a = false;
            object = ((u.e)object).e;
            ((p)object).e.j = false;
            ((p)object).g = false;
            ((l)object).r();
            object = this.a.f;
            ((p)object).e.j = false;
            ((p)object).g = false;
            ((n)object).q();
            this.c();
        }
        if (this.b(this.d)) {
            return false;
        }
        this.a.r1(0);
        this.a.s1(0);
        this.a.e.h.d(0);
        this.a.f.h.d(0);
        return true;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public boolean h(boolean var1_1, int var2_2) {
        block20: {
            block19: {
                block21: {
                    var10_3 /* !! */  = this.a;
                    var9_4 = false;
                    var10_3 /* !! */  = var10_3 /* !! */ .w(0);
                    var11_5 = this.a.w(1);
                    var6_6 = this.a.Z();
                    var5_7 = this.a.a0();
                    if (var1_1 && (var10_3 /* !! */  == (var12_8 = e.b.d) || var11_5 == var12_8)) {
                        block18: {
                            var12_8 = this.e;
                            var7_9 = var12_8.size();
                            var3_10 = 0;
                            while (true) {
                                var8_12 = var1_1;
                                if (var3_10 >= var7_9) break block18;
                                var13_13 /* !! */  = var12_8.get(var3_10);
                                var4_11 = var3_10 + 1;
                                var13_13 /* !! */  = (p)var13_13 /* !! */ ;
                                var3_10 = var4_11;
                                if (var13_13 /* !! */ .f != var2_2) continue;
                                var3_10 = var4_11;
                                if (!var13_13 /* !! */ .m()) break;
                            }
                            var8_12 = false;
                        }
                        if (var2_2 == 0) {
                            if (var8_12 && var10_3 /* !! */  == e.b.d) {
                                this.a.U0(e.b.c);
                                var12_8 = this.a;
                                var12_8.p1(this.e((u.f)var12_8, 0));
                                var12_8 = this.a;
                                var12_8.e.e.d(var12_8.Y());
                            }
                        } else if (var8_12 && var11_5 == e.b.d) {
                            this.a.l1(e.b.c);
                            var12_8 = this.a;
                            var12_8.Q0(this.e((u.f)var12_8, 1));
                            var12_8 = this.a;
                            var12_8.f.e.d(var12_8.z());
                        }
                    }
                    if (var2_2 != 0) break block21;
                    var12_8 = this.a;
                    var13_13 /* !! */  = var12_8.b0[0];
                    if (var13_13 /* !! */  == e.b.c || var13_13 /* !! */  == e.b.f) {
                        var3_10 = var12_8.Y() + var6_6;
                        this.a.e.i.d(var3_10);
                        this.a.e.e.d(var3_10 - var6_6);
lbl45:
                        // 2 sources

                        while (true) {
                            var3_10 = 1;
                            break block19;
                            break;
                        }
                    }
                    ** GOTO lbl-1000
                }
                var12_8 = this.a;
                var13_13 /* !! */  = var12_8.b0[1];
                if (var13_13 /* !! */  != e.b.c && var13_13 /* !! */  != e.b.f) lbl-1000:
                // 2 sources

                {
                    var3_10 = 0;
                } else {
                    var3_10 = var12_8.z() + var5_7;
                    this.a.f.i.d(var3_10);
                    this.a.f.e.d(var3_10 - var5_7);
                    ** continue;
                }
            }
            this.m();
            var12_8 = this.e;
            var5_7 = var12_8.size();
            for (var4_11 = 0; var4_11 < var5_7; ++var4_11) {
                var13_13 /* !! */  = var12_8.get(var4_11);
                var13_13 /* !! */  = (p)var13_13 /* !! */ ;
                if (var13_13 /* !! */ .f != var2_2 || var13_13 /* !! */ .b == this.a && !var13_13 /* !! */ .g) continue;
                var13_13 /* !! */ .e();
            }
            var12_8 = this.e;
            var6_6 = var12_8.size();
            var4_11 = 0;
            while (var4_11 < var6_6) {
                var13_13 /* !! */  = var12_8.get(var4_11);
                var5_7 = var4_11 + 1;
                var13_13 /* !! */  = (p)var13_13 /* !! */ ;
                if (var13_13 /* !! */ .f != var2_2) {
                    var4_11 = var5_7;
                    continue;
                }
                if (var3_10 == 0 && var13_13 /* !! */ .b == this.a) {
                    var4_11 = var5_7;
                    continue;
                }
                if (!var13_13 /* !! */ .h.j) {
                    var1_1 = var9_4;
                } else if (!var13_13 /* !! */ .i.j) {
                    var1_1 = var9_4;
                } else {
                    var4_11 = var5_7;
                    if (var13_13 /* !! */  instanceof c) continue;
                    var4_11 = var5_7;
                    if (var13_13 /* !! */ .e.j) continue;
                    var1_1 = var9_4;
                }
                break block20;
            }
            var1_1 = true;
        }
        this.a.U0((e.b)var10_3 /* !! */ );
        this.a.l1(var11_5);
        return var1_1;
    }

    public final void i(p object, int n3, ArrayList arrayList) {
        for (Object object2 : ((p)object).h.k) {
            if (object2 instanceof f) {
                this.a((f)object2, n3, 0, ((p)object).i, arrayList, null);
                continue;
            }
            if (!(object2 instanceof p)) continue;
            this.a(((p)object2).h, n3, 0, ((p)object).i, arrayList, null);
        }
        for (Object object3 : ((p)object).i.k) {
            if (object3 instanceof f) {
                this.a((f)object3, n3, 1, ((p)object).h, arrayList, null);
                continue;
            }
            if (!(object3 instanceof p)) continue;
            this.a(((p)object3).i, n3, 1, ((p)object).h, arrayList, null);
        }
        if (n3 == 1) {
            for (Object object3 : ((n)object).k.k) {
                if (!(object3 instanceof f)) continue;
                this.a((f)object3, n3, 2, null, arrayList, null);
            }
        }
    }

    public void j() {
        this.b = true;
    }

    public void k() {
        this.c = true;
    }

    public final void l(u.e e3, e.b b3, int n3, e.b b4, int n4) {
        b.a a4 = this.h;
        a4.a = b3;
        a4.b = b4;
        a4.c = n3;
        a4.d = n4;
        this.g.b(e3, a4);
        e3.p1(this.h.e);
        e3.Q0(this.h.f);
        e3.P0(this.h.h);
        e3.F0(this.h.g);
    }

    public void m() {
        ArrayList arrayList = this.a.V0;
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            Object object = arrayList.get(n4);
            int n5 = n4 + 1;
            object = (u.e)object;
            if (((u.e)object).a) {
                n4 = n5;
                continue;
            }
            Object object2 = ((u.e)object).b0;
            e.b b3 = object2[0];
            e.b b4 = object2[1];
            n4 = ((u.e)object).w;
            int n6 = ((u.e)object).x;
            e.b b5 = e.b.d;
            n4 = b3 != b5 && (b3 != e.b.e || n4 != 1) ? 0 : 1;
            n6 = b4 != b5 && (b4 != e.b.e || n6 != 1) ? 0 : 1;
            g g3 = ((u.e)object).e.e;
            boolean bl = g3.j;
            object2 = ((u.e)object).f.e;
            boolean bl2 = ((f)object2).j;
            if (bl && bl2) {
                b3 = e.b.c;
                this.l((u.e)object, b3, g3.g, b3, ((f)object2).g);
                ((u.e)object).a = true;
            } else if (bl && n6 != 0) {
                this.l((u.e)object, e.b.c, g3.g, b5, ((f)object2).g);
                if (b4 == e.b.e) {
                    ((u.e)object).f.e.m = ((u.e)object).z();
                } else {
                    ((u.e)object).f.e.d(((u.e)object).z());
                    ((u.e)object).a = true;
                }
            } else if (bl2 && n4 != 0) {
                this.l((u.e)object, b5, g3.g, e.b.c, ((f)object2).g);
                if (b3 == e.b.e) {
                    ((u.e)object).e.e.m = ((u.e)object).Y();
                } else {
                    ((u.e)object).e.e.d(((u.e)object).Y());
                    ((u.e)object).a = true;
                }
            }
            n4 = n5;
            if (!((u.e)object).a) continue;
            object2 = ((u.e)object).f.l;
            n4 = n5;
            if (object2 == null) continue;
            ((g)object2).d(((u.e)object).r());
            n4 = n5;
        }
    }

    public void n(b.b b3) {
        this.g = b3;
    }
}

