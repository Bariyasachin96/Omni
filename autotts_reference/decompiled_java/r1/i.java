/*
 * Decompiled with CFR 0.152.
 */
package r1;

public abstract class i {
    public static int a(int n3) {
        for (int i3 = 0; i3 < 6; ++i3) {
            int n4 = (new int[]{1, 2, 3, 4, 5, 6})[i3];
            if (n4 != 0) {
                if (n4 - 1 != n3) continue;
                return n4;
            }
            throw null;
        }
        return 1;
    }
}

