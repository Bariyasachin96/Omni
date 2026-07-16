/*
 * Decompiled with CFR 0.152.
 */
package p3;

import java.util.Random;
import p3.c;

public abstract class a
extends c {
    @Override
    public int b(int n3) {
        return this.c().nextInt(n3);
    }

    public abstract Random c();
}

