/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.b;
import c1.d;
import c1.i;

public final class a
implements i {
    public final d a;
    public final b b;
    public final float c;
    public final float d;

    public /* synthetic */ a(d d3, b b3, float f3, float f4) {
        this.a = d3;
        this.b = b3;
        this.c = f3;
        this.d = f4;
    }

    @Override
    public final float a(float f3) {
        return c1.b.c(this.a, this.b, this.c, this.d, f3);
    }
}

