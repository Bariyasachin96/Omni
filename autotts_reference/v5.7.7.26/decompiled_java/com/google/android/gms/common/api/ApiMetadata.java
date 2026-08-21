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
import com.google.android.gms.common.api.ComplianceOptions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import s1.b;

public final class ApiMetadata
extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ApiMetadata> CREATOR = b.a();
    public static final ApiMetadata f = ApiMetadata.p().a();
    public static final ApiMetadata g;
    public final ComplianceOptions c;
    public final boolean d;
    public boolean e;

    static {
        a a4 = ApiMetadata.p();
        a4.b(true);
        g = a4.a();
    }

    public ApiMetadata(ComplianceOptions complianceOptions, boolean bl) {
        this.c = complianceOptions;
        this.d = bl;
    }

    public static final ApiMetadata o() {
        return f;
    }

    public static a p() {
        return new a();
    }

    public final boolean equals(Object object) {
        if (!(object instanceof ApiMetadata)) {
            return false;
        }
        object = (ApiMetadata)object;
        return u1.a.a(this.c, ((ApiMetadata)object).c) && this.e == ((ApiMetadata)object).e && this.d == ((ApiMetadata)object).d;
    }

    public final int hashCode() {
        return u1.a.b(this.c, this.e, this.d);
    }

    public final /* synthetic */ void q(boolean bl) {
        this.e = bl;
    }

    public final String toString() {
        String string = String.valueOf(this.c);
        StringBuilder stringBuilder = new StringBuilder(string.length() + 31);
        stringBuilder.append("ApiMetadata(complianceOptions=");
        stringBuilder.append(string);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int n3) {
        if (this.e) {
            parcel.setDataPosition(parcel.dataPosition() - 4);
            parcel.setDataSize(parcel.dataSize() - 4);
            return;
        }
        parcel.writeInt(-204102970);
        int n4 = v1.b.a(parcel);
        v1.b.k(parcel, 1, this.c, n3, false);
        v1.b.c(parcel, 2, this.d);
        v1.b.b(parcel, n4);
    }

    public static final class a {
        public ComplianceOptions a;
        public boolean b = false;
        public boolean c;

        public ApiMetadata a() {
            ApiMetadata apiMetadata = new ApiMetadata(this.a, this.b);
            apiMetadata.q(this.c);
            return apiMetadata;
        }

        public final /* synthetic */ a b(boolean bl) {
            this.c = bl;
            return this;
        }
    }
}

