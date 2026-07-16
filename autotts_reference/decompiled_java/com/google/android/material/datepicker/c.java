/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.datepicker;

import com.google.android.material.datepicker.e;

public final class c
implements Runnable {
    public final e c;
    public final String d;

    public /* synthetic */ c(e e3, String string) {
        this.c = e3;
        this.d = string;
    }

    @Override
    public final void run() {
        e.b(this.c, this.d);
    }
}

