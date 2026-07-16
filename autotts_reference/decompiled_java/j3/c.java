/*
 * Decompiled with CFR 0.152.
 */
package j3;

public abstract class c {
    public static final int a(int n3, int n4, int n5) {
        return c.c(c.c(n3, n5) - c.c(n4, n5), n5);
    }

    public static final int b(int n3, int n4, int n5) {
        block7: {
            block8: {
                block6: {
                    block5: {
                        if (n5 <= 0) break block5;
                        if (n3 < n4) {
                            return n4 - c.a(n4, n3, n5);
                        }
                        break block6;
                    }
                    if (n5 >= 0) break block7;
                    if (n3 > n4) break block8;
                }
                return n4;
            }
            return n4 + c.a(n3, n4, -n5);
        }
        throw new IllegalArgumentException("Step is zero.");
    }

    public static final int c(int n3, int n4) {
        if ((n3 %= n4) >= 0) {
            return n3;
        }
        return n3 + n4;
    }
}

