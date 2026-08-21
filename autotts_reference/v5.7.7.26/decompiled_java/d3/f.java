/*
 * Decompiled with CFR 0.152.
 */
package d3;

import d3.e;
import o3.k;

public abstract class f {
    public static final Object a(Throwable throwable) {
        k.e(throwable, "exception");
        return new e.b(throwable);
    }

    public static final void b(Object object) {
        if (!(object instanceof e.b)) {
            return;
        }
        throw ((e.b)object).c;
    }
}

