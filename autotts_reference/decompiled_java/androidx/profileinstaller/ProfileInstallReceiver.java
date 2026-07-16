/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Process
 */
package androidx.profileinstaller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import androidx.profileinstaller.c;
import h1.e;

public class ProfileInstallReceiver
extends BroadcastReceiver {
    public static void a(c.c c3) {
        Process.sendSignal((int)Process.myPid(), (int)10);
        c3.b(12, null);
    }

    public void onReceive(Context context, Intent object) {
        if (object != null) {
            Object object2 = object.getAction();
            if ("androidx.profileinstaller.action.INSTALL_PROFILE".equals(object2)) {
                c.j(context, new e(), new a(this), true);
                return;
            }
            if ("androidx.profileinstaller.action.SKIP_FILE".equals(object2)) {
                if ((object = object.getExtras()) != null) {
                    if ("WRITE_SKIP_FILE".equals(object = object.getString("EXTRA_SKIP_FILE_OPERATION"))) {
                        c.k(context, new e(), new a(this));
                        return;
                    }
                    if ("DELETE_SKIP_FILE".equals(object)) {
                        c.c(context, new e(), new a(this));
                        return;
                    }
                }
            } else {
                if ("androidx.profileinstaller.action.SAVE_PROFILE".equals(object2)) {
                    ProfileInstallReceiver.a(new a(this));
                    return;
                }
                if ("androidx.profileinstaller.action.BENCHMARK_OPERATION".equals(object2) && (object = object.getExtras()) != null) {
                    object = object.getString("EXTRA_BENCHMARK_OPERATION");
                    object2 = new a(this);
                    if ("DROP_SHADER_CACHE".equals(object)) {
                        androidx.profileinstaller.a.b(context, (a)object2);
                        return;
                    }
                    ((a)object2).b(16, null);
                }
            }
        }
    }

    public class a
    implements c.c {
        public final ProfileInstallReceiver a;

        public a(ProfileInstallReceiver profileInstallReceiver) {
            this.a = profileInstallReceiver;
        }

        @Override
        public void a(int n3, Object object) {
            c.b.a(n3, object);
        }

        @Override
        public void b(int n3, Object object) {
            c.b.b(n3, object);
            this.a.setResultCode(n3);
        }
    }
}

