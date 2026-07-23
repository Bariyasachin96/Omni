/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.Path$Op
 *  android.graphics.PointF
 *  android.graphics.RectF
 */
package v2;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import v2.d;
import v2.e;
import v2.g;
import v2.o;
import v2.q;

public class p {
    public final q[] a = new q[4];
    public final Matrix[] b = new Matrix[4];
    public final Matrix[] c = new Matrix[4];
    public final PointF d = new PointF();
    public final Path e = new Path();
    public final Path f = new Path();
    public final q g = new q();
    public final float[] h = new float[2];
    public final float[] i = new float[2];
    public final Path j = new Path();
    public final Path k = new Path();
    public boolean l = true;

    public p() {
        for (int i3 = 0; i3 < 4; ++i3) {
            this.a[i3] = new q();
            this.b[i3] = new Matrix();
            this.c[i3] = new Matrix();
        }
    }

    public static p l() {
        return v2.p$a.a;
    }

    public final float a(int n3) {
        return (n3 + 1) % 4 * 90;
    }

    public final void b(c object, int n3) {
        this.h[0] = this.a[n3].k();
        this.h[1] = this.a[n3].l();
        this.b[n3].mapPoints(this.h);
        if (n3 == 0) {
            Path path = ((c)object).b;
            float[] fArray = this.h;
            path.moveTo(fArray[0], fArray[1]);
        } else {
            Path path = ((c)object).b;
            float[] fArray = this.h;
            path.lineTo(fArray[0], fArray[1]);
        }
        this.a[n3].d(this.b[n3], ((c)object).b);
        object = ((c)object).d;
        if (object != null) {
            object.a(this.a[n3], this.b[n3], n3);
        }
    }

    public final void c(c object, int n3) {
        int n4 = (n3 + 1) % 4;
        this.h[0] = this.a[n3].i();
        this.h[1] = this.a[n3].j();
        this.b[n3].mapPoints(this.h);
        this.i[0] = this.a[n4].k();
        this.i[1] = this.a[n4].l();
        this.b[n4].mapPoints(this.i);
        Object object2 = this.h;
        float f3 = object2[0];
        Object object3 = this.i;
        float f4 = Math.max((float)Math.hypot(f3 - object3[0], object2[1] - object3[1]) - 0.001f, 0.0f);
        f3 = this.j(((c)object).c, n3);
        this.g.n(0.0f, 0.0f);
        object2 = this.k(n3, ((c)object).a);
        object2.b(f4, f3, ((c)object).e, this.g);
        this.j.reset();
        this.g.d(this.c[n3], this.j);
        if (this.l && (object2.a() || this.m(this.j, n3) || this.m(this.j, n4))) {
            object2 = this.j;
            object2.op((Path)object2, this.f, Path.Op.DIFFERENCE);
            this.h[0] = this.g.k();
            this.h[1] = this.g.l();
            this.c[n3].mapPoints(this.h);
            object3 = this.e;
            object2 = this.h;
            object3.moveTo(object2[0], object2[1]);
            this.g.d(this.c[n3], this.e);
        } else {
            this.g.d(this.c[n3], ((c)object).b);
        }
        object = ((c)object).d;
        if (object != null) {
            object.b(this.g, this.c[n3], n3);
        }
    }

    public void d(o o3, float f3, RectF rectF, Path path) {
        this.e(o3, f3, rectF, null, path);
    }

    public void e(o o3, float f3, RectF rectF, b b3, Path path) {
        this.f(o3, null, f3, rectF, b3, path);
    }

    public void f(o object, float[] fArray, float f3, RectF rectF, b b3, Path path) {
        path.rewind();
        this.e.rewind();
        this.f.rewind();
        this.f.addRect(rectF, Path.Direction.CW);
        object = new c((o)object, f3, rectF, b3, path);
        int n3 = 0;
        int n4 = 0;
        while (true) {
            if (n4 >= 4) break;
            this.n((c)object, n4, fArray);
            this.o(n4);
            ++n4;
        }
        for (int i3 = n3; i3 < 4; ++i3) {
            this.b((c)object, i3);
            this.c((c)object, i3);
        }
        path.close();
        this.e.close();
        if (!this.e.isEmpty()) {
            path.op(this.e, Path.Op.UNION);
        }
    }

    public final void g(int n3, RectF rectF, PointF pointF) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    pointF.set(rectF.right, rectF.top);
                    return;
                }
                pointF.set(rectF.left, rectF.top);
                return;
            }
            pointF.set(rectF.left, rectF.bottom);
            return;
        }
        pointF.set(rectF.right, rectF.bottom);
    }

    public d h(int n3, o o3) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    return o3.t();
                }
                return o3.r();
            }
            return o3.j();
        }
        return o3.l();
    }

    public final e i(int n3, o o3) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    return o3.s();
                }
                return o3.q();
            }
            return o3.i();
        }
        return o3.k();
    }

    public final float j(RectF rectF, int n3) {
        float[] fArray = this.h;
        q q3 = this.a[n3];
        fArray[0] = q3.c;
        fArray[1] = q3.d;
        this.b[n3].mapPoints(fArray);
        if (n3 != 1 && n3 != 3) {
            return Math.abs(rectF.centerY() - this.h[1]);
        }
        return Math.abs(rectF.centerX() - this.h[0]);
    }

    public final g k(int n3, o o3) {
        if (n3 != 1) {
            if (n3 != 2) {
                if (n3 != 3) {
                    return o3.o();
                }
                return o3.p();
            }
            return o3.n();
        }
        return o3.h();
    }

    public final boolean m(Path path, int n3) {
        this.k.reset();
        this.a[n3].d(this.b[n3], this.k);
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        this.k.computeBounds(rectF, true);
        path.op(this.k, Path.Op.INTERSECT);
        path.computeBounds(rectF, true);
        return !rectF.isEmpty() || rectF.width() > 1.0f && rectF.height() > 1.0f;
        {
        }
    }

    public final void n(c c3, int n3, float[] object) {
        object = object == null ? (Object)this.h(n3, c3.a) : (Object)new v2.c(object[n3]);
        this.i(n3, c3.a).b(this.a[n3], 90.0f, c3.e, c3.c, (d)object);
        float f3 = this.a(n3);
        this.b[n3].reset();
        this.g(n3, c3.c, this.d);
        object = this.b[n3];
        c3 = this.d;
        object.setTranslate(((PointF)c3).x, ((PointF)c3).y);
        this.b[n3].preRotate(f3);
    }

    public final void o(int n3) {
        this.h[0] = this.a[n3].i();
        this.h[1] = this.a[n3].j();
        this.b[n3].mapPoints(this.h);
        float f3 = this.a(n3);
        this.c[n3].reset();
        Matrix matrix = this.c[n3];
        float[] fArray = this.h;
        matrix.setTranslate(fArray[0], fArray[1]);
        this.c[n3].preRotate(f3);
    }

    public static abstract class a {
        public static final p a = new p();
    }

    public static interface b {
        public void a(q var1, Matrix var2, int var3);

        public void b(q var1, Matrix var2, int var3);
    }

    public static final class c {
        public final o a;
        public final Path b;
        public final RectF c;
        public final b d;
        public final float e;

        public c(o o3, float f3, RectF rectF, b b3, Path path) {
            this.d = b3;
            this.a = o3;
            this.e = f3;
            this.c = rectF;
            this.b = path;
        }
    }
}

