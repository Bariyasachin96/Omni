/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.util.Property
 */
package n2;

import a2.c;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import com.google.android.material.loadingindicator.LoadingIndicatorSpec;
import n2.b;
import n2.c;
import x0.i;
import x0.k;
import x0.l;

public class a {
    public static final Property i = new Property(Float.class, "animationFraction"){

        public Float a(a a4) {
            return Float.valueOf(a4.f());
        }

        public void b(a a4, Float f3) {
            a4.l(f3.floatValue());
        }
    };
    public static final i j = new i("morphFactor"){

        public float c(a a4) {
            return a4.g();
        }

        public void d(a a4, float f3) {
            a4.m(f3);
        }
    };
    public int a;
    public float b;
    public float c;
    public ObjectAnimator d;
    public k e;
    public LoadingIndicatorSpec f;
    public b g;
    public c.a h;

    public a(LoadingIndicatorSpec loadingIndicatorSpec) {
        this.f = loadingIndicatorSpec;
        this.h = new c.a();
    }

    public static /* synthetic */ int a(a a4) {
        int n3;
        a4.a = n3 = a4.a + 1;
        return n3;
    }

    public void e() {
        Object object = this.d;
        if (object != null) {
            object.cancel();
        }
        if ((object = this.e) != null) {
            ((k)object).t();
        }
    }

    public final float f() {
        return this.b;
    }

    public final float g() {
        return this.c;
    }

    public void h() {
        this.k();
    }

    public final void i() {
        if (this.e == null) {
            this.e = (k)new k(this, j).s(new l().h(200.0f).f(0.6f)).h(0.01f);
        }
        if (this.d == null) {
            ObjectAnimator objectAnimator;
            this.d = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)i, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration(650L);
            this.d.setInterpolator(null);
            this.d.setRepeatCount(-1);
            this.d.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final a a;
                {
                    this.a = a4;
                }

                public void onAnimationRepeat(Animator animator) {
                    super.onAnimationRepeat(animator);
                    this.a.e.o(n2.a.a(this.a));
                }
            });
        }
    }

    public void j(b b3) {
        this.g = b3;
    }

    public void k() {
        this.a = 1;
        this.m(0.0f);
        this.h.a = this.f.e[0];
    }

    public void l(float f3) {
        this.b = f3;
        this.o((int)(f3 * 650.0f));
        b b3 = this.g;
        if (b3 != null) {
            b3.invalidateSelf();
        }
    }

    public void m(float f3) {
        this.c = f3;
        this.p();
        b b3 = this.g;
        if (b3 != null) {
            b3.invalidateSelf();
        }
    }

    public void n() {
        this.i();
        this.k();
        this.e.o(this.a);
        this.d.start();
    }

    public final void o(int n3) {
        float f3;
        float f4 = this.a - 1;
        float f5 = this.c;
        float f6 = f3 = (float)n3 / 650.0f;
        if (f3 == 1.0f) {
            f6 = 0.0f;
        }
        this.h.c = (f4 * 140.0f + f6 * 50.0f + (f5 - f4) * 90.0f) % 360.0f;
    }

    public final void p() {
        c.a a4 = this.h;
        a4.b = this.c;
        int n3 = this.a;
        int[] nArray = this.f.e;
        int n4 = (n3 - 1) % nArray.length;
        int n5 = nArray.length;
        n3 = nArray[n4];
        n4 = nArray[(n4 + 1) % n5];
        a4.a = a2.c.b().a(j0.a.a(this.c - (float)(this.a - 1), 0.0f, 1.0f), n3, n4);
    }
}

