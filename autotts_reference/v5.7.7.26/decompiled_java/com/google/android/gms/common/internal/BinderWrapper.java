/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import u1.e;

public final class BinderWrapper
implements Parcelable {
    public static final Parcelable.Creator<BinderWrapper> CREATOR = new e();
    public final IBinder c;

    public /* synthetic */ BinderWrapper(Parcel parcel, byte[] byArray) {
        this.c = parcel.readStrongBinder();
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        parcel.writeStrongBinder(this.c);
    }
}

