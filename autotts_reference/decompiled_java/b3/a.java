/*
 * Decompiled with CFR 0.152.
 */
package b3;

import b3.b;

public abstract class a {
    public static final byte[] a = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    public static final byte[] b = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    public static final byte[] c = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, -9, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, -9, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};
    public static final byte[] d = new byte[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -5, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -5, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, 62, -9, -9, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -9, -9, -9, -1, -9, -9, -9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -9, -9, -9, -9, 63, -9, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -9, -9, -9, -9, -9};

    public static byte[] a(String object) {
        object = ((String)object).getBytes();
        return b3.a.b((byte[])object, 0, ((Object)object).length);
    }

    public static byte[] b(byte[] byArray, int n3, int n4) {
        return b3.a.c(byArray, n3, n4, c);
    }

    public static byte[] c(byte[] object, int n3, int n4, byte[] object2) {
        int n5;
        byte[] byArray = new byte[n4 * 3 / 4 + 2];
        byte[] byArray2 = new byte[4];
        int n6 = n5 = 0;
        for (int i3 = 0; i3 < n4; ++i3) {
            int n7 = i3 + n3;
            byte by = (byte)(object[n7] & 0x7F);
            byte by2 = object2[by];
            if (by2 >= -5) {
                n7 = n5;
                int n8 = n6;
                if (by2 >= -1) {
                    if (by == 61) {
                        n7 = n4 - i3;
                        n3 = (byte)(object[n4 - 1 + n3] & 0x7F);
                        if (n5 != 0 && n5 != 1) {
                            if (n5 == 3 && n7 > 2 || n5 == 4 && n7 > 1) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("padding byte '=' falsely signals end of encoded value at offset ");
                                ((StringBuilder)object).append(i3);
                                throw new b(((StringBuilder)object).toString());
                            }
                            if (n3 == 61 || n3 == 10) break;
                            throw new b("encoded value has invalid trailing byte");
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("invalid padding byte '=' at byte offset ");
                        ((StringBuilder)object).append(i3);
                        throw new b(((StringBuilder)object).toString());
                    }
                    n7 = n5 + 1;
                    byArray2[n5] = by;
                    if (n7 == 4) {
                        n8 = n6 + b3.a.d(byArray2, 0, byArray, n6, (byte[])object2);
                        n7 = 0;
                    } else {
                        n8 = n6;
                    }
                }
                n5 = n7;
                n6 = n8;
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Bad Base64 input character at ");
            ((StringBuilder)object2).append(i3);
            ((StringBuilder)object2).append(": ");
            ((StringBuilder)object2).append((int)object[n7]);
            ((StringBuilder)object2).append("(decimal)");
            throw new b(((StringBuilder)object2).toString());
        }
        n3 = n6;
        if (n5 != 0) {
            if (n5 != 1) {
                byArray2[n5] = 61;
                n3 = n6 + b3.a.d(byArray2, 0, byArray, n6, (byte[])object2);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("single trailing character at offset ");
                ((StringBuilder)object).append(n4 - 1);
                throw new b(((StringBuilder)object).toString());
            }
        }
        object = new byte[n3];
        System.arraycopy(byArray, 0, object, 0, n3);
        return object;
    }

    public static int d(byte[] byArray, int n3, byte[] byArray2, int n4, byte[] byArray3) {
        byte by = byArray[n3 + 2];
        if (by == 61) {
            by = byArray3[byArray[n3]];
            byArray2[n4] = (byte)((byArray3[byArray[n3 + 1]] << 24 >>> 12 | by << 24 >>> 6) >>> 16);
            return 1;
        }
        byte by2 = byArray[n3 + 3];
        if (by2 == 61) {
            by2 = byArray3[byArray[n3]];
            n3 = byArray3[byArray[n3 + 1]] << 24 >>> 12 | by2 << 24 >>> 6 | byArray3[by] << 24 >>> 18;
            byArray2[n4] = (byte)(n3 >>> 16);
            byArray2[n4 + 1] = (byte)(n3 >>> 8);
            return 2;
        }
        byte by3 = byArray3[byArray[n3]];
        n3 = byArray3[byArray[n3 + 1]] << 24 >>> 12 | by3 << 24 >>> 6 | byArray3[by] << 24 >>> 18 | byArray3[by2] << 24 >>> 24;
        byArray2[n4] = (byte)(n3 >> 16);
        byArray2[n4 + 1] = (byte)(n3 >> 8);
        byArray2[n4 + 2] = (byte)n3;
        return 3;
    }

    public static String e(byte[] byArray) {
        return b3.a.f(byArray, 0, byArray.length, a, true);
    }

    public static String f(byte[] byArray, int n3, int n4, byte[] byArray2, boolean bl) {
        byArray = b3.a.g(byArray, n3, n4, byArray2, Integer.MAX_VALUE);
        for (n3 = byArray.length; !bl && n3 > 0 && byArray[n3 - 1] == 61; --n3) {
        }
        return new String(byArray, 0, n3);
    }

    public static byte[] g(byte[] byArray, int n3, int n4, byte[] byArray2, int n5) {
        int n6;
        int n7 = (n4 + 2) / 3 * 4;
        byte[] byArray3 = new byte[n7 + n7 / n5];
        int n8 = n7 = 0;
        for (n6 = 0; n6 < n4 - 2; n6 += 3) {
            int n9 = byArray[n6 + n3] << 24 >>> 8 | byArray[n6 + 1 + n3] << 24 >>> 16 | byArray[n6 + 2 + n3] << 24 >>> 24;
            byArray3[n7] = byArray2[n9 >>> 18];
            int n10 = n7 + 1;
            byArray3[n10] = byArray2[n9 >>> 12 & 0x3F];
            byArray3[n7 + 2] = byArray2[n9 >>> 6 & 0x3F];
            byArray3[n7 + 3] = byArray2[n9 & 0x3F];
            int n11 = n8 + 4;
            n9 = n7;
            n8 = n11;
            if (n11 == n5) {
                byArray3[n7 + 4] = 10;
                n8 = 0;
                n9 = n10;
            }
            n7 = n9 + 4;
        }
        if (n6 < n4) {
            b3.a.h(byArray, n3 + n6, n4 - n6, byArray3, n7, byArray2);
            if (n8 + 4 == n5) {
                byArray3[n7 + 4] = 10;
            }
        }
        return byArray3;
    }

    public static byte[] h(byte[] byArray, int n3, int n4, byte[] byArray2, int n5, byte[] byArray3) {
        int n6 = 0;
        int n7 = n4 > 0 ? byArray[n3] << 24 >>> 8 : 0;
        int n8 = n4 > 1 ? byArray[n3 + 1] << 24 >>> 16 : 0;
        if (n4 > 2) {
            n6 = byArray[n3 + 2] << 24 >>> 24;
        }
        n3 = n7 | n8 | n6;
        if (n4 != 1) {
            if (n4 != 2) {
                if (n4 != 3) {
                    return byArray2;
                }
                byArray2[n5] = byArray3[n3 >>> 18];
                byArray2[n5 + 1] = byArray3[n3 >>> 12 & 0x3F];
                byArray2[n5 + 2] = byArray3[n3 >>> 6 & 0x3F];
                byArray2[n5 + 3] = byArray3[n3 & 0x3F];
                return byArray2;
            }
            byArray2[n5] = byArray3[n3 >>> 18];
            byArray2[n5 + 1] = byArray3[n3 >>> 12 & 0x3F];
            byArray2[n5 + 2] = byArray3[n3 >>> 6 & 0x3F];
            byArray2[n5 + 3] = 61;
            return byArray2;
        }
        byArray2[n5] = byArray3[n3 >>> 18];
        byArray2[n5 + 1] = byArray3[n3 >>> 12 & 0x3F];
        byArray2[n5 + 2] = 61;
        byArray2[n5 + 3] = 61;
        return byArray2;
    }
}

