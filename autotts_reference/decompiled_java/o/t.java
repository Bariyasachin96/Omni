/*
 * Decompiled with CFR 0.152.
 */
package o;

import o.s;
import o3.k;
import p.a;

public abstract class t {
    public static final Object a = new Object();

    public static final /* synthetic */ void a(s s3) {
        t.d(s3);
    }

    public static final /* synthetic */ Object b() {
        return a;
    }

    public static final Object c(s object, int n3) {
        k.e(object, "<this>");
        n3 = p.a.a(((s)object).d, ((s)object).f, n3);
        if (n3 >= 0 && (object = ((s)object).e[n3]) != a) {
            return object;
        }
        return null;
    }

    public static final void d(s s3) {
        int n3 = s3.f;
        int[] nArray = s3.d;
        Object[] objectArray = s3.e;
        int n4 = 0;
        for (int i3 = 0; i3 < n3; ++i3) {
            Object object = objectArray[i3];
            int n5 = n4;
            if (object != a) {
                if (i3 != n4) {
                    nArray[n4] = nArray[i3];
                    objectArray[n4] = object;
                    objectArray[i3] = null;
                }
                n5 = n4 + 1;
            }
            n4 = n5;
        }
        s3.c = false;
        s3.f = n4;
    }
}

