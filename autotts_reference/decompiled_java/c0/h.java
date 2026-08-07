/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package c0;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public abstract class h {
    public static Intent a(Activity activity) {
        Object object = activity.getParentActivityIntent();
        if (object != null) {
            return object;
        }
        object = h.c(activity);
        if (object == null) {
            return null;
        }
        ComponentName componentName = new ComponentName((Context)activity, (String)object);
        try {
            if (h.d((Context)activity, componentName) == null) {
                return Intent.makeMainActivity((ComponentName)componentName);
            }
            activity = new Intent();
            activity = activity.setComponent(componentName);
            return activity;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getParentActivityIntent: bad parentActivityName '");
            stringBuilder.append((String)object);
            stringBuilder.append("' in manifest");
            Log.e((String)"NavUtils", (String)stringBuilder.toString());
            return null;
        }
    }

    public static Intent b(Context context, ComponentName componentName) {
        String string = h.d(context, componentName);
        if (string == null) {
            return null;
        }
        if (h.d(context, componentName = new ComponentName(componentName.getPackageName(), string)) == null) {
            return Intent.makeMainActivity((ComponentName)componentName);
        }
        return new Intent().setComponent(componentName);
    }

    public static String c(Activity object) {
        try {
            object = h.d((Context)object, object.getComponentName());
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new IllegalArgumentException(nameNotFoundException);
        }
    }

    public static String d(Context context, ComponentName object) {
        Object object2 = context.getPackageManager();
        int n3 = Build.VERSION.SDK_INT >= 29 ? 269222528 : 787072;
        object = object2.getActivityInfo((ComponentName)object, n3);
        object2 = ((ActivityInfo)object).parentActivityName;
        if (object2 != null) {
            return object2;
        }
        object = ((ActivityInfo)object).metaData;
        if (object == null) {
            return null;
        }
        if ((object = object.getString("android.support.PARENT_ACTIVITY")) == null) {
            return null;
        }
        if (((String)object).charAt(0) == '.') {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(context.getPackageName());
            ((StringBuilder)object2).append((String)object);
            return ((StringBuilder)object2).toString();
        }
        return object;
    }

    public static void e(Activity activity, Intent intent) {
        activity.navigateUpTo(intent);
    }

    public static boolean f(Activity activity, Intent intent) {
        return activity.shouldUpRecreateTask(intent);
    }
}

