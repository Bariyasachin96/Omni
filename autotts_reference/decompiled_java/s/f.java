/*
 * Decompiled with CFR 0.152.
 */
package s;

import s.b;

public class f
extends b {
    public double[] a;
    public double[][] b;
    public double c = Double.NaN;
    public boolean d = true;
    public double[] e;

    public f(double[] dArray, double[][] dArray2) {
        int n3 = dArray2[0].length;
        this.e = new double[n3];
        this.a = dArray;
        this.b = dArray2;
        if (n3 > 2) {
            double d3 = 0.0;
            for (n3 = 0; n3 < dArray.length; ++n3) {
                double d4 = dArray2[n3][0];
                if (n3 > 0) {
                    Math.hypot(d4 - d3, d4 - d3);
                }
                d3 = d4;
            }
            this.c = 0.0;
        }
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
                double d6 = this.b[0][n3];
                d5 = d3 - d4;
                d4 = this.f(d4, n3);
                d3 = d6;
                return d3 + d5 * d4;
            }
            n4 = n5 - 1;
            double d7 = dArray[n4];
            if (d3 >= d7) {
                d4 = this.b[n4][n3];
                d5 = d3 - d7;
                d7 = this.f(d7, n3);
                d3 = d4;
                d4 = d7;
                return d3 + d5 * d4;
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
            d4 = dArray2[n6];
            if (d3 == d4) {
                return this.b[n6][n3];
            }
            n4 = n6 + 1;
            double d8 = dArray2[n4];
            if (d3 < d8) {
                d3 = (d3 - d4) / (d8 - d4);
                double[][] dArray3 = this.b;
                return dArray3[n6][n3] * (1.0 - d3) + dArray3[n4][n3] * d3;
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
                            n6 = 0;
                            int n8 = 0;
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
                        n6 = n7 - 1;
                        d4 = object[n6];
                        if (!(d3 >= d4)) break block13;
                        this.g(d4, this.e);
                        for (n4 = 0; n4 < n5; ++n4) {
                            dArray[n4] = this.b[n6][n4] + (d3 - this.a[n6]) * this.e[n4];
                        }
                        break block10;
                    }
                    if (!(d3 <= object[0])) break block14;
                    for (n4 = 0; n4 < n5; ++n4) {
                        dArray[n4] = this.b[0][n4];
                    }
                    break block10;
                }
                n3 = n7 - 1;
                if (d3 >= object[n3]) {
                    for (n4 = n6; n4 < n5; ++n4) {
                        dArray[n4] = this.b[n3][n4];
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
                    d3 = (d3 - d5) / (d4 - d5);
                    for (n6 = n8; n6 < n5; ++n6) {
                        object = this.b;
                        dArray[n6] = object[n4][n6] * (1.0 - d3) + object[n3][n6] * d3;
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
            int n7;
            Object object;
            block13: {
                block14: {
                    block11: {
                        block12: {
                            object = this.a;
                            n7 = ((double[])object).length;
                            double[][] dArray = this.b;
                            n6 = 0;
                            int n8 = 0;
                            n5 = dArray[0].length;
                            if (!this.d) break block11;
                            d4 = object[0];
                            if (!(d3 <= d4)) break block12;
                            this.g(d4, this.e);
                            for (n4 = 0; n4 < n5; ++n4) {
                                fArray[n4] = (float)(this.b[0][n4] + (d3 - this.a[0]) * this.e[n4]);
                            }
                            break block10;
                        }
                        n6 = n7 - 1;
                        d4 = object[n6];
                        if (!(d3 >= d4)) break block13;
                        this.g(d4, this.e);
                        for (n4 = 0; n4 < n5; ++n4) {
                            fArray[n4] = (float)(this.b[n6][n4] + (d3 - this.a[n6]) * this.e[n4]);
                        }
                        break block10;
                    }
                    if (!(d3 <= object[0])) break block14;
                    for (n4 = 0; n4 < n5; ++n4) {
                        fArray[n4] = (float)this.b[0][n4];
                    }
                    break block10;
                }
                n3 = n7 - 1;
                if (d3 >= object[n3]) {
                    for (n4 = n6; n4 < n5; ++n4) {
                        fArray[n4] = (float)this.b[n3][n4];
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
                if (d3 < (d4 = (object = this.a)[n3 = n4 + 1])) {
                    double d5 = object[n4];
                    d3 = (d3 - d5) / (d4 - d5);
                    for (n6 = n8; n6 < n5; ++n6) {
                        object = this.b;
                        fArray[n6] = (float)(object[n4][n6] * (1.0 - d3) + object[n3][n6] * d3);
                    }
                    break;
                }
                n4 = n3;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public double f(double d3, int n3) {
        int n4;
        double d4;
        double d5;
        int n5;
        int n6;
        block8: {
            block7: {
                double[] dArray;
                block6: {
                    dArray = this.a;
                    n6 = dArray.length;
                    n5 = 0;
                    d5 = dArray[0];
                    if (!(d3 < d5)) break block6;
                    d3 = d5;
                    break block7;
                }
                d4 = dArray[n6 - 1];
                n4 = n5;
                d5 = d3;
                if (!(d3 >= d4)) break block8;
                d3 = d4;
            }
            n4 = n5;
            d5 = d3;
        }
        while (n4 < n6 - 1) {
            double[] dArray = this.a;
            n5 = n4 + 1;
            d3 = dArray[n5];
            if (d5 <= d3) {
                d4 = dArray[n4];
                double[][] dArray2 = this.b;
                d5 = dArray2[n4][n3];
                return (dArray2[n5][n3] - d5) / (d3 - d4);
            }
            n4 = n5;
        }
        return 0.0;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void g(double d3, double[] dArray) {
        double d4;
        double d5;
        int n3;
        int n4;
        int n5;
        block8: {
            block7: {
                double[] dArray2;
                block6: {
                    dArray2 = this.a;
                    n5 = dArray2.length;
                    double[][] dArray3 = this.b;
                    n4 = 0;
                    n3 = dArray3[0].length;
                    d5 = dArray2[0];
                    if (!(d3 <= d5)) break block6;
                    d3 = d5;
                    break block7;
                }
                d4 = dArray2[n5 - 1];
                d5 = d3;
                if (!(d3 >= d4)) break block8;
                d3 = d4;
            }
            d5 = d3;
        }
        int n6 = 0;
        while (n6 < n5 - 1) {
            double[] dArray4 = this.a;
            int n7 = n6 + 1;
            d3 = dArray4[n7];
            if (d5 <= d3) {
                d4 = dArray4[n6];
                while (n4 < n3) {
                    double[][] dArray5 = this.b;
                    d5 = dArray5[n6][n4];
                    dArray[n4] = (dArray5[n7][n4] - d5) / (d3 - d4);
                    ++n4;
                }
                return;
            }
            n6 = n7;
        }
    }

    @Override
    public double[] h() {
        return this.a;
    }
}

