/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 */
package com.pairip.application;

import android.content.Context;
import com.pairip.licensecheck.LicenseClient;

public class Application
extends android.app.Application {
    protected void attachBaseContext(Context context) {
        LicenseClient.checkLicense(context);
        this.attachBaseContext(context);
    }
}

