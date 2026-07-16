/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TypeEvaluator
 *  android.graphics.Matrix
 */
package a2;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

public abstract class g
implements TypeEvaluator {
    public final float[] a = new float[9];
    public final float[] b = new float[9];
    public final Matrix c = new Matrix();

    public Matrix a(float f3, Matrix object, Matrix matrix) {
        object.getValues(this.a);
        matrix.getValues(this.b);
        for (int i3 = 0; i3 < 9; ++i3) {
            object = this.b;
            Matrix matrix2 = object[i3];
            float f4 = this.a[i3];
            object[i3] = (Matrix)(f4 + (matrix2 - f4) * f3);
        }
        this.c.setValues(this.b);
        return this.c;
    }
}

