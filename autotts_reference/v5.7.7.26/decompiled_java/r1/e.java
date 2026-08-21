/*
 * Decompiled with CFR 0.152.
 */
package r1;

import java.util.Arrays;
import r1.d;

public final class e
extends d {
    public final byte[] e;

    public e(byte[] byArray) {
        super(Arrays.copyOfRange(byArray, 0, 25));
        this.e = byArray;
    }

    @Override
    public final byte[] j() {
        return this.e;
    }
}

