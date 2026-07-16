/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.WindowInsetsController
 *  android.view.WindowInsetsController$OnControllableInsetsChangedListener
 */
package o0;

import android.view.WindowInsetsController;
import java.util.concurrent.atomic.AtomicBoolean;
import o0.l0;

public final class s0
implements WindowInsetsController.OnControllableInsetsChangedListener {
    public final AtomicBoolean a;

    public /* synthetic */ s0(AtomicBoolean atomicBoolean) {
        this.a = atomicBoolean;
    }

    public final void onControllableInsetsChanged(WindowInsetsController windowInsetsController, int n3) {
        l0.b.d(this.a, windowInsetsController, n3);
    }
}

