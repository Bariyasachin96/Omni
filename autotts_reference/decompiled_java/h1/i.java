/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package h1;

import android.content.Context;
import androidx.profileinstaller.ProfileInstallerInitializer;

public final class i
implements Runnable {
    public final Context c;

    public /* synthetic */ i(Context context) {
        this.c = context;
    }

    @Override
    public final void run() {
        ProfileInstallerInitializer.c(this.c);
    }
}

