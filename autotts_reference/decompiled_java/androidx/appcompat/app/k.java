/*
 * Decompiled with CFR 0.152.
 */
package androidx.appcompat.app;

import android.window.OnBackInvokedCallback;
import androidx.appcompat.app.AppCompatDelegateImpl;

public final class k
implements OnBackInvokedCallback {
    public final AppCompatDelegateImpl a;

    public /* synthetic */ k(AppCompatDelegateImpl appCompatDelegateImpl) {
        this.a = appCompatDelegateImpl;
    }

    public final void onBackInvoked() {
        this.a.A0();
    }
}

