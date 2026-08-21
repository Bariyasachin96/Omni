/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 */
package f0;

import android.graphics.Color;
import g0.a;

public abstract class b {
    public static final float[][] a = new float[][]{{0.401288f, 0.650173f, -0.051461f}, {-0.250268f, 1.204414f, 0.045854f}, {-0.002079f, 0.048952f, 0.953127f}};
    public static final float[][] b;
    public static final float[] c;
    public static final float[][] d;

    static {
        float[] fArray = new float[]{1.8620678f, -1.0112547f, 0.14918678f};
        float[] fArray2 = new float[]{-0.0158415f, -0.03412294f, 1.0499644f};
        b = new float[][]{fArray, {0.38752654f, 0.62144744f, -0.00897398f}, fArray2};
        c = new float[]{95.047f, 100.0f, 108.883f};
        d = new float[][]{{0.41233894f, 0.35762063f, 0.18051042f}, {0.2126f, 0.7152f, 0.0722f}, {0.01932141f, 0.11916382f, 0.9503448f}};
    }

    public static int a(float f3) {
        if (f3 < 1.0f) {
            return -16777216;
        }
        if (f3 > 99.0f) {
            return -1;
        }
        float f4 = (f3 + 16.0f) / 116.0f;
        f3 = f3 > 8.0f ? f4 * f4 * f4 : (f3 /= 903.2963f);
        float f5 = f4 * f4 * f4;
        boolean bl = f5 > 0.008856452f;
        float f6 = bl ? f5 : (f4 * 116.0f - 16.0f) / 903.2963f;
        if (!bl) {
            f5 = (f4 * 116.0f - 16.0f) / 903.2963f;
        }
        float[] fArray = c;
        return g0.a.b(f6 * fArray[0], f3 * fArray[1], f5 * fArray[2]);
    }

    public static float b(int n3) {
        return f0.b.c(f0.b.g(n3));
    }

    public static float c(float f3) {
        if ((f3 /= 100.0f) <= 0.008856452f) {
            return f3 * 903.2963f;
        }
        return (float)Math.cbrt(f3) * 116.0f - 16.0f;
    }

    public static float d(float f3, float f4, float f5) {
        return f3 + (f4 - f3) * f5;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float e(int n3) {
        float f3 = (float)n3 / 255.0f;
        if (f3 <= 0.04045f) {
            f3 /= 12.92f;
            return f3 * 100.0f;
        }
        f3 = (float)Math.pow((f3 + 0.055f) / 1.055f, 2.4f);
        return f3 * 100.0f;
    }

    public static void f(int n3, float[] fArray) {
        float f3 = f0.b.e(Color.red((int)n3));
        float f4 = f0.b.e(Color.green((int)n3));
        float f5 = f0.b.e(Color.blue((int)n3));
        Object object = d;
        float[] fArray2 = object[0];
        fArray[0] = fArray2[0] * f3 + fArray2[1] * f4 + fArray2[2] * f5;
        fArray2 = object[1];
        fArray[1] = fArray2[0] * f3 + fArray2[1] * f4 + fArray2[2] * f5;
        object = object[2];
        fArray[2] = f3 * object[0] + f4 * object[1] + f5 * object[2];
    }

    public static float g(int n3) {
        float f3 = f0.b.e(Color.red((int)n3));
        float f4 = f0.b.e(Color.green((int)n3));
        float f5 = f0.b.e(Color.blue((int)n3));
        float[] fArray = d[1];
        return f3 * fArray[0] + f4 * fArray[1] + f5 * fArray[2];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static float h(float f3) {
        if (f3 > 8.0f) {
            f3 = (float)Math.pow(((double)f3 + 16.0) / 116.0, 3.0);
            return f3 * 100.0f;
        }
        f3 /= 903.2963f;
        return f3 * 100.0f;
    }
}

