/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import androidx.transition.PathMotion;
import f0.k;
import m1.r;
import org.xmlpull.v1.XmlPullParser;

public class ArcMotion
extends PathMotion {
    public static final float g = (float)Math.tan(Math.toRadians(35.0));
    public float a = 0.0f;
    public float b = 0.0f;
    public float c = 70.0f;
    public float d = 0.0f;
    public float e = 0.0f;
    public float f = g;

    public ArcMotion(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, r.j);
        attributeSet = (XmlPullParser)attributeSet;
        this.d(k.f((TypedArray)context, (XmlPullParser)attributeSet, "minimumVerticalAngle", 1, 0.0f));
        this.c(k.f((TypedArray)context, (XmlPullParser)attributeSet, "minimumHorizontalAngle", 0, 0.0f));
        this.b(k.f((TypedArray)context, (XmlPullParser)attributeSet, "maximumAngle", 2, 70.0f));
        context.recycle();
    }

    public static float e(float f3) {
        if (!(f3 < 0.0f) && !(f3 > 90.0f)) {
            return (float)Math.tan(Math.toRadians(f3 / 2.0f));
        }
        throw new IllegalArgumentException("Arc must be between 0 and 90 degrees");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Path a(float f3, float f4, float f5, float f6) {
        Path path = new Path();
        path.moveTo(f3, f4);
        float f7 = f5 - f3;
        float f8 = f6 - f4;
        float f9 = f7 * f7 + f8 * f8;
        float f10 = (f3 + f5) / 2.0f;
        float f11 = (f4 + f6) / 2.0f;
        float f12 = 0.25f * f9;
        boolean bl = f4 > f6;
        if (Math.abs(f7) < Math.abs(f8)) {
            f8 = Math.abs(f9 / (f8 * 2.0f));
            if (bl) {
                f8 += f6;
                f7 = f5;
            } else {
                f8 += f4;
                f7 = f3;
            }
            f9 = this.e;
        } else {
            f7 = f9 / (f7 * 2.0f);
            if (bl) {
                f8 = f4;
                f7 += f3;
            } else {
                f7 = f5 - f7;
                f8 = f6;
            }
            f9 = this.d;
        }
        f9 = f12 * f9 * f9;
        float f13 = f10 - f7;
        float f14 = f11 - f8;
        f14 = f13 * f13 + f14 * f14;
        f13 = this.f;
        f12 = f12 * f13 * f13;
        if (!(f14 < f9)) {
            f9 = f14 > f12 ? f12 : 0.0f;
        }
        f13 = f8;
        f12 = f7;
        if (f9 != 0.0f) {
            f9 = (float)Math.sqrt(f9 / f14);
            f12 = (f7 - f10) * f9 + f10;
            f13 = f11 + f9 * (f8 - f11);
        }
        path.cubicTo((f3 + f12) / 2.0f, (f4 + f13) / 2.0f, (f12 + f5) / 2.0f, (f13 + f6) / 2.0f, f5, f6);
        return path;
    }

    public void b(float f3) {
        this.c = f3;
        this.f = ArcMotion.e(f3);
    }

    public void c(float f3) {
        this.a = f3;
        this.d = ArcMotion.e(f3);
    }

    public void d(float f3) {
        this.b = f3;
        this.e = ArcMotion.e(f3);
    }
}

