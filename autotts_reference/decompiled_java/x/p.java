/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package x;

import android.view.View;
import androidx.constraintlayout.motion.widget.c;

public final class p
implements Runnable {
    public final c c;
    public final View[] d;

    public /* synthetic */ p(c c3, View[] viewArray) {
        this.c = c3;
        this.d = viewArray;
    }

    @Override
    public final void run() {
        androidx.constraintlayout.motion.widget.c.a(this.c, this.d);
    }
}

