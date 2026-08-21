/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 */
package m1;

import android.animation.TypeEvaluator;

public class c
implements TypeEvaluator {
    public float[] a;

    public c(float[] fArray) {
        this.a = fArray;
    }

    public float[] a(float f3, float[] fArray, float[] fArray2) {
        float[] fArray3;
        float[] fArray4 = fArray3 = this.a;
        if (fArray3 == null) {
            fArray4 = new float[fArray.length];
        }
        for (int i3 = 0; i3 < fArray4.length; ++i3) {
            float f4 = fArray[i3];
            fArray4[i3] = f4 + (fArray2[i3] - f4) * f3;
        }
        return fArray4;
    }
}

