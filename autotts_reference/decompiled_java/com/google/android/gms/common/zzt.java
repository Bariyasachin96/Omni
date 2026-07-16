/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.util.Log
 */
package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import r1.d;
import r1.e;
import r1.h;
import u1.j;
import v1.b;
import x1.a;

public final class zzt
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzt> CREATOR = new h();
    public final String c;
    public final d d;
    public final boolean e;
    public final boolean f;

    /*
     * Loose catch block
     */
    public zzt(String object, IBinder iBinder, boolean bl, boolean bl2) {
        block6: {
            this.c = object;
            Object var5_6 = null;
            if (iBinder == null) {
                object = var5_6;
            } else {
                object = j.i(iBinder).a();
                object = object == null ? null : (Object)((byte[])x1.b.j((a)object));
                if (object != null) {
                    object = new e((byte[])object);
                } else {
                    Log.e((String)"GoogleCertificatesQuery", (String)"Could not unwrap certificate");
                    object = var5_6;
                }
            }
            break block6;
            catch (RemoteException remoteException) {
                Log.e((String)"GoogleCertificatesQuery", (String)"Could not unwrap certificate", (Throwable)remoteException);
                object = var5_6;
            }
        }
        this.d = object;
        this.e = bl;
        this.f = bl2;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        Object object = this.c;
        n3 = b.a(parcel);
        b.l(parcel, 1, (String)object, false);
        d d3 = this.d;
        object = d3;
        if (d3 == null) {
            Log.w((String)"GoogleCertificatesQuery", (String)"certificate binder is null");
            object = null;
        }
        b.f(parcel, 2, (IBinder)object, false);
        b.c(parcel, 3, this.e);
        b.c(parcel, 4, this.f);
        b.b(parcel, n3);
    }
}

