/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import r.d;
import r.i;
import u.c;
import u.e;
import u.f;

public abstract class b {
    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public static void a(f var0, d var1_1, int var2_2, int var3_3, c var4_4) {
        block74: {
            block73: {
                var25_5 = var4_4.a;
                var26_6 = var4_4.c;
                var18_7 = var4_4.b;
                var23_8 = var4_4.d;
                var27_9 = var4_4.e;
                var5_10 = var4_4.k;
                var11_11 = var0.b0[var2_2] == e.b.d ? 1 : 0;
                if (var2_2 == 0) {
                    var14_12 = var27_9.J0;
                    var9_13 = var14_12 == 0 ? 1 : 0;
                    var10_14 = var14_12 == 1 ? 1 : 0;
                    var12_15 = var9_13;
                    var13_16 = var10_14;
                    if (var14_12 == 2) {
                        var12_15 = var9_13;
lbl16:
                        // 2 sources

                        while (true) {
                            var9_13 = 1;
                            var13_16 = var10_14;
                            break block73;
                            break;
                        }
                    }
lbl20:
                    // 3 sources

                    while (true) {
                        var9_13 = 0;
                        break block73;
                        break;
                    }
                }
                var14_12 = var27_9.K0;
                var9_13 = var14_12 == 0 ? 1 : 0;
                var10_14 = var14_12 == 1 ? 1 : 0;
                var12_15 = var9_13;
                var13_16 = var10_14;
                ** while (var14_12 != 2)
lbl29:
                // 1 sources

                var12_15 = var9_13;
                ** while (true)
            }
            var20_17 = var25_5;
            var10_14 = 0;
            var15_18 = var11_11;
            var14_12 = var9_13;
            var9_13 = var10_14;
            while (true) {
                var24_24 /* !! */  = null;
                var21_22 = null;
                if (var9_13 != 0) break;
                var22_23 = var20_17.Y[var3_3];
                var10_14 = var14_12 != 0 ? 1 : 4;
                var11_11 = var22_23.f();
                var24_24 /* !! */  = var20_17.b0[var2_2];
                var19_21 /* !! */  = e.b.e;
                var17_20 = var24_24 /* !! */  == var19_21 /* !! */  && var20_17.y[var2_2] == 0;
                var24_24 /* !! */  = var22_23.f;
                var16_19 = var11_11;
                if (var24_24 /* !! */  != null) {
                    var16_19 = var11_11;
                    if (var20_17 != var25_5) {
                        var16_19 = var11_11 + var24_24 /* !! */ .f();
                    }
                }
                var11_11 = var10_14;
                if (var14_12 != 0) {
                    var11_11 = var10_14;
                    if (var20_17 != var25_5) {
                        var11_11 = var10_14;
                        if (var20_17 != var18_7) {
                            var11_11 = 8;
                        }
                    }
                }
                if ((var24_24 /* !! */  = var22_23.f) != null) {
                    if (var20_17 == var18_7) {
                        var1_1.h(var22_23.i, var24_24 /* !! */ .i, var16_19, 6);
                    } else {
                        var1_1.h(var22_23.i, var24_24 /* !! */ .i, var16_19, 8);
                    }
                    var10_14 = var11_11;
                    if (var17_20) {
                        var10_14 = var11_11;
                        if (var14_12 == 0) {
                            var10_14 = 5;
                        }
                    }
                    if (var20_17 == var18_7 && var14_12 != 0 && var20_17.j0(var2_2)) {
                        var10_14 = 5;
                    }
                    var1_1.e(var22_23.i, var22_23.f.i, var16_19, var10_14);
                }
                if (var15_18 != 0) {
                    if (var20_17.X() != 8 && var20_17.b0[var2_2] == var19_21 /* !! */ ) {
                        var19_21 /* !! */  = var20_17.Y;
                        var1_1.h(var19_21 /* !! */ [var3_3 + 1].i, var19_21 /* !! */ [var3_3].i, 0, 5);
                    }
                    var1_1.h(var20_17.Y[var3_3].i, var0.Y[var3_3].i, 0, 8);
                }
                var22_23 = var20_17.Y[var3_3 + 1].f;
                var19_21 /* !! */  = var21_22;
                if (var22_23 != null) {
                    var22_23 = var22_23.d;
                    var24_24 /* !! */  = var22_23.Y[var3_3].f;
                    var19_21 /* !! */  = var21_22;
                    if (var24_24 /* !! */  != null) {
                        var19_21 /* !! */  = var24_24 /* !! */ .d != var20_17 ? var21_22 : var22_23;
                    }
                }
                if (var19_21 /* !! */  == null) {
                    var9_13 = 1;
                    var19_21 /* !! */  = var20_17;
                }
                var20_17 = var19_21 /* !! */ ;
            }
            if (var23_8 == null) break block74;
            var19_21 /* !! */  = var26_6.Y;
            var9_13 = var3_3 + 1;
            if (var19_21 /* !! */ [var9_13].f == null) break block74;
            var19_21 /* !! */  = var23_8.Y[var9_13];
            if (var23_8.b0[var2_2] != e.b.e || var23_8.y[var2_2] != 0 || var14_12 != 0) ** GOTO lbl-1000
            var20_17 = var19_21 /* !! */ .f;
            if (var20_17.d == var0) {
                var1_1.e(var19_21 /* !! */ .i, var20_17.i, -var19_21 /* !! */ .f(), 5);
            } else if (var14_12 != 0) {
                var20_17 = var19_21 /* !! */ .f;
                if (var20_17.d == var0) {
                    var1_1.e(var19_21 /* !! */ .i, var20_17.i, -var19_21 /* !! */ .f(), 4);
                }
            }
            var1_1.j(var19_21 /* !! */ .i, var26_6.Y[var9_13].f.i, -var19_21 /* !! */ .f(), 6);
        }
        if (var15_18 != 0) {
            var0 = var0.Y;
            var9_13 = var3_3 + 1;
            var19_21 /* !! */  = var0[var9_13].i;
            var0 = var26_6.Y[var9_13];
            var1_1.h((i)var19_21 /* !! */ , var0.i, var0.f(), 8);
        }
        if ((var0 = var4_4.h) != null && (var10_14 = var0.size()) > 1) {
            var6_25 = var4_4.r != false && var4_4.t == false ? (float)var4_4.j : var5_10;
            var7_26 = 0.0f;
            var8_27 = 0.0f;
            var19_21 /* !! */  = null;
            for (var9_13 = 0; var9_13 < var10_14; ++var9_13) {
                var20_17 = (e)var0.get(var9_13);
                var5_10 = var20_17.N0[var2_2];
                if (var5_10 < var7_26) {
                    if (var4_4.t) {
                        var20_17 = var20_17.Y;
                        var1_1.e(var20_17[var3_3 + 1].i, var20_17[var3_3].i, 0, 4);
                        continue;
                    }
                    var5_10 = 1.0f;
                }
                if (var5_10 == var7_26) {
                    var20_17 = var20_17.Y;
                    var1_1.e(var20_17[var3_3 + 1].i, var20_17[var3_3].i, 0, 8);
                    continue;
                }
                if (var19_21 /* !! */  != null) {
                    var21_22 = var19_21 /* !! */ .Y;
                    var19_21 /* !! */  = var21_22[var3_3].i;
                    var11_11 = var3_3 + 1;
                    var21_22 = var21_22[var11_11].i;
                    var28_28 /* !! */  = var20_17.Y;
                    var22_23 = var28_28 /* !! */ [var3_3].i;
                    var28_28 /* !! */  = var28_28 /* !! */ [var11_11].i;
                    var29_29 = var1_1.r();
                    var29_29.l(var8_27, var6_25, var5_10, (i)var19_21 /* !! */ , (i)var21_22, (i)var22_23, (i)var28_28 /* !! */ );
                    var1_1.d(var29_29);
                }
                var19_21 /* !! */  = var20_17;
                var8_27 = var5_10;
            }
        }
        if (var18_7 != null && (var18_7 == var23_8 || var14_12 != 0)) {
            var0 = var25_5.Y[var3_3];
            var4_4 = var26_6.Y;
            var9_13 = var3_3 + 1;
            var19_21 /* !! */  = var4_4[var9_13];
            var0 = var0.f;
            var0 = var0 != null ? var0.i : null;
            var4_4 = var19_21 /* !! */ .f;
            var4_4 = var4_4 != null ? var4_4.i : null;
            var20_17 = var18_7.Y[var3_3];
            if (var23_8 != null) {
                var19_21 /* !! */  = var23_8.Y[var9_13];
            }
            if (var0 != null && var4_4 != null) {
                var5_10 = var2_2 == 0 ? var27_9.q0 : var27_9.r0;
                var9_13 = var20_17.f();
                var2_2 = var19_21 /* !! */ .f();
                var1_1.c(var20_17.i, (i)var0, var9_13, var5_10, (i)var4_4, var19_21 /* !! */ .i, var2_2, 7);
            }
        } else if (var12_15 != 0 && var18_7 != null) {
            var9_13 = var4_4.j;
            var10_14 = var9_13 > 0 && var4_4.i == var9_13 ? 1 : 0;
            var21_22 = var4_4 = var18_7;
            while (var4_4 != null) {
                var19_21 /* !! */  = var4_4.P0[var2_2];
                while (var19_21 /* !! */  != null && var19_21 /* !! */ .X() == 8) {
                    var19_21 /* !! */  = var19_21 /* !! */ .P0[var2_2];
                }
                if (var19_21 /* !! */  != null || var4_4 == var23_8) {
                    var20_17 = var4_4.Y[var3_3];
                    var27_9 = var20_17.i;
                    var0 = var20_17.f;
                    var0 = var0 != null ? var0.i : null;
                    if (var21_22 != var4_4) {
                        var0 = var21_22.Y[var3_3 + 1].i;
                    } else if (var4_4 == var18_7) {
                        var0 = var25_5.Y[var3_3].f;
                        var0 = var0 != null ? var0.i : null;
                    }
                    var15_18 = var20_17.f();
                    var20_17 = var4_4.Y;
                    var14_12 = var3_3 + 1;
                    var11_11 = var20_17[var14_12].f();
                    if (var19_21 /* !! */  != null) {
                        var22_23 = var19_21 /* !! */ .Y[var3_3];
                        var20_17 = var22_23.i;
                    } else {
                        var22_23 = var26_6.Y[var14_12].f;
                        var20_17 = var22_23 != null ? var22_23.i : null;
                    }
                    var28_28 /* !! */  = var4_4.Y[var14_12].i;
                    var9_13 = var11_11;
                    if (var22_23 != null) {
                        var9_13 = var11_11 + var22_23.f();
                    }
                    var11_11 = var15_18 + var21_22.Y[var14_12].f();
                    if (var27_9 != null && var0 != null && var20_17 != null && var28_28 /* !! */  != null) {
                        if (var4_4 == var18_7) {
                            var11_11 = var18_7.Y[var3_3].f();
                        }
                        if (var4_4 == var23_8) {
                            var9_13 = var23_8.Y[var14_12].f();
                        }
                        var14_12 = var10_14 != 0 ? 8 : 5;
                        var1_1.c((i)var27_9, (i)var0, var11_11, 0.5f, (i)var20_17, (i)var28_28 /* !! */ , var9_13, var14_12);
                    }
                }
                if (var4_4.X() != 8) {
                    var21_22 = var4_4;
                }
                var4_4 = var19_21 /* !! */ ;
            }
        } else if (var13_16 != 0 && var18_7 != null) {
            var9_13 = var4_4.j;
            var9_13 = var9_13 > 0 && var4_4.i == var9_13 ? 1 : 0;
            var21_22 = var4_4 = var18_7;
            while (var4_4 != null) {
                var0 = var4_4.P0[var2_2];
                while (var0 != null && var0.X() == 8) {
                    var0 = var0.P0[var2_2];
                }
                if (var4_4 != var18_7 && var4_4 != var23_8 && var0 != null) {
                    var19_21 /* !! */  = var0;
                    if (var0 == var23_8) {
                        var19_21 /* !! */  = null;
                    }
                    var0 = var4_4.Y[var3_3];
                    var27_9 = var0.i;
                    var20_17 = var0.f;
                    if (var20_17 != null) {
                        var20_17 = var20_17.i;
                    }
                    var20_17 = var21_22.Y;
                    var15_18 = var3_3 + 1;
                    var28_28 /* !! */  = var20_17[var15_18].i;
                    var14_12 = var0.f();
                    var11_11 = var4_4.Y[var15_18].f();
                    if (var19_21 /* !! */  != null) {
                        var22_23 = var19_21 /* !! */ .Y[var3_3];
                        var20_17 = var22_23.i;
                        var0 = var22_23.f;
                        var0 = var0 != null ? var0.i : null;
                    } else {
                        var22_23 = var23_8.Y[var3_3];
                        var20_17 = var22_23 != null ? var22_23.i : null;
                        var0 = var4_4.Y[var15_18].i;
                    }
                    var10_14 = var11_11;
                    if (var22_23 != null) {
                        var10_14 = var11_11 + var22_23.f();
                    }
                    var15_18 = var21_22.Y[var15_18].f();
                    var11_11 = var9_13 != 0 ? 8 : 4;
                    if (var27_9 != null && var28_28 /* !! */  != null && var20_17 != null && var0 != null) {
                        var1_1.c((i)var27_9, (i)var28_28 /* !! */ , var14_12 + var15_18, 0.5f, (i)var20_17, (i)var0, var10_14, var11_11);
                    }
                    var0 = var19_21 /* !! */ ;
                }
                if (var4_4.X() != 8) {
                    var21_22 = var4_4;
                }
                var4_4 = var0;
            }
            var0 = var18_7.Y[var3_3];
            var4_4 = var25_5.Y[var3_3].f;
            var19_21 /* !! */  = var23_8.Y;
            var2_2 = var3_3 + 1;
            var20_17 = var19_21 /* !! */ [var2_2];
            var19_21 /* !! */  = var26_6.Y[var2_2].f;
            if (var4_4 != null) {
                if (var18_7 != var23_8) {
                    var1_1.e(var0.i, var4_4.i, var0.f(), 5);
                } else if (var19_21 /* !! */  != null) {
                    var1_1.c(var0.i, var4_4.i, var0.f(), 0.5f, var20_17.i, var19_21 /* !! */ .i, var20_17.f(), 5);
                }
            }
            if (var19_21 /* !! */  != null && var18_7 != var23_8) {
                var1_1.e(var20_17.i, var19_21 /* !! */ .i, -var20_17.f(), 5);
            }
        }
        if ((var12_15 != 0 || var13_16 != 0) && var18_7 != null && var18_7 != var23_8) {
            var22_23 = var18_7.Y;
            var21_22 = var22_23[var3_3];
            var4_4 = var23_8;
            if (var23_8 == null) {
                var4_4 = var18_7;
            }
            var0 = var4_4.Y;
            var20_17 = var0[++var3_3];
            var0 = var21_22.f;
            var19_21 /* !! */  = var0 != null ? var0.i : null;
            var0 = var20_17.f;
            var0 = var0 != null ? var0.i : null;
            if (var26_6 != var4_4) {
                var23_8 = var26_6.Y[var3_3].f;
                var0 = var24_24 /* !! */ ;
                if (var23_8 != null) {
                    var0 = var23_8.i;
                }
            }
            if (var18_7 == var4_4) {
                var20_17 = var22_23[var3_3];
            }
            if (var19_21 /* !! */  != null && var0 != null) {
                var2_2 = var21_22.f();
                var3_3 = var4_4.Y[var3_3].f();
                var1_1.c(var21_22.i, (i)var19_21 /* !! */ , var2_2, 0.5f, (i)var0, var20_17.i, var3_3, 5);
            }
        }
    }

    public static void b(f f3, d d3, ArrayList arrayList, int n3) {
        int n4;
        c[] cArray;
        int n5;
        int n6 = 0;
        if (n3 == 0) {
            n5 = f3.g1;
            cArray = f3.j1;
            n4 = 0;
        } else {
            n5 = f3.h1;
            cArray = f3.i1;
            n4 = 2;
        }
        while (n6 < n5) {
            c c3 = cArray[n6];
            c3.a();
            if (arrayList == null || arrayList.contains(c3.a)) {
                b.a(f3, d3, n3, n4, c3);
            }
            ++n6;
        }
    }
}

