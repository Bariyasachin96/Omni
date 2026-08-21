/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.util.Property
 */
package r2;

import a2.c;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.Property;
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec;
import o2.a;
import r2.b;
import r2.j;
import r2.k;

public final class e
extends k {
    public static final TimeInterpolator k = a2.a.b;
    public static final int[] l = new int[]{0, 1500, 3000, 4500};
    public static final float[] m = new float[]{0.1f, 0.87f};
    public static final Property n = new Property(Float.class, "animationFraction"){

        public Float a(e e3) {
            return Float.valueOf(e3.p());
        }

        public void b(e e3, Float f3) {
            e3.u(f3.floatValue());
        }
    };
    public static final Property o = new Property(Float.class, "completeEndFraction"){

        public Float a(e e3) {
            return Float.valueOf(e3.q());
        }

        public void b(e e3, Float f3) {
            e3.v(f3.floatValue());
        }
    };
    public ObjectAnimator c;
    public ObjectAnimator d;
    public final TimeInterpolator e;
    public final b f;
    public int g = 0;
    public float h;
    public float i;
    public n1.b j = null;

    public e(Context context, CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(1);
        this.f = circularProgressIndicatorSpec;
        this.e = p2.k.g(context, z1.c.motionEasingStandardInterpolator, k);
    }

    public static /* synthetic */ int j(e e3, int n3) {
        e3.g = n3;
        return n3;
    }

    private float p() {
        return this.h;
    }

    private float q() {
        return this.i;
    }

    private void r() {
        ObjectAnimator objectAnimator;
        if (this.c == null) {
            this.c = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)n, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration((long)(this.f.n * 6000.0f));
            this.c.setInterpolator(null);
            this.c.setRepeatCount(-1);
            this.c.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final e a;
                {
                    this.a = e3;
                }

                public void onAnimationRepeat(Animator object) {
                    super.onAnimationRepeat(object);
                    object = this.a;
                    r2.e.j((e)object, (((e)object).g + l.length) % ((e)this.a).f.e.length);
                }
            });
        }
        if (this.d == null) {
            this.d = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)o, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration((long)(this.f.n * 500.0f));
            this.d.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final e a;
                {
                    this.a = e3;
                }

                public void onAnimationEnd(Animator object) {
                    super.onAnimationEnd((Animator)object);
                    this.a.a();
                    e e3 = this.a;
                    object = e3.j;
                    if (object != null) {
                        ((n1.b)object).b(e3.a);
                    }
                }
            });
        }
    }

    private void s(int n3) {
        int[] nArray;
        for (int i3 = 0; i3 < (nArray = l).length; ++i3) {
            float f3 = this.b(n3, nArray[i3], 100);
            if (!(f3 >= 0.0f) || !(f3 <= 1.0f)) continue;
            n3 = this.g;
            nArray = this.f.e;
            i3 = (i3 + n3) % nArray.length;
            int n4 = nArray.length;
            n3 = nArray[i3];
            i3 = nArray[(i3 + 1) % n4];
            f3 = this.e.getInterpolation(f3);
            ((j.a)this.b.get((int)0)).c = a2.c.b().a(f3, n3, i3);
            return;
        }
    }

    private void v(float f3) {
        this.i = f3;
    }

    private void w() {
        this.r();
        this.c.setDuration((long)(this.f.n * 6000.0f));
        this.d.setDuration((long)(this.f.n * 500.0f));
    }

    private void x(int n3) {
        j.a a4 = (j.a)this.b.get(0);
        float f3 = this.h;
        Object[] objectArray = l;
        int n4 = objectArray.length;
        float f4 = 0.0f;
        for (int i3 = 0; i3 < n4; ++i3) {
            int n5 = objectArray[i3];
            f4 += this.e.getInterpolation(this.b(n3, n5, 500)) * 90.0f;
        }
        a4.g = f3 * 1080.0f + f4;
        f3 = this.e.getInterpolation(this.b(n3, 0, 3000));
        f4 = this.e.getInterpolation(this.b(n3, 3000, 3000));
        a4.a = 0.0f;
        objectArray = m;
        a4.b = f3 = o2.a.f(objectArray[0], objectArray[1], f3 - f4);
        f4 = this.i;
        if (f4 > 0.0f) {
            a4.b = f3 * (1.0f - f4);
        }
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
        this.w();
        this.t();
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
        this.r();
        this.t();
        this.c.start();
    }

    @Override
    public void h() {
        this.j = null;
    }

    public void t() {
        this.g = 0;
        ((j.a)this.b.get((int)0)).c = this.f.e[0];
        this.i = 0.0f;
    }

    public void u(float f3) {
        this.h = f3;
        int n3 = (int)(f3 * 6000.0f);
        this.x(n3);
        this.s(n3);
        this.a.invalidateSelf();
    }
}

