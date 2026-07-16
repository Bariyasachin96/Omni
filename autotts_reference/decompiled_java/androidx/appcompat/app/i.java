/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.window.OnBackInvokedDispatcher
 */
package androidx.appcompat.app;

import android.app.Activity;
import android.window.OnBackInvokedDispatcher;

public abstract class i {
    public static /* bridge */ /* synthetic */ OnBackInvokedDispatcher a(Activity activity) {
        return activity.getOnBackInvokedDispatcher();
    }
}

