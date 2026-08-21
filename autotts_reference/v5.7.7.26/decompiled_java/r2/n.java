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
package r2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Property;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import r2.b;
import r2.j;
import r2.k;

public final class n
extends k {
    public static final Property i = new Property(Float.class, "animationFraction"){

        public Float a(n n3) {
            return Float.valueOf(n3.n());
        }

        public void b(n n3, Float f3) {
            n3.r(f3.floatValue());
        }
    };
    public ObjectAnimator c;
    public d1.b d;
    public final b e;
    public int f = 1;
    public boolean g;
    public float h;

    public n(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(3);
        this.e = linearProgressIndicatorSpec;
        this.d = new d1.b();
    }

    public static /* synthetic */ int j(n n3, int n4) {
        n3.f = n4;
        return n4;
    }

    public static /* synthetic */ boolean l(n n3, boolean bl) {
        n3.g = bl;
        return bl;
    }

    private float n() {
        return this.h;
    }

    private void o() {
        if (this.c == null) {
            ObjectAnimator objectAnimator;
            this.c = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)i, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration((long)(this.e.n * 333.0f));
            this.c.setInterpolator(null);
            this.c.setRepeatCount(-1);
            this.c.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final n a;
                {
                    this.a = n3;
                }

                public void onAnimationRepeat(Animator object) {
                    super.onAnimationRepeat(object);
                    object = this.a;
                    n.j((n)object, (((n)object).f + 1) % ((n)this.a).e.e.length);
                    n.l(this.a, true);
                }
            });
        }
    }

    private void s() {
        this.o();
        this.c.setDuration((long)(this.e.n * 333.0f));
    }

    private void t(int n3) {
        float f3;
        ((j.a)this.b.get((int)0)).a = 0.0f;
        float f4 = this.b(n3, 0, 667);
        j.a a4 = (j.a)this.b.get(0);
        j.a a5 = (j.a)this.b.get(1);
        a5.a = f3 = this.d.getInterpolation(f4);
        a4.b = f3;
        a5 = (j.a)this.b.get(1);
        a4 = (j.a)this.b.get(2);
        a4.a = f3 = this.d.getInterpolation(f4 + 0.49925038f);
        a5.b = f3;
        ((j.a)this.b.get((int)2)).b = 1.0f;
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
        this.s();
        this.q();
    }

    @Override
    public void d(n1.b b3) {
    }

    @Override
    public void f() {
    }

    @Override
    public void g() {
        this.o();
        this.q();
        this.c.start();
    }

    @Override
    public void h() {
    }

    public final void p() {
        if (this.g && ((j.a)this.b.get((int)1)).b < 1.0f) {
            ((j.a)this.b.get((int)2)).c = ((j.a)this.b.get((int)1)).c;
            ((j.a)this.b.get((int)1)).c = ((j.a)this.b.get((int)0)).c;
            ((j.a)this.b.get((int)0)).c = this.e.e[this.f];
            this.g = false;
        }
    }

    public void q() {
        this.g = true;
        this.f = 1;
        for (j.a a4 : this.b) {
            b b3 = this.e;
            a4.c = b3.e[0];
            a4.d = b3.i / 2;
        }
    }

    public void r(float f3) {
        this.h = f3;
        this.t((int)(f3 * 333.0f));
        this.p();
        this.a.invalidateSelf();
    }
}

