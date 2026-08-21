/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint$Style
 */
package r2;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import j0.a;
import r2.b;
import r2.c;
import r2.f;
import r2.g;
import r2.i;
import r2.j;
import r2.m;
import x0.k;
import x0.l;

public final class h
extends i {
    public static final x0.i E = new x0.i("indicatorLevel"){

        public float c(h h3) {
            return h3.E() * 10000.0f;
        }

        public void d(h h3, float f3) {
            h3.K(f3 / 10000.0f);
            h3.G((int)f3);
        }
    };
    public ValueAnimator A;
    public TimeInterpolator B;
    public TimeInterpolator C;
    public TimeInterpolator D;
    public j t;
    public final l u;
    public final k v;
    public final j.a w;
    public float x;
    public boolean y = false;
    public final ValueAnimator z;

    public h(Context object, b b3, j object2) {
        super((Context)object, b3);
        this.I((j)object2);
        object = new j.a();
        this.w = object;
        ((j.a)object).h = true;
        object = new l();
        this.u = object;
        ((l)object).f(1.0f);
        ((l)object).h(50.0f);
        this.v = object2 = new k((Object)this, E);
        ((k)object2).s((l)object);
        object = new ValueAnimator();
        this.z = object;
        object.setDuration(1000L);
        object.setFloatValues(new float[]{0.0f, 1.0f});
        object.setRepeatCount(-1);
        object.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new f(this, b3));
        if (b3.b(true) && b3.m != 0) {
            object.start();
        }
        this.p(1.0f);
    }

    public static h A(Context context, CircularProgressIndicatorSpec circularProgressIndicatorSpec, c c3) {
        return new h(context, circularProgressIndicatorSpec, c3);
    }

    public static h B(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec, m m3) {
        return new h(context, linearProgressIndicatorSpec, m3);
    }

    public static /* synthetic */ void v(h h3, b b3, ValueAnimator valueAnimator) {
        ((Object)((Object)h3)).getClass();
        if (b3.b(true) && b3.m != 0 && h3.isVisible()) {
            h3.invalidateSelf();
        }
    }

    public static /* synthetic */ void w(h h3, ValueAnimator valueAnimator) {
        h3.w.e = h3.B.getInterpolation(h3.A.getAnimatedFraction());
    }

    public final float C(int n3) {
        float f3 = n3;
        if (f3 >= 1000.0f && f3 <= 9000.0f) {
            return 1.0f;
        }
        return 0.0f;
    }

    public j D() {
        return this.t;
    }

    public final float E() {
        return this.w.b;
    }

    public final void F() {
        if (this.A != null) {
            return;
        }
        Context context = this.c;
        int n3 = z1.c.motionEasingStandardInterpolator;
        TimeInterpolator timeInterpolator = a2.a.a;
        this.C = p2.k.g(context, n3, timeInterpolator);
        this.D = p2.k.g(this.c, z1.c.motionEasingEmphasizedAccelerateInterpolator, timeInterpolator);
        timeInterpolator = new ValueAnimator();
        this.A = timeInterpolator;
        timeInterpolator.setDuration(500L);
        this.A.setFloatValues(new float[]{0.0f, 1.0f});
        this.A.setInterpolator(null);
        this.A.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new g(this));
    }

    public final void G(int n3) {
        if (this.d.b(true)) {
            this.F();
            float f3 = this.C(n3);
            if (f3 != this.x) {
                if (this.A.isRunning()) {
                    this.A.cancel();
                }
                this.x = f3;
                if (f3 == 1.0f) {
                    this.B = this.C;
                    this.A.start();
                    return;
                }
                this.B = this.D;
                this.A.reverse();
                return;
            }
            if (!this.A.isRunning()) {
                this.H(f3);
            }
        }
    }

    public final void H(float f3) {
        this.w.e = f3;
        this.invalidateSelf();
    }

    public void I(j j3) {
        this.t = j3;
    }

    public void J(boolean bl) {
        if (bl && !this.z.isRunning()) {
            this.z.start();
            return;
        }
        if (!bl && this.z.isRunning()) {
            this.z.cancel();
        }
    }

    public final void K(float f3) {
        this.w.b = f3;
        this.invalidateSelf();
    }

    public void L(float f3) {
        this.setLevel((int)(f3 * 10000.0f));
    }

    public void draw(Canvas canvas) {
        if (!this.getBounds().isEmpty() && this.isVisible() && canvas.getClipBounds(this.r)) {
            canvas.save();
            this.t.h(canvas, this.getBounds(), this.h(), this.m(), this.l());
            this.w.f = this.i();
            this.p.setStyle(Paint.Style.FILL);
            this.p.setAntiAlias(true);
            j.a a4 = this.w;
            b b3 = this.d;
            a4.c = b3.e[0];
            int n3 = b3.i;
            if (n3 > 0) {
                if (!(this.t instanceof m)) {
                    n3 = (int)((float)n3 * a.a(this.E(), 0.0f, 0.01f) / 0.01f);
                }
                this.t.d(canvas, this.p, this.E(), 1.0f, this.d.f, this.getAlpha(), n3);
            } else {
                this.t.d(canvas, this.p, 0.0f, 1.0f, b3.f, this.getAlpha(), 0);
            }
            this.t.c(canvas, this.p, this.w, this.getAlpha());
            this.t.b(canvas, this.p, this.d.e[0], this.getAlpha());
            canvas.restore();
        }
    }

    public int getIntrinsicHeight() {
        return this.t.e();
    }

    public int getIntrinsicWidth() {
        return this.t.f();
    }

    public void jumpToCurrentState() {
        this.v.t();
        this.K((float)this.getLevel() / 10000.0f);
    }

    public boolean onLevelChange(int n3) {
        float f3 = this.C(n3);
        if (this.y) {
            this.v.t();
            this.K((float)n3 / 10000.0f);
            this.H(f3);
        } else {
            this.v.j(this.E() * 10000.0f);
            this.v.o(n3);
        }
        return true;
    }

    @Override
    public boolean t(boolean bl, boolean bl2, boolean bl3) {
        bl = super.t(bl, bl2, bl3);
        float f3 = this.e.a(this.c.getContentResolver());
        if (f3 == 0.0f) {
            this.y = true;
            return bl;
        }
        this.y = false;
        this.u.h(50.0f / f3);
        return bl;
    }
}

