/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.google.android.material.datepicker;

import android.view.View;
import com.google.android.material.datepicker.DateSelector;

public final class g
implements Runnable {
    public final View c;

    public /* synthetic */ g(View view) {
        this.c = view;
    }

    @Override
    public final void run() {
        DateSelector.c(this.c);
    }
}

