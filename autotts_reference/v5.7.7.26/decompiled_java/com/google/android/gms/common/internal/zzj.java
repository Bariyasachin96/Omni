/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import u1.f;
import v1.b;

public final class zzj
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzj> CREATOR = new f();
    public Bundle c;
    public Feature[] d;
    public int e;
    public ConnectionTelemetryConfiguration f;

    public zzj(Bundle bundle, Feature[] featureArray, int n3, ConnectionTelemetryConfiguration connectionTelemetryConfiguration) {
        this.c = bundle;
        this.d = featureArray;
        this.e = n3;
        this.f = connectionTelemetryConfiguration;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        int n4 = b.a(parcel);
        b.d(parcel, 1, this.c, false);
        b.n(parcel, 2, this.d, n3, false);
        b.g(parcel, 3, this.e);
        b.k(parcel, 4, this.f, n3, false);
        b.b(parcel, n4);
    }
}

