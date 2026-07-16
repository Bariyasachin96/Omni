/*
 * Decompiled with CFR 0.152.
 */
package n0;

import java.util.Locale;

public abstract class h {
    public static void a(boolean bl) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException();
    }

    public static void b(boolean bl, Object object) {
        if (bl) {
            return;
        }
        throw new IllegalArgumentException(String.valueOf(object));
    }

    public static int c(int n3, int n4, int n5, String string) {
        if (n3 >= n4) {
            if (n3 <= n5) {
                return n3;
            }
            throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too high)", string, n4, n5));
        }
        throw new IllegalArgumentException(String.format(Locale.US, "%s is out of range of [%d, %d] (too low)", string, n4, n5));
    }

    public static int d(int n3) {
        if (n3 >= 0) {
            return n3;
        }
        throw new IllegalArgumentException();
    }

    public static int e(int n3, String string) {
        if (n3 >= 0) {
            return n3;
        }
        throw new IllegalArgumentException(string);
    }

    public static int f(int n3, int n4) {
        if ((n3 & n4) == n3) {
            return n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Requested flags 0x");
        stringBuilder.append(Integer.toHexString(n3));
        stringBuilder.append(", but only 0x");
        stringBuilder.append(Integer.toHexString(n4));
        stringBuilder.append(" are allowed");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Object g(Object object) {
        object.getClass();
        return object;
    }

    public static Object h(Object object, Object object2) {
        if (object != null) {
            return object;
        }
        throw new NullPointerException(String.valueOf(object2));
    }

    public static void i(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new IllegalStateException(string);
    }
}

