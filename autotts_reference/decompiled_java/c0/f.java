/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.os.Binder
 *  android.os.Build$VERSION
 */
package c0;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;

public abstract class f {
    public static int a(Context context, int n3, String string, String string2) {
        if (Build.VERSION.SDK_INT >= 29) {
            AppOpsManager appOpsManager = b.c(context);
            int n4 = b.a(appOpsManager, string, Binder.getCallingUid(), string2);
            if (n4 != 0) {
                return n4;
            }
            return b.a(appOpsManager, string, n3, b.b(context));
        }
        return f.b(context, string, string2);
    }

    public static int b(Context context, String string, String string2) {
        return a.c(a.a(context, AppOpsManager.class), string, string2);
    }

    public static String c(String string) {
        return a.d(string);
    }

    public static abstract class a {
        public static <T> T a(Context context, Class<T> clazz) {
            return (T)context.getSystemService(clazz);
        }

        public static int b(AppOpsManager appOpsManager, String string, String string2) {
            return appOpsManager.noteProxyOp(string, string2);
        }

        public static int c(AppOpsManager appOpsManager, String string, String string2) {
            return appOpsManager.noteProxyOpNoThrow(string, string2);
        }

        public static String d(String string) {
            return AppOpsManager.permissionToOp((String)string);
        }
    }

    public static abstract class b {
        public static int a(AppOpsManager appOpsManager, String string, int n3, String string2) {
            if (appOpsManager == null) {
                return 1;
            }
            return appOpsManager.checkOpNoThrow(string, n3, string2);
        }

        public static String b(Context context) {
            return context.getOpPackageName();
        }

        public static AppOpsManager c(Context context) {
            return (AppOpsManager)context.getSystemService(AppOpsManager.class);
        }
    }
}

