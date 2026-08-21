/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.datepicker;

import com.google.android.material.datepicker.e;

public final class d
implements Runnable {
    public final e c;
    public final long d;

    public /* synthetic */ d(e e3, long l3) {
        this.c = e3;
        this.d = l3;
    }

    @Override
    public final void run() {
        e.a(this.c, this.d);
    }
}

