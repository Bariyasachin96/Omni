/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 */
package f0;

import android.graphics.Typeface;
import f0.h;

public final class i
implements Runnable {
    public final h.e c;
    public final Typeface d;

    public /* synthetic */ i(h.e e3, Typeface typeface) {
        this.c = e3;
        this.d = typeface;
    }

    @Override
    public final void run() {
        h.e.a(this.c, this.d);
    }
}

