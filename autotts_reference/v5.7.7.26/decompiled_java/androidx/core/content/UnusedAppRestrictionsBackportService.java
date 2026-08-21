/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.IBinder
 */
package androidx.core.content;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import d0.a;
import d0.b;
import e0.e;

public abstract class UnusedAppRestrictionsBackportService
extends Service {
    public b.a c = new b.a(this){
        public final UnusedAppRestrictionsBackportService d;
        {
            this.d = unusedAppRestrictionsBackportService;
        }

        @Override
        public void c(a object) {
            if (object == null) {
                return;
            }
            object = new e((a)object);
            this.d.a((e)object);
        }
    };

    public abstract void a(e var1);

    public IBinder onBind(Intent intent) {
        return this.c;
    }
}

