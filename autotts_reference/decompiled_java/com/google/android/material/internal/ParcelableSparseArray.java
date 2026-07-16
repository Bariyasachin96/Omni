/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.util.SparseArray
 */
package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class ParcelableSparseArray
extends SparseArray<Parcelable>
implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseArray> CREATOR = new Parcelable.ClassLoaderCreator(){

        public ParcelableSparseArray a(Parcel parcel) {
            return new ParcelableSparseArray(parcel, null);
        }

        public ParcelableSparseArray b(Parcel parcel, ClassLoader classLoader) {
            return new ParcelableSparseArray(parcel, classLoader);
        }

        public ParcelableSparseArray[] c(int n3) {
            return new ParcelableSparseArray[n3];
        }
    };

    public ParcelableSparseArray() {
    }

    public ParcelableSparseArray(Parcel parcelableArray, ClassLoader classLoader) {
        int n3 = parcelableArray.readInt();
        int[] nArray = new int[n3];
        parcelableArray.readIntArray(nArray);
        parcelableArray = parcelableArray.readParcelableArray(classLoader);
        for (int i3 = 0; i3 < n3; ++i3) {
            this.put(nArray[i3], parcelableArray[i3]);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        int n4 = this.size();
        int[] nArray = new int[n4];
        Parcelable[] parcelableArray = new Parcelable[n4];
        for (int i3 = 0; i3 < n4; ++i3) {
            nArray[i3] = this.keyAt(i3);
            parcelableArray[i3] = (Parcelable)this.valueAt(i3);
        }
        parcel.writeInt(n4);
        parcel.writeIntArray(nArray);
        parcel.writeParcelableArray(parcelableArray, n3);
    }
}

