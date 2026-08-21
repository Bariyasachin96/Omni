/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.util.AttributeSet
 *  org.xmlpull.v1.XmlPullParser
 */
package androidx.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import androidx.transition.PathMotion;
import f0.k;
import g0.d;
import m1.r;
import org.xmlpull.v1.XmlPullParser;

public class PatternPathMotion
extends PathMotion {
    public Path a;
    public final Path b;
    public final Matrix c;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PatternPathMotion(Context context, AttributeSet object) {
        Throwable throwable2;
        block3: {
            this.b = new Path();
            this.c = new Matrix();
            context = context.obtainStyledAttributes(object, r.k);
            try {
                object = k.i((TypedArray)context, (XmlPullParser)object, "patternPathData", 0);
                if (object == null) break block3;
                this.c(d.e((String)object));
            }
            catch (Throwable throwable2) {}
            context.recycle();
            return;
        }
        super("pathData must be supplied for patternPathMotion");
        throw object;
        context.recycle();
        throw throwable2;
    }

    public static float b(float f3, float f4) {
        return (float)Math.sqrt(f3 * f3 + f4 * f4);
    }

    @Override
    public Path a(float f3, float f4, float f5, float f6) {
        float f7 = PatternPathMotion.b(f5 -= f3, f6 -= f4);
        double d3 = Math.atan2(f6, f5);
        this.c.setScale(f7, f7);
        this.c.postRotate((float)Math.toDegrees(d3));
        this.c.postTranslate(f3, f4);
        Path path = new Path();
        this.b.transform(this.c, path);
        return path;
    }

    public void c(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float f3 = pathMeasure.getLength();
        float[] fArray = new float[2];
        pathMeasure.getPosTan(f3, fArray, null);
        float f4 = fArray[0];
        f3 = fArray[1];
        pathMeasure.getPosTan(0.0f, fArray, null);
        float f5 = fArray[0];
        float f6 = fArray[1];
        if (f5 == f4 && f6 == f3) {
            throw new IllegalArgumentException("pattern must not end at the starting point");
        }
        this.c.setTranslate(-f5, -f6);
        f5 = f4 - f5;
        f6 = f3 - f6;
        f3 = 1.0f / PatternPathMotion.b(f5, f6);
        this.c.postScale(f3, f3);
        double d3 = Math.atan2(f6, f5);
        this.c.postRotate((float)Math.toDegrees(-d3));
        path.transform(this.c, this.b);
        this.a = path;
    }
}

