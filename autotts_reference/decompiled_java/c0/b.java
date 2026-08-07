/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.SharedElementCallback$OnSharedElementsReadyListener
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.text.TextUtils
 */
package c0;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import c0.d;
import java.util.Arrays;
import java.util.HashSet;

public abstract class b
extends e0.a {
    public static /* synthetic */ void j(Activity activity) {
        if (!activity.isFinishing() && !d.i(activity)) {
            activity.recreate();
        }
    }

    public static void k(Activity activity) {
        activity.finishAffinity();
    }

    public static void l(Activity activity) {
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
            return;
        }
        new Handler(activity.getMainLooper()).post((Runnable)new c0.a(activity));
    }

    public static void m(Activity object, String[] stringArray, int n3) {
        int n4;
        HashSet<Integer> hashSet = new HashSet<Integer>();
        int n5 = 0;
        for (n4 = 0; n4 < stringArray.length; ++n4) {
            if (!TextUtils.isEmpty((CharSequence)stringArray[n4])) {
                if (Build.VERSION.SDK_INT >= 33 || !TextUtils.equals((CharSequence)stringArray[n4], (CharSequence)"android.permission.POST_NOTIFICATIONS")) continue;
                hashSet.add(n4);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Permission request for permissions ");
            ((StringBuilder)object).append(Arrays.toString(stringArray));
            ((StringBuilder)object).append(" must not contain null or empty values");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        n4 = hashSet.size();
        String[] stringArray2 = n4 > 0 ? new String[stringArray.length - n4] : stringArray;
        if (n4 > 0) {
            if (n4 == stringArray.length) {
                return;
            }
            int n6 = 0;
            while (n5 < stringArray.length) {
                n4 = n6;
                if (!hashSet.contains(n5)) {
                    stringArray2[n6] = stringArray[n5];
                    n4 = n6 + 1;
                }
                ++n5;
                n6 = n4;
            }
        }
        if (object instanceof b) {
            ((b)object).a(n3);
        }
        c0.b$a.b((Activity)object, stringArray, n3);
    }

    public static void n(Activity activity, Intent intent, int n3, Bundle bundle) {
        activity.startActivityForResult(intent, n3, bundle);
    }

    public static void o(Activity activity, IntentSender intentSender, int n3, Intent intent, int n4, int n5, int n6, Bundle bundle) {
        activity.startIntentSenderForResult(intentSender, n3, intent, n4, n5, n6, bundle);
    }

    public static abstract class a {
        public static void a(Object object) {
            ((SharedElementCallback.OnSharedElementsReadyListener)object).onSharedElementsReady();
        }

        public static void b(Activity activity, String[] stringArray, int n3) {
            activity.requestPermissions(stringArray, n3);
        }

        public static boolean c(Activity activity, String string) {
            return activity.shouldShowRequestPermissionRationale(string);
        }
    }

    public static interface b {
        public void a(int var1);
    }
}

