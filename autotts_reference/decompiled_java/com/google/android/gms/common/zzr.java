/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import r1.c;
import r1.g;
import r1.i;
import v1.b;

public final class zzr
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzr> CREATOR = new g();
    public final boolean c;
    public final String d;
    public final int e;
    public final int f;
    public final long g;

    public zzr(boolean bl, String string, int n3, int n4, long l3) {
        this.c = bl;
        this.d = string;
        this.e = i.a(n3) - 1;
        this.f = r1.c.a(n4) - 1;
        this.g = l3;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        n3 = b.a(parcel);
        b.c(parcel, 1, this.c);
        b.l(parcel, 2, this.d, false);
        b.g(parcel, 3, this.e);
        b.g(parcel, 4, this.f);
        b.j(parcel, 5, this.g);
        b.b(parcel, n3);
    }
}

