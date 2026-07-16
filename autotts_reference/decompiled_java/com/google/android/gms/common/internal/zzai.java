/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import u1.d;
import v1.b;

@Deprecated
public final class zzai
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzai> CREATOR = new d();
    public final int c;

    public zzai(int n3) {
        this.c = n3;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        int n4 = this.c;
        n3 = b.a(parcel);
        b.g(parcel, 1, n4);
        b.b(parcel, n3);
    }
}

