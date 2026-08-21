/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.graphics.Rect
 */
package r2;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import java.util.Arrays;

public abstract class j {
    public r2.b a;
    public final Path b;
    public final Path c;
    public final PathMeasure d;
    public final Matrix e;

    public j(r2.b b3) {
        Path path;
        this.b = path = new Path();
        this.c = new Path();
        this.d = new PathMeasure(path, false);
        this.a = b3;
        this.e = new Matrix();
    }

    public abstract void a(Canvas var1, Rect var2, float var3, boolean var4, boolean var5);

    public abstract void b(Canvas var1, Paint var2, int var3, int var4);

    public abstract void c(Canvas var1, Paint var2, a var3, int var4);

    public abstract void d(Canvas var1, Paint var2, float var3, float var4, int var5, int var6, int var7);

    public abstract int e();

    public abstract int f();

    public abstract void g();

    public void h(Canvas canvas, Rect rect, float f3, boolean bl, boolean bl2) {
        this.a.h();
        this.a(canvas, rect, f3, bl, bl2);
    }

    public float i(float[] fArray) {
        return (float)Math.toDegrees(Math.atan2(fArray[1], fArray[0]));
    }

    public static class a {
        public float a;
        public float b;
        public int c;
        public int d;
        public float e = 1.0f;
        public float f;
        public float g;
        public boolean h;
    }

    public class b {
        public float[] a;
        public float[] b;
        public final Matrix c;
        public final j d;

        public b(j object) {
            this.d = object;
            this.a = new float[2];
            object = new float[2];
            this.b = (float[])object;
            object[0] = 1.0f;
            this.c = new Matrix();
        }

        public b(j j3, b b3) {
            this(j3, b3.a, b3.b);
        }

        public b(j object, float[] fArray, float[] fArray2) {
            this.d = object;
            object = new float[2];
            this.a = (float[])object;
            this.b = new float[2];
            System.arraycopy(fArray, 0, object, 0, 2);
            System.arraycopy(fArray2, 0, this.b, 0, 2);
            this.c = new Matrix();
        }

        public void a(float f3) {
            float[] fArray = this.b;
            float f4 = (float)(Math.atan2(fArray[1], fArray[0]) + 1.5707963267948966);
            fArray = this.a;
            double d3 = fArray[0];
            double d4 = f3;
            double d5 = f4;
            fArray[0] = (float)(d3 + Math.cos(d5) * d4);
            fArray = this.a;
            fArray[1] = (float)((double)fArray[1] + d4 * Math.sin(d5));
        }

        public void b(float f3) {
            float[] fArray = this.b;
            float f4 = (float)Math.atan2(fArray[1], fArray[0]);
            fArray = this.a;
            double d3 = fArray[0];
            double d4 = f3;
            double d5 = f4;
            fArray[0] = (float)(d3 + Math.cos(d5) * d4);
            fArray = this.a;
            fArray[1] = (float)((double)fArray[1] + d4 * Math.sin(d5));
        }

        public void c() {
            Arrays.fill(this.a, 0.0f);
            Arrays.fill(this.b, 0.0f);
            this.b[0] = 1.0f;
            this.c.reset();
        }

        public void d(float f3) {
            this.c.reset();
            this.c.setRotate(f3);
            this.c.mapPoints(this.a);
            this.c.mapPoints(this.b);
        }

        public void e(float f3, float f4) {
            float[] fArray = this.a;
            fArray[0] = fArray[0] * f3;
            fArray[1] = fArray[1] * f4;
            fArray = this.b;
            fArray[0] = fArray[0] * f3;
            fArray[1] = fArray[1] * f4;
        }

        public void f(float f3, float f4) {
            float[] fArray = this.a;
            fArray[0] = fArray[0] + f3;
            fArray[1] = fArray[1] + f4;
        }
    }
}

