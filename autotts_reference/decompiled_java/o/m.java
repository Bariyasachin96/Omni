/*
 * Decompiled with CFR 0.152.
 */
package o;

import java.util.Arrays;
import o.f;
import o3.k;

public final class m
extends f {
    public m(int n3) {
        super(n3, null);
    }

    public final boolean h(float f3) {
        this.i(this.b + 1);
        float[] fArray = this.a;
        int n3 = this.b;
        fArray[n3] = f3;
        this.b = n3 + 1;
        return true;
    }

    public final void i(int n3) {
        float[] fArray = this.a;
        if (fArray.length < n3) {
            fArray = Arrays.copyOf(fArray, Math.max(n3, fArray.length * 3 / 2));
            k.d(fArray, "copyOf(this, newSize)");
            this.a = fArray;
        }
    }
}

