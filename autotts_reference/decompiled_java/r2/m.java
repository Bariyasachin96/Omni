/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Cap
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.PathMeasure
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.util.Pair
 */
package r2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import o2.a;
import r2.b;
import r2.j;

public final class m
extends j {
    public float f = 300.0f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k;
    public int l;
    public boolean m;
    public float n;
    public Pair o = new Pair((Object)new j.b(this), (Object)new j.b(this));

    public m(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(linearProgressIndicatorSpec);
    }

    private void j(PathMeasure pathMeasure, Path path, Pair object, float f3, float f4, float f5, float f6) {
        int n3 = this.m ? ((LinearProgressIndicatorSpec)this.a).j : ((LinearProgressIndicatorSpec)this.a).k;
        if (pathMeasure == this.d && n3 != this.l) {
            this.l = n3;
            this.g();
        }
        path.rewind();
        float f7 = -this.f / 2.0f;
        boolean bl = ((LinearProgressIndicatorSpec)this.a).b(this.m);
        float f8 = f7;
        float f9 = f3;
        float f10 = f4;
        if (bl) {
            f10 = this.f;
            f8 = this.k;
            f9 = f10 / f8;
            f10 = f6 / f9;
            float f11 = f9 / (f9 + 1.0f);
            f9 = (f3 + f10) * f11;
            f10 = (f4 + f10) * f11;
            f8 = f7 - f6 * f8;
        }
        f3 = f9 * pathMeasure.getLength();
        f4 = f10 * pathMeasure.getLength();
        pathMeasure.getSegment(f3, f4, path, true);
        j.b b3 = (j.b)((Pair)object).first;
        b3.c();
        pathMeasure.getPosTan(f3, b3.a, b3.b);
        object = (j.b)((Pair)object).second;
        ((j.b)object).c();
        pathMeasure.getPosTan(f4, ((j.b)object).a, ((j.b)object).b);
        this.e.reset();
        this.e.setTranslate(f8, 0.0f);
        b3.f(f8, 0.0f);
        ((j.b)object).f(f8, 0.0f);
        if (bl) {
            f3 = this.j * f5;
            this.e.postScale(1.0f, f3);
            b3.e(1.0f, f3);
            ((j.b)object).e(1.0f, f3);
        }
        path.transform(this.e);
    }

    private void l(Canvas canvas, Paint paint, j.b b3, float f3, float f4, float f5) {
        this.m(canvas, paint, b3, f3, f4, f5, null, 0.0f, 0.0f, 0.0f, false);
    }

    @Override
    public void a(Canvas canvas, Rect object, float f3, boolean bl, boolean bl2) {
        if (this.f != (float)object.width()) {
            this.f = object.width();
            this.g();
        }
        float f4 = this.e();
        canvas.translate((float)object.left + (float)object.width() / 2.0f, (float)object.top + (float)object.height() / 2.0f + Math.max(0.0f, ((float)object.height() - f4) / 2.0f));
        if (((LinearProgressIndicatorSpec)this.a).q) {
            canvas.scale(-1.0f, 1.0f);
        }
        float f5 = this.f / 2.0f;
        canvas.clipRect(-f5, -(f4 /= 2.0f), f5, f4);
        object = this.a;
        this.g = (float)((LinearProgressIndicatorSpec)object).a * f3;
        this.h = (float)Math.min(((LinearProgressIndicatorSpec)object).a / 2, ((LinearProgressIndicatorSpec)object).a()) * f3;
        object = this.a;
        this.j = (float)((LinearProgressIndicatorSpec)object).l * f3;
        this.i = Math.min((float)((LinearProgressIndicatorSpec)object).a / 2.0f, (float)((LinearProgressIndicatorSpec)object).i()) * f3;
        if (bl || bl2) {
            if (bl && ((LinearProgressIndicatorSpec)this.a).g == 2 || bl2 && ((LinearProgressIndicatorSpec)this.a).h == 1) {
                canvas.scale(1.0f, -1.0f);
            }
            if (bl || bl2 && ((LinearProgressIndicatorSpec)this.a).h != 3) {
                canvas.translate(0.0f, (float)((LinearProgressIndicatorSpec)this.a).a * (1.0f - f3) / 2.0f);
            }
        }
        if (bl2 && ((LinearProgressIndicatorSpec)this.a).h == 3) {
            this.n = f3;
            return;
        }
        this.n = 1.0f;
    }

    @Override
    public void b(Canvas canvas, Paint paint, int n3, int n4) {
        n3 = h2.a.a(n3, n4);
        this.m = false;
        if (((LinearProgressIndicatorSpec)this.a).r > 0 && n3 != 0) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(n3);
            Object object = this.a;
            float f3 = ((LinearProgressIndicatorSpec)object).s != null ? ((LinearProgressIndicatorSpec)object).s.floatValue() + (float)((LinearProgressIndicatorSpec)this.a).r / 2.0f : this.g / 2.0f;
            object = new j.b(this, new float[]{this.f / 2.0f - f3, 0.0f}, new float[]{1.0f, 0.0f});
            b b3 = this.a;
            this.l(canvas, paint, (j.b)object, ((LinearProgressIndicatorSpec)b3).r, ((LinearProgressIndicatorSpec)b3).r, this.h * (float)((LinearProgressIndicatorSpec)b3).r / this.g);
        }
    }

    @Override
    public void c(Canvas canvas, Paint paint, j.a a4, int n3) {
        int n4 = h2.a.a(a4.c, n3);
        this.m = a4.h;
        float f3 = a4.a;
        float f4 = a4.b;
        n3 = a4.d;
        this.k(canvas, paint, f3, f4, n4, n3, n3, a4.e, a4.f, true);
    }

    @Override
    public void d(Canvas canvas, Paint paint, float f3, float f4, int n3, int n4, int n5) {
        n3 = h2.a.a(n3, n4);
        this.m = false;
        this.k(canvas, paint, f3, f4, n3, n5, n5, 0.0f, 0.0f, false);
    }

    @Override
    public int e() {
        b b3 = this.a;
        return ((LinearProgressIndicatorSpec)b3).a + ((LinearProgressIndicatorSpec)b3).l * 2;
    }

    @Override
    public int f() {
        return -1;
    }

    @Override
    public void g() {
        this.b.rewind();
        if (((LinearProgressIndicatorSpec)this.a).b(this.m)) {
            int n3 = this.m ? ((LinearProgressIndicatorSpec)this.a).j : ((LinearProgressIndicatorSpec)this.a).k;
            float f3 = this.f;
            int n4 = (int)(f3 / (float)n3);
            this.k = f3 / (float)n4;
            for (n3 = 0; n3 <= n4; ++n3) {
                Path path = this.b;
                int n5 = n3 * 2;
                float f4 = n5;
                f3 = n5 + 1;
                path.cubicTo(f4 + 0.48f, 0.0f, f3 - 0.48f, 1.0f, f3, 1.0f);
                path = this.b;
                f4 = n5 + 2;
                path.cubicTo(f3 + 0.48f, 1.0f, f4 - 0.48f, 0.0f, f4, 0.0f);
            }
            this.e.reset();
            this.e.setScale(this.k / 2.0f, -2.0f);
            this.e.postTranslate(0.0f, 1.0f);
            this.b.transform(this.e);
        } else {
            this.b.lineTo(this.f, 0.0f);
        }
        this.d.setPath(this.b, false);
    }

    public final void k(Canvas canvas, Paint paint, float f3, float f4, int n3, int n4, int n5, float f5, float f6, boolean bl) {
        float f7;
        float f8;
        f3 = j0.a.a(f3, 0.0f, 1.0f);
        f4 = j0.a.a(f4, 0.0f, 1.0f);
        f3 = o2.a.f(1.0f - this.n, 1.0f, f3);
        f4 = o2.a.f(1.0f - this.n, 1.0f, f4);
        int n6 = (int)((float)n4 * j0.a.a(f3, 0.0f, 0.01f) / 0.01f);
        n4 = (int)((float)n5 * (1.0f - j0.a.a(f4, 0.99f, 1.0f)) / 0.01f);
        float f9 = this.f;
        n5 = (int)(f3 * f9 + (float)n6);
        n6 = (int)(f4 * f9 - (float)n4);
        f3 = this.h;
        f4 = this.i;
        if (f3 != f4) {
            f4 = Math.max(f3, f4);
            f3 = this.f;
            f4 /= f3;
            f3 = o2.a.f(this.h, this.i, j0.a.a((float)n5 / f3, 0.0f, f4) / f4);
            f9 = this.h;
            f8 = this.i;
            f7 = this.f;
            f4 = o2.a.f(f9, f8, j0.a.a((f7 - (float)n6) / f7, 0.0f, f4) / f4);
        } else {
            f4 = f3;
        }
        float f10 = -this.f / 2.0f;
        n4 = ((LinearProgressIndicatorSpec)this.a).b(this.m) && bl && f5 > 0.0f ? 1 : 0;
        if (n5 <= n6) {
            float f11 = (float)n5 + f3;
            f8 = (float)n6 - f4;
            f7 = f3 * 2.0f;
            f9 = 2.0f * f4;
            paint.setColor(n3);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(this.g);
            ((j.b)this.o.first).c();
            ((j.b)this.o.second).c();
            ((j.b)this.o.first).f(f11 + f10, 0.0f);
            ((j.b)this.o.second).f(f10 + f8, 0.0f);
            if (n5 == 0 && f8 + f4 < f11 + f3) {
                Pair pair = this.o;
                j.b b3 = (j.b)pair.first;
                f5 = this.g;
                this.m(canvas, paint, b3, f7, f5, f3, (j.b)pair.second, f9, f5, f4, true);
                return;
            }
            if (f11 - f3 > f8 - f4) {
                Pair pair = this.o;
                j.b b4 = (j.b)pair.second;
                f5 = this.g;
                this.m(canvas, paint, b4, f9, f5, f4, (j.b)pair.first, f7, f5, f3, false);
                return;
            }
            paint.setStyle(Paint.Style.STROKE);
            Object object = ((LinearProgressIndicatorSpec)this.a).g() ? Paint.Cap.ROUND : Paint.Cap.BUTT;
            paint.setStrokeCap(object);
            if (n4 == 0) {
                Pair pair = this.o;
                object = pair.first;
                f6 = ((j.b)object).a[0];
                f5 = ((j.b)object).a[1];
                object = pair.second;
                canvas.drawLine(f6, f5, ((j.b)object).a[0], ((j.b)object).a[1], paint);
            } else {
                PathMeasure pathMeasure = this.d;
                Path path = this.c;
                object = this.o;
                f10 = this.f;
                this.j(pathMeasure, path, (Pair)object, f11 / f10, f8 / f10, f5, f6);
                canvas.drawPath(this.c, paint);
            }
            if (!((LinearProgressIndicatorSpec)this.a).g()) {
                if (f11 > 0.0f && f3 > 0.0f) {
                    this.l(canvas, paint, (j.b)this.o.first, f7, this.g, f3);
                }
                if (f8 < this.f && f4 > 0.0f) {
                    this.l(canvas, paint, (j.b)this.o.second, f9, this.g, f4);
                }
            }
        }
    }

    public final void m(Canvas canvas, Paint paint, j.b b3, float f3, float f4, float f5, j.b object, float f6, float f7, float f8, boolean bl) {
        float f9 = Math.min(f4, this.g);
        float f10 = -f3 / 2.0f;
        f4 = -f9 / 2.0f;
        float f11 = f3 / 2.0f;
        RectF rectF = new RectF(f10, f4, f11, f9 /= 2.0f);
        paint.setStyle(Paint.Style.FILL);
        canvas.save();
        if (object != null) {
            f7 = Math.min(f7, this.g);
            f8 = Math.min(f6 / 2.0f, f8 * f7 / this.g);
            RectF rectF2 = new RectF();
            if (bl) {
                f3 = ((j.b)object).a[0] - f8 - (b3.a[0] - f5);
                if (f3 > 0.0f) {
                    ((j.b)object).f(-f3 / 2.0f, 0.0f);
                    f3 = f6 + f3;
                } else {
                    f3 = f6;
                }
                rectF2.set(0.0f, f4, f11, f9);
            } else {
                f3 = ((j.b)object).a[0] + f8 - (b3.a[0] + f5);
                if (f3 < 0.0f) {
                    ((j.b)object).f(-f3 / 2.0f, 0.0f);
                    f3 = f6 - f3;
                } else {
                    f3 = f6;
                }
                rectF2.set(f10, f4, 0.0f, f9);
            }
            RectF rectF3 = new RectF(-f3 / 2.0f, -f7 / 2.0f, f3 / 2.0f, f7 / 2.0f);
            Object object2 = ((j.b)object).a;
            canvas.translate(object2[0], object2[1]);
            canvas.rotate(this.i(((j.b)object).b));
            object2 = new Path();
            object2.addRoundRect(rectF3, f8, f8, Path.Direction.CCW);
            canvas.clipPath((Path)object2);
            canvas.rotate(-this.i(((j.b)object).b));
            object = ((j.b)object).a;
            canvas.translate((float)(-object[0]), (float)(-object[1]));
            object = b3.a;
            canvas.translate((float)object[0], (float)object[1]);
            canvas.rotate(this.i(b3.b));
            canvas.drawRect(rectF2, paint);
            canvas.drawRoundRect(rectF, f5, f5, paint);
        } else {
            object = b3.a;
            canvas.translate((float)object[0], (float)object[1]);
            canvas.rotate(this.i(b3.b));
            canvas.drawRoundRect(rectF, f5, f5, paint);
        }
        canvas.restore();
    }
}

