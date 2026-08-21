/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import s1.d;
import v1.b;

public final class ComplianceOptions
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ComplianceOptions> CREATOR;
    public static final ComplianceOptions g;
    public final int c;
    public final int d;
    public final int e;
    public final boolean f;

    static {
        a a4 = ComplianceOptions.o();
        a4.b(-1);
        a4.c(-1);
        a4.e(0);
        a4.d(true);
        g = a4.a();
        CREATOR = new d();
    }

    public ComplianceOptions(int n3, int n4, int n5, boolean bl) {
        this.c = n3;
        this.d = n4;
        this.e = n5;
        this.f = bl;
    }

    public static a o() {
        return new a();
    }

    public final boolean equals(Object object) {
        if (!(object instanceof ComplianceOptions)) {
            return false;
        }
        object = (ComplianceOptions)object;
        return this.c == ((ComplianceOptions)object).c && this.d == ((ComplianceOptions)object).d && this.e == ((ComplianceOptions)object).e && this.f == ((ComplianceOptions)object).f;
    }

    public final int hashCode() {
        return u1.a.b(this.c, this.d, this.e, this.f);
    }

    public final String toString() {
        int n3 = this.c;
        int n4 = String.valueOf(n3).length();
        int n5 = this.d;
        int n6 = String.valueOf(n5).length();
        int n7 = this.e;
        int n8 = String.valueOf(n7).length();
        boolean bl = this.f;
        StringBuilder stringBuilder = new StringBuilder(n4 + 55 + n6 + 19 + n8 + 13 + String.valueOf(bl).length() + 1);
        stringBuilder.append("ComplianceOptions{callerProductId=");
        stringBuilder.append(n3);
        stringBuilder.append(", dataOwnerProductId=");
        stringBuilder.append(n5);
        stringBuilder.append(", processingReason=");
        stringBuilder.append(n7);
        stringBuilder.append(", isUserData=");
        stringBuilder.append(bl);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        int n4 = this.c;
        n3 = b.a(parcel);
        b.g(parcel, 1, n4);
        b.g(parcel, 2, this.d);
        b.g(parcel, 3, this.e);
        b.c(parcel, 4, this.f);
        b.b(parcel, n3);
    }

    public static final class a {
        public int a = -1;
        public int b = -1;
        public int c = 0;
        public boolean d = true;

        public ComplianceOptions a() {
            return new ComplianceOptions(this.a, this.b, this.c, this.d);
        }

        public a b(int n3) {
            this.a = n3;
            return this;
        }

        public a c(int n3) {
            this.b = n3;
            return this;
        }

        public a d(boolean bl) {
            this.d = bl;
            return this;
        }

        public a e(int n3) {
            this.c = n3;
            return this;
        }
    }
}

