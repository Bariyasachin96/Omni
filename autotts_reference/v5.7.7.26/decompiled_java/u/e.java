/*
 * Decompiled with CFR 0.152.
 */
package u;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import r.c;
import r.i;
import u.d;
import u.f;
import u.h;
import u.k;
import u.m;
import v.l;
import v.n;
import v.p;

public class e {
    public static float U0 = 0.5f;
    public int A = 0;
    public int A0;
    public float B = 1.0f;
    public int B0;
    public int C = 0;
    public boolean C0;
    public int D = 0;
    public boolean D0;
    public float E = 1.0f;
    public boolean E0;
    public boolean F;
    public boolean F0;
    public boolean G;
    public boolean G0;
    public int H = -1;
    public boolean H0;
    public float I = 1.0f;
    public boolean I0;
    public int[] J;
    public int J0;
    public float K;
    public int K0;
    public boolean L = false;
    public boolean L0;
    public boolean M;
    public boolean M0;
    public boolean N = false;
    public float[] N0;
    public int O = 0;
    public e[] O0;
    public int P = 0;
    public e[] P0;
    public d Q;
    public e Q0;
    public d R;
    public e R0;
    public d S;
    public int S0;
    public d T;
    public int T0;
    public d U;
    public d V;
    public d W;
    public d X;
    public d[] Y;
    public ArrayList Z;
    public boolean a = false;
    public boolean[] a0;
    public p[] b = new p[2];
    public b[] b0;
    public v.c c;
    public e c0;
    public v.c d;
    public int d0;
    public l e = null;
    public int e0;
    public n f = null;
    public float f0;
    public boolean[] g = new boolean[]{true, true};
    public int g0;
    public boolean h = false;
    public int h0;
    public boolean i = true;
    public int i0;
    public boolean j = false;
    public int j0;
    public boolean k = true;
    public int k0;
    public int l = -1;
    public int l0;
    public int m = -1;
    public int m0;
    public t.a n = new t.a(this);
    public int n0;
    public String o;
    public int o0;
    public boolean p = false;
    public int p0;
    public boolean q = false;
    public float q0;
    public boolean r = false;
    public float r0;
    public boolean s = false;
    public Object s0;
    public int t = -1;
    public int t0;
    public int u = -1;
    public int u0;
    public int v = 0;
    public boolean v0;
    public int w = 0;
    public String w0;
    public int x = 0;
    public String x0;
    public int[] y = new int[2];
    public int y0;
    public int z = 0;
    public int z0;

    public e() {
        float f3;
        Object object;
        this.J = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.K = Float.NaN;
        this.Q = new d(this, d.a.d);
        this.R = new d(this, d.a.e);
        this.S = new d(this, d.a.f);
        this.T = new d(this, d.a.g);
        this.U = new d(this, d.a.h);
        this.V = new d(this, d.a.j);
        this.W = new d(this, d.a.k);
        this.X = object = new d(this, d.a.i);
        this.Y = new d[]{this.Q, this.S, this.R, this.T, this.U, object};
        this.Z = new ArrayList();
        this.a0 = new boolean[2];
        object = u.e$b.c;
        this.b0 = new b[]{object, object};
        this.c0 = null;
        this.d0 = 0;
        this.e0 = 0;
        this.f0 = 0.0f;
        this.g0 = -1;
        this.h0 = 0;
        this.i0 = 0;
        this.j0 = 0;
        this.k0 = 0;
        this.l0 = 0;
        this.m0 = 0;
        this.n0 = 0;
        this.q0 = f3 = U0;
        this.r0 = f3;
        this.t0 = 0;
        this.u0 = 0;
        this.v0 = false;
        this.w0 = null;
        this.x0 = null;
        this.I0 = false;
        this.J0 = 0;
        this.K0 = 0;
        this.N0 = new float[]{-1.0f, -1.0f};
        this.O0 = new e[]{null, null};
        this.P0 = new e[]{null, null};
        this.Q0 = null;
        this.R0 = null;
        this.S0 = -1;
        this.T0 = -1;
        this.d();
    }

    public float A() {
        return this.q0;
    }

    public final void A0(StringBuilder stringBuilder, String string, float f3, float f4) {
        if (f3 == f4) {
            return;
        }
        stringBuilder.append(string);
        stringBuilder.append(" :   ");
        stringBuilder.append(f3);
        stringBuilder.append(",\n");
    }

    public int B() {
        return this.J0;
    }

    public final void B0(StringBuilder stringBuilder, String string, int n3, int n4) {
        if (n3 == n4) {
            return;
        }
        stringBuilder.append(string);
        stringBuilder.append(" :   ");
        stringBuilder.append(n3);
        stringBuilder.append(",\n");
    }

    public b C() {
        return this.b0[0];
    }

    public final void C0(StringBuilder stringBuilder, String string, String string2, String string3) {
        if (string3.equals(string2)) {
            return;
        }
        stringBuilder.append(string);
        stringBuilder.append(" :   ");
        stringBuilder.append(string2);
        stringBuilder.append(",\n");
    }

    public int D() {
        d d3 = this.Q;
        int n3 = d3 != null ? d3.g : 0;
        d3 = this.S;
        int n4 = n3;
        if (d3 != null) {
            n4 = n3 + d3.g;
        }
        return n4;
    }

    public final void D0(StringBuilder stringBuilder, String string, float f3, int n3) {
        if (f3 == 0.0f) {
            return;
        }
        stringBuilder.append(string);
        stringBuilder.append(" :  [");
        stringBuilder.append(f3);
        stringBuilder.append(",");
        stringBuilder.append(n3);
        stringBuilder.append("");
        stringBuilder.append("],\n");
    }

    public int E() {
        return this.O;
    }

    public void E0(boolean bl) {
        this.v0 = bl;
    }

    public int F() {
        return this.P;
    }

    public void F0(int n3) {
        this.n0 = n3;
        boolean bl = n3 > 0;
        this.L = bl;
    }

    public int G(int n3) {
        if (n3 == 0) {
            return this.Y();
        }
        if (n3 == 1) {
            return this.z();
        }
        return 0;
    }

    public void G0(Object object) {
        this.s0 = object;
    }

    public int H() {
        return this.J[1];
    }

    public void H0(String string) {
        this.w0 = string;
    }

    public int I() {
        return this.J[0];
    }

    /*
     * Unable to fully structure code
     */
    public void I0(String var1_1) {
        block8: {
            block10: {
                block9: {
                    if (var1_1 == null || var1_1.length() == 0) break block8;
                    var9_3 = var1_1.length();
                    var10_4 = var1_1.indexOf(44);
                    var7_5 = 0;
                    var8_6 = 0;
                    var6_7 = -1;
                    var5_8 = var7_5;
                    var4_9 = var6_7;
                    if (var10_4 > 0) {
                        var5_8 = var7_5;
                        var4_9 = var6_7;
                        if (var10_4 < var9_3 - 1) {
                            var11_10 = var1_1.substring(0, var10_4);
                            var4_9 = var11_10.equalsIgnoreCase("W") != false ? var8_6 : (var11_10.equalsIgnoreCase("H") != false ? 1 : -1);
                            var5_8 = var10_4 + 1;
                        }
                    }
                    if ((var6_7 = var1_1.indexOf(58)) < 0 || var6_7 >= var9_3 - 1) break block9;
                    var11_10 = var1_1.substring(var5_8, var6_7);
                    var1_1 = var1_1.substring(var6_7 + 1);
                    if (var11_10.length() <= 0 || var1_1.length() <= 0) break block10;
                    var2_11 = Float.parseFloat(var11_10);
                    var3_12 = Float.parseFloat(var1_1);
                    if (!(var2_11 > 0.0f) || !(var3_12 > 0.0f)) break block10;
                    if (var4_9 != 1) ** GOTO lbl28
                    var2_11 = Math.abs(var3_12 / var2_11);
                    ** GOTO lbl40
lbl28:
                    // 1 sources

                    var2_11 = Math.abs(var2_11 / var3_12);
                    ** GOTO lbl40
                }
                if ((var1_1 = var1_1.substring(var5_8)).length() <= 0) break block10;
                try {
                    var2_11 = Float.parseFloat(var1_1);
                    ** GOTO lbl40
                }
                catch (NumberFormatException var1_2) {
                    ** continue;
                }
            }
            while (true) {
                var2_11 = 0.0f;
lbl40:
                // 4 sources

                if (var2_11 > 0.0f) {
                    this.f0 = var2_11;
                    this.g0 = var4_9;
                }
                return;
            }
        }
        this.f0 = 0.0f;
    }

    public int J() {
        return this.p0;
    }

    public void J0(int n3) {
        if (!this.L) {
            return;
        }
        int n4 = n3 - this.n0;
        int n5 = this.e0;
        this.i0 = n4;
        this.R.t(n4);
        this.T.t(n5 + n4);
        this.U.t(n3);
        this.q = true;
    }

    public int K() {
        return this.o0;
    }

    public void K0(int n3, int n4) {
        if (this.p) {
            return;
        }
        this.Q.t(n3);
        this.S.t(n4);
        this.h0 = n3;
        this.d0 = n4 - n3;
        this.p = true;
    }

    public e L(int n3) {
        if (n3 == 0) {
            d d3 = this.S;
            d d4 = d3.f;
            if (d4 != null && d4.f == d3) {
                return d4.d;
            }
        } else if (n3 == 1) {
            d d5 = this.T;
            d d6 = d5.f;
            if (d6 != null && d6.f == d5) {
                return d6.d;
            }
        }
        return null;
    }

    public void L0(int n3) {
        this.Q.t(n3);
        this.h0 = n3;
    }

    public e M() {
        return this.c0;
    }

    public void M0(int n3) {
        this.R.t(n3);
        this.i0 = n3;
    }

    public e N(int n3) {
        if (n3 == 0) {
            d d3 = this.Q;
            d d4 = d3.f;
            if (d4 != null && d4.f == d3) {
                return d4.d;
            }
        } else if (n3 == 1) {
            d d5 = this.R;
            d d6 = d5.f;
            if (d6 != null && d6.f == d5) {
                return d6.d;
            }
        }
        return null;
    }

    public void N0(int n3, int n4) {
        if (this.q) {
            return;
        }
        this.R.t(n3);
        this.T.t(n4);
        this.i0 = n3;
        this.e0 = n4 - n3;
        if (this.L) {
            this.U.t(n3 + this.n0);
        }
        this.q = true;
    }

    public int O() {
        return this.Z() + this.d0;
    }

    public void O0(int n3, int n4, int n5, int n6) {
        int n7 = n5 - n3;
        n5 = n6 - n4;
        this.h0 = n3;
        this.i0 = n4;
        if (this.u0 == 8) {
            this.d0 = 0;
            this.e0 = 0;
            return;
        }
        b[] bArray = this.b0;
        b b3 = bArray[0];
        b b4 = u.e$b.c;
        n3 = n7;
        if (b3 == b4) {
            n4 = this.d0;
            n3 = n7;
            if (n7 < n4) {
                n3 = n4;
            }
        }
        n4 = n5;
        if (bArray[1] == b4) {
            n6 = this.e0;
            n4 = n5;
            if (n5 < n6) {
                n4 = n6;
            }
        }
        this.d0 = n3;
        this.e0 = n4;
        n5 = this.p0;
        if (n4 < n5) {
            this.e0 = n5;
        }
        if (n3 < (n5 = this.o0)) {
            this.d0 = n5;
        }
        if ((n5 = this.A) > 0 && b3 == u.e$b.e) {
            this.d0 = Math.min(this.d0, n5);
        }
        if ((n5 = this.D) > 0 && this.b0[1] == u.e$b.e) {
            this.e0 = Math.min(this.e0, n5);
        }
        if (n3 != (n5 = this.d0)) {
            this.l = n5;
        }
        if (n4 != (n3 = this.e0)) {
            this.m = n3;
        }
    }

    public p P(int n3) {
        if (n3 == 0) {
            return this.e;
        }
        if (n3 == 1) {
            return this.f;
        }
        return null;
    }

    public void P0(boolean bl) {
        this.L = bl;
    }

    public void Q(StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("  ");
        stringBuilder2.append(this.o);
        stringBuilder2.append(":{\n");
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("    actualWidth:");
        stringBuilder2.append(this.d0);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("    actualHeight:");
        stringBuilder2.append(this.e0);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("    actualLeft:");
        stringBuilder2.append(this.h0);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("    actualTop:");
        stringBuilder2.append(this.i0);
        stringBuilder.append(stringBuilder2.toString());
        stringBuilder.append("\n");
        this.S(stringBuilder, "left", this.Q);
        this.S(stringBuilder, "top", this.R);
        this.S(stringBuilder, "right", this.S);
        this.S(stringBuilder, "bottom", this.T);
        this.S(stringBuilder, "baseline", this.U);
        this.S(stringBuilder, "centerX", this.V);
        this.S(stringBuilder, "centerY", this.W);
        this.R(stringBuilder, "    width", this.d0, this.o0, this.J[0], this.l, this.z, this.w, this.B, this.b0[0], this.N0[0]);
        this.R(stringBuilder, "    height", this.e0, this.p0, this.J[1], this.m, this.C, this.x, this.E, this.b0[1], this.N0[1]);
        this.D0(stringBuilder, "    dimensionRatio", this.f0, this.g0);
        this.A0(stringBuilder, "    horizontalBias", this.q0, U0);
        this.A0(stringBuilder, "    verticalBias", this.r0, U0);
        this.B0(stringBuilder, "    horizontalChainStyle", this.J0, 0);
        this.B0(stringBuilder, "    verticalChainStyle", this.K0, 0);
        stringBuilder.append("  }");
    }

    public void Q0(int n3) {
        this.e0 = n3;
        int n4 = this.p0;
        if (n3 < n4) {
            this.e0 = n4;
        }
    }

    public final void R(StringBuilder stringBuilder, String string, int n3, int n4, int n5, int n6, int n7, int n8, float f3, b b3, float f4) {
        stringBuilder.append(string);
        stringBuilder.append(" :  {\n");
        this.C0(stringBuilder, "      behavior", ((Object)((Object)b3)).toString(), ((Object)((Object)u.e$b.c)).toString());
        this.B0(stringBuilder, "      size", n3, 0);
        this.B0(stringBuilder, "      min", n4, 0);
        this.B0(stringBuilder, "      max", n5, Integer.MAX_VALUE);
        this.B0(stringBuilder, "      matchMin", n7, 0);
        this.B0(stringBuilder, "      matchDef", n8, 0);
        this.A0(stringBuilder, "      matchPercent", f3, 1.0f);
        stringBuilder.append("    },\n");
    }

    public void R0(float f3) {
        this.q0 = f3;
    }

    public final void S(StringBuilder stringBuilder, String string, d d3) {
        if (d3.f == null) {
            return;
        }
        stringBuilder.append("    ");
        stringBuilder.append(string);
        stringBuilder.append(" : [ '");
        stringBuilder.append(d3.f);
        stringBuilder.append("'");
        if (d3.h != Integer.MIN_VALUE || d3.g != 0) {
            stringBuilder.append(",");
            stringBuilder.append(d3.g);
            if (d3.h != Integer.MIN_VALUE) {
                stringBuilder.append(",");
                stringBuilder.append(d3.h);
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(" ] ,\n");
    }

    public void S0(int n3) {
        this.J0 = n3;
    }

    public float T() {
        return this.r0;
    }

    public void T0(int n3, int n4) {
        this.h0 = n3;
        this.d0 = n4 -= n3;
        n3 = this.o0;
        if (n4 < n3) {
            this.d0 = n3;
        }
    }

    public int U() {
        return this.K0;
    }

    public void U0(b b3) {
        this.b0[0] = b3;
    }

    public b V() {
        return this.b0[1];
    }

    public void V0(int n3, int n4, int n5, float f3) {
        this.w = n3;
        this.z = n4;
        n4 = n5;
        if (n5 == Integer.MAX_VALUE) {
            n4 = 0;
        }
        this.A = n4;
        this.B = f3;
        if (f3 > 0.0f && f3 < 1.0f && n3 == 0) {
            this.w = 2;
        }
    }

    public int W() {
        int n3 = this.Q != null ? this.R.g : 0;
        int n4 = n3;
        if (this.S != null) {
            n4 = n3 + this.T.g;
        }
        return n4;
    }

    public void W0(float f3) {
        this.N0[0] = f3;
    }

    public int X() {
        return this.u0;
    }

    public void X0(int n3, boolean bl) {
        this.a0[n3] = bl;
    }

    public int Y() {
        if (this.u0 == 8) {
            return 0;
        }
        return this.d0;
    }

    public void Y0(boolean bl) {
        this.M = bl;
    }

    public int Z() {
        e e3 = this.c0;
        if (e3 != null && e3 instanceof f) {
            return ((f)e3).c1 + this.h0;
        }
        return this.h0;
    }

    public void Z0(boolean bl) {
        this.N = bl;
    }

    public int a0() {
        e e3 = this.c0;
        if (e3 != null && e3 instanceof f) {
            return ((f)e3).d1 + this.i0;
        }
        return this.i0;
    }

    public void a1(int n3, int n4) {
        this.O = n3;
        this.P = n4;
        this.d1(false);
    }

    public boolean b0() {
        return this.L;
    }

    public void b1(int n3) {
        this.J[1] = n3;
    }

    public boolean c0(int n3) {
        int n4;
        int n5;
        if (n3 == 0) {
            int n6;
            n3 = this.Q.f != null ? 1 : 0;
            return n3 + (n6 = this.S.f != null ? 1 : 0) < 2;
        }
        n3 = this.R.f != null ? 1 : 0;
        return n3 + (n5 = this.T.f != null ? 1 : 0) + (n4 = this.U.f != null ? 1 : 0) < 2;
    }

    public void c1(int n3) {
        this.J[0] = n3;
    }

    public final void d() {
        this.Z.add(this.Q);
        this.Z.add(this.R);
        this.Z.add(this.S);
        this.Z.add(this.T);
        this.Z.add(this.V);
        this.Z.add(this.W);
        this.Z.add(this.X);
        this.Z.add(this.U);
    }

    public boolean d0() {
        int n3 = this.Z.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            if (!((d)this.Z.get(i3)).m()) continue;
            return true;
        }
        return false;
    }

    public void d1(boolean bl) {
        this.i = bl;
    }

    public void e(f f3, r.d d3, HashSet hashSet, int n3, boolean bl) {
        block10: {
            block11: {
                block9: {
                    if (!bl) break block9;
                    if (!hashSet.contains(this)) break block10;
                    u.k.a(f3, d3, this);
                    hashSet.remove(this);
                    this.g(d3, f3.Z1(64));
                }
                if (n3 != 0) break block11;
                Object object = this.Q.d();
                if (object != null) {
                    object = ((HashSet)object).iterator();
                    while (object.hasNext()) {
                        ((d)object.next()).d.e(f3, d3, hashSet, n3, true);
                    }
                }
                if ((object = this.S.d()) == null) break block10;
                object = ((HashSet)object).iterator();
                while (object.hasNext()) {
                    ((d)object.next()).d.e(f3, d3, hashSet, n3, true);
                }
                break block10;
            }
            Object object = this.R.d();
            if (object != null) {
                object = ((HashSet)object).iterator();
                while (object.hasNext()) {
                    ((d)object.next()).d.e(f3, d3, hashSet, n3, true);
                }
            }
            if ((object = this.T.d()) != null) {
                object = ((HashSet)object).iterator();
                while (object.hasNext()) {
                    ((d)object.next()).d.e(f3, d3, hashSet, n3, true);
                }
            }
            if ((object = this.U.d()) != null) {
                object = ((HashSet)object).iterator();
                while (object.hasNext()) {
                    ((d)object.next()).d.e(f3, d3, hashSet, n3, true);
                }
            }
        }
    }

    public boolean e0() {
        return this.l != -1 || this.m != -1;
        {
        }
    }

    public void e1(int n3) {
        if (n3 < 0) {
            this.p0 = 0;
            return;
        }
        this.p0 = n3;
    }

    public boolean f() {
        return this instanceof m || this instanceof h;
        {
        }
    }

    public boolean f0(int n3, int n4) {
        if (n3 == 0) {
            d d3 = this.Q.f;
            if (d3 != null && d3.n() && (d3 = this.S.f) != null && d3.n()) {
                return this.S.f.e() - this.S.f() - (this.Q.f.e() + this.Q.f()) >= n4;
            }
        } else {
            d d4 = this.R.f;
            if (d4 != null && d4.n() && (d4 = this.T.f) != null && d4.n() && this.T.f.e() - this.T.f() - (this.R.f.e() + this.R.f()) >= n4) {
                return true;
            }
        }
        return false;
    }

    public void f1(int n3) {
        if (n3 < 0) {
            this.o0 = 0;
            return;
        }
        this.o0 = n3;
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public void g(r.d var1_1, boolean var2_2) {
        block65: {
            block67: {
                block66: {
                    block61: {
                        block63: {
                            block64: {
                                block62: {
                                    block60: {
                                        block58: {
                                            block59: {
                                                block57: {
                                                    var32_3 = var1_1.q(this.Q);
                                                    var26_4 = var1_1.q(this.S);
                                                    var31_5 = var1_1.q(this.R);
                                                    var33_6 = var1_1.q(this.T);
                                                    var34_7 = var1_1.q(this.U);
                                                    var27_8 /* !! */  = this.c0;
                                                    if (var27_8 /* !! */  == null) break block57;
                                                    var14_9 = var27_8 /* !! */  != null && var27_8 /* !! */ .b0[0] == u.e$b.d;
                                                    var13_10 = var27_8 /* !! */  != null && var27_8 /* !! */ .b0[1] == u.e$b.d;
                                                    var4_11 = this.v;
                                                    if (var4_11 == 1) break block58;
                                                    if (var4_11 == 2) break block59;
                                                    if (var4_11 != 3) break block60;
                                                }
                                                var13_10 = false;
                                                var14_9 = false;
                                                break block60;
                                            }
                                            var14_9 = false;
                                            break block60;
                                        }
                                        var13_10 = false;
                                    }
                                    if (this.u0 == 8 && !this.v0 && !this.d0() && (var27_8 /* !! */  = (Object)this.a0)[0] == false && var27_8 /* !! */ [1] == false) {
                                        return;
                                    }
                                    var15_12 = this.p;
                                    if (var15_12 || this.q) {
                                        if (var15_12) {
                                            var1_1.f(var32_3, this.h0);
                                            var1_1.f((i)var26_4, this.h0 + this.d0);
                                            if (var14_9 && (var27_8 /* !! */  = this.c0) != null) {
                                                if (this.k) {
                                                    var27_8 /* !! */  = (f)var27_8 /* !! */ ;
                                                    var27_8 /* !! */ .E1(this.Q);
                                                    var27_8 /* !! */ .D1(this.S);
                                                } else {
                                                    var1_1.h(var1_1.q(var27_8 /* !! */ .S), (i)var26_4, 0, 5);
                                                }
                                            }
                                        }
                                        if (this.q) {
                                            var1_1.f(var31_5, this.i0);
                                            var1_1.f(var33_6, this.i0 + this.e0);
                                            if (this.U.m()) {
                                                var1_1.f((i)var34_7, this.i0 + this.n0);
                                            }
                                            if (var13_10 && (var27_8 /* !! */  = this.c0) != null) {
                                                if (this.k) {
                                                    var27_8 /* !! */  = (f)var27_8 /* !! */ ;
                                                    var27_8 /* !! */ .J1(this.R);
                                                    var27_8 /* !! */ .I1(this.T);
                                                } else {
                                                    var1_1.h(var1_1.q(var27_8 /* !! */ .T), var33_6, 0, 5);
                                                }
                                            }
                                        }
                                        if (this.p && this.q) {
                                            this.p = false;
                                            this.q = false;
                                            return;
                                        }
                                    }
                                    var15_12 = r.d.s;
                                    if (var2_2 && (var27_8 /* !! */  = this.e) != null && (var29_13 = this.f) != null) {
                                        var28_14 /* !! */  = var27_8 /* !! */ .h;
                                        if (var28_14 /* !! */ .j && var27_8 /* !! */ .i.j && var29_13.h.j && var29_13.i.j) {
                                            var1_1.f(var32_3, var28_14 /* !! */ .g);
                                            var1_1.f((i)var26_4, this.e.i.g);
                                            var1_1.f(var31_5, this.f.h.g);
                                            var1_1.f(var33_6, this.f.i.g);
                                            var1_1.f((i)var34_7, this.f.k.g);
                                            if (this.c0 != null) {
                                                if (var14_9 && this.g[0] && !this.k0()) {
                                                    var1_1.h(var1_1.q(this.c0.S), (i)var26_4, 0, 8);
                                                }
                                                if (var13_10 && this.g[1] && !this.m0()) {
                                                    var1_1.h(var1_1.q(this.c0.T), var33_6, 0, 8);
                                                }
                                            }
                                            this.p = false;
                                            this.q = false;
                                            return;
                                        }
                                    }
                                    if (this.c0 != null) {
                                        if (this.h0(0)) {
                                            ((f)this.c0).A1(this, 0);
                                            var15_12 = true;
                                        } else {
                                            var15_12 = this.k0();
                                        }
                                        if (this.h0(1)) {
                                            ((f)this.c0).A1(this, 1);
                                            var16_15 = true;
                                        } else {
                                            var16_15 = this.m0();
                                        }
                                        if (!var15_12 && var14_9 && this.u0 != 8 && this.Q.f == null && this.S.f == null) {
                                            var1_1.h(var1_1.q(this.c0.S), (i)var26_4, 0, 1);
                                        }
                                        if (!var16_15 && var13_10 && this.u0 != 8 && this.R.f == null && this.T.f == null && this.U == null) {
                                            var1_1.h(var1_1.q(this.c0.T), var33_6, 0, 1);
                                        }
                                        var17_16 = var16_15;
                                        var16_15 = var15_12;
                                    } else {
                                        var16_15 = false;
                                        var17_16 = false;
                                    }
                                    var10_17 = this.d0;
                                    var8_18 = this.o0;
                                    if (var10_17 >= var8_18) {
                                        var8_18 = var10_17;
                                    }
                                    var9_19 = this.e0;
                                    var4_11 = this.p0;
                                    if (var9_19 >= var4_11) {
                                        var4_11 = var9_19;
                                    }
                                    var28_14 /* !! */  = this.b0;
                                    var27_8 /* !! */  = var28_14 /* !! */ [0];
                                    var35_20 = u.e$b.e;
                                    var15_12 = var27_8 /* !! */  != var35_20;
                                    var28_14 /* !! */  = var28_14 /* !! */ [1];
                                    var18_21 = var28_14 /* !! */  != var35_20;
                                    this.H = var11_22 = this.g0;
                                    this.I = var3_23 = this.f0;
                                    var6_24 = this.w;
                                    var7_25 = this.x;
                                    if (!(var3_23 > 0.0f)) break block61;
                                    var12_26 = this.u0;
                                    var5_27 = var4_11;
                                    if (var12_26 == 8) break block61;
                                    if (var27_8 /* !! */  == var35_20 && var6_24 == 0) {
                                        var6_24 = 3;
                                    }
                                    var4_11 = var28_14 /* !! */  == var35_20 && var7_25 == 0 ? 3 : var7_25;
                                    if (var27_8 /* !! */  != var35_20 || var28_14 /* !! */  != var35_20 || var6_24 != 3 || var4_11 != 3) break block62;
                                    this.t1(var14_9, var13_10, var15_12, var18_21);
                                    var9_19 = var5_27;
                                    break block63;
                                }
                                if (var27_8 /* !! */  != var35_20 || var6_24 != 3) break block64;
                                this.H = 0;
                                var8_18 = (int)((float)var9_19 * var3_23);
                                if (var28_14 /* !! */  != var35_20) {
                                    var6_24 = 4;
                                    var7_25 = var4_11;
                                    var4_11 = var8_18;
lbl129:
                                    // 3 sources

                                    while (true) {
                                        var15_12 = false;
lbl131:
                                        // 3 sources

                                        while (true) {
                                            continue;
                                            break;
                                        }
                                        break;
                                    }
                                }
                                var15_12 = true;
                                var7_25 = var4_11;
                                var4_11 = var8_18;
                                ** GOTO lbl131
                            }
                            var9_19 = var5_27;
                            if (var28_14 /* !! */  != var35_20) break block63;
                            var9_19 = var5_27;
                            if (var4_11 != 3) break block63;
                            this.H = 1;
                            if (var11_22 == -1) {
                                this.I = 1.0f / var3_23;
                            }
                            var9_19 = var5_27 = (int)(this.I * (float)var10_17);
                            if (var27_8 /* !! */  == var35_20) break block63;
                            var7_25 = 4;
                            var4_11 = var8_18;
                            ** GOTO lbl129
                        }
                        var15_12 = true;
                        var7_25 = var4_11;
                        var4_11 = var8_18;
                        var5_27 = var9_19;
                        ** while (true)
                    }
                    var5_27 = var4_11;
                    var4_11 = var8_18;
                    ** while (true)
                    var27_8 /* !! */  = var26_4;
                    var28_14 /* !! */  = (b[])this.y;
                    var28_14 /* !! */ [0] = (b)var6_24;
                    var28_14 /* !! */ [1] = (b)var7_25;
                    this.h = var15_12;
                    var19_28 = var15_12 && ((var8_18 = this.H) == 0 || var8_18 == -1);
                    var18_21 = var15_12 != false && ((var8_18 = this.H) == 1 || var8_18 == -1);
                    var28_14 /* !! */  = this.b0[0];
                    var36_29 = u.e$b.d;
                    var20_30 = var28_14 /* !! */  == var36_29 && this instanceof f != false;
                    if (var20_30) {
                        var4_11 = 0;
                    }
                    var22_31 = this.X.o() ^ true;
                    var28_14 /* !! */  = (b[])this.a0;
                    var24_32 = var28_14 /* !! */ [0];
                    var23_33 = var28_14 /* !! */ [1];
                    var8_18 = this.t;
                    var30_34 = null;
                    if (var8_18 == 2 || this.p) ** GOTO lbl187
                    if (!var2_2 || (var29_13 = this.e) == null) ** GOTO lbl-1000
                    var28_14 /* !! */  = var29_13.h;
                    if (var28_14 /* !! */ .j && var29_13.i.j) {
                        if (var2_2) {
                            var1_1.f(var32_3, var28_14 /* !! */ .g);
                            var1_1.f((i)var27_8 /* !! */ , this.e.i.g);
                            if (this.c0 != null && var14_9 && this.g[0] && !this.k0()) {
                                var1_1.h(var1_1.q(this.c0.S), (i)var27_8 /* !! */ , 0, 8);
                            }
                        }
lbl187:
                        // 6 sources

                        var27_8 /* !! */  = var26_4;
                    } else lbl-1000:
                    // 2 sources

                    {
                        var28_14 /* !! */  = (var28_14 /* !! */  = this.c0) != null ? var1_1.q(var28_14 /* !! */ .S) : null;
                        var29_13 = this.c0;
                        var29_13 = var29_13 != null ? var1_1.q(var29_13.Q) : null;
                        var25_35 = this.g[0];
                        var37_36 = this.b0;
                        var27_8 /* !! */  = var37_36[0];
                        var39_37 = this.Q;
                        var38_38 = this.S;
                        var9_19 = this.h0;
                        var8_18 = this.o0;
                        var10_17 = this.J[0];
                        var3_23 = this.q0;
                        var21_39 = var37_36[1] == var35_20;
                        this.i(var1_1, true, var14_9, var13_10, var25_35, (i)var29_13, (i)var28_14 /* !! */ , (b)var27_8 /* !! */ , var20_30, var39_37, var38_38, var9_19, var4_11, var8_18, var10_17, var3_23, var19_28, var21_39, var16_15, var17_16, (boolean)var24_32, var6_24, var7_25, this.z, this.A, this.B, var22_31);
                    }
                    var28_14 /* !! */  = var26_4;
                    if (!var2_2 || (var27_8 /* !! */  = this.f) == null) ** GOTO lbl-1000
                    var26_4 = var27_8 /* !! */ .h;
                    if (var26_4.j && var27_8 /* !! */ .i.j) {
                        var1_1.f(var31_5, var26_4.g);
                        var4_11 = this.f.i.g;
                        var1_1.f(var33_6, var4_11);
                        var1_1.f((i)var34_7, this.f.k.g);
                        var26_4 = this.c0;
                        if (var26_4 != null && !var17_16 && var13_10 && this.g[1]) {
                            var1_1.h(var1_1.q(var26_4.T), var33_6, 0, 8);
                        }
                        var4_11 = 0;
                    } else lbl-1000:
                    // 2 sources

                    {
                        var4_11 = 1;
                    }
                    if (this.u == 2) {
                        var4_11 = 0;
                    }
                    if (var4_11 == 0 || this.q) break block65;
                    var2_2 = this.b0[1] == var36_29 && this instanceof f != false;
                    var4_11 = var2_2 != false ? 0 : var5_27;
                    var26_4 = this.c0;
                    var26_4 = var26_4 != null ? var1_1.q(var26_4.T) : null;
                    var29_13 = this.c0;
                    var27_8 /* !! */  = var30_34;
                    if (var29_13 != null) {
                        var27_8 /* !! */  = var1_1.q(var29_13.R);
                    }
                    if (this.n0 > 0) break block66;
                    var19_28 = var22_31;
                    if (this.u0 != 8) break block67;
                }
                var29_13 = this.U;
                if (var29_13.f != null) {
                    var1_1.e((i)var34_7, var31_5, this.r(), 8);
                    var1_1.e((i)var34_7, var1_1.q(this.U.f), this.U.f(), 8);
                    if (var13_10) {
                        var1_1.h((i)var26_4, var1_1.q(this.T), 0, 5);
                    }
                    var19_28 = false;
                } else if (this.u0 == 8) {
                    var1_1.e((i)var34_7, var31_5, var29_13.f(), 8);
                    var19_28 = var22_31;
                } else {
                    var1_1.e((i)var34_7, var31_5, this.r(), 8);
                    var19_28 = var22_31;
                }
            }
            var21_39 = this.g[1];
            var30_34 = this.b0;
            var36_29 = var30_34[1];
            var34_7 = this.R;
            var29_13 = this.T;
            var8_18 = this.i0;
            var5_27 = this.p0;
            var9_19 = this.J[1];
            var3_23 = this.r0;
            var20_30 = var30_34[0] == var35_20;
            this.i(var1_1, false, var13_10, var14_9, var21_39, (i)var27_8 /* !! */ , (i)var26_4, var36_29, var2_2, (d)var34_7, (d)var29_13, var8_18, var4_11, var5_27, var9_19, var3_23, var18_21, var20_30, var17_16, var16_15, (boolean)var23_33, var7_25, var6_24, this.C, this.D, this.E, var19_28);
        }
        if (var15_12) {
            if (this.H == 1) {
                var1_1.k(var33_6, var31_5, (i)var28_14 /* !! */ , var32_3, this.I, 8);
            } else {
                var1_1.k((i)var28_14 /* !! */ , var32_3, var33_6, var31_5, this.I, 8);
            }
        }
        if (this.X.o()) {
            var1_1.b(this, this.X.j().h(), (float)Math.toRadians(this.K + 90.0f), this.X.f());
        }
        this.p = false;
        this.q = false;
    }

    public void g0(d.a a4, e e3, d.a a5, int n3, int n4) {
        this.q(a4).b(e3.q(a5), n3, n4, true);
    }

    public void g1(int n3, int n4) {
        this.h0 = n3;
        this.i0 = n4;
    }

    public boolean h() {
        return this.u0 != 8;
    }

    public final boolean h0(int n3) {
        Object object = this.Y;
        d d3 = object[n3 *= 2];
        d d4 = d3.f;
        if (d4 != null && d4.f != d3) {
            object = object[n3 + 1];
            d4 = object.f;
            if (d4 != null && d4.f == object) {
                return true;
            }
        }
        return false;
    }

    public void h1(e e3) {
        this.c0 = e3;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void i(r.d object, boolean bl, boolean bl2, boolean bl3, boolean bl4, i object2, i i3, b object3, boolean bl5, d object4, d bArray, int n3, int n4, int n5, int n6, float f3, boolean bl6, boolean bl7, boolean bl8, boolean bl9, boolean bl10, int n7, int n8, int n9, int n10, float f4, boolean bl11) {
        void var7_20;
        i i4;
        int n11;
        int n12;
        int n13;
        b[] bArray2;
        i i5;
        b b3;
        block84: {
            e e3;
            i i6;
            i i7;
            block86: {
                block83: {
                    block82: {
                        i i8;
                        int n14;
                        void var14_35;
                        int n15;
                        int n16;
                        i i9;
                        block104: {
                            i i10;
                            block107: {
                                block108: {
                                    void var20_41;
                                    e e4;
                                    e e5;
                                    e e6;
                                    int n17;
                                    void var17_38;
                                    block105: {
                                        int n18;
                                        block106: {
                                            void var21_42;
                                            void var16_37;
                                            int n19;
                                            int n20;
                                            int n21;
                                            int n22;
                                            block103: {
                                                block88: {
                                                    block90: {
                                                        block94: {
                                                            block95: {
                                                                block100: {
                                                                    block101: {
                                                                        block98: {
                                                                            boolean bl12;
                                                                            block102: {
                                                                                block99: {
                                                                                    block97: {
                                                                                        block96: {
                                                                                            block93: {
                                                                                                block92: {
                                                                                                    block91: {
                                                                                                        block89: {
                                                                                                            boolean bl13;
                                                                                                            int n23;
                                                                                                            block87: {
                                                                                                                block85: {
                                                                                                                    void var19_40;
                                                                                                                    void var27_48;
                                                                                                                    void var8_21;
                                                                                                                    i5 = ((r.d)object).q((Object)b3);
                                                                                                                    i7 = ((r.d)object).q(bArray2);
                                                                                                                    i9 = ((r.d)object).q(((d)((Object)b3)).j());
                                                                                                                    i6 = ((r.d)object).q(bArray2.j());
                                                                                                                    r.d.x();
                                                                                                                    n23 = ((d)((Object)b3)).o();
                                                                                                                    bl13 = bArray2.o();
                                                                                                                    boolean bl14 = this.X.o();
                                                                                                                    n16 = bl13 ? n23 + 1 : n23;
                                                                                                                    n13 = n16;
                                                                                                                    if (bl14) {
                                                                                                                        n13 = n16 + 1;
                                                                                                                    }
                                                                                                                    n15 = var17_38 != false ? 3 : n22;
                                                                                                                    n22 = var8_21.ordinal();
                                                                                                                    n21 = n22 != 0 && n22 != 1 && n22 == 2 && n15 != 4 ? 1 : 0;
                                                                                                                    int n24 = this.l;
                                                                                                                    n22 = n21;
                                                                                                                    n16 = n12;
                                                                                                                    if (n24 != -1) {
                                                                                                                        n22 = n21;
                                                                                                                        n16 = n12;
                                                                                                                        if (bl) {
                                                                                                                            this.l = -1;
                                                                                                                            n16 = n24;
                                                                                                                            n22 = 0;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    if ((n12 = this.m) != -1 && !bl) {
                                                                                                                        this.m = -1;
                                                                                                                        n22 = 0;
                                                                                                                    } else {
                                                                                                                        n12 = n16;
                                                                                                                    }
                                                                                                                    if (this.u0 == 8) {
                                                                                                                        n12 = 0;
                                                                                                                        n22 = 0;
                                                                                                                    }
                                                                                                                    if (var27_48 != false) {
                                                                                                                        if (n23 == 0 && !bl13 && !bl14) {
                                                                                                                            ((r.d)object).f(i5, n11);
                                                                                                                        } else if (n23 != 0 && !bl13) {
                                                                                                                            ((r.d)object).e(i5, i9, ((d)((Object)b3)).f(), 8);
                                                                                                                        }
                                                                                                                    }
                                                                                                                    if ((n11 = n22) == 0) {
                                                                                                                        if (bl12) {
                                                                                                                            ((r.d)object).e(i7, i5, 0, 3);
                                                                                                                            if (var14_35 > 0) {
                                                                                                                                ((r.d)object).h(i7, i5, (int)var14_35, 8);
                                                                                                                            }
                                                                                                                            if (n17 < Integer.MAX_VALUE) {
                                                                                                                                ((r.d)object).j(i7, i5, n17, 8);
                                                                                                                            }
                                                                                                                        } else {
                                                                                                                            ((r.d)object).e(i7, i5, n12, 8);
                                                                                                                        }
                                                                                                                        n16 = n11;
                                                                                                                        n11 = n14;
                                                                                                                        n14 = n20;
                                                                                                                    } else if (n13 != 2 && var17_38 == false && (n15 == 1 || n15 == 0)) {
                                                                                                                        n11 = n12 = Math.max(n20, n12);
                                                                                                                        if (n14 > 0) {
                                                                                                                            n11 = Math.min(n14, n12);
                                                                                                                        }
                                                                                                                        ((r.d)object).e(i7, i5, n11, 8);
                                                                                                                        n11 = n14;
                                                                                                                        n16 = 0;
                                                                                                                        n14 = n20;
                                                                                                                    } else {
                                                                                                                        n17 = n20 == -2 ? n12 : n20;
                                                                                                                        n22 = n14 == -2 ? n12 : n14;
                                                                                                                        n20 = n12;
                                                                                                                        if (n12 > 0) {
                                                                                                                            n20 = n12;
                                                                                                                            if (n15 != 1) {
                                                                                                                                n20 = 0;
                                                                                                                            }
                                                                                                                        }
                                                                                                                        n12 = n20;
                                                                                                                        if (n17 > 0) {
                                                                                                                            ((r.d)object).h(i7, i5, n17, 8);
                                                                                                                            n12 = Math.max(n20, n17);
                                                                                                                        }
                                                                                                                        n20 = n12;
                                                                                                                        if (n22 > 0) {
                                                                                                                            if (!bl2 || n15 != 1) {
                                                                                                                                ((r.d)object).j(i7, i5, n22, 8);
                                                                                                                            }
                                                                                                                            n20 = Math.min(n12, n22);
                                                                                                                        }
                                                                                                                        if (n15 == 1) {
                                                                                                                            if (bl2) {
                                                                                                                                ((r.d)object).e(i7, i5, n20, 8);
                                                                                                                            } else if (var19_40 != false) {
                                                                                                                                ((r.d)object).e(i7, i5, n20, 5);
                                                                                                                                ((r.d)object).j(i7, i5, n20, 8);
                                                                                                                            } else {
                                                                                                                                ((r.d)object).e(i7, i5, n20, 5);
                                                                                                                                ((r.d)object).j(i7, i5, n20, 8);
                                                                                                                            }
                                                                                                                            n16 = n11;
                                                                                                                            n11 = n22;
                                                                                                                            n14 = n17;
                                                                                                                        } else {
                                                                                                                            if (n15 == 2) {
                                                                                                                                void var26_47;
                                                                                                                                void var38_64;
                                                                                                                                void var8_24;
                                                                                                                                d.a a4;
                                                                                                                                d.a a5 = ((d)((Object)b3)).k();
                                                                                                                                if (a5 != (a4 = d.a.e) && ((d)((Object)b3)).k() != d.a.g) {
                                                                                                                                    i i11 = ((r.d)object).q(this.c0.q(d.a.d));
                                                                                                                                    i i12 = ((r.d)object).q(this.c0.q(d.a.f));
                                                                                                                                } else {
                                                                                                                                    i i13 = ((r.d)object).q(this.c0.q(a4));
                                                                                                                                    i i14 = ((r.d)object).q(this.c0.q(d.a.g));
                                                                                                                                }
                                                                                                                                ((r.d)object).d(((r.d)object).r().k(i7, i5, (i)var8_24, (i)var38_64, (float)var26_47));
                                                                                                                                if (bl2) {
                                                                                                                                    n11 = 0;
                                                                                                                                }
                                                                                                                                n16 = n11;
                                                                                                                            } else {
                                                                                                                                bl4 = true;
                                                                                                                                n16 = n11;
                                                                                                                            }
                                                                                                                            n11 = n22;
                                                                                                                            n14 = n17;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    i4 = i7;
                                                                                                                    if (var27_48 == false || var19_40 != false) break block84;
                                                                                                                    if (n23 == 0 && !bl13 && !bl14) break block82;
                                                                                                                    if (n23 == 0 || bl13) break block85;
                                                                                                                    e e7 = ((d)((Object)b3)).f.d;
                                                                                                                    n11 = bl2 && e7 instanceof u.a ? 8 : 5;
                                                                                                                    i i15 = i6;
                                                                                                                    break block86;
                                                                                                                }
                                                                                                                if (n23 != 0 || !bl13) break block87;
                                                                                                                ((r.d)object).e(i4, i6, -bArray2.f(), 8);
                                                                                                                if (bl2) {
                                                                                                                    e e8;
                                                                                                                    if (this.j && i5.i && (e8 = this.c0) != null) {
                                                                                                                        f f5 = (f)e8;
                                                                                                                        if (bl) {
                                                                                                                            f5.E1((d)((Object)b3));
                                                                                                                            break block82;
                                                                                                                        } else {
                                                                                                                            f5.J1((d)((Object)b3));
                                                                                                                        }
                                                                                                                        break block82;
                                                                                                                    } else {
                                                                                                                        ((r.d)object).h(i5, (i)object2, 0, 5);
                                                                                                                    }
                                                                                                                }
                                                                                                                break block82;
                                                                                                            }
                                                                                                            if (n23 == 0 || !bl13) break block82;
                                                                                                            e6 = ((d)((Object)b3)).f.d;
                                                                                                            e5 = bArray2.f.d;
                                                                                                            e4 = this.M();
                                                                                                            n18 = 6;
                                                                                                            if (n16 == 0) break block88;
                                                                                                            if (n15 != 0) break block89;
                                                                                                            if (n11 == 0 && n14 == 0) {
                                                                                                                if (i9.i && i6.i) {
                                                                                                                    ((r.d)object).e(i5, i9, ((d)((Object)b3)).f(), 8);
                                                                                                                    ((r.d)object).e(i4, i6, -bArray2.f(), 8);
                                                                                                                    return;
                                                                                                                }
                                                                                                                n11 = 8;
                                                                                                                n12 = 8;
                                                                                                                n17 = 0;
                                                                                                                n19 = 1;
                                                                                                                n22 = 0;
                                                                                                            } else {
                                                                                                                n11 = 5;
                                                                                                                n12 = 5;
                                                                                                                n17 = 1;
                                                                                                                n19 = 0;
                                                                                                                n22 = 1;
                                                                                                            }
                                                                                                            if (e6 instanceof u.a || e5 instanceof u.a) {
                                                                                                                n12 = 4;
                                                                                                            }
                                                                                                            n21 = 6;
                                                                                                            n20 = n19;
                                                                                                            n19 = n17;
                                                                                                            n17 = n11;
                                                                                                            n11 = n21;
                                                                                                            break block90;
                                                                                                        }
                                                                                                        if (n15 != 2) break block91;
                                                                                                        n12 = !(e6 instanceof u.a) && !(e5 instanceof u.a) ? 5 : 4;
                                                                                                        n17 = 5;
                                                                                                        break block92;
                                                                                                    }
                                                                                                    if (n15 != 1) break block93;
                                                                                                    n12 = 4;
                                                                                                    n17 = 8;
                                                                                                }
                                                                                                n22 = 1;
                                                                                                n19 = 1;
                                                                                                break block94;
                                                                                            }
                                                                                            if (n15 != 3) break block95;
                                                                                            if (this.H != -1) break block96;
                                                                                            n11 = var20_41 != false ? (bl2 ? 5 : 4) : 8;
                                                                                            n12 = 5;
                                                                                            n17 = 8;
                                                                                            n22 = 1;
                                                                                            n19 = 1;
                                                                                            n20 = 1;
                                                                                            break block90;
                                                                                        }
                                                                                        if (var17_38 == false) break block97;
                                                                                        if (n19 != 2 && n19 != 1) {
                                                                                            n12 = 8;
                                                                                            n11 = 5;
                                                                                        } else {
                                                                                            n12 = 5;
                                                                                            n11 = 4;
                                                                                        }
                                                                                        bl12 = true;
                                                                                        n17 = n12;
                                                                                        n12 = n11;
                                                                                        break block98;
                                                                                    }
                                                                                    if (n11 <= 0) break block99;
                                                                                    n12 = 5;
                                                                                    break block100;
                                                                                }
                                                                                if (n11 != 0 || n14 != 0) break block101;
                                                                                if (var20_41 != false) break block102;
                                                                                n12 = 8;
                                                                                break block100;
                                                                            }
                                                                            n11 = e6 != e4 && e5 != e4 ? 4 : 5;
                                                                            bl12 = true;
                                                                            n12 = 4;
                                                                            n17 = n11;
                                                                        }
                                                                        n20 = 1;
                                                                        n19 = 1;
                                                                        n22 = 1;
                                                                        n11 = 6;
                                                                        break block90;
                                                                    }
                                                                    n12 = 4;
                                                                }
                                                                n20 = 1;
                                                                n19 = 1;
                                                                n22 = 1;
                                                                n11 = 6;
                                                                n17 = 5;
                                                                break block90;
                                                            }
                                                            n12 = 4;
                                                            n17 = 5;
                                                            n22 = 0;
                                                            n19 = 0;
                                                        }
                                                        n11 = 6;
                                                        n20 = 0;
                                                    }
                                                    n21 = n11;
                                                    n13 = n19;
                                                    break block103;
                                                }
                                                if (i9.i && i6.i) {
                                                    ((r.d)object).c(i5, i9, ((d)((Object)b3)).f(), (float)var16_37, i6, i4, bArray2.f(), 8);
                                                    if (!bl2) return;
                                                    if (!bl4) return;
                                                    n11 = bArray2.f != null ? bArray2.f() : 0;
                                                    if (i6 == var7_20) return;
                                                    ((r.d)object).h((i)var7_20, i4, n11, 5);
                                                    return;
                                                }
                                                n22 = 1;
                                                n13 = 1;
                                                n21 = 6;
                                                n17 = 5;
                                                n12 = 4;
                                                n20 = 0;
                                            }
                                            if (n22 != 0 && i9 == i6 && e6 != e4) {
                                                n19 = 0;
                                                n11 = 0;
                                            } else {
                                                n11 = 1;
                                                n19 = n22;
                                            }
                                            if (n13 != 0) {
                                                void var18_39;
                                                if (n16 == 0 && var18_39 == false && var20_41 == false && i9 == object2 && i6 == var7_20) {
                                                    n22 = 8;
                                                    bl2 = false;
                                                    n17 = 8;
                                                    n11 = 0;
                                                } else {
                                                    n22 = n21;
                                                }
                                                ((r.d)object).c(i5, i9, ((d)((Object)b3)).f(), (float)var16_37, i6, i4, bArray2.f(), n22);
                                            }
                                            i8 = i7;
                                            n21 = 1;
                                            i10 = i6;
                                            if (this.u0 == 8 && !bArray2.m()) {
                                                return;
                                            }
                                            n22 = n17;
                                            if (n19 != 0) {
                                                if (bl2 && i9 != i10 && n16 == 0 && (e6 instanceof u.a || e5 instanceof u.a)) {
                                                    n17 = 6;
                                                }
                                                ((r.d)object).h(i5, i9, ((d)((Object)b3)).f(), n17);
                                                ((r.d)object).j(i8, i10, -bArray2.f(), n17);
                                                n22 = n17;
                                            }
                                            if (bl2 && var21_42 != false && !(e6 instanceof u.a) && !(e5 instanceof u.a) && e5 != e4) {
                                                n11 = 6;
                                                n17 = 6;
                                                n22 = n21;
                                            } else {
                                                n17 = n22;
                                                n22 = n11;
                                                n11 = n12;
                                            }
                                            if (n22 == 0) break block104;
                                            n12 = n11;
                                            if (n20 == 0) break block105;
                                            if (var20_41 == false) break block106;
                                            n12 = n11;
                                            if (!bl3) break block105;
                                        }
                                        n12 = n18;
                                        if (e6 != e4) {
                                            n12 = e5 == e4 ? n18 : n11;
                                        }
                                        if (e6 instanceof h || e5 instanceof h) {
                                            n12 = 5;
                                        }
                                        if (e6 instanceof u.a || e5 instanceof u.a) {
                                            n12 = 5;
                                        }
                                        if (var20_41 != false) {
                                            n12 = 5;
                                        }
                                        n12 = Math.max(n12, n11);
                                    }
                                    n11 = n12;
                                    if (!bl2) break block107;
                                    n11 = n12 = Math.min(n17, n12);
                                    if (var17_38 == false) break block107;
                                    n11 = n12;
                                    if (var20_41 != false) break block107;
                                    if (e6 == e4) break block108;
                                    n11 = n12;
                                    if (e5 != e4) break block107;
                                }
                                n11 = 4;
                            }
                            ((r.d)object).e(i5, i9, ((d)((Object)b3)).f(), n11);
                            ((r.d)object).e(i8, i10, -bArray2.f(), n11);
                        }
                        if (bl2) {
                            n11 = object2 == i9 ? ((d)((Object)b3)).f() : 0;
                            if (i9 != object2) {
                                ((r.d)object).h(i5, (i)object2, n11, 5);
                            }
                        }
                        if (bl2 && n16 != 0 && var14_35 == false && n14 == 0) {
                            if (n16 != 0 && n15 == 3) {
                                ((r.d)object).h(i8, i5, 0, 8);
                                break block83;
                            } else {
                                ((r.d)object).h(i8, i5, 0, 5);
                            }
                        }
                        break block83;
                    }
                    i i16 = i6;
                }
                i i18 = i7;
                i18 = i6;
                n11 = 5;
            }
            if (!bl2) return;
            if (!bl4) return;
            n12 = bArray2.f != null ? bArray2.f() : 0;
            if (i6 == var7_20) return;
            if (this.j && i7.i && (e3 = this.c0) != null) {
                object = (f)e3;
                if (bl) {
                    ((f)object).D1((d)bArray2);
                    return;
                }
                ((f)object).I1((d)bArray2);
                return;
            }
            ((r.d)object).h((i)var7_20, i7, n12, n11);
            return;
        }
        if (n13 >= 2) return;
        if (!bl2) return;
        if (!bl4) return;
        ((r.d)object).h(i5, (i)object2, 0, 8);
        n12 = !bl && this.U.f != null ? 0 : 1;
        n11 = n12;
        if (!bl) {
            d d3 = this.U.f;
            n11 = n12;
            if (d3 != null) {
                b b4;
                e e9 = d3.d;
                n11 = e9.f0 != 0.0f && (b3 = (bArray2 = e9.b0)[0]) == (b4 = u.e$b.e) && bArray2[1] == b4 ? 1 : 0;
            }
        }
        if (n11 == 0) return;
        ((r.d)object).h((i)var7_20, i4, 0, 8);
    }

    public boolean i0() {
        return this.r;
    }

    public void i1(float f3) {
        this.r0 = f3;
    }

    public void j(d.a a4, e e3, d.a a5) {
        this.k(a4, e3, a5, 0);
    }

    public boolean j0(int n3) {
        return this.a0[n3];
    }

    public void j1(int n3) {
        this.K0 = n3;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void k(d.a object, e object2, d.a object3, int n3) {
        Object object4;
        d d3;
        int n4;
        void var3_17;
        d.a a4 = d.a.i;
        if (object == a4) {
            if (var3_17 == a4) {
                d.a a5 = d.a.d;
                d d4 = this.q(a5);
                d.a a6 = d.a.f;
                d d5 = this.q(a6);
                d.a a7 = d.a.e;
                d d6 = this.q(a7);
                d.a a8 = d.a.g;
                d d7 = this.q(a8);
                boolean bl = true;
                if (d4 != null && d4.o() || d5 != null && d5.o()) {
                    n4 = 0;
                } else {
                    this.k(a5, (e)((Object)d3), a5, 0);
                    this.k(a6, (e)((Object)d3), a6, 0);
                    n4 = 1;
                }
                if (d6 != null && d6.o() || d7 != null && d7.o()) {
                    bl = false;
                } else {
                    this.k(a7, (e)((Object)d3), a7, 0);
                    this.k(a8, (e)((Object)d3), a8, 0);
                }
                if (n4 != 0 && bl) {
                    this.q(a4).a(((e)((Object)d3)).q(a4), 0);
                    return;
                }
                if (n4 != 0) {
                    d.a a9 = d.a.j;
                    this.q(a9).a(((e)((Object)d3)).q(a9), 0);
                    return;
                }
                if (!bl) return;
                d.a a10 = d.a.k;
                this.q(a10).a(((e)((Object)d3)).q(a10), 0);
                return;
            }
            d.a a11 = d.a.d;
            if (var3_17 != a11 && var3_17 != d.a.f) {
                d.a a12 = d.a.e;
                if (var3_17 != a12 && var3_17 != d.a.g) return;
                this.k(a12, (e)((Object)d3), (d.a)var3_17, 0);
                this.k(d.a.g, (e)((Object)d3), (d.a)var3_17, 0);
                this.q(a4).a(((e)((Object)d3)).q((d.a)var3_17), 0);
                return;
            }
            this.k(a11, (e)((Object)d3), (d.a)var3_17, 0);
            this.k(d.a.f, (e)((Object)d3), (d.a)var3_17, 0);
            this.q(a4).a(((e)((Object)d3)).q((d.a)var3_17), 0);
            return;
        }
        d.a a13 = d.a.j;
        if (object == a13 && (var3_17 == (object4 = d.a.d) || var3_17 == d.a.f)) {
            d d8 = this.q((d.a)((Object)object4));
            d3 = ((e)((Object)d3)).q((d.a)var3_17);
            d d9 = this.q(d.a.f);
            d8.a(d3, 0);
            d9.a(d3, 0);
            this.q(a13).a(d3, 0);
            return;
        }
        d.a a14 = d.a.k;
        if (object == a14 && (var3_17 == (object4 = d.a.e) || var3_17 == d.a.g)) {
            d d10 = ((e)((Object)d3)).q((d.a)var3_17);
            this.q((d.a)((Object)object4)).a(d10, 0);
            this.q(d.a.g).a(d10, 0);
            this.q(a14).a(d10, 0);
            return;
        }
        if (object == a13 && var3_17 == a13) {
            d.a a15 = d.a.d;
            this.q(a15).a(((e)((Object)d3)).q(a15), 0);
            d.a a16 = d.a.f;
            this.q(a16).a(((e)((Object)d3)).q(a16), 0);
            this.q(a13).a(((e)((Object)d3)).q((d.a)var3_17), 0);
            return;
        }
        if (object == a14 && var3_17 == a14) {
            d.a a17 = d.a.e;
            this.q(a17).a(((e)((Object)d3)).q(a17), 0);
            d.a a18 = d.a.g;
            this.q(a18).a(((e)((Object)d3)).q(a18), 0);
            this.q(a14).a(((e)((Object)d3)).q((d.a)var3_17), 0);
            return;
        }
        object4 = this.q((d.a)((Object)object));
        if (!((d)object4).p(d3 = ((e)((Object)d3)).q((d.a)var3_17))) return;
        d.a a19 = d.a.h;
        if (object == a19) {
            d d11 = this.q(d.a.e);
            d d12 = this.q(d.a.g);
            if (d11 != null) {
                d11.q();
            }
            if (d12 != null) {
                d12.q();
            }
        } else if (object != d.a.e && object != d.a.g) {
            if (object == d.a.d || object == d.a.f) {
                d d13 = this.q(a4);
                if (d13.j() != d3) {
                    d13.q();
                }
                d d14 = this.q((d.a)((Object)object)).g();
                d d15 = this.q(a13);
                if (d15.o()) {
                    d14.q();
                    d15.q();
                }
            }
        } else {
            d d16;
            d d17 = this.q(a19);
            if (d17 != null) {
                d17.q();
            }
            if ((d16 = this.q(a4)).j() != d3) {
                d16.q();
            }
            d d18 = this.q((d.a)((Object)object)).g();
            d d19 = this.q(a14);
            if (d19.o()) {
                d18.q();
                d19.q();
            }
        }
        ((d)object4).a(d3, n4);
    }

    public boolean k0() {
        block3: {
            block2: {
                d d3 = this.Q;
                d d4 = d3.f;
                if (d4 != null && d4.f == d3) break block2;
                d4 = this.S;
                d3 = d4.f;
                if (d3 == null || d3.f != d4) break block3;
            }
            return true;
        }
        return false;
    }

    public void k1(int n3, int n4) {
        this.i0 = n3;
        this.e0 = n4 -= n3;
        n3 = this.p0;
        if (n4 < n3) {
            this.e0 = n3;
        }
    }

    public void l(d d3, d d4, int n3) {
        if (d3.h() == this) {
            this.k(d3.k(), d4.h(), d4.k(), n3);
        }
    }

    public boolean l0() {
        return this.M;
    }

    public void l1(b b3) {
        this.b0[1] = b3;
    }

    public void m(e e3, float f3, int n3) {
        d.a a4 = d.a.i;
        this.g0(a4, e3, a4, n3, 0);
        this.K = f3;
    }

    public boolean m0() {
        block3: {
            block2: {
                d d3 = this.R;
                d d4 = d3.f;
                if (d4 != null && d4.f == d3) break block2;
                d4 = this.T;
                d3 = d4.f;
                if (d3 == null || d3.f != d4) break block3;
            }
            return true;
        }
        return false;
    }

    public void m1(int n3, int n4, int n5, float f3) {
        this.x = n3;
        this.C = n4;
        n4 = n5;
        if (n5 == Integer.MAX_VALUE) {
            n4 = 0;
        }
        this.D = n4;
        this.E = f3;
        if (f3 > 0.0f && f3 < 1.0f && n3 == 0) {
            this.x = 2;
        }
    }

    public void n(e object, HashMap hashMap) {
        this.t = ((e)object).t;
        this.u = ((e)object).u;
        this.w = ((e)object).w;
        this.x = ((e)object).x;
        Object object2 = this.y;
        int[] nArray = ((e)object).y;
        object2[0] = nArray[0];
        object2[1] = nArray[1];
        this.z = ((e)object).z;
        this.A = ((e)object).A;
        this.C = ((e)object).C;
        this.D = ((e)object).D;
        this.E = ((e)object).E;
        this.F = ((e)object).F;
        this.G = ((e)object).G;
        this.H = ((e)object).H;
        this.I = ((e)object).I;
        object2 = ((e)object).J;
        this.J = Arrays.copyOf(object2, ((int[])object2).length);
        this.K = ((e)object).K;
        this.L = ((e)object).L;
        this.M = ((e)object).M;
        this.Q.q();
        this.R.q();
        this.S.q();
        this.T.q();
        this.U.q();
        this.V.q();
        this.W.q();
        this.X.q();
        this.b0 = Arrays.copyOf(this.b0, 2);
        object2 = this.c0;
        nArray = null;
        object2 = object2 == null ? null : (Object)((e)hashMap.get(((e)object).c0));
        this.c0 = object2;
        this.d0 = ((e)object).d0;
        this.e0 = ((e)object).e0;
        this.f0 = ((e)object).f0;
        this.g0 = ((e)object).g0;
        this.h0 = ((e)object).h0;
        this.i0 = ((e)object).i0;
        this.j0 = ((e)object).j0;
        this.k0 = ((e)object).k0;
        this.l0 = ((e)object).l0;
        this.m0 = ((e)object).m0;
        this.n0 = ((e)object).n0;
        this.o0 = ((e)object).o0;
        this.p0 = ((e)object).p0;
        this.q0 = ((e)object).q0;
        this.r0 = ((e)object).r0;
        this.s0 = ((e)object).s0;
        this.t0 = ((e)object).t0;
        this.u0 = ((e)object).u0;
        this.v0 = ((e)object).v0;
        this.w0 = ((e)object).w0;
        this.x0 = ((e)object).x0;
        this.y0 = ((e)object).y0;
        this.z0 = ((e)object).z0;
        this.A0 = ((e)object).A0;
        this.B0 = ((e)object).B0;
        this.C0 = ((e)object).C0;
        this.D0 = ((e)object).D0;
        this.E0 = ((e)object).E0;
        this.F0 = ((e)object).F0;
        this.G0 = ((e)object).G0;
        this.H0 = ((e)object).H0;
        this.J0 = ((e)object).J0;
        this.K0 = ((e)object).K0;
        this.L0 = ((e)object).L0;
        this.M0 = ((e)object).M0;
        object2 = this.N0;
        Object[] objectArray = ((e)object).N0;
        object2[0] = (int)objectArray[0];
        object2[1] = (int)objectArray[1];
        objectArray = this.O0;
        object2 = ((e)object).O0;
        objectArray[0] = object2[0];
        objectArray[1] = object2[1];
        object2 = this.P0;
        objectArray = ((e)object).P0;
        object2[0] = (int)objectArray[0];
        object2[1] = (int)objectArray[1];
        object2 = ((e)object).Q0;
        object2 = object2 == null ? null : (Object)((e)hashMap.get(object2));
        this.Q0 = object2;
        object = ((e)object).R0;
        object = object == null ? (Object)nArray : (e)hashMap.get(object);
        this.R0 = object;
    }

    public boolean n0() {
        return this.N;
    }

    public void n1(float f3) {
        this.N0[1] = f3;
    }

    public void o(r.d d3) {
        d3.q(this.Q);
        d3.q(this.R);
        d3.q(this.S);
        d3.q(this.T);
        if (this.n0 > 0) {
            d3.q(this.U);
        }
    }

    public boolean o0() {
        return this.i && this.u0 != 8;
    }

    public void o1(int n3) {
        this.u0 = n3;
    }

    public void p() {
        if (this.e == null) {
            this.e = new l(this);
        }
        if (this.f == null) {
            this.f = new n(this);
        }
    }

    public boolean p0() {
        return this.p || this.Q.n() && this.S.n();
        {
        }
    }

    public void p1(int n3) {
        this.d0 = n3;
        int n4 = this.o0;
        if (n3 < n4) {
            this.d0 = n4;
        }
    }

    public d q(d.a a4) {
        switch (u.e$a.a[a4.ordinal()]) {
            default: {
                throw new AssertionError((Object)a4.name());
            }
            case 9: {
                return null;
            }
            case 8: {
                return this.W;
            }
            case 7: {
                return this.V;
            }
            case 6: {
                return this.X;
            }
            case 5: {
                return this.U;
            }
            case 4: {
                return this.T;
            }
            case 3: {
                return this.S;
            }
            case 2: {
                return this.R;
            }
            case 1: 
        }
        return this.Q;
    }

    public boolean q0() {
        return this.q || this.R.n() && this.T.n();
        {
        }
    }

    public void q1(int n3) {
        if (n3 >= 0 && n3 <= 3) {
            this.v = n3;
        }
    }

    public int r() {
        return this.n0;
    }

    public boolean r0() {
        return this.s;
    }

    public void r1(int n3) {
        this.h0 = n3;
    }

    public float s(int n3) {
        if (n3 == 0) {
            return this.q0;
        }
        if (n3 == 1) {
            return this.r0;
        }
        return -1.0f;
    }

    public void s0() {
        this.r = true;
    }

    public void s1(int n3) {
        this.i0 = n3;
    }

    public int t() {
        return this.a0() + this.e0;
    }

    public void t0() {
        this.s = true;
    }

    public void t1(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        if (this.H == -1) {
            if (bl3 && !bl4) {
                this.H = 0;
            } else if (!bl3 && bl4) {
                this.H = 1;
                if (this.g0 == -1) {
                    this.I = 1.0f / this.I;
                }
            }
        }
        if (!(this.H != 0 || this.R.o() && this.T.o())) {
            this.H = 1;
        } else if (!(this.H != 1 || this.Q.o() && this.S.o())) {
            this.H = 0;
        }
        if (!(this.H != -1 || this.R.o() && this.T.o() && this.Q.o() && this.S.o())) {
            if (this.R.o() && this.T.o()) {
                this.H = 0;
            } else if (this.Q.o() && this.S.o()) {
                this.I = 1.0f / this.I;
                this.H = 1;
            }
        }
        if (this.H == -1) {
            int n3 = this.z;
            if (n3 > 0 && this.C == 0) {
                this.H = 0;
                return;
            }
            if (n3 == 0 && this.C > 0) {
                this.I = 1.0f / this.I;
                this.H = 1;
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CharSequence charSequence = this.x0;
        String string = "";
        if (charSequence != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("type: ");
            ((StringBuilder)charSequence).append(this.x0);
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        charSequence = string;
        if (this.w0 != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("id: ");
            ((StringBuilder)charSequence).append(this.w0);
            ((StringBuilder)charSequence).append(" ");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append("(");
        stringBuilder.append(this.h0);
        stringBuilder.append(", ");
        stringBuilder.append(this.i0);
        stringBuilder.append(") - (");
        stringBuilder.append(this.d0);
        stringBuilder.append(" x ");
        stringBuilder.append(this.e0);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public Object u() {
        return this.s0;
    }

    public boolean u0() {
        b[] bArray = this.b0;
        b b3 = bArray[0];
        b b4 = u.e$b.e;
        return b3 == b4 && bArray[1] == b4;
    }

    public void u1(boolean bl, boolean bl2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        boolean bl3;
        boolean bl4;
        block15: {
            block14: {
                bl4 = bl & this.e.k();
                bl3 = bl2 & this.f.k();
                l l3 = this.e;
                n7 = l3.h.g;
                n n8 = this.f;
                n6 = n8.h.g;
                n5 = l3.i.g;
                n4 = n8.i.g;
                if (n5 - n7 < 0 || n4 - n6 < 0 || n7 == Integer.MIN_VALUE || n7 == Integer.MAX_VALUE || n6 == Integer.MIN_VALUE || n6 == Integer.MAX_VALUE || n5 == Integer.MIN_VALUE || n5 == Integer.MAX_VALUE || n4 == Integer.MIN_VALUE) break block14;
                n3 = n4;
                if (n4 != Integer.MAX_VALUE) break block15;
            }
            n5 = 0;
            n6 = n3 = (n7 = 0);
        }
        n4 = n5 - n7;
        n5 = n3 - n6;
        if (bl4) {
            this.h0 = n7;
        }
        if (bl3) {
            this.i0 = n6;
        }
        if (this.u0 == 8) {
            this.d0 = 0;
            this.e0 = 0;
            return;
        }
        if (bl4) {
            n3 = n4;
            if (this.b0[0] == u.e$b.c) {
                n6 = this.d0;
                n3 = n4;
                if (n4 < n6) {
                    n3 = n6;
                }
            }
            this.d0 = n3;
            n6 = this.o0;
            if (n3 < n6) {
                this.d0 = n6;
            }
        }
        if (bl3) {
            n3 = n5;
            if (this.b0[1] == u.e$b.c) {
                n6 = this.e0;
                n3 = n5;
                if (n5 < n6) {
                    n3 = n6;
                }
            }
            this.e0 = n3;
            n6 = this.p0;
            if (n3 < n6) {
                this.e0 = n6;
            }
        }
    }

    public String v() {
        return this.w0;
    }

    public void v0() {
        b b3;
        float f3;
        this.Q.q();
        this.R.q();
        this.S.q();
        this.T.q();
        this.U.q();
        this.V.q();
        this.W.q();
        this.X.q();
        this.c0 = null;
        this.K = Float.NaN;
        this.d0 = 0;
        this.e0 = 0;
        this.f0 = 0.0f;
        this.g0 = -1;
        this.h0 = 0;
        this.i0 = 0;
        this.l0 = 0;
        this.m0 = 0;
        this.n0 = 0;
        this.o0 = 0;
        this.p0 = 0;
        this.q0 = f3 = U0;
        this.r0 = f3;
        Object[] objectArray = this.b0;
        objectArray[0] = b3 = u.e$b.c;
        objectArray[1] = b3;
        this.s0 = null;
        this.t0 = 0;
        this.u0 = 0;
        this.x0 = null;
        this.G0 = false;
        this.H0 = false;
        this.J0 = 0;
        this.K0 = 0;
        this.L0 = false;
        this.M0 = false;
        objectArray = this.N0;
        objectArray[0] = (b)-1.0f;
        objectArray[1] = (b)-1.0f;
        this.t = -1;
        this.u = -1;
        objectArray = this.J;
        objectArray[0] = (b)Integer.MAX_VALUE;
        objectArray[1] = (b)Integer.MAX_VALUE;
        this.w = 0;
        this.x = 0;
        this.B = 1.0f;
        this.E = 1.0f;
        this.A = Integer.MAX_VALUE;
        this.D = Integer.MAX_VALUE;
        this.z = 0;
        this.C = 0;
        this.h = false;
        this.H = -1;
        this.I = 1.0f;
        this.I0 = false;
        objectArray = this.g;
        objectArray[0] = (b)true;
        objectArray[1] = (b)true;
        this.N = false;
        objectArray = this.a0;
        objectArray[0] = (b)false;
        objectArray[1] = (b)false;
        this.i = true;
        objectArray = this.y;
        objectArray[0] = (b)false;
        objectArray[1] = (b)false;
        this.l = -1;
        this.m = -1;
    }

    public void v1(r.d object, boolean bl) {
        int n3;
        int n4;
        int n5;
        int n6;
        block12: {
            block11: {
                v.d d3;
                int n7 = ((r.d)object).y(this.Q);
                n6 = ((r.d)object).y(this.R);
                n5 = ((r.d)object).y(this.S);
                int n8 = ((r.d)object).y(this.T);
                n4 = n7;
                n3 = n5;
                if (bl) {
                    d3 = this.e;
                    n4 = n7;
                    n3 = n5;
                    if (d3 != null) {
                        object = d3.h;
                        n4 = n7;
                        n3 = n5;
                        if (((v.f)object).j) {
                            d3 = d3.i;
                            n4 = n7;
                            n3 = n5;
                            if (((v.f)d3).j) {
                                n4 = ((v.f)object).g;
                                n3 = ((v.f)d3).g;
                            }
                        }
                    }
                }
                n5 = n6;
                n7 = n8;
                if (bl) {
                    d3 = this.f;
                    n5 = n6;
                    n7 = n8;
                    if (d3 != null) {
                        object = d3.h;
                        n5 = n6;
                        n7 = n8;
                        if (((v.f)object).j) {
                            d3 = d3.i;
                            n5 = n6;
                            n7 = n8;
                            if (((v.f)d3).j) {
                                n5 = ((v.f)object).g;
                                n7 = ((v.f)d3).g;
                            }
                        }
                    }
                }
                if (n3 - n4 < 0 || n7 - n5 < 0 || n4 == Integer.MIN_VALUE || n4 == Integer.MAX_VALUE || n5 == Integer.MIN_VALUE || n5 == Integer.MAX_VALUE || n3 == Integer.MIN_VALUE || n3 == Integer.MAX_VALUE || n7 == Integer.MIN_VALUE) break block11;
                n6 = n3;
                n3 = n7;
                if (n7 != Integer.MAX_VALUE) break block12;
            }
            n4 = 0;
            n3 = 0;
            n6 = n5 = 0;
        }
        this.O0(n4, n5, n6, n3);
    }

    public b w(int n3) {
        if (n3 == 0) {
            return this.C();
        }
        if (n3 == 1) {
            return this.V();
        }
        return null;
    }

    public void w0() {
        this.x0();
        this.i1(U0);
        this.R0(U0);
    }

    public float x() {
        return this.f0;
    }

    public void x0() {
        e e3 = this.M();
        if (e3 == null || !(e3 instanceof f) || !((f)this.M()).R1()) {
            int n3 = this.Z.size();
            for (int i3 = 0; i3 < n3; ++i3) {
                ((d)this.Z.get(i3)).q();
            }
        }
    }

    public int y() {
        return this.g0;
    }

    public void y0() {
        this.p = false;
        this.q = false;
        this.r = false;
        this.s = false;
        int n3 = this.Z.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((d)this.Z.get(i3)).r();
        }
    }

    public int z() {
        if (this.u0 == 8) {
            return 0;
        }
        return this.e0;
    }

    public void z0(c c3) {
        this.Q.s(c3);
        this.R.s(c3);
        this.S.s(c3);
        this.T.s(c3);
        this.U.s(c3);
        this.X.s(c3);
        this.V.s(c3);
        this.W.s(c3);
    }

    public static final class b
    extends Enum {
        public static final /* enum */ b c = new b("FIXED", 0);
        public static final /* enum */ b d = new b("WRAP_CONTENT", 1);
        public static final /* enum */ b e = new b("MATCH_CONSTRAINT", 2);
        public static final /* enum */ b f = new b("MATCH_PARENT", 3);
        public static final b[] g = u.e$b.a();

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        public b() {
            void cfr_renamed_1;
            void cfr_renamed_2;
        }

        public static /* synthetic */ b[] a() {
            return new b[]{c, d, e, f};
        }

        public static b valueOf(String string) {
            return Enum.valueOf(b.class, string);
        }

        public static b[] values() {
            return (b[])g.clone();
        }
    }
}

