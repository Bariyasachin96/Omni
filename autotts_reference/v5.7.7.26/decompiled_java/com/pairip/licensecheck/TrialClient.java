/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.pairip.licensecheck;

import android.content.Context;
import com.pairip.licensecheck.LicenseClient;

public final class TrialClient {
    private TrialClient() {
    }

    public static void stopTrial(Context context) {
        LicenseClient.stopTrial(context);
    }
}

