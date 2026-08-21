/*
 * Decompiled with CFR 0.152.
 */
package androidx.activity;

import android.window.OnBackInvokedCallback;
import androidx.activity.OnBackPressedDispatcher;
import n3.a;

public final class p
implements OnBackInvokedCallback {
    public final a a;

    public /* synthetic */ p(a a4) {
        this.a = a4;
    }

    public final void onBackInvoked() {
        OnBackPressedDispatcher.f.a(this.a);
    }
}

