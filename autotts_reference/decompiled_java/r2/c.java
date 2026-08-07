/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.os.Build$VERSION
 *  android.util.Pair
 */
package r2;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.Pair;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import java.util.ArrayList;
import o2.a;
import r2.b;
import r2.j;

public final class c
extends j {
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k;
    public int l;
    public float m;
    public boolean n;
    public float o;
    public final RectF p = new RectF();
    public final Pair q = new Pair((Object)new j.b(this), (Object)new j.b(this));

    public c(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
    }

    @Override
    public void a(Canvas object, Rect rect, float f3, boolean bl, boolean bl2) {
        float f4 = (float)rect.width() / (float)this.f();
        float f5 = (float)rect.height() / (float)this.e();
        b b3 = this.a;
        float f6 = (float)((CircularProgressIndicatorSpec)b3).p / 2.0f + (float)((CircularProgressIndicatorSpec)b3).q;
        object.translate(f6 * f4 + (float)rect.left, f6 * f5 + (float)rect.top);
        object.rotate(-90.0f);
        object.scale(f4, f5);
        if (((CircularProgressIndicatorSpec)this.a).r != 0) {
            object.scale(1.0f, -1.0f);
            if (Build.VERSION.SDK_INT == 29) {
                object.rotate(0.1f);
            }
        }
        f4 = -f6;
        object.clipRect(f4, f4, f6, f6);
        object = this.a;
        this.f = (float)((CircularProgressIndicatorSpec)object).a * f3;
        this.g = (float)Math.min(((CircularProgressIndicatorSpec)object).a / 2, ((CircularProgressIndicatorSpec)object).a()) * f3;
        object = this.a;
        this.h = (float)((CircularProgressIndicatorSpec)object).l * f3;
        this.i = f6 = (float)(((CircularProgressIndicatorSpec)object).p - ((CircularProgressIndicatorSpec)object).a) / 2.0f;
        if (bl || bl2) {
            f4 = (1.0f - f3) * (float)((CircularProgressIndicatorSpec)object).a / 2.0f;
            if (bl && ((CircularProgressIndicatorSpec)object).g == 2 || bl2 && ((CircularProgressIndicatorSpec)object).h == 1) {
                this.i = f6 + f4;
            } else if (bl && ((CircularProgressIndicatorSpec)object).g == 1 || bl2 && ((CircularProgressIndicatorSpec)object).h == 2) {
                this.i = f6 - f4;
            }
        }
        if (bl2 && ((CircularProgressIndicatorSpec)object).h == 3) {
            this.o = f3;
            return;
        }
        this.o = 1.0f;
    }

    @Override
    public void b(Canvas canvas, Paint paint, int n3, int n4) {
    }

    @Override
    public void c(Canvas canvas, Paint paint, j.a a4, int n3) {
        int n4 = h2.a.a(a4.c, n3);
        canvas.save();
        canvas.rotate(a4.g);
        this.n = a4.h;
        float f3 = a4.a;
        float f4 = a4.b;
        n3 = a4.d;
        this.m(canvas, paint, f3, f4, n4, n3, n3, a4.e, a4.f, true);
        canvas.restore();
    }

    @Override
    public void d(Canvas canvas, Paint paint, float f3, float f4, int n3, int n4, int n5) {
        n3 = h2.a.a(n3, n4);
        this.n = false;
        this.m(canvas, paint, f3, f4, n3, n5, n5, 0.0f, 0.0f, false);
    }

    @Override
    public int e() {
        return this.p();
    }

    @Override
    public int f() {
        return this.p();
    }

    @Override
    public void g() {
        this.b.rewind();
        this.b.moveTo(1.0f, 0.0f);
        for (int i3 = 0; i3 < 2; ++i3) {
            this.b.cubicTo(1.0f, 0.5522848f, 0.5522848f, 1.0f, 0.0f, 1.0f);
            this.b.cubicTo(-0.5522848f, 1.0f, -1.0f, 0.5522848f, -1.0f, 0.0f);
            this.b.cubicTo(-1.0f, -0.5522848f, -0.5522848f, -1.0f, 0.0f, -1.0f);
            this.b.cubicTo(0.5522848f, -1.0f, 1.0f, -0.5522848f, 1.0f, 0.0f);
        }
        this.e.reset();
        Matrix matrix = this.e;
        float f3 = this.i;
        matrix.setScale(f3, f3);
        this.b.transform(this.e);
        if (((CircularProgressIndicatorSpec)this.a).b(this.n)) {
            this.d.setPath(this.b, false);
            this.l(this.d, this.b, this.k);
        }
        this.d.setPath(this.b, false);
    }

    public final void j(Path path, j.b object, j.b b3) {
        Object object2 = this.j / 2.0f * 0.48f;
        Object object3 = new j.b(this, (j.b)object);
        object = new j.b(this, b3);
        ((j.b)object3).b((float)object2);
        ((j.b)object).b(-object2);
        object3 = ((j.b)object3).a;
        Object object4 = object3[0];
        Object object5 = object3[1];
        object = ((j.b)object).a;
        Object object6 = object[0];
        object2 = object[1];
        object = b3.a;
        path.cubicTo((float)object4, (float)object5, (float)object6, object2, (float)object[0], (float)object[1]);
    }

    public final void k(PathMeasure pathMeasure, Path path, Pair object, float f3, float f4, float f5, float f6) {
        float f7 = this.h * f5;
        int n3 = this.n ? ((CircularProgressIndicatorSpec)this.a).j : ((CircularProgressIndicatorSpec)this.a).k;
        f5 = this.i;
        if (f5 != this.m || pathMeasure == this.d && (f7 != this.k || n3 != this.l)) {
            this.k = f7;
            this.l = n3;
            this.m = f5;
            this.g();
        }
        path.rewind();
        f7 = 0.0f;
        float f8 = j0.a.a(f4, 0.0f, 1.0f);
        f5 = f3;
        f4 = f7;
        if (((CircularProgressIndicatorSpec)this.a).b(this.n)) {
            f4 = f6 / (float)((double)this.i * (Math.PI * 2) / (double)this.j);
            f5 = f3 + f4;
            f4 = 0.0f - f4 * 360.0f;
        }
        f3 = pathMeasure.getLength() * (f5 %= 1.0f) / 2.0f;
        f5 = (f5 + f8) * pathMeasure.getLength() / 2.0f;
        pathMeasure.getSegment(f3, f5, path, true);
        j.b b3 = (j.b)((Pair)object).first;
        b3.c();
        pathMeasure.getPosTan(f3, b3.a, b3.b);
        object = (j.b)((Pair)object).second;
        ((j.b)object).c();
        pathMeasure.getPosTan(f5, ((j.b)object).a, ((j.b)object).b);
        this.e.reset();
        this.e.setRotate(f4);
        b3.d(f4);
        ((j.b)object).d(f4);
        path.transform(this.e);
    }

    public final void l(PathMeasure object, Path path, float f3) {
        Object object2;
        path.rewind();
        float f4 = object.getLength();
        int n3 = this.n ? ((CircularProgressIndicatorSpec)this.a).j : ((CircularProgressIndicatorSpec)this.a).k;
        int n4 = Math.max(3, (int)(f4 / (float)n3 / 2.0f)) * 2;
        this.j = f4 / (float)n4;
        ArrayList<Object> arrayList = new ArrayList<Object>();
        for (n3 = 0; n3 < n4; ++n3) {
            j.b b3 = new j.b(this);
            float f5 = this.j;
            f4 = n3;
            object.getPosTan(f5 * f4, b3.a, b3.b);
            object2 = new j.b(this);
            f5 = this.j;
            object.getPosTan(f4 * f5 + f5 / 2.0f, ((j.b)object2).a, ((j.b)object2).b);
            arrayList.add(b3);
            ((j.b)object2).a(f3 * 2.0f);
            arrayList.add(object2);
        }
        arrayList.add((j.b)arrayList.get(0));
        object = (j.b)arrayList.get(0);
        object2 = object.a;
        f3 = object2[0];
        path.moveTo(f3, object2[1]);
        for (n3 = 1; n3 < arrayList.size(); ++n3) {
            object2 = (j.b)arrayList.get(n3);
            this.j(path, (j.b)object, (j.b)object2);
            object = object2;
        }
    }

    public final void m(Canvas canvas, Paint paint, float f3, float f4, int n3, int n4, int n5, float f5, float f6, boolean bl) {
        float f7;
        f4 = f4 >= f3 ? (f4 -= f3) : f4 + 1.0f - f3;
        f3 = f7 = f3 % 1.0f;
        if (f7 < 0.0f) {
            f3 = f7 + 1.0f;
        }
        if (this.o < 1.0f && (f7 = f3 + f4) > 1.0f) {
            this.m(canvas, paint, f3, 1.0f, n3, n4, 0, f5, f6, bl);
            this.m(canvas, paint, 1.0f, f7, n3, 0, n5, f5, f6, bl);
            return;
        }
        float f8 = (float)Math.toDegrees(this.g / this.i);
        float f9 = f4 - 0.99f;
        f7 = f4;
        float f10 = f3;
        if (f9 >= 0.0f) {
            f9 = f9 * f8 / 180.0f / 0.01f;
            f7 = f4 += f9;
            f10 = f3;
            if (!bl) {
                f10 = f3 - f9 / 2.0f;
                f7 = f4;
            }
        }
        f3 = o2.a.f(1.0f - this.o, 1.0f, f10);
        f4 = o2.a.f(0.0f, this.o, f7);
        f7 = (float)Math.toDegrees((float)n4 / this.i);
        f4 = f4 * 360.0f - f7 - (float)Math.toDegrees((float)n5 / this.i);
        f7 = f3 * 360.0f + f7;
        if (!(f4 <= 0.0f)) {
            n4 = ((CircularProgressIndicatorSpec)this.a).b(this.n) && bl && f5 > 0.0f ? 1 : 0;
            paint.setAntiAlias(true);
            paint.setColor(n3);
            paint.setStrokeWidth(this.f);
            f3 = this.g * 2.0f;
            f10 = f8 * 2.0f;
            if (f4 < f10) {
                f6 = f7 + f8 * (f4 /= f10);
                j.b b3 = new j.b(this);
                if (n4 == 0) {
                    b3.d(f6 + 90.0f);
                    b3.a(-this.i);
                } else {
                    f6 = f6 / 360.0f * this.d.getLength() / 2.0f;
                    f5 = this.h * f5;
                    f7 = this.i;
                    if (f7 != this.m || f5 != this.k) {
                        this.k = f5;
                        this.m = f7;
                        this.g();
                    }
                    this.d.getPosTan(f6, b3.a, b3.b);
                }
                paint.setStyle(Paint.Style.FILL);
                this.o(canvas, paint, b3, f3, this.f, f4);
                return;
            }
            paint.setStyle(Paint.Style.STROKE);
            Paint.Cap cap = ((CircularProgressIndicatorSpec)this.a).g() ? Paint.Cap.ROUND : Paint.Cap.BUTT;
            paint.setStrokeCap(cap);
            f7 += f8;
            f4 -= f10;
            ((j.b)this.q.first).c();
            ((j.b)this.q.second).c();
            if (n4 == 0) {
                ((j.b)this.q.first).d(f7 + 90.0f);
                ((j.b)this.q.first).a(-this.i);
                ((j.b)this.q.second).d(f7 + f4 + 90.0f);
                ((j.b)this.q.second).a(-this.i);
                cap = this.p;
                f5 = this.i;
                cap.set(-f5, -f5, f5, f5);
                canvas.drawArc(this.p, f7, f4, false, paint);
            } else {
                this.k(this.d, this.c, this.q, f7 / 360.0f, f4 / 360.0f, f5, f6);
                canvas.drawPath(this.c, paint);
            }
            if (!((CircularProgressIndicatorSpec)this.a).g() && this.g > 0.0f) {
                paint.setStyle(Paint.Style.FILL);
                this.n(canvas, paint, (j.b)this.q.first, f3, this.f);
                this.n(canvas, paint, (j.b)this.q.second, f3, this.f);
            }
        }
    }

    public final void n(Canvas canvas, Paint paint, j.b b3, float f3, float f4) {
        this.o(canvas, paint, b3, f3, f4, 1.0f);
    }

    public final void o(Canvas canvas, Paint paint, j.b b3, float f3, float f4, float f5) {
        f4 = Math.min(f4, this.f);
        float f6 = this.g * f4 / this.f;
        float f7 = f3 / 2.0f;
        f6 = Math.min(f7, f6);
        RectF rectF = new RectF(-f3 / 2.0f, -f4 / 2.0f, f7, f4 / 2.0f);
        canvas.save();
        float[] fArray = b3.a;
        canvas.translate(fArray[0], fArray[1]);
        canvas.rotate(this.i(b3.b));
        canvas.scale(f5, f5);
        canvas.drawRoundRect(rectF, f6, f6, paint);
        canvas.restore();
    }

    public final int p() {
        b b3 = this.a;
        return ((CircularProgressIndicatorSpec)b3).p + ((CircularProgressIndicatorSpec)b3).q * 2;
    }
}

