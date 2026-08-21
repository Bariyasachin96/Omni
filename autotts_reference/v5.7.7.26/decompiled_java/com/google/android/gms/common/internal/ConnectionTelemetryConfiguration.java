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
import com.google.android.gms.common.internal.RootTelemetryConfiguration;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import u1.g;
import v1.b;

public class ConnectionTelemetryConfiguration
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ConnectionTelemetryConfiguration> CREATOR = new g();
    public final RootTelemetryConfiguration c;
    public final boolean d;
    public final boolean e;
    public final int[] f;
    public final int g;
    public final int[] h;

    public ConnectionTelemetryConfiguration(RootTelemetryConfiguration rootTelemetryConfiguration, boolean bl, boolean bl2, int[] nArray, int n3, int[] nArray2) {
        this.c = rootTelemetryConfiguration;
        this.d = bl;
        this.e = bl2;
        this.f = nArray;
        this.g = n3;
        this.h = nArray2;
    }

    public int o() {
        return this.g;
    }

    public int[] p() {
        return this.f;
    }

    public int[] q() {
        return this.h;
    }

    public boolean r() {
        return this.d;
    }

    public boolean s() {
        return this.e;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        int n4 = b.a(parcel);
        b.k(parcel, 1, this.c, n3, false);
        b.c(parcel, 2, this.r());
        b.c(parcel, 3, this.s());
        b.h(parcel, 4, this.p(), false);
        b.g(parcel, 5, this.o());
        b.h(parcel, 6, this.q(), false);
        b.b(parcel, n4);
    }
}

