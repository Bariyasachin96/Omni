/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.Matrix
 *  android.graphics.Outline
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.graphics.Region
 *  android.graphics.Region$Op
 *  android.graphics.Xfermode
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build$VERSION
 *  android.os.Looper
 *  android.util.AttributeSet
 *  android.util.Log
 */
package v2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import java.util.BitSet;
import u2.a;
import v2.b;
import v2.m;
import v2.o;
import v2.p;
import v2.q;
import v2.r;
import v2.w;
import x0.k;
import x0.l;

public class i
extends Drawable
implements r {
    public static final String H = "i";
    public static final o I;
    public static final Paint J;
    public static final e[] K;
    public boolean A;
    public o B;
    public l C;
    public k[] D;
    public float[] E;
    public float[] F;
    public d G;
    public final o.c c = new o.c(this){
        public final i a;
        {
            this.a = i3;
        }

        @Override
        public v2.d a(v2.d d3) {
            if (d3 instanceof m) {
                return d3;
            }
            return new b(-this.a.M(), d3);
        }
    };
    public c d;
    public final q.g[] e = new q.g[4];
    public final q.g[] f = new q.g[4];
    public final BitSet g = new BitSet(8);
    public boolean h;
    public boolean i;
    public final Matrix j = new Matrix();
    public final Path k = new Path();
    public final Path l = new Path();
    public final RectF m = new RectF();
    public final RectF n = new RectF();
    public final Region o = new Region();
    public final Region p = new Region();
    public final Paint q;
    public final Paint r;
    public final a s;
    public final p.b t;
    public final p u;
    public PorterDuffColorFilter v;
    public PorterDuffColorFilter w;
    public int x;
    public final RectF y;
    public boolean z;

    static {
        e[] eArray = v2.o.a();
        I = eArray.q(0, 0.0f).m();
        eArray = new Paint(1);
        J = eArray;
        eArray.setColor(-1);
        eArray.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        K = new e[4];
        for (int i3 = 0; i3 < (eArray = K).length; ++i3) {
            eArray[i3] = new e(i3);
        }
    }

    public i() {
        this(new o());
    }

    public i(Context context, AttributeSet attributeSet, int n3, int n4) {
        this(v2.o.e(context, attributeSet, n3, n4).m());
    }

    public i(c c3) {
        Paint paint;
        Paint paint2;
        this.q = paint2 = new Paint(1);
        this.r = paint = new Paint(1);
        this.s = new a();
        p p3 = Looper.getMainLooper().getThread() == Thread.currentThread() ? v2.p.l() : new p();
        this.u = p3;
        this.y = new RectF();
        this.z = true;
        this.A = true;
        this.D = new k[4];
        this.d = c3;
        paint.setStyle(Paint.Style.STROKE);
        paint2.setStyle(Paint.Style.FILL);
        this.A0();
        this.w0(this.getState());
        this.t = new p.b(this){
            public final i a;
            {
                this.a = i3;
            }

            @Override
            public void a(q q3, Matrix matrix, int n3) {
                this.a.g.set(n3, q3.e());
                ((i)this.a).e[n3] = q3.f(matrix);
            }

            @Override
            public void b(q q3, Matrix matrix, int n3) {
                this.a.g.set(n3 + 4, q3.e());
                ((i)this.a).f[n3] = q3.f(matrix);
            }
        };
    }

    public i(o o3) {
        this(new c(o3, null));
    }

    public static int b0(int n3, int n4) {
        return n3 * (n4 + (n4 >>> 7)) >>> 8;
    }

    public static /* synthetic */ boolean h(i i3, boolean bl) {
        i3.h = bl;
        return bl;
    }

    public static /* synthetic */ boolean i(i i3, boolean bl) {
        i3.i = bl;
        return bl;
    }

    public static i r(Context context, float f3, ColorStateList object) {
        ColorStateList colorStateList = object;
        if (object == null) {
            colorStateList = ColorStateList.valueOf((int)h2.a.c(context, z1.c.colorSurface, i.class.getSimpleName()));
        }
        object = new i();
        ((i)object).W(context);
        ((i)object).i0(colorStateList);
        ((i)object).h0(f3);
        return object;
    }

    public final RectF A() {
        this.n.set(this.z());
        float f3 = this.M();
        this.n.inset(f3, f3);
        return this.n;
    }

    public final boolean A0() {
        PorterDuffColorFilter porterDuffColorFilter = this.v;
        PorterDuffColorFilter porterDuffColorFilter2 = this.w;
        c c3 = this.d;
        this.v = this.p(c3.h, c3.i, this.q, true);
        c3 = this.d;
        this.w = this.p(c3.g, c3.i, this.r, false);
        c3 = this.d;
        if (c3.v) {
            this.s.d(c3.h.getColorForState(this.getState(), 0));
        }
        return !n0.c.a(porterDuffColorFilter, this.v) || !n0.c.a(porterDuffColorFilter2, this.w);
        {
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public float B() {
        float f3;
        float f4;
        float[] fArray = this.E;
        if (fArray != null) {
            f4 = fArray[3] + fArray[2] - fArray[1];
            f3 = fArray[0];
            return (f4 - f3) / 2.0f;
        }
        RectF rectF = this.z();
        f4 = this.u.h(3, this.K()).a(rectF) + this.u.h(2, this.K()).a(rectF) - this.u.h(1, this.K()).a(rectF);
        f3 = this.u.h(0, this.K()).a(rectF);
        return (f4 - f3) / 2.0f;
    }

    public final void B0() {
        float f3 = this.S();
        this.d.s = (int)Math.ceil(0.75f * f3);
        this.d.t = (int)Math.ceil(f3 * 0.25f);
        this.A0();
        this.X();
    }

    public float C() {
        return this.d.p;
    }

    public ColorStateList D() {
        return this.d.e;
    }

    public float E() {
        return this.d.l;
    }

    public float F() {
        return this.d.o;
    }

    public int G() {
        return this.x;
    }

    public int H() {
        c c3 = this.d;
        return (int)((double)c3.t * Math.sin(Math.toRadians(c3.u)));
    }

    public int I() {
        c c3 = this.d;
        return (int)((double)c3.t * Math.cos(Math.toRadians(c3.u)));
    }

    public int J() {
        return this.d.s;
    }

    public o K() {
        return this.d.a;
    }

    public ColorStateList L() {
        return this.d.f;
    }

    public final float M() {
        if (this.V()) {
            return this.r.getStrokeWidth() / 2.0f;
        }
        return 0.0f;
    }

    public float N() {
        return this.d.m;
    }

    public ColorStateList O() {
        return this.d.h;
    }

    public float P() {
        float[] fArray = this.E;
        if (fArray != null) {
            return fArray[3];
        }
        return this.d.a.r().a(this.z());
    }

    public float Q() {
        float[] fArray = this.E;
        if (fArray != null) {
            return fArray[0];
        }
        return this.d.a.t().a(this.z());
    }

    public float R() {
        return this.d.q;
    }

    public float S() {
        return this.C() + this.R();
    }

    public final boolean T() {
        c c3 = this.d;
        int n3 = c3.r;
        return n3 != 1 && c3.s > 0 && (n3 == 2 || this.d0());
    }

    public final boolean U() {
        Paint.Style style = this.d.w;
        return style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.FILL;
        {
        }
    }

    public final boolean V() {
        Paint.Style style = this.d.w;
        return (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.r.getStrokeWidth() > 0.0f;
    }

    public void W(Context context) {
        this.d.c = new k2.a(context);
        this.B0();
    }

    public final void X() {
        super.invalidateSelf();
    }

    public boolean Y() {
        k2.a a4 = this.d.c;
        return a4 != null && a4.e();
    }

    public boolean Z() {
        float[] fArray;
        return this.d.a.v(this.z()) || (fArray = this.E) != null && o2.a.a(fArray) && this.d.a.u();
        {
        }
    }

    public final void a0(Canvas canvas) {
        if (!this.T()) {
            return;
        }
        canvas.save();
        this.c0(canvas);
        if (!this.z) {
            this.s(canvas);
            canvas.restore();
            return;
        }
        int n3 = (int)(this.y.width() - (float)this.getBounds().width());
        int n4 = (int)(this.y.height() - (float)this.getBounds().height());
        if (n3 >= 0 && n4 >= 0) {
            Bitmap bitmap = Bitmap.createBitmap((int)((int)this.y.width() + this.d.s * 2 + n3), (int)((int)this.y.height() + this.d.s * 2 + n4), (Bitmap.Config)Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmap);
            float f3 = this.getBounds().left - this.d.s - n3;
            float f4 = this.getBounds().top - this.d.s - n4;
            canvas2.translate(-f3, -f4);
            this.s(canvas2);
            canvas.drawBitmap(bitmap, f3, f4, null);
            bitmap.recycle();
            canvas.restore();
            return;
        }
        throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
    }

    public final void c0(Canvas canvas) {
        int n3 = this.H();
        int n4 = this.I();
        canvas.translate((float)n3, (float)n4);
    }

    public boolean d0() {
        return !this.Z() && !this.k.isConvex() && Build.VERSION.SDK_INT < 29;
    }

    public void draw(Canvas canvas) {
        this.q.setColorFilter((ColorFilter)this.v);
        int n3 = this.q.getAlpha();
        this.q.setAlpha(v2.i.b0(n3, this.d.n));
        this.r.setColorFilter((ColorFilter)this.w);
        this.r.setStrokeWidth(this.d.m);
        int n4 = this.r.getAlpha();
        this.r.setAlpha(v2.i.b0(n4, this.d.n));
        if (this.U()) {
            if (this.h) {
                this.k(this.z(), this.k);
                this.h = false;
            }
            this.a0(canvas);
            this.t(canvas);
        }
        if (this.V()) {
            if (this.i) {
                this.n();
                this.i = false;
            }
            this.w(canvas);
        }
        this.q.setAlpha(n3);
        this.r.setAlpha(n4);
    }

    public void e0(float f3) {
        this.setShapeAppearanceModel(this.d.a.x(f3));
    }

    public void f0(v2.d d3) {
        this.setShapeAppearanceModel(this.d.a.y(d3));
    }

    public void g0(l l3) {
        if (this.C != l3) {
            k[] kArray;
            this.C = l3;
            for (int i3 = 0; i3 < (kArray = this.D).length; ++i3) {
                if (kArray[i3] == null) {
                    kArray[i3] = new k(this, K[i3]);
                }
                this.D[i3].s(new l().f(l3.a()).h(l3.c()));
            }
            this.y0(this.getState(), true);
            this.invalidateSelf();
        }
    }

    public int getAlpha() {
        return this.d.n;
    }

    public Drawable.ConstantState getConstantState() {
        return this.d;
    }

    public int getOpacity() {
        return -3;
    }

    public void getOutline(Outline outline) {
        RectF rectF;
        if (this.d.r == 2 || (rectF = this.z()).isEmpty()) {
            return;
        }
        float f3 = this.m(rectF, this.d.a, this.E);
        if (f3 >= 0.0f) {
            outline.setRoundRect(this.getBounds(), f3 * this.d.l);
            return;
        }
        if (this.h) {
            this.k(rectF, this.k);
            this.h = false;
        }
        j2.d.l(outline, this.k);
    }

    public boolean getPadding(Rect rect) {
        Rect rect2 = this.d.j;
        if (rect2 != null) {
            rect.set(rect2);
            return true;
        }
        return super.getPadding(rect);
    }

    public Region getTransparentRegion() {
        Rect rect = this.getBounds();
        this.o.set(rect);
        this.k(this.z(), this.k);
        this.p.setPath(this.k, this.o);
        this.o.op(this.p, Region.Op.DIFFERENCE);
        return this.o;
    }

    public void h0(float f3) {
        c c3 = this.d;
        if (c3.p != f3) {
            c3.p = f3;
            this.B0();
        }
    }

    public void i0(ColorStateList colorStateList) {
        c c3 = this.d;
        if (c3.e != colorStateList) {
            c3.e = colorStateList;
            this.onStateChange(this.getState());
        }
    }

    public void invalidateSelf() {
        this.h = true;
        this.i = true;
        super.invalidateSelf();
    }

    public boolean isStateful() {
        Object object;
        return super.isStateful() || (object = this.d.h) != null && object.isStateful() || (object = this.d.g) != null && object.isStateful() || (object = this.d.f) != null && object.isStateful() || (object = this.d.e) != null && object.isStateful() || (object = this.d.b) != null && ((w)object).f();
        {
        }
    }

    public final PorterDuffColorFilter j(Paint paint, boolean bl) {
        if (bl) {
            int n3;
            int n4 = paint.getColor();
            this.x = n3 = this.q(n4);
            if (n3 != n4) {
                return new PorterDuffColorFilter(n3, PorterDuff.Mode.SRC_IN);
            }
        }
        return null;
    }

    public void j0(float f3) {
        c c3 = this.d;
        if (c3.l != f3) {
            c3.l = f3;
            this.h = true;
            this.i = true;
            this.invalidateSelf();
        }
    }

    public final void k(RectF rectF, Path path) {
        this.l(rectF, path);
        if (this.d.k != 1.0f) {
            this.j.reset();
            Matrix matrix = this.j;
            float f3 = this.d.k;
            matrix.setScale(f3, f3, rectF.width() / 2.0f, rectF.height() / 2.0f);
            path.transform(this.j);
        }
        path.computeBounds(this.y, true);
    }

    public void k0(d d3) {
        this.G = d3;
    }

    public final void l(RectF rectF, Path path) {
        p p3 = this.u;
        c c3 = this.d;
        p3.f(c3.a, this.E, c3.l, rectF, this.t, path);
    }

    public void l0(int n3, int n4, int n5, int n6) {
        c c3 = this.d;
        if (c3.j == null) {
            c3.j = new Rect();
        }
        this.d.j.set(n3, n4, n5, n6);
        this.invalidateSelf();
    }

    public final float m(RectF rectF, o o3, float[] fArray) {
        if (fArray == null) {
            if (o3.v(rectF)) {
                return o3.r().a(rectF);
            }
        } else if (o2.a.a(fArray) && o3.u()) {
            return fArray[0];
        }
        return -1.0f;
    }

    public void m0(Paint.Style style) {
        this.d.w = style;
        this.X();
    }

    public Drawable mutate() {
        this.d = new c(this.d);
        return this;
    }

    public final void n() {
        this.z0();
        this.u.f(this.B, this.F, this.d.l, this.A(), null, this.l);
    }

    public void n0(float f3) {
        c c3 = this.d;
        if (c3.o != f3) {
            c3.o = f3;
            this.B0();
        }
    }

    public final PorterDuffColorFilter o(ColorStateList colorStateList, PorterDuff.Mode mode, boolean bl) {
        int n3;
        int n4 = n3 = colorStateList.getColorForState(this.getState(), 0);
        if (bl) {
            n4 = this.q(n3);
        }
        this.x = n4;
        return new PorterDuffColorFilter(n4, mode);
    }

    public void o0(boolean bl) {
        this.z = bl;
    }

    public void onBoundsChange(Rect rect) {
        this.h = true;
        this.i = true;
        super.onBoundsChange(rect);
        if (this.d.b != null && !rect.isEmpty()) {
            this.y0(this.getState(), this.A);
        }
        this.A = rect.isEmpty();
    }

    public boolean onStateChange(int[] nArray) {
        if (this.d.b != null) {
            this.x0(nArray);
        }
        boolean bl = this.w0(nArray);
        boolean bl2 = this.A0();
        bl2 = bl || bl2;
        if (bl2) {
            this.invalidateSelf();
        }
        return bl2;
    }

    public final PorterDuffColorFilter p(ColorStateList colorStateList, PorterDuff.Mode mode, Paint paint, boolean bl) {
        if (colorStateList != null && mode != null) {
            return this.o(colorStateList, mode, bl);
        }
        return this.j(paint, bl);
    }

    public void p0(int n3) {
        this.s.d(n3);
        this.d.v = false;
        this.X();
    }

    public int q(int n3) {
        float f3 = this.S();
        float f4 = this.F();
        k2.a a4 = this.d.c;
        int n4 = n3;
        if (a4 != null) {
            n4 = a4.c(n3, f3 + f4);
        }
        return n4;
    }

    public void q0(int n3) {
        c c3 = this.d;
        if (c3.r != n3) {
            c3.r = n3;
            this.X();
        }
    }

    public void r0(w w3) {
        c c3 = this.d;
        if (c3.b != w3) {
            c3.b = w3;
            this.y0(this.getState(), true);
            this.invalidateSelf();
        }
    }

    public final void s(Canvas canvas) {
        int n3;
        if (this.g.cardinality() > 0) {
            Log.w((String)H, (String)"Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.d.t != 0) {
            canvas.drawPath(this.k, this.s.c());
        }
        for (n3 = 0; n3 < 4; ++n3) {
            this.e[n3].b(this.s, this.d.s, canvas);
            this.f[n3].b(this.s, this.d.s, canvas);
        }
        if (this.z) {
            int n4 = this.H();
            n3 = this.I();
            canvas.translate((float)(-n4), (float)(-n3));
            canvas.drawPath(this.k, J);
            canvas.translate((float)n4, (float)n3);
        }
    }

    public void s0(float f3, int n3) {
        this.v0(f3);
        this.u0(ColorStateList.valueOf((int)n3));
    }

    public void setAlpha(int n3) {
        c c3 = this.d;
        if (c3.n != n3) {
            c3.n = n3;
            this.X();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.d.d = colorFilter;
        this.X();
    }

    @Override
    public void setShapeAppearanceModel(o o3) {
        c c3 = this.d;
        c3.a = o3;
        c3.b = null;
        this.E = null;
        this.F = null;
        this.invalidateSelf();
    }

    public void setTint(int n3) {
        this.setTintList(ColorStateList.valueOf((int)n3));
    }

    public void setTintList(ColorStateList colorStateList) {
        this.d.h = colorStateList;
        this.A0();
        this.X();
    }

    public void setTintMode(PorterDuff.Mode mode) {
        c c3 = this.d;
        if (c3.i != mode) {
            c3.i = mode;
            this.A0();
            this.X();
        }
    }

    public final void t(Canvas canvas) {
        this.v(canvas, this.q, this.k, this.d.a, this.E, this.z());
    }

    public void t0(float f3, ColorStateList colorStateList) {
        this.v0(f3);
        this.u0(colorStateList);
    }

    public void u(Canvas canvas, Paint paint, Path path, RectF rectF) {
        this.v(canvas, paint, path, this.d.a, this.E, rectF);
    }

    public void u0(ColorStateList colorStateList) {
        c c3 = this.d;
        if (c3.f != colorStateList) {
            c3.f = colorStateList;
            this.onStateChange(this.getState());
        }
    }

    public final void v(Canvas canvas, Paint paint, Path path, o o3, float[] fArray, RectF rectF) {
        float f3 = this.m(rectF, o3, fArray);
        if (f3 >= 0.0f) {
            canvas.drawRoundRect(rectF, f3 *= this.d.l, f3, paint);
            return;
        }
        canvas.drawPath(path, paint);
    }

    public void v0(float f3) {
        this.d.m = f3;
        this.invalidateSelf();
    }

    public void w(Canvas canvas) {
        this.v(canvas, this.r, this.l, this.B, this.F, this.A());
    }

    public final boolean w0(int[] nArray) {
        boolean bl;
        int n3;
        int n4;
        if (this.d.e != null && (n4 = this.q.getColor()) != (n3 = this.d.e.getColorForState(nArray, n4))) {
            this.q.setColor(n3);
            bl = true;
        } else {
            bl = false;
        }
        if (this.d.f != null && (n3 = this.r.getColor()) != (n4 = this.d.f.getColorForState(nArray, n3))) {
            this.r.setColor(n4);
            return true;
        }
        return bl;
    }

    public float x() {
        float[] fArray = this.E;
        if (fArray != null) {
            return fArray[2];
        }
        return this.d.a.j().a(this.z());
    }

    public final void x0(int[] nArray) {
        this.y0(nArray, false);
    }

    public float y() {
        float[] fArray = this.E;
        if (fArray != null) {
            return fArray[1];
        }
        return this.d.a.l().a(this.z());
    }

    public final void y0(int[] object, boolean bl) {
        RectF rectF = this.z();
        if (this.d.b != null && !rectF.isEmpty()) {
            Object object2 = this.C;
            int n3 = 0;
            int n4 = object2 == null ? 1 : 0;
            boolean bl2 = bl | n4;
            if (this.E == null) {
                this.E = new float[4];
            }
            object = this.d.b.d((int[])object);
            for (n4 = n3; n4 < 4; ++n4) {
                float f3 = this.u.h(n4, (o)object).a(rectF);
                if (bl2) {
                    this.E[n4] = f3;
                }
                if ((object2 = this.D[n4]) == null) continue;
                ((k)object2).o(f3);
                if (!bl2) continue;
                this.D[n4].t();
            }
            if (bl2) {
                this.invalidateSelf();
            }
        }
    }

    public RectF z() {
        this.m.set(this.getBounds());
        return this.m;
    }

    public final void z0() {
        this.B = this.K().z(this.c);
        float[] fArray = this.E;
        if (fArray == null) {
            this.F = null;
            return;
        }
        if (this.F == null) {
            this.F = new float[fArray.length];
        }
        float f3 = this.M();
        for (int i3 = 0; i3 < (fArray = this.E).length; ++i3) {
            this.F[i3] = Math.max(0.0f, fArray[i3] - f3);
        }
    }

    public static class c
    extends Drawable.ConstantState {
        public o a;
        public w b;
        public k2.a c;
        public ColorFilter d;
        public ColorStateList e = null;
        public ColorStateList f = null;
        public ColorStateList g = null;
        public ColorStateList h = null;
        public PorterDuff.Mode i = PorterDuff.Mode.SRC_IN;
        public Rect j = null;
        public float k = 1.0f;
        public float l = 1.0f;
        public float m;
        public int n = 255;
        public float o = 0.0f;
        public float p = 0.0f;
        public float q = 0.0f;
        public int r = 0;
        public int s = 0;
        public int t = 0;
        public int u = 0;
        public boolean v = false;
        public Paint.Style w = Paint.Style.FILL_AND_STROKE;

        public c(c c3) {
            this.a = c3.a;
            this.b = c3.b;
            this.c = c3.c;
            this.m = c3.m;
            this.d = c3.d;
            this.e = c3.e;
            this.f = c3.f;
            this.i = c3.i;
            this.h = c3.h;
            this.n = c3.n;
            this.k = c3.k;
            this.t = c3.t;
            this.r = c3.r;
            this.v = c3.v;
            this.l = c3.l;
            this.o = c3.o;
            this.p = c3.p;
            this.q = c3.q;
            this.s = c3.s;
            this.u = c3.u;
            this.g = c3.g;
            this.w = c3.w;
            if (c3.j != null) {
                this.j = new Rect(c3.j);
            }
        }

        public c(o o3, k2.a a4) {
            this.a = o3;
            this.c = a4;
        }

        public int getChangingConfigurations() {
            return 0;
        }

        public Drawable newDrawable() {
            i i3 = new i(this);
            v2.i.h(i3, true);
            v2.i.i(i3, true);
            return i3;
        }
    }

    public static interface d {
        public void a(float var1);
    }

    public static class e
    extends x0.i {
        public final int b;

        public e(int n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("cornerSizeAtIndex");
            stringBuilder.append(n3);
            super(stringBuilder.toString());
            this.b = n3;
        }

        public float c(i i3) {
            if (i3.E != null) {
                return i3.E[this.b];
            }
            return 0.0f;
        }

        public void d(i i3, float f3) {
            if (i3.E != null && i3.E[this.b] != f3) {
                ((i)i3).E[this.b] = f3;
                if (i3.G != null) {
                    i3.G.a(i3.B());
                }
                i3.invalidateSelf();
            }
        }
    }
}

