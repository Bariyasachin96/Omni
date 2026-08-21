/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcelable
 */
package androidx.core.app;

import android.app.PendingIntent;
import android.os.Parcelable;
import androidx.core.app.RemoteActionCompat;
import androidx.core.graphics.drawable.IconCompat;
import o1.a;

public class RemoteActionCompatParcelizer {
    public static RemoteActionCompat read(a a4) {
        RemoteActionCompat remoteActionCompat = new RemoteActionCompat();
        remoteActionCompat.a = (IconCompat)a4.v(remoteActionCompat.a, 1);
        remoteActionCompat.b = a4.l(remoteActionCompat.b, 2);
        remoteActionCompat.c = a4.l(remoteActionCompat.c, 3);
        remoteActionCompat.d = (PendingIntent)a4.r((Parcelable)remoteActionCompat.d, 4);
        remoteActionCompat.e = a4.h(remoteActionCompat.e, 5);
        remoteActionCompat.f = a4.h(remoteActionCompat.f, 6);
        return remoteActionCompat;
    }

    public static void write(RemoteActionCompat remoteActionCompat, a a4) {
        a4.x(false, false);
        a4.M(remoteActionCompat.a, 1);
        a4.D(remoteActionCompat.b, 2);
        a4.D(remoteActionCompat.c, 3);
        a4.H((Parcelable)remoteActionCompat.d, 4);
        a4.z(remoteActionCompat.e, 5);
        a4.z(remoteActionCompat.f, 6);
    }
}

