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
import androidx.fragment.app.BackStackState;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

final class FragmentManagerState
implements Parcelable {
    public static final Parcelable.Creator<FragmentManagerState> CREATOR = new Parcelable.Creator(){

        public FragmentManagerState a(Parcel parcel) {
            return new FragmentManagerState(parcel);
        }

        public FragmentManagerState[] b(int n3) {
            return new FragmentManagerState[n3];
        }
    };
    public ArrayList c;
    public ArrayList d;
    public BackStackRecordState[] e;
    public int f;
    public String g = null;
    public ArrayList h = new ArrayList();
    public ArrayList i = new ArrayList();
    public ArrayList j;

    public FragmentManagerState() {
    }

    public FragmentManagerState(Parcel parcel) {
        this.c = parcel.createStringArrayList();
        this.d = parcel.createStringArrayList();
        this.e = (BackStackRecordState[])parcel.createTypedArray(BackStackRecordState.CREATOR);
        this.f = parcel.readInt();
        this.g = parcel.readString();
        this.h = parcel.createStringArrayList();
        this.i = parcel.createTypedArrayList(BackStackState.CREATOR);
        this.j = parcel.createTypedArrayList(FragmentManager.LaunchedFragmentInfo.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n3) {
        parcel.writeStringList((List)this.c);
        parcel.writeStringList((List)this.d);
        parcel.writeTypedArray((Parcelable[])this.e, n3);
        parcel.writeInt(this.f);
        parcel.writeString(this.g);
        parcel.writeStringList((List)this.h);
        parcel.writeTypedList((List)this.i);
        parcel.writeTypedList((List)this.j);
    }
}

