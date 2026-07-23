/*
 * Decompiled with CFR 0.152.
 */
package v2;

import v2.g;
import v2.q;

public final class h
extends g {
    public final float c;

    public h(float f3) {
        this.c = f3 - 0.001f;
    }

    @Override
    public boolean a() {
        return true;
    }

    @Override
    public void b(float f3, float f4, float f5, q q3) {
        f5 = (float)((double)this.c * Math.sqrt(2.0) / 2.0);
        f3 = (float)Math.sqrt(Math.pow(this.c, 2.0) - Math.pow(f5, 2.0));
        q3.n(f4 - f5, (float)(-((double)this.c * Math.sqrt(2.0) - (double)this.c)) + f3);
        q3.m(f4, (float)(-((double)this.c * Math.sqrt(2.0) - (double)this.c)));
        q3.m(f4 + f5, (float)(-((double)this.c * Math.sqrt(2.0) - (double)this.c)) + f3);
    }
}

