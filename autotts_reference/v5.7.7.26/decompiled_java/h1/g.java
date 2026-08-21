/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.Choreographer$FrameCallback
 */
package h1;

import android.content.Context;
import android.view.Choreographer;
import androidx.profileinstaller.ProfileInstallerInitializer;

public final class g
implements Choreographer.FrameCallback {
    public final ProfileInstallerInitializer a;
    public final Context b;

    public /* synthetic */ g(ProfileInstallerInitializer profileInstallerInitializer, Context context) {
        this.a = profileInstallerInitializer;
        this.b = context;
    }

    public final void doFrame(long l3) {
        ProfileInstallerInitializer.e(this.a, this.b, l3);
    }
}

