/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.NotificationManager
 *  android.content.Context
 */
package c0;

import android.app.NotificationManager;
import android.content.Context;
import java.util.HashSet;
import java.util.Set;

public final class n {
    public static final Object c = new Object();
    public static Set d = new HashSet();
    public static final Object e = new Object();
    public final Context a;
    public final NotificationManager b;

    public n(Context context) {
        this.a = context;
        this.b = (NotificationManager)context.getSystemService("notification");
    }

    public static n b(Context context) {
        return new n(context);
    }

    public boolean a() {
        return c0.n$a.a(this.b);
    }

    public static abstract class a {
        public static boolean a(NotificationManager notificationManager) {
            return notificationManager.areNotificationsEnabled();
        }

        public static int b(NotificationManager notificationManager) {
            return notificationManager.getImportance();
        }
    }
}

