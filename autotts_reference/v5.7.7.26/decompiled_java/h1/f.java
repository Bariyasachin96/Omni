/*
 * Decompiled with CFR 0.152.
 */
package h1;

import androidx.profileinstaller.c;

public final class f
implements Runnable {
    public final c.c c;
    public final int d;
    public final Object e;

    public /* synthetic */ f(c.c c3, int n3, Object object) {
        this.c = c3;
        this.d = n3;
        this.e = object;
    }

    @Override
    public final void run() {
        androidx.profileinstaller.c.a(this.c, this.d, this.e);
    }
}

