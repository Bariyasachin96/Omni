/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.google.android.material.internal;

import android.view.View;
import com.google.android.material.internal.c0;

public final class b0
implements Runnable {
    public final View c;
    public final boolean d;

    public /* synthetic */ b0(View view, boolean bl) {
        this.c = view;
        this.d = bl;
    }

    @Override
    public final void run() {
        c0.a(this.c, this.d);
    }
}

