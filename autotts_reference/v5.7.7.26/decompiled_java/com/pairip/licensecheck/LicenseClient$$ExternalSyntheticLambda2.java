/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.pairip.licensecheck;

import android.content.Context;
import com.pairip.licensecheck.LicenseClient;

public final class LicenseClient$$ExternalSyntheticLambda2
implements Runnable {
    public final Context f$0;

    public /* synthetic */ LicenseClient$$ExternalSyntheticLambda2(Context context) {
        this.f$0 = context;
    }

    @Override
    public final void run() {
        LicenseClient.lambda$stopTrial$0(this.f$0);
    }
}

