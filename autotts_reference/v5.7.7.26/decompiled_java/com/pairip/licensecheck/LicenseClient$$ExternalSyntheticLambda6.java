/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.pairip.licensecheck;

import android.content.Context;
import com.pairip.licensecheck.LicenseClient;

public final class LicenseClient$$ExternalSyntheticLambda6
implements Runnable {
    public final Context f$0;

    public /* synthetic */ LicenseClient$$ExternalSyntheticLambda6(Context context) {
        this.f$0 = context;
    }

    @Override
    public final void run() {
        LicenseClient.lambda$checkLicense$0(this.f$0);
    }
}

