/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package com.google.android.material.stateful;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.customview.view.AbsSavedState;
import o.r;

public class ExtendableSavedState
extends AbsSavedState {
    public static final Parcelable.Creator<ExtendableSavedState> CREATOR = new Parcelable.ClassLoaderCreator(){

        public ExtendableSavedState a(Parcel parcel) {
            return new ExtendableSavedState(parcel, null, null);
        }

        public ExtendableSavedState b(Parcel parcel, ClassLoader classLoader) {
            return new ExtendableSavedState(parcel, classLoader, null);
        }

        public ExtendableSavedState[] c(int n3) {
            return new ExtendableSavedState[n3];
        }
    };
    public final r e;

    public ExtendableSavedState(Parcel parcel, ClassLoader stringArray) {
        super(parcel, (ClassLoader)stringArray);
        int n3 = parcel.readInt();
        stringArray = new String[n3];
        parcel.readStringArray(stringArray);
        Object[] objectArray = new Bundle[n3];
        parcel.readTypedArray(objectArray, Bundle.CREATOR);
        this.e = new r(n3);
        for (int i3 = 0; i3 < n3; ++i3) {
            this.e.put(stringArray[i3], objectArray[i3]);
        }
    }

    public /* synthetic */ ExtendableSavedState(Parcel parcel, ClassLoader classLoader, a a4) {
        this(parcel, classLoader);
    }

    public ExtendableSavedState(Parcelable parcelable) {
        super(parcelable);
        this.e = new r();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ExtendableSavedState{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" states=");
        stringBuilder.append(this.e);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n3) {
        super.writeToParcel(parcel, n3);
        int n4 = this.e.size();
        parcel.writeInt(n4);
        String[] stringArray = new String[n4];
        Bundle[] bundleArray = new Bundle[n4];
        for (n3 = 0; n3 < n4; ++n3) {
            stringArray[n3] = (String)this.e.f(n3);
            bundleArray[n3] = (Bundle)this.e.j(n3);
        }
        parcel.writeStringArray(stringArray);
        parcel.writeTypedArray((Parcelable[])bundleArray, 0);
    }
}

