/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Rect
 *  android.graphics.RectF
 */
package n2;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import c1.m;
import c1.u;
import c1.x;
import com.google.android.material.loadingindicator.LoadingIndicatorSpec;
import v2.k;

public class c {
    public static final u[] d;
    public static final m[] e;
    public LoadingIndicatorSpec a;
    public final Path b = new Path();
    public final Matrix c = new Matrix();

    static {
        m[] mArray = k.Q(k.E, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u u3 = k.Q(k.y, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u u4 = k.Q(k.r, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u u5 = k.Q(k.n, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u u6 = k.Q(k.t, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u[] uArray = k.Q(k.v, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u u7 = k.Q(k.m, true, new RectF(-1.0f, -1.0f, 1.0f, 1.0f));
        u[] uArray2 = new u[]{mArray, u3, u4, u5, u6, uArray, u7};
        d = uArray2;
        e = new m[uArray2.length];
        int n3 = 0;
        while (n3 < (uArray = d).length) {
            mArray = e;
            u3 = uArray[n3];
            int n4 = n3 + 1;
            mArray[n3] = new m(u3, uArray[n4 % uArray.length]);
            n3 = n4;
        }
    }

    public c(LoadingIndicatorSpec loadingIndicatorSpec) {
        this.a = loadingIndicatorSpec;
    }

    public void a(Canvas canvas, Rect rect) {
        canvas.translate((float)rect.centerX(), (float)rect.centerY());
        if (this.a.a) {
            float f3 = Math.min((float)rect.width() / (float)this.e(), (float)rect.height() / (float)this.d());
            canvas.scale(f3, f3);
        }
        canvas.clipRect((float)(-this.e()) / 2.0f, (float)(-this.d()) / 2.0f, (float)this.e() / 2.0f, (float)this.d() / 2.0f);
        canvas.rotate(-90.0f);
    }

    public void b(Canvas canvas, Paint paint, int n3, int n4) {
        LoadingIndicatorSpec loadingIndicatorSpec = this.a;
        float f3 = (float)Math.min(loadingIndicatorSpec.c, loadingIndicatorSpec.d) / 2.0f;
        paint.setColor(h2.a.a(n3, n4));
        paint.setStyle(Paint.Style.FILL);
        loadingIndicatorSpec = this.a;
        n3 = loadingIndicatorSpec.c;
        float f4 = (float)(-n3) / 2.0f;
        n4 = loadingIndicatorSpec.d;
        canvas.drawRoundRect(new RectF(f4, (float)(-n4) / 2.0f, (float)n3 / 2.0f, (float)n4 / 2.0f), f3, f3, paint);
    }

    public void c(Canvas canvas, Paint paint, a a4, int n3) {
        paint.setColor(h2.a.a(a4.a, n3));
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        canvas.rotate(a4.c);
        this.b.rewind();
        n3 = (int)Math.floor(a4.b);
        m[] mArray = e;
        int n4 = o2.a.d(n3, mArray.length);
        float f3 = a4.b;
        float f4 = n3;
        x.b(mArray[n4], f3 - f4, this.b);
        a4 = this.c;
        n3 = this.a.b;
        a4.setScale((float)n3 / 2.0f, (float)n3 / 2.0f);
        this.b.transform(this.c);
        canvas.drawPath(this.b, paint);
        canvas.restore();
    }

    public int d() {
        LoadingIndicatorSpec loadingIndicatorSpec = this.a;
        return Math.max(loadingIndicatorSpec.c, loadingIndicatorSpec.b);
    }

    public int e() {
        LoadingIndicatorSpec loadingIndicatorSpec = this.a;
        return Math.max(loadingIndicatorSpec.d, loadingIndicatorSpec.b);
    }

    public static class a {
        public int a;
        public float b;
        public float c;
    }
}

