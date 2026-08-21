/*
 * Decompiled with CFR 0.152.
 */
package r;

import java.util.ArrayList;
import r.c;
import r.d;
import r.i;

public class b
implements d.a {
    public i a = null;
    public float b = 0.0f;
    public boolean c = false;
    public ArrayList d = new ArrayList();
    public a e;
    public boolean f = false;

    public b() {
    }

    public b(c c3) {
        this.e = new r.a(this, c3);
    }

    public void A(d d3, i i3, boolean bl) {
        if (i3 != null && i3.i) {
            float f3 = this.e.c(i3);
            this.b += i3.h * f3;
            this.e.g(i3, bl);
            if (bl) {
                i3.d(this);
            }
            if (r.d.u && this.e.f() == 0) {
                this.f = true;
                d3.b = true;
            }
        }
    }

    public void B(d d3, b b3, boolean bl) {
        float f3 = this.e.e(b3, bl);
        this.b += b3.b * f3;
        if (bl) {
            b3.a.d(this);
        }
        if (r.d.u && this.a != null && this.e.f() == 0) {
            this.f = true;
            d3.b = true;
        }
    }

    public void C(d d3, i i3, boolean bl) {
        if (i3 != null && i3.p) {
            float f3 = this.e.c(i3);
            this.b += i3.r * f3;
            this.e.g(i3, bl);
            if (bl) {
                i3.d(this);
            }
            this.e.i(d3.o.d[i3.q], f3, bl);
            if (r.d.u && this.e.f() == 0) {
                this.f = true;
                d3.b = true;
            }
        }
    }

    public void D(d d3) {
        if (d3.h.length != 0) {
            boolean bl = false;
            while (!bl) {
                i i3;
                int n3;
                int n4 = this.e.f();
                for (n3 = 0; n3 < n4; ++n3) {
                    i3 = this.e.h(n3);
                    if (i3.f == -1 && !i3.i && !i3.p) continue;
                    this.d.add(i3);
                }
                n4 = this.d.size();
                if (n4 > 0) {
                    for (n3 = 0; n3 < n4; ++n3) {
                        i3 = (i)this.d.get(n3);
                        if (i3.i) {
                            this.A(d3, i3, true);
                            continue;
                        }
                        if (i3.p) {
                            this.C(d3, i3, true);
                            continue;
                        }
                        this.B(d3, d3.h[i3.f], true);
                    }
                    this.d.clear();
                    continue;
                }
                bl = true;
            }
            if (r.d.u && this.a != null && this.e.f() == 0) {
                this.f = true;
                d3.b = true;
            }
        }
    }

    @Override
    public void a(d.a object) {
        if (object instanceof b) {
            b b3 = (b)object;
            this.a = null;
            this.e.clear();
            for (int i3 = 0; i3 < b3.e.f(); ++i3) {
                object = b3.e.h(i3);
                float f3 = b3.e.a(i3);
                this.e.i((i)object, f3, true);
            }
        }
    }

    @Override
    public void b(i i3) {
        int n3 = i3.g;
        float f3 = 1.0f;
        if (n3 != 1) {
            if (n3 == 2) {
                f3 = 1000.0f;
            } else if (n3 == 3) {
                f3 = 1000000.0f;
            } else if (n3 == 4) {
                f3 = 1.0E9f;
            } else if (n3 == 5) {
                f3 = 1.0E12f;
            }
        }
        this.e.d(i3, f3);
    }

    @Override
    public i c(d d3, boolean[] blArray) {
        return this.w(blArray, null);
    }

    @Override
    public void clear() {
        this.e.clear();
        this.a = null;
        this.b = 0.0f;
    }

    public b d(d d3, int n3) {
        this.e.d(d3.o(n3, "ep"), 1.0f);
        this.e.d(d3.o(n3, "em"), -1.0f);
        return this;
    }

    public b e(i i3, int n3) {
        this.e.d(i3, n3);
        return this;
    }

    public boolean f(d object) {
        boolean bl;
        if ((object = this.g((d)object)) == null) {
            bl = true;
        } else {
            this.x((i)object);
            bl = false;
        }
        if (this.e.f() == 0) {
            this.f = true;
        }
        return bl;
    }

    /*
     * Unable to fully structure code
     */
    public i g(d var1_1) {
        var8_2 = this.e.f();
        var17_3 = null;
        var6_4 = 0.0f;
        var5_5 = 0.0f;
        var11_8 = var12_7 = false;
        var16_9 = null;
        for (var7_6 = 0; var7_6 < var8_2; ++var7_6) {
            var2_10 = this.e.a(var7_6);
            var13_15 = this.e.h(var7_6);
            if (var13_15.l == i.a.c) {
                if (var17_3 == null) {
                    var9_13 = this.u(var13_15, var1_1);
lbl13:
                    // 2 sources

                    while (true) {
                        var14_16 = var13_15;
                        var15_17 = var16_9;
                        var10_14 = var11_8;
                        var3_11 = var2_10;
                        var4_12 = var5_5;
                        break;
                    }
                } else {
                    if (var6_4 > var2_10) {
                        var9_13 = this.u(var13_15, var1_1);
                        ** continue;
                    }
                    var14_16 = var17_3;
                    var15_17 = var16_9;
                    var9_13 = var12_7;
                    var10_14 = var11_8;
                    var3_11 = var6_4;
                    var4_12 = var5_5;
                    if (!var12_7) {
                        var14_16 = var17_3;
                        var15_17 = var16_9;
                        var9_13 = var12_7;
                        var10_14 = var11_8;
                        var3_11 = var6_4;
                        var4_12 = var5_5;
                        if (this.u(var13_15, var1_1)) {
                            var9_13 = true;
                            var14_16 = var13_15;
                            var15_17 = var16_9;
                            var10_14 = var11_8;
                            var3_11 = var2_10;
                            var4_12 = var5_5;
                        }
                    }
                }
            } else {
                var14_16 = var17_3;
                var15_17 = var16_9;
                var9_13 = var12_7;
                var10_14 = var11_8;
                var3_11 = var6_4;
                var4_12 = var5_5;
                if (var17_3 == null) {
                    var14_16 = var17_3;
                    var15_17 = var16_9;
                    var9_13 = var12_7;
                    var10_14 = var11_8;
                    var3_11 = var6_4;
                    var4_12 = var5_5;
                    if (var2_10 < 0.0f) {
                        if (var16_9 == null) {
                            var9_13 = this.u(var13_15, var1_1);
lbl60:
                            // 2 sources

                            while (true) {
                                var10_14 = var9_13;
                                var14_16 = var17_3;
                                var15_17 = var13_15;
                                var9_13 = var12_7;
                                var3_11 = var6_4;
                                var4_12 = var2_10;
                                break;
                            }
                        } else {
                            if (var5_5 > var2_10) {
                                var9_13 = this.u(var13_15, var1_1);
                                ** continue;
                            }
                            var14_16 = var17_3;
                            var15_17 = var16_9;
                            var9_13 = var12_7;
                            var10_14 = var11_8;
                            var3_11 = var6_4;
                            var4_12 = var5_5;
                            if (!var11_8) {
                                var14_16 = var17_3;
                                var15_17 = var16_9;
                                var9_13 = var12_7;
                                var10_14 = var11_8;
                                var3_11 = var6_4;
                                var4_12 = var5_5;
                                if (this.u(var13_15, var1_1)) {
                                    var10_14 = true;
                                    var4_12 = var2_10;
                                    var3_11 = var6_4;
                                    var9_13 = var12_7;
                                    var15_17 = var13_15;
                                    var14_16 = var17_3;
                                }
                            }
                        }
                    }
                }
            }
            var17_3 = var14_16;
            var16_9 = var15_17;
            var12_7 = var9_13;
            var11_8 = var10_14;
            var6_4 = var3_11;
            var5_5 = var4_12;
        }
        if (var17_3 != null) {
            return var17_3;
        }
        return var16_9;
    }

    @Override
    public i getKey() {
        return this.a;
    }

    public b h(i i3, i i4, int n3, float f3, i i5, i i6, int n4) {
        float f4;
        block10: {
            block9: {
                block8: {
                    if (i4 == i5) {
                        this.e.d(i3, 1.0f);
                        this.e.d(i6, 1.0f);
                        this.e.d(i4, -2.0f);
                        return this;
                    }
                    if (f3 != 0.5f) break block8;
                    this.e.d(i3, 1.0f);
                    this.e.d(i4, -1.0f);
                    this.e.d(i5, -1.0f);
                    this.e.d(i6, 1.0f);
                    if (n3 > 0 || n4 > 0) {
                        this.b = -n3 + n4;
                        return this;
                    }
                    break block9;
                }
                if (f3 <= 0.0f) {
                    this.e.d(i3, -1.0f);
                    this.e.d(i4, 1.0f);
                    this.b = n3;
                    return this;
                }
                if (f3 >= 1.0f) {
                    this.e.d(i6, -1.0f);
                    this.e.d(i5, 1.0f);
                    this.b = -n4;
                    return this;
                }
                a a4 = this.e;
                f4 = 1.0f - f3;
                a4.d(i3, f4 * 1.0f);
                this.e.d(i4, f4 * -1.0f);
                this.e.d(i5, -1.0f * f3);
                this.e.d(i6, 1.0f * f3);
                if (n3 > 0 || n4 > 0) break block10;
            }
            return this;
        }
        this.b = (float)(-n3) * f4 + (float)n4 * f3;
        return this;
    }

    public b i(i i3, int n3) {
        float f3;
        this.a = i3;
        i3.h = f3 = (float)n3;
        this.b = f3;
        this.f = true;
        return this;
    }

    @Override
    public boolean isEmpty() {
        return this.a == null && this.b == 0.0f && this.e.f() == 0;
    }

    public b j(i i3, i i4, float f3) {
        this.e.d(i3, -1.0f);
        this.e.d(i4, f3);
        return this;
    }

    public b k(i i3, i i4, i i5, i i6, float f3) {
        this.e.d(i3, -1.0f);
        this.e.d(i4, 1.0f);
        this.e.d(i5, f3);
        this.e.d(i6, -f3);
        return this;
    }

    public b l(float f3, float f4, float f5, i i3, i i4, i i5, i i6) {
        this.b = 0.0f;
        if (f4 != 0.0f && f3 != f5) {
            if (f3 == 0.0f) {
                this.e.d(i3, 1.0f);
                this.e.d(i4, -1.0f);
                return this;
            }
            if (f5 == 0.0f) {
                this.e.d(i5, 1.0f);
                this.e.d(i6, -1.0f);
                return this;
            }
            f3 = f3 / f4 / (f5 / f4);
            this.e.d(i3, 1.0f);
            this.e.d(i4, -1.0f);
            this.e.d(i6, f3);
            this.e.d(i5, -f3);
            return this;
        }
        this.e.d(i3, 1.0f);
        this.e.d(i4, -1.0f);
        this.e.d(i6, 1.0f);
        this.e.d(i5, -1.0f);
        return this;
    }

    public b m(i i3, int n3) {
        if (n3 < 0) {
            this.b = n3 * -1;
            this.e.d(i3, 1.0f);
            return this;
        }
        this.b = n3;
        this.e.d(i3, -1.0f);
        return this;
    }

    public b n(i i3, i i4, int n3) {
        int n4 = 0;
        int n5 = 0;
        if (n3 != 0) {
            n4 = n5;
            n5 = n3;
            if (n3 < 0) {
                n5 = n3 * -1;
                n4 = 1;
            }
            this.b = n5;
        }
        if (n4 == 0) {
            this.e.d(i3, -1.0f);
            this.e.d(i4, 1.0f);
            return this;
        }
        this.e.d(i3, 1.0f);
        this.e.d(i4, -1.0f);
        return this;
    }

    public b o(i i3, i i4, i i5, int n3) {
        int n4 = 0;
        int n5 = 0;
        if (n3 != 0) {
            n4 = n5;
            n5 = n3;
            if (n3 < 0) {
                n5 = n3 * -1;
                n4 = 1;
            }
            this.b = n5;
        }
        if (n4 == 0) {
            this.e.d(i3, -1.0f);
            this.e.d(i4, 1.0f);
            this.e.d(i5, 1.0f);
            return this;
        }
        this.e.d(i3, 1.0f);
        this.e.d(i4, -1.0f);
        this.e.d(i5, -1.0f);
        return this;
    }

    public b p(i i3, i i4, i i5, int n3) {
        int n4 = 0;
        int n5 = 0;
        if (n3 != 0) {
            n4 = n5;
            n5 = n3;
            if (n3 < 0) {
                n5 = n3 * -1;
                n4 = 1;
            }
            this.b = n5;
        }
        if (n4 == 0) {
            this.e.d(i3, -1.0f);
            this.e.d(i4, 1.0f);
            this.e.d(i5, -1.0f);
            return this;
        }
        this.e.d(i3, 1.0f);
        this.e.d(i4, -1.0f);
        this.e.d(i5, 1.0f);
        return this;
    }

    public b q(i i3, i i4, i i5, i i6, float f3) {
        this.e.d(i5, 0.5f);
        this.e.d(i6, 0.5f);
        this.e.d(i3, -0.5f);
        this.e.d(i4, -0.5f);
        this.b = -f3;
        return this;
    }

    public void r() {
        float f3 = this.b;
        if (f3 < 0.0f) {
            this.b = f3 * -1.0f;
            this.e.k();
        }
    }

    public boolean s() {
        i i3 = this.a;
        return !(i3 == null || i3.l != i.a.c && this.b < 0.0f);
    }

    public boolean t(i i3) {
        return this.e.b(i3);
    }

    public String toString() {
        return this.z();
    }

    public final boolean u(i i3, d d3) {
        return i3.o <= 1;
    }

    public i v(i i3) {
        return this.w(null, i3);
    }

    public final i w(boolean[] blArray, i i3) {
        int n3 = this.e.f();
        i i4 = null;
        float f3 = 0.0f;
        for (int i5 = 0; i5 < n3; ++i5) {
            float f4;
            i i6;
            block5: {
                i i7;
                float f5;
                block7: {
                    block6: {
                        f5 = this.e.a(i5);
                        i6 = i4;
                        f4 = f3;
                        if (!(f5 < 0.0f)) break block5;
                        i7 = this.e.h(i5);
                        if (blArray == null) break block6;
                        i6 = i4;
                        f4 = f3;
                        if (blArray[i7.e]) break block5;
                    }
                    i6 = i4;
                    f4 = f3;
                    if (i7 == i3) break block5;
                    i.a a4 = i7.l;
                    if (a4 == i.a.e) break block7;
                    i6 = i4;
                    f4 = f3;
                    if (a4 != i.a.f) break block5;
                }
                i6 = i4;
                f4 = f3;
                if (f5 < f3) {
                    f4 = f5;
                    i6 = i7;
                }
            }
            i4 = i6;
            f3 = f4;
        }
        return i4;
    }

    public void x(i i3) {
        i i4 = this.a;
        if (i4 != null) {
            this.e.d(i4, -1.0f);
            this.a.f = -1;
            this.a = null;
        }
        float f3 = this.e.g(i3, true) * -1.0f;
        this.a = i3;
        if (f3 == 1.0f) {
            return;
        }
        this.b /= f3;
        this.e.j(f3);
    }

    public void y() {
        this.a = null;
        this.e.clear();
        this.b = 0.0f;
        this.f = false;
    }

    /*
     * Unable to fully structure code
     */
    public String z() {
        if (this.a == null) {
            var7_1 = new StringBuilder();
            var7_1.append("");
            var7_1.append("0");
            var7_1 = var7_1.toString();
        } else {
            var7_1 = new StringBuilder();
            var7_1.append("");
            var7_1.append(this.a);
            var7_1 = var7_1.toString();
        }
        var8_2 = new StringBuilder();
        var8_2.append((String)var7_1);
        var8_2.append(" = ");
        var7_1 = var8_2.toString();
        var1_3 = this.b;
        var4_4 = 0;
        if (var1_3 != 0.0f) {
            var8_2 = new StringBuilder();
            var8_2.append((String)var7_1);
            var8_2.append(this.b);
            var7_1 = var8_2.toString();
            var3_5 = true;
        } else {
            var3_5 = false;
        }
        var5_6 = this.e.f();
        while (var4_4 < var5_6) {
            var8_2 = this.e.h(var4_4);
            if (var8_2 != null && (var6_8 = (cfr_temp_0 = (var2_7 = this.e.a(var4_4)) - 0.0f) == 0.0f ? 0 : (cfr_temp_0 > 0.0f ? 1 : -1)) != false) {
                var9_9 = var8_2.toString();
                if (!var3_5) {
                    var8_2 = var7_1;
                    var1_3 = var2_7;
                    if (var2_7 < 0.0f) {
                        var8_2 = new StringBuilder();
                        var8_2.append((String)var7_1);
                        var8_2.append("- ");
                        var8_2 = var8_2.toString();
lbl48:
                        // 2 sources

                        while (true) {
                            var1_3 = var2_7 * -1.0f;
                            break;
                        }
                    }
                } else if (var6_8 > 0) {
                    var8_2 = new StringBuilder();
                    var8_2.append((String)var7_1);
                    var8_2.append(" + ");
                    var8_2 = var8_2.toString();
                    var1_3 = var2_7;
                } else {
                    var8_2 = new StringBuilder();
                    var8_2.append((String)var7_1);
                    var8_2.append(" - ");
                    var8_2 = var8_2.toString();
                    ** continue;
                }
                if (var1_3 == 1.0f) {
                    var7_1 = new StringBuilder();
                    var7_1.append((String)var8_2);
                    var7_1.append(var9_9);
                    var7_1 = var7_1.toString();
                } else {
                    var7_1 = new StringBuilder();
                    var7_1.append((String)var8_2);
                    var7_1.append(var1_3);
                    var7_1.append(" ");
                    var7_1.append(var9_9);
                    var7_1 = var7_1.toString();
                }
                var3_5 = true;
            }
            ++var4_4;
        }
        var8_2 = var7_1;
        if (!var3_5) {
            var8_2 = new StringBuilder();
            var8_2.append((String)var7_1);
            var8_2.append("0.0");
            var8_2 = var8_2.toString();
        }
        return var8_2;
    }

    public static interface a {
        public float a(int var1);

        public boolean b(i var1);

        public float c(i var1);

        public void clear();

        public void d(i var1, float var2);

        public float e(b var1, boolean var2);

        public int f();

        public float g(i var1, boolean var2);

        public i h(int var1);

        public void i(i var1, float var2, boolean var3);

        public void j(float var1);

        public void k();
    }
}

