/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 */
package r2;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.google.android.material.internal.z;
import h2.a;
import s2.c;
import z1.e;
import z1.m;

public abstract class b {
    public int a;
    public int b;
    public float c;
    public boolean d;
    public int[] e = new int[0];
    public int f;
    public int g;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public int m;
    public float n;

    public b(Context context, AttributeSet attributeSet, int n3, int n4) {
        int n5 = context.getResources().getDimensionPixelSize(z1.e.mtrl_progress_track_thickness);
        TypedArray typedArray = z.i(context, attributeSet, z1.m.BaseProgressIndicator, n3, n4, new int[0]);
        this.a = s2.c.d(context, typedArray, z1.m.BaseProgressIndicator_trackThickness, n5);
        attributeSet = typedArray.peekValue(z1.m.BaseProgressIndicator_trackCornerRadius);
        if (attributeSet != null) {
            n3 = attributeSet.type;
            if (n3 == 5) {
                this.b = Math.min(TypedValue.complexToDimensionPixelSize((int)attributeSet.data, (DisplayMetrics)typedArray.getResources().getDisplayMetrics()), this.a / 2);
                this.d = false;
            } else if (n3 == 6) {
                this.c = Math.min(attributeSet.getFraction(1.0f, 1.0f), 0.5f);
                this.d = true;
            }
        }
        this.g = typedArray.getInt(z1.m.BaseProgressIndicator_showAnimationBehavior, 0);
        this.h = typedArray.getInt(z1.m.BaseProgressIndicator_hideAnimationBehavior, 0);
        this.i = typedArray.getDimensionPixelSize(z1.m.BaseProgressIndicator_indicatorTrackGapSize, 0);
        n3 = Math.abs(typedArray.getDimensionPixelSize(z1.m.BaseProgressIndicator_wavelength, 0));
        this.j = Math.abs(typedArray.getDimensionPixelSize(z1.m.BaseProgressIndicator_wavelengthDeterminate, n3));
        this.k = Math.abs(typedArray.getDimensionPixelSize(z1.m.BaseProgressIndicator_wavelengthIndeterminate, n3));
        this.l = Math.abs(typedArray.getDimensionPixelSize(z1.m.BaseProgressIndicator_waveAmplitude, 0));
        this.m = typedArray.getDimensionPixelSize(z1.m.BaseProgressIndicator_waveSpeed, 0);
        this.n = typedArray.getFloat(z1.m.BaseProgressIndicator_indeterminateAnimatorDurationScale, 1.0f);
        this.e(context, typedArray);
        this.f(context, typedArray);
        typedArray.recycle();
    }

    public int a() {
        if (this.d) {
            return (int)((float)this.a * this.c);
        }
        return this.b;
    }

    public boolean b(boolean bl) {
        return this.l > 0 && (!bl && this.k > 0 || bl && this.j > 0);
    }

    public boolean c() {
        return this.h != 0;
    }

    public boolean d() {
        return this.g != 0;
    }

    public final void e(Context object, TypedArray typedArray) {
        int n3 = z1.m.BaseProgressIndicator_indicatorColor;
        if (!typedArray.hasValue(n3)) {
            this.e = new int[]{h2.a.b(object, c.a.colorPrimary, -1)};
            return;
        }
        if (typedArray.peekValue((int)n3).type != 1) {
            this.e = new int[]{typedArray.getColor(n3, -1)};
            return;
        }
        object = object.getResources().getIntArray(typedArray.getResourceId(n3, -1));
        this.e = (int[])object;
        if (((Context)object).length != 0) {
            return;
        }
        throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
    }

    public final void f(Context context, TypedArray typedArray) {
        int n3 = z1.m.BaseProgressIndicator_trackColor;
        if (typedArray.hasValue(n3)) {
            this.f = typedArray.getColor(n3, -1);
            return;
        }
        this.f = this.e[0];
        context = context.getTheme().obtainStyledAttributes(new int[]{0x1010033});
        float f3 = context.getFloat(0, 0.2f);
        context.recycle();
        n3 = (int)(f3 * 255.0f);
        this.f = h2.a.a(this.f, n3);
    }

    public boolean g() {
        return this.d && this.c == 0.5f;
    }

    public void h() {
        if (this.i >= 0) {
            return;
        }
        throw new IllegalArgumentException("indicatorTrackGapSize must be >= 0.");
    }
}

