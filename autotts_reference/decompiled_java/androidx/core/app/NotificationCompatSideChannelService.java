/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.IBinder
 */
package androidx.core.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public abstract class NotificationCompatSideChannelService
extends Service {
    public IBinder onBind(Intent intent) {
        intent.getAction().equals("android.support.BIND_NOTIFICATION_SIDE_CHANNEL");
        return null;
    }
}

