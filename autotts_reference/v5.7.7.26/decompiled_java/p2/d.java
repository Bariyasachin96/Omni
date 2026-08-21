/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.window.OnBackInvokedDispatcher
 */
package p2;

import android.view.View;
import android.window.OnBackInvokedDispatcher;

public abstract class d {
    public static /* bridge */ /* synthetic */ OnBackInvokedDispatcher a(View view) {
        return view.findOnBackInvokedDispatcher();
    }
}

