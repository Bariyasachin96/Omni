/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package g0;

import android.graphics.Color;

public abstract class a {
    public static final ThreadLocal a = new ThreadLocal();

    public static void a(int n3, int n4, int n5, double[] dArray) {
        if (dArray.length == 3) {
            double d3 = (double)n3 / 255.0;
            d3 = d3 < 0.04045 ? (d3 /= 12.92) : Math.pow((d3 + 0.055) / 1.055, 2.4);
            double d4 = (double)n4 / 255.0;
            d4 = d4 < 0.04045 ? (d4 /= 12.92) : Math.pow((d4 + 0.055) / 1.055, 2.4);
            double d5 = (double)n5 / 255.0;
            d5 = d5 < 0.04045 ? (d5 /= 12.92) : Math.pow((d5 + 0.055) / 1.055, 2.4);
            dArray[0] = (0.4124 * d3 + 0.3576 * d4 + 0.1805 * d5) * 100.0;
            dArray[1] = (0.2126 * d3 + 0.7152 * d4 + 0.0722 * d5) * 100.0;
            dArray[2] = (d3 * 0.0193 + d4 * 0.1192 + d5 * 0.9505) * 100.0;
            return;
        }
        throw new IllegalArgumentException("outXyz must have a length of 3.");
    }

    public static int b(double d3, double d4, double d5) {
        double d6 = (3.2406 * d3 + -1.5372 * d4 + -0.4986 * d5) / 100.0;
        double d7 = (-0.9689 * d3 + 1.8758 * d4 + 0.0415 * d5) / 100.0;
        d5 = (0.0557 * d3 + -0.204 * d4 + 1.057 * d5) / 100.0;
        d3 = d6 > 0.0031308 ? Math.pow(d6, 0.4166666666666667) * 1.055 - 0.055 : d6 * 12.92;
        d4 = d7 > 0.0031308 ? Math.pow(d7, 0.4166666666666667) * 1.055 - 0.055 : d7 * 12.92;
        d5 = d5 > 0.0031308 ? Math.pow(d5, 0.4166666666666667) * 1.055 - 0.055 : (d5 *= 12.92);
        return Color.rgb((int)g0.a.i((int)Math.round(d3 * 255.0), 0, 255), (int)g0.a.i((int)Math.round(d4 * 255.0), 0, 255), (int)g0.a.i((int)Math.round(d5 * 255.0), 0, 255));
    }

    public static int c(int n3, int n4, float f3) {
        float f4 = 1.0f - f3;
        float f5 = Color.alpha((int)n3);
        float f6 = Color.alpha((int)n4);
        float f7 = Color.red((int)n3);
        float f8 = Color.red((int)n4);
        float f9 = Color.green((int)n3);
        float f10 = Color.green((int)n4);
        float f11 = Color.blue((int)n3);
        float f12 = Color.blue((int)n4);
        return Color.argb((int)((int)(f5 * f4 + f6 * f3)), (int)((int)(f7 * f4 + f8 * f3)), (int)((int)(f9 * f4 + f10 * f3)), (int)((int)(f11 * f4 + f12 * f3)));
    }

    public static double d(int n3) {
        double[] dArray = g0.a.j();
        g0.a.e(n3, dArray);
        return dArray[1] / 100.0;
    }

    public static void e(int n3, double[] dArray) {
        g0.a.a(Color.red((int)n3), Color.green((int)n3), Color.blue((int)n3), dArray);
    }

    public static int f(int n3, int n4) {
        return 255 - (255 - n4) * (255 - n3) / 255;
    }

    public static int g(int n3, int n4) {
        int n5 = Color.alpha((int)n4);
        int n6 = Color.alpha((int)n3);
        int n7 = g0.a.f(n6, n5);
        return Color.argb((int)n7, (int)g0.a.h(Color.red((int)n3), n6, Color.red((int)n4), n5, n7), (int)g0.a.h(Color.green((int)n3), n6, Color.green((int)n4), n5, n7), (int)g0.a.h(Color.blue((int)n3), n6, Color.blue((int)n4), n5, n7));
    }

    public static int h(int n3, int n4, int n5, int n6, int n7) {
        if (n7 == 0) {
            return 0;
        }
        return (n3 * 255 * n4 + n5 * n6 * (255 - n4)) / (n7 * 255);
    }

    public static int i(int n3, int n4, int n5) {
        if (n3 < n4) {
            return n4;
        }
        return Math.min(n3, n5);
    }

    public static double[] j() {
        double[] dArray;
        ThreadLocal threadLocal = a;
        double[] dArray2 = dArray = (double[])threadLocal.get();
        if (dArray == null) {
            dArray2 = new double[3];
            threadLocal.set(dArray2);
        }
        return dArray2;
    }

    public static int k(int n3, int n4) {
        if (n4 >= 0 && n4 <= 255) {
            return n3 & 0xFFFFFF | n4 << 24;
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }
}

