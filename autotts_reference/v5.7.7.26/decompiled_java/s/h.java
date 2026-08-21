/*
 * Decompiled with CFR 0.152.
 */
package s;

import java.util.Arrays;
import s.g;

public class h {
    public float[] a = new float[0];
    public double[] b = new double[0];
    public double[] c;
    public String d;
    public g e;
    public int f;
    public double g = Math.PI * 2;
    public boolean h = false;

    public void a(double d3, float f3) {
        int n3;
        int n4 = this.a.length + 1;
        int n5 = n3 = Arrays.binarySearch(this.b, d3);
        if (n3 < 0) {
            n5 = -n3 - 1;
        }
        this.b = Arrays.copyOf(this.b, n4);
        this.a = Arrays.copyOf(this.a, n4);
        this.c = new double[n4];
        double[] dArray = this.b;
        System.arraycopy(dArray, n5, dArray, n5 + 1, n4 - n5 - 1);
        this.b[n5] = d3;
        this.a[n5] = f3;
        this.h = false;
    }

    public double b(double d3) {
        int n3;
        if (d3 <= 0.0) {
            return 0.0;
        }
        if (d3 >= 1.0) {
            return 1.0;
        }
        int n4 = n3 = Arrays.binarySearch(this.b, d3);
        if (n3 < 0) {
            n4 = -n3 - 1;
        }
        Object[] objectArray = this.a;
        float f3 = objectArray[n4];
        n3 = n4 - 1;
        float f4 = objectArray[n3];
        double d4 = f3 - f4;
        objectArray = this.b;
        float f5 = objectArray[n4];
        float f6 = objectArray[n3];
        f5 = (float)(d4 / (f5 - f6));
        return d3 * f5 + ((double)f4 - f5 * f6);
    }

    public double c(double d3) {
        int n3;
        if (d3 <= 0.0) {
            return 0.0;
        }
        if (d3 >= 1.0) {
            return 1.0;
        }
        int n4 = n3 = Arrays.binarySearch(this.b, d3);
        if (n3 < 0) {
            n4 = -n3 - 1;
        }
        Object[] objectArray = this.a;
        float f3 = objectArray[n4];
        n3 = n4 - 1;
        float f4 = objectArray[n3];
        double d4 = f3 - f4;
        objectArray = this.b;
        float f5 = objectArray[n4];
        float f6 = objectArray[n3];
        return this.c[n3] + ((double)f4 - (d4 /= f5 - f6) * f6) * (d3 - f6) + d4 * (d3 * d3 - f6 * f6) / 2.0;
    }

    public double d(double d3, double d4, double d5) {
        d4 += this.c(d3);
        d3 = this.b(d3) + d5;
        switch (this.f) {
            default: {
                d5 = this.g;
                return d3 * d5 * Math.cos(d5 * d4);
            }
            case 7: {
                return this.e.f(d4 % 1.0, 0);
            }
            case 6: {
                return d3 * 4.0 * ((d4 * 4.0 + 2.0) % 4.0 - 2.0);
            }
            case 5: {
                d5 = this.g;
                return -d5 * d3 * Math.sin(d5 * d4);
            }
            case 4: {
                return -d3 * 2.0;
            }
            case 3: {
                return d3 * 2.0;
            }
            case 2: {
                return d3 * 4.0 * Math.signum((d4 * 4.0 + 3.0) % 4.0 - 2.0);
            }
            case 1: 
        }
        return 0.0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public double e(double d3, double d4) {
        d3 = this.c(d3) + d4;
        switch (this.f) {
            default: {
                return Math.sin(this.g * d3);
            }
            case 7: {
                return this.e.c(d3 % 1.0, 0);
            }
            case 6: {
                d3 = 1.0 - Math.abs(d3 * 4.0 % 4.0 - 2.0);
                d3 *= d3;
                return 1.0 - d3;
            }
            case 5: {
                return Math.cos(this.g * (d4 + d3));
            }
            case 4: {
                d3 = (d3 * 2.0 + 1.0) % 2.0;
                return 1.0 - d3;
            }
            case 3: {
                return (d3 * 2.0 + 1.0) % 2.0 - 1.0;
            }
            case 2: {
                d3 = Math.abs((d3 * 4.0 + 1.0) % 4.0 - 2.0);
                return 1.0 - d3;
            }
            case 1: 
        }
        return Math.signum(0.5 - d3 % 1.0);
    }

    public void f() {
        float f3;
        int n3;
        Object[] objectArray;
        int n4;
        double d3 = 0.0;
        for (n4 = 0; n4 < (objectArray = this.a).length; ++n4) {
            d3 += (double)objectArray[n4];
        }
        double d4 = 0.0;
        for (n4 = 1; n4 < (objectArray = this.a).length; ++n4) {
            n3 = n4 - 1;
            f3 = (objectArray[n3] + objectArray[n4]) / 2.0f;
            objectArray = this.b;
            d4 += (objectArray[n4] - objectArray[n3]) * (double)f3;
        }
        for (n4 = 0; n4 < (objectArray = this.a).length; ++n4) {
            objectArray[n4] = objectArray[n4] * (float)(d3 / d4);
        }
        this.c[0] = 0.0;
        for (n4 = 1; n4 < (objectArray = this.a).length; ++n4) {
            n3 = n4 - 1;
            f3 = (objectArray[n3] + objectArray[n4]) / 2.0f;
            objectArray = this.b;
            d4 = objectArray[n4];
            d3 = objectArray[n3];
            objectArray = this.c;
            objectArray[n4] = objectArray[n3] + (d4 - d3) * (double)f3;
        }
        this.h = true;
    }

    public void g(int n3, String string) {
        this.f = n3;
        this.d = string;
        if (string != null) {
            this.e = s.g.i(string);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("pos =");
        stringBuilder.append(Arrays.toString(this.b));
        stringBuilder.append(" period=");
        stringBuilder.append(Arrays.toString(this.a));
        return stringBuilder.toString();
    }
}

