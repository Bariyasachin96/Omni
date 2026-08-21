/*
 * Decompiled with CFR 0.152.
 */
package com.google.android.material.internal;

public abstract class h {
    public static void a(float f3, float[] fArray) {
        if (f3 <= 0.5f) {
            fArray[0] = 1.0f - f3 * 2.0f;
            fArray[1] = 0.0f;
            return;
        }
        fArray[0] = 0.0f;
        fArray[1] = f3 * 2.0f - 1.0f;
    }
}

