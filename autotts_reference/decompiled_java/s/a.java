/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.util.Arrays;
import s.b;

public class a
extends b {
    public final double[] a;
    public a[] b;
    public boolean c = true;

    /*
     * Unable to fully structure code
     */
    public a(int[] var1_1, double[] var2_2, double[][] var3_3) {
        super();
        this.a = var2_2;
        this.b = new a[var2_2.length - 1];
        var16_4 = 1;
        var13_5 = 1;
        var14_6 = 0;
        while (var14_6 < (var18_14 = this.b).length) {
            block4: {
                block5: {
                    block6: {
                        block7: {
                            var17_13 = var1_1[var14_6];
                            var12_11 = 3;
                            var15_12 = var16_4;
                            if (var17_13 == 0) break block4;
                            if (var17_13 == 1) break block5;
                            if (var17_13 == 2) break block6;
                            if (var17_13 == 3) break block7;
                            var12_11 = 4;
                            var15_12 = var16_4;
                            if (var17_13 != 4) {
                                var12_11 = 5;
                                var15_12 = var16_4;
                                if (var17_13 != 5) {
                                    var12_11 = var13_5;
                                    var15_12 = var16_4;
                                }
                            }
                            break block4;
                        }
                        if (var16_4 != 1) break block5;
                        break block6;
lbl28:
                        // 2 sources

                        while (true) {
                            var13_5 = var12_11;
                            var15_12 = var12_11;
                            var12_11 = var13_5;
                            break block4;
                            break;
                        }
                    }
                    var12_11 = 2;
                    ** GOTO lbl28
                }
                var12_11 = 1;
                ** continue;
            }
            var4_7 = var2_2[var14_6];
            var13_5 = var14_6 + 1;
            var8_9 = var2_2[var13_5];
            var19_15 = var3_3[var14_6];
            var10_10 = var19_15[0];
            var6_8 = var19_15[1];
            var19_15 = var3_3[var13_5];
            var18_14[var14_6] = new a(var12_11, var4_7, var8_9, var10_10, var6_8, var19_15[0], var19_15[1]);
            var14_6 = var13_5;
            var16_4 = var15_12;
            var13_5 = var12_11;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public double c(double d3, int n3) {
        double d4;
        int n4;
        Object object;
        boolean bl = this.c;
        int n5 = 0;
        if (bl) {
            a[] aArray = this.b;
            object = aArray[0];
            double d5 = ((a)object).c;
            if (d3 < d5) {
                double d6;
                double d7 = d3 - d5;
                if (((a)object).r) {
                    double d8;
                    if (n3 == 0) {
                        d8 = ((a)object).f(d5);
                        d3 = this.b[0].d(d5);
                        return d8 + d7 * d3;
                    }
                    d8 = ((a)object).g(d5);
                    d3 = this.b[0].e(d5);
                    return d8 + d7 * d3;
                }
                ((a)object).k(d5);
                if (n3 == 0) {
                    d6 = this.b[0].h();
                    d3 = this.b[0].b();
                    return d6 + d7 * d3;
                }
                d6 = this.b[0].i();
                d3 = this.b[0].c();
                return d6 + d7 * d3;
            }
            n4 = n5;
            d4 = d3;
            if (d3 > aArray[aArray.length - 1].d) {
                double d9;
                d5 = aArray[aArray.length - 1].d;
                n4 = aArray.length - 1;
                if (n3 == 0) {
                    d9 = aArray[n4].f(d5);
                    d4 = this.b[n4].d(d5);
                    return d9 + (d3 - d5) * d4;
                }
                d9 = aArray[n4].g(d5);
                d4 = this.b[n4].e(d5);
                return d9 + (d3 - d5) * d4;
            }
        } else {
            object = this.b;
            d4 = object[0].c;
            if (d3 < d4) {
                n4 = n5;
            } else {
                n4 = n5;
                d4 = d3;
                if (d3 > object[((a[])object).length - 1].d) {
                    d4 = ((a)object[((Object)object).length - 1]).d;
                    n4 = n5;
                }
            }
        }
        while (n4 < ((a[])(object = this.b)).length) {
            object = object[n4];
            if (d4 <= ((a)object).d) {
                if (((a)object).r) {
                    if (n3 != 0) return ((a)object).g(d4);
                    return ((a)object).f(d4);
                }
                ((a)object).k(d4);
                if (n3 != 0) return this.b[n4].i();
                return this.b[n4].h();
            }
            ++n4;
        }
        return Double.NaN;
    }

    @Override
    public void d(double d3, double[] dArray) {
        double d4;
        double d5;
        Object object;
        if (this.c) {
            a[] aArray = this.b;
            object = aArray[0];
            d5 = ((a)object).c;
            if (d3 < d5) {
                d3 -= d5;
                if (((a)object).r) {
                    dArray[0] = ((a)object).f(d5) + this.b[0].d(d5) * d3;
                    dArray[1] = this.b[0].g(d5) + d3 * this.b[0].e(d5);
                    return;
                }
                ((a)object).k(d5);
                dArray[0] = this.b[0].h() + this.b[0].b() * d3;
                dArray[1] = this.b[0].i() + d3 * this.b[0].c();
                return;
            }
            d4 = d3;
            if (d3 > aArray[aArray.length - 1].d) {
                d5 = aArray[aArray.length - 1].d;
                d4 = d3 - d5;
                int n3 = aArray.length - 1;
                object = aArray[n3];
                if (((a)object).r) {
                    dArray[0] = ((a)object).f(d5) + this.b[n3].d(d5) * d4;
                    dArray[1] = this.b[n3].g(d5) + d4 * this.b[n3].e(d5);
                    return;
                }
                ((a)object).k(d3);
                dArray[0] = this.b[n3].h() + this.b[n3].b() * d4;
                dArray[1] = this.b[n3].i() + d4 * this.b[n3].c();
                return;
            }
        } else {
            object = this.b;
            d4 = object[0].c;
            d5 = d3;
            if (d3 < d4) {
                d5 = d4;
            }
            d4 = d5;
            if (d5 > object[((a[])object).length - 1].d) {
                d4 = ((a)object[((Object)object).length - 1]).d;
            }
        }
        for (int i3 = 0; i3 < ((Object)(object = this.b)).length; ++i3) {
            object = object[i3];
            if (!(d4 <= ((a)object).d)) continue;
            if (((a)object).r) {
                dArray[0] = ((a)object).f(d4);
                dArray[1] = this.b[i3].g(d4);
                return;
            }
            ((a)object).k(d4);
            dArray[0] = this.b[i3].h();
            dArray[1] = this.b[i3].i();
            return;
        }
    }

    @Override
    public void e(double d3, float[] fArray) {
        double d4;
        Object object;
        if (this.c) {
            object = this.b;
            a a4 = object[0];
            d4 = a4.c;
            if (d3 < d4) {
                d3 -= d4;
                if (a4.r) {
                    fArray[0] = (float)(a4.f(d4) + this.b[0].d(d4) * d3);
                    fArray[1] = (float)(this.b[0].g(d4) + d3 * this.b[0].e(d4));
                    return;
                }
                a4.k(d4);
                fArray[0] = (float)(this.b[0].h() + this.b[0].b() * d3);
                fArray[1] = (float)(this.b[0].i() + d3 * this.b[0].c());
                return;
            }
            d4 = d3;
            if (d3 > object[((a[])object).length - 1].d) {
                d4 = object[((a[])object).length - 1].d;
                double d5 = d3 - d4;
                int n3 = ((Object)object).length - 1;
                object = object[n3];
                if (((a)object).r) {
                    fArray[0] = (float)(((a)object).f(d4) + this.b[n3].d(d4) * d5);
                    fArray[1] = (float)(this.b[n3].g(d4) + d5 * this.b[n3].e(d4));
                    return;
                }
                ((a)object).k(d3);
                fArray[0] = (float)this.b[n3].h();
                fArray[1] = (float)this.b[n3].i();
                return;
            }
        } else {
            object = this.b;
            d4 = object[0].c;
            if (!(d3 < d4)) {
                d4 = d3;
                if (d3 > object[((a[])object).length - 1].d) {
                    d4 = object[((a[])object).length - 1].d;
                }
            }
        }
        for (int i3 = 0; i3 < ((a[])(object = this.b)).length; ++i3) {
            object = object[i3];
            if (!(d4 <= ((a)object).d)) continue;
            if (((a)object).r) {
                fArray[0] = (float)((a)object).f(d4);
                fArray[1] = (float)this.b[i3].g(d4);
                return;
            }
            ((a)object).k(d4);
            fArray[0] = (float)this.b[i3].h();
            fArray[1] = (float)this.b[i3].i();
            return;
        }
    }

    @Override
    public double f(double d3, int n3) {
        Object object = this.b;
        int n4 = 0;
        double d4 = object[0].c;
        double d5 = d3;
        if (d3 < d4) {
            d5 = d4;
        }
        int n5 = n4;
        d3 = d5;
        if (d5 > object[((a[])object).length - 1].d) {
            d3 = object[((a[])object).length - 1].d;
            n5 = n4;
        }
        while (n5 < ((a[])(object = this.b)).length) {
            object = object[n5];
            if (d3 <= ((a)object).d) {
                if (((a)object).r) {
                    if (n3 == 0) {
                        return ((a)object).d(d3);
                    }
                    return ((a)object).e(d3);
                }
                ((a)object).k(d3);
                if (n3 == 0) {
                    return this.b[n5].b();
                }
                return this.b[n5].c();
            }
            ++n5;
        }
        return Double.NaN;
    }

    @Override
    public void g(double d3, double[] dArray) {
        Object object = this.b;
        double d4 = object[0].c;
        if (!(d3 < d4)) {
            d4 = d3;
            if (d3 > object[((a[])object).length - 1].d) {
                d4 = object[((a[])object).length - 1].d;
            }
        }
        for (int i3 = 0; i3 < ((a[])(object = this.b)).length; ++i3) {
            object = object[i3];
            if (!(d4 <= ((a)object).d)) continue;
            if (((a)object).r) {
                dArray[0] = ((a)object).d(d4);
                dArray[1] = this.b[i3].e(d4);
                return;
            }
            ((a)object).k(d4);
            dArray[0] = this.b[i3].b();
            dArray[1] = this.b[i3].c();
            return;
        }
    }

    @Override
    public double[] h() {
        return this.a;
    }

    public static class a {
        public static double[] s = new double[91];
        public double[] a;
        public double b;
        public double c;
        public double d;
        public double e;
        public double f;
        public double g;
        public double h;
        public double i;
        public double j;
        public double k;
        public double l;
        public double m;
        public double n;
        public double o;
        public double p;
        public boolean q;
        public boolean r;

        public a(int n3, double d3, double d4, double d5, double d6, double d7, double d8) {
            boolean bl = false;
            boolean bl2 = false;
            this.r = false;
            double d9 = d7 - d5;
            double d10 = d8 - d6;
            int n4 = 1;
            if (n3 != 1) {
                if (n3 != 4) {
                    if (n3 != 5) {
                        this.q = false;
                    } else {
                        bl = bl2;
                        if (d10 < 0.0) {
                            bl = true;
                        }
                        this.q = bl;
                    }
                } else {
                    if (d10 > 0.0) {
                        bl = true;
                    }
                    this.q = bl;
                }
            } else {
                this.q = true;
            }
            this.c = d3;
            this.d = d4;
            this.i = 1.0 / (d4 - d3);
            if (3 == n3) {
                this.r = true;
            }
            if (!(this.r || Math.abs(d9) < 0.001 || Math.abs(d10) < 0.001)) {
                this.a = new double[101];
                bl = this.q;
                n3 = bl ? -1 : 1;
                this.j = d9 * (double)n3;
                n3 = bl ? n4 : -1;
                this.k = d10 * (double)n3;
                d3 = bl ? d7 : d5;
                this.l = d3;
                d3 = bl ? d6 : d8;
                this.m = d3;
                this.a(d5, d6, d7, d8);
                this.n = this.b * this.i;
                return;
            }
            this.r = true;
            this.e = d5;
            this.f = d7;
            this.g = d6;
            this.h = d8;
            this.b = d3 = Math.hypot(d10, d9);
            this.n = d3 * this.i;
            d3 = this.d;
            d4 = this.c;
            this.l = d9 / (d3 - d4);
            this.m = d10 / (d3 - d4);
        }

        public final void a(double d3, double d4, double d5, double d6) {
            double[] dArray;
            int n3;
            double d7 = 0.0;
            double d8 = 0.0;
            double d9 = 0.0;
            for (n3 = 0; n3 < (dArray = s).length; ++n3) {
                double d10 = Math.toRadians((double)n3 * 90.0 / (double)(dArray.length - 1));
                double d11 = Math.sin(d10);
                d10 = Math.cos(d10);
                double d12 = d11 * (d5 - d3);
                d11 = d10 * (d4 - d6);
                d10 = d7;
                if (n3 > 0) {
                    s.a$a.s[n3] = d10 = d7 + Math.hypot(d12 - d8, d11 - d9);
                }
                d8 = d12;
                d9 = d11;
                d7 = d10;
            }
            this.b = d7;
            for (n3 = 0; n3 < (dArray = s).length; ++n3) {
                dArray[n3] = dArray[n3] / d7;
            }
            for (n3 = 0; n3 < (dArray = this.a).length; ++n3) {
                d5 = (double)n3 / (double)(dArray.length - 1);
                int n4 = Arrays.binarySearch(s, d5);
                if (n4 >= 0) {
                    this.a[n3] = (double)n4 / (double)(s.length - 1);
                    continue;
                }
                if (n4 == -1) {
                    this.a[n3] = 0.0;
                    continue;
                }
                n4 = -n4;
                int n5 = n4 - 2;
                d3 = n5;
                dArray = s;
                d4 = dArray[n5];
                this.a[n3] = d3 = (d3 + (d5 - d4) / (dArray[n4 - 1] - d4)) / (double)(dArray.length - 1);
            }
        }

        public double b() {
            double d3 = this.j * this.p;
            double d4 = -this.k;
            double d5 = this.o;
            d5 = this.n / Math.hypot(d3, d4 * d5);
            if (this.q) {
                return -d3 * d5;
            }
            return d3 * d5;
        }

        public double c() {
            double d3 = this.j;
            double d4 = this.p;
            double d5 = -this.k * this.o;
            d3 = this.n / Math.hypot(d3 * d4, d5);
            if (this.q) {
                return -d5 * d3;
            }
            return d5 * d3;
        }

        public double d(double d3) {
            return this.l;
        }

        public double e(double d3) {
            return this.m;
        }

        public double f(double d3) {
            double d4 = this.c;
            double d5 = this.i;
            double d6 = this.e;
            return d6 + (d3 - d4) * d5 * (this.f - d6);
        }

        public double g(double d3) {
            double d4 = this.c;
            double d5 = this.i;
            double d6 = this.g;
            return d6 + (d3 - d4) * d5 * (this.h - d6);
        }

        public double h() {
            return this.l + this.j * this.o;
        }

        public double i() {
            return this.m + this.k * this.p;
        }

        public double j(double d3) {
            if (d3 <= 0.0) {
                return 0.0;
            }
            if (d3 >= 1.0) {
                return 1.0;
            }
            double[] dArray = this.a;
            int n3 = (int)(d3 *= (double)(dArray.length - 1));
            double d4 = n3;
            double d5 = dArray[n3];
            return d5 + (d3 - d4) * (dArray[n3 + 1] - d5);
        }

        public void k(double d3) {
            d3 = this.q ? this.d - d3 : (d3 -= this.c);
            d3 = this.j(d3 * this.i) * 1.5707963267948966;
            this.o = Math.sin(d3);
            this.p = Math.cos(d3);
        }
    }
}

