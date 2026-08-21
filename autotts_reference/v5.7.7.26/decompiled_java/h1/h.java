/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package h1;

import android.content.Context;
import androidx.profileinstaller.ProfileInstallerInitializer;

public final class h
implements Runnable {
    public final Context c;

    public /* synthetic */ h(Context context) {
        this.c = context;
    }

    @Override
    public final void run() {
        ProfileInstallerInitializer.d(this.c);
    }
}

