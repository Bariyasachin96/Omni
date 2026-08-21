/*
 * Decompiled with CFR 0.152.
 */
package o2;

public abstract class a {
    public static boolean a(float[] fArray) {
        if (fArray.length <= 1) {
            return true;
        }
        float f3 = fArray[0];
        for (int i3 = 1; i3 < fArray.length; ++i3) {
            if (fArray[i3] == f3) continue;
            return false;
        }
        return true;
    }

    public static float b(float f3, float f4, float f5, float f6) {
        return (float)Math.hypot(f5 - f3, f6 - f4);
    }

    public static float c(float f3, float f4, float f5, float f6, float f7, float f8) {
        return a.g(a.b(f3, f4, f5, f6), a.b(f3, f4, f7, f6), a.b(f3, f4, f7, f8), a.b(f3, f4, f5, f8));
    }

    public static int d(int n3, int n4) {
        int n5;
        int n6 = n5 = n3 / n4;
        if ((n3 ^ n4) < 0) {
            n6 = n5;
            if (n5 * n4 != n3) {
                n6 = n5 - 1;
            }
        }
        return n3 - n6 * n4;
    }

    public static boolean e(float f3, float f4, float f5) {
        return f3 + f5 >= f4;
    }

    public static float f(float f3, float f4, float f5) {
        return (1.0f - f5) * f3 + f5 * f4;
    }

    public static float g(float f3, float f4, float f5, float f6) {
        if (f3 > f4 && f3 > f5 && f3 > f6) {
            return f3;
        }
        if (f4 > f5 && f4 > f6) {
            return f4;
        }
        if (f5 > f6) {
            return f5;
        }
        return f6;
    }
}

