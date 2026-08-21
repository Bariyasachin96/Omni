/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.appcompat.app;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

public abstract class h {
    public static /* bridge */ /* synthetic */ void a(OnBackInvokedDispatcher onBackInvokedDispatcher, OnBackInvokedCallback onBackInvokedCallback) {
        onBackInvokedDispatcher.unregisterOnBackInvokedCallback(onBackInvokedCallback);
    }
}

