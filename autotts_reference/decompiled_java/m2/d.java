/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Matrix
 */
package m2;

import android.animation.ValueAnimator;
import android.graphics.Matrix;
import com.google.android.material.floatingactionbutton.a;

public final class d
implements ValueAnimator.AnimatorUpdateListener {
    public final a a;
    public final float b;
    public final float c;
    public final float d;
    public final float e;
    public final float f;
    public final float g;
    public final float h;
    public final Matrix i;

    public /* synthetic */ d(a a4, float f3, float f4, float f5, float f6, float f7, float f8, float f9, Matrix matrix) {
        this.a = a4;
        this.b = f3;
        this.c = f4;
        this.d = f5;
        this.e = f6;
        this.f = f7;
        this.g = f8;
        this.h = f9;
        this.i = matrix;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        com.google.android.material.floatingactionbutton.a.a(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, valueAnimator);
    }
}

