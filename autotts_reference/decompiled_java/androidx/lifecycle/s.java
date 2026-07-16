/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 */
package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.c;
import androidx.lifecycle.f;
import androidx.lifecycle.k;
import androidx.lifecycle.l;
import androidx.lifecycle.r;
import o3.g;

public final class s
implements k {
    public static final b k = new b(null);
    public static final s l = new s();
    public int c;
    public int d;
    public boolean e = true;
    public boolean f = true;
    public Handler g;
    public final l h = new l(this);
    public final Runnable i = new r(this);
    public final ReportFragment.a j = new ReportFragment.a(this){
        public final s a;
        {
            this.a = s3;
        }

        @Override
        public void a() {
        }

        @Override
        public void b() {
            this.a.g();
        }

        @Override
        public void c() {
            this.a.h();
        }
    };

    public static /* synthetic */ void a(s s3) {
        s.k(s3);
    }

    public static final void k(s s3) {
        o3.k.e(s3, "this$0");
        s3.l();
        s3.m();
    }

    public final void f() {
        int n3;
        this.d = n3 = this.d - 1;
        if (n3 == 0) {
            Handler handler = this.g;
            o3.k.b(handler);
            handler.postDelayed(this.i, 700L);
        }
    }

    public final void g() {
        int n3;
        this.d = n3 = this.d + 1;
        if (n3 == 1) {
            if (this.e) {
                this.h.h(f.a.ON_RESUME);
                this.e = false;
                return;
            }
            Handler handler = this.g;
            o3.k.b(handler);
            handler.removeCallbacks(this.i);
        }
    }

    public final void h() {
        int n3;
        this.c = n3 = this.c + 1;
        if (n3 == 1 && this.f) {
            this.h.h(f.a.ON_START);
            this.f = false;
        }
    }

    public final void i() {
        --this.c;
        this.m();
    }

    public final void j(Context context) {
        o3.k.e(context, "context");
        this.g = new Handler();
        this.h.h(f.a.ON_CREATE);
        context = context.getApplicationContext();
        o3.k.c(context, "null cannot be cast to non-null type android.app.Application");
        ((Application)context).registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new c(this){
            final s this$0;
            {
                this.this$0 = s3;
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                o3.k.e(activity, "activity");
                if (Build.VERSION.SDK_INT < 29) {
                    ReportFragment.d.b(activity).f(this.this$0.j);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                o3.k.e(activity, "activity");
                this.this$0.f();
            }

            public void onActivityPreCreated(Activity activity, Bundle bundle) {
                o3.k.e(activity, "activity");
                a.a(activity, new c(this.this$0){
                    final s this$0;
                    {
                        this.this$0 = s3;
                    }

                    public void onActivityPostResumed(Activity activity) {
                        o3.k.e(activity, "activity");
                        this.this$0.g();
                    }

                    public void onActivityPostStarted(Activity activity) {
                        o3.k.e(activity, "activity");
                        this.this$0.h();
                    }
                });
            }

            @Override
            public void onActivityStopped(Activity activity) {
                o3.k.e(activity, "activity");
                this.this$0.i();
            }
        });
    }

    public final void l() {
        if (this.d == 0) {
            this.e = true;
            this.h.h(f.a.ON_PAUSE);
        }
    }

    public final void m() {
        if (this.c == 0 && this.e) {
            this.h.h(f.a.ON_STOP);
            this.f = true;
        }
    }

    @Override
    public f t() {
        return this.h;
    }

    public static final class a {
        public static final a a = new a();

        public static final void a(Activity activity, Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            o3.k.e(activity, "activity");
            o3.k.e(activityLifecycleCallbacks, "callback");
            activity.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
    }

    public static final class b {
        public b() {
        }

        public /* synthetic */ b(g g3) {
            this();
        }

        public final k a() {
            return l;
        }

        public final void b(Context context) {
            o3.k.e(context, "context");
            l.j(context);
        }
    }
}

