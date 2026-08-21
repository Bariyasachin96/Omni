/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package androidx.versionedparcelable;

import android.os.Parcel;
import android.os.Parcelable;
import o1.b;
import o1.c;

public class ParcelImpl
implements Parcelable {
    public static final Parcelable.Creator<ParcelImpl> CREATOR = new Parcelable.Creator(){

        public ParcelImpl a(Parcel parcel) {
            return new ParcelImpl(parcel);
        }

        public ParcelImpl[] b(int n3) {
            return new ParcelImpl[n3];
        }
    };
    public final c c;

    public ParcelImpl(Parcel parcel) {
        this.c = new b(parcel).u();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        new b(parcel).L(this.c);
    }
}

