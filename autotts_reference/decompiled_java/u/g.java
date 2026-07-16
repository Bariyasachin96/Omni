/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import u.d;
import u.e;
import u.f;
import u.m;

public class g
extends m {
    public int A1 = 0;
    public int B1 = -1;
    public int C1 = 0;
    public ArrayList D1 = new ArrayList();
    public e[] E1 = null;
    public e[] F1 = null;
    public int[] G1 = null;
    public e[] H1;
    public int I1 = 0;
    public int k1 = -1;
    public int l1 = -1;
    public int m1 = -1;
    public int n1 = -1;
    public int o1 = -1;
    public int p1 = -1;
    public float q1 = 0.5f;
    public float r1 = 0.5f;
    public float s1 = 0.5f;
    public float t1 = 0.5f;
    public float u1 = 0.5f;
    public float v1 = 0.5f;
    public int w1 = 0;
    public int x1 = 0;
    public int y1 = 2;
    public int z1 = 2;

    public static /* synthetic */ int W1(g g3) {
        return g3.m1;
    }

    public static /* synthetic */ float X1(g g3) {
        return g3.s1;
    }

    public static /* synthetic */ int Y1(g g3) {
        return g3.o1;
    }

    public static /* synthetic */ float Z1(g g3) {
        return g3.u1;
    }

    public static /* synthetic */ float a2(g g3) {
        return g3.r1;
    }

    public static /* synthetic */ int b2(g g3) {
        return g3.n1;
    }

    public static /* synthetic */ float c2(g g3) {
        return g3.t1;
    }

    public static /* synthetic */ int d2(g g3) {
        return g3.p1;
    }

    public static /* synthetic */ float e2(g g3) {
        return g3.v1;
    }

    public static /* synthetic */ int f2(g g3) {
        return g3.y1;
    }

    public static /* synthetic */ int k2(g g3) {
        return g3.l1;
    }

    public static /* synthetic */ int l2(g g3) {
        return g3.z1;
    }

    public static /* synthetic */ int m2(g g3) {
        return g3.k1;
    }

    public static /* synthetic */ float n2(g g3) {
        return g3.q1;
    }

    public void A2(float f3) {
        this.q1 = f3;
    }

    public void B2(int n3) {
        this.w1 = n3;
    }

    public void C2(int n3) {
        this.k1 = n3;
    }

    public void D2(float f3) {
        this.u1 = f3;
    }

    public void E2(int n3) {
        this.o1 = n3;
    }

    public void F2(float f3) {
        this.v1 = f3;
    }

    public void G2(int n3) {
        this.p1 = n3;
    }

    @Override
    public void H1(int n3, int n4, int n5, int n6) {
        int n7;
        if (this.W0 > 0 && !this.J1()) {
            this.M1(0, 0);
            this.L1(false);
            return;
        }
        int n8 = this.E1();
        int n9 = this.F1();
        int n10 = this.G1();
        int n11 = this.D1();
        int[] nArray = new int[2];
        int n12 = n4 - n8 - n9;
        int n13 = this.C1;
        if (n13 == 1) {
            n12 = n6 - n10 - n11;
        }
        if (n13 == 0) {
            if (this.k1 == -1) {
                this.k1 = 0;
            }
            if (this.l1 == -1) {
                this.l1 = 0;
            }
        } else {
            if (this.k1 == -1) {
                this.k1 = 0;
            }
            if (this.l1 == -1) {
                this.l1 = 0;
            }
        }
        e[] eArray = this.V0;
        int n14 = 0;
        int n15 = n13 = 0;
        while (true) {
            n7 = n15;
            n15 = this.W0;
            if (n14 >= n15) break;
            n15 = n7;
            if (this.V0[n14].X() == 8) {
                n15 = n7 + 1;
            }
            ++n14;
        }
        if (n7 > 0) {
            eArray = new e[n15 - n7];
            n15 = n14 = n13;
            while (n14 < this.W0) {
                e e3 = this.V0[n14];
                n7 = n15;
                if (e3.X() != 8) {
                    eArray[n15] = e3;
                    n7 = n15 + 1;
                }
                ++n14;
                n15 = n7;
            }
        }
        this.H1 = eArray;
        this.I1 = n15;
        n14 = this.A1;
        if (n14 != 0) {
            if (n14 != 1) {
                if (n14 != 2) {
                    if (n14 == 3) {
                        this.t2(eArray, n15, this.C1, n12, nArray);
                    }
                } else {
                    this.r2(eArray, n15, this.C1, n12, nArray);
                }
            } else {
                this.s2(eArray, n15, this.C1, n12, nArray);
            }
        } else {
            this.u2(eArray, n15, this.C1, n12, nArray);
        }
        n14 = nArray[n13] + n8 + n9;
        n15 = nArray[1] + n10 + n11;
        n3 = n3 == 0x40000000 ? n4 : (n3 == Integer.MIN_VALUE ? Math.min(n14, n4) : (n3 == 0 ? n14 : n13));
        n4 = n5 == 0x40000000 ? n6 : (n5 == Integer.MIN_VALUE ? Math.min(n15, n6) : (n5 == 0 ? n15 : n13));
        this.M1(n3, n4);
        this.p1(n3);
        this.Q0(n4);
        if (this.W0 > 0) {
            n13 = 1;
        }
        this.L1(n13 != 0);
    }

    public void H2(int n3) {
        this.B1 = n3;
    }

    public void I2(int n3) {
        this.C1 = n3;
    }

    public void J2(int n3) {
        this.z1 = n3;
    }

    public void K2(float f3) {
        this.r1 = f3;
    }

    public void L2(int n3) {
        this.x1 = n3;
    }

    public void M2(int n3) {
        this.l1 = n3;
    }

    public void N2(int n3) {
        this.A1 = n3;
    }

    @Override
    public void g(r.d object, boolean bl) {
        super.g((r.d)object, bl);
        bl = this.M() != null && ((f)this.M()).V1();
        int n3 = this.A1;
        if (n3 != 0) {
            if (n3 != 1) {
                if (n3 != 2) {
                    if (n3 == 3) {
                        int n4 = this.D1.size();
                        for (n3 = 0; n3 < n4; ++n3) {
                            object = (a)this.D1.get(n3);
                            boolean bl2 = n3 == n4 - 1;
                            ((a)object).d(bl, n3, bl2);
                        }
                    }
                } else {
                    this.o2(bl);
                }
            } else {
                int n5 = this.D1.size();
                for (n3 = 0; n3 < n5; ++n3) {
                    object = (a)this.D1.get(n3);
                    boolean bl3 = n3 == n5 - 1;
                    ((a)object).d(bl, n3, bl3);
                }
            }
        } else if (this.D1.size() > 0) {
            ((a)this.D1.get(0)).d(bl, 0, true);
        }
        this.L1(false);
    }

    @Override
    public void n(e e3, HashMap hashMap) {
        super.n(e3, hashMap);
        e3 = (g)e3;
        this.k1 = ((g)e3).k1;
        this.l1 = ((g)e3).l1;
        this.m1 = ((g)e3).m1;
        this.n1 = ((g)e3).n1;
        this.o1 = ((g)e3).o1;
        this.p1 = ((g)e3).p1;
        this.q1 = ((g)e3).q1;
        this.r1 = ((g)e3).r1;
        this.s1 = ((g)e3).s1;
        this.t1 = ((g)e3).t1;
        this.u1 = ((g)e3).u1;
        this.v1 = ((g)e3).v1;
        this.w1 = ((g)e3).w1;
        this.x1 = ((g)e3).x1;
        this.y1 = ((g)e3).y1;
        this.z1 = ((g)e3).z1;
        this.A1 = ((g)e3).A1;
        this.B1 = ((g)e3).B1;
        this.C1 = ((g)e3).C1;
    }

    public final void o2(boolean bl) {
        if (this.G1 != null && this.F1 != null && this.E1 != null) {
            Object object;
            Object object2;
            int n3;
            int n4;
            for (n4 = 0; n4 < this.I1; ++n4) {
                this.H1[n4].x0();
            }
            Object object3 = this.G1;
            int n5 = object3[0];
            int n6 = object3[1];
            float f3 = this.q1;
            object3 = null;
            for (n4 = 0; n4 < n5; ++n4) {
                if (bl) {
                    n3 = n5 - n4 - 1;
                    f3 = 1.0f - this.q1;
                } else {
                    n3 = n4;
                }
                object2 = this.F1[n3];
                object = object3;
                if (object2 != null) {
                    if (((e)object2).X() == 8) {
                        object = object3;
                    } else {
                        if (n4 == 0) {
                            ((e)object2).l(((e)object2).Q, this.Q, this.E1());
                            ((e)object2).S0(this.k1);
                            ((e)object2).R0(f3);
                        }
                        if (n4 == n5 - 1) {
                            ((e)object2).l(((e)object2).S, this.S, this.F1());
                        }
                        if (n4 > 0 && object3 != null) {
                            ((e)object2).l(((e)object2).Q, ((e)object3).S, this.w1);
                            ((e)object3).l(((e)object3).S, ((e)object2).Q, 0);
                        }
                        object = object2;
                    }
                }
                object3 = object;
            }
            object = object3;
            for (n4 = 0; n4 < n6; ++n4) {
                object2 = this.E1[n4];
                object3 = object;
                if (object2 != null) {
                    if (((e)object2).X() == 8) {
                        object3 = object;
                    } else {
                        if (n4 == 0) {
                            ((e)object2).l(((e)object2).R, this.R, this.G1());
                            ((e)object2).j1(this.l1);
                            ((e)object2).i1(this.r1);
                        }
                        if (n4 == n6 - 1) {
                            ((e)object2).l(((e)object2).T, this.T, this.D1());
                        }
                        if (n4 > 0 && object != null) {
                            ((e)object2).l(((e)object2).R, ((e)object).T, this.x1);
                            ((e)object).l(((e)object).T, ((e)object2).R, 0);
                        }
                        object3 = object2;
                    }
                }
                object = object3;
            }
            for (n4 = 0; n4 < n5; ++n4) {
                for (n3 = 0; n3 < n6; ++n3) {
                    int n7 = n3 * n5 + n4;
                    if (this.C1 == 1) {
                        n7 = n4 * n6 + n3;
                    }
                    if (n7 >= ((int[])(object3 = (Object)this.H1)).length || (object2 = (Object)object3[n7]) == null || ((e)object2).X() == 8) continue;
                    object3 = this.F1[n4];
                    object = this.E1[n3];
                    if (object2 != object3) {
                        ((e)object2).l(((e)object2).Q, ((e)object3).Q, 0);
                        ((e)object2).l(((e)object2).S, ((e)object3).S, 0);
                    }
                    if (object2 == object) continue;
                    ((e)object2).l(((e)object2).R, ((e)object).R, 0);
                    ((e)object2).l(((e)object2).T, ((e)object).T, 0);
                }
            }
        }
    }

    public final int p2(e e3, int n3) {
        if (e3 == null) {
            return 0;
        }
        if (e3.V() == e.b.e) {
            int n4 = e3.x;
            if (n4 == 0) {
                return 0;
            }
            if (n4 == 2) {
                if ((n3 = (int)(e3.E * (float)n3)) != e3.z()) {
                    e3.d1(true);
                    this.I1(e3, e3.C(), e3.Y(), e.b.c, n3);
                }
                return n3;
            }
            if (n4 == 1) {
                return e3.z();
            }
            if (n4 == 3) {
                return (int)((float)e3.Y() * e3.f0 + 0.5f);
            }
        }
        return e3.z();
    }

    public final int q2(e e3, int n3) {
        if (e3 == null) {
            return 0;
        }
        if (e3.C() == e.b.e) {
            int n4 = e3.w;
            if (n4 == 0) {
                return 0;
            }
            if (n4 == 2) {
                if ((n3 = (int)(e3.B * (float)n3)) != e3.Y()) {
                    e3.d1(true);
                    this.I1(e3, e.b.c, n3, e3.V(), e3.z());
                }
                return n3;
            }
            if (n4 == 1) {
                return e3.Y();
            }
            if (n4 == 3) {
                return (int)((float)e3.z() * e3.f0 + 0.5f);
            }
        }
        return e3.Y();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void r2(e[] var1_1, int var2_2, int var3_3, int var4_4, int[] var5_5) {
        block35: {
            if (var3_3 == 0) {
                var8_7 = var6_6 = this.B1;
                if (var6_6 <= 0) {
                    var6_6 = 0;
                    var7_9 = var9_8 = 0;
                    while (true) {
                        var8_7 = var6_6;
                        if (var9_8 >= var2_2) break;
                        var8_7 = var7_9;
                        if (var9_8 > 0) {
                            var8_7 = var7_9 + this.w1;
                        }
                        if ((var13_10 /* !! */  = var1_1 /* !! */ [var9_8]) == null) {
                            var7_9 = var8_7;
                        } else {
                            var7_9 = var8_7 + this.q2((e)var13_10 /* !! */ , var4_4);
                            if (var7_9 > var4_4) {
                                var8_7 = var6_6;
                                break;
                            }
                            ++var6_6;
                        }
                        ++var9_8;
                    }
                }
                var7_9 = var8_7;
                var6_6 = 0;
            } else {
                var8_7 = var6_6 = this.B1;
                if (var6_6 <= 0) {
                    var6_6 = 0;
                    var7_9 = var9_8 = 0;
                    while (true) {
                        var8_7 = var6_6;
                        if (var9_8 >= var2_2) break;
                        var8_7 = var7_9;
                        if (var9_8 > 0) {
                            var8_7 = var7_9 + this.x1;
                        }
                        if ((var13_10 /* !! */  = var1_1 /* !! */ [var9_8]) == null) {
                            var7_9 = var8_7;
                        } else {
                            var7_9 = var8_7 + this.p2((e)var13_10 /* !! */ , var4_4);
                            if (var7_9 > var4_4) {
                                var8_7 = var6_6;
                                break;
                            }
                            ++var6_6;
                        }
                        ++var9_8;
                    }
                }
                var7_9 = 0;
                var6_6 = var8_7;
            }
            if (this.G1 == null) {
                this.G1 = new int[2];
            }
            if (var6_6 != 0) break block35;
            var11_11 = var6_6;
            var9_8 = var7_9;
            if (var3_3 == 1) ** GOTO lbl57
        }
        if (var7_9 == 0 && var3_3 == 0) {
            var9_8 = var7_9;
            var11_11 = var6_6;
lbl57:
            // 6 sources

            while (true) {
                var12_13 = true;
                var6_6 = var11_11;
                var7_9 = var9_8;
                break;
            }
        } else {
            var12_13 = false;
        }
        while (!var12_13) {
            block36: {
                if (var3_3 == 0) {
                    var6_6 = (int)Math.ceil((float)var2_2 / (float)var7_9);
                } else {
                    var7_9 = (int)Math.ceil((float)var2_2 / (float)var6_6);
                }
                var13_10 /* !! */  = this.F1;
                if (var13_10 /* !! */  != null && var13_10 /* !! */ .length >= var7_9) {
                    Arrays.fill(var13_10 /* !! */ , null);
                } else {
                    this.F1 = new e[var7_9];
                }
                var13_10 /* !! */  = this.E1;
                if (var13_10 /* !! */  != null && var13_10 /* !! */ .length >= var6_6) {
                    Arrays.fill(var13_10 /* !! */ , null);
                } else {
                    this.E1 = new e[var6_6];
                }
                for (var8_7 = 0; var8_7 < var7_9; ++var8_7) {
                    for (var9_8 = 0; var9_8 < var6_6; ++var9_8) {
                        var10_12 = var9_8 * var7_9 + var8_7;
                        if (var3_3 == 1) {
                            var10_12 = var8_7 * var6_6 + var9_8;
                        }
                        if (var10_12 >= var1_1 /* !! */ .length || (var13_10 /* !! */  = var1_1 /* !! */ [var10_12]) == null) continue;
                        var10_12 = this.q2((e)var13_10 /* !! */ , var4_4);
                        var14_14 = this.F1[var8_7];
                        if (var14_14 == null || var14_14.Y() < var10_12) {
                            this.F1[var8_7] = var13_10 /* !! */ ;
                        }
                        var10_12 = this.p2((e)var13_10 /* !! */ , var4_4);
                        var14_14 = this.E1[var9_8];
                        if (var14_14 != null && var14_14.z() >= var10_12) continue;
                        this.E1[var9_8] = var13_10 /* !! */ ;
                    }
                }
                var8_7 = 0;
                for (var9_8 = 0; var9_8 < var7_9; ++var9_8) {
                    var13_10 /* !! */  = this.F1[var9_8];
                    var10_12 = var8_7;
                    if (var13_10 /* !! */  != null) {
                        var10_12 = var8_7;
                        if (var9_8 > 0) {
                            var10_12 = var8_7 + this.w1;
                        }
                        var10_12 += this.q2((e)var13_10 /* !! */ , var4_4);
                    }
                    var8_7 = var10_12;
                }
                var10_12 = 0;
                for (var9_8 = 0; var9_8 < var6_6; ++var9_8) {
                    var13_10 /* !! */  = this.E1[var9_8];
                    var11_11 = var10_12;
                    if (var13_10 /* !! */  != null) {
                        var11_11 = var10_12;
                        if (var9_8 > 0) {
                            var11_11 = var10_12 + this.x1;
                        }
                        var11_11 += this.p2((e)var13_10 /* !! */ , var4_4);
                    }
                    var10_12 = var11_11;
                }
                var5_5[0] = var8_7;
                var5_5[1] = var10_12;
                if (var3_3 != 0) break block36;
                var11_11 = var6_6;
                var9_8 = var7_9;
                if (var8_7 <= var4_4) ** GOTO lbl57
                var11_11 = var6_6;
                var9_8 = var7_9;
                if (var7_9 <= 1) ** GOTO lbl57
                --var7_9;
                continue;
            }
            var11_11 = var6_6;
            var9_8 = var7_9;
            if (var10_12 <= var4_4) ** GOTO lbl57
            var11_11 = var6_6;
            var9_8 = var7_9;
            if (var6_6 > 1) ** break;
            ** continue;
            --var6_6;
        }
        var1_1 /* !! */  = (e[])this.G1;
        var1_1 /* !! */ [0] = (e)var7_9;
        var1_1 /* !! */ [1] = (e)var6_6;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void s2(e[] var1_1, int var2_2, int var3_3, int var4_4, int[] var5_5) {
        var16_6 = this;
        if (var2_2 == 0) {
            return;
        }
        var16_6.D1.clear();
        var17_7 = var16_6.Q;
        var19_8 = var16_6.R;
        var18_9 = var16_6.S;
        var20_10 = var16_6.T;
        var17_7 = new a((g)var16_6, var3_3, (d)var17_7, (d)var19_8, (d)var18_9, (d)var20_10, var4_4);
        var16_6.D1.add(var17_7);
        if (var3_3 == 0) {
            var6_11 = 0;
            var8_12 = 0;
            var9_13 = 0;
            while (true) {
                var18_9 = var16_6;
                var7_14 = var6_11;
                if (var9_13 < var2_2) {
                    var19_8 = var1_1[var9_13];
                    var11_16 = var16_6.q2((e)var19_8, var4_4);
                    var7_14 = var6_11;
                    if (var19_8.C() == e.b.e) {
                        var7_14 = var6_11 + 1;
                    }
                    var6_11 = (var8_12 == var4_4 || var16_6.w1 + var8_12 + var11_16 > var4_4) && u.g$a.a((a)var17_7) != null ? 1 : 0;
                    var10_15 = var6_11;
                    if (var6_11 == 0) {
                        var10_15 = var6_11;
                        if (var9_13 > 0) {
                            var12_17 = var16_6.B1;
                            var10_15 = var6_11;
                            if (var12_17 > 0) {
                                var10_15 = var6_11;
                                if (var9_13 % var12_17 == 0) {
                                    var10_15 = 1;
                                }
                            }
                        }
                    }
                    if (var10_15 != 0) {
                        var18_9 = new a((g)var16_6, var3_3, var16_6.Q, var16_6.R, var16_6.S, var16_6.T, var4_4);
                        var18_9.i(var9_13);
                        var16_6.D1.add(var18_9);
                        while (true) {
                            var8_12 = var11_16;
                            var17_7 = var18_9;
                            break;
                        }
                    } else {
                        var18_9 = var17_7;
                        if (var9_13 <= 0) ** continue;
                        var8_12 += var16_6.w1 + var11_16;
                    }
                    var17_7.b((e)var19_8);
                    ++var9_13;
                    var6_11 = var7_14;
                    continue;
                }
                break;
            }
        } else {
            var6_11 = 0;
            var8_12 = 0;
            var9_13 = 0;
            while (true) {
                var18_9 = var16_6;
                var7_14 = var6_11;
                if (var9_13 >= var2_2) break;
                var19_8 = var1_1[var9_13];
                var11_16 = var16_6.p2((e)var19_8, var4_4);
                var7_14 = var6_11;
                if (var19_8.V() == e.b.e) {
                    var7_14 = var6_11 + 1;
                }
                var6_11 = (var8_12 == var4_4 || var16_6.x1 + var8_12 + var11_16 > var4_4) && u.g$a.a((a)var17_7) != null ? 1 : 0;
                var10_15 = var6_11;
                if (var6_11 == 0) {
                    var10_15 = var6_11;
                    if (var9_13 > 0) {
                        var12_17 = var16_6.B1;
                        var10_15 = var6_11;
                        if (var12_17 > 0) {
                            var10_15 = var6_11;
                            if (var9_13 % var12_17 == 0) {
                                var10_15 = 1;
                            }
                        }
                    }
                }
                if (var10_15 != 0) {
                    var18_9 = new a((g)var16_6, var3_3, var16_6.Q, var16_6.R, var16_6.S, var16_6.T, var4_4);
                    var18_9.i(var9_13);
                    var16_6.D1.add(var18_9);
                    while (true) {
                        var8_12 = var11_16;
                        var17_7 = var18_9;
                        break;
                    }
                } else {
                    var18_9 = var17_7;
                    if (var9_13 <= 0) ** continue;
                    var8_12 += var16_6.x1 + var11_16;
                }
                var17_7.b((e)var19_8);
                ++var9_13;
                var6_11 = var7_14;
            }
        }
        var15_18 = var18_9.D1.size();
        var16_6 = var18_9.Q;
        var17_7 = var18_9.R;
        var19_8 = var18_9.S;
        var20_10 = var18_9.T;
        var9_13 = var18_9.E1();
        var6_11 = var18_9.G1();
        var10_15 = var18_9.F1();
        var11_16 = var18_9.D1();
        var21_19 /* !! */  = var18_9.C();
        var2_2 = var21_19 /* !! */  != (var1_1 = e.b.d) && var18_9.V() != var1_1 ? 0 : 1;
        if (var7_14 > 0 && var2_2 != 0) {
            for (var2_2 = 0; var2_2 < var15_18; ++var2_2) {
                var1_1 = (a)var18_9.D1.get(var2_2);
                if (var3_3 == 0) {
                    var1_1.g(var4_4 - var1_1.f());
                    continue;
                }
                var1_1.g(var4_4 - var1_1.e());
            }
        }
        var12_17 = 0;
        var7_14 = var6_11;
        var6_11 = 0;
        for (var8_12 = 0; var8_12 < var15_18; ++var8_12) {
            var21_19 /* !! */  = (a)var18_9.D1.get(var8_12);
            if (var3_3 == 0) {
                if (var8_12 < var15_18 - 1) {
                    var1_1 = u.g$a.a((a)((a)var18_9.D1.get((int)(var8_12 + 1)))).R;
                    var2_2 = 0;
                } else {
                    var1_1 = var18_9.T;
                    var2_2 = var18_9.D1();
                }
                var20_10 = u.g$a.a((a)var21_19 /* !! */ ).T;
                var21_19 /* !! */ .j(var3_3, (d)var16_6, (d)var17_7, (d)var19_8, (d)var1_1, var9_13, var7_14, var10_15, var2_2, var4_4);
                var7_14 = Math.max(var12_17, var21_19 /* !! */ .f());
                var6_11 = var11_16 = var21_19 /* !! */ .e() + var6_11;
                if (var8_12 > 0) {
                    var6_11 = var11_16 + var18_9.x1;
                }
                var17_7 = var20_10;
                var13_20 = 0;
                var11_16 = var2_2;
            } else {
                if (var8_12 < var15_18 - 1) {
                    var1_1 = u.g$a.a((a)((a)var18_9.D1.get((int)(var8_12 + 1)))).Q;
                    var2_2 = 0;
lbl136:
                    // 2 sources

                    while (true) {
                        continue;
                        break;
                    }
                }
                var1_1 = var18_9.S;
                var2_2 = var18_9.F1();
                ** continue;
                var19_8 = var1_1;
                var1_1 = u.g$a.a((a)var21_19 /* !! */ ).S;
                var21_19 /* !! */ .j(var3_3, (d)var16_6, (d)var17_7, (d)var19_8, (d)var20_10, var9_13, var7_14, var2_2, var11_16, var4_4);
                var9_13 = var21_19 /* !! */ .f() + var12_17;
                var12_17 = Math.max(var6_11, var21_19 /* !! */ .e());
                var6_11 = var9_13;
                if (var8_12 > 0) {
                    var6_11 = var9_13 + var18_9.w1;
                }
                var14_21 = var6_11;
                var9_13 = 0;
                var16_6 = var1_1;
                var10_15 = var2_2;
                var13_20 = var7_14;
                var1_1 = var20_10;
                var6_11 = var12_17;
                var7_14 = var14_21;
            }
            var12_17 = var7_14;
            var20_10 = var1_1;
            var7_14 = var13_20;
        }
        var5_5[0] = var12_17;
        var5_5[1] = var6_11;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void t2(e[] var1_1, int var2_2, int var3_3, int var4_4, int[] var5_5) {
        var17_6 = this;
        if (var2_2 == 0) {
            return;
        }
        var17_6.D1.clear();
        var18_7 = var17_6.Q;
        var19_8 = var17_6.R;
        var21_9 = var17_6.S;
        var20_10 = var17_6.T;
        var18_7 = new a((g)var17_6, var3_3, (d)var18_7, (d)var19_8, (d)var21_9, (d)var20_10, var4_4);
        var17_6.D1.add(var18_7);
        var13_11 = 1;
        if (var3_3 == 0) {
            var10_12 = 0;
            var6_13 = 0;
            var8_14 = 0;
            var9_15 = 0;
            while (true) {
                var19_8 = var17_6;
                var7_16 = var6_13;
                if (var9_15 < var2_2) {
                    ++var10_12;
                    var20_10 = var1_1 /* !! */ [var9_15];
                    var12_18 = var17_6.q2((e)var20_10, var4_4);
                    var7_16 = var6_13;
                    if (var20_10.C() == e.b.e) {
                        var7_16 = var6_13 + 1;
                    }
                    var6_13 = (var8_14 == var4_4 || var17_6.w1 + var8_14 + var12_18 > var4_4) && u.g$a.a((a)var18_7) != null ? 1 : 0;
                    var11_17 = var6_13;
                    if (var6_13 == 0) {
                        var11_17 = var6_13;
                        if (var9_15 > 0) {
                            var14_19 = var17_6.B1;
                            var11_17 = var6_13;
                            if (var14_19 > 0) {
                                var11_17 = var6_13;
                                if (var10_12 > var14_19) {
                                    var11_17 = 1;
                                }
                            }
                        }
                    }
                    if (var11_17 != 0) {
                        var19_8 = new a((g)var17_6, var3_3, var17_6.Q, var17_6.R, var17_6.S, var17_6.T, var4_4);
                        var19_8.i(var9_15);
                        var17_6.D1.add(var19_8);
                        var6_13 = 1;
                        while (true) {
                            var8_14 = var12_18;
                            var18_7 = var19_8;
                            break;
                        }
                    } else {
                        var19_8 = var18_7;
                        var6_13 = var10_12;
                        if (var9_15 <= 0) ** continue;
                        var8_14 += var17_6.w1 + var12_18;
                        var6_13 = var10_12;
                    }
                    var18_7.b((e)var20_10);
                    ++var9_15;
                    var10_12 = var6_13;
                    var6_13 = var7_16;
                    continue;
                }
                break;
            }
        } else {
            var10_12 = 0;
            var6_13 = 0;
            var8_14 = 0;
            var9_15 = 0;
            while (true) {
                var19_8 = var17_6;
                var7_16 = var6_13;
                if (var9_15 >= var2_2) break;
                ++var10_12;
                var20_10 = var1_1 /* !! */ [var9_15];
                var12_18 = var17_6.p2((e)var20_10, var4_4);
                var7_16 = var6_13;
                if (var20_10.V() == e.b.e) {
                    var7_16 = var6_13 + 1;
                }
                var6_13 = (var8_14 == var4_4 || var17_6.x1 + var8_14 + var12_18 > var4_4) && u.g$a.a((a)var18_7) != null ? 1 : 0;
                var11_17 = var6_13;
                if (var6_13 == 0) {
                    var11_17 = var6_13;
                    if (var9_15 > 0) {
                        var14_19 = var17_6.B1;
                        var11_17 = var6_13;
                        if (var14_19 > 0) {
                            var11_17 = var6_13;
                            if (var10_12 > var14_19) {
                                var11_17 = 1;
                            }
                        }
                    }
                }
                if (var11_17 != 0) {
                    var19_8 = new a((g)var17_6, var3_3, var17_6.Q, var17_6.R, var17_6.S, var17_6.T, var4_4);
                    var19_8.i(var9_15);
                    var17_6.D1.add(var19_8);
                    var6_13 = 1;
                    while (true) {
                        var8_14 = var12_18;
                        var18_7 = var19_8;
                        break;
                    }
                } else {
                    var19_8 = var18_7;
                    var6_13 = var10_12;
                    if (var9_15 <= 0) ** continue;
                    var8_14 += var17_6.x1 + var12_18;
                    var6_13 = var10_12;
                }
                var18_7.b((e)var20_10);
                ++var9_15;
                var10_12 = var6_13;
                var6_13 = var7_16;
            }
        }
        var16_20 = var19_8.D1.size();
        var17_6 = var19_8.Q;
        var18_7 = var19_8.R;
        var20_10 = var19_8.S;
        var21_9 = var19_8.T;
        var10_12 = var19_8.E1();
        var8_14 = var19_8.G1();
        var11_17 = var19_8.F1();
        var12_18 = var19_8.D1();
        var1_1 /* !! */  = var19_8.C();
        var2_2 = var1_1 /* !! */  != (var22_21 = e.b.d) && var19_8.V() != var22_21 ? 0 : 1;
        if (var7_16 > 0 && var2_2 != 0) {
            for (var2_2 = 0; var2_2 < var16_20; ++var2_2) {
                var1_1 /* !! */  = (a)var19_8.D1.get(var2_2);
                if (var3_3 == 0) {
                    var1_1 /* !! */ .g(var4_4 - var1_1 /* !! */ .f());
                    continue;
                }
                var1_1 /* !! */ .g(var4_4 - var1_1 /* !! */ .e());
            }
        }
        var6_13 = 0;
        var7_16 = var8_14;
        var2_2 = 0;
        var8_14 = var13_11;
        var13_11 = var2_2;
        for (var9_15 = 0; var9_15 < var16_20; ++var9_15) {
            var22_21 = (a)var19_8.D1.get(var9_15);
            if (var3_3 == 0) {
                if (var9_15 < var16_20 - 1) {
                    var1_1 /* !! */  = u.g$a.a((a)((a)var19_8.D1.get((int)(var9_15 + 1)))).R;
                    var2_2 = 0;
                } else {
                    var1_1 /* !! */  = var19_8.T;
                    var2_2 = var19_8.D1();
                }
                var21_9 = u.g$a.a((a)var22_21).T;
                var22_21.j(var3_3, (d)var17_6, (d)var18_7, (d)var20_10, (d)var1_1 /* !! */ , var10_12, var7_16, var11_17, var2_2, var4_4);
                var7_16 = Math.max(var13_11, var22_21.f());
                var6_13 = var12_18 = var22_21.e() + var6_13;
                if (var9_15 > 0) {
                    var6_13 = var12_18 + var19_8.x1;
                }
                var18_7 = var21_9;
                var14_19 = 0;
                var12_18 = var2_2;
            } else {
                if (var9_15 < var16_20 - 1) {
                    var1_1 /* !! */  = u.g$a.a((a)((a)var19_8.D1.get((int)(var9_15 + 1)))).Q;
                    var2_2 = 0;
lbl151:
                    // 2 sources

                    while (true) {
                        continue;
                        break;
                    }
                }
                var1_1 /* !! */  = var19_8.S;
                var2_2 = var19_8.F1();
                ** continue;
                var20_10 = var1_1 /* !! */ ;
                var1_1 /* !! */  = u.g$a.a((a)var22_21).S;
                var22_21.j(var3_3, (d)var17_6, (d)var18_7, (d)var20_10, (d)var21_9, var10_12, var7_16, var2_2, var12_18, var4_4);
                var10_12 = var22_21.f() + var13_11;
                var15_22 = Math.max(var6_13, var22_21.e());
                var6_13 = var10_12;
                if (var9_15 > 0) {
                    var6_13 = var10_12 + var19_8.w1;
                }
                var13_11 = var6_13;
                var10_12 = 0;
                var17_6 = var1_1 /* !! */ ;
                var11_17 = var2_2;
                var14_19 = var7_16;
                var1_1 /* !! */  = var21_9;
                var6_13 = var15_22;
                var7_16 = var13_11;
            }
            var13_11 = var7_16;
            var21_9 = var1_1 /* !! */ ;
            var7_16 = var14_19;
        }
        var5_5[0] = var13_11;
        var5_5[var8_14] = var6_13;
    }

    public final void u2(e[] eArray, int n3, int n4, int n5, int[] nArray) {
        a a4;
        if (n3 == 0) {
            return;
        }
        if (this.D1.size() == 0) {
            a4 = new a(this, n4, this.Q, this.R, this.S, this.T, n5);
            this.D1.add(a4);
        } else {
            a4 = (a)this.D1.get(0);
            a4.c();
            a4.j(n4, this.Q, this.R, this.S, this.T, this.E1(), this.G1(), this.F1(), this.D1(), n5);
        }
        for (n4 = 0; n4 < n3; ++n4) {
            a4.b(eArray[n4]);
        }
        nArray[0] = a4.f();
        nArray[1] = a4.e();
    }

    public void v2(float f3) {
        this.s1 = f3;
    }

    public void w2(int n3) {
        this.m1 = n3;
    }

    public void x2(float f3) {
        this.t1 = f3;
    }

    public void y2(int n3) {
        this.n1 = n3;
    }

    public void z2(int n3) {
        this.y1 = n3;
    }

    public class a {
        public int a;
        public e b;
        public int c;
        public d d;
        public d e;
        public d f;
        public d g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public final g r;

        public a(g g3, int n3, d d3, d d4, d d5, d d6, int n4) {
            this.r = g3;
            this.b = null;
            this.c = 0;
            this.h = 0;
            this.i = 0;
            this.j = 0;
            this.k = 0;
            this.l = 0;
            this.m = 0;
            this.n = 0;
            this.o = 0;
            this.p = 0;
            this.q = 0;
            this.a = n3;
            this.d = d3;
            this.e = d4;
            this.f = d5;
            this.g = d6;
            this.h = g3.E1();
            this.i = g3.G1();
            this.j = g3.F1();
            this.k = g3.D1();
            this.q = n4;
        }

        public static /* synthetic */ e a(a a4) {
            return a4.b;
        }

        public void b(e e3) {
            int n3 = this.a;
            int n4 = 0;
            int n5 = 0;
            if (n3 == 0) {
                n3 = this.r.q2(e3, this.q);
                if (e3.C() == e.b.e) {
                    ++this.p;
                    n3 = 0;
                }
                n4 = this.r.w1;
                if (e3.X() == 8) {
                    n4 = n5;
                }
                this.l += n3 + n4;
                n3 = this.r.p2(e3, this.q);
                if (this.b == null || this.c < n3) {
                    this.b = e3;
                    this.c = n3;
                    this.m = n3;
                }
            } else {
                int n6 = this.r.q2(e3, this.q);
                n3 = this.r.p2(e3, this.q);
                if (e3.V() == e.b.e) {
                    ++this.p;
                    n3 = 0;
                }
                n5 = this.r.x1;
                if (e3.X() != 8) {
                    n4 = n5;
                }
                this.m += n3 + n4;
                if (this.b == null || this.c < n6) {
                    this.b = e3;
                    this.c = n6;
                    this.l = n6;
                }
            }
            ++this.o;
        }

        public void c() {
            this.c = 0;
            this.b = null;
            this.l = 0;
            this.m = 0;
            this.n = 0;
            this.o = 0;
            this.p = 0;
        }

        /*
         * Unable to fully structure code
         */
        public void d(boolean var1_1, int var2_2, boolean var3_3) {
            block63: {
                block64: {
                    var12_4 = this.o;
                    for (var6_5 = 0; var6_5 < var12_4 && this.n + var6_5 < u.g.i2(this.r); ++var6_5) {
                        var13_6 = u.g.j2(this.r)[this.n + var6_5];
                        if (var13_6 == null) continue;
                        var13_6.x0();
                    }
                    if (var12_4 == 0 || this.b == null) break block63;
                    var9_7 = var3_3 != false && var2_2 == 0;
                    var7_8 = -1;
                    var8_9 = -1;
                    for (var6_5 = 0; var6_5 < var12_4 && this.n + (var10_10 = var1_1 != false ? var12_4 - 1 - var6_5 : var6_5) < u.g.i2(this.r); ++var6_5) {
                        var13_6 = u.g.j2(this.r)[this.n + var10_10];
                        var11_11 = var7_8;
                        var10_10 = var8_9;
                        if (var13_6 != null) {
                            var11_11 = var7_8;
                            var10_10 = var8_9;
                            if (var13_6.X() == 0) {
                                var8_9 = var7_8;
                                if (var7_8 == -1) {
                                    var8_9 = var6_5;
                                }
                                var10_10 = var6_5;
                                var11_11 = var8_9;
                            }
                        }
                        var7_8 = var11_11;
                        var8_9 = var10_10;
                    }
                    var6_5 = this.a;
                    var14_12 = null;
                    var13_6 = null;
                    if (var6_5 != 0) break block64;
                    var15_13 = this.b;
                    var15_13.j1(u.g.k2(this.r));
                    var6_5 = var10_10 = this.i;
                    if (var2_2 > 0) {
                        var6_5 = var10_10 + u.g.V1(this.r);
                    }
                    var15_13.R.a(this.e, var6_5);
                    if (var3_3) {
                        var15_13.T.a(this.g, this.k);
                    }
                    if (var2_2 > 0) {
                        this.e.d.T.a(var15_13.R, 0);
                    }
                    var2_2 = u.g.l2(this.r);
                    var10_10 = 3;
                    if (var2_2 == 3 && !var15_13.b0()) {
                        for (var2_2 = 0; var2_2 < var12_4 && this.n + (var6_5 = var1_1 != false ? var12_4 - 1 - var2_2 : var2_2) < u.g.i2(this.r); ++var2_2) {
                            var14_12 = u.g.j2(this.r)[this.n + var6_5];
                            if (!var14_12.b0()) {
                                continue;
                            }
                            break;
                        }
                    } else {
                        var14_12 = var15_13;
                    }
                    var2_2 = var10_10;
                    for (var6_5 = 0; var6_5 < var12_4 && this.n + (var10_10 = var1_1 != false ? var12_4 - 1 - var6_5 : var6_5) < u.g.i2(this.r); ++var6_5) {
                        block65: {
                            block62: {
                                block66: {
                                    var16_19 = u.g.j2(this.r)[this.n + var10_10];
                                    if (var16_19 == null) continue;
                                    if (var6_5 == 0) {
                                        var16_19.l(var16_19.Q, this.d, this.h);
                                    }
                                    if (var10_10 != 0) break block65;
                                    var10_10 = u.g.m2(this.r);
                                    var4_15 = var5_17 = u.g.n2(this.r);
                                    if (var1_1) {
                                        var4_15 = 1.0f - var5_17;
                                    }
                                    if (this.n != 0 || u.g.W1(this.r) == -1) break block66;
                                    var2_2 = u.g.W1(this.r);
                                    if (var1_1) {
                                        var4_15 = u.g.X1(this.r);
lbl68:
                                        // 2 sources

                                        while (true) {
                                            var4_15 = 1.0f - var4_15;
lbl70:
                                            // 3 sources

                                            while (true) {
                                                var5_17 = var4_15;
                                                break block62;
                                                break;
                                            }
                                            break;
                                        }
                                    }
                                    var4_15 = u.g.X1(this.r);
                                    ** GOTO lbl70
                                }
                                var2_2 = var10_10;
                                var5_17 = var4_15;
                                if (var3_3) {
                                    var2_2 = var10_10;
                                    var5_17 = var4_15;
                                    if (u.g.Y1(this.r) != -1) {
                                        var2_2 = u.g.Y1(this.r);
                                        if (var1_1) {
                                            var4_15 = u.g.Z1(this.r);
                                            ** continue;
                                        }
                                        var4_15 = u.g.Z1(this.r);
                                        ** continue;
                                    }
                                }
                            }
                            var16_19.S0(var2_2);
                            var16_19.R0(var5_17);
                        }
                        if (var6_5 == var12_4 - 1) {
                            var16_19.l(var16_19.S, this.f, this.j);
                        }
                        if (var13_6 != null) {
                            var16_19.Q.a(var13_6.S, u.g.U1(this.r));
                            if (var6_5 == var7_8) {
                                var16_19.Q.u(this.h);
                            }
                            var13_6.S.a(var16_19.Q, 0);
                            if (var6_5 == var8_9 + 1) {
                                var13_6.S.u(this.j);
                            }
                        }
                        if (var16_19 != var15_13) {
                            var10_10 = u.g.l2(this.r);
                            var2_2 = 3;
                            if (var10_10 == 3 && var14_12.b0() && var16_19 != var14_12 && var16_19.b0()) {
                                var16_19.U.a(var14_12.U, 0);
                                var13_6 = var16_19;
                                continue;
                            }
                            var10_10 = u.g.l2(this.r);
                            if (var10_10 != 0) {
                                if (var10_10 != 1) {
                                    if (var9_7) {
                                        var16_19.R.a(this.e, this.i);
                                        var16_19.T.a(this.g, this.k);
                                        var13_6 = var16_19;
                                        continue;
                                    }
                                    var16_19.R.a(var15_13.R, 0);
                                    var16_19.T.a(var15_13.T, 0);
                                    var13_6 = var16_19;
                                    continue;
                                }
                                var16_19.T.a(var15_13.T, 0);
                                var13_6 = var16_19;
                                continue;
                            }
                            var16_19.R.a(var15_13.R, 0);
                            var13_6 = var16_19;
                            continue;
                        }
                        var2_2 = 3;
                        var13_6 = var16_19;
                    }
                    break block63;
                }
                var15_14 = this.b;
                var15_14.S0(u.g.m2(this.r));
                var6_5 = var10_10 = this.h;
                if (var2_2 > 0) {
                    var6_5 = var10_10 + u.g.U1(this.r);
                }
                if (var1_1) {
                    var15_14.S.a(this.f, var6_5);
                    if (var3_3) {
                        var15_14.Q.a(this.d, this.j);
                    }
                    if (var2_2 > 0) {
                        this.f.d.Q.a(var15_14.S, 0);
                    }
                } else {
                    var15_14.Q.a(this.d, var6_5);
                    if (var3_3) {
                        var15_14.S.a(this.f, this.j);
                    }
                    if (var2_2 > 0) {
                        this.d.d.S.a(var15_14.Q, 0);
                    }
                }
                var13_6 = var14_12;
                for (var6_5 = 0; var6_5 < var12_4 && this.n + var6_5 < u.g.i2(this.r); ++var6_5) {
                    var14_12 = u.g.j2(this.r)[this.n + var6_5];
                    if (var14_12 == null) continue;
                    if (var6_5 == 0) {
                        var14_12.l(var14_12.R, this.e, this.i);
                        var10_10 = u.g.k2(this.r);
                        var5_18 = u.g.a2(this.r);
                        if (this.n == 0 && u.g.b2(this.r) != -1) {
                            var2_2 = u.g.b2(this.r);
                            var4_16 = u.g.c2(this.r);
                        } else {
                            var2_2 = var10_10;
                            var4_16 = var5_18;
                            if (var3_3) {
                                var2_2 = var10_10;
                                var4_16 = var5_18;
                                if (u.g.d2(this.r) != -1) {
                                    var2_2 = u.g.d2(this.r);
                                    var4_16 = u.g.e2(this.r);
                                }
                            }
                        }
                        var14_12.j1(var2_2);
                        var14_12.i1(var4_16);
                    }
                    if (var6_5 == var12_4 - 1) {
                        var14_12.l(var14_12.T, this.g, this.k);
                    }
                    if (var13_6 != null) {
                        var14_12.R.a(var13_6.T, u.g.V1(this.r));
                        if (var6_5 == var7_8) {
                            var14_12.R.u(this.i);
                        }
                        var13_6.T.a(var14_12.R, 0);
                        if (var6_5 == var8_9 + 1) {
                            var13_6.T.u(this.k);
                        }
                    }
                    if (var14_12 != var15_14) {
                        if (var1_1) {
                            var2_2 = u.g.f2(this.r);
                            if (var2_2 != 0) {
                                if (var2_2 != 1) {
                                    if (var2_2 == 2) {
                                        var14_12.Q.a(var15_14.Q, 0);
                                        var14_12.S.a(var15_14.S, 0);
                                    }
                                } else {
                                    var14_12.Q.a(var15_14.Q, 0);
                                }
                            } else {
                                var14_12.S.a(var15_14.S, 0);
                            }
                        } else {
                            var2_2 = u.g.f2(this.r);
                            if (var2_2 != 0) {
                                if (var2_2 != 1) {
                                    if (var2_2 == 2) {
                                        if (var9_7) {
                                            var14_12.Q.a(this.d, this.h);
                                            var14_12.S.a(this.f, this.j);
                                        } else {
                                            var14_12.Q.a(var15_14.Q, 0);
                                            var14_12.S.a(var15_14.S, 0);
                                        }
                                    }
                                } else {
                                    var14_12.S.a(var15_14.S, 0);
                                }
                            } else {
                                var14_12.Q.a(var15_14.Q, 0);
                            }
                        }
                    }
                    var13_6 = var14_12;
                }
            }
        }

        public int e() {
            if (this.a == 1) {
                return this.m - this.r.x1;
            }
            return this.m;
        }

        public int f() {
            if (this.a == 0) {
                return this.l - this.r.w1;
            }
            return this.l;
        }

        public void g(int n3) {
            int n4 = this.p;
            if (n4 == 0) {
                return;
            }
            int n5 = this.o;
            n3 /= n4;
            for (n4 = 0; n4 < n5 && this.n + n4 < this.r.I1; ++n4) {
                int n6;
                e e3 = this.r.H1[this.n + n4];
                if (this.a == 0) {
                    n6 = n3;
                    if (e3 != null) {
                        n6 = n3;
                        if (e3.C() == e.b.e) {
                            n6 = n3;
                            if (e3.w == 0) {
                                this.r.I1(e3, e.b.c, n3, e3.V(), e3.z());
                                n6 = n3;
                            }
                        }
                    }
                } else {
                    n6 = n3;
                    if (e3 != null) {
                        n6 = n3;
                        if (e3.V() == e.b.e) {
                            n6 = n3;
                            if (e3.x == 0) {
                                this.r.I1(e3, e3.C(), e3.Y(), e.b.c, n3);
                                n6 = n3;
                            }
                        }
                    }
                }
                n3 = n6;
            }
            this.h();
        }

        public final void h() {
            this.l = 0;
            this.m = 0;
            this.b = null;
            this.c = 0;
            int n3 = this.o;
            for (int i3 = 0; i3 < n3 && this.n + i3 < this.r.I1; ++i3) {
                int n4;
                int n5;
                e e3 = this.r.H1[this.n + i3];
                if (this.a == 0) {
                    n5 = e3.Y();
                    n4 = this.r.w1;
                    if (e3.X() == 8) {
                        n4 = 0;
                    }
                    this.l += n5 + n4;
                    n4 = this.r.p2(e3, this.q);
                    if (this.b != null && this.c >= n4) continue;
                    this.b = e3;
                    this.c = n4;
                    this.m = n4;
                    continue;
                }
                n5 = this.r.q2(e3, this.q);
                int n6 = this.r.p2(e3, this.q);
                n4 = this.r.x1;
                if (e3.X() == 8) {
                    n4 = 0;
                }
                this.m += n6 + n4;
                if (this.b != null && this.c >= n5) continue;
                this.b = e3;
                this.c = n5;
                this.l = n5;
            }
        }

        public void i(int n3) {
            this.n = n3;
        }

        public void j(int n3, d d3, d d4, d d5, d d6, int n4, int n5, int n6, int n7, int n8) {
            this.a = n3;
            this.d = d3;
            this.e = d4;
            this.f = d5;
            this.g = d6;
            this.h = n4;
            this.i = n5;
            this.j = n6;
            this.k = n7;
            this.q = n8;
        }
    }
}

