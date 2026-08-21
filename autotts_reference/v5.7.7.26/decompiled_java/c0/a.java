/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 */
package c0;

import android.app.Activity;
import c0.b;

public final class a
implements Runnable {
    public final Activity c;

    public /* synthetic */ a(Activity activity) {
        this.c = activity;
    }

    @Override
    public final void run() {
        b.j(this.c);
    }
}

