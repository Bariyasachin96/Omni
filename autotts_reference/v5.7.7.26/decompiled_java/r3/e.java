/*
 * Decompiled with CFR 0.152.
 */
package r3;

import r3.a;
import r3.c;
import r3.d;

public abstract class e
extends d {
    public static int a(int n3, int n4) {
        if (n3 < n4) {
            return n4;
        }
        return n3;
    }

    public static int b(int n3, int n4) {
        if (n3 > n4) {
            return n4;
        }
        return n3;
    }

    public static float c(float f3, float f4, float f5) {
        if (!(f4 > f5)) {
            if (f3 < f4) {
                return f4;
            }
            if (f3 > f5) {
                return f5;
            }
            return f3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot coerce value to an empty range: maximum ");
        stringBuilder.append(f5);
        stringBuilder.append(" is less than minimum ");
        stringBuilder.append(f4);
        stringBuilder.append('.');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static a d(int n3, int n4) {
        return a.f.a(n3, n4, -1);
    }

    public static c e(int n3, int n4) {
        if (n4 <= Integer.MIN_VALUE) {
            return c.g.a();
        }
        return new c(n3, n4 - 1);
    }
}

