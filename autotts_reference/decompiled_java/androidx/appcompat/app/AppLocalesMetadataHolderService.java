/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ServiceInfo
 *  android.os.IBinder
 */
package androidx.appcompat.app;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.IBinder;

public final class AppLocalesMetadataHolderService
extends Service {
    public static ServiceInfo a(Context context) {
        int n3 = a.a();
        return context.getPackageManager().getServiceInfo(new ComponentName(context, AppLocalesMetadataHolderService.class), n3 | 0x80);
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    public static abstract class a {
        public static int a() {
            return 512;
        }
    }
}

