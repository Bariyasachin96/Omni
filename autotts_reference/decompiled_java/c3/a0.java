/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package c3;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import c3.x;
import c3.y;
import c3.z;

public abstract class a0 {
    public static PackageInfo a(Context object) {
        PackageManager packageManager = object.getPackageManager();
        object = object.getPackageName();
        if (Build.VERSION.SDK_INT >= 33) {
            return z.a(packageManager, (String)object, y.a(0L));
        }
        return packageManager.getPackageInfo((String)object, 0);
    }

    public static long b(Context context) {
        int n3;
        try {
            context = a0.a(context);
            if (Build.VERSION.SDK_INT >= 28) {
                return x.a((PackageInfo)context);
            }
            n3 = context.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)"VersionUtils", (String)"Error getting version code", (Throwable)nameNotFoundException);
            return -1L;
        }
        return n3;
    }

    public static String c(Context object) {
        try {
            object = a0.a((Context)object).versionName;
            return object;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.e((String)"VersionUtils", (String)"Error getting version name", (Throwable)nameNotFoundException);
            return "Unknown";
        }
    }
}

