/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.Choreographer$FrameCallback
 */
package x0;

import android.view.Choreographer;
import x0.c;

public final class g
implements Choreographer.FrameCallback {
    public final Runnable a;

    public /* synthetic */ g(Runnable runnable) {
        this.a = runnable;
    }

    public final void doFrame(long l3) {
        c.f.c(this.a, l3);
    }
}

