/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application$ActivityLifecycleCallbacks
 *  android.os.Bundle
 */
package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import o3.k;

public abstract class c
implements Application.ActivityLifecycleCallbacks {
    public void onActivityCreated(Activity activity, Bundle bundle) {
        k.e(activity, "activity");
    }

    public void onActivityDestroyed(Activity activity) {
        k.e(activity, "activity");
    }

    public void onActivityPaused(Activity activity) {
        k.e(activity, "activity");
    }

    public void onActivityResumed(Activity activity) {
        k.e(activity, "activity");
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        k.e(activity, "activity");
        k.e(bundle, "outState");
    }

    public void onActivityStarted(Activity activity) {
        k.e(activity, "activity");
    }

    public void onActivityStopped(Activity activity) {
        k.e(activity, "activity");
    }
}

