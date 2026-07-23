/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 */
package a2;

import android.animation.TypeEvaluator;

public class c
implements TypeEvaluator {
    public static final c a = new c();

    public static c b() {
        return a;
    }

    public Integer a(float f3, Integer n3, Integer n4) {
        int n5 = n3;
        float f4 = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(n5 & 0xFF) / 255.0f;
        n5 = n4;
        float f8 = (float)(n5 >> 24 & 0xFF) / 255.0f;
        float f9 = (float)(n5 >> 16 & 0xFF) / 255.0f;
        float f10 = (float)(n5 >> 8 & 0xFF) / 255.0f;
        float f11 = (float)(n5 & 0xFF) / 255.0f;
        f5 = (float)Math.pow(f5, 2.2);
        f6 = (float)Math.pow(f6, 2.2);
        f7 = (float)Math.pow(f7, 2.2);
        f9 = (float)Math.pow(f9, 2.2);
        f10 = (float)Math.pow(f10, 2.2);
        f11 = (float)Math.pow(f11, 2.2);
        f5 = (float)Math.pow(f5 + (f9 - f5) * f3, 0.45454545454545453);
        f6 = (float)Math.pow(f6 + (f10 - f6) * f3, 0.45454545454545453);
        f11 = (float)Math.pow(f7 + f3 * (f11 - f7), 0.45454545454545453);
        n5 = Math.round((f4 + (f8 - f4) * f3) * 255.0f);
        return Math.round(f5 * 255.0f) << 16 | n5 << 24 | Math.round(f6 * 255.0f) << 8 | Math.round(f11 * 255.0f);
    }
}

