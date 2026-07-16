/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package androidx.appcompat.app;

import android.content.Context;
import androidx.appcompat.app.d;

public final class c
implements Runnable {
    public final Context c;

    public /* synthetic */ c(Context context) {
        this.c = context;
    }

    @Override
    public final void run() {
        d.c(this.c);
    }
}

