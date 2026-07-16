/*
 * Decompiled with CFR 0.152.
 */
package androidx.appcompat.app;

import androidx.appcompat.app.d;

public final class e
implements Runnable {
    public final d.c c;
    public final Runnable d;

    public /* synthetic */ e(d.c c3, Runnable runnable) {
        this.c = c3;
        this.d = runnable;
    }

    @Override
    public final void run() {
        d.c.c(this.c, this.d);
    }
}

