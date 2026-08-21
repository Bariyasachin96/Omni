/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import s1.a;
import s1.f;
import u1.a;
import v1.b;

public final class Status
extends AbstractSafeParcelable
implements ReflectedParcelable {
    public static final Parcelable.Creator<Status> CREATOR;
    public static final Status g;
    public static final Status h;
    public static final Status i;
    public static final Status j;
    public static final Status k;
    public static final Status l;
    public static final Status m;
    public static final Status n;
    public final int c;
    public final String d;
    public final PendingIntent e;
    public final ConnectionResult f;

    static {
        g = new Status(-1);
        h = new Status(0);
        i = new Status(14);
        j = new Status(8);
        k = new Status(15);
        l = new Status(16);
        m = new Status(17);
        n = new Status(18);
        CREATOR = new f();
    }

    public Status(int n3) {
        this(n3, null);
    }

    public Status(int n3, String string) {
        this(n3, string, null);
    }

    public Status(int n3, String string, PendingIntent pendingIntent) {
        this(n3, string, pendingIntent, null);
    }

    public Status(int n3, String string, PendingIntent pendingIntent, ConnectionResult connectionResult) {
        this.c = n3;
        this.d = string;
        this.e = pendingIntent;
        this.f = connectionResult;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Status)) {
            return false;
        }
        object = (Status)object;
        return this.c == ((Status)object).c && u1.a.a(this.d, ((Status)object).d) && u1.a.a(this.e, ((Status)object).e) && u1.a.a(this.f, ((Status)object).f);
    }

    public int hashCode() {
        return u1.a.b(this.c, this.d, this.e, this.f);
    }

    public ConnectionResult o() {
        return this.f;
    }

    public int p() {
        return this.c;
    }

    public String q() {
        return this.d;
    }

    public final String r() {
        String string = this.d;
        if (string != null) {
            return string;
        }
        return a.a(this.c);
    }

    public String toString() {
        a.a a4 = u1.a.c(this);
        a4.a("statusCode", this.r());
        a4.a("resolution", this.e);
        return a4.toString();
    }

    public void writeToParcel(Parcel parcel, int n3) {
        int n4 = b.a(parcel);
        b.g(parcel, 1, this.p());
        b.l(parcel, 2, this.q(), false);
        b.k(parcel, 3, (Parcelable)this.e, n3, false);
        b.k(parcel, 4, this.o(), n3, false);
        b.b(parcel, n4);
    }
}

