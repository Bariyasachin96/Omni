/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 */
package com.google.android.material.internal;

import android.animation.TimeInterpolator;

public class t
implements TimeInterpolator {
    public final TimeInterpolator a;

    public t(TimeInterpolator timeInterpolator) {
        this.a = timeInterpolator;
    }

    public static TimeInterpolator a(boolean bl, TimeInterpolator timeInterpolator) {
        if (bl) {
            return timeInterpolator;
        }
        return new t(timeInterpolator);
    }

    public float getInterpolation(float f3) {
        return 1.0f - this.a.getInterpolation(f3);
    }
}

