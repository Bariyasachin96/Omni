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
import u1.c;
import v1.b;

public class RootTelemetryConfiguration
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<RootTelemetryConfiguration> CREATOR = new c();
    public final int c;
    public final boolean d;
    public final boolean e;
    public final int f;
    public final int g;

    public RootTelemetryConfiguration(int n3, boolean bl, boolean bl2, int n4, int n5) {
        this.c = n3;
        this.d = bl;
        this.e = bl2;
        this.f = n4;
        this.g = n5;
    }

    public int o() {
        return this.f;
    }

    public int p() {
        return this.g;
    }

    public boolean q() {
        return this.d;
    }

    public boolean r() {
        return this.e;
    }

    public int s() {
        return this.c;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        n3 = b.a(parcel);
        b.g(parcel, 1, this.s());
        b.c(parcel, 2, this.q());
        b.c(parcel, 3, this.r());
        b.g(parcel, 4, this.o());
        b.g(parcel, 5, this.p());
        b.b(parcel, n3);
    }
}

