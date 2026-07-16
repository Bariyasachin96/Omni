/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable$Creator
 */
package s1;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.ApiMetadata;
import com.google.android.gms.common.api.ComplianceOptions;
import v1.a;

public final class c
implements Parcelable.Creator {
    public static final ApiMetadata a(Parcel parcel) {
        int n3 = a.r(parcel);
        boolean bl = false;
        ComplianceOptions complianceOptions = null;
        while (parcel.dataPosition() < n3) {
            int n4 = a.k(parcel);
            int n5 = a.h(n4);
            if (n5 != 1) {
                if (n5 != 2) {
                    a.q(parcel, n4);
                    continue;
                }
                bl = a.i(parcel, n4);
                continue;
            }
            complianceOptions = (ComplianceOptions)a.c(parcel, n4, ComplianceOptions.CREATOR);
        }
        a.g(parcel, n3);
        return new ApiMetadata(complianceOptions, bl);
    }

    public final /* synthetic */ Object[] newArray(int n3) {
        return new ApiMetadata[n3];
    }
}

