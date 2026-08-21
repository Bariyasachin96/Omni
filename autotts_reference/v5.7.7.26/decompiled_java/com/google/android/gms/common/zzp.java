/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import r1.f;
import v1.b;
import x1.a;

public final class zzp
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzp> CREATOR = new f();
    public final String c;
    public final boolean d;
    public final boolean e;
    public final Context f;
    public final boolean g;
    public final boolean h;
    public final boolean i;

    public zzp(String string, boolean bl, boolean bl2, IBinder iBinder, boolean bl3, boolean bl4, boolean bl5) {
        this.c = string;
        this.d = bl;
        this.e = bl2;
        this.f = (Context)x1.b.j(a.a.i(iBinder));
        this.g = bl3;
        this.h = bl4;
        this.i = bl5;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        String string = this.c;
        n3 = b.a(parcel);
        b.l(parcel, 1, string, false);
        b.c(parcel, 2, this.d);
        b.c(parcel, 3, this.e);
        b.f(parcel, 4, (IBinder)x1.b.k(this.f), false);
        b.c(parcel, 5, this.g);
        b.c(parcel, 6, this.h);
        b.c(parcel, 8, this.i);
        b.b(parcel, n3);
    }
}

