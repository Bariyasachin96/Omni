/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package u1;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.GetServiceRequest;
import v1.b;

public final class h
implements Parcelable.Creator {
    public static void a(GetServiceRequest getServiceRequest, Parcel parcel, int n3) {
        int n4 = b.a(parcel);
        b.g(parcel, 1, getServiceRequest.c);
        b.g(parcel, 2, getServiceRequest.d);
        b.g(parcel, 3, getServiceRequest.e);
        b.l(parcel, 4, getServiceRequest.f, false);
        b.f(parcel, 5, getServiceRequest.g, false);
        b.n(parcel, 6, getServiceRequest.h, n3, false);
        b.d(parcel, 7, getServiceRequest.i, false);
        b.k(parcel, 8, (Parcelable)getServiceRequest.j, n3, false);
        b.n(parcel, 10, getServiceRequest.k, n3, false);
        b.n(parcel, 11, getServiceRequest.l, n3, false);
        b.c(parcel, 12, getServiceRequest.m);
        b.g(parcel, 13, getServiceRequest.n);
        b.c(parcel, 14, getServiceRequest.o);
        b.l(parcel, 15, getServiceRequest.o(), false);
        b.b(parcel, n4);
    }

    public final /* synthetic */ Object[] newArray(int n3) {
        return new GetServiceRequest[n3];
    }
}

