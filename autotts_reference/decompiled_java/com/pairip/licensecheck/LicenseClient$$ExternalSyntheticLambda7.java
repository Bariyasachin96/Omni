/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.pairip.licensecheck;

import android.os.Bundle;
import com.pairip.licensecheck.LicenseClient;
import com.pairip.licensecheck.RepeatedCheckMetadata;

public final class LicenseClient$$ExternalSyntheticLambda7
implements Runnable {
    public final LicenseClient f$0;
    public final RepeatedCheckMetadata f$1;
    public final Bundle f$2;

    public /* synthetic */ LicenseClient$$ExternalSyntheticLambda7(LicenseClient licenseClient, RepeatedCheckMetadata repeatedCheckMetadata, Bundle bundle) {
        this.f$0 = licenseClient;
        this.f$1 = repeatedCheckMetadata;
        this.f$2 = bundle;
    }

    @Override
    public final void run() {
        LicenseClient.$r8$lambda$ot_XkRbEJeEFG1Hy_d3H6N4DX_I(this.f$0, this.f$1, this.f$2);
    }
}

