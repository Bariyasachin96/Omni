/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseIntArray
 */
package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

public class ParcelableSparseIntArray
extends SparseIntArray
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseIntArray> CREATOR = new Parcelable.Creator(){

        public ParcelableSparseIntArray a(Parcel parcel) {
            int n3 = parcel.readInt();
            ParcelableSparseIntArray parcelableSparseIntArray = new ParcelableSparseIntArray(n3);
            int[] nArray = new int[n3];
            int[] nArray2 = new int[n3];
            parcel.readIntArray(nArray);
            parcel.readIntArray(nArray2);
            for (int i3 = 0; i3 < n3; ++i3) {
                parcelableSparseIntArray.put(nArray[i3], nArray2[i3]);
            }
            return parcelableSparseIntArray;
        }

        public ParcelableSparseIntArray[] b(int n3) {
            return new ParcelableSparseIntArray[n3];
        }
    };

    public ParcelableSparseIntArray(int n3) {
        super(n3);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        int[] nArray = new int[this.size()];
        int[] nArray2 = new int[this.size()];
        for (n3 = 0; n3 < this.size(); ++n3) {
            nArray[n3] = this.keyAt(n3);
            nArray2[n3] = this.valueAt(n3);
        }
        parcel.writeInt(this.size());
        parcel.writeIntArray(nArray);
        parcel.writeIntArray(nArray2);
    }
}

