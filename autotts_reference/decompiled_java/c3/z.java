/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$PackageInfoFlags
 */
package c3;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public abstract class z {
    public static /* bridge */ /* synthetic */ PackageInfo a(PackageManager packageManager, String string, PackageManager.PackageInfoFlags packageInfoFlags) {
        return packageManager.getPackageInfo(string, packageInfoFlags);
    }
}

