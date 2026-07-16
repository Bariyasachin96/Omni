/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.IBinder
 */
package androidx.lifecycle;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.lifecycle.f;
import androidx.lifecycle.k;
import androidx.lifecycle.x;

public class LifecycleService
extends Service
implements k {
    public final x c = new x(this);

    public IBinder onBind(Intent intent) {
        o3.k.e(intent, "intent");
        this.c.b();
        return null;
    }

    public void onCreate() {
        this.c.c();
        super.onCreate();
    }

    public void onDestroy() {
        this.c.d();
        super.onDestroy();
    }

    public void onStart(Intent intent, int n3) {
        this.c.e();
        super.onStart(intent, n3);
    }

    public int onStartCommand(Intent intent, int n3, int n4) {
        return super.onStartCommand(intent, n3, n4);
    }

    @Override
    public f t() {
        return this.c.a();
    }
}

