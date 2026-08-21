/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build$VERSION
 */
package androidx.profileinstaller;

import android.content.Context;
import android.os.Build;
import androidx.profileinstaller.ProfileInstallReceiver;
import java.io.File;

public abstract class a {
    public static boolean a(File fileArray) {
        if (fileArray.isDirectory()) {
            if ((fileArray = fileArray.listFiles()) == null) {
                return false;
            }
            int n3 = fileArray.length;
            boolean bl = true;
            for (int i3 = 0; i3 < n3; ++i3) {
                bl = a.a(fileArray[i3]) && bl;
            }
            return bl;
        }
        fileArray.delete();
        return true;
    }

    public static void b(Context object, ProfileInstallReceiver.a a4) {
        object = Build.VERSION.SDK_INT >= 34 ? b.a(object).getCacheDir() : a.a(b.a(object));
        if (a.a((File)object)) {
            a4.b(14, null);
            return;
        }
        a4.b(15, null);
    }

    public static abstract class a {
        public static File a(Context context) {
            return context.getCodeCacheDir();
        }
    }

    public static abstract class b {
        public static Context a(Context context) {
            return context.createDeviceProtectedStorageContext();
        }
    }
}

