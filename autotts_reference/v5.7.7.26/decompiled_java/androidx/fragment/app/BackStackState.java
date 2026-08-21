/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package androidx.fragment.app;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.fragment.app.BackStackRecordState;
import java.util.List;

class BackStackState
implements Parcelable {
    public static final Parcelable.Creator<BackStackState> CREATOR = new Parcelable.Creator(){

        public BackStackState a(Parcel parcel) {
            return new BackStackState(parcel);
        }

        public BackStackState[] b(int n3) {
            return new BackStackState[n3];
        }
    };
    public final List c;
    public final List d;

    public BackStackState(Parcel parcel) {
        this.c = parcel.createStringArrayList();
        this.d = parcel.createTypedArrayList(BackStackRecordState.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeStringList(this.c);
        parcel.writeTypedList(this.d);
    }
}

