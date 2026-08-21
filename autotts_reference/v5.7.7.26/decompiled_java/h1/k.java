/*
 * Decompiled with CFR 0.152.
 */
package h1;

import java.util.Arrays;

public abstract class k {
    public static final byte[] a = new byte[]{48, 49, 53, 0};
    public static final byte[] b = new byte[]{48, 49, 48, 0};
    public static final byte[] c = new byte[]{48, 48, 57, 0};
    public static final byte[] d = new byte[]{48, 48, 53, 0};
    public static final byte[] e = new byte[]{48, 48, 49, 0};
    public static final byte[] f = new byte[]{48, 48, 49, 0};
    public static final byte[] g = new byte[]{48, 48, 50, 0};

    public static String a(byte[] byArray) {
        if (Arrays.equals(byArray, e)) {
            return ":";
        }
        if (Arrays.equals(byArray, d)) {
            return ":";
        }
        return "!";
    }
}

