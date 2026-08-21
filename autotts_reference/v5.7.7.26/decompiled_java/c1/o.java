/*
 * Decompiled with CFR 0.152.
 */
package c1;

import c1.d;
import c1.q;
import o3.k;

public final class o
extends d {
    public o() {
        super(null, 1, null);
    }

    public final void q(q q3) {
        k.e(q3, "f");
        this.r(q3, 0);
        this.r(q3, 2);
        this.r(q3, 4);
        this.r(q3, 6);
    }

    public final void r(q q3, int n3) {
        float f3 = this.j()[n3];
        float[] fArray = this.j();
        int n4 = n3 + 1;
        long l3 = q3.a(f3, fArray[n4]);
        this.j()[n3] = Float.intBitsToFloat((int)(l3 >> 32));
        this.j()[n4] = Float.intBitsToFloat((int)(l3 & 0xFFFFFFFFL));
    }
}

