/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.res.Resources
 *  android.os.SystemClock
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewConfiguration
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnimationUtils
 *  android.view.animation.Interpolator
 */
package androidx.core.widget;

import android.content.res.Resources;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import o0.x0;

public abstract class a
implements View.OnTouchListener {
    public static final int t = ViewConfiguration.getTapTimeout();
    public final a c = new a();
    public final Interpolator d = new AccelerateInterpolator();
    public final View e;
    public Runnable f;
    public float[] g = new float[]{0.0f, 0.0f};
    public float[] h = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    public int i;
    public int j;
    public float[] k = new float[]{0.0f, 0.0f};
    public float[] l = new float[]{0.0f, 0.0f};
    public float[] m = new float[]{Float.MAX_VALUE, Float.MAX_VALUE};
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean s;

    public a(View view) {
        this.e = view;
        float f3 = Resources.getSystem().getDisplayMetrics().density;
        int n3 = (int)(1575.0f * f3 + 0.5f);
        int n4 = (int)(f3 * 315.0f + 0.5f);
        f3 = n3;
        this.o(f3, f3);
        f3 = n4;
        this.p(f3, f3);
        this.l(1);
        this.n(Float.MAX_VALUE, Float.MAX_VALUE);
        this.s(0.2f, 0.2f);
        this.t(1.0f, 1.0f);
        this.k(t);
        this.r(500);
        this.q(500);
    }

    public static float e(float f3, float f4, float f5) {
        if (f3 > f5) {
            return f5;
        }
        if (f3 < f4) {
            return f4;
        }
        return f3;
    }

    public static int f(int n3, int n4, int n5) {
        if (n3 > n5) {
            return n5;
        }
        if (n3 < n4) {
            return n4;
        }
        return n3;
    }

    public abstract boolean a(int var1);

    public abstract boolean b(int var1);

    public void c() {
        long l3 = SystemClock.uptimeMillis();
        MotionEvent motionEvent = MotionEvent.obtain((long)l3, (long)l3, (int)3, (float)0.0f, (float)0.0f, (int)0);
        this.e.onTouchEvent(motionEvent);
        motionEvent.recycle();
    }

    public final float d(int n3, float f3, float f4, float f5) {
        float f6 = this.h(this.g[n3], f4, this.h[n3], f3);
        float f7 = f6 - 0.0f;
        float f8 = f7 == 0.0f ? 0 : (f7 > 0.0f ? 1 : -1);
        if (f8 == false) {
            return 0.0f;
        }
        float f9 = this.k[n3];
        f3 = this.l[n3];
        f4 = this.m[n3];
        f5 = f9 * f5;
        if (f8 > 0) {
            return a.e(f6 * f5, f3, f4);
        }
        return -a.e(-f6 * f5, f3, f4);
    }

    public final float g(float f3, float f4) {
        if (f4 == 0.0f) {
            return 0.0f;
        }
        int n3 = this.i;
        if (n3 != 0 && n3 != 1) {
            if (n3 == 2 && f3 < 0.0f) {
                return f3 / -f4;
            }
        } else if (f3 < f4) {
            if (f3 >= 0.0f) {
                return 1.0f - f3 / f4;
            }
            if (this.q && n3 == 1) {
                return 1.0f;
            }
        }
        return 0.0f;
    }

    public final float h(float f3, float f4, float f5, float f6) {
        block4: {
            block3: {
                block2: {
                    f5 = a.e(f3 * f4, 0.0f, f5);
                    f3 = this.g(f6, f5);
                    f3 = this.g(f4 - f6, f5) - f3;
                    if (!(f3 < 0.0f)) break block2;
                    f3 = -this.d.getInterpolation(-f3);
                    break block3;
                }
                if (!(f3 > 0.0f)) break block4;
                f3 = this.d.getInterpolation(f3);
            }
            return a.e(f3, -1.0f, 1.0f);
        }
        return 0.0f;
    }

    public final void i() {
        if (this.o) {
            this.q = false;
            return;
        }
        this.c.i();
    }

    public abstract void j(int var1, int var2);

    public a k(int n3) {
        this.j = n3;
        return this;
    }

    public a l(int n3) {
        this.i = n3;
        return this;
    }

    public a m(boolean bl) {
        if (this.r && !bl) {
            this.i();
        }
        this.r = bl;
        return this;
    }

    public a n(float f3, float f4) {
        float[] fArray = this.h;
        fArray[0] = f3;
        fArray[1] = f4;
        return this;
    }

    public a o(float f3, float f4) {
        float[] fArray = this.m;
        fArray[0] = f3 / 1000.0f;
        fArray[1] = f4 / 1000.0f;
        return this;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        block7: {
            block6: {
                block4: {
                    block5: {
                        if (!this.r) {
                            return false;
                        }
                        int n3 = motionEvent.getActionMasked();
                        if (n3 == 0) break block4;
                        if (n3 == 1) break block5;
                        if (n3 == 2) break block6;
                        if (n3 != 3) break block7;
                    }
                    this.i();
                    break block7;
                }
                this.p = true;
                this.n = false;
            }
            float f3 = this.d(0, motionEvent.getX(), view.getWidth(), this.e.getWidth());
            float f4 = this.d(1, motionEvent.getY(), view.getHeight(), this.e.getHeight());
            this.c.l(f3, f4);
            if (!this.q && this.u()) {
                this.v();
            }
        }
        return this.s && this.q;
    }

    public a p(float f3, float f4) {
        float[] fArray = this.l;
        fArray[0] = f3 / 1000.0f;
        fArray[1] = f4 / 1000.0f;
        return this;
    }

    public a q(int n3) {
        this.c.j(n3);
        return this;
    }

    public a r(int n3) {
        this.c.k(n3);
        return this;
    }

    public a s(float f3, float f4) {
        float[] fArray = this.g;
        fArray[0] = f3;
        fArray[1] = f4;
        return this;
    }

    public a t(float f3, float f4) {
        float[] fArray = this.k;
        fArray[0] = f3 / 1000.0f;
        fArray[1] = f4 / 1000.0f;
        return this;
    }

    public boolean u() {
        a a4 = this.c;
        int n3 = a4.f();
        int n4 = a4.d();
        return n3 != 0 && this.b(n3) || n4 != 0 && this.a(n4);
    }

    public final void v() {
        int n3;
        if (this.f == null) {
            this.f = new b(this);
        }
        this.q = true;
        this.o = true;
        if (!this.n && (n3 = this.j) > 0) {
            x0.a0(this.e, this.f, n3);
        } else {
            this.f.run();
        }
        this.n = true;
    }

    public static class a {
        public int a;
        public int b;
        public float c;
        public float d;
        public long e = Long.MIN_VALUE;
        public long f = 0L;
        public int g = 0;
        public int h = 0;
        public long i = -1L;
        public float j;
        public int k;

        public void a() {
            if (this.f != 0L) {
                long l3 = AnimationUtils.currentAnimationTimeMillis();
                float f3 = this.g(this.e(l3));
                long l4 = this.f;
                this.f = l3;
                f3 = (float)(l3 - l4) * f3;
                this.g = (int)(this.c * f3);
                this.h = (int)(f3 * this.d);
                return;
            }
            throw new RuntimeException("Cannot compute scroll delta before calling start()");
        }

        public int b() {
            return this.g;
        }

        public int c() {
            return this.h;
        }

        public int d() {
            float f3 = this.c;
            return (int)(f3 / Math.abs(f3));
        }

        public final float e(long l3) {
            long l4 = this.e;
            if (l3 < l4) {
                return 0.0f;
            }
            long l5 = this.i;
            if (l5 >= 0L && l3 >= l5) {
                float f3 = this.j;
                return 1.0f - f3 + f3 * androidx.core.widget.a.e((float)(l3 - l5) / (float)this.k, 0.0f, 1.0f);
            }
            return androidx.core.widget.a.e((float)(l3 - l4) / (float)this.a, 0.0f, 1.0f) * 0.5f;
        }

        public int f() {
            float f3 = this.d;
            return (int)(f3 / Math.abs(f3));
        }

        public final float g(float f3) {
            return -4.0f * f3 * f3 + f3 * 4.0f;
        }

        public boolean h() {
            return this.i > 0L && AnimationUtils.currentAnimationTimeMillis() > this.i + (long)this.k;
        }

        public void i() {
            long l3 = AnimationUtils.currentAnimationTimeMillis();
            this.k = androidx.core.widget.a.f((int)(l3 - this.e), 0, this.b);
            this.j = this.e(l3);
            this.i = l3;
        }

        public void j(int n3) {
            this.b = n3;
        }

        public void k(int n3) {
            this.a = n3;
        }

        public void l(float f3, float f4) {
            this.c = f3;
            this.d = f4;
        }

        public void m() {
            long l3;
            this.e = l3 = AnimationUtils.currentAnimationTimeMillis();
            this.i = -1L;
            this.f = l3;
            this.j = 0.5f;
            this.g = 0;
            this.h = 0;
        }
    }

    public class b
    implements Runnable {
        public final a c;

        public b(a a4) {
            this.c = a4;
        }

        @Override
        public void run() {
            a a4;
            a a5 = this.c;
            if (!a5.q) {
                return;
            }
            if (a5.o) {
                a5.o = false;
                a5.c.m();
            }
            if (!(a4 = this.c.c).h() && this.c.u()) {
                a5 = this.c;
                if (a5.p) {
                    a5.p = false;
                    a5.c();
                }
                a4.a();
                int n3 = a4.b();
                int n4 = a4.c();
                this.c.j(n3, n4);
                x0.Z(this.c.e, this);
                return;
            }
            this.c.q = false;
        }
    }
}

