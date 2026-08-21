/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 */
package com.pairip.licensecheck;

import android.os.Handler;
import com.pairip.licensecheck.LicenseClient;

public final class LicenseClient$$ExternalSyntheticLambda5
implements LicenseClient.ImmediateTaskExecutor {
    public final Handler f$0;

    public /* synthetic */ LicenseClient$$ExternalSyntheticLambda5(Handler handler) {
        this.f$0 = handler;
    }

    @Override
    public final void run(Runnable runnable) {
        this.f$0.post(runnable);
    }
}

