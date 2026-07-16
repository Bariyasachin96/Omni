/*
 * Decompiled with CFR 0.152.
 */
package o;

import java.util.ConcurrentModificationException;
import o.b;
import o3.k;
import p.a;

public abstract class d {
    public static final void a(b b3, int n3) {
        k.e(b3, "<this>");
        b3.h(new int[n3]);
        b3.g(new Object[n3]);
    }

    public static final int b(b b3, int n3) {
        k.e(b3, "<this>");
        try {
            n3 = a.a(b3.c(), b3.e(), n3);
            return n3;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ConcurrentModificationException();
        }
    }

    public static final int c(b b3, Object object, int n3) {
        int n4;
        k.e(b3, "<this>");
        int n5 = b3.e();
        if (n5 == 0) {
            return -1;
        }
        int n6 = d.b(b3, n3);
        if (n6 < 0 || k.a(object, b3.b()[n6])) {
            return n6;
        }
        for (n4 = n6 + 1; n4 < n5 && b3.c()[n4] == n3; ++n4) {
            if (!k.a(object, b3.b()[n4])) continue;
            return n4;
        }
        for (n5 = n6 - 1; n5 >= 0 && b3.c()[n5] == n3; --n5) {
            if (!k.a(object, b3.b()[n5])) continue;
            return n5;
        }
        return ~n4;
    }

    public static final int d(b b3) {
        k.e(b3, "<this>");
        return d.c(b3, null, 0);
    }
}

