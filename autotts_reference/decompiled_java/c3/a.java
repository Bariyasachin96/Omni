/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 */
package c3;

import android.app.Notification;
import com.vnspeak.autotts.AutoTtsService;

public abstract class a {
    public static /* bridge */ /* synthetic */ void a(AutoTtsService autoTtsService, int n3, Notification notification, int n4) {
        autoTtsService.startForeground(n3, notification, n4);
    }
}

