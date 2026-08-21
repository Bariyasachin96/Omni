/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.RectF
 */
package v2;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import u2.a;

public class q {
    public float a;
    public float b;
    public float c;
    public float d;
    public float e;
    public float f;
    public final List g = new ArrayList();
    public final List h = new ArrayList();
    public boolean i;

    public q() {
        this.n(0.0f, 0.0f);
    }

    public void a(float f3, float f4, float f5, float f6, float f7, float f8) {
        Object object = new d(f3, f4, f5, f6);
        ((d)object).s(f7);
        ((d)object).t(f8);
        this.g.add(object);
        object = new b((d)object);
        float f9 = f7 + f8;
        boolean bl = f8 < 0.0f;
        f8 = f7;
        if (bl) {
            f8 = (f7 + 180.0f) % 360.0f;
        }
        f7 = bl ? (180.0f + f9) % 360.0f : f9;
        this.c((g)object, f8, f7);
        f7 = (f5 - f3) / 2.0f;
        double d3 = f9;
        this.r((f3 + f5) * 0.5f + f7 * (float)Math.cos(Math.toRadians(d3)));
        this.s((f4 + f6) * 0.5f + (f6 - f4) / 2.0f * (float)Math.sin(Math.toRadians(d3)));
    }

    public final void b(float f3) {
        float f4;
        if (this.g() == f3 || (f4 = (f3 - this.g() + 360.0f) % 360.0f) > 180.0f) {
            return;
        }
        d d3 = new d(this.i(), this.j(), this.i(), this.j());
        d3.s(this.g());
        d3.t(f4);
        this.h.add(new b(d3));
        this.p(f3);
    }

    public final void c(g g3, float f3, float f4) {
        this.b(f3);
        this.h.add(g3);
        this.p(f4);
    }

    public void d(Matrix matrix, Path path) {
        int n3 = this.g.size();
        for (int i3 = 0; i3 < n3; ++i3) {
            ((f)this.g.get(i3)).a(matrix, path);
        }
    }

    public boolean e() {
        return this.i;
    }

    public g f(Matrix matrix) {
        this.b(this.h());
        matrix = new Matrix(matrix);
        return new g(this, new ArrayList(this.h), matrix){
            public final List c;
            public final Matrix d;
            public final q e;
            {
                this.e = q3;
                this.c = list;
                this.d = matrix;
            }

            @Override
            public void a(Matrix object, a a4, int n3, Canvas canvas) {
                object = this.c.iterator();
                while (object.hasNext()) {
                    ((g)object.next()).a(this.d, a4, n3, canvas);
                }
            }
        };
    }

    public final float g() {
        return this.e;
    }

    public final float h() {
        return this.f;
    }

    public float i() {
        return this.c;
    }

    public float j() {
        return this.d;
    }

    public float k() {
        return this.a;
    }

    public float l() {
        return this.b;
    }

    public void m(float f3, float f4) {
        Object object = new e();
        v2.q$e.c((e)object, f3);
        v2.q$e.e((e)object, f4);
        this.g.add(object);
        object = new c((e)object, this.i(), this.j());
        this.c((g)object, ((c)object).c() + 270.0f, ((c)object).c() + 270.0f);
        this.r(f3);
        this.s(f4);
    }

    public void n(float f3, float f4) {
        this.o(f3, f4, 270.0f, 0.0f);
    }

    public void o(float f3, float f4, float f5, float f6) {
        this.t(f3);
        this.u(f4);
        this.r(f3);
        this.s(f4);
        this.p(f5);
        this.q((f5 + f6) % 360.0f);
        this.g.clear();
        this.h.clear();
        this.i = false;
    }

    public final void p(float f3) {
        this.e = f3;
    }

    public final void q(float f3) {
        this.f = f3;
    }

    public final void r(float f3) {
        this.c = f3;
    }

    public final void s(float f3) {
        this.d = f3;
    }

    public final void t(float f3) {
        this.a = f3;
    }

    public final void u(float f3) {
        this.b = f3;
    }

    public static class b
    extends g {
        public final d c;

        public b(d d3) {
            this.c = d3;
        }

        @Override
        public void a(Matrix matrix, a a4, int n3, Canvas canvas) {
            float f3 = this.c.m();
            float f4 = this.c.n();
            a4.a(canvas, matrix, new RectF(this.c.k(), this.c.o(), this.c.l(), this.c.j()), n3, f3, f4);
        }
    }

    public static class c
    extends g {
        public final e c;
        public final float d;
        public final float e;

        public c(e e3, float f3, float f4) {
            this.c = e3;
            this.d = f3;
            this.e = f4;
        }

        @Override
        public void a(Matrix matrix, a a4, int n3, Canvas canvas) {
            float f3 = this.c.c;
            float f4 = this.e;
            float f5 = this.c.b;
            float f6 = this.d;
            RectF rectF = new RectF(0.0f, 0.0f, (float)Math.hypot(f3 - f4, f5 - f6), 0.0f);
            this.a.set(matrix);
            this.a.preTranslate(this.d, this.e);
            this.a.preRotate(this.c());
            a4.b(canvas, this.a, rectF, n3);
        }

        public float c() {
            return (float)Math.toDegrees(Math.atan((this.c.c - this.e) / (this.c.b - this.d)));
        }
    }

    public static class d
    extends f {
        public static final RectF h = new RectF();
        public float b;
        public float c;
        public float d;
        public float e;
        public float f;
        public float g;

        public d(float f3, float f4, float f5, float f6) {
            this.q(f3);
            this.u(f4);
            this.r(f5);
            this.p(f6);
        }

        @Override
        public void a(Matrix matrix, Path path) {
            Matrix matrix2 = this.a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            matrix2 = h;
            matrix2.set(this.k(), this.o(), this.l(), this.j());
            path.arcTo((RectF)matrix2, this.m(), this.n(), false);
            path.transform(matrix);
        }

        public final float j() {
            return this.e;
        }

        public final float k() {
            return this.b;
        }

        public final float l() {
            return this.d;
        }

        public final float m() {
            return this.f;
        }

        public final float n() {
            return this.g;
        }

        public final float o() {
            return this.c;
        }

        public final void p(float f3) {
            this.e = f3;
        }

        public final void q(float f3) {
            this.b = f3;
        }

        public final void r(float f3) {
            this.d = f3;
        }

        public final void s(float f3) {
            this.f = f3;
        }

        public final void t(float f3) {
            this.g = f3;
        }

        public final void u(float f3) {
            this.c = f3;
        }
    }

    public static class e
    extends f {
        public float b;
        public float c;

        public static /* synthetic */ float c(e e3, float f3) {
            e3.b = f3;
            return f3;
        }

        public static /* synthetic */ float e(e e3, float f3) {
            e3.c = f3;
            return f3;
        }

        @Override
        public void a(Matrix matrix, Path path) {
            Matrix matrix2 = this.a;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.b, this.c);
            path.transform(matrix);
        }
    }

    public static abstract class f {
        public final Matrix a = new Matrix();

        public abstract void a(Matrix var1, Path var2);
    }

    public static abstract class g {
        public static final Matrix b = new Matrix();
        public final Matrix a = new Matrix();

        public abstract void a(Matrix var1, a var2, int var3, Canvas var4);

        public final void b(a a4, int n3, Canvas canvas) {
            this.a(b, a4, n3, canvas);
        }
    }
}

