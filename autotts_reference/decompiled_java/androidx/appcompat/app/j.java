/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.appcompat.app;

import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

public abstract class j {
    public static /* bridge */ /* synthetic */ void a(OnBackInvokedDispatcher onBackInvokedDispatcher, int n3, OnBackInvokedCallback onBackInvokedCallback) {
        onBackInvokedDispatcher.registerOnBackInvokedCallback(n3, onBackInvokedCallback);
    }
}

