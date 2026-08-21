/*
 * Decompiled with CFR 0.152.
 */
package f1;

import f1.a;
import o3.g;
import o3.k;

public final class d
extends a {
    public d() {
        this(null, 1, null);
    }

    public d(a a4) {
        k.e(a4, "initialExtras");
        this.a().putAll(a4.a());
    }

    public /* synthetic */ d(a a4, int n3, g g3) {
        if ((n3 & 1) != 0) {
            a4 = a.a.b;
        }
        this(a4);
    }

    public final void b(a.b b3, Object object) {
        k.e(b3, "key");
        this.a().put(b3, object);
    }
}

