/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.google.android.material.bottomappbar;

import android.view.View;
import com.google.android.material.bottomappbar.BottomAppBar;

public final class c
implements Runnable {
    public final View c;

    public /* synthetic */ c(View view) {
        this.c = view;
    }

    @Override
    public final void run() {
        BottomAppBar.P(this.c);
    }
}

