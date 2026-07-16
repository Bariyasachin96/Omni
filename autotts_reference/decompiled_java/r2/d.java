/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.util.Property
 */
package r2;

import a2.c;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.util.Property;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import r2.b;
import r2.j;
import r2.k;

public final class d
extends k {
    public static final int[] k = new int[]{0, 1350, 2700, 4050};
    public static final int[] l = new int[]{667, 2017, 3367, 4717};
    public static final int[] m = new int[]{1000, 2350, 3700, 5050};
    public static final Property n = new Property(Float.class, "animationFraction"){

        public Float a(d d3) {
            return Float.valueOf(d3.o());
        }

        public void b(d d3, Float f3) {
            d3.t(f3.floatValue());
        }
    };
    public static final Property o = new Property(Float.class, "completeEndFraction"){

        public Float a(d d3) {
            return Float.valueOf(d3.p());
        }

        public void b(d d3, Float f3) {
            d3.u(f3.floatValue());
        }
    };
    public ObjectAnimator c;
    public ObjectAnimator d;
    public final d1.b e;
    public final b f;
    public int g = 0;
    public float h;
    public float i;
    public n1.b j = null;

    public d(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.f = circularProgressIndicatorSpec;
        this.e = new d1.b();
    }

    public static /* synthetic */ int j(d d3, int n3) {
        d3.g = n3;
        return n3;
    }

    @Override
    public void a() {
        ObjectAnimator objectAnimator = this.c;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    @Override
    public void c() {
        this.v();
        this.s();
    }

    @Override
    public void d(n1.b b3) {
        this.j = b3;
    }

    @Override
    public void f() {
        ObjectAnimator objectAnimator = this.d;
        if (objectAnimator != null && !objectAnimator.isRunning()) {
            if (this.a.isVisible()) {
                this.d.start();
                return;
            }
            this.a();
        }
    }

    @Override
    public void g() {
        this.q();
        this.s();
        this.c.start();
    }

    @Override
    public void h() {
        this.j = null;
    }

    public final float o() {
        return this.h;
    }

    public final float p() {
        return this.i;
    }

    public final void q() {
        ObjectAnimator objectAnimator;
        if (this.c == null) {
            this.c = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)n, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration((long)(this.f.n * 5400.0f));
            this.c.setInterpolator(null);
            this.c.setRepeatCount(-1);
            this.c.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final d a;
                {
                    this.a = d3;
                }

                public void onAnimationRepeat(Animator object) {
                    super.onAnimationRepeat(object);
                    object = this.a;
                    r2.d.j((d)object, (((d)object).g + 4) % ((d)this.a).f.e.length);
                }
            });
        }
        if (this.d == null) {
            this.d = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)o, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration((long)(this.f.n * 333.0f));
            this.d.setInterpolator((TimeInterpolator)this.e);
            this.d.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final d a;
                {
                    this.a = d3;
                }

                public void onAnimationEnd(Animator object) {
                    super.onAnimationEnd(object);
                    this.a.a();
                    object = this.a;
                    n1.b b3 = object.j;
                    if (b3 != null) {
                        b3.b(object.a);
                    }
                }
            });
        }
    }

    public final void r(int n3) {
        for (int i3 = 0; i3 < 4; ++i3) {
            float f3 = this.b(n3, m[i3], 333);
            if (!(f3 > 0.0f) || !(f3 < 1.0f)) continue;
            n3 = this.g;
            int[] nArray = this.f.e;
            i3 = (i3 + n3) % nArray.length;
            int n4 = nArray.length;
            n3 = nArray[i3];
            i3 = nArray[(i3 + 1) % n4];
            f3 = this.e.getInterpolation(f3);
            ((j.a)this.b.get((int)0)).c = a2.c.b().a(f3, n3, i3);
            return;
        }
    }

    public void s() {
        this.g = 0;
        ((j.a)this.b.get((int)0)).c = this.f.e[0];
        this.i = 0.0f;
    }

    public void t(float f3) {
        this.h = f3;
        int n3 = (int)(f3 * 5400.0f);
        this.w(n3);
        this.r(n3);
        this.a.invalidateSelf();
    }

    public final void u(float f3) {
        this.i = f3;
    }

    public final void v() {
        this.q();
        this.c.setDuration((long)(this.f.n * 5400.0f));
        this.d.setDuration((long)(this.f.n * 333.0f));
    }

    public final void w(int n3) {
        Object object = this.b;
        object = (j.a)object.get(0);
        float f3 = this.h;
        ((j.a)object).a = f3 * 1520.0f - 20.0f;
        ((j.a)object).b = f3 * 1520.0f;
        for (int i3 = 0; i3 < 4; ++i3) {
            f3 = this.b(n3, k[i3], 667);
            ((j.a)object).b += this.e.getInterpolation(f3) * 250.0f;
            f3 = this.b(n3, l[i3], 667);
            ((j.a)object).a += this.e.getInterpolation(f3) * 250.0f;
        }
        float f4 = ((j.a)object).a;
        f3 = ((j.a)object).b;
        ((j.a)object).a = (f4 + (f3 - f4) * this.i) / 360.0f;
        ((j.a)object).b = f3 / 360.0f;
    }
}

