/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.vending.licensing;

import com.google.android.vending.licensing.DeviceLimiter;

public class NullDeviceLimiter
implements DeviceLimiter {
    @Override
    public int a(String string) {
        return 256;
    }
}

