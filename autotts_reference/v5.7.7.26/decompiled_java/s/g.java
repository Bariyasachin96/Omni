/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.lang.reflect.Array;
import java.util.Arrays;
import s.b;

public class g
extends b {
    public double[] a;
    public double[][] b;
    public double[][] c;
    public boolean d = true;
    public double[] e;

    public g(double[] dArray, double[][] dArray2) {
        Object object;
        double d3;
        double d4;
        int n3;
        int n4;
        int n5;
        int n6 = dArray.length;
        int n7 = dArray2[0].length;
        this.e = new double[n7];
        int n8 = n6 - 1;
        Object object2 = Double.TYPE;
        double[][] dArray3 = (double[][])Array.newInstance(object2, new int[]{n8, n7});
        object2 = (double[][])Array.newInstance(object2, new int[]{n6, n7});
        for (n5 = 0; n5 < n7; ++n5) {
            n4 = 0;
            while (n4 < n8) {
                n3 = n4 + 1;
                d4 = dArray[n3];
                d3 = dArray[n4];
                object = dArray3[n4];
                object[n5] = d4 = (dArray2[n3][n5] - dArray2[n4][n5]) / (d4 - d3);
                object2[n4][n5] = n4 == 0 ? (Object)d4 : (Object)((dArray3[n4 - 1][n5] + d4) * 0.5);
                n4 = n3;
            }
            object2[n8][n5] = dArray3[n6 - 2][n5];
        }
        for (n5 = 0; n5 < n8; ++n5) {
            for (n4 = 0; n4 < n7; ++n4) {
                d3 = dArray3[n5][n4];
                if (d3 == 0.0) {
                    object2[n5][n4] = 0.0;
                    object2[n5 + 1][n4] = 0.0;
                    continue;
                }
                d4 = (double)(object2[n5][n4] / d3);
                n3 = n5 + 1;
                double d5 = Math.hypot(d4, d3 = (double)(object2[n3][n4] / d3));
                if (!(d5 > 9.0)) continue;
                d5 = 3.0 / d5;
                object = object2[n5];
                double[] dArray4 = dArray3[n5];
                object[n4] = d4 * d5 * dArray4[n4];
                object2[n3][n4] = d5 * d3 * dArray4[n4];
            }
        }
        this.a = dArray;
        this.b = dArray2;
        this.c = (double[][])object2;
    }

    public static g i(String string) {
        double[] dArray = new double[string.length() / 2];
        int n3 = string.indexOf(40) + 1;
        int n4 = string.indexOf(44, n3);
        int n5 = 0;
        while (n4 != -1) {
            dArray[n5] = Double.parseDouble(string.substring(n3, n4).trim());
            n3 = n4 + 1;
            n4 = string.indexOf(44, n3);
            ++n5;
        }
        dArray[n5] = Double.parseDouble(string.substring(n3, string.indexOf(41, n3)).trim());
        return g.j(Arrays.copyOf(dArray, n5 + 1));
    }

    public static g j(double[] dArray) {
        int n3 = dArray.length * 3 - 2;
        int n4 = dArray.length - 1;
        double d3 = 1.0 / (double)n4;
        double[][] dArray2 = new double[n3][1];
        double[] dArray3 = new double[n3];
        for (n3 = 0; n3 < dArray.length; ++n3) {
            double d4;
            double d5 = dArray[n3];
            int n5 = n3 + n4;
            dArray2[n5][0] = d5;
            dArray3[n5] = d4 = (double)n3 * d3;
            if (n3 <= 0) continue;
            n5 = n4 * 2 + n3;
            dArray2[n5][0] = d5 + 1.0;
            dArray3[n5] = d4 + 1.0;
            n5 = n3 - 1;
            dArray2[n5][0] = d5 - 1.0 - d3;
            dArray3[n5] = d4 - 1.0 - d3;
        }
        return new g(dArray3, dArray2);
    }

    public static double k(double d3, double d4, double d5, double d6, double d7, double d8) {
        double d9 = d4 * d4;
        double d10 = d4 * 6.0;
        double d11 = 3.0 * d3;
        return -6.0 * d9 * d6 + d10 * d6 + 6.0 * d9 * d5 - d10 * d5 + d11 * d8 * d9 + d11 * d7 * d9 - 2.0 * d3 * d8 * d4 - 4.0 * d3 * d7 * d4 + d3 * d7;
    }

    public static double l(double d3, double d4, double d5, double d6, double d7, double d8) {
        double d9 = d4 * d4;
        double d10 = d9 * d4;
        double d11 = 3.0 * d9;
        double d12 = d3 * d8;
        d8 = d3 * d7;
        return -2.0 * d10 * d6 + d11 * d6 + d10 * 2.0 * d5 - d11 * d5 + d5 + d12 * d10 + d10 * d8 - d12 * d9 - d3 * 2.0 * d7 * d9 + d8 * d4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public double c(double d3, int n3) {
        int n4;
        double d4;
        double[] dArray = this.a;
        int n5 = dArray.length;
        boolean bl = this.d;
        int n6 = 0;
        if (bl) {
            double d5;
            d4 = dArray[0];
            if (d3 <= d4) {
                d5 = this.b[0][n3];
                d3 -= d4;
                d4 = this.f(d4, n3);
                return d5 + d3 * d4;
            }
            n4 = n5 - 1;
            d4 = dArray[n4];
            if (d3 >= d4) {
                d5 = this.b[n4][n3];
                d3 -= d4;
                d4 = this.f(d4, n3);
                return d5 + d3 * d4;
            }
        } else {
            if (d3 <= dArray[0]) {
                return this.b[0][n3];
            }
            n4 = n5 - 1;
            if (d3 >= dArray[n4]) {
                return this.b[n4][n3];
            }
        }
        while (n6 < n5 - 1) {
            double[] dArray2 = this.a;
            double d6 = dArray2[n6];
            if (d3 == d6) {
                return this.b[n6][n3];
            }
            n4 = n6 + 1;
            d4 = dArray2[n4];
            if (d3 < d4) {
                d4 -= d6;
                d6 = (d3 - d6) / d4;
                double[][] dArray3 = this.b;
                double d7 = dArray3[n6][n3];
                d3 = dArray3[n4][n3];
                double[][] dArray4 = this.c;
                return g.l(d4, d6, d7, d3, dArray4[n6][n3], dArray4[n4][n3]);
            }
            n6 = n4;
        }
        return 0.0;
    }

    @Override
    public void d(double d3, double[] dArray) {
        block10: {
            int n3;
            int n4;
            double d4;
            int n5;
            int n6;
            int n7;
            Object object;
            block13: {
                block14: {
                    block11: {
                        block12: {
                            object = this.a;
                            n7 = ((double[])object).length;
                            double[][] dArray2 = this.b;
                            int n8 = 0;
                            n6 = 0;
                            n5 = dArray2[0].length;
                            if (!this.d) break block11;
                            d4 = object[0];
                            if (!(d3 <= d4)) break block12;
                            this.g(d4, this.e);
                            for (n4 = 0; n4 < n5; ++n4) {
                                dArray[n4] = this.b[0][n4] + (d3 - this.a[0]) * this.e[n4];
                            }
                            break block10;
                        }
                        n3 = n7 - 1;
                        d4 = object[n3];
                        if (!(d3 >= d4)) break block13;
                        this.g(d4, this.e);
                        for (n4 = n6; n4 < n5; ++n4) {
                            dArray[n4] = this.b[n3][n4] + (d3 - this.a[n3]) * this.e[n4];
                        }
                        break block10;
                    }
                    if (!(d3 <= object[0])) break block14;
                    for (n4 = 0; n4 < n5; ++n4) {
                        dArray[n4] = this.b[0][n4];
                    }
                    break block10;
                }
                n6 = n7 - 1;
                if (d3 >= object[n6]) {
                    for (n4 = 0; n4 < n5; ++n4) {
                        dArray[n4] = this.b[n6][n4];
                    }
                }
                break block13;
                break block10;
            }
            n4 = 0;
            while (n4 < n7 - 1) {
                if (d3 == this.a[n4]) {
                    for (n6 = 0; n6 < n5; ++n6) {
                        dArray[n6] = this.b[n4][n6];
                    }
                }
                if (d3 < (d4 = (object = this.a)[n3 = n4 + 1])) {
                    double d5 = object[n4];
                    double d6 = (d3 - d5) / (d4 -= d5);
                    for (n6 = n8; n6 < n5; ++n6) {
                        object = this.b;
                        d3 = object[n4][n6];
                        d5 = object[n3][n6];
                        object = this.c;
                        dArray[n6] = g.l(d4, d6, d3, d5, (double)object[n4][n6], (double)object[n3][n6]);
                    }
                    break;
                }
                n4 = n3;
            }
        }
    }

    @Override
    public void e(double d3, float[] fArray) {
        block10: {
            int n3;
            int n4;
            double d4;
            int n5;
            int n6;
            Object object;
            int n7;
            block13: {
                double[] dArray;
                block14: {
                    block11: {
                        block12: {
                            dArray = this.a;
                            n7 = dArray.length;
                            object = this.b;
                            int n8 = 0;
                            n6 = 0;
                            n5 = object[0].length;
                            if (!this.d) break block11;
                            d4 = dArray[0];
                            if (!(d3 <= d4)) break block12;
                            this.g(d4, this.e);
                            for (n4 = 0; n4 < n5; ++n4) {
                                fArray[n4] = (float)(this.b[0][n4] + (d3 - this.a[0]) * this.e[n4]);
                            }
                            break block10;
                        }
                        n3 = n7 - 1;
                        d4 = dArray[n3];
                        if (!(d3 >= d4)) break block13;
                        this.g(d4, this.e);
                        for (n4 = n6; n4 < n5; ++n4) {
                            fArray[n4] = (float)(this.b[n3][n4] + (d3 - this.a[n3]) * this.e[n4]);
                        }
                        break block10;
                    }
                    if (!(d3 <= dArray[0])) break block14;
                    for (n4 = 0; n4 < n5; ++n4) {
                        fArray[n4] = (float)this.b[0][n4];
                    }
                    break block10;
                }
                n6 = n7 - 1;
                if (d3 >= dArray[n6]) {
                    for (n4 = 0; n4 < n5; ++n4) {
                        fArray[n4] = (float)this.b[n6][n4];
                    }
                }
                break block13;
                break block10;
            }
            n4 = 0;
            while (n4 < n7 - 1) {
                if (d3 == this.a[n4]) {
                    for (n6 = 0; n6 < n5; ++n6) {
                        fArray[n6] = (float)this.b[n4][n6];
                    }
                }
                if (d3 < (d4 = (double)(object = (Object)this.a)[n3 = n4 + 1])) {
                    Object object2 = object[n4];
                    double d5 = (d3 - object2) / (d4 -= object2);
                    for (n6 = n8; n6 < n5; ++n6) {
                        object = this.b;
                        object2 = object[n4][n6];
                        d3 = object[n3][n6];
                        object = this.c;
                        fArray[n6] = (float)g.l(d4, d5, (double)object2, d3, object[n4][n6], object[n3][n6]);
                    }
                    break;
                }
                n4 = n3;
            }
        }
    }

    @Override
    public double f(double d3, int n3) {
        Object object = this.a;
        int n4 = ((double[])object).length;
        int n5 = 0;
        double d4 = object[0];
        if (d3 < d4) {
            d3 = d4;
        } else {
            d4 = object[n4 - 1];
            if (d3 >= d4) {
                d3 = d4;
            }
        }
        while (n5 < n4 - 1) {
            object = this.a;
            int n6 = n5 + 1;
            d4 = object[n6];
            if (d3 <= d4) {
                double d5 = object[n5];
                d3 = (d3 - d5) / (d4 -= d5);
                object = this.b;
                void var8_9 = object[n5][n3];
                d5 = object[n6][n3];
                object = this.c;
                return g.k(d4, d3, (double)var8_9, d5, (double)object[n5][n3], (double)object[n6][n3]) / d4;
            }
            n5 = n6;
        }
        return 0.0;
    }

    @Override
    public void g(double d3, double[] dArray) {
        Object object = this.a;
        int n3 = ((double[])object).length;
        double[][] dArray2 = this.b;
        int n4 = 0;
        int n5 = dArray2[0].length;
        double d4 = object[0];
        if (d3 <= d4) {
            d3 = d4;
        } else {
            d4 = object[n3 - 1];
            if (d3 >= d4) {
                d3 = d4;
            }
        }
        int n6 = 0;
        while (n6 < n3 - 1) {
            object = this.a;
            int n7 = n6 + 1;
            d4 = object[n7];
            if (d3 <= d4) {
                double d5 = object[n6];
                d4 -= d5;
                d5 = (d3 - d5) / d4;
                while (n4 < n5) {
                    object = this.b;
                    d3 = object[n6][n4];
                    void var8_12 = object[n7][n4];
                    object = this.c;
                    dArray[n4] = g.k(d4, d5, d3, (double)var8_12, (double)object[n6][n4], (double)object[n7][n4]) / d4;
                    ++n4;
                }
                break;
            }
            n6 = n7;
        }
    }

    @Override
    public double[] h() {
        return this.a;
    }
}

