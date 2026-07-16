/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 */
package com.pairip.licensecheck;

import android.os.IBinder;
import com.pairip.licensecheck.LicenseClient;

public final class LicenseClient$$ExternalSyntheticLambda6
implements Runnable {
    public final LicenseClient f$0;
    public final IBinder f$1;

    public /* synthetic */ LicenseClient$$ExternalSyntheticLambda6(LicenseClient licenseClient, IBinder iBinder) {
        this.f$0 = licenseClient;
        this.f$1 = iBinder;
    }

    @Override
    public final void run() {
        LicenseClient.$r8$lambda$gb_vmUiJUmqdCloCudVdY_igh7I(this.f$0, this.f$1);
    }
}

