/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Process
 */
package e0;

import android.content.Context;
import android.os.Process;
import c0.f;
import n0.c;

public abstract class d {
    public static int a(Context context, String object, int n3, int n4, String string) {
        if (context.checkPermission((String)object, n3, n4) == -1) {
            return -1;
        }
        String string2 = f.c((String)object);
        if (string2 == null) {
            return 0;
        }
        object = string;
        if (string == null) {
            object = context.getPackageManager().getPackagesForUid(n4);
            if (object != null && ((String[])object).length > 0) {
                object = object[0];
            } else {
                return -1;
            }
        }
        n3 = Process.myUid();
        string = context.getPackageName();
        if ((n3 = n3 == n4 && c.a(string, object) ? f.a(context, n4, string2, (String)object) : f.b(context, string2, (String)object)) == 0) {
            return 0;
        }
        return -2;
    }

    public static int b(Context context, String string) {
        return d.a(context, string, Process.myPid(), Process.myUid(), context.getPackageName());
    }
}

