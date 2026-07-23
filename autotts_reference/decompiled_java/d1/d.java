/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.animation.Interpolator
 */
package d1;

import android.view.animation.Interpolator;

public abstract class d
implements Interpolator {
    public final float[] a;
    public final float b;

    public d(float[] fArray) {
        this.a = fArray;
        this.b = 1.0f / (float)(fArray.length - 1);
    }

    public float getInterpolation(float f3) {
        if (f3 >= 1.0f) {
            return 1.0f;
        }
        if (f3 <= 0.0f) {
            return 0.0f;
        }
        float[] fArray = this.a;
        int n3 = Math.min((int)((float)(fArray.length - 1) * f3), fArray.length - 2);
        float f4 = n3;
        float f5 = this.b;
        f3 = (f3 - f4 * f5) / f5;
        fArray = this.a;
        f4 = fArray[n3];
        return f4 + f3 * (fArray[n3 + 1] - f4);
    }
}

