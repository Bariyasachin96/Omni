/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 */
package c3;

import android.content.Context;
import android.content.pm.PackageManager;

public abstract class v {
    public static boolean a(Context context) {
        return v.b("com.google.android.tts", context.getPackageManager());
    }

    public static boolean b(String string, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(string, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }
}

