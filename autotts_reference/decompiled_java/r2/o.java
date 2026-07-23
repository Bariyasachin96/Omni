/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.content.Context
 *  android.util.Property
 *  android.view.animation.Interpolator
 */
package r2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Property;
import android.view.animation.Interpolator;
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec;
import j0.a;
import java.util.Iterator;
import n1.d;
import r2.b;
import r2.j;
import r2.k;

public final class o
extends k {
    public static final int[] k = new int[]{533, 567, 850, 750};
    public static final int[] l = new int[]{1267, 1000, 333, 0};
    public static final Property m = new Property(Float.class, "animationFraction"){

        public Float a(o o3) {
            return Float.valueOf(o3.n());
        }

        public void b(o o3, Float f3) {
            o3.r(f3.floatValue());
        }
    };
    public ObjectAnimator c;
    public ObjectAnimator d;
    public final Interpolator[] e;
    public final b f;
    public int g = 0;
    public boolean h;
    public float i;
    public n1.b j = null;

    public o(Context context, LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(2);
        this.f = linearProgressIndicatorSpec;
        this.e = new Interpolator[]{n1.d.a(context, z1.a.linear_indeterminate_line1_head_interpolator), n1.d.a(context, z1.a.linear_indeterminate_line1_tail_interpolator), n1.d.a(context, z1.a.linear_indeterminate_line2_head_interpolator), n1.d.a(context, z1.a.linear_indeterminate_line2_tail_interpolator)};
    }

    public static /* synthetic */ int j(o o3, int n3) {
        o3.g = n3;
        return n3;
    }

    public static /* synthetic */ boolean l(o o3, boolean bl) {
        o3.h = bl;
        return bl;
    }

    private float n() {
        return this.i;
    }

    private void o() {
        ObjectAnimator objectAnimator;
        if (this.c == null) {
            this.c = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)m, (float[])new float[]{0.0f, 1.0f});
            objectAnimator.setDuration((long)(this.f.n * 1800.0f));
            this.c.setInterpolator(null);
            this.c.setRepeatCount(-1);
            this.c.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final o a;
                {
                    this.a = o3;
                }

                public void onAnimationRepeat(Animator object) {
                    super.onAnimationRepeat(object);
                    object = this.a;
                    o.j((o)object, (((o)object).g + 1) % ((o)this.a).f.e.length);
                    o.l(this.a, true);
                }
            });
        }
        if (this.d == null) {
            this.d = objectAnimator = ObjectAnimator.ofFloat((Object)this, (Property)m, (float[])new float[]{1.0f});
            objectAnimator.setDuration((long)(this.f.n * 1800.0f));
            this.d.setInterpolator(null);
            this.d.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
                public final o a;
                {
                    this.a = o3;
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

    private void p() {
        if (this.h) {
            Iterator iterator = this.b.iterator();
            while (iterator.hasNext()) {
                ((j.a)iterator.next()).c = this.f.e[this.g];
            }
            this.h = false;
        }
    }

    private void s() {
        this.o();
        this.c.setDuration((long)(this.f.n * 1800.0f));
        this.d.setDuration((long)(this.f.n * 1800.0f));
    }

    private void t(int n3) {
        for (int i3 = 0; i3 < this.b.size(); ++i3) {
            j.a a4 = (j.a)this.b.get(i3);
            int[] nArray = l;
            int n4 = i3 * 2;
            int n5 = nArray[n4];
            int[] nArray2 = k;
            float f3 = this.b(n3, n5, nArray2[n4]);
            a4.a = j0.a.a(this.e[n4].getInterpolation(f3), 0.0f, 1.0f);
            f3 = this.b(n3, nArray[++n4], nArray2[n4]);
            a4.b = j0.a.a(this.e[n4].getInterpolation(f3), 0.0f, 1.0f);
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
        this.s();
        this.q();
    }

    @Override
    public void d(n1.b b3) {
        this.j = b3;
    }

    @Override
    public void f() {
        ObjectAnimator objectAnimator = this.d;
        if (objectAnimator != null && !objectAnimator.isRunning()) {
            this.a();
            if (this.a.isVisible()) {
                this.d.setFloatValues(new float[]{this.i, 1.0f});
                this.d.setDuration((long)((1.0f - this.i) * 1800.0f));
                this.d.start();
            }
        }
    }

    @Override
    public void g() {
        this.o();
        this.q();
        this.c.start();
    }

    @Override
    public void h() {
        this.j = null;
    }

    public void q() {
        this.g = 0;
        Iterator iterator = this.b.iterator();
        while (iterator.hasNext()) {
            ((j.a)iterator.next()).c = this.f.e[0];
        }
    }

    public void r(float f3) {
        this.i = f3;
        this.t((int)(f3 * 1800.0f));
        this.p();
        this.a.invalidateSelf();
    }
}

