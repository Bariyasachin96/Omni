/*
 * Decompiled with CFR 0.152.
 */
package h1;

import androidx.profileinstaller.b;

public final class a
implements Runnable {
    public final b c;
    public final int d;
    public final Object e;

    public /* synthetic */ a(b b3, int n3, Object object) {
        this.c = b3;
        this.d = n3;
        this.e = object;
    }

    @Override
    public final void run() {
        b.a(this.c, this.d, this.e);
    }
}

