/*
 * Decompiled with CFR 0.152.
 */
package f0;

import f0.h;

public final class j
implements Runnable {
    public final h.e c;
    public final int d;

    public /* synthetic */ j(h.e e3, int n3) {
        this.c = e3;
        this.d = n3;
    }

    @Override
    public final void run() {
        h.e.b(this.c, this.d);
    }
}

