/*
 * Decompiled with CFR 0.152.
 */
package b1;

import b1.c;
import b1.g;

public final class b
implements Runnable {
    public final String c;
    public final g d;

    public /* synthetic */ b(String string, g g3) {
        this.c = string;
        this.d = g3;
    }

    @Override
    public final void run() {
        b1.c.a(this.c, this.d);
    }
}

