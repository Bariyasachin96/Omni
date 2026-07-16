/*
 * Decompiled with CFR 0.152.
 */
package v;

import java.util.ArrayList;
import u.a;
import u.d;
import u.e;
import u.f;
import u.g;
import u.h;
import u.j;
import v.b;
import v.o;

public abstract class i {
    public static o a(e e3, int n3, ArrayList arrayList, o object) {
        Object object2;
        int n4;
        int n5;
        int n6;
        block15: {
            n6 = n3 == 0 ? e3.S0 : e3.T0;
            n5 = 0;
            if (n6 != -1 && (object == null || n6 != ((o)object).c())) {
                n4 = 0;
                while (true) {
                    object2 = object;
                    if (n4 >= arrayList.size()) break block15;
                    object2 = (o)arrayList.get(n4);
                    if (((o)object2).c() == n6) {
                        if (object != null) {
                            ((o)object).g(n3, (o)object2);
                            arrayList.remove(object);
                        }
                        break block15;
                    }
                    ++n4;
                }
            }
            object2 = object;
            if (n6 != -1) {
                return object;
            }
        }
        object = object2;
        if (object2 == null) {
            object = object2;
            if (e3 instanceof j) {
                n4 = ((j)e3).x1(n3);
                object = object2;
                if (n4 != -1) {
                    n6 = 0;
                    while (true) {
                        object = object2;
                        if (n6 >= arrayList.size() || ((o)(object = (o)arrayList.get(n6))).c() == n4) break;
                        ++n6;
                    }
                }
            }
            object2 = object;
            if (object == null) {
                object2 = new o(n3);
            }
            arrayList.add(object2);
            object = object2;
        }
        if (((o)object).a(e3)) {
            if (e3 instanceof h) {
                h h3 = (h)e3;
                object2 = h3.w1();
                n6 = n5;
                if (h3.x1() == 0) {
                    n6 = 1;
                }
                ((d)object2).c(n6, arrayList, (o)object);
            }
            if (n3 == 0) {
                e3.S0 = ((o)object).c();
                e3.Q.c(n3, arrayList, (o)object);
                e3.S.c(n3, arrayList, (o)object);
            } else {
                e3.T0 = ((o)object).c();
                e3.R.c(n3, arrayList, (o)object);
                e3.U.c(n3, arrayList, (o)object);
                e3.T.c(n3, arrayList, (o)object);
            }
            e3.X.c(n3, arrayList, (o)object);
        }
        return object;
    }

    public static o b(ArrayList arrayList, int n3) {
        int n4 = arrayList.size();
        for (int i3 = 0; i3 < n4; ++i3) {
            o o3 = (o)arrayList.get(i3);
            if (n3 != o3.c()) continue;
            return o3;
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    public static boolean c(f var0, b.b var1_1) {
        var19_2 = var0.w1();
        var3_3 = var19_2.size();
        for (var2_4 = 0; var2_4 < var3_3; ++var2_4) {
            var8_5 = (e)var19_2.get(var2_4);
            if (!i.d(var0.C(), var0.V(), var8_5.C(), var8_5.V())) {
                return false;
            }
            if (!(var8_5 instanceof g)) continue;
            return false;
        }
        var13_6 = null;
        var8_5 = null;
        var10_7 = null;
        var9_8 = null;
        var12_9 = null;
        var11_10 = null;
        for (var2_4 = 0; var2_4 < var3_3; ++var2_4) {
            var20_17 = (e)var19_2.get(var2_4);
            if (!i.d(var0.C(), var0.V(), var20_17.C(), var20_17.V())) {
                f.Y1(0, var20_17, (b.b)var1_1, var0.z1, b.a.k);
            }
            var7_11 = var20_17 instanceof h;
            var16_14 = var13_6;
            var15_13 = var10_7;
            if (var7_11) {
                var17_15 = (h)var20_17;
                var14_12 = var10_7;
                if (var17_15.x1() == 0) {
                    var14_12 = var10_7;
                    if (var10_7 == null) {
                        var14_12 = new ArrayList<h>();
                    }
                    var14_12.add((h)var17_15);
                }
                var16_14 = var13_6;
                var15_13 = var14_12;
                if (var17_15.x1() == 1) {
                    var10_7 = var13_6;
                    if (var13_6 == null) {
                        var10_7 = new ArrayList<E>();
                    }
                    var10_7.add(var17_15);
                    var15_13 = var14_12;
                    var16_14 = var10_7;
                }
            }
            var10_7 = var8_5;
            var14_12 = var9_8;
            if (var20_17 instanceof j) {
                if (var20_17 instanceof a) {
                    var17_15 = (a)var20_17;
                    var13_6 = var8_5;
                    if (var17_15.C1() == 0) {
                        var13_6 = var8_5;
                        if (var8_5 == null) {
                            var13_6 = new ArrayList<ArrayList<E>>();
                        }
                        var13_6.add(var17_15);
                    }
                    var10_7 = var13_6;
                    var14_12 = var9_8;
                    if (var17_15.C1() == 1) {
                        var14_12 = var9_8;
                        if (var9_8 == null) {
                            var14_12 = new ArrayList<E>();
                        }
                        var14_12.add((h)var17_15);
                        var10_7 = var13_6;
                    }
                } else {
                    var13_6 = (j)var20_17;
                    var10_7 = var8_5;
                    if (var8_5 == null) {
                        var10_7 = new ArrayList<ArrayList<ArrayList<ArrayList<E>>>>();
                    }
                    var10_7.add(var13_6);
                    var14_12 = var9_8;
                    if (var9_8 == null) {
                        var14_12 = new ArrayList<h>();
                    }
                    var14_12.add((h)var13_6);
                }
            }
            var17_15 = var12_9;
            if (var20_17.Q.f == null) {
                var17_15 = var12_9;
                if (var20_17.S.f == null) {
                    var17_15 = var12_9;
                    if (!var7_11) {
                        var17_15 = var12_9;
                        if (!(var20_17 instanceof a)) {
                            var8_5 = var12_9;
                            if (var12_9 == null) {
                                var8_5 = new ArrayList<e>();
                            }
                            var8_5.add(var20_17);
                            var17_15 = var8_5;
                        }
                    }
                }
            }
            var18_16 = var11_10;
            if (var20_17.R.f == null) {
                var18_16 = var11_10;
                if (var20_17.T.f == null) {
                    var18_16 = var11_10;
                    if (var20_17.U.f == null) {
                        var18_16 = var11_10;
                        if (!var7_11) {
                            var18_16 = var11_10;
                            if (!(var20_17 instanceof a)) {
                                var8_5 = var11_10;
                                if (var11_10 == null) {
                                    var8_5 = new ArrayList<E>();
                                }
                                var8_5.add(var20_17);
                                var18_16 = var8_5;
                            }
                        }
                    }
                }
            }
            var13_6 = var16_14;
            var8_5 = var10_7;
            var10_7 = var15_13;
            var9_8 = var14_12;
            var12_9 = var17_15;
            var11_10 = var18_16;
        }
        var14_12 = new ArrayList<E>();
        if (var13_6 != null) {
            var4_18 = var13_6.size();
            for (var2_4 = 0; var2_4 < var4_18; ++var2_4) {
                var1_1 = var13_6.get(var2_4);
                i.a((h)var1_1, 0, var14_12, null);
            }
        }
        if (var8_5 != null) {
            var4_18 = var8_5.size();
            for (var2_4 = 0; var2_4 < var4_18; ++var2_4) {
                var1_1 = var8_5.get(var2_4);
                var13_6 = (j)var1_1;
                var1_1 = i.a((e)var13_6, 0, var14_12, null);
                var13_6.w1(var14_12, 0, (o)var1_1);
                var1_1.b(var14_12);
            }
        }
        if ((var1_1 = var0.q(d.a.d)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 0, var14_12, null);
            }
        }
        if ((var1_1 = var0.q(d.a.f)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 0, var14_12, null);
            }
        }
        if ((var1_1 = var0.q(d.a.i)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 0, var14_12, null);
            }
        }
        if (var12_9 != null) {
            var4_18 = var12_9.size();
            for (var2_4 = 0; var2_4 < var4_18; ++var2_4) {
                var1_1 = var12_9.get(var2_4);
                i.a((e)var1_1, 0, var14_12, null);
            }
        }
        if (var10_7 != null) {
            var4_18 = var10_7.size();
            for (var2_4 = 0; var2_4 < var4_18; ++var2_4) {
                var1_1 = var10_7.get(var2_4);
                i.a((h)var1_1, 1, var14_12, null);
            }
        }
        if (var9_8 != null) {
            var4_18 = var9_8.size();
            for (var2_4 = 0; var2_4 < var4_18; ++var2_4) {
                var1_1 = var9_8.get(var2_4);
                var8_5 = (j)var1_1;
                var1_1 = i.a((e)var8_5, 1, var14_12, null);
                var8_5.w1(var14_12, 1, (o)var1_1);
                var1_1.b(var14_12);
            }
        }
        if ((var1_1 = var0.q(d.a.e)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 1, var14_12, null);
            }
        }
        if ((var1_1 = var0.q(d.a.h)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 1, var14_12, null);
            }
        }
        if ((var1_1 = var0.q(d.a.g)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 1, var14_12, null);
            }
        }
        if ((var1_1 = var0.q(d.a.i)).d() != null) {
            var1_1 = var1_1.d().iterator();
            while (var1_1.hasNext()) {
                i.a(((d)var1_1.next()).d, 1, var14_12, null);
            }
        }
        if (var11_10 != null) {
            var4_18 = var11_10.size();
            for (var2_4 = 0; var2_4 < var4_18; ++var2_4) {
                var1_1 = var11_10.get(var2_4);
                i.a((e)var1_1, 1, var14_12, null);
            }
        }
        for (var2_4 = 0; var2_4 < var3_3; ++var2_4) {
            var8_5 = (e)var19_2.get(var2_4);
            if (!var8_5.u0()) continue;
            var1_1 = i.b(var14_12, var8_5.S0);
            var8_5 = i.b(var14_12, var8_5.T0);
            if (var1_1 == null || var8_5 == null) continue;
            var1_1.g(0, (o)var8_5);
            var8_5.i(2);
            var14_12.remove(var1_1);
        }
        if (var14_12.size() <= 1) {
            return false;
        }
        if (var0.C() != e.b.d) ** GOTO lbl-1000
        var6_19 = var14_12.size();
        var1_1 = null;
        var3_3 = 0;
        var2_4 = 0;
        while (var2_4 < var6_19) {
            var8_5 = var14_12.get(var2_4);
            var4_18 = var2_4 + 1;
            if ((var8_5 = (o)var8_5).d() == 1) {
                var2_4 = var4_18;
                continue;
            }
            var8_5.h(false);
            var5_20 = var8_5.f(var0.Q1(), 0);
            var2_4 = var4_18;
            if (var5_20 <= var3_3) continue;
            var1_1 = var8_5;
            var3_3 = var5_20;
            var2_4 = var4_18;
        }
        if (var1_1 != null) {
            var0.U0(e.b.c);
            var0.p1(var3_3);
            var1_1.h(true);
            var8_5 = var1_1;
        } else lbl-1000:
        // 2 sources

        {
            var8_5 = null;
        }
        if (var0.V() != e.b.d) ** GOTO lbl-1000
        var6_19 = var14_12.size();
        var1_1 = null;
        var2_4 = 0;
        var3_3 = 0;
        while (var2_4 < var6_19) {
            var9_8 = var14_12.get(var2_4);
            var4_18 = var2_4 + 1;
            if ((var9_8 = (o)var9_8).d() == 0) {
                var2_4 = var4_18;
                continue;
            }
            var9_8.h(false);
            var5_20 = var9_8.f(var0.Q1(), 1);
            var2_4 = var4_18;
            if (var5_20 <= var3_3) continue;
            var1_1 = var9_8;
            var3_3 = var5_20;
            var2_4 = var4_18;
        }
        if (var1_1 != null) {
            var0.l1(e.b.c);
            var0.Q0(var3_3);
            var1_1.h(true);
        } else lbl-1000:
        // 2 sources

        {
            var1_1 = null;
        }
        return var8_5 != null || var1_1 != null;
        {
        }
    }

    public static boolean d(e.b b3, e.b b4, e.b b5, e.b b6) {
        e.b b7;
        e.b b8 = e.b.c;
        boolean bl = b5 == b8 || b5 == (b7 = e.b.d) || b5 == e.b.f && b3 != b7;
        boolean bl2 = b6 == b8 || b6 == (b3 = e.b.d) || b6 == e.b.f && b4 != b3;
        return bl || bl2;
        {
        }
    }
}

