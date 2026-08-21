/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.d;
import c1.e;
import c1.g;
import c1.p;
import c1.q;
import c1.y;
import e3.k;
import e3.l;
import e3.t;
import java.util.List;

public final class u {
    public static final a e = new a(null);
    public final List a;
    public final float b;
    public final float c;
    public final List d;

    /*
     * Unable to fully structure code
     */
    public u(List var1_1, float var2_2, float var3_3) {
        block20: {
            o3.k.e(var1_1, "features");
            super();
            this.a = var1_1;
            this.b = var2_2;
            this.c = var3_3;
            var16_4 = k.c();
            var4_5 = var1_1.size();
            var6_6 = 0;
            var11_7 = null;
            var12_8 = null;
            if (var4_5 > 0 && ((g)var1_1.get(0)).a().size() == 3) {
                var10_9 = ((d)((g)var1_1.get(0)).a().get(1)).m(0.5f);
                var9_10 = (d)var10_9.a();
                var10_9 = (d)var10_9.b();
                var9_10 = l.i(new d[]{((g)var1_1.get(0)).a().get(0), var9_10});
                var10_9 = l.i(new d[]{var10_9, ((g)var1_1.get(0)).a().get(2)});
            } else {
                var10_9 = null;
                var9_10 = null;
            }
            var7_11 = var1_1.size();
            if (var7_11 >= 0) {
                var4_5 = 0;
                var11_7 = null;
                while (true) {
                    if (var4_5 == 0 && var10_9 != null) {
                        var1_1 = var10_9;
                    } else if (var4_5 == this.a.size()) {
                        if (var9_10 == null) {
                            var1_1 = var11_7;
                            var13_14 = var12_8;
                            while (true) {
                                var9_10 = var1_1;
                                var1_1 = var13_14;
                                break block20;
                                break;
                            }
                        }
                        var1_1 = var9_10;
                    } else {
                        var1_1 = ((g)this.a.get(var4_5)).a();
                    }
                    var8_13 = var1_1.size();
                    for (var5_12 = 0; var5_12 < var8_13; ++var5_12) {
                        var15_16 = (d)var1_1.get(var5_12);
                        if (!var15_16.p()) {
                            if (var11_7 != null) {
                                var16_4.add(var11_7);
                            }
                            if (var12_8 == null) {
                                var13_14 = var11_7 = var15_16;
                                var14_15 = var11_7;
                            } else {
                                var13_14 = var15_16;
                                var14_15 = var12_8;
                            }
                        } else {
                            var14_15 = var12_8;
                            var13_14 = var11_7;
                            if (var11_7 != null) {
                                var11_7.j()[6] = var15_16.d();
                                var11_7.j()[7] = var15_16.e();
                                var13_14 = var11_7;
                                var14_15 = var12_8;
                            }
                        }
                        var12_8 = var14_15;
                        var11_7 = var13_14;
                    }
                    var13_14 = var12_8;
                    var1_1 = var11_7;
                    if (var4_5 == var7_11) ** continue;
                    ++var4_5;
                }
            }
            var1_1 = null;
            var9_10 = var11_7;
        }
        if (var9_10 != null && var1_1 != null) {
            var16_4.add(c1.e.a(var9_10.b(), var9_10.c(), var9_10.f(), var9_10.g(), var9_10.h(), var9_10.i(), var1_1.b(), var1_1.c()));
        }
        var9_10 = k.a(var16_4);
        this.d = var9_10;
        var1_1 = var9_10.get(var9_10.size() - 1);
        var5_12 = var9_10.size();
        for (var4_5 = var6_6; var4_5 < var5_12; ++var4_5) {
            var9_10 = (d)this.d.get(var4_5);
            var2_2 = var9_10.b();
            if (!(Math.abs(var2_2 - (var1_1 = (d)var1_1).d()) > 1.0E-4f) && !(Math.abs(var9_10.c() - var1_1.e()) > 1.0E-4f)) {
                var1_1 = var9_10;
                continue;
            }
            throw new IllegalArgumentException("RoundedPolygon must be contiguous, with the anchor points of all curves matching the anchor points of the preceding and succeeding cubics");
        }
    }

    public static /* synthetic */ float[] c(u u3, float[] fArray, boolean bl, int n3, Object object) {
        if ((n3 & 1) != 0) {
            fArray = new float[4];
        }
        if ((n3 & 2) != 0) {
            bl = true;
        }
        return u3.b(fArray, bl);
    }

    public final float[] a(float[] fArray) {
        o3.k.e(fArray, "bounds");
        return u.c(this, fArray, false, 2, null);
    }

    public final float[] b(float[] fArray, boolean bl) {
        o3.k.e(fArray, "bounds");
        if (fArray.length >= 4) {
            int n3 = this.d.size();
            float f3 = Float.MIN_VALUE;
            float f4 = Float.MAX_VALUE;
            float f5 = Float.MAX_VALUE;
            float f6 = Float.MIN_VALUE;
            for (int i3 = 0; i3 < n3; ++i3) {
                ((d)this.d.get(i3)).a(fArray, bl);
                f4 = Math.min(f4, fArray[0]);
                f5 = Math.min(f5, fArray[1]);
                f3 = Math.max(f3, fArray[2]);
                f6 = Math.max(f6, fArray[3]);
            }
            fArray[0] = f4;
            fArray[1] = f5;
            fArray[2] = f3;
            fArray[3] = f6;
            return fArray;
        }
        throw new IllegalArgumentException("Required bounds size of 4");
    }

    public final float[] d(float[] fArray) {
        o3.k.e(fArray, "bounds");
        if (fArray.length >= 4) {
            float f3;
            int n3 = this.d.size();
            float f4 = 0.0f;
            for (int i3 = 0; i3 < n3; ++i3) {
                d d3 = (d)this.d.get(i3);
                f3 = y.e(d3.b() - this.b, d3.c() - this.c);
                long l3 = d3.k(0.5f);
                f4 = Math.max(f4, Math.max(f3, y.e(p.g(l3) - this.b, p.h(l3) - this.c)));
            }
            f4 = (float)Math.sqrt(f4);
            f3 = this.b;
            fArray[0] = f3 - f4;
            float f5 = this.c;
            fArray[1] = f5 - f4;
            fArray[2] = f3 + f4;
            fArray[3] = f5 + f4;
            return fArray;
        }
        throw new IllegalArgumentException("Required bounds size of 4");
    }

    public final float e() {
        return this.b;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof u)) {
            return false;
        }
        return o3.k.a(this.a, ((u)object).a);
    }

    public final float f() {
        return this.c;
    }

    public final List g() {
        return this.a;
    }

    public final u h(q q3) {
        o3.k.e(q3, "f");
        long l3 = p.m(o.e.b(this.b, this.c), q3);
        List list = k.c();
        int n3 = this.a.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            list.add(((g)this.a.get(i3)).b(q3));
        }
        return new u(k.a(list), p.g(l3), p.h(l3));
    }

    public int hashCode() {
        return ((Object)this.a).hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RoundedPolygon. Cubics = ");
        stringBuilder.append(t.v(this.d, null, null, null, 0, null, null, 63, null));
        stringBuilder.append(" || Features = ");
        stringBuilder.append(t.v(this.a, null, null, null, 0, null, null, 63, null));
        stringBuilder.append(" || Center = (");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append(this.c);
        stringBuilder.append(")]");
        return stringBuilder.toString();
    }

    public static final class a {
        public a() {
        }

        public /* synthetic */ a(o3.g g3) {
            this();
        }
    }
}

