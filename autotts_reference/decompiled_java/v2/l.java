/*
 * Decompiled with CFR 0.152.
 */
package v2;

import v2.g;
import v2.q;

public final class l
extends g {
    public final g c;
    public final float d;

    public l(g g3, float f3) {
        this.c = g3;
        this.d = f3;
    }

    @Override
    public boolean a() {
        return this.c.a();
    }

    @Override
    public void b(float f3, float f4, float f5, q q3) {
        this.c.b(f3, f4 - this.d, f5, q3);
    }
}

