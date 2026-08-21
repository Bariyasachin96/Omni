/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.graphics.ColorFilter
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.Animatable
 *  android.graphics.drawable.Drawable
 *  android.os.SystemClock
 *  android.util.Property
 */
package r2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Property;
import java.util.ArrayList;
import java.util.List;
import r2.a;
import r2.b;
import r2.h;

public abstract class i
extends Drawable
implements Animatable {
    public static final Property s = new Property(Float.class, "growFraction"){

        public Float a(i i3) {
            return Float.valueOf(i3.h());
        }

        public void b(i i3, Float f3) {
            i3.p(f3.floatValue());
        }
    };
    public final Context c;
    public final b d;
    public a e;
    public ValueAnimator f;
    public ValueAnimator g;
    public boolean h;
    public boolean i;
    public float j;
    public float k = -1.0f;
    public List l;
    public n1.b m;
    public boolean n;
    public float o;
    public final Paint p = new Paint();
    public int q;
    public Rect r;

    public i(Context context, b b3) {
        this.c = context;
        this.d = b3;
        this.r = new Rect();
        this.e = new a();
        this.setAlpha(255);
    }

    public final void d(ValueAnimator ... valueAnimatorArray) {
        boolean bl = this.n;
        this.n = true;
        int n3 = valueAnimatorArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            valueAnimatorArray[i3].cancel();
        }
        this.n = bl;
    }

    public final void e() {
        Object object = this.m;
        if (object != null) {
            ((n1.b)object).b(this);
        }
        if ((object = this.l) != null && !this.n) {
            object = object.iterator();
            while (object.hasNext()) {
                ((n1.b)object.next()).b(this);
            }
        }
    }

    public final void f() {
        Object object = this.m;
        if (object != null) {
            ((n1.b)object).c(this);
        }
        if ((object = this.l) != null && !this.n) {
            object = object.iterator();
            while (object.hasNext()) {
                ((n1.b)object.next()).c(this);
            }
        }
    }

    public final void g(ValueAnimator ... valueAnimatorArray) {
        boolean bl = this.n;
        this.n = true;
        int n3 = valueAnimatorArray.length;
        for (int i3 = 0; i3 < n3; ++i3) {
            valueAnimatorArray[i3].end();
        }
        this.n = bl;
    }

    public int getAlpha() {
        return this.q;
    }

    public int getOpacity() {
        return -3;
    }

    public float h() {
        if (!this.d.d() && !this.d.c()) {
            return 1.0f;
        }
        if (!this.i && !this.h) {
            return this.o;
        }
        return this.j;
    }

    public float i() {
        float f3 = this.k;
        if (f3 > 0.0f) {
            return f3;
        }
        if (this.d.b(this.k()) && this.d.m != 0 && (f3 = this.e.a(this.c.getContentResolver())) > 0.0f) {
            float f4;
            int n3 = this.k() ? this.d.j : this.d.k;
            n3 = (int)((float)n3 * 1000.0f / (float)this.d.m * f3);
            f3 = f4 = (float)(SystemClock.uptimeMillis() % (long)n3) / (float)n3;
            if (f4 < 0.0f) {
                f3 = f4 % 1.0f + 1.0f;
            }
            return f3;
        }
        return 0.0f;
    }

    public boolean isRunning() {
        return this.m() || this.l();
        {
        }
    }

    public boolean j() {
        return this.s(false, false, false);
    }

    public final boolean k() {
        return this instanceof h;
    }

    public boolean l() {
        ValueAnimator valueAnimator = this.g;
        return valueAnimator != null && valueAnimator.isRunning() || this.i;
    }

    public boolean m() {
        ValueAnimator valueAnimator = this.f;
        return valueAnimator != null && valueAnimator.isRunning() || this.h;
    }

    public final void n() {
        ObjectAnimator objectAnimator;
        if (this.f == null) {
            objectAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (Property)s, (float[])new float[]{0.0f, 1.0f});
            this.f = objectAnimator;
            objectAnimator.setDuration(500L);
            this.f.setInterpolator(a2.a.b);
            this.r(this.f);
        }
        if (this.g == null) {
            objectAnimator = ObjectAnimator.ofFloat((Object)((Object)this), (Property)s, (float[])new float[]{1.0f, 0.0f});
            this.g = objectAnimator;
            objectAnimator.setDuration(500L);
            this.g.setInterpolator(a2.a.b);
            this.q(this.g);
        }
    }

    public void o(n1.b b3) {
        if (this.l == null) {
            this.l = new ArrayList();
        }
        if (!this.l.contains(b3)) {
            this.l.add(b3);
        }
    }

    public void p(float f3) {
        if (this.o != f3) {
            this.o = f3;
            this.invalidateSelf();
        }
    }

    public final void q(ValueAnimator valueAnimator) {
        ValueAnimator valueAnimator2 = this.g;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            throw new IllegalArgumentException("Cannot set hideAnimator while the current hideAnimator is running.");
        }
        this.g = valueAnimator;
        valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                i.super.setVisible(false, false);
                this.a.e();
            }
        });
    }

    public final void r(ValueAnimator valueAnimator) {
        ValueAnimator valueAnimator2 = this.f;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            throw new IllegalArgumentException("Cannot set showAnimator while the current showAnimator is running.");
        }
        this.f = valueAnimator;
        valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(this){
            public final i a;
            {
                this.a = i3;
            }

            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                this.a.f();
            }
        });
    }

    public boolean s(boolean bl, boolean bl2, boolean bl3) {
        float f3 = this.e.a(this.c.getContentResolver());
        bl3 = bl3 && f3 > 0.0f;
        return this.t(bl, bl2, bl3);
    }

    public void setAlpha(int n3) {
        this.q = n3;
        this.invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.p.setColorFilter(colorFilter);
        this.invalidateSelf();
    }

    public boolean setVisible(boolean bl, boolean bl2) {
        return this.s(bl, bl2, true);
    }

    public void start() {
        this.t(true, true, false);
    }

    public void stop() {
        this.t(false, true, false);
    }

    public boolean t(boolean bl, boolean bl2, boolean bl3) {
        ValueAnimator valueAnimator;
        block12: {
            block11: {
                this.n();
                boolean bl4 = this.isVisible();
                boolean bl5 = false;
                if (!bl4 && !bl) {
                    return false;
                }
                valueAnimator = bl ? this.f : this.g;
                ValueAnimator valueAnimator2 = bl ? this.g : this.f;
                if (!bl3) {
                    if (valueAnimator2.isRunning()) {
                        this.d(valueAnimator2);
                    }
                    if (valueAnimator.isRunning()) {
                        valueAnimator.end();
                    } else {
                        this.g(valueAnimator);
                    }
                    return super.setVisible(bl, false);
                }
                if (valueAnimator.isRunning()) {
                    return false;
                }
                if (!bl) break block11;
                bl3 = bl5;
                if (!super.setVisible(bl, false)) break block12;
            }
            bl3 = true;
        }
        if (!(bl = bl ? this.d.d() : this.d.c())) {
            this.g(valueAnimator);
            return bl3;
        }
        if (!bl2 && valueAnimator.isPaused()) {
            valueAnimator.resume();
            return bl3;
        }
        valueAnimator.start();
        return bl3;
    }

    public boolean u(n1.b b3) {
        List list = this.l;
        if (list != null && list.contains(b3)) {
            this.l.remove(b3);
            if (this.l.isEmpty()) {
                this.l = null;
            }
            return true;
        }
        return false;
    }
}

