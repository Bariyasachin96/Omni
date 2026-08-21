/*
 * Decompiled with CFR 0.152.
 */
package r1;

public abstract class c {
    public static int a(int n3) {
        for (int i3 = 0; i3 < 3; ++i3) {
            int n4 = (new int[]{1, 2, 3})[i3];
            if (n4 != 0) {
                if (n4 - 1 != n3) continue;
                return n4;
            }
            throw null;
        }
        return 1;
    }
}

