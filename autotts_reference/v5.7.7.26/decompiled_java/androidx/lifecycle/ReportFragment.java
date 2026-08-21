/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.app.Fragment
 *  android.os.Build$VERSION
 *  android.os.Bundle
 */
package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import androidx.lifecycle.f;
import androidx.lifecycle.k;
import androidx.lifecycle.l;
import androidx.lifecycle.t;
import o3.g;

public class ReportFragment
extends Fragment {
    public static final b d = new b(null);
    public a c;

    public static final void e(Activity activity) {
        d.c(activity);
    }

    public final void a(f.a a4) {
        if (Build.VERSION.SDK_INT < 29) {
            b b3 = d;
            Activity activity = this.getActivity();
            o3.k.d(activity, "activity");
            b3.a(activity, a4);
        }
    }

    public final void b(a a4) {
        if (a4 != null) {
            a4.a();
        }
    }

    public final void c(a a4) {
        if (a4 != null) {
            a4.b();
        }
    }

    public final void d(a a4) {
        if (a4 != null) {
            a4.c();
        }
    }

    public final void f(a a4) {
        this.c = a4;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.b(this.c);
        this.a(f.a.ON_CREATE);
    }

    public void onDestroy() {
        super.onDestroy();
        this.a(f.a.ON_DESTROY);
        this.c = null;
    }

    public void onPause() {
        super.onPause();
        this.a(f.a.ON_PAUSE);
    }

    public void onResume() {
        super.onResume();
        this.c(this.c);
        this.a(f.a.ON_RESUME);
    }

    public void onStart() {
        super.onStart();
        this.d(this.c);
        this.a(f.a.ON_START);
    }

    public void onStop() {
        super.onStop();
        this.a(f.a.ON_STOP);
    }

    public static interface a {
        public void a();

        public void b();

        public void c();
    }

    public static final class b {
        public b() {
        }

        public /* synthetic */ b(g g3) {
            this();
        }

        public final void a(Activity object, f.a a4) {
            o3.k.e(object, "activity");
            o3.k.e((Object)a4, "event");
            if (object instanceof k && (object = ((k)object).t()) instanceof l) {
                ((l)object).h(a4);
            }
        }

        public final ReportFragment b(Activity activity) {
            o3.k.e(activity, "<this>");
            activity = activity.getFragmentManager().findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag");
            o3.k.c(activity, "null cannot be cast to non-null type androidx.lifecycle.ReportFragment");
            return (ReportFragment)activity;
        }

        public final void c(Activity activity) {
            o3.k.e(activity, "activity");
            if (Build.VERSION.SDK_INT >= 29) {
                androidx.lifecycle.ReportFragment$c.Companion.a(activity);
            }
            if ((activity = activity.getFragmentManager()).findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
                activity.beginTransaction().add((Fragment)new ReportFragment(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
                activity.executePendingTransactions();
            }
        }
    }

    public static final class c
    implements Application.ActivityLifecycleCallbacks {
        public static final a Companion = new a(null);

        public static final void registerIn(Activity activity) {
            Companion.a(activity);
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
            o3.k.e(activity, "activity");
        }

        public void onActivityDestroyed(Activity activity) {
            o3.k.e(activity, "activity");
        }

        public void onActivityPaused(Activity activity) {
            o3.k.e(activity, "activity");
        }

        public void onActivityPostCreated(Activity activity, Bundle bundle) {
            o3.k.e(activity, "activity");
            d.a(activity, f.a.ON_CREATE);
        }

        public void onActivityPostResumed(Activity activity) {
            o3.k.e(activity, "activity");
            d.a(activity, f.a.ON_RESUME);
        }

        public void onActivityPostStarted(Activity activity) {
            o3.k.e(activity, "activity");
            d.a(activity, f.a.ON_START);
        }

        public void onActivityPreDestroyed(Activity activity) {
            o3.k.e(activity, "activity");
            d.a(activity, f.a.ON_DESTROY);
        }

        public void onActivityPrePaused(Activity activity) {
            o3.k.e(activity, "activity");
            d.a(activity, f.a.ON_PAUSE);
        }

        public void onActivityPreStopped(Activity activity) {
            o3.k.e(activity, "activity");
            d.a(activity, f.a.ON_STOP);
        }

        public void onActivityResumed(Activity activity) {
            o3.k.e(activity, "activity");
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            o3.k.e(activity, "activity");
            o3.k.e(bundle, "bundle");
        }

        public void onActivityStarted(Activity activity) {
            o3.k.e(activity, "activity");
        }

        public void onActivityStopped(Activity activity) {
            o3.k.e(activity, "activity");
        }

        public static final class a {
            public a() {
            }

            public /* synthetic */ a(g g3) {
                this();
            }

            public final void a(Activity activity) {
                o3.k.e(activity, "activity");
                t.a(activity, new c());
            }
        }
    }
}

