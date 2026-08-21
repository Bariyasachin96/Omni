/*
 * Decompiled with CFR 0.152.
 */
package androidx.appcompat.widget;

import android.window.OnBackInvokedCallback;

public final class p0
implements OnBackInvokedCallback {
    public final Runnable a;

    public /* synthetic */ p0(Runnable runnable) {
        this.a = runnable;
    }

    public final void onBackInvoked() {
        this.a.run();
    }
}

