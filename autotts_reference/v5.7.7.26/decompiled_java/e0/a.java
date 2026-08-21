/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.ColorStateList
 *  android.graphics.drawable.Drawable
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Process
 *  android.text.TextUtils
 */
package e0;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import c0.n;
import f0.h;
import java.io.File;
import n0.c;

public abstract class a {
    public static final Object a = new Object();

    public static int a(Context context, String string) {
        c.c(string, "permission must be non-null");
        if (Build.VERSION.SDK_INT < 33 && TextUtils.equals((CharSequence)"android.permission.POST_NOTIFICATIONS", (CharSequence)string)) {
            if (n.b(context).a()) {
                return 0;
            }
            return -1;
        }
        return context.checkPermission(string, Process.myPid(), Process.myUid());
    }

    public static int b(Context context, int n3) {
        return b.a(context, n3);
    }

    public static ColorStateList c(Context context, int n3) {
        return h.d(context.getResources(), n3, context.getTheme());
    }

    public static Drawable d(Context context, int n3) {
        return e0.a$a.b(context, n3);
    }

    public static File[] e(Context context) {
        return context.getExternalCacheDirs();
    }

    public static File[] f(Context context, String string) {
        return context.getExternalFilesDirs(string);
    }

    public static Object g(Context context, Class clazz) {
        return b.b(context, clazz);
    }

    public static boolean h(Context context, Intent[] intentArray, Bundle bundle) {
        context.startActivities(intentArray, bundle);
        return true;
    }

    public static void i(Context context, Intent intent, Bundle bundle) {
        context.startActivity(intent, bundle);
    }

    public static abstract class a {
        public static File a(Context context) {
            return context.getCodeCacheDir();
        }

        public static Drawable b(Context context, int n3) {
            return context.getDrawable(n3);
        }

        public static File c(Context context) {
            return context.getNoBackupFilesDir();
        }
    }

    public static abstract class b {
        public static int a(Context context, int n3) {
            return context.getColor(n3);
        }

        public static <T> T b(Context context, Class<T> clazz) {
            return (T)context.getSystemService(clazz);
        }

        public static String c(Context context, Class<?> clazz) {
            return context.getSystemServiceName(clazz);
        }
    }
}

