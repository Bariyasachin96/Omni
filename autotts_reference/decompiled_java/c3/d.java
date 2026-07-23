/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.ServiceConnection
 *  android.content.pm.ResolveInfo
 *  android.os.IBinder
 */
package c3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import c3.m;
import c3.o;
import com.vnspeak.autotts.AutoTtsService;

public class d {
    public final Context a;
    public ServiceConnection b;
    public String c;
    public final AutoTtsService d;

    public d(Context context, AutoTtsService autoTtsService) {
        this.a = context.getApplicationContext();
        this.d = autoTtsService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean c(String string) {
        synchronized (this) {
            Throwable throwable2;
            block11: {
                Object object;
                block10: {
                    ResolveInfo resolveInfo;
                    boolean bl;
                    boolean bl2;
                    block9: {
                        block8: {
                            if (string == null) {
                                return false;
                            }
                            try {
                                bl2 = string.equals(this.c);
                                bl = true;
                                if (!bl2 || (object = this.b) == null) break block8;
                            }
                            catch (Throwable throwable2) {}
                            return true;
                        }
                        this.e();
                        object = new Intent("android.intent.action.TTS_SERVICE");
                        object.setPackage(string);
                        resolveInfo = this.a.getPackageManager().resolveService((Intent)object, 0);
                        if (resolveInfo != null && resolveInfo.serviceInfo != null) break block9;
                        break block10;
                        break block11;
                    }
                    resolveInfo = resolveInfo.serviceInfo;
                    Object object2 = new ComponentName(resolveInfo.packageName, resolveInfo.name);
                    object.setComponent((ComponentName)object2);
                    object2 = new ServiceConnection(this){
                        public final d a;
                        {
                            this.a = d3;
                        }

                        public void onBindingDied(ComponentName object) {
                            o o3 = m.a;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Binding died: ");
                            stringBuilder.append(object.flattenToShortString());
                            o3.c("AutoTTS", stringBuilder.toString());
                            object = this.a.c;
                            this.a.e();
                            this.a.c((String)object);
                        }

                        public void onNullBinding(ComponentName componentName) {
                            m.a.c("AutoTTS", "Service returned null binding");
                            this.a.e();
                        }

                        public void onServiceConnected(ComponentName componentName, IBinder object) {
                            o o3 = m.a;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Keep-alive bound to ");
                            ((StringBuilder)object).append(componentName.flattenToShortString());
                            o3.c("AutoTTS", ((StringBuilder)object).toString());
                        }

                        public void onServiceDisconnected(ComponentName componentName) {
                            o o3 = m.a;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Engine process died: ");
                            stringBuilder.append(componentName.flattenToShortString());
                            o3.c("AutoTTS", stringBuilder.toString());
                            this.a.d();
                        }
                    };
                    this.b = object2;
                    try {
                        bl2 = this.a.bindService((Intent)object, (ServiceConnection)object2, 65);
                        if (bl2) {
                            bl = bl2;
                            this.c = string;
                            return bl2;
                        }
                        bl = bl2;
                        object = m.a;
                        bl = bl2;
                        bl = bl2;
                        object2 = new StringBuilder();
                        bl = bl2;
                        ((StringBuilder)object2).append("bindService failed for ");
                        bl = bl2;
                        ((StringBuilder)object2).append(string);
                        bl = bl2;
                        ((o)object).c("AutoTTS", ((StringBuilder)object2).toString());
                        bl = bl2;
                        this.b = null;
                        return bl2;
                    }
                    catch (Exception exception) {
                        o o3 = m.a;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("bindService failed for ");
                        ((StringBuilder)object2).append(string);
                        o3.c("AutoTTS", ((StringBuilder)object2).toString());
                        this.b = null;
                    }
                    return bl;
                }
                o o4 = m.a;
                object = new StringBuilder();
                ((StringBuilder)object).append("No TTS service found in ");
                ((StringBuilder)object).append(string);
                o4.c("AutoTTS", ((StringBuilder)object).toString());
                return false;
            }
            throw throwable2;
        }
    }

    public final void d() {
        this.d.i0(this.c);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void e() {
        synchronized (this) {
            try {
                ServiceConnection serviceConnection = this.b;
                if (serviceConnection != null) {
                    try {
                        this.a.unbindService(serviceConnection);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {}
                    this.b = null;
                    this.c = null;
                }
                return;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }
}

