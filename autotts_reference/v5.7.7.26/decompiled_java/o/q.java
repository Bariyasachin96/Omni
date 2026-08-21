/*
 * Decompiled with CFR 0.152.
 */
package o;

import o.o;

public abstract class q {
    public static final long[] a = new long[]{-9187201950435737345L, -1L};
    public static final o b = new o(0);

    public static final int a(int n3) {
        if (n3 == 7) {
            return 6;
        }
        return n3 - n3 / 8;
    }

    public static final int b(int n3) {
        if (n3 > 0) {
            return -1 >>> Integer.numberOfLeadingZeros(n3);
        }
        return 0;
    }

    public static final int c(int n3) {
        if (n3 == 7) {
            return 8;
        }
        return n3 + (n3 - 1) / 7;
    }
}

