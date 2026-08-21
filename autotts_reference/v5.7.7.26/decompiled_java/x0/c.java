/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.animation.ValueAnimator$DurationScaleChangeListener
 *  android.os.Build$VERSION
 *  android.os.Looper
 *  android.os.SystemClock
 *  android.view.Choreographer
 *  android.view.Choreographer$FrameCallback
 */
package x0;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import java.util.ArrayList;
import o.r;
import x0.g;
import x0.j;

public class c {
    public static final ThreadLocal j = new ThreadLocal();
    public final r a = new r();
    public final ArrayList b = new ArrayList();
    public final b c = new b(this, null);
    public final Runnable d = new x0.b(this);
    public j e;
    public long f = 0L;
    public boolean g = false;
    public float h = 1.0f;
    public e i;

    public c(j j3) {
        this.e = j3;
    }

    public static /* synthetic */ void a(c c3) {
        c3.c.a();
    }

    public static c h() {
        ThreadLocal threadLocal = j;
        if (threadLocal.get() == null) {
            threadLocal.set(new c(new f()));
        }
        return (c)threadLocal.get();
    }

    public void d(c c3, long l3) {
        if (this.b.size() == 0) {
            this.e.a(this.d);
            if (Build.VERSION.SDK_INT >= 33) {
                this.h = x0.a.a();
                if (this.i == null) {
                    this.i = new d(this);
                }
                this.i.b();
            }
        }
        if (!this.b.contains(c3)) {
            this.b.add(c3);
        }
        if (l3 > 0L) {
            this.a.put(c3, SystemClock.uptimeMillis() + l3);
        }
    }

    public final void e() {
        if (this.g) {
            for (int i3 = this.b.size() - 1; i3 >= 0; --i3) {
                if (this.b.get(i3) != null) continue;
                this.b.remove(i3);
            }
            if (this.b.size() == 0 && Build.VERSION.SDK_INT >= 33) {
                this.i.a();
            }
            this.g = false;
        }
    }

    public void f(long l3) {
        long l4 = SystemClock.uptimeMillis();
        for (int i3 = 0; i3 < this.b.size(); ++i3) {
            c c3 = (c)this.b.get(i3);
            if (c3 == null || !this.i(c3, l4)) continue;
            c3.a(l3);
        }
        this.e();
    }

    public float g() {
        return this.h;
    }

    public final boolean i(c c3, long l3) {
        Long l4 = (Long)this.a.get(c3);
        if (l4 == null) {
            return true;
        }
        if (l4 < l3) {
            this.a.remove(c3);
            return true;
        }
        return false;
    }

    public boolean j() {
        return this.e.b();
    }

    public void k(c c3) {
        this.a.remove(c3);
        int n3 = this.b.indexOf(c3);
        if (n3 >= 0) {
            this.b.set(n3, null);
            this.g = true;
        }
    }

    public class b {
        public final c a;

        public b(c c3) {
            this.a = c3;
        }

        public /* synthetic */ b(c c3, a a4) {
            this(c3);
        }

        public void a() {
            this.a.f = SystemClock.uptimeMillis();
            c c3 = this.a;
            c3.f(c3.f);
            if (this.a.b.size() > 0) {
                this.a.e.a(this.a.d);
            }
        }
    }

    public static interface c {
        public boolean a(long var1);
    }

    public class d
    implements e {
        public ValueAnimator.DurationScaleChangeListener a;
        public final c b;

        public d(c c3) {
            this.b = c3;
        }

        public static /* synthetic */ void c(d d3, float f3) {
            d3.b.h = f3;
        }

        @Override
        public boolean a() {
            boolean bl = x0.d.a(this.a);
            this.a = null;
            return bl;
        }

        @Override
        public boolean b() {
            if (this.a == null) {
                x0.f f3 = new x0.f(this);
                this.a = f3;
                return x0.e.a(f3);
            }
            return true;
        }
    }

    public static interface e {
        public boolean a();

        public boolean b();
    }

    public static final class f
    implements j {
        public final Choreographer a = Choreographer.getInstance();
        public final Looper b = Looper.myLooper();

        public static /* synthetic */ void c(Runnable runnable, long l3) {
            runnable.run();
        }

        @Override
        public void a(Runnable runnable) {
            this.a.postFrameCallback((Choreographer.FrameCallback)new g(runnable));
        }

        @Override
        public boolean b() {
            return Thread.currentThread() == this.b.getThread();
        }
    }
}

