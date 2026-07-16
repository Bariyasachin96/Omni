/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.accounts.Account
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.a;
import com.google.android.gms.common.internal.b;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import u1.h;

public class GetServiceRequest
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GetServiceRequest> CREATOR = new h();
    public static final Scope[] q = new Scope[0];
    public static final Feature[] r = new Feature[0];
    public final int c;
    public final int d;
    public final int e;
    public String f;
    public IBinder g;
    public Scope[] h;
    public Bundle i;
    public Account j;
    public Feature[] k;
    public Feature[] l;
    public final boolean m;
    public final int n;
    public boolean o;
    public final String p;

    public GetServiceRequest(int n3, int n4, int n5, String string, IBinder iBinder, Scope[] featureArray, Bundle featureArray2, Account account, Feature[] featureArray3, Feature[] featureArray4, boolean bl, int n6, boolean bl2, String string2) {
        Feature[] featureArray5 = featureArray;
        if (featureArray == null) {
            featureArray5 = q;
        }
        featureArray = featureArray2;
        if (featureArray2 == null) {
            featureArray = new Bundle();
        }
        featureArray2 = featureArray3;
        if (featureArray3 == null) {
            featureArray2 = r;
        }
        featureArray3 = featureArray4;
        if (featureArray4 == null) {
            featureArray3 = r;
        }
        this.c = n3;
        this.d = n4;
        this.e = n5;
        this.f = "com.google.android.gms".equals(string) ? "com.google.android.gms" : string;
        if (n3 < 2) {
            string = iBinder != null ? a.j(b.a.i(iBinder)) : null;
            this.j = string;
        } else {
            this.g = iBinder;
            this.j = account;
        }
        this.h = featureArray5;
        this.i = featureArray;
        this.k = featureArray2;
        this.l = featureArray3;
        this.m = bl;
        this.n = n6;
        this.o = bl2;
        this.p = string2;
    }

    public String o() {
        return this.p;
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        u1.h.a(this, parcel, n3);
    }
}

