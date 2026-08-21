/*
 * Decompiled with CFR 0.152.
 */
package p;

import o3.k;

public abstract class a {
    public static final int[] a = new int[0];
    public static final long[] b = new long[0];
    public static final Object[] c = new Object[0];

    public static final int a(int[] nArray, int n3, int n4) {
        k.e(nArray, "array");
        --n3;
        int n5 = 0;
        while (n5 <= n3) {
            int n6 = n5 + n3 >>> 1;
            int n7 = nArray[n6];
            if (n7 < n4) {
                n5 = n6 + 1;
                continue;
            }
            if (n7 > n4) {
                n3 = n6 - 1;
                continue;
            }
            return n6;
        }
        return ~n5;
    }

    public static final int b(long[] lArray, int n3, long l3) {
        k.e(lArray, "array");
        --n3;
        int n4 = 0;
        while (n4 <= n3) {
            int n5 = n4 + n3 >>> 1;
            long l4 = lArray[n5] - l3;
            long l5 = l4 == 0L ? 0 : (l4 < 0L ? -1 : 1);
            if (l5 < 0) {
                n4 = n5 + 1;
                continue;
            }
            if (l5 > 0) {
                n3 = n5 - 1;
                continue;
            }
            return n5;
        }
        return ~n4;
    }

    public static final boolean c(Object object, Object object2) {
        return k.a(object, object2);
    }

    public static final int d(int n3) {
        for (int i3 = 4; i3 < 32; ++i3) {
            int n4 = (1 << i3) - 12;
            if (n3 > n4) continue;
            return n4;
        }
        return n3;
    }

    public static final int e(int n3) {
        return p.a.d(n3 * 4) / 4;
    }

    public static final int f(int n3) {
        return p.a.d(n3 * 8) / 8;
    }
}

