/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.SparseBooleanArray
 */
package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class ParcelableSparseBooleanArray
extends SparseBooleanArray
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseBooleanArray> CREATOR = new Parcelable.Creator(){

        public ParcelableSparseBooleanArray a(Parcel parcel) {
            int n3 = parcel.readInt();
            ParcelableSparseBooleanArray parcelableSparseBooleanArray = new ParcelableSparseBooleanArray(n3);
            int[] nArray = new int[n3];
            boolean[] blArray = new boolean[n3];
            parcel.readIntArray(nArray);
            parcel.readBooleanArray(blArray);
            for (int i3 = 0; i3 < n3; ++i3) {
                parcelableSparseBooleanArray.put(nArray[i3], blArray[i3]);
            }
            return parcelableSparseBooleanArray;
        }

        public ParcelableSparseBooleanArray[] b(int n3) {
            return new ParcelableSparseBooleanArray[n3];
        }
    };

    public ParcelableSparseBooleanArray(int n3) {
        super(n3);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        int[] nArray = new int[this.size()];
        boolean[] blArray = new boolean[this.size()];
        for (n3 = 0; n3 < this.size(); ++n3) {
            nArray[n3] = this.keyAt(n3);
            blArray[n3] = this.valueAt(n3);
        }
        parcel.writeInt(this.size());
        parcel.writeIntArray(nArray);
        parcel.writeBooleanArray(blArray);
    }
}

