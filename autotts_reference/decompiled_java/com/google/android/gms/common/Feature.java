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
import r1.b;
import u1.a;

public class Feature
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new b();
    public final String c;
    public final int d;
    public final long e;
    public final boolean f;

    public Feature(String string, int n3, long l3, boolean bl) {
        this.c = string;
        this.d = n3;
        this.e = l3;
        this.f = bl;
    }

    public final boolean equals(Object object) {
        if (object instanceof Feature) {
            object = (Feature)object;
            if (a.a(this.p(), ((Feature)object).p()) && this.q() == ((Feature)object).q() && this.o() == ((Feature)object).o()) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return a.b(this.p(), this.q(), this.o());
    }

    public boolean o() {
        return this.f;
    }

    public String p() {
        return this.c;
    }

    public long q() {
        long l3;
        long l4 = l3 = this.e;
        if (l3 == -1L) {
            l4 = this.d;
        }
        return l4;
    }

    public final String toString() {
        a.a a4 = a.c(this);
        a4.a("name", this.p());
        a4.a("version", this.q());
        a4.a("is_fully_rolled_out", this.o());
        return a4.toString();
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        n3 = v1.b.a(parcel);
        v1.b.l(parcel, 1, this.p(), false);
        v1.b.g(parcel, 2, this.d);
        v1.b.j(parcel, 3, this.q());
        v1.b.c(parcel, 4, this.o());
        v1.b.b(parcel, n3);
    }
}

