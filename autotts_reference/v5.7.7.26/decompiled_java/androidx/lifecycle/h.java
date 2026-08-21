/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.content.Context
 *  android.os.Bundle
 */
package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.lifecycle.ReportFragment;
import androidx.lifecycle.c;
import java.util.concurrent.atomic.AtomicBoolean;
import o3.k;

public final class h {
    public static final h a = new h();
    public static final AtomicBoolean b = new AtomicBoolean(false);

    public static final void a(Context context) {
        k.e(context, "context");
        if (b.getAndSet(true)) {
            return;
        }
        context = context.getApplicationContext();
        k.c(context, "null cannot be cast to non-null type android.app.Application");
        ((Application)context).registerActivityLifecycleCallbacks((Application.ActivityLifecycleCallbacks)new a());
    }

    public static final class a
    extends c {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            k.e(activity, "activity");
            ReportFragment.d.c(activity);
        }
    }
}

